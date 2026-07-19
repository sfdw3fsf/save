<script>
import api from '../../API/CompleteProfileAPI'
import { lay_trangthai_hdtb } from '../../../../setup/z_common_func/setup_common'

export default {
  name: 'LoginCommon',
  props: {
    vhdtb_id: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      formData: {
        txtTenDangNhap: '',
        txtMatKhau: '',
      }
    }
  },

  methods: {
    async btnKichHoat () {
      if(!this.validate_form()) return
      try {
        this.loading(true)

        let rs_trangthai = await lay_trangthai_hdtb(this.vhdtb_id)
        let vtthd_id = rs_trangthai[0].tthd_id
        if (vtthd_id == 6) {
          this.$toast.success('Trạng thái kích hoạt thành công, hãy hoàn thiện hồ sơ!')
          return
        }

        let login_form = {
          username: this.formData.txtTenDangNhap,
          password: this.formData.txtMatKhau
        }

        const get_ketqua_dynamic = await this.ConvertResApiHBDT(api.cntt_dynamic_api(this.axios, {
          hdtb_id: this.vhdtb_id,
          sub_object: JSON.stringify(login_form),
          vtype: "login_tsa"
        }))
        const ketqua_status = get_ketqua_dynamic.status
        if (ketqua_status == '00') {
          this.$toast.success(get_ketqua_dynamic.message)
          // await this.call_dynamic_api_2nd()
          // console.log('call_dynamic_api_2nd')
        } else {
          this.$toast.error(get_ketqua_dynamic.message)
        }
      }
      catch (e){
        console.log(e)
      }
      finally {
        this.loading(false)
      }
    },

    async ConvertResApiHBDT(response_input) {
      let response = await response_input
      if (response.data.errorCode && response.data.errorCode != '' && response.data.errorCode != '0' && response.data.faultString && response.data.faultString != '') {
        throw response.data.faultString
      }
      if (response.data.error === 200 || response.data.error === '200' || response.data.errorCode === 0) {
        if (response.data.error_code === 'BSS-00000000') {
          return response.data.data
        }
      } else {
        if (response.data.error === '204' || response.data.error === 204) {
          return []
        } else return response.data.message
      }
    },
    validate_form() {
      if (this.formData.txtTenDangNhap == '') {
        this.$toast.error('Vui lòng nhập tên đăng nhập')
        return false
      }
      if (this.formData.txtMatKhau == '') {
        this.$toast.error('Vui lòng nhập mật khẩu')
        return false
      }
      return true
    }
  }
}
</script>

<template>
  <div class="modal-content popup-box">
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a @click="btnKichHoat">
            <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span>Kích hoạt
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="info-row">
          <div class="key w90">Tên đăng nhập</div>
          <div class="value">
            <input type="text" class="form-control" v-model="formData.txtTenDangNhap">
          </div>
        </div>
        <div class="info-row">
          <div class="key w90">Mật khẩu</div>
          <div class="value">
            <input type="password" class="form-control" v-model="formData.txtMatKhau">
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>
