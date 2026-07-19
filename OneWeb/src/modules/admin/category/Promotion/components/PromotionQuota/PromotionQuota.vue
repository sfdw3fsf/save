<template src='./PromotionQuota.html'></template>
<style scoped src='./PromotionQuota.scss'></style>
<script>
import { mapActions } from "vuex";
import gridview from "../../../../../../components/Shared/gridview.vue";

export default {
  components: {
    gridview,
  },
  props: ["chitietkm_id"],
  name: "PromotionQuota",
  data: () => ({
    cuoc_sd_min: 0,
    cuoc_sd_max: 0,
    tien_sd_min: 0,
    tien_sd_max: 0,
    tyle_sd_min: 0,
    tyle_sd_max: 0,

    cuoc_tb_max: 0,
    tien_tb_min: 0,
    tien_tb_max: 0,
    cuoc_tb_min: 0,
    tyle_tb_min: 0,
    tyle_tb_max: 0,
  }),
  computed: {
    listQuota() {
      return JSON.parse(
        JSON.stringify(this.$store.getters["promotionDetail/listQuota"])
      );
    },
  },
  async created() {
    this.$root.showLoading(true);
    await this.loadQuota();
    this.$root.showLoading(false);
  },
  mounted() {
    console.log(this.chitietkm_id);
    console.log(this.listQuota);
    //     chitietkm_id: 15118
    // cuoc_sd_max: 0
    // cuoc_sd_min: 0
    // cuoc_tb_max: 0
    // cuoc_tb_min: 0
    // phanvung_id: 26
    // tien_sd_max: 0
    // tien_sd_min: 0
    // tien_tb_max: 0
    // tien_tb_min: 0
    // tyle_sd_max: 30
    // tyle_sd_min: 0
    // tyle_tb_max: 0
    // tyle_tb_min: 0
  },
  watch: {},
  methods: {
    ...mapActions("promotionDetail", [
      "updateQuotaByIDDetailPromotion",
      "getQuotaByIDDetailPromotion",
      "deleteQuotaByIDDetailPromotion",
    ]),
    async loadQuota() {
      await this.getQuotaByIDDetailPromotion({"p_param": this.chitietkm_id, "p_type":1}).then((result) => {
        const dataQuota = result.data.data;
        if (
          dataQuota && // 👈 null and undefined check
          Object.keys(dataQuota).length > 0
        ) {
          this.cuoc_sd_max = dataQuota[0].cuoc_sd_max;
          this.cuoc_sd_min = dataQuota[0].cuoc_sd_min;

          this.tien_sd_min = dataQuota[0].tien_sd_min;
          this.tien_sd_max = dataQuota[0].tien_sd_max;

          this.tyle_sd_min = dataQuota[0].tyle_sd_min;
          this.tyle_sd_max = dataQuota[0].tyle_sd_max;

          this.cuoc_tb_max = dataQuota[0].cuoc_tb_max;
          this.cuoc_tb_min = dataQuota[0].cuoc_tb_min;

          this.tien_tb_min = dataQuota[0].tien_tb_min;
          this.tien_tb_max = dataQuota[0].tien_tb_max;

          this.tyle_tb_min = dataQuota[0].tyle_tb_min;
          this.tyle_tb_max = dataQuota[0].tyle_tb_max;
        }
      });
    },
    handGhiLai(e) {
      e.preventDefault();
      if (this.tien_sd_min > this.tien_sd_max){
        this.$toast.error("Tiền sử dụng MIN không được phép lớn hơn Tiền sử dụng MAX")
        return;
      }
      if (this.cuoc_sd_min > this.cuoc_sd_max){
        this.$toast.error("Cước sử dụng MIN không được phép lớn hơn Cước sử dụng MAX")
        return;
      }
      if (this.tyle_sd_min > this.tyle_sd_max){
        this.$toast.error("Tỷ lệ sử dụng MIN không được phép lớn hơn Tỷ lệ sử dụng MAX")
        return;
      }
      if (this.tien_tb_min > this.tien_tb_max){
        this.$toast.error("Tiền TB MIN không được phép lớn hơn Tiền TB MAX")
        return;
      }
      if (this.tien_tb_min > this.tien_tb_max){
        this.$toast.error("Tiền TB MIN không được phép lớn hơn Tiền TB MAX")
        return;
      }
      if (this.cuoc_tb_min > this.cuoc_tb_max){
        this.$toast.error("Cước TB MIN không được phép lớn hơn Cước TB MAX")
        return;
      }
      if (this.tyle_tb_min > this.tyle_tb_max){
        this.$toast.error("Tỷ lệ TB MIN không được phép lớn hơn Tỷ lệ TB MAX")
        return;
      }

      this.$root.showLoading(true);
      const jsonData = {
        PHANVUNG_ID: 26,
        TIEN_SD_MIN: this.tien_sd_min,
        TIEN_SD_MAX: this.tien_sd_max,
        CUOC_SD_MIN: this.cuoc_sd_min,
        CUOC_SD_MAX: this.cuoc_sd_max,
        TYLE_SD_MIN: this.tyle_sd_min,
        TYLE_SD_MAX: this.tyle_sd_max,
        TIEN_TB_MIN: this.tien_tb_min,
        TIEN_TB_MAX: this.tien_tb_max,
        CUOC_TB_MIN: this.cuoc_tb_min,
        CUOC_TB_MAX: this.cuoc_tb_max,
        TYLE_TB_MIN: this.tyle_tb_min,
        TYLE_TB_MAX: this.tyle_tb_max,
      };
      const data = {
        chitietkm_id: this.chitietkm_id,
        json_data: JSON.stringify(jsonData).toString(),
      };
      this.updateQuotaByIDDetailPromotion(data)
        .then((response) => {
          if (response.data.message == "Thành công") {
            this.$toast.success("Cập nhập định mức khuyến mại thành công!!");
            this.loadQuota();
          }
        })
        .finally(() => {
          this.$root.showLoading(false);
        });
    },
    async handXoa(e) {
      e.preventDefault();

      const isConfirm = await this.$bvModal.msgBoxConfirm(
        `Bạn có thực sự muốn xóa định mức chi tiết khuyến mại không?`,
        {
          title: "",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        }
      );
      if (isConfirm) {
        this.$root.showLoading(true);
        await this.deleteQuotaByIDDetailPromotion({
          chitietkm_id: this.chitietkm_id,
        }).then((response) => {
          if (response.data.message == "Thành công") {
            this.$toast.success("Xóa định mức chi tiết khuyến mại thành công");
            this.loadQuota();
            this.$emit("close");
          }
        });

        this.$root.showLoading(false);
      }
    },
  },
};
</script>
