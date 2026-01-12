# TequilaSunrise
The first personal project involves both testing new technologies and implementing the functions of a startup project.

# dawn-web模块
## 本地经典测试模式（默认）
mvn clean package -Pdefault
java -jar dawn-web/target/dawn-web-1.0.0-SNAPSHOT.jar --spring.datasource.url="jdbc:sqlite:./database/dawn.db"

## 线上经典镜像模式（默认）
复制dawn-web/docker/目录到线上/opt/dawn-web/
复制database中的数据库文件到线上，并设置相应的.env文件
赋予脚本执行权限：chmod +x /opt/dawn-web/scripts/*.sh
执行部署：./opt/dawn-web/scripts/deploy.sh


## AOT原生镜像构建
mvn clean -pl dawn-web -am spring-boot:build-image -Pnative

## 运行原生镜像
docker run --rm -p 8080:8080 -v /host/data:/app/data dawn-project:1.0.0-SNAPSHOT