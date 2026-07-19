<template>
  <b-modal
    ref="ref-modal"
    size="xl"
    no-close-on-backdrop
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    @hidden="onModalHidden"
  >
    <div class="modal-content popup-box hoso-dauchuyen-daunoi-thuebao">
      <div class="popup-header">
        <div class="title">Đấu nối thuê bao vào tuyến cáp</div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="hide"
        ></div>
      </div>
      <DauNoiThueBao
        :object="data"
        @NHAP_CAP_THUEBAO="onSuccessNhapCapThueBao"
      />
    </div>
  </b-modal>
</template>
<style>
.hoso-dauchuyen-daunoi-thuebao .page-content {
  overflow: visible !important;
  height: fit-content;
  background-color: white;
}
</style>
<script>
import DauNoiThueBao from "@/modules/CABMAN/DauNoiThueBao/index.vue";
export default {
  components: {
    DauNoiThueBao,
  },
  data() {
    return {
      data: null,
    };
  },
  methods: {
    show() {
      return this.$refs["ref-modal"].show();
    },
    hide() {
      this.$refs["ref-modal"].hide();
    },
    onModalHidden() {
      this.data = null;
      this.$emit("hidden");
    },
    setData(data) {
      this.data = data;
    },
    onSuccessNhapCapThueBao() {
      this.$emit("change");
    },
  },
};
</script>
