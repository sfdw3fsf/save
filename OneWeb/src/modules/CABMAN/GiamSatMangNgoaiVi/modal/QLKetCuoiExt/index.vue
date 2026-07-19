<template src="./index.template.html"></template>
<script>
import Vue from 'vue'
import ModalCsht from '../../../BanDoTuyenCot/Csht/Modal'
import ModalIMS from '../IMS/Modal'
import ModalChonDiaChi from '../../../BanDoTuyenCot/ChonDiaChi/Modal'
import frmChonDiaChi from '../../../Modal/ChonDiaChi/DiaChiModal.vue'
import ModalAnhChup from '../AnhChup/Modal'
import ModalDsCot from '../DanhSachCot/Modal/ModalDsCot'
import ModalDsBe from '../DanhSachBe/Modal/ModalDsBe'
import ModalChonToaDo from '../ChonToaDo'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required, minValue, maxValue, maxLength } from 'vuelidate/lib/validators'
import { DropDownListPlugin } from '@syncfusion/ej2-vue-dropdowns'
Vue.use(DropDownListPlugin)

export default {
  name: 'ModalQLKetCuoi',
  props: { modalID: null },
  components: {
    ModalChonToaDo, BssErrorMarker, BssRequiredMarker, ModalCsht, ModalChonDiaChi, ModalDsCot, ModalAnhChup, ModalDsBe, ModalIMS, frmChonDiaChi
  },
  created: async function () {
  },
  validations: {
    dataSelected:
    {
      kyhieu: {
        required,
        maxLength: maxLength(200)
      },
      loaikc_id: {
        required
      },
      ten_kc: {
        required,
        maxLength: maxLength(200)
      },
      diachi: {
        required,
        maxLength: maxLength(600)
      },
      ttsd_id: {
        required
      },
      kieulap_id: {
        required
      },
      vitri_id: {
        required
      },
      loaivo_id: {
        required
      },
      noisx_id: {
        required
      },
      dungluong: {maxValue: maxValue(99999)},
      tongso_dd: {
        required,
        minValue: minValue(1),
        maxValue: maxValue(99999)
      },
      kinhdo: {
        required,
        minValue: minValue(0)
      },
      vido: {
        required,
        minValue: minValue(0)
      }
    }

  },
  watch: {
    'dataSelected.kyhieu' (val) {
      // this.dataSelected.ten_kc = val
    },
    'dataSelected.loaikc_id' (val) {
    },
    'dataSelected.duan_id' (val) {
      this.$root.context.get(
        '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'TAISAN', option: this.dataSelected.duan_id === null ? 0 : this.dataSelected.duan_id}
      ).then((rs) => {
        this.dsTaiSan = rs.data
      })
    }
  },
  computed: {
    getKetCuoi () {
      var data = {
        CHUQUAN_ID: 1,
        KETCUOI_ID: this.dataSelected.ketcuoi_id,
        TEN_KC: this.dataSelected.ten_kc
      }
      return data
    },
    getPosition () {
      return {
        lat: parseFloat(this.dataSelected.vido),
        lng: parseFloat(this.dataSelected.kinhdo)
      }
    },
    getDiachi () {
      return this.dataSelected.diachi
    }
  },
  methods: {
    onFiltering: function (e) {
    },
    onChangeKyHieu () {
      if (this.dataQLKetCuoi.cmd !== 'SuaKetcuoi') {
        this.dataSelected.ten_kc = this.dataSelected.kyhieu
      }
    },
    async onChangeLoaiKC () {
      console.log('🚀 ~ file: index.vue ~ line 125 ~ onChangeLoaiKC ~ this.dataSelected.loaikc_id', this.dataSelected.loaikc_id)
      if (this.dataSelected.loaikc_id) {
        this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'CAP_SP_CAPNHAT', option: this.dataSelected.loaikc_id}
        ).then((rs) => {
          this.dsCAP_SP = rs.data
          if (this.dsCAP_SP.length > 0) {
            // this.dataSelected.cap_sp = this.dsCAP_SP[0].CAP_SP
          } else {
            this.dataSelected.cap_sp = null
          }
        })
      }
    },
    anhChup () {
      if (this.dataSelected && this.dataSelected.ketcuoi_id === null) return
      let doituong = {
        doituong_id: this.dataSelected.ketcuoi_id,
        doituong_mc: 'CST_BLOCK'
      }
      this.$refs.ModalAnhChup.show(doituong).then((result) => {
        if (result.ok) {
        }
      })
    },
    TaoKetCuoiCon (ketcuoi_cha_id) {
      return new Promise((resolve, reject) => {
        this.ketcuoi_cha_id = ketcuoi_cha_id
        this.$bvModal.show(this.modalID)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    show (dataQLKetCuoi) {
      return new Promise((resolve, reject) => {
        this.dataQLKetCuoi = dataQLKetCuoi
        this.$bvModal.show(this.modalID)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    taoKetCuoiThieu (dataQLKetCuoi) {
      return new Promise((resolve, reject) => {
        this.dataQLKetCuoi = dataQLKetCuoi
        this.$bvModal.show(this.modalID)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    closeModal: function () {
      this.resolvePromise({ok: false, data: null})
      this.$bvModal.hide(this.modalID)
    },
    XacNhanToaDo (position) {
      this.dataSelected.vido = position.lat
      this.dataSelected.kinhdo = position.lng
    },
    getBe (value) {
      this.dataSelected.becap_id = value.BE_ID
      this.dataSelected.becap_sohieu = value.KYHIEU
    },
    getCot (value) {
      this.dataSelected.cot_id = value.COT_ID
      this.dataSelected.cot_sohieu = value.SO_HIEU
    },
    hiddenModal: async function () {
      this.init()
    },
    resetModal: async function () {
      try {
        this.$v.$reset()
        if (this.ketcuoi_cha_id !== null) {
          try {
            this.$root.showLoading(true)
            await this.initForm()
            var data = {
              P_KETCUOI_CHA_ID: this.ketcuoi_cha_id
            }
            var rs = await this.$root.context.post(
              '/web-cabman/ban-do-mang-cap/get_ketcuoi_cha', data
            )
            this.$root.showLoading(false)
            if (rs.data.length > 0) {
              this.dataSelected.ketcuoi_cha_id = this.ketcuoi_cha_id
              this.dataSelected.capgoc_id = rs.data[0].CAPGOC_ID
              this.dataSelected.donvi_id = rs.data[0].DONVI_ID
              this.dataSelected.kinhdo = rs.data[0].KINHDO
              this.dataSelected.vido = rs.data[0].VIDO
              this.dataSelected.loaikc_id = rs.data[0].LOAIKC_ID
              await this.onChangeLoaiKC()
              this.dataSelected.phanloaikc_id = rs.data[0].PHANLOAIKC_ID
              this.dataSelected.chuquan_id = rs.data[0].CHUQUAN_ID

              this.dataSelected.diachi = rs.data[0].DIACHI
              this.dataSelected.tinh_id = rs.data[0].TINH_ID
              this.dataSelected.quan_id = rs.data[0].QUAN_ID
              this.dataSelected.phuong_id = rs.data[0].PHUONG_ID
              this.dataSelected.pho_id = rs.data[0].PHO_ID
              this.dataSelected.ap_id = rs.data[0].AP_ID
              this.dataSelected.khu_id = rs.data[0].KHU_ID
              this.dataSelected.dacdiem_id = rs.data[0].DACDIEM_ID
              this.dataSelected.sonha = rs.data[0].SONHA
            } else {
              await this.$alert('Kết cuối cha không tồn tại hoặc đã bị xóa', 'Thông báo', {
                confirmButtonText: 'Đóng',
                type: 'error'
              })
            }
          } catch (error) {
            this.$root.showLoading(false)
          }
          return
        }
        console.log('🚀 ~ file: index.vue ~ line 232 ~ this.dataQLKetCuoi', this.dataQLKetCuoi)
        if (this.dataQLKetCuoi !== null && this.dataQLKetCuoi !== undefined) {
          if (this.dataQLKetCuoi.cmd === 'SuaKetcuoi') {
            this.$root.showLoading(true)
            await this.initForm()
            this.dataSelected.ketcuoi_id = this.dataQLKetCuoi.data.ketcuoi_id
            console.log('🚀 ~ file: index.vue ~ line 236 ~ this.dataSelected.ketcuoi_id', this.dataSelected.ketcuoi_id)
            this.dataSelected.phanloaikc_id = this.dataQLKetCuoi.data.phanloaikc_id
            if (this.dataSelected.ketcuoi_id != null) { await this.thongtinKetCuoi() }
            this.$root.showLoading(false)
          } else if (this.dataQLKetCuoi.cmd === 'ViewKetcuoi') {
            this.$root.showLoading(true)
            await this.initForm()
            this.dataSelected.ketcuoi_id = this.dataQLKetCuoi.data.ketcuoi_id
            if (this.dataSelected.ketcuoi_id != null) {
              await this.thongtinKetCuoi()
            }
            this.$root.showLoading(false)
          } else {
            this.$root.showLoading(true)
            await this.initForm()
            this.dataSelected.ketcuoi_id = this.dataQLKetCuoi.data.ketcuoi_id
            this.dataSelected.phanloaikc_id = this.dataQLKetCuoi.data.phanloaikc_id
            this.dataSelected.donvi_id = this.dataQLKetCuoi.data.donvi_id
            this.dataSelected.capgoc_kyhieu = this.dataQLKetCuoi.data.capgoc.KYHIEU
            this.dataSelected.capgoc_id = this.dataQLKetCuoi.data.capgoc.CAP_ID

            this.dataSelected.kinhdo = this.dataQLKetCuoi.data.kinhdo
            this.dataSelected.vido = this.dataQLKetCuoi.data.vido

            if (this.dataQLKetCuoi.data.cap_id !== undefined) { this.dataSelected.cap_id = this.dataQLKetCuoi.data.cap_id }
            if (this.dataQLKetCuoi.data.nguondich !== undefined) { this.dataSelected.nguondich = this.dataQLKetCuoi.data.nguondich }

            if (this.dataQLKetCuoi.data.ketcuoi_truoc_id !== null) {
              let rs = await this.$root.context.get(
                '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'KETCUOI', option: this.dataQLKetCuoi.data.ketcuoi_truoc_id}
              )
              if (rs.data && rs.data.length > 0) {
                this.dataSelected.kinhdo = rs.data[0].KINHDO
                this.dataSelected.vido = rs.data[0].VIDO
              }
            }
            this.$root.showLoading(false)
          }
        }
      } catch (er) {} finally {
        this.$root.showLoading(false)
      }
    },
    thongtinKetCuoi: async function () {
      try {
        var rs = await this.$root.context.get(
          '/web-cabman/ketcuoi/layThongtinKetCuoi?ketcuoi_id=' + this.dataSelected.ketcuoi_id
        )
        if (rs.data != null) { await this.mapDataKetCuoi(rs.data) }
      } catch (error) {
      } finally {
      }
    },
    init () {
      this.category = {
        phanloai: 0,
        dinhdanh: 0,
        dactinh: 0,
        lienket: 0,
        kiemke: 0,
        vitri: 0,
        chuquan: 0,
        bando: 0,
        macsht: 0,
        ngaythang: 0
      }
      for (var i in this.dataSelected) {
        this.dataSelected[i] = null
      }
      this.ketcuoi_cha_id = null
      this.dataSelected.ttsd_id = 1
      this.dataSelected.noisx_id = 1
      this.dataSelected.kieulap_id = 1
      this.dataSelected.vitri_id = 1
      this.dataSelected.loaivo_id = 1
      this.dataSelected.kinhdo = 0
      this.dataSelected.vido = 0
      this.dataSelected.ngay_sd = new Date()
      this.dataSelected.ngay_ld = new Date()
    },
    initForm: async function () {
      try {
        var rs = null
        // rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/sp_load_danhmuc')
        // console.log('🚀 ~ file: index.vue ~ line 299 ~ rs', rs)
        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'PHANLOAI_KC', option: ''}
        )
        this.dsPHANLOAI_KC = rs.data

        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'LOAI_KC', option: ''}
        )
        this.dsLOAI_KC = rs.data

        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'TRANGTHAI_SD', option: ''}
        )
        this.dsTRANGTHAI_SD = rs.data

        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'KIEU_LAP', option: ''}
        )
        this.dsKIEU_LAP = rs.data

        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'NOI_SX', option: ''}
        )
        this.dsNOI_SX = rs.data

        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'VITRI', option: ''}
        )
        this.dsVITRI = rs.data

        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'LOAI_VO', option: ''}
        )
        this.dsLOAI_VO = rs.data

        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'DUAN', option: ''}
        )
        this.dsDuAn = rs.data

        // this.dsDuAn = rs.data.map((x) => ({id: x.DUAN_ID, text: x.MA_CT}))
        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'CHUQUAN', option: ''}
        )
        this.dsChuQuan = rs.data
        if (this.dsChuQuan.length > 0) {
          this.dataSelected.chuquan_id = this.dsChuQuan[0].CHUQUAN_ID
        }
        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'TOQL', option: ''}
        )
        this.dsTOQL = rs.data

        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'TOANHA', option: ''}
        )
        this.dsTOANHA = rs.data
      } catch (error) {
      } finally {
      }
    },
    async mapDataKetCuoi (data) {
      if (data != null) {
        this.dataSelected.phanloaikc_id = data.PHANLOAIKC_ID
        this.dataSelected.loaikc_id = data.LOAIKC_ID
        this.dataSelected.cap_sp = data.CAP_SP

        this.dataSelected.capgoc_id = data.CAPGOC_ID
        this.dataSelected.capgoc_kyhieu = data.CAPGOC_KYHIEU

        this.dataSelected.tongso_dd = data.TONGSO_DD
        this.dataSelected.dungluong = data.DUNGLUONG

        this.dataSelected.sl_port_dadung = data.SL_PORT_DADUNG
        this.dataSelected.sl_port_roi = data.SL_PORT_ROI
        this.dataSelected.sl_port_tb = data.SL_PORT_TB

        this.dataSelected.kieulap_id = data.KIEULAP_ID
        this.dataSelected.noisx_id = data.NOISX_ID
        this.dataSelected.vitri_id = data.VITRI_ID
        this.dataSelected.loaivo_id = data.LOAIVO_ID

        this.dataSelected.chuquan_id = data.CHUQUAN_ID
        this.dataSelected.taisan_id = data.TAISAN_ID
        this.dataSelected.duan_id = data.DUAN_ID
        this.dataSelected.ttsd_id = data.TTSD_ID

        this.dataSelected.ma_kc = data.MA_KC
        this.dataSelected.kyhieu = data.KYHIEU
        this.dataSelected.ten_kc = data.TEN_KC

        this.dataSelected.ten_nv = data.TEN_NV

        this.dataSelected.diachi = data.DIACHI

        this.dataSelected.donvi_id = data.DONVI_ID

        this.dataSelected.ngay_ld = data.NGAY_LD
        this.dataSelected.ngay_sd = data.NGAY_SD

        this.dataSelected.kinhdo = data.KINHDO
        this.dataSelected.vido = data.VIDO

        this.dataSelected.toanha_id = data.TOANHA_ID

        this.dataSelected.ma_csht = data.MA_CSHT
        this.dataSelected.ims_id = data.IMS_ID
        this.dataSelected.ghichu = data.GHICHU
        this.dataSelected.diachi_id = data.DIACHI_ID

        this.dataSelected.tinh_id = data.TINH_ID
        this.dataSelected.quan_id = data.QUAN_ID
        this.dataSelected.phuong_id = data.PHUONG_ID
        this.dataSelected.pho_id = data.PHO_ID
        this.dataSelected.ap_id = data.AP_ID
        this.dataSelected.khu_id = data.KHU_ID
        this.dataSelected.dac_diem_id = data.DACDIEM_ID
        this.dataSelected.so_nha = data.SONHA
        this.dataSelected.ketcuoi_cha_id = data.KETCUOI_CHA_ID
        this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'CAP_SP_CAPNHAT', option: this.dataSelected.loaikc_id}
        ).then((rs) => {
          this.dsCAP_SP = rs.data
        })
      }
    },
    ghiLai: async function () {
      if (this.dataSelected.ketcuoi_id != null) { this.capNhat() } else { this.themMoi() }
    },
    capNhat: async function () {
      try {
        if (!this.validateData()) return
        this.loading = true
        this.$root.showLoading(true)
        if (this.dataSelected.ngay_ld != null) { this.dataSelected.ngay_ld = this.convertDate(this.dataSelected.ngay_ld) }
        if (this.dataSelected.ngay_sd != null) { this.dataSelected.ngay_sd = this.convertDate(this.dataSelected.ngay_sd) }
        var rs = await this.$root.context.post(
          '/web-cabman/ketcuoi/capnhat_v2', this.dataSelected
        )
        if (rs.data != null) {
          this.$root.$toast.success('Cập nhật kết cuối thành công')
          this.resolvePromise({ok: true, data: rs.data})
          this.$bvModal.hide(this.modalID)
        } else { this.$root.$toast.error('Cập nhật kết cuối thất bại') }
      } catch (error) {
        this.$root.showLoading(false)
        this.$alert(error.response.data.message_detail, error.response.data.message, {
          dangerouslyUseHTMLString: true,
          confirmButtonText: 'Đóng',
          type: 'error'
        })
      } finally {
        this.$root.showLoading(false)
      }
    },
    onFilteringDuAn: function (e) {
      console.log('🚀 ~ file: index.vue ~ line 447 ~ e', e)
    },
    themMoi: async function () {
      try {
        if (!this.validateData()) return
        this.$root.showLoading(true)
        var ngay_ld = this.dataSelected.ngay_ld && this.convertDate(this.dataSelected.ngay_ld)
        var ngay_sd = this.dataSelected.ngay_sd && this.convertDate(this.dataSelected.ngay_sd)
        var data = {
          ten_kc: this.dataSelected.ten_kc,
          ma_kc: this.dataSelected.ma_kc,
          kyhieu: this.dataSelected.kyhieu,
          tongso_dd: this.dataSelected.tongso_dd,
          dungluong: this.dataSelected.dungluong,
          ngay_ld: ngay_ld,
          ngay_sd: ngay_sd,
          ttsd_id: this.dataSelected.ttsd_id,
          kieulap_id: this.dataSelected.kieulap_id,
          noisx_id: this.dataSelected.noisx_id,
          vitri_id: this.dataSelected.vitri_id,
          loaivo_id: this.dataSelected.loaivo_id,
          capgoc_id: this.dataSelected.capgoc_id,
          loaikc_id: this.dataSelected.loaikc_id,
          phanloaikc_id: this.dataSelected.phanloaikc_id,
          donvi_id: this.dataSelected.donvi_id,
          taisan_id: this.dataSelected.taisan_id,
          duan_id: this.dataSelected.duan_id,
          toanha_id: this.dataSelected.toanha_id,
          kinhdo: this.dataSelected.kinhdo,
          vido: this.dataSelected.vido,
          ketcuoi_cha_id: this.dataSelected.ketcuoi_cha_id,
          cap_sp: this.dataSelected.cap_sp,
          ghichu: this.dataSelected.ghichu,
          diachi: this.dataSelected.diachi,
          tinh_id: this.dataSelected.tinh_id,
          quan_id: this.dataSelected.quan_id,
          phuong_id: this.dataSelected.phuong_id,
          pho_id: this.dataSelected.pho_id,
          ap_id: this.dataSelected.ap_id,
          khu_id: this.dataSelected.khu_id,
          dacdiem_id: this.dataSelected.dacdiem_id,
          sonha: this.dataSelected.sonha,
          tramtbi_id: this.dataSelected.tramtbi_id,
          cap_id: this.dataSelected.cap_id,
          nguondich: this.dataSelected.nguondich,
          chuquan_id: this.dataSelected.chuquan_id,
          cot_id: this.dataSelected.cot_id,
          becap_id: this.dataSelected.becap_id,
          ma_csht: this.dataSelected.ma_csht,
          ims_id: this.dataSelected.ims_id
        }
        var rs = await this.$root.context.post(
          '/web-cabman/ketcuoi/themmoi_v2', data
        )
        this.$root.showLoading(false)
        if (rs.data != null) {
          this.$alert('Ghi dữ liệu kết cuối thành công', 'Thông báo', {
            confirmButtonText: 'Đóng',
            type: 'info',
            callback: action => {
              this.resolvePromise({ok: true, data: rs.data})
              this.$bvModal.hide(this.modalID)
            }
          })
        } else { this.$root.$toast.error('Thêm kết cuối thất bại') }
      } catch (error) {
        this.$root.showLoading(false)
        this.$alert(error.response.data.message_detail, error.response.data.message, {
          dangerouslyUseHTMLString: true,
          confirmButtonText: 'Đóng',
          type: 'error'
        })
      } finally {
        this.$root.showLoading(false)
      }
    },
    validateData () {
      this.$v.$touch()

      if (this.$v.$invalid && !this.$v.dataSelected.loaikc_id.required) {
        this.$root.$toast.error('Loại kết cuối không được để trống')
        return false
      }

      if (this.$v.$invalid && !this.$v.dataSelected.kyhieu.required) {
        this.$root.$toast.error('Ký hiệu không được để trống')
        return false
      }

      if (this.$v.$invalid && !this.$v.dataSelected.kyhieu.maxLength) {
        this.$root.$toast.error('Ký hiệu không được nhập quá 200 ký tự')
        return false
      }

      if (this.$v.$invalid && !this.$v.dataSelected.ten_kc.required) {
        this.$root.$toast.error('Tên kết cuối không được để trống')
        return false
      }

      if (this.$v.$invalid && !this.$v.dataSelected.ten_kc.maxLength) {
        this.$root.$toast.error('Tên kết cuối không được nhập quá 200 ký tự')
        return false
      }

      if (this.$v.$invalid && !this.$v.dataSelected.diachi.required) {
        this.$root.$toast.error('Địa chỉ không được để trống')
        return false
      }

      if (this.$v.$invalid && !this.$v.dataSelected.tongso_dd.required) {
        this.$root.$toast.error('Dung lượng không được để trống')
        return false
      }

      if (this.$v.$invalid && !this.$v.dataSelected.tongso_dd.minValue) {
        this.$root.$toast.error('Dung lượng không được nhập nhỏ hơn 1')
        return false
      }

      if (this.$v.$invalid && !this.$v.dataSelected.tongso_dd.maxValue) {
        this.$root.$toast.error('Dung lượng không được nhập lớn hơn 99999')
        return false
      }

      if (this.$v.$invalid && !this.$v.dataSelected.kinhdo.required) {
        this.$root.$toast.error('Kinh độ không được để trống')
        return false
      }

      if (this.$v.$invalid && !this.$v.dataSelected.vido.required) {
        this.$root.$toast.error('Vĩ độ không được để trống')
        return false
      }

      if (this.$v.$invalid && !this.$v.dataSelected.kinhdo.minValue) {
        this.$root.$toast.error('Kinh độ không được nhập nhỏ hơn 0')
        return false
      }

      if (this.$v.$invalid && !this.$v.dataSelected.vido.minValue) {
        this.$root.$toast.error('Vĩ độ không được nhập nhỏ hơn 0')
        return false
      }

      return true
    },
    DiaChiXacNhan: function (data) {
      this.dataSelected.diachi = data.diachimoi
      this.dataSelected.tinh_id = data.tinh_id
      this.dataSelected.quan_id = data.quan_id
      this.dataSelected.phuong_id = data.phuong_id
      this.dataSelected.pho_id = data.pho_id
      this.dataSelected.ap_id = data.ap_id
      this.dataSelected.khu_id = data.khu_id
      this.dataSelected.dac_diem_id = data.dacdiem_id
      this.dataSelected.sonha = data.so_nha
    },
    convertDate: function (date) {
      return new Date(date).toLocaleDateString('en-US', { day: '2-digit' }) + '/' + new Date(date).toLocaleDateString('en-US', { month: '2-digit' }) + '/' + new Date(date).toLocaleDateString('en-US', { year: 'numeric' })
    },
    xoaMaCSHT: function () {
      this.dataSelected.ma_csht = null
    },
    xoaMaIMS: function () {
      this.dataSelected.ims_id = null
    },
    xoaTOANHA: function () {
      this.dataSelected.toanha_id = null
    },
    xoaBe: function () {
      this.dataSelected.becap_id = null
      this.dataSelected.be_sohieu = null
    },
    xoaCot: function () {
      this.dataSelected.cot_id = null
      this.dataSelected.cot_sohieu = null
    },
    xoaMaTS: function () {
      this.dataSelected.taisan_id = null
    },
    xoaMaDA: function () {
      this.dataSelected.duan_id = null
    },
    getMaCsht: function (args) {
      this.dataSelected.ma_csht = args
    },
    getMaIMS: function (args) {
      this.dataSelected.ims_id = args
    },
    xoaTOQL: function () {
      this.dataSelected.donvi_id = null
    },
    onAcceptDiaChi (data) {
      this.dataSelected = data
    },
    ChonDiaChi () {
      this.dataDiaChi = this.dataSelected
      // this.$refs.frmChonDiaChi.showModal()
      this.$bvModal.show('popupDiaChiKetCuoi')
    }
  },
  data: function () {
    return {
      dataDiaChi: {
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: '',
        diachi: ''
      },
      ketcuoi_cha_id: null,
      loading: true,
      allowFiltering: false,
      readonly: true,
      dataSelected: {
        capgoc_kyhieu: null,
        ngay_sd: new Date(),
        ngay_ld: new Date(),
        phanloaikc_id: null,
        loaikc_id: null,
        cap_sp: null,
        kyhieu: null,
        ten_kc: null,
        diachi: null,
        diachi_id: null,
        ghichu: null,
        ma_kc: null,
        tongso_dd: null,
        dungluong: null,
        sl_port_dadung: null,
        sl_port_roi: null,
        sl_port_tb: null,
        ttsd_id: 1,
        noisx_id: 1,
        kieulap_id: 1,
        vitri_id: 1,
        loaivo_id: 1,
        duan_id: null,
        taisan_id: null,
        chuquan_id: null,
        donvi_id: null,
        kinhdo: 0,
        vido: 0,
        ketcuoi_id: null,
        cap_id: null,
        capgoc_id: null,
        ims_id: null,
        nguondich: null,
        tinh_id: null,
        quan_id: null,
        phuong_id: null,
        pho_id: null,
        ap_id: null,
        khu_id: null,
        ma_csht: null,
        becap_id: null,
        becap_sohieu: null,
        cot_id: null,
        cot_sohieu: null,
        toanha_id: null,
        tramtbi_id: null,
        ten_tramtbi: null,
        sonha: null,
        dacdiem_id: null,
        ketcuoi_cha_id: null,
        ten_nv: null
      },
      dsPHANLOAI_KC: [],
      dsLOAI_KC: [],
      dsCAP_SP: [],
      dsTRANGTHAI_SD: [],
      dsNOI_SX: [],
      dsKIEU_LAP: [],
      dsVITRI: [],
      dsLOAI_VO: [],
      dsDuAn: [],
      dsTaiSan: [],
      dsChuQuan: [],
      dsTOQL: [],
      dsTOANHA: [],
      category: {
        phanloai: 0,
        dinhdanh: 0,
        dactinh: 0,
        lienket: 0,
        kiemke: 0,
        vitri: 0,
        chuquan: 0,
        bando: 0,
        macsht: 0,
        ngaythang: 0
      },
      waterMark: 'Chọn ngày',
      dateVal: new Date(),
      dateFormat: 'dd/MM/yyyy',
      resolvePromise: null,
      rejectPromise: null,
      dataQLKetCuoi: {}
    }
  },
  mounted: function () {
  }
}
</script>
<style src="./index.scss">
</style>
