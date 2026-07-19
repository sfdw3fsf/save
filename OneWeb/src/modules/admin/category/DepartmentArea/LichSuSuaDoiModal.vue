<template>
  <b-modal ref="popupLichSuSuaDoi" size="xl" hide-header hide-header-close hide-footer no-close-on-backdrop body-class="p-0" @show="handleShowModal">
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span> Lịch sử sửa đổi</div>
        <div class="close -ap icon-close" @click="closeForm"></div>
      </div>
      <div class="popup-body">
        <DataGrid :columns="columns" :dataSource="dataSources" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true" />
      </div>
    </div>
  </b-modal>
</template>
<script>
export default {
  name: 'LichSuSuaDoiModal',
  props: {
    dataLS: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      columns: [
        {
          fieldName: 'ten_quan',
          headerText: 'Quận',
          allowFiltering: true,
          allowSorting: true,
          width: 120
        },
        {
          fieldName: 'ten_phuong',
          headerText: 'Phường',
          allowFiltering: true,
          allowSorting: true,
          width: 120
        },
        {
          fieldName: 'ten_pho',
          headerText: 'Phố',
          allowFiltering: true,
          allowSorting: true,
          width: 120
        },
        {
          fieldName: 'ten_ap',
          headerText: 'Ấp',
          allowFiltering: true,
          allowSorting: true,
          width: 120
        },
        {
          fieldName: 'ten_khu',
          headerText: 'Khu',
          allowFiltering: true,
          allowSorting: true,
          width: 120
        },
        {
          fieldName: 'dacdiem',
          headerText: 'Đặc điểm',
          allowFiltering: true,
          allowSorting: true,
          width: 120
        },
        {
          fieldName: 'status',
          headerText: 'Trạng thái',
          allowFiltering: true,
          allowSorting: true,
          width: 120
        },
        {
          fieldName: 'nguoi_cn',
          headerText: 'Người CN',
          allowFiltering: true,
          allowSorting: true,
          width: 120
        },
        {
          fieldName: 'may_cn',
          headerText: 'Máy CN',
          allowFiltering: true,
          allowSorting: true,
          width: 120
        },
        {
          fieldName: 'ngay_cn',
          headerText: 'Ngày CN',
          allowFiltering: true,
          allowSorting: true,
          width: 120
        }
      ],
      dataSources: []
    }
  },
  methods: {
    handleShowModal() {
      this.dataSources = []
      setTimeout(() => {
        this.dataSources = this.dataLS
      }, 500)
    },
    showModal() {
      this.$refs['popupLichSuSuaDoi'].show()
    },
    hideModal() {
      this.$refs['popupLichSuSuaDoi'].hide()
    },
    closeForm() {
      this.hideModal()
    }
  }
}
</script>
