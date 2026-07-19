<template src='./QuyetToanVatTuCongTrinh.html'></template>
<style scoped src='./QuyetToanVatTuCongTrinh.scss'></style>
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
} from "../store/quyettoanvattucongtrinh";
import select2 from "@/components/Select2.vue";
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
Vue.use(GridPlugin);

export default {
  components: { breadcrumb, appSelect2: select2 },
  name: "QuyetToanVatTuCongTrinh",
  mounted() {},

  computed: {
    ...mapState("quyettoanvattucongtrinh", statePropertyName),
    ...mapGetters("quyettoanvattucongtrinh", getterName),
  },
  data() {
    return {
      header: {
        title: "QUYẾT TOÁN VẬT TƯ CÔNG TRÌNH",
        list: [
          {
            name: "Quản lý vật tư",
            link: { name: "Ui.cards" },
          },
          {
            name: "Quyết toán vật tư công trình",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      dateConfig: {
        default: new Date(),
        formatDay: "dd/MM/yyyy",
        formatDayTime: "dd/MM/y HH:mm:ss",
        waterMark: "Chọn ngày",
      },

      DSVatTu_DVT: [],
      DSKieuTbi: [],
      DSKhoGiao: [],
      DSKhoNhan: [],
      DSLoHang: [],
      DSCongTrinh: [],
      DSVatTuCap: [],
      DSLichSuVatTu: [],
      MucDichXuat: [],
      MucDichNhapBH: [],
      KTCapNhatPhieuVatTu: "",
      KTKiemTraHanChungTu: [],
      chungTuId: "",
      KTQuyetToanVatTuCT: "",
      KTSerialThuHoi: "",
      KTKhoThuHoi: "",
      KTThuHoiVTCT: "",
      KTXoaChungTu: "",
      KTCapMaVT: "",
      KTCapNhapMaVT: "",
      SuaVTId: "",
      rowDoiVatTu: {},
      params: {
        maChungTu: "",
        kieuTbiId: "",
        ngayCTTu: new Date(),
        trangThai: 2,
        isTuNgay: false,
        tuNgay: new Date(new Date().getTime() - 7 * 24 * 60 * 60 * 1000),
        denNgay: new Date(),
        khoGiaoId: "-1",
        khoCTId: "-1",
        noiDungTra: "",
        loHangId: "",
      },
      Keys: {
        Insert: 45,
        Delete: 46,
        Space: 32,
        V: 86,
      },
      ngayCT: new Date(),
      noiDungGiao: "",
      DSCongTrinhCheckBox: [],
      thongTinNguoiDung: {
        nhanVienId: this.$root.token.getNhanVienID(), //this.$root.token.getNhanVienID(),
        nguoiCN: this.$root.token.getUserName(),
        mayCN: "DESKTOP-IDG67LD", //this.$auth.MAYCN
        ipCN: "10.59.90.128", //this.$auth.IPCN
        donViId: this.$root.token.getDonViID(),
      },
      classIconHiddenBox: "icon fa fa-angle-up",
      DSTrangthai: [
        { ID: 2, TRANGTHAI: "Mới tiếp nhận" },
        { ID: 4, TRANGTHAI: "Hoàn thành" },
      ],
      visible: {
        colEdit: false,
        colLHCu: false,
        colLHMoi: false,
        colDonGia: false,
        colSP: false,
        colNCT: false,
        btnXacNhan: true,
        btnHuyXN: false,
        btnTraLai: false,
        btnHoanCong: false,
        boxSearch: true,
      },
      enable: {
        btnLayTT: false,
      },
      readonly: {
        cboKieuGiao: true,
        cboTrangThai: true,
      },
      mucdich_id: 0,
      chungtu_id: 0,
      dvi_nhan_id: -1,
      DSLichSuVTChecked: [],
      ds_tbi: [],
      classCongTrinh: "active",
      classXacNhan: "",
      groupVatTuCap: { showDropArea: false, columns: ["NGAY_CAP"] },
      groupLichSuVatTu: {
        showDropArea: false,
        columns: ["TEN_CT", "NGAY_CAP"],
      },
      filterOptions: {
        enableCaseSensitivity: false,
        mode: "Immediate",
        ignoreAccent: true,
      },
      templateOptions: {
        create: function (args) {
          if (["STT", "btnXoaVT", "btnEditTbi"].includes(args.column.field)) {
            return '<span class="fa input-icon-right"></span>';
          }

          return (
            '<div class="input-icon-right"><input id="' +
            args.column.field +
            '_filterBarcell" type="search" class="form-control"> <span class="icon nc-icon-outline ui-1_zoom"></span></div>'
          );
        },
        write: function (args) {},
      },
      btnTBTemplate: function () {
        return {
          template: Vue.component("btnTBTemplate", {
            template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" v-if="btnTB" @click="btnSuaVT_ButtonClick">
                                  <span class="act -ap icon-chevron-thin-down"></span>
                              </button>`,
            data: function () {
              return { data: {} };
            },
            computed: {
              btnTB: {
                get() {
                  if (
                    this.data.SPVT == 0 ||
                    (this.data.KTVT != "" && this.data.KTVT != null)
                  ) {
                    return true;
                  }
                  return false; // true =>test
                },
              },
            },
            methods: {
              async btnSuaVT_ButtonClick() {
                console.log(this.data, " = data");
                await this.$parent.$parent.$parent.btnSuaVT_ButtonClick(
                  this.data
                );
              },
            },
          }),
        };
      },
      cRowSelected: function () {
        return {
          template: Vue.component("columntemplate", {
            template: '<span class="fa"></span>',
          }), // end of template
        };
      },
      rowCheckedCT: [],
      checkAllCT: false,
      CheckboxCT: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"                                    
                                    v-model="parent.rowCheckedCT"
                                    :value="data.CONGTRINH_ID"
                                    :disabled="!parent.visible.btnXacNhan"/>
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

      CheckboxAllCT: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAllCT" class="uncheck" :disabled="!parent.visible.btnXacNhan"/>
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
    };
  },
  methods: {
    ...mapActions("quyettoanvattucongtrinh", actionName),
    ...mapMutations("quyettoanvattucongtrinh", mutationName),

    // chuyển Date thành type string
    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    f_StringToDate(value, format = "DD/MM/YYYY") {
      return moment(value, format).toDate();
    },
    f_DateTimeToString: function (value, format = "DD/MM/YYYY HH:mm:ss") {
      return moment(value).format(format);
    },
    async frmQuyetToan_VTCT_Load() {
      // try {
      await this.LOAD_COMBO();
      // } catch (error) {
      //     this.$toast.error("Có lỗi khi nạp danh sách combo")
      // }
    },
    async LOAD_COMBO() {
      let DSKieuTbi = await this.getDanhMucVT_KieuTB();
      this.DSKieuTbi = DSKieuTbi.filter(function (el) {
        return el.kieuTbiId == 1 || el.kieuTbiId == 3;
      });
      this.DSKieuTbi.length
        ? (this.params.kieuTbiId = this.DSKieuTbi[0].kieuTbiId)
        : "";
      await this.NAP_DS_KHO();
      this.DSLoHang = await this.getDSLoHang();
      this.DSLoHang.length
        ? (this.params.loHangId = this.DSLoHang[0].loHang)
        : "";
    },
    async DSLichSuVatTu_KeyDown(args) {
      console.log(args, " = keyPressed");
      if (args.name == "keyPressed" && args.keyCode == this.Keys.Space) {
        for (let e of this.DSLichSuVTChecked) {
          e.LH = this.params.loHangId;
          let LoHangChon = this.DSLoHang.filter(
            (e) => e.loHang == this.params.loHangId
          );
          if (LoHangChon.length > 0) {
            e.DONGIA = LoHangChon[0].donGia;
          }
        }
        this.$refs.gridLichSuVatTu.grid.refreshColumns();
      }
    },

    async NAP_DS_KHO() {
      try {
        if (!this.params.kieuTbiId || !this.params.trangThai) {
          this.$toast.error(
            "Không có thông tin về combo kiểu thiết bị/ trạng thái"
          );
          return;
        }

        this.DSKhoGiao = await this.getDSKhoGiao({
          kieu: this.params.kieuTbiId, //this.params.kieuTbiId,
          nhan_vien_id: this.thongTinNguoiDung.nhanVienId,
          status: this.params.trangThai,
        });
        this.DSKhoGiao.length
          ? (this.params.khoGiaoId = this.DSKhoGiao[0].khoID)
          : "-1";

        this.DSKhoNhan = await this.getDSKhoNhan({
          kieu: this.params.kieuTbiId,
          nhan_vien_id: this.thongTinNguoiDung.nhanVienId,
        });
        this.DSKhoNhan.length
          ? (this.params.khoCTId = this.DSKhoNhan[0].khoID)
          : "-1";
      } catch (error) {
        this.$toast.error("Có lỗi khi nạp danh sách kho");
      }
    },
    async onChangeKieuTbi() {
      try {
        this.visible.colEdit = false;
        if (this.params.trangThai == "2" && this.params.kieuTbiId == "3") {
          this.visible.colEdit = true;
        }
        this.visible.colLHMoi = false;
        this.visible.colLHCu = true;
        this.visible.colDonGia = false;
        if (this.params.trangThai == "2" && this.params.kieuTbiId == "3") {
          this.visible.colLHCu = false;
          this.visible.colLHMoi = true;
          this.visible.colDonGia = true;
        }

        await this.NAP_DS_KHO();
        this.TIMKIEM(0);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async onChangeTrangThai() {
      try {
        this.loading(true);
        if (!this.params.trangThai) {
          return;
        }
        this.params.isTuNgay = false;
        if (this.params.trangThai == "4") {
          this.visible.colSP = true;
          this.visible.colNCT = true;
          this.params.isTuNgay = true;
        } else {
          this.visible.colSP = false;
          this.visible.colNCT = false;
        }
        this.visible.colLHMoi = false;
        this.visible.colLHCu = true;
        this.visible.colDonGia = false;
        if (this.params.trangThai == "2" && this.params.kieuTbiId == "3") {
          this.visible.colLHCu = false;
          this.visible.colLHMoi = true;
          this.visible.colDonGia = true;
        }

        await this.NAP_DS_KHO();
        await this.TIMKIEM(0);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },

    async refreshKhoGiao() {
      this.loading(true);
      await this.NAP_DS_KHO();
      this.loading(false);
    },

    async refreshKhoNhan() {
      this.loading(true);
      await this.NAP_DS_KHO();
      this.loading(false);
    },

    dataBound(data) {
      // this.$refs.tableDSCongTrinh.selectRow(0,false)
      this.$refs.gridLichSuVatTu.selectRow(0, false);
    },
    async TIMKIEM(kieu) {
      // try {
      let maChungTu = this.params.maChungTu == "" ? "0" : this.params.maChungTu;
      let DSCongTrinh = await this.getDSQuyetToanCT({
        kieuTBiId: this.params.kieuTbiId,
        trangThai: this.params.trangThai,
        timNgay: this.params.isTuNgay ? 1 : 0,
        tuNgay: this.f_DateToString(this.params.tuNgay),
        denNgay: this.f_DateToString(this.params.denNgay),
        khoId: this.params.khoGiaoId,
        maCT: maChungTu,
      });
      DSCongTrinh.forEach((element) => {
        element.NGAYLAP = element.NGAYLAP.replaceAll("-", "/").slice(0, 10);
      });
      this.rowCheckedCT = [];
      this.checkAllCT = false;
      this.DSCongTrinh = DSCongTrinh;

      if (this.DSCongTrinh.length == 0) {
        this.params.khoGiaoId = "-1";
        this.params.khoCTId = "-1";
        this.DSVatTuCap = [];
        this.DSLichSuVatTu = [];
      }
    },
    async onClickLayThongTin() {
      try {
        if (this.params.khoGiaoId == "-1") {
          await this.$bvModal
            .msgBoxConfirm(
              "Không có dữ liệu kho giao.Bạn có muốn load toàn bộ danh sách kho không?",
              {
                okVariant: "success",
                okTitle: "Đồng ý",
                cancelTitle: "Hủy",
                centered: true,
              }
            )
            .then(async (value) => {
              if (value) {
                await this.NAP_DS_KHO();
                if (this.params.khoGiaoId) {
                  return;
                }
              }
            })
            .catch((err) => {
              // An error occurred
            });
        }
        await this.TIMKIEM(1);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },

    async XACNHAN_HUY_DS(kieu) {
      if (!this.DSCongTrinh.length) {
        this.$toast.error("Không có dữ liệu công trình để thực hiện");
        return;
      }

      if (this.rowCheckedCT.length == 0) {
        this.$toast.error("Bạn chưa chọn công trình để thực hiện");
        return;
      }
      let jsonlist = [];
      this.rowCheckedCT.forEach((a) => jsonlist.push({ CONGTRINH_ID: a }));
      switch (kieu) {
        case 1:
          let DSLichSuVatTu = await this.getDSLichSuVatTu({
            dsCT: JSON.stringify(jsonlist),
            kieuTbiId: this.params.kieuTbiId,
            status: this.params.trangThai,
            nhanVienId: this.thongTinNguoiDung.nhanVienId,
          });
          DSLichSuVatTu.forEach((element) => {
            element.NGAY_CAP = element.NGAY_CAP.replaceAll("-", "/").slice(
              0,
              10
            );
          });
          this.DSLichSuVatTu = DSLichSuVatTu;

          this.classXacNhan = "active";
          this.classCongTrinh = "";

          this.visible.btnXacNhan = false;
          this.visible.btnHuyXN = true;
          this.enable.btnLayTT = false;
          if (this.params.trangThai != "4") {
            this.visible.btnTraLai = true;
            this.visible.btnHoanCong = true;
          }
          this.readonly.cboKieuGiao = true;
          this.readonly.cboTrangThai = true;
          break;
        case 3:
          this.DSLichSuVatTu = [];
          this.visible.btnXacNhan = true;
          this.visible.btnHuyXN = false;
          this.visible.btnLayTT = true;
          this.visible.btnTraLai = false;
          this.visible.btnHoanCong = false;
          this.readonly.cboKieuGiao = false;
          this.readonly.cboTrangThai = false;
          break;
      }
    },
    async XacNhanSuaVT() {
      try {
        this.$bvModal.hide("popupSuaVT");
        if (
          this.rowDoiVatTu.THIETBI_ID == "" ||
          this.rowDoiVatTu.THIETBI_ID == null
        ) {
          let data = {
            PHIEUVT_ID: this.rowDoiVatTu.PHIEUVT_ID,
            VATTU_ID: this.SuaVTId,
            KHO_KH: this.params.khoGiaoId,
          };
          let ketQua = await this.ThayDoiVatTu({
            data: JSON.stringify(data),
            nguoiCN: this.$auth.getUserName(),
            mayCN: "test",
            ipCN: "test",
          });
          if (ketQua != "1") {
            this.$toast.error(ketQua);
          } else {
            this.$toast.success("Đổi vật tư thành công");
            await this.XACNHAN_HUY_DS(1);
          }
        } else {
          let data = {
            PHIEUVT_ID: this.rowDoiVatTu.PHIEUVT_ID,
            VATTU_ID: this.SuaVTId,
            THIETBI_ID: this.rowDoiVatTu.THIETBI_ID,
          };
          let ketQua = await this.capNhatMaVT({
            data: JSON.stringify(data),
            noiDung: "",
          });
          this.$toast.success("Đổi vật tư thành công");
          await this.XACNHAN_HUY_DS(1);
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async btnSuaVT_ButtonClick(data) {
      // Tạm thời rem getDanhMucVT_DVT giảm thời gian load
      this.rowDoiVatTu = data;
      this.SuaVTId = data.VATTU_ID;
      this.DSVatTu_DVT = await this.getDanhMucVT_DVT();
      this.$bvModal.show("popupSuaVT");
    },
    async onClickXacNhan() {
      try {
        this.loading(true);
        await this.XACNHAN_HUY_DS(1);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async onClickHuyXacNhan() {
      try {
        this.loading(true);
        await this.XACNHAN_HUY_DS(3);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async onClickTraLai() {
      try {
        await this.TRAPHIEU();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async onClickHoanThanh() {
      try {
        this.loading(true);
        await this.HOANTHIEN();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async HOANTHIEN() {
      var ma_muc_dich = "2.QT.1";
      if (this.params.kieuTbiId == "1") {
        ma_muc_dich = "2.QT.1";
      } else {
        ma_muc_dich = "1.4";
      }

      this.MucDichXuat = await this.getMucDichXuat({
        ma_muc_dich: ma_muc_dich,
      });
      if (!this.MucDichXuat.length) {
        this.$toast.error("Không lấy được id mục đích " + ma_muc_dich);
        return;
      }
      this.mucdich_id = this.MucDichXuat[0].mucDichId;

      if (this.params.khoCTId == "-1" || this.params.khoGiaoId == "-1") {
        this.$toast.error("Chưa chọn kho giao/đến không thể hoàn thiện");
        return;
      }

      if (this.DSKhoNhan.length) {
        let t = this.DSKhoNhan.find((a) => a.khoID === this.params.khoCTId);
        if (!t) {
          this.$toast.error("Không tìm thấy kho đến");
          return;
        }
        if (!t.donViId) {
          this.$toast.error("Kho nhận không có đơn vị. Bạn hãy kiểm tra lại");
          return;
        }
        this.dvi_nhan_id = parseInt(t.donViId);
      }
      if (this.dvi_nhan_id == -1) {
        this.$toast.error("Kho nhận không có đơn vị. Bạn hãy kiểm tra lại");
        return;
      }
      if (!this.DSLichSuVatTu.length) {
        this.$toast.error("Không có dữ liệu vật tư để thực hiện");
        return;
      }
      if (this.params.kieuTbiId == "3") {
        if (this.DSLichSuVatTu.find((a) => !a.loHang)) {
          this.$toast.error("Bạn phải nhập hết lô hàng");
          return;
        }
      }

      this.$bvModal.show("popupNhapNoiDung");
    },
    async onClickXacNhanNoiDung() {
      try {
        this.$bvModal.hide("popupNhapNoiDung");

        this.loading(true);
        let dsvattu = [];
        this.DSLichSuVatTu.forEach((a) =>
          dsvattu.push({
            PHIEUVT_ID: parseInt(a.PHIEUVT_ID),
            TUVT_ID: a.TUVT_ID,
            SOLUONG: a.SOLUONG,
            THIETBI_ID: parseInt(a.THIETBI_ID) || "",
            SERIAL: a.SERIAL,
            MA_TBI: a.MA_TBI,
            LOHANG: a.LOHANG,
            VATTU_ID: parseInt(a.VATTU_ID),
            LH: a.LH,
            DONGIA: a.DONGIA,
            NGAY_CAP: a.NGAY_CAP,
            CONGTRINH_ID: a.CONGTRINH_ID,
          })
        );

        if (this.params.kieuTbiId == "1") {
          // Quyết toán

          if (
            !(await this.KiemTraHanChotCT(
              this.f_DateToString(this.ngayCT, "DD/MM/YYYY"),
              parseInt(this.params.khoGiaoId),
              parseInt(this.params.khoCTId)
            ))
          ) {
            this.loading(false);
            return;
          }

          let KetQua = await this.QuyetToanVatTuCTAll({
            ds_vattu: dsvattu,
            mucdich_id: this.mucdich_id,
            kho_giao_id: this.params.khoGiaoId,
            kho_nhan_id: this.params.khoCTId,
            donvi_nhan_id: this.dvi_nhan_id,
            ngay_tao: this.f_DateTimeToString(this.ngayCT),
            nd_giao: this.noiDungGiao,
          });
          this.$toast.success("Đã hoàn tạm ứng thành công");
          await this.XACNHAN_HUY_DS(3);
          await this.onClickLayThongTin();
        }
        // Thu hồi
        else {
          if (
            !(await this.KIEMTRA_TH(
              this.DSVatTuCap,
              this.f_DateTimeToString(this.ngayCT)
            ))
          ) {
            return;
          }

          let KetQua = await this.ThuHoiCTAll({
            ds_tbi: this.ds_tbi,
            ds_vattu: dsvattu,
            mucdich_id: this.mucdich_id,
            kho_giao_id: this.params.khoGiaoId,
            kho_nhan_id: this.params.khoCTId,
            donvi_nhan_id: this.dvi_nhan_id,
            ngay_tao: this.f_DateTimeToString(this.ngayCT),
            nd_giao: this.noiDungGiao,
          });
          this.$toast.success("Đã hoàn tạm ứng thành công");
          await this.XACNHAN_HUY_DS(3);
          await this.onClickLayThongTin();
        }
        this.loading(false);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async KIEMTRA_TH(ds_chon, dtpNgay) {
      let kt = false;
      this.ds_tbi = [];
      ds_chon.forEach((a) => {
        if (!a.THIETBI_ID) {
          this.ds_tbi.push({
            PHIEUVT_ID: parseInt(a.PHIEUVT_ID),
            VATTU_ID: parseInt(a.VATTU_ID),
            KHO_KH: parseInt(a.KHO_KH),
          });
          kt = true;
        }
      });
      let ds_xml = [];
      ds_chon.forEach((a) => {
        if (a.SERIAL) {
          ds_xml.push({ sr: a.SERIAL });
        }
      });
      this.KTSerialThuHoi = await this.KiemTraSerialThuHoi({
        serial: JSON.stringify(ds_xml),
      });
      if (this.KTSerialThuHoi != "ok") {
        let loi = this.KTSerialThuHoi.split("/r/n");
        let dston = loi.filter((a) => a != "");
        let d = dston.length;
        this.$toast.error(
          "Có " +
            d.toString() +
            " serial đang tồn tại ở trong kho. Bạn có muốn xuất excel những serial đó không?"
        );
        alert(dston.join("','"));
        return false;
      }
      let ds = [];
      for (let j = 0; j < ds_chon.length; j++) {
        if (ds_chon[j].SERIAL) {
          if (ds_chon[j].NGAY_CAP > dtpNgay) {
            this.$toast.error(
              "Ngày thu hồi (" +
                ds_chon[j].NGAY_CAP +
                ") của serial:" +
                ds_chon[j].SERIAL +
                " lớn hơn so với ngày nhập chứng từ"
            );
            return false;
          }
          if (ds_chon[j].THIETBI_ID) {
            ds.push({
              PHIEUVT_ID: ds_chon[j].PHIEUVT_ID,
              THIETBI_ID: ds_chon[j].THIETBI_ID,
              DONGIA: ds_chon[j].DONGIA,
              SERIAL: ds_chon[j].SERIAL,
            });
          }
        }
      }
      this.KTKhoThuHoi = await this.KiemTraKhoThuHoi({
        data: JSON.stringify(ds),
        khoKH: parseInt(this.p_khogiao),
        nguoiCN: this.thongTinNguoiDung.nguoiCN, //ttnd.ma_nd
        mayCN: this.thongTinNguoiDung.mayCN, //ttnd.may_cn
        ipCN: this.thongTinNguoiDung.ipCN, //ttnd.ip
      });
      if (this.KTKhoThuHoi != "ok") {
        this.$toast.error(this.KTKhoThuHoi);
        return false;
      }
      return true;
    },

    async KiemTraHanChotCT(ngay_tao, tukho, denkho) {
      let vkho_nhan_id = 0;
      let vkho_giao_id = 0;
      if (tukho != 0) {
        vkho_giao_id = tukho;
      }
      if (denkho != 0) {
        vkho_nhan_id = denkho;
      }

      this.KiemTraHanChungTu = await this.KiemTraHanChungTu({
        chungTuId: -1, //DHSX đang hardcode -1
        khoGiaoId: vkho_giao_id,
        khoNhanId: vkho_nhan_id,
        ngayCt: ngay_tao,
      });
      if (
        this.KTKiemTraHanChungTu.length > 0 &&
        this.KTKiemTraHanChungTu[0] != "1"
      ) {
        this.$toast.error("Đã quá hạn sửa chứng từ!");
        return false;
      }
      return true;
    },
    async TRAPHIEU() {
      if (!this.DSLichSuVatTu.length) {
        this.$toast.error("Không có dữ liệu vật tư để thực hiện ");
        return;
      }
      if (!this.params.noiDungTra) {
        this.$toast.error("Bạn chưa nhập nội dung trả ");
        return;
      }

      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm("Bạn có muốn trả danh sách vật tư đã chọn không?", {
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
      try{
        this.loading(true)
        let t = this.DSLichSuVTChecked;
        if (!t.length) {
          this.$toast.error("Bạn chưa chọn danh sách vật tư cần trả ");        
          return;
        }
        let jsonlist = [];
        t.forEach((a) => jsonlist.push({ PHIEUVT_ID: a.PHIEUVT_ID }));

        this.KTCapNhatPhieuVatTu = await this.CapNhatPhieuVatTu({
          data: JSON.stringify(jsonlist),
          noiDung: this.params.noiDungTra,
        });

        if (this.KTCapNhatPhieuVatTu == "1") {
          this.$toast.success("Đã hủy giao vật tư thành công");
          await this.XACNHAN_HUY_DS(1);
        } else {
          this.$toast.error(this.KTCapNhatPhieuVatTu);
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    

    DSLichSuVatTuCheckbox: function (dataItem) {
      this.DSLichSuVTChecked = dataItem;
    },
    async gridDSCongTrinhSelected(row) {
      try {
        this.loading(true);
        if (row) {
          var dsCT = [];
          dsCT.push({ CONGTRINH_ID: row.CONGTRINH_ID });
          let DSVatTuCap = await this.getDSPhieuVatTuQTCT({
            dsCT: JSON.stringify(dsCT),
            kieuTbiId: this.params.kieuTbiId,
            status: this.params.trangThai,
            nhanVienId: this.thongTinNguoiDung.nhanVienId,
          });
          DSVatTuCap.forEach((element) => {
            element.NGAY_CAP = element.NGAY_CAP.replaceAll("-", "/").slice(
              0,
              10
            );
          });
          this.DSVatTuCap = DSVatTuCap;
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    gridViewLichSuVatTu_CustomRowCellEdit() {},

    gridLichSuVatTu_KeyDown() {},
    onClickHiddenBox() {
      this.classIconHiddenBox = this.visible.boxSearch
        ? "icon fa fa-angle-down"
        : "icon fa fa-angle-up";
      this.visible.boxSearch = this.visible.boxSearch ? false : true;
    },
    onClickBoxCongTrinh() {
      this.classCongTrinh = "active";
      this.classXacNhan = "";
    },
    onClickBoxXacNhan() {
      this.classCongTrinh = "";
      this.classXacNhan = "active";
    },
    clearData() {
      this.DSVatTu_DVT = [];
      this.DSKieuGiao = [];
      this.DSKhoGiao = [];
      this.DSKhoNhan = [];
      this.DSCongTrinh = [];
      this.rowCheckedCT = [];
      this.checkAllCT = false;
      this.DSLoHang = [];
      this.DSVatTuCap = [];
      this.DSLichSuVatTu = [];
      this.MucDichXuat = [];
      this.MucDichNhapBH = [];
      this.KTCapNhatPhieuVatTu = "";
      this.KTKiemTraHanChungTu = [];
    },
  },

  created: async function () {
    try {
      this.loading(true);
      this.clearData();
      await this.frmQuyetToan_VTCT_Load();
    } catch (e) {
      this.$toast.error(e.data.message);
    } finally {
      this.loading(false);
    }
  },
  watch: {
    rowCheckedCT: function (value) {
      console.log(this.rowCheckedCT, " = rowCheckedCT");
      this.rowCheckedCT = value;
    },
    checkAllCT: function (value) {
      if (value) {
        for (const e of this.DSCongTrinh) {
          if (!this.rowCheckedCT.includes(e.CONGTRINH_ID)) {
            this.rowCheckedCT.push(e.CONGTRINH_ID);
          }
        }
      } else {
        this.rowCheckedCT = [];
      }
      console.log(this.rowCheckedCT, " = rowCheckedCT");
    },
  },
  destroyed() {},
  provide: {
    grid: [Freeze, Edit, Group, Page, Filter, Sort, Resize, Aggregate, Toolbar],
  },
};
</script>