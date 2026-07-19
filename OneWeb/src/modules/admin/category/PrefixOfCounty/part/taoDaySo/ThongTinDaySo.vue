<template>
  <div class="box-form">
    <div class="legend-title">Thông tin dải số</div>
    <div class="info-row">
      <div class="key w100">
        Loại số<span class="required">*</span>
      </div>
      <div class="value">
        <div class="select-custom">
          <!-- <SelectExt
            ref="refCacLoaiSoID"
            :validate="['required']"
            v-model="loaiSoID"
            :dataSource="danhSachCacLoaiSo"
            @change="handleCacLoaiSoID"
            dataTextField="NAME"
            dataValueField="ID"
            :required="true"
          ></SelectExt> -->
          <SelectExt
              ref="refCacLoaiSoID"
              v-model="loaiSoID"
              :dataSource="danhSachCacLoaiSo1"
              @change="handleCacLoaiSoID"
              dataTextField= "NAME"
              dataValueField= "ID"
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
            :dataSource="danhSachDichVu1"
            @change="handleDichVuID"
            dataTextField="NAME"
            dataValueField="ID"
            :required="true"
          ></SelectExt>
        </div>
      </div>
    </div>
    <div class="info-row">
      <div class="key w100">
        Đơn vị QL<span class="required">*</span>
      </div>
      <div class="value">
        <div class="select-custom">
          <SelectExt
            ref="refCacDonViID"
            :validate="['required']"
            v-model="cacDonViID"
            :dataSource="danhSachCacDonVi1"
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
        Tổng đài<span class="required">*</span>
      </div>
      <div class="value">
        <div class="select-custom">
          <SelectExt
            ref="refTongDaiID"
            :validate="['required']"
            v-model="tongDaiID"
            :dataSource="danhSachTongDai1"
            @change="handleTongDaiID"
            dataTextField="NAME"
            dataValueField="ID"
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
          <SelectExt
            ref="refQuanHuyenId"
            :validate="['required']"
            v-model="quanHuyenId"
            :dataSource="danhSachQuanHuyen1"
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
            :validate="['required']"
            v-model="phuong_id"
            :dataSource="danhSachPhuongXa"
            @change="handlePhuongXaID"
            dataTextField="NAME"
            dataValueField="ID"
            :required="true"
          ></SelectExt>
        </div>
      </div>
    </div>
    <div class="info-row">
      <div class="value">
        <vue-input
          v-model="ghichu"
          label="Ghi chú"
          :labelWidth="'100'"
        ></vue-input>
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions, mapState } from 'vuex'
import api from './../../api'
export default {
  props: {
    isAccept: {
      type: Boolean
    },
    dataSelect: {
      type: Array
    }
  },
  data () {
    return {
      loaiSoID: null,
      quanHuyenId: null,
      cacDonViID: null,
      phuong_id: null,
      tongDaiID: null,
      dichVuID: null,
      ghichu: null,
      danhSachCacDonVi1: [],
      danhSachCacLoaiSo1: [],
      danhSachDichVu1: [],
      danhSachTongDai1: [],
      danhSachQuanHuyen1: [],
      danhSachPhuongXa1: []
    }
  },
  mounted () {
    this.danhSachCacLoaiSo1 = [...this.danhSachCacLoaiSo]
    this.danhSachCacDonVi1 = [...this.danhSachCacDonVi]
    this.danhSachDichVu1 = [...this.danhSachDichVu]
    this.danhSachTongDai1 = [...this.danhSachTongDai]
    this.danhSachQuanHuyen1 = [...this.danhSachQuanHuyen]
    this.danhSachPhuongXa1 = [...this.danhSachPhuongXa]
    this.handleQuanHuyen()
  },
  computed: {
    ...mapState('prefixOfCounty', [
      'danhSachCacLoaiSo',
      'danhSachCacDonVi',
      'danhSachQuanHuyen',
      'danhSachPhuongXa',
      'danhSachTongDai',
      'danhSachDichVu',
      'danhSachPrefixDaChon',
      'dataItem',
      'isShowModal',
      'paramFixedNumber'
    ])
  },
  watch: {
    isShowModal: {
      handler () {
        this.handleShowModal()
      },
      deep: true,
      immediate: true
    },
    isAccept() {
      this.handleSubmitForm()
    }
  },
  methods: {
    ...mapActions('prefixOfCounty', ['layDanhSachPhuongXa', 'layDanhSachPrefixChuaChon', 'layDanhSachPrefixDaChon', 'layDanhSachDichVu', 'layDanhSachDaiCoDinh', 'setParamFixed']),
    handleQuanHuyen () {

      console.log('danhSachPhuongXa is ', this.danhSachPhuongXa)
      this.layDanhSachPhuongXa({ID: this.quanHuyenId})
      this.getDanhSachPrefixDaChon()
      this.$store.commit('prefixOfCounty/SET_DANH_ITEM_SELECT', {...this.paramFixedNumber, quanHuyenId: this.quanHuyenId})
    },
    handleDichVuID () {
      this.getDanhSachPrefixDaChon()
      this.$store.commit('prefixOfCounty/SET_PARAM_FIXED', {...this.paramFixedNumber, dichVuID: this.dichVuID})
    },
    handleSubmitForm () {
      if (!this.isValidForm()) {
        return
      }
      if (!this.dataSelect || this.dataSelect.length == 0) {
        this.$toast.error(`Prefix không được bỏ trống!`)
        return
      }
      this.$root.showLoading(true)
      this.dataSelect.map(item => {
        console.log('item item', item)
        api.taoDaiSo(this.axios, {dichvu_vienthong_id: this.dichVuID, length: item.length, donvi_id: this.cacDonViID, ghichu: this.ghichu, prefix: item.prefix, so_batdau: item.tuso, so_ketthuc: item.denso, suffix: item.suffix}).then(res => {
          if (res && res.data && res.data.message == 'Success') {
            this.$toast.success(`Tạo dải prefix ${item.prefix}  thành công!`)
            this.$root.showLoading(false)
          } else {
            this.$toast.error(`Tạo dải prefix ${item.prefix} lỗi`)
            this.$root.showLoading(false)
          }
        }).catch((e) => {
          this.$toast.error(`Cập nhật dữ liệu lỗi`)
          this.$root.showLoading(false)
        })
      })
    },
    handleShowModal () {
      this.loaiSoID = this.dataItem.loaiSoID
      this.quanHuyenId = this.dataItem.quanHuyenId
      this.cacDonViID = this.dataItem.donViID
      this.phuong_id = this.dataItem.phuong_id
      this.tongDaiID = this.dataItem.tongDaiID
      this.dichVuID = this.dataItem.dichVuID
      this.$store.commit('prefixOfCounty/SET_PARAM_FIXED', {...this.paramFixedNumber, donViID: this.cacDonViID})
      this.$store.commit('prefixOfCounty/SET_PARAM_FIXED', {...this.paramFixedNumber, tongDaiID: this.tongDaiID})
      this.$store.commit('prefixOfCounty/SET_PARAM_FIXED', {...this.paramFixedNumber, phuong_id: this.phuong_id})
      this.$store.commit('prefixOfCounty/SET_PARAM_FIXED', {...this.paramFixedNumber, loaiSoID: this.loaiSoID})
      this.$store.commit('prefixOfCounty/SET_PARAM_FIXED', {...this.paramFixedNumber, quanHuyenId: this.quanHuyenId})
      this.$store.commit('prefixOfCounty/SET_PARAM_FIXED', {...this.paramFixedNumber, dichVuID: this.dichVuID})
      this.getDanhSachPrefixDaChon()
    },
    handleCacDonViID () {
      this.getDanhSachPrefixDaChon()
      this.$store.commit('prefixOfCounty/SET_PARAM_FIXED', {...this.paramFixedNumber, donViID: this.cacDonViID})
    },
    handleTongDaiID () {
      this.getDanhSachPrefixDaChon()
      this.$store.commit('prefixOfCounty/SET_PARAM_FIXED', {...this.paramFixedNumber, tongDaiID: this.tongDaiID})
    },
    handlePhuongXaID () {
      this.getDanhSachPrefixDaChon()
      this.$store.commit('prefixOfCounty/SET_PARAM_FIXED', {...this.paramFixedNumber, phuong_id: this.phuong_id})
    },
    handleCacLoaiSoID () {
      this.getDanhSachPrefixDaChon()
      if (this.loaiSoID == 1) {
        const params = '1,10,11'
        this.layDanhSachDichVu({ ID: params })
      } else {
        const params = '4,8,7,9,12,13'
        this.layDanhSachDichVu({ ID: params })
      }
      this.$store.commit('prefixOfCounty/SET_PARAM_FIXED', {...this.paramFixedNumber, loaiSoID: this.loaiSoID})
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
    },

    isValidForm () {
      // this.warningInput()

      if (!this.loaiSoID) {
        return false
      }

      if (!this.quanHuyenId) {
        return false
      }

      if (!this.cacDonViID) {
        return false
      }

      if (!this.phuong_id) {
        return false
      }

      if (!this.tongDaiID) {
        return false
      }
      if (!this.dichVuID) {
        return false
      }
      return true
    }

  }
}
</script>
