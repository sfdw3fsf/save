<template src='./RestoreUnconfiguredPort.html'></template>
<style scoped src='./RestoreUnconfiguredPort.scss'></style>

<script>
import moment from "moment"
// eslint-disable-next-line no-unused-vars
import axios from "axios"
import api from "./api"
import { FormState } from "./Enums"
import BssErrorMarker from "@/components/BssErrorMarker"
import { required, helpers } from "vuelidate/lib/validators"

import KDatePicker from "@/components/kylq_components/KDatePicker.vue"
const checkedConstraint = (isChecked) => isChecked === true
export default {
  components: {
    KDatePicker, BssErrorMarker
  },

  data () {
    return {

      ngayHtError: false,
      ngayGVError: false,
      dateconfig: {
        altFormat: "d/m/Y G:i:S K",
        altInput: true,
        dateFormat: "d/m/Y G:i:S K",
        allowInput: true
      },
      current_form_state: 0,
      isDisableCapNhatButton: true,
      isDisableHoanThienButton: true,
      ds_loai_hd: [],
      ds_dichvu_vt: [],
      params: {
        phieu_id: 0,
        loai_hopdong_id: 1,
        dichvu_vt_id: 4,
        huonggiao_id: 20012,
        nhanvien_gv_id: 0,
        ma_gd: "",
        ma_tb: "",
        ma_tb_moi: "",
        sone: "",
        host_ld_cu: "",
        host_ld_moi: "",
        ten_tb: "",
        diachi_ld: "",
        kieu_ld: "",
        ghi_chu: "",

        ds_nguoi_gv: [],
        nhanvien_current: null,
        noidung_giao: "",
        noidung_thuchien: "",
        datetimeFormat: "DD/MM/YYYY HH:mm:ss",
        ngay_gv: {
          value: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
          isChecked: false
        },
        ngay_ht: {
          value: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
          isChecked: false
        }
      },

      ds_phieu: {
        header: [
          { headerText: "Mã Gd", fieldName: "ma_gd" },
          { headerText: "Ngày giao", fieldName: "ngaygiao" },
          { headerText: "Nội dung giao", fieldName: "nd_giao" },
          { headerText: "Số máy/Acc", fieldName: "ma_tb" },
          { headerText: "Tên TB", fieldName: "ten_tb" },
          { headerText: "Địa chỉ LĐ", fieldName: "diachi_ld" },
          { headerText: "Loại hình", fieldName: "loaihinh_tb" },
          { headerText: "Host LĐ cũ", fieldName: "host_cu" },
          { headerText: "Ghi chú", fieldName: "ghichu" }
        ],
        list: []
      },

      loai_hd_avaiable: [1, 3, 4, 6, 7, 8, 25]
    }
  },

  created () {
  },
  async mounted () {
    try {
      this.loading(true)
      await Promise.all([
        this.loadLoaiHopDong(),
        this.loadDichVuVienThong(),
        this.loadDanhSachPhieu(),
        this.loadDanhSachNguoiGiaoViec()
      ])
      window.addEventListener("keyup", (e) => {
        console.log(e.key)
        if (e.key === "F5") {
          this.loadDanhSachPhieu()
        } else if (e.key === "F9") {
          console.log("da an F9")
          if (!this.isDisableCapNhatButton) {
            this.onSaveClicked()
          } else {
            this.$toast.warning("Chưa có phiếu nào được chọn")
          }
        }
      })
    } catch (ex) {
      console.log(ex)
    } finally {
      this.loading(false)
    }
  },
  methods: {
    getData (response) {
      if (
        response.data.error === 200 ||
        response.data.error_code === "BSS-00000000"
      ) {
        return response.data.data
      } else {
        this.$toast.warning("Không có dữ liệu")
        console.log(response.data.error_code)
      }
      return []
    },
    async loadLoaiHopDong () {
      try {
        var tmpDsLoaiHd = this.getData(await api.get_loai_hopdong(this.axios))
        this.ds_loai_hd = tmpDsLoaiHd.filter(item => this.loai_hd_avaiable.includes(item.LOAIHD_ID))
      } catch (error) {
        console.log(error)
      }
    },
    onLoaiHopDongChanged () {
      this.loadDanhSachPhieu()
    },
    async loadDichVuVienThong () {
      try {
        this.ds_dichvu_vt = this.getData(await api.get_dichvu_vt(this.axios))
      } catch (error) {
        console.log(error)
      }
    },
    async loadDanhSachPhieu (vMaGD) {
      this.clearNoiDungForm()
      if (!vMaGD) {
        vMaGD = "0"
      }
      var body = {
        loaihd_id: this.params.loai_hopdong_id,
        dichvuvt_id: this.params.dichvu_vt_id,
        ma_gd: vMaGD,
        phieu_id: 0,
        huonggiao_id: this.params.huonggiao_id
      }
      try {
        this.loading(true)
        this.ds_phieu.list = this.getData(
          await api.sp_ds_phieu(this.axios, body)
        )
      } catch (ex) {
        console.log(ex.message)
      } finally {
        this.loading(false)
      }
    },
    async loadDanhSachNguoiGiaoViec () {
      var body = { "loainv_id": 6 }
      try {
        await this.thongTinNguoiDung()
        this.params.ds_nguoi_gv = this.getData(
          await api.sp_ds_nguoi_giaoviec(this.axios, body))
        this.params.ds_nguoi_gv.push({"ten_nv": this.params.nhanvien_current.ten_nv, "nhanvien_id": this.$root.token.getNhanVienID()})
        this.params.nhanvien_gv_id = this.$root.token.getNhanVienID()
      } catch (ex) {
        console.log(ex.message)
      }
    },
    async thongTinNguoiDung () {
      try {
        this.params.nhanvien_current = this.getData(
          await api.thongtin_nguoidung(this.axios)
        )
      } catch (ex) {
        console.log(ex.message)
      }
    },
    async onMaGDEnter () {
      await this.loadDanhSachPhieu(this.params.ma_gd.trim())
    },
    async onDichVuVTChanged () {
      this.loadDanhSachPhieu()
    },
    onSelectedRowChange (phieu) {
      this.clearNoiDungForm()
      this.current_form_state = FormState.NEW

      this.params.phieu_id = phieu.phieu_id
      this.params.kieu_ld = phieu.ten_kieuld
      this.params.ma_gd = phieu.ma_gd
      this.params.ma_tb = phieu.ma_tb
      this.params.ten_tb = phieu.ten_tb
      this.params.diachi_ld = phieu.diachi_ld
      this.params.noidung_thuchien = phieu.nd_thuchien
      this.params.noidung_giao = phieu.nd_giao
      this.params.ghi_chu = phieu.ghichu
      // this.params.nhanvien_gv_id = this.params.ds_nguoi_gv[0].nhanvien_id
      // if (phieu.ngay_gv) {
      //   this.params.ngay_gv.isChecked = true
      //   this.params.ngay_gv.value = moment(phieu.ngay_gv, "YYYY-MM-DD HH:mm:ss").format("DD/MM/YYYY hh:mm:ss A")
      // }
      if (phieu.ngay_ht_gp) {
        this.params.ngay_ht.isChecked = true
        this.params.ngay_ht.value = moment(phieu.ngay_ht_gp, "YYYY-MM-DD HH:mm:ss").format("DD/MM/YYYY hh:mm:ss A")
        console.log("ngay_ht " + this.params.ngay_ht.value)
      } else {
        this.params.ngay_ht.value = moment(new Date()).format("DD/MM/YYYY hh:mm:ss A")
      }
      this.params.ngay_gv.value = moment(new Date()).format("DD/MM/YYYY hh:mm:ss A")
    },

    async update_noidung_thuchien () {
      if (!this.params.phieu_id) {
        this.$toast.warning("Chưa có phiếu nào được chọn")
        return
      }
      if (!this.params.noidung_thuchien) {
        this.$toast.warning("Bạn chưa nhập nội dung thực hiện")
        return
      }
      var body = {
        nd_thuchien: this.params.noidung_thuchien,
        phieu_id: this.params.phieu_id
      }
      try {
        var response = await api.sp_update_noidung_thuchien(this.axios, body)
        if (response.data.error_code === "BSS-00000000") {
          this.$toast.success("Cập nhật nội dung thực hiện thành công")
          this.update_phieu_nd_thuchien(this.params.phieu_id, this.params.noidung_thuchien)
        } else {
          this.$toast.error("Có lỗi xảy ra khi cập nhật nội dung thực hiện ")
        }
      } catch (ex) {
        this.$toast.error("Có lỗi xảy ra khi cập nhật nội dung thực hiện ")
      }
    },
    async onRefreshClicked () {
      this.loadDanhSachPhieu()
    },
    async onSaveClicked () {
      if (!this.checkDieuKienCapNhat()) {
        return
      }

      try {
        this.loading(true)
        var body = {
          "vngay_th": moment(this.params.ngay_ht.value, "DD/MM/YYYY hh:mm:ss A").format("DD/MM/YYYY HH:mm:ss"),
          "vphieu_id": this.params.phieu_id
        }
        console.log("body = " + body["vngay_th"])
        var response = await api.capnhat_phieu_thuhoi(this.axios, body)
        if (response.data.error === "200" && response.data.error_code === "BSS-00000000") {
          this.current_form_state = FormState.UPDATED
          this.$toast.success("Đã cập nhật thành công lên hệ thống")
          this.update_phieu_ngay_ht(this.params.phieu_id, this.params.ngay_ht.value)
        } else {
          this.$toast.error("Có lỗi xảy ra, chưa cập nhật được lên hệ thống")
        }
      } catch (e) {
        this.$toast.error("Có lỗi xảy ra")
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    update_phieu_nd_thuchien (phieuId, noidung) {
      var currentPhieu = this.ds_phieu.list.filter(item => item.phieu_id == phieuId)
      if (currentPhieu && currentPhieu.length == 1) {
        currentPhieu[0].nd_thuchien = noidung
      }
    },
    update_phieu_ngay_ht (phieuId, ngayHt) {
      var currentPhieu = this.ds_phieu.list.filter(item => item.phieu_id == phieuId)
      if (currentPhieu && currentPhieu.length == 1) {
        currentPhieu[0].ngay_ht_gp = moment(ngayHt, "DD/MM/YYYY hh:mm:ss A").format("YYYY-MM-DD hh:mm:ss")
      }
    },
    async onHoanThienClicked () {
      try {
        this.loading(true)
        var body = {
          "vngay_th": moment(this.params.ngay_ht.value, "DD/MM/YYYY hh:mm:ss A").format("DD/MM/YYYY HH:mm:ss"),
          "vphieu_id": this.params.phieu_id,
          "vttph_id": "5"
        }
        var response = await api.capnhat_tt_phieugiao(this.axios, body)
        if (response.data.error === "200" && response.data.error_code === "BSS-00000000") {
          await this.ThongBao("Đã hoàn thiện thành công", "Hoàn thiện khôi phục cổng đã huỷ")
          await this.loadDanhSachPhieu()
        } else {
          this.$toast.error("Có lỗi xảy ra " + response.data.error)
        }
      } catch (e) {
        this.$toast.error("Có lỗi xảy ra " + e.message)
      } finally {
        this.loading(false)
      }
    },
    clearNoiDungForm () {
      this.current_form_state = FormState.NONE
      this.params.phieu_id = 0
      this.params.ngay_gv.isChecked = false
      this.params.ngay_ht.isChecked = false
      this.params.kieu_ld = ""
      this.params.ten_tb = ""
      this.params.diachi_ld = ""
      this.params.noidung_giao = ""
      this.params.noidung_thuchien = ""
      this.params.ghi_chu = ""
      this.params.ngay_ht.value = moment(new Date()).format("DD/MM/YYYY hh:mm:ss A")
      this.params.ngay_gv.value = moment(new Date()).format("DD/MM/YYYY hh:mm:ss A")
      this.params.ma_tb = ""
      // this.params.nhanvien_gv_id = 0
      this.params.sone = ""
      this.params.ma_tb_moi = ""
      this.params.host_ld_cu = ""
      this.params.host_ld_moi = ""
      console.log("clearNoiDungForm " + this.current_form_state)
    },
    async ThongBao (msg, title) {
      if (!title) title = "Lỗi dữ liệu"
      this.$alert(msg
        , title,
        {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",

          type: "info"

        }
      )
    },
    checkDieuKienCapNhat () {
      var result = true
      if (!this.params.phieu_id) {
        this.$toast.warning("Chưa có phiếu nào được chọn")
        result = false
      }
      if (
        !this.params.ngay_ht.isChecked ||
       !this.params.ngay_ht.value.trim()) {
        result = false
        this.$toast.warning("Phải chọn ngày hoàn thành")
      }

      // if (!this.params.ngay_gv.isChecked || !this.params.ngay_gv.value.trim()) {
      //   result = false
      //   this.$toast.warning("Phải chọn ngày giao việc")
      // } else if (
      //   !this.params.ngay_ht.isChecked ||
      //  !this.params.ngay_ht.value.trim()) {
      //   result = false
      //   this.$toast.warning("Phải chọn ngày hoàn thành")
      // } else if (moment(this.params.ngay_gv.value, "DD/MM/YYYY hh:mm:ss A") > moment(this.params.ngay_ht.value, "DD/MM/YYYY hh:mm:ss A")) {
      //   result = false
      //   this.$toast.warning("Ngày giao việc không được lớn hơn ngày hoàn thành")
      // }
      return result
    }

  },
  watch: {
    current_form_state: function (newState, oldState) {
      this.isDisableHoanThienButton = newState !== FormState.UPDATED
      this.isDisableCapNhatButton = newState === FormState.NONE
    }

    // "params.ngay_ht.value": function (newState, oldState) {
    //   if (!this.params.ngay_ht.value.trim()) {
    //     this.ngayHtError = true
    //   } else if (this.params.ngay_gv.value.trim() && moment(this.params.ngay_gv.value, "DD/MM/YYYY hh:mm:ss A") > moment(this.params.ngay_ht.value, "DD/MM/YYYY hh:mm:ss A")) {
    //     this.ngayHtError = true
    //   } else {
    //     this.ngayHtError = false
    //   }
    //   if (this.params.ngay_ht.value.trim() && this.params.ngay_gv.value.trim() && moment(this.params.ngay_gv.value, "DD/MM/YYYY hh:mm:ss A") < moment(this.params.ngay_ht.value, "DD/MM/YYYY hh:mm:ss A")) {
    //     this.ngayGVError = false
    //   }
    // },
    // "params.ngay_gv.value": function (newState, oldState) {
    //   if (!this.params.ngay_gv.value.trim()) {
    //     this.ngayGVError = true
    //   } else if (this.params.ngay_ht.value.trim() && moment(this.params.ngay_gv.value, "DD/MM/YYYY hh:mm:ss A") > moment(this.params.ngay_ht.value, "DD/MM/YYYY hh:mm:ss A")) {
    //     this.ngayGVError = true
    //   } else {
    //     this.ngayGVError = false
    //   }
    //   if (this.params.ngay_ht.value.trim() && this.params.ngay_gv.value.trim() && moment(this.params.ngay_gv.value, "DD/MM/YYYY hh:mm:ss A") < moment(this.params.ngay_ht.value, "DD/MM/YYYY hh:mm:ss A")) {
    //     this.ngayHtError = false
    //   }
    // }
  }
}
</script>
