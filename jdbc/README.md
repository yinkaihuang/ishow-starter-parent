# jdbc模块

已入该模块需要进行下面操作
```
1.配置url，账号及密码
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/teacher?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=123456


2.配置mybatis相关配置
logging.level.cn.ishow.*.*=debug
mybatis-plus.mapper-locations=classpath:/mapper/*Mapper.xml
mybatis-plus.typeAliasesPackage=cn.ishow.teacher.model.po
mybatis-plus.global-config.id-type=0
mybatis-plus.global-config.field-strategy=2
mybatis-plus.global-config.db-column-underline=true
mybatis-plus.global-config.key-generator=com.baomidou.mybatisplus.incrementer.OracleKeyGenerator
mybatis-plus.global-config.logic-delete-value=1
mybatis-plus.global-config.logic-not-delete-value=0
mybatis-plus.configuration.map-underscore-to-camel-case= true
mybatis-plus.configuration.cache-enabled= false
mybatis-plus.configuration.jdbc-type-for-null='null'

```

监控
```
地址：
账号密码是 admin admin

```