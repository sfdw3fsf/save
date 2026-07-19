<template>
  <div class="main-wrapper">
    <div>
      <breadcrumb />
      <div class="list-actions-top">
        <ul class="list">
          <li style="cursor: pointer;">
            <a @click.prevent="tinhThuLao"> <span class="icon one-file-plus"></span> Tính thù lao </a>
          </li>
        </ul>
      </div>
    </div>
    <div class="page-content">
      <div class="box-form">
        <div class="info-row info-time">
          <div class="key w70 nowrap bold">Thời gian</div>
          <div class="value">
            <ejs-datepicker format="MM/yyyy" start="Year" depth="Year" v-model="thoigian" :max="max" />
          </div>
        </div>
        <DataGrid
          ref="gridDanhSach"
          panelDataHeight="500px"
          v-bind:columns="gridDanhSach.header"
          v-bind:dataSource="gridDanhSach.list"
          :enabledSelectFirstRow="false"
          :showFilter="true"
          :enablePagingServer="false"
          :showColumnCheckbox="true"
          :selectionSettings="{ enableToggle: false, checkboxOnly: false, persistSelection: true, checkboxMode: 'ResetOnRowClick' }"
          :allowPaging="true"
          :allowExcelExport="true"
          @rowSelected="gridDanhSach_rowSelected"
        />
        <!-- <div class="row">
        
        <div class="col-sm-6 col-12">
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w70 nowrap">Đơn vị: </div>
            <div class="value">
              <select2 />
            </div>
          </div>
        </div>
      </div> -->
      </div>
    </div>
  </div>
</template>

<script>
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import * as moment from 'moment'
import api from './API'
export default {
  components: {
    breadcrumb
  },
  data() {
    return {
      thoigian: moment().subtract(1, 'months').format('MM/YYYY'),
      max: new Date(new Date().getFullYear(), new Date().getMonth(), 0),
      gridDanhSach: {
        header: [
          { width: 100, fieldName: 'chukyno', headerText: 'Thời gian', allowFiltering: true },
          { fieldName: 'ten_donvi', headerText: 'Đơn vị', allowFiltering: true },
          { fieldName: 'kenhban', headerText: 'Kênh bán', allowFiltering: true },
          { fieldName: 'sohd', headerText: 'Hợp đồng', allowFiltering: true },
          { fieldName: 'ma_tb', headerText: 'Thuê bao', allowFiltering: true },
          { fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowFiltering: true },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true },
          { fieldName: 'congdoan', headerText: 'Công đoạn', allowFiltering: true },
          { fieldName: 'doanhthu', headerText: 'Doanh thu', allowFiltering: true },
          { fieldName: 'tyle_thamgia', headerText: 'Tỷ lệ tham gia', allowFiltering: true },
          { fieldName: 'tyle_hh', headerText: 'Tỷ lệ hoa hồng', allowFiltering: true },
          { fieldName: 'tien_hh', headerText: 'Tiền hoa hồng', allowFiltering: true },
          { fieldName: 'sotienthulao', headerText: 'Số tiền thù lao', allowFiltering: true },
          { fieldName: 'sotienconno', headerText: 'Số tiền còn nợ', allowFiltering: true }
        ],
        list: [],
        selected: [],
        value: null
      }
    }
  },
  methods: {
    async gridDanhSach_rowSelected(e) {},
    async tinhThuLao() {
      try {
        this.loading(true)

        const res = await api.tinhThuLao(this.axios, { p_chuky: moment(this.thoigian, 'MM/YYYY').format('YYYYMM') })
        if (res.data.data != 'ok') {
          this.$toast.error(res.data.data)
        } else {
          this.$toast.success('Tính thù lao thành công')
        }

        let kq = await api.xemsolieuexcel(this.axios, { p_thoigiantu: moment(this.thoigian, 'MM/YYYY').format('YYYYMM') })
        if (kq.data.data && kq.data.data.length) {
          this.gridDanhSach.list = kq.data.data
        } else {
          this.gridDanhSach.list = []
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.info-time {
  max-width: 500px;
  min-width: 200px;
  padding: 20px 0px;
  position: relative;
  margin: 0 auto;
}
</style>
