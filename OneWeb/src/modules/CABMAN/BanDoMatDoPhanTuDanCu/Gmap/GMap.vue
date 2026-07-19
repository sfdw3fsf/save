<template>
  <div>
    <div style="height: 933px">
      <vue-simple-context-menu
        elementId="menuMap"
        :options="optionsMapMenu"
        ref="vueSimpleContextMenuMap"
        z-index="10000"
        @option-clicked="menuMap_Clicked"
      />
      <GmapMap
        id="mapcontrol"
        ref="oneGmap"
        :zoom="zoomLevel"
        :options="mapOptions"
        :center="getPositionCenter"
        @center_changed="center_changed($event)"
        @zoom_changed="zoom_changed($event)"
        @bounds_changed="bounds_changed($event)"
        @rightclick="rightclickMap($event)"
        @click="clickMap($event)"
        style="width: 100%; height: calc(100% - 26px)"
      >
        <GmapMarker
          v-for="node in getlistNode"
          :key="node.id"
          :position="node.position"
          :icon="node.getIcon()"
        >
        </GmapMarker>
        <GmapInfoWindow
          :options="infoOptionsNode"
          :position="infoPositionNode"
          :opened="infoOpenedNode"
          @closeclick="infoOpenedNode = false"
        >
          <span v-html="infoContentNode"></span>
        </GmapInfoWindow>
      </GmapMap>
    </div>
  </div>
</template>
<script>
import GraphNode from "../structures/GraphNode";
import { gmapApi } from "gmap-vue";
import VueSimpleContextMenu from "vue-simple-context-menu";
import MeasureTool from "measuretool-googlemaps-v3";

export default {
  components: { "vue-simple-context-menu": VueSimpleContextMenu },
  name: "GMap",
  props: ["infoMapDefault"],
  computed: {
    google: gmapApi,
    getPositionCenter() {
      return this.location;
    },
    getlistNode() {
      return this.nodes.filter((n) => n.visible === true);
    },
    getinfoMapDefault() {
      return this.infoMapDefault;
    },
  },
  async created() {
    await this.$gmapApiPromiseLazy();
  },
  data() {
    return {
      zoomLevel: 20,
      draggable: false,
      isMapChanged: false,
      mapOptions: {
        zoomControl: true,
        scaleControl: true,
        disableDefaultUi: true,
        streetViewControl: false,
        mapTypeControl: false,
        fullscreenControl: false,
        maxZoom: this.maxZoom,
        disableDoubleClickZoom: true,
        draggable:true
      },
      nodes: [],
      polylines: [],
      location: { lat: -1, lng: -1 },
      mouselatLng: {},
      maxZoom: 20,
      map: null,
      measureTool: null,
      loadingMap: true,
      searchDiaDiem: null,
      showSearchDiaDiem: false,
      infoOptionsNode: {
        shouldFocus: false,
        pixelOffset: {
          width: 0,
          height: -35,
        },
      },
      infoOptionsPolyline: {
        pixelOffset: {
          width: 0,
          height: -35,
        },
      },
      infoContentNode: null,
      infoContentPolyline: null,
      infoPositionNode: null,
      infoPositionPolyline: null,
      infoOpenedNode: false,
      infoOpenedPolyline: false,
      optionsMapMenu: [
        {
          name: '<span class="icon text-main f20 one-trash"></span> Đây là đâu ?',
          slug: "dayladau",
        },
        {
          type: "divider",
        },
        {
          name: '<span class="icon text-main f20 one-chuyencap"></span> Đo khoảng cách',
          slug: "dokhoangcach",
        },
        {
          name: '<span class="icon text-main f20 one-taodiemuon"></span> Xóa phép đo',
          slug: "xoaphepdo",
        },
      ],
      heatmap: null,
      centerMap: null,
      recPoint: "",
      infoMapCurrent: null,
      points: [],
      drawingManager: null,
      atHere: null,
      typeKVChon: 0,
      overlay: null,
      isDelete: false,
      recKVCon:"",
      radiusHeatmap:0,
      opacityHeatmap:0
    };
  },
  watch: {
    searchDiaDiem: {
      handler: function (val) {
        this.location = {
          lat: val.geometry.location.lat(),
          lng: val.geometry.location.lng(),
        };
      },
      deep: true,
    },
    nodes: {
      handler: function (val) {},
      deep: true,
    },

    points: function (val) {
      this.heatmap = new window.google.maps.visualization.HeatmapLayer({
        data: this.getPoints(val),
        map: this.map,
        radius: this.radiusHeatmap,
        opacity: this.opacityHeatmap,
      });
    },
    typeKVChon: function (val) {
       if (this.drawingManager != null) {
        this.drawingManager.setDrawingMode(null);
       // this.typeKVChon =0
      }
      if (val != 0) this.drawOverlay(val);
    },
    isDelete: function (val) {
      if (val) {
        if (this.overlay != null) {
          this.overlay.setMap(null);
        }
        this.isDelete = false;
      }
    },
  },
  async mounted() {
    let vm = this;

    this.$refs.oneGmap.$mapPromise.then((map) => {
      vm.map = map;
      this.loading = false;
      this.measureTool = new MeasureTool(this.map, {
        contextMenu: false,
        unit: MeasureTool.UnitTypeId.METRIC,
        tooltip: false,
      });

      var polygon = new this.google.maps.Polygon({
        clickable: true,
        // other options...
      });

      this.measureTool.addListener("measure_start", () => {
        polygon.setOptions({ clickable: false });
      });
      this.measureTool.addListener("measure_end", (e) => {
        polygon.setOptions({ clickable: true });
      });
      this.measureTool.addListener("measure_change", (e) => {
       // polygon.setOptions({clickable: true});
      });
       window.addEventListener("keydown", function (e) {       
      if (e.key == "Enter") {
         vm.measureTool._mapClickEvent.remove()
          new vm.google.maps.Polygon({
        clickable: true,
        // other options...
      });     
      }
      if (e.key == "Escape") {
        vm.measureTool && vm.measureTool.end();
        vm.$emit('dokhoangcach',false)
      }});
    });
    window.addEventListener("keydown", function (e) {
      if (e.key == "Delete") {
        vm.isDelete = true;
      }
    });
  },
  methods: {
    reload() {
      this.bounds = new window.google.maps.LatLngBounds();
      this.map.setZoom(20);
      this.map.setCenter(
        new window.google.maps.LatLng(this.location.lat, this.location.lng)
      );
    },
    //#region "Hàm sự kiện"
    latLng2Point(latLng, map) {
      var topRight = map
        .getProjection()
        .fromLatLngToPoint(map.getBounds().getNorthEast());
      var bottomLeft = map
        .getProjection()
        .fromLatLngToPoint(map.getBounds().getSouthWest());
      var scale = Math.pow(2, map.getZoom());
      var worldPoint = map.getProjection().fromLatLngToPoint(latLng);
      return new window.google.maps.Point(
        (worldPoint.x - bottomLeft.x) * scale,
        (worldPoint.y - topRight.y) * scale
      );
    },
    fromLatLngToPixel: function (position) {
      var scale = Math.pow(2, this.map.getZoom());
      var proj = this.map.getProjection();
      var bounds = this.map.getBounds();
      var nw = proj.fromLatLngToPoint(
        new window.google.maps.LatLng(
          bounds.getNorthEast().lat(),
          bounds.getSouthWest().lng()
        )
      );
      var point = proj.fromLatLngToPoint(position);
      return new window.google.maps.Point(
        Math.floor((point.x - nw.x) * scale),
        Math.floor((point.y - nw.y) * scale)
      );
    },
    bounds_changed: function ($event) {
      let obj = this.getinfoMapCurrent();
      this.$emit("infoMapCurrent", obj);
    },
    center_changed: function ($event) {
      let obj = this.getinfoMapCurrent();
      this.$emit("infoMapCurrent", obj);
    },
    zoom_changed: function ($event) {
      let obj = this.getinfoMapCurrent();
      this.$emit("infoMapCurrent", obj);
    },
    getinfoMapCurrent() {
      try {
        this.centerMap =
          this.map.getCenter().lat() + "," + this.map.getCenter().lng();
        var topRight = this.map.getBounds().getNorthEast();
        var bottomLeft = this.map.getBounds().getSouthWest();
        this.recPoint =
          topRight.lat() +
          "," +
          topRight.lng() +
          ";" +
          topRight.lat() +
          "," +
          bottomLeft.lng() +
          ";" +
          bottomLeft.lat() +
          "," +
          bottomLeft.lng() +
          ";" +
          bottomLeft.lat() +
          "," +
          topRight.lng();
        this.zoomLevel = this.map.getZoom();
        var rectBoundsLatlng = new Array();
        var rectBoundsLatlngPath = new Array();
        (rectBoundsLatlng[0] = new google.maps.LatLng(
          topRight.lat(),
          topRight.lng()
        )),
          (rectBoundsLatlng[1] = new google.maps.LatLng(
            bottomLeft.lat(),
            bottomLeft.lng()
          )),
          (rectBoundsLatlngPath[0] = new google.maps.LatLng(
            rectBoundsLatlng[1].lat(),
            rectBoundsLatlng[0].lng()
          )),
          (rectBoundsLatlngPath[1] = new google.maps.LatLng(
            rectBoundsLatlng[0].lat(),
            rectBoundsLatlng[1].lng()
          ));

        var areaPath = [
          rectBoundsLatlng[0],
          rectBoundsLatlngPath[0],
          rectBoundsLatlng[1],
          rectBoundsLatlngPath[1],
          rectBoundsLatlng[0],
        ];
        let area = window.google.maps.geometry.spherical.computeArea(areaPath);
        return {
          centerMap: this.centerMap,
          recPoint: this.recPoint,
          zoomLevel: this.zoomLevel,
          area: area,
        };
      } catch (e) {}
    },
    rightclickMap(event) {
      this.$refs.vueSimpleContextMenuMap &&
        this.$refs.vueSimpleContextMenuMap.hideContextMenu();
      this.$refs.vueSimpleContextMenuMap.showMenu(event);
      const menu = document.getElementById("menuMap");
      let pixelOffset = new window.google.maps.Point(10, -5);
      let mapDiv = this.map.getDiv();
      var mousePosition = this.fromLatLngToPixel(event.latLng);
      var mapSize = new window.google.maps.Size(
        mapDiv.offsetWidth,
        mapDiv.offsetHeight
      );
      var menuSize = new window.google.maps.Size(
        menu.offsetWidth,
        menu.offsetHeight
      );
      var left = mousePosition.x;
      var top = mousePosition.y;
      if (mousePosition.x > mapSize.width - menuSize.width - pixelOffset.x) {
        left = left - menuSize.width - pixelOffset.x;
      } else {
        left += pixelOffset.x;
      }
      if (mousePosition.y > mapSize.height - menuSize.height - pixelOffset.y) {
        top = top - menuSize.height - pixelOffset.y;
      } else {
        top += pixelOffset.y;
      }
      this.atHere = event.latLng.lat() + "," + event.latLng.lng();
      menu.style.left = left + "px";
      menu.style.top = top + "px";
      menu.position = "fixed";
      menu.classList.add("menu-context");
    },
    clickMap: function (event) {
      this.isViewThongTinKV = false;
      this.$emit("anThongTinKV", false);
    },
    insertAt(arr, index, newItem) {
      return [...arr.slice(0, index), newItem, ...arr.slice(index)];
    },
    menuMap_Clicked(event) {
      switch (event.option.slug) {
        case "dayladau":
          this.isHere()
          break;
        case "dokhoangcach":
          this.measureTool && this.measureTool.start();
          this.$emit('dokhoangcach',true)
          break;
        case "xoaphepdo":
          this.measureTool && this.measureTool.end();
          this.$emit('dokhoangcach',false)
          break;
      }
    },
    dragendPositionNode(event, node) {},
    dragPositionNode(event, node) {
      try {
        this.isMapChanged = true;
        const nodeid = node.id;
        let lat = event.latLng.lat();
        let lng = event.latLng.lng();

        const objIndex = this.nodes.findIndex((obj) => obj.id === nodeid);
        if (objIndex < 0) return;
        this.nodes[objIndex].position.lat = lat;
        this.nodes[objIndex].position.lng = lng;
        this.nodes[objIndex].modified = true;
      } catch (error) {
        console.log("dragPositionNode error", error);
      }
    },
    getLabel(symbol) {
      return {
        text: symbol,
        color: "#333333",
        fontSize: "12px",
        fontFamily: "Roboto, Arial",
        fontWeight: "bold",
      };
    },
    mouseoverNode(event, node) {
      node.mouseover = true;
      this.infoPositionNode = event.latLng;
      this.infoContentNode = node.tooltipText;
      this.infoOpenedNode = true;
      this.infoOpenedPolyline = false;
    },
    mouseoutNode(event, node) {
      node.mouseover = false;
      this.infoOpenedNode = false;
      this.infoOpenedPolyline = false;
    },
    onSearchDiaDiem(data) {
      this.searchDiaDiem = data;
    },
    //#endregion
    luuToaDoBanDo() {
      var nodes = [];
      let count = this.nodes.length;
      const node = this.nodes[count - 1];
      nodes.push({
        lat: node.getPosition().lat.toString(),
        lng: node.getPosition().lng.toString(),
      });
      this.$emit("luuToaDoBanDo", nodes);
    },
    getPoints: function (points) {
      let data = [];

      if (points != null && points.length > 0)
        for (let i = 0; i < points.length; i++)
          data.push(
            new window.google.maps.LatLng(
              points[i].split(",")[0],
              points[i].split(",")[1]
            )
          );
      return data;
      //    return [
      //     new window.google.maps.LatLng(20.8451833,106.5852595),
      // new window.google.maps.LatLng(20.86941833,106.6552595),
      // new window.google.maps.LatLng(20.84661833,106.6852695),
      //    new window.google.maps.LatLng(20.4441833,106.6552595),
      // new window.google.maps.LatLng(20.8457833,106.68556595),
      // new window.google.maps.LatLng(20.84458313,106.6852595),
      // new window.google.maps.LatLng(20.8418353,106.65824195),
      // new window.google.maps.LatLng(20.84418233,106.68352595),
      // new window.google.maps.LatLng(20.84418133,106.66852595),
      //    new window.google.maps.LatLng(20.81441833,106.68452595),
      // new window.google.maps.LatLng(20.84411833,106.68152595),
      // new window.google.maps.LatLng(20.84241833,106.68552595),
      //   ]
    },
    //#region "Hàm xủ lý"
    dragendPosition(event, node) {
      this.isMapChanged = true;
    },
    setPositionNode(id, position) {
      const objIndex = this.nodes.findIndex((obj) => obj.id === id);
      if (objIndex >= 0) {
        this.nodes[objIndex].position.lat = parseFloat(position.lat);
        this.nodes[objIndex].position.lng = parseFloat(position.lng);
        this.nodes[objIndex].modified = true;
      }
    },
    getPositionNode: function (id) {
      const objIndex = this.nodes.findIndex((obj) => obj.id === id);
      if (objIndex >= 0) {
        return {
          lat: parseFloat(this.nodes[objIndex].position.lat),
          lng: parseFloat(this.nodes[objIndex].position.lng),
        };
      } else return null;
    },
    setDraggableNode(id, draggable) {
      const objIndex = this.nodes.findIndex((obj) => obj.id === id);
      if (objIndex >= 0) {
        this.nodes[objIndex].draggable = draggable;
      }
    },
    mousemove(event) {
      this.mouselatLng = { lat: event.latLng.lat(), lng: event.latLng.lng() };
    },
    panToMarker(pos) {
      this.$refs.oneGmap.panTo(pos);
    },
    nodeCreate() {
      var node = new GraphNode();
      return node;
    },
    create_UUID() {
      var dt = new Date().getTime();
      var uuid = "xxxxxxxx_xxxx_4xxx_yxxx_xxxxxxxxxxxx".replace(
        /[xy]/g,
        function (c) {
          var r = (dt + Math.random() * 16) % 16 | 0;
          dt = Math.floor(dt / 16);
          return (c === "x" ? r : (r & 0x3) | 0x8).toString(16);
        }
      );
      return uuid;
    },
    getNodes() {
      return this.nodes;
    },
    nodeUpdate(node) {
      const objIndex = this.nodes.findIndex((obj) => obj.id === node.id);
      if (objIndex >= 0) return false;
      this.nodes.push(node);
      return true;
    },
    setLocationEx(position) {
      if (position === null || position === "undefined") {
        if (this.nodes.length > 0) {
          this.location = {
            lat: this.nodes[0].position.lat,
            lng: this.nodes[0].position.lng,
          };
        }
      } else {
        this.location = {
          lat: position.lat,
          lng: position.lng,
        };
      }
    },
    clear() {
      this.nodes.length = 0;
      this.nodes = [];
      this.polylines.length = 0;
      this.polylines = [];
    },
    getPositionNodeId(id) {
      const objIndex = this.nodes.findIndex((obj) => obj.id === id);
      if (objIndex >= 0) {
        return this.nodes[objIndex].position;
      } else return null;
    },
    getNodeById(id) {
      const objIndex = this.nodes.findIndex((obj) => obj.id === id);
      if (objIndex >= 0) return this.nodes[objIndex];
      else return null;
    },
    setViewNodeSuaToaDoCap(id) {
      const polyline = this.getPolylineById(id);
      if (polyline !== null) {
        const path = polyline.path;
        for (let index = 0; index < path.length; index++) {
          const objIndex = this.nodes.findIndex(
            (obj) => obj.id === path[index].id
          );
          if (objIndex >= 0) {
            this.nodes[objIndex].visible = true;
            this.nodes[objIndex].draggable = true;
          }
        }
      }
    },
    getIsMapChanged() {
      return this.isMapChanged;
    },
    setNodes(nodes) {
      //if(this.heatmap!=null)
      // this.heatmap.setMap(null)
      this.nodes = nodes;
    },
    setDataPoints(points,size,opacity) {
      this.points = points;
      this.radiusHeatmap =size
      this.opacityHeatmap = opacity
    },
    setinfoMapDefault: function (infoMapDefault) {
      this.location.lat = infoMapDefault.lat;
      this.location.lng = infoMapDefault.lng;
      this.zoomLevel = infoMapDefault.zoom;
    },
    setNullHeatmap: function () {
      if (this.heatmap != null) {
        this.heatmap.setMap(null);
      }
    },
    drawOverlay: function (type) {
      let mode = null;
      if (type == 1) mode = window.google.maps.drawing.OverlayType.CIRCLE;
      if (type == 2) mode = window.google.maps.drawing.OverlayType.RECTANGLE;
      if (type == 3) mode = window.google.maps.drawing.OverlayType.POLYGON;
      this.drawingManager = new window.google.maps.drawing.DrawingManager({
        drawingMode: mode,
        drawingControl: false,
        drawingControlOptions: {
          position: window.google.maps.ControlPosition.TOP_CENTER,
          drawingModes: [
            window.google.maps.drawing.OverlayType.CIRCLE,
            window.google.maps.drawing.OverlayType.POLYGON,
            window.google.maps.drawing.OverlayType.POLYLINE,
            window.google.maps.drawing.OverlayType.RECTANGLE,
          ],
        },
        circleOptions: {
          fillOpacity: 0.3,
          strokeWeight: 1,
          clickable: true,
          editable: false,
          zIndex: 1,
        },
        rectangleOptions: {
          fillOpacity: 0.3,
          strokeWeight: 1,
          clickable: true,
          editable: false,
          zIndex: 1,
        },
        polylineOptions: {
          fillOpacity: 0.3,
          strokeWeight: 1,
          clickable: true,
          editable: false,
          zIndex: 1,
        },
      });
      this.drawingManager.addListener("circlecomplete", this.circlecomplete);
      this.drawingManager.addListener("polygoncomplete", this.polygoncomplete);
      this.drawingManager.addListener("rectanglecomplete", this.rectanglecomplete);
      this.drawingManager.setMap(this.map);
    },
    circlecomplete: function (overlay) {
      let circle
       circle= new  window.google.maps.Circle
       circle= overlay
       var self = this
      window.google.maps.event.addListener(circle ,"click",function(){
   
         var bounds = circle.getBounds();
        var topRight = bounds.getNorthEast();
        var bottomLeft = bounds.getSouthWest();
        this.recKVCon =
          topRight.lat() +
          "," +
          topRight.lng() +
          ";" +
          topRight.lat() +
          "," +
          bottomLeft.lng() +
          ";" +
          bottomLeft.lat() +
          "," +
          bottomLeft.lng() +
          ";" +
          bottomLeft.lat() +
          "," +
          topRight.lng();
         circle.fillOpacity=1
         self.setOverLay(circle)
         self.$emit('getRecKVCon',this.recKVCon)
      })
      if (this.drawingManager != null) {
        this.drawingManager.setDrawingMode(null);
        this.typeKVChon =0
      }
    },
    polygoncomplete: function (overlay) {
      let polygon
      polygon = new  window.google.maps.Polygon
      polygon = overlay
       var self = this
      window.google.maps.event.addListener(polygon ,"click",function(){
         var path = polygon.getPath()
         var coordinates = "";
        for (var i = 0 ; i < path.length ; i++) {
         let point = path.getAt(i).lat()+","+path.getAt(i).lng()
         coordinates+=point+";"
        }
          this.recKVCon =coordinates
          self.$emit('getRecKVCon',this.recKVCon)
         polygon.fillOpacity=1
         self.setOverLay(polygon)
      })
      if (this.drawingManager != null) {
        this.drawingManager.setDrawingMode(null);
        this.typeKVChon =0
      }
    },
    rectanglecomplete: function (overlay) {
      let rectangle
      rectangle = new  window.google.maps.Rectangle
      rectangle= overlay
       var self = this
      window.google.maps.event.addListener(rectangle ,"click",function(){
        var bounds = rectangle.getBounds();
        var topRight = bounds.getNorthEast();
        var bottomLeft = bounds.getSouthWest();
        this.recKVCon =
          topRight.lat() +
          "," +
          topRight.lng() +
          ";" +
          topRight.lat() +
          "," +
          bottomLeft.lng() +
          ";" +
          bottomLeft.lat() +
          "," +
          bottomLeft.lng() +
          ";" +
          bottomLeft.lat() +
          "," +
          topRight.lng();
         rectangle.fillOpacity=1
         self.setOverLay(rectangle)
        self.$emit('getRecKVCon',this.recKVCon)
      })
      if (this.drawingManager != null) {
        this.drawingManager.setDrawingMode(null);
        this.typeKVChon =0
      }
    },
    setTypeDraw: function (type) {
      this.typeKVChon = type;
    },
    setOverLay: function (e) {
      this.overlay = e;
    },
    setLockMap: function (islock) {
     // console.log(this.map)
      this.mapOptions.draggable = islock;
    },
    isHere:function(){
    const latlng = {
    lat: parseFloat(this.atHere.split(',')[0]),
    lng: parseFloat(this.atHere.split(',')[1]),
  };
    const geocoder = new window.google.maps.Geocoder();
         geocoder
    .geocode({ location: latlng })
    .then((response) => {
      if (response.results[0]) {
        console.log('Địa chỉ:',response.results[0])
       this.$root.toastSuccess('Đây là: '+response.results[0].formatted_address);
      } else {
         this.$root.toastError("Không tìm thấy");
      }
    })
    .catch((e) =>  this.$root.toastError("Lỗi phát sinh: " + e));
    }
    //#endregion
  },
};
</script>
<style>
.vue-simple-context-menu {
  top: 0;
  left: 0;
  margin: 0;
  padding: 0;
  display: none;
  list-style: none;
  position: absolute;
  z-index: 1000000;
  background-color: #ecf0f1;
  border-bottom-width: 0px;
  font-family: "Roboto", Arial;
  font-size: 1rem;
  box-shadow: 0 3px 6px 0 rgba(51, 51, 51, 0.2);
  border-radius: 4px;
}
.vue-simple-context-menu--active {
  display: block;
}
.vue-simple-context-menu__item {
  display: flex;
  color: #333;
  cursor: pointer;
  padding: 5px 15px;
  align-items: center;
}
.vue-simple-context-menu__item:hover {
  background-color: #f5f5f5;
}
.vue-simple-context-menu__divider {
  box-sizing: content-box;
  height: 1px;
  background-color: #c0cdd1;
  padding: 4px 0;
  background-clip: content-box;
  pointer-events: none;
}
.vue-simple-context-menu li:first-of-type {
  margin-top: 4px;
}
.vue-simple-context-menu li:last-of-type {
  margin-bottom: 4px;
}
.gm-style-iw > button {
  display: none !important;
}
.control-map {
  margin-top: 4px;
}
</style>
