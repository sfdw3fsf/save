<template src='./DangKyNhuCauVT.html'></template>
<style src='./DangKyNhuCauVT.scss'></style>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import API from "../api/DangKyNhuCauVT.js";
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import xlsx from "xlsx";
import CopyDangKy from "./components/CopyDangKy/index.vue";
import ChonDangKy from "./components/ChonDangKy/index.vue";
import XemDangKy from "./components/XemDangKy/index.vue";
import { previewPrint } from "../../../utils/util";
import {
  TreeGridPlugin,
  Page,
  Filter,
  Sort,
  Resize,
  Aggregate,
  Toolbar,
  Freeze,
  Edit,
} from "@syncfusion/ej2-vue-treegrid";
import { DateTimePickerPlugin } from "@syncfusion/ej2-vue-calendars";
import ClickOutside from "vue-click-outside";
import Vue from "vue";
Vue.use(TreeGridPlugin);
Vue.use(DateTimePickerPlugin);
import { L10n } from "@syncfusion/ej2-base";
L10n.load({
  "vi-VN": {
    grid: {
      EmptyRecord: "Không có bản ghi nào để hiển thị",
      Item: "Bản ghi",
      Items: "Bản ghi",
    },
    pager: {
      currentPageInfo: "{0} trên {1} trang",
      totalItemsInfo: "({0} Bản ghi)",
      pagerDropDown: "Bản ghi trên trang",
    },
  },
});
let removeVT = function () {
  return {
    template: {
      template: `
                    <div class="">
                        <button class="btn btn-danger pad3 lh14" @click="xoa(value)">
                              <span class="-ap icon-close"></span>
                          </button>
                    </div> 
                `,
      data() {
        return {
          data: {},
        };
      },
      computed: {
        parent() {
          console.log("$parent = ", this.$parent.$parent);
          return this.$parent.$parent.$parent;
        },
        value() {
          console.log("$value = ", this.data);
          return this.data;
        },
      },
      methods: {
        xoa(data) {          
          this.$parent.$parent.$parent.$parent.$parent.removeVatTu(data);
        },
      },
    },
  };
};

let btnXemDangKy = function () {
  return {
    template: {
      template: `
          <div class="center" v-if="data.CT_VT_DK > 0">
            <button class="btn btn-success pad3 lh14" @click="view(value)">
                <span class="-ap icon-search"></span>
            </button>
          </div>
                `,
      data() {
        return {
          data: {},
        };
      },
      computed: {
        parent() {
          console.log("$parent = ", this.$parent.$parent);
          return this.$parent.$parent.$parent;
        },
        value() {
          console.log("$value = ", this.data);
          return this.data;
        },
      },
      methods: {
        view(data) {
          console.log("Xóa data =", data);
          console.log("$parent = ", this.$parent.$parent);
          this.$parent.$parent.$parent.$parent.$parent.searchDSChonDangKy();
        },
      },
    },
  };
};

export default {
  components: { breadcrumb, CopyDangKy, ChonDangKy, XemDangKy },
  name: "DangKyNhuCauVT",
  mounted() {},
  watch: {
    chuKyDKSelected: {
      handler: async function (data) {
        console.log("chuKyDKSelected", data);
        this.thoiGianDKSelected = "";
        if (data === "") {
          this.thoigianDK_show = true;
          this.dotDK_show = true;
          this.listThoiGianDK = [];
        } else if (data || data == 0) {
          this.thoigianDK_show = false;
          this.dotDK_show = false;
          this.listThoiGianDK = [];
          await this.fetchDSThoiGianDK(data);
          // this.thoiGianDKSelected = moment().format("M");
        } else {
          this.thoigianDK_show = true;
          this.dotDK_show = true;
          this.listThoiGianDK = [];
        }
      },
    },
    radio_DKNCVT: {
      handler: async function (data) {
        try {
          this.loading(true);
          if (data.toString() == "1") {
            this.statusNhapMoi = false;
            this.statusGhiLai = false;
            this.statusHuyBo = false;
            this.statusXoa = false;
            this.statusNopPhieu = false;
            this.statusInPhieu = true;
            this.statusNhapExcel = false;
          } else {
            this.statusNhapMoi = true;
            this.statusGhiLai = true;
            this.statusHuyBo = true;
            this.statusXoa = true;
            this.statusNopPhieu = true;
            this.statusInPhieu = true;
            this.statusNhapExcel = true;
          }
          await this.fetchDSDangKyNhuCauTinhTrang(data);
          if (this.listDangKyNhuCauTinhTrang.length == 0) {
            this.listVatTuDaDK = [];
          }
        } catch (e) {
          this.$toast.error(e.data.message);
        } finally {
          this.loading(false);
        }
      },
    },
    showCbxTimkiem: {
      //cbxVattu_CheckedChanged
      handler: async function (data) {
        try {
          this.loading(true);
          if (["2", "3", "4"].includes(this.tagForm.toString()) && data) {
            this.txt_VatTu = "Vật tư tồn";
            this.list_CbxTimkiem = [];
            this.value_cboTimKiem = "";
            this.cboTimKiem = "";
            await this.fetchDSVatTuTK();
            await this.fetchListVTThuocNhomMDDK();
            this.list_CbxTimkiem = await this.getDSVatTuTimKiem();
            this.listVatTuDK_Ton = this.listVatTuTK;
          } else if (
            ["2", "3", "4"].includes(this.tagForm.toString()) &&
            data == false
          ) {
            this.txt_VatTu = "Vật tư";
            this.listVatTuDK_Ton = [];
            this.value_cboTimKiem = "";
            this.cboTimKiem = "";
            await this.fetchVattu_MDDK(-1);
            this.listVatTuDK_Ton = this.list_CbxTimkiem;
          } else {
            this.listVatTuDK_Ton = [];
            this.value_cboTimKiem = "";
            this.cboTimKiem = "";
            await this.fetchVattu_MDDK(-1);
            this.listVatTuDK_Ton = this.list_CbxTimkiem;
            this.listVatTuTK = this.list_CbxTimkiem;
          }
        } catch (e) {
          this.$toast.error(e.data.message);
        } finally {
          this.loading(false);
        }
      },
    },
    listVatTuDK_Ton: function (value) {
      console.log("Again listVatTuDK_Ton = ", value);
    },
    value_cboTimKiem: {
      // cboTimKiem_EditValueChanged
      handler: function (VatTuId) {
        if (VatTuId != null && VatTuId != "" && !this.showCbxTimkiem) {
          this.checkVatTuCha(false);
        } else if (VatTuId != null && VatTuId != "" && this.showCbxTimkiem) {
          let dataVaTu = this.listVatTuTK.filter((a) => a.VATTU_ID == VatTuId);
          if (
            dataVaTu.length &&
            (dataVaTu[0].VATTU_CHA_ID == null || dataVaTu[0].VATTU_CHA_ID == "")
          ) {
            this.listVatTuDK_Ton = this.listVatTuTK;
          } else {
            this.listVatTuDK_Ton = dataVaTu;
          }
        }
      },
    },
    listDangKyNhuCauTinhTrang: function (val) {
      if (val.length > 0) {
        this.congTrinhSelected = val[0].CONGTRINH_ID;
      }
    },
  },
  computed: {
    getShowComponent: function () {
      return true;
    },
  },
  async created() {
    try {
      this.loading(true);
      this.loadTagForm(this.$route.query);
      this.SetButton(0);
      await this.fetchDotDK();
      await this.fetchDuAnNV();
      await this.fetchDSCongTrinh();
      await this.fetchNhanVienLienHe();
      if (["1", "2", "3", "4"].includes(this.tagForm.toString())) {
        await this.fetchDSKhoTheoNV();
      } else {
        await this.fetchKhoDVCungUng();
      }
      // await this.fetchGetKeyDKVTMuaSam();
      await this.fetchVattu_MDDK(-1);
      await this.fetchDSMucDichDK();
      // this.statusCheckAll = this.$refs.gridDSVTTMDDK.selectAllItem;
      await this.HienThiDSDK();
      await this.LoadThamSo();
    } catch (e) {
      this.$toast.error(e.data.message);
    } finally {
      this.loading(false);
    }
  },
  data() {
    return {
      removeVT: removeVT,
      xemDangKy: btnXemDangKy,
      header: {
        title: "Đăng ký nhu cầu vật tư",
        list: [
          {
            name: "Quản lí vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Đăng ký nhu cầu vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Đăng ký nhu cầu mua sắm",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      chuKyDangKy: [
        { value: 0, text: "Nhu cầu hàng tháng" },
        { value: 6, text: "Nhu cầu 6 tháng" },
        { value: 3, text: "Nhu cầu theo quý" },
      ],
      tagForm: 0,
      txt_VatTu: "Vật tư",
      showTTDKNCVT: true,
      chuKyDKSelected: "",
      listThoiGianDK: [],
      thoiGianDKSelected: "",
      thoigianDK_show: true,
      ngayYC: new Date(),
      namDK: moment(new Date()).format("YYYY"),
      listDotDK: [],
      dotDKSelected: "",
      dotDK_show: true,
      listDuAnNV: [],
      duAnNVSelected: "",
      listCongTrinh: [],
      congTrinhSelected: "",
      text_congTrinhSelected: "",
      showCongTrinh: false,
      listNhanVienLienHe: [],
      nhanVienLienHeSelected: "",
      showNhanVienLH: false,
      idNhanVienLHSelected: "",
      listKho: [],
      kho_Visible: false,
      khoSelected: "",
      text_khoSelected: "",
      listDangKyNhuCauTinhTrang: [],
      radio_DKNCVT: 0,
      statusNhapMoi: false,
      listVatTuDaDK: [],
      listCuonCap: [],
      //Grid Danh sách vật tư theo mục đích đăng ký nhu cầu
      listMucDichDK: [],
      value_mucDichDKSelected: "",
      text_MucDichDK: "",
      showMucDichDK: false,
      listVatTuTK: [],
      value_cboTimKiem: "",
      cboTimKiem: "",
      showVatTuTK: false,
      pageSettings: {
        pageSizes: ["10", "20", "50", "100", "All"],
        pageSize: 10,
      },
      fields_listVatTuDaDK: [
        {
          fieldName: "MA_VT",
          headerText: "Mã VT",
          allowFiltering: true,
        },
        {
          fieldName: "TEN_VT",
          headerText: "Tên vật tư",
          allowFiltering: true,
        },
        {
          fieldName: "MUCDICH",
          headerText: "Mục đích",
          allowFiltering: true,
        },
        {
          fieldName: "DVI_TINH",
          headerText: "ĐVT",
          allowFiltering: true,
        },
        {
          fieldName: "SL_TON_DK",
          headerText: "SL Tồn ĐK",
          allowFiltering: true,
          cellCssClass: "text-right",
        },
        {
          fieldName: "SOLUONG",
          headerText: "Số lượng",
          allowFiltering: true,
          cellCssClass: "text-right",
        },
        {
          fieldName: "GHICHU",
          headerText: "Ghi chú",
          allowFiltering: true,
        },
        {
          fieldName: "SL_DUYET",
          headerText: "SL Duyệt",
          allowFiltering: true,
          cellCssClass: "text-right",
        },
        {
          isComponent: true,
          component:
            '<a class="btn btn-remove padt0 padb0 w20 center"><span class="-ap icon-ion-android-close"></span></a>',
        },
      ],
      //Danh sách vật tư theo mục đích đăng ký nhu cầu
      listVatTuMD: [],
      listVatTuMD_show: [],
      filterOptions: {
        enableCaseSensitivity: false,
        mode: "Immediate",
        ignoreAccent: true,
      },
      groupDSVTTMDDK: {
        showDropArea: false,
        columns: ["TEN_VT"],
      },
      listVatTuDK_Ton: [],
      list_CbxTimkiem: [],
      showCbxTimkiem: false,
      nhomMDIdSelected: "",
      statusGhiLai: false,
      statusXoa: false,
      statusHuyBo: false,
      statusThemVT: false,
      statusbtnFilePath: false,
      statusNhapExcel: false,
      statusInPhieu: false,
      txtMaPhieuDK: "",
      dangKyId_DSVT: "",
      dangKyId_DSVT_DaDK: "",
      txtGhiChu: "",
      checkDangKyCT: "",
      checkDangKyCT_ok: true,
      checkDangKyCT_message: "",
      dtpThangDK: "",
      editSettings: { allowEditing: true, mode: "Batch" },
      selectionSettings: {
        type: "Multiple",
        checkboxOnly: true,
      },
      listCheckInfoVatTu: [],
      getKeyVTDK_ID: "",
      duLieuVatTuDKv2: "",
      listVatTuDaDK_VTDKID: [],
      vattu_id_cha: "",
      list_CbxTimkiem_temp: [],
      soCVSelected: "",
      statusNopPhieu: true,
      statusCheckAll: false,
      copy_dk: false,
      listThamSoMD: [],
      txtSDT: "",
      fileNames: [],
      openDropDownInPhieu: false,
      temp_vatTuDKid: "",
      today: new Date(),
      value_IU_DANGKY_TT: "",
      VTThuocNhomMDDK_List: [],
      chonDK_visible: false,
      colXemDK: false,
    };
  },
  methods: {
    ...mapActions("dangkynhucauVT", [
      "getDSThoiGianDK",
      "getDotDK",
      "getDuAnNV",
      "getDSCongTrinh",
      "getNhanVienLienHe",
      "getKhoDVCungUng",
      "getDSDangKyNhuCauTinhTrang",
      "getDSVatTuDaDK",
      "getDSCuonCapTheoKho",
      "getDSMucDichDK",
      "getDSVatTuMD",
      "getDSVatTuTimKiem",
      "deleteVatTuDangKy",
      "getVatTuTon",
      "getVattu_MDDK",
      "deleteDuLieuVT",
      "kiemTraDangKyCT",
      "kiemTraDSDangKyTheoTime",
      "themMoiVatTuDK",
      "getKeyDKVTMuaSam",
      "checkInfoVatTu",
      "addDuLieuVatTuDKv2",
      "getDSThamSoMacDinh",
      "IU_DANGKY_TT",
      "getVattu_MDDK_v2",
      "getDSKhoTheoNV",
      "listVTThuocNhomMDDK",
      "getVattuMucDich",
      "themMoiVatTuKHDN",
      "getVattuMucDichKHDN",
      "addDuLieuVatTuDKKHDN",
      "getDSVatTuDaDKKHDN",
      "xoaVatTuDangKyKHDN",
      "getVatTuDangKyKHDN",
      "XoaDangKyKHDN"
    ]),
    async fetchListVTThuocNhomMDDK() {
      this.loading(true);
      const response = await this.listVTThuocNhomMDDK();
      if (response.error_code === "BSS-00000000") {
        this.VTThuocNhomMDDK_List = response.data;
      }
      console.log("VTThuocNhomMDDK_List = ", this.VTThuocNhomMDDK_List);
      this.loading(false);
    },
    async fetchDSKhoTheoNV() {
      let data = {
        phanVungId: this.$root.token.getPhanVungID(),
        nhanVienId: this.$root.token.getNhanVienID(),
        tenTat: "",
        tagForm: this.tagForm,
        kieuId: "0",
        maNguoiDung: this.$root.token.getUserName(),
      };
      const response = await this.getDSKhoTheoNV(data);
      if (response.error_code === "BSS-00000000") {
        // for (const el of response.data) {
        //   this.listKho.push({
        //     text: el.tenKho,
        //     value: el.khoID,
        //   });
        // }
        this.listKho = response.data;
      }
    },

    async fetch_IU_DANGKY_TT(
      _dangKyid,
      _kieu,
      _dot,
      _tuThangDk,
      _denThangDk,
      _soCV,
      _khoId,
      _congTrinhId
    ) {
      var mm = new Date();
      let data = {
        phanVungId: this.$root.token.getPhanVungID(),
        kieu: _kieu,
        dangKyId: _dangKyid,
        ngayDk:
          _kieu == 1 ? moment(this.ngayYC).format("DD/MM/YYYY HH:mm:ss") : "",
        duAnId: this.duAnNVSelected,
        soCv: _soCV,
        fileCv: this.fileNames.toString(),
        donViId: this.$root.token.getDonViID(),
        ghiChu: this.txtGhiChu,
        ngayCn: moment(mm).format("DD/MM/yyyy HH:mm:ss"),
        nguoiCn: this.$root.token.getUserName(),
        mayCn: "",
        ipCn: "",
        dot: _dot,
        tuThangDk: _tuThangDk,
        denThangDk: _denThangDk,
        dviQlyId: this.$root.token.getDonViDuLieuID(),
        khoId: _khoId,
        tagForm: this.tagForm,
        nhanVienLhId: this.idNhanVienLHSelected,
        sdt: this.txtSDT,
        congTrinhId: _congTrinhId,
      };
      if (this.tagForm == "4") {
        let ketQua = await this.themMoiVatTuKHDN(data);
      } else {
        const response = await this.IU_DANGKY_TT(data);
      }
      this.$root.toastSuccess("Thêm mới đăng ký thành công!");
      await this.HienThiDSDK();
    },

    hide: function () {
      this.showNhanVienLH = false;
    },

    async fetchCheckInfoVatTu(_vatTuId) {
      this.loading(true);
      let data = {
        phanVungId: this.$root.token.getPhanVungID(),
        dangKyId: this.dangKyId_DSVT,
        vatTuId: _vatTuId,
        mucDichId: this.value_mucDichDKSelected,
      };
      console.log("Data nè", data);
      if(this.tagForm == "4"){
        this.listCheckInfoVatTu = await this.getVatTuDangKyKHDN({
          dangky_id : this.dangKyId_DSVT,
          mucdich_id : this.value_mucDichDKSelected,
          tinh_dk_id : 97,
          vattu_id : _vatTuId
        });
        if (this.listCheckInfoVatTu.length > 0) {
          this.temp_vatTuDKid = this.listCheckInfoVatTu[0].VTDK_ID;
        }
      }else{
        const response = await this.checkInfoVatTu(data);
        if (response.error_code === "BSS-00000000") {
          this.listCheckInfoVatTu = response.data;
          if (response.data.length > 0) {
            this.temp_vatTuDKid = response.data[0].vatTuDkId;
          }
        }
      }      
      this.loading(false);
    },
    async fetchDSThoiGianDK(_ckdkId) {
      this.loading(true);
      let data = {
        ckdkId: _ckdkId,
      };
      const response = await this.getDSThoiGianDK(data);
      console.log("response Thời gian đăng ký", response);
      if (response.error_code === "BSS-00000000") {
        response.data[0].tgDk.forEach((element) => {
          this.listThoiGianDK.push({
            text: element.thoiGianDk,
            value: element.tgDkId,
          });
        });
      }
      this.loading(false);
    },
    async fetchDotDK() {
      const response = await this.getDotDK();
      if (response.error_code === "BSS-00000000") {
        response.data.forEach((element) => {
          this.listDotDK.push({
            text: element.dotDk,
            value: element.dotDkId,
          });
        });
      }
    },
    async fetchDuAnNV() {
      let data = {
        nhanVienId: this.$root.token.getNhanVienID(),
      };
      const response = await this.getDuAnNV(data);
      console.log("response Dự án", response);
      if (response.error_code === "BSS-00000000") {
        response.data.forEach((element) => {
          this.listDuAnNV.push({
            text: element.maCT,
            value: element.duAnId,
          });
        });
      }
    },
    async fetchDSCongTrinh() {
      let data = {
        nhanVienId: this.$root.token.getNhanVienID(),
        phanVungId: this.$root.token.getPhanVungID(),
        tenTat: "",
        tagForm: this.tagForm,
        kieuId: 1,
        maNguoiDung: this.$root.token.getMaNhanVien(),
      };
      const response = await this.getDSCongTrinh(data);
      if (response.error_code === "BSS-00000000") {
        // response.data.forEach((element) => {
        //   this.listCongTrinh.push({
        //     text: element.TEN_CT,
        //     value: element.CONGTRINH_ID,
        //   });
        // });
        this.listCongTrinh = response.data;
      }
    },
    async fetchNhanVienLienHe() {
      let data = {
        nhanVienId: this.$root.token.getNhanVienID(),
      };
      const response = await this.getNhanVienLienHe(data);
      console.log("response Nhân Viên", response);
      if (response.error_code === "BSS-00000000") {
        this.listNhanVienLienHe = response.data;
        var nvht = this.listNhanVienLienHe.filter(
          (a) => a.NHANVIEN_ID == data.nhanVienId
        );
        if (nvht.length > 0) {
          this.nhanVienLienHeSelected = nvht[0].TEN_NV;
          this.idNhanVienLHSelected = nvht[0].NHANVIEN_ID;
          this.txtSDT = nvht[0].SO_DT;
        }
      }
    },
    gridNhanVienLH_selectedRowChanged(data) {
      this.nhanVienLienHeSelected = data.TEN_NV;
      this.idNhanVienLHSelected = data.NHANVIEN_ID;
      this.txtSDT = data.SO_DT;
    },
    gridCongTrinh_selectedRowChanged(data) {
      this.text_congTrinhSelected = data.TEN_CT;
      this.congTrinhSelected = data.CONGTRINH_ID;
    },
    // gridListKho_selectedRowChanged(data) {
    //   this.khoSelected = data.khoID;
    //   this.text_khoSelected = data.tenKho;
    // },
    async fetchKhoDVCungUng() {
      const response = await this.getKhoDVCungUng();
      if (response.error_code === "BSS-00000000") {
        // response.data.forEach((element) => {
        //   this.listKho.push({
        //     text: element.tenKho,
        //     value: element.khoID,
        //   });
        // });
        this.listKho = response.data;
      }
    },
    async fetchDSDangKyNhuCauTinhTrang(_ttDk) {
      this.loading(true);
      let data = {
        phanVungId: this.$root.token.getPhanVungID(),
        donViId: this.$root.token.getDonViID(),
        ttDk: _ttDk,
        kieuDk: 1,
        tagForm: this.tagForm,
      };
      const response = await this.getDSDangKyNhuCauTinhTrang(data);
      console.log("Danh sách đăng ký nhu cầu vật tư", response.data);
      if (response.error_code === "BSS-00000000") {
        this.listDangKyNhuCauTinhTrang = response.data;

        // var result = response.data.find((str) => str.CT_VT_DK > 0);

        if (this.listDangKyNhuCauTinhTrang.length == 0) {
          this.listVatTuDaDK = [];
          this.SetButton(1);
        }
      }
      this.loading(false);
    },
    f_StringToDate(value, format = "DD/MM/YYYY") {
      return moment(value, format).toDate();
    },
    async HienThiDSDK() {
      try {
        this.loading(true);
        await this.fetchDSDangKyNhuCauTinhTrang(this.radio_DKNCVT);
        if (
          this.listDangKyNhuCauTinhTrang.length > 0 &&
          this.radio_DKNCVT == 0
        ) {
          await this.fetchDSVatTuMD();
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    gridDangKyNCTT_selectedRowChanged: async function (data) {
      console.log("Trạng thái", data);
      if (data) {
        // this.colXemDK = CT_VT_DK;
        this.dangKyId_DSVT = data.DANGKY_ID;
        if (data.FILE_CV !== "" && data.FILE_CV != "||") {
          this.fileNames = data.FILE_CV.split(",");
        } else {
          this.fileNames = [];
        }
        await this.fetchDSVatTuDaDK(data.DANGKY_ID);
        if (this.radio_DKNCVT == 0) {
          await this.fetchDSVatTuMD();
        } else {
          this.listVatTuMD = [];
        }

        this.SetButton(3);
        this.congTrinhSelected = data.CONGTRINH_ID;
        let CongTrinhDaChon = this.listCongTrinh.filter(
          (e) => e.CONGTRINH_ID == data.CONGTRINH_ID
        );
        this.text_congTrinhSelected =
          CongTrinhDaChon.length > 0 ? CongTrinhDaChon[0].TEN_CT : "";
        this.txtMaPhieuDK = data.MAPHIEU_DK;
        this.txtGhiChu = data.GHICHU;
        this.duAnNVSelected = data.MA_CT;
        this.soCVSelected = data.SO_CV;
        this.duAnNVSelected = data.DUAN_ID;
        this.dotDKSelected = data.DOT;
        this.namDK = data.THANGDK_TU.toString().slice(0, 4);
        this.khoSelected = data.KHO_ID;
        this.ngayYC = this.f_StringToDate(data.NGAY_DK, "DD-MM-YYYY hh:mm:ss");
        if (data.CONLAI.toString() == "1") {
          this.chuKyDKSelected = 0;
          this.thoiGianDKSelected =
            data.THANGDK_TU.toString().slice(4, 5) == 0
              ? data.THANGDK_TU.toString().slice(5, 6)
              : data.THANGDK_TU.toString().slice(4, 6);
        } else if (data.CONLAI.toString() == "6") {
          this.chuKyDKSelected = 6;
          this.thoiGianDKSelected =
            data.THANGDK_TU.toString().slice(4, 5) == 0
              ? data.THANGDK_TU.toString().slice(5, 6)
              : data.THANGDK_TU.toString().slice(4, 6);
        } else {
          this.chuKyDKSelected = 3;
          this.thoiGianDKSelected =
            data.THANGDK_TU.toString().slice(4, 5) == 0
              ? data.THANGDK_TU.toString().slice(5, 6)
              : data.THANGDK_TU.toString().slice(4, 6);
        }

        var nvht = this.listNhanVienLienHe.filter(
          (a) => a.NHANVIEN_ID == data.NHANVIEN_LH_ID
        );
        if (nvht.length > 0) {
          this.nhanVienLienHeSelected = nvht[0].TEN_NV;
          this.idNhanVienLHSelected = nvht[0].NHANVIEN_ID;
          this.txtSDT = nvht[0].SO_DT;
        }
        this.chonDK_visible = true;
      } else {
        this.dangKyId_DSVT = 0;
      }
    },
    btnLayTT() {
      this.statusNhapMoi = true;
    },
    async fetchDSVatTuDaDK(_dangKyId) {
      let data = {
        dangKyId: _dangKyId,
      };
      try {
        if (this.tagForm == "4") {
          this.listVatTuDaDK = await this.getDSVatTuDaDKKHDN(_dangKyId);
        } else {
          const response = await this.getDSVatTuDaDK(data);
          if (response.error_code === "BSS-00000000") {
            this.listVatTuDaDK = response.data;
          }
        }
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
      }
    },
    selected_listVatTuDaDK_checkbox(data) {
      this.listVatTuDaDK_VTDKID = data;
      console.log("selected_listVatTuDaDK_checkbox", data);
      // this.listVatTuDaDK_VTDKID((value) => {
      //   this.listVatTuDaDK.forEach((el) => {
      //     if (value == el.VTDK_ID) {

      //     }
      //   });
      // });
    },
    grid_listVatTuDaDK_selectedRowChanged(data) {
      console.log("grid_listVatTuDaDK_selectedRowChanged = ", data);
    },
    async fetchDSCuonCapTheoKho() {
      this.loading(true);
      let data = {
        khoId: 3528,
      };
      const response = await this.getDSCuonCapTheoKho(data);
      if (response.error_code === "BSS-00000000") {
        this.listCuonCap = response.data;
      }
      this.loading(false);
    },
    gridCuonCap_selectedRowChanged() {},
    gridCuonCap_checkbox() {},
    async fetchDSMucDichDK() {
      console.log("this.tagForm = ", this.tagForm);
      let data = {
        kieu:
          this.$root.token.getPhanVungID() != 28
            ? 0
            : ["1", "2", "3", "4"].includes(this.tagForm.toString())
            ? 12
            : 0,
      };
      console.log(data);
      try {
        const response = await this.getDSMucDichDK(data);
        console.log("Mục đích ĐK", response);
        if (response.error_code === "BSS-00000000") {
          this.listMucDichDK = response.data;
          if (response.data.length > 0) {
            this.nhomMDIdSelected = response.data[0].nhomMdId;
            this.value_mucDichDKSelected = response.data[0].mucDichId;
            this.text_MucDichDK = response.data[0].mucDich;
          }
        }
      } catch (e) {
        // this.$toast.error("Lỗi lấy mục đích đăng ký " + e.message_detail);
        console.log("Lỗi lấy mục đích đăng ký ", e);
      } finally {
        this.loading(false);
      }
    },
    gridMucDichDK_selectedRowChanged(data) {
      this.text_MucDichDK = data.mucDich;
      this.value_mucDichDKSelected = data.mucDichId;
    },
    async fetchDSVatTuTK() {
      const response = await this.getVatTuTon();
      if (response.error_code === "BSS-00000000") {
        this.listVatTuTK = response.data;
      }
    },
    gridVatTuDK_selectedRowChanged(data) {
      this.vattu_id_cha = data.VATTU_CHA_ID;
      this.cboTimKiem = data.TEN_VT;
      this.value_cboTimKiem = data.VATTU_ID;
      console.log("Chọn", data);
    },
    async checkVatTuCha(bool) {
      this.loading(true);
      this.list_CbxTimkiem_temp = [];
      let list_DSMucDichDK = [];
      let data = {
        donViId: this.$root.token.getDonViID(),
        nhomMd: this.tagForm,
      };
      if (this.listDangKyNhuCauTinhTrang.length > 0) {
        if (this.tagForm != "4") {
          const response = await this.getDSVatTuMD(data);
          if (response.error_code === "BSS-00000000") {
            list_DSMucDichDK = response.data;
          }
        } else {
          list_DSMucDichDK = await this.getVattuMucDichKHDN(-1);
        }
      }
      var dataTree = list_DSMucDichDK;
      var VATTU_CHA_ID = this.vattu_id_cha;
      var VATTU_ID = this.value_cboTimKiem;
      var dataFilter = [];

      // nếu focus vào gốc
      // if (VATTU_CHA_ID == "") {
      //   dataFilter = dataTree;
      // } else {
        // Lấy node focus
        var nodeFocus = dataTree.filter(function (el) {
          return el.VATTU_ID == VATTU_ID;
        });
        if (nodeFocus.length > 0) {
          dataFilter.push(nodeFocus[0]);
        }

        // tìm node cha của node focus
        var isNodeOrigin = false;
        var VATTU_CHA_ID_Filter = VATTU_CHA_ID;
        while (!isNodeOrigin) {
          var listNodeParent = dataTree.filter(function (el) {
            return el.VATTU_ID == VATTU_CHA_ID_Filter;
          });
          if (listNodeParent.length > 0) {
            dataFilter.push(listNodeParent[0]);
            VATTU_CHA_ID_Filter = listNodeParent[0].VATTU_CHA_ID;
          } else {
            isNodeOrigin = true;
          }
          
        }
        // tìm node con của node focus
        var listNodeChildID = [VATTU_ID];
        var isNodeChildExist = true;
        while (isNodeChildExist) {
          var listNodeChildId = [];
          for (const i in listNodeChildID) {
            //Lấy data node con
            var listNodeChild = dataTree.filter(function (el) {
              return el.VATTU_CHA_ID == listNodeChildID[i];
            });
            // thêm vào node con vào true và lấy ID của node con

            // console.log(listNodeChild, " = listNodeChild");
            for (const i in listNodeChild) {
              dataFilter.push(listNodeChild[i]);
              listNodeChildId.push(listNodeChild[i].VATTU_ID);
            }
          }
          //gán lại node con để tìm tiếp
          listNodeChildID = listNodeChildId;
          //hết node con
          if (listNodeChildId.length == 0) {
            isNodeChildExist = false;
          }
        }
       
      //}
      if (dataFilter.length > 0) {
        if (bool) {         
          this.listVatTuMD_show = this.list_to_tree(dataFilter.slice(0, 200));
        } else {
          this.listVatTuMD_show = this.list_to_tree(dataFilter);
        }
      }
      this.loading(false);
    },
    async fetchdeleteVatTuDangKy(_dangKyId, _vtuDkId) {
      let data = {
        dangKyId: _dangKyId,
        vtuDkId: _vtuDkId,
      };
      const response = await this.deleteVatTuDangKy(data);
      if (response.error_code === "BSS-00000000") {
        this.$toast.success("Xóa dữ liệu vật tư đã đăng ký thành công!");
      }
      await this.fetchDSVatTuDaDK(_dangKyId);
    },
    async removeVatTu(data) {
      try{
        if(this.radio_DKNCVT == "1"){
          this.$toast.error("Phiếu đã nộp không thể xóa vật tư!")
          return
        }
        var isBoxConfirm = false;
        await this.$bvModal.msgBoxConfirm("Bạn chắc chắn muốn xóa?", {
          title: "Thông báo",
          centered: true,
          size: "md",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then((value) => {
          if (value) {
            isBoxConfirm = true;
          } 
        });

        if (!isBoxConfirm) {
          return;
        }

        this.loading(true);
        if(this.tagForm == "4"){                
          await this.xoaVatTuDangKyKHDN({
              dangky_id: data.DANGKY_ID,
              vtdk_id: data.VTDK_ID,
              tinh_dk : 97
          });
          this.$toast.success("Xóa dữ liệu vật tư đã đăng ký thành công!");
          await this.fetchDSVatTuDaDK(data.DANGKY_ID);
        }else{
          await this.fetchdeleteVatTuDangKy(data.DANGKY_ID, data.VTDK_ID);
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false);
      }
      
    },
    removeAllVatTu() {
      this.$bvModal
        .msgBoxConfirm("Bạn chắc chắn muốn xóa?", {
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (v) {
            try {
              this.loading(true);
              if (this.listVatTuDaDK_VTDKID.length > 0) {
                this.listVatTuDaDK_VTDKID.forEach((el) => {
                  this.listVatTuDaDK.forEach(async (temp) => {
                    if (el == temp.VTDK_ID) {
                      await this.fetchdeleteVatTuDangKy(
                        temp.DANGKY_ID,
                        temp.VTDK_ID
                      );
                    }
                  });
                });
              } else {
                this.$toast.error("Chưa chọn vật tư nào được đăng ký!");
                return;
              }
            } catch (error) {
            } finally {
              this.loading(false);
            }
          }
        })
        .catch((err) => {
          this.$toast.error("Xóa thất bại");
        });
    },
    async fetchDSVatTuMD() {
      this.loading(true);
      let data = {
        donViId: this.$root.token.getDonViID(),
        nhomMd: this.tagForm,
      };
      const response = await this.getDSVatTuMD(data);
      if (response.error_code === "BSS-00000000") {
        this.listVatTuMD = response.data;
        this.checkVatTuCha(true);
      }
      this.loading(false);
    },
    async fetchVatTuTon() {
      const response = await this.getVatTuTon();
      if (response.error_code === "BSS-00000000") {
        this.listVatTuDK_Ton = response.data.data;
        // this.value_cboTimKiem = response.data.data[];
        // this.cboTimKiem = "";
      }
    },
    async fetchVattu_MDDK(_nhomMucDichId) {
      let data = {
        nhomMucDichId: _nhomMucDichId,
      };
      if (this.tagForm != "4") {
        const response = await this.getVattu_MDDK(data);
        if (response.error_code === "BSS-00000000") {
          this.list_CbxTimkiem = response.data;
        }
      } else {
        this.list_CbxTimkiem = await this.getVattuMucDichKHDN(_nhomMucDichId); // truyền cho vui, DB k dùng
      }
    },
    async fetchVattu_MDDK_v2(_nhomMucDichId) {
      const response = await this.getVattu_MDDK_v2();
      if (response.error_code === "BSS-00000000") {
        this.list_CbxTimkiem = response.data;
      }
    },

    async SetButton(kieu) {
      if (kieu == -1) {
        //Bat dau
        this.statusGhiLai = true;
        this.statusHuyBo = true;
      }
      if (kieu == 0) {
        //Bat dau
        //cbo_MucDich.Focus();
        this.statusHuyBo = false;
        this.statusGhiLai = false;
        this.statusNhapMoi = true;
        // this.ngayYC = moment(new Date()).format("DD/MM/yyyy HH:mm:ss");
        await this.Clear();
      }
      if (kieu == 1) {
        //Them moi
        this.dangky_id = 0;
        // cbo_MucDich.Focus();
        this.statusGhiLai = true;
        this.statusHuyBo = true;
        this.txtMaPhieuDK = "";
        this.statusNhapMoi = false;
        this.statusbtnFilePath = true;
        await this.Clear();
        // await this.fetchGetKeyDKVTMuaSam();
      }
      if (kieu == 2) {
        //Huy
        this.statusNhapMoi = true;
        this.statusXoa = true;
        this.Clear();
      }
      if (kieu == 3 && this.radio_DKNCVT != 1) {
        //Edit
        this.statusbtnFilePath = true;
        this.statusNhapMoi = true;
        this.statusXoa = true;
        this.statusGhiLai = true;
        this.statusHuyBo = true;
        this.statusThemVT = true;
        this.statusNhapExcel = true;
        this.statusInPhieu = true;
      }
    },
    async fetchDeleteDuLieuVT(_dangKyId) {
      this.loading(true);
      let data = {
        dangKyId: _dangKyId,
      };
      try {
        const response = await this.deleteDuLieuVT(data);
        if (response.error_code === "BSS-00000000") {
          this.$toast.success("Xóa dữ liệu đăng ký thành công!");
        }
      } catch (error) {
        this.$toast.error(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async Xoa() {
      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm("Bạn chắc chắn muốn xóa?", {
          title: "Thông báo",
          centered: true,
          size: "md",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then((value) => {
          if (value) {
            isBoxConfirm = true;
          }
        });

      if (!isBoxConfirm) {
        return;
      }
      try {
        this.loading(true);
        let body = this.makeBodyUpdateFile();
        this.capNhatDK(body);
        if(this.tagForm == 4){ // xóa KHDN 
          await this.XoaDangKyKHDN(this.dangKyId_DSVT);
        }else{
          await this.fetchDeleteDuLieuVT(this.dangKyId_DSVT);
        }
        
        this.SetButton(0);
        await this.HienThiDSDK();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    Clear() {
      this.dangKyId_DSVT = 0;
      this.txtGhiChu = "";
      this.soCVSelected = "";
      this.khoSelected = "";
      this.duAnNVSelected = "";
      this.congTrinhSelected = "";
      this.text_congTrinhSelected = "";
      // tvMucdich.FocusedNode = null;
      // this.listDangKyNhuCauTinhTrang = [];
      // this.listVatTuDaDK = [];
      // this.listVatTuMD = [];
      // this.listVatTuDK_Ton = [];

      // set lại theo nhân viên hiện tại
      var nvht = this.listNhanVienLienHe.filter(
        (a) => a.NHANVIEN_ID == this.$root.token.getNhanVienID()
      );
      if (nvht.length > 0) {
        this.nhanVienLienHeSelected = nvht[0].TEN_NV;
        this.idNhanVienLHSelected = nvht[0].NHANVIEN_ID;
        this.txtSDT = nvht[0].SO_DT;
      }
      this.listVatTuMD_show = [];
      this.listVatTuDK_Ton = [];
    },
    async GhiLai() {
      try {
        await this.KiemTra_DuLieu();
        if (!this.checkDangKyCT_ok) {
          return;
        }
        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm("Bạn có muốn cập nhật dữ liệu không? ", {
            title: "Thông báo",
            centered: true,
            size: "md",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          })
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            }
          });

        if (!isBoxConfirm) {
          return;
        }
        this.loading(true);
        if (this.statusNhapMoi === false) {
          await this.TaoDuLieu(true);
        } else {
          await this.TaoDuLieu(false);
        }
        await this.HienThiDSDK();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async fetchKiemTraDangKyCT() {
      this.loading(true);
      let data = {
        phanVungId: this.$root.token.getPhanVungID(),
        khoId: this.khoSelected,
        congTrinhId: this.congTrinhSelected,
        tagForm: this.tagForm,
      };
      try {
        const response = await this.kiemTraDangKyCT(data);
        this.checkDangKyCT = response.data[0];
        if (response.data[0] != "1") {
          this.checkDangKyCT_message = response.data[0];
        }
      } catch (ex) {
        this.$toast.error(
          `${ex.data.data[0].fieldName} ${ex.data.data[0].message}`
        );
      } finally {
        this.loading(false);
      }
    },
    async KiemTra_DuLieu() {
      if (this.congTrinhSelected != null && this.congTrinhSelected != "") {
        await this.fetchKiemTraDangKyCT();
        if (this.checkDangKyCT != "1") {
          if (this.checkDangKyCT_message != "") {
            this.$toast.warning(`${this.checkDangKyCT_message}`);
          }
          return (this.checkDangKyCT_ok = false);
        }
      }

      if (["1", "2", "3", "4"].includes(this.tagForm)) {
        if (this.khoSelected == "" || this.khoSelected == null) {
          this.$toast.error("Bạn chưa chọn kho");
          return (this.checkDangKyCT_ok = false);
        }
        // if (this.duAnNVSelected == null || this.duAnNVSelected == "") {
        //   this.$toast.error("Bạn chưa chọn dự án");
        //   return (this.checkDangKyCT_ok = false);
        // }
        if (
          this.idNhanVienLHSelected == null ||
          this.idNhanVienLHSelected == ""
        ) {
          this.$toast.error("Bạn chưa chọn nhân viên liên hệ");
          return (this.checkDangKyCT_ok = false);
        }
        if (this.txtSDT == "") {
          this.$toast.error("Bạn chưa nhập số điện thoại");
          return (this.checkDangKyCT_ok = false);
        }
      }
      if (this.chuKyDKSelected === null || this.chuKyDKSelected === "") {
        this.$toast.error("Chưa chọn Chu kỳ ĐK");
        return (this.checkDangKyCT_ok = false);
      }
      if (this.statusNhapMoi) {
        if (this.dotDKSelected === null || this.dotDKSelected === "") {
          this.$toast.error("Chưa chọn Đợt ĐK");
          return (this.checkDangKyCT_ok = false);
        }
      }
      return (this.checkDangKyCT_ok = true);
    },
    async fecthThemMoiVatTuDK(_kieu, _dot, _tuThangDk, _denThangDk, _soCV) {
      this.loading(true);
      var mm = new Date();
      let data = {
        phanVungId: this.$root.token.getPhanVungID(),
        kieu: _kieu,
        dangKyId: this.dangKyId_DSVT,
        ngayDk:
          _kieu == 1 ? moment(this.ngayYC).format("DD/MM/YYYY HH:mm:ss") : "",
        duAnId: this.duAnNVSelected,
        soCv: _soCV,
        fileCv: this.fileNames.toString(),
        donViId: this.$root.token.getDonViID(),
        ghiChu: this.txtGhiChu,
        ngayCn: moment(mm).format("DD/MM/yyyy HH:mm:ss"), // BE xử lý
        nguoiCn: this.$root.token.getUserName(),
        mayCn: "", // BE xử lý
        ipCn: "", // BE xử lý
        dot: _dot,
        tuThangDk: _tuThangDk,
        denThangDk: _denThangDk,
        dviQlyId: this.$root.token.getDonViDuLieuID(),
      };
      const response = await this.themMoiVatTuDK(data);
      if (response.error_code === "BSS-00000000") {
        this.$root.toastSuccess("Thêm mới đăng ký thành công !");
        await this.HienThiDSDK();
      }
      this.loading(false);
    },
    async fecthThemMoiVatTuDK2(_kieu, _dangKyId) {   
      let data = {
        phanVungId: this.$root.token.getPhanVungID(),
        kieu: _kieu,
        dangKyId: _dangKyId,
        ngayDk:
          _kieu == 1 ? moment(this.ngayYC).format("DD/MM/YYYY HH:mm:ss") : "",
        duAnId: 0,
        soCv: "",
        fileCv: this.fileNames.toString(),
        donViId: this.$root.token.getDonViID(),
        ghiChu: this.txtGhiChu,
        ngayCn: "", // BE xử lý
        nguoiCn: this.$root.token.getUserName(),
        mayCn: "", // BE xử lý
        ipCn: "", // BE xử lý
        dot: 0,
        tuThangDk: 0,
        denThangDk: 0,
        dviQlyId: this.$root.token.getDonViDuLieuID(),
        congTrinhId: -1,
        nhanVienLhId: this.idNhanVienLHSelected,
      };

      if (this.tagForm == "4") {
        let ketQua = await this.themMoiVatTuKHDN(data);
      } else {
        const response = await this.themMoiVatTuDK(data);
      }      
      await this.fetchDSDangKyNhuCauTinhTrang(this.radio_DKNCVT);
      await this.fetchDSVatTuDaDK(this.dangKyId_DSVT);
    },
    async TaoDuLieu(themmoi) {
      try {
        let vso_cv = "";
        let vduan_id = -1;
        let vthangdk_tu = 0;
        let vthangdk_den = 0;
        let vngay_dk;
        let vghichu = "";
        let vfile_cv = "";

        // if (themmoi) {
        //   this.fetchGetKeyDKVTMuaSam();
        // }
        if (this.thoiGianDKSelected == null || this.thoiGianDKSelected == "") {
          this.$root.toastError("Bạn chưa chọn Thời gian ĐK!");
          return;
        }

        vso_cv = this.soCVSelected.trim();
        vngay_dk = moment(this.ngayYC).format("DD/MM/YYYY HH:mm:ss");
        vghichu = this.txtGhiChu.trim();
        let v_ngayYC = vngay_dk.split(" ");
        let temp_v_ngayYC = v_ngayYC[0].split("/", 3);

        if (this.chuKyDKSelected.toString() == 0) {
          //Đăng ký hàng thang
          vthangdk_tu = parseInt(
            `${this.namDK}${
              this.thoiGianDKSelected < 10 ? 0 : ""
            }${parseInt(this.thoiGianDKSelected)}`
          );
          vthangdk_den = parseInt(
            `${this.namDK}${
              this.thoiGianDKSelected < 10 ? 0 : ""
            }${parseInt(this.thoiGianDKSelected)}`
          );
        }
        if (this.chuKyDKSelected.toString() == "6") {
          vthangdk_tu = parseInt(
            `${this.namDK}${
              this.thoiGianDKSelected < 10 ? 0 : ""
            }${parseInt(this.thoiGianDKSelected)}`
          );
          vthangdk_den = parseInt(
            `${this.namDK}${this.thoiGianDKSelected < 5 ? 0 : ""}${
              parseInt(this.thoiGianDKSelected) + 5
            }`
          );
        }
        if (this.chuKyDKSelected.toString() == "3") {
          vthangdk_tu = parseInt(
            `${this.namDK}${
              this.thoiGianDKSelected < 10 ? 0 : ""
            }${parseInt(this.thoiGianDKSelected)}`
          );
          vthangdk_den = parseInt(
            `${this.namDK}${this.thoiGianDKSelected < 10 ? 0 : ""}${
              parseInt(this.thoiGianDKSelected) + 2
            }`
          );
        }
        console.log("vthangdk_tu = ", vthangdk_tu);
        console.log("vthangdk_den = ", vthangdk_den);
        let vcongtrinh_id = -1;
        if (this.congTrinhSelected != null && this.congTrinhSelected != []) {
          vcongtrinh_id = parseInt(this.congTrinhSelected);
        }

        if (["1", "2", "3", "4"].includes(this.tagForm)) {
          let kho_id = 0;
          if (this.khoSelected != "" && this.khoSelected != null) {
            kho_id = parseInt(this.khoSelected.toString());
          }
          if (themmoi == false) {
            await this.fetch_IU_DANGKY_TT(
              this.dangKyId_DSVT,
              3,
              themmoi == false ? this.dotDKSelected : 1,
              vthangdk_tu,
              vthangdk_den,
              vso_cv,
              kho_id,
              vcongtrinh_id
            );
          } else {
            await this.fetch_IU_DANGKY_TT(
              "",
              1,
              1,
              vthangdk_tu,
              vthangdk_den,
              vso_cv,
              kho_id,
              vcongtrinh_id
            );
          }
        } else {
          if (themmoi == false) {
            await this.fecthThemMoiVatTuDK(
              3,
              themmoi == false ? this.dotDKSelected : 1,
              vthangdk_tu,
              vthangdk_den,
              vso_cv
            );
          } else {
            await this.fecthThemMoiVatTuDK(
              1,
              1,
              vthangdk_tu,
              vthangdk_den,
              vso_cv
            );
          }
        }
      } catch (ex) {
        this.$toast.error(ex.data.message);
      }
    },
    async fetchGetKeyDKVTMuaSam() {
      this.loading(true);
      let data = {
        phanVungId: this.$root.token.getPhanVungID(),
        keyName: "DANGKY_ID",
        numBlockSize: 0,
        numRetry: 0,
      };
      const response = await this.getKeyDKVTMuaSam(data);
      if (response.error_code === "BSS-00000000") {
        this.dangKyId_DSVT = response.data;
      }
      this.loading(false);
    },
    async fetchGetKeyVTDK_ID() {   
      let data = {
        phanVungId: this.$root.token.getPhanVungID(),
        keyName: "VTDK_ID",
        numBlockSize: 0,
        numRetry: 0,
        phanvung_id: this.tagForm == "4" ? 97 : null
      };
      const response = await this.getKeyDKVTMuaSam(data);
      if (response.error_code === "BSS-00000000") {
        this.getKeyVTDK_ID = response.data;
      }     
    },
    // GhiLai_Con()
    //     {
    //         try
    //         {
    //             DataTable dt_vtcon = (DataTable) grcDSVatTu_DaDK.DataSource;
    //             if (dt_vtcon == null || dt_vtcon.Rows.Count == 0)
    //                 return;
    //             var q = dt_vtcon.AsEnumerable().Where(l=>l["ISUPDATE"].toString()=="1");

    //             if (q==null)
    //             {

    //                 // Message_Box.ShowTB("Không có dữ liệu thay đổi để cập nhật");
    //                 return;
    //             }
    //             int dem = 0;
    //             foreach (DataRow dr in q)
    //             {
    //                 long sl_ton = 0;
    //                 if (dr["VATTU_ID"].toString() != "-1")
    //                 {
    //                     DataTable dston = NAP_VT_MK();
    //                         //DanhMuc.LAY_DS_VTDK_THEO_MD(ttnd.donvi_dl_id).Tables[0];
    //                     var q_ton = dston.AsEnumerable().Where(l => l["VATTU_ID"].toString() == dr["VATTU_ID"].toString()).FirstOrDefault();
    //                     if (q_ton != null)
    //                         sl_ton = Convert.ToInt64(q_ton["SL_TON"].toString());
    //                 }
    //                 vtdk_id = Convert.ToInt64(dr["VTDK_ID"].toString());
    //                 dem += TaoDuLieu_VTDK(false, dangky_id, 0, 1, 0, Convert.ToInt64(dr["SOLUONG"].toString()), dr["GHICHU"].toString(), dr["TEN_VT"].toString(), 1, sl_ton);
    //             }
    //             if (dem != 0)
    //             {
    //                 Message_Box.ShowTB("Có lỗi trong quá trình cập nhật dữ liệu");
    //             }
    //             else
    //             {
    //                 Message_Box.ShowTB(" Cập nhật dữ liệu thành công");
    //                 HienThiDS_VTDK();
    //             }
    //         }

    //         catch (Exception ex)
    //         {
    //             if (ex.Message.Contains("12899"))
    //             {
    //                 string[] er = ex.Message.Split('"');
    //                 string s_er = er[6].Replace("actual", "Độ dài nhập");
    //                 s_er = s_er.Replace("maximum", "Độ dài tối đa cho phép");

    //                 if (er[5] == "GHICHU")
    //                 {
    //                     Message_Box.ShowTB("Bạn nhập ghi chú quá dài " + s_er);
    //                 }
    //             }
    //             else
    //                 Message_Box.ShowError("Có lỗi: " + ex.toString());
    //         }
    //     }
    hideMDDK() {
      this.showMucDichDK = false;
    },
    hideVTDK() {
      this.showVatTuTK = false;
    },
    hideCongTrinh() {
      this.showCongTrinh = false;
    },
    async editGridVatTuTheoMucDich(arg) {
      try {
        this.loading(true);
        let VTDK_ID = arg.rowData.VATTU_ID;
        if (arg.columnName == "SOLUONG") {
          if (arg.value) {
            if (arg.value <= 0 || arg.value == 0) {
              this.$root.toastError("Số lượng đăng ký phải lớn hơn 0");
            } else if (arg.rowData.DVITINH_ID == 15) {
              this.$root.toastError(
                `Bạn không thể đăng ký vật tư này do đơn vị tính là "Không xác định"`
              );
            } else {
              await this.fetchCheckInfoVatTu(VTDK_ID);
              if (this.listCheckInfoVatTu.length > 0) {
                let v_soLuong = parseInt(this.listCheckInfoVatTu[0].SOLUONG) + parseInt(arg.value);
                await this.TaoDuLieu_VTDK(
                  false,
                  this.dangKyId_DSVT,
                  arg.rowData.VTID,
                  1,
                  arg.rowData.DVITINH_ID,
                  v_soLuong,
                  arg.rowData.TEN_VT,
                  this.value_mucDichDKSelected,
                  arg.rowData.SL_TON,
                  arg.rowData.GHICHU,
                  this.temp_vatTuDKid
                );
                arg.value = 0;
                for (
                  let index = 0;
                  index < this.listVatTuMD_show.length;
                  index++
                ) {
                  const element = this.listVatTuMD_show[index];
                  if (element.VTDK_ID == VTDK_ID) {
                    element.SOLUONG = 0;
                    break;
                  }
                }
              } else {
                await this.TaoDuLieu_VTDK(
                  true,
                  0,
                  arg.rowData.VTID,
                  1,
                  arg.rowData.DVITINH_ID,
                  arg.value,
                  arg.rowData.TEN_VT,
                  this.value_mucDichDKSelected,
                  arg.rowData.SL_TON,
                  arg.rowData.GHICHU,
                  this.temp_vatTuDKid
                );
                arg.value = 0;
                for (
                  let index = 0;
                  index < this.listVatTuMD_show.length;
                  index++
                ) {
                  const element = this.listVatTuMD_show[index];
                  if (element.VTDK_ID == VTDK_ID) {
                    element.SOLUONG = 0;
                    break;
                  }
                }
              }
            }
            this.$refs.gridDSVTTMDDK.refreshColumns();
          }
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async editGridVatTuDaDK(arg) {
      if (arg.columnName == "SOLUONG") {
        await this.TaoDuLieu_VTDK(
          false,
          arg.rowData.DANGKY_ID,
          arg.rowData.VATTU_ID,
          1,
          arg.rowData.DVITINH_ID,
          arg.value,
          arg.rowData.TEN_VT,
          this.value_mucDichDKSelected,
          arg.rowData.SL_TON_DK,
          arg.rowData.GHICHU,
          arg.rowData.VTDK_ID
        );
      }
    },
    async TaoDuLieu_VTDK(
      themmoi,
      vdk_id,
      vt_id,
      loai,
      dvt_id,
      sl_ld,
      ten_vt,
      vmd_id,
      sl_ton,
      ghichu,
      v_vatTuDkId
    ) {
      let vten_vt = "";
      let v_vtdk_id = 0;
      vdk_id = 0;
      let v_vt_id = -1;
      let vloai = 0;
      let vdvitinh_id = 0;
      let v_sl = 0;
      // let vsl_ton = 0;
      let vtdk_id = 0;

      if (themmoi) {
        await this.fetchGetKeyVTDK_ID();
        vtdk_id = this.getKeyVTDK_ID;
      }
      if (ten_vt !== null || ten_vt !== "") {
        vten_vt = ten_vt;
      }

      if (themmoi == false) {
        v_vtdk_id = v_vatTuDkId;
      } else {
        v_vtdk_id = vtdk_id;
      }
      vdk_id = this.dangKyId_DSVT;
      if (vt_id !== 0 && vt_id !== "") {
        v_vt_id = vt_id;
      }

      vloai = loai;
      vdvitinh_id = dvt_id;
      v_sl = sl_ld;

      // vsl_ton = sl_ton;
      if (themmoi === false) {
        await this.fetchAddDuLieuVatTuDKv2(
          2,
          v_vtdk_id,
          vdk_id,
          v_vt_id,
          vten_vt,
          vloai,
          v_sl,
          vmd_id,
          vdvitinh_id,
          sl_ton,
          ghichu
        );
      } else {
        await this.fetchAddDuLieuVatTuDKv2(
          1,
          v_vtdk_id,
          vdk_id,
          v_vt_id,
          vten_vt,
          vloai,
          v_sl,
          vmd_id,
          vdvitinh_id,
          sl_ton,
          ghichu
        );
      }
      // await this.fetchDSVatTuDaDK(v_vtdk_id);
    },

    async TaoDuLieu_VTDK_Excel(
      v_vtdk_id,
      themmoi,
      vdk_id,
      vt_id,
      loai,
      dvt_id,
      sl_ld,
      ten_vt,
      vmd_id,
      sl_ton,
      ghichu,
      v_vatTuDkId
    ) {
      let vten_vt = "";
      vdk_id = 0;
      let v_vt_id = -1;
      let vloai = 0;
      let vdvitinh_id = 0;
      let v_sl = 0;
      // let vsl_ton = 0;
      let vtdk_id = 0;

      if (themmoi) {
        await this.fetchGetKeyVTDK_ID();
        vtdk_id = this.getKeyVTDK_ID;
      }
      if (ten_vt !== null || ten_vt !== "") {
        vten_vt = ten_vt;
      }
      vdk_id = this.dangKyId_DSVT;
      if (vt_id !== 0 && vt_id !== "") {
        v_vt_id = vt_id;
      }

      vloai = loai;
      vdvitinh_id = dvt_id;
      v_sl = sl_ld;

      // vsl_ton = sl_ton;
      if (themmoi === false) {
        await this.fetchAddDuLieuVatTuDKv2(
          2,
          v_vtdk_id,
          vdk_id,
          v_vt_id,
          vten_vt,
          vloai,
          v_sl,
          vmd_id,
          vdvitinh_id,
          sl_ton,
          ghichu
        );
      } else {
        await this.fetchAddDuLieuVatTuDKv2(
          1,
          v_vtdk_id,
          vdk_id,
          v_vt_id,
          vten_vt,
          vloai,
          v_sl,
          vmd_id,
          vdvitinh_id,
          sl_ton,
          ghichu
        );
      }
      // await this.fetchDSVatTuDaDK(v_vtdk_id);
    },
    async fetchAddDuLieuVatTuDKv2(
      _kieu,
      v_vtdk_id,
      vdk_id,
      v_vt_id,
      vten_vt,
      vloai,
      v_sl,
      vmd_id,
      vdvitinh_id,
      vsl_ton,
      ghichu
    ) {
      let data = {
        phanVungId: this.$root.token.getPhanVungID(),
        kieu: _kieu,
        vtDangKyId: v_vtdk_id,
        dangKyId: vdk_id,
        vatTuId: v_vt_id,
        tenVatTu: vten_vt,
        loai: vloai,
        soLuong: v_sl,
        mucDichId: vmd_id,
        donViTinhId: vdvitinh_id,
        ghiChu: ghichu,
        ngayCn: "",
        nguoiCn: this.$root.token.getUserName(),
        mayCn: "",
        ipCn: "",
        soLuongTon: vsl_ton == null || vsl_ton == "" ? 0 : vsl_ton,
      };

      if (this.tagForm == "4") {
        let ketQua = await this.addDuLieuVatTuDKKHDN(data);
      } else {
        const response = await this.addDuLieuVatTuDKv2(data);
        if (response.error_code === "BSS-00000000") {
          this.duLieuVatTuDKv2 = response.data;
        }
      }
      await this.fetchDSVatTuDaDK(this.dangKyId_DSVT);
    },
    async NopPhieu() {
      if (this.listDangKyNhuCauTinhTrang.length == 0) {
        this.$root.toastError("Không có đăng ký để nộp");
        return;
      }
      if (this.listVatTuDaDK.length == 0) {
        this.$root.toastWarning("Không có vật tư trong danh sách đăng ký");
        return;
      }

      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm("Bạn có chắc chắn muốn nộp phiếu đăng ký không?", {
          title: "Xác nhận hành động",
          centered: true,
          size: "md",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then((value) => {
          if (value) {
            isBoxConfirm = true;
          } else {
            return;
          }
        });

      if (!isBoxConfirm) {
        return;
      }

      try {
        this.loading(true);
        for (const el of this.listVatTuDaDK) {
          await this.fecthThemMoiVatTuDK2(4, el.DANGKY_ID);
        }
        await this.fetchDSDangKyNhuCauTinhTrang(this.radio_DKNCVT);
        this.$root.toastSuccess("Nộp đăng ký thành công!");
      } catch (e) {
        this.$toast.error("Có lỗi xảy ra khi nộp phiếu!");
      } finally {
        this.loading(false);
      }
    },
    DSVatTuMD_selectedRowChanged(data) {},
    exportExcelDSVTTMDDK() {
      if (!this.showCbxTimkiem) {
        if (this.listVatTuMD.length <= 0) {
          this.$root.toastError(
            "Danh sách vật tư theo mục đích đăng ký nhu cầu không có dữ liệu để xuất Excel!"
          );
        } else {
          this.loading(true);
          try {
            var dsach = xlsx.utils.json_to_sheet(this.listVatTuMD);
            var wb = xlsx.utils.book_new();
            xlsx.utils.book_append_sheet(wb, dsach, "DS");
            xlsx.writeFile(wb, "Danh sách vật tư theo mục đích.xlsx");
          } catch (error) {
          } finally {
            this.loading(false);
          }
        }
      } else {
        if (this.listVatTuDK_Ton.length <= 0) {
          this.$root.toastError(
            "Danh sách vật tư theo mục đích đăng ký nhu cầu không có dữ liệu để xuất Excel!"
          );
        } else {
          this.loading(true);
          try {
            var dsach = xlsx.utils.json_to_sheet(this.listVatTuDK_Ton);
            var wb = xlsx.utils.book_new();
            xlsx.utils.book_append_sheet(wb, dsach, "DS");
            xlsx.writeFile(wb, "Danh sách vật tư theo mục đích.xlsx");
          } catch (error) {
          } finally {
            this.loading(false);
          }
        }
      }
    },
    async fetchDSThamSoMacDinh() {
      this.loading(true);
      const response = await this.getDSThamSoMacDinh();
      if (response.error_code === "BSS-00000000") {
        this.listThamSoMD = response.data;
      }
      this.loading(false);
    },
    async LoadThamSo() {
      await this.fetchDSThamSoMacDinh();
      this.listThamSoMD.forEach((el) => {
        if (el.maTS.toString() == "QLVT_COPY_DK") {
          if (el.tenTS.toString().trim() == "1") {
            this.copy_dk = true;
          }
        }
      });
    },
    exportExcelDSVTDDK() {
      if (this.listVatTuMD.length <= 0) {
        this.$root.toastError(
          "Danh sách vật tư đã đăng ký không có dữ liệu để xuất Excel!"
        );
      } else {
        this.loading(true);
        try {
          var dsach = xlsx.utils.json_to_sheet(this.listVatTuDaDK);
          var wb = xlsx.utils.book_new();
          xlsx.utils.book_append_sheet(wb, dsach, "DS");
          xlsx.writeFile(wb, "Danh sách vật tư đã đăng ký.xlsx");
        } catch (error) {
        } finally {
          this.loading(false);
        }
      }
    },

    async btnChonDK_Click() {
      try {
        var f = this.$refs.frmChonDangKy;
        await f.Open();
      } catch (ex) {
        this.$toast.error("Có lỗi: " + ex);
      }
    },

    async tsbtnCopyDK_Click() {
      try {
        await this.LoadThamSo();
        // this.copy_dk = true;
        if (this.copy_dk) {
          var f = this.$refs.frmCopyDK;
          await f.Open();
        } else {
          this.$toast.warning("Coming soon ...");
        }
      } catch (ex) {
        this.$toast.error("Có lỗi: " + ex);
      }
    },
    async CapNhatDS_DK_Excel(excel) {
      for (const indexX of excel) {
        try {
          let sl_ld_ex = 0;
          let gc = "";

          if (indexX.SOLUONG != "0") {
            sl_ld_ex = parseInt(indexX.SOLUONG);
          }

          if (indexX.GHICHU != "") {
            gc = indexX.GHICHU;
          } else {
            gc = "";
          }

          if (!indexX.MA_VT.toString() && sl_ld_ex >= 0 && sl_ld_ex != 0) {
            var q = this.list_CbxTimkiem
              .AsEnumerable()
              .Where(
                (l) =>
                  l["MA_VT"].toString().Trim() == indexX["MA_VT"].toString()
              );
            if (q.length != 0) {
              // nếu có mã vt trong mục đích
              var q_dk = this.listVatTuDaDK
                .AsEnumerable()
                .Where(
                  (l) =>
                    l["VATTU_ID"].toString().Trim() ==
                      q[0]["VATTU_ID"].toString().Trim() &&
                    l["MUCDICH_ID"].toString() ==
                      this.value_mucDichDKSelected.toString()
                );
              if (q_dk.length > 0) {
                // Nếu có mã vật trong đăng ký và cùng MĐ rồi thì up
                // if (!String.IsNullOrEmpty(q_dk.CopyToDataTable().Rows[0]["DVITINH_ID"].toString()))
                // {
                // long slld = sl_ld_ex + Convert.ToInt64(q_dk.CopyToDataTable().Rows[0]["SOLUONG"].toString());
                // // TaoDuLieu_VTDK(false,dangky_id,)
                // TaoDuLieu_VTDK_Excel(false, dangky_id, Convert.ToInt64(q_dk.CopyToDataTable().Rows[0]["VTDK_ID"].toString()), Convert.ToInt64(q_dk.CopyToDataTable().Rows[0]["VATTU_ID"].toString()), 1, Convert.ToInt32(q_dk.CopyToDataTable().Rows[0]["DVITINH_ID"].toString()), slld, gc, "", Convert.ToInt32(this.value_mucDichDKSelected.toString()));
                // // DangKy.Update(dsVATTUDK);
                // i++;
                // }
                // else
                // {
                //     r["lydo"] = "Đơn vị tính trong danh mục của vật tư chưa có";
                //     dtNotInsert.ImportRow(r);
                // }
              }
              //     else // thì insert
              //     {
              //         if (!String.IsNullOrEmpty(q.CopyToDataTable().Rows[0]["DVITINH_ID"].toString()))
              //         {
              //             //TaoDuLieu_VTDK_Excel(true, dangky_id, 0, Convert.ToInt64(q.CopyToDataTable().Rows[0]["VATTU_ID"].toString()), 1, Convert.ToInt32(q.CopyToDataTable().Rows[0]["DVITINH_ID"].toString()), sl_ld_ex, gc, null);
              //             TaoDuLieu_VTDK_Excel(true, dangky_id, 0, Convert.ToInt64(q.CopyToDataTable().Rows[0]["VATTU_ID"].toString()), 1, Convert.ToInt32(q.CopyToDataTable().Rows[0]["DVITINH_ID"].toString()), sl_ld_ex, gc, "", Convert.ToInt32(this.value_mucDichDKSelected.toString()));
              //             i++;
              //         }
              //         else
              //         {
              //             r["lydo"] = "Đơn vị tính trong danh mục của vật tư chưa có";
              //             dtNotInsert.ImportRow(r);
              //         }
              //     }
            }
            // else // Ko có mã trong mục đích
            // {
            //     r["lydo"] = "Mã vật tư không có trong mục đích đăng ký";
            //     dtNotInsert.ImportRow(r);
            // }
          }
        } catch (ex) {}
      }
    },
    async importExcel(e) {
      const files = e.target.files;
      if (!files.length) {
        return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$toast.warning("File excel không đúng định dạng!");
      }
      const fileReader = new FileReader();
      fileReader.onload = async (ev) => {
        try {
          const data = ev.target.result;
          const XLSX = xlsx;
          const workbook = XLSX.read(data, {
            type: "binary",
          });
          const wsname = workbook.SheetNames[0];
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
          //Test
          // await this.CapNhatDS_DK_Excel(ws);
          if (ws.length < 0 || ws.length == 0) {
            this.$root.toastError("File excel chưa có dữ liệu");
            return;
          }

          this.loading(true);
          let ds_vt_dk = [];
          if (this.tagForm != "4") {
            ds_vt_dk = await this.getVattuMucDich(-1);
          } else {
            ds_vt_dk = await this.getVattuMucDichKHDN(-1);
          }

          let loi_excel = [];
          for (let e of ws) {
            if (e.MA_VT == "" || e.TEN_VT == "") {
              e.LYDO = "Chưa điền tên hoặc mã vật tư";
              loi_excel.push(e);
              continue;
            }
            if (parseInt(e.SOLUONG) <= 0 || parseInt(e.SOLUONG == 0)) {
              e.LYDO = "Số lượng đăng ký phải lớn hơn 0";
              loi_excel.push(e);
              continue;
            }
            if (e.DVITINH_ID == 15) {
              e.LYDO = `Bạn không thể đăng ký vật tư này do đơn vị tính là "Không xác định"`;
              loi_excel.push(e);
              continue;
            }
            let checkMaVT = ds_vt_dk.filter((a) => a.MA_VT == e.MA_VT);
            if (checkMaVT.length == 0) {
              e.LYDO = `Mã vật tư không có trong mục đích đăng ký`;
              loi_excel.push(e);
              continue;
            }
            try {
              let checkVatTuExist = this.listVatTuDaDK.filter(
                (a) =>
                  a.VATTU_ID == e.VATTU_ID &&
                  a.MUCDICH_ID == this.value_mucDichDKSelected
              );
              if (checkVatTuExist.length > 0) {
                let v_soLuong =
                  parseFloat(this.listVatTuDaDK[0].SOLUONG) +
                  parseFloat(e.SOLUONG);
                await this.TaoDuLieu_VTDK_Excel(
                  checkVatTuExist[0].VTDK_ID,
                  false,
                  e.DANGKY_ID,
                  e.VTID,
                  1,
                  e.DVITINH_ID,
                  v_soLuong,
                  e.TEN_VT,
                  this.value_mucDichDKSelected,
                  e.SL_TON,
                  e.GHICHU,
                  e.VTDK_ID
                );
              } else {
                await this.TaoDuLieu_VTDK_Excel(
                  0,
                  true,
                  0,
                  e.VTID,
                  1,
                  e.DVITINH_ID,
                  e.SOLUONG,
                  e.TEN_VT,
                  this.value_mucDichDKSelected,
                  e.SL_TON,
                  e.GHICHU,
                  e.VTDK_ID
                );
              }
            } catch (error) {
              e.LYDO = error.data.message;
              loi_excel.push(e);
            }
          }
          if (loi_excel.length > 0) {
            var dsach = xlsx.utils.json_to_sheet(loi_excel);
            var wb = xlsx.utils.book_new();
            xlsx.utils.book_append_sheet(wb, dsach, "DS");
            xlsx.writeFile(wb, "Lỗi import vật tư.xlsx");
          } else {
            this.$toast.success("Nhập từ file thành công!");
          }
        } catch (e) {
          this.$toast.error(e.data.message);
        } finally {
          this.loading(false);
        }
      };
      fileReader.readAsBinaryString(files[0]);
      var input = document.getElementById("upload");
    },
    NhapMoi() {
      this.chuKyDKSelected = "";
      this.listVatTuMD = [];
      this.listVatTuDaDK = [];
      this.fileNames = [];
      this.namDK = moment(new Date()).format("YYYY");
      this.ngayYC = new Date();
      // this.txtSDT = "";
      // this.nhanVienLienHeSelected = "";
      // this.idNhanVienLHSelected = "";
      this.SetButton(1);
    },
    list_to_tree(list) {
      var list_origin = [];
      for (const i in list) {
        var VATTU_CHA_ID = list[i].VATTU_CHA_ID;
        var listNodeParent = list.filter(function (el) {
          return el.VATTU_ID == VATTU_CHA_ID;
        });
        if (listNodeParent.length == 0) {
          list_origin.push(list[i].VATTU_CHA_ID);
        }
      }
      var map = {},
        node,
        roots = [],
        i;
      for (i = 0; i < list.length; i += 1) {
        map[list[i].VATTU_ID] = i; // initialize the map
        list[i].children = []; // initialize the children
      }
      for (i = 0; i < list.length; i += 1) {
        node = list[i];
        if (!list_origin.includes(node.VATTU_CHA_ID)) {
          list[map[node.VATTU_CHA_ID]].children.push(node);
        } else {
          roots.push(node);
        }
      }
      return roots;
    },

    onRowSelectingTreeGrid(args) {},

    async btnInPhieu() {
      if (this.listVatTuDaDK.toString() == "") {
        this.$toast.error("Vui lòng chọn vật tư theo mục đích đăng ký nhu cầu");
        return;
      }
      this.loading(true);
      const baseUrl = process.env.API;
      await this.axios({
        url: `${baseUrl}/web-qlvt/api/bao-cao/dang-ky-nhu-cau-vat-tu?extend=pdf`,
        method: "POST",
        responseType: "blob",
        data: {
          dangKyId: this.dangKyId_DSVT.toString(),
          thoiGianDk: this.thoiGianDKSelected,
          namDk: this.namDK,
          phanvung_id : this.tagForm == "4" ? 97 : null
        },
      }).then((response) => {
        previewPrint(response.data);
      });
      this.loading(false);
    },
    async onChangeFile(e) {
      try {
        this.loading(true);
        const files = e.target.files;
        if (files[0].size < 15728640) {
          if (files.length > 0) {
            const formData = new FormData();
            for (let index = 0; index < files.length; index++) {
              const element = files[index];
              formData.append("file", element);
            }
            await this.upLoadFile(formData);
          }
        } else {
          var a = files[0].size / 1048576;
          this.$root.toastError(
            `File vượt quá kích thước cho phép.\nFile hiện tại: ${a.toFixed(
              2
            )} Mb\nTối đa 15 Mb`
          );
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    makeBodyUpdateFile() {
      let vthangdk_tu = 0;
      let vthangdk_den = 0;
      let vngay_dk = moment(this.ngayYC).format("DD/MM/yyyy HH:mm:ss");
      let v_ngayYC = vngay_dk.split(" ");
      let temp_v_ngayYC = v_ngayYC[0].split("/", 3);

      if (this.chuKyDKSelected === 0) {
        //Đăng ký hàng thang
        vthangdk_tu = parseInt(
          `${this.namDK}${
            this.thoiGianDKSelected < 10 ? 0 : ""
          }${parseInt(this.thoiGianDKSelected)}`
        );
        vthangdk_den = parseInt(
          `${this.namDK}${
            this.thoiGianDKSelected < 10 ? 0 : ""
          }${parseInt(this.thoiGianDKSelected)}`
        );
      }
      if (this.chuKyDKSelected === "6") {
        vthangdk_tu = parseInt(
          `${this.namDK}${
            this.thoiGianDKSelected < 10 ? 0 : ""
          }${parseInt(this.thoiGianDKSelected)}`
        );
        vthangdk_den = parseInt(
          `${this.namDK}${this.thoiGianDKSelected < 5 ? 0 : ""}${
            parseInt(this.thoiGianDKSelected) + 5
          }`
        );
      }
      if (this.chuKyDKSelected === "3") {
        vthangdk_tu = parseInt(
          `${this.namDK}${
            this.thoiGianDKSelected < 10 ? 0 : ""
          }${parseInt(this.thoiGianDKSelected)}`
        );
        vthangdk_den = parseInt(
          `${this.namDK}${this.thoiGianDKSelected < 10 ? 0 : ""}${
            parseInt(this.thoiGianDKSelected) + 2
          }`
        );
      }
      return {
        dangKyId: 18991,
        ngayDk: moment(this.ngayYC).format("DD/MM/yyyy HH:mm:ss"),
        duAnId: this.duAnNVSelected,
        fileCv: this.fileNames.length > 0 ? this.fileNames.join() : "",
        soCv: this.soCVSelected,
        nhanVienId: this.$auth.getNhanVienID(),
        donViId: this.$auth.getDonViID(),
        ghiChu: this.txtGhiChu,
        ngayCn: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
        nguoiCn: this.$auth.getUserName(),
        mayCn: "pcAdmin",
        ipCn: "192.168.24.5",
        dot: this.dotDKSelected,
        tuThangDk: vthangdk_tu,
        denThangDk: vthangdk_den,
        dviQlyId: this.$root.token.getDonViDuLieuID(),
      };
    },
    upLoadFile: async function (formData) {
      this.$root.showLoading(true);
      try {
        const { data } = await this.axios.post(
          `web-qlvt/api/storage/uploadFile`,
          formData,
          {
            headers: { "Content-Type": "multipart/form-data" },
          }
        );
        if (data.error == "200") {
          this.$toast.success("Tải lên thành công!");
          data.data.forEach((element) => {
            this.fileNames.push(element.message.relativeUrl);
          });

          let body = this.makeBodyUpdateFile();
          await this.capNhatDK(body);
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    async downLoadFile(fileName) {
      try {
        var rs = await this.axios.get(`/web-qlvt/api/storage/downloadFile`, {
          params: {
            fileSource: fileName,
          },
        });
        return rs.data;
      } catch (error) {
        return "";
      }
    },
    async onClickDownFile(value) {
      if (value) {
        this.$root.showLoading(true);
        this.DOWLOAD_OPEN(value);
        // const data = await this.downLoadFile(value);
        this.$root.showLoading(false);
        // if (data) {
        //   const filePath = value.split("/");
        //   const linkSource = `data:application/octet-stream;base64,${data.data.body.data}`;
        //   const downloadLink = document.createElement("a");
        //   const fileName = filePath[filePath.length - 1];
        //   downloadLink.href = linkSource;
        //   downloadLink.download = fileName;
        //   downloadLink.click();
        // }
      }
    },
    previewPrint(data) {
      var blobURL = window.URL.createObjectURL(
        new Blob([data], { type: "application/pdf" })
      );
      var iframe = document.createElement("iframe");
      document.body.appendChild(iframe);

      iframe.style.display = "none";
      iframe.src = blobURL;
      iframe.onload = function () {
        setTimeout(function () {
          iframe.focus();
          iframe.contentWindow.print();
        }, 1);
      };
    },
    downloadFile(data, fileName) {
      if (data) {
        var blob = new Blob([data], { type: "application/octet-stream" });
        var url = window.URL.createObjectURL(blob);
        var a = document.createElement("a");
        a.href = url;
        a.download = fileName;
        a.click();
        document.body.removeChild(a);
      }
    },
    async DOWLOAD_OPEN(url) {
      let fileName = url.substring(url.lastIndexOf("/"));
      try {
        this.loading(true);
        const baseUrl = process.env.API;
        await this.axios({
          url: `${baseUrl}/web-qlvt/api/minioStorage/downloadFile?fileSource=${url}`,
          method: "GET",
          responseType: "blob",
          data: {},
        }).then((response) => {
          if (fileName.includes(".pdf")) {
            this.previewPrint(response.data);
          } else {
            this.downloadFile(response.data, fileName);
          }
        });
      } catch (error) {
        let res = await JSON.parse(await error.data.text());
        this.$toast.error(res.error_code);
      } finally {
        this.$root.loading(false);
      }
    },
    async onClickDeleteFile(value) {
      if (value) {
        let isConfirm = await this.$bvModal.msgBoxConfirm(
          `Bạn có muốn xóa file này không ?`,
          {
            okVariant: "success",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
            centered: true,
          }
        );
        if (isConfirm) {
          let body = this.makeBodyUpdateFile();
          let file =
            this.fileNames.filter((item) => item != value).join() || "";
          body.fileCv = file;
          let res = await this.TaoDuLieu_DK(file);
          this.fileNames = this.fileNames.filter((item) => item != value);

          const rs = await this.$root.context.delete(
            `/web-qlvt/api/minioStorage/deleteFile?fileSource=${value}`
          );
        }
      }
    },
    async getLinkFile(_fileSource) {
      try {
        this.loading(true);
        let data = {
          fileSource: _fileSource,
        };
        var rs = await this.axios.post(
          "/web-qlvt/api/storage/getPresignedUrl",
          data
        );
        this.getLink = rs.data;
      } catch (err) {
        this.$toast.error(error.data.message);
      } finally {
        this.loading(false);
      }
    },
    async checkFileExist(_fileSource) {
      try {
        this.loading(true);
        let data = {
          fileSource: _fileSource,
        };
        var rs = await this.axios.post(
          "/web-qlvt/api/storage/checkExistsFile",
          data
        );

        this.checkFile = rs.data;
      } catch (err) {
        this.$toast.error(error.data.message);
      } finally {
        this.loading(false);
      }
    },
    async onClickViewFile(value) {
      if (value) {
        await this.checkFileExist(value);

        let linkFile = "";
        if (this.checkFile.message == "Success" && this.checkFile.data.data) {
          await this.getLinkFile(value);

          linkFile = this.getLink.data.name;
        } else {
          linkFile = "http://10.70.52.164:8081/FILES/HPG/VATTU/" + value;
        }
        const downloadLink = document.createElement("a");
        downloadLink.href = linkFile;
        const filePath = value.split("/");
        const fileName = filePath[filePath.length - 1];
        downloadLink.download = fileName;
        downloadLink.target = "_blank";
        downloadLink.click();
      }
    },
    datetoString(value, format = "DD/MM/YYYY HH:mm:ss") {
      return moment(value).format(format);
    },
    stringToDate(value, format = "DD/MM/YYYY") {
      return moment(value, format).toDate();
    },
    async TaoDuLieu_DK(fileCv) {
      try {
        this.$root.loading(true);
        let data = {
          kieu: 2,
          dangKyId: this.dangKyId_DSVT,
          ngayDk: this.datetoString(new Date()),
          duAnId: 0,
          soCv: "",
          fileCv: fileCv,
          donViId: this.$auth.getDonViID(),
          ghiChu: "",
          ngayCn: this.datetoString(new Date()),
          nguoiCn: this.$auth.getUserName(),
          dot: 0,
          tuThangDk: 0,
          denThangDk: 0,
          dviQlyId: this.$root.token.getDonViDuLieuID(),
        };
        const response = await (
          await API.themMoiVatTuDK(this.axios, data)
        ).data.data;
        return response;
      } catch (error) {
        console.log(error);
      } finally {
        this.$root.loading(false);
      }
    },
    async deleteFile(fileName) {
      try {
        const rs = await this.$root.context.delete(
          `/web-qlvt/api/minioStorage/deleteFile?fileSource=${fileName}`
        );

        const temp = this.fileNames;

        for (let index = 0; index < this.fileNames.length; index++) {
          const element = this.fileNames[index];

          if (fileName != element) {
            temp.push(element);
          }
        }
        this.fileNames = temp;

        return rs.data.data;
      } catch (error) {
        return "";
      }
    },
    getFileHd: async function (_dangKyId_DSVT) {
      try {
        var rs = await this.$root.context.get(
          `/web-qlvt/api/dang-ky-cap-phat/ds-file-cv-theo-dangky/${_dangKyId_DSVT}`
        );
        // console.log('getFileHd', rs.data)
        console.log(rs);
        return rs.data.split(",") || [];
      } catch (error) {
        return [];
      } finally {
      }
    },
    async capNhatDK(body) {
      try {
        let res = await API.postCapNhatVatTuDK(this.axios, body);
        console.log(res.data);
        if (res.data.error_code == "BSS-00000000") {
          console.log(res.data.data);
          await this.fecthThemMoiVatTuDK2(2, this.dangKyId_DSVT);
          // this.$root.toastSuccess("Nộp đăng ký thành công!");
        }
      } catch (error) {
        console.log(error);
      }
    },
    async btnBC_BB30_Click() {
      if (
        this.congTrinhSelected == "" ||
        this.congTrinhSelected == null ||
        this.congTrinhSelected == 0
      ) {
        this.$toast.error("Chưa có công trình không thể in biên bản này!");
        return;
      }
      if (
        this.dangKyId_DSVT == "" ||
        this.dangKyId_DSVT == null ||
        this.dangKyId_DSVT == 0
      ) {
        this.$toast.error("Chưa có dữ liệu để in biên bản này!");
        return;
      }
      this.loading(true);
      const baseUrl = process.env.API;

      await this.axios({
        url: `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-cap-vat-tu-ct`,
        method: "POST",
        responseType: "blob",
        data: {
          congTrinhId: this.congTrinhSelected,
          dangKyId: this.dangKyId_DSVT,
        },
      }).then((response) => {
        previewPrint(response.data);
      });
      this.loading(false);
    },
    async ToolStripMenuItem_Click() {
      if (
        this.dangKyId_DSVT == "" ||
        this.dangKyId_DSVT == null ||
        this.dangKyId_DSVT == 0
      ) {
        this.$toast.error("Chưa có dữ liệu để in biên bản này!");
        return;
      }
      this.loading(true);
      const baseUrl = process.env.API;

      await this.axios({
        url: `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-dang-ky-vt-doi-vt?extend=pdf`,
        method: "POST",
        responseType: "blob",
        data: {
          dangKyId: this.dangKyId_DSVT,
          phanvung_id : this.tagForm == "4" ? 97 : null
        },
      }).then((response) => {
        previewPrint(response.data);
      });
      this.loading(false);
    },
    async reload_DSVT_MUCDICH() {
      this.cboTimKiem = "";
      await this.checkVatTuCha(true);
      this.$refs.gridDSVTTMDDK.refreshColumns();
    },

    changeNgay() {
      let temp = this.ngayYC.toString().split(" ");
      for (const i of temp) {
        if (
          i == "Mon" ||
          i == "Tue" ||
          i == "Wed" ||
          i == "Thu" ||
          i == "Fri" ||
          i == "Sat" ||
          i == "Sun"
        ) {
          this.ngayYC = moment(this.ngayYC).format("DD/MM/yyyy HH:mm:ss");
          console.log("Đã change = ", this.ngayYC);
        }
      }
    },

    loadTagForm(data) {
      if (Object.values(data) == undefined || Object.values(data) == "") {
        this.tagForm = "0";
      } else if (
        ["1", "2", "3", "4", "5"].includes(Object.values(data).toString())
      ) {
        this.tagForm = Object.values(data).toString();
      } else {
        this.tagForm = "0";
      }
    },

    async searchDSChonDangKy() {
      try {
        var f = this.$refs.frmXemDangKy;
        await f.Open();
      } catch (ex) {
        this.$toast.error("Có lỗi: " + ex);
      }
    },
  },
  directives: {
    ClickOutside,
  },
  provide: {
    treegrid: [Freeze, Edit, Page, Filter, Sort, Resize, Aggregate, Toolbar],
  },
};
</script>
