<template src="./index.html"></template>
<script>
import GhiChuDuyet from '../GhiChuDuyet'
import API from '../API';
import Panzoom from "@panzoom/panzoom";
import html2canvas from "html2canvas";

export default {
  components: {GhiChuDuyet},
  name: "ThongTinHoDan",
  props: {
    hoDan: {
      type: Object,
      required: true
    },
    id: String,
    options: {
      type: Object, default: () => {
      }
    },
  },
  data: function () {
    return {
      key: 1,
      isShown: false,

      isDuyet: false, // true: Duyet, false: Khong Dat
      checkedHoDan: {}, // danh sach ho dan de xet duyet (= hoDan)

      thongTinHoDan: {},
      dsKhachHang: [],
      dsThueBao: [],
      dsThanhToan: [],
      dsDichVuDoiThu: [],
      thongTinImages: [], // danh sách các link ảnh
      viewImageSource: '', // image dang xem

      SERVER_IMAGES: "http://10.70.52.164:8081/FILE/" + this.$root.token.getMaTinh() + "/",

      zoom: 1,
      panzoom: '',
      scaleImage: 1.0,
      scaleDsImage: 0.7,

      rotation: 0
    }
  },
  watch: {},
  methods: {
    onHiddenModal() {
      this.isShown = false
      this.key++
    },

    async onShownModal() {
      // console.log(this.hoDan);
      this.isShown = true
      this.clear()
      try {
        this.loading(true)
        this.initPanZoom()

        this.thongTinHoDan = this.hoDan;
        this.thongTinImages = this.hoDan.listImage;
        if (this.thongTinImages.length > 0) this.viewImageSource = this.thongTinImages[0].url
        await this.getDsKhachHang(this.hoDan.sdkh_id)
        await this.getDsThanhToan(this.hoDan.sdkh_id)
        await this.getDsThueBao(this.hoDan.sdkh_id)
        await this.getDsDichVuDoiThu(this.hoDan.sdkh_id)

      } catch (e) {

      } finally {
        this.loading(false)
      }
    },
    initPanZoom() {
      this.panzoom = Panzoom(document.getElementById('panzoom-element'), {
        maxScale: 5,
        canvas: true
      })
      const parent = document.getElementById('panzoom-element').parentElement

      // No function bind needed
      parent.addEventListener('wheel', this.panzoom.zoomWithWheel)

      //
      document.getElementById("panzoom-element").addEventListener('panzoomchange', (event) => {
        this.scaleImage = Math.round(event.detail.scale * 100)
      })

    },
    btnDuyet() {
      this.isDuyet = true
      this.checkedHoDan = this.hoDan
      this.$bvModal.show('popupGhiChuDuyet')
    },
    btnKhongDat() {
      this.isDuyet = false
      this.checkedHoDan = this.hoDan
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
    onClickRotate(type) {
      if (type === 1) {
        //rotate next
        this.rotation += 90
      } else this.rotation -= 90 // rotate back
      if (this.rotation === 360 || this.rotation === -360) {
        // 360 means rotate back to 0
        this.rotation = 0;
      }
      document.querySelector("#img-id").style.transform = `rotate(${this.rotation}deg)`;
    },

    chooseImageToView(image) {
      this.viewImageSource = image.url
    },

    async downloadImage(imageSrc) {
      if (imageSrc === '') {
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
      } catch (e) {

      } finally {
        this.loading(false)
      }
    },

    fileDownload(downloadUrl) {
      let link = document.createElement('a');
      link.href = downloadUrl  // use realtive url
      link.download = '';
      link.target = '_blank'
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);

    },

    scaleDsAnh(event) {
      let val = event.target.value;
      $('.list-img').attr('style', `max-width: ${val}%`);
    },

    clear() {
      this.isDuyet = false // true: Duyet, false: Khong Dat
      this.checkedHoDan = {} // danh sach ho dan de xet duyet (= hoDan)

      this.thongTinHoDan = {}
      this.dsKhachHang = []
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

    async loadThongTinHoDan() {
      try {
        this.loading(true)
        await this.getThongTinHoDan(this.thongTinHoDan.sdkh_id)
        this.$emit("reload")
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },

    async getThongTinHoDan(sdkhId) {
      await API.hodan_chi_tiet(this.axios, sdkhId)
        .then(rs => {
          let {data} = rs.data;
          if (data instanceof Array && data.length > 0) {
            this.thongTinHoDan = data[0];
          } else {
            this.thongTinHoDan = rs.data.data
          }
        })
        .catch(e => this.$toast.error("Có lỗi xảy ra khi tải thông tin hộ dân"));
    },

    async getDsThueBao(sdkhId) {
      await API.ds_thuebao_sdkhid(this.axios, sdkhId)
        .then(rs => {
          this.dsThueBao = rs.data.data
        })
        .catch(e => this.$toast.error(("Có lỗi xảy ra khi tải danh sách thuê bao")));
    },

    async getDsKhachHang(sdkhId) {
      await API.ds_khachhang_sdkhid(this.axios, sdkhId)
        .then(rs => {
          this.dsKhachHang = rs.data.data
        })
        .catch(e => this.$toast.error("Có lỗi xảy ra khi tải danh sách khách hàng"));
    },

    async getDsThanhToan(sdkhId) {
      await API.ds_thanhtoan_sdkhid(this.axios, sdkhId)
        .then(rs => {
          this.dsThanhToan = rs.data.data
        })
        .catch(e => this.$toast.error("Có lỗi xảy ra khi tải danh sách thanh toán"));
    },

    async getDsDichVuDoiThu(sdkhId) {
      await API.ds_dichvudoithu_sdkhid(this.axios, sdkhId)
        .then(rs => {
          this.dsDichVuDoiThu = rs.data.data
        })
        .catch(e => this.$toast.error("Có lỗi xảy ra khi tải danh sách dịch vụ đối thủ"));
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

.btn-second {
  background-color: #fff;
  border-color: #0176FF;
  color: #0176FF;
  font-weight: 600;
}

.btn-second:hover, .btn-second.active {
  background: #0176FF;
  color: #fff;
}

.modal-ho-dan {
  width: 100%;
  max-width: 1280px;
}

.btn-main {
  background: #0176FF;
  color: #fff;
}

.btn-main:hover {
  background: #035FCC;
  color: #fff;
}
</style>
