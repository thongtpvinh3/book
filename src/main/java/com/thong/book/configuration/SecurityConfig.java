package com.thong.book.configuration;

import com.thong.book.common.AntMatchersList;
import com.thong.book.service.UserServiceSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.Cookie;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserServiceSecurity userServiceSecurity;
    private final JwtRequestFilter jwtRequestFilter;
    private final CustomAuthenticationEntryPoint authEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceSecurity);
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutUrl("/logout")
                .addLogoutHandler((request, response, auth) -> {
                    for (Cookie cookie : request.getCookies()) {
                        String cookieName = cookie.getName();
                        Cookie cookieToDelete = new Cookie(cookieName, null);
                        cookieToDelete.setMaxAge(0);
                        response.addCookie(cookieToDelete);
                    }
                });

        http.exceptionHandling().authenticationEntryPoint(authEntryPoint).accessDeniedHandler(authEntryPoint);

        http.authorizeRequests().antMatchers(AntMatchersList.getAntMatchersList()).permitAll()
                .antMatchers("/api/book/delete/*").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }


}
