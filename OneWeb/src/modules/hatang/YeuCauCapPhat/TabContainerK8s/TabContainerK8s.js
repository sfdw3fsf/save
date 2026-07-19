import KhaiBaoCongPower from '../Modal/khaibao_cong_power.vue'
import ContainerK8sModal from '../Modal/container_k8s.vue'
import { KIENTRUC_THANHPHAN } from '../constants'

export default {
  name: 'TabContainerK8s',
  components: {
    KhaiBaoCongPower,
    ContainerK8sModal
  },
  props: {
    thongTinCard: {
      type: Object
    },
    containerList: {
      type: Array,
      default: () => []
    }
  },
  data: function() {
    return {
      containerCols: [
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
        { fieldName: 'cpu', headerText: 'CPU', textAlign: 'Left' },
        { fieldName: 'ram', headerText: 'RAM', textAlign: 'Left' },
        // { fieldName: 'ssd', headerText: 'SSD', textAlign: 'Left' },
        // { fieldName: 'hdd', headerText: 'HDD', textAlign: 'Left' },
        { fieldName: 'storage', headerText: 'Storage', textAlign: 'Left' },
        { fieldName: 'pod', headerText: 'Số lượng Pob', textAlign: 'Left' }
      ]
    }
  },
  computed: {
    grdItems: function() {
      return this.$refs.grdItems
    },
    containerModalRef() {
      return this.$refs.ContainerK8sModal
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
      this.containerModalRef.openDialog()
    },
    khaiBaoCong() {
      // Handle the event when the button is clicked
      this.containerModalRef.openDialog()
    },
    grid_onCommandClicked(name, dataItem) {
      if (this.disableAdd) return
      if (name === 'SUA') {
        this.containerModalRef.setCurrentItem(dataItem)
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
          this.$emit('deleteContainerItem', args)
        } catch (error) {
          this.$root.toastError('Xóa container thất bại')
        }
      }
    }
  }
}
