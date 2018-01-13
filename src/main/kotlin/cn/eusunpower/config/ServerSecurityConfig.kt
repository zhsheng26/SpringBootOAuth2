package cn.eusunpower.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
@EnableWebSecurity
class ServerSecurityConfig : WebSecurityConfigurerAdapter() {

//    override fun configure(auth: AuthenticationManagerBuilder) {
//        auth.inMemoryAuthentication()
//                .withUser("user_1").password("123456").authorities("USER")
////        auth.parentAuthenticationManager(authenticationManager)
////                .inMemoryAuthentication()
////                .withUser("john").password("123").roles("USER")
//    }

    @Bean
    override fun userDetailsService(): UserDetailsService {
        val manager = InMemoryUserDetailsManager()
        manager.createUser(User.withUsername("user_1").password("123456").authorities("USER").build())
        manager.createUser(User.withUsername("user_2").password("123456").authorities("USER").build())
        return manager
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {

//        http
//                .requestMatchers().anyRequest()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/oauth/*").permitAll()
        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**", "/oauth/*")
                .permitAll()
                .anyRequest()
                .authenticated()
    }

}