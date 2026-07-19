<template>
  <div class="popup-body">
    <div class="box-form">
      <div class="info-row">
        <div class="key w80">Mã thuê bao</div>
        <div class="value">
          <input type="text" v-model.trim="maThueBao" @keyup.enter="txtMaTB_KeyPress" class="form-control" />
        </div>
      </div>
    </div>
    <DataGrid
      :columns="histories.headers"
      :dataSource="histories.dataSources"
      :showColumnCheckbox="false"
      :enable-paging-server="false"
      :allowPaging="true"
      :showFilter="true"
    />
  </div>
</template>

<script>
import api from '../api'
import moment from 'moment'

export default {
  name: 'ViewHistoryPopup',
  data () {
    return {
      maThueBao: '',
      histories: {
        headers: [
          { fieldName: 'ma_tb', headerText: 'Mã TB', allowFiltering: true },
          { fieldName: 'ma_dv_cu', headerText: 'Mã đơn vị cũ', allowFiltering: true },
          { fieldName: 'ma_dv_moi', headerText: 'Mã đơn vị mới', allowFiltering: true },
          { fieldName: 'ten_dv_moi', headerText: 'Đơn vị mới', allowFiltering: true },
          { fieldName: 'ten_dv_nv', headerText: 'Đơn vị (Nhân viên)', allowFiltering: true },
          { fieldName: 'nguoi_cn', headerText: 'Người cập nhật', allowFiltering: true },
          { fieldName: 'ngay_cn', headerText: 'Ngày cập nhật', allowFiltering: true },
          { fieldName: 'may_cn', headerText: 'Máy cập nhật', allowFiltering: true },
          { fieldName: 'ten_nv', headerText: 'Nhân viên cập nhật', allowFiltering: true }
        ],
        dataSources: []
      }
    }
  },
  methods: {
    initData (dataSources) {
      this.maThueBao = ''
      this.histories.dataSources = [...dataSources]
    },
    async txtMaTB_KeyPress () {
      if (!this.maThueBao) {
        this.$root.$toast.warning('Bạm phải nhập mã thuê bao')
        return false
      }
      this.loading(true)
      try {
        const data = await this.getLogBDTBTheoMaThueBao()
        if (data.length > 0) {
          data.forEach(elm => {
            elm.ngay_cn = this.parseDate(elm.ngay_cn)
            return elm
          })
        }
        this.histories.dataSources = [...data]
      } catch (e) {
        this.histories.dataSources = []
      } finally {
        this.loading(false)
      }
    },
    async getLogBDTBTheoMaThueBao () {
      try {
        const response = await api.getLogBDTBTheoMaThueBao(this.axios, this.maThueBao)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin lịch sử')
        return []
      }
    },
    parseDate (date) {
      if (!date) return null
      const format = 'DD/MM/YYYY HH:mm:ss'
      let parsedDate = moment(date, 'ddd MMM DD hh:mm:ss [ICT] YYYY')
      if (!parsedDate.isValid()) parsedDate = moment(date, format)
      if (!parsedDate.isValid()) return null
      return parsedDate.format(format)
    }
  }
}
</script>
