<template src="./GachNoKhachHangThanhLy.html"></template>

<script>
import { maxLength, numeric, required } from "vuelidate/lib/validators"
import BssRequiredMarker from "@/components/BssRequiredMarker"
import GachNoAPI from '../api/GachNoAPI'
import CommonsAPI from '../api/Commons'
import GachNoKHThanhLyAPI from '../api/GachNoKhachHangThanhLy'
import TraCuuNoTongHopAPI from '../api/TraCuuNoTongHopAPI'
import DanhSachPhieuTraHuy from './components/DanhSachPhieuTraHuy.vue'
import moment from 'moment'

export default{
  components : {
    BssRequiredMarker,
    appDanhSachPhieuTraHuy: DanhSachPhieuTraHuy
  },
  async created () 
  {
    this.loading(true)
    const res = await Promise.all([
      this.getDSThuNgan(),
      this.getDSHTTT(),
      this.getDSNganHang()
    ])

    this.loading(false)
    
  },
  computed : {
    p_ngay_ttoan : {
      get() {
        return this.params.p_ngay_ttoan
      },
      set(value) {
        return this.params.p_ngay_ttoan = value
      }
    }
  },
  data() {
    return {
      keyDSKhoanMucNo: 0,
      sumTemplate: function() {
        return {
            template: Vue.component('sumTemplate', {
            template: `<span>Sum: {{data.Sum}}</span>`,
            data: function () {return {data: {data: {}}};}
            })
        }
      },
      columns: [
        {fieldName: 'TENKHOANMUC', headerText: 'Tên khoản mục', allowFiltering: true},
        {fieldName: 'NOGOC', headerText: 'Nợ gốc', allowFiltering: true, type: 'number', format:'N0'},
        {fieldName: 'THUE', headerText: 'Thuế', allowFiltering: true, type:'number', format:'N0'},
        {fieldName: 'TONGNO', headerText: 'Tổng nợ', allowFiltering: true, type:'number', format:'N0'},
        ],
      params: {
        p_nhom_httt_id: 0,
        p_ma_tt: null,
        p_acc_somay: null,
        p_ten_tt: null,
        p_diachi_tt: null,
        p_thungan_options: [],
        p_thungan: null,
        p_quaythu_id: null,
        p_httt_options: [],
        p_httt: null,
        p_ngay_ttoan: new Date(),
        p_nganhang_options: [],
        p_nganhang: null,
        p_ghichu: null
      },
      fieldsNVThuNgan: {text: 'TEN_NV', value: 'NHANVIEN_ID' },
      fieldsHTTT: {text: 'HTTT', value: 'HTTT_ID' },
      fieldsNganHang: {text: 'TEN_NH', value: 'NGANHANG_ID' },
      dateConfig: {
        value: new Date(),
        format: 'dd/MM/y',
        max: new Date()
      },
      dsKhoanMucNo: [],
      //totalItems: 0,
      pageinfo: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0
      },
    }
   },   // end of data
   methods : {
    validateData: function() {
      let errors = []

      if(this.params.p_ma_tt === null || this.params.p_ma_tt.trim() === '')
      {
        errors.push('Trường mã thanh toán bắt buộc nhập')
      }
      if(this.params.p_thungan === null)
      {
        errors.push('Trường thu ngân bắt buộc nhập')
      }
      if(this.params.p_ngay_tt === null)
      {
        errors.push('Trường ngày TT bắt buộc nhập')
      }
      if(this.params.p_httt === null)
      {
        errors.push('Trường hình thức TT bắt buộc nhập')
      }
/*
      if(this.params.p_ghichu === null || this.params.p_ghichu === '' || this.params.p_ghichu.trim() === '')
      {
        errors.push('Trường ghi chú bắt buộc nhập')
      }
*/      

      if(errors.length > 0)
      {
        this.$toast.error(errors.join('\r\n'))
        return false
      }
      return true
    },
    doPopupPhieuHuy: function() {
      this.$refs.refDanhSachPhieuTraHuy.openDialog()
    },
    doGachNo: function() {
      if(!this.validateData())
      {
        return false
      }

      let tongno = this.dsKhoanMucNo.reduce((acc, curr) => {
          return acc = Number(curr.TONGNO) + acc
        }, 0)
      if(tongno <= 0)
      {
        this.$toast.error('Không tồn tại nợ, không thể gạch nợ!')
        return false
      }
      let thungan = this.params.p_thungan_options.filter(x => x.NHANVIEN_ID === this.params.p_thungan)
      let postData = {
        "maTB": this.params.p_acc_somay,
        "maTT": this.params.p_ma_tt,
        "loaiTienId": 1,
        "maTN": thungan !== null ? thungan[0].MA_NV : null,
        "htttId": this.params.p_httt,
        "ngayTT": moment(this.params.p_ngay_tt).format('DD/MM/YYYY'),
        "ghiChu": this.params.p_ghichu !== null ? this.params.p_ghichu.trim() : null
      }

      this.loading(true)

      GachNoKHThanhLyAPI.gachNoThanhLy(this.axios, postData)
      .then(
        (res) => {
          //console.log(res)
          if(
              res.data.error_code === 'BSS-00000000'
          )
          {
            this.$toast.success('Gạch thành công.')
            this.onSearch(1)
          }

        }
      )
      .catch(error => {
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {
        this.loading(false)
      })
    },

    async getDSThuNgan() {

      try{
        GachNoAPI.getDSThuNgan(this.axios).then(
          (response) => {
            if(response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0)
            {
              this.params.p_thungan_options = response.data.data
              this.params.p_thungan = response.data.data[0].NHANVIEN_ID
            }
          }
        )
      } catch (error)
      {
        this.$toast.error(error)
      }
    },
    async getDSHTTT()
    {
      try{
        CommonsAPI.getHinhThucThanhToan(this.axios).then(
          (response) => {
            if(response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined)
            {
              this.params.p_httt_options = response.data.data
              this.params.p_httt = response.data.data[0].HTTT_ID
            }
          }
        )
      } catch (error)
      {
        this.$toast.error(error)
      }
    },
    async getDSNganHang()
    {
      try{
        CommonsAPI.getDSNganHangGachNo(this.axios).then(
          (response) => {
            if(response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined)
            {
              this.params.p_nganhang_options = response.data.data
              // this.params.p_nganhang = response.data.data[0].LOAITIEN_ID
            }
          }
        )
      } catch (error)
      {
        this.$toast.error(error)
      }
    },
    async getPayment_Info_ForPay(kieu) {
      try{
        let postData = {
          pPhanVungId: this.$auth.getPhanVungID(),
          pKyCuoc: this.params.p_kycuoc,
          maTT: kieu === 1 ? this.params.p_ma_tt.trim() : null,
          maTB: kieu === 2 ? this.params.p_acc_somay.trim() : null
        }
        const results = await TraCuuNoTongHopAPI.getThongTinThanhToan(this.axios, postData);
        return results
      }catch(err) {
        this.$toast.error(error.data.message_detail)
        return null
      }
    },
    async getTTKhachHang (data){
      try{
        const results = await GachNoKHThanhLyAPI.getTTKhachHangThanhLy(this.axios, data);
        return results
      }catch(err) {
        console.log(err)
      }
    },

    async onSearch(kieu) {
      if(kieu === 1 && (this.params.p_ma_tt === undefined || this.params.p_ma_tt.trim() === ''))
      {
        this.$toast.error('Trường mã thanh toán bắt buộc nhập.')
        return false
      }
      if(kieu === 2 && (this.params.p_acc_somay === undefined || this.params.p_acc_somay.trim() === ''))
      {
        this.$toast.error('Trường thuê bao bắt buộc nhập.')
        return false
      }
      let postData = {
        maTT: kieu === 1 ? this.params.p_ma_tt.trim() : null,
        maTB: kieu === 2 ? this.params.p_acc_somay.trim() : null,
      }

      this.loading(true)


      let res = await this.getPayment_Info_ForPay(kieu)

      if(res !== null
        && res.data.error_code === 'BSS-00000000'
        && res.data.data !== undefined
        && res.data.data.length > 0
      )
      {
        this.params.p_ma_tt = res.data.data[0].MA_TT
        this.params.p_acc_somay = res.data.data[0].MATB_DD
        this.params.p_ten_tt = res.data.data[0].TEN_TT
        this.params.p_diachi_tt = res.data.data[0].DIACHI_TT

        this.dsKhoanMucNo = []
        this.pageinfo.page = 0
        this.pageinfo.totalElement = 0

        this.getTTKhachHang(postData).then(
          (res) => {
            if(res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined)
            {
              this.keyDSKhoanMucNo++
              this.dsKhoanMucNo = res.data.data
              this.pageinfo.page = 0
              this.pageinfo.totalElement = res.data.data.length
            }
          }
        )
        .catch(error => { this.$toast.error(error.data.messsage_detail)})
        .finally(
          () => {
            this.loading(false)
          }
        )
      } else
      {
        this.$toast.error('Không tìm thấy thông tin thanh toán.')
        this.loading(false)
      }
    },

    onFilteringThuNgan(e){
      var query = new Query();
      var keyword = e.text.trim()
      query = (keyword !== '') ? query.where('TEN_NV', 'contains', keyword, true) : query;
      e.updateData(this.params.p_thungan_options, query);
    },
    onFilteringNganHang(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('TEN_NH', 'contains', e.text, true) : query;
      e.updateData(this.params.p_nganhang_options, query);
    },

   }
}
</script>
