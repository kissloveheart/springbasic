package com.example.springboot.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserAppCommand {
    private Long id;
    private String email;
    private String password;
    private Date createDate = new Date();
    private boolean enabled;
    private Set<String> roleNameSet = new HashSet<>();

}