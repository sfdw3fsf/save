import moment from 'moment'

export default {
  name: 'DacTinhThietBi',
  props: {
    currentItem: {
      type: Object,
      default: () => {}
    },
    isServer: {
      type: Boolean,
      default: () => false
    },
    isNetwork: {
      type: Boolean,
      default: () => false
    },
    isDiskShelf: {
      type: Boolean,
      default: () => false
    },
    isIDC: {
      type: Boolean,
      default: () => false
    },
    isOther: {
      type: Boolean,
      default: () => false
    }
  },
  data() {
    return {
      manufacturerList: [],
      deviceRoleList: [],
      supplierList: [],
      OSList: [],
      usageStatusList: [],
      // currentItem: this.$parent.currentItem,
      dacTinhThietBiControls: {
        txt_vaitrothietbi: {
          enabled: false
        },
        txt_psu_qty_in: {
          enabled: false,
          invalid: true
        },
        txt_psu_qty_out: {
          enabled: false,
          invalid: true
        },
        txt_cauhinh: {
          enabled: false
        },
        select_hangsx_id: {
          enabled: false
        },
        txt_namsx: {
          enabled: false
        },
        select_nhacungcap_id: {
          enabled: false
        },
        txt_cs_dinhdanh: {
          enabled: false
        },
        txt_cs_thucte: {
          enabled: false
        },
        select_hedieuhanh_id: {
          enabled: false
        },
        select_trangthai_sd: {
          enabled: false
        },
        date_ngay_lapdat: {
          enabled: false
        },
        date_ngay_baohanh: {
          enabled: false
        },
        date_ngay_sudung: {
          enabled: false
        },
        txt_cpu_sokhe: {
          enabled: false
        },
        txt_cpu_dacam: {
          enabled: false
        },
        txt_cpu_total: {
          enabled: false
        },
        txt_hdd_sokhe: {
          enabled: false
        },
        txt_hdd_dacam: {
          enabled: false
        },
        txt_hdd_total: {
          enabled: false
        },
        txt_ram_sokhe: {
          enabled: false
        },
        txt_ram_dacam: {
          enabled: false
        },
        txt_ram_total: {
          enabled: false
        },
        txt_controller_number: {
          enabled: false,
          invalid: false
        },
        txt_controller_start: {
          enabled: false,
          invalid: false
        },
        txt_controller_name: {
          enabled: false,
          invalid: false
        },
        txt_disc_number: {
          enabled: false,
          invalid: false
        },
        txt_disc_start: {
          enabled: false,
          invalid: false
        },
        txt_disc_name: {
          enabled: false,
          invalid: false
        }
      },
      vaiTroNormalizer(node) {
        return {
          id: node.ID,
          label: node.TEN
        }
      }
      // isServer: this.$parent.isServer,
      // isNetwork: this.$parent.isNetwork,
      // isDiskShelf: this.$parent.isDiskShelf,
      // isIDC: this.$parent.isIDC,
      // isOther: this.$parent.isOther
    }
  },
  computed: {
    check_psu_qty_in() {
      const val = this.currentItem.psu_qty_in
      const check = val === '' || Number.isNaN(Number(val))
      return check
    },
    check_psu_qty_out() {
      const val = this.currentItem.psu_qty_out
      const check = val === '' || Number.isNaN(Number(val))
      return check
    }
  },
  watch: {
    'currentItem.ngay_lapdat': function(val) {
      this.currentItem.ngay_lapdat = moment(val).format('YYYY-MM-DD')
    },
    'currentItem.ngay_baohanh': function(val) {
      this.currentItem.ngay_baohanh = moment(val).format('YYYY-MM-DD')
    },
    'currentItem.ngay_sudung': function(val) {
      this.currentItem.ngay_sudung = moment(val).format('YYYY-MM-DD')
    },
    'currentItem.bodieukhien_qty': function(val) {
      const value = this.returnNumberValue(val)
      this.currentItem.bodieukhien_qty = value
      if (this.isDiskShelf) {
        this.dacTinhThietBiControls.txt_controller_number.invalid = Number.isNaN(value)
      }
    },
    'currentItem.bodieukhien_dau': function(val) {
      const value = this.returnNumberValue(val)
      this.currentItem.bodieukhien_dau = value
      if (this.isDiskShelf) {
        this.dacTinhThietBiControls.txt_controller_start.invalid = Number.isNaN(value)
      }
    },
    'currentItem.bodieukhien_fname': function(val) {
      if (this.isDiskShelf) {
        this.dacTinhThietBiControls.txt_controller_name.invalid = !val
      }
    },
    'currentItem.khaydia_qty': function(val) {
      const value = this.returnNumberValue(val)
      this.currentItem.khaydia_qty = value
      if (this.isDiskShelf) {
        this.dacTinhThietBiControls.txt_disc_number.invalid = Number.isNaN(value)
      }
    },
    'currentItem.khaydia_dau': function(val) {
      const value = this.returnNumberValue(val)
      this.currentItem.khaydia_dau = value
      if (this.isDiskShelf) {
        this.dacTinhThietBiControls.txt_disc_start.invalid = Number.isNaN(value)
      }
    },
    'currentItem.khaydia_fname': function(val) {
      if (this.isDiskShelf) {
        this.dacTinhThietBiControls.txt_disc_name.invalid = !val
      }
    },
    'currentItem.cpu_sokhe': function(val) {
      if (!val || val < 0) {
        this.currentItem.cpu_sokhe = 0
      }
      this.currentItem.cpu_dacam = Math.min(this.currentItem.cpu_sokhe, this.currentItem.cpu_dacam)
    },
    'currentItem.hdd_sokhe': function(val) {
      if (!val || val < 0) {
        this.currentItem.hdd_sokhe = 0
      }
      this.currentItem.hdd_dacam = Math.min(this.currentItem.hdd_sokhe, this.currentItem.hdd_dacam)
    },
    'currentItem.ram_sokhe': function(val) {
      if (!val || val < 0) {
        this.currentItem.ram_sokhe = 0
      }
      this.currentItem.ram_dacam = Math.min(this.currentItem.ram_sokhe, this.currentItem.ram_dacam)
    },
    'currentItem.cpu_dacam': function(val) {
      if (!val || val < 0) {
        this.currentItem.cpu_dacam = 0
      }
      this.currentItem.cpu_dacam = Math.min(this.currentItem.cpu_sokhe, this.currentItem.cpu_dacam)
    },
    'currentItem.hdd_dacam': function(val) {
      if (!val || val < 0) {
        this.currentItem.hdd_dacam = 0
      }
      this.currentItem.hdd_dacam = Math.min(this.currentItem.hdd_sokhe, this.currentItem.hdd_dacam)
    },
    'currentItem.ram_dacam': function(val) {
      if (!val || val < 0) {
        this.currentItem.ram_dacam = 0
      }
      this.currentItem.ram_dacam = Math.min(this.currentItem.ram_sokhe, this.currentItem.ram_dacam)
    },
    'currentItem.chungloaithietbi_id': function(newVal, oldVal) {
      if (newVal) {
        this.dacTinhThietBiControls.select_hangsx_id.enabled = false
        this.dacTinhThietBiControls.txt_cs_dinhdanh.enabled = false
      }
    }
  },
  async created() {
    await this.loadInfo()
    this.usageStatusList = [
      {
        ID: 1,
        TEN: 'Đang sử dụng'
      },
      {
        ID: 0,
        TEN: 'Không sử dụng'
      }
    ]
  },
  methods: {
    async loadInfo() {
      await Promise.all([this.getManufacturer(), this.getOS(), this.getDeviceRole(), this.getSupplier()])
    },
    handleErrorResponse(error) {
      if (!(error.response == null || error.response == undefined)) {
        if (!(error.response.data == null || error.response.data == undefined)) {
          this.$root.toastError(error.response.data.message_detail)
        }
      }
    },
    validateControls() {
      let msg = ''
      if (this.isDiskShelf) {
        if (
          this.dacTinhThietBiControls.txt_controller_number.invalid ||
          this.dacTinhThietBiControls.txt_controller_start.invalid ||
          this.dacTinhThietBiControls.txt_controller_name.invalid
        ) {
          msg += '\n\r'
          msg += 'Đặc tính bộ điều khiển của tủ đĩa không được để trống'
        }

        if (
          this.dacTinhThietBiControls.txt_disc_number.invalid ||
          this.dacTinhThietBiControls.txt_disc_start.invalid ||
          this.dacTinhThietBiControls.txt_disc_name.invalid
        ) {
          msg += '\n\r'
          msg += 'Đặc tính bộ khay đĩa của tủ đĩa không được để trống'
        }
      }

      if (this.isOther) {
        if (this.check_psu_qty_in || this.check_psu_qty_out) {
          msg += '\n\r'
          msg += 'Số cổng nguồn không được để trống'
        }
      }

      return msg
    },
    returnNumberValue(text) {
      return Number.parseFloat(text) ? Number.parseFloat(text) : 0
    },
    enableControls(args) {
      Object.keys(this.dacTinhThietBiControls).forEach((key) => {
        if (this.dacTinhThietBiControls[key].hasOwnProperty('enabled')) {
          this.dacTinhThietBiControls[key].enabled = args
        }
      })
    },
    async getManufacturer() {
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/hangsanxuat/danhsach')
        this.manufacturerList = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getOS() {
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/he-dieuhanh/get-items')
        this.OSList = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getDeviceRole() {
      try {
        let rs = await this.$root.context.get('/web-ecms/hatang/loai-vaitro/v2/get-items?validity=1')
        this.deviceRoleList = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getSupplier() {
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/nhacungcap/danhsach')
        this.supplierList = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    }
  }
}
