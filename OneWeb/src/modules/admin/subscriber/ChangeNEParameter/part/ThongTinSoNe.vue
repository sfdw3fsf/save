<template>
  <div>
    <div class="legend-title">Thông tin NE</div>
    <form @submit.prevent="handleSubmit">
      <div class="info-row">
        <div
          class="select-custom"
          :class="{ 'is-invalid': submitted && $v.form.veTinhID.$error }"
        >
          <vue-select
            v-model="form.veTinhID"
            :checkbox="false"
            :options="danhSachVeTinh"
            labelField="ten_dv"
            valueField="donvi_id"
            label="Vệ tinh"
            @input="handleVeTinh"
          ></vue-select>
        </div>
      </div>

      <div class="info-row">
        <div class="select-custom"
         :class="{ 'is-invalid': submitted && $v.form.trangThaiId.$error }"
        >
          <vue-select
            v-model="form.trangThaiId"
            :checkbox="false"
            :options="danhSachTrangThai"
            labelField="TEN_TTSO"
            valueField="TRANGTHAISO_ID"
            label="Trạng thái"
            @input="handleTrangthai"
          ></vue-select>
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Số NE</div>
        <div class="value">
          <input
            v-model="form.soNe"
             disabled
            type="text"
            class="form-control highlight"
            :class="{ 'is-invalid': submitted && $v.form.soNe.$error }"
          />
        </div>
      </div>
    </form>
  </div>
</template>
<script>
import api from './../api/index'
import { mapState } from 'vuex'
import { required } from 'vuelidate/lib/validators'
export default {
  props: {
    checkAccept: {
      type: Boolean
    }
  },
  data () {
    return {
      submitted: false,
      danhSachVeTinh: [],
      danhSachTrangThai: [],
      optionsTypeofUnit: [],
      form: {
        veTinhID: null,
        trangThaiId: null,
        soNe: ''
      },
      ne_id: null
    }
  },
  validations: {
    form: {
      veTinhID: { required },
      trangThaiId: { required },
      soNe: { required }
    }
  },
  computed: {
    ...mapState('changeNEParameter', ['danhSachSoNe', 'itemNe', 'donViID'])
  },
  created () {
    console.log('this.donViID', this.donViID)
    this.form.veTinhID = this.donViID
    this.form.trangThaiId = 1
    api
      .layThongTinDonVi(this.axios, {
        donvi_id: this.donViID,
        loaidv_id: 25
      })
      .then((res) => {
        this.danhSachVeTinh = res.data.data
        this.form.veTinhID = res.data.data && res.data.data.length > 0 ? res.data.data[0].donvi_id : null
      })
    api.layTrangThai(this.axios).then((res) => {
      this.danhSachTrangThai = res.data.data
    })
  },
  watch: {
    itemNe: 'handleSoNE',
    veTinhID: 'layDanhSachSoNe',
    checkAccept: 'handleAccept'
  },
  methods: {
    handleAccept () {
      this.handleSubmit()
    },
    handleVeTinh () {
      this.layDanhSachSoNe()
    },
    handleTrangthai () {
      this.layDanhSachSoNe()
    },
    layDanhSachSoNe () {
      this.$store.dispatch('changeNEParameter/layDanhSachSoNe', {
        donvi_id: this.form.veTinhID,
        trangthaiso_id: this.form.trangThaiId,
        type: 1
      })
    },
    handleSoNE () {
      if (this.itemNe && this.itemNe.sone) {
        this.form.soNe = this.itemNe.sone
        this.ne_id = this.itemNe.ne_id
      }
    },
    handleSubmit (e) {
      this.submitted = true
      // stop here if form is invalid
      this.$v.$touch()
      if (this.$v.$invalid) {
        this.$toast.error(
          `Bạn chưa chọn số Ne!`
        )
        return
      }
      this.$bvModal.hide('popupTCTTTB')
      this.$store.commit('changeNEParameter/SET_SELECT_NE', {...this.itemNe, trangthaiso_id: this.form.trangThaiId, type: this.form.veTinhID, neMoi: this.form.soNe, neMoiID: this.ne_id})
    }
  }
}
</script>
<style scoped  lang="css">
.select-error {
  margin-left: 86px !important;
  width: 100%;
}
>>> .select-custom.is-invalid input {
  border: 1px solid red !important;
}
>>> .modal-body {
  padding: 0 !important;
}
</style>
