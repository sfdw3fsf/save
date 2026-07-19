<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span> Loại hình thuê bao</div>
        <div class="close -ap icon-close hidden" data-dismiss="modal"></div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="javascript:;" @click.prevent="btnXacNhan_Click">
                    <span class="icon one-save"></span> Xác nhận
                </a>
            </li>
            <li>
                <a href="javascript:;" @click.prevent="btnHuyBo_Click">
                    <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Hủy bỏ
                </a>
            </li>
        </ul>
    </div>
    <div class="popup-body">            
      <div class="box-form">
        <div class="row">
          <div class="info-row">
            <div class="key">Dịch vụ</div>
            <div class="value">
              <div class="select-custom" ref="cboDichVu">
                <select2 :z-index="2000" :settings="{ dropdownParent: $refs['cboDichVu'] }" ref="cboDichVu"
                  v-model="cboDichVu.value" class="select2" @change="onChangeDichVuVT"
                  :options="cboDichVu.Items && cboDichVu.Items.length > 0 ? cboDichVu.Items.map(e=> ({id: e.DICHVUVT_ID,text: e.TEN_DVVT})) : []">
                </select2>
              </div>
            </div>
          </div>                
        </div>
        <br>
        <div class="box-form">
          <div class="info-row">
            <div class="legend-title">
              <span class="icon fa fa-angle-down"></span> Danh sách loại hình thuê bao
            </div>
            <DataGrid
              class="table-result table-gachle"
              ref="grcLoaiTB"
              v-bind:columns="grcLoaiTB.header"
              v-bind:dataSource="grcLoaiTB.list"
              :enable-paging-server="false"
              :allowPaging="false"
              :showFilter="true"
              :showColumnCheckbox="true"
              :enabledSelectFirstRow="false"
              @selectedItemsChanged="getSelectedItem"
            >
            </DataGrid> 
          </div>
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
@media (min-width: 992px) {
  .modal .modal-huge {
    max-width: 95% !important;
    width: 100% !important;
  }
}
@media (min-width: 576px) {
  .modal .modal-huge {
    max-width: 95% !important;
    width: 100% !important;
  }
}
</style>

<script>
import breadcrumb from "@/components/breadcrumb";
import VueFlatPickr from "vue-flatpickr-component";
import moment from "moment";
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import DataGrid from "@/components/Controls/DataGrid";
import {
  required,
  minLength,
  integer,
  maxValue,
} from "vuelidate/lib/validators";
import {
  LoaiHopDong,
  TrangThaiHD,
  DichVuVienThong,
  listDichVuCNTT,
  TRANGBI,
  KieuLapDat,
  KHOANMUC_TT,
  LoaiHinhTB,
  Loai_KM,
  NHOM_LNV,
  LOAI_DV,
} from "@/modules/admin/category/MultiServicePackage/enum.js";
import { Checkbox } from "element-ui";
import api from "../API.js"

export default {
name: "popupChonLoaiTB",
components: {
  required,
  minLength,
  integer,
  maxValue,
  DatePicker,
  VueFlatPickr,
  breadcrumb,
  DataGrid
},
props: ["dulieu"],
data() {
  return {   
    final_list: [],
    selected_list: [],
    cboDichVu: { value: 0, Items: []},
    grcLoaiTB: {
      list: [],
      header: [
      {
        fieldName: "ID",
        headerText: "ID",
        width: 70,
        allowFiltering: true,
      },
      {
        fieldName: "NAME",
        headerText: "Loại hình TB",
        allowFiltering: true,
      },
      ],
      value: {},
      isEnabled: true,
      checked: [],
    },
  };
},
watch: {  
  "dulieu.ds"(val) {
    if (val && val.length > 0) {
      this.final_list = val;          
    }        
  }
},
computed: {},
created() {    
},
mounted() { 
  Promise.all([this.popupLoad()]);  
},
methods: {  
  layDSDichVuVT: async function () {
    try {
      this.cboDichVu.Items = [];
      let response = await api.SP_LAY_DS_DICHVU(this.axios);
      if (
        response &&
        response.data &&
        response.data.error_code &&
        response.data.error_code == "BSS-00000000"
      ) {
        this.cboDichVu.Items = this.UpperCasePropertyList(response.data.data);
      } else if (
        response &&
        response.data &&
        response.data.error_code &&
        response.data.error_code == "BSS-00000204"
      ) {
        this.cboDichVu.Items =  []
      } else {
        this.$toast.error("Lỗi lấy danh sách dịch vụ: " + response.data.message_detail);
      }
    } catch (e) {
      this.$toast.error("Lỗi lấy danh sách dịch vụ: " + e);
    } 
  },
  layDSLoaiTB: async function () {
    try {
      this.grcLoaiTB.list = [];
      var input = {
        dichvu_id: Number(this.cboDichVu.value)
      }
      let response = await api.SP_LAY_DS_LOAIHINH_TB(this.axios, input);
      if (response && response.data && response.data.error_code == "BSS-00000000") {
        this.grcLoaiTB.list = this.UpperCasePropertyList(response.data.data).map(e => ({ID: e.LOAITB_ID, NAME: e.LOAIHINH_TB}));
      } else if (response && response.data && response.data.error_code == "BSS-00000204") {
        this.grcLoaiTB.list = []
      } else {
        this.$toast.error("Lỗi lấy danh sách loại hình TB: " + response.data.message_detail);
      }
    } catch (e) {
      this.$toast.error("Lỗi lấy danh sách loại hình TB: " + e);
    } 
  },
  onChangeDichVuVT: function () {
    this.loading(true);
    this.layDSLoaiTB();
    this.loading(false);
  },
  getSelectedItem: function (data) {
    this.selected_list = data
  },
  btnXacNhan_Click: function () {
    if (this.selected_list && this.selected_list.length > 0) {
      this.final_list = this.final_list.concat(this.selected_list)
    } 
    this.$emit("form-close", this.final_list);
  },
  btnHuyBo_Click: function () {
    this.$emit("form-close",  this.dulieu.ds)
  },
  closeForm: function (val) {
    if (val) this.$emit("form-close", val)
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
  ShowSuccess: function (msg) {
    this.$toast.success(msg);
  },
  ShowError: function (msg) {
    this.$toast.error(msg);
  },
  popupLoad: async function () {
    try {
      await this.layDSDichVuVT();
      if (this.cboDichVu.Items && this.cboDichVu.Items.length > 0) {
        this.cboDichVu.value = this.cboDichVu.Items[0].DICHVUVT_ID;
        await this.layDSLoaiTB();
      }
      // this.final_list = await this.dulieu.ds && this.dulieu.ds.length > 0 ? this.dulieu.ds : []; 
      
      if (this.final_list && this.final_list.length > 0) {
        for (var i = 0; i < this.final_list.length; i++) {
          var index = this.grcLoaiTB.list.findIndex((x) => x.ID == this.final_list[i].ID)
          if (index > -1) {
            this.grcLoaiTB.list.splice(index, 1)
          }
        }
      }       
    } catch (e) {
      console.log(e)
    }      
  },
},
};
</script>