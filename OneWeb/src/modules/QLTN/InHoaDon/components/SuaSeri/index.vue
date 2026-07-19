<template>
  <ejs-dialog
    :enableResize="true"
    :allowDragging="true"
    :visible="false"
    ref="dlgSuaSeriIHD"
    :header="'Nhập thông tin'"
    :animationSettings="animationSettings"
    showCloseIcon="true"
    width="20%"
    :target="target"
    :close="reset"
    :isModal="true"
    :zIndex="1"
  >
    <div class="popup-body mart10">
      <div class="info-row">
        <div class="key w80">Số thùng<span class="required">*</span></div>
        <div class="value">
          <input
            ref="soThungRef"
            type="number"
            class="form-control"
            v-model="dataSeri.soThung"
          />
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Seri<span class="required">*</span></div>
        <div class="value">
          <input    ref="seriRef" type="text" class="form-control" v-model="dataSeri.seri" />
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Số seri<span class="required">*</span></div>
        <div class="value">
          <input ref="soSeriRef"  type="text" class="form-control" v-model="dataSeri.soSeri" />
        </div>
      </div>
      <div class="group-buttons -bottom center">
        <button
          class="btn btn-main"
          style="min-width: 130px"
          @click="btnXacNhan"
        >
          Xác nhận
        </button>
        <button
          class="btn btn-second"
          style="min-width: 130px"
          @click="dialogClose"
        >
          Huỷ bỏ
        </button>
      </div>
    </div>
  </ejs-dialog>
</template>

<script>
export default {
  data() {
    return {
      target: ".main-wrapper",
      animationSettings: { effect: "Zoom" },
      dataSeri: {
        soThung: null,
        seri: null,
        soSeri: null,
      },
    };
  },
  methods: {
    dialogOpen() {
      this.$refs.dlgSuaSeriIHD.show();
      console.log(this.dataSeri);
    },
    reset() {
      this.dataSeri = {
        soThung: null,
        seri: null,
        soSeri: null,
      };
    },
    dialogClose() {
      this.$refs.dlgSuaSeriIHD.hide();
      this.reset();
    },
    btnXacNhan() {
      console.log(this.dataSeri.soThung);
      if (!this.dataSeri.soThung) {
        this.$refs.soThungRef.focus();
        return this.$toast.error("Bạn chưa nhập số thùng");
      }
      if (!this.dataSeri.seri) {
            this.$refs.seriRef.focus();
        return this.$toast.error("Bạn chưa nhập Seri");
      }
      if (!this.dataSeri.soSeri) {
            this.$refs.soSeriRef.focus();
        return this.$toast.error("Bạn chưa nhập số Seri");
      }
      this.$emit("dataInputChanged", this.dataSeri);
    },
  },
};
</script>

<style src="./index.scss"></style>
