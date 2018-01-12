package cn.eusunpower.config

import org.hibernate.SessionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
}
