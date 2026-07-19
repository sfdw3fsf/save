<template>
  <div class="main-wrapper">
    <div class="breadcrumb-top">
      <div class="main-title">Tra cứu log cập nhật thông tin thông báo cước</div>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="getDanhSach">
            <span class="icon one-search"></span>Tìm kiếm
          </a>
        </li>
      </ul>

    </div>
    <div class="page-content">
      <div class="box-form">
        <div class="legend-title">Thông tin tra cứu</div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">
                Tìm kiếm theo
              </div>
              <div class="value">
                <Select2 v-model="loaiThanhToan" :options="lsloaiThanhToan" />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="value">
              <input type="text" class="form-control highlight" v-model="codePay">
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">
          <div class="pull-left">
            Thông tin biến động
          </div>
          <div class="clearfix"></div>
        </div>
        <DataGrid v-bind:columns="DsPort.headers2" v-bind:dataSource="DsPort.data" :showColumnCheckbox="false"
          :enable-paging-server="false" :allowPaging="true" :showFilter="true" ref="refDsPort">
        </DataGrid>

      </div>
    </div>
  </div>
</template>
<script>
import gridView from '@/modules/complaint/ResovleComplaintAfterSales/components/gridview.vue'
import treegridView from '@/modules/complaint/ResovleComplaintAfterSales/components/treegridview.vue'
import breadcrumb from '@/components/breadcrumb'
import 'flatpickr/dist/plugins/monthSelect/style.css'
import bssFlatPicker from '@/components/BssDatePicker'
import XLSX from 'xlsx'
import moment from 'moment'
import api from './Api'
export default {
  components: {
    breadcrumb,
    gridView,
    treegridView,
    bssFlatPicker,
    XLSX,
    moment
  },
  name: 'SearchPortDetail',
  data() {
    return {
      DsPort: {
        headers: [
          { name: 'Mã GD', text: 'Mã GD', value: '' },
          { name: 'Mã TT', text: 'Mã TT', value: '' },
          { name: 'Email', text: 'Email', value: '' },
          { name: 'Kênh nhận TB', text: 'Kênh nhận TB', value: '' },
          { name: 'Người CN', text: 'Người CN', value: '' },
          { name: 'Đơn vị CN', text: 'Đơn vị CN', value: '' },
          { name: 'Kênh CN', text: 'Kênh CN', value: '' },
          { name: 'SĐT', text: 'SĐT', value: '' },
          { name: 'Thời gian CN', text: 'Thời gian CN', value: '' },
        ],
        headers2: [
          { fieldName: 'ma_tt', headerText: 'Mã TT', allowFiltering: true, allowSorting: false },
          { fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true, allowSorting: false },
          { fieldName: 'email', headerText: 'Email', allowFiltering: true, allowSorting: false },
          { fieldName: 'kenh_tb', headerText: 'Kênh TB', allowFiltering: true, allowSorting: false },
          { fieldName: 'kenh_cn', headerText: 'Kênh CN', allowFiltering: true, allowSorting: false },
          { fieldName: 'ngay_cn', headerText: 'Ngày CN', allowFiltering: true, allowSorting: false },
          { fieldName: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true, allowSorting: false },
          { fieldName: 'so_dt', headerText: 'SĐT', allowFiltering: true, allowSorting: false },
          { fieldName: 'ten_dv', headerText: 'Tên DV', allowFiltering: true, allowSorting: false },
        ],
        data: []
      },
      Loading: false,
      lsloaiThanhToan: [
        {
          id: 'a.ma_tt=',
          text: 'Mã thanh toán'
        },
        {
          id: 'a.so_dt=',
          text: 'SĐT thanh toán'
        }
      ],
      loaiThanhToan: '',
      codePay: '',
    }
  },
  methods: {
    toDefault1: function (a) {
      if (a === '') return -1
      return a
    },
    getDanhSach: async function () {
      var input = {
        'vdk': this.loaiThanhToan,
        'vtimkiem': this.codePay
      }
      this.$root.showLoading(true)
      api.getDs(this.axios, input).then((res) => {
        if (res && res.data.data.length > 0) {
          this.DsPort.data = res.data.data
        } else {
          this.ShowWarning('Lỗi tìm kiếm dữ liệu')
        }
      });
      console.log(this.DsPort.data)
      this.$root.showLoading(false)
    },
    ShowWarning: function (message) {
      this.$toast.error(message)
    },
    ShowMessage: function (message) {
      alert(message)
    },
  }
}
</script>
