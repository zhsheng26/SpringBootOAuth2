package cn.eusunpower.config

import org.springframework.http.MediaType
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class Interceptor : HandlerInterceptorAdapter() {
    override fun preHandle(request: HttpServletRequest?, response: HttpServletResponse, handler: Any?): Boolean {
        response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
        return super.preHandle(request, response, handler)
    }
}