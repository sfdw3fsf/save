<template src='./QuanLyThuTuIn.html'></template>
<style src='./QuanLyThuTuIn.scss' scoped></style>
<script>
import xlsx from "xlsx";
import { mapActions, mapState } from "vuex";
import KyCuoc from "../components/KyCuoc.vue";
import moment from "moment";
import ComboboxGrid from "../components/ComboboxGrid";

export default {
  components: { appKyCuoc: KyCuoc, ComboboxGrid },
  name: "QuanLyThuTuIn",
  mounted() {},
  computed: {
    ...mapState("quanlythutuin", ["danhsachLoi", "danhsachNhanVienTC"]),
    checkTucThi() {
      const now = new Date().getDate();
      const tinhthanh = this.$auth.getMaTinh()
      // console.log(tinhthanh, now)
      if (tinhthanh === 'HCM') {
        if (now === 1) {
          return false;
        } else {
          return true;
        }
      } else {
        if ([1, 2, 3, 4].includes(now)) {
          return false;
        } else {
          return true;
        }
      }
      
    },
  },
  data() {
    return {
      lbl_thongbao: 'Chỉ được chọn tức thì trong những ngày từ 1-4 hàng tháng',
      prevMonth: moment(new Date())
        .subtract(1, "months")
        .endOf("month")
        .toDate(),
      pKyCuoc: "",
      isTab: false,
      duongDan: "",
      dsLoi: [],
      dsThuTuIn: [],
      selectNV: "",
      fieldsLoi: [
        {
          fieldName: "MA_TT",
          headerText: "Mã thanh toán",
          allowFiltering: true,
        //   width: 200,
        //   isSum: true,
        //   sumTotal: true,
        },
        {
          fieldName: "GHICHU",
          headerText: "Ghi chú",
          allowFiltering: true,
        },
      ],
      fieldsThuTu: [
        {
          fieldName: "ma_tt",
          headerText: "Mã thanh toán",
          allowFiltering: true,
        //   width: 200,
        //   isSum: true,
        //   sumTotal: true,
        },
        {
          fieldName: "thutu_in",
          headerText: "Thứ tự",
          allowFiltering: true,
        },
        {
          fieldName: "dotgiao_id",
          headerText: "Đợt",
          allowFiltering: true,
        },
      ],
      dsMaTTTemp: [],
      dsDanhSachTemp: [],
      pTucThi: false,
      animationSettings: { effect: "Zoom" },
      target: ".main-wrapper"
    };
  },
  methods: {
    ...mapActions("quanlythutuin", [
      "fetchKiemtraSolieu",
      "getDanhSachNVThuCuoc",
      "capNhatDotGiao",
    ]),
    openDialog() {
      this.$refs.dlgQuanLyThuTuIn.show();
    },
    handleKyCuoc(e) {
      this.pKyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no;
    },
    Nhapmoi() {
      this.dsLoi = [];
      this.dsThuTuIn = [];
      this.duongDan = "";
    },

    async onChangeFile(e) {
      const files = e.target.files;
      this.duongDan = e.target.files[0].name;
      if (!files.length) {
        return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$root.toastError("File excel không đúng định dạng!");
      }
      const fileReader = new FileReader();
      fileReader.onload = async (ev) => {
        try {
          this.dsLoi = [];
          this.dsThuTuIn = [];
          const data = ev.target.result;
          const XLSX = xlsx;
          const workbook = XLSX.read(data, {
            type: "binary",
          });
          const wsname = workbook.SheetNames[0];
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
          const excellist = [];
          let dsDanhSach = [];
          let dsMaTT = [];
          let dsDuplicateMaTT = [];
          // Edit data
          for (var i = 0; i < ws.length; i++) {
            excellist.push(ws[i]);
            dsMaTT.push(ws[i].MA_TT);
            dsDanhSach.push({
              ma_tt: ws[i].MA_TT,
              thutu_in: ws[i].THUTU,
              dotgiao_id: ws[i].DOT,
            });
          }

          var propCount = 0;
          if (excellist && excellist.length > 0) {
            for (var prop in excellist[0]) {
              if (excellist[0].hasOwnProperty(prop)) propCount++;
            }
          }
          if (propCount == 0) {
            this.dsLoi.push({
              MA_TT: "",
              GHICHU: "Không có thứ tự;Không có đợt",
            });
            return;
          }
          if (propCount < 3) {
            this.$refs.duongDan.focus();
            this.$refs.duongDan.select();
            this.$root.toastError("Số lượng cột phải >= 3 cột!");
            return;
          }
          const arrayCol = Object.keys(excellist[0]);
          if (
            arrayCol[0] !== "MA_TT" ||
            arrayCol[1] !== "THUTU" ||
            arrayCol[2] !== "DOT"
          ) {
            this.$refs.duongDan.focus();
            this.$refs.duongDan.select();
            this.$root.toastError("Thứ tự cột phải là: MA_TT, THUTU, DOT!");
            return;
          }
          // if (
          //   (!excellist[0].hasOwnProperty("MA_TT") &&
          //     !excellist[0].hasOwnProperty("THUTU")) ||
          //   !excellist[0].hasOwnProperty("DOT")
          // ) {
          //   this.$root.toastWarning(
          //     "File cần có chính xác 3 cột MA_TT, THUTU, DOT!"
          //   );
          //   return;
          // }
          dsMaTT.forEach((element) => {
            if (
              dsMaTT.indexOf(element) != dsMaTT.lastIndexOf(element) &&
              !dsDuplicateMaTT.includes(element)
            )
              dsDuplicateMaTT.push(element);
          });
          dsDuplicateMaTT.forEach((element) => {
            this.dsLoi.push({
              MA_TT: element,
              GHICHU: "Thanh toán bị lặp lại",
            });
          });

          this.dsMaTTTemp = dsMaTT;
          this.dsDanhSachTemp = dsDanhSach;
          //console.log("Read results", excellist); // At this point, you get an array containing objects that need to be processed
        } catch (e) {
          return this.$root.toastError("Kiểm tra dữ liệu file đầu vào!");
        }
      };
      fileReader.readAsBinaryString(files[0]);
    },
    async DanhSach() {
      if (this.duongDan.trim() === "") {
        this.$root.toastError("Hãy chọn đường dẫn!");
        this.$refs.fileInput.focus();
        return;
      }

      // if (this.selectNV === "") {
      //   this.$root.toastWarning("Bạn chưa chọn nhân viên!");
      //   this.$refs.selectNV.focus();
      //   return;
      // }

      if (this.dsLoi.length > 0) {
        this.isTab = true;
        return;
      }

      const pDSThanhToanId = this.dsMaTTTemp.toString();
      const dataBody = {
        pNhanVienThuId: this.selectNV, // 8250
        pDSThanhToanId,
      };
      this.loading(true);
      const response = await this.fetchKiemtraSolieu(dataBody);
      if (response.data.error_code === "BSS-00000000") {
        this.loading(false);
        if (response.data.data !== undefined && response.data.data.length > 0) {
          this.isTab = true;
          this.dsLoi = response.data.data;
          this.$refs.tabLoi.setCurrentSelectedRow(0);
        } else {
          this.isTab = false;
          this.dsThuTuIn = this.dsDanhSachTemp;
          this.$refs.tabIn.setCurrentSelectedRow(0);
          this.dsLoi = [];
        }
      } else {
        this.loading(false);
        this.$root.toastError(response.message_detail);
      }
    },
    CapNhat() {
      if (this.selectNV === "") {
        this.$root.toastError("Bạn chưa chọn nhân viên!");
        // this.$refs.selectNV.focus();
        return;
      }
      if (this.dsThuTuIn.length <= 0) {
        this.$root.toastError("Chưa có danh sách để chuyển!");
        this.isTab = false;
        return;
      }
      if (this.dsLoi.length > 0) {
        this.$root.toastError(
          "Danh sách trong file đang lỗi, hãy kiểm tra lại file đầu vào!"
        );
        this.isTab = true;
        return;
      }
      this.$confirm(`Bạn có chắc chắn muốn cập nhật thứ tự in?`, "Xác nhận", {
        confirmButtonText: "Có",
        cancelButtonText: "Không",
      })
        .then(async () => {
          const dataBody = {
            pTucThi: this.pTucThi ? 1 : 0,
            pNhanVienThuId: this.selectNV,
            pDSCapNhat: this.dsThuTuIn,
            // pDSCapNhat :  [
            // {
            //     "ma_tt": "HPG-04-603630",
            //     "thutu_in": 1,
            //     "dotgiao_id": 100
            // }
            // ]
          };
          this.loading(true);
          const response = await this.capNhatDotGiao(dataBody);
          if (response.data.error_code === "BSS-00000000") {
            this.loading(false);
            this.$root.toastSuccess("Cập nhật dữ liệu thành công!");
          } else {
            this.loading(false);
            this.$root.toastError(response.message_detail);
          }
        })
        .catch(() => {});
    },
    XuatExcel() {
      if (this.dsThuTuIn.length <= 0) {
        this.$root.toastError("Chưa có danh sách để xuất Excel!");
        this.isTab = false;
        return;
      }
      this.loading(true);
      try {
        var dsach = xlsx.utils.json_to_sheet(this.dsThuTuIn);
        var wb = xlsx.utils.book_new(); // make Workbook of Excel
        xlsx.utils.book_append_sheet(wb, dsach, "TT"); // sheetAName is name of Worksheet
        xlsx.writeFile(wb, "mau.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    resetData() {
      this.duongDan = "";
      this.selectNV = "";
      this.dsLoi = [];
      this.dsThuTuIn = [];
    },
    openForm() {
      this.Nhapmoi();
      let data = {
        pNguoiDungId: this.$auth.getNguoiDungID()
      }
      this.getDanhSachNVThuCuoc(data);
    },
    nvtc_selectedChanged(dataItem){
      if(dataItem){
         this.selectNV = dataItem.NHANVIEN_ID
      }
    }
  },
  created() {
    const tinhthanh = this.$auth.getMaTinh()
    // console.log(tinhthanh, now)
    if (tinhthanh === 'HCM') {
      this.lbl_thongbao = 'Chỉ được chọn tức thì trong ngày mồng 1 hàng tháng'
    }
  },
};
</script>
