<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title"><span class="icon one-notepad"></span>Thông tin thuê bao bổ sung</div>
      <div class="close -ap icon-close hidden" data-dismiss="modal"></div>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="javascript:;" @click.prevent="btnGhiLai">
            <span class="icon one-save"></span> Ghi lại
          </a>
        </li>
        <!-- <li>
          <a href="javascript:;" @click.prevent="btnHuy">
            <span class="icon one-users"></span> Hủy
          </a>
        </li> -->
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="info-row">
          <div class="key">Người ký hồ sơ</div>
          <div class="value">
            <input
              type="text"
              v-model="txtNguoiKyHoSo.value"
              :disabled="txtNguoiKyHoSo.isReadonly"
              ref="txtNguoiKyHoSo"
              class="form-control"
            />
          </div>
        </div>
        <div class="info-row">
          <div class="key">Chức danh</div>
          <div class="value">
            <input
              type="text"
              v-model="txtChucDanh.value"
              :disabled="txtChucDanh.isReadonly"
              ref="txtChucDanh"
              class="form-control"
            />
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
</style>
<script>
import breadcrumb from "@/components/breadcrumb";
import VueFlatPickr from "vue-flatpickr-component";
import moment from "moment";
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
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
  name: "frmHDTB_SUB_V3",
  components: {
    required,
    minLength,
    integer,
    maxValue,
    DatePicker,
    VueFlatPickr,
    breadcrumb,
  },
  props: ["dulieu"],
  data() {
    return {
      isReadonly: false,
      isUpdate: false,
      _hdtb_id: 0,
      _loaitb_id: 0,
      _kieuld_id: 0,
      ds_hdtb_sub: {},
      txtNguoiKyHoSo: { value: "", Enabled: true, isReadonly: false },      
      txtChucDanh: { value: "", dataSource: [], Enabled: true, isReadonly: false },
      txtNguoiDaiDien: { value: "", Enabled: true, isReadonly: false },
    };
  },
  watch: {  
    "dulieu.readOnly"(val) {
      this.isReadonly = val
    },
    "dulieu.isUpdate"(val) {
      this.isUpdate = val
    },
    "dulieu.hdtb_id"(val) {
      this._hdtb_id = val
    },
    "dulieu.vloaitb_id_cbo"(val) {
      this._loaitb_id = val
    },
    "dulieu.vkieuld_id_cbo"(val) {
      this._kieuld_id = val
    },
    "dulieu._hdtbsub"(val) {
      this.ds_hdtb_sub = val
    }
  },
  computed: {},
  async mounted() {
    await this.frmHDTB_SUB_V3_init()
    await this.frmHDTB_SUB_V3_Load()
  },
  methods: {
    LAY_TT_THUEBAO_BOSUNG: async function (vhdtb_id) {
      try {
        var input = {
          vhdtb_id: vhdtb_id
        }
        let response = await api.lay_tt_thuebao_bosung(this.axios, input);
        if (
          response &&
          response.data &&
          response.data.error_code == "BSS-00000000"
        ) {
          return response.data.data;
        } else {
          this.ShowAlert(
            "Không lấy được TTBS " + response.data.message_detail, 2
          );
          return []
        }
      } catch (ex) {
        console.log(ex);
        this.ShowAlert("Lỗi lấy TTBS", 2);
      }
    },
    IVAN_KHOA_FORM_THONGTIN_BOSUNG: async function () {
      try {
        let response = await api.ivan_khoa_form_thongtin_bosung(this.axios, this._kieuld_id);
        if (
          response &&
          response.data &&
          response.data.error_code == "BSS-00000000"
        ) {
          return response.data.data
        } else {
          this.ShowAlert(
            "Không lấy được khóa Form TT IVAN " + response.data.message_detail, 2
          );
          return "error"
        }
      } catch (ex) {
        console.log(ex);
        this.ShowAlert("Lỗi lấy khóa Form TT IVAN", 2);
      }
    },
    frmHDTB_SUB_V3_init () {
      this.isReadonly = this.dulieu.readOnly;
      this.isUpdate = this.dulieu.isUpdate;
      this._hdtb_id = this.dulieu.hdtb_id;
      this._loaitb_id = this.dulieu.vloaitb_id_cbo;
      this._kieuld_id = this.dulieu.vkieuld_id_cbo;
      this.ds_hdtb_sub = this.dulieu._hdtbsub ? this.dulieu._hdtbsub : {};
    },
    frmHDTB_SUB_V3_Load: async function () {
      var dt_load = await this.LAY_TT_THUEBAO_BOSUNG(this._hdtb_id);
      if (dt_load && dt_load.length > 0) {
        this.txtNguoiKyHoSo.value = dt_load[0]["NGUOI_KY_HOSO"]
        this.txtChucDanh.value = dt_load[0]["CHUCDANH"]
      } else {
        this.txtNguoiKyHoSo.value = "";
        this.txtChucDanh.value = "";
      }
      if (!this.isReadonly) {
        this.txtNguoiDaiDien.Enabled = true;
        this.txtChucDanh.Enabled = true;
      } else {
        var khoaform = await this.IVAN_KHOA_FORM_THONGTIN_BOSUNG()
        if (khoaform == "all") {
          this.txtChucDanh.isReadonly = true
          this.txtNguoiDaiDien.isReadonly = true
        } else {
          var ds_khoa = khoaform.split(',')
          if (ds_khoa && ds_khoa.length > 0) {
            for (var i = 0; i < ds_khoa.length; i++) {
              var label = ds_khoa[i]
              this[label].isReadonly = true
            }
          }
        }
      }
      //Hien thi thong tin vua nhap 
      if (!this.dulieu.hdtb_sub) return;
      
      if (this.dulieu.hdtb_sub) {
        this.txtNguoiKyHoSo.value = this.dulieu.hdtb_sub["NGUOI_KY_HOSO"] ? this.dulieu.hdtb_sub["NGUOI_KY_HOSO"] : ""
        this.txtChucDanh.value = this.dulieu.hdtb_sub["CHUCDANH"] ? this.dulieu.hdtb_sub["CHUCDANH"] : ""
      }
    },
    FN_CAPNHAT_HDTB_SUB: async function (hdtb_id, data) {
      try {
        var input = { 
          vhdtb_id: hdtb_id, 
          vds_dulieu: data ? JSON.stringify(data) : ""
        }
        let response = await api.capnhat_hdtb_sub_ra_ca(this.axios, input);
        if (
          response &&
          response.data &&
          response.data.error_code == "BSS-00000000"
        ) {
          if (response.data.data == "ok")
          {
            this.ShowAlert("Cập nhật thông tin thành công", 0);
            return true
          }
          else
          {
            this.ShowAlert("Chưa cập nhật thành công: " + response.data.message, 2);
            return false
          }
        } else {
          this.ShowAlert(
            "Lỗi cập nhật " + response.data.message_detail, 2
          );
          return false
        }      
      } catch (e) { 
        this.ShowAlert(
          "Lỗi cập nhật TBBS" + e, 2
        );
        return false 
      }
    },
    btnGhiLai: async function () {
      if (!this.txtNguoiKyHoSo.value || !this.txtChucDanh.value) {
        this.ShowAlert("Bạn chưa nhập đủ thông tin",2);
        return;
      }
      var row = {}
      row.NGUOI_KY_HOSO = this.txtNguoiKyHoSo.value;
      row.CHUCDANH = this.txtChucDanh.value;
      row.HDTB_ID = this._hdtb_id;

      if (!this.ds_hdtb_sub) this.ds_hdtb_sub = {}
      this.ds_hdtb_sub.NGUOI_KY_HOSO = this.txtNguoiKyHoSo.value;
      this.ds_hdtb_sub.CHUCDANH = this.txtChucDanh.value;

      if (this.isUpdate || this._hdtb_id) {
        var action = await this.FN_CAPNHAT_HDTB_SUB(this._hdtb_id, row)
        if (!action) return
      }
      this.closeForm();
    },
    btnHuy: function () {},
    closeForm() {
      this.$emit("form-close", this.ds_hdtb_sub);
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