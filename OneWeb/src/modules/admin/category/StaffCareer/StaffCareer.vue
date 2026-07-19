<template>
  <div class="main-wrapper">
    <div class="breadcrumb-top">
      <div class="main-title">gán nhân viên với ngành nghề</div>
      <ul class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="#"
            ><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ
          </a>
        </li>
        <li class="breadcrumb-item"><a href="#">Lập hợp đồng</a></li>
        <li class="breadcrumb-item active">Lắp đặt mới</li>
      </ul>
    </div>
    <header-search-staff @search="searchStaff"></header-search-staff>
    <div class="page-content">
      <form-search-staff />
      <table-list-staff></table-list-staff>

      <div class="box-move-select-table">
        <table-list-career-not-maping> </table-list-career-not-maping>

        <div class="actions">
          <button class="btn">
            <span class="-ap icon-chevron-thin-right"></span>
          </button>
          <button class="btn">
            <span class="-ap icon-chevron-thin-left"></span>
          </button>
        </div>

        <table-list-career-maping></table-list-career-maping>
      </div>
    </div>
  </div>
</template>

<script>
import HeaderSearchStaff from './part/HeaderSearchStaff.vue'
import FormSearchStaff from './part/FormSearchStaff.vue'
import TableListStaff from './part/TableListStaff.vue'
import TableListCareerNotMaping from './part/TableListCareerNotMaping.vue'
import TableListCareerMaping from './part/TableListCareerMaping.vue'

import api from './api'

export default {
  name: 'StaffCareer',
  components: {
    HeaderSearchStaff,
    FormSearchStaff,
    TableListStaff,
    TableListCareerMaping,
    TableListCareerNotMaping
  },
  data: () => ({}),
  methods: {
    searchStaff () {
      let data = {
        p_donvi_id: 185, // this.unit
        p_loaikhuvuc: this.typeOfArea,
        p_loainv_id: this.typeOfStaff,
        p_ma_nv: '0104'
      }

      api.searchStaff(this.axios, data).then((response) => {
      })
    }
  }
}
</script>
