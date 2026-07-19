<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import xlsx from "xlsx";
import API from "../api/BanGoiCuoc_DD";

export default {
  components: {},
  data() {
    return {
      duongDan: "",
      ds_goi: [],
      ds_loi: [],
      isLoi: false,
    };
  },
  validations: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    onChapNhan() {
      if (this.isLoi) {
        this.$root.toastError("Dữ liệu đang có lỗi, mời thử lại!");
        return;
      }
      if (this.ds_goi.length <= 0) {
        this.$root.toastError("Danh sách trống, không có dữ liệu để cập nhật!");
        return;
      }
      this.onCapNhat();
    },
    async onCapNhat() {
      try {
        const data = this.ds_goi.map((item) => ({
          userDangKy: item.USER_DANGKY,
          maGoi: item.MA_GOI,
          soDTKhachHang: item.SODT_KH,
          ngayGio: item.NGAYGIO_DK,
          kenhDangKy: item.KENH_DANGKY,
          giaGoi: item.GIAGOI,
        }));
        const response = await API.capNhatDL(this.axios, { ds: data });
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Cập nhật kết quả thành công!");
          this.ds_goi = [];
          this.ds_loi = [];
          this.duongDan = "";
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    onChangeFile(e) {
      const files = e.target.files;
      this.duongDan = e.target.files[0].name;
      this.ds_goi = [];
      this.ds_loi = [];
      this.isLoi = false;
      if (!files.length) {
        return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$root.toastError("File excel không đúng định dạng!");
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
          const excellist = [];
          let dsDanhSach = [];
          for (let i = 0; i < ws.length; i++) {
            excellist.push(ws[i]);
            let lyDo = "";
            //Kiemtra null
            lyDo = !ws[i].USER_DANGKY
              ? "User đăng ký [USER_DANGKY]không được để trống!"
              : !ws[i].MA_GOI
              ? "Mã gói [MA_GOI] không được để trống!"
              : !ws[i].SODT_KH
              ? "Số điện thoại [SODT_KH] khách hàng không được để trống!"
              : !ws[i].NGAYGIO_DK
              ? "Ngày giờ đăng ký [NGAYGIO_DK] không được để trống!"
              : !ws[i].KENH_DANGKY
              ? "Kênh đăng ký [KENH_DANGKY] không được để trống!"
              : !ws[i].GIAGOI
              ? "Giá gói [GIAGOI] không được bỏ trống!"
              : "";
            if (lyDo) this.isLoi = true;

            //kiemtra dinh dang ngay nop
            // const isCheckDate = this.testDate(ws[i].NGAYNOP.toString());
            // if (!isCheckDate) {
            //   lyDo = "Ngày nộp không đúng định dạng dd/MM/yyyy";
            //   this.isLoi = true;
            // }
            //kiemtra giá gói
            if (isNaN(ws[i].GIAGOI)) {
              lyDo = "Giá gói [GIAGOI] phải là dạng số!";
              this.isLoi = true;
            }
            dsDanhSach.push({
              USER_DANGKY: ws[i].USER_DANGKY,
              MA_GOI: ws[i].MA_GOI,
              SODT_KH: ws[i].SODT_KH,
              NGAYGIO_DK: ws[i].NGAYGIO_DK,
              KENH_DANGKY: ws[i].KENH_DANGKY,
              GIAGOI: ws[i].GIAGOI,
              TRANGTHAI: lyDo,
            });
          }

          const arrayCol = Object.keys(excellist[0]);
          if (
            arrayCol[0] != "USER_DANGKY" &&
            arrayCol[1] != "MA_GOI" &&
            arrayCol[2] != "SODT_KH" &&
            arrayCol[3] != "NGAYGIO_DK" &&
            arrayCol[4] != "KENH_DANGKY" &&
            arrayCol[5] != "GIAGOI"
          ) {
            this.$root.toastError(
              "File không đúng định dạng, vui lòng tải lại file mẫu!"
            );
            return;
          }
          let danhSachLoi = [];
          dsDanhSach.map((item, index) => {
            if (!item.TRANGTHAI) {
              this.ds_goi.push({ ...item, STT: ++index });
            } else {
              danhSachLoi.push(item);
            }
          });
          if (danhSachLoi.length > 0) {
            this.$root.toastError("Dữ liệu đang có lỗi, mời thử lại!");
            this.ds_loi = danhSachLoi;
            return;
          } else {
            this.fetchKiemTraLoi(dsDanhSach);
          }
        } catch (error) {
          return this.$root.toastError("Kiểm tra dữ liệu file đầu vào!");
        }
      };
      fileReader.readAsBinaryString(files[0]);
    },
    async fetchKiemTraLoi(ds) {
      const data = ds.map((item) => ({
        userDangKy: item.USER_DANGKY,
        maGoi: item.MA_GOI,
        soDTKhachHang: item.SODT_KH,
        ngayGio: item.NGAYGIO_DK,
        kenhDangKy: item.KENH_DANGKY,
        giaGoi: item.GIAGOI,
      }));
      this.loading(true);
      const response = await API.kiemTraDuLieu(this.axios, { ds: data });
      if (response.data.error_code === "BSS-00000000") {
        const result = response.data.data;
        let dsLoi = [];
        let dsThueBao = [];
        result.map((item, index) => {
          if (item.TRANGTHAI) {
            dsLoi.push(item);
          } else {
            dsThueBao.push({ ...item, STT: ++index });
          }
        });
        this.ds_loi = dsLoi;
        this.ds_goi = dsThueBao;
        this.loading(false);
      } else {
        this.loading(false);
      }
    },
    TaiFileMau() {
      this.loading(true);
      try {
        const temp = [
          {
            USER_DANGKY: "84836665118",
            MA_GOI: "HEY",
            SODT_KH: "84935868896",
            NGAYGIO_DK: "08/08/2019 20:08:37",
            KENH_DANGKY: "SMS",
            GIAGOI: 50000,
          },
        ];
        var dsach = xlsx.utils.json_to_sheet(temp);
        var wb = xlsx.utils.book_new(); // make Workbook of Excel
        xlsx.utils.book_append_sheet(wb, dsach, "DS"); // sheetAName is name of Worksheet
        // export Excel file
        xlsx.writeFile(wb, "Bieumau_bangoicuoc.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    XuatExcel() {
      if (this.ds_loi <= 0) {
        this.$root.toastError("Không có dữ liệu lỗi để xuất file!");
        return;
      }
      this.loading(true);
      try {
        var dsach = xlsx.utils.json_to_sheet(this.ds_loi);
        var wb = xlsx.utils.book_new(); // make Workbook of Excel
        xlsx.utils.book_append_sheet(wb, dsach, "DS"); // sheetAName is name of Worksheet
        xlsx.writeFile(wb, "dsLoi.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
  },
  computed: {},
};
</script>
