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

    console.log("sever(on) - connect, id: " + socket.id + ", connected: " + socket.connected);
    // console.log(socket);

    socket.on("disconnect", (reason) => {
        console.log("sever(on) - disconnect, id: " + socket.id + ", connected: " + socket.connected + ", reason: " + reason);
    });

    // studyId를 통해 room 구별
    socket.on('join', (studyId) => {
        socket.join(studyId);
        if (studyId in dataMap) {
            socket.emit('send-data', dataMap[studyId]);
        }
        console.log("sever(on) - join, id: " + socket.id + ", room: ");
        console.log(socket.rooms);

        console.log("------------------------sockets---------------------------");
        for (const [_, temp] of io.of("/").sockets) {
            console.log("id: " + temp.id + ", room: ");
            console.log(temp.rooms);
        }
        console.log("----------------------------------------------------------");
    });

    // 나머지 clients에게 broadcast
    socket.on('send-data', (data) => {
        console.log("sever(on) - send-data, id: " + socket.id + ", room: ");
        console.log(socket.rooms);
        console.log(data);
        console.log(data.canvas);
        socket.broadcast.to(data.studyId).emit('send-data', data);
        dataMap[data.studyId] = data;
        console.log("server(emit) - send-data");
    });
});

http.listen(port, () => console.log('listening on port ' + port));