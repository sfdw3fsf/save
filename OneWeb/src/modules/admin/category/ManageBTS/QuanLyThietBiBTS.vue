<template src='./QuanLyThietBiBTS.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from 'moment';
import Vue from "vue";
import breadcrumb from '@/components/breadcrumb'
//import QuanLyThietBiBTSAPI from  './api/QuanLyThietBiBTSAPI'
import select2 from '@/components/Select2.vue'
//import { getterName,actionName,statePropertyName,mutationName} from './store/quanlythietbibts/index.js'
import ManageBTS  from './ManageBTS'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import XLSX from 'xlsx'
Vue.use(DialogPlugin)
import API from './api.js'
import { exportExcel } from "./export-excel";
export default {

  components: { ManageBTS, breadcrumb, appSelect2: select2 },
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
          p_Diachi: ""
        },
        cboPhanLoai: [],
        cboUPE: [],
        cboSWL2: [],
        gridBTS: [],
        wbrChiTietThietBi: "",
        row_Selected :"",
        _focusRowHandle: 0,
        phan_loai : "",
        ma_node : "",
        file:null,
        system_name:''

    };
  },
  methods: {
    //...mapActions("quanlythietbibts", actionName),
    // ...mapMutations("quanlythietbibts", mutationName),

    // chuyển Date thành type string
     f_DateToString: function (value,format='DD/MM/YYYY'){
      return moment(value).format(format)
    },
    f_StringToDate(value,format='DD/MM/YYYY'){
      return moment(value,format).toDate();
    },
    async btnLayTT_Click(){
      // try{
        var p_upe_id = parseInt(this.params.p_UPE);
        var p_swl2_id = parseInt(this.params.p_SWL2);
        var p_phan_loai = this.params.p_PhanLoai
        var p_ma_node = this.params.p_Ma_Node.trim();
        var p_ten_thiet_bi = this.params.p_Ten_thietbi.trim();
        var p_diachi = this.params.p_Diachi.trim();
        try{
          this.loading(true)
          var data = await this.axios.post("/web-ecms/bts/lay-ds-bts-nodeb",{
            "p_upe_id": p_upe_id,
            "p_swl2_id": p_swl2_id,
            "p_phan_loai": p_phan_loai,
            "p_ma_node": p_ma_node,
            "p_ten_thiet_bi": p_ten_thiet_bi,
            "p_diachi": p_diachi
          })
          this.loading(false)
          let ds = data.data.data
          if (ds.length > 0){
            this.gridBTS = ds
          }else{
            this.gridBTS = [];
          }
          console.log('ds', ds)
        }catch(e){
          this.loading(false)
          this.gridBTS = [];
        }
        
      // }
      // catch (error){
      //   this.$toast.error("Có lỗi: " + error.toString());
      // }
    },
    btnImportFile(){
      //sp_import_insert_bts
      this.$refs.fileInput.value=null
      document.getElementById("file").click()
    },
    onChangeFile(event){
        this.file=event.target.files[0]
        this.readFileExcel()
    },
    readFileExcel(){
      this.danhsach_excel=[]
      let columns
      try {
          this.loading(true)
          if (this.checkfile()) {
              const reader = new FileReader()
              reader.onload = (e) => {
                  /* Parse data */
                  const bstr = e.target.result
                  const wb = XLSX.read(bstr, { type: "binary" })
                  /* Get first worksheet */
                  const wsname = wb.SheetNames[0];
                  const ws = wb.Sheets[wsname];
                  /* Convert array of arrays */
                  const data = XLSX.utils.sheet_to_json(ws, { header: 1 });
                  columns=data[0]
                  //MA_NODE, KHUVUC_ID
                  if(this.kiemTraDuLieu(columns)){
                      for(var i=1;i<data.length;i++){
                          this.danhsach_excel.push({
                              SYSTEM_NAME:data[i][2]!=undefined?data[i][2]:null,
                              KHUVUC_ID:data[i][3]!=undefined?data[i][3]:null
                          })
                      }
                      if(this.danhsach_excel.length>0){
                          this.import_file(this.danhsach_excel)
                      }else{
                          this.$toast.error('Danh sách trống!')
                      }
                  }
                  
              }
              reader.readAsBinaryString(this.file)
          }
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi xảy ra khi đọc file')
      } finally {
          this.loading(false)
      }
    },
    async import_file(data){
      let rs=await this.sp_import_update_khuvuc_bts({
        js_bts:JSON.stringify(data)
      })
      if(rs.code==1){
        if(rs.data=='ok'){
          this.$toast.success('Import file thành công!')
        }else{
          let danhsach=JSON.parse(rs.data)
          this.$toast.error('Import file lỗi, thông tin danh sách lỗi dưới file excel!')
          let data = XLSX.utils.json_to_sheet(danhsach);
          let wb = XLSX.utils.book_new(); // make Workbook of Excel
          XLSX.utils.book_append_sheet(wb, data, 'Sheet1'); // sheetAName is name of Worksheet
          // export Excel file
          let fileFullName='danhsach_loi.xlsx'
          XLSX.writeFile(wb, fileFullName);
          
        }
      }else{
        this.$toast.error(rs.data)
      }
    },
    kiemTraDuLieu: function (columns) {
        if (columns == null) {
            this.$toast.error('File có cấu trúc không hợp lệ!')
            return false
        }
        if(columns.length<=3){
            this.$toast.error('File excel gồm 4 trường: PHANVUNG_ID, TBI_ID, SYSTEM_NAME, KHUVUC_ID!')
            return false
        }
        if(!columns[0]||columns[0].toString().toUpperCase() != "PHANVUNG_ID"){
            this.$toast.error('Tên cột đầu tiên trong file không đúng định dạng : PHANVUNG_ID')
            return false
        }
        if(!columns[1]||columns[1].toString().toUpperCase() != "TBI_ID"){
            this.$toast.error('Tên cột thứ 2 trong file không đúng định dạng : TBI_ID')
            return false
        }
        if(!columns[2]||columns[2].toString().toUpperCase() != "SYSTEM_NAME"){
            this.$toast.error('Tên cột thứ 3 trong file không đúng định dạng : SYSTEM_NAME')
            return false
        }
        if(!columns[3]||columns[3].toString().toUpperCase() != "KHUVUC_ID"){
            this.$toast.error('Tên cột thứ 4 trong file không đúng định dạng : KHUVUC_ID')
            return false
        }
        return true
    },
    checkfile: function () {
        if (this.file == null) {
            this.$toast.error('Chưa chọn file excel')
            return false
        }
        if (this.file.type != "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"){
            this.$toast.error('File upload không đúng định dạng')
            return false
        }
        return true
    },
    btnExportFile(){
      if(this.gridBTS.length == 0){
        this.$toast.error('Không có dữ liệu xuất Excel')
        return
      }

      const dataExport =  this.gridBTS.map(item =>{
       return {
          PHANVUNG_ID:item.PHANVUNG_ID,
          TBI_ID:item.TBI_ID,
          SYSTEM_NAME:item.SYSTEM_NAME,
          KHUVUC_ID:item.KHUVUC_ID,
          PHAN_LOAI:item.PHAN_LOAI,
          MA_NODE:item.MA_NODE,
          TEN_KHUVUC: item.TEN_KHUVUC,
          TEN_KHUVUC_XULY: item.TEN_KHUVUC_XULY,
          TEN_NV: item.TEN_NV,
          SDT_NV: item.SDT_NV,
          TRANGTHAI_HOATDONG: item.TRANGTHAI_HOATDONG,
          TEN_QL: item.TEN_QL,
          CAUHINH: item.CAUHINH,
          TEN_UPE: item.TEN_UPE,
          TEN_SWL2: item.TEN_SWL2,
          TEN_THIETBI_HETHONG: item.TEN_THIETBI_HETHONG,
          MA_NODE_CHA: item.MA_NODE_CHA,
          BANGTAN: item.BANGTAN,
          TEN_QUANHUYEN: item.TEN_QUANHUYEN,
          TEN_PHUONGXA: item.TEN_PHUONGXA
      }})
      exportExcel(dataExport, "quanly_thietbi_bts")
    },
    async grvBTS_FocusedRowChanged(row){
      // try{
        // var indx = row.rowIndex;
        var r_phan_loai = row.PHAN_LOAI.toString()
        var r_ma_node = row.MA_NODE.toString();
        this.phan_loai = r_phan_loai;
        this.ma_node = r_ma_node
        this.row_Selected = row;
        this.system_name=row.SYSTEM_NAME?row.SYSTEM_NAME:''
        var strThongTinThietBi = await this.axios.post('/web-ecms/bts/lay-tt-thietbi-bts',{
          "p_phan_loai": r_phan_loai,
          "p_ma_node": r_ma_node
        })

        // Xóa hiển thị cũ
        this.wbrChiTietThietBi = "";
        // Hiển thị dữ liệu mới
        this.wbrChiTietThietBi = strThongTinThietBi.data.data || '';
      // }
      // catch (error){
      //     this.$toast.error("Có lỗi: " + error.toString());
      // }
    },

    async frmBTS_Load(){
        try {
        let data = await this.axios.get("web-ecms/bts/lay-danhmuc-bts-nodeb")
        let dsDanhMuc = data.data.data
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
      } catch (err) {
        throw err;
      }
    },

    async grvBTS_DoubleClick(args){
        var vma_node = args.rowData["MA_NODE"]
        var vphan_loai = args.rowData["PHAN_LOAI"]
        var system_name= args.rowData["SYSTEM_NAME"]
        await this.$refs.frmCapNhatBTS.Clear()
        this.$refs.frmCapNhatBTS.frmCapNhatBTS("U", vma_node, vphan_loai, system_name)
        this.$refs.popup_frmCapNhatBTS.show()
    },

    async tsbtnNhapMoi_Click(){
      this.$refs.frmCapNhatBTS.Clear()
      this.$refs.frmCapNhatBTS.frmCapNhatBTS("A","","", "")
      this.$refs.popup_frmCapNhatBTS.show()
    },

    async tsbtnSua_Click(){
      this.$refs.frmCapNhatBTS.Clear()
      this.$refs.frmCapNhatBTS.frmCapNhatBTS("U", this.ma_node, this.phan_loai, this.system_name)
      this.$refs.popup_frmCapNhatBTS.show()
    },

    async btnDelete_click(){
        this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa dữ liệu không?', {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then(async (v) => {
          if (!v) {
            return
          }
          try {
            if(this.row_Selected.BTS_ID != null || this.row_Selected.KHUVUC_ID != null){
                let data = await this.axios.post("web-quantri/khai-bao-bts/sp_delete_bts",{
                vbts_id : this.row_Selected.BTS_ID,
                vkhuvuc_id : this.row_Selected.KHUVUC_ID,
                })
                if(data.data.error == '200')
                this.$root.$toast.success('Xóa thành công.')
                else
                this.$root.$toast.success(data.data.message)
            }
            else
              this.$root.$toast.error("Không thể xóa thiết bị thiếu dữ liệu 'BTS_ID' và 'KHUVUC_ID'")
        }
        catch(err){
            this.$root.$toast.console.error();('Xóa thất bại')
        }
            
        })
    },

    async tsbtnSync_Click(){
      this.$toast.error("Chức năng đang trong quá trình thực hiện");
    },
    // update quy 25/3 
    async cboSWL2_EditValueChanged(){
      await this.btnLayTT_Click()
    },
    rowDataBound(args){
      //console.log('rowDataBound',args )
      if(args.data['KHUVUC_ID']==null||Number(args.data['KHUVUC_ID'])==-1){
        args.row.classList.remove('e-altrow')
        args.row.classList.add('khuvuc-line-below0')
      }
    },
    async sp_import_update_khuvuc_bts(data){
      try{
        this.loading(true)
        let response = await API.sp_import_update_khuvuc_bts(this.axios, data)
        this.loading(false)
        if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
          return {
            code:1,
            data:response.data.data
          }
        }else{
            if(response && response.data && response.data.message){
              return {
                code:0,
                data:response.data.message
              }
            }else{
              return {
                code:0,
                data:'Đã xảy ra lỗi import không thành công!'
              }
            }   
        }
      }catch(error){
        this.loading(false)
        if(error.data&&error.data.message){
          return {
            code:0,
            data:error.data.message
          }
        }else{
          return {
            code:0,
            data:'Đã xảy ra lỗi import không thành công!'
          }
        }
      }
    },


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
.khuvuc-line-below0{
  background-color: #FFFF00 !important;
}
</style>