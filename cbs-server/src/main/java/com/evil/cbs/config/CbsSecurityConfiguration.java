package com.evil.cbs.config;

import com.evil.cbs.security.MySavedRequestAwareAuthenticationSuccessHandler;
import com.evil.cbs.security.RestAuthenticationEntryPoint;
import com.evil.cbs.web.rest.error.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class CbsSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder encoder;

    private SimpleUrlAuthenticationFailureHandler myFailureHandler = new SimpleUrlAuthenticationFailureHandler();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/v1/api/csrfAttacker*").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/api/users/**").hasRole("ADMIN")
                .antMatchers("/v1/api/users/**").permitAll()
                .antMatchers(HttpMethod.POST,"/v1/api/halls/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/v1/api/halls/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/v1/api/halls/**").hasRole("USER")
                .antMatchers("/v1/api/auth/**").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/api/movies/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/v1/api/movies/**").hasRole("ADMIN")
                .antMatchers("/v1/api/movies/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/v1/api/movie-sessions/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/v1/api/movie-sessions/**").hasRole("ADMIN")
                .antMatchers("/v1/api/movie-sessions/**").hasRole("USER")
                .antMatchers("/v1/api/admin/**").hasRole("ADMIN")
                .and()
                .formLogin()
                .successHandler(mySuccessHandler)
                .failureHandler(myFailureHandler)
                .and()
                .httpBasic()
                .and()
                .logout();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(encoder)
                .usersByUsernameQuery("select email, password, enabled from cbs.users where email = ?")
                .authoritiesByUsernameQuery("select email, role from cbs.users where email = ?");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
