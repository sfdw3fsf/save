<template src="./thongtin-chung.html"></template>
<script>


export default {

  data() {
    return {

      currentItem: {
        VRF_ID: 0,
        TEN_VRF: null,
        IDC_ID:null,
        TEN_IDC:null,
        ASN:null,
        RD:null,
        RT_IMPORT:null,
        RT_EXPORT:null,
        GHI_CHU: null,
        HIEU_LUC: 1,
      },
      currentVrfId: null,
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
      return this.currentVrfId > 0 && this.currentItem.VRF_ID > 0
    }
  },
  methods: {
    getVrfById: async function (vrfId) {
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc-quanly-vrf/get-danhmuc-vrf", {
          loaiDanhMuc: "VRF-BY-ID",
          thamSo1: vrfId,
        });
        this.currentItem = rs.data[0] || {};
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
    },
    getDsVlan: async function () {
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc-quanly-vrf/get-danhmuc-vrf", {
          loaiDanhMuc: "DS-VLAN",
          thamSo1:this.currentVrfId,
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
        let rs = await this.$root.context.post("/web-ecms/danhmuc-quanly-vrf/get-danhmuc-vrf", {
          loaiDanhMuc: "DS-TBI",
          thamSo1:this.currentVrfId,
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
        let rs = await this.$root.context.post("/web-ecms/danhmuc-quanly-vrf/get-danhmuc-vrf", {
          loaiDanhMuc: "DS-TBI-MANG",
          thamSo1:this.currentVrfId,
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
        let rs = await this.$root.context.post("/web-ecms/danhmuc-quanly-vrf/get-danhmuc-vrf", {
          loaiDanhMuc: "DS-SUBNET",
          thamSo1:this.currentVrfId,
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
        let rs = await this.$root.context.post("/web-ecms/danhmuc-quanly-vrf/get-danhmuc-vrf", {
          loaiDanhMuc: "DS-IP",
          thamSo1:this.currentVrfId,
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
        let rs = await this.$root.context.post("/web-ecms/danhmuc-quanly-vrf/get-danhmuc-vrf", {
          loaiDanhMuc: "DS-LICHSU-VRF",
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
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
    },
    upsertVrf: async function (args) {
      let result = false;
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc-quanly-vrf/upsert", {
          vrfId: args.VRF_ID === 0 ? null : args.VRF_ID,
          tenVrf: args.TEN_VRF,
          asn: args.ASN,
          rd: args.RD,
          rtImport: args.RT_IMPORT,
          rtExport: args.RT_EXPORT,
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
        this.getDsLichSu()   // nếu cần lọc theo vrfId thì sửa lại thamSo1
      ]);
    },
    clearAllDatasets() {
      this.currentItem = {
        VRF_ID: 0,
        TEN_VRF: null,
        IDC_ID:null,
        TEN_IDC:null,
        ASN:null,
        RD:null,
        RT_IMPORT:null,
        RT_EXPORT:null,
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
    async openModal({ mode = 'ADD', vrfId = null, vrfData = null } = {}) {
      try {
        this.$root.showLoading(true);
        // Reset sạch
        this.currentVrfId = vrfId;
        this.clearAllDatasets();
        await this.getDsIDC()          // Load danh sách IDC

        // Nếu là sửa thì load dữ liệu
        if (mode === 'EDIT' && vrfId) {
          await this.getVrfById(vrfId)        // API lấy chi tiết VRF
          await this.loadAllRelatedData()       // Load VLAN, Subnet, IP...
        }
        // Mở modal
        this.$refs.modalThongTinChungVrf.show()
      } catch (e) {
        console.log('Lỗi:', e);
        this.$toast.error('Đã xảy ra lỗi khi mở dialog');
      } finally {
        this.$root.showLoading(false);
      }
    },
    closeModal() {
      this.$refs.modalThongTinChungVrf.hide();
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

            let rs = await this.upsertVrf(this.currentItem);
            // 🟡 1) Nếu SP trả lỗi
            if (rs?.STATUS === "ERROR") {
              this.$root.toastError(rs.MESSAGE || "Lỗi chưa xác định");
              return; // DỪNG ở đây
            }
            if (rs?.VRF_ID) {
              if (this.currentItem.VRF_ID == 0 || this.currentItem.VRF_ID == null) {
                this.$root.toastSuccess("Thêm VRF thành công");
              }
              else  {
                this.$root.toastSuccess("Cập nhật VRF thành công");
              }
              await this.$parent.reloadVrfList(); // thông báo đã lưu thành công
              this.closeModal();

            }
            else {
              if (this.currentItem.VRF_ID === 0) {
                this.$root.toastError("Thêm VRF thất bại");
              }
              else {
                this.$root.toastError("Cập nhật VRF thất bại");
              }
            }
          } finally {
            this.$root.showLoading(false);
          }
        }
      }
    },

    validateForm() {
      let errors = [];
      if (!this.currentItem.TEN_VRF) errors.push('Tên VRF không được để trống');
      if (!this.currentItem.IDC_ID) errors.push('IDC không được để trống');
      return errors;
    },

    onOverlayClick: function() {
      this.clearAllDatasets();
      this.$refs.modalThongTinChungVrf.hide();  // hoặc xử lý khác
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
