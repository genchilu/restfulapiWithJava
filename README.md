用 spring-boot 實作的 REST API  
web server 使用非同步呼叫對後台的 socket server 溝通  

啟動 web
```
cd restfulWeb/
gradle build
java -jar build/libs/webServer-1.0.jar
```

啟動 socket
```
cd socketApp
gradle build
java -jar build/libs/socketApp-1.0.jar
```

打開瀏覽器輸入  
http://127.0.0.1:8080
