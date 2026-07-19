<template src="./index.template.html"></template>

<script>
import TabCPU from './components/CPU/tab-cpu'
import TabRAM from './components/RAM/tab-ram'
import TabOCung from './components/OCung/tab-ocung'
import TabGPU from './components/GPU/tab-gpu'
import TabBlade from './components/Blade/tab-blade'
import TabBDK from './components/BoDieuKhien/tab-bdk'
import TabKhayDia from './components/KhayDia/tab-khaydia'
import TabFAN from './components/FAN/tab-fan'
import TabPCI from './components/PCI/tab-pci'

export default {
  props: {
    currentItem: {
      type: Object,
      required: true
    },
    // controls: {
    //   type: Object,
    //   required: true
    // },
    deviceTypeInfo: {
      type: Object,
      default: () => ({
        phanLoai: null,
        ten: null,
        id: null,
        isServer: false,
        isNetwork: false,
        isServerBlade: false,
        isBlade: false,
        isTuDia: false,
        isBoDieuKhien: false,
        isKhayDia: false,
        isLinecard: false,
        isPowerCard: false,
        isConsoleCard: false
      })
    }
  },
  components: {
    TabCPU,
    TabRAM,
    TabOCung,
    TabGPU,
    TabBlade,
    TabBDK,
    TabKhayDia,
    TabFAN,
    TabPCI,
  },
  data() {
    return {
      test1: [],
      test: [
        {
          '1': 'cpu socket 1',
          '2': 'intel xeon e5-2620 v4',
          '3': '8',
          '4': '16',
          '5': 'đang hoạt động',
          '6': 'xem chi tiết'
        },
        {
          '1': 'cpu socket 2',
          '2': 'intel xeon e5-2620 v4',
          '3': '8',
          '4': '16',
          '5': 'đang hoạt động',
          '6': 'xem chi tiết'
        },
        {
          '1': 'cpu socket 3',
          '2': 'intel xeon e5-2620 v4',
          '3': '8',
          '4': '16',
          '5': 'chưa hoạt động',
          '6': 'xem chi tiết'
        },
        {
          '1': 'cpu socket 4',
          '2': 'intel xeon e5-2620 v4',
          '3': '8',
          '4': '16',
          '5': 'đang hoạt động',
          '6': 'xem chi tiết'
        }
      ],
      currentTab: 1,
      thietBiCurrent: {},
      dsCPU:[],
      dsRAM:[], 
      dsGPU:[],
      dsBlade:[],
      dsOCung:[],
      dsPhanCung:[],
      dsKhayDia:[],
      dsBodKh:[],
    }
  },
  computed: {
    grditems: function() {
      // dữ liệu mẫu – người sau chỉ cần thay thế bằng api
    }
  },
  created: async function() {
    try {
      this.$root.showLoading(true)
      await this.loadData()
    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
    loadData: async function() {
      if (this.currentItem.id) {
        this.thietBiCurrent = await this.apiThongtinthietbi();
        
        if (this.deviceTypeInfo.isServer || this.deviceTypeInfo.isBlade) {
          this.dsCPU = await this.apiDsCpu();
          this.dsRAM = await this.apiDsRam();
          this.dsGPU = await this.apiDsGpu();
          this.dsOCung = await this.apiDsOcung();
        }
        else if (this.deviceTypeInfo.isServerBlade) {
          this.dsBlade = await this.apiDsBlade();
        }
        else if (this.deviceTypeInfo.isTuDia) {
          this.dsKhayDia = await this.apiDsKhaydia();
          this.dsBodKh = await this.apiDsBodkh();
        }
      }
    },
    //Định dạng tên
    formatName(pattern, index) {
      if (!pattern) {
        return String(index)
      }
      return `${pattern}_${index}`
    },
    apiThongtinthietbi: async function() {
      let result = []
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.post(`/web-ecms/hatang/cauhinh-phancung-thietbi/thongtin`, {
          thietbi_id: this.currentItem.id
        })
        result = rs.data[0]
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      finally {
        this.$root.showLoading(false)
      }
      return result
    },
    apiDsRam: async function() {
      let result = []
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.post(`/web-ecms/hatang/cauhinh-phancung-thietbi/ds-ram`, {
          thietbi_id: this.currentItem.id
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      finally {
        this.$root.showLoading(false)
      }
      return result
    },
    apiDsCpu: async function() {
      let result = []
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.post(`/web-ecms/hatang/cauhinh-phancung-thietbi/ds-cpu`, {
          thietbi_id: this.currentItem.id
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      finally {
        this.$root.showLoading(false)
      }
      return result
    },
    apiDsGpu: async function() {
      let result = []
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.post(`/web-ecms/hatang/cauhinh-phancung-thietbi/ds-gpu`, {
          thietbi_id: this.currentItem.id
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      finally {
        this.$root.showLoading(false)
      }
      return result
    },
    apiDsBlade: async function() {
      let result = []
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.post(`/web-ecms/hatang/cauhinh-phancung-thietbi/ds-blade`, {
          thietbi_id: this.currentItem.id
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      finally {
        this.$root.showLoading(false)
      }
      return result
    },
    apiDsOcung: async function() {
      let result = []
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.post(`/web-ecms/hatang/cauhinh-phancung-thietbi/ds-ocung`, {
          thietbi_id: this.currentItem.id
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      finally {
        this.$root.showLoading(false)
      }
      return result
    },
    apiDsPhanCung: async function() {
      let result = []
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.post(`/web-ecms/hatang/cauhinh-phancung-thietbi/ds-phancung`, {
          thietbi_id: this.currentItem.id
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      finally {
        this.$root.showLoading(false)
      }
      return result
    },
    apiDsKhaydia: async function() {
      let result = []
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.post(`/web-ecms/hatang/cauhinh-phancung-thietbi/ds-khaydia`, {
          thietbi_id: this.currentItem.id
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      finally {
        this.$root.showLoading(false)
      }
      return result
    },
    apiDsBodkh: async function() {
      let result = []
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.post(`/web-ecms/hatang/cauhinh-phancung-thietbi/ds-bodkh`, {
          thietbi_id: this.currentItem.id
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      finally {
        this.$root.showLoading(false)
      }
      return result
    },
    
  }
}
</script>
<style>
.hardware-monitor-container {
  padding: 20px;
  font-family: -apple-system, blinkmacsystemfont, 'segoe ui', roboto, sans-serif;
}

.resource-card {
  border: 1px solid #ddd;
  border-radius: 12px;
  padding: 20px;
  text-align: center;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  transition: all 0.2s ease;
}

.resource-card:hover {
  transform: translatey(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.resource-card .icon {
  font-size: 2rem;
  margin-bottom: 12px;
  color: #0d6efd;
}

.resource-card .label {
  font-size: 0.9rem;
  color: #6c757d;
  margin-bottom: 4px;
}

.resource-card .value {
  font-size: 1.5rem;
  font-weight: bold;
  color: #212529;
}

.resource-card .progress {
  height: 6px;
  border-radius: 3px;
  margin-top: 10px;
  background-color: #e9ecef;
}

.resource-card .title {
  font-size: 1rem;
  color: #495057;
  margin-top: 8px;
}

/* đảm bảo input trong box-form căn chỉnh đẹp */
.info-row .key {
  min-width: 150px;
}
</style>
