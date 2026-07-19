<template>
  <div>
    <!-- <el-tooltip class="item" effect="light"  content="Lưu tọa độ bản đồ" placement="top">
        <el-button ref="ctrlLuuToaDoBanDo" type="info"  plain  icon="el-icon-s-tools"  size="small" class="control-map"></el-button>
      </el-tooltip> -->
    <el-tooltip class="item" effect="light" content="Thay đổi nguồn dữ liệu bản đồ" placement="top">
      <el-button ref="ctrlThayDoiNguonDuLieu" type="info" plain icon="el-icon-s-order" size="small"
        class="control-map"></el-button>
    </el-tooltip>
    <el-tooltip class="item" effect="light" content="Di chuyển đối tượng" placement="top">
      <el-button ref="ctrlDuyChuyenDoiTuong" type="info" plain icon="el-icon-rank" size="small"
        class="control-map"></el-button>
    </el-tooltip>
    <div style="height: 800px;">
      <transition name="slide-fade" v-if="showSearchDiaDiem">
        <gmap-autocomplete ref="SearchDiaDiem1" placeholder="Gõ để tìm kiếm địa điểm" class="form-control"
          :options="{fields: ['geometry', 'formatted_address', 'address_components']}" @place_changed="onSearchDiaDiem" />
      </transition>
      <vue-simple-context-menu elementId="menuMap1" vueSimpleContextMenuMap1 :options="optionsMapMenu" ref=""
        z-index="10000" @option-clicked="menuMap_Clicked" />
      <GmapMap id="mapcontrol" ref="oneGmap" :zoom="zoomLevel" :options="mapOptions" :center="getPositionCenter"
        @rightclick="rightclickMap($event)" style="width: 100%; height: calc(100% - 26px);">
        <GmapMarker v-for="node in getlistNode" :key="node.id" :position="node.position" :icon="node.getIcon()"
          @mouseover="mouseoverNode($event, node)" @mouseout="mouseoutNode($event, node)"
          @drag="dragPositionNode($event,node)" @click="leftclickNode($event, node)"
          @dragend="dragendPositionNode($event,node)" :draggable="node.draggable && draggable">
        </GmapMarker>
        <GmapInfoWindow :options="infoOptionsNode" :position="infoPositionNode" :opened="infoOpenedNode"
          @closeclick="infoOpenedNode = false">
          <span v-html="infoContentNode"></span>
        </GmapInfoWindow>
      </GmapMap>
    </div>
    <div class="map-position bg-white">
      <div class="row">
        <div class="col-sm-4 col-12">
          <div class="info-row marb0">
            <div class="key w30 nowrap">Vĩ độ</div>
            <div class="value">
              <input type="text" v-model="vido" class="form-control">
            </div>
          </div>
        </div>
        <div class="col-sm-4 col-12">
          <div class="info-row marb0">
            <div class="key w30 nowrap">Kinh độ</div>
            <div class="value">
              <input type="text" v-model="kinhdo" class="form-control">
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>

import GraphNode from '../structures/GraphNode'
import { gmapApi } from 'gmap-vue'
import VueSimpleContextMenu from 'vue-simple-context-menu'
import MeasureTool from 'measuretool-googlemaps-v3'

export default {
  components: { 'vue-simple-context-menu': VueSimpleContextMenu },
  name: 'GMap',
  props: {
  },
  computed: {
    google: gmapApi,
    getPositionCenter() { return this.location },
    getlistNode() { return this.nodes.filter(n => n.visible === true) },
  },
  async created() {
    await this.$gmapApiPromiseLazy()
  },
  data() {
    return {
      zoomLevel: 20,
      draggable: false,
      isMapChanged: false,
      mapOptions: {
        zoomControl: true, scaleControl: true,
        disableDefaultUi: true, streetViewControl: false,
        mapTypeControl: false, fullscreenControl: false,
        maxZoom: this.maxZoom,
        disableDoubleClickZoom: true
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
      activePolyline: null,
      showSearchDiaDiem: false,
      infoOptionsNode: {
        shouldFocus: false,
        pixelOffset: {
          width: 0,
          height: -35
        }
      },
      infoOptionsPolyline: {
        pixelOffset: {
          width: 0,
          height: -35
        }
      },
      infoContentNode: null,
      infoContentPolyline: null,
      infoPositionNode: null,
      infoPositionPolyline: null,
      infoOpenedNode: false,
      infoOpenedPolyline: false,
      kinhdo: "",
      vido: "",
      optionsMapMenu: [
        {
          name: '<span class="icon text-main f20 one-daucuoi"></span> Làm tươi',
          slug: 'lamtuoi'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 one-trash"></span> Đây là đâu ?',
          slug: 'dayladau'
        },
        {
          name: '<span class="icon text-main f20 one-daochieu"></span> Tìm địa điểm',
          slug: 'tim_dia_diem'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 one-chuyencap"></span> Đo khoảng cách',
          slug: 'dokhoangcach'
        },
        {
          name: '<span class="icon text-main f20 one-taodiemuon"></span> Xóa phép đo',
          slug: 'xoaphepdo'
        }
      ]
    }
  },
  watch: {
    polylines: {
      handler: function (val) {
      },
      deep: true
    },
    searchDiaDiem: {
      handler: function (val) {
        this.location = {
          lat: val.geometry.location.lat(),
          lng: val.geometry.location.lng()
        }
      },
      deep: true
    },
    nodes: {
      handler: function (val) {
      },
      deep: true
    }
  },
  async mounted() {
    let vm = this
    this.$refs.oneGmap.$mapPromise.then((map) => {
      vm.map = map

      let ctrlDuyChuyenDoiTuong = this.$refs.ctrlDuyChuyenDoiTuong.$el
      map.controls[this.google.maps.ControlPosition.TOP_RIGHT].push(ctrlDuyChuyenDoiTuong)
      ctrlDuyChuyenDoiTuong.addEventListener('click', () => {
        this.draggable = !this.draggable
      })

      // let ctrlLuuToaDoBanDo = this.$refs.ctrlLuuToaDoBanDo.$el
      // map.controls[this.google.maps.ControlPosition.RIGHT_TOP].push(ctrlLuuToaDoBanDo)
      // ctrlLuuToaDoBanDo.addEventListener('click', () => {
      //   this.luuToaDoBanDo()
      // })
      let ctrlThayDoiNguonDuLieu = this.$refs.ctrlThayDoiNguonDuLieu.$el
      map.controls[this.google.maps.ControlPosition.RIGHT_TOP].push(ctrlThayDoiNguonDuLieu)
      ctrlThayDoiNguonDuLieu.addEventListener('click', () => {
      })

      this.loading = false
      this.measureTool = new MeasureTool(this.map, {
        contextMenu: false,
        unit: MeasureTool.UnitTypeId.METRIC,
        tooltip: false
      })

      var polygon = new this.google.maps.Polygon({
        clickable: true
        // other options...
      })

      this.measureTool.addListener('measure_start', () => {
        polygon.setOptions({ clickable: false })
      })
      this.measureTool.addListener('measure_end', (e) => {
        polygon.setOptions({ clickable: true })
      })
      this.measureTool.addListener('measure_change', (e) => {
      })
    })
  },
  methods: {
    reload() {
      this.bounds = new window.google.maps.LatLngBounds()
      this.map.setZoom(20)
      this.map.setCenter(
        new window.google.maps.LatLng(
          this.location.lat,
          this.location.lng
        )
      )
    },
    //#region "Hàm sự kiện"
    latLng2Point(latLng, map) {
      var topRight = map.getProjection().fromLatLngToPoint(map.getBounds().getNorthEast())
      var bottomLeft = map.getProjection().fromLatLngToPoint(map.getBounds().getSouthWest())
      var scale = Math.pow(2, map.getZoom())
      var worldPoint = map.getProjection().fromLatLngToPoint(latLng)
      return new window.google.maps.Point((worldPoint.x - bottomLeft.x) * scale, (worldPoint.y - topRight.y) * scale)
    },
    fromLatLngToPixel: function (position) {
      var scale = Math.pow(2, this.map.getZoom())
      var proj = this.map.getProjection()
      var bounds = this.map.getBounds()
      var nw = proj.fromLatLngToPoint(
        new window.google.maps.LatLng(
          bounds.getNorthEast().lat(),
          bounds.getSouthWest().lng()
        ))
      var point = proj.fromLatLngToPoint(position)
      return new window.google.maps.Point(
        Math.floor((point.x - nw.x) * scale),
        Math.floor((point.y - nw.y) * scale))
    },

    rightclickMap(event) {
      this.$refs.vueSimpleContextMenuMap1 && this.$refs.vueSimpleContextMenuMap1.hideContextMenu()
      // this.$refs.vueSimpleContextMenuNode && this.$refs.vueSimpleContextMenuNode.hideContextMenu()
      //  this.$refs.vueSimpleContextMenuPolyline && this.$refs.vueSimpleContextMenuPolyline.hideContextMenu()
      //  this.$refs.vueSimpleContextMenuNodeDiemUon && this.$refs.vueSimpleContextMenuNodeDiemUon.hideContextMenu()
      this.$refs.vueSimpleContextMenuMap1.showMenu(event)
      const menu = document.getElementById('menuMap1')
      let pixelOffset = new window.google.maps.Point(10, -5)
      let mapDiv = this.map.getDiv()
      var mousePosition = this.fromLatLngToPixel(event.latLng)
      var mapSize = new window.google.maps.Size(mapDiv.offsetWidth, mapDiv.offsetHeight)
      var menuSize = new window.google.maps.Size(menu.offsetWidth, menu.offsetHeight)
      var left = mousePosition.x
      var top = mousePosition.y
      if (mousePosition.x > mapSize.width - menuSize.width - pixelOffset.x) {
        left = left - menuSize.width - pixelOffset.x
      } else {
        left += pixelOffset.x
      }
      if (mousePosition.y > mapSize.height - menuSize.height - pixelOffset.y) {
        top = top - menuSize.height - pixelOffset.y
      } else {
        top += pixelOffset.y
      }
      menu.style.left = left + 'px'
      menu.style.top = top + 'px'
      menu.position = 'fixed'
      menu.classList.add('menu-context')
    },

    leftclickNode(event, node) {

    },
    insertAt(arr, index, newItem) {
      return [
        ...arr.slice(0, index),
        newItem,
        ...arr.slice(index)
      ]
    },
    menuMap_Clicked(event) {
      switch (event.option.slug) {
        case 'tim_dia_diem':
          this.showSearchDiaDiem = !this.showSearchDiaDiem
          break
        case 'dokhoangcach':
          this.measureTool && this.measureTool.start()
          break
        case 'xoaphepdo':
          this.measureTool && this.measureTool.end()
          break
      }
    },
    dragendPositionNode(event, node) { },
    dragPositionNode(event, node) {
      try {
        this.isMapChanged = true
        const nodeid = node.id
        let lat = event.latLng.lat()
        let lng = event.latLng.lng()

        const objIndex = this.nodes.findIndex(obj => obj.id === nodeid)
        if (objIndex < 0) return
        this.nodes[objIndex].position.lat = lat
        this.nodes[objIndex].position.lng = lng
        this.nodes[objIndex].modified = true
        this.vido = lat
        this.kinhdo = lng
        this.luuToaDoBanDo();
      } catch (error) {
        console.log('dragPositionNode error', error)
      }
    },
    polylineOptions: function (polyline) {
      var style = polyline.style
      if (style === null) style = '0-0-0-1'
      var st = style.split('-')
      var opt = { strokeOpacity: 0, geodesic: true }

      var lineSymbol = {
        path: 'M 0,-1 0,1',
        strokeOpacity: 1,
        strokeWeight: 2,
        scale: 3
      }
      var doubleLine = {
        path: 'M 0.5,-1 0.5,1 M -0.5,-1 -0.5,1',
        strokeOpacity: 1,
        strokeWeight: 1,
        scale: 3
      }
      var color = ['#a52a2a', '#1064b4', '#000000']
      var icons = [
        [{
          icon: lineSymbol,
          offset: '0%',
          repeat: '6px'
        }],
        [{
          icon: lineSymbol,
          offset: '50%',
          repeat: '15px'
        }],
        [{
          icon: doubleLine,
          offset: '0%',
          repeat: '6px'
        }],
        [{
          icon: lineSymbol,
          offset: '50%',
          repeat: '25px'
        }]
      ]
      switch (st[0]) {
        case '0':
          opt = {
            ...opt,
            strokeWeight: 4
          }
          break
        case '1':
          opt = {
            ...opt,
            strokeWeight: 6
          }
          break
        default:
          opt = {
            ...opt,
            strokeWeight: 2
          }
      }
      switch (st[1]) {
        case '0':
          opt = {
            ...opt,
            strokeColor: color[0]
          }
          break
        case '1':
          opt = {
            ...opt,
            strokeColor: color[1]
          }
          break
        default:
          opt = {
            ...opt,
            strokeColor: color[2]
          }
      }
      switch (st[2]) {
        case '2': // Kéo cống        [▬▬  ▬▬  ▬▬  ▬▬  ▬▬  ▬]
          opt = {
            ...opt,
            icons: icons[1]
          }
          break
        case '3': // Kéo treo        [▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬]
          opt = {
            ...opt,
            icons: icons[0]
          }
          break
        case '4': // Chôn        [▬ ▬ ▬ ▬ ▬ ▬ ▬ ▬ ▬ ▬ ▬]
          opt = {
            ...opt,
            icons: icons[3]
          }
          break
        case '5': // Đề xuất kéo mới [▬▬▬ ▬ ▬▬▬ ▬ ▬▬▬ ▬ ▬▬▬]
          opt = {
            ...opt,
            icons: icons[2]
          }
          break
        default: // Không xác định   [• • • • • • • • • • •]
          opt = {
            ...opt,
            icons: icons[1]
          }
      }
      if (st[3] === '1') {
        opt.strokeWeight = opt.strokeWeight / 2
        opt = {
          ...opt,
          strokeOpacity: 0.5
        }
      }
      return opt
    },
    getLabel(symbol) {
      return { 'text': symbol, 'color': '#333333', 'fontSize': '12px', 'fontFamily': 'Roboto, Arial', 'fontWeight': 'bold' }
    },
    mouseoverNode(event, node) {
      node.mouseover = true
      this.infoPositionNode = event.latLng
      this.infoContentNode = node.tooltipText
      this.infoOpenedNode = true
      this.infoOpenedPolyline = false
    },
    mouseoverNodeDiemUon(event, node) {
      this.infoOpenedNode = false
      this.infoOpenedPolyline = false
    },
    mouseoutNode(event, node) {
      node.mouseover = false
      this.infoOpenedNode = false
      this.infoOpenedPolyline = false
    },
    mouseoverPolyline(event, polyline) {
      polyline.mouseover = true
      this.infoPositionNode = event.latLng
      this.infoContentNode = polyline.label
      this.infoOpenedNode = true
      this.infoOpenedPolyline = false
    },
    mouseoutPolyline(event, polyline) {
      polyline.mouseover = false
      this.infoOpenedNode = false
      this.infoOpenedPolyline = false
    },
    onSearchDiaDiem(data) {
      this.searchDiaDiem = data
    },
    //#endregion
    luuToaDoBanDo() {
      var nodes = []
      let count = this.nodes.length
      const node = this.nodes[count - 1]
      nodes.push({ lat: node.getPosition().lat.toString(), lng: node.getPosition().lng.toString() })
      this.$emit('luuToaDoBanDo', nodes)
    },
    //#region "Hàm xủ lý"
    dragendPosition(event, node) {
      this.isMapChanged = true
    },
    setPositionNode(id, position) {
      const objIndex = this.nodes.findIndex(obj => obj.id === id)
      if (objIndex >= 0) {
        this.nodes[objIndex].position.lat = parseFloat(position.lat)
        this.nodes[objIndex].position.lng = parseFloat(position.lng)
        this.nodes[objIndex].modified = true
      }
    },
    getPositionNode: function (id) {
      const objIndex = this.nodes.findIndex(obj => obj.id === id)
      if (objIndex >= 0) {
        return {
          lat: parseFloat(this.nodes[objIndex].position.lat),
          lng: parseFloat(this.nodes[objIndex].position.lng)
        }
      } else return null
    },
    setDraggableNode(id, draggable) {
      const objIndex = this.nodes.findIndex(obj => obj.id === id)
      if (objIndex >= 0) {
        this.nodes[objIndex].draggable = draggable
      }
    },
    mousemove(event) {
      this.mouselatLng = { lat: event.latLng.lat(), lng: event.latLng.lng() }
    },
    getPositionPolyline: function (item) {
      return {
        lat: parseFloat(item.lat),
        lng: parseFloat(item.lng)
      }
    },
    panToMarker(pos) {
      this.$refs.oneGmap.panTo(pos)
    },
    nodeCreate() {
      var node = new GraphNode()
      return node
    },
    create_UUID() {
      var dt = new Date().getTime()
      var uuid = 'xxxxxxxx_xxxx_4xxx_yxxx_xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = (dt + Math.random() * 16) % 16 | 0
        dt = Math.floor(dt / 16)
        return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16)
      })
      return uuid
    },
    diemuonAdd(id_polyline, position, visible, draggable) {
      var id = 'DIEMUON_' + this.create_UUID()
      var node = new GraphNode()
      node.id = id
      node.visible = visible
      node.draggable = draggable
      node.setPosition(position)
      node.isDiemuon = true
      node.id_polyline = id_polyline
      this.nodeUpdate(node)
      return id
    },
    getNodes() {
      return this.nodes
    },
    getPolylines() {
      return this.polylines
    },
    nodeUpdate(node) {
      const objIndex = this.nodes.findIndex(obj => obj.id === node.id)
      if (objIndex >= 0) return false
      this.nodes.push(node)
      return true
    },
    setLocationEx(position) {
      if (position === null || position === 'undefined') {
        if (this.nodes.length > 0) {
          this.location = {
            lat: this.nodes[0].position.lat,
            lng: this.nodes[0].position.lng
          }
        }
      } else {
        this.location = {
          lat: position.lat,
          lng: position.lng
        }
      }
    },
    clear() {
      this.nodes.length = 0
      this.nodes = []
      this.polylines.length = 0
      this.polylines = []
    },
    polylineCreate() {
      var polyline = new GraphPolyline()
      return polyline
    },
    polylineUpdate(polyline) {
      const objIndex = this.polylines.findIndex(obj => obj.id === polyline.id)
      if (objIndex >= 0) return false
      this.polylines.push(polyline)
      return true
    },
    getPositionNodeId(id) {
      const objIndex = this.nodes.findIndex(obj => obj.id === id)
      if (objIndex >= 0) {
        return this.nodes[objIndex].position
      } else return null
    },
    getPolylineById(id) {
      const objIndex = this.polylines.findIndex(obj => obj.id === id)
      if (objIndex >= 0) return this.polylines[objIndex]
      else return null
    },
    getNodeById(id) {
      const objIndex = this.nodes.findIndex(obj => obj.id === id)
      if (objIndex >= 0) return this.nodes[objIndex]
      else return null
    },
    setViewNodeSuaToaDoCap(id) {
      const polyline = this.getPolylineById(id)
      if (polyline !== null) {
        const path = polyline.path
        for (let index = 0; index < path.length; index++) {
          const objIndex = this.nodes.findIndex(obj => obj.id === path[index].id)
          if (objIndex >= 0) {
            this.nodes[objIndex].visible = true
            this.nodes[objIndex].draggable = true
          }
        }
      }
    },
    getPolylinePath(id) {
      const polyline = this.getPolylineById(id)
      if (polyline !== null) {
        const path = polyline.path
        var pathposition = []
        for (let index = 0; index < path.length; index++) {
          const position = this.getPositionNodeId(path[index])
          if (position !== null) {
            pathposition.push(position)
          }
        }
        return pathposition
      } else return []
    },
    getIsMapChanged() {
      return this.isMapChanged
    },
    setNodes(nodes) {
      this.nodes = nodes
      let count = this.nodes.length
      if (count > 0) {
        this.vido = this.nodes[count - 1].position.lat
        this.kinhdo = this.nodes[count - 1].position.lng
        this.location = {
          lat: this.vido,
          lng: this.kinhdo
        }
        this.luuToaDoBanDo()
      }
    },
    setPolylines(polylines) {
      this.polylines = polylines
    }
    //#endregion
  }
}
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

.gm-style-iw>button {
  display: none !important;
}

.control-map {
  margin-top: 4px;
}
</style>
