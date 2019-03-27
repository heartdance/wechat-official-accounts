# wechat
## 环境
1. IntelliJ IDEA 2018.3.2 x64 + lombok插件
2. JAVA 8
3. apache-maven-3.6.0
4. tomcat 9.0.16
5. mysql 8.0.15
## 使用步骤
1. clone或download
2. 修改applicatin.yml文件中的mysql配置
3. 修改TulingConfig.java中的key为你的图灵机器人key
4. 修改WechatConfig.java中的APP_ID、APP_SECRET、TOKEN，从你的微信公众号管理页面获取
## 打包&发布
1. 执行maven打包命令
2. 将生成的wechat.jar上传到你的服务器，请保证80端口未被占用
3. 执行启动命令：java -jar wechat.jar
