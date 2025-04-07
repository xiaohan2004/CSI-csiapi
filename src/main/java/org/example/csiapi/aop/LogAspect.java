package org.example.csiapi.aop;

import com.alibaba.fastjson.JSONObject;
import org.example.csiapi.mapper.OperateLogMapper;
import org.example.csiapi.pojo.OperateLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Component
@Aspect //切面类
public class LogAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Around("@annotation(org.example.csiapi.annotation.RecordLog)")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取目标方法的名字
        String methodName = joinPoint.getSignature().getName();

        //操作时间
        LocalDateTime operateTime = LocalDateTime.now();

        //操作类名
        String className = joinPoint.getTarget().getClass().getName();

        //操作方法参数
        Object[] args = joinPoint.getArgs();
        String methodParams = Arrays.toString(args);

        long begin = System.currentTimeMillis();
        //调用原始目标方法运行
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        //方法返回值
        String returnValue = JSONObject.toJSONString(result);

        //操作耗时
        Long costTime = end - begin;


        //记录操作日志
        OperateLog operateLog = new OperateLog(null, operateTime, className, methodName, methodParams, returnValue, costTime);
        operateLogMapper.insertOperateLog(operateLog);

        log.info("AOP记录操作日志: {}", operateLog);

        return result;
    }

}
