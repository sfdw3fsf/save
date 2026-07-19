<template>
  <div class="box-form">
    <div class="legend-title">Thông tin tra cứu</div>
    <div class="row">
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <div class="key w80">Nguồn Đơn</div>
          <div class="value">
            <div class="select-custom">
              <SelectExt dataValueField="ungdung_id" dataTextField="ten_ungdung" v-model.number="searchInfo.ungDungId" :settings="{ dropdownParent: $refs['ungDungId'] }" :dataSource="ungDungOptions" />
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <div class="key w80">Loại KH</div>
          <div class="value">
            <div class="select-custom">
              <SelectExt dataValueField="id" dataTextField="text" v-model.number="searchInfo.loaiKHId" :dataSource="loaiKHOptions" />
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <div class="key w80">Đơn Vị</div>
          <div class="value">
            <div class="select-custom">
              <SelectExt dataValueField="id" dataTextField="text" v-model.number="searchInfo.donViId" :settings="{ dropdownParent: $refs['donViId'] }" :dataSource="donviOptions" />
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <vue-input labelWidth="100" label="Mã Kênh Bán" v-model="searchInfo.maKenh" />
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <vue-input labelWidth="90" label="Mã đơn hàng" v-model="searchInfo.maDonHang" />
        </div>
      </div>
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <div class="key w80">Dịch vụ</div>
          <div class="value">
            <div class="select-custom">
              <SelectExt dataValueField="id" dataTextField="text" v-model.number="searchInfo.dichVuVtId" :settings="{ dropdownParent: $refs['dichVuVtId'] }" :dataSource="cboDVOptions" @change="onChangeDichVuVT" />
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <div class="key w80">Loại Hình</div>
          <div class="value">
            <div class="select-custom">
              <SelectExt dataValueField="id" dataTextField="text" v-model="searchInfo.loaiHinhId" :settings="{ dropdownParent: $refs['loaiHinhId'] }" :dataSource="cboLoaiHinhTBOptions" />
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <div class="key w100">SDT Kênh Bán</div>
          <div class="value">
            <input type="number" class="form-control" v-model="searchInfo.sdtKenhBan" />
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <div class="key w90">
            <span class="name">Từ Ngày</span>
          </div>
          <div class="value " >
            <date-picker format="DD/MM/YYYY" v-model="searchInfo.dtpTuNgay" type="date">
              <template #icon-calendar>
                <span class="icon one-calendar"></span>
              </template>
            </date-picker>
          </div>
        </div>
      </div>
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <div class="key w90">
            <span class="name">Đến ngày</span>
          </div>
          <div class="value">
            <date-picker format="DD/MM/YYYY" v-model="searchInfo.dtpDenNgay" type="date">
              <template #icon-calendar>
                <span class="icon one-calendar"></span>
              </template>
            </date-picker>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from '../api.js'
import apiHelper from '../api.helper.js'
import DatePicker from 'vue2-datepicker'
import moment from 'moment'
import _ from 'lodash'

export default {
  name: 'ThongTinTraCuu',
  components: {
    DatePicker
  },
  props: {
    dichVuOptions: {
      type: Array,
      default: function() {
        return [] // Return a new array
      }
    },
    ungDungOptions: {
      type: Array,
      default: function() {
        return [] // Return a new array
      }
    }
  },
  data() {
    return {
      searchInfo: {
        ungDungId: '-1',
        loaiKHId: null,
        donViId: 0,
        maKenh: '',
        maDonHang: '',
        sdtKenhBan: '',
        dichVuVtId: 0,
        loaiHinhId: 0,
        dtpTuNgay: new Date(),
        dtpDenNgay: new Date()
      },
      cboLoaiHinhTBOptions: [],
      donviOptions: [],
      maKenhOptions: [],
      loaiKHOptions: [],
      cboDVOptions: [],

    }
  },
  computed: {},
  async mounted() {
    await this.onloadComponent()
  },

  methods: {
    async onloadComponent() {
      await this.getCboLoaiKH()
      await this.getCboDonVi()
    },

    async onChangeDichVuVT() {
      if (!this.searchInfo.dichVuVtId) return

      await this.getCboLoaiHinhThueBao(this.searchInfo.dichVuVtId)
    },

    async getCboLoaiHinhThueBao(dichVuVtId) {
      if (!dichVuVtId) return

      const response = await api.getcboLoaiHinhTBTheoDV(this.axios, { params: { dichVuVtId: dichVuVtId } })
      this.searchInfo.loaiHinhId = 0
      this.cboLoaiHinhTBOptions = apiHelper.getDataFromResponseApiReturnArray(response).map((x) => ({ id: x.ID, text: x.NAME }))
    },
    async getCboLoaiKH() {
      const response = await api.getCboLoaiKH(this.axios)
      this.loaiKHOptions = apiHelper.getDataFromResponseApiReturnArray(response).map((x) => ({ id: x.loaikh_id, text: x.loai_kh }))

      if (this.loaiKHOptions.length > 0) this.searchInfo.loaiKHId = this.loaiKHOptions[0].id
      else this.searchInfo.loaiKHId = 0
    },

    async getCboDonVi() {
      const response = await api.getCboDonViXuLyDon(this.axios)
      // console.log("getCboDonVi",response)
      this.donviOptions = apiHelper.getDataFromResponseApiReturnArray(response).map((x) => ({ id: x.donvi_id, text: x.ten_dv }))
    },

    async getCboDichVu() {
      const response = await api.getcboDichVuVT(this.axios, {})
      this.cboDVOptions = apiHelper.getDataFromResponseApiReturnArray(response)
    }
  },

  watch: {
    searchInfo: {
      handler: function(val, oldVal) {
        this.$emit('searchInfo-change', val)
      },
      deep: true
    },
    dichVuOptions: {
      handler: function(val, oldVal) {
        this.cboDVOptions = val
      },
      deep: true
    }
  }
}
</script>
<style scoped>
.select-custom {
  margin-left: 8px;
}
/* Remove Arrows/Spinners Chrome, Safari, Edge, Opera */
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}
</style>
