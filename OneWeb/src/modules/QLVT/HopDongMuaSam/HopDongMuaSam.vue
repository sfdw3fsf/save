<template src='./HopDongMuaSam.html'></template>
<style scoped src='./HopDongMuaSam.scss'></style>
<script>
import ClickOutside from "vue-click-outside";
import moment from "moment";
import DSVatTu from "./components/ds-vat-tu";
import NVThau from "./components/nv-thau";
import GhepHopDong from "./components/ghep-hop-dong";
import TraCuuHopDongVaDonHang from "../TraCuuHopDongVaDonHang";
import VTCustomDropdown from "./components/custom-dropdown";
import { currency } from "@/filters/currency";
import PhieuIn from "./components/in-phieu";
import VueHtml2pdf from "vue-html2pdf";

import Vue from "vue";
import { DatePickerPlugin } from "@syncfusion/ej2-vue-calendars";
import EventBus from "@/utils/eventBus";
import xlsx from "xlsx";
import { export_json_to_excel } from "../../QLTN/TraCuuDichVu/Export2Excel";
import { docSo } from "./utils.js";
Vue.use(DatePickerPlugin);
const initValue = {
  maHd: "",
  ngayKy: null,
  ngayYc: new Date(),
  ngayKh: new Date(),
  ngayHl: new Date(),
  ngayDh: new Date(),
  ngayThanhLy: new Date(),
  ndThanhLy: "",
  ngayCl: 0,
  nhaThau: {
    text: "",
    id: "",
    show: false,
  },
  htHD: {
    text: "",
    id: "",
    show: false,
  },
  dsPhuLuc: {
    text: "",
    id: "",
    vVisible: "",
    show: false,
  },
  loaiHD: {
    text: "",
    id: "",
    show: false,
  },
  trangThaiHd: "1",
  kieuHD: "",
  soNgayTh: {
    dispValue: 0,
    inpValue: 0,
    show: false,
    text: "Số ngày",
    theoNgay: true,
  },
  noiDung: "",
  ghiChu: "",
  tienHd: 0,
  vat: 0,
  thanhTien: 0,
  tienBangChu: "",
  giaTri: 0,
  fileNames: [],
  check_RowCell_PhuLucID: {},
};
export default {
  components: {
    VueHtml2pdf,
    "ds-vattu": DSVatTu,
    "nv-thau": NVThau,
    "ghep-hop-dong": GhepHopDong,
    "vt-custom-dropdown": VTCustomDropdown,
    "phieu-in": PhieuIn,
    "tra-cuu-hd": TraCuuHopDongVaDonHang,
  },
  name: "HopDongMuaSam",
  async created() {
    await this.getDsQuyen();
    if (this.$root.token.getPhanVungID() != "28") {
      this.tthd.dsPhuLuc.vVisible = false;
    } else {
      this.tthd.dsPhuLuc.vVisible = true;
    }
    this.dsNhaThau();
    this.dsHTHopDong();
    this.dsTTHopDong();
    this.dsLoaiHopDong();
    this.getDsHD();
    this.getDsDvTinh();
    this.getDsVaiTro();
    this.getDSPhuLuc(0);
    this.APITyLeVAT();
    await this.getDmVatTu();
    EventBus.$on("onDeleteRow", this.onDeleteRowHandler);
    EventBus.$on("onDeleteRowNVT", this.onDeleteRowHandlerNVT);
  },
  data() {
    return {
      tthd: {
        maHd: "",
        ngayKy: null,
        ngayYc: new Date(),
        ngayKh: new Date(),
        ngayHl: new Date(),
        ngayDh: new Date(),
        ngayCl: 0,
        nhaThau: {
          text: "",
          id: "",
          show: false,
        },
        htHD: {
          text: "",
          id: "",
          show: false,
        },
        dsPhuLuc: {
          text: "",
          id: "",
          show: false,
        },
        listPhuLuc: [],
        loaiHD: {
          text: "",
          id: "",
          show: false,
        },
        trangThaiHd: "1",
        kieuHD: "",
        soNgayTh: {
          dispValue: 0,
          inpValue: 0,
          show: false,
          text: "Số ngày",
          theoNgay: true,
        },
        noiDung: "",
        ghiChu: "",
        tienHd: 0,
        vat: 0,
        thanhTien: 0,
        tienBangChu: "",
        giaTri: 0,
        fileNames: [],
        hdms_id: "",
      },
      nhaThauLst: [],
      htHopDongLst: [],
      trangThaiHDLst: [],
      loaiHopDongLst: [],
      kieuHopDongLst: [
        {
          KIEUHD_ID: 1,
          KIEU_HD: "Theo số lượng",
        },
        {
          KIEUHD_ID: 2,
          KIEU_HD: "Theo giá trị",
        },
      ],
      hopDongLst: {
        data: [],
        totalItems: 0,
      },
      vatTuHDLSt: {
        data: [],
        totalItems: 0,
      },
      dkncCha: {
        data: [],
        totalItems: 0,
      },
      dkncCon: {
        data: [],
        totalItems: 0,
      },
      quyenNdLst: [],
      hopDongChon: {},
      editSettings: { allowEditing: true, mode: "Batch" },
      childGrid: {
        dataSource: [], //this.dkncCon.data,
        queryString: "TONGHOP_ID",
        allowResizing: true,
        columns: [
          {
            field: "VATTU_ID",
            headerText: "Mã vật tư",
            textAlign: "Right",
            headerTextAlign: "Center",
          },
          {
            field: "TEN_VT",
            headerText: "Tên vật tư",
            textAlign: "Left",
            headerTextAlign: "Center",
          },
          {
            field: "DVITINH_ID",
            headerText: "ĐVT",
            textAlign: "Left",
            headerTextAlign: "Center",
          },
          {
            field: "SOLUONG",
            headerText: "SL dự kiến",
            textAlign: "Left",
            headerTextAlign: "Center",
          },
          {
            field: "GHICHU",
            headerText: "Ghi chú",
            textAlign: "Left",
            headerTextAlign: "Center",
          },
        ],
        value_CapNhatPhuLucHD: "",
        DSNhanVienThamGia: [],
        params: {
          vKIEUHD_ID: "",
          vTTHD_ID: "",
          check_PhuLucId: "",
          checkFile: {},
          getLink: {},
        },
      },
      isAddNew: false,
      isUpdate: false,
      isHuy: false,
      nvThauShowing: false,
      isDone: true,
      showPrintMenu: false,
      fileImport: null,
      deleteTemplate: function () {
        return {
          template: Vue.component("columnTemplate", {
            template: `<div>
                          <button class="btn btn-danger lh14 pad3" @click="onDeleteRow">
                            <span class="-ap icon-close"></span>
                          </button>
                      </div>`,
            data() {
              return { data: {} };
            },
            methods: {
              onDeleteRow() {
                EventBus.$emit("onDeleteRow", this.data);
              },
            },
          }),
        };
      },
      deleteTemplateNVT: function () {
        return {
          template: Vue.component("columnTemplate", {
            template: `<div>
                          <button class="btn btn-danger lh14 pad3" @click="onDeleteRowNVT">
                            <span class="-ap icon-close"></span>
                          </button>
                      </div>`,
            data() {
              return { data: {} };
            },
            methods: {
              onDeleteRowNVT() {
                EventBus.$emit("onDeleteRowNVT", this.data);
              },
            },
          }),
        };
      },
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
      maVatTuTemplate: function () {
        return {
          template: {
            template: `<div class="select-custom">
                            <select class="form-control" :value="value">
                                <option v-for="dv in parent.dmVatTuLst" v-bind:value="dv.vatTuID">{{dv.maVatTu}}
																</option>
                            </select>
                        </div>`,
            data() {
              return {
                data: {},
                vtId: "",
              };
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
              value() {
                return `${this.data.VATTU_ID}`;
              },
            },
            created: async function () {},
            methods: {},
          },
        };
      },
      dvTinhLst: [],
      vaiTroLst: [],
      nVTGLst: {
        data: [],
        totalItems: 0,
      },
      dmVatTuLst: [],
      printDataLst: [],
      listTyLeVAT: [],
      tyle_VAT: "",
      tyle_VAT_HD: "",
    };
  },
  computed: {
    canViewHD: function () {
      return this.quyenNdLst.includes(917) || this.quyenNdLst.includes(2500);
    },
    PTAggregatesColumns() {
      return [
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
  directives: {
    ClickOutside,
  },
  watch: {},
  methods: {
    APITyLeVAT: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-qlvt/api/hop-dong-mua-sam/lay-ds-tyle-vat"
        );
        console.log("APITyLeVAT = ", rs.data);
        return (this.listTyLeVAT = rs.data);
      } catch (e) {
        return e.response.data.message;
      } finally {
      }
    },
    APIHuyThanhLy: async function () {
      try {
        let data = {
          hdms_id: this.tthd.hdms_id,
          nguoi_tl: this.$auth.getUserName(),
        };
        var rs = await this.$root.context.post(
          "/web-qlvt/api/hop-dong-mua-sam/huy_thanhly-hd-ms",
          data
        );
        return rs.data;
      } catch (e) {
        return e.response.data.message;
      } finally {
      }
    },
    APIThanhLy: async function () {
      try {
        let data = {
          hdms_id: this.tthd.hdms_id,
          nguoi_tl: this.$auth.getUserName(),
          ngay_tl: moment(this.tthd.ngayThanhLy).format("DD/MM/YYYY"),
          nd_tl: this.tthd.ndThanhLy,
        };
        var rs = await this.$root.context.post(
          "/web-qlvt/api/hop-dong-mua-sam/thanhly-hd-ms",
          data
        );
        console.log(rs, " = rs");
        return rs.data;
      } catch (e) {
        return e.response.data.message;
      } finally {
      }
    },
    async tsbtnThanhLy_Click() {
      if (this.tthd.trangThaiHd != 1) {
        this.$toast.error("Hợp đồng đã hết hiệu lực ! Không thể thanh lý !");
        return;
      }
      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm(
          "Hợp đồng còn hiệu lực, bạn có thực sự muốn thanh lý hợp đồng không ??? ",
          {
            title: "Thông báo",
            centered: true,
            size: "md",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          }
        )
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
      this.tthd.ngayThanhLy = new Date();
      this.tthd.ndThanhLy = "";
      this.$bvModal.show("popupThanhLy");
    },
    async XacNhanThanhLy() {
      this.$bvModal.hide("popupThanhLy");
      let ketQua = await this.APIThanhLy();
      if (ketQua == 1) {
        this.$toast.success("Thanh lý hợp đồng thành công !");
        await this.getDsHD();
      } else {
        this.$toast.error(ketQua);
      }
    },
    async tsbtnHuyTL_Click() {
      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm("Bạn có muốn hủy thanh lý hợp đồng không ? ", {
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
      let ketQua = await this.APIHuyThanhLy();
      if (ketQua == 1) {
        this.$toast.success("Hủy thanh lý hợp đồng thành công !");
        await this.getDsHD();
      } else {
        this.$toast.error(ketQua);
      }
    },
    async getPdf(url, body) {
      try {
        this.loading(true);
        const baseUrl = process.env.API;
        await this.axios({
          url: `${baseUrl}/web-qlvt/${url}`,
          method: "POST",
          responseType: "blob",
          data: body,
        }).then((response) => {
          this.previewPrint(response.data);
        });
      } catch (error) {
        let res = await JSON.parse(await error.data.text());
        this.$toast.error(res.error_code);
      } finally {
        this.$root.loading(false);
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
    async DOWLOAD_OPEN(isShow) {
      let url = this.vurl_luoi;
      if (!url) return;
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
    async onClickInPhieu() {
      // this.$bvModal.show("modal-luot-da-giao");
      // this.$root.showLoading(true);
      // await this.getDataIn();
      // if (this.printDataLst.length == 0) {
      //   this.$toast.error("Không có dữ liệu để in");
      //   this.$root.showLoading(false);
      //   return false;
      // }
      // this.$refs.html2Pdf2.generatePdf();
      let url = "api/bao-cao/bao-cao-phieu-gia-thanh-toan?extend=pdf";
      let body = {
        hdMsId: this.tthd.hdms_id,
        maBc: "TT",
      };
      this.getPdf(url, body);
    },

    onProgress(evt) {
      this.$root.showLoading(false);
    },
    toggleMenuPrint() {
      this.showPrintMenu = !this.showPrintMenu;
    },
    hideMenuPrint() {
      this.showPrintMenu = false;
    },
    getTotalTIEN() {
      const tong = this.vatTuHDLSt.data.reduce((a, b) => +a + +b.TIEN, 0);
      return currency(tong);
    },
    getTotalVAT() {
      const tong = this.vatTuHDLSt.data.reduce((a, b) => +a + +b.VAT, 0);
      return currency(tong);
    },
    onClickSaveGtHd() {
      if (!this.tthd.giaTri || !this.tthd.giaTri < 0) {
        this.$toast.error("Giá trị HĐ phải lớn hơn 0 !");
      } else if (this.hopDongChon) {
        const mucDich = this.loaiHopDongLst.filter(
          (item) => item.MUCDICH_ID == this.hopDongChon.MUCDICH_ID
        );
        if (mucDich && mucDich.MA_MD == "M.DAY" && tthd.kieuHD == 2) {
          this.capNhatGtHd();
        }
      }
    },
    onClickNhapMoi() {
      this.resetForm();
      this.isAddNew = true;
      this.isHuy = false;
      // this.$refs.tableHD.setCurrentSelectedRow(-1);
      this.hopDongChon = null;
      this.vatTuHDLSt.data = [];
      this.vatTuHDLSt.totalItems = 0;
    },
    onClickHuy() {
      this.resetForm();
      this.isAddNew = false;
      this.isHuy = true;
    },
    downFileMau() {
      this.exportTemplate();
    },
    exportTemplate() {
      const tHeader = ["MA_VT", "SOLUONG", "DONGIA"];
      const data = [];
      export_json_to_excel({
        header: tHeader,
        data,
        filename: "template",
        sheetname: "data",
      });
    },
    async onChangeFileExcel(e) {
      // console.log(e.target.files);
      const maHd = this.loaiHopDongLst.filter(
        (item) => item.MUCDICH_ID == this.hopDongChon.MUCDICH_ID
      );
      console.log(maHd);
      if (maHd.length == 0 || maHd[0].MA_MD != "M.DAY") {
        this.$toast.error("Hợp đồng hiện tại không phải hợp đồng dây nhảy");
        return false;
      }
      this.fileImport = e.target.files;
      if (this.fileImport.length > 0) {
        await this.importExcel();
      }
    },
    onClickNhapExcel() {
      this.$refs.fileExcel.value = "";
      this.$refs.fileExcel.click();
    },
    async importExcel() {
      this.validData = [];
      const fileReader = new FileReader();
      fileReader.readAsBinaryString(this.fileImport[0]);
      fileReader.onload = async (ev) => {
        try {
          const data = ev.target.result;
          const XLSX = xlsx;
          const workbook = XLSX.read(data, {
            type: "binary",
          });
          const wsname = workbook.SheetNames[0];
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
          console.log(ws);
          if (!ws || ws.length == 0) {
            const txt = XLSX.utils.sheet_to_csv(workbook.Sheets[wsname]);
            if (txt.length > 0) {
              const errorMsg = [];
              const txtArr = txt.split(",");
              if (txtArr.length < 3) {
                errorMsg.push(
                  "File excel gồm 3 trường: MA_VT, SOLUONG, DONGIA"
                );
              } else {
                txtArr[0] != "MA_VT" &&
                  errorMsg.push(
                    "Tên cột đầu tiên không đúng định dạng : MA_VT"
                  );
                txtArr[1] != "SOLUONG" &&
                  errorMsg.push("Tên cột thứ 2 không đúng định dạng : SOLUONG");
                txtArr[2].slice(0, -1) != "DONGIA" &&
                  errorMsg.push("Tên cột thứ 3 không đúng định dạng : DONGIA");
              }
              errorMsg.length > 0 && this.$toast.error(errorMsg.join("\n"));
            }
          } else if (this.checkDataFormat(ws)) {
            this.xuLyDuLieu(ws);
          }
        } catch (e) {
          return alert("Lỗi định dạng file excel, bạn xem lại! " + e);
        }
      };
    },
    checkDataFormat(data) {
      const errorMsg = [];
      if (data && data.length > 0) {
        let keys = Object.keys(data[0]);
        data.forEach((el) => {
          let key = Object.keys(el);
          keys = [...new Set(keys.concat(key))];
        });
        if (keys.length < 3) {
          errorMsg.push("File excel gồm 3 trường: MA_VT, SOLUONG, DONGIA");
        } else {
          keys[0] != "MA_VT" &&
            errorMsg.push("Tên cột đầu tiên không đúng định dạng : MA_VT");
          keys[1] != "SOLUONG" &&
            errorMsg.push("Tên cột thứ 2 không đúng định dạng : SOLUONG");
          keys[2] != "DONGIA" &&
            errorMsg.push("Tên cột thứ 3 không đúng định dạng : DONGIA");
        }
      }
      if (errorMsg.length > 0) {
        this.$toast.error(errorMsg.join("\n"));
        return false;
      } else {
        return true;
      }
    },
    async xuLyDuLieu(data) {
      for (let index = 0; index < data.length; index++) {
        const item = data[index];
        // console.log(item);
        if (item.SOLUONG >= 0) {
          const vatTu = await this.getVTTheoMa(item.MA_VT);
          // console.log(vatTu);
          if (vatTu.length <= 0) {
            this.$toast.error(
              `Mã vật tư ${item.MA_VT} có lỗi. Hãy kiểm tra lại!`
            );
            break;
          } else {
            const isExistVT = await this.checkExistVatTu(vatTu[0].vatTuID);
            // console.log('isExistVT', isExistVT);
            if (isExistVT.length > 0) {
              const sl = Number(item.SOLUONG) + Number(isExistVT[0].soLuong);
              const body = {
                hdVtId: isExistVT[0].hdVtId,
                hdMsId: this.hopDongChon.HDMS_ID,
                vatTuId: vatTu[0].vatTuID,
                soLuong: sl,
                slKd: Number(item.SOLUONG) + Number(isExistVT[0].soLuongKd),
                donGia: item.DONGIA,
                tien: sl * Number(item.DONGIA),
                vat: 0.1 * sl * Number(item.DONGIA),
                ghiChu: "",
                nguoiDungId: this.$auth.getNguoiDungID(),
                ngayCn: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
                nguoiCn: this.$auth.getUserName(), //"testAdmin"
                mayCn: "", //"pcAdmin",
                ipCn: "", //"192.168.24.5",
              };
              await this.capNhatVatTu(body);
            } else {
              const body = {
                hdMsId: this.hopDongChon.HDMS_ID,
                vatTuId: vatTu[0].vatTuID,
                soLuong: item.SOLUONG,
                slKd: item.SOLUONG,
                donGia: item.DONGIA,
                tien: Number(item.SOLUONG) * Number(item.DONGIA),
                vat: 0.1 * Number(item.SOLUONG) * Number(item.DONGIA),
                ghiChu: "",
                nguoiDungId: this.$auth.getNguoiDungID(),
                ngayCn: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
                nguoiCn: this.$auth.getUserName(), //"testAdmin"
                mayCn: "", //"pcAdmin",
                ipCn: "", //"192.168.24.5",
              };
              await this.taoMoiVatTu(body);
            }
          }
        }
      }
      this.getDsVTHD();
    },
    onDetailDataBound(args) {
      args.detailElement.querySelector(".e-grid").ej2_instances[0].dataSource =
        this.dkncCon.data; // assign data source for child grid.
    },
    onDeleteRowHandler(item) {
      console.log(item);
      if (item) {
        this.$bvModal
          .msgBoxConfirm(`Bạn có muốn xóa vật tư này không ?`, {
            title: "Thông báo",
            size: "sm",
            id: "modal-notice",
            buttonSize: "md",
            okVariant: "primary",
            headerClass: "p-2 pb-0 border-bottom-0",
            footerClass: "p-2 pt-0 border-top-0 justify-content-center",
            centered: true,
            noCloseOnBackdrop: true,
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          })
          .then((value) => {
            value && this.xoaVT(item.HDVT_ID);
          });
      }
    },
    onDeleteRowHandlerNVT(item) {
      console.log(item);
      if (item) {
        this.$bvModal
          .msgBoxConfirm(`Bạn có muốn xóa nhân viên tổ thầu này không ?`, {
            title: "Thông báo",
            size: "sm",
            id: "modal-notice",
            buttonSize: "md",
            okVariant: "primary",
            headerClass: "p-2 pb-0 border-bottom-0",
            footerClass: "p-2 pt-0 border-top-0 justify-content-center",
            centered: true,
            noCloseOnBackdrop: true,
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          })
          .then((value) => {
            value && this.xoaNVTT(item.nhanVienId);
          });
      }
    },
    resetForm() {
      //  console.log(initValue)
      this.tthd = Object.assign({}, initValue, {
        nhaThau: Object.assign({}, initValue.nhaThau),
        htHD: Object.assign({}, initValue.htHD),
        loaiHD: Object.assign({}, initValue.loaiHD),
        soNgayTh: Object.assign({}, initValue.soNgayTh),
      });
      //  this.$refs.nhaThauRef.rowClick(-1, null);
      // this.$refs.loaiHDRef.rowClick(-1, null);
      this.selectRowComboboxGrid(
        "nhaThauDtCbbRef",
        this.nhaThauLst[this.nhaThauLst.length - 1]
      );
    },
    selectRowComboboxGrid(refCbbGrid, value) {
      setTimeout(() => {
        this.$refs[refCbbGrid].$children[0].selectRowByRowData(value);
      }, 300);
    },
    async setFormValue(value) {
      if (value) {
        console.log("setFormValue = ", value);
        this.check_RowCell_PhuLucID = value;
        this.tthd.maHd = value.MA_HD;
        this.tthd.ngayKy = value.NGAY_KY
          ? moment(value.NGAY_KY, "DD/MM/YYYY HH:mm:ss").toDate()
          : null;
        this.tthd.ngayYc = value.NGAY_YC
          ? moment(value.NGAY_YC, "DD/MM/YYYY HH:mm:ss").toDate()
          : null;
        this.tthd.ngayKh = value.NGAY_KH
          ? moment(value.NGAY_KH, "DD/MM/YYYY HH:mm:ss").toDate()
          : null;
        this.tthd.ngayHl = value.NGAY_HL
          ? moment(value.NGAY_HL, "DD/MM/YYYY HH:mm:ss").toDate()
          : null;
        this.tthd.ngayDh = value.NGAY_HH
          ? moment(value.NGAY_HH, "DD/MM/YYYY HH:mm:ss").toDate()
          : null;
        this.tthd.soNgayTh.dispValue = value.SONGAY_TH;
        this.tthd.soNgayTh.inpValue = value.SONGAY_TH;
        this.tthd.noiDung = value.NOIDUNG;
        this.tthd.ghiChu = value.GHICHU;
        this.tthd.kieuHD = value.KIEUHD_ID;
        this.tthd.hdms_id = parseInt(value.HDMS_ID);
        this.tthd.trangThaiHd = value.TTHD_ID;
        if (value.HINHTHUC_ID) {
          const indexHinhThucHd = this.htHopDongLst.findIndex(
            (x) => x.mucDichId == value.HINHTHUC_ID
          );
          this.$refs.htHdRef.rowClick(
            indexHinhThucHd,
            this.htHopDongLst[indexHinhThucHd]
          );
        } else {
          this.tthd.htHD.text = "";
          this.$refs.htHdRef.rowClick(-1, "");
        }
        if ("PHULUC_ID" in value) {
          console.log("Vô");
          this.check_PhuLucId = value.PHULUC_ID;
          if (value.PHULUC_ID != null || value.PHULUC_ID != "") {
            this.tthd.dsPhuLuc.id = value.PHULUC_ID;
          }
        }

        const idNhaThau = this.nhaThauLst.findIndex(
          (x) => x.KHO_ID == value.KHO_ID
        );
        //  this.$refs.nhaThauRef.rowClick(idNhaThau, this.nhaThauLst[idNhaThau]);
        this.selectRowComboboxGrid(
          "nhaThauDtCbbRef",
          this.nhaThauLst[idNhaThau]
        );
        const idlaiHD = this.loaiHopDongLst.findIndex(
          (x) => x.MUCDICH_ID == value.MUCDICH_ID
        );
        this.$refs.loaiHDRef.rowClick(idlaiHD, this.nhaThauLst[idlaiHD]);

        this.tthd.giaTri = await this.getGiaTriHd();
        this.tthd.fileNames.length = 0;
        this.tthd.fileNames = await this.getFileHd();
        this.tthd.fileNames = this.tthd.fileNames.filter(
          (e) => e != "" && e != null
        ); // loại bỏ giá trị null
      } else {
        this.resetForm();
      }
    },
    // Nha thau combobox
    rowSelectNhaThau(value) {
      //   console.log("rowSelectNhaThau", value);
      if (value) {
        this.tthd.nhaThau.id = value.KHO_ID;
        this.tthd.nhaThau.text = value.TEN_KHO;
        this.tthd.nhaThau.show = false;
      }
    },
    toggleNhaThau() {
      this.tthd.nhaThau.show = !this.tthd.nhaThau.show;
    },
    hideNhaThau() {
      this.tthd.nhaThau.show = false;
    },
    // HT Hop dong combobox
    rowSelectHTHopDong(value) {
      if (value) {
        this.tthd.htHD.id = value.mucDichId;
        this.tthd.htHD.text = value.maMd + " - " + value.mucDich;
        this.tthd.htHD.show = false;
      }
    },
    toggleHTHopDong() {
      this.tthd.htHD.show = !this.tthd.htHD.show;
    },
    hideHTHopDong() {
      this.tthd.htHD.show = false;
    },
    hidePhuLuc() {
      this.tthd.dsPhuLuc.show = false;
    },
    toggleDsPhuLuc() {
      this.tthd.dsPhuLuc.show = !this.tthd.dsPhuLuc.show;
    },
    rowSelectDsPhuLuc(value) {
      if (value) {
        this.tthd.dsPhuLuc.id = value.PHULUC_ID;
        this.tthd.dsPhuLuc.text = value.PHULUC;
        this.tthd.dsPhuLuc.show = false;
      }
    },
    // Loai Hop dong combobox
    rowSelectLoaiHopDong(value) {
      if (value) {
        this.tthd.loaiHD.id = value.MUCDICH_ID;
        this.tthd.loaiHD.text = value.MUCDICH;
        this.tthd.loaiHD.show = false;
      }
    },
    toggleLoaiHopDong() {
      this.tthd.loaiHD.show = !this.tthd.loaiHD.show;
    },
    hideLoaiHopDong() {
      this.tthd.loaiHD.show = false;
    },
    toggleSoNgay() {
      this.tthd.soNgayTh.show = !this.tthd.soNgayTh.show;
    },
    hideSoNgay() {
      this.tthd.soNgayTh.show = false;
    },
    async onClickDownFile(value) {
      try {
        this.loading(true);
        if (value) {
          await this.checkFileExist(value); // check có tồn tại ở minio không, nếu không thì call về hệ thống cũ

          if (this.checkFile.message == "Success" && this.checkFile.data.data) {
            const data = await this.downLoadFile(value);
            if (data) {
              const filePath = value.split("/");
              const linkSource = `data:application/octet-stream;base64,${data.data.body.data}`;
              const downloadLink = document.createElement("a");
              const fileName = filePath[filePath.length - 1];
              downloadLink.href = linkSource;
              downloadLink.download = fileName;
              downloadLink.click();
            }
          } else {
            const downloadLink = document.createElement("a");
            downloadLink.href = value;
            const filePath = value.split("/");
            const fileName = filePath[filePath.length - 1];
            downloadLink.download = fileName;
            downloadLink.target = "_blank";
            downloadLink.click();
          }
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async onClickDeleteFile(value) {
      if (value) {
        try {
          const isConfirm = await this.$bvModal.msgBoxConfirm(
            `Bạn có muốn xóa file không ?`,
            {
              title: "Thông báo",
              size: "xs",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            }
          );
          if (!isConfirm) return;
          this.$root.loading(true);
          const rs = await this.deleteFile(value);
          let body = this.makeBodyUpdateFile();
          body.fileVb = this.tthd.fileNames
            .filter((item) => item != value)
            .join();
          this.capNhatHd(body);
        } catch (error) {
          console.log(error.data.message);
        } finally {
          this.$root.loading(false);
        }
      }
    },
    async rowSelectHopDong(value) {
      console.log("Danh sách hợp đồng = ", value);
      if (value) {
        this.tyle_VAT_HD = value.TYLE_VAT;
        this.tyle_VAT = value.TYLE_VAT;
        this.tthd.ngayCl = value.CONLAI;
        this.isAddNew = false;
        this.isUpdate = true;
        this.isHuy = false;
        this.hopDongChon = value;
        this.setFormValue(value);
        this.getDsVTHD();
        this.getDsDKNCCha();
        this.getDsDKNCCon();
        this.getDsNVTG();
        try {
          var rs = await this.$root.context.get(
            `/web-qlvt/api/hop-dong-mua-sam/sl-hd-mua-sam/${value.HDMS_ID}`
          );
          console.log("Số hợp đồng là : ", rs.data);
          this.isDone = !this.isAddNew && !(rs.data == 0);
        } catch (error) {
          return null;
        } finally {
        }
      } else {
        this.tthd.hdms_id = 0;
      }
    },
    async onClickXoa() {
      if (this.nVTGLst.data.length > 0) {
        this.$toast.error("Bạn phải xóa nhân viên tổ thầu trước");
        return false;
      }
      if (this.dkncCon.data.length > 0) {
        this.$toast.error("Bạn phải xóa danh sách vật tư tổng hợp trước");
        return false;
      }
      if (this.vatTuHDLSt.data.length > 0) {
        this.$toast.error("Bạn phải xóa danh sách vật tư của hợp đồng trước");
        return false;
      }
      if (this.hopDongChon.SLDH > 0) {
        return this.$toast.error("Hợp đồng có đơn hàng không thể xóa!");
      }
      let isConfirm = await this.$bvModal.msgBoxConfirm(
        `Bạn có muốn xóa dữ liệu không ?`,
        {
          title: "Thông báo",
          size: "xs",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        }
      );
      if (isConfirm) {
        // BS Process xoa file
        if (this.tthd.fileNames.length) {
          this.$root.loading(true);
          this.tthd.fileNames.forEach(async (value) => {
            try {
              const rs = await this.deleteFile(value);
              if (rs.status) {
                let body = this.makeBodyUpdateFile();
                body.fileVb = this.tthd.fileNames
                  .filter((item) => item != value)
                  .join();
                this.capNhatHd(body);
              }
            } catch (error) {
              console.log(error);
            } finally {
            }
          });
          this.$root.loading(false);
        }
        this.xoaHopDong(this.hopDongChon.HDMS_ID);
      }
    },
    async onClickHoanThien() {
      if (this.hopDongChon) {
        // const body = {
        //   inIdNeo: "count(*)",
        //   inTable: "qlvt.hd_muasam",
        //   inDk: `where hdms_cha_id =${this.hopDongChon.HDMS_ID}`,
        // };
        // const data = await this.checkEnableDone(body);
        // if (data == null || data == 0) {
        //   this.$toast.error(
        //     "Hợp đồng chưa có hợp đồng ảo không thể hoàn thiện"
        //   );
        // } else {
        this.$bvModal
          .msgBoxConfirm(
            `Bạn có thực sự muốn hoàn thiện hợp đồng đang chọn không?`,
            {
              title: "Thông báo",
              size: "sm",
              id: "modal-notice",
              buttonSize: "md",
              okVariant: "primary",
              headerClass: "p-2 pb-0 border-bottom-0",
              footerClass: "p-2 pt-0 border-top-0 justify-content-center",
              centered: true,
              noCloseOnBackdrop: true,
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            }
          )
          .then((value) => {
            if (value) {
              const body = {
                hdMsId: this.hopDongChon.HDMS_ID,
                nguoiCn: this.$auth.getUserName(),
                mayCn: "",
                ipCn: "",
              };
              this.hoanThien(body);
            }
          });
        // }
      } else {
        this.$toast.error("Hãy chọn hợp đồng để hoàn thiện");
      }
    },
    themVTThanhCong() {
      this.$toast.success("Cập nhật dữ liệu thành công");
      this.$bvModal.hide("modal-dsvt");
      this.getDsVTHD();
      this.getDsDKNCCha();
      this.getDsDKNCCon();
    },
    editGridVTHD(arg) {
      // console.log(arg);
      const item = arg.rowData;
      if (arg.columnName == "DONGIA") {
        const donGia = arg.value;
        const thanhTien = donGia * item.SOLUONG;
        const vat = (thanhTien * this.tyle_VAT_HD) / 100;
        this.vatTuHDLSt.data = this.vatTuHDLSt.data.map((item) => {
          const isEqual = item.HDVT_ID == arg.rowData.HDVT_ID;
          return {
            ...item,
            TIEN: isEqual ? thanhTien : item.TIEN,
            VAT: isEqual ? vat : item.VAT,
            DONGIA: isEqual ? donGia : item.DONGIA,
            ISUPDATE: isEqual ? 1 : item.ISUPDATE,
          };
        });
      }

      if (arg.columnName == "GHICHU") {
        this.vatTuHDLSt.data = this.vatTuHDLSt.data.map((item) => {
          const isEqual = item.HDVT_ID == arg.rowData.HDVT_ID;
          return {
            ...item,
            GHICHU: isEqual ? arg.value : item.GHICHU,
            ISUPDATE: isEqual ? 1 : item.ISUPDATE,
          };
        });
      }

      // console.log(this.vatTuHDLSt.data);
    },
    changeKieuTinh(value) {
      // console.log(value)
      this.tthd.soNgayTh.theoNgay = value;
      this.tinhGiaTri();
    },
    tinhGiaTri() {
      this.tthd.ngayDh = new Date();
      if (!!this.tthd.soNgayTh.inpValue && this.tthd.ngayHl) {
        let dateSum;
        if (this.tthd.soNgayTh.theoNgay) {
          dateSum = moment(this.tthd.ngayHl)
            .add(this.tthd.soNgayTh.inpValue, "d")
            .toDate();
        } else {
          dateSum = moment(this.tthd.ngayHl)
            .add(this.tthd.soNgayTh.inpValue, "M")
            .toDate();
        }
        this.tthd.ngayDh = dateSum;
        const numDays = moment(this.tthd.ngayDh).diff(this.tthd.ngayHl, "days");
        this.tthd.soNgayTh.dispValue = numDays;
        this.tthd.soNgayTh.text = numDays + " ngày";
      } else {
        // this.tthd.ngayDh = new Date();
      }
    },
    changeNgayHetHan() {
      if (this.tthd.ngayDh && this.tthd.ngayHl) {
        const numDays = moment(this.tthd.ngayDh).diff(this.tthd.ngayHl, "days");
        this.tthd.soNgayTh.dispValue = numDays;
        this.tthd.soNgayTh.text = numDays + " ngày";
      }
    },
    async onChangeFile(e) {
      const files = e.target.files;
      console.log(files);
      if (files.length > 0) {
        // const txtFileName = files[0].name;
        // console.log(txtFileName);
        const formData = new FormData();
        files.forEach((file) => {
          formData.append("file", file);
        });
        this.upLoadFile(formData);
      }
    },
    makeBodyUpdateFile() {
      return {
        hdMsId: this.hopDongChon.HDMS_ID,
        loai: this.hopDongChon.LOAI,
        ngayYc: this.hopDongChon.NGAY_YC.replaceAll("-", "/"),
        ngayKy: this.hopDongChon.NGAY_KY.replaceAll("-", "/"),
        maHd: this.hopDongChon.MA_HD,
        khoId: this.hopDongChon.KHO_ID,
        mucDichId: this.hopDongChon.MUCDICH_ID,
        ngayKh: this.hopDongChon.NGAY_KH.replaceAll("-", "/"),
        ngayHl: this.hopDongChon.NGAY_HL.replaceAll("-", "/"),
        soNgayTh: this.hopDongChon.SONGAY_TH,
        ngayHh: this.hopDongChon.NGAY_HH.replaceAll("-", "/"),
        ngayNt: this.hopDongChon.NGAY_NT.replaceAll("-", "/"),
        noiDung: this.hopDongChon.NOIDUNG,
        ghiChu: this.hopDongChon.GHICHU,
        fileVb:
          this.tthd.fileNames.length > 0 ? this.tthd.fileNames.join() : "",
        ttHdId: this.hopDongChon.TTHD_ID,
        nhanVienId: this.$auth.getNhanVienID(),
        donViId: this.$auth.getDonViID(),
        nguoiDungId: this.$auth.getNguoiDungID(),
        ngayCn: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
        nguoiCn: this.$auth.getUserName(),
        mayCn: "", //"pcAdmin",
        ipCn: "", //"192.168.24.5",
        hdMsCha: "", //0,
        kieuHdId: this.hopDongChon.KIEUHD_ID || 0,
        hinhThucId: this.hopDongChon.HINHTHUC_ID || 0,
        giaTriHd: 0,
        ngayHuyTl: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"), //"25/08/2021",
        ngayTl: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"), //"25/08/2021",
        nguoiHuyTl: "",
        nguoiTl: "",
        noiDungTl: "",
        phuLucId: "",
        tyle_vat: this.tyle_VAT,
      };
    },
    async onClickGhiLai() {
      if (await this.validateData()) {
        const message = this.validateGhiLai();
        if (message.length > 0) {
          this.$toast.error(message);
          return false;
        }
        if (this.isAddNew) {
          console.log("Them moi");
          // console.log(this.tthd.ngayKh)
          const body = {
            loai: !!this.tthd.ngayKy ? 1 : 0,
            ngayYc: moment(this.tthd.ngayYc).format("DD/MM/YYYY HH:mm:ss"), // "25/08/2021 00:00:00",
            ngayKy: this.datetoString(this.tthd.ngayKy, "DD/MM/YYYY HH:mm:ss"),
            // moment(this.tthd.ngayKy).format("DD/MM/YYYY HH:mm:ss") || "", //"25/08/2021 00:00:00",
            maHd: this.tthd.maHd ? this.tthd.maHd : await this.getMaHD(), //this.tthd.maHd,//"23099/HĐMS-Test123",
            khoId: this.tthd.nhaThau.id, //4845,
            mucDichId: this.tthd.loaiHD.id, //65,
            ngayKh: moment(this.tthd.ngayKh).format("DD/MM/YYYY HH:mm:ss"), //"25/08/2021",
            ngayHl: moment(this.tthd.ngayHl).format("DD/MM/YYYY HH:mm:ss"), //"25/08/2021",
            soNgayTh: this.tthd.soNgayTh.dispValue, //63,
            ngayHh: moment(this.tthd.ngayDh).format("DD/MM/YYYY HH:mm:ss"), //"25/08/2021",
            ngayNt: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"), //"25/08/2021",
            noiDung: this.tthd.noiDung, //"Mua sắm tập trung thiết bị",
            ghiChu: this.tthd.ghiChu, //"test",
            fileVb: "",
            ttHdId: this.tthd.trangThaiHd,
            nhanVienId: this.$auth.getNhanVienID(), //3966,
            donViId: this.$auth.getDonViID(), // 882,
            nguoiDungId: this.$auth.getNguoiDungID(), // 1987,
            ngayCn: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"), //"25/08/2021",
            nguoiCn: this.$auth.getUserName(), //"testAdmin",
            mayCn: "",
            ipCn: "",
            hdMsCha: "", //0,
            kieuHdId: this.tthd.kieuHD || 0, //0,
            hinhThucId: this.tthd.htHD.id || 0, //0,
            giaTriHd: 0,
            ngayHuyTl: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
            ngayTl: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
            nguoiHuyTl: "",
            nguoiTl: "",
            noiDungTl: "",
            phuLucId: "",
            tyle_vat: this.tyle_VAT,
          };
          this.taoHd(body);
        } else {
          const body = {
            hdMsId: this.hopDongChon.HDMS_ID,
            loai: !!this.tthd.ngayKy ? 1 : 0,
            ngayYc: moment(this.tthd.ngayYc).format("DD/MM/YYYY HH:mm:ss"), // "25/08/2021 00:00:00",
            ngayKy: this.datetoString(this.tthd.ngayKy, "DD/MM/YYYY HH:mm:ss"),
            //   moment(this.tthd.ngayKy).format("DD/MM/YYYY HH:mm:ss") || "", //"25/08/2021 00:00:00",
            maHd: this.tthd.maHd ? this.tthd.maHd : await this.getMaHD(), //this.tthd.maHd,//"23099/HĐMS-Test123",
            khoId: this.tthd.nhaThau.id, //4845,
            mucDichId: this.tthd.loaiHD.id, //65,
            ngayKh: moment(this.tthd.ngayKh).format("DD/MM/YYYY HH:mm:ss"), //"25/08/2021",
            ngayHl: moment(this.tthd.ngayHl).format("DD/MM/YYYY HH:mm:ss"), //"25/08/2021",
            soNgayTh: this.tthd.soNgayTh.dispValue, //63,
            ngayHh: moment(this.tthd.ngayDh).format("DD/MM/YYYY HH:mm:ss"), //"25/08/2021",
            ngayNt: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"), //"25/08/2021",
            noiDung: this.tthd.noiDung, //"Mua sắm tập trung thiết bị",
            ghiChu: this.tthd.ghiChu, //"test",
            fileVb:
              this.tthd.fileNames.length > 0 ? this.tthd.fileNames[0] : "",
            ttHdId: this.tthd.trangThaiHd,
            nhanVienId: this.$auth.getNhanVienID(), //3966,
            donViId: this.$auth.getDonViID(), // 882,
            nguoiDungId: this.$auth.getNguoiDungID(), // 1987,
            ngayCn: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"), //"25/08/2021",
            nguoiCn: this.$auth.getUserName(), //"testAdmin",
            mayCn: "",
            ipCn: "",
            hdMsCha: "", //0,
            kieuHdId: this.tthd.kieuHD || 0, //0,
            hinhThucId: this.tthd.htHD.id || 0, //0,
            giaTriHd: 0,
            ngayHuyTl: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"), //"25/08/2021",
            ngayTl: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"), //"25/08/2021",
            nguoiHuyTl: "",
            nguoiTl: "",
            noiDungTl: "",
            phuLucId: "",
            tyle_vat: this.tyle_VAT,
          };
          // console.log(body);
          this.capNhatHd(body);
        }
      }
    },
    async validateData() {
      if (!this.tthd.loaiHD.id) {
        // this.$toast.error("Mục đích chưa chọn không thể cập nhật");
        this.$toast.error("Chưa chọn loại hợp đồng!");
        return false;
      }
      const bodyCheck = {
        hdMsId: 0,
        kieuNhap: 1,
        maNd:
          this.$auth.getUserName() !== -1 ? this.$auth.getUserName() : "admin",
        mucDichHd: this.tthd.loaiHD.id,
      };
      const rs = await this.isValidData(bodyCheck);
      if (rs.length > 0 && rs[0] != "1") {
        this.$toast.error(rs[0]);
        return false;
      }
      // if (!kiemtra_chan_tien())
      // {
      //     return false;
      // }
      return true;
    },
    validateGhiLai() {
      let msg = "";
      // if (!this.tthd.ngayKy) {
      //   msg += `Chưa nhập ngày ký\r\n`;
      // }
      if (!this.tthd.nhaThau.id) {
        msg += `Chưa chọn kho nhà thầu\r\n`;
      }
      if (!this.tthd.noiDung) {
        msg += `Chưa điền nội dung\r\n`;
      }
      if (
        this.tthd.ngayDh <= this.tthd.ngayHl ||
        this.tthd.ngayDh <= this.tthd.ngayKh
      ) {
        msg += "Ngày hết hạn phải lớn hơn ngày kế hoạch và hiệu lực\r\n";
      }

      if (!!this.tthd.ngayKy && this.tthd.ngayHl < this.tthd.ngayKy) {
        msg += "Ngày hiệu lực phải lớn ngày ký\r\n";
      }

      if (!this.tthd.kieuHD) {
        msg += "Chưa chọn kiểu hợp đồng\r\n";
      }

      if (!this.tthd.loaiHD.id) {
        msg += "Chưa chọn loại hợp đồng\r\n";
      }

      if (!this.tyle_VAT) {
        msg += "Chưa chọn Tỷ lệ VAT\r\n";
      }
      return msg;
    },
    async isValidData(body) {
      try {
        var rs = await this.$root.context.post(
          "/web-qlvt/api/hop-dong-mua-sam/kiem-tra-thong-tin-hd",
          body
        );
        return rs.data;
      } catch (error) {}
    },
    onClickDSVT() {
      this.$refs.popupDSVatTu.show();
      // this.$bvModal.show("modal-dsvt");
    },
    onClickNVThau() {
      this.$bvModal.show("modal-nvt");
      this.nvThauShowing = true;
    },
    onCloseModalNVThau() {
      this.nvThauShowing = false;
      this.getDsNVTG();
    },
    onClickNGhepHd() {
      console.log(this.hopDongChon);
      if (this.hopDongChon) {
        const slhd = this.hopDongChon.SLDH;
        if (slhd == 0) {
          this.$bvModal.show("modal-ghd");
        } else {
          this.$toast.error(
            "Hợp đồng đã có đơn hàng không thể ghép thêm hợp đồng ảo"
          );
        }
      } else {
        this.$toast.error("Hãy chọn hợp đồng để ghép");
      }
      // this.nvThauShowing = true;
    },
    onClickTraCuu() {
      // this.$bvModal.show("modal-tracuu");

      console.log(this.tthd.hdms_id);
      setTimeout(() => {
        this.$refs.traCuuHDRef.readonly.ckbDH_TimKiem = true;
        this.$refs.traCuuHDRef.readonly.ckbHDTimKiem = true;
        this.$refs.traCuuHDRef.params.p_ckb_tracuuhopdong = true;
        this.$refs.traCuuHDRef.readonly.ckbDonVi = true;
        this.$refs.traCuuHDRef.params.p_ckb_donvi_dk = true;
        this.$refs.traCuuHDRef.temp_hd_id = this.tthd.hdms_id;
        this.$refs.traCuuHDRef.params.p_ma_hd = this.tthd.maHd;
        this.$refs.traCuuHDRef.accept = 1;
        this.$refs.traCuuHDRef._owner = true;
        this.$refs.traCuuHDRef.visible.tsbtnDongY = true;
      }, 500);
      this.$refs.traCuuHDRef.dialogOpen();
      this.$refs.traCuuRef.show();
    },
    async traCuuHDConfirm(value) {
      try {
        this.loading(true);
        this.$refs.traCuuRef.hide();

        await this.getDsHD();
        let indexData = 0;
        let itemConfirm = this.hopDongLst.data.find((x, index) => {
          if (x.HDMS_ID == value) {
            indexData = index;
            return x;
          }
        });
        if (!itemConfirm) {
          itemConfirm = this.hopDongLst.data.find((x, index) => {
            if (x.HDMS_ID == this.tthd.hdms_id) {
              indexData = index;
              return x;
            }
          });
        }
        if (indexData > 10) {
          this.hopDongLst.data.splice(indexData, 1);
          this.hopDongLst.data.unshift(itemConfirm);
          setTimeout(() => {
            this.$refs["dsHDRef"].grid.selectRow(0);
          }, 1000);
        } else this.$refs["dsHDRef"].grid.selectRow(indexData);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },

    ontest() {
      // console.log(
      //   this.tthd.ngayKy,
      //   moment(new Date()).format("DD/MM/YYYY HH:mm:ss")
      // );
      // console.log(this.datetoString(this.tthd.ngayKy));
      // console.log(this.datetoString(""));
    },
    async onSaveDSVT() {
      const lstUpdate = this.vatTuHDLSt.data
        .filter((item) => item.ISUPDATE == "1")
        .map((item) => ({
          hdVtId: item.HDVT_ID,
          soLuong: item.SOLUONG,
          soLuongKd: item.SL_KD,
          donGia: item.DONGIA,
          tien: item.TIEN,
          vat: item.VAT,
          ghiChu: item.GHICHU,
        }));
      console.log(lstUpdate);
      if (lstUpdate.length > 0) {
        lstUpdate.forEach((element) => {
          this.capNhatVT(element);
        });
      }
    },
    dsNhaThau: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-qlvt/api/tra-cuu-hop-dong-don-hang/kho-nha-cung-cap/1"
        );
        this.nhaThauLst = rs.data;
        this.nhaThauLst.push({
          // tạo ra giá trị reset
          KHO_ID: null,
          MA_KHO: "",
          MA_LOAI: "",
          TEN_KHO: "",
        });
      } catch (error) {
      } finally {
      }
    },
    dsHTHopDong: async function () {
      try {
        var rs = await this.$root.context.post(
          "/web-qlvt/api/cap-vat-tu-thue-bao/ds-muc-dich-ly-do-cap",
          { nhomMdIdArr: [18] }
        );
        this.htHopDongLst = rs.data;
      } catch (error) {
      } finally {
      }
    },
    dsTTHopDong: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-qlvt/api/tra-cuu-hop-dong-don-hang/trang-thai-hop-dong/1"
        );
        this.trangThaiHDLst = rs.data;
      } catch (error) {
      } finally {
      }
    },
    dsLoaiHopDong: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-qlvt/api/tra-cuu-hop-dong-don-hang/muc-dich-mua-sam/1"
        );
        this.loaiHopDongLst = rs.data;
      } catch (error) {
      } finally {
      }
    },
    async taoHd(body) {
      try {
        var rs = await this.$root.context.post(
          "/web-qlvt/api/hop-dong-mua-sam/tao-hd-ms",
          body
        );
        console.log(rs.data);
        if (rs.data == "1") {
          this.$toast.success("Thêm mới hợp đồng thành công!");
          this.getDsHD();
        }
      } catch (error) {}
    },
    async capNhatHd(body) {
      try {
        var rs = await this.$root.context.post(
          "/web-qlvt/api/hop-dong-mua-sam/cap-nhat-tbl-hd-mua-sam",
          body
        );
        console.log("capNhatHd", rs);
        if (rs.data == 1) {
          this.$toast.success("Cập nhật hợp đồng thành công!");
          this.getDsHD();
        }
      } catch (error) {
        console.log(error.response);
      }
    },
    getMaHD: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-qlvt/api/hop-dong-mua-sam/ds-key-hd?keyName=HĐMS"
        );
        return rs.data[0];
      } catch (error) {
        return "";
      } finally {
      }
    },
    getDsHD: async function () {
      let donViId = this.$auth.getDonViID();
      if (this.canViewHD) {
        donViId = -1;
      }
      try {
        var rs = await this.$root.context.get(
          `/web-qlvt/api/hop-dong-mua-sam/ds-hd-theo-don-vi/${donViId}`
        );
        this.hopDongLst.data = rs.data.map((item) => {
          const dvtLst = this.nhaThauLst.filter(
            (el) => el.KHO_ID == item.KHO_ID
          );
          const trangThaiLst = this.trangThaiHDLst.filter(
            (el) => el.TTHD_ID == item.TTHD_ID
          );
          const loaiHD = this.loaiHopDongLst.filter(
            (el) => el.MUCDICH_ID == item.MUCDICH_ID
          );
          const kieuHD = this.kieuHopDongLst.filter(
            (el) => el.KIEUHD_ID == item.KIEUHD_ID
          );

          return {
            ...item,
            NHA_THAU:
              dvtLst && dvtLst.length > 0 ? dvtLst[0].TEN_KHO : item.KHO_ID,
            TRANG_THAI:
              trangThaiLst && trangThaiLst.length > 0
                ? trangThaiLst[0].TRANGTHAI_HD
                : "",
            LOAI_HD: loaiHD && loaiHD.length > 0 ? loaiHD[0].MUCDICH : "",
            KIEU_HD: kieuHD && kieuHD.length > 0 ? kieuHD[0].KIEU_HD : "",
          };
        });
        this.hopDongLst.totalItems = rs.data.length;
        let stt = 0;
        if (this.hopDongChon) {
          for (let index = 0; index < this.hopDongLst.data.length; index++) {
            const element = this.hopDongLst.data[index];
            if (element.HDMS_ID == this.hopDongChon.HDMS_ID) {
              stt = index;
              break;
            }
          }
        }
        //  this.$refs.dsHDRef.setCurrentSelectedRow(stt);
        setTimeout(() => {
          this.$refs.dsHDRef.setCurrentSelectedRow(stt);
        }, 500);
        if (this.hopDongLst.totalItems == 0) {
          this.isAddNew = true;
        }
      } catch (error) {
      } finally {
      }
    },
    async btnXoaHetVT_Click(){
      try {
        this.loading(true)
        if(this.hopDongChon == null || this.hopDongChon == {}){
          this.$toast.error("Chưa chọn hợp đồng!")
          return
        }
        await this.XoaVatTuHopDong()
        await this.getDsVTHD()
      }catch(e){
        this.$toast.error(e.message)
      }finally {
        this.loading(false)
      }
    },
    XoaVatTuHopDong: async function () {
      var rs = await this.$root.context.get(`/web-qlvt/api/hop-dong-mua-sam/xoa-vattu-theo-hopdong/${this.hopDongChon.HDMS_ID}`);
    },
    getDsQuyen: async function () {
      const nguoiDungId = this.$auth.getNguoiDungID();
      try {
        var rs = await this.$root.context.get(
          `/web-qlvt/api/tra-cuu-ton-kho-user/ds-quyen-nd/${nguoiDungId}`
        );
        // console.log(rs);
        this.quyenNdLst = rs.data;
      } catch (error) {
      } finally {
      }
    },
    getDsDvTinh: async function () {
      try {
        var rs = await this.$root.context.get(
          `/web-qlvt/api/tra-cuu-hop-dong-don-hang/don-vi-tinh`
        );
        // console.log("donviTinh", rs);
        this.dvTinhLst = rs.data;
      } catch (error) {
      } finally {
      }
    },
    getDsVTHD: async function () {
      try {
        var rs = await this.$root.context.get(
          `/web-qlvt/api/tra-cuu-hop-dong-don-hang/vat-tu-trong-hop-dong/${this.hopDongChon.HDMS_ID}`
        );
        this.vatTuHDLSt.data = rs.data.map((item) => {
          const dvtLst = this.dvTinhLst.filter(
            (el) => el.dviTinhId == item.DVITINH_ID
          );
          const maVT = this.dmVatTuLst.filter(
            (el) => el.vatTuID == item.VATTU_ID
          );
          return {
            ...item,
            TIEN: Number(item.TIEN),
            DONGIA: Number(item.DONGIA),
            VAT: Number(item.VAT),
            SOLUONG: Number(item.SOLUONG),
            DVITINH_VALUE:
              dvtLst && dvtLst.length > 0 ? dvtLst[0].dviTinh : item.DVITINH_ID,
            MA_VATU: maVT && maVT.length > 0 ? maVT[0].maVatTu : item.VATTU_ID,
          };
        });
        this.vatTuHDLSt.totalItems = rs.length;
        //  console.log(' this.vatTuHDLSt',  this.vatTuHDLSt);
        this.tthd.tienHd = this.getTotalTIEN();
        this.tthd.vat = this.getTotalVAT();
        const tien = Number(this.tthd.tienHd.replaceAll(",", ""));
        const vat = Number(this.tthd.vat.replaceAll(",", ""));
        this.tthd.thanhTien = currency(tien + vat);
        this.tthd.tienBangChu = docSo.doc(tien + vat) + " ĐỒNG";
      } catch (error) {
      } finally {
      }
    },
    datetoString(value, format = "DD/MM/YYYY") {
      try {
        if (value) return moment(value).format(format);
        return "";
      } catch (error) {
        return "";
      }
    },
    stringToDate(value, format = "DD/MM/YYYY") {
      return moment(value, format).toDate();
    },
    getDsDKNCCha: async function () {
      try {
          var rs = await this.$root.context.get(
            `/web-qlvt/api/hop-dong-mua-sam/ds-tong-hop-theo-hd/${this.hopDongChon.HDMS_ID}`
          );
          for (let e of rs.data) {
            e.THANG_VIEW = this.datetoString(
              this.stringToDate(e.THANG),
              "MM/YYYY"
            );
          }
          this.dkncCha.data = rs.data;
          this.dkncCha.totalItems = rs.length;
        } catch (error) {
        } finally {
      }
    },
    getDsDKNCCon: async function () {
      try {
        var rs = await this.$root.context.get(
          `/web-qlvt/api/hop-dong-mua-sam/ds-vat-tu-th-hd/${this.hopDongChon.HDMS_ID}`
        );
        // console.log("this.dkncCon.data", rs);
        this.dkncCon.data = rs.data;
        this.dkncCon.totalItems = rs.length;
      } catch (error) {
      } finally {
      }
    },
    capNhatVT: async function (body) {
      console.log(body);
      try {
        const { data } = await this.axios.put(
          "/web-qlvt/api/hop-dong-mua-sam/cap-nhat-hop-dong-vt",
          body
        );
        console.log(data);
        if (data && data.error == "200") {
          this.getDsVTHD();
        }
      } catch (error) {
        this.$toast.error("Lỗi khi lưu danh sách vật tư");
      }
    },
    async xoaVT(idvt) {
      try {
        var { data } = await this.$root.axios.post(
          `/web-qlvt/api/hop-dong-mua-sam/xoa-hd-vat-tu`,
          null,
          {
            params: {
              hdVtId: idvt,
            },
          }
        );
        // console.log(data);
        if (data && data.error == "200") {
          this.$toast.success("Xóa dữ liệu vật tư thành công !");
          this.getDsVTHD();
          this.getDsDKNCCha();
          this.getDsDKNCCon();
        }
      } catch (error) {
        this.$toast.error("Xóa dữ liệu vật tư thất bạ !");
      } finally {
      }
    },
    getDsNVTG: async function () {
      try {
        var rs = await this.$root.context.get(
          `/web-qlvt/api/hop-dong-mua-sam/ds-nv-tham-gia/${this.hopDongChon.HDMS_ID}`
        );
        this.nVTGLst.data = rs.data.map((item) => {
          const vaiTro = this.vaiTroLst.filter(
            (item2) => item2.vaiTroId == item.vaiTroId
          );
          return {
            ...item,
            vaiTroText: vaiTro.length > 0 ? vaiTro[0].vaiTro : item.vaiTroId,
          };
        });
        this.nVTGLst.length = rs.data.length;
        // console.log(this.nVTGLst);
      } catch (error) {
      } finally {
      }
    },
    getDsVaiTro: async function () {
      try {
        var rs = await this.$root.context.get(
          `/web-qlvt/api/hop-dong-mua-sam/ds-vai-tro/-1`
        );
        this.vaiTroLst = rs.data;
      } catch (error) {
      } finally {
      }
    },
    async xoaNVTT(nhanVienId) {
      try {
        var { data } = await this.$root.axios.delete(
          `/web-qlvt/api/hop-dong-mua-sam/xoa-hd-to-thau/${this.hopDongChon.HDMS_ID}/${nhanVienId}`
        );
        // console.log(data);
        if (data && data.error == "200") {
          this.$toast.success("Xóa nhân viên thầu thành công !");
          this.getDsNVTG();
        }
      } catch (error) {
        this.$toast.error("Xóa nhân viên thầu thất bạ !");
      } finally {
      }
    },
    checkEnableDone: async function (body) {
      // try {
      //   var rs = await this.$root.context.post(
      //     `/web-qlvt/api/giao-phieu-bao-hanh/ds-map-tk-quytrinh`,
      //     body
      //   );
      //   return rs.data[0];
      // } catch (error) {
      //   return null;
      // } finally {
      // }
    },
    hoanThien: async function (body) {
      console.log(body);
      try {
        this.$root.loading(true);
        const { data } = await this.axios.put(
          `/web-qlvt/api/hop-dong-mua-sam/cap-nhat-hop-dong-mua-sam`,
          body
        );
        if (data && data.error == "200") {
          this.$toast.success("Đã hoàn thiện hợp đồng");
          this.getDsHD();
        }
      } catch (error) {
        this.$toast.error("Lỗi khi lưu danh sách vật tư: " + error.error_code);
      } finally {
        this.$root.loading(false);
      }
    },
    async xoaHopDong(mshdId) {
      try {
        this.$root.loading(true);
        var { data } = await this.$root.axios.delete(
          `/web-qlvt/api/hop-dong-mua-sam/xoa-hd-mua-sam/${mshdId}`
        );
        if (data && data.error == "200") {
          this.$toast.success("Xóa dữ liệu hợp đồng thành công !");
          this.getDsHD();
        }
      } catch (error) {
        this.$toast.error("Không thể xóa ràng buộc");
      } finally {
        this.$root.loading(false);
      }
    },
    capNhatGtHd: async function () {
      try {
        const { data } = await this.axios.put(
          "/web-qlvt/api/hop-dong-mua-sam/cap-nhat-gia-tri-hd",
          null,
          {
            params: {
              hdMsId: this.hopDongChon.HDMS_ID,
              giaTriHd: this.tthd.giaTri,
            },
          }
        );
        if (data && data.error == "200") {
          this.$toast.success("Cập nhật giá trị HĐ thành công !");
          this.getDsHD();
        }
      } catch (error) {
        this.$toast.error("Lỗi khi cập nhật");
      }
    },
    getGiaTriHd: async function () {
      try {
        var rs = await this.$root.context.get(
          `/web-qlvt/api/hop-dong-mua-sam/gia-tri-hd/${this.hopDongChon.HDMS_ID}`
        );
        return rs.data || 0;
      } catch (error) {
        return 0;
      } finally {
      }
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
            this.tthd.fileNames.push(element.message.relativeUrl);
          });
          console.log(this.tthd.fileNames);
          const body = this.makeBodyUpdateFile();
          // console.log(body);
          this.capNhatHd(body);
        }
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.$root.showLoading(false);
      }
    },

    getFileHd: async function () {
      try {
        var rs = await this.$root.context.get(
          `/web-qlvt/api/hop-dong-mua-sam/file-cv-hd/${this.hopDongChon.HDMS_ID}`
        );
        // console.log('getFileHd', rs.data)
        return rs.data.split(",") || [];
      } catch (error) {
        return [];
      } finally {
      }
    },

    getVTTheoMa: async function (maVatTu) {
      try {
        var rs = await this.$root.context.get(
          `/web-qlvt/api/nhap-xuat-chuyen-vt/ds-vat-tu-theo-ma`,
          {
            maVt: maVatTu,
          }
        );
        return rs.data || "";
      } catch (error) {
        return "";
      } finally {
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

    async getLink(fileName) {
      try {
        var rs = await this.$root.context.post(
          `/web-qlvt/api/storage/getPresignedUrl`,
          {
            fileSource: fileName,
          }
        );
        return rs.data || "";
      } catch (error) {
        return "";
      }
    },

    async deleteFile(fileName) {
      try {
        const rs = await this.$root.context.post(
          `/web-qlvt/api/storage/deleteFile`,
          {
            fileSource: fileName,
          }
        );
        return rs.data;
      } catch (error) {
        return "";
      }
    },

    checkExistVatTu: async function (vatuId) {
      try {
        var rs = await this.$root.context.get(
          `/web-qlvt/api/hop-dong-mua-sam/ds-vat-tu-hd/${this.hopDongChon.HDMS_ID}/${vatuId}`
        );
        return rs.data || "";
      } catch (error) {
        return "";
      } finally {
      }
    },

    async taoMoiVatTu(body) {
      try {
        var rs = await this.$root.context.post(
          `/web-qlvt/api/hop-dong-mua-sam/tao-hd-vt`,
          body
        );
      } catch (error) {}
    },

    async capNhatVatTu(body) {
      try {
        var rs = await this.$root.context.post(
          `/web-qlvt/api/hop-dong-mua-sam/cap-nhat-tbl-hd-vat-tu`,
          body
        );
      } catch (error) {}
    },

    getDmVatTu: async function () {
      try {
        var rs = await this.$root.context.get(
          `/web-qlvt/api/dang-ky-cap-phat/ds-vat-tu-lua-chon`
        );
        // console.log('getDmVatTu', rs);
        return (this.dmVatTuLst = rs.data.filter(
          (item) => item != null && item.vatTuID != null
        ));
      } catch (error) {}
    },

    getDataIn: async function () {
      try {
        var rs = await this.$root.context.get(
          `/web-qlvt/api/bao-cao/bao-cao-phieu-gia-thanh-toan/${this.hopDongChon.HDMS_ID}`
        );
        console.log(rs);
        rs.data.forEach((element, index) => {
          element.STT = index + 1;
        });
        this.printDataLst = rs.data;
      } catch (error) {}
    },

    rowDataBound(args) {
      if (args.data && args.data.DONGIA == 0) {
        // args.row.classList.add('below-dongia');
        args.row.classList.remove("e-altrow");
        args.row.style.background = "lightsalmon";
      }
    },

    getDSPhuLuc: async function (_kieu) {
      try {
        this.loading(true);
        var rs = await this.$root.context.get(
          `/web-qlvt/api/hop-dong-mua-sam/ds-phu-luc-hd/${_kieu}`
        );
        console.log("getDSPhuLuc = ", rs);
        if (rs.error_code === "BSS-00000000") {
          this.tthd.listPhuLuc = rs.data;
        }
      } catch (error) {
        console.log(error.data.message);
      } finally {
        this.loading(false);
      }
    },

    capNhatPhuLucHD: async function (_hdMsId, _phuLucId, _ttPhuLuc) {
      try {
        this.loading(true);
        var rs = await this.axios.put(
          `/web-qlvt/api/hop-dong-mua-sam/cap-nhat-phu-luc-hd?hdMsId=${_hdMsId}&phuLucId=${_phuLucId}&ttPhuLuc=${_ttPhuLuc}`
        );
        console.log("capNhatPhuLucHD = ", rs);
        if (rs.data.error_code === "BSS-00000000") {
          this.value_CapNhatPhuLucHD = rs.data.data;
        }
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.loading(false);
      }
    },

    getDSNhanVienThamGia: async function (_hdMsId) {
      try {
        this.loading(true);
        var rs = await this.$root.context.get(
          `/web-qlvt/api/hop-dong-mua-sam/kiem-tra-don-hang-hd/${_hdMsId}`
        );
        console.log("getDSNhanVienThamGia = ", rs);
        if (rs.error_code === "BSS-00000000") {
          this.DSNhanVienThamGia = rs.data;
        }
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.loading(false);
      }
    },

    async cboTTPhuLuc_ButtonClick() {
      try {
        let ok = "";
        if (this.tthd.dsPhuLuc.id == null || this.tthd.dsPhuLuc.id == "") {
          this.$toast.error("Không có dữ liệu phụ lục !");
          return;
        }
        if (this.tthd.hdms_id == 0) {
          this.$toast.error("Không có thông tin hợp đồng");
          return;
        }
        if ("PHULUC_ID" in this.check_RowCell_PhuLucID) {
          if (
            this.check_PhuLucId != "0" &&
            this.check_PhuLucId !== undefined &&
            this.check_PhuLucId !== null &&
            this.check_PhuLucId != ""
          ) {
            console.log("this.tthd.dsPhuLuc.id = ", this.tthd.dsPhuLuc.id);
            this.$toast.error(
              "Hợp đồng đã có phụ lục kèm theo ! Không thể thêm phụ lục !"
            );
            return;
          }
        }
        if (this.tthd.dsPhuLuc.id == "0") {
          return;
        }
        if (this.tthd.dsPhuLuc.id == "1") {
          if (this.tthd.kieuHD.toString() != "2") {
            this.$toast.error(
              "Hợp đồng không phải hợp đồng giá trị ! Không thể thêm phụ lục giảm giá!"
            );
            return;
          }
          await this.capNhatPhuLucHD(
            this.tthd.hdms_id,
            this.tthd.dsPhuLuc.id,
            1
          );
          ok = this.value_CapNhatPhuLucHD;
        } else if (this.tthd.dsPhuLuc.id == "2") {
          if (this.tthd.kieuHD != "1") {
            this.$toast.error(
              "Hợp đồng không phải hợp đồng số lượng ! Không thể thêm phụ lục chuyển đổi!"
            );
            return;
          }
          if (this.tthd.trangThaiHd != "1") {
            this.$toast.error(
              "Hợp đồng hết hiệu lực hoặc hoàn thành ! Không thể thêm phụ lục chuyển đổi!"
            );
            return;
          }
          await this.getDSNhanVienThamGia(this.tthd.hdms_id);
          if (this.DSNhanVienThamGia.length == 0) {
            this.$toast.error(
              "Hợp đồng không có đơn hàng ! Không thể thêm phụ lục chuyển đổi!"
            );
            return;
          }
          if (!this.quyenNdLst.includes(2511)) {
            //Chuyển đổi kiểu hợp đồng vật tư
            this.$toast.error("Bạn không có quyền thêm phụ lục chuyển đổi!");
            return;
          }
          await this.capNhatPhuLucHD(
            this.tthd.hdms_id,
            this.tthd.dsPhuLuc.id,
            2
          );
          ok = this.value_CapNhatPhuLucHD;
        }
        console.log("ok == ", ok);
        if (ok.toString() == "1") {
          this.$toast.success("Thêm phụ lục thành công !");
          await this.getDsHD();
        } else {
          this.$toast.error(ok);
          return;
        }
      } catch (er) {
        console.log("er == ", er);
        this.$toast.error(er);
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
        console.log("checkFileExist = ", rs);
        this.checkFile = rs.data;
      } catch (err) {
        this.$toast.error(error.data.message);
      } finally {
        this.loading(false);
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
        console.log("getLinkFile = ", rs);
        this.getLink = rs.data;
      } catch (err) {
        this.$toast.error(error.data.message);
      } finally {
        this.loading(false);
      }
    },

    async onClickViewFile(value) {
      if (value) {
        console.log(value);
        await this.checkFileExist(value);
        console.log("Click checkFile = ", this.checkFile);
        let linkFile = "";
        if (this.checkFile.message == "Success" && this.checkFile.data.data) {
          await this.getLinkFile(value);
          console.log("Click getLink = ", this.getLink);
          linkFile = this.getLink.data.name;
        } else {
          linkFile = value;
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

    TaoDuLieu_HD_TOTHAU: async function (_nhanVienId, _donViId, _vaiTroId) {
      try {
        this.loading(true);
        let data = {
          hdMsId: this.tthd.hdms_id,
          nhanVienId: _nhanVienId,
          donViId: _donViId,
          vaiTroId: _vaiTroId,
          ghiChu: "",
        };
        var rs = await this.axios.post(
          "/web-qlvt/api/hop-dong-mua-sam/cap-nhat-tbl-hd-to-thau",
          data
        );
        console.log("TaoDuLieu_HD_TOTHAU = ", rs);
        // if (rs.data.error_code === "BSS-00000000") {
        //   this.value_CapNhatPhuLucHD = rs.data.data;
        // }
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.loading(false);
      }
    },

    Delete_2Keys: async function (_nhanVienId) {
      try {
        this.loading(true);
        var rs = await this.axios.post(
          `/web-qlvt/api/hop-dong-mua-sam/xoa-hd-to-thau/${this.tthd.hdms_id}/${_nhanVienId}`
        );
        console.log("Delete_2Keys = ", rs);
        // if (rs.data.error_code === "BSS-00000000") {
        //   this.value_CapNhatPhuLucHD = rs.data.data;
        // }
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.loading(false);
      }
    },

    async v_btn_Save_MouseClick() {
      console.log("this.isAddNew = ", this.isAddNew);
      console.log("this.nvThauShowing = ", this.nvThauShowing);
      if (!this.isAddNew && !this.nvThauShowing) {
        console.log("this.nVTGLst.data = ", this.nVTGLst.data);
        if (this.nVTGLst.data.length > 0) {
          for (const i in this.nVTGLst.data) {
            if (this.nVTGLst.data[i].vaiTroId.toString() != "") {
              console.log("Test = 1");
              await this.TaoDuLieu_HD_TOTHAU(
                this.nVTGLst.data[i].nhanVienId,
                this.nVTGLst.data[i].donViId,
                this.nVTGLst.data[i].vaiTroId
              );
            } else {
              await this.Delete_2Keys(this.nVTGLst.data[i].nhanVienId);
            }
          }
          await this.getDsNVTG();
        }
      }
    },
  },
  destroyed() {
    EventBus.$off("onDeleteRow", this.onDeleteRowHandler);
    EventBus.$off("onDeleteRowNVT", this.onDeleteRowHandlerNVT);
  },
};
</script>
