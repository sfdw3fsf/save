<template>
  <div class="modal-content popup-box">
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="closeForm">
            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp
            nhận
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="table-content">
          <!-- <table class="table-result table-gachle table-filter ">
                        <tr>
                            <td colspan="16">  -->
          <ejs-grid
            ref="grid"
            :load="load"
            :dataSource="ds_thietbimua.list"
            locale="vi-VN"
            :allowSorting="true"
            :allowFiltering="true"
            :allowPaging="true"
            :pageSettings="{ pageSize: 10, pageSizes: [10, 50, 100, 200] }"
            :gridLines="1"
            :rowSelected="rowselect"
            :rowDeselected="rowdeselect"
            height="400"
            :editSettings="{ allowEditing: true, mode: 'Normal' }"
            :actionComplete="actionComplete"
          >
            <e-columns>
              <e-column
                field="ma_tbi"
                headerText="Mã thiết bị"
                headerTextAlign="center"
                textAlign="Left"
                width="150"
                isPrimaryKey="true"
              ></e-column>
              <e-column
                field="loai_tbi"
                headerText="Tên dịch vụ"
                headerTextAlign="center"
                textAlign="Left"
                width="250"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="soluong"
                format="N"
                headerText="Số lượng"
                headerTextAlign="center"
                textAlign="Left"
                :allowEditing="true"
                editType="numericedit"
                width="100"
              ></e-column>
              <!-- <e-column type='checkbox' field='dangky' headerText='Mua' headerTextAlign="center" textAlign= 'Center' displayAsCheckBox='true' width='50' format='C2' :allowEditing="false" editType="booleanedit"></e-column> -->
              <e-column
                field="dangky"
                headerText="Mua"
                width="100"
                editType="booleanedit"
                displayAsCheckBox="true"
                type="boolean"
                textAlign="Center"
                :allowEditing="true"
              ></e-column>
              <e-column
                field="serial"
                headerText="Serial"
                headerTextAlign="center"
                textAlign="Left"
                width="150"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="tien"
                format="N"
                headerText="Tiền"
                headerTextAlign="center"
                textAlign="Right"
                width="150"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="vat"
                format="N"
                headerText="Vat"
                headerTextAlign="center"
                textAlign="Right"
                width="150"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="tien_tratruoc"
                format="N"
                headerText="Tiền trả trước"
                headerTextAlign="center"
                textAlign="Right"
                width="150"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="vat_tratruoc"
                format="N"
                headerText="Vat trả trước"
                headerTextAlign="center"
                textAlign="Right"
                width="150"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="tien_km"
                format="N"
                headerText="Tiền KM"
                headerTextAlign="center"
                textAlign="Right"
                width="100"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="vat_km"
                format="N"
                headerText="Vat KM"
                headerTextAlign="center"
                textAlign="Right"
                width="100"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="sl_cha"
                headerText="SL Cha"
                headerTextAlign="center"
                textAlign="Right"
                width="100"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="block_tiep"
                format="N"
                headerText="Block"
                headerTextAlign="center"
                textAlign="Right"
                width="100"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="gia_block_tiep"
                format="N"
                headerText="Giá Block"
                headerTextAlign="center"
                textAlign="Right"
                width="100"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="vat_block_tiep"
                format="N"
                headerText="Vat Block"
                headerTextAlign="center"
                textAlign="Right"
                width="100"
                :allowEditing="false"
              ></e-column>
            </e-columns>
            <e-aggregates>
              <e-aggregate>
                <e-columns>
                  <e-column
                    columnName="tien"
                    format="N"
                    type="Custom"
                    :customAggregate="tienAggregateFn"
                  ></e-column>
                  <e-column
                    columnName="vat"
                    format="N"
                    type="Custom"
                    :customAggregate="vatAggregateFn"
                  ></e-column>
                  <e-column
                    columnName="gia_block_tiep"
                    format="N"
                    type="Custom"
                    :customAggregate="giablkAggregateFn"
                  ></e-column>
                  <e-column
                    columnName="vat_block_tiep"
                    format="N"
                    type="Custom"
                    :customAggregate="vatblkAggregateFn"
                  ></e-column>
                </e-columns>
              </e-aggregate>
            </e-aggregates>
          </ejs-grid>
        </div>
      </div>
    </div>
  </div>
</template>
<style>
input.disabled,
select.disabled,
.disabled input,
.disabled select,
.disabled /deep/ .select2-selection {
  background-color: #eee;
  color: lightgray;
}
.e-grid .e-gridheader .e-sortfilter .e-headercelldiv,
.e-grid .e-gridheader .e-stackedheadercelldiv,
.e-grid .e-gridheader .e-headercell .e-headercelldiv.e-headerchkcelldiv {
  padding: 0 5px 0 5px !important;
}
</style>
<script>
import Vue from "vue";
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import { L10n, setCulture } from "@syncfusion/ej2-base";
import {
  GridPlugin,
  Page,
  Filter,
  GridComponent,
  Sort,
  Grid,
  Aggregate,
  Edit,
  Resize,
  Group,
  CommandColumn,
  ExcelExport,
  Freeze,
  Toolbar,
} from "@syncfusion/ej2-vue-grids";
import {
  required,
  minLength,
  integer,
  maxValue,
} from "vuelidate/lib/validators";
import API from "./API";
import { Checkbox } from "element-ui";

L10n.load({
  "vi-VN": {
    grid: {
      EmptyRecord: "Không có bản ghi nào để hiển thị ",
      Item: "Bản ghi",
      Items: "Bản ghi",
      FilterbarTitle: "",
    },
  },
});

export default {
  name: "popupBuyDevices",
  provide: {
    grid: [
      Sort,
      Filter,
      Resize,
      Group,
      Freeze,
      CommandColumn,
      ExcelExport,
      Edit,
      Aggregate,
      Toolbar,
    ],
  },
  components: {
    API,
    required,
    minLength,
    integer,
    maxValue,
    DatePicker,
    L10n,
    setCulture,
    GridPlugin,
    Page,
    Sort,
    Filter,
    GridComponent,
    Grid,
    Aggregate,
    Edit,
  },
  props: ["dulieu"],
  // watch: {
  //     'ds_thietbimua.list': {
  //         handler: function() { console.log(this.$refs.grid.ej2Instances); },
  //         deep:true,
  //     },
  // },
  data() {
    return {
      initialSort: {
        columns: [
          { field: "ma_tbi", direction: "Ascending" },
          { field: "loai_tbi", direction: "Descending" },
        ],
      },
      ds_thietbimua: {
        list: [],
        header: [],
        value: {},
        isEnabled: true,
        checked: [],
      },
      success_code: "BSS-00000000",
      dichvudangky: [],
      rowchon: [],
      rowchon0: [],

      loaitb_id: 0,
      tongtien: 0,
      tongvat: 0,
      giablock: 0,
      vatblock: 0,
    };
  },
  async mounted() {
    this.loaitb_id = this.dulieu._loaitb_id ? this.dulieu._loaitb_id : 0;
    this.GetDsTbiMua();
  },
  computed: {},
  methods: {
    actionComplete(args) {
      if (args.requestType === "save") {
        for (var item of this.ds_thietbimua.list)
          if (item.loaitbi_id == args.data.loaitbi_id) {
            item.dangky = args.data.dangky ? 1 : 0;
            break;
          }
        this.$forceUpdate();
      }
      if (args.requestType === "refresh") {
        console.log(this.ds_thietbimua.list);
      }
    },
    load: function () {
      this.$refs.grid.ej2Instances.element.addEventListener(
        "mouseup",
        function (e) {
          console.log(e, e.target);
          var instance = this.ej2_instances[0];
          if (e.target.classList.contains("e-rowcell")) {
            if (instance.isEdit) instance.endEdit();
            let index = parseInt(e.target.getAttribute("Index"));
            instance.selectRow(index);
            instance.startEdit();
          }
        }
      );
    },
    tienAggregateFn: function (d) {
      return this.TinhTongAggr(d, "tien");
    },
    vatAggregateFn: function (d) {
      return this.TinhTongAggr(d, "vat");
    },
    giablkAggregateFn: function (d) {
      return this.TinhTongAggr(d, "gia_block_tiep");
    },
    vatblkAggregateFn: function (d) {
      return this.TinhTongAggr(d, "vat_block_tiep");
    },
    TinhTongAggr: function (d, e) {
      var fdata = d.result.filter((item) => item.dangky == 1);
      return fdata && fdata.length > 0
        ? fdata.map((x) => x[e]).reduce((a, b) => a + b)
        : 0;
    },
    closeForm() {
      this.dichvudangky = this.ds_thietbimua.list.filter((x) => x.dangky == 1);
      this.$emit("form-close", this.dichvudangky);
    },
    formatPrice(value) {
      let val = (value / 1).toFixed(0).replace(".", ",");
      return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    },
    ShowAlert(mesage, val) {
      if (val == 0) {
        this.$toast.success(mesage);
      } else if (val == 1) {
        this.$toast.warning(mesage);
      } else if (val == 2) {
        this.$toast.error(mesage);
      }
    },
    rowselect(data) {
      // var grid= this.$refs.grid;
      // this.rowchon0 = this.rowchon;
      // this.rowchon = grid.getSelectedRecords();
      // for (var item of this.rowchon)
      // {
      //     this.ds_thietbimua.list.filter(x=>x.loaitbi_id == item.loaitbi_id)[0].dangky=1;
      // }
    },

    rowdeselect() {
      // var grid= this.$refs.grid;
      // this.rowchon0 = this.rowchon;
      // this.rowchon = grid.getSelectedRecords();
      // for (var item of this.rowchon0)
      // {
      //     if (this.rowchon.filter(x => x.loaitbi_id == item.loaitbi_id).length<=0)
      //     {
      //         this.ds_thietbimua.list.filter(x=>x.loaitbi_id == item.loaitbi_id)[0].dangky=0;
      //     }
      // }
    },

    GetDsTbiMua: async function () {
      //Lấy danh sách thiết bị
      var input = {};
      input.vloaitb_id = this.loaitb_id;
      this.loading(true);
      await API.lay_ds_thietbi(this.axios, input)
        .then((response) => {
          this.loading(false);
          if (
            response.data.error_code &&
            response.data.error_code === this.success_code
          ) {
            this.ds_thietbimua.list = response.data.data;
            if (this.dulieu.ds && this.dulieu.ds.length > 0) {
              var ds = this.dulieu.ds;
              this.ds_thietbimua.list.forEach(function (item) {
                var dstb = ds.filter((x) => x.loaitbi_id === item.loaitbi_id);
                if (dstb.length > 0) {
                  item.dangky = 1;
                  item.soluong = dstb[0].soluong;
                } else {
                  item.dangky = 0;
                }
              });
            }
          } else {
            this.ShowAlert("Lỗi lấy danh sách thiết bị!", 2);
          }
        })
        .finally(() => {
          this.loading(false);
        });
    },
  },
};
</script>