<template src='./DeviceRecallCommission.html'></template>
<style scoped src='./DeviceRecallCommission.scss'></style>
<script>
import { mapActions } from 'vuex'
import gridview from '../../../../components/Shared/gridview.vue'
import select3 from '@/components/form/VueSelect.vue'
import { Page, Filter } from '@syncfusion/ej2-vue-grids'
import DeviceRecallCommissionAPI from './DeviceRecallCommission'
import { SUCCESS_CODE } from '@/constants'
import { pagingAndFilter } from '@/modules/admin/category/Staff/mixins/pagingAndFilter.js'

export default {
  components: {
    gridview,
    select3
  },
  mixins: [pagingAndFilter],
  name: 'DeviceRecallCommission',
  data: () => ({
    pageSettings: { pageSize: 5 },
    deviceRecallCommissionInfo: {
      noiDung: null,
      nhomTbi: null,
      loaiTbi: null,
      dieuKien: null,
      donGia: 0
    },
    listAssignedInvoicePeriod: [],
    listNotAssignedInvoicePeriod: [],
    listAssignedEmployeeType: [],
    listNotAssignedEmployeeType: [],
    listAssignedArea: [],
    listNotAssignedArea: [],
    selectedDeviceRecallId: null,
    selectedDeviceReallRow: {},
    buttonStatus: {
      btnAddNew: true,
      btnSave: true,
      btnCancel: true,
      btnDelete: true
    },
    cboLoaiTbi: { dataSource: [], selectedValue: null }
  }),
  computed: {
    listDeviceGroup () {
      return this.$store.getters['deviceRecallCommissionCommon/listDeviceGroup']
    },
    listDeviceType () {
      return this.$store.getters['deviceRecallCommissionCommon/listDeviceType']
    },
    listCondition () {
      return this.$store.getters['deviceRecallCommissionCommon/listCondition']
    },
    listDeviceRecallCommission () {
      return this.$store.getters['deviceRecallCommissionCommon/listDeviceRecallCommission']
    }
  },
  mounted () {
    window.app = this
    this.$store.dispatch('deviceRecallCommissionCommon/getListDeviceGroup', {})
    this.$store.dispatch('deviceRecallCommissionCommon/getListCondition', {})
    this.loadListDeviceRecall()
    // remove grid header
    this.$refs.assignedInvoice.getHeaderContent().style.display = 'none'
    this.$refs.notAssignedInvoice.getHeaderContent().style.display = 'none'
    this.$refs.assingedEmployeeType.getHeaderContent().style.display = 'none'
    this.$refs.notAssingedEmployeeType.getHeaderContent().style.display = 'none'
    this.$refs.assingedArea.getHeaderContent().style.display = 'none'
    this.$refs.notAssingedArea.getHeaderContent().style.display = 'none'

    window.app = this
  },
  watch: {
    selectedDeviceReallRow: (value) => {
      if (this.buttonStatus) {
        this.buttonStatus.btnDelete = (value != null && value != undefined)
      }
    }
  },
  methods: {
    ...mapActions('deviceRecallCommissionCommon', [
      'getListDeviceGroup'
    ]),
    loadListDeviceRecall () {
      this.$store.dispatch('deviceRecallCommissionCommon/getListDeviceRecallCommission', {'pagenumber': 1, 'pagesize': 99999})
    },
    clear () {
      this.deviceRecallCommissionInfo.noiDung = ""
      this.deviceRecallCommissionInfo.donGia = "0"
      this.selectedDeviceRecallId = -1
      this.selectedDeviceReallRow = {}

      this.loadListAssignedAndNotAssigned()
    },
    setButton (kieu) {
      this.buttonStatus.btnAddNew = false
      this.buttonStatus.btnSave = false
      this.buttonStatus.btnDelete = false
      this.buttonStatus.btnCancel = false

      if (kieu == -1) {
        this.$refs.txtNoiDungTBi.focus()
        this.buttonStatus.btnSave = true
        this.buttonStatus.btnCancel = true
      }

      if (kieu == 0) {
        this.buttonStatus.btnAddNew = true
        this.clear()
      }

      if (kieu == 1) {
        this.$refs.txtNoiDungTBi.focus()
        this.buttonStatus.btnSave = true
        this.buttonStatus.btnCancel = true
        this.clear()
      }

      if (kieu == 2) {
        this.$refs.txtNoiDungTBi.focus()
        this.buttonStatus.btnAddNew = true
        this.buttonStatus.btnDelete = true
        this.clear()
      }

      if (kieu == 3) {
        this.buttonStatus.btnAddNew = true
        this.buttonStatus.btnDelete = true
        this.buttonStatus.btnSave = true
        this.buttonStatus.btnCancel = true
      }
    },
    loadListAssignedAndNotAssigned () {
      this.listAssignedInvoicePeriod = []
      this.listNotAssignedInvoicePeriod = []
      this.listAssignedEmployeeType = []
      this.listNotAssignedEmployeeType = []
      this.listAssignedArea = []
      this.listNotAssignedArea = []

      // type = 0 : chưa gán, 1 : đã gán
      DeviceRecallCommissionAPI.getListInvoicePeriod({'thulao_tbi_id': this.selectedDeviceRecallId, 'type': 1}).then(rs => {
        if (rs.data.data) {
          this.listAssignedInvoicePeriod = rs.data.data
        }
      })
      DeviceRecallCommissionAPI.getListInvoicePeriod({'thulao_tbi_id': this.selectedDeviceRecallId, 'type': 0}).then(rs => {
        if (rs.data.data) {
          this.listNotAssignedInvoicePeriod = rs.data.data
        }
      })
      DeviceRecallCommissionAPI.getListEmployeeType({'thulao_tbi_id': this.selectedDeviceRecallId, 'type': 1}).then(rs => {
        if (rs.data.data) {
          this.listAssignedEmployeeType = rs.data.data
        }
      })
      DeviceRecallCommissionAPI.getListEmployeeType({'thulao_tbi_id': this.selectedDeviceRecallId, 'type': 0}).then(rs => {
        if (rs.data.data) {
          this.listNotAssignedEmployeeType = rs.data.data
        }
      })
      DeviceRecallCommissionAPI.getListArea({'thulao_tbi_id': this.selectedDeviceRecallId, 'type': 1}).then(rs => {
        if (rs.data.data) {
          this.listAssignedArea = rs.data.data
        }
      })
      DeviceRecallCommissionAPI.getListArea({'thulao_tbi_id': this.selectedDeviceRecallId, 'type': 0}).then(rs => {
        if (rs.data.data) {
          this.listNotAssignedArea = rs.data.data
        }
      })
    },
    onDeviceGroupChange () {
      let data = {
        'nhom_tbi_id': +this.deviceRecallCommissionInfo.nhomTbi
      }

      DeviceRecallCommissionAPI.getListDeviceType(data).then(rs => {
        this.cboLoaiTbi.dataSource = rs.data.data

        setTimeout(() => {
          this.$set(this.cboLoaiTbi, 'selectedValue', this.deviceRecallCommissionInfo.loaiTbi)
        }, 200)
      })
    },
    onDataRecallRowChange (e) {
      if (e) {
        e = e.rowData
      }

      this.selectedDeviceReallRow = e
      this.selectedDeviceRecallId = e.thulao_tbi_id || null

      this.deviceRecallCommissionInfo.loaiTbi = e.loaitbi_id
      this.deviceRecallCommissionInfo.donGia = e.dongia
      this.deviceRecallCommissionInfo.noiDung = e.noidung_tbi
      this.deviceRecallCommissionInfo.dieuKien = e.dieukien_id

      DeviceRecallCommissionAPI.layNhomTbTheoLoaiTb(e.loaitbi_id).then(rs => {
        if (rs.data.length > 0) {
          this.deviceRecallCommissionInfo.nhomTbi = rs.data[0].nhom_tbi_id
        } else {
          this.deviceRecallCommissionInfo.nhomTbi = null
          this.deviceRecallCommissionInfo.loaiTbi = null
          this.cboLoaiTbi.selectedValue = null
        }
      })

      this.loadListAssignedAndNotAssigned()

      this.setButton(3)
    },
    onBtnAddNewClick (e) {
      this.setButton(1)
    },
    validate () {
      if (!this.deviceRecallCommissionInfo.noiDung) {
        this.$toast.warning('Bạn chưa nhập "Nội dung thù lao thu hồi thiết bị"!')
        return false
      }
      if (!this.deviceRecallCommissionInfo.nhomTbi) {
        this.$toast.warning('Bạn chưa chọn nhóm thiết bị!')
        return false
      }
      if (!this.cboLoaiTbi.selectedValue) {
        this.$toast.warning('Bạn chưa chọn loại thiết bị!')
        return false
      }
      if (!this.deviceRecallCommissionInfo.dieuKien) {
        this.$toast.warning('Bạn chưa chọn điều kiện!')
        return false
      }
      if (!this.deviceRecallCommissionInfo.donGia || +this.deviceRecallCommissionInfo.donGia <= 0) {
        this.$toast.warning('"Đơn giá" phải lớn hơn 0!')
        return false
      }
      if (this.listAssignedInvoicePeriod.length === 0) {
        this.$toast.warning('Bạn chưa gán kỳ hóa đơn!')
        return false
      }
      if (this.listAssignedEmployeeType.length === 0) {
        this.$toast.warning('Bạn chưa gán loại nhân viên!')
        return false
      }
      if (this.listAssignedArea.length === 0) {
        this.$toast.warning('Bạn chưa gán khu vực!')
        return false
      }

      return true
    },
    async onBtnSaveClick (e) {
      if (!this.validate()) {
        return
      }
      let dataDeviceRecall = [
        {
          'THULAO_TBI_ID': +this.selectedDeviceRecallId,
          'NOIDUNG_TBI': this.deviceRecallCommissionInfo.noiDung,
          'LOAITBI_ID': this.cboLoaiTbi.selectedValue,
          'DONGIA': this.deviceRecallCommissionInfo.donGia,
          'DIEUKIEN': this.deviceRecallCommissionInfo.dieuKien
        }
      ]
      let dataArea = this.listAssignedArea.map(el => { return {'KHUVUC_ID': el.khuvuc_id} })
      let dataInvoice = this.listAssignedInvoicePeriod.map(el => { return {'CHUKYNO': el.id} })
      let dataEmployee = this.listAssignedEmployeeType.map(el => { return {'LOAINV_ID': el.loainv_id} })

      if (this.buttonStatus.btnAddNew) {
        // Update
        let data = {
          'thulao_tbi_id': +this.selectedDeviceRecallId,
          'json_thulao_tbi': JSON.stringify(dataDeviceRecall),
          'json_thulaotbi_kv': JSON.stringify(dataArea),
          'json_thulaotbi_lnv': JSON.stringify(dataEmployee),
          'json_thulaotbi_khd': JSON.stringify(dataInvoice)
        }
        DeviceRecallCommissionAPI.updateDeviceRecall(data).then(rs => {
          if (rs.data.error_code == SUCCESS_CODE) {
            this.$toast.success('Cập nhật thành công')
            this.loadListDeviceRecall()
            this.buttonStatus = {
              btnAddNew: true,
              btnSave: true,
              btnCancel: true,
              btnDelete: true
            }
          } else {
            this.showError(rs, 'Cập nhật thất bại')
          }
        }).catch(err => {
          this.showError(err, 'Cập nhật thất bại')
        })
      } else {
        // Add
        dataDeviceRecall[0]['THULAO_TBI_ID'] = 0
        let data = {
          'thulao_tbi_id': 0,
          'json_thulao_tbi': JSON.stringify(dataDeviceRecall),
          'json_thulaotbi_kv': JSON.stringify(dataArea),
          'json_thulaotbi_lnv': JSON.stringify(dataEmployee),
          'json_thulaotbi_khd': JSON.stringify(dataInvoice)
        }
        DeviceRecallCommissionAPI.updateDeviceRecall(data).then(rs => {
          if (rs.data.error_code == SUCCESS_CODE) {
            this.$toast.success('Thêm mới thành công')
            this.loadListDeviceRecall()
            this.buttonStatus = {
              btnAddNew: true,
              btnSave: true,
              btnCancel: true,
              btnDelete: true
            }
          } else {
            this.showError(rs, 'Thêm mới thất bại')
          }
        }).catch(err => {
          this.showError(err, 'Thêm mới thất bại')
        })
      }
    },
    showError (err, defaultMsg) {
      if (!err || !err.data) {
        this.$toast.error(defaultMsg)
        return
      }

      if (err.data.message && err.data.message.length > 5) {
        this.$toast.error(err.data.message)
      } else if (err.data.message_detail && err.data.message_detail.length > 5) {
        this.$toast.error(err.data.message_detail)
      } else {
        this.$toast.error(defaultMsg)
      }
    },
    onBtnCancelClick (e) {
      this.setButton(0)
      // this.onDataRecallRowChange(this.selectedDeviceReallRow)
      this.loadListAssignedAndNotAssigned()
    },
    onBtnDeleteClick (e) {
      this.$bvModal.msgBoxConfirm('Bạn chắc chắn muốn xóa thù lao thu hồi thiết bị không?')
        .then(value => {
          if (value) {
            DeviceRecallCommissionAPI.deleteDeviceRecall(+this.selectedDeviceRecallId).then(rs => {
              if (rs.data == 1) {
                this.$toast.success('Xóa thành công')
                this.loadListDeviceRecall()
              } else {
                this.$toast.error('Xóa thất bại')
              }
            })
          }
        })
    },
    moveSelectedFromNotAssignedInvoice () {
      this.listAssignedInvoicePeriod = [...this.$refs.notAssignedInvoice.getSelectedRecords(), ...this.listAssignedInvoicePeriod]
      this.listNotAssignedInvoicePeriod = this.listNotAssignedInvoicePeriod.filter(item => !this.$refs.notAssignedInvoice.getSelectedRecords().find(i => i == item))
    },
    moveAllFromNotAssignedInvoice () {
      this.listAssignedInvoicePeriod = [...this.listNotAssignedInvoicePeriod, ...this.listAssignedInvoicePeriod]
      this.listNotAssignedInvoicePeriod = []
    },
    moveSelectedFromAssignedInvoice () {
      this.listNotAssignedInvoicePeriod = [...this.$refs.assignedInvoice.getSelectedRecords(), ...this.listNotAssignedInvoicePeriod]
      this.listAssignedInvoicePeriod = this.listAssignedInvoicePeriod.filter(item => !this.$refs.assignedInvoice.getSelectedRecords().find(i => i == item))
    },
    moveAllFromAssignedInvoice () {
      this.listNotAssignedInvoicePeriod = [...this.listAssignedInvoicePeriod, ...this.listNotAssignedInvoicePeriod]
      this.listAssignedInvoicePeriod = []
    },
    moveSelectedFromAssignedEmployee () {
      this.listNotAssignedEmployeeType = [...this.$refs.assingedEmployeeType.getSelectedRecords(), ...this.listNotAssignedEmployeeType]
      this.listAssignedEmployeeType = this.listAssignedEmployeeType.filter(item => !this.$refs.assingedEmployeeType.getSelectedRecords().find(i => i == item))
    },
    moveAllFromAssignedEmployee () {
      this.listNotAssignedEmployeeType = [...this.listAssignedEmployeeType, ...this.listNotAssignedEmployeeType]
      this.listAssignedEmployeeType = []
    },
    moveSelectedFromNotAssignedEmployee () {
      this.listAssignedEmployeeType = [...this.$refs.notAssingedEmployeeType.getSelectedRecords(), ...this.listAssignedEmployeeType]
      this.listNotAssignedEmployeeType = this.listNotAssignedEmployeeType.filter(item => !this.$refs.notAssingedEmployeeType.getSelectedRecords().find(i => i == item))
    },
    moveAllFromNotAssignedEmployee () {
      this.listAssignedEmployeeType = [...this.listNotAssignedEmployeeType, ...this.listAssignedEmployeeType]
      this.listNotAssignedEmployeeType = []
    },
    moveSelectedFromAssignedArea () {
      this.listNotAssignedArea = [...this.$refs.assingedArea.getSelectedRecords(), ...this.listNotAssignedArea]
      this.listAssignedArea = this.listAssignedArea.filter(item => !this.$refs.assingedArea.getSelectedRecords().find(i => i == item))
    },
    moveAllFromAssignedArea () {
      this.listNotAssignedArea = [...this.listAssignedArea, ...this.listNotAssignedArea]
      this.listAssignedArea = []
    },
    moveSelectedFromNotAssignedArea () {
      this.listAssignedArea = [...this.$refs.notAssingedArea.getSelectedRecords(), ...this.listAssignedArea]
      this.listNotAssignedArea = this.listNotAssignedArea.filter(item => !this.$refs.notAssingedArea.getSelectedRecords().find(i => i == item))
    },
    moveAllFromNotAssignedArea () {
      this.listAssignedArea = [...this.listNotAssignedArea, ...this.listAssignedArea]
      this.listNotAssignedArea = []
    }
  },
  provide: {
    grid: [Page, Filter]
  }
}
</script>
