
export default {
  name: 'TabIDG',
  components: {
  },
  props: {
    thongTinCard: {
      type: Object
    },
    IDGList: {
      type: Array,
      default: () => []
    }
  },
  data: function() {
    return {
      IDGCols: [
        { fieldName: 'sothutu', headerText: 'STT', textAlign: 'Left' },
        { fieldName: 'kientruc', headerText: 'Kiến trúc/Thành phần', textAlign: 'Left' },
        { fieldName: 'thoigian_khoitao', headerText: 'Thời gian khởi tạo', textAlign: 'Left' },
        { fieldName: 'thoigian_batdau', headerText: 'Thời gian bắt đầu', textAlign: 'Left' },
        { fieldName: 'thoigian_ketthuc', headerText: 'Thời gian kết thúc', textAlign: 'Left' },
        { fieldName: 'rabitmq', headerText: 'RabitMQ', textAlign: 'Left' },
        { fieldName: 'object', headerText: 'Object', textAlign: 'Left' },
        { fieldName: 'ma_kafka', headerText: 'Mã Kafka', textAlign: 'Left' },
        { fieldName: 'ma_api_gateway', headerText: 'API GateWay', textAlign: 'Left' },
        { fieldName: 'ma_mongodb', headerText: 'MongoDB', textAlign: 'Left' },
        { fieldName: 'ma_mysql', headerText: 'MySQL', textAlign: 'Left' },
        { fieldName: 'ma_search_engine', headerText: 'Search Engine', textAlign: 'Left' }
      ]
    }
  },
  updated() {},
  computed: {
    grdItems: function() {
      return this.$refs.grdItems
    },
    IDFModalRef() {
      return this.$refs.IDGModal
    },
    grdItems_haTangMang: function() {
      return this.$refs.grdItems_haTangMang
    },
    grdItems_subnet: function() {
      return this.$refs.grdItems_subnet
    },
    grdItems_history: function() {
      return this.$refs.grdItems_history
    }
  },
  watch: {
    // 'thongTinCard.id': async function(newId) {
    //   if (newId) {
    //     // this.lichSuItemList = await this.getLichSuThayDoiCLTB(newId);
    //     this.thongTinCong.chungloaiThietBiId = this.thongTinCard.id
    //     this.congParam.chungloaiThietBiId = this.thongTinCard.id
    //     this.loadDsCong()
    //   }
    // }
  },
  created: async function() {
    try {
      this.$root.showLoading(true)
    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
    updateCong() {
      // Handle the event when the button is clicked
      this.IDFModalRef.openDialog()
    },
    khaiBaoCong() {
      // Handle the event when the button is clicked
      this.IDFModalRef.openDialog()
    },
    enableControls: function(args) {
      for (const key in this.controls) {
        if (this.controls.hasOwnProperty(key)) {
          this.controls[key].enabled = args
        }
      }

      this.buttons.btnSave.enabled = args
      this.buttons.btnAddNew.enabled = !args
      this.buttons.btnDelete.enabled = !args

      if (this.currentItem.id > 0) this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled
    },
    validateControls: function() {
      let msg = ''
      if (this.controls.txtModelThietBi.invalid) {
        msg = 'Tên không được để trống.'
      }
      if (this.controls.selectNhaSanXuat.invalid) {
        if (msg != '') msg += '\n\r'
        msg += 'IDC không được để trống.'
      }
      if (this.controls.selectChungLoaiTb.invalid) {
        if (msg != '') msg += '\n\r'
        msg += 'Zone không được để trống.'
      }
      if (this.controls.selectVRF.invalid) {
        if (msg != '') msg += '\n\r'
        msg += 'VRF không được để trống.'
      }
      if (this.controls.txtChieuCao.invalid) {
        if (msg != '') msg += '\n\r'
        msg += 'Thiết bị không được để trống.'
      }
      if (msg != '') this.$root.toastError(msg)
      return msg == ''
    },
    grid_onCommandClicked(name, dataItem) {
      if (name === 'SUA') {
        this.IDFModalRef.setCurrentItem(dataItem)
        this.updateCong()
      }
      if (name === 'XOA') {
        this.onDeleteCong(dataItem)
      }
    },
    grdItems_RowSelected: async function(args) {
      try {
        this.$root.showLoading(true)
        this.setCurrentItem(args.data)
        this.haTangMangItemList = await this.getHaTangItems(args.data.ID)
      } finally {
        this.$root.showLoading(false)
      }
    },
    grdItems_RowDeselected: async function() {
      let items = this.grdItems.getSelectedRecords()
      if (!(items == null || items.length == 0)) {
        try {
          this.$root.showLoading(true)
          this.setCurrentItem(items[0])
          this.haTangMangItemList = await this.getHaTangItems(items[0].ID)
        } finally {
          this.$root.showLoading(false)
        }
      } else {
        this.setCurrentItemDefault()
        this.lichSuItemList = []
        this.haTangMangItemList = []
        this.subnetItemList = []
      }
    },
    onDeleteCong: async function(args) {
      let msg = `Bạn có muốn xóa dữ liệu không?`
      let flag = await this.$confirm(msg, 'Xác nhận', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Không đồng ý',
        type: 'warning',
        dangerouslyUseHTMLString: true
      })
        .then((s) => true)
        .catch((e) => {
          return false
        })
      if (flag) {
        // try {
        //   this.$root.showLoading(true)
        //   let rs = await this.deleteCong(args.ID)
        //   if (rs) {
        //     this.setCurrentItemDefault()
        //     this.loadDsCong()
        //     this.$root.toastSuccess('Xóa cổng thành công')
        //   } else {
        //     this.$root.toastError('Xóa cổng thất bại')
        //   }
        // } finally {
        //   this.$root.showLoading(false)
        // }
        try {
          this.$emit('deleteIDGItem', args)
        } catch (error) {
          this.$root.toastError('Xóa IDG thất bại')
        }
      }
    }
  }
}
