const express = require('express');
const cors = require('cors');
const app = express();
app.use(cors());
const http = require('http').Server(app);
const io = require('socket.io')(http, {cors: { origin: '*' }});
const port = process.env.PORT || 3000;

// app.use(express.static(__dirname + '/public'));
var dataMap = {}

io.on('connection', (socket) => {

    // 연결 확인 event
    io.emit('got-connected');

    // studyId를 통해 room 구별
    socket.on('join', (studyId) => {
        socket.join(studyId);
        if (studyId in dataMap) {
            socket.emit('send-data', dataMap[studyId]);
        }
    });

    // 나머지 clients에게 broadcast
    socket.on('send-data', (data) => {
        socket.broadcast.to(data.studyId).emit('send-data', data);
        dataMap[data.studyId] = data;
    });
});

http.listen(port, () => console.log('listening on port ' + port));