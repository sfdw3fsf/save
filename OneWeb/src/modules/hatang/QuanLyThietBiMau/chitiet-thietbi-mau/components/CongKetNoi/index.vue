<template src="./index.template.html"></template>
<script>
import TabCongDulieu from './components/TabCongDulieu/index.vue'
import TabCongNguon from './components/TabCongNguon/index.vue'
import TabCongDieuKhien from './components/TabCongDieuKhien/index.vue'
import { PHANLOAI_CONG } from './config.js'
import { getEquipmentTypeFlags } from '../ThongTinChung/equipmentTypeConfig.js'

export default {
  name: 'CongKetNoi',
  components: {
    TabCongDulieu,
    TabCongNguon,
    TabCongDieuKhien
  },
  props: {
    currentItem: {
      type: Object,
      required: true
    },
    currentMode: {
      type: String,
      required: true
    },
    controls: {
      type: Object,
      required: true
    },
    equipmentType: {
      type: String,
      default: null
    }
  },
  data() {
    return {
      currentTab: 1,
      phanLoai: PHANLOAI_CONG.CONG_DU_LIEU
    }
  },
  computed: {
    equipmentTypeInfo() {
      return getEquipmentTypeFlags(this.equipmentType)
    },
    displayTab1() {
      return true
    },
    displayTab2() {
      return true
    },
    displayTab3() {
      return true
    }
  },
  mounted() {
    this.currentTab = 1
    this.phanLoai = PHANLOAI_CONG.CONG_DU_LIEU
  },
  methods: {
    onChangeTab(tabIndex) {
      this.currentTab = tabIndex
      this.phanLoai =
        this.currentTab === 1
          ? PHANLOAI_CONG.CONG_DU_LIEU
          : this.currentTab === 2
          ? PHANLOAI_CONG.CONG_NGUON
          : PHANLOAI_CONG.CONG_DIEU_KHIEN
      
      // Reset port fields when switching tabs
      this.currentItem.so_cong = null
      this.currentItem.bat_dau_cong = null
      this.currentItem.dinh_dang_ten = ''
      this.currentItem.da_su_dung = 0
      this.currentItem.con_trong = 0
    },
    async loadInitialData() {
      // Load data for ports if needed
      if (this.$refs.refCongDulieu && this.$refs.refCongDulieu.loadInitialData) {
        await this.$refs.refCongDulieu.loadInitialData()
      }
      if (this.$refs.refCongNguon && this.$refs.refCongNguon.loadInitialData) {
        await this.$refs.refCongNguon.loadInitialData()
      }
      if (this.$refs.refCongDieuKhien && this.$refs.refCongDieuKhien.loadInitialData) {
        await this.$refs.refCongDieuKhien.loadInitialData()
      }
    }
  }
}
</script>
<style scoped>
</style>
