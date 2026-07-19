<template>
  <div class="main-wrapper">
    <div class="breadcrumb-top">
      <breadcrumb/>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" :class="{ disabled: isDisableBtnCreate }" @click.prevent="createInvoice">
            <span class="icon one-file-plus"></span>Nhập mới
          </a>
        </li>
        <li>
          <a href="#" :class="{ disabled: isDisableBtnSave }" @click.prevent="saveInvoice">
            <span class="icon one-save"></span>Ghi lại
          </a>
        </li>
        <li>
          <a href="#" :class="{ disabled: isDisableBtnCancel }" @click.prevent="createInvoice">
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy
          </a>
        </li>
        <li>
          <a href="#" :class="{ disabled: isDisableBtnDelete }" @click.prevent="deleteInvoice">
            <span class="icon one-trash"></span>Xóa
          </a>
        </li>
        <li>
          <a href="#" @click.prevent="openModalSeri"> <span class="icon one-seri"></span>Lấy seri </a>
        </li>
      </ul>
    </div>
    <div class="box-form">
      <div class="legend-title">Thông tin hoá đơn</div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w100">Kiểu hóa đơn
              <span class="required">*</span>
            </div>
            <div class="value">
              <div class="select-custom" :class="{'e-error e-input-group': showValidError && !form.invoiceType.selected}">
                <ejs-dropdownlist
                  popupWidth="auto"
                  v-model="form.invoiceType.selected"
                  :dataSource="form.invoiceType.options"
                  :allowFiltering='true'
                  :fields="{ text: 'kieu', value: 'kieu_id' }"
                  @filtering="onFilteringDropDownList($event, form.invoiceType.options, 'kieu')"
                ></ejs-dropdownlist>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w100" style="padding-left: 40px">Ký hiệu
              <span class="required">*</span>
            </div>
            <div class="value">
              <div :class="{'e-error e-input-group': showValidError && !form.symbol.trim()}">
                <input type="text" class="e-input" v-model="form.symbol">
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w100">Loại hóa đơn
              <span class="required">*</span>
            </div>
            <div class="value">
              <div :class="{'e-error e-input-group': showValidError && !form.typeofInvoice.trim()}">
                <input ref="txtLoaiHoaDon" type="text" class="e-input" v-model="form.typeofInvoice">
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w100" style="padding-left: 40px">Tên tắt
              <span class="required">*</span>
            </div>
            <div class="value">
              <div :class="{'e-error e-input-group': showValidError && !form.acronymName.trim()}">
                <input type="text" class="e-input" v-model="form.acronymName">
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="box-form">
      <div class="legend-title">Danh sách hóa đơn</div>
    </div>

    <div class="grid-ext grid-ext-freeze">
      <ejs-grid
        ref="tableInvoiceType"
        :dataSource="invoiceTypeList"
        :allowPaging="true"
        :pageSettings="pageSettings"
        :allowSorting="false"
        :allowFiltering="true"
        :allowSelection="true"
        :selectionSettings="selectionOptionsSingle"
        :selectedRowIndex="0"
        :pagerTemplate='pagerTemplate'
        @recordClick="rowClickEvent"
        @actionComplete="actionComplete"
        gridLines="Both"
      >
        <e-columns>
          <e-column template="<span class='fa selected-flag'></span>" :allowFiltering="false"
                    filterTemplate="<span></span>"
                    width="26" maxWidth="26" minWidth="26" textAlign="center"
                    :customAttributes="{class: 'column-selected-flag'}"
                    :freeze="'left'" :allowResizing="false" clipMode="Ellipsis"></e-column>
          <e-column field="LOAIHOADON_ID" headerText="STT" :filter="filterOptions" width="70"></e-column>
          <e-column field="TEN_LOAIHD" headerText="Loại hóa đơn" :filter="filterOptions" width="auto"></e-column>
          <e-column field="KYHIEU" headerText="Ký hiệu" :filter="filterOptions"></e-column>
          <e-column field="TENTAT" headerText="Tên Tắt" :filter="filterOptions"></e-column>
          <e-column field="TenKieuHoaDon" headerText="Kiểu" :filter="filterOptions" width="150"></e-column>
        </e-columns>
      </ejs-grid>
    </div>
    <modal-seri
      ref="modalSeri"
      :loai-hoa-don-id="selectedInvoiceType ? selectedInvoiceType.LOAIHOADON_ID : 0">
    </modal-seri>
  </div>
</template>

<script>
import * as api from './api'
import ModalSeri from './modals/ModalSeri'
import breadcrumb from '@/components/breadcrumb'
import { pagingAndFilter } from '../Staff/mixins/pagingAndFilter'
const MODE_CREATE = 1
const MODE_EDIT = 2
export default {
  name: 'InvoiceType',
  mixins: [pagingAndFilter],
  components: {
    ModalSeri,
    breadcrumb
  },
  async created () {
    this.loading(true)
  },
  async mounted () {
    await this.getInvoiceTypeOptions()
    await this.getInvoiceList()
    if (this.invoiceTypeList.length > 0) {
      this.selectedInvoiceType = this.cloneObject(this.invoiceTypeList[0])
      this.setDataForm(this.selectedInvoiceType)
    } else {
      this.selectedInvoiceType = this.getDefaultInvoiceType()
      this.mode = MODE_CREATE
    }
    this.loading(false)
  },

  data: () => ({
    showModalSeri: false,
    form: {
      invoiceType: {
        options: [],
        selected: null
      },
      symbol: '',
      typeofInvoice: '',
      acronymName: ''
    },
    mode: MODE_EDIT,
    invoiceTypeList: [],
    selectedInvoiceType: null,
    showValidError: false,
    isDisableBtnCreate: false,
    isDisableBtnSave: false,
    isDisableBtnCancel: false,
    isDisableBtnDelete: false,
    isDisableBtnGetSeri: false,
    selectedIndex: 0
  }),
  methods: {
    rowClickEvent ($event) {
      if (this.selectedInvoiceType.LOAIHOADON_ID !== $event.rowData.LOAIHOADON_ID) {
        this.mode = MODE_EDIT
        this.setDataForm($event.rowData)
      }
    },

    setDataForm (invoiceType) {
      this.selectedInvoiceType = this.cloneObject(invoiceType)
      this.form.invoiceType.selected = this.selectedInvoiceType.MAP_KIEU
      this.form.symbol = this.selectedInvoiceType.KYHIEU
      this.form.acronymName = this.selectedInvoiceType.TENTAT
      this.form.typeofInvoice = this.selectedInvoiceType.TEN_LOAIHD
    },

    getDefaultInvoiceType () {
      return {
        LOAIHOADON_ID: null,
        TEN_LOAIHD: '',
        KYHIEU: '',
        TENTAT: '',
        KIEU: null,
        MAP_KIEU: null
      }
    },

    async getInvoiceTypeOptions () {
      this.form.invoiceType.options = await api.getInvoiceTypeOptions(
        this.axios
      )
    },

    setStatusButton () {
      this.isDisableBtnCreate = true
      this.isDisableBtnSave = true
      this.isDisableBtnDelete = true
      this.isDisableBtnCancel = true

      if (this.mode === MODE_CREATE) {
        this.isDisableBtnSave = false
        this.isDisableBtnCancel = false
      }

      if (this.mode === MODE_EDIT) {
        this.isDisableBtnCreate = false
        this.isDisableBtnSave = false
        this.isDisableBtnDelete = false
        this.isDisableBtnCancel = false
      }
      this.$refs.txtLoaiHoaDon.focus()
    },

    async getInvoiceList () {
      this.invoiceTypeList = await api.getInvoiceList(this.axios)
    },

    createInvoice () {
      this.selectedInvoiceType = this.getDefaultInvoiceType()
      this.setDataForm(this.selectedInvoiceType)
      this.$refs.tableInvoiceType.clearSelection()
      this.mode = MODE_CREATE
      this.selectedIndex = -1
    },

    async deleteInvoice () {
      this.loading(true)
      if (!confirm('Bạn thật sự muốn xóa loại hóa đơn ?')) return
      let response = await api.deleteInvoice(
        this.axios,
        this.selectedInvoiceType.LOAIHOADON_ID
      )
      if (response.status) {
        this.$toast.success('Xóa dữ liệu thành công!')
        await this.getInvoiceList()
      } else {
        this.$toast.error(response.error)
      }
      this.loading(false)
    },

    cloneObject (value) {
      return JSON.parse(JSON.stringify(value))
    },

    async saveInvoice () {
      if (!this.isValidForm()) {
        this.showValidError = true
        setTimeout(() => {
          this.showValidError = false
        }, 5000)
        return
      }
      this.loading(true)
      let url = '/web-quantri/serihoadon/fn_insert_loai_hoadon'
      let param = {
        ten_loaihd: this.form.typeofInvoice,
        kieu: this.form.invoiceType.selected,
        kyhieu: this.form.symbol,
        tentat: this.form.acronymName
      }

      if (this.mode === MODE_EDIT) {
        url = '/web-quantri/serihoadon/fn_update_loai_hoadon'
        param.loaihoadon_id = this.selectedInvoiceType.LOAIHOADON_ID
      }
      this.selectedIndex = this.$refs.tableInvoiceType.getSelectedRowIndexes()[0]
      const response = await api.saveInvoice(this.axios, url, param)
      if (response.status) {
        const mes = this.mode === MODE_CREATE
          ? 'Thêm mới loại hóa đơn thành công !' : 'Cập nhật loại hóa đơn thành công !'
        this.$toast.success(mes)
        await this.getInvoiceList()
      } else {
        this.$toast.error(response.error)
      }
      this.loading(false)
    },

    isValidForm () {
      let isValid = true
      if (!this.form.invoiceType.selected) {
        this.$toast.error('Bạn chưa nhập Kiểu hóa đơn!')
        isValid = false
      }

      if (!this.form.typeofInvoice || !this.form.typeofInvoice.trim()) {
        this.$toast.error('Bạn chưa nhập Loại hóa đơn!')
        isValid = false
      }

      if (!this.form.acronymName || !this.form.acronymName.trim()) {
        this.$toast.error('Bạn chưa nhập Tên tắt!')
        isValid = false
      }

      if (!this.form.symbol || !this.form.symbol.trim()) {
        this.$toast.error('Bạn chưa nhập Ký hiệu!')
        isValid = false
      }
      return isValid
    },

    openModalSeri () {
      if (this.selectedInvoiceType.KIEU === 2 ||
        this.selectedInvoiceType.KIEU === 4 ||
        this.selectedInvoiceType.KIEU === 5) {
        this.$refs.modalSeri.show()
      } else {
        this.$toast.error('Chức năng này chỉ sử dụng cho loại hóa đơn điện tử!')
      }
    },
    actionComplete ($event) {
      if ($event.name === 'actionComplete' && $event.requestType === 'refresh') {
        this.$nextTick(() => {
          this.$refs.tableInvoiceType.selectRow(this.selectedIndex)
        })
      }
    }
  },
  watch: {
    mode: {
      handler: function () {
        this.setStatusButton()
      }
    }
  }
}
</script>

<style scoped>
/deep/ .grid-ext .e-grid .e-rowcell {
  cursor: pointer !important;
}
</style>
