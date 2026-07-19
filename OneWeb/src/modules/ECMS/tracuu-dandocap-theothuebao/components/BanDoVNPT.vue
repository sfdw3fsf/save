<template>
  <div style="height: 600px;">
      <vue-simple-context-menu
        :elementId="menuMapId"
        :options="optionsMapMenu"
        ref="vueSimpleContextMenuMap"
        @option-clicked="menuMap_Clicked"
      />
      <div id="mapVNPTXetDuyetPhanTuMangHoDanThuThap" class="mapVNPTXetDuyetPhanTuMangHoDanThuThap"></div>
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
  name: 'BanDo',
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
  },
  destroyed () {
    EventBus.$off('IN_BANDO', this.inBando)
  },
  async created () {
    EventBus.$on('IN_BANDO', this.inBando)
  },
  data () {
    return {
      pointSearch: null,
      basemapApi: null,
      mapVNPT2: null,
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
      bankinhTk: '',
      maxZoom: 20,
      map: null,
      nodes: [],
      positionCenter: this.position,
      optionsMapMenu:
      [
        {
          name: '<span class="icon text-main f20 one-chuyencap"></span> Đo khoảng cách',
          slug: 'dokhoangcach'
        },
        {
          name: '<span class="icon text-main f20 one-taodiemuon"></span> Xóa phép đo',
          slug: 'xoaphepdo'
        },
        {
          name: '<span class="icon text-main f20 one-daochieu"></span> Tìm lân cận',
          slug: 'tim_dia_diem'
        },
        {
          name: '<span class="icon text-main f20 one-trash"></span> Đây là đâu ?',
          slug: 'day_la_dau'
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
      }
    }
  },
  watch: {
    mapTypeId (val) {
      if (val === 'ROADMAP') {
        // this.map.setMapTypeId(window.google.maps.MapTypeId.ROADMAP)
      } else {
        // this.map.setMapTypeId(window.google.maps.MapTypeId.SATELLITE)
      }
    }
  },
  async  mounted () {
    let vm = this
    let options = {
      showZoomControl: false,
      id: this.generateId()
    }
    vm.mapVNPT2 = VnptMap.initMap('mapVNPTXetDuyetPhanTuMangHoDanThuThap', options)
    vm.mapVNPT2.on('load', (e) => {
      vm.basemapApi = VnptMap.initBasemapApi()
      setTimeout(() => vm.mapVNPT2.updateSized(), 1)
      this.getMarkersVNPT()
    })
    vm.mapVNPT2.on('contextmenu', (e) => {
      // this.rightclickMap(e)
    })
    vm.mapVNPT2.on('click', (e) => {
      if (e.originalEvent.detail === 2) {
        this.dblclickMap(e)
      }
    })
  },
  methods: {
    dblclickMap (event) {
      console.log('🚀 ~ file: BanDoVNPT.vue:157 ~ dblclickMap ~ event:', event.lngLat)
      let coord = [parseFloat(event.lngLat.lng), parseFloat(event.lngLat.lat)]
      this.pointSearch && this.pointSearch.remove()
      this.pointSearch = VnptMap.initPoint(coord, {
        id: 'NODE_' + this.generateId(),
        iconUrl: require('../position.png'),
        iconWidth: 30,
        iconHeight: 30,
        isDraggable: true
      })
      this.pointSearch.onDrag('drag', (e) => {
        this.$emit('dblclickMap', { lat: e.coordinates.lat, lng: e.coordinates.lng })
      })
      this.pointSearch.addLayerTo(this.mapVNPT2)
      this.mapVNPT2.flyTo(coord, 15)
      this.$emit('dblclickMap', { lat: parseFloat(event.lngLat.lat), lng: parseFloat(event.lngLat.lng) })
    },
    getIcon1 () {
      return '/static/icons/icon100x100/iconDiemUon.png'
    },
    getIcon2 () {
      return require('./placeholder.svg')
    },
    getMarkersVNPT () {
      for (let index = 0; index < this.markers.length; index++) {
        let marker = this.markers[index]
        console.log('🚀 ~ file: BanDo.vue:191 ~ getMarkersVNPT ~ marker:', marker)
        let point = [marker.KINHDO, marker.VIDO]
        let obj = {
          iconUrl: marker.LOAI === 1 ? this.getIcon2() : this.getIcon1(),
          id: marker.id + '_' + this.generateId(),
          iconWidth: 30,
          iconHeight: 30,
          isDraggable: false
        }
        let pointLayer = VnptMap.initPoint(point, obj)
        pointLayer.id = marker.id
        pointLayer.setVisible(true)
        pointLayer.visible = true
        pointLayer.tooltipText = marker.tooltipText
        pointLayer.location = {lng: marker.KINHDO, lat: marker.VIDO}
        pointLayer.LOAI = marker.LOAI
        let me = this
        pointLayer.on(this.mapVNPT2, 'mouseenter', (e) => {
          let lng = e.coordinates[0]
          let lat = e.coordinates[1]
          let content = '<b>' + pointLayer.tooltipText + '</b>'
          me.popupInstance && me.popupInstance.remove()
          me.popupInstance = VnptMap.initPopup([lng, lat], content, {
            offset: 20
          })
          me.popupInstance.addLayerTo(me.mapVNPT2)
        })
        pointLayer.on(this.mapVNPT2, 'mouseout', (e) => {
          me.popupInstance && me.popupInstance.remove()
        })
        this.nodes.push(pointLayer)
        this.mapVNPT2.addLayer(pointLayer)
      }
      if (this.nodes.length > 0) {
        this.mapVNPT2.flyTo([this.nodes[0].location.lng, this.nodes[0].location.lat], 15)
      }
    },
    generateId () {
      const randomId = Math.random().toString(36).substr(2, 9) + '_' + Date.now().toString(36)
      return randomId
    },
    searchLocation: function (searchAddressInput) {
      var re = new RegExp(/-?(\d*\.)?\d+;\s*-?(\d*\.)?\d+/)
      if (re.test(searchAddressInput)) {
        let arr = searchAddressInput.split(';')
        let coord = [parseFloat(arr[1]), parseFloat(arr[0])]
        this.pointSearch && this.pointSearch.remove()
        this.pointSearch = VnptMap.initPoint(coord, {
          id: 'NODE_' + this.generateId(),
          iconUrl: require('../position.png'),
          iconWidth: 30,
          iconHeight: 30,
          isDraggable: true
        })
        this.pointSearch.onDrag('drag', (e) => {
          this.$emit('dblclickMap', { lat: e.coordinates.lat, lng: e.coordinates.lng })
        })
        this.pointSearch.addLayerTo(this.mapVNPT2)
        this.mapVNPT2.flyTo(coord, 15)
        this.$emit('dblclickMap', { lat: parseFloat(arr[0]), lng: parseFloat(arr[1]) })
      } else {
        this.basemapApi.getLonLatByAddress(searchAddressInput, TOKEN, (evt) => {
          if (evt.status) {
            let result
            for (let i in evt.result) {
              if (evt.result[i].address === searchAddressInput) result = evt.result[i]
            }
            if (!result) result = evt.result[0]
            {
              const coord = [result.long, result.lat]
              let content =
                      '<b>+ Địa chỉ: </b>' +
                      result.address +
                      '<br> <b>+ Kinh độ: </b>' +
                      result.long +
                      '<br> <b>+ Vĩ độ: </b>' +
                      result.lat
              const popupAddress = VnptMap.initPopup(coord, content)
              popupAddress.addLayerTo(this.mapVNPT2)
              this.pointSearch && this.pointSearch.remove()
              this.pointSearch = VnptMap.initPoint(coord, {
                id: 'NODE_' + this.generateId(),
                iconUrl: require('../position.png'),
                iconWidth: 30,
                iconHeight: 30,
                isDraggable: true
              })
              this.pointSearch.addLayerTo(this.mapVNPT2)
              this.mapVNPT2.flyTo(coord, 12)
              this.$emit('dblclickMap', { lat: result.lat, lng: result.long })
            }
          }
        })
      }
    },
    dragendPositionNode (event, node) {
      node.position.lat = event.latLng.lat()
      node.position.lng = event.latLng.lng()
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
    rightclickMap (event) {
      // this.locationrightclickMap = event.latLng
      // this.$refs.vueSimpleContextMenuMap && this.$refs.vueSimpleContextMenuMap.hideContextMenu()
      // this.$refs.vueSimpleContextMenuMap.showMenu(event)
      // const menu = document.getElementById(this.menuMapId)
      // let pixelOffset = new window.google.maps.Point(10, -5)
      // let mapDiv = this.map.getDiv()
      // var mousePosition = this.fromLatLngToPixel(event.latLng)
      // var mapSize = new window.google.maps.Size(mapDiv.offsetWidth, mapDiv.offsetHeight)
      // var menuSize = new window.google.maps.Size(menu.offsetWidth, menu.offsetHeight)
      // var left = mousePosition.x
      // var top = mousePosition.y
      // if (mousePosition.x > mapSize.width - menuSize.width - pixelOffset.x) {
      //   left = left - menuSize.width - pixelOffset.x
      // } else {
      //   left += pixelOffset.x
      // }
      // if (mousePosition.y > mapSize.height - menuSize.height - pixelOffset.y) {
      //   top = top - menuSize.height - pixelOffset.y
      // } else {
      //   top += pixelOffset.y
      // }
      // menu.style.left = left + 'px'
      // menu.style.top = top + 'px'
      // menu.position = 'fixed'
      // menu.classList.add('menu-context')
    },
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
    inBando (file) {
      var fin = document.getElementById('mapVNPTCapNhatToaDoThueBaoChonToaDo')
      html2canvas(fin, {
        useCORS: true,
        allowTaint: false,
        ignoreElements: (node) => {
          return node.nodeName === 'IFRAME'
        }}).then(function (canvas) {
        var imgSrc = canvas.toDataURL('image/png').replace('image/png', 'image/octet-stream')
        var link = document.createElement('a')
        link.href = imgSrc
        link.download = file + '.png'
        link.click()
      })
    },
    menuMap_Clicked (event) {
      console.log('menuMap_Clicked', event)
      switch (event.option.slug) {
        case 'dokhoangcach':
          this.measureTool && this.measureTool.start()
          break
        case 'xoaphepdo':
          this.measureTool && this.measureTool.end()
          break
        case 'tim_dia_diem':
          this.$bvModal.show('modal-bankinh')
          break
        case 'day_la_dau':
          console.log('day_la_dau', this.locationrightclickMap.lat(), this.locationrightclickMap.lng())
          this.$bvModal.show('modal-whereareyou')
          break
      }
    },
    async handleOk () {
      try {
        this.loading(true)
        let rs = await this.$root.context.post('/web-cabman/danhmuc/toadothuebao/ds-thuebao-trong-bankinh', {
          lat: this.locationrightclickMap.lat(),
          lng: this.locationrightclickMap.lng(),
          bankinh: this.bankinhTk
        })
        this.dsTBLanCan = rs.data
        this.radiusObj = []
        this.radiusObj.push({
          position: { lat: this.locationrightclickMap.lat(), lng: this.locationrightclickMap.lng() },
          r: parseInt(this.bankinhTk)
        })
        this.bankinhTk = null
        this.$bvModal.hide('modal-bankinh')
      } catch (error) {

      } finally {
        this.loading(false)
      }
    },
    mouseoverPolyline (event, polyline) {
      let distance = this.getDistance(polyline.path[1], polyline.path[0])
      polyline.mouseover = true
      this.infoPositionNode = event.latLng
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
      var R = 6378137
      var dLat = this.rad(p2.lat - p1.lat)
      var dLong = this.rad(p2.lng - p1.lng)
      var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(this.rad(p1.lat)) * Math.cos(this.rad(p2.lat)) *
        Math.sin(dLong / 2) * Math.sin(dLong / 2)
      var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
      var d = R * c
      return d
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
  .gm-style-iw {

  }
  .control-map{
    margin-top: 4px;
  }
  .mapVNPTXetDuyetPhanTuMangHoDanThuThap {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
  }
</style>
