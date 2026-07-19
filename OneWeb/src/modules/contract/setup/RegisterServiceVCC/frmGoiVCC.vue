<template>
  <b-modal
    ref="frmGoiVCC"
    id="frmGoiVCC"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-esc
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>DANH SÁCH GÓI CƯỚC VCC
        </div>
        <div class="close -ap icon-close hidden" data-dismiss="modal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript:;" @click="tsbtnNhapMoi_Click">
              <span class="icon one-file-plus"></span> Nhập mới
            </a>
          </li>
          <li>
            <a href="javascript:;" @click.prevent="tsbtnHoanThanh_Click">
              <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
            </a>
          </li>
          <li>
            <a href="javascript:;" @click.prevent="hideModal">
              <span class="fa fa-remove f20 inline vcenter"></span> Thoát
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="box">
            <div class="row">
              <div class="col-sm-6 col-12">
                <DataGrid class="table-result table-gachle" :columns="grdGoiNoiMang.header"
                  :dataSource="grdGoiNoiMang.list" :enable-paging-server="false" :allowPaging="true"
                  :showFilter="true" :showColumnCheckbox="false" :enabledSelectFirstRow="false" ref="grdGoiNoiMang" panelDataHeight="300px"/>
              </div>
              <div class="col-sm-6 col-12">
                <DataGrid class="table-result table-gachle" :columns="grdGoiNgoaiMang.header"
                  :dataSource="grdGoiNgoaiMang.list" :enable-paging-server="false" :allowPaging="true"
                  :showFilter="true" :showColumnCheckbox="false" :enabledSelectFirstRow="false" ref="grdGoiNgoaiMang" panelDataHeight="300px"/>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Thông tin giảm giá gói</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key" style="width: 150px">Giảm giá gói nội mạng</div>
                <div class="value">
                  <div class="select-custom" ref="cboNoiMang">
                    <select2 :z-index="2000" :settings="{ dropdownParent: $refs['cboNoiMang'] }"
                      v-model="cboNoiMang.value" :options="cboNoiMang.Items">
                    </select2>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key" style="width: 160px">Giảm giá gói ngoại mạng</div>
                <div class="value">
                  <div class="select-custom" ref="cboNgoaiMang">
                    <select2 :z-index="2000" :settings="{ dropdownParent: $refs['cboNgoaiMang'] }"
                      v-model="cboNgoaiMang.value"
                      :options="cboNgoaiMang.Items">
                    </select2>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
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
import { Checkbox } from "element-ui";
import {
  LoaiHopDong,
  TrangThaiHD,
  TrangThaiTB,
  DichVuVienThong,
  TRANGBI,
  KieuLapDat,
  KHOANMUC_TT,
} from "@/modules/admin/category/MultiServicePackage/enum.js";
import api from "./API.js"

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
  name: "frmGoiVCC",
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
  watch: {
  },
  data() {
    return {
      vma_tinh: "",
      dichvuvt_id: DichVuVienThong.DI_DONG,
      goicuoc_vcc: "",
      // goicuoc_vcc_noimang: "",
      // goicuoc_vcc_ngoaimang: "",
      giamgia_noimang: "",
      giamgia_ngoaimang: "",
      cboNoiMang: {value: 0, Items: []},
      cboNgoaiMang: {value: 0, Items: []},
      tt_nd: {
        ma_tinh: "",
        phanvung_id: 0, //this.$root.token.getPhanVungID(),
        donvi_id: 0, //this.$root.token.getDonviID(),
        nhanvien_id: 0, //this.$root.token.getNhanVienID(),
        ip_cn: "",
        may_cn: "",
        nguoi_cn: "",
      },
      grdGoiNoiMang: {
        list: [],
        header: [
          {
            fieldName: "ISCHECK",
            headerText: "",
            textAlign: "center",
            width: 70,
            allowFiltering: false,
            template: () => {
              return {
                template: {
                  template: `<div>
                      <input
                        type="checkbox"
                        class="check"
                        @change="onChangeProcessed(data)"
                        :value="data.ID"        
                        v-model="data.ISCHECK"  
                      />
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent.$parent.$parent;
                    },
                  },
                  methods: {
                    onChangeProcessed(value) {
                      var obj =
                        this.parent.grdGoiNoiMang.list.find(
                          (f) => f.ID == value.ID
                        );
                      obj.ISCHECK = value.ISCHECK;
                    },
                  },
                },
              };
            },
          },
          {
            fieldName: "ID",
            headerText: "MÃ GÓI",
            allowFiltering: true,
          },
          {
            fieldName: "NAMEPKG",
            headerText: "GÓI NỘI MẠNG",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      grdGoiNgoaiMang: {
        list: [],
        header: [
          {
            fieldName: "ISCHECK",
            headerText: "",
            textAlign: "center",
            width: 70,
            allowFiltering: false,
            template: () => {
              return {
                template: {
                  template: `<div>
                      <input
                        type="checkbox"
                        class="check"
                        @change="onChangeProcessed(data)"
                        :value="data.ID"         
                        v-model="data.ISCHECK"               
                      />
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent.$parent.$parent;
                    },
                  },
                  methods: {
                    onChangeProcessed(value) {
                      var obj =
                        this.parent.grdGoiNgoaiMang.list.find(
                          (f) => f.ID == value.ID
                        );
                      obj.ISCHECK = value.ISCHECK;                      
                    },                    
                  },
                },
              };
            },
          },
          {
            fieldName: "ID",
            headerText: "MÃ GÓI",
            allowFiltering: true,
          },
          {
            fieldName: "NAMEPKG",
            headerText: "GÓI NGOẠi MẠNG",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
    };
  },
  provide: {
    grid: [Sort, Page, Filter, Aggregate, Edit],
  },

  async mounted() {
    try {
      this.tt_nd = {
        ma_tinh: await this.$root.token.getMaTinh(),
        phanvung_id: Number(await this.$root.token.getPhanVungID()),
        donvi_id: Number(await this.$root.token.getDonViID()),
        nhanvien_id: Number(await this.$root.token.getNhanVienID()),
        ip_cn: await this.$root.token.getIP(),
        may_cn: await this.$root.token.getMachine(),
        nguoi_cn: await this.$root.token.getUserName(),
      };      
    } catch (e) {

    }
  },
  computed: {},
  watch: {
    "dulieu.goicuoc_vcc"(val) {
      if (val || val == 0) this.goicuoc_vcc = val
    },
    "dulieu.giamgia_noimang"(val) {
      if (val || val == 0) this.giamgia_noimang = val
    },
    "dulieu.giamgia_ngoaimang"(val) {
      if (val || val == 0) this.giamgia_ngoaimang = val
    }        
  },
  methods: {
    handleShowModal: async function () {
      this.loading(true);
      try {
        await Promise.all([this.frmGoiVCC_Load()]);
        this.loading(false);
      } catch (ex) {
        this.loading(false);
      }
    },
    frmGoiVCC_Load: async function () {
      try {
        // this.vma_tinh = this.tt_nd.ma_tinh;

        var goi_noimang = await this.ts_vcc_ds_goicuoc("1");
        var goi_ngoaimang = await this.ts_vcc_ds_goicuoc("2");

        if (goi_noimang && goi_noimang.length > 0) {
          for (var i = 0; i < goi_noimang.length; i++) {
            goi_noimang[i]["ISCHECK"] = false;
          }
        }                
        
        if (goi_ngoaimang && goi_ngoaimang.length > 0) {
          for (var i = 0; i < goi_ngoaimang.length; i++) {
            goi_ngoaimang[i]["ISCHECK"] = false;
          }
        }

        if (this.goicuoc_vcc && this.goicuoc_vcc.trim() != "") {
          var arrListStr = this.goicuoc_vcc.split(',');
          for (var i = 0; i < arrListStr.length; i++) {
            for (var j = 0; j < goi_noimang.length; j++)
              if (arrListStr[i] == goi_noimang[j]["ID"].toString()) {
                goi_noimang[j]["ISCHECK"] = true;
              }
            for (var j = 0; j < goi_ngoaimang.length; j++)
              if (arrListStr[i] == goi_ngoaimang[j]["ID"].toString()) {
                goi_ngoaimang[j]["ISCHECK"] = true;
              }
          }
        }

        this.grdGoiNoiMang.list = goi_noimang;
        this.grdGoiNgoaiMang.list = goi_ngoaimang;

        var noimang = await this.ts_vcc_ds_muc_giamgia("3");
        if (noimang && noimang.length > 0) {
          noimang = this.UpperCasePropertyList(noimang);
          this.cboNoiMang.Items = noimang.map(e => ({
            id: e.RATING_,
            text: e.NAME
          }));
          this.cboNoiMang.value = this.cboNoiMang.Items[0].id;
        }
        
        var ngoaimang = await this.ts_vcc_ds_muc_giamgia("3");
        if (ngoaimang && ngoaimang.length > 0) {
          ngoaimang = this.UpperCasePropertyList(ngoaimang);
          this.cboNgoaiMang.Items = ngoaimang.map(e => ({
            id: e.RATING_,
            text: e.NAME
          }));
          this.cboNgoaiMang.value = this.cboNgoaiMang.Items[0].id;
        }        

        
        this.cboNoiMang.value = this.giamgia_noimang ? this.giamgia_noimang : this.cboNoiMang.value;
        this.cboNgoaiMang.value = this.giamgia_ngoaimang ? this.giamgia_ngoaimang : this.cboNgoaiMang.value; 
      } catch (e) {
        console.log(e)
      }
    },
    tsbtnHoanThanh_Click()
    {
      try {
        // grdGoiNoiMang.CloseEditor();
        // grdGoiNoiMang.CloseEditor();

        var goicuoc_vcc = "";
        var goicuoc_vcc_noimang = "";
        var goicuoc_vcc_ngoaimang = "";
        var giamgia_noimang = "";
        var giamgia_ngoaimang = "";

        for (var i = 0; i < this.grdGoiNoiMang.list.length; i++) {
          var is_check = this.grdGoiNoiMang.list[i]["ISCHECK"];
          if (is_check)
          {
            var mobile_noi_id = this.grdGoiNoiMang.list[i]["ID"].toString();
            goicuoc_vcc_noimang = goicuoc_vcc_noimang + mobile_noi_id + ",";
          }
        }

        for (var i = 0; i < this.grdGoiNgoaiMang.list.length; i++) {
          var is_check = this.grdGoiNgoaiMang.list[i]["ISCHECK"];
          if (is_check)
          {
            var mobile_ngoai_id = this.grdGoiNgoaiMang.list[i]["ID"].toString();
            goicuoc_vcc_ngoaimang = goicuoc_vcc_ngoaimang + mobile_ngoai_id + ",";
          }
        }

        goicuoc_vcc = goicuoc_vcc_noimang + goicuoc_vcc_ngoaimang;
        // 3 giá trị cuối cùng:
        goicuoc_vcc = goicuoc_vcc.slice(0, -1);
        
        giamgia_noimang = this.cboNoiMang.value;
        giamgia_ngoaimang = this.cboNgoaiMang.value;
        var data = {
          goicuoc_vcc: goicuoc_vcc,
          giamgia_noimang: giamgia_noimang,
          giamgia_ngoaimang: giamgia_ngoaimang
        }
        this.$emit("xacnhan", data);
        this.hideModal()
      } catch (e) {

      }        
    },
    tsbtnNhapMoi_Click: async function ()
    {
      try {
        this.loading(true)
        var goi_noimang = await this.ts_vcc_ds_goicuoc("1");
        var goi_ngoaimang = await this.ts_vcc_ds_goicuoc("2");

        if (goi_noimang && goi_noimang.length > 0) {
          for (var i = 0; i < goi_noimang.length; i++) {
            goi_noimang[i]["ISCHECK"] = false;
          }
        }                
        
        if (goi_ngoaimang && goi_ngoaimang.length > 0) {
          for (var i = 0; i < goi_ngoaimang.length; i++) {
            goi_ngoaimang[i]["ISCHECK"] = false;
          }
        }
        this.grdGoiNoiMang.list = goi_noimang;
        this.grdGoiNgoaiMang.list = goi_ngoaimang;

        this.cboNoiMang.value = this.cboNoiMang.Items[0].id
        this.cboNgoaiMang.value = this.cboNgoaiMang.Items[0].id        
        this.loading(false)
      }
      catch (e) {
        this.loading(false)
      }       
    },
    ts_vcc_ds_goicuoc: async function (type_id) {
      try {
        var input = {
          type: type_id,
        };
        let response = await api.ts_vcc_ds_goicuoc(this.axios, input);
        if (response.data.error == 200 || response.data.error == "200") {
          return response.data.data;
        } else {
          this.ShowError(response.data.message);
        }
        return [];
      } catch (e) {
        return [];
      }      
    },
    ts_vcc_ds_muc_giamgia: async function (type_id) {
      try {
        var input = {
          type_id: type_id,
        };
        let response = await api.ts_vcc_ds_muc_giamgia(this.axios, input);
        if (response.data.error == 200 || response.data.error == "200") {
          return response.data.data;
        } else {
          this.ShowError(response.data.message);
        }
        return [];
      } catch (e) {
        return [];
      }      
    },    
    selectedGoiNoiMang_click: function (item) {
    },
    selectedGoiNgoaiMang_click: function (item) {
    },
    showModal: function () {
      this.$refs["frmGoiVCC"].show();
    },
    hideModal: function () {
      this.$refs["frmGoiVCC"].hide();
    },
    closeForm() {
      var data = {}
      this.$emit("form-close", data);
    },
    formatPrice(value) {
      let val = (value / 1).toFixed(0).replace(".", ",");
      return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    },
    LowerCasePropertyList(obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toLowerCase();
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key];
            delete item[key];
          }
        }
        return item;
      });
    },
    UpperCasePropertyList(obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toUpperCase();
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key];
            delete item[key];
          }
        }
        return item;
      });
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
  },
};
</script>