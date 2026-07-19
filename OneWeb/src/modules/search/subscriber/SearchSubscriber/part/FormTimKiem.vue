<template>
  <vue-card label="Thông tin tìm kiếm">
    <b-row>
      <b-col
        sm="6"
        cols="12"
      >
        <div class="info-row">
          <div class="key w80 nowrap">Dịch vụ VT</div>
          <div class="value">
            <div class="select-custom">
              <SelectExt
                ref="dichvu"
                v-model="form_temp.dichVuVTId"
                :dataSource="options.options_dsdv"
                dataTextField="ten_dvvt"
                dataValueField="dichvuvt_id"
                label="Dịch vụ VT"
              ></SelectExt>
            </div>
          </div>
        </div>
      </b-col>
      <b-col
        sm="3"
        cols="12"
      >
        <vue-date
          ref="tungay"
          labelWidth="60"
          v-model="form_temp.tuNgay"
          type="date"
          format="DD/MM/YYYY"
          label="Từ ngày"
        ></vue-date>
      </b-col>
      <b-col
        sm="3"
        cols="12"
      >
        <vue-date
          ref="denngay"
          labelWidth="80"
          v-model="form_temp.denNgay"
          type="date"
          format="DD/MM/YYYY"
          label="Đến ngày"
        ></vue-date>
      </b-col>
      <b-col
        sm="6"
        cols="12"
      >
        <div class="info-row">
          <div class="key w80 nowrap">Loại HĐ/PL</div>
          <div class="value">
            <div class="select-custom">
              <SelectExt
                ref="hopdong"
                v-model="form_temp.loaiHDId"
                :dataSource="options.options_dslhd"
                id="loaiHD"
                dataTextField="ten_loaihd"
                dataValueField="loaihd_id"
                label="Loại HĐ/PL"
              ></SelectExt>
            </div>
          </div>
        </div>
      </b-col>
      <b-col
        sm="3"
        cols="12"
      >
        <vue-input
          labelWidth="60"
          v-model="form_temp.userCN"
          label="User"
          :checkbox="true"
        ></vue-input>
      </b-col>
      <b-col
        sm="3"
        cols="12"
      >
        <div class="info-row">
          <div class="key w80">
            <div class="check-action">
              <input
                v-model="form_temp.ckDonvi"
                :true-value="1"
                :false-value="0"
                type="checkbox"
                class="check"
              >
              <span class="name">Đơn vị</span>
            </div>
          </div>
          <div class="value">
            <div class="select-custom">
              <ComboboxGrid id="ds-capgoc" ref="ds-capgoc" 
                                v-bind:columns="[
                                {fieldName: 'ma_dv', headerText: 'Mã đơn vị', allowFiltering: true},       
                                {fieldName: 'ten_dv', headerText: 'Tên đơn vị', allowFiltering: true}                                                         
                                ]" 
                                v-bind:dataSource="options.options_dsdonvi" textField="ten_dv" valueField="donvi_id"      
                                :enabled="form_temp.ckDonvi ? true : false"                          
                                @selectedChanged="cboDonvi_changed">
              </ComboboxGrid>
              <!-- <SelectExt
                :disabled="!ckDonvi"
                v-model="form_temp.donViId"
                label="Đơn vị"
                :dataSource="options.options_dsdonvi"
                :checkbox="true"
                dataTextField="ten_dv"
                dataValueField="donvi_id"
              ></SelectExt> -->
            </div>
          </div>
        </div>
      </b-col>
    </b-row>
  </vue-card>
</template>
<script>
import api from '../api'
export default {
  data: () => ({
    ckDonvi: 0,
    options: {
      options_dsdv: [],
      options_dsdonvi: [],
      options_dslhd: []
    },
    form_temp: {
      denNgay: '',
      dichVuVTId: '',
      donViId: '',
      loaiHDId: '',
      tuNgay: '',
      userCN: '',
      ckDonvi: 0
    }
  }),
  computed: {
    vmodel: {
      get() {
        return this.value
      },
      set(key, value) {
        this.$emit('input', this.form_temp)
      }
    }
  },
  props: {
    value: Object
  },
  created() {
    this.$nextTick(async () => {
      this.form_temp = this.value
      this.loading(true)
      await this.getDanhSachDichVu()
      await this.getDanhSachDonVi()
      await this.getDanhSachLoaiHopDong()
      this.loading(false)
    })
  },
  methods: {
    cboDonvi_changed(item) {
      if(item && item.donvi_id)
        this.form_temp.donViId = item.donvi_id
    },
    validate() {

    },
    getDanhSachDichVu() {
      return api.getDanhSachDichVu(this.axios, {
        p_phanvung_id: 26,
        p_nguoidung_id: 1
      }).then(response => {
        this.options.options_dsdv = response.data.data
        this.form_temp.dichVuVTId = this.options.options_dsdv[0] ? this.options.options_dsdv[0].dichvuvt_id : null
      })
    },
    getDanhSachDonVi() {
      return api.getDanhSachDonVi(this.axios, {
        p_phanvung_id: 26
      }).then(response => {
        this.options.options_dsdonvi = response.data.data
      })
    },
    getDanhSachLoaiHopDong() {
      return api.getDanhSachLoaiHopDong(this.axios, {
        loaiNhomId: 1
      }).then(response => {
        this.options.options_dslhd = response.data.data
        this.form_temp.loaiHDId = this.options.options_dslhd[0] ? this.options.options_dslhd[0].loaihd_id : null
      })
    }
  }
}
</script>
