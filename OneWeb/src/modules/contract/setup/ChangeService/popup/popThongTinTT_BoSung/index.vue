<template src="./index.html">
</template>
<script>
import moment from "moment";
export default {
  name: "frmThongTinTT",
  components: {        
  },
  data() {
    return {
      headerTitle: 'Thông tin thanh toán bổ sung',      
      wbrInformation: '',
      cboLoaiTien: {
        value: 0,
        data: []
      },
      cboSoNgay: {
        value: 0,
        data: [
          {id : 1, text: '30 ngày'},
          {id : 2, text: 'Số ngày trong tháng'}
        ]
      },      
      txtSoTien: 0,
      txtTiGia: 0,
      txtTongTien: 0,
      txtMucThue: 0,      
      hdkh_id:0,
      hdtb_id:0,
      chophep_sua:1,
    }
  }, 
  watch: {
    // thay đổi dịch vụ => thay đổi cả loại hình tb
    async txtTiGia(val) {      
       this.txtTongTien = val  * this.txtSoTien    
    },
    async txtSoTien(val) {      
       this.txtTongTien = val  * this.txtTiGia    
    }
  },
  methods: {
    handleShowModal() {
    },
    hideModal() {      
      this.cboLoaiTien.value = 0;
      this.txtSoTien = 0;
      this.txtTiGia = 0;
      this.txtTongTien = 0;
      this.txtMucThue = 0;
      this.$refs.frmThongTinTT.hide()
    },
    async initData() {         
      await this.lay_dulieu();   
    },
    showModal() {
      this.$refs.frmThongTinTT.show();      
    },
    
    
    async lay_dulieu() {
      try {
        console.log('lay_dulieu')
        console.log(this.hdkh_id)        
        if (this.hdkh_id == 0)
        {
          this.$toast.error(`Chưa tạo hợp đồng!`,);          
          return;
        }
        let rs = await this.axios.get('/web-quantri/danhmuc-chung/CSS_LOAITIEN');
        if (rs.data.data) {          
          this.cboLoaiTien.data = rs.data.data;
          this.cboLoaiTien.value = rs.data.data[0].LOAITIEN_ID                       
        }
        //if (this.chophep_sua == 0){
          //load thông tin  
          let rs1 = await this.axios.post('/web-thicong/capnhatthongtin/sp_lay_tt_thanhtoan_bs',
          {p_hdkh_id:this.hdkh_id, p_hdtb_id:this.hdtb_id}
          );
          if (rs1.data.data && rs1.data.data.length > 0) {          
              this.txtTiGia = (rs1.data.data[0].value!=null?rs1.data.data[0].value:0)  
              this.txtSoTien = (rs1.data.data[0].mucuoc_tb!=null?rs1.data.data[0].mucuoc_tb:0)  
              this.txtMucThue = (rs1.data.data[0].mucthue!=null?rs1.data.data[0].mucthue:0)  
              this.cboSoNgay.value = (rs1.data.data[0].songay_kc!=null?rs1.data.data[0].songay_kc:0)  
              this.cboLoaiTien.value = (rs1.data.data[0].loaitien_id!=null?rs1.data.data[0].loaitien_id:0) 
          }            
        //}
      } catch (e) {
        console.log(e);
      }
    },
    async btnGhilai_Click() {
      let rs1 = await this.axios.post('/web-thicong/capnhatthongtin/fn_update_thongtin_thanhtoan_bs',
          {
            p_hdkh_id:this.hdkh_id, 
            p_hdtb_id:this.hdtb_id,
            p_loaitien_id:this.cboLoaiTien.value, 
            p_sotien:this.txtSoTien, 
            p_tigia:this.txtTiGia, 
            p_mucthue:this.txtMucThue, 
            p_songay_kc:this.cboSoNgay.value, 
          }
          );
      if (rs1.data.error == '200'&&rs1.data.error_code == 'BSS-00000000') {          
        this.$root.toastSuccess("Cập nhật thành công!");
      }
      else {
        this.$toast.error(`Có lỗi khi cập nhật!`,);     
      }   
    },
  }
}
</script>
<style scoped>
</style>