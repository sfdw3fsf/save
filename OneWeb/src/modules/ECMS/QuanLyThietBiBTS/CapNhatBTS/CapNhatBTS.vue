<template src='./CapNhatBTS.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from 'moment';
import Vue from "vue";
import breadcrumb from '@/components/breadcrumb'
import QuanLyThietBiBTSAPI from  '../../api/QuanLyThietBiBTSAPI'
import select2 from '@/components/Select2.vue'
import { getterName,actionName,statePropertyName,mutationName} from '../../store/quanlythietbibts'
import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import {  } from '@syncfusion/ej2-vue-calendars'


Vue.use(DatePickerPlugin);
export default {

  components: {breadcrumb, appSelect2: select2 },
  name: "CapNhatBTS",
  mounted() {
  },
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
        txtKinhDo: "",
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
        //baolt 7/7/2023
        p_dvql_id: "",
        p_dvxl_id: "",
        DVQL_ID: "",
        DVXL_ID: "",
        NV_ID: "",
        SDT_NV: "",
        DONVI_XLSC_ID: "",
        DONVI_QUANLY_ID: ""
      },
      cboUPE: [],
      cboSWL2: [],
      //baolt 7/7/2023
      cboDVQL: [],
      cboDVXL: [],
      LOAI_KV: {
        KHUVUC_CHINHSACH: 1, //
        KHUVUC_KHAC: 2,
        KHUVUC_THULAO_DAILY: 3,
        KHUVUC_DIABAN: 4,
        KHUVUC_DIABAN_VT: 5,
        KHUVUC_DIABAN_TT: 6,
        KHUVUC_DIABAN_TC: 7
      },
      donvi_id: this.$root.token.getDonViID() * 1,
      cboNV: [],
      cboDVNXL: [],
      cboDVQLTB: [],
      _action_type: "A",
      _ma_node: "",
      _phan_loai: "",
      Text: {
        lblTrangThai: "(Sửa)"
      }
      
    };
  },
  methods: {
    ...mapActions("ecms/quanlythietbibts", actionName),
    // ...mapMutations("quanlythietbibts", mutationName),

    // chuyển Date thành type string
     f_DateToString: function (value,format='DD/MM/YYYY'){
      return moment(value).format(format)
    },
    f_StringToDate(value,format='DD/MM/YYYY'){
      return moment(value,format).toDate();
    },
    async frmCapNhatBTS(action_type, ma_node, phan_loai ){
      this._action_type = action_type;
      this._ma_node = ma_node;
      this._phan_loai = phan_loai;

      if (this._action_type == "A"){
        this.Text.lblTrangThai = "(Thêm mới)";
      }
      else{
        this.Text.lblTrangThai = "(Sửa)";
      }
      await this.load_form()
      await this.Load_Item(this._action_type, this._ma_node, this._phan_loai);
    },
    Close(){
      this.$parent.hide()
    },
    async btnCapNhat_Click(){
      // try {
        var objBTS = {}
        objBTS.phan_loai = this.params.txtPhanLoai
        objBTS.bts_id = 0
        objBTS.ma_node = this.params.txtMaNode
        objBTS.upe_id = this.params.UPE
        objBTS.swl2_id = this.params.SWL2

        //baolt 7/7/2023
        objBTS.khuvuc_id = this.params.DVQL_ID
        objBTS.khuvuc_xuly_id = this.params.DVXL_ID
        objBTS.nhanvien_ttdh_id = this.params.NV_ID
        objBTS.so_dt_ttdh = this.params.SDT_NV
        objBTS.donvi_xlsc_id = this.params.DONVI_XLSC_ID
        objBTS.donvi_quanly_id = this.params.DONVI_QUANLY_ID

        var resCapNhat = await this.CapNhatThongTinBTS({
          "p_action_type": this._action_type,
          "p_json_input": JSON.stringify(objBTS),
        })
        if (resCapNhat == "ok"){
          this.$toast.success("Cập nhật thành công !")
        }
        else{
          this.$toast.error(resCapNhat)
        }
      // } catch (error) {
      //   this.$toast.error(error)
      // }
    },
    async load_form(){
      var dsDanhMuc = await this.getDanhMucBTS()
        if (Object.keys(dsDanhMuc).length){
          this.cboUPE = dsDanhMuc['items_2']
          this.cboUPE.length ? this.params.UPE = this.cboUPE[0].DSLAM_ID : ""

          this.cboSWL2 = dsDanhMuc['items_3']
          this.cboSWL2.length ? this.params.SWL2 = this.cboSWL2[0].DSLAM_ID : ""

          //baolt 7/7/2023
          var dsDVQL = await QuanLyThietBiBTSAPI.getDonViQuanLy(this.axios,{
            p_loaikv_id: this.LOAI_KV.KHUVUC_DIABAN
          })
          if(dsDVQL.data.data.returnds.length){
            this.cboDVQL = dsDVQL.data.data.returnds
          }
          var dsDVXL = await QuanLyThietBiBTSAPI.getDonViXuLy(this.axios,{
            p_loaikv_id: this.LOAI_KV.KHUVUC_DIABAN
          })
          if(dsDVXL.data.data.returnds.length){
            this.cboDVXL = dsDVXL.data.data.returnds
          }
          var dsNV = await QuanLyThietBiBTSAPI.getNhanVien(this.axios,{
            donvi_id: this.donvi_id
          })
          if(dsNV.data.data.returnds){
            this.cboNV = dsNV.data.data.returnds
          }
          var dsDVNXL = await QuanLyThietBiBTSAPI.getDonViNhanXuLy(this.axios)
          if(dsDVNXL.data.data.returnds){
            this.cboDVNXL = dsDVNXL.data.data.returnds
          }
          var dsDVQLTB = await QuanLyThietBiBTSAPI.getDonViQLThietBi(this.axios)
          if(dsDVQLTB.data.data.returnds){
            this.cboDVQLTB = dsDVQLTB.data.data.returnds
          }
        }
    },
    frmCapNhatBTS_Load(){

    },
    async Load_Item(action_type, ma_node, phan_loai){
      if (this._action_type == "A"){
        await this.Clear()
      }
      else{
        var dt = await this.getChiTietBTS({
          p_phan_loai: phan_loai,
          p_ma_node: ma_node
        })
        if (dt){
          this.params.txtPhanLoai = dt.returnds[0]["PHAN_LOAI"] || ''
          this.params.txtTrangThaiHoatDong = dt.returnds[0]["TRANGTHAI_HOATDONG"] || ''
          this.params.txtMaNode = dt.returnds[0]["MA_NODE"] || ''
          this.params.txtTen_QL = dt.returnds[0]["TEN_QL"] || ''
          this.params.txtTen_ThietBi_HeThong = dt.returnds[0]["TEN_THIETBI_HETHONG"] || ''
          this.params.txtDiaChi = dt.returnds[0]["DIACHI"] || ''
          this.params.txtMaNodeCha = dt.returnds[0]["MA_NODE_CHA"] || ''
          this.params.txtCauHinh = dt.returnds[0]["CAUHINH"] || ''
          this.params.txtTen_QuanHuyen = dt.returnds[0]["TEN_QUANHUYEN"] || ''
          this.params.txtTen_XaPhuong = dt.returnds[0]["TEN_PHUONGXA"] || ''
          this.params.txtNhanVien = dt.returnds[0]["TEN_NV"] || ''
          this.params.txtSoDT = dt.returnds[0]["SDT_NV"] || ''
          this.params.txtBangTan = dt.returnds[0]["BANGTAN"] || ''
          this.params.txtMaCSHT = dt.returnds[0]["MA_CSHT"] || ''
          this.params.txtKinhDo = dt.returnds[0]["KINHDO"] || ''
          this.params.txtViDo = dt.returnds[0]["VIDO"] || ''
          this.params.txtHangSX = dt.returnds[0]["TEN_THIETBI"] || ''
          this.params.txtLoaiTram = dt.returnds[0]["LOAI_TRAM"] || ''
          this.params.txtLoaiTramPSBD = dt.returnds[0]["LOAI_TRAM_PSBD"] || ''
          this.params.txtLoaiThietBi = dt.returnds[0]["LOAI_THIETBI"] || ''
          this.params.txtHoanCanhRaDoi = dt.returnds[0]["HOANCANH_RADOI"] || ''
          this.params.UPE = dt.returnds[0]["UPE_ID"] || ''
          this.params.SWL2 = dt.returnds[0]["SWL2_ID"] || ''
          this.params.txtTen_QuanHuyen = dt.returnds[0]["TEN_QUANHUYEN"] || ''
          this.params.txtTen_XaPhuong = dt.returnds[0]["TEN_PHUONGXA"] || ''
          //baolt 7/7/2023
          this.params.DVQL_ID = dt.returnds[0]["KHUVUC_ID"] || '',
          this.params.DVXL_ID = dt.returnds[0]["KHUVUC_XULY_ID"] || ''
          this.params.NV_ID = dt.returnds[0]["NHANVIEN_TTDH_ID"] || ''
          this.params.SDT_NV = dt.returnds[0]["SO_DT_TTDH"] || ''
          this.params.DONVI_XLSC_ID = dt.returnds[0]["DONVI_XLSC_ID"] || '',
          this.params.DONVI_QUANLY_ID = dt.returnds[0]["DONVI_QUANLY_ID"] || ''
          if (dt.returnds[0]["NGAY_HOATDONG"] != null && dt.returnds[0]["NGAY_HOATDONG"] != ""){
            this.params.dtpNgayHoatDong = this.f_StringToDate(dt.returnds[0]["NGAY_HOATDONG"],'YYYY-MM-DD HH:mm:ss.S');
          }
          if (dt.returnds[0]["NGAY_CAPPHEP"] != null && dt.returnds[0]["NGAY_CAPPHEP"] != ""){
            this.params.dtpNgayCapPhep = this.f_StringToDate(dt.returnds[0]["NGAY_CAPPHEP"],'YYYY-MM-DD HH:mm:ss.S');
          }
          if (dt.returnds[0]["NGAY_KIEMDUYET"] != null && dt.returnds[0]["NGAY_KIEMDUYET"] != ""){
            this.params.dtpNgayKiemDuyet = this.f_StringToDate(dt.returnds[0]["NGAY_KIEMDUYET"],'YYYY-MM-DD HH:mm:ss.S');
          }
          if (dt.returnds[0]["NGAY_DANGKY"] != null && dt.returnds[0]["NGAY_DANGKY"] != ""){
            this.params.dtpNgayDangKy = this.f_StringToDate(dt.returnds[0]["NGAY_DANGKY"],'YYYY-MM-DD HH:mm:ss.S');
          }

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
      this.params.DVQL_ID = '',
      this.params.DVXL_ID = '',
      this.params.NV_ID = '',
      this.params.SDT_NV = '',
      this.params.DONVI_XLSC_ID = '',
      this.params.DONVI_QUANLY_ID = ''
    },
    cboQuanHuyen_EditValueChanged(){

    },
    cboXaPhuong_EditValueChanged(){

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
  
</style>