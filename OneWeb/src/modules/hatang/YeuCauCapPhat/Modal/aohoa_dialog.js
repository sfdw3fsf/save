import moment from 'moment'
import { DATE_FORMAT, DEFAULT_AO_HOA_DATA, KIENTRUC_THANHPHAN } from '../constants'
import { createRow, formatDate, updateRow } from '../commons'
import { getPositiveNumber } from '../utils'

export default {
  props: {
    // thongTinAoHoa: {
    //   type: Object
    // }
  },
  data() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      currentItem: {
        id: null,
        tmpId: null,
        thanhphan: 1,
        ngaytao: moment().format(DATE_FORMAT),
        batdau: moment().format(DATE_FORMAT),
        ketthuc: moment().format(DATE_FORMAT),
        vcpu: 0,
        vram: 0,
        vgpu: 0,
        ssd: 0,
        hdd: 0,
        iops: 0,
        throughput: 0,
        network_card: 0
      },
      kientrucList: [],
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
      }
    }
  },
  watch: {
    'currentItem.thoigian_khoitao': function(val) {
      if (val) {
        this.currentItem.thoigian_khoitao = moment(val).format(DATE_FORMAT)
      }
      this.controls.txtThoiGianKhoiTao.invalid = !val
    },
    'currentItem.thoigian_batdau': function(val) {
      if (val) {
        this.currentItem.thoigian_batdau = moment(val).format(DATE_FORMAT)
      }
      this.controls.txtThoiGianBatDau.invalid = !val
    },
    'currentItem.thoigian_ketthuc': function(val) {
      if (val) {
        this.currentItem.thoigian_ketthuc = moment(val).format(DATE_FORMAT)
      }
      this.controls.txtThoiGianKetThuc.invalid = !val
    }
  },
  computed: {
    dialogRef() {
      return this.$refs.aohoaDialog
    },
    aoHoaList() {
      return this.$parent.$parent.aoHoaList
    },
    minDate() {
      return moment().format(DATE_FORMAT)
    }
  },
  methods: {
    clearAll() {
      this.currentItem = { ...DEFAULT_AO_HOA_DATA }
    },
    async dialogOpen() {
      try {
        this.loading(true) // Bắt đầu trạng thái loading
        // this.clearAll()
        // await this.InitForm()

        // Kiểm tra sự tồn tại của ref và gọi phương thức show() trên dialog
        if (this.dialogRef) {
          this.dialogRef.show() // Hiển thị dialog
        } else {
          this.$toast.error('Dialog không tồn tại hoặc không được tham chiếu đúng')
        }
        this.kientrucList = [...KIENTRUC_THANHPHAN]
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

    setDataProps() {
      // this.isChonNhanVien = this.p_chonNhanVien
      // this.DSTinhThanh.ID = this.p_phanVungSDID ?  this.p_phanVungSDID : null
      // this.tenDonVi = this.p_tenDonVi ? this.p_tenDonVi : ""
    },
    closeDialog() {
      this.clearAll()
      this.dialogRef.hide()
    },
    async InitForm() {
      this.setDataProps() // Gọi hàm này để thiết lập các thuộc tính cần thiết khác
    },
    validateControls: function() {
      let msg = ''

      if (this.controls.txtThoiGianKhoiTao.invalid) {
        msg += 'Thời gian khởi tạo không được để trống.\n'
      }
      if (this.controls.txtThoiGianBatDau.invalid) {
        msg += 'Thời gian bắt đầu không được để trống.\n'
      }
      if (this.controls.txtThoiGianKetThuc.invalid) {
        msg += 'Thời gian kết thúc không được để trống.\n'
      }
      if (msg) {
        this.$root.toastError(msg)
        return false
      }
      return true
    },
    onSave() {
      try {
        if (this.validateControls()) {
          if (!this.validateControls()) return

          if (!this.currentItem.id && !this.currentItem.tmpId) {
            const dateFields = ['ngaytao', 'batdau', 'ketthuc']

            const formattedData = {
              ...this.currentItem,
              ...Object.fromEntries(dateFields.map((field) => [field, formatDate(this.currentItem[field])]))
            }
            createRow(this.aoHoaList, formattedData)
          } else {
            updateRow(this.aoHoaList, this.currentItem, this.pickAoHoaData, this.$root)
          }

          this.closeDialog()
        }
      } catch (error) {
        console.log(`🚀 ~ error`, error)
      }
    },
    pickAoHoaData(data) {
      return {
        thanhphan: data.thanhphan ?? 1,
        ngaytao: moment(data.ngaytao).format(DATE_FORMAT),
        batdau: moment(data.batdau).format(DATE_FORMAT),
        ketthuc: moment(data.ketthuc).format(DATE_FORMAT),
        vcpu: data.vcpu ?? 0,
        vram: data.vram ?? 0,
        vgpu: data.vgpu ?? 0,
        ssd: data.ssd ?? 0,
        hdd: data.hdd ?? 0,
        iops: data.iops ?? 0,
        throughput: data.throughput ?? 0,
        network_card: data.network_card ?? 0
      }
    },
    setCurrentItem(data) {
      const formattedData = { ...data }

      // ;['ngaytao', 'batdau', 'ketthuc'].forEach((field) => {
      //   if (formattedData[field]) {
      //     formattedData[field] = moment(formattedData[field], ['YYYY-MM-DD', DATE_FORMAT], true).format(DATE_FORMAT)
      //   }
      // })

      this.currentItem = { ...this.currentItem, ...formattedData }
    },
    formatPositive(fieldName) {
      this.currentItem[fieldName] = getPositiveNumber(this.currentItem[fieldName])
    }
  }
}
