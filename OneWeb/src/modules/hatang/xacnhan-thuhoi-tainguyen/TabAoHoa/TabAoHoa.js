
export default {
  name: 'TabAoHoa',
  components: {
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
      currentItem: {
        tongSoPortTb: '',
        soInterface: '',
        batDauTu: '',
        dinhDangTen: ''
      },
      thongTinAoHoa: {
        kienTrucThanhPhan: null,
        thoiGianKhoiTao: '',
        thoiGianBatDau: '',
        thoiGianKetThuc: '',
        cpu: '',
        ram: '',
        ssd: '',
        hdd: '',
        iops: '',
        throughput: '',
        soCardMangIP: ''
      },
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
        // { fieldName: 'kientruc', headerText: 'Kiến trúc thành phần', textAlign: 'Left' },
        { fieldName: 'thoigian_khoitao', headerText: 'Thời gian khởi tạo', textAlign: 'Left' },
        { fieldName: 'thoigian_batdau', headerText: 'Thời gian bắt đầu', textAlign: 'Left' },
        { fieldName: 'thoigian_ketthuc', headerText: 'Thời gian kết thúc', textAlign: 'Left' },
        { fieldName: 'vcpu', headerText: 'CPU', textAlign: 'Left' },
        { fieldName: 'vram', headerText: 'RAM', textAlign: 'Left' },
        { fieldName: 'vgpu', headerText: 'GPU', textAlign: 'Left' },
        { fieldName: 'ssd', headerText: 'SSD', textAlign: 'Left' },
        { fieldName: 'hdd', headerText: 'HDD', textAlign: 'Left' },
        { fieldName: 'iops', headerText: 'IOPS', textAlign: 'Left' },
        { fieldName: 'throughput', headerText: 'Throughput', textAlign: 'Left' },
        { fieldName: 'so_card_mang', headerText: 'Số Card mạng (IP)', textAlign: 'Left' }
      ]
    }
  },
  computed: {
    grdItems: function() {
      return this.$refs.grdItems
    },
    aoHoaModalRef() {
      return this.$refs.dialogAoHoa
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
      if (name === 'SUA') {
        this.aoHoaModalRef.setCurrentItem(dataItem)
        this.updateCong()
      }
      if (name === 'XOA') {
        this.onDeleteCongInterface(dataItem)
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
        //   // Gọi API xóa yêu cầu ở đây
        //   //
        //   if (rs) {
        //     this.setCurrentItemDefault()
        //     this.loadDsAoHoa()
        //     this.$root.toastSuccess('Xóa cổng thành công')
        //   } else {
        //     this.$root.toastError('Xóa cổng thất bại')
        //   }
        // } finally {
        //   this.$root.showLoading(false)
        // }
        try {
          this.$emit('deleteAoHoaItem', args)
        } catch (error) {
          this.$root.toastError('Xóa ảo hóa thất bại')
        }
      }
    }
  }
}
