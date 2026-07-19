<template src="./frmSuaChungTu.html"></template>
<script>
import api from './api'
import moment from 'moment'
import Vue from 'vue'
import { convertToUpcaseKey, isNullOrEmpty, getDateStringOrDefault } from '../../helpers'
import { FORM_CONFIG } from '../../const'

export default {
  name: 'frmSuaCT',
  components: {
     ChiTietVatTuComponent: () => import('./components/ChiTietVatTuComponent.vue'),
     PSISComponent: () => import('./components/PSISComponent.vue'),
    },
  emits: ['onSaveForm'],
  props: {

    chuaht_or_khac_2xcp: {
      type: [Boolean],
      default: null,
      required: true
    },
    
    dtChungTu: {
      type: [Array],
      default: [],
      required: true
    },
    kieuphieu: {
      type: [String, Number],
      default: null,
      required: true
    },
    vchungtu_id: {
      type: [String, Number],
      default: null,
      required: true
    },
    loaict_id: {
      type: [String, Number],
      default: null,
      required: true
    },
    vma_md: {
      type: [String, Number],
      default: null,
      required: true
    },
  },
  data() {
    return {
      target: '.main-wrapper',
      cboKieuPhieu: { enabled: true, list: [], value: null },
      txtlenhVB: { enabled: true, value: null },
      txtSo_HD: { enabled: true, value: null },
      dtpNgayVB: { enabled: true, value: null },
      dtpNgay_HD: { enabled: true, value: null },
      dtpNgayXuat: { enabled: true, value: null },
      dtpNgayNhan: { enabled: true, value: null },
      txtND: { enabled: true, value: null },
      txtTKDUAll: { enabled: true, value: null },
      txtTK_Kho_All: { enabled: true, value: null },

      gridChungTu: {
        header: [
          { fieldName: 'SOPHIEU', headerText: 'Số phiếu', textAlign: 'center', allowFiltering: true, width: 130, allowEditing: false },
          { fieldName: 'LOAI_CT', headerText: 'Loại chứng từ', textAlign: 'center', allowFiltering: true, width: 130, allowEditing: false },
          { fieldName: 'NGAY_CT', headerText: 'Ngày yêu cầu', textAlign: 'center', allowFiltering: true, width: 130, allowEditing: false },
          { fieldName: 'TIEN', headerText: 'Tiền', textAlign: 'center', allowFiltering: true, width: 130, allowEditing: false, format: 'N0', type: 'number' },
          { fieldName: 'NGUOI_CN', headerText: 'Người lập', textAlign: 'center', allowFiltering: true, width: 130, allowEditing: false },
          { fieldName: 'SO_VB', headerText: 'Lệnh VB', textAlign: 'left', allowFiltering: true, width: 130, allowEditing: false },
          { fieldName: 'SO_HOADON', headerText: 'Số HĐ', textAlign: 'left', allowFiltering: true, width: 130, allowEditing: false }
        ],
        list: [],
        value: null
      },
      gridVatTuUpdate: [],
      layoutPSIS: { visible: false },
      layoutNoidung: {
        visible: true
      },
      allowEdiCTVT: true,
      isLoadFileAgain: false,
    }
    
  },
  computed: {
    classGridChungTu() {
      if (this.layoutNoidung.visible && this.layoutPSIS.visible) return 'col-sm-5 col-5'
      else if (this.layoutNoidung.visible) return 'col-sm-7 col-7'
      else return 'col-sm-12 col-12'
    }
  },
  watch: {
    vma_md(value){
      if(value =="N_MS"){
        this.layoutPSIS.visible = true
      }else{
        this.layoutPSIS.visible = false
      }
    }
  },
  methods: {
    openDialog() {
      this.$refs.dlgSuaChungTu.show()
    },
    closeForm() {
      this.$emit('onSaveForm', {
        xacnhan: this.xacnhan ,
        isLoadFileAgain: this.isLoadFileAgain
      })
    },

    async load_ChungTu() {
      try {
        this.$root.loading(true)
        const resCbKieuPhieu = await api.lay_ds_kieu_phieu(this.axios, {
          loaict_id: this.loaict_id
        })
        this.cboKieuPhieu.list = resCbKieuPhieu.data.data

        this.cboKieuPhieu.value = this.kieuphieu

        let s = this.dtChungTu.filter((e) => e.CHUNGTU_ID == this.vchungtu_id)
        if (!s.length) {
          this.gridChungTu.list = []

          return
        }
        this.gridChungTu.list = s.map((e) => e)
      } catch (error) {
        console.log(error)
      } finally {
        this.$root.loading(false)
      }
    },
    async frmSuaCT_Load() {
      if (this.chuaht_or_khac_2xcp) {
        this.layoutNoidung.visible = false
      }
      await this.load_ChungTu()
    },

    async KiemTraLoaiKhoMaKho() {
      if (this.loaict_id == 2) return true

      if (this.gridVatTuUpdate.length <= 0) {
        this.$toast.warning('Không có Vật tư nào để ghi lại!')
        return false;
      } 
      
      // let json = JSON.stringify(this.gridVatTuUpdate)
      const resCheckLMK = await api.kiem_tra_loai_ma_kho(this.axios, { dataDs: this.gridVatTuUpdate, kieu: 0 })

      let kq = resCheckLMK?.data?.data

      if (kq == 'loaikho') {
        this.$toast.error('Không thể ghi lại. Bạn phải chọn hết loại kho ở danh sách vật tư trước!')
        return false
      }
      if (kq == 'makhotd') {
        this.$toast.error('Không thể ghi lại. Bạn phải chọn hết mã kho ở danh sách vật tư trước!')
        return false
      }
      return true
    },
    async CAPNHAT() {
      this.$root.loading(true)
      try {
        if (this.gridChungTu.list != null || this.gridVatTuUpdate != null) {
          if (this.cboKieuPhieu.value == null) {
            this.$root.loading(false)
            this.$toast.warning('Bạn phải chọn kiểu phiếu! ')
            return
          }
          if (!(await this.KiemTraLoaiKhoMaKho())) return


          //doing
          let json = JSON.stringify({
            CHUNGTU_ID: this.vchungtu_id,
            SO_VB: this.txtlenhVB.value,
            LOAIPHIEU_ID: this.cboKieuPhieu.value,
            ND_GIAO: this.txtND.value,
            SO_HOADON: this.txtSo_HD.value,
            NGAY_VB: this.dtpNgayVB.value?  moment(this.dtpNgayVB.value,'DDMMYYYY').format('DD/MM/YYYY hh:mm:ss'): "",
            NGAY_HOADON: this.dtpNgay_HD.value? moment(this.dtpNgay_HD.value,"DDMMYYYY").format('DD/MM/YYYY hh:mm:ss'): "",
            NGAYGUI_TT: this.dtpNgayXuat.value? moment(this.dtpNgayXuat.value,'DDMMYYYY').format('DD/MM/YYYY hh:mm:ss'): "",
            NGAYNHAN_TT: this.dtpNgayNhan.value? moment(this.dtpNgayNhan.value,'DDMMYYYY').format('DD/MM/YYYY hh:mm:ss'): "",
          })
          console.log('this.gridVatTuUpdate')
          console.log(this.gridVatTuUpdate)
          let json_chitiet = JSON.stringify(this.gridVatTuUpdate)

          const reskq = await api.cap_nhat_ct_co_quyen(this.axios, {
            ds: json,
            chiTiet: json_chitiet,
            kieu: this.loaict_id,
            nguoiCn: await this.$root.token.getUserName(),
            mayCN: await this.$root.token.getMachine(),
            ipCn: await this.$root.token.getIP()
          })
          const kq = reskq?.data?.data
          this.$root.loading(false)
          if (kq == '1') {
            this.xacnhan = true
            this.closeForm()
            this.$toast.success('Cập nhật chứng từ thành công!')
          } else {
            this.$toast.error('Có lỗi: ' + kq)
          }
        } else {
          this.$toast.error('Không có dữ liệu')
        }
      } catch (err) {
        this.$root.loading(false)
        console.log(err)
        this.$toast.error(`Có lỗi khi cập nhật! ${err}`)
      } finally {
        this.$root.loading(false)
      }
    },

    async gridviewChungTu_FocusedRowChanged(item) {
        console.log("gridviewChungTu_FocusedRowChanged:", item);
        if (!item) return;
        if(this.gridChungTu.value?.CHUNGTU_ID == item?.CHUNGTU_ID) return;

        this.gridChungTu.value = item

        this.txtlenhVB.value = item.SO_VB
        this.txtSo_HD.value = item.SO_HOADON
        this.cboKieuPhieu.value = item.LOAIPHIEU_ID || 0
        this.dtpNgayVB.value = item.NGAY_VB || null;
        this.dtpNgay_HD.value = item.NGAY_HOADON || null;
        this.dtpNgayNhan.value = item.NGAYNHAN_TT || null;
        this.dtpNgayXuat.value = item.NGAYGUI_TT || null;
        
   
        this.txtND.value = item.ND_GIAO
     
    },
    async tsbtnGhiLai_Click() {
      await this.CAPNHAT()
      this.$emit('onBtnXacNhanClicked', { xacnhan: this.xacnhan })
    },
    async btnFilePath() {
      try {
        this.$root.loading(true)
        if (!this.vchungtu_id) {
          return this.$toast.error('Không tìm thấy chứng từ !')
        }
       
        let check = await (
          await api.getCheckFile(this.axios, {
            chungTuId: this.vchungtu_id
          })
        ).data.data
        if (check.length) {
          console.log('btnFilePath')
          console.log(check[0]!=null)
          if (check[0]==null || check[0].FILE_CV) {
            return this.$toast.error('Chứng từ đã có file VB ! Không thể thêm file VB !')
          }
        }
        this.$refs.filePath.click()
      } catch (error) {
        console.log(error)
      } finally {
        this.$root.loading(false)
      }
    },
    async onChangeFile(e) {
      try {
        this.$root.loading(true)
        const files = e.target.files

        if (!files.length) {
          return
        }
        let formData = new FormData()
        formData.append('file', e.target.files[0])
        api
          .uploadFile(this.axios, formData, {
            headers: { 'Content-Type': 'multipart/form-data' }
          })
          .then(async (response) => {
            if (response.data.error_code === 'BSS-00000000') {
              let res = await api.updateFileCongVan(this.axios, {
                chungTuId: this.vchungtu_id,
                result: response.data.data.message.relativeUrl
              })
              this.isLoadFileAgain = true;
              this.$root.loading(false)
              // this.gParams.load_file_again = true
              this.$toast.success('Upload file thành công!')
            }
          })
          .catch(function () {
            // this.gParams.load_file_again = false
            this.$toast.error('File upload failed!')
          })
      } catch (error) {
        console.log(error)
      } finally {
        this.$root.loading(false)
      }
    },
    handeOnEditGridVTu(data) {
      console.log("handeOnEditGridVTu=>", data)
      this.gridVatTuUpdate = data
    },
    stringToDate(value, format = 'DD/MM/YYYY hh:mm:ss') {
      if(value){
        return moment(value).format(format)
      }
        return null
      
    }
  },
  async created() {
    await this.frmSuaCT_Load()
  }
}
</script>
<style scoped></style>
