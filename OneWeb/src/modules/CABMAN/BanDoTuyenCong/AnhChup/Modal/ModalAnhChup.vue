<template src="./ModalAnhChup.html"></template>
<script>
    export default {
        name: "ModalAnhChup",
        props: ["modalId", "data"],
        created: async function () {
        },

        computed: {},
        methods: {
            focusMyElement: async function () {
                this.edit=false
                this.stateBtn.isDisableBtnXemAnh =true
                this.stateBtn.isDisableBtnSuaAnh =true
                this.beCapId = this.data.beCapId
                this.tenBeCap= this.data.tenBeCap
                this.getMota();
            },
            getMota:async function(){
                try {
                    let rs = await this.$root.context.get(
                        "/web-cabman/BanDoTuyenCong/lay-thong-tin-anh-be-cap?beCapId=" + this.beCapId
                    );
                    let src =''
                    console.log('mota',rs.data.MOTA)
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
                    //this.$toast.info("Hệ thống chưa được nâng cấp cho chức năng này!\r\nLiên hệ ERP để được hỗ trợ!");
                } finally {
                }
            },
            uploadFile: async function(){
                try{
                    let rs = await this.$root.context.post(
                        "/web-cabman/storage/uploadFile",this.formData);
                    if(rs.data !=null && rs.data.status)  {
                        this.path= rs.data.message.relativeUrl
                        await this.saveImage()
                    }else{
                        this.$root.toastError('Có lỗi xảy ra trong quá trình upload file')
                    }
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
                    let input = {
                        fileSource: src
                    }
                    let rs = await this.$root.context.post(
                        "/web-cabman/storage/getPresignedUrl", input)
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
                    let mota = this.title_anh+";"+this.path
                    let rs = await this.$root.context.post(
                        "/web-cabman/BanDoTuyenCong/cap-nhat-anh-be-cap",{
                            "beCapId":this.beCapId,
                            "moTa":mota
                        });
                    if(rs.data !=null)
                        this.$toast.success("Lưu ảnh thành công");
                    else
                        this.$toast.error("Lưu ảnh thất bại");
                } catch (error) {
                    this.$toast.error("Lưu ảnh thất bại");
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
                let input = event.target;
                if (input.files) {
                    let reader = new FileReader();
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
                formData: null,
                beCapId:null,
                tenBeCap:null

            }
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
  }
</style>
