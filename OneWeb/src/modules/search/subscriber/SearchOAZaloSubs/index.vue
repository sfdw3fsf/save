<template>
  <div class="main-wrapper">
    <div class="breadcrumb-top">
      <div class="main-title">Tra cứu khách hàng quan tâm OA</div>
      <ul class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a>
        </li>
        <li class="breadcrumb-item"><a href="#">Lập hợp đồng</a></li>
        <li class="breadcrumb-item active">Lắp đặt mới</li>
      </ul>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="getDsKhachHang"> <span class="icon one-search"></span>Tìm kiếm </a>
        </li>
        <li>
          <a href="#" @click.prevent="exportExcel"> <span class="icon one-xlsx-import"></span>Xuất excel </a>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <div class="box-form">
        <div class="legend-title">Thông tin tra cứu</div>
        <div class="row">
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key">Số điện thoại <span style="color: #dc3545"> (*)</span></div>
              <div class="value">
                <input type="text" class="form-control" :class="{ 'is-invalid': $v.ma_thuebao.$error }" ref="ma_thuebao" v-model.trim="$v.ma_thuebao.$model" v-on:keyup.enter="getDsKhachHang" />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">Danh sách khách hàng</div>
        <!--
                 <gridView :headers="DsKhachHang.headers" :datalist="DsKhachHang.data" :flcol="DsKhachHang.flcol"
                                :showCheck='false' :showHeader='true'>
                            </gridView>
                            -->
        <DataGrid v-bind:columns="DsKhachHang.headers2" v-bind:dataSource="DsKhachHang.data" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true" ref="refDsKhachHang"> </DataGrid>
      </div>
    </div>
  </div>
</template>
<script>
import gridView from '@/modules/complaint/ResovleComplaintAfterSales/components/gridview.vue'
import treegridView from '@/modules/complaint/ResovleComplaintAfterSales/components/treegridview.vue'
import breadcrumb from '@/components/breadcrumb'
import { required, minLength, alphaNum } from 'vuelidate/lib/validators'
import XLSX from 'xlsx'
import moment from 'moment'
import api from './Api'
export default {
  components: {
    breadcrumb,
    gridView,
    treegridView,
    XLSX,
    moment
  },
  props: ['ma_tb'],
  name: 'SearchOAZaloSubs',
  created() {
    if (this.ma_tb != null && this.ma_tb != undefined && this.ma_tb.length > 0) {
      this.DsKhachHang.ma_thuebao = this.ma_tb
      this.getDsKhachHang()
    }
  },
  mounted() {
    this.$refs.ma_thuebao.focus()
  },
  data() {
    return {
      header: {
        title: 'Tra cứu',
        list: [
          { name: ' TRA CỨU KHÁCH HÀNG QUAN TÂM OA', link: { name: 'Ui.cards' } },
          {
            name: 'TRA CỨU KHÁCH HÀNG QUAN TÂM OA',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      DsKhachHang: {
        headers: [
          { name: 'ma_kh', text: 'Mã khách hàng', value: '' },
          { name: 'ten_kh', text: 'Tên khách hàng', value: '' },
          { name: 'ma_tt', text: 'Mã thanh toán', value: '' },
          { name: 'ten_tt', text: 'Tên thanh toán', value: '' },
          { name: 'diachi_tt', text: 'Địa chỉ thanh toán', value: '' },
          { name: 'manv_tc', text: 'Mã Đường thu', value: '' },
          { name: 'da_quantam_oa', text: 'Quan tâm OA', value: '', type: 'bool' },
          { name: 'mucdich_sd', text: 'Mục đích liên hệ', value: '' }
        ],
        headers2: [
          { fieldName: 'ma_kh', headerText: 'Mã khách hàng' },
          { fieldName: 'ten_kh', headerText: 'Tên khách hàng' },
          { fieldName: 'ma_tt', headerText: 'Mã thanh toán' },
          { fieldName: 'ten_tt', headerText: 'Tên thanh toán' },
          { fieldName: 'diachi_tt', headerText: 'Địa chỉ thanh toán' },
          { fieldName: 'manv_tc', headerText: 'Mã Đường thu' },
          { fieldName: 'quantam_oa', headerText: 'Quan tâm OA', textAlign: 'Center', allowHtml: true, width: 100 },
          { fieldName: 'mucdich_sd', headerText: 'Mục đích liên hệ', value: '' }
        ],
        data: [],
        test: ''
      },
      ma_thuebao: '' // 0906046385
    }
  },
  validations: {
    ma_thuebao: {
      required,
      minLength: minLength(10),
      alphaNum
    }
  },
  methods: {
    getDsKhachHang: async function () {
      try {
        this.$v.$touch()
        if (this.$v.$invalid) {
          this.$refs.ma_thuebao.focus()
          return
        }
        if (this.ma_thuebao.length <= 0) {
          this.ShowError(`Bạn chưa nhập thông tin tìm kiếm !`)
          this.$refs.ma_thuebao.focus()
          return
        }
        var input = { ma_thuebao: this.ma_thuebao }
        this.$root.showLoading(true)
        this.DsKhachHang.data = this.GetData(await api.getDsKhachHang(this.axios, input))
        if (this.DsKhachHang.data == null || this.DsKhachHang.data.length == 0) {
          this.ShowError(`Không tìm thấy thông tin tìm kiếm !`)
          return
        }
        this.DsKhachHang.data.forEach(function (item) {
          if (item.da_quantam_oa == 1) {
            item.quantam_oa = '<input type="checkbox" disabled checked />'
          } else {
            item.quantam_oa = '<input type="checkbox" disabled  />'
          }
        })

        this.$root.showLoading(false)
      } catch (err) {
      } finally {
        this.$root.showLoading(false)
      }

      /*
      if (this.DsKhachHang.data==null || this.DsKhachHang.data.length <= 0) {
        this.ShowWarning(`Không tìm thấy thông tin thuê bao ${this.DsKhachHang.ma_thuebao}!`)
      }
      */
    },
    exportExcel: function () {
      if (this.DsKhachHang.data != null && this.DsKhachHang.data.length > 0) {
        let temp = this.DsKhachHang.data.map((x) => ({
          'Mã Kh': x.ma_kh,
          'Tên Kh': x.ten_kh,
          'Mã TT': x.ma_tt,
          'Tên TT': x.ten_tt,
          'Địa chỉ TT': x.diachi_tt,
          'Mã Đường thu': x.manv_tc,
          'Quan tâm OA': x.da_quantam_oa,
          'Mục đích liên hệ': x.mucdich_sd
        }))
        // let data = XLSX.utils.json_to_sheet(this.DsKhachHang.data)
        let data = XLSX.utils.json_to_sheet(temp)
        let wb = XLSX.utils.book_new() // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data, 'OAZaloSubs') // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, 'OAZaloSubs.xlsx')
      } else this.$toast.error('Không có dữ liệu!')
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    ShowWarning: function (message) {
      this.$toast.error(message)
    },
    ShowError: function (message) {
      this.$toast.error(message)
    },
    ShowMessage: function (message) {
      this.$toast.warning(message)
    }
  }
}
</script>
<style>
div.is-invalid {
  border-color: #dc3545;
  border: 1px solid #dc3545;
  background-position: right calc(0.375em + 0.1875rem) center;
}
</style>
