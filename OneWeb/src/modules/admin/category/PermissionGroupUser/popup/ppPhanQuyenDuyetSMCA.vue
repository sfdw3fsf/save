<template>
  <div>
    <div class="modal-content popup-box">
      <!-- <breadcrumb :header="header" /> -->
      <div class="list-actions-top">
        <ul class="list">
          <li @click="btnGhiLai_PhanQuyen">
            <a> <span class="icon one-search"></span>Ghi lại</a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="row">
            <div class="col-12">
              <div class="info-row">
                <div class="key">
                  Người dùng
                </div>
                <div class="value">
                  <!--                  <select2-->
                  <!--                    :allowFiltering="true"-->
                  <!--                    v-model="nguoidung_selected"-->
                  <!--                    :dataSource="danhsach_nguoidung_SMCA_duyetSMCA"-->
                  <!--                    :filtering="filterSelect"-->
                  <!--                    :fields="{ text: 'ten_nd', value: 'nguoidung_id' }" />-->
<!--                  <select2 v-model="nguoidung_selected" class="select-custom"-->
<!--                           :options="danhsach_nguoidung_SMCA.map(ls=>({id:ls.nguoidung_id,text:ls.ten_nd}))"-->
<!--                  ></select2>-->

                  <select2 v-model="nguoidung_selected" class="select-custom"
                           :options="danhsach_nguoidung_SMCA"
                  ></select2>
                </div>
              </div>
            </div>
            <div class="col-12">
              <div class="info-row">
                <div class="key">
                  UID <span style="color: red">*</span>
                </div>
                <div class="value">
                  <input type="text"
                         v-model="formData.uid"
                         :class="{ 'is-invalid': $v.formData.uid.$error }"
                         ref="input_uid"
                         class="form-control" />
                </div>
              </div>
            </div>
            <div class="col-12">
              <div class="info-row">
                <div class="key">
                  Serial <span style="color: red">*</span>
                </div>
                <div class="value">
                  <input type="text"
                         v-model="formData.serial"
                         :class="{ 'is-invalid': $v.formData.serial.$error }"
                         ref="input_serial"
                         class="form-control" />

                </div>
              </div>
            </div>
            <div class="col-12">
              <div class="info-row">
                <div class="key">
                  Secret key <span style="color: red">*</span>
                </div>
                <div class="value">
                  <input type="text"
                         v-model="formData.secretKey"
                         :class="{ 'is-invalid': $v.formData.secretKey.$error }"
                         ref="input_secretKey"
                         class="form-control" />
                </div>
              </div>
            </div>
            <div class="col-12">
              <div class="info-row">
                <div class="key">
                  Password <span style="color: red">*</span>
                </div>
                <div class="value">
                  <input type="text"
                         v-model="formData.password"
                         :class="{ 'is-invalid': $v.formData.password.$error }"
                         ref="input_password"
                         class="form-control" />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>

import {required } from 'vuelidate/lib/validators'
import api from '../PermissionGroupUserAPI'

export default {
  props: {
    // data: {
    //   type: Object,
    //   required: true
    // },
    danhsach_nguoidung: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      formData: {
        uid: '',
        serial: '',
        secretKey: '',
        password: ''
      },
      nguoidung_selected: null,
      danhsach_nguoidung_SMCA: [],
      password: ''
    }
  },
  computed: {
  },

  validations: {
    formData: {
      uid: { required },
      serial: { required },
      secretKey: { required },
      password: { required }
    }
  },
  async mounted() {
     // this.danhsach_nguoidung_SMCA = await this.lay_danhsach_nguoidung_SMCA()

    let data = {
      'nguoidung_id': '0',
      'id': '0',
      'ten_nd': 'Duyệt SMCA Tự động',
      'text': 'Duyệt SMCA Tự động',
      'ma_nd': '0216621',
      'matkhau': 'gOKQfIb/iBhzDforfiNNqg==',
      'quantri': '0',
      'nhanvien_id': '0',
      'nhom_nd_id': '2000',
      'trangthai': '0',
      'ghichu': null,
      'user_neo': null,
      'user_visa': 'visahni',
      'user_vasc': 'hnipttb',
      'user_map': 'admin_hni',
      'pass_vasc': '649Ps21AQ9YYLEgnVcD5qw==',
      'diemgiaodich_vasc': 'HNI0000',
      'user_vtn': 'soap_hn',
      'pass_vtn': '8gXCpxehr2A=',
      'pass_visa': 'X4cmtSaaR/j1qfVPpcAf7Q==',
      'quan_id': '-1',
      'login_sai': '0',
      'ngay_login': '16/11/2020 09:00:39',
      'ngay_mk': '06/10/2022 18:36:11',
      'ngoaile': '1',
      'user_hddt': 'hnicssservice',
      'pass_hddt': '1234567aA@',
      'ngay_tao': null,
      'ngay_cn': null,
      'isadmin': '0',
      'khoa': null,
      'user_hddt_td': 'hnicss',
      'pass_hddt_td': 'Ttkdhni@321',
      'user_neo_ws': null,
      'pass_neo_ws': null,
      'user_gphone': 'ttth.ptpm2_hni',
      'url_visa_ws': 'http://10.70.52.215:8082/visa/services/ApplicationBusinessServices?wsdl',
      'user_jira': null,
      'pass_jira': null,
      'phanvung_id': '21',
      'ten_nv': 'Trần Mai Hương',
      'ten_nhom': 'Quyền cho tổ 18001166_all tỉnh',
      'chon': 's0',
      'donvi': '-'
    };

    let updatedList =  [...this.danhsach_nguoidung]
    updatedList.unshift(data);
    this.danhsach_nguoidung_SMCA = updatedList
  },
  methods: {
    async btnGhiLai_PhanQuyen() {
      // console.log(this.nguoidung_selected)
      // return

      if (!this.validateForm()) return
      // if (!this.validateForm_v2(this.formData)) return
      await this.CAPNHAT_CTS_DUYETTUDONG_SMCA()
    },

    validateForm() {
      this.$v.$touch()

      let errorMessage = []
      let allow = true

      if (this.$v.formData.uid.$error) {
        this.$refs.input_uid.focus()
        allow = false
        if (!this.$v.formData.uid.required) {
          errorMessage.push('Trường UID bắt buộc nhập.')
        }
      }
      if (this.$v.formData.serial.$error) {
        if (allow) {
          this.$refs.input_serial.focus()
          allow = false
        }
        if (!this.$v.formData.serial.required) {
          errorMessage.push('Trường Serial bắt buộc nhập.')
        }
      }
      if (this.$v.formData.secretKey.$error) {
        if (allow) {
          this.$refs.input_serial.focus()
          allow = false
        }

        if (!this.$v.formData.secretKey.required) {
          errorMessage.push('Trường Secret Key bắt buộc nhập.')
        }
      }
      if (this.$v.formData.password.$error) {
        if (allow) {
          this.$refs.input_password.focus()
          allow = false
        }

        if (!this.$v.formData.password.required) {
          errorMessage.push('Trường Password bắt buộc nhập.')
        }
      }

      if (this.$v.$invalid) {
        this.$toast.error(errorMessage.join('\n'))
        return false
      } else {
        return true
      }
    },
    // validateForm_v2(formData) {
    //   for (let key in formData) {
    //     if (formData[key] === null || formData[key] === undefined || formData[key] === '' || this.nguoidung_selected == null) {
    //       this.$toast.error('Hãy nhập đầy đủ thông tin trước khi ghi lại!')
    //       return false // Có key có giá trị rỗng
    //     }
    //   }
    //   return true
    // },

    async CAPNHAT_CTS_DUYETTUDONG_SMCA() {
      try {
        let objSelected = this.danhsach_nguoidung_SMCA.find(obj => obj.nguoidung_id == this.nguoidung_selected)

        let data = {
          vnhanvien_id: objSelected.nhanvien_id,
          vnguoidung_id: objSelected.nguoidung_id,
          vuid: this.formData.uid,
          vserial: this.formData.serial,
          vsecret_key: this.formData.secretKey,
          vpassword: this.formData.password
        }
        let rs = await api.CAPNHAT_CTS_DUYETTUDONG_SMCA(data)
        console.log(rs.data.data)
        if (rs.data.data == 'ok') {
          this.$toast.success('Cập nhật thành công')
        } else {
          this.$toast.error('Cập nhật thất bại')
        }
      } catch (e) {
        console.log(e)
      }
    },

    // async lay_danhsach_nguoidung_SMCA() {
    //   try {
    //     this.loading(true)
    //     let response = await api.lay_danhsach_nguoidung_SMCA(this.axios)
    //     this.loading(false)
    //     if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
    //       return response.data.data.map((item) => Object.assign(item, {
    //         ten_nd: item.ma_nd + ' - ' + item.ten_nd,
    //         text: item.ma_nd + ' - ' + item.ten_nd,
    //         id: item.nguoidung_id,
    //       }))
    //     } else {
    //       return []
    //     }
    //   } catch (e) {
    //     this.loading(false)
    //     return []
    //   }
    // }
  }
}
</script>
<style scoped>
</style>
