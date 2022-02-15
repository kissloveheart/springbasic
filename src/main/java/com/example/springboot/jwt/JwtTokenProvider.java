package com.example.springboot.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Slf4j
@Component
public class JwtTokenProvider {
    // Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    private final String JWT_SECRET = "1111111111";

    // Tạo ra jwt từ thông tin user
    public String generateToken(UserDetails userDetails){
        Date now = new Date(System.currentTimeMillis());
        //Thời gian có hiệu lực của chuỗi jwt
        //milis
        long expiration = 60*1000L;
        Date expiryDate = new Date(System.currentTimeMillis()+ expiration);
        // Tạo chuỗi json web token từ email của user.
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }
    // Lấy thông tin claims từ token
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    // Lấy  user email từ jwt
    public String getUserEmailFromToken(String token){
        return getClaimFromToken(token,Claims::getSubject);
    }
    // Lấy ngày hết hạn
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public boolean validateToken(String authToken, UserDetails userDetails) {
        final String username = getUserEmailFromToken(authToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(authToken));
    }


}
