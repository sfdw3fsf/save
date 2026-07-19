<template>
  <b-modal
    ref="popupDoiNe"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Đổi NE
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="popup-body">
        <div class="info-row">
          <div class="key w90">Số máy/Acc</div>
          <div class="value">
            <input type="text" ref="txtMaTB" v-model="matb"
                   class="form-control">
          </div>
        </div>
        <div class="info-row">
          <div class="key w90">Số NE cũ</div>
          <div class="value">
            <input type="text" ref="txtSoNeCu"
                   class="form-control">
          </div>
        </div>
        <div class="info-row">
          <div class="key w90">Số NE mới</div>
          <div class="value">
            <input type="text" ref="txtSoNeMoi" v-model="txtSoNeMoi" class="form-control">
          </div>
        </div>
        <div class="info-row">
          <b-button variant="outline-primary" class="pull-right" @click="changeNe">Đổi NE</b-button>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
export default {
  name: 'DuAnHDTB'
}
</script>

<style scoped>

</style>
