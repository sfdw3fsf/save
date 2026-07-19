<template>
  <ejs-dialog
    :enableResize="true"
    :allowDragging="true"
    :visible="false"
    :buttons="buttons"
    ref="dialog_chuyen_theo_doi"
    showCloseIcon="true"
    width="40%"
    target="#app .main-wrapper"
    header="Nhập thông tin"
  >
    <div class="popup-body">
      <div class="box-form">
        <div class="row">
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w60">Số ngày</div>
            </div>
          </div>
          <div class="col-sm-8 col-12">
            <div class="info-row">
              <input type="number" class="form-control" v-model="songay" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>

<script>
export default {
  props: ["value"],
  data() {
    return {
      songay: null,
      buttons: [
        {
          click: this.OK,
          buttonModel: { content: "OK", isPrimary: true },
        },
        { click: this.hide, buttonModel: { content: "Cancel" } },
      ],
    };
  },
  methods: {
    showDialog() {
      this.$refs.dialog_chuyen_theo_doi.show();
    },
    hide() {
      this.$refs.dialog_chuyen_theo_doi.hide();
    },
    OK() {
      this.$emit("input", this.songay);
      this.$emit("OK");
    },
  },
};
</script>

<style>
</style>