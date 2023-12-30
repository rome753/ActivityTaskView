// Node.js
const express = require('express');
const { spawn } = require('child_process');
const app = express();
const clients = [];

// 提供静态文件
app.use(express.static(__dirname));

// 启动adb logcat
const adb = spawn('adb', ['logcat' , '-s', 'ActivityTaskView.atv']);

adb.stdout.on('data', (data) => {
    // console.log(`stdout: ${data}`);
    // 当有新的日志时，发送到所有SSE客户端
    clients.forEach((res) => {
        res.write(`data: ${data}\n\n`);
    });
});

app.get('/events', (req, res) => {
    // 设置SSE相关的头
    res.setHeader('Content-Type', 'text/event-stream');
    res.setHeader('Cache-Control', 'no-cache');
    res.setHeader('Connection', 'keep-alive');
    res.flushHeaders();

    // 添加到客户端列表
    clients.push(res);

    // 当客户端断开连接时，从列表中移除
    req.on('close', () => {
        clients.splice(clients.indexOf(res), 1);
    });
});

app.listen(3000, () => {
    console.log('Server is running on port 3000');
});