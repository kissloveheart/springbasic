package com.example.springboot.controller;

import com.example.springboot.command.UserAppCommand;
import com.example.springboot.dto.ProductInfoDTO;
import com.example.springboot.handler.OrderTransactionException;
import com.example.springboot.model.Orders;
import com.example.springboot.model.Product;
import com.example.springboot.repository.ProductRepository;
import com.example.springboot.service.OrdersService;
import com.example.springboot.service.ProductService;
import com.example.springboot.service.ProductServiceImpl;
import com.example.springboot.service.UserAppService;
import com.example.springboot.session.CartInfo;
import com.example.springboot.utils.SessionUtil;
import com.example.springboot.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class ShopCartController {
    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserAppService userAppService;

    @Autowired
    OrdersService ordersService;

    @RequestMapping({ "/addCart/{id:\\d+}" })
    public String addToCart(HttpServletRequest request, @PathVariable String id) {

        ProductInfoDTO product = null;

        if (id != null && id.length() > 0) {
            product = productRepository.findById(Long.parseLong(id),ProductInfoDTO.class);
        }

        if (product != null) {
            CartInfo cartInfo = SessionUtil.getCartInSession(request);
            cartInfo.addProduct(product, 1);
        }
        return "redirect:/";
    }

    @RequestMapping({ "/removeCart/{id:\\d+}" })
    public String removeProductCart(HttpServletRequest request, @PathVariable  String id) {
        ProductInfoDTO product = null;

        if (id != null && id.length() > 0) {
            product = productRepository.findById(Long.parseLong(id),ProductInfoDTO.class);
        }

        if (product != null) {
            CartInfo cartInfo = SessionUtil.getCartInSession(request);
            cartInfo.removeProduct(product);
        }
        return "redirect:/";
    }

    @RequestMapping("/orderConfirm")
    public  String orderConfirm(HttpServletRequest request,Model model, Principal principal){
        CartInfo myCart = SessionUtil.getCartInSession(request);
        model.addAttribute("cartForm", myCart);

        User loginUser = (User) ((Authentication) principal).getPrincipal();
        UserAppCommand command = userAppService.findCommandByEmail(loginUser.getUsername());
        model.addAttribute("userInfo", command);

        return "/web/orderConfirm";
    }

    @RequestMapping("/increaseQuantity/{id:\\d+}")
    public String increaseQuantity(HttpServletRequest request, @PathVariable  String id){
        CartInfo cartInfo = SessionUtil.getCartInSession(request);
        cartInfo.updateProduct(Long.parseLong(id),1);
        return "redirect:/orderConfirm";
    }

    @RequestMapping("/decreaseQuantity/{id:\\d+}")
    public String decreaseQuantity(HttpServletRequest request, @PathVariable  String id){
        CartInfo cartInfo = SessionUtil.getCartInSession(request);
        cartInfo.updateProduct(Long.parseLong(id),-1);
        return "redirect:/orderConfirm";
    }

    @RequestMapping("/saveOrder")
    public String saveOrder(HttpServletRequest request, Model model){
        Long id;
        try {
            id = ordersService.cashOrder(request);
        } catch (OrderTransactionException e) {
            model.addAttribute("Fail", e.getMessage());
            return "/web/orderSuccess";
        }
        model.addAttribute("OrderId",id);
        SessionUtil.removeCartInSession(request);
        return "/web/orderSuccess";
    }



}
