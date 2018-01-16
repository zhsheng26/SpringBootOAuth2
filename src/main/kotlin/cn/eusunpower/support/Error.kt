package cn.eusunpower.support

import org.apache.log4j.Logger
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest


object IError {
    //错误码
    val CODE_SUCCESS = 1000
    val CODE_FAIL = -1
    val CODE_USER_NOT_FOUND = 10001
    val CODE_USER_INFO_NOT_NULL = 10002
    val CODE_LOGIN_ERROR = 10003
    val CODE_USER_NOT_MATCH = 10004
    val CODE_NOTIFICATION_NOT_FOUND = 10005
    val CODE_EVENT_NOT_FOUND = 10006
    val CODE_COMMENT_NOT_FOUND = 10007
    val CODE_FRIEND_IS_FOLLOW = 10008
    val CODE_FRIEND_NOT_FOLLOW = 10009
    val CODE_ERROR_OPTION = 10010
    val CODE_FILE_NOT_NULL = 10011
    val CODE_FILE_NOT_MATCH = 10012
    val CODE_FILE_UPLOAD_ERROR = 10013
    val CODE_FILE_SIZE_ERROR = 10014
    val CODE_SERVER_ERROR = 10015
    val CODE_TOKEN_ERROR = 10016
    val CODE_USER_REGISTER_ERROR = 10017
    val CODE_USER_PASSWORD_SHORT_ERROR = 10018
    val CODE_NUMBER_FORMAT_ERROR = 10019
    val CODE_COMMENT_EVENT_ERROR = 10020
    val CODE_ARGUMENT_NOT_NULL = 10021
    val CODE_POST_NOT_FOUND = 10022
    //错误消息
    val MSG_SUCCESS = "请求成功"
    val MSG_FAIL = "请求失败"
    val MSG_USER_NOT_FOUND = "找不到此用户."
    val MSG_USER_INFO_NOT_NULL = "用户信息不能为空."
    val MSG_LOGIN_ERROR = "登录失败,请检查账号密码."
    val MSG_USER_NOT_MATCH = "用户信息不匹配."
    val MSG_NOTIFICATION_NOT_FOUND = "该消息提醒不存在."
    val MSG_EVENT_NOT_FOUND = "找不到此事件."
    val MSG_COMMENT_NOT_FOUND = "找不到此评论."
    val MSG_FRIEND_IS_FOLLOW = "已关注此用户."
    val MSG_FRIEND_NOT_FOLLOW = "未关注此用户."
    val MSG_ERROR_OPTION = "此操作不允许."
    val MSG_FILE_NOT_NULL = "文件不能为空."
    val MSG_FILE_NOT_MATCH = "文件类型不匹配(只支持jpg,png,jpeg)."
    val MSG_NUMBER_FORMAT_EXCEPTION = "参数类型不匹配请检查后重试"
    val MSG_COMMENT_EVENT_ERROR = "评论事件不一致,不能发表回复"
    val MSG_ARGUMENT_NOT_NULL = "参数不能给为空"
    val MSG_POST_NOT_FOUND = "找不到此动态"
    //连接超时
    val MSG_FILE_UPLOAD_ERROR = "文件上传失败(请检查网络连接后重试)."
    val MSG_FILE_SIZE_ERROR = "单个文件大小不能超过2MB(多个文件总大小不能超过20MB)."
    val MSG_SERVER_ERROR = "服务出错."
    val MSG_TOKEN_ERROR = "验证失败,请重新登录后执行此操作."
    val MSG_USER_REGISTER_ERROR = "用户名已被注册,请更换用户名后重新注册."
    val MSG_USER_PASSWORD_SHORT_ERROR = "用户密码长度不能小于8位"
}

data class IException(val code: Int = IError.CODE_FAIL, override val message: String = IError.MSG_FAIL) : Exception()

data class IErrorResponse<out T>(val code: Int = IError.CODE_FAIL, val data: T? = null, val message: String = IError.MSG_FAIL, val url: String = "")

@ControllerAdvice
class GlobalExceptionHandler {

    private val logger = Logger.getLogger("EXCEPTION")

    @ExceptionHandler(value = [IException::class])
    @ResponseBody
    fun jsonErrorHandler(e: IException, request: HttpServletRequest): IErrorResponse<Nothing> {
        logger.error("request : ${request.requestURI} , happen exception : ${e.message}")
        return IErrorResponse(e.code, null, e.message, url = request.requestURI.toString())
    }

    @ExceptionHandler(value = [AuthenticationException::class])
    @ResponseBody
    fun error404(ex: Exception, request: HttpServletRequest): IErrorResponse<Boolean> {
        logger.error("happen NullPointerException : " +
                "url = ${request.requestURI} , " +
                "message = ${ex.message}")
        return IErrorResponse(message = "sorry！resource not fund", url = request.requestURI.toString())
    }


}