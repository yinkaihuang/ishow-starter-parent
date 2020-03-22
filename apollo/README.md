# apollo模块接入

```
1.在resource目录下创建META-INF目录，再创建app.properties文件
内容如下：
app.id=YOUR-APP-ID

2.在resource目录下创建apollo-env.properties文件
内容如下：
dev.meta=http://1.1.1.1:8080
fat.meta=http://apollo.fat.xxx.com
uat.meta=http://apollo.uat.xxx.com
pro.meta=http://apollo.xxx.com


3.
对于Windows，文件位置为C:\opt\settings\server.properties
对于Mac/Linux，文件位置为/opt/settings/server.properties
内容如下：
env=DEV

4.支持通过application.properties/bootstrap.properties来配置，该方式能使配置在更早的阶段注入
apollo.bootstrap.enabled = true

5.注入非默认application namespace或多个namespace的配置示例
apollo.bootstrap.namespaces = application,FX.apollo,application.yml
```

改造后
```
1.在resource目录下创建META-INF目录，再创建app.properties文件
内容如下：
app.id=YOUR-APP-ID

2.启动时通过命令指定 spring.profiles.active=dev
```