#!/bin/sh
# 将容器内的挂载点（如./logs）所有者改为appuser
#chown -R appuser:appuser ./logs

#启动java应用
exec java $JAVA_OPTS -Dlogging.path=/app/logs -jar app.jar