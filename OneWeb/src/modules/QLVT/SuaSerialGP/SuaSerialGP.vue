<template src='./SuaSerialGP.html'></template>
<style scoped src='./SuaSerialGP.scss'></style>
<script>

import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from '@/components/breadcrumb'
import { getterName,actionName,statePropertyName,mutationName} from '../store/suaserialgp'
import select2 from '@/components/Select2.vue'
import TraCuuChungTu from "../TraCuuChungTu/TraCuuChungTu.vue";
import Vue from "vue";

import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'

Vue.use(DatePickerPlugin)

export default {

  components: {breadcrumb, appSelect2: select2,TraCuuChungTu },
  name: "SuaSerialGP",
  mounted() {
  },
  
  data() {
    return {
      
      DSLichSu : [],
      txtCu : "",
      txtMoi: "",
      linkImg: "",
      srcImg : "",
      txtChungTu : "",
      vchungtu_id: 0,
      isRender: false,
      dataImg: null,
      image: null,
      base64: null,
      Enabled:{
        tsbtnThemmoi : true,
        tsbtnCapNhat : true,
        tsbtnHuy: true
      },
      isShowToolsImg : false,
      cssImg:{
        widthImg:50,
        widthMainImg: 100,
        scale:1,
        rotate : 0,
      },
      boxImgMain : "col-sm-12 col-12",
      boxImgPreview: "",
      isHiddenPreview: false,
      listWidth: [25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400],
      animationSettings: { effect: 'Zoom' },
    };
  },

  computed: {
    ...mapState("suaserialgp", statePropertyName),
    ...mapGetters("suaserialgp", getterName),

    
  },
  methods: {
    ...mapActions("suaserialgp", actionName),
    ...mapMutations("suaserialgp", mutationName),

    async frmSua_Serial_GP_Load(){
      await this.load_ds()
    },
    async load_ds(){
      this.DSLichSu = await this.getDSLichSuSuaSerialGP()
      if(this.DSLichSu.length == 0){
        this.SetButton(1);
      }
      console.log(this.DSLichSu , " = this.DSLichSu")
    },
    SetButton(kieu){
      this.Enabled.tsbtnThemmoi = false
      this.Enabled.tsbtnCapNhat = false
      this.Enabled.tsbtnHuy = false
      if(kieu == -1){ // bắt đầu

        this.Enabled.tsbtnCapNhat = true
        this.Enabled.tsbtnHuy = true

      }else if(kieu == 0){ // bắt đầu

        this.Enabled.tsbtnThemmoi = true
        this.Clear()

      }else if(kieu == 1){ // thêm mới

        this.Enabled.tsbtnCapNhat = true
        this.Enabled.tsbtnHuy = true
        this.Clear()

      }else if(kieu == 2){ // Hủy

        this.Enabled.tsbtnThemmoi = true
        this.Clear()

      }else if(kieu == 3){ // Edit

        this.Enabled.tsbtnThemmoi = true
        this.Enabled.tsbtnCapNhat = true
        this.Enabled.tsbtnHuy = true
        
      }
    },
    Clear(){
      this.txtCu = ""
      this.txtMoi = ""
      this.linkImg = ""
      this.srcImg = ""
      this.txtChungTu = ""
      this.vchungtu_id = 0
    },
    async gridLichSuSelectRow(row){     
      try{
        this.loading(true)
        if(row){
          this.linkImg = ""        
          this.srcImg = "data:image/png;base64," + row.BANGCHUNG; // Save ảnh trực tiếp trên DB
          
          // Save ảnh trên Minio
          // if(row.BANGCHUNG != null && row.BANGCHUNG != ""){
          //   this.linkImg = row.BANGCHUNG
          //   var checkFileExist = await this.checkFileExist({fileSource : this.linkImg})
          //   if(checkFileExist.data){
          //     var fullLink = await this.getLinkFile({fileSource : this.linkImg})
          //     this.srcImg = fullLink.name
          //   } 
          // }
          this.SetButton(3);
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
      
    },
    async tsbtnLayTT_Click(){
      try{
        await this.load_ds()
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }      
    },
    tsbtnThemmoi_Click(){
      this.SetButton(1);
    },
    tsbtnHuy_Click(){
      this.SetButton(0);
    },
    async tsbtnSave_Click(){     
      var mess_err = ""

      if (this.Enabled.tsbtnThemmoi)
      {
        mess_err += "Bạn không ở chế độ thêm mới. Không thể cập nhật!\n" 
      }
      if (this.txtChungTu.trim() == "")
      {
        mess_err += "Chưa chọn chứng từ!\n" 
      }
      if (this.txtCu.trim() == "")
      {
        mess_err += "Serial GP cũ không được để trống ! \n" 
      }
      if (this.txtMoi.trim() == "")
      {
        mess_err += "Serial GP mới không được để trống ! \n" 
      }
      // if(this.linkImg == ""){
      //   mess_err += "Bạn chưa upload ảnh serial GP thiết bị !" 
      // }
      if(this.srcImg == ""){
        mess_err += "Bạn chưa upload ảnh serial GP thiết bị !" 
      }
      if(mess_err != ""){
        this.$toast.error(mess_err)
        return
      }
      var isBoxConfirm = false;
      await this.$bvModal.msgBoxConfirm(
          "Bạn có muốn cập nhật Serial GP [" + this.txtCu + "] thành [" + this.txtMoi + "] không ?",
          {
            title: "Xác nhận hành động",
            centered: true,
            size: "md",
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy',
          }
        )
        .then((value) => {
          if (value) {
            isBoxConfirm = true;
          } else {
            return;
          }
        });

      if (!isBoxConfirm) {
        return
      }
      try{
        this.loading(true)
        var strImage = this.srcImg.replace(/^data:image\/[a-z]+;base64,/, '')
        var ketQua = await this.capNhapSerialGP({
          serialCu: this.txtCu,
          serialMoi: this.txtMoi,
          chungTuId: this.vchungtu_id,
          urlBangChung: strImage
        })

        if(parseInt(ketQua).toString() == "NaN"){ // check xem kiểu trả về là LogID hay mess lỗi
          this.$toast.error(ketQua)
        }else{
          this.$toast.success("Cập nhật thành công !")
          await this.load_ds()
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }      
    },
    async btnTraCuuCT_Click(){
      if (this.Enabled.tsbtnThemmoi){
        this.$toast.error("Bạn không ở chế độ thêm mới. Không thể cập nhật!");
        return
      }
      // this.isRender = true
      var f = this.$refs.frmTraCuuChungTu
      f.parentForm = "SuaSerialGP"
      this.$refs.popupTraCuuChungTu.show()
    },
    async XacNhan_TraCuuChungTu(){
      this.$refs.popupTraCuuChungTu.hide()
      var f = this.$refs.frmTraCuuChungTu
      if(f.xacnhan){
        this.vchungtu_id = f.chungtu_id
        this.txtChungTu = f.sophieu
      }
    },
    chooseFile() {
      this.$refs['ref-choose-file'].$el.querySelector('input').click()
    },
    getFileContents (file) {
      return new Promise((resolve, reject) => {
        let contents = ''
        const reader = new FileReader()
        reader.onloadend = function (e) {
          contents = e.target.result
          resolve(contents)
        }
        reader.onerror = function (e) {
          reject(e)
        }
        reader.readAsDataURL(file)
      })
    },
    async onChangeFile(e) {
      try{
        this.loading(true)
        const file = e.target.files[0];   
        console.log(file , " = file")         
        const contents = await this.getFileContents(file)      
        this.srcImg = contents 

        // Rem Lưu file từ minio
        // let fileChooser = this.$refs['ref-choose-file']
        // let choosedFiles = fileChooser.$el.querySelector('input').files  
     
        // if (choosedFiles.length == 0) {
        //     fileChooser.reset()
        //     return
        // }
        // choosedFiles = [...choosedFiles]
        // fileChooser.reset()

       
        // let formData = new FormData()
        // for (let i = 0; i < choosedFiles.length; i++) {
        //     let item = choosedFiles[i]          
        //     formData.append("file", item)
        // }
        // this.dataImg = formData
        // console.log(formData , " = formData")
        
        // let paths = await this.uploadFile(formData)
        // console.log(paths , " = paths")
        // if (paths.length == 0) {
        //   this.$toast.error("Upload file thất bại!");
        //   this.loading(false)
        //   return
        // }

        // for (let i = 0; i < paths.length; i++) {
        //   if(paths[i].status){
        //     this.linkImg = paths[i].message.relativeUrl
        //     this.srcImg = paths[i].message.presignedUrl
        //   }
        // }
        // console.log(this.linkImg , " = this.linkImg")
        // this.$root.toastSuccess(`Upload ảnh thành công!`)
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    scalePlus(){
      this.cssImg.widthMainImg += 25
    },
    scaleNone(){
      this.cssImg.widthMainImg = 100
    },
    scaleMinus(){    
      if(this.cssImg.widthMainImg >= 50){
        this.cssImg.widthMainImg -= 25       
      }  
    },
    rotateLeft(){
      this.cssImg.rotate -= 90;
    },
    rotateRight(){
      this.cssImg.rotate += 90;
    },
    hiddenPreview(){
      this.isHiddenPreview = !this.isHiddenPreview
    },
    async downloadImg() {
      var checkFile = await this.checkFileExist({fileSource : this.linkImg})
      if(!checkFile.data){
        this.$toast.error("File không còn tồn tại!");
        return
      }
      await this.onClickDownFile(this.linkImg)
    },
    async onClickDownFile(value) {
      if(value) {
        const data = await this.downloadFile(value);
        console.log(data , " = data")
        if(data) {
          const filePath= value.split('/');
          const linkSource = `data:application/octet-stream;base64,${data.body.data}`;
          const downloadLink = document.createElement("a");
          const fileName = filePath[filePath.length - 1];
          downloadLink.href = linkSource;
          downloadLink.download = fileName;    
          downloadLink.click();
        }
      }
    },
    showTools(){
      this.isShowToolsImg = !this.isShowToolsImg
      this.boxImgMain = this.isShowToolsImg ? "col-sm-9 col-12" : "col-sm-12 col-12"
      this.boxImgPreview = this.isShowToolsImg ? "col-sm-3 col-12" : ""
    }

  },
  watch: {   
  },
  created: async function() {   
    try{    
      this.loading(true)
      await this.frmSua_Serial_GP_Load()      
    }catch(e){
      this.$toast.error(e.data.message);
    }finally{
      this.loading(false);
    }
  }
 
};
</script>