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
  name: 'frmDoiNeHNI',
  props: ['hdtb_id', 'sone_cu', 'ma_tb'],
  data () {
    return {
      txtSoNECu: '',
      txtSoNeMoi: '',
      matb: '',
      hdtbid: 0
    }
  },
  methods: {
    async changeNe () {
      if (this.txtSoNeMoi === '') {
        this.$root.$toast.warning('Bạn chưa nhập số Ne mới !')
        return
      }
      let request = {
        hdtb_id: Number.parseInt(this.hdtb_id),
        p_ne: this.txtSoNeMoi.trim()
      }
      await this.axios.post('/web-hopdong/thaydoiloaihinhtb/update_ne_hdtb_cd', request)
        .then(response => {
          console.log(response)
        })
      this.hideModal()
    },
    showModal () {
      this.$refs.popupDoiNe.show()
    },
    handleShowModal () {
      this.hdtbid = this.hdtb_id ? this.hdtbid : 0
      this.txtSoNECu = this.sone_cu ? this.sone_cu : ''
      this.matb = this.ma_tb ? this.ma_tb : ''
    },
    hideModal () {
      this.matb = ''
      this.hdtbid = 0
      this.txtSoNECu = ''
      this.txtSoNeMoi = ''
      this.$emit('popupDoiNeClosed')
      this.$refs.popupDoiNe.hide()
    }
  }
}
</script>

<style scoped>

</style>
