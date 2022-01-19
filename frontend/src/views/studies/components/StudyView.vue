<template @resize="onResize">
  <div>
    <canvas class="whiteboard" @mousedown="onMouseDown"
     @mousemove="onMouseMove" @mouseup="onMouseUp"></canvas>

    <div class="colors">
      <div class="color black"></div>
      <div class="color red"></div>
      <div class="color green"></div>
      <div class="color blue"></div>
      <div class="color yellow"></div>
    </div>
  </div>
</template>
<script>
/* eslint-disable */
export default {
  data() {
    return {
      colors: [],
      current: {
        color: 'black',
      },
      drawing: false,
      canvas: null,
      context: null,
    }
  },
  inject: ['$socket'],

  methods: {
    drawLine (x0, y0, x1, y1, color, emit){
      let ctx = this.context;
      ctx.beginPath();
      ctx.strokeStyle = color;
      ctx.lineWidth = 2;
      ctx.moveTo(x0, y0);
      ctx.lineTo(x1, y1);
      ctx.stroke();
      ctx.closePath();

      if (!emit) { return; }
      var w = this.canvas.width;
      var h = this.canvas.height;
      this.$socket.emit('drawing', {
        x0: x0 / w,
        y0: y0 / h,
        x1: x1 / w,
        y1: y1 / h,
        color: color
      });
    },
    onMouseDown(e){
      this.drawing = true;
      this.current.x = e.clientX||e.touches[0].clientX;
      this.current.y = e.clientY||e.touches[0].clientY;
    },
    onMouseUp(e){
      if (!this.drawing) { return; }
      this.drawing = false;
      this.drawLine(this.current.x, this.current.y, e.clientX||e.touches[0].clientX, e.clientY||e.touches[0].clientY, this.current.color, true);
    },
    onMouseMove(e){
      if (!this.drawing) { return; }
      this.drawLine(this.current.x, this.current.y, e.clientX||e.touches[0].clientX, e.clientY||e.touches[0].clientY, this.current.color, true);
      this.current.x = e.clientX||e.touches[0].clientX;
      this.current.y = e.clientY||e.touches[0].clientY;
    },
    onColorUpdate(e){
      this.current.color = e.target.className.split(' ')[1];
    },
    onDrawingEvent(data){
      const w = this.canvas.width;
      const h = this.canvas.height;
      this.drawLine(data.x0 * w, data.y0 * h, data.x1 * w, data.y1 * h, data.color);
    },

    throttle(callback, delay) {
      const previousCall = new Date().getTime();
      return function() {
        const time = new Date().getTime();

        if ((time - previousCall) >= delay) {
          previousCall = time;
          callback.apply(null, arguments);
        }
      };
    },
    onResize() {
      this.canvas.width = window.innerWidth;
      this.canvas.height = window.innerHeight;
    }
  },
  mounted() {
    const canvas = document.querySelector(".whiteboard");
    this.canvas = canvas;
    this.onResize()
    this.context = canvas.getContext('2d');
    this.colors = document.getElementsByClassName('color');
    for (let i = 0; i < this.colors.length; i++){
      this.colors[i].addEventListener('click', this.onColorUpdate, false);
    }
    this.$socket.on('drawing', this.onDrawingEvent)
  }
};
</script>
<style scoped>

/**
 * Fix user-agent
 */

* {
  box-sizing: border-box;
}

html, body {
  height: 100%;
  margin: 0;
  padding: 0;
}

/**
 * Canvas
 */

.whiteboard {
  height: 100%;
  width: 100%;
  position: absolute;
  left: 0;
  right: 0;
  bottom: 0;
  top: 0;
}

.colors {
  position: fixed;
}

.color {
  display: inline-block;
  height: 48px;
  width: 48px;
}

.color.black { background-color: black; }
.color.red { background-color: red; }
.color.green { background-color: green; }
.color.blue { background-color: blue; }
.color.yellow { background-color: yellow; }

</style>
