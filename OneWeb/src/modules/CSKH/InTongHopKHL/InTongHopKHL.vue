<template src="./InTongHopKHL.html"></template>
<style src="./InTongHopKHL.scss"></style>
<script>
import select2 from "@/components/Select2.vue";
import { mapState } from "vuex";
import YesNoModal from "../utils/YesNoModal.vue";
import KyCuoc from "../../QLTN/components/KyCuoc.vue";
import moment from "moment";
import { currency } from "@/filters/currency";
import CauHinhSMTP from "./popup/CauHinhSMTP.vue";
import ThongTinLienHe from "../../contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue"
import SendMail from "./popup/SendMail.vue";
//import { Workbook } from "@syncfusion/ej2-excel-export/index";
import { previewPrint } from "../../../utils/util";
import FilterDialog from './popup/FilterDialog.vue'

export default {
  name: "InTongHopKHL",
  components: {
    appSelect2: select2,
    confirmPopup: YesNoModal,
    appKyCuoc: KyCuoc,
    appCauHinhSMTP: CauHinhSMTP,
    ThongTinLienHe,
    appSendMail: SendMail,
    FilterDialog
  },
  data() {
    return {
      kyCuoc: moment(new Date()).add(-1, "months").format("YYYYMM") + "01",
      pDsNhanVien: [],
      pDsKhachHang: [],
      pDsNhanVienSelected: 0,
      pDsKieuInSelected: 1,
      pDsKieuGuiSelected: 0,
      pDataIn: [],
      pDsTimKiemSelected: 1,
      pTxtTimKiem: "",

      API_SUCCESS: "BSS-00000000",
      btnStatus: {
        tsbnGuiEmailEnable: false,
      },

      khachHangId: 0,
      dsEmails: [],
      smtp_dclh: "",
      smtp_email: "",
      smtp_sdt: "",
      smtp_thongtin: "",
      smtp_chuyenKhoan:"",
      checkAllKH: false,
      columnsCheckboxAllKH: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAllKH" class="uncheck"/>
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
      rowSelectedKH:[],
      columnsCheckboxKH: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"
                                    v-model="parent.rowSelectedKH"
                                    :value="data.NHOMKH_ID"/>
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
      checkInAll: false,
      ds_danhSachKH: []
    };
  },
  async created() {},
  async mounted() {
    // this.getValueSMTP()
    try {
      this.$root.showLoading(true);
      await this.getDsNhanVien();
    } catch (e) {
    } finally {
      this.$root.showLoading(false);
    }
  },
  computed: {
    ...mapState("reg", [
      "SMTP_CSKH_SERVER",
      "SMTP_CSKH_USERNAME",
      "SMTP_CSKH_PASSWORD",
      "SMTP_CSKH_PORT",
      "SMTP_ALIAS",
      "SMTP_CSKH_DCLH",
      "SMTP_CSKH_EMAIL",
      "SMTP_CSKH_SDT",
    ]),
    danhSachKieuIn() {
      return [
        { value: 1, text: "Theo mã thanh toán" },
        { value: 2, text: "Theo mã thuê bao" },
        { value: 3, text: "Theo mã thuê bao cước phát sinh >= 0" },
      ];
    },
    danhSachKieuGui() {
      return [
        { value: 0, text: "Tất cả" },
        { value: 1, text: "Mail + Fax" },
        { value: 2, text: "Fax" },
        { value: 3, text: "Mail" },
      ];
    },
    danhSachTimKiem() {
      return [
        { value: 1, text: "ID Nhóm" },
        { value: 2, text: "Mã thanh toán" },
        { value: 3, text: "Tên nhóm" },
        { value: 4, text: "Mã thuê bao" },
        { value: 5, text: "Mã số thuế" },
      ];
    }, 
  },
  watch: {
    checkAllKH: function (value) {
      this.rowSelectedKH = [];
      if (value) {
        for (const e of this.pDsKhachHang) {
          this.rowSelectedKH.push(e.NHOMKH_ID);
        }
      }
    },
    rowSelectedKH: function (value) {
      this.rowSelectedKH = value;
    },
    pDsNhanVienSelected: function(value){
      if(this.pDsNhanVienSelected>0){
          this.getValueSMTP()
          //this.pDsKhachHang = []
          this.checkInAll = false
        }

    },
  },
  methods: {
    async getValueSMTP(){
        const objSMTP = localStorage.getItem('KEY_SMTP')
        const valueSMTP = JSON.parse(objSMTP)
        this.smtp_dclh = (valueSMTP && valueSMTP.SMTP_CSKH_DCLH) ? valueSMTP.SMTP_CSKH_DCLH : this.SMTP_CSKH_DCLH || "";
        this.smtp_email = (valueSMTP && valueSMTP.SMTP_CSKH_EMAIL) ? valueSMTP.SMTP_CSKH_EMAIL : this.SMTP_CSKH_EMAIL || "";
        this.smtp_sdt = (valueSMTP && valueSMTP.SMTP_CSKH_SDT) ? valueSMTP.SMTP_CSKH_SDT : this.SMTP_CSKH_SDT || "";
        this.smtp_thongtin = (valueSMTP && valueSMTP.SMTP_CSKH_THONGTIN) ? valueSMTP.SMTP_CSKH_THONGTIN : "";
        this.smtp_chuyenKhoan = (valueSMTP && valueSMTP.SMTP_CSKH_CHUYENKHOAN) ? valueSMTP.SMTP_CSKH_CHUYENKHOAN : "";

        if (this.pDsNhanVienSelected>0){
          const res = await this.$root.context.get(
            "/web-cskh/api/in-tong-hop-khl/cau-hinh-smtp",
            {
              nhanVienId: this.pDsNhanVienSelected,
            }
          );
          if (res.error_code === this.API_SUCCESS) {
            if(res.data.data.length > 0){
              let obj = res.data.data[0]
              this.smtp_dclh = obj.DIACHI_LH
              this.smtp_email = obj.EMAIL
              this.smtp_sdt = obj.SO_DT
              this.smtp_thongtin = obj.MOTA
              this.smtp_chuyenKhoan = obj.CHUYEN_KHOAN
            }
          }
                  
        }
    },
    handleKyHoaDon(e) {
      this.kyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no; //"20210101"; //
    },
    async onTimKiemClicked() {
      try {
        this.$root.showLoading(true);
        await this.getDsKhachHang();
      } catch (e) {
        console.log(e);
      } finally {
        this.$root.showLoading(false);
      }
    },
    async onTaoFileClicked() {
    //   await this.getValueSMTP()
      try {
        if (
          !this.smtp_dclh ||
          !this.smtp_email ||
          !this.smtp_sdt
        ) {
          this.$root.toastError(
            "Chưa có thông tin liên hệ!\nHãy nhập đầy đủ thông tin trước khi gửi mail!"
          );
          this.$bvModal.show("popup-cauHinhSMTP");
          return;
        }
        if (this.pDsKhachHang.length <= 0) {
          this.$root.toastError("Không có dữ liệu để tạo file !");
          return;
        }
        const dsKH = this.rowSelectedKH
        if (dsKH.length <= 0) {
          this.$root.toastError("Bạn chưa chọn dữ liệu để tạo file !");
          return;
        }

        this.$root.showLoading(true);
        await this.getDuLieuTaoFile(dsKH);
        console.log("	this.pDataIn", this.pDataIn);

        let rowKhachHangs = this.pDsKhachHang.filter((x) =>
          dsKH.includes(x.NHOMKH_ID)
        );
        rowKhachHangs.forEach(async (rowKhachHang) => {
          await this.taoFileXLSX(rowKhachHang);
        });

        // if (this.pDataIn && this.pDataIn.length > 0) {
        // 	this.pDataIn.forEach(async (rowKhachHang) => {
        // 		console.log('rowKhachHang', rowKhachHang)
        // 		await this.taoFileXLSX(rowKhachHang)
        // 	})
        // }
        // else {
        // 	this.$root.toastError("Không có dữ liệu để tạo file")
        // }

        this.btnStatus.tsbnGuiEmailEnable = true;
      } catch (e) {
        console.log(e);
      } finally {
        this.$root.showLoading(false);
      }
    },
    async getDuLieuTaoFile(data) {
      try {
        let dsKhachHangIds = data.join();
        let rs = await this.$root.context.get(
          "/web-cskh/api/in-tong-hop-khl/in",
          {
            kyHoaDon: this.kyCuoc,
            dsKhachHangId: dsKhachHangIds,
            kieu: this.pDsKieuInSelected,
            pageNo: 1,
            pageSize: 100000,
          }
        );
        if (rs.error_code === this.API_SUCCESS) {
          this.pDataIn = rs.data.data || [];
        }
      } catch (e) {
        console.log(e);
      }
    },
/*
    async taoFileXLSX(rowKhachHang) {
    //   console.log("rowKhachHang", rowKhachHang);
      try {
        let columns = [
          { index: 1, width: 30 },
          { index: 2, width: 130 },
          { index: 3, width: 130 },
          { index: 4, width: 130 },
          { index: 5, width: 130 },
          { index: 6, width: 130 },
          { index: 7, width: 280 },
          { index: 8, width: 130 },
          { index: 9, width: 130 },
          { index: 10, width: 130 },
          { index: 11, width: 130 },
          { index: 12, width: 130 },
        ];

        let rows = [];
        let rowIndex = 1;
        rows.push(this.emptyRow(rowIndex++));

        rows.push({
          index: rowIndex++,
          cells: [
            {
              index: 1,
              value: "TỔNG CÔNG TY DỊCH VỤ VIỄN THÔNG",
              colSpan: 6,
              style: { hAlign: "Center", bold: true },
            },
            {
              index: 7,
              value: "CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM",
              colSpan: 4,
              style: { hAlign: "Center", bold: true },
            },
          ],
        });

        rows.push({
          index: rowIndex++,
          cells: [
            {
              index: 1,
              value:
                "TRUNG TÂM KINH DOANH VNPT " + this.$root.token.getMaTinh(),
              colSpan: 6,
              style: { hAlign: "Center", bold: true },
            },
            {
              index: 7,
              value: "ĐỘC LẬP - TỰ DO - HẠNH PHÚC",
              colSpan: 4,
              style: { hAlign: "Center", bold: true },
            },
          ],
        });

        rows.push(this.emptyRow(rowIndex++));

        rows.push({
          index: rowIndex++,
          cells: [
            {
              index: 1,
              value:
                "TỔNG HỢP CƯỚC DỊCH VỤ VIỄN THÔNG THÁNG " + rowKhachHang.KYCUOC,
              colSpan: 10,
              style: { hAlign: "Center", bold: true },
            },
          ],
        });

        rows.push({
          index: rowIndex++,
          cells: [
            {
              index: 1,
              value: `${rowKhachHang.LIENHE} - Mail: ${rowKhachHang.MAIL} - SĐT: ${rowKhachHang.SDT}`,
              colSpan: 10,
              style: { hAlign: "Center", bold: true },
            },
          ],
        });

        rows.push({
          index: rowIndex++,
          cells: [
            {
              index: 1,
              value: `Kính gửi: ${rowKhachHang.TENNHOM}`,
              colSpan: 10,
              style: { hAlign: "Left", bold: true },
            },
          ],
        });

        rows.push({
          index: rowIndex++,
          cells: [
            {
              index: 1,
              value: `Địa chỉ xuất hóa đơn: ${rowKhachHang.DIACHI_CT || ''}`,
              colSpan: 10,
              style: { hAlign: "Left", bold: true },
            },
          ],
        });

        rows.push({
          index: rowIndex++,
          cells: [
            {
              index: 1,
              value: `Mã số thuế: ${rowKhachHang.MST || ''}`,
              colSpan: 10,
              style: { hAlign: "Left", bold: true },
            },
          ],
        });

        rows.push(this.emptyRow(rowIndex++));

        rows.push({
          index: rowIndex++,
          cells: [
            {
              index: 1,
              value: `Thông tin chuyển khoản: `,
              colSpan: 10,
              style: { hAlign: "Left", bold: true },
            },
          ],
        });

        rows.push({
          index: rowIndex++,
          cells: [
            {
              index: 1,
              value: `Tên đơn vị thụ hưởng : TRUNG TÂM KINH DOANH VNPT TPHCM - CHI NHÁNH TỔNG CÔNG TY DỊCH VỤ VIỄN THÔNG`,
              colSpan: 10,
              style: { hAlign: "Left", bold: true },
            },
          ],
        });

        rows.push({
          index: rowIndex++,
          cells: [
            {
              index: 1,
              value: `Hệ thống tài khoản ngân hàng: `,
              colSpan: 10,
              style: { hAlign: "Left", bold: true },
            },
          ],
        });

        rows.push({
          index: rowIndex++,
          cells: [
            {
              index: 1,
              value: `   + TK số 007.100.1020048 tại NH TMCP Ngoại thương VN(VCB)-CN TP.HCM`,
              colSpan: 10,
              style: { hAlign: "Left", bold: true },
            },
          ],
        });

        rows.push({
          index: rowIndex++,
          cells: [
            {
              index: 1,
              value: `   + TK số 3751.0.9001114 tại Kho bạc TP.HCM`,
              colSpan: 10,
              style: { hAlign: "Left", bold: true },
            },
          ],
        });

        rows.push({
          index: rowIndex++,
          cells: [
            {
              index: 1,
              value: `Nội dung chuyển khoản: ${rowKhachHang.KYCUOC}, ${rowKhachHang.TENNHOM}`,
              colSpan: 10,
              style: { hAlign: "Left", bold: true },
            },
          ],
        });

        rows.push(this.emptyRow(rowIndex++));

        let tableColumns = [
          "Stt",
          "Mã KH",
          "Mã TB",
          "Ký hiệu HĐĐT",
          "Seri",
          "Ngày phát hành HĐ",
          "Địa chỉ LD",
          "Nợ đầu kỳ",
          "Thuế đầu kỳ",
          "Nợ phát sinh",
          "Thuế phát sinh",
          "Tổng cộng",
        ];

        let tableHeader = {
          index: rowIndex++,
          cells: [],
        };
        tableColumns.forEach((col, index) => {
          tableHeader.cells.push({
            index: index + 1,
            value: col,
            style: {
              borders: {
                color: "#00000000",
                lineStyle: "thin",
              },
              bold: true,
            },
          });
        });

        rows.push(tableHeader);
        let dsDataRows = this.pDataIn.filter(
          (x) => x.NHOMKH_ID == rowKhachHang.NHOMKH_ID
        );
        for (let i = 0; i < dsDataRows.length; i++) {
          let row = {
            index: rowIndex++,
            cells: [],
          };
          row.cells.push({
            index: 1,
            value: i + 1,
            style: { borders: { color: "#00000000", lineStyle: "thin" } },
          });
          row.cells.push({
            index: 2,
            value: dsDataRows[i].MA_TT,
            style: { borders: { color: "#00000000", lineStyle: "thin" } },
          });
          row.cells.push({
            index: 3,
            value: dsDataRows[i].MA_TB,
            style: { borders: { color: "#00000000", lineStyle: "thin" } },
          });
          row.cells.push({
            index: 4,
            value: dsDataRows[i].SOSERI,
            style: { borders: { color: "#00000000", lineStyle: "thin" } },
          });
          row.cells.push({
            index: 5,
            value: dsDataRows[i].SERI,
            style: { borders: { color: "#00000000", lineStyle: "thin" } },
          });
          row.cells.push({
            index: 6,
            value: dsDataRows[i].NGAY_PH,
            style: { borders: { color: "#00000000", lineStyle: "thin" } },
          });
          row.cells.push({
            index: 7,
            value: dsDataRows[i].DIACHI_LD,
            style: { borders: { color: "#00000000", lineStyle: "thin" } },
          });
          row.cells.push({
            index: 8,
            value: currency(dsDataRows[i].NOGOC_DK),
            style: { borders: { color: "#00000000", lineStyle: "thin" },hAlign: "Right" },
          });
          row.cells.push({
            index: 9,
            value: currency(dsDataRows[i].THUE_DK),
            style: { borders: { color: "#00000000", lineStyle: "thin" },hAlign: "Right" },
          });
          row.cells.push({
            index: 10,
            value: currency(dsDataRows[i].NOGOC_PS),
            style: { borders: { color: "#00000000", lineStyle: "thin" },hAlign: "Right" },
          });
          row.cells.push({
            index: 11,
            value: currency(dsDataRows[i].THUE_PS),
            style: { borders: { color: "#00000000", lineStyle: "thin" },hAlign: "Right" },
          }),
            row.cells.push({
              index: 12,
              value: currency(dsDataRows[i].TONG_NO),
              style: { borders: { color: "#00000000", lineStyle: "thin" },hAlign: "Right" },
            });

          rows.push(row);
        }

        rows.push({
          index: rowIndex++,
          cells: [
            {
              index: 1,
              value: `Tổng cộng: `,
              colSpan: 6,
              style: {
                borders: { color: "#00000000", lineStyle: "thin" },
                hAlign: "Right",
                bold: true,
              },
            },
            {
              index: 7,
              value: '',
              style: {
                borders: { color: "#00000000", lineStyle: "thin" },
                hAlign: "Right",
                bold: true,
              },
            },
            {
              index: 8,
              value: currency(dsDataRows.reduce(
                (total, element) => total + element.NOGOC_DK,
                0
              )),
              style: {
                borders: { color: "#00000000", lineStyle: "thin" },
                hAlign: "Right",
                bold: true,
              },
            },
            {
              index: 9,
              value: currency(dsDataRows.reduce(
                (total, element) => total + element.THUE_DK,
                0
              )),
              style: {
                borders: { color: "#00000000", lineStyle: "thin" },
                hAlign: "Right",
                bold: true,
              },
            },
            {
              index: 10,
              value: currency(dsDataRows.reduce(
                (total, element) => total + element.NOGOC_PS,
                0
              )),
              style: {
                borders: { color: "#00000000", lineStyle: "thin" },
                hAlign: "Right",
                bold: true,
              },
            },
            {
              index: 11,
              value: currency(dsDataRows.reduce(
                (total, element) => total + element.THUE_PS,
                0
              )),
              style: {
                borders: { color: "#00000000", lineStyle: "thin" },
                hAlign: "Right",
                bold: true,
              },
            },
            {
              index: 12,
              value: currency(dsDataRows.reduce(
                (total, element) => total + element.TONG_NO,
                0
              )),
              style: {
                borders: { color: "#00000000", lineStyle: "thin" },
                hAlign: "Right",
                bold: true,
              },
            },
          ],
        });

        rows.push(this.emptyRow(rowIndex++));
        // rows.push({
        //   index: rowIndex++,
        //   cells: [
        //     {
        //       index: 1,
        //       value: `Địa chỉ liên hệ: ${this.$root.token.getMaTinh()}`,
        //       colSpan: 10,
        //       style: { hAlign: "Left", bold: true },
        //     },
        //   ],
        // });

        rows.push({
          index: rowIndex++,
          cells: [
            {
              index: 1,
              value: `Địa chỉ liên hệ: ${this.smtp_dclh}`,
              colSpan: 10,
              style: { hAlign: "Left", bold: true },
            },
          ],
        });

        rows.push({
          index: rowIndex++,
          cells: [
            {
              index: 1,
              value: `NGÀY ${moment().format("DD/MM/YYYY")} - LIÊN HỆ : ${
                rowKhachHang.TEN_NV
              }, ĐT: ${this.smtp_sdt}, MAIL: ${this.smtp_email}`,
              colSpan: 10,
              style: { hAlign: "Left", bold: true },
            },
          ],
        });

        if (this.$root.token.getPhanVungID() == 28) {
          rows.push({
            index: rowIndex++,
            cells: [
              {
                index: 1,
                value: `${this.smtp_thongtin}`,
                colSpan: 10,
                style: { hAlign: "Left", bold: true },
              },
            ],
          });
        }
        // rows.push({
        // 	index: rowIndex++,
        // 	cells: [

        // 	]
        // })

        let worksheets = [{ columns, rows }];
        let workbook = new Workbook({ worksheets }, "xlsx");
        workbook.save(`${rowKhachHang.NHOMKH_ID}_${rowKhachHang.TENNHOM}.xlsx`);
      } catch (e) {
        console.log(e);
      }
    },
    */
    emptyRow(index) {
      let row = {
        index: index,
        cells: [],
      };
      row.cells.push({
        index: 1,
        colSpan: 10,
        value: "",
        style: {},
      });
      return row;
    },
    async getDsNhanVien() {
      try {
        this.pDsNhanVien = [];
        let rs = await this.$root.context.get(
          "/web-cskh/api/nhom-in-tong-hop/nhan-vien",
          {
            nhanVienId: this.$root.token.getNhanVienID(),
            pageNo: 1,
            pageSize: 10000,
          }
        );
        if (rs.error_code === this.API_SUCCESS) {
          let ds = rs.data.data || [];
          ds.forEach((e) => {
            this.pDsNhanVien.push({
              id: e.NHANVIEN_ID,
              text: e.TEN_NV,
            });
          });
          this.$nextTick(() => {
            if (this.pDsNhanVien.length > 0) {
              this.pDsNhanVienSelected = this.pDsNhanVien[0].id;
            }
          });
        }
      } catch (e) {
        console.log(e);
      }
    },
    async getDsKhachHang() {
      try {
        this.pDsKhachHang = [];
        let rs = await this.$root.context.get(
          "/web-cskh/api/in-tong-hop-khl/tim-kiem",
          {
            nhanVienId: this.pDsNhanVienSelected,
            timKiemId: this.pDsTimKiemSelected,
            giaTriTimKiem: this.pTxtTimKiem,            
            kyHoaDon: this.kyCuoc,
            kieuIn: this.pDsKieuGuiSelected,
            pageNo: 1,
            pageSize: 10000,
          }
        );
        if (rs.error_code === this.API_SUCCESS) {
          this.pDsKhachHang = rs.data.data || [];
          this.pDsKhachHang.forEach((e) => {
            e.TIENNO_TEXT = currency(e.TIENNO);
          });
        //   if (this.pDsKhachHang.length > 0) {
        //     this.$nextTick(() =>
        //       this.$refs.gridDsKhachHang.setCurrentSelectedRow(0)
        //     );
        //   }
          this.ds_danhSachKH = [...this.pDsKhachHang]
        }

        if (!this.pDsKhachHang || this.pDsKhachHang.length == 0) {
          this.$root.toastError("Không có dữ liệu, vui lòng thử lại.");
        }
      } catch (e) {
        console.log(e);
      }
    },
    onGridDsKhachHang_RowSelected(item) {
      if (item) {
        this.khachHangId = item.KHACHHANG_ID;
        

      }
    },
    getdsEmails() {
    //   const result = this.rowSelectedKH
    //   let dsKH = this.pDsKhachHang.filter((x) =>
    //     result.includes(x.NHOMKH_ID)
    //   );
      let dsKH = this.$refs.gridDsKhachHang.getSelectedRecords() || [];
      let emails = [];
      dsKH.forEach((x) => emails.push({ id: x.NHOMKH_ID, text: x.MAIL }));
      this.dsEmails = emails;
    },
    onGuiEmailClicked() {
      this.getdsEmails();
      this.$refs.frmSendMail.openDialog();
    },
    async onInTHClicked() {
      if (this.pDsKhachHang.length <= 0 && !this.checkInAll) {
        this.$root.toastError("Không có dữ liệu để tạo file !");
        return;
      }
    //   const dsKH = this.rowSelectedKH
      const dsSelected = this.$refs.gridDsKhachHang.getSelectedRecords() || [];
      let dsKH = []
      const dsIn = this.checkInAll ? [] : dsSelected
      dsKH = dsIn.map(item => item.NHOMKH_ID)

      if (dsKH.length <= 0 && !this.checkInAll) {
        this.$root.toastError("Bạn chưa chọn dữ liệu để in TH !");
        return;
      }
      const selectNhanvien = this.pDsNhanVien.find(item=>item.id == this.pDsNhanVienSelected)

      this.loading(true);
      try {
        const baseUrl = process.env.API;
        await this.axios({
          url: `${baseUrl}/web-cskh/api/in-tong-hop-khl/in-tong-hop-cuoc-dvvt`,
          method: "POST",
          responseType: "blob",
          data: {
            kyHoaDon: this.kyCuoc,
            dsNhomKH: dsKH,
            kieu: this.pDsKieuInSelected,
            diaChiLH: this.smtp_dclh,
            moTa: this.smtp_thongtin,
            chuyenKhoan: this.smtp_chuyenKhoan,
            tenNhanVien:selectNhanvien.text,
            dienThoai:this.smtp_sdt,
            email:this.smtp_email,
            checkAll: this.checkInAll ? 1: 0,
            nhanVienID: this.pDsNhanVienSelected
          },
        }).then((response) => {
          previewPrint(response.data);
        });
      } catch (error) {
        this.$root.toastError("Có lỗi trong quá trình in báo cáo!");
      } finally {
        this.loading(false);
      }
    },
    async onExcelExport(){
        
        if (!this.pDsNhanVienSelected) {
          this.$root.toastError("Chưa chọn nhân viên!");
          return;
        }
        this.loading(true);
        try {
          const baseUrl = process.env.API;
          await this.axios({
          url: `${baseUrl}/web-cskh/api/in-tong-hop-khl/export-excel-nhom-in-tong-hop/?nhanVienId=${this.pDsNhanVienSelected}&kyHoaDon=${this.kyCuoc}&loaiId=1`,
          method: "GET",
          responseType: "blob",
          }).then((response) => {
          this.downloadFileZip(`InTH.xlsx`,response.data)
          });
        } catch (e) {
          console.log(e)
            this.$root.toastError("Có lỗi trong quá trình xuất excel! Mời thử lại");
        } finally {
            this.loading(false);
        }
    },

    async Ver2_onTaoFileClicked(type) {
      if (this.pDsKhachHang.length <= 0 && !this.checkInAll) {
        this.$root.toastError("Không có dữ liệu để tạo file !");
        return;
      }
    //   const dsKH = this.rowSelectedKH
      const dsSelected = this.$refs.gridDsKhachHang.getSelectedRecords() || [];
      let dsKH = []
      const dsIn = this.checkInAll ? [] : dsSelected
      dsKH = dsIn.map(item => item.NHOMKH_ID)
      if (dsKH.length <= 0 && !this.checkInAll) {
        this.$root.toastError("Bạn chưa chọn dữ liệu để tạo file!");
        return;
      }
      const selectNhanvien = this.pDsNhanVien.find(item=>item.id == this.pDsNhanVienSelected)

      this.loading(true);
      try {
        const baseUrl = process.env.API;
        await this.axios({
          url: `${baseUrl}/web-cskh/api/in-tong-hop-khl/export-file-tong-hop-cuoc`,
          method: "POST",
          responseType: "blob",
          data: {
            kyHoaDon: this.kyCuoc,
            dsNhomKH: dsKH,
            kieu: this.pDsKieuInSelected,
            diaChiLH: this.smtp_dclh,
            moTa: this.smtp_thongtin,
            chuyenKhoan: this.smtp_chuyenKhoan,
            tenNhanVien:selectNhanvien.text,
            dienThoai:this.smtp_sdt,
            email:this.smtp_email,
            checkAll: this.checkInAll ? 1: 0,
            nhanVienID: this.pDsNhanVienSelected,
            kieuFile: type, // 1: excel, 2: pdf
          },
        }).then((response) => {
          let name = moment(this.kyCuoc,'YYYYMMDD').format('MMYYYY');
          this.downloadFileZip(`${selectNhanvien.text}_${name}.zip`,response.data)
        });
      } catch (error) {
        this.$root.toastError("Có lỗi trong quá trình tạo file! Mời thử lại");
      } finally {
        this.loading(false);
      }
    },
    downloadFileZip(filename, data){
        let blob = new Blob([data],{type:"octet/stream"})
        let blobUrl = URL.createObjectURL(blob);

        var element = document.createElement('a');
        element.setAttribute('href', blobUrl);
        element.setAttribute('download', filename);

        element.style.display = 'none';
        document.body.appendChild(element);

        element.click();

        document.body.removeChild(element);
    },

    async onTBCTaoFile(type){
      if (this.pDsKhachHang.length <= 0 && !this.checkInAll) {
        this.$root.toastError("Không có dữ liệu để tạo file !");
        return;
      }
      const dsSelected = this.$refs.gridDsKhachHang.getSelectedRecords() || [];
      let dsKH = []
      const dsIn = this.checkInAll ? [] : dsSelected
      dsKH = dsIn.map(item => item.NHOMKH_ID)
      if (dsKH.length <= 0 && !this.checkInAll) {
        this.$root.toastError("Bạn chưa chọn dữ liệu để tạo file!");
        return;
      }
      const selectNhanvien = this.pDsNhanVien.find(item=>item.id == this.pDsNhanVienSelected)

      this.loading(true);
      try {
        const baseUrl = process.env.API;
        await this.axios({
          url: `${baseUrl}/web-cskh/api/in-tong-hop-khl/thong-bao-cuoc-cbo`,
          method: "POST",
          responseType: "blob",
          data: {
            kyHoaDon: this.kyCuoc,
            dsNhomKH: dsKH,
            kieu: this.pDsKieuInSelected,
            diaChiLH: this.smtp_dclh,
            moTa: this.smtp_thongtin,
            chuyenKhoan: this.smtp_chuyenKhoan,
            tenNhanVien:selectNhanvien.text,
            dienThoai:this.smtp_sdt,
            email:this.smtp_email,
            checkAll: this.checkInAll ? 1: 0,
            nhanVienID: this.pDsNhanVienSelected,
            kieuFile: type
          },
        }).then((response) => {
          let name = moment(this.kyCuoc,'YYYYMMDD').format('MMYYYY');
          this.downloadFileZip(`${selectNhanvien.text}_${name}.zip`,response.data)
        });
      } catch (error) {
        this.$root.toastError("Có lỗi trong quá trình tạo file! Mời thử lại");
      } finally {
        this.loading(false);
      }
    },

    onCapNhapTTLHClicked() {
      this.$refs.thongtinlienhe.show();
    },

    openFilterDialog () {
      this.pDsKhachHang = [...this.ds_danhSachKH]
      this.$refs.refFilterDialog.openDialog()
    },

    onAcceptFilterHandler(args){
        if (args !== null) {
            let ret = []
            if (args.tu_tien === null) {
            ret = this.ds_danhSachKH.filter(x => x.TIENNO <= args.den_tien)
            } else if (args.den_tien === null) {
            ret = this.ds_danhSachKH.filter(x => x.TIENNO >= args.tu_tien)
            } else {
            ret = this.ds_danhSachKH.filter(x => x.TIENNO >= args.tu_tien && x.TIENNO <= args.den_tien)
            }
            ret.sort((a,b) => (a.TIENNO - b.TIENNO))
            this.pDsKhachHang = [...ret]
        } else {
            this.pDsKhachHang = [...this.ds_danhSachKH]
        }
    }
  },
};
</script>
