package com.mao.demo.common.exception;

import com.mao.demo.common.annotation.ExceptionCode;
import com.mao.demo.common.response.ResponseCode;
import com.mao.demo.common.response.ResponseResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.util.Objects;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    /**
     *
     * @param bindException 表单校验异常
     * @return 统一响应结果
     */
    @ExceptionHandler(value = BindException.class)
    public ResponseResult<String> errorHandler(BindException bindException) {
        BindingResult bindingResult = bindException.getBindingResult();
        String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();

        return new ResponseResult<>(ResponseCode.VALIDATE_FAILED, defaultMessage);
    }

    /**
     *
     * @param e 自定义异常
     * @return 统一响应结果
     */
    @ExceptionHandler(APIException.class)
    public ResponseResult<String> APIExceptionHandler(APIException e) {
        return new ResponseResult<>(ResponseCode.FAILED, e.getMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<String> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) throws NoSuchFieldException {
        // 从异常对象中拿到错误信息
        String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        // 参数的Class对象，等下好通过字段名称获取Field对象
        Class<?> parameterType = e.getParameter().getParameterType();
        // 拿到错误的字段名称
        String fieldName = Objects.requireNonNull(e.getBindingResult().getFieldError()).getField();
        Field field = parameterType.getDeclaredField(fieldName);
        // 获取Field对象上的自定义注解
        ExceptionCode annotation = field.getAnnotation(ExceptionCode.class);

        // 有注解的话就返回注解的响应信息
        if (annotation != null) {
            return new ResponseResult<>(annotation.value(), annotation.message(), defaultMessage);
        }

        // 没有注解就提取错误提示信息进行返回统一错误码
        return new ResponseResult<>(ResponseCode.UNPROCESSABLE_ENTITY, defaultMessage);
    }
}
