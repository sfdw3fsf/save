<template src="./ModalLoaiTBi.html"></template>
<script>
import {minLength,maxLength,numeric, required} from 'vuelidate/lib/validators'
export default {
  name: "ModalLoaiTBi",
  props: ["modalId"],
  computed: {},
  destroyed() {},
  methods: {
    focusMyElement: async function () {
      try{
      this.$root.showLoading(true)
      await this.laydsLoaiTbi();
      }catch(er){}
      finally{
         this.$root.showLoading(false)
      }
    },
   onclickLoaiTbi: async function(loaitbi_id) {
      this.$root.showLoading(true);
      try {
        await this.chitietLoaiTbi(loaitbi_id);
        this.currentId = loaitbi_id
        this.loaitbiId = this.dtloaitbi.LOAITBI_ID;
        this.loaitbi = this.dtloaitbi.LOAI_TBI;
        this.ghichu = this.dtloaitbi.GHICHU;
        this.selected = this.dtloaitbi.LOAITBI_ID;
        this.setDisableBtn(false, false, true, false);
      } catch (err) {
        console.log(err.response.data.message_detail);
      } finally {
        this.$root.showLoading(false);
      }
    },
    checkForm: function () {
      if (this.loaitbi == null || this.loaitbi == "") {
        this.$root.$toast.error("Trường loại thiết bị không được để trống");
        return false;}
      if (this.loaitbi.length>100) {
        this.$root.$toast.error("Trường loại thiết bị không được quá 100 kí tự");
        return false;}
      if (this.ghichu.trim()!="" && this.ghichu.length>300) {
        this.$root.$toast.error("Trường ghi chú không được quá 300 kí tự");
        return false;}
      return true;
      
    },
    clearForm: function() {
      this.setDisableBtn(true, false, false, true);
      this.loaitbiId = "";
      this.loaitbi = "";
      this.ghichu = "";
      this.$v.$reset()
    },
    reset:async function() {
      if(this.loaitbiId!=null && this.loaitbiId!=""){
       await this.onclickLoaiTbi(this.loaitbiId)
      }
        else
        this.clearForm();
    },
   SaveAs:async function() {
     
      if (!this.validateData()) {       
        if (this.loaitbiId == null || this.loaitbiId=="") 
        await this.themMoi()
         else 
        await this.capNhat()
        
      } else {
        this.$refs.loaitbiIn.focus();
        this.isError = true;
        
      }
    },
    themMoi:async function(){
     this.$root.showLoading(true);
          let objAdd = {
            loaiTbi: this.loaitbi,
            ghiChu: this.ghichu,
          };
          try {
            let rs = await this.$root.context.post(
              "/web-ecms/danhmuc/loaitbi/them",
              objAdd
            );
         
            if (rs.error_code == "BSS-00000000") {
           
              this.laydsLoaiTbi();
              this.setDisableBtn(false, false, true, false);
             // this.$refs.grid.firstPage()
              this.$refs.grid.setCurrentSelectedRow(0)
              this.$root.$toast.success("Thêm mới thành công!");
            } else {
              this.$root.$toast.error("Thêm mới thất bại!");
            }
          } catch (err) {
            console.log(err)
            this.$root.$toast.error("Thêm mới thất bại!");
           // this.$root.$toast.error(err.response.data.message_detail);
          } finally {
            this.$root.showLoading(false);
          }
    },
    capNhat:async function(){
          let objUpd = {
            loaiTbiId: this.loaitbiId,
            loaiTbi: this.loaitbi,
            ghiChu: this.ghichu,
          };
          try {
            var rs = await this.$root.context.post(
              "/web-ecms/danhmuc/loaitbi/capnhat",
              objUpd
            );
            if (rs.error_code == "BSS-00000000") {
              this.$root.$toast.success("Cập nhật thành công!");
              this.laydsLoaiTbi();
               this.setDisableBtn(false, false, true, false);
            }else {
              this.$root.$toast.error("Cập nhật thất bại!");
            }
          } catch (err) {
              this.$root.$toast.error("Cập nhật thất bại!");
           // this.$root.$toast.error(err.response.data.message_detail);
          } finally {
            this.$root.showLoading(false);
          }
    },
    xoaLoaiTbi: function() {
      if (!this.isDisableBtnXoa) {
        this.modalXoa = !this.modalXoa;
      }
    },
    xacNhanXoa: async function () {
      try {
        this.$root.showLoading(true);
        var rs = await this.$root.context.post(
          "web-ecms/danhmuc/loaitbi/xoa/" +this.loaitbiId
        );
        if (rs.error_code === "BSS-00000000") {
          this.setDisableBtn(false, true, true, true);
          this.$root.$toast.success("Xóa loại thiết bị thành công");
          this.$refs['modalXoa'].hide();
          await this.laydsLoaiTbi();
        } else {
          this.$root.$toast.error("Xóa loại thiết bị thất bại");
          this.laydsLoaiTbi(); 
        }
      } catch (err) {
         this.$root.$toast.error("Xóa loại thiết bị thất bại");
        // this.$root.$toast.error(err.response.data.message_detail);
      } finally {
        this.$root.showLoading(false);
      }
    },
    setDisableBtn: function (isNhapmoi, isGhilai, isHuy, isXoa) {
      this.isDisableBtnNhapMoi = isNhapmoi;
      this.isDisableBtnGhiLai = isGhilai;
      this.isDisableBtnHuy = isHuy;
      this.isDisableBtnXoa = isXoa;
    },
    loaitbi_selectedRowChanged:async function (dataItem) {
      if (dataItem != null) await this.onclickLoaiTbi(dataItem.LOAITBI_ID);
    },
    laydsLoaiTbi: async function(){
      try {
         this.$root.showLoading(true)
        var rs = await this.$root.context.get(
          "web-ecms/danhmuc/loaitbi/danhsach" 
        );
        this.dsLoaiThietBi = rs.data 
        if(this.dsLoaiThietBi!=null && this.dsLoaiThietBi.length>0)
            this.onclickLoaiTbi(this.dsLoaiThietBi[0].LOAITBI_ID)
             
      } catch (error) {
      } finally {
         this.$root.showLoading(false)
      }
    },
    chitietLoaiTbi: async function(id){
       try {
         this.$root.showLoading(true)
        var rs = await this.$root.context.get(
          "web-ecms/danhmuc/loaitbi/chitiet/"+ id 
        );
        this.dtloaitbi = rs.data       
      } catch (error) {
      } finally {
         this.$root.showLoading(false)
      }
    },
    validateData(data){
    this.$v.$touch();
    let errorMessage = []
    if (this.$v.$invalid) {
    if(!this.$v.loaitbi.required)
        errorMessage.push("Trường loại thiết bị bắt buộc có dữ liệu.")
    if(!this.$v.loaitbi.maxLength)
        errorMessage.push("Trường loại thiết bị không được quá 100 kí tự.")   
      if(!this.$v.ghichu.maxLength)
          errorMessage.push("Trường ghi chú không được quá 300 kí tự..")          
      if (errorMessage.length > 0) 
      this.$toast.error(errorMessage.join('\n'))
      return true   
      }
    return false
     }
  },

  async created() {
  
  },
  data() {
    return {
      modalXoa: false,
      dsLoaiThietBi:[],
      dtloaitbi:null,
      isDisableBtnNhapMoi: false,
      isDisableBtnGhiLai: true,
      isDisableBtnHuy: true,
      isDisableBtnXoa: true,
      loaitbiId: "",
      loaitbi: "",
      ghichu: "",
      selected: 0,
      isError: false,
      ok: true
    };
  },
  validations: {
        loaitbi:{required,
                maxLength:maxLength(100)},
        ghichu:{maxLength:maxLength(300)}
    },
};
</script>
<style>
div#popupTCTTTB___BV_modal_outer_ {
  z-index: 10 !important;
  margin-top: 100px;
}
.dashboard-header {
  z-index: 9 !important;
}
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>