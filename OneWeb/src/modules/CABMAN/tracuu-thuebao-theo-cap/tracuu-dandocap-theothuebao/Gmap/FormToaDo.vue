<template>
  <div class="map-canvas-box">
    <div class="map-canvas-main">
      <vue-simple-context-menu elementId="menuPolyline1" :options="optionsPolylineMenu"
        ref="vueSimpleContextMenuPolyline1" @option-clicked="menuPolyline1_Clicked" />

      <vue-simple-context-menu elementId="menuMap1" :options="optionsMapMenu" ref="vueSimpleContextMenuMap1"
        z-index="10000" @option-clicked="menuMap1_Clicked" />
      <vue-simple-context-menu elementId="menuNode1" :options="optionsNodeMenu" ref="vueSimpleContextMenuNode1"
        @option-clicked="menuNode1_Clicked" />
      <div id="modalMapVNPT" class="modalMapVNPT"></div>
      <div id="map" class="map"></div>
      <div class="map-position bg-white">
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="info-row marb0">
              <div class="key w30 nowrap">Kinh độ</div>
              <div class="value">
                <input type="text" v-model="positionDefault.lng" class="form-control bg-white text-primary" readonly />
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row marb0">
              <div class="key w30 nowrap">Vĩ độ</div>
              <div class="value">
                <input type="text" v-model="positionDefault.lat" class="form-control bg-white text-primary" readonly />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="map-header">
      <div class="map-header-right">
        <div class="input-group input-group-search">
          <div class="input-text">
            <!-- Thêm ref="searchInput" vào phần tử input -->
            <input type="text" class="form-control" v-model="txtSearch" @keyup.enter="btnSearch_Click" id="searchInput" />
          </div>
          <div class="input-addon">
            <button class="btn" @click="btnSearch_Click">
              <span class="icon one-search"></span>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import VnptMap from '@vnpt/maps-onebss'
import GraphNode from '../structures/GraphNode'
import GraphPolyline from '../structures/GraphPolyline'
import VueSimpleContextMenu from 'vue-simple-context-menu'
import EventBus from '@/utils/eventBus'
// const TOKEN = 'VNPT-f76bf568-b093-45cf-9761-f6339d3e7d9a'
import html2canvas from "html2canvas";

export default {
  components: { 'vue-simple-context-menu': VueSimpleContextMenu, html2canvas },
  name: 'VNPTMap',
  props: {},
  computed: {
    getPositionCenter() {
      return this.location
    },
    getlistNode() {
      return this.nodes.filter((n) => n.visible === true)
    },
    getlistPolyline() {
      return this.polylines
    },
    optionsPolylineMenu() {
      var menu = []
      if (this.activePolyline) {
        if (!this.activePolyline.modified) {
          return [...this.optionsPolyline]
        } else {
          return [...this.optionsPolyline2]
        }
      } else return [...this.optionsPolyline]
    }
  },
  async created() {
    // await this.getThongTinBanDo()
  },
  data() {
    return {
      point: null,
      pointLayers: [],
      pointDiemUonLayers: [],
      showModal: false,
      inputValue: '',
      tongKhoangCach: 0,
      demClickDoKhoangCach: 0,
      pointSearchsLayers: [],
      polylineLayers: [],
      batDoKhoangCach: false,
      pointDoKhoangCach: [],
      polylineDoKhoangCach: [],
      pointDoKhoangCachLayer: [],
      polylineDoKhoangCachLayer: [],
      pointLabelKhoangCachLayer: [],
      positionDefault: {
        lat: 0,
        lng: 0
      },
      txtSearch: null,
      searchAddressInput: this.positionDefault,
      zoomLevel: 15,
      mapVNPT: null,
      token: null,
      iconUrl: "/static/icons/location.png",
      draggable: false,
      geofency: null,
      basemapApi: null,
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
        mapTypeId: 'ROADMAP',
        draggableCursor: 'default',
        draggingCursor: 'pointer'
      },
      nodes: [],
      polylines: [],
      location: { lat: -1, lng: -1 },
      mouselatLng: {},
      maxZoom: 25,
      map: null,
      measureTool: null,
      loadingMap: true,
      searchDiaDiem: null,
      activePolyline: null,
      showSearchDiaDiem: false,
      thongtinbando: {},
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
      optionsNodeMenu: [
        {
          name: '<span class="icon text-main f20 one-taocap"></span> Liên kết tới cột tiếp theo',
          slug: 'lienketcot'
        },
        {
          name: '<span class="icon text-main f20 one-taokn"></span> Liên kết tới cột thuộc tuyến khác',
          slug: 'lienketcotkhac'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 one-trash"></span> Xóa cột',
          slug: 'xoacot'
        },
        {
          name: '<span class="icon text-main f20 -ap icon-info"></span> Thuộc tính',
          slug: 'thuoctinh'
        }
      ],
      optionsMapMenu: [
        {
          name: '<span class="icon text-main f20 one-daucuoi"></span> Làm tươi',
          slug: 'lamtuoi'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 one-circle-question"></span> Đây là đâu ?',
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
      ],
      optionsPolyline: [
        {
          name: '<span class="icon text-main f16 nc-icon-glyph ui-1_circle-remove"></span> Hủy liên kết',
          slug: 'huylienket'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 one-daochieu"></span> Đảo chiều liên kết',
          slug: 'daochieu'
        }
      ],
      optionsPolyline2: [
        {
          name: '<span class="icon text-main f16 nc-icon-glyph ui-1_circle-remove"></span> Hủy liên kết',
          slug: 'huylienket'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 one-daochieu"></span> Đảo chiều liên kết',
          slug: 'daochieu'
        }
      ],
      ischonCot: false,
      atHere: null,
      isGhiToaDo: false,
      isGhimToaDo: false,
      cotChuaToaDo: Object
    }
  },
  watch: {
    polylines: {
      handler(val, oldVal) {
        this.getPolylinesVNPT()
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
      handler: function (val) { },
      deep: true
    },
    isMapChanged: function (val) {
      if (val) {
        this.mapOptions.mapTypeId = 'SATELLITE'
      } else {
        this.mapOptions.mapTypeId = 'ROADMAP'
      }
    },
    batDoKhoangCach: function (val) {
      if (val) {
        console.log('Bắt đầu đo khoảng cách')
      } else {
        console.log('Kết thúc đo khoảng cách')
        this.removePointDoKhoangCach()
      }
    },
  },
  async mounted() {
    let tt = await this.getThongTinBanDo();
    if (tt == null || tt == undefined) {
      this.token = null;
    }
    else {
      this.token = tt.TOKEN;

      this.mapVNPT = VnptMap.initMap("modalMapVNPT");
      this.mapVNPT.on('click', (e) => {
        this.batDoKhoangCach ? this.clickDoKhoangCach(e) : this.clickMap(e)
      })
      this.mapVNPT.on('contextmenu', (e) => {
        if (this.point.x !== e.originalEvent.x || this.point.y !== e.originalEvent.y) {
          this.rightclickMap(e)
        } else {
          this.point.x = 0
          this.point.y = 0
        }
      })
      setTimeout(() => this.mapVNPT.updateSized(), 10);
    }
    if (this.token == null) {
      this.$root.toastError("Lỗi truy cập đến thông tin bản đồ");
    }
    // let vm = this
    // let options = {
    //   showZoomControl: false,
    //   showFullscreen: false,
    //   id: this.generateId(),
    //   maxZoom: 24
    // }
    // vm.mapVNPT = VnptMap.initMap('modalMapVNPT')
    this.mapVNPT.on('load', (e) => {
      this.basemapApi = VnptMap.initBasemapApi()
      this.geofency = VnptMap.initGeofency()
      setTimeout(() => {
        this.mapVNPT.updateSized()
      }, 10)
      EventBus.$on('ghiToaDo', (args) => {
        this.isGhiToaDo = true
        this.beChuaToaDo = args
      })
      EventBus.$on('ghimToaDo', (args) => {
        this.isGhimToaDo = true
      })
    })
    // vm.mapVNPT.on('click', (e) => {
    //   this.clickMap(e)
    // })
    // vm.mapVNPT.on('contextmenu', (e) => {
    //   if (this.point.x !== e.originalEvent.x || this.point.y !== e.originalEvent.y) {
    //     this.rightclickMap(e)
    //   } else {
    //     this.point.x = 0
    //     this.point.y = 0
    //   }
    // })
    // EventBus.$on('ghiToaDo', (args) => {
    //   vm.isGhiToaDo = true
    //   vm.cotChuaToaDo = args
    // })
    // EventBus.$on('ghimToaDo', (args) => {
    //   vm.isGhimToaDo = true
    // })
    // //log node
    // console.log('nodes', this.nodes)
  },
  destroyed() {
    EventBus.$off('ghiToaDo')
    EventBus.$off('ghimToaDo')
  },
  methods: {
    bindData: function (kinhDo, viDo, diaChi) {
      this.setPoint(kinhDo, viDo, diaChi);
    },
    printMap: function () {
      this.$root.showLoading(true); // Show loading

      HTMLCanvasElement.prototype.getContext = function (origFn) {
        return function (type, attribs) {
          attribs = attribs || {};
          attribs.preserveDrawingBuffer = true;
          return origFn.call(this, type, attribs);
        };
      }(HTMLCanvasElement.prototype.getContext);

      html2canvas($("#modalMapVNPT.modalMapVNPT")[0], {
        useCORS: true
      }).then((canvas) => {
        let imgData = canvas.toDataURL("image/png");
        var newWindow = window.open("");
        $(newWindow.document.body)
          .html(
            "<img src=" +
            imgData +
            " style='width: " +
            canvas.width +
            "px'></img>"
          )
          .ready(function () {
            newWindow.focus();
            newWindow.print();
            this.$root.showLoading(false); // Hide loading after print
          }.bind(this)); // Bind 'this' to the function scope
      }).catch((error) => {
        console.error("Error during print:", error);
        this.$root.showLoading(false); // Hide loading in case of error
      });
    },
    generateId() {
      const randomId = Math.random().toString(36).substr(2, 9) + '_' + Date.now().toString(36)
      return randomId
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
    removePointDoKhoangCach: async function () {
      for (let index = 0; index < this.pointDoKhoangCachLayer.length; index++) {
        let element = this.pointDoKhoangCachLayer[index]
        try {
          element && element.remove()
          element = null
        } catch (error) { }
      }
      for (let index = 0; index < this.polylineDoKhoangCachLayer.length; index++) {
        let element = this.polylineDoKhoangCachLayer[index]
        try {
          element && element.remove()
          element = null
        } catch (error) { }
      }
      for (let index = 0; index < this.pointLabelKhoangCachLayer.length; index++) {
        let element = this.pointLabelKhoangCachLayer[index]
        try {
          element && element.remove()
          element = null
        } catch (error) { }
      }
      this.pointDoKhoangCach = []
      this.pointDoKhoangCachLayer = []
      this.polylineDoKhoangCachLayer = []
      this.pointLabelKhoangCachLayer = []
      this.tongKhoangCach = 0
      this.demClickDoKhoangCach = 0
    },
    reload() {
      console.log(this.mapVNPT)
      this.bounds = this.mapVNPT.getBounds()
      this.mapVNPT.setZoom(15)
      this.mapVNPT.setCenter([this.location.lng, this.location.lat])
    },
    getThongTinBanDo: async function () {
      try {
        let rs = await this.$root.context.post("/web-cabman/quanlyhatang/getThongTinBanDo");
        return rs.data;
      }
      catch (e) {
        this.$root.toastError("Lỗi truy cập đến thông tin bản đồ");
        return null;
      }
    },
    btnSearch_Click(arg) {
      try {
        let value = this.txtSearch
        var re = new RegExp(/-?(\d*\.)?\d+[;,]\s*-?(\d*\.)?\d+/)
        if (re.test(value)) {
          let arr = value.split(/[;,]/)
          const point = [arr[1], arr[0]]
          const obj = {
            iconUrl: 'https://maps.vnpt.vn/v2/assets/images/markers/click-marker.gif',
            id: 'NODE_' + this.create_UUID(),
            iconWidth: 40,
            iconHeight: 40,
            isDraggable: false
          }
          let pointLayer = VnptMap.initPoint(point, obj)
          let me = this
          pointLayer.on(this.mapVNPT, 'mouseenter', (e) => {
            let lng = e.coordinates[0]
            let lat = e.coordinates[1]
            let content = '[' + lat + ',' + lng + ']'
            me.popupInstance && me.popupInstance.remove()
            me.popupInstance = VnptMap.initPopup([lng, lat], content, {
              offset: 20
            })
            me.popupInstance.addLayerTo(me.mapVNPT)
          })
          pointLayer.on(this.mapVNPT, 'mouseout', (e) => {
            me.popupInstance && me.popupInstance.remove()
          })
          this.mapVNPT.addLayer(pointLayer)
          this.pointSearchsLayers.push(pointLayer)
          this.positionDefault.lat = arr[0]
          this.positionDefault.lng = arr[1]
          this.mapVNPT.setCenter([arr[1], arr[0]])
          this.mapVNPT.setZoom(12)
        } else {
          this.basemapApi.getLonLatByAddress(value, this.token, (evt) => {
            if (evt.status) {
              let result
              for (let i in evt.result) {
                if (evt.result[i].address === value) result = evt.result[i]
              }
              if (!result) result = evt.result[0]
              {
                const coord = [result.long, result.lat]
                let content = '<b>+ Địa chỉ: </b>' + result.address + '<br> <b>+ Kinh độ: </b>' + result.long + '<br> <b>+ Vĩ độ: </b>' + result.lat
                const popupAddress = VnptMap.initPopup(coord, content)
                popupAddress.addLayerTo(this.mapVNPT)
                const point = VnptMap.initPoint(coord, {
                  id: 'NODE_' + this.create_UUID(),
                  iconUrl: 'https://maps.vnpt.vn/v2/assets/images/markers/click-marker.gif',
                  iconWidth: 40,
                  iconHeight: 40,
                  isDraggable: false
                })
                point.addLayerTo(this.mapVNPT)
                this.pointSearchsLayers.push(point)
                this.positionDefault.lat = result.lat
                this.positionDefault.lng = result.long
                this.mapVNPT.setCenter(coord)
                this.mapVNPT.setZoom(12)
              }
            }
          })
        }
      } catch (error) {
        this.pointSearchsLayers = []
        // hiển thị thông báo lỗi từ error ra toas
        this.$root.toastError(error.message)
      }
    },
    setPoint: function (lng, lat, address) {
      this.positionDefault.lat = lat
      this.positionDefault.lng = lng
      let coord = [this.positionDefault.lng, this.positionDefault.lat]
      if (this.point == null) {
        this.point = VnptMap.initPoint(coord, {
          iconUrl: this.iconUrl,
          id: "Point",
          isDraggable: true,
          iconWidth: 48,
          iconHeight: 48
        });
        this.point.addLayerTo(this.mapVNPT);
        this.point.onDrag('dragend', (e) => {
          this.positionDefault.lat = e.coordinates.lat;
          this.positionDefault.lng = e.coordinates.lng;
        });
      }
      else {
        this.point.setData(coord);
      }
      this.mapVNPT.flyTo(coord, 13);
      this.point.addPopup(address);
    },
    setZoomMap: function (step) {
      let zoomCurrent = this.mapVNPT.getZoom()
      if (step == 1) {
        this.mapVNPT.setZoom(zoomCurrent + 1)
      } else if (step == -1) {
        this.mapVNPT.setZoom(zoomCurrent - 1)
      } else {
        this.mapVNPT.setZoom(this.zoomLevel)
      }
    },
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
      var nw = proj.fromLatLngToPoint(new window.google.maps.LatLng(bounds.getNorthEast().lat(), bounds.getSouthWest().lng()))
      var point = proj.fromLatLngToPoint(position)
      return new window.google.maps.Point(Math.floor((point.x - nw.x) * scale), Math.floor((point.y - nw.y) * scale))
    },
    rightclickPolyline(event, polyline) {
      this.diemuonLatlng = event.lngLat
      this.activePolyline = polyline
      this.mouselatLng.lat = event.lngLat.lat
      this.mouselatLng.lng = event.lngLat.lng
      this.$refs.vueSimpleContextMenuMap1 && this.$refs.vueSimpleContextMenuMap1.hideContextMenu()
      this.$refs.vueSimpleContextMenuNode1 && this.$refs.vueSimpleContextMenuNode1.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolyline1 && this.$refs.vueSimpleContextMenuPolyline1.hideContextMenu()
      this.$refs.vueSimpleContextMenuNode1DiemUon && this.$refs.vueSimpleContextMenuNode1DiemUon.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolyline1.showMenu(event, polyline)
      const menu = document.getElementById('menuPolyline1')
      var left = event.point.x
      var top = event.point.y
      menu.style.left = left + 'px'
      menu.style.top = top + 'px'
      menu.position = 'fixed'
      menu.classList.add('menu-context')
    },
    rightclickMap(event) {
      this.locationrightclickMap = event.lngLat
      this.$refs.vueSimpleContextMenuMap1 && this.$refs.vueSimpleContextMenuMap1.hideContextMenu()
      this.$refs.vueSimpleContextMenuNode1 && this.$refs.vueSimpleContextMenuNode1.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolyline1 && this.$refs.vueSimpleContextMenuPolyline1.hideContextMenu()
      this.$refs.vueSimpleContextMenuNode1DiemUon && this.$refs.vueSimpleContextMenuNode1DiemUon.hideContextMenu()
      this.$refs.vueSimpleContextMenuMap1.showMenu(event)
      const menu = document.getElementById('menuMap1')
      var left = event.point.x
      var top = event.point.y
      menu.style.left = left + 'px'
      menu.style.top = top + 'px'
      menu.position = 'fixed'
      menu.classList.add('menu-context')
    },
    rightclickNode(event, node) {
      this.$refs.vueSimpleContextMenuMap1 && this.$refs.vueSimpleContextMenuMap1.hideContextMenu()
      this.$refs.vueSimpleContextMenuNode1DiemUon && this.$refs.vueSimpleContextMenuNode1DiemUon.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolyline1 && this.$refs.vueSimpleContextMenuPolyline1.hideContextMenu()
      this.$refs.vueSimpleContextMenuNode1.showMenu(event, node)
      const menu = document.getElementById('menuNode1')
      var left = event.pointEvent.layerX
      var top = event.pointEvent.layerY
      menu.style.left = left + 'px'
      menu.style.top = top + 'px'
      menu.position = 'fixed'
      menu.classList.add('menu-context')
    },
    leftclickNode(event, node) {
      if (this.ischonCot) {
        let cot_id = node.id.split('_')[1]
        this.$emit('getcotSau', cot_id)
      }
      this.ischonCot = false
    },
    dblclickPolyline($event, polyline) {
      let obj = Object
      try {
        if (polyline.id.includes('LINK_COT')) {
          let value = polyline.id.split('_')
          obj = {
            cot_id: value[2],
            cotsau_id: value[4]
          }
          this.$emit('Kc2Cot', obj)
        }
      } catch (e) { }
    },
    insertAt(arr, index, newItem) {
      return [...arr.slice(0, index), newItem, ...arr.slice(index)]
    },
    menuNode1_Clicked(event) {
      let type_action
      switch (event.option.slug) {
        case 'lienketcot':
          type_action = 0
          this.ischonCot = true
          // this.$emit('lienketcotkhac', event.item.id)
          break
        case 'lienketcotkhac':
          type_action = 1
          // this.$emit('lienketcotkhac', event.item.id)
          break
        case 'xoacot':
          type_action = 2
          //  this.$emit('xoacot', event.item.id)
          break
        case 'thuoctinh':
          type_action = 3
          //  this.$emit('thuoctinh', event.item.id)
          break
      }
      let data = {
        type: type_action,
        value: event.item
      }
      this.$emit('node_action', data)
    },
    menuMap1_Clicked(event) {
      switch (event.option.slug) {
        case 'lamtuoi':
          this.getThongTinBanDo()
          break
        case 'dayladau':
          this.isHere()
          break
        case 'tim_dia_diem':
          document.getElementById('searchInput').focus()
          break
        case 'dokhoangcach':
          this.batDoKhoangCach = !this.batDoKhoangCach
          break
        case 'xoaphepdo':
          this.batDoKhoangCach = false
          break
      }
    },
    menuPolyline1_Clicked(event) {
      let type_action
      switch (event.option.slug) {
        case 'huylienket':
          type_action = 0
          break
        case 'daochieu':
          type_action = 1
          break
      }
      let data = {
        type: type_action,
        value: event.item
      }
      this.$emit('polyline_action', data)
    },
    dragendPositionNode(event, node) { },
    dragPositionNode(event, node) {
      try {
        this.isMapChanged = true
        const nodeid = node.id
        let lat = event.latLng.lat()
        let lng = event.latLng.lng()

        const objIndex = this.nodes.findIndex((obj) => obj.id === nodeid)
        if (objIndex < 0) return
        this.nodes[objIndex].position.lat = lat
        this.nodes[objIndex].position.lng = lng
        this.nodes[objIndex].modified = true

        const length = this.polylines.length
        let copiedArray = new Array(length)
        for (let i = 0; i < length; i++) {
          let polyline = this.polylines[i]
          if (nodeid === polyline.dest || nodeid === polyline.source || nodeid.startsWith('DIEMUON_')) {
            let path = polyline.path.slice(0)
            for (let j = 0; j < path.length; j++) {
              if (path[j].id === nodeid) {
                path[j].lat = lat
                path[j].lng = lng
                polyline.modified = !!nodeid.startsWith('DIEMUON_')
                break
              }
            }
            polyline.path = path
            copiedArray[i] = polyline
          } else {
            copiedArray[i] = polyline
          }
        }
        this.polylines = copiedArray.slice(0)
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
        [
          {
            icon: lineSymbol,
            offset: '0%',
            repeat: '6px'
          }
        ],
        [
          {
            icon: lineSymbol,
            offset: '50%',
            repeat: '15px'
          }
        ],
        [
          {
            icon: doubleLine,
            offset: '0%',
            repeat: '6px'
          }
        ],
        [
          {
            icon: lineSymbol,
            offset: '50%',
            repeat: '25px'
          }
        ]
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
      // return {'text': symbol, 'color': '#333333', 'fontSize': '12px', 'fontFamily': 'Roboto, Arial', 'fontWeight': 'bold'}
      return null
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
    luuToaDoBanDo() {
      var nodes = []
      let count = this.nodes.length
      const node = this.nodes[count - 1]
      nodes.push({ lat: node.getPosition().lat.toString(), lng: node.getPosition().lng.toString() })
      this.$emit('luuToaDoBanDo', nodes)
    },
    dragendPosition(event, node) {
      this.isMapChanged = true
    },
    setPositionNode(id, position) {
      const objIndex = this.nodes.findIndex((obj) => obj.id === id)
      if (objIndex >= 0) {
        this.nodes[objIndex].position.lat = parseFloat(position.lat)
        this.nodes[objIndex].position.lng = parseFloat(position.lng)
        this.nodes[objIndex].modified = true
      }
    },
    getPositionNode: function (id) {
      const objIndex = this.nodes.findIndex((obj) => obj.id === id)
      if (objIndex >= 0) {
        return {
          lat: parseFloat(this.nodes[objIndex].position.lat),
          lng: parseFloat(this.nodes[objIndex].position.lng)
        }
      } else return null
    },
    setDraggableNode(id, draggable) {
      const objIndex = this.nodes.findIndex((obj) => obj.id === id)
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
      this.$refs.oneBSSGmap.panTo(pos)
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
        return (c === 'x' ? r : (r & 0x3) | 0x8).toString(16)
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
    getPolylinesVNPT() {
      return this.polylines
    },
    nodeUpdate(node) {
      const objIndex = this.nodes.findIndex((obj) => obj.id === node.id)
      if (objIndex >= 0) return false
      this.nodes.push(node)
      return true
    },
    setLocationEx(position) {
      if (position === null || position === 'undefined') {
        if (this.nodes.length > 0) {
          this.positionDefault.lat = this.nodes[0].position.lat
          this.positionDefault.lng = this.nodes[0].position.lng
        }
      } else {
        this.positionDefault.lat = position.lat
        this.positionDefault.lng = position.lng
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
      const objIndex = this.polylines.findIndex((obj) => obj.id === polyline.id)
      if (objIndex >= 0) return false
      this.polylines.push(polyline)
      return true
    },
    getPositionNodeId(id) {
      const objIndex = this.nodes.findIndex((obj) => obj.id === id)
      if (objIndex >= 0) {
        return this.nodes[objIndex].position
      } else return null
    },
    getPolylineById(id) {
      const objIndex = this.polylines.findIndex((obj) => obj.id === id)
      if (objIndex >= 0) return this.polylines[objIndex]
      else return null
    },
    getNodeById(id) {
      const objIndex = this.nodes.findIndex((obj) => obj.id === id)
      if (objIndex >= 0) return this.nodes[objIndex]
      else return null
    },
    setViewNodeSuaToaDoCap(id) {
      const polyline = this.getPolylineById(id)
      if (polyline !== null) {
        const path = polyline.path
        for (let index = 0; index < path.length; index++) {
          const objIndex = this.nodes.findIndex((obj) => obj.id === path[index].id)
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
        this.positionDefault.lat = this.nodes[count - 1].position.lat
        this.positionDefault.lng = this.nodes[count - 1].position.lng
        this.bindData(this.positionDefault.lng, this.positionDefault.lat, this.nodes[count - 1].tooltipText)

        this.luuToaDoBanDo()
      }
    },
    setPolylines(polylines) {
      this.polylines = polylines
      // append polyline to map
      for (let index = 0; index < polylines.length; index++) {
        const polyline = polylines[index]
        const polylineList = []
        let l = polyline.path.length
        if (l > 1) {
          if ((polyline.path[0].lat * polyline.path[0].lng > 0) && (polyline.path[l - 1].lat * polyline.path[l - 1].lng > 0)) {
          } else {
            continue
          }
        } else {
          continue
        }
        for (let index = 0; index < polyline.path.length; index++) {
          let p = polyline.path[index]
          polylineList.push([p.lng, p.lat])
        }
        let dashArray = polyline.style.split('-')
        dashArray = dashArray.map(str => {
          return parseInt(str, 10)
        })
        const obj = {
          id: polyline.id,
          color: '#0036D3',
          opacity: 1,
          weight: 3,
          lineCap: 'round',
          dashArray: dashArray
        }
        let polylineLayer = VnptMap.initPolyline(polylineList, obj)
        polylineLayer.style = polyline.style
        polylineLayer.id = polyline.id
        polylineLayer.source = polyline.source
        polylineLayer.dest = polyline.dest
        polylineLayer.length_met = polyline.length_met
        polylineLayer.length_km = polyline.length_km
        polylineLayer.modified = polyline.modified
        polylineLayer.updatePath = polyline.updatePath
        polylineLayer.path = polyline.path
        polylineLayer.label = polyline.label
        polylineLayer.iconDiemUonBase64 = polyline.iconDiemUonBase64
        let me = this
        this.mapVNPT.addLayer(polylineLayer)
        polylineLayer.on('mouseenter', (e) => {
          polylineLayer.setStyle({ color: 'red' })
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
          polylineLayer.setStyle({ color: '#0036D3' })
        })
        polylineLayer.on('contextmenu', (e) => {
          me.rightclickPolyline(e, polylineLayer.id)
        })
        polylineLayer.on('click', (e) => {
          if (e.originalEvent.detail === 2) {
            this.$emit('suaCap', polylineLayer.id)
          }
        })
        // polylineLayer.on('mousedown', (e) => {
        // })
        this.polylineLayers.push(polylineLayer)
      }
    },
    isHere: function () {
      this.basemapApi.getAddressByLonLat(this.locationrightclickMap, this.token, (rs) => {
        if (rs.status) {
          const popup = VnptMap.initPopup([this.locationrightclickMap.lng, this.locationrightclickMap.lat], rs.result.address)
          popup.addLayerTo(this.mapVNPT)
        } else {
          console.error(rs.message)
        }
      })
    },
    async refreshMap() {
      this.draggable = false
      this.polylineLayerTimKiem && this.polylineLayerTimKiem.remove()
      this.polylineLayerTimKiem = null
      setTimeout(() => this.mapVNPT.updateSized(), 10)
      for (let index = 0; index < this.pointLayers.length; index++) {
        let element = this.pointLayers[index]
        try {
          element && element.remove()
          element = null
        } catch (error) {
        }
      }
      for (let index = 0; index < this.pointDiemUonLayers.length; index++) {
        let element = this.pointDiemUonLayers[index]
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
      this.pointLayers = []
      this.pointDiemUonLayers = []
      this.polylineLayers = []
    },
    capnhatToaDo: async function (data) {
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.post('/web-cabman/bando_tuyencot/capnhat_toado', data)
        if (rs.error_code === 'BSS-00000000') {
          this.$toast.success('Cập nhật tọa độ thành công')
        }
      } catch (error) {
        this.$toast.error(error.message)
      } finally {
        this.$root.showLoading(false)
      }
    },
    ghiToaDo: async function () {
      if (this.cotChuaToaDo != null) {
        let cot_id = this.cotChuaToaDo.COT_ID.split('_')[1]
        let kinhdo = this.atHere.split(',')[1]
        let vido = this.atHere.split(',')[0]
        let obj = {
          cot_id: cot_id,
          kinhdo: kinhdo,
          vido: vido
        }
        await this.capnhatToaDo(obj)
      }
    },
    clickMap: async function (event) {
      if (this.isGhiToaDo) {
        this.atHere = event.lngLat.lat + ',' + event.lngLat.lng
        this.isGhiToaDo = false
        await this.ghiToaDo()
        EventBus.$emit('refeshBanDo', true)
      }
      if (this.isGhimToaDo) {
        this.isGhimToaDo = false
        EventBus.$emit('GhimToaDoBanDoTuyenCot', { lat: event.lngLat.lat, lng: event.lngLat.lng })
      }
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

.gm-style-iw>button {
  display: none !important;
}

.control-map {
  margin-top: 4px;
}

.modalMapVNPT {
  position: initial;
  height: 400px;
  width: 100%;
}

.map-canvas-box {
  overflow: hidden;
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
</style>
