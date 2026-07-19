import moment from 'moment'
import { createRow, formatDate, updateRow } from '../commons'
import { DATE_FORMAT, DEFAULT_BACKUP_DATA, KIENTRUC_THANHPHAN } from '../constants'
import { getPositiveNumber } from '../utils'

export default {
  name: 'BackupModal',
  data() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      bkp_select: [],
      currentItem: {
        id: null,
        tmpId: null,
        thanhphan: 1,
        ngaytao: moment().format(DATE_FORMAT),
        batdau: moment().format(DATE_FORMAT),
        ketthuc: moment().format(DATE_FORMAT),
        tap_trung: 1,
        manual: 1,
        dungluong: 0,
        dungluong_tangtruong: 0,
        vitri_backup: '',
        tansuat: '',
        sl_backup: 0,
        external_backup: 0
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
  mounted() {},
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
      return this.$refs.backupDialog
    },
    backupList() {
      return this.$parent.$parent.backupList
    },
    minDate() {
      return moment().format(DATE_FORMAT)
    }
  },
  async created() {
    await this.delay(500)
    this.$set(this, 'bkp_select', [
      { text: 'Không', value: 0 },
      { text: 'Có', value: 1 }
    ])
  },
  methods: {
    delay(ms) {
      return new Promise((resolve) => setTimeout(resolve, ms))
    },
    openDialog() {
      this.dialogRef.show()
      this.kientrucList = [...KIENTRUC_THANHPHAN]
    },
    closeDialog() {
      this.currentItem = { ...DEFAULT_BACKUP_DATA }
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

      if (msg !== '') {
        this.$root.toastError(msg)
      }
      return msg === ''
    },
    onSave() {
      try {
        if (!this.validateControls()) return

        if (!this.currentItem.id && !this.currentItem.tmpId) {
          const dateFields = ['ngaytao', 'batdau', 'ketthuc']

          const formattedData = {
            ...this.currentItem,
            ...Object.fromEntries(dateFields.map((field) => [field, formatDate(this.currentItem[field])]))
          }
          createRow(this.backupList, formattedData)
        } else {
          updateRow(this.backupList, this.currentItem, this.pickBackupData, this.$root)
        }

        this.closeDialog()
      } catch (error) {
        console.log(`🚀 ~ error`, error)
      }
    },
    pickBackupData(data) {
      return {
        thanhphan: data.thanhphan ?? 1,
        ngaytao: data.ngaytao ?? moment().format(DATE_FORMAT),
        batdau: data.batdau ?? moment().format(DATE_FORMAT),
        ketthuc: data.ketthuc ?? moment().format(DATE_FORMAT),
        tap_trung: data.tap_trung ?? 0,
        manual: data.manual ?? 0,
        dungluong: data.dungluong ?? 0,
        dungluong_tangtruong: data.dungluong_tangtruong ?? 0,
        vitri_backup: data.vitri_backup ?? '',
        tansuat: data.tansuat ?? '',
        sl_backup: data.sl_backup ?? 0,
        external_backup: data.external_backup ?? 0
      }
    },
    setCurrentItem(data) {
      const formattedData = { ...data }

      // ;['ngaytao', 'batdau', 'ketthuc'].forEach((field) => {
      //   if (formattedData[field]) {
      //     formattedData[field] = moment(formattedData[field], ['YYYY-MM-DD', DATE_FORMAT], true).format('YYYY-MM-DD')
      //   }
      // })

      this.currentItem = { ...this.currentItem, ...formattedData }
    },
    formatPositive(fieldName) {
      this.currentItem[fieldName] = getPositiveNumber(this.currentItem[fieldName])
    }
  }
}
