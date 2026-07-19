<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
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
                <a href="javascript: void(0)" @click="onClickAccept">
                    <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận
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
                            <ejs-numerictextbox locale="vi-VN"  :showSpinButton="false" class="red form-control tright" v-model="$data.params.p_luottra" format="N0"></ejs-numerictextbox>
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-8 col-12">
                      <div class="info-row">
                          <div class="key w90">Thu ngân</div>
                          <div class="value">
                            <ejs-dropdownlist :filtering='onFilteringThuNgan' :allowFiltering="true" :dataSource='params.p_thungan_options' :fields="config.fieldsNVThuNgan" v-model = "params.p_thungan" :placeholder="'Chọn thu ngân'"></ejs-dropdownlist>
                          </div>
                      </div>
                  </div>
              </div>
              <div class="info-row">
                  <div class="key w90">Hình thức</div>
                  <div class="value">
                    <ejs-dropdownlist :filtering='onFilteringHTTT' :allowFiltering="true" :dataSource='params.p_httt_options' :fields="config.fieldsHTTT" v-model = "params.p_httt" :placeholder="'Chọn hình thức thanh toán'"></ejs-dropdownlist>
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
                      <textarea v-model="params.p_ghichu" name="p_ghichu"
                      oninput="javascript: if (this.value.length > this.maxLength) this.value = this.value.slice(0, this.maxLength);"
                      maxlength=150
                      id="p_ghichu" cols="30" rows="10" class="form-control" style="height: 100px;resize: vertical;"></textarea>
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
import XuLyPhieuApi from '../../api/XuLyPhieuThanhToanAPI.js'

Vue.use(DialogPlugin)

 export default Vue.extend({
    components : {
    },
    props: {
      input: {
          type: Object
      }
    },
    computed () {
    },
    async created () {
      //EventBus.$on('chukycuocChange', this.chukycuocChangeHandler)
    },
    destroyed () {
      //EventBus.$off('chukycuocChange', this.chukycuocChangeHandler);
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
          p_ghichu : null,
          p_xacnhan_matkhau : null,
          //p_nhom_httt: null
        },
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' }
      }
    },
    methods : {
      openDialog() {

        this.$refs.dlgSuaThongTinLuotThanhToan.show()
      },
      closeDialog() {
        this.clearForm()
        this.clearResult()
        this.$refs.dlgSuaThongTinLuotThanhToan.hide()
      },
      onDialogClose() {
        this.clearForm()
        this.clearResult()
      },
      onDialogOpen() {
        this.params.p_luottra = this.input.lanTra
        this.params.p_ghichu = this.input.ghiChu

        this.getDSHTTT(this.input.nhomHTTTId, 0)
        this.getDSThuNgan()
      },
      clearForm() {

      },
      clearResult() {

      },
      async onClickAccept()
      {

        let errors = []


        if(this.input.kyCuoc === null)
        {
          errors.push('Không tìm thấy thông tin kỳ cước.')
        }

        if(this.input.phieuId === null)
        {
          errors.push('Không tìm thấy phiếu thanh toán cần sửa.')
        }

        if(this.params.p_luottra === null)
        {
          errors.push('Chưa nhập cho lượt thanh toán.')
        }

        if(this.params.p_thungan === null)
        {
          errors.push('Chưa chọn thu ngân.')
        }

        if(this.params.p_httt === null)
        {
          errors.push('Chưa chọn hình thức.')
        }

        if(this.params.p_ghichu === null || this.params.p_ghichu === '' || this.params.p_ghichu.trim() === '')
        {
          errors.push('Chưa nhập ghi chú.')
        }

        if(this.params.p_xacnhan_matkhau === null || this.params.p_xacnhan_matkhau === '')
        {
          errors.push('Chưa nhập xác nhận mật khẩu.')
        }

        if(errors.length > 0)
        {
          this.$toast.error(errors.join('\r\n'))
          return
        }

        let postData = {
          kyCuoc: this.input.kyCuoc,
          phieuId: this.input.phieuId,
          thuNganId: this.params.p_thungan,
          htttId: this.params.p_httt,
          lanTra: this.params.p_luottra,
          ghiChu: this.params.p_ghichu,
          matKhau: this.params.p_xacnhan_matkhau
        }


        const res = await this.updateThongTinThanhToan(postData)
        if(res)
        {
          this.$emit('acceptChange', res);
          this.closeDialog()
        }


      },
      async updateThongTinThanhToan(apiBody) {
        try {
          this.loading(true);
          let response = await XuLyPhieuApi.updateThongTinThanhToan(this.axios, apiBody);
          return response.data.error_code === 'BSS-00000000' ? true : false;
        }
        catch(error) {
          this.$toast.error(error.data.message_detail)
          return false;
        }
        finally {
          this.loading(false);
        }
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
                //this.getDonViQuanLy()
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
