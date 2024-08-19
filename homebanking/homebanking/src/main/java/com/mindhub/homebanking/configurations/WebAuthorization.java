package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
class WebAuthorization {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/web/index.html", "/web/css/**", "/web/js/**", "/web/img/**").permitAll()
                .antMatchers("/admin/**").permitAll() // Permitir acceso a rutas bajo /admin sin restricciones
                .antMatchers(HttpMethod.POST, "/clients", "/api/clients").permitAll()
                .antMatchers(HttpMethod.PUT, "/clients/**", "/api/clients/**").permitAll()
                .antMatchers(HttpMethod.PATCH, "/clients/**", "/api/clients/**").permitAll()
                .antMatchers(HttpMethod.GET,"/clients/**", "/api/clients/**").permitAll()
                .antMatchers(HttpMethod.POST, "/loans", "/api/loans").permitAll()
                .antMatchers(HttpMethod.POST, "/loans/**", "/api/loans/**").permitAll()
                .antMatchers(HttpMethod.POST, "/cards", "/api/cards").permitAll()
                .antMatchers(HttpMethod.PUT, "/cards/**", "/api/cards/**").permitAll()
                .antMatchers(HttpMethod.PATCH, "/cards/**", "/api/cards/**").permitAll();



        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");

        // Desactivar verificación de tokens CSRF
        http.csrf().disable();

        // Desactivar opciones de marco para acceder a la consola H2
        http.headers().frameOptions().disable();

        // Si el usuario no está autenticado, enviar una respuesta de autenticación fallida
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // Si el inicio de sesión tiene éxito, limpiar las banderas de autenticación
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // Si el inicio de sesión falla, enviar una respuesta de autenticación fallida
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // Si el cierre de sesión tiene éxito, enviar una respuesta exitosa
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        return http.build();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
