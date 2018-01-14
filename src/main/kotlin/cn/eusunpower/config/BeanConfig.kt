package cn.eusunpower.config

import org.hibernate.SessionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.annotation.Resource
import javax.persistence.EntityManagerFactory


@Configuration
class BeanConfig {

    @Resource
    lateinit var entityManagerFactory: EntityManagerFactory

    @Bean
    fun sessionFactory(): SessionFactory {
        return entityManagerFactory.unwrap(SessionFactory::class.java)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}
