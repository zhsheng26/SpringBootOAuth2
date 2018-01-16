package cn.eusunpower.config

import cn.eusunpower.model.entity.UserEntity
import cn.eusunpower.support.Me
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.annotation.Resource

@Component
class CurrentUserResolvers : HandlerMethodArgumentResolver {
    @Resource
    lateinit var userFacade: IAuthenticationFacade

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        if (parameter.parameterType.isAssignableFrom(UserEntity::class.java)
                && parameter.hasParameterAnnotation(Me::class.java)) {
            return true
        }
        return false
    }

    override fun resolveArgument(parameter: MethodParameter?, mavContainer: ModelAndViewContainer?,
                                 webRequest: NativeWebRequest?, binderFactory: WebDataBinderFactory?): Any {
        return userFacade.getCurrentUser()
    }

}