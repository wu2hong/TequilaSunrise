#!/bin/bash
set -e

echo "开始部署应用..."

# 1.引用 .env 文件
if [ -f ".env" ]; then
	source .env
	echo ".env 文件导入成功"
else
	echo ".env 文件不存在"
	exit 1
fi

# 2. 清理临时目录，创建其他目录
rm -rf ../temp
mkdir -p ../temp ../logs

# 3. 从GitHub克隆源码
echo "克隆代码库..."
git clone --depth=1 $BUILD_GIT_URL ../temp

# 4. 构建Docker镜像
echo "构建Docker镜像..."
docker compose -f ../docker-compose.yml build

#拷贝配置文件
#cp ../temp/src/$BUILD_MODULE_NAME/target/config/* ../config/

# 5. 运行新容器
echo "启动服务..."
docker compose up -d

# 6. 健康检查
echo "等待服务健康检查..."
sleep 20

if docker compose ps | grep -q "Up"; then
    echo "✅ 服务启动成功"
else
    echo "❌ 服务启动异常，请检查日志"
    docker compose logs java-app
fi

echo "部署完成！"