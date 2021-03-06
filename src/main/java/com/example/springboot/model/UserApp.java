package com.example.springboot.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "Email_Uk",columnNames = "email")})
public class UserApp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Id")
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(name="password")
    private String encryptedPassword;
    @Temporal(TemporalType.DATE)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createDate = new Date();
    private boolean enabled;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name ="User_Role",joinColumns = {@JoinColumn(name="User_Id")},
            inverseJoinColumns = {@JoinColumn(name = "Role_Id")})
    @ToString.Exclude
    private Set<Role>  roleSet = new HashSet<>();

    private String phoneNumber;
    private String address;
    private Double cash;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @ToString.Exclude
    @JoinColumn(name = "VerificationToken_Id")
    private VerificationToken verificationToken;

    @OneToMany(mappedBy = "userApp",cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Orders> ordersSet = new HashSet<>();

    public void addRole(Role role){
        this.getRoleSet().add(role);
    }
}
