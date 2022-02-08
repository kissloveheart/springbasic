package com.example.springboot.bootstrap;



import com.example.springboot.dto.ProductInfoDTO;
import com.example.springboot.model.Category;
import com.example.springboot.model.Product;
import com.example.springboot.projection.ProductInfo;
import com.example.springboot.repository.ProductRepository;
import com.example.springboot.service.ProductService;
import com.example.springboot.service.RoleService;
import com.example.springboot.service.UserAppService;
import com.example.springboot.model.Role;
import com.example.springboot.model.UserApp;
import com.example.springboot.repository.UserAppRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DataLoad implements ApplicationListener<ContextRefreshedEvent> {
    private static final String PASSWORD ="$2a$10$f7QCa1euQIci96KeGFZ.meDqNC53EFoeK0ic0T94AE/SJIhB2kKxy" ;

    private final UserAppRepository userAppRepository;
    private final RoleService roleService;
    private final UserAppService userAppService;
    private final ProductService productService;
    private final ProductRepository productRepository;

    public DataLoad(UserAppRepository userAppRepository, RoleService roleService, UserAppService userAppService, ProductService productService, ProductRepository productRepository) {
        this.userAppRepository = userAppRepository;
        this.roleService = roleService;
        this.userAppService = userAppService;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //roleRepository.saveAll(getRoleList());
        userAppRepository.saveAll(getUserAppList());

/*        UserApp userAppAdmin = userAppService.findByEmail("admin@admin.com");
       // testSession(userAppAdmin);
        log.info(roleService.getRoleListFromUserApp(userAppAdmin).toString());
        UserApp userAppUser = userAppService.findByEmail("user@user.com");
        log.info(roleService.getRoleListFromUserApp(userAppUser).toString());
        log.info("Data user save successfully");*/

        productService.saveAll(getListProduct());
/*        List<ProductInfo> productInfos = new ArrayList<>(productRepository.findBy(ProductInfo.class));
        productInfos.forEach(productInfo -> {
            log.info(productInfo.getName());
            log.info(String.valueOf(productInfo.getPrice()));
            log.info(productInfo.getCategory().getName());
        });
        */
        log.info(productRepository.findById(1L, ProductInfoDTO.class).toString());
        ProductInfo productInfo = productRepository.findById(1L, ProductInfo.class);
        log.info(productInfo.getCategory().getName());
    }

    public List<Role> getRoleList(){
        List<Role> roleList= new ArrayList<>();

        Role roleAdmin = new Role();
        roleAdmin.setRoleName("ADMIN");

        Role roleUser = new Role();
        roleUser.setRoleName("USER");
        roleList.add(roleAdmin);
        roleList.add(roleUser);
        return roleList;
    }

    public List<UserApp> getUserAppList(){
        Date date = new Date();

/*        // get user roles
        Optional<Role> roleAdminOptional = roleRepository.findByRoleName("ADMIN");
        if(!roleAdminOptional.isPresent()){
            throw new RuntimeException("Role admin not found!");
        }

        Optional<Role> roleUserOptional = roleRepository.findByRoleName("USER");
        if(!roleUserOptional.isPresent()){
            throw new RuntimeException("Role user not found!");
        }
        Role roleAdmin = roleAdminOptional.get();
        Role roleUser = roleUserOptional.get();*/

        Role roleAdmin = new Role();
        roleAdmin.setRoleName("ADMIN");

        Role roleUser = new Role();
        roleUser.setRoleName("USER");




        List<UserApp> userAppList = new ArrayList<>();
        UserApp userApp1 = new UserApp();
        userApp1.setEmail("admin@admin.com");
        userApp1.setEncryptedPassword(PASSWORD);
        userApp1.setEnabled(true);
        userApp1.setCash(5000D);
       // userApp1.setCreateDate(date);
        userApp1.getRoleSet().add(roleAdmin);
        userApp1.getRoleSet().add(roleUser);



        userAppList.add(userApp1);



        UserApp userApp2 = new UserApp();
        userApp2.setEmail("user@user.com");
        userApp2.setEncryptedPassword(PASSWORD);
        userApp2.setEnabled(true);
        userApp2.setCash(5000D);
       // userApp2.setCreateDate(date);
        userApp2.getRoleSet().add(roleUser);

        userAppList.add(userApp2);
        return userAppList;
    }

    public List<Product> getListProduct(){
        Category categoryPhone = new Category();
        categoryPhone.setName("Phone");
        Category categoryLaptop = new Category();
        categoryLaptop.setName("Laptop");

        Product productPhone = new Product();
        productPhone.setName("Iphone 8");
        productPhone.setPrice(399.00);
        productPhone.setCategory(categoryPhone);

        Product productPhone1 = new Product();
        productPhone1.setName("Iphone 9");
        productPhone1.setPrice(499.00);
        productPhone1.setCategory(categoryPhone);

        Product productPhone2 = new Product();
        productPhone2.setName("Iphone 10");
        productPhone2.setPrice(599.00);
        productPhone2.setCategory(categoryPhone);

        Product productPhone3 = new Product();
        productPhone3.setName("Iphone 11");
        productPhone3.setPrice(699.00);
        productPhone3.setCategory(categoryPhone);

        Product productLaptop = new Product();
        productLaptop.setName("Dell X99");
        productLaptop.setPrice(1999.00);
        productLaptop.setCategory(categoryLaptop);

        Product productLaptop1 = new Product();
        productLaptop1.setName("Lenovo Y11");
        productLaptop1.setPrice(999.00);
        productLaptop1.setCategory(categoryLaptop);

        List<Product> products = new ArrayList<>();
        products.add(productPhone);
        products.add(productPhone1);
        products.add(productPhone2);
        products.add(productPhone3);
        products.add(productLaptop);
        products.add(productLaptop1);

        return products;


    }

    public void testSession(UserApp userApp){
        userApp.getRoleSet().forEach(role -> log.info(role.getRoleName()));
    }
}
