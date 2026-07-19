<template src="./index.template.html"></template>
<script>
let getDateElements = (date) => {
  date = new Date(date)

  let day = date.getDate()
  day = day > 9 ? day : '0' + day

  let month = date.getMonth() + 1
  month = month > 9 ? month : '0' + month

  let hour = date.getHours()
  hour = hour > 9 ? hour : '0' + hour

  let min = date.getMinutes()
  min = min > 9 ? min : '0' + min

  let second = date.getSeconds()
  second = second > 9 ? second : '0' + second

  return {
    y: date.getFullYear(),
    M: month,
    d: day,
    h: hour,
    m: min,
    s: second
  }
}

let ddmmyyyy = (date) => {
  if (!date) {
    return ''
  }

  date = getDateElements(date)
  return `${date.d}/${date.M}/${date.y}`
}

let ddmmyyyyhhmm = (date) => {
  if (!date) {
    return ''
  }

  date = getDateElements(date)
  return `${date.d}/${date.M}/${date.y} ${date.h}:${date.m}`
}

let convertSecondToHms = function(seconds) {
  let hours = Math.floor(seconds / 3600)
  let minutes = Math.floor((seconds - hours * 3600) / 60)
  seconds = seconds - hours * 3600 - minutes * 60

  if (hours < 10) {
    hours = '0' + hours
  }
  if (minutes < 10) {
    minutes = '0' + minutes
  }
  if (seconds < 10) {
    seconds = '0' + seconds
  }
  return hours + ':' + minutes + ':' + seconds
}

import XemThongTin from '../capnhat-ketqua-xuly-suco/modal-xem-thongtin/index.vue'
import ModalGiaoPhieuNv from '../capnhat-ketqua-xuly-suco/modal-giaophieu-nv/index.vue'
import ModalInSuCo from './modal-in-suco/index.vue'
import XLSX from 'xlsx'
import ModalKhoaPhieu from '../khoa-phieu/index.vue'
import breadcrumb from '@/components/breadcrumb'
import apiHelper from './api.helper'

export default {
  components: {
    XemThongTin,
    ModalGiaoPhieuNv,
    ModalInSuCo,
    ModalKhoaPhieu,
    breadcrumb
  },
  data() {
    return {
      header: {
        title: 'TTS - Giao Phiếu xử lý sự cố',
        list: [
          { name: 'Giao Phiếu', link: { name: 'Ui.cards' } },
          { name: 'TTS - Giao Phiếu xử lý sự cố', link: { name: 'Ui.cards' } }
        ]
      },
      cboNguonSC: {
        Value: null,
        DataSource: [],
        Enabled: false
      },
      cboQuyTrinh: {
        Value: null,
        DataSource: [],
        Enabled: false
      },
      cboHuongGiao: {
        Value: null,
        DataSource: []
      },
      cboMucDoSC: {
        Value: null,
        DataSource: []
      },
      cboTrangThai: {
        Value: null,
        DataSource: []
      },
      ccbDonViNhan: {
        Value: null,
        DataSource: []
      },
      cboMucDoKC: {
        Value: null,
        DataSource: []
      },
      cboBuocXuLy: {
        Value: null,
        DataSource: []
      },
      cboQuyTrinhXuLy: {
        Value: null,
        DataSource: []
      },
      txtNoiDung: {
        Text: ''
      },
      txtThongTinThietBi: {
        Text: ''
      },
      txtNoiDungTraoDoi: {
        Text: ''
      },
      txtMaSC: {
        Text: ''
      },
      txtQuyTrinhNET: {
        Text: ''
      },
      btnGiaoPhieu: {
        Enabled: false
      },
      btnHuyGiao: {
        Enabled: false,
        Visible: false
      },
      btnCapNhat: {
        Enabled: false
      },
      btnKhoaPhieu: {
        Enabled: false,
        Visible: true
      },
      lblThietHaiDT: {
        Visible: false
      },
      cbxNguonSC: {
        Checked: false
      },
      cbxQuyTrinh: {
        Checked: false
      },
      cbxNgayGiao: {
        Checked: true
      },
      gridPhieuDaGiao: {
        DataSource: [],
        idPhieuDuocChon: null,
        phieuDuocChon: null
      },
      grvPhieu: {
        DataSource: [],
        allowFilter: true,
        idSuCoDuocChon: null,
        suCoDuocChon: null
      },
      dtpNgayGiao: {
        DateTime: new Date(),
        Enabled: true
      },
      vnguonsc_id: -1,
      vphieu_cha_id: 0,
      vnhomsc_id: -1,
      vnhom_sc: '',
      l_mota_hethong: '',
      l_ma_qt_net: '',
      l_phieu_id: 0,
      l_ttph_id: 1
    }
  },
  computed: {
    vnguonsc_id_tk() {
      return !this.cbxNguonSC.Checked ? 0 : parseInt(!this.cboNguonSC.Value ? -1 : this.cboNguonSC.Value)
    },
    vhuonggiao_id() {
      return !this.cboHuongGiao.Value ? -1 : parseInt(this.cboHuongGiao.Value)
    },
    vnd_giao() {
      return this.txtNoiDungTraoDoi.Text
    },
    vsuco_id() {
      return !this.grvPhieu.idSuCoDuocChon ? -1 : this.grvPhieu.idSuCoDuocChon
    },
    vtrangthai_tk() {
      return !this.cboTrangThai.Value ? -1 : parseInt(this.cboTrangThai.Value)
    },
    vdonvi_nhan_id() {
      switch (this.vtrangthai_tk) {
        case 1:
        case 3:
          return !this.ccbDonViNhan.Value ? '' : this.ccbDonViNhan.Value
        default:
          return ''
      }
    },
    vkhan() {
      return !this.cboMucDoKC.Value ? -1 : parseInt(this.cboMucDoKC.Value)
    },
    vma_sc_tk() {
      return !this.txtMaSC.Text ? '' : this.txtMaSC.Text.trim()
    },
    vngaygiao_tk() {
      return this.cbxNgayGiao.Checked ? ddmmyyyy(this.dtpNgayGiao.DateTime) : null
    },
    vmucdosc_id_tk() {
      return parseInt(this.cboMucDoSC.Value)
    }
  },
  watch: {
    'grvPhieu.DataSource': function() {
      this.grvPhieu.idSuCoDuocChon = null
      this.grvPhieu.suCoDuocChon = null
      this.XOA_FORM()
    },
    'gridPhieuDaGiao.DataSource': function() {
      this.gridPhieuDaGiao.idPhieuDuocChon = null
      this.gridPhieuDaGiao.phieuDuocChon = null
    },
    'ccbDonViNhan.DataSource': function(value) {
      if (!value || value.length == 0) {
        this.ccbDonViNhan.Value = null
        return
      }

      if (!!this.gridPhieuDaGiao.phieuDuocChon) {
        let phieuDuocChon = this.gridPhieuDaGiao.phieuDuocChon
        setTimeout(() => {
          this.$refs['ref-ccb-don-vi-nhan'].$children[0].selectRowByRowData(phieuDuocChon['DONVI_NHAN_ID'])
        }, 300)
      }
    },
    'cbxQuyTrinh.Checked': function() {
      this.cbxQuyTrinh_CheckedChanged()
    },
    'cbxNguonSC.Checked': function() {
      this.cbxNguonSC_CheckedChanged()
    },
    'cboQuyTrinh.Value': function() {
      this.cboQuyTrinh_EditValueChanged()
    },
    'cboHuongGiao.Value': function() {
      this.cboHuongGiao_EditValueChanged()
    },
    'grvPhieu.suCoDuocChon': function(value) {
      this.repoCHON_CheckedChanged(value)
    },
    'gridPhieuDaGiao.phieuDuocChon': function(value) {
      this.reposCHON_CheckedChanged(value)
    },
    'cbxNgayGiao.Checked': function() {
      this.cbxNgayGiao_CheckedChanged()
    },
    'cboTrangThai.Value': function() {
      this.cboTrangThai_EditValueChanged()
    },
    'cboMucDoSC.Value': function() {
      this.cboMucDoSC_EditValueChanged()
    },
    'cboNguonSC.Value': function() {
      this.cboNguonSC_EditValueChanged()
    },
    'dtpNgayGiao.DateTime': function() {
      this.dtpNgayGiao_EditValueChanged()
    },
    'cboBuocXuLy.Value': function() {
      this.cboBuocXuLy_EditValueChanged()
    }
  },
  async mounted() {
    await this.frmGiaoPhieu_NET()

    setInterval(() => {
      let gridSuCo = this.$refs['grid-suco']

      if (!gridSuCo) {
        return
      }

      gridSuCo = gridSuCo.grid

      if (!gridSuCo.dataSource || gridSuCo.dataSource.length == 0) {
        return
      }

      gridSuCo.dataSource.forEach((item) => ++item.SECOND)

      gridSuCo.getCurrentViewRecords().forEach((item) => {
        let hms = convertSecondToHms(item.SECOND)
        gridSuCo.setCellValue(item.KEY, 'THOIGIAN', hms)
      })
    }, 1000)
  },
  methods: {
    btnHuyGiao_Click() {},
    btnKhoaPhieu_Click() {
      if (!this.btnKhoaPhieu.Enabled) {
        return
      }

      let modal = this.$refs['ref-modal-khoaphieu']
      modal.setNhomScId(this.vnhomsc_id)
      modal.setNhomScText(this.vnhom_sc)
      modal.setSuCoId(this.vsuco_id)
      modal.setNguonScId(this.vnguonsc_id)
      modal.setKhoaPhieu(true)
      this.$bvModal.show('modal-khoaphieu')
    },
    async onSuccessKhoaPhieu() {
      await this.NAP_DS_PHIEU()
    },
    btnXuatExcel_DSPH_Click() {
      if (!this.grvPhieu.DataSource || this.grvPhieu.DataSource.length == 0) {
        this.$root.toastError('Không có dữ liệu để xuất')
        return
      }

      let gridSuCo = this.$refs['grid-suco']
      let excelExportProperties = {
        dataSource: this.grvPhieu.DataSource
      }
      gridSuCo.excelExport(excelExportProperties)
      // let exportData = []
    },
    btnLamMoi_DSPH_Click() {
      this.btnTimKiem_Click()
    },
    btnIn_DSPH_Click() {
      if (!this.grvPhieu.DataSource || this.grvPhieu.DataSource.length == 0) {
        this.$root.toastError('Không có dữ liệu để in')
        return
      }

      this.$refs['ref-modal-in-suco'].setData({
        dsSuCo: this.grvPhieu.DataSource,
        suCoId: this.grvPhieu.idSuCoDuocChon
      })

      this.$bvModal.show('frm-giao-phieu-net-modal-in-suco')
    },
    async btnCapNhat_Click() {
      if (!this.btnCapNhat.Enabled) {
        return
      }

      if (!this.grvPhieu.suCoDuocChon) {
        this.$root.toastError('Hãy chọn phiếu để cập nhật')
        return
      }

      if (!this.grvPhieu.suCoDuocChon.PHIEU_ID) {
        this.$root.toastError('Phiếu chưa giao đi không thể cập nhật')
        return
      }

      await this.CAPNHAT()
    },
    async btnGiaoPhieu_Click() {
      if (!this.btnGiaoPhieu.Enabled) {
        return
      }

      if (!this.vnd_giao) {
        this.$root.toastError('Hãy nhập nội dung giao')
        this.$refs['ref-txt-noi-dung-trao-doi'].focus()
        return
      }

      await this.GIAOPHIEU()

      this.$emit('success')
    },
    repoGIAOVIEC_NV_ButtonClick(data) {
      let modal = this.$refs['ref-modal-giaophieu-nv']
      modal.setPhieuId(data.PHIEU_ID)
      modal.setSuCoId(this.vsuco_id)
      modal.setSuCoPtmId(data.SUCO_PTM_ID)
      modal.show()
    },
    getColumnTemplateChucNangGiaoViec(parent) {
      return function() {
        return {
          template: {
            template: `
                            <div>
                                <span title="Giao việc nhân viên">
                                    <button class="btn btn-sm btn-second" @click="parent.repoGIAOVIEC_NV_ButtonClick(data)">
                                        <span>Giao việc</span>
                                    </button>
                                </span>
                            </div>
                        `,
            data() {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
    cboBuocXuLy_EditValueChanged() {
      this.NAP_DS_DONVI(this.l_ma_qt_net, this.cboBuocXuLy.Value)
    },
    btnXemTTHT_Click() {
      let info = !this.l_mota_hethong ? 'Không có thông tin' : this.l_mota_hethong
      this.$refs['ref-modal-xem-thongtin'].setTitle('Thông tin hệ thống')
      this.$refs['ref-modal-xem-thongtin'].setContent(info)
      this.$bvModal.show('frm-giao-phieu-net-modal-id-xem-thong-tin-tbi')
    },
    dtpNgayGiao_EditValueChanged() {
      this.NAP_DS_PHIEU()
    },
    cboNguonSC_EditValueChanged() {
      this.NAP_DS_PHIEU()
    },
    cboMucDoSC_EditValueChanged() {
      this.NAP_DS_PHIEU()
    },
    txtMaSC_KeyUp() {
      this.NAP_DS_PHIEU()
    },
    cboTrangThai_EditValueChanged() {
      this.NAP_DS_PHIEU()
    },
    cbxNgayGiao_CheckedChanged() {
      this.dtpNgayGiao.Enabled = this.cbxNgayGiao.Checked
      this.NAP_DS_PHIEU()
    },
    reposCHON_CheckedChanged(phieuDuocChon) {
      if (!phieuDuocChon) {
        this.txtNoiDungTraoDoi.Text = ''
        this.ccbDonViNhan.Value = null
        this.cboBuocXuLy.Value = null
        this.btnGiaoPhieu.Enabled = false
        this.btnHuyGiao.Enabled = false
        this.btnCapNhat.Enabled = false
        this.btnKhoaPhieu.Enabled = false
        return
      }

      this.NAP_NOIDUNG_PHIEU(phieuDuocChon)
    },
    getColumnTemplateChonPhieuGiao(parent) {
      return function() {
        return {
          template: {
            template: `
                            <div class="check-action" @click="onClick()">
                                <input
                                    type="radio"
                                    class="check"
                                    v-model="parent.gridPhieuDaGiao.idPhieuDuocChon"
                                    :value="data.PHIEU_ID"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                parent: parent,
                data: {}
              }
            },
            computed: {
              isSelected() {
                return this.parent.gridPhieuDaGiao.idPhieuDuocChon == this.data.PHIEU_ID
              }
            },
            methods: {
              onClick() {
                if (this.isSelected) {
                  this.parent.gridPhieuDaGiao.idPhieuDuocChon = null
                  this.parent.gridPhieuDaGiao.phieuDuocChon = null
                } else {
                  this.parent.gridPhieuDaGiao.idPhieuDuocChon = this.data.PHIEU_ID
                  this.parent.gridPhieuDaGiao.phieuDuocChon = this.data
                }
              }
            }
          }
        }
      }
    },
    repoCHON_CheckedChanged(suCoDuocChon) {
      if (!suCoDuocChon) {
        this.XOA_FORM()
        this.btnGiaoPhieu.Enabled = false
        this.btnHuyGiao.Enabled = false
        this.btnCapNhat.Enabled = false
        this.btnKhoaPhieu.Enabled = false
        return
      }

      this.NAP_TT_PHIEU(suCoDuocChon)
      this.btnGiaoPhieu.Enabled = [1, 3].includes(this.vtrangthai_tk)
      this.btnCapNhat.Enabled = this.vtrangthai_tk == 2
      this.btnHuyGiao.Enabled = [2, 3].includes(this.vtrangthai_tk)
      this.btnKhoaPhieu.Enabled = true
    },
    onQueryCellInfoSuCo(args) {

        const {data} = args;

        // if(data && data.FMS_CLEAR_TIME)  {
        //     args.cell.style.setProperty('background-color', `#40A7E2`, 'important')
        // }

        if (!!args.column.field) {
            args.cell.title = args.cell.innerHTML
        }
    },
    gridSuCo_RowDataBound: function(args){
      let second = args.data.SECOND;
      if(second < (60 * 3600)){
        if(args.data.FMS_CLEAR_TIME)
          args.row.classList.add("e-row-lev2");
        else
          args.row.classList.add("e-row-lev1");
      }
      else if(second < (72 * 3600)){
        args.row.classList.add("e-row-lev3");
      }
      else {
        args.row.classList.add("e-row-lev4");
      }
    },
    getColumnTemplateChonSuCo(parent) {
      return function() {
        return {
          template: {
            template: `
                            <div class="check-action" @click="onClick()">
                                <input
                                    type="radio"
                                    class="check"
                                    v-model="parent.grvPhieu.idSuCoDuocChon"
                                    :value="data.SUCO_ID"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                parent: parent,
                data: {}
              }
            },
            computed: {
              isSelected() {
                return this.parent.grvPhieu.idSuCoDuocChon == this.data.SUCO_ID
              }
            },
            methods: {
              onClick() {
                if (this.isSelected) {
                  this.parent.grvPhieu.idSuCoDuocChon = null
                  this.parent.grvPhieu.suCoDuocChon = null
                } else {
                  this.parent.grvPhieu.idSuCoDuocChon = this.data.SUCO_ID
                  this.parent.grvPhieu.suCoDuocChon = this.data
                }
              }
            }
          }
        }
      }
    },
    cboHuongGiao_EditValueChanged() {
      this.NAP_DS_PHIEU()
    },
    btnTimKiem_Click() {
      this.NAP_DS_PHIEU()
    },
    cboQuyTrinh_EditValueChanged() {
      this.NAP_DS_HUONGGIAO()
    },
    cbxNguonSC_CheckedChanged() {
      this.cboNguonSC.Enabled = this.cbxNguonSC.Checked
      this.NAP_DS_PHIEU()
    },
    cbxQuyTrinh_CheckedChanged() {
      this.cboQuyTrinh.Enabled = this.cbxQuyTrinh.Checked
      this.NAP_DS_HUONGGIAO()
    },
    async frmGiaoPhieu_NET() {
      let KIEMTRA_QUYEN_TRUYCAP = await this.KIEMTRA_QUYEN_TRUYCAP()

      if (!KIEMTRA_QUYEN_TRUYCAP) {
        this.$root.toastError('Bạn không có quyền truy cập chức năng này. \nHãy liên hệ Quản trị hệ thống hoặc quản lý của bạn để được cấp quyền.')
        return
      }

      try {
        this.loading(true)
        await this.NAP_DS_NGUON_SC()
        await this.NAP_DS_QUYTRINH()
        await this.NAP_DS_HUONGGIAO()
        await this.getCboQuyTrinhXLSuCoTTS()
        await this.NAP_DS_MUCDO_SC()
        await this.NAP_DS_TRANGTHAI()
        await this.NAP_DS_MUCDO_KC()
        await this.NAP_DS_PHIEU()

      } finally {
        this.loading(false)
      }
    },
    async KIEMTRA_QUYEN_TRUYCAP() {
      let dtQuyTrinh = await this.layDmQuyTrinh()
      return dtQuyTrinh.length != 0
    },
    async CAPNHAT() {
      let data = {
        phieu_id: this.grvPhieu.suCoDuocChon.PHIEU_ID,
        donvi_nhan_id: this.vdonvi_nhan_id,
        nd_giao: this.vnd_giao,
        khan: this.vkhan,
        chitieu_tg: null
      }

      try {
        this.loading(true)
        await this.capNhatPhieu(data)
        this.$root.toastSuccess('Cập nhật phiếu thành công')
      } catch (err) {
        this.$root.toastError(err)
        return
      } finally {
        this.loading(false)
      }
    },
    async capNhatPhieu(data) {
      try {
        let response = await this.$root.context.post(`/web-suco/giao-phieu-xu-li-su-co/capnhat-phieu-xuly`, data)

        if (response.error_code !== 'BSS-00000000') {
          throw { response: { data: response } }
        }
      } catch (err) {
        let responseData = err.response.data
        let message = []

        if (responseData.message) {
          message.push(responseData.message)
        }

        if (responseData.message_detail) {
          message.push(responseData.message_detail)
        }

        if (responseData.data) {
          let detailMessage = Object.getOwnPropertyNames(responseData.data).map((item) => responseData.data[item])
          message.push([...new Set(detailMessage)].join('\n'))
        }

        throw message.join('\n')
      }
    },
    async HUYGIAO_NET() {
      // button hủy giao đã bị ẩn và chưa có procedure để xử lý
    },
    async GIAOPHIEU() {
      //comment if theo yeu cau The Anh
      //if (this.vnguonsc_id_tk == 5 || this.vnguonsc_id_tk == 6) {

      let data = {
        type: 2,
        phieu_cha_id: this.vphieu_cha_id,
        nd_giao: this.vnd_giao,
        donvi_nhan_id: this.vdonvi_nhan_id,
        ma_quytrinh: null,
        ma_buoc: this.cboBuocXuLy.Value
      }

      try {
        this.loading(true)
        let result = await this.giaoPhieuXuLyNet(data)

        this.$root.toastSuccess('Giao phiếu thành công')

        await this.handleGiaoPhieuSuccess(this.vsuco_id)
        await this.NAP_DS_PHIEU_DAGIAO(this.vsuco_id)

        if (this.vnguonsc_id_tk == 6) {
          try {
            let cssId = result[0]['GIAOPHIEU_ID']
            let jsSendTTS = await this.get_json_dhsx_send_to_tts(cssId)
            let objTTS = await this.send_dhsx_to_tts(jsSendTTS)

            if (objTTS.STATUS == 1) {
              await this.capnhat_api_net_problems(cssId)
            }
          } catch (err) {
            console.log(err)
          }
        }
      } catch (err) {
        console.log(err)
        this.$toast.error(err)
      } finally {
        this.loading(false)
      }
    },
    async send_dhsx_to_tts(jsonData) {
      try {
        let response = await this.$root.context.post(`/tichhop/fms_alarms/post_net_dhsx_to_tts`, { jsonData })
        return JSON.parse(response.data)
      } catch (err) {
        return { STATUS: 0 }
      }
    },
    async capnhat_api_net_problems(css_id) {
      try {
        let response = await this.$root.context.post('/web-suco/giao-phieu-xu-ly-su-co-tts/capnhat_api_net_problems', { css_id })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data[0].KQ
        }

        throw { response: { data: response } }
      } catch (err) {
        return 0
      }
    },
    async get_json_dhsx_send_to_tts(css_id) {
      try {
        let response = await this.$root.context.get('/web-suco/giao-phieu-xu-ly-su-co-tts/get_json_dhsx_send_to_tts', { css_id })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return JSON.stringify(response.data[0])
        }

        throw { response: { data: response } }
      } catch (err) {
        return null
      }
    },
    async giaoPhieuXuLyNet(data) {
      try {
        let response = await this.$root.context.post('/web-suco/giao-phieu-xu-ly-su-co-tts/giaophieu_xuly_net', data)

        if (response.error_code !== 'BSS-00000000') {
          throw { response: { data: response } }
        } else {
          return response.data
        }
      } catch (err) {
        let responseData = err.response.data
        let message = []

        if (responseData.message) {
          message.push(responseData.message)
        }

        if (responseData.message_detail) {
          message.push(responseData.message_detail)
        }

        if (responseData.data) {
          let detailMessage = Object.getOwnPropertyNames(responseData.data).map((item) => responseData.data[item])
          message.push([...new Set(detailMessage)].join('\n'))
        }

        throw message.join('\n')
      }
    },
    async giaoPhieu(data) {
      try {
        let response = await this.$root.context.post(`/web-suco/giao-phieu-xu-li-su-co/giaophieu-xuly`, data)

        if (response.error_code !== 'BSS-00000000') {
          throw { response: { data: response } }
        }
      } catch (err) {
        let responseData = err.response.data
        let message = []

        if (responseData.message) {
          message.push(responseData.message)
        }

        if (responseData.message_detail) {
          message.push(responseData.message_detail)
        }

        if (responseData.data) {
          let detailMessage = Object.getOwnPropertyNames(responseData.data).map((item) => responseData.data[item])
          message.push([...new Set(detailMessage)].join('\n'))
        }

        throw message.join('\n')
      }
    },
    onSelectDonViNhan(data) {
      this.ccbDonViNhan.Value = !data ? null : data.DONVI_ID
    },
    NAP_NOIDUNG_PHIEU(row) {
      this.vphieu_cha_id = row['PHIEU_ID']
      this.l_phieu_id = row['PHIEU_ID']
      this.l_ttph_id = row['TTPH_ID']
      this.txtNoiDungTraoDoi.Text = ''
      this.btnGiaoPhieu.Enabled = [1, 2, 3, 4].includes(this.l_ttph_id)
      this.btnCapNhat.Enabled = [1, 2].includes(this.l_ttph_id)
      this.btnHuyGiao.Enabled = [2, 3].includes(this.l_ttph_id)
      this.btnKhoaPhieu.Enabled = true

      if (parseInt(this.cboBuocXuLy.Value) != row['MA_BUOC']) {
        this.cboBuocXuLy.Value = row['MA_BUOC']
      } else if (this.ccbDonViNhan.DataSource.length > 0) {
        setTimeout(() => {
          this.$refs['ref-ccb-don-vi-nhan'].$children[0].selectRowByRowData(row['DONVI_NHAN_ID'])
        }, 300)
      }
    },
    async NAP_TT_PHIEU(row) {
      this.vphieu_cha_id = row['PHIEU_ID']
      this.vnhomsc_id = row['NHOMSC_ID']
      this.vnhom_sc = row['NHOM_SC']
      this.txtNoiDung.Text = row['NOIDUNG_SC']
      this.txtThongTinThietBi.Text = row['THONGTINTHIETBI']
      this.l_mota_hethong = row['MOTA_THIETBI']
      this.l_ma_qt_net = row['MA_QT_NET']
      this.txtQuyTrinhNET.Text = row['QUYTRINH_NET']
      this.ccbDonViNhan.Value = null
      this.vnguonsc_id = row['NGUONSC_ID']

      try {
        this.loading(true)
        await this.NAP_DS_BUOC_XULY(this.l_ma_qt_net, '1')
        await this.NAP_DS_PHIEU_DAGIAO(row['SUCO_ID'])
      } finally {
        this.loading(false)
      }
    },
    XOA_FORM() {
      if (this.cboMucDoKC.DataSource.length == 0) {
        this.cboMucDoKC.Value = null
      } else {
        this.cboMucDoKC.Value = this.cboMucDoKC.DataSource[0].MUCDO_ID
      }

      this.txtNoiDung.Text = ''
      this.txtThongTinThietBi.Text = ''
      this.txtNoiDungTraoDoi.Text = ''
      this.ccbDonViNhan.Value = null
      this.btnGiaoPhieu.Enabled = false
      this.btnHuyGiao.Enabled = false
      this.btnCapNhat.Enabled = false
      this.btnKhoaPhieu.Enabled = false
      this.lblThietHaiDT.Visible = false
      this.vnguonsc_id = -1
      this.gridPhieuDaGiao.DataSource = []
    },
    async NAP_DS_PHIEU_DAGIAO(p_suco_id) {
      try {
        let response = await this.$root.context.get('/web-suco/giao-phieu-xu-ly-su-co-tts/lay_ds_phieu_dagiao', { suco_id: p_suco_id })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          response.data.forEach((item, index) => {
            item.KEY = index + 1
          })

          this.gridPhieuDaGiao.DataSource = response.data
          return
        }

        throw { response: { data: response } }
      } catch (err) {
        this.gridPhieuDaGiao.DataSource = []
      }
    },
    async NAP_DS_PHIEU() {
      if (this.vtrangthai_tk == -1) {
        return
      }

      let data = {
        vnguonsc_id: this.vnguonsc_id_tk,
        vma_sc: this.vma_sc_tk,
        vngaygiao: this.vngaygiao_tk,
        vtrangthai: this.vtrangthai_tk,
        vmucdosc_id: this.vmucdosc_id_tk,
        vkeodai: null,
        vhuonggiao_id: this.vhuonggiao_id
      }

      try {
        this.loading(true)
         //update giao phieu v4 BSS-114915
      
        let listSuCo = await this.getDanhSachListSuCoTTS()

        this.grvPhieu.DataSource = listSuCo.map((item, index) => {
            item.KEY = index + 1
            // item.NGAY_SC = ddmmyyyyhhmm(item.NGAY_SC)

            let hms = !item.THOIGIAN ? ['0'] : `${item.THOIGIAN}`.split(':')
            let second = 0

            switch (hms.length) {
              case 3:
                second = parseInt(hms[0] * 3600) + parseInt(hms[1] * 60) + parseInt(hms[2])
                break
              case 2:
                second = parseInt(hms[0] * 60) + parseInt(hms[1])
                break
              case 1:
                second = parseInt(hms[0])
                break
              default:
                second = 0
            }

            item.SECOND = second

            item.NOIDUNG_SC_HIENTHI = item.NOIDUNG_SC

            if (!!item.DS_THIETBI) {
              item.NOIDUNG_SC_HIENTHI += ` (${item.DS_THIETBI})`
            }
            return item
          })
      } catch (err) {
        this.grvPhieu.DataSource = []
      } finally {
        this.loading(false)
      }
    },
    async NAP_DS_BUOC_XULY(i_ma_qt, i_ma_buoc) {
      let data = {
        ma_qt: i_ma_qt,
        ma_buoc: i_ma_buoc
      }

      try {
        let response = await this.$root.context.post('/web-suco/giao-phieu-xu-ly-su-co-tts/lay_ds_qt_buoc_xuly', data)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          this.cboBuocXuLy.DataSource = response.data
        } else {
          throw { response: { data: response } }
        }
      } catch (err) {
        this.cboBuocXuLy.DataSource = []
      }

      if (this.cboBuocXuLy.DataSource.length == 0) {
        this.cboBuocXuLy.Value = null
        return
      }

      this.cboBuocXuLy.Value = this.cboBuocXuLy.DataSource[0].MA_BUOC
    },
    async NAP_DS_MUCDO_KC() {
      this.cboMucDoKC.DataSource = await this.layDmMucDoKhanCap()

      if (this.cboMucDoKC.DataSource.length > 0) {
        this.cboMucDoKC.Value = this.cboMucDoKC.DataSource[0].MUCDO_ID
      }
    },
    async layDmMucDoKhanCap() {
      try {
        let response = await this.$root.context.get('/web-suco/danhmuc/mucdo-khancap')

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    },
    async NAP_DS_DONVI(i_ma_qt, i_ma_buoc) {
      if (!i_ma_buoc) {
        this.ccbDonViNhan.DataSource = []
        return
      }

      let data = {
        ma_qt: i_ma_qt,
        ma_buoc: i_ma_buoc
      }

      try {
        let response = await this.$root.context.post('/web-suco/giao-phieu-xu-ly-su-co-tts/lay_donvi_theo_buoc_xuly', data)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          this.ccbDonViNhan.DataSource = response.data
        } else {
          throw { response: { data: response } }
        }
      } catch (err) {
        this.ccbDonViNhan.DataSource = []
      }
    },
    async NAP_DS_TRANGTHAI() {
      this.cboTrangThai.DataSource = await this.layDmThaoTac()

      if (this.cboTrangThai.DataSource.length > 0) {
        this.cboTrangThai.Value = this.cboTrangThai.DataSource[0].TRANGTHAI_ID
      }
    },
    async layDmThaoTac() {
      try {
        let response = await this.$root.context.get('/web-suco/danhmuc/ds-thaotac-giaophieu')

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    },
    async NAP_DS_MUCDO_SC() {
      this.cboMucDoSC.DataSource = await this.layDmMucDoSuCo()

      if (this.cboMucDoSC.DataSource.length > 0) {
        this.cboMucDoSC.Value = this.cboMucDoSC.DataSource[0].MUCDO_ID
      }
    },
    async layDmMucDoSuCo() {
      try {
        let response = await this.$root.context.get('/web-suco/danhmuc/mucdo-suco')

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return [{ MUCDO_ID: 0, MUCDO_SC: 'Tất cả', CANHBAO_DT: -1 }, ...response.data]
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    },
    async NAP_DS_NGUON_SC() {
      this.cboNguonSC.DataSource = await this.layDmNguonSuCo()

      if (this.cboNguonSC.DataSource.length > 0) {
        this.cboNguonSC.Value = this.cboNguonSC.DataSource[0].NGUONSC_ID
      }
    },
    async layDmNguonSuCo() {
      try {
        let response = await this.$root.context.get('/web-suco/danhmuc/nguon-suco')

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          let dsNguonSc = []

          response.data.forEach((x) => {
            if (x.NGUONSC_ID == 0) {
              // loại bỏ item 'Tất cả'
              return
            }

            if (x.NGUONSC_ID == 6 /*TTS*/) {
              dsNguonSc.unshift(x)
            } else {
              dsNguonSc.push(x)
            }
          })

          return dsNguonSc
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    },
    async NAP_DS_QUYTRINH() {
      this.cboQuyTrinh.DataSource = await this.layDmQuyTrinh()

      if (this.cboQuyTrinh.DataSource.length > 0) {
        this.cboQuyTrinh.Value = this.cboQuyTrinh.DataSource[0].QUYTRINH_ID
      }
    },
    async layDmQuyTrinh() {
      try {
        let response = await this.$root.context.get('/web-suco/danhmuc/ds-quytrinh')

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          let dsQuyTrinh = []

          response.data.forEach((x) => {
            if (x.QUYTRINH.includes('TTS')) {
              // Điều hành xử lý sự cố TTS/ Công nghệ thông tin
              dsQuyTrinh.unshift(x)
            } else {
              dsQuyTrinh.push(x)
            }
          })

          return dsQuyTrinh
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    },
    async NAP_DS_HUONGGIAO() {
      let vquytrinh_id_tk = !this.cbxQuyTrinh.Checked ? 0 : !this.cboQuyTrinh.Value ? -1 : this.cboQuyTrinh.Value

      this.cboHuongGiao.DataSource = await this.layDmHuongGiao(vquytrinh_id_tk)

      if (this.cboHuongGiao.DataSource.length > 0) {
        this.cboHuongGiao.Value = this.cboHuongGiao.DataSource[0].HUONGGIAO_ID
      }
    },
    async layDmHuongGiao(quyTrinhId) {
      try {
        let response = await this.$root.context.get(`/web-suco/danhmuc/ds-huong-giao?quytrinh_id=${quyTrinhId}`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          let dsHuongGiao = []

          response.data.forEach((x) => {
            if (x.HUONGGIAO.toLowerCase().includes('công nghệ thông tin') || x.HUONGGIAO.toLowerCase().includes('cntt')) {
              dsHuongGiao.unshift(x)
            } else {
              dsHuongGiao.push(x)
            }
          })

          return dsHuongGiao
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    },

    async handleGiaoPhieuSuccess(sucoId) {

      const listSuco = await this.getDanhSachListSuCoTTS();
      let suco = listSuco.find(x => x.SUCO_ID == sucoId);
      if(suco) return;

      await this.NAP_DS_PHIEU()
    },
    async getCboQuyTrinhXLSuCoTTS() {
      let response = await this.$root.context.get('web-tracuu/tracuu/sp_lay_ds_quytrinh_suco_tts')
      this.cboQuyTrinhXuLy.DataSource = apiHelper.getDataFromResponseApiReturnArray(response)
      if (this.cboQuyTrinhXuLy.DataSource.length > 0)   this.cboQuyTrinhXuLy.Value = this.cboQuyTrinhXuLy.DataSource[0].ma_quytrinh
      
    },
    async getDanhSachListSuCoTTS(){
        const params = { 
        vnguonsc_id:  this.vnguonsc_id_tk, 
        vma_sc: this.vma_sc_tk, 
        vnet_ma_qt:this.cboQuyTrinhXuLy.Value, 
        vma_buoc: this.cboBuocXuLy.Value, 
        vngaygiao: this.vngaygiao_tk, 
        vtrangthai: this.vtrangthai_tk, 
        vmucdosc_id: this.vmucdosc_id_tk, 
        vkeodai: null, 
        vhuonggiao_id: this.vhuonggiao_id 
     }

      let response = await this.$root.context.post('/web-tracuu/tracuu/sp_lay_ds_phieu_xuly_net_v4', params)
      return apiHelper.getDataFromResponseApiReturnArray(response)
        
    }
  }
}
</script>
<style>
.tts-giaophieu-xuly-suco .disable-btn:hover {
  background-color: white !important;
}

.tts-giaophieu-xuly-suco .disable-btn a {
  color: #6c757d !important;
}

.tts-giaophieu-xuly-suco .disable-btn a:hover {
  cursor: default;
  color: #6c757d;
}

.tts-giaophieu-xuly-suco .grid-suco div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex='1'],
.tts-giaophieu-xuly-suco .grid-suco div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex='1'] > div,
.tts-giaophieu-xuly-suco .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex='1'],
.tts-giaophieu-xuly-suco .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex='1'] > div {
  padding: 0 !important;
}

.combo-donvi .grid-panel {
  right: 0;
}
.grid-suco.grid-ext .e-grid .e-row.e-row-lev2 {
    background-color: #3f97ff !important;
}
.grid-suco.grid-ext .e-grid .e-row.e-row-lev1 {
    background-color: #fff !important;
}
.grid-suco.grid-ext .e-grid .e-row.e-row-lev3 {
    background-color: #ff9419 !important;
}
.grid-suco.grid-ext .e-grid .e-row.e-row-lev4 {
    background-color: #ff0000 !important;
}
</style>
