<template>
    <div class="map-canvas-box">
        <!-- <el-tooltip class="item" effect="light"  content="Lưu tọa độ bản đồ" placement="top">
        <el-button ref="ctrlLuuToaDoBanDo" type="info"  plain  icon="el-icon-s-tools"  size="small" class="control-map"></el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="light"  content="Thay đổi nguồn dữ liệu bản đồ" placement="top">
          <el-button ref="ctrlThayDoiNguonDuLieu" type="info"  plain  icon="el-icon-s-order"  size="small" class="control-map"></el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="light"  content="Di chuyển đối tượng" placement="top">
          <el-button ref="ctrlDiChuyenDoiTuong" type="info"  plain  icon="one-drag"  size="small" class="control-map"></el-button>
        </el-tooltip> -->
        <!-- <transition name="slide-fade" v-if="showSearchDiaDiem">
          <gmap-autocomplete
            ref="SearchDiaDiem"
            placeholder="Gõ để tìm kiếm địa điểm"
            class="form-control"
            :options="{fields: ['geometry', 'formatted_address', 'address_components']}"
            @place_changed="onSearchDiaDiem"/>
        </transition> -->
        <vue-simple-context-menu
          elementId="menuPolyline"
          :options="optionsPolylineMenu"
          ref="vueSimpleContextMenuPolyline"
          @option-clicked="menuPolyline_Clicked"/>
        <vue-simple-context-menu
          elementId="menuNodeDiemUon"
          :options="optionsNodeDiemUonMenu"
          ref="vueSimpleContextMenuNodeDiemUon"
          @option-clicked="menuNodeDiemUon_Clicked"/>
        <vue-simple-context-menu
          elementId="menuMap"
          :options="optionsMapMenu"
          ref="vueSimpleContextMenuMap"
          @option-clicked="menuMap_Clicked"/>
        <vue-simple-context-menu
          elementId="menuNode"
          :options="optionsNodeMenu"
          ref="vueSimpleContextMenuNode"
          @option-clicked="menuNode_Clicked"/>
        <div id="mapVNPTBanDoTuyenCong" class="mapVNPTBanDoTuyenCong"></div>
    </div>
</template>
<script>

import GraphNode from '../structures/GraphNode'
import GraphPolyline from '../structures/GraphPolyline'
import VueSimpleContextMenu from 'vue-simple-context-menu'
import EventBus from '@/utils/eventBus'
import VnptMap from '@vnpt/maps-onebss'
const TOKEN = 'VNPT-f76bf568-b093-45cf-9761-f6339d3e7d9a'
export default {
  components: {'vue-simple-context-menu': VueSimpleContextMenu},
  name: 'GMapVNPT',
  props: {
  },
  computed: {
    getPositionCenter () { return this.location },
    optionsPolylineMenu () {
      return [...this.optionsPolyline]
    }
  },
  async created () {
  },
  data () {
    return {
      point: {},
      pointLayers: [],
      pointDiemUonLayers: [],
      polylineLayers: [],
      geofency: null,
      basemapApi: null,
      mapVNPT: null,
      zoomLevel: 20,
      draggable: false,
      isMapChanged: false,
      isMapModified: false,
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
      location: {lat: -1, lng: -1},
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
      optionsNodeMenu: [
        {
          name: '<span class="icon text-main f20 one-taocap"></span> Tạo cổng tới bể tiếp theo',
          slug: 'taoCong'
        },
        {
          name: '<span class="icon text-main f20 one-taokn"></span> Tạo cổng kết nối với bể khác tuyến',
          slug: 'taoCongKhacTuyen'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 one-trash"></span> Xóa bể',
          slug: 'xoaBe'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 -ap icon-info"></span> Thuộc tính',
          slug: 'thuocTinhBe'
        },
        {
          name: '<span class="icon text-main f20 one-forward"></span> Chuyển sang tuyến cống bể khác',
          slug: 'chuyenTuyen'
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
      ],
      optionsNodeDiemUonMenu: [
        {
          name: '<span class="icon one-td-app-outline"></span> Xóa điểm uốn',
          slug: 'xoaDiemUonCong'
        }
      ],
      optionsPolyline: [
        {
          name: '<span class="icon text-main f20 one-tc-chenbe"></span> Chèn bể',
          slug: 'chenBe'
        },
        {
          name: '<span class="icon text-main f20 one-tc-daochieu"></span> Đảo chiều đoạn cống',
          slug: 'daoChieu'
        },
        {
          name: '<span class="icon text-main f20 one-tc-chenbea"></span> Thay đổi bể A',
          slug: 'doiBeA'
        },
        {
          name: '<span class="icon text-main f20 one-tc-chenbeb"></span> Thay đổi bể B',
          slug: 'doiBeB'
        },
        {
          name: '<span class="icon text-main f20 one-trash"></span> Xóa cống',
          slug: 'xoaCong'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 one-taodiemuon"></span> Cập nhật điểm uốn',
          slug: 'suaDiemUon'
        },
        {
          name: '<span class="icon text-main f20 one-taomid"></span> Tạo điểm uốn',
          slug: 'taoDiemUon'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 -ap icon-info"></span> Thuộc tính',
          slug: 'thuocTinhDoanCong'
        }
      ],
      isChonBe: false,
      atHere: null,
      isGhiToaDo: false,
      beChuaToaDo: Object
    }
  },
  watch: {
    polylines: {
      handler: function (val) {
      },
      deep: true},
    searchDiaDiem: {
      handler: function (val) {
        this.location = {
          lat: val.geometry.location.lat(),
          lng: val.geometry.location.lng()
        }
      },
      deep: true},
    nodes: {
      handler: function (val) {
      },
      deep: true},
    isMapChanged: function (val) {
      if (val) { this.mapOptions.mapTypeId = 'SATELLITE' } else { this.mapOptions.mapTypeId = 'ROADMAP' }
    }
  },
  async  mounted () {
    // let vm = this
    // this.$refs.oneBSSGmap.$mapPromise.then((map) => {
    //   vm.map = map
    //   let ctrlLuuToaDoBanDo = this.$refs.ctrlLuuToaDoBanDo.$el
    //   map.controls[this.google.maps.ControlPosition.RIGHT_TOP].push(ctrlLuuToaDoBanDo)
    //   ctrlLuuToaDoBanDo.addEventListener('click', () => {
    //     this.luuToaDoBanDo()
    //   })

    //   let ctrlThayDoiNguonDuLieu = this.$refs.ctrlThayDoiNguonDuLieu.$el
    //   map.controls[this.google.maps.ControlPosition.RIGHT_TOP].push(ctrlThayDoiNguonDuLieu)
    //   ctrlThayDoiNguonDuLieu.addEventListener('click', () => {
    //     if (vm.isMapChanged) vm.isMapChanged = false
    //     else vm.isMapChanged = true
    //   })

    //   let ctrlDiChuyenDoiTuong = this.$refs.ctrlDiChuyenDoiTuong.$el
    //   map.controls[this.google.maps.ControlPosition.RIGHT_TOP].push(ctrlDiChuyenDoiTuong)
    //   ctrlDiChuyenDoiTuong.addEventListener('click', () => {
    //     vm.draggable = !vm.draggable
    //   })

    //   this.loading = false
    //   this.measureTool = new MeasureTool(this.map, {
    //     contextMenu: false,
    //     unit: MeasureTool.UnitTypeId.METRIC,
    //     tooltip: false
    //   })

    //   let polygon = new this.google.maps.Polygon({
    //     clickable: true
    //   })

    //   this.measureTool.addListener('measure_start', () => {
    //     polygon.setOptions({clickable: false})
    //   })
    //   this.measureTool.addListener('measure_end', (e) => {
    //     polygon.setOptions({clickable: true})
    //   })
    //   this.measureTool.addListener('measure_change', (e) => {
    //   })
    //   window.addEventListener('keydown', function (e) {
    //     if (vm.isChonBe) {
    //       if (e.key == 'Escape') {
    //         vm.isChonBe = false
    //         vm.$toast.info('Đã hủy thao tác tạo cống')
    //       }
    //     }
    //   })
    //   EventBus.$on('ghiToaDo', (args) => {
    //     vm.isGhiToaDo = true
    //     vm.beChuaToaDo = args
    //   })
    // })
    let vm = this
    let options = {
      showZoomControl: false,
      showFullscreen: false,
      id: this.generateId(),
      maxZoom: 24
    }
    vm.mapVNPT = VnptMap.initMap('mapVNPTBanDoTuyenCong', options)
    vm.mapVNPT.on('load', (e) => {
      vm.basemapApi = VnptMap.initBasemapApi()
      vm.geofency = VnptMap.initGeofency()
      setTimeout(() => this.mapVNPT.updateSized(), 10)
      EventBus.$on('ghiToaDo', (args) => {
        vm.isGhiToaDo = true
        vm.beChuaToaDo = args
      })
    })
    vm.mapVNPT.on('click', (e) => {
      this.map_LeftClick(e);
    })
    vm.mapVNPT.on('contextmenu', (e) => {
      if (this.point.x !== e.originalEvent.x || this.point.y !== e.originalEvent.y) {
        // this.rightclickMap(e)
      } else {
        this.point.x = 0
        this.point.y = 0
      }
    });
    EventBus.$on('ghiToaDo', (args) => {
      console.log("ghiToaDo", args);
      vm.isGhiToaDo = true
      vm.beChuaToaDo = args
    });
  },
  methods: {
    generateId () {
      const randomId = Math.random().toString(36).substr(2, 9) + '_' + Date.now().toString(36)
      return randomId
    },
    menuNodeDiemUon_Clicked (event) {
      console.log('🚀 ~ menuNodeDiemUon_Clicked ~ event:', event)
      switch (event.option.slug) {
        case 'xoaDiemUonCong':
          const nodeid = event.item.id
          const idpolyline = event.item.id_polyline
          const objIndex = this.pointDiemUonLayers.findIndex(obj => obj.id == nodeid)
          if (objIndex > -1) {
            let node = this.pointDiemUonLayers[objIndex]
            node && node.remove()
            node = null
            this.pointDiemUonLayers.splice(objIndex, 1)
            const idx = this.polylineLayers.findIndex(obj => obj.id == idpolyline)
            if (idx < 0) return
            var path = this.polylineLayers[idx].path.splice(0)
            const idx2 = path.findIndex(obj => obj.id == nodeid)
            if (idx2 > -1) {
              path.splice(idx2, 1)
              this.polylineLayers[idx].path = path.splice(0)
              this.polylineLayers[idx].modified = true
              this.rePolyline(this.polylineLayers[idx])
            }
          }
          break
      }
    },
    rePolyline (polyline) {
      polyline.remove()
      const polylineList = []
      for (let index = 0; index < polyline.path.length; index++) {
        let p = polyline.path[index]
        polylineList.push([p.lng, p.lat])
      }
      // let dashArray = polyline.style.split('-')
      // dashArray = dashArray.map(str => {
      //   return parseInt(str, 10)
      // })
      let dashArray = [2.6, 2.6]
      const obj = {
        id: polyline.id.toString(),
        color: '#EC3707',
        opacity: 1,
        weight: 3.5,
        lineCap: 'round',
        dashArray: dashArray
      }
      let polylineLayer = VnptMap.initPolyline(polylineList, obj)
      polylineLayer.id = polyline.id
      polylineLayer.source = polyline.source
      polylineLayer.dest = polyline.dest
      polylineLayer.length_met = polyline.length_met
      polylineLayer.length_km = polyline.length_km
      polylineLayer.modified = polyline.modified
      polylineLayer.updatePath = polyline.updatePath
      polylineLayer.path = polyline.path
      polylineLayer.style = polyline.style
      polylineLayer.label = polyline.label
      polylineLayer.iconDiemUonBase64 = polyline.iconDiemUonBase64
      let me = this
      this.mapVNPT.addLayer(polylineLayer)
      polylineLayer.on('mouseenter', (e) => {
        polylineLayer.setStyle({ color: '#0F55F8' })
        let lng = e.lngLat.lng
        let lat = e.lngLat.lat
        let content = polyline.label
        me.popupInstance && me.popupInstance.remove()
        me.popupInstance = VnptMap.initPopup([lng, lat], content, {
          offset: 20
        })
        me.popupInstance.addLayerTo(me.mapVNPT)
      })
      polylineLayer.on('mouseout', (e) => {
        me.popupInstance && me.popupInstance.remove()
        polylineLayer.setStyle({ color: '#EC3707' })
      })
      polylineLayer.on('contextmenu', (e) => {
        me.rightclickPolyline(e, polylineLayer.id)
      })
      polylineLayer.on('click', (e) => {
      })
      polylineLayer.on('mousedown', (e) => {
      })
    },
    reload () {
      this.bounds = new window.google.maps.LatLngBounds()
      this.map.setZoom(20)
      this.map.setCenter(
        new window.google.maps.LatLng(
          this.location.lat,
          this.location.lng
        )
      )
    },
    latLng2Point (latLng, map) {
      let topRight = map.getProjection().fromLatLngToPoint(map.getBounds().getNorthEast())
      let bottomLeft = map.getProjection().fromLatLngToPoint(map.getBounds().getSouthWest())
      let scale = Math.pow(2, map.getZoom())
      let worldPoint = map.getProjection().fromLatLngToPoint(latLng)
      return new window.google.maps.Point((worldPoint.x - bottomLeft.x) * scale, (worldPoint.y - topRight.y) * scale)
    },
    fromLatLngToPixel: function (position) {
      let scale = Math.pow(2, this.map.getZoom())
      let proj = this.map.getProjection()
      let bounds = this.map.getBounds()
      let nw = proj.fromLatLngToPoint(
        new window.google.maps.LatLng(
          bounds.getNorthEast().lat(),
          bounds.getSouthWest().lng()
        ))
      let point = proj.fromLatLngToPoint(position)
      return new window.google.maps.Point(
        Math.floor((point.x - nw.x) * scale),
        Math.floor((point.y - nw.y) * scale))
    },
    getPolylineFromID (polyline_id) {
      return this.polylineLayers.find(e => e.id === polyline_id)
    },
    rightclickPolyline (event, polyline_id) {
      let polyline = this.getPolylineFromID(polyline_id)
      this.diemuonLatlng = event.lngLat
      this.activePolyline = polyline
      this.mouselatLng.lat = event.lngLat.lat
      this.mouselatLng.lng = event.lngLat.lng

      this.$refs.vueSimpleContextMenuMap && this.$refs.vueSimpleContextMenuMap.hideContextMenu()
      this.$refs.vueSimpleContextMenuNode && this.$refs.vueSimpleContextMenuNode.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolyline && this.$refs.vueSimpleContextMenuPolyline.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeDiemUon && this.$refs.vueSimpleContextMenuNodeDiemUon.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolyline.showMenu(event, polyline)
      this.$refs.vueSimpleContextMenuPolyline.showMenu(event, polyline)
      const menu = document.getElementById('menuPolyline')
      var left = event.point.x
      var top = event.point.y
      menu.style.left = left + 'px'
      menu.style.top = top + 'px'
      menu.position = 'fixed'
      menu.classList.add('menu-context')
    },
    rightclickMap (event) {
      // this.$refs.vueSimpleContextMenuMap && this.$refs.vueSimpleContextMenuMap.hideContextMenu()
      // this.$refs.vueSimpleContextMenuNode && this.$refs.vueSimpleContextMenuNode.hideContextMenu()
      // this.$refs.vueSimpleContextMenuPolyline && this.$refs.vueSimpleContextMenuPolyline.hideContextMenu()
      // this.$refs.vueSimpleContextMenuNodeDiemUon && this.$refs.vueSimpleContextMenuNodeDiemUon.hideContextMenu()
      // this.$refs.vueSimpleContextMenuMap.showMenu(event)
      // const menu = document.getElementById('menuMap')
      // let pixelOffset = new window.google.maps.Point(10, -5)
      // let mapDiv = this.map.getDiv()
      // let mousePosition = this.fromLatLngToPixel(event.latLng)
      // let mapSize = new window.google.maps.Size(mapDiv.offsetWidth, mapDiv.offsetHeight)
      // let menuSize = new window.google.maps.Size(menu.offsetWidth, menu.offsetHeight)
      // let left = mousePosition.x
      // let top = mousePosition.y
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
      // this.atHere = event.latLng.lat() + ',' + event.latLng.lng()
      // menu.style.left = left + 'px'
      // menu.style.top = top + 'px'
      // menu.position = 'fixed'
      // menu.classList.add('menu-context')
    },
    getNodeFromID (point_id) {
      return this.pointLayers.find(e => e.id === point_id)
    },
    rightclickNode (event, point_id) {
      this.$refs.vueSimpleContextMenuMap && this.$refs.vueSimpleContextMenuMap.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeDiemUon && this.$refs.vueSimpleContextMenuNodeDiemUon.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolyline && this.$refs.vueSimpleContextMenuPolyline.hideContextMenu()
      let node = this.getNodeFromID(point_id)
      this.$refs.vueSimpleContextMenuNode.showMenu(event, node)
      const menu = document.getElementById('menuNode')
      var left = event.pointEvent.layerX
      var top = event.pointEvent.layerY
      menu.style.left = left + 'px'
      menu.style.top = top + 'px'
      menu.position = 'fixed'
      menu.classList.add('menu-context')
    },
    updateSized () {
      setTimeout(() => this.mapVNPT.updateSized(), 10)
    },
    leftclickNode (event, node) {
      if (this.isChonBe) {
        if (node.id != '') {
          let obj = {
            dataBe: node.dataBe,
            type: 'MENU_ITEM_LK_BE_TT_CHON'
          }
          this.$emit('taoDoanCong', obj)
          this.isChonBe = false
        }
      }
    },
    dblClickNode (event, node) {
      this.$emit('dblClickItem', {
        type: 'nodes',
        id: 'BC_' + node.id
      })
    },
    dblclickPolyline ($event, polyline) {
      try {
        this.$emit('dblClickItem', {
          type: 'connectors',
          id: 'DC_' + polyline.id
        })
      } catch (e) {
        console.log(e)
      }
    },
    insertAt (arr, index, newItem) {
      return [
        ...arr.slice(0, index),
        newItem,
        ...arr.slice(index)
      ]
    },
    menuNode_Clicked (event) {
      let type_action
      switch (event.option.slug) {
        case 'taoCong':
          type_action = 0
          this.isChonBe = true
          break
        case 'taoCongKhacTuyen':
          type_action = 1
          break
        case 'xoaBe':
          type_action = 2
          break
        case 'thuocTinhBe':
          type_action = 3
          break
        case 'chuyenTuyen':
          type_action = 4
          break
      }
      let data = {
        type: type_action,
        value: event.item.id,
        dataBe: event.item.dataBe
      }
      this.$emit('node_action', data)
    },
    menuMap_Clicked (event) {
      switch (event.option.slug) {
        case 'lamtuoi':
          break
        case 'dayladau':
          this.isHere()
          break
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
    menuPolyline_Clicked (event) {
      let type_action
      switch (event.option.slug) {
        case 'chenBe':
          type_action = 0
          this.isChonBe = true
          break
        case 'daoChieu':
          type_action = 1
          break
        case 'doiBeA':
          type_action = 2
          break
        case 'doiBeB':
          type_action = 3
          break
        case 'xoaCong':
          type_action = 4
          break
        case 'thuocTinhDoanCong':
          type_action = 5
          break
        case 'suaDiemUon':
          event.item.modified = true
          for (let index = 0; index < this.pointDiemUonLayers.length; index++) {
            if (this.pointDiemUonLayers[index].id_polyline === event.item.id && this.pointDiemUonLayers[index].isDiemuon === true) {
              this.pointDiemUonLayers[index].visible = true
              this.pointDiemUonLayers[index].setVisible(true)
            }
          }
          break
        case 'taoDiemUon':
          let id_diemuon = this.diemuonAdd(event.item.id, event.item.iconDiemUonBase64, this.diemuonLatlng.lng, this.diemuonLatlng.lat, true, true)
          if (id_diemuon !== null) {
            for (let i = 0; i < event.item.path.length - 1; i++) {
              let node1 = event.item.path[i]
              let node2 = event.item.path[i + 1]
              let point = [this.diemuonLatlng.lng, this.diemuonLatlng.lat]
              let polylineList = [
                [node1.lng, node1.lat],
                [node2.lng, node2.lat]
              ]
              let chk = this.geofency.isLocationOnEdgeLine(point, polylineList, 10)
              if (chk) {
                let p = {
                  id: id_diemuon,
                  lat: this.diemuonLatlng.lat,
                  lng: this.diemuonLatlng.lng
                }
                const result = this.insertAt(event.item.path, i + 1, p)
                event.item.path = result.slice(0)
                event.item.modified = true
                break
              }
            }
          }
          break
      }
      let data = {
        type: type_action,
        value: event.item.id
      }
      this.$emit('polyline_action', data)
    },
    dragendPositionNode (event, node) {
    },
    getLabel (symbol) {
      return {'text': symbol, 'color': '#333333', 'fontSize': '12px', 'fontFamily': 'Roboto, Arial', 'fontWeight': 'bold'}
    },
    mouseoverNode (event, node) {
      node.mouseover = true
      this.infoPositionNode = event.latLng
      this.infoContentNode = node.tooltipText !== null ? '<b>' + node.tooltipText.replaceAll('#', '<br>') + '</b>' : null
      this.infoOpenedNode = true
      this.infoOpenedPolyline = false
    },
    ctrlThayDoiNguonDuLieu () {
      let t = this.mapTypeId;
      if(!(t == null || t == undefined))
        t = t.toUpperCase();
      if (t === 'ROADMAP') {
        this.mapTypeId = 'SATELLITE'
        this.mapVNPT.setStyle('https://maps.vnpt.vn/storage/vnptmaps/style/style_vetinh.json')
      } else {
        this.mapTypeId = 'ROADMAP'
        this.mapVNPT.setStyle('https://maps.vnpt.vn/tileserver/styles/mapvnpt_v2/style.json')
      }
      this.$emit('thayDoiNguonDuLieu', {
        mapTypeId: this.mapTypeId,
        map: this.mapVNPT
      });
      setTimeout(() => EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2'), 100)
    },
    mouseoverNodeDiemUon (event, node) {
      this.infoOpenedNode = false
      this.infoOpenedPolyline = false
    },
    mouseoutNode (event, node) {
      node.mouseover = false
      this.infoOpenedNode = false
      this.infoOpenedPolyline = false
    },
    mouseoverPolyline (event, polyline) {
      polyline.mouseover = true
      this.infoPositionNode = event.latLng
      this.infoContentNode = '<b>' + polyline.Name + '</b>'
      this.infoOpenedNode = false
      this.infoOpenedPolyline = true
    },
    mouseoutPolyline (event, polyline) {
      polyline.mouseover = false
      this.infoOpenedNode = false
      this.infoOpenedPolyline = false
    },
    onSearchDiaDiem (data) {
      this.searchDiaDiem = data
    },
    luuToaDoBanDo () {
      var nodes = []
      for (let index = 0; index < this.pointLayers.length; index++) {
        const node = this.pointLayers[index]
        if (node.modified && !node.id.toString().startsWith('DIEMUON_')) {
          let lat = node.position.lat
          let lng = node.position.lng
          nodes.push({loai: 'BECAP', id: node.id, lat: lat, lng: lng})
        }
      }
      var polylines = []
      for (let index = 0; index < this.polylineLayers.length; index++) {
        const polyline = this.polylineLayers[index]
        if (polyline.modified) {
          polylines.push({id: polyline.id,
            day_toado: (polyline.path.filter(element => {
              return element.id.toString().startsWith('DIEMUON_')
            }))})
        }
      }
      this.$emit('luuToaDoBanDo', nodes, polylines)
    },
    // luuToaDoBanDo () {
    //   let nodes = []
    //   for (let index = 0; index < this.nodes.length; index++) {
    //     const node = this.nodes[index]
    //     if (node.modified && !node.id.toString().startsWith('DIEMUON_')) {
    //       nodes.push({loai: 'BECAP', id: node.getID().toString(), lat: node.getPosition().lat.toString(), lng: node.getPosition().lng.toString()})
    //     }
    //   }
    //   let polylines = []
    //   for (let index = 0; index < this.polylines.length; index++) {
    //     const polyline = this.polylines[index]
    //     if (polyline.modified) {
    //       polylines.push({id: polyline.id,
    //         day_toado: (polyline.path.filter(element => {
    //           return String(element.id).startsWith('DIEMUON_')
    //         }))})
    //     }
    //   }
    //   this.$emit('luuToaDoBanDo', nodes, polylines)
    // },
    setPositionNode (id, position) {
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
    ctrlLuuToaDoBanDo () {
      this.luuToaDoBanDo()
    },
    setDraggableNode (id, draggable) {
      const objIndex = this.nodes.findIndex(obj => obj.id === id)
      if (objIndex >= 0) {
        this.nodes[objIndex].draggable = draggable
      }
    },
    mousemove (event) {
      this.mouselatLng = {lat: event.latLng.lat(), lng: event.latLng.lng()}
    },
    getPositionPolyline: function (item) {
      return {
        lat: parseFloat(item.lat),
        lng: parseFloat(item.lng)
      }
    },
    panToMarker (pos) {
      this.$refs.oneBSSGmap.panTo(pos)
    },
    nodeCreate () {
      let node = new GraphNode()
      return node
    },
    create_UUID () {
      let dt = new Date().getTime()
      let uuid = 'xxxxxxxx_xxxx_4xxx_yxxx_xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        let r = (dt + Math.random() * 16) % 16 | 0
        dt = Math.floor(dt / 16)
        return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16)
      })
      return uuid
    },
    diemuonAdd (id_polyline, iconDiemUonBase64, lng, lat, visible, draggable) {
      var id = 'DIEMUON_' + this.create_UUID()
      const point = [lng, lat]
      let iconUrl = iconDiemUonBase64
      const obj = {
        iconUrl: iconUrl,
        id: id,
        iconWidth: 15,
        iconHeight: 15,
        isDraggable: draggable
      }
      let pointLayer = VnptMap.initPoint(point, obj)
      pointLayer.id = id
      pointLayer.id_polyline = id_polyline
      pointLayer.draggable = draggable
      pointLayer.isDiemuon = true
      pointLayer.visible = visible
      pointLayer.setVisible(visible)
      pointLayer.position = { lng, lat }
      pointLayer.iconUrl = iconUrl
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
      if (this.nodeUpdate(pointLayer)) {
        return id
      } else return null
    },
    getNodes () {
      return this.nodes
    },
    getPolylines () {
      return this.polylines
    },
    nodeUpdate (pointLayer) {
      try {
        const objIndex = this.pointDiemUonLayers.findIndex(obj => obj.id == pointLayer.id)
        if (objIndex >= 0) return false
        this.pointDiemUonLayers.push(pointLayer)
        let me = this
        pointLayer.on(this.mapVNPT, 'contextmenu', (e) => {
          this.point.x = e.pointEvent.x
          this.point.y = e.pointEvent.y
          me.rightclickNodeDiemUon(e, pointLayer.id)
        })
        pointLayer.onDrag('drag', (e) => {
          this.isMapChanged = true
          let nodeid = pointLayer.id
          let lat = e.coordinates.lat
          let lng = e.coordinates.lng
          let objIndex = this.pointDiemUonLayers.findIndex(obj => obj.id == nodeid)
          if (objIndex < 0) return
          this.pointDiemUonLayers[objIndex].position.lat = lat
          this.pointDiemUonLayers[objIndex].position.lng = lng
          this.pointDiemUonLayers[objIndex].modified = true
          let length = this.polylineLayers.length
          let copiedArray = new Array(length)
          for (let i = 0; i < length; i++) {
            let polyline = this.polylineLayers[i]
            if ((nodeid == polyline.dest || nodeid == polyline.source) || nodeid.toString().startsWith('DIEMUON_')) {
              let path = polyline.path.slice(0)
              for (let j = 0; j < path.length; j++) {
                if (path[j].id == nodeid) {
                  path[j].lat = lat
                  path[j].lng = lng
                  polyline.modified = !!nodeid.toString().startsWith('DIEMUON_')
                  break
                }
              }
              polyline.path = path
              copiedArray[i] = polyline
            } else {
              copiedArray[i] = polyline
            }
          }
          this.polylineLayers = copiedArray.slice(0)
          for (let index = 0; index < this.polylineLayers.length; index++) {
            let element = this.polylineLayers[index]
            let polylineList = []
            for (let index = 0; index < element.path.length; index++) {
              let p = element.path[index]
              polylineList.push([p.lng, p.lat])
            }
            element.setData(polylineList)
          }
        })
        this.mapVNPT.addLayer(pointLayer)
        return true
      } catch (error) {
        console.log('🚀 ~ file: BanDoVNPT.vue:613 ~ nodeUpdate ~ error:', error)
        return false
      }
    },
    setLocationEx (position) {
      if (position === null || position === 'undefined') {
        if (this.pointLayers.length > 0) {
          this.location = {
            lat: this.pointLayers[0].position.lat,
            lng: this.pointLayers[0].position.lng
          }
        }
      } else {
        this.location = {
          lat: position.lat,
          lng: position.lng
        }
      }
      this.mapVNPT.setCenter([this.location.lng, this.location.lat])
      this.mapVNPT.setZoom(18)
    },
    clear () {
      this.nodes.length = 0
      this.nodes = []
      this.polylines.length = 0
      this.polylines = []
    },
    polylineCreate () {
      let polyline = new GraphPolyline()
      return polyline
    },
    polylineUpdate (polyline) {
      const objIndex = this.polylines.findIndex(obj => obj.id === polyline.id)
      if (objIndex >= 0) return false
      this.polylines.push(polyline)
      return true
    },
    getPositionNodeId (id) {
      const objIndex = this.nodes.findIndex(obj => obj.id === id)
      if (objIndex >= 0) {
        return this.nodes[objIndex].position
      } else return null
    },
    getPolylineById (id) {
      const objIndex = this.polylines.findIndex(obj => obj.id === id)
      if (objIndex >= 0) return this.polylines[objIndex]
      else return null
    },
    getNodeById (id) {
      const objIndex = this.nodes.findIndex(obj => obj.id === id)
      if (objIndex >= 0) return this.nodes[objIndex]
      else return null
    },
    setViewNodeSuaToaDoCap (id) {
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
    getPolylinePath (id) {
      const polyline = this.getPolylineById(id)
      if (polyline !== null) {
        const path = polyline.path
        let pathposition = []
        for (let index = 0; index < path.length; index++) {
          const position = this.getPositionNodeId(path[index])
          if (position !== null) {
            pathposition.push(position)
          }
        }
        return pathposition
      } else return []
    },
    getIsMapChanged () {
      return this.isMapChanged
    },
    async refreshMap () {
      this.draggable = false
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
    setNodes (nodes) {
      this.refreshMap()
      let _nodes = nodes.filter(n => n.visible === true && n.isDiemuon === false)
      for (let index = 0; index < _nodes.length; index++) {
        const node = _nodes[index]
        console.log('🚀 ~ setNodes ~ node:', node)
        const point = [node.position.lng, node.position.lat]
        const obj = {
          iconUrl: node.icon.url,
          id: node.id,
          iconWidth: node.icon.size.width,
          iconHeight: node.icon.size.height,
          isDraggable: false
        }
        let pointLayer = VnptMap.initPoint(point, obj)
        pointLayer.symbol = node.symbol
        if (node.symbol !== null) {
          pointLayer.setLabel(node.symbol)
          pointLayer.setLabelVisible(true)
        }
        pointLayer.id = node.id
        pointLayer.id_polyline = node.id_polyline
        pointLayer.modified = node.modified
        pointLayer.draggable = false
        pointLayer.isDiemuon = node.isDiemuon
        pointLayer.visible = node.visible && (node.position.lat * node.position.lng > 0)
        pointLayer.setVisible(pointLayer.visible)
        pointLayer.position = node.position
        pointLayer.icon = node.icon
        pointLayer.tooltipText = node.tooltipText
        let me = this
        pointLayer.on(this.mapVNPT, 'mouseenter', (e) => {
          let lng = e.coordinates[0]
          let lat = e.coordinates[1]
          let content = '<b>' + node.tooltipText + '</b>'
          me.popupInstance && me.popupInstance.remove()
          me.popupInstance = VnptMap.initPopup([lng, lat], content, {
            offset: 20
          })
          me.popupInstance.addLayerTo(me.mapVNPT)
        })
        pointLayer.on(this.mapVNPT, 'mouseout', (e) => {
          me.popupInstance && me.popupInstance.remove()
        })
        pointLayer.onDrag('dragstart', (e) => {
        })
        pointLayer.onDrag('drag', (e) => {
          this.isMapChanged = true
          const nodeid = pointLayer.id
          let lat = e.coordinates.lat
          let lng = e.coordinates.lng
          const objIndex = this.pointLayers.findIndex(obj => obj.id == nodeid)
          if (objIndex < 0) return
          this.pointLayers[objIndex].position.lat = lat
          this.pointLayers[objIndex].position.lng = lng
          this.pointLayers[objIndex].modified = true
          const length = this.polylineLayers.length
          let copiedArray = new Array(length)
          for (let i = 0; i < length; i++) {
            let polyline = this.polylineLayers[i]
            if ((nodeid == polyline.dest || nodeid == polyline.source) || nodeid.toString().startsWith('DIEMUON_')) {
              let path = polyline.path.slice(0)
              for (let j = 0; j < path.length; j++) {
                if (path[j].id == nodeid) {
                  path[j].lat = lat
                  path[j].lng = lng
                  polyline.modified = !!nodeid.toString().startsWith('DIEMUON_')
                  break
                }
              }
              polyline.path = path
              copiedArray[i] = polyline
            } else {
              copiedArray[i] = polyline
            }
          }
          this.polylineLayers = copiedArray.slice(0)
          for (let index = 0; index < this.polylineLayers.length; index++) {
            let element = this.polylineLayers[index]
            let polylineList = []
            for (let index = 0; index < element.path.length; index++) {
              let p = element.path[index]
              polylineList.push([p.lng, p.lat])
            }
            element.setData(polylineList)
          }
        })
        pointLayer.onDrag('dragend', (e) => {
        })
        pointLayer.on(this.mapVNPT, 'click', (e) => {
        })
        pointLayer.on(this.mapVNPT, 'contextmenu', (e) => {
          this.point.x = e.pointEvent.x
          this.point.y = e.pointEvent.y
          me.rightclickNode(e, pointLayer.id)
        })
        pointLayer.addLayerTo(this.mapVNPT)
        this.pointLayers.push(pointLayer)
      }
      let _nodesDiemUon = nodes.filter(n => n.isDiemuon == true)
      for (let index = 0; index < _nodesDiemUon.length; index++) {
        const node = _nodesDiemUon[index]
        console.log('🚀 ~ setNodes _nodesDiemUon ~ node:', node)
        const point = [node.position.lng, node.position.lat]
        const obj = {
          iconUrl: node.iconUrl,
          id: node.id,
          iconWidth: 15,
          iconHeight: 15,
          isDraggable: node.draggable
        }
        let pointLayer = VnptMap.initPoint(point, obj)
        pointLayer.id = node.id
        pointLayer.id_polyline = node.id_polyline
        pointLayer.modified = node.modified
        pointLayer.draggable = node.draggable
        pointLayer.isDiemuon = node.isDiemuon
        pointLayer.visible = node.visible
        // pointLayer.visible = true
        pointLayer.position = node.position
        pointLayer.iconUrl = node.iconUrl
        pointLayer.setVisible(false)
        // pointLayer.setVisible(true)
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
        pointLayer.onDrag('dragstart', (e) => {
        })
        pointLayer.onDrag('dragend', (e) => {
        })
        pointLayer.onDrag('drag', (e) => {
          this.isMapChanged = true
          const nodeid = pointLayer.id
          let lat = e.coordinates.lat
          let lng = e.coordinates.lng
          const objIndex = this.pointDiemUonLayers.findIndex(obj => obj.id == nodeid)
          if (objIndex < 0) return
          this.pointDiemUonLayers[objIndex].position.lat = lat
          this.pointDiemUonLayers[objIndex].position.lng = lng
          this.pointDiemUonLayers[objIndex].modified = true
          const length = this.polylineLayers.length
          let copiedArray = new Array(length)
          for (let i = 0; i < length; i++) {
            let polyline = this.polylineLayers[i]
            if ((nodeid == polyline.dest || nodeid == polyline.source) || nodeid.toString().startsWith('DIEMUON_')) {
              let path = polyline.path.slice(0)
              for (let j = 0; j < path.length; j++) {
                if (path[j].id == nodeid) {
                  path[j].lat = lat
                  path[j].lng = lng
                  polyline.modified = !!nodeid.toString().startsWith('DIEMUON_')
                  break
                }
              }
              polyline.path = path
              copiedArray[i] = polyline
            } else {
              copiedArray[i] = polyline
            }
          }
          this.polylineLayers = copiedArray.slice(0)
          for (let index = 0; index < this.polylineLayers.length; index++) {
            let element = this.polylineLayers[index]
            let polylineList = []
            for (let index = 0; index < element.path.length; index++) {
              let p = element.path[index]
              polylineList.push([p.lng, p.lat])
            }
            element.setData(polylineList)
          }
        })
        pointLayer.on(this.mapVNPT, 'contextmenu', (e) => {
          this.point.x = e.pointEvent.x
          this.point.y = e.pointEvent.y
          me.rightclickNodeDiemUon(e, pointLayer.id)
        })
        this.mapVNPT.addLayer(pointLayer)
        this.pointDiemUonLayers.push(pointLayer)
      }
    },
    getNodeDiemUonFromID (point_id) {
      return this.pointDiemUonLayers.find(e => e.id === point_id)
    },
    rightclickNodeDiemUon (event, point_id) {
      let node = this.getNodeDiemUonFromID(point_id)
      this.$refs.vueSimpleContextMenuPolyline && this.$refs.vueSimpleContextMenuPolyline.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeDiemUon && this.$refs.vueSimpleContextMenuNodeDiemUon.hideContextMenu()
      this.$refs.vueSimpleContextMenuMap && this.$refs.vueSimpleContextMenuMap.hideContextMenu()
      this.$refs.vueSimpleContextMenuNode && this.$refs.vueSimpleContextMenuNode.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeDiemUon.showMenu(event, node)
      const menu = document.getElementById('menuNodeDiemUon')
      var left = event.pointEvent.layerX
      var top = event.pointEvent.layerY
      menu.style.left = left + 'px'
      menu.style.top = top + 'px'
      menu.position = 'fixed'
      menu.classList.add('menu-context')
    },
    ctrlDuyChuyenDoiTuong () {
      try {
        this.$root.showLoading(true)
        for (let index = 0; index < this.pointLayers.length; index++) {
          let element = this.pointLayers[index]
          element.setDraggable(!this.draggable)
        }
        this.draggable = !this.draggable
        this.$root.showLoading(false)
      } catch (error) {
        console.log('🚀 ~ file: BanDoVNPT.vue:366 ~ ctrlDuyChuyenDoiTuong ~ error:', error)
        this.$root.showLoading(false)
      }
    },
    setPolylines (polylines) {
      console.log('🚀 ~ setPolylines ~ polylines:', polylines)
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
        // let dashArray = polyline.style.split('-')
        // dashArray = dashArray.map(str => {
        //   return parseInt(str, 10)
        // })
        let dashArray = [2.6, 2.6]
        const obj = {
          id: polyline.id.toString(),
          color: '#EC3707',
          opacity: 1,
          weight: 3.5,
          lineCap: 'round',
          dashArray: dashArray
        }
        console.log('🚀 ~ setPolylines ~ obj:', obj)
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
          polylineLayer.setStyle({ color: '#0F55F8' })
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
          polylineLayer.setStyle({ color: '#EC3707' })
        })
        polylineLayer.on('contextmenu', (e) => {
          me.rightclickPolyline(e, polylineLayer.id)
        })
        polylineLayer.on('click', (e) => {
          if (e.originalEvent.detail === 2) {
            try {
              this.$emit('dblClickItem', {
                type: 'connectors',
                id: 'DC_' + polylineLayer.id
              })
            } catch (e) {
              console.log(e)
            }
          }
        })
        polylineLayer.on('mousedown', (e) => {
        })
        this.polylineLayers.push(polylineLayer)
      }
    },
    isHere: function () {
      // const latlng = {
      //   lat: parseFloat(this.atHere.split(',')[0]),
      //   lng: parseFloat(this.atHere.split(',')[1])
      // }
      // const geocoder = new window.google.maps.Geocoder()
      // geocoder
      //   .geocode({ location: latlng })
      //   .then((response) => {
      //     if (response.results[0]) {
      //       console.log('Đại chỉ:', response.results[0].formatted_address)
      //       this.$root.toastInfo('Đây là: ' + response.results[0].formatted_address)
      //     } else {
      //       this.$root.toastInfo('Không tìm thấy')
      //     }
      //   })
      //   .catch((e) => this.$root.toastError('Lỗi phát sinh: ' + e))
    },
    capnhatToaDo: async function (data) {
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.post('/web-cabman/BanDoTuyenCong/cap-nhat-toa-do-be-cap', data)
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
      if (this.beChuaToaDo != null) {
        console.log('beChuaToaDo', this.beChuaToaDo)
        let beId = this.beChuaToaDo.BECAP_ID
        let kinhdo = this.atHere.split(',')[1]
        let vido = this.atHere.split(',')[0]
        let obj = {
          beCapId: beId,
          kinhDo: kinhdo,
          viDo: vido
        }
        await this.capnhatToaDo(obj)
      }
    },
    map_LeftClick: async function (event) {
      if (this.isGhiToaDo) {
        this.atHere = event.latLng.lat() + ',' + event.latLng.lng()
        this.isGhiToaDo = false
        await this.ghiToaDo()
        EventBus.$emit('refeshBanDo', true)
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

  .mapVNPTBanDoTuyenCong {
    position: initial;
    height: 800px;
    width: 100%;
  }
  .map-canvas-box {
    overflow: hidden;
    height: calc(100%);
    width: 100%;
  }
  .vnptmaps-pointlabel
  {
    position: fixed;
    top: 22px;
    color: black;
    font-size: 11px;
    font-family: Arial;
    flex-wrap: nowrap;
    white-space : nowrap !important;
    text-shadow: 0.5px 0.5px white;
  }
</style>
