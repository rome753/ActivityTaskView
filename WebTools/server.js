// Node.js
const express = require('express');
const { spawn } = require('child_process');
const WebSocket = require('ws');

const app = express();
const wss = new WebSocket.Server({ noServer: true });

// 提供静态文件
app.use(express.static(__dirname));

// 启动adb logcat
const adb = spawn('adb', ['logcat' , '-s', 'ActivityTaskView.atv']);

adb.stdout.on('data', (data) => {
    // console.log(`stdout: ${data}`);
    // 当有新的日志时，发送到所有WebSocket客户端
    wss.clients.forEach((client) => {
        if (client.readyState === WebSocket.OPEN) {
            client.send(`data: ${data}`);
        }
    });
});

const server = app.listen(3000, async () => {
    console.log('Server is running on port 3000');
    const open = (await import('open')).default;
    open('http://localhost:3000');
});

wss.on('connection', ws => {
    console.log('New client connected');
});

server.on('upgrade', (request, socket, head) => {
    wss.handleUpgrade(request, socket, head, ws => {
        wss.emit('connection', ws, request);
    });
});