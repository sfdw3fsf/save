<template src="./index.html"></template>
<script>

import XemToaDo from "./../BanDo/index"
import BssRequiredMarker from '@/components/BssRequiredMarker'

export default {
  components: {XemToaDo, BssRequiredMarker},
  name: "QuanLyDoiTuong",
  props: {

    id: String,
    objUpdate: Object
  },
  data: function () {
    return {
      tenDoiTuong: "",
      loaiDoiTuong: "",
      loaiDoiTuongId: "",
      maDoiTuongThuc: "",
      tuyenTruyendan: "",
      tuyenTruyendanId: "",
      maDoiTuong: -1,
      thietBiId: 0,
      tenHienThiCreate: "",
      viTriXTieuDe: 0,
      viTriYTieuDe: 0,
      viTriX: 0,
      viTriY: 0,
      kinhDo: 0,
      viDo: 0,
      category: {
        dinhdanh: 0,
        prtg: 0,
        gianDo: 0,
        banDo: 0
      }
    }
  }, computed: {
    getPosition() {
      return {
        lat: parseFloat(this.objUpdate.VIDO),
        lng: parseFloat(this.objUpdate.KINHDO)
      }
    }
  },
  methods: {
    hideModal: function () {
      // this.$emit('hideModal')
      this.clear()
      this.$bvModal.hide(this.id)
      // this.$emit('reloadGianDo')
    }, reloadGianDo: function () {
      // this.$emit('hideModal')
      this.$emit('reloadGianDo')
      this.$bvModal.hide(this.id)
    },
    onHiddenModalDT() {

    },
    onClickShowKinhDo() {
      this.$bvModal.show('ModalChonToaDo')
    },
    async onShownModalDT() {
      this.tenHienThiCreate = ""
      this.kinhDo = this.getPosition.lat
      this.viDo = this.getPosition.lng
      if (this.objUpdate) {
        if (this.objUpdate.KIEU_ === "UPDATE") {
          this.maDoiTuong = this.objUpdate.DOITUONG_ID
          this.tenDoiTuong = this.objUpdate.TEN_DT
          this.tenHienThiCreate = this.objUpdate.TEN_HT
          this.loaiDoiTuong = this.objUpdate.TEN_LOAI_DT
          this.loaiDoiTuongId = this.objUpdate.LOAIDT_ID
          this.maDoiTuongThuc = this.objUpdate.DOITUONG_MAP_ID
          this.tuyenTruyendan = this.objUpdate.TEN_TTD
          this.tuyenTruyendanId = this.objUpdate.TUYENTD_ID
          this.viTriX = this.objUpdate.VITRI_X
          this.viTriY = this.objUpdate.VITRI_Y
          this.viTriYTieuDe = this.objUpdate.VITRI_TD_Y
          this.viTriXTieuDe = this.objUpdate.VITRI_TD_X
        } else {
          this.tenDoiTuong = this.objUpdate.TEN_DT
          this.loaiDoiTuong = this.objUpdate.LOAI_DT
          this.loaiDoiTuongId = this.objUpdate.LOAI_DT_ID
          this.maDoiTuongThuc = this.objUpdate.DOITUONG_ID
          this.tuyenTruyendan = this.objUpdate.TEN_TUYENTD
          this.tuyenTruyendanId = this.objUpdate.TUYENTD_ID
          if (this.objUpdate.VITRI_X) {
            this.viTriX = this.objUpdate.VITRI_X

          } else {
            this.viTriX = 0

          }
          if (this.objUpdate.VITRI_Y) {
            this.viTriY = this.objUpdate.VITRI_Y

          } else {
            this.viTriY = 0

          }
          this.viTriYTieuDe = 0
          this.viTriXTieuDe = 0
        }
        console.log(this.objUpdate)
      }
    },
    async btnGhiLai() {
      if (!this.viTriXTieuDe) {
        this.viTriXTieuDe = 0
      }
      if (!this.viTriYTieuDe) {
        this.viTriYTieuDe = 0
      }
      if (this.objUpdate.KIEU_ === "UPDATE") {
        await this.capNhatDoiTuong()
      } else {
        await this.taomoiDoiTuong()
      }


    }, async taomoiDoiTuong() {
      let body = {
        ten_ht: this.tenHienThiCreate,
        loaidt_id: this.loaiDoiTuongId,
        doituong_map_id: this.maDoiTuongThuc,
        tuyentd_id: this.tuyenTruyendanId,
        vitri_x: this.viTriX,
        vitri_y: this.viTriY,
        vitri_td_x: this.viTriXTieuDe,
        vitri_td_y: this.viTriYTieuDe

      }
      await this.$root.context.post(
        "/web-suco/mang_bangrong/taodoituong", body
      ).then(res => {
        if (res) {
          this.$toast.success("Thêm mới thành công.")
          this.reloadGianDo()
        } else {
          this.$toast.error("Thêm mới thất bại.")
        }
      }).catch(e => {
        throw e
      })
    }, async capNhatDoiTuong() {
      let body = {
        doituong_id: this.maDoiTuong,
        ten_ht: this.tenHienThiCreate,
        tuyentd_id: this.tuyenTruyendanId,
        vitri_x: this.viTriX,
        vitri_y: this.viTriY,
        vitri_td_x: this.viTriXTieuDe,
        vitri_td_y: this.viTriYTieuDe
      }


      await this.$root.context.post(
        "/web-suco/mang_bangrong/capnhatdoituong", body
      ).then(res => {
        if (res) {
          this.$toast.success("Cập nhật thành công.")
          this.reloadGianDo()
        } else {
          this.$toast.error("Cập nhật thất bại.")
        }
      }).catch(e => {
        throw e
      })
    },
    clear(){
      this.tenDoiTuong = ""
      this.loaiDoiTuong = ""
      this.loaiDoiTuongId = ""
      this.maDoiTuongThuc = ""
      this.tuyenTruyendan = ""
      this.tuyenTruyendanId = ""
      this.maDoiTuong = -1
      this.thietBiId = 0
      this.tenHienThiCreate = ""
      this.viTriXTieuDe = 0
      this.viTriYTieuDe = 0
      this.viTriX = 0
      this.viTriY = 0
      this.kinhDo = 0
      this.viDo = 0
    }

  }


}
</script>
