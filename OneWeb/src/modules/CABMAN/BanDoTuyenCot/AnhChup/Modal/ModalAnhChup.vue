<template src="./ModalAnhChup.html"></template>
<script>
export default {
  name: "ModalAnhChup",
  props: ["modalId", "cot_id","ten_cot"],
  created: async function () {
  },

  computed: {},
  methods: {
    focusMyElement: async function () {
      this.edit=false;
      this.stateBtn.isDisableBtnXemAnh =true;
      this.stateBtn.isDisableBtnSuaAnh =true;
      this.cot_id = this.cot_id;
      this.ten_cot= this.ten_cot;
      await this.getMotaCot()
      
    },
    hiddenForm: function(){
     Object.assign(this.$data, this.$options.data.call(this));
    },
    getMotaCot:async function(){
    try { 
        var rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/lay_mo_ta/" + this.cot_id
        );
        let src =''
        if(rs.data!=null){
        if(rs.data.MOTA.split(";").length>1){
        this.title_anh=rs.data.MOTA.split(";")[0];
        src=rs.data.MOTA.split(";")[1];
        }else
         src=rs.data.MOTA.split(";")[0];
        if(src!="")
         this.viewImage(src)
        }
      } catch (error) {
        console.log(error)
        this.$root.toastError("Hệ thống chưa được nâng cấp cho chức năng này!\r\nLiên hệ ERP để được hỗ trợ!");
      } finally {
      }
    },
   uploadFile: async function(){
    try{
       if(this.image!=null){
        var rs = await this.$root.context.post(
          "/web-cabman/storage/uploadFile",this.formData);   
        if(rs.data !=null && rs.data.status)  {
              this.path= rs.data.message.relativeUrl
              await this.saveImage()
        }else{
          this.$root.toastError('Có lỗi xảy ra trong quá trình upload file')
        }
       }else
        await this.saveImage()
    }catch(e){
      console.log(e)
      this.$root.toastError('Có lỗi xảy ra trong quá trình upload file')
    }
   },
   ghiLai:async function(){
     await this.uploadFile()    
   },
    viewImage: async function(src){
     try{ 
        var rs = await this.$root.context.post(
          "/web-cabman/storage/getPresignedUrl",{fileSource:src});  
        if(rs.data !=null)  {
             this.src= rs.data.result
        }else{
          this.$root.toastError('Có lỗi xảy ra trong quá trình lấy đường dẫn file')
        }
    }catch(e){
      console.log(e)
      this.$root.toastError('Có lỗi xảy ra trong quá trình lấy đường dẫn file')
    }
    },
   saveImage :async function(){
    try {
       this.$root.showLoading(true);
       let mota=this.title_anh+";"+this.path
        var rs = await this.$root.context.post(
          "/web-cabman/bando_tuyencot/capnhat_mo_ta",{
            "cot_id":this.cot_id,
             "mota":mota
           });      
        if(rs.data !=null)  
         this.$toast.success("Cập nhật thành công!");
         else
         this.$toast.error("Cập nhật thất bại!");
      } catch (error) {
        this.$toast.error("Lỗi khi upload file");
      } finally {
       this.$root.showLoading(false);
      }
    },
    chonImage: function(){
      if(this.edit)
      document.getElementById("my-file").click();
    },
    addImage:function(){
      this.title_anh=""
      this.src=require("../../../../../assets/uploads/avatar-default.png")
      this.edit=true
      this.hasImage=true
    },
    previewImage:function(event){
      var input = event.target;
      if (input.files) {
        var reader = new FileReader();
        reader.onload = (e) => {
          this.src = e.target.result;
          this.hasImage=false
        }
        this.image=input.files[0];
         this.formData = new FormData()
         this.formData.append("file",input.files[0])
        reader.readAsDataURL(input.files[0]);
      }
    },
    xoaImage:function(){
    this.image= null
    this.src=require("../../../../../assets/uploads/avatar-default.png")
    },
    suaImage:function(){
      this.edit=true
      this.hasImage=true
    },
    xemImage:function(){
      this.stateBtn.isDisableBtnSuaAnh =false;
    },
    preview:function(){
      this.stateBtn.isDisableBtnXemAnh =false;
    }
  },
  data: function () {
    return {    
      src:null,
      file:null,
       stateBtn:{
        isDisableBtnSuaAnh:true,
        isDisableBtnXemAnh:true
      },
      title_anh:"",
      edit:false,
      image:null,
      hasImage:false,
      path:"",
      formData: null
    };
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
a.disabled {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>