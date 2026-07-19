<template src="./index.template.html"></template>
<script>
import {
  Edit,
  Sort,
  Filter,
  ForeignKey,
  Resize,
  Freeze,
} from "@syncfusion/ej2-vue-grids";
import { L10n } from "@syncfusion/ej2-base";
import ModalThongTinBienDongCapKC from "./Modal/ModalThongTinBienDongCapKC.vue";

L10n.load({
  "vi-VN": {
    grid: {
      EmptyRecord: "Không có bản ghi nào để hiển thị",
    },
  },
});

const LoaiDoiTuong_NV = {
  CST_EXCHANGE: 9, //Tổng đài
  CST_BLOCK: 10, //Kết cuối
  CST_CABLE: 11, //Cáp
  CST_MANHOLE: 13, //Bể
  CST_PIPESEGMENT: 12, //Cống
  CST_PILLAR: 14, //Cột
};

export default {
  components: {
    ModalThongTinBienDongCapKC,
  },
  provide: {
    grid: [Edit, Sort, Filter, ForeignKey, Resize, Freeze],
  },
  name: "DanhSachCapKetCuoi",
  data() {
    return {
      dsLoaiDoiTuong: [
        { DOITUONG_ID: LoaiDoiTuong_NV.CST_BLOCK, TEN_DT: "Kết cuối" },
        { DOITUONG_ID: LoaiDoiTuong_NV.CST_EXCHANGE, TEN_DT: "Tổng đài" },
        { DOITUONG_ID: LoaiDoiTuong_NV.CST_CABLE, TEN_DT: "Cáp" },
        { DOITUONG_ID: LoaiDoiTuong_NV.CST_MANHOLE, TEN_DT: "Bể cáp" },
        { DOITUONG_ID: LoaiDoiTuong_NV.CST_PIPESEGMENT, TEN_DT: "Cống cáp" },
        { DOITUONG_ID: LoaiDoiTuong_NV.CST_PILLAR, TEN_DT: "Cột cáp" },
      ],
      loaiDoiTuongId: LoaiDoiTuong_NV.CST_BLOCK,
      dsTuyen: [],
      tuyenId: null,
      chkTuyenChecked: true,
      dsToanTu: ["=", ">", "<"],
      toanTu: "=",
      chkDungLuongChecked: true,
      dungLuong: 1,
      dsQuan: [],
      quanId: null,
      chkQuanChecked: false,
      dsPhuong: [],
      phuongId: null,
      chkPhuongChecked: false,
      dsPho: [],
      phoId: null,
      chkPhoChecked: false,
      soNha: null,
      kieuTimKiem: 1,
      tenDoiTuong: null,
      kyHieu: null,
      diaChi: null,
      dsKetCuoi: [],
      dsCotCap: [],
      dsCap: [],
      dsDoanCong: [],
      dsBeCap: [],
      dsTongDai: [],
      dataSource: [],
      searchData: null,
      soLuongBanGhi: 0,
      selectedRowIndex: 0,
      bdItem: {},
    };
  },
  computed: {
    xuatExcel: function () {
      return (
        parseInt(this.loaiDoiTuongId) == LoaiDoiTuong_NV.CST_BLOCK ||
        parseInt(this.loaiDoiTuongId) == LoaiDoiTuong_NV.CST_CABLE
      );
    },
  },
  methods: {
    async layDmTuyen() {
      try {
        let response = await this.$root.context.get(
          `/web-cabman/danhmuc/ds-cap-ketcuoi/dm-tuyen?doituong_id=${this.loaiDoiTuongId}`
        );

        if (response.error_code === "BSS-00000000" && response.data != null) {
          this.dsTuyen = response.data.map((item) => {
            if (this.loaiDoiTuongId === LoaiDoiTuong_NV.CST_PILLAR) {
              return { id: item.TUYENCOT_ID, ma: item.TUYEN_COT };
            }

            if (
              [LoaiDoiTuong_NV.CST_PIPESEGMENT, LoaiDoiTuong_NV.CST_MANHOLE].indexOf(
                this.loaiDoiTuongId
              ) >= 0
            ) {
              return { id: item.TUYENCB_ID, ma: item.TUYEN_CB };
            }

            return { id: item.TUYENCAP_ID, ma: item.TUYEN_CAP };
          });

          if (this.dsTuyen.length > 0) {
            this.tuyenId = this.dsTuyen[0].id;
          }
        } else {
          this.dsTuyen = [];
        }
      } catch (err) {
        this.dsTuyen = [];
      }
    },
    async layDmQuan() {
      try {
        let response = await this.$root.context.get("/web-cabman/danhmuc/diachi-quan");

        if (response.error_code === "BSS-00000000" && response.data != null) {
          response.data.unshift({ QUAN_ID: -1, TEN_QUAN: "Không chọn" });
          this.dsQuan = response.data;
          this.quanId = -1;
        } else {
          this.dsQuan = [];
        }
      } catch (err) {
        this.dsQuan = [];
      }
    },
    async layDmPhuong() {
      if (parseInt(this.quanId) == -1) {
        this.dsPhuong = [{ PHUONG_ID: -1, TEN_PHUONG: "Không xác định" }];
        this.phuongId = -1;
        this.dsPho = [];
        return;
      }

      try {
        let response = await this.$root.context.get(
          `/web-cabman/danhmuc/diachi-phuong/${this.quanId}`
        );

        if (response.error_code === "BSS-00000000" && response.data != null) {
          this.dsPhuong = response.data;
        } else {
          this.dsPhuong = [];
        }
      } catch (err) {
        this.dsPhuong = [];
      }
    },
    async layDmPho() {
      if (!this.phuongId) {
        return;
      }

      try {
        let dsNhomPho = [1 /*Đường phố*/, 2 /*Ấp*/, 3 /*Khu*/];
        let response = await this.$root.context.post("/web-cabman/danhmuc/diachi-pho", {
          phuong_id: this.phuongId,
          ds_nhompho: dsNhomPho.join(),
        });

        if (response.error_code === "BSS-00000000" && response.data != null) {
          this.dsPho = response.data;
        } else {
          this.dsPho = [];
        }
      } catch (err) {
        this.dsPho = [];
      }
    },
    async onClickButtonTimKiem() {
      let message = [];

      if (this.chkTuyenChecked && !this.tuyenId) {
        message.push("Bạn chưa chọn tuyến");
      }

      if (this.chkPhoChecked && !this.phoId) {
        message.push("Bạn chưa chọn phố");
      }

      if (message.length > 0) {
        this.$root.toastError(message.join("\n"));
        return;
      }

      this.searchData = {
        dau: this.chkDungLuongChecked ? this.toanTu : "",
        dung_luong: this.chkDungLuongChecked && this.dungLuong ? this.dungLuong : "",
        dia_chi: !this.diaChi ? "" : this.diaChi.trim(),
        doi_tuong: this.loaiDoiTuongId,
        kieu: this.kieuTimKiem,
        ky_hieu: !this.kyHieu ? "" : this.kyHieu.trim(),
        pho_id: this.chkPhoChecked && this.phoId ? this.phoId : "",
        phuong_id:
          this.chkPhuongChecked && parseInt(this.phuongId) != -1 ? this.phuongId : "",
        quan_id: this.chkQuanChecked && parseInt(this.quanId) != -1 ? this.quanId : "",
        so_nha: !this.soNha ? "" : this.soNha.trim(),
        ten_dt: !this.tenDoiTuong ? "" : this.tenDoiTuong.trim(),
        tuyen_id: this.chkTuyenChecked && this.tuyenId ? this.tuyenId : "",
      };
      this.soLuongBanGhi = await this.laySoLuongBanGhi();

      if (this.soLuongBanGhi == 0) {
        this.$toast.error("Không tìm thấy thông tin đối tượng đã chọn.");
      }
    },
    onSelectedRowChanged(data) {
      if (!data) {
        return;
      }

      switch (parseInt(this.loaiDoiTuongId)) {
        case LoaiDoiTuong_NV.CST_BLOCK:
          this.tenDoiTuong = data.TEN_KC;
          this.kyHieu = data.KYHIEU;
          this.dungLuong = data.TONGSO_DD;
          this.diaChi = data.DIACHI;
          break;
        case LoaiDoiTuong_NV.CST_PILLAR:
          this.tenDoiTuong = data.TENCOTCAP;
          this.kyHieu = data.SOHIEUCOT;
          this.diaChi = data.DIACHI;
          break;
        case LoaiDoiTuong_NV.CST_CABLE:
          this.tenDoiTuong = data.TEN_CAP;
          this.kyHieu = data.KYHIEU;
          this.diaChi = "";
          break;
        case LoaiDoiTuong_NV.CST_PIPESEGMENT:
          this.tenDoiTuong = data.TEN_DC;
          this.kyHieu = data.KYHIEU;
          this.diaChi = "";
          break;
        case LoaiDoiTuong_NV.CST_MANHOLE:
          this.tenDoiTuong = data.TEN_BC;
          this.kyHieu = data.KYHIEU;
          this.diaChi = data.DIACHI;
          break;
        case LoaiDoiTuong_NV.CST_EXCHANGE:
          this.tenDoiTuong = data.TEN_DV;
          this.kyHieu = data.MA_DV;
          this.diaChi = data.DIACHI_DV;
          break;
      }
    },
    async onPageChange(args) {
      if (!this.searchData) {
        return;
      }

      this.searchData.page_no = args.pageIndex;
      this.searchData.page_size = args.pageSize;

      let dataSource = await this.timKiemDoiTuong();

      switch (parseInt(this.loaiDoiTuongId)) {
        case LoaiDoiTuong_NV.CST_BLOCK:
          this.dsKetCuoi = dataSource;
          break;
        case LoaiDoiTuong_NV.CST_PILLAR:
          this.dsCotCap = dataSource;
          break;
        case LoaiDoiTuong_NV.CST_CABLE:
          this.dsCap = dataSource;
          break;
        case LoaiDoiTuong_NV.CST_PIPESEGMENT:
          this.dsDoanCong = dataSource;
          break;
        case LoaiDoiTuong_NV.CST_MANHOLE:
          this.dsBeCap = dataSource;
          break;
        case LoaiDoiTuong_NV.CST_EXCHANGE:
          this.dsTongDai = dataSource;
          break;
      }
    },
    async laySoLuongBanGhi() {
      let soLuongBanGhi = 0;
      try {
        this.loading(true);
        let response = await this.$root.context.post(
          "/web-cabman/danhmuc/ds-cap-ketcuoi/so-ban-ghi",
          this.searchData
        );

        if (response.error_code === "BSS-00000000" && response.data != null) {
          soLuongBanGhi = response.data.SO_BAN_GHI;
        } else {
          let message = [];

          if (response.message) {
            message.push(response.message);
          }

          if (response.message_detail) {
            message.push(response.message_detail);
          }

          if (response.data) {
            let detailMessage = Object.getOwnPropertyNames(response.data).map(
              (item) => response.data[item]
            );
            message.push([...new Set(detailMessage)].join("\n"));
          }

          this.$toast.error(message.join("\n"));
        }
      } catch (err) {
        let message = [];
        let response = err.response.data;

        if (response.message) {
          message.push(response.message);
        }

        if (response.message_detail) {
          message.push(response.message_detail);
        }

        if (response.data) {
          let detailMessage = Object.getOwnPropertyNames(response.data).map(
            (item) => response.data[item]
          );
          message.push([...new Set(detailMessage)].join("\n"));
        }

        this.$toast.error(message.join("\n"));
      } finally {
        this.loading(false);
      }

      return soLuongBanGhi;
    },
    async timKiemDoiTuong() {
      let dsDoiTuong = [];

      try {
        this.loading(true);
        let response = await this.$root.context.post(
          "/web-cabman/danhmuc/ds-cap-ketcuoi",
          this.searchData
        );

        if (response.error_code === "BSS-00000000" && response.data != null) {
          dsDoiTuong = response.data;
        } else {
          let message = [];

          if (response.message) {
            message.push(response.message);
          }

          if (response.message_detail) {
            message.push(response.message_detail);
          }

          if (response.data) {
            let detailMessage = Object.getOwnPropertyNames(response.data).map(
              (item) => response.data[item]
            );
            message.push([...new Set(detailMessage)].join("\n"));
          }

          this.$toast.error(message.join("\n"));
        }
      } catch (err) {
        let message = [];
        let response = err.response.data;

        if (response.message) {
          message.push(response.message);
        }

        if (response.message_detail) {
          message.push(response.message_detail);
        }

        if (response.data) {
          let detailMessage = Object.getOwnPropertyNames(response.data).map(
            (item) => response.data[item]
          );
          message.push([...new Set(detailMessage)].join("\n"));
        }

        this.$toast.error(message.join("\n"));
      } finally {
        this.loading(false);
      }

      return dsDoiTuong;
    },
    async timKiemAllDoiTuong() {
      let dsDoiTuong = [];
      try {
        this.loading(true);
        const args = { ...this.searchData };
        args.page_no = 0;
        args.page_size = this.soLuongBanGhi > 10000 ? 10000 : this.soLuongBanGhi;
        let response = await this.$root.context.post(
          "/web-cabman/danhmuc/ds-cap-ketcuoi",
          args
        );

        if (response.error_code === "BSS-00000000" && response.data != null) {
          dsDoiTuong = response.data;
        } else {
          let message = [];

          if (response.message) {
            message.push(response.message);
          }

          if (response.message_detail) {
            message.push(response.message_detail);
          }

          if (response.data) {
            let detailMessage = Object.getOwnPropertyNames(response.data).map(
              (item) => response.data[item]
            );
            message.push([...new Set(detailMessage)].join("\n"));
          }

          this.$toast.error(message.join("\n"));
        }
      } catch (err) {
        let message = [];
        let response = err.response.data;

        if (response.message) {
          message.push(response.message);
        }

        if (response.message_detail) {
          message.push(response.message_detail);
        }

        if (response.data) {
          let detailMessage = Object.getOwnPropertyNames(response.data).map(
            (item) => response.data[item]
          );
          message.push([...new Set(detailMessage)].join("\n"));
        }

        this.$toast.error(message.join("\n"));
      } finally {
        this.loading(false);
      }
      return dsDoiTuong;
    },
    async timKiemAllDoiTuong_bk() {
      if (this.soLuongBanGhi < 5000) {
        let dsDoiTuong = [];

        try {
          this.loading(true);
          const args = { ...this.searchData };
          (args.page_no = 0), (args.page_size = this.soLuongBanGhi);
          let response = await this.$root.context.post(
            "/web-cabman/danhmuc/ds-cap-ketcuoi",
            args
          );

          if (response.error_code === "BSS-00000000" && response.data != null) {
            dsDoiTuong = response.data;
          } else {
            let message = [];

            if (response.message) {
              message.push(response.message);
            }

            if (response.message_detail) {
              message.push(response.message_detail);
            }

            if (response.data) {
              let detailMessage = Object.getOwnPropertyNames(response.data).map(
                (item) => response.data[item]
              );
              message.push([...new Set(detailMessage)].join("\n"));
            }

            this.$toast.error(message.join("\n"));
          }
        } catch (err) {
          let message = [];
          let response = err.response.data;

          if (response.message) {
            message.push(response.message);
          }

          if (response.message_detail) {
            message.push(response.message_detail);
          }

          if (response.data) {
            let detailMessage = Object.getOwnPropertyNames(response.data).map(
              (item) => response.data[item]
            );
            message.push([...new Set(detailMessage)].join("\n"));
          }

          this.$toast.error(message.join("\n"));
        } finally {
          this.loading(false);
        }

        return dsDoiTuong;
      } else {
        this.$root.toastError(
          "Số bản ghi đang nhiều hơn 5000 nên chỉ lấy theo danh sách trang hiện tại."
        );
        return await this.timKiemDoiTuong();
      }
    },
    btnXuatExcel_Excel: async function () {
      if (parseInt(this.loaiDoiTuongId) == LoaiDoiTuong_NV.CST_BLOCK) {
        let excelExportProperties = {
          columns: [
            { field: "KETCUOI_ID", headerText: "Kết cuối ID (*)" },
            { field: "MA_KC", headerText: "Mã kết cuối" },
            { field: "KYHIEU", headerText: "Ký hiệu" },
            { field: "TEN_KC", headerText: "Tên kết cuối" },
            { field: "DIACHI", headerText: "Địa chỉ" },
            { field: "GHICHU", headerText: "Ghi chú" },
          ],
        };
        let d = await this.timKiemAllDoiTuong();
        if (d.length > 0) {
          excelExportProperties.dataSource = d;
          this.$refs.gridKetCuoi.excelExport(excelExportProperties);
        } else {
          this.$toast.error("Không có dữ liệu để xuất excel!");
        }
      } else if (parseInt(this.loaiDoiTuongId) == LoaiDoiTuong_NV.CST_CABLE) {
        let excelExportProperties = {
          columns: [
            { field: "CAP_ID", headerText: "Cáp ID (*)" },
            { field: "MA_CAP", headerText: "Mã cáp" },
            { field: "KYHIEU", headerText: "Ký hiệu" },
            { field: "TEN_CAP", headerText: "Tên cáp" },
            { field: "GHICHU", headerText: "Ghi chú" },
          ],
        };
        let d = await this.timKiemAllDoiTuong();
        if (d.length > 0) {
          excelExportProperties.dataSource = d;
          this.$refs.gridCap.excelExport(excelExportProperties);
        } else {
          this.$toast.error("Không có dữ liệu để xuất excel!");
        }
      }
    },
    grid_onCommandClicked: function (name, dataItem) {
      let r = {
        doi_tuong: this.loaiDoiTuongId,
        bd_id:
          parseInt(this.loaiDoiTuongId) == LoaiDoiTuong_NV.CST_CABLE
            ? dataItem.CAP_ID
            : dataItem.KETCUOI_ID,
        //1621995
        //(parseInt(this.loaiDoiTuongId) == LoaiDoiTuong_NV.CST_CABLE) ? dataItem.CAP_ID : dataItem.KETCUOI_ID
        // 1655858
      };
      this.$refs.popupThongTinBienDongCapKC.showModal(r);
    },
  },
  watch: {
    async loaiDoiTuongId() {
      this.dataSource = [];
      this.dsKetCuoi = [];
      this.dsCotCap = [];
      this.dsCap = [];
      this.dsDoanCong = [];
      this.dsBeCap = [];
      this.dsTongDai = [];
      this.soLuongBanGhi = 0;
      this.selectedRowIndex = 0;
      this.searchData = null;
      this.dungLuong = 1;
      this.tenDoiTuong = "";
      this.kyHieu = "";
      this.diaChi = "";
      try {
        this.loading(true);
        await this.layDmTuyen();
      } catch (err) {
      } finally {
        this.loading(false);
      }
    },
    async quanId(newVal) {
      try {
        this.loading(true);
        await this.layDmPhuong();
      } catch (err) {
      } finally {
        this.loading(false);
      }
    },
    async phuongId() {
      try {
        this.loading(true);
        await this.layDmPho();
      } catch (err) {
      } finally {
        this.loading(false);
      }
    },
    chkQuanChecked(newVal) {
      if (!newVal) {
        this.chkPhuongChecked = false;
        this.chkPhoChecked = false;
      }
    },
    chkPhuongChecked(newVal) {
      if (!newVal) {
        this.chkPhoChecked = false;
      }
    },
  },
  async mounted() {
    try {
      this.loading(true);
      let loading1 = this.layDmQuan();
      let loading2 = this.layDmTuyen();
      await loading1;
      await loading2;
    } catch (err) {
    } finally {
      this.loading(false);
    }

    this.$refs["input-ten-doituong"].focus();
  },
};
</script>
<style>
.danhsach-cap-ketcuoi .e-grid {
  font-family: "Roboto", Arial !important;
}

.danhsach-cap-ketcuoi .e-grid .e-filterbarcell {
  padding: 5px 7px !important;
}

.danhsach-cap-ketcuoi .e-grid th.e-headercell,
.danhsach-cap-ketcuoi .e-grid th.e-filterbarcell {
  background-color: #bae7ff !important;
  border-color: white !important;
}

.danhsach-cap-ketcuoi .e-grid th.e-filterbarcell {
  border-width: 1px 0 0 1px !important;
}

.danhsach-cap-ketcuoi .e-grid td.e-active {
  background: #f9e1a9 !important;
}

.danhsach-cap-ketcuoi
  .e-grid.e-gridhover
  tr[role="row"]:not(.e-editedrow):hover
  .e-rowcell:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.danhsach-cap-ketcuoi
  .e-grid.e-gridhover
  tr[role="row"]:hover
  .e-detailrowcollapse:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.danhsach-cap-ketcuoi
  .e-grid.e-gridhover
  tr[role="row"]:hover
  .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.danhsach-cap-ketcuoi
  .e-grid.e-rtl
  .e-gridhover
  tr[role="row"]:hover
  .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.danhsach-cap-ketcuoi
  .e-grid.e-gridhover
  tr[role="row"]:hover
  .e-detailrowexpand:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell) {
  background-color: #fff9eb !important;
}

.danhsach-cap-ketcuoi .e-grid td.e-rowcell {
  line-height: 20px !important;
  padding: 5px 7px !important;
}

.danhsach-cap-ketcuoi
  .e-grid
  .e-filterdiv.e-fltrinputdiv
  .e-input-group.e-control-wrapper {
  border: 0 !important;
}

.danhsach-cap-ketcuoi .e-grid .e-filtertext.e-input.e-disable {
  display: none !important;
}

.danhsach-cap-ketcuoi .e-grid tr.e-emptyrow td {
  text-align: center !important;
}
</style>
