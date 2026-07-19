<template>
  <ejs-dialog
    :enableResize='false'
    :allowDragging='true'
    :visible='false'
    ref="dlgNhapThongTinTT"
    :header='"Nhập thông tin"'
    showCloseIcon=true
    width='500'
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="modal-content popup-box">
      <div class="popup-body">
          <div class="box-form">
              <div class="info-row">
                  <div class="key w60">Mã TT</div>
                  <div class="value">
                      <input type="text" @keyup.enter="doSearch(1)" class="form-control" v-model="params.p_ma_tt">
                  </div>
              </div>
              <div class="info-row">
                  <div class="key w60">Mã ĐD</div>
                  <div class="value">
                      <input type="text" @keyup.enter="doSearch(2)" class="form-control" v-model="params.p_ma_dd">
                  </div>
              </div>
              <div class="group-buttons -bottom center">
                  <button class="btn btn-main" @click="doSearch">OK</button>
                  <button class="btn btn-second" @click="doCancel">Cancel</button>
              </div>
          </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import Vue from "vue"
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import EventBus from '@/utils/eventBus'
import { DialogUtility } from '@syncfusion/ej2-popups'
Vue.use(DialogUtility)
let DialogObj = undefined

Vue.use(DialogPlugin)

 export default Vue.extend({
    components : {
    },
    props: {
    },
    computed : {
    },
    async created () {
    },
    destroyed () {
    },
    data () {
      return {
        params: {
          p_ma_tt: null,
          p_ma_dd: null,
          p_type: 1,
        },
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' }
      }
    },
    methods : {
      doSearch: function(type) {
        let keyword = ''
        if(type === 1)
        {
          if(this.params.p_ma_tt === null || ('' + this.params.p_ma_tt).trim() === '')
          {
            this.$toast.error('Trường Mã TT bắt buộc nhập.')
            return
          }
          keyword = ('' + this.params.p_ma_tt).trim()
          this.params.p_type = 1
        } else if(type === 2)
        {
          if(this.params.p_ma_dd === null || ('' + this.params.p_ma_dd).trim() === '')
          {
            this.$toast.error('Trường Mã ĐD bắt buộc nhập.')
            return
          }
          keyword = ('' + this.params.p_ma_dd).trim()
          this.params.p_type = 2
        } else {
          if(
            (this.params.p_ma_tt === null || ('' + this.params.p_ma_tt).trim() === '')
            &&
            (this.params.p_ma_dd === null || ('' + this.params.p_ma_dd).trim() === '')
          )
          {
            this.$toast.error('Trường Mã ĐD hoặc Mã TT bắt buộc nhập.')
            return
          }
          if(('' + this.params.p_ma_tt).trim() !== '')
          {
            keyword = ('' + this.params.p_ma_tt).trim()
            this.params.p_type = 1
          }else
          {
            keyword = ('' + this.params.p_ma_dd).trim()
            this.params.p_type = 2
          }
        }
        let postData = {
          keyword: keyword,
          type: this.params.p_type
        }
        this.$emit('searchThanhToan', postData);
      },
      doCancel: function(){
        this.$refs.dlgNhapThongTinTT.hide()
      },
      openDialog() {
        this.$refs.dlgNhapThongTinTT.show()
      },
      closeDialog() {
        this.$refs.dlgNhapThongTinTT.hide()
      },
      onDialogClose() {
      },
      onDialogOpen() {
      },
      clearForm() {

      },
      clearResult() {

      },

    },
  })
</script>
