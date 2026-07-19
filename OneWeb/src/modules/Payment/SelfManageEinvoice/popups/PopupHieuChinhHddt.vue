<template>
  <b-modal
    ref="hieuChinhHDDTRef"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Hiệu chỉnh hóa đơn điện tử
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a @click="onPrint">
              <span class="icon one-print"></span>In phiếu
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form" v-html="html"></div>
      </div>
    </div>
  </b-modal>
</template>

<script>
export default {
  name: 'PopupHieuChinhHddt',
  props: ['html'],
  methods: {
    showModal () {
      this.$refs.hieuChinhHDDTRef.show()
    },
    hideModal () {
      this.$refs.hieuChinhHDDTRef.hide()
    },
    onPrint () {
      const w = window.open('')
      w.document.write(this.html)
      w.document.close()
      w.focus()
      w.print()
    }
  }
}
</script>
