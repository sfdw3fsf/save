<template>
  <ejs-dialog
    header="Chọn số chính ISDN"
    showCloseIcon="true"
    width="60%"
    ref="frmChonSoChinhDialogRef"
    :target="'.main-wrapper'"
    :position="{ X: 'center', Y: 'top' }"
    :visible="false"
    :enableResize="true"
    :allowDragging="true"
  >
    <vue-nav>
      <ul class="list">
        <li @click="onOk()">
          <a>
            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
          </a>
        </li>
      </ul>
    </vue-nav>
    <div class="popup-body">
      <vue-card>
        <DataGrid
          :columns="dgvkhoso.headers"
          :dataSource="dgvkhoso.dataSource"
          :allowFiltering="true"
          :showColumnCheckbox="false"
          :enable-paging-server="false"
          :allowPaging="true"
          @selectedRowChanged="selectedRowChanged"
        />
      </vue-card>
    </div>
  </ejs-dialog>
</template>

<script>
export default {
  name: 'frmChonSoChinh',
  data () {
    return {
      dgvkhoso: {
        headers: [
          { fieldName: 'ma_tb', headerText: 'Số máy', allowFiltering: true, allowSorting: false, width: 60 },
          { fieldName: 'trangthai_tb', headerText: 'Trạng thái', allowFiltering: true, allowSorting: false, width: 60 }
        ],
        dataSource: []
      },
      soChinh: ''
    }
  },
  methods: {
    showDialog (ds) {
      this.dgvkhoso.dataSource = ds
      this.$refs.frmChonSoChinhDialogRef.show()
    },
    selectedRowChanged (item) {
      this.soChinh = item.ma_tb
    },
    onOk () {
      this.$emit('onOk', this.soChinh)
      this.$refs.frmChonSoChinhDialogRef.hide()
    }
  }
}
</script>
