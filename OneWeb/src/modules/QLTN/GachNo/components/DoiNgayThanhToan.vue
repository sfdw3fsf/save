<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :animationSettings='animationSettings'
    ref="dlgThayDoiNgayThanhToan"
    :header='"Thay đổi ngày thanh toán"'
    showCloseIcon=true
    width='600px'
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="modal-content popup-box">
      <div class="list-actions-top">
          <ul class="list">
              <li>
                  <a href="#" v-on:click.prevent="doAccept()">
                      <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận(F5)
                  </a>
              </li>
          </ul>
      </div>
      <div class="popup-body">
          <div class="box-form">
              <div class="popup-tdntt">
                  <div class="icon">
                      <img src="@/assets/img/calendar-full.svg" alt="">
                  </div>
                  <div class="tdntt-content">
                      <span class="note red fw6  block">
                          <span class="-ap icon-warning3 text-warning f20 mar10"></span> Chỉ được chuyển trong cùng kỳ hóa đơn
                      </span>
                      <div class="row">
                          <div class="col-md-8 col-12">
                              <div class="info-row">
                                  <div class="key text-main">Ngày TT cũ</div>
                                  <div class="value">
                                    <ejs-datepicker
                                      :value="params.p_ngay_ttoan_old"
                                      :format="configs.dateConfig.format"
                                      :allowEdit=false
                                      :showClearButton=false
                                      >
                                    </ejs-datepicker>
                                  </div>
                              </div>
                          </div>
                          <div class="col-md-4 col-12">
                              <div class="label-text text-main">Kỳ:022021. Lần: 01</div>
                          </div>
                      </div>
                      <div class="row">
                          <div class="col-md-8 col-12">
                              <div class="info-row">
                                  <div class="key red">Ngày TT mới</div>
                                  <div class="value">
                                    <ejs-datepicker
                                      v-model="p_ngay_ttoan_new"
                                      :value="params.p_ngay_ttoan_new"
                                      :format="configs.dateConfig.format"
                                      :allowEdit=false
                                      :showClearButton=false
                                      >
                                    </ejs-datepicker>
                                  </div>
                              </div>
                          </div>
                          <div class="col-md-4 col-12">
                              <div class="label-text red">Kỳ:022021. Lần: 01</div>
                          </div>
                      </div>
                      <div class="row">
                          <div class="col-md-8 col-12">
                              <div class="info-row">
                                  <div class="key">Mật khẩu</div>
                                  <div class="value">
                                      <input type="text" placeholder="" class="form-control highlight">
                                  </div>
                              </div>
                          </div>
                          <div class="col-md-4 col-12">
                          </div>
                      </div>
                  </div>
              </div>
          </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import Vue from "vue"
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import moment from 'moment'
import GachNoAPI from '../../api/GachNoAPI'

Vue.use(DialogPlugin)

 export default Vue.extend({
    components : {
    },
    props: {
      phieu_id: {
        type: Number
      },
      ngay_ttoan : {
        type: String
      },
      kycuoc: {
        type: String
      }
    },
    computed :{

      p_ngay_ttoan_new : {
        get() {
          return this.params.p_ngay_ttoan_new
        },
        set(value) {
          return this.params.p_ngay_ttoan_new = value
        }
      },
    },
    async created () {

    },
    destroyed () {
    },
    data () {
      return {
        configs: {
          dateConfig: {
            value: new Date(),
            format: 'dd/MM/y',
            max: new Date()
          },
        },
        params: {
          p_ngay_ttoan_new: new Date(),
          p_ngay_ttoan_old: null,
        },
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' }
      }
    },
    methods : {
      async getTongNoTheoKyHoaDon() {
        try{
          let postData = {
            pKyCuoc: this.kycuoc,
            pMaTT: this.ma_tt
          }
          const results = await GachNoAPI.getTongNoTheoKyHoaDon(this.axios, postData)
          return results
        }catch(err) {
          console.log(err)
        }
      },
      openDialog() {
        this.params.p_ngay_ttoan_old = moment(this.ngay_ttoan, 'DD/MM/YYYY').toDate()
        this.$refs.dlgThayDoiNgayThanhToan.show()
      },
      closeDialog() {
        this.$refs.dlgThayDoiNgayThanhToan.hide()
      },
      onDialogClose() {
        this.clearForm()
        this.clearResult()
      },
      onDialogOpen() {

      },
      clearForm() {

      },
      clearResult() {

      },
      doAccept: function(){
        let postData = {
          "pKyCuoc": this.kycuoc,
          "pNgayTT": moment(this.params.p_ngay_ttoan_new).format('DD/MM/YYYY'),
          "pPhieuId": this.phieu_id
        }

        this.loading(true)

        GachNoAPI.updateNgayTT(this.axios, postData)
        .then(
          (res) => {
            if(res.data.error_code === 'BSS-00000000')
            {
              this.$toast.success('Cập nhật phiếu ' + this.phieu_id + ' thành công')
              this.$emit("updateNgayTToan", this.phieu_id);
            } else
            {
              this.$emit("updateNgayTToan", 0);
            }

          }
        ).catch(error => {
          console.log(error)
          this.$toast.error(error.data.messsage_detail)
          this.$emit("updateNgayTToan", 0);
        })
        .finally(() => {
          this.loading(false)
          this.$refs.dlgThayDoiNgayThanhToan.hide()
        })


      }
    },
    watch : {

    }
  })
</script>
