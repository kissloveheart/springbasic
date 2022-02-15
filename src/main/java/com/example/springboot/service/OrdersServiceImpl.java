package com.example.springboot.service;

import com.example.springboot.activemq.Producer;
import com.example.springboot.dto.OrderInfoDTO;
import com.example.springboot.handler.OrderTransactionException;
import com.example.springboot.model.OrderDetail;
import com.example.springboot.model.Orders;
import com.example.springboot.model.Product;
import com.example.springboot.model.UserApp;
import com.example.springboot.repository.OrdersRepository;
import com.example.springboot.repository.ProductRepository;
import com.example.springboot.repository.UserAppRepository;
import com.example.springboot.session.CartInfo;
import com.example.springboot.session.CartLineInfo;
import com.example.springboot.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    UserAppRepository userAppRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserAppService userAppService;

    @Autowired
    Producer producer;


    @Override
    @Transactional(propagation = Propagation.MANDATORY )
    public Orders saveOrder(CartInfo cartInfo) throws OrderTransactionException{

        UserApp userApp = userAppService.getCurrentUserApp();
        if (userApp == null){
            throw new OrderTransactionException("User not login");
        }
        Orders orders = new Orders();
        orders.setUserApp(userApp);

        if (cartInfo.isEmpty()){
            log.info("The cart don't have any items");
            throw new OrderTransactionException("The cart don't have any items");
        }

        for (CartLineInfo carLineInfo : cartInfo.getCartLines()){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrders(orders);
            Optional<Product> productOptional = productRepository.findById(carLineInfo.getProductInfo().getId());
            if(productOptional.isEmpty()){
                log.info("The product not exist!");
                throw new OrderTransactionException("The product not exist!");
            }
            orderDetail.setProduct(productOptional.get());
            orderDetail.setQuantity(carLineInfo.getQuantity());
            orderDetail.setAmount(carLineInfo.getQuantity()*productOptional.get().getPrice());
            orders.getOrderDetailList().add(orderDetail);
        }
        orders.setTotalAmount(orders.getOrderDetailList().stream().map(OrderDetail::getAmount)
                .reduce(0D, Double::sum));
        if(userApp.getCash() < orders.getTotalAmount()){
            log.info("the money is not enough");
            throw new OrderTransactionException("You don't have money to place the order");
        }

        // charge money from user
        userApp.setCash(userApp.getCash() - orders.getTotalAmount());
        userAppRepository.save(userApp);

        return ordersRepository.save(orders);

    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void tipMoneyForAdmin(){
        UserApp admin = userAppRepository.findByEmail("admin@admin.com");
        admin.setCash(admin.getCash()+100D);
        userAppRepository.save(admin);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = OrderTransactionException.class)
    public Long cashOrder(HttpServletRequest request) throws OrderTransactionException {
        tipMoneyForAdmin();
        CartInfo cartInfo = SessionUtil.getCartInSession(request);
        Orders orders = saveOrder(cartInfo);
        //send message to queue
        producer.send(orders);
        return orders.getId();
    }

    @Override
    public List<OrderInfoDTO> getOrderListByUser() {
        UserApp userApp = userAppService.getCurrentUserApp();
        return  new ArrayList<>(ordersRepository.findByUserApp(OrderInfoDTO.class, userApp));
    }




}
