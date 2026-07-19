<template src="./index.template.html"></template>

<script>
import Vue from 'vue'
import { mapActions, mapState, mapGetters } from 'vuex'
import { getterName, actionName, statePropertyName } from '../../../store'
import BanDoVNPT from '../../../components2/BanDoVNPT.vue'
import GianDo from '../../../components2/GianDo.vue'
import GraphVizControlGianDo from '../../../components2/GraphVizControlGianDo.vue'
import GraphPolyline from '../../../structures/GraphPolyline'
import GraphNode from '../../../structures/GraphNode'
import DiagramLib from '@/utils/DiagramLib'
import ModalTracnghiemExt from '../../../modal/TracNghiem'
import ModalChonDSKetCuoiExt from '../../../modal/ChonDSKetCuoiExt'
import ModalQLKetCuoiExt from '../../../modal/QLKetCuoiExt2'
import ModalChonTramThietBiExt from '../../../modal/ChonTramThietBiExt'
import ModalQLCapExt from '../../../../BanDoMangCap/modal/QLCapExt2'
import ModalPhanChiaDoanCapChenKetCuoi from '../../../modal/PhanChiaDoanCapChenKetCuoi'
import DauNoiCap from '../../../../DauNoiCap/DauNoiCap'
import TraCuuTBaoCap from '../../../../tracuu-thuebao-theo-cap/TraCuuTBao'
import TraCuuTBaoKetCuoi from '../../../../tracuu-thuebao-theo-ketcuoi/TraCuuTBao'
import QuanLyCapReNhanh from '../../../../QuanLyCapReNhanh/QuanLyCapReNhanh'
import QuanLyTramThietBi from '../../../../QuanLyMangTruyenDan-Cuong/QuanLyTramThietBi'
import ModalQLBe from '../../../../BanDoTuyenCong/QLBe/Modal'
import ModalDsBe from '../../../../BanDoTuyenCong/DanhSachBe/Modal'
import ModalDsTuyenCB from '../../../../BanDoTuyenCong/DanhSachTuyenCongBe/Modal'
import ModalQLDoanCongVaOngCong from '../../../../BanDoTuyenCong/QLDoanCongVaCacOngCongNamTrongDoanCong/Modal'
import ModalQLCot from '../../../../BanDoTuyenCot/QLCot/Modal'
import EventBus from '@/utils/eventBus'
import { debounce } from './debounce'
import DynamicMarker from '@/modules/CABMAN/BanDoMangCap/components2/DynamicMarker'
const DynamicMarkerConstructor = Vue.extend(DynamicMarker)
export default {
  name: 'pane1',
  components: {
    QuanLyTramThietBi,
    BanDoVNPT,
    GianDo,
    GraphVizControlGianDo,
    QuanLyCapReNhanh,
    TraCuuTBaoCap,
    TraCuuTBaoKetCuoi,
    ModalChonTramThietBiExt,
    ModalTracnghiemExt,
    ModalChonDSKetCuoiExt,
    ModalQLKetCuoiExt,
    ModalQLCapExt,
    ModalPhanChiaDoanCapChenKetCuoi,
    DauNoiCap,
    ModalQLBe,
    ModalDsTuyenCB,
    ModalQLDoanCongVaOngCong,
    ModalDsBe,
    ModalQLCot
  },
  data() {
    return {
      //Tuyen cong be
      dsBeCap: [],
      dsBeChuaCoToaDo: [],
      dsBeToaDo: [],
      dsDoanCong: [],
      dsSearch: [],
      dataSelected: [],
      //
      //Tuyen cot
      dscot: [],
      dsCotSearch: [],
      dsCotChuaCoToaDo: [],
      dsCotToaDo: [],
      showDataSearch: false,
      demCot: 0,
      demhang: 0,
      // data send modal
      dataSend: {},
      //
      dataSelected: {
        ttvt: 0,
        toql: 0,
        timKiem: 1
      },
      //
      nodeGroupLength: 0,
      dsGroupNode: [],
      getDSTimKiemMap: [],
      useGraphviz: false,
      isLoadingTimKiem: false,
      nodeDauNoi: {},
      ds_dv: [],
      tramTb_id: null,
      showtabpane: 0,
      dsLoaiDT: [
        {
          value: 0,
          label: 'Tất cả',
          photo: ''
        },
        {
          value: 1,
          label: 'Kết cuối',
          photo: ''
        },
        {
          value: 2,
          label: 'Cáp',
          photo: ''
        },
        {
          value: 3,
          label: 'Thuê bao',
          photo: ''
        },
        {
          value: 4,
          label: 'Bể',
          photo: ''
        },
        {
          value: 5,
          label: 'Cống',
          photo: ''
        },
        {
          value: 6,
          label: 'Cột',
          photo: ''
        }
      ],
      valueLoaiDT: 0,
      timKiemDT: false,
      timKiemMap: false,
      txtTimKiem: '',
      debouncedtxtTimKiem: '',
      dsDT: [],
      showKetQuaTimKiem: false,
      search_query: '',
      infoMapDefault: {},
      thongtinbando: {},
      capObject: {
        CAP_ID: '',
        MIDSPAN_ID: ''
      },
      doiTuongCap: { cap_id: '' },
      doiTuongKetCuoi: {
        ketcuoi_id: '',
        vitri: -1,
        mat: 2
      },
      bottomactions: false,
      midspanObject: {
        MIDSPAN_ID: null,
        CAP_ID: null,
        KETCUOI_N_ID: null,
        KETCUOI_D_ID: null,
        KYHIEU_KETCUOI_N: null,
        KYHIEU_KETCUOI_D: null,
        DONVI_ID: null,
        DONVI_TD_ID: null,
        TEN_DV: null,
        TEN_DV_TD: null,
        LOPMC_ID: null,
        LOP_MC: null,
        KIEUDC_ID: null,
        KIEUCAP_ID: null,
        KIEU_CAP: null,
        KYHIEU: '',
        CHIEUDAI: null,
        DS_SOI: ''
      }
    }
  },
  computed: {
    ...mapState('cabman/GiamSatMangNgoaiVi', statePropertyName),
    ...mapState('cabman', ['GiamSatMangNgoaiVi']),
    ...mapGetters('cabman/GiamSatMangNgoaiVi', getterName),
    graphID() {
      const uint32 = window.crypto.getRandomValues(new Uint32Array(1))[0]
      return 'graph-giando-oneBSS-' + uint32.toString(16)
    }
  },
  watch: {
    debouncedtxtTimKiem(val) {
      let txtTimKiem = val.trim()
      if (txtTimKiem.length > 3) {
        var typeList = '1,2,3'
        if (this.valueLoaiDT !== 0) {
          typeList = this.valueLoaiDT.toString()
        }
        this.queryDT(txtTimKiem, typeList)
      }
    },
    txtTimKiem: debounce(function (newVal) {
      this.debouncedtxtTimKiem = newVal
    }, 500),
    showtabpane(val) {
      this.set_TabPageIndex(val)
      if (val === 2) {
        this.$refs.mapVNPT && this.$refs.mapVNPT.updateSized()
      }
    },
    ds_cap(val) {
      this.dsGroupNode = []
      this.dsBeCap = []
      this.dsDoanCong = []
      this.dscot = []
      this.reRender()
      var mapVNPT = this.$refs.mapVNPT
      mapVNPT.refreshMap()
    },
    node_focus(val) {
      console.log(val, 'hieu check focus')
      if (val.includes('_CONGBE')) {
        this.$refs.giando.focusByNodeID('BC_' + val)
      }  else {
        this.$refs.giando.focusByNodeID(val)
      }
    },
    async ky_hieu_phan_biet_delete(val) {
      if (val != null && val != '') {
        this.dsGroupNode = this.dsGroupNode.filter((e) => e.kyHieuPhanBiet != val && e.kyHieuPhanBiet != val)
        this.dsBeCap = this.dsBeCap.filter((e) => e.kyHieuPhanBiet != val && e.kyHieuPhanBiet != val)
        this.dsDoanCong = this.dsDoanCong.filter((e) => e.kyHieuPhanBiet != val && e.kyHieuPhanBiet != val)
        this.dscot = this.dscot.filter((e) => e.kyHieuPhanBiet != val && e.kyHieuPhanBiet != val)
        this.reRender()
        this.deleteCapGoc('')
      }
    },
    async gian_do_cap(val) {
      if (val[0]?.kyHieuPhanBiet != null && val[0]?.kyHieuPhanBiet != '') {
        let objIndex = this.dsGroupNode.findIndex((obj) => obj.kyHieuPhanBiet === val[0].kyHieuPhanBiet)
        if (objIndex > -1) {
          return
        }
      }
      val.forEach((e) => {
        e.CAP_ID = e.CAP_ID + '_' + e.kyHieuPhanBiet
        e.DICH_ID = e.DICH_ID + '_' + e.kyHieuPhanBiet
        e.NGUON_ID = e.NGUON_ID + '_' + e.kyHieuPhanBiet
        this.dsGroupNode.push(e)
      })
      try {
        let data = this.dsGroupNode
        this.timKiemMap = false
        var mapVNPT = this.$refs.mapVNPT
        var giando = this.$refs.giando
        var nodes = []
        var polylines = []
        if (!this.isGraphviz()) {
          this.$refs.giando && giando.clear()
          this.$refs.giando && giando.hideToast()
        }
        for (let i = 0; i < data.length; i++) {
          var node_n = new GraphNode()
          var ten_n = data[i].TEN_N + ' - Dung lượng rỗi: ' + data[i].DL_SOI_ROI + '- Dung lượng đã dùng: ' + data[i].DL_DA_DUNG
          var nguon_id = data[i].NGUON_ID
          var lopmc_id = data[i].LOPMC_ID
          var icon_n = data[i].ICON_N
          if (ten_n === null) {
            if (lopmc_id === 1 || lopmc_id === 4) {
              nguon_id = 'THIEU_TRAM_NGUON_' + data[i].CAP_ID
              ten_n = 'Nhấn vào đây để tạo trạm nguồn'
            } else {
              nguon_id = 'THIEU_KETCUOI_NGUON_' + data[i].CAP_ID
              ten_n = 'Nhấn vào đây để tạo kết cuối nguồn'
            }
            icon_n = 'ADD'
          }
          node_n.id = nguon_id
          node_n.id_polyline = 'CAP_' + data[i].CAP_ID
          node_n.label = ten_n
          node_n.style = icon_n
          node_n.symbol = data[i].KYHIEU_N === null ? ten_n : data[i].KYHIEU_N
          node_n.tooltipText = ten_n
          node_n.insideLabel = data[i].CHAR_N
          node_n.setPosition(data[i].TOADO_N)
          node_n.setIcon(icon_n)
          node_n.draggable = true
          let objIndex = nodes.findIndex((obj) => obj.id === node_n.id)
          if (objIndex < 0) nodes.push(node_n)

          var polyline = new GraphPolyline()
          let lat
          let lng
          if (data[i].TOADO_N === null) {
            lat = 0
            lng = 0
          } else {
            lat = parseFloat(data[i].TOADO_N.split(',')[0])
            lng = parseFloat(data[i].TOADO_N.split(',')[1])
          }
          polyline.path.push({ id: nguon_id, lat: lat, lng: lng })
          polyline.iconDiemUonBase64 = this.getDynamicMarkerIcon('iconDiemUon', this.randomNonGreenColor())
          let DAY_TOADO = data[i].DAY_TOADO
          if (DAY_TOADO) {
            let iconUrl = polyline.iconDiemUonBase64
            let arr = DAY_TOADO.split(';')
            for (let j = 0; j < arr.length; j++) {
              let arr2 = arr[j].split(',')
              let lat = parseFloat(arr2[0])
              let lng = parseFloat(arr2[1])
              var id = 'DIEMUON_' + this.create_UUID()
              var node = new GraphNode()
              node.id = id
              node.visible = false
              node.draggable = true
              node.setPosition(arr[j])
              node.isDiemuon = true
              node.iconUrl = iconUrl
              node.id_polyline = 'CAP_' + data[i].CAP_ID
              let objIndex = nodes.findIndex((obj) => obj.id === node.id)
              if (objIndex < 0) nodes.push(node)
              polyline.path.push({ id: id, lat: lat, lng: lng })
            }
          }
          var node_d = new GraphNode()
          var ten_d = data[i].TEN_D
          var dich_id = data[i].DICH_ID
          var icon_d = data[i].ICON_D
          if (ten_d === null) {
            if (lopmc_id === 3 || lopmc_id === 4) {
              dich_id = 'THIEU_TRAM_DICH_' + data[i].CAP_ID
              ten_d = 'Nhấn vào đây để tạo trạm đích'
            } else {
              dich_id = 'THIEU_KETCUOI_DICH_' + data[i].CAP_ID
              ten_d = 'Nhấn vào đây để tạo kết cuối đích'
            }
            icon_d = 'ADD'
          }
          let lat2
          let lng2
          if (data[i].TOADO_D === null) {
            lat2 = 0
            lng2 = 0
          } else {
            lat2 = parseFloat(data[i].TOADO_D.split(',')[0])
            lng2 = parseFloat(data[i].TOADO_D.split(',')[1])
          }
          polyline.path.push({ id: dich_id, lat: lat2, lng: lng2 })
          polyline.id = 'CAP_' + data[i].CAP_ID
          polyline.label = data[i].TEN_CAP + ' - Dung lượng rỗi: ' + data[i].DL_SOI_ROI + '- Dung lượng đã dùng: ' + data[i].DL_DA_DUNG
          polyline.source = nguon_id
          polyline.dest = dich_id
          polyline.style = data[i].STYLE
          objIndex = polylines.findIndex((obj) => obj.id === polyline.id)
          if (objIndex < 0) polylines.push(polyline)
          node_d.id = dich_id
          node_d.id_polyline = 'CAP_' + data[i].CAP_ID
          node_d.label = ten_d
          node_d.style = icon_d
          node_d.symbol = data[i].KYHIEU_D === null ? ten_d : data[i].KYHIEU_D
          node_d.tooltipText = ten_d + ' - Dung lượng rỗi: ' + data[i].DL_SOI_ROI + '- Dung lượng đã dùng: ' + data[i].DL_DA_DUNG
          node_d.insideLabel = data[i].CHAR_D
          node_d.setPosition(data[i].TOADO_D)
          node_d.setIcon(icon_d)
          node_d.draggable = true
          objIndex = nodes.findIndex((obj) => obj.id === node_d.id)
          if (objIndex < 0) nodes.push(node_d)
        }
        mapVNPT.setNodes(nodes.slice(0))
        mapVNPT.setPolylines(polylines.slice(0))
        // ---------------------- Giản đồ region --------------------------
        let node_giando = []
        let edge_giando = []
        for (let index = 0; index < nodes.length; index++) {
          const element = nodes[index]
          if (!element.isDiemuon) {
            let node = this.taoNodeCapGoc(element.id, element.symbol, element.style, element.label, element.insideLabel)
            if (!this.isGraphviz()) {
              giando.addNode(node)
            } else {
              node_giando.push(node)
            }
          }
        }
        var ds_ketcuoi_ctd = []
        for (let index = 0; index < nodes.length; index++) {
          const element = nodes[index]
          if (!element.id.startsWith('KETCUOI')) continue
          if (element.position.lng * element.position.lat !== 0 || element.position.lng === null || element.position.lat === null) continue
          if (element.id.split('_').length === 1) continue
          ds_ketcuoi_ctd.push({
            FULL_ID: element.id,
            KETCUOI_ID: element.id.split('_')[1],
            LOAIKC_ID: 1,
            KYHIEU: element.symbol
          })
        }
        this.set_ds_ketcuoi_ctd(ds_ketcuoi_ctd)
        console.log('🚀 ~ file: index.vue:289 ~ polylines:', polylines)
        let connectors = []
        let check = false
        let enableRouting = true
        polylines.forEach((line) => {
          if (check) {
            if (line.source !== line.dest) {
              if (!connectors.some((e) => e.source === line.dest && e.dest === line.source)) {
                connectors.push(line)
                let idx = line.label.indexOf('Chiều dài:')
                let chieudai = ''
                if (idx > -1) {
                  chieudai = line.label && line.label.substring(idx + 'Chiều dài:'.length).trim()
                } else {
                  chieudai = line.label
                }
                let connector = this.taoConnectorCapGoc(line.id, line.source, line.dest, chieudai, line.style, line.label)
                if (!this.isGraphviz()) {
                  giando.addConnector(connector)
                } else {
                  edge_giando.push(connector)
                }
              } else {
                enableRouting = false
                connectors.push(line)
                let idx = line.label.indexOf('Chiều dài:')
                let chieudai = ''
                if (idx > -1) {
                  chieudai = line.label && line.label.substring(idx + 'Chiều dài:'.length).trim()
                } else {
                  chieudai = line.label
                }
                let connector = this.taoConnectorCapGoc(line.id, line.source, line.dest, chieudai, line.style, line.label)
                if (!this.isGraphviz()) {
                  giando.addConnector(connector)
                } else {
                  edge_giando.push(connector)
                }
              }
            } else {
              enableRouting = false
              connectors.push(line)
              let idx = line.label.indexOf('Chiều dài:')
              let chieudai = ''
              if (idx > -1) {
                chieudai = line.label && line.label.substring(idx + 'Chiều dài:'.length).trim()
              } else {
                chieudai = line.label
              }
              let connector = this.taoConnectorCapGoc(line.id, line.source, line.dest, chieudai, line.style, line.label)
              if (!this.isGraphviz()) {
                giando.addConnector(connector)
              } else {
                edge_giando.push(connector)
              }
            }
          } else {
            let idx = line.label.indexOf('Chiều dài:')
            let chieudai = ''
            if (idx > -1) {
              chieudai = line.label && line.label.substring(idx + 'Chiều dài:'.length).trim()
            } else {
              chieudai = line.label
            }
            let connector = this.taoConnectorCapGoc(line.id, line.source, line.dest, chieudai, line.style, line.label)
            if (!this.isGraphviz()) {
              if (line.source === line.dest) {
                connectors.push(line)
              }
              giando.addConnector(connector)
            } else {
              edge_giando.push(connector)
            }
          }
        })
        if (this.isGraphviz()) {
          this.$refs.GraphVizControlGianDo && (await this.$refs.GraphVizControlGianDo.loadData(this.giando_config, node_giando, edge_giando))
        }
        if (!this.isGraphviz()) {
          if (this.dsBeCap.length > 0) {
            this.sapXepGianDo()
            this.veGianDoCongBe()
            this.veBanDoCongBe()
          }
          if (this.dscot.length > 0) {
            this.veGianDoCot()
            this.veBanDoCot()
          }
        }
        if (!this.isGraphviz()) {
          enableRouting = false
          giando && giando.doLayout(enableRouting, connectors)
        }
        // ---------------------- End Giản đồ region --------------------------
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 292 ~ error', error)
      }
    },
    async gian_do_cong(val) {
      try {
        var giando = this.$refs.giando
        val.dsBeCap.sort(function (a, b) {
          return a.BECAP_ID < b.BECAP_ID ? -1 : 1
        })
        val.dsBeCap.forEach((item) => {
          item.BECAP_ID = item.BECAP_ID + '_' + item.kyHieuPhanBiet
          this.dsBeCap.push(item)
        })
        this.dsBeChuaCoToaDo = this.dsBeCap.filter((item) => item.TUYENKHAC == 0 && (item.TOADO == null || item.TOADO == '0,0'))
        this.dsBeToaDo = this.dsBeCap.filter((item) => item.TOADO != null && item.TOADO != '0,0')
        val.dsDoanCong.forEach((item) => {
          item.BECAP_A_ID = item.BECAP_A_ID + '_' + item.kyHieuPhanBiet
          item.BECAP_B_ID = item.BECAP_B_ID + '_' + item.kyHieuPhanBiet
          item.DOANCONG_ID = item.DOANCONG_ID + '_' + item.kyHieuPhanBiet
          this.dsDoanCong.push(item)
        })
        this.dsSearch = []
        this.dataSelected.timKiem = 1
        if (!this.isGraphviz()) {
          this.$refs.giando && giando.clear()
          this.$refs.giando && giando.hideToast()
          if (this.dsGroupNode.length > 0) {
            try {
              let data = this.dsGroupNode
              this.timKiemMap = false
              var mapVNPT = this.$refs.mapVNPT
              var giando = this.$refs.giando
              var nodes = []
              var polylines = []
              if (!this.isGraphviz()) {
                this.$refs.giando && giando.clear()
                this.$refs.giando && giando.hideToast()
              }
              for (let i = 0; i < data.length; i++) {
                var node_n = new GraphNode()
                var ten_n = data[i].TEN_N + ' - Dung lượng rỗi: ' + data[i].DL_SOI_ROI + '- Dung lượng đã dùng: ' + data[i].DL_DA_DUNG
                var nguon_id = data[i].NGUON_ID
                var lopmc_id = data[i].LOPMC_ID
                var icon_n = data[i].ICON_N
                if (ten_n === null) {
                  if (lopmc_id === 1 || lopmc_id === 4) {
                    nguon_id = 'THIEU_TRAM_NGUON_' + data[i].CAP_ID
                    ten_n = 'Nhấn vào đây để tạo trạm nguồn'
                  } else {
                    nguon_id = 'THIEU_KETCUOI_NGUON_' + data[i].CAP_ID
                    ten_n = 'Nhấn vào đây để tạo kết cuối nguồn'
                  }
                  icon_n = 'ADD'
                }
                node_n.id = nguon_id
                node_n.id_polyline = 'CAP_' + data[i].CAP_ID
                node_n.label = ten_n
                node_n.style = icon_n
                node_n.symbol = data[i].KYHIEU_N === null ? ten_n : data[i].KYHIEU_N
                node_n.tooltipText = ten_n
                node_n.insideLabel = data[i].CHAR_N
                node_n.setPosition(data[i].TOADO_N)
                node_n.setIcon(icon_n)
                node_n.draggable = true
                let objIndex = nodes.findIndex((obj) => obj.id === node_n.id)
                if (objIndex < 0) nodes.push(node_n)

                var polyline = new GraphPolyline()
                let lat
                let lng
                if (data[i].TOADO_N === null) {
                  lat = 0
                  lng = 0
                } else {
                  lat = parseFloat(data[i].TOADO_N.split(',')[0])
                  lng = parseFloat(data[i].TOADO_N.split(',')[1])
                }
                polyline.path.push({ id: nguon_id, lat: lat, lng: lng })
                polyline.iconDiemUonBase64 = this.getDynamicMarkerIcon('iconDiemUon', this.randomNonGreenColor())
                let DAY_TOADO = data[i].DAY_TOADO
                if (DAY_TOADO) {
                  let iconUrl = polyline.iconDiemUonBase64
                  let arr = DAY_TOADO.split(';')
                  for (let j = 0; j < arr.length; j++) {
                    let arr2 = arr[j].split(',')
                    let lat = parseFloat(arr2[0])
                    let lng = parseFloat(arr2[1])
                    var id = 'DIEMUON_' + this.create_UUID()
                    var node = new GraphNode()
                    node.id = id
                    node.visible = false
                    node.draggable = true
                    node.setPosition(arr[j])
                    node.isDiemuon = true
                    node.iconUrl = iconUrl
                    node.id_polyline = 'CAP_' + data[i].CAP_ID
                    let objIndex = nodes.findIndex((obj) => obj.id === node.id)
                    if (objIndex < 0) nodes.push(node)
                    polyline.path.push({ id: id, lat: lat, lng: lng })
                  }
                }
                var node_d = new GraphNode()
                var ten_d = data[i].TEN_D
                var dich_id = data[i].DICH_ID
                var icon_d = data[i].ICON_D
                if (ten_d === null) {
                  if (lopmc_id === 3 || lopmc_id === 4) {
                    dich_id = 'THIEU_TRAM_DICH_' + data[i].CAP_ID
                    ten_d = 'Nhấn vào đây để tạo trạm đích'
                  } else {
                    dich_id = 'THIEU_KETCUOI_DICH_' + data[i].CAP_ID
                    ten_d = 'Nhấn vào đây để tạo kết cuối đích'
                  }
                  icon_d = 'ADD'
                }
                let lat2
                let lng2
                if (data[i].TOADO_D === null) {
                  lat2 = 0
                  lng2 = 0
                } else {
                  lat2 = parseFloat(data[i].TOADO_D.split(',')[0])
                  lng2 = parseFloat(data[i].TOADO_D.split(',')[1])
                }
                polyline.path.push({ id: dich_id, lat: lat2, lng: lng2 })
                polyline.id = 'CAP_' + data[i].CAP_ID
                polyline.label = data[i].TEN_CAP + ' - Dung lượng rỗi: ' + data[i].DL_SOI_ROI + '- Dung lượng đã dùng: ' + data[i].DL_DA_DUNG
                polyline.source = nguon_id
                polyline.dest = dich_id
                polyline.style = data[i].STYLE
                objIndex = polylines.findIndex((obj) => obj.id === polyline.id)
                if (objIndex < 0) polylines.push(polyline)
                node_d.id = dich_id
                node_d.id_polyline = 'CAP_' + data[i].CAP_ID
                node_d.label = ten_d
                node_d.style = icon_d
                node_d.symbol = data[i].KYHIEU_D === null ? ten_d : data[i].KYHIEU_D
                node_d.tooltipText = ten_d + ' - Dung lượng rỗi: ' + data[i].DL_SOI_ROI + '- Dung lượng đã dùng: ' + data[i].DL_DA_DUNG
                node_d.insideLabel = data[i].CHAR_D
                node_d.setPosition(data[i].TOADO_D)
                node_d.setIcon(icon_d)
                node_d.draggable = true
                objIndex = nodes.findIndex((obj) => obj.id === node_d.id)
                if (objIndex < 0) nodes.push(node_d)
              }
              mapVNPT.setNodes(nodes.slice(0))
              mapVNPT.setPolylines(polylines.slice(0))
              // ---------------------- Giản đồ region --------------------------
              let node_giando = []
              let edge_giando = []
              for (let index = 0; index < nodes.length; index++) {
                const element = nodes[index]
                if (!element.isDiemuon) {
                  let node = this.taoNodeCapGoc(element.id, element.symbol, element.style, element.label, element.insideLabel)
                  if (!this.isGraphviz()) {
                    giando.addNode(node)
                  } else {
                    node_giando.push(node)
                  }
                }
              }
              var ds_ketcuoi_ctd = []
              for (let index = 0; index < nodes.length; index++) {
                const element = nodes[index]
                if (!element.id.startsWith('KETCUOI')) continue
                if (element.position.lng * element.position.lat !== 0 || element.position.lng === null || element.position.lat === null) continue
                if (element.id.split('_').length === 1) continue
                ds_ketcuoi_ctd.push({
                  FULL_ID: element.id,
                  KETCUOI_ID: element.id.split('_')[1],
                  LOAIKC_ID: 1,
                  KYHIEU: element.symbol
                })
              }
              this.set_ds_ketcuoi_ctd(ds_ketcuoi_ctd)
              console.log('🚀 ~ file: index.vue:289 ~ polylines:', polylines)
              let connectors = []
              let check = false
              let enableRouting = true
              polylines.forEach((line) => {
                if (check) {
                  if (line.source !== line.dest) {
                    if (!connectors.some((e) => e.source === line.dest && e.dest === line.source)) {
                      connectors.push(line)
                      let idx = line.label.indexOf('Chiều dài:')
                      let chieudai = ''
                      if (idx > -1) {
                        chieudai = line.label && line.label.substring(idx + 'Chiều dài:'.length).trim()
                      } else {
                        chieudai = line.label
                      }
                      let connector = this.taoConnectorCapGoc(line.id, line.source, line.dest, chieudai, line.style, line.label)
                      if (!this.isGraphviz()) {
                        giando.addConnector(connector)
                      } else {
                        edge_giando.push(connector)
                      }
                    } else {
                      enableRouting = false
                      connectors.push(line)
                      let idx = line.label.indexOf('Chiều dài:')
                      let chieudai = ''
                      if (idx > -1) {
                        chieudai = line.label && line.label.substring(idx + 'Chiều dài:'.length).trim()
                      } else {
                        chieudai = line.label
                      }
                      let connector = this.taoConnectorCapGoc(line.id, line.source, line.dest, chieudai, line.style, line.label)
                      if (!this.isGraphviz()) {
                        giando.addConnector(connector)
                      } else {
                        edge_giando.push(connector)
                      }
                    }
                  } else {
                    enableRouting = false
                    connectors.push(line)
                    let idx = line.label.indexOf('Chiều dài:')
                    let chieudai = ''
                    if (idx > -1) {
                      chieudai = line.label && line.label.substring(idx + 'Chiều dài:'.length).trim()
                    } else {
                      chieudai = line.label
                    }
                    let connector = this.taoConnectorCapGoc(line.id, line.source, line.dest, chieudai, line.style, line.label)
                    if (!this.isGraphviz()) {
                      giando.addConnector(connector)
                    } else {
                      edge_giando.push(connector)
                    }
                  }
                } else {
                  let idx = line.label.indexOf('Chiều dài:')
                  let chieudai = ''
                  if (idx > -1) {
                    chieudai = line.label && line.label.substring(idx + 'Chiều dài:'.length).trim()
                  } else {
                    chieudai = line.label
                  }
                  let connector = this.taoConnectorCapGoc(line.id, line.source, line.dest, chieudai, line.style, line.label)
                  if (!this.isGraphviz()) {
                    if (line.source === line.dest) {
                      connectors.push(line)
                    }
                    giando.addConnector(connector)
                  } else {
                    edge_giando.push(connector)
                  }
                }
              })
              if (this.isGraphviz()) {
                this.$refs.GraphVizControlGianDo && (await this.$refs.GraphVizControlGianDo.loadData(this.giando_config, node_giando, edge_giando))
              }
              if (!this.isGraphviz()) {
                enableRouting = false
                giando && giando.doLayout(enableRouting, connectors)
              }
              if (this.dsBeCap.length > 0) {
                this.sapXepGianDo()
                this.veGianDoCongBe()
                this.veBanDoCongBe()
              }
              if (this.dscot.length > 0) {
                this.veGianDoCot()
                this.veBanDoCot()
              }
              // ---------------------- End Giản đồ region --------------------------
            } catch (error) {
              console.log('🚀 ~ file: index.vue ~ line 292 ~ error', error)
            }
          }
          if (this.dsBeCap.length > 0) {
            this.sapXepGianDo()
            this.veGianDoCongBe()
            this.veBanDoCongBe()
          }
          if (this.dscot.length > 0) {
            this.veGianDoCot()
            this.veBanDoCot()
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 292 ~ error', error)
      } finally {
        this.reRender()
      }
    },
    async gian_do_cot(val) {
      try {
        var giando = this.$refs.giando
        this.showDataSearch = false
        if (val.dscot != null && val.dscot.length > 0) {
          val.dscot.sort(function (a, b) {
            return a.COT_ID < b.COT_ID ? -1 : 1
          })
          await val.dscot.forEach((item) => {
            if (item.COTSAU_ID) {
              item.COTSAU_ID = item.COTSAU_ID
            }
            item.COT_ID = item.COT_ID
            this.dscot.push(item)
          })
          this.dscotSearch = [...this.dscot]
          this.dscotChuaCoToaDo = this.dscot.filter((cot) => cot.TOADO == '0,0')
          this.dscotToaDo = this.dscot.filter((cot) => cot.TOADO != '0,0')
          if (!this.isGraphviz()) {
            this.$refs.giando && giando.clear()
            this.$refs.giando && giando.hideToast()
            if (this.dsGroupNode.length > 0) {
              try {
                let data = this.dsGroupNode
                this.timKiemMap = false
                var mapVNPT = this.$refs.mapVNPT
                var giando = this.$refs.giando
                var nodes = []
                var polylines = []
                if (!this.isGraphviz()) {
                  this.$refs.giando && giando.clear()
                  this.$refs.giando && giando.hideToast()
                }
                for (let i = 0; i < data.length; i++) {
                  var node_n = new GraphNode()
                  var ten_n = data[i].TEN_N + ' - Dung lượng rỗi: ' + data[i].DL_SOI_ROI + '- Dung lượng đã dùng: ' + data[i].DL_DA_DUNG
                  var nguon_id = data[i].NGUON_ID
                  var lopmc_id = data[i].LOPMC_ID
                  var icon_n = data[i].ICON_N
                  if (ten_n === null) {
                    if (lopmc_id === 1 || lopmc_id === 4) {
                      nguon_id = 'THIEU_TRAM_NGUON_' + data[i].CAP_ID
                      ten_n = 'Nhấn vào đây để tạo trạm nguồn'
                    } else {
                      nguon_id = 'THIEU_KETCUOI_NGUON_' + data[i].CAP_ID
                      ten_n = 'Nhấn vào đây để tạo kết cuối nguồn'
                    }
                    icon_n = 'ADD'
                  }
                  node_n.id = nguon_id
                  node_n.id_polyline = 'CAP_' + data[i].CAP_ID
                  node_n.label = ten_n
                  node_n.style = icon_n
                  node_n.symbol = data[i].KYHIEU_N === null ? ten_n : data[i].KYHIEU_N
                  node_n.tooltipText = ten_n
                  node_n.insideLabel = data[i].CHAR_N
                  node_n.setPosition(data[i].TOADO_N)
                  node_n.setIcon(icon_n)
                  node_n.draggable = true
                  let objIndex = nodes.findIndex((obj) => obj.id === node_n.id)
                  if (objIndex < 0) nodes.push(node_n)

                  var polyline = new GraphPolyline()
                  let lat
                  let lng
                  if (data[i].TOADO_N === null) {
                    lat = 0
                    lng = 0
                  } else {
                    lat = parseFloat(data[i].TOADO_N.split(',')[0])
                    lng = parseFloat(data[i].TOADO_N.split(',')[1])
                  }
                  polyline.path.push({ id: nguon_id, lat: lat, lng: lng })
                  polyline.iconDiemUonBase64 = this.getDynamicMarkerIcon('iconDiemUon', this.randomNonGreenColor())
                  let DAY_TOADO = data[i].DAY_TOADO
                  if (DAY_TOADO) {
                    let iconUrl = polyline.iconDiemUonBase64
                    let arr = DAY_TOADO.split(';')
                    for (let j = 0; j < arr.length; j++) {
                      let arr2 = arr[j].split(',')
                      let lat = parseFloat(arr2[0])
                      let lng = parseFloat(arr2[1])
                      var id = 'DIEMUON_' + this.create_UUID()
                      var node = new GraphNode()
                      node.id = id
                      node.visible = false
                      node.draggable = true
                      node.setPosition(arr[j])
                      node.isDiemuon = true
                      node.iconUrl = iconUrl
                      node.id_polyline = 'CAP_' + data[i].CAP_ID
                      let objIndex = nodes.findIndex((obj) => obj.id === node.id)
                      if (objIndex < 0) nodes.push(node)
                      polyline.path.push({ id: id, lat: lat, lng: lng })
                    }
                  }
                  var node_d = new GraphNode()
                  var ten_d = data[i].TEN_D
                  var dich_id = data[i].DICH_ID
                  var icon_d = data[i].ICON_D
                  if (ten_d === null) {
                    if (lopmc_id === 3 || lopmc_id === 4) {
                      dich_id = 'THIEU_TRAM_DICH_' + data[i].CAP_ID
                      ten_d = 'Nhấn vào đây để tạo trạm đích'
                    } else {
                      dich_id = 'THIEU_KETCUOI_DICH_' + data[i].CAP_ID
                      ten_d = 'Nhấn vào đây để tạo kết cuối đích'
                    }
                    icon_d = 'ADD'
                  }
                  let lat2
                  let lng2
                  if (data[i].TOADO_D === null) {
                    lat2 = 0
                    lng2 = 0
                  } else {
                    lat2 = parseFloat(data[i].TOADO_D.split(',')[0])
                    lng2 = parseFloat(data[i].TOADO_D.split(',')[1])
                  }
                  polyline.path.push({ id: dich_id, lat: lat2, lng: lng2 })
                  polyline.id = 'CAP_' + data[i].CAP_ID
                  polyline.label = data[i].TEN_CAP + ' - Dung lượng rỗi: ' + data[i].DL_SOI_ROI + '- Dung lượng đã dùng: ' + data[i].DL_DA_DUNG
                  polyline.source = nguon_id
                  polyline.dest = dich_id
                  polyline.style = data[i].STYLE
                  objIndex = polylines.findIndex((obj) => obj.id === polyline.id)
                  if (objIndex < 0) polylines.push(polyline)
                  node_d.id = dich_id
                  node_d.id_polyline = 'CAP_' + data[i].CAP_ID
                  node_d.label = ten_d
                  node_d.style = icon_d
                  node_d.symbol = data[i].KYHIEU_D === null ? ten_d : data[i].KYHIEU_D
                  node_d.tooltipText = ten_d + ' - Dung lượng rỗi: ' + data[i].DL_SOI_ROI + '- Dung lượng đã dùng: ' + data[i].DL_DA_DUNG
                  node_d.insideLabel = data[i].CHAR_D
                  node_d.setPosition(data[i].TOADO_D)
                  node_d.setIcon(icon_d)
                  node_d.draggable = true
                  objIndex = nodes.findIndex((obj) => obj.id === node_d.id)
                  if (objIndex < 0) nodes.push(node_d)
                }
                mapVNPT.setNodes(nodes.slice(0))
                mapVNPT.setPolylines(polylines.slice(0))
                // ---------------------- Giản đồ region --------------------------
                let node_giando = []
                let edge_giando = []
                for (let index = 0; index < nodes.length; index++) {
                  const element = nodes[index]
                  if (!element.isDiemuon) {
                    let node = this.taoNodeCapGoc(element.id, element.symbol, element.style, element.label, element.insideLabel)
                    if (!this.isGraphviz()) {
                      giando.addNode(node)
                    } else {
                      node_giando.push(node)
                    }
                  }
                }
                var ds_ketcuoi_ctd = []
                for (let index = 0; index < nodes.length; index++) {
                  const element = nodes[index]
                  if (!element.id.startsWith('KETCUOI')) continue
                  if (element.position.lng * element.position.lat !== 0 || element.position.lng === null || element.position.lat === null) continue
                  if (element.id.split('_').length === 1) continue
                  ds_ketcuoi_ctd.push({
                    FULL_ID: element.id,
                    KETCUOI_ID: element.id.split('_')[1],
                    LOAIKC_ID: 1,
                    KYHIEU: element.symbol
                  })
                }
                this.set_ds_ketcuoi_ctd(ds_ketcuoi_ctd)
                console.log('🚀 ~ file: index.vue:289 ~ polylines:', polylines)
                let connectors = []
                let check = false
                let enableRouting = true
                polylines.forEach((line) => {
                  if (check) {
                    if (line.source !== line.dest) {
                      if (!connectors.some((e) => e.source === line.dest && e.dest === line.source)) {
                        connectors.push(line)
                        let idx = line.label.indexOf('Chiều dài:')
                        let chieudai = ''
                        if (idx > -1) {
                          chieudai = line.label && line.label.substring(idx + 'Chiều dài:'.length).trim()
                        } else {
                          chieudai = line.label
                        }
                        let connector = this.taoConnectorCapGoc(line.id, line.source, line.dest, chieudai, line.style, line.label)
                        if (!this.isGraphviz()) {
                          giando.addConnector(connector)
                        } else {
                          edge_giando.push(connector)
                        }
                      } else {
                        enableRouting = false
                        connectors.push(line)
                        let idx = line.label.indexOf('Chiều dài:')
                        let chieudai = ''
                        if (idx > -1) {
                          chieudai = line.label && line.label.substring(idx + 'Chiều dài:'.length).trim()
                        } else {
                          chieudai = line.label
                        }
                        let connector = this.taoConnectorCapGoc(line.id, line.source, line.dest, chieudai, line.style, line.label)
                        if (!this.isGraphviz()) {
                          giando.addConnector(connector)
                        } else {
                          edge_giando.push(connector)
                        }
                      }
                    } else {
                      enableRouting = false
                      connectors.push(line)
                      let idx = line.label.indexOf('Chiều dài:')
                      let chieudai = ''
                      if (idx > -1) {
                        chieudai = line.label && line.label.substring(idx + 'Chiều dài:'.length).trim()
                      } else {
                        chieudai = line.label
                      }
                      let connector = this.taoConnectorCapGoc(line.id, line.source, line.dest, chieudai, line.style, line.label)
                      if (!this.isGraphviz()) {
                        giando.addConnector(connector)
                      } else {
                        edge_giando.push(connector)
                      }
                    }
                  } else {
                    let idx = line.label.indexOf('Chiều dài:')
                    let chieudai = ''
                    if (idx > -1) {
                      chieudai = line.label && line.label.substring(idx + 'Chiều dài:'.length).trim()
                    } else {
                      chieudai = line.label
                    }
                    let connector = this.taoConnectorCapGoc(line.id, line.source, line.dest, chieudai, line.style, line.label)
                    if (!this.isGraphviz()) {
                      if (line.source === line.dest) {
                        connectors.push(line)
                      }
                      giando.addConnector(connector)
                    } else {
                      edge_giando.push(connector)
                    }
                  }
                })
                if (this.isGraphviz()) {
                  this.$refs.GraphVizControlGianDo && (await this.$refs.GraphVizControlGianDo.loadData(this.giando_config, node_giando, edge_giando))
                }
                if (!this.isGraphviz()) {
                  enableRouting = false
                  giando && giando.doLayout(enableRouting, connectors)
                }
                if (this.dsBeCap.length > 0) {
                  this.sapXepGianDo()
                  this.veGianDoCongBe()
                  this.veBanDoCongBe()
                }
                // ---------------------- End Giản đồ region --------------------------
              } catch (error) {
                console.log('🚀 ~ file: index.vue ~ line 292 ~ error', error)
              }
            }
            if (this.dsBeCap.length > 0) {
              this.sapXepGianDo()
              this.veGianDoCongBe()
              this.veBanDoCongBe()
            }
            if (this.dscot.length > 0) {
              this.veGianDoCot()
              this.veBanDoCot()
            }
          }
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.reRender()
      }
    }
  },
  methods: {
    ...mapActions('cabman/GiamSatMangNgoaiVi', actionName),
    reRender: async function () {
      try {
        let data = this.dsGroupNode
        this.timKiemMap = false
        var mapVNPT = this.$refs.mapVNPT
        var giando = this.$refs.giando
        var nodes = []
        var polylines = []
        if (!this.isGraphviz()) {
          this.$refs.giando && giando.clear()
          this.$refs.giando && giando.hideToast()
          mapVNPT.refreshMap()
        }
        for (let i = 0; i < data.length; i++) {
          var node_n = new GraphNode()
          var ten_n = data[i].TEN_N + ' - Dung lượng rỗi: ' + data[i].DL_SOI_ROI + '- Dung lượng đã dùng: ' + data[i].DL_DA_DUNG
          var nguon_id = data[i].NGUON_ID
          var lopmc_id = data[i].LOPMC_ID
          var icon_n = data[i].ICON_N
          if (ten_n === null) {
            if (lopmc_id === 1 || lopmc_id === 4) {
              nguon_id = 'THIEU_TRAM_NGUON_' + data[i].CAP_ID
              ten_n = 'Nhấn vào đây để tạo trạm nguồn'
            } else {
              nguon_id = 'THIEU_KETCUOI_NGUON_' + data[i].CAP_ID
              ten_n = 'Nhấn vào đây để tạo kết cuối nguồn'
            }
            icon_n = 'ADD'
          }
          node_n.id = nguon_id
          node_n.id_polyline = 'CAP_' + data[i].CAP_ID
          node_n.label = ten_n
          node_n.style = icon_n
          node_n.symbol = data[i].KYHIEU_N === null ? ten_n : data[i].KYHIEU_N
          node_n.tooltipText = ten_n
          node_n.insideLabel = data[i].CHAR_N
          node_n.setPosition(data[i].TOADO_N)
          node_n.setIcon(icon_n)
          node_n.draggable = true
          let objIndex = nodes.findIndex((obj) => obj.id === node_n.id)
          if (objIndex < 0) nodes.push(node_n)
          var polyline = new GraphPolyline()
          let lat
          let lng
          if (data[i].TOADO_N === null) {
            lat = 0
            lng = 0
          } else {
            lat = parseFloat(data[i].TOADO_N.split(',')[0])
            lng = parseFloat(data[i].TOADO_N.split(',')[1])
          }
          polyline.path.push({ id: nguon_id, lat: lat, lng: lng })
          polyline.iconDiemUonBase64 = this.getDynamicMarkerIcon('iconDiemUon', this.randomNonGreenColor())
          let DAY_TOADO = data[i].DAY_TOADO
          if (DAY_TOADO) {
            let iconUrl = polyline.iconDiemUonBase64
            let arr = DAY_TOADO.split(';')
            for (let j = 0; j < arr.length; j++) {
              let arr2 = arr[j].split(',')
              let lat = parseFloat(arr2[0])
              let lng = parseFloat(arr2[1])
              var id = 'DIEMUON_' + this.create_UUID()
              var node = new GraphNode()
              node.id = id
              node.visible = false
              node.draggable = true
              node.setPosition(arr[j])
              node.isDiemuon = true
              node.iconUrl = iconUrl
              node.id_polyline = 'CAP_' + data[i].CAP_ID
              let objIndex = nodes.findIndex((obj) => obj.id === node.id)
              if (objIndex < 0) nodes.push(node)
              polyline.path.push({ id: id, lat: lat, lng: lng })
            }
          }
          var node_d = new GraphNode()
          var ten_d = data[i].TEN_D
          var dich_id = data[i].DICH_ID
          var icon_d = data[i].ICON_D
          if (ten_d === null) {
            if (lopmc_id === 3 || lopmc_id === 4) {
              dich_id = 'THIEU_TRAM_DICH_' + data[i].CAP_ID
              ten_d = 'Nhấn vào đây để tạo trạm đích'
            } else {
              dich_id = 'THIEU_KETCUOI_DICH_' + data[i].CAP_ID
              ten_d = 'Nhấn vào đây để tạo kết cuối đích'
            }
            icon_d = 'ADD'
          }
          let lat2
          let lng2
          if (data[i].TOADO_D === null) {
            lat2 = 0
            lng2 = 0
          } else {
            lat2 = parseFloat(data[i].TOADO_D.split(',')[0])
            lng2 = parseFloat(data[i].TOADO_D.split(',')[1])
          }
          polyline.path.push({ id: dich_id, lat: lat2, lng: lng2 })
          polyline.id = 'CAP_' + data[i].CAP_ID
          polyline.label = data[i].TEN_CAP + ' - Dung lượng rỗi: ' + data[i].DL_SOI_ROI + '- Dung lượng đã dùng: ' + data[i].DL_DA_DUNG
          polyline.source = nguon_id
          polyline.dest = dich_id
          polyline.style = data[i].STYLE
          objIndex = polylines.findIndex((obj) => obj.id === polyline.id)
          if (objIndex < 0) polylines.push(polyline)

          node_d.id = dich_id
          node_d.id_polyline = 'CAP_' + data[i].CAP_ID
          node_d.label = ten_d
          node_d.style = icon_d
          node_d.symbol = data[i].KYHIEU_D === null ? ten_d : data[i].KYHIEU_D
          node_d.tooltipText = ten_d + ' - Dung lượng rỗi: ' + data[i].DL_SOI_ROI + '- Dung lượng đã dùng: ' + data[i].DL_DA_DUNG
          node_d.insideLabel = data[i].CHAR_D
          node_d.setPosition(data[i].TOADO_D)
          node_d.setIcon(icon_d)
          node_d.draggable = true
          objIndex = nodes.findIndex((obj) => obj.id === node_d.id)
          if (objIndex < 0) nodes.push(node_d)
        }
        mapVNPT.setNodes(nodes.slice(0))
        mapVNPT.setPolylines(polylines.slice(0))
        mapVNPT.setLocationEx(null)
        // ---------------------- Giản đồ region --------------------------
        let node_giando = []
        let edge_giando = []
        for (let index = 0; index < nodes.length; index++) {
          const element = nodes[index]
          if (!element.isDiemuon) {
            let node = this.taoNodeCapGoc(element.id, element.symbol, element.style, element.label, element.insideLabel)
            if (!this.isGraphviz()) {
              giando.addNode(node)
            } else {
              node_giando.push(node)
            }
          }
        }
        console.log('🚀 ~ file: addNode index.vue:263 ~ nodes:', nodes)
        var ds_ketcuoi_ctd = []
        for (let index = 0; index < nodes.length; index++) {
          const element = nodes[index]
          if (!element.id.startsWith('KETCUOI')) continue
          if (element.position.lng * element.position.lat !== 0 || element.position.lng === null || element.position.lat === null) continue
          if (element.id.split('_').length === 1) continue
          ds_ketcuoi_ctd.push({
            FULL_ID: element.id,
            KETCUOI_ID: element.id.split('_')[1],
            LOAIKC_ID: 1,
            KYHIEU: element.symbol
          })
        }
        this.set_ds_ketcuoi_ctd(ds_ketcuoi_ctd)
        console.log('🚀 ~ file: index.vue:289 ~ polylines:', polylines)
        let connectors = []
        let check = false
        let enableRouting = true
        polylines.forEach((line) => {
          if (check) {
            if (line.source !== line.dest) {
              if (!connectors.some((e) => e.source === line.dest && e.dest === line.source)) {
                connectors.push(line)
                let idx = line.label.indexOf('Chiều dài:')
                let chieudai = ''
                if (idx > -1) {
                  chieudai = line.label && line.label.substring(idx + 'Chiều dài:'.length).trim()
                } else {
                  chieudai = line.label
                }
                let connector = this.taoConnectorCapGoc(line.id, line.source, line.dest, chieudai, line.style, line.label)
                if (!this.isGraphviz()) {
                  giando.addConnector(connector)
                } else {
                  edge_giando.push(connector)
                }
              } else {
                enableRouting = false
                connectors.push(line)
                let idx = line.label.indexOf('Chiều dài:')
                let chieudai = ''
                if (idx > -1) {
                  chieudai = line.label && line.label.substring(idx + 'Chiều dài:'.length).trim()
                } else {
                  chieudai = line.label
                }
                let connector = this.taoConnectorCapGoc(line.id, line.source, line.dest, chieudai, line.style, line.label)
                if (!this.isGraphviz()) {
                  giando.addConnector(connector)
                } else {
                  edge_giando.push(connector)
                }
              }
            } else {
              enableRouting = false
              connectors.push(line)
              let idx = line.label.indexOf('Chiều dài:')
              let chieudai = ''
              if (idx > -1) {
                chieudai = line.label && line.label.substring(idx + 'Chiều dài:'.length).trim()
              } else {
                chieudai = line.label
              }
              let connector = this.taoConnectorCapGoc(line.id, line.source, line.dest, chieudai, line.style, line.label)
              if (!this.isGraphviz()) {
                giando.addConnector(connector)
              } else {
                edge_giando.push(connector)
              }
            }
          } else {
            let idx = line.label.indexOf('Chiều dài:')
            let chieudai = ''
            if (idx > -1) {
              chieudai = line.label && line.label.substring(idx + 'Chiều dài:'.length).trim()
            } else {
              chieudai = line.label
            }
            let connector = this.taoConnectorCapGoc(line.id, line.source, line.dest, chieudai, line.style, line.label)
            if (!this.isGraphviz()) {
              if (line.source === line.dest) {
                connectors.push(line)
              }
              giando.addConnector(connector)
            } else {
              edge_giando.push(connector)
            }
          }
        })
        if (!this.isGraphviz()) {
          if (this.dsBeCap.length > 0) {
            this.veGianDoCongBe()
            this.veBanDoCongBe()
            this.sapXepGianDo()
          }
          if (this.dscot.length > 0) {
            this.veGianDoCot()
            this.veBanDoCot()
          }
        }
        if (this.isGraphviz()) {
          this.$refs.GraphVizControlGianDo && (await this.$refs.GraphVizControlGianDo.loadData(this.giando_config, node_giando, edge_giando))
        }
        if (!this.isGraphviz()) {
          enableRouting = false
          giando && giando.doLayout(enableRouting, connectors)
        }

        // ---------------------- End Giản đồ region --------------------------
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 292 ~ error', error)
      }
    },
    veGianDoCot: function () {
      try {
        var giando = this.$refs.giando
        let nodes = []
        let connectors = []
        let index = 0
        this.demCot = 0
        let dsChuaLienKet = this.laydsCotChuaLienKet()
        if (dsChuaLienKet.length > 0) {
          for (let i = 0; i < dsChuaLienKet.length; i++) {
            let node
            node = this.TaoDuLieuNodeCot(dsChuaLienKet[i], true, index, i)
            nodes.push(node)
          }
        }
        index++
        // Phần chuỗi liên kết
        let dsDauChuoiLienKet = this.laydsCot_DauCuaChuoiLienKet()
        for (let i = 0; i < dsDauChuoiLienKet.length; i++) {
          let dt = this.taoChuoiLienKet(dsDauChuoiLienKet[i], nodes, connectors, index)
          nodes = dt[0]
          connectors = dt[1]
          index = dt[2]
        }
        for (let i = 0; i < this.dscot.length; i++) {
          let dt = this.taoChuoiLienKet(this.dscot[i], nodes, connectors, index)
          nodes = dt[0]
          connectors = dt[1]
          index = dt[2]
        }
        nodes.forEach((item) => {
          giando.addNode(item)
        })
        connectors.forEach((item) => {
          giando.addConnector(item)
        })
      } catch (err) {
        console.log(err)
      }
    },
    veBanDoCot: function () {
      let data = this.dscot
      var mapVNPT = this.$refs.mapVNPT
      var nodes = []
      var polylines = []
      for (let i = 0; i < data.length; i++) {
        var node_n = this.taoDuLieuNodeTuyenCot(data[i])
        let objIndex = nodes.findIndex((obj) => obj.id === node_n.id)
        if (objIndex < 0) nodes.push(node_n)
        if (data[i].COTSAU_ID != null) {
          var polyline = new GraphPolyline()
          let lat = parseFloat(data[i].TOADO.split(',')[0])
          let lng = parseFloat(data[i].TOADO.split(',')[1])
          polyline.path.push({ id: data[i].COT_ID, lat: lat, lng: lng })
          let cotD = this.getcotByID(data[i].COTSAU_ID)
          let lat2 = parseFloat(cotD.TOADO.split(',')[0])
          let lng2 = parseFloat(cotD.TOADO.split(',')[1])
          polyline.path.push({ id: cotD.COT_ID, lat: lat2, lng: lng2 })
          polyline.id = 'LINK_' + data[i].COT_ID + '_' + cotD.COT_ID
          polyline.label = data[i].KC_COTSAU == null ? '0 m' : data[i].KC_COTSAU + ' m'
          polyline.source = data[i].COT_ID
          polyline.dest = cotD.COT_ID
          polyline.style = '0-1-4-0'
          objIndex = polylines.findIndex((obj) => obj.id === polyline.id)
          if (objIndex < 0) polylines.push(polyline)

          var node_d = this.taoDuLieuNodeTuyenCot(cotD)

          objIndex = nodes.findIndex((obj) => obj.id === node_d.id)
          if (objIndex < 0) nodes.push(node_d)
        }
      }
      mapVNPT.setNodesCot(nodes.slice(0))
      mapVNPT.setPolylinesCot(polylines.slice(0))
      mapVNPT.setLocationExCot(null)
    },
    taoDuLieuNodeTuyenCot: function (cot) {
      let node = new GraphNode()
      if (cot != null) {
        node.id = cot.COT_ID
        node.label = cot.TEN_COT
        node.style = cot.ICON_STYLE
        node.symbol = cot.SO_HIEU + ' ' + cot.DIACHI
        node.tooltipText = cot.TEN_COT
        node.setPosition(cot.TOADO)
        node.setIcon(cot.ICON_STYLE)
        node.draggable = true
      }
      return node
    },
    taoChuoiLienKet: function (cot, nodes, connectors, index) {
      let nodeN
      let dsCotTruoc = []
      if (nodes.length == 0 || nodes.filter((x) => x.id == cot.COT_ID).length == 0) {
        nodeN = this.TaoDuLieuNodeCot(cot, false, index, 0)
        nodes.push(nodeN)
        let count = 0
        while (cot.COTSAU_ID != null) {
          let cot_sau = this.getcotByID(cot.COTSAU_ID)
          count++
          let nodeD
          nodeD = this.TaoDuLieuNodeCot(cot_sau, true, index, count)
          let link_id = 'LINK_' + nodeN.id + '_' + nodeD.id
          console.log(link_id, 1)
          if (connectors.length == null || connectors.filter((x) => x.id == link_id).length == 0) {
            if (nodes.filter((x) => x.id == cot_sau.COT_ID).length == 0) {
              nodes.push(nodeD)
              connectors.push(this.taoKetNoiCot(link_id, nodeN, nodeD, cot.KC_COTSAU == null ? ' ' : cot.KC_COTSAU.toString() + ' m', nodeN.ports[1].id, nodeD.ports[0].id))
            } else {
              connectors.push(this.taoKetNoiCot(link_id, nodeN, nodeD, cot.KC_COTSAU == null ? ' ' : cot.KC_COTSAU.toString() + ' m', nodeN.ports[2].id, nodeD.ports[3].id))
              break
            }
          }
          cot = cot_sau
          nodeN = nodeD
        }
        index++
      }
      let doituong = []
      doituong.push(nodes)
      doituong.push(connectors)
      doituong.push(index)
      return doituong
    },
    laydsCot_DauCuaChuoiLienKet: function () {
      let list = []
      let dsCotChuaLK = this.dscot.filter((x) => x.COTSAU_ID != null)
      for (let i = 0; i < dsCotChuaLK.length; i++) {
        if (this.dscot.filter((y) => y.COTSAU_ID == dsCotChuaLK[i].COT_ID).length == 0) {
          list.push(dsCotChuaLK[i])
        }
      }
      return list
    },
    TaoDuLieuNodeCot(cot, sau, i, count) {
      let COT_ID = cot.COT_ID
      let icon = cot.ICON_STYLE
      let ten = cot.SO_HIEU + '<br> ' + cot.DIACHI
      let node = this.taoNodeCot(COT_ID, ten, icon)
      const step = 30
      let limit = 5000
      this.demCot += 1
      if (this.demhang == 100) {
        this.demhang = 0
      }

      let indx = this.demhang
      let col = parseInt(this.demCot / 100)
      if (!sau) {
        if (!this.hienThiGianDo) {
          node.offsetX = step
        } // 50;
        else {
          node.offsetX = limit
        }
      } else if (!this.hienThiGianDo) {
        node.offsetX = 100 * indx + 50
      } else {
        node.offsetX = limit - step * indx
      }
      node.offsetY = 100 + 100 * col
      this.demhang += 1
      return node
    },
    laydsCotChuaLienKet: function () {
      let list = []
      let dsCotChuaLK = this.dscot.filter((x) => x.COTSAU_ID == null)
      for (let i = 0; i < dsCotChuaLK.length; i++) {
        if (this.dscot.filter((y) => y.COTSAU_ID == dsCotChuaLK[i].COT_ID).length == 0) {
          list.push(dsCotChuaLK[i])
        }
      }
      return list
    },
    taoKetNoiCot: function (id, nodeSource, nodeTarget, text, portN, portD) {
      let sourceID = nodeSource.id
      let sourcePortID = portN // nodeSource.ports[1].id;
      let targetID = nodeTarget.id
      let targetPortID = portD // nodeTarget.ports[0].id;
      let annotations = [
        {
          height: 20,
          width: 30,
          content: text,
          margin: { top: 20 },
          style: {
            textWrapping: 'NoWrap',
            fill: '#F5F5F5',
            strokeWidth: 1,
            strokeColor: '#E0E0E0'
          }
        }
      ]
      let ketNoi = DiagramLib.taoKetNoiTheoStyle('0-1-4-0', id, sourceID, targetID, sourcePortID, targetPortID, annotations)
      ketNoi.targetDecorator = {
        shape: 'None'
      }
      ketNoi.type = 'Orthogonal'
      return ketNoi
    },
    taoNodeCot: function (nodeID, text, stringStyle) {
      if (text == null) text = 'null'
      text = "<div style='position: absolute; top: -70px; left: -40px; right: -50px'><div class='text-nowrap'" + " style='padding: 1px 1px; border: 1px solid #E0E0E0; background-color: #F5F5F5;" + " display: inline-table; border-radius: 5px;text-align:center'>" + text + '</div></div>'
      let ports = []
      ports.push(DiagramLib.taoPort(nodeID + '_port_1', DiagramLib.PORT_TRAI))
      ports.push(DiagramLib.taoPort(nodeID + '_port_2', DiagramLib.PORT_PHAI))
      ports.push(DiagramLib.taoPort(nodeID + '_port_3', DiagramLib.PORT_TREN))
      ports.push(DiagramLib.taoPort(nodeID + '_port_4', DiagramLib.PORT_DUOI))
      let annotations = []
      annotations.push({ template: text, style: { textWrapping: 'NoWrap' } })
      let node = DiagramLib.taoNodeTheoStyle(stringStyle, nodeID, ports, annotations)
      node.height = 25
      node.width = 25
      return node
    },
    getcotByID: function (Cot_id) {
      return this.dscot.filter((x) => x.COT_ID == Cot_id)[0]
    },
    sapXepGianDo: function () {
      try {
        let len = this.dsDoanCong.length
        let level = 0
        for (let i = 0; i < len; i++) {
          for (let j = i + 1; j < len; j++) {
            if (this.dsDoanCong[j].BECAP_B_ID === this.dsDoanCong[i].BECAP_A_ID && this.dsDoanCong[j].BECAP_A_ID !== this.dsDoanCong[i].BECAP_B_ID) {
              this.dsDoanCong.splice(i, 0, this.dsDoanCong[j])
              this.dsDoanCong.splice(j + 1, 1)
            }
          }
        }
        for (let i = 0; i < len; i++) {
          this.dsDoanCong[i].level = level
          this.dsDoanCong[i].diemDau = 0
          if (i < len - 1) {
            if (this.dsDoanCong[i].BECAP_B_ID !== this.dsDoanCong[i + 1].BECAP_A_ID) {
              level++
              this.dsDoanCong[i].diemDau = 1
            }
          }
        }
      } catch (e) {
        console.log('🚀 ~ file: index.vue:709 ~ e:', e)
      }
    },
    veGianDoCongBe: function () {
      try {
        var giando = this.$refs.giando
        let nodes = []
        let connectors = []
        let slDoanCong = this.dsDoanCong.length
        let index = 0
        let dem = 0
        for (let i = 0; i < slDoanCong; i++) {
          let nodeN
          let nodeD
          let doanCong = this.dsDoanCong[i]
          let beCapA = this.dsBeCap.filter((x) => x.BECAP_ID === doanCong.BECAP_A_ID)
          let beCapB = this.dsBeCap.filter((x) => x.BECAP_ID === doanCong.BECAP_B_ID)

          beCapA[0].level = doanCong.level
          beCapB[0].level = doanCong.level

          nodeN = this.TaoDuLieuNodeCongBe(beCapA[0], false, dem++, 0)
          let checkN = nodes.find((x) => x.id === nodeN.id)
          if (checkN == null) {
            nodes.push(nodeN)
          }
          nodeD = this.TaoDuLieuNodeCongBe(beCapB[0], false, dem++, 0)
          let checkD = nodes.find((x) => x.id === nodeD.id)
          if (checkD == null) {
            nodes.push(nodeD)
          }
          if (beCapA == null || beCapB == null) {
            continue
          }
          let _doanCong = this.taoKetNoiCongBe('DC_' + doanCong.DOANCONG_ID.toString(), nodeN, nodeD, doanCong.TEN_DC, nodeN.ports[1].id, nodeD.ports[0].id)
          // fix 2 doan cong noi 2 be gan nhau
          let f = connectors.filter((x) => x.targetPortID === _doanCong.targetPortID)
          if (f && f.length > 0) {
            let _targetPortID = f[0].targetPortID
            _targetPortID = _targetPortID.substring(0, _targetPortID.length - 1) + '3'
            console.log('🚀 ~ file: index.vue:762 ~ _targetPortID:', _targetPortID)
            _doanCong.targetPortID = _targetPortID
          }
          connectors.push(_doanCong)
        }
        let dsChuaLienKet = this.dsBeCap.filter((x) => x.DOANCONG_ID == null)
        dsChuaLienKet.forEach((item) => {
          nodes.push(this.TaoDuLieuNodeCongBe(item, true, index++, 0))
        })
        nodes.forEach((item) => {
          giando.addNode(item)
        })
        connectors.forEach((item) => {
          giando.addConnector(item)
        })
      } catch (err) {
        console.log('🚀 ~ file: index.vue:821 ~ err:', err)
      }
    },
    veBanDoCongBe: async function () {
      let dsBe = this.dsBeCap
      let dsDoanCong = this.dsDoanCong
      var mapVNPT = this.$refs.mapVNPT
      if (mapVNPT.isMapModified) {
        await mapVNPT.luuToaDoBanDoCongBe()
        mapVNPT.isMapModified = false
      }
      let nodes = []
      let polylines = []
      for (let i = 0; i < dsDoanCong.length; i++) {
        // Tạo node
        let beA = dsBe.find((item) => item.BECAP_ID == dsDoanCong[i].BECAP_A_ID)
        let beB = dsBe.find((item) => item.BECAP_ID == dsDoanCong[i].BECAP_B_ID)
        if (beA != null && beB != null) {
          let node_n = this.taoDuLieuNodeCongBeMap(beA)
          let objIndex = nodes.findIndex((obj) => obj.id === node_n.id)
          if (objIndex < 0) nodes.push(node_n)
          let node_d = this.taoDuLieuNodeCongBeMap(beB)
          objIndex = nodes.findIndex((obj) => obj.id === node_d.id)
          if (objIndex < 0) nodes.push(node_d)
          // tao ket noi
          let polyline = new GraphPolyline()
          let lat = parseFloat(beA.TOADO.split(',')[0])
          let lng = parseFloat(beA.TOADO.split(',')[1])
          polyline.path.push({ id: beA.BECAP_ID, lat: lat, lng: lng })
          polyline.iconDiemUonBase64 = this.getDynamicMarkerIcon('iconDiemUon', this.randomNonGreenColor())
          let DAY_TOADO = dsDoanCong[i].DAY_TOADO
          if (DAY_TOADO) {
            let iconUrl = polyline.iconDiemUonBase64
            let arr = DAY_TOADO.split(';')
            for (let j = 0; j < arr.length; j++) {
              let arr2 = arr[j].split(',')
              let lat = parseFloat(arr2[0])
              let lng = parseFloat(arr2[1])
              let id = 'DIEMUON_' + this.create_UUID()
              let node = new GraphNode()
              node.id = id
              node.visible = false
              node.draggable = true
              node.setPosition(arr[j])
              node.isDiemuon = true
              node.iconUrl = iconUrl
              node.id_polyline = dsDoanCong[i].DOANCONG_ID
              let objIndex = nodes.findIndex((obj) => obj.id == node.id)
              if (objIndex < 0) nodes.push(node)
              polyline.path.push({ id: id, lat: lat, lng: lng })
            }
          }

          let lat2 = parseFloat(beB.TOADO.split(',')[0])
          let lng2 = parseFloat(beB.TOADO.split(',')[1])
          polyline.path.push({ id: beB.BECAP_ID, lat: lat2, lng: lng2 })

          polyline.id = dsDoanCong[i].DOANCONG_ID
          polyline.label = dsDoanCong[i].KYHIEU
          polyline.source = beA.BECAP_ID
          polyline.dest = beB.BECAP_ID
          polyline.style = '0-0-4-0'

          objIndex = polylines.findIndex((obj) => obj.id === polyline.id)
          if (objIndex < 0) polylines.push(polyline)
        }
      }
      mapVNPT.setNodesCongBe(nodes.slice(0))
      mapVNPT.setPolylinesCongBe(polylines.slice(0))
      mapVNPT.setLocationExCongBe(null)
    },
    taoDuLieuNodeCongBeMap: function (data) {
      let node = new GraphNode()
      if (data != null) {
        node.id = data.BECAP_ID
        node.label = data.KYHIEU
        node.style = data.ICON_STYLE === 'BE_TUYNEL' ? 'BE_CHUA_XACDINH' : data.ICON_STYLE
        node.tooltipText = data.KYHIEU
        node.setPosition(data.TOADO)
        node.setIcon(data.ICON_STYLE === 'BE_TUYNEL' ? 'BE_CHUA_XACDINH' : data.ICON_STYLE)
        node.draggable = true
        node.dataBe = data
      }
      return node
    },
    TaoDuLieuNodeCongBe(data, khongLienKet, i, count) {
      try {
        let BECAP_ID = 'BC_' + data.BECAP_ID.toString()
        let icon = data.ICON_STYLE
        let ten = `<b>${data.KYHIEU}</b><br><div>${data.DIACHI}</div>` // data.TEN_BC
        let level = data.level
        let node = this.taoNodeCongBe(BECAP_ID, ten, icon, data)
        if (!khongLienKet) {
          node.offsetX = 100 * (i + 1)
          node.offsetY = 250 + level * 100
        } else {
          node.offsetX = 100 * (i + 1)
          node.offsetY = 100
        }
        node.dataBe = data
        return node
      } catch (e) {
        console.log(e)
        this.$toast.error('lỗi bất thường')
      }
    },
    taoNodeCongBe: function (nodeID, text, stringStyle) {
      if (text == null) text = 'null'
      text = "<div style='position: absolute; top: -75px; left: -35px;'><div class='text-nowrap' " + "style='padding: 7px 10px; border: 1px solid #E0E0E0; background-color: #F5F5F5; display: block; border-radius: 5px;width: 120px; overflow: auto'>" + text + '</div></div>'
      let ports = []
      ports.push(DiagramLib.taoPort(nodeID + '_port_1', DiagramLib.PORT_TRAI))
      ports.push(DiagramLib.taoPort(nodeID + '_port_2', DiagramLib.PORT_PHAI))
      ports.push(DiagramLib.taoPort(nodeID + '_port_3', DiagramLib.PORT_TREN))
      ports.push(DiagramLib.taoPort(nodeID + '_port_4', DiagramLib.PORT_DUOI))
      let annotations = []
      annotations.push({ template: text, style: { textWrapping: 'NoWrap' } })
      let node = DiagramLib.taoNodeTheoStyle(stringStyle, nodeID, ports, annotations)
      return node
    },
    taoKetNoiCongBe: function (id, nodeSource, nodeTarget, text, portN, portD) {
      let sourceID = nodeSource.id
      let sourcePortID = portN
      let targetID = nodeTarget.id
      let targetPortID = portD
      let annotations = [
        {
          height: 20,
          width: 50,
          content: text,
          margin: { top: 20 },
          style: {
            textWrapping: 'NoWrap',
            fill: '#F5F5F5',
            strokeWidth: 1,
            strokeColor: '#E0E0E0'
          }
        }
      ]
      let ketNoi = DiagramLib.taoKetNoiTheoStyle('0-0-4-0', id, sourceID, targetID, sourcePortID, targetPortID, annotations)
      ketNoi.targetDecorator = {
        shape: 'None'
      }
      ketNoi.type = 'Orthogonal'
      return ketNoi
    },
    napGD() {
      EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2')
    },
    zoomIn() {
      this.$refs.giando.zoomIn()
    },
    zoomOut() {
      this.$refs.giando.zoomOut()
    },
    saveDiagram() {
      let dataDiagram = this.$refs.giando.saveDiagram()
      this.setState({ key: 'dataDiagram', value: JSON.parse(dataDiagram) })
    },
    isGraphviz() {
      return this.useGraphviz && this.giando_config !== null
    },
    loadDiagram() {
      let dataDiagram = this.dataDiagram
      this.$refs.giando.setScrollSettings(dataDiagram)
    },
    BanDoZoomIn() {
      this.$refs.mapVNPT.zoomIn()
    },
    BanDoZoomOut() {
      this.$refs.mapVNPT.zoomOut()
    },
    ctrlDuyChuyenDoiTuong() {
      this.$refs.mapVNPT.ctrlDuyChuyenDoiTuong()
    },
    ctrlLuuToaDoBanDo() {
      this.$refs.mapVNPT.ctrlLuuToaDoBanDo()
    },
    ctrlPhanCapMang() {
      this.$refs.mapVNPT.ctrlPhanCapMang()
    },
    ctrlThayDoiNguonDuLieu() {
      this.$refs.mapVNPT.ctrlThayDoiNguonDuLieu()
    },
    compareNumbers(a, b) {
      return parseInt(b.split('_')[2]) - parseInt(a.split('_')[2])
    },
    compareNumbers2(a, b) {
      return b.VITRI - a.VITRI
    },
    compareNumbers3(a, b) {
      return parseInt(b.split('_')[1]) - parseInt(a.split('_')[1])
    },
    Format(fmtstr) {
      var args = Array.prototype.slice.call(arguments, 1)
      return fmtstr.replace(/\{(\d+)\}/g, function (match, index) {
        return args[index]
      })
    },
    onError(error) {
      console.log('🚀 ~ file: index.vue ~ line 330 ~ onError ~ error', error)
    },
    onReload() {},
    async dauNoiBenTrong(node) {
      console.log('🚀 ~ file: index.vue ~ line 329 ~ dauNoiBenTrong ~ node', node)
      try {
        EventBus.$emit('SHOW_PANE_DAUNOI', node)
      } catch (error) {
        this.$root.showLoading(false)
        console.log('🚀 ~ file: index.vue ~ line 335 ~ dauNoiBenTrong ~ error', error)
      }
    },
    interactive() {},
    HidePanelDauNoi() {
      try {
        this.sizeAreaL = 100
        this.nodeDauNoi = {}
        this.$refs.GraphVizControl.setDataGraphviz(null)
      } catch (error) {}
      this.sizeAreaL = 100
      this.nodeDauNoi = {}
    },
    isEmptyObj(Obj) {
      var isEmptyObj = !Object.keys(Obj).length
      return isEmptyObj
    },
    getSizeAreaL() {
      return this.sizeAreaL
    },
    getSizeAreaR() {
      return 100 - this.sizeAreaL
    },
    setThongTinMap: async function () {
      try {
        let mapVNPT = this.$refs.mapVNPT
        await mapVNPT.setinfoMapDefault(this.map)
      } catch (error) {
      } finally {
      }
    },
    setTxtTimKiem(value, searchquery, loai) {
      this.txtTimKiem = value
      this.TIMKIEM_DOITUONG(searchquery, loai)
    },
    async TIMKIEM_DOITUONG_V2(searchquery, loai) {
      console.log('🚀 ~ file: index.vue:403 ~ TIMKIEM_DOITUONG_V2 ~ searchquery', searchquery)
    },
    async TIMKIEM_DOITUONG(searchquery, loai) {
      var query = searchquery + '|NGUOIDUNG_ID=' + this.$root.token.getNguoiDungID()
      console.log('🚀 ~ file: index.vue ~ line 400 ~ TIMKIEM_DOITUONG ~ query', query)
      try {
        var rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/timkiem-doituong-v2', { query: query })
        console.log('🚀 ~ file: index.vue ~ line 405 ~ TIMKIEM_DOITUONG ~ rs', rs)
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
          console.log('🚀 ~ file: index.vue:447 ~ TIMKIEM_DOITUONG ~ dsDOITUONG:', dsDOITUONG)
          if (dsDOITUONG[0].NHOMLC_ID === 0) {
            this.set_nhomlc(0)
            EventBus.$emit('SET_NHOMLC_ID', 0)
          } else {
            this.set_nhomlc(1)
            EventBus.$emit('SET_NHOMLC_ID', 1)
          }
          EventBus.$emit('SET_STATE', {
            dsTTVTS: rs.data.dsTTVTS,
            dsTOKT: rs.data.dsTOKT,
            dsTONGDAI: rs.data.dsTONGDAI,
            dsDOITUONG: rs.data.dsDOITUONG,
            dsDANDO_VEGOC: rs.data.dsDANDO_VEGO
          })
        }
      } catch (error) {
        console.log('TIMKIEM_DOITUONG', error.response)
        this.$alert(error.response.data.message_detail, error.response.data.message, {
          confirmButtonText: 'Đóng',
          type: 'error'
        })
      }
    },
    async onChonMap() {
      this.timKiemMap = false
      this.$refs.mapVNPT.XoaPolyline()
      let s = this.$refs.gridTimKiemMap.getSelectedRecords()
      if (s.length > 0) {
        this.$refs.mapVNPT.veP(s[0].POLYLINE, s[0].DS_TOADO.decodedData)
      }
    },
    async onTimKiem() {
      var typeList = '1,2,3'
      if (this.valueLoaiDT !== 0) {
        typeList = this.valueLoaiDT.toString()
      }
      var txtTimKiem = this.txtTimKiem.trim()
      if (txtTimKiem.length > 3) {
        await this.queryDT(txtTimKiem, typeList)
      }
    },
    txtTimKiem1_click() {
      console.log('🚀 ~ file: index.vue:481 ~ txtTimKiem1_click')
    },
    toggleDT() {
      if (this.tabPageIndex === 0) {
        document.getElementById('myDropdown1').classList.toggle('dropdown')
      }
      if (this.tabPageIndex === 1) {
        document.getElementById('myDropdown2').classList.toggle('dropdown')
      }
    },
    removeDuplicateObjects(array, property) {
      const uniqueIds = []
      const unique = array.filter((element) => {
        const isDuplicate = uniqueIds.includes(element[property])
        if (!isDuplicate) {
          uniqueIds.push(element[property])
          return true
        }
        return false
      })
      return unique
    },
    queryDT: async function (txtTimKiem, typeList) {
      try {
        this.isLoadingTimKiem = true
        var rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/goiNhacTimKiem', { searchQuery: txtTimKiem.toLowerCase(), types: typeList })
        this.dsDT = rs.data.filter((e) => e.IS_EXACTLY === 0)
        this.dsDT = this.removeDuplicateObjects(this.dsDT, 'SEARCH_QUERY')
        console.log('🚀 ~ file: index.vue:582 ~ this.dsDT:', this.dsDT)
        this.toggleDT()
      } catch (error) {
        console.log('🚀 ~ file: index.vue:474 ~ error:', error)
        this.dsDT = []
      } finally {
        this.isLoadingTimKiem = false
      }
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
    hideTimKiem() {
      this.isLoadingTimKiem = false
      this.timKiemDT = false
    },
    hideTimKiemMap() {
      let a = 1
      console.log('🚀 ~ hideTimKiemMap ~ a:', a)
      this.timKiemMap = false
      this.$refs.mapVNPT.XoaPolyline()
    },
    showTimKiem() {
      this.timKiemDT = !this.timKiemDT
    },
    getDSTimKiemMap_CellClick(data) {
      console.log('🚀 ~ getDSTimKiemMap_CellClick ~ data:', data)
      this.$refs.mapVNPT.vePolyline(data.rowData)
    },
    showTimKiemMap(input) {
      console.log('🚀 ~ showTimKiemMap ~ input:', input)
      this.timKiemMap = true
      this.getDSTimKiemMap = []
      for (var i = 0; i < input.data.length; i++) {
        let e = input.data[i]
        this.getDSTimKiemMap.push({
          TUYEN: 'Dài ' + e.TUYEN + ' mét',
          DS_TOADO: e.DS_TOADO,
          DATA: e.DATA,
          COLOR: this.randomNonGreenColor(),
          DIEM_DAU: [e.DIEM_DAU.lng, e.DIEM_DAU.lat],
          DIEM_CUOI: [e.DIEM_CUOI.lng, e.DIEM_CUOI.lat],
          POLYLINE: input.POLYLINE
        })
      }
      console.log('🚀 ~ showTimKiemMap ~ this.getDSTimKiemMap:', this.getDSTimKiemMap)
      this.$refs.mapVNPT.vePolyline(this.getDSTimKiemMap[0])
    },
    closeModaltracnghiem: function () {
      this.modalShowtracnghiem = !this.modalShowtracnghiem
    },
    closeModalChonDSKetCuoi: function () {
      this.modalShowChonDSKetCuoi = false
    },
    closeModalQLKetCuoi: function () {
      this.modalShowQLKetCuoi = !this.modalShowQLKetCuoi
    },
    async onChonKetCuoi(data) {
      let ketcuoi_id_new = data.KETCUOI_ID
      let cap_id = this.cap_id.split('_')[1]
      if (ketcuoi_id_new !== null) {
        let chieudaicap = 100
        let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/chieu-dai-cap?cap_id=' + cap_id)
        if (rs.data && rs.data.length > 0) {
          chieudaicap = parseInt(rs.data[0].CHIEUDAI)
        }
        var cap1Length = Math.round(chieudaicap / 2)
        var cap2Length = Math.round(chieudaicap - cap1Length)
        this.$refs.ModalPhanChiaDoanCapChenKetCuoi.show(chieudaicap).then((result) => {
          if (result.ok) {
            cap1Length = result.data.chieudaicap_truoc
            cap2Length = result.data.chieudaicap_sau
          } else {
          }
          this.$confirm(`Sau khi chèn kết cuối thành công, thông tin đấu nối vào đích của cáp hiện tại sẽ bị gỡ bỏ. Bạn có chắc muốn thực hiện ?`, 'Giản đồ mạng cáp', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            type: 'warning'
          })
            .then(() => {
              this.loading(true)
              this.api_set_chen_ketcuoi({ cap_id: cap_id, ketcuoi_id: ketcuoi_id_new, dodaicap1: cap1Length, dodaicap2: cap2Length })
                .then((rep) => {
                  this.loading(false)
                  this.$root.$toast.success('Chèn kết cuối thành công')
                  EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2')
                })
                .catch((e) => {
                  this.loading(false)
                  this.$root.$toast.error(e.message)
                })
            })
            .catch((e) => {})
        })
      }
    },
    taoConnectorCapGoc: function (id, source_id, dest_id, text, style, tooltip) {
      let sourceID = source_id
      let targetID = dest_id
      let text2 = "<div style='position: absolute; top: -40px; left: -20px;'><div class='text-nowrap' style='padding: 1px 1px; border: 1px solid #E0E0E0; background-color: #F5F5F5; display: inline-table; border-radius: 5px;'>" + tooltip + '</div></div>'
      let text3 = "<div class='text-nowrap' style='opacity: 0.8; font-family: Arial; font-size: 12px; font-weight: bold; padding: 1px 1px; border: 1px solid #F5F5F5; background-color: #EFECE7; display: inline-table; border-radius: 5px;'>" + tooltip + '</div>'
      let text4 = "<div style='opacity: 0.8 padding: 1px 1px; border: 1px solid #E0E0E0; background-color: #F5F5F5; border-radius: 5px;'><div class='text-nowrap' style='font-family: Arial; font-size: 12px; font-weight: bold; display: inline-table'>" + tooltip + '</div></div>'
      let annotations = [
        {
          height: 0,
          width: 30,
          content: text,
          margin: { top: 20 },
          style: {
            textWrapping: 'NoWrap',
            fill: '#F5F5F5',
            strokeWidth: 1,
            strokeColor: '#E0E0E0'
          }
        }
      ]
      let annotations2 = [
        {
          template: text3,
          style: { textWrapping: 'NoWrap' },
          height: 60,
          width: 100,
          offset: 0.5
        }
      ]
      let ketNoi = DiagramLib.taoKetNoiTheoStyle(style, id, sourceID, targetID, null, null, annotations2)
      ketNoi.tooltip = {
        content: tooltip,
        position: 'BottomCenter',
        relativeMode: 'Object',
        animation: {
          open: { effect: 'FadeZoomIn', delay: 0 },
          close: { effect: 'FadeZoomOut', delay: 0 }
        }
      }
      ketNoi.styleCap = ketNoi.style
      if (targetID.startsWith('TRAM_')) {
        ketNoi.targetDecorator = {
          shape: 'None'
        }
      }
      return ketNoi
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
      console.log('🚀 ~ file: index.vue:784 ~ randomNonGreenColor ~ color:', color)
      return color
    },
    randomColor() {
      var color = 'hsl(' + Math.round(Math.random() * 359) + ',100%,50%)'
      console.log('🚀 ~ file: index.vue:801 ~ randomColor ~ color:', color)
      return color
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
    taoNodeCapGoc: function (nodeID, text, stringStyle, tooltip, insideLabel) {
      try {
        console.log('🚀 ~ file: index.vue:608 ~ taoNode nodeID:', nodeID, insideLabel, stringStyle)
        let annotations2 = []
        let text2 = tooltip.replaceAll('<br>', '\n').replaceAll('<strong>', '').replaceAll('</strong>', '').replaceAll('<b>', '').replaceAll('</b>', '')
        // text2 = 'abc'
        annotations2.push({ content: text2, offset: { x: 0.5, y: 1 }, margin: { top: 22 }, style: { textWrapping: 'NoWrap', bold: true, fontfamily: 'Arial', fontsize: 10 } })
        let node = DiagramLib.taoNodeTheoStyle(stringStyle, nodeID, null, annotations2)
        node.height = 64
        node.width = 64
        if (stringStyle === '5') {
          // Thay doi theo yeu cau chuan hoa ma mang ngoai vi
          // if (insideLabel === 'S1') {
          //   let iconFile = 'SPLITTER_S1.jpg'
          //   node.shape = {
          //     type: 'Image',
          //     source: '/static/icons/icon100x100/' + iconFile,
          //     scale: 'None'
          //   }
          // }
          // if (insideLabel === 'S2') {
          //   let iconFile = 'SPLITTER_S2.jpg'
          //   node.shape = {
          //     type: 'Image',
          //     source: '/static/icons/icon100x100/' + iconFile,
          //     scale: 'None'
          //   }
          // }
        }
        if (stringStyle === '6' || stringStyle === 'TU_QUANG_SPLITTER') {
          if (insideLabel === 'S1') {
            // Thay doi theo yeu cau chuan hoa ma mang ngoai vi
            // let iconFile = 'TU_QUANG_SPLITTER_S1.jpg'
            let iconFile = 'T(S1).png'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === 'S2') {
            // Thay doi theo yeu cau chuan hoa ma mang ngoai vi
            // let iconFile = 'TU_QUANG_SPLITTER_S2.jpg'
            let iconFile = 'T(S1).png'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '1') {
            let iconFile = 'TU_QUANG_SPLITTER_1.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '2') {
            let iconFile = 'TU_QUANG_SPLITTER_2.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '3') {
            let iconFile = 'TU_QUANG_SPLITTER_3.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }
          if (insideLabel === '4') {
            let iconFile = 'TU_QUANG_SPLITTER_4.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '5') {
            let iconFile = 'TU_QUANG_SPLITTER_5.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }
          if (insideLabel === '6') {
            let iconFile = 'TU_QUANG_SPLITTER_6.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }
          if (insideLabel === '7') {
            let iconFile = 'TU_QUANG_SPLITTER_7.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }
          if (insideLabel === '8') {
            let iconFile = 'TU_QUANG_SPLITTER_8.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }
          if (insideLabel === '9') {
            let iconFile = 'TU_QUANG_SPLITTER_9.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '9') {
            let iconFile = 'TU_QUANG_SPLITTER_9.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '9') {
            let iconFile = 'TU_QUANG_SPLITTER_9.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '10') {
            let iconFile = 'TU_QUANG_SPLITTER_10.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '11') {
            let iconFile = 'TU_QUANG_SPLITTER_11.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '12') {
            let iconFile = 'TU_QUANG_SPLITTER_12.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '13') {
            let iconFile = 'TU_QUANG_SPLITTER_13.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '14') {
            let iconFile = 'TU_QUANG_SPLITTER_14.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '15') {
            let iconFile = 'TU_QUANG_SPLITTER_15.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }
        }

        node.tooltip = {
          content: tooltip,
          position: 'TopCenter',
          relativeMode: 'Object',
          animation: {
            open: { effect: 'FadeZoomIn', delay: 0 },
            close: { effect: 'FadeZoomOut', delay: 0 }
          }
        }
        console.log('🚀 ~ file: index.vue:653 ~ node', node)
        return node
      } catch (error) {
        console.log('🚀 ~ file: index.vue:838 ~ error:', error)
      }
    },
    getPosition: function (marker) {
      return {
        lat: parseFloat(marker.lat),
        lng: parseFloat(marker.lng)
      }
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
    async suaCap(id) {
      var arr = id.split('_')
      if (arr[2] === '0') {
        var data = {
          cap_id: arr[1]
        }
        console.log('suaCap', data)
        this.$refs.ModalQLCap.suaCap(data).then((result) => {
          if (result.ok) {
            this.$refs.mapVNPT.capNhatCap(result.result)
          } else {
            console.log('Cancel')
          }
        })
      } else {
        // Cap mid-span
        this.midspanObject.MIDSPAN_ID = arr[2]
        this.$bvModal.show('popupQuanLyCapReNhanhUPD')
      }
    },
    async xoaCap(id) {
      console.log('id')
      const vdoituongid = id.split('_')
      if (vdoituongid[2] === '0') {
        this.$confirm(`Bạn có muốn xóa cáp đang chọn không ?`, 'Giản đồ mạng cáp', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'warning'
        })
          .then(() => {
            this.loading(true)
            this.api_set_xoa_cap({ cap_id: vdoituongid[1], xoa_batbuoc: 0 })
              .then((rep) => {
                this.loading(false)
                this.$root.$toast.success('Xóa cáp thành công')
                EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2')
              })
              .catch((error) => {
                this.loading(false).error.response && this.$toast.error(error.message)
                error.response &&
                  this.$alert(error.response.data.message_detail, error.response.data.message, {
                    dangerouslyUseHTMLString: true,
                    confirmButtonText: 'Đóng',
                    type: 'error'
                  })
              })
          })
          .catch((e) => {})
      } else {
        if (vdoituongid[2] !== '0') {
          this.$confirm(`Bạn có muốn xóa cáp mid-span đang chọn không ?`, 'Giản đồ mạng cáp', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          })
            .then(() => {
              this.loading(true)
              this.api_set_xoa_cap_midspan({ cap_id: vdoituongid[1], midspan_id: vdoituongid[2] })
                .then((rep) => {
                  this.loading(false)
                  this.$root.$toast.success('Xóa cáp midspan thành công')
                  EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2')
                })
                .catch((error) => {
                  this.loading(false).error.response && this.$toast.error(error.message)
                  error.response &&
                    this.$alert(error.response.data.message_detail, error.response.data.message, {
                      dangerouslyUseHTMLString: true,
                      confirmButtonText: 'Đóng',
                      type: 'error'
                    })
                })
            })
            .catch((e) => {})
        }
      }
    },
    async chuyenvaoTrongTram(data) {
      console.log('🚀 ~ file: index.vue ~ line 689 ~ chuyenvaoTrongTram ~ data', data)
      try {
        var qt = await this.$confirm('Chức năng dùng để chuyển splitter hiện tại vào trong tủ cáp(kết cuối) hoặc chuyển vào trong trạm thiết bị. Mọi thông tin đấu nối của splitter sẽ bị xóa bỏ để đấu nối lại. Bạn có chắc muốn thực hiện ?', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'warning'
        })
        var bChk = false
        if (qt !== 'confirm') return false
        var tracnghiem = {
          title: 'Chọn đích',
          caption: 'Bạn cần chuyển đối tượng đi đâu ?',
          options: ['Chuyển vào trong kết cuối', 'Chuyển vào trong trạm thiết bị'],
          data: null
        }
        var result = await this.$refs.ModalTracnghiemExt.showWithSetChecked(tracnghiem, 0)
        if (!result.ok) return
        console.log('🚀 ~ file: index.vue ~ line 705 ~ chuyenvaoTrongTram ~ result', result)
        var vketcuoi_id = data.KETCUOI_ID.split('_')[1].toString()
        var rsCaps = await this.DS_CAP_VAO_KC(vketcuoi_id)
        if (result.option === 0) {
          var chonketcuoi = await this.$refs.ModalChonDSKetCuoiExt.show(0)
          console.log('🚀 ~ file: index.vue ~ line 708 ~ chuyenvaoTrongTram ~ chonketcuoi', chonketcuoi)
          if (!chonketcuoi.ok) return
          var vketcuoi_cha_id = chonketcuoi.selectedRow.KETCUOI_ID.toString()
          console.log('🚀 ~ file: index.vue ~ line 711 ~ chuyenvaoTrongTram ~ vketcuoi_cha_id', vketcuoi_cha_id)
          console.log('🚀 ~ file: index.vue ~ line 713 ~ chuyenvaoTrongTram ~ vketcuoi_id', vketcuoi_id)
          if (vketcuoi_cha_id === vketcuoi_id) {
            await this.$alert('Không được phép chọn kết cuối đang cần chuyển.', 'Thông báo', {
              confirmButtonText: 'Đóng',
              type: 'warning'
            })
            return
          }
          bChk = await this.CHUYEN_KETCUOI_CHA(vketcuoi_id, vketcuoi_cha_id)
        }
        if (result.option === 1) {
          var ChonTramThietBi = await this.$refs.ModalChonTramThietBiExt.show(null)
          if (!ChonTramThietBi.ok) return
          var vtramtbi_id = ChonTramThietBi.selectedRow.DONVI_ID.toString()
          var rs = await this.$root.context.get('/web-cabman/ban-do-mang-cap/danh-muc', { loai: 'DS_TOKT_QL', option: vtramtbi_id })
          var vtoql_id = '0'
          if (rs.data.length === 0) {
            await this.$alert('Trạm thiết bị chưa được phân tổ kỹ thuật quản lý. Không được phép chuyển Splitter vào.', 'Thông báo', {
              confirmButtonText: 'Đóng',
              type: 'error'
            })
            return
          }
          if (rs.data.length === 1) {
            vtoql_id = rs.data[0].TOKT_ID.toString()
          } else {
            var dsTo = []
            rs.data.forEach((dr) => {
              var id = dr.TOKT_ID.toString()
              if (!dsTo.includes(id)) {
                dsTo.push({ value: id, text: dr.TOKT })
              }
            })
            if (dsTo.length > 0) {
              try {
                tracnghiem = {
                  title: 'Tổ kỹ thuật quản lý kết cuối',
                  caption: 'Chọn 1 tổ kỹ thuật quản lý kết cuối.',
                  options: dsTo,
                  data: null
                }
                var result3 = await this.$refs.ModalTracnghiemExt.showWithSetChecked(tracnghiem, dsTo[0].value)
                console.log('🚀 ~ file: index.vue ~ line 758 ~ chuyenvaoTrongTram ~ result3', result3)
                vtoql_id = result3.option.toString()
              } catch (error) {
                console.log('🚀 ~ file: index.vue ~ line 761 ~ chuyenvaoTrongTram ~ error', error)
                vtoql_id = dsTo[0].id.toString()
              }
            }
          }
          bChk = await this.CHUYEN_SPLITTER_VAO_TRAMTBI(vketcuoi_id, vtramtbi_id, vtoql_id)
        }

        if (bChk && rsCaps.length > 0) {
          tracnghiem = {
            title: 'Xử lý cáp',
            caption: 'Splitter thì đã chuyển đi rồi, bạn muốn xử lý thế nào đối với cáp cũ của splitter ?',
            options: ['Xóa cáp cũ', 'Không xóa nhưng ẩn cáp khỏi giản đồ', 'Không xóa và vẫn hiện cáp trên giản đồ'],
            data: null
          }
          var result2 = await this.$refs.ModalTracnghiemExt.showWithSetChecked(tracnghiem, 0)
          if (!result2.ok) return
          if (result2.option === 0) {
            rsCaps.forEach(async (dr) => {
              try {
                await this.api_set_xoa_cap({ cap_id: dr.CAP_ID, xoa_batbuoc: 1 })
              } catch (error) {
                console.log('🚀 ~ file: index.vue ~ line 742 ~ chuyenvaoTrongTram ~ error', error)
              }
            })
            await this.$alert('Xóa cáp cũ thành công !', 'Thông báo', {
              confirmButtonText: 'Đóng',
              type: 'success'
            })
          }
          if (result2.option === 1) {
            var tmp = rsCaps.map((x) => x.CAP_ID)
            console.log('🚀 ~ file: index.vue ~ line 752 ~ chuyenvaoTrongTram ~ tmp', tmp)
            var dsCap_ID = tmp.join()
            console.log('🚀 ~ file: index.vue ~ line 752 ~ chuyenvaoTrongTram ~ dsCap_ID', dsCap_ID)
            data = {
              P_DS_CAP: dsCap_ID
            }
            var rss = await this.$root.context.post('/web-cabman/ban-do-mang-cap/sp_an_cap_cu', data)
            console.log('🚀 ~ file: index.vue ~ line 764 ~ chuyenvaoTrongTram ~ rss', rss)
            if (rss.data.length > 0) {
              await this.$alert('Ẩn cáp cũ thành công !', 'Thông báo', {
                confirmButtonText: 'Đóng',
                type: 'success'
              })
            }
          }
        }

        if (result.option === 0) {
          // TIMKIEM_DOITUONG
          EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2')
          return
        }
        if (result.option === 1) {
          EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2')
        }
      } catch (error) {}
    },
    async CHUYEN_SPLITTER_VAO_TRAMTBI(vketcuoi_id, vtramtbi_id, vtoql_id) {
      try {
        this.loading = true
        var data = {
          VKETCUOI_ID: vketcuoi_id,
          VTRAMTBI_ID: vtramtbi_id,
          VTOQL_ID: vtoql_id,
          VNGUOI_CN: this.$root.token.getUserName()
        }
        var rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/sp_chuyen_splitter_vao_tramtbi', data)
        this.loading = false
        console.log('🚀 ~ file: index.vue ~ line 839 ~ CHUYEN_SPLITTER_VAO_TRAMTBI ~ rs', rs)
        await this.$alert('Chuyển Splitter thành công.', 'Thông báo', {
          confirmButtonText: 'Đóng',
          type: 'success'
        })
        this.loadData()
        return true
      } catch (error) {
        this.loading = false
        if (error.response && error.response.status === 400) {
          await this.$alert(error.response.data.message_detail, 'Thông báo', {
            confirmButtonText: 'Đóng',
            type: 'error'
          })
        }
        return false
      }
    },
    async DS_CAP_VAO_KC(ketcuoi_id) {
      try {
        var rs = await this.$root.context.get('/web-cabman/ban-do-mang-cap/danh-muc', { loai: 'DS_CAP_VAO_KC', option: ketcuoi_id })
        if (rs.data.length > 0) {
          return rs.data
        } else return []
      } catch (error) {
        return []
      }
    },
    async CHUYEN_KETCUOI_CHA(vketcuoi_id, vketcuoi_cha_id) {
      try {
        this.loading = true
        var data = {
          VKETCUOI_ID: vketcuoi_id,
          VKETCUOI_CHA_ID: vketcuoi_cha_id,
          VNGUOI_CN: this.$root.token.getUserName()
        }
        var rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/sp_chuyen_ketcuoi_cha', data)
        this.loading = false
        console.log('🚀 ~ file: GraphVizControl1.vue ~ line 437 ~ CHUYEN_KETCUOI_CHA ~ rs', rs)
        await this.$alert('Chuyển Splitter thành công.', 'Thông báo', {
          confirmButtonText: 'Đóng',
          type: 'success'
        })
        this.loadData()
        return true
      } catch (error) {
        this.loading = false
        if (error.response && error.response.status === 400) {
          await this.$alert(error.response.data.message_detail, 'Thông báo', {
            confirmButtonText: 'Đóng',
            type: 'error'
          })
        }
        return false
      } finally {
      }
    },
    async chenketcuoi(id) {
      const mouseLatLng = JSON.parse(localStorage.getItem('mouseLatLng'))
      if (id) {
        this.cap_id = id
        var tracnghiem = {
          title: 'Chèn kết cuối',
          caption: 'Bước 1: Chọn nguồn kết cuối muốn chèn',
          options: ['Kết cuối chưa nối cáp', 'Tạo kết cuối mới'],
          data: id
        }
        this.$refs.ModalTracnghiemExt.showWithSetChecked(tracnghiem, 0).then((result) => {
          if (result.ok) {
            console.log('Chèn kết cuối', result)
            if (result.option === 0) {
              this.$refs.ModalChonDSKetCuoiExt.show(1).then((result) => {
                if (result.ok) {
                  console.log('Chọn danh sách kết cuối chưa đấu cáp', result)
                  this.onChonKetCuoi(result.selectedRow)
                } else {
                  console.log('Cancel')
                }
              })
            } else {
              var dataQLKetCuoi = {}
              dataQLKetCuoi.cmd = 'TaoKetCuoi'
              dataQLKetCuoi.data = {
                capgoc_id: this.capgoc.CAP_ID,
                donvi_id: this.toql.DONVI_ID,
                phanloaikc_id: this.phanloaikc_id,
                // kinhdo: 0,
                // vido: 0,
                // lấy dữ liệu kinh độ theo vị trí đã chọn
                kinhdo: mouseLatLng ? mouseLatLng.lng : 0,
                vido: mouseLatLng ? mouseLatLng.lat : 0,
                capgoc: this.capgoc,
                ketcuoi_id: null,
                ketcuoi_truoc_id: null
              }
              this.$refs.ModalQLKetCuoiExt.show(dataQLKetCuoi).then((result) => {
                if (result.ok) {
                  console.log('Tạo kết cuối mới', result.data.KETCUOI_ID)
                  if (result.data.KETCUOI_ID !== null) {
                    this.onChonKetCuoi({ KETCUOI_ID: result.data.KETCUOI_ID })
                  }
                } else {
                }
              })
            }
          } else {
            console.log('Cancel')
          }
        })
      }
    },
    async thueBaoThuocCap(id) {
      this.doiTuongCap.cap_id = id.split('_')[1]
      this.$bvModal.show('popupTraCuuTBaoCap')
    },
    async thueBaoThuocKetCuoi(id) {
      this.doiTuongKetCuoi.ketcuoi_id = id.split('_')[1]
      this.$bvModal.show('popupTraCuuTBaoKetCuoi')
    },
    QuanLyTramThietBiIsOk(data) {
      EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2')
    },
    chuyennhanhcap(id) {
      if (id) {
        this.cap_id = id
        var tracnghiem = {
          title: 'Chuyển cáp',
          caption: 'Chọn kết cuối nguồn hoặc trạm thiết bị nguồn cần chuyển cáp tới',
          options: ['Trạm thiết bị nguồn', 'Kết cuối nguồn'],
          data: id
        }
        var doituong_nguon_id = -1
        var loai_nguon = null
        this.$refs.ModalTracnghiemExt.showWithSetChecked(tracnghiem, 0).then((result) => {
          if (result.ok) {
            if (result.option === 0) {
              loai_nguon = 'TRAM'
              this.$refs.ModalChonTramThietBiExt.show(null).then((result) => {
                if (result.ok) {
                  doituong_nguon_id = result.selectedRow.DONVI_ID
                  if (doituong_nguon_id !== -1 && loai_nguon !== null) {
                    this.$confirm(`Sau khi chuyển cáp thành công, thông tin đấu nối vào nguồn của cáp hiện tại sẽ bị gỡ bỏ. Bạn có chắc muốn thực hiện ?`, 'Giản đồ mạng cáp', {
                      confirmButtonText: 'Có',
                      cancelButtonText: 'Không'
                    })
                      .then(() => {
                        this.CHUYEN_CAP(this.cap_id, doituong_nguon_id, loai_nguon)
                      })
                      .catch((e) => {})
                  }
                } else {
                  console.log('Cancel')
                }
              })
            } else {
              loai_nguon = 'KETCUOI'
              console.log('🚀 ~ file: index.vue:1353 ~ this.$refs.ModalChonDSKetCuoiExt.show2 ~ this.donvi:', this.donvi)
              console.log('🚀 ~ file: index.vue:1353 ~ this.$refs.ModalChonDSKetCuoiExt.show2 ~ this.donvi:', this.toql)
              this.$refs.ModalChonDSKetCuoiExt.show(0).then((result) => {
                // this.$refs.ModalChonDSKetCuoiExt.show2(0, this.ttvt_id, this.toql.DONVI_ID, this.tramvt_id).then(result => {
                if (result.ok) {
                  console.log('Chọn danh sách kết cuối đã đấu cáp', result)
                  doituong_nguon_id = result.selectedRow.KETCUOI_ID
                  if (doituong_nguon_id !== -1 && loai_nguon !== null) {
                    this.$confirm(`Sau khi chuyển cáp thành công, thông tin đấu nối vào nguồn của cáp hiện tại sẽ bị gỡ bỏ. Bạn có chắc muốn thực hiện ?`, 'Giản đồ mạng cáp', {
                      confirmButtonText: 'Có',
                      cancelButtonText: 'Không'
                    })
                      .then(() => {
                        this.CHUYEN_CAP(this.cap_id, doituong_nguon_id, loai_nguon)
                      })
                      .catch((e) => {})
                  }
                } else {
                  console.log('Cancel')
                }
              })
            }
          } else {
            console.log('Cancel')
          }
        })
      }
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
        diagramInstance.selectedItems.connectors = []
        diagramInstance.selectedItems.nodes = []
        this.setState({ key: 'vaction', value: 'None' })
        this.setState({ key: 'vnguon_id', value: null })
        this.setState({ key: 'vdich_id', value: null })
      } catch (error) {}
    },
    async CHUYEN_CAP(cap_id, doituong_nguon_id, loai_nguon) {
      console.log(cap_id, doituong_nguon_id, loai_nguon)
      try {
        var doituong = cap_id.split('_')
        var rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/sp_chuyen_cap', { P_CAP_ID: doituong[1], P_DOITUONG_NGUON_ID: doituong_nguon_id, P_LOAI_NGUON: loai_nguon })
        console.log('CHUYEN_CAP', rs)
        await this.TIMKIEM_DOITUONG('CAP|' + doituong[1] + '|TIM_THEO_ID', 2)
      } catch (error) {
        this.$root.$toast.error(error.message)
      }
    },
    async TramThietBi(id) {
      console.log('🚀 ~ file: index.vue:1170 ~ TramThietBi ~ id', id)
      this.tramTb_id = id
      this.$bvModal.show('popupTramThietBiGianDo')
    },
    async daochieuCap(id) {
      const vdoituongid = id.split('_')
      this.$confirm(`Sau khi đảo chiều cáp thành công, thông tin đấu nối vào 2 đầu của cáp hiện tại sẽ bị gỡ bỏ. Bạn có chắc muốn thực hiện ?`, 'Giản đồ mạng cáp', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'warning'
      })
        .then(() => {
          this.loading(true)
          this.api_set_daochieu_cap({ cap_id: vdoituongid[1] })
            .then((rep) => {
              this.loading(false)
              this.$root.$toast.success('Đảo chiều cáp thành công')
              EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2')
            })
            .catch((error) => {
              this.loading(false).error.response && this.$toast.error(error.message)
              error.response &&
                this.$alert(error.response.data.message_detail, error.response.data.message, {
                  dangerouslyUseHTMLString: true,
                  confirmButtonText: 'Đóng',
                  type: 'error'
                })
            })
        })
        .catch((e) => {})
    },
    async dauNoiCap(id) {
      var doituong = id.split('_')
      this.capObject.CAP_ID = doituong[1]
      this.capObject.MIDSPAN_ID = doituong[2]
      this.$bvModal.show('popupDauNoiCap')
    },
    async suaTram(id) {
      this.tramTb_id = parseFloat(id.split('_')[1])
      this.$bvModal.show('popupTramThietBiGianDo')
    },
    async xemKetcuoi(id) {
      var tmp = id.split('_')
      if (tmp.length !== 2) return
      if (tmp[1] === '') return
      var dataQLKetCuoi = {}
      dataQLKetCuoi.cmd = 'ViewKetcuoi'
      dataQLKetCuoi.data = {
        ketcuoi_id: tmp[1],
        phanloaikc_id: this.phanloaikc_id
      }
      this.$refs.ModalQLKetCuoiExt.show(dataQLKetCuoi).then((result) => {
        if (result.ok) {
        } else {
        }
      })
    },
    async suaKetcuoi(id) {
      var dataQLKetCuoi = {}
      dataQLKetCuoi.cmd = 'SuaKetcuoi'
      dataQLKetCuoi.data = {
        ketcuoi_id: id.split('_')[1],
        phanloaikc_id: this.phanloaikc_id
      }
      this.$refs.ModalQLKetCuoiExt.show(dataQLKetCuoi).then((result) => {
        if (result.ok) {
          this.$refs.mapVNPT.capNhatKetCuoi(result.result)
        } else {
        }
      })
    },
    async luuToaDoBanDo(nodes, polylines) {
      console.log('🚀 ~ file: index.vue:1672 ~ luuToaDoBanDo ~ nodes, polylines:', nodes, polylines)
      if (polylines.length === 0 && nodes.length === 0) return
      this.$confirm(`Bạn có muốn lưu tọa độ cáp và kết cuối ?`, 'Giản đồ mạng cáp', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'warning'
      })
        .then(() => {
          try {
            this.loading(true)
            let ck = false
            if (nodes && nodes.length > 0) {
              const myData = JSON.stringify(nodes)
              this.api_set_toado_doituong(myData)
                .then((rep) => {
                  ck = true
                  EventBus.$emit('NAP_GIANDO_V2', { focused: { lng: nodes[0].lng, lat: nodes[0].lat } })
                })
                .catch((e) => {
                  this.$root.$toast.error(e.message)
                })
            }
            if (polylines && polylines.length > 0) {
              var json = []
              let focused = null
              for (let index = 0; index < polylines.length; index++) {
                const element = polylines[index]
                var day_toado = element.day_toado
                var data = []
                for (let index = 0; index < day_toado.length; index++) {
                  const e = day_toado[index]
                  data.push(e.lat + ',' + e.lng)
                  if (index == 0) {
                    focused = { lng: e.lng, lat: e.lat }
                  }
                }
                if (data.length > 0) {
                  json.push({ id: element.id, json: data.join(';') })
                } else {
                  json.push({ id: element.id, json: null })
                }
              }
              if (json.length > 0) {
                let data = JSON.stringify(json)
                this.api_set_toado_cap(data)
                  .then((rep) => {
                    ck = true
                    EventBus.$emit('NAP_GIANDO_V2', [{ focused: focused }])
                  })
                  .catch((e) => {
                    this.$root.$toast.error(e.message)
                  })
              }
            }
            if (!ck) EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2')
            this.loading(false)
          } catch (e) {
            this.loading(false)
            this.$root.$toast.error(e.message)
          }
        })
        .catch(() => {})
    },
    isDuplicateKey(id, arr) {
      let isDupl = false
      for (let i = 0; i < arr.length; i += 1) {
        const obj = arr[i]
        if (obj.info.id === id) {
          isDupl = true
          break
        }
      }
      return isDupl
    },

    getUniqueId(prefix) {
      var d = new Date().getTime()
      d += parseInt(Math.random() * 100).toString()
      if (undefined === prefix) {
        prefix = 'uid-'
      }
      d = prefix + d
      return d
    },
    window_resize() {},
    async thuocTinhBe(id) {
      this.dataSend.beCapId = id
      let rs = await this.$root.context.get(
          "/web-cabman/BanDoTuyenCong/chi-tiet-be-cap?beCapId=" +
          id
        );      
      this.dataSend.tuyenCongId = rs.data.TUYENCB_ID
      this.dataSend.dvql = {
        id: this.toql.DONVI_ID,
        name: this.toql.TEN_DV
      }
      this.$bvModal.show('popupQLBe')
    },
    xacnhanBe: function (obj) {
      this.dataSend.beCapId = null
      if (obj != null) {
        this.beIdThaoTac = obj.beId
        this.getGianDoCong()
        if (this.loaiThaoTac == 3) {
          this.chenBeTaoMoi(obj)
        }
        if (obj.type == 'MENU_ITEM_THUOC_TINH') {
          this.dataSend.beCapId = obj.beId
          this.$bvModal.show('popupQLBe')
        }
      }
    },
    async chuyenTuyenCongBe(id) {
      this.dataSend.beCapId = id
      this.$bvModal.show('popupDsTuyenCB')
    },
    thayDoiTuyen: function (data) {
      this.tuyenCongIdDoi = data.TUYENCB_ID
      this.chuyenTuyenBeCap()
    },
    chuyenTuyenBeCap: async function () {
      try {
        var rs = await this.$root.context.post('/web-cabman/BanDoTuyenCong/chuyen-tuyen-be-cap/', {
          beCapId: this.beIdThaoTac,
          tuyenCongBeId: this.tuyenCongIdDoi
        })
        if (rs.error_code === 'BSS-00000000') {
          this.$toast.success('chuyển tuyến bể cáp thành công')
          if (!this.showBeChuaTD) {
            this.getGianDoCong()
          }
        } else {
          this.$toast.error('chuyển tuyến bể cáp thất bại')
        }
      } catch (error) {
      } finally {
      }
    },
    async thuocTinhCapCongBe(id) {
      this.dataSend.doanCongId = id
      this.dataSend.dvql = 99999
      this.$bvModal.show('popupQLDoanCongVaOngCong')
    },
    async themBe(obj) {
      this.dataSend.doanCongId = obj.doanCongId
      this.dataSend.loaiThaoTac = 3
      let tracnghiem = {
        title: 'Chèn bể cáp',
        caption: 'Bước 1: Chọn bể muốn chèn',
        options: ['Chọn bể có sẵn', 'Tạo bể mới'],
        data: obj.dataBe
      }
      this.$refs.ModalTracnghiemExt.show(tracnghiem).then((result) => {
        if (result.ok) {
          this.loaiThaoTac = 3
          if (result.option === 0) {
            this.$bvModal.show('popupDsBe')
          } else {
            this.dataSelected.beCapId = null
            this.$bvModal.show('popupQLBe')
          }
        } else {
          console.log('Cancel')
        }
      })
    },
    xacNhanDsBe: function (data) {
      switch (this.loaiThaoTac) {
        case 1:
          this.thayDoiBe(data)
          break
        case 2:
          this.taoCongDenBeKhactuyen(data)
          break
        case 3:
          this.chenBeCoSan(data)
          break
      }
    },
    thayDoiBe: function (data) {
      this.beIdThaoTac = data.BECAP_ID
      if (this.loaiBeDoi == 1) {
        this.doiBeCapA()
      }

      if (this.loaiBeDoi == 2) {
        this.doiBeCapB()
      }
    },
    chenBeCoSan: function (data) {
      this.dataSend.beCapId = data.BECAP_ID
      this.dataSend.chieuDai = this.dsDoanCong.find((item) => item.DOANCONG_ID == this.doanCongId).CHIEUDAI
      this.dataSend.doanCongId = this.doanCongId
      this.$bvModal.show('popupPhanChiaDoDai')
    },
    async thayDoiBeAB(obj) {
      if (obj.type == 'MENU_ITEM_DOI_BE_A') {
        this.loaiBeDoi = 1
        this.loaiThaoTac = 1
      }

      if (obj.type == 'MENU_ITEM_DOI_BE_B') {
        this.loaiBeDoi = 2
        this.loaiThaoTac = 1
      }
      this.$bvModal.show('popupDsBe')
    },
    async thuocTinhCot(obj) {
      this.dataSend.cot_id = obj.cotId
      this.dataSend.readOnly = false
      console.log(this.dataSend)
      this.$bvModal.show('popupQLCot')
    }
  },
  async mounted() {
    try {
      this.setThongTinMap()
      EventBus.$on('call_chuyenvaoTrongTram', this.chuyenvaoTrongTram)
      this.$refs.mapVNPT.updateSized()
    } catch (error) {
      console.log('error', error)
    }
  },
  created() {
    EventBus.$on('SUA_KETCUOI', this.suaKetcuoi)
    EventBus.$on('THUOC_TINH_BE', this.thuocTinhBe)
    EventBus.$on('CHUYEN_TUYEN_CONG_BE', this.chuyenTuyenCongBe)
    EventBus.$on('TIMKIEM_DOITUONG_V2', this.TIMKIEM_DOITUONG_V2)
    EventBus.$on('NAP_GIANDO_V2', this.reRender)
  },
  beforeDestroy() {
    EventBus.$off('TIMKIEM_DOITUONG_V2', this.TIMKIEM_DOITUONG_V2)
    EventBus.$off('call_chuyenvaoTrongTram', this.chuyenvaoTrongTram)
  }
}
</script>

<style scoped>
#B1 {
  height: calc(100vh - 255px - 2 * 12px);
}

#B2 {
  height: calc(100vh - 255px - 2 * 12px);
}

#B3 {
  height: calc(100vh - 255px - 2 * 12px);
}

.box-search-map {
  position: absolute;
  max-width: 200px;
  max-height: 600px;
  width: 100%;
  background: #ffffff;
  border: 1px solid #e0e0e0;
  left: 0px;
  margin: auto;
  padding: 15px 2px 2px 5px;
  top: 0px;
  box-shadow: 0px 4px 4px rgba(177, 177, 177, 0.15);
  border-radius: 4px;
}

.popup-close-button {
  position: absolute;
  right: 0;
  top: 0;
  border: 0;
  border-radius: 0 3px 0 0;
  cursor: pointer;
  background-color: transparent;
}
</style>
