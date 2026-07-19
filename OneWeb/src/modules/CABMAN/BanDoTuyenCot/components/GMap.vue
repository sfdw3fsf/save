<template>
  <div>
    <div style="height: 800px;">
      <transition name="slide-fade" v-if="showSearchDiaDiem">
          <gmap-autocomplete
            ref="SearchDiaDiem"
            placeholder="Gõ để tìm kiếm địa điểm"
            class="form-control"
            :options="{fields: ['geometry', 'formatted_address', 'address_components']}"
            @place_changed="onSearchDiaDiem"/>
      </transition>
      <vue-simple-context-menu
        elementId="menuPolyline"
        :options="optionsPolylineMenu"
        ref="vueSimpleContextMenuPolyline"
        @option-clicked="menuPolyline_Clicked"/>
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
      <GmapMap
            id="map"
            ref="oneBSSGmap"
            :zoom="zoomLevel"
            :options="mapOptions"
            :center="getPositionCenter"
            @rightclick="rightclickMap($event)"
            @click="clickMap($event)"
            style="width: 100%; height: calc(100% - 26px);">
              <GmapMarker
                  v-for="node in getlistNode"
                  :key="node.id"
                  :position="node.position"
                  :icon="node.getIcon()"
                  @mouseover="mouseoverNode($event, node)"
                  @mouseout="mouseoutNode($event, node)"
                  @drag="dragPositionNode($event,node)"
                  @click="leftclickNode($event, node)"
                  @rightclick="rightclickNode($event, node)"
                  @dragend="dragendPositionNode($event,node)"
                  :draggable="node.draggable && draggable"
                  :label="getLabel(node.symbol)">
              </GmapMarker>
              <GmapPolyline
                  v-for="polyline in getlistPolyline"
                  :key="polyline.id"
                  :path.sync="polyline.path"
                  geodesic: true
                  :label="getLabel(polyline.label)"
                  @mouseover="mouseoverPolyline($event, polyline)"
                  @mouseout="mouseoutPolyline($event, polyline)"
                  @dblclick="dblclickPolyline($event, polyline)"
                  @rightclick="rightclickPolyline($event, polyline)"
                  :options="polyline.polylineOptions()">
              </GmapPolyline>
              <GmapInfoWindow
                :options="infoOptionsNode"
                :position="infoPositionNode"
                :opened="infoOpenedNode"
                @closeclick="infoOpenedNode = false">
                <span v-html="infoContentNode"></span>
              </GmapInfoWindow>
              <GmapInfoWindow
                :options="infoOptionsPolyline"
                :position="infoPositionPolyline"
                :opened="infoOpenedPolyline"
                @closeclick="infoOpenedPolyline = false">
                <span v-html="infoContentPolyline"></span>
              </GmapInfoWindow>
      </GmapMap>
    </div>
  </div>
</template>
<script>

import GraphNode from '../structures/GraphNode'
import GraphPolyline from '../structures/GraphPolyline'
import {gmapApi} from 'gmap-vue'
import VueSimpleContextMenu from 'vue-simple-context-menu'
import MeasureTool from 'measuretool-googlemaps-v3'
import EventBus from '@/utils/eventBus'
export default {
  components: {'vue-simple-context-menu': VueSimpleContextMenu},
  name: 'GMap',
  props: {
  },
  computed: {
    google: gmapApi,
    getPositionCenter () { return this.location },
    getlistNode () { return this.nodes.filter(n => n.visible === true) },
    getlistPolyline () { return this.polylines },
    optionsPolylineMenu () {
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
  async created () {
    await this.$gmapApiPromiseLazy()
    await this.getThongTinBanDo()
  },
  data () {
    return {
      zoomLevel: 15,
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
        mapTypeId: 'roadmap',
        draggableCursor: 'default',
        draggingCursor: 'pointer'
      },
      nodes: [],
      polylines: [],
      location: {lat: -1, lng: -1},
      mouselatLng: {},
      maxZoom: 25,
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
      if (val) { this.mapOptions.mapTypeId = 'satellite' } else { this.mapOptions.mapTypeId = 'roadmap' }
    }

  },
  async  mounted () {
    let vm = this
    this.$refs.oneBSSGmap.$mapPromise.then((map) => {
      vm.map = map
      const ctrlDuyChuyenDoiTuong = document.createElement('a')
      ctrlDuyChuyenDoiTuong.setAttribute('id', 'ctrlDuyChuyenDoiTuong')
      ctrlDuyChuyenDoiTuong.setAttribute('title', 'Di chuyển đối tượng')
      ctrlDuyChuyenDoiTuong.setAttribute('class', 'btn btn-main control-map')
      ctrlDuyChuyenDoiTuong.innerHTML = '<span class="one-drag"></span>'
      map.controls[this.google.maps.ControlPosition.TOP_RIGHT].push(ctrlDuyChuyenDoiTuong)
      ctrlDuyChuyenDoiTuong.addEventListener('click', () => {
        this.draggable = !this.draggable
      })

      const ctrlLuuToaDoBanDo = document.createElement('a')
      ctrlLuuToaDoBanDo.setAttribute('id', 'ctrlLuuToaDoBanDo')
      ctrlLuuToaDoBanDo.setAttribute('title', 'Lưu tọa độ bản đồ')
      ctrlLuuToaDoBanDo.setAttribute('class', 'btn btn-main control-map')
      ctrlLuuToaDoBanDo.innerHTML = '<span class="-ap icon-table"></span>'
      map.controls[this.google.maps.ControlPosition.RIGHT_TOP].push(ctrlLuuToaDoBanDo)
      ctrlLuuToaDoBanDo.addEventListener('click', () => {
        this.luuToaDoBanDo()
      })

      const ctrlThayDoiNguonDuLieu = document.createElement('a')
      ctrlThayDoiNguonDuLieu.setAttribute('id', 'ctrlThayDoiNguonDuLieu')
      ctrlThayDoiNguonDuLieu.setAttribute('title', 'Thay đổi nguồn dữ liệu bản đồ')
      ctrlThayDoiNguonDuLieu.setAttribute('class', 'btn btn-main control-map')
      ctrlThayDoiNguonDuLieu.innerHTML = '<span class="-ap icon-settings"></span>'
      map.controls[this.google.maps.ControlPosition.RIGHT_TOP].push(ctrlThayDoiNguonDuLieu)
      ctrlThayDoiNguonDuLieu.addEventListener('click', () => {
        if (vm.isMapChanged) vm.isMapChanged = false
        else vm.isMapChanged = true
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
        polygon.setOptions({clickable: false})
      })
      this.measureTool.addListener('measure_end', (e) => {
        polygon.setOptions({clickable: true})
      })
      this.measureTool.addListener('measure_change', (e) => {
      })
      window.addEventListener('keydown', function (e) {
        if (vm.ischonCot) {
          if (e.key == 'Escape') {
            vm.isChonCot = false
            vm.isGhiToaDo = false
            vm.isGhimToaDo = false
            vm.$toast.info('Đã hủy thao tác liên kết cột')
          }
        }
        if (vm.isGhiToaDo) {
          if (e.key == 'Escape') {
            vm.isGhiToaDo = false
            vm.isChonCot = false
            vm.isGhimToaDo = false
            vm.$toast.info('Đã hủy thao tác')
          }
        }
        if (vm.isGhimToaDo) {
          if (e.key == 'Escape') {
            vm.isGhimToaDo = false
            vm.isChonCot = false
            vm.isGhiToaDo = false
            vm.$toast.info('Đã hủy thao tác')
          }
        }
      })
    })
    EventBus.$on('ghiToaDo', (args) => {
      vm.isGhiToaDo = true
      vm.cotChuaToaDo = args
    })
    EventBus.$on('ghimToaDo', (args) => {
      vm.isGhimToaDo = true
    })
  },
  destroyed () {
    EventBus.$off('ghiToaDo')
    EventBus.$off('ghimToaDo')
  },
  methods: {
    reload () {
      this.bounds = new window.google.maps.LatLngBounds()
      this.map.setZoom(15)
      this.map.setCenter(
        new window.google.maps.LatLng(
          this.location.lat,
          this.location.lng
        )
      )
    },
    getThongTinBanDo: async function () {
      try {
        var rs = await this.$root.context.get(
          '/web-cabman/bando_matdo/thongtin_bando'
        )
        if (rs.data != null && rs.data.length > 0) {
          this.thongtinbando = rs.data[0]
          this.location = {
            lat: rs.data[0].C_LATITUS,
            lng: rs.data[0].C_LONGTITUS
          }
          this.zoomLevel = rs.data[0].DEFAULT_ZOOM
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    setZoomMap: function (step) {
      let zoomCurrent = this.map.getZoom()
      if (step == 1) { this.map.setZoom(zoomCurrent + 1) } else if (step == -1) { this.map.setZoom(zoomCurrent - 1) } else { this.map.setZoom(this.zoomLevel) }
    },
    latLng2Point (latLng, map) {
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
    rightclickPolyline (event, polyline) {
      this.diemuonLatlng = event.latLng
      this.activePolyline = polyline
      this.mouselatLng.lat = event.latLng.lat()
      this.mouselatLng.lng = event.latLng.lng()
      this.$refs.vueSimpleContextMenuMap && this.$refs.vueSimpleContextMenuMap.hideContextMenu()
      this.$refs.vueSimpleContextMenuNode && this.$refs.vueSimpleContextMenuNode.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolyline && this.$refs.vueSimpleContextMenuPolyline.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeDiemUon && this.$refs.vueSimpleContextMenuNodeDiemUon.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolyline.showMenu(event, polyline)
      const menu = document.getElementById('menuPolyline')
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
    rightclickMap (event) {
      this.$refs.vueSimpleContextMenuMap && this.$refs.vueSimpleContextMenuMap.hideContextMenu()
      this.$refs.vueSimpleContextMenuNode && this.$refs.vueSimpleContextMenuNode.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolyline && this.$refs.vueSimpleContextMenuPolyline.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeDiemUon && this.$refs.vueSimpleContextMenuNodeDiemUon.hideContextMenu()
      this.$refs.vueSimpleContextMenuMap.showMenu(event)
      const menu = document.getElementById('menuMap')
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
      this.atHere = event.latLng.lat() + ',' + event.latLng.lng()
      menu.style.left = left + 'px'
      menu.style.top = top + 'px'
      menu.position = 'fixed'
      menu.classList.add('menu-context')
    },
    rightclickNode (event, node) {
      this.$refs.vueSimpleContextMenuMap && this.$refs.vueSimpleContextMenuMap.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeDiemUon && this.$refs.vueSimpleContextMenuNodeDiemUon.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolyline && this.$refs.vueSimpleContextMenuPolyline.hideContextMenu()
      this.$refs.vueSimpleContextMenuNode.showMenu(event, node)
      const menu = document.getElementById('menuNode')
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
    leftclickNode (event, node) {
      if (this.ischonCot) {
        let cot_id = node.id.split('_')[1]
        this.$emit('getcotSau', cot_id)
      }
      this.ischonCot = false
    },
    dblclickPolyline ($event, polyline) {
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
      } catch (e) {}
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
        value: event.item.id
      }
      this.$emit('node_action', data)
    },
    menuMap_Clicked (event) {
      switch (event.option.slug) {
        case 'lamtuoi':
          // this.showSearchDiaDiem = !this.showSearchDiaDiem
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
        case 'huylienket':
          type_action = 0
          break
        case 'daochieu':
          type_action = 1
          break
      }
      let data = {
        type: type_action,
        value: event.item.id
      }
      this.$emit('polyline_action', data)
    },
    dragendPositionNode (event, node) {},
    dragPositionNode (event, node) {
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

        const length = this.polylines.length
        let copiedArray = new Array(length)
        for (let i = 0; i < length; i++) {
          let polyline = this.polylines[i]
          if ((nodeid === polyline.dest || nodeid === polyline.source) || nodeid.startsWith('DIEMUON_')) {
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
      var opt = {strokeOpacity: 0, geodesic: true}

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
    getLabel (symbol) {
      // return {'text': symbol, 'color': '#333333', 'fontSize': '12px', 'fontFamily': 'Roboto, Arial', 'fontWeight': 'bold'}
      return null
    },
    mouseoverNode (event, node) {
      node.mouseover = true
      this.infoPositionNode = event.latLng
      this.infoContentNode = node.tooltipText
      this.infoOpenedNode = true
      this.infoOpenedPolyline = false
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
      this.infoContentNode = polyline.label
      this.infoOpenedNode = true
      this.infoOpenedPolyline = false
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
      for (let index = 0; index < this.nodes.length; index++) {
        const node = this.nodes[index]
        if (node.modified && !node.id.startsWith('DIEMUON_')) {
          let arr = (node.getID()).split('_')
          nodes.push({loai: arr[0], id: arr[1], lat: node.getPosition().lat.toString(), lng: node.getPosition().lng.toString()})
        }
      }
      var polylines = []
      for (let index = 0; index < this.polylines.length; index++) {
        const polyline = this.polylines[index]
        if (polyline.modified) {
          polylines.push({id: polyline.id,
            day_toado: (polyline.path.filter(element => {
              return element.id.startsWith('DIEMUON_')
            }))})
        }
      }
      this.$emit('luuToaDoBanDo', nodes, polylines)
    },
    dragendPosition (event, node) {
      this.isMapChanged = true
    },
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
      var node = new GraphNode()
      return node
    },
    create_UUID () {
      var dt = new Date().getTime()
      var uuid = 'xxxxxxxx_xxxx_4xxx_yxxx_xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = (dt + Math.random() * 16) % 16 | 0
        dt = Math.floor(dt / 16)
        return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16)
      })
      return uuid
    },
    diemuonAdd (id_polyline, position, visible, draggable) {
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
    getNodes () {
      return this.nodes
    },
    getPolylines () {
      return this.polylines
    },
    nodeUpdate (node) {
      const objIndex = this.nodes.findIndex(obj => obj.id === node.id)
      if (objIndex >= 0) return false
      this.nodes.push(node)
      return true
    },
    setLocationEx (position) {
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
    clear () {
      this.nodes.length = 0
      this.nodes = []
      this.polylines.length = 0
      this.polylines = []
    },
    polylineCreate () {
      var polyline = new GraphPolyline()
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
    getIsMapChanged () {
      return this.isMapChanged
    },
    setNodes (nodes) {
      this.nodes = nodes
    },
    setPolylines (polylines) {
      this.polylines = polylines
    },
    isHere: function () {
      const latlng = {
        lat: parseFloat(this.atHere.split(',')[0]),
        lng: parseFloat(this.atHere.split(',')[1])
      }
      const geocoder = new window.google.maps.Geocoder()
      geocoder
        .geocode({ location: latlng })
        .then((response) => {
          if (response.results[0]) {
            this.$root.toastInfo('Đây là: ' + response.results[0].formatted_address)
          } else {
            this.$root.toastInfo('Không tìm thấy địa chỉ cần tìm')
          }
        })
        .catch((e) => this.$root.toastError('Lỗi phát sinh: ' + e))
    },
    capnhatToaDo: async function (data) {
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.post(
          '/web-cabman/bando_tuyencot/capnhat_toado', data
        )
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
        this.atHere = event.latLng.lat() + ',' + event.latLng.lng()
        this.isGhiToaDo = false
        await this.ghiToaDo()
        EventBus.$emit('refeshBanDo', true)
      }
      if (this.isGhimToaDo) {
        this.isGhimToaDo = false
        EventBus.$emit('GhimToaDoBanDoTuyenCot', {lat: event.latLng.lat(), lng: event.latLng.lng()})
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
</style>
