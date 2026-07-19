<template src="./ThongTinRack.html"></template>

<script>
import axios from 'axios';
export default {
  props: {
    rackId: {
      type: Number,
      default: 0
    }
  },
  data: function() {
    return {
      dataItem: {},
      isNew: false,
      isEditMode: false,
      loaiRackSelectList: [],
      idcSelectList: [],
      matSanSelectList: [],
      phongSelectList: [],
      quyHoachSelectList: [],
      vanHanhSelectList: [],
      ccdvSelectList: [],
      controls: {
        txtRackId: {
          enabled: false
        },
        txtTenRack:{
          enabled: false
        },
        txtMaTaiSan: {
          enabled: false
        },
        txtGhiChu: {
          enabled: false
        },
        selectLoaiRackId: {
          enabled: false
        },
        selectVanHanh: {
          enabled: false
        },
        selectQuyHoach: {
          enabled: false
        },
        selectCCDV: {
          enabled: false
        },
        selectIDC: {
          enabled: false
        },
        selectMatSan: {
          enabled: false
        },
        selectPhong: {
          enabled: false
        },
        chkHieuLuc: {
          enabled: false,
        },
        chkSoHuuRack: {
          enabled: false,
        },
      },
      buttons: {
        // btnNhapMoi: {
        //   enabled: true,
        // },
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
      animationSettings: { effect: 'Zoom' }
    }
  },
  watch: {
    // rackId(newVal) {
    //   if (newVal) {
    //     this.dialogOpen(); // mở dialog khi nhận được rackId mới
    //   }
    // },
    "dataItem.TEN_RACK": function (value) {
      this.controls.txtTenRack.invalid = value == "";
    },
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      this.dataItem = await this.getChiTietRack();
      this.loaiRackSelectList = await this.getLoaiRackItems();
      this.ccdvSelectList = await this.getCCDVItems();
      this.vanHanhSelectList = await this.getVanHanhItems();
      this.quyHoachSelectList = await this.getQuyHoachItems();
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    // Phần gọi các API
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
    upsertRack: async function (args) {
      let result = false;
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/upsert", {
          rackId: args.RACK_ID === 0 ? null : args.RACK_ID,
          loaiRackId: args.LOAIRACK_ID,
          tenRack: args.TEN_RACK,
          maTaiSan: args.MA_TS,
          quyHoach: args.QUYHOACH,
          vanHanh: args.VANHANH,
          ccdv: args.CCDV,
          suDung: args.SUDUNG,
          ghiChu: args.GHI_CHU,
          soHuuRack: args.SOHUU_RACK
        });
        result = rs.data;
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    clearAll() {
      this.dataItem = {};
    },
    getData: async function(id) {
      try {
        this.$root.showLoading(true);
        let rs = await this.getChiTietRack(id);
        if (rs) {
          this.clearAll();
          this.dataItem = rs[0] || {}; // Giả sử API trả về mảng, lấy phần tử đầu tiên
        } else {
          this.$toast.error('Tải dữ liệu không thành công!');
        }
      } finally {
        this.$root.showLoading(false);
      }
    },
    closeDialog() {
      this.clearAll();
      this.enableControls(false);
      this.$refs.ModalThongTinRack.hide();
    },

    async dialogOpen(id) {
      try {
        this.isNew = false;
        this.isEditMode = false;
        this.$root.showLoading(true);
        this.clearAll();
        if (this.$refs.ModalThongTinRack) {
          if (id > 0) {
            await this.getData(id);
            this.$refs.ModalThongTinRack.show();
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

    async openNewDialog() {
      this.isNew = true;
      this.isEditMode = true;
      this.clearAll();

      this.$set(this.dataItem, 'SUDUNG', 1);  // Tự động check Hiệu lực
      this.$set(this.dataItem, 'SOHUU_RACK', 1); // Tự động chọn IDC đầu tiên nếu có

      if (this.$refs.ModalThongTinRack) {
        this.buttons.btnGhiLai.enabled = true;
        this.$refs.ModalThongTinRack.show();
      } else {
        this.$toast.error('Dialog không tồn tại hoặc không được tham chiếu đúng');
      }
    },

    enableControls: function (args) {
      for (const key in this.controls) {
        if (this.controls.hasOwnProperty(key)) {
          this.controls[key].enabled = args;
        }
      }
      this.buttons.btnGhiLai.enabled = args;
      this.buttons.btnHuy.enabled = this.buttons.btnGhiLai.enabled;
      this.buttons.btnSua.enabled = !this.buttons.btnGhiLai.enabled;
    },
    validateForm() {
      let errors = [];
      if (!this.dataItem.TEN_RACK) errors.push('Tên Rack không được để trống');
      if (!this.dataItem.SOHUU_RACK && this.dataItem.SOHUU_RACK !== 0) errors.push('Sở hữu Rack không được để trống');
      if (!this.dataItem.LOAIRACK_ID) errors.push('Loại Rack không được để trống');
      if (!this.dataItem.VANHANH) errors.push('Trạng thái vận hành không được để trống');
      if (this.dataItem.SOHUU_RACK == 1) {
        if (!this.dataItem.MA_TS) errors.push('Mã tài sản không được để trống');
      }
      if (!this.dataItem.CCDV) errors.push('Trạng thái Rack CCDV không được để trống');
      if (!this.dataItem.QUYHOACH) errors.push('Trạng thái Rack theo quy hoạch không được để trống');
      // Thêm các trường khác nếu cần
      return errors;
    },
    onOverlayClick: function() {
      this.clearAll();
      this.enableControls(false);
      this.$refs.ModalThongTinRack.hide();  // hoặc xử lý khác
    },

    btnGhiLai_Click: async function () {
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
            let rs = await this.upsertRack(this.dataItem);
            if (rs) {
              if (this.dataItem.RACK_ID == 0 || this.dataItem.RACK_ID == null) {
                this.$root.toastSuccess("Thêm Rack thành công");
              }
              else  {
                this.$root.toastSuccess("Cập nhật Rack thành công");
              }
              this.$parent.reloadRacks();;
              this.$parent.reloadHistory();
            }
            else {
              if (this.dataItem.RACK_ID === 0) {
                this.$root.toastError("Thêm Rack thất bại");
              }
              else {
                this.$root.toastError("Cập nhật Rack thất bại");
              }
            }
            this.enableControls(!rs);
            this.isEditMode = !rs;
            this.$refs.ModalThongTinRack.hide();
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
        this.dataItem = rs[0];
        this.isEditMode = false;
        this.enableControls(false);
      } finally {
        this.$root.showLoading(false);
      }
    },

  }
}
</script>
