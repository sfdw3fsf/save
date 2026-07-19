<template>
  <div style="height: 100%;">
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
    <vue-simple-context-menu
      elementId="menuNodeTram"
      :options="optionsNodeTramMenu"
      ref="vueSimpleContextMenuNodeTram"
      @option-clicked="menuNodeTram_Clicked"
    />
    <DaunoiThietBi id="bpopupDauNoiTbi" :doiTuong="doiTuong"></DaunoiThietBi>
    <GmapMap
          id="map"
          ref="oneBSSGmap"
          :zoom="zoomLevel"
          @idle="onFirstIdle"
          :options="mapOptions"
          :center="getPositionCenter"
          @click="leftclickMap($event)"
          @mousemove="mousemoveMap()"
          @rightclick="rightclickMap($event)"
          style="width: 100%; height: calc(100% - 0px);">
            <GmapMarker
                   v-for="node in getNodesearchs"
                  :key="node.id"
                  :draggable="false"
                  @mouseover="mouseoverNode($event, node)"
                  @mouseout="mouseoutNode($event, node)"
                  :position="node.position"
                  :icon="getMarkerIconSearch()">
            </GmapMarker>
            <GmapCluster v-if="clustering" imagePath="https://raw.githubusercontent.com/googlemaps/v3-utility-library/37c2a570c318122df57b83140f5f54665b9359e5/packages/markerclustererplus/images/m"
            :zoomOnClick="true" :maxZoom="17" @click="resolveCluster">
              <GmapMarker
                v-for="node in getlistNode"
                :key="node.id"
                :position="node.position"
                :icon="node.getIcon()"
                :optimized= "true"
                @mouseover="mouseoverNode($event, node)"
                @mouseout="mouseoutNode($event, node)"
                @drag="dragPositionNode($event,node)"
                @dblclick="dblclickNode($event, node)"
                @rightclick="rightclickNode($event, node)"
                @dragend="dragendPositionNode($event,node)"
                :draggable="node.draggable && draggable"
                :label="getLabel(node.symbol)">
              </GmapMarker>
              <GmapMarker
                v-for="node in getlistNodeDiemUon"
                :key="node.id"
                :position="node.position"
                :draggable="node.draggable"
                @mouseover="mouseoverNodeDiemUon($event, node)"
                @drag="dragPositionNode($event,node)"
                @dragend="dragendPositionNode($event,node)"
                @rightclick="rightclickNodeDiemUon($event, node)"
                :icon="getIconDiemUon">
              </GmapMarker>
            </GmapCluster>
            <div v-if="!clustering">
              <GmapMarker
                v-for="node in getlistNode"
                :key="node.id"
                :position="node.position"
                :icon="node.getIcon()"
                :optimized= "true"
                @mouseover="mouseoverNode($event, node)"
                @mouseout="mouseoutNode($event, node)"
                @drag="dragPositionNode($event,node)"
                @dblclick="dblclickNode($event, node)"
                @rightclick="rightclickNode($event, node)"
                @dragend="dragendPositionNode($event,node)"
                :draggable="node.draggable && draggable"
                :label="getLabel(node.symbol)">
              </GmapMarker>
              <GmapMarker
                v-for="node in getlistNodeDiemUon"
                :key="node.id"
                :position="node.position"
                :draggable="node.draggable"
                @mouseover="mouseoverNodeDiemUon($event, node)"
                @drag="dragPositionNode($event,node)"
                @dragend="dragendPositionNode($event,node)"
                @rightclick="rightclickNodeDiemUon($event, node)"
                :icon="getIconDiemUon">
              </GmapMarker>
            </div>
            <GmapPolyline
                v-for="polyline in getlistPolyline"
                :key="polyline.id"
                :path.sync="polyline.path"
                geodesic: true
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
</template>
<script>
// imagePath="https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m"
import Vue from 'vue'
import { mapActions, mapState, mapGetters } from 'vuex'
import { getterName, actionName, statePropertyName } from './../store'
import GraphNode from '../structures/GraphNode'
import GraphPolyline from '../structures/GraphPolyline'
import { gmapApi } from 'gmap-vue'
import VueSimpleContextMenu from 'vue-simple-context-menu'
import MeasureTool from 'measuretool-googlemaps-v3'
import EventBus from '../../../../utils/eventBus'
import html2canvas from 'html2canvas'
import DaunoiThietBi from '../../daunoi-thietbi/Modal'
import GmapCluster from 'gmap-vue/dist/components/cluster'
Vue.component('GmapCluster', GmapCluster)
export default {
  components: {'vue-simple-context-menu': VueSimpleContextMenu, MeasureTool, html2canvas, DaunoiThietBi},
  name: 'BanDo',
  props: {
  },
  computed: {
    google: gmapApi,
    getNodesearchs () {
      return this.nodesearchs
    },
    getPositionCenter () { return this.location },
    getlistNode () {
      return this.nodes.filter(n => n.visible === true && n.isDiemuon === false && n.position.lat * n.position.lng > 0)
    },
    getlistNodeDiemUon () { return this.nodes.filter(n => n.visible === true && n.isDiemuon === true) },
    getlistPolyline () {
      var filteredArray = this.polylines.filter(function (element) {
        let l = element.path.length
        if (l > 1) {
          if ((element.path[0].lat * element.path[0].lng > 0) && (element.path[l - 1].lat * element.path[l - 1].lng > 0)) {
            return true
          } else {
            return false
          }
        } else return false
      })
      return filteredArray
    },
    getIconDiemUon () {
      return {
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
    EventBus.$on('GHIM_KETCUOI', this.ghimKetCuoi)
    EventBus.$on('BANDO_PhanCapMang', this.ctrlPhanCapMang)
    EventBus.$on('BANDO_DuyChuyenDoiTuong', this.ctrlDuyChuyenDoiTuong)
    EventBus.$on('BANDO_LuuToaDoBanDo', this.ctrlLuuToaDoBanDo)
    EventBus.$on('BANDO_ThayDoiNguonDuLieu', this.ctrlThayDoiNguonDuLieu)
    EventBus.$on('ACTIVE_OBJECT_MAP_V2', this.ACTIVE_OBJECT_MAP_V2)
  },
  destroyed () {
    EventBus.$off('IN_BANDO', this.inBando)
    EventBus.$off('GHIM_KETCUOI', this.ghimKetCuoi)
    EventBus.$off('BANDO_PhanCapMang', this.ctrlPhanCapMang)
    EventBus.$off('BANDO_DuyChuyenDoiTuong', this.ctrlDuyChuyenDoiTuong)
    EventBus.$off('BANDO_LuuToaDoBanDo', this.ctrlLuuToaDoBanDo)
    EventBus.$off('BANDO_ThayDoiNguonDuLieu', this.ctrlThayDoiNguonDuLieu)
    EventBus.$off('ACTIVE_OBJECT_MAP_V2', this.ACTIVE_OBJECT_MAP_V2)
  },
  data () {
    return {
      clustering: true,
      nodesearchs: [],
      abc: null,
      ketcuoi_ghim: null,
      ghimNode: false,
      mapTypeId: 'ROADMAP',
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
        minZoom: 5,
        disableDoubleClickZoom: true,
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
      locationrightclickMap: null,
      searchDiaDiem: null,
      activePolyline: null,
      showSearchDiaDiem: false,
      infoOptionsNode: {
        shouldFocus: false,
        pixelOffset: {
          width: 0,
          height: -10
        }
      },
      infoOptionsPolyline: {
        pixelOffset: {
          width: 0,
          height: -10
        }
      },
      infoContentNode: null,
      infoContentPolyline: null,
      infoPositionNode: null,
      infoPositionPolyline: null,
      infoOpenedNode: false,
      infoOpenedPolyline: false,
      optionsNodeTramMenu: [
        {
          name: '<span class="icon text-main f20 one-daunoi"></span> Đấu nối thiết bị',
          slug: 'dau_noi_thiet_bi_tram'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 -ap icon-info"></span> Thuộc tính',
          slug: 'sua_tram'
        }
      ],
      optionsNodeMenu: [
        {
          name: '<span class="icon text-main f20 one-daunoi"></span> Tạo kết cuối mới và nối cáp tới',
          slug: 'tao_ket_cuoi'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 one-taokn"></span> Tạo cáp kết nối kết cuối khác tuyến',
          slug: 'tao_cap_ket_noi_ketcuoi_khactuyen'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 one-daunoi1"></span> Đấu nối bên trong',
          slug: 'dau_noi_ben_trong'
        },
        {
          name: '<span class="icon text-main f20 one-chuyen"></span> Chuyển vào trong kết cuối/trạm thiết bị',
          slug: 'chuyen_vao_trong_tram'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 -ap icon-apps"></span> Thuê bao trên kết cuối',
          slug: 'thue_bao_thuoc_kc'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 one-trash"></span> Xóa kết cuối',
          slug: 'xoa_ket_cuoi'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 -ap icon-info"></span> Thuộc tính',
          slug: 'sua_ket_cuoi'
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
          slug: 'day_la_dau'
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
          slug: 'dau_noi_cap'
        },
        {
          name: '<span class="icon text-main f20 one-trash"></span> Xoá cáp',
          slug: 'xoa_cap'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 one-taodiemuon"></span> Sửa tọa độ cáp',
          slug: 'sua_toado_cap'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 one-chuyencap"></span> Chuyển nhánh cáp',
          slug: 'chuyen_nhanh_cap'
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
          slug: 'thue_bao_thuoc_cap'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 -ap icon-info"></span> Thuộc tính',
          slug: 'sua_cap'
        }
      ],
      optionsPolyline2: [
        {
          name: '<span class="icon text-main f20 one-daucuoi"></span> Đấu nối 2 đầu cáp',
          slug: 'dau_noi_cap'
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
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 one-chuyencap"></span> Chuyển nhánh cáp',
          slug: 'chuyen_nhanh_cap'
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
          slug: 'thue_bao_thuoc_cap'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 -ap icon-info"></span> Thuộc tính',
          slug: 'sua_cap'
        }
      ],
      doiTuong: {}
    }
  },
  watch: {
    mapTypeId (val) {
      if (val === 'ROADMAP') {
        this.map.setMapTypeId(window.google.maps.MapTypeId.ROADMAP)
      } else {
        this.map.setMapTypeId(window.google.maps.MapTypeId.HYBRID)
      }
    },
    polylines: {
      handler: function (val) {
        // console.log('polylines', val)
      },
      deep: true},
    searchDiaDiem: {
      handler: function (val) {
        console.log('🚀 ~ file: BanDo.vue:463 ~ val:', val)
        this.location = {
          lat: val.geometry.location.lat(),
          lng: val.geometry.location.lng()
        }
      },
      deep: true},
    nodes: {
      handler: function (val) {
      },
      deep: true}
  },
  async  mounted () {
    let vm = this
    this.$refs.oneBSSGmap.$mapPromise.then((map) => {
      vm.map = map

      // const ctrlPhanCapMang = document.createElement('a')
      // ctrlPhanCapMang.setAttribute('id', 'ctrlPhanCapMang')
      // ctrlPhanCapMang.setAttribute('class', 'btn btn-main control-map')
      // ctrlPhanCapMang.innerHTML = '<span class="nc-icon-glyph location_map-pin"></span>'
      // map.controls[this.google.maps.ControlPosition.RIGHT_TOP].push(ctrlPhanCapMang)
      // ctrlPhanCapMang.addEventListener('click', () => {
      //   if (this.nodes.length > 0) {
      //     var myLatlng = new window.google.maps.LatLng(this.nodes[0].position.lat, this.nodes[0].position.lng)
      //     this.map.panTo(myLatlng)
      //   }
      // })

      // const ctrlDuyChuyenDoiTuong = document.createElement('a')
      // ctrlDuyChuyenDoiTuong.setAttribute('id', 'ctrlDuyChuyenDoiTuong')
      // ctrlDuyChuyenDoiTuong.setAttribute('class', 'btn btn-main control-map')
      // ctrlDuyChuyenDoiTuong.innerHTML = '<span class="one-drag"></span>'
      // map.controls[this.google.maps.ControlPosition.TOP_RIGHT].push(ctrlDuyChuyenDoiTuong)
      // ctrlDuyChuyenDoiTuong.addEventListener('click', () => {
      //   this.draggable = !this.draggable
      // })

      // const ctrlLuuToaDoBanDo = document.createElement('a')
      // ctrlLuuToaDoBanDo.setAttribute('id', 'ctrlLuuToaDoBanDo')
      // ctrlLuuToaDoBanDo.setAttribute('class', 'btn btn-main control-map')
      // ctrlLuuToaDoBanDo.innerHTML = '<span class="-ap icon-table"></span>'
      // map.controls[this.google.maps.ControlPosition.RIGHT_TOP].push(ctrlLuuToaDoBanDo)
      // ctrlLuuToaDoBanDo.addEventListener('click', () => {
      //   this.luuToaDoBanDo()
      // })

      // const ctrlThayDoiNguonDuLieu = document.createElement('a')
      // ctrlThayDoiNguonDuLieu.setAttribute('id', 'ctrlThayDoiNguonDuLieu')
      // ctrlThayDoiNguonDuLieu.setAttribute('class', 'btn btn-main control-map')
      // ctrlThayDoiNguonDuLieu.innerHTML = '<span class="-ap icon-settings"></span>'
      // map.controls[this.google.maps.ControlPosition.RIGHT_TOP].push(ctrlThayDoiNguonDuLieu)
      // ctrlThayDoiNguonDuLieu.addEventListener('click', () => {
      //   if (this.mapTypeId === 'ROADMAP') {
      //     this.mapTypeId = 'HYBRID'
      //   } else {
      //     this.mapTypeId = 'ROADMAP'
      //   }
      // })

      map.addListener('zoom_changed', () => {
        this.zoom_changed(map.getZoom())
      })

      this.loadingMap = false
      this.measureTool = new MeasureTool(this.map, {
        contextMenu: false,
        unit: MeasureTool.UnitTypeId.METRIC,
        tooltip: false
      })

      var polygon = new this.google.maps.Polygon({
        clickable: true
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
    ...mapActions('cabman/BanDoMangCap', actionName),
    resolveCluster (event) {
      this.$gmapApiPromiseLazy().then(() => {
        const map = this.$refs.oneBSSGmap.$mapObject
        console.log('🚀 ~ file: BanDo.vue:541 ~ this.$gmapApiPromiseLazy ~ map:', map)
        if (map.getZoom() > 21) {
          console.log('🚀 ~ file: BanDo.vue:5433 ~ this.$gmapApiPromiseLazy ~ map.getZoom():', map.getZoom())
          map.setZoom(map.getZoom() - 4)
        }
      })
    },
    async zoom_changed (zoom) {
      console.log('Map zoom_changed', zoom)
      // console.log('🚀 ~ file: BanDo.vue:587 ~ zoom_changed ~ this.getlistPolyline:', this.getlistPolyline)
      // this.getlistPolyline.forEach(element => {
      //   console.log('🚀 ~ file: BanDo.vue:584 ~ zoom_changed ~ element:', element)
      // })
      // const map = this.$refs.oneBSSGmap.$mapObject
      // console.log('🚀 ~ file: BanDo.vue:593 ~ zoom_changed ~ map:', map)
    },
    ctrlPhanCapMang () {
      if (this.nodes.length > 0) {
        var myLatlng = new window.google.maps.LatLng(this.nodes[0].position.lat, this.nodes[0].position.lng)
        this.map.panTo(myLatlng)
      }
    },
    ctrlDuyChuyenDoiTuong () { this.draggable = !this.draggable },
    ctrlLuuToaDoBanDo () { this.luuToaDoBanDo() },
    ctrlThayDoiNguonDuLieu () {
      if (this.mapTypeId === 'ROADMAP') {
        this.mapTypeId = 'HYBRID'
      } else {
        this.mapTypeId = 'ROADMAP'
      }
    },
    onFirstIdle () { },
    ghimKetCuoi (item) {
      // console.log('ghimKetCuoi', item)
      this.ghimNode = !this.ghimNode
      if (this.ghimNode) {
        this.ketcuoi_ghim = item
      } else this.ketcuoi_ghim = null
    },
    mousemoveMap () {
      // console.log('mousemoveMap')
      if (this.ghimNode) {
        this.map.setOptions({draggableCursor: 'crosshair'})
      } else {
        this.map.setOptions({draggableCursor: 'default'})
      }
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
    dblclickPolyline (event, polyline) {
      console.log('dblclickPolyline', event, polyline)
      this.$emit('suaCap', polyline.id)
    },
    rightclickPolyline (event, polyline) {
      // console.log('rightclickPolyline', event, polyline)
      this.diemuonLatlng = event.latLng
      this.activePolyline = polyline
      this.mouselatLng.lat = event.latLng.lat()
      this.mouselatLng.lng = event.latLng.lng()
      this.$refs.vueSimpleContextMenuMap && this.$refs.vueSimpleContextMenuMap.hideContextMenu()
      this.$refs.vueSimpleContextMenuNode && this.$refs.vueSimpleContextMenuNode.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeTram && this.$refs.vueSimpleContextMenuNodeTram.hideContextMenu()
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
      // console.log('rightclickNodeDiemUon', event, node)
      this.$refs.vueSimpleContextMenuMap && this.$refs.vueSimpleContextMenuMap.hideContextMenu()
      this.$refs.vueSimpleContextMenuNode && this.$refs.vueSimpleContextMenuNode.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeTram && this.$refs.vueSimpleContextMenuNodeTram.hideContextMenu()
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
    leftclickMap (event) {
      console.log('leftclickMap1', event)
      if (this.ghimNode & this.ketcuoi_ghim !== null) {
        let lat = event.latLng.lat()
        let lng = event.latLng.lng()
        let ketcuoi_id = this.ketcuoi_ghim.FULL_ID
        var idx = this.nodes.findIndex(obj => obj.id === ketcuoi_id)
        if (idx >= 0) {
          // console.log('leftclickMap2', this.nodes[idx])
          this.nodes[idx].position.lat = lat
          this.nodes[idx].position.lng = lng
          this.nodes[idx].modified = true
          this.isMapChanged = true
          this.remove_ds_ketcuoi_ctd_id(ketcuoi_id)
          for (let index = 0; index < this.polylines.length; index++) {
            if (this.polylines[index].source === ketcuoi_id || this.polylines[index].dest === ketcuoi_id) {
              for (let index2 = 0; index2 < this.polylines[index].path.length; index2++) {
                if (this.polylines[index].path[index2].id === ketcuoi_id) {
                  // console.log('leftclickMap3', this.polylines[index])
                  this.polylines[index].path[index2].lat = lat
                  this.polylines[index].path[index2].lng = lng
                  this.polylines[index].modified = true
                  break
                }
              }
            }
          }
        }
        this.ghimNode = false
        this.ketcuoi_ghim = null
        this.draggable = true
      }
    },
    rightclickMap (event) {
      console.log('rightclickMap', event)
      this.locationrightclickMap = event.latLng
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
    dblclickNode (event, node) {
      console.log('dblclickNode', event, node)
      if (node.id.startsWith('KETCUOI_')) {
        this.$emit('suaKetcuoi', node.id)
        return
      }
      if (node.id.startsWith('KETCUOIKHACTUYEN_')) {
        this.$emit('xemKetcuoi', node.id)
        return
      }
      if (node.id.startsWith('TRAM_')) {
        this.$emit('suaTram', node.id)
      }
    },

    rightclickNode (event, node) {
      console.log('rightclickNode', event, node)
      this.$refs.vueSimpleContextMenuMap && this.$refs.vueSimpleContextMenuMap.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeDiemUon && this.$refs.vueSimpleContextMenuNodeDiemUon.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolyline && this.$refs.vueSimpleContextMenuPolyline.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeTram && this.$refs.vueSimpleContextMenuNodeTram.hideContextMenu()
      this.$refs.vueSimpleContextMenuNode && this.$refs.vueSimpleContextMenuNode.hideContextMenu()
      var menu = null
      if (node.id.startsWith('KETCUOIKHACTUYEN_')) {
        return
      }
      if (node.id.startsWith('KETCUOI_')) {
        this.$refs.vueSimpleContextMenuNode.showMenu(event, node)
        menu = document.getElementById('menuNode')
      } else {
        this.$refs.vueSimpleContextMenuNodeTram.showMenu(event, node)
        menu = document.getElementById('menuNodeTram')
      }

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
        case 'thue_bao_thuoc_kc':
          this.$emit('thueBaoThuocKetCuoi', event.item.id)
          break
        case 'xoa_ket_cuoi':
          this.$emit('xoaKetcuoi', event.item.id)
          break
        case 'sua_ket_cuoi':
          this.$emit('suaKetcuoi', event.item.id)
          break
        case 'tao_ket_cuoi':
          this.$emit('taoKetcuoi', event.item.id)
          break
        case 'dau_noi_ben_trong':
          this.$emit('dauNoiBenTrong', {KETCUOI_ID: event.item.id, KYHIEU_KC: event.item.symbol})
          break
        case 'chuyen_vao_trong_tram':
          this.$emit('chuyenvaoTrongTram', {KETCUOI_ID: event.item.id, KYHIEU_KC: event.item.symbol})
          break
        case 'tao_cap_ket_noi_ketcuoi_khactuyen':
          this.$emit('taoCapKetNoiKetCuoiKhacTuyen', event.item.id)
          break
      }
    },
    menuNodeTram_Clicked (event) {
      switch (event.option.slug) {
        case 'dau_noi_thiet_bi_tram':
          // Call WinUITruyenDan.frmDauNoiTBI Minh
          console.log('daunoiThietBi', event.item.id)
          var doituong = event.item.id.split('_')
          this.doiTuong.tramtb_id = doituong[1]
          this.$bvModal.show('bpopupDauNoiTbi')
          break
        case 'sua_tram':
          this.$emit('suaTram', event.item.id)
          break
      }
    },
    menuNodeDiemUon_Clicked (event) {
      // console.log('menuNodeDiemUon_Clicked', event)
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
    getMarkerIconSearch () {
      return {
        labelOrigin: { x: 20, y: -10 },
        url: require('./pin.png'),
        size: { width: 40, height: 40, f: 'px', b: 'px' },
        scaledSize: { width: 40, height: 40, f: 'px', b: 'px' }
      }
    },
    getMarkerLabelSearch () {
      return ({'text': 'ABC', 'color': '#3225F6', 'fontSize': '12px', 'fontFamily': 'Arial', 'fontWeight': 'bold'})
    },
    menuMap_Clicked (event) {
      console.log('menuMap_Clicked', event)
      let vm = this
      switch (event.option.slug) {
        case 'lam_tuoi':
          EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2')
          break
        case 'tim_dia_diem':
          this.$prompt('Nhập địa chỉ hoặc tọa độ muốn tìm', 'Nhập text', {
            confirmButtonText: 'Xác nhận',
            cancelButtonText: 'Hủy bỏ'
          }).then(({ value }) => {
            var re = new RegExp(/-?(\d*\.)?\d+,\s*-?(\d*\.)?\d+/)
            if (re.test(value)) {
              let arr = value.split(',')
              this.$refs.oneBSSGmap.$mapPromise.then((map) => {
                vm.location = {
                  lat: parseFloat(arr[0]),
                  lng: parseFloat(arr[1])
                }
                let panPoint = new window.google.maps.LatLng(vm.location.lat, vm.location.lng)
                map.setCenter(panPoint)
                this.nodesearchs = []
                this.nodesearchs.push({
                  id: 'x',
                  position: {
                    lat: parseFloat(arr[0]),
                    lng: parseFloat(arr[1])
                  },
                  tooltipText: value
                })
              })
            } else {
              this.$refs.oneBSSGmap.$mapPromise.then((map) => {
                var geocoder = new window.google.maps.Geocoder()
                var address = value + ', Việt Nam'
                geocoder.geocode({ 'address': address }, function (results, status) {
                  console.log('🚀 ~ file: BanDo.vue:891 ~ results:', results)
                  if (status === 'OK') {
                    var latitude = results[0].geometry.location.lat()
                    var longitude = results[0].geometry.location.lng()
                    vm.location = {
                      lat: latitude,
                      lng: longitude
                    }
                    let panPoint = new window.google.maps.LatLng(vm.location.lat, vm.location.lng)
                    map.setCenter(panPoint)
                    this.nodesearchs = []
                    this.nodesearchs.push({
                      id: 'x',
                      position: {
                        lat: (latitude),
                        lng: (longitude)
                      },
                      tooltipText: address
                    })
                  }
                })
              })
            }
          }).catch(() => {
            this.nodesearchs = []
          })
          break
        case 'day_la_dau':
          console.log('day_la_dau', this.locationrightclickMap.lat(), this.locationrightclickMap.lng())
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
      // console.log('menuPolyline_Clicked', event)
      switch (event.option.slug) {
        case 'chuyen_nhanh_cap':
          this.$emit('chuyennhanhcap', event.item.id)
          break
        case 'chen_ket_cuoi':
          this.$emit('chenketcuoi', event.item.id)
          break
        case 'dao_chieu_cap':
          this.$emit('daochieuCap', event.item.id)
          break
        case 'sua_cap':
          this.$emit('suaCap', event.item.id)
          break
        case 'xoa_cap':
          this.$emit('xoaCap', event.item.id)
          break
        case 'dau_noi_cap':
          this.$emit('dauNoiCap', event.item.id)
          break
        case 'thue_bao_thuoc_cap':
          this.$emit('thueBaoThuocCap', event.item.id)
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
      return {
        text: symbol,
        // color: this.mapTypeId=='ROADMAP'?'#000':'#FFF',
        fontSize: '12px',
        fontFamily: 'Roboto, Arial',
        fontWeight: 'bold',
        className: this.mapTypeId == 'ROADMAP' ? 'my-marker-label-black' : 'my-marker-label-white'
      }
    },
    mouseoverNode (event, node) {
      // console.log('mouseoverNode', event, node)
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
    inBetween (cordinatesA, cordinatesB) {
      let start = new window.google.maps.LatLng(cordinatesA.lat, cordinatesA.lng)
      let end = new window.google.maps.LatLng(cordinatesB.lat, cordinatesB.lng)
      return new window.google.maps.geometry.spherical.interpolate(start, end, 0.5)
    },
    getDistance (cordinatesA, cordinatesB) {
      let start = new window.google.maps.LatLng(cordinatesA.lat, cordinatesA.lng)
      let end = new window.google.maps.LatLng(cordinatesB.lat, cordinatesB.lng)
      let distanceinMetre = new window.google.maps.geometry.spherical.computeDistanceBetween(start, end)
      // if (distanceinMetre >= 0 && distanceinMetre < 1000) {
      //   distanceinMetre = distanceinMetre.toFixed() + ' m'
      // } else if (distanceinMetre >= 1000) {
      //   distanceinMetre = (distanceinMetre / 1000).toFixed(1).replace('.', ',') + ' km'
      // }
      distanceinMetre = distanceinMetre.toFixed() + ' m'
      return distanceinMetre
    },
    async ACTIVE_OBJECT_MAP_V2 (obj) {
      console.log('🚀 ~ file: BanDo.vue:1115 ~ ACTIVE_OBJECT_MAP_V2 ~ obj:', obj)
      try {
        if (obj.objData) {
          if (obj.objData.LOAIDT === 'KETCUOI' || obj.objData.LOAIDT === 'THUEBAO') {
            let objNodeId = obj.objData.ID.toString()
            if (obj.objData.PARENT_ID && obj.objData.PARENT_ID !== null) {
              objNodeId = obj.objData.PARENT_ID.toString()
            }
            console.log('🚀 ~ file: BanDo.vue:1120 ~ ACTIVE_OBJECT_MAP_V2 ~ objNodeId:', objNodeId)
            let node = this.getPositionNodeId('KETCUOI_' + objNodeId)
            if (node !== null) {
              console.log('🚀 ~ file: BanDo.vue:1125 ~ ACTIVE_OBJECT_MAP_V2 ~ node:', node)
              let panPoint = new window.google.maps.LatLng(node.lat, node.lng)
              console.log('🚀 ~ file: BanDo.vue:1127 ~ ACTIVE_OBJECT_MAP_V2 ~ panPoint:', panPoint)
              this.map.panTo(panPoint, { 'animation': true })
              this.map.setZoom(this.maxZoom)
              this.map.setCenter(panPoint)
            }
          }
          if (obj.objData.LOAIDT === 'CAP') {
            let objNodeId = obj.objData.ID.toString()
            if (obj.objData.PARENT_ID && obj.objData.PARENT_ID !== null) {
              objNodeId = obj.objData.PARENT_ID.toString()
            }
            console.log('🚀 ~ file: BanDo.vue:1120 ~ ACTIVE_OBJECT_MAP_V2 ~ objNodeId:', objNodeId)
            let node = this.getPositionNodeId('KETCUOI_' + objNodeId)
            console.log('🚀 ~ file: BanDo.vue:1125 ~ ACTIVE_OBJECT_MAP_V2 ~ node:', node)
            if (node !== null) {
              let panPoint = new window.google.maps.LatLng(node.lat, node.lng)
              console.log('🚀 ~ file: BanDo.vue:1127 ~ ACTIVE_OBJECT_MAP_V2 ~ panPoint:', panPoint)
              this.map.panTo(panPoint, { 'animation': true })
              this.map.setZoom(this.maxZoom)
              this.map.setCenter(panPoint)
            }
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: BanDo.vue:1119 ~ ACTIVE_OBJECT_MAP_V2 ~ error:', error)
      }
    },
    mouseoverNodeDiemUon (event, node) {
      // console.log('mouseoverNodeDiemUon', event, node)
      this.infoOpenedNode = false
      this.infoOpenedPolyline = false
    },
    mouseoutNode (event, node) {
      // console.log('mouseoutNode', event, node)
      node.mouseover = false
      this.infoOpenedNode = false
      this.infoOpenedPolyline = false
    },
    mouseoverPolyline (event, polyline) {
      // console.log('mouseoverPolyline', event, polyline)
      polyline.mouseover = true
      this.infoPositionNode = event.latLng
      this.infoContentNode = polyline.label
      this.infoOpenedNode = true
      this.infoOpenedPolyline = false
    },
    mouseoutPolyline (event, polyline) {
      // console.log('mouseoutPolyline', event, polyline)
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
      try {
        console.log('🚀 ~ file: BanDo.vue:1220 ~ panToMarker ~ pos:', pos)
        this.$refs.oneBSSGmap.panTo(pos)
      // map.setZoom(3);
      } catch (error) {
        console.log('🚀 ~ file: BanDo.vue:1225 ~ panToMarker ~ error:', error)
      }
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
      if (position === null || position === undefined) {
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
      console.log('🚀 ~ file: BanDo.vue:1290 ~ getPositionNodeId ~ this.nodes:', this.nodes)
      const objIndex = this.nodes.findIndex(obj => {
        return obj.id === id
      })
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
    setinfoMapDefault (infoMapDefault) {
      this.location.lat = (infoMapDefault.center.lat)
      this.location.lng = (infoMapDefault.center.lng)
      // this.zoomLevel = (infoMapDefault.zoom)
      this.zoomLevel = 20
      // this.clustering = infoMapDefault.cluster_map_google
      console.log('🚀 ~ file: BanDo.vue:1498 ~ setinfoMapDefault ~ this.clustering:', this.clustering)
      // this.zoomLevel = Math.log(window.screen.width / 512) + 9
      // console.log('🚀 ~ file: BanDo.vue:1468 ~ setinfoMapDefault ~ this.zoomLevel:', this.zoomLevel)
    },
    zoomIn () {
      if (this.zoomLevel === this.maxZoom) return
      this.zoomLevel = this.zoomLevel + 1
    },
    zoomOut () {
      if (this.zoomLevel === 5) return
      this.zoomLevel = this.zoomLevel - 1
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
  .my-marker-label-white{
    color:#fff !important;
    text-shadow: -1px 1px 0 #000,1px 1px 0 #000,1px -1px 0 #000,-1px -1px 0 #000;
  }
  .my-marker-label-black{
    color:#000 !important;
    text-shadow: -1px 1px 0 #fff,1px 1px 0 #fff,1px -1px 0 #fff,-1px -1px 0 #fff;
  }
</style>
