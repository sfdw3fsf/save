<template src="./SuaChungTu.html"></template>

<script>
import API from "../api/SuaChungTu.js";
import moment from "moment";
import xlsx from "xlsx";
import Vue from "vue";
import { TooltipPlugin } from "@syncfusion/ej2-vue-popups";
Vue.use(TooltipPlugin);
import { Tooltip } from "@syncfusion/ej2-popups";
export default {
  name: "frmSuaCT",
  data() {
    return {
      target: ".main-wrapper",
      animationSettings: { effect: "Zoom" },
      tooltip: Tooltip,
      gParams: {
        dtChungTu: [],
        dtChitiet: [],
        dt_temp: [],
        vchungtu_id: "",
        loaict_id: "",
        kieuphieu: "",
        xacnhan: false,
        load_file_again: false,
        chuaht_or_khac_2xcp: false,
        makho: "",
        loaikho: "",

        cboKieuPhieu: "",
        txtlenhVB: "",
        dtpNgayVB: new Date(),
        dtpNgayXuat: new Date(),
        txtSo_HD: "",
        dtpNgay_HD: new Date(),
        dtpNgayNhan: new Date(),
        txtND: "",
        dtChungTuRowChanged: {},
        dtVatTuRowChanged: {},
        txtTKDUAll: "",
        txtTK_Kho_All: "",
        cboLoaiKho: "",
        cboChatLuong: "",
        attachFile: "",
        kich_thuoc_file: 0,
      },
      gridChungTu: [],
      dtVatTu: [],
      cboKieuPhieu: [],
      cboLoaiKho: [],
      cboChatLuong: [],
      sumTemplate: function () {
        return {
          template: Vue.component("sumTemplate", {
            template: `<span>Sum: {{data.Sum}}</span>`,
            data: function () {
              return { data: { data: {} } };
            },
          }),
        };
      },
      aggregatesColumns: [
        {
          field: "TIEN",
          type: "Sum",
          format: "N0",
          footerTemplate: this.sumTemplate,
        },
      ],
      visible: {
        layoutNoidung: true,
      },
      rowSelected: {
        gridChungTuRowSelected: {},
        gridVatTuRowSelected: {},
      },
      default: {
        isFirtChungTuSelectedRow: false,
      },
    };
  },
  methods: {
    openDialog() {
      //mockData
      // this.gParams.chuaht_or_khac_2xcp = false;
      // this.gParams.dtChungTu = [
      //   {
      //     CHUNGTU_ID: 3767,
      //     SOPHIEU: "16/46.BAOHANH_ANSV-N-22",
      //     LOAI_CT: "Nhập",
      //     NGAY_CT: "13/05/2022",
      //     TIEN: 0,
      //     NGUOI_CN: 11632,
      //     NGAY_VB: "13/05/2022",
      //     SO_VB: "",
      //     SO_HOADON: "",
      //     NGAY_HOADON: "13/05/2022",
      //     MUCDICH_ID: 0,
      //   },
      // ];
      // this.gParams.kieuphieu = 10100;
      // this.gParams.vchungtu_id = 3767;
      // this.gParams.loaict_id = 1;
      this.$refs.dlgSuaChungTu.show();
    },
    closeForm() {
      this.$emit("close", {
        xacnhan: this.gParams.xacnhan ? true : false,
        load_file_again: this.gParams.load_file_again ? true : false,
      });
      this.$refs.dlgSuaChungTu.hide();
    },
    async dialogOpen() {
      if (this.gParams.chuaht_or_khac_2xcp) {
        this.visible.layoutNoidung = false;
      }
      await this.load_ChungTu();
      await this.cboKieuPhieuChanged();
      let dsChatLuong = await (
        await API.getDsChatLuong(this.axios, {})
      ).data.data;
      this.cboChatLuong = dsChatLuong;
      this.gParams.cboChatLuong = this.setFirstValue(
        this.cboChatLuong,
        "CHATLUONG_ID"
      );
    },
    reset() {
      this.gParams.dtChungTu = [];
      this.gParams.dt_temp = [];
      this.gParams.vchungtu_id = "";
      this.gParams.kieuphieu = "";
    },
    isNullOrEmpty(value) {
      return value === null || value === "";
    },
    dateToString(value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    stringToDate(value, format = "DD/MM/YYYY") {
      return moment(value, format).toDate();
    },
    setFirstValue(cbo, key) {
      if (cbo.length) {
        return cbo[0][key];
      } else {
        return "";
      }
    },
    async Load_ds_vattu() {
      try {
        this.$root.loading(true);
        this.gParams.dtChitiet = await (
          await API.getDSVTChungTu(this.axios, {
            chungTuId: this.gParams.vchungtu_id,
          })
        ).data.data;
        console.log(this.gParams.dtChitiet);
        this.dtVatTu = this.gParams.dtChitiet;
        console.log(this.dtVatTu);
      } catch (error) {
        console.log(error);
      } finally {
        this.$root.loading(false);
      }
    },
    async load_ChungTu() {
      try {
        this.$root.loading(true);
        this.cboKieuPhieu = await (
          await API.getCboKieuPhieu(this.axios, {
            loaiCtId: this.gParams.loaict_id,
          })
        ).data.data;
        this.gParams.cboKieuPhieu = this.gParams.kieuphieu;
        console.log("this.gParams.vchungtu_id", this.gParams.vchungtu_id);
        let s = this.gParams.dtChungTu.filter(
          (e) => e.CHUNGTU_ID == this.gParams.vchungtu_id
        );
        console.log("s", s);
        if (!s.length) {
          this.gridChungTu = [];
          this.dtVatTu = [];
          return;
        }
        this.gridChungTu = s;
        this.dtVatTu = this.gParams.dtChitiet;
      } catch (error) {
        console.log(error);
      } finally {
        this.$root.loading(false);
      }
    },
    getNgayVB(parent) {
      return function () {
        return {
          template: {
            components: {},
            template: `
                          <ejs-datepicker
                                :placeholder="'Ngày giao'"
                                v-model="value"
                                format="dd/MM/yyyy"
                                :allowEdit="false"
                                :width="'100%'"
                                class="redcolor"
                                @change="valueChange"
                            >
                          </ejs-datepicker>
                        `,
            data() {
              return {
                parent: parent, //this
                data: {}, //rowData
                value: "",
              };
            },
            mounted() {
              console.log(this.parent);
              console.log(this.data);
              this.value = this.parent.stringToDate(this.data.NGAY_VB);
              console.log(this.value);
            },
            methods: {
              valueChange(e) {
                console.log(e.value);
                this.parent.gridChungTu.forEach((item) => {
                  if (item.CHUNGTU_ID == this.data.CHUNGTU_ID) {
                    item.NGAY_VB = this.parent.dateToString(e.value);
                  }
                });
              },
            },
          },
        };
      };
    },
    getNgayHoaDon(parent) {
      return function () {
        return {
          template: {
            components: {},
            template: `
                          <ejs-datepicker
                                :placeholder="'Ngày giao'"
                                v-model="value"
                                format="dd/MM/yyyy"
                                :allowEdit="false"
                                :width="'100%'"
                                class="redcolor"
                                @change="valueChange"
                            >
                          </ejs-datepicker>
                        `,
            data() {
              return {
                parent: parent, //this
                data: {}, //rowData
                value: "",
              };
            },
            mounted() {
              console.log(this.parent);
              console.log(this.data);
              this.value = this.parent.stringToDate(this.data.NGAY_HOADON);
            },
            methods: {
              valueChange(e) {
                this.parent.gridChungTu.forEach((item) => {
                  if (item.CHUNGTU_ID == this.data.CHUNGTU_ID) {
                    item.NGAY_HOADON = this.parent.dateToString(e.value);
                  }
                });
              },
            },
          },
        };
      };
    },
    onDtChungTuCellSaved(args) {
      console.log("onDtChungTuCellSaved", args);
      if (args) {
        if (args.columnName == "SO_VB") {
          this.gridChungTu.forEach((e) => {
            if ((e.SOPHIEU = args.rowData.SOPHIEU)) {
              e["SO_VB"] = args.value;
              console.log("   = args.value;", args.value);
            }
          });
        }
        if (args.columnName == "SO_HOADON") {
          this.gridChungTu.forEach((e) => {
            if ((e.SOPHIEU = args.rowData.SOPHIEU)) {
              e["SO_HOADON"] = args.value;
            }
          });
        }
      }
    },

    tsbtnGhiLai() {
      this.CAPNHAT();
    },
    async btnFilePath() {
      try {
        this.$root.loading(true);
        if (!this.gParams.vchungtu_id) {
          return this.$toast.error("Không tìm thấy chứng từ !");
        }
        let check = await (
          await API.getCheckFile(this.axios, {
            chungTuId: this.gParams.vchungtu_id,
          })
        ).data.data;
        if (check.length) {
          if (check[0]["FILE_CV"]) {
            return this.$toast.error(
              "Chứng từ đã có file VB ! Không thể thêm file VB !"
            );
          }
        }
        this.$refs.filePath.click();
      } catch (error) {
        console.log(error);
      } finally {
        this.$root.loading(false);
      }
    },
    async onChangeFile(e) {
      try {
        this.$root.loading(true);
        const files = e.target.files;
        console.log(files);
        if (!files.length) {
          return;
        }
        let formData = new FormData();
        this.gParams.kich_thuoc_file =
          parseFloat(e.target.files[0].size / 1024).toFixed(2) + "MB";
        formData.append("file", e.target.files[0]);
        let response = await API.uploadFile(this.axios, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
          .then(async (response) => {
            if (response.data.error_code === "BSS-00000000") {
              let res = await API.updateFileCongVan(this.axios, {
                chungTuId: this.gParams.vchungtu_id,
                result: response.data.data.message.relativeUrl,
              });
              this.gParams.load_file_again = true;
              this.$toast.success("Upload file thành công!");
            }
          })
          .catch(function () {
            this.gParams.load_file_again = false;
            this.$toast.error("File upload failed!");
          });
      } catch (error) {
        console.log(error);
      } finally {
        this.$root.loading(false);
      }

      // fileReader.onload = async (ev) => {
      //   try {
      //     const data = ev.target.result;
      //   } catch (e) {
      //     console.log(e);
      //     return this.$toast.error("Kiểm tra dữ liệu file đầu vào!");
      //   }
      //   if (this.dt_docfile.length > 0) {
      //     this.$toast.success(
      //       `Có ${this.dt_docfile.length} thành phần từ file`
      //     );
      //   }
      // };
      // fileReader.readAsBinaryString(files[0]);
    },
    onDtChungTuRowSelected(value) {
      console.log(value);
    },
    async onDtChungTuRowChanged(value) {
      //  console.log("onDtChungTuRowChanged", value);
      this.gParams.dtChungTuRowChanged = value;
      if (!this.default.isFirtChungTuSelectedRow && value) {
        this.default.isFirtChungTuSelectedRow = true;
        await this.Load_ds_vattu();
        this.gParams.txtlenhVB = value.SO_VB;
        this.gParams.cboKieuPhieu = value.LOAIPHIEU_ID
          ? parseInt(value.LOAIPHIEU_ID)
          : 0;
        let d_sngay = await (
          await API.getThongTinNgayChungTu(this.axios, {
            chungTuId: this.gParams.vchungtu_id,
          })
        ).data.data;
        if (d_sngay.length) {
          this.gParams.dtpNgayVB = new Date();
          this.gParams.dtpNgay_HD = new Date();
          this.gParams.dtpNgayNhan = new Date();
          this.gParams.dtpNgayXuat = new Date();
          if (d_sngay[0]["NGAY_VB"]) {
            this.gParams.dtpNgayVB = this.stringToDate(d_sngay[0]["NGAY_VB"]);
          }
          if (d_sngay[0]["NGAY_HOADON"]) {
            this.gParams.dtpNgay_HD = this.stringToDate(
              d_sngay[0]["NGAY_HOADON"]
            );
          }
          if (d_sngay[0]["NGAYGUI_TT"]) {
            this.gParams.dtpNgayXuat = this.stringToDate(
              d_sngay[0]["NGAYGUI_TT"]
            );
          }
          if (d_sngay[0]["NGAYNHAN_TT"]) {
            this.gParams.dtpNgayNhan = this.stringToDate(
              d_sngay[0]["NGAYNHAN_TT"]
            );
          }
        }
        this.gParams.txtSo_HD = value.SO_HOADON;
        this.gParams.txtND = value.ND_GIAO;
        let md = await (
          await API.getDSMucDichTheoMDId(this.axios, {
            mucDichId: value.MUCDICH_ID,
          })
        ).data.data;
        if (md.length) {
          if (md[0] == "2.XCP") {
          }
        }
      }
      if (!value) {
        this.gParams.txtlenhVB = "";
        this.gParams.dtpNgayVB = new Date();
        this.gParams.txtND = "";
        this.dtVatTu = [];
        this.gParams.cboKieuPhieu = "";
      }
    },

    ondtVatTuRowChanged(value) {
      this.gParams.dtVatTuRowChanged = value;
      if (value) {
        this.gParams.makho = value.MAKHOTD;
        this.gParams.loaikho = value.LOAIKHO;
      }
    },
    ondtVatTuCellSaved(args) {
      console.log(args);
      if (args) {
        if (args.columnName == "TK_KHO") {
          this.gridChungTu.forEach((e) => {
            if ((e.MA_VT = args.rowData.MA_VT)) {
              e["TK_KHO"] = args.value;
            }
          });
        }
        if (args.columnName == "TK_DOIUNG") {
          this.gridChungTu.forEach((e) => {
            if ((e.MA_VT = args.rowData.MA_VT)) {
              e["TK_DOIUNG"] = args.value;
            }
          });
        }
        if (args.columnName == "GHICHU_HD") {
          this.gridChungTu.forEach((e) => {
            if ((e.MA_VT = args.rowData.MA_VT)) {
              e["GHICHU_HD"] = args.value;
            }
          });
        }
      }
    },
    onVatTuQueryCellInfor(args) {
      // console.log(args);
      switch (args.column.field) {
        case "SOLUONG":
          if (args.data.SOLUONG) {
            args.cell.setAttribute("style", "color: black;font-weight: bold; ");
          }
          break;
        case "DONGIA":
          if (args.data.DONGIA) {
            args.cell.setAttribute("style", "color: blue;font-weight: bold; ");
          }
          break;
        case "TIEN":
          if (args.data.TIEN) {
            args.cell.setAttribute("style", "color: red;font-weight: bold; ");
          }
          break;
      }
    },

    getCboLoaiKho(parent) {
      return function () {
        return {
          template: {
            components: {},
            template: `
                           <ejs-dropdownlist                           
                            :dataSource="parent.cboLoaiKho"                        
                            :fields="{value:'loaiKhoTdId', text: 'tenLk'}"                   
                            @change="valueChange"
                            v-model="value"
                        ></ejs-dropdownlist>
                        `,
            data() {
              return {
                parent: parent, //this
                data: {}, //rowData
                value: "",
              };
            },
            created() {
              this.value = this.data.LOAIKHO;
            },
            methods: {
              async valueChange(arg) {
                console.log(arg.value);
                this.parent.dtVatTu.forEach((item) => {
                  if (this.data.MA_VT == item.MA_VT) {
                    item.LOAIKHO = arg.value;
                  }
                });
                this.parent.$refs.gridVatTu.grid.refreshColumns();
              },
            },
          },
        };
      };
    },
    async getDSCboMakhoTD(value) {
      try {
        this.$root.loading(true);
        let ds = await (
          await API.getDSKhoTapDoan(this.axios, {
            loaiKhoTdId: value,
          })
        ).data.data;
        return ds;
      } catch (error) {
        console.log(error);
      } finally {
        this.$root.loading(false);
      }
    },
    getCboMakhoTD(parent) {
      return function () {
        return {
          template: {
            components: {},
            template: `
                           <ejs-dropdownlist                           
                            :dataSource="dataSource"                        
                            :fields="{value:'khoTDId', text: 'maKhoTd'}"                   
                            @change="valueChange"
                            v-model="value"
                        ></ejs-dropdownlist>
                        `,
            data() {
              return {
                parent: parent, //this
                data: {}, //rowData
                value: "",
                dataSource: [],
              };
            },
            async created() {
              this.value = this.data.MAKHOTD;
              this.dataSource = await this.parent.getDSCboMakhoTD(
                this.data.LOAIKHO
              );
            },
            methods: {
              valueChange(arg) {
                console.log(arg.value);
                this.parent.dtVatTu.forEach((item) => {
                  if (this.data.MA_VT == item.MA_VT) {
                    item.MAKHOTD = arg.value;
                  }
                });
              },
            },
          },
        };
      };
    },
    getCboChatLuong(parent) {
      return function () {
        return {
          template: {
            components: {},
            template: `
                           <ejs-dropdownlist                           
                            :dataSource="parent.cboChatLuong"                        
                            :fields="{value:'chatLuongId', text: 'chatLuong'}"                   
                            :change="valueChange"
                            v-model="value"
                        ></ejs-dropdownlist>
                        `,
            data() {
              return {
                parent: parent, //this
                data: {}, //rowData
                value: "",
              };
            },
            created() {
              this.value = this.data.CHATLUONG_ID;
            },
            methods: {
              valueChange(arg) {
                console.log(arg.value);
                this.parent.dtVatTu.forEach((item) => {
                  if (this.data.MA_VT == item.MA_VT) {
                    item.CHATLUONG_ID = arg.value;
                  }
                });
              },
            },
          },
        };
      };
    },

    async cboKieuPhieuChanged(value) {
      try {
        this.$root.loading(true);
        let ds = await (
          await API.getDSLoaiKhoTapDoan(this.axios, {
            mucDichId: this.gParams.cboKieuPhieu,
          })
        ).data.data;
        this.cboLoaiKho = ds;
        this.gParams.cboLoaiKho = this.setFirstValue(
          this.cboLoaiKho,
          "loaiKhoTdId"
        );
      } catch (error) {
        console.log(error);
      } finally {
        this.$root.loading(false);
      }
    },
    async KiemTra_LoaiKho_MaKho() {
      if (this.gParams.loaict_id == 2) return true;
      let lkmk = this.dtVatTu;
      if (!lkmk.length) {
        return false;
      }
      let json = lkmk;
      let kq = await (
        await API.postKiemTraLoaiKhoMaKho(this.axios, {
          kieu: 0,
          dataDs: json,
        })
      ).data.data;
      if (kq[0] == "loaikho") {
        this.$toast.error(
          "Không thể ghi lại. Bạn phải chọn hết loại kho ở danh sách vật tư trước!"
        );
        return false;
      }
      if (kq[0] == "makhotd") {
        this.$toast.error(
          "Không thể ghi lại. Bạn phải chọn hết mã kho ở danh sách vật tư trước!"
        );
        return false;
      }
      return true;
    },
    async CAPNHAT() {
      try {
        console.log('CAPNHAT - 1')
        this.$root.loading(true);
        if (!this.gridChungTu.length || this.dtVatTu.length) {
          console.log('CAPNHAT - 2')
          if (!this.gParams.cboKieuPhieu) {
            return this.$toast.error("Bạn phải chọn kiểu phiếu!");
          }
          let isCheck = await this.KiemTra_LoaiKho_MaKho();

          if (!isCheck) {
            return;
          }
          console.log('CAPNHAT - 3')
          console.log(this.gParams.chuaht_or_khac_2xcp)
          if (!this.gParams.chuaht_or_khac_2xcp) {
            console.log('CAPNHAT - 4')
            this.gParams.dt_temp = [
              {
                CHUNGTU_ID: this.gParams.vchungtu_id,
                SO_VB: this.gParams.txtlenhVB,
                NGAY_VB: this.dateToString(this.gParams.dtpNgayVB),
                LOAIPHIEU_ID: this.gParams.cboKieuPhieu,
                ND_GIAO: this.gParams.txtND,
                SO_HOADON: this.gParams.txtSo_HD,
                NGAY_HOADON: this.dateToString(this.gParams.dtpNgay_HD),
                NGAYGUI_TT: this.dateToString(this.gParams.dtpNgayXuat),
                NGAYNHAN_TT: this.dateToString(this.gParams.dtpNgayNhan),
              },
            ];
          }
          let json = JSON.stringify(this.gParams.dt_temp);
          let ct = this.dtVatTu;
          let json_chitiet = JSON.stringify(ct);
          let rs = await (
            await API.postCapNhatChungTuCoQuyen(this.axios, {
              ds: json,
              chiTiet: json_chitiet,
              kieu: this.gParams.loaict_id,
              nguoiCn: await this.$root.token.getUserName(),
              mayCN: await this.$root.token.getMachine(),
              ipCn: await this.$root.token.getIP(),
            })
          ).data.data;
          console.log('716 - CAPNHAT')
          console.log(rs)
          if (rs == "1") {
            this.gParams.xacnhan = true;
            this.$toast.success("Ghi lại thành công!");
            this.closeForm();
          } else {
            this.$toast.error("Có lỗi: " + rs);
          }

        } else {
          this.$toast.error("Không có dữ liệu");
        }
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.error_code);
      } finally {
        this.$root.loading(false);
      }
    },
    txtTKDUAllEnter() {
      console.log('txtTKDUAllEnter')
      if (this.isNullOrEmpty(this.gParams.txtTKDUAll)) {
        return this.$toast.error("Chưa nhập tài khoản đối ứng !");
      }
      this.dtVatTu = this.dtVatTu.map((e) => {
        e.TK_DOIUNG = parseInt(this.gParams.txtTKDUAll);
        return e
      });
    },
    txtTK_Kho_AllEnter() {
      if (this.isNullOrEmpty(this.gParams.txtTK_Kho_All)) {
        return this.$toast.error("Chưa nhập tài khoản đối ứng !");
      }
      this.dtVatTu.forEach((e) => {
        e.TK_KHO = parseInt(this.gParams.txtTK_Kho_All);
      });
    },
  },
};
</script>

<style src="./SuaChungTu.scss"></style>