<template src="./PayBillByFile.html"></template>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import breadcrumb from "@/components/breadcrumb";
import API from "./API";
import DanhSachLoiModal from "./DanhSachLoiModal.vue";
import XLSX from "xlsx";
export default {
  components: { breadcrumb, DanhSachLoiModal },
  data: function () {
    return {
      header: {
        title: "Cập nhật thanh toán hoá đơn theo file",
      },
      list: [
          {name: 'Lập hợp đồng', link: {name: 'Ui.cards'}},
          {
            name: 'Lắp đặt mới',
            link: {name: 'Ui.buttons'}
          }
        ],
      dataSources: [],
      dataSourcesError: [],
      danhsach_excel: [],
      file_full_path: "",
      file: null,
      btnGhiLai:false,
      btnKiemTra: false,
      columnsError: [],
      columnsError1: [
        {
          field: "STT",
          label: "STT",
          allowFilter: false,
        },
        {
          field: "MA_GD",
          label: "Mã giao dịch",
          allowFilter: false,
        },
        {
          field: "THONGTIN_PT",
          label: "Thông tin PT",
          allowFilter: false,
        },
        {
          field: "LOI",
          label: "Lỗi",
          allowFilter: false,
        },
      ],
      columnsError2: [
        {
          field: "stt",
          label: "STT",
          allowFilter: false,
        },
        {
          field: "ma_gd",
          label: "Mã giao dịch",
          allowFilter: false,
        },
        {
          field: "loi",
          label: "Lỗi",
          allowFilter: false,
        },
      ],
    };
  },
  created() {},
  methods: {
    chooseFile() {
      this.dataSources = [];
      this.dataSourcesError = [];
      this.danhsach_excel = [];
      document.getElementById("file").click();
    },
    onChangeFile(event) {
      this.file_full_path = event.target.value;
      this.file = event.target.files[0];
      this.readFileExcel();
    },
    readFileExcel() {
      this.dataSources = [];
      this.dataSourcesError = [];
      this.danhsach_excel = [];
      let columns;
      try {
        this.loading(true);
        if (this.checkfile()) {
          const reader = new FileReader();
          reader.onload = (e) => {
            /* Parse data */
            const bstr = e.target.result;
            const wb = XLSX.read(bstr, { type: "binary" });
            /* Get first worksheet */
            const wsname = wb.SheetNames[0];
            const ws = wb.Sheets[wsname];
            /* Convert array of arrays */
            const data = XLSX.utils.sheet_to_json(ws, { header: 1 });
            columns = data[0];
            if (this.kiemTraDuLieu(columns)) {
              for (var i = 1; i < data.length; i++) {
                this.danhsach_excel.push({
                  MA_GD: data[i][0],
                  THONGTIN_PT: data[i][1],
                });
              }
              //check lỗi null
              this.danhsach_excel.forEach((item) => {
                if (
                  item.MA_GD == undefined ||
                  item.MA_GD == null ||
                  item.MA_GD.toString().trim() == ""
                ) {
                  this.dataSourcesError.push({
                    MA_GD: "",
                    THONGTIN_PT:
                      item.THONGTIN_PT == undefined ? "" : item.THONGTIN_PT,
                    LOI: "Mã giao dịch null",
                  });
                } else if (
                  item.THONGTIN_PT == undefined ||
                  item.THONGTIN_PT == null ||
                  item.THONGTIN_PT.toString().trim() == ""
                ) {
                  this.dataSourcesError.push({
                    MA_GD: item.MA_GD == undefined ? "" : item.MA_GD,
                    THONGTIN_PT: "",
                    LOI: "Thông tin PT  null",
                  });
                }
              });
              //showModal lỗi null
              if (this.dataSourcesError.length > 0) {
                this.columnsError = this.columnsError1;
                this.dataSourcesError.forEach((item, index) => {
                  item.STT = index + 1;
                });
                this.$refs.danhsachloi.showModal();
                return;
              }

              //check lỗi trùng
              //let ds_trung=this.findDuplicates(this.danhsach_excel)
              let ds_trung = this.findDuplicates(this.danhsach_excel);
              console.log("ds_trung", ds_trung);
              if (ds_trung.length > 0) {
                this.columnsError = this.columnsError1;
                ds_trung.forEach((item, index) => {
                  this.dataSourcesError.push({
                    STT: index + 1,
                    MA_GD: item.MA_GD,
                    THONGTIN_PT: item.THONGTIN_PT,
                    LOI: "MA_GD bị lặp lại",
                  });
                });
                this.$refs.danhsachloi.showModal();
                return;
              }
              //FN_INS_KIEMTRA_MA_GD
              var danhsach = [];
              this.danhsach_excel.forEach((item) => {
                danhsach.push({
                  MA_GD: item.MA_GD,
                  GIATRI: item.THONGTIN_PT,
                });
              });
              this.insert_kiemtra(danhsach);
            }
          };
          reader.readAsBinaryString(this.file);
        }
      } catch (error) {
        this.$alert("Có lỗi xảy ra", "", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "error",
        });
      } finally {
        this.loading(false);
      }
    },
    checkfile: function () {
      if (this.file == null) {
        this.$alert("Chưa chọn file excel", "", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "error",
        });
        return false;
      }
      if (
        this.file.type !=
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      ) {
        this.$alert("File upload không đúng định dạng", "", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "error",
        });
        return false;
      }
      return true;
    },
    kiemTraDuLieu: function (columns) {
      if (columns == null) {
        this.$alert("File có cấu trúc không hợp lệ!", "", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "error",
        });
        return false;
      }
      if (
        !(
          columns[0].toUpperCase() == "MA_GD" &&
          columns[1].toUpperCase() == "THONGTIN_PT"
        )
      ) {
        this.$alert("File có cấu trúc không hợp lệ!", "", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "error",
        });
        return false;
      }
      return true;
    },
    findDuplicates(array) {
      var result = [];
      array.forEach((item, index) => {
        if (
          array.findIndex(
            (x) => x.MA_GD.toString().trim() == item.MA_GD.toString().trim()
          ) != index
        ) {
          const index = result.findIndex(
            (r) => r.MA_GD.toString().trim() == item.MA_GD.toString().trim()
          );
          if (index == -1) {
            result.push(item);
          }
        }
      });
      return result;
    },
    async insert_kiemtra(data) {
      try {
        this.dataSources = [];
        this.dataSourcesError = [];
        let apiParams = {
          vphanvung_id: this.$root.token.getPhanVungID(),
          nguoidung_id: this.$root.token.getNguoiDungID(),
          p_kieu_id: 1,
          p_ds_giatri: JSON.stringify(data),
        };
        this.loading(true);
        let response = await API.fn_insert_kiemtra_ma_gd_tmp(
          this.axios,
          apiParams
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          console.log(response.data.data.results);
        }
        let response_loi = await API.lay_ds_ma_gd_loi(this.axios, {
          vphanvung_id: this.$root.token.getPhanVungID(),
          nguoidung_id: this.$root.token.getNguoiDungID(),
          vkieu_id: "1",
        });
        if (response_loi.data.data.rs1.length > 0) {
          this.columnsError = this.columnsError2;
          response_loi.data.data.rs1.forEach((item, index) => {
            this.dataSourcesError.push({
              stt: index + 1,
              ma_gd: item.ma_gd,
              loi: item.loi,
            });
          });
          this.btnKiemTra = false;
            this.btnGhiLai=false;
          this.$refs.danhsachloi.showModal();
        } else {
          let response_khongloi = await API.lay_ds_ma_gd_khong_loi(this.axios, {
            vphanvung_id: this.$root.token.getPhanVungID(),
            nguoidung_id: this.$root.token.getNguoiDungID(),
            vkieu_id: "1",
          });
          if (response_khongloi.data.data.rs1.length > 0) {
            response_khongloi.data.data.rs1.forEach((item) => {
              this.dataSources.push({
                stt: item.stt,
                ma_gd: item.ma_gd,
                thongtin_pt: item.thongtin_pt,
                tongtien: item.tongtien,
              });
            });
            this.btnKiemTra = true;
            this.btnGhiLai=true;
          }
        }
      } catch (e) {
        this.$alert("Đã có lỗi xảy ra" + e.toString(), "Lỗi", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "error",
        });
      } finally {
        this.loading(false);
      }
    },
    tsbtnKiemTra_Click() {
      this.loading(true);
      API.fn_kiemtra_gd_da_tt(this.axios, {
        vphanvung_id: this.$root.token.getPhanVungID(),
        nguoidung_id: this.$root.token.getNguoiDungID(),
      })
        .then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            this.$toast.success(response.data.data);
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    tsbtnGhiLai_Click() {
      if (this.dataSources.length > 0) {
        this.loading(true);
        API.thanhtoan_trasau_theo_file(this.axios, {
          vphanvung_id: this.$root.token.getPhanVungID(),
          nguoidung_id: this.$root.token.getNguoiDungID(),
          vnhanvien_id: this.$root.token.getNhanVienID(),
          vdonvi_id: this.$root.token.getDonViID(),
          vnguoi_cn: this.$root.token.getUserName(),
          vmay_cn: this.$root.token.getUserName(),
          vkieu_id: "1",
        })
          .then((response) => {
            console.log(response)
            if (
              response.data.message==="Success" &&
              response.data.error_code === "BSS-00000000"
            ) {
              this.$toast.success("Cập nhật dữ liệu lên viễn thông tỉnh thành công!");
              this.btnGhiLai=false;
              this.btnKiemTra=true;
            }else{
              this.$toast.error("Lỗi: "+response.data.data);
            }
          })
          .catch((error) => {
            console.log(error);
          })
          .finally(() => {
            this.loading(false);
          });
      }else{
        this.$toast.error("Không có danh sách hoá đơn cần thanh toán!")
      }
    },
  },
};
</script>