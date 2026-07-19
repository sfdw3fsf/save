<template src="./index.html"></template>
<script>
import SearchContractModal from './SearchContract/SearchContractModal.vue'
import SearchAccountModal from './SearchAccountModal.vue'
export default {
  components: {
    SearchContractModal,
    SearchAccountModal
  },
  name: 'TraCuuDanDoCapThueBao',
  computed: {

  },
  methods: {
    async btnTraCuu_Click () {
      if (this.check === '1' || this.check === '2') {
        await this.NAP_TT_THUEBAO()
      }
      if (this.check === '3' || this.check === '4') {
        await this.NAP_DS_PORT()
      }
    },
    async NAP_TT_THUEBAO () {
      var txt = this.check === '1' ? this.txtThueBao : this.txtHopDong
      if (txt === '') {
        this.$root.$toast.warning('Mã thuê bao không được để trống')
        return
      }
      let dt = []
      let is_thuebao = -1
      let next = false
      try {
        is_thuebao = this.check === '1' ? 1 : 0
        if (is_thuebao === -1) {
          this.$root.$toast.error('Kiểu tìm kiếm không hợp lệ')
          return
        }
        let rs = await this.$root.context.post('/web-cabman/tracuu/dan-do-cap-thuebao/thongtin-thuebao?ma_tb=' + txt + '&is_thuebao=' + is_thuebao)
        console.log('🚀 ~ file: index.vue ~ line 31 ~ NAP_TT_THUEBAO ~ rs', rs)
        dt = rs.data
        if (dt.length === 0) {
          this.thuebao = null
          this.grcDauNoi = []
          this.grcDanDo = []
          this.$root.$toast.warning('Không tìm thấy thuê bao với mã ' + txt)
          this.wbrThongTinTB = ''
          return
        }
        var idx = 0
        if (dt.length > 1) {
          var ret = dt.filter(e => e.MA_TB_SUB.toString() === txt)
          if (ret.length === 0) {

          } else {
            this.thuebao = ret[0]
            next = true
          }
        }
        if (!next) this.thuebao = dt[idx]
        txt = this.thuebao.MA_TB_SUB !== null ? this.thuebao.MA_TB_SUB : this.thuebao.MA_TB

        this.wbrThongTinTB = ''
        this.wbrThongTinTB += '<b>Mã thuê bao:</b> ' + this.Escape(this.thuebao['MA_TB']) + '<br>'
        this.wbrThongTinTB += '<b>Loại hình:</b> ' + this.Escape(this.thuebao['LOAI_HINH']) + '<br>'
        this.wbrThongTinTB += '<b>Tên thuê bao:</b> ' + this.Escape(this.thuebao['TEN_TB']) + '<br>'
        this.wbrThongTinTB += '<b>Trạng thái:</b> ' + this.Escape(this.thuebao['TRANGTHAI_TB']) + '<br>'
        this.wbrThongTinTB += '<b>Địa chỉ lắp đặt:</b> ' + this.Escape(this.thuebao['DIACHI_LD']) + '<br>'
        this.wbrThongTinTB += '<b>Tổ quản lý:</b> ' + this.Escape(this.thuebao['TO_QL']) + '<br>'
        this.wbrThongTinTB += '<b>Trạm thiết bị:</b> ' + this.Escape(this.thuebao['TRAM_TBI']) + '<br>'
        this.wbrThongTinTB += '<b>Loại hợp đồng:</b> ' + this.Escape(this.thuebao['LOAI_HD']) + '<br>'
        this.wbrThongTinTB += '<b>Kiểu lắp đặt:</b> ' + this.Escape(this.thuebao['KIEU_LD']) + '<br>'
        this.wbrThongTinTB += '<b>Ghi chú:</b> ' + this.Escape(this.thuebao['GHICHU']) + '<br>'

        await this.NAP_DS_DAUNOI()
        var flag = false
        if (this.check === '2') {
          if (this.thuebao.TRANGTHAITB_ID === 6 || this.thuebao.TRANGTHAITB_ID === 7) {
            flag = true
          } else flag = false
        }
        if (this.check === '1') {
          if (this.thuebao.TRANGTHAITB_ID === 7 || this.thuebao.TRANGTHAITB_ID === 8 || this.thuebao.TRANGTHAITB_ID === 9) {
            flag = true
          } else flag = false
        }
        this.CULY.AllowEdit = !flag
        this.GHICHU.AllowEdit = !flag
      } catch (error) {
      }
    },
    async grcDauNoi_selectedRowChanged (row) {
      console.log('🚀 ~ file: index.vue ~ line 89 ~ grcDauNoi_selectedRowChanged ~ row', row)
      this.rowDauNoi = row
      await this.NAP_DS_DANDO()
    },
    Escape (str) {
      if (str === null) return ''
      return str
    },
    async NAP_DS_DAUNOI () {
      try {
        if (this.thuebao === null) {
          this.grcDauNoi = []
          return
        }
        let rs = await this.$root.context.post('/web-cabman/tracuu/dan-do-cap-thuebao/ds-daunoi?thuebao_id=' + this.thuebao['THUEBAO_ID'] + '&daucuoi_id=' + this.thuebao['DAUCUOI_ID'])
        console.log('🚀 ~ file: index.vue ~ line 93 ~ NAP_DS_DAUNOI ~ rs.data', rs.data)
        if (rs.data.length > 0) {
          this.grcDauNoi = rs.data
        } else this.grcDauNoi = []
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 96 ~ NAP_DS_DAUNOI ~ error', error)
        this.grcDauNoi = []
      }
    },
    async NAP_DS_TTVT () {
      try {
        let rs = await this.$root.context.get('/web-cabman/tracuu/dan-do-cap-thuebao/ds-ttvt')
        if (rs.data.length > 0) {
          this.cboTTVT.Properties.DataSource = rs.data
          this.cboTTVT.EditValue = rs.data[0].DONVI_ID
        }
      } catch (error) {
      }
    },
    async NAP_DS_TOVT (ttvt_id) {
      try {
        let rs = await this.$root.context.get('/web-cabman/tracuu/dan-do-cap-thuebao/ds-tovt?ttvt_id=' + ttvt_id)
        if (rs.data.length > 0) {
          this.cboTOKT.Properties.DataSource = rs.data
          this.cboTOKT.EditValue = rs.data[0].DONVI_ID
        }
      } catch (error) {
      }
    },
    async NAP_DS_TRAM (ttvt_id, tokt_id) {
      try {
        let rs = await this.$root.context.get('/web-cabman/tracuu/dan-do-cap-thuebao/ds-tram?ttvt_id=' + ttvt_id + '&tokt_id=' + tokt_id)
        if (rs.data.length > 0) {
          this.cboTRAM.Properties.DataSource = rs.data
          this.cboTRAM.EditValue = rs.data[0].DONVI_ID
        }
      } catch (error) {
      }
    },
    async NAP_DS_LOAI_TBI (donvi_id) {
      try {
        let rs = await this.$root.context.get('/web-cabman/tracuu/dan-do-cap-thuebao/ds-loai-tbi?donvi_id=' + donvi_id)
        console.log('🚀 ~ file: index.vue ~ line 58 ~ NAP_DS_LOAI_TBI ~ rs.data', rs.data)
        if (rs.data.length > 0) {
          this.cboLOAI_TBI.Properties.DataSource = rs.data
          this.cboLOAI_TBI.EditValue = rs.data[0].LOAITBI_ID
        } else {
          this.cboLOAI_TBI.Properties.DataSource = []
          this.cboLOAI_TBI.EditValue = null
        }
      } catch (error) {
      }
    },
    async NAP_DS_THIETBI (loaitb_id, donvi_id) {
      try {
        let rs = await this.$root.context.get('/web-cabman/tracuu/dan-do-cap-thuebao/ds-thietbi?donvi_id=' + donvi_id + '&loaitb_id=' + loaitb_id)
        console.log('🚀 ~ file: index.vue ~ line 69 ~ NAP_DS_THIETBI ~ rs.data', rs.data)
        if (rs.data.length > 0) {
          this.cboTHIETBI.Properties.DataSource = rs.data
          this.cboTHIETBI.EditValue = rs.data[0].THIETBI_ID
        } else {
          this.cboTHIETBI.Properties.DataSource = []
          this.cboTHIETBI.EditValue = null
        }
      } catch (error) {
      }
    },
    async NAP_DS_CARD (loaitb_cha_id, thietbi_cha_id) {
      try {
        let rs = await this.$root.context.get('/web-cabman/tracuu/dan-do-cap-thuebao/ds-card?loaitb_cha_id=' + loaitb_cha_id + '&thietbi_cha_id=' + thietbi_cha_id)
        console.log('🚀 ~ file: index.vue ~ line 86 ~ NAP_DS_CARD ~ rs.data', rs.data)
        if (rs.data.length > 0) {
          this.cboCARD.Properties.DataSource = rs.data
          this.cboCARD.EditValue = rs.data[0].THIETBI_ID
        } else {
          this.cboCARD.Properties.DataSource = []
          this.cboCARD.EditValue = null
        }
      } catch (error) {
      }
    },
    async NAP_DS_MODULE (loaitb_cha_id, thietbi_cha_id) {
      try {
        let rs = await this.$root.context.get('/web-cabman/tracuu/dan-do-cap-thuebao/ds-module?loaitb_cha_id=' + loaitb_cha_id + '&thietbi_cha_id=' + thietbi_cha_id)
        console.log('🚀 ~ file: index.vue ~ line 89 ~ NAP_DS_MODULE ~ rs', rs)
        if (rs.data.length > 0) {
          this.cboMODULE.Properties.DataSource = rs.data
          this.cboMODULE.EditValue = rs.data[0].THIETBI_ID
        } else {
          this.cboMODULE.Properties.DataSource = []
          this.cboMODULE.EditValue = null
        }
        this.cboMODULE.Properties.DataSource.push(
          {
            THIETBI_ID: -1,
            TEN_TB: '---'
          }
        )
        this.cboMODULE.EditValue = -1
      } catch (error) {
      }
    },
    async txtKyHieuKC_KeyUp () {
      await this.NAP_DS_PORT()
    },
    async txtThueBao_KeyUp () {
      await this.NAP_TT_THUEBAO()
    },
    async txtHopDong_KeyUp () {
      await this.NAP_TT_THUEBAO()
    },
    txtThueBao_ButtonClick () {
      if (this.check.toString() === '1') {
        this.$refs.popupTraCuuDanhBa.showModal()
      }
    },
    async formCloseSearchAccount (data) {
      console.log('🚀 ~ file: index.vue ~ line 224 ~ formCloseSearchAccount ~ data', data)
      if (data !== null) { this.txtThueBao = data.ma_tb }
      if (this.txtThueBao !== '') {
        await this.txtThueBao_KeyUp()
      }
    },
    txtHopDong_ButtonClick () {
      if (this.check.toString() === '2') {
        this.$refs.popupSearchContract.showModal()
      }
    },
    async acceptSearchContract (item) {
      console.log('🚀 ~ file: index.vue ~ line 235 ~ acceptSearchContract ~ item', item)
      this.txtHopDong = item.ma_tb
      if (this.txtHopDong !== '') {
        await this.txtHopDong_KeyUp()
      }
    },
    grcDanDo_selectedRowChanged (row) {
      console.log('🚀 ~ file: index.vue ~ line 118 ~ grcDanDo_selectedRowChanged ~ row', row)
    },
    grcDanDo_RowDataBound (args) {
      console.log('🚀 ~ file: index.vue ~ line 246 ~ grcDanDo_RowDataBound ~ args', args)
      if (args.data.STT % 2 === 1) {
        console.log('🚀 ~ file: index.vue ~ line 249 ~ grcDanDo_RowDataBound ~ args.data.STT', args.data.STT)
        args.row.classList.add('boldrow')
        // if (args.data.IDX.toString() === '0') {
        //   args.row.style.foreColor = 'lightgreen'
        // } else {
        //   args.row.style.foreColor = 'Black'
        // }
      }
    },
    async NAP_DS_DANDO () {
      try {
        this.$root.showLoading(true)
        var dt = []
        if (this.check.toString() === '3' || this.check.toString() === '4') {
          try {
            let data = {
              loaitbi_id: parseInt(this.LOAITB_ID_PORT(this.cboPORT).LOAITBI_ID),
              thietbi_id: parseInt(this.LOAITB_ID_PORT(this.cboPORT).THIETBI_ID),
              vitri: parseInt(this.cboPORT.EditValue),
              mat: 2
            }
            let rs = await this.$root.context.post('/web-cabman/tracuu/dan-do-cap-thuebao/dando-cap', data)
            console.log('🚀 ~ file: index.vue ~ line 128 ~ NAP_DS_DANDO ~ rs', rs)
            dt = rs.data
            this.grcDanDo = dt
          } catch (error) {
            console.log('🚀 ~ file: index.vue ~ line 131 ~ NAP_DS_DANDO ~ error', error)
            this.grcDanDo = []
          }
        } else if (this.grcDauNoi.length > 0 && this.rowDauNoi !== null && this.rowDauNoi !== undefined) {
          try {
            let data = {
              loaitbi_id: parseInt(this.rowDauNoi.LOAITBI_ID),
              thietbi_id: parseInt(this.rowDauNoi.THIETBI_ID),
              vitri: parseInt(this.rowDauNoi.VITRI),
              mat: 2
            }
            let rs = await this.$root.context.post('/web-cabman/tracuu/dan-do-cap-thuebao/dando-cap', data)
            dt = rs.data
            this.grcDanDo = dt
          } catch (error) {
            this.grcDanDo = []
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 249 ~ NAP_DS_DANDO ~ error', error)
      }
      this.$root.showLoading(false)
    },
    async NAP_DS_PORT () {
      if (this.check.toString() === '3') {
        try {
          let rs = await this.$root.context.get('/web-cabman/tracuu/dan-do-cap-thuebao/ds-port-theo-ketcuoi?kyhieu=' + this.txtKyHieuKC.Text)
          console.log('🚀 ~ file: index.vue ~ line 109 ~ NAP_DS_PORT ~ rs', rs)
          if (rs.data.length > 0) {
            this.cboPORT.Properties.DataSource = rs.data
            this.cboPORT.EditValue = rs.data[0].VITRI
          } else {
            this.cboPORT.Properties.DataSource = rs.data
            this.cboPORT.EditValue = rs.data[0].VITRI
          }
        } catch (error) {
        }
      } else {
        let tbi = null
        if (this.cboMODULE.EditValue.toString() !== '-1') {
          tbi = this.cboMODULE
        } else if (this.cboCARD.EditValue.toString() !== '-1') {
          tbi = this.cboCARD
        } else {
          tbi = this.cboTHIETBI
        }
        try {
          let rs = await this.$root.context.get('/web-cabman/tracuu/dan-do-cap-thuebao/ds-port?loaitbi_id=' + this.LOAITB_ID(tbi) + '&thietbi_id=' + tbi.EditValue)
          console.log('🚀 ~ file: index.vue ~ line 109 ~ NAP_DS_PORT ~ rs', rs)
          if (rs.data.length > 0) {
            this.cboPORT.Properties.DataSource = rs.data
            this.cboPORT.EditValue = rs.data[0].VITRI
          } else {
            this.cboPORT.Properties.DataSource = rs.data
            this.cboPORT.EditValue = rs.data[0].VITRI
          }
        } catch (error) {
        }
      }
    },
    LOAITB_ID (tbi) {
      // console.log('🚀 ~ file: index.vue ~ line 147 ~ LOAITB_ID ~ tbi', tbi)
      var e = tbi.Properties.DataSource.find(e => e.THIETBI_ID.toString() === tbi.EditValue.toString())
      if (e) return e.LOAITB_ID
      else return null
    },
    LOAITB_ID_PORT (tbi, loai) {
      // console.log('🚀 ~ file: index.vue ~ line 147 ~ LOAITB_ID ~ tbi', tbi)
      var e = tbi.Properties.DataSource.find(e => e.VITRI.toString() === tbi.EditValue.toString())
      if (e) return e
      else return null
    },
    RESET (level) {
      if (level <= 0) { this.cboTTVT.Properties.LockEvents = true }
      if (level <= 1) { this.cboTOKT.Properties.LockEvents = true }
      if (level <= 2) { this.cboTRAM.Properties.LockEvents = true }
      if (level <= 3) { this.cboLOAI_TBI.Properties.LockEvents = true }
      if (level <= 4) { this.cboTHIETBI.Properties.LockEvents = true }
      if (level <= 5) { this.cboCARD.Properties.LockEvents = true }
      if (level <= 6) { this.cboMODULE.Properties.LockEvents = true }
      if (level <= 7) { this.cboPORT.Properties.LockEvents = true }

      if (level <= 0) { this.cboTTVT.EditValue = null }
      if (level <= 1) { this.cboTOKT.EditValue = null }
      if (level <= 2) { this.cboTRAM.EditValue = null }
      if (level <= 3) { this.cboLOAI_TBI.EditValue = null }
      if (level <= 4) { this.cboTHIETBI.EditValue = null }
      if (level <= 5) { this.cboCARD.EditValue = null }
      if (level <= 6) { this.cboMODULE.EditValue = null }
      if (level <= 7) { this.cboPORT.EditValue = null }

      if (level <= 0) { this.cboTTVT.Properties.DataSource = [] }
      if (level <= 1) { this.cboTOKT.Properties.DataSource = [] }
      if (level <= 2) { this.cboTRAM.Properties.DataSource = [] }
      if (level <= 3) { this.cboLOAI_TBI.Properties.DataSource = [] }
      if (level <= 4) { this.cboTHIETBI.Properties.DataSource = [] }
      if (level <= 5) { this.cboCARD.Properties.DataSource = [] }
      if (level <= 6) { this.cboMODULE.Properties.DataSource = [] }
      if (level <= 7) { this.cboPORT.Properties.DataSource = [] }

      if (level <= 8) {
        this.grcDanDo = []
        this.grcDauNoi = []
      }

      if (level <= 0) { this.cboTTVT.Properties.LockEvents = false }
      if (level <= 1) { this.cboTOKT.Properties.LockEvents = false }
      if (level <= 2) { this.cboTRAM.Properties.LockEvents = false }
      if (level <= 3) { this.cboLOAI_TBI.Properties.LockEvents = false }
      if (level <= 4) { this.cboTHIETBI.Properties.LockEvents = false }
      if (level <= 5) { this.cboCARD.Properties.LockEvents = false }
      if (level <= 6) { this.cboMODULE.Properties.LockEvents = false }
      if (level <= 7) { this.cboPORT.Properties.LockEvents = false }
    },
    async CHUYEN_KIEU_TRACUU (kieu) {
      this.txtThueBao_ReadOnly = this.check.toString() !== '1'
      this.txtHopDong_ReadOnly = this.check.toString() !== '2'
      this.txtKyHieuKC_ReadOnly = this.check.toString() !== '3'

      this.lblTTVT_Visible = this.check.toString() === '4'
      this.cboTTVT.Visible = this.check.toString() === '4'

      this.lblTOKT_Visible = this.check.toString() === '4'
      this.cboTOKT.Visible = this.check.toString() === '4'

      this.lblTRAM_Visible = this.check.toString() === '4'
      this.cboTRAM.Visible = this.check.toString() === '4'

      this.lblLOAI_TBI_Visible = this.check.toString() === '4'
      this.cboLOAI_TBI.Visible = this.check.toString() === '4'

      this.lblTHIETBI_Visible = this.check.toString() === '4'
      this.cboTHIETBI.Visible = this.check.toString() === '4'

      this.lblCARD_Visible = this.check.toString() === '4'
      this.cboCARD.Visible = this.check.toString() === '4'

      this.lblMODULE_Visible = this.check.toString() === '4'
      this.cboMODULE.Visible = this.check.toString() === '4'

      this.lblPORT_Visible = this.check.toString() === '3' || this.check.toString() === '4'
      this.cboPORT.Visible = this.check.toString() === '3' || this.check.toString() === '4'

      this.lblThongTinTB_Visible = this.check.toString() === '1' || this.check.toString() === '2'

      this.spcDanDo_Panel1Collapsed = this.check.toString() !== '1' && this.check.toString() !== '2'

      if (kieu === 1 || kieu === 2) { await this.NAP_DS_DAUNOI() } else if (kieu === 3) {
        this.RESET(7)
        await this.NAP_DS_PORT()
      } else if (kieu === 4) {
        this.RESET(0)
        await this.NAP_DS_TTVT()
      }
      console.log('🚀 ~ file: index.vue ~ line 417 ~ CHUYEN_KIEU_TRACUU ~ this.check', this.check)
      if (this.check.toString() === '1') {
        this.$refs.thuebao.focus()
      }
      if (this.check.toString() === '2') {
        this.$refs.hopdong.focus()
      }
      if (this.check.toString() === '3') {
        this.$refs.kyhieukc.focus()
      }
    }
  },
  created () {

  },
  data () {
    return {
      showDanhSachDauNoi: true,
      spcDanDo_Panel1Collapsed: false,
      lblTTVT_Visible: false,
      lblTOKT_Visible: false,
      lblTRAM_Visible: false,
      lblLOAI_TBI_Visible: false,
      lblMODULE_Visible: false,
      lblCARD_Visible: false,
      lblPORT_Visible: false,
      lblThongTinTB_Visible: true,
      lblTHIETBI_Visible: false,
      txtThueBao_ReadOnly: false,
      txtHopDong_ReadOnly: true,
      txtKyHieuKC_ReadOnly: true,
      wbrThongTinTB: '',
      CULY: {AllowEdit: false},
      GHICHU: {AllowEdit: false},
      thuebao: null,
      txtThueBao: '',
      txtHopDong: '',
      txtKyHieuKC: {
        Properties: {
          ReadOnly: false
        },
        Text: ''
      },
      grcDanDo: [],
      rowDauNoi: null,
      grcDauNoi: [],
      check: '1',
      cboTTVT: {
        Properties: {
          DataSource: [],
          LockEvents: false
        },
        EditValue: null,
        Visible: false
      },
      cboTOKT: {
        Properties: {
          DataSource: [],
          LockEvents: false
        },
        EditValue: null,
        Visible: false
      },
      cboTRAM: {
        Properties: {
          DataSource: [],
          LockEvents: false
        },
        EditValue: null,
        Visible: false
      },
      cboLOAI_TBI: {
        Properties: {
          DataSource: [],
          LockEvents: false
        },
        EditValue: null,
        Visible: false
      },
      cboTHIETBI: {
        Properties: {
          DataSource: [],
          LockEvents: false
        },
        EditValue: null,
        Visible: false
      },
      cboCARD: {
        Properties: {
          DataSource: [],
          LockEvents: false
        },
        EditValue: null,
        Visible: false
      },
      cboMODULE: {
        Properties: {
          DataSource: [],
          LockEvents: false
        },
        EditValue: null,
        Visible: false
      },
      cboPORT: {
        Properties: {
          DataSource: [],
          LockEvents: false
        },
        EditValue: null,
        Visible: false
      }
    }
  },
  watch: {
    check (val) {
      this.CHUYEN_KIEU_TRACUU(parseInt(val))
      this.showDanhSachDauNoi = !!(parseInt(val) !== 3 && parseInt(val) !== 4)
    },
    'cboTTVT.EditValue' (val) {
      if (val !== null) {
        this.RESET(1)
        this.NAP_DS_TOVT(val)
      }
    },
    'cboTOKT.EditValue' (val) {
      if (val !== null) {
        this.RESET(2)
        this.NAP_DS_TRAM(this.cboTTVT.EditValue, val)
      }
    },
    'cboTRAM.EditValue' (val) {
      if (val !== null) {
        this.RESET(3)
        this.NAP_DS_LOAI_TBI(val)
      }
    },
    'cboLOAI_TBI.EditValue' (val) {
      if (val !== null) {
        this.RESET(4)
        this.NAP_DS_THIETBI(val, this.cboTRAM.EditValue)
      }
    },
    'cboTHIETBI.EditValue' (val) {
      if (val !== null) {
        this.RESET(5)
        this.NAP_DS_CARD(this.LOAITB_ID(this.cboTHIETBI), val)
      }
    },
    'cboCARD.EditValue' (val) {
      if (val !== null) {
        this.RESET(6)
        this.NAP_DS_MODULE(this.LOAITB_ID(this.cboCARD), this.cboCARD.EditValue)
      }
    },
    'cboMODULE.EditValue' (val) {
      if (val !== null) {
        this.RESET(7)
        this.NAP_DS_PORT(val)
      }
    },
    'cboPORT.EditValue' (val) {
      if (val !== null) {
        this.RESET(8)
        this.NAP_DS_DANDO()
      }
    }
  },
  async mounted () {

  }
}
</script>
<style src="./index.scss">
</style>
