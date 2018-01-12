package cn.eusunpower.model

import cn.eusunpower.support.IError

data class ResponseData<T>(
        var code: Int = IError.CODE_SUCCESS,
        var data: T? = null,
        var message: String = IError.MSG_SUCCESS
)