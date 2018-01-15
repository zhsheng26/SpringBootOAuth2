package cn.eusunpower.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.web.servlet.config.annotation.*


@Configuration
@EnableWebMvc
class ResourceWebConfig : WebMvcConfigurerAdapter() {
    @Bean
    fun propertySourcesPlaceholderConfigurer(): PropertySourcesPlaceholderConfigurer {
        return PropertySourcesPlaceholderConfigurer()
    }

    override fun configureDefaultServletHandling(
            configurer: DefaultServletHandlerConfigurer?) {
        configurer!!.enable()
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/")
    }

    @Bean
    fun verifyInterceptor(): Interceptor {
        return Interceptor()
    }


    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(verifyInterceptor())
        super.addInterceptors(registry)
    }
}