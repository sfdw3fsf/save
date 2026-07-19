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
          <div class="key">Loại giấy tờ</div>
          <div class="value">
            <select2 :z-index="2000" ref="cbLoaiGT"
              v-model="cbLoaiGT.value"
              :disabled="cbLoaiGT.isReadonly"
              class="select2"
              :options="cbLoaiGT.dataSource.map(e=> ({id: e.loaigt_id,text: e.loai_gt}))"></select2>
          </div>
        </div>
        <div class="info-row">
          <div class="key">Số giấy tờ</div>
          <div class="value">
            <input
              type="text"
              v-model="tbSoGT.value"
              :disabled="tbSoGT.isReadonly"
              ref="tbSoGT"
              class="form-control"
            />
          </div>
        </div>
        <div class="info-row">
          <div class="key">Ngày hết hạn</div>
          <div class="value">
            <DatePicker
              :format="dateFormat"
              :value-type="'format'"
              v-model="dtpNgayHetHan.value"
              :disabled="dtpNgayHetHan.isReadonly"
              :input-attr="{
                required: 'true',
                title: 'Bạn chưa nhập ngày hết hạn!',
              }"
              @change="changeNgayHH"
            >
            </DatePicker>
          </div>
        </div>
        <div class="info-row">
          <div class="key">Chức danh</div>
          <div class="value">
            <input
              type="text"
              v-model="tbChucDanh.value"
              :disabled="tbChucDanh.isReadonly"
              ref="tbChucDanh"
              class="form-control"
            />
          </div>
        </div>
        <div class="info-row">
          <div class="key">CN (Tên TB)</div>
          <div class="value">
            <input
              type="text"
              v-model="txtTenTB.value"
              :disabled="txtTenTB.isReadonly"
              ref="lyiTenTB"
              class="form-control"
            />
          </div>
        </div>
        <div class="info-row">
          <div class="key">O/OU(Tên KH)</div>
          <div class="value">
            <input
              type="text"
              v-model="txtTenKH.value"
              :disabled="txtTenKH.isReadonly"
              ref="lyiTenKH"
              class="form-control"
            />
          </div>
        </div>
        <div class="info-row">
          <div class="key">Người đại diện</div>
          <div class="value">
            <input
              type="text"
              v-model="txtNguoiDaiDien.value"
              :disabled="txtNguoiDaiDien.isReadonly"
              ref="txtNguoiDaiDien"
              class="form-control"
            />
          </div>
        </div>
        <div class="info-row">
          <div class="key">Địa chỉ thuê bao</div>
          <div class="value">
            <input
              type="text"
              v-model="txtDiaChiThueBao.value"
              :disabled="txtDiaChiThueBao.isReadonly"
              ref="txtDiaChiThueBao"
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
  name: "frmHDTB_SUB_V2",
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
      dateFormat: "DD/MM/YYYY HH:mm:ss",
      isUpdated: false,
      isRAUpdate: false,
      isReadonly: false,
      loaitb_id: -1,
      tbChucDanh: { value: "", isReadonly: false, dataSource: [] },
      tbSoGT: { value: "", isReadonly:false, dataSource: [] },
      cbLoaiGT: { value: "", isReadonly: false, dataSource: [] },
      dtpNgayHetHan: { value: "", isReadonly: false },
      txtTenTB: { value: "", isReadonly: false },
      txtTenKH: { value: "", isReadonly: false },
      txtNguoiDaiDien: { value: "", isReadonly: false },
      txtDiaChiThueBao: { value: "", isReadonly: false },
      hdtb_sub: {}
    };
  },
  watch: {
    "dulieu.LOAIGT_ID"(val) {
      this.cbLoaiGT.value = val
    },
    "dulieu.CHUCDANH"(val) {
      this.tbChucDanh.value = val
    },
    "dulieu.NGAYHETHAN_GT"(val) {
      this.dtpNgayHetHan.value =  moment(val).format(this.dateFormat);
    },
    "dulieu.SO_GT"(val) {
      this.tbSoGT.value = val
    },
    "dulieu.NGUOI_DD"(val) {
      this.txtNguoiDaiDien.value = val
    },
  },
  computed: {},
  async mounted() {
    console.log('mounted')
    console.log(this.dulieu)
    await this.frmHDTB_SUB_V2_init()
    await this.frmHDTB_SUB_V2_Load()
  },
  methods: {
    LoadThongTinLoaiGT: async function () {
      try {
        let response = await api.lay_thongtin_loaigt(this.axios);
        if (
          response &&
          response.data &&
          response.data.error_code == "BSS-00000000"
        ) {
          this.cbLoaiGT.dataSource = response.data.data;
          if (!this.cbLoaiGT.value) {
            this.cbLoaiGT.value = response.data.data[0].loaigt_id
          }
        } else {
          this.ShowError(
            "Không lấy được DS loại GT " + response.data.message_detail
          );
        }
      } catch (ex) {
        console.log(ex);
        this.ShowError("Lỗi lấy loại GT");
      }
    },
    lay_thongtin_loaitb_id: async function () {
      try {
        var hdtb_id = this.dulieu.hdtb_id
        let response = await api.lay_thongtin_loaitb_id(this.axios, hdtb_id);
        if (
          response &&
          response.data &&
          response.data.error_code == "BSS-00000000"
        ) {
          this.loaitb_id = response.data.data;
        } else {
          this.ShowError(
            "Không lấy được loại TB " + response.data.message_detail
          );
        }
      } catch (ex) {
        console.log(ex);
        this.ShowError("Lỗi lấy loại TB");
      }
    },
    LAY_TT_THUEBAO_BOSUNG_CA: async function () {
      console.log('lay_tt_thuebao_bosung_ca')
      try {
        var hdtb_id = this.dulieu.hdtb_id
        let response = await api.lay_tt_thuebao_bosung_ca(this.axios, hdtb_id);

        if (
          response &&
          response.data &&
          response.data.error_code == "BSS-00000000"
        ) {
          if (response.data.data && response.data.data.length > 0) {
            this.tbChucDanh.value = response.data.data[0]["CHUCDANH"]
            this.tbSoGT.value = response.data.data[0]["SO_GT"]
            this.cbLoaiGT.value = response.data.data[0]["LOAIGT_ID"]
            this.txtTenKH.value = response.data.data[0]["TEN_KH"]
            this.txtTenTB.value = response.data.data[0]["TEN_TB"]
            this.txtNguoiDaiDien.value = response.data.data[0]["NGUOI_DD"]
            this.txtDiaChiThueBao.value = response.data.data[0]["DIACHI_TB"]
            this.dtpNgayHetHan.value = moment(this.dulieu.hdtb_sub.NGAYHETHAN_GT).format(this.dateFormat);
          }
        } else {
          this.ShowError(
            "Không lấy được loại TB " + response.data.message_detail
          );
        }
      } catch (ex) {
        console.log(ex);
        this.ShowError("Lỗi lấy loại TB");
      }
    },
    IVAN_KHOA_FORM_THONGTIN_BOSUNG: async function () {
      try {
        var kieuld_id = this.dulieu.vkieuld_id_cbo
        let response = await api.ivan_khoa_form_thongtin_bosung(this.axios, kieuld_id);
        if (
          response &&
          response.data &&
          response.data.error_code == "BSS-00000000"
        ) {
          return response.data.data
        } else {
          this.ShowError(
            "Không lấy được khóa Form TT IVAN " + response.data.message_detail
          );
          return "error"
        }
      } catch (ex) {
        console.log(ex);
        this.ShowError("Lỗi lấy khóa Form TT IVAN");
      }
    },
    CA_KHOA_FORM_THONGTIN_BOSUNG: async function () {
      try {
        var kieuld_id = this.dulieu.vkieuld_id_cbo
        let response = await api.ca_khoa_form_thongtin_bosung(this.axios, kieuld_id);
        if (
          response &&
          response.data &&
          response.data.error_code == "BSS-00000000"
        ) {
          return response.data.data
        } else {
          this.ShowError(
            "Không lấy được khóa Form TT CA " + response.data.message_detail
          );
          return "error"
        }
      } catch (ex) {
        console.log(ex);
        this.ShowError("Lỗi lấy khóa Form TT CA");
      }
    },
    frmHDTB_SUB_V2_init: function () {
      var check = Object.keys(this.dulieu).findIndex(e => e == "readonly_init")
      if (check && check > 0 ) {
        this.isRAUpdate = true;
        this.txtTenKH.Enabled = true;
        this.txtTenTB.Enabled = true;
        this.isReadonly = this.dulieu.readonly_init;
        this.txtNguoiDaiDien.Enabled = true;
      } else {
        this.hdtb_sub = this.dulieu.hdtb_sub;
        this.isUpdated = false;
        this.txtTenKH.Enabled = false;
        this.txtTenTB.Enabled = false;
        this.txtNguoiDaiDien.Enabled = false;
      }
    },
    frmHDTB_SUB_V2_Load: async function () {
      await this.LAY_TT_THUEBAO_BOSUNG_CA()
      if (this.isRAUpdate && this.dulieu && this.dulieu.loaitb_id != LoaiHinhTB.IVAN) {
        if (this.isReadonly)
        {
          this.tbChucDanh.isReadonly = this.isReadonly;
          this.tbSoGT.isReadonly = this.isReadonly;
          this.cbLoaiGT.isReadonly = this.isReadonly;
          this.txtTenKH.isReadonly = this.isReadonly;
          this.txtTenTB.isReadonly = this.isReadonly;
          this.txtNguoiDaiDien.isReadonly = this.isReadonly;
          this.dtpNgayHetHan.isReadonly = this.isReadonly;
        }
        await this.LoadThongTinLoaiGT()
        // this.LAY_TT_THUEBAO_BOSUNG_CA()
      } else {
        this.LoadThongTinLoaiGT()
        var khoaform = ""
        if (this.dulieu.loaitb_id == LoaiHinhTB.IVAN)
        {
          khoaform = await this.IVAN_KHOA_FORM_THONGTIN_BOSUNG()
        }
        else
        {
          khoaform = await this.CA_KHOA_FORM_THONGTIN_BOSUNG()
        }
        if (khoaform == "all") {
          this.tbChucDanh.isReadonly = true
          this.tbSoGT.isReadonly = true
          this.cbLoaiGT.isReadonly = true
          this.dtpNgayHetHan.isReadonly = true
          this.txtTenTB.isReadonly = true
          this.txtTenKH.isReadonly = true
          this.txtNguoiDaiDien.isReadonly = true
          this.txtDiaChiThueBao.isReadonly = true
        } else {
          var ds_khoa = khoaform.split(',')
          if (ds_khoa && ds_khoa.length > 0) {
            for (var i = 0; i < ds_khoa.length; i++) {
              var label = ds_khoa[i]
              this[label].isReadonly = true
            }
          }
        }
        this.tbChucDanh.value = this.dulieu.hdtb_sub.CHUCDANH;
        this.tbSoGT.value = this.dulieu.hdtb_sub.SO_GT;
        this.dtpNgayHetHan.value = moment(this.dulieu.hdtb_sub.NGAYHETHAN_GT).format(this.dateFormat);
        this.cbLoaiGT.value = this.dulieu.hdtb_sub.LOAIGT_ID;
      }
    },
    GhiLaiGH: function () {
      if (!this.tbChucDanh.value || !this.tbSoGT.value || !this.cbLoaiGT.value || !this.dtpNgayHetHan.value) {
        this.ShowError("Bạn chưa nhập đầy đủ các trường thông tin!");
        return;
      }
      this.hdtb_sub.CHUCDANH = this.tbChucDanh.value;
      this.hdtb_sub.NGUOI_DD = this.txtNguoiDaiDien.value;
      this.hdtb_sub.LOAIGT_ID = this.cbLoaiGT.value;
      this.hdtb_sub.SO_GT = this.tbSoGT.value;
      this.hdtb_sub.NGAYHETHAN_GT =  moment(this.dulieu.hdtb_sub.NGAYHETHAN_GT, "DD/MM/YYYY HH:mm:ss");;
      this.isUpdated = true;
      this.ShowSuccess("Cập nhật thành công thông tin bổ sung");
      this.closeForm();
    },
    GhiLaiRA: async function() {
      var _hdtb_sub_ra = {
        NGUOI_DD: this.txtNguoiDaiDien.value ? this.txtNguoiDaiDien.value : "",
        LOAIGT_ID: this.cbLoaiGT.value ? this.cbLoaiGT.value : 0,
        SO_GT: this.tbSoGT.value ? this.tbSoGT.value : "",
        TEN_KH: this.txtTenKH.value ? this.txtTenKH.value : "",
        TEN_TB: this.txtTenTB.value ? this.txtTenTB.value : "",
        CHUCDANH: this.tbChucDanh.value ? this.tbChucDanh.value : ""
      }
      var input = {
        vhdtb_id: this.dulieu.hdtb_id,
        vds_dulieu: JSON.stringify(_hdtb_sub_ra)
      }
      let response = await api.capnhat_hdtb_sub_ra_ca(this.axios, input);
      if (
        response &&
        response.data &&
        response.data.error_code == "BSS-00000000"
      ) {
        if (response.data.data == "ok")
        {
          this.ShowSuccess("Cập nhật thông tin thành công");
          this.hdtb_sub.CHUCDANH = this.tbChucDanh.value;
          this.hdtb_sub.NGUOI_DD = this.txtNguoiDaiDien.value;
          this.hdtb_sub.LOAIGT_ID = this.cbLoaiGT.value;
          this.hdtb_sub.SO_GT = this.tbSoGT.value;
          this.hdtb_sub.NGAYHETHAN_GT = this.dtpNgayHetHan.value;
          this.closeForm();
        }
        else
        {
          this.ShowError(response.data.message);
        }
      } else {
        this.ShowError(
          "Lỗi cập nhật " + response.data.message_detail
        );
      }
    },
    btnGhiLai: function () {
      // console.log(this.dulieu)
      // this.dtpNgayHetHan.value = moment(this.dulieu.hdtb_sub.NGAYHETHAN_GT).format(this.dateFormat);
      // console.log(this.dtpNgayHetHan.value)
      //
      // return
      if (this.isRAUpdate)
      {
        this.GhiLaiRA();
      }
      else
      {
        this.GhiLaiGH();
      }
    },
    btnHuy: function () {},
    closeForm() {
      this.$emit("form-close", this.hdtb_sub);
    },
    changeNgayHH: function (value_) {
      if (!moment(value_, "DD/MM/YYYY HH:mm:ss").isValid()) {
        this.dtpNgayHetHan.value = "01/01/2000 00:00:00";
      } else {
        this.dtpNgayHetHan.value = value_;
      }
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
    ShowSuccess: function (msg) {
      this.$toast.success(msg);
    },
    ShowError: function (msg) {
      this.$toast.error(msg);
    },
  },
};
</script>
