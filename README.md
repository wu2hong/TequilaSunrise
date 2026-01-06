# TequilaSunrise
The first personal project involves both testing new technologies and implementing the functions of a startup project.




# 本地测试模式（默认）
mvn clean package -Pdefault
java -jar dawn-web/target/dawn-web-1.0.0-SNAPSHOT.jar

# AOT原生镜像构建
mvn clean -pl dawn-web -am spring-boot:build-image -Pnative

# 运行原生镜像
docker run --rm -p 8080:8080 -v /host/data:/app/data dawn-project:1.0.0-SNAPSHOT