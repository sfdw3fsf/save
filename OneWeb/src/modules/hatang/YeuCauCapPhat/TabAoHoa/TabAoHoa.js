import { KIENTRUC_THANHPHAN } from '../constants'
import AoHoaDialog from '../Modal/aohoa_dialog.vue'

export default {
  name: 'TabAoHoa',
  components: {
    AoHoaDialog
  },
  props: {
    thongTinCard: {
      type: Object
    },
    aoHoaList: {
      type: Array,
      default: () => []
    }
  },
  data: function() {
    return {
      controls: {
        txtThoiGianKhoiTao: {
          enabled: true,
          invalid: false
        },
        txtThoiGianBatDau: {
          enabled: true,
          invalid: false
        },
        txtThoiGianKetThuc: {
          enabled: true,
          invalid: false
        }
      },
      aoHoaCols: [
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
        { fieldName: 'vcpu', headerText: 'CPU', textAlign: 'Left' },
        { fieldName: 'vram', headerText: 'RAM', textAlign: 'Left' },
        { fieldName: 'vgpu', headerText: 'GPU', textAlign: 'Left' },
        { fieldName: 'ssd', headerText: 'SSD', textAlign: 'Left' },
        { fieldName: 'hdd', headerText: 'HDD', textAlign: 'Left' },
        { fieldName: 'iops', headerText: 'IOPS', textAlign: 'Left' },
        { fieldName: 'throughput', headerText: 'Throughput', textAlign: 'Left' },
        { fieldName: 'network_card', headerText: 'Số Card mạng (IP)', textAlign: 'Left' }
      ]
    }
  },
  computed: {
    grdItems: function() {
      return this.$refs.grdItems
    },
    aoHoaModalRef() {
      return this.$refs.dialogAoHoa
    },
    disableAdd() {
      return !this.$parent.buttons.btnSave.enabled
    }
  },
  watch: {},
  created: async function() {
    try {
      this.$root.showLoading(true)
      // if (this.thongTinCard.soLineCard != 0) {

      // } else {
      // this.loadDsAoHoa()
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
      this.aoHoaModalRef.dialogOpen()
    },
    khaiBaoCong() {
      // Handle the event when the button is clicked
      // this.setThongTinCongNewItem()
      this.aoHoaModalRef.dialogOpen()
    },
    grid_onCommandClicked(name, dataItem) {
      if (this.disableAdd) return
      if (name === 'SUA') {
        this.aoHoaModalRef.setCurrentItem(dataItem)
        this.updateCong()
      }
      if (name === 'XOA') {
        this.deleteRow(dataItem)
      }
    },
    deleteRow: async function(args) {
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
          this.$emit('deleteAoHoaItem', args)
        } catch (error) {
          this.$root.toastError('Xóa ảo hóa thất bại')
        }
      }
    }
  }
}
