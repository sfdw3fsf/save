<template>
  <ejs-dialog :enableResize="true" :isModal="true" :allowDragging="true" :visible="false" ref="frmHuyDuyetCAOnline" :header="'Hủy duyệt CA Online'" showCloseIcon="true" width="50%" target="#app .main-wrapper">
    <div class="">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript:;" @click="btnHuyDuyet_Click"> <span class="icon one-file-arrow-right1"></span> Hủy duyệt </a>
          </li>
        </ul>
      </div>
      <div class="row">
        <div class="col-3">
          <div class="row">
            <div class="col-xs-6 col-sm-6 col-lg-6 col-md-6"><b>Lý do hủy</b></div>
          </div>
        </div>
        <div class="col-8">
          <input type="text" class="form-control" v-model="lydohuy" />
        </div>
        <div class="col-1"></div>
      </div>
    </div>
  </ejs-dialog>
</template>

<script>
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment'
import axios from 'axios'
import api from '@/modules/contract/complete_profile/CompleteProfileITVAS/API/CompleteProfileAPI.js'

import smartca from '../../API/smartCAFunction'
import { DataManager, JsonAdaptor } from '@syncfusion/ej2-data'
import { ComboBoxPlugin } from '@syncfusion/ej2-vue-dropdowns'
import { TextBox } from '@syncfusion/ej2-vue-inputs'
//Vue.use(ComboBoxPlugin);
export default {
  components: {
    moment,
    DatePicker,
    api
  },
  name: 'HuyDuyetCAOnline',
  data() {
    let self = this
    // console.log(self.$root.token);
    return {
      hdtb_id: 0,
      lydohuy: ''
    }
  },
  methods: {
    async btnHuyDuyet_Click() {
      let self = this
      let kq = await smartca.HuyDuyetSmartCAOnline(axios, self.hdtb_id, self.lydohuy)
      self.$toast.error(json.stringify(kq))
    },
    openDialog(dulieu) {
      this.resetForm()
      this.hdtb_id = dulieu.hdtb_id
      this.$refs.frmHuyDuyetCAOnline.show()
    },
    resetForm() {}
  }
}
</script>

<style></style>
