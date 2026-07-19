<template src="./index.template.html"></template>
<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import { getterName, actionName, statePropertyName } from './../store'

import EventBus from '@/utils/eventBus'
import Vue from 'vue'

import ModalChonDSKetCuoiExt from '../modal/ChonDSKetCuoiExt'
import ModalQLKetCuoiExt from '../modal/QLKetCuoiExt2'
import ModalQLCapExt from '../modal/QLCapExt2'
import QuanLyTramThietBi from '../../QuanLyMangTruyenDan-Cuong/QuanLyTramThietBi'
import QuanLyCapReNhanh from '../../QuanLyCapReNhanh/QuanLyCapReNhanh'
import { Query } from '@syncfusion/ej2-data'
export default {
  name: 'leftPaneContent',
  components: {QuanLyTramThietBi, ModalChonDSKetCuoiExt, ModalQLKetCuoiExt, ModalQLCapExt, QuanLyCapReNhanh},
  computed: {
    ...mapState('cabman/BanDoMangCap', statePropertyName),
    ...mapState('cabman', ['BanDoMangCap']),
    ...mapGetters('cabman/BanDoMangCap', getterName),
    getSoKetCuoiChuaDauCap () {
      return `Kết cuối chưa đấu cáp (${this.ds_ketcuoi_cnc.length})`
    },
    getSoKetCuoiChuaCoToaDo () {
      return `Kết cuối chưa có tọa độ (${this.ds_ketcuoi_ctd.length})`
    },
    getDSKetCuoiChuaCoToaDo () {
      return [...this.ds_ketcuoi_ctd]
    },
    getMidspanObject () {
      return this.midspanObject
    }
  },
  data () {
    return {
      contextMenuItems: [
        // {
        //   text: 'Tạo kết cuối mới và nối cáp tới',
        //   id: 'MENU_TAO_KC',
        //   target: '.e-content',
        //   iconCss: 'icon text-main f20 one-daunoi',
        //   group: 'KETCUOI'
        // },
        // {
        //   id: 'MENU_ITEM_SEPARATOR_2',
        //   separator: true,
        //   group: 'KETCUOI'
        // },
        // {
        //   text: 'Tạo cáp tới kết cuối cùng tuyến',
        //   id: 'MENU_TAO_CAP_KC_CUNG_TUYEN',
        //   target: '.e-content',
        //   iconCss: 'icon text-main f20 one-taocap',
        //   group: 'KETCUOI'
        // },
        // {
        //   text: 'Tạo cáp kết nối kết cuối khác tuyến',
        //   id: 'MENU_TAO_CAP_KC_KHAC_TUYEN',
        //   target: '.e-content',
        //   iconCss: 'icon text-main f20 one-taokn',
        //   group: 'KETCUOI'
        // },
        // {
        //   id: 'MENU_ITEM_SEPARATOR_3',
        //   separator: true,
        //   group: 'KETCUOI'
        // },
        // {
        //   text: 'Đấu nối bên trong',
        //   id: 'MENU_DAU_NOI_BEN_TRONG',
        //   target: '.e-content',
        //   iconCss: 'icon text-main f20 one-daunoi1',
        //   group: 'KETCUOI'
        // },
        {
          text: 'Chuyển vào trong kết cuối/trạm thiết bị',
          id: 'MENU_CHUYEN_VAO_TRONG_KC',
          target: '.e-content',
          iconCss: 'icon text-main f20 one-chuyen',
          group: 'KETCUOI'
        }
        // {
        //   id: 'MENU_ITEM_SEPARATOR_4',
        //   separator: true,
        //   group: 'KETCUOI'
        // },
        // {
        //   text: 'Thuê bao trên kết cuối',
        //   id: 'MENU_THUE_BAO_TREN_KC',
        //   target: '.e-content',
        //   iconCss: 'icon text-main f20 -ap icon-apps',
        //   group: 'KETCUOI'
        // },
        // {
        //   id: 'MENU_ITEM_SEPARATOR_5',
        //   separator: true,
        //   group: 'KETCUOI'
        // },
        // {
        //   text: 'Xóa kết cuối',
        //   id: 'MENU_XOA_KC',
        //   target: '.e-content',
        //   iconCss: 'icon text-main f20 one-trash',
        //   group: 'KETCUOI'
        // },
        // {
        //   id: 'MENU_ITEM_SEPARATOR_6',
        //   separator: true,
        //   group: 'KETCUOI'
        // },
        // {
        //   text: 'Thuộc tính',
        //   id: 'MENU_THUOC_TINH_KC',
        //   target: '.e-content',
        //   iconCss: 'icon text-main f20 -ap icon-info',
        //   group: 'KETCUOI'
        // }
      ],
      message: null,
      selectedTRAMid: null,
      tramTb_id: null,
      lock_events: false,
      selectedTTVT: {
        DONVI_ID: null,
        TEN_DV: null
      },
      selectedTOKT: {
        DONVI_ID: null,
        TEN_DV: null
      },
      selectedTRAM: {
        DONVI_ID: null,
        TEN_DV: null
      },
      ds_nhom: [{
        ID: 0,
        TEN: 'Cáp đồng'
      }, {
        ID: 1,
        TEN: 'Cáp quang'
      }],
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
      },
      nhomlc_id: 1,
      showGridKCCNC: false,
      showGridKCCTD: false,
      pageSettings: { pageSize: 20 },
      customAttributes: {class: 'customcss'},
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
      },
      templateCmd: function () {
        return { template: Vue.component('columnTemplate', {
          template: `<div>
                      <a @click.prevent="suaKetCuoi(item)" href="#" class="btn btn-main pad4 lh14">
                        <span class="-ap icon-more_horiz"></span>
                      </a>

                      <a @click.prevent="ghimKetCuoiBanDo(item)" href="#" class="btn btn-main pad4 lh14">
                        <span class="-ap icon-chevron-thin-right"></span>
                      </a>
                    </div>`,
          data: function () {
            return {
              data: {}
            }
          },
          methods: {
            suaKetCuoi (item) {
              EventBus.$emit('SUA_KETCUOI', item.FULL_ID)
            },
            ghimKetCuoiBanDo (item) {
              EventBus.$emit('GHIM_KETCUOI', item)
            }
          },
          computed: {
            item: function () {
              return this.data
            }
          }
        })}
      },
      templateLocation: function () {
        return { template: Vue.component('columnTemplate', {
          template: `<div>
                     <a href="#" class="link one-location_crosshair f20"></a>
                    </div>`,
          data: function () {
            return {
              data: {}
            }
          },
          methods: {
          },
          computed: {
          }
        })}
      }
    }
  },
  provide: {

  },
  methods: {
    ...mapActions('cabman/BanDoMangCap', actionName),
    contextMenuClick (args) {
      let row = args.rowInfo.rowData
      if (args.item.id === 'tao_ket_cuoi') {}
      if (args.item.id === 'tao_cap_toi_ket_cuoi_cung_tuyen') {}
      if (args.item.id === 'tao_cap_toi_ket_cuoi_khac_tuyen') {}
      if (args.item.id === 'dau_noi_ben_trong') {}
      if (args.item.id === 'MENU_CHUYEN_VAO_TRONG_KC') {
        if (row) {
          console.log('🚀 ~ file: index.vue:277 ~ contextMenuClick ~ row:', row)
          let node = {
            KETCUOI_ID: 'KETCUOI_' + row.KETCUOI_ID,
            KYHIEU_KC: row.KYHIEU
          }
          EventBus.$emit('call_chuyenvaoTrongTram', node)
        }
      }
      if (args.item.id === 'thue_bao_thuoc_ket_cuoi') {}
      if (args.item.id === 'xoa_ket_cuoi') {}
      if (args.item.id === 'thuoc_tinh_ket_cuoi') {}
    },
    filtering: function (e) {
      var searchData = this.ds_tram
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_DV', 'contains', e.text, true) : query
      e.updateData(searchData, query)
    },
    Help () {
      this.$alert('Chọn kết cuối chưa đấu cáp chưa thuộc cáp gốc nào để đưa vào chung giản đồ của cáp gốc hiện tại.', 'Thông báo', {
        confirmButtonText: 'Đóng'
      })
    },
    cboTRAMButtonClick () {
      this.tramTb_id = this.selectedTRAM.DONVI_ID
      this.$bvModal.show('popupTramThietBiGianDo2')
    },
    napGD () {
      EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2')
    },
    QuanLyTramThietBiIsOk () {},
    async ChonKCChuaNoiCap () {
      console.log(this.capgoc)
      if (Object.keys(this.capgoc).length === 0) {
        this.$alert('Chưa chọn cáp gốc, <b>Click Double</b> vào 1 cáp gốc từ danh sách để chọn.', 'Kết cuối chưa nối cáp', {
          confirmButtonText: 'Đóng',
          type: 'warning',
          dangerouslyUseHTMLString: true,
          callback: action => {

          }
        })
      } else {
        if (this.capgoc.CAP_ID !== null) {
          this.$refs.ModalChonDSKetCuoiExt.show(1).then(result => {
            if (result.ok) {
              this.$root.context.post(
                '/web-cabman/ban-do-mang-cap/sp_capnhat_capgoc_cho_ketcuoi', {P_CAPGOC_ID: this.capgoc.CAP_ID, P_KETCUOI_ID: result.selectedRow.KETCUOI_ID}
              ).then((rs) => {
                if (rs.data.length > 0) {
                  if (rs.data[0].RESULT === 1) {
                    this.$root.context.post(
                      '/web-cabman/ban-do-mang-cap/sp_capnhat_capgoc_cho_cap', {P_CAPGOC_ID: this.capgoc.CAP_ID, P_KETCUOI_ID: result.selectedRow.KETCUOI_ID}
                    ).then((rs) => {
                      this.napGianDo_v2(null, null)
                    })
                  }
                }
              })
            } else {
              console.log('Cancel')
            }
          })
        }
      }
    },
    grid3_onCommandClicked (name, dataItem) {
      if (name === 'SUA_KETCUOI') {
        EventBus.$emit('SUA_KETCUOI', dataItem.FULL_ID)
        return
      }
      if (name === 'GHIM_KETCUOI') {
        EventBus.$emit('GHIM_KETCUOI', dataItem)
      }
    },
    async KETNOI_KETCUOI_VAO_CAPGOC_THIEU_DICH (vcap_id, vketcuoi_d_id) {
      try {
        var data = {
          VCAP_ID: vcap_id,
          VKETCUOI_D_ID: vketcuoi_d_id
        }
        var rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/ketnoi_ketcuoi_vao_capgoc_thieu_dich', data)
        if (rs.data.length > 0) {
          EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2')
        }
      } catch (error) {
      }
      this.setState({key: 'vaction', value: 'None'})
      this.setState({key: 'vnguon_id', value: null})
      this.setState({key: 'vdich_id', value: null})
    },
    async  grid2_onCommandClicked (name, dataItem) {
      const ketcuoi_id = dataItem.KETCUOI_ID
      if (name === 'SUA_KETCUOI') {
        var dataQLKetCuoi = {}
        dataQLKetCuoi.cmd = 'SuaKetcuoi'
        dataQLKetCuoi.data = {
          ketcuoi_id: ketcuoi_id,
          phanloaikc_id: this.phanloaikc_id
        }
        this.$refs.ModalQLKetCuoiExt.show(dataQLKetCuoi).then((result) => {
          if (result.ok) {
            if (result.ok) {
              if (result.data.result) {
                this.NAP_DS_KETCUOI_CNC()
              }
            }
          } else {

          }
        })
        return
      }
      if (name === 'XOA_KETCUOI') {
        this.$confirm(`Bạn có muốn xóa kết cuối đang chọn không ?`,
          'Giản đồ mạng cáp',
          {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            type: 'warning'
          }).then(() => {
          this.loading(true)
          this.api_set_xoa_ketcuoi({ketcuoi_id: ketcuoi_id, xoa_batbuoc: 0}).then((rep) => {
            this.loading(false)
            this.$root.$toast.success('Xóa kết cuối thành công')
            this.NAP_DS_KETCUOI_CNC()
          }).catch((error) => {
            this.loading(false)
            !error.response && this.$toast.error(error.message)
            error.response && this.$alert(error.response.data.message_detail, error.response.data.message, {
              dangerouslyUseHTMLString: true,
              confirmButtonText: 'Đóng',
              type: 'error'
            })
          })
        }).catch((e) => { this.loading(false) })
      }
      if (name === 'LOAIBO_KETCUOI') {
        this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/sp_loaibo_capgoc_cho_ketcuoi', {P_KETCUOI_ID: ketcuoi_id}
        ).then((rs) => {
          if (rs.data.length > 0) {
            this.$root.context.post(
              '/web-cabman/ban-do-mang-cap/sp_loaibo_capgoc_cho_cap', {P_KETCUOI_ID: ketcuoi_id}
            ).then((rs) => {
              this.NAP_DS_KETCUOI_CNC()
            })
          }
        })
      }
      if (name === 'TAO_CAP') {
        var DT = ('KETCUOI_' + dataItem.KETCUOI_ID).split('_')
        if (this.vaction === 'None') {
          this.setState({key: 'vaction', value: 'TaoCap'})
          this.setState({key: 'vnguon_id', value: DT})
          this.setState({key: 'vdich_id', value: null})
          EventBus.$emit('SHOW_TOAST', {s1: 'Chọn kết cuối / trạm thiết bị đích', s2: '<b>Nhấn ESC để hủy</b>'})
          return
        }
        if (this.vaction === 'KetNoiKetCuoiThieu') {
          if (DT[0] !== 'KETCUOI') return
          await this.KETNOI_KETCUOI_VAO_CAPGOC_THIEU_DICH(this.vnguon_id, DT[1])
          return
        }
        if (this.vaction === 'TaoCap') {
          if (this.vnguon_id[0] !== DT[0] || this.vnguon_id[1] !== DT[1]) {
            this.setState({key: 'vdich_id', value: DT})
            var donvi_id = null
            var ketcuoi_n_id = null
            var ketcuoi_d_id = null
            var donvi_td_id = null
            if (this.vnguon_id[0] === 'TRAM') { donvi_id = this.vnguon_id[1] } else { ketcuoi_n_id = this.vnguon_id[1] }
            if (this.vdich_id[0] === 'TRAM') { donvi_td_id = this.vdich_id[1] } else { ketcuoi_d_id = this.vdich_id[1] }
            let ten_donvi = null
            try {
              let tendv = await this.$root.context.get('/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'DON_VI', option: donvi_id})
              if (tendv && tendv.data.length > 0) {
                ten_donvi = tendv[0].TEN_DONVI
              }
            } catch (error) {
              console.log('🚀 ~ file: index.vue:456 ~ grid2_onCommandClicked ~ error:', error)
            }
            var data = {
              capgoc: this.capgoc,
              // donvi: donvi_id,
              donvi: {DONVI_ID: donvi_id, TEN_DV: ten_donvi},
              donvi_td_id: donvi_td_id,
              ketcuoi_n_id: ketcuoi_n_id,
              ketcuoi_d_id: ketcuoi_d_id,
              kieucap_id: 0,
              toql: this.toql
            }
            this.$refs.ModalQLCap.taoCap(data).then((result) => {
              if (result.ok) {
                if (result.data.CAP_ID !== null) { this.napGianDo_v2(null, null) }
              } else {

              }
              EventBus.$emit('HIDE_TOAST', 'HIDE_TOAST')
            })
          }
        }
        if (this.vaction === 'TaoCapMidSpan') {
          if (this.vnguon_id[0] !== DT[0] || this.vnguon_id[1] !== DT[1]) {
            this.setState({key: 'vdich_id', value: DT})
            var donvi_id = null
            var ketcuoi_n_id = null
            var ketcuoi_d_id = null
            var donvi_td_id = null
            if (this.vnguon_id[0] == 'TRAM') { donvi_id = this.vnguon_id[1] } else { ketcuoi_n_id = this.vnguon_id[1] }
            if (this.vdich_id[0] == 'TRAM') { donvi_td_id = this.vdich_id[1] } else { ketcuoi_d_id = this.vdich_id[1] }
            this.midspanObject.CAP_ID = this.vnguon_id[2]
            this.midspanObject.DONVI_ID = donvi_id
            this.midspanObject.DONVI_TD_ID = donvi_td_id
            this.midspanObject.KETCUOI_N_ID = ketcuoi_n_id
            this.midspanObject.KETCUOI_D_ID = ketcuoi_d_id
            this.midspanObject.KIEUCAP_ID = 0
            this.$bvModal.show('popupQuanLyCapReNhanhLeft')
            EventBus.$emit('HIDE_TOAST', 'HIDE_TOAST')
          }
        }
      }
    },
    async selectedKCCNC (args) {

    },
    async onDoubleClick (args) {
      try {
        var rowData = args.rowData
        this.set_cap_goc(rowData).then(async () => {
          this.setState({key: 'IsLoadNewGraph', value: true})
          await this.napGianDo_v2(null, null)
        })
      } catch (error) {
        this.$root.$toast.error('Lỗi thực hiện nạp dữ liệu giản đồ mạng cáp')
      }
    },
    async napGianDo (obj) {
      try {
        EventBus.$emit('HIDE_PANE_DAUNOI', 'HIDE_PANE_DAUNOI')
        if (Object.keys(this.capgoc).length === 0 || this.capgoc.CAP_ID === null) return
        this.loading(true)
        await this.api_get_gian_do_cap(this.capgoc.CAP_ID)
        if (obj && obj.length > 0) {
          let objData = obj[0]
          EventBus.$emit('ACTIVE_OBJECT', objData)
        }
        await this.NAP_DS_KETCUOI_CNC()
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 383 ~ napGianDo ~ error', error)
        this.$root.$toast.error('Lỗi thực hiện nạp dữ liệu giản đồ mạng cáp')
      } finally {
        this.loading(false)
      }
    },
    async GianDoBackupState () {},
    async GianDoRestoreState () {},
    async napGianDo_v2 (obj, dsDANDO_VEGOC) {
      console.log('🚀 ~ file: index.vue:537 ~ napGianDo_v2 ~ this.IsLoadNewGraph:', this.IsLoadNewGraph)
      if (!this.IsLoadNewGraph) {
        await this.GianDoBackupState()
      }
      try {
        // EventBus.$emit('HIDE_PANE_DAUNOI', 'HIDE_PANE_DAUNOI')
        if (Object.keys(this.capgoc).length === 0 || this.capgoc.CAP_ID === null) return
        this.loading(true)
        await this.api_get_gian_do_cap(this.capgoc.CAP_ID)
        await this.NAP_DS_KETCUOI_CNC()
      } catch (error) {
        this.$root.$toast.error('Lỗi thực hiện nạp dữ liệu giản đồ mạng cáp')
      } finally {
        if (!this.IsLoadNewGraph) {
          await this.GianDoRestoreState()
          if (obj && obj.length > 0) {
            let objData = obj[0]
            EventBus.$emit('ACTIVE_OBJECT_V2', { objData, dsDANDO_VEGOC })
            EventBus.$emit('ACTIVE_OBJECT_MAP_V2', { objData, dsDANDO_VEGOC })
          }
        }
        this.loading(false)
      }
      this.setState({key: 'IsLoadNewGraph', value: false})
    },
    async NAP_DS_KETCUOI_CNC () {
      if (this.capgoc.CAP_ID === undefined) return
      try {
        await this.api_get_ds_ketcuoi_chua_nhapcap(this.capgoc.CAP_ID)
      } catch (error) {
        this.$root.$toast.error('Lỗi thực hiện nạp dữ liệu giản đồ mạng cáp')
      }
    },
    async loadData () {
      try {
        await this.api_get_ds_ttvt(this.$root.token.getPhanVungID())
        if (this.ds_ttvt.length > 0) {
          this.selectedTTVT = this.ds_ttvt[0]
          this.setState({key: 'ttvt_id', value: this.ds_ttvt[0].DONVI_ID})
        } else {
          this.selectedTTVT.DONVI_ID = null
          this.selectedTTVT.TEN_DV = null
        }
      } catch (error) {
        this.$root.$toast.error('Lỗi thực hiện nạp dữ liệu giản đồ mạng cáp')
      }
    },
    async getDsToKT () {
      try {
        await this.api_get_ds_tokt(this.selectedTTVT.DONVI_ID)
        if (this.ds_tokt.length > 0) {
          this.selectedTOKT = this.ds_tokt[0]
        } else {
          this.selectedTOKT.DONVI_ID = null
          this.selectedTOKT.TEN_DV = null
        }
      } catch (error) {
        this.$root.$toast.error('Lỗi thực hiện nạp dữ liệu giản đồ mạng cáp')
      }
    },
    async getDsTram () {
      try {
        await this.api_get_ds_tram(this.selectedTOKT.DONVI_ID)
        if (this.ds_tram.length > 0) {
          this.selectedTRAMid = this.ds_tram[0].DONVI_ID
        } else {
          this.selectedTRAMid = null
        }
      } catch (error) {
        this.$root.$toast.error('Lỗi thực hiện nạp dữ liệu giản đồ mạng cáp')
      }
    },
    grid1_rowSelected (data) {
      console.log('grid1_rowSelected', data)
    },
    async getDsCap () {
      try {
        this.loading(true)
        this.setState({key: 'capgoc', value: {}})
        if (this.nhomlc_id === null || this.donvi.DONVI_ID === null) return
        let data = {donvi_id: this.donvi.DONVI_ID, nhomlc_id: this.nhomlc_id}
        await this.api_get_ds_cap(data)
        this.lock_events = false
      } catch (error) {
        this.$root.$toast.error('Lỗi thực hiện nạp dữ liệu giản đồ mạng cáp')
      } finally {
        this.loading(false)
      }
    },
    loadDsCap () {
      if (this.lock_events) return false
    },
    async SET_NHOMLC_ID (data) {
      this.nhomlc_id = data
    },
    async SET_STATE (data) {
      this.lock_events = true
      this.setState({ key: 'ds_ttvt',
        value: data.dsTTVTS.map(item => {
          return {
            DONVI_ID: item.ID,
            TEN_DV: item.NAME
          }
        }) })
      const i1 = data.dsTTVTS.find(e => e.SELECTED === 1)
      if (i1) {
        this.selectedTTVT = { DONVI_ID: i1.ID, TEN_DV: i1.NAME }
      }
      this.setState({ key: 'ds_tokt',
        value: data.dsTOKT.map(item => {
          return {
            DONVI_ID: item.ID,
            TEN_DV: item.NAME
          }
        }) })
      const i2 = data.dsTOKT.find(e => e.SELECTED === 1)
      if (i2) {
        this.selectedTOKT = { DONVI_ID: i2.ID, TEN_DV: i2.NAME }
      }
      this.setState({ key: 'ds_tram',
        value: data.dsTONGDAI.map(item => {
          return {
            DONVI_ID: item.ID,
            TEN_DV: item.NAME
          }
        }) })
      const i3 = data.dsTONGDAI.find(e => e.SELECTED === 1)
      if (i3) {
        this.selectedTRAM = { DONVI_ID: i3.ID, TEN_DV: i3.NAME }
        this.selectedTRAMid = i3.ID
        await this.set_donvi({ DONVI_ID: i3.ID, TEN_DV: i3.NAME })
        await this.getDsCap()
        for (let index = 0; index < this.ds_cap.length; index++) {
          const element = this.ds_cap[index]
          var cap_id = element.CAP_ID
          var kyhieu = element.KYHIEU
          if (cap_id === data.dsDOITUONG[0].CAPGOC_ID) {
            setTimeout(() => {
              this.$refs.grid1.setCurrentSelectedRow(index || 0)
            }, 500)
            this.set_cap_goc(element).then(() => {
              this.napGianDo_v2(data.dsDOITUONG, data.dsDANDO_VEGOC).then(() => {
              })
            })
            break
          }
        }
      }
    }
  },
  watch: {
    message (val) {
    },
    lock_events (val) {
    },
    selectedTTVT: function (val) {
      if (this.lock_events) return
      this.clearState_ds_cap()
      this.getDsToKT()
      this.setState({key: 'ttvt_id', value: val.DONVI_ID})
    },
    selectedTOKT: function (val) {
      if (this.lock_events) return
      this.clearState_ds_cap()
      this.set_tokt(val)
      this.getDsTram()
    },
    selectedTRAMid: function (val) {
      if (this.lock_events) return
      if (val === null) {
        this.selectedTRAM.DONVI_ID = null
        this.selectedTRAM.TEN_DV = null
        this.setState({key: 'tramvt_id', value: 0})
      } else {
        this.selectedTRAM.DONVI_ID = val
        let e = this.ds_tram.filter(e => e.DONVI_ID === val)
        this.setState({key: 'tramvt_id', value: val})
        if (e !== null) {
          this.selectedTRAM.TEN_DV = e.TEN_DV
        }
      }
    },
    selectedTRAM: {
      handler: function (val, oldVal) {
        if (this.lock_events) return
        this.clearState_ds_cap()
        this.set_donvi({...val})
        this.getDsCap()
      },
      deep: true
    },
    getDSKetCuoiChuaCoToaDo (val) {
    },
    ds_cap (val) {
      this.setState({key: 'gian_do_cap', value: []})
      this.setState({key: 'ds_ketcuoi_cnc', value: []})
      this.setState({key: 'ds_ketcuoi_ctd', value: []})
    }
  },
  created () {
    this.clearState()
    this.createApiManager()
    this.loadData()
  },
  beforeDestroy () {
    this.napGianDo && EventBus.$off('NAP_GIANDO', this.napGianDo)
    this.napGianDo && EventBus.$off('NAP_GIANDO_V2', this.napGianDo_v2)
    this.getDsCap && EventBus.$off('NAP_DS_CAP', this.getDsCap)
    this.SET_NHOMLC_ID && EventBus.$off('SET_NHOMLC_ID', this.SET_NHOMLC_ID)
  },
  async mounted () {
    EventBus.$on('NAP_GIANDO', this.napGianDo)
    EventBus.$on('NAP_GIANDO_V2', this.napGianDo_v2)
    EventBus.$on('NAP_DS_CAP', this.getDsCap)
    EventBus.$on('SET_STATE', this.SET_STATE)
    EventBus.$on('SET_NHOMLC_ID', this.SET_NHOMLC_ID)
  }
}
</script>

<style>
#grid2 .e-grid .e-gridheader .e-columnheader
{
  display: none;
}
#grid3 .e-grid .e-gridheader .e-columnheader
{
  display: none;
}
.e-dropdownbase .e-list-item.e-item-focus, .e-dropdownbase .e-list-item.e-active, .e-dropdownbase .e-list-item.e-active.e-hover, .e-dropdownbase .e-list-item.e-hover
{
  background-color: #0B7FC5;
  color: #FDFEFE;
}
.e-dropdownbase .e-list-item, .e-dropdownbase .e-list-item.e-item-focus
{
    font-size: 9;
}

</style>
