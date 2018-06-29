package com.minxin.base.web.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于错误跳转
 *
 * @author todd
 */
@Controller
public class ErrorController extends BaseController {

    /**
     * 处理异常
     * @param request 请求
     * @return 返回异常到页面
     */
    @RequestMapping(path = "/error", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView handle(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        Map<String, Object> errors = new HashMap<String, Object>();

        errors.put("status", request.getAttribute("javax.servlet.error.status_code"));
        errors.put("reason", request.getAttribute("javax.servlet.error.message"));
        mv.addAllObjects(errors);
        mv.setViewName("error/error");
        return  mv;
    }
}
