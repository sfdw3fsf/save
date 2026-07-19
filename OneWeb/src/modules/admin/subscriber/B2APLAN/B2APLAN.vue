<template src="./B2APLAN.html"></template>
<style src="./B2APLAN.scss" scoped></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import api from "./API.js";
import DataGrid from "@/components/Controls/DataGrid";
import moment from "moment";
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import { DatabaseConstants } from "@/modules/contract/setup/InstallNewSubs/Enums.js";
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
import BssErrorMarker from "@/components/BssErrorMarker";
import BssRequiredMarker from "@/components/BssRequiredMarker";
import { required, minValue, maxValue } from "vuelidate/lib/validators";
import { CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns"
import { DataManager,Query,ODataV4Adaptor,Predicate } from "@syncfusion/ej2-data"
import { L10n } from '@syncfusion/ej2-base'
import popupCauHinhCanhBao from './popups/popupCauHinhCanhBao'
import popupCauHinhHanMuc from './popups/popupCauHinhHanMuc'
L10n.load({
  'vi-VN': {
    'dropdowns': {
      'noRecordsTemplate': "Không có dữ liệu",
      'actionFailureTemplate': ""
    }
  }
})

export default {
  components: {
    breadcrumb,
    DataGrid,
    DatePicker,
    BssErrorMarker,
    BssRequiredMarker,
    popupCauHinhCanhBao,
    popupCauHinhHanMuc
  },
  name: "B2APLAN",
  validations: {
  },
  provide: {
    multiselect: [CheckBoxSelection]
  },
  data() {
    return {
      button: {
        tsbtnLamMoi: { Enabled: true, Visible: true },
        tsbtnNhapMoi: { Enabled: true, Visible: true },
        tsbtnGhiLai: { Enabled: true, Visible: true },
        tsbtnHuyBo: { Enabled: true, Visible: true },
        tsbtnXoa: { Enabled: true, Visible: true },
        tsbtnCanhBao: { Enabled: true, Visible: true },
        tsbtnHanMuc: { Enabled: true, Visible: true },
      },
      header: {
        title: "Chiến dịch B2A",
        list: [
          // { name: "Dữ liệu", link: { name: "Ui.cards" } },
          // { 
          //   name: "Quản lý Chiến dịch B2A", 
          //   link: { name: "Ui.cards" } 
          // },
          {
            name: "Chiến dịch B2A",
            link: { name: "Ui.cards" },
            active: true,
          },
        ],
      },
      dateFormat: "DD/MM/YYYY",
      position: { X: "center", Y: "center" },
      currentTab: 1,
      tt_nd: {
        phanvung_id: 0, //this.$root.token.getPhanVungID(),
        donvi_id: 0, //this.$root.token.getDonviID(),
        nhanvien_id: 0, //this.$root.token.getNhanVienID(),
        ip_cn: "",
        may_cn: "",
        nguoi_cn: "",
      },
      selectedRow: [],
      cd_id: 0,
      txtMaCD: {value: "", Enabled: false},
      txtTenCD: {value: "", Enabled: true},
      dtpTuNgay: {value: moment(new Date()).format("DD/MM/YYYY"), Enabled: true},
      dtpDenNgay: {value: moment(new Date()).format("DD/MM/YYYY"), Enabled: true},
      cboKenhTC: {value: 0, Items: [], Enabled: true},
      cboLoaiCD: {value: 1, Enabled: true, Items: [
        {
          id: 1,
          text:"CSKH",
        },
        {          
          id: 2,
          text: "Bán hàng",
        },
        {
          id: 3,
          text: "CSKH & Bán hàng"
        }
      ]},
      cboDoUuTien: {value: 1, Enabled: true, Items: [
        {
          id: 0,
          text: "0",
        },
        {
          id: 1,
          text: "1",
        },
        {          
          id: 2,
          text: "2",
        },
        {
          id: 3,
          text: "3"
        },
        {
          id: 4,
          text: "4"
        },
        {
          id: 5,
          text: "5"
        }
      ]},
      cboTinhTrang: {value: 0, Items: [], Enabled: true},
      txtNoiDung: {value: "", Enabled: true},
      txtGanNhan: {value: "", Enabled: true},
      txtLinkCV: {value: "", Enabled: true},
      txtMaSRV: {value: "", Enabled: true},
      txtSoEO: {value: "", Enabled: true},
      txtGhiChu: {value: "", Enabled: true, ReadOnly: false},
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
          {
            fieldName: "action",
            headerText: "Xóa",
            textAlign: "center",
            width: 70,
            template: () => {
              return {
                template: {
                  template: `<div>
                      <button type="button" class="btn btn-primary" @click="onbtnXoaTinh_Click">
                        <span class="one-trash inline vcenter"></span>
                      </button>
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  methods: {
                    onbtnXoaTinh_Click() {
                      this.$parent.$parent.$parent.btnXoaTinh_Click(this.data);
                    },
                  },
                },
              };
            },
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
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
            headerText: "Tên loại hình TB",
            allowFiltering: true,
          },
          {
            fieldName: "action",
            headerText: "Xóa",
            textAlign: "center",
            width: 70,
            template: () => {
              return {
                template: {
                  template: `<div>
                      <button type="button" class="btn btn-primary" @click="onbtnXoaLoaiTB_Click">
                        <span class="one-trash inline vcenter"></span>
                      </button>
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  methods: {
                    onbtnXoaLoaiTB_Click() {
                      this.$parent.$parent.$parent.btnXoaLoaiTB_Click(this.data);
                    },
                  },
                },
              };
            },
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      grcThanhVien: {
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
            headerText: "Thành viên kênh",
            allowFiltering: true,
          },
          {
            fieldName: "action",
            headerText: "Xóa",
            textAlign: "center",
            width: 70,
            template: () => {
              return {
                template: {
                  template: `<div>
                      <button type="button" class="btn btn-primary" @click="onbtnXoaThanhVien_Click">
                        <span class="one-trash inline vcenter"></span>
                      </button>
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  methods: {
                    onbtnXoaThanhVien_Click() {
                      this.$parent.$parent.$parent.btnXoaThanhVien_Click(this.data);
                    },
                  },
                },
              };
            },
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      grcDanhSach: {
        list: [],
        header: [
          {
            fieldName: "STT",
            headerText: "STT",
            allowFiltering: false,
          },
          {
            fieldName: "MA_CD",
            headerText: "Mã CD",
            allowFiltering: true,
          },
          {
            fieldName: "TEN_CD",
            headerText: "Tên chiến dịch",
            allowFiltering: true,
          },
          {
            fieldName: "TU_NGAY",
            headerText: "Từ ngày",
            allowFiltering: true,
          },
          {
            fieldName: "DEN_NGAY",
            headerText: "Đến ngày",
            allowFiltering: true,
          },
          {
            fieldName: "MUCDO_UT",
            headerText: "Mức ưu tiên",
            allowFiltering: true,
          },
          {
            fieldName: "TEN_LOAI",
            headerText: "Loại",
            allowFiltering: true,
          },
          {
            fieldName: "KENH_TC",
            headerText: "Kênh tiếp cận",
            allowFiltering: true,
          },
          {
            fieldName: "TINHTRANG",
            headerText: "Tình trạng",
            allowFiltering: true,
          },
          {
            fieldName: "MA_SRV",
            headerText: "Mã SRV",
            allowFiltering: true,
          },
          {
            fieldName: "SO_EOFFICE",
            headerText: "Số Eoffice",
            allowFiltering: true,
          },
          {
            fieldName: "LINK_CV",
            headerText: "Link công văn",
            allowFiltering: true,
          },
          {
            fieldName: "TEN_DV",
            headerText: "Đơn vị tạo",
            allowFiltering: true,
          },
          {
            fieldName: "NGUOI_TAO",
            headerText: "Người tạo",
            allowFiltering: true,
          },
          {
            fieldName: "NGAY_TAO",
            headerText: "Ngày tạo",
            allowFiltering: true,
          },
          {
            fieldName: "NGUOI_CN",
            headerText: "Người CN",
            allowFiltering: true,
          },
          {
            fieldName: "NGAY_CN",
            headerText: "Người CN",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
        "form-close": this.popupClosed,
      },
      popupComponentName: "",
      popupComponentHeader: "",
    };
  },
  created() {
    this.$root.$on("bv::modal::shown", (bvEvent, modalId) => {
      document.getElementsByClassName("modal-content").forEach(function (v) {
        v.removeAttribute("tabindex");
      });
    });
  },
  methods: {
    layDSKenhTiepNhanB2A: async function (kieu) {
      try {
        let response = await api.SP_LAY_DS_KENH_TIEPNHAN_B2A(this.axios);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          return response.data.data;
        } else if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000204"
        ) {
          return []
        } else {
          return []
        }
      } catch (ex) {
        if (ex.data && ex.data.message_detail)
          this.$toast.error("Có lỗi: " + ex.data.message_detail);
        else this.$toast.error("Có lỗi: " + ex);
        return []
      } 
    },
    layDSTinhTrangB2A: async function (kieu) {
      try {
        let response = await api.SP_LAY_DS_TINHTRANG_B2A(this.axios);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          return response.data.data;
        } else if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000204"
        ) {
          return []
        } else {
          return []
        }
      } catch (ex) {
        if (ex.data && ex.data.message_detail)
          this.$toast.error("Có lỗi: " + ex.data.message_detail);
        else this.$toast.error("Có lỗi: " + ex);
        return []
      } 
    },
    Save: async function () {
      try {
        var chiendich = {
          "CD_ID": this.cd_id,
          "MA_CD": this.txtMaCD.value,
          "TEN_CD": this.txtTenCD.value,
          "TU_NGAY": this.dtpTuNgay.value,
          "DEN_NGAY": this.dtpDenNgay.value,
          "MUCDO_UT": this.cboDoUuTien.value,
          "LOAI": this.cboLoaiCD.value,
          "TINHTRANG_ID": this.cboTinhTrang.value,
          "KENHTC_ID": this.cboKenhTC.value,
          "NOIDUNG_CD": this.txtNoiDung.value,
          "NHAN": this.txtGanNhan.value,
          "MA_SRV": this.txtMaSRV.value,
          "SO_EOFFICE": this.txtSoEO.value,
          "LINK_CV": this.txtLinkCV.value,
          "GHICHU": this.txtGhiChu.value,
          "JS_DS_TINH": this.grcTinh.list && this.grcTinh.list.length > 0 ? this.grcTinh.list : "",
          "JS_DS_LHTB": this.grcLoaiTB.list && this.grcLoaiTB.list.length > 0 ? this.grcLoaiTB.list : "",
          "JS_DS_KENH": this.grcThanhVien.list && this.grcThanhVien.list.length > 0 ? this.grcThanhVien.list : "",
        }
        var input = {
          json_chiendich: JSON.stringify(chiendich),
          nhanvien_id: this.tt_nd.nhanvien_id
        }
        let response = await api.SP_CAPNHAT_CHIENDICH_B2A(this.axios, input);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          var data = JSON.parse(response.data.data);
          if (data.ERROR_CODE == 1) {
            this.ShowSuccess("Cập nhật thành công!");
            await this.SetButton(3);
            await this.HT_DS_CHIENDICH();
          } else {
            this.ShowError(data.MESSAGE)
          }          
        } else {
          this.ShowError("Lỗi ghi lại: " + response.data.message_detail)
        }
      } catch (ex) {
        if (ex.data && ex.data.message_detail)
          this.$toast.error("Có lỗi: " + ex.data.message_detail);
        else this.$toast.error("Có lỗi: " + ex);
      }
    },
    Xoa: async function () {
      try {
        if (!this.cd_id || this.cd_id.toString() == "") {
          this.ShowError("Chưa có thông tin CD_ID!")
          return ;
        }
        var input = {
          cd_id: this.cd_id,
          nhanvien_id: this.tt_nd.nhanvien_id
        }
        let response = await api.SP_XOA_CHIENDICH_B2A(this.axios, input);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          if (response.data.data && response.data.data.includes("ERROR")) {
            var result = JSON.parse(response.data.data)
            if (result && Number(result['ERROR_CODE']) != 1) {
              this.$toast.error(result['MESSAGE'])
              return
            }
          }
          this.ShowSuccess("Xóa chiến dịch thành công!");
          await this.HT_DS_CHIENDICH();
        } else {
          this.ShowError("Lỗi xóa chiến dịch: " + response.data.message_detail)
        }
      } catch (ex) {
        if (ex.data && ex.data.message_detail)
          this.$toast.error("Có lỗi: " + ex.data.message_detail);
        else this.$toast.error("Có lỗi: " + ex);
      }
    },
    ValidateData(isNew) {
      if (!isNew) {
        if (!this.txtMaCD.value || this.txtMaCD.value.toString().trim() == "") {
          this.ShowError("Chưa nhập Mã chiến dịch!");
          this.$refs.txtMaCD.focus();
          return false;
        }
        if (this.txtMaCD.value.length > 50) {
          this.ShowError("Mã chiến dịch quá dài!");
          this.$refs.txtMaCD.focus();
          return false;
        }
      }

      if (!this.txtTenCD.value || this.txtTenCD.value.toString().trim() == "") {
        this.ShowError("Chưa nhập tên chiến dịch!");
        this.$refs.txtTenCD.focus();
        return false;
      }

      if (this.txtTenCD.value.length > 199) {
        this.ShowError("Tên chiến dịch quá dài!");
        this.$refs.txtTenCD.focus();
        return false;
      }

      if (!this.dtpTuNgay.value || this.dtpTuNgay.value.toString().trim() == "") {
        this.ShowError("Chưa chọn thời điểm bắt đầu!");
        return false;
      }

      if (!this.dtpDenNgay.value || this.dtpDenNgay.value.toString().trim() == "") {
        this.ShowError("Chưa chọn thời điểm kết thúc!");
        return false;
      }

      if (moment(this.dtpTuNgay.value, "DD/MM/YYYY") > moment(this.dtpDenNgay.value, "DD/MM/YYYY")) {
        this.ShowError("Khoảng thời gian chiến dịch không đúng!");
        return false;
      }

      if (!this.cboKenhTC.value || this.cboKenhTC.value.toString().trim() == "") {
        this.ShowError("Chưa chọn kênh tiếp cận!");
        return false;
      }

      if (!this.cboLoaiCD.value || this.cboLoaiCD.value.toString().trim() == "") {
        this.ShowError("Chưa chọn loại chiến dịch!");
        return false;
      }

      if (!this.cboDoUuTien.value || this.cboDoUuTien.value.toString().trim() == "") {
        this.ShowError("Chưa chọn độ ưu tiên!");
        return false;
      }

      if (!this.cboTinhTrang.value || this.cboTinhTrang.value.toString().trim() == "") {
        this.ShowError("Chưa chọn trạng thái!");
        return false;
      }

      if (this.txtNoiDung.value.toString().trim() == "") {
        this.ShowError("Chưa nhập nội dung chiến dịch!");
        this.$refs.txtNoiDung.focus();
        return false;
      }

      // if (this.txtMaSRV.value.toString().trim() == "") {
      //   this.ShowError("Chưa nhập mã SRV!");
      //   this.$refs.txtMaSRV.focus();
      //   return false;
      // }

      if (!this.grcTinh.list || this.grcTinh.list.length == 0) {
        this.ShowError("Chưa chọn Đơn vị áp dụng!");
        return false;
      }

      if (!this.grcLoaiTB.list || this.grcLoaiTB.list.length == 0) {
        this.ShowError("Chưa chọn Loại hình thuê bao!");
        return false;
      }

      if (!this.grcThanhVien.list || this.grcThanhVien.list.length == 0) {
        this.ShowError("Chưa chọn Thành viên kênh!");
        return false;
      }

      return true;
    },
    tsbtnLamMoi_Click: function () {
      try {
        this.loading(true);
        this.SetButton(2);
        this.HT_DS_CHIENDICH();
        this.loading(false);
      } catch (e) {
        this.loading(false);
      }
    },
    tsbtnNhapMoi_Click: function () {
      this.loading(true);
      this.cd_id = 0;
      this.SetButton(1);
      this.loading(false);
    },
    tsbtnGhiLai_Click: async function () {
      try {
        this.loading(true);
        var isNew = !this.cd_id ? true : false;
        if (!this.ValidateData(isNew)) {
          this.loading(false);
          return;
        }

        await this.Save();       

        this.loading(false);
      } catch (e) {
        this.$toast.error("" + e);
        this.loading(false);
      }
    },
    tsbtnHuyBo_Click: function () {
      this.loading(true);
      this.SetButton(2);
      this.loading(false);
    },
    tsbtnXoa_Click: function () {
      try {
        this.$bvModal.msgBoxConfirm("Xóa chiến dịch?", {
          title: "Thông báo",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (v) {
            this.loading(true);
            await this.Xoa();
            if (!this.grcDanhSach.list || this.grcDanhSach.list.length == 0) {
              this.SetButton(1);
            }
            this.loading(false);
          }
        })
        .catch((e) => {
          this.ShowError("Lỗi xóa chiến dịch");
          this.loading(false);
        });
      } catch (ex) {
        if (ex.data && ex.data.message_detail)
          this.$toast.error("Có lỗi: " + ex.data.message_detail);
        else this.$toast.error("Có lỗi: " + ex);
        this.loading(false);
      }
    },
    btnThemTinh_click: function () {
      this.popupChonDonVi();
    },
    btnXoaTinh_Click: function (item) {
      var tmp = this.grcTinh.list.filter(e => e.ID != item.ID)
      this.grcTinh.list = tmp
    },
    btnReloadTinh_Click: function () {
      this.grcTinh.list = this.selectedRow.JS_TINH && this.selectedRow.JS_TINH.length > 0 ? this.UpperCasePropertyList(JSON.parse(this.selectedRow.JS_TINH)) : [];
    },
    btnThemLoaiTB_Click: function () {
      this.popupChonLoaiTB()
    },
    btnXoaLoaiTB_Click: function (item) {
      var tmp = this.grcLoaiTB.list.filter(e => e.ID != item.ID)
      this.grcLoaiTB.list = tmp
    },
    btnReloadLoaiTB_Click: function () {
      this.grcLoaiTB.list = this.selectedRow.JS_LHTB && this.selectedRow.JS_LHTB.length > 0 ? this.UpperCasePropertyList(JSON.parse(this.selectedRow.JS_LHTB)) : [];
    },
    btnThemThanhVien_Click: function () {
      this.popupChonThanhVien()
    },
    btnXoaThanhVien_Click: function (item) {
      var tmp = this.grcThanhVien.list.filter(e => e.ID != item.ID)
      this.grcThanhVien.list = tmp
    },
    btnReloadThanhVien_Click: function () {
      this.grcThanhVien.list = this.selectedRow.JS_KENH && this.selectedRow.JS_KENH.length > 0 ? this.UpperCasePropertyList(JSON.parse(this.selectedRow.JS_KENH)) : [];
    },
    btnChCanhBao_Click: function () {
      this.$refs.popupCB.showModal(this.cd_id);
    },
    btnChHanMuc_Click: function () {
      this.$refs.popupHM.showModal(this.cd_id);
    },
    changeTab(tab) {
      if (this.currentTab == tab) {
        return;
      }
      this.currentTab = tab;
    },
    changeTuNgay: function (value_) {
      if (!moment(value_, "DD/MM/YYYY").isValid()) {
        this.dtpTuNgay.value = "01/01/2000";
      } else {
        this.dtpTuNgay.value = value_;
      }
    },
    changeDenNgay: function (value_) {
      if (!moment(value_, "DD/MM/YYYY").isValid()) {
        this.dtpDenNgay.value = "01/01/2000";
      } else {
        this.dtpDenNgay.value = value_;
      }
    },
    popupChonDonVi() {
      this.popupComponent = () =>
        import("./popups/popupChonDonVi");
      this.popupComponentName = "popupChonDonVi";
      this.popupComponentHeader = "Chọn đơn vị";
      this.popupComponentData = { ds: this.grcTinh.list };
      this.Popup("popupComponents");
    },
    popupChonLoaiTB() {
      this.popupComponent = () =>
        import("./popups/popupChonLoaiTB");
      this.popupComponentName = "popupChonLoaiTB";
      this.popupComponentHeader = "Chọn loại hình thuê bao";
      this.popupComponentData = { ds: this.grcLoaiTB.list };
      this.Popup("popupComponents");
    },
    popupChonThanhVien() {
      this.popupComponent = () =>
        import("./popups/popupChonThanhVien");
      this.popupComponentName = "popupChonThanhVien";
      this.popupComponentHeader = "Chọn loại nhân viên";
      this.popupComponentData = { ds: this.grcThanhVien.list };
      this.Popup("popupComponents");
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
      this.popupComponent = null;
    },
    async popupClosed(val) {
      if (!val) {
        this.ClosePopup("popupComponents");
        return;
      }
      this.loading(true);
      try {
        switch (this.popupComponentName) {
          case "popupChonDonVi":
            this.grcTinh.list = val
            break;    
          case "popupChonLoaiTB":
            this.grcLoaiTB.list = val
            break;   
          case "popupChonThanhVien":
            this.grcThanhVien.list = val
            break;          
        }
      } catch (ex) {
        if (ex.data && ex.data.message_detail) {
          this.$toast.error("" + ex.data.message_detail);
        } else {
          this.$toast.error("" + ex);
        }
        
        if (this.debug) console.error(ex);
      } finally {
        this.ClosePopup("popupComponents");
        this.loading(false);
      }
    },
    GET_KEYS: async function (key) {
      var input = {
        keyname: key,
      };
      let response = await api.getKey(this.axios, input);
      if (
        response &&
        response.data &&
        response.data.error_code &&
        response.data.error_code == "BSS-00000000" &&
        response.data.data
      ) {
        return Number(response.data.data);
      } else {
        this.$toast.error("Có lỗi trong quá trình tạo key mới");
        return;
      }
    },
    ChuanHoaTen(ten) {
      if (!ten) return "";
      var ten_new = "";
      var str_con = ten.trim();
      var str_xl = "";
      var k = 0;
      var found = true;
      if (str_con.length < 1) return;
      str_con = str_con.replaceAll(/\s+/g, " ");
      try {
        while (found) {
          k = str_con.indexOf(" ");
          if (k > 0) {
            str_xl = str_con.substring(0, k);
            str_con = str_con.substring(k + 1);
            str_xl = this.ChuyenChuHoa(str_xl);
            if (ten_new != "") ten_new += " " + str_xl;
            else ten_new = str_xl;
            found = true;
          } else found = false;
        }
        if (str_con != "") {
          str_xl = str_con;
          str_xl = this.ChuyenChuHoa(str_xl);
          if (ten_new != "") ten_new = ten_new + " " + str_xl;
          else ten_new = str_xl;
        }
        return ten_new;
      } catch (ex) {
        return ten;
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
    numberWithCommas(n) {
      var parts = n.toString().split(".");
      return (
        parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +
        (parts[1] ? "." + parts[1] : "")
      );
    },
    GetData: function (response) {
      if (response.data && response.data.error_code == "BSS-00000000") {
        return response.data.data;
      } else {
        // console.log(response.data.message);
        // this.ShowError(response.data.message);
      }
      return [];
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg);
    },
    ShowError: function (msg) {
      this.$toast.error(msg);
    },
    SetButton(kieu)
    {
      this.button.tsbtnNhapMoi.Enabled = false;
      this.button.tsbtnGhiLai.Enabled = false;
      if (kieu == -1)//Bat dau
      {
        this.button.tsbtnGhiLai.Enabled = true;
      }
      if (kieu == 0)//Bat dau
      {
        this.$refs.txtMaCD.focus();
        this.button.tsbtnNhapMoi.Enabled = true;
        this.ClearUI();
      }
      if (kieu == 1)//Them moi
      {
        this.$refs.txtMaCD.focus();
        this.button.tsbtnGhiLai.Enabled = true;
        this.ClearUI();
      }
      if (kieu == 2)//Huy
      {
        this.button.tsbtnGhiLai.Enabled = true;
        this.button.tsbtnNhapMoi.Enabled = true;
        this.ClearUI();
      }
      if (kieu == 3)//Edit
      {
        this.button.tsbtnNhapMoi.Enabled = true;
        this.button.tsbtnGhiLai.Enabled = true;
      }
    },
    ClearUI: function () {
      this.txtMaCD.value = "";
      this.txtTenCD.value = "";
      this.dtpTuNgay.value = moment(new Date()).format("DD/MM/YYYY");
      this.dtpDenNgay.value = moment(new Date()).format("DD/MM/YYYY");
      this.cboKenhTC.value = 0;
      this.cboLoaiCD.value = 0;
      this.cboDoUuTien.value = 0;
      this.cboTinhTrang.value = 0;
      this.txtNoiDung.value = "";
      this.txtGanNhan.value = "";
      this.txtLinkCV.value = "";
      this.txtMaSRV.value = "";
      this.txtSoEO.value = "";
      this.txtGhiChu.value = "";
      this.grcTinh.list = [];
      this.grcLoaiTB.list = [];
      this.grcThanhVien.list = [];
    },
    LOAD_DATASOURCE_CBO: async function () {
      try {
        this.cboKenhTC.Items = await this.layDSKenhTiepNhanB2A();
        this.cboTinhTrang.Items = await this.layDSTinhTrangB2A();

        // try {
        // this.cboKenhTC.Items = await this.layDSKenhTiepNhanB2A();
        // var data = await this.layDSTinhTrangB2A();
        // this.cboTinhTrang.Items =  data.map(obj => {
        // if (obj.tinhtrang_id === 2) {
        //   return { ...obj, tinhtrang: "Đang triển khai" };
        // }
        // return obj;
        // });
      } catch (e) {}
    },
    HT_DS_CHIENDICH: async function () {
      try {
        this.cd_id = 0;
        this.selectedRow = {},
        this.grcDanhSach.list = [];
        var input = {
          phanvung_id: this.tt_nd.phanvung_id,
          nhanvien_id: this.tt_nd.nhanvien_id
        }
        let response = await api.SP_LAY_DS_CHIENDICH_B2A(this.axios, input);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          this.grcDanhSach.list = this.UpperCasePropertyList(response.data.data);
        } else if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000204"
        ) {
          this.grcDanhSach.list = []
        } else {
          this.grcDanhSach.list = []
        }
      } catch (ex) {
        if (ex.data && ex.data.message_detail)
          this.$toast.error("Có lỗi: " + ex.data.message_detail);
        else this.$toast.error("Có lỗi: " + ex);
        this.grcDanhSach.list = []
      } 
    },
    grcDanhSach_SelectionChanged: function (item) {
      if (item && item.length > 0) {
        this.selectedRow = this.UpperCasePropertyList(item)[0];
        this.cd_id = item[0]["CD_ID"];
        this.txtMaCD.value = item[0]["MA_CD"];
        this.txtTenCD.value = item[0]["TEN_CD"];

        this.dtpTuNgay.value = moment(item[0]["TU_NGAY"]).format("DD/MM/YYYY");
        this.dtpDenNgay.value = moment(item[0]["DEN_NGAY"]).format("DD/MM/YYYY");
        this.cboKenhTC.value = item[0]["KENHTC_ID"];
        this.cboLoaiCD.value = item[0]["LOAI"];
        this.cboDoUuTien.value = item[0]["MUCDO_UT"];
        this.cboTinhTrang.value = item[0]["TINHTRANG_ID"];

        this.txtNoiDung.value = item[0]["NOIDUNG_CD"];
        this.txtGanNhan.value = item[0]["NHAN"];
        this.txtLinkCV.value = item[0]["LINK_CV"];
        this.txtSoEO.value = item[0]["SO_EOFFICE"];
        this.txtMaSRV.value = item[0]["MA_SRV"];
        this.txtGhiChu.value = item[0]["GHICHU"];

        this.grcTinh.list = item[0].JS_TINH && item[0].JS_TINH.length > 0 ? this.UpperCasePropertyList(JSON.parse(item[0].JS_TINH)) : [];
        this.grcLoaiTB.list = item[0].JS_LHTB && item[0].JS_LHTB.length > 0 ? this.UpperCasePropertyList(JSON.parse(item[0].JS_LHTB)) : [];
        this.grcThanhVien.list = item[0].JS_KENH && item[0].JS_KENH.length > 0 ? this.UpperCasePropertyList(JSON.parse(item[0].JS_KENH)) : [];
        this.SetButton(3);
      }
    },
    frmChienDichB2A_FormLoad: async function () {
      this.tt_nd = {
        phanvung_id: Number(await this.$root.token.getPhanVungID()),
        donvi_id: Number(await this.$root.token.getDonViID()),
        nhanvien_id: Number(await this.$root.token.getNhanVienID()),
        ip_cn: await this.$root.token.getIP(),
        may_cn: await this.$root.token.getMachine(),
        nguoi_cn: await this.$root.token.getUserName(),
      };
      await this.LOAD_DATASOURCE_CBO();
      await this.HT_DS_CHIENDICH();

      if (!this.grcDanhSach.list || this.grcDanhSach.list.length == 0) {
        this.SetButton(1);
      }
    },
    async popupClosedlg() {
        this.ClosePopup("popupComponentslg");
    },
  },
  async mounted() {
    this.loading(true);
    try {
      await Promise.all([this.frmChienDichB2A_FormLoad()]);

      this.loading(false);
    } catch (ex) {
      this.loading(false);
      console.log(ex);
    }
  },
  computed: {
   
  },
  watch: {
    "grcDanhSach.list"(val) {
      if (val && val.length > 0) {
        this.SetButton(3);
      } else {
        this.SetButton(1);
      }
    }
  },
};
</script>

<style>
.disabled {
  cursor: not-allowed;
  color: #666 !important;
}
.mx-datepicker {
  width: 100% !important;
}

.mx-input.disabled,
.mx-input:disabled {
  color: #666 !important;
}

.hoso-dientu .page-content {
  position: unset;
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
