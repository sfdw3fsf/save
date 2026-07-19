<template src='./QuyetToanVatTu.html'></template>
<style scoped src='./QuyetToanVatTu.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from "moment";
import Vue from "vue";
import breadcrumb from "@/components/breadcrumb";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/quyettoanvattu";
import select2 from "@/components/Select2.vue";
import { LOAI_DV, KETQUA_XL, SERVER_IMAGES } from "@/const/enums";
import {
  Freeze,
  Edit,
  Group,
  Page,
  Filter,
  Sort,
  Resize,
  GridPlugin,
  Aggregate,
  Toolbar,
} from "@syncfusion/ej2-vue-grids";
import { L10n } from "@syncfusion/ej2-base";
import { addScrolling } from "@syncfusion/ej2-navigations";
import xlsx from "xlsx";
import { LOAI_KHO } from "@/const/enums";
import { Query, Predicate } from "@syncfusion/ej2-data";
import NhapXuatVT from "../NhapXuatVT/NhapXuatVT.vue";
import NhapXuatVT_v2 from "../NhapXuatVT_v2/NhapXuatVT_v2.vue";
import SearchEProfile from "@/modules/search/subscriber/SearchEProfile";

Vue.use(GridPlugin);
L10n.load({
  "vi-VN": {
    grid: {
      EmptyRecord: "Không có bản ghi nào để hiển thị",
    },
  },
});
export default {
  components: {
    SearchEProfile,
    NhapXuatVT,
    NhapXuatVT_v2,
    breadcrumb,
    appSelect2: select2,
  },
  name: "QuyetToanVatTu",
  mounted() {},

  computed: {
    ...mapState("quyettoanvattu", statePropertyName),
    ...mapGetters("quyettoanvattu", getterName),
  },
  data() {
    return {
      header: {
        title: "QUYẾT TOÁN VẬT TƯ LẮP CHO KHÁCH HÀNG",
        list: [
          {
            name: "Quản lý vật tư",
            link: { name: "Ui.cards" },
          },
          {
            name: "Quyết toán vật tư lắp cho khách hàng",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      ttvt_goc: 0,
      isRenderNhapXuatVT: false,
      isRenderNhapXuatVTV2: false,
      frmNhapXuatVTV2: {
        visible: false,
        input: {
          sophieu: 0,
          vchungtu_id: 0,
          vma_md: 0,
          dt_vt: null,
          tag: 3,
        },
      },
      params: {
        trangThaiId: 1,
        tuNgay: new Date(new Date().getTime() - 3 * 24 * 60 * 60 * 1000),
        denNgay: new Date(),
        ngayQuyetToan: new Date(),
        donViId: 0,
        loaiNhomId: 0,
        dichVuId: 0,
        soMay: "",
        LoaiHDId: 0,
        khoId: 0,
        DSThueBaoChecked: [],
        noiDungTra: "",
        khoKhachHangId: 0,
        ghiChu: "",
        ngayChungTu: new Date(),
        nangCao: -999,
      },
      checkAllThueBao: false,
      rowSelectedThueBao: [],
      ThueBaoFocus: {},
      FirstLoad: true,
      dataResponse: {
        DSKhoKhachHang: [],
        DSKho: [],
        DSHopDong: [],
        cboNangCao: [],
        DSVatTuDaQuyetToan: [],
        DSVatTu: [],
        DSThueBao: [],
        DSDonVi: [],
        DSLoaiNhom: [
          {
            nhomId: 0,
            tenNhom: "Tất cả",
          },
          {
            nhomId: 1,
            tenNhom: "Thi công",
          },
          {
            nhomId: 2,
            tenNhom: "Báo hỏng",
          },
        ],
        DSDichVu: [],
        vts_sohoa: "",
        qt_donvi: false,
        ds_tt_chungtu: [],
        DSTrangThai: [
          {
            trangThaiId: 1,
            tenTrangThai: "Chưa quyết toán",
          },
          {
            trangThaiId: 2,
            tenTrangThai: "Đã quyết toán",
          },
          {
            trangThaiId: 4,
            tenTrangThai: "Hoàn thành",
          },
        ],
      },

      colCheckboxAllThueBao: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAllThueBao" class="uncheck"/>
                                <span class="name"></span>
                            </div>
                        `,
            data: function () {
              return {};
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
          },
        };
      },

      colCheckboxThueBao: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"                                    
                                    v-model="parent.rowSelectedThueBao"
                                    :value="data.KEY_ROW"/>
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
                return this.$parent.$parent.$parent;
              },
            },
          },
        };
      },
      dataRequest: {
        mucdich_id: 0,
        kho_tt: 0,
        kho_kh: 0,
        dvi_nhan: 0,
        nd: "",
      },
      timKiem: "",
      tagForm: "0",
      thongTinNguoiDung: {
        nhanVienId: this.$root.token.getNhanVienID(),
        nguoiCN: this.$root.token.getUserName(),
        mayCN: "DESKTOP-IDG67LD", //this.$auth.MAYCN
        ipCN: "10.59.90.128", //this.$auth.IPCN
        donViId: this.$root.token.getDonViID(),
        phanVungId: this.$root.token.getPhanVungID(),
        maNguoiDung: this.$root.token.getNguoiDungID(),
        MaTinh: this.$root.token.getMaTinh(),
      },
      dateConfig: {
        default: new Date(),
        formatDay: "dd/MM/yyyy",
        formatDayTime: "dd/MM/y HH:mm:ss",
        waterMark: "Chọn ngày",
      },
      Visible: {
        vatTu: false,
        danhSachQuyetToan: true,
        colLyDoCap: true,
        colTrangThaiVTQuyetToan: true,
        colTocDo: false,
        colGhiChu: false,
        colTenTrangBi: false,
        colMucCuoc: false,
        colNguyenNhan: false,
        colTinhTrang: false,
      },
      Enabled: {
        quyetToan: true,
        huyQuyetToan: true,
        hoanTamUng: true,
        traLai: true,
        giaoSuaVatTu: true,
        Kho: false,
      },
      Checkbox: {
        tuNgay: false,
        nangCao: false,
      },
      Text: {
        traLai: "Trả phiếu",
      },
      filterOptions: {
        enableCaseSensitivity: false,
        mode: "Immediate",
        ignoreAccent: true,
      },
      selectionSettings: { type: "Single", checkboxOnly: true },
      templateOptions: {
        create: function (args) {
          return (
            '<div class="input-icon-right"><input id="' +
            args.column.field +
            '_filterBarcell" type="search" class="form-control"> <span class="icon nc-icon-outline ui-1_zoom"></span></div>'
          );
        },
        write: function (args) {},
      },
      // groupVatTu: {
      //   showDropArea: false,
      //   columns: ["NGAY_CAP"],
      // },
      groupVatTu: ["NGAY_CAP"],
      onFilterDonVi: function (e) {
        var query = new Query();
        let predicate;
        if (e.text !== "") {
          predicate = new Predicate("tenDV", "contains", e.text.trim(), true);
          query.where(predicate);
        }
        e.updateData(this.dataSource, query);
      },
      onFilterKho: function (e) {
        var query = new Query();
        let predicate;
        if (e.text !== "") {
          predicate = new Predicate(
            "TEN_K_NEW",
            "contains",
            e.text.trim(),
            true
          );
          query.where(predicate);
        }
        e.updateData(this.dataSource, query);
      },
      onFilterKhoKH: function (e) {
        var query = new Query();
        let predicate;
        if (e.text !== "") {
          predicate = new Predicate("tenKho", "contains", e.text.trim(), true);
          query.where(predicate);
        }
        e.updateData(this.dataSource, query);
      },
    };
  },
  methods: {
    ...mapActions("quyettoanvattu", actionName),
    ...mapMutations("quyettoanvattu", mutationName),

    // chuyển Date thành type string
    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    f_DateTimeToString: function (value, format = "DD/MM/YYYY HH:mm:ss") {
      return moment(value).format(format);
    },
    f_StringToDate(value, format = "DD/MM/YYYY") {
      return moment(value, format).toDate();
    },

    async Load_Cbo() {
      if (this.tagForm == "2") {
        this.header.title = "QUYẾT TOÁN VẬT TƯ CỦA TTKD CHO KHÁCH HÀNG";
        this.header.list[1].name = "Quyết toán vật tư của TTKD cho khách hàng";
        this.Visible.vatTu = true;
        this.Text.traLai = "Hủy phiếu";
      }

      if (this.tagForm == "0") {
        this.dataResponse.DSDonVi = await this.getDSDonViTheoDichVu({
          nhanVienId: this.thongTinNguoiDung.nhanVienId,
          listDVId: [LOAI_DV.TRAM_VT],
        });
      } else if (this.tagForm == "2") {
        this.dataResponse.DSDonVi = await this.getDSDonViTheoDichVu({
          nhanVienId: this.thongTinNguoiDung.nhanVienId,
          listDVId: [LOAI_DV.PHONG_BANHANG_KV],
        });
      }
      // remove phần tử null đầu tiên
      this.dataResponse.DSDonVi.shift();
      this.dataResponse.DSDonVi.unshift({ donViId: 0, tenDV: "" });
      this.dataResponse.DSDichVu = await this.getDSDichVuVatTu();
      this.dataResponse.DSKhoKhachHang = await this.getDSKhoTheoNhanVienId(
        this.thongTinNguoiDung.nhanVienId
      );
      this.dataResponse.cboNangCao = await this.getDSTimKiemNangCao();
      this.onChangeLoaiNhom();
      this.onChangeTrangThai();
    },
    async onChangeTrangThai() {
      try {
        this.loading(true);
        this.params.soMay = "";
        this.SetButton();
        await this.TimKiem();
      } catch (e) {
        this.$toast.error("Đã có lỗi xảy ra: ", e);
      } finally {
        this.loading(false);
      }
    },
    SetButton() {
      this.Enabled.quyetToan = false;
      this.Enabled.huyQuyetToan = false;
      this.Enabled.hoanTamUng = false;
      this.Enabled.traLai = false;
      this.Enabled.giaoSuaVatTu = true;
      this.Visible.danhSachQuyetToan = false;
      this.Checkbox.tuNgay = false;
      this.Visible.vatTu = false;

      if (this.params.trangThaiId == "1") {
        this.Enabled.quyetToan = true;
        this.Enabled.traLai = true;
        this.Enabled.giaoSuaVatTu = false;
        if (this.tagForm == "2") {
          this.Visible.vatTu = true;
        }
      } else if (this.params.trangThaiId == "2") {
        this.Enabled.huyQuyetToan = true;
        this.Enabled.hoanTamUng = true;
        this.Visible.danhSachQuyetToan = true;
        this.Enabled.giaoSuaVatTu = false;
      } else if (this.params.trangThaiId == "4") {
        this.Checkbox.tuNgay = true;
        this.params.tuNgay = new Date(
          new Date().getTime() - 3 * 24 * 60 * 60 * 1000
        );
      }
    },
    async TimKiem() {
      this.ttvt_goc = this.params.trangThaiId;
      await this.LoadThongTinThueBao();
    },
    async onChangeLoaiNhom() {
      if (this.params.loaiNhomId == 0) {
        this.params.dichVuId = 0;
        this.dataResponse.DSHopDong = [
          {
            loaiDHId: 0,
            loaiDH: "Tất cả",
          },
        ];
        this.params.LoaiHDId = 0;
      } else if (this.params.loaiNhomId == 1) {
        this.dataResponse.DSHopDong = await this.getDSLoaiHopDong();
      } else if (this.params.loaiNhomId == 2) {
        this.dataResponse.DSHopDong = [
          {
            loaiDHId: 100,
            loaiDH: "Xử lý sự cố",
          },
        ];
        this.params.LoaiHDId = 100;
      }
    },
    async getDSThueBaoVatTu() {
      var soMay = this.timKiem == "" ? "0" : this.params.soMay;
      if (this.vts_sohoa == 1) {
        this.Checkbox.tuNgay = true;
      }
      var tuNgay = this.Checkbox.tuNgay
        ? this.f_DateToString(this.params.tuNgay)
        : "0";
      var denNgay = this.Checkbox.tuNgay
        ? this.f_DateToString(this.params.denNgay)
        : "0";

      if (this.Checkbox.tuNgay) {
        var msg = "";
        if (tuNgay == "Invalid date") {
          msg += "Ngày giao không đúng định dạng \t\n";
        }
        if (denNgay == "Invalid date") {
          msg += "Đến ngày không đúng định dạng \t\n";
        }
        if (msg != "") {
          this.$toast.error(msg);
          return this.dataResponse.DSThueBao;
        }
      }

      var data = {
        nhomId: this.params.loaiNhomId,
        dichVuVtId: this.params.dichVuId,
        loaiHdId: this.params.LoaiHDId,
        maTb: soMay,
        tuNgay: tuNgay,
        denNgay: denNgay,
        nhanVienId: this.thongTinNguoiDung.nhanVienId,
        status: this.params.trangThaiId,
        donViId: this.thongTinNguoiDung.donViId,
        tkNangCao: this.Checkbox.nangCao ? this.params.nangCao : -999,
      };
      var DSThueBaoVatTu = await this.getDSQuyetToanNangCao(data);
      return DSThueBaoVatTu;
    },
    async LoadThongTinThueBao() {
      // this.dataResponse.DSVatTuDaQuyetToan = [];
      var DSThueBaoVatTu = await this.getDSThueBaoVatTu();
      for (let e of DSThueBaoVatTu) {
        e.KEY_ROW = e.PHIEU_ID + "-" + e.HDTB_ID + "-" + e.BAOHONG_ID;
      }
      this.rowSelectedThueBao = []; // reset checkbox
      this.checkAllThueBao = false;

      if (this.params.donViId != 0) {
        var donViId = this.params.donViId;

        this.dataResponse.DSThueBao = DSThueBaoVatTu.filter(function (el) {
          return el.DONVI_GIAO_ID == donViId;
        });
      } else {
        this.dataResponse.DSThueBao = DSThueBaoVatTu;
      }

      if (this.dataResponse.DSThueBao.length > 0) {
        this.Visible.colTocDo = "TOCDO" in this.dataResponse.DSThueBao[0];
        this.Visible.colGhiChu = "GHICHU" in this.dataResponse.DSThueBao[0];
        this.Visible.colTenTrangBi =
          "TENTRANGBI" in this.dataResponse.DSThueBao[0];
        this.Visible.colMucCuoc = "MUCUOC" in this.dataResponse.DSThueBao[0];
        this.Visible.colNguyenNhan =
          "NGUYEN_NHAN" in this.dataResponse.DSThueBao[0];
        this.Visible.colTinhTrang =
          "TINHTRANG" in this.dataResponse.DSThueBao[0];
        this.LOC_KHO();
      } else {
        this.dataResponse.DSThueBao = [];
        this.dataResponse.DSVatTu = [];
        this.dataResponse.DSVatTuDaQuyetToan = [];
      }
    },

    async LOC_KHO() {
      this.dataResponse.DSVatTuDaQuyetToan = [];
      this.rowSelectedThueBao = [];
      this.checkAllThueBao = false;
      if (this.params.khoId == 0 || this.params.khoId == null) {
        return;
      }
      // check KHO_ID có trong DB k, KHO_ID dùng cho phân vùng khác Hải Phòng
      if (this.dataResponse.DSThueBao.length > 0) {
        if (!("KHO_ID" in this.dataResponse.DSThueBao[0])) {
          return;
        }
      }
      // var DSThueBaoVatTu = await this.getDSThueBaoVatTu();

      if (this.dataResponse.DSThueBao.length > 0) {
        var DSThueBao_Filter = this.dataResponse.DSThueBao.filter(
          (a) => a.KHO_ID == this.params.khoId
        );
        this.dataResponse.DSThueBao = DSThueBao_Filter;
      }

      if (this.dataResponse.DSThueBao.length == 0) {
        this.dataResponse.DSVatTu = [];
      }
    },
    async DSThueBaoFocus(row) {
      if (row) {    
        this.ThueBaoFocus = row;
        this.params.soMay = row.MA_TB;
        this.dataResponse.DSVatTu = await this.getDSVatTu({
          phieuId: row.PHIEU_CHA_ID,
          baoHongId: row.BAOHONG_ID,
          hdTbId: row.HDTB_ID,
          kieuTbi: 1,
          status: this.params.trangThaiId,
        });
        this.dataResponse.DSVatTu.forEach((e) => {
          e.NGAY_CAP = e.NGAY_CAP.slice(0, 10);
          e.TIEN = e.TIEN == null || e.TIEN == "" ? 0 : parseFloat(e.TIEN);
          e.DONGIA = e.DONGIA == null || e.DONGIA == "" ? 0 : parseFloat(e.DONGIA);
        });    
        this.Visible.colLyDoCap =
          "LYDOCAP" in this.dataResponse.DSVatTu ? true : false;
        this.Visible.colTrangThaiVTQuyetToan =
          "TRANGTHAI" in this.dataResponse.DSVatTu ? true : false;
      } else {
        // this.params.soMay = "";
        this.dataResponse.DSVatTu = [];
        this.ThueBaoFocus = {};
      }
    },
    getDSVatTuDaQuyetToan: async function (dataItem) {      
      this.params.DSThueBaoChecked = this.dataResponse.DSThueBao.filter(
        function (el) {
          return dataItem.includes(el.KEY_ROW);
        }
      );

      if (dataItem.length == 0) {
        this.dataResponse.DSVatTuDaQuyetToan = [];
      } else {
        if (this.params.trangThaiId != "2" && this.params.trangThaiId != "4") {
          return;
        }

        var data = [];
        for (const i in this.params.DSThueBaoChecked) {
          data.push({
            PHIEU_ID: this.params.DSThueBaoChecked[i].PHIEU_CHA_ID,
            HDTB_ID: this.params.DSThueBaoChecked[i].HDTB_ID,
            BAOHONG_ID: this.params.DSThueBaoChecked[i].BAOHONG_ID,
          });
        }

        this.dataResponse.DSVatTuDaQuyetToan = await this.getDSVatTuQuyetToan({
          data: JSON.stringify(data),
          kieuTBi: 1,
          status: this.params.trangThaiId,
        });
        this.dataResponse.DSVatTuDaQuyetToan.forEach((e) => {
          e.NGAY_CAP = e.NGAY_CAP.slice(0, 10);
        });
      }
    },

    async onClickLayTT() {
      try {
        this.loading(true);
        await this.TimKiem();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async txtSoThueBao_KeyPress() {
      this.timKiem = this.params.soMay;
      await this.LoadThongTinThueBao();
      this.timKiem = "";
    },
    async onClickHuyQuyetToan() {
      try {
        if (this.ttvt_goc != this.params.trangThaiId) {
          this.$toast.error("Bạn thay đổi lựa chọn khác với tìm kiếm");
          return;
        }

        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm("Bạn chắc chắn muốn hủy quyết toán không ?", {
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
        if (this.params.DSThueBaoChecked.length == 0) {
          this.$toast.error("Bạn chưa chọn thuê bao để hủy quyết toán");
          this.loading(false);
          return;
        }

        var data = [];
        for (const i in this.params.DSThueBaoChecked) {
          data.push({
            PHIEU_CHA_ID: this.params.DSThueBaoChecked[i].PHIEU_CHA_ID,
            HDTB_ID: this.params.DSThueBaoChecked[i].HDTB_ID,
            BAOHONG_ID: this.params.DSThueBaoChecked[i].BAOHONG_ID,
          });
        }

        if (!this.qt_donvi) {
          var ketQua = await this.huyQuyetToanVatTu({
            data: JSON.stringify(data),
            nguoiCN: this.thongTinNguoiDung.nguoiCN,
            mayCN: this.thongTinNguoiDung.mayCN,
            ipCN: this.thongTinNguoiDung.ipCN,
          });          
          if (ketQua == "ok") {
            this.$toast.success("Hủy quyết toán thành công");
          } else {
            this.$toast.error(ketQua);
          }
        } else {
          var ketQua = await this.huyQuyetToanGiaoPhieuVatTu({
            data: data,
          });
          if (ketQua == "ok") {
            this.$toast.success("Hủy quyết toán thành công");
          } else {
            this.$toast.error(ketQua);
          }
        }

        await this.LoadThongTinThueBao();
        this.loading(false);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },

    async onClickHoanTamUng() {
      var kiemTraDuLieu = await this.kiemTraDuLieu();
      if (!kiemTraDuLieu) {
        return;
      }
      var checkVatTu = await this.block_vattu(1);
      if (!checkVatTu) {
        return;
      }
      this.params.ngayChungTu = new Date();
      this.$bvModal.show("popupNhapGhiChu");
    },

    async onClickXacNhanHoanTamUng() {
      try {
        this.$bvModal.hide("popupNhapGhiChu");

        this.loading(true);

        if (!this.qt_donvi) {
          var KTHanChungTu = await this.kiemTraHanChungTu({
            chungTuId: -1,
            khoGiaoId: this.dataRequest.kho_tt,
            khoNhanId: this.dataRequest.kho_kh,
            ngayCt: this.f_DateTimeToString(this.params.ngayChungTu),
          });

          if (KTHanChungTu != "1") {
            this.$toast.error("Đã quá hạn sửa chứng từ!");
            this.loading(false);
            return;
          }
        }

        if (this.dataResponse.DSVatTuDaQuyetToan.length == 0) {
          this.$toast.error("Không lấy được thông tin vật tư để quyết toán");
          this.loading(false);
          return;
        }

        if (!this.qt_donvi) {
          for (const i in this.dataResponse.DSVatTuDaQuyetToan) {
            if (
              this.dataResponse.DSVatTuDaQuyetToan[i].KHOTO != "0" &&
              this.dataResponse.DSVatTuDaQuyetToan[i].KHOTO !=
                this.dataRequest.kho_tt
            ) {
              this.$toast.error(
                "Có thiết bị quyêt toán không thuộc kho tổ trưởng bạn đang chọn (" +
                  this.dataResponse.DSVatTuDaQuyetToan[i].HDTB_ID +
                  "," +
                  this.dataResponse.DSVatTuDaQuyetToan[i].BAOHONG_ID +
                  ")"
              );
              this.loading(false);
              return;
            }
          }
        }

        var js_vtquyettoan = [];
        var ds_chon = [];
        if (this.qt_donvi) {
          var DSCheck = this.dataResponse.DSVatTuDaQuyetToan.filter(function (
            el
          ) {
            return el.STATUS == 2;
          });
          if (DSCheck.length == 0) {
            this.$toast.error(
              "Các vật tư đã hoàn thành hết. Bạn hãy kiểm tra lại"
            );
            this.loading(false);
            return;
          }
          ds_chon = DSCheck;
        } else {
          ds_chon = this.dataResponse.DSVatTuDaQuyetToan;
        }

        for (const e of ds_chon) {
          js_vtquyettoan.push({
            MA_TBI: e.MA_TBI,
            KHOTO: e.KHOTO,
            THIETBI_ID: e.THIETBI_ID,
            SOLUONG: e.SOLUONG,
            PHIEUVT_ID: e.PHIEUVT_ID,
          });
        }

        if (this.dataResponse.DSThueBao.length == 0) {
          this.$toast.error("Không lấy được thông tin phiếu để quyết toán");
          this.loading(false);
          return;
        }
        if (this.params.DSThueBaoChecked.length == 0) {
          this.$toast.error("Bạn chưa chọn phiếu để quyết toán");
          this.loading(false);
          return;
        }

        var js_phieuquyettoan = [];        
        for (const e of this.params.DSThueBaoChecked) {
          js_phieuquyettoan.push({
            PHIEU_ID: e.PHIEU_ID,
            PHIEU_CHA_ID: e.PHIEU_CHA_ID,
            HDTB_ID: e.HDTB_ID,
            BAOHONG_ID: e.BAOHONG_ID,
            THUEBAO_ID: e.THUEBAO_ID,
          });
        }
        this.ds_tt_chungtu = [];
        this.ds_tt_chungtu.push({
          LOAIPHIEU_ID: 12,
          LOAICT_ID: 2,
          MUCDICH_ID: this.dataRequest.mucdich_id,
          KHO_GIAO_ID: this.dataRequest.kho_tt,
          KHO_NHAN_ID: this.dataRequest.kho_kh,
          DONVI_GIAO_ID: this.thongTinNguoiDung.donViId,
          DONVI_NHAN_ID: this.dataRequest.dvi_nhan,
          NHANVIEN_GIAO_ID: this.thongTinNguoiDung.nhanVienId,
          ND_GIAO: this.params.ghiChu.trim(),
          NGAY_CT: this.f_DateTimeToString(this.params.ngayChungTu),
        });

        var ketQua = await this.hoanTamUng({
          ds_tt_chungtu: this.ds_tt_chungtu,
          ds_vattu: js_vtquyettoan,
          ds_phieu: js_phieuquyettoan,
          tungay: this.f_DateToString(this.params.tuNgay),
          denngay: this.f_DateToString(this.params.denNgay),
          mucdich_id: this.dataRequest.mucdich_id,
          kho_giao_id: this.dataRequest.kho_tt,
          kho_nhan_id: this.dataRequest.kho_kh,
          donvi_giao_id: this.thongTinNguoiDung.donViId,
          donvi_nhan_id: this.dataRequest.dvi_nhan,
          ngay_ct: this.f_DateTimeToString(this.params.ngayChungTu),
          nd_giao: this.params.ghiChu.trim(),
        });

        this.$toast.success("Đã hoàn tạm ứng thành công");
        await this.LoadThongTinThueBao();
        if (this.thongTinNguoiDung.phanVungId == 28) {       
          let f = this.$refs.frmNhapXuatVT_v2;
          f.Tag = "3";
          this.frmNhapXuatVTV2.input = {
            vinput_chungtu_id: ketQua.CHUNGTU_ID,
            tag: 3,
          };
          this.frmNhapXuatVTV2.visible = true;
          // await f.frmNhapXuatVT_v2_Load();
          this.$refs.popupNhapXuatVT_v2.show();
        } else {       
          let frmNhapXuatVT = this.$refs.frmNhapXuatVT;
          if (this.thongTinNguoiDung.MaTinh == "HDG") {
            frmNhapXuatVT.Tag = "2";
          } else {
            frmNhapXuatVT.Tag = "1";
          }
          await frmNhapXuatVT.frmNhapXuatVT_Load();
          await frmNhapXuatVT.FocusGridViewRow(
            "gridviewChungTu",
            "CHUNGTU_ID",
            ketQua.CHUNGTU_ID
          );
          this.$refs.popupNhapXuatVT.show();
        }        
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },

    async kiemTraDuLieu() {
      var msg = "";
      var DSMucDich = await this.getMucDichXuat("2.QT.1");      
      if (DSMucDich.length == 0) {
        msg += "Mục đích QT.1 không có \r\n";
      } else {
        this.dataRequest.mucdich_id = DSMucDich[0].mucDichId;
        this.dataRequest.nd = DSMucDich[0].mucDich;
      }

      if (this.params.donViId == 0 && this.params.phanVungId != 50) {
        msg += "Bạn phải chọn 1 đơn vị để hoàn tạm ứng \r\n";
      } else {
        if (
          (this.params.khoId == 0 || this.params.khoId == null) &&
          !this.qt_donvi
        ) {
          msg += " Bạn phải chọn kho để hoàn tạm ứng \r\n";
        } else {
          this.dataRequest.kho_tt = this.qt_donvi ? 0 : this.params.khoId;
        }
        if (this.params.khoKhachHangId == 0) {
          msg += " Bạn chưa chọn kho khách hàng \r\n";
        } else {
          this.dataRequest.kho_kh = this.params.khoKhachHangId;
        }

        var DSDonViNhan = await this.getDSDonViNhan();
        if (DSDonViNhan.length == 0) {
          msg += " Không tim thấy đơn vị khách hàng \r\n";
        } else {
          this.dataRequest.dvi_nhan = DSDonViNhan[0];
        }
      }

      if (this.dataResponse.DSVatTuDaQuyetToan.length == 0) {
        msg += " Chưa có danh sách vật tư đã quyết toán!";
      }
      if (msg != "") {
        this.$toast.error(msg);
        return false;
      }

      return true;
    },
    async onClickQuyetToan() {
      if (this.params.ngayQuyetToan == null) {
        this.$toast.error("Chưa chọn ngày quyết toán!");
        return;
      }

      if (this.ttvt_goc != this.params.trangThaiId) {
        this.$toast.error("Bạn thay đổi lựa chọn khác với tìm kiếm");
        return;
      }
      if (this.params.DSThueBaoChecked.length == 0) {
        this.$toast.error("Bạn chưa chọn thuê bao để quyết toán");
        return;
      }

      if (
        this.thongTinNguoiDung.phanVungId == 21 ||
        this.thongTinNguoiDung.phanVungId == 28
      ) {
        var checkVatTu = await this.block_vattu(0);
        if (!checkVatTu) {
          return;
        }
      }

      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm("Bạn chắc chắn muốn quyết toán không ?", {
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

      try {
        this.loading(true);
        var listData = [];
        for (const i in this.params.DSThueBaoChecked) {
          listData.push({
            ph: this.params.DSThueBaoChecked[i].PHIEU_ID,
            pc: this.params.DSThueBaoChecked[i].PHIEU_CHA_ID,
            hd: this.params.DSThueBaoChecked[i].HDTB_ID,
            bh: this.params.DSThueBaoChecked[i].BAOHONG_ID,
          });
        }
        var ketQua = await this.quyetToanVatTuV2({
          listData: listData,
          nguoiCn: this.thongTinNguoiDung.nguoiCN,
          mayCn: this.thongTinNguoiDung.mayCN,
          ipCn: this.thongTinNguoiDung.ipCN,
          ngayQt: this.f_DateTimeToString(this.params.ngayQuyetToan),
        });     
        if (ketQua == "ok") {         
          this.$toast.success("Quyết toán thành công");
        } else {
          this.$toast.error(ketQua);
        }
        await this.LoadThongTinThueBao();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async onClickTraLai() {
      try {
        if (this.dataResponse.DSThueBao == 0) {
          this.$toast.error("Không có thuê bao được chọn");
          return;
        }

        if (this.params.DSThueBaoChecked.length > 0) {
          var dataKiemTra = [];
          for (const i in this.params.DSThueBaoChecked) {
            dataKiemTra.push({
              PHIEU_ID: this.params.DSThueBaoChecked[i].PHIEU_CHA_ID,
              HDTB_ID: this.params.DSThueBaoChecked[i].HDTB_ID,
              BAOHONG_ID: this.params.DSThueBaoChecked[i].BAOHONG_ID,
              KIEUTBI_ID: this.params.DSThueBaoChecked[i].KIEUTBI_ID,
            });
          }
          var ketQua = await this.kiemTraPhieuTra({
            data: JSON.stringify(dataKiemTra),
          });
          if (ketQua == "fail") {
            this.$toast.error(
              "Không thể trả phiếu khi đã có vật tư nhập kho thu hồi"
            );
            return;
          }
        } else {
          this.$toast.error("Chưa chọn thuê bao để trả phiếu!");
          return;
        }

        var cauHoi =
          this.tagForm == "2"
            ? "Bạn có chắc chắn muốn hủy phiếu không ?"
            : "Bạn chắc chắn muốn trả lại không ?";
        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm(cauHoi, {
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

        if (this.params.noiDungTra == "" && this.tagForm != "2") {
          this.$toast.error("Bạn hãy nhập lý do trả lại");
          return;
        }
        if (this.ttvt_goc != this.params.trangThaiId) {
          this.$toast.error("Bạn thay đổi lựa chọn khác với tìm kiếm");
          return;
        }
        var data = [];
        for (const i in this.params.DSThueBaoChecked) {
          data.push({
            PHIEU_ID: this.params.DSThueBaoChecked[i].PHIEU_ID,
            HDTB_ID: this.params.DSThueBaoChecked[i].HDTB_ID,
            BAOHONG_ID: this.params.DSThueBaoChecked[i].BAOHONG_ID,
            PHIEU_CHA_ID: this.params.DSThueBaoChecked[i].PHIEU_CHA_ID,
          });
        }

        var ketQua = await this.huyTraPhieuQuyetToan({
          data: JSON.stringify(data),
          nguoiCN: this.thongTinNguoiDung.nguoiCN,
          mayCN: this.thongTinNguoiDung.mayCN,
          ipCN: this.thongTinNguoiDung.ipCN,
          noiDungTra: this.params.noiDungTra,
          nhanVienTraId: this.thongTinNguoiDung.nhanVienId,
          tagForm: this.tagForm,
        });
        if (this.tagForm == "2") {
          if (ketQua == "ok") {
            // this.$toast.success(
            //   "Hủy " + this.params.DSThueBaoChecked.length + " phiếu thành công."
            // );
            this.$toast.success("Hủy phiếu thành công.");
          } else {
            this.$toast.error(ketQua);
          }
        } else {
          if (ketQua == "ok") {
            this.$toast.success("Trả lại phiếu thành công.");
          } else {
            this.$toast.error(ketQua);
          }
        }
        await this.LoadThongTinThueBao();
        this.params.noiDungTra = "";
        this.loading(false);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async block_vattu(kieu) {
      if (kieu == 0) {
        //Quyết toán
        var DSVatTuThueBao = [];
        for (const i in this.params.DSThueBaoChecked) {
          var DSThueBaoTemp = await this.getDSPhieuVTThueBao({
            phieuId: this.params.DSThueBaoChecked[i].PHIEU_CHA_ID,
            baoHongId: this.params.DSThueBaoChecked[i].BAOHONG_ID,
            hdTbId: this.params.DSThueBaoChecked[i].HDTB_ID,
            kieuTbi: 1,
            status: this.params.trangThaiId,
          });
          DSThueBaoTemp.forEach((e) => {
            DSVatTuThueBao.push(e);
          });
        }

        for (let e of DSVatTuThueBao) {
          if (this.thongTinNguoiDung.phanVungId == 21) {
            if (e.TRANGTHAI === 0) {
              this.$toast.error(
                "Thuê bao [" +
                  e.MA_TB +
                  "] có vật tư chưa có đầu vào, không thể quyết toán được!\n" +
                  "Bạn hãy thay đổi vật tư chưa có đầu vào ở [Nhập/xuất/điều chuyển vật tư]"
              );
              return false;
            }
          }
          if (this.thongTinNguoiDung.phanVungId == 28) {
            var tkdu = e.TK_DOIUNG;
            if (tkdu == "14123" || tkdu == "14133") {
              this.$toast.error(
                "Thuê bao [" +
                  e.MA_TB +
                  "], lô hàng" +
                  e.LOHANG +
                  " có tài khoản đối ứng: " +
                  tkdu_z +
                  ". Không thể quyết toán được!\n"
              );
              return false;
            }
          }
        }
      } else {
        //hoàn tạm ứng

        this.dataResponse.DSVatTuDaQuyetToan.forEach((e) => {
          if (this.thongTinNguoiDung.phanVungId == 21) {
            if (e.TRANGTHAI == 0) {
              this.$toast.error(
                "Thuê bao [" +
                  e.MA_TB +
                  "] có vật tư chưa có đầu vào, không thể hoàn tạm ứng được!\n" +
                  "Bạn hãy thay đổi vật tư chưa có đầu vào ở [Nhập/xuất/điều chuyển vật tư]"
              );
              return false;
            }
          }
          if (this.thongTinNguoiDung.phanVungId == 28) {
            var tkdu = e.TK_DOIUNG;
            if (tkdu == "14123" || tkdu == "14133") {
              this.$toast.error(
                "Thuê bao [" +
                  e.MA_TB +
                  "], lô hàng" +
                  e.LOHANG +
                  " có tài khoản đối ứng: " +
                  tkdu_z +
                  ".  Không thể hoàn tạm ứng được!\n"
              );
              return false;
            }
          }
        });
      }

      return true;
    },

    async onClickGiaoSuaVT() {
      this.loading(true);
      if (this.dataResponse.DSThueBao == 0) {
        this.$toast.error("Không có thuê bao để giao phiếu yêu cầu sửa");
        this.loading(false);
        return;
      }

      if (this.params.DSThueBaoChecked == 0) {
        this.$toast.error("Bạn chưa chọn thuê bao để giao phiếu yêu cầu sửa");
        this.loading(false);
        return;
      }

      if (this.params.noiDungTra == "") {
        this.$toast.error("Bạn chưa điền lý do giao sửa phiếu");
        this.loading(false);
        return;
      }

      if (this.dataResponse.DSVatTu > 0) {
        var strPhieuVTId = "";
        this.dataResponse.DSVatTu.forEach((e) => {
          strPhieuVTId += e.PHIEUVT_ID + ",";
        });
        strPhieuVTId = strPhieuVTId.substring(0, strPhieuVTId.length - 1);        

        var DSKDuocSua = await this.getDSPhieuKDCSUA({ data: strPhieuVTId });
        if (DSKDuocSua.length > 0) {
          var strChungTu = "";
          this.dataResponse.DSVatTu.forEach((e) => {
            strChungTu += e.SOPHIEU + ",";
          });
          strChungTu = strChungTu.substring(0, strChungTu.length - 1);
          this.$toast.error(
            "Các thuê bao này không thể giao phiếu sửa bạn cần phải xóa chứng từ " +
              strChungTu +
              " để có thể giao phiếu sửa"
          );
          this.loading(false);
          return;
        }
      }
      this.loading(false);
      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm("Bạn có chắc chắn muốn giao sửa lại phiếu này không ?", {
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
      var SLCheck = 0;
      for (const i in this.params.DSThueBaoChecked) {
        var ketQua = await this.kiemTraSuDungVatTu(
          this.params.DSThueBaoChecked[i].THUEBAO_ID
        );
        SLCheck += ketQua;      
      }

      if (SLCheck != this.params.DSThueBaoChecked.length) {
        this.$toast.error(
          "Có " +
            (this.params.DSThueBaoChecked.length - SLCheck) +
            " thuê bao đang có vật tư được cấp/thu hồi không thể sửa"
        );
        this.loading(false);
        return;
      }
      var dem = 0;
      for (const i in this.params.DSThueBaoChecked) {
        var ketQua = await this.kiemTraSuaVatTu({
          phieuId: this.params.DSThueBaoChecked[i].PHIEU_ID,
          nguoiCN: this.thongTinNguoiDung.nguoiCN,
          mayCN: this.thongTinNguoiDung.mayCN,
          ipCN: this.thongTinNguoiDung.ipCN,
          hdtbId: this.params.DSThueBaoChecked[i].HDTB_ID,
          baoHongId: this.params.DSThueBaoChecked[i].BAOHONG_ID,
          noiDungTra: this.params.noiDungTra,
          nhanVienTraId: this.thongTinNguoiDung.nhanVienId,
        });        
        if (ketQua == 1) {
          dem++;
        }
      }

      // this.$toast.success("Yêu cầu giao để sửa vật tư thành công " + dem + "/" + this.params.DSThueBaoChecked.length + " bản ghi");
      this.$toast.success("Yêu cầu giao để sửa vật tư thành công");
      await this.LoadThongTinThueBao();
      this.params.noiDungTra = "";
      this.loading(false);
    },
    async onClickXuatExcel() {
      try {
        if (this.dataResponse.DSThueBao.length == 0) {
          this.$toast.error("Không có dữ liệu thuê bao để xuất excel !");
          return;
        }
        this.loading(true);
        var data = [];
        for (const i in this.dataResponse.DSThueBao) {
          data.push({
            PHIEU_ID: this.dataResponse.DSThueBao[i].PHIEU_CHA_ID,
            HDTB_ID: this.dataResponse.DSThueBao[i].HDTB_ID,
            BAOHONG_ID: this.dataResponse.DSThueBao[i].BAOHONG_ID,
          });
        }

        var DSVatTuQTXuatExcel = await this.getDSVatTuQuyetToan({
          data: JSON.stringify(data),
          kieuTBi: 1,
          status: this.params.trangThaiId,
        });
        DSVatTuQTXuatExcel.forEach((e) => {
          e.NGAY_CAP = e.NGAY_CAP.slice(0, 10);
        });
        var trangThaiId = this.params.trangThaiId;

        var TrangThaiFilter = this.dataResponse.DSTrangThai.filter(function (
          el
        ) {
          return el.trangThaiId == trangThaiId;
        });

        var NameFile = "QLVT - " + TrangThaiFilter[0].tenTrangThai + ".xls";
        var DSThueBao = xlsx.utils.json_to_sheet(this.dataResponse.DSThueBao);
        var DSVatTuQTXuatExcel = xlsx.utils.json_to_sheet(DSVatTuQTXuatExcel);
        var wb = xlsx.utils.book_new();
        xlsx.utils.book_append_sheet(wb, DSThueBao, "Sheet1");
        xlsx.utils.book_append_sheet(wb, DSVatTuQTXuatExcel, "Sheet2");
        xlsx.writeFile(wb, NameFile);
        this.loading(false);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async onChangeKho() {
      if (!this.qt_donvi) {
        await this.LOC_KHO();
      }
    },
    async onChangeDonVi() {
      try {
        this.loading(true);
        this.dataResponse.DSKho = []
        this.params.soMay = "";
        
        if (this.params.donViId == 0) {
          if (this.FirstLoad) {
            // tránh lặp API
            this.FirstLoad = false;
          } else {
            await this.LoadThongTinThueBao();
          }

          if (this.qt_donvi) {
            return;
          }

          if (this.thongTinNguoiDung.phanVungId == 50) {
            this.dataResponse.DSKho = await this.getDSKhoTheoCBoDonVi({
              nhanVienId: this.$auth.getNhanVienID(),
              donViId: this.params.donViId
            })   
          } else {
            this.$refs.refKho.ej2Instances.value = null;
            this.$refs.refKho.dataBind();
            this.params.khoId = 0;
            this.Enabled.Kho = false;
          }
        } else {
          await this.LoadThongTinThueBao();
          this.Enabled.Kho = true;
          if (this.qt_donvi) {
            return;
          }
          this.dataResponse.DSKho = await this.getDSKhoTheoCBoDonVi({
            nhanVienId: this.$auth.getNhanVienID(),
            donViId: this.params.donViId
          })         
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    // async onChangeDonVi() {
    //   try {
    //     this.loading(true);
    //     this.dataResponse.DSKho = []
    //     this.params.soMay = "";
    //     if (this.params.donViId == 0) {
    //       if (this.FirstLoad) {
    //         // tránh lặp API
    //         this.FirstLoad = false;
    //       } else {
    //         await this.LoadThongTinThueBao();
    //       }

    //       if (this.qt_donvi) {
    //         return;
    //       }

    //       if (this.thongTinNguoiDung.phanVungId == 50) {
    //         var DSKhoTheoNhanVien = await this.getDSKhoTheoNhanVien({
    //           nhanVienId: this.thongTinNguoiDung.nhanVienId,
    //           donViId: this.thongTinNguoiDung.donViId,
    //         });
    //         var DSKho = DSKhoTheoNhanVien.filter(function (el) {
    //           return (
    //             el.LOAIKHO_ID == LOAI_KHO.KHO_TOTRUONG && el.TRANGTHAI == "1"
    //           );
    //         });
    //         if (DSKho.length > 0) {
    //           this.dataResponse.DSKho = DSKho;
    //           this.params.khoId == DSKho[0].khoId;
    //         } else {
    //           this.dataResponse.DSKho = [];
    //         }
    //       } else {
    //         this.$refs.refKho.ej2Instances.value = null;
    //         this.$refs.refKho.dataBind();
    //         this.params.khoId = 0;
    //         this.Enabled.Kho = false;
    //       }
    //     } else {
    //       await this.LoadThongTinThueBao();
    //       this.Enabled.Kho = true;
    //       if (this.qt_donvi) {
    //         return;
    //       }
    //       var DSKhoTheoNhanVien = await this.getDSKhoTheoNhanVien({
    //         nhanVienId: this.thongTinNguoiDung.nhanVienId,
    //         donViId: this.thongTinNguoiDung.donViId,
    //       });

    //       if (DSKhoTheoNhanVien.length == 0) {
    //         return;
    //       }

    //       if (
    //         this.thongTinNguoiDung.phanVungId != 21 &&
    //         this.thongTinNguoiDung.phanVungId != 23 &&
    //         this.thongTinNguoiDung.phanVungId != 24 &&
    //         this.thongTinNguoiDung.phanVungId != 44
    //       ) {
    //         var donViId = this.params.donViId;
    //         var DSKho = DSKhoTheoNhanVien.filter(function (el) {
    //           return (
    //             el.LOAIKHO_ID == LOAI_KHO.KHO_TOTRUONG &&
    //             el.TRANGTHAI == "1" &&
    //             el.DONVI_ID == donViId
    //           );
    //         });
    //         if (DSKho.length > 0) {
    //           this.dataResponse.DSKho = DSKho;
    //           this.params.khoId == DSKho[0].khoId;
    //         } else {
    //           this.dataResponse.DSKho = [];
    //         }
    //       } else {
    //         var donViId = this.params.donViId;
    //         var DSKho = DSKhoTheoNhanVien.filter(function (el) {
    //           return (
    //             el.LOAIKHO_ID == LOAI_KHO.KHO_TOTRUONG &&
    //             el.TRANGTHAI == "1" &&
    //             el.DONVI_ID == donViId
    //           );
    //         });

    //         if (
    //           DSKho.length > 0 &&
    //           this.thongTinNguoiDung.phanVungId != 21 &&
    //           this.thongTinNguoiDung.phanVungId != 24 &&
    //           this.thongTinNguoiDung.phanVungId != 44
    //         ) {
    //           this.dataResponse.DSKho = DSKho;
    //           this.params.khoId == DSKho[0].khoId;
    //         } else {
    //           var DSKho = await this.getDSKhoTheoDonVi({
    //             nhanVienId: this.thongTinNguoiDung.nhanVienId,
    //             donViId: this.params.donViId,
    //           });
    //           if (DSKho.length > 0) {
    //             this.dataResponse.DSKho = DSKho;
    //             this.params.khoId == DSKho[0].khoId;
    //           } else {
    //             this.dataResponse.DSKho = [];
    //           }
    //         }
    //       }
    //     }
    //   } catch (e) {
    //     this.$toast.error(e.data.message);
    //   } finally {
    //     this.loading(false);
    //   }
    // },
    onClickHSDienTu() {
      if (this.dataResponse.DSThueBao.length == 0) {
        this.$toast.error("Không có thuê bao!");
        return;
      }
      if (this.ThueBaoFocus == {}) {
        this.$toast.error("Vui lòng chọn thuê bao!");
        return;
      }

      if (this.ThueBaoFocus.MA_TB == "") {
        this.$toast.error(
          "Không có Số máy/Acc ! Không thể tra cứu hồ sơ điện tử !"
        );
        return;
      }

      let f = this.$refs.frmTraCuuHSDT;
      f.MaTb = this.ThueBaoFocus.MA_TB;
      this.$refs.popupTraCuuHSDT.show();
    },
  },

  created: async function () {
    try {
      this.loading(true);
      this.tagForm =
        this.$route.query.tagForm != undefined
          ? this.$route.query.tagForm
          : "0";
      await this.Load_Cbo();

      var vts_sohoa = await this.getTSMacDinh("TS_QLVT_SOHOA_NEW");
      this.vts_sohoa = vts_sohoa.length > 0 && vts_sohoa[0].TEN_TS;

      var giaTriTS = await this.getTSMacDinh("QT_DONVI");
      this.qt_donvi = giaTriTS.length > 0 && giaTriTS[0].TEN_TS == "1";    
      if(this.$auth.getPhanVungID() != 28){
        this.isRenderNhapXuatVT = true
      }else{
        this.isRenderNhapXuatVTV2 = true
      }  
    } catch (e) {
      this.$toast.error(e.data.message);
    } finally {
      this.loading(false);
    }
  },
  watch: {
    rowSelectedThueBao: async function (value) {
      try {
        this.loading(true);
        this.rowSelectedThueBao = value;
        await this.getDSVatTuDaQuyetToan(this.rowSelectedThueBao);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    checkAllThueBao: async function (value) {
      try {
        this.loading(true);
        this.rowSelectedThueBao = [];
        if (value) {
          for (const e of this.$refs.gridThongTinThueBao.filteredDataSource) {
            this.rowSelectedThueBao.push(e.KEY_ROW);
          }
        }
        await this.getDSVatTuDaQuyetToan(this.rowSelectedThueBao);       
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
  },
  destroyed() {},
  provide: {
    grid: [Freeze, Edit, Group, Page, Filter, Sort, Resize, Aggregate, Toolbar],
  },
};
</script>