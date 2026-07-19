<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title"><span class="icon one-notepad"></span> Thông tin thanh toán</div>
      <div class="close -ap icon-close" data-dismiss="modal" @click="closeForm">
      </div>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="javascript: void(0)" @click.prevent="btnCapNhat_click">
            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Cập nhật
          </a>
        </li>
        <li>
          <a href="javascript: void(0)" @click.prevent="closeForm">
            <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w110">Mã TT</div>
              <div class="value">
                <input type="text" :value="txt_MaTT.value" @change="v => txt_MaTT.value = v.target.value"
                  class="form-control" />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w110">Tên TT</div>
              <div class="value">
                <input type="text" :value="txt_TenTT.value" @change="v => txt_TenTT.value = v.target.value"
                  class="form-control" />
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="info-row">
              <div class="key w110">Địa chỉ TT</div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn" v-b-modal.popupdiachiTT title="Chọn địa chỉ TT" :disabled="!txt_DiaChi.Enabled">
                    <span class="-ap icon-more_horiz"></span>
                  </button>

                  <ModalChonDiaChi modalId="popupdiachiTT" :data="diachiTT" :isStrictMode="true" @xacnhan="getdiachiTT" />
                  <bss-error-marker :has-error="$v.diachiTT.DIACHI.$error">
                    <input type="text" v-model="$v.diachiTT.DIACHI.$model" class="form-control" required
                      :title="$v.diachiTT.DIACHI.$model" :disabled="!txt_DiaChi.Enabled">
                  </bss-error-marker>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w110">Số TK</div>
              <div class="value">
                <input type="text" :value="txt_SoTK.value" @change="v => txt_SoTK.value = v.target.value"
                  class="form-control" />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w110">Chủ tài khoản</div>
              <div class="value">
                <input type="text" :value="txt_ChuTK.value" @change="v => txt_ChuTK.value = v.target.value"
                  class="form-control" />
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="row custom-row">
              <div class="w110 pad2">Ngân hàng</div>
              <select2 :z-index="2000" class="select2 pad2" style="width: calc(100% - 110px) !important; padding-left: .8rem !important;" v-model="cbb_NganHang.value"
                :options="cbb_NganHang.Items.length > 0 ? cbb_NganHang.Items.map(e => ({ id: e.nganhang_id, text: e.ten_nh })) : []"
                :disabled="!cbb_NganHang.Enabled">
              </select2>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="row custom-row">
              <div class="w110 pad2">Hình thức TT</div>
              <select2 :z-index="2000" class="select2 pad2" style="width: calc(100% - 110px) !important; padding-left: .8rem !important;" v-model="cbb_HTTT.value"
                :options="cbb_HTTT.Items.length > 0 ? cbb_HTTT.Items.map(e => ({ id: e.httt_id, text: e.hinhthuc })) : []"
                :disabled="!cbb_HTTT.Enabled">
              </select2>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="row custom-row">
              <div class="w110 pad2">Đơn vị QL</div>
              <select2 :z-index="2000" class="select2 pad2" style="width: calc(100% - 110px) !important; padding-left: .8rem !important;" v-model="cbb_DVQL.value"
                :options="cbb_DVQL.Items.length > 0 ? cbb_DVQL.Items.map(e => ({ id: e.donvi_id, text: e.ten_dv })) : []"
                :disabled="!cbb_DVQL.Enabled" @change="onChangeDVQLTT($event)">
              </select2>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w110">Nhân viên CT</div>
              <div class="value">
                <select2 :z-index="2000" class="select2" v-model="cbb_NVCT.value"
                  :options="cbb_NVCT.Items.length > 0 ? cbb_NVCT.Items.map(e => ({ id: e.nhanvien_id, text: e.ten_nv })) : []"
                  :disabled="!cbb_NVCT.Enabled">
                </select2>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w110">Mã số thuế</div>
              <div class="value">
                <input type="text" :value="txt_MST.value" @change="v => txt_MST.value = v.target.value"
                  class="form-control" />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w110">Ví VNPT Pay</div>
              <div class="value">
                <input type="text" class="form-control" :value="txt_Vi_VNPT.value"
                  @change="v => txt_Vi_VNPT.value = v.target.value">
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="info-row">
              <div class="key w110">Ghi chú</div>
              <div class="value">
                <input type="text" :value="txt_GhiChu_TT.value" @change="v => txt_GhiChu_TT.value = v.target.value"
                  class="form-control" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<style>
.select2 {
  width: 100% !important;
}

.custom-row {
  display: flex !important;
  flex-direction: row !important;
}

.pad2 {
  padding-left: .5rem !important;
  padding-right: .5rem !important;
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
</style>
<script>
import moment from "moment";
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import api from '../API.js'
import {
  required,
  minLength,
  integer,
  maxValue,
} from "vuelidate/lib/validators";
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
const ModalChonDiaChi = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi')
export default {
  name: "popup_TTThanhToan",
  components: {
    required,
    minLength,
    integer,
    maxValue,
    DatePicker,
    BssErrorMarker,
    BssRequiredMarker,
    ModalChonDiaChi
  },
  props: ["dulieu"],
  data() {
    return {
      dateFormat: "DD/MM/YYYY",
      txt_MaTT: { value: "", Enabled: true },
      txt_TenTT: { value: "", Enabled: true },
      txt_SoTK: { value: "", Enabled: true },
      txt_ChuTK: { value: "", Enabled: true },
      cbb_NganHang: { value: 0, Items: [], Enabled: true },
      cbb_HTTT: { value: 0, Items: [], Enabled: true },
      cbb_DVQL: { value: 0, Items: [], Enabled: true },
      cbb_NVCT: { value: 0, Items: [], Enabled: true },
      txt_MST: { value: "", Enabled: true },
      txt_Vi_VNPT: { value: "", Enabled: true },
      txt_GhiChu_TT: { value: "", Enabled: true },
      diachiTT: {
        DIACHI: null,
        TINH_ID: 0,
        QUAN_ID: 0,
        PHUONG_ID: 0,
        PHO_ID: 0,
        AP_ID: 0,
        KHU_ID: 0,
        DACDIEM_ID: 0,
        SO_NHA: null,
        Lng: 0,
        Lat: 0,
        BLOCK: null,
        TANG: null,
        PHONG: null,
        MOTA: null
      },
      txt_DiaChi: { value: '', Enabled: true },
    };
  },
  validations: {
    diachiTT: {
      DIACHI: {
        required
      }
    },
  },
  watch: {},
  computed: {},
  async mounted() {
    await this.Form_Load()
  },
  methods: {
    Form_Load: async function () {      
      if (this.dulieu) {
        this.txt_MaTT.value = this.dulieu['ma_tt']
        this.txt_TenTT.value = this.dulieu['ten_tt']
        this.txt_SoTK.value = this.dulieu['stk']
        this.txt_ChuTK.value = this.dulieu['chutaikhoan']
        this.cbb_NganHang.value = this.dulieu['nganhang_id']
        this.cbb_HTTT.value = this.dulieu['httt_id']
        this.cbb_DVQL.value = this.dulieu['donvi_qltt']
        this.cbb_NVCT.value = this.dulieu['nv_chitra_id']
        this.txt_MST.value = this.dulieu['mst_tt']
        this.txt_Vi_VNPT.value = this.dulieu['vi_vnpay']
        this.txt_GhiChu_TT.value = this.dulieu['ghichu_tt']
        this.diachiTT.DIACHI = this.dulieu['diachi_tt']
        this.diachiTT.TINH_ID = this.dulieu['tinh_id']
      }
      await Promise.all([
        this.getDanhsach_NganHang(),
        this.getDanhsach_HTTT(),
        this.getDanhsach_DonViQL(),
        this.getDanhsach_NhanVienChiTra()
      ])
    },
    // Lấy danh sách ngân hàng
    getDanhsach_NganHang: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsNganHang(this.axios, input));
      this.cbb_NganHang.Items = data;
    },
    // Lấy danh sách hình thức TT
    getDanhsach_HTTT: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsHinhThucThanhToan(this.axios, input));
      this.cbb_HTTT.Items = data;
    },
    // Lấy danh sách DVQL
    getDanhsach_DonViQL: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsDonViQL(this.axios, input));
      this.cbb_DVQL.Items = data;
    },
    onChangeDVQLTT: function () {
      this.getDanhsach_NhanVienChiTra()
    },
    // Lấy danh sách nhân viên chi trả
    getDanhsach_NhanVienChiTra: async function () {
      let input = { donvi_id: this.cbb_DVQL.value }
      let data = this.GetDataList(await api.dsNhanVienChiTra(this.axios, input));
      this.cbb_NVCT.Items = data;
    },
    getdiachiTT: function (data) {
      this.getDiaChi(data, 'diachiTT')
    },
    getDiaChi: function (data, datatype) {
      if (!this[datatype]) this[datatype] = {}
      this[datatype].DIACHI = data.diachimoi
      this[datatype].TINH_ID = data.tinh_id
      this[datatype].QUAN_ID = data.quan_id
      this[datatype].PHUONG_ID = data.phuong_id
      this[datatype].PHO_ID = data.pho_id
      this[datatype].AP_ID = data.ap_id
      this[datatype].KHU_ID = data.khu_id
      this[datatype].DACDIEM_ID = data.dacdiem_id
      this[datatype].SO_NHA = data.so_nha
      this[datatype].BLOCK = data.block
      this[datatype].TANG = data.tang
      this[datatype].PHONG = data.sophong
      this[datatype].MOTA = data.motathem
      this[datatype].Lat = data.lat
      this[datatype].Lng = data.lng
    },
    async btnCapNhat_click() {
      if (!this.validate()) return
      await this.CapNhat()
    },
    CapNhat: async function () {
      try {
        var input = {
          "hdkenh_id": Number(this.dulieu.hdkenh_id),
          "ma_tt": this.txt_MaTT.value,
          "ten_tt": this.txt_TenTT.value,
          "diachi_tt": this.diachiTT.DIACHI,
          "stk": this.txt_SoTK.value,
          "chutaikhoan": this.txt_ChuTK.value,
          "nganhang_id": Number(this.cbb_NganHang.value),
          "httt_id": Number(this.cbb_HTTT.value),
          "donvi_qltt": Number(this.cbb_DVQL.value),
          "nv_chitra_id": Number(this.cbb_NVCT.value),
          "vi_vnpay": this.txt_Vi_VNPT.value,
          "ghichu": this.txt_GhiChu_TT.value,
        }
        var response = await api.capNhatThongTinTT(this.axios, input)
        if (response && response.data.error_code == 'BSS-00000000') {
          if (response.data.data == 'ok' || response.data.data == 'OK') {
            this.$toast.success('Cập nhật thông tin thanh toán thành công!')
          } else if (response.data.data) {
            this.$toast.error(response.data.data)
          }
        } else {
          this.$toast.error('Lỗi ghi lại: ' + response.data.message_detail ? response.data.message_detail : response.data.message)
        }
      } catch (e) {
        this.$toast.error('Lỗi ghi lại: ' + e)
      }
    },
    validate() {
      if (!this.dulieu.hdkenh_id) {
        this.$toast.error('Không có hợp đồng kênh ID!')
        return false
      }

      if (!this.txt_TenTT.value) {
        this.$toast.error('Bạn chưa nhập Tên thanh toán!')
        return false
      }

      if (!this.txt_SoTK.value) {
        this.$toast.error('Bạn chưa nhập Số tài khoản!')
        return false
      }

      if (!this.cbb_NganHang.value) {
        this.$toast.error('Bạn chưa chọn ngân hàng!')
        return false
      }

      if (!this.txt_ChuTK.value) {
        this.$toast.error('Bạn chưa nhập Chủ tài khoản!')
        return false
      }

      if (!this.cbb_DVQL.value) {
        this.$toast.error('Bạn chưa chọn Đơn vị chi trả!')
        return false
      }

      if (!this.cbb_NVCT.value) {
        this.$toast.error('Bạn chưa chọn Nhân viên chi trả!')
        return false
      }

      if (!this.diachiTT.DIACHI) {
        this.$toast.error('Bạn chưa chọn Địa chỉ thanh toán!')
        return false
      }

      if (!this.cbb_HTTT.value) {
        this.$toast.error('Bạn chưa chọn Hình thức thanh toán!')
        return false
      }

      return true
    },
    btnHuy: function () { },
    closeForm() {
      this.$emit("form-close", null);
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
    GetDataList: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return []
      } else {
        this.$root.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
        return []
      }
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return ''
      } else {
        this.$root.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
        return ''
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