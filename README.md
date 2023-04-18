# jenkins-python-dockerfile

## 这个dockerfile的镜像里有什么？
1、 jenkins
2、python3.8

## jenkins里配置哪些东西
1、设置了admin用户的默认密码admin
2、配置了环境变量
3、配置了allure报告
4、配置了邮件模板
5、创建了默认任务，该任务定时执行、生成报告、发送邮件

## 如果想要直接跑起来，需要改哪些东西
/init-groovys/credentials_password.groovy文件中EMAIL_NAME和PASSWORD换成实际的邮箱和授权码

## groovy脚本代码环境搭建
1、下载jenkins源码：[jenkins源码](https://github.com/jenkinsci/docker/blob/master/README.md)
2、修改pom.xml，增加插件maven源
``` xml
    <dependency>
      <groupId>ru.yandex.qatools.allure</groupId>
      <artifactId>allure-jenkins-plugin</artifactId>
      <version>3.11.0</version>
    </dependency
```
3、添加要调试的插件包
比如：
``` xml
    <dependency>
      <groupId>ru.yandex.qatools.allure</groupId>
      <artifactId>allure-jenkins-plugin</artifactId>
      <version>3.11.0</version>
    </dependency>
    <dependency>
       <groupId>org.jenkinsci.plugins</groupId>
       <artifactId>emailext-template</artifactId>
       <version>1.5</version>
    </dependency>
```
调试：可以在jenkins的控制台中执行
