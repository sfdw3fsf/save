<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title">
        <span class="icon one-notepad"></span>Đăng ký dịch vụ gia tăng
      </div>
      <div class="close -ap icon-close hidden" data-dismiss="modal"></div>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="javascript:;" @click.prevent="closeForm">
            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp
            nhận
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="table-content">
          <ejs-grid
            ref="grid"
            :dataSource="ds_dichvuGTGT.list"
            locale="vi-VN"
            :allowSorting="true"
            :allowFiltering="true"
            :allowPaging="true"
            :pageSettings="{ pageSize: 50, pageSizes: [50, 100, 200] }"
            :editSettings="editSettings"
            :created="created"
            :rowSelected="rowselect"
            :rowDeselected="rowdeselect"
            height="400"
            :queryCellInfo="queryCellInfo"
          >
            <e-columns>
              <e-column
                field="DICHVUGT_ID"
                headerText="ID"
                headerTextAlign="center"
                textAlign="Center"
                width="60"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="MA_DVGT"
                headerText="Mã dịch vụ"
                headerTextAlign="center"
                textAlign="Left"
                width="150"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="TEN_DVGT"
                headerText="Tên dịch vụ"
                headerTextAlign="center"
                width="700"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="TRANGTHAITB_ID"
                headerText="Trạng thái"
                headerTextAlign="center"
                width="100"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="DANGKY"
                headerText="Đăng ký"
                width="100"
                editType="booleanedit"
                displayAsCheckBox="true"
                type="boolean"
                textAlign="Center"
                :allowEditing="false"
                :allowFiltering="false"
              ></e-column>
              <e-column 
                field="HUY"
                headerText="Hủy"
                width="100"
                editType="booleanedit"
                displayAsCheckBox="true"
                type="boolean"
                textAlign="Center"
                :allowEditing="false"
                :allowFiltering="false"
              ></e-column>
              <e-column v-if="this.dulieu && !this.dulieu.edit_disabled"
                field="SUA"
                headerText="Sửa"
                width="100"
                editType="booleanedit"
                displayAsCheckBox="true"
                type="boolean"
                textAlign="Center"
                :allowEditing="this.dulieu && !this.dulieu.edit_disabled"
                :allowFiltering="false"
              ></e-column>
              <e-column
                field="NOIDUNG"
                headerText="Nội dung/Số lượng"
                headerTextAlign="center"
                textAlign="right"
                width="200"
                :allowEditing="true"
              ></e-column>
              <e-column
                field="CUOC_LD"
                format="N"
                class="cuoclapdat"
                :customAttributes="{ class: 'cuoclapdat' }"
                headerText="Cước lắp đặt"
                headerTextAlign="center"
                textAlign="right"
                width="120"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="VAT_LD"
                format="N"
                class="vatlapdat"
                :customAttributes="{ class: 'vatlapdat' }"
                headerText="Vat lắp đặt"
                headerTextAlign="center"
                textAlign="right"
                width="150"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="TIEN"
                format="N"
                class="tongtien"
                :customAttributes="{ class: 'tongtien' }"
                headerText="Tổng tiền"
                headerTextAlign="center"
                textAlign="right"
                width="150"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="CUOC_SD"
                format="N"
                headerText="Phí duy trì"
                headerTextAlign="center"
                textAlign="right"
                width="150"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="VAT_SD"
                format="N"
                headerText="Vat duy trì"
                headerTextAlign="center"
                textAlign="right"
                width="150"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="SL_CHA"
                format="N"
                headerText="Số lượng cha"
                headerTextAlign="center"
                textAlign="right"
                width="150"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="BLOCK_TIEP"
                format="N"
                headerText="Block"
                headerTextAlign="center"
                textAlign="right"
                width="150"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="GIA_BLOCK_TIEP"
                format="N"
                headerText="Giá Block"
                headerTextAlign="center"
                textAlign="right"
                width="150"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="VAT_BLOCK_TIEP"
                format="N"
                headerText="Vat Block"
                headerTextAlign="center"
                textAlign="right"
                width="150"
                :allowEditing="false"
              ></e-column>
              <e-column
                field="GHICHU"
                headerText="Ghi chú"
                headerTextAlign="center"
                width="200"
                :allowEditing="true"
              ></e-column>
            </e-columns>
            <e-aggregates>
              <e-aggregate>
                <e-columns>
                  <e-column
                    columnName="MA_DVGT"
                    :customAttributes="{ class: 'tien' }"
                    type="Custom"
                    :customAggregate="tongtienAggregateFn"
                    :allowEditing="false"
                  ></e-column>
                  <e-column
                    columnName="CUOC_SD"
                    format="N"
                    type="Custom"
                    :customAggregate="tienAggregateFn"
                    :allowEditing="false"
                  ></e-column>
                  <e-column
                    columnName="VAT_SD"
                    format="N"
                    type="Custom"
                    :customAggregate="vatAggregateFn"
                    :allowEditing="false"
                  ></e-column>
                  <e-column
                    columnName="GIA_BLOCK_TIEP"
                    format="N"
                    type="Custom"
                    :customAggregate="giablkAggregateFn"
                    :allowEditing="false"
                  ></e-column>
                  <e-column
                    columnName="VAT_BLOCK_TIEP"
                    format="N"
                    type="Custom"
                    :customAggregate="vatblkAggregateFn"
                    :allowEditing="false"
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
@import "@/node_modules/@syncfusion/ej2-vue-grids/styles/material.css";
.e-grid .e-rowcell.cuoclapdat,
.cuoclapdat {
  font-weight: bold;
  color: blue;
}
.e-grid .e-rowcell.vatlapdat,
.vatlapdat {
  font-weight: bold;
  color: cadetblue;
}
.e-grid .e-rowcell.tongtien,
.tongtien {
  font-weight: bold;
  color: red;
}

.e-grid .e-rowcell.vat,
.e-grid .e-summarycell.vat,
.vat {
  font-weight: bold;
  color: darkred;
}
.e-grid .e-rowcell.tien,
.e-grid .e-summarycell.tien,
.tien {
  font-weight: bold;
  color: red;
}
</style>
<script>
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
} from "@syncfusion/ej2-vue-grids";
import {
  required,
  minLength,
  integer,
  maxValue,
} from "vuelidate/lib/validators";
import api_installnewsubs from "@/modules/contract/setup/InstallNewSubs/Popups/API.js";
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
  name: "popupServiceAdds",
  components: {
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
  },
  props: ["dulieu"],
  lines: "Both",
  watch: {
    "ds_dichvuGTGT.list": {
      handler: function () {
        this.$forceUpdate();
      },
      deep: true,
    },
  },
  data() {
    return {
      initialSort: {
        columns: [
          { field: "MA_DVGT", direction: "Ascending" },
          { field: "TEN_DVGT", direction: "Descending" },
        ],
      },
      lvDvgt: [],
      ds_dichvuGTGT: {
        list: [],
        header: [],
        value: {},
        isEnabled: true,
        checked: [],
      },
      success_code: "BSS-00000000",
      loaitb_id: "0",
      hdtb_id: "0",
      linhvuc_id: "0",
      tocdo_id: "0",
      dichvudangky: [],
      rowchon: [],
      rowchon0: [],
      phiduytri: 0,
      vatduytri: 0,
      giablock: 0,
      vatblock: 0,
      lapmoi: false,
      editSettings: { allowEditing: true, mode: "Normal" },
    };
  },
  provide: {
    grid: [Sort, Page, Filter, Aggregate, Edit],
  },

  async mounted() {
    try {
      //this.$refs.grid.gridLines = 'Both';
      this.loaitb_id = this.dulieu._loaitb_id ? this.dulieu._loaitb_id : 0;
      this.hdtb_id = this.dulieu._hdtb_id ? this.dulieu._hdtb_id : 0;
      this.linhvuc_id = this.dulieu._linhvuc_id ? this.dulieu._linhvuc_id : 0;
      this.tocdo_id = this.dulieu._tocdo_id ? this.dulieu._tocdo_id : 0;
      let lvDvgt = this.dulieu.lvDvgt && this.dulieu.lvDvgt.length > 0 ? this.UpperCasePropertyList(this.dulieu.lvDvgt) : []
      this.lvDvgt = lvDvgt.filter(e => Number(e['DANGKY']) || Number(e['HUY']) || Number(e['SUA']) || e['SUDUNG'])
      console.log('mounted > lvDvgt', lvDvgt)
      this.GetDsDVGT();
    } catch (e) {
      console.log(e)
    }
  },
  computed: {},
  methods: {
    LowerCasePropertyList(obj) {
      return obj.map(function(item) {
        for (var key in item) {
          var upper = key.toLowerCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    UpperCasePropertyList(obj) {
      return obj.map(function(item) {
        for (var key in item) {
          var upper = key.toUpperCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    tongtienAggregateFn: function (d) {
      return (
        "Tổng: " +
        (
          this.TinhTongAggr(d, "CUOC_SD") * 1 +
          this.TinhTongAggr(d, "VAT_SD") * 1 + 
          this.TinhTongBlockAggr(d, "GIA_BLOCK_TIEP") +
          this.TinhTongBlockAggr(d, "VAT_BLOCK_TIEP")
        ).toLocaleString("en-US")
      );
    },
    tienAggregateFn: function (d) {
      return this.TinhTongAggr(d, "CUOC_SD");
    },
    vatAggregateFn: function (d) {
      return this.TinhTongAggr(d, "VAT_SD");
    },
    giablkAggregateFn: function (d) {
      return this.TinhTongBlockAggr(d, "GIA_BLOCK_TIEP");
    },
    vatblkAggregateFn: function (d) {
      return this.TinhTongBlockAggr(d, "VAT_BLOCK_TIEP");
    },
    TinhTongAggr: function (d, e) {
      var fdata = d.result.filter((item) => item.DANGKY == 1);
      return fdata && fdata.length > 0
        ? fdata.map((x) => x[e] * (x["NOIDUNG"] && Number(x["NOIDUNG"]) > 1 ? Number(x["NOIDUNG"]) : 1)).reduce((a, b) => a + b)
        : 0;
    },
    TinhTongBlockAggr: function (d, e) {
      var fdata = d.result.filter((item) => item.DANGKY == 1);
      return fdata && fdata.length > 0
        ? fdata.map((x) => {
          if (x["SL_CHA"] && Number(x["SL_CHA"]) > 0) {
            if (x["BLOCK_TIEP"].toString() != ""
              && x[e].toString() != ""
              && x[e].toString() != "0")
            {
              if (Number(x["SL_CHA"]) > Number(x["BLOCK_DAU"])) {
                var vsl_conlai = Number(x["SL_CHA"]) - Number(x["BLOCK_DAU"]);

                var t2 = vsl_conlai % Number(x["BLOCK_TIEP"]);
                var vsl_bl = 0;
                if (t2 > 0)
                    vsl_bl = vsl_conlai / Number(x["BLOCK_TIEP"]) + 1;
                else
                    vsl_bl = vsl_conlai / Number(x["BLOCK_TIEP"]);

                var tien_block = 0;
                var count = x["NOIDUNG"] && Number(x["NOIDUNG"]) > 1 ? Number(x["NOIDUNG"]) : 1;
                tien_block = vsl_bl * Number(x[e]);
                return (tien_block * count);
              } else {
                return 0
              }
            } else {
              return 0
            }
          } else {
            return 0
          }
        }).reduce((a, b) => a + b)
        : 0;
    },
    formatPrice(value) {
      let val = (value / 1).toFixed(0).replace(".", ",");
      return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    },

    rowselect() {      
      var grid = this.$refs.grid;
      this.rowchon0 = this.rowchon;
      this.rowchon = grid.getSelectedRecords();
      this.phiduytri = 0;
      this.vatduytri = 0;
      this.giablock = 0;
      this.vatblock = 0;
      var self = this;
      this.rowchon.forEach(function (item) {
        self.ds_dichvuGTGT.list.filter(
          (x) => x.DICHVUGT_ID == item.DICHVUGT_ID
        )[0].NOIDUNG = item.NOIDUNG;
        self.phiduytri =
          self.phiduytri + (item.CUOC_SD == "" ? 0 : item.CUOC_SD) * (item.NOIDUNG && Number(item.NOIDUNG) > 1 ? Number(item.NOIDUNG) : 1);
        self.vatduytri = self.vatduytri + (item.VAT_SD == "" ? 0 : item.VAT_SD) * (item.NOIDUNG && Number(item.NOIDUNG) > 1 ? Number(item.NOIDUNG) : 1);
        self.giablock =
          self.giablock + (item.GIA_BLOCK_TIEP == "" ? 0 : item.GIA_BLOCK_TIEP);
        self.vatblock =
          self.vatblock + (item.VAT_BLOCK_TIEP == "" ? 0 : item.VAT_BLOCK_TIEP);
      });
    },
    rowdeselect() {
      var grid = this.$refs.grid;
      this.rowchon0 = this.rowchon;
      this.rowchon = grid.getSelectedRecords();
      
      for (var item of this.rowchon0) {
        if (
          this.rowchon.filter((x) => x.DICHVUGT_ID == item.DICHVUGT_ID)
            .length <= 0
        ) {
          // this.ds_dichvuGTGT.list.filter(
          //   (x) => x.DICHVUGT_ID == item.DICHVUGT_ID
          // )[0].DANGKY = 0;
          this.phiduytri =
            this.phiduytri - (item.CUOC_SD == "" ? 0 : item.CUOC_SD) * (item.NOIDUNG && Number(item.NOIDUNG) > 1 ? Number(item.NOIDUNG) : 1);
          this.vatduytri =
            this.vatduytri - (item.VAT_SD == "" ? 0 : item.VAT_SD) * (item.NOIDUNG && Number(item.NOIDUNG) > 1 ? Number(item.NOIDUNG) : 1);
          this.giablock =
            this.giablock -
            (item.GIA_BLOCK_TIEP == "" ? 0 : item.GIA_BLOCK_TIEP);
          this.vatblock =
            this.vatblock -
            (item.VAT_BLOCK_TIEP == "" ? 0 : item.VAT_BLOCK_TIEP);
        }
      }
    },
    created(e) {
      this.$refs.grid.ej2Instances.element.addEventListener(
        "click",
        this.click
      );
    },
    click(e) {
      // for normal ediitng
      if (
        e.target.classList.contains("e-rowcell") &&
        e.target.querySelector(".e-checkbox-wrapper")
      ) {
        if (this.$refs.grid.ej2Instances.isEdit)
          this.$refs.grid.ej2Instances.endEdit();
        var index = parseInt(e.target.getAttribute("Index"), 10);
        var rowData = this.$refs.grid.ej2Instances.currentViewData[index];
        var column =
          this.$refs.grid.ej2Instances.columns[
            parseInt(e.target.getAttribute("aria-colindex"), 10)
          ].field;
        var checkValue = !rowData[column];
        rowData.DANGKY = false;
        rowData.HUY = false;
        rowData.SUA = false;
        rowData[column] = checkValue;

        // Update the row with the modified data
        this.$refs.grid.ej2Instances.updateRow(index, rowData);
      }
    },
    closeForm() {
      // this.dichvudangky.forEach(function (item1) {
      //   if (item1.DANGKY == "1") {
      //     item1.HUY = 1;
      //     item1.DANGKY = 0;
      //   }
      // });
      var self = this;
      if (this.ds_dichvuGTGT.list && this.ds_dichvuGTGT.list.length > 0) {
        for (var i = 0; i < this.ds_dichvuGTGT.list.length; i++) {
          this.ds_dichvuGTGT.list[i].DANGKY =
            this.ds_dichvuGTGT.list[i].DANGKY ||
            this.ds_dichvuGTGT.list[i].DANGKY.toString() == "1"
              ? 1
              : 0;
          this.ds_dichvuGTGT.list[i].HUY =
            this.ds_dichvuGTGT.list[i].HUY ||
            this.ds_dichvuGTGT.list[i].HUY.toString() == "1"
              ? 1
              : 0;
          this.ds_dichvuGTGT.list[i].SUA =
            this.ds_dichvuGTGT.list[i].SUA ||
            this.ds_dichvuGTGT.list[i].SUA.toString() == "1"
              ? 1
              : 0;
        }
      }
      this.ds_dichvuGTGT.list
        .filter((x) => x.DANGKY.toString() == "1" || x.SUA.toString() == "1" || x.HUY.toString() == "1")
        .forEach(function (item1) {
          self.dichvudangky.filter(
            (x) => x.DICHVUGT_ID == item1.DICHVUGT_ID
          )[0].DANGKY = item1.DANGKY;
          self.dichvudangky.filter(
            (x) => x.DICHVUGT_ID == item1.DICHVUGT_ID
          )[0].SUA = item1.SUA
          self.dichvudangky.filter(
            (x) => x.DICHVUGT_ID == item1.DICHVUGT_ID
          )[0].HUY = item1.HUY
          self.dichvudangky.filter(
            (x) => x.DICHVUGT_ID == item1.DICHVUGT_ID
          )[0].NOIDUNG = item1.NOIDUNG;
        });
      var changes = self.dichvudangky;
      var _flagdvgtdk = false;
      var _flagdvgt_kodk = true;      
      if (changes && changes.length > 0) {        
        for (var i = 0; i < changes.length; i++) {
          if ((changes[i]["DANGKY"].toString() == "1" || changes[i]["SUA"].toString() == "1")
            && changes[i]["FLAG"] && changes[i]["FLAG"].toString() == "1"
            && (!changes[i]["NOIDUNG"] || changes[i]["NOIDUNG"].toString() == ""))
          {
            this.ShowAlert("Bạn chưa nhập '" + (changes[i]["THONGBAO"] ? changes[i]["THONGBAO"].toString() : "") + "' vào ô Nội dung cho dịch vụ '" + changes[i]["TEN_DVGT"].toString() + "' !",2);
            return;
          }

          if ((changes[i]["DANGKY"].toString() == "1" || changes[i]["SUA"].toString() == "1")
            && changes[i]["KIEU_DL"] && changes[i]["KIEU_DL"].toString() == "1"
            && changes[i]["NOIDUNG"] && changes[i]["NOIDUNG"].toString())
          {
            if (!changes[i]["NOIDUNG"].toString() || Number(changes[i]["NOIDUNG"].toString()) == NaN)
            {
              this.ShowAlert("Nội dung của dịch vụ '" + changes[i]["TEN_DVGT"].toString() + " phải là kiểu số !",2);
              return;
            }
          }

          /* nhapt them 28/11/2016
          * Thực hiện kiểm tra xem có chọn dịch vụ mặc định không?
          * Nếu trường flag =3(dichvu_khac) thì bắt buộc chọn 1 trong các mặc định đó 
          * Nếu không muốn mặc định chọn, có thể bỏ các mặc định trong bảng dichvu_khac di (đưa flag về 0)
          */
          if (changes[i]["FLAG"] && changes[i]["FLAG"].toString() == "3")
          {
            _flagdvgt_kodk = false;
            if (changes[i]["DANGKY"].toString() == "1") _flagdvgtdk = true;
          }

          if (changes[i]["DANGKY"].toString() == "1"
            && changes[i]["TEN_DVGT"].toString() == "PAYTV"
            && changes[i]["NOIDUNG"]) {
            if (Number(changes[i]["NOIDUNG"]) == NaN || Number(changes[i]["NOIDUNG"]) < 3) {
              this.ShowAlert("Thông tin đăng ký PAYTV trọn gói 20.000 không hợp lệ!", 2);
              return;
            }
          }
          
          var index = self.dichvudangky.indexOf(
            (e) => e.MA_DVGT.toString() == changes[i].MA_DVGT.toString()
          ); 
          if (index != -1) {
            self.dichvudangky[index].NOIDUNG = changes[i].NOIDUNG;
            self.dichvudangky[index].GHICHU = changes[i].GHICHU;
          }
        }
      }
      if (!_flagdvgtdk && !_flagdvgt_kodk) {
        this.ShowAlert("Hãy chọn một trong các dịch vụ gia tăng mặc định bắt buộc", 2);
        return;
      }      
      this.$emit("form-close", self.dichvudangky);
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

    GetDsDVGT: async function () {
      //Lấy thông tin chi tiết thuê bao
      var inputstring = {
        LOAITB_ID: this.loaitb_id,
        HDTB_ID: this.hdtb_id,
        LINHVUC_ID: this.linhvuc_id,
        I_TOCDO_ID: this.tocdo_id,
      };
      var input = {};
      input.ds_para = JSON.stringify(inputstring);
      this.loading(true);
      await api_installnewsubs
        .fn_lay_ds_dvgt_macdinh(this.axios, input)
        .then((response) => {
          this.loading(false);
          if (
            response.data.error_code &&
            response.data.error_code == this.success_code
          ) {
            this.ds_dichvuGTGT.list = JSON.parse(
              response.data.data
            ).RESULT.DS_TB_DICHVU;
            this.dichvudangky = JSON.parse(
              response.data.data
            ).RESULT.DS_TB_DICHVU;
            // console.log(this.ds_dichvuGTGT.list);
            // console.log(this.lvDvgt);
            if (
              this.ds_dichvuGTGT.list &&
              this.ds_dichvuGTGT.list.length > 0 &&
              this.lvDvgt &&
              this.lvDvgt.length > 0
            )
              for (var item1 of this.lvDvgt)
                for (var item2 of this.ds_dichvuGTGT.list)
                  if (item1.DICHVUGT_ID == item2.DICHVUGT_ID) {
                    item2.DANGKY = item1.DANGKY;
                    item2.SUA = item1.SUA;
                    item2.HUY = item1.HUY;
                    item2.NOIDUNG = item1.NOIDUNG;
                    break;
                  }
          } else if (response.data.error == 204) {
            this.ShowAlert("Không có danh sách dịch vụ gia tăng cho loại thuê bao này!", 2);
          } else {
            this.ShowAlert("Lỗi lấy danh sách dịch vụ gia tăng!", 2);
          }
        })
        .finally(() => {
          this.loading(false);
        });
    },
    queryCellInfo(args){      
      if (args.column.field === 'DANGKY') {     
        var row_sudung = this.lvDvgt.find(e => e['DICHVUGT_ID'] == args.data['DICHVUGT_ID'])   
        if (row_sudung && row_sudung['SUDUNG'] == 'Su dung') {
          args.cell.classList.remove('e-active')
          args.cell.classList.remove('e-selectionbackground')
          args.cell.classList.add('dang-su-dung')
          args.cell.cancel = true
          console.log('queryCellInfo', args)
        }
      }
    }
  },
};
</script>
<style>
.dang-su-dung{
  color: #d3d3d3 !important;
  background-color: #d3d3d3 !important;
  pointer-events: none !important;
  cursor: not-allowed !important;
  opacity: 0.8 !important;
}
</style>
