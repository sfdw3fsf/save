<template src="./index.template.html"></template>
<script>

import Vue from 'vue'
import EventBus from '@/utils/eventBus'
import '@/utils/gmaps'
import BinaryReader from '@/utils/BinaryReader'
import BinaryWriter from '@/utils/BinaryWriter'
import Gmap from './components/BanDo.vue'
import BanDoVNPT from './components/BanDoVNPT.vue'
import Diagram from './components/GianDo.vue'
import Point from './class/Point'
import NodeMapVNPT from './class/NodeMapVNPT'
import GraphPolyline from '../BanDoMangCap/structures/GraphPolyline'
import { mapActions, mapState, mapGetters } from 'vuex'
import { getterName, actionName, statePropertyName } from './store'
import { Page, Filter, Resize, Freeze, GridPlugin, Group } from '@syncfusion/ej2-vue-grids'
import QLCap from '../QLCap/QLCap'
import XemTruocKhiIn from "../../QLSC/CauTrucMangBangThongRong/XemTruocKhiIn"

import { L10n } from '@syncfusion/ej2-base'
L10n.load({
  'vi-VN': {
    grid: {
      EmptyRecord: 'Không có bản ghi nào để hiển thị'
    }
  }
})
Vue.use(GridPlugin)
export default {
  components: { Gmap, Diagram, QLCap, XemTruocKhiIn, BanDoVNPT },
  // components: { Diagram, QLCap, XemTruocKhiIn, BanDoVNPT },
  name: 'QuanLyMangTruyenDan',
  provide: {
    grid: [Page, Filter, Resize, Freeze, Group]
  },
  computed: {
    ...mapState('cabman/QuanLyMangTruyenDan', statePropertyName),
    ...mapState('cabman', ['QuanLyMangTruyenDan']),
    ...mapGetters('cabman/QuanLyMangTruyenDan', getterName),
    getMap () { return this.map },
  },
  data () {
    return {
      //banner diagram when selected
      isRenderBaDo: false,
      chuthich1:{
        lblChuThich1:'',
        visible:false,
      },
      chuthich2:{
        lblChuThich2:'',
        visible: false,
      },
      ditu1:{
        lblDiTu1:'',
        visible: false,
      },
      ditu2:{
        lblDiTu2:'',
        visible: false,
      },
      diden1:{
        lblDiDen1:'',
        visible:false,
      },
      diden2:{
        lblDiDen2:'',
        visible:false,
      },
      lblBocuc:'',

      // tiện ích diagram
      isShowLayer:false,
      isShowDoiTuong: true,
      isShowLienKet:true,
      isShowTitle:true,
      isShowGridLine:true,
      // Tìm kiếm
      isSearch: 0,
      searchCategory:'TRAM_TB',
      dsKetCuoi:[],
      dsKetCuoiSearch:[],
      dsTramTB:[],
      dsTramTBSearch:[],
      dsCap:[],
      dsCapSearch:[],
      txttimkiem:'',
      showDataSearch: false,
      mapTypeId: 'ROADMAP',
      //THEM TRAM_TB
      ADD_TRAM_TB_FLAG:false,
      THONGTIN_TRAM_TB:null,

      currentZoomScale: {},

      savePoint:[],

      capObject:{}, // Tạo cáp tới (Giản đồ, Bản đồ)
      A:{}, // Nguồn - Tạo cáp tới (Giản đồ, Bản đồ)
      B:{}, // Đích - Tạo cáp tới (Giản đồ, Bản đồ)

      showtabGianDo: true,
      txt_ghichuTuyenCap:null,
      selected: {
        TUYENCAP_ID: null,
        TTVT_ID: null,
        TOVT_ID: null,
        TTVT_ID_2: null,
        TRAMTB_ID: null,
        TOVT_ID_2: null
      },
      bocuc: { version: null, translate: null, zoom: null, points: [], link:[] },
      cmd: true,
      color: '#FFEC3D',
      groupOptions: { columns: ['NHOM_LC'], showDropArea: false, captionTemplate: '<span class="groupItems" style="color: black">Nhóm loại cáp: ${key}</span>' },
      columnTemplate: function () {
        return {
          template: {
            template: '<span :id="data.index" :style="{display: display }" style="font-size: 12px;" class="col-mark-selected-row fa fa-play text-main"></span>',
            data () {
              return {
                data: { }
              }
            },
            computed: {
              display () {
                return parseInt(this.data.index) === this.$parent.getSelectedRowIndexes()[0] ? 'block' : 'none'
              }
            }
          }
        }
      },
      filterBarTemplate: {
        create: function (args) {
          let div = document.createElement('div')
          div.classList.add('input-icon-right')

          let input = document.createElement('input')
          input.classList.add('form-control')
          input.id = args.column.foreignKeyField

          let span = document.createElement('span')
          span.classList.add('icon', 'nc-icon-outline', 'ui-1_zoom')

          div.append(input)
          div.append(span)

          return div
        },
        write: function (args) {
          args.element.querySelector('input').addEventListener('input', (args) => {
            let value = args.currentTarget.value

            if (!value) {
              this.parent.removeFilteredColsByField(args.currentTarget.id)
            } else {
              this.parent.filterByColumn(args.currentTarget.id, 'contains', value)
            }
          })
        }
      }
    }
  },
  methods: {
    ...mapActions('cabman/QuanLyMangTruyenDan', actionName),
    
    onActionComplete (args) {
      if (args.requestType === 'filtering') {
        let element = document.getElementById(args.currentFilterObject.field)

        if (element.type === 'text') {
          element.click()
          element.focus()
        }
      }
    },
    onRowSelectedTTB: async function (args) {
      this.$refs['gridTTB'].$el.querySelectorAll('.col-mark-selected-row').forEach(item => {
        item.style.display = args.rowIndex === parseInt(item.id) ? 'block' : 'none'
      })
    },
    onCreateGridTTB () {
      this.$refs['gridTTB'].$el.classList.add('table-result')
    },
    dataBoundTTB: function () {
      try {
        this.$refs.gridTTB.selectRow(0)
      } catch (error) {

      }
    },
    async onDoubleClickTTB (args) {
      try {
        var rowData = args.rowData
        this.$refs.gridTTB.ej2Instances.selectRow(args.rowIndex, true)
      } catch (error) {
      }
    },
    onRowSelectedCG: async function (args) {
      this.$refs['gridCG'].$el.querySelectorAll('.col-mark-selected-row').forEach(item => {
        item.style.display = args.rowIndex === parseInt(item.id) ? 'block' : 'none'
      })
    },
    onCreateGridCG () {
      this.$refs['gridCG'].$el.classList.add('table-result')
    },
    dataBoundCG: function () {
      try {
        this.$refs.gridCG.selectRow(0)
      } catch (error) {

      }
    },
    async onDoubleClickCG (args) {
      try {
        var rowData = args.rowData
        this.$refs.gridCG.ej2Instances.selectRow(args.rowIndex, true)
      } catch (error) {
      }
    },
    onClickCmd (index) {
      console.log('onClickCmd', index)
      var phancap_id = 0
      if (index > -1) {
        this.color = '#' + this.data.dsPHANCAP[index].MAU
        phancap_id = this.data.dsPHANCAP[index].PHANCAP_ID
        this.$refs.BanDo.phanCapCap(phancap_id)
      }
      this.cmd = !this.cmd
    },
    thaydoiMapType () {

      if (this.mapTypeId === 'ROADMAP') {
        this.mapTypeId = 'SATELLITE';
      } else {
        this.mapTypeId = 'ROADMAP';
      }
      this.$refs.BanDo.thaydoiMapType();
      this.NAP_GIANDO_TRUYENDAN();
    },
    allowMoveNode () { this.$refs.BanDo.allowMoveNode() },
    async luuToaDoBanDo () {
      try {
        this.$root.showLoading(true)
        for (const Node of this.$refs.BanDo.getNodes()) {
          if (Node.Modified && !Node.isDiemuon) {
            let id = Node.id2
            if (Node.Group === 'TRAM_TB') {
              await this.$root.context.post('/web-cabman/mang-truyen-dan/capnhat-toado', {
                tramtb_id: id,
                vido: Node.Position.lat,
                kinhdo: Node.Position.lng
              });
            }
            if (Node.Group === 'KETCUOI') {
              await this.$root.context.post('/web-cabman/mang-truyen-dan/capnhat-toado-ketcuoi', {
                KETCUOI_ID: id,
                VIDO: Node.Position.lat,
                KINHDO: Node.Position.lng
              });
            }
          }
        }
        for (const Route of this.$refs.BanDo.getRoutes()) {
          if (Route.Modified || Route.modified) {
            let json = []
            let id = Route.id
            let day_toado = Route.path.filter((e) => {
              return e.id.startsWith('DIEMUON_')
            })
            day_toado.forEach((e) => {
              json.push(e.lat + "," + e.lng)
            })
            await this.$root.context.post('/web-cabman/mang-truyen-dan/capnhat-toado-cap', {
              CAP_ID: id,
              DAY_TOADO: json.join(';'),
            });
          }
        }
        this.$root.showLoading(false)
      } catch (error) {
        this.$root.showLoading(false)
      } finally {
        this.$refs.BanDo.clearModified()
        // this.nap_gian_do()
      }
    },
    async btnNapGD(){
      if (this.selected && this.selected.TUYENCAP_ID) {
        await this.$root.context.get("/web-cabman/mang-truyen-dan/lay-ghichu-tuyencap-giando",{tuyencap_id:this.selected.TUYENCAP_ID})
          .then(res =>{
            if (res.error === "200")
              this.txt_ghichuTuyenCap = res.data.GHICHU
          }).catch(e =>{})
        await this.NAP_GIANDO_TRUYENDAN()
      }
    },
    async tuyencap_selectedChanged(data) {
      if (data && data.TUYENCAP_ID) {
        await this.$root.context.get("/web-cabman/mang-truyen-dan/lay-ghichu-tuyencap-giando",{tuyencap_id:data.TUYENCAP_ID})
          .then(res =>{
            if (res.error === "200")
              this.txt_ghichuTuyenCap = res.data.GHICHU
          }).catch(e =>{})
        await this.NAP_GIANDO_TRUYENDAN()
      }
    },
    nap_gian_do :async function ()
    {
      let translate = this.$refs.GianDo.getCurrentOffset()
      let zoom = this.currentZoomScale.CurrentZoom
      await this.NAP_GIANDO_TRUYENDAN()
      this.$refs.GianDo.bringIntoView(translate)
      this.$refs.GianDo.zoom(zoom)
    } ,
    loadData: async function () {
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.get(
          '/web-cabman/bando_matdo/thongtin_bando'
        )
        if (rs.data != null && rs.data.length > 0) {
          this.setThongTinMap({
            lat: parseFloat(rs.data[0].C_LATITUS),
            lng: parseFloat(rs.data[0].C_LONGTITUS),
            zoom: rs.data[0].DEFAULT_ZOOM,
            mapTypeId: 'roadmap'
          })
        }
        // rs = await this.api_execute({ data: { V_PHANVUNG_ID: this.$root.token.getPhanVungID(), V_NHANVIEN_ID: null }, function: 'CABMAN.PKG_SINHDN.SP_NAP_DS_TUYENCAP' })
        rs = await this.$root.context.get('web-cabman/mang-truyen-dan/lay-danhsach-tuyencap')
        if (rs.data) {
          console.log('dsTuyenCap', rs)
          this.setState({key: 'dsTuyenCap', value: rs.data})
          if (this.data.dsTuyenCap.length > 0) {
            this.selected.TUYENCAP_ID = this.data.dsTuyenCap[0].TUYENCAP_ID
          }
        }
        // rs = await this.api_execute({ data: { V_PHANVUNG_ID: this.$root.token.getPhanVungID(), V_NHANVIEN_ID: null }, function: 'CABMAN.PKG_SINHDN.SP_NAP_DS_TTVT' })
        rs = await this.$root.context.get('web-cabman/mang-truyen-dan/lay-danhsach-ttvt')
        if (rs.data) {
          // console.log('dsTTVT', rs)
          this.setState({key: 'dsTTVT', value: rs.data})
          if (this.data.dsTTVT.length > 0) {
            this.selected.TTVT_ID = this.data.dsTTVT[0].DONVI_ID
            this.selected.TTVT_ID_2 = this.data.dsTTVT[0].DONVI_ID
          }
        }
        this.NAP_DS_PHANCAP()
      } catch (error) {
        console.log('loadData error', error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    Point (x, y) {
      this.x = x
      this.y = y
    },
    base64toBlob (base64Data, contentType) {
      contentType = contentType || ''
      var sliceSize = 1024
      var byteCharacters = atob(base64Data)
      var bytesLength = byteCharacters.length
      var slicesCount = Math.ceil(bytesLength / sliceSize)
      var byteArrays = new Array(slicesCount)

      for (var sliceIndex = 0; sliceIndex < slicesCount; ++sliceIndex) {
        var begin = sliceIndex * sliceSize
        var end = Math.min(begin + sliceSize, bytesLength)

        var bytes = new Array(end - begin)
        for (var offset = begin, i = 0; offset < end; ++i, ++offset) {
          bytes[i] = byteCharacters[offset].charCodeAt(0)
        }
        byteArrays[sliceIndex] = new Uint8Array(bytes)
      }
      return new Blob(byteArrays, { type: contentType })
    },
    base64ToArrayBuffer (base64) {
      var binary_string = window.atob(base64)
      var len = binary_string.length
      var bytes = new Uint8Array(len)
      for (var i = 0; i < len; i++) {
        bytes[i] = binary_string.charCodeAt(i)
      }
      return bytes.buffer
    },
    async NAP_GIANDO_TRUYENDAN () {
      try {
        this.bocuc = { version: null, translate: null, zoom: null, points: [], link:[] }
        this.$refs.BanDo.Clear()
        this.$refs.GianDo.Clear()
        this.MAP_CLEAR();
        this.$root.showLoading(true)
        // var rs = await this.api_execute({ data: { V_PHANVUNG_ID: this.$root.token.getPhanVungID(), V_TUYENCAP_ID: this.selected.TUYENCAP_ID }, function: 'CABMAN.PKG_SINHDN.SP_LAY_GIANDO_TRUYENDAN' })
        let rs = await this.$root.context.get('web-cabman/mang-truyen-dan/lay-giando-truyendan', {
          tuyenCapId: this.selected.TUYENCAP_ID
        })
        if (rs.data) {
          // console.log('dsGIANDO', JSON.stringify(rs.data))
          this.setState({key: 'dsGIANDO', value: rs.data.filter(x => x.KETCUOI_D_ID != null && x.KETCUOI_N_ID != null)})
          // rs = await this.api_execute({ data: { V_PHANVUNG_ID: this.$root.token.getPhanVungID(), V_TUYENCAP_ID: this.selected.TUYENCAP_ID }, function: 'CABMAN.PKG_SINHDN.SP_LAY_BOCUC_GIANDO' })
          rs = await this.$root.context.get('web-cabman/mang-truyen-dan/lay-bocuc-giando-truyendan', {
            tuyenCapId: this.selected.TUYENCAP_ID
          })
          //bocuc 8: layout type = '', bocuc !=8: layout = 'ComplexHierarchicalTree'
          const BOCUC_CUSTOM = 8
          if (rs.data[0].BOCUC !== BOCUC_CUSTOM){
            await this.$refs.GianDo.changeLayout("ComplexHierarchicalTree")
            this.lblBocuc = "ComplexHierarchicalTree"
          }else {
            await this.$refs.GianDo.changeLayout(null)
            this.lblBocuc = "Tùy chỉnh"
          }

          if (rs.data) {
            if (rs.data[0].DULIEU_BC !== null) {
              try {
                var br = new BinaryReader(rs.data[0].DULIEU_BC, 'little', 'base64')
                this.bocuc.version = br.ReadBytes(4)
                var x = br.ReadInt32()
                var y = br.ReadInt32()
                this.bocuc.translate = {
                  x: x,
                  y: y
                }
                var z = br.ReadFloat()
                this.bocuc.zoom = z
                // Point
                var np = br.ReadInt32()
                for (let index = 0; index < np; index++) {
                  var cc = br.ReadBytes(1)
                  var pt
                  // console.log('SP_LAY_BOCUC_GIANDO cc', cc[0])
                  switch (cc[0]) {
                    case 0:
                      pt = 'TRAM_TB'
                      break
                    case 1:
                      pt = 'KETCUOI'
                      break
                    default:
                      continue
                  }
                  var pi = br.ReadInt64()
                  var px = br.ReadInt32()
                  var py = br.ReadInt32()
                  var pd = br.ReadBytes(1)
                  this.bocuc.points.push(
                    {
                      pt: pt,
                      pi: pi,
                      px: px,
                      py: py,
                      pd: pd
                    }
                  )
                }
                // console.log('SP_LAY_BOCUC_GIANDO bocuc', this.bocuc)
                // Link
                var nl = br.ReadInt32()
                for (let index = 0; index < nl; index++) {
                  var li = br.ReadInt64();
                  // var lnk = this.$refs.GianDo.TimLink(li)
                  // var lnk = this.data.dsGIANDO.find(x => x.CAP_ID == li)
                  // if (lnk) {
                    let textPoint_x = br.ReadInt32();
                    let textPoint_y = br.ReadInt32();
                    let curvePoint1_x = br.ReadInt32();
                    let curvePoint1_y = br.ReadInt32();
                    let curvePoint2_x = br.ReadInt32();
                    let curvePoint2_y = br.ReadInt32();
                    let pointList = []
                    // pointList.push({x:null,y:null}) // A
                    let ln = br.ReadInt32();
                    for (let j = 0; j < ln; j++){
                      var lx = br.ReadInt32();
                      var ly = br.ReadInt32();
                      pointList.push({x:lx, y:ly})
                    }
                    // pointList.push({x:null,y:null}) // B
                    this.bocuc.link.push({
                      li:li,
                      textPoint:{x:textPoint_x, y:textPoint_y},
                      curvePoint1:{x:curvePoint1_x, y:curvePoint1_y},
                      curvePoint2:{x:curvePoint2_x, y:curvePoint2_y},
                      points:pointList
                    })
                  // } else {
                  //   //ms.Seek(6 * sizeof(int), SeekOrigin.Current);
                  //   // br.ByteBuffer = br.ByteBuffer.slice(6*4)
                  //   // br.Position += 6*4
                  //   br.ReadInt32();br.ReadInt32();br.ReadInt32();br.ReadInt32();br.ReadInt32();br.ReadInt32()
                  //   let ln = br.ReadInt32();
                  //   // ms.Seek(ln * sizeof(int) * 2, SeekOrigin.Current);
                  //   // br.ByteBuffer = br.ByteBuffer.slice(ln*4*2)
                  //   // br.Position += ln*4*2
                  //   for (let i =0; i < ln; i++) {
                  //     br.ReadInt32();br.ReadInt32();
                  //   }
                  //   console.log("Không tìm thấy cáp:", li, ln)
                  // }


                }
              } catch (error) {
                console.log('SP_LAY_BOCUC_GIANDO', error)
              }
            }
          }
          this.NAP_GIANDO_CABMAN_CON(this.bocuc, this.data.dsGIANDO, -1)
        }
      } catch (error) {
        this.$root.showLoading(false)
      }
      this.$root.showLoading(false)
    },
    CreateNode () {
      return new Point()
    },
    NAP_GIANDO_CABMAN_CON (bocuc, dsGIANDO, capgoc_id) {
      // this.$refs.BanDo.refreshMap();
      for (let index = 0; index < dsGIANDO.length; index++) {
        const GIANDO = dsGIANDO[index]
        var cap_id = GIANDO.CAP_ID
        if (Object.keys(this.$refs.GianDo.TimLink(cap_id.toString())).length === 0) {
          var lopmc_id = GIANDO.LOPMC_ID
          var ketcuoi_n = new Point()
          var m_ketcuoi_n = new NodeMapVNPT()
          var ketcuoi_d = new Point()
          var m_ketcuoi_d = new NodeMapVNPT()
          // var m_polyline = new GraphPolyline()
          // console.log('lopmc_id', lopmc_id)
          // ===============================================================
          // Tạo trạm nguồn (1, 4), kết cuối nguồn (2, 3)
          // ===============================================================
          switch (lopmc_id) {
            case 1 :
            case 4 :
              var ketcuoi_n_id = GIANDO.KETCUOI_N_ID
              if (ketcuoi_n_id) {
                // Giản đồ
                ketcuoi_n = this.$refs.GianDo.TimPoint('TRAM_TB_' + ketcuoi_n_id.toString())
                if (Object.keys(ketcuoi_n).length === 0) {
                  ketcuoi_n = this.$refs.GianDo.TaoPoint(
                    'TRAM_TB_' + ketcuoi_n_id.toString(),
                    GIANDO.KYHIEU_KCN + '',
                    GIANDO.ICON_KCN + '',
                    GIANDO.TENHT_KCN + '')
                  let idx = bocuc.points.findIndex(p => p.pi === ketcuoi_n_id && p.pt === 'TRAM_TB')
                  if (idx > -1) {
                    ketcuoi_n.offsetX = bocuc.points[idx].px
                    ketcuoi_n.offsetY = bocuc.points[idx].py
                  } else if (this.lblBocuc == "Custom") {
                    console.log("Không tìm thấy tọa độ tram_tb nguồn : ", ketcuoi_n_id, GIANDO.KYHIEU_KCN)
                  }
                  ketcuoi_n.Group = 'TRAM_TB'
                  ketcuoi_n.ImageKey = GIANDO.ICON_KCN
                  ketcuoi_n.Name = GIANDO.KYHIEU_KCN
                  ketcuoi_n.DisplayName = GIANDO.TENHT_KCN
                  ketcuoi_n.Size = { width: 40, height: 40 }
                  ketcuoi_n.AllowMove = false
                  ketcuoi_n.RootId = capgoc_id
                  ketcuoi_n.Position = { x: 0, y: 0 }
                  this.$refs.GianDo.CapNhatPoint(ketcuoi_n)
                  this.MAP_PUSH( ketcuoi_n.Group +'#'+ ketcuoi_n_id, capgoc_id)
                }
                // Bản đồ
                m_ketcuoi_n = this.$refs.BanDo.TimNode('TRAM_TB_' + ketcuoi_n_id)
                if (m_ketcuoi_n === null) {
                  m_ketcuoi_n = this.$refs.BanDo.TaoNode()
                  m_ketcuoi_n.id = 'TRAM_TB_' + ketcuoi_n_id
                  m_ketcuoi_n.id2 = ketcuoi_n_id
                  m_ketcuoi_n.Group = 'TRAM_TB'
                  m_ketcuoi_n.ImageKey = GIANDO.ICON_KCN
                  m_ketcuoi_n.SetIcon(m_ketcuoi_n.ImageKey)
                  m_ketcuoi_n.Label = GIANDO.KYHIEU_KCN
                  m_ketcuoi_n.Tooltip = GIANDO.TENHT_KCN
                  m_ketcuoi_n.Tag = ketcuoi_n
                  m_ketcuoi_n.AllowMove = false
                  m_ketcuoi_n.RootId = capgoc_id
                  m_ketcuoi_n.Position = { lat: Math.abs(parseFloat(GIANDO.VIDO_KCN)) > 90 ? 0 : parseFloat(GIANDO.VIDO_KCN), lng: Math.abs(parseFloat(GIANDO.KINHDO_KCN)) > 180 ? 0 : parseFloat(GIANDO.KINHDO_KCN) }
                }
              } else {
                // Thiếu thông tin trạm nguồn khi tạo cáp
                break
              }
              break
            case 2 :
            case 3 :
              ketcuoi_n_id = GIANDO.KETCUOI_N_ID
              if (ketcuoi_n_id) {
                // Giản đồ
                ketcuoi_n = this.$refs.GianDo.TimPoint('KETCUOI_' + ketcuoi_n_id.toString())
                if (Object.keys(ketcuoi_n).length === 0) {
                  ketcuoi_n = this.$refs.GianDo.TaoPoint(
                    'KETCUOI_' + ketcuoi_n_id.toString(),
                    GIANDO.KYHIEU_KCN + '',
                    GIANDO.ICON_KCN + '',
                    GIANDO.TENHT_KCN + '')

                  let idx = bocuc.points.findIndex(p => p.pi === ketcuoi_n_id && p.pt === 'KETCUOI')
                  if (idx > -1) {
                    ketcuoi_n.offsetX = bocuc.points[idx].px
                    ketcuoi_n.offsetY = bocuc.points[idx].py
                  } else if (this.lblBocuc == "Custom") {
                    console.log("Không tìm thấy tọa độ kết cuối nguồn : ", ketcuoi_n_id, GIANDO.KYHIEU_KCN)
                  }

                  ketcuoi_n.Group = 'KETCUOI'
                  ketcuoi_n.ImageKey = GIANDO.ICON_KCN
                  ketcuoi_n.Name = GIANDO.KYHIEU_KCN
                  ketcuoi_n.DisplayName = GIANDO.TENHT_KCN
                  ketcuoi_n.Size = { width: 24, height: 24 }
                  ketcuoi_n.AllowMove = false
                  ketcuoi_n.RootId = capgoc_id
                  ketcuoi_n.Position = { x: 0, y: 0 }
                  this.$refs.GianDo.CapNhatPoint(ketcuoi_n)
                  this.MAP_PUSH( ketcuoi_n.Group +'#'+ ketcuoi_n_id, capgoc_id)

                }
                // Bản đồ
                m_ketcuoi_n = this.$refs.BanDo.TimNode('KETCUOI_' + ketcuoi_n_id)
                if (m_ketcuoi_n === null) {
                  m_ketcuoi_n = this.$refs.BanDo.TaoNode()
                  m_ketcuoi_n.id = 'KETCUOI_' + ketcuoi_n_id
                  m_ketcuoi_n.id2 = ketcuoi_n_id
                  m_ketcuoi_n.Group = 'KETCUOI'
                  m_ketcuoi_n.Size = { width: 24, height: 24 }
                  m_ketcuoi_n.ImageKey = GIANDO.ICON_KCN
                  m_ketcuoi_n.SetIcon(m_ketcuoi_n.ImageKey)
                  m_ketcuoi_n.Label = GIANDO.KYHIEU_KCN
                  m_ketcuoi_n.Tooltip = GIANDO.TENHT_KCN
                  m_ketcuoi_n.Tag = ketcuoi_n
                  m_ketcuoi_n.AllowMove = false
                  m_ketcuoi_n.RootId = capgoc_id
                  m_ketcuoi_n.Position = { lat: Math.abs(parseFloat(GIANDO.VIDO_KCN)) > 90 ? 0 : parseFloat(GIANDO.VIDO_KCN), lng: Math.abs(parseFloat(GIANDO.KINHDO_KCN)) > 180 ? 0 : parseFloat(GIANDO.KINHDO_KCN) }
                }
              } else {
                // Thiếu thông tin trạm nguồn khi tạo cáp
                break
              }
              break
            default:
          }
          // ===============================================================
          // Tạo kết cuối đích (1, 2), trạm đích (3, 4)
          // ===============================================================
          switch (lopmc_id) {
            case 1 :
            case 2 :
              var ketcuoi_d_id = GIANDO.KETCUOI_D_ID
              if (ketcuoi_d_id) {
                // Giản đồ
                ketcuoi_d = this.$refs.GianDo.TimPoint('KETCUOI_' + ketcuoi_d_id.toString())
                if (Object.keys(ketcuoi_d).length === 0) {
                  ketcuoi_d = this.$refs.GianDo.TaoPoint(
                    'KETCUOI_' + ketcuoi_d_id.toString(),
                    GIANDO.KYHIEU_KCD + '',
                    GIANDO.ICON_KCD + '',
                    GIANDO.TENHT_KCD + '')

                  let idx = bocuc.points.findIndex(p => p.pi === ketcuoi_d_id && p.pt === 'KETCUOI')
                  if (idx > -1) {
                    ketcuoi_d.offsetX = bocuc.points[idx].px
                    ketcuoi_d.offsetY = bocuc.points[idx].py
                  } else if (this.lblBocuc == "Custom") {
                    console.log("Không tìm thấy tọa độ kết cuối đích : ", ketcuoi_d_id, GIANDO.KYHIEU_KCD)
                  }

                  ketcuoi_d.Group = 'KETCUOI'
                  ketcuoi_d.ImageKey = GIANDO.ICON_KCD
                  ketcuoi_d.Name = GIANDO.KYHIEU_KCD
                  ketcuoi_d.DisplayName = GIANDO.TENHT_KCD
                  ketcuoi_d.Size = { width: 24, height: 24 }
                  ketcuoi_d.AllowMove = false
                  ketcuoi_d.RootId = capgoc_id
                  ketcuoi_d.Position = { x: 0, y: 0 }
                  this.$refs.GianDo.CapNhatPoint(ketcuoi_d)
                  this.MAP_PUSH( ketcuoi_d.Group +'#'+ ketcuoi_d_id, capgoc_id)
                }
                // Bản đồ
                m_ketcuoi_d = this.$refs.BanDo.TimNode('KETCUOI_' + ketcuoi_d_id)
                if (m_ketcuoi_d === null) {
                  m_ketcuoi_d = this.$refs.BanDo.TaoNode()
                  m_ketcuoi_d.id = 'KETCUOI_' + ketcuoi_d_id
                  m_ketcuoi_d.id2 = ketcuoi_d_id
                  m_ketcuoi_d.Group = 'KETCUOI'
                  m_ketcuoi_d.Size = { width: 24, height: 24 }
                  m_ketcuoi_d.ImageKey = GIANDO.ICON_KCD
                  m_ketcuoi_d.SetIcon(m_ketcuoi_d.ImageKey)
                  m_ketcuoi_d.Label = GIANDO.KYHIEU_KCD
                  m_ketcuoi_d.Tooltip = GIANDO.TENHT_KCD
                  m_ketcuoi_d.Tag = ketcuoi_d
                  m_ketcuoi_d.AllowMove = false
                  m_ketcuoi_d.RootId = capgoc_id
                  m_ketcuoi_d.Position = { lat: Math.abs(parseFloat(GIANDO.VIDO_KCD)) > 90 ? 0 : parseFloat(GIANDO.VIDO_KCD), lng: Math.abs(parseFloat(GIANDO.KINHDO_KCD)) > 180 ? 0: parseFloat(GIANDO.KINHDO_KCD) }
                }
              } else {
                // Thiếu thông tin trạm nguồn khi tạo cáp
                break
              }
              break
            case 3 :
            case 4 :
              ketcuoi_d_id = GIANDO.KETCUOI_D_ID
              if (ketcuoi_d_id) {
                // Giản đồ
                ketcuoi_d = this.$refs.GianDo.TimPoint('TRAM_TB_' + ketcuoi_d_id.toString())
                if (Object.keys(ketcuoi_d).length === 0) {
                  ketcuoi_d = this.$refs.GianDo.TaoPoint(
                    'TRAM_TB_' + ketcuoi_d_id.toString(),
                    GIANDO.KYHIEU_KCD + '',
                    GIANDO.ICON_KCD + '',
                    GIANDO.TENHT_KCD + '')

                  let idx = bocuc.points.findIndex(p => p.pi === ketcuoi_d_id && p.pt === 'TRAM_TB')
                  if (idx > -1) {
                    ketcuoi_d.offsetX = bocuc.points[idx].px
                    ketcuoi_d.offsetY = bocuc.points[idx].py
                  } else if (this.lblBocuc == "Custom") {
                    console.log("Không tìm thấy tọa độ tram_tb đích : ", ketcuoi_d_id, GIANDO.KYHIEU_KCD)
                  }

                  ketcuoi_d.Group = 'TRAM_TB'
                  ketcuoi_d.ImageKey = GIANDO.ICON_KCD
                  ketcuoi_d.Name = GIANDO.KYHIEU_KCD
                  ketcuoi_d.DisplayName = GIANDO.TENHT_KCD
                  ketcuoi_d.Size = { width: 40, height: 40 }
                  ketcuoi_d.AllowMove = false
                  ketcuoi_d.RootId = capgoc_id
                  ketcuoi_d.Position = { x: 0, y: 0 }
                  this.$refs.GianDo.CapNhatPoint(ketcuoi_d)
                  this.MAP_PUSH( ketcuoi_d.Group +'#'+ ketcuoi_d_id, capgoc_id)

                }
                // Bản đồ
                m_ketcuoi_d = this.$refs.BanDo.TimNode('TRAM_TB_' + ketcuoi_d_id)
                if (m_ketcuoi_d === null) {
                  m_ketcuoi_d = this.$refs.BanDo.TaoNode()
                  m_ketcuoi_d.id = 'TRAM_TB_' + ketcuoi_d_id
                  m_ketcuoi_n.id2 = ketcuoi_d_id
                  m_ketcuoi_d.Group = 'TRAM_TB'
                  m_ketcuoi_d.ImageKey = GIANDO.ICON_KCD
                  m_ketcuoi_d.SetIcon(m_ketcuoi_d.ImageKey)
                  m_ketcuoi_d.Label = GIANDO.KYHIEU_KCD
                  m_ketcuoi_d.Tooltip = GIANDO.TENHT_KCD
                  m_ketcuoi_d.Tag = ketcuoi_d
                  m_ketcuoi_d.AllowMove = false
                  m_ketcuoi_d.RootId = capgoc_id
                  m_ketcuoi_d.Position = { lat: Math.abs(parseFloat(GIANDO.VIDO_KCD)) > 90 ? 0 : parseFloat(GIANDO.VIDO_KCD), lng: Math.abs(parseFloat(GIANDO.KINHDO_KCD)) > 180 ? 0 : parseFloat(GIANDO.KINHDO_KCD) }
                }
              } else {
                // Thiếu thông tin trạm nguồn khi tạo cáp
                break
              }
              break
            default:
          }
          // ===============================================================
          // Tạo cáp
          // ===============================================================
          // Giản đồ
          let cap
          if (ketcuoi_n_id == ketcuoi_d_id) {
            console.log("ketcuoi_n_id:", ketcuoi_n_id, ketcuoi_n)
            console.log("ketcuoi_d_id:", ketcuoi_d_id, ketcuoi_d, Object.keys(ketcuoi_d).length === 0)
            let ketcuoi_d_vitual = this.$refs.GianDo.TaoPoint(
              ketcuoi_d.id + "_v"+ index,
              GIANDO.KYHIEU_KCD + ' (Ảo)',
              GIANDO.ICON_KCD + '',
              GIANDO.TENHT_KCD + ' (Ảo)')
            ketcuoi_d_vitual.Group = [3,4].includes(lopmc_id) ? "TRAM_TB" : "KETCUOI" // 1,2 kc dich, 3,4 tram dich
            ketcuoi_d_vitual.ImageKey = GIANDO.ICON_KCD
            ketcuoi_d_vitual.Name = GIANDO.KYHIEU_KCD + " (Ảo)"
            ketcuoi_d_vitual.DisplayName = GIANDO.TENHT_KCD + " (Ảo)"
            ketcuoi_d_vitual.Size = { width: 40, height: 40 }
            ketcuoi_d_vitual.AllowMove = false
            ketcuoi_d_vitual.RootId = capgoc_id
            ketcuoi_d_vitual.Position = { x: 0, y: 0 }
            this.MAP_PUSH( ketcuoi_d_vitual.Group +'#'+ ketcuoi_d_vitual, capgoc_id)
            if (this.lblBocuc == "Custom") {
              let idx = bocuc.points.findIndex(p => p.pi === ketcuoi_d_id && p.pt === ketcuoi_d_vitual.Group)
              if (idx > -1) {
                ketcuoi_d_vitual.offsetX = bocuc.points[idx].px + this.getRndInteger(-10, 10);
                ketcuoi_d_vitual.offsetY = bocuc.points[idx].py + this.getRndInteger(-10, 10);
              } else{
                console.log("Không tìm thấy tọa độ: ", ketcuoi_d_vitual, GIANDO.KYHIEU_KCN)
              }
            }
            console.log("ketcuoi_d_vitual:", ketcuoi_d_vitual)
            this.$refs.GianDo.CapNhatPoint(ketcuoi_d_vitual)
            cap = this.$refs.GianDo.TaoLink(cap_id.toString(), ketcuoi_n, ketcuoi_d_vitual, bocuc.link)
          } else {
            cap = this.$refs.GianDo.TaoLink(cap_id.toString(), ketcuoi_n, ketcuoi_d, bocuc.link)
          }
          cap.Group = 'CAP'
          cap.Name = GIANDO.KYHIEU
          cap.DisplayName = GIANDO.TEN_HT
          cap.ShowCaption = GIANDO.HIENTHI === 1
          cap.LOPMC_ID = GIANDO.LOPMC_ID;
          cap.Width = 1
          cap.SelectedWidth = 2
          cap.RootId = capgoc_id
          // cap.id = cap_id
          cap.STYLE_KIEU_DC(GIANDO.KIEUDC_ID)
          cap.STYLE_NHOM_LC(GIANDO.NHOMLC_ID)
          cap.STYLE_KIEU_CAP(GIANDO.KIEUCAP_ID)
          cap.BoCuc = this.lblBocuc
          this.$refs.GianDo.CapNhatLink(cap)
          this.MAP_PUSH( cap.Group +'#'+ cap_id, capgoc_id)

          let m_polyline  = this.$refs.BanDo.TaoPolyline()
          m_polyline.path.push({id: m_ketcuoi_n.id, lat: m_ketcuoi_n.Position.lat, lng: m_ketcuoi_n.Position.lng});

          //tạo điểm uốn
          let DAY_TOADO = GIANDO.DAY_TOADO
          if (DAY_TOADO) {
            let arr = DAY_TOADO.split(";");
            for (let j = 0; j < arr.length; j++) {
              let arr2 = arr[j].split(',');
              // console.log(arr2);
              let lat = parseFloat(arr2[0]);
              let lng = parseFloat(arr2[1]);
              var id = 'DIEMUON_' + this.create_UUID();
              var node =  new NodeMapVNPT();
              node.id = id;
              node.Visible = true;
              node.draggable = true;
              node.Group = 'DIEMUON';
              // node.ImageKey = GIANDO.ICON_KCD
              node.Position = { lat: lat, lng: lng };
              node.IsDiemuon = true;
              node.id_polyline = 'CAP_' + dsGIANDO[index].CAP_ID;
              this.$refs.BanDo.ThemNodeDiemUon(node);
              m_polyline.path.push({id: id, lat: lat, lng: lng});
            }
          }

          m_polyline.path.push({id: m_ketcuoi_d.id, lat: m_ketcuoi_d.Position.lat, lng: m_ketcuoi_d.Position.lng});
          m_polyline.id = '' + GIANDO.CAP_ID;
          m_polyline.label = GIANDO.KYHIEU;
          m_polyline.source = m_ketcuoi_n.id;
          m_polyline.dest = m_ketcuoi_d.id;
          m_polyline.style = '0-1-0-1';



          // var objIndex = polylines.findIndex(obj => obj.id === polyline.id);
          // if (objIndex < 0) polylines.push(polyline);

          let m_cap = this.$refs.BanDo.TaoRoute()
          m_cap.Group = 'CAP'
          m_cap.id = cap_id
          m_cap.ThemPosition(m_ketcuoi_n.id, m_ketcuoi_n.Position)
          m_cap.ThemDanhSachPosition(GIANDO.DAY_TOADO)
          m_cap.ThemPosition(m_ketcuoi_d.id, m_ketcuoi_d.Position)
          m_cap.A = m_ketcuoi_n
          m_cap.B = m_ketcuoi_d
          m_cap.Tag = cap
          m_cap.Name = GIANDO.KYHIEU
          m_cap.Layer = GIANDO.PHANCAP_ID
          m_cap.RootId = capgoc_id
          m_cap.BackColor = GIANDO.MAU

          // if (index > 200) break
        }
      }
      this.$refs.BanDo.FitBounds()
      this.$refs.GianDo.DoLayout()
      //this.$refs.GianDo.bringIntoView(bocuc.translate)
      //this.$refs.GianDo.zoom(bocuc.zoom)
      this.$refs.GianDo.FitToPage()
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

    getRndInteger(min, max) {
      return Math.floor(Math.random() * (max - min + 1) ) + min;
    },
    GIANDO_TIM_CAP_ID (cap_id) {
      return null
    },
    GIANDO_TIM_KETCUOI_ID (ketcuoi_id) {
      return null
    },
    NAP_DS_PHANCAP () {
      // this.api_execute({ data: { V_PHANVUNG_ID: this.$root.token.getPhanVungID() }, function: 'CABMAN.PKG_SINHDN.SP_NAP_DS_PHANCAP' }).then((reponse) => {
      //   if (reponse.data) {
      //     console.log('SP_NAP_DS_PHANCAP', reponse)
      //     this.setState({key: 'dsPHANCAP', value: reponse.data})
      //     this.$refs.BanDo.addMenuPhanCapCap()
      //   }
      // })
      this.$root.context.get('web-cabman/mang-truyen-dan/lay-danhsach-phancap').then(reponse => {
        if (reponse.data) {
          console.log('SP_NAP_DS_PHANCAP', reponse)
          this.setState({key: 'dsPHANCAP', value: reponse.data})
          this.$refs.BanDo.addMenuPhanCapCap()
        }
      })
    },
    async NAP_GIANDO_CABMAN(capgoc_id) {
      try {
        this.loading(true)
        let ds
        await this.$root.context.get("web-cabman/mang-truyen-dan/lay-giando-cabman", {capgocId: capgoc_id})
        .then(res =>{
          if (res.error === "200"){
            ds = res.data
          }
        }).catch(e =>this.$root.toastError("Có lỗi khi nạp giản đồ Cabman\r\n"+ e.message))
        // Nạp giản đồ liên thông
        this.NAP_GIANDO_CABMAN_CON(this.bocuc,ds.GD_LIENTHONG, capgoc_id);
        // Nạp kết cuối không liên thông
        for (let kc of ds.KC_KHONG_LIENTHONG){
          let ketcuoi = this.$refs.GianDo.TaoPoint(
            'KETCUOI_'+ kc.KETCUOI_ID,
            kc.KYHIEU + '',
            kc.ICON_KC + '',
            kc.TENHT + '')
          ketcuoi.Group = 'KETCUOI'
          ketcuoi.ImageKey = kc.ICON_KC
          ketcuoi.Name = kc.KYHIEU
          ketcuoi.DisplayName = kc.TENHT
          ketcuoi.Size = { width: 24, height: 24 }
          ketcuoi.AllowMove = false
          ketcuoi.RootId = capgoc_id
          ketcuoi.Position = { x: 0, y: 0 }
          this.$refs.GianDo.CapNhatPoint(ketcuoi)
          this.MAP_PUSH(ketcuoi.Group + '#' + kc.KETCUOI_ID, capgoc_id);

        }
        // Nạp cáp không liên thông
        this.NAP_GIANDO_CABMAN_CON(this.bocuc,ds.CAP_KHONG_LIENTHONG, capgoc_id);
      }catch (e){

      }finally {
        this.loading(false)
      }
    },
    XOA_NAP_GIANDO_CABMAN(capgoc_id){
      let node_list = [...this.$refs.GianDo.GetNodes()]
      let connector_list = [...this.$refs.GianDo.GetConnectors()]

      // Xóa kết cuối, trạm thiết bị
      for (let node of node_list){
        let group_id = node.Group + '#' + node.id.split('_').at(-1)
        if (!this.MAP_POP(group_id,capgoc_id)) continue
        this.$refs.GianDo.DeleteNode(node)
      }
      // Xóa cáp
      for (let connector of connector_list){
        if (!this.MAP_POP(connector.Group + "#" + connector.id,capgoc_id)) continue
        this.$refs.GianDo.DeleteConnector(connector)
      }
      this.$refs.GianDo.DataBind()
    },
    /***
     * TẠO KẾT CUỐI
     * @param args
     * @returns {Promise<void>}
     */
    mniTaoKetCuoi_Click(...args){
      const [point,currentOffset] = args
      let ketcuoi = this.$refs.GianDo.TaoPoint(
        'KETCUOI_' + point.id,
        point.Name + '',
        'CHUA_XAC_DINH',
        point.DisplayName + ''
      )
      ketcuoi.offsetX = currentOffset.x
      ketcuoi.offsetY = currentOffset.y
      ketcuoi.ImageKey = 'CHUA_XAC_DINH'
      ketcuoi.Group = 'KETCUOI'
      ketcuoi.DisplayName = point.DisplayName
      ketcuoi.Name = point.Name
      ketcuoi.Size = {width: 24, height: 24}
      ketcuoi.AllowMove = false
      ketcuoi.RootId = -1
      ketcuoi.Position = currentOffset
      console.log("ketcuoi add:",ketcuoi)
      console.log("point add:",point)
      this.$refs.GianDo.CapNhatPoint(ketcuoi)
      this.$refs.GianDo.DataBind()
      //map
      let m_ketcuoi = this.$refs.BanDo.TaoNode()
      m_ketcuoi.id = 'KETCUOI_' + point.KETCUOI_ID
      m_ketcuoi.id2 = point.KETCUOI_ID
      m_ketcuoi.Group = 'KETCUOI'
      m_ketcuoi.Size = { width: 24, height: 24 }
      m_ketcuoi.ImageKey = 'CHUA_XAC_DINH'
      m_ketcuoi.SetIcon(m_ketcuoi.ImageKey)
      m_ketcuoi.Label = point.Name
      m_ketcuoi.Tooltip = point.DisplayName.replace("#", '\r\n');
      m_ketcuoi.Tag = ketcuoi
      m_ketcuoi.AllowMove = false
      m_ketcuoi.Position = { lat: Math.abs(parseFloat(point.VIDO)) > 90 ? 0 : parseFloat(point.VIDO), lng: Math.abs(parseFloat(point.KINHDO)) > 180 ? 0 : parseFloat(point.KINHDO) }
      this.$refs.BanDo.FitBounds()
    },
    // TẠO ĐIỂM UỐN CÁP
    // mniThemDiemUonGD_Click(...args){
    //   function ThreePointStraightLine(p1,p2,p3){
    //     let x1 = p1.x, y1 = p1.y
    //     let x2 = p2.x, y2 = p2.y
    //     let x3 = p3.x, y3 = p3.y
    //     // Formula for area of triangle is :
    //     // let rs = 0.5 * [x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)]
    //     // console.log(rs)
    //     // check p3 nằm giữa p1, p2 và thẳng hàng
    //     let p1p3_distance = Math.sqrt(Math.pow(x3-x1,2) + Math.pow(y3-y1,2))
    //     let p2p3_distance = Math.sqrt(Math.pow(x3-x2,2) + Math.pow(y3-y2,2))
    //     let p1p2_distance = Math.sqrt(Math.pow(x2-x1,2) + Math.pow(y2-y1,2))
    //     console.log(p1p2_distance,p1p3_distance + p2p3_distance,p1p2_distance-(p1p3_distance + p2p3_distance))
    //     // +-1000: threshold của 3 điểm thẳng hàng, +-20: threshold p3 nằm giữa p1, p2 -> ~ 0
    //     return /*Math.abs(rs) < 1000 &&*/ Math.abs(p1p2_distance-(p1p3_distance + p2p3_distance)) < 20
    //   }
    //   const [cap, position] = args
    //   let idx = this.bocuc.link.findIndex(x =>x.li.toString() === cap.id.toString())
    //   if (idx > -1){
    //     let points = [...this.bocuc.link[idx].points]
    //     points.unshift({x:cap.sourcePoint.x, y:cap.sourcePoint.y})
    //     points.push({x:cap.targetPoint.x, y:cap.targetPoint.y})
    //     if (points.length >= 2){
    //       for (let i = 0; i < points.length -1;i++){
    //         if (ThreePointStraightLine(points[i], points[i+1], position)){
    //           this.bocuc.link[idx].points.splice(i,0,position)
    //           break
    //         }
    //       }
    //       this.$refs.GianDo.mniThemDiemUonGD_Click(this.bocuc.link[idx].points)
    //     }
    //   }else{
    //     // case: không có thông tin trong bố cục
    //     let link = new Object()
    //     link.li = parseInt(cap.id,10)
    //     link.textPoint = cap.textPoint
    //     link.curvePoint1 = {x:0, y:0}
    //     link.curvePoint2 = {x:0, y:0}
    //     link.points = [position]
    //     this.bocuc.link.push(link)
    //     this.$refs.GianDo.mniThemDiemUonGD_Click([position])
    //   }
    // },
    // CẬP NHẬT ĐIỂM UỐN KHI DRAG
    // ThayDoiToaDoDiemUon(cap){
    //   let idx = this.bocuc.link.findIndex(x => x.li.toString() === cap.id.toString())
    //   if (idx > -1){
    //     let points = cap.segments.map(item => ({x:item.point.x, y:item.point.y}))
    //     points.pop()
    //     if (points.length > 0){
    //       this.bocuc.link[idx].points = points
    //     }
    //   }
    // },
    // XÓA TOÀN BỘ ĐIỂM UỐN - CÁP
    // mniXoaToanBoDU_Click(cap){
    //   let idx = this.bocuc.link.findIndex(x => x.li.toString() === cap.id.toString())
    //   if (idx > -1){
    //     this.bocuc.link[idx].points = []
    //     this.$refs.GianDo.mniXoaToanBoDU_Click(cap)
    //   }
    // },
    // XÓA TOÀN BỘ ĐIỂM UỐN - GIẢN ĐỒ
    // mniXoaTBDU_GianDo_Click(){
    //   this.$bvModal.msgBoxConfirm("Bạn có muốn xóa toàn bộ điểm uốn trên giản đồ không?",
    //     {
    //       title: "VNPT",
    //       centered: true,
    //       size: "sm",
    //       okTitle: 'Đồng ý',
    //       cancelTitle: 'Không đồng ý',
    //     }).then((value) => {
    //     if (value){
    //       this.bocuc.link = this.bocuc.link.map(item => ({...item, points:[]}))
    //       this.$refs.GianDo.mniXoaTBDU_GianDo_Click()
    //     }
    //     else{
    //       return
    //     }
    //   })
    // },
    // CHUYỂN KIỂU CÁP - GIẢN ĐỒ
    async mniChuyenKieuCap_Click(...args){
      const [KIEU, CAP_ID] = args
      if (KIEU !== null && CAP_ID !== null && this.selected.TUYENCAP_ID !== null){
        let isCapNhatKieuCap = false , isCapNhatTuyenCapTTVT = false
        try {
          // CAPNHAT_KIEUCAP
          await this.$root.context.post("web-cabman/mang-truyen-dan/capnhat-kieucap", {KIEUCAP_ID:KIEU, CAP_ID:CAP_ID})
            .then(rs =>{
              if (rs.error === "200" && rs.error_code === "BSS-00000000" && rs.data.result === true){
                isCapNhatKieuCap = true
              }
            }).catch(e => {
              this.$root.toastError("Có lỗi xảy ra khi cập nhật kiểu cáp")
              throw e
            })
          // CAPNHAT_TUYEN_CAP_TTVT
          await this.$root.context.post("web-cabman/mang-truyen-dan/capnhat-tuyencap-ttvt",{KIEU:KIEU, CAP_ID:CAP_ID, TUYENCAP_ID:this.selected.TUYENCAP_ID})
            .then(rs =>{
              if (rs.error === "200" && rs.error_code === "BSS-00000000" && rs.data.result === true){
                isCapNhatTuyenCapTTVT = true
              }
            }).catch(e =>{
              this.$root.toastError("Có lỗi xảy ra khi cập nhật tuyến cáp TTVT")
              throw e
            })
          //THAY ĐỔI TRÊN GIẢN ĐỒ
          if (isCapNhatKieuCap && isCapNhatTuyenCapTTVT){
            this.$refs.GianDo.mniChuyenKieuCap_Click(KIEU)
          }
        }catch (e){
          console.log(e)
        }
      }
    },
    // XÓA CÁP
    mniXoaCap_Click(cap){
      this.$bvModal.msgBoxConfirm("Bạn có thực sự muốn xóa cáp ["+ cap.Name +"] không ?",
        {
          title: "Xác nhận hành động",
          centered: true,
          size: "sm",
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy',
        }).then(async (value) => {
        if (value) {
          try {
            this.loading(true)
            let xoacap = new Object()
            xoacap.CAP_ID = parseInt(cap.id,10)
            xoacap.TUYENCAP_ID = this.selected.TUYENCAP_ID
            await this.$root.context.post("web-cabman/mang-truyen-dan/xoa-cap-gd",xoacap)
            .then(res =>{
              if (res.error === "200" && res.data.result === true){
                this.$root.toastSuccess("Xóa cáp thành công")
                this.$refs.GianDo.mniXoaCap_Click(cap)
              }
            })
          } catch (e) {
            if (e.response && e.response.data.message_detail){
              this.$root.toastError(e.response.data.message_detail)
            }
          } finally {
            this.loading(false)
          }
        } else {
          return
        }
      })
    },
    async CAPNHAT_BOCUC_GIANDO(layout){
      await this.$root.context.post("web-cabman/mang-truyen-dan/capnhat-bocuc-giando",{BOCUC:layout, TUYENCAP_ID:this.selected.TUYENCAP_ID})
      .then(async res => {
        if (res.error === "200" && res.data.result === true) {
          // this.$root.toastError("Cập nhật bố cục thành công")
          // const BOCUC_CUSTOM = 8
          // if (layout !== BOCUC_CUSTOM){
          //   this.$refs.GianDo.changeLayout("ComplexHierarchicalTree")
          // }else {
          //   this.$refs.GianDo.changeLayout(null)
          // }
          await this.NAP_GIANDO_TRUYENDAN()
        }
      }).catch(e =>this.$root.toastError("Lỗi cập nhật bố cục giản đồ"))
    },
    mniLuuBoCuc_Click(){
      this.$bvModal.msgBoxConfirm("Bạn có muốn tiếp tục lưu bố cục của giản đồ không",
        {
          title: "VNPT",
          centered: true,
          size: "sm",
          okTitle: 'Đồng ý',
          cancelTitle: 'Không đồng ý',
        }).then(async (value) => {
        if (value) {
          let bw = new BinaryWriter('little', 'base64')
          // Version
          bw.WriteBytes([1, 0, 0, 0])
          // Translate
          let translate = this.$refs.GianDo.getCurrentOffset()
          bw.WriteInt32(translate.x)
          bw.WriteInt32(translate.y)
          // Zoom
          bw.WriteFloat(this.currentZoomScale.CurrentZoom)
          // Point
          let pl = this.$refs.GianDo.GetNodes()
          console.log("length write", pl.length)
          bw.WriteInt32(pl.length)
          let id
          for (let p of pl) {
            switch (p.Group) {
              case "TRAM_TB":
                bw.WriteBytes([0])
                id = parseInt(p.id.split("_")[2], 10)
                break
              case "KETCUOI":
                bw.WriteBytes([1])
                id = parseInt(p.id.split("_")[1], 10)
                break
              default:
                bw.WriteBytes([255])
                continue
            }
            // console.log(id,p.offsetX, p.offsetY)
            bw.WriteInt64(id)
            bw.WriteInt32(p.offsetX)
            bw.WriteInt32(p.offsetY)
            let badge = 0;
            bw.WriteBytes([badge])
          }
          // Link
          let ll = this.$refs.GianDo.GetConnectors()
          bw.WriteInt32(ll.length)
          for (let i = 0 ; i <ll.length ; i++) {
            let l= ll[i]
            bw.WriteInt64(parseInt(l.id, 10))
            if (l.TextPoint) {
              bw.WriteInt32(l.TextPoint.x)
              bw.WriteInt32(l.TextPoint.y)
            }else{
              bw.WriteInt32(0)
              bw.WriteInt32(0)
            }
            bw.WriteInt32(0) //l.CurvePoint1.Position.X
            bw.WriteInt32(0) //l.CurvePoint1.Position.Y
            bw.WriteInt32(0) //l.CurvePoint2.Position.X
            bw.WriteInt32(0) //l.CurvePoint2.Position.Y
            bw.WriteInt32(l.segments.length - 1)
            for (let i = 0; i < l.segments.length - 1; i++) {
              bw.WriteInt32(l.segments[i].point.x)
              bw.WriteInt32(l.segments[i].point.y)
            }
          }
          console.log(bw.ByteBuffer)
          // update bo cuc gian do
          let bocucObject = {}
          bocucObject.TUYENCAP_ID = this.selected.TUYENCAP_ID
          bocucObject.DULIEU_BOCUC = bw.ByteBuffer
          await this.$root.context.post("web-cabman/mang-truyen-dan/capnhat-dulieu-bocuc-giando", bocucObject)
          .then(async res => {
            if (res.error === "200" && res.data.result === true) {
              this.$root.toastSuccess("Lưu bố cục thành công")
              await this.NAP_GIANDO_TRUYENDAN()
            }
          }).catch(e =>this.$root.toastError("Có lỗi xảy ra khi lưu bố cục giản đồ"))
          // let bocuc = {link: [], zoom: null, version: null, translate: null, points: []}
          // let br = new BinaryReader(bw.ByteBuffer, 'little', 'base64')
          // bocuc.version = br.ReadBytes(4)
          // var x = br.ReadInt32()
          // var y = br.ReadInt32()
          // bocuc.translate = {
          //   x: x,
          //   y: y
          // }
          // var z = br.ReadFloat()
          // bocuc.zoom = z
          // // Point
          // var np = br.ReadInt32()
          // // console.log("length read",np)
          // for (let index = 0; index < np; index++) {
          //   var cc = br.ReadBytes(1)
          //   var pt
          //   // console.log('SP_LAY_BOCUC_GIANDO cc', cc[0])
          //   switch (cc[0]) {
          //     case 0:
          //       pt = 'TRAM_TB'
          //       break
          //     case 1:
          //       pt = 'KETCUOI'
          //       break
          //     default:
          //       continue
          //   }
          //   var pi = br.ReadInt64()
          //   var px = br.ReadInt32()
          //   var py = br.ReadInt32()
          //   var pd = br.ReadBytes(1)
          //   bocuc.points.push(
          //     {
          //       pt: pt,
          //       pi: pi,
          //       px: px,
          //       py: py,
          //       pd: pd
          //     }
          //   )
          // }
          // // // console.log('SP_LAY_BOCUC_GIANDO bocuc', this.bocuc)
          // // // Link
          // var nl = br.ReadInt32()
          // for (let index = 0; index < nl; index++) {
          //   var li = br.ReadInt64();
          //   // var lnk = this.$refs.GianDo.TimLink(li)
          //   let textPoint_x = br.ReadInt32();
          //   let textPoint_y = br.ReadInt32();
          //   let curvePoint1_x = br.ReadInt32();
          //   let curvePoint1_y = br.ReadInt32();
          //   let curvePoint2_x = br.ReadInt32();
          //   let curvePoint2_y = br.ReadInt32();
          //   let pointList = []
          //   // pointList.push({x:null,y:null}) // A
          //   var ln = br.ReadInt32();
          //   for (let j = 0; j < ln; j++) {
          //     var lx = br.ReadInt32();
          //     var ly = br.ReadInt32();
          //     pointList.push({x: lx, y: ly})
          //   }
          //   // pointList.push({x:null,y:null}) // B
          //   bocuc.link.push({
          //     li: li,
          //     textPoint: {x: textPoint_x, y: textPoint_y},
          //     curvePoint1: {x: curvePoint1_x, y: curvePoint1_y},
          //     curvePoint2: {x: curvePoint2_x, y: curvePoint2_y},
          //     points: pointList
          //   })
          // }
          // console.log("bocuc sau khi luu", bocuc)
        } else {
          return
        }
      })
    },
    mniLamTuoi_Click(){
      this.NAP_GIANDO_TRUYENDAN()
    },
    // TẠO CÁP TỚI
    diaGianDo_ObjectAdded(...args){
      console.log('diaGianDo_ObjectAdded', args)
      this.capObject = {}
      this.A = {}
      this.B = {}
      const [TYPE_OBJ,A,B] = args
      // save
      this.A = A
      this.B = B
      if (TYPE_OBJ === "Connector"){
        // Thêm cáp từ trạm đến kết cuối (Lớp mạng cáp 1: Cáp gốc)
        if (A.Group === "TRAM_TB" && B.Group === "KETCUOI"){
          this.capObject.KETCUOI_N_ID = -1
          this.capObject.KETCUOI_D_ID = parseInt(B.id.split("KETCUOI_")[1])
          this.capObject.DONVI_ID = parseInt(A.id.split("TRAM_TB_")[1])
          this.capObject.TOQL_ID = -1
          this.capObject.CAPGOC_ID = -1
          this.capObject.NHOMLC_ID = 1
          this.capObject.LOPMC_ID = 1
          this.capObject.KIEUCAP_ID = 1
          this.capObject.DONVI_TD_ID = -1 // tự bổ sung thêm để dễ kiểm tra
          this.$bvModal.show("QLCap")
          // RESULT is QLCapIsOk
        }
        // Thêm cáp từ kết cuối đến kết cuối (Lớp mạng cáp 2: Cáp trung gian)
        if (A.Group === "KETCUOI" && B.Group === "KETCUOI"){
          this.capObject.KETCUOI_N_ID = parseInt(A.id.split("KETCUOI_")[1])
          this.capObject.KETCUOI_D_ID = parseInt(B.id.split("KETCUOI_")[1])
          this.capObject.DONVI_ID = -1
          this.capObject.TOQL_ID = -1
          this.capObject.CAPGOC_ID = -1
          this.capObject.NHOMLC_ID = 1
          this.capObject.LOPMC_ID = 2
          this.capObject.KIEUCAP_ID = 1
          this.capObject.DONVI_TD_ID = -1 // tự bổ sung thêm để dễ kiểm tra
          this.$bvModal.show("QLCap")
          // RESULT is QLCapIsOk
        }
        // Thêm cáp từ kết cuối đến trạm (Lớp mạng cáp 3: Cáp truyền dẫn)
        if (A.Group === "KETCUOI" && B.Group === "TRAM_TB"){
          this.capObject.KETCUOI_N_ID = parseInt(A.id.split("KETCUOI_")[1])
          this.capObject.KETCUOI_D_ID = -1
          this.capObject.DONVI_ID = -1
          this.capObject.TOQL_ID = -1
          this.capObject.CAPGOC_ID = -1
          this.capObject.NHOMLC_ID = 1
          this.capObject.LOPMC_ID = 3
          this.capObject.KIEUCAP_ID = 1
          this.capObject.DONVI_TD_ID = parseInt(B.id.split("TRAM_TB_")[1])
          this.$bvModal.show("QLCap")
          // RESULT is QLCapIsOk
        }
        // Thêm cáp từ trạm 1 đến trạm 2 (Lớp mạng cáp 4: Cáp gốc liên trạm)
        if (A.Group === "TRAM_TB" && B.Group === "TRAM_TB"){
          this.capObject.KETCUOI_N_ID = -1
          this.capObject.KETCUOI_D_ID = -1
          this.capObject.DONVI_ID = parseInt(A.id.split("TRAM_TB_")[1])
          this.capObject.TOQL_ID = -1
          this.capObject.CAPGOC_ID = -1
          this.capObject.NHOMLC_ID = 1
          this.capObject.LOPMC_ID = 4
          this.capObject.KIEUCAP_ID = 1
          this.capObject.DONVI_TD_ID = parseInt(B.id.split("TRAM_TB_")[1])
          this.$bvModal.show("QLCap")
          // RESULT is QLCapIsOk
        }
      }else if (TYPE_OBJ === "Node"){
        // Tạo TRAM_TB
        // A: offset diagram click, B: null, TYPE_OBJ = "Node"

        // Giản đồ
        let tram_tb = this.$refs.GianDo.TaoPoint(
          'TRAM_TB_' + this.THONGTIN_TRAM_TB.DONVI_ID.toString(),
          this.THONGTIN_TRAM_TB.TEN_DV + '',
          "TRAM_THIET_BI" + '',
          this.THONGTIN_TRAM_TB.TEN_HT + '')
        tram_tb.offsetX = A.x
        tram_tb.offsetY = A.y
        tram_tb.Position = A
        tram_tb.Group = "TRAM_TB"
        tram_tb.ImageKey = "TRAM_THIET_BI"
        tram_tb.Name = this.THONGTIN_TRAM_TB.TEN_DV
        tram_tb.DisplayName = this.THONGTIN_TRAM_TB.TEN_HT
        tram_tb.Size = { width: 40, height: 40 }
        tram_tb.AllowMove = false
        tram_tb.RootId = -1
        this.$refs.GianDo.CapNhatPoint(tram_tb)
        this.MAP_PUSH(tram_tb.Group+'#'+ this.THONGTIN_TRAM_TB.DONVI_ID, -1);

        // Bản đồ
        let lat = this.THONGTIN_TRAM_TB.VIDO
        let lng = this.THONGTIN_TRAM_TB.KINHDO
        let m_tram_tb = this.$refs.BanDo.TaoNode()
        m_tram_tb.Group = 'TRAM_TB'
        m_tram_tb.id = 'TRAM_TB_' + this.THONGTIN_TRAM_TB.DONVI_ID
        m_tram_tb.id2 = this.THONGTIN_TRAM_TB.DONVI_ID
        m_tram_tb.ImageKey = "TRAM_THIET_BI"
        m_tram_tb.SetIcon(m_tram_tb.ImageKey)
        m_tram_tb.Label = this.THONGTIN_TRAM_TB.TEN_DV
        m_tram_tb.Tooltip = this.THONGTIN_TRAM_TB.TEN_HT
        m_tram_tb.Tag = tram_tb
        m_tram_tb.AllowMove = false
        m_tram_tb.RootId = -1
        m_tram_tb.Position = { lat: Math.abs(parseFloat(lat)) > 90 ? 0 : parseFloat(lat), lng: Math.abs(parseFloat(lng)) > 180 ? 0 : parseFloat(lng) }

        this.$refs.BanDo.FitBounds()
        this.$refs.GianDo.DoLayout()
        // remove TRAM_TB
        let dsTRAMTB = [...this.data.dsTRAMTB.filter(item => item.DONVI_ID !== this.THONGTIN_TRAM_TB.DONVI_ID)]
        this.setState({key: 'dsTRAMTB', value: dsTRAMTB})
        // reset flag, thong tin tramtb
        this.ADD_TRAM_TB_FLAG = false
        this.THONGTIN_TRAM_TB = null
      }
    },
    async QLCapIsOk(...args) {
      const [TYPE, DATA] = args
      if (TYPE === "INSERT") {
        // Cập nhật tuyến
        await this.$root.context.post("web-cabman/mang-truyen-dan/capnhat-tuyencap-ttvt", {
          KIEU: 1,
          CAP_ID: DATA.CAP_ID,
          TUYENCAP_ID: this.selected.TUYENCAP_ID
        }).catch(e => {
            this.$root.toastError("Có lỗi xảy ra khi cập nhật tuyến cáp TTVT")
          })
        // Giản đồ
        let cap = this.$refs.GianDo.TaoLink(DATA.CAP_ID.toString(), this.A, this.B, [])
        cap.Group = 'CAP'

        await this.$root.context.get("web-cabman/mang-truyen-dan/lay-ten-cap",{capId:DATA.CAP_ID,kieu:1})
        .then(res =>{
          if (res.error === "200")
            cap.Name = res.data.RESULT
        }).catch(e =>{})
        await this.$root.context.get("web-cabman/mang-truyen-dan/lay-ten-cap",{capId:DATA.CAP_ID,kieu:1})
        .then(res =>{
          if (res.error === "200")
            cap.DisplayName = res.data.RESULT
        }).catch(e =>{})

        cap.Width = 1
        cap.SelectedWidth = 2
        cap.RootId = DATA.CAPGOC_ID
        cap.LOPMC_ID = DATA.LOPMC_ID;
        cap.STYLE_KIEU_DC(DATA.KIEUDC_ID)
        cap.STYLE_NHOM_LC(DATA.NHOMLC_ID)
        cap.STYLE_KIEU_CAP(DATA.KIEUCAP_ID)
        this.$refs.GianDo.CapNhatLink(cap)

        // Bản đồ
        let m_ketcuoi_n = this.$refs.BanDo.TimNode(this.A.id)
        let m_ketcuoi_d = this.$refs.BanDo.TimNode(this.B.id)
        let m_cap = this.$refs.BanDo.TaoRoute()
        m_cap.Group = "CAP"
        m_cap.id = DATA.CAP_ID
        m_cap.ThemPosition(m_ketcuoi_n.id, m_ketcuoi_n.Position)
        m_cap.ThemPosition(m_ketcuoi_d.id, m_ketcuoi_d.Position)
        m_cap.A = m_ketcuoi_n
        m_cap.B = m_ketcuoi_d
        m_cap.Tag = cap
        m_cap.Name = DATA.KYHIEU
        m_cap.Layer = DATA.PHANCAP_ID
        m_cap.RootId = DATA.CAPGOC_ID
        let mau = this.data.dsPHANCAP.filter(item => item.PHANCAP_ID === DATA.PHANCAP_ID)[0].MAU
        m_cap.BackColor = mau
        this.$refs.BanDo.FitBounds()
      }
      // SỬA THÔNG TIN CÁP
      if (TYPE === "UPDATE"){
        // Giản đồ
        let link = this.$refs.GianDo.TimLink2(DATA.CAP_ID)
        console.log("QLCapIsOk", link)
        await this.$root.context.get("web-cabman/mang-truyen-dan/lay-ten-cap",{capId:DATA.CAP_ID,kieu:0})
          .then(res =>{
            if (res.error === "200")
              link.Name = res.data.RESULT
          }).catch(e =>{})
        await this.$root.context.get("web-cabman/mang-truyen-dan/lay-ten-cap",{capId:DATA.CAP_ID,kieu:1})
          .then(res =>{
            if (res.error === "200")
              link.DisplayName = res.data.RESULT
          }).catch(e =>{})
        if (link.DisplayName)
          link.annotations[0].content = link.DisplayName
        this.$refs.GianDo.DataBind()
        this.$refs.GianDo.CapNhatBanner("connectors")

        // Bản đồ
        let route = this.$refs.BanDo.TimRoute(DATA.CAP_ID)
        if (route) route.Name = DATA.KYHIEU
      }
    },
    async SUA_THUOCTINH_KETCUOI(ketcuoi) {
      console.log("ketcuoi:",ketcuoi)
      // Giản đồ
      let node = this.$refs.GianDo.TimPoint2("KETCUOI_" + ketcuoi.KETCUOI_ID)
      console.log("suathuoctinhketcuoi",node)
      await this.$root.context.get("web-cabman/mang-truyen-dan/lay-ten-ketcuoi", {
        ketcuoiId: ketcuoi.KETCUOI_ID,
        kieu: 0
      })
        .then(res => {
          if (res.error === "200") {
            node.Name = res.data.RESULT
          }
        }).catch(e => {
        })
      await this.$root.context.get("web-cabman/mang-truyen-dan/lay-ten-ketcuoi", {
        ketcuoiId: ketcuoi.KETCUOI_ID,
        kieu: 1
      })
        .then(res => {
          if (res.error === "200") {
            node.DisplayName = res.data.RESULT
          }
        }).catch(e => {
        })
      node.annotations[0].content = node.Name
      node.tooltip = {
        content: node.DisplayName,
        position: 'BottomCenter',
        relativeMode: 'Object'
      }
      // this.api_execute({ data: { P_PHANVUNG_ID: parseInt(this.$root.token.getPhanVungID()+''), P_KETCUOI_ID: ketcuoi.KETCUOI_ID}, function: 'CABMAN.PKG_MANG_TRUYEN_DAN.SP_LAY_ICON_KETCUOI' }).then((reponse) => {
      //   if (reponse.data && reponse.data.length > 0) {
      //     node.shape.source =
      //       '/static/icons/icon100x100/' + reponse.data[0].RESULT + '.jpg'
      //     node.ImageKey = reponse.data[0].RESULT
      //   }
      // })
      await this.$root.context.get('web-cabman/mang-truyen-dan/lay-icon-ketcuoi', {
        ketCuoiId: ketcuoi.KETCUOI_ID
      }).then((reponse) => {
        if (reponse.data && reponse.data.length > 0) {
          node.shape.source =
            '/static/icons/icon100x100/' + reponse.data[0].RESULT + '.jpg'
          node.ImageKey = reponse.data[0].RESULT
        }
      })
      this.$refs.GianDo.DataBind()
      this.$refs.GianDo.CapNhatBanner("nodes")

      // Bản đồ
      let marker = this.$refs.BanDo.TimNode("KETCUOI_" + ketcuoi.KETCUOI_ID)
      marker.ImageKey = node.ImageKey
      marker.SetIcon(marker.ImageKey)
      marker.Label = ketcuoi.KYHIEU
      marker.Tooltip = node.DisplayName.replace("#", "\r\n");
      this.$refs.BanDo.FitBounds()
    },
    SUA_THUOCTINH_TRAM_TB(...args){
      // Bản đồ
      const [ID, POSITTION] = args
      let marker = this.$refs.BanDo.TimNode(ID)
      marker.Position = POSITTION
      this.$refs.BanDo.FitBounds()
      // reload routes
      this.$refs.BanDo.RefreshRoutes(marker.id)
    },
    MAP_CLEAR() {
      this.savePoint = []
    },
    MAP_PUSH(group_id, capgoc_id){
      if (this.savePoint.filter(item => item.key === group_id).length === 0){
        this.savePoint.push({key:group_id, value:[]})
      }
      let idx = this.savePoint.findIndex(item => item.key === group_id)
      this.savePoint[idx].value.push(capgoc_id)
    },
    MAP_POP(group_id, capgoc_id){
      if (this.savePoint.filter(item => item.key === group_id).length === 0){
        return true;
      }
      let idx = this.savePoint.findIndex(item => item.key === group_id)
      this.savePoint[idx].value = this.savePoint[idx].value.filter(item => item !== capgoc_id)
      return this.savePoint[idx].value.length === 0;
    },
    async capgoc_rowSelected(args) {
      await this.NAP_GIANDO_CABMAN(args.data.CAP_ID)
    },
    capgoc_rowDeselected(args){
      this.XOA_NAP_GIANDO_CABMAN(args.data.CAP_ID)
    },
    showInforSelected(clickedItem){
      console.log("showInforSelected",clickedItem)
      if (clickedItem){
        this.chuthich1.visible = true
        this.chuthich2.visible = true
        if (clickedItem.propName === "nodes"){
          this.ditu1.visible = false
          this.ditu2.visible = false
          this.diden1.visible = false
          this.diden2.visible = false
          switch (clickedItem.Group){
            case "TRAM_TB":
              this.chuthich1.lblChuThich1 = "Trạm thiết bị"
              this.chuthich2.lblChuThich2 = clickedItem.DisplayName ? clickedItem.DisplayName.replace("#", "  |  ") : ""
              break
            case "KETCUOI":
              this.chuthich1.lblChuThich1 = "Kết cuối"
              this.chuthich2.lblChuThich2 = clickedItem.DisplayName ? clickedItem.DisplayName.replace("#", "  |  ") : ""
              break
          }
        }
        if (clickedItem.propName === "connectors"){
          switch (clickedItem.Group){
            case "CAP":
              this.chuthich1.lblChuThich1 = "Cáp"
              this.chuthich2.lblChuThich2 = clickedItem.DisplayName ? clickedItem.DisplayName.replace("#", "  |  ") : ""

              this.ditu1.visible = true
              this.ditu2.visible = true
              let A = this.$refs.GianDo.TimPoint2(clickedItem.sourceID)
              let B = this.$refs.GianDo.TimPoint2(clickedItem.targetID)
              console.log("A",A)
              console.log("B",B)
              switch (A.Group)
              {
                case "TRAM_TB":
                  this.ditu1.lblDiTu1 = "Đi từ Trạm thiết bị";
                  this.ditu2.lblDiTu2 = A.DisplayName ? A.DisplayName.replace("#", "  |  ") : ""
                  break;
                case "KETCUOI":
                  this.ditu1.lblDiTu1 = "Đi từ Kết cuối";
                  this.ditu2.lblDiTu2 = A.DisplayName ? A.DisplayName.replace("#", "  |  ") : ""
                  break;
              }

              this.diden1.visible = true;
              this.diden2.visible = true;
              switch (B.Group)
              {
                case "TRAM_TB":
                  this.diden1.lblDiDen1 = "đến Trạm thiết bị";
                  this.diden2.lblDiDen2 = B.DisplayName ? B.DisplayName.replace("#", "  |  ") : ""
                  break;
                case "KETCUOI":
                  this.diden1.lblDiDen1 = "đến Kết cuối";
                  this.diden2.lblDiDen2 = B.DisplayName ? B.DisplayName.replace("#", "  |  ") : ""
                  break;
              }
              break;
          }
        }
      }else {
        this.chuthich1.visible = false
        this.chuthich2.visible = false

        this.ditu1.visible = false
        this.ditu2.visible = false
        this.diden1.visible = false
        this.diden2.visible = false
      }
    },
    fitToPageGD(){
      this.$refs.GianDo.FitToPage()
    },
    zoomInGD(){
      this.$refs.GianDo.zoomIn()
    },
    zoomOutGD(){
      this.$refs.GianDo.zoomOut()
    },
    btnShowHiddenDoiTuong(){
      this.isShowDoiTuong = !this.isShowDoiTuong
    },
    btnShowHiddenLienKet(){
      this.isShowLienKet = !this.isShowLienKet
    },
    btnShowHiddenTitle(){
      this.isShowTitle = !this.isShowTitle
    },
    btnShowHiddenLayer(){
      this.isShowLayer = !this.isShowLayer
    },
    btnShowHiddenGridLine(){
      this.isShowGridLine = !this.isShowGridLine
    },
    timkiemNode(node){
      this.txttimkiem = node.Name;
      this.$refs.GianDo.selectNode(node.properties.id)
    },
    timkiemConnector(connector){
      this.txttimkiem = connector.Name;
      this.$refs.GianDo.selectConnector(connector.properties.id)
    },
    getCurrentZoomScale(args){
      this.currentZoomScale = args
    },
    btnInGD(){
      this.$bvModal.show("popupXemTruocKhiIn")
    },
    recordDoubleClickTRAM_TB(args){
      if (this.showtabGianDo){
        this.ADD_TRAM_TB_FLAG = true
        this.THONGTIN_TRAM_TB = args.rowData
      }
    },
  },
  watch: {
    showtabGianDo (val) {
      if(!val)
        // this.$refs.BanDo.updateSized();
        this.$refs.BanDo && this.$refs.BanDo.updateSized();
    },
    'selected.TTVT_ID' (val) {
      // console.log('watch selected.TTVT_ID', val)
      // this.api_execute({ data: { V_PHANVUNG_ID: this.$root.token.getPhanVungID(), V_TTVT_ID: val, V_NHANVIEN_ID: this.$root.token.getNhanVienID() }, function: 'CABMAN.PKG_SINHDN.SP_NAP_DS_TOVT' }).then((reponse) => {
      //   if (reponse.data) {
      //     console.log('dsTOVT', reponse)
      //     this.setState({key: 'dsTOVT', value: reponse.data})
      //     if (this.data.dsTOVT.length > 0) {
      //       this.selected.TOVT_ID = this.data.dsTOVT[0].DONVI_ID
      //     }
      //   }
      // })
      this.$root.context.get('web-cabman/mang-truyen-dan/lay-danhsach-tovt', {
        ttvtId: val
      }).then(reponse =>{
        if (reponse.data) {
          // console.log('dsTOVT', reponse)
          this.setState({key: 'dsTOVT', value: reponse.data})
          if (this.data.dsTOVT.length > 0) {
            this.selected.TOVT_ID = this.data.dsTOVT[0].DONVI_ID
          }
        }
      })
    },
    'selected.TTVT_ID_2' (val) {
      // console.log('watch selected.TTVT_ID_2', val)
      // this.api_execute({ data: { V_PHANVUNG_ID: this.$root.token.getPhanVungID(), V_TTVT_ID: val, V_NHANVIEN_ID: this.$root.token.getNhanVienID() }, function: 'CABMAN.PKG_SINHDN.SP_NAP_DS_TOVT' }).then((reponse) => {
      //   if (reponse.data) {
      //     console.log('dsTOVT', reponse)
      //     this.setState({key: 'dsTOVT_2', value: reponse.data})
      //     if (this.data.dsTOVT_2.length > 0) {
      //       this.selected.TOVT_ID_2 = this.data.dsTOVT_2[0].DONVI_ID
      //     }
      //   }
      // })
      this.$root.context.get('web-cabman/mang-truyen-dan/lay-danhsach-tovt', {
        ttvtId: val
      }).then(reponse =>{
        if (reponse.data) {
          // console.log('dsTOVT', reponse)
          this.setState({key: 'dsTOVT_2', value: reponse.data})
          if (this.data.dsTOVT_2.length > 0) {
            this.selected.TOVT_ID_2 = this.data.dsTOVT_2[0].DONVI_ID
          }
        }
      })
    },
    'selected.TOVT_ID' (val) {
      // console.log('watch selected.TOVT_ID', val)
      // this.api_execute({ data: { V_PHANVUNG_ID: this.$root.token.getPhanVungID(), V_TUYENCAP_ID: this.selected.TUYENCAP_ID, V_TOVT_ID: val }, function: 'CABMAN.PKG_SINHDN.SP_LAY_DS_TRAMTB_GIANDO' }).then((reponse) => {
      //   if (reponse.data) {
      //     console.log('dsTRAMTB', reponse)
      //     this.setState({key: 'dsTRAMTB', value: reponse.data})
      //   }
      // })
      this.$root.context.get('web-cabman/mang-truyen-dan/lay-danhsach-tramtb-giando', {
        tuyenCapId: this.selected.TUYENCAP_ID ? this.selected.TUYENCAP_ID : 0,
        tovtId: val
      }).then(reponse =>{
        if (reponse.data) {
          console.log('dsTRAMTB', reponse)
          this.setState({key: 'dsTRAMTB', value: reponse.data})
        }
      })
    },
    'selected.TOVT_ID_2' (val) {
      // console.log('watch selected.TOVT_ID_2', val)
      // this.api_execute({ data: { V_PHANVUNG_ID: this.$root.token.getPhanVungID(), P_TOVT_ID: val }, function: 'CABMAN.PKG_SINHDN.SP_LAY_DS_TRAMTB' }).then((reponse) => {
      //   if (reponse.data) {
      //     console.log('dsTRAMTB', reponse)
      //     this.setState({key: 'dsTRAMTB_2', value: reponse.data})
      //     if (this.data.dsTRAMTB_2.length > 0) {
      //       this.selected.TRAMTB_ID = this.data.dsTRAMTB_2[0].DONVI_ID
      //     }
      //   }
      // })
      this.$root.context.get('web-cabman/mang-truyen-dan/lay-danhsach-tramtb', {
        tovtId: val
      }).then(response => {
        if (response.data) {
          console.log('dsTRAMTB', response)
          this.setState({key: 'dsTRAMTB_2', value: response.data})
          if (this.data.dsTRAMTB_2.length > 0) {
            this.selected.TRAMTB_ID = this.data.dsTRAMTB_2[0].DONVI_ID
          }
        }
      })
    },
    'selected.TRAMTB_ID' (val) {
      // console.log('watch selected.TRAMTB_ID', val)
      // this.api_execute({ data: { V_PHANVUNG_ID: this.$root.token.getPhanVungID(), P_TRAMTB_ID: val }, function: 'CABMAN.PKG_SINHDN.SP_LAY_DS_CAPGOC_GIANDO' }).then((reponse) => {
      //   if (reponse.data) {
      //     console.log('dsCAPGOC', reponse)
      //     this.setState({key: 'dsCAPGOC', value: reponse.data})
      //     // if (this.data.dsTRAMTB_2.length > 0) {
      //     //   this.selected.TRAMTB_ID = this.data.dsTRAMTB_2[0].DONVI_ID
      //     // }
      //   }
      // })
      this.$root.context.get('web-cabman/mang-truyen-dan/lay-danhsach-capgoc-giando', {
        tramtbId: val
      }).then(response => {
        if (response.data) {
          console.log('dsCAPGOC', response)
          this.setState({key: 'dsCAPGOC', value: response.data})
          // if (this.data.dsTRAMTB_2.length > 0) {
          //   this.selected.TRAMTB_ID = this.data.dsTRAMTB_2[0].DONVI_ID
          // }
        }
      })
    },
    isShowDoiTuong:function (val){
      this.$refs.GianDo.showDoiTuong(val)
    },
    isShowLienKet: function (val){
      this.$refs.GianDo.showLienKet(val)
    },
    isShowTitle: function(val){
      this.$refs.GianDo.showTitle(val)
    },
    isShowGridLine: function (val){
      this.$refs.GianDo.showGridLine(val)
    },
    isSearch: function (val){
      if (val % 2 == 1){
        this.dsTramTB = this.$refs.GianDo.GetNodes().filter(item => item.Group === "TRAM_TB")
        this.dsTramTBSearch = [...this.dsTramTB]
        this.dsKetCuoi = this.$refs.GianDo.GetNodes().filter(item => item.Group === "KETCUOI")
        this.dsKetCuoiSearch = [...this.dsKetCuoi]
        this.dsCap = this.$refs.GianDo.GetConnectors()
        this.dsCapSearch = [...this.dsCap]
      }else{
        this.dsTramTB = []
        this.dsTramTBSearch = []
        this.dsKetCuoi = []
        this.dsKetCuoiSearch = []
        this.dsCap = []
        this.dsCapSearch = []
      }
    },
    txttimkiem: function(val){
      switch (this.searchCategory){
        case "TRAM_TB":
          try{
            this.dsTramTBSearch =[...this.dsTramTB]
            if(val !== ""){
              let sleep = ms => new Promise(resolve => setTimeout(resolve, 100))
              this.dsTramTBSearch = this.dsTramTBSearch.filter(x=>x.DisplayName.
              toLowerCase().includes(val.toLowerCase()))
              this.showDataSearch=true
            }
          }catch(e){
            this.dsTramTBSearch =[...this.dsTramTB]
          }
          break
        case "KETCUOI":
          try{
            this.dsKetCuoiSearch =[...this.dsKetCuoi]
            if(val !== ""){
              let sleep = ms => new Promise(resolve => setTimeout(resolve, 100))
              this.dsKetCuoiSearch = this.dsKetCuoiSearch.filter(x=>x.Name.
              toLowerCase().includes(val.toLowerCase()))
              this.showDataSearch=true
            }
          }catch(e){
            this.dsKetCuoiSearch =[...this.dsKetCuoi]
          }
          break
        case "CAP":
          try{
            this.dsCapSearch = [...this.dsCap]
            if(val !== ""){
              let sleep = ms => new Promise(resolve => setTimeout(resolve, 100))
              this.dsCapSearch = this.dsCapSearch.filter(
                x=>
                  x.Name.toLowerCase().includes(val.toLowerCase()) ||
                  (x.id != undefined && x.id == val)
              )
              this.showDataSearch=true
            }
          }catch(e){
            this.dsCapSearch =[...this.dsCap]
          }
          break
      }
    }
  },
  async created () {
    this.createApiManager()
    this.clearState()
    this.loadData()
  },
  async mounted () {
    this.$refs.BanDo.DuyChuyenDenViTri({lat: 16.04686891354617, lng: 108.19221059008628})
  }
}
</script>
<style>
.quan-ly-truyen-dan .sitemap-chart-canvas .box-search {
  position: absolute;
  max-width: 600px;
  width: 100%;
  background: #ffffff;
  border: 1px solid #e0e0e0;
  left: 0px;
  right: auto;
  margin: auto;
  padding: 10px;
  top: 10px;
  box-shadow: 0px 4px 4px rgb(177 177 177 / 15%);
  border-radius: 4px;
}
.quan-ly-truyen-dan .image-connector-search {
  position: absolute;
  top: 0px;
  text-align: center;
  width: 30px;
  height: 30px;
  line-height: 30px;
  bottom: 0px;
  margin: auto;
  left: 0px;
  font-size: 20px;
}
.quan-ly-truyen-dan .sitemap-chart-canvas .dropdown-search.dropdown-icon a {
  position: relative;
  padding-top: 0;
  padding-bottom: 0;
}
.quan-ly-truyen-dan .sitemap-chart-canvas .zoom-actions-bottom {
  position: absolute;
  bottom: 80px !important;
  right: 10px;
  width: 36px;
}
.e-fltrTemp {
  text-align: center !important;
}
input[type='radio'] {
  text-align: center !important;
}
.e-filterbarcell {
  padding: 5px 7px !important;
}

th.e-headercell,
th.e-filterbarcell {
  background-color: #bae7ff !important;
  border-color: white !important;
}

th.e-filterbarcell {
  border-width: 1px 0 0 1px !important;
}

.e-grid {
  font-family: 'Roboto', Arial !important;
}

.e-grid td.e-active {
  background: #f9e1a9 !important;
}

.e-grid.e-gridhover tr[role='row']:not(.e-editedrow):hover .e-rowcell:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.e-grid.e-gridhover tr[role='row']:hover .e-detailrowcollapse:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.e-grid.e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.e-grid.e-rtl .e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.e-grid.e-gridhover tr[role='row']:hover .e-detailrowexpand:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell) {
  background-color: #fff9eb !important;
}

.e-grid td.e-rowcell {
  line-height: 20px !important;
  padding: 5px 7px !important;
}

.e-grid .e-filterdiv.e-fltrinputdiv .e-input-group.e-control-wrapper {
  border: 0 !important;
}

.e-grid .e-filtertext.e-input.e-disable {
  display: none !important;
}

.e-grid .cell-error-custom {
  color: white;
  background-color: red;
  width: 20px;
  height: 20px;
  border-radius: 10px;
  text-align: center;
  float: left;
}
.e-grid tr.e-emptyrow td {
  text-align: center !important;
}
div#popupTCTTTB___BV_modal_outer_ {
  z-index: 10 !important;
  margin-top: 100px;
}
.dashboard-header {
  z-index: 9 !important;
}
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
div.banner {
  position: absolute;
  bottom: 49px;
  z-index: 9;
  /* background: #fff; */
  width: 100%;
  height: max-content;
  display: block;
  padding-right: 10px;
  margin-right: 10px;
}
div.banner-diagram {
  background: rgb(177 177 177 / 15%);
  width: 100%;
  padding: 15px 15px;
}
.split.split-horizontal {
  position: relative;
}
.quan-ly-truyen-dan .popup-expand-custom .grid-panel {
  z-index: 100 !important;
}
.quan-ly-truyen-dan .split-custom {
  overflow: auto;
  overflow-x: initial;
  overflow-y: initial;
}
.quan-ly-truyen-dan .combobox-grid .grid-panel {
  position: absolute;
  width: auto !important;
  background-color: white;
  border: 1px solid #e0e0e0;
  border-radius: 5px;
  z-index: 9;
  box-shadow: 0px 4px 4px rgb(177 177 177 / 15%);
  overflow: hidden;
  max-width: 600px;
  min-width: 100%;
}

.e-toolbar {
  display: none !important;
}
</style>
