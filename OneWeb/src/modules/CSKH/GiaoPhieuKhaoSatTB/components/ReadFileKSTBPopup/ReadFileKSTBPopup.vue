<template src="./ReadFileKSTBPopup.html"></template>

<script>
import API from "../../../api/GiaoNhanPhieuKhaoSatTB";
import xlsx from "xlsx";
import ExportExcel from "../../utils/ExportExcel.js";
export default {
  data() {
    return {
      target: ".main-wrapper",
      animationSettings: { effect: "Zoom" },
      linlblThongBao: "Danh sách thuê bao",
      columnDSTB: [
        {
          fieldName: "STT",
          headerText: "STT",
          width: 40,
        },
        {
          fieldName: "MA_TB",
          headerText: "Mã TB",
          allowFiltering: true,
          allowSorting: false,
          cellCssClass: "w20 center",
          width: 130,
        },
        {
          fieldName: "TRANGTHAI",
          headerText: "Trạng thái",
          allowFiltering: true,
          allowSorting: false,
          cellCssClass: "w20 center",
        },
      ],
      dtThueBao: [],
      files: [],
      txtFileName: "",
      xlsData: [],
      tabDanhSach: false,
      ds_load: [],
      kyhoadon: "",
      trangthai: 1,
      chapNhan: false,
      dichvuvt_id: 0,
      loaitb_id: "",
      chieukhoa: 1,
      khoamo: 1,
      thongbao: "",
      vhinhthucks_id: "",
      chapNhan: false,
      dsThueBaoLoi:[]
    };
  },
  methods: {
    reset() {
      this.dtThueBao = [];
      this.dsThueBaoLoi = [];
      this.txtFileName = "";
      this.xlsData = [];
      this.tabDanhSach = false;
      this.trangthai = 1;
      this.chapNhan = false;
      this.dichvuvt_id = 0;
      this.loaitb_id = "";
      this.chieukhoa = 1;
      this.khoamo = 1;
      this.thongbao = "";
    },
    async openDialog() {
      this.$refs.dlgReadFileKSTB.show();
    },
    dialogOpened() {},
    async onChangeFile(e) {
      console.log(e);
      this.tabDanhSach = false
      this.dsThueBaoLoi = []
      this.files = e.target.files;
      this.txtFileName = e.target.files[0].name;
      if (!this.txtFileName)
        return this.$toast.error("Chưa có thông tin về đường dẫn");
      await this.importExcel_ver2();
    },
    importExcel() {
      this.xlsData = [];
      const files = this.files;
      console.log(files);
      if (!files.length) {
        this.trangthai = 1;
        return this.$toast.error("Chưa có thông tin file");
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        this.$root.toastError("File không đúng định dạng : xls, xlsx");
        return;
      }
      const fileReader = new FileReader();
      fileReader.onload = async (ev) => {
        this.loading(true);
        try {
          this.dtThueBao = [];
          const data = ev.target.result;
          const XLSX = xlsx;
          const workbook = XLSX.read(data, {
            type: "binary",
          });
          const wsname = workbook.SheetNames[0];
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
          const excellist = [];
          let dsDuplicateMaTB = [];
          // Edit data
          for (var i = 0; i < ws.length; i++) {
            excellist.push({ STT: i + 1, ...ws[i] });
          }
          let properties = Object.getOwnPropertyNames(excellist[0]).filter(
            (e) => !e.includes("__rowNum__") && !e.includes("STT")
          );
          console.log("excellist", excellist);
          console.log("properties", properties);
          if (properties.length != 1 && properties.length != 3) {
            this.trangthai = 1;
            return this.$toast.error("Số lượng cột phải bằng 1 hoặc 3");
          }
          if (!properties.includes("MA_TB")) {
            return this.$root.toastError(
              "Tên cột thứ 1 trong file không đúng định dạng : MA_TB"
            );
          }
          if (properties.length > 1) {
            if (!properties.includes("TEN_TB")) {
              return this.$root.toastError(
                "Tên cột thứ 2 trong file không đúng định dạng : TEN_TB"
              );
            }
            if (!properties.includes("DIACHI")) {
              return this.$root.toastError(
                "Tên cột thứ 3 trong file không đúng định dạng : DIACHI"
              );
            }
          }
          let dsErr = [];
          excellist.forEach((element, index) => {
            if (!element.MA_TB)
              dsErr.push({
                STT: index + 1,
                MA_TB: element.MA_TB,
                TRANGTHAI: !element.MA_TB ? "Mã thuê bao null" : "",
              });
          });

          if (dsErr.length > 0) {
            this.trangthai = 1;
            this.dsThueBaoLoi = dsErr;
            this.tabDanhSach = true
            return;
          }
          let dsCheckTrung = excellist.map((x) => x.MA_TB);
          excellist.forEach((element) => {
            if (
              dsCheckTrung.indexOf(element.MA_TB) !=
                dsCheckTrung.lastIndexOf(element.MA_TB) &&
              !dsDuplicateMaTB.includes(element.MA_TB)
            )
              dsDuplicateMaTB.push(element);
          });
          dsDuplicateMaTB.forEach((element, index) => {
            dsErr.push({
              STT: index + 1,
              MA_TB: element.MA_TB,
              TRANGTHAI: "MA_TB bị lặp lại",
            });
          });

          if (dsErr.length > 0) {
            this.trangthai = 1;
            this.dsThueBaoLoi = dsErr;
            this.tabDanhSach = true
            return;
          }

          // this.dtThueBao = excellist;

          if (excellist.length > 0) {
            let tmp = {
              first: 1,
              hinhThucKSId: this.vhinhthucks_id,
              dsMaTB: excellist.map((e) => e.MA_TB).join(),
            };
            let response = await API.postUploadDS(this.axios, tmp);
            if (response.data.error_code === "BSS-00000000") {
              let dsLoad = response.data.data;
              if (dsLoad.length <= 0) {
                this.trangthai = 1;
                this.$toast.error("Không tìm thấy dữ liệu");
                return;
              }
            //   dsLoad.forEach((e, index) => {
            //     e.STT = index + 1;
            //   });
            //   if (dsLoad[0]["TRANGTHAI_ID"] == 1) {
            //         this.linlblThongBao = "Danh sách thuê bao lỗi";
            //         this.trangthai = 1;
            //         this.dtThueBao = dsLoad;
            //     } else {
            //         this.linlblThongBao = "Danh sách thuê bao khảo sát";
            //         this.dtThueBao = dsLoad;
            //         this.trangthai = 0;
            //     }
                this.dsThueBaoLoi = dsLoad.filter(item=>item.TRANGTHAI_ID == 1).map((item,index)=>({...item,STT:++index}))
                this.dtThueBao = dsLoad.filter(item=>item.TRANGTHAI_ID == 0).map((item,index)=>({...item,STT:++index}))
                if(this.dsThueBaoLoi.length > 0){
                    this.trangthai = 1
                    this.tabDanhSach = true
                } else {
                    this.trangthai = 0
                    this.tabDanhSach = false
                }
            }
          }
        } catch (e) {
          console.log(e);
          this.$root.toastError("Có lỗi :" + e.data.error_code);
        } finally {
          this.loading(false);
        }
      };

      fileReader.readAsBinaryString(files[0]);
    },
    importExcel_ver2() {
      this.xlsData = [];
      const files = this.files;
      console.log(files);
      if (!files.length) {
        this.trangthai = 1;
        return this.$toast.error("Chưa có thông tin file");
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        this.$root.toastError("File không đúng định dạng : xls, xlsx");
        return;
      }
      const fileReader = new FileReader();
      fileReader.onload = async (ev) => {
        this.loading(true);
        try {
          this.dtThueBao = [];
          const data = ev.target.result;
          const XLSX = xlsx;
          const workbook = XLSX.read(data, {
            type: "binary",
          });
          const wsname = workbook.SheetNames[0];
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
          const excellist = [];
          let dsDuplicateMaTB = [];
          // Edit data
          for (var i = 0; i < ws.length; i++) {
            excellist.push({ STT: i + 1, ...ws[i] });
          }
          let properties = Object.getOwnPropertyNames(excellist[0]).filter(
            (e) => !e.includes("__rowNum__") && !e.includes("STT")
          );
          console.log("excellist", excellist);
          console.log("properties", properties);
          if (properties.length != 1 && properties.length != 2) {
            this.trangthai = 1;
            return this.$toast.error("Số lượng cột phải bằng 1 hoặc 2");
          }
          if (!properties.includes("MA_TB")) {
            return this.$root.toastError(
              "Tên cột thứ 1 trong file không đúng định dạng : MA_TB"
            );
          }
          if (properties.length > 1) {
            if (!properties.includes("MO_TA")) {
              return this.$root.toastError(
                "Tên cột thứ 2 trong file không đúng định dạng : MO_TA"
              );
            }
          }
          let dsErr = [];
          excellist.forEach((element, index) => {
            if (!element.MA_TB)
              dsErr.push({
                STT: index + 1,
                MA_TB: element.MA_TB,
                TRANGTHAI: !element.MA_TB ? "Mã thuê bao null" : "",
              });
          });

          if (dsErr.length > 0) {
            this.trangthai = 1;
            this.dsThueBaoLoi = dsErr;
            this.tabDanhSach = true
            return;
          }
          let dsCheckTrung = excellist.map((x) => x.MA_TB);
          excellist.forEach((element) => {
            if (
              dsCheckTrung.indexOf(element.MA_TB) !=
                dsCheckTrung.lastIndexOf(element.MA_TB) &&
              !dsDuplicateMaTB.includes(element.MA_TB)
            )
              dsDuplicateMaTB.push(element);
          });
          dsDuplicateMaTB.forEach((element, index) => {
            dsErr.push({
              STT: index + 1,
              MA_TB: element.MA_TB,
              TRANGTHAI: "MA_TB bị lặp lại",
            });
          });

          if (dsErr.length > 0) {
            this.trangthai = 1;
            this.dsThueBaoLoi = dsErr;
            this.tabDanhSach = true
            return;
          }

          // this.dtThueBao = excellist;

          if (excellist.length > 0) {
            let tmp = {
              hinhThucKSId: this.vhinhthucks_id,
              dsThueBao: excellist.map((e) => ({MA_TB:e.MA_TB,MO_TA:e.MO_TA})),
            };
            let response = await API.postUploadDSVer2(this.axios, tmp);
            if (response.data.error_code === "BSS-00000000") {
              let dsLoad = response.data.data;
              if (dsLoad.length <= 0) {
                this.trangthai = 1;
                this.$toast.error("Không tìm thấy dữ liệu");
                return;
              }
            //   dsLoad.forEach((e, index) => {
            //     e.STT = index + 1;
            //   });
            //   if (dsLoad[0]["TRANGTHAI_ID"] == 1) {
            //         this.linlblThongBao = "Danh sách thuê bao lỗi";
            //         this.trangthai = 1;
            //         this.dtThueBao = dsLoad;
            //     } else {
            //         this.linlblThongBao = "Danh sách thuê bao khảo sát";
            //         this.dtThueBao = dsLoad;
            //         this.trangthai = 0;
            //     }
                this.dsThueBaoLoi = dsLoad.filter(item=>item.TRANGTHAI_ID == 1).map((item,index)=>({...item,STT:++index}))
                this.dtThueBao = dsLoad.filter(item=>item.TRANGTHAI_ID == 0).map((item,index)=>({...item,STT:++index}))
                if(this.dsThueBaoLoi.length > 0){
                    this.trangthai = 1
                    this.tabDanhSach = true
                } else {
                    this.trangthai = 0
                    this.tabDanhSach = false
                }
            }
          }
        } catch (e) {
          console.log(e);
          this.$root.toastError("Có lỗi :" + e.data.error);
        } finally {
          this.loading(false);
        }
      };

      fileReader.readAsBinaryString(files[0]);
    },
    async tsbtnXem() {
      let value = await this.$bvModal.msgBoxConfirm("Bạn muốn mở file ?", {
        title: "Thông báo",
        size: "lg",
        buttonSize: "lg",
        okVariant: "primary",
        okTitle: "Đồng ý",
        cancelTitle: "Hủy bỏ",
        hideHeaderClose: false,
        centered: true,
        "modal-class": ["f18", "text-center"],
      });
      if (value) {
        this.importExcel_ver2();
      }
    },
    tsbtnChapNhan() {
      if (this.dtThueBao.length <= 0) {
        this.$toast.error("Chưa có dữ liệu!");
        return;
      }
      if (this.trangthai == 1 || this.dsThueBaoLoi.length > 0) {
        this.$toast.error("Dữ liệu đang có lỗi. Không được phép cập nhật!");
        return;
      }
      this.chapNhan = true;
      this.dialogClose();
    },
    tsbtnXuatExcel() {
      if (this.dtThueBao.length <= 0) {
        this.$toast.error("Không có dữ liệu để xuất Excel !");
        return;
      }
      ExportExcel(this.columnDSTB, this.dtThueBao, "DSThueBao", "export");
    },
    dialogClose() {
      this.$emit("closed", { CHAP_NHAN: this.chapNhan });
      this.$refs.dlgReadFileKSTB.hide();
    },
  },
};
</script>

<style></style>
