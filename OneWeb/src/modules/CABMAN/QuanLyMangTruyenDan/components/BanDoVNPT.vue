<template>
  <div>
    <div style="height: 910px">
      <vue-simple-context-menu elementId="menuMap" :options="optionsMenuMap" ref="menuMap" @option-clicked="menuMap_Clicked" />
      <vue-simple-context-menu elementId="menuRoute1" :options="optionsMenu" ref="menuRoute1" @option-clicked="menuRoute_Clicked1" />
      <vue-simple-context-menu elementId="menuDiemUon" :options="optionsMenuDiemUon" ref="menuDiemUon" @option-clicked="menuDiemUon_Clicked" />
      <vue-simple-context-menu elementId="menuNode" :options="optionsMenuNode" ref="menuNode" @option-clicked="menuNode_Clicked" />
      <div id="mapVNPT" class="mapVNPT"></div>
    </div>
    <DauNoiThietBi id="popupDauNoiThietBi" :doiTuong="doiTuong" />
    <QLCap id="popupQuanLyCap" :capObj="thongTinCap.capObj" :taomoi="thongTinCap.taomoi" :editable="thongTinCap.editable" @isOk="QLCapIsOk"></QLCap>
    <QuanLyTramThietBi id="popupTramThietBi" :tramTb_id="tramTb_id" @isOk="QuanLyTramThietBiIsOk" />
    <ChonDoiTuong id="popupChonDoiTuongDich" @chon="ChonDT" :listObj="listObj" />
    <QLKetCuoi modalID="QLKetCuoi" ref="QLKetCuoi" />
    <b-modal id="popupCapNhatToaDo" scrollable centered title="Nhập text">
      <div>Nhập tọa độ (vĩ độ, kinh độ) hoặc địa chỉ để thay đổi</div>
      <div>
        <input type="text" class="form-control" v-model="addressCapNhatToaDo" ref="ref-capnhat-toado" v-on:keyup.enter="CapNhatToaDo(nodeIdCapNhatToaDo)" />
      </div>
      <template #modal-footer="{ ok, cancel }">
        <b-button variant="primary" style="color: white" @click="CapNhatToaDo(nodeIdCapNhatToaDo)">Xác nhận</b-button>
        <b-button variant="secondary" style="color: white" @click="cancel()">Hủy bỏ</b-button>
      </template>
    </b-modal>
    <ModalTracnghiemExt id="ModalTracnghiemExtBD" ref="ModalTracnghiemExtBD" />
    <ModalChonDSKcExt id="ModalChonDSKcExtBD" ref="ModalChonDSKcExtBD" :doiTuong="doiTuongKc" />
  </div>
</template>
<script>
import Vue from 'vue'
import VnptMap from '@vnpt/maps-onebss'
import { mapActions, mapState, mapGetters } from 'vuex'
import { getterName, actionName, statePropertyName } from '../store'
import { gmapApi } from 'gmap-vue'
import MeasureTool from 'measuretool-googlemaps-v3'
import EventBus from '@/utils/eventBus'
import html2canvas from 'html2canvas'
import NodeMapVNPT from '../class/NodeMapVNPT'
import Route from '../class/Route'
import GraphPolyline from '../../BanDoMangCap/structures/GraphPolyline'
import VueSimpleContextMenu from 'vue-simple-context-menu'
import { ContextMenuComponent } from '@syncfusion/ej2-vue-navigations'
import DauNoiThietBi from '../../QuanLyMangTruyenDan-Minh/daunoi-thietbi/Modal'
import QLCap from '../../QLCap/QLCap'
import QuanLyTramThietBi from '../../QuanLyMangTruyenDan-Cuong/QuanLyTramThietBi'
import ChonDoiTuong from '../../QuanLyMangTruyenDan-Cuong/ChonDoiTuongDich/Modal'
import QLKetCuoi from '../Modal/QLKetCuoi'
import Point from '../class/Point'
import ModalTracnghiemExt from '../TracNghiemExt/index.vue'
import ModalChonDSKcExt from '../ChenKetCuoi/index.vue'
import DynamicMarker from '@/modules/CABMAN/BanDoMangCap/components2/DynamicMarker'
const DynamicMarkerConstructor = Vue.extend(DynamicMarker)
const TOKEN = 'VNPT-f76bf568-b093-45cf-9761-f6339d3e7d9a'
export default {
  components: {
    ModalChonDSKcExt,
    ModalTracnghiemExt,
    MeasureTool,
    html2canvas,
    'vue-simple-context-menu': VueSimpleContextMenu,
    'ejs-contextmenu': ContextMenuComponent,
    DauNoiThietBi,
    QLCap,
    QuanLyTramThietBi,
    ChonDoiTuong,
    QLKetCuoi
  },
  name: 'BanDo',
  props: {
    mapTypeId: null
  },
  computed: {
    ...mapState('cabman/QuanLyMangTruyenDan', statePropertyName),
    ...mapState('cabman', ['QuanLyMangTruyenDan']),
    ...mapGetters('cabman/QuanLyMangTruyenDan', getterName),
    google: gmapApi,
    getPositionCenter() {
      return this.location
    },
    optionsRouteMenu() {
      return this.optionsMenu
    },
    getlistNodeDiemUon() {
      return this.Nodes.filter((n) => n.Visible === true && n.IsDiemuon === true)
    },
    getlistNode() {
      return this.Nodes.filter((n) => n.Visible === true && n.IsDiemuon === false && n.Position.lat * n.Position.lng > 0)
    },
    getlistRoute() {
      return this.Routes.filter((n) => n.Visible === true)
    },
    getMapTypeId() {
      return this.mapTypeId
    }
  },
  async created() {
    EventBus.$on('IN_BANDO', this.inBando)
    EventBus.$on('GHIM_KETCUOI', this.ghimKetCuoi)
    EventBus.$on('BANDO_PhanCapMang', this.ctrlPhanCapMang)
    EventBus.$on('BANDO_DuyChuyenDoiTuong', this.ctrlDuyChuyenDoiTuong)
    EventBus.$on('BANDO_LuuToaDoBanDo', this.ctrlLuuToaDoBanDo)
    EventBus.$on('BANDO_ThayDoiNguonDuLieu', this.ctrlThayDoiNguonDuLieu)
    EventBus.$on('ACTIVE_OBJECT_MAP_V2', this.ACTIVE_OBJECT_MAP_V2)
    EventBus.$on('getRoadBetween2Points', this.getRoadBetween2Points)
    this.refreshMap()
  },
  destroyed() {
    EventBus.$off('IN_BANDO', this.inBando)
    EventBus.$off('GHIM_KETCUOI', this.ghimKetCuoi)
    EventBus.$off('BANDO_PhanCapMang', this.ctrlPhanCapMang)
    EventBus.$off('BANDO_DuyChuyenDoiTuong', this.ctrlDuyChuyenDoiTuong)
    EventBus.$off('BANDO_LuuToaDoBanDo', this.ctrlLuuToaDoBanDo)
    EventBus.$off('BANDO_ThayDoiNguonDuLieu', this.ctrlThayDoiNguonDuLieu)
    EventBus.$off('ACTIVE_OBJECT_MAP_V2', this.ACTIVE_OBJECT_MAP_V2)
    EventBus.$off('getRoadBetween2Points', this.getRoadBetween2Points)
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
      doiTuongKc: {
        kieu: 0
      },
      // Popup Cập nhật tọa độ
      addressCapNhatToaDo: '',
      nodeIdCapNhatToaDo: null,

      allowMove: false,
      // mouselatLng: {},
      activePolyline: null,
      diemuonLatlng: null,
      optionsMenu: [
        {
          name: '<span class="icon nc-icon-outline ui-1_simple-add"></span> Thêm điểm uốn',
          slug: 'tao_diem_uon_cap'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon one-m-edit"></span> Sửa tọa độ cáp',
          slug: 'sua_toa_do_cap'
        },
        {
          name: '<span class="icon one-save1"></span> Kết thúc sửa',
          slug: 'ket_thuc_sua_cap'
        },
        {
          name: '<span class="icon one-reload-next"></span> Làm lại tuyến',
          slug: 'lam_lai_tuyen_cap'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon -ap icon-info_outline"></span> Thuộc tính',
          slug: 'thuoc_tinh_cap'
        }
      ],
      optionsMenu1: [
        {
          text: 'Thêm điểm uốn',
          iconCss: 'icon nc-icon-outline ui-1_simple-add',
          id: 'tao_diem_uon_cap'
        },
        {
          id: 'MENU_ITEM_SEPARATOR_1',
          separator: true
        },
        {
          text: 'Sửa tọa độ cáp',
          iconCss: 'icon one-m-edit',
          id: 'sua_toa_do_cap'
        },
        {
          text: 'Kết thúc sửa',
          iconCss: 'icon one-save1',
          id: 'ket_thuc_sua_cap'
        },
        {
          text: 'Làm lại tuyến',
          iconCss: 'icon one-reload-next',
          id: 'lam_lai_tuyen_cap'
        },
        {
          id: 'MENU_ITEM_SEPARATOR_1',
          separator: true
        },
        {
          text: 'Thuộc tính',
          iconCss: 'icon -ap icon-info_outline',
          id: 'thuoc_tinh_cap'
        }
      ],
      optionsMenuDiemUon: [
        {
          name: '<span class="icon one-td-app-outline"></span> Xóa điểm uốn',
          slug: 'xoa_diem_uon_cap'
        },
        {
          name: '<span class="icon nc-icon-outline ui-1_simple-add"></span> Chèn kết cuối',
          slug: 'chen_ket_cuoi'
        }
      ],
      optionsMenuNode: [
        // Huu Minh - add đấu nối thiết bị
        // {
        //   name: '<span class="icon one-td-journey2"></span> Đấu nối thiết bị',
        //   slug: 'dau_noi_tbi'
        // },
        // {
        //   type: 'divider'
        // },
        // Huu Minh - end
        {
          name: '<span class="icon nc-icon-outline arrows-1_tail-right"></span> Tạo cáp tới...',
          slug: 'tao_cap_toi_ketcuoi'
        },
        {
          name: '<span class="icon one-map-location1"></span> Cập nhật tọa độ...',
          slug: 'cap_nhat_toado_ketcuoi'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon -ap icon-info_outline"></span> Thuộc tính',
          slug: 'thuoc_tinh_ketcuoi'
        }
      ],
      optionsMenuMap: [
        {
          name: '<span class="icon one-reload-next"></span> Làm tươi',
          slug: 'lam_tuoi'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon one-map-location1"></span> Đây là đâu ?',
          slug: 'day_la_dau'
        },
        {
          name: '<span class="icon -ap icon-info_outline"></span> Tìm địa điểm',
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
      locationrightclickMap: null,
      zoomLevel: 20,
      draggable: false,
      showMapfirst: true,
      location: { lat: -1, lng: -1 },
      geofency: null,
      point: {},
      popupInstance: null,
      pointLayers: [],
      pointSearchsLayers: [],
      pointDiemUonLayers: [],
      polylineLayers: [],
      basemapApi: null,
      mouselatLng: {},
      maxZoom: 20,
      mapVNPT: null,
      isMapChanged: false,
      mapOptions: {
        zoomControl: false,
        scaleControl: true,
        disableDefaultUi: true,
        streetViewControl: false,
        mapTypeControl: false,
        fullscreenControl: false,
        maxZoom: this.maxZoom,
        disableDoubleClickZoom: true,
        draggableCursor: 'default',
        draggingCursor: 'pointer'
      },
      Nodes: [],
      Routes: [],
      Polylines: [],
      infoOptionsNode: {
        shouldFocus: false,
        pixelOffset: {
          width: 0,
          height: -15
        }
      },
      infoOptionsRoute: {
        pixelOffset: {
          width: 0,
          height: -15
        }
      },
      infoPositionNode: null,
      infoOpenedNode: false,
      infoContentNode: null,

      infoPositionRoute: null,
      infoOpenedRoute: false,
      infoContentRoute: null,
      doiTuong: {},
      thongTinCap: {
        capObj: {},
        taomoi: false,
        editable: false
      },
      tramTb_id: null,
      // Form ChonDoiTuongDich
      listObj: {},
      nodeSelected: {},
      //right click route
      routeSelected: null
    }
  },
  watch: {
    Nodes(val) {
      this.setNodes(val)
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

    batDoKhoangCach: function (val) {
      if (val) {
        console.log('Bắt đầu đo khoảng cách')
      } else {
        console.log('Kết thúc đo khoảng cách')
        this.removePointDoKhoangCach()
      }
    }

    // Nodes (val) {
    //   console.log('map Nodes', val)
    // },
    // Routes (val) {
    //   console.log('map Routes', val)
    // },
  },
  async mounted() {
    let vm = this
    let options = {
      showZoomControl: false,
      showFullscreen: false,
      id: this.generateId(),
      maxZoom: 24
    }
    vm.mapVNPT = VnptMap.initMap('mapVNPT', options)
    vm.mapVNPT.on('load', (e) => {
      vm.basemapApi = VnptMap.initBasemapApi()
      vm.geofency = VnptMap.initGeofency()
      // setTimeout(() => this.mapVNPT.updateSized(), 10)
      // console.log('🚀 ~ vm.mapVNPT.on ~ vm.mapVNPT.getMaxZoom():', vm.mapVNPT.getMaxZoom())
    })
    //setTimeout(() => {vm.mapVNPT.updateSized();
    //console.log('12312');}
    //, 10)
    this.mapVNPT.setZoom(18)
    vm.mapVNPT.on('click', (e) => {
      this.batDoKhoangCach ? this.clickDoKhoangCach(e) : this.leftclickMap(e)
    })
    vm.mapVNPT.on('contextmenu', (e) => {
      if (this.point.x !== e.originalEvent.x || this.point.y !== e.originalEvent.y) {
        this.rightclickMap(e)
      } else {
        this.point.x = 0
        this.point.y = 0
      }
    })
  },
  methods: {
    ...mapActions('cabman/QuanLyMangTruyenDan', actionName),
    // Cac fn xử lý sự hiện
    dblclickNode(event, Node) {
      this.tramTb_id = parseInt(Node.id.split('_')[2])
      this.$bvModal.show('popupTramThietBi')
    },
    updateSized: function () {
      if (this.showMapfirst) {
        setTimeout(() => this.mapVNPT.updateSized(), 10)
        this.showMapfirst = false
      }
    },
    dblclickRoute(event, Route) {
      // console.log('dblclickPolyline', event, Route)
      // alert('thuoc tinh cap: ' + Route.id)
      // TRUCNT - ráp update thông tin cáp
      this.thongTinCap.capObj.CAP_ID = Route.id
      this.thongTinCap.taomoi = false
      this.thongTinCap.editable = true
      this.$bvModal.show('popupQuanLyCap')
    },
    dragendPositionNode(event, Node) {},
    dragPositionNode(event, Node) {
      try {
        const nodeid = Node.id
        let lat = event.latLng.lat()
        let lng = event.latLng.lng()

        const objIndex = this.Nodes.findIndex((obj) => obj.id === nodeid)
        if (objIndex < 0) return
        this.Nodes[objIndex].Position.lat = lat
        this.Nodes[objIndex].Position.lng = lng
        this.Nodes[objIndex].Modified = true

        const length = this.Routes.length
        let copiedArray = new Array(length)
        for (let i = 0; i < length; i++) {
          let Route = this.Routes[i]
          if (nodeid === Route.A.id || nodeid === Route.B.id || nodeid.startsWith('DIEMUON_')) {
            let path = Route.Positions.slice(0)
            for (let j = 0; j < path.length; j++) {
              if (path[j].id === nodeid) {
                path[j].lat = lat
                path[j].lng = lng
                Route.Modified = !!nodeid.startsWith('DIEMUON_')
                break
              }
            }
            Route.Positions = path
            copiedArray[i] = Route
          } else {
            copiedArray[i] = Route
          }
        }
        this.Routes = copiedArray.slice(0)
      } catch (error) {
        console.log('dragPositionNode error', error)
      }
    },
    generateId() {
      const randomId = Math.random().toString(36).substr(2, 9) + '_' + Date.now().toString(36)
      return randomId
    },
    getPolylineFromID(polyline_id) {
      return this.polylineLayers.find((e) => e.id === polyline_id)
    },
    getNodeFromID(point_id) {
      return this.pointLayers.find((e) => e.id === point_id)
    },
    getNodeDiemUonFromID(point_id) {
      return this.pointDiemUonLayers.find((e) => e.id === point_id)
    },
    getRouteFromID(route_id) {
      return this.Routes.find((e) => e.id === route_id)
    },
    rightclickNode(event, nodeId) {
      let node = this.getNodeFromID(nodeId)
      this.$refs.menuRoute1 && this.$refs.menuRoute1.hideContextMenu()
      this.$refs.menuDiemUon && this.$refs.menuDiemUon.hideContextMenu()
      this.$refs.menuMap && this.$refs.menuMap.hideContextMenu()
      this.$refs.menuNode && this.$refs.menuNode.hideContextMenu()
      this.$refs.menuNode.showMenu(event, node)
      const menu = document.getElementById('menuNode')
      var left = event.pointEvent.layerX
      var top = event.pointEvent.layerY
      menu.style.left = left + 'px'
      menu.style.top = top + 'px'
      menu.position = 'fixed'
      menu.classList.add('menu-context')
    },
    rightclickNodeDiemUon(event, Node) {
      let node = this.getNodeDiemUonFromID(Node)
      this.$refs.menuRoute1 && this.$refs.menuRoute1.hideContextMenu()
      this.$refs.menuDiemUon && this.$refs.menuDiemUon.hideContextMenu()
      this.$refs.menuMap && this.$refs.menuMap.hideContextMenu()
      this.$refs.menuNode && this.$refs.menuNode.hideContextMenu()
      this.$refs.menuDiemUon.showMenu(event, node)
      const menu = document.getElementById('menuDiemUon')
      var left = event.pointEvent.layerX
      var top = event.pointEvent.layerY
      menu.style.left = left + 'px'
      menu.style.top = top + 'px'
      menu.position = 'fixed'
      menu.classList.add('menu-context')
    },
    rightclickRoute(event, polyline_id) {
      let polyline = this.getPolylineFromID(polyline_id)
      this.diemuonLatlng = event.lngLat
      this.activePolyline = polyline
      this.mouselatLng.lat = event.lngLat.lat
      this.mouselatLng.lng = event.lngLat.lng
      localStorage.setItem('mouseLatLng', JSON.stringify(this.mouselatLng))
      this.$refs.menuRoute && this.$refs.menuRoute.hideContextMenu()
      this.$refs.menuDiemUon && this.$refs.menuDiemUon.hideContextMenu()
      this.$refs.menuMap && this.$refs.menuMap.hideContextMenu()
      this.$refs.menuNode && this.$refs.menuNode.hideContextMenu()
      this.routeSelected = this.getRouteFromID(Number(polyline_id))
      this.$refs.menuRoute1.showMenu(event, polyline)
      const menu = document.getElementById('menuRoute1')
      var left = event.point.x
      var top = event.point.y
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

    diemuonAdd(id_polyline, iconDiemUonBase64, lng, lat, visible, draggable) {
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
    nodeUpdate(pointLayer) {
      try {
        const objIndex = this.pointDiemUonLayers.findIndex((obj) => obj.id === pointLayer.id)
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
          let objIndex = this.pointDiemUonLayers.findIndex((obj) => obj.id === nodeid)
          if (objIndex < 0) return
          this.pointDiemUonLayers[objIndex].position.lat = lat
          this.pointDiemUonLayers[objIndex].position.lng = lng
          this.pointDiemUonLayers[objIndex].Modified = true
          let length = this.polylineLayers.length
          let copiedArray = new Array(length)
          for (let i = 0; i < length; i++) {
            let polyline = this.polylineLayers[i]
            if (nodeid === polyline.dest || nodeid === polyline.source || nodeid.startsWith('DIEMUON_')) {
              let path = polyline.path.slice(0)
              for (const element of path) {
                if (element.id === nodeid) {
                  element.lat = lat
                  element.lng = lng
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
        this.pointLayers.push(pointLayer)
        this.mapVNPT.addLayer(pointLayer)
        return true
      } catch (error) {
        console.log('🚀 ~ file: BanDoVNPT.vue:613 ~ nodeUpdate ~ error:', error)
        return false
      }
    },
    insertAt(arr, index, newItem) {
      return [...arr.slice(0, index), newItem, ...arr.slice(index)]
    },
    async LAY_TT_KETCUOI(ketcuoi_id) {
      try {
        let rs = await this.$root.context.get('web-cabman/mang-truyen-dan/lay-thongtin-ketcuoi', {
          ketcuoiId: ketcuoi_id
        })
        console.log('LAY_TT_KETCUOI', rs.data)
        if (rs.data.length) return rs.data[0]
      } catch (error) {
        return null
      }
    },
    async quanLyKetCuoi(ketcuoi_id) {
      var ketcuoi = await this.LAY_TT_KETCUOI(ketcuoi_id)
      if (ketcuoi) {
        this.$refs.QLKetCuoi.show(false, ketcuoi).then((result) => {
          if (result.ok) {
            this.$emit('SUA_THUOCTINH_KETCUOI', result.data)
          } else {
          }
        })
      }
    },
    menuNode_Clicked(event) {
      console.log('menuNode_Clicked', event)
      switch (event.option.slug) {
        case 'tao_cap_toi_ketcuoi':
          let dsketcuoi = this.Nodes.filter((item) => item.Group === 'KETCUOI' && item.id !== event.item.id)
            .map((item) => item.id.split('KETCUOI_')[1])
            .toString()
          let dstram = this.Nodes.filter((item) => item.Group === 'TRAM_TB' && item.id !== event.item.id)
            .map((item) => item.id.split('TRAM_TB_')[1])
            .toString()
          this.listObj = { dsketcuoi, dstram }
          this.nodeSelected = event.item
          this.$bvModal.show('popupChonDoiTuongDich')
          break
        case 'cap_nhat_toado_ketcuoi':
          this.addressCapNhatToaDo = ''
          this.nodeIdCapNhatToaDo = event.item.id
          // alert('cap_nhat_toado_ketcuoi: ' + event.item.id)
          this.$bvModal.show('popupCapNhatToaDo')

          break
        // Hong Quan - add form quan ly tram thiet bi
        case 'thuoc_tinh_ketcuoi':
          if (event.item.Group === 'KETCUOI') {
            var ketcuoi_id = parseInt(event.item.id.split('_')[1])
            this.quanLyKetCuoi(ketcuoi_id)
            break
          }
          this.tramTb_id = parseInt(event.item.id.split('_')[2])
          this.$bvModal.show('popupTramThietBi')
          break
        // Huu Minh - add đấu nối thiết bị
        case 'dau_noi_tbi':
          if (event.item.Group === 'TRAM_TB') {
            let tram_tbi_id = event.item.id.split('_')[2]
            this.doiTuong = {
              tramtb_id: tram_tbi_id
            }
            this.$bvModal.show('popupDauNoiThietBi')
          }
          break
        // Huu Minh - end
      }
    },
    menuMap_Clicked(event) {
      console.log('menuMap_Clicked', event)
      switch (event.option.slug) {
        case 'lam_tuoi':
          break
        case 'day_la_dau':
          break
        case 'tim_dia_diem':
          break
        case 'dokhoangcach':
          this.batDoKhoangCach = !this.batDoKhoangCach
          break
        case 'xoaphepdo':
          this.batDoKhoangCach = false
          break
      }
    },
    menuDiemUon_Clicked(event) {
      switch (event.option.slug) {
        case 'xoa_diem_uon_cap':
          const nodeid = event.item.id
          const idpolyline = event.item.id_polyline
          const objIndex = this.pointDiemUonLayers.findIndex((obj) => obj.id === nodeid)
          if (objIndex > -1) {
            let node = this.pointDiemUonLayers[objIndex]
            console.log('node', node)
            node && node.remove()
            node = null
            this.pointDiemUonLayers.splice(objIndex, 1)
            const idx = this.polylineLayers.findIndex((obj) => obj.id === idpolyline)
            if (idx < 0) return
            var path = this.polylineLayers[idx].path.splice(0)
            const idx2 = path.findIndex((obj) => obj.id === nodeid)
            if (idx2 > -1) {
              path.splice(idx2, 1)
              this.polylineLayers[idx].path = path.splice(0)
              this.polylineLayers[idx].modified = true
              this.rePolyline(this.polylineLayers[idx])
            }
          }
          break
        case 'chen_ket_cuoi':
          let route = this.Routes.find((x) => x.id == event.item.Id_Route)
          let currentLocation = { lat: event.item.position.lat, lng: event.item.position.lng } // position diemuon
          if (route == null) return
          var tracnghiem = {
            title: 'Chèn kết cuối',
            caption: 'Chọn loại kết cuối muốn chèn',
            options: ['Kết cuối đã nối cáp', 'Kết cuối chưa nối cáp', 'Kết cuối mới'],
            // Chỗ data này là cáp ID
            data: 123
          }
          this.$refs.ModalTracnghiemExtBD.show(tracnghiem).then((result) => {
            let ketcuoi_id = -1
            switch (result.option) {
              case 0:
                // this.$refs.ModalChonDSKetCuoiExt.show()
                this.$refs.ModalChonDSKcExtBD.show(0).then((result) => {
                  ketcuoi_id = result.doituong_id
                  this.updateKetCuoi({ KETCUOI_ID: ketcuoi_id }, route, currentLocation)
                })

                break
              case 1:
                this.$refs.ModalChonDSKcExtBD.show(1).then((result) => {
                  ketcuoi_id = result.doituong_id
                  this.updateKetCuoi({ KETCUOI_ID: ketcuoi_id }, route, currentLocation)
                })
                break
              case 2:
                // Tạo kết cuối
                let point = this.TaoNode()
                point.id = -1
                point.id2 = -1
                point.Group = 'KETCUOI'
                point.Size = { width: 24, height: 24 }
                point.ImageKey = 'KHONG_XAC_DINH'
                point.SetIcon(point.ImageKey)
                point.AllowMove = true
                point.Position = { lat: event.item.position.lat, lng: event.item.position.lng }

                this.$refs.QLKetCuoi.show(true, null, true).then((result) => {
                  if (result.ok) {
                    point.id = 'KETCUOI_' + result.data.KETCUOI_ID
                    point.id2 = result.data.KETCUOI_ID
                    point.Label = result.data.KYHIEU
                    point.Tooltip = result.data.TEN_KC
                    point.Tag = result.data
                    point.RootId = result.data.CAPGOC_ID
                    ketcuoi_id = result.data.KETCUOI_ID
                    this.updateKetCuoi(result.data, route, currentLocation)
                  }
                })
                break
            }
          })
      }
    },
    async updateKetCuoi(data, route, currentLocation) {
      ////Cập nhật toạ độ kết cuối
      try {
        this.loading(true)
        if (currentLocation.lat !== undefined && currentLocation.lat !== null && currentLocation.lng !== undefined && currentLocation.lng !== null) {
          await this.CAPNHAT_TOADO_KETCUOI(data.KETCUOI_ID, currentLocation.lat, currentLocation.lng)
        }
        let rs = await this.CHEN_KETCUOI_VAO_CAP(data.KETCUOI_ID, route.id, -1)
        if (rs.error == '200') {
          this.$root.$toast.success('Chèn kết cuối thành công')
        }
        this.$emit('nap_gian_do')
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async CHEN_KETCUOI_VAO_CAP(ketcuoi_id, cap_id, dodaicap_moi) {
      return await this.$root.context.post(`/web-cabman/ban-do-mang-cap/chen-ket-cuoi-v2?cap_id=${cap_id}&ketcuoi_id=${ketcuoi_id}&dodaicapmoi=${dodaicap_moi}`)
    },
    async CAPNHAT_TOADO_KETCUOI(ketcuoi_id, lat, lng) {
      return await this.$root.context.post('web-cabman/mang-truyen-dan/capnhat_toado_bando', {
        ketcuoi_id: ketcuoi_id,
        vi_do: lat,
        kinh_do: lng
      })
    },
    menuRoute_Clicked1(args) {
      if (!this.routeSelected) return
      switch (args.option.slug) {
        case 'thuoc_tinh_cap':
          this.thongTinCap.capObj.CAP_ID = this.routeSelected.id
          this.thongTinCap.taomoi = false
          this.thongTinCap.editable = true
          this.$bvModal.show('popupQuanLyCap')
          break
        case 'lam_lai_tuyen_cap':
          this.mniLamLaiTuyen_Click(this.routeSelected.id)
          break
        case 'ket_thuc_sua_cap':
          var id_cap = this.routeSelected.id
          for (let index = 0; index < this.Nodes.length; index++) {
            if (this.Nodes[index].Id_Route === id_cap && this.Nodes[index].IsDiemuon === true) {
              this.Nodes[index].Visible = false
            }
          }
          break
        case 'sua_toa_do_cap':
          args.item.modified = true
          console.log(args)
          console.log(this.pointDiemUonLayers)
          for (let index = 0; index < this.pointDiemUonLayers.length; index++) {
            if (this.pointDiemUonLayers[index].id_polyline === args.item.id && this.pointDiemUonLayers[index].isDiemuon === true) {
              this.pointDiemUonLayers[index].visible = true
              this.pointDiemUonLayers[index].setVisible(true)
            }
          }

          break
        case 'tao_diem_uon_cap':
          const iconDiemUonBase64 = this.getDynamicMarkerIcon('iconDiemUon', this.randomNonGreenColor())
          const id_diemuon = this.diemuonAdd(args.item.id, iconDiemUonBase64, this.diemuonLatlng.lng, this.diemuonLatlng.lat, true, true)
          if (id_diemuon !== null) {
            for (let i = 0; i < args.item.path.length - 1; i++) {
              let node1 = args.item.path[i]
              let node2 = args.item.path[i + 1]
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
                const result = this.insertAt(args.item.path, i + 1, p)
                args.item.path = result.slice(0)
                args.item.modified = true
                break
              }
            }
          }
          break
      }
      // Phân cấp cáp
      for (let item of this.data.dsPHANCAP) {
        if (args.item.id === 'phan_cap_cap_' + item.PHANCAP_ID) {
          this.SUA_MAU_CAP(this.routeSelected, item.PHANCAP_ID)
          break
        }
      }
    },

    async clearModified() {
      this.pointDiemUonLayers.forEach((element) => {
        element.modified = false
        element.setVisible(false)
      })
    },
    getDynamicMarkerIcon(text, color) {
      const iconComponent = new DynamicMarkerConstructor({
        propsData: {
          text,
          color
        }
      })
      iconComponent.$mount()
      const iconDom = iconComponent.$el
      const iconString = new XMLSerializer().serializeToString(iconDom)
      return 'data:image/svg+xml;charset=UTF-8;base64,' + btoa(iconString)
    },
    randomNonGreenColor() {
      var ranges = [
        [0, 60],
        [180, 359]
      ]
      var total = 0
      var i
      for (i = 0; i < ranges.length; i += 1) {
        total += ranges[i][1] - ranges[i][0] + 1
      }
      var randomHue = Math.floor(Math.random() * total)
      var pos = 0
      for (i = 0; i < ranges.length; i += 1) {
        pos = ranges[i][0]
        if (randomHue + pos <= ranges[i][1]) {
          randomHue += pos
          break
        } else {
          randomHue -= ranges[i][1] - ranges[i][0] + 1
        }
      }
      let color = 'hsl(' + randomHue + ',100%,50%)'
      // console.log('🚀 ~ file: index.vue:784 ~ randomNonGreenColor ~ color:', color)
      return color
    },
    randomColor() {
      var color = 'hsl(' + Math.round(Math.random() * 359) + ',100%,50%)'
      // console.log('🚀 ~ file: index.vue:801 ~ randomColor ~ color:', color)
      return color
    },
    OnFirstIdle() {
      console.log('onFirstIdle')
    },
    leftclickMap(event) {
      console.log('leftclickMap', event)
    },
    rightclickMap(event) {
      this.locationrightclickMap = event.latLng
      this.$refs.menuRoute1 && this.$refs.menuRoute1.hideContextMenu()
      this.$refs.menuDiemUon && this.$refs.menuDiemUon.hideContextMenu()
      this.$refs.menuMap && this.$refs.menuMap.hideContextMenu()
      this.$refs.menuNode && this.$refs.menuNode.hideContextMenu()

      this.$refs.menuMap.showMenu(event)
      const menu = document.getElementById('menuMap')
      var left = event.point.x
      var top = event.point.y
      menu.style.left = left + 'px'
      menu.style.top = top + 'px'
      menu.position = 'fixed'
      menu.classList.add('menu-context')
    },
    MouseOverNode(event, Node) {
      console.log('MouseOverNode', event)
      console.log('MouseOverNode', Node)
      Node.MouseOver = true
      this.infoPositionNode = event.latLng
      this.infoContentNode = Node.Tooltip !== null ? '<b>' + Node.Tooltip.replaceAll('#', '<br>') + '</b>' : null
      this.infoOpenedNode = true
      this.infoOpenedRoute = false
    },
    MouseOutNode(event, Node) {
      Node.MouseOver = false
      this.infoOpenedNode = false
      this.infoOpenedRoute = false
    },
    MouseOverRoute(event, Route) {
      console.log('MouseOverRoute', Route)
      Route.MouseOver = true
      this.infoPositionRoute = event.latLng
      this.infoContentRoute = '<b>' + Route.Name + '</b>'
      this.infoOpenedNode = false
      this.infoOpenedRoute = true
    },
    MouseOutRoute(event, Route) {
      Route.MouseOver = false
      this.infoOpenedNode = false
      this.infoOpenedRoute = false
    },
    //
    DuyChuyenDenViTri(location) {
      this.location = location
    },
    ThamSoMacDinhBanDo(infoMapDefault) {
      this.location.lat = infoMapDefault.center.lat
      this.location.lng = infoMapDefault.center.lng
      this.zoomLevel = infoMapDefault.zoom
    },
    TaoNode() {
      var node = new NodeMapVNPT()
      this.Nodes.push(node)
      return node
    },
    ThemNodeDiemUon(node) {
      let obj = this.TimNode(node.id)
      if (!obj) this.Nodes.push(node)
    },
    TimNode(Id) {
      var index = this.Nodes.findIndex((p) => p.id === Id)
      if (index > -1) return this.Nodes[index]
      else return null
    },
    TaoPolyline() {
      var polyline = new GraphPolyline()
      this.Polylines.push(polyline)
      return polyline
    },
    TimPolyline(Id) {
      var index = this.Polylines.findIndex((p) => p.id === Id)
      if (index > -1) return this.Polylines[index]
      else return null
    },
    TimRoute(Id) {
      let index = this.Routes.findIndex((p) => p.id === Id)
      if (index > -1) return this.Routes[index]
      else return null
    },
    TaoRoute() {
      var route = new Route()
      this.Routes.push(route)
      return route
    },
    TaoRoute2() {
      var route = new Route()
      return route
    },
    CapNhatRoute(route) {
      this.Routes.push(route)
    },
    Clear() {
      this.Nodes = []
      this.Routes = []
      this.Polylines = []
    },
    getNodes() {
      return this.pointLayers
    },
    getRoutes() {
      return this.polylineLayers
    },
    allowMoveNode() {
      this.allowMove = !this.allowMove
    },
    ctrlLuuToaDoBanDo() {
      this.LuuToaDoBanDo()
    },
    luuToaDoBanDo() {
      alert('Lưu tọa độ bản đồ')
    },
    phanCapCap(phancapcap_id) {
      if (phancapcap_id === 0) {
        for (let index = 0; index < this.Routes.length; index++) {
          this.Routes[index].Visible = true
        }
      } else {
        for (let index = 0; index < this.Routes.length; index++) {
          if (this.Routes[index].Layer === phancapcap_id) {
            this.Routes[index].Visible = true
          } else {
            this.Routes[index].Visible = false
          }
        }
      }
    },
    async setinfoMapDefault(infoMapDefault) {
      try {
        this.location.lat = infoMapDefault.Position.lat
        this.location.lng = infoMapDefault.Position.lng
        this.mapVNPT.setCenter([this.location.lng, this.location.lat])
        this.zoomLevel = 18
        this.mapVNPT.setZoom(this.zoomLevel)
      } catch (error) {
        console.log('🚀 ~ file: BanDoVNPT.vue:335 ~ setinfoMapDefault ~ error:', error)
      }
    },
    thaydoiMapType() {
      if (this.mapTypeId === 'ROADMAP') {
        this.mapVNPT.setStyle('https://maps.vnpt.vn/storage/vnptmaps/style/style_vetinh.json')
      } else {
        this.mapVNPT.setStyle('https://maps.vnpt.vn/tileserver/styles/mapvnpt_v2/style.json')
      }
    },
    FitBounds() {
      if (this.Nodes.length > 0) {
        this.setNodes(this.Nodes)
        this.setPolylines()
        this.setinfoMapDefault(this.Nodes[0])
        this.location = {
          lat: this.Nodes[0].Position.lat,
          lng: this.Nodes[0].Position.lng
        }
      }
    },
    getPositionNodeId(id) {
      const objIndex = this.pointLayers.findIndex((obj) => {
        return obj.id === id
      })
      if (objIndex >= 0) {
        return this.pointLayers[objIndex].position
      } else return null
    },
    setNodes(nodes) {
      // console.log('🚀 ~ file: BanDoVNPT.vue:1425 ~ setNodes ~ nodes:', nodes)
      let _nodes = nodes.filter((n) => n.Visible === true && n.IsDiemuon === false)
      for (let index = 0; index < _nodes.length; index++) {
        const node = _nodes[index]
        const point = [node.Position.lng, node.Position.lat]
        const obj = {
          iconUrl: node.icon.url,
          id: node.id,
          iconWidth: node.icon.size.width,
          iconHeight: node.icon.size.height,
          isDraggable: false
        }
        let pointLayer = VnptMap.initPoint(point, obj)
        pointLayer.symbol = node.Label
        pointLayer.setLabel(node.Label)
        pointLayer.setLabelVisible(true)
        pointLayer.id = node.id
        pointLayer.id_polyline = node.id2
        pointLayer.modified = node.Modified
        pointLayer.Modified = node.Modified
        pointLayer.draggable = false
        pointLayer.isDiemuon = node.IsDiemuon
        pointLayer.IsDiemuon = node.IsDiemuon
        pointLayer.visible = node.Visible && node.Position.lat * node.Position.lng > 0
        pointLayer.setVisible(pointLayer.visible)
        pointLayer.position = node.Position
        pointLayer.Position = node.Position
        pointLayer.icon = node.Icon
        pointLayer.tooltipText = node.Tooltip
        pointLayer.Group = node.Group
        pointLayer.id2 = node.id2
        let me = this
        pointLayer.on(this.mapVNPT, 'mouseenter', (e) => {
          let lng = e.coordinates[0]
          let lat = e.coordinates[1]
          let content = '<b>' + node.Tooltip + '</b>'
          me.popupInstance && me.popupInstance.remove()
          me.popupInstance = VnptMap.initPopup([lng, lat], content, {
            offset: 20
          })
          me.popupInstance.addLayerTo(me.mapVNPT)
        })
        pointLayer.on(this.mapVNPT, 'mouseout', (e) => {
          me.popupInstance && me.popupInstance.remove()
        })
        // pointLayer.onDrag('dragstart', (e) => {
        // })
        pointLayer.onDrag('drag', (e) => {
          this.isMapChanged = true
          const nodeid = pointLayer.id
          let lat = e.coordinates.lat
          let lng = e.coordinates.lng
          const objIndex = this.pointLayers.findIndex((obj) => obj.id === nodeid)
          if (objIndex < 0) return
          this.pointLayers[objIndex].Position.lat = lat
          this.pointLayers[objIndex].Position.lng = lng
          this.pointLayers[objIndex].Modified = true
          const length = this.polylineLayers.length
          let copiedArray = new Array(length)
          for (let i = 0; i < length; i++) {
            let polyline = this.polylineLayers[i]
            if (nodeid === polyline.dest || nodeid === polyline.source || nodeid.startsWith('DIEMUON_')) {
              let path = polyline.path.slice(0)
              for (let j = 0; j < path.length; j++) {
                if (path[j].id === nodeid) {
                  path[j].lat = lat
                  path[j].lng = lng
                  polyline.Modified = !!nodeid.startsWith('DIEMUON_')
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
        // pointLayer.onDrag('dragend', (e) => {
        // })
        // pointLayer.on(this.mapVNPT, 'click', (e) => {
        // })
        pointLayer.on(this.mapVNPT, 'contextmenu', (e) => {
          this.point.x = e.pointEvent.x
          this.point.y = e.pointEvent.y
          me.rightclickNode(e, pointLayer.id)
        })
        // pointLayer.on(this.mapVNPT, 'contextmenu', (e) => {
        //   me.rightclickNode(e, pointLayer.id)
        // });
        pointLayer.addLayerTo(this.mapVNPT)
        this.pointLayers.push(pointLayer)
      }

      let _nodesDiemUon = nodes.filter((n) => n.IsDiemuon === true)
      // console.log('1111', _nodesDiemUon)
      var iconDiemUonBase64 = this.getDynamicMarkerIcon('iconDiemUon', this.randomNonGreenColor())
      for (let index = 0; index < _nodesDiemUon.length; index++) {
        const node = _nodesDiemUon[index]
        const point = [node.Position.lng, node.Position.lat]
        const obj = {
          iconUrl: iconDiemUonBase64,
          id: node.id,
          iconWidth: 15,
          iconHeight: 15,
          isDraggable: node.draggable
        }
        let pointLayer = VnptMap.initPoint(point, obj)
        pointLayer.id = node.id
        pointLayer.id_polyline = node.id_polyline.split('_')[1]
        pointLayer.modified = node.Modified
        // pointLayer.draggable = node.draggable
        // pointLayer.visible = node.Visible
        // tạm comment ẩn hiển thị icon điêm uốn chờ check nghiệp vụ
        pointLayer.draggable = false
        pointLayer.visible = false
        pointLayer.setVisible(false)

        pointLayer.isDiemuon = node.IsDiemuon
        pointLayer.position = node.Position
        pointLayer.iconUrl = iconDiemUonBase64
        pointLayer.tooltipText = node.Tooltip
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
        // pointLayer.onDrag('dragstart', (e) => {
        // })
        // pointLayer.onDrag('dragend', (e) => {
        // })
        pointLayer.onDrag('drag', (e) => {
          this.isMapChanged = true
          const nodeid = pointLayer.id
          let lat = e.coordinates.lat
          let lng = e.coordinates.lng
          const objIndex = this.pointDiemUonLayers.findIndex((obj) => obj.id === nodeid)
          if (objIndex < 0) return
          this.pointDiemUonLayers[objIndex].position.lat = lat
          this.pointDiemUonLayers[objIndex].position.lng = lng
          this.pointDiemUonLayers[objIndex].Modified = true
          const length = this.polylineLayers.length
          let copiedArray = new Array(length)
          for (let i = 0; i < length; i++) {
            let polyline = this.polylineLayers[i]
            if (nodeid === polyline.dest || nodeid === polyline.source || nodeid.startsWith('DIEMUON_')) {
              let path = polyline.path.slice(0)
              for (let j = 0; j < path.length; j++) {
                if (path[j].id === nodeid) {
                  path[j].lat = lat
                  path[j].lng = lng
                  polyline.Modified = !!nodeid.startsWith('DIEMUON_')
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
    async refreshMap() {
      this.draggable = false
      this.polylineLayerTimKiem && this.polylineLayerTimKiem.remove()
      this.polylineLayerTimKiem = null
      this.pointLayers.forEach((element) => {
        element.remove()
      })
      this.pointDiemUonLayers.forEach((element) => {
        element.remove()
      })
      this.polylineLayers.forEach((element) => {
        element.remove()
      })
      setTimeout(() => this.mapVNPT.updateSized(), 10)
      this.pointLayers = []
      this.pointDiemUonLayers = []
      this.pointSearchsLayers = []
      this.polylineLayers = []
    },
    reNode(node, draggable = false) {
      const point = [node.Position.lng, node.Position.lat]
      const obj = {
        iconUrl: node.icon.url,
        id: node.id,
        iconWidth: node.icon.size.width,
        iconHeight: node.icon.size.height,
        isDraggable: draggable
      }
      let pointLayer = VnptMap.initPoint(point, obj)
      pointLayer.symbol = node.Label
      pointLayer.setLabel(node.Label)
      pointLayer.setLabelVisible(true)
      pointLayer.id = node.id
      pointLayer.id_polyline = node.id2
      pointLayer.modified = node.Modified
      pointLayer.draggable = draggable
      pointLayer.isDiemuon = false
      pointLayer.visible = true
      pointLayer.position = node.Position
      let me = this
      pointLayer.on(this.mapVNPT, 'mouseenter', (e) => {
        let lng = e.coordinates[0]
        let lat = e.coordinates[1]
        let content = '<b>' + node.Tooltip + '</b>'
        me.popupInstance && me.popupInstance.remove()
        me.popupInstance = VnptMap.initPopup([lng, lat], content, {
          offset: 20
        })
        me.popupInstance.addLayerTo(me.mapVNPT)
      })
      pointLayer.on(this.mapVNPT, 'mouseout', (e) => {
        me.popupInstance && me.popupInstance.remove()
      })
      // pointLayer.onDrag('dragstart', (e) => {
      // })
      pointLayer.onDrag('drag', (e) => {
        this.isMapChanged = true
        const nodeid = pointLayer.id
        let lat = e.coordinates.lat
        let lng = e.coordinates.lng
        const objIndex = this.pointLayers.findIndex((obj) => obj.id === nodeid)
        if (objIndex < 0) return
        this.pointLayers[objIndex].position.lat = lat
        this.pointLayers[objIndex].position.lng = lng
        this.pointLayers[objIndex].modified = true

        const length = this.polylineLayers.length
        let copiedArray = new Array(length)
        for (let i = 0; i < length; i++) {
          let polyline = this.polylineLayers[i]
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
      // pointLayer.onDrag('dragend', (e) => {
      // })
      // pointLayer.on(this.mapVNPT, 'click', (e) => {
      // })
      pointLayer.on(this.mapVNPT, 'contextmenu', (e) => {
        this.point.x = e.pointEvent.x
        this.point.y = e.pointEvent.y
        me.rightclickNode(e, pointLayer.id)
      })
      this.mapVNPT.addLayer(pointLayer)
    },
    reAllPolyline() {
      for (let i = 0; i < this.polylineLayers.length - 1; i++) {
        let polyline = this.polylineLayers[i]
        polyline.remove()
        let polylineList = []
        for (let index = 0; index < polyline.path.length; index++) {
          let p = polyline.path[index]
          polylineList.push([p.lng, p.lat])
        }
        let dashArray = polyline.style.split('-')
        dashArray = dashArray.map((str) => {
          return parseInt(str, 10)
        })
        let obj = {
          id: polyline.id,
          color: '#0036D3',
          opacity: 1,
          weight: 3,
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
          polylineLayer.setStyle({ color: 'red' })
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
          polylineLayer.setStyle({ color: '#0036D3' })
        })
        polylineLayer.on('contextmenu', (e) => {
          me.rightclickRoute(e, polylineLayer.id)
        })
        // polylineLayer.on('click', (e) => {
        // })
        // polylineLayer.on('mousedown', (e) => {
        // })
        this.polylineLayers[i] = polylineLayer
      }
    },
    rePolyline(polyline) {
      polyline.remove()
      const polylineList = []
      for (let index = 0; index < polyline.path.length; index++) {
        let p = polyline.path[index]
        polylineList.push([p.lng, p.lat])
      }
      let dashArray = polyline.style.split('-')
      dashArray = dashArray.map((str) => {
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
        polylineLayer.setStyle({ color: 'red' })
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
        polylineLayer.setStyle({ color: '#0036D3' })
      })
      polylineLayer.on('contextmenu', (e) => {
        me.rightclickRoute(e, polylineLayer.id)
      })
      // polylineLayer.on('click', (e) => {
      // })
      // polylineLayer.on('mousedown', (e) => {
      // })
    },
    XoaPolyline() {
      try {
        this.polylineLayerTimKiem && this.polylineLayerTimKiem.remove()
        this.polylineLayerTimKiem = null
      } catch (error) {
        console.log('🚀 ~ XoaPolyline ~ error:', error)
      }
    },
    veP(p, decodedData) {
      for (let i = 0; i < p.path.length; ++i) {
        let e = p.path[i]
        if (e.id.startsWith('DIEMUON_')) {
          let objIndex = this.pointDiemUonLayers.findIndex((obj) => obj.id === e.id)
          if (objIndex > -1) {
            let node = this.pointDiemUonLayers[objIndex]
            node && node.remove()
            node = null
            this.pointDiemUonLayers.splice(objIndex, 1)
          }
        }
      }
      p.path = [p.path[0], p.path[p.path.length - 1]]
      for (let i = 0; i < decodedData.length; ++i) {
        let e = decodedData[i]
        const point = [e[1], e[0]]
        const id = 'DIEMUON_' + this.create_UUID()
        p.path.splice(p.path.length - 1, 0, { id, lat: e[0], lng: e[1] })
        const obj = {
          iconUrl: p.iconDiemUonBase64,
          id: id,
          iconWidth: 15,
          iconHeight: 15,
          isDraggable: true
        }
        let pointLayer = VnptMap.initPoint(point, obj)
        pointLayer.id = id
        pointLayer.id_polyline = p.id
        pointLayer.modified = false
        pointLayer.draggable = true
        pointLayer.isDiemuon = true
        pointLayer.visible = true
        pointLayer.position = { lng: e[1], lat: e[0] }
        pointLayer.setVisible(true)
        pointLayer.iconUrl = p.iconDiemUonBase64
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
        pointLayer.onDrag('drag', (e) => {
          this.isMapChanged = true
          const nodeid = pointLayer.id
          let lat = e.coordinates.lat
          let lng = e.coordinates.lng
          const objIndex = this.pointDiemUonLayers.findIndex((obj) => obj.id === nodeid)
          if (objIndex < 0) return
          this.pointDiemUonLayers[objIndex].position.lat = lat
          this.pointDiemUonLayers[objIndex].position.lng = lng
          this.pointDiemUonLayers[objIndex].modified = true
          const length = this.polylineLayers.length
          let copiedArray = new Array(length)
          for (let i = 0; i < length; i++) {
            let polyline = this.polylineLayers[i]
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
      p.modified = true
      this.rePolyline(p)
    },
    vePolyline(data) {
      console.log('🚀 ~ vePolyline ~ data:', data)
      const polylineList = []
      polylineList.push(data.DIEM_DAU)
      for (let index = 0; index < data.DS_TOADO.decodedData.length; index++) {
        const element = data.DS_TOADO.decodedData[index]
        polylineList.push([element[1], element[0]])
      }
      polylineList.push(data.DIEM_CUOI)
      const obj = {
        id: this.generateId(),
        color: '#1DCD60',
        // color: data.COLOR,
        opacity: 1,
        weight: 2.5,
        lineCap: 'round'
      }
      this.polylineLayerTimKiem && this.polylineLayerTimKiem.remove()
      this.polylineLayerTimKiem = null
      this.polylineLayerTimKiem = VnptMap.initPolyline(polylineList, obj)
      this.mapVNPT.addLayer(this.polylineLayerTimKiem)
    },
    setPolylines() {
      this.polylineLayers.forEach((element) => {
        element.remove()
      })
      this.polylineLayers = []
      var polylines = this.Polylines
      for (let index = 0; index < polylines.length; index++) {
        const polyline = polylines[index]
        const polylineList = []
        let l = polyline.path.length
        if (l > 1) {
          if (polyline.path[0].lat * polyline.path[0].lng > 0 && polyline.path[l - 1].lat * polyline.path[l - 1].lng > 0) {
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
        dashArray = dashArray.map((str) => {
          return parseInt(str, 10)
        })
        const obj = {
          id: polyline.id + '_' + this.create_UUID(),
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
          this.point.x = e.originalEvent.x
          this.point.y = e.originalEvent.y
          me.rightclickRoute(e, polylineLayer.id)
        })
        // console.log(this.polylineLayers)
        this.polylineLayers.push(polylineLayer)
      }
    },
    setLocationEx(position) {
      if (position === null || position === undefined) {
        if (this.pointLayers.length > 0) {
          this.location = {
            lat: this.pointLayers[0].Position.lat,
            lng: this.pointLayers[0].Position.lng
          }
        }
      } else {
        this.location = {
          lat: position.lat,
          lng: position.lng
        }
      }
      // this.mapVNPT.flyTo([this.location.lng, this.location.lat], 18)
      this.mapVNPT.setCenter([this.location.lng, this.location.lat])
      this.mapVNPT.setZoom(18)
    },
    // Refresh routes when changed position of node
    RefreshRoutes(nodeid) {
      try {
        const objIndex = this.Nodes.findIndex((obj) => obj.id === nodeid)
        if (objIndex < 0) return
        let lat = this.Nodes[objIndex].Position.lat
        let lng = this.Nodes[objIndex].Position.lng

        const length = this.Routes.length
        let copiedArray = new Array(length)
        for (let i = 0; i < length; i++) {
          let Route = this.Routes[i]
          if (nodeid === Route.A.id || nodeid === Route.B.id || nodeid.startsWith('DIEMUON_')) {
            let path = Route.Positions.slice(0)
            for (let j = 0; j < path.length; j++) {
              if (path[j].id === nodeid) {
                path[j].lat = lat
                path[j].lng = lng
                Route.Modified = !!nodeid.startsWith('DIEMUON_')
                break
              }
            }
            Route.Positions = path
            copiedArray[i] = Route
          } else {
            copiedArray[i] = Route
          }
        }
        this.Routes = copiedArray.slice(0)
      } catch (e) {
        console.log('RefreshRoutes error', e)
      }
    },
    ChonDT(data) {
      console.log('data from child ', data)
      // console.log('Nodes ', this.Nodes)
      // return data
      let toObj = this.Nodes.filter((item) => item.id === data.loai_dt + '_' + data.doituong_id)
      if (toObj.length > 0) {
        toObj = toObj[0]
        let fromObj = this.nodeSelected
        this.$emit('diaGianDo_ObjectAdded', 'Connector', fromObj, toObj)
      }
    },
    QLCapIsOk(...args) {
      const [TYPE, DATA] = args
      this.$emit('QLCapIsOk', TYPE, DATA)
    },
    QuanLyTramThietBiIsOk(data) {
      this.$emit('SUA_THUOCTINH_TRAM_TB', 'TRAM_TB_' + this.tramTb_id, data)
    },
    CapNhatToaDo(nodeId) {
      console.log('searchLocation', nodeId)
      //tạm bỏ cho map VNPT
      // let node = this.TimNode(nodeId)
      // let geocoder = new window.google.maps.Geocoder()
      // geocoder.geocode({'address': this.addressCapNhatToaDo.trim()}, (results, status) => {
      //   console.log('searchLocation', status)
      //   if (status === 'OK') {
      //     console.log(this.addressCapNhatToaDo.trim())
      //     let lat = results[0].geometry.location.lat()
      //     let lng = results[0].geometry.location.lng()
      //     console.log("result", lat,lng)
      //     node.Modified = true
      //     node.Position = {lat : lat, lng:lng}
      //     this.FitBounds()
      //     this.RefreshRoutes(nodeId)
      //     let myLatlng = new window.google.maps.LatLng(lat, lng)
      //     this.mapVNPT.panTo(myLatlng)
      //   }else{
      //     this.$root.toastError("Không tìm thấy địa chỉ [" + this.addressCapNhatToaDo.trim() + "] vừa nhập")
      //   }
      // })

      let node = this.TimNode(nodeId)
      let value = this.addressCapNhatToaDo.trim()
      this.basemapApi.getLonLatByAddress(value, TOKEN, (evt) => {
        console.log('🚀 ~ file: BanDoVNPT.vue:561 ~ this.basemapApi.getLonLatByAddress ~ evt:', evt)
        if (evt.status) {
          let result
          for (let i in evt.result) {
            if (evt.result[i].address === value) result = evt.result[i]
          }
          if (!result) result = evt.result[0]
          {
            const coord = [result.long, result.lat]
            node.Modified = true
            node.Position = { lat: result.lat, lng: result.long }
            this.RefreshRoutes(nodeId)
            this.FitBounds()
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
            this.mapVNPT.setCenter(coord)
            this.mapVNPT.setZoom(12)
          }
        } else {
          this.$root.toastError('Không tìm thấy địa chỉ [' + this.addressCapNhatToaDo.trim() + '] vừa nhập')
        }
      })

      this.$bvModal.hide('popupCapNhatToaDo')
    },
    mniLamLaiTuyen_Click(capId) {
      let route = this.Routes.filter((item) => item.id === capId)
      if (route.length > 0) {
        route = route[0]
        // clear diem uon
        route.Positions = [route.Positions[0], route.Positions[route.Positions.length - 1]]
        this.Routes = [...this.Routes]
      }
    },
    addMenuPhanCapCap() {
      if (this.data.dsPHANCAP.length > 0) {
        let dsPHANCAP = [...this.data.dsPHANCAP]
        let phancapcapChild = dsPHANCAP
          .sort((a, b) => {
            return a.PHANCAP_ID - b.PHANCAP_ID
          })
          .map((item) => ({
            text: item.PHANCAP_CAP,
            id: 'phan_cap_cap_' + item.PHANCAP_ID,
            iconCss: '-ap  icon-controller-record ' + item.MAU
          }))
        let phancapcap = {
          text: 'Phân cấp cáp ...',
          iconCss: 'icon one-reload-next',
          id: 'phan_cap_cap',
          items: phancapcapChild
        }
        this.$refs.menuRoute1.insertAfter([phancapcap], 'Làm lại tuyến')
      }
    },
    async SUA_MAU_CAP(route, phancap_id) {
      try {
        this.loading(true)
        await this.$root.context.post('web-cabman/mang-truyen-dan/capnhat-phancap-cap-bando', { PHANCAP_ID: phancap_id, CAP_ID: route.id }).catch((e) => {
          this.$root.toastError('Có lỗi xảy ra trong quá trình cập nhật phân cấp cáp')
          throw e
        })
        let idx = this.Routes.findIndex((item) => item.id === route.id)
        if (idx > -1) {
          this.Routes[idx].Layer = phancap_id
          let mau = this.data.dsPHANCAP.filter((item) => item.PHANCAP_ID === phancap_id)[0].MAU
          this.Routes[idx].BackColor = mau
        }
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
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

.mapVNPT {
  /* position: initial; */
  position: relative;
  height: 800px;
  /* height: calc(100%)!important; */
  width: 100% !important;
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
.vnptmaps-pointlabel-mid {
  color: black;
  font-size: 14px;
  font-weight: bold;
}
</style>
