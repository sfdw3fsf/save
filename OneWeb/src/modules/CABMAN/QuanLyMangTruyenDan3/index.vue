<template src="./index.template.html"></template>
<script>

import Vue from 'vue'
import EventBus from '@/utils/eventBus'
import '@/utils/gmaps'
import BinaryReader from '@/utils/BinaryReader'
import Gmap from './components/BanDo.vue'
import Diagram from './components/GianDo.vue'
import Point from './class/Point'
import Node from './class/Node'
import { mapActions, mapState, mapGetters } from 'vuex'
import { getterName, actionName, statePropertyName } from './store'
import { Page, Filter, Resize, Freeze, GridPlugin, Group } from '@syncfusion/ej2-vue-grids'

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
  components: { Gmap, Diagram },
  name: 'QuanLyMangTruyenDan',
  provide: {
    grid: [Page, Filter, Resize, Freeze, Group]
  },
  computed: {
    ...mapState('cabman/QuanLyMangTruyenDan', statePropertyName),
    ...mapState('cabman', ['QuanLyMangTruyenDan']),
    ...mapGetters('cabman/QuanLyMangTruyenDan', getterName),
    getMap () { return this.map }
  },
  data () {
    return {
      showtabGianDo: true,
      selected: {
        TUYENCAP_ID: null,
        TTVT_ID: null,
        TOVT_ID: null,
        TTVT_ID_2: null,
        TRAMTB_ID: null,
        TOVT_ID_2: null
      },
      bocuc: { version: null, translate: null, zoom: null, points: [] },
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
    thaydoiMapType () { this.$refs.BanDo.thaydoiMapType() },
    allowMoveNode () { this.$refs.BanDo.allowMoveNode() },
    async luuToaDoBanDo () {
      try {
        this.$root.showLoading(true)
        for (const Node of this.$refs.BanDo.getNodes()) {
          if (Node.Modified && !Node.IsDiemuon) {
            let id = Node.id2
            if (Node.Group === 'TRAM_TB') {
              let rs = await this.api_execute({ data: { V_PHANVUNG_ID: this.$root.token.getPhanVungID(), V_TRAMTB_ID: id, V_VIDO: Node.Position.lat, V_KINHDO: Node.Position.lng }, function: 'CABMAN.PKG_SINHDN.SP_CAPNHAT_TOADO_TRAMTB' })
            }
            if (Node.Group === 'KETCUOI') {
              let rs = await this.api_execute({ data: { V_PHANVUNG_ID: this.$root.token.getPhanVungID(), V_KETCUOI_ID: id, V_VIDO: Node.Position.lat, V_KINHDO: Node.Position.lng }, function: 'CABMAN.PKG_SINHDN.SP_CAPNHAT_TOADO_KETCUOI' })
            }
          }
        }
        for (const Route of this.$refs.BanDo.getRoutes()) {
          if (Route.Modified) {
            let id = Route.id
            let day_toado = Route.GetPoints()
            // console.log('day_toado', day_toado)
            let rs = await this.api_execute({ data: { V_PHANVUNG_ID: this.$root.token.getPhanVungID(), V_CAP_ID: id, V_DAY_TOADO: day_toado }, function: 'CABMAN.PKG_SINHDN.SP_CAPNHAT_TOADO_CAP' })
          }
        }
        this.$root.showLoading(false)
      } catch (error) {
        this.$root.showLoading(false)
      }
    },
    tuyencap_selectedChanged (data) {
      console.log('tuyencap_selectedChanged', data)
    },
    loadData: async function () {
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.get(
          '/web-cabman/bando_matdo/thongtin_bando'
        )
        if (rs.data != null && rs.data.length > 0) {
          console.log('setThongTinMap', rs.data)
          this.setThongTinMap({
            lat: parseFloat(rs.data[0].C_LATITUS),
            lng: parseFloat(rs.data[0].C_LONGTITUS),
            zoom: rs.data[0].DEFAULT_ZOOM,
            mapTypeId: 'roadmap'
          })
        }
        rs = await this.api_execute({ data: { V_PHANVUNG_ID: this.$root.token.getPhanVungID(), V_NHANVIEN_ID: null }, function: 'CABMAN.PKG_SINHDN.SP_NAP_DS_TUYENCAP' })
        if (rs.data) {
          console.log('dsTuyenCap', rs)
          this.setState({key: 'dsTuyenCap', value: rs.data})
          if (this.data.dsTuyenCap.length > 0) {
            this.selected.TUYENCAP_ID = this.data.dsTuyenCap[0].TUYENCAP_ID
          }
        }
        rs = await this.api_execute({ data: { V_PHANVUNG_ID: this.$root.token.getPhanVungID(), V_NHANVIEN_ID: null }, function: 'CABMAN.PKG_SINHDN.SP_NAP_DS_TTVT' })
        if (rs.data) {
          console.log('dsTTVT', rs)
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
        this.$root.showLoading(true)
        var rs = await this.api_execute({ data: { V_PHANVUNG_ID: this.$root.token.getPhanVungID(), V_TUYENCAP_ID: this.selected.TUYENCAP_ID }, function: 'CABMAN.PKG_SINHDN.SP_LAY_GIANDO_TRUYENDAN' })
        if (rs.data) {
          console.log('dsGIANDO', JSON.stringify(rs.data))
          this.setState({key: 'dsGIANDO', value: rs.data})
          rs = await this.api_execute({ data: { V_PHANVUNG_ID: this.$root.token.getPhanVungID(), V_TUYENCAP_ID: this.selected.TUYENCAP_ID }, function: 'CABMAN.PKG_SINHDN.SP_LAY_BOCUC_GIANDO' })
          if (rs.data) {
            console.log('SP_LAY_BOCUC_GIANDO', rs.data)
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
                console.log('SP_LAY_BOCUC_GIANDO bocuc', this.bocuc)
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
      try {
        this.$refs.BanDo.Clear()
        this.$refs.GianDo.Clear()
        for (let index = 0; index < dsGIANDO.length; index++) {
          const GIANDO = dsGIANDO[index]
          var cap_id = GIANDO.CAP_ID
          // console.log('GIANDO.CAP_ID', GIANDO.CAP_ID)
          if (Object.keys(this.$refs.GianDo.TimLink(cap_id.toString())).length === 0) {
            var lopmc_id = GIANDO.LOPMC_ID
            var ketcuoi_n = new Point()
            var m_ketcuoi_n = new Node()
            var ketcuoi_d = new Point()
            var m_ketcuoi_d = new Node()
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
                    ketcuoi_n = this.$refs.GianDo.TaoPoint('TRAM_TB_' + ketcuoi_n_id.toString(), GIANDO.KYHIEU_KCN, GIANDO.ICON_KCN, GIANDO.TENHT_KCN)
                    let idx = bocuc.points.findIndex(p => p.pi === ketcuoi_n_id && p.pt === 'TRAM_TB')
                    if (idx > -1) {
                      ketcuoi_n.x = bocuc.points[idx].px
                      ketcuoi_n.y = bocuc.points[idx].py
                    }
                    ketcuoi_n.Group = 'TRAM_TB'
                    ketcuoi_n.ImageKey = GIANDO.ICON_KCN
                    ketcuoi_n.Name = GIANDO.KYHIEU_KCN
                    ketcuoi_n.DisplayName = GIANDO.TENHT_KCN
                    ketcuoi_n.Size = { width: 40, height: 40 }
                    ketcuoi_n.AllowMove = true
                    ketcuoi_n.RootId = capgoc_id
                    ketcuoi_n.Position = { x: 0, y: 0 }
                    this.$refs.GianDo.CapNhatPoint(ketcuoi_n)
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
                    m_ketcuoi_n.AllowMove = true
                    m_ketcuoi_n.RootId = capgoc_id
                    m_ketcuoi_n.Position = { lat: parseFloat(GIANDO.VIDO_KCN), lng: parseFloat(GIANDO.KINHDO_KCN) }
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
                    ketcuoi_n = this.$refs.GianDo.TaoPoint('KETCUOI_' + ketcuoi_n_id.toString(), GIANDO.KYHIEU_KCN, GIANDO.ICON_KCN, GIANDO.TENHT_KCN)
                    let idx = bocuc.points.findIndex(p => p.pi === ketcuoi_n_id && p.pt === 'KETCUOI')
                    if (idx > -1) {
                      ketcuoi_n.x = bocuc.points[idx].px
                      ketcuoi_n.y = bocuc.points[idx].py
                    }
                    ketcuoi_n.Group = 'KETCUOI'
                    ketcuoi_n.ImageKey = GIANDO.ICON_KCN
                    ketcuoi_n.Name = GIANDO.KYHIEU_KCN
                    ketcuoi_n.DisplayName = GIANDO.TENHT_KCN
                    ketcuoi_n.Size = { width: 24, height: 24 }
                    ketcuoi_n.AllowMove = true
                    ketcuoi_n.RootId = capgoc_id
                    ketcuoi_n.Position = { x: 0, y: 0 }
                    this.$refs.GianDo.CapNhatPoint(ketcuoi_n)
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
                    m_ketcuoi_n.AllowMove = true
                    m_ketcuoi_n.RootId = capgoc_id
                    m_ketcuoi_n.Position = { lat: parseFloat(GIANDO.VIDO_KCN), lng: parseFloat(GIANDO.KINHDO_KCN) }
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
                    ketcuoi_d = this.$refs.GianDo.TaoPoint('KETCUOI_' + ketcuoi_d_id.toString(), GIANDO.KYHIEU_KCD, GIANDO.ICON_KCD, GIANDO.TENHT_KCD)
                    let idx = bocuc.points.findIndex(p => p.pi === ketcuoi_d_id && p.pt === 'KETCUOI')
                    if (idx > -1) {
                      ketcuoi_d.x = bocuc.points[idx].px
                      ketcuoi_d.y = bocuc.points[idx].py
                    }
                    ketcuoi_d.Group = 'KETCUOI'
                    ketcuoi_d.ImageKey = GIANDO.ICON_KCD
                    ketcuoi_d.Name = GIANDO.KYHIEU_KCD
                    ketcuoi_d.DisplayName = GIANDO.TENHT_KCD
                    ketcuoi_d.Size = { width: 24, height: 24 }
                    ketcuoi_d.AllowMove = true
                    ketcuoi_d.RootId = capgoc_id
                    ketcuoi_d.Position = { x: 0, y: 0 }
                    this.$refs.GianDo.CapNhatPoint(ketcuoi_d)
                  }
                  // Bản đồ
                  m_ketcuoi_d = this.$refs.BanDo.TimNode('KETCUOI_' + ketcuoi_d_id)
                  if (m_ketcuoi_d === null) {
                    m_ketcuoi_d = this.$refs.BanDo.TaoNode()
                    m_ketcuoi_d.id = 'KETCUOI_' + ketcuoi_d_id
                    m_ketcuoi_n.id2 = ketcuoi_d_id
                    m_ketcuoi_d.Group = 'KETCUOI'
                    m_ketcuoi_n.Size = { width: 24, height: 24 }
                    m_ketcuoi_d.ImageKey = GIANDO.ICON_KCD
                    m_ketcuoi_d.SetIcon(m_ketcuoi_d.ImageKey)
                    m_ketcuoi_d.Label = GIANDO.KYHIEU_KCD
                    m_ketcuoi_d.Tooltip = GIANDO.TENHT_KCD
                    m_ketcuoi_d.Tag = ketcuoi_d
                    m_ketcuoi_d.AllowMove = true
                    m_ketcuoi_d.RootId = capgoc_id
                    m_ketcuoi_d.Position = { lat: parseFloat(GIANDO.VIDO_KCD), lng: parseFloat(GIANDO.KINHDO_KCD) }
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
                    ketcuoi_d = this.$refs.GianDo.TaoPoint('TRAM_TB_' + ketcuoi_d_id.toString(), GIANDO.KYHIEU_KCD, GIANDO.ICON_KCD, GIANDO.TENHT_KCD)
                    let idx = bocuc.points.findIndex(p => p.pi === ketcuoi_d_id && p.pt === 'TRAM_TB')
                    if (idx > -1) {
                      ketcuoi_d.x = bocuc.points[idx].px
                      ketcuoi_d.y = bocuc.points[idx].py
                    }
                    ketcuoi_d.Group = 'TRAM_TB'
                    ketcuoi_d.ImageKey = GIANDO.ICON_KCD
                    ketcuoi_d.Name = GIANDO.KYHIEU_KCD
                    ketcuoi_d.DisplayName = GIANDO.TENHT_KCD
                    ketcuoi_d.Size = { width: 40, height: 40 }
                    ketcuoi_d.AllowMove = true
                    ketcuoi_d.RootId = capgoc_id
                    ketcuoi_d.Position = { x: 0, y: 0 }
                    this.$refs.GianDo.CapNhatPoint(ketcuoi_d)
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
                    m_ketcuoi_d.AllowMove = true
                    m_ketcuoi_d.RootId = capgoc_id
                    m_ketcuoi_d.Position = { lat: parseFloat(GIANDO.VIDO_KCD), lng: parseFloat(GIANDO.KINHDO_KCD) }
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
            let cap = this.$refs.GianDo.TaoLink(cap_id.toString(), ketcuoi_n, ketcuoi_d)
            cap.Group = 'CAP'
            cap.Name = GIANDO.KYHIEU
            cap.DisplayName = GIANDO.TEN_HT
            cap.ShowCaption = GIANDO.HIENTHI === 1
            cap.Width = 1
            cap.SelectedWidth = 2
            cap.RootId = capgoc_id
            // cap.STYLE_KIEU_DC(GIANDO.KIEUDC_ID)
            // cap.STYLE_NHOM_LC(GIANDO.NHOMLC_ID)
            // cap.STYLE_KIEU_CAP(GIANDO.KIEUCAP_ID)

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

          // if (index > 10) break
          }
        }
        this.$refs.BanDo.FitBounds()
        this.$refs.GianDo.DoLayout()
      } catch (error) {
        console.log('error', error)
      }
    },
    GIANDO_TIM_CAP_ID (cap_id) {
      return null
    },
    GIANDO_TIM_KETCUOI_ID (ketcuoi_id) {
      return null
    },
    NAP_DS_PHANCAP () {
      this.api_execute({ data: { V_PHANVUNG_ID: this.$root.token.getPhanVungID() }, function: 'CABMAN.PKG_SINHDN.SP_NAP_DS_PHANCAP' }).then((reponse) => {
        if (reponse.data) {
          console.log('SP_NAP_DS_PHANCAP', reponse)
          this.setState({key: 'dsPHANCAP', value: reponse.data})
        }
      })
    }
  },
  watch: {
    showtabGianDo (val) {
      console.log('watch showtabGianDo', val)
    },
    'selected.TTVT_ID' (val) {
      console.log('watch selected.TTVT_ID', val)
      this.api_execute({ data: { V_PHANVUNG_ID: this.$root.token.getPhanVungID(), V_TTVT_ID: val, V_NHANVIEN_ID: this.$root.token.getNhanVienID() }, function: 'CABMAN.PKG_SINHDN.SP_NAP_DS_TOVT' }).then((reponse) => {
        if (reponse.data) {
          console.log('dsTOVT', reponse)
          this.setState({key: 'dsTOVT', value: reponse.data})
          if (this.data.dsTOVT.length > 0) {
            this.selected.TOVT_ID = this.data.dsTOVT[0].DONVI_ID
          }
        }
      })
    },
    'selected.TTVT_ID_2' (val) {
      console.log('watch selected.TTVT_ID_2', val)
      this.api_execute({ data: { V_PHANVUNG_ID: this.$root.token.getPhanVungID(), V_TTVT_ID: val, V_NHANVIEN_ID: this.$root.token.getNhanVienID() }, function: 'CABMAN.PKG_SINHDN.SP_NAP_DS_TOVT' }).then((reponse) => {
        if (reponse.data) {
          console.log('dsTOVT', reponse)
          this.setState({key: 'dsTOVT_2', value: reponse.data})
          if (this.data.dsTOVT_2.length > 0) {
            this.selected.TOVT_ID_2 = this.data.dsTOVT_2[0].DONVI_ID
          }
        }
      })
    },
    'selected.TOVT_ID' (val) {
      console.log('watch selected.TOVT_ID', val)
      this.api_execute({ data: { V_PHANVUNG_ID: this.$root.token.getPhanVungID(), V_TUYENCAP_ID: this.selected.TUYENCAP_ID, V_TOVT_ID: val }, function: 'CABMAN.PKG_SINHDN.SP_LAY_DS_TRAMTB_GIANDO' }).then((reponse) => {
        if (reponse.data) {
          console.log('dsTRAMTB', reponse)
          this.setState({key: 'dsTRAMTB', value: reponse.data})
        }
      })
    },
    'selected.TOVT_ID_2' (val) {
      console.log('watch selected.TOVT_ID_2', val)
      this.api_execute({ data: { V_PHANVUNG_ID: this.$root.token.getPhanVungID(), P_TOVT_ID: val }, function: 'CABMAN.PKG_SINHDN.SP_LAY_DS_TRAMTB' }).then((reponse) => {
        if (reponse.data) {
          console.log('dsTRAMTB', reponse)
          this.setState({key: 'dsTRAMTB_2', value: reponse.data})
          if (this.data.dsTRAMTB_2.length > 0) {
            this.selected.TRAMTB_ID = this.data.dsTRAMTB_2[0].DONVI_ID
          }
        }
      })
    },
    'selected.TRAMTB_ID' (val) {
      console.log('watch selected.TRAMTB_ID', val)
      this.api_execute({ data: { V_PHANVUNG_ID: this.$root.token.getPhanVungID(), P_TRAMTB_ID: val }, function: 'CABMAN.PKG_SINHDN.SP_LAY_DS_CAPGOC_GIANDO' }).then((reponse) => {
        if (reponse.data) {
          console.log('dsCAPGOC', reponse)
          this.setState({key: 'dsCAPGOC', value: reponse.data})
          // if (this.data.dsTRAMTB_2.length > 0) {
          //   this.selected.TRAMTB_ID = this.data.dsTRAMTB_2[0].DONVI_ID
          // }
        }
      })
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
.e-fltrTemp{
  text-align: center !important;
}
input[type=radio]{
  text-align: center !important;
}
 .e-filterbarcell {
      padding: 5px 7px !important;
  }

  th.e-headercell, th.e-filterbarcell {
      background-color: #BAE7FF !important;
      border-color: white !important;
  }

  th.e-filterbarcell {
      border-width: 1px 0 0 1px !important;
  }

  .e-grid {
      font-family: "Roboto", Arial !important;
  }

  .e-grid td.e-active {
      background: #F9E1A9 !important;
  }

  .e-grid.e-gridhover tr[role='row']:not(.e-editedrow):hover .e-rowcell:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
  .e-grid.e-gridhover tr[role='row']:hover .e-detailrowcollapse:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
  .e-grid.e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
  .e-grid.e-rtl .e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
  .e-grid.e-gridhover tr[role='row']:hover .e-detailrowexpand:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell) {
    background-color: #FFF9EB !important;
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
  .e-grid .e-gridheader .e-columnheader{
        display: none;
    }
</style>
