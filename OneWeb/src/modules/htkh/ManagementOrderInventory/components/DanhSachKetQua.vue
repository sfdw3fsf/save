<template>
  <div class="box-form">
    <div class="legend-title">
      <span>Danh Sách Kết quả</span>
    </div>
    <div class="tab-checkbox">
      <div class="table-content manual-fill">
        <DataGrid :columns="danhSachKetQua.headers" :dataSource="danhSachKetQua.dataSources" :showColumnCheckbox="false" @rowSelected="getData" :enable-paging-server="false" :allowPaging="true" :showFilter="true" @selectedRowChanged="onSelectedRowChanged" />
      </div>
    </div>
  </div>
</template>

<script>
import SockJS from 'sockjs-client' // Import SockJS
import { Stomp } from '@stomp/stompjs' // Import Stomp
import moment from 'moment'
import _ from 'lodash'
import helper from '../helper'
import API from '../api'
import apiHelper from '../api.helper'
import EventBus from '@/utils/eventBus'

export default {
  name: 'DanhSachKetQua',
  props: {},
  data() {
    return {
      huy_donhang_req: {
        phanvung_id: '',
        hdtb_id: ''
      },
      danhSachKetQua: {
        headers: [
          { fieldName: 'tentinh', headerText: 'Tỉnh', allowFiltering: true, visible: true },
          { fieldName: 'ten_dv', headerText: 'Đơn vị', allowFiltering: true, visible: true }, // thieu
          { fieldName: 'ma_gd', headerText: 'Mã giao dịch', allowFiltering: true, visible: true },
          { fieldName: 'ten_kh', headerText: 'Tên khách hàng', allowFiltering: true, visible: true },
          { fieldName: 'diachi_kh', headerText: 'Địa chỉ KH', allowFiltering: true, visible: true },
          { fieldName: 'so_dt', headerText: 'Số ĐT', allowFiltering: true, visible: true },
          { fieldName: 'nguoi_cn', headerText: 'Nhân viên', allowFiltering: true, visible: true }, // thieu
          { fieldName: 'trangthai_hd', headerText: 'Trạng thái hợp đồng', allowFiltering: true, visible: true },
          { fieldName: 'ngay_yc', headerText: 'Ngày yêu cầu', allowFiltering: true, visible: true },

          { fieldName: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true, visible: false },
          { fieldName: 'ungdung', headerText: 'Loại hình', allowFiltering: true, visible: false }
        ],
        dataSources: [],
        selectedRow: null
      },
      stompClient: null,
      domainlocal: process.env.API + '/web-ipcc',
      socket: null
    }
  },
  computed: {
    cptHeader() {
      return 'Bearer ' + JSON.parse(localStorage.getItem('OneBSS-Token')).access_token
    },
    userCode() {
      return this.$root.token.getUserName()
    }
  },

  methods: {
    getData(e) {
      console.log(e.data)
      if (e.data) {
       this.huy_donhang_req.phanvung_id = e.data.tinh_id
       this.huy_donhang_req.hdtb_id = e.data.hdtb_id 
      }
    },

    async huy_donhang() {
      console.log(this.huy_donhang_req.phanvung_id)
      console.log(this.huy_donhang_req.hdtb_id)
      let resp = await API.huy_giaophieu(this.axios,{
        p_phanvung_id: this.huy_donhang_req.phanvung_id,
        p_hdtb_id: this.huy_donhang_req.hdtb_id
      })
      console.log(this.huy_donhang_req)
      if(resp.data && resp.data.error_code == "BSS-00000000"){
        this.$toast.success("Hủy giao đơn hàng thành công")
        if(this.danhSachKetQua.dataSources.length > 0){
          this.danhSachKetQua.dataSources = this.danhSachKetQua.dataSources.filter(x => x["hdtb_id"] != this.huy_donhang_req.hdtb_id)
          console.log(this.danhSachKetQua.dataSources)
        }
      }else{
        this.$toast.error(resp.data.message.toString())
      }
    },

    onSelectedRowChanged(item) {
      this.danhSachKetQua.selectedRow = null
      if (!item) return
      this.danhSachKetQua.selectedRow = item
      this.$emit('on-row-selected', item)
    },

    getDanhSachDonHang(param) {
      try {
        this.$parent.onSetButtonEnable('tsbtnStop', true)
        this.$parent.onSetButtonEnable('tsbtnSearch', false)

        this.danhSachKetQua.dataSources = []
        const headers = { Authorization: this.cptHeader }

        console.log(this.domainlocal)
        this.socket = new SockJS(this.domainlocal + '/ipcc-endpoint') // Adjust the URL
        this.stompClient = Stomp.over(this.socket)

        this.stompClient.connect(headers, () => {
          this.reconnect = true
          console.log('Connected')

          this.socket.onclose = () => {
            if (!this.reconnect) {
              return
            }

            console.error('WebSocket connection closed or encountered an error')
            setTimeout(() => {
              if (this.reconnect) {
                this.connect()
              }
            }, 5000)
          }

          var topic = this.userCode + '_' + moment().format('DDMMYYHHmmss') + String(Math.floor(Math.random() * 1000))
          this.stompClient.subscribe('/topic/' + topic, (message) => {
            console.log('body', message.body)

            let obj = JSON.parse(message.body)
            if (obj['Status'] == '1') {
              this.disconnect()
            } else {
              const result = obj.Data
              // Data se tra ve o day, xu ly de show len man hinh
              if (result?.length) this.danhSachKetQua.dataSources = _.concat(this.danhSachKetQua.dataSources, result)
            }
          })

          const data = {
            UserCode: topic,
            service: 'SP_TRACUUHOPDONGTOANQUOC', // Fix
            input: param
          }

          // Gửi dữ liệu tới server
          console.log('dữ liệu: ', JSON.stringify(data))
          this.stompClient.send(topic, headers, JSON.stringify(data))
        })
      } catch (err) {
        console.log('lay danh sach don hang loi: ', err)
        this.$parent.onSetButtonEnable('tsbtnStop', false)
        this.$parent.onSetButtonEnable('tsbtnSearch', true)
      }
    },
    disconnect() {
      this.reconnect = false
      if (this.socket) this.socket.close()
      console.log('Disconnected')
      this.$parent.onSetButtonEnable('tsbtnSearch', true)
      this.$parent.onSetButtonEnable('tsbtnStop', false)
    },

    async xoaPhieu() {
      if (!this.danhSachKetQua.selectedRow) {
        this.$toast.warning('Không có dữ liệu !')
        return
      }

      try {
        this.loading(true)
        const { tinh_id, hdkh_id, tthd_id } = this.danhSachKetQua.selectedRow
        const param = { tinh_id, hdkh_id, tthd_id }
        const response = await API.deleteOrderInventory(this.axios, param)
        const errMsg = apiHelper.getSrtCodeError(response)
        console.log('errMsg', errMsg)
        this.loading(false)
        if (errMsg != 'OK') {
          this.$toast.error(errMsg)
          return
        }
        this.danhSachKetQua.dataSources = this._removeItemFormArray(this.danhSachKetQua.dataSources, this.danhSachKetQua.selectedRow)
        this.$toast.success('Xoá phiếu thành công')
      } catch (error) {
        this.$toast.error('Có lỗi xảy ra: ', error)
        this.loading(false)
        console.log('err:', error)
      }
    },
    searchDetail() {
      if (!this.danhSachKetQua.selectedRow) return this.$toast.error('Không có dữ liệu để tìm kiếm')
      const { ma_tb, tinh_id } = this.danhSachKetQua.selectedRow
      EventBus.$emit('emitChuyenTinh', { id: String(tinh_id), reload: false })

      let query = { ma_tb }
      let routeData = this.$router.resolve({
        name: 'SearchInstallDetail',
        query: query,
        path: '/search/SearchInstallDetail'
      })
      window.open(routeData.href, '_blank')
      // this.$router.push({ path: '/search/SearchInstallDetail', query: { ma_tb: ma_tb}})
    },

    ExportExcel() {
      if (!this.danhSachKetQua.dataSources.length) return this.$toast.error('Không có dữ liệu để xuất Excel !')

      helper.exportExcel(this.danhSachKetQua.dataSources, 'Don_Hang_Ton')
    },

    _removeItemFormArray(array, { tinh_id, hdkh_id, tthd_id }) {
      return _.remove(array, function (item) {
        return tinh_id != item.tinh_id && hdkh_id != item.hdkh_id && tthd_id != item.tthd_id
      })
    }
  },
  watch: {}
}
</script>