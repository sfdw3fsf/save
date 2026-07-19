<template src="./index.html">

</template>

<script>

import moment from "moment";
import config from "./config";

export default {
  name: "frmCapNhatNoiDungTruyenThong",
  components: {    
  },
  data() {
    return {
      headerTitle: 'Cập nhật nội dung truyền thông',
      dsSuCo: [],
      wbrInformation: '',
      cboPhanLoai: {
        value: 0,
        data: []
      },
      cboTrangThai: {
        value: 0,
        data: []
      },
      cboNguonSuCo: {
        value: 0,
        data: []
      },
      cboNoidungTT: {
        value: 0,
        data: [
          {noidungtt_id:0,ten:'Tất cả'},
          {noidungtt_id:1,ten:'Có'},
          {noidungtt_id:2,ten:'Không'},
        ]
      },
      txtMaSuCo: '',
      dateFrom: '',
      dateTo: '',
      cols: config.cols,
      dsDanhMucChung: [],
      noidung:'',
      vanban:'',
      tieude:'', 
      vdb_id:0,    
      vphanloai:0,  
      label_suco:''
    }
  },
  computed: {    
    formAllowFileType() {
      return ".doc,.docx,.xls,.xlsx,.pdf,.jpg,.jpeg,.png";
    },
  },
  mounted () {
    this.initData()
  },
  methods: {
    handleShowModal() {
    },

    hideModal() {
      this.dsSuCo = [];
      this.cboPhanLoai.value = this.cboTrangThai.value = this.cboNguonSuCo.value = 0;
      this.txtMaSuCo = '';
      this.$refs.frmCapNhatNoiDungTruyenThong.hide()
    },

    async initData() {      

      this.dateFrom = '01/01/' + moment().format('YYYY');
      this.dateTo = moment().format('DD/MM/YYYY');
      await this.lay_ds_danhmuc_chung();
      await this.HT_THONGBAO_SUCO_2();
    },

    showModal() {
      this.$refs.frmCapNhatNoiDungTruyenThong.show();
    },
    dsSuCo_Click(item) {    
      console.log(item)  
      if (item && item.data){
        this.label_suco = item.data.noidung_sc
        this.vdb_id = item.data.suco_id
        this.vphanloai = item.data.phanloai
        this.noidung = item.data.noidung_tt
        this.vanban = item.data.path_file
        this.tieude = item.data.tieude_tt
      }      
    },
    async lay_ds_danhmuc_chung() {
      try {
        let rs = await this.axios.post('/web-quantri/danhmuc/lay_ds_danhmuc_chung', {
          "vma_nhom_loai": "TIEN_DO_XLY"
        });

        if (rs.data.data) {
          let {data} = rs.data;
          let {returnds, returnds_phanloai, returnds_nguonsc} = data;
          this.cboTrangThai.data = returnds;
          this.cboPhanLoai.data = returnds_phanloai;
          this.cboNguonSuCo.data = returnds_nguonsc;
          if (returnds) {
            this.cboTrangThai.value = 2;
          }

          if (returnds_phanloai) {
            this.cboPhanLoai.value = returnds_phanloai[0].ma_loai;
          }

          if (returnds_nguonsc) {
            this.cboNguonSuCo.value = returnds_nguonsc[0].nguonsc_id;
          }
        }
      } catch (e) {
        console.log(e);
      }
    },

    async HT_THONGBAO_SUCO_2() { 
      try {        
        this.dsSuCo = []
        let rs = await this.axios.post('web-thicong/baohong-suco/lay_ds_thongbao_suco_ttvt_popup_v2', { 
          "vtungay": this.dateFrom,
          "vdenngay": this.dateTo,
          "vtrangthai": this.cboTrangThai.value,
          "vphanloai": this.cboPhanLoai.value,
          "vnguonsc_id": this.cboNguonSuCo.value,
          "vma_sc": this.txtMaSuCo
        });
        if (rs.data && rs.data.data  && rs.data.data.length > 0){          
          if (this.cboNoidungTT.value == 0){
            this.dsSuCo = rs.data.data;
          } else if (this.cboNoidungTT.value == 1){
            this.dsSuCo = rs.data.data.filter(e => e.noidung_tt != "" && e.noidung_tt != null);
          } else if (this.cboNoidungTT.value == 2){
            this.dsSuCo = rs.data.data.filter(e => e.noidung_tt == "" || e.noidung_tt == null);
          } 
        }
      } catch (e) {
        console.log(e);
        this.dsSuCo = [];
      }
    },

    btnLayDL_Click() {
      this.HT_THONGBAO_SUCO_2();
    },
    async tsbtnUpdate_Click(){      
      console.log('tsbtnUpdate_Click')
      if (!this.tieude || this.tieude == "" || !this.noidung || this.noidung == ""){
        this.$toast.error('Vui lòng nhập đủ tiêu đề và nội dung!');
        return
      }      
      const data = { 
        "vtype": this.vphanloai, 
        "vdb_id": this.vdb_id, 
        "vtieude_tt": this.tieude, 
        "vnoidung_tt": this.noidung, 
        "vpath_upload": this.vanban 
        }

      let ds = await this.axios.post('/web-thicong/baohong-suco/fn_capnhat_thongtin_truyenthong', data);
      console.log('fn_capnhat_thongtin_truyenthong')
      console.log(ds)
      if(ds && ds.data && ds.data.data){        
        this.$toast.success('Cập nhật thông tin truyền thông thành công!');
        await this.HT_THONGBAO_SUCO_2();        
      }
      console.log('log 1')
      
    },
    async UploadFileHoSo() {
      this.loading(true);
       let formData = new FormData();
      const lastDot = this.$refs.files.files[0].name.lastIndexOf(".");
      const vfile_ext = this.$refs.files.files[0].name.substring(lastDot + 1);
      for (var file of this.$refs.files.files) {
        formData.append("files", file);
      }      
      await this.axios.post('/web-quantri/upload', formData, {
        headers: { "Content-Type": "multipart/form-data" },
      })
        .then((response) => {
          this.loading(false);
          if (response.data.error_code && response.data.error_code === 'BSS-00000000' ) {
            this.vanban = response.data.data[0]             
          }
        })
        
        ;
      this.loading(false);
    },
    btnExportExcel_Click() {
      if (this.dsSuCo.length <= 0) {
        this.$root.$toast.error('Chưa có dữ liệu trên lưới để xuất excel!');
        return;
      }

      //ẩn cột checkbox nếu allowCheckBox
      let cols = this.$refs.gridDanhSach.grid.getColumns();
      cols[0].visible = false;
      let excelExportProperties = {
        dataSource: this.dsSuCo,
        enableFilter: true,
        fileName: `Danh sách sự cố_${moment().format('YYYYMMDD')}.xlsx`
      };
      this.$refs.gridDanhSach.excelExport(excelExportProperties)
        //.then((e) => cols[0].visible = true);//hiện lại khi export xong
    },
    async Clear(){
      this.label_suco = ""
      this.vdb_id = 0
      this.vphanloai = 0
    },
  }
}
</script>

<style scoped>

</style>
