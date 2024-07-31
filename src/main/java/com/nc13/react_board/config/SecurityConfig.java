package com.nc13.react_board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) ->
                        // 프론트로 분리하면서 8080의 security 의 설정 값을 달리 해야 함.
                        authorize
                                .requestMatchers("/user/**").permitAll()
                                .anyRequest().authenticated())
                .formLogin((form) ->
                        form
                                .loginPage("/login")
                                // 프론트3000번 포트에서 로그인할때 값을
                                // 백엔드로 오도록 해주는 설정이다.
                                // form 데이터로 오도록 함
                                .loginProcessingUrl("/user/auth")
                                .defaultSuccessUrl("/user/authSuccess"))
                .logout((logout) ->
                        logout
                                .logoutUrl("/user/logOut")
                                .logoutSuccessUrl("/user/logOutSuccess")
                                .deleteCookies("JSESSIONID")
                );

        httpSecurity.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    //CORS 속성 걸기 - controller 가 아니기 때문에 @bean 으로 만든다.
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 정보를 보내는 것을 허락하는 코드 -> 로그인 정보를 보내줄 수 있도록 함.
        configuration.setAllowCredentials(true);
        // 어디에서 오는 정보를 허락하겠나
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return new CorsFilter(source);
    }
}

