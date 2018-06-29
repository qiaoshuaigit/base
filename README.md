Base
---------

base项目用来做web工程的基础框架。定义了shiro+cas、redis缓存。

项目采用maven约定的管理结构。

项目的父项目是minxin-root，同时在root中管理依赖，在base中只负责引入依赖，不需要关注依赖的版本。

使用说明：
--------
1. 在web工程的pom.xml中，增加minxin-base的依赖，需要有具体的版本；
    ```
    <dependency>
        <groupId>com.minxin</groupId>
        <artifactId>minxin-base</artifactId>
        <version>${minxin-base.version}</version>
    </dependency>
    ```
2. web工程在root-context.xml中，引入root-context-base.xml
    ```
    <import resource="classpath*:/root-context-base.xml"/>
    ```
3. 在web.xml中增加以下配置：SingleSignOutHttpSessionListener、handleSingleSignOutFilter、shiroFilter
    请参考 http://10.10.24.52/minxin/minxin-passport/blob/dev/src/main/webapp/WEB-INF/web.xml 的配置
    ```
        <!-- handle CAS logout requests-->
        <listener>
            <listener-class>org.jasig.cas.client.session.SingleSignOutHttpSessionListener</listener-class>
        </listener>
    
        <!-- handle single sign out -->
        <filter>
            <filter-name>handleSingleSignOutFilter</filter-name>
            <filter-class>com.minxin.base.web.filter.HandleSingleSignOutFilter</filter-class>
        </filter>
        <filter-mapping>
            <filter-name>handleSingleSignOutFilter</filter-name>
            <url-pattern>/*</url-pattern>
        </filter-mapping>
    
        <!-- The filter-name matches name of a 'shiroFilter' bean inside applicationContext.xml -->
        <filter>
            <filter-name>shiroFilter</filter-name>
            <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
            <init-param>
                <param-name>targetFilterLifecycle</param-name>
                <param-value>true</param-value>
            </init-param>
        </filter>
        <!-- catches all requests to Shiro is filtered. Usually this filter mapping is defined first  -->
        <filter-mapping>
            <filter-name>shiroFilter</filter-name>
            <url-pattern>/*</url-pattern>
        </filter-mapping>
    ```
4. 在web工程的resources目录中增加auth.properties配置文件，添加以下配置：
    ```
        #sso地址，用于登录控制
        sso.service.urlPrefix=http://10.10.21.71:8080/sso
        #passport地址，用于权限、控件权限获取
        passport.service.url=http://10.10.21.71:8080/minxin-passport
        
        #本地应用路径
        #local.app.url 本地应用在tomcat中的上下文路径
        local.app.url=http://10.10.95.86:8080
        
        #session超时时间，毫秒
        global.session.timtout=3600000
        #shiro定时清理过期session的时间间隔，毫秒
        session.validation.interval=1800000
        
        #此url对应的请求，不经过sso过滤，通常将第三方请求的服务放在此处
        pathToExclude=/serve/**
    ```
    配置文件中，同一个地址只保留一个根路径，其余路径使用根路径在xml中配置，不作为单独的配置项，如：
    ```
         local.app.url=http://localhost:8080/
         local.app.callbackurl=http://localhost:8080/callback
    ```
    只保留 local.app.url 一项，原 local.app.callbackurl 在xml中以 ${local.app.url}/callback 的方式存在。
    请参考 http://10.10.24.52/minxin/minxin-passport/tree/dev/src/main/resources 中两个文件的配置
    在auth.properties 中 
    ```
        pathToExclude=/serve/**
    ```
    此url对应的请求，不经过sso过滤，通常将第三方请求的服务放在此处
   
   在web工程的resources目录中增加cache.properties配置文件，添加以下配置：
   ```
      redis.pool.config.maxTotal=10
      
      redis.pool.config.maxIdle=2
      redis.pool.config.maxWaitMillis=1000
      redis.pool.config.testOnBorrow=true
      redis.pool.config.testOnReturn=true
      
      #======= redis config =======#
      redis.pool.main.host=10.10.23.71
      redis.pool.main.port=6379
      redis.pool.main.timeout=1000
      redis.pool.main.password=10
   ```
    
Note:
----------
1. 目前项目与sso的配置采用的是shiro+pac4j。
2. 工程中采用修改auth.properties、cache.properties配置文件名称的方式，避免引用的工程中，配置文件失效的问题，应该改为maven profile的方式。

代码提交说明:
-----------
版本管理为git，可以参考.gitignore，增加文件后缀的方式，忽略要提交的文件，例如target文件夹、.class文件等，开发工具的相关文件如.eclipse等也可以增加到文件中，以作忽略处理。

master为稳定分支，dev为开发分支，代码提交到dev分支中。