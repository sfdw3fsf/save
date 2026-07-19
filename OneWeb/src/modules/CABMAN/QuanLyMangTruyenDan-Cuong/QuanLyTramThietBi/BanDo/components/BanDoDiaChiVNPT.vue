<template>
  <div :style="{ height: height }">
    <vue-simple-context-menu elementId="menuMap" :options="optionsMapMenu" ref="vueSimpleContextMenuMap" @option-clicked="menuMap_Clicked" />
    <div id="mapVNPTDiaChi" class="mapVNPTDiaChi"></div>
    <b-modal id="modal-bankinh" title="Nhập vào bán kình cần tìm" @ok="handleOk" ok-title="Đồng ý" cancel-title="Hủy">
      <input type="number" class="form-control" v-model="bankinhTk" />
    </b-modal>
    <b-modal id="modal-whereareyou" title="Bạn đang ở vị trí" ok-title="Đồng ý" ok-only>
      <label>{{ locationrightclickMap ? locationrightclickMap.lat() : '' }}, {{ locationrightclickMap ? locationrightclickMap.lng() : '' }}</label>
    </b-modal>
  </div>
</template>
<script>
import Vue from 'vue'
import VnptMap from '@vnpt/maps-onebss'
import html2canvas from 'html2canvas'
import EventBus from '@/utils/eventBus'
import VueSimpleContextMenu from 'vue-simple-context-menu'
import MeasureTool from 'measuretool-googlemaps-v3'
import DynamicMarker from '@/modules/CABMAN/BanDoMangCap/components2/DynamicMarker'
const DynamicMarkerConstructor = Vue.extend(DynamicMarker)
const TOKEN = 'VNPT-f76bf568-b093-45cf-9761-f6339d3e7d9a'

export default {
  components: { 'vue-simple-context-menu': VueSimpleContextMenu, MeasureTool, html2canvas, VnptMap },
  name: 'BanDo',
  props: {
    position: null,
    diachi: null,
    mapTypeId: null,
    zoomDefault: null,
    height: {
      type: String,
      default: '800px'
    }
  },
  watch: {
    position(val) {
      console.log('position watch position watch', val)
    }
  },
  computed: {
    getPositionCenter() {
      return this.position
    },
    getNodes() {
      return this.nodes
    },
    getMapTypeId(){
      return this.mapTypeId
    }
  },
  destroyed() {
    EventBus.$off('IN_BANDO', this.inBando)
  },
  async created() {
    EventBus.$on('IN_BANDO', this.inBando)
  },
  data() {
    return {
      zoomLevel: this.zoomDefault,
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
        disableDoubleClickZoom: true
      },
      iconUrl: '/static/icons/location.png',
      bankinhTk: '',
      maxZoom: 20,
      map: null,
      // mapTypeId: 'ROADMAP',
      nodes: [],
      pointLayers : [],
      pointPopupLayers : [],
      pointSearchsLayers: [],
      pointDiemUonLayers: [],
      polylineLayers: [],
      point: null,
      basemapApi: null,
      location: { lat: -1, lng: -1 },
      positionCenter: this.position,
      optionsMapMenu: [
        {
          name: '<span class="icon text-main f20 one-chuyencap"></span> Đo khoảng cách',
          slug: 'dokhoangcach'
        },
        {
          name: '<span class="icon text-main f20 one-taodiemuon"></span> Xóa phép đo',
          slug: 'xoaphepdo'
        },
        // {
        //   name: '<span class="icon text-main f20 one-daochieu"></span> Tìm lân cận',
        //   slug: 'tim_dia_diem'
        // },
        {
          name: '<span class="icon text-main f20 one-trash"></span> Đây là đâu ?',
          slug: 'day_la_dau'
        }
      ]
    }
  },
  watch: {
    searchDiaDiem: {
      handler: function(val) {
        this.location = {
          lat: val.geometry.location.lat(),
          lng: val.geometry.location.lng()
        }
      },
      deep: true
    },
  },
  async mounted() {
    let vm = this
    let options = {
      showZoomControl: false,
      showFullscreen: false,
      id: this.generateId() + '_' + 'VNPT',
      maxZoom: 24
    }
    vm.map = VnptMap.initMap('mapVNPTDiaChi', options)
    vm.map.on('load', (e) => {
      vm.basemapApi = VnptMap.initBasemapApi()
      vm.geofency = VnptMap.initGeofency()
      setTimeout(() => this.map.updateSized(), 10)
    })
    vm.map.setZoom(18)
    vm.map.on('dblclick', (e) => {
      this.dblclickMap(e)
    })
    vm.map.on('contextmenu', (e) => {
      if (this.point.x !== e.originalEvent.x || this.point.y !== e.originalEvent.y) {
        this.rightclickMap(e)
      } else {
        this.point.x = 0
        this.point.y = 0
      }
    })

    if (this.positionCenter) {
      this.$emit('dblclickMap', { lat: this.positionCenter.lat, lng: this.positionCenter.lng })
      this.setPoint(this.positionCenter.lng, this.positionCenter.lat)
    }
  },
  methods: {
    setPoint: function(lng, lat) {
      this.lng = lng
      this.lat = lat
      let coord = [this.lng, this.lat]
      if (this.point == null) {
        this.point = VnptMap.initPoint(coord, {
          iconUrl: this.getMarkerIcon2().url, //this.iconUrl,
          id: 'Point' + '_' + this.generateId(),
          isDraggable: false,
          iconWidth: this.getMarkerIcon2().size.width,
          iconHeight: this.getMarkerIcon2().size.height
        })
        this.point.addLayerTo(this.map)
        this.pointLayers.push(this.point);
        let content = 'Kinh độ: ' + lng + ' , ' + 'Vĩ độ: ' + lat
        let popupAddress = VnptMap.initPopup(coord, content)
        popupAddress.addLayerTo(this.map);
        this.pointPopupLayers.push(popupAddress);
        this.point.onDrag('dragend', (e) => {
          this.lat = e.coordinates.lat
          this.lng = e.coordinates.lng
        })
      } else {
        this.point.setData(coord)
      }
      this.map.setCenter(coord)
      this.map.setZoom(18)
    },
    resetPoint : function () {
      if (this.pointLayers.length > 0) {
        for (let index = 0; index < this.pointLayers.length; index++) {
          let element = this.pointLayers[index]
          try {
            element && element?.remove()
            element = null
          } catch (error) {}
        }
      }
      if (this.pointPopupLayers.length > 0) {
        for (let index = 0; index < this.pointPopupLayers.length; index++) {
          let element = this.pointPopupLayers[index]
          try {
            element && element?.remove()
            element = null
          } catch (error) {}
        }
      }
    },
    thaydoiMapType() {
      if (this.mapTypeId === 'ROADMAP') {
        this.map.setStyle('https://maps.vnpt.vn/storage/vnptmaps/style/style_vetinh.json');
      } else {
        this.map.setStyle('https://maps.vnpt.vn/tileserver/styles/mapvnpt_v2/style.json');
      }
    },
    generateId() {
      const randomId =
        Math.random()
          .toString(36)
          .substr(2, 9) +
        '_' +
        Date.now().toString(36)
      return randomId
    },
    searchLocation: function(searchAddressInput) {
      // console.log('🚀 ~ file: BanDoVNPT.vue:561 ~ this.basemapApi.getLonLatByAddress ~ evt:', searchAddressInput)
      this.basemapApi.getLonLatByAddress(searchAddressInput, TOKEN, (evt) => {
        // console.log('🚀 ~ file: BanDoVNPT.vue:561 ~ this.basemapApi.getLonLatByAddress ~ evt:', evt)
        if (evt.status) {
          let result
          for (let i in evt.result) {
            if (evt.result[i].address === searchAddressInput) result = evt.result[i]
          }
          if (!result) result = evt.result[0]
          {
            const coord = [result.long, result.lat]
            const idx = this.nodes.findIndex((obj) => obj.id === 'x')
            if (idx > -1) {
              this.nodes[idx].position.lat = result.lat
              this.nodes[idx].position.lng = result.long
            } else {
              this.nodes.push({
                id: 'x',
                position: {
                  lat: result.lat,
                  lng: result.long
                }
              })
            }

            let content = '<b>+ Địa chỉ: </b>' + result.address + '<br> <b>+ Kinh độ: </b>' + result.long + '<br> <b>+ Vĩ độ: </b>' + result.lat
            const popupAddress = VnptMap.initPopup(coord, content)
            popupAddress.addLayerTo(this.map)
            const point = VnptMap.initPoint(coord, {
              id: 'NODE_' + this.create_UUID(),
              iconUrl: this.getMarkerIcon2().url,
              iconWidth: this.getMarkerIcon2().size.width,
              iconHeight: this.getMarkerIcon2().size.height,
              isDraggable: false
            })
            point.addLayerTo(this.map)
            this.pointSearchsLayers.push(point)
            this.map.setCenter(coord)
            this.map.setZoom(12)
            this.$emit('dblclickMap', { lat: result.lat, lng: result.long })
          }
        } else {
          this.$root.toastError('Không xác định được vị trí')
        }
      })
    },
    dragendPositionNode(event, node) {
      node.position.lat = event.lngLat.lat
      node.position.lng = event.lngLat.lng
      this.$emit('dblclickMap', { lat: event.lngLat.lat, lng: event.lngLat.lng })
    },
    rePoint(lng, lat) {//vẽ lại point khi double lick
      this.lng = lng
      this.lat = lat
      let coord = [this.lng, this.lat]
      if (this.point == null) {
        this.point = VnptMap.initPoint(coord, {
          iconUrl: this.getMarkerIcon2().url, //this.iconUrl,
          id: 'Point' + '_' + this.generateId(),
          isDraggable: false,
          iconWidth: this.getMarkerIcon2().size.width,
          iconHeight: this.getMarkerIcon2().size.height
        })
        this.point.addLayerTo(this.map)
        this.pointLayers.push(this.point);
        let content = 'Kinh độ: ' + lng + ' , ' + 'Vĩ độ: ' + lat
        let popupAddress = VnptMap.initPopup(coord, content)
        popupAddress.addLayerTo(this.map);
        this.pointPopupLayers.push(popupAddress);
        this.point.onDrag('dragend', (e) => {
          this.lat = e.coordinates.lat
          this.lng = e.coordinates.lng
        })
      } else {
        this.point.setData(coord)
      }
      this.map.flyTo(coord, 20)
    },
    dblclickMap(event) {
      // console.log('event', event)
      const idx = this.nodes.findIndex((obj) => obj.id === 'x');
      console.log('idx', idx);
      if (idx > -1) {
        this.nodes[idx].position.lat = event.lngLat.lat;
        this.nodes[idx].position.lng = event.lngLat.lng;
      } else {
        this.nodes.push({
          id: 'x',
          position: {
            lat: event.lngLat.lat,
            lng: event.lngLat.lng
          }
        })
      }
      this.point = null;
      this.resetPoint();
      this.rePoint(event.lngLat.lng, event.lngLat.lat);
      this.$emit('dblclickMap', { lat: event.lngLat.lat, lng: event.lngLat.lng });
    },
    rightclickMap(event) {
      // console.log('rightclickMap', event)
      this.locationrightclickMap = event.lngLat
      this.$refs.vueSimpleContextMenuMap && this.$refs.vueSimpleContextMenuMap.hideContextMenu()
      this.$refs.vueSimpleContextMenuMap.showMenu(event)
      const menu = document.getElementById('menuMap')
      var left = event.point.x
      var top = event.point.y
      menu.style.left = left + 'px'
      menu.style.top = top + 'px'
      menu.position = 'fixed'
      menu.classList.add('menu-context')
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
    getMarkerIcon() {
      return {
        url: require('../position.png'),
        size: { width: 40, height: 40, f: 'px', b: 'px' },
        scaledSize: { width: 40, height: 40, f: 'px', b: 'px' }
      }
    },
    getMarkerIcon2() {
      return {
        labelOrigin: { x: 20, y: -10 },
        url: require('../placeholder.svg'),
        size: { width: 40, height: 40, f: 'px', b: 'px' },
        scaledSize: { width: 40, height: 40, f: 'px', b: 'px' }
      }
    },
    getLabel2() {
      return { text: this.diachi !== null ? this.diachi : this.position.lat + ';' + this.position.lng, color: '#3225F6', fontSize: '12px', fontFamily: 'Arial', fontWeight: 'bold' }
    },
    reload() {
      // this.bounds = new window.google.maps.LatLngBounds()
      const coord = [this.location.lng, this.location.lat]
      this.map.setZoom(20)
      this.map.setCenter(coord)
    },
    inBando(file) {
      var fin = document.getElementById('map')
      console.log('inBando', fin)
      html2canvas(fin, {
        useCORS: true,
        allowTaint: false,
        ignoreElements: (node) => {
          return node.nodeName === 'IFRAME'
        }
      }).then(function(canvas) {
        var imgSrc = canvas.toDataURL('image/png').replace('image/png', 'image/octet-stream')
        var link = document.createElement('a')
        link.href = imgSrc
        link.download = file + '.png'
        link.click()
      })
    },
    menuMap_Clicked(event) {
      console.log('menuMap_Clicked', event)
      switch (event.option.slug) {
        case 'dokhoangcach':
          this.measureTool && this.measureTool.start()
          break
        case 'xoaphepdo':
          this.measureTool && this.measureTool.end()
          break
        // case 'tim_dia_diem':
        //   // this.showSearchDiaDiem = !this.showSearchDiaDiem
        //   this.$bvModal.show('modal-bankinh');
        //   break
        case 'day_la_dau':
          console.log('day_la_dau', this.locationrightclickMap.lat(), this.locationrightclickMap.lng())
          this.$bvModal.show('modal-whereareyou')
          break
      }
    },
    handleOk() {
      console.log('ban kinh tim kiem ', this.bankinhTk)
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

.mapVNPTDiaChi {
  position: relative;
  overflow: hidden;
  height: calc(100%);
  width: 100%;
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
  text-shadow: -1px -1px 0px #dfdfdf, 0px -1px 0px #dfdfdf, 1px -1px 0px #dfdfdf, -1px 0px 0px #dfdfdf, 1px 0px 0px #dfdfdf, -1px 1px 0px #dfdfdf, 0px 1px 0px #dfdfdf, 1px 1px 0px #dfdfdf;
}
</style>
