
export default {
  name: 'TabContainerK8s',
  components: {
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
        { fieldName: 'kientruc', headerText: 'Kiến trúc/thành phần', textAlign: 'Left' },
        { fieldName: 'thoigian_khoitao', headerText: 'Thời gian khởi tạo', textAlign: 'Left' },
        { fieldName: 'thoigian_batdau', headerText: 'Thời gian bắt đầu', textAlign: 'Left' },
        { fieldName: 'thoigian_ketthuc', headerText: 'Thời gian kết thúc', textAlign: 'Left' },
        { fieldName: 'cpu', headerText: 'CPU', textAlign: 'Left' },
        { fieldName: 'ram', headerText: 'RAM', textAlign: 'Left' },
        { fieldName: 'ssd', headerText: 'SSD', textAlign: 'Left' },
        { fieldName: 'hdd', headerText: 'HDD', textAlign: 'Left' },
        { fieldName: 'soluong_pob', headerText: 'Số lượng Pob', textAlign: 'Left' }
      ]
    }
  },
  computed: {
    grdItems: function() {
      return this.$refs.grdItems
    },
    containerModalRef() {
      return this.$refs.ContainerK8sModal
    }
  },
  watch: {
    'thongTinCard.id': async function(newId) {
      if (newId) {
        // this.lichSuItemList = await this.getLichSuThayDoiCLTB(newId);
        this.thongTinCong.chungloaiThietBiId = this.thongTinCard.id
        this.congParam.chungloaiThietBiId = this.thongTinCard.id
        this.loadDsCong()
      } else {
        this.lichSuItemList = []
      }
    }
  },
  created: async function() {
    try {
      this.$root.showLoading(true)
      if (this.thongTinCard.soPortPower != 0) {
      } else {
        this.loadDsCong()
      }
    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
    updateCong() {
      // Handle the event when the button is clicked
      this.containerModalRef.openDialog()
    },
    khaiBaoCong() {
      // Handle the event when the button is clicked
      this.containerModalRef.openDialog()
    },
    grid_onCommandClicked(name, dataItem) {
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
