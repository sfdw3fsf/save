<template>
  <div class="popup-body">
    <div class="box-form">
      <div class="legend-title">Thông tin thuê bao</div>
      <form @submit.prevent="handleSubmit">
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w90">Số máy</div>
              <div class="value">
                <input
                  type="text"
                  class="form-control highlight"
                  v-model="form.in_ma_tb"
                  id="firstName"
                  name="firstName"
                  :class="{
                    'is-invalid': submitted && $v.form.in_ma_tb.$error,
                  }"
                  @keyup.enter="handleChangePhoneNumber"
                />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w90">Trạng thái TB</div>
              <div class="value">
                <input
                  type="text"
                  class="form-control italic red font-weight-normal"
                  v-model="form.trangthai_tb"
                  disabled
                  id="firstName"
                  name="lastName"
                />
              </div>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w90">Tên TB</div>
          <div class="value">
            <input
              type="text"
              class="form-control"
            disabled
              v-model="form.ten_tb"
            />
          </div>
        </div>
        <div class="info-row">
          <div class="key w90">Địa chỉ LĐ</div>
          <div class="value">
            <input
              type="text"
              disabled
              class="form-control"
              v-model="form.diachi_ld"
            />
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w90">Ne mới</div>
              <div class="value">
                <div class="input-more-button">
                  <button type="button" class="btn" @click="handleShowForm">
                    <span class="-ap icon-more_horiz"></span>
                  </button>
                  <input
                    type="text"
                    v-model="form.ne_moi"
                    class="form-control highlight"
                    ref="search"
                    :class="{
                    'is-invalid': submitted && $v.form.ne_moi.$error,
                  }"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w90">Ne cũ</div>
              <div class="value">
                <input
                  type="text"
                 disabled
                  class="form-control"
                  v-model="form.ne_cu"
                />
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>
<script>
import { mapState } from 'vuex'
import { required } from 'vuelidate/lib/validators'
import api from './../api/index'
export default {
  props: {
    formSumbit: {
      type: Boolean
    },
    checkResetForm: {
      type: Boolean
    },
    checkResetFormActivated: {
      type: Boolean
    }
  },
  data () {
    return {
      tongDai: null,
      maDichVu: null,
      nap_id: null,
      loaitram: 0,
      tramtb_id: null,
      donvi_id: null,
      so_dt: null,
      trangthaitb_id: null,
      thuebao_id: null,
      neCuID: null,
      form: {
        in_ma_tb: '',
        trangthai_tb: '',
        ten_tb: '',
        diachi_ld: '',
        ne_cu: '',
        ne_moi: ''
        // confirmPassword: ''
      },
      submitted: false,
      submitForm: null,
      dongbo_test : false
    }
  },
  validations: {
    form: {
      in_ma_tb: {
        required
      },
      trangthai_tb: {
        required
      },
      ten_tb: {
        required
      },
      diachi_ld: {
        required
      },
      ne_cu: {
        required
      },
      ne_moi: {
        required
      }
    }
  },
  computed: {
    ...mapState('changeNEParameter', ['itemNe', 'selectNe'])
  },
  watch: {
    formSumbit: 'handleFormSumbit',
    checkResetForm: 'handleResetForm',
    selectNe: 'handlesetFocus',
    checkResetFormActivated: 'handleFormSumbitActivated'
  },
  mounted () {},
  methods: {
    handlesetFocus () {
      this.$refs.search.focus()
      this.form.ne_moi = this.selectNe.neMoi
      this.ne_moi_id = this.selectNe.neMoiID
    },
    handleResetForm () {
      this.submitted = false
      this.so_dt = null
      this.trangthaitb_id = null
      this.thuebao_id = null
      this.form.in_ma_tb = ''
      this.form.trangthai_tb = ''
      this.form.ten_tb = ''
      this.diachi_ld = ''
      this.form.diachi_ld = ''
      this.form.ne_cu = ''
      this.form.ne_moi = ''
    },
    getData: function (response) {	
      if (response.data.error === 200 || response.data.error === '200' || response.data.error === '0') {	
        return response.data.data	
      } else {	
        console.log(response.data.error_code)	
      }	
      return []	
    },
    async handleChangePhoneNumber (e) {
      this.$root.showLoading(true)
      this.form.ne_moi = ""
      const result = await api.layDanhBaTheoMaMay(this.axios, {
        in_ma_tb: this.form.in_ma_tb,
        in_dichvuvt_id: 1,
        in_donvi_dl_id: 0
      })
      if (
        result &&
        result.data &&
        result.data.data &&
        result.data.data.length > 0
      ) {
        this.form.trangthai_tb = result.data.data[0].trangthai_tb
        this.form.ten_tb = result.data.data[0].ten_tb
        this.form.diachi_ld = result.data.data[0].diachi_tb
        this.trangthaitb_id = result.data.data[0].trangthaitb_id
        this.thuebao_id = result.data.data[0].thuebao_id
        this.so_dt = result.data.data[0].so_dt
        const resultDBCD = await api.layDanhBaCoDinh(this.axios, {
          in_thuebao_id: this.thuebao_id
        })
        this.$root.showLoading(false)
        //this.getListOnService()
        //this.getListSwitchboards()
        if (
          resultDBCD &&
          resultDBCD.data &&
          resultDBCD.data.data &&
          resultDBCD.data.data.length > 0
        ) {
          this.form.ne_cu = resultDBCD.data.data[0].ne
          this.neCuID = resultDBCD.data.data[0].ne_id
          this.nap_id = resultDBCD.data.data[0].nap_id
          this.tramtb_id = resultDBCD.data.data[0].tramtb_id
          this.donvi_id = resultDBCD.data.data[0].tramvt_id
          if (this.nap_id != null) {
            this.loaitram = 1
          }
          this.$root.showLoading(false)
          this.$store.commit('changeNEParameter/SET_DON_VI_ID', this.donvi_id)
        }
      } else {
        this.$root.showLoading(false)
        this.$toast.error(
          `Không tìm thấy thông tin về thuê bao: ${this.form.in_ma_tb}. Đề nghị kiểm tra lại số máy.!`
        )
      }
    },
    async getListOnService () {
      const resul = await api.layDanhSuDungDichVuGiaTang(this.axios, {
        in_thuebao_id: this.thuebao_id
      })
      if (resul && resul.length > 0) {
        let data = resul.find(res => res.thuebao_id === this.thuebao_id)
        this.maDichVu = data.dichvugt_id
      }
    },
    async getListSwitchboards () {
      const resul = await api.layDanhTongDai(this.axios, {
        in_thuebao_id: this.form.in_ma_tb
      })
      if (resul && resul.length > 0) {
        let data = resul.data.data.find(res => res.somay === this.thuebao_id)	
        this.tongDai = data.donvi_id
      }
    },
    handleFormSumbitActivated (e) {
      this.submitForm = 'activated'
      this.handleSubmit(e)
    },
    handleFormSumbit (e) {
      this.submitForm = 'f9'
      this.handleSubmit(e)
    },
    handleShowForm () {
      this.$bvModal.show('popupTCTTTB')
      this.$store.dispatch('changeNEParameter/layDanhSachSoNe', {
        donvi_id: this.donvi_id,
        trangthaiso_id: null,
        type: null
      })
    },
    handleSubmit (e) {
      try {
        this.submitted = true
        // stop here if form is invalid
        this.$v.$touch()
        if (!this.form.in_ma_tb && !this.form.ne_moi) {
          this.$toast.error(`Chưa có thuê bao đế đổi số Ne!`);
          return
        }
        if (this.form.in_ma_tb && !this.form.ne_moi) {
          this.$toast.error(`Chưa có Ne mới đế đổi số Ne!`);
          return
        }
        this.$bvModal
          .msgBoxConfirm(
            `Bạn thật sự muốn thay đổi thông tin Ne của thuê bao  ${this.form.in_ma_tb} ?`,
            {
              title: '',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy'
            }
          )
          .then(async (v) => {
            if (v) {
              if (this.submitForm === 'activated') {
                this.$root.showLoading(true)
                let maTinh = this.$root.token.getMaTinh()	
                await api.get_app_config(this.axios, {	
                  "keyname": "DONGBO_TEST"	
                }).then((res) => {	
                  if (res && res.data && res.data.data) {	
                    if (res.data.data.dongbo == 1 && res.data.data.keyvalue == 'FALSE') {	
                      this.dongbo_test = false	
                    } else {	
                      this.dongbo_test = true	
                    }	
                  }	
                })
                if (maTinh == 'hpg' && !this.dongbo_test) {	
                  console.log('run in this')	
                  api	
                  .dangKyDichVu(this.axios, {	
                    tongDai: this.tongDai,	
                    soMay: this.form.in_ma_tb,	
                    viTri: this.form.ne_moi,	
                    maDichVu: this.maDichVu,	
                    thamSo: null	
                  })	
                  .then((res) => {	
                    if (res && res.data && res.data.data) {	
                      this.updateAncientInformation()	
                    } else {	
                      this.$toast.error(`${res.data.faultString}`)	
                      this.$root.showLoading(false)	
                    }	
                  })	
                } else {	
                  this.updateAncientInformation()	
                }
              }
            }
          })
      } catch (err) {
        this.$toast.error(err)
      }
    },
    updateAncientInformation () {
      this.$root.showLoading(true)
      api
        .spTdttCdUpdate(this.axios, {
          thuebao_id: this.thuebao_id,
          ne_moi: this.form.ne_moi,
          ne_moi_id: this.ne_moi_id,
          nap_id: this.nap_id,
          loaitram: this.loaitram,
          tramtb_id: this.tramtb_id,
          js_data: JSON.stringify([
            {
              MA_TB: this.form.in_ma_tb,
              NE_CU: this.form.ne_cu,
              NE_ID_CU: this.neCuID,
              TRANGTHAI_ID: this.trangthaitb_id,
              NE: this.form.ne_cu,
              NE_ID: this.neCuID
            }
          ])
        })
        .then((res) => {
          if (res && res.data && res.data.error == 200) {
            this.submitted = false;
            this.$toast.success(`Cập nhật dữ liệu vào danh bạ thành công!`)
            this.handleChangePhoneNumber()
            this.form.ne_moi = ''
            this.ne_moi_id = null
            this.$root.showLoading(false)
          } else {
            this.$toast.error(`Cập nhật dữ liệu lỗi`)
            this.$root.showLoading(false)
          }
        })
        .catch((e) => {
          this.$toast.error(`Cập nhật dữ liệu lỗi`)
          this.$root.showLoading(false)
        })
    }
  }
}
</script>
<style scoped>
  input {font-weight:bold;}
</style>