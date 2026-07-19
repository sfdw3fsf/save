<template>
    <div class="modal-content popup-box">
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a href="#" @click.prevent="btnChapNhan">
                        <span class="icon one-save"></span> Chấp nhận
                    </a>
                </li>
                <li>
                    <a href="#" @click.prevent="btnThoat">
                        <span class="icon one-users"></span> Thoát
                    </a>
                </li>

            </ul>
        </div>
        <div class="popup-body">
            <div class="box-form">
                <div class="legend-title">
                    <span class="icon fa fa-angle-down">
                    </span>
                    Thông tin thuê số
                </div>
                <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                    <div class="key">
                        <div class="check-action">
                        <input type="radio" id="one" value="1" v-model="isTruotSo" @click="rdTruotSo_Clicked" />
                        <span class="name">Trượt số</span>
                        </div>
                    </div>
                    <div class="value">
                        <input type="text" v-model.trim="txtSoTruot" ref="TruotSo" class="form-control" :class="{ disabled: !isSoTruot }" />
                    </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                    <div class="key">Thời gian thuê</div>
                    <div class="value">
                        <input type="text" v-model.trim="txtThoiGian" ref="ThoiGianThue" class="form-control" :class="{ disabled: !isThoiGianThue }" @keypress="validateNumber" />
                    </div>
                    </div>
                </div>
                </div>
                <div class="row">
                <div class="col-12">
                    <div class="info-row">
                    <div class="key">
                        <div class="check-action">
                        <input type="radio" id="one" value="2" v-model="isTruotSo" @click="rdThongBao_Clicked" />
                        <span class="name">Thông báo</span>
                        </div>
                    </div>
                    <div class="value">
                        <input type="text" class="form-control" v-model.trim="txtThongBao" ref="ThongBao" :class="{ disabled: !isThongBao }" />
                    </div>
                    </div>
                </div>
                </div>
            </div>
        </div>
    </div>
</template>
<style>
    input.disabled, select.disabled, .disabled input, .disabled select, .disabled /deep/ .select2-selection { background-color: #EEE; color:lightgray;}
    .e-grid .e-gridheader .e-sortfilter .e-headercelldiv, .e-grid .e-gridheader .e-stackedheadercelldiv, .e-grid .e-gridheader .e-headercell .e-headercelldiv.e-headerchkcelldiv
    {
    padding: 0 5px 0 5px !important;
    }

</style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import { required, minLength, integer, maxValue } from 'vuelidate/lib/validators'
export default {
  name: 'frmChuyenDich_ThueSo',
  components: { required, minLength, integer, maxValue, DatePicker, VueFlatPickr, breadcrumb },
  props: [ 'dulieu' ],
  watch: {

  },
  data () {
    return {
      returnString: {},
      txtSoTruot: '',
      txtThoiGian: '',
      txtThongBao: '',
      dateconfig: {
        altFormat: 'd/m/Y H:i:S',
        altInput: true,
        dateFormat: 'd/m/Y H:i:S',
        allowInput: true
      },
      toptarget: '#app',
      toptarget1: '#app .SearchGeneral',
      sotruot: '',
      success_code: 'BSS-00000000',
      vhdtb_id: 0,
      TruotSo: '',
      ThoiGianThue: '',
      ThongBao: '',
      LoaiHD: {
        list: [],
        value: {}
      },
      kieu_thueso: 0,
      isTruotSo: 1,
      isThoiGianThue: true,
      isSoTruot: true,
      isThongBao: false,

      hdkh_id: '0',
      vma_kh: '',
      ts_kt_tthdkhdn: 0,
      matb: '',
      THUESO_DT_CU_SAU_DC: 540,
      THUESO_DT_CU_SAU_TL: 557,
      TRUOTSO: 1,
      THONGBAO: 2,
      TrangThaiHD_MOI: 1,

      // Dữ liệu truyền vào
      vkieukh_id: '',
      vkhachhang_id: '',
      // Dữ liệu trả ra
      vma_tb: '',
      vnhomso_id: '',
      vdonvi_id: '',
      noidung_thueso: '',
      sothang_thueso: 0
    }
  },
  computed: {
    iTruotSo: {
      get () { return this.isTruotSo },
      set (value) { this.isTruotSo = 2 }
    }
  },
  dateConfig: {
    altInput: true,
    dateFormat: 'd/m/Y',
    altFormat: 'd/m/Y'
  },
  async mounted () {
    this.txtSomay = this.dulieu.vma_tb
    this.txtSoTruot = this.dulieu.sotruot
    if (this.dulieu.vhdtb_id !== 0) {
      let data = {
        loai: 'HDTB_CD',
        value: this.dulieu.vhdtb_id
      }
      try {
        this.loading(true)
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/getdata', data)
        console.log('🚀 ~ file: frmChuyenDich_ThueSo.vue ~ line 161 ~ mounted ~ rs', rs)
        let ds_cd = rs.data
        if (ds_cd.data.length > 0) {
          if (ds_cd[0]['tg_thueso'] != null &&
                        ds_cd[0]['sotruot'] != null) {
            this.isSoTruot = true
            this.isTruotSo = 0
            this.isThongBao = false

            this.txtThoiGian = ds_cd[0]['tg_thueso']
            this.txtSoTruot = ds_cd[0]['sotruot']
            this.txtThongBao = ''
          }
          if (ds_cd[0]['tg_thueso'] != null &&
                        ds_cd[0]['noidung_tb'] != null) {
            this.isSoTruot = false
            this.isTruotSo = 1
            this.isThongBao = true
            this.txtThoiGian = ds_cd[0]['tg_thueso']
            this.txtSoTruot = ''
            this.txtThongBao = ds_cd[0]['noidung_tb']
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: frmChuyenDich_ThueSo.vue ~ line 162 ~ mounted ~ error', error)
      } finally {
        this.loading(false)
      }
    }
  },

  methods: {
    async MAP_ID (P_MAP, P_VALUE) {
      try {
        let data = {
          loai: P_MAP,
          value: P_VALUE
        }
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/getdata', data)
        if (rs.data.length > 0) {
          var obj = rs.data[0]
          if (P_MAP === 'PHANLOAIKH_ID_HD_KHACHHANG') {
            return obj.phanloaikh_id
          } else {
            return obj[P_MAP.toLowerCase()]
          }
        } else return null
      } catch (error) {
        return null
      }
    },
    closeForm () {
      this.$emit('form-close', null)
    },
    validateNumber: event => {
      let keyCode = event.keyCode
      if (keyCode < 48 || keyCode > 57) {
        event.preventDefault()
      }
    },
    ShowAlert (mesage, val) {
      if (val == 0) {
        this.$toast.success(mesage)
      } else if (val === 1) {
        this.$toast.warning(mesage)
      } else if (val === 2) {
        this.$toast.error(mesage)
      }
    },
    rdThongBao_Clicked () {
      this.txtThoiGian = ''
      this.txtThongBao = ''
      this.txtSoTruot = ''
      this.isThongBao = true
      this.isSoTruot = false
    },
    rdTruotSo_Clicked () {
      this.txtThoiGian = ''
      this.txtThongBao = ''
      this.txtSoTruot = ''
      this.isThongBao = false
      this.isSoTruot = true
    },
    btnThoat () {
      try {
        this.closeForm()
      } catch (error) {

      }
    },
    btnChapNhan () {
      if (this.txtThoiGian === '') {
        this.ShowAlert('Bạn chưa nhập thời gian thuê số!', 2)
        return
      }
      if (this.isSoTruot && this.txtSoTruot === '') {
        this.ShowAlert('Bạn chưa nhập Số trượt!', 2)
        return
      }
      if (this.isThongBao && this.txtThongBao === '') {
        this.ShowAlert('Bạn chưa nhập Thông báo!', 2)
        return
      }
      if (this.isThongBao) {
        this.noidung_thueso = this.txtThongBao
      }
      if (this.isSoTruot) {
        this.noidung_thueso = this.txtSoTruot
        this.kieu_thueso = this.TRUOTSO
      } else {
        this.kieu_thueso = this.THONGBAO
      }
      this.sothang_thueso = this.txtThoiGian
      this.$emit('accept', {noidung_thueso: this.noidung_thueso, sothang_thueso: this.sothang_thueso, kieu_thueso: this.kieu_thueso})
    }

  }
}
</script>
