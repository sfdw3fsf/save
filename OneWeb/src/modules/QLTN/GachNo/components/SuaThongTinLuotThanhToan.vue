<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :isModal="true"
    :animationSettings='animationSettings'
    ref="dlgSuaThongTinLuotThanhToan"
    :header='"Sửa thông tin lượt thanh toán"'
    showCloseIcon=true
    width='800'
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="modal-content popup-box">
      <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#">
                    <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận (F5)
                </a>
            </li>
        </ul>
      </div>
      <div class="popup-body">
          <div class="box-form">
              <div class="legend-title">Thông tin thanh toán mới</div>
              <div class="row">
                  <div class="col-sm-4 col-12">
                      <div class="info-row">
                          <div class="key w90">Lượt TT</div>
                          <div class="value">
                              <input type="text" class="form-control" v-model="params.p_luottra">
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-8 col-12">
                      <div class="info-row">
                          <div class="key w90">Thu ngân</div>
                          <div class="value">
                              <div class="select-custom">
                                <ejs-dropdownlist :filtering='onFilteringThuNgan' :allowFiltering="true" :dataSource='params.p_thungan_options' :fields="config.fieldsNVThuNgan" v-model = "params.p_thungan" :placeholder="'Chọn thu ngân'"></ejs-dropdownlist>
                              </div>

                          </div>
                      </div>
                  </div>
              </div>
              <div class="info-row">
                  <div class="key w90">Hình thức</div>
                  <div class="value">
                      <div class="select-custom">
                          <select name="" id="" class="form-control">
                              <ejs-dropdownlist :filtering='onFilteringHTTT' :allowFiltering="true" :dataSource='params.p_httt_options' :fields="config.fieldsHTTT" v-model = "params.p_httt" :placeholder="'Chọn hình thức thanh toán'"></ejs-dropdownlist>
                          </select>
                      </div>

                  </div>
              </div>
              <div class="info-row">
                  <div class="key w90 text-main">Xác nhận MK</div>
                  <div class="value">
                      <input type="text" class="form-control" v-model="params.p_xacnhan_matkhau">
                  </div>
                  <div class="value nowrap w30 red">
                      <span class="one-alert text-warning f24 inline vcenter"></span>
                      Chỉ được chuyển trong một kỳ hóa đơn, yêu cầu xác nhận mật khẩu
                  </div>
              </div>
              <div class="info-row">
                  <div class="key w90">Ghi chú</div>
                  <div class="value">
                      <textarea v-model="params.p_ghichu" name="p_ghichu" id="p_ghichu" cols="30" rows="10" class="form-control" style="height: 100px;resize: vertical;"></textarea>
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
import GachNoAPI from '../../api/GachNoAPI'

Vue.use(DialogPlugin)

 export default Vue.extend({
    components : {
    },
    props: {
      nhom_httt : {
        type: Number
      },
      lantra: {
        type: String
      },
      ghichu: {
        type: String
      },
    },
    computed () {
    },
    async created () {
      EventBus.$on('chukycuocChange', this.chukycuocChangeHandler)
    },
    destroyed () {
      EventBus.$off('chukycuocChange', this.chukycuocChangeHandler);
    },
    data () {
      return {
        config: {
          fieldsNVThuNgan: {text: 'TEN_NV', value: 'NHANVIEN_ID' },
          fieldsHTTT: {text: 'HINHTHUC', value: 'HTTT_ID' },
        },
        params: {
          p_luottra: null,
          p_thungan_options: [],
          p_thungan: null,
          p_httt_options: [],
          p_httt: null,
          p_ghichu,
          p_xacnhan_matkhau
        },
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' }
      }
    },
    methods : {
      openDialog() {
        this.$refs.dlgChenNo.show()
      },
      closeDialog() {
        this.$refs.dlgChenNo.hide()
      },
      onDialogClose() {
        this.clearForm()
        this.clearResult()
      },
      onDialogOpen() {
        this.params.p_luottra = this.lantra
        this.params.p_ghichu = this.ghichu
        this.getDSHTTT(this.nhom_httt, 1)
        this.getDSThuNgan()
      },
      clearForm() {

      },
      clearResult() {

      },
      doAccept: function(){
        this.$refs.dlgSuaThongTinLuotThanhToan.hide()
      },
      getDSHTTT: function(nhom, kieu){
        try{
          //CommonsAPI.getDanhSachHinhThucThanhToan(this.axios, {kieu: kieu, nhomHTTT: nhom}).then(
            GachNoAPI.getDanhSachHTTT(this.axios, {kieu: kieu, nhomHTTT: nhom}).then(

            (response) => {
              if(response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined)
              {
                this.params.p_httt_options = response.data.data
                this.params.p_httt = response.data.data[0].HTTT_ID
              }
            }
          )
        } catch (error)
        {
          this.$toast.error(error)
        }
      },
      getDSThuNgan: function(){

        try{
          GachNoAPI.getDSThuNgan(this.axios).then(
            (response) => {
              if(response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined)
              {
                this.params.p_thungan_options = response.data.data
                this.params.p_thungan = response.data.data[0].NHANVIEN_ID
                this.params.p_quaythu_id = response.data.data[0].DONVI_ID
                this.getDonViQuanLy()
              }
            }
          )
        } catch (error)
        {
          this.$toast.error(error)
        }
      },
      onFilteringThuNgan(e){
        var query = new Query();
        let keyword = e.text.trim()
        query = (e.text !== '') ? query.where('TEN_NV', 'contains', keyword, true) : query;
        e.updateData(this.params.p_thungan_options, query);
      },
      onFilteringHTTT(e){
        var query = new Query();
        let keyword = e.text.trim()
        query = (e.text !== '') ? query.where('HINHTHUC', 'contains', keyword, true) : query;
        e.updateData(this.params.p_httt_options, query);
      },
    }
  })
</script>
