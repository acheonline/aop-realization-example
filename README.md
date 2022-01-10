# AOP use cases example project


_This project takes overview of some most useful concepts of AOP Spring.
Such as Advices, PointCuts, key core concepts of AOP and business use cases of them._

### **For local execution customize jdk image at Dockerfile and SERVER_PORT**

    mvn clean install
    at Dockerfile: fix "FROM amazoncorretto:17" according to your JAVA_HOME jdk
    docker build -t aop-example-image .
    docker run -p 8081:8081 aop-example-image --SERVER_PORT=8081


If you want to customize your request route, you should fix last command like `docker run -p localhostPort:innerContainerPort aop-example-image --SERVER_PORT=innerContainerPort`, for example: `docker run -p 8088:8081 aop-example-image --SERVER_PORT=8081`.


Thus all curl request should be modyfied like this: `docker curl -I localhost:localhostPort/aop/add`, for example: `curl -I localhost:8088/aop/add`

### **After executing docker please try REST Controller requests for execute AOP functionality**



| API call                                                              | Description of API call                                                                                                                                                                                                | 
|-----------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| curl -I localhost:${SERVER_PORT}/aop/add                              | Calculate sum of 2 random digits in range of 1..100. Execute @Before advice for null arg method, @Around advice for time execution estimate and @AfterReturning advice for processing return value of method execution |
| curl -I localhost:${SERVER_PORT}/aop/param?parameter=(any int number) | Calculate sum of 2 random digits in range of 1..N. Execute @Before advice and get arg value of method and @Around advice for time execution estimate                                                                   |
| curl -I localhost:${SERVER_PORT}/aop/dummy                            | Dummy endpoint, return String. Execute @Before advice for null arg method, @Around advice for time execution estimate and @AfterReturning advice for processing return value of method execution                       |
| curl -I localhost:${SERVER_PORT}/aop/exception                                  | @AfterThrowing advice. Exclude processing @Before advice                                                                                                                                                               |

