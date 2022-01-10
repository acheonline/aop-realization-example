package ru.achernyavskiy0n.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.achernyavskiy0n.exception.SimpleException;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Aspect
public class AopApplicationAspectManager {

    public static final Logger log = LoggerFactory.getLogger(AopApplicationAspectManager.class);

    @Pointcut("this(ru.achernyavskiy0n.service.SimpleService)") //all methods at SimpleService class
    public void simpleServiceMethods() {
    }

    @Pointcut("execution(* *..add*(..))")
    // all methods containing word "add" in name with any parameters at any kind of instance
    public void addMethod() {
    }

    @Pointcut("execution(* *..dummy*(..))")
    // all methods containing word "dummy" in name with any parameters at any kind of instance
    public void dummyMethod() {
    }

    @Pointcut("execution(public * *(..))") // all public methods with any parameters at any kind of instance
    public void publicMethods() {
    }

    @Pointcut("execution(* *..generateException*(..))")
    // all methods containing word "generateException" in name with any parameters at any kind of instance
    public void simpleServiceExceptionMethod() {
    }

    //combinations of aspects
    @Pointcut("simpleServiceMethods() && addMethod()") // 2 aspects include in filter condition
    public void simpleServiceGeneratorMethod() {
    }

    @Pointcut("simpleServiceMethods() && dummyMethod()")
    public void simpleServiceDummyMethod() {
    }

    @Pointcut("simpleServiceMethods() && publicMethods() && !simpleServiceExceptionMethod()")
    // 2 aspects include and 1 exclude in filter condition
    public void simpleServiceBeforeAllPublicMethod() {
    }

    @Before("simpleServiceBeforeAllPublicMethod()")
    //advice for pointcut @simpleServiceBeforeAllPublicMethod() / @Before like advice
    public void logBeforePublicMethodCall(JoinPoint jp) {
        var signatureMethods = jp.getArgs();
        var collect = Arrays.stream(signatureMethods)
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        log.info(createSb(jp, "(@Before advice) calling public method with parameter value: ",
                collect.isEmpty() ? "null" : collect));
    }

    @AfterReturning(value = "simpleServiceGeneratorMethod()", returning = "retVal")
    //advice for pointcut @simpleServiceGeneratorMethod() / @AfterReturning like advice
    public void logMethodCall(JoinPoint jp, Object retVal) {
        log.info(createSb(jp, "(@After advice) Result of adding: ", String.valueOf(retVal)));
    }

    @AfterReturning(value = "simpleServiceDummyMethod()", returning = "dummy")
    public void dummyMethodCall(JoinPoint jp, Object dummy) {
        log.info(createSb(jp, " (@After advice) Result of dummy method: ", (String) dummy));
    }

    @Around("simpleServiceMethods()") //advice for pointcut simpleServiceMethods() / @Around like advice
    public Object measureMethodExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        var start = System.nanoTime();
        var retVal = pjp.proceed();
        var end = System.nanoTime();
        var signature = pjp.getSignature();
        log.info("Execution of method " +
                signature.getDeclaringType().getName() + "." +
                signature.getName() + "()" + " (@Around Aspect) took " +
                TimeUnit.NANOSECONDS.toMillis(end - start) + " ms");
        return retVal;
    }

    @AfterThrowing(value = "simpleServiceExceptionMethod()", throwing = "ex")
    //advice for pointcut @simpleServiceExceptionMethod() / @AfterThrowing like advice for exception handling
    public void logExceptionMethodCall(JoinPoint jp, SimpleException ex) {
        log.error(createSb(jp, "(@After Throwing advice) Logging error: ", ex.getMessage()));
    }

    private String createSb(JoinPoint jp, String funcContextMessage, String pl) {
        return new StringBuilder()
                .append("Execution of ")
                .append(jp.getSignature().getDeclaringType().getName())
                .append(" at method ").append(jp.getSignature().getName()).append("(). ")
                .append(funcContextMessage)
                .append(pl).toString();
    }
}