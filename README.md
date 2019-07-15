# mini-spring
手写Spring系列
手动实现一个Spring框架
内容持续更新，详细教程欢迎关注我的博客https://blog.csdn.net/qq_31749835/article/details/90812799

最近学习了一下spring的相关内容，所以也就想要照猫画虎地记录和实现一下spring的框架，通过阅读这些也希望能够消除对Spring框架的恐惧，其实细心阅读框架也很容易理解。
mini-spring尽量实现spring的核心功能，欢迎大佬们移步我的博客为我增加几个访问量！
1. [从头开始实现一个小型spring框架——手写Spring之实现SpringBoot启动](https://blog.csdn.net/qq_31749835/article/details/90812799)
2. [从头开始实现一个小型spring框架——手写Spring之集成Tomcat服务器](https://blog.csdn.net/qq_31749835/article/details/90933527)
3. [从头开始实现一个小型spring框架——控制器controller实现mvc请求拦截和响应](https://blog.csdn.net/qq_31749835/article/details/91038594)
4. [从头开始实现一个小型spring框架——实现Bean管理（IOC与DI）](https://blog.csdn.net/qq_31749835/article/details/91350454)

## 本仓库适合的对象
Mini-Spring适合对Spring框架有一定了解，或使用过一段时间，想要通过源码更进一步提升自己对Spring体系相关源码认知的人。但由于Spring源码体系较为庞大，且源码大部分用于处理多线程和效率优化，因此本仓库试图简化Spring的处理逻辑，以方便各编码者对源码的认知和理解，故仅对核心部分进行了实现。同时也希望能够通过源码更进一步加深自己对源码的理解。

## 开发环境
- 语言：Java8
- IDE：IDEA或Eclipse安装lombok插件
- 依赖管理与项目构建：Gradle

## 功能结构
```
Mimi_Spring
├─framework
│  ├─beans 
│  │  ├─Autowired  注入标记注解
│  │  ├─Bean  bean对象标记注解
│  │  └─BeanFactory  bean工厂
│  ├─context
│  ├─core
│  │  └─ClassScanner 包扫描
│  ├─starter
│  │  └─MiniApplication 框架启动调用类
│  ├─web 页面控制逻辑相关
│  │  ├─handler
│  │  │  ├─HandlerManager
│  │  │  └─MappringHandler 
│  │  ├─mvc
│  │  │  ├─Controller 控制器注解
│  │  │  ├─RequestMapping 请求路径注解
│  │  │  └─RequestParam 请求参数注解
│  │  ├─server
│  │  │  └─TomcatServer 集成web容器
│  │  ├─servlet
│  │  │  └─DispatcherServlet 请求拦截
├─test
│  ├─controller
│  │  └─UserController 用户测试控服务类 
└─ └─Application 项目启动类
   └─ 更多功能尚待完善。。
```


## 说明

项目分为framework和test两个模块，framework负责实现spring的核心功能，并且使用包名对各个功能进行了划分；test模块则负责对framework模块代码的正确性进行验证。
### SpringBoot启动
在博客[从头开始实现一个小型spring框架——手写Spring之实现SpringBoot启动](https://blog.csdn.net/qq_31749835/article/details/90812799) 中我们对SpringBoot的项目启动方式进行了详细的阐述，Application通过调用MiniApplication的入口run方法启动整个程序，并在程序启动成功后输出相应的结果。
项目中framework模块为test模块提供了支撑，test模块则反过来为framework模块提供验证。

### 集成Tomcat服务器
博客[从头开始实现一个小型spring框架——手写Spring之集成Tomcat服务器](https://blog.csdn.net/qq_31749835/article/details/90933527)在第一篇实现SpringBoot启动的基础上进一步将Tomcat容器集成在了我们的mini-spring框架中。
首先对容器处理请求的逻辑进行了简单介绍，分析了tomcat容器对请求的处理和响应，对于内部数据究竟是怎样处理的，web容器并不关心。然后对tomcat的集成做了具体实现，我们新添加了TomcatServer和TestServlet类分别实例化我们的容器和测试我们的Servlet请求拦截，当TestServlet正确匹配到我们请求的/test.json，并成功返回一个test字符串，说明我们的Servelt是有效的。

### 实现mvc请求拦截和响应
文章[从头开始实现一个小型spring框架——控制器controller实现mvc请求拦截和响应](https://blog.csdn.net/qq_31749835/article/details/91038594)对SpringMvc的核心功能进行了实现，通过使用包扫描和反射机制获取到注解的信息，并进行实例化，最终实现请求的拦截和相应。
test模块的Application向framework模块的MiniApplication传递类信息，MiniApplication调用ClassScanner进行包扫描，通过类加载器扫描包中的类路径（其中对jar包的信息做了进一步处理），并返回classList类列表至MiniApplication；进而使用HandlerManagger获取类信息，并通过HandlerManager进行组装。
TomcatServer则更改其拦截的请求uri路径为/，以拦截所有通过tomcat的请求，并转发至DispatcherServlet，DispatcherServlet的service方法进行uri匹配，匹配成功则调用Handler的resolveMappingHandler方法,实例化并返回响应信息。


### Bean管理（IOC与DI）
博客[从头开始实现一个小型spring框架——实现Bean管理（IOC与DI）](https://blog.csdn.net/qq_31749835/article/details/91350454)使用反射的方式对依赖注入进行了实现，增加了Bean注解用于标记一个类是否是需要管理的Bean，Autowired注解用于注解在何处注入实例化后的Bean。以及核心的实现类BeanFactory工厂，对外提供了获取bean的getBean方法和初始化工厂的initBean的方法。同时修改MappingHandler获取Bean的方式为从工厂中获取。
测试中增加了UserService并用@Bean标记，并在UserController中注入，成功完成测试。


## 备注
本框架仅为对Spring核心的一些实现，其中还存在许多的不足和缺陷。且对于数据的持久化并未进行叙述和集成，在多线程并发情况也未进行处理，因此建议
对此源码仅用于理解框架和自我提高。
若有感兴趣的小伙伴，欢迎联系我或是提issue，很高兴能有和大家交流的机会。😄
