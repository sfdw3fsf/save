<template src='./DeNghiCapMaSanPham.html'></template>

<script>
import Vue from 'vue'
import api from './API'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import ThemMoiSanPham from './popups/ThemMoiSanPham/ThemMoiSanPham'
import LichSuSanPham from './popups/LichSuSanPham/LichSuSanPham'
import TuChoiDuyetSanPham from './popups/TuChoiDuyet/TuChoiDuyetSanPham'
import ImportFile from './popups/ImportFile/ImportFile'
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
Vue.use(DialogPlugin);
export default {
  components:{
    ThemMoiSanPham,LichSuSanPham,TuChoiDuyetSanPham,breadcrumb,ImportFile
  },
  data: function () {
    return {
      header: {
        title: 'Đề nghị cấp mã sản phẩm',
        list: [
          { name: 'Trang chủ', link: { name: 'Ui.cards' } },
          { name: 'Sản phẩm dịch vụ', link: { name: 'Ui.cards' } },
          { name: 'Đề nghị cấp mã sản phẩm', link: { name: 'Ui.cards' } },
        ]
      },
      position: {X: 'center', Y: 'top'},
      cboTrangThai:{
        list:[],
        SelectedValue:null
      },
      cboNHOM_SPDV:{
        list:[],
        SelectedValue:null
      },
      cboPHUONGTHUC_PT:{
        list:[],
        SelectedValue:null
      },
      cboDONVI:{
        list:[],
        SelectedValue:null
      },
      selectedItem: null,
      dataSourcesHD:{
        list:[

        ],
        cols: [
          {
            fieldName: "dv_chuquan",
            headerText: "Đơn vị chủ quản",
            width:150,
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ma_spdv",
            headerText: "Mã định danh",
            width:150,
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ten_spdv",
            headerText: "Tên nội bộ",
            allowFilter: true,
            allowSorting:false
          },
          {
            fieldName: "ten_viettat",
            headerText: "Tên viết tắt",
            allowFilter: true,
            allowSorting:false
          },
          {
            fieldName: "ngay_gtm",
            headerText: "Ngày GTM",
            allowFilter: true,
            allowSorting:false
          },
          {
            fieldName: "trangthai_spdv",
            headerText: "Trạng thái",
            allowFilter: true,
            allowSorting:false
          }
        ]
      },
      txtMaDinhDang:"",
      txtTenSp:"",

    }
  },
  methods:{
    async init_form(){
      let ds_cbo = await this.lay_thongtin_cbo();

      this.cboTrangThai.list.push({trangthai_id:0,trangthai_spdv:"--Tất cả--"})
      ds_cbo.TRANGTHAI.forEach((item)=>{
        this.cboTrangThai.list.push(item);
      })
      this.cboTrangThai.SelectedValue = 0
      this.cboNHOM_SPDV.list.push({nhomspdv_id:0,ten_nhomspdv:"--Tất cả--"});
      ds_cbo.NHOM_SPDV.forEach((item)=>{
        this.cboNHOM_SPDV.list.push({nhomspdv_id:item.nhomspdv_id,ten_nhomspdv:item.ma_nhomspdv + " - " + item.ten_nhomspdv})
      })
      this.cboNHOM_SPDV.SelectedValue = 0
      this.cboPHUONGTHUC_PT.list.push({phuongthuc_id:0,ten_phuongthuc:"--Tất cả--"});
      ds_cbo.PHUONGTHUC_PT.forEach((item)=>{
        this.cboPHUONGTHUC_PT.list.push({phuongthuc_id:item.phuongthuc_id,ten_phuongthuc: item.ten_phuongthuc})
      })
      this.cboPHUONGTHUC_PT.SelectedValue = 0
      this.cboDONVI.list.push({donvi_id:0,ten_dv:"--Tất cả--"});
      ds_cbo.DONVI.forEach((item)=>{
        this.cboDONVI.list.push({donvi_id:item.donvi_id,ten_dv: item.ma_dv + " - " + item.ten_dv})
      })
      this.cboDONVI.SelectedValue = 0
      await this.HienThiDanhSach();
    },
    async lay_thongtin_cbo() {
      return this.GetDataList(await api.sp_lay_danhmuc_chung(this.axios))
    },
    async HienThiDanhSach(){
      let tt =
        {
          tag: this.tag,
          nhomspdv_id: this.cboNHOM_SPDV.SelectedValue,
          phuongthucpt_id: this.cboPHUONGTHUC_PT.SelectedValue,
          donvi_id: this.cboDONVI.SelectedValue,
          trangthai_id: this.cboTrangThai.SelectedValue,
          ma_spdv: this.txtMaDinhDang,
          ten_spdv: this.txtTenSp
        };
      try{
        this.loading(true)
        let response = await api.sp_ds_sanpham(this.axios, tt)
        if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          this.dataSourcesHD.list=response.data.data
        }else{
          this.dataSourcesHD.list=[]
        }
      }catch(e){
        this.$alert('Đã xảy ra lỗi', '', {
          dangerouslyUseHTMLString: true,
          confirmButtonText: 'OK',
          type: 'error'
        })
      }finally{
        this.loading(false)
      }
    },
    async selectedRowChanged(args){
      console.log(args);
      this.selectedItem = args
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
    tsbtnThemMoi_Click(){
      this.selectedItem = null;
      this.$refs.popupThemMoiSanPham.$children[0].clearData();
      this.$refs.popupThemMoiSanPham.$children[0].loadData();
      this.$refs.popupThemMoiSanPham.$children[0].openDialog(this.selectedItem);
      this.$refs.popupThemMoiSanPham.show();
    },
    tsbtnCapNhat_Click(){
      if(this.selectedItem != null){
        this.$refs.popupThemMoiSanPham.$children[0].clearData();
        this.$refs.popupThemMoiSanPham.$children[0].loadData();
        this.$refs.popupThemMoiSanPham.$children[0].openDialog(this.selectedItem);
        this.$refs.popupThemMoiSanPham.show();
      }
      else{
        this.$toast.error('Vui lòng chọn sản phẩm')
      }
    },
    async tsbtnXoa_Click() {
      if (this.selectedItem == null) {
        this.$toast.error('Vui lòng chọn sản phẩm')
        return
      }

      if (!confirm('Bạn có chắc chắn muốn xóa sản phẩm không?')) return

      let response = await api.fn_xoa_sanpham(this.axios, { spdv_id: this.selectedItem.spdv_id })
      if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
        this.$toast.success('Xóa sản phẩm thành công')
        await this.HienThiDanhSach()
      } else {
        this.$toast.error(response.data.message)
      }
    },
    tsbtnTimKiem(){
      this.HienThiDanhSach();
    },
    tsbtnLichSu_Click(){
      this.$refs.popupLichSuSanPham.$children[0].load_data(this.selectedItem.spdv_id);
      this.$refs.popupLichSuSanPham.show();
    },
    tsbtnImportFile_Click(){
      this.$refs.popupImportFile.show();
    },
    async tsbtnGuiPheDuyet_Click(){
      if(this.selectedItem.spdv_id == null){
        this.$toast.error("Vui lòng chọn sản phẩm");
      }
      else{
        let response = await api.fn_gui_duyet_sanpham_cty(this.axios, { spdv_id: this.selectedItem.spdv_id })
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          this.$toast.success('Gửi duyệt sản phẩm TCT thành công')
          await this.HienThiDanhSach()
        } else {
          this.$toast.error(response.data.message)
        }
      }
    },
    async tsbtnPheDuyet_Click(){
      if(this.selectedItem.spdv_id == null){
        this.$toast.error("Vui lòng chọn sản phẩm");
      }
      else{
        let response = await api.fn_duyet_sanpham_cty(this.axios, { spdv_id: this.selectedItem.spdv_id })
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          this.$toast.success('Phê duyệt sản phẩm TCT thành công')
          await this.HienThiDanhSach()
        } else {
          this.$toast.error(response.data.message)
        }
      }
    },
    async tsbtnKhongDuyet_Click(){
      if(this.selectedItem.spdv_id == null){
        this.$toast.error("Vui lòng chọn sản phẩm");
      }
      else{
        this.$refs.popupTuChoiDuyet.$children[0].init_data(this.tag, this.selectedItem.spdv_id);
        this.$refs.popupTuChoiDuyet.show();
      }
    },
    async tsbtnGuiTD_Click(){
      if(this.selectedItem.spdv_id == null){
        this.$toast.error("Vui lòng chọn sản phẩm");
      }
      else{
        let response = await api.fn_gui_duyet_sanpham_td(this.axios, { spdv_id: this.selectedItem.spdv_id })
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          this.$toast.success('Gửi duyệt sản phẩm TĐ thành công')
          await this.HienThiDanhSach()
        } else {
          this.$toast.error(response.data.message)
        }
      }
    },
    async tsbtnDuyetTD_Click(){
      if(this.selectedItem.spdv_id == null){
        this.$toast.error("Vui lòng chọn sản phẩm");
      }
      else{
        let response = await api.fn_duyet_sanpham_td(this.axios, { spdv_id: this.selectedItem.spdv_id })
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          this.$toast.success('Phê duyệt sản phẩm TĐ thành công')
          await this.HienThiDanhSach()
        } else {
          this.$toast.error(response.data.message)
        }
      }
    },
    tsbtnKhongDuyetTD_Click(){
      if(this.selectedItem.spdv_id == null){
        this.$toast.error("Vui lòng chọn sản phẩm");
      }
      else{
        this.$refs.popupTuChoiDuyet.$children[0].init_data(this.tag, this.selectedItem.spdv_id);
        this.$refs.popupTuChoiDuyet.show();
      }
    },
    exportExcel() {
      try{
        if (this.dataSourcesHD.list.length <= 0) {
          this.$toast.error('Không có dữ liệu để xuất Excel!');
          return;
        }
        console.log("test");
        let cols = this.$refs.dataSourcesHD.grid.getColumns();
        let excelExportProperties = {
          dataSource: this.dataSourcesHD.list,
          enableFilter: true,
          fileName: `DanhSachSP_${moment().format('DDMMYYYY')}.xlsx`
        };
        this.$refs.dataSourcesHD.excelExport(excelExportProperties)
        this.$toast.success("Export thành công");
      }
      catch (e) {
        console.log(e);
        this.$toast.error("Export thất bại");
      }

    },
    acceptThemMoiSanPham() {
      this.$refs.popupThemMoiSanPham.hide()
      this.HienThiDanhSach();
    },
    acceptTuChoiDuyetSanPham() {
      this.$refs.popupTuChoiDuyet.hide()
      this.HienThiDanhSach();
    },
    acceptImportFile(){
      this.$refs.popupImportFile.hide()
      this.HienThiDanhSach();
    },
  },
  mounted(){
    if(this.tag == 2)
      this.header.title = "TCT PHÊ DUYỆT CẤP MÃ SẢN PHẨM"
    if(this.tag == 3)
      this.header.title = "TĐ PHÊ DUYỆT CẤP MÃ SẢN PHẨM"
    if(this.tag == 4)
      this.header.title = "TRA CỨU SẢN PHẨM"
    this.init_form();
  },
  created () {
    if (this.$route.params.tag != null && this.$route.params.tag != '' && this.$route.params.tag.length > 0) {
      this.tag = this.$route.params.tag;
    }
    else if(this.$route.query.tag != null && this.$route.query.tag != '' && this.$route.query.tag.length > 0){
      this.tag = this.$route.query.tag;
    }
    console.log("Tag: " + this.tag);
  }
}

</script>

