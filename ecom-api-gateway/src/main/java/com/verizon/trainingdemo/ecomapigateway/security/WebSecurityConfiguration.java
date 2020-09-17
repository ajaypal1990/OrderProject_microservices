package com.verizon.trainingdemo.ecomapigateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private final Environment environment;
    
    @Autowired
    private com.verizon.trainingdemo.ecomapigateway.security.Configuration configuration;

    public WebSecurityConfiguration(com.verizon.trainingdemo.ecomapigateway.security.Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
        httpSecurity.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/user/authenticate").permitAll()
				.antMatchers("/h2-console/**").permitAll()
				.antMatchers("/actuator/**").permitAll()
                .antMatchers(HttpMethod.GET).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAfter(new JWTAuthorizationFilter(configuration), UsernamePasswordAuthenticationFilter.class);

        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
