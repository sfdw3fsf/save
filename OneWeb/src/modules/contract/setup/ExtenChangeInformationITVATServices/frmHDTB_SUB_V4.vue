<template>
  <div class="modal-content popup-box">
      <div class="popup-header">
          <div class="title"><span class="icon one-notepad"></span> Thông tin thuê bao bổ sung</div>
          <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide('frmHDTB_SUB_V4')">
          </div>
      </div>
      <div class="list-actions-top">
          <ul class="list">
              <li>
                  <a href="#" @click.prevent="frmHDTB_SUB_V4_tsbtnGhiLai_Click">
                      <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
                  </a>
              </li>
              <li>
                  <a href="#" @click.prevent="$bvModal.hide('frmHDTB_SUB_V4')">
                      <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát
                  </a>
              </li>
          </ul>
      </div>
      <div class="popup-body">
          <div class="box-form">
              <div class="info-row">
                  <div class="key">Người đại diện</div>
                  <div class="value">
                      <input type="text"  :value="txtNguoiKyHoSo.value" @change="v => txtNguoiKyHoSo.value = v.target.value" class="form-control" />
                  </div>
              </div>
              <div class="info-row">
                  <div class="key">Ngày sinh</div>
                  <div class="value">
                      <date-picker input-class="form-control" :format="dateFormat" :value-type="dateFormat" v-model="dtpNgaySinh.value"></date-picker>
                  </div>
              </div>
              <div class="info-row">
                  <div class="key">Giới tính</div>
                  <div class="value">
                      <div class="list-checks mart7">
                          <div class="item">
                              <div class="check-action">
                                  <input type="radio" class="check" value="1" v-model="cboGioiTinh.value">
                                  <span class="name">Nam</span>
                              </div>
                          </div>
                          <div class="item">
                              <div class="check-action">
                                  <input type="radio" class="check" value="2" v-model="cboGioiTinh.value">
                                  <span class="name">Nữ</span>
                              </div>
                          </div>
                      </div>
                  </div>
              </div>
              <div class="info-row">
                  <div class="key">Số giấy tờ</div>
                  <div class="value">
                      <input type="text"  :value="txtSoGT.value" @change="v=>txtSoGT.value=v.target.value" class="form-control" />
                  </div>
              </div>
              <div class="info-row">
                  <div class="key">Ngày cấp</div>
                  <div class="value">
                      <date-picker input-class="form-control" :format="dateFormat" :value-type="dateFormat" v-model="dtpNgayCap.value"></date-picker>
                  </div>
              </div>
              <div class="info-row">
                  <div class="key">Nơi cấp</div>
                  <div class="value">
                      <input type="text"  :value="txtNoiCap.value" @change="v=>txtNoiCap.value=v.target.value" class="form-control" />
                  </div>
              </div>
              <div class="info-row">
                  <div class="key">Email</div>
                  <div class="value">
                      <input type="text"  :value="txtEmail.value" @change="v=>txtEmail.value=v.target.value" class="form-control" />
                  </div>
              </div>
              <div class="info-row">
                  <div class="key">Số điện thoại</div>
                  <div class="value">
                      <input type="text" :value="txtSoDT.value" @change="v=>txtSoDT.value=v.target.value" class="form-control" />
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
import api_installnewsubs from "@/modules/contract/setup/InstallNewSubs/InstallNewSubsAPI.js";
import { Checkbox } from "element-ui";
export default {
  name: "frmHDTB_SUB_V4",
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
      loaitb_id: 0,
      hdtb_id: 0,
      txtNguoiKyHoSo: {value: ""},
      dtpNgaySinh: {value: ""},
      cboGioiTinh: {value: ""},
      txtSoGT: {value: ""},
      dtpNgayCap: {value: ""},
      txtNoiCap: {value: ""},
      txtEmail: {value: ""},
      txtSoDT: {value: ""},
      hdtb_sub: {}
    };
  },
  watch: {    
    "dulieu.vloaitb_id_cbo"(val) {
      if (val) this.loaitb_id = val
    },
    "dulieu.hdtb_id"(val) {
      if (val) this.hdtb_id = val
    }
  },
  computed: {},
  async mounted() {
    await this.frmHDTB_SUB_V4_Load()
  },
  methods: {
    frmHDTB_SUB_V4_Load: async function () {
      if (this.loaitb_id != LoaiHinhTB.Voice_Brandname) {
        //frmHDTB_SUB_V4_Load 
        var dt_load = [];
        if (this.hdtb_id) {
          dt_load = await api_installnewsubs.post_lay_tt_thuebao_bosung(this.axios,{ "vhdtb_id": this.hdtb_id }).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              return (response.data.data);
            } else return [];
          }).catch(()=>{ return []; });
        }
        if (dt_load && dt_load.length > 0) {
          dt_load = this.UpperCasePropertyList(dt_load);
          this.txtNguoiKyHoSo.value = dt_load[0]["NGUOI_KY_HOSO"];
          this.dtpNgaySinh.value = dt_load[0]["NGAYSINH"];
          this.cboGioiTinh.value = dt_load[0]["GIOITINH"];
          this.txtSoGT.value = dt_load[0]["SO_GT"];
          this.dtpNgayCap.value = dt_load[0]["NGAYCAP"];
          this.txtNoiCap.value = dt_load[0]["NOICAP"];
          this.txtEmail.value = dt_load[0]["EMAIL"];
          this.txtSoDT.value = dt_load[0]["SO_DT"];
        } else {
          this.txtNguoiKyHoSo.value = "";
          this.dtpNgaySinh.value = null;
          this.cboGioiTinh.value = "";
          this.txtSoGT.value = "";
          this.dtpNgayCap.value = null;
          this.txtNoiCap.value = "";
          this.txtEmail.value = "";
          this.txtSoDT.value = "";
        }

        if(this.dulieu.hdtb_sub) {
          if(this.dulieu.hdtb_sub["NGUOI_KY_HOSO"]) this.txtNguoiKyHoSo.value = this.dulieu.hdtb_sub["NGUOI_KY_HOSO"];
          if(this.dulieu.hdtb_sub["NGAYSINH"]) this.dtpNgaySinh.value = this.dulieu.hdtb_sub["NGAYSINH"];
          if(this.dulieu.hdtb_sub["GIOITINH"]) this.cboGioiTinh.value = this.dulieu.hdtb_sub["GIOITINH"];
          if(this.dulieu.hdtb_sub["SO_GT"]) this.txtSoGT.value = this.dulieu.hdtb_sub["SO_GT"];
          if(this.dulieu.hdtb_sub["NGAYCAP"]) this.dtpNgayCap.value = this.dulieu.hdtb_sub["NGAYCAP"];
          if(this.dulieu.hdtb_sub["NOICAP"]) this.txtNoiCap.value = this.dulieu.hdtb_sub["NOICAP"];
          if(this.dulieu.hdtb_sub["EMAIL"]) this.txtEmail.value = this.dulieu.hdtb_sub["EMAIL"];
          if(this.dulieu.hdtb_sub["SO_DT"]) this.txtSoDT.value = this.dulieu.hdtb_sub["SO_DT"];
        }
      }   
    },
    async frmHDTB_SUB_V4_tsbtnGhiLai_Click() {
      if (!this.txtNguoiKyHoSo.value || this.txtNguoiKyHoSo.value.trim() == "") {
        this.$toast.error("Bạn chưa nhập đủ thông tin");
        return;
      }
      let row = {};

      row.NGUOI_KY_HOSO = this.txtNguoiKyHoSo.value;
      row.NGAYSINH = this.dtpNgaySinh.value;
      row.GIOITINH = this.cboGioiTinh.value;
      row.SO_GT = this.txtSoGT.value;
      row.NGAYCAP = this.dtpNgayCap.value;
      row.NOICAP = this.txtNoiCap.value;
      row.EMAIL = this.txtEmail.value;
      row.SO_DT = this.txtSoDT.value;
      row.HDTB_ID = this.hdtb_id;
      // row.MST_KHTN = await API.get_mst_khtn(this.axios,{params:{"hdtb_id":this.hdtb_id}}).then((response) => {
      //     if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
      //       return (response.data.data);
      //     } else return null;
      //   }).catch(()=>{ return null; });
      // if(!row.MST_KHTN) row.MST_KHTN = this.thuebao.txtMSTThueBao;
      this.hdtb_sub = row;
      if (this.hdtb_sub) {
        this.ShowSuccess("Cập nhật thành công thông tin bổ sung");
        this.closeForm();
      }      
    },
    btnHuy: function () {},
    closeForm() {
      this.$emit("form-close", this.hdtb_sub);
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
    ShowSuccess: function (msg) {
      this.$toast.success(msg);
    },
    ShowError: function (msg) {
      this.$toast.error(msg);
    },
  },
};
</script>