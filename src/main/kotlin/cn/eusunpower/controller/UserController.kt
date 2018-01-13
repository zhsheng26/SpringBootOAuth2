package cn.eusunpower.controller

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
class UserController {
    @GetMapping("/product/{id}")
    fun getProduct(@PathVariable id: String): String {
        val authentication = SecurityContextHolder.getContext().authentication
        return "product id : " + id
    }

    @GetMapping("/order/{id}")
    fun getOrder(@PathVariable id: String): String {
        val authentication = SecurityContextHolder.getContext().authentication
        return "order id : " + id
    }
}