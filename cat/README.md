# cat模块接入

```
1.在resources的META-INF目录下创建app.properties
app.name=项目名称

2.在项目运行的磁盘目录下创建 data/appdatas/cat文件目录
再创建在该目录下创建client.xml文件
<?xml version="1.0" encoding="utf-8"?>
<config mode="client" xmlns:xsi="http://www.w3.org/2001/XMLSchema" xsi:noNamespaceSchemaLocation="config.xsd">
	<servers>
		<!-- Local mode for development -->
		<server ip="127.0.0.1" port="2280" http-port="8080" />
	</servers>
</config>


```