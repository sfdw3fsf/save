<template>
  <div style="height: 800px;">
      <vue-simple-context-menu
        :elementId="menuMapId"
        :options="optionsMapMenu"
        ref="vueSimpleContextMenuMap"
        @option-clicked="menuMap_Clicked"
      />
        <div id="mapVNPTMatDoPhanTuDanCu" class="mapVNPTMatDoPhanTuDanCu"></div>
        <b-modal
          id="modal-bankinh"
          title="Nhập vào bán kình cần tìm (m)"
          @ok="handleOk"
          ok-title="Đồng ý"
          cancel-title="Hủy">
          <input type="number" class="form-control" v-model="bankinhTk" />
        </b-modal>
        <b-modal
          id="modal-whereareyou"
          title="Bạn đang ở vị trí"
          ok-title="Đồng ý"
          ok-only>
          <label>{{locationrightclickMap ? locationrightclickMap.lat : ''}}, {{locationrightclickMap ? locationrightclickMap.lng : ''}}</label>
        </b-modal>
  </div>
</template>
<script>
import VnptMap from '@vnpt/maps-onebss'
import html2canvas from 'html2canvas'
import EventBus from '@/utils/eventBus'
import VueSimpleContextMenu from 'vue-simple-context-menu'
const TOKEN = 'VNPT-f76bf568-b093-45cf-9761-f6339d3e7d9a'
export default {
  components: {'vue-simple-context-menu': VueSimpleContextMenu, html2canvas},
  name: 'BanDoVNPT',
  props: {
    position: null,
    diachi: null,
    mapTypeId: null,
    zoomDefault: null,
    markers: [],
    polylines: [],
    center: {},
    menuMapId: {
      type: String,
      default: 'menuMap'
    }
  },
  computed: {
    getPositionCenter () {
      return this.getNodes.length > 0 ? this.getNodes[0].position : this.center
    },
    getNodes () {
      return this.nodes
    },
    getlistPolyline () {
      return this.polylines
    },
    getRadiusObj () {
      return this.radiusObj
    }
  },
  destroyed () {
    EventBus.$off('IN_BANDO', this.inBando)
  },
  async created () {
    EventBus.$on('IN_BANDO', this.inBando)
  },
  data () {
    return {
      circleLayers: null,
      polylineLayers: [],
      popupInstance: null,
      basemapApi: null,
      mapVNPT: null,
      zoomLevel: 15,
      locationrightclickMap: null,
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
        disableDoubleClickZoom: true,
        draggable: true
      },
      bankinhTk: '',
      maxZoom: 20,
      map: null,
      nodes: [],
      nodesLanCan: [],
      positionCenter: this.position,
      optionsMapMenu:
      [
        {
          name: '<span class="icon text-main f20 one-trash"></span> Đây là đâu ?',
          slug: 'day_la_dau'
        },
        {
          name: '<span class="icon text-main f20 one-chuyencap"></span> Đo khoảng cách',
          slug: 'dokhoangcach'
        },
        {
          name: '<span class="icon text-main f20 one-taodiemuon"></span> Xóa phép đo',
          slug: 'xoaphepdo'
        }
      ],
      dsTBLanCan: [],
      radiusObj: [],
      infoContentNode: null,
      infoContentPolyline: null,
      infoPositionNode: null,
      infoPositionPolyline: null,
      infoOpenedNode: false,
      infoOpenedPolyline: false,
      infoOptionsPolyline: {
        pixelOffset: {
          width: 0,
          height: -10
        }
      },
      infoOptionsNode: {
        shouldFocus: false,
        pixelOffset: {
          width: 0,
          height: -10
        }
      },
      tongKhoangCach: 0,
      demClickDoKhoangCach: 0,
      batDoKhoangCach: false,
      pointDoKhoangCach: [],
      polylineDoKhoangCach: [],
      pointDoKhoangCachLayer: [],
      polylineDoKhoangCachLayer: [],
      pointLabelKhoangCachLayer: [],
      location: { lat: -1, lng: -1 },
    }
  },
  watch: {
    mapTypeId (val) {
      // if (val === 'ROADMAP') {
      //   // this.map.setMapTypeId(window.google.maps.MapTypeId.ROADMAP)
      // } else {
      //   // this.map.setMapTypeId(window.google.maps.MapTypeId.SATELLITE)
      // }
      if (this.mapTypeId === 'ROADMAP') {
        // this.mapTypeId = 'SATELLITE'
        this.mapVNPT.setStyle('https://maps.vnpt.vn/storage/vnptmaps/style/style_vetinh.json')
      } else {
        // this.mapTypeId = 'ROADMAP'
        this.mapVNPT.setStyle('https://maps.vnpt.vn/tileserver/styles/mapvnpt_v2/style.json')
      }
    },
    markers: {
      handler (val, oldVal) {
        this.getMarkersVNPT()
      },
      deep: true
    },
    polylines: {
      handler (val, oldVal) {
        this.getPolylinesVNPT()
      },
      deep: true
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
  async  mounted () {
    let vm = this
    let options = {
      showZoomControl: false,
      id: this.generateId()
    }
    vm.mapVNPT = VnptMap.initMap('mapVNPTMatDoPhanTuDanCu', options)
    vm.basemapApi = VnptMap.initBasemapApi()
    vm.mapVNPT.on('contextmenu', (e) => {
      this.rightclickMap(e)
    })
    vm.mapVNPT.on('click', (e) => {
      if (this.batDoKhoangCach) {
        this.clickDoKhoangCach(e)
      }
    })
  },
  methods: {
    rightclickMap (event) {
      console.log('🚀 ~ file: BanDoVNPT.vue:260 ~ rightclickMap ~ event:', event)
      this.locationrightclickMap = event.lngLat
      this.$refs.vueSimpleContextMenuMap && this.$refs.vueSimpleContextMenuMap.hideContextMenu()
      this.$refs.vueSimpleContextMenuMap.showMenu(event)
      const menu = document.getElementById(this.menuMapId)
      var left = event.point.x
      var top = event.point.y
      menu.style.left = left + 'px'
      menu.style.top = top + 'px'
      menu.position = 'fixed'
      menu.classList.add('menu-context')
    },
    getIcon1 () {
      // return '/static/icons/icon100x100/iconDiemUon.png'
    },
    getIcon2 () {
      // return require('./placeholder.svg')
    },
    getPolylinesVNPT () {
      for (let index = 0; index < this.polylines.length; index++) {
        let polyline = this.polylines[index]
        console.log('🚀 ~ file: BanDoVNPT.vue:338 ~ getPolylinesVNPT ~ polyline:', polyline)
        let polylineList = []
        for (let index = 0; index < polyline.path.length; index++) {
          let p = polyline.path[index]
          polylineList.push([p.lng, p.lat])
        }
        let dashArray = polyline.style.split('-')
        dashArray = dashArray.map(str => {
          return parseInt(str, 10)
        })
        let obj = {
          id: polyline.id,
          color: '#0036D3',
          opacity: 1,
          weight: 2,
          lineCap: 'round',
          dashArray: dashArray
        }
        let me = this
        let polylineLayer = VnptMap.initPolyline(polylineList, obj)
        console.log('🚀 ~ file: BanDoVNPT.vue:207 ~ getPolylinesVNPT ~ polylineLayer:', polylineLayer)
        polylineLayer.addLayerTo(me.mapVNPT)
        polylineLayer.on('mouseenter', (e) => {
          let lng = e.lngLat.lng
          let lat = e.lngLat.lat
          let content = '<b>' + polyline.label + '</b>'
          me.popupInstance && me.popupInstance.remove()
          me.popupInstance = VnptMap.initPopup([lng, lat], content, {
            offset: 20
          })
          me.popupInstance.addLayerTo(me.mapVNPT)
        })
        polylineLayer.on('mouseout', (e) => {
          me.popupInstance && me.popupInstance.remove()
        })
        this.polylineLayers.push(polylineLayer)
      }
    },
    clear () {
      for (let index = 0; index < this.nodes.length; index++) {
        let element = this.nodes[index]
        try {
          element && element.remove()
          element = null
        } catch (error) {
        }
      }
      for (let index = 0; index < this.nodesLanCan.length; index++) {
        let element = this.nodesLanCan[index]
        try {
          element && element.remove()
          element = null
        } catch (error) {
        }
      }
      for (let index = 0; index < this.polylineLayers.length; index++) {
        let element = this.polylineLayers[index]
        try {
          element && element.remove()
          element = null
        } catch (error) {

        }
      }
      this.polylineLayers = []
      this.nodesLanCan = []
      this.nodes = []
      try {
        this.circleLayers && this.circleLayers.remove()
        this.circleLayers = null
      } catch (error) {
      }
    },
    getMarkersVNPT () {
      for (let index = 0; index < this.markers.length; index++) {
        let marker = this.markers[index]
        let lng = isNaN(marker.KINHDO) ? 0 : marker.KINHDO
        let lat = isNaN(marker.VIDO) ? 0 : marker.VIDO
        let point = [lng, lat]
        let obj = {
          iconUrl: marker.LOAI === 1 ? this.getIcon2() : this.getIcon1(),
          id: marker.id,
          iconWidth: 30,
          iconHeight: 30,
          isDraggable: false
        }
        let pointLayer = VnptMap.initPoint(point, obj)
        pointLayer.id = marker.id
        pointLayer.setVisible(true)
        pointLayer.visible = true
        pointLayer.tooltipText = marker.tooltipText
        pointLayer.location = {lng: lng, lat: lat}
        pointLayer.LOAI = marker.LOAI
        let me = this
        pointLayer.on(this.mapVNPT, 'mouseenter', (e) => {
          let lng = e.coordinates[0]
          let lat = e.coordinates[1]
          let content = '<b>' + pointLayer.tooltipText + '</b>'
          me.popupInstance && me.popupInstance.remove()
          me.popupInstance = VnptMap.initPopup([lng, lat], content, {
            offset: 20
          })
          me.popupInstance.addLayerTo(me.mapVNPT)
        })
        pointLayer.on(this.mapVNPT, 'mouseout', (e) => {
          me.popupInstance && me.popupInstance.remove()
        })
        this.nodes.push(pointLayer)
        this.mapVNPT.addLayer(pointLayer)
      }
      if (this.nodes.length > 0) {
        this.mapVNPT.flyTo([this.nodes[0].location.lng, this.nodes[0].location.lat], 15)
      }
    },
    generateId () {
      const randomId = Math.random().toString(36).substr(2, 9) + '_' + Date.now().toString(36)
      return randomId
    },
    getMarkersInAreaVNPT () {
      for (let index = 0; index < this.nodesLanCan.length; index++) {
        let element = this.nodesLanCan[index]
        element && element.remove()
        element = null
      }
      this.nodesLanCan = []
      for (let index = 0; index < this.dsTBLanCan.length; index++) {
        let marker = this.dsTBLanCan[index]
        let point = [marker.KINHDO, marker.VIDO]
        let obj = {
          iconUrl: this.getIcon2(),
          id: marker.THUEBAO_ID,
          iconWidth: 30,
          iconHeight: 30,
          isDraggable: false
        }
        let pointLayer = VnptMap.initPoint(point, obj)
        pointLayer.id = marker.id
        pointLayer.setVisible(true)
        pointLayer.visible = true
        pointLayer.tooltipText = marker.TEN_TB
        pointLayer.location = {lng: marker.KINHDO, lat: marker.VIDO}
        let me = this
        pointLayer.on(this.mapVNPT, 'mouseenter', (e) => {
          let lng = e.coordinates[0]
          let lat = e.coordinates[1]
          let content = '<b>' + pointLayer.tooltipText + '</b>'
          me.popupInstance && me.popupInstance.remove()
          me.popupInstance = VnptMap.initPopup([lng, lat], content, {
            offset: 20
          })
          me.popupInstance.addLayerTo(me.mapVNPT)
        })
        pointLayer.on(this.mapVNPT, 'mouseout', (e) => {
          me.popupInstance && me.popupInstance.remove()
        })
        this.nodesLanCan.push(pointLayer)
        this.mapVNPT.addLayer(pointLayer)
      }
      if (this.nodesLanCan.length > 0) {
        console.log('🚀 ~ file: BanDoVNPT.vue:347 ~ getMarkersVNPT ~ this.nodes:', this.nodesLanCan)
        this.mapVNPT.flyTo([this.nodesLanCan[0].location.lng, this.nodesLanCan[0].location.lat], 15)
      }
    },
    searchLocation: function (searchAddressInput) {
      var re = new RegExp(/-?(\d*\.)?\d+,\s*-?(\d*\.)?\d+/)
      if (re.test(searchAddressInput)) {
        // let arr = searchAddressInput.split(',')
        // this.$refs.oneBSSGmap.$mapPromise.then((map) => {
        //   let location = {
        //     lat: parseFloat(arr[0]),
        //     lng: parseFloat(arr[1])
        //   }
        //   console.log('🚀 ~ file: BanDo.vue:99 ~ this.$refs.oneBSSGmap.$mapPromise.then ~ location:', location)
        //   const idx = this.nodes.findIndex(obj => obj.id === 'x')
        //   if (idx > -1) {
        //     this.nodes[idx].position.lat = location.lat
        //     this.nodes[idx].position.lng = location.lng
        //   } else {
        //     this.nodes.push({
        //       id: 'x',
        //       position: {
        //         lat: location.lat,
        //         lng: location.lng
        //       }
        //     })
        //   }
        //   let panPoint = new window.google.maps.LatLng(location.lat, location.lng)
        //   map.panTo(panPoint)
        //   this.$emit('dblclickMap', { lat: location.lat, lng: location.lng })
        // })
      } else {
        // this.$refs.oneBSSGmap.$mapPromise.then((map) => {
        //   var geocoder = new window.google.maps.Geocoder()
        //   geocoder.geocode({'address': searchAddressInput + ', Việt Nam'}, (results, status) => {
        //     console.log('🚀 ~ file: BanDo.vue:93 ~ geocoder.geocode ~ results:', results)
        //     console.log('searchLocation', status)
        //     if (status === 'OK') {
        //       var lat = results[0].geometry.location.lat()
        //       var lng = results[0].geometry.location.lng()
        //       const idx = this.nodes.findIndex(obj => obj.id === 'x')
        //       if (idx > -1) {
        //         this.nodes[idx].position.lat = lat
        //         this.nodes[idx].position.lng = lng
        //       } else {
        //         this.nodes.push({
        //           id: 'x',
        //           position: {
        //             lat: lat,
        //             lng: lng
        //           }
        //         })
        //       }
        //       var myLatlng = new window.google.maps.LatLng(lat, lng)
        //       this.map.panTo(myLatlng)
        //       this.$emit('dblclickMap', { lat: lat, lng: lng })
        //     } else {
        //       this.$root.toastError('Không xác định được vị trí')
        //     }
        //   })
        // })
      }
    },
    dragendPositionNode (event, node) {
      node.position.lat = event.latLng.lat()
      node.position.lng = event.latLng.lng()
      this.$emit('dblclickMap', { lat: event.latLng.lat(), lng: event.latLng.lng() })
    },
    dblclickMap (event) {
      const idx = this.nodes.findIndex(obj => obj.id === 'x')
      if (idx > -1) {
        this.nodes[idx].position.lat = event.latLng.lat()
        this.nodes[idx].position.lng = event.latLng.lng()
      } else {
        this.nodes.push({
          id: 'x',
          position: {
            lat: event.latLng.lat(),
            lng: event.latLng.lng()
          }
        })
      }
      this.$emit('dblclickMap', { lat: event.latLng.lat(), lng: event.latLng.lng() })
    },
    // fromLatLngToPixel: function (position) {
    //   var scale = Math.pow(2, this.map.getZoom())
    //   var proj = this.map.getProjection()
    //   var bounds = this.map.getBounds()
    //   var nw = proj.fromLatLngToPoint(
    //     new window.google.maps.LatLng(
    //       bounds.getNorthEast().lat(),
    //       bounds.getSouthWest().lng()
    //     ))
    //   var point = proj.fromLatLngToPoint(position)
    //   return new window.google.maps.Point(
    //     Math.floor((point.x - nw.x) * scale),
    //     Math.floor((point.y - nw.y) * scale))
    // },
    getLabel2 () {
      return ({'text': this.diachi !== null ? this.diachi : this.position.lat + ';' + this.position.lng, 'color': '#3225F6', 'fontSize': '12px', 'fontFamily': 'Arial', 'fontWeight': 'bold'})
    },
    reload () {
      // this.bounds = new window.google.maps.LatLngBounds()
      // this.map.setZoom(20)
      // this.map.setCenter(
      //   new window.google.maps.LatLng(
      //     this.location.lat,
      //     this.location.lng
      //   )
      // )
    },
    inBando(file) {
      HTMLCanvasElement.prototype.getContext = function (origFn) {
        return function (type, attribs) {
          attribs = attribs || {};
          attribs.preserveDrawingBuffer = true;
          return origFn.call(this, type, attribs);
        };
      }(HTMLCanvasElement.prototype.getContext);

      html2canvas($("#mapVNPTMatDoPhanTuDanCu.mapVNPTMatDoPhanTuDanCu")[0], {
        useCORS: true
      }).then((canvas) => {
        var imgSrc = canvas.toDataURL('image/png').replace('image/png', 'image/octet-stream')
        var link = document.createElement('a')
        link.href = imgSrc
        link.download = 'map.png'
        link.click()
      });
    },
    menuMap_Clicked (event) {
      console.log('menuMap_Clicked', event)
      switch (event.option.slug) {
        case 'day_la_dau':
          console.log('day_la_dau', this.locationrightclickMap.lat, this.locationrightclickMap.lng)
          this.$bvModal.show('modal-whereareyou')
          break
        case 'dokhoangcach':
          this.batDoKhoangCach = !this.batDoKhoangCach
          break
        case 'xoaphepdo':
          this.batDoKhoangCach = false
          break
      }
    },
    async handleOk () {
      try {
        this.loading(true)
        let rs = await this.$root.context.post('/web-cabman/danhmuc/toadothuebao/ds-thuebao-trong-bankinh', {
          lat: this.locationrightclickMap.lat,
          lng: this.locationrightclickMap.lng,
          bankinh: this.bankinhTk
        })
        this.dsTBLanCan = rs.data
        this.getMarkersInAreaVNPT()
        console.log('🚀 ~ file: BanDoVNPT.vue:555 ~ handleOk ~ this.dsTBLanCan:', this.dsTBLanCan)
        this.radiusObj = []
        this.radiusObj.push({
          position: { lat: this.locationrightclickMap.lat, lng: this.locationrightclickMap.lng },
          r: parseInt(this.bankinhTk)
        })
        const circleCenter = [this.locationrightclickMap.lng, this.locationrightclickMap.lat]
        const radiusValue = this.bankinhTk
        const circleLayer = VnptMap.initCircle(circleCenter, radiusValue, {
          id: 'circleLayer',
          fillColor: 'red',
          fillOpacity: 0.1
        })
        circleLayer.addLayerTo(this.mapVNPT)
        this.circleLayers = circleLayer
        this.bankinhTk = null
        this.$bvModal.hide('modal-bankinh')
      } catch (error) {

      } finally {
        this.loading(false)
      }
    },
    mouseoverPolyline (event, polyline) {
      // console.log('mouseoverPolyline', event, polyline)
      let distance = this.getDistance(polyline.path[1], polyline.path[0])
      polyline.mouseover = true
      this.infoPositionNode = event.latLng
      // this.infoContentNode = polyline.label
      this.infoContentNode = distance.toFixed(2) + ' m'
      this.infoOpenedNode = true
      this.infoOpenedPolyline = false
    },
    mouseoutPolyline (event, polyline) {
      polyline.mouseover = false
      this.infoOpenedNode = false
      this.infoOpenedPolyline = false
    },
    rad: function (x) {
      return x * Math.PI / 180
    },
    getDistance: function (p1, p2) {
      var R = 6378137 // Earth’s mean radius in meter
      var dLat = this.rad(p2.lat - p1.lat)
      var dLong = this.rad(p2.lng - p1.lng)
      var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(this.rad(p1.lat)) * Math.cos(this.rad(p2.lat)) *
        Math.sin(dLong / 2) * Math.sin(dLong / 2)
      var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
      var d = R * c
      return d // returns the distance in meter
    },
    mouseoverNode (event, node) {
      node.mouseover = true
      if (node.tooltipText) {
        this.infoPositionNode = event.latLng
        this.infoContentNode = node.tooltipText
        this.infoOpenedNode = true
        this.infoOpenedPolyline = false
      }
    },
    mouseoutNode (event, node) {
      node.mouseover = false
      this.infoOpenedNode = false
      this.infoOpenedPolyline = false
    },
    zoomIn() {
      if (this.zoomLevel === this.maxZoom) return
      this.zoomLevel = this.zoomLevel + 1
      this.mapVNPT.setZoom(this.zoomLevel)
    },
    zoomOut() {
      if (this.zoomLevel === 5) return
      this.zoomLevel = this.zoomLevel - 1
      this.mapVNPT.setZoom(this.zoomLevel)
    },
    clickDoKhoangCach: async function (event) {
      if (this.pointDoKhoangCach.length > 0) {
        let point = this.pointDoKhoangCach[this.pointDoKhoangCach.length - 1]
        if (point[0] === event.lngLat.lng && point[1] === event.lngLat.lat) {
          this.$toast.error('Vui lòng chọn điểm khác')
          return
        }
      }
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
      pointLayer.on(this.mapVNPT, 'click', (e) => {
        this.leftclickNode(e)
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
        var lat = coordinates[i][1] * Math.PI / 180
        var lon = coordinates[i][0] * Math.PI / 180
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
      return [lon * 180 / Math.PI, lat * 180 / Math.PI]
    },
    create_UUID() {
      var dt = new Date().getTime()
      var uuid = 'xxxxxxxx_xxxx_4xxx_yxxx_xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = (dt + Math.random() * 16) % 16 | 0
        dt = Math.floor(dt / 16)
        return (c === 'x' ? r : (r & 0x3) | 0x8).toString(16)
      })
      return uuid
    },
    leftclickNode(e) { },
    setLockMap: function (islock) {
      this.mapOptions.draggable = islock;
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
    setNullHeatmap() { }
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
  .gm-style-iw > button {
  display: none !important;
  }
  .control-map{
    margin-top: 4px;
  }
  /* #mapVNPTMatDoPhanTuDanCu {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
  } */

  .mapVNPTMatDoPhanTuDanCu {
    position: initial;
    height: 100%;
    width: 100%;
  }
  .map-canvas-box {
    overflow: hidden;
    height: calc(100%);
    width: 100%;
  }

</style>
