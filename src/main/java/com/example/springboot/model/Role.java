package com.example.springboot.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "Role_Name_Uk",columnNames = "Role_Name")})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Role_Id")
    private Long id;
    @Column(name = "Role_Name")
    private String roleName;
    @ManyToMany(mappedBy = "roleSet",fetch = FetchType.LAZY)
    private Set<UserApp> userAppSet = new HashSet<>();
}
