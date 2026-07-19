<template src="./index.html"></template>

<script>
import API from "../../../api/CapNhatKQKhoaMayNoCuoc";
import { fieldsDSLyDoHuyEdit } from "../../fields";
export default {
  props: {
    kieu: "",
    loaiTbId: "",
    loaiLdId: "",
    hdtbId: "",
    kieuLyDoHuyLHTB: {
      type: Number,
      default: 2
    }
  },
  data() {
    return {
      lapmoi: false,
      hdkh_id: "",
      cbbNhomHuyEdit: [],
      cbbNhomHuySelected: "",
      dtDanhSachLyDo: [],
      fieldTable: fieldsDSLyDoHuyEdit,
      itemSeletedDT: ""
    };
  },
  watch: {
    cbbNhomHuySelected: async function (newValue, oldValue) {
      if (this.cbbNhomHuySelected != null) {
        this.dtDanhSachLyDo = await this.getDSLyDoHuy(
          this.loaiLdId,
          this.loaiTbId,
          this.kieu,
          this.cbbNhomHuySelected
        );
      }
    }
  },
  methods: {
    async getDSNhomHuy(loaiLdId, loaiTbId, kieu) {
      try {
        this.loading(true);
        let data = {
          pKieu: kieu,
          pLoaiLD: loaiLdId,
          pLoaiTBId: loaiTbId
        };
        let res = [];
        let response = await API.getDanhSachNhomHuy(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          if (response.data.data.length > 0) {
            response.data.data.forEach(item => {
              res.push(item);
            });
          }
        }
        return res;
      } catch (error) {
        console.log(error);
        this.$toast.error(
          "Bạn chưa chọn dữ liệu trên lưới phiếu chờ xác nhận!"
        );
      } finally {
        this.loading(false);
      }
    },
    async getDSLyDoHuy(loaiLdId, loaiTbId, kieu, nhomHuyId) {
      try {
        this.loading(true);
        let data = {
          pLoaiLDId: loaiLdId,
          pLoaiTBId: loaiTbId,
          pNhomHuyId: nhomHuyId,
          pKieu: kieu
        };
        let res = [];
        let response = await API.getDanhSachLyDoHuy(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          if (response.data.data.length > 0) {
            response.data.data.forEach(item => {
              res.push(item);
            });
          }
        }
        return res;
      } catch (error) {
        console.log(error);
        this.$toast.error(error.response.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    itemSelectedChanged: function (value) {
      console.log(value);
      this.itemSeletedDT = value;
    },
    async chapNhan() {
      let dsChon = [];
      this.itemSeletedDT.forEach(element => {
        this.dtDanhSachLyDo.forEach(item => {
          if (item.LYDOHUY_ID == element) {
            dsChon.push(item);
          }
        });
      });
      if (dsChon.length < 1) {
        this.$toast.warning("Chưa chọn lý do hủy!");
        return;
      }
      let dsPhieuChon = [];
      dsChon.forEach(element => {
        dsPhieuChon.push({
          LYDOHUY_ID: element.LYDOHUY_ID,
          NOIDUNG: element.NOIDUNG
        });
      });
      if (this.hdtbId != "0") {
        try {
          this.loading(true);
          let data = {
            pHDTBId: this.hdtbId,
            pKieuLyDoHuy: this.kieuLyDoHuyLHTB,
            pDS: dsPhieuChon
          };
          let response = await API.putCapNhatThayDoiLyDoHuy(this.axios, data);
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined
          ) {
            if (response.data.data == true || response.data.data == "true") {
              this.$toast.success("Cập nhật thành công!");
            }
          }
        } catch (error) {

          this.$toast.error(error.data.message_detail);
        } finally {
          this.loading(false);
        }

      }
      else {
        this.$emit("sendData", { dslydo: dsPhieuChon });
      }
    }
  },
  async mounted() {
    console.log(
      this.loaiTbId,
      this.loaiLdId,
      this.hdtbId,
      this.kieuLyDoHuyLHTB
    );
    this.cbbNhomHuyEdit = await this.getDSNhomHuy(
      this.loaiLdId,
      this.loaiTbId,
      this.kieu
    );
    if (this.cbbNhomHuyEdit.length > 0)
      this.cbbNhomHuySelected = this.cbbNhomHuyEdit[1].NHOMHUY_ID;
  }
};
</script>

<style></style>
