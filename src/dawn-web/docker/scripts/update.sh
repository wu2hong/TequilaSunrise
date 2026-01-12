#!/bin/bash
set -e

echo "开始更新应用================="

# 1.引用 .env 文件
if [ -f ".env" ]; then
	source .env
	echo ".env 文件导入成功"
else
	echo ".env 文件不存在"
	exit 1
fi

# 2.执行备份
echo "执行备份..."
./backup.sh

# 3. 清理临时目录，创建其他目录
rm -rf ../temp
mkdir -p ../temp ../logs

# 4. 从GitHub克隆源码
echo "克隆代码库..."
git clone --depth=1 $BUILD_GIT_URL ../temp

# 5. 构建Docker镜像
echo "构建Docker镜像..."
docker compose build

#拷贝配置文件
#cp ../temp/src/$BUILD_MODULE_NAME/target/config/* ../config/

# 6.停止服务
echo "停止服务..."
docker compose down

# 7. 运行新容器
echo "启动服务..."
docker compose up -d

# 8. 健康检查
echo "等待服务健康检查..."
sleep 20

if docker compose ps | grep -q "Up"; then
    echo "✅ 服务启动成功"
else
    echo "❌ 服务启动异常，请检查日志"
    docker compose logs java-app
fi

echo "服务更新完成"