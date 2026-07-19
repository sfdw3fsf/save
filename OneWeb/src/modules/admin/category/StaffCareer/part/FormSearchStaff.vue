<template>
  <div class="box-form">
    <div class="legend-title">Tìm kiếm</div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key">
            <div class="check-action">
              <input type="checkbox" class="check" @change="enableDonViChange()" v-model="unit.enable">
              <span class="name">Đơn vị</span>
            </div>
          </div>
          <div class="value">
            <div class="select-custom">
              <ejs-dropdownlist
                ref="dropdownDonVi"
                :dataSource="unit.options"
                :enabled="unit.enable"
                :allowFiltering="true"
                :fields="{ text: 'ten_dv', value: 'donvi_id' }"
                v-model="selectedUnit"
                @filtering="onFilteringDropDownList($event, unit.options, 'ten_dv')"
              ></ejs-dropdownlist>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key">
            <div class="check-action">
              <input type="checkbox" class="check" @change="enableLoaiNhanVienChange()" v-model="typeOfStaff.enable">
              <span class="name">Loại NV</span>
            </div>
          </div>
          <div class="value">
            <div class="select-custom">
              <ejs-dropdownlist
                ref="dropdownDonVi"
                :dataSource="typeOfStaff.options"
                :enabled="typeOfStaff.enable"
                :allowFiltering="true"
                :fields="{ text: 'TEN', value: 'LOAINV_ID' }"
                v-model="selectedTypeOfStaff"
                @filtering="onFilteringDropDownList($event, typeOfStaff.options, 'TEN')"
              ></ejs-dropdownlist>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w110">
            Mã NV
          </div>
          <div class="value">
            <input type="text" class="form-control highlight" v-model="staffCode" @keyup.enter="searchStaff">
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key">
            <div class="check-action">
              <input type="checkbox" class="check" @change="enableLoaiKhuVucChange()" v-model="typeOfArea.enable">
              <span class="name">Loại khu vực</span>
            </div>
          </div>
          <div class="value">
            <div class="select-custom">
              <ejs-dropdownlist
                ref="danhSachBras"
                :dataSource="typeOfArea.options"
                :enabled="typeOfArea.enable"
                :allowFiltering="true"
                :fields="{ text: 'LOAI_KV', value: 'LOAIKV_ID' }"
                v-model="selectedTypeOfArea"
                @filtering="onFilteringDropDownList($event, typeOfArea.options, 'LOAI_KV')"
              ></ejs-dropdownlist>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import api from '../api'
import { pagingAndFilter } from '../../Staff/mixins/pagingAndFilter'
export default {
  mixins: [pagingAndFilter],
  components: {},
  name: 'FormSearchStaff',
  computed: {
    selectedTypeOfArea: {
      get () {
        return this.$store.state.mapStaffAndCareer.selectedTypeOfArea
      },
      set (value) {
        this.setSelectedTypeOfArea(value)
      }
    },
    selectedTypeOfStaff: {
      get () {
        return this.$store.state.mapStaffAndCareer.selectedTypeOfStaff
      },
      set (value) {
        this.setSelectedTypeOfStaff(value)
      }
    },
    selectedUnit: {
      get () {
        return this.$store.state.mapStaffAndCareer.selectedUnit
      },
      set (value) {
        this.setSelectedUnit(value)
      }
    },
    staffCode: {
      get () {
        return this.$store.state.mapStaffAndCareer.staffCode
      },
      set (value) {
        this.setStaffCode(value)
      }
    }
  },
  data: () => ({
    unit: {
      enable: false,
      options: []
    },
    typeOfStaff: {
      enable: false,
      options: []
    },
    typeOfArea: {
      enable: false,
      options: []
    }
  }),
  created () {
    this.getListUnitManagement()
    this.getTypeOfStaff()
    this.getTypeOfArea()
  },
  methods: {
    ...mapMutations('mapStaffAndCareer', [
      'setSelectedUnit',
      'setSelectedTypeOfStaff',
      'setSelectedTypeOfArea',
      'setStaffCode',
      'setStaffList',
      'setSelectedStaff',
      'setCareerMappedList',
      'setCareerNotMappedList'
    ]),
    getListUnitManagement () {
      api
        .getListUnitManagement(this.axios, {
          donvi_id: 0,
          loaidv_id: 2
        })
        .then((response) => {
          this.unit.options = response.data.data
        })
    },
    getTypeOfStaff () {
      const param = {
        p_is_insert: 1,
        p_data: {
          LOAITB_ID: 120,
          PHANLOAI: 'VNPT TEST',
          PHANLOAI_ID: 0
        }
      }
      api.getTypeOfStaff(this.axios, param).then((response) => {
        this.typeOfStaff.options = response.data.data
      })
    },
    getTypeOfArea () {
      let param = {
        p_phanloai_id: 50
      }
      api.getTypeOfArea(this.axios, param).then((response) => {
        this.typeOfArea.options = response.data.data
      })
    },
    enableDonViChange () {
      if (!this.unit.enable) {
        this.selectedUnit = null
      }
    },
    enableLoaiNhanVienChange () {
      if (!this.typeOfStaff.enable) {
        this.selectedTypeOfStaff = null
      }
    },
    enableLoaiKhuVucChange () {
      if (!this.typeOfArea.enable) {
        this.selectedTypeOfArea = null
      }
    },
    async searchStaff () {
      const data = {
        'p_donvi_id': this.selectedUnit,
        'p_loaikhuvuc': this.selectedTypeOfArea,
        'p_loainv_id': this.selectedTypeOfStaff,
        'p_ma_nv': this.staffCode
      }
      this.loading(true)
      await api.searchStaff(this.axios, data)
        .then(response => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.setStaffList(response.data.data)
          } else {
            this.setStaffList([])
            this.$toast.error('Không có thông tin tìm kiếm !')
            this.setCareerMappedList([])
            this.setCareerNotMappedList([])
            this.setSelectedStaff({})
          }
        })
        .finally(() => {
          this.loading(false)
        })
        .catch(err => {
          this.$toast.error(err.response.data.message)
        })
    }
  }
}
</script>
