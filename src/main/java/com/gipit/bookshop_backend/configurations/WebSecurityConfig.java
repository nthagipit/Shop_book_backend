package com.gipit.bookshop_backend.configurations;

import com.gipit.bookshop_backend.components.Oauth2Client.CustomOAuth2UserService;
import com.gipit.bookshop_backend.components.Oauth2Client.OAuth2LoginSuccessHandler;
import com.gipit.bookshop_backend.filters.JwtTokenFilter;
import com.gipit.bookshop_backend.services.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig implements WebMvcConfigurer {

    private final JwtTokenFilter jwtTokenFilter;

    private String[] getPublicPostEndpoints(){
        return new String[]{
                String.format("%s/auth/login",apiPrefix),
                String.format("%s/auth/logout",apiPrefix),
                String.format("%s/auth/refresh",apiPrefix),
                String.format("%s/users/**",apiPrefix)
        };
    }

    @Value("${api.prefix}")
    private String apiPrefix;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserService userService, CustomOAuth2UserService customOAuth2UserService, OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler) throws Exception {

        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(f->f.disable())
                .exceptionHandling(e->
                        e.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                                .accessDeniedHandler(new CustomAccessDeniedHandler())
                )

                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests->
                        requests
                                .requestMatchers(HttpMethod.POST,getPublicPostEndpoints()).permitAll()
                                .requestMatchers(String.format("%s/auth/oauth2/**",apiPrefix)).permitAll()
                                .requestMatchers("/oauth2/**", "/login/oauth2/**", "/error","/images/**").permitAll()
                                .requestMatchers(HttpMethod.GET,String.format("%s/books/**",apiPrefix),
                                                                String.format("%s/carts/**",apiPrefix),
                                                                String.format("%s/cartd-etails/**",apiPrefix),
                                                                String.format("%s/users/active/**",apiPrefix)).permitAll()
                                .anyRequest().authenticated()

                )
                .oauth2Login(oauth2->oauth2
                                .successHandler(oAuth2LoginSuccessHandler)
                                .userInfoEndpoint(userInfo->userInfo
                                        .userService(customOAuth2UserService))
                        )
        ;

        return http.build();

    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:uploads/images/");

    }


}
