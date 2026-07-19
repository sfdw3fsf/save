<template src="./DSLoiGPXLN.html"></template>
<style>
.text-red {
  color: red !important;
}
</style>
<script>
import API from "../../../api/GiaoPhieuXuLyNo";
export default {
  data() {
    return {
      target: ".main-wrapper",
      animationSettings: { effect: "Zoom" },
      dtLoi: [],
      dtDS_DaGiao: [],
      loi: true,
      vkieu: 0,
      chapnhan: false,
      dsMa_Tt: "",
      TAODS_KHL_KH: 10,
      tabIndex: 0,
      itemSelected: [],
    };
  },
  methods: {
    reset() {
      this.dtLoi = [];
      this.dtDS_DaGiao = [];
      this.loi = true;
      this.vkieu = 0;
      this.chapnhan = false;
      this.dsMa_Tt = "";
    },
    async openDialog() {
      if (this.vkieu == this.TAODS_KHL_KH) {
      }
      if (this.dtLoi.length > 0) {
        this.tabIndex = 1;
      }
      if (this.dtDS_DaGiao.length > 0) {
        let lst = [...this.dtDS_DaGiao];

        for (let index = 0; index < lst.length; index++) {
          if (lst[index].CHON == "s0") {
            lst.splice(index, 1);
            index--;
          }
        }
        this.dtDS_DaGiao = lst;
      }
      this.$refs.dlgLoiGPXLN.show();
    },
    selectedItemsChangedPhieuDaGiao(value) {
      this.itemSelected = value;
    },
    async btnCapNhat() {
      let lstPhieuDaGiao = [];
      this.itemSelected.forEach((element) => {
        this.dtDS_DaGiao.forEach((item) => {
          if (item.MA_XLN == element) {
            lstPhieuDaGiao.push(item);
            return;
          }
        });
      });
      await this.$bvModal
        .msgBoxConfirm("Bạn có chắc chắn cập nhật dữ liệu không ?", {
          title: "Thông báo",
          size: "lg",
          buttonSize: "lg",
          okVariant: "primary",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy bỏ",
          hideHeaderClose: false,
          centered: true,
          "modal-class": ["f18", "text-center"],
        })
        .then(async (value) => {
          if (value) {
            let dsXLNCN = [];
            for (const iterator of lstPhieuDaGiao) {
              dsXLNCN.push({
                noidung_xln_id: iterator.NOIDUNG_XLN_ID,
                ghichu: iterator.GHICHU,
                bienban_id: iterator.BIENBAN_ID,
              });
            }
            try {
              this.loading(true);
              let response = await API.putCapNhatGPXLNFile(this.axios, dsXLNCN);
              if (
                response.data.error_code === "BSS-00000000" &&
                response.data.data !== undefined
              ) {
                this.$toast.success("Cập nhật dữ liệu thành công!");
              }
            } catch (error) {
              this.$toast.error(error.data.message_detail);
            } finally {
              this.loading(false);
            }
          }
        })
        .catch((err) => {
          this.loading(false);
        });
    },
  },
};
</script>

<style>
</style>