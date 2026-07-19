<template src='./DonHang.html'></template>
<style scoped src='./DonHang.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import { TreeViewPlugin } from "@syncfusion/ej2-vue-navigations";
import ThanhToanDonHang from "../ThanhToanDonHang/ThanhToanDonHang.vue";
import NghiemThuDonHang from "../NghiemThuDonHang/NghiemThuDonHang.vue";
import TraCuuHopDongVaDonHang from "../TraCuuHopDongVaDonHang/TraCuuHopDongVaDonHang.vue";
// import frmNhapCap from "../frmNhapCap/frmNhapCap.vue";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/donhang";
import {
  Freeze,
  Edit,
  Group,
  Page,
  Filter,
  Sort,
  Resize,
} from "@syncfusion/ej2-vue-grids";
import moment from "moment";
import Vue from "vue";
import { L10n } from "@syncfusion/ej2-base";
import { DatePickerPlugin } from "@syncfusion/ej2-vue-calendars";
import { currency } from "@/filters/currency";
import { Query, Predicate } from "@syncfusion/ej2-data";
import { DropDownListPlugin } from "@syncfusion/ej2-vue-dropdowns";
import { previewPrint } from "../../../utils/util";
import XLSX from "xlsx";
Vue.use(DatePickerPlugin, DropDownListPlugin);
L10n.load({
  "vi-VN": {
    grid: {
      EmptyRecord: "Không có bản ghi nào để hiển thị",
    },
  },
});
Vue.use(TreeViewPlugin);

export default {
  components: {
    TraCuuHopDongVaDonHang,
    NghiemThuDonHang,
    ThanhToanDonHang,
    // frmNhapCap,
    breadcrumb,
    XLSX,
  },
  name: "DonHang",

  provide: {
    grid: [Freeze, Edit, Group, Page, Filter, Sort, Resize],
  },
  data() {
    return {
      header: {
        title: "QUẢN LÝ CÁC ĐƠN HÀNG",
        list: [
          {
            name: "Quản lí vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Quản lý các đơn hàng",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      dateConfig: {
        default: new Date(),
        formatDay: "dd/MM/yyyy",
        waterMark: "Chọn ngày",
      },
      arrayImportFile: {
        filexcel: null,
        ok: false,
        dsImportExcel: [],
        dataExcel: [],
        ds_file: [],
      },
      checkBox: {
        trangThaiDH: false,
        hopDong: false,
        ngayNHTu: false,
        ngayNHDen: false,
        ngayNghiemThuTu: false,
        ngayNghiemThuDen: false,
        trangThaiHopDong: false,
        donHangAo: false,
        hienThiToanBo: false,
      },
      classIconHiddenBox: "-ap icon-chevron-thin-down",
      isVisibleDKLoc: false,
      isFirst: true,
      duan_in: "",
      temp_cha_id: 0,
      hd: -1,
      dh_focus_id: -1,
      hdms_id: -1,
      donHangIdFocus: "",
      donHangId: -1,
      DSDonHangCha: [],
      kieuhd_id: 0,
      giatri_hd_conlai: 0,
      hd_tracuu_id: 0,
      noiDung: "",
      vts_sohoa: "",
      ngay_nk: "",
      taikhoan_ks: "",
      matkhau_ks: "",
      params: {
        ngayNHTu: new Date(),
        ngayNghiemThuTu: new Date(),
        ngayNHDen: new Date(),
        ngayNghiemThuDen: new Date(),
        ngayYeuCau: new Date(),
        ngayNH: "",
        trangThaiDonHang: 0,
        hopDongId: 0,
        khoNhaCCId: 0,
        trangThaiHopDong: 0,
        maHopDong: 0,
        loaiDonhangId: 0,
        ghiChu: "",
        maDonHang: "",
        donHangCha: "",
        trangThaiDHHT: "",
        loaiDH: "",
        duAnId: 0,
        thoiHanGiaoHang: 0,
        thongTinNhanHang: "",
        nguoiLap: "",
        tenKhoNhaCC: "",
        tenDuAn: "",
      },
      valueFilterTree: "",
      showNhaThau: "none",
      showDuAn: "none",
      thongTinNguoiDung: {
        nguoiCN: this.$root.token.getUserName(), //this.$auth.MaNguoiDung
        mayCN: "DESKTOP-IDG67LD", //this.$auth.MAYCN
        ipCN: "10.59.90.128", //this.$auth.MAYCN
        donViId: this.$root.token.getDonViID(), // 882
        phanVungId: this.$root.token.getPhanVungID(),
        nhanVienId: this.$root.token.getNhanVienID(),
        nguoiDungId: this.$root.token.getNguoiDungID(),
      },
      ObjDonViTinh: {},
      dataResponse: {
        DSDonViTinh: [],
        DSTrangThaiDonHang: [],
        DSHopDong: [],
        DSKhoNhaCC: [],
        DSTrangThaiHopDong: [],
        DSHopDongTheoDK: [],
        DSLoaiDonHang: [],
        DSDonHangTheoDieuKien: [],
        DSVatTuDonHang: [],
        DSVatTuHopDong: [],
        DSDonHangCha: [],
        DSDuAn: [],
        DSHD: [],
        DSQuyen: [],
        DSTrangThaiDHHT: [],
      },
      visible: {
        tsbtnExcel: false,
        tsbtnFileMau: false,
      },
      rowSelectedDonHang: [],
      rowSelectedVatTu: [],
      checkAllVT: false,
      checkAllDH: false,
      open_columns: "-1",
      Enabled: {
        nhapMoi: false,
        ghiLai: false,
        huyBo: false,
        Xoa: false,
        nhanHang: false,
        huyNhanHang: false,
        nghiemThu: false,
        thanhToan: false,

        hopDong: true,
        timKiem: false,
        Save: false,
        trangThaiDHHT: false,
        NgayNH: false,
        Insert: false,
        Delete: false,
        nguoiLap: true,
        InPhieu: false,
        mniXoaDH: false,
        Excel: false,
        fileMau: false,

        mniThemDH: false,
        editThanhTien: false,
        editVAT: false,
        editSoLuong: false,
        editDonGia: false,
      },
      paramsRequest: {
        trangThaiDonHang: 0,
        hopDongId: 0,
        ngayNHTu: "",
        ngayNHDen: "",
        ngayNghiemThuTu: "",
        ngayNghiemThuDen: "",
        loaiDH: -1,
        trangThaiHopDong: 0,
      },
      filterOptions: {
        enableCaseSensitivity: false,
        mode: "Immediate",
        ignoreAccent: true,
      },
      onFilteringHD: function (e) {
        var query = new Query();
        let predicate;

        //frame the query based on search string with filter type.
        if (e.text !== "") {
          predicate = new Predicate("MA_HD", "contains", e.text.trim(), true);
          query.where(predicate);
        }

        //pass the filter data source, filter query to updateData method.
        e.updateData(this.dataSource, query);
      },
      onFilteringDH: function (e) {
        var query = new Query();
        let predicate;

        //frame the query based on search string with filter type.
        if (e.text !== "") {
          predicate = new Predicate("MA_DH", "contains", e.text.trim(), true);
          query.where(predicate);
        }

        //pass the filter data source, filter query to updateData method.
        e.updateData(this.dataSource, query);
      },

      fields: {
        dataSource: [],
        id: "DONHANG_ID",
        parentID: "DONHANG_CHA_ID",
        text: "MA_DH",
        hasChildren: "hasChild",
        selected: "isSelected",
      },
      editSettings: { allowEditing: true, mode: "Batch" },
      customTemplate: function () {
        return {
          template: Vue.component("footerTemplate", {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data() {
              return { data: { data: {} } };
            },
          }),
        };
      },
      columnsCheckboxVT: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"                                    
                                    v-model="parent.rowSelectedVatTu"
                                    :value="data.HDVT_ID"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                data: {},
              };
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent.$parent.$parent;
              },
            },
          },
        };
      },
      columnsCheckboxAllVT: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAllVT" class="uncheck"/>
                                <span class="name"></span>
                            </div>
                        `,
            data: function () {
              return {};
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent.$parent.$parent;
              },
            },
          },
        };
      },
      columnsCheckboxAllDH: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAllDH" class="uncheck"/>
                                <span class="name"></span>
                            </div>
                        `,
            data: function () {
              return {};
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent.$parent.$parent;
              },
            },
          },
        };
      },
      columnsCheckboxDH: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"                                    
                                    v-model="parent.rowSelectedDonHang"
                                    :value="data.CTDH_ID"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                data: {},
              };
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent.$parent.$parent;
              },
            },
          },
        };
      },
    };
  },

  mounted: async function () {
    document.addEventListener("click", (event) => {
      if (typeof this.$refs["title-nha-thau"] == "undefined") {
        return;
      }
      if (this.$refs["title-nha-thau"].contains(event.target)) {
        this.showNhaThau = this.showNhaThau == "none" ? "block" : "none";
      } else if (this.$refs["table-nha-thau"].contains(event.target)) {
        if (event.target.tagName.toLowerCase() == "td") {
          this.showNhaThau = "none";
        } else {
          this.showNhaThau = "block";
        }
      } else {
        this.showNhaThau = "none";
      }

      if (this.showNhaThau == "block") {
        this.popper = new Popper(
          this.$refs["title-nha-thau"],
          this.$refs["table-nha-thau"],
          {
            placement: "bottom-start",
            modifiers: [
              {
                name: "offset",
                options: {
                  offset: [0, 50],
                },
              },
            ],
          }
        );
      } else {
        if (this.popper) {
          this.popper.destroy();
        }
        this.popper = null;
      }
    });

    document.addEventListener("click", (event) => {
      if (typeof this.$refs["title-du-an"] == "undefined") {
        return;
      }
      if (this.$refs["title-du-an"].contains(event.target)) {
        this.showDuAn = this.showDuAn == "none" ? "block" : "none";
      } else if (this.$refs["table-du-an"].contains(event.target)) {
        if (event.target.tagName.toLowerCase() == "td") {
          this.showDuAn = "none";
        } else {
          this.showDuAn = "block";
        }
      } else {
        this.showDuAn = "none";
      }

      if (this.showDuAn == "block") {
        this.popper = new Popper(
          this.$refs["title-du-an"],
          this.$refs["table-du-an"],
          {
            placement: "bottom-start",
            modifiers: [
              {
                name: "offset",
                options: {
                  offset: [0, 50],
                },
              },
            ],
          }
        );
      } else {
        if (this.popper) {
          this.popper.destroy();
        }
        this.popper = null;
      }
    });
  },
  watch: {
    
    rowSelectedVatTu: function (value) {
      this.rowSelectedVatTu = value;
      for (const i in this.dataResponse.DSVatTuHopDong) {
        if (
          this.rowSelectedVatTu.includes(
            this.dataResponse.DSVatTuHopDong[i].HDVT_ID
          ) &&
          this.dataResponse.DSVatTuHopDong[i].SL_DONHANG == 0
        ) {
          this.dataResponse.DSVatTuHopDong[i].SL_DONHANG =
            this.dataResponse.DSVatTuHopDong[i].SLAOCL;
        }
      }
      this.$refs.gridVatTu.grid.refreshColumns();
    },
    checkAllVT: function (value) {
      if (value) {
        for (const i in this.dataResponse.DSVatTuHopDong) {
          this.dataResponse.DSVatTuHopDong[i].SL_DONHANG =
            this.dataResponse.DSVatTuHopDong[i].SLAOCL;
          this.rowSelectedVatTu.push(
            this.dataResponse.DSVatTuHopDong[i].HDVT_ID
          );
        }
      } else {
        this.rowSelectedVatTu = [];
      }

      this.$refs.gridVatTu.grid.refreshColumns();
    },
    rowSelectedDonHang: function (value) {
      this.rowSelectedDonHang = value;
    },
    checkAllDH: function (value) {
      if (value) {
        for (const e of this.dataResponse.DSVatTuDonHang) {
          this.rowSelectedDonHang.push(e.CTDH_ID);
        }
      } else {
        this.rowSelectedDonHang = [];
      }
    },
  },
  computed: {
    ...mapState("donhang", statePropertyName),
    ...mapGetters("donhang", getterName),
    PTAggregatesColumns() {
      return [
        {
          field: "SOLUONG",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.getTotalSoLuong,
        },
        {
          field: "SL_NT",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.getTotalSLNT,
        },
        {
          field: "SL_HONG",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.getTotalSLHong,
        },
        {
          field: "SL_CHUAN",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.getTotalSLChuan,
        },
        {
          field: "TIEN",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.getTotalTIEN,
        },
        {
          field: "VAT",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.getTotalVAT,
        },
      ];
    },
  },

  methods: {
    ...mapActions("donhang", actionName),
    ...mapMutations("donhang", mutationName),

    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    f_DateTimeToString: function (value, format = "DD/MM/YYYY HH:mm:ss") {
      return moment(value).format(format);
    },
    getTotalSoLuong() {
      var TongTien = 0;
      this.dataResponse.DSVatTuDonHang.forEach((e) => {
        TongTien += parseFloat(e.SOLUONG);
      });
      return currency(TongTien,"",3);
    },
    getTotalSLNT() {
      var TongTien = 0;
      this.dataResponse.DSVatTuDonHang.forEach((e) => {
        TongTien += parseFloat(e.SL_NT);
      });
      return currency(TongTien,"",3);
    },
    getTotalSLHong() {
      var TongTien = 0;
      this.dataResponse.DSVatTuDonHang.forEach((e) => {
        TongTien += parseFloat(e.SL_HONG);
      });
      return currency(TongTien,"",3);
    },
    getTotalSLChuan() {
      var TongTien = 0;
      this.dataResponse.DSVatTuDonHang.forEach((e) => {
        TongTien += parseFloat(e.SL_CHUAN);
      });
      return currency(TongTien,"",3);
    },
    getTotalTIEN() {
      var TongTien = 0;
      this.dataResponse.DSVatTuDonHang.forEach((e) => {
        TongTien += parseFloat(e.TIEN);
      });
      return currency(TongTien,"",3);
    },
    getTotalVAT() {
      var TongVAT = 0;
      this.dataResponse.DSVatTuDonHang.forEach((e) => {
        TongVAT += parseFloat(e.VAT);
      });
      return currency(TongVAT,"",3);
    },

    async LOAD_COMBOBOX() {
      this.loading(true);
      // Load trạng thái đơn hàng
      this.dataResponse.DSTrangThaiDonHang = await this.getTrangThaiDonHang();
      this.params.trangThaiDonHang =
        this.dataResponse.DSTrangThaiDonHang.length > 0
          ? this.dataResponse.DSTrangThaiDonHang[0].ttdhid
          : 0;

      // Load hợp đồng
      this.dataResponse.DSHopDong = await this.getDSHopDong(
        this.thongTinNguoiDung.donViId
      );
      this.params.hopDongId =
        this.dataResponse.DSHopDong.length > 0
          ? this.dataResponse.DSHopDong[0].HDMS_ID
          : 0;

      // Load DSHD theo C#
      this.dataResponse.DSHD = await this.getDSHopDong(
        this.thongTinNguoiDung.donViId
      );

      // Load Kho nhà cung cấp
      this.dataResponse.DSKhoNhaCC = await this.getDSKhoNhaCungCap(1);
      // this.dataResponse.DSKhoNhaCC = await this.getDSKhoNhaCungCap(4361);

      // Load trạng thái
      this.dataResponse.DSTrangThaiDHHT = await this.getDSTrangThaiDHHT();

      // Load Đơn vị tính
      this.dataResponse.DSDonViTinh = await this.getDSDonViTinh();
      this.dataResponse.DSDonViTinh.forEach((e) => {
        this.ObjDonViTinh[e.dviTinhId] = e.dviTinh;
      });

      // Load trạng tháng hợp đồng
      this.dataResponse.DSTrangThaiHopDong = await this.getTrangThaiHopDong(1);
      this.params.trangThaiHopDong =
        this.dataResponse.DSTrangThaiHopDong.length > 0
          ? this.dataResponse.DSTrangThaiHopDong[0].TTHD_ID
          : 0;

      // Load Hợp đồng đã đăng ký
      this.dataResponse.DSHopDongTheoDK = await this.getDSHopDongTheoDK(
        this.thongTinNguoiDung.donViId
      );
      //push 0 , -1 để ejs-dropdownlist nhận được giá trị khi reset
      this.dataResponse.DSHopDongTheoDK.push(
        {
          HDMS_ID: "0",
          MA_HD: "",
        },
        {
          HDMS_ID: "-1",
          MA_HD: "",
        }
      );

      // Load loại đơn hàng
      this.dataResponse.DSLoaiDonHang = await this.getDSLoaiDonHang();
      this.loading(false);
    },
    async onDonHangSelected(data) {
      try {
        this.loading(true);
        let treeObj = document.getElementById("treeview").ej2_instances[0];
        this.donHangIdFocus = treeObj.selectedNodes[0];
        await this.forcusTree();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    onClickNhapMoi() {
      this.SET_BUTTON(1);

      if (this.dh_focus_id != -1) {
        this.params.donHangCha = this.dh_focus_id;
        this.params.maHopDong = this.hdms_id;
        this.Enabled.hopDong = false;
      } else if (this.hdms_id != -1) {
        this.params.maHopDong = this.hdms_id;
      } else {
        this.params.maHopDong = "0";
      }
      this.onChangeMaHopDong();
      // if(this.params.khoNhaCCId == 0 && this.dataResponse.DSKhoNhaCC.length > 0){
      //   this.params.khoNhaCCId = this.dataResponse.DSKhoNhaCC[0].KHO_ID
      //   this.params.tenKhoNhaCC = this.dataResponse.DSKhoNhaCC[0].TEN_KHO
      // }
    },
    async LOC_TREEVIEW() {
      this.paramsRequest.trangThaiDonHang = this.checkBox.trangThaiDH
        ? this.params.trangThaiDonHang
        : 0;
      this.paramsRequest.hopDongId = this.checkBox.hopDong
        ? this.params.hopDongId
        : 0;
      this.paramsRequest.ngayNHTu = this.checkBox.ngayNHTu
        ? this.f_DateToString(this.params.ngayNHTu)
        : "";
      this.paramsRequest.ngayNHDen = this.checkBox.ngayNHDen
        ? this.f_DateToString(this.params.ngayNHDen)
        : "";
      this.paramsRequest.ngayNghiemThuTu = this.checkBox.ngayNghiemThuTu
        ? this.f_DateToString(this.params.ngayNghiemThuTu)
        : "";
      this.paramsRequest.ngayNghiemThuDen = this.checkBox.ngayNghiemThuDen
        ? this.f_DateToString(this.params.ngayNghiemThuDen)
        : "";
      this.paramsRequest.loaiDH = this.checkBox.donHangAo ? 0 : -1;
      this.paramsRequest.trangThaiHopDong = this.checkBox.trangThaiHopDong
        ? this.params.trangThaiHopDong
        : 0;

      this.dataResponse.DSDonHangTheoDieuKien =
        await this.getDSDonHangTheoDieuKien({
          ten: "Mã HĐ",
          ttdhId: this.paramsRequest.trangThaiDonHang,
          hdmsId: this.paramsRequest.hopDongId,
          dViId: this.thongTinNguoiDung.donViId,
          nhanDhTu: this.paramsRequest.ngayNHTu,
          nhanDhDen: this.paramsRequest.ngayNHDen,
          ntDhTu: this.paramsRequest.ngayNghiemThuTu,
          ntDhDen: this.paramsRequest.ngayNghiemThuDen,
          loaiDh: this.paramsRequest.loaiDH,
          ttdh: this.paramsRequest.trangThaiHopDong,
        });

      if (this.dataResponse.DSDonHangTheoDieuKien.length != 1) {
        this.CLEAR();
        this.createTree(this.dataResponse.DSDonHangTheoDieuKien);
        this.fields = {
          dataSource: this.dataResponse.DSDonHangTheoDieuKien,
          id: "DONHANG_ID",
          parentID: "DONHANG_CHA_ID",
          text: "MA_DH",
          hasChildren: "hasChild",
          selected: "isSelected",
        };
        this.filterTree();
        this.forcusTree();
      } else {
        this.$toast.error("Không tìm thấy kết quả");
        this.CLEAR();
        this.createTree(this.dataResponse.DSDonHangTheoDieuKien);
        this.donHangIdFocus =
          this.dataResponse.DSDonHangTheoDieuKien[0].DONHANG_ID;
        this.fields = {
          dataSource: this.dataResponse.DSDonHangTheoDieuKien,
          id: "DONHANG_ID",
          parentID: "DONHANG_CHA_ID",
          text: "MA_DH",
          hasChildren: "hasChild",
          selected: "isSelected",
        };
      }
      // Đức rem 24.03 giảm thời gian load form
      // this.DSDonHangCha = await this.getDSComboDonHang({
      //   kieu: -1,
      //   ttdhId: this.paramsRequest.trangThaiDonHang,
      //   hdmsId: this.paramsRequest.hopDongId,
      //   dViId: this.thongTinNguoiDung.donViId,
      //   nhanDhTu: this.paramsRequest.ngayNHTu,
      //   nhanDhDen: this.paramsRequest.ngayNHDen,
      //   ntDhTu: this.paramsRequest.ngayNghiemThuTu,
      //   ntDhDen: this.paramsRequest.ngayNghiemThuDen,
      //   loaiDh: this.paramsRequest.loaiDH,
      // });
    },
    async onClickLayTT() {
      try {
        this.loading(true);
        await this.LOAD_COMBOBOX();
        await this.LOC_TREEVIEW();
        await this.getDSDonHangCha();
        this.SET_BUTTON(0);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async forcusTree() {
      var donHangId = this.donHangIdFocus.toString();
      var donHangForcus = this.dataResponse.DSDonHangTheoDieuKien.filter(
        function (el) {
          return el.DONHANG_ID == donHangId;
        }
      );

      if (donHangForcus.length == 0) {
        return;
      }

      var donHangChaId =
        donHangId == "" || donHangId == 0
          ? "-1"
          : donHangForcus[0].DONHANG_CHA_ID;

      await this.getDSDonHangCha();
      if (!this.Enabled.nhapMoi) {
        if (donHangChaId == "-1") {
          this.params.donHangCha = "";
          this.Enabled.hopDong = true;
          this.params.maHopDong = "0";
          this.DSDonHangCha = this.dataResponse.DSDonHangCha;
          this.dh_focus_id = -1;
          this.hd = -1;
        } else if (donHangId.includes("hd")) {
          this.dh_focus_id = -1;
          this.params.donHangCha = "";
          this.Enabled.hopDong = false;
          this.params.maHopDong = donHangForcus[0].HDMS_ID;

          this.DSDonHangCha = this.dataResponse.DSDonHangCha.filter(function (
            el
          ) {
            return (
              el.HDMS_ID == donHangForcus[0].HDMS_ID ||
              el.DONHANG_CHA_ID == "-1"
            );
          });
          if (this.DSDonHangCha.length == 0) {
            this.DSDonHangCha = this.dataResponse.DSDonHangCha;
          }
        } else {
          this.params.donHangCha = donHangId;
          this.Enabled.hopDong = false;
          this.dh_focus_id = -1;
          this.hd = donHangForcus[0].HDMS_ID;
          this.DSDonHangCha = this.dataResponse.DSDonHangCha.filter(function (
            el
          ) {
            return (
              el.HDMS_ID == donHangForcus[0].HDMS_ID ||
              el.DONHANG_CHA_ID == "-1"
            );
          });
          if (this.DSDonHangCha.length == 0) {
            this.DSDonHangCha = this.dataResponse.DSDonHangCha;
          }
        }
      } else {
        if (donHangChaId == "-1") {
          this.SET_BUTTON(0);
          this.Enabled.hopDong = true;
          this.dh_focus_id = -1;
          this.hdms_id = -1;
          this.dataResponse.DSHopDongTheoDK = await this.getDSHopDongTheoDK(
            this.thongTinNguoiDung.donViId
          );
          this.dataResponse.DSHopDongTheoDK.push(
            {
              HDMS_ID: "0",
              MA_HD: "",
            },
            {
              HDMS_ID: "-1",
              MA_HD: "",
            }
          );
          this.DSDonHangCha = this.dataResponse.DSDonHangCha;
          this.visible.tsbtnExcel = false;
          this.visible.tsbtnFileMau = false;
        } else if (donHangId.includes("hd")) {
          this.dh_focus_id = -1;
          this.CLEAR();
          this.Enabled.nhapMoi = true;
          this.Enabled.InPhieu = false;
          this.Enabled.ghiLai = false;
          this.Enabled.huyBo = false;
          this.Enabled.Xoa = false;
          this.Enabled.nghiemThu = false;
          this.Enabled.thanhToan = false;
          this.Enabled.Insert = false;
          this.Enabled.Delete = false;
          this.Enabled.huyNhanHang = false;
          this.Enabled.nhanHang = false;
          this.hdms_id = donHangForcus[0].HDMS_ID;
          this.params.maHopDong = donHangForcus[0].HDMS_ID;
          var DSDonHangChaFilter = this.dataResponse.DSDonHangCha.filter(
            function (el) {
              return (
                el.HDMS_ID == donHangForcus[0].HDMS_ID ||
                el.HDMS_ID == null ||
                el.DONHANG_CHA_ID == "-1"
              );
            }
          );
          if (DSDonHangChaFilter.length > 0) {
            this.DSDonHangCha = DSDonHangChaFilter;
          }

          this.Enabled.hopDong = true;
          if (this.thongTinNguoiDung.phanVungId == 28) {
            this.Enabled.Excel = false;
            this.Enabled.fileMau = false;
            var DSThongTinHopDong = await this.getDSThongTinHopDong(
              this.hdms_id
            );
            if (DSThongTinHopDong.length > 0) {
              console.log(DSThongTinHopDong[0].KIEUHD_ID , " = DSThongTinHopDong[0].KIEUHD_ID")
              this.kieuhd_id = DSThongTinHopDong[0].KIEUHD_ID;
              this.giatri_hd_conlai = parseFloat(DSThongTinHopDong[0].TIEN_CL);
            }
            this.visible.tsbtnExcel = false;
            this.visible.tsbtnFileMau = false;
          }
        } else {
          this.SET_BUTTON(3);
          this.donHangId = donHangId;
          this.dh_focus_id = donHangId;
          var DSDonHangChaFilter = this.dataResponse.DSDonHangCha.filter(
            function (el) {
              return (
                el.HDMS_ID == donHangForcus[0].HDMS_ID ||
                el.DONHANG_CHA_ID == "-1"
              );
            }
          );
          this.$refs.cboDonHangCha.ej2Instances.value = null;

          if (DSDonHangChaFilter.length > 0) {
            this.DSDonHangCha = DSDonHangChaFilter;
          }
          this.CHON_DONHANG(donHangForcus);
          if (this.thongTinNguoiDung.phanVungId == 28) {
            var DSThongTinHopDong = await this.getDSThongTinHopDong(
              this.hdms_id
            );
            console.log(DSThongTinHopDong, " = DSThongTinHopDong");
            if (DSThongTinHopDong.length > 0) {
              this.kieuhd_id = DSThongTinHopDong[0].KIEUHD_ID;
              this.giatri_hd_conlai = parseFloat(DSThongTinHopDong[0].TIEN_CL);
              if (
                this.kieuhd_id == 2 &&
                DSThongTinHopDong[0].MA_MD == "M.DAY"
              ) {
                this.visible.tsbtnExcel = true;
                this.visible.tsbtnFileMau = true;
              } else {
                this.visible.tsbtnExcel = false;
                this.visible.tsbtnFileMau = false;
              }
            }
          }
        }
      }
      this.createTree(this.dataResponse.DSDonHangTheoDieuKien);
    },
    async txtMaDH_KeyPress() {
      try {
        this.loading(true);
        if (this.dataResponse.DSQuyen.includes(2508)) {
          if (this.donHangId <= 0) {
            this.$toast.error("Bạn chưa chọn vào đơn hàng");
            return;
          }

          if (this.params.maDonHang.trim() == "") {
            this.$toast.error("Mã đơn hàng không được để trống");
            return;
          }
          let kiemTraMaDH = await this.kiemTraMaDonHang({
            maDH: this.params.maDonHang.trim(),
          });

          if (kiemTraMaDH.length > 0 && kiemTraMaDH[0] > 0) {
            this.$toast.error("Mã ĐH đã tồn tại trong hệ thống !");
            return;
          }

          let ketQua = await this.capNhatMaDonHang({
            donHangId: this.donHangId,
            maDonHang: this.params.maDonHang.trim(),
            nguoiCn: this.$auth.getUserName(),
            kieu: 1,
          });
          if (ketQua == "1") {
            let donHangFocus = this.donHangId;
            this.$toast.success("Sửa mã đơn hàng thành công !");
            this.SET_BUTTON(3);
            await this.LOC_TREEVIEW();
            await this.getDSDonHangCha();

            if (donHangFocus > 0) {
              this.donHangIdFocus = donHangFocus;
              this.createTree(this.dataResponse.DSDonHangTheoDieuKien);
              this.fields = {
                dataSource: this.dataResponse.DSDonHangTheoDieuKien,
                id: "DONHANG_ID",
                parentID: "DONHANG_CHA_ID",
                text: "MA_DH",
                hasChildren: "hasChild",
                selected: "isSelected",
              };
              this.filterTree();
              await this.forcusTree();
            }
          } else {
            this.$toast.error(ketQua);
          }
        } else {
          this.$toast.error("Bạn không có quyền sửa mã đơn hàng !");
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async saveNhaThau() {
      try {
        this.loading(true);
        if (!this.dataResponse.DSQuyen.includes(2508)) {
          this.$toast.error("Bạn không có quyền sửa mã đơn hàng !");
          return
        } 
        
        if (this.donHangId <= 0) {
          this.$toast.error("Bạn chưa chọn vào đơn hàng");
          return;
        }

        if (this.params.khoNhaCCId == "" || this.params.khoNhaCCId == null || this.params.khoNhaCCId == 0 ) {
          this.$toast.error("Chưa chọn nhà thầu");
          return;
        }
       
        let ketQua = await this.capNhatMaDonHang({
          donHangId: this.donHangId,
          maDonHang: this.params.khoNhaCCId,
          nguoiCn: this.$auth.getUserName(),
          kieu: 2,
        });
        if (ketQua == "1") {
          let donHangFocus = this.donHangId;
          this.$toast.success("Sửa nhà thầu thành công !");
          this.SET_BUTTON(3);
          await this.LOC_TREEVIEW();
          await this.getDSDonHangCha();

          if (donHangFocus > 0) {
            this.donHangIdFocus = donHangFocus;
            this.createTree(this.dataResponse.DSDonHangTheoDieuKien);
            this.fields = {
              dataSource: this.dataResponse.DSDonHangTheoDieuKien,
              id: "DONHANG_ID",
              parentID: "DONHANG_CHA_ID",
              text: "MA_DH",
              hasChildren: "hasChild",
              selected: "isSelected",
            };
            this.filterTree();
            await this.forcusTree();
          }
        } else {
          this.$toast.error(ketQua);
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async CHON_DONHANG(donHangForcus) {
      this.hdms_id = donHangForcus[0].HDMS_ID;
      if (this.hdms_id != -1) {
        this.Enabled.hopDong = false;
        this.params.maHopDong = this.hdms_id.toString();
        this.dataResponse.DSDuAn = await this.getDSDuAn(this.hdms_id);

        if (this.dataResponse.DSDuAn.length > 0) {
          if (donHangForcus[0].DUAN_ID != null) {
            this.params.duAnId = donHangForcus[0].DUAN_ID;
            var duAnFilter = this.dataResponse.DSDuAn.filter(function (el) {
              return el.DUAN_ID == donHangForcus[0].DUAN_ID;
            });
            this.params.tenDuAn =
              duAnFilter.length > 0 ? duAnFilter[0].MA_CT : "";
            this.duan_in =
              duAnFilter.length > 0 ? duAnFilter[0].MA_CT : this.duan_in;
          } else {
            this.params.duAnId = 0;
            this.params.tenDuAn = "";
          }
        } else {
          this.params.duAnId = 0;
          this.params.tenDuAn = "";
        }
      } else {
        this.Enabled.hopDong = true;
      }

      this.params.maDonHang = donHangForcus[0].MA_DH;
      this.params.loaiDonhangId = donHangForcus[0].LOAIDH_ID;
      this.params.thoiHanGiaoHang = donHangForcus[0].HPGH;
      this.params.khoNhaCCId = donHangForcus[0].KHO_ID;
      var DSKhoFocus = this.dataResponse.DSKhoNhaCC.filter(function (el) {
        return el.KHO_ID == donHangForcus[0].KHO_ID;
      });

      this.params.tenKhoNhaCC =
        DSKhoFocus.length > 0 ? DSKhoFocus[0].TEN_KHO : "";
      console.log(donHangForcus[0].NGAY_YC, " = NGAY_YC");
      this.params.ngayYeuCau =
        donHangForcus[0].NGAY_YC == "" || donHangForcus[0].NGAY_YC == null
          ? null
          : new Date(
              this.convertDateTime_DDMMYYYY_to_YYYYMMDD(
                donHangForcus[0].NGAY_YC.replaceAll("/", "-")
              )
            );

      this.params.ngayNH =
        donHangForcus[0].NGAY_NH == "" || donHangForcus[0].NGAY_NH == null
          ? null
          : new Date(
              this.convertDateTime_DDMMYYYY_to_YYYYMMDD(
                donHangForcus[0].NGAY_NH.replaceAll("/", "-")
              )
            );

      if (donHangForcus[0].NGAY_NH != null && donHangForcus[0].NGAY_NH != "") {
        var hanCuoiNhanHang = moment(this.params.ngayYeuCau)
          .add(this.params.thoiHanGiaoHang, "days")
          .endOf("date")
          .toDate();

        var ngayChenhLech =
          (this.params.ngayNH - hanCuoiNhanHang) / (1000 * 3600 * 24);

        if (ngayChenhLech < 0) {
          this.params.thongTinNhanHang =
            "Thời gian nhận hàng sớm " +
            Math.abs(Math.round(ngayChenhLech)).toString() +
            " ngày";
        } else if (ngayChenhLech == 0) {
          this.params.thongTinNhanHang = "Thời gian nhận hàng đúng ngày";
        } else {
          this.params.thongTinNhanHang =
            "Thời gian nhận hàng muộn " +
            Math.abs(Math.round(ngayChenhLech)).toString() +
            " ngày";
        }
      } else {
        this.params.ngayNH = "";
        this.params.thongTinNhanHang = "";
      }
      this.params.ghiChu = donHangForcus[0].GHICHU;
      this.params.trangThaiDHHT = donHangForcus[0].TTDH_ID;

      if (donHangForcus[0].DONHANG_CHA_ID != null) {
        if (
          donHangForcus[0].DONHANG_CHA_ID != "-1" &&
          !donHangForcus[0].DONHANG_CHA_ID.includes("hd")
        ) {
          this.params.donHangCha = donHangForcus[0].DONHANG_CHA_ID;
          this.temp_cha_id = donHangForcus[0].DONHANG_CHA_ID;
        } else {
          this.params.donHangCha = "";
          this.temp_cha_id = 0;
        }
      }

      if (donHangForcus[0].LOAIDH_ID == "2") {
        if (donHangForcus[0].TTDH_ID != "1") {
          if (donHangForcus[0].TTDH_ID == "7") {
            this.Enabled.huyNhanHang = true;
            this.Enabled.nhanHang = false;
          } else {
            this.Enabled.huyNhanHang = false;
            this.Enabled.nhanHang = false;
          }

          this.Enabled.thanhToan = true;
          this.Enabled.nghiemThu = true;
          this.Enabled.Delete = false;
          this.Enabled.Insert = false;
          this.Enabled.InPhieu = true;
        } else {
          this.Enabled.huyNhanHang = false;
          this.Enabled.nhanHang = true;
          this.Enabled.thanhToan = false;
          this.Enabled.nghiemThu = false;
          this.Enabled.Delete = true;
          this.Enabled.Insert = true;
          this.Enabled.InPhieu = true;
        }
      } else {
        this.Enabled.huyNhanHang = false;
        this.Enabled.nhanHang = false;
        this.Enabled.thanhToan = false;
        this.Enabled.nghiemThu = false;
        this.Enabled.Delete = true;
        this.Enabled.Insert = true;
      }

      if (donHangForcus[0].TTDH_ID == "6") {
        this.Enabled.editThanhTien = false;
        this.Enabled.editVAT = false;
        this.Enabled.editSoLuong = false;
        this.Enabled.editDonGia = false;
      } else {
        this.Enabled.editVAT = this.open_columns == "0";
        this.Enabled.editDonGia = false;
        this.Enabled.editSoLuong =
          donHangForcus[0].TTDH_ID == "2" ||
          donHangForcus[0].TTDH_ID == "5" ||
          donHangForcus[0].TTDH_ID == "7"
            ? false
            : true;
      }

      var DSTenNguoiDung = await this.getTenNguoiDungTheoDonHang(
        this.donHangId
      );
      this.params.nguoiLap = DSTenNguoiDung.length > 0 ? DSTenNguoiDung[0] : "";
      await this.NAP_DS_VATTU();
    },
    async NAP_DS_VATTU() {
      var DSVatTuHopDong = await this.getDSVatTuHopDong({
        hdmsId: this.hdms_id,
        donHangId: this.donHangIdFocus,
      });

      if (!this.checkBox.hienThiToanBo) {
        DSVatTuHopDong = DSVatTuHopDong.filter(function (el) {
          return el.SLAOCL != "0";
        });
      }

      DSVatTuHopDong.forEach((e) => {
        e.SLAOCL = e.SLAOCL == "" || e.SLAOCL == null ? 0 : parseFloat(e.SLAOCL);
        e.SOLUONG =
          e.SOLUONG == "" || e.SOLUONG == null ? 0 : parseFloat(e.SOLUONG);
        e.SL_NT = e.SL_NT == "" || e.SL_NT == null ? 0 : parseFloat(e.SL_NT);
        e.SL_KD = e.SL_KD == "" || e.SL_KD == null ? 0 : parseFloat(e.SL_KD);
        e.SL_DONHANG =
          e.SL_DONHANG == "" || e.SL_DONHANG == null
            ? 0
            : parseFloat(e.SL_DONHANG);
        e.DVITINH = this.ObjDonViTinh[e.DVITINH_ID];
      });

      this.dataResponse.DSVatTuHopDong = DSVatTuHopDong;
      this.rowSelectedVatTu = []; // reset lại checkbox
      await this.HandleDSVatTuDonHang();
    },

    convertDateTime_DDMMYYYY_to_YYYYMMDD(datetime) {
      datetime = datetime.split(" ");
      var thisDate = datetime[0].split("-");
      var newDate = [thisDate[2], thisDate[1], thisDate[0]].join("/");
      return newDate;
    },
    createTree(dataTree) {
      var donHangIdFocus = this.donHangIdFocus;
      var donHangFocus = dataTree.filter(function (el) {
        return el.DONHANG_ID == donHangIdFocus;
      });

      var donHangChaIdFocus = "";
      if (donHangFocus.length > 0) {
        if ("DONHANG_CHA_ID" in donHangFocus[0]) {
          donHangChaIdFocus = donHangFocus[0].DONHANG_CHA_ID;
        }
      } else {
        this.donHangIdFocus = "";
      }

      for (const i in dataTree) {
        var donHang = dataTree[i].DONHANG_ID;
        var donHangCha = dataTree[i].DONHANG_CHA_ID;

        var arrChild = dataTree.filter(function (el) {
          return el.DONHANG_CHA_ID == donHang;
        });

        if (arrChild.length > 0) {
          dataTree[i].hasChild = true;
        }

        // expanded vào root, đơn hàng cha hoặc hợp đồng
        dataTree[i].expanded =
          donHang == this.donHangIdFocus ||
          donHang == donHangChaIdFocus ||
          donHang == ""
            ? true
            : false;
        dataTree[i].isSelected = donHang == this.donHangIdFocus ? true : false;

        // remove DONHANG_CHA_ID
        var arrParrent = dataTree.filter(function (el) {
          return el.DONHANG_ID == donHangCha;
        });
        if (arrParrent.length == 0) {
          delete dataTree[i].DONHANG_CHA_ID;
        }
      }
    },

    async onClickTimKiem() {
      this.loading(true);
      this.SET_BUTTON(0);
      this.dataResponse.DSHD = await this.getDSHopDong(
        this.thongTinNguoiDung.donViId
      );
      this.LOC_TREEVIEW();
      this.loading(false);
    },

    async getDSDonHangCha() {
      this.dataResponse.DSDonHangCha = await this.getDSComboDonHang({
        kieu: -1,
        ttdhId: this.paramsRequest.trangThaiDonHang,
        hdmsId: this.paramsRequest.hopDongId,
        dViId: this.thongTinNguoiDung.donViId,
        nhanDhTu: this.paramsRequest.ngayNHTu,
        nhanDhDen: this.paramsRequest.ngayNHDen,
        ntDhTu: this.paramsRequest.ngayNghiemThuTu,
        ntDhDen: this.paramsRequest.ngayNghiemThuDen,
        loaiDh: this.paramsRequest.loaiDH,
      });
    },
    CLEAR() {
      this.params.maHopDong = "0";
      this.params.ghiChu = "";
      this.params.maDonHang = "";
      this.params.ngayNH = "";
      this.params.ngayYeuCau = new Date();
      this.$refs.cboDonHangCha.ej2Instances.value = null;
      this.params.donHangCha = "";
      this.params.trangThaiDHHT = "";
      this.params.loaiDonhangId = "";
      this.params.duAnId = 0;
      this.params.tenDuAn = "";
      (this.params.thoiHanGiaoHang = 0), (this.params.thongTinNhanHang = "");
      this.params.nguoiLap = "";
      this.params.khoNhaCCId = 0;
      this.params.tenKhoNhaCC = "";
      this.dataResponse.DSVatTuDonHang = [];
      this.dataResponse.DSVatTuHopDong = [];
      this.rowSelectedVatTu = []; // reset lại checkbox
      this.kieuhd_id = 0;
      this.giatri_hd_conlai = 0;
    },
    SET_BUTTON(kieu) {
      this.Enabled.nhapMoi = false;
      this.Enabled.ghiLai = false;
      this.Enabled.huyBo = false;
      this.Enabled.Xoa = false;
      this.Enabled.nhanHang = false;
      this.Enabled.huyNhanHang = false;
      this.Enabled.nghiemThu = false;
      this.Enabled.thanhToan = false;

      this.Enabled.timKiem = false;
      this.Enabled.Save = false;
      this.Enabled.trangThaiDHHT = false;
      this.Enabled.NgayNH = false;
      this.Enabled.Insert = false;
      this.Enabled.Delete = false;

      this.Enabled.InPhieu = false;
      this.Enabled.mniXoaDH = false;
      this.Enabled.Excel = false;
      this.Enabled.fileMau = false;

      if (kieu == 0) {
        this.Enabled.hopDong =
          this.dh_focus_id == -1 ? true : this.Enabled.hopDong;
        this.Enabled.nhapMoi = true;
        this.CLEAR();
        this.Enabled.mniThemDH = true;
      } else if (kieu == 1) {
        this.Enabled.timKiem = true;
        this.Enabled.ghiLai = true;
        this.Enabled.huyBo = true;
        this.CLEAR();
        this.params.trangThaiDHHT = 1;
      } else if (kieu == 2) {
        this.Enabled.nhapMoi = true;
        this.Enabled.Xoa = true;
        this.Enabled.mniXoaDH = true;
        this.donHangId = -1;
        this.CLEAR();
        this.Enabled.mniThemDH = true;
      } else if (kieu == 3) {
        this.Enabled.nghiemThu = true;
        this.Enabled.nhapMoi = true;
        this.Enabled.Xoa = true;
        this.Enabled.mniXoaDH = true;
        this.Enabled.ghiLai = true;
        this.Enabled.huyBo = true;
        this.Enabled.Insert = true;
        this.Enabled.Delete = true;
        this.Enabled.Save = true;
        this.Enabled.NgayNH = true;
        this.Enabled.mniThemDH = true;
      } else if (kieu == -1) {
        this.Enabled.NgayNH = true;
        this.Enabled.nhapMoi = true;
        this.Enabled.nghiemThu = true;
        this.Enabled.thanhToan = true;
      }
    },
    async onChangeMaHopDong() {
      if (
        !this.Enabled.nhapMoi &&
        this.params.maHopDong != 0 &&
        this.params.maHopDong != -1
      ) {
        var MaHopDong = this.params.maHopDong;
        var DSHopDongFilter = this.dataResponse.DSHopDongTheoDK.filter(
          function (el) {
            return el.HDMS_ID == MaHopDong;
          }
        );

        if (DSHopDongFilter.length > 0) {
          if (
            DSHopDongFilter[0].KHO_ID != null &&
            DSHopDongFilter[0].KHO_ID != -1 &&
            DSHopDongFilter[0].KHO_ID != 0
          ) {
            var DSKhoFilter = this.dataResponse.DSKhoNhaCC.filter(function (
              el
            ) {
              return el.KHO_ID == DSHopDongFilter[0].KHO_ID;
            });

            if (DSKhoFilter.length > 0) {
              this.params.khoNhaCCId = DSKhoFilter[0].KHO_ID;
              this.params.tenKhoNhaCC = DSKhoFilter[0].TEN_KHO;
            } else {
              this.params.khoNhaCCId = 0;
              this.params.tenKhoNhaCC = "";
            }
            //////////////////////////////////
            //// Dự án tạm rem, bổ sung sau
            // getDSDuAn(this.params.maHopDong)
          } else {
            this.params.khoNhaCCId = 0;
            this.params.tenKhoNhaCC = "";
          }
        } else {
          this.params.khoNhaCCId = 0;
          this.params.tenKhoNhaCC = "";
        }
      }
    },
    async KIEMTRA_NGUOICN() {
      if (this.Enabled.nhapMoi) {
        var DSNguoiCapNhat = await this.getTenNguoiCapNhap(this.donHangId);
        var mess = "";
        if (DSNguoiCapNhat.length == 0) {
          mess += "Không có dữ liệu đơn hàng! \t\n";
        }
        if (DSNguoiCapNhat[0] != this.thongTinNguoiDung.nguoiCN) {
          mess += "Bạn không được cập nhật đơn hàng của người khác! \t\n";
        }
        if (mess != "") {
          this.$toast.error(mess);
          return false;
        }
      }
      return true;
    },
    async KIEMTRA_DULIEU(kieu) {
      var kTNguoiCN = await this.KIEMTRA_NGUOICN();
      if (!kTNguoiCN) {
        this.loading(false);
        return false;
      }
      var mess = "";
      if (
        this.params.maHopDong == 0 ||
        this.params.maHopDong == -1 ||
        this.params.maHopDong == null ||
        this.params.maHopDong == "0"
      ) {
        mess += "Bạn chưa nhập mã hợp đồng! \t\n";
      }
      if (this.params.loaiDonhangId == 0) {
        mess +=
          "Bạn chưa nhập loại đơn hàng. Lưu ý loại đặt đơn hàng số lượng sẽ không trừ và không phải nghiệm thu loại đơn hàng là nhận hàng sẽ làm bình thường \t\n";
      }

      if (this.params.khoNhaCCId == 0) {
        mess += "Bạn chưa nhập nhà thầu \t\n";
      }

      if (this.params.thoiHanGiaoHang == 0) {
        mess += "Bạn chưa nhập thời gian giao hàng \t\n";
      }

      if (!this.Enabled.nhapMoi) {
        var MaHD = this.params.maHopDong;
        var DSHDFiler = this.dataResponse.DSHopDongTheoDK.filter(function (el) {
          return el.TTHD_ID == "1" && el.HDMS_ID == MaHD && MaHD == 0;
        });

        if (DSHDFiler.length) {
          mess += "Hợp đồng này đã hết hạn \t\n";
        }
      }

      if (this.Enabled.nhapMoi && this.Enabled.ghiLai) {
        var DSChiTietDonHang = await this.getDSVatTuDonHang(
          this.donHangIdFocus
        );
        if (DSChiTietDonHang.length == 0) {
          var MaHD = this.params.maHopDong;
          var DSHDFiler = this.dataResponse.DSHopDongTheoDK.filter(function (
            el
          ) {
            return el.TTHD_ID == "1" && el.HDMS_ID == MaHD && MaHD == 0;
          });
          if (DSHDFiler.length) {
            mess += "Hợp đồng này đã hết hạn \t\n";
          }

          if (this.params.ngayNH != "") {
            mess += "Bạn không thể nhận hàng khi chưa có vật tư \t\n";
          }
        }
      }

      var DSNgayKy = await this.getNgayKy(this.params.maHopDong);
      if (DSNgayKy.length > 0) {
        if (this.params.ngayYeuCau < new Date(DSNgayKy[0])) {
          mess +=
            "Ngày yêu cầu nhận hàng phải lớn hơn ngày ký " +
            DSNgayKy[0] +
            " \t\n";
        }
      }

      if (kieu == 1) {
        if (this.Enabled.nhapMoi) {
          var loaiDonHang = await this.getDSLoaiDonHangTheoId(
            this.donHangIdFocus
          );

          if (loaiDonHang.length > 0) {
            if (
              loaiDonHang[0] != this.params.loaiDonhangId &&
              loaiDonHang[0] == 1
            ) {
              var TTDonHangCha = await this.getThongTinDonHangCha(
                this.donHangIdFocus
              );
              if (TTDonHangCha.length > 0) {
                mess +=
                  "Đã có các đơn hàng con bạn không thể chuyển loại đơn hàng \t\n";
              }
            }
          }

          var donHangCha = await this.getDSLoaiDonHangTheoId(this.temp_cha_id);

          if (donHangCha.length > 0) {
            if (
              donHangCha[0] != this.params.loaiDonhangId &&
              donHangCha[0] == 2
            ) {
              mess +=
                "Đã có các đơn hàng cha bạn không thể chuyển loại đơn hàng \t\n";
            }
          }
          var dhCha = "0";
          if (this.params.donHangCha != "") {
            dhCha = this.params.donHangCha;
            if (this.temp_cha_id != dhCha) {
              mess += "Không được chỉnh sửa đơn hàng cha \t\n";
            }
          }
        } else {
          if (this.params.donHangCha != "") {
            if (this.params.loaiDonhangId != "2") {
              mess +=
                "Đơn hàng con chỉ có thể chọn loại đơn hàng là đơn hàng nhận \t\n";
            }
          }
        }
      }
      if (mess != "") {
        this.$toast.error(mess);
        this.loading(false);
        return false;
      }
      return true;
    },
    async TAO_DULIEU(themmoi, kieu) {
      // Đơn hàng cha truyền "" thay vì -1 để DB nhận giá trị null
      var dh_cha_id_t = "";
      var duan_id = 0;
      var ngay_nh_t = this.f_DateTimeToString(new Date());
      if (themmoi) {
        this.donHangId = await this.getKey({
          keyName: "DONHANG_ID",
          numBlockSize: 0,
          numRetry: 0,
        });
      }

      var ma_dh_t = "";
      if (this.params.maDonHang != "") {
        ma_dh_t = this.params.maDonHang;
      } else {
        ma_dh_t = await this.getMaDonHang(this.params.maHopDong);
      }

      if (this.params.duAnId !== undefined && this.params.duAnId != 0) {
        duan_id = this.params.duAnId;
      }

      var getTime = ngay_nh_t.split(" ");
      if (this.params.ngayNH != "" && this.params.ngayNH != null) {
        var ngayNH = this.f_DateToString(this.params.ngayNH);
        ngay_nh_t = ngayNH + " " + getTime[1];
      }

      dh_cha_id_t =
        this.params.donHangCha != "" && this.params.donHangCha != null
          ? this.params.donHangCha.replace("hd-", "")
          : dh_cha_id_t;
      var ngayYeuCau =
        this.f_DateToString(this.params.ngayYeuCau) + " " + getTime[1];

      var kq = await this.themMoiDonHang({
        donHangId: this.donHangId,
        dongHangChaId: dh_cha_id_t,
        hdmsId: this.params.maHopDong,
        ngayYC: ngayYeuCau,
        maDH: ma_dh_t,
        ttdhId: this.params.trangThaiDHHT,
        ngayNH: ngay_nh_t,
        nhanVienId: this.thongTinNguoiDung.nhanVienId,
        donViId: this.thongTinNguoiDung.donViId,
        ghiChu: this.params.ghiChu,
        nguoiCN: this.thongTinNguoiDung.nguoiCN,
        ngayCN: this.f_DateTimeToString(new Date()),
        mayCN: this.thongTinNguoiDung.mayCN,
        ipCN: this.thongTinNguoiDung.ipCN,
        hpgh: this.params.thoiHanGiaoHang,
        kieu: kieu,
        khoId: this.params.khoNhaCCId,
        duAnId: duan_id,
        loaiDhId: this.params.loaiDonhangId,
      });
    },
    async HandleDSVatTuDonHang() {
      var DSVatTuDonHang = await this.getDSVatTuDonHang(this.donHangId);
      DSVatTuDonHang.forEach((e) => {
        e.SOLUONG =
          e.SOLUONG == "" || e.SOLUONG == null ? 0 : parseFloat(e.SOLUONG);
        e.SL_NT = e.SL_NT == "" || e.SL_NT == null ? 0 : parseFloat(e.SL_NT);
        e.SL_HONG =
          e.SL_HONG == "" || e.SL_HONG == null ? 0 : parseFloat(e.SL_HONG);
        e.SL_CHUAN =
          e.SL_CHUAN == "" || e.SL_CHUAN == null ? 0 : parseFloat(e.SL_CHUAN);
        e.DONGIA = e.DONGIA == "" || e.DONGIA == null ? 0 : parseFloat(e.DONGIA);
        e.TIEN = e.TIEN == "" || e.SOLUONG == null ? 0 : parseFloat(e.TIEN);
        e.VAT = e.VAT == "" || e.VAT == null ? 0 : parseFloat(e.VAT);
        e.DVITINH = this.ObjDonViTinh[e.DVITINH_ID];
      });
      this.dataResponse.DSVatTuDonHang = DSVatTuDonHang;
    },
    async onClickRefresh() {
      if (this.Enabled.nhapMoi && this.Enabled.ghiLai) {
        this.loading(true);
        await this.HandleDSVatTuDonHang();
        this.loading(false);
      }
    },
    async onClickSave() {
      var donHangId = this.donHangIdFocus.toString();
      var donHangForcus = this.dataResponse.DSDonHangTheoDieuKien.filter(
        function (el) {
          return el.DONHANG_ID == donHangId;
        }
      );
      if (
        this.Enabled.nhapMoi &&
        this.Enabled.ghiLai &&
        donHangForcus[0].TTDH_ID != "6" &&
        donHangForcus[0].TTDH_ID != "2"
      ) {
        var donHangUpdate = this.dataResponse.DSVatTuDonHang.filter(function (
          el
        ) {
          return el.ISUPDATE == "1";
        });
        if (donHangUpdate.length == 0) {
          this.$toast.error("Không có dữ liệu thay đổi để cập nhật");
          return;
        }

        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm("Bạn có muốn cập nhật dữ liệu không ?", {
            title: "Thông báo",
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
        this.loading(true);
        var listUpdateChiTietDHIn = [];
        donHangUpdate.forEach((e) => {
          listUpdateChiTietDHIn.push({
            ctdhId: e.CTDH_ID,
            soLuong: e.SOLUONG,
            donGia: e.DONGIA,
            tien: e.TIEN,
            vat: e.VAT,
            type: "chitiet",
          });
        });
        await this.updateChiTietDonHang({
          listUpdateChiTietDHIn: listUpdateChiTietDHIn,
        });
        await this.NAP_DS_VATTU();
        this.$toast.success("Cập nhật dữ liệu thành công");
        this.loading(false);
      } else {
        this.$toast.error("Trạng thái đơn hàng không cho phép sửa");
      }
    },
    async onClickGhiLai() {
      try {
        this.loading(true);
        var temp_id = 0;
        var kiemTraDuLieu = await this.KIEMTRA_DULIEU(1);
        if (kiemTraDuLieu) {
          if (!this.Enabled.nhapMoi) {
            await this.TAO_DULIEU(true, 1);
            temp_id = this.donHangId;
          } else {
            var DSTHId = await this.getTTDonHangTheoDHId(this.donHangId);
            if (DSTHId.length > 0) {
              if (DSTHId[0] != "1") {
                this.$toast.error("Đơn hàng này không thể cập nhật dữ liệu");
                this.loading(false);
                return;
              }
              await this.TAO_DULIEU(false, 2);
              temp_id = this.donHangId;
            }
          }

          await this.capNhatTrangThaiNTDonHang({
            donHangId: this.donHangId,
            hdmsId: this.hdms_id,
            nhanVienId: this.thongTinNguoiDung.nhanVienId,
          });
          if (temp_id != 0) {
            this.SET_BUTTON(3);
            await this.LOC_TREEVIEW();
            await this.getDSDonHangCha();
            var newFocus = this.dataResponse.DSDonHangTheoDieuKien.filter(
              function (el) {
                return el.DONHANG_ID == temp_id;
              }
            );
            if (newFocus.length > 0) {
              this.donHangIdFocus = temp_id;
              this.createTree(this.dataResponse.DSDonHangTheoDieuKien);
              this.fields = {
                dataSource: this.dataResponse.DSDonHangTheoDieuKien,
                id: "DONHANG_ID",
                parentID: "DONHANG_CHA_ID",
                text: "MA_DH",
                hasChildren: "hasChild",
                selected: "isSelected",
              };
              this.filterTree();
              await this.forcusTree();
            }
            this.$toast.success("Cập nhật dữ liệu đăng ký thành công !");
          }
        }
        this.loading(false);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },

    async onClickHuyBo() {
      try {
        this.loading(true);
        this.SET_BUTTON(0);
        this.hdms_id = -1;
        this.donHangIdFocus = "";
        this.createTree(this.dataResponse.DSDonHangTheoDieuKien);
        this.fields = {
          dataSource: this.dataResponse.DSDonHangTheoDieuKien,
          id: "DONHANG_ID",
          parentID: "DONHANG_CHA_ID",
          text: "MA_DH",
          hasChildren: "hasChild",
          selected: "isSelected",
        };
        this.filterTree();
        await this.forcusTree();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },

    async onClickTraCuuHDDH() {
      this.loading(true);
      let f = this.$refs.frmTraCuuHopDongDonHang;
      f.resetParentForm();
      if (this.thongTinNguoiDung.phanVungId == 28) {
        f.params.p_ckb_tracuudonhang = false;
        f.enable.cboDviDK = true;
      } else {
        f.params.p_ckb_tracuudonhang = true;
        f.readonly.ckbHDTimKiem = true;
        f.params.p_ckb_donvi_dk = true;
        f.params.p_ckb_trangthai_hd = true;
        f.enable.cboTrangThaiHD = false;
        f.params.p_ckb_hd_dat = true;
        f.enable.p_ckb_hd_dat = false;
      }
      f.params.p_ckb_tracuuhopdong = true;
      f.params.p_ckb_donvi_dk = true;
      f.params.p_ckb_trangthai_hd = true;
      f.params.p_ckb_hd_dat = true;
      f.params.p_donvi_dk = this.thongTinNguoiDung.donViId;
      f._owner_dh = true;
      await f.frmTraCuuHopDong();
      this.loading(false);
      this.$refs.popupTraCuuHopDongDonHang.show();
    },
    async Close_popup_frmTraCuuHopDong() {
      var frm = this.$refs.frmTraCuuHopDongDonHang;
      if (frm.chapnhan) {
        this.params.maHopDong = frm.hdms_id.toString() || "0";
      }
    },
    async onClickNghiemThu() {
      try {
        this.loading(true);
        var donHangId = this.donHangIdFocus;
        var donHangForcus = this.dataResponse.DSDonHangTheoDieuKien.filter(
          function (el) {
            return el.DONHANG_ID == donHangId;
          }
        );

        let f = this.$refs.frmNghiemThuDonHang;
        f.checkAllVT = false
        f.rowCheckedCTDH = []
        f.params.donHangId = donHangId;
        f.params.maDonHang = donHangForcus[0].MA_DH;
        var listNgayNghiemThu = await this.getNgayNghiemThuNhapKho(
          this.donHangId
        );

        f.params.ngayNhanHang = new Date(
          this.convertDateTime_DDMMYYYY_to_YYYYMMDD(
            donHangForcus[0].NGAY_NH.replaceAll("/", "-")
          )
        );
        f.params.soHoaDon = donHangForcus[0].SO_HD;

        if (listNgayNghiemThu.length > 0) {
          if (listNgayNghiemThu[0] != null) {
            if (listNgayNghiemThu[0].ngayNT != null) {
              f.params.ngayNghiemThu = listNgayNghiemThu[0].ngayNT;
              f.params.ngayNghiemThuGoc = listNgayNghiemThu[0].ngayNT;
              if (listNgayNghiemThu[0].ngayNK != null) {
                f.params.ngayNhapKho = listNgayNghiemThu[0].ngayNK;
              } else {
                f.params.ngayNhapKho = "";
              }
            } else {
              f.params.ngayNghiemThu = "";
              f.params.ngayNhapKho = "";
            }
          } else {
            f.params.ngayNghiemThu = "";
            f.params.ngayNhapKho = "";
          }
        }
        f.params.hdms_id = this.hdms_id;
        f.params.trangThaiDHHT = this.params.trangThaiDHHT;
        f.params.dsQuyen = this.dataResponse.DSQuyen;
        await f.Open();
        this.loading(false);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async closeNghiemThuDH() {
      try {
        this.loading(true);
        var donHangId = this.donHangIdFocus;
        let frmNghiemThuDonHang = this.$refs.frmNghiemThuDonHang;
        if (frmNghiemThuDonHang.ghi) {
          if (donHangId != 0) {
            this.SET_BUTTON(3);
            this.LOC_TREEVIEW();
            var newFocus = this.dataResponse.DSDonHangTheoDieuKien.filter(
              function (el) {
                return el.DONHANG_ID == donHangId;
              }
            );

            if (newFocus.length > 0) {
              this.donHangIdFocus = donHangId;
              this.createTree(this.dataResponse.DSDonHangTheoDieuKien);
              this.fields = {
                dataSource: this.dataResponse.DSDonHangTheoDieuKien,
                id: "DONHANG_ID",
                parentID: "DONHANG_CHA_ID",
                text: "MA_DH",
                hasChildren: "hasChild",
                selected: "isSelected",
              };
              this.filterTree();
              await this.forcusTree();
            }
          }
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },

    async onClickThanhToan() {
      var donHangId = this.donHangIdFocus;
      var donHangForcus = this.dataResponse.DSDonHangTheoDieuKien.filter(
        function (el) {
          return el.DONHANG_ID == donHangId;
        }
      );

      if (!"2,5,6".includes(donHangForcus[0].TTDH_ID)) {
        this.$toast.error(
          "Đơn hàng cần phải được nghiệm thu trước khi thanh toán"
        );
        return;
      }

      //this.$refs.popupThanhToanDonHang.show()
      let frmThanhToanDonHang = this.$refs.frmThanhToanDonHang;
      frmThanhToanDonHang.donhang_id = this.donHangId;
      var listNgayNghiemThu = await this.getNgayNghiemThuNhapKho(
        this.donHangId
      );

      frmThanhToanDonHang.params.p_ngay_tt = new Date(
        listNgayNghiemThu[0].ngayNK
      );

      await frmThanhToanDonHang.Open();
    },
    async closeThanhToanDonHang() {
      var temp_id = this.donHangId;
      let frmThanhToanDonHang = this.$refs.frmThanhToanDonHang;
      if (frmThanhToanDonHang.dongy) {
        if (temp_id != 0) {
          this.SET_BUTTON(3);
          this.LOC_TREEVIEW();
          var newFocus = this.dataResponse.DSDonHangTheoDieuKien.filter(
            function (el) {
              return el.DONHANG_ID == temp_id;
            }
          );

          if (newFocus.length > 0) {
            this.donHangIdFocus = temp_id;
            this.createTree(this.dataResponse.DSDonHangTheoDieuKien);
            this.fields = {
              dataSource: this.dataResponse.DSDonHangTheoDieuKien,
              id: "DONHANG_ID",
              parentID: "DONHANG_CHA_ID",
              text: "MA_DH",
              hasChildren: "hasChild",
              selected: "isSelected",
            };
            this.filterTree();
            await this.forcusTree();
          }
        } else {
          this.LOC_TREEVIEW();
        }
      }
    },
    async onClickXoa() {
      try {
        this.loading(true);

        var kTNguoiCN = await this.KIEMTRA_NGUOICN();
        if (!kTNguoiCN) {
          this.loading(false);
          return false;
        }
        var DSVatTu = await this.getDSVatTuTuDonHangTT(this.donHangId);
        if (DSVatTu.length != 0) {
          this.$toast.error("Bạn phải xóa vật tư trong chi tiết đơn hàng");
          this.loading(false);
          return;
        }
        this.loading(false);
        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm("Bạn có muốn xóa dữ liệu không ?", {
            title: "Thông báo",
            centered: true,
            size: "md",
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
        this.loading(true);
        await this.xoaDonHang(this.donHangId);

        var t_cha = 0;
        if (this.temp_cha_id != 0) {
          t_cha = this.temp_cha_id;
        }
        this.$toast.success("Xóa dữ liệu đơn hàng thành công !");
        this.SET_BUTTON(0);
        if (t_cha != 0) {
          this.SET_BUTTON(3);
          await this.LOC_TREEVIEW();
          var newFocus = this.dataResponse.DSDonHangTheoDieuKien.filter(
            function (el) {
              return el.DONHANG_ID == t_cha;
            }
          );

          if (newFocus.length > 0) {
            this.donHangIdFocus = t_cha;
            this.createTree(this.dataResponse.DSDonHangTheoDieuKien);
            this.fields = {
              dataSource: this.dataResponse.DSDonHangTheoDieuKien,
              id: "DONHANG_ID",
              parentID: "DONHANG_CHA_ID",
              text: "MA_DH",
              hasChildren: "hasChild",
              selected: "isSelected",
            };
            this.filterTree();
            await this.forcusTree();
          }
        } else {
          this.SET_BUTTON(0);
          await this.LOC_TREEVIEW();
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },

    async onClickNhanHang() {
      try {
        var temp_id = 0;
        if (this.params.ngayNH == "") {
          this.$toast.error("Bạn chưa chọn ngày nhận hàng");
          return;
        }
        if (this.params.ngayNH < this.params.ngayYeuCau) {
          this.$toast.error("Ngày nhận hàng phải lớn hơn ngày yêu cầu");
          return;
        }
        this.loading(true);
        var kiemTraDuLieu = await this.KIEMTRA_DULIEU(2);
        if (kiemTraDuLieu) {
          await this.TAO_DULIEU(false, 3);
          this.$toast.success("Xác nhận đơn hàng thành công !");
          temp_id = this.donHangId;

          await this.capNhatTrangThaiNTDonHang({
            donHangId: this.donHangId,
            hdmsId: this.hdms_id,
            nhanVienId: this.thongTinNguoiDung.nhanVienId,
          });

          if (temp_id != 0) {
            this.SET_BUTTON(3);
            await this.LOC_TREEVIEW();
            await this.getDSDonHangCha();
            var newFocus = this.dataResponse.DSDonHangTheoDieuKien.filter(
              function (el) {
                return el.DONHANG_ID == temp_id;
              }
            );

            if (newFocus.length > 0) {
              this.donHangIdFocus = temp_id;
              this.createTree(this.dataResponse.DSDonHangTheoDieuKien);
              this.fields = {
                dataSource: this.dataResponse.DSDonHangTheoDieuKien,
                id: "DONHANG_ID",
                parentID: "DONHANG_CHA_ID",
                text: "MA_DH",
                hasChildren: "hasChild",
                selected: "isSelected",
              };
              this.filterTree();
              await this.forcusTree();
            }
          }
        }
        this.loading(false);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async onClickInsert() {
      try {
        if (this.rowSelectedVatTu.length == 0) {
          this.$toast.error("Hợp đồng không có danh sách vật tư được chọn");
          return;
        }
        this.loading(true);
        var LoaiDHVaTrangThaiDH = await this.getLoaiDHVaTrangThaiDH(
          this.donHangId
        );

        if (LoaiDHVaTrangThaiDH.length > 0) {
          if (LoaiDHVaTrangThaiDH[0].loaiDHId == 1) {
            var ChiTietDonHangCha = await this.getChiTietDHCha(this.donHangId);
            if (ChiTietDonHangCha.length > 0) {
              this.$toast.error(
                "Đã có các đơn hàng con bạn không thể thay đổi thông tin vật tư"
              );
              this.loading(false);
              return;
            }
          }

          if (LoaiDHVaTrangThaiDH[0].ttdhId != 1) {
            this.$toast.error(
              "Trạng thái đơn hàng đã bị thay đổi bạn không thể thêm vật tư"
            );
            this.loading(false);
            return;
          }
        }

        var listVattuCheckBox = this.rowSelectedVatTu;
        let rowSelectedVatTu = this.dataResponse.DSVatTuHopDong.filter(
          function (el) {
            return listVattuCheckBox.includes(el.HDVT_ID);
          }
        );

        if (rowSelectedVatTu.length > 0) {
          console.log(this.kieuhd_id , " = kieuhd_id")
          if (this.kieuhd_id == 2) { // theo giá trị
            var giaTri = 0;
            rowSelectedVatTu.forEach((element) => {
              giaTri = giaTri + parseFloat(element.DONGIA) * parseFloat(element.SL_DONHANG);
            });
            var DSThongTinHopDong = await this.getDSThongTinHopDong(
              this.hdms_id
            );
            if (DSThongTinHopDong.length > 0) {
              this.giatri_hd_conlai = parseFloat(DSThongTinHopDong[0].TIEN_CL);
            }           
            if (giaTri > this.giatri_hd_conlai) {
              this.$toast.error(
                "Giá trị hợp đồng không còn đủ để tạo hợp đồng. Giá trị hợp đồng còn lại : " +
                  this.giatri_hd_conlai
              );
              this.loading(false);
              return;
            }
          } else { // theo số lượng
            var giaTri = 0;
            rowSelectedVatTu.forEach((element) => {
              if (element.SL_DONHANG != 0 && element.SLAOCL != 0) {
                if (element.SL_DONHANG > element.SLAOCL) {
                  this.$toast.error(
                    "Số lượng đơn hàng phải nhỏ hơn bằng số lượng tồn mã vật tư : " +
                      element.MA_VT
                  );
                  this.loading(false);
                  return;
                }
              } else if (element.SL_DONHANG == 0) {
                this.$toast.error(
                  "Số lượng đơn đặt không được bằng 0  mã vật tư " +
                    element.MA_VT
                );
                this.loading(false);
                return;
              } else if (element.SLAOCL == 0) {
                this.$toast.error(
                  "Số lượng tồn đã hết mã vật tư " + element.MA_VT
                );
                this.loading(false);
                return;
              }
            });
          }
          var listBackupCTDHIn = [];
          var listUpdateSLCTDHIn = [];
          rowSelectedVatTu.forEach((element) => {
            var VatTuTemp = this.dataResponse.DSVatTuDonHang.filter(function (
              el
            ) {
              return el.VATTU_ID == element.VATTU_ID;
            });

            if (VatTuTemp.length > 0) {
              var soLuong = 0;
              soLuong =
                element.SL_DONHANG != 0 ? element.SL_DONHANG : element.SLAOCL;
              soLuong = soLuong + VatTuTemp[0].SOLUONG;

              listUpdateSLCTDHIn.push({
                soLuong: soLuong,
                ctdhId: VatTuTemp[0].CTDH_ID,
              });
            } else {
              var soLuong = 0;
              soLuong =
                element.SL_DONHANG != 0 ? element.SL_DONHANG : element.SLAOCL;
              listBackupCTDHIn.push({
                donHangId: this.donHangId,
                vatTuId: element.VATTU_ID,
                soLuong: soLuong,
                donGia: element.DONGIA,
                tien: 0,
                vat: 0,
                hdvtId: element.HDVT_ID,
                slNt: 0,
                slHong: 0,
                slChuan: 0,
              });
            }
          });

          if (listBackupCTDHIn.length > 0) {
            await this.insertBackupDonHang({
              listBackupCTDHIn: listBackupCTDHIn,
            });
          }
          if (listUpdateSLCTDHIn.length > 0) {
            await this.updateBackupDonHang({
              listUpdateSLCTDHIn: listUpdateSLCTDHIn,
            });
          }
          await this.NAP_DS_VATTU();
          this.rowSelectedVatTu = []
          this.checkAllVT = false
          this.rowSelectedDonHang = []
          this.checkAllDH = false
          
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async onClickDelete() {
      try {
        if (this.rowSelectedDonHang.length == 0) {
          this.$toast.error("Đơn hàng không có danh sách vật tư được chọn");
          return;
        }
        this.loading(true);
        var LoaiDHVaTrangThaiDH = await this.getLoaiDHVaTrangThaiDH(
          this.donHangId
        );
        if (LoaiDHVaTrangThaiDH.length > 0) {
          if (LoaiDHVaTrangThaiDH[0].loaiDHId == 1) {
            var ChiTietDonHangCha = await this.getChiTietDHCha(this.donHangId);
            if (ChiTietDonHangCha.length > 0) {
              this.$toast.error(
                "Đã có các đơn hàng con bạn không thể thay đổi thông tin vật tư"
              );
              this.loading(false);
              return;
            }
          }

          if (LoaiDHVaTrangThaiDH[0].ttdhId != 1) {
            this.$toast.error(
              "Trạng thái đơn hàng đã bị thay đổi bạn không thể thêm vật tư"
            );
            this.loading(false);
            return;
          }
        }

        var listCTDHIDIn = [];
        this.rowSelectedDonHang.forEach((e) => {
          listCTDHIDIn.push(e);
        });
        await this.xoaChiTietDonHang({ listCTDHIDIn: listCTDHIDIn });
        await this.NAP_DS_VATTU();
        this.rowSelectedVatTu = []
        this.checkAllVT = false
        this.rowSelectedDonHang = []
        this.checkAllDH = false
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },

    async onClickHuyNhanHang() {
      try {
        this.loading(true);

        var temp_id = 0;
        await this.TAO_DULIEU(false, 4);
        this.$toast.success("Hủy xác nhận thành công !");
        temp_id = this.donHangId;

        await this.capNhatTrangThaiNTDonHang({
          donHangId: this.donHangId,
          hdmsId: this.hdms_id,
          nhanVienId: this.thongTinNguoiDung.nhanVienId,
        });

        if (temp_id != 0) {
          this.SET_BUTTON(3);
          await this.LOC_TREEVIEW();
          await this.getDSDonHangCha();
          var newFocus = this.dataResponse.DSDonHangTheoDieuKien.filter(
            function (el) {
              return el.DONHANG_ID == temp_id;
            }
          );

          if (newFocus.length > 0) {
            this.donHangIdFocus = temp_id;
            this.createTree(this.dataResponse.DSDonHangTheoDieuKien);
            this.fields = {
              dataSource: this.dataResponse.DSDonHangTheoDieuKien,
              id: "DONHANG_ID",
              parentID: "DONHANG_CHA_ID",
              text: "MA_DH",
              hasChildren: "hasChild",
              selected: "isSelected",
            };
            this.filterTree();
            await this.forcusTree();
          }
        }

        this.loading(false);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async frmDonHang_Load() {
      this.loading(true);
      this.dataResponse.DSQuyen = await this.getDSQuyen(
        this.thongTinNguoiDung.nguoiDungId
      );
      var DSThamSoMacDinh = await this.getThamSoMacDinh(0);

      if (DSThamSoMacDinh.length > 0) {
        var DSThamSo = DSThamSoMacDinh.filter(function (el) {
          return el.maTS == "KHOA_TT";
        });

        if (DSThamSo.length > 0) {
          if (DSThamSo[0].tenTS.trim() == "0") {
            this.open_columns = "0";
            this.Enabled.editThanhTien = true;
            this.Enabled.editVAT = true;
          }
        }
      }
      this.Enabled.nguoiLap =
        this.thongTinNguoiDung.phanVungId == 28 ? true : false;
      this.loading(false);
    },
    onClickHiddenDKLoc() {
      this.isVisibleDKLoc = !this.isVisibleDKLoc;
      this.classIconHiddenBox = this.isVisibleDKLoc
        ? "-ap icon-chevron-thin-up"
        : "-ap icon-chevron-thin-down";
    },
    async onChangeNhaThau(row) {
      this.params.tenKhoNhaCC = row.TEN_KHO;
      this.params.khoNhaCCId = row.KHO_ID;
    },
    async onChangeDuAn(row) {
      this.params.tenDuAn = row.MA_CT == null ? "" : row.MA_CT;
      this.params.duAnId = row.DUAN_ID;
    },
    async filterTree() {
      var valueFilterTree = this.valueFilterTree.trim();
      var treeFilter = [];

      if (valueFilterTree == "") {
        this.fields = {
          dataSource: this.dataResponse.DSDonHangTheoDieuKien,
          id: "DONHANG_ID",
          parentID: "DONHANG_CHA_ID",
          text: "MA_DH",
          hasChildren: "hasChild",
          selected: "isSelected",
        };
      } else {
        treeFilter.push(this.dataResponse.DSDonHangTheoDieuKien[0]);

        var DSNodeFilter = this.dataResponse.DSDonHangTheoDieuKien.filter(
          function (el) {
            return (
              el.MA_DH.toLowerCase().includes(valueFilterTree.toLowerCase()) &&
              el.MA_DH != "Root"
            );
          }
        );

        DSNodeFilter.forEach((e) => {
          // e.isSelected = true
          e.expanded = true;
          if ("hasChild" in e) {
            var DONHANG_ID = e.DONHANG_ID;
            var DSNodeExist = treeFilter.filter(function (el) {
              return el.DONHANG_ID == DONHANG_ID;
            });
            // Node cha chưa tồn tại thì thêm vào
            if (DSNodeExist.length == 0) {
              treeFilter.push(e);
            }
            // Thêm Node con
            var DSNodeChild = this.dataResponse.DSDonHangTheoDieuKien.filter(
              function (el) {
                return el.DONHANG_CHA_ID == DONHANG_ID;
              }
            );

            DSNodeChild.forEach((p) => {
              var DONHANG_ID = p.DONHANG_ID;
              var DSNodeExist = treeFilter.filter(function (el) {
                return el.DONHANG_ID == DONHANG_ID;
              });
              if (DSNodeExist.length == 0) {
                treeFilter.push(p);
              }
            });
          } else {
            var DONHANG_CHA_ID = e.DONHANG_CHA_ID;
            //không có node cha
            if (DONHANG_CHA_ID == undefined) {
              treeFilter.push(e);
            } else {
              // kiểm tra node cha đã thêm vào treeFilter chưa
              var DSNodeParrent =
                this.dataResponse.DSDonHangTheoDieuKien.filter(function (el) {
                  return el.DONHANG_ID == DONHANG_CHA_ID;
                });
              var DSNodeParrentExist = treeFilter.filter(function (el) {
                return el.DONHANG_ID == DONHANG_CHA_ID;
              });

              if (DSNodeParrentExist.length == 0) {
                DSNodeParrent.forEach((p) => {
                  if (
                    !p.MA_DH.toLowerCase().includes(
                      valueFilterTree.toLowerCase()
                    )
                  ) {
                    treeFilter.push(e);
                    p.expanded = true;
                    treeFilter.push(p);
                  }
                });
              }else{
                //check đơn hàng cùng cha nhưng chưa thêm vào
                let CheckDonHang = treeFilter.filter(function (el) {
                  return el.DONHANG_ID == e.DONHANG_ID;
                });
                if(CheckDonHang.length == 0){
                  treeFilter.push(e);
                }                
              }
            }
          }
        });
        this.fields = {
          dataSource: treeFilter,
          id: "DONHANG_ID",
          parentID: "DONHANG_CHA_ID",
          text: "MA_DH",
          hasChildren: "hasChild",
          selected: "isSelected",
        };
      }
    },

    rowsCheckboxDonHang: function (dataItem) {
      this.rowSelectedDonHang = dataItem;
    },
    rowsCheckboxVatTu: function (dataItem) {
      // this.rowSelectedVatTu = dataItem
    },
    async editGridDonHang(arg) {
      let gridDonHang = this.$refs.gridDonHang;
      let changes = gridDonHang.getBatchChanges();
      if (changes.changedRecords.length > 0) {
        if (arg.columnName == "SOLUONG") {
          if (changes.changedRecords[0].SOLUONG < 0) {
            this.$toast.error("Số lượng không được âm !");
            changes.changedRecords[0].SOLUONG = arg.rowData.SOLUONG;
          } else if (changes.changedRecords[0].SOLUONG == 0) {
            changes.changedRecords[0].TIEN = 0;
            changes.changedRecords[0].VAT = 0;
            changes.changedRecords[0].ISUPDATE = 1;
          } else {
            changes.changedRecords[0].TIEN =
              changes.changedRecords[0].DONGIA *
              changes.changedRecords[0].SOLUONG;
            changes.changedRecords[0].VAT =
              (changes.changedRecords[0].DONGIA *
                changes.changedRecords[0].SOLUONG) /
              10;
            changes.changedRecords[0].ISUPDATE = 1;
          }
          gridDonHang.batchUpdate(changes);
          this.$refs.gridDonHang.grid.refreshColumns();
        } else if (arg.columnName == "TIEN") {
          if (changes.changedRecords[0].TIEN < 0) {
            this.$toast.error("Số lượng không được âm !");
            await this.NAP_DS_VATTU();
          } else {
            var thanhTien =
              changes.changedRecords[0].DONGIA *
              changes.changedRecords[0].SOLUONG;
            if (Math.abs(changes.changedRecords[0].TIEN - thanhTien) > 100) {
              this.$toast.error(
                "Thành tiền không hợp lệ (thành tiền ≈ đơn giá * số lượng ± 100 đ))"
              );
              await this.NAP_DS_VATTU();
            } else {
              var listUpdateChiTietDHIn = [];
              listUpdateChiTietDHIn.push({
                ctdhId: changes.changedRecords[0].CTDH_ID,
                soLuong: changes.changedRecords[0].SOLUONG,
                donGia: changes.changedRecords[0].DONGIA,
                tien: changes.changedRecords[0].TIEN,
                vat: changes.changedRecords[0].VAT,
                type: "tien",
              });
            }
            await this.updateChiTietDonHang({
              listUpdateChiTietDHIn: listUpdateChiTietDHIn,
            });
            await this.NAP_DS_VATTU();
          }
        } else if (arg.columnName == "VAT") {
          if (changes.changedRecords[0].VAT < 0) {
            this.$toast.error("VAT không hợp lệ (VAT ≥ 0)");
            await this.NAP_DS_VATTU();
          } else {
            if (
              changes.changedRecords[0].VAT > changes.changedRecords[0].TIEN
            ) {
              this.$toast.error("VAT không được lớn hơn Thành tiền");
            } else {
              var listUpdateChiTietDHIn = [];
              listUpdateChiTietDHIn.push({
                ctdhId: changes.changedRecords[0].CTDH_ID,
                soLuong: changes.changedRecords[0].SOLUONG,
                donGia: changes.changedRecords[0].DONGIA,
                tien: changes.changedRecords[0].TIEN,
                vat: changes.changedRecords[0].VAT,
                type: "vat",
              });
              await this.updateChiTietDonHang({
                listUpdateChiTietDHIn: listUpdateChiTietDHIn,
              });
            }
            await this.NAP_DS_VATTU();
          }
        }
      }
    },
    editGridVatTu(arg) {
      let gridVatTu = this.$refs.gridVatTu;
      let changes = gridVatTu.getBatchChanges();
      if (changes.changedRecords.length > 0) {
        if (arg.columnName == "SL_DONHANG") {
          if (
            changes.changedRecords[0].SL_DONHANG >
            changes.changedRecords[0].SLAOCL && this.kieuhd_id != "2"
          ) {
            changes.changedRecords[0].SL_DONHANG =
              changes.changedRecords[0].SLAOCL;
            this.rowSelectedVatTu.push(changes.changedRecords[0].HDVT_ID);
            this.$toast.error("Số lượng đơn hàng phải nhỏ hơn số lương tồn!");
          } else if (changes.changedRecords[0].SL_DONHANG < 0) {
            changes.changedRecords[0].SL_DONHANG = 0;
            this.rowSelectedVatTu = this.removeElementInArray(
              this.rowSelectedVatTu,
              changes.changedRecords[0].HDVT_ID
            );
          } else if (changes.changedRecords[0].SL_DONHANG == 0) {
            this.rowSelectedVatTu = this.removeElementInArray(
              this.rowSelectedVatTu,
              changes.changedRecords[0].HDVT_ID
            );
          } else {
            if (
              !this.rowSelectedVatTu.includes(changes.changedRecords[0].HDVT_ID)
            ) {
              this.rowSelectedVatTu.push(changes.changedRecords[0].HDVT_ID);
            }
          }
        }

        gridVatTu.batchUpdate(changes);
        // this.$refs.gridVatTu.grid.refreshColumns()
      }
    },
    removeElementInArray(array, element) {
      const index = array.indexOf(element);
      if (index > -1) {
        array.splice(index, 1);
      }
      return array;
    },

    async onChangeHienThiToanBo() {
      if (
        this.donHangId > 0 &&
        this.hdms_id > 0 &&
        this.donHangId != null &&
        this.hdms_id != null
      ) {
        await this.NAP_DS_VATTU();
      }
    },

    // async onClickNhapCap(){
    //   let f = this.$refs.frmNhapCap;
    //   f.ten_vt = "ĐỨc ĐẠi ca test"
    //   f.tong_sl = 1000
    //   await f.Open();
    // }

    async InPhieuDeNghi() {
      try {
        this.loading(true);
        const baseUrl = process.env.API;
        await this.axios({
          url: `${baseUrl}/web-qlvt/api/bao-cao/de-nghi-giao-hang?extend=pdf`,
          method: "POST",
          responseType: "blob",
          data: {
            donHangId: this.donHangId,
            noiDung: "",
          },
        }).then((response) => {
          previewPrint(response.data);
        });
        this.loading(false);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async InPhieuNhapKho() {
      try {
        var DSNgayNhapKho = await this.getNgayNghiemThuNhapKho(this.donHangId);

        if (DSNgayNhapKho.length == 0) {
          this.$toast.error("Chưa có ngày nhập kho");          
          return;
        }

        if (DSNgayNhapKho[0] == null || DSNgayNhapKho[0].ngayNK == null || DSNgayNhapKho[0].ngayNK == "") {
          this.$toast.error("Chưa có ngày nhập kho");        
          return;
        }
        this.ngay_nk = DSNgayNhapKho[0].ngayNK;
        this.$bvModal.show("popupNoiDung");
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async onClickXacNhanNoiDung() {
      try {
        this.$bvModal.hide("popupNoiDung");
        this.loading(true);
        if (this.vts_sohoa == "1") {
          var dtThaoTac = await this.getDSThaoTac();
          console.log(dtThaoTac, " = dtThaoTac");
          if (this.PHAILAM(dtThaoTac, "QLVT_XACTHUC_KYSO")) {
            this.taikhoan_ks = await this.getTaiKhoanKySo();
            this.$bvModal.show("popupNhapUser");
            return;
          } else {
            await this.giaoPhieuKySo();
          }
        } else {
          const baseUrl = process.env.API;
          await this.axios({
            url: `${baseUrl}/web-qlvt/api/bao-cao/de-nghi-nhap-vt?extend=pdf`,
            method: "POST",
            responseType: "blob",
            data: {
              donHangId: this.donHangId,
              noiDung: this.noiDung,
            },
          }).then((response) => {
            previewPrint(response.data);
          });
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async xacNhanKySoDonHang() {
      try {
        this.loading(true);
        this.$bvModal.hide("popupNhapUser");
        await this.giaoPhieuKySo();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async giaoPhieuKySo() {
      var Date_NK = this.ngay_nk.split(" ");
      var YMD_NK = Date_NK[0].split("-");
      var kq_gp = await this.kySoDonHang({
        taiKhoanKs: this.taikhoan_ks,
        matKhauKs: this.matkhau_ks,
        maDh: this.params.maDonHang,
        donHangId: this.donHangId,
        noiDung: this.noiDung,
        ngayLap: YMD_NK[2],
        thangLap: YMD_NK[1],
        namLap: YMD_NK[0],
      });
      if (kq_gp.toUpperCase() == "OK") {
        this.$toast.success("Giao phiếu thành công");
        this.onClickNghiemThu();
      } else {
        this.$toast.error(kq_gp);
      }
    },
    PHAILAM(dtThaoTac, CODE) {
      let result = dtThaoTac.filter((e) => e.ENABLE == 1 && e.CODE == CODE);
      return result.length > 0;
    },
    async onClickHuyBoNoiDung() {
      this.$bvModal.hide("popupNoiDung");

      var DSNoiDung = await this.getDSNoiDung(this.hdms_id);
      var noiDung = DSNoiDung.length > 0 ? DSNoiDung[0] : "";

      this.loading(true);
      const baseUrl = process.env.API;
      await this.axios({
        url: `${baseUrl}/web-qlvt/api/bao-cao/de-nghi-nhap-vt?extend=pdf`,
        method: "POST",
        responseType: "blob",
        data: {
          donHangId: this.donHangId,
          noiDung: noiDung,
        },
      }).then((response) => {
        previewPrint(response.data);
      });
      this.loading(false);
    },
    async InPhieuKiemTra() {
      try {
        this.loading(true);
        var DSNgayKiemTra = await this.getNgayNghiemThuNhapKho(this.donHangId);

        if (DSNgayKiemTra.length == 0) {
          this.$toast.error("Chưa có ngày nhập kho");
          this.loading(false);
          return;
        }

        if (DSNgayKiemTra[0].ngayNK == null) {
          this.$toast.error("Chưa có ngày nhập kho");
          this.loading(false);
          return;
        }

        const baseUrl = process.env.API;
        await this.axios({
          url: `${baseUrl}/web-qlvt/api/bao-cao/de-nghi-nhap-vt?extend=pdf`,
          method: "POST",
          responseType: "blob",
          data: {
            donHangId: this.donHangId,
            noiDung: "",
          },
        }).then((response) => {
          previewPrint(response.data);
        });
        this.loading(false);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async tsbtnFileMau_Click() {
      let dt = [
        {
          MA_VT: "11.01.01.11.00.30",
          SOLUONG: 5,
        },
        {
          MA_VT: "BTS.HCM154.81.2011.2",
          SOLUONG: 20,
        },
      ];

      let data1 = XLSX.utils.json_to_sheet(dt);
      let wb = XLSX.utils.book_new(); // make Workbook of Excel
      XLSX.utils.book_append_sheet(wb, data1, "ThietBi"); // sheetAName is name of Worksheet
      // export Excel file
      XLSX.writeFile(wb, "Mau_NhapDonHangExcel.xls");
    },
    async tsbtnExcel_Click() {
      document.getElementById("fileUpload").click();
    },
    checkfile: function () {
      if (this.arrayImportFile.filexcel == null) {
        this.$toast.error("Chưa chọn file nào!");
        return false;
      }

      if (
        this.arrayImportFile.filexcel.type !=
          "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" &&
        this.arrayImportFile.filexcel.type != "application/vnd.ms-excel"
      ) {
        this.$toast.error("File không phải định dạng xlsx hoặc xls!");
        return false;
      }
      return true;
    },
    async onChange() {
      this.arrayImportFile.filexcel =
        this.$refs.fileUpload.$refs.input.files[0];

      try {
        this.loading(true);
        if (this.checkfile()) {
          const reader = new FileReader();
          reader.onload = async (e) => {
            /* Parse data */
            const bstr = e.target.result;
            const wb = XLSX.read(bstr, { type: "binary" });
            /* Get first worksheet */
            const wsname = wb.SheetNames[0];
            const ws = wb.Sheets[wsname];
            /* Convert array of arrays */
            const data = XLSX.utils.sheet_to_json(ws, { header: 1 });
            // retrieve headers (i.e. remove first row)
            let headers = data.shift(); // bỏ dòng header đầu tiên trong data

            // then build the json for each row
            var result = data.map(function (a) {
              var jsonRow = {};
              a.forEach(function (cellValue, cellIndex) {
                jsonRow[headers[cellIndex]] = cellValue;
              });
              headers.forEach((b) => {
                if (!Object.keys(jsonRow).includes(b)) {
                  jsonRow[b] = "";
                }
              });
              return jsonRow;
            });

            console.log(result, " = result");
            if (result.length == 0) {
              this.$toast.error("File không có dữ liệu");
              return;
            } else {
              if (!("MA_VT" in result[0] && "SOLUONG" in result[0])) {
                this.$toast.error("Cấu trúc file phải có 2 cột MA_VT, SOLUONG");
                return;
              }
            }
            await this.KiemTraDuLieu(result);
          };
          reader.readAsBinaryString(this.arrayImportFile.filexcel);
        }
      } catch (error) {
        this.$toast.error("Có lỗi xảy ra: " + error.data.message);
      } finally {
        this.$refs.fileUpload.reset();
        this.loading(false);
      }
      this.arrayImportFile.filexcel = null;
    },
    async KiemTraDuLieu(data_excel) {
      try {
        this.loading(true);
        var loaiDonHang = await this.getDSLoaiDonHangTheoId(
          this.donHangIdFocus
        );
        if (loaiDonHang.length > 0 && loaiDonHang[0] == "1") {
          var TTDonHangCha = await this.getThongTinDonHangCha(
            this.donHangIdFocus
          );
        
          if (TTDonHangCha.length > 0) {
            this.$toast.error(
              "Đã có các đơn hàng con bạn không thể thay đổi thông tin vật tư"
            );
            return;
          }
        }
          
        for (let vt_ex of data_excel) {      
          for (let vt_hd of this.dataResponse.DSVatTuHopDong) {
            if (vt_ex.MA_VT == vt_hd.MA_VT) {             
              vt_hd.SL_DONHANG = parseFloat(vt_ex.SOLUONG);
              this.rowSelectedVatTu.push(vt_hd.HDVT_ID);
              break;
            }
          }
        }
        let MaVTNotExist = []
        let MaVTHopDong = []
        for (let vt_hd of this.dataResponse.DSVatTuHopDong) {
          MaVTHopDong.push(vt_hd.MA_VT)
        }
      
        for (let vt_ex of data_excel) {
          if(vt_ex.MA_VT != "" && !MaVTHopDong.includes(vt_ex.MA_VT)){
            MaVTNotExist.push(vt_ex.MA_VT)
          }
        }
    
        if (MaVTNotExist.length > 0) {
          this.$toast.error(
            "Vật tư trong file phải tồn tại ở danh sách vật tư của hợp đồng : " + MaVTNotExist.join(" | ")
          );
          return;
        }
        
        let VTHD_Import = this.dataResponse.DSVatTuHopDong.filter((e) =>
          this.rowSelectedVatTu.includes(e.HDVT_ID)
        );

        if (this.kieuhd_id == 2) {
          let giatri = 0;
          for (let e of VTHD_Import) {
            giatri = giatri + parseFloat(e.DONGIA) * parseFloat(e.SL_DONHANG);
          }

          if (giatri > parseFloat(this.giatri_hd_conlai)) {
            this.$toast.error(
              "Giá trị hợp đồng không còn đủ để tạo hợp đồng. Giá trị hợp đồng còn lại : " +
                this.giatri_hd_conlai
            );
            return;
          }
        }

        var listBackupCTDHIn = [];
        var listUpdateSLCTDHIn = [];
        VTHD_Import.forEach((element) => {
          var VatTuTemp = this.dataResponse.DSVatTuDonHang.filter(function (
            el
          ) {
            return el.VATTU_ID == element.VATTU_ID;
          });

          if (VatTuTemp.length > 0) {
            var soLuong = 0;
            soLuong =
              element.SL_DONHANG != 0 ? element.SL_DONHANG : element.SLAOCL;
            soLuong = soLuong + VatTuTemp[0].SOLUONG;

            listUpdateSLCTDHIn.push({
              soLuong: soLuong,
              ctdhId: VatTuTemp[0].CTDH_ID,
            });
          } else {
            var soLuong = 0;
            soLuong =
              element.SL_DONHANG != 0 ? element.SL_DONHANG : element.SLAOCL;
            listBackupCTDHIn.push({
              donHangId: this.donHangId,
              vatTuId: element.VATTU_ID,
              soLuong: soLuong,
              donGia: element.DONGIA,
              tien: 0,
              vat: 0,
              hdvtId: element.HDVT_ID,
              slNt: 0,
              slHong: 0,
              slChuan: 0,
            });
          }
        });

        if (listBackupCTDHIn.length > 0) {
          await this.insertBackupDonHang({
            listBackupCTDHIn: listBackupCTDHIn,
          });
        }
        if (listUpdateSLCTDHIn.length > 0) {
          await this.updateBackupDonHang({
            listUpdateSLCTDHIn: listUpdateSLCTDHIn,
          });
        }
        await this.NAP_DS_VATTU();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
  },
  created: async function () {
    // try {
    // this.loading(true);
    await this.frmDonHang_Load();
    await this.LOAD_COMBOBOX();
    await this.LOC_TREEVIEW();
    this.SET_BUTTON(0);
    var TSMD = await this.getTSMacDinh("TS_QLVT_SOHOA_MSTT");
    this.vts_sohoa = TSMD.length > 0 && TSMD[0].TEN_TS;
    // } catch (e) {
    //   this.$toast.error(e.data.message);
    // } finally {
    //   this.loading(false);
    // }
  },
};
</script>
<style>
  .table-content .e-grid .e-content {
    max-height: 500px !important;
    display: block !important;
  } 
</style>