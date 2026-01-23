# dawn-web模块

## 运行指南

### 本地包模式（profile:default=JIT包）

```
./mvnw clean package -DskipTests -pl dawn-web -am -Pdefault
java -jar dawn-web/target/dawn-web-1.0.0-SNAPSHOT.jar
```

### 线上镜像模式（profile:default=JIT包，native=AOT包）

```
复制目录docker/到线上，复制根目录database/的数据库文件到线上，并设置相应的.env文件
赋予脚本执行权限：chmod +x scripts/*.sh
编译发布脚本：./deploy.sh
备份脚本：./backup.sh
更新脚本：./update.sh
```
