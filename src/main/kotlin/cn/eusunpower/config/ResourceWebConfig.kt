package cn.eusunpower.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter


@Configuration
@EnableWebMvc
class ResourceWebConfig : WebMvcConfigurerAdapter()