package cn.eusunpower.controller

import cn.eusunpower.config.IAuthenticationFacade
import cn.eusunpower.model.ResponseData
import cn.eusunpower.support.IErrorResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*


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
        return "product id : " + authenticationFacade.getCurrentUser()
    }

    @GetMapping("/order/{id}")
    fun getOrder(@PathVariable id: String): IErrorResponse<String> {
        return IErrorResponse(data = "null error", url = "/order/$id")
    }
}