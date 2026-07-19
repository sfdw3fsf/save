<template src="./index.template.html"></template>

<script>
import Vue from 'vue'
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import ChonNgay from '../chon-ngay.vue'
const EventBus = new Vue(); // Event bus global

export default {
  components: { ChonNgay },
  data: function () {
    return {
      dsThangTimKiem: [],
      dsLoaiToiUu: [
        { Id: 2, Name: "Giảm" },
      ],
      dsTrangThaiXacNhan: [
        // { Id: 1, Name: "Chờ QTVH xác nhận" },
        { Id: 2, Name: "Chờ Pdm xác nhận" },
        { Id: 3, Name: "Pdm đã xác nhận" },
      ],
      currentFilter: {
        thangTimKiem: `${String(new Date().getMonth() + 1).padStart(2, '0')}/${new Date().getFullYear()}`,
        loaiToiUu: 2, //Tăng
        trangThaiXacNhan: 2, //Chờ QTVH xác nhận
      },
      currentYeuCauItem: {
        thangTimKiem: null,
        loaiToiUu: null,
        trangThaiXacNhan: null,
      },
      trangThaiXacNhanYeuCau: 0,
      dsMayChuToiUuSelected: [],
      dsYeuCauToiUu: [],
      dsMayChuToiUu: [],
      buttons: {
        btnTimKiem: {
          enabled: true,
        },
        btnExport: {
          enabled: true,
        },
        btnXacNhanToiUu: {
          enabled: false,
        }
      },
      sizingHienTaiColumns: [
        {
          field: 'VCPU',
          headerText: 'CPU',
          headerAlign: 'Center',
          cssClass: "text-center",
          textAlign: 'Center',
          width: 130,
          allowFiltering: true
        },
        {
          field: 'VRAM',
          width: 130,
          headerText: 'RAM',
          headerAlign: 'Center',
          textAlign: 'Center',
          cssClass: "text-center",
          allowFiltering: true
        },
      ],
      mucDoSDCaoNhatT3Columns: [
        {
          field: 'VCPU_MAX_T3',
          headerText: 'CPU',
          headerAlign: 'Center',
          textAlign: 'Center',
          width: 150,
          cssClass: "text-center",
          allowFiltering: true
        },
        {
          field: 'VRAM_MAX_T3',
          headerText: 'RAM',
          headerAlign: 'Center',
          textAlign: 'Center',
          width: 150,
          cssClass: "text-center",
          allowFiltering: true
        },
      ],
      mucDoSDCaoNhatT2Columns: [
        {
          field: 'VCPU_MAX_T2',
          headerText: 'CPU',
          textAlign: 'Center',
          headerAlign: 'Center',
          width: 150,
          cssClass: "text-center",
          allowFiltering: true
        },
        {
          field: 'VRAM_MAX_T2',
          headerText: 'RAM',
          headerAlign: 'Center',
          textAlign: 'Center',
          width: 150,
          cssClass: "text-center",
          allowFiltering: true
        },
      ],
      mucDoSDCaoNhatT1Columns: [
        {
          field: 'VCPU_MAX_T1',
          headerText: 'CPU',
          headerAlign: 'Center',
          cssClass: "text-center",
          textAlign: 'Center',
          width: 150,
          allowFiltering: true
        },
        {
          field: 'VRAM_MAX_T1',
          headerText: 'RAM',
          headerAlign: 'Center',
          cssClass: "text-center",
          textAlign: 'Center',
          width: 150,
          allowFiltering: true
        },
      ],
      hieuNangMax3TColumns: [
        {
          field: 'VCPU_MAX_TOTAL',
          headerText: 'CPU',
          headerAlign: 'Center',
          cssClass: "text-center",
          textAlign: 'Center',
          width: 130,
          allowFiltering: true
        },
        {
          field: 'VRAM_MAX_TOTAL',
          headerText: 'RAM',
          headerAlign: 'Center',
          cssClass: "text-center",
          textAlign: 'Center',
          width: 130,
          allowFiltering: true
        },
      ],
      sizingCanGiamColumns: [
        {
          field: 'VCPU_GIAM',
          headerText: 'CPU',
          headerAlign: 'Center',
          cssClass: "text-center",
          textAlign: 'Center',
          width: 130,
          allowFiltering: true
        },
        {
          field: 'VCPU_GIAM_GHICHU',
          headerText: 'Ghi chú',
          headerAlign: 'Center',
          cssClass: "text-center",
          textAlign: 'Center',
          width: 130,
          allowFiltering: true
        },
        {
          field: 'VRAM_GIAM',
          headerText: 'RAM',
          headerAlign: 'Center',
          cssClass: "text-center",
          textAlign: 'Center',
          width: 130,
          allowFiltering: true
        },
        {
          field: 'VRAM_GIAM_GHICHU',
          headerText: 'Ghi chú',
          headerAlign: 'Center',
          cssClass: "text-center",
          textAlign: 'Center',
          width: 130,
          allowFiltering: true
        },
      ],
      chonNgayTemplate: function (props) {
        console.log("props", props);
        return {
          template: {
            extends: ChonNgay,
            data() {
              return {
                rowData: props, // Truyền dữ liệu hàng từ Grid vào component
                type: 'giam'
              };
            },
            created() {
              // Gắn listener cho sự kiện date-changed
              this.$on("date-changed", this.handleDateChange);
            },
            methods: {
              handleDateChange(result) {
                EventBus.$emit('date-changed', result);
              }
            }
          }
        };
      }
    };
  },
  computed: {
    gridMayChuToiUuItems: function () {
      return this.$refs.gridMayChuToiUuItems;
    },
    dynamicColumns() {
      let columns = [
          {fieldName: 'ID', visible: false},
          {fieldName: 'QTRI_CHECK', visible: false},
          {fieldName: 'PDM_CHECK', visible: false},
          {fieldName: 'STT', headerText: 'STT', width: 70, textAlign: 'Center'},
          {fieldName: 'TEN_MAYCHU', headerText: 'Tên máy chủ', textAlign: 'Center'},
          {fieldName: 'IP', headerText: 'IP', width: 110, textAlign: 'Center'},
          {fieldName: 'TEN_SPDV', headerText: 'Sản phẩm dịch vụ', textAlign: 'Center'},
          {fieldName: 'DV_CHUQUAN', headerText: 'Đơn vị chủ quản', textAlign: 'Center'},
          {fieldName: 'PDM_SANPHAM', headerText: 'PDM Sản phẩm', textAlign: 'Center'},
          {fieldName: 'DV_VANHANH', headerText: 'Đơn vị vận hành', textAlign: 'Center'},
          {fieldName: 'SIZING_HIENTAI', headerText: 'Sizing hiện tại', textAlign: 'Center', columns: this.sizingHienTaiColumns},
          {fieldName: 'MAX_T3', headerText: 'Mức độ sử dụng MAX trong tháng T-3', textAlign: 'Center', columns: this.mucDoSDCaoNhatT3Columns},
          {fieldName: 'MAX_T2', headerText: 'Mức độ sử dụng MAX trong tháng T-2', textAlign: 'Center', columns: this.mucDoSDCaoNhatT2Columns},
          {fieldName: 'MAX_T1', headerText: 'Mức độ sử dụng MAX trong tháng T-1', textAlign: 'Center', columns: this.mucDoSDCaoNhatT1Columns},
          {fieldName: 'MAX_3T', headerText: 'Hiệu năng MAX trong 3 tháng', textAlign: 'Center', columns: this.hieuNangMax3TColumns},
          {fieldName: 'SIZING_CANGIAM', headerText: 'Tài nguyên cần giảm', textAlign: 'Center', columns: this.sizingCanGiamColumns}
      ];
      switch (this.trangThaiXacNhanYeuCau) {
        case 2:
          return [
            ...columns,
            {fieldName: 'NGAYGIAM', headerText: 'Thời điểm giảm', width: 150, textAlign: 'Center', template: this.chonNgayTemplate}
          ];
        case 3:
          return [
            ...columns,
            { fieldName: 'NGAYGIAM', headerText: 'Thời điểm giảm', width: 150, textAlign: 'Center' }
          ];
        default:
          return columns;
      }
    }
  },
  watch: {
    dsMayChuToiUuSelected(newValue) {
      this.buttons.btnExport.enabled = newValue.length > 0;
      this.buttons.btnXacNhanToiUu.enabled = newValue.length > 0 && (this.currentYeuCauItem.trangThaiXacNhan == 1 || this.currentYeuCauItem.trangThaiXacNhan == 2);
    },
    // "currentFilter.trangThaiXacNhan"(newVal) {
    //   this.reloadYeuCauToiUu();
    // },
    dsYeuCauToiUu(newValue) {
      this.buttons.btnExport.enabled = newValue.length > 0;
    },
    dynamicColumns() {
      this.gridMayChuToiUuItems.refresh();
    }
  },
  created: async function () {
    EventBus.$on('date-changed', this.handleDateChange); // Lắng nghe từ event bus
    this.dsThangTimKiem = await this.getDsThangNamToiUu();
    if (this.dsThangTimKiem.length > 0) {
      this.currentFilter.thangTimKiem = this.dsThangTimKiem[0].ID; // Gán giá trị đầu tiên
    }

    try {
      this.$root.showLoading(true);
      this.dsYeuCauToiUu = await this.getDsYeuCau();
      await this.load();
    } finally {
      this.$root.showLoading(false);
    }
  },
  beforeDestroy() {
    EventBus.$off('date-changed', this.handleDateChange); // Dọn dẹp listener
  },
  methods: {
    getDsThangNamToiUu: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
          loaiDanhMuc: "HATANG_THANGNAM_TOIUU",
          thamSo1: null,
          thamSo2: null
        });
        result = rs.data;
      } catch (e) {
        this.handleApiError(e);
      }
      return result;
    },
    getDsYeuCau: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/toi-uu-tai-nguyen-ao-hoa/ds-yeu-cau-toi-uu", {
          thang: this.currentFilter.thangTimKiem,
          loaiToiUu: this.currentFilter.loaiToiUu,
          trangThaiXacNhan: this.currentFilter.trangThaiXacNhan
        });
        result = rs.data;
        this.trangThaiXacNhanYeuCau = this.currentFilter.trangThaiXacNhan;
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    getDsMayChu: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/toi-uu-tai-nguyen-ao-hoa/ds-may-chu-toi-uu", {
          thang: this.currentYeuCauItem.thangTimKiem,
          loaiToiUu: this.currentYeuCauItem.loaiToiUu,
          trangThaiXacNhan: this.currentYeuCauItem.trangThaiXacNhan
        });
        result = rs.data;
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    load: async function () {
    },
    onSearch: async function (args) {
      this.$root.showLoading(true);
      try {
        this.dsYeuCauToiUu = await this.getDsYeuCau();
      } finally {
        this.$root.showLoading(false);
      }
    },
    onExport: async function (args) {
      const exportProperties = {
        fileName: 'DanhSachXacNhanToiUuTaiNguyenGiam.xlsx',
      };
      this.gridMayChuToiUuItems.excelExport(exportProperties);
    },
    onConfirm: async function (id) {
      this.updateSelected();
      var err_msg = "";
      if (this.dsMayChuToiUuSelected.length == 0) {
        this.$root.toastError("Vui lòng chọn máy chủ cần tối ưu");
        return;
      }
      this.dsMayChuToiUuSelected.forEach(item => {
        if (item.pdm_check == 1) {
          if (item.ngayXacNhan == null || item.ngayXacNhan == undefined) {
            if (err_msg != "") {
              err_msg += "\n";
            }
            err_msg += "Ngày giảm của máy chủ "+ (item.maychu || 'N/A') +" không được để trống";
          }
        }
      });
      if (err_msg != "") {
        this.$root.toastError(err_msg);
        return;
      }

      this.$root.showLoading(true);
      try {
        var data = {
          loaiXacNhan: this.currentYeuCauItem.trangThaiXacNhan == 2 ? 3 : 1,
          data: JSON.stringify(this.dsMayChuToiUuSelected)
        }
        await this.$root.context.post("/web-ecms/toi-uu-tai-nguyen-ao-hoa/xac-nhan-toi-uu", data);
        this.$root.toastSuccess("Xác nhận tối ưu tài nguyên ảo hóa thành công");
        this.reloadYeuCauToiUu();
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      this.$root.showLoading(false);
    },
    gridYeuCauToiUuItems_RowSelected: async function (args) {
      this.currentYeuCauItem.thangTimKiem = args.data.THANG;
      this.currentYeuCauItem.loaiToiUu = args.data.PHANLOAI;
      this.currentYeuCauItem.trangThaiXacNhan = args.data.TT_XACNHAN;

      this.reloadDsMayChuToiUu();
    },
    gridYeuCauToiUuItems_RowDeselected: async function (args) {
      // this.currentYeuCauItem.thangTimKiem = null;
      // this.currentYeuCauItem.loaiToiUu = null;
      // this.currentYeuCauItem.trangThaiXacNhan = null;
      this.dsMayChuToiUu = [];
    },
    gridMayChuToiUuItems_CheckBoxChange: async function (args) {
      this.updateSelected();
    },
    gridMayChuToiUuItems_RowSelected: async function (args) {
      this.updateSelected();
    },
    gridMayChuToiUuItems_RowDeselected: async function (args) {
      this.updateSelected();
    },
    reloadYeuCauToiUu: async function () {
      this.$root.showLoading(true);
      try {
        this.dsYeuCauToiUu = await this.getDsYeuCau();
      } finally {
        this.$root.showLoading(false);
      }
    },
    reloadDsMayChuToiUu: async function () {
      this.$root.showLoading(true);
      try {
        this.dsMayChuToiUu = await this.getDsMayChu();
      } finally {
        this.$root.showLoading(false);
      }
    },
    updateSelected: function () {
      this.dsMayChuToiUuSelected = [];
      var selected = this.$refs.gridMayChuToiUuItems.getSelectedRecords();
      if (selected) {
        selected.forEach(item => {
          this.dsMayChuToiUuSelected.push({
            id: item.ID,
            maychu: item.TEN_MAYCHU,
            ngayXacNhan: item.NGAYGIAM,
            qtri_check: this.currentYeuCauItem.trangThaiXacNhan == 1 ? 1 : item.QTRI_CHECK,
            pdm_check: this.currentYeuCauItem.trangThaiXacNhan == 2 ? 1 : item.PDM_CHECK
          });
        });
      }
    },
    handleDateChange(result) {
      const index = this.dsMayChuToiUu.findIndex(item => item.ID === result.id);
      if (index !== -1) {
        this.$set(this.dsMayChuToiUu[index], 'NGAYGIAM', result.dateStr);
      } else {
        console.error("Row not found in :", rowData);
      }
    }
  },
};
</script>
<style>
.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type="checkbox"]:checked:disabled+.name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type="checkbox"]:disabled+.name:before {
  background: #fff;
  border-color: #0176ff;
}
</style>
