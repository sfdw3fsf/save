<template src='./ThemMoiSanPham.html'>

</template>

<script>
import moment from 'moment'
import api from '../../API'
export default {

  data: function(){
    return{
      comboboxData: {
        NHOM_SPDV: [],
        DONVI: [],
        PHUONGTHUC_PT: [],
        DM_DICHVU_CLVT: [],
        LINHVUC_CN:[],
      },
      cboSPCHA:{
        list:[],
      },
      tt_sanpham:{
        spdv_id:0,
        trangthai_gtm:1,
        sinhma_tudong:1,
        ten_spdv:"",
        ten_viettat:"",
        ma_spdv:"",
        ten_tienganh:"",
        ten_thuongmai:"",
        phienban:"",
        hientrang_gtm:"",
        tailieu:"",
        ngay_gtm:"",
        thongtin_khac:"",
        ngaydm_cty:"",
        ngaydm_td:"",
        giatri_manglai:"",
        socu_trienkhai:"",
        yeucau_kt:"",
        tt_thitruong:"",
        ten_cnct:"",
        chucdanh_cnct:"",
        email_cnct:"",
        so_dt_cnct:"",
        dtpNgayGTM:"",
        dtpNgayDMCT:"",
        dtpNgayDMTD:"",
        ghichu:"",
        ghichu_ng:"",
        nhomspdv_id:0,
        dv_chuquan_id:null,
        dv_phattrien_id:null,
        phuongthuc_id:null,
        dv_clvt_id:null,
        spdv_cha_id:null,
        linhvuc_cn_id:null,
      },
    }
  },
  watch:{

  },
  methods:{
    async load_Form() {
      this.tt_sanpham.ngay_gtm = moment().format("DD/MM/YYYY");
      this.tt_sanpham.ngaydm_cty = moment().format("DD/MM/YYYY") ;
      this.tt_sanpham.ngaydm_td = moment().format("DD/MM/YYYY");
      this.comboboxData = await this.lay_thongtin_cbo();
    },
    async openDialog(data) {
      if (data) {
        this.tt_sanpham = data
      } else {
        this.tt_sanpham = {
          spdv_id:0,
          trangthai_gtm:1,
          sinhma_tudong:1,
          ten_spdv:"",
          ten_viettat:"",
          ma_spdv:"",
          ten_tienganh:"",
          ten_thuongmai:"",
          phienban:"",
          hientrang_gtm:"",
          tailieu:"",
          ngay_gtm:"",
          thongtin_khac:"",
          ngaydm_cty:"",
          ngaydm_td:"",
          giatri_manglai:"",
          socu_trienkhai:"",
          yeucau_kt:"",
          tt_thitruong:"",
          ten_cnct:"",
          chucdanh_cnct:"",
          email_cnct:"",
          so_dt_cnct:"",
          ghichu:"",
          ghichu_ng:"",
          nhomspdv_id:0,
          dv_chuquan_id:null,
          dv_phattrien_id:null,
          phuongthuc_id:null,
          dv_clvt_id:null,
          spdv_cha_id:null,
          linhvuc_cn_id:null,
        }
      }
      this.cboSPCHA.list = this.GetDataList(await api.sp_lay_ds_sanpham_cha(this.axios,{spdv_id:this.tt_sanpham.spdv_id}));


    },
    async tsbtnGhiLai_Click(){
      try{
      if(this.tt_sanpham.ten_spdv == null || this.tt_sanpham.ten_spdv == ""){
        this.$root.$toast.error("Vui lòng nhập thông tin tên Nội bộ");
        return;
      }
      if(this.tt_sanpham.nhomspdv_id == null){
        this.$root.$toast.error("Vùi lòng kiểm tra thông tin Nhóm SPDV");
        return;
      }
      if(this.tt_sanpham.phuongthuc_id == null){
        this.$root.$toast.error("Vùi lòng kiểm tra thông tin PT phát triển");
        return;
      }

      if(this.tt_sanpham.dv_clvt_id == null){
        this.$root.$toast.error("Vùi lòng kiểm tra thông tin DV cốt lõi vệ tinh");
        return;
      }

        this.loading(true)
        if(this.tt_sanpham.spdv_id != null){
          let response = await api.fn_capnhat_sanpham(this.axios, this.tt_sanpham)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            this.$root.$toast.success("Cập nhật thành công");
            this.hideModal();
          }else{
            this.$root.$toast.error(response.data.message);
          }
        }
        else{
          let response = await api.fn_themmoi_sanpham(this.axios, this.tt_sanpham)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            this.$root.$toast.success("Thêm mới thành công");
            this.hideModal();
          }else{
            this.$root.$toast.error(response.data.message);
          }
        }

      }catch(e){
        console.log(e);
        this.$root.$toast.error(e);
      }finally{
        this.loading(false)
      }
    },
    async tsbtnGuiPheDuyet_Click(){
      if(this.tt_sanpham.spdv_id == null){
        this.$toast.error("Không có thông tin sản phẩm");
      }
      else{
        let response = await api.fn_gui_duyet_sanpham_cty(this.axios, { spdv_id: this.tt_sanpham.spdv_id })
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          this.$toast.success('Gửi duyệt sản phẩm TCT thành công')
          this.hideModal();
        } else {
          this.$toast.error(response.data.message)
        }
      }
    },
    hideModal(){
      this.$emit("accept", {});
    },
    async loadData(){
      try{
        this.loading(true)
        await this.load_Form();
        this.loading(false)
      }
      catch (e){
        this.loading(false)
      }
    },
    clearData(){
      this.cboSPCHA.list = [];
      this.tt_sanpham = {
          spdv_id:0,
          ten_spdv:"",
          ten_viettat:"",
          ma_spdv:"",
          ten_tienganh:"",
          ten_thuongmai:"",
          phienban:"",
          hientrang_gtm:"",
          tailieu:"",
          ngay_gtm:"",
          thongtin_khac:"",
          ngaydm_cty:"",
          ngaydm_td:"",
          giatri_manglai:"",
          socu_trienkhai:"",
          yeucau_kt:"",
          tt_thitruong:"",
          ten_cnct:"",
          chucdanh_cnct:"",
          email_cnct:"",
          so_dt_cnct:"",
          trangthai_gtm: 1,
          sinhma_tudong: 1,
          ghichu:"",
          ghichu_ng:"",
          nhomspdv_id:0,
          dv_chuquan_id:null,
          dv_phattrien_id:null,
          phuongthuc_id:null,
          dv_clvt_id:null,
          spdv_cha_id:null,
          linhvuc_cn_id:null,

      };
    },
    async lay_thongtin_cbo() {
      return this.GetDataList(await api.sp_lay_danhmuc_chung(this.axios))
    },

    GetDataList: function(response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000') {
        return response.data.data == null ? [] : response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
      } else {
        console.log(response)
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
    },
  }
}
</script>

<style scoped>

</style>
