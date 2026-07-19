<template>
  <b-modal
    ref="popupNhapText"
    size="nhaptext-size"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Nhập text
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="popup-body">
        <div class="form-group">
          <label class="">{{ formProps.lblTieuDe2 }}</label>
          <b-form-input v-model="formProps.txtTenVT"></b-form-input>
        </div>
        <div class="form-group">
          <Select2 ref="cboKieuTbi" v-model="cboVatTu.value"
                   :options="cboVatTu.list" :settings="settingSelect2"/>
        </div>
        <div class="form-group">
          <div class="input-icon-right">
            <k-date-picker type="datetime" format="DD/MM/YYYY HH:mm"
                           v-model="formProps.dtpNgay"/>
          </div>
        </div>
        <div class="form-group">
          <label>{{ formProps.lblTieuDe }}</label>
          <b-form-input type="number" min="1" v-model="formProps.spMuc"></b-form-input>
        </div>
        <div class="form-group">
          <div class="list-checks  red normal pt-1">
            <div class="item red">
              <div class="check-action">
                <input type="radio" class="check" name="rdioCKb"
                       v-model="formProps.rdioCKb"
                       value="XoaToanBo">
                <span class="name" style="font-weight: 600 !important;">Xóa toàn bộ</span>
              </div>
            </div>
            <div class="item red">
              <div class="check-action">
                <input type="radio" class="check" name="rdioCKb"
                       v-model="formProps.rdioCKb"
                       value="ChiXoaTapDoan">
                <span class="name" style="font-weight: 600 !important;">Chi xóa tập đoàn</span>
              </div>
            </div>
          </div>
          <div class="form-group pt-2">
            <div class="lblCanhBao red">Vui lòng nhập thông tin vào ô trên trước khi nhấn xác nhận</div>
          </div>
        </div>
        <div class="form-group pull-right">
          <button class="btn btn-sm btn-primary">Xác nhận</button>
          <button class="btn btn-sm btn-primary">Hủy bỏ</button>
          <button class="btn btn-sm btn-primary">Bỏ qua</button>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'

export default {
  name: 'NhapText',
  components: {
    KDatePicker
  },
  data() {
    return {
      settingSelect2: {
        language: 'vi'
      },
      formProps: {
        txtTenVT: '',
        dtpNgay: new Date(),
        txtNhapText: '',
        spMuc: 0,
        rdioCKb: 'XoaToanBo',
        lblTieuDe: 'Tiêu đề',
        lblTieuDe2: 'Tiêu đề'
      },
      cboVatTu: {
        list: [],
        value: 0
      }
    }
  },
  methods: {
    hideModal() {
      this.$refs.popupNhapText.hide()
    },
    showModal() {
      this.$refs.popupNhapText.show()
    }
  }
}
</script>

<style>
.modal .modal-nhaptext-size {
  max-width: 30% !important;
}
</style>
