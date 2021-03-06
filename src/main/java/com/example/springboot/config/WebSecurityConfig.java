package com.example.springboot.config;

import com.example.springboot.cache.CustomLogoutHandler;
import com.example.springboot.jwt.JwtAuthenticationFilter;
import com.example.springboot.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private CustomLogoutHandler logoutHandler;
    @Autowired
    private  DataSource dataSource;
    @Autowired
    private  UserDetailsServiceImpl userDetailsService;



    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

   @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        // S??t ?????t d???ch v??? ????? t??m ki???m User trong Database.
        // V?? s??t ?????t PasswordEncoder.
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.headers().frameOptions().disable();

        // C??c trang kh??ng y??u c???u login
        http.authorizeRequests().antMatchers("/","/h2","/index","/login","/logout","/api/login",
                "/regis/**","/error","/schedule/**").permitAll();

        // Trang /userInfo y??u c???u ph???i login v???i vai tr?? ROLE_USER ho???c ROLE_ADMIN.
        // N???u ch??a login, n?? s??? redirect t???i trang /login.
        http.authorizeRequests().antMatchers("/userInfo","/orderConfirm").hasAnyAuthority("ADMIN","USER");

        // Trang ch??? d??nh cho ADMIN
        http.authorizeRequests().antMatchers("/admin").hasAuthority("ADMIN");

        // Authentication jwt api
        http.authorizeRequests().antMatchers("/api/**").authenticated();

        // Khi ng?????i d??ng ???? login, v???i vai tr?? XX.
        // Nh??ng truy c???p v??o trang y??u c???u vai tr?? YY,
        // Ngo???i l??? AccessDeniedException s??? n??m ra.
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

        // C???u h??nh cho Login Form.
        http.authorizeRequests().and().formLogin()//
                // Submit URL c???a trang login
                .loginProcessingUrl("/j_spring_security_check") // Submit URL
                .loginPage("/login")//
                .defaultSuccessUrl("/userInfo")//
                .failureUrl("/login?error=true")//
                .usernameParameter("email")//
                .passwordParameter("password")
                .failureHandler(authenticationFailureHandler)
                // C???u h??nh cho Logout Page.
                .and().logout()
                .logoutUrl("/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessUrl("/logoutSuccessful");
               // .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //add for API

        // C???u h??nh Remember Me.
        http.authorizeRequests().and().rememberMe().tokenRepository(this.persistentTokenRepository())
                .tokenValiditySeconds(24*60*60); // 24h
        // Th??m m???t l???p Filter UsernamePassword filter ki???m tra jwt
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

/*
    // Token stored in Database
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }*/
     //Token stored in Memory (Of Web Server).
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }

}
