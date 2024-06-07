# wechat-official-accounts

微信公众号后端示例代码，集成了图灵机器人

## 环境
1. JAVA 21
2. apache-maven-3.6.0
3. mysql 8.0.15

## 使用步骤
1. 修改src/resources/application.yml.template文件名为application.yml
2. 修改application.yml中的turing.key为你的图灵机器人key
3. 修改application.yml中的wechat.appId、wechat.appSecret、wechat.token，从你的微信公众号管理页面获取

## 打包&启动
1. 执行maven打包命令
2. 将生成的wechat.jar上传到你的服务器，请保证80端口未被占用
3. 执行启动命令：java -jar wechat.jar
