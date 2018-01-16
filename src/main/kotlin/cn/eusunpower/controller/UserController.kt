package cn.eusunpower.controller

import cn.eusunpower.model.ResponseData
import cn.eusunpower.model.entity.UserEntity
import cn.eusunpower.service.UserService
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource


@RestController
@RequestMapping(value = ["/user"])
class UserController {
    @Resource
    lateinit var userService: UserService

    @GetMapping(value = ["username"])
    fun currentUserName(authentication: Authentication): ResponseData<String> = ResponseData(data = authentication.name)

    @GetMapping(value = ["account/{account}"])
    fun userInfoByAccount(@PathVariable account: String): ResponseData<UserEntity> {
        val user = userService.getUserEntityByAccount(account)
        return ResponseData(data = user, message = if (user == null) "账号不存在" else "success")
    }

    @GetMapping(value = ["info/{uid}"])
    fun userInfoByUid(@PathVariable uid: String): ResponseData<UserEntity> {
        val userEntity = userService.getUserEntityByUid(uid)
        return ResponseData(data = userEntity, message = if (userEntity == null) "用户不存在" else "success")
    }

}