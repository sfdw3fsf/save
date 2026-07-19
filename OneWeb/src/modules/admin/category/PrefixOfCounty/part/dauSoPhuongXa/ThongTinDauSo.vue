<template>
  <div class="box-form">
    <div class="legend-title">Thông tin đầu số</div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w100">
            Đơn vị QL<span class="required">*</span>
          </div>
          <div class="value">
            <div class="select-custom">
              <!-- <vue-select
                ref="refCacDonViID"
                 :class="!cacDonViID && checkSubmit?'validationCacDonViID' :''"
                v-model="cacDonViID"
                :labelWidth="'100'"
                :options="danhSachCacDonVi"
                labelField="NAME"
                valueField="ID"
                label="Đơn vị QL"
                @input="handleCacDonViID"
                :required="true"
              ></vue-select> -->
              <SelectExt
                ref="refCacDonViID"
                 :class="!cacDonViID && checkSubmit?'validationCacDonViID' :''"
                v-model="cacDonViID"
                :dataSource="danhSachCacDonVi"
                dataTextField="NAME"
                dataValueField="ID"
                @change="handleCacDonViID"
                :required="true"
              ></SelectExt>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w100">
            Quận/Huyện<span class="required">*</span>
          </div>
          <div class="value">
            <div class="select-custom">
              <!-- <vue-select
                ref="refQuanHuyenId"
                 :class="!quanHuyenId && checkSubmit?'validationQuanHuyenId' :''"
                v-model="quanHuyenId"
                :labelWidth="'100'"
                :options="danhSachQuanHuyen"
                @input="handleQuanHuyen"
                labelField="NAME"
                valueField="ID"
                label="Quận/Huyện"
                :required="true"
              ></vue-select> -->
              <SelectExt
                ref="refQuanHuyenId"
                 :class="!quanHuyenId && checkSubmit?'validationQuanHuyenId' :''"
                v-model="quanHuyenId"
                :dataSource="danhSachQuanHuyen"
                @change="handleQuanHuyen"
                dataTextField="NAME"
                dataValueField="ID"
                :required="true"
              ></SelectExt>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w100">
            Phường/Phố<span class="required">*</span>
          </div>
          <div class="value">
            <div class="select-custom">
              <SelectExt
                ref="refPhuongID"
                v-model="phuong_id"
                :dataSource="danhSachPhuongXa"
                @change="handlePhuongXaID"
                 :class="!phuong_id && checkSubmit?'validationPhuongXaID' :''"
                dataTextField="NAME"
                dataValueField="ID"
                :required="true"
              ></SelectExt>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w100">
            Tổng đài<span class="required">*</span>
          </div>
          <div class="value" >
            <div class="select-custom">
              <SelectExt
                ref="refTongDaiID"
                :validate="['required']"
                v-model="tongDaiID"
                :dataSource="danhSachTongDai"
                @change="handleTongDaiID"
                dataTextField="NAME"
                dataValueField="ID"
                :required="true"
              ></SelectExt>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w100">
            Loại số<span class="required">*</span>
          </div>
          <div class="value">
            <div class="select-custom">
              <SelectExt
                ref="refCacLoaiSoID"
                :validate="['required']"
                v-model="loaiSoID"
                :dataSource="danhSachCacLoaiSo"
                @change="handleCacLoaiSoID"
                dataTextField="NAME"
                dataValueField="ID"
                :required="true"
              ></SelectExt>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w100">
            Dịch vụ<span class="required">*</span>
          </div>
          <div class="value">
            <div class="select-custom">
              <SelectExt
                ref="refDichVuID"
                :validate="['required']"
                v-model="dichVuID"
                :dataSource="danhSachDichVu"
                @change="handleDichVuID"
                dataTextField="NAME"
                dataValueField="ID"
                :required="true"
              ></SelectExt>
            </div>
          </div>
        </div>
        <div class="info-row">
            <div class="key w100 " style="">Từ số  <span  class="required"> *</span> </div>
               <div class="value">
                <input ref="refSoBatDau" @input="changeSoBatDau($event)" type="number"  :validate="['required']" class="form-control"  v-model="soBatDau" :class="{ 'is-invalid': !soBatDau && checkSubmit}">
            </div>
        </div>
        <div class="info-row">
               <div class="key w100 " style="">Đến số  <span  class="required"> *</span> </div>
               <div class="value">
                <input ref="refSoKetThuc" @input="changeSoKetThuc($event)" type="number" class="form-control"   v-model="soKetThuc" :class="{ 'is-invalid': !soKetThuc && checkSubmit}">
            </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions, mapState } from 'vuex'
import api from './../../api'
export default {
  props: {
    saveForm: Boolean,
    dataSelect: Array
  },
  data () {
    return {
      loaiSoID: 1,
      quanHuyenId: null,
      cacDonViID: null,
      phuong_id: null,
      tongDaiID: 399,
      dichVuID: 1,
      soBatDau: null,
      soKetThuc: null,
      checkSubmit: false
    }
  },
  computed: {
    ...mapState('prefixOfCounty', ['danhSachCacLoaiSo', 'danhSachCacDonVi', 'danhSachQuanHuyen', 'danhSachPhuongXa', 'danhSachTongDai', 'danhSachDichVu', 'danhSachPrefixDaChon', 'dataItem'])
  },
  watch: {
    saveForm: 'handleSaveForm'
  },
  created () {
    const params = '1,10,11'
    this.layDanhSachDichVu({ID: params})
  },
  methods: {
    ...mapActions('prefixOfCounty', ['layDanhSachPhuongXa', 'layDanhSachPrefixChuaChon', 'layDanhSachPrefixDaChon', 'layDanhSachDichVu']),
    handleQuanHuyen () {
      this.layDanhSachPhuongXa({ID: this.quanHuyenId})
      this.getDanhSachPrefixChuaChon()
      this.getDanhSachPrefixDaChon()
      this.$store.commit('prefixOfCounty/SET_DANH_ITEM_SELECT', {...this.dataItem, quanHuyenId: this.quanHuyenId})
    },
    changeSoBatDau(event) {
      this.$emit('tuso', event.target.value)
    },
    changeSoKetThuc(event) {
      this.$emit('denso', event.target.value)
    },
    handleDichVuID () {
      this.getDanhSachPrefixChuaChon()
      this.getDanhSachPrefixDaChon()
      this.$store.commit('prefixOfCounty/SET_DANH_ITEM_SELECT', {...this.dataItem, dichVuID: this.dichVuID})
    },
    async handleSaveForm () {
      this.checkSubmit = true
      if (!this.isValidForm()) {
        return
      }
      // if (!this.danhSachPrefixDaChon || this.danhSachPrefixDaChon.length == 0) {
      //   this.$toast.error(`Prefix không được bỏ trống!`)
      //   return
      // }
      const value = this.dataSelect.map(res => {
        return {
          dichvuvt_id: this.dichVuID,
          donvi_ql_id: this.cacDonViID,
          host_id: this.tongDaiID,
          phuong_id: this.phuong_id,
          quan_id: this.quanHuyenId,
          denso: this.soKetThuc,
          tuso: this.soBatDau,
          prefix: res.prefix
        }
      })
      this.$root.showLoading(true)
      api.xoaDauSoPhuongXa(this.axios, [ {
        dichvuvt_id: this.dichVuID,
        donvi_ql_id: this.cacDonViID,
        host_id: this.tongDaiID,
        phuong_id: this.phuong_id,
        quan_id: this.quanHuyenId,
        loaiso_id: this.loaiSoID
      }]).then(result => {
        if (result && result.data && result.data.message == 'Success') {
          api.thenDauSoPhuongXa(this.axios, value).then(res => {
            if (result && result.data && result.data.message == 'Success') {
              this.$toast.success(`Cập nhật dữ liệu  thành công!`)
              this.$root.showLoading(false)
              this.getDanhSachPrefixChuaChon()
              this.getDanhSachPrefixDaChon()
            } else {
              this.$toast.error(`Cập nhật dữ liệu lỗi`)
              this.$root.showLoading(false)
            }
          })
        }
      })
    },
    handleCacDonViID () {
      this.getDanhSachPrefixChuaChon()
      this.getDanhSachPrefixDaChon()
      this.$store.commit('prefixOfCounty/SET_DANH_ITEM_SELECT', {...this.dataItem, donViID: this.cacDonViID})
    },
    handleTongDaiID () {
      this.getDanhSachPrefixChuaChon()
      this.getDanhSachPrefixDaChon()
      this.$store.commit('prefixOfCounty/SET_DANH_ITEM_SELECT', {...this.dataItem, tongDaiID: this.tongDaiID})
    },
    handlePhuongXaID () {
      this.getDanhSachPrefixChuaChon()
      this.getDanhSachPrefixDaChon()
      this.$store.commit('prefixOfCounty/SET_DANH_ITEM_SELECT', {...this.dataItem, phuong_id: this.phuong_id})
    },
    handleCacLoaiSoID () {
      this.getDanhSachPrefixChuaChon()
      this.getDanhSachPrefixDaChon()
      if (this.loaiSoID == 1) {
        const params = '1,10,11'
        this.layDanhSachDichVu({ID: params})
      } else {
        const params = '4,8,7,9,12,13'
        this.layDanhSachDichVu({ID: params})
      }
      this.$store.commit('prefixOfCounty/SET_DANH_ITEM_SELECT', {...this.dataItem, loaiSoID: this.loaiSoID})
    },
    getDanhSachPrefixChuaChon () {
      this.layDanhSachPrefixChuaChon({
        'dichvuvt_id': this.dichVuID,
        'donvi_ql_id': this.cacDonViID,
        'host_id': this.tongDaiID,
        'loaiso_id': this.loaiSoID,
        'phuong_id': this.phuong_id,
        'quan_id': this.quanHuyenId,
        'trangthai_chon': 'chua-chon'
      })
    },
    getDanhSachPrefixDaChon () {
      this.layDanhSachPrefixDaChon({
        'dichvuvt_id': this.dichVuID,
        'donvi_ql_id': this.cacDonViID,
        'host_id': this.tongDaiID,
        'loaiso_id': this.loaiSoID,
        'phuong_id': this.phuong_id,
        'quan_id': this.quanHuyenId,
        'trangthai_chon': 'da-chon'
      })
    },
    warningInput () {
      this.$refs.refCacLoaiSoID.checkValidate()
      this.$refs.refDichVuID.checkValidate()
      this.$refs.refCacDonViID.checkValidate()
      this.$refs.refTongDaiID.checkValidate()
      this.$refs.refQuanHuyenId.checkValidate()
      this.$refs.refPhuongID.checkValidate()
      this.$refs.refSoKetThuc.checkValidate()
      this.$refs.refSoBatDau.checkValidate()
    },
    isValidForm () {
      if (!this.cacDonViID) {
        this.$toast.error(`Đơn vị QL không thể bỏ trống`)
        return false
      }
      if (!this.loaiSoID) {
        this.$toast.error(` Loại số  không thể bỏ trống`)
        return false
      }

      if (!this.quanHuyenId) {
        this.$toast.error(` Quận/Huyện không thể bỏ trống`)
        return false
      }

      if (!this.phuong_id) {
        this.$toast.error(` Phường/Phố không thể bỏ trống`)
        return false
      }

      if (!this.tongDaiID) {
        this.$toast.error(` Tổng đài không thể bỏ trống`)
        return false
      }
      if (!this.dichVuID) {
        this.$toast.error(` Dịch vụ không thể bỏ trống`)
        return false
      }
      if (!this.soBatDau) {
        this.$toast.error(` Từ số không thể bỏ trống`)
        return false
      }
      if (!this.soKetThuc) {
        this.$toast.error(`Đến số không thể bỏ trống`)
        return false
      }
      return true
    }
  }
}
</script>

<style scoped>
>>> .keyW100{
  width: 105px !important;
}
>>> .validationCacDonViID input {
  border: 1px solid red;
}
>>> .validationQuanHuyenId input{
   border: 1px solid red;
}
>>> .validationPhuongXaID input{
  border: 1px solid red;
}

.validationCacDonViID span.selection .select2-selection--single, 
.validationQuanHuyenId span.selection .select2-selection--single,
.validationPhuongXaID span.selection .select2-selection--single
{
  border: 1px solid red;
}
</style>
