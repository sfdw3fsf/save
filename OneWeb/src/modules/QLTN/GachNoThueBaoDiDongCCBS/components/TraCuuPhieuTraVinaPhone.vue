<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :animationSettings='animationSettings'
    ref="dlgTraCuuPhieuTraVinaPhone"
    :header='"Tra cứu thông tin phiếu trả di động"'
    showCloseIcon=true
    width='1300px'
    height='700'
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="modal-content popup-box">
      <div class="popup-top-action">
        <div class="acept item" v-on:click.prevent="doAccept">
            <span class="icon -ap icon-ion-ios-checkmark"></span> Tìm kiếm
        </div>
        <div class="acept item" v-on:click.prevent="doAccept">
            <span class="icon -ap icon-ion-ios-checkmark"></span> Gạch nợ VNP
        </div>
        <div class="acept item" v-on:click.prevent="doAccept">
            <span class="icon -ap icon-ion-ios-checkmark"></span> Đồng bộ
        </div>
        <div class="acept item" v-on:click.prevent="doAccept">
            <span class="icon -ap icon-ion-ios-checkmark"></span> Xóa phiếu
        </div>

      </div>
      <div class="popup-body">
        <!-- -->
        <div class="row">
          <div class="col-sm-4 col-12">
              <div class="box-form">
                  <div class="legend-title padt10">Thông tin tra cứu</div>
                  <div class="info-row">
                      <div class="key w120">
                          <div class="check-action">
                            <input name="p_loai_timkiem" type="radio" value="0" class="check" v-model="params.p_loai_timkiem">
                            <span class="name">Mã TT</span>
                          </div>
                      </div>
                      <div class="value">
                          <div class="input-icon-right">
                              <input type="text" @focus="changeSearchType(0)" @keyup.enter="doSearchByMaTT" class="form-control highlight" v-model="params.p_ma_tt">
                              <span class="icon nc-icon-outline ui-1_zoom"></span>
                          </div>
                      </div>
                  </div>
                  <div class="info-row">
                      <div class="key w120">
                          <div class="check-action">
                            <input name="p_loai_timkiem" type="radio" value="1" class="check" v-model="params.p_loai_timkiem">
                            <span class="name">Số máy (84)</span>
                          </div>
                      </div>
                      <div class="value">
                        <input type="text" @focus="changeSearchType(1)" @keyup.enter="doSearchBySM" class="form-control highlight" v-model="params.p_so_dd">
                      </div>
                  </div>
                  <div class="info-row">
                      <div class="key w120">
                          <div class="check-action">
                            <input name="p_loai_timkiem" type="radio" value="2" class="check" v-model="params.p_loai_timkiem">
                            <span class="name">User Neo</span>
                          </div>
                      </div>
                      <div class="value">
                        <input type="text" @focus="changeSearchType(2)" class="form-control highlight">
                      </div>
                  </div>
                  <div class="info-row">
                      <div class="key w120">
                          Số phiếu
                      </div>
                      <div class="value">
                        <vue-numeric class="form-control tright red" currency="" separator="," v-model="$data.params.p_tong_no"></vue-numeric>
                      </div>
                  </div>

              </div>
          </div>
          <div class="col-sm-4 col-12">
              <div class="box-form">
                  <div class="legend-title padt10">Thông tin thanh toán</div>
                  <div class="info-row">
                      <div class="key w100">
                          Mã TT Neo
                      </div>
                      <div class="value">
                          <input type="text" class="form-control" value="" v-model="params.p_neo_ma_tt">
                      </div>
                  </div>
                  <div class="info-row">
                      <div class="key w100">
                          Mã đại diện
                      </div>
                      <div class="value">
                          <input type="text" class="form-control" value="" v-model="params.p_ma_dd">
                      </div>
                  </div>
                  <div class="info-row">
                      <div class="key w100">
                          Tên thanh toán
                      </div>
                      <div class="value">
                          <input type="text" class="form-control" v-model="params.p_ten_tt">
                      </div>
                  </div>
                  <div class="info-row">
                      <div class="key w100">
                          Địa chỉ TT
                      </div>
                      <div class="value">
                        <textarea class="form-control" style="height: 66px;" v-model="params.p_diachi_tt"></textarea>
                      </div>
                  </div>
              </div>
          </div>
          <div class="col-sm-4 col-12">
              <div class="box-form" style="height: 270px;">
                  <div class="legend-title padb2">
                      <div class="pull-left mart5">Thông tin Log</div>
                      <div class="pull-right">
                          <button class="btn btn-main">
                              <span class="nc-icon-outline arrows-1_refresh-69"></span>
                          </button>
                      </div>
                      <div class="clearfix"></div>
                  </div>
              </div>
          </div>
        </div>
        <!-- -->
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

  data () {
      return {
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' },
        params: {
          p_kycuoc: moment(new Date()).subtract(1,'months').endOf('month').format('YYYYMM') + '01',
          p_loai_timkiem: 0,  // 0: ma_tt, 1: so_dd
          p_ma_tt: null,
          p_ma_kh: null,
          p_so_dd: null,
          p_tong_no: 0,
          p_tien_tra: 0,
          p_kieu_dongbo: 0, // 0: qua DBSync, 1: qua WS
          p_neo_ma_tt: null,
          p_ma_dd: null,
          p_ten_tt: null,
          p_diachi_tt: null,
        },
      }
  },

  methods: {
    openDialog() {
      this.$refs.dlgTraCuuPhieuTraVinaPhone.show()
    },
    closeDialog() {
      this.$refs.dlgTraCuuPhieuTraVinaPhone.hide()
    },
    onDialogClose()
    {

    },
    onDialogOpen()
    {

    }
  }

})

</script>
