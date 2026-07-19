<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title"><span class="icon one-notepad"></span>Đăng ký thiết bị IDC</div>
      <div class="close -ap icon-close hidden" data-dismiss="modal"></div>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="javascript:;" @click.prevent="btnGhiLai">
            <span class="icon one-save"></span> Ghi lại
          </a>
        </li>
        <li >
          <a href="javascript:;" @click.prevent="btnHuy">
            <span class="icon one-users"></span> Hủy
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form" >
        <div class="legend-title">
          <span class="icon fa fa-angle-down"> </span>
          Danh sách thiết bị đăng ký
        </div>
        <div class="table-content">
          <DataGrid
            class="table-result table-gachle"
            panelDataHeight="200"
            :columns="grcHDThietBi.header"
            :dataSource="grcHDThietBi.list"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            :showColumnCheckbox="true"
            :enabledSelectFirstRow="false"
            :defaultColumnCheckboxChecked="false"
            ref="gridDSDangKy"
            @selectedItemsChanged="dataCheckedsGan($event, 1)"
          />
        </div>
      </div>
      <div class="box-form">
        <div class="info-row text-center">
          <button class="btn btn-second btn-lg" @click="btnDangky_Click"><span class="icon fa fa-caret-down"></span></button>
          <button class="btn btn-second btn-lg" @click="btnHuy_Click" style="margin-left: 5px;"><span class="icon fa fa-caret-up"></span></button>
        </div>
      </div>
      <div class="box-form" >
        <div class="legend-title">
          <span class="icon fa fa-angle-down"> </span>
          Danh sách thiết bị biến động
        </div>
        <div class="table-content">
          <DataGrid
            class="table-result table-gachle"
            panelDataHeight="200"
            :columns="grcDanhBaTBI.header"
            :dataSource="grcDanhBaTBI.list"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            :showColumnCheckbox="true"
            :enabledSelectFirstRow="false"
            :defaultColumnCheckboxChecked="true"
            ref="gridDSBienDong"
            @selectedItemsChanged="dataCheckedsGan($event, 2)"
          />          
        </div>
      </div>        
    </div>
  </div>
</template>
<style>
.btn-second {
	border-color: #0d6efd !important;
}
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
import api from "./API";
import { Checkbox } from "element-ui";

export default {
  name: "frmDangKyThietBiIDC",
  components: {
    required,
    minLength,
    integer,
    maxValue,
    DatePicker,
    VueFlatPickr,
    breadcrumb,
    DataGrid,
  },
  props: ["dulieu"],
  data() {
    return {
      formPopup: "",
      title: "",
      dateFormat: "DD/MM/YYYY HH:mm:ss",
      rowchonDK: [],
      rowchonDK0: [],
      rowchonBD: [],
      rowchonBD0: [],
      editSettings: { allowEditing: true, mode: "Normal" },
      grcHDThietBi: {
        list: [],
        header: [
          {
            fieldName: "MA_TB_CT",
            headerText: "Mã TB (ĐHSX)",
            allowFiltering: true,
          },
          {
            fieldName: "MA_PHU_CT",
            headerText: "ID Thiết bị",
            allowFiltering: true,
          },
          {
            fieldName: "WEBSITE_CT",
            headerText: "Tên thiết bị",
            allowFiltering: true,
          },
          {
            fieldName: "PARENT_FIELD",
            headerText: "Loại tài sản/thiết bị",
            allowFiltering: true,
          },
          {
            fieldName: "MA_CHUAN_CT",
            headerText: "Serial number",
            allowFiltering: true,
          },
          {
            fieldName: "IP_CT",
            headerText: "IP",
            allowFiltering: true,
          },
          {
            fieldName: "CONGSUAT_CT",
            headerText: "Công suất",
            allowFiltering: true,
          },
          {
            fieldName: "KHONGGIAN_CT",
            headerText: "Không gian",
            allowFiltering: true,
          },
          {
            fieldName: "DOMAIN_CT",
            headerText: "Tầng",
            allowFiltering: true,
          },
          {
            fieldName: "DIACHI_TB_CT",
            headerText: "Địa chỉ RACK",
            allowFiltering: true,
          },
          {
            fieldName: "VITRI_DUOI_CT",
            headerText: "U dưới",
            allowFiltering: true,
          },
          {
            fieldName: "VITRI_TREN_CT",
            headerText: "U trên",
            allowFiltering: true,
          },
          {
            fieldName: "NGAY_TIEPNHAN_CT",
            headerText: "Ngày tiếp nhận (IDC)",
            allowFiltering: true,
          },
          {
            fieldName: "NGAY_SUDUNG_CT",
            headerText: "Ngày sử dụng (IDC)",
            allowFiltering: true,
          },
          {
            fieldName: "NGAY_NGHIEMTHU_CT",
            headerText: "Ngày IDC nghiệm thu",
            allowFiltering: true,
          },
          {
            fieldName: "TRANGTHAI_CT",
            headerText: "Trạng thái HĐ",
            allowFiltering: true,
          },
          {
            fieldName: "MA_GD_CT",
            headerText: "Mã GD (ĐHSX)",
            allowFiltering: true,
          },
          {
            fieldName: "ID_NUMBER",
            headerText: "Số PYC backend IDC",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      grcDanhBaTBI: {
        list: [],
        header: [
          {
            fieldName: "MA_TB_CT",
            headerText: "Mã TB (ĐHSX)",
            allowFiltering: true,
          },
          {
            fieldName: "MA_PHU_CT",
            headerText: "ID Thiết bị",
            allowFiltering: true,
          },
          {
            fieldName: "WEBSITE_CT",
            headerText: "Tên thiết bị",
            allowFiltering: true,
          },
          {
            fieldName: "PARENT_FIELD",
            headerText: "Loại tài sản/thiết bị",
            allowFiltering: true,
          },
          {
            fieldName: "MA_CHUAN_CT",
            headerText: "Serial number",
            allowFiltering: true,
          },
          {
            fieldName: "IP_CT",
            headerText: "IP",
            allowFiltering: true,
          },
          {
            fieldName: "CONGSUAT_CT",
            headerText: "Công suất",
            allowFiltering: true,
          },
          {
            fieldName: "KHONGGIAN_CT",
            headerText: "Không gian",
            allowFiltering: true,
          },
          {
            fieldName: "DOMAIN_CT",
            headerText: "Tầng",
            allowFiltering: true,
          },
          {
            fieldName: "DIACHI_LD",
            headerText: "Nơi đặt (IDC)",
            allowFiltering: true,
          },
          {
            fieldName: "DIACHI_TB_CT",
            headerText: "Địa chỉ RACK",
            allowFiltering: true,
          },
          {
            fieldName: "VITRI_DUOI_CT",
            headerText: "U dưới",
            allowFiltering: true,
          },
          {
            fieldName: "VITRI_TREN_CT",
            headerText: "U trên",
            allowFiltering: true,
          },
          {
            fieldName: "NGAY_TIEPNHAN_CT",
            headerText: "Ngày tiếp nhận (IDC)",
            allowFiltering: true,
          },
          {
            fieldName: "NGAY_SUDUNG_CT",
            headerText: "Ngày sử dụng (IDC)",
            allowFiltering: true,
          },
          {
            fieldName: "NGAY_NGHIEMTHU_CT",
            headerText: "Ngày IDC nghiệm thu",
            allowFiltering: true,
          },
          {
            fieldName: "TRANGTHAI_CT",
            headerText: "Trạng thái HĐ",
            allowFiltering: true,
          },
          {
            fieldName: "MA_GD_CT",
            headerText: "Mã GD (ĐHSX)",
            allowFiltering: true,
          },
          {
            fieldName: "ID_NUMBER",
            headerText: "Số PYC backend IDC",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      thuebao_id: 0,
      hdtb_id: 0,
    };
  },
  watch: { 
    "dulieu.thuebao_id"(val) {
      if (val) this.thuebao_id = val
    },
    "dulieu.hdtb_id"(val) {
      if (val) this.hdtb_id = val
    }
  },
  computed: {},
  mounted() { 
    this.thuebao_id = this.dulieu.thuebao_id ? this.dulieu.thuebao_id : 0
    this.hdtb_id = this.dulieu.hdtb_id ? this.dulieu.hdtb_id : 0
    this.frmDangKyThietBiIDC_Load()
  },
  methods: {
    LAY_DS_THIETBI_IDC_DANGKY: async function () {        
      try {
        this.grcHDThietBi.list = []
        let response = await api.lay_ds_thietbi_idc_dangky(this.axios, this.hdtb_id);
        if (
          response &&
          response.data &&
          response.data.error_code == "BSS-00000000"
        ) {
          if (response.data.data && response.data.data.length > 0) {
            // for (var i = 0; i < response.data.data.length; i++) {
            //   response.data.data[i].CHON = false
            // }
            this.grcHDThietBi.list = this.UpperCasePropertyList(response.data.data);
          }            
        } else {
          this.ShowAlert(
            "Không lấy được DS thiết bị IDC đăng ký " + response.data.message_detail, 2
          );
        }
      } catch (ex) { 
        console.log(ex);
        this.ShowAlert("Lỗi lấy DS thiết bị IDC đăng ký ", 2);
      }
    },
    LAY_DS_THIETBI_IDC_SUDUNG: async function () {        
      try {
        this.grcDanhBaTBI.list = []
        let response = await api.lay_ds_thietbi_idc_sudung(this.axios, this.thuebao_id);
        if (
          response &&
          response.data &&
          response.data.error_code == "BSS-00000000"
        ) {
          if (response.data.data && response.data.data.length > 0) {
            // for (var i = 0; i < response.data.data.length; i++) {
            //   response.data.data[i].CHON = true
            // }
            this.grcDanhBaTBI.list = this.UpperCasePropertyList(response.data.data);
          }
        } else {
          this.ShowAlert(
            "Không lấy được DS thiết bị IDC sử dụng " + response.data.message_detail, 2
          );
        }
      } catch (ex) { 
        console.log(ex);
        this.ShowAlert("Lỗi lấy DS thiết bị IDC sử dụng ", 2);
      }
    },
    frmDangKyThietBiIDC_Load: async function () {
      if (this.hdtb_id != 0) {
        await this.LAY_DS_THIETBI_IDC_DANGKY()
      }

      if (this.thuebao_id != 0) {
        await this.LAY_DS_THIETBI_IDC_SUDUNG()
      }
    },
    CAPNHAT_THIETBI_DK_BIENDONG_IDC: async function () {
      this.loading(true);
      try {
        var data = this.grcDanhBaTBI.list
        if (data.length == 0) {
          this.ShowAlert("Bạn chưa chọn thiết bị đăng ký!", 2)
          return
        }
        
        for (var i = 0; i < data.length; i++) {
          var ds_label = Object.keys(data[i])
          for (var j = 0; j < ds_label.length; j++) {
            if (data[i][ds_label[j]] == null) {
              data[i][ds_label[j]] = ""
            }
          }          
        }

        var input = {
          vhdtb_id: this.hdtb_id, 
          vds_dulieu: data.length > 0 ? JSON.stringify(data) : ""
        }
        let response = await api.capnhat_thietbi_dk_biendong_idc(this.axios, input);
        if (
          response &&
          response.data &&
          response.data.error_code == "BSS-00000000"
        ) {
          if (response.data.data == "ok") {
            this.ShowAlert("Đăng ký thiết bị thành công!", 0);
          } else {
            this.ShowAlert("Đăng ký thiết bị thất bại! " + response.data.message, 2);
          }
        } else {
          this.ShowAlert("Đăng ký thiết bị thất bại! " + response.data.message, 2);
        }
      } catch (ex) { 
        console.log(ex);
        this.ShowAlert("Lỗi Đăng ký thiết bị! " + ex, 2);
        this.loading(false);
      } finally {
        this.loading(false);
      }      
    },
    dataCheckedsGan(data, type) {
      if (type == 1) {
        this.rowchonDK = data;
      } else if (type == 2) {
        this.rowchonBD = data;
      } 
    },
    btnDangky_Click() {           
      this.loading(true);
      var tmp = this.rowchonDK
      if (tmp && tmp.length > 0) {
        for (var i = 0; i < tmp.length; i++) {
          const index = this.grcHDThietBi.list.findIndex((x) => x == tmp[i]);
          if (index > -1) {
            this.grcHDThietBi.list.splice(index, 1);
          }
          // tmp[i].CHON = true
        }
      }
      tmp = tmp.concat(this.grcDanhBaTBI.list)      
      
      setTimeout(() => {        
        this.rowchonDK = []     
        this.grcDanhBaTBI.list = tmp
        this.loading(false);
      }, 200);
    },
    btnHuy_Click: function () {
      this.loading(true);
      var tmp = this.rowchonBD
      if (tmp && tmp.length > 0) {
        for (var i = 0; i < tmp.length; i++) {
          const index = this.grcDanhBaTBI.list.findIndex((x) => x == tmp[i]);
          if (index > -1) {
            this.grcDanhBaTBI.list.splice(index, 1);
          }
          // tmp[i].CHON = false
        }
      }
      tmp = tmp.concat(this.grcHDThietBi.list)      
      
      setTimeout(() => {        
        this.rowchonBD = []     
        this.grcHDThietBi.list = tmp
        this.loading(false);
      }, 200);
    },
    btnGhiLai: function () {
      this.CAPNHAT_THIETBI_DK_BIENDONG_IDC()
    },
    btnHuy: function () {},
    closeForm() {
      this.$emit("form-close", this.dulieu);
    },
    formatPrice(value) {
      let val = (value / 1).toFixed(0).replace(".", ",");
      return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    },
    validateNumber: (event) => {
      let keyCode = event.keyCode;
      if (keyCode < 48 || keyCode > 57) {
        event.preventDefault();
      }
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