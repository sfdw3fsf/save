<template>
  <div class="">
    <div class="list-actions-top">
      <ul class="list">
        <li v-if="tsbtnNhapMoi.visible" :class="{ disabledInput: !tsbtnNhapMoi.enabled }" @click="tsbtnNhapMoi_Click">
          <a> <span class="icon act -ap  icon  one-search ui-1_check-circle-08"></span>Tìm kiếm</a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="row">
          <div class="info-row col-sm-4 col-12">
            <div class="key w100">
              Số phiếu
            </div>
            <div class="value">
              <input type="text" v-model="txtSoPhieu.value" class="form-control" @keyup.enter="txtSoPhieu_KeyPress" />
            </div>
          </div>
        </div>
        <div class="title-bg-main">
          <span class="title">Thông tin đồng bộ</span>
        </div>
        <div class="table-content">
          <DataGrid ref="grcTT" :columns="grcTT.header" :dataSource="grcTT.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :enabledSelectFirstRow="false" />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import breadcrumb from '@/components/breadcrumb'
import api from './api'
export default {
  components: { breadcrumb },
  props: {
    vchungtu_id: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    sophieu: {
      type: [String, Number],
      defaul: null,
      require: false
    }
  },
  data() {
    return {
      tsbtnNhapMoi: { visible: true, enabled: true },
      txtSoPhieu: { visible: true, enabled: true, value: null },
      grcTT: {
        header: [
          { fieldName: 'IMS', headerText: 'Trạng thái IMS', allowFiltering: true },
          { fieldName: 'FMIS', headerText: 'Trạng thái FIMS - KTBĐ', allowFiltering: true },
          { fieldName: 'XACNHAN_IN', headerText: 'Xác nhận in phiếu', allowFiltering: true }
        ],
        list: [],
        value: null
      }
    }
  },
  methods: {
    async frmTraCuuTTDongBo_Load() {
      this.txtSoPhieu.value = this.sophieu
      await this.lay_ds()
    },
    async lay_ds() {
      try {
        
      } catch(e) {
        this.$toast.error(`Có lỗi xay ra: ${e}`)
      }
      let area = ''
      if (this.$root.token.getMaTinh() == 'BCN') area = 'BKN'
      else if (this.$root.token.getMaTinh() == 'VTU') area = 'BRA'
      else if (this.$root.token.getMaTinh() == 'HAG') area = 'HGI'
      else if (this.$root.token.getMaTinh() == 'DAN') area = 'DNO'
      else area = this.$root.token.getMaTinh()
      this.loading(true)
      let dongbo = (await api.kiem_tra_trang_thai_ims(this.axios, {
        soPhieu: this.txtSoPhieu.value + '|' + area,
        iMode: '0'
      })).data.data
      this.grcTT.list = (await api.kiem_tra_trang_thai_dong_bo_phieu(this.axios, {
        soPhieu: this.txtSoPhieu.value,
        kqIms: dongbo
      })).data.data
      this.loading(false)
    },
    async tsbtnNhapMoi_Click() {
      await this.lay_ds()
    },
    async txtSoPhieu_KeyPress() {
      await this.tsbtnNhapMoi()
    }
  },
  async mounted() {
    await this.frmTraCuuTTDongBo_Load()
  }
}
</script>
