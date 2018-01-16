package cn.eusunpower.controller

import cn.eusunpower.model.ResponseData
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class IndexController {

    @RequestMapping("/")
    @ResponseBody
    fun index(): ResponseData<String> {
        return ResponseData(data = "success")
    }

}