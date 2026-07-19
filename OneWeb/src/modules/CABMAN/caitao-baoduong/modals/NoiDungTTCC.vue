<template src="./NoiDungTTCC.template.html"></template>
<script>
import { required } from "vuelidate/lib/validators";
export default {
  name: "NoiDungTTCC",
  props: {
    title:{
      type: String,
      default: "Nội dung",
    },
  },
  data: function () {
    return {
      hsccID: 0,
      chiTietCCID: 0,
      tienTrinhCCID: 0,
      noiDung: "",
    };
  },
  validations: {
    noiDung: {
      required,
    },
  },
  computed: {
    isGhiChu: function(){
      return this.title.toLowerCase().trim() != "nội dung";
    }
  },
  methods: {
    themTienTrinhCatChuyen: async function () {
      let rs = await this.$root.context.post(
        "/web-cabman/CaiTaoBaoDuong/themTienTrinhCatChuyen",
        {
          hsccID: this.hsccID,
          chiTietCCID: this.chiTietCCID,
          noiDung: this.noiDung,
          ngayDuKienBD: null,
          ngayDuKienKT: null,
        }
      );
      return rs.data.result;
    },
    capNhatTienTrinhCatChuyen: async function () {
      let rs = await this.$root.context.post(
        "/web-cabman/CaiTaoBaoDuong/capNhatTienTrinhCatChuyen",
        {
          tienTrinhCCID: this.tienTrinhCCID,
          noiDung: this.noiDung,
          ngayDuKienBD: null,
          ngayDuKienKT: null,
        }
      );
      return rs.data.result;
    },
    fillData: function (hsccID, chiTietCCID, tienTrinhCCID, noiDung) {
      this.hsccID = hsccID;
      this.chiTietCCID = chiTietCCID;
      this.tienTrinhCCID = tienTrinhCCID;
      this.noiDung = noiDung;
      if(this.isGhiChu)
        this.$refs.txtGhiChu.focus();
      else
        this.$refs.txtNoiDung.focus();
    },
    saveData: async function () {
      if (this.tienTrinhCCID == 0) {
        await this.themTienTrinhCatChuyen();
      } else {
        await this.capNhatTienTrinhCatChuyen();
      }
    },
    validateForm: function () {
      this.$v.noiDung.$touch();
      if (this.noiDung.length == 0) this.$root.toastError("Bạn phải nhập nội dung tiến trình")
      return !this.$v.noiDung.$error;
    },
  },
};
</script>
