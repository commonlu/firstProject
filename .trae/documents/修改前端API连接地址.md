# 修改前端API连接地址

## 任务概述
将前端项目中的API连接地址从`http://localhost:8080`修改为后端服务器的公网IP地址`http://47.107.75.215:8080`，确保前端能够正常连接后端服务。

## 实现步骤

### 1. 修改API配置文件
- 文件路径：`d:\Downloads\workspace-java\vueFile\hr-frontend\src\main.js`
- 修改行：第25行
- 将`axios.defaults.baseURL = 'http://localhost:8080'`修改为`axios.defaults.baseURL = 'http://47.107.75.215:8080'`

### 2. 保存修改
- 保存对main.js文件的修改

### 3. 测试前端连接
- 启动前端开发服务器
- 访问前端页面，测试是否能正常连接后端
- 检查浏览器控制台，确认API请求是否成功

## 技术要点
- 使用Vue 3的组合式API
- Axios全局配置
- 跨域资源共享（CORS）
- 前后端分离架构

## 预期效果
- 前端页面能够正常加载
- API请求能够成功发送到后端服务器
- 能够获取到后端返回的数据
- 没有跨域错误

## 注意事项
- 确保后端已经配置了正确的CORS
- 确保后端服务正在运行在8080端口
- 确保公网IP地址正确
- 测试完成后可以构建生产版本

## 具体修改内容
```javascript
// 原代码
axios.defaults.baseURL = 'http://localhost:8080' // 你的Spring Boot后端地址

// 修改后的代码
axios.defaults.baseURL = 'http://47.107.75.215:8080' // 你的Spring Boot后端地址
```

## 测试方法
1. 启动前端开发服务器：`npm run serve`
2. 访问前端页面，通常为`http://localhost:8081`
3. 打开浏览器开发者工具，检查Network标签
4. 查看API请求是否成功，返回200状态码
5. 检查控制台是否有错误信息

通过这个修改，前端将能够连接到部署在公网服务器上的后端服务，实现前后端的正常通信。