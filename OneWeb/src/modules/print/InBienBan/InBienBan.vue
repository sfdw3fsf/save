<template>
  <div>
    <b-modal
      ref="popupInBienBan"
      id="popupInHopDong"
      size="lg"
      hide-header
      hide-header-close
      hide-footer
      body-class="p-0"
      no-close-on-backdrop
      @show="onShowModal"
      @hide="onHideModal"
    >
      <div class="modal-content popup-box">
        <div class="popup-header">
          <div class="title">
            <span class="icon one-notepad"></span> In biên bản
          </div>
          <div class="close -ap icon-close" @click="hide"></div>
        </div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li v-for="item in actions" :key="item.id" @click="onActionClick(item)"
              :class="{disabled: !item.active}" v-show="item.visible">
            <a style="cursor: pointer;" :class="{disabled_color: !item.active}" :ref="item.id">
              <span class="icon" :class="item.icon_class"></span> {{ item.name }}
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Danh sách mẫu in</div>
          <div class="info-row">
            <div class="key nowrap">
              <div class="check-action">
                <input type="checkbox" class="check" v-model="chkLuuIn.checked">
                <span class="name">Lưu in</span>
              </div>
            </div>
            <div class="value">
              <div class="select-custom">
                <Select2 ref="cboMauIn" :disabled='false' v-model="cboMauIn.value"
                         :options="cboMauIn.list" :settings="settingSelect2"/>
              </div>
            </div>
          </div>
        </div>
      </div>
    </b-modal>
    <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings"
                ref="dialogPreviewBienBan" :position="position" :header="'Xem biên bản'"
                showCloseIcon="true" width="80%" height="100%" target="#app" :isModal="true">
      <component :is="popupComponent" ref="generalPopup" :dulieu="popupComponentData" v-bind="popupComponentAttr"
                 isPopup="true" v-on="popupComponentEvts"></component>
    </ejs-dialog>
    <b-modal
      ref="popupXemThamSo"
      id="popupXemThamSo"
      size="lg"
      hide-header
      hide-header-close
      hide-footer
      body-class="p-0"
      no-close-on-backdrop
    >
      <div class="modal-content popup-box">
        <div class="popup-header">
          <div class="title">
            <span class="icon one-notepad"></span> Tham số báo cáo
          </div>
          <div class="close -ap icon-close" @click="hide1"></div>
        </div>
      </div>
      <div class="popup-body">
        <pre style="font-family: 'Roboto',serif; white-space: pre; font-size: 14px">{{ pretty(jsonstr) }}</pre>
      </div>
    </b-modal>
  </div>
</template>

<script>
import API from '../InBienBan/API'
import VuePdfApp from 'vue-pdf-app'
import 'vue-pdf-app/dist/icons/main.css'
import NHOM_BC from '../NhomBC'
import {LOAI_DV} from '../../../const/enums'

export default {
  name: 'InBienBan',
  components: {
    VuePdfApp
  },
  props: {
    params: {
      type: Object,
      default: function () {
        return null
      }
    }
  },
  data() {
    return {
      animationSettings: {
        effect: 'Zoom'
      },
      position: {X: 'center', Y: 'top'},
      targetTop: '#app',
      settingSelect2: {
        language: 'vi'
      },
      actions: [
        {
          id: 'tsbtnInBaoHong',
          name: 'In biên bản',
          active: true,
          icon_class: 'one-print',
          visible: true
        },
        {
          id: 'btnInHopDong',
          name: 'Biên bản',
          active: true,
          icon_class: 'one-print',
          visible: true
        },
        {
          id: 'btnXemThamSo',
          name: 'Xem tham số',
          active: false,
          icon_class: 'one-view-result',
          visible: true
        }
      ],
      popupComponent: null,
      popupComponentEvts: {},
      popupComponentData: null,
      popupComponentAttr: {},
      listOfParamNameValues: [],
      pdf: null,
      hdkh_id: 0,
      hdtb_id: 0,
      phieu_id: 53768878,
      huonggiao_id: 0,
      nvth_id: 0,
      cv_thuchien: '',
      ma_gd: '',
      ma_tb: '',
      ngay_lhd: '',
      ngay_yc: '',
      baohong: false,
      kt_thaydoi: false,
      tentram_tb: '',
      loaihd_id: 0,
      n_hdkh_id: '',
      n_hdtb_id: '',
      n_phieu_id: '',
      n_baohong_id: 0,
      donviql_id: 0,
      loaitien_id: 0,
      seri_id: 0,
      loaihoadon_id: 0,
      hoadon: '',
      fkey: '',
      thang_ld: '',
      vsuco_id: '',
      vgiaoviec_id: '',
      vnhanvien_id: '',
      quyettoan_id: '',
      magd: 0,
      thungan_id: '',
      ngay_tt: '',
      thang_tt: '',
      ma_tt: '',
      nguoi_cn: '',
      kieu: '',
      nhom_httt_id: '',
      httt_id: '',
      vdonviql_id: '',
      vnhanvienql_id: '',
      vtuyenthu_id: '',
      vdagan: '',
      thuebao_id: '',
      ds_phieu_id: '',
      baohanh_id: 0,
      donvinhan: '',
      donvigiao: '',
      chungtu_id: '',
      nhanvien_giao_id: '',
      nhanvien_nhan_id: '',
      dsluotgiao_id: '',
      dsxln_id: '',
      buocxln_id: '',
      kycuoc: '',
      dskn_id: '',
      dsxulyno_id: '',
      jsonstr: {},
      cboMauIn: {
        list: [],
        disable: false,
        value: 0
      },
      extension: {
        csv: 'csv',
        excel: 'xls',
        excel2000: 'xls',
        html: 'html',
        pdf: 'pdf',
        rtf: 'rft',
        xlsx: 'xlsx',
        xml: 'xml'
      },
      chkLuuIn: {
        checked: true,
        disable: false
      },
      tendvql: '',
      tentotruong: '',
      nhanvienid: Number(this.$root.token.getNhanVienID()),
      type: 'pdf',
      path: '',
      title: 'Biên bản',
      phanvungId: Number.parseInt(this.$root.token.getPhanVungID()),
      dsMauIn: []
    }
  },
  watch: {
    'cboMauIn.value': async function (newValue) {
      try {
        this.loading(true)
        let itemSelectIdx = this.dsMauIn.findIndex(item => item.baocao_id == newValue)
        let p = this.dsMauIn[itemSelectIdx].caulenh
        if (p) {
          this.path = this.dsMauIn[itemSelectIdx].caulenh.trim()
          await this.getReport(this.path)
          this.setActiveButton('btnXemThamSo', true)
        } else {
          this.path = ''
        }
      } catch (e) {
        console.log(e)
        this.$root.$toast.error('Có lỗi: ' + e.message)
      } finally {
        this.loading(false)
      }
    }
  },
  methods: {
    onShowModal() {
      this.getDataFromProps()
      this.frmInBienBan_Load()
    },
    onHideModal() {
      this.popupComponent = null
      this.popupComponentData = null
    },
    hide1() {
      this.$refs.popupXemThamSo.hide()
    },
    async frmInBienBan_Load() {
      try {
        this.tentotruong = await this.FN_TT_NHANVIEN(this.nhanvienid)
        await this.SP_LAY_DS_DONVI_LDV_MAUIN()
        this.setVisibleButton('tsbtnInBaoHong', false)
        this.setVisibleButton('btnInHopDong', false)
        this.setActiveButton('btnXemThamSo', false)
        await this.getReport(this.path)
        if (!this.baohong) {
          this.setVisibleButton('btnInHopDong', true)
          let vloaihdid = ''
          let hdkhid
          if (this.n_hdkh_id.toString().includes(',')) {
            hdkhid = this.n_hdkh_id.split(',')[0]
          } else {
            hdkhid = this.n_hdkh_id
          }
          vloaihdid = await this.FN_TT_HD_KHACHHANG(hdkhid)
          if (vloaihdid.toLocaleString() !== '-1') {
            this.loaihd_id = Number.parseInt(vloaihdid)
          }
          await this.SP_LAY_DS_MAU_IN(this.loaihd_id, NHOM_BC.BB_NGHIEMTHU, this.huonggiao_id)
        } else {
          this.setVisibleButton('tsbtnInBaoHong', true)
          await this.SP_LAY_DS_MAU_IN(this.loaihd_id, NHOM_BC.BC_BAOHONG_119, this.huonggiao_id)
        }
      } catch (error) {
        this.$root.$toast.error('Có lỗi: ' + error.message)
      }
    },
    pretty(value) {
      return JSON.stringify(value, null, 2);
    },
    async FN_TT_NHANVIEN(nhanvienid) {
      return this.GetData(await API.FN_TT_NHANVIEN(this.axios, {param: nhanvienid, type: 3}))
    },
    async FN_TT_HD_KHACHHANG(hdkhid) {
      return this.GetData(await API.FN_TT_HD_KHACHHANG(this.axios, {param: hdkhid, type: 1}))
    },
    getDataFromProps() {
      if (this.params != null) {
        console.log(this.params)
        this.n_hdkh_id = this.params.n_hdkh_id ? this.params.n_hdkh_id : this.n_hdkh_id
        this.n_hdtb_id = this.params.n_hdtb_id ? this.params.n_hdtb_id : this.n_hdtb_id
        this.ma_gd = this.params.ma_gd ? this.params.ma_gd : this.ma_gd
        this.ngay_yc = this.params.ngay_yc ? this.params.ngay_yc : this.ngay_yc
        this.ngaylap_hd = this.params.ngaylap_hd ? this.params.ngaylap_hd : this.ngaylap_hd
        this.n_baohong_id = this.params.n_baohong_id ? this.params.n_baohong_id : this.n_baohong_id
        this.huonggiao_id = this.params.huonggiao_id ? this.params.huonggiao_id : this.huonggiao_id
        this.tentram_tb = this.params.tentram_tb ? this.params.tentram_tb : this.tentram_tb
        this.n_phieu_id = this.params.n_phieu_id ? this.params.n_phieu_id : this.n_phieu_id
        this.cv_thuchien = this.params.cv_thuchien ? this.params.cv_thuchien : this.cv_thuchien
        this.nvth_id = this.params.nvth_id ? this.params.nvth_id : this.nvth_id
        this.path = this.params.path ? this.params.path : this.path
        this.ma_tb = this.params.ma_tb ? this.params.ma_tb : this.ma_tb
        this.phanvungId = this.params.phanvung_id ? this.params.phanvung_id : this.phanvungId
        this.loaihd_id = this.params.loaihd_id ? this.params.loaihd_id : this.loaihd_id
        this.loaitien_id = this.params.loaitien_id ? this.params.loaitien_id : this.loaitien_id
        this.ma_gd = this.params.magd ? this.params.magd : this.ma_gd
        this.donviql_id = this.params.donviql_id ? this.params.donviql_id : this.donviql_id
        this.seri_id = this.params.seri_id ? this.params.seri_id : this.seri_id
        this.loaihoadon_id = this.params.loaihoadon_id ? this.params.loaihoadon_id : this.loaihoadon_id
        this.hoadon = this.params.hoadon ? this.params.hoadon : this.hoadon
        this.fkey = this.params.fkey ? this.params.fkey : this.fkey
        this.thang_ld = this.params.thang_ld ? this.params.thang_ld : this.thang_ld
        this.vsuco_id = this.params.vsuco_id ? this.params.vsuco_id : this.vsuco_id
        this.vgiaoviec_id = this.params.vgiaoviec_id ? this.params.vgiaoviec_id : this.vgiaoviec_id
        this.vnhanvien_id = this.params.vnhanvien_id ? this.params.vnhanvien_id : this.vnhanvien_id
        this.quyettoan_id = this.params.quyettoan_id ? this.params.quyettoan_id : this.quyettoan_id
        this.thungan_id = this.params.thungan_id ? this.params.thungan_id : this.thungan_id
        this.ngay_tt = this.params.ngay_tt ? this.params.ngay_tt : this.ngay_tt
        this.thang_tt = this.params.thang_tt ? this.params.thang_tt : this.thang_tt
        this.ma_tt = this.params.ma_tt ? this.params.ma_tt : this.ma_tt
        this.nguoi_cn = this.params.nguoi_cn ? this.params.nguoi_cn : this.nguoi_cn
        this.kieu = this.params.kieu ? this.params.kieu : this.kieu
        this.nhom_httt_id = this.params.nhom_httt_id ? this.params.nhom_httt_id : this.nhom_httt_id
        this.httt_id = this.params.httt_id ? this.params.httt_id : this.httt_id
        this.vdonviql_id = this.params.vdonviql_id ? this.params.vdonviql_id : this.vdonviql_id
        this.vnhanvienql_id = this.params.vnhanvienql_id ? this.params.vnhanvienql_id : this.vnhanvienql_id
        this.vtuyenthu_id = this.params.vtuyenthu_id ? this.params.vtuyenthu_id : this.vtuyenthu_id
        this.vdagan = this.params.vdagan ? this.params.vdagan : this.vdagan
        this.thuebao_id = this.params.thuebao_id ? this.params.thuebao_id : this.thuebao_id
        this.ds_phieu_id = this.params.ds_phieu_id ? this.params.ds_phieu_id : this.ds_phieu_id
        this.baohanh_id = this.params.baohanh_id ? this.params.baohanh_id : this.baohanh_id
        this.donvigiao = this.params.donvigiao ? this.params.donvigiao : this.donvigiao
        this.donvinhan = this.params.donvinhan ? this.params.donvinhan : this.donvinhan
        this.chungtu_id = this.params.chungtu_id ? this.params.chungtu_id : this.chungtu_id
        this.nhanvien_giao_id = this.params.nhanvien_giao_id ? this.params.nhanvien_giao_id : this.nhanvien_giao_id
        this.nhanvien_nhan_id = this.params.nhanvien_nhan_id ? this.params.nhanvien_nhan_id : this.nhanvien_nhan_id
        this.dsluotgiao_id = this.params.dsluotgiao_id ? this.params.dsluotgiao_id : this.dsluotgiao_id
        this.dsxln_id = this.params.dsxln_id ? this.params.dsxln_id : this.dsxln_id
        this.buocxln_id = this.params.buocxln_id ? this.params.buocxln_id : this.buocxln_id
        this.kycuoc = this.params.kycuoc ? this.params.kycuoc : this.kycuoc
        this.dskn_id = this.params.dskn_id ? this.params.dskn_id : this.dskn_id
        this.dsxulyno_id = this.params.dsxulyno_id ? this.params.dsxulyno_id : this.dsxulyno_id
        this.baohong = this.params.baohong ? this.params.baohong : this.baohong
      }
    },
    showModal() {
      this.$refs.popupInBienBan.show()
    },
    hide() {
      // this.params = null
      this.$refs.popupInBienBan.hide()
    },
    convertToSlug(Text) {
      return Text.toLowerCase().replace(/ /g, '-')
    },
    GetDataList: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return null
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return null
    },
    async SP_LAY_DS_DONVI_LDV_MAUIN() {
      let req = {
        'p_loaidv_id': Number(LOAI_DV.DONVIQL_LD),
        'p_donvi_id': Number(this.$root.token.getDonViID())
      }
      let data = this.GetDataList(await API.SP_LAY_DS_DONVI_LDV_MAUIN(this.axios, req))
      if (data.length > 0) {
        this.tendvql = data[0].ten_dvql
      }
    },
    async SP_LAY_DS_MAU_IN(loahdid, nhombc, huonggiaoid) {
      let request = {
        'p_loaihd_id': loahdid,
        'p_nhombc_id': nhombc,
        'p_huonggiao_id': huonggiaoid
      }
      let data = this.GetDataList(await API.SP_LAY_DS_MAU_IN(this.axios, request))
      if (data.length > 0) {
        this.dsMauIn = data
        this.cboMauIn.list = data.map((item) => {
          return {
            id: item.baocao_id,
            text: item.ten_bc
          }
        })
      } else {
        this.dsMauIn = []
        this.cboMauIn.list = []
        this.cboMauIn.value = 0
      }
    },
    async viewReport(type) {
      this.type = type
      let request = {
        report: this.path,
        type: type,
        file_name:
          this.convertToSlug(this.title) + '_' + this.n_hdkh_id + '.' + this.extension[this.type],
        items: this.listOfParamNameValues
      }
      await API.RUN(this.axios, request)
        .then((response) => {
          console.log(response)
          if (response.data) {
            try {
              this.pdf = this.base64ToArrayBuffer(response.data)
            } catch(error) {
              console.log(error)
              this.$root.$toast.error('Xảy ra lỗi khi lấy dữ liệu biên bản in: ' + response.data)
            }
          } else {
            this.$root.$toast.error('Biên bản từ hệ thống BIP trả về rỗng. Hãy thử lại!')
          }
        }).catch(error => {
          if (error.data) {
            this.$root.$toast.error('Xảy ra lỗi khi lấy dữ liệu biên bản in: ' + error.data.message)
          } else if (error.request) {
            if (error.request.status == 500) {
              this.$root.$toast.error('API lấy biên bản in lỗi: ' + error.request.statusText)
            }
          } else {
            this.$root.$toast.error('Lỗi khi thực hiện lấy biên bản in: ' + error.message)
          }
        })
    },
    async getReport(path) {
      this.pdf = null
      this.listOfParamNameValues = []
      await API.GET_BI_PARAMS(this.axios, path).then(response => {
        if (response.data.listOfParamNameValues) {
          this.listOfParamNameValues =
            response.data.listOfParamNameValues.item
          this.listOfParamNameValues.forEach((v, index) => {
            let paramName = v.name.toLowerCase()
            let paramLabel = v.label
            let paramType = v.UIType
            console.log('ParamName: ' + paramName + ', ParamType:' + paramType + ', ParamLable:' + paramLabel)
            paramName = paramName.toLowerCase()
            if (paramName.includes('phanvung_id')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.phanvungId
              )
            } else if (paramName.includes('hdtb_id')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.n_hdtb_id
              )
            } else if (paramName.includes('hdkh_id')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.n_hdkh_id
              )
            } else if (paramName.includes('ma_gd') || paramName.includes('magd')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.ma_gd.toString()
              )
            } else if (paramName.includes('phieu_id')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.n_phieu_id
              )
            } else if (paramName.includes('ten_dvql')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.tendvql
              )
            } else if (paramName.includes('ten_totruong')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.tentotruong
              )
            } else if (paramName.includes('ma_tb') || paramName.includes('somay')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.ma_tb
              )
            } else if (paramName.includes('baohong')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.n_baohong_id
              )
            } else if (paramName.includes('ma_gd') || paramName.includes('magd')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.ma_gd
              )
            } else if (paramName.includes('loaitien') || paramName.includes('loaitien_id')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.loaitien_id
              )
            } else if (paramName.includes('donvi') || paramName.includes('donvi_id')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.donviql_id
              )
            } else if (paramName.includes('loaihd') || paramName.includes('loaihd_id')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.loaihd_id
              )
            } else if (paramName.includes('loaihoadon') || paramName.includes('loaihoadon_id')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.loaihoadon_id
              )
            } else if (paramName.includes('seri') || paramName.includes('seri_id')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.seri_id
              )
            } else if (paramName.includes('hoadon')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.hoadon
              )
            } else if (paramName.includes('fkey') || paramName.includes('key')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.fkey
              )
            } else if (paramName.includes('thang_ld') || paramName.includes('thang')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.thang_ld
              )
            } else if (paramName.includes('suco') || paramName.includes('suco_id')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.vsuco_id
              )
            } else if (paramName.includes('vgiaoviec_id') || paramName.includes('giaoviec')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.vgiaoviec_id
              )
            } else if (paramName.includes('nhanvien') || paramName.includes('vnhanvien_id')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.vnhanvien_id
              )
            } else if (paramName.includes('quyettoan_id') || paramName.includes('quyettoan')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.quyettoan_id
              )
            } else if (paramName.includes('thungan_id') || paramName.includes('thungan')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.thungan_id
              )
            } else if (paramName.includes('ngay_tt') || paramName.includes('ngay_tt')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.ngay_tt
              )
            } else if (paramName.includes('thang_tt') || paramName.includes('thang_tt')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.thang_tt
              )
            } else if (paramName.includes('nguoi_cn') || paramName.includes('nguoi_cn')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.nguoi_cn
              )
            } else if (paramName.includes('kieu') || paramName.includes('kieu')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.kieu
              )
            } else if (paramName.includes('nhom_httt_id') || paramName.includes('nhom_httt')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.nhom_httt_id
              )
            } else if (paramName.includes('httt_id') || paramName.includes('httt_id')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.httt_id
              )
            } else if (paramName.includes('vdonviql_id') || paramName.includes('vdonviql')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.vdonviql_id
              )
            } else if (paramName.includes('vnhanvienql_id') || paramName.includes('vnhanvienql')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.vnhanvienql_id
              )
            } else if (paramName.includes('vtuyenthu_id') || paramName.includes('vtuyenthu')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.vtuyenthu_id
              )
            } else if (paramName.includes('vdagan') || paramName.includes('vdagan')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.vdagan
              )
            } else if (paramName.includes('ds_phieu_id') || paramName.includes('ds_phieu')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.ds_phieu_id
              )
            } else if (paramName.includes('thuebao_id') || paramName.includes('thuebao')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.thuebao_id
              )
            } else if (paramName.includes('baohanh') || paramName.includes('baohanh_id')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.baohanh_id
              )
            } else if (paramName.includes('donvinhan') || paramName.includes('donvinhan')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.donvinhan
              )
            } else if (paramName.includes('donvigiao') || paramName.includes('donvigiao')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.donvigiao
              )
            } else if (paramName.includes('chungtu_id') || paramName.includes('chungtu')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.chungtu_id
              )
            } else if (paramName.includes('nhanvien_giao_id') || paramName.includes('nhanvien_giao')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.nhanvien_giao_id
              )
            } else if (paramName.includes('nhanvien_nhan_id') || paramName.includes('nhanvien_nhan')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.nhanvien_nhan_id
              )
            } else if (paramName.includes('dsluotgiao_id') || paramName.includes('dsluotgiao')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.dsluotgiao_id
              )
            } else if (paramName.includes('dsxln_id') || paramName.includes('dsxln')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.dsxln_id
              )
            } else if (paramName.includes('buocxln_id') || paramName.includes('buocxln')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.buocxln_id
              )
            } else if (paramName.includes('kycuoc') || paramName.includes('kycuoc')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.kycuoc
              )
            } else if (paramName.includes('dskn_id') || paramName.includes('dskn')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.dskn_id
              )
            } else if (paramName.includes('dsxulyno_id') || paramName.includes('dsxulyno')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.dsxulyno_id
              )
            } else if (paramName.includes('username')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.$root.token.getUserName()
              )
            }
          })
          // this.viewReport('pdf')
        }
      })
    },
    base64ToArrayBuffer(base64) {
      let binaryString = window.atob(base64)
      let len = binaryString.length
      let bytes = new Uint8Array(len)
      for (let i = 0; i < len; i++) {
        bytes[i] = binaryString.charCodeAt(i)
      }
      return bytes.buffer
    },
    async openPdf(pdfApp) {
      if (pdfApp.pdfViewer) {
        await pdfApp.pdfViewer.pagesPromise
        pdfApp.pdfViewer.currentScale = 1
      }
    },
    setActiveButton(id, status) {
      let index = this.actions.findIndex(item => item.id === id)
      if (index >= 0) {
        this.actions[index].active = status
      }
    },
    setVisibleButton(id, status) {
      let index = this.actions.findIndex(item => item.id === id)
      if (index >= 0) {
        this.actions[index].visible = status
      }
    },
    async btnInHopDong_Click() {
      try {
        this.loading(true)
        if (this.phieu_id === 0) {
          this.$root.$toast.warning('Chưa có thông tin phiếu!')
          return
        }
        /* if (this.cboMauIn.value === 0) {
          this.$root.$toast.warning('Chưa chọn mẫu in!!')
          return
        } */
        if (this.path == '') {
          this.$root.$toast.warning('Mẫu in chưa được thiết kế biểu mẫu. Không thể thực hiện chức năng in!')
          return
        }
        await this.viewReport('pdf')
        if (this.pdf) {
          this.popupComponent = () => import('../components/PreviewReport')
          this.popupComponentData = {
            pdf: this.pdf
          }
          await this.$refs.dialogPreviewBienBan.show()

        }
      } catch (error) {
        this.$root.$toast.error(error.message)
      } finally {
        this.loading(false)
      }
    },
    async SP_CAPNHAT_INPHIEU (phieu_id) {
      let req = {
        p_phieu_id : phieu_id
      }
      await API.SP_CAPNHAT_INPHIEU(this.axios, req)
    },
    async tsbtnInBaoHong_Click() {
      try {
        this.loading(true)
        if (this.phieu_id === 0) {
          this.$root.$toast.warning('Chưa có thông tin phiếu!')
          return
        }
        /* if (this.cboMauIn.value === 0) {
          this.$root.$toast.warning('Chưa chọn mẫu in!!')
          return
        } */
        if (this.path == '') {
          this.$root.$toast.warning('Mẫu in chưa được thiết kế biểu mẫu. Không thể thực hiện chức năng in!')
          return
        }

        await this.viewReport('pdf')
        if (this.pdf) {
          this.popupComponent = () => import('../components/PreviewReport')
          this.popupComponentData = {
            pdf: this.pdf
          }
          await this.$refs.dialogPreviewBienBan.show()

          if (this.chkLuuIn.checked == true) {
            await this.SP_CAPNHAT_INPHIEU(this.n_phieu_id)
          }
        }
      } catch (error) {
        this.$root.$toast.error(error.message)
      } finally {
        this.loading(false)
      }
    },
    btnXemThamSo_Click() {
      this.jsonstr = {
        report: this.path,
        items: this.listOfParamNameValues
      }
      this.$refs.popupXemThamSo.show()
    },
    onActionClick(action) {
      if (action.id === 'btnInHopDong') {
        this.btnInHopDong_Click()
      } else if (action.id === 'tsbtnInBaoHong') {
        this.tsbtnInBaoHong_Click()
      } else if (action.id === 'btnXemThamSo') {
        this.btnXemThamSo_Click()
      }
    }
  }
}
</script>

<style scoped>

</style>
