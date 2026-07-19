<template src='./QuanLyThietBiBTS.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from 'moment';
import Vue from "vue";
import breadcrumb from '@/components/breadcrumb'
import QuanLyThietBiBTSAPI from  '../api/QuanLyThietBiBTSAPI'
import select2 from '@/components/Select2.vue'
import { getterName,actionName,statePropertyName,mutationName} from '../store/quanlythietbibts'
import CapNhatBTS  from './CapNhatBTS'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'

class BtsNoded{
  BANGTAN = "";
  BTS_ID = "";
  CAUHINH = "";
  COSITE_ID = "";
  DIACHI = "";
  FILTER_USER = "";
  HOANCANH_RADOI = "";
  KHUVUC_ID = "";
  KHUVUC_XULY_ID = "";
  KINHDO = 0;
  LOAI_THIETBI = "";
  LOAI_TRAM = "";
  LOAI_TRAM_PSBD = "";
  LOAI_TRUYENDAN = null;
  LUUTRU_ID = null;
  MA_COSITE = null;
  MA_CSHT = "";
  MA_NODE = "";
  MA_NODE_CHA = "";
  MA_TRAM_DA = null;
  NGAY_CAPPHEP = "";
  NGAY_DANGKY = "";
  NGAY_HOATDONG = "";
  NGAY_KIEMDUYET = "";
  NHANVIEN_TTDH_ID = "";
  PHANVUNG_ID = "";
  PHAN_LOAI = "";
  PHUONG_ID = null;
  PORT_KETNOI = null;
  QUAN_ID = null;
  SDT_NV = "";
  SO_DT_TTDH = "";
  SWL2_ID = 0;
  TEN_DVI = "";
  TEN_NV = "";
  TEN_PHUONGXA = "";
  TEN_QL = "";
  TEN_QUANHUYEN = "";
  TEN_SWL2 = "";
  TEN_THIETBI = "";
  TEN_THIETBI_HETHONG = "";
  TEN_UPE = "";
  TRANGTHAI_DAT_MAYNO = null;
  TRANGTHAI_HOATDONG = "";
  UPE_ID = null;
  VIDO = 0;
  KHUVUC = "";
  KHUVUC_XULY = "";
  NHANVIEN_TTDH = "";
  DONVI_XLSC_ID = "";
  DONVI_QUANLY_ID = "";
  DONVI_XLSC = "";
  DONVI_QUANLY = "";

  constructor(object = {}){
    this.BANGTAN = object.BANGTAN || "";
    this.BTS_ID = object.BTS_ID || "";
    this.CAUHINH = object.CAUHINH || "";
    this.COSITE_ID = object.COSITE_ID || "";
    this.DIACHI = object.DIACHI || "";
    this.FILTER_USER = object.FILTER_USER || "";
    this.HOANCANH_RADOI = object.HOANCANH_RADOI || "";
    this.KHUVUC_ID = object.KHUVUC_ID || "";
    this.KHUVUC_XULY_ID = object.KHUVUC_XULY_ID || "";
    this.KINHDO = object.KINHDO || 0;
    this.LOAI_THIETBI = object.LOAI_THIETBI || "";
    this.LOAI_TRAM = object.LOAI_TRAM || "";
    this.LOAI_TRAM_PSBD = object.LOAI_TRAM_PSBD || "";
    this.LOAI_TRUYENDAN = object.LOAI_TRUYENDAN || null;
    this.LUUTRU_ID = object.LUUTRU_ID || null;
    this.MA_COSITE = object.MA_COSITE || null;
    this.MA_CSHT = object.MA_CSHT || "";
    this.MA_NODE = object.MA_NODE || "";
    this.MA_NODE_CHA = object.MA_NODE_CHA || "";
    this.MA_TRAM_DA = object.MA_TRAM_DA || null;
    this.NGAY_CAPPHEP = object.NGAY_CAPPHEP || "";
    this.NGAY_DANGKY = object.NGAY_DANGKY || "";
    this.NGAY_HOATDONG = object.NGAY_HOATDONG || "";
    this.NGAY_KIEMDUYET = object.NGAY_KIEMDUYET || "";
    this.NHANVIEN_TTDH_ID = object.NHANVIEN_TTDH_ID || "";
    this.PHANVUNG_ID = object.PHANVUNG_ID || "";
    this.PHAN_LOAI = object.PHAN_LOAI || "";
    this.PHUONG_ID = object.PHUONG_ID || null;
    this.PORT_KETNOI = object.PORT_KETNOI || null;
    this.QUAN_ID = object.QUAN_ID || null;
    this.SDT_NV = object.SDT_NV || "";
    this.SO_DT_TTDH = object.SO_DT_TTDH || "";
    this.SWL2_ID = object.SWL2_ID || 0;
    this.TEN_DVI = object.TEN_DVI || "";
    this.TEN_NV = object.TEN_NV || "";
    this.TEN_PHUONGXA = object.TEN_PHUONGXA || "";
    this.TEN_QL = object.TEN_QL || "";
    this.TEN_QUANHUYEN = object.TEN_QUANHUYEN || "";
    this.TEN_SWL2 = object.TEN_SWL2 || "";
    this.TEN_THIETBI = object.TEN_THIETBI || "";
    this.TEN_THIETBI_HETHONG = object.TEN_THIETBI_HETHONG || "";
    this.TEN_UPE = object.TEN_UPE || "";
    this.TRANGTHAI_DAT_MAYNO = object.TRANGTHAI_DAT_MAYNO || null;
    this.TRANGTHAI_HOATDONG = object.TRANGTHAI_HOATDONG || "";
    this.UPE_ID = object.UPE_ID || null;
    this.VIDO = object.VIDO || 0;
    this.KHUVUC = object.KHUVUC || "";
    this.KHUVUC_XULY = object.KHUVUC_XULY || "";
    this.NHANVIEN_TTDH = object.NHANVIEN_TTDH || "";
    this.DONVI_XLSC_ID = object.DONVI_XLSC_ID || "";
    this.DONVI_QUANLY_ID = object.DONVI_QUANLY_ID || "";
    this.DONVI_XLSC = object.DONVI_XLSC || "";
    this.DONVI_QUANLY = object.DONVI_QUANLY || "";
  }
  
}


Vue.use(DialogPlugin)
export default {

  components: { CapNhatBTS, breadcrumb, appSelect2: select2 },
  name: "QuanLyThietBiBTS",
  mounted() {
  },
  computed: {
    // ...mapState("quanlythietbibts", statePropertyName),
    // ...mapGetters("quanlythietbibts", getterName),

  },
  data() {
    return {
        params: {
          p_PhanLoai: "",
          p_Ma_Node: "",
          p_UPE: "",
          p_SWL2: "",
          p_Ten_thietbi: "",
          p_Diachi: "",
          p_DVQL_ID: null,
          p_DVXL_ID: null,
          p_NV_ID: null,
          p_SDT_NV: "",
          p_DONVI_XLSC_ID: null,
          p_DONVI_QUANLY_ID: null
        },
        cboPhanLoai: [],
        cboUPE: [],
        cboSWL2: [],
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
        gridBTS: [],
        wbrChiTietThietBi: "",

        _focusRowHandle: 0,
        

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
    async grvBTS_FocusedRowChanged(row){
      // try{
        // var indx = row.rowIndex;
        var r_phan_loai = row.PHAN_LOAI.toString()
        var r_ma_node = row.MA_NODE.toString();

        var strThongTinThietBi = await this.getThongTinThietBiBTS({
          "p_phan_loai": r_phan_loai,
          "p_ma_node": r_ma_node
        })

        // Xóa hiển thị cũ
        this.wbrChiTietThietBi = "";
        // Hiển thị dữ liệu mới
        this.wbrChiTietThietBi = strThongTinThietBi || '';
      // }
      // catch (error){
      //     this.$toast.error("Có lỗi: " + error.toString());
      // }
    },

    async frmBTS_Load(){
      // try{
        var dsDanhMuc = await this.getDanhMucBTS()
        if (Object.keys(dsDanhMuc).length){
          this.cboPhanLoai = dsDanhMuc['items_1']
          this.cboPhanLoai.length ? this.params.p_PhanLoai = this.cboPhanLoai[0].MA_LOAI : ""

          this.cboUPE = dsDanhMuc['items_2']
          this.cboUPE.length ? this.params.p_UPE = this.cboUPE[0].DSLAM_ID : ""

          this.cboSWL2 = dsDanhMuc['items_3']
          // mặc định là chưa xác định
          this.cboSWL2.length ? this.params.p_SWL2 = -1 : ""
          // this.cboSWL2.length ? this.params.p_SWL2 = this.cboSWL2[0].DSLAM_ID : ""
        }
          // bangts.HT_DS_LOOKUPEDIT(cboPhanLoai, dsDanhMuc.Tables[0], "TEN_LOAI", "MA_LOAI", true);
          // bangts.HT_DS_LOOKUPEDIT(cboUPE, dsDanhMuc.Tables[1], "TENDSLAM", "DSLAM_ID", true);
          // bangts.HT_DS_LOOKUPEDIT(cboSWL2, dsDanhMuc.Tables[2], "TENDSLAM", "DSLAM_ID", true);
          // wbrChiTietThietBi.DocumentText = "<html><body style='font: 9pt Tahoma; margin: 5px'></body></html>";
      // }
      // catch (error){
      // }
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
        if(this.gridBTS.length > 0){
          setTimeout(()=>{
              this.gridBTS.forEach(item =>{
              if(item.KHUVUC_ID){
                item.KHUVUC = this.cboDVQL.filter(value =>{ return value.KHUVUC_ID == item.KHUVUC_ID})[0].TEN_KV
              }
              if(item.KHUVUC_XULY_ID){
                item.KHUVUC_XULY = this.cboDVXL.filter(value =>{ return value.KHUVUC_ID == item.KHUVUC_XULY_ID})[0].TEN_KV
              }
              if(item.NHANVIEN_TTDH_ID){
                item.NHANVIEN_TTDH = this.cboNV.filter(value =>{ return value.NHANVIEN_ID == item.NHANVIEN_TTDH_ID})[0].TEN_NV
              }
              if(item.DONVI_XLSC_ID){
                item.DONVI_XLSC = this.cboDVNXL.filter(value =>{ return value.DONVI_ID == item.DONVI_XLSC_ID})[0].TEN_DV
              }
              if(item.DONVI_QUANLY_ID){
                item.DONVI_QUANLY = this.cboDVQLTB.filter(value =>{ return value.DONVI_ID == item.DONVI_QUANLY_ID})[0].TEN_DV
              }
            })
          }, 1000)
        }
    },
    async btnLayTT_Click(){
      // try{
        this.gridBTS = []
        var p_upe_id = parseInt(this.params.p_UPE);
        var p_swl2_id = parseInt(this.params.p_SWL2);
        var p_phan_loai = this.params.p_PhanLoai
        var p_ma_node = this.params.p_Ma_Node.trim();
        var p_ten_thiet_bi = this.params.p_Ten_thietbi.trim();
        var p_diachi = this.params.p_Diachi.trim();
        var p_dvql_id = this.params.p_DVQL_ID
        var p_dvxl_id = this.params.p_DVXL_ID
        var p_nv_id = this.params.p_NV_ID
        var p_sdt_nv = this.params.p_SDT_NV
        var p_donvi_xlsc_id = this.params.p_DONVI_XLSC_ID
        var p_donvi_quanly_id = this.params.p_DONVI_QUANLY_ID
        var ds = await this.getDSBTSNodeb({
          "p_upe_id": p_upe_id,
          "p_swl2_id": p_swl2_id,
          "p_phan_loai": p_phan_loai,
          "p_ma_node": p_ma_node,
          "p_ten_thiet_bi": p_ten_thiet_bi,
          "p_diachi": p_diachi,
          "p_khuvuc_id" : p_dvql_id || null,
          "p_khuvuc_xuly_id" : p_dvxl_id || null,
          "p_nhanvien_ttdh_id" : p_nv_id || null,
          "p_so_dt_ttdh": p_sdt_nv,
          "p_donvi_xlsc_id" : p_donvi_xlsc_id || null,
          "p_donvi_quanly_id" : p_donvi_quanly_id || null
        })
        
        if (ds.length > 0){
          ds.forEach((item) => {
            this.gridBTS.push(new BtsNoded(item))
          });
          // this.gridBTS = ds
          setTimeout(()=>{
              this.gridBTS.forEach(item =>{
              if(item.KHUVUC_ID){
                item.KHUVUC = this.cboDVQL.filter(value =>{ return value.KHUVUC_ID == item.KHUVUC_ID})[0].TEN_KV
              }
              if(item.KHUVUC_XULY_ID){
                item.KHUVUC_XULY = this.cboDVXL.filter(value =>{ return value.KHUVUC_ID == item.KHUVUC_XULY_ID})[0].TEN_KV
              }
              if(item.NHANVIEN_TTDH_ID){
                item.NHANVIEN_TTDH = this.cboNV.filter(value =>{ return value.NHANVIEN_ID == item.NHANVIEN_TTDH_ID})[0].TEN_NV
              }
              if(item.DONVI_XLSC_ID){
                item.DONVI_XLSC = this.cboDVNXL.filter(value =>{ return value.DONVI_ID == item.DONVI_XLSC_ID})[0].TEN_DV
              }
              if(item.DONVI_QUANLY_ID){
                item.DONVI_QUANLY = this.cboDVQLTB.filter(value =>{ return value.DONVI_ID == item.DONVI_QUANLY_ID})[0].TEN_DV
              }
            })
          }, 1000)
         
          // console.log(this.gridBTS);
        }
  
        else{
          this.gridBTS = [];
        }
      // }
      // catch (error){
      //   this.$toast.error("Có lỗi: " + error.toString());
      // }
    },

    async grvBTS_DoubleClick(args){
        var vma_node = args.rowData["MA_NODE"]
        var vphan_loai = args.rowData["PHAN_LOAI"]
        await this.$refs.frmCapNhatBTS.Clear()
        this.$refs.frmCapNhatBTS.frmCapNhatBTS("U", vma_node, vphan_loai)
        this.$refs.popup_frmCapNhatBTS.show()
    },

    async tsbtnNhapMoi_Click(){
      this.$refs.frmCapNhatBTS.Clear()
      this.$refs.frmCapNhatBTS.frmCapNhatBTS("A","","")
      this.$refs.popup_frmCapNhatBTS.show()
    },

    async tsbtnSync_Click(){
      this.$toast.error("Chức năng đang trong quá trình thực hiện");
    },
    // update quy 25/3 
    async cboSWL2_EditValueChanged(){
      await this.btnLayTT_Click()
    },

     // update baolt 7/7/2023 
    async cboDVQL_EditValueChanged(){
      await this.btnLayTT_Click()
    },

    // update baolt 7/7/2023 
    async cboDVXL_EditValueChanged(){
      await this.btnLayTT_Click()
    },

    // update baolt 7/7/2023 
    async cboNV_EditValueChanged(){
      await this.btnLayTT_Click()
    },

    // update baolt 13/7/2023 
    async cboDVNXL_EditValueChanged(){
      await this.btnLayTT_Click()
    },

    // update baolt 13/7/2023 
    async cboDVQLTB_EditValueChanged(){
      await this.btnLayTT_Click()
    }


  },
  created: async function() {
    await this.frmBTS_Load()
  },
  watch: {
    
  },
  destroyed () {
  }
};
</script>
<style>
  
</style>