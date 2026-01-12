#!/bin/bash
set -e

echo "开始备份应用..."

# 1.配置变量
DATE=$(date +%Y%m%d_%H%M%S)
BACKUP_FILE="backup_$DATE.tar.gz"
echo "$DATE"=$DATE
echo "$BACKUP_FILE"=$BACKUP_FILE

# 2.备份 SQLite 数据库
if [ -f "../data/app.db" ]; then
    echo "备份数据库文件..."
    #sqlite3 ../data/app.db ".backup ../backup/app_$DATE.db"
	cp ../data/app.db ../backup/app_$DATE.db
else
    echo "数据库文件不存在，跳过数据库备份"
fi

# 3.备份日志文件
if [ -d "../logs" ]; then
    echo "打包日志文件..."
	tar --exclude='*.tmp' -czvf ../backup/logs_$DATE.tar.gz -C .. logs/
fi

# 4.创建完整备份包
echo "创建完整备份包..."
tar -czf ../backup/$BACKUP_FILE -C ../backup/ app_$DATE.db logs_$DATE.tar.gz 2>/dev/null || true

# 5.清理临时文件
echo "理临时文件..."
rm -f ../backup/app_$DATE.db ../backup/logs_$DATE.tar.gz

# 6.清理30天前的备份
echo "清理30天前的备份..."
find ../backup/ -name "backup_*.tar.gz" -mtime +30 -delete

echo "备份完成: $BACKUP_DIR/$BACKUP_FILE"