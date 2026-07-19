<template src="./thicong-vitri-rack.html"></template>

<script>
import axios from 'axios';
export default {
  props: {
    phieuThiCongId: {
     type: Number,
     default: null,
    },
  },
  data: function() {
    return {
      // Biến lưu dữ liệu
      dataRack: {},
      rackId: null,
      // loaiRackSelectList: [],
      // quyHoachSelectList: [],
      // vanHanhSelectList: [],
      // ccdvSelectList: [],
      idcSelectList: [],
      matSanSelectList: [],
      phongSelectList: [],
      toanhaSelectList: [],

      isEditMode: false,
      buttons: {
        btnGhiLai: {
          enabled: false,
        },
        btnHuy: {
          enabled: false,
        },
        btnSua: {
          enabled: true,
        },
      },


      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
    }
  },
  // created: async function () {
  //   try {
  //     this.$root.showLoading(true);
  //     this.dataRack = await this.getChiTietRack();
  //   } finally {
  //     this.$root.showLoading(false);
  //   }
  // },

  methods: {
    // Phần gọi các API
    getChiTietRack: async function(id) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "CHITIET-RACK",
          thamSo1:id
        });
        result = rs.data || []; // Lấy bản ghi đầu tiên từ mảng data
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    getData: async function(id) {
      let rs = await this.getChiTietRack(id);
      if (rs) {
        this.clearAll();
        this.dataRack = rs[0] || {}; // Giả sử API trả về mảng, lấy phần tử đầu tiên
      } else {
        this.$toast.error('Tải dữ liệu không thành công!');
      }
    },
    upsertRack: async function (args) {
      let result = false;
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/upsert", {
          rackId: this.rackId,
          phongId: args.PHONG_ID,
          idcId: args.IDC_ID,
          vitriRack: args.VITRI_RACK,
        });
        result = rs.data;
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    getLoaiRackItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "LOAIRACK",
          thamSo1:null
        });
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    getCCDVItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "CCDV",
          thamSo1:null
        });
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    getQuyHoachItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "QUYHOACH",
          thamSo1:null
        });
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    getVanHanhItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "VANHANH",
          thamSo1:null
        });
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    getIDCItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "IDC-BY-PHIEUTC",
          thamSo1:this.phieuThiCongId
        });
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    getToanhaItems: async function (idcId) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "TOANHA",
          thamSo1:idcId
        });
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    getMatSanItems: async function (toanhaId) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "MATSAN",
          thamSo1:toanhaId
        });
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    getPhongItems: async function (matSanId) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "PHONG",
          thamSo1:matSanId
        });
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    clearAll() {
      this.dataRack = {};
    },
    closeDialog() {
      this.clearAll();
      this.enableControls(false);
      this.$refs.ModalLapDatViTriRack.hide();
    },
    async dialogOpen(rackId) {
      try {
        this.$root.showLoading(true);
        this.isEditMode = false;
        this.clearAll();
        this.rackId = rackId;
        if (this.$refs.ModalLapDatViTriRack) {
          if (rackId > 0) {
            await this.getData(rackId);
            // this.loaiRackSelectList = await this.getLoaiRackItems();
            // this.ccdvSelectList = await this.getCCDVItems();
            // this.vanHanhSelectList = await this.getVanHanhItems();
            // this.quyHoachSelectList = await this.getQuyHoachItems();
            this.idcSelectList = await this.getIDCItems();
            this.toanhaSelectList = await this.getToanhaItems(this.dataRack.IDC_ID);
            this.matSanSelectList = await this.getMatSanItems(this.dataRack.TOANHA_ID);
            this.phongSelectList = await this.getPhongItems(this.dataRack.MATSAN_ID);
            this.$refs.ModalLapDatViTriRack.show();
          } else {
            this.$toast.error('Không tìm thấy ID rack!');
          }
        } else {
          this.$toast.error('Dialog không tồn tại hoặc không được tham chiếu đúng');
        }
      } catch (e) {
        console.log('Lỗi:', e);
        this.$toast.error('Đã xảy ra lỗi khi mở dialog');
      } finally {
        this.$root.showLoading(false);
      }
    },
    enableControls: function (args) {
      this.buttons.btnGhiLai.enabled = args;
      this.buttons.btnHuy.enabled = this.buttons.btnGhiLai.enabled;
      this.buttons.btnSua.enabled = !this.buttons.btnGhiLai.enabled;
    },

    // Validate form
    validateForm() {
      let errors = [];
      // if (!this.dataRack.TEN_RACK) errors.push('Tên Rack không được để trống');
      // if (!this.dataRack.SOHUU_RACK && this.dataRack.SOHUU_RACK !== 0) errors.push('Sở hữu Rack không được để trống');
      // if (!this.dataRack.LOAIRACK_ID) errors.push('Loại Rack không được để trống');
      // if (!this.dataRack.VANHANH) errors.push('Trạng thái vận hành không được để trống');
      // if (this.dataRack.SOHUU_RACK == 1) {
      //   if (!this.dataRack.MA_TS) errors.push('Mã tài sản không được để trống');
      // }
      // if (!this.dataRack.CCDV) errors.push('Trạng thái Rack CCDV không được để trống');
      // if (!this.dataRack.QUYHOACH) errors.push('Trạng thái Rack theo quy hoạch không được để trống');
      if (!this.dataRack.IDC_ID) errors.push('IDC không được để trống');
      if (!this.dataRack.MATSAN_ID) errors.push('Mặt sàn không được để trống');
      if (!this.dataRack.PHONG_ID) errors.push('Phòng không được để trống');
      if (!this.dataRack.VITRI_RACK) errors.push('Vị trí Rack không được để trống');

      return errors;
    },

    // Xử lý sự kiện nút bấm
    btnGhiLai_Click: async function () {
      if (!this.phieuThiCongId) {
        this.$toast.warning('Không có thông tin phiếu thi công');
        return;
      }
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
            let rs = await this.upsertRack(this.dataRack);
            if (rs) {
              this.$root.toastSuccess("Lắp đặt Rack thành công");
              this.$emit('rackAllocated', this.dataRack);
              this.$parent.loadHistoryList();
              this.closeDialog();
            }
            else {
              this.$root.toastError("Lắp đặt Rack thất bại");
            }
            this.enableControls(!rs);
            this.isEditMode = !rs;
            this.$refs.ModalLapDatViTriRack.hide();
          } finally {
            this.$root.showLoading(false);
          }
        }
      }
    },
    btnSua_Click: async function () {
      this.isEditMode = true;
      this.enableControls(true);
    },
    btnHuy_Click: async function () {
      try {
        this.$root.showLoading(true);
        let rs = await this.getChiTietRack(this.rackId);
        this.dataRack = rs[0];
        this.isEditMode = false;
        this.enableControls(false);
      } finally {
        this.$root.showLoading(false);
      }
    },
    onChangeIDC: async function () {
      if (this.dataRack.IDC_ID != 0) {
        try {
          this.$root.showLoading(true);
          this.toanhaSelectList = await this.getToanhaItems(this.dataRack.IDC_ID);
          this.dataRack.TOANHA_ID = 0;
          this.dataRack.MATSAN_ID = 0;
          this.dataRack.PHONG_ID= 0;
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    onChangeToanha: async function () {
      if (this.dataRack.TOANHA_ID != 0) {
        try {
          this.$root.showLoading(true);
          this.matSanSelectList = await this.getMatSanItems(this.dataRack.TOANHA_ID);
          this.dataRack.MATSAN_ID = 0;
          this.dataRack.PHONG_ID= 0;
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    onChangeMatSan: async function () {
      if (this.dataRack.MATSAN_ID != 0) {
        try {
          this.$root.showLoading(true);
          this.phongSelectList = await this.getPhongItems(this.dataRack.MATSAN_ID);
          this.dataRack.PHONG_ID = 0;
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    onSelectRack: async function (rack) {
      this.dataRack = { ...rack };
      this.idcSelectList = await this.getIDCItems();
      this.toanhaSelectList = await this.getToanhaItems(rack.IDC_ID);
      this.matSanSelectList = await this.getMatSanItems(rack.TOANHA_ID);
      this.phongSelectList = await this.getPhongItems(rack.MATSAN_ID);
    },

    onOverlayClick: function() {
      this.clearAll();
      this.$refs.ModalLapDatViTriRack.hide();  // hoặc xử lý khác
    },


  }
}
</script>
