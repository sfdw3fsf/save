
export default {
  name: 'TabBackup',
  components: {
  },
  props: {
    thongTinCard: {
      type: Object
    },
    backupList: {
      type: Array,
      default: () => []
    }
  },
  data: function() {
    return {
      backupCols: [
        { fieldName: 'sothutu', headerText: 'STT', textAlign: 'Left' },
        { fieldName: 'kientruc', headerText: 'Kiến trúc/Thành phần', textAlign: 'Left' },
        { fieldName: 'thoigian_khoitao', headerText: 'Thời gian khởi tạo', textAlign: 'Left' },
        { fieldName: 'thoigian_batdau', headerText: 'Thời gian bắt đầu', textAlign: 'Left' },
        { fieldName: 'thoigian_ketthuc', headerText: 'Thời gian kết thúc', textAlign: 'Left' },
        { fieldName: 'backup_taptrung', headerText: 'Backup tập trung', textAlign: 'Left' },
        {
          fieldName: 'backup_manual',
          headerText: 'Backup manual',
          textAlign: 'Left',
          template: this.getBackupColumnTemplate()
        },
        { fieldName: 'dungluong_backup', headerText: 'Dung lượng backup', textAlign: 'Left' },
        { fieldName: 'dungluong_tangtruong', headerText: 'Dung lượng tăng trưởng', textAlign: 'Left' },
        { fieldName: 'vitri_backup', headerText: 'Vị trí', textAlign: 'Left' },
        { fieldName: 'tansuat_backup', headerText: 'Tần suất', textAlign: 'Left' },
        { fieldName: 'soban_backup', headerText: 'Số bản bkp', textAlign: 'Left' }
      ]
    }
  },
  computed: {
    grdItems: function() {
      return this.$refs.grdItems
    },
    backupModalRef() {
      return this.$refs.backupModal
    }
  },
  watch: {
    // 'thongTinCard.id': async function(newId) {
    //   if (newId) {
    //     // this.lichSuItemList = await this.getLichSuThayDoiCLTB(newId);
    //     this.thongTinCong.chungloaiThietBiId = this.thongTinCard.id
    //     this.congParam.chungloaiThietBiId = this.thongTinCard.id
    //     this.loadDsCongInterface()
    //   } else {
    //     this.lichSuItemList = []
    //   }
    // }
  },
  created: async function() {
    try {
      this.$root.showLoading(true)
      // if (this.thongTinCard.soLineCard != 0) {

      // } else {
      this.loadDsCongInterface()
      //}
    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
    handleError(error) {
      if (!(error.response == null || error.response == undefined)) {
        if (!(error.response.data == null || error.response.data == undefined)) {
          this.$root.toastError(error.response.data.message_detail)
        }
      }
    },
    getBackupManualText(value) {
      return value == 1 ? 'Có' : 'Không'
    },
    getBackupColumnTemplate() {
      return () => {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function() {
                return this.$parent.$parent.$parent.getBackupManualText(this.data.backup_manual)
              }
            }
          }
        }
      }
    },
    xemDanhSach() {
      // Handle the event when the button is clicked
      this.$refs.dialogDanhSachPortTheoLineCard.dialogOpen()
    },
    updateCong() {
      // Handle the event when the button is clicked
      this.backupModalRef.openDialog()
    },
    khaiBaoCong() {
      // Handle the event when the button is clicked
      this.backupModalRef.openDialog()
    },

    grid_onCommandClicked(name, dataItem) {
      if (name === 'SUA') {
        this.backupModalRef.setCurrentItem(dataItem)
        this.updateCong()
      }
      if (name === 'XOA') {
        this.onDeleteCongInterface(dataItem)
      }
    },
    grdItems_RowSelected: async function(args) {
      try {
        this.$root.showLoading(true)
        this.setCurrentItem(args.data)
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
        } finally {
          this.$root.showLoading(false)
        }
      } else {
        this.setCurrentItemDefault()
      }
    },

    onDeleteCongInterface: async function(args) {
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
        //   let rs = await this.deleteCongInterface(args.ID)
        //   if (rs) {
        //     this.setCurrentItemDefault()
        //     this.loadDsCongInterface()
        //     this.$root.toastSuccess('Xóa cổng thành công')
        //   } else {
        //     this.$root.toastError('Xóa cổng thất bại')
        //   }
        // } finally {
        //   this.$root.showLoading(false)
        // }
        try {
          this.$emit('deleteBackupItem', args)
        } catch (error) {
          this.$root.toastError('Xóa backup thất bại')
        }
      }
    }
  }
}
