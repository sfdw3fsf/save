// import KhaiBaoCongConsole from '../Modal/khaibao_cong_console.vue'
import IDGModal from '../Modal/IDG.vue'
import { KIENTRUC_THANHPHAN } from '../constants'

export default {
  name: 'TabIDG',
  components: {
    IDGModal
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
        {
          fieldName: 'thanhphan',
          headerText: 'Kiến trúc thành phần',
          textAlign: 'Left',
          template: this.getColumnThanhPhanTemplate()
        },
        { fieldName: 'ngaytao', headerText: 'Thời gian khởi tạo', textAlign: 'Left' },
        { fieldName: 'batdau', headerText: 'Thời gian bắt đầu', textAlign: 'Left' },
        { fieldName: 'ketthuc', headerText: 'Thời gian kết thúc', textAlign: 'Left' },
        { fieldName: 'ma_rabbitmq', headerText: 'RabitMQ', textAlign: 'Left' },
        { fieldName: 'idg_object', headerText: 'Object', textAlign: 'Left' },
        { fieldName: 'ma_kafka', headerText: 'Mã Kafka', textAlign: 'Left' },
        { fieldName: 'ma_api_gateway', headerText: 'API GateWay', textAlign: 'Left' },
        { fieldName: 'ma_mongo', headerText: 'MongoDB', textAlign: 'Left' },
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
    },
    disableAdd() {
      return !this.$parent.buttons.btnSave.enabled
    }
  },
  watch: {},
  created: async function() {
    try {
      this.$root.showLoading(true)
    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
    getColumnThanhPhanTemplate() {
      return () => {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText() {
                return this.$parent.$parent.$parent.getThanhPhanText(this.data.thanhphan)
              }
            }
          }
        }
      }
    },
    getThanhPhanText(value) {
      return KIENTRUC_THANHPHAN.find((item) => item.id == value)?.name || ''
    },
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
      if (this.disableAdd) return
      if (name === 'SUA') {
        this.IDFModalRef.setCurrentItem(dataItem)
        this.updateCong()
      }
      if (name === 'XOA') {
        this.onDeleteCong(dataItem)
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
        try {
          this.$emit('deleteIDGItem', args)
        } catch (error) {
          this.$root.toastError('Xóa IDG thất bại')
        }
      }
    }
  }
}
