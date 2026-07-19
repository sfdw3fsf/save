<template src="./ProvideDevices4GforSubscribers.html"></template>
<style src="./ProvideDevices4GforSubscribers.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import api from './api'
import moment from 'moment'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
export default {
  name: 'ProvideDevices4GforSubscribers',
  components: {
    breadcrumb,
    KRequiredMarker,
    KDatePicker,
  },
  data() {
    return {
        thongtin_TB:{
            so_tb:'',
            diachi_kh:"",
            tram_bts:"",
            giatri_KMTB:"",
            loaitb_duocap:"",
            serial:"",
            don_gia:"",
            tg_baohanh: null,
            ten_kh:"",
            phong_banhang:"",
            hang_hoivien:"",
            loai_tb:"",
            imei:"",
            ma_VTHH:"",
            ngay_capmoi:null,
            ngaydoi_baohanh:null,
        },
        isDisabled_hoanthanh:false,
        check_nhapmoi:false,
        thongtin_tb:[],
        hangduoc_chon:[],
        popupComponentName: '',
        popupComponentHeader: '',
        popupComponent: null,
        popupComponentData: null,
        popupComponentAttr: {},
        popupComponentEvts: {},
        list_thietbi4g:[],
        list_thietbi4g_goc:[],
        disabled_ngaycapmoi:true,
        disabled_ngaybh:true,
        hinhthuccap :[{value : "0", list :[]},],
        columns: [
          {fieldName: 'tentat', headerText: 'Tỉnh', allowfiltering: true},
          {fieldName: 'ma_tb', headerText: 'Số thuê bao', allowfiltering: true},
          {fieldName: 'ten_kh', headerText: 'Tên KH', allowfiltering: true},
          {fieldName: 'diachi_kh', headerText: 'Địa chỉ KH', allowfiltering: true},
          {fieldName: 'phong_bh', headerText: 'Phòng bán hàng', allowfiltering: true},
          {fieldName: 'tram_bts', headerText: 'Trạm BTS', allowfiltering: true},
          {fieldName: 'nguoi_cn', headerText: 'Người thực hiện', allowfiltering: true,allowSorting: true},
          {fieldName: 'ngay_cn', headerText: 'Ngày thực hiện', allowfiltering: true},
          {fieldName: 'phong_bh', headerText: 'PBH thực hiện', allowfiltering: true},
          {fieldName: 'ngay_ht', headerText: 'Ngày hoàn thành', allowfiltering: true},
          {fieldName: 'nguoi_th', headerText: 'Người hoàn thành', allowfiltering: true},

        ],

        }
  },
  created: async function () {
  },
  mounted :async function() {
    this.loading(true)
    await this.sp_lay_kieu_tbi_thietbi_thuebao_dd()
    await this.sp_ds_thietbi_thuebao_dd()
    this.loading(false)
  },
  methods: {
    timkiemClick : async function () {
      this.thongtin_TB.diachi_kh = ''
        this.thongtin_TB.ten_kh = ''
        this.thongtin_TB.tram_bts = ''
        this.thongtin_TB.phong_banhang = ''
        this.thongtin_TB.giatri_KMTB =''
        this.thongtin_TB.serial = ''
        this.thongtin_TB.imei = ''
        this.thongtin_TB.tg_baohanh = ''
        this.thongtin_TB.don_gia = ''
        this.thongtin_TB.ngay_capmoi=  ''
        this.thongtin_TB.ngaydoi_baohanh=''
        this.thongtin_TB.loai_tb=''
      this.thongtin_TB.so_tb=this.thongtin_TB.so_tb.trim()
      if (this.thongtin_TB.so_tb.length <= 0){
        this.$toast.error('Vui lòng nhập số thuê bao để tìm kiếm')
        return
      }

      let input = {
        vma_tb:this.thongtin_TB.so_tb,
        vloaitbi_dd:"4G"
                } 
      const res = await api.sp_tt_thietbi_thuebao_dd_theo_matb(this.axios, input)
      console.log("sp_tt_thietbi_thuebao_dd_theo_matb",res)
      if(res.data.error_code === 'BSS-00000000'){
        console.log("tú kiểm tra")
        this.thongtin_tb=res.data.data[0]
        this.index_timkiem(this.thongtin_tb)
        this.list_thietbi4g=this.list_thietbi4g_goc.filter(item => item.ma_tb === this.thongtin_TB.so_tb);
      }
      else{
        this.$toast.error('Thuê bao không nằm trong danh sách được cấp thiết bị 4G')
        await this.sp_ds_thietbi_thuebao_dd()
        this.thongtin_TB.diachi_kh = ''
        this.thongtin_TB.ten_kh = ''
        this.thongtin_TB.tram_bts = ''
        this.thongtin_TB.phong_banhang = ''
        this.thongtin_TB.giatri_KMTB =''
        this.thongtin_TB.serial = ''
        this.thongtin_TB.imei = ''
        this.thongtin_TB.tg_baohanh = ''
        this.thongtin_TB.don_gia = ''
        this.thongtin_TB.ngay_capmoi=  ''
        this.thongtin_TB.ngaydoi_baohanh=''
        this.thongtin_TB.loai_tb=''
      }

    },
    sp_ds_thietbi_thuebao_dd : async function () {
      const res_luoi = await api.sp_ds_thietbi_thuebao_dd(this.axios,{ vkieu : "1"})
      console.log("sp_tt_thietbi_thuebao_dd_theo_matb",res_luoi)
      if(res_luoi.data.error_code === 'BSS-00000000'){
        console.log("this.list_thietbi4g")
        this.list_thietbi4g_goc=res_luoi.data.data
        this.list_thietbi4g=res_luoi.data.data
      }
      else this.list_thietbi4g=[]
    },
    nhapmoiClick : async function () {
      this.thongtin_TB.so_tb=this.thongtin_TB.so_tb.trim()
      if (this.thongtin_TB.so_tb.length <= 0){
        this.$toast.error('Vui lòng nhập số thuê bao để tìm kiếm và thao tác nhập mới lại')
        return
      }
      this.thongtin_TB.giatri_KMTB =''
      this.thongtin_TB.tg_baohanh = ''
      this.hinhthuccap.value=''
      this.thongtin_TB.ngay_capmoi=  ''
      this.thongtin_TB.ngaydoi_baohanh=''
      this.check_nhapmoi=true
    },
    ghilaiClick : async function () {
      console.log("this.$root.token",this.$root.token)
      var ip =await this.$root.token.getIP()
      console.log("this.$root.token.getThongTinNguoiDung()",ip , typeof ip)
      console.log("this.$root.token.55555()",this.$root.token.getUserName())

      if (this.check_nhapmoi){
        if(this.thongtin_TB.giatri_KMTB.trim()==''){
          this.$toast.error("Bạn chưa điền Giá trị khuyến mãi TB")
          return
        }
        if(this.thongtin_TB.tg_baohanh=='' || this.thongtin_TB.tg_baohanh==null){
          this.$toast.error("Bạn chưa chọn TG bảo hành TB")
          return
        }
        if(this.hinhthuccap.value==''){
          this.$toast.error("Bạn chưa chọn Hình thức cấp TB")
          return
        }
        else {
          if(this.hinhthuccap.value==1){
            if(this.thongtin_TB.ngay_capmoi=='' || this.thongtin_TB.ngay_capmoi==null ){
              this.$toast.error("Bạn chưa chọn Ngày cấp mới")
              return
            }
          }
          else {
            if(this.thongtin_TB.ngaydoi_baohanh=='' || this.thongtin_TB.ngaydoi_baohanh==null){
              this.$toast.error("Bạn chưa chọn Ngày đổi bảo hành")
              return
            }
          }
        }

        var vkieu_input=0
        this.check_nhapmoi=false
        var ds_capnhat=[{
        DSTB_ID:this.thongtin_tb.dstb_id,
        TBI_TB_ID:"",
        GIATRI_KM:this.thongtin_TB.giatri_KMTB,
        KIEUTBI_ID:this.hinhthuccap.value,
        HAN_BH:this.thongtin_TB.tg_baohanh,
        NGAY_CAP:this.thongtin_TB.ngay_capmoi,
        NGAY_BH:this.thongtin_TB.ngaydoi_baohanh,
        NGUOI_CN:this.$root.token.getUserName(),
        MA_TB:this.thongtin_tb.ma_tb,
        MAY_CN:ip,
        IP_CN:ip
        }]
        var input ={
          vkieu:vkieu_input,
          vds: JSON.stringify(ds_capnhat)
        }
        console.log("input cập nhật", input)

        const res = await api.sp_capnhat_thietbi_thuebao_dd(this.axios, input)
        console.log("sp_capnhat_thietbi_thuebao_dd",res)
        if(res.data.error_code === 'BSS-00000000' && res.data.data == "OK"){
          this.$toast.success('Cập nhật thành công!')
          await this.sp_ds_thietbi_thuebao_dd()
          this.list_thietbi4g=this.list_thietbi4g_goc.filter(item => item.ma_tb === this.thongtin_TB.so_tb);
        }
        else{
          this.$toast.warning(res.data.message_detail.split('\n')[0].split(': ')[1])
        }
      } else 

      {
        if(this.thongtin_TB.giatri_KMTB.trim()==''){
          this.$toast.error("Bạn chưa điền Giá trị khuyến mãi TB")
          return
        }
        if(this.thongtin_TB.tg_baohanh=='' || this.thongtin_TB.tg_baohanh==null){
          this.$toast.error("Bạn chưa chọn TG bảo hành TB")
          return
        }
        if(this.hinhthuccap.value==''){
          this.$toast.error("Bạn chưa chọn Hình thức cấp TB")
          return
        }
        else {
          if(this.hinhthuccap.value==1){
            if(this.thongtin_TB.ngay_capmoi=='' || this.thongtin_TB.ngay_capmoi==null ){
              this.$toast.error("Bạn chưa chọn Ngày cấp mới")
              return
            }
          }
          else {
            if(this.thongtin_TB.ngaydoi_baohanh=='' || this.thongtin_TB.ngaydoi_baohanh==null){
              this.$toast.error("Bạn chưa chọn Ngày đổi bảo hành")
              return
            }
          }
        }

        var vkieu_input=1
        this.check_nhapmoi=false
        var ds_capnhat=[{
        DSTB_ID:this.thongtin_tb.dstb_id,
        TBI_TB_ID:this.hangduoc_chon.tbi_tb_id,
        GIATRI_KM:this.thongtin_TB.giatri_KMTB,
        KIEUTBI_ID:this.hinhthuccap.value,
        HAN_BH:this.thongtin_TB.tg_baohanh,
        NGAY_CAP:this.thongtin_TB.ngay_capmoi,
        NGAY_BH:this.thongtin_TB.ngaydoi_baohanh,
        NGUOI_CN:this.$root.token.getUserName(),
        MAY_CN:ip,
        IP_CN:ip
        }]
        var input ={
          vkieu:vkieu_input,
          vds: JSON.stringify(ds_capnhat)
        }
        console.log("input cập nhật", input)

        const res = await api.sp_capnhat_thietbi_thuebao_dd(this.axios, input)
        console.log("sp_capnhat_thietbi_thuebao_dd",res)
        if(res.data.error_code === 'BSS-00000000' && res.data.data == "OK"){
          this.$toast.success('Cập nhật thành công!')
          await this.sp_ds_thietbi_thuebao_dd()
          this.list_thietbi4g=this.list_thietbi4g_goc.filter(item => item.ma_tb === this.thongtin_TB.so_tb);
        }
        else{
          this.$toast.warning(res.data.message_detail.split('\n')[0].split(': ')[1])
        }
      }
    },
    xoaClick : async function () {
      var ip =await this.$root.token.getIP()
      console.log("this.$root.token.getIP()",ip , typeof ip)
      console.log("this.$root.token.getUserName()",this.$root.token.getUserName())
      var ds_capnhat=[{
        DSTB_ID:this.hangduoc_chon.dstb_id,
        TBI_TB_ID:this.hangduoc_chon.tbi_tb_id,
        GIATRI_KM:this.hangduoc_chon.giatri_km,
        KIEUTBI_ID:this.hangduoc_chon.kieutbi_id,
        HAN_BH:this.hangduoc_chon.han_bh,
        NGAY_CAP:this.hangduoc_chon.ngay_cap,
        NGAY_BH:this.hangduoc_chon.ngay_bh,
        NGUOI_CN:this.$root.token.getUserName(),
        MA_TB:this.hangduoc_chon.ma_tb,
        MAY_CN:ip,
        IP_CN:ip
      }]
      var input ={
        vkieu:2,
        vds: JSON.stringify(ds_capnhat)
      }
      console.log("input xó", input)
      const res = await api.sp_capnhat_thietbi_thuebao_dd(this.axios, input)
      console.log("sp_capnhat_thietbi_thuebao_dd",res)
      if(res.data.error_code === 'BSS-00000000'){
        this.$toast.success('Xóa thành công!')
        await this.sp_ds_thietbi_thuebao_dd()
        // this.list_thietbi4g=this.list_thietbi4g_goc.filter(item => item.ma_tb === this.thongtin_TB.so_tb);
      }
      else{
        this.$toast.error(res.data.message_detail.split('\n')[0].split(': ')[1])
      }
    },
    sp_lay_kieu_tbi_thietbi_thuebao_dd : async function () {
      const res = await api.sp_lay_kieu_tbi_thietbi_thuebao_dd(this.axios)
      console.log("sp_lay_kieu_tbi_thietbi_thuebao_dd",res)
      if(res.data.error_code === 'BSS-00000000'){
        console.log("kiểm tra selected")
        this.hinhthuccap.list = res.data.data.map(e => ({id: e.kieutbi_id, text: e.kieu_tbi}))
        console.log("kiểm tra selected 2424",this.hinhthuccap.list )
      }
      else{
        this.$toast.error('Xóa thất bại!')
      }
    },
    xuatkhoClick : async function () {
       window.open('https://onebss.vnpt.vn/#/qlvt/NhapXuatVT?Tag=1')
    },
    hoanthanhClick : async function () {
        this.showPopupHoanthanh()
    },
    index_timkiem(data){
      console.log("data",data)
      this.thongtin_TB.so_tb=data.ma_tb
      this.thongtin_TB.diachi_kh = data.diachi_kh
      this.thongtin_TB.ten_kh = data.ten_kh
      this.thongtin_TB.tram_bts = data.tram_bts
      this.thongtin_TB.phong_banhang =data.phong_bh
      this.thongtin_TB.loai_tb=data.ma_tb.startsWith('842') ? "Gphone" : ""
    },
    onSelectedRow_1: function (args) {
      console.log("hàng được chọn",args.data , typeof args.data )
      this.hangduoc_chon=args.data
      if(this.hangduoc_chon.length != []){
        this.hangduoc_chon.trangthai_tbi_id==6 ? this.isDisabled_hoanthanh=true: this.isDisabled_hoanthanh=false
        console.log("this.hangduoc_chon.han_bh",this.hangduoc_chon.han_bh , typeof this.hangduoc_chon.han_bh)
        this.hinhthuccap.value=this.hangduoc_chon.kieutbi_id ? this.hangduoc_chon.kieutbi_id: null
        this.thongtin_TB.loai_tb= this.hangduoc_chon.ma_tb.startsWith('842') ? "Gphone" : ""
        this.thongtin_TB.giatri_KMTB =this.hangduoc_chon.giatri_km
        this.thongtin_TB.serial = this.hangduoc_chon.serial
        this.thongtin_TB.imei = this.hangduoc_chon.imei
        this.thongtin_TB.tg_baohanh = (this.hangduoc_chon.han_bh!==null) ? this.convertDateTimeFormat(this.hangduoc_chon.han_bh) : null
        this.thongtin_TB.don_gia = this.hangduoc_chon.dongia
        this.thongtin_TB.ngay_capmoi= (this.hangduoc_chon.ngay_cap!==null)? this.convertDateTimeFormat(this.hangduoc_chon.ngay_cap) : null
        this.thongtin_TB.ngaydoi_baohanh= (this.hangduoc_chon.ngay_bh!==null)? this.convertDateTimeFormat(this.hangduoc_chon.ngay_bh) : null
        console.log("this.hinhthuccap.value",this.hinhthuccap.value, typeof this.hinhthuccap.value)
        if(this.hinhthuccap.value == 1 ){
          this.disabled_ngaycapmoi=false
          this.disabled_ngaybh=true
        }
        else if (this.hinhthuccap.value == 5) {
          this.disabled_ngaycapmoi=true
          this.disabled_ngaybh=false
        }
        else {
          this.disabled_ngaycapmoi=true
          this.disabled_ngaybh=true
        }
      }
    },
    
    change_hinhthuc(args){
      console.log("change_hinhthuc",args)
      if(args == 1 ){
        this.disabled_ngaycapmoi=false
        this.disabled_ngaybh=true
      }
      else {
        this.disabled_ngaycapmoi=true
        this.disabled_ngaybh=false
      }
    },
    formatDate(date, format) {
      let defaultFormat = 'DD/MM/YYYY'
      if (format) {
        return moment(date).format(format)
      } else {
        return moment(date).format(defaultFormat)
      }
    },
    convertDateTimeFormat(dateTimeStr) {
      const [datePart, timePart] = dateTimeStr.split(' ');
      const [year, month, day] = datePart.split('-');
      const formattedDateTimeStr = `${day}/${month}/${year} ${timePart}`;
      return formattedDateTimeStr;
   },
    showPopupHoanthanh : function () {
      this.popupComponent = () =>
        import(
          "@/modules/contract/setup/ProvideDevices4GforSubscribers/popup/hoanthanh.vue"
        );
      this.popupComponentName = "hoanthanh_capthietbi";
      this.popupComponentHeader = 'Hoàn thành cấp thiết bị 4G cho thuê bao';
      this.popupComponentData = {
        isPopup: true,
        thongtin_tb:this.thongtin_tb,
        rowclick:this.hangduoc_chon
      }; 
      console.log("this.popupComponentData", this.popupComponentData)
      this.Popup("popupComponents");
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    async ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
      this.popupComponent = null;
    },
    async popupClosed(val) {
        if (!val) {
        this.ClosePopup('popupComponents')
        return
      }
      this.loading(true);
      try {
        switch (this.popupComponentName) {
          case "hoanthanh_capthietbi": 
            console.log();
            this.capnhat_KQ_OB.chuyen_b2a=val
          break;
        }
      } catch (ex) {
        if (ex.data && ex.data.message_detail) {
          this.$toast.error("" + ex.data.message_detail);
        } else {
          this.$toast.error("" + ex);
        }
      } finally {
        this.ClosePopup("popupComponents");
        this.loading(false);
      }
    },
  },
  watch: {
  'capnhat_KQ_OB.tt_ketnoi': async function(newVal) {
    console.log('Current value:', newVal);
    console.log('Type of val:', typeof newVal);
    if (newVal) {
      await this.getListKetQuaOB(newVal);
    }
  },
  list_thietbi4g (newVal){
    if (newVal.length>0) this.isDisabled_hoanthanh=false
    else this.isDisabled_hoanthanh=true
  }
},

  computed: {
  },
}
</script>
