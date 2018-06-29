package com.minxin.base.web.exception;

import com.minxin.base.web.util.AjaxUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

/**
 * Created by todd on 2016/11/12.
 *
 * @author todd
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理filter中的错误
     *
     * @param request 请求
     * @param ex      异常
     * @return 返回值
     */
    @ResponseBody
    @ExceptionHandler
    public String handleException(WebRequest request, Exception ex) {
        if (AjaxUtil.isAjaxRequest(request)) {
            return ex.getMessage();
        }

        return "";
    }

    /**
     * 处理业务异常
     * @param ex 异常
     * @return 处理结果
     */
    @ResponseBody
    @ExceptionHandler
    public String handleBusinessException(BusinessException ex) {
        return ex.getMessage();
    }
}
