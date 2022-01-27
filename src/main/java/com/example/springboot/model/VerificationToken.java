package com.example.springboot.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    @OneToOne(targetEntity = UserApp.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "user_id", foreignKey = @ForeignKey(name = "FK_VERIFY_USER"))
    private UserApp userApp;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;

    public VerificationToken(String token, UserApp userApp) {
        this.token = token;
        this.userApp = userApp;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }



}
