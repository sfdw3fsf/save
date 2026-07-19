<template src="./InLeXuLyNo.html"></template>
<style src="./InLeXuLyNo.scss" scoped></style>
<script>
import { mapActions, mapState } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import xlsx from "xlsx";
import KyCuoc from "../components/KyCuoc.vue";
import moment from "moment";
import PopupDocLoiXLN from "./popup-loi.vue";
import API from "../api/InLeXuLyNo";
import {previewPrint} from "../../../utils/util";
import API2 from "../api/InThongBaoNo";

export default {
  components: { breadcrumb, appKyCuoc: KyCuoc, PopupDocLoiXLN },
  name: "InLeXuLyNo",
  mounted() {
    this.$refs.maTT.focus();
  },
  computed: {
    ...mapState("inlexulyno", ["danhsachKyHoaDon", "danhsachXuLyNo"]),
  },
  data() {
    return {
      header: {
        title: "IN LẺ XỬ LÝ NỢ",
        list: [
          { name: "Tra cứu", link: { name: "Ui.cards" } },
          {
            name: "Xử lý nợ",
            link: { name: "Ui.buttons" },
          },
          {
            name: "In lẻ xử lý nợ",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      maTT: "",
      kyCuoc: "",
      fromKyHD: "",
      toKyHD: "",
      fields: [
        {
          fieldName: "MA_XLN",
          headerText: "Mã XLN",
          allowFiltering: true,
        },
        {
          fieldName: "MA_KH",
          headerText: "Mã KH",
          allowFiltering: true,
        },
        {
          fieldName: "MATB_DD",
          headerText: "Mã đại diện",
          allowFiltering: true,
        },
        {
          fieldName: "TEN_KH",
          headerText: "Tên khách hàng",
          allowFiltering: true,
        },
        {
          fieldName: "DIACHI_KH",
          headerText: "Địa chỉ khách hàng",
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
          fieldName: "SO_CV",
          headerText: "Số công văn",
          allowFiltering: true,
          cellCssClass: "text-right",
        },
        {
          fieldName: "LANTAO_ID",
          headerText: "Lần XLN",
          allowFiltering: true,
          cellCssClass: "text-right",
        },
        {
          fieldName: "TEN_TRANGTHAI",
          headerText: "Trạng thái XLN",
          allowFiltering: true,
        },
      ],
      enabledKyHD: false,
      mauInKim: false,
      mauIn: "",
      tenKH: "",
      diaChi: "",
      dsLoi: [],
      dsMauIn: [],
    };
  },
  methods: {
    ...mapActions("inlexulyno", [
      "getDanhSachKyHoaDon",
      "getDanhSachXuLyNo",
      "clearData",
      "kiemtraMaXLN",
      "getDanhSachXuLyNoTheoFile",
    ]),
    handleKyHoaDon(e) {
      this.kyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no;
    },
    async onChangeFile(e) {
      this.maTT = "";
      const files = e.target.files;
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
          let dsmaTT = [];
          // Edit data
          for (var i = 0; i < ws.length; i++) {
            excellist.push(ws[i]);
            dsmaTT.push(ws[i].MA_TT);
          }

          var propCount = 0;
          if (excellist && excellist.length > 0) {
            for (var prop in excellist[0]) {
              if (excellist[0].hasOwnProperty(prop)) propCount++;
            }
          }
          if (propCount == 0) {
            this.dsLoi = [{ MA_XLN: "", TRANGTHAI: "Không tìm thấy Mã TT" }];
            this.$bvModal.show("popup-loixln");
            return;
          }

          if (propCount != 1) {
            this.$root.toastError("File có 1 cột: MA_TT!");
            return;
          }

          if (!excellist[0].hasOwnProperty("MA_TT")) {
            this.$root.toastError("Tên cột không đúng!");
            return;
          }
          const result = await this.kiemTraFile(dsmaTT);
          if (result) {
            let result = dsmaTT.toString();
            this.maTT = result;
          }
          this.$refs.maTT.focus();
        } catch (e) {
          return alert("Read failure!" + e);
        }
      };
      fileReader.readAsBinaryString(files[0]);
      var input = document.getElementById("upload");
    },
    async kiemTraFile(ds) {
      const dsMa = ds.map((item) => ({ maXLN: item }));
      const data = {
        file: dsMa,
        kyCuoc: this.kyCuoc,
        kieuXL: 1, // mã Tb
      };
      this.loading(true);
      const response = await this.kiemtraMaXLN(data);
      if (response.data.error_code === "BSS-00000000") {
        if (response.data.data !== undefined && response.data.data.length > 0) {
          this.dsLoi = response.data.data;
          this.$bvModal.show("popup-loixln");
          this.loading(false);
          return false;
        } else {
          this.loading(false);
          return true;
        }
      } else {
        this.loading(false);
        // this.$root.toastError(response.message_detail);
        return false;
      }
    },
    async fetchMaTTByTB(dsMaTT) {
      const response = await API.getDsMaTT(this.axios, { dsMaTT: dsMaTT });
      if (response.data.error_code === "BSS-00000000") {
        return response.data.data;
      } else {
        return dsMaTT;
      }
    },
    selectedRowChanged(index, data) {
      if (data) {
        this.tenKH = data.TEN_KH;
        this.diaChi = data.DIACHI_KH;
      } else {
        this.tenKH = "";
        this.diaChi = "";
      }
    },
    InDS() {
      const dsSelected = this.$refs.danhSach.getSelectedRecords() || [];
      if (dsSelected <= 0) {
        this.$root.toastError("Chưa có dữ liệu để in!");
        return;
      }
      const dsMaXLN = dsSelected.map(item=>item.MA_XLN);
      const result = dsMaXLN.toString();
      this.inThongBaoNoLe(result);
    },
    async inThongBaoNoLe(dsMaXLN){
      this.loading(true);
      try {
        const baseUrl = process.env.API;
        await this.axios({
          url:
            `${baseUrl}/web-thuno/api/thu-no/xu-ly-no/xuat-file-le-xln`,
          method: "GET",
          responseType: "blob",
          params: {
              kyCuoc: this.kyCuoc,
              kieuXLId: 1, // thanh toán
              dsXLN: dsMaXLN,
              reportId: this.mauIn,
              inKim: this.mauInKim
          }
        }).then((response) => {
          previewPrint(response.data);
        });
      } catch (error) {
        // this.$root.toastError(error.statusText);
        this.$root.toastError("Báo cáo này chưa được thiết kế mẫu !");
      } finally {
        this.loading(false);
      }
    },
    // async LoadDS() {
    //   if (this.maTT.trim() === "") {
    //     this.$root.toastError("Hãy nhập Mã TT hoặc Mã TB để tìm kiếm!");
    //     return;
    //   }
    //   const dsmaTT = await this.fetchMaTTByTB(this.maTT.trim())
    //   const dsResult = dsmaTT.split(",");
    //   const result = await this.kiemTraFile(dsResult);
    //   if (result) {
    //     this.loading(true);
    //     const data = {
    //       kyCuoc: this.kyCuoc,
    //       kieuTaoDL: 1,
    //       dsMaXLN: dsmaTT,
    //     };
    //     const response = await this.getDanhSachXuLyNo(data);
    //     if (response.data.error_code === "BSS-00000000") {
    //       this.loading(false);
    //       if (
    //         response.data.data !== undefined &&
    //         response.data.data.length > 0
    //       ) {
    //         this.$refs.danhSach.setCurrentSelectedRow(0);
    //         this.diaChi = response.data.data[0].DIACHI_KH;
    //         this.tenKH = response.data.data[0].TEN_KH;
    //       } else {
    //         this.tenKH = "";
    //         this.diaChi = "";
    //         this.$root.toastError("Không tìm thấy dữ liệu thỏa mãn điều kiện!");
    //       }
    //     } else {
    //       this.loading(false);
    //       this.$root.toastError(response.message_detail);
    //     }
    //   }
    // },
    async LoadDS() {
      if (this.maTT.trim() === "") {
        this.$root.toastError("Hãy nhập Mã TT hoặc Mã TB để tìm kiếm!");
        return;
      }
      const dsmaTT = await this.fetchMaTTByTB(this.maTT.trim());
      const dsResult = dsmaTT.split(",");
      const result = await this.kiemTraFile(dsResult);
      if (result) {
        this.loading(true);
        const data = {
          kyCuoc: this.kyCuoc,
          kieuXLId: 1, // thanh toán
          dsXLN: dsmaTT,
        };
        const response = await this.getDanhSachXuLyNoTheoFile(data);
        if (response.data.error_code === "BSS-00000000") {
          this.loading(false);
          if (
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.diaChi = response.data.data[0].DIACHI_KH;
            this.tenKH = response.data.data[0].TEN_KH;
          } else {
            this.tenKH = "";
            this.diaChi = "";
            this.$root.toastError("Không tìm thấy dữ liệu thỏa mãn điều kiện!");
          }
        } else {
          this.loading(false);
          this.$root.toastError(response.message_detail);
        }
      }
    },
    XuatExcel() {
      if (this.danhsachXuLyNo.length <= 0) {
        this.$root.toastError("Chưa có danh sách để xuất Excel!");
        return;
      }
      this.loading(true);
      try {
        var dsach = xlsx.utils.json_to_sheet(this.danhsachXuLyNo);
        var wb = xlsx.utils.book_new(); // make Workbook of Excel
        xlsx.utils.book_append_sheet(wb, dsach, "DS"); // sheetAName is name of Worksheet
        xlsx.writeFile(wb, "mau.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    async getDanhSachMauIn() {
      const data = {
        loaiHD: 1,
        dsBaoCao: null,
        trangThaiXL: null, //3
        nhomBCId: 24, //báo cáo Xử lý nợ
        buocXLNId: 22, // In lẻ thông báo
      };
      const response = await API2.getDanhSachMauIn(this.axios, data);
      if (response.data.error_code === "BSS-00000000") {
        if (
          response.data.data !== undefined &&
          response.data.data.length > 0
        ) {
          this.dsMauIn = response.data.data;
          this.mauIn = response.data.data[0].BAOCAO_ID
        }
      } else {
        this.dsMauIn = [];
      }
    },
  },
  async created() {
    this.clearData();
    const res = await this.getDanhSachKyHoaDon();
    if (res.data.data !== undefined && res.data.data.length > 0) {
      this.fromKyHD = res.data.data[0].ID;
      this.toKyHD = res.data.data[0].ID;
    }
    this.getDanhSachMauIn();
  },
};
</script>
