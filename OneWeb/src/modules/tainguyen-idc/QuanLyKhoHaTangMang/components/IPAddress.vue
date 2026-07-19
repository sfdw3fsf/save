<template>
  <div class="box-form">
    <div class="row d-flex align-items-center">
      <div class="legend-title d-flex justify-content-between align-items-center">
        <span>Danh sách IP Address</span>
      </div>
    </div>
    <div class="table-content">
      <DataGrid
        v-bind:columns="[
          { fieldName: 'ip', headerText: 'IP', textAlign: 'Left', allowFiltering: true, width: 200 },
          { fieldName: 'loaiIP', headerText: 'Loại IP', textAlign: 'Left', allowFiltering: true, width: 150 },
          { fieldName: 'phanLoaiIP', headerText: 'Phân loại IP', textAlign: 'Left', allowFiltering: true, width: 150 },
          { fieldName: 'idcName', headerText: 'IDC', textAlign: 'Left', allowFiltering: true, width: 120 },
          { fieldName: 'vrfName', headerText: 'VRF', textAlign: 'Left', allowFiltering: true, width: 120 },
          { fieldName: 'zoneName', headerText: 'Zone', textAlign: 'Left', allowFiltering: true, width: 120 },
          { fieldName: 'vlanName', headerText: 'Vlan', textAlign: 'Left', allowFiltering: true, width: 120 },
          { fieldName: 'subnet', headerText: 'Subnet', textAlign: 'Left', allowFiltering: true, width: 120 },
          { fieldName: 'mucDichText', headerText: 'Mục đích', textAlign: 'Left', allowFiltering: true, width: 120 },
          {
            fieldName: 'haTangMangName',
            headerText: 'Hạ tầng mạng',
            textAlign: 'Left',
            allowFiltering: true,
            width: 120
          },
          { fieldName: 'thietBiName', headerText: 'Thiết bị', textAlign: 'Left', allowFiltering: true, width: 120 },
          {
            fieldName: 'trangThaiCCDVText',
            headerText: 'Trạng thái CCDV',
            textAlign: 'Left',
            allowFiltering: true,
            width: 120
          },
          { fieldName: 'actions', headerText: 'Thao tác', textAlign: 'Center', template: actionColumn(), width: 120 }
        ]"
        :dataSource="ipList"
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
  name: 'IPAddress',
  props: {
    itemId: {
      type: Number,
      default: 0
    }
  },
  mounted() {
    this.loadData()
  },
  data() {
    return {
      ipList: []
    }
  },
  methods: {
    async loadData() {
      if (!this.itemId) {
        this.ipList = []
        return
      }
      try {
        this.$root.showLoading(true)
        const rs = await api.getIpAddressByHaTang(this.$root.context, this.itemId)
        this.ipList = rs.data ? rs.data : []
      } catch (error) {
        this.$root.toastError('Lỗi khi tải danh sách IP Address')
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
                self.$root.toastInfo('Xem chi tiết IP ID: ' + this.data.id)
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
