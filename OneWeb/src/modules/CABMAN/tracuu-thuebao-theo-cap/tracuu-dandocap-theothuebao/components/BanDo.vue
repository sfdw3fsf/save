<template>
  <div>
    <a href="javascript:void(0)" class="btn btn-main control-map" ref="ctrlPhanCapMang">
      <span class="-ap icon-settings"></span>
    </a>
    <a href="javascript:void(0)" class="btn btn-main control-map" ref="ctrlThayDoiNguonDuLieu">
        <span class="-ap icon-settings"></span>
    </a>
    <a href="javascript:void(0)" class="btn btn-main control-map" ref="ctrlLuuToaDoBanDo">
        <span class="nc-icon-glyph location_map-pin"></span>
    </a>
    <a href="javascript:void(0)" class="btn btn-main control-map" ref="ctrlDuyChuyenDoiTuong">
        <span class="one-drag"></span>
    </a>

    <div style="height: 800px;">
      <transition name="slide-fade" v-if="showSearchDiaDiem">
          <gmap-autocomplete  style="max-width: 600px;"
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
        elementId="menuNodeDiemUon"
        :options="optionsNodeDiemUonMenu"
        ref="vueSimpleContextMenuNodeDiemUon"
        @option-clicked="menuNodeDiemUon_Clicked"/>
      <vue-simple-context-menu
        elementId="menuMap"
        :options="optionsMapMenu"
        ref="vueSimpleContextMenuMap"
        @option-clicked="menuMap_Clicked"
      />
      <vue-simple-context-menu
        elementId="menuNode"
        :options="optionsNodeMenu"
        ref="vueSimpleContextMenuNode"
        @option-clicked="menuNode_Clicked"
      />
      <GmapMap
            id="map"
            ref="oneBSSGmap"
            :zoom="zoomLevel"
            :options="mapOptions"
            :center="getPositionCenter"
            @rightclick="rightclickMap($event)"
            style="width: 100%; height: calc(100% - 26px);">
              <GmapMarker
                  v-for="node in getlistNode"
                  :key="node.id"
                  :position="node.position"
                  :icon="node.getIcon()"
                  @mouseover="mouseoverNode($event, node)"
                  @mouseout="mouseoutNode($event, node)"
                  @drag="dragPositionNode($event,node)"
                  @rightclick="rightclickNode($event, node)"
                  @dragend="dragendPositionNode($event,node)"
                  :draggable="node.draggable && draggable"
                  :label="getLabel(node.symbol)">
              </GmapMarker>
              <!-- <GmapMarker
                  v-for="node in getlistNodeDiemUon"
                  :key="node.id"
                  :position="node.position"
                  :draggable="node.draggable"
                  @mouseover="mouseoverNodeDiemUon($event, node)"
                  @drag="dragPositionNode($event,node)"
                  @dragend="dragendPositionNode($event,node)"
                  @rightclick="rightclickNodeDiemUon($event, node)"
                  :icon="getIconDiemUon">
              </GmapMarker> -->
              <GmapPolyline
                  v-for="polyline in getlistPolyline"
                  :key="polyline.id"
                  :path.sync="polyline.path"
                  geodesic: true
                  @mouseover="mouseoverPolyline($event, polyline)"
                  @mouseout="mouseoutPolyline($event, polyline)"
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
import EventBus from '../../../../../utils/eventBus'
import html2canvas from 'html2canvas'

export default {
  components: {'vue-simple-context-menu': VueSimpleContextMenu, MeasureTool, html2canvas},
  name: 'BanDo',
  props: {
  },
  computed: {
    google: gmapApi,
    getPositionCenter () { return this.location },
    getlistNode () { return this.nodes.filter(n => n.visible === true && n.isDiemuon === false) },
    getlistNodeDiemUon () { return this.nodes.filter(n => n.visible === true && n.isDiemuon === true) },
    getlistPolyline () { return this.polylines },
    getIconDiemUon () {
      return {
        // path: 'M-20,0a20,20 0 1,0 40,0a20,20 0 1,0 -40,0',
        // fillColor: '#ffa500',
        // fillOpacity: 1,
        // anchor: new window.google.maps.Point(0, 0),
        // strokeWeight: 0,
        // scale: 0.4
        path: window.google.maps.SymbolPath.CIRCLE,
        fillColor: '#0a65b7',
        fillOpacity: 1,
        strokeColor: '#ffa500',
        strokeOpacity: 1,
        strokeWeight: 3,
        scale: 7
      }
    },
    optionsPolylineMenu () {
      var menu = []
      if (this.activePolyline) {
        console.log('activePolyline', this.activePolyline)
        if (!this.activePolyline.modified) {
          return [...this.optionsPolyline]
        } else {
          return [...this.optionsPolyline2]
        }
      } else return [...this.optionsPolyline]
    },
    optionsNodeDiemUonMenu () {
      return [{
        name: '<span class="icon text-main f20 one-trash"></span> Xoá điểm uốn',
        slug: 'xoa_diem_uon'
      }]
    }
  },
  async created () {
    await this.$gmapApiPromiseLazy()
    EventBus.$on('IN_BANDO', this.inBando)
  },
  destroyed () {
    EventBus.$off('IN_BANDO', this.inBando)
  },
  data () {
    return {
      zoomLevel: 20,
      draggable: false,
      isMapChanged: false,
      mapOptions: {
        zoomControl: false,
        scaleControl: true,
        disableDefaultUi: true,
        streetViewControl: false,
        mapTypeControl: false,
        fullscreenControl: false,
        maxZoom: this.maxZoom,
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
      diemuonLatlng: null,
      loadingMap: true,
      searchDiaDiem: null,
      activePolyline: null,
      showSearchDiaDiem: true,
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
          name: '<span class="icon text-main f20 one-daucuoi"></span> Tạo kết cuối mới và nối cáp tới',
          slug: 'tao_ket_cuoi'
        },
        {
          name: '<span class="icon text-main f20 one-trash"></span> Tạo cáp kết nối kết cuối khác tuyến',
          slug: 'edit'
        },
        {
          name: '<span class="icon text-main f20 one-daochieu"></span> Đấu nối bên trong',
          slug: 'edit'
        },
        {
          name: '<span class="icon text-main f20 one-chuyencap"></span> Chuyển vào trong kết cuối/trạm thiết bị',
          slug: 'edit'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 one-taodiemuon"></span> Thuê bao trên kết cuối',
          slug: 'edit'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 one-chenbe"></span> Xóa kết cuối',
          slug: 'xoa_ket_cuoi'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 -ap icon-info"></span> Thuộc tính',
          slug: 'view_ket_cuoi'
        }
      ],
      optionsMapMenu: [
        {
          name: '<span class="icon text-main f20 one-daucuoi"></span> Làm tươi',
          slug: 'lam_tuoi'
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
          name: '<span class="icon text-main f20 one-daucuoi"></span> Đấu nối 2 đầu cáp',
          slug: 'duplicate'
        },
        {
          name: '<span class="icon text-main f20 one-taomid"></span> Tạo Mid-Span',
          slug: 'duplicate'
        },
        {
          name: '<span class="icon text-main f20 one-trash"></span> Xoá cáp',
          slug: 'xoa_cap'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 one-trash"></span> Sửa tọa độ cáp',
          slug: 'sua_toado_cap'
        },
        {
          name: '<span class="icon text-main f20 one-chuyencap"></span> Chuyển nhánh cáp',
          slug: 'delete'
        },
        {
          name: '<span class="icon text-main f20 one-chenbe"></span> Chèn kết cuối',
          slug: 'chen_ket_cuoi'
        },
        {
          name: '<span class="icon text-main f20 one-daochieu"></span> Đảo chiều cáp',
          slug: 'dao_chieu_cap'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 -ap icon-apps"></span> Thuê bao đi qua cáp',
          slug: 'delete'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 -ap icon-info"></span> Thuộc tính',
          slug: 'delete'
        }
      ],
      optionsPolyline2: [
        {
          name: '<span class="icon text-main f20 one-daucuoi"></span> Đấu nối 2 đầu cáp',
          slug: 'duplicate'
        },
        {
          name: '<span class="icon text-main f20 one-trash"></span> Xoá cáp',
          slug: 'edit'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 one-taodiemuon"></span> Tạo điểm uốn',
          slug: 'tao_diem_uon'
        },

        {
          name: '<span class="icon text-main f20 one-chuyencap"></span> Chuyển nhánh cáp',
          slug: 'delete'
        },
        {
          name: '<span class="icon text-main f20 one-chenbe"></span> Chèn kết cuối',
          slug: 'delete'
        },
        {
          name: '<span class="icon text-main f20 one-daochieu"></span> Đảo chiều cáp',
          slug: 'dao_chieu_cap'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 -ap icon-apps"></span> Thuê bao đi qua cáp',
          slug: 'delete'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 -ap icon-info"></span> Thuộc tính',
          slug: 'delete'
        }
      ]
    }
  },
  watch: {
    polylines: {
      handler: function (val) {
        console.log('watch polylines', val)
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
        console.log('watch nodes', val)
      },
      deep: true}
  },
  async  mounted () {
    let vm = this
    this.$refs.oneBSSGmap.$mapPromise.then((map) => {
      vm.map = map
      let ctrlDuyChuyenDoiTuong = this.$refs.ctrlDuyChuyenDoiTuong
      map.controls[this.google.maps.ControlPosition.TOP_RIGHT].push(ctrlDuyChuyenDoiTuong)
      ctrlDuyChuyenDoiTuong.addEventListener('click', () => {
        this.draggable = !this.draggable
      })
      let ctrlPhanCapMang = this.$refs.ctrlPhanCapMang
      map.controls[this.google.maps.ControlPosition.RIGHT_TOP].push(ctrlPhanCapMang)
      ctrlPhanCapMang.addEventListener('click', () => {
      })
      let ctrlLuuToaDoBanDo = this.$refs.ctrlLuuToaDoBanDo
      map.controls[this.google.maps.ControlPosition.RIGHT_TOP].push(ctrlLuuToaDoBanDo)
      ctrlLuuToaDoBanDo.addEventListener('click', () => {
        this.luuToaDoBanDo()
      })
      let ctrlThayDoiNguonDuLieu = this.$refs.ctrlThayDoiNguonDuLieu
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
        console.log('started')
        polygon.setOptions({clickable: false})
      })
      this.measureTool.addListener('measure_end', (e) => {
        console.log('ended', e.result)
        polygon.setOptions({clickable: true})
      })
      this.measureTool.addListener('measure_change', (e) => {
        console.log('changed', e.result)
      })
    })
  },
  methods: {
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
      console.log('fromLatLngToPoint', point)
      return new window.google.maps.Point(
        Math.floor((point.x - nw.x) * scale),
        Math.floor((point.y - nw.y) * scale))
    },
    rightclickPolyline (event, polyline) {
      console.log('rightclickPolyline', event, polyline)
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
    rightclickNodeDiemUon (event, node) {
      console.log('rightclickNodeDiemUon', event, node)
      this.$refs.vueSimpleContextMenuMap && this.$refs.vueSimpleContextMenuMap.hideContextMenu()
      this.$refs.vueSimpleContextMenuNode && this.$refs.vueSimpleContextMenuNode.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolyline && this.$refs.vueSimpleContextMenuPolyline.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeDiemUon && this.$refs.vueSimpleContextMenuNodeDiemUon.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeDiemUon.showMenu(event, node)
      const menu = document.getElementById('menuNodeDiemUon')
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
      console.log('rightclickMap', event)
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
      menu.style.left = left + 'px'
      menu.style.top = top + 'px'
      menu.position = 'fixed'
      menu.classList.add('menu-context')
    },
    rightclickNode (event, node) {
      console.log('rightclickNode', event, node)
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
    insertAt (arr, index, newItem) {
      return [
        ...arr.slice(0, index),
        newItem,
        ...arr.slice(index)
      ]
    },
    menuNode_Clicked (event) {
      switch (event.option.slug) {
        case 'xoa_ket_cuoi':
          this.$emit('xoaKetcuoi', event.item.id)
          break
        case 'view_ket_cuoi':
          this.$emit('thongtinKetcuoi', event.item.id)
          break
        case 'tao_ket_cuoi':
          console.log('tao_ket_cuoi', event.item.id)
          this.$emit('taoKetcuoi', event.item.id)
          break
      }
    },
    menuNodeDiemUon_Clicked (event) {
      console.log('menuNodeDiemUon_Clicked', event)
      switch (event.option.slug) {
        case 'xoa_diem_uon':
          const nodeid = event.item.id
          const idpolyline = event.item.id_polyline
          const objIndex = this.nodes.findIndex(obj => obj.id === nodeid)
          if (objIndex > -1) {
            const idx = this.polylines.findIndex(obj => obj.id === idpolyline)
            if (idx < 0) return
            var path = this.polylines[idx].path.splice(0)
            const idx2 = path.findIndex(obj => obj.id === nodeid)
            if (idx2 > -1) {
              path.splice(idx2, 1)
              this.polylines[idx].path = path.splice(0)
              this.polylines[idx].modified = true
              this.nodes.splice(objIndex, 1)
            }
          }
          break
      }
    },
    menuMap_Clicked (event) {
      console.log('menuMap_Clicked', event)
      switch (event.option.slug) {
        case 'lam_tuoi':
          EventBus.$emit('NAP_GIANDO', 'NAP_GIANDO')
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
      console.log('menuPolyline_Clicked', event)
      switch (event.option.slug) {
        case 'chen_ket_cuoi':
          this.$emit('chenketcuoi', event.item.id)
          break
        case 'dao_chieu_cap':
          this.$emit('daochieuCap', event.item.id)
          break
        case 'xoa_cap':
          this.$emit('xoaCap', event.item.id)
          break
        case 'sua_toado_cap':
          event.item.modified = true
          for (let index = 0; index < this.nodes.length; index++) {
            if (this.nodes[index].id_polyline === event.item.id && this.nodes[index].isDiemuon === true) {
              console.log('menuPolyline_Clicked node', this.nodes[index])
              this.nodes[index].visible = true
            }
          }
          break
        case 'tao_diem_uon':
          const id_diemuon = this.diemuonAdd(event.item.id, this.diemuonLatlng.lat() + ',' + this.diemuonLatlng.lng(), true, true)
          for (let i = 0; i < event.item.path.length - 1; i++) {
            let node1 = event.item.path[i]
            let node2 = event.item.path[i + 1]
            let latLng1 = new window.google.maps.LatLng(node1.lat, node1.lng)
            let latLng2 = new window.google.maps.LatLng(node2.lat, node2.lng)
            const polylineCoords = [latLng1, latLng2]
            var polyline = new window.google.maps.Polyline({ path: polylineCoords })
            let chk = this.google.maps.geometry.poly.isLocationOnEdge(this.diemuonLatlng, polyline, 0.00001)
            if (chk) {
              let p = {
                id: id_diemuon,
                lat: this.diemuonLatlng.lat(),
                lng: this.diemuonLatlng.lng()
              }
              const result = this.insertAt(event.item.path, i + 1, p)
              event.item.path = result.slice(0)
              event.item.modified = true
              break
            }
          }
          break
      }
    },
    dragendPositionNode (event, node) {},
    dragPositionNode (event, node) {
      try {
        console.log('dragPositionNode', event, node)
        this.isMapChanged = true
        const nodeid = node.id
        let lat = event.latLng.lat()
        let lng = event.latLng.lng()
        console.log(lat, lng);
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
      return {'text': symbol, 'color': '#333333', 'fontSize': '12px', 'fontFamily': 'Roboto, Arial', 'fontWeight': 'bold'}
    },
    mouseoverNode (event, node) {
      console.log('mouseoverNode', event, node)
      node.mouseover = true
      if (node.id.startsWith('DIEMUON_')) {
        this.infoOpenedNode = false
        this.infoOpenedPolyline = false
      }
      this.infoPositionNode = event.latLng
      this.infoContentNode = node.tooltipText
      this.infoOpenedNode = true
      this.infoOpenedPolyline = false
    },
    mouseoverNodeDiemUon (event, node) {
      console.log('mouseoverNodeDiemUon', event, node)
      this.infoOpenedNode = false
      this.infoOpenedPolyline = false
    },
    mouseoutNode (event, node) {
      console.log('mouseoutNode', event, node)
      node.mouseover = false
      this.infoOpenedNode = false
      this.infoOpenedPolyline = false
    },
    mouseoverPolyline (event, polyline) {
      console.log('mouseoverPolyline', event, polyline)
      polyline.mouseover = true
      this.infoPositionNode = event.latLng
      this.infoContentNode = polyline.label
      this.infoOpenedNode = true
      this.infoOpenedPolyline = false
    },
    mouseoutPolyline (event, polyline) {
      console.log('mouseoutPolyline', event, polyline)
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
      console.log('luuToaDoBanDo', nodes, polylines)
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
          console.log('getPolylinePath', position)
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
    inBando (file) {
      var fin = document.getElementById('map')
      console.log('inBando', fin)
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
</style>
