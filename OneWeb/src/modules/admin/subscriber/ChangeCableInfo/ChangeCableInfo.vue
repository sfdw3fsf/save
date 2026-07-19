<template src="./ChangeCableInfo.html"></template>
<style src="./ChangeCableInfo.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import ChangeCableInfoAPI from './ChangeCableInfoAPI'
import gridview from './gridview'
import searchAccount from '@/modules/search/subscriber/SearchAccount'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
export default {
  components: { breadcrumb,gridview, searchAccount },
  name: 'ChangeCableInfoAPI',
  mounted () {
    this.$bvModal.show('suattcap')
    //load ds loại cáp
    ChangeCableInfoAPI.lay_loai_cap(this.axios, {}).then((response) => {
    if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.loai_cap.list = response.data.data;
        this.listbox.loai_cap_m.list = response.data.data;
        }
      }
    );
    //load ds dịch vụ viễn thông
    ChangeCableInfoAPI.lay_ds_dvvt(this.axios, {}).then((response) => {
    if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.ds_dvvt.list = response.data.data;
        }
      }
    );
    // load danh sách loại hình thuê bao
    var pdata = {};
    pdata.dichvuvt_id = this.listbox.ds_dvvt.value;
    console.log(pdata);
    ChangeCableInfoAPI.lay_ds_lhtb(this.axios, pdata).then((response) => {
    if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.ds_lhtb.list = response.data.data;
        console.log(this.listbox.ds_lhtb.list);
        }
      }
    );
    this.listbox.ketquatimkiem.list = [];
    this.listbox.ketquatimkiem.header = [{name:'STT',text:'Số TT'},{name:'somay',text:'Số máy'},{name:'kieu_so',text:'Kiểu số'},{name:'tien_ck',text:'Tiền cam kết'},{name:'ten_ttso',text:'Trạng thái số'},{name:'ngay_gs',text:'Ngày giữ'},{name:'thoigian_gs',text:'Thời gian giữ'},{name:'nguoi_cn',text:'Người giữ'},{name:'ghichu',text:'Ghi chú'}]

  },
  destroyed () {},
  data () {
    return {
      header: {
        title: 'Sửa đổi thông tin cáp',
        list: [
          { 
            name: 'Quản lý ĐB', 
            link: { name: 'Ui.cards' } 
          },
          {
            name: 'Thay đổi thông tin cáp',
            link: { name: 'Ui.buttons' }
          },
        ]
      },
      txtTraCuu: "",
      txtLoaiCap: "",
      txtTuyenCap: "",
      txtHopCap: "",
      txtCapGoc: "",
      txtDoiCapGoc: "",
      txtTuCapGoc: "",
      txtMaLT: "",
      txtLienTu: "",
      txtCuLy: "",
      txtTuCapNgon: "",
      txtDoiCapNgon: "",
      txtCapNgon: "",

      txtLoaiCap1: "",
      txtTuyenCap1: "",
      txtHopCap1: "",
      txtCapGoc1: "",
      txtDoiCapGoc1: "",
      txtTuCapGoc1: "",
      txtMaLT1: "",
      txtLienTu1: "",
      txtCuLy1: "",
      txtTuCapNgon1: "",
      txtDoiCapNgon1: "",
      txtCapNgon1: "",

      txtTenTB: "",
      txtDiaChi: "",
      txtTrangThaiTB: "",
      txtGhiChu: "",
      listbox: {
        loai_cap:{
          list:[],
          value: 1,
          isEnabled:false,
        },
        loai_cap_m:{
          list:[],
          value: 1,
          isEnabled:false,
        },
        ds_dvvt: {
          list:[],
          value: 1,
          isEnabled:false,
        },
        ds_lhtb: {
          list:[],
          value: 1,
          isEnabled:false,
        },
        ketquatimkiem3: {
          list:[],
          value: "",
          isEnabled:false,
        },
        ketquatimkiem: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
        },
        ketquatimkiem_temp: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
        },
      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      from_date: new Date(),
      to_date: new Date()
    }
  },
  methods: {
    filterThueBao() {
      var pdata = {};
      pdata.dichvuvt_id = this.listbox.ds_dvvt.value;
      if (this.listbox.ds_dvvt.value == 1){
        this.listbox.ds_lhtb.value = 1;
      }
      if (this.listbox.ds_dvvt.value == 4){
        this.listbox.ds_lhtb.value = 10;
      }
      if (this.listbox.ds_dvvt.value == 11){
        this.listbox.ds_lhtb.value = 62;
      }
     // console.log(pdata);
      ChangeCableInfoAPI.lay_ds_lhtb(this.axios, pdata).then((response) => {
        if(response && response.data) {
          this.listbox.ds_lhtb.list = response.data.data;
        }
      });
    },
    btnSearchClicked() {
      this.Cancel_Tiem_Kiem();
      if(this.listbox.ds_dvvt.value == 1){
      this.listbox.ketquatimkiem3.list = "";
      this.listbox.ketquatimkiem.list = "";
      var pdata = {};
      if(this.txtTraCuu == ""){
        this.$toast.error("Vui lòng nhập số máy/Account");
      }
      else {
      this.txtTraCuu = this.txtTraCuu.trim();
      pdata.in_matb = this.txtTraCuu;
      pdata.in_dichvuvt_id = this.listbox.ds_dvvt.value;
      pdata.in_donvi_dl_id = 0;
    //  console.log(this.txtTraCuu);
      ChangeCableInfoAPI.tracuu(this.axios, pdata).then((response) => {
        if (response.data.message=="No Content") {
          this.$toast.error("Không có thông tin về thuê bao "+ this.txtTraCuu);
        }
        else if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.ketquatimkiem3.list = response.data.data;
            var pdata1 = {};
            pdata1.in_thuebao_id = this.listbox.ketquatimkiem3.list[0].thuebao_id;  
            ChangeCableInfoAPI.lay_tt_cap(this.axios, pdata1).then((response) => {
              if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
                  this.listbox.ketquatimkiem.list = response.data.data;
        }
        this.listbox.ds_lhtb.value = this.listbox.ketquatimkiem3.list[0].loaitb_id;
        this.listbox.loai_cap.value = this.listbox.ketquatimkiem.list[0].loaicap_id;
        this.listbox.loai_cap_m.value = this.listbox.ketquatimkiem.list[0].loaicap_id;
        this.txtTenTB = this.listbox.ketquatimkiem3.list[0].ten_tb;
        this.txtDiaChi = this.listbox.ketquatimkiem3.list[0].diachi_tb;
        this.txtTrangThaiTB = this.listbox.ketquatimkiem3.list[0].trangthai_tb;
        this.txtTuyenCap = this.listbox.ketquatimkiem.list[0].tuyencap;
        this.txtHopCap = this.listbox.ketquatimkiem.list[0].hopcap_p;
        this.txtTuCapGoc = this.listbox.ketquatimkiem.list[0].tucap_goc;
        this.txtDoiCapGoc = this.listbox.ketquatimkiem.list[0].doicap_goc;
        this.txtCapGoc = this.listbox.ketquatimkiem.list[0].cap_goc;
        this.txtMaLT = this.listbox.ketquatimkiem.list[0].ma_lt;
        this.txtLienTu = this.listbox.ketquatimkiem.list[0].lientu;
        this.txtCuLy = this.listbox.ketquatimkiem.list[0].culy;
        this.txtTuCapNgon = this.listbox.ketquatimkiem.list[0].tucap_p;
        this.txtDoiCapNgon = this.listbox.ketquatimkiem.list[0].doicap_p;
        this.txtCapNgon = this.listbox.ketquatimkiem.list[0].cap_p;
        this.txtGhiChu = this.listbox.ketquatimkiem.list[0].ghichu;

        this.txtTuyenCap1 = this.listbox.ketquatimkiem.list[0].tuyencap;
        this.txtHopCap1 = this.listbox.ketquatimkiem.list[0].hopcap_p;
        this.txtTuCapGoc1 = this.listbox.ketquatimkiem.list[0].tucap_goc;
        this.txtDoiCapGoc1 = this.listbox.ketquatimkiem.list[0].doicap_goc;
        this.txtCapGoc1 = this.listbox.ketquatimkiem.list[0].cap_goc;
        this.txtMaLT1 = this.listbox.ketquatimkiem.list[0].ma_lt;
        this.txtLienTu1 = this.listbox.ketquatimkiem.list[0].lientu;
        this.txtCuLy1 = this.listbox.ketquatimkiem.list[0].culy;
        this.txtTuCapNgon1 = this.listbox.ketquatimkiem.list[0].tucap_p;
        this.txtDoiCapNgon1 = this.listbox.ketquatimkiem.list[0].doicap_p;
        this.txtCapNgon1 = this.listbox.ketquatimkiem.list[0].cap_p;
          }); 
        }
          });
        }
      }
      else if(this.listbox.ds_dvvt.value == 4){
      this.listbox.ketquatimkiem3.list = "";
      this.listbox.ketquatimkiem.list = "";
      var pdata = {};
      if(this.txtTraCuu == ""){
        this.$toast.error("Vui lòng nhập số máy/Account");
      }
      else {
      this.txtTraCuu = this.txtTraCuu.trim()
      pdata.in_matb = this.txtTraCuu;
      pdata.in_dichvuvt_id = this.listbox.ds_dvvt.value;
      pdata.in_donvi_dl_id = 0;
      ChangeCableInfoAPI.tracuu(this.axios, pdata).then((response) => {
        if (response.data.message=="No Content") {
          this.$toast.error("Không có thông tin về thuê bao "+ this.txtTraCuu);
        }
        else if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.ketquatimkiem3.list = response.data.data;
            this.txtTenTB = this.listbox.ketquatimkiem3.list[0].ten_tb;
            this.txtDiaChi = this.listbox.ketquatimkiem3.list[0].diachi_tb;
            this.txtTrangThaiTB = this.listbox.ketquatimkiem3.list[0].trangthai_tb;
            var in_thuebao_id = this.listbox.ketquatimkiem3.list[0].thuebao_id;  
            ChangeCableInfoAPI.lay_tt_cap_megawan(this.axios, "thueBaoId="+in_thuebao_id).then((response) => {
              if(response && response.data && response.data.data) {
                  this.listbox.ketquatimkiem.list = response.data.data;
        }
        this.listbox.ds_lhtb.value = this.listbox.ketquatimkiem3.list[0].loaitb_id;
        this.listbox.loai_cap.value = this.listbox.ketquatimkiem.list[0].loaicap_id;
        this.listbox.loai_cap_m.value = this.listbox.ketquatimkiem.list[0].loaicap_id;
        this.txtTuyenCap = this.listbox.ketquatimkiem.list[0].tuyencap;
        this.txtHopCap = this.listbox.ketquatimkiem.list[0].hopcap_p;
        this.txtTuCapGoc = this.listbox.ketquatimkiem.list[0].tucap_goc;
        this.txtDoiCapGoc = this.listbox.ketquatimkiem.list[0].doicap_goc;
        this.txtCapGoc = this.listbox.ketquatimkiem.list[0].cap_goc;
        this.txtMaLT = this.listbox.ketquatimkiem.list[0].ma_lt;
        this.txtLienTu = this.listbox.ketquatimkiem.list[0].lientu;
        this.txtCuLy = this.listbox.ketquatimkiem.list[0].culy;
        this.txtTuCapNgon = this.listbox.ketquatimkiem.list[0].tucap_p;
        this.txtDoiCapNgon = this.listbox.ketquatimkiem.list[0].doicap_p;
        this.txtCapNgon = this.listbox.ketquatimkiem.list[0].cap_p;
        this.txtGhiChu = this.listbox.ketquatimkiem.list[0].ghichu;

        this.txtTuyenCap1 = this.listbox.ketquatimkiem.list[0].tuyencap;
        this.txtHopCap1 = this.listbox.ketquatimkiem.list[0].hopcap_p;
        this.txtTuCapGoc1 = this.listbox.ketquatimkiem.list[0].tucap_goc;
        this.txtDoiCapGoc1 = this.listbox.ketquatimkiem.list[0].doicap_goc;
        this.txtCapGoc1 = this.listbox.ketquatimkiem.list[0].cap_goc;
        this.txtMaLT1 = this.listbox.ketquatimkiem.list[0].ma_lt;
        this.txtLienTu1 = this.listbox.ketquatimkiem.list[0].lientu;
        this.txtCuLy1 = this.listbox.ketquatimkiem.list[0].culy;
        this.txtTuCapNgon1 = this.listbox.ketquatimkiem.list[0].tucap_p;
        this.txtDoiCapNgon1 = this.listbox.ketquatimkiem.list[0].doicap_p;
        this.txtCapNgon1 = this.listbox.ketquatimkiem.list[0].cap_p;
          }); 
       }
       });
      }
      }
      else if(this.listbox.ds_dvvt.value == 11){
      this.listbox.ketquatimkiem3.list = "";
      this.listbox.ketquatimkiem.list = "";
      var pdata = {};
      if(this.txtTraCuu == ""){
        this.$toast.error("Vui lòng nhập số máy/Account");
      }
      else {
      this.txtTraCuu = this.txtTraCuu.trim()
      pdata.in_matb = this.txtTraCuu;
      pdata.in_dichvuvt_id = this.listbox.ds_dvvt.value;
      pdata.in_donvi_dl_id = 0;
      ChangeCableInfoAPI.tracuu(this.axios, pdata).then((response) => {
        if (response.data.message=="No Content") {
          this.$toast.error("Không có thông tin về thuê bao "+ this.txtTraCuu);
        }
        else if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.ketquatimkiem3.list = response.data.data;
            this.txtTenTB = this.listbox.ketquatimkiem3.list[0].ten_tb;
            this.txtDiaChi = this.listbox.ketquatimkiem3.list[0].diachi_tb;
            this.txtTrangThaiTB = this.listbox.ketquatimkiem3.list[0].trangthai_tb;
            var in_thuebao_id = this.listbox.ketquatimkiem3.list[0].thuebao_id;  
            ChangeCableInfoAPI.lay_tt_cap_ims(this.axios, "thueBaoId="+in_thuebao_id).then((response) => {
              if(response && response.data && response.data.data) {
                  this.listbox.ketquatimkiem.list = response.data.data;
        }
        this.listbox.ds_lhtb.value = this.listbox.ketquatimkiem3.list[0].loaitb_id;
        this.listbox.loai_cap.value = this.listbox.ketquatimkiem.list[0].loaicap_id;
        this.listbox.loai_cap_m.value = this.listbox.ketquatimkiem.list[0].loaicap_id;
        this.txtTuyenCap = this.listbox.ketquatimkiem.list[0].tuyencap;
        this.txtHopCap = this.listbox.ketquatimkiem.list[0].hopcap_p;
        this.txtTuCapGoc = this.listbox.ketquatimkiem.list[0].tucap_goc;
        this.txtDoiCapGoc = this.listbox.ketquatimkiem.list[0].doicap_goc;
        this.txtCapGoc = this.listbox.ketquatimkiem.list[0].cap_goc;
        this.txtMaLT = this.listbox.ketquatimkiem.list[0].ma_lt;
        this.txtLienTu = this.listbox.ketquatimkiem.list[0].lientu;
        this.txtCuLy = this.listbox.ketquatimkiem.list[0].culy;
        this.txtTuCapNgon = this.listbox.ketquatimkiem.list[0].tucap_p;
        this.txtDoiCapNgon = this.listbox.ketquatimkiem.list[0].doicap_p;
        this.txtCapNgon = this.listbox.ketquatimkiem.list[0].cap_p;
        this.txtGhiChu = this.listbox.ketquatimkiem.list[0].ghichu;

        this.txtTuyenCap1 = this.listbox.ketquatimkiem.list[0].tuyencap;
        this.txtHopCap1 = this.listbox.ketquatimkiem.list[0].hopcap_p;
        this.txtTuCapGoc1 = this.listbox.ketquatimkiem.list[0].tucap_goc;
        this.txtDoiCapGoc1 = this.listbox.ketquatimkiem.list[0].doicap_goc;
        this.txtCapGoc1 = this.listbox.ketquatimkiem.list[0].cap_goc;
        this.txtMaLT1 = this.listbox.ketquatimkiem.list[0].ma_lt;
        this.txtLienTu1 = this.listbox.ketquatimkiem.list[0].lientu;
        this.txtCuLy1 = this.listbox.ketquatimkiem.list[0].culy;
        this.txtTuCapNgon1 = this.listbox.ketquatimkiem.list[0].tucap_p;
        this.txtDoiCapNgon1 = this.listbox.ketquatimkiem.list[0].doicap_p;
        this.txtCapNgon1 = this.listbox.ketquatimkiem.list[0].cap_p;
          }); 
       }
       });
      }
      }
    },
    Saved() {
      var pdata = {};
      if(this.listbox.ketquatimkiem3.list==""){
        this.$toast.error("Vui lòng nhập số máy/Account");
      }
      else if(isNaN(this.txtDoiCapGoc)){
        this.$toast.error("Đôi cáp gốc phải là kiểu số");
      }
      else if(isNaN(this.txtDoiCapNgon)){
        this.$toast.error("Đôi cáp ngọn phải là kiểu số");
      }
      else if(isNaN(this.txtCuLy)){
        this.$toast.error("Cự ly phải là kiểu số");
      }
      else {
      pdata.thuebao_id = this.listbox.ketquatimkiem3.list[0].thuebao_id;
      pdata.dichvuvt_id = this.listbox.ds_dvvt.value; 
      var json_data = {};
      json_data.PHANVUNG_ID = this.$auth.getPhanVungID();
      json_data.MA_TB = this.txtTraCuu
      json_data.MA_LT = this.txtMaLT;
      json_data.MA_LT_CU = this.txtMaLT
      json_data.MA_LT_CU = this.txtMaLT
      if(this.txtTuyenCap != ""){
        json_data.TUYENCAP = this.txtTuyenCap.trim();
      }
      else json_data.TUYENCAP = this.txtTuyenCap
      if(this.txtLienTu != ""){
        json_data.LIENTU = this.txtLienTu;
        json_data.LIENTU_CU = this.txtLienTu;
      }
      else{
         json_data.LIENTU = this.txtLienTu
         json_data.LIENTU_CU = this.txtLienTu
      }
      if(this.txtHopCap != ""){
        json_data.HOPCAP_P = this.txtHopCap.trim();
      }
      else json_data.HOPCAP_P = this.txtHopCap
      if(this.txtTuCapNgon != ""){
        json_data.TUCAP_P = this.txtTuCapNgon.trim();
      }
      else json_data.TUCAP_P = this.txtTuCapNgon
      if(this.txtCapGoc != ""){
        json_data.CAP_GOC = this.txtCapGoc.trim();
        json_data.CAP_GOC_CU = this.txtCapGoc.trim();
      }
      else{
        json_data.CAP_GOC = this.txtCapGoc
        json_data.CAP_GOC_CU = this.txtCapGoc
      }
      if(this.txtCapNgon != ""){
        json_data.CAP_P = this.txtCapNgon.trim();
      }
      else json_data.CAP_P = this.txtCapNgon
      if(this.txtTuCapGoc != ""){
        json_data.TUCAP_GOC = this.txtTuCapGoc.trim();
      }
      else json_data.TUCAP_GOC = this.txtTuCapGoc
      json_data.DOICAP_P = this.txtDoiCapNgon;
      json_data.LOAICAP_ID = this.listbox.loai_cap_m.value;
      json_data.CAP_P_CU = this.txtCapNgon
      json_data.DOICAP_GOC = this.txtDoiCapGoc
      json_data.DOICAP_GOC_CU = this.txtDoiCapGoc
      json_data.TUCAP_GOC = this.txtTuCapGoc.trim();
      json_data.TUCAP_GOC_CU = this.txtTuCapGoc
      json_data.CULY = this.txtCuLy
      json_data.CULY_CU = this.txtCuLy
      json_data.TUYENCAP_CU = this.txtTuyenCap
      json_data.DOICAP_P_CU = this.txtDoiCapNgon
      json_data.HOPCAP_P_CU = this.txtHopCap
      json_data.TUCAP_P_CU = this.txtTuCapNgon
      json_data.LOAICAP_ID_CU = this.listbox.loai_cap.value;
      json_data.TRANGTHAI_ID = 1;
      json_data.NHANVIEN_ID = this.$auth.getNhanVienID();
      json_data.DONVI_ID = this.$auth.getDonViID();
      json_data.GHICHU = "Thay đổi thông tin cáp";
      json_data.MAY_CN = "PC";
      json_data.NGUOI_CN = this.$auth.getNguoiDungID();
      json_data = JSON.stringify(json_data);
      pdata.js_data = json_data;
      console.log(pdata);
      if(this.Check()==0){ 
      ChangeCableInfoAPI.luu_lai(this.axios, pdata).then((response) => {
        if(response && response.data && response.data.message=="Success") {
        this.$toast.success("Cập nhật dữ liệu thành công");
        this.btnSearchClicked();
        }
      });
      }
      }
    },
    
    Check(){   
      var thong_bao = "";
      if(this.txtTuCapGoc == "" || this.txtDoiCapGoc == "" || this.txtCapGoc == "" || this.txtCuLy == "" || this.txtDoiCapNgon == "" || this.txtCapNgon == ""){
      if(this.txtTuCapGoc == ""){
        thong_bao = thong_bao + "Hãy nhập thông tin tủ cáp gốc";
       // this.$toast.error("Hãy nhập thông tin tủ cáp gốc");
      }
      if(this.txtDoiCapGoc == ""){
        thong_bao = thong_bao + "\nHãy nhập thông tin đôi cáp gốc";
       // this.$toast.error("Hãy nhập thông tin đôi cáp gốc");
      }
      if(this.txtCapGoc == ""){
        thong_bao = thong_bao + "\nHãy nhập thông tin cáp gốc";
       // this.$toast.error("Hãy nhập thông tin cáp gốc");
      }
      if(this.txtCuLy == ""){
        thong_bao = thong_bao + "\nHãy nhập thông tin cự ly";
       // this.$toast.error("Hãy nhập thông tin cự ly");
      }
      if(this.txtDoiCapNgon == ""){
        thong_bao = thong_bao + "\nHãy nhập thông tin đôi cáp ngọn";
       // this.$toast.error("Hãy nhập thông tin đôi cáp ngọn");
      }
      if(this.txtCapNgon == ""){
        thong_bao = thong_bao + "\nHãy nhập thông tin cáp ngọn";
       // this.$toast.error("Hãy nhập thông tin cáp ngọn");
      }
      this.$toast.error(thong_bao);
          return 1;
      }
      return 0;
    },
    Cancel() {
      this.listbox.ketquatimkiem3.list = "";
      this.listbox.ketquatimkiem.list = "";
      this.txtTraCuu = "";
      this.txtLoaiCap = "";
      this.txtTuyenCap = "";
      this.txtHopCap = "";
      this.txtTuCapGoc = "";
      this.txtDoiCapGoc = "";
      this.txtCapGoc = "";
      this.txtMaLT = "";
      this.txtLienTu = "";
      this.txtCuLy = "";
      this.txtTuCapNgon = "";
      this.txtDoiCapNgon = "";
      this.txtCapNgon = "";
      this.txtLoaiCap1 = "";
      this.txtTuyenCap1 = "";
      this.txtHopCap1 = "";
      this.txtTuCapGoc1 = "";
      this.txtDoiCapGoc1 = "";
      this.txtCapGoc1 = "";
      this.txtMaLT1 = "";
      this.txtLienTu1 = "";
      this.txtCuLy1 = "";
      this.txtTuCapNgon1 = "";
      this.txtDoiCapNgon1 = "";
      this.txtCapNgon1 = "";
      this.txtTenTB = "";
      this.txtDiaChi = "";
      this.txtTrangThaiTB = "";
    },
    Cancel_Tiem_Kiem() {
      this.listbox.ketquatimkiem3.list = "";
      this.listbox.ketquatimkiem.list = "";
     // this.txtTraCuu = "";
      this.txtLoaiCap = "";
      this.txtTuyenCap = "";
      this.txtHopCap = "";
      this.txtTuCapGoc = "";
      this.txtDoiCapGoc = "";
      this.txtCapGoc = "";
      this.txtMaLT = "";
      this.txtLienTu = "";
      this.txtCuLy = "";
      this.txtTuCapNgon = "";
      this.txtDoiCapNgon = "";
      this.txtCapNgon = "";
      this.txtLoaiCap1 = "";
      this.txtTuyenCap1 = "";
      this.txtHopCap1 = "";
      this.txtTuCapGoc1 = "";
      this.txtDoiCapGoc1 = "";
      this.txtCapGoc1 = "";
      this.txtMaLT1 = "";
      this.txtLienTu1 = "";
      this.txtCuLy1 = "";
      this.txtTuCapNgon1 = "";
      this.txtDoiCapNgon1 = "";
      this.txtCapNgon1 = "";
      this.txtTenTB = "";
      this.txtDiaChi = "";
      this.txtTrangThaiTB = "";
    },
    PopUpTraCuu: function () {
      this.$bvModal.show('my-modal-search-account')
    },
    created: async function () {
      this.loading(true);
      this.loadData();
      this.loading(false);
    },
  },
};

</script>
