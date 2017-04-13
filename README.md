# 系统需求
## 监视器
如果编译加打包部署需要安装jdk,maven和docker
只是部署镜像只需docker
## 传感器
需要jdk,maven加nmap
# 部署方法
## 监视器
监视器组件在monitor文件夹中
运行mvn clean package docker:build
即可在本地生成docker镜像，springio/topology
生成成功后，运行docker run -d -p {port}:8080 springio/topology即可

## 传感器
传感器的部署分两步，首先要部署ip查询模块，以供下级传感器查询他们的IP.
部署方法是：
1. 在sensor/ip文件夹中，运行mvn spring-boot:run
   将服务运行在8080端口上
2. 在sensor/script文件夹中，运行python get_topology.py {sensorId} {sensor_ip} {sensor_mask} {monitor_ip} {monitor_port} 
   sensorId是这个sensor的标签，唯一
   sensor_ip是传感器的ip地址
   sensor_mask是传感器的子网掩码，十进制，如255.255.255.0为24
   monitor_ip是监视器的ip地址
   monitor_port是监视器的端口号
   
   运行后该脚本，会：
   1. 首先向监视器注册该监视器，通过traceroute查询默认网关。
   2. 再通过向监视器查询网关查询上级传感器的地址，从而查询该传感器对外表现的ip地址。
   3. 接下来查询子网内的所有设备信息。
   4. 将信息汇总传输给监视器。
   


