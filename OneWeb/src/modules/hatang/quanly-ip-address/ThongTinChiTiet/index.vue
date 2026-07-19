<template src="./index.template.html"></template>
<script>
import api from '../api'
import ThongTinChung from './Components/ThongTinChung/index.vue'
import Subnet from './Components/Subnet/index.vue'
import IPStore from './Components/IPStore/index.vue'
import HaTangAoHoa from './Components/HaTangAoHoa/index.vue'
import TabHistory from '../../../tainguyen-idc/components/Tabs/TabHistory/index.vue'
import { TAB_INDICES, LOAI_HATANG } from '../config.js'

export default {
  name: 'ThongTinChiTietIPAddress',
  components: {
    ThongTinChung,
    Subnet,
    IPStore,
    HaTangAoHoa,
    TabHistory
  },
  data() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      currentTab: TAB_INDICES.THONG_TIN_CHUNG,
      TAB_INDICES: TAB_INDICES,
      selectedSubnetId: null,
      ip_id: null,
      modalMode: 'view'
    }
  },
  computed: {},
  watch: {},
  methods: {
    onChangeTab(tabIndex) {
      this.currentTab = tabIndex
    },
    showModal(data, modalMode = 'view') {
      this.$refs.ThongTinChung.reLoadListDanhMuc()
      this.modalMode = modalMode
      this.$refs.ThongTinChung.loadDanhMuc()
      if (data) {
        this.ip_id = data.IP_ID
        this.$refs.ThongTinChung.loadFormData(data.IP_ID)
      } else {
        this.ip_id = null
        this.$refs.ThongTinChung.resetFormData()
      }
      // this.$refs.HaTangAoHoa.dsHaTangTypes = LOAI_HATANG
      this.$refs.ThongTinChiTiet.show()
    },
    closeModal() {
      this.$refs.ThongTinChiTiet.hide()
    },
    onSubnetSelected(subnet) {
      // Khi chọn một subnet, cập nhật selectedSubnetId để IPStore tab có thể load dữ liệu
      this.selectedSubnetId = subnet ? subnet.ID : null
    },
    async onSave() {
      if (!this.$refs.ThongTinChung.validateForm()) {
        return
      }
      const data = this.$refs.ThongTinChung.getFormData()
      console.log('Data to save:', data)

      try {
        this.$root.showLoading(true)
        const response = await api.saveIP(this.$root.context, data)
        if (response && response.error_code === 'BSS-00000000') {
          this.$root.toastSuccess('Lưu thông tin thành công!')
          await this.$parent.loadIpAddresses()
          await this.$refs.grdLichSu.refreshData()
        } else {
          this.$root.toastError(response.message || 'Lưu thông tin thất bại!')
        }
      } catch (e) {
        console.log('Error saving IP:', e)
        const msg =
          e?.response?.data?.message_detail ||
          e?.response?.data?.message ||
          e.message ||
          'Có lỗi xảy ra khi lưu thông tin IP'

        this.$root.toastError(msg)
      } finally {
        this.$root.showLoading(false)
      }
    },
    onCancel() {
      if (this.modalMode === 'create') {
        this.$refs.ThongTinChung.resetFormData()
      } else {
        // Edit mode: reload original data
        this.$refs.ThongTinChung.loadFormData(this.ip_id)
      }
    }
  }
}
</script>
<style scoped>
.close {
  cursor: pointer !important;
}
</style>
