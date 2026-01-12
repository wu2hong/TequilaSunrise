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
echo "执行备份================="
./backup.sh

# 3.停止服务
echo "停止服务================="
docker-compose down

# 4.执行编译发布
echo "编译发布================="
./deploy.sh

echo "服务更新完成================="