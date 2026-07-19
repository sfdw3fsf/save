<template>
  <b-modal
    size="xl"
    dialog-class="dialog-content"
    body-class="body-content"
    content-class="popup-box"
    v-model="visible"
    hide-footer
    hide-header
  >
    <div style="margin: -1rem">
      <NguoiGioiThieu
        @acceptChangeNGT="onAccept"
        @PopUpNguoiGioiThieu_Thoat="show(false)"
      />
    </div>
  </b-modal>
</template>
<script>
import NguoiGioiThieu from "../../../../setup/ChangeContractOwner/popup/NguoiGioiThieu.vue";
export default {
  components: {
    NguoiGioiThieu
  },
  data: () => ({
    visible: false,
    source: null
  }),
  methods: {
    show(visible, source) {
      this.visible = visible;
      this.source = source;
    },
    onAccept(data) {
      console.log("data:", data);
      this.$emit("onAccept", { source: this.source, data });
      this.show(false);
    }
  }
};
</script>
