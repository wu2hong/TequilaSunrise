# dawn项目

The first personal project involves both testing new technologies and implementing the functions of a startup project.  

[![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk)](https://openjdk.org/)
[![Spring Boot Version](https://img.shields.io/badge/Spring%20Boot-3.2.12-brightgreen.svg)](https://spring.io/)
[![GraalVM](https://img.shields.io/badge/GraalVM-Native%20Image-FF6C37?logo=graalvm)](https://www.graalvm.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

## 目录结构

TequilaSunrise/  
├──database/  #数据库文件和初始化脚本  
├──src/  #java源代码  
&nbsp;&nbsp;├── dawn-http-service/  #web接口层模块(可执行程序)  
&nbsp;&nbsp;&nbsp;&nbsp;├── docker/  #构建容器相关文件  
&nbsp;&nbsp;├── dawn-business-service/  #业务层模块  
&nbsp;&nbsp;└── dawn-data-access/  #数据访问层模块  

## 环境要求

- JAVA 21+