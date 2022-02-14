package com.example.springboot.controller;

import com.example.springboot.model.Category;
import com.example.springboot.model.Product;
import com.example.springboot.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class PageControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);


    @Autowired
    ProductRepository productRepository;

    @Autowired
    MockMvc mvc;

    @BeforeEach
    void setUp(){
        Category category = new Category();
        category.setName("test");
        productRepository.saveAll(IntStream.range(0,20)
                .mapToObj(i-> {
                    Product product = new Product();
                    product.setCategory(category);
                    product.setName("test"+i);
                    return product;
                }).collect(Collectors.toList()));

/*        MockitoAnnotations.openMocks(this);
        pageController = new PageController(productRepository);
        mvc = MockMvcBuilders.standaloneSetup(pageController).build();*/
    }

    @ParameterizedTest
    @CsvSource({"0,5", "1,5", "2,3"})
    void getProductList(String pageNumber, String pageSize) throws Exception {

        MvcResult result = mvc.perform(get("/list-product?page="+pageNumber+"&size="+pageSize+"&sort=name").contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['pageable']['paged']").value("true"))
                .andExpect(jsonPath("$['pageable']['pageSize']").value(pageSize))
                .andExpect(jsonPath("$['pageable']['pageNumber']").value(pageNumber))
                .andDo(print())
                .andReturn();
        String resultBody = result.getResponse().getContentAsString();
        log.info(resultBody);
    }
}