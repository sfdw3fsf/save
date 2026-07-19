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
      width='700px'
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
                    <div class="col-sm-6 col-6">
                      <ejs-dropdownlist :change="onKey1Change" :filtering='onFilteringColumn'
                            :allowFiltering="true" :dataSource='columns' :fields="configs.fieldsColumn"
                            v-model="params.key1" :placeholder="'Chọn loại thông tin'"></ejs-dropdownlist>
                    </div>
                    <div class="col-sm-6 col-6">
                      <ejs-dropdownlist :filtering='onFilteringValue1' :change="onValue1Change"
                              :allowFiltering="true" :dataSource='value1' :fields="configs.fieldsValue"
                              v-model="params.value1" :placeholder="'Chọn giá trị lọc'"></ejs-dropdownlist>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-6">
                      <ejs-dropdownlist :change="onKey2Change" :filtering='onFilteringColumn'
                            :allowFiltering="true" :dataSource='columns' :fields="configs.fieldsColumn"
                            v-model="params.key2" :placeholder="'Chọn tiêu chí'"></ejs-dropdownlist>
                    </div>
                    <div class="col-sm-6 col-6">
                      <ejs-dropdownlist :filtering='onFilteringValue2' :change="onValue2Change"
                              :allowFiltering="true" :dataSource='value2' :fields="configs.fieldsValue"
                              v-model="params.value2" :placeholder="'Chọn giá trị lọc'"></ejs-dropdownlist>
                    </div>
                </div>
            </div>
        </div> <!-- end body -->
    </div>
    </ejs-dialog>
  </template>
<script>

import Vue from 'vue'
import { Query } from '@syncfusion/ej2-data'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'

Vue.use(DialogPlugin)
export default Vue.extend({
  components: {
  },
  props: {
    columns: {
      type: Array,
      required: false
    },
    value1: {
      type: Array,
      required: false
    },
    value2: {
      type: Array,
      required: false
    },
    value3: {
      type: Array,
      required: false
    }
  },
  computed: {

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
        key1: null,
        value1: null,
        key2: null,
        value2: null,
        key3: null,
        value3: null
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
      this.params.key1 = null
      this.params.value1 = null
      this.params.key2 = null
      this.params.value2 = null
      this.params.key3 = null
      this.params.value3 = null
    },
    onFilteringColumn (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('headerText', 'contains', e.text, true) : query
      e.updateData(this.params.p_quan_options, query)
    },
    onKey1Change (args) {
      this.params.value1 = null
      this.params.value2 = null
      let data = []
      if (args.value !== null) {
        data.push({ key: args.value })
      }
      this.$emit('onFilter', data)
    },
    onKey2Change (args) {
      let data = []
      if (this.params.key1 !== null && this.params.value1 !== null) {
        data.push({ key: this.params.key1, value: this.params.value1 })
      }
      if (args.value !== null) {
        data.push({ key: args.value })
      }
      this.$emit('onFilter', data)
    },
    onKey3Change (args) {
      const data = {
        key: args.value,
        index: 3
      }
      // console.log(args)
      this.$emit('onFilter', data)
    },
    onFilteringValue1 (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('value', 'contains', e.text.trim(), true) : query
      e.updateData(this.value1, query)
    },
    onFilteringValue2 (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('value', 'contains', e.text.trim(), true) : query
      e.updateData(this.value2, query)
    },

    onValue1Change (e) {
      this.params.value2 = null
      let data = []
      if (this.params.key1 !== null) {
        data.push({ key: this.params.key1, value: e.value })
      }
      this.$emit('onAccept', data)
    },
    onValue2Change (e) {
      let data = []
      if (this.params.key1 !== null) {
        data.push({ key: this.params.key1, value: this.params.value1 })
      }
      if (this.params.key2 !== null) {
        data.push({ key: this.params.key2, value: e.value })
      }
      this.$emit('onAccept', data)
    },
    doAccept () {
      // let data = []
      // if (this.params.key1 !== null) {
      //   data.push({ key: this.params.key1, value: this.params.value1 })
      // }
      // if (this.params.key2 !== null) {
      //   data.push({ key: this.params.key2, value: this.params.value2 })
      // }
      // this.$emit('onAccept', data)
      this.$refs.dlgFilterDialog.hide()
    },
    doClear () {
      this.$emit('onAccept', null)
      this.$refs.dlgFilterDialog.hide()
    }
  }
    
})
</script>
