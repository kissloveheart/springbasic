package com.example.springboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Orders_Id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id")
    @ToString.Exclude
    @JsonIgnore
    private UserApp userApp;
    @OneToMany(mappedBy = "orders",cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<OrderDetail> orderDetailList = new ArrayList<>();
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate = new Date();
    private Double totalAmount;
}
