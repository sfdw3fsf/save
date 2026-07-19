<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import moment from "moment";
import breadcrumb from "@/components/breadcrumb";
import API from "../api/DieuChinhNoKhieuNai";
import GachNoAPI from "../api/GachNoAPI";
import { currency } from "@/filters/currency";

export default {
  components: { breadcrumb },
  data() {
    return {
      header: {
        title: "ĐIỀU CHỈNH NỢ KHIẾU NẠI",
        list: [
          { name: "Gạch nợ", link: { name: "Ui.cards" } },
          {
            name: "Điều chỉnh nợ khiếu nại",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      idSelected: "1",
      kyHoaDon: moment(new Date())
        .subtract(1, "months")
        .endOf("month")
        .toDate(),
      max_kyhoadon: moment(new Date())
        .subtract(1, "months")
        .endOf("month")
        .toDate(),
      chuKy: "01",
      kyCuoc: "",
      form: {
        maTT: "",
        soMay: "",
        tenTT: "",
        diaChiTT: "",
        idHinhThucTT: "",
        lyDo: "",
        ndKhieuNai: "",
        idThuQuy: "",
        idDaiLy: "Đại lý - Tuyến thu",
        ngayTT: new Date(),
        thangDC: "",
        idLoaiTien: "",
        tienNo: 0,
        tienTra: 0,
      },
      dsThuQuy: [],
      dsHinhThuc: [],
      dsLoaiTien: [],
      dsKhieuNai: [],
      thongTinTemp: null,
      dsGC: [],
      dsKhieuNaiId: [],
      ma_tn: "",
      quaythu_id: "",
      isFirst: false,
      fields: [
        {
          fieldName: "NGAY_TN",
          headerText: "Ngày tiếp nhận",
          allowFiltering: true,
        },
        { fieldName: "MA_TT", headerText: "Mã TT", allowFiltering: true },
        { fieldName: "MA_TB", headerText: "Số máy/Acc", allowFiltering: true },
        {
          fieldName: "MATB_DD",
          headerText: "Mã đại diện",
          allowFiltering: true,
        },
        {
          fieldName: "MA_KN",
          headerText: "Mã khiếu nại",
          allowFiltering: true,
        },
        {
          fieldName: "TEN_DVVT",
          headerText: "Dịch vụ VT",
          allowFiltering: true,
        },
        {
          fieldName: "TONGNO",
          headerText: "Tổng nợ",
          allowFiltering: true,
          type: "number",
          format: "N0",
        },
        {
          fieldName: "DATRA",
          headerText: "Đã trả",
          allowFiltering: true,
          type: "number",
          format: "N0",
        },
        {
          fieldName: "TONG_NOCL",
          headerText: "Còn nợ",
          allowFiltering: true,
          type: "number",
          format: "N0",
        },
        {
          fieldName: "TONG_GC",
          headerText: "Giảm trừ",
          allowFiltering: true,
          type: "number",
          format: "N0",
        },
        {
          fieldName: "CONNO",
          headerText: "Sau giảm trừ",
          allowFiltering: true,
          type: "number",
          format: "N0",
        },
        {
          fieldName: "NGUOI_KN",
          headerText: "Người khiếu nại",
          allowFiltering: true,
        },
        {
          fieldName: "ND_KHIEUNAI",
          headerText: "Nội dụng khiếu nại",
          allowFiltering: true,
        },
        { fieldName: "DIENTHOAI_LH", headerText: "SĐT", allowFiltering: true },
      ],
      fields_USD: [
        {
          fieldName: "NGAY_TN",
          headerText: "Ngày tiếp nhận",
          allowFiltering: true,
        },
        { fieldName: "MA_TT", headerText: "Mã TT", allowFiltering: true },
        { fieldName: "MA_TB", headerText: "Số máy/Acc", allowFiltering: true },
        {
          fieldName: "MATB_DD",
          headerText: "Mã đại diện",
          allowFiltering: true,
        },
        {
          fieldName: "MA_KN",
          headerText: "Mã khiếu nại",
          allowFiltering: true,
        },
        {
          fieldName: "TEN_DVVT",
          headerText: "Dịch vụ VT",
          allowFiltering: true,
        },
        {
          fieldName: "TONGNO",
          headerText: "Tổng nợ",
          allowFiltering: true,
          type: "number",
          format: "N2",
        },
        {
          fieldName: "DATRA",
          headerText: "Đã trả",
          allowFiltering: true,
          type: "number",
          format: "N2",
        },
        {
          fieldName: "TONG_NOCL",
          headerText: "Còn nợ",
          allowFiltering: true,
          type: "number",
          format: "N2",
        },
        {
          fieldName: "TONG_GC",
          headerText: "Giảm trừ",
          allowFiltering: true,
          type: "number",
          format: "N2",
        },
        {
          fieldName: "CONNO",
          headerText: "Sau giảm trừ",
          allowFiltering: true,
          type: "number",
          format: "N2",
        },
        {
          fieldName: "NGUOI_KN",
          headerText: "Người khiếu nại",
          allowFiltering: true,
        },
        {
          fieldName: "ND_KHIEUNAI",
          headerText: "Nội dụng khiếu nại",
          allowFiltering: true,
        },
        { fieldName: "DIENTHOAI_LH", headerText: "SĐT", allowFiltering: true },
      ],
      keyReset: 1
    };
  },
  validations: {},
  watch: {
    idSelected: {
      handler: function (val) {
        this.dsKhieuNai = [];
        this.resetThongTin();
      },
    },
    "form.idThuQuy": {
      handler: async function (value) {
        const objThuNgan = this.dsThuQuy.find(
          (item) => item.NHANVIEN_ID == value
        );
        if (objThuNgan) {
          this.quaythu_id = objThuNgan.DONVI_ID;
          this.ma_tn = objThuNgan.MA_NV;
        }
      },
    },
    kyCuoc: {
      handler: function (val) {
        if (this.isFirst) this.fetchDSKhieuNai();
      },
    },
  },
  created() {
     this.fetchDSHinhThucTT();
     this.fetchDSThuQuy();
    //this.fetchDSLoaiTien();
    this.kyCuoc = moment(this.kyHoaDon).format("YYYYMM") + this.chuKy;
    this.getDSLoaiTien();
  },
  mounted() {
    this.$refs.maTT.focus();
    this.kyCuoc = moment(this.kyHoaDon).format("YYYYMM") + this.chuKy;
    this.form.thangDC = this.kyHoaDon;
    
  },
  methods: {
    onKyHoaDonChange: function (args) {
      if (args.value === null) {
        this.$root.toastError("Vui lòng chọn Kỳ hóa đơn");
        return;
      }
      this.kyCuoc = moment(args.value).format("YYYYMM") + this.chuKy;
      this.form.thangDC = args.value;
      this.isFirst = true;
    },
    async fetchDSThuQuy() {
      const response = await API.getDanhSachThuQuy(this.axios);
      if (
        response.data.error_code === "BSS-00000000" &&
        response.data.data.length > 0
      ) {
        this.dsThuQuy = response.data.data;
        this.form.idThuQuy = response.data.data[0].NHANVIEN_ID;
      }
    },
    async fetchDSHinhThucTT() {
      const params = {
        nhomHTTT: 7,
        kieu: 0,
      };
      const response = await API.getDSHinhThuc_TT(this.axios, params);
      if (
        response.data.error_code === "BSS-00000000" &&
        response.data.data.length > 0
      ) {
        this.dsHinhThuc = response.data.data;
        this.form.idHinhThucTT = response.data.data[0].HTTT_ID;
      }
    },
    async fetchDSLoaiTien() {
      const response = await API.getDanhSachLoaiTien(this.axios);
      if (
        response.data.error_code === "BSS-00000000" &&
        response.data.data.length > 0
      ) {
        this.dsLoaiTien = response.data.data;
        this.form.idLoaiTien = response.data.data[0].LOAITIEN_ID;
      }
    },
    async fetchDSKhieuNai() {
      this.keyReset += 1;
      this.loading(true);
      try {
        const params = {
          kyCuoc: this.kyCuoc,
          ttphId: this.idSelected,
          maND: this.$auth.getUserName(), //"thuyltt.hpg"
          maTT: this.form.maTT,
          pLoaiTien: this.form.idLoaiTien,
        };
        const response = await API.getDSKhieuNai(this.axios, params);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.dsKhieuNai = response.data.data;
        } else {
          this.dsKhieuNai = [];
          this.resetThongTin();
          this.$root.toastError("Không có dữ liệu. Mời thử lại!");
        }
      } catch (error) {
        this.dsKhieuNai = [];
        this.resetThongTin();
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async NAP_DS_GC(id) {
      // this.loading(true);
      try {
        const response = await API.getDanhSachGC(this.axios, id);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          const dsResult = response.data.data;
          this.dsGC = dsResult;
          this.form.thangDC = dsResult[0].THANG_GCHT
        } else {
          this.dsGC = [];
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchDieuChinh() {
      const dsDC = this.$refs.gridDanhsach.getSelectedRecords() || [];
      const dsResult = dsDC.map((item) => ({
        maTT: item.MA_TT,
        maTB: item.MA_TB,
        tienDC: item.TONG_GC,
        khieuNaiId: item.KHIEUNAI_ID        
      }));
      this.loading(true);
      try {
        const data = {
          kyCuoc: this.kyCuoc,
          ds: dsResult,
          chungTu: null,
          htttId: this.form.idHinhThucTT,
          maTN: this.ma_tn,
          lyDo: this.form.lyDo,
          thangDC: moment(this.form.thangDC).format("YYYYMM") + this.chuKy,
          pLoaiTien:this.form.idLoaiTien
        };
        const response = await API.gachNoDieuChinh(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Gạch nợ thành công!");
          this.form.maTT = "";
          this.fetchDSKhieuNai();
        } else {
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchHuyGC() {
      this.loading(true);
      try {
        const data = {
          dsKhieuNaiId: this.dsKhieuNaiId,
          lyDoHuy: this.form.lyDo,
        };
        const response = await API.huyPhieu(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Hủy giảm cước thành công");
          this.form.maTT = "";
          this.fetchDSKhieuNai();
        } else {
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchXoaPhieu() {
      this.loading(true);
      try {
        const data = {
          pKyCuoc: this.kyCuoc,
          pPhieuId: this.thongTinTemp.PHIEU_ID,
          pMayHuy: "",
          pNguoiHuy: "",
        };
        const response = await API.xoaPhieu(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Xóa phiếu thành công");
          this.form.maTT = "";
          this.fetchDSKhieuNai();
        } else {
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    ds_selectedItemsChanged(data) {
      this.dsKhieuNaiId = data;
    },
    ds_selectedRowChanged(index, data) {
      if (data) {
        this.fillThongTin(data);
        this.thongTinTemp = data;
      } else {
        this.thongTinTemp = null;
        this.resetThongTin();
      }
    },
    fillThongTin(data) {
      this.form.maTT = data.MA_TT;
      this.form.soMay = data.MA_TB;
      this.form.tenTT = data.TEN_TT;
      this.form.diaChiTT = data.DIACHI_TT;
      this.form.tienNo = currency(data.TONG_NOCL);
      this.form.tienTra = currency(data.TONG_GC);
      this.form.ndKhieuNai = data.ND_KHIEUNAI;
      this.form.thangDC = data.kyHoaDon;
      this.form.idDaiLy = `Đại lý: ${data.DAILY || ""} - ${data.TEN_DAILY || ""
        } - NV Quản lý: ${data.MANV_QL || ""} - ${data.TENNV_QL || ""} - ${data.SO_DT || ""
        }`;
      this.NAP_DS_GC(data.KHIEUNAI_ID);
    },
    resetThongTin() {
      this.form.maTT = "";
      this.form.soMay = "";
      this.form.tenTT = "";
      this.form.diaChiTT = "";
      this.form.idDaiLy = "Đại lý - Tuyến thu";
      this.form.ngayTT = new Date();
      this.form.thangDC = this.kyHoaDon;
      this.form.tienNo = 0;
      this.form.tienTra = 0;
      this.form.lyDo = "";
      this.form.ndKhieuNai = "";
      this.dsGC = [];
    },
    TimKiem() {
      this.fetchDSKhieuNai();
    },
    DieuChinh() {
      if (this.dsKhieuNai.length <= 0) {
        this.$root.toastError(
          "Không có dữ liệu phiếu khiếu nại để điều chỉnh!"
        );
        return;
      }
      const dsSelected = this.$refs.gridDanhsach.getSelectedRecords() || [];
      if (dsSelected.length <= 0) {
        this.$root.toastError(
          "Bạn chưa chọn danh sách khiếu nại để điểu chỉnh!"
        );
        return;
      }
      if (this.form.idHinhThucTT == "") {
        this.$root.toastError("Chưa chọn hình thức thanh toán!");
        return;
      }
      if (this.form.lyDo.trim() == "") {
        this.$root.toastError("Bạn chưa điền lý do điều chỉnh!");
        this.$refs.lyDo.focus();
        return;
      }
      // ngày TT thuộc trong kỳ
      this.fetchDieuChinh();
    },
    HuyGiamCuoc() {
      if (this.dsKhieuNai.length <= 0) {
        this.$root.toastError("Không có dữ liệu phiếu khiếu nại để hủy!");
        return;
      }
      const dsSelected = this.$refs.gridDanhsach.getSelectedRecords() || [];
      if (dsSelected.length <= 0) {
        this.$root.toastError("Bạn chưa chọn danh sách khiếu nại để hủy");
        return;
      }
      if (this.form.lyDo.trim() == "") {
        this.$root.toastError("Bạn điền lý hủy giảm cước!");
        this.$refs.lyDo.focus();
        return;
      }
      this.fetchHuyGC();
    },
    XoaPhieu() {
      if (this.dsKhieuNai.length <= 0) {
        this.$root.toastError("Không có dữ liệu phiếu khiếu nại để xóa!");
        return;
      }
      if (this.thongTinTemp == null) {
        this.$root.toastError("Bạn chưa chọn danh sách khiếu nại để xóa!");
        return;
      }
      this.fetchXoaPhieu();
    },
    HuongDan() { },
    async editGridSanPham(arg) {
      let KEY = arg.rowData.KHIEUNAI_ID;
      if (arg.columnName == "TONG_GC") {
        if (arg.value) {
          this.dsKhieuNai.forEach((element) => {
            if (element.KHIEUNAI_ID == KEY) {
              element.TONG_GC = arg.value;
              this.form.tienTra = currency(arg.value);
              return;
            }
          });
        }
      }
    },
  },
  computed: {
    getFields: function () {      
      let columns = this.form.idLoaiTien==1?this.fields:this.fields_USD;
      if (this.idSelected == "4") {
        columns = [
          ...columns,
          ...[
            {
              fieldName: "NGAY_TT",
              headerText: "Ngày TT",
              allowFiltering: true,
            },
            {
              fieldName: "PHIEU_ID",
              headerText: "Phiếu ID",
              allowFiltering: true,
            },
          ],
        ];
      }
      if (this.idSelected == "3") {
        columns = [
          ...columns,
          ...[
            {
              fieldName: "NGAY_TT",
              headerText: "Ngày TT",
              allowFiltering: true,
            },
            {
              fieldName: "LYDO_HUYGC",
              headerText: "Lý do hủy",
              allowFiltering: true,
            },
            {
              fieldName: "PHIEU_ID",
              headerText: "Phiếu ID",
              allowFiltering: true,
            },
          ],
        ];
      }
      return columns;
    },
    getDSLoaiTien () {
      try {
        GachNoAPI.getDSLoaiTien(this.axios, {pKyCuoc: this.kyCuoc})
          .then(
            (response) => {
              if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                this.dsLoaiTien = response.data.data
                this.form.idLoaiTien = response.data.data[0].LOAITIEN_ID
              }
            }
          )
          .catch(error => {
            this.$toast.error(error.data.message_detail)
          })
      } catch (error) {
        this.$toast.error(error)
      }
    },
  },
};
</script>
