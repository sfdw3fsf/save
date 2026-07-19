<template>
  <div class="map-canvas-box">
    <div class="map-canvas-main">
      <vue-simple-context-menu elementId="menuMap" :options="optionsMapMenu" ref="vueSimpleContextMenuMap" @option-clicked="menuMap_Clicked" />
      <vue-simple-context-menu elementId="menuPolylineVNPTMAP" :options="optionsPolylineMenu" ref="vueSimpleContextMenuPolyline" @option-clicked="menuPolyline_Clicked" />
      <vue-simple-context-menu elementId="menuPolylineCongBeVNPTMAP" :options="optionsPolylineCongBeMenu" ref="vueSimpleContextMenuPolylineCongBe" @option-clicked="menuPolylineCongBe_Clicked" />
      <vue-simple-context-menu elementId="menuPolylineTuyenCotVNPTMAP" :options="optionsPolylineTuyenCotMenu" ref="vueSimpleContextMenuPolylineTuyenCot" @option-clicked="menuPolylineTuyenCot_Clicked" />
      <vue-simple-context-menu elementId="menuNodeVNPTMAP" :options="optionsNodeMenu" ref="vueSimpleContextMenuNode" @option-clicked="menuNode_Clicked" />
      <vue-simple-context-menu elementId="menuNodeTramVNPTMAP" :options="optionsNodeTramMenu" ref="vueSimpleContextMenuNodeTram" @option-clicked="menuNodeTram_Clicked" />
      <vue-simple-context-menu elementId="menuNodeDiemUonVNPTMAP" :options="optionsNodeDiemUonMenu" ref="vueSimpleContextMenuNodeDiemUon" @option-clicked="menuNodeDiemUon_Clicked" />
      <vue-simple-context-menu elementId="menuNodeKhacTuyenVNPTMAP" :options="optionsNodeKhacTuyenMenu" ref="vueSimpleContextMenuNodeKhacTuyen" @option-clicked="menuNodeKhacTuyen_Clicked" />
      <vue-simple-context-menu elementId="menuNodeCongBeVNPTMAP" :options="optionsNodeCongBe" ref="vueSimpleContextMenuNodeCongBe" @option-clicked="menuNodeCongBe_Clicked" />
      <vue-simple-context-menu elementId="menuNodeTuyenCotVNPTMAP" :options="optionsNodeTuyenCot" ref="vueSimpleContextMenuNodeTuyenCot" @option-clicked="menuNodeTuyenCot_Clicked" />
      <div id="mapVNPTGiamSatMangNgoaiVi" class="mapVNPTGiamSatMangNgoaiVi"></div>
    </div>
    <ModalQLCapExt modalID="ModalQLCapExt" ref="ModalQLCapExt" />
  </div>
</template>

<script>
import VnptMap from '@vnpt/maps-onebss'
import { mapActions, mapState, mapGetters } from 'vuex'
import { getterName, actionName, statePropertyName } from './../store'
import VueSimpleContextMenu from 'vue-simple-context-menu'
import MeasureTool from 'measuretool-googlemaps-v3'
import EventBus from '../../../../utils/eventBus'
import html2canvas from 'html2canvas'
import DaunoiThietBi from '../../daunoi-thietbi/Modal'
import ModalQLCapExt from '../../BanDoMangCap/modal/QLCapExt2'
const TOKEN = 'VNPT-f76bf568-b093-45cf-9761-f6339d3e7d9a'
export default {
  components: { 'vue-simple-context-menu': VueSimpleContextMenu, MeasureTool, html2canvas, DaunoiThietBi, ModalQLCapExt },
  name: 'BanDoVNPT',
  props: ['vaction', 'vdich_id', 'vnguon_id'],
  async created() {
    EventBus.$on('IN_BANDO', this.inBando)
    EventBus.$on('GHIM_KETCUOI', this.ghimKetCuoi)
    EventBus.$on('BANDO_PhanCapMang', this.ctrlPhanCapMang)
    EventBus.$on('BANDO_DuyChuyenDoiTuong', this.ctrlDuyChuyenDoiTuong)
    EventBus.$on('BANDO_LuuToaDoBanDo', this.ctrlLuuToaDoBanDo)
    EventBus.$on('BANDO_ThayDoiNguonDuLieu', this.ctrlThayDoiNguonDuLieu)
    EventBus.$on('ACTIVE_OBJECT_MAP_V2', this.ACTIVE_OBJECT_MAP_V2)
    EventBus.$on('getRoadBetween2Points', this.getRoadBetween2Points)
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
    clearInterval(this.stylePolylineInterval)
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
      polylineLayerTimKiem: null,
      ds_dv: [],
      geofency: null,
      point: {},
      popupInstance: null,
      pointLayers: [],
      pointSearchsLayers: [],
      pointDiemUonLayers: [],
      polylineLayers: [],
      basemapApi: null,
      mapVNPT: null,
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
      location: { lat: -1, lng: -1 },
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
      vaction: 'None',
      vnguon_id: null,
      vdich_id: null,
      highlightNguon: null,
      highlightDich: null,
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
      doiTuong: {},
      stylePolylineInterval: null,
      isBlueColor: true
    }
  },
  watch: {
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
  },
  computed: {
    getPositionCenter() {
      return this.location
    },
    optionsPolylineMenu() {
      if (this.activePolyline) {
        if (this.activePolyline.path.length === 2) {
          return [...this.optionsPolyline2]
        }
        if (!this.activePolyline.modified) {
          return [...this.optionsPolyline]
        } else {
          return [...this.optionsPolyline2]
        }
      } else return [...this.optionsPolyline]
    },
    optionsPolylineCongBeMenu() {
      return [
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
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 -ap icon-info"></span> Thuộc tính',
          slug: 'thuocTinhDoanCong'
        }
      ]
    },
    optionsPolylineTuyenCotMenu() {
      return [
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
      ]
    },
    optionsNodeDiemUonMenu() {
      return []
    },
    optionsNodeKhacTuyenMenu() {
      return [
        {
          name: '<span class="icon text-main f20 one-trash"></span> Di chuyển tới giản đồ của tuyến',
          slug: 'chuyen_den_gian_do_khac_tuyen'
        }
      ]
    },
    optionsNodeCongBe() {
      return [
        {
          name: '<span class="icon text-main f20 -ap icon-info"></span> Thuộc tính',
          slug: 'thuocTinhBe'
        },
        {
          name: '<span class="icon text-main f20 one-forward"></span> Chuyển sang tuyến cống bể khác',
          slug: 'chuyenTuyen'
        }
      ]
    },
    optionsNodeTuyenCot() {
      return [
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
      ]
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
    vm.mapVNPT = VnptMap.initMap('mapVNPTGiamSatMangNgoaiVi', options)
    vm.mapVNPT.on('load', (e) => {
      vm.basemapApi = VnptMap.initBasemapApi()
      vm.geofency = VnptMap.initGeofency()
      setTimeout(() => this.mapVNPT.updateSized(), 10)
      console.log('🚀 ~ vm.mapVNPT.on ~ vm.mapVNPT.getMaxZoom():', vm.mapVNPT.getMaxZoom())
    })
    vm.mapVNPT.on('click', (e) => {
      if (this.batDoKhoangCach) {
        this.clickDoKhoangCach(e)
      } else {
        switch (this.vaction) {
          case 'TaoCap':
            this.leftclickMap(e)
            break
          case 'TaoCapKetNoi':
            if (this.vnguon_id === null || this.vnguon_id.length === 0) {
              if (this.highlightNguon !== null) {
                this.highlightNguon[0].classList.remove('highlight-node')
              }
              if (this.highlightDich !== null) {
                this.highlightDich[0].classList.remove('highlight-node')
              }
            }
            this.taoCapKetNoi(e)
            break
        }
      }
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
    ...mapActions('cabman/GiamSatMangNgoaiVi', actionName),
    ctrlDuyChuyenDoiTuong() {
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
    getPositionNodeId(id) {
      const objIndex = this.pointLayers.findIndex((obj) => {
        return obj.id === id
      })
      if (objIndex >= 0) {
        return this.pointLayers[objIndex].position
      } else return null
    },
    async ACTIVE_OBJECT_MAP_V2(obj) {
      try {
        if (obj.objData) {
          if (!(obj.objData.focused == undefined || obj.objData.focused == null)) {
            this.mapVNPT.setCenter([obj.objData.focused.lng, obj.objData.focused.lat])
            this.mapVNPT.setZoom(20)
          }
          if (obj.objData.LOAIDT === 'KETCUOI' || obj.objData.LOAIDT === 'THUEBAO') {
            let objNodeId = obj.objData.ID.toString()
            if (obj.objData.PARENT_ID && obj.objData.PARENT_ID !== null) {
              objNodeId = obj.objData.PARENT_ID.toString()
            }
            let node = this.getPositionNodeId('KETCUOI_' + objNodeId)
            if (node !== null) {
              this.mapVNPT.setCenter([node.lng, node.lat])
              this.mapVNPT.setZoom(20)
              let activeNode = document.getElementsByClassName('KETCUOI_' + objNodeId + 'maps')
              activeNode[0].classList.add('highlight-node')
            }
          }
          if (obj.objData.LOAIDT === 'CAP') {
            let objNodeId = obj.objData.ID.toString()
            if (obj.objData.PARENT_ID && obj.objData.PARENT_ID !== null) {
              objNodeId = obj.objData.PARENT_ID.toString()
            }
            let node = this.getPositionNodeId('KETCUOI_' + objNodeId)
            if (node !== null) {
              this.mapVNPT.setCenter([node.lng, node.lat])
              this.mapVNPT.setZoom(20)
              let activeNode = document.getElementsByClassName('KETCUOI_' + objNodeId + 'maps')
              activeNode[0].classList.add('highlight-node')
            }
            let objEdgeId = obj.objData.ID.toString()
            let activePolyline = this.getPolylineFromID('CAP_' + objEdgeId + '_0')
            if (activePolyline) {
              clearInterval(this.stylePolylineInterval)
              this.isBlueColor = true
              this.stylePolylineInterval = setInterval(function () {
                if (this.isBlueColor) {
                  activePolyline.setStyle({ color: '#0036D3' })
                } else {
                  activePolyline.setStyle({ color: 'red' })
                }
                this.isBlueColor = !this.isBlueColor
              }, 500)
            }
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: BanDo.vue:1119 ~ ACTIVE_OBJECT_MAP_V2 ~ error:', error)
      }
    },
    menuNode_Clicked(event) {
      switch (event.option.slug) {
        case 'thue_bao_thuoc_kc':
          this.$emit('thueBaoThuocKetCuoi', event.item.id)
          break
        case 'sua_ket_cuoi':
          this.$emit('suaKetcuoi', event.item.id)
          break
        case 'tao_ket_cuoi':
          this.$emit('taoKetcuoi', event.item.id)
          break
        case 'dau_noi_ben_trong':
          this.$emit('dauNoiBenTrong', { KETCUOI_ID: event.item.id, KYHIEU_KC: event.item.symbol })
          break
        case 'chuyen_vao_trong_tram':
          this.$emit('chuyenvaoTrongTram', { KETCUOI_ID: event.item.id, KYHIEU_KC: event.item.symbol })
          break
        case 'tao_cap_toi_ketcuoi_cungtuyen':
          this.vaction = 'TaoCap'
          this.vnguon_id = event.item.id.split('_')
          this.$emit('taoCapKetNoiKetCuoiCungTuyen', event.item.id)
          break
        case 'tao_cap_ket_noi_ketcuoi_khactuyen':
          this.$emit('taoCapKetNoiKetCuoiKhacTuyen', event.item.id)
          break
      }
    },
    menuNodeTram_Clicked(event) {
      switch (event.option.slug) {
        case 'dau_noi_thiet_bi_tram':
          var doituong = event.item.id.split('_')
          this.doiTuong.tramtb_id = doituong[1]
          this.$bvModal.show('bpopupDauNoiTbi')
          break
        case 'sua_tram':
          this.$emit('suaTram', event.item.id)
          break
      }
    },
    menuNodeKhacTuyen_Clicked(event) {
      try {
        if (event) {
          var id = event.item.id.split('_')
          this.TIMKIEM_DOITUONG('KETCUOI|' + id[1] + '|TIM_THEO_ID', 1)
        }
      } catch (error) {
        console.log('🚀 ~ file: BanDoVNPT.vue:483 ~ menuNodeKhacTuyen_Clicked ~ error:', error)
      }
    },
    menuNodeCongBe_Clicked(event) {
      let id
      switch (event.option.slug) {
        case 'thuocTinhBe':
          id = event.item.id.split('_')[0]
          this.$emit('thuocTinhBe', id)
          break
        case 'chuyenTuyen':
          id = event.item.id.split('_')[0]
          this.$emit('chuyenTuyenCongBe', id)
          break
      }
    },
    menuNodeTuyenCot_Clicked(event) {
      let id
      switch (event.option.slug) {
        case 'lienketcot':
          var doituong = event.item.id.split('_')
          this.doiTuong.tramtb_id = doituong[1]
          this.$bvModal.show('bpopupDauNoiTbi')
          break
        case 'thuoctinh':
          let id = event.item.id.split('_')[1]
          let obj = {
            cotId: id
          }
          this.$emit('thuocTinhCot', obj)
          break
      }
    },
    menuNodeDiemUon_Clicked(event) {
      switch (event.option.slug) {
        case 'xoa_diem_uon':
          const nodeid = event.item.id
          const idpolyline = event.item.id_polyline
          const objIndex = this.pointDiemUonLayers.findIndex((obj) => obj.id === nodeid)
          if (objIndex > -1) {
            let node = this.pointDiemUonLayers[objIndex]
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
      }
    },
    luuToaDoBanDo() {
      var nodes = []
      for (let index = 0; index < this.pointLayers.length; index++) {
        const node = this.pointLayers[index]
        if (node.modified && !node.id.startsWith('DIEMUON_')) {
          let arr = node.id.split('_')
          let lat = node.position.lat
          let lng = node.position.lng
          nodes.push({ loai: arr[0], id: arr[1], lat: lat, lng: lng })
        }
      }
      var polylines = []
      for (let index = 0; index < this.polylineLayers.length; index++) {
        const polyline = this.polylineLayers[index]
        if (polyline.modified) {
          polylines.push({
            id: polyline.id,
            day_toado: polyline.path.filter((element) => {
              return element.id.startsWith('DIEMUON_')
            })
          })
        }
      }
      this.$emit('luuToaDoBanDo', nodes, polylines)
    },
    ctrlPhanCapMang() {
      if (this.pointLayers.length > 0) {
        this.location = {
          lat: this.pointLayers[0].position.lat,
          lng: this.pointLayers[0].position.lng
        }
        this.mapVNPT.setCenter([this.location.lng, this.location.lat])
        this.mapVNPT.setZoom(15)
      }
    },
    ctrlLuuToaDoBanDo() {
      this.luuToaDoBanDo()
    },
    ctrlThayDoiNguonDuLieu() {
      if (this.mapTypeId === 'ROADMAP') {
        this.mapTypeId = 'SATELLITE'
        this.mapVNPT.setStyle('https://maps.vnpt.vn/storage/vnptmaps/style/style_vetinh.json')
      } else {
        this.mapTypeId = 'ROADMAP'
        this.mapVNPT.setStyle('https://maps.vnpt.vn/tileserver/styles/mapvnpt_v2/style.json')
      }
      setTimeout(() => EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2'), 100)
    },
    ghimKetCuoi(item) {
      this.ghimNode = !this.ghimNode
      if (this.ghimNode) {
        this.ketcuoi_ghim = item
      } else this.ketcuoi_ghim = null
    },
    isObject(obj) {
      let sourceEventId = obj.originalEvent.srcElement.id
      return sourceEventId
    },
    addHighlightNguon(e) {
      let objNodeId = e.originalEvent.srcElement.id.split('_')
      this.highlightNguon = document.getElementsByClassName(objNodeId[0] + '_' + objNodeId[1] + 'maps')
      this.highlightNguon[0].classList.add('highlight-node')
    },
    addHighlightDich(e) {
      let objNodeId = e.originalEvent.srcElement.id.split('_')
      this.highlightDich = document.getElementsByClassName(objNodeId[0] + '_' + objNodeId[1] + 'maps')
      this.highlightDich[0].classList.add('highlight-node')
    },
    onEscPress() {
      this.vnguon_id = null
      this.vdich_id = null
      this.highlightNguon[0].classList.remove('highlight-node')
      this.highlightDich[0].classList.remove('highlight-node')
    },
    // duannv bổ sung xử lý tạo cáp mapVNPT
    async taoCapKetNoi(args) {
      let clickedItem = args.originalEvent.srcElement.id
      if (clickedItem) {
        if (this.vaction === 'TaoCapKetNoi') {
          if (this.vnguon_id === null || this.vnguon_id.length === 0) {
            this.vnguon_id = args.originalEvent.srcElement.id.split('_')
            this.addHighlightNguon(args)
            this.setState({ key: 'vnguon_id', value: this.vnguon_id[1] })
            this.toast && this.toast.close()
            this.showToast('Chọn kết cuối / trạm thiết bị đích', '<b>Nhấn ESC để hủy<b>')
            return
          }
          this.addHighlightDich(args)
          this.vdoituong_id = args.originalEvent.srcElement.id.split('_')
          if (this.vnguon_id[0] !== this.vdoituong_id[0] || this.vnguon_id[1] !== this.vdoituong_id[1]) {
            if (this.vdoituong_id[0] === 'CAP') {
              this.toast && this.toast.close()
              this.setState({ key: 'vaction', value: 'None' })
              this.setState({ key: 'vnguon_id', value: null })
              this.setState({ key: 'vdich_id', value: null })
              return
            }
            this.toast && this.toast.close()
            this.setState({ key: 'vdich_id', value: this.vdoituong_id })
            var donvi_id = null
            var ketcuoi_n_id = null
            var ketcuoi_d_id = null
            var donvi_td_id = null
            this.vdich_id = this.vdoituong_id
            if (this.vnguon_id[0] === 'TRAM') {
              donvi_id = this.vnguon_id[1]
            } else {
              ketcuoi_n_id = this.vnguon_id[1]
            }
            if (this.vdich_id[0] === 'TRAM') {
              donvi_td_id = this.vdich_id[1]
            } else {
              ketcuoi_d_id = this.vdich_id[1]
            }
            var data = {
              capgoc: this.capgoc,
              donvi: { DONVI_ID: donvi_id },
              donvi_td_id: donvi_td_id,
              ketcuoi_n_id: ketcuoi_n_id,
              ketcuoi_d_id: ketcuoi_d_id,
              kieucap_id: 0,
              toql: this.toql
            }
            this.$refs.ModalQLCapExt.taoCap(data)
              .then((result) => {
                if (result.ok) {
                  EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2')
                  this.highlightNguon[0].classList.remove('highlight-node')
                  this.highlightDich[0].classList.remove('highlight-node')
                } else {
                  this.highlightNguon[0].classList.remove('highlight-node')
                  this.highlightDich[0].classList.remove('highlight-node')
                }
                this.setState({ key: 'vaction', value: 'None' })
                this.setState({ key: 'vnguon_id', value: null })
                this.setState({ key: 'vdich_id', value: null })
                this.vnguon_id = null
              })
              .catch(() => {
                this.highlightNguon[0].classList.remove('highlight-node')
                this.highlightDich[0].classList.remove('highlight-node')
                this.setState({ key: 'vaction', value: 'None' })
                this.setState({ key: 'vnguon_id', value: null })
                this.setState({ key: 'vdich_id', value: null })
                this.vnguon_id = null
              })
          }
        }
      }
    },
    // hungvv: bổ sung hàm xử lý nạp kết cuối cùng tuyến
    leftclickMap(event) {
      let clickedItem = this.isObject(event)
      if (clickedItem) {
        let vdoituong_id = clickedItem.split('_')
        console.log(this.vaction)
        if (this.vaction !== 'None') {
          if (this.vaction === 'TaoCap') {
            console.log('🚀 ~ file: GianDo.vue ~ line 63000 ~ onClick ~ this.vnguon_id', this.vnguon_id)
            if (this.vnguon_id === null || this.vnguon_id.length === 0) {
              this.vnguon_id = this.vdoituong_id
              this.toast && this.toast.close()
              this.showToast('Chọn kết cuối / trạm thiết bị đích', '<b>Nhấn ESC để hủy<b>')
              return
            }
            if (this.vnguon_id[0] !== vdoituong_id[0] || this.vnguon_id[1] !== vdoituong_id[1]) {
              if (vdoituong_id[0] == 'CAP') {
                this.toast && this.toast.close()
                this.vaction = 'None'
                this.vnguon_id = null
                this.vdich_id = null
                return
              }
              this.toast && this.toast.close()
              this.vdich_id = vdoituong_id
              var donvi_id = null
              var ketcuoi_n_id = null
              var ketcuoi_d_id = null
              var donvi_td_id = null
              console.log('🚀 ~ file: GianDo.vue:784 ~ onClick ~ this.vnguon_id:', this.vnguon_id)
              console.log('🚀 ~ file: GianDo.vue:784 ~ onClick ~ this.vdich_id:', this.vdich_id)
              if (this.vnguon_id[0] === 'TRAM') {
                donvi_id = this.vnguon_id[1]
              } else {
                ketcuoi_n_id = this.vnguon_id[1]
              }
              if (this.vdich_id[0] === 'TRAM') {
                donvi_td_id = this.vdich_id[1]
              } else {
                ketcuoi_d_id = this.vdich_id[1]
              }
              var data = {
                capgoc: this.capgoc,
                donvi: { DONVI_ID: donvi_id },
                donvi_td_id: donvi_td_id,
                ketcuoi_n_id: ketcuoi_n_id,
                ketcuoi_d_id: ketcuoi_d_id,
                kieucap_id: 0,
                toql: this.toql
              }
              console.log('🚀 ~ file: GianDo.vue:795 ~ onClick ~ data:', data)
              this.$refs.ModalQLCapExt.taoCap(data)
                .then((result) => {
                  if (result.ok) {
                    EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2')
                  } else {
                  }
                  this.vaction = 'None'
                  this.vnguon_id = null
                  this.vdich_id = null
                })
                .catch(() => {
                  this.vaction = 'None'
                  this.vnguon_id = null
                  this.vdich_id = null
                })
            }
            return
          }
        }
        return
      }
      if (this.ghimNode & (this.ketcuoi_ghim !== null)) {
        let lat = event.lngLat.lat
        let lng = event.lngLat.lng
        let ketcuoi_id = this.ketcuoi_ghim.FULL_ID
        let node = this.getNodeFromID(ketcuoi_id)
        if (node) {
          node.remove()
          node.position.lat = lat
          node.position.lng = lng
          node.modified = true
          node.visible = true
          this.reNode(node)
          this.isMapChanged = true
          this.remove_ds_ketcuoi_ctd_id(ketcuoi_id)
          for (let index = 0; index < this.polylineLayers.length; index++) {
            if (this.polylineLayers[index].source === ketcuoi_id || this.polylineLayers[index].dest === ketcuoi_id) {
              for (let index2 = 0; index2 < this.polylineLayers[index].path.length; index2++) {
                if (this.polylineLayers[index].path[index2].id === ketcuoi_id) {
                  this.polylineLayers[index].path[index2].lat = lat
                  this.polylineLayers[index].path[index2].lng = lng
                  this.polylineLayers[index].modified = true
                  this.rePolyline(this.polylineLayers[index])
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
    async getRoadBetween2Points(input) {
      this.basemapApi.getRoadBetween2Points(input.value, TOKEN, (evt) => {
        console.log('🚀 ~ file: BanDoVNPT.vue:602 ~ this.basemapApi.getRoadBetween2Points ~ evt:', evt)
        if (evt.paths.length > 0) {
          let encryption = VnptMap.initEncryption()
          var ddata = encryption.decode(evt.paths[0].geom_encoded)
          let data = null
          for (let i = 0; i < ddata.decodedData.length; ++i) {
            let e = ddata.decodedData[i]
            if (data === null) {
              data = e[0] + ',' + e[1]
            } else {
              data = data + ';' + e[0] + ',' + e[1]
            }
          }
          if (data !== null) {
          }
        }
      })
    },
    menuMap_Clicked(event) {
      switch (event.option.slug) {
        case 'lam_tuoi':
          EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2')
          break
        case 'tim_dia_diem':
          this.$prompt('Nhập địa chỉ hoặc tọa độ muốn tìm', 'Nhập text', {
            confirmButtonText: 'Xác nhận',
            cancelButtonText: 'Hủy bỏ'
          })
            .then(({ value }) => {
              var re = new RegExp(/-?(\d*\.)?\d+,\s*-?(\d*\.)?\d+/)
              if (re.test(value)) {
                let arr = value.split(',')
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
                this.mapVNPT.setCenter([arr[1], arr[0]])
                this.mapVNPT.setZoom(12)
              } else {
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
                  }
                })
              }
            })
            .catch(() => {
              this.pointSearchsLayers = []
            })
          break
        case 'day_la_dau':
          this.basemapApi.getAddressByLonLat(this.locationrightclickMap, TOKEN, (rs) => {
            console.log('🚀 ~ file: BanDoVNPT.vue:587 ~ getAddress ~ rs:', rs)
            if (rs.status) {
              const popup = VnptMap.initPopup([this.locationrightclickMap.lng, this.locationrightclickMap.lat], rs.result.address)
              popup.addLayerTo(this.mapVNPT)
            } else {
              console.error(rs.message)
            }
          })
          break
        case 'dokhoangcach':
          this.batDoKhoangCach = !this.batDoKhoangCach
          break
        case 'xoaphepdo':
          this.batDoKhoangCach = false
          break
        case 'tao_ket_cuoi_vnptmap':
          this.basemapApi.getAddressByLonLat(this.locationrightclickMap, TOKEN, (rs) => {
            if (rs.status) {
              this.$emit('taoketCuoiVNPTMap', this.locationrightclickMap, TOKEN, rs.result)
            } else {
              console.error(rs.message)
            }
          })
      }
    },
    menuPolyline_Clicked(event) {
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
          for (let index = 0; index < this.pointDiemUonLayers.length; index++) {
            if (this.pointDiemUonLayers[index].id_polyline === event.item.id && this.pointDiemUonLayers[index].isDiemuon === true) {
              this.pointDiemUonLayers[index].visible = true
              this.pointDiemUonLayers[index].setVisible(true)
            }
          }
          break
        case 'tao_diem_uon':
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
        case 'xoa_all_diem_uon':
          if (event.item.path.length === 2) return
          for (let i = 0; i < event.item.path.length; ++i) {
            let e = event.item.path[i]
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
          event.item.path = [event.item.path[0], event.item.path[event.item.path.length - 1]]
          event.item.modified = true
          this.rePolyline(event.item)
          break
        case 'auto_tao_diem_uon':
          this.$emit('hide-timkiemMap')
          let path = event.item.path
          let points = { pointStart: [path[0].lng, path[0].lat], pointStop: [path[path.length - 1].lng, path[path.length - 1].lat] }
          this.basemapApi.getRoadBetween2Points(points, TOKEN, (evt) => {
            console.log('🚀 ~ file: BanDoVNPT.vue:799 ~ this.basemapApi.getRoadBetween2Points ~ evt:', evt)
            if (evt.paths.length > 1) {
              let data = []
              let encryption = VnptMap.initEncryption()
              for (let index = 0; index < evt.paths.length; index++) {
                const element = evt.paths[index]
                const ddata = encryption.decode(element.geom_encoded)
                data.push({
                  TUYEN: element.distance,
                  DS_TOADO: ddata,
                  DATA: element,
                  DIEM_DAU: event.item.path[0],
                  DIEM_CUOI: event.item.path[event.item.path.length - 1]
                })
              }
              this.$emit('show-timkiemMap', { data, POLYLINE: event.item })
              return
            }
            if (evt.paths.length > 0) {
              let encryption = VnptMap.initEncryption()
              var ddata = encryption.decode(evt.paths[0].geom_encoded)
              if (ddata.decodedData.length === 0) return
              for (let i = 0; i < event.item.path.length; ++i) {
                let e = event.item.path[i]
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
              event.item.path = [event.item.path[0], event.item.path[event.item.path.length - 1]]
              for (let i = 0; i < ddata.decodedData.length; ++i) {
                let e = ddata.decodedData[i]
                const point = [e[1], e[0]]
                const id = 'DIEMUON_' + this.create_UUID()
                event.item.path.splice(event.item.path.length - 1, 0, { id, lat: e[0], lng: e[1] })
                const obj = {
                  iconUrl: event.item.iconDiemUonBase64,
                  id: id,
                  iconWidth: 15,
                  iconHeight: 15,
                  isDraggable: true
                }
                let pointLayer = VnptMap.initPoint(point, obj)
                pointLayer.id = id
                pointLayer.id_polyline = event.item.id
                pointLayer.modified = false
                pointLayer.draggable = true
                pointLayer.isDiemuon = true
                pointLayer.visible = true
                pointLayer.position = { lng: e[1], lat: e[0] }
                pointLayer.setVisible(true)
                pointLayer.iconUrl = event.item.iconDiemUonBase64
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
              event.item.modified = true
              this.rePolyline(event.item)
            }
          })
          break
      }
    },
    menuPolylineCongBe_Clicked(event) {
      switch (event.option.slug) {
        case 'chuyen_nhanh_cap':
          this.$emit('chuyennhanhcap', event.item.id)
          break
        case 'chen_ket_cuoi':
          this.$emit('chenketcuoi', event.item.id)
          break
      }
    },
    menuPolylineTuyenCot_Clicked(event) {
      switch (event.option.slug) {
        case 'chuyen_nhanh_cap':
          this.$emit('chuyennhanhcap', event.item.id)
          break
        case 'chen_ket_cuoi':
          this.$emit('chenketcuoi', event.item.id)
          break
      }
    },
    insertAt(arr, index, newItem) {
      return [...arr.slice(0, index), newItem, ...arr.slice(index)]
    },
    generateId() {
      const randomId = Math.random().toString(36).substr(2, 9) + '_' + Date.now().toString(36)
      return randomId
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
          this.pointDiemUonLayers[objIndex].modified = true
          let length = this.polylineLayers.length
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
        this.mapVNPT.addLayer(pointLayer)
        return true
      } catch (error) {
        console.log('🚀 ~ file: BanDoVNPT.vue:613 ~ nodeUpdate ~ error:', error)
        return false
      }
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
    async setinfoMapDefault(infoMapDefault) {
      try {
        this.location.lat = infoMapDefault.center.lat
        this.location.lng = infoMapDefault.center.lng
        this.mapVNPT.setCenter([this.location.lng, this.location.lat])
        this.zoomLevel = 20
        this.mapVNPT.setZoom(this.zoomLevel)
      } catch (error) {
        console.log('🚀 ~ file: BanDoVNPT.vue:335 ~ setinfoMapDefault ~ error:', error)
      }
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
    rightclickPolyline(event, polyline_id) {
      let polyline = this.getPolylineFromID(polyline_id)
      let menu
      this.diemuonLatlng = event.lngLat
      this.activePolyline = polyline
      this.mouselatLng.lat = event.lngLat.lat
      this.mouselatLng.lng = event.lngLat.lng
      // Save to local storage
      localStorage.setItem('mouseLatLng', JSON.stringify(this.mouselatLng))
      this.$refs.vueSimpleContextMenuMap && this.$refs.vueSimpleContextMenuMap.hideContextMenu()
      this.$refs.vueSimpleContextMenuNode && this.$refs.vueSimpleContextMenuNode.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeTram && this.$refs.vueSimpleContextMenuNodeTram.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolyline && this.$refs.vueSimpleContextMenuPolyline.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeDiemUon && this.$refs.vueSimpleContextMenuNodeDiemUon.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeKhacTuyen && this.$refs.vueSimpleContextMenuNodeKhacTuyen.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolylineCongBe && this.$refs.vueSimpleContextMenuPolylineCongBe.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolylineTuyenCot && this.$refs.vueSimpleContextMenuPolylineTuyenCot.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeCongBe && this.$refs.vueSimpleContextMenuNodeCongBe.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeTuyenCot && this.$refs.vueSimpleContextMenuNodeTuyenCot.hideContextMenu()

      if(polyline_id.split('_')[polyline_id.split('_').length - 1] === 'CONGBE'){
        this.$refs.vueSimpleContextMenuPolylineCongBe.showMenu(event, polyline)
        menu = document.getElementById('menuPolylineCongBeVNPTMAP')
      } else if (polyline_id.split('_')[polyline_id.split('_').length - 1] === 'TUYENCOT'){
        this.$refs.vueSimpleContextMenuPolylineTuyenCot.showMenu(event, polyline)
        menu = document.getElementById('menuPolylineTuyenCotVNPTMAP')
      } else {
        this.$refs.vueSimpleContextMenuPolyline.showMenu(event, polyline)
        menu = document.getElementById('menuPolylineVNPTMAP')
      }
      var left = event.point.x
      var top = event.point.y
      menu.style.left = left + 'px'
      menu.style.top = top + 'px'
      menu.position = 'fixed'
      menu.classList.add('menu-context')
    },
    rightclickNodeDiemUon(event, point_id) {
      let node = this.getNodeDiemUonFromID(point_id)
      this.$refs.vueSimpleContextMenuMap && this.$refs.vueSimpleContextMenuMap.hideContextMenu()
      this.$refs.vueSimpleContextMenuNode && this.$refs.vueSimpleContextMenuNode.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeTram && this.$refs.vueSimpleContextMenuNodeTram.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolyline && this.$refs.vueSimpleContextMenuPolyline.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeDiemUon && this.$refs.vueSimpleContextMenuNodeDiemUon.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeKhacTuyen && this.$refs.vueSimpleContextMenuNodeKhacTuyen.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolylineCongBe && this.$refs.vueSimpleContextMenuPolylineCongBe.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolylineTuyenCot && this.$refs.vueSimpleContextMenuPolylineTuyenCot.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeCongBe && this.$refs.vueSimpleContextMenuNodeCongBe.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeTuyenCot && this.$refs.vueSimpleContextMenuNodeTuyenCot.hideContextMenu()

      this.$refs.vueSimpleContextMenuNodeDiemUon.showMenu(event, node)
      const menu = document.getElementById('menuNodeDiemUonVNPTMAP')
      var left = event.pointEvent.layerX
      var top = event.pointEvent.layerY
      menu.style.left = left + 'px'
      menu.style.top = top + 'px'
      menu.position = 'fixed'
      menu.classList.add('menu-context')
    },
    rightclickMap(event) {
      this.locationrightclickMap = event.lngLat
      this.$refs.vueSimpleContextMenuMap && this.$refs.vueSimpleContextMenuMap.hideContextMenu()
      this.$refs.vueSimpleContextMenuNode && this.$refs.vueSimpleContextMenuNode.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolyline && this.$refs.vueSimpleContextMenuPolyline.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeDiemUon && this.$refs.vueSimpleContextMenuNodeDiemUon.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeKhacTuyen && this.$refs.vueSimpleContextMenuNodeKhacTuyen.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolylineCongBe && this.$refs.vueSimpleContextMenuPolylineCongBe.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolylineTuyenCot && this.$refs.vueSimpleContextMenuPolylineTuyenCot.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeCongBe && this.$refs.vueSimpleContextMenuNodeCongBe.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeTuyenCot && this.$refs.vueSimpleContextMenuNodeTuyenCot.hideContextMenu()

      this.$refs.vueSimpleContextMenuMap.showMenu(event)
      const menu = document.getElementById('menuMap')
      var left = event.point.x
      var top = event.point.y
      menu.style.left = left + 'px'
      menu.style.top = top + 'px'
      menu.position = 'fixed'
      menu.classList.add('menu-context')
    },
    ds_donvi: async function () {
      try {
        var rs = await this.$root.context.get('/web-cabman/ban-do-mang-cap/danh-sach-dv-nv')
        return rs.data
      } catch (error) {
        return []
      } finally {
      }
    },
    async TIMKIEM_DOITUONG(searchquery, loai) {
      var query = searchquery + '|NGUOIDUNG_ID=' + this.$root.token.getNguoiDungID()
      try {
        var rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/timkiem-doituong-v2', { query: query })
        if (rs.data.dsDOITUONG.length === 0) {
          await this.$alert('Không tìm thấy tuyến cáp của đối tượng', 'Thông báo', {
            confirmButtonText: 'Đóng',
            type: 'error'
          })
          return
        } else {
          // Kiểm tra quyền thao tác với tuyến của tổ kỹ thuật khác
          if (this.ds_dv.length === 0) {
            this.ds_dv = await this.ds_donvi()
          }
          if (rs.data.dsTOKT.length > 0 && this.ds_dv.length > 0) {
            var tmp = rs.data.dsTOKT.filter((e) => {
              return e.SELECTED === 1
            })
            if (tmp.length > 0) {
              var isPermitted = false
              for (let index = 0; index < tmp.length; index++) {
                const DONVI_ID = tmp[index].ID
                const idx = this.ds_dv.findIndex((e) => e.DONVI_ID === DONVI_ID)
                if (idx > -1) {
                  isPermitted = true
                  break
                }
              }
              if (!isPermitted) {
                await this.$alert('Đối tượng thuộc quản lý của tổ kỹ thuật ' + tmp[0].NAME + ', bạn không có quyền thao tác.', 'Thông báo', {
                  confirmButtonText: 'Đóng',
                  type: 'error'
                })
                return
              }
            }
          }
          // End Kiểm tra quyền thao tác với tuyến của tổ kỹ thuật khác
          var dsDOITUONG = rs.data.dsDOITUONG
          if (dsDOITUONG[0].NHOMLC_ID === 0) {
            this.set_nhomlc(0)
          } else {
            this.set_nhomlc(1)
          }
          EventBus.$emit('SET_STATE', { dsTTVTS: rs.data.dsTTVTS, dsTOKT: rs.data.dsTOKT, dsTONGDAI: rs.data.dsTONGDAI, dsDOITUONG: rs.data.dsDOITUONG, dsDANDO_VEGOC: rs.data.dsDANDO_VEGO })
        }
      } catch (error) {
        this.$alert(error.response.data.message_detail, error.response.data.message, {
          confirmButtonText: 'Đóng',
          type: 'error'
        })
      }
    },
    rightclickNode(event, point_id) {
      this.$refs.vueSimpleContextMenuMap && this.$refs.vueSimpleContextMenuMap.hideContextMenu()
      this.$refs.vueSimpleContextMenuNode && this.$refs.vueSimpleContextMenuNode.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeTram && this.$refs.vueSimpleContextMenuNodeTram.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolyline && this.$refs.vueSimpleContextMenuPolyline.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeDiemUon && this.$refs.vueSimpleContextMenuNodeDiemUon.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeKhacTuyen && this.$refs.vueSimpleContextMenuNodeKhacTuyen.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolylineCongBe && this.$refs.vueSimpleContextMenuPolylineCongBe.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolylineTuyenCot && this.$refs.vueSimpleContextMenuPolylineTuyenCot.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeCongBe && this.$refs.vueSimpleContextMenuNodeCongBe.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeTuyenCot && this.$refs.vueSimpleContextMenuNodeTuyenCot.hideContextMenu()

      let node = this.getNodeFromID(point_id)
      var menu = null
      if (node.id.startsWith('KETCUOIKHACTUYEN_')) {
        this.$refs.vueSimpleContextMenuNodeKhacTuyen.showMenu(event, node)
        menu = document.getElementById('menuNodeKhacTuyenVNPTMAP')
      } else {
        if (node.id.startsWith('KETCUOI_')) {
          this.$refs.vueSimpleContextMenuNode.showMenu(event, node)
          menu = document.getElementById('menuNodeVNPTMAP')
        } else if (node.id.startsWith('COT_')) {
          this.$refs.vueSimpleContextMenuNodeTuyenCot.showMenu(event, node)
          menu = document.getElementById('menuNodeTuyenCotVNPTMAP')
        }
        else if (node.id.split('_')[node.id.split('_').length - 1] === 'CONGBE') {
          this.$refs.vueSimpleContextMenuNodeCongBe.showMenu(event, node)
          menu = document.getElementById('menuNodeCongBeVNPTMAP')
        } else {
          this.$refs.vueSimpleContextMenuNodeTram.showMenu(event, node)
          menu = document.getElementById('menuNodeTramVNPTMAP')
        }
      }
      var left = event.pointEvent.layerX
      var top = event.pointEvent.layerY
      menu.style.left = left + 'px'
      menu.style.top = top + 'px'
      menu.position = 'fixed'
      menu.classList.add('menu-context')
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
        } catch (error) {}
      }
      for (let index = 0; index < this.pointDiemUonLayers.length; index++) {
        let element = this.pointDiemUonLayers[index]
        try {
          element && element.remove()
          element = null
        } catch (error) {}
      }
      for (let index = 0; index < this.polylineLayers.length; index++) {
        let element = this.polylineLayers[index]
        try {
          element && element.remove()
          element = null
        } catch (error) {}
      }
      for (let index = 0; index < this.pointSearchsLayers.length; index++) {
        let element = this.pointSearchsLayers[index]
        try {
          element && element.remove()
          element = null
        } catch (error) {}
      }
      this.pointLayers = []
      this.pointDiemUonLayers = []
      this.pointSearchsLayers = []
      this.polylineLayers = []
      clearInterval(this.stylePolylineInterval)
    },
    updateSized() {
      setTimeout(() => this.mapVNPT.updateSized(), 10)
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
    setNodes(nodes) {
      let _nodes = nodes.filter((n) => n.visible === true && n.isDiemuon === false)
      for (let index = 0; index < _nodes.length; index++) {
        const node = _nodes[index]
        const point = [node.position.lng, node.position.lat]
        const obj = {
          iconUrl: node.icon.url,
          id: node.id,
          iconWidth: node.icon.size.width,
          iconHeight: node.icon.size.height,
          isDraggable: false,
          className: node.id + 'maps'
        }
        let pointLayer = VnptMap.initPoint(point, obj)
        pointLayer.symbol = node.symbol
        pointLayer.setLabel(node.symbol)
        pointLayer.setLabelVisible(true)
        pointLayer.id = node.id
        pointLayer.id_polyline = node.id_polyline
        pointLayer.modified = node.modified
        pointLayer.draggable = false
        pointLayer.isDiemuon = node.isDiemuon
        pointLayer.visible = node.visible && node.position.lat * node.position.lng > 0
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
        pointLayer.addLayerTo(this.mapVNPT)
        this.pointLayers.push(pointLayer)
      }
      let _nodesDiemUon = nodes.filter((n) => n.isDiemuon === true)
      for (let index = 0; index < _nodesDiemUon.length; index++) {
        const node = _nodesDiemUon[index]
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
        pointLayer.position = node.position
        pointLayer.iconUrl = node.iconUrl
        pointLayer.setVisible(false)

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
    },
    reNode(node, draggable = false) {
      const point = [node.position.lng, node.position.lat]
      const obj = {
        iconUrl: node.icon.url,
        id: node.id,
        iconWidth: node.icon.size.width,
        iconHeight: node.icon.size.height,
        isDraggable: draggable
      }
      let pointLayer = VnptMap.initPoint(point, obj)
      pointLayer.symbol = node.symbol
      pointLayer.setLabel(node.symbol)
      pointLayer.setLabelVisible(true)
      pointLayer.id = node.id
      pointLayer.id_polyline = node.id_polyline
      pointLayer.modified = node.modified
      pointLayer.draggable = draggable
      pointLayer.isDiemuon = false
      pointLayer.visible = true
      pointLayer.position = node.position
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
          me.rightclickPolyline(e, polylineLayer.id)
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
        me.rightclickPolyline(e, polylineLayer.id)
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
    setPolylines(polylines) {
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
    setLocationEx(position) {
      if (position === null || position === undefined) {
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
      // this.mapVNPT.flyTo([this.location.lng, this.location.lat], 18)
      this.mapVNPT.setCenter([this.location.lng, this.location.lat])
      this.mapVNPT.setZoom(18)
    },
    capNhatCap: function (args) {
      let layer = this.polylineLayers.find((x) => x.id == 'CAP_' + args.cap_id + '_0')
      if (!(layer == null || layer == undefined)) {
        layer.on('mouseenter', (e) => {
          layer.setStyle({ color: 'red' })
          let lng = e.lngLat.lng
          let lat = e.lngLat.lat
          let content = '<b>' + args.ten_cap + ' [' + args.sodoiday + '] ' + args.chieudai + 'm</b>'
          this.popupInstance && this.popupInstance.remove()
          this.popupInstance = VnptMap.initPopup([lng, lat], content, {
            offset: 20
          })
          this.popupInstance.addLayerTo(this.mapVNPT)
        })
      }
    },
    capNhatKetCuoi: function (args) {
      let layer = this.pointLayers.find((x) => x.id == 'KETCUOI_' + args.ketcuoi_id)
      if (!(layer == null || layer == undefined)) {
        layer.setLabel(args.kyhieu)
        const point = [args.kinhdo, args.vido]
        layer.setData(point)
        let ds = this.polylineLayers.filter((x) => x.source == layer.id || x.dest == layer.id)
        if (ds.length > 0) {
          for (let i = 0; i < ds.length; i++) {
            const d = ds[i]
            console.log('d', d)
            if (d.path[0].id == layer.id) {
              let t = [
                [args.kinhdo, args.vido],
                [d.path[1].lng, d.path[1].lat]
              ]
              d.setData(t)
            } else {
              let t = [
                [d.path[0].lng, d.path[0].lat],
                [args.kinhdo, args.vido]
              ]
              d.setData(t)
            }
          }
        }
        layer.on(this.mapVNPT, 'mouseenter', (e) => {
          let lng = e.coordinates[0]
          let lat = e.coordinates[1]
          let content = '<strong>' + args.kyhieu + '</strong><br>' + args.diachi
          this.popupInstance && this.popupInstance.remove()
          this.popupInstance = VnptMap.initPopup([lng, lat], content, {
            offset: 20
          })
          this.popupInstance.addLayerTo(this.mapVNPT)
        })
      }
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
    },
    showToast(title, msg) {
      this.toast = this.$notify.success({
        title: title,
        message: msg,
        showClose: false,
        dangerouslyUseHTMLString: true,
        duration: 0
      })
      console.log('this.toast', this.toast)
    },
    hideToast() {
      try {
        this.toast && this.toast.close()
        // diagramInstance.selectedItems.connectors = []
        // diagramInstance.selectedItems.nodes = []
        this.highlightNguon[0].classList.remove('highlight-node')
        this.highlightDich[0].classList.remove('highlight-node')
        this.setState({ key: 'vaction', value: 'None' })
        this.setState({ key: 'vnguon_id', value: null })
        this.setState({ key: 'vdich_id', value: null })
      } catch (error) {}
    },
    setNodesCongBe(nodes) {
      let _nodes = nodes.filter((n) => n.visible === true && n.isDiemuon === false)
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
        pointLayer.visible = node.visible && node.position.lat * node.position.lng > 0
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
        pointLayer.onDrag('dragstart', (e) => {})
        pointLayer.onDrag('drag', (e) => {
          this.isMapChanged = true
          const nodeid = pointLayer.id
          let lat = e.coordinates.lat
          let lng = e.coordinates.lng
          const objIndex = this.pointLayers.findIndex((obj) => obj.id == nodeid)
          if (objIndex < 0) return
          this.pointLayers[objIndex].position.lat = lat
          this.pointLayers[objIndex].position.lng = lng
          this.pointLayers[objIndex].modified = true
          const length = this.polylineLayers.length
          let copiedArray = new Array(length)
          for (let i = 0; i < length; i++) {
            let polyline = this.polylineLayers[i]
            if (nodeid == polyline.dest || nodeid == polyline.source || nodeid.toString().startsWith('DIEMUON_')) {
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
        pointLayer.onDrag('dragend', (e) => {})
        pointLayer.on(this.mapVNPT, 'click', (e) => {})
        pointLayer.on(this.mapVNPT, 'contextmenu', (e) => {
          this.point.x = e.pointEvent.x
          this.point.y = e.pointEvent.y
          me.rightclickNode(e, pointLayer.id)
        })
        pointLayer.addLayerTo(this.mapVNPT)
        this.pointLayers.push(pointLayer)
      }
      let _nodesDiemUon = nodes.filter((n) => n.isDiemuon == true)
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
        pointLayer.onDrag('dragstart', (e) => {})
        pointLayer.onDrag('dragend', (e) => {})
        pointLayer.onDrag('drag', (e) => {
          this.isMapChanged = true
          const nodeid = pointLayer.id
          let lat = e.coordinates.lat
          let lng = e.coordinates.lng
          const objIndex = this.pointDiemUonLayers.findIndex((obj) => obj.id == nodeid)
          if (objIndex < 0) return
          this.pointDiemUonLayers[objIndex].position.lat = lat
          this.pointDiemUonLayers[objIndex].position.lng = lng
          this.pointDiemUonLayers[objIndex].modified = true
          const length = this.polylineLayers.length
          let copiedArray = new Array(length)
          for (let i = 0; i < length; i++) {
            let polyline = this.polylineLayers[i]
            if (nodeid == polyline.dest || nodeid == polyline.source || nodeid.toString().startsWith('DIEMUON_')) {
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
    setPolylinesCongBe(polylines) {
      console.log('🚀 ~ setPolylines ~ polylines:', polylines)
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
        polylineLayer.on('mousedown', (e) => {})
        this.polylineLayers.push(polylineLayer)
      }
    },
    luuToaDoBanDoCongBe() {
      var nodes = []
      for (let index = 0; index < this.pointLayers.length; index++) {
        const node = this.pointLayers[index]
        if (node.modified && !node.id.toString().startsWith('DIEMUON_')) {
          let lat = node.position.lat
          let lng = node.position.lng
          nodes.push({ loai: 'BECAP', id: node.id, lat: lat, lng: lng })
        }
      }
      var polylines = []
      for (let index = 0; index < this.polylineLayers.length; index++) {
        const polyline = this.polylineLayers[index]
        if (polyline.modified) {
          polylines.push({
            id: polyline.id,
            day_toado: polyline.path.filter((element) => {
              return element.id.toString().startsWith('DIEMUON_')
            })
          })
        }
      }
      this.$emit('luuToaDoBanDo', nodes, polylines)
    },
    setLocationExCongBe(position) {
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
    setNodesCot(nodes) {
      let _nodes = nodes.filter((n) => n.visible === true && n.isDiemuon === false)
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
        pointLayer.visible = node.visible && node.position.lat * node.position.lng > 0
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
        pointLayer.onDrag('dragstart', (e) => {})
        pointLayer.onDrag('drag', (e) => {
          this.isMapChanged = true
          const nodeid = pointLayer.id
          let lat = e.coordinates.lat
          let lng = e.coordinates.lng
          const objIndex = this.pointLayers.findIndex((obj) => obj.id == nodeid)
          if (objIndex < 0) return
          this.pointLayers[objIndex].position.lat = lat
          this.pointLayers[objIndex].position.lng = lng
          this.pointLayers[objIndex].modified = true
          const length = this.polylineLayers.length
          let copiedArray = new Array(length)
          for (let i = 0; i < length; i++) {
            let polyline = this.polylineLayers[i]
            if (nodeid == polyline.dest || nodeid == polyline.source || nodeid.toString().startsWith('DIEMUON_')) {
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
        pointLayer.onDrag('dragend', (e) => {})
        pointLayer.on(this.mapVNPT, 'click', (e) => {
          me.leftclickNode(e, pointLayer)
        })
        pointLayer.on(this.mapVNPT, 'contextmenu', (e) => {
          this.point.x = e.pointEvent.x
          this.point.y = e.pointEvent.y
          me.rightclickNode(e, pointLayer.id)
        })
        pointLayer.addLayerTo(this.mapVNPT)
        this.pointLayers.push(pointLayer)
      }
    },
    setPolylinesCot(polylines) {
      this.polylines = polylines
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
    setLocationExCot(position) {
      if (position === null || position === undefined) {
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

.my-marker-label-white {
  color: #fff !important;
  text-shadow: -1px 1px 0 #000, 1px 1px 0 #000, 1px -1px 0 #000, -1px -1px 0 #000;
}

.my-marker-label-black {
  color: #000 !important;
  text-shadow: -1px 1px 0 #fff, 1px 1px 0 #fff, 1px -1px 0 #fff, -1px -1px 0 #fff;
}

.mapVNPTGiamSatMangNgoaiVi {
  position: initial;
  height: 800px;
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

.highlight-node {
  border: 2px solid red;
  border-radius: 0;
  animation: highlightNode 0.5s infinite alternate;
}

@keyframes highlightNode {
  0% {
    border-color: red;
  }
  100% {
    border-color: green;
  }
}
</style>
