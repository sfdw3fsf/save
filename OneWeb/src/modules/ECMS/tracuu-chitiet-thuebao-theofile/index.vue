<template src="./index.template.html"></template>
<script>
import downloadexcel from "vue-json-excel";
import XLSX from "xlsx";
import ModalXuatDuLieu from "./../TraCuuSplitterTheoPortOLT/ModalXuatDuLieu/index.vue";
export default {
  components: { downloadexcel, XLSX, ModalXuatDuLieu },
  data: function () {
    return {
      kqTimKiem: [],
      dsMa_tbao: [],
      dataExcel: [],
      dataTCExcel: [],
      json_name_tc: "dsChiTietThueBao",
      file: null,
      excelData: null,
      ok: false,
      filename: "",
      dsMatbao: [],
      isError: null,
      dsThuocTinh: [
        "STT",
        "MA_TB",
        "MA_LT",
        "DAUCUOI",
        "TRANGTHAI_TB",
        "TEN_TB",
        "SDT_LIENHE",
        "TEN_DVVT",
        "LOAIHINH_TB",
        "DONVI_QL",
        "DIACHI_LD",
        "SONE",
        "DSLAM",
        "PORT_OLT",
        "SPLITTER",
        "CAPGOC",
        "CAPNGON",
        "KETCUOI",
        "DIACHI_KC",
        "CAPTRUNGGIAN",
        "CULY",
        "TOCDO",
        "TEN_LOAIKH",
        "TEN_PLKH",
        "KHUVUC_TB",
        "NHANVIEN_QL",
      ],
      isDisableBtnXuatExcel: true,
      init: true,
    };
  },
  created: function () {},
  watch: {
    file: function (val) {
      if (val != null) this.isError = null;
      //  else
      //   this.isError=false
    },
  },
  methods: {
    getDsFilemau: async function () {
      this.$root.showLoading(true);
      try {
        var rs = await this.$root.context.get("/web-ecms/tracuu/thuebaotheofile/filemau");
        if (rs.data != null) {
          const data_tem = rs.data.MA_TB.split(",");
          data_tem.forEach((i) => {
            this.dataExcel.push({ MA_TB: i });
          });
          var ma_thuebao = XLSX.utils.json_to_sheet(this.dataExcel);
          var wb = XLSX.utils.book_new(); // make Workbook of Excel
          XLSX.utils.book_append_sheet(wb, ma_thuebao, "MA_TB"); // sheetAName is name of Worksheet
          // export Excel file
          XLSX.writeFile(wb, "ma_thue_bao_mau.xlsx");
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    tracuuThueBao: async function (ma_tbao) {
      this.$root.showLoading(true);
      try {
        let times = parseInt(this.dsMa_tbao.length / 100) + 1;
        let query = "";
        let dt = [];
        for (let i = 0; i < times; i++) {
          query = "";
          let len = this.dsMa_tbao.length < 100 ? this.dsMa_tbao.length : 100 * (i + 1);
          let start = 100 * i;
          for (let j = start; j < len; j++) {
            query += this.dsMa_tbao[j] + ",";
          }
          var rs = await this.$root.context.post("/web-ecms/tracuu/thuebaotheofile", {
            ma_tbao: query,
          });
          if (rs.data != null && rs.data.length > 0) {
            dt = dt.concat(rs.data);
          }
        }

        this.kqTimKiem = dt;
        if (this.kqTimKiem.length == 0) {
          this.$root.$toast.error("Không tìm thấy dữ liệu tra cứu!");
          this.dataTCExcel = [];
        } else {
          for (let i = 0; i < this.dsMa_tbao.length; i++) {
            let tbao = Object;
            tbao = { STT: i + 1, MA_TB: this.dsMa_tbao[i] };
            let thuebao = this.kqTimKiem.filter(
              (x) => x.MA_TB == this.dsMa_tbao[i] || x.MA_LT == this.dsMa_tbao[i]
            );
            if (thuebao.length == 0) {
              this.dataTCExcel.push(tbao);
            } else {
              thuebao[0].STT = i + 1;
              this.dataTCExcel.push(thuebao[0]);
            }
          }
          this.isDisableBtnXuatExcel = false;
        }
      } catch (error) {
        this.$root.$toast.error("Có lỗi xảy ra trong quá trình nạp danh sách!");
      } finally {
        this.$root.showLoading(false);
      }
    },
    tracuu: async function () {
      this.$root.showLoading(true);
      try {
        this.dsMa_tbao = [];
        this.kqTimKiem = [];
        this.dataTCExcel = [];
        if (this.checkfile()) {
          const reader = new FileReader();
          reader.onload = async (e) => {
            let matb = "";
            /* Parse data */
            const bstr = e.target.result;
            const wb = XLSX.read(bstr, { type: "binary" });
            /* Get first worksheet */
            const wsname = wb.SheetNames[0];
            const ws = wb.Sheets[wsname];
            /* Convert array of arrays */
            const data = XLSX.utils.sheet_to_json(ws, { header: 1 });
            if (data.length == 0) {
              this.$root.$toast.error();
              ("Tệp excel không có dữ liệu!");
              return;
            }
            let index = -1;
            let done = false;
            for (let y = 0; y < data[0].length; y++)
              if (data[0][y] == "MA_TB") {
                index = y;
                break;
              }
            if (index > -1) {
              let len = data.length > 500 ? 501 : data.length;
              let start = 1;
              for (let i = start; i < len; i++) {
                let item = (" " + data[i][index]).replace(" ", "");
                this.dsMa_tbao.push(item.trim());
                matb += item + ",";
              }
              let ckATTT = await this.kiemTraNoiDung();
              if (ckATTT) {
                await this.tracuuThueBao(matb);
              }
            } else {
              this.$root.$toast.error(
                "File không đúng định dạng, trong file đọc vào phải có cột MA_TB là Account của thuê bao cần tra cứu"
              );
              return;
            }
          };

          reader.readAsBinaryString(this.file);
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    kiemTraNoiDung: async function () {
      let rs = true;
      try {
        if (this.dsMa_tbao.length > 0) {
          let rq = await this.$root.context.post("/web-ecms/tracuu/kiemTraNoiDung", {
            content: this.dsMa_tbao,
          });
          if (!(rq.data == null || rq.data == undefined)) {
            if (rq.data.length > 0) {
              rs = false;
              let msg = "";
              for (let i = 0; i < rq.data.length; i++) {
                msg += "\r\n" + rq.data[i].noiDung + ": " + rq.data[i].tuKhoa;
              }
              this.$root.$toast.error("Các nội dung không hợp lệ: " + msg);
            }
          }
        }
      } catch (error) {
        this.$root.$toast.error(
          "Có lỗi xảy ra trong quá trình kiểm tra nội dung: " + error
        );
      }
      return rs;
    },
    checkfile: function () {
      if (this.file == null) {
        this.$toast.error("Hãy chọn file trước khi tra cứu!");
        this.isError = false;
        return false;
      }
      if (
        this.file.type !=
          "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" &&
        this.file.type != "application/vnd.ms-excel"
      ) {
        this.$toast.error("File không phải định dạng xlsx hoặc xls!");
        return false;
      }
      this.isError = null;
      return true;
    },
    checkDownloadExcel: function () {
      if (this.kqTimKiem == null || this.kqTimKiem.length == 0) {
        this.$root.$toast.error("Chưa có dữ liệu.");
        return;
      } else this.$bvModal.show("modal-xuat-dulieu");
    },
    bindDataExcel: async function (args) {
      this.dsMa_tbao = [];
      if (!(args == null || args.length == 0)) {
        for (let i = 0; i < args.length; i++) {
          const arg = args[i];
          this.dsMa_tbao.push(arg.MA_TB);
        }
        let ckATTT = await this.kiemTraNoiDung();
        if (ckATTT) {
          if (this.dsMa_tbao.length > 0) await this.tracuuThueBao("");
        }
      }
    },
  },
};
</script>
<style>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
