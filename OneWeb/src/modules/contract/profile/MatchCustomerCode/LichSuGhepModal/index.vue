<template src="./template.html"></template>
<style src="./style.scss"></style>

<script>
import api from '../api'
import moment from 'moment'
export default {
  components: {},
  props: {
    visible: {
      type: Boolean
    },
    maKHGoc: String
  },
  name: 'LichSuGhepModal',
  data() {
    return {
      loading: false,
      maKH: '',
      user: '',
      gridData: { list: [], selected: null },
      gridHeader: [
        {
          fieldName: 'KHGoc',
          headerText: 'Khách hàng gốc',
          columns: [
            { field: 'ma_kh', headerText: 'Mã khách hàng', allowFiltering: true, allowSorting: true },
            { field: 'ten_kh', headerText: 'Tên khách hàng', allowFiltering: true, allowSorting: true }
          ]
        },
        {
          fieldName: 'KHGhep',
          headerText: 'Khách hàng ghép',
          columns: [
            { field: 'ma_tt', headerText: 'Mã TT', allowFiltering: true, allowSorting: true },
            { field: 'ma_tb', headerText: 'Mã TB', allowFiltering: true, allowSorting: true },
            { field: 'ten_tt', headerText: 'Tên thanh toán', allowFiltering: true, allowSorting: true },
            { field: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: true }
          ]
        },
        {
          fieldName: 'TTCN',
          headerText: 'Thông tin cập nhật',
          columns: [
            { field: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true, allowSorting: true },
            { field: 'may_cn', headerText: 'Máy CN', allowFiltering: true, allowSorting: true },
            { field: 'ngay_cn', headerText: 'Ngày CN', allowFiltering: true, allowSorting: true, format: 'yMd' }
          ]
        }
      ],
      searchDate: {
        isEnabledFromDate: false,
        isEnabledToDate: false,
        fromDate: moment(new Date()).format('DD/MM/YYYY'),
        toDate: moment(new Date()).format('DD/MM/YYYY'),
        dateconfig: {
          altFormat: 'd/m/Y',
          altInput: true,
          dateFormat: 'd/m/Y',
          allowInput: true
        }
      }
    }
  },
  computed: {
    showModal: {
      get() {
        return this.visible
      },
      set(val) {
        this.$emit('set-visible', val)
      }
    }
  },
  watch: {
    loading: function (val, oldval) {
      this.$root.showLoading(val)
    },
    showModal: function (val, oldval) {
      if (!val) {
        window.removeEventListener('keydown', this.onF5KeyDown)
      } else {
        this.clearData()
        if (this.maKHGoc && this.maKHGoc !== '') {
          this.maKH = this.maKHGoc
          this.$nextTick(() => {
            this.$refs.maKHInput.focus()
          })
        }
        window.addEventListener('keydown', this.onF5KeyDown)
      }
    }
  },
  methods: {
    setFocusMaKHInut() {
      this.$refs.maKHInput.focus()
    },
    onF5KeyDown(event) {
      if (event.keyCode === 116) {
        event.preventDefault()
        this.getData()
        console.log('Tra cuu')
      }
    },
    async getData() {
      if (this.maKH !== '') {
        this.loading = true
        try {
          this.gridData = { list: [], selected: {} }
          const params = {
            ma_kh: this.maKH.toString().trim(),
            nguoi_cn: this.user.toString().trim(),
            from_date: this.searchDate.isEnabledFromDate ? this.searchDate.fromDate : moment(new Date()).format('DD/MM/YYYY'),
            to_date: this.searchDate.isEnabledToDate ? this.searchDate.toDate : moment(new Date()).format('DD/MM/YYYY')
          }
          let response = await api.getLSGMKH(this.axios, params)
          if ((response.data.error === 200 || response.data.error === '200') && response.data.data && response.data.data.length > 0) {
            // const data = response.data.data
            // this.gridData.list = data.map((x) => {
            //   return { KHGoc: { ma_kh: x.ma_kh, ten_kh: x.ten_kh }, KHGhep: { ma_tt: x.ma_tt, ma_tb: x.ma_tb, ten_tt: x.ten_tt, ten_tb: x.ten_tb }, TTCN: { nguoi_cn: x.nguoi_cn, may_cn: x.may_cn, ngay_cn: x.ngay_cn } }
            // })
            this.gridData.list = response.data.data
          } else {
            console.log(response.data.error_code)
            this.$toast.error('Không tìm thấy thông tin khách hàng')
          }
        } catch (err) {
          console.error(err)
        } finally {
          this.loading = false
        }
      } else {
        this.$toast.error('Bạn chưa nhập mã khách hàng!')
      }
    },
    gridSelectedItemsChanged(value) {
      this.gridData.selected = value
    },
    show() {
      this.showModal = true
    },
    hide() {
      this.showModal = false
    },
    formClose(value) {
      this.$emit('form-close', value)
    },
    clearData() {
      this.maKH = ''
      this.user = ''
      this.searchDate.isEnabledFromDate = false
      this.searchDate.isEnabledToDate = false
      this.searchDate.fromDate = moment(new Date()).format('DD/MM/YYYY')
      this.searchDate.toDate = moment(new Date()).format('DD/MM/YYYY')
    }
  }
}
</script>
