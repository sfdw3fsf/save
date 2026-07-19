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
      height='400'
      :target='target'
      :close="onDialogClose"
      :open="onDialogOpen"
      >
    <div class="modal-content popup-box">
        <div class="popup-top-action">
          <div class="acept item pointer" v-on:click.prevent="doAccept">
              <span class="icon -ap icon-ion-ios-checkmark"></span> Chấp nhận
          </div>
        </div>
        <div class="popup-body">
            <div class="box-form">
                <div class="row">
                    <div class="col-sm-12 col-12">
                        <div class="info-row">
                            <div class="key w100"></div>
                            <div class="value" style="color:blue"></div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="info-row">
                            <div class="key w100">Tỉnh/TP <span style="color: red">*</span></div>
                            <div class="value">
                                <ejs-dropdownlist :change="onTinhChange" :filtering='onFilteringTinh'
                                    :allowFiltering="true" :dataSource='params.p_tinh_options' :fields="configs.fieldsTinh"
                                    v-model="diachi.tinh_id" :placeholder="'Chọn tỉnh'"></ejs-dropdownlist>
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
    }
  },
  methods: {
    openDialog () {
      this.$refs.dlgFilterDialog.show()
    },
    closeDialog () {
      this.$refs.dlgFilterDialog.hide()
    },
    onDialogClose () {
    },
    async onDialogOpen () {
        
    },
    clearData () {

    }
},
  watch: {}
})
</script>
