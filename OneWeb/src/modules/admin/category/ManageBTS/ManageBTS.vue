<template src='./ManageBTS.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from 'moment';
import Vue from "vue";
import breadcrumb from '@/components/breadcrumb'
//import api from  './api'
import select2 from '@/components/Select2.vue'
// import { getterName,actionName,statePropertyName,mutationName} from '../../store/quanlythietbibts'
import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import {  } from '@syncfusion/ej2-vue-calendars'

import DiaChiKhachHangModal from '../../../contract/setup/SurveyRequest/popups/ChonDiaChi/DiaChiKhachHangModal.vue'

import VungPhuModal from './popup/VungPhuModal'

Vue.use(DatePickerPlugin);
export default {

  components: {breadcrumb, appSelect2: select2,DiaChiKhachHangModal,VungPhuModal },
  name: "CapNhatBTS",
  computed: {
    // ...mapState("quanlythietbibts", statePropertyName),
    // ...mapGetters("quanlythietbibts", getterName),

  },
  data() {
    return {

      params: {
        txtPhanLoai: "",
        txtTrangThaiHoatDong: "",
        dtpNgayHoatDong: '',
        dtpNgayDangKy: '',

        txtMaNode: "",
        dtpNgayKiemDuyet: '',
        dtpNgayCapPhep: '',

        txtTen_QL: "",
        txtNhanVien: "",
        txtSoDT: "",

        txtTen_ThietBi_HeThong: "",
        txtMaCSHT: "",
        txtBangTan: "",

        UPE: "",
        txtBTSID: "",
        txtViDo: "",

        SWL2: "",
        txtHangSX: "",
        txtLoaiTram: "",

        txtCauHinh: "",
        txtLoaiThietBi: "",
        txtLoaiTramPSBD: "",

        txtMaNodeCha: "",
        txtHoanCanhRaDoi: "",

        txtDiaChi: "",
        txtTen_QuanHuyen: "",
        txtTen_XaPhuong: "",

        txtKhuVuc :"",
        txtLuuTru :"",
        NVQL:"",
        txtVungPhu : ""
      },
      cboUPE: [],
      cboSWL2: [],
      cboKV: [],
      cboLuutru : [],
      cboNVQL : [],
      _action_type: "A",
      _ma_node: "",
      _phan_loai: "",
      Text: {
        lblTrangThai: "(Cập nhật)"
      },
      dataDiaChi:{
        diachi_id:0,
        tinh_id:0,
        quan_id:0,
        phuong_id:0,
        pho_id:0,
        ap_id:0,
        khu_id:0,
        dacdiem_id:0,
        sonha:'',
        diachi:''
      },
      vjs :[],
      dataVungPhu :{
        pbh_id:'',
        khuvuc_id: '',
        customerSearchType :'',
        ttvt_id:'',
        tram_id:'',
        vungphu :''
      },
      system_name:''
      
    };
  },
  mounted() {
    this.cboLuutru = [  {id : 1, name: 'Lưu động'},
                                  { id : 2 , name: 'Cố định'}]
    //this.load_form();

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
    async frmCapNhatBTS(action_type, ma_node, phan_loai, system_name){
      this._action_type = action_type;
      //console.log(this._action_type)
      this._ma_node = ma_node;
      this._phan_loai = phan_loai;
      this.system_name=system_name

      if (this._action_type == "A"){
        this.Text.lblTrangThai = "(Thêm mới)";
      }
      else{
        this.Text.lblTrangThai = "(Cập nhật)";
      }
      await this.load_form()
      await this.Load_Item(this._action_type, this._ma_node, this._phan_loai);
    },
    Close(){
      this.$parent.hide()
    },
    async btnCapNhat_Click(){
      try {
        if (this.params.txtMaNode == null || this.params.txtMaNode == "")
        {
            this.$root.$toast.warning('Bạn chưa nhập thông tin Mã node')
            return;
        }
        if (this.params.txtBTSID == null || this.params.txtBTSID == "")
        {
            this.$root.$toast.warning('Bạn chưa nhập thông tin BTS ID')
            return;
        }
        if (!this.params.txtDiaChi || this.params.txtDiaChi == "")
        {
            this.$root.$toast.warning('Bạn chưa chọn thông tin Địa chỉ')
            return;
        }
        if (!this.params.txtKhuVuc || this.params.txtKhuVuc == "")
        {
            this.$root.$toast.warning('Bạn chưa chọn thông tin khu vực')
            return;
        }
        let user = JSON.parse(localStorage.getItem('OneBSS-Client'))
        var vjs_bts = {}
        var vjs_khuvuc_phusong_bts ={};
        var data ;
        var pdata = {
          PHANVUNG_ID: user.phanvung_id,
          PHAN_LOAI : this.params.txtPhanLoai,
          BTS_ID : this.params.txtBTSID,
          MA_NODE : this.params.txtMaNode,
          TEN_NV : this.params.txtNhanVien,
          SDT_NV : this.params.txtSoDT,
          TRANGTHAI_HOATDONG : this.params.txtTrangThaiHoatDong,
          NGAY_HOATDONG : this.params.dtpNgayHoatDong,
          HOANCANH_RADOI : this.params.txtHoanCanhRaDoi,
          TEN_QL : this.params.txtTen_QL,
          NGAY_DANGKY : this.params.dtpNgayDangKy,
          NGAY_KIEMDUYET : this.params.dtpNgayKiemDuyet,
          NGAY_CAPPHEP :this.params.dtpNgayCapPhep,
          CAUHINH : this.params.txtCauHinh,
          DIACHI : this.params.txtDiaChi,
          MA_CSHT : this.params.txtMaCSHT,
          MA_NODE_CHA : this.params.txtMaNodeCha,
          TEN_THIETBI_HETHONG : this.params.txtTen_ThietBi_HeThong,
          BANGTAN : this.params.txtBangTan,
          LOAI_TRAM : this.params.txtLoaiTram,
          LOAI_THIETBI : this.params.txtLoaiThietBi,
          LOAI_TRAM_PSBD : this.params.txtLoaiTramPSBD,
          UPE_ID : this.params.UPE,
          SWL2_ID : this.params.SWL2,
          KHUVUC_ID : this.params.txtKhuVuc,
          LUUTRU_ID : this.params.txtLuuTru
        }
        var pdataps = [];
        if(this.dataVungPhu.khuvuc_id.length != 0)
          for(let i = 0; i< this.dataVungPhu.khuvuc_id.length; i++){
            let tdata = {
              "PHANVUNG_ID":user.phanvung_id,
              "KHUVUC_ID":this.dataVungPhu.khuvuc_id[i],
              "BTS_ID":this.params.txtBTSID
            }
            pdataps.push(tdata);
          }
          console.log(pdataps)
        vjs_bts = JSON.stringify(pdata);
        vjs_khuvuc_phusong_bts = JSON.stringify(pdataps);
        if(this._action_type == "U"){
          let input={
            system_name:this.system_name,
            vjs_bts,
            vjs_khuvuc_phusong_bts  }
          console.log('input', input) 
          this.loading(true)
          data = await this.axios.post("web-quantri/khai-bao-bts/sp_update_bts",input);
          this.loading(false)
          if(data.data.error == '200'){
            this.$root.$toast.success('Cập nhật thành công.')
            this.Close();
          }else{
            this.$root.$toast.error(data.data.message_detail)
          }
          // if(data.data.error =="400")
          // this.$root.$toast.error(data.data.message_detail)
        }
        if(this._action_type == "A"){
          this.loading(true)
          data = await this.axios.post("web-quantri/khai-bao-bts/sp_insert_bts",{
            system_name:this.params.txtTen_ThietBi_HeThong?this.params.txtTen_ThietBi_HeThong.trim():'',
            vjs_bts,
            vjs_khuvuc_phusong_bts  });
          this.loading(false)
          if(data.data.error == '200'){
            this.$root.$toast.success('Thêm mới thành công.')
            this.Close();
          }else{
            this.$root.$toast.error(data.data.message_detail)
          }
          // if(data.data.error =="400")
          //   this.$root.$toast.error(data.data.message_detail)
        }
      }
      catch(err){
        this.loading(false)
        if(err.data&&err.data.message){
          this.$toast.error(err.data.message)
        }else{
          this.$root.$toast.error(err.message)
        }
      }
    },
  
    async load_form(){
      try {
        let data = await this.axios.get("web-ecms/bts/lay-danhmuc-bts-nodeb")
        let dsDanhMuc = data.data.data
        if (Object.keys(dsDanhMuc).length){
          this.cboUPE = dsDanhMuc['items_2']
          this.cboUPE.length ? this.params.UPE = this.cboUPE[0].DSLAM_ID : ""

          this.cboSWL2 = dsDanhMuc['items_3']
          this.cboSWL2.length ? this.params.SWL2 = -1 : ""
        }
        //Load all khu vực

        data = await this.axios.post("web-quantri/diaban/sp_lay_ds_khuvuc_theo_diadanh_2",{
          v_loaikv_id : 4,          
          v_phuong_id : 0,
          v_pho_id :0,
          v_ap_id : 0,
          v_khu_id : 0,
          v_dacdiem_id : 0,
          v_quan_id : 0
        })
        dsDanhMuc = data.data.data
        if (Object.keys(dsDanhMuc).length){
          this.cboKV = dsDanhMuc;
          if(this.cboKV.length){
            this.params.txtKhuVuc = this.cboKV[0].khuvuc_id;
            await this.changeKV(this.cboKV[0],2)
          }else{
            this.params.txtKhuVuc =""
          }
        }
      } catch (err) {

      }

    },
    async Load_KhuVuc(){
      try {
        let data = await this.axios.post("web-quantri/diaban/sp_lay_ds_khuvuc_theo_diadanh_2",{
          v_loaikv_id : 4,
          v_phuong_id : this.dataDiaChi.phuong_id = "" ? 0 : this.dataDiaChi.phuong_id,
          v_pho_id : this.dataDiaChi.pho_id = "" ? 0 : this.dataDiaChi.pho_id,
          v_ap_id : this.dataDiaChi.ap_id = "" ? 0 : this.dataDiaChi.ap_id,
          v_khu_id : this.dataDiaChi.khu_id = "" ? 0 : this.dataDiaChi.khu_id,
          v_dacdiem_id : this.dataDiaChi.dacdiem_id = "" ? 0 : this.dataDiaChi.dacdiem_id,
          v_quan_id : this.dataDiaChi.quan_id = "" ? 0 : this.dataDiaChi.quan_id  ,
        })
        let dsDanhMuc = data.data.data
        if (Object.keys(dsDanhMuc).length){
          this.cboKV = dsDanhMuc;
          if(this.cboKV.length){
            this.params.txtKhuVuc = this.cboKV[0].khuvuc_id;
            this.changeKV(this.cboKV[0],2)
          }else
            this.params.txtKhuVuc ="";

        }
      } catch (err) {
        this.cboKV = [];
      }
    },
    async changeKV(event,type){
      try{
        console.log(event)
        var khu_id = (type == 1) ? event.value : event;
        let data = await this.axios.post("web-quantri/diaban/sp_lay_nhanvien_diaban_theo_khuvuc_id",{
          "v_khu_id" : khu_id

        })
        let dsDanhMuc = data.data.data
        if (Object.keys(dsDanhMuc).length){
          this.cboNVQL = dsDanhMuc;
          this.cboNVQL.length ? this.params.NVQL = this.cboNVQL[0].nhanvien_id : "";
        }
      }
      catch(err){
        console.log("Load kv lỗi")
        //this.$root.$toast.console.error('Load')
      }
    },
    clickChonDiaChi(){
          this.dataDiaChi=this.dataDiaChi
          this.$refs.diaChiModal.showModal();
    },
    async onAcceptDiaChi(data){
      Object.assign(this.dataDiaChi, data)
      this.params.txtDiaChi=data.diachi
      await this.Load_KhuVuc();
    },
    async onAcceptVungPhu(data){
      Object.assign(this.dataVungPhu, data)
      this.params.txtVungPhu=data.vungphu
      //await this.Load_KhuVuc();
    },
    clickChonVungPhu(){
      //this.dataDiaChi=this.dataDiaChi
      this.$refs.VungPhuModal.loadPbhAndTtvt();
      //this.$refs.popup_VungPhuModal.show();
      this.$refs.VungPhuModal.showModal();
    },
    async Load_Item(action_type, ma_node, phan_loai){
      if (this._action_type == "A"){
        await this.Clear()
      }else{
        var data = await this.axios.post('web-quantri/khai-bao-bts/sp_lay_chitiet_bts_enodeb',{
          vphan_loai: phan_loai,
          vma_node: ma_node
        })
        let dt = data.data.data.data;
        if (dt.length){
          this.params.txtPhanLoai = dt[0]["phan_loai"] || ''
          this.params.txtTrangThaiHoatDong = dt[0]["trangthai_hoatdong"] || ''
          this.params.txtMaNode = dt[0]["ma_node"] || ''
          this.params.txtTen_QL = dt[0]["ten_ql"] || ''
          this.params.txtTen_ThietBi_HeThong = dt[0]["ten_thietbi_hethong"] || ''
          this.params.txtDiaChi = dt[0]["diachi"] || ''
          this.params.txtMaNodeCha = dt[0]["ma_node_cha"] || ''
          this.params.txtCauHinh = dt[0]["cauhinh"] || ''
          this.params.txtTen_XaPhuong = dt[0]["ten_phuongxa"] || ''
          this.params.txtNhanVien = dt[0]["ten_nv"] || ''
          this.params.txtSoDT = dt[0]["sdt_nv"] || ''
          this.params.txtBangTan = dt[0]["bangtan"] || ''
          this.params.txtMaCSHT = dt[0]["ma_csht"] || ''
          this.params.txtKinhDo = dt[0]["kinhdo"] || ''
          this.params.txtViDo = dt[0]["vido"] || ''
          this.params.txtHangSX = dt[0]["ten_thietbi"] || ''
          this.params.txtLoaiTram = dt[0]["loai_tram"] || ''
          this.params.txtLoaiTramPSBD = dt[0]["loai_tram_psbd"] || ''
          this.params.txtLoaiThietBi = dt[0]["loai_thietbi"] || ''
          this.params.txtHoanCanhRaDoi = dt[0]["hoancanh_radoi"] || ''
          this.params.UPE = dt[0]["upe_id"] || ''
          this.params.SWL2 = dt[0]["swl2_id"] || ''
          this.params.txtLuuTru = dt[0]["luutru_id"] || ''
          this.params.txtTen_XaPhuong = dt[0]["ten_phuongxa"] || ''
          this.params.txtBTSID = dt[0]["bts_id"] || ''
         
          
          if (dt[0]["ngay_hoatdong"] != null && dt[0]["ngay_hoatdong"] != ""){
            this.params.dtpNgayHoatDong = this.f_StringToDate(dt[0]["ngay_hoatdong"],'YYYY-MM-DD HH:mm:ss.S');
          }
          if (dt[0]["ngay_capphep"] != null && dt[0]["ngay_capphep"] != ""){
            this.params.dtpNgayCapPhep = this.f_StringToDate(dt[0]["ngay_capphep"],'YYYY-MM-DD HH:mm:ss.S');
          }
          if (dt[0]["ngay_kiemduyet"] != null && dt[0]["ngay_kiemduyet"] != ""){
            this.params.dtpNgayKiemDuyet = this.f_StringToDate(dt[0]["ngay_kiemduyet"],'YYYY-MM-DD HH:mm:ss.S');
          }
          if (dt[0]["ngay_dangky"] != null && dt[0]["ngay_dangky"] != ""){
            this.params.dtpNgayDangKy = this.f_StringToDate(dt[0]["ngay_dangky"],'YYYY-MM-DD HH:mm:ss.S');
          }
          this.params.txtKhuVuc=dt[0]["khuvuc_id"] || ''
          await this.changeKV(this.cboKV[0],2)

          let arrVungPhu = this.cboKV.filter(item1 => data.data.data.khuvuc.some(item2 => item2.khuvuc_id === item1.khuvuc_id)).map((x) => x.ten_kv)
          this.params.txtVungPhu = arrVungPhu.toString()
        }
      }
    },
    async Clear(){
      this.params.txtPhanLoai = "";
      this.params.txtTrangThaiHoatDong = "";
      this.params.txtMaNode = "";
      this.params.txtTen_QL = "";
      this.params.txtTen_ThietBi_HeThong = "";
      this.params.txtMaNodeCha = "";
      this.params.txtCauHinh = "";
      this.params.txtDiaChi = "";
      this.params.txtTen_QuanHuyen = "";
      this.params.txtTen_XaPhuong = "";
      this.params.txtNhanVien = "";
      this.params.txtSoDT = "";
      this.params.txtBangTan = "";
      this.params.txtMaCSHT = "";
      this.params.txtKinhDo = "";
      this.params.txtViDo = "";
      this.params.txtHangSX = "";
      this.params.txtLoaiTram = "";
      this.params.txtLoaiTramPSBD = "";
      this.params.txtLoaiThietBi = "";
      this.params.txtHoanCanhRaDoi = "";
      this.params.dtpNgayCapPhep = "";
      this.params.dtpNgayDangKy = "";
      this.params.dtpNgayHoatDong = "";
      this.params.dtpNgayKiemDuyet = "";
      this.params.txtBTSID = "";
      this.params.NVQL = "";
      this.params.txtKhuVuc = "";
      this.cboKV = [];
      this.cboNVQL = [];
      this.params.txtVungPhu = ''
    },


  },
created: async function() {

  },
  watch: {

  },
  destroyed () {
  }
};
</script>
<style>
  .w140 {
    width: 140px;
  }
</style>
