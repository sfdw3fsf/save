<template src="./thicong-thaydoi-rack.html"></template>

<script>
import ThanhNguonModal from './thaydoi-thanhnguon-modal.vue';
export default {
  components: {
    ThanhNguonModal,
  },
  props: {
    phieuThiCongId: {
     type: Number,
     default: null,
    },
  },
  data: function() {
    return {
      // Biến lưu dữ liệu
      dataRack: {
        TEN_RACK: '',
        LOAIRACK_ID: null,
        IDC_ID: null,
        TOANHA_ID: null,
        MATSAN_ID: null,
        PHONG_ID: null,
        VITRI_RACK: '',
        GHI_CHU: '',
        MA_TS: '',
        QUYHOACH: null,
        VANHANH: null,
        CCDV: null,
        SUDUNG: 1,
        SOHUU_RACK: null
      },
      dataThicongRack: {
        THICONGRACK_ID: null,
        NOIDUNG: '',
      },
      rackId: null,
      loaiRackSelectList: [],
      quyHoachSelectList: [],
      vanHanhSelectList: [],
      ccdvSelectList: [],
      idcSelectList: [],
      matSanSelectList: [],
      phongSelectList: [],
      toanhaSelectList: [],
      thanhNguonItemList: [],

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

  methods: {
    // Phần gọi các API
    getChiTietRack: async function(rackId) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "CHITIET-RACK",
          thamSo1:rackId
        });
        result = rs.data || []; // Lấy bản ghi đầu tiên từ mảng data
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    getThicongRack: async function(rackId) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "THICONG-RACK-THEO-PHIEUTC",
          thamSo1:this.phieuThiCongId,
          thamSo2:rackId
        });
        result = rs.data || []; // Lấy bản ghi đầu tiên từ mảng data
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    // getRackData: async function(rackId) {
    //   let rs = await this.getChiTietRack(rackId);
    //   if (rs) {
    //     this.clearAll();
    //     this.dataRack = rs[0] || {}; // Giả sử API trả về mảng, lấy phần tử đầu tiên
    //     console.log('dataRack', this.dataRack);
    //   } else {
    //     this.$toast.error('Tải dữ liệu không thành công!');
    //   }
    // },
    // getThicongRackData: async function(rackId) {
    //   let rs = await this.getThicongRack(rackId);
    //   if (rs) {
    //     this.clearAll();
    //     this.dataThicongRack = rs[0] || {}; // Giả sử API trả về mảng, lấy phần tử đầu tiên
    //   } else {
    //     this.$toast.error('Tải dữ liệu không thành công!');
    //   }
    // },
    upsertRacknThiCongRack: async function (args) {
      let result = false;
      try {

        let rs = await this.$root.context.post("/web-ecms/thicong-rack/upsert", {
          thiCongRack: {
            tcRackId: this.dataThicongRack.THICONGRACK_ID,
            rackId: this.rackId,
            phieuTCId: this.phieuThiCongId,
            noiDung: this.dataThicongRack.NOIDUNG,
          },
          thongTinRack: {
            rackId: this.rackId,
            phongId: args.PHONG_ID,
            idcId: args.IDC_ID,
            vitriRack: args.VITRI_RACK,
          }
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
    async getThongTinThanhNguon(id) {
      let result;
      try {
        let rs = await this.$root.context.post('/web-ecms/thongtin-rack/get-danhmuc-rack', {
          loaiDanhMuc: "THANHNGUON",
          thamSo1: id
        });
        this.thanhNguonItemList = rs.data;
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
        this.thanhNguonItemList = [];
      }
      return result;
    },
    clearAll() {
      this.dataRack = {};
    },
    closeDialog() {
      this.clearAll();
      this.enableControls(false);
      this.$refs.ModalThayDoiRack.hide();
    },
    async dialogOpen(rackId) {
      try {
        this.$root.showLoading(true);
        this.isEditMode = false;
        this.clearAll();
        this.rackId = rackId;
        if (this.$refs.ModalThayDoiRack) {
          if (rackId > 0) {
            let rs1 = await this.getChiTietRack(this.rackId);
            this.dataRack = rs1[0] || {};
            let rs2 = await this.getThicongRack(this.rackId);
            this.dataThicongRack = rs2[0] || {};
            // const [
            //   loaiRackSelectList,
            //   tccdvSelectList,
            //   vanHanhSelectList,
            //   quyHoachSelectList,
            // ] = await Promise.all([
            //   this.getLoaiRackItems(),
            //   this.getCCDVItems(),
            //   this.getVanHanhItems(),
            //   this.getQuyHoachItems(),
            // ]);
            // this.loaiRackSelectList = loaiRackSelectList;
            // this.ccdvSelectList = tccdvSelectList;
            // this.vanHanhSelectList = vanHanhSelectList;
            // this.quyHoachSelectList = quyHoachSelectList;
            this.idcSelectList = await this.getIDCItems();
            this.toanhaSelectList = await this.getToanhaItems(this.dataRack.IDC_ID);
            this.matSanSelectList = await this.getMatSanItems(this.dataRack.TOANHA_ID);
            this.phongSelectList = await this.getPhongItems(this.dataRack.MATSAN_ID);
            await this.getThongTinThanhNguon(rackId);
            this.$refs.ModalThayDoiRack.show();
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
      if (!this.dataRack.TOANHA_ID) errors.push('Tòa nhà không được để trống');
      if (!this.dataRack.MATSAN_ID) errors.push('Mặt sàn không được để trống');
      if (!this.dataRack.PHONG_ID) errors.push('Phòng không được để trống');
      if (!this.dataRack.VITRI_RACK) errors.push('Vị trí Rack không được để trống');
      if (!this.dataThicongRack.NOIDUNG) errors.push('Lí do thay đổi không được để trống');

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
            let rs = await this.upsertRacknThiCongRack(this.dataRack);
            if (rs) {
              this.$root.toastSuccess("Thay đổi Rack thành công");
              this.$emit('rackAllocated', this.dataRack);
              this.$parent.loadHistoryList();
              this.closeDialog();
            }
            else {
              this.$root.toastError("Thay đổi Rack thất bại");
            }
            this.enableControls(!rs);
            this.isEditMode = !rs;
            this.$refs.ModalThayDoiRack.hide();
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
    onThanhNguonRowDoubleClick: async function (args) {
      let thanhNguonId = null
      thanhNguonId = args.rowData.THANHNGUON_ID;
      this.$refs.popupThanhNguon.dialogOpen(thanhNguonId);
    },
    onThanhNguonAllocated() {
      this.getThongTinThanhNguon(this.rackId);
    },
    onOverlayClick: function() {
      this.clearAll();
      this.$refs.ModalThayDoiRack.hide();  // hoặc xử lý khác
    },


  }
}
</script>
