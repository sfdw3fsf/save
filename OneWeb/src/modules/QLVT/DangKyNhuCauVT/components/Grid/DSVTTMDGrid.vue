<template>
  <div class="table-content">
    <ejs-grid
      ref="dsvttmdGrid"
      :dataSource="$props.items"
      :allowExcelExport="true"
      :allowPaging="false"
      :allowFiltering="true"
      :allowResizing="true"
      :allowSelection="true"
      :allowSorting="true"
      :gridLines="'Both'"
      :selectionSettings="selectionOptions"
      :filterSettings="filterOptions"
      rowHeight="32"
      locale="vi-VN"
      childMapping="VATTU_ID"
    >
      <e-columns>
        <!-- <e-column field='STT' headerTextAlign='Center' headerText='STT' textAlign='Right' width=60 :filterBarTemplate='templateOptions' freeze='Left' isPrimaryKey='true'></e-column> -->
        <e-column
          type="CHECKBOX"
          :allowSorting="false"
          minWidth="38"
          width="40"
          maxWidth="42"
          freeze="Left"
          headerTextAlign="Center"
        ></e-column>
        <e-column
          field="TEN_VT"
          headerText="Tên vật tư"
          width="300"
          textAlign="Left"
          :filterBarTemplate="templateOptions"
          headerTextAlign="Center"
        >
        </e-column>
        <e-column
          field="MA_VT"
          headerText="Mã VT"
          width="200"
          textAlign="Left"
          :filterBarTemplate="templateOptions"
          headerTextAlign="Center"
        ></e-column>
        <e-column
          field="DVI_TINH"
          headerText="ĐVT"
          width="120"
          textAlign="Left"
          :filterBarTemplate="templateOptions"
          headerTextAlign="Center"
        ></e-column>
        <e-column
          field="GHICHU"
          headerText="Ghi chú"
          width="120"
          textAlign="Left"
          :filterBarTemplate="templateOptions"
          headerTextAlign="Center"
        ></e-column>
        <e-column
          field="PART_NUMBER"
          headerText="Part number"
          width="120"
          textAlign="Right"
          :filterBarTemplate="templateOptions"
          headerTextAlign="Center"
        >
        </e-column>
        <e-column
          field="MOTA"
          headerText="Mô tả"
          width="120"
          textAlign="Left"
          :filterBarTemplate="templateOptions"
          headerTextAlign="Center"
        >
        </e-column>
        <e-column
          field="HANG_SX"
          headerText="Hãng SX"
          width="90"
          textAlign="Left"
          :filterBarTemplate="templateOptions"
          headerTextAlign="Center"
        ></e-column>
        <e-column
          field="SL_TON"
          headerText="SL Tồn"
          width="90"
          freeze="Right"
          textAlign="Right"
          :filterBarTemplate="templateOptions"
          headerTextAlign="Center"
        ></e-column>
        <e-column
          field="SOLUONG"
          headerText="Số lượng"
          width="90"
          freeze="Right"
          :filterBarTemplate="templateOptions"
          headerTextAlign="Center"
          textAlign="Right"
        ></e-column>
      </e-columns>

      <!-- FOOTER -->
      <!-- <e-aggregates v-if="sumFooter.length > 0">
                <e-aggregate>
                <e-columns>
                    <e-column type="Sum" format="N0" field='TIENTRA' :footerTemplate='footerSumSelected'>
                    </e-column>
                </e-columns>
                </e-aggregate>
            </e-aggregates>           -->
    </ejs-grid>
  </div>
</template>
<script>
import Vue from "vue";
import { L10n, setCulture } from "@syncfusion/ej2-base";
import {
  GridPlugin,
  Filter,
  Resize,
  ExcelExport,
  Sort,
  Selection,
  Aggregate,
  Freeze,
} from "@syncfusion/ej2-vue-grids";
import RemotePagination from "@/components/RemotePagination";
import EventBus from "@/utils/eventBus";

setCulture("vi-VN");

L10n.load({
  "vi-VN": {
    grid: {
      EmptyRecord: "Không có bản ghi nào để hiển thị",
    },
  },
});

Vue.use(GridPlugin);

export default Vue.extend({
  components: {},
  name: "DSVTTMDGrid",

  props: {
    items: Array,
  },

  data() {
    return {
      selectionOptions: {
        persistSelection: true,
        type: "Multiple",
        checkboxOnly: true,
      },
      pageOptions: {
        pageSize: 10,
        pageCount: 5,
        pageSizes: true,
        totalRecordCount: 1000,
      },
      filterOptions: {
        enableCaseSensitivity: false,
        mode: "Immediate",
        ignoreAccent: true,
      },
      templateOptions: {
        create: function (args) {
          if (args.column.field === "ID") return '<span class="fa"></span>';
          else if (args.column.field === "CHECKBOX")
            return '<div class="e-checkbox-wrapper e-css"><input class="e-checkselect" type="checkbox"><span class="e-frame e-icons"></span><span class="e-label"> </span></div>';
          else
            return (
              '<div class="input-icon-right"><input id="' +
              args.column.field +
              '_filterBarcell" type="search" class="form-control"> <span class="icon nc-icon-outline ui-1_zoom"></span></div>'
            );
        },
        write: function (args) {},
      },

      cRowSelected: function () {
        return {
          template: Vue.component("columntemplate", {
            template: '<span class="fa"></span>',
          }), // end of template
        };
      },
    };
  },

  methods: {
    // customiseCell: function (args) {
    //   if (args.column.field === "TEN_NHOM") args.cell.classList.add("ten-nhom");
    //   else if (args.column.field === "MA_TT") args.cell.classList.add("ma-tt");
    //   else if (args.column.field === "TIENTRA")
    //     args.cell.classList.add("tien-tra");
    // },
  },

  provide: {
    grid: [Filter, Resize, Selection, Sort, ExcelExport, Aggregate, Freeze],
  },
});
</script>
<style>
/* @import "../../../../node_modules/@syncfusion/ej2-vue-grids/styles/material.css"; */
.ten-nhom {
  color: rgb(55, 0, 255);
}
.ma-tt {
  color: rgb(255, 0, 0);
}
.tien-tra {
  color: rgb(134, 10, 10);
}
</style>
