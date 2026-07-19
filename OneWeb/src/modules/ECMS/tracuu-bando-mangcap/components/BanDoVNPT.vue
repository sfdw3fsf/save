<template>
  <div style="height: 480px">
    <ModalQLKetCuoiExt modalID="modalQLKetCuoi" ref="ModalQLKetCuoiExt" />
    <vue-simple-context-menu elementId="menuMap" :options="optionsMapMenu" ref="vueSimpleContextMenuMap" @option-clicked="menuMap_Clicked" />
    <vue-simple-context-menu elementId="menuNodeTram" :options="optionsNodeTramMenu" ref="vueSimpleContextMenuNodeTram" @option-clicked="menuNodeTram_Clicked" />
    <div id="mapVNPTTraCuuMNV" class="mapVNPTTraCuuMNV"></div>
    <b-modal id="modal-whereareyou" title="Bạn đang ở vị trí" ok-title="Đồng ý" ok-only>
      <label>{{ locationrightclickMap != null ? locationrightclickMap.lat : '' }}, {{ locationrightclickMap != null ? locationrightclickMap.lng : '' }}</label>
    </b-modal>
    <DauNoiKC id="DauNoiKC" :ketcuoi_id="ketcuoi_id_selected"></DauNoiKC>
  </div>
</template>
<script>
import VnptMap from '@vnpt/maps-onebss'
import { gmapApi } from 'gmap-vue'
import VueSimpleContextMenu from 'vue-simple-context-menu'
import MeasureTool from 'measuretool-googlemaps-v3'
import ModalQLKetCuoiExt from '../../../CABMAN/BanDoMangCap/modal/QLKetCuoiExt'
import DauNoiKC from '../DauNoiKC'
const TOKEN = 'VNPT-f76bf568-b093-45cf-9761-f6339d3e7d9a'
export default {
  components: { 'vue-simple-context-menu': VueSimpleContextMenu, MeasureTool, ModalQLKetCuoiExt, DauNoiKC },
  name: 'BanDo',
  props: {
    markers: Array,
    radiusObj: Object,
    center: Object
  },
  watch: {
    center: {
      handler(value) {
        this.mapVNPT.flyTo([value.lng, value.lat], 15)
        this.setMarkLocation([value.lng, value.lat])
        setTimeout(() => {
          if(this.circleLayer) {
            this.circleLayer.setCenter([value.lng, value.lat])
          }
        }, 100)
      },
      deep: true 
    },
    radiusObj: function (value) {
      const valueChange = value.r
      if (this.circleLayer) {
        this.circleLayer.setRadius(valueChange)
      }
    },
    markers: function (value) {
      this.markers = value
      this.drawMarkers(this.markers)
    },
    batDoKhoangCach: function (val) {
      if (val) {
        console.log('Bắt đầu đo khoảng cách')
      } else {
        console.log('Kết thúc đo khoảng cách')
        this.removePointDoKhoangCach()
      }
    }
  },
  computed: {
    google: gmapApi,
    getPositionCenter() {
      return this.center
    },
    getRadiusObj() {
      return this.radiusObj
    },
    getPathPolyline() {
      return this.pathPolyline
    },
    getMarkers() {
      let nodes = []
      for (let index = 0; index < this.markers.length; index++) {
        const marker = this.markers[index]
        if (marker.KETCUOI_ID === 'KETCUOI_1') {
          nodes.push({
            id: marker.KETCUOI_ID,
            position: {
              lat: marker.VIDO,
              lng: marker.KINHDO
            },
            title: marker.KYHIEU,
            icon: null
          })
        } else {
          nodes.push({
            id: marker.KETCUOI_ID,
            position: {
              lat: marker.VIDO,
              lng: marker.KINHDO
            },
            title: marker.KYHIEU,
            tooltipText: marker.STYLE.ToolTip,
            icon: {
              url: marker.STYLE.ImageUrl,
              size: new window.google.maps.Size(marker.STYLE.WIDTH, marker.STYLE.HEIGHT),
              scaledSize: new window.google.maps.Size(marker.STYLE.WIDTH, marker.STYLE.HEIGHT),
              labelOrigin: { x: 16, y: 40 },
              anchor: new window.google.maps.Point(15, 15)
            }
          })
        }
      }
      return nodes
    }
  },
  async created() {
    await this.$gmapApiPromiseLazy()
  },
  data() {
    return {
      //
      tongKhoangCach: 0,
      demClickDoKhoangCach: 0,
      batDoKhoangCach: false,
      pointDoKhoangCach: [],
      polylineDoKhoangCach: [],
      pointDoKhoangCachLayer: [],
      polylineDoKhoangCachLayer: [],
      pointLabelKhoangCachLayer: [],
      //
      markerLayers: [],
      circleLayer: null,
      pointLayers: [],
      ketcuoi_id_selected: null,
      // variable of polyline
      pathPolyline: [],
      infoOptionsRoute: {
        pixelOffset: {
          width: 0,
          height: -15
        }
      },
      basemapApi: null,
      infoPositionRoute: null,
      infoOpenedRoute: true,
      infoContentRoute: null,

      optionsMapMenu: [
        {
          name: '<span class="icon text-main f20 one-chuyencap"></span> Đo khoảng cách',
          slug: 'dokhoangcach'
        },
        {
          name: '<span class="icon text-main f20 one-taodiemuon"></span> Xóa phép đo',
          slug: 'xoaphepdo'
        },
        {
          name: '<span class="icon text-main f20 one-taodiemuon"></span> Đây là đâu',
          slug: 'day_la_dau'
        }
      ],
      optionsNodeTramMenu: [
        {
          name: '<span class="icon text-main f20 one-daunoi"></span> Xem đấu nối bên trong',
          slug: 'xem_dau_noi_thiet_bi'
        }
      ],
      zoomLevel: 17,
      mapOptions: {
        zoomControl: false,
        scaleControl: true,
        disableDefaultUi: true,
        streetViewControl: false,
        mapTypeControl: false,
        fullscreenControl: false,
        maxZoom: this.maxZoom,
        draggableCursor: 'default',
        draggingCursor: 'pointer',
        disableDoubleClickZoom: true
      },
      node: null,
      nodes: [],
      locationrightclickMap: null,
      location: { lat: 16.07511849179389, lng: 108.2093338123915 },
      maxZoom: 20,
      map: null,
      infoOptionsNode: {
        shouldFocus: false,
        pixelOffset: {
          width: 0,
          height: -10
        }
      },
      infoContentNode: null,
      infoPositionNode: null,
      infoOpenedNode: false,
      point: {}
    }
  },
  async mounted() {
    let vm = this
    let options = {
      showZoomControl: false,
      showFullscreen: false,
      id: this.generateId(),
      maxZoom: 24
    }
    vm.mapVNPT = VnptMap.initMap('mapVNPTTraCuuMNV', options)
    vm.mapVNPT.on('load', (e) => {
      vm.basemapApi = VnptMap.initBasemapApi()
      vm.geofency = VnptMap.initGeofency()
      setTimeout(() => {
        this.mapVNPT.updateSized()
      }, 10)
      this.mapVNPT.setCenter(this.position)
      vm.mapVNPT.on('click', (e) => {
        if (this.batDoKhoangCach) {
          this.clickDoKhoangCach(e)
        } 
      })
       vm.mapVNPT.on('dblclick', (e) => {
          this.dblclickMap(e)
      })
      this.mapVNPT.on('contextmenu', (e) => {
        if (this.point.x !== e.originalEvent.x || this.point.y !== e.originalEvent.y) {
          this.rightclickMap(e)
        } else {
          this.point.x = 0
          this.point.y = 0
        }
      })
      this.setMarkLocation()
      let lat = parseFloat(this.center.lat)
      let lng = parseFloat(this.center.lng)
      const circleLayer = VnptMap.initCircle([lng, lat], this.radiusObj.r, {
        id: 'circleLayer',
        fillColor: 'orange',
        fillOpacity: 0.1,
        color: 'orange',
        opacity: 0.5
      })
      circleLayer.addLayerTo(vm.mapVNPT)
      this.circleLayer = circleLayer
      this.mapVNPT.flyTo([lng, lat], 15)
    })
  },
  methods: {
    drawMarkers(listMarker) {
      this.refreshMap()
      for (let index = 0; index < listMarker.length; index++) {
        const marker = listMarker[index]
        if (marker.KETCUOI_ID === 'KETCUOI_1') {
          const point = [marker.KINHDO, marker.VIDO]
          const obj = {
            iconUrl: null,
            id: marker.KETCUOI_ID,
            iconWidth: marker.STYLE.WIDTH,
            iconHeight: marker.STYLE.HEIGHT,
            isDraggable: false
          }
          let markerLayer = VnptMap.initPoint(point, obj)
          markerLayer.on(this.mapVNPT, 'contextmenu', (e) => {
            this.point.x = e.pointEvent.x
            this.point.y = e.pointEvent.y
            this.rightclickMarker(e, marker)
          })
          markerLayer.addPopup(marker.STYLE.ToolTip)
          markerLayer.addLayerTo(this.mapVNPT)
          this.markerLayers.push(markerLayer)
        } else {
          const point = [marker.KINHDO, marker.VIDO]
          const obj = {
            iconUrl: marker.STYLE.ImageUrl,
            id: marker.KETCUOI_ID,
            iconWidth: marker.STYLE.WIDTH,
            iconHeight: marker.STYLE.HEIGHT,
            isDraggable: false
          }
          let markerLayer = VnptMap.initPoint(point, obj)
          markerLayer.on(this.mapVNPT, 'contextmenu', (e) => {
            this.rightclickMarker(e, marker)
          })
          markerLayer.addPopup(marker.STYLE.ToolTip)
          markerLayer.addLayerTo(this.mapVNPT)
          this.markerLayers.push(markerLayer)
        }
      }
    },
    refreshMap() {
      for (let index = 0; index < this.markerLayers.length; index++) {
        let element = this.markerLayers[index]
        try {
          element && element.remove()
          element = null
        } catch (error) {}
      }
      this.markerLayers = []
    },
    setMarkLocation(position) {
      if (position) {
        this.pointLayers.forEach((item) => {
          item.remove()
        })
        this.pointLayers = []
        const obj = {
          iconUrl: this.getMarkerIcon2().url,
          id: 'POINT' + this.generateId(),
          iconWidth: this.getMarkerIcon2().size.width,
          iconHeight: this.getMarkerIcon2().size.height,
          isDraggable: false,
          isMarker: true
        }
        let pointLayer = VnptMap.initPoint(position, obj)
        pointLayer.addLayerTo(this.mapVNPT)
        this.pointLayers.push(pointLayer)
      } else {
        const obj = {
          iconUrl: this.getMarkerIcon2().url,
          id: 'POINT' + this.generateId(),
          iconWidth: this.getMarkerIcon2().size.width,
          iconHeight: this.getMarkerIcon2().size.height,
          isDraggable: false
        }
        let pointLayer = VnptMap.initPoint(this.center, obj)
        pointLayer.addLayerTo(this.mapVNPT)
        this.pointLayers.push(pointLayer)
      }
    },
    resetPoint() {
      if (this.pointLayers.length > 0) {
        for (let index = 0; index < this.pointLayers.length; index++) {
          let element = this.pointLayers[index]
          try {
            element && element?.remove()
            element = null
          } catch (error) {}
        }
      }
    },
    generateId() {
      const randomId = Math.random().toString(36).substr(2, 9) + '_' + Date.now().toString(36)
      return randomId
    },
    getMarkerIcon2() {
      return {
        labelOrigin: { x: 20, y: -10 },
        url: require('./placeholder.svg'),
        size: { width: 40, height: 40, f: 'px', b: 'px' },
        scaledSize: { width: 40, height: 40, f: 'px', b: 'px' }
      }
    },
    RouteOptions() {
      try {
        var opt = { strokeOpacity: 1, geodesic: true }
        opt = {
          ...opt,
          strokeWeight: this.Width
        }
        opt = {
          ...opt,
          strokeColor: '#FF0000'
        }
        return opt
      } catch (error) {
        console.log('RouteOptions', error)
      }
    },
    mouseOverRoute(event, Route) {
      this.infoPositionRoute = event.latLng
      this.infoContentRoute = '<b>' + this.haversine_distance(Route[0], Route[1]).toFixed(2) + ' m' + '</b>'
      this.infoOpenedNode = false
      this.infoOpenedRoute = true
    },
    mouseOutRoute(event, Route) {
      this.infoOpenedNode = false
      this.infoOpenedRoute = false
    },
    haversine_distance(mk1, mk2) {
      let R = 3958.8 // Radius of the Earth in miles
      let rlat1 = mk1.lat * (Math.PI / 180) // Convert degrees to radians
      let rlat2 = mk2.lat * (Math.PI / 180) // Convert degrees to radians
      let difflat = rlat2 - rlat1 // Radian difference (latitudes)
      let difflon = (mk2.lng - mk1.lng) * (Math.PI / 180) // Radian difference (longitudes)

      let d = 2 * R * Math.asin(Math.sqrt(Math.sin(difflat / 2) * Math.sin(difflat / 2) + Math.cos(rlat1) * Math.cos(rlat2) * Math.sin(difflon / 2) * Math.sin(difflon / 2)))
      return d * 1609.344 // convert to meters
    },
    mouseoverNode(event, node) {
      this.infoPositionNode = event.latLng
      this.infoContentNode = node.tooltipText
      this.infoOpenedNode = true
      this.node = node
    },
    mouseoutNode(event, node) {
      this.infoOpenedNode = false
    },
    setmapType(type) {
      if (type === 'ROADMAP') {
        this.map.setMapTypeId(window.google.maps.MapTypeId.ROADMAP)
      } else {
        this.map.setMapTypeId(window.google.maps.MapTypeId.SATELLITE)
      }
    },
    reload() {
      this.bounds = new window.google.maps.LatLngBounds()
      this.map.setZoom(20)
      this.map.setCenter(new window.google.maps.LatLng(this.location.lat, this.location.lng))
    },
    getMarkerIcon() {
      return {
        url: require('../pin.png'),
        size: { width: 20, height: 20, f: 'px', b: 'px' },
        scaledSize: { width: 20, height: 20, f: 'px', b: 'px' }
      }
    },
    dblclickMap(event) {
      try {
        console.log(event, 'check event')
        this.radiusObj.position.lat = event.lngLat.lat
        this.radiusObj.position.lng = event.lngLat.lng
        this.$emit('dblclickMap', { lat: event.lngLat.lat, lng: event.lngLat.lng })
      } catch (error) {
        console.log('🚀 ~ file: BanDo.vue:329 ~ dblclickMap ~ error:', error)
      }
    },
    dblclickMarker(event) {
      console.log('dbclick event', this.node)
      var dataQLKetCuoi = {}
      dataQLKetCuoi.cmd = 'ViewKetcuoi'
      dataQLKetCuoi.data = {
        ketcuoi_id: this.node.id
      }
      this.$refs.ModalQLKetCuoiExt.show(dataQLKetCuoi).then((result) => {
        if (result.ok) {
          console.log('Sửa kết cuối', result)
        } else {
        }
      })
    },
    clickMarker() {
      try {
        console.log('🚀 ~ file: BanDo.vue:345 ~ clickMarker ~ this.pathPolyline:')
        this.pathPolyline = [this.radiusObj.position, this.node.position]
      } catch (error) {
        console.log('🚀 ~ file: BanDo.vue:355 ~ clickMarker ~ error:', error)
      }
    },

    fromLatLngToPixel: function (position) {
      var scale = Math.pow(2, this.map.getZoom())
      var proj = this.map.getProjection()
      var bounds = this.map.getBounds()
      var nw = proj.fromLatLngToPoint(new window.google.maps.LatLng(bounds.getNorthEast().lat(), bounds.getSouthWest().lng()))
      var point = proj.fromLatLngToPoint(position)
      return new window.google.maps.Point(Math.floor((point.x - nw.x) * scale), Math.floor((point.y - nw.y) * scale))
    },
    rightclickMap(event) {
      console.log(event, '1')
      this.$refs.vueSimpleContextMenuNodeTram && this.$refs.vueSimpleContextMenuNodeTram.hideContextMenu()
      this.$refs.vueSimpleContextMenuMap && this.$refs.vueSimpleContextMenuMap.hideContextMenu()
      this.$refs.vueSimpleContextMenuMap.showMenu(event)
      const menu = document.getElementById('menuMap')
      var left = event.point.x
      var top = event.point.y
      menu.style.left = left + 'px'
      menu.style.top = top + 'px'
      menu.position = 'fixed'
      menu.classList.add('menu-context')
      this.locationrightclickMap = {
        lat: event.lngLat.lat,
        lng: event.lngLat.lng
      }
    },
    rightclickMarker(event, node) {
      this.node = {
        id: node.KETCUOI_ID
      }
      this.point.x = event.pointEvent.x
      this.point.y = event.pointEvent.y
      this.$refs.vueSimpleContextMenuMap && this.$refs.vueSimpleContextMenuMap.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeTram && this.$refs.vueSimpleContextMenuNodeTram.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeTram.showMenu(event)
      const menu = document.getElementById('menuNodeTram')
      var left = event.pointEvent.layerX
      var top = event.pointEvent.layerY
      menu.style.left = left + 'px'
      menu.style.top = top + 'px'
      menu.position = 'fixed'
      menu.classList.add('menu-context')
    },
    create_UUID() {
      var dt = new Date().getTime()
      var uuid = 'xxxxxxxx_xxxx_4xxx_yxxx_xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = (dt + Math.random() * 16) % 16 | 0
        dt = Math.floor(dt / 16)
        return (c === 'x' ? r : (r & 0x3) | 0x8).toString(16)
      })
      return uuid
    },
    menuMap_Clicked(event) {
      console.log('menuMap_Clicked', event)
      switch (event.option.slug) {
        case 'dokhoangcach':
          this.batDoKhoangCach = !this.batDoKhoangCach
          break
        case 'xoaphepdo':
          this.batDoKhoangCach = false
          break
        case 'day_la_dau':
          this.isHere()
          break
      }
    },
    isHere: function () {
      this.basemapApi.getAddressByLonLat(this.locationrightclickMap, TOKEN, (rs) => {
        if (rs.status) {
          const popup = VnptMap.initPopup([this.locationrightclickMap.lng, this.locationrightclickMap.lat], rs.result.address)
          popup.addLayerTo(this.mapVNPT)
        } else {
          console.error(rs.message)
        }
      })
    },
    menuNodeTram_Clicked(event) {
      switch (event.option.slug) {
        case 'xem_dau_noi_thiet_bi':
          this.ketcuoi_id_selected = this.node.id
          this.$bvModal.show('DauNoiKC')
          break
      }
    },
    geolocate: function () {
      navigator.geolocation.getCurrentPosition((position) => {
        this.center = {
          lat: position.coords.latitude,
          lng: position.coords.longitude
        }
      })
    },
    clickDoKhoangCach: async function (event) {
      this.demClickDoKhoangCach = this.demClickDoKhoangCach + 1
      const point = [event.lngLat.lng, event.lngLat.lat]
      const obj = {
        iconUrl: '/static/icons/dot_do_khoang_cach.png',
        id: 'NODE_' + this.create_UUID(),
        iconWidth: 20,
        iconHeight: 20,
        isDraggable: false
      }
      // add point to map
      let pointLayer = VnptMap.initPoint(point, obj)
      pointLayer.on(this.mapVNPT, 'mouseenter', (e) => {
        let lng = e.coordinates[0]
        let lat = e.coordinates[1]
        let content = '[' + lat + ',' + lng + ']'
        this.popupInstance && this.popupInstance.remove()
        this.popupInstance = VnptMap.initPopup([lng, lat], content, {
          offset: 20
        })
        this.popupInstance.addLayerTo(this.mapVNPT)
      })
      pointLayer.on(this.mapVNPT, 'mouseout', (e) => {
        this.popupInstance && this.popupInstance.remove()
      })
      pointLayer.addLayerTo(this.mapVNPT)
      this.pointDoKhoangCach.push(point)
      this.pointDoKhoangCachLayer.push(pointLayer)
      if (this.demClickDoKhoangCach > 1) {
        let polyline = VnptMap.initPolyline([this.pointDoKhoangCach[this.pointDoKhoangCach.length - 2], this.pointDoKhoangCach[this.pointDoKhoangCach.length - 1]], {
          id: 'POLYLINE_' + this.create_UUID(),
          color: '#000000',
          opacity: 1,
          weight: 3,
          lineCap: 'round',
          dashArray: [1, 1]
        })
        polyline.addLayerTo(this.mapVNPT)
        this.polylineDoKhoangCachLayer.push(polyline)
        this.tongKhoangCach = this.tongKhoangCach + polyline.getLength()
        let content = polyline.getLength().toFixed(2) + 'km'
        let popupLatLng = polyline.getCoordinates()
        let midPoint = this.getMidPoint(popupLatLng.geometry.coordinates)
        let nodeLabel = VnptMap.initPoint(midPoint, {
          iconUrl: '',
          id: 'NODE_' + this.create_UUID(),
          iconWidth: 20,
          iconHeight: 20,
          isDraggable: false,
          className: 'vnptmaps-pointlabel-mid'
        })
        nodeLabel.setLabel(content)
        nodeLabel.setLabelVisible(true)
        nodeLabel.addLayerTo(this.mapVNPT)
        let popupTongKhoangCach = VnptMap.initPopup(point, this.tongKhoangCach.toFixed(2) + 'km', {
          offset: 20
        })
        popupTongKhoangCach.addLayerTo(this.mapVNPT)
        this.pointLabelKhoangCachLayer.push(nodeLabel)
      }
    },
    removePointDoKhoangCach: async function () {
      for (let index = 0; index < this.pointDoKhoangCachLayer.length; index++) {
        let element = this.pointDoKhoangCachLayer[index]
        try {
          element && element.remove()
          element = null
        } catch (error) {}
      }
      for (let index = 0; index < this.polylineDoKhoangCachLayer.length; index++) {
        let element = this.polylineDoKhoangCachLayer[index]
        try {
          element && element.remove()
          element = null
        } catch (error) {}
      }
      for (let index = 0; index < this.pointLabelKhoangCachLayer.length; index++) {
        let element = this.pointLabelKhoangCachLayer[index]
        try {
          element && element.remove()
          element = null
        } catch (error) {}
      }
      this.pointDoKhoangCach = []
      this.pointDoKhoangCachLayer = []
      this.polylineDoKhoangCachLayer = []
      this.pointLabelKhoangCachLayer = []
      this.tongKhoangCach = 0
      this.demClickDoKhoangCach = 0
    },
    getMidPoint: function (coordinates) {
      var x = 0.0
      var y = 0.0
      var z = 0.0
      for (var i = 0; i < coordinates.length; i++) {
        var lat = (coordinates[i][1] * Math.PI) / 180
        var lon = (coordinates[i][0] * Math.PI) / 180
        x += Math.cos(lat) * Math.cos(lon)
        y += Math.cos(lat) * Math.sin(lon)
        z += Math.sin(lat)
      }
      x = x / coordinates.length
      y = y / coordinates.length
      z = z / coordinates.length
      var lon = Math.atan2(y, x)
      var hyp = Math.sqrt(x * x + y * y)
      var lat = Math.atan2(z, hyp)
      return [(lon * 180) / Math.PI, (lat * 180) / Math.PI]
    }
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
  font-family: 'Roboto', Arial;
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
.gm-style-iw {
}
.control-map {
  margin-top: 4px;
}

.mapVNPTTraCuuMNV {
  position: initial;
  height: 100%;
  width: 100%;
  overflow: hidden;
}

.map-canvas-box {
  overflow: hidden;
  height: calc(100%);
  width: 100%;
}
.vnptmaps-pointlabel {
  position: fixed;
  top: 22px;
  color: black;
  font-size: 11px;
  font-family: Arial;
  flex-wrap: nowrap;
  white-space: nowrap !important;
  text-shadow: 0.5px 0.5px white;
}
.vnptmaps-pointlabel-mid {
  color: black;
  font-size: 14px;
  font-weight: bold;
}
</style>
