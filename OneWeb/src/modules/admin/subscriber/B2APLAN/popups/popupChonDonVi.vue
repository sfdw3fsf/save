<template>
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title"><span class="icon one-notepad"></span>Tick chọn các đơn vị bên dưới</div>
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
                <DataGrid
                    class="table-result table-gachle"
                    ref="grcTinh"
                    v-bind:columns="grcTinh.header"
                    v-bind:dataSource="grcTinh.list"
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
name: "popupChonDonVi",
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
        grcTinh: {
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
                headerText: "Tên tỉnh",
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
            this.final_list = val            
        }        
    }
},
computed: {},
created() {    
},
mounted() { 
    this.popupLoad()
},
methods: {  
    layDSTinh: async function () {
      try {
        this.grcTinh.list = []
        let response = await api.SP_LAY_DS_TINH(this.axios);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data && response.data.data.length > 0
        ) {
          this.grcTinh.list = this.UpperCasePropertyList(response.data.data).map(e => ({ID: e.TINH_ID, NAME: e.TENTINH}));
        } else if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000204"
        ) {
            this.grcTinh.list = []
        } else {
            this.$toast.error("Lỗi lấy danh sách tỉnh: " + response.data.message_detail);
            this.grcTinh.list = []
        }
      } catch (e) {
        this.$toast.error("Lỗi lấy danh sách tỉnh: " + e);
        this.grcTinh.list = []
      } 
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
        this.$emit("form-close", this.dulieu.ds)
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
        await this.layDSTinh();    
        this.final_list = await this.dulieu.ds && this.dulieu.ds.length > 0 ? this.dulieu.ds : []; 
        
        if (this.final_list && this.final_list.length > 0) {
            for (var i = 0; i < this.final_list.length; i++) {
                var index = this.grcTinh.list.findIndex((x) => x.ID == this.final_list[i].ID)
                if (index > -1) {
                    this.grcTinh.list.splice(index, 1)
                }
            }
        }       
    },
},
};
</script>