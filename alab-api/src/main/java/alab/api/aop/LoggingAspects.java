package alab.api.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspects {

    @Before("within(alab.api.app.controller.*)")
    public void controllerStartLog(JoinPoint joinPoint) {
        String string = joinPoint.toString();
        String args = Arrays.toString(joinPoint.getArgs());

        log.info("Start {}, args: {}", string, args);
    }

    @AfterReturning("within(alab.api.app.controller.*)")
    public void controllerEndLog(JoinPoint joinPoint) {
        String string = joinPoint.toString();
        String args = Arrays.toString(joinPoint.getArgs());

        log.info("End {}, args: {}", string, args);
    }

    @AfterThrowing(value = "within(alab.api.app.controller.*)", throwing = "e")
    public void afterException(JoinPoint joinPoint, Exception e) {
        String string = joinPoint.toString();
        String args = Arrays.toString(joinPoint.getArgs());

        log.error("Error!, Exception: {}, {}, args = {}", e.getMessage(), string, args);
    }
}
