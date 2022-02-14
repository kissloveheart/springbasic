package com.example.springboot.repository;

import com.example.springboot.projection.IUserALLInfoDTO;
import com.example.springboot.model.UserApp;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserAppRepository extends CrudRepository<UserApp,Long> {
    @Query("select u from UserApp u join fetch u.roleSet where u.email =:email")
    @Cacheable(cacheNames = "users", key = "#email")
    UserApp findByEmail(@Param("email") String email);

    @Override
    //@CachePut(cacheNames = "users",key = "#entity.email")
    @CacheEvict(cacheNames = "users",key = "#entity.email")
    <S extends UserApp> S save(S entity);

    @Query(value = "SELECT U.EMAIL, U.CASH, V.TOKEN, R.ROLE_NAME AS ROLENAME, O.TOTAL_AMOUNT AS TOTALAMOUNT FROM USER_APP U" +
            " LEFT JOIN VERIFICATION_TOKEN V ON U.USER_ID = V.USER_ID" +
            " LEFT JOIN USER_ROLE UR ON U.USER_ID = UR.USER_ID" +
            " LEFT JOIN ROLE R ON UR.ROLE_ID = R.ROLE_ID" +
            " LEFT JOIN ORDERS O ON U.USER_ID = O.USER_ID", nativeQuery = true)
    List<IUserALLInfoDTO> findAllUserAppInfoNative();

    @Query("select u.email as email, u.cash as cash, v.token as token," +
            " r.roleName as roleName, o.totalAmount as totalAmount, p.name as productName, p.price as productPrice   " +
            "from UserApp u left join VerificationToken v on v.userApp.id = u.id" +
            " left join u.roleSet r left join u.ordersSet o join o.orderDetailList od join od.product p")
    List<IUserALLInfoDTO> findAllUserAppInfo();


}
