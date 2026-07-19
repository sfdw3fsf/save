<template>
  <div class="box-form">
    <div class="row d-flex align-items-center">
      <div class="legend-title d-flex justify-content-between align-items-center">
        <span>Danh sách hạ tầng ảo hoá</span>
        <div style="min-width: 300px;">
          <div class="info-row">
            <div class="value">
              <SelectExt
                v-model="searchConditions.congNgheTrienKhai"
                placeholder="Hạ tầng ảo hoá/Smart Cloud/iIDG/Backup"
                dataTextField="text"
                dataValueField="value"
                :dataSource="congNgheTrienKhaiList"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="table-content">
      <DataGrid
        v-bind:columns="[
          { fieldName: 'stt', headerText: 'STT', textAlign: 'Center', width: 60 },
          { fieldName: 'id', headerText: 'Mã hạ tầng', textAlign: 'Left', allowFiltering: true, width: 80 },
          { fieldName: 'ten', headerText: 'Tên hạ tầng', textAlign: 'Left', allowFiltering: true, width: 200 },
          { fieldName: 'vRAM', headerText: 'vRAM', textAlign: 'Left', allowFiltering: true, width: 150 },
          { fieldName: 'vCPU', headerText: 'vCPU', textAlign: 'Left', allowFiltering: true, width: 150 },
          { fieldName: 'vStorage', headerText: 'vStorage', textAlign: 'Left', allowFiltering: true, width: 120 },
          { fieldName: 'idcName', headerText: 'IDC', textAlign: 'Left', allowFiltering: true, width: 120 },
          {
            fieldName: 'nguoiQuanTri',
            headerText: 'Người quản trị',
            textAlign: 'Left',
            allowFiltering: true,
            width: 150
          },
          { fieldName: 'actions', headerText: 'Thao tác', textAlign: 'Center', template: actionColumn(), width: 120 }
        ]"
        :dataSource="haTangList"
        :showFilter="true"
        :allowPaging="true"
        :enablePagingServer="false"
        :showColumnCheckbox="true"
        :allowExcelExport="true"
        :enabledSelectFirstRow="true"
        dataKeyField="ID"
        ref="grdItems"
      />
    </div>
  </div>
</template>
<script>
export default {
  name: 'HaTangAoHoa',
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
  mounted() {
    this.getDanhMucCongNgheTrienKhai()
    this.loadData()
  },
  data() {
    return {
      congNgheTrienKhaiList: [],
      haTangList: [],
      searchConditions: {
        congNgheTrienKhai: null
      }
    }
  },
  methods: {
    loadData(phanLoai = null) {
      // Load dữ liệu hạ tầng ảo hoá ở đây
    },
    getDanhMucCongNgheTrienKhai() {
      this.congNgheTrienKhaiList = [
        { text: 'Hạ tầng ảo hoá', value: 'HA_TANG_AO_HOA' },
        { text: 'Smart Cloud', value: 'SMART_CLOUD' },
        { text: 'iIDG', value: 'IIDG' },
        { text: 'Backup', value: 'BACKUP' }
      ]
    },
    actionColumn() {}
  }
}
</script>
<style scoped></style>
