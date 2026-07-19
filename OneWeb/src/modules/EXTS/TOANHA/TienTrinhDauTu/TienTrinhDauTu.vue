<template src='./TienTrinhDauTu.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from 'moment';
import Vue from "vue";
import TienTrinhDauTuAPI from  '../api/TienTrinhDauTuAPI'
import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
// import { getterName,actionName,statePropertyName,mutationName} from '../store/tientrinhdautu'
// import { DialogPlugin } from '@syncfusion/ej2-vue-popups'

Vue.use(DatePickerPlugin)
export default {

  components: {  },
  name: "TienTrinhDauTu",
  mounted() {
  },
  computed: {
    // ...mapState("quanlythietbibts", statePropertyName),
    // ...mapGetters("quanlythietbibts", getterName),
    vttdt_id_luoi: {
      get() {
        return this.grvDanhSach_FocusedRow != null && this.grvDanhSach_FocusedRow["ttdt_id"] != '' ? parseInt(this.grvDanhSach_FocusedRow["ttdt_id"]) : 0
      }
    },
    vso_cv_giaonv_luoi: {
      get() {
        return this.grvDanhSach_FocusedRow != null && this.grvDanhSach_FocusedRow["so_cv_giaonv"] != '' ? this.grvDanhSach_FocusedRow["so_cv_giaonv"] : ""
      }
    },
    vduan_xdht_luoi: {
      get() {
        return this.grvDanhSach_FocusedRow != null && this.grvDanhSach_FocusedRow["duan_xdht"] != '' ? this.grvDanhSach_FocusedRow["duan_xdht"] : ""
      }
    },
    vhddt_id_luoi: {
      get() {
        return this.grvDanhSach_FocusedRow != null && this.grvDanhSach_FocusedRow["hddt_id"] != '' ? parseInt(this.grvDanhSach_FocusedRow["hddt_id"]) : 0
      }
    },
    vdonvi_tk_id_luoi: {
      get() {
        return this.grvDanhSach_FocusedRow != null && this.grvDanhSach_FocusedRow["donvi_tk_id"] != '' ? parseInt(this.grvDanhSach_FocusedRow["donvi_tk_id"]) : 0
      }
    },
    vngay_giaonv_luoi: {
      get() {
        return this.grvDanhSach_FocusedRow != null && this.grvDanhSach_FocusedRow["ngay_giaonv"] != '' ? this.f_StringToDate(this.grvDanhSach_FocusedRow["ngay_giaonv"],'YYYY-MM_DD hh:mm:ss')  : ""
      }
    },
    vdonvi_tk_id: {
      get() {
        return this.params.p_DV_TrienKhai == '' ? -1 : parseInt(this.params.p_DV_TrienKhai)
      }
    },
    vngay_giaonv: {
      get() {
        return this.f_DateToString(this.params.p_dtpNgayGiaoNV,'DD/MM/YYYY')
      }
    },
    
  },
  data() {
    return {
        params: {
            p_txtHDDT: "",
            p_DV_TrienKhai: "",
            p_dtpNgayGiaoNV: new Date(),
            p_txtSoCV: "",
            p_txtDuAn: "",

        },
        cboDV_TrienKhai: [],
        dgvDanhSach: [],
        
        vttdt_id: 0,
        dt: [],
        vhddt_id: 0,
        vma_hd: "",
        vten_hd: "",

        ReadOnly: {
            txtHDDT: false
        },
        Enabled: {
            tsbtnNhapMoi: false,
            tsbtnGhiLai: false,
            tsbtnXoa: false,
            tsbtnHuyBo: false,
        },
        grvDanhSach_FocusedRow: {}

    };
  },
  methods: {
    // ...mapActions("ecms/quanlythietbibts", actionName),
    // ...mapMutations("quanlythietbibts", mutationName),

    // chuyển Date thành type string
     f_DateToString: function (value,format='DD/MM/YYYY'){
      return moment(value).format(format)
    },
    f_StringToDate(value,format='DD/MM/YYYY'){
      return moment(value,format).toDate();
    },
    async frmTienTrinh_DauTu_Load(){
        await this.SetButton(-1)
        await this.Load_cbo()
        this.ReadOnly.txtHDDT = true
        await this.ShowData()
        this.params.p_txtHDDT = this.vma_hd + " -- " + this.vten_hd
    },
    async ShowData(){
        
        await TienTrinhDauTuAPI.getDSTienTrinhDauTu(this.axios, {hddt_id: this.vhddt_id})
            .then(
                (response) => {
                  this.dt = response.data.data || []
                }
            )
            .catch(error=>this.$toast.error(error)).finally(function() {})

        this.dgvDanhSach = []
        if (this.dt.length){
            this.dgvDanhSach = this.dt
        }
        else{
            await this.SetButton(1)
        }
    },
    async Load_cbo(){
        var dtDVTK = []
        await TienTrinhDauTuAPI.getDSDonVi(this.axios,)
            .then(
                (response) => {
                    dtDVTK = response.data.data || []
                }
            )
            .catch(error=>this.$toast.error(error)).finally(function() {})
        this.cboDV_TrienKhai = dtDVTK
    },
    async SetButton(status){
        this.Enabled.tsbtnNhapMoi = false;
        this.Enabled.tsbtnGhiLai = false;
        this.Enabled.tsbtnXoa = false;
        this.Enabled.tsbtnHuyBo = false;

        switch (status){
            case -1: //Begin
                this.Enabled.tsbtnGhiLai = true;
                this.Enabled.tsbtnHuyBo = true;
                break;
            case 0: //Begin
                this.Enabled.tsbtnNhapMoi = true;
                await this.Clear();
                break;
            case 1: //Insert 
                await this.Clear();
                this.Enabled.tsbtnGhiLai = true;
                this.Enabled.tsbtnHuyBo = true;
                break;
            case 2: //Cancel
                this.Enabled.tsbtnNhapMoi = true;
                //this.Enabled.tsbtnXoa = true;
                await this.Clear();
                break;
            case 3: //Edit
                this.Enabled.tsbtnNhapMoi = true;
                this.Enabled.tsbtnXoa = true;
                this.Enabled.tsbtnGhiLai = true;
                this.Enabled.tsbtnHuyBo = true;
                break;
        }
    },
    async Clear(){
        this.params.p_txtSoCV = "";
        this.params.p_txtDuAn = "";
        this.params.p_DV_TrienKhai = "";
        this.dtpNgayGiaoNV = new Date()
        this.$refs.cboDV_TrienKhai.focusIn();
    },
    async tsbtnNhapMoi_Click(){
        await this.SetButton(1)
    },
    async tsbtnXoa_Click(){
        await this.$bvModal.msgBoxConfirm("Bạn thật sự muốn xoá dữ liệu không?", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                var s = null
                await TienTrinhDauTuAPI.FN_TienTrinhDauTu(this.axios,{
                    "p_kieu": 3,
                    "p_ttdt_id": this.vttdt_id,
                    "p_hddt_id": this.vhddt_id,
                    "p_donvi_tk_id": this.vdonvi_tk_id,
                    "p_so_cv_giaonv": this.params.p_txtSoCV,
                    "p_ngay_giaonv": this.vngay_giaonv,
                    "p_duan_xdht": this.params.p_txtDuAn,
                    })
                    .then(
                        (response) => {
                            s = response.data.data || []
                        }
                    )
                    .catch(error=>this.$toast.error(error)).finally(function() {})}
                
                if (s == null || s == ""){
                    this.$toast.error("Không nhận được thông tin phản hồi khi xóa dữ liệu")
                    return
                }
                if(s.toString() == "1"){
                    this.$toast.success("Xóa dữ liệu thành công")
                    await this.ShowData()
                }
                else{
                    this.$toast.error(s)
                }
          })
          .catch(err => {
              // An error occurred
          })
    },
    async grvDanhSach_FocusedRowChanged(row){
      
      // if (Object.keys(row).length){
      //   try {
          this.grvDanhSach_FocusedRow = row    
          this.vttdt_id = parseInt(this.vttdt_id_luoi);
          this.params.p_DV_TrienKhai = this.vdonvi_tk_id_luoi;
          this.params.p_txtSoCV = this.vso_cv_giaonv_luoi;
          this.params.p_dtpNgayGiaoNV = this.vngay_giaonv_luoi;
          this.params.p_txtDuAn = this.vduan_xdht_luoi;
          await this.SetButton(3);
      //   } catch (error) {
          
      //   }
      // }
      // else{
      //   this.grvDanhSach_FocusedRow = null
      // }
    },
    async tsbtnGhiLai_Click(){
        await this.CapNhat()
    },
    async tsbtnHuyBo_Click(){
        await this.SetButton(2)
    },
    async tsbtnThoat_Click(){

    },
    async Kiemtra_DL(){
        if (this.params.p_cboDV_TrienKhai == ""){
            this.$toast.error("Hãy nhập Đơn vị triển khai");
            this.$refs.cboDV_TrienKhai.focusIn();
            return false;
        }
        if (this.params.p_dtpNgayGiaoNV == null || this.params.p_dtpNgayGiaoNV == ""){
            this.$toast.error("Hãy nhập Ngày giao nhiệm vụ");
            this.$refs.dtpNgayGiaoNV.focus();
            return false;
        }
        if (this.params.p_txtDuAn.trim().length > 200){
            this.$toast.error("Dự án XDHT không được lớn hơn 200 ký tự");
            this.$refs.txtDuAn.focus();
            return false;
        }
        if (this.params.p_txtSoCV.trim().length > 200){
            this.$toast.error("Số công văn không được lớn hơn 200 ký tự");
            this.$refs.txtSoCV.focus();
            return false;
        }

        return true;
    },
    async CapNhat_load(){
        var kieu = 0
        //Nhập mới
        if (!this.Enabled.tsbtnNhapMoi){
            kieu = 1;
        }
        var s = ""
        await TienTrinhDauTuAPI.FN_TienTrinhDauTu(this.axios,{
            "p_kieu": kieu,
            "p_ttdt_id": this.vttdt_id,
            "p_hddt_id": this.vhddt_id,
            "p_donvi_tk_id": this.vdonvi_tk_id,
            "p_so_cv_giaonv": this.params.p_txtSoCV,
            "p_ngay_giaonv": this.vngay_giaonv,
            "p_duan_xdht": this.params.p_txtDuAn,
            })
            .then(
                (response) => {
                    s = response.data.data || ""
                }
            )
            .catch(error=>this.$toast.error(error)).finally(function() {})
        return s
    },
    async CapNhat(){
        if (! await this.Kiemtra_DL()) return;
        var s = await this.CapNhat_load()

        if (s.toString().startsWith("OK-")){
            //Nhập mới
            if (!this.Enabled.tsbtnNhapMoi){
                this.$toast.success("Nhập mới thành công");
            }
            else{
                this.$toast.success("Cập nhật thành công");
            }
            var _id = parseInt(s.toString().split('-')[1]);
            await this.ShowData()
            let index = this.dgvDanhSach.findIndex(item=>item.ttdt_id == _id)
            this.$refs.grvDanhSach.selectRow(index)
        }
        else{
            this.$toast.error("Thông báo: " + s)
        }
        

    },

    async tsbtnXem_Click(){
        await this.ShowData()
    }
    

  },
created: async function() {
    await this.frmTienTrinh_DauTu_Load()
  },
  watch: {
    
  },
  destroyed () {
  }
};
</script>
<style>
  
</style>