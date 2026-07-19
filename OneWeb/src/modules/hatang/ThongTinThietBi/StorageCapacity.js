export default {
  name: 'StorageCapacity',
  props: {
    isDiskShelf: {
      type: Boolean,
      default: () => false
    }
  },
  data() {
    return {
      currentItem: this.$parent.currentItem,
      nangLucLuuTruControls: {
        txt_hdd_tong: {
          enabled: false,
          invalid: false
        },
        txt_hdd_nangluc: {
          enabled: false,
          invalid: false
        },
        txt_hdd_capphat: {
          enabled: false,
          invalid: false
        },
        txt_ssd_tong: {
          enabled: false,
          invalid: false
        },
        txt_ssd_nangluc: {
          enabled: false,
          invalid: false
        },
        txt_ssd_capphat: {
          enabled: false,
          invalid: false
        },
        txt_iops_tong: {
          enabled: false,
          invalid: false
        },
        txt_iops_nangluc: {
          enabled: false,
          invalid: false
        },
        txt_iops_capphat: {
          enabled: false,
          invalid: false
        },
        txt_mem_cache: {
          enabled: false
        },
        txt_flash_cache: {
          enabled: false
        }
      }
    }
  },
  computed: {},
  watch: {
    'currentItem.hdd_tong': function(val) {
      const value = this.returnNumberValue(val)
      this.currentItem.hdd_tong = value
      if (this.isDiskShelf) {
        this.nangLucLuuTruControls.txt_hdd_tong.invalid = Number.isNaN(value)
      }
    },
    'currentItem.hdd_nangluc': function(val) {
      const value = this.returnNumberValue(val)
      this.currentItem.hdd_nangluc = Math.min(value, this.currentItem.hdd_tong)
      if (this.isDiskShelf) {
        this.nangLucLuuTruControls.txt_hdd_nangluc.invalid = Number.isNaN(value)
      }
      this.currentItem.hdd_capphat = this.currentItem.hdd_tong - this.currentItem.hdd_nangluc
    },
    'currentItem.hdd_capphat': function(val) {
      const value = this.returnNumberValue(val)
      this.currentItem.hdd_capphat = value
      if (this.isDiskShelf) {
        this.nangLucLuuTruControls.txt_hdd_capphat.invalid = Number.isNaN(value)
      }
    },
    'currentItem.ssd_tong': function(val) {
      const value = this.returnNumberValue(val)
      this.currentItem.ssd_tong = value
      if (this.isDiskShelf) {
        this.nangLucLuuTruControls.txt_ssd_tong.invalid = Number.isNaN(value)
      }
    },
    'currentItem.ssd_nangluc': function(val) {
      const value = this.returnNumberValue(val)
      this.currentItem.ssd_nangluc = Math.min(value, this.currentItem.ssd_tong)
      if (this.isDiskShelf) {
        this.nangLucLuuTruControls.txt_ssd_nangluc.invalid = Number.isNaN(value)
      }
      this.currentItem.ssd_capphat = this.currentItem.ssd_tong - this.currentItem.ssd_nangluc
    },
    'currentItem.ssd_capphat': function(val) {
      const value = this.returnNumberValue(val)
      this.currentItem.ssd_capphat = value
      if (this.isDiskShelf) {
        this.nangLucLuuTruControls.txt_ssd_capphat.invalid = Number.isNaN(value)
      }
    },
    'currentItem.iops_tong': function(val) {
      const value = this.returnNumberValue(val)
      this.currentItem.iops_tong = value
      if (this.isDiskShelf) {
        this.nangLucLuuTruControls.txt_iops_tong.invalid = Number.isNaN(value)
      }
    },
    'currentItem.iops_nangluc': function(val) {
      const value = this.returnNumberValue(val)
      this.currentItem.iops_nangluc = value
      if (this.isDiskShelf) {
        this.nangLucLuuTruControls.txt_iops_nangluc.invalid = Number.isNaN(value)
      }
    },
    'currentItem.iops_capphat': function(val) {
      const value = this.returnNumberValue(val)
      this.currentItem.iops_capphat = value
      if (this.isDiskShelf) {
        this.nangLucLuuTruControls.txt_iops_capphat.invalid = Number.isNaN(value)
      }
    }
  },
  async created() {},
  methods: {
    loadDataFromParent(currentItem) {
      this.currentItem = currentItem
    },
    enableControls(args) {
      Object.keys(this.nangLucLuuTruControls).forEach((key) => {
        if (this.nangLucLuuTruControls[key].hasOwnProperty('enabled')) {
          this.nangLucLuuTruControls[key].enabled = args
        }
      })
    },
    returnNumberValue(text) {
      return Number.parseFloat(text) ? Number.parseFloat(text) : 0
    },
    validateControls() {
      let msg = ''
      if (
        this.nangLucLuuTruControls.txt_hdd_tong.invalid ||
        this.nangLucLuuTruControls.txt_hdd_nangluc.invalid ||
        this.nangLucLuuTruControls.txt_hdd_capphat.invalid
      ) {
        msg += '\n\r'
        msg += 'Khả năng lưu trữ HDD của tủ đĩa không được để trống'
      }
      if (
        this.nangLucLuuTruControls.txt_ssd_tong.invalid ||
        this.nangLucLuuTruControls.txt_ssd_nangluc.invalid ||
        this.nangLucLuuTruControls.txt_ssd_capphat.invalid
      ) {
        msg += '\n\r'
        msg += 'Khả năng lưu trữ SSD của tủ đĩa không được để trống'
      }
      if (
        this.nangLucLuuTruControls.txt_iops_tong.invalid ||
        this.nangLucLuuTruControls.txt_iops_nangluc.invalid ||
        this.nangLucLuuTruControls.txt_iops_capphat.invalid
      ) {
        msg += '\n\r'
        msg += 'Khả năng lưu trữ IOPS của tủ đĩa không được để trống'
      }

      return msg
    },
    getData() {
      return {
        hdd_tong: this.currentItem.hdd_tong,
        hdd_nangluc: this.currentItem.hdd_nangluc,
        hdd_capphat: this.currentItem.hdd_capphat,
        ssd_tong: this.currentItem.ssd_tong,
        ssd_nangluc: this.currentItem.ssd_nangluc,
        ssd_capphat: this.currentItem.ssd_capphat,
        iops_tong: this.currentItem.iops_tong,
        iops_nangluc: this.currentItem.iops_nangluc,
        iops_capphat: this.currentItem.iops_capphat
      }
    }
  }
}
