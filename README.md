用 spring-boot 實作的 REST API  
web server 使用非同步呼叫與後台的 socket server 溝通  

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
可以執行 GET / POST / PUT / DELETE   等指令 
每個指令會以非同步的方式同時發送兩個指令(country & address)給後台 socket server  
socket server 收到每個指令後啟動 thread 處理， sleep 一分鐘以後傳結果給 RESTful  server  
藉此測試非同步方式呼叫不會 block 
