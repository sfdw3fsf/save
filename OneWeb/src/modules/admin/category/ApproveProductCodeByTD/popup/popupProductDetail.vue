<template src="./popupProductDetail.html"></template>

<script>
import API from '../ApproveProductCodeByTDAPI';

export default {
  name: "popupProductDetail",
  props: ["DmChung", "SanPham"],
  data() {
    return {
      dsSpdv: [],
      dsPtPhatTrien: [],
      dsDonVi: [],
      dsTrangThai: [],
      dsDichVuCLTV: [],
      SanPhamSelected: null,
      searchInfo: {
        nhomspdv_id: 0,
        phuongthuc_id: 0,
        donvi_id: 0,
        trangthai_id: 1,
        dv_clvt_id: 1,
        tag: 1,
        ma_spdv: '',
        ten_spdv: ''
      },
    }
  },
  methods: {
    async pheDuyet_Tct() {
      if (this.SanPham == undefined) {
        this.$toast.warning("Vui lòng chọn sản phẩm");
        return;
      }
      let data = { "spdv_id": this.SanPham.spdv_id };
      API.pheDuyetCapTct(this.axios, data)
        .then((response) => {
          if (response.data.error_code === "BSS-00000000") {
            this.$toast.success(response.data.message);
            document.getElementsByClassName("click-tk").click()
          } else {
            this.$toast.warning(response.data.message);

          }
        })
        .catch((error) => {
          this.$toast.show('Trạng thái: ' + response.data.data)
        });
    },
    async khongPheDuyet_Tct() {
      if (this.SanPham == undefined) {
        this.$toast.warning("Vui lòng chọn sản phẩm");
        return;
      }
      this.$bvModal.show('popupKoDuyet');
      // let data = { "spdv_id": this.SanPham.spdv_id };
      // API.tuChoiDuyetCapTct(this.axios, data)
      //     .then((response) => {
      //         if (response.data.error_code === "BSS-00000000") {
      //             this.$toast.success(response.data.message);
      //         }
      //         else {
      //             this.$toast.warning(response.data.message);

      //         }
      //     })
      //     .catch((error) => {
      //         // this.$toast.info(response.message);
      //     });
    },
    async guiTapDoanDuyet() {
      if (this.SanPham == undefined) {
        this.$toast.warning("Vui lòng chọn sản phẩm");
        return;
      }
      let data = { "spdv_id": this.SanPham.spdv_id };
      API.guiPheDuyetCapTapDoan(this.axios, data)
        .then((response) => {
          if (response.data.error_code === "BSS-00000000") {
            this.$toast.success(response.data.message);
          } else {
            this.$toast.warning(response.data.message);

          }
        })
        .catch((error) => {
          this.$toast.warning(response.data.message);
        });
    }
  },
  async mounted() {
    // await this.layDanhMucChung();
    // await console.log(this.SanPham);
    // await console.log(this.DmChung.DM_DICHVU_CLVT);
  },
}
</script>
