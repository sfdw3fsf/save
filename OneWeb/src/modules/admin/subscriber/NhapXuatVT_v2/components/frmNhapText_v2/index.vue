<template>
  <div>
    <div class="popup-body frmNhapText-content">
      <div v-show="lblTieuDe2.visible" class="">{{ lblTieuDe2.value }}</div>
      <input v-show="txtTenVT.visible" v-model="lblTieuDe2.value" type="text" />
      <SelectExt v-show="cboVatTu.visible" ref="cboVatTu" :disabled="!cboVatTu.enabled" v-model="cboVatTu.value" :dataSource="cboVatTu.list" dataTextField="loaiCt" dataValueField="loaiCtId" />
      <vue-date v-show="dtpNgay.visible" v-model="dtpNgay.value" format="DD/MM/YYYY HH:mm:ss" type="datetime" />
      <div v-show="lblTieuDe.visible" class="">{{ lblTieuDe.value }}</div>
      <input v-show="spMuc.visible" v-model="spMuc.value" type="text" />
      <input v-show="txtNhapText.visible" v-model="txtNhapText.value" type="text" />
      <div v-show="rdioCKb.visible">
        <div class="red">
          <div v-for="(item, index) in rdioCKb.list" :key="index" class="item my-2">
            <div class="check-action">
              <input type="radio" v-model="rdioCKb.value" name="delete-type" class="check" checked :value="item.value" />
              <span class="name">{{ item.text }}</span>
            </div>
          </div>
        </div>
      </div>
      <div v-show="lblCanhBao.visible" class="">{{ lblCanhBao.value }}</div>
    </div>
    <div class="popup-footer d-flex justify-content-end py-3">
      <button v-show="btnHuyBo.visible" :disabled="!btnHuyBo.enabled" class="btn-nhap-text btn btn-outline-primary mr-3" @click="btnHuyBo_Click">Hủy</button>
      <button v-show="btnXacNhan.visible" :disabled="!btnXacNhan.enabled" class="btn-nhap-text btn btn-outline-danger mr-4 btn-main" @click="btnXacNhan_Click">Xác nhận</button>
      <button v-show="btnHuyThat.visible" :disabled="!btnHuyThat.enabled" class="btn-nhap-text btn btn-outline-primary" @click="btnHuyThat_Click">Bỏ qua</button>
    </div>
  </div>
</template>
<script>
import Vue from 'vue'
export default {
  emits: ['onBtnHuyClicked', 'onBtnXacNhanClicked'],
  props: {
    vkieu_nhap: {
      type: [String],
      required: true
    },
    tieuDe: {
      type: [String],
      required: false
    }
  },
  data() {
    return {
      lblTieuDe2: { visible: true, enabled: true, value: null },
      txtTenVT: { visible: true, enabled: true, value: null },
      cboVatTu: { visible: true, enabled: true, list: [], value: null },
      dtpNgay: { visible: true, enabled: true, value: null },
      lblTieuDe: { visible: true, enabled: true, value: null },
      spMuc: { visible: true, enabled: true, value: null },
      txtNhapText: { visible: true, enabled: true, value: null },
      rdioCKb: {
        visible: true,
        enabled: true,
        list: [
          { text: 'Xóa toàn bộ', value: 1 },
          { text: 'Chỉ xóa tập đoàn', value: 2 },
          { text: 'Chỉ xóa ở IMS', value: 3 }
        ],
        value: null
      },
      lblCanhBao: { visible: true, enabled: true, value: null },
      btnXacNhan: { visible: true, enabled: true, value: null },
      btnHuyBo: { visible: true, enabled: true, value: null },
      btnHuyThat: { visible: true, enabled: true, value: null }
    }
  },
  methods: {
    async frmNhapText() {
      if (this.vkieu_nhap == 'nhap_text') {
        this.lblTieuDe.value = this.tieuDe
        this.lblTieuDe2.visible = false
        this.dtpNgay.visible = false
        this.cboVatTu.visible = false
        this.spMuc.visible = false
        this.txtTenVT.visible = false
        this.rdioCKb.visible = false
      }
    },
    async btnXacNhan_Click() {
      let result = {}
      switch (this.vkieu_nhap) {
        case 'nhap_text':
          result.Output = this.txtNhapText.value
          break
      }
      // this.$emit('onBtnXacNhanClicked', { vkieu_luachon: this.rdioCKb.value })
      this.$emit('onBtnXacNhanClicked', result)
    },
    async btnHuyBo_Click() {
      this.$emit('onBtnHuyClicked')
    },
    async btnHuyThat_Click() {}
  },
  async mounted() {
    // this.loading(true)
    // setTimeout(() => {
    //   this.showview = true
    //   this.loading(false)
    // }, 500)
    await this.frmNhapText()
  }
}
</script>
<style scoped>
.frmNhapText-content {
  display: flex;
  flex-direction: column;
}
.btn-nhap-text {
  width: 120px !important;
}
</style>
