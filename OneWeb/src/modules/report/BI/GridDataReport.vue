<template>
  <div>
    <a v-if="dataSource.length > 0" href="javascript:;">
      <downloadexcel class="btn btn-primary mb-2" :stringifyLongNum="true" :fields="fieldExports2" :data="remakeDataSource" worksheet="export"
                  :name="fileName + '.xls'" >
                 <span class="icon one-excel-download"></span>Xuất Excel
              </downloadexcel>
    </a>
    <!--<downloadexcel v-if="dataSource.length > 0" class="btn btn-primary mb-2 float-right" :fields="fieldExports2" :data="remakeDataSource" worksheet="export"
                  :name="fileName + '.xls'" >
                 <span class="icon one-excel-download"></span>Xuất XLS
              </downloadexcel>-->
    <DataGrid :allowPaging="true" :enablePagingServer="false" ref="grid" v-if="dataSource.length > 0" :allowExcelExport="true" :showColumnCheckbox="false" :enabledSelectFirstRow="false" :dataSource="remakeDataSource" v-bind:columns="gridColumns" />
  </div>
</template>
<script>
import downloadexcel from "./JsonExcel";

export default {
  components: {downloadexcel},
  data() {
    return {
      path: '',
      dataSource: [],
      gridColumns: [],
      loading: false,
      fileName: "path",
      fileType: "'xlsx'"
    }
  },
  computed: {
    remakeDataSource() {
      console.log(this.dataSource[0])
      return this.dataSource.filter((value, i) => i > 0)
    },
    fieldExports2() {
      return this.gridColumns.reduce((obj, item) => Object.assign(obj, {
        [item.fieldName]:
          {
            field: item.fieldName, // nested attribute supported
            callback: (value) => {
              if (this.dataSource[0][item.fieldName] == 'String') {
                if(!isNaN(value)) {
                  if(item.fieldName == "madonvi") {
                    return value ? Number(value) : null
                  }
                }
                return value ? String(value) : null
              }
              if (this.dataSource[0][item.fieldName] == 'Number') {

                if(value === 0 || value === "0") return 0

                return value ? Number(value) : null
              }

              return value ? value : null;
          }
        }

      }), {});
    }

  }
}
</script>
