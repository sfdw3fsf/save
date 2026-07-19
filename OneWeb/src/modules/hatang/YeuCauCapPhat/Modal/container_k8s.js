import moment from 'moment'
import { createRow, formatDate, updateRow } from '../commons'
import { DATE_FORMAT, DEFAULT_CONTAINER_DATA, KIENTRUC_THANHPHAN } from '../constants'
import { getPositiveNumber } from '../utils'

export default {
  name: 'ContainerK8sModal',
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
        cpu: 0,
        ram: 0,
        // ssd: 0,
        // hdd: 0,
        storage: 0,
        pod: 0
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
      return this.$refs.containerDialog
    },
    containerList() {
      return this.$parent.$parent.containerList
    },
    minDate() {
      return moment().format(DATE_FORMAT)
    }
  },
  methods: {
    setDefaultData() {
      this.currentItem = { ...DEFAULT_CONTAINER_DATA }
    },
    openDialog() {
      this.dialogRef.show()
      this.kientrucList = [...KIENTRUC_THANHPHAN]
    },
    closeDialog() {
      this.setDefaultData()
      this.dialogRef.hide()
    },
    validateControls() {
      let msg = ''
      if (this.controls.txtThoiGianKhoiTao.invalid) {
        msg = 'Thời gian khởi tạo không được để trống\n'
      }
      if (this.controls.txtThoiGianBatDau.invalid) {
        msg += 'Thời gian bắt đầu không được để trống\n'
      }
      if (this.controls.txtThoiGianKetThuc.invalid) {
        msg += 'Thời gian kết thúc không được để trống\n'
      }
      if (msg !== '') this.$root.toastError(msg)
      return msg === ''
    },
    onSave() {
      if (!this.validateControls()) return

      if (!this.currentItem.id && !this.currentItem.tmpId) {
        const dateFields = ['ngaytao', 'batdau', 'ketthuc']

        const formattedData = {
          ...this.currentItem,
          ...Object.fromEntries(dateFields.map((field) => [field, formatDate(this.currentItem[field])]))
        }
        createRow(this.containerList, formattedData)
      } else {
        updateRow(this.containerList, this.currentItem, this.pickContainerData, this.$root)
      }

      this.closeDialog()
    },
    pickContainerData(data) {
      return {
        thanhphan: data.thanhphan ?? 1,
        ngaytao: data.ngaytao ?? moment().format(DATE_FORMAT),
        batdau: data.batdau ?? moment().format(DATE_FORMAT),
        ketthuc: data.ketthuc ?? moment().format(DATE_FORMAT),
        cpu: data.cpu ?? 0,
        ram: data.ram ?? 0,
        storage: data.storage ?? 0,
        pod: data.pod ?? 0
      }
    },
    setCurrentItem(data) {
      const formattedData = { ...data }

      this.currentItem = { ...this.currentItem, ...formattedData }
    },
    formatPositive(fieldName) {
      this.currentItem[fieldName] = getPositiveNumber(this.currentItem[fieldName])
    }
  }
}
