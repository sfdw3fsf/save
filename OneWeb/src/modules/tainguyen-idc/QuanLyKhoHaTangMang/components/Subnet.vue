<template>
  <div class="box-form">
    <div class="row d-flex align-items-center">
      <div class="legend-title d-flex justify-content-between align-items-center">
        <span>Danh sách Subnet</span>
      </div>
    </div>
    <div class="table-content">
      <DataGrid
        v-bind:columns="[
          { fieldName: 'ten', headerText: 'Tên Subnet', textAlign: 'Left', allowFiltering: true, width: 200 },
          { fieldName: 'loaiIpName', headerText: 'Loại IP', textAlign: 'Left', allowFiltering: true, width: 150 },
          { fieldName: 'phanLoaiIpName', headerText: 'Phân lại IP', textAlign: 'Left', allowFiltering: true, width: 150 },
          { fieldName: 'subnet', headerText: 'Subnet', textAlign: 'Left', allowFiltering: true, width: 120 },
          { fieldName: 'subnetMask', headerText: 'Subnet mask', textAlign: 'Left', allowFiltering: true, width: 120 },
          {
            fieldName: 'idcName',
            headerText: 'IDC',
            textAlign: 'Left',
            allowFiltering: true,
            width: 150
          },
          { fieldName: 'vrfName', headerText: 'VRF', textAlign: 'Left', allowFiltering: true, width: 150 },
          { fieldName: 'zoneName', headerText: 'Zone', textAlign: 'Left', allowFiltering: true, width: 150 },
          { fieldName: 'vlanName', headerText: 'Vlan', textAlign: 'Left', allowFiltering: true, width: 150 },
          { fieldName: 'subnetCha', headerText: 'Subnet cha', textAlign: 'Left', allowFiltering: true, width: 150 },
          {
            fieldName: 'trangThaiCCDVName',
            headerText: 'Trạng thái CCDV',
            textAlign: 'Left',
            allowFiltering: true,
            width: 120
          },
          { fieldName: 'actions', headerText: 'Thao tác', textAlign: 'Center', template: actionColumn(), width: 120 }
        ]"
        :dataSource="subnetList"
        :showFilter="true"
        :allowPaging="true"
        :enablePagingServer="false"
        dataKeyField="id"
        ref="grdItems"
      />
    </div>
  </div>
</template>
<script>
import api from '../api.js'

export default {
  name: 'Subnet',
  props: {
    itemId: {
      type: Number,
      default: 0
    },
    mode: {
      type: String,
      default: 'VIEW', // 'ADD', 'EDIT', 'VIEW'
      validator: (value) => ['ADD', 'EDIT', 'VIEW'].includes(value)
    }
  },
  data() {
    return {
      subnetList: [],
      isDataLoaded: false
    }
  },
  methods: {
    async loadData() {
      if (!this.itemId || this.itemId === 0) return

      try {
        this.$root.showLoading(true)
        const response = await api.getSubnetsByHaTang(this.$root.context, this.itemId)
        this.subnetList = response.data || []
        this.isDataLoaded = true
      } catch (error) {
        console.error('Lỗi khi tải danh sách subnet:', error)
        this.$root.toastError('Lỗi khi tải danh sách subnet')
      } finally {
        this.$root.showLoading(false)
      }
    },
    actionColumn() {
      const self = this
      return function() {
        return {
          template: {
            template: `
              <div style="display: flex; gap: 8px; justify-content: center;">
                <button class="btn btn-sm btn-outline-primary ml-1" @click="onView()" title="Xem">
                    <i class="fa fa-eye"></i>
                </button>
              </div>
            `,
            methods: {
              onView() {
                self.$root.toastInfo('Xem chi tiết subnet ID: ' + this.data.id)
              }
            }
          }
        }
      }
    }
  }
}
</script>
<style scoped></style>
