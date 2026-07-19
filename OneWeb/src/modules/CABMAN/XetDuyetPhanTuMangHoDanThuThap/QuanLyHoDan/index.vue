<template src="./index.html"></template>
<script>
import GhiChuDuyet from '../GhiChuDuyet'
import Panzoom from "@panzoom/panzoom";
import axios from "axios";
import html2canvas from "html2canvas";
export default {
  components: {GhiChuDuyet},
  name: "QuanLyHoDan",
  props: {
    hoDan: {
      type: Object,
      required: true
    },
    id: String,
    options: { type: Object, default: () => {} },
  },
  data: function () {
    return {
      key: 1,
      isShown: false,

      isDuyet:false, // true: Duyet, false: Khong Dat
      checkedHoDan:[], // danh sach ho dan de xet duyet (= hoDan)

      thongTinHoDan:{},
      dsKhacHang: [],
      dsThueBao: [],
      dsThanhToan: [],
      dsDichVuDoiThu: [],
      thongTinImages :[], // danh sách các link ảnh
      viewImageSource:'', // image dang xem

      SERVER_IMAGES: "http://10.70.52.164:8081/FILE/" + this.$root.token.getMaTinh() + "/",

      zoom: 1,
      panzoom:'',
      scaleImage:1.0,
      scaleDsImage:0.7,

      rotation: 0
    }
  },
  watch:{
  },
  methods: {
    onHiddenModal() {
      this.isShown = false
      this.key++
    },
    async onShownModal() {
      this.isShown = true
      this.clear()
      try {
        this.loading(true)
        this.initPanZoom()

        this.thongTinHoDan = this.hoDan
        this.thongTinImages = await this.getThongTinImages(this.hoDan.MOTA)
        if (this.thongTinImages.length > 0) this.viewImageSource = this.thongTinImages[0].url
        await this.getDsThueBao(this.hoDan.SDKH_ID)
        await this.getDsKhachHang(this.hoDan.SDKH_ID)
        await this.getDsThanhToan(this.hoDan.SDKH_ID)
        await this.getDsDichVuDoiThu(this.hoDan.SDKH_ID)

      } catch (e) {

      } finally {
        this.loading(false)
      }
    },
    initPanZoom(){
      this.panzoom = Panzoom(document.getElementById('panzoom-element'), {
        maxScale: 5,
        canvas: true
      })
      console.log('panzoom',this.panzoom)
      const parent = document.getElementById('panzoom-element').parentElement

      // No function bind needed
      parent.addEventListener('wheel', this.panzoom.zoomWithWheel)

      //
      document.getElementById("panzoom-element").addEventListener('panzoomchange', (event) => {
        this.scaleImage = Math.round(event.detail.scale *100)
      })

    },
    btnDuyet(){
      this.isDuyet = true
      this.checkedHoDan = [this.hoDan]
      this.$bvModal.show('popupGhiChuDuyet')
    },
    btnKhongDat(){
      this.isDuyet = false
      this.checkedHoDan = [this.hoDan]
      this.$bvModal.show('popupGhiChuDuyet')
    },
    onClickZoomIn: async function () {
      this.onClickZoom(1);
    },
    onClickZoomOut: async function () {
      this.onClickZoom(2);
    },
    onClickZoomDefault: async function () {
      this.onClickZoom(3);
    },
    onClickRotate(type){
      if (type === 1){
        //rotate next
        this.rotation +=90
      }
      else this.rotation -=90 // rotate back
      if (this.rotation === 360 || this.rotation === -360) {
        // 360 means rotate back to 0
        this.rotation = 0;
      }
      document.querySelector("#img-id").style.transform = `rotate(${this.rotation}deg)`;
    },
    onClickZoom(loai) {
      switch (loai) {
        case 1:
          this.panzoom.zoomIn()
          break;
        case 2:
          this.panzoom.zoomOut()
          break;
        case 3:
          this.panzoom.reset()
          break;
      }
    },
    chooseImageToView(image){
      this.viewImageSource = image.url
    },
    async downloadImage(imageSrc) {
      if (imageSrc === ''){
        this.$root.toastError("Không có ảnh để tải về")
        return
      }
      try {
        this.loading(true)
        let element = document.getElementById("img-id")
        console.log(element)
        html2canvas(element, {
          useCORS: true,
        }).then((canvas) => {
          let imgData = canvas.toDataURL("image/png");
          this.fileDownload(imgData);
        })
      }catch (e){

      }finally {
        this.loading(false)
      }
    },

    fileDownload(downloadUrl) {
      // let aLink = document.createElement("a");
      // aLink.style.display = "none";
      // aLink.href = downloadUrl;
      // aLink.download = "image.png";
      // document.body.appendChild(aLink);
      // aLink.click();
      // document.body.removeChild(aLink);

      // console.log(imageSrc)
      // window.location.href =
      // window.open(downloadUrl, '_blank');
      let link = document.createElement('a');
      link.href = downloadUrl  // use realtive url
      link.download = '';
      link.target = '_blank'
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);

    },
    async getThongTinImages(mota){
      let images = []
      if (window.DOMParser)
      {
        let parser = new DOMParser();
        let xmlDoc = parser.parseFromString(mota.trim(), "text/xml")
        let imgs = xmlDoc.getElementsByTagName("img")
        if (imgs.length > 0){
          // for img
          for (let i = 0; i < imgs.length;i++){
            await this.$root.context.post("/web-cabman/storage/getPresignedUrl",{fileSource:imgs[i].attributes["url"].value})
              .then(res =>{
                if (res.error=== "200" && res.error_code === "BSS-00000000"){
                  images.push({id: imgs[i].attributes["id"].value, title: imgs[i].attributes["title"].value, url: res.data.result})
                }
              }).catch(e => this.$root.toastError("Lỗi tải ảnh"))
          }
        }
      }else { // Internet Explorer
        let xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
        xmlDoc.async = false;
        xmlDoc.loadXML(mota.trim());
        let imgs = xmlDoc.getElementsByTagName("img")
        if (imgs.length > 0){
          // for img
          for (let i = 0; i < imgs.length;i++){
            await this.$root.context.post("/web-cabman/storage/getPresignedUrl",{fileSource:imgs[i].attributes["url"].value})
              .then(res =>{
                if (res.error=== "200" && res.error_code === "BSS-00000000"){
                  images.push({id: imgs[i].attributes["id"].value, title: imgs[i].attributes["title"].value, url: res.data.result})
                }
              }).catch(e => this.$root.toastError("Lỗi tải ảnh"))
          }
        }
      }
      return images
    },
    clear(){
      this.isDuyet = false // true: Duyet, false: Khong Dat
      this.checkedHoDan = [] // danh sach ho dan de xet duyet (= hoDan)

      this.thongTinHoDan = {}
      this.dsKhacHang = []
      this.dsThueBao = []
      this.dsThanhToan = []
      this.dsDichVuDoiThu = []
      this.thongTinImages = [] // danh sách các link ảnh
      this.viewImageSource = '' // image dang xem

      this.SERVER_IMAGES = "http://10.70.52.164:8081/FILE/" + this.$root.token.getMaTinh() + "/"

      this.zoom = 1
      this.panzoom = ''
      this.scaleImage = 1.0
      this.scaleDsImage = 0.7

      this.rotation = 0
    },
    async loadThongTinHoDan(){
      try {
        this.loading(true)
        await this.getThongTinHoDan(this.thongTinHoDan.SDKH_ID)
        this.$emit("reload")
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    async getThongTinHoDan(sdkhId){
      await this.$root.context.get("web-cabman/phantu_mang/lay-thongtin-hodan",{sdkhId:sdkhId})
        .then(rs =>{
          this.thongTinHoDan = rs.data
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải thông tin hộ dân"))
    },
    async getDsThueBao(sdkhId){
      await this.$root.context.get("web-cabman/phantu_mang/lay-ds-thuebao",{sdkhId:sdkhId})
      .then(rs =>{
        this.dsThueBao = rs.data
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách thuê bao"))
    },
    async getDsKhachHang(sdkhId){
      await this.$root.context.get("web-cabman/phantu_mang/lay-ds-khachhang",{sdkhId:sdkhId})
      .then(rs =>{
        this.dsKhacHang = rs.data
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách khách hàng"))
    },
    async getDsThanhToan(sdkhId){
      await this.$root.context.get("web-cabman/phantu_mang/lay-ds-thanhtoan",{sdkhId:sdkhId})
      .then(rs =>{
        this.dsThanhToan = rs.data
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách thanh toán"))
    },
    async getDsDichVuDoiThu(sdkhId){
      await this.$root.context.get("web-cabman/phantu_mang/lay-ds-dichvu-doithu",{sdkhId:sdkhId})
      .then(rs =>{
        this.dsDichVuDoiThu = rs.data
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách dịch vụ đối thủ"))
    }
  }
}
</script>

<style scoped>
img {
  margin: 20px;
  transition: all 400ms;
}

/* or use CSS */
.rotate90 {
  -webkit-transform: rotate(90deg);
  -moz-transform: rotate(90deg);
  -o-transform: rotate(90deg);
  -ms-transform: rotate(90deg);
  transform: rotate(90deg);
}
</style>
