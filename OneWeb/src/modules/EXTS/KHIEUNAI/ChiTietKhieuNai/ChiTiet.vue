<template src="./ChiTiet.html"></template>
<script>
export default {
  props: {
    khieuNaiID: {
      type: Number,
      default: null
    }
  },
  data: function() {
    return {
      KHIEUNAI: {},
      phieuXMCurrent: {},
      dsTTTD: [],
      dsTTGTC: [],
      dsPhieuXMKN: [],
      dsTTXuLy: [],
      dsTTXM: [],
      dsTTGT: [],
      dsTTXMGT: [],
      collapseBangTTTN: false,
      collapseBangTTGQ: false,
      collapseBangTTGT: false,
      collapseBangKQNT: false,
      collapseBangTTGPXM: false,
      collapseBangKQXLPXM: false,
      collapseBangPhieuXMKN: false,
      optXuLyTN: '0',
      optXuLyGQ: '1',
      currentTab: 1,
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' }
    }
  },
  watch: {},
  created: async function() {},
  methods: {
    async clearAll() {
      this.KHIEUNAI = {}
      this.phieuXMCurrent = {}
      this.optXuLyTN = '0'
      this.optXuLyGQ = '1'
      this.dsTTTD = []
      this.dsTTGTC = []
      this.dsPhieuXMKN = []
      this.dsTTXuLy = []
      this.dsTTXM = []
      this.dsTTGT = []
      this.dsTTXMGT = []
      this.currentTab = 1
    },
    onGetData: async function(id) {
      this.$root.showLoading(true)
      try {
        await this.clearAll()
        let rs = await this.apiChiTiet(id)
        if (rs) {
          this.KHIEUNAI = rs
          if (this.KHIEUNAI.DS_TT_XM != null) {
            this.dsTTXM = JSON.parse(this.KHIEUNAI.DS_TT_XM)
          } else {
            this.dsTTXM = []
          }
          if (this.KHIEUNAI.DS_TT_GT != null) {
            this.dsTTGT = JSON.parse(this.KHIEUNAI.DS_TT_GT)
          } else {
            this.dsTTGT = []
          }
          await this.onclickTab(this.currentTab)
        } else {
          //this.$toast.error('Tải dữ liệu không thành công!')
        }
      } finally {
        this.$root.showLoading(false)
      }
    },
    async onclickTab(tabId) {
      this.currentTab = tabId
      if (!this.KHIEUNAI) return
      const id = this.KHIEUNAI.KHIEUNAI_ID
      if (tabId === 1) {
        this.dsTTXuLy = await this.apiXuLy(id)
      } else if (tabId === 2) {
        this.dsPhieuXMKN = await this.apiXacMinh(id)
      } else if (tabId == 3) {
        this.dsTTGTC = await this.apiGiamCuoc(id)
      } else if (tabId == 4) {
        this.dsTTTD = await this.apiLichSu(id)
      }
    },
    closeDialog() {
      this.clearAll()
      this.$refs.dialogChiTietKhieuNai.hide()
    },
    apiChiTiet: async function(id) {
      this.loading(true)
      let result
      try {
        let rs = await this.$root.context.post(`web-thuno/api/thu-no/khieu-nai/chi-tiet`, {
          khieuNaiId: id
        })
        result = rs.data[0]
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      } finally {
        this.loading(false)
      }
      return result
    },
    apiLichSu: async function(id) {
      this.loading(true)
      let result
      try {
        let rs = await this.$root.context.post(`web-thuno/api/thu-no/khieu-nai/lich-su`, {
          khieuNaiId: id
        })
        result = rs.data
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      } finally {
        this.loading(false)
      }
      return result
    },
    apiXuLy: async function(id) {
      this.loading(true)
      let result
      try {
        let rs = await this.$root.context.post(`web-thuno/api/thu-no/khieu-nai/xu-ly`, {
          khieuNaiId: id
        })
        result = rs.data
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      } finally {
        this.loading(false)
      }
      return result
    },
    apiGiamCuoc: async function(id) {
      let result
      this.loading(true)
      try {
        let rs = await this.$root.context.post(`web-thuno/api/thu-no/khieu-nai/giam-cuoc`, {
          khieuNaiId: id
        })
        result = rs.data
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      } finally {
        this.loading(false)
      }
      return result
    },
    apiXacMinh: async function(id) {
      this.loading(true)
      let result
      try {
        let rs = await this.$root.context.post(`web-thuno/api/thu-no/khieu-nai/xac-minh`, {
          khieuNaiId: id
        })
        result = rs.data
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      } finally {
        this.loading(false)
      }
      return result
    },
    async dialogOpen() {
      try {
        this.loading(true)
        this.clearAll()
        if (8884) {
          await this.onGetData(8884)
        }
        this.$refs.dialogChiTietKhieuNai.show() // Hiển thị dialog
      } catch (e) {
        // Hiển thị thông báo lỗi nếu có vấn đề
        console.log('loi', e)
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message)
        } else {
          this.$toast.error('Đã xảy ra lỗi khi mở dialog')
        }
      } finally {
        this.loading(false) // Tắt trạng thái loading
      }
    },
    grdItems_XMKN_RowSelected: async function(args) {
      try {
        this.$root.showLoading(true)
        this.setCurrentXMKNItem(args.data)
      } finally {
        this.$root.showLoading(false)
      }
    },

    grdItems_XMKN_RowDeselected: async function() {
      let items = this.$refs.grdPhieuXMKN.getSelectedRecords()
      if (items && items.length > 0) {
        try {
          this.$root.showLoading(true)
          this.setCurrentXMKNItem(items[0])
        } finally {
          this.$root.showLoading(false)
        }
      } else {
        this.setCurrentXMKNItemDefault()
      }
    },
    setCurrentXMKNItem: async function(data) {
      this.phieuXMCurrent = data
      if (this.phieuXMCurrent.DS_TT_XM_GT != null) {
        this.dsTTXMGT = JSON.parse(this.phieuXMCurrent.DS_TT_XM_GT)
      } else {
        this.dsTTXMGT = []
      }
    },
    setCurrentXMKNItemDefault: async function() {
      this.phieuXMCurrent = {}
      this.dsTTXMGT = []
    }
  },
  mounted() {}
}
</script>
<style>
.bg-glue {
  background: #dcf4ff;
  padding: 5px;
  font-weight: bold;
  color: #000;
  border-radius: 5px;
}
</style>
