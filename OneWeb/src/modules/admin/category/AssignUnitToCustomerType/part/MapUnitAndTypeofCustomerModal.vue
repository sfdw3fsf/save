<template>
  <b-modal size="xl" class="modal-xl" id="popupModalMapUnitAndTypeofCustomer" dialog-class="dialog-content" body-class="body-content" header-class="popup-header" content-class="popup-box" hide-footer>
    <template #modal-header>
      <div class="title"><span class="icon one-notepad"></span> Đơn vị - Loại khách hàng</div>
      <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide('popupModalMapUnitAndTypeofCustomer')"></div>
    </template>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="save"> <span class="icon one-save"></span>Ghi lại </a>
        </li>
      </ul>
    </div>
    <div class="mt-10">
      <div class="row">
        <div class="col-sm-4 col-12">
          <div class="box-form">
            <div class="legend-title">Thông tin đơn vị</div>
            <div class="info-row">
              <div class="value">
                <div class="select-custom">
                  <vue-select :checkbox="false" v-model="selectedTypeofUnit" :options="optionsTypeofUnit" labelField="TEN_LOAIDV" valueField="LOAIDV_ID" label="Loại đơn vị"></vue-select>
                </div>
              </div>
            </div>
            <div class="table-content">
              <DataGrid v-bind:columns="tableUnit.header" v-bind:dataSource="unitTable" :enable-paging-server="false" :allowPaging="true" :showFilter="true" ref="tableUnit" @selectedRowChanged="tableUnitOnSelectedRowChanged"> </DataGrid>
            </div>
          </div>
        </div>
        <div class="col-sm-8 col-12">
          <div class="box-move-select-table">
            <div class="box-col box-form">
              <div class="legend-title">Loại khách hàng đã gán</div>
              <div class="table-content">
                <DataGrid v-bind:columns="loaiKhachHangDaGan.header" v-bind:dataSource="typeofCustomerMappedTable" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="true" ref="loaiKhachHangDaGan"> </DataGrid>
              </div>
            </div>
            <div class="actions">
              <button class="btn" @click="moveMappedToNotMapped">
                <span class="-ap icon-chevron-thin-right"></span>
              </button>
              <button class="btn" @click="moveNotMappedToMapped">
                <span class="-ap icon-chevron-thin-left"></span>
              </button>
            </div>
            <div class="box-col box-form">
              <div class="legend-title">Loại khách hàng chưa gán</div>
              <div class="table-content">
                <DataGrid v-bind:columns="loaiKhachHangChuaGan.header" v-bind:dataSource="typeofCustomerNotMappedTable" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="true" ref="loaiKhachHangChuaGan"> </DataGrid>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import api from '../api'
import { pagingAndFilter } from '../../Staff/mixins/pagingAndFilter'

const MAPPED = 1
const NOT_MAPPED = 2

export default {
  name: 'MapUnitAndTypeofCustomerModal',
  mixins: [pagingAndFilter],
  mounted() {
    this.getListTypeofUnit()
  },
  data: () => ({
    tableUnit: {
      header: [
        { fieldName: 'donvi_id', headerText: 'ID', allowFiltering: true, width: 150 },
        { fieldName: 'ma_dv', headerText: 'Mã KV', allowFiltering: true, width: 150 },
        { fieldName: 'ten_dv', headerText: 'Tên đơn vị', allowFiltering: true, width: 150 },
        { fieldName: 'ten_dv_cha', headerText: 'Đơn vị cha', allowFiltering: true, width: 150 }
      ]
    },
    loaiKhachHangDaGan: {
      header: [
        // { fieldName: 'loaikh_id', headerText: 'loaikh_id', allowFiltering: true, width: 150 },
        { fieldName: 'ma_loaikh', headerText: 'Mã LKH', allowFiltering: true, width: 150 },
        { fieldName: 'ten_loaikh', headerText: 'Loại khách hàng', allowFiltering: true, width: 150 }
      ]
    },
    loaiKhachHangChuaGan: {
      header: [
        // { fieldName: 'loaikh_id', headerText: 'loaikh_id', allowFiltering: true, width: 150 },
        { fieldName: 'ma_loaikh', headerText: 'Mã LKH', allowFiltering: true, width: 150 },
        { fieldName: 'ten_loaikh', headerText: 'Loại khách hàng', allowFiltering: true, width: 150 }
      ]
    },
    selectedTypeofUnit: null,
    selectedUnit: {},
    optionsTypeofUnit: [],
    componentKey: 0,
    unitTable: [],
    typeofCustomerMappedTable: [],
    typeofCustomerNotMappedTable: []
  }),
  components: {},
  methods: {
    dataBound: function() {
      this.$refs.tableUnit.autoFitColumns()
    },
    dataBoundChuaGan: function() {
      this.$refs.loaiKhachHangChuaGan.autoFitColumns()
    },
    dataBoundDaGan: function() {
      this.$refs.loaiKhachHangDaGan.autoFitColumns()
    },
    changeSelectedUnit(rowSelected) {
      if (this.selectedUnit.donvi_id === rowSelected.data.donvi_id) return
      this.selectedUnit = JSON.parse(JSON.stringify(rowSelected.data))
      this.typeofCustomerMappedTable = []
      this.typeofCustomerNotMappedTable = []
      this.getTypeofCustomerList(MAPPED, this.selectedUnit)
      this.getTypeofCustomerList(NOT_MAPPED, this.selectedUnit)
    },

    gridReady($event) {
      if ($event.requestType === 'refresh') {
        this.$refs.tableUnit.clearFiltering()
        this.$refs.tableUnit.selectRow(0)
      }
    },

    getListTypeofUnit() {
      api.getTypeofUnitList(this.axios).then((response) => {
        this.optionsTypeofUnit = response.data.data
      })
    },
    getListUnit() {
      this.loading(true)
      api
        .getUnitList(this.axios, {
          loaidv_id: this.selectedTypeofUnit
        })
        .then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.unitTable = response.data.data
          } else {
            this.unitTable = []
            this.selectedUnit = {}
            this.typeofCustomerMappedTable = []
            this.typeofCustomerNotMappedTable = []
            this.$toast.error('Không có dữ liệu danh sách đơn vị')
          }
        })
        .finally(() => {
          this.loading(false)
        })
    },
    getTypeofCustomerList(type, selectedUnit) {
      api
        .getTypeofCustomerList(this.axios, {
          donvi_id: selectedUnit.donvi_id,
          loaidv_id: this.selectedTypeofUnit,
          kieu: type
        })
        .then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            if (type === NOT_MAPPED) {
              this.typeofCustomerNotMappedTable = response.data.data
            } else {
              this.typeofCustomerMappedTable = response.data.data
            }
          }
        })
    },

    moveMappedToNotMapped() {
      const selectedRecords = this.$refs.loaiKhachHangDaGan.getSelectedRecords()
      if (selectedRecords.length === 0) return

      const a = JSON.parse(JSON.stringify(this.typeofCustomerNotMappedTable))
      const b = JSON.parse(JSON.stringify(this.typeofCustomerMappedTable))

      a.push(...selectedRecords)
      this.typeofCustomerNotMappedTable = a
      selectedRecords.forEach((item) => {
        const index = b.findIndex((findItem) => findItem.loaikh_id === item.loaikh_id)
        b.splice(index, 1)
      })
      this.typeofCustomerMappedTable = b
    },

    moveNotMappedToMapped() {
      const selectedRecords = this.$refs.loaiKhachHangChuaGan.getSelectedRecords()
      if (selectedRecords.length === 0) return

      const a = JSON.parse(JSON.stringify(this.typeofCustomerMappedTable))
      const b = JSON.parse(JSON.stringify(this.typeofCustomerNotMappedTable))

      a.push(...selectedRecords)
      this.typeofCustomerMappedTable = a
      selectedRecords.forEach((item) => {
        const index = b.findIndex((findItem) => findItem.loaikh_id === item.loaikh_id)
        b.splice(index, 1)
      })
      this.typeofCustomerNotMappedTable = b
    },

    save() {
      if (!!this.selectedUnit || !this.selectedUnit.donvi_id)
        var param = {
          donvi_id: this.selectedUnit.donvi_id,
          loaidv_id: this.selectedTypeofUnit,
          json_data: JSON.stringify(
            this.typeofCustomerMappedTable.map((item) => {
              return {
                LOAIKH_ID: item.loaikh_id
              }
            })
          )
        }
      api
        .save(this.axios, param)
        .then((response) => {
          if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            this.$toast.success('Cập nhật dữ liệu thành công!')
          } else {
            this.$toast.error(response.data.message_detail)
          }
        })
        .catch(() => {
          this.$toast.error('Cập nhật dữ liệu lỗi!')
        })
    },
    tableUnitOnSelectedRowChanged(obj) {
      if (this.selectedUnit.donvi_id === obj.donvi_id) return
      this.selectedUnit = JSON.parse(JSON.stringify(obj))
      this.typeofCustomerMappedTable = []
      this.typeofCustomerNotMappedTable = []
      this.getTypeofCustomerList(MAPPED, this.selectedUnit)
      this.getTypeofCustomerList(NOT_MAPPED, this.selectedUnit)
    }
  },
  watch: {
    selectedTypeofUnit: {
      handler() {
        this.getListUnit()
      }
    }
  }
}
</script>

<style scoped>
.mt-10 {
  margin-top: 10px;
}
</style>
