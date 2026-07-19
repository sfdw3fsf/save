<template>
  <div>
    <b-modal ref="popupChonTinh" size="lg" hide-header hide-header-close hide-footer body-class="p-0" no-close-on-backdrop @show="onLoad">
      <div class="modal-content popup-box">
        <div class="popup-header">
          <div class="title"><span class="icon one-notepad"></span> Chọn tỉnh danh mục hợp đồng đầu tư</div>
          <div class="close -ap icon-close" @click="hideModal"></div>
        </div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li @click="btnChapNhan_Click" :class="{ disabled: !btnChapNhan.Enabled }">
            <a style="cursor: pointer" :class="{ disabled: !btnChapNhan.Enabled }"> <span class="icon one-print"></span> Chấp nhận </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="info-row">
            <div class="key nowrap">
              <span class="name">Chọn tỉnh</span>
            </div>
            <div class="value">
              <div class="select-custom">
                <SelectExt class="select-custom" v-model="cboTinh.SelectedValue" :dataSource="cboTinh.Items" dataTextField="tentinh" dataValueField="tinh_id" @change="cboTinh_SelectedValueChanged"> </SelectExt>
              </div>
            </div>
          </div>
        </div>
      </div>
    </b-modal>
  </div>
</template>
<script>
import { Query } from '@syncfusion/ej2-data'

class Deffered {
  constructor() {
    this.promise = new Promise((resolve, reject) => {
      this.reject = reject
      this.resolve = resolve
    })
  }
}

export default {
  name: 'frmChonTinhHopDong',

  data() {
    return {
      cboTinh: {
        Items: [],
        SelectedValue: null
      },
      btnChapNhan: {
        Enabled: true
      },
      promise: new Deffered()
    }
  },

  methods: {
    async onLoad() {
      this.axios.post('web-hopdong/bss_group1/lay_ds_tinh_thicong_combobox', {}).then((rs) => {
        this.$set(this.cboTinh, 'Items', rs.data.data)
        if (this.cboTinh.Items.length > 0) {
          this.$set(this.cboTinh, 'SelectedValue', this.$auth.getPhanVungID())
        }
      })
    },
    filtering: function (args) {
      var query = new Query()
      query = args.text != '' ? query.where('tentinh', 'contains', args.text, false) : query
      args.updateData(this.cboTinh.Items, query)
    },
    showModal() {
      this.$refs.popupChonTinh.show()
      this.promise = new Deffered()
      return this.promise.promise
    },
    hideModal() {
      this.$refs.popupChonTinh.hide()
      this.promise.resolve(null)
    },
    btnChapNhan_Click() {
      this.$emit('onChapNhan', this.cboTinh.SelectedValue)
      this.$refs.popupChonTinh.hide()
      this.promise.resolve(this.cboTinh.SelectedValue)
    },
    cboTinh_SelectedValueChanged() {
      if (this.cboTinh.SelectedValue > 0) {
        this.$set(this.btnChapNhan, 'Enabled', true)
      }
    }
  }
}
</script>

<style scoped></style>
