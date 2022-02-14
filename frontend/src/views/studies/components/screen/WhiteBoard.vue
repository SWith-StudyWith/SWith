<template>
  <div class="whiteboard-wrapper">
    <div id="whiteboard">
        <div class="btn-group" role="group">
            <button type="button" class="btn btn-primary btn" :class="{ 'active': isPointer }" @click="activateTool('pointer')">
                <i class="fas fa-mouse-pointer"></i>
            </button>
            <button type="button" class="btn btn-primary btn" :class="{ 'active': isPen }" @click="activateTool('pen')">
                <i class="fas fa-pen"></i>
            </button>
            <button type="button" class="btn btn-primary btn" :class="{ 'active': isLine }" @click="activateTool('line')">
                <i class="fas fa-slash fa-rotate-90"></i>
            </button>
            <button type="button" class="btn btn-primary btn" :class="{ 'active': isRect }" @click="activateTool('rect')">
                <i class="far fa-square"></i>
            </button>

            <div class="dropdown">
                <button type="button" class="btn btn-primary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                    <i class="fas fa-minus"></i>
                </button>
                <div class="dropdown-menu">
                    <div class="menu-label">Width (0 ~ 10)</div>
                    <input type="range" class="form-range" min="0" max="10" step="1" v-model="current.width" @change="handleChangeWidth">
                </div>
            </div>

            <div class="dropdown">
                <button type="button" class="btn btn-primary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                    <i class="fas fa-tint"></i>
                </button>
                <div class="dropdown-menu">
                    <div class="menu-label">Color</div>
                    <div class="form-check form-check-inline">
                        <input id="black-radio" class="form-check-input" type="radio" value="black" v-model="current.color" @change="handleChangeColor">
                        <div class="color" style="background-color: black"></div>
                    </div>
                    <div class="form-check form-check-inline">
                        <input id="red-radio" class="form-check-input" type="radio" value="red" v-model="current.color" @change="handleChangeColor">
                        <div class="color" style="background-color: red"></div>
                    </div>
                    <div class="form-check form-check-inline">
                        <input id="blue-radio" class="form-check-input" type="radio" value="blue" v-model="current.color" @change="handleChangeColor">
                        <div class="color" style="background-color: blue"></div>
                    </div>
                    <div class="form-check form-check-inline">
                        <input id="green-radio" class="form-check-input" type="radio" value="green" v-model="current.color" @change="handleChangeColor">
                        <div class="color" style="background-color: green"></div>
                    </div>
                    <div class="form-check form-check-inline">
                        <input id="yellow-radio" class="form-check-input" type="radio" value="yellow" v-model="current.color" @change="handleChangeColor">
                        <div class="color" style="background-color: yellow"></div>
                    </div>
                </div>
            </div>
            <button type="button" class="btn btn-primary btn" @click="undo">
                <i class="fas fa-undo"></i>
            </button>
            <button type="button" class="btn btn-primary btn" @click="redo">
                <i class="fas fa-redo"></i>
            </button>
            <button type="button" class="btn btn-primary btn" @click="clear">
                <i class="fas fa-trash-alt"></i>
            </button>
        </div>
        <canvas id="canvas">
        </canvas>
    </div>
  </div>
</template>

<script>
import { fabric } from "fabric";
import io from "socket.io-client";

export default {
    data() {
        return {
            sendSocket: null,
            receiveSocket: null,
            canvas: null,
            context: null,
            isPointer: false,
            isPen: true,
            isLine: false,
            isRect: false,
            isMouseDown: false,
            line: null,
            rect: null,
            startCoord: {
                x: null,
                y: null
            },
            current: {
                width: 2,
                color: 'black'
            },
            snapshotIndex: -1,
            snapshotList: [],
            studyId: this.$route.params.studyId
        }
    },

    mounted() {
        // fabric canvas init
        this.canvas = new fabric.Canvas('canvas', { isDrawingMode: true });
        this.context = this.canvas.getContext('2d');
        window.addEventListener('resize', this.onResize, false);
        this.onResize();
        this.canvas.selectionFullyContained = false;
        this.handleChangeWidth();
        this.handleChangeColor();
        this.pushSnapshot();

        // socket.io connection
        this.sendSocket = io.connect(this.urlForSocket(), { secure: true });
        this.receiveSocket = io.connect(this.urlForSocket(), { secure: true });

        this.sendSocket.on('connect', () => {
            console.log("client(send:on) - connect, id: " + this.sendSocket.id + ", connected: " + this.sendSocket.connected);
            console.log(this.sendSocket);
            // join study
            this.sendSocket.emit('join',
                this.studyId
            );
        });

        this.receiveSocket.on('connect', () => {
            console.log("client(receive:on) - connect, id: " + this.receiveSocket.id + ", connected: " + this.receiveSocket.connected);
            console.log(this.receiveSocket);
            // join study
            this.receiveSocket.emit('join',
                this.studyId
            );
        });

        // canvas init
        this.receiveSocket.on('send-data', (data) => {
            console.log("client(receive:on) - send-data");
            this.receiveCanvas(data);
        });

        // event init
        this.listenToCanvasEvents();
    },

    beforeUnmount() {
        this.sendSocket.disconnect();
        this.receiveSocket.disconnect();
        console.log("client - disconnect, id: " + this.sendSocket.id + ", connected: " + this.sendSocket.connected);
        console.log("client - disconnect, id: " + this.receiveSocket.id + ", connected: " + this.receiveSocket.connected);
    },

    methods: {

        // tool selection
        activateTool(tool) {
            this.isPointer = false;
            this.isPen = false;
            this.isLine = false;
            this.isRect = false;
            this.canvas.selection = (tool === 'pointer');
            this.toggleObjectsSelectable(tool === 'pointer');
            this.canvas.isDrawingMode = (tool === 'pen');
            switch (tool) {
                case 'pointer':
                    this.isPointer = true;
                    break;
                case 'pen':
                    this.isPen = true;
                    break;
                case 'line':
                    this.isLine = true;
                    break;
                case 'rect':
                    this.isRect = true;
                    break;
            }
        },

        toggleObjectsSelectable(selectable) {
            // console.log(this.canvas);
            this.canvas.discardActiveObject();
            this.canvas.forEachObject((obj) => {
                // console.log(obj);
                // console.log(selectable);
                obj.selectable = selectable;
                obj.evented = selectable;
            });
            this.canvas.renderAll();
        },

        // return server URL
        urlForSocket() {
            // const protocol = 'http://';
            // const hostName = window.location.hostname;
            // const hostPort = 3000;
            // console.log(protocol + hostName + `:${hostPort}`);
            // return protocol + hostName + `:${hostPort}`;
            return 'https://i6a501.p.ssafy.io';
            // return 'http://localhost:3000';
        },

        // send canvas data to server
        sendCanvas() {
            // console.log("send");
            const canvasAsJSON = this.canvas.toJSON();
            this.sendSocket.emit('send-data', {
                studyId: this.studyId,
                canvas: canvasAsJSON
            });
            // console.log("send-data - studyId: " + this.studyId);
        },

        // receive canvas data from server
        receiveCanvas(data) {
            // console.log("recieveCanvas - studyId: " + data.studyId);
            if (!!data  && !!data.canvas) {
                this.canvas.loadFromJSON(data.canvas, this.canvas.renderAll.bind(this.canvas));
                this.toggleObjectsSelectable(this.isPointer);
            }
        },

        // listen to canvas events
        listenToCanvasEvents() {
            this.listenToMouseDown();
            this.listenToMouseMove();
            this.listenToMouseUp();
            this.listenToObjectModified();
            //this.listenToObjectScaling();
        },

        listenToMouseDown() {
            this.canvas.on('mouse:down', (event) => {
                // console.log("mouse down");
                this.isMouseDown = true;
                if (this.isPen) { return }

                // line, rect draw
                if (this.isLine) {
                    const pointer = this.canvas.getPointer(event.e);
                    const points = [ pointer.x, pointer.y, pointer.x, pointer.y ];
                    this.line = new fabric.Line(points, {
                        strokeWidth: Number(this.current.width),
                        stroke: this.current.color,
                        selectable: false,
                        evented: false
                    });
                    this.canvas.add(this.line);
                } else if (this.isRect) {
                    const pointer = this.canvas.getPointer(event.e);
                    this.startCoord.x = pointer.x;
                    this.startCoord.y = pointer.y;
                    this.rect = new fabric.Rect(
                        {
                            left: pointer.x,
                            top: pointer.y,
                            height: 0,
                            width: 0,
                            fill: 'transparent',
                            stroke: this.current.color,
                            strokeWidth: Number(this.current.width),
                            borderScaleFactor: 1,
                            selectable: false,
                            evented: false
                        }
                    );
                    this.canvas.add(this.rect);
                }
                this.canvas.renderAll();
            });
        },

        listenToMouseMove() {
            this.canvas.on('mouse:move', (event) => {
                if (!this.isMouseDown) { return; }

                // line, rect end point set and re-render
                if (this.isLine) {
                    const pointer = this.canvas.getPointer(event.e);
                    this.line.set({ x2: pointer.x, y2: pointer.y });
                    this.line.setCoords();
                } else if (this.isRect) {
                    const pointer = this.canvas.getPointer(event.e);
                    this.rect.set({ left: Math.min(this.startCoord.x , pointer.x), top: Math.min(this.startCoord.y, pointer.y) });
                    this.rect.set({ height: Math.abs(pointer.y - this.startCoord.y), width: Math.abs(pointer.x - this.startCoord.x) });
                    this.rect.setCoords();
                }
                this.canvas.renderAll();
            });
        },

        listenToMouseUp() {
            this.canvas.on('mouse:up', () => {
                this.isMouseDown = false;

                if (this.isPointer) { return; }
                this.pushSnapshot();
                this.sendCanvas();
            });
        },

        listenToObjectModified() {
            this.canvas.on('object:modified', () => {
            this.pushSnapshot();
            this.sendCanvas();
            });
        },

        // listenToObjectScaling() {
        //     this.canvas.on('object:scaling', (e) => {
        //         var o = e.target;
        //         if (!o.strokeWidthUnscaled && o.strokeWidth) {
        //             o.strokeWidthUnscaled = o.strokeWidth;
        //         }
        //         if (o.strokeWidthUnscaled) {
        //             o.strokeWidth = o.strokeWidthUnscaled / o.scaleX;
        //         }
        //     });
        // },

        handleChangeWidth() {
            this.canvas.freeDrawingBrush.width = Number(this.current.width);
        },

        handleChangeColor() {
        const brush = this.canvas.freeDrawingBrush;
        brush.color = this.current.color;
            if (brush.getPatternSrc) {
                brush.source = brush.getPatternSrc.call(brush);
            }
        },

        pushSnapshot() {
            this.snapshotIndex++;
            if (this.snapshotList.length !== this.snapshotIndex) {
                this.snapshotList = this.snapshotList.splice(0, this.snapshotIndex);
            }
            // console.log('pushSnapshot');
            this.snapshotList.push(JSON.stringify(this.canvas.toJSON()));
        },

        undo() {
            if (this.snapshotIndex !== 0) {
                // console.log("undo");
                // console.log(this.snapshotList);
                this.snapshotIndex--;
                this.canvas.loadFromJSON(
                    JSON.parse(this.snapshotList[this.snapshotIndex]),
                    this.canvas.renderAll.bind(this.canvas)
                );
                this.toggleObjectsSelectable(this.isPointer);
            }
            this.sendCanvas();
        },

        redo() {
            if (this.snapshotIndex < this.snapshotList.length - 1) {
                // console.log("redo");
                // console.log(this.snapshotList);
                this.snapshotIndex++;
                this.canvas.loadFromJSON(
                    JSON.parse(this.snapshotList[this.snapshotIndex]),
                    this.canvas.renderAll.bind(this.canvas)
                );
                this.toggleObjectsSelectable(this.isPointer);
            }
            this.sendCanvas();
        },

        clear() {
            // console.log("clear");
            this.canvas.clear();
            this.pushSnapshot();
            this.sendCanvas();
        },

        onResize() {
            var whiteboard = document.getElementById('canvas').parentElement.parentElement;
            this.canvas.setWidth(whiteboard.offsetWidth);
            this.canvas.setHeight(whiteboard.offsetHeight);
        }
    }
}
</script>

<style scoped>
.whiteboard-wrapper {
  display: flex;
  justify-content: center;
  height: 72vh;
}
#whiteboard {
    position: relative;
    width: 76vw;
    height: 72vh;
}

.menu-label {
    text-align: center;
}

#canvas {
    position: absolute;
    display: block;
    width: 100%;
    height: auto;
    background-color: rgb(200, 214, 226);
    background-color: #ffffff;
    border-radius: 0.8rem;
}


.btn-group .dropdown{
    display: inline-block;
}

.btn-group {
    position: absolute;
    z-index: 1;
    left: 50%;
    transform: translate(-50%, 0%);
}

.color {
    display: inline-block;
    height: 12px;
    width: 12px;
}

.active{
    background-color: #1E304F;
}
</style>
