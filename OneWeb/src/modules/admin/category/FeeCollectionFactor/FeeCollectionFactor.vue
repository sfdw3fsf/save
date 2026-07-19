<template>
    <div>
        <!-- <div class="breadcrumb-top">
            <div class="main-title">Hệ số thu cước róc</div>
            <ul class="breadcrumb">
                <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ
                    </a></li>
                <li class="breadcrumb-item"><a href="#">Lập hợp đồng</a></li>
                <li class="breadcrumb-item active">Lắp đặt mới</li>
            </ul>
        </div> -->
        <breadcrumb :header="header" />
        <div class="list-actions-top">
            <ul class="list">
                <li :class="{ '-disabled': !isEnableAddNew }">
                    <a href="#" @click="addNew">
                        <span class="icon one-file-plus"></span>Nhập mới
                    </a>
                </li>
                <li :class="{ '-disabled': !isEnableSave }">
                    <a href="#" @click="save">
                        <span class="icon one-save"></span>Ghi lại
                    </a>
                </li>
                <li :class="{ '-disabled': !isEnableCancel }">
                    <a href="#" @click="cancel">
                        <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy
                    </a>
                </li>
                <li :class="{ '-disabled': !isEnableDelete }">
                    <a href="#" @click="deleteSelectedRecord">
                        <span class="icon one-trash"></span>Xóa
                    </a>
                </li>
            </ul>
        </div>
        <div class="page-content">
            <div class="row">
                <div class="col-sm-6 col-12">
                    <info-factor ref="infoFactor"
                        :feeFactor="getSelectedFeeFactor"></info-factor>
                    <info-factor-list
                        :dataSource="getFeeList"
                        v-on:onSelectedFactor="onSelectedFactor"></info-factor-list>
                </div>
                <div class="col-sm-6 col-12">
                    <invoice ref="invoice"
                        :invoicePeriodsNotAdd="getInvoicePeriodsNotAdd"
                        :invoicePeriodsAdded="getInvoicePeriodsAdded"></invoice>
                    <employee ref="employee"
                        :employeeTypesNotAdd="getEmployeeTypesNotAdd"
                        :employeeTypesAdded="getEmployeeTypesAdded"></employee>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import api from './api/index'
import axios from 'axios'
import { mapActions, mapGetters } from 'vuex'
import breadcrumb from '@/components/breadcrumb'
export default {
  components: {
    'info-factor': () => import('./parts/InfoFactor.vue'),
    'info-factor-list': () => import('./parts/FeeFactorList.vue'),
    'invoice': () => import('./parts/Invoice.vue'),
    'employee': () => import('./parts/employee.vue'),
    breadcrumb
  },
  data () {
    return {
      isEnableAddNew: true,
      isEnableDelete: true,
      isEnableSave: true,
      isEnableCancel: true
    }
  },
  computed: {
    ...mapGetters('feeCollectionFactorCommon', [
      'getFeeList',
      'getFeeTypes',
      'getEmployeeTypesNotAdd',
      'getEmployeeTypesAdded',
      'getInvoicePeriodsNotAdd',
      'getInvoicePeriodsAdded',
      'getSelectedFeeFactor'
    ])
  },
  methods: {
    ...mapActions('feeCollectionFactorCommon', [
      'loadFeeList',
      'saveRecord',
      'deleteRecord',
      'setEmployeeTypesAdded',
      'setInvoicePeriodsAdded'
    ]),
    addNew () {
      this.isEnableAddNew = false
      this.isEnableDelete = false
      this.resetData()
    },
    save: async function () {
      this.$root.showLoading(true)
      try {
        if (this.isValidForm()) {
          let hesoTcrLnv = ''
          this.getEmployeeTypesAdded.forEach(item => {
            if (hesoTcrLnv.length > 0) {
              hesoTcrLnv += ','
            }
            hesoTcrLnv += item.loainv_id.toString()
          })
          let hesoTcrKhd = ''
          this.getInvoicePeriodsAdded.forEach(item => {
            if (hesoTcrKhd.length > 0) {
              hesoTcrKhd += ','
            }
            hesoTcrKhd += item.id.toString()
          })

          let heso_tcr_id = ''
          if (this.isEnableAddNew) {
            heso_tcr_id = this.getSelectedFeeFactor.heso_tcr_id
          } else {
            heso_tcr_id = null
          }

          const res = await api.save(axios, {
            phanvung_id: this.$root.token.getPhanVungID(),
            heso_tcr_id: heso_tcr_id,
            thoidiem_tps: this.$refs.infoFactor.timeTPS,
            ngaythu_tu: this.$refs.infoFactor.dateFromNum,
            ngaythu_den: this.$refs.infoFactor.dateToNum,
            heso_tcr: this.$refs.infoFactor.factor,
            kieu_tcr: this.$refs.infoFactor.selectedFeeTypeId,
            ghichu: this.$refs.infoFactor.note,
            heso_tcr_lnv: hesoTcrLnv,
            heso_tcr_khd: hesoTcrKhd
          })

          if (res.data.message_detail) {
            throw res.data.message_detail
          }

          this.loadData()
          this.$toast.success('Cập nhật dữ liệu thành công !')
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    async cancel () {
      const isConfirm = await this.$bvModal
        .msgBoxConfirm(
          `Bạn có thực sự muốn hủy thông tin đã nhập không?`,
          {
            title: '',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          }
        )
      if (isConfirm) {
        this.isEnableAddNew = true
        this.isEnableDelete = true
        this.isEnableSave = true
        this.isEnableCancel = true
        this.resetData()
      }
    },
    resetData: function () {
      this.$refs.infoFactor.timeTPS = null
      this.$refs.infoFactor.dateFromNum = 0
      this.$refs.infoFactor.dateToNum = 0
      this.$refs.infoFactor.selectedFeeTypeId = this.getFeeTypes.length > 0 ? this.getFeeTypes[0].kieu_tcr_id : ''
      this.$refs.infoFactor.factor = 0
      this.$refs.infoFactor.note = null

      this.setEmployeeTypesAdded([])
      this.setInvoicePeriodsAdded([])
    },
    isValidForm: function () {
      if (!this.$refs.infoFactor.timeTPS) {
        this.$toast.error('Bạn chưa nhập thời điểm thu cước phát sinh!')
        return false
      }
      if (!(this.$refs.infoFactor.factor && this.$refs.infoFactor.factor > 0)) {
        this.$toast.error('Hệ số phải lớn hơn 0!')
        return false
      }
      if (!(this.$refs.infoFactor.dateFromNum && this.$refs.infoFactor.dateFromNum > 0 && this.$refs.infoFactor.dateFromNum < 31)) {
        this.$toast.error('\\"Ngày thu từ\\" phải lớn hơn 0 và nhỏ hơn 31!')
        return false
      }
      if (!(this.$refs.infoFactor.dateToNum && this.$refs.infoFactor.dateToNum > 0 && this.$refs.infoFactor.dateToNum < 31)) {
        this.$toast.error('\\"Ngày thu đến\\" phải lớn hơn 0 và nhỏ hơn 31!')
        return false
      }
      if (!(this.$refs.invoice.tableAdded.options && this.$refs.invoice.tableAdded.options.length > 0)) {
        this.$toast.error('Bạn chưa gán kỳ hóa đơn!')
        return false
      }
      if (!(this.$refs.employee.tableAdded.options && this.$refs.employee.tableAdded.options.length > 0)) {
        this.$toast.error('Bạn chưa gán loại nhân viên!')
        return false
      }
      return true
    },
    deleteSelectedRecord: async function () {
      try {
        const isConfirm = await this.$bvModal
          .msgBoxConfirm(
            `Bạn chắc chắn muốn xóa hệ số thu cước róc không?`,
            {
              title: 'Thông báo',
              size: 'sm',
              okTitle: 'Yes',
              cancelTitle: 'No',
              centered: true
            }
          )

        if (isConfirm) {
          this.$root.showLoading(true)

          const resDelete = await api.deleteRecord(axios, {
            phanvung_id: this.$root.token.getPhanVungID(),
            heso_tcr_id: this.getSelectedFeeFactor.heso_tcr_id
          })

          if (resDelete.data.message_detail) { throw resDelete.data.message_detail }

          this.loadData()
          this.$toast.success('Xóa hệ số thu cước róc thành công')
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    loadData: async function () {
      try {
        await this.loadFeeList({
          phanvung_id: this.$root.token.getPhanVungID()
        })
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
    },
    onSelectedFactor: function (e) {
      this.isEnableAddNew = true
      this.isEnableDelete = true
      this.isEnableSave = true
      this.isEnableCancel = true
    }
  },
  mounted: async function () {
    this.$root.showLoading(true)
    await this.loadData()
    this.$root.showLoading(false)
  }
}
</script>

<style scoped>
.-disabled{
    pointer-events: none;
    cursor: default;
}
.-disabled:hover{
    background: unset;
}
.-disabled > a {
    color: gray !important;
}
.-disabled > a > span {
    color: gray;
}

a:hover{
  cursor: pointer;
}
</style>
