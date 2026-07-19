<template>
    <ejs-dialog
      :enableResize='true'
      :allowDragging='true'
      :visible='false'
      :isModal="true"
      :animationSettings='animationSettings'
      ref="dlgFilterDialog"
      :header='"Lọc kết quả tìm kiếm"'
      showCloseIcon=true
      width='800px'
      height='300'
      :target='target'
      :close="onDialogClose"
      :open="onDialogOpen"
      >
    <div class="modal-content popup-box">
        <div class="popup-top-action">
          <div class="acept item pointer" v-on:click.prevent="doAccept">
              <span class="icon -ap icon-ion-ios-checkmark"></span> Chấp nhận
          </div>
          <div class="acept item pointer" v-on:click.prevent="doClear">
              <span class="icon -ap icon-ion-ios-checkmark"></span> Huỷ bỏ
          </div>
        </div>
        <div class="popup-body">
            <div class="box-form">
                <div class="row">
                    <div class="col-sm-4 col-4">
                      <ejs-dropdownlist :dataSource='columns' :fields="configs.fieldsColumn"
                            v-model="params.key1" :placeholder="'Chọn loại thông tin'"></ejs-dropdownlist>
                    </div>
                    <div class="col-md-4 col-xs-12 col-12">
                          <div class="info-row">
                              <div class="key">
                                  <div class="">
                                    <b-form-checkbox
                                      v-model = "params.p_chk_tutien"
                                      class = "check"
                                      value = '1'
                                      unchecked-value = '0'
                                    >
                                      <span class="name">Từ tiền</span>
                                    </b-form-checkbox>
                                  </div>
                              </div>
                              <div class="value">
                                  <ejs-numerictextbox locale="vi-VN" :enabled="chk_tutien" style="padding-right:15px" :showSpinButton="false" class="red form-control tright" v-model="$data.params.p_tutien" format="N0"></ejs-numerictextbox>
                              </div>
                          </div>
                      </div>
                      <div class="col-md-4 col-xs-12 col-12">
                          <div class="info-row">
                              <div class="key">
                                  <div class="">
                                    <b-form-checkbox
                                      v-model = "params.p_chk_dentien"
                                      class = "check"
                                      value = '1'
                                      unchecked-value = '0'
                                    >
                                      <span class="name">Đến tiền</span>
                                    </b-form-checkbox>
                                  </div>
                              </div>
                              <div class="value">
                                  <ejs-numerictextbox locale="vi-VN" :enabled="chk_dentien" style="padding-right:15px" :showSpinButton="false" class="red form-control tright" v-model="$data.params.p_dentien" format="N0"></ejs-numerictextbox>

                              </div>
                          </div>
                      </div>
                </div>
            </div>
        </div> <!-- end body -->
    </div>
    </ejs-dialog>
  </template>
<script>

import Vue from 'vue'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'

Vue.use(DialogPlugin)
export default Vue.extend({
  components: {
  },
  props: {
    columns: {
      type: Array,
      required: false
    }
  },
  computed: {
    chk_dentien () {
      return this.params.p_chk_dentien === '1'
    },
    chk_tutien () {
      return this.params.p_chk_tutien === '1'
    }
  },
  async created () {
  },
  mounted () {
  },
  destroyed () {
  },
  data () {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      configs: {
        fieldsColumn: { text: 'headerText', value: 'fieldName' },
        fieldsValue: { text: 'value', value: 'value' }
      },
      params: {
        p_chk_tutien: '1',
        p_chk_dentien: '1',
        p_tutien: 0,
        p_dentien: 0,
        key1: null,
        value1: null
      }
    }
  },
  methods: {
    openDialog () {
      // console.log(this.columns)
      this.$refs.dlgFilterDialog.show()
    },
    closeDialog () {
      this.$refs.dlgFilterDialog.hide()
    },
    onDialogClose () {
    },
    async onDialogOpen () {
      this.clearData()
    },
    clearData () {
    },
    doAccept () {
      let data = {}
      if (this.params.key1 === null) {
        this.$toast.error('Chưa chọn điều kiện lọc.')
        return
      }
      if (this.params.p_chk_tutien !== '1' && this.params.p_chk_dentien !== '1') {
        this.$toast.error('Chưa chọn điều kiện lọc.')
        return
      }
      if (this.params.p_chk_tutien === '1' && this.params.p_chk_dentien === '1' && this.params.p_tutien > this.params.p_dentien) {
        this.$toast.error('Giá trị tiền không hợp lệ.')
        return
      }
      data = {
        keyword: this.params.key1,
        tu_tien: this.params.p_chk_tutien === '1' ? this.params.p_tutien : null,
        den_tien: this.params.p_chk_dentien === '1' ? this.params.p_dentien : null
      }
      this.$emit('onAccept', data)
      this.$refs.dlgFilterDialog.hide()
    },
    doClear () {
      this.$emit('onAccept', null)
      this.$refs.dlgFilterDialog.hide()
    }
  }

})
</script>
