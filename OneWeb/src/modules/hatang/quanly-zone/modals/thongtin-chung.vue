<template src="./thongtin-chung.html"></template>
<script>


export default {

  data() {
    return {

      currentItem: {
        ZONE_ID: 0,
        TEN_ZONE: null,
        IDC_ID:null,
        TEN_IDC:null,
        GHI_CHU: null,
        HIEU_LUC: 1,
      },
      currentZoneId: null,
      currentTab: 1,

      dsIDC: [],
      dsVLAN: [],
      dsTbi: [],
      dsTbiMang: [],
      dsSubnet: [],
      dsIP: [],
      dsLichSu: [],

      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' }
    }
  },
  computed: {
    isEditMode() {
      return this.currentZoneId > 0 && this.currentItem.ZONE_ID > 0
    }
  },
  methods: {
    getZoneById: async function (zoneId) {
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc-quanly-zone/get-danhmuc-zone", {
          loaiDanhMuc: "ZONE-BY-ID",
          thamSo1: zoneId,
        });
        this.currentItem = rs.data[0] || {};
        // console.log(this.currentItem);
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
    },
    getDsVlan: async function () {
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc-quanly-zone/get-danhmuc-zone", {
          loaiDanhMuc: "DS-VLAN",
          thamSo1:this.currentZoneId,
        });
        this.dsVLAN = rs.data || [];
        this.dsVLAN.forEach((item, index) => {
          item.STT = index + 1; // Thêm trường STT
        });
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
    },
    getDsTbi: async function () {
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc-quanly-zone/get-danhmuc-zone", {
          loaiDanhMuc: "DS-TBI",
          thamSo1:this.currentZoneId,
        });
        this.dsTbi = rs.data || [];
        this.dsTbi.forEach((item, index) => {
          item.STT = index + 1; // Thêm trường STT
        });
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
    },
    getDsTbiMang: async function () {
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc-quanly-zone/get-danhmuc-zone", {
          loaiDanhMuc: "DS-TBI-MANG",
          thamSo1:this.currentZoneId,
        });
        this.dsTbiMang = rs.data || [];
        this.dsTbiMang.forEach((item, index) => {
          item.STT = index + 1; // Thêm trường STT
        });
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
    },
    getDsSubnet: async function () {
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc-quanly-zone/get-danhmuc-zone", {
          loaiDanhMuc: "DS-SUBNET",
          thamSo1:this.currentZoneId,
        });
        this.dsSubnet = rs.data || [];
        this.dsSubnet.forEach((item, index) => {
          item.STT = index + 1; // Thêm trường STT
        });
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
    },
    getDsIP: async function () {
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc-quanly-zone/get-danhmuc-zone", {
          loaiDanhMuc: "DS-IP",
          thamSo1:this.currentZoneId,
        });
        this.dsIP = rs.data || [];
        this.dsIP.forEach((item, index) => {
          item.STT = index + 1; // Thêm trường STT
        });
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
    },
    getDsLichSu: async function () {
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc-quanly-zone/get-danhmuc-zone", {
          loaiDanhMuc: "DS-LICHSU-ZONE",
          thamSo1:null,
        });
        this.dsLichSu = rs.data || [];
        this.dsLichSu.forEach((item, index) => {
          item.STT = index + 1; // Thêm trường STT
        });
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
    },
    getDsIDC: async function () {
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "IDC",
          thamSo1:null
        });
        this.dsIDC = rs.data || [];
        console.log('dsIDC loaded:', this.dsIDC);
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
    },
    upsertZone: async function (args) {
      let result = false;
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc-quanly-zone/upsert", {
          zoneId: args.ZONE_ID === 0 ? null : args.ZONE_ID,
          tenZone: args.TEN_ZONE,
          idcId: args.IDC_ID,
          ghichu: args.GHI_CHU,
          hieuluc: args.HIEU_LUC,
        });
        result = rs.data;
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    async loadAllRelatedData() {
      await Promise.all([
        this.getDsVlan(),
        this.getDsTbi(),
        this.getDsTbiMang(),
        this.getDsSubnet(),
        this.getDsIP(),
        // this.getDsIDC(),
        this.getDsLichSu()   // nếu cần lọc theo zoneId thì sửa lại thamSo1
      ]);
    },
    clearAllDatasets() {
      this.currentItem = {
        ZONE_ID: 0,
        TEN_ZONE: null,
        IDC_ID:null,
        TEN_IDC:null,
        GHI_CHU: null,
        HIEU_LUC: 1,
      };
      this.dsVLAN = [];
      this.dsTbi = [];
      this.dsTbiMang = [];
      this.dsSubnet = [];
      this.dsIP = [];
      // this.dsIDC = [];
      this.dsLichSu = [];
      this.currentTab = 1;
    },
    async openModal({ mode = 'ADD', zoneId = null, zoneData = null } = {}) {
      try {
        this.$root.showLoading(true);
        // Reset sạch
        this.currentZoneId = zoneId;
        this.clearAllDatasets();
        await this.getDsIDC()          // Load danh sách IDC

        // Nếu là sửa thì load dữ liệu
        if (mode === 'EDIT' && zoneId) {
          await this.getZoneById(zoneId)        // API lấy chi tiết Zone
          await this.loadAllRelatedData()       // Load VLAN, Subnet, IP...
        }
        // Mở modal
        this.$refs.modalThongTinChung.show()
      } catch (e) {
        console.log('Lỗi:', e);
        this.$toast.error('Đã xảy ra lỗi khi mở dialog');
      } finally {
        this.$root.showLoading(false);
      }
    },
    closeModal() {
      this.$refs.modalThongTinChung.hide();
    },

    onSave: async function () {
      const errors = this.validateForm();
      if (errors.length) {
        this.$toast.error(errors.join('\n'));
        return;
      } else {
        let flag = await this.$confirm(
          "Bạn có muốn lưu thông tin này không ?",
          "Xác nhận",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
            type: "info",
            dangerouslyUseHTMLString: true,
          }
        )
          .then((s) => true)
          .catch((e) => {
            return false;
          });
        if (flag) {
          try {
            this.$root.showLoading(true);

            let rs = await this.upsertZone(this.currentItem);
            // 🟡 1) Nếu SP trả lỗi
            if (rs?.STATUS === "ERROR") {
              this.$root.toastError(rs.MESSAGE || "Lỗi chưa xác định");
              return; // DỪNG ở đây
            }
            if (rs?.ZONE_ID) {
              if (this.currentItem.ZONE_ID == 0 || this.currentItem.ZONE_ID == null) {
                this.$root.toastSuccess("Thêm Zone thành công");
              }
              else  {
                this.$root.toastSuccess("Cập nhật Zone thành công");
              }
              this.$emit('zoneAllocated'); // thông báo đã lưu thành công
              this.closeModal();

            }
            else {
              if (this.currentItem.ZONE_ID === 0) {
                this.$root.toastError("Thêm Zone thất bại");
              }
              else {
                this.$root.toastError("Cập nhật Zone thất bại");
              }
            }
          } finally {
            await this.getDsLichSu();
            this.$root.showLoading(false);
          }
        }
      }
    },

    validateForm() {
      let errors = [];
      if (!this.currentItem.TEN_ZONE) errors.push('Tên Zone không được để trống');
      if (!this.currentItem.IDC_ID) errors.push('IDC không được để trống');
      return errors;
    },

    onOverlayClick: function() {
      this.clearAllDatasets();
      this.$refs.modalThongTinChung.hide();  // hoặc xử lý khác
    },
  }
}
</script>
<style>
.w90-modal .modal-dialog {
  max-width: 80% !important;
  width: 80% !important;
}
</style>
