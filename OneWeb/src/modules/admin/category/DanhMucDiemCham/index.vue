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
      header: 'Danh mục điểm chạm',
      dsDiemCham: [],
      wbrInformation: '',
      cboLoaiDiemCham: {
        value: 0,
        data: []
      },
      cboDiemChamCha: {
        value: 0,
        data: []
      },      
      txtMaDiemCham: '',
      txtTenDiemCham: '',
      cols: config.cols,      
      ungdung_id: 0,
    }
  },
  computed: {        
  },
  mounted () {
    this.initData()
  },
  watch: {
    "cboLoaiDiemCham.value": async function (newVal, oldVal) {
      console.log('cboLoaiDiemCham')
      console.log(newVal)
      await this.lay_ds_diemcham_cha(newVal)      
    }
  },
  methods: {
    
    Clear() {      
      this.cboLoaiDiemCham.value = this.cboDiemChamCha.value = 0;
      this.txtMaDiemCham = '';
      this.txtTenDiemCham = '';  
      this.ungdung_id = 0;    
    },

    async initData() {      
      await this.lay_ds_danhmuc_chung();      
      await this.lay_ds_diemcham();      
    },
    dsDiemCham_Click(item) {    
      console.log(item)  
      if (item && item.data){
        this.txtMaDiemCham = item.data.ma_ungdung
        this.txtTenDiemCham = item.data.ten_ungdung        
        this.ungdung_id = item.data.ungdung_id
        this.cboLoaiDiemCham.value = item.data.loai
        this.cboDiemChamCha.value = item.data.ungdung_cha_id
      }      
    },
    async lay_ds_danhmuc_chung() {
      try {
        let rs1 = await this.axios.get('/web-khdn/donhang/lay_danhsach_loai_diemcham');
        if (rs1.data && rs1.data.data && rs1.data.data.length > 0) {
          console.log(rs1.data.data)
          this.cboLoaiDiemCham.data = rs1.data.data
          this.cboLoaiDiemCham.value = rs1.data.data[0].id
          
        }
      } catch (e) {
        console.log(e);
      }
    },
    async lay_ds_diemcham_cha(loai) {
      try {        
        let rs1 = await this.axios.get('/web-khdn/donhang/sp_lay_ds_diemcham_cha?p_loai='+loai);
        if (rs1.data && rs1.data.data) {
          this.cboDiemChamCha.data = rs1.data.data
          this.cboDiemChamCha.value = rs1.data.data[0].ungdung_id
          
        }
      } catch (e) {
        console.log(e);
      }
    },
    async lay_ds_diemcham() {
      try {
        this.dsDiemCham = []
        let rs = await this.axios.get('/web-khdn/donhang/lay_danhsach_diemcham');
        if (rs.data && rs.data.data) {
          this.dsDiemCham = rs.data.data          
        }
        
      } catch (e) {
        console.log(e);
      }
    },
    
    async tsbtnGhilai_Click(){      
      console.log('tsbtnGhilai_Click')
      if (!this.txtMaDiemCham || this.txtMaDiemCham == "" || !this.txtTenDiemCham || this.txtTenDiemCham == ""){
        this.$toast.error('Vui lòng nhập đủ mã và tên điểm chạm!');
        return
      }
      let input = { 
        p_kieu: (this.ungdung_id == 0?1:2), 
        p_ungdung_id: (this.ungdung_id == 0?0:this.ungdung_id),
        p_json_data: JSON.stringify([{
          "ten_ungdung":this.txtTenDiemCham,
          "ma_ungdung":this.txtMaDiemCham,
          "ungdung_cha_id":this.cboDiemChamCha.value,
          "loai":this.cboLoaiDiemCham.value
        }]) 
      }
      try {        
        let rs1 = await this.axios.post('/web-khdn/donhang/capnhat_danhsach_ungdung',input);
        if (rs1.data && rs1.data.data) {
          this.$toast.success('Cập nhật thành công!');
          await this.lay_ds_diemcham()
        }
        else {
          this.$toast.error('Cập nhật thất bại!');
        }
      } catch (e) {
        console.log(e);
        this.$toast.error('Cập nhật thất bại!');
      }
      
      
    },  
    async tsbtnXoa_Click(){      
      console.log('tsbtnXoa_Click')
      if (this.ungdung_id == 0){
        this.$toast.error('Vui lòng chọn điểm chạm để xóa!');
        return
      }
      if (!confirm(`Bạn có chắc chắn muốn xóa điểm chạm ${this.txtTenDiemCham} không?`)) return
      let input = { 
        p_kieu: 3, 
        p_ungdung_id: this.ungdung_id,
        p_json_data: JSON.stringify([{
          "ten_ungdung":this.txtTenDiemCham,
          "ma_ungdung":this.txtMaDiemCham,
          "ungdung_cha_id":this.cboDiemChamCha.value,
          "loai":this.cboLoaiDiemCham.value
        }]) 
      }
      try {        
        let rs1 = await this.axios.post('/web-khdn/donhang/capnhat_danhsach_ungdung',input);
        if (rs1.data && rs1.data.data) {
          this.$toast.success('Xóa thành công!');
          await this.lay_ds_diemcham()
        }
        else {
          this.$toast.error('Xóa thất bại!');
        }
      } catch (e) {
        console.log(e);
        this.$toast.error('Xóa thất bại!');
      }
      
      
      
    },   
  }
}
</script>

<style scoped>

</style>
