package cn.eusunpower.controller

import cn.eusunpower.model.ResponseData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import cn.eusunpower.config.IAuthenticationFacade


@RestController
@RequestMapping(value = ["/user"])
class UserController {
    @Autowired
    lateinit var authenticationFacade: IAuthenticationFacade

    @GetMapping(value = ["/username"])
    @ResponseBody
    fun currentUserName(authentication: Authentication): ResponseData<String> = ResponseData(data = authentication.name)

    @GetMapping("/product/{id}")
    fun getProduct(@PathVariable id: String): String {
        val authentication = SecurityContextHolder.getContext().authentication
        return "product id : " + authenticationFacade.getAuthentication().principal.toString()
    }

    @GetMapping("/order/{id}")
    fun getOrder(@PathVariable id: String): String {
        val authentication = SecurityContextHolder.getContext().authentication
        return "order id : " + id
    }
}