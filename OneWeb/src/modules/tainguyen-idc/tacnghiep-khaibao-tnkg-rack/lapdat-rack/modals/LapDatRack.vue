<template src="./LapDatRack.html"></template>

<script>
import DanhSachRack from './DanhSachRack.vue';
import DateTimeLib from '../../../../../utils/DateTimeLib.js';
export default {

  components: {
    DanhSachRack
  },
  props: {
    phieuThiCongId: {
     type: Number,
     default: null,
    },
  },
  data: function() {
    return {
      //showModalLapDatRack: false, // Biến điều khiển hiển thị dialog
      isEditMode: false, // Chế độ chỉnh sửa

      // Biến lưu dữ liệu
      dataRack: {},
      dataThiCongRack: {},
      loaiRackSelectList: [],
      hangSXSelectList: [],
      quyHoachSelectList: [],
      ccdvSelectList: [],
      vanHanhSelectList: [],
      chuQuanSelectList: [],
      // idcSelectList: [],
      // matSanSelectList: [],
      // phongSelectList: [],

      // Ảnh icon
      displayIconUrl: '',
      previousIconRelative: '',
      pendingIconFile: null,
      pendingIconPreviewUrl: '',
      pendingRemoveIcon: false,
      uploadingIcon: false,
      loadingImageUrl: false,

      buttons: {
        btnNhapMoi: {
          enabled: true,
        },
        btnGhiLai: {
          enabled: false,
        },
        btnHuy: {
          enabled: false,
        },
        btnChonRack: {
          enabled: true,
        }
      },
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' }
    }
  },
  mounted() {
    //this.setDatePickerEnabled(this.isEditMode);
    //this.setNguoiQLSelectEnabled(this.controls.enable.enabled);
  },
  watch: {
    'dataRack.LOAIRACK_ID': {
      handler(newId) {
        if (!newId) {
          this.dataRack.CHIEUDAI = this.dataRack.CHIEURONG = this.dataRack.CHIEUCAO = null;
          return;
        }
        const loai = this.loaiRackSelectList.find(x => x.ID == newId);
        if (loai) {
          this.dataRack.CHIEUDAI   = loai.CHIEUDAI;
          this.dataRack.CHIEURONG  = loai.CHIEURONG;
          this.dataRack.CHIEUCAO   = loai.CHIEUCAO;
        }
      },
      immediate: true
    },

  },
  methods: {
    // Phần gọi các API
    upsertRacknThiCongRack: async function (dataRack, dataThiCongRack) {
      let result = false;
      try {
        //http://localhost:10990/thicong-rack/upsert
        let rs = await this.$root.context.post("/web-ecms/thicong-rack/upsert", {
          thiCongRack: {
            tcRackId: dataThiCongRack.THICONGRACK_ID === 0 ? null : dataThiCongRack.THICONGRACK_ID,
            phieuTCId: this.phieuThiCongId,
            noiDung: dataThiCongRack.NOIDUNG,
            ghiChu: dataThiCongRack.GHICHU,
            moTa: dataThiCongRack.MOTA
          },
          thongTinRack: {
            rackId: dataRack.RACK_ID,
            loaiRackId: dataRack.LOAIRACK_ID,
            tenRack: dataRack.TEN_RACK,
            // maTaiSan: dataRack.MA_TS,
            quyHoach: dataRack.QUYHOACH,
            vanHanh: dataRack.VANHANH,
            ccdv: dataRack.CCDV,
            suDung: dataRack.SUDUNG,
            ghiChu: dataRack.GHI_CHU,
            chuQuanId: dataRack.CHUQUAN_ID,
            phongId: dataRack.PHONG_ID,
            vitriRack: dataRack.VITRI_RACK,
            hangSXId: dataRack.HANGSX_ID,
            camera: dataRack.CAMERA ? dataRack.CAMERA : null,
            ngaySD: dataRack.NGAY_SD ? DateTimeLib.toDateDisplay(dataRack.NGAY_SD) : null,
            hinhAnh: dataRack.HINHANH ? dataRack.HINHANH : null,
          }
        });

        result = rs.data;
        console.log('result', result);
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    getChuQuanItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "CHUQUAN",
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
    getHangSXItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "HANGSX",
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

    // Phần API cho file MinIO helpers
    async getPresignedUrlFromMinio(src) {
      try {
        let url = `/web-ecms/storage/getPresignedUrl`
        let response = await this.$root.context.post(url, { fileSource: src })
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          return response.data.result
        } else return null
      } catch (error) {
        console.log(error)
        return null
      }
    },
    async uploadFileToMinio(item) {
      try {
        this.$root.showLoading(true)
        let formData = new FormData()
        formData.append('file', item)
        let url = `/web-ecms/storage/uploadFile`
        let response = await this.$root.context.post(url, formData)
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          if (response.data.status) {
            return response.data.message
          } else return null
        } else return null
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi xảy ra trong quá trình upload file!')
        return null
      } finally {
        this.$root.showLoading(false)
      }
    },
    async deleteFileFromMinio(src) {
      try {
        this.$root.showLoading(true)
        let url = `/web-ecms/storage/deleteFile`
        let response = await this.$root.context.post(url, { fileSource: src })
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          if (!response.data.status) this.$toast.error(response.data.message)
          return response.data.status
        } else return false
      } catch (error) {
        console.log(error)
        return false
      } finally {
        this.$root.showLoading(false)
      }
    },

    // Phần sự kiện upload hình ảnh
    onClickUploadIcon: function() {
      if (this.$refs.fileInputIcon) {
        this.$refs.fileInputIcon.value = null
        this.$refs.fileInputIcon.click()
      }
    },
    onRemoveIcon: function() {
      this.pendingRemoveIcon = true
      this.pendingIconFile = null
      this.pendingIconPreviewUrl = ''
      this.displayIconUrl = ''
    },
    onChangeIconFile: async function(event) {
      try {
        const files = event.target.files
        if (!files || files.length === 0) {
          return
        }
        const file = files[0]
        // Chỉ lưu tạm thời file và hiển thị preview local, không gọi API ngay
        this.pendingRemoveIcon = false
        this.pendingIconFile = file
        // Tạo URL preview tạm thời
        if (this.pendingIconPreviewUrl) {
          try {
            URL.revokeObjectURL(this.pendingIconPreviewUrl)
          } catch (e) {}
        }
        this.pendingIconPreviewUrl = URL.createObjectURL(file)
        this.displayIconUrl = this.pendingIconPreviewUrl
      } catch (e) {
        this.$root.toastError('Có lỗi khi tải ảnh')
      } finally {
        this.uploadingIcon = false
      }
    },

    clearAll() {
      // Force clear với Vue.set hoặc Object.assign
      // this.dataRack = {},
      // this.dataThiCongRack = {};
      this.$set(this, 'dataRack', {
        RACK_ID: null,
        LOAIRACK_ID: null,
        TEN_RACK: '',
        // MA_TS: '',
        QUYHOACH: null,
        VANHANH: null,
        CCDV: null,
        SUDUNG: 1,
        GHI_CHU: '',
        CHUQUAN_ID: null,
        PHONG_ID: null,
        VITRI_RACK: '',
        IDC_ID: null,
        MATSAN_ID: null,
        HANGSX_ID: null,
        CAMERA: 1,
        NGAY_SD: null,
        HINHANH: null,

      });

      this.$set(this, 'dataThiCongRack', {
        THICONGRACK_ID: null,
        NOIDUNG: '',
        GHICHU: '',
        MOTA: ''
      });
    },
    closeDialog() {
      this.clearAll();
      this.isEditMode = false;
      this.enableControls(false);
      //this.showModalLapDatRack = false;
      this.$refs.ModalLapDatRack.hide();
    },
    async dialogOpen() {
      try {
        this.$root.showLoading(true);
        this.clearAll();
        const [
          loaiRackSelectList,
          hangSXSelectList,
          ccdvSelectList,
          vanHanhSelectList,
          quyHoachSelectList,
          chuQuanSelectList,
        ] = await Promise.all([
          this.getLoaiRackItems(),
          this.getHangSXItems(),
          this.getCCDVItems(),
          this.getVanHanhItems(),
          this.getQuyHoachItems(),
          this.getChuQuanItems(),
        ]);

        this.loaiRackSelectList = loaiRackSelectList;
        this.hangSXSelectList = hangSXSelectList;
        this.ccdvSelectList = ccdvSelectList;
        this.vanHanhSelectList = vanHanhSelectList;
        this.quyHoachSelectList = quyHoachSelectList;
        this.chuQuanSelectList = chuQuanSelectList;

        this.$refs.ModalLapDatRack.show();
      } catch (e) {
        console.log('Lỗi:', e);
        this.$toast.error('Đã xảy ra lỗi khi mở dialog');
      } finally {
        this.$root.showLoading(false);
      }
    },

    // Validate form
    validateForm() {
      let errors = [];
      if (!this.dataRack.TEN_RACK) errors.push('Tên Rack không được để trống');
      if (!this.dataRack.CHUQUAN_ID)  errors.push('Sở hữu Rack không được để trống');
      if (!this.dataRack.LOAIRACK_ID) errors.push('Loại Rack không được để trống');
      if (!this.dataRack.VANHANH) errors.push('Trạng thái vận hành không được để trống');
      if (!this.dataRack.CCDV) errors.push('Trạng thái Rack CCDV không được để trống');
      if (!this.dataRack.QUYHOACH) errors.push('Trạng thái Rack theo quy hoạch không được để trống');
      if (!this.dataRack.HANGSX_ID) errors.push('Hãng sản xuất không được để trống');
      if (!this.dataRack.CAMERA) errors.push('Camera không được để trống');
      if (!this.dataRack.NGAY_SD) errors.push('Ngày sử dụng không được để trống');

      return errors;
    },
    enableControls: function (isEnabled) {
      // Khi truyền true
      if (isEnabled) {
        this.buttons.btnGhiLai.enabled = true;
        this.buttons.btnHuy.enabled = true;
        this.buttons.btnNhapMoi.enabled = false;
        this.buttons.btnChonRack.enabled = false;
      } else {
        // Khi truyền false
        this.buttons.btnGhiLai.enabled = false;
        this.buttons.btnHuy.enabled = false;
        this.buttons.btnNhapMoi.enabled = true;
        this.buttons.btnChonRack.enabled = true;
      }
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

            // 1) Xóa ảnh icon cũ nếu có
            if (this.pendingRemoveIcon && this.dataRack.HINHANH) {
              await this.deleteFileFromMinio(this.dataRack.HINHANH)
              this.dataRack.HINHANH = ''
            }
            // 2) Nếu có chọn ảnh mới
            if (this.pendingIconFile) {
              if (this.dataRack.HINHANH) {
                await this.deleteFileFromMinio(this.dataRack.HINHANH)
              }
              const uploadRs = await this.uploadFileToMinio(this.pendingIconFile)
              if (uploadRs && uploadRs.relativeUrl) {
                this.dataRack.HINHANH = uploadRs.relativeUrl
                const viewUrl = await this.getPresignedUrlFromMinio(this.dataRack.HINHANH)
                this.displayIconUrl = viewUrl || ''
              } else {
                this.$root.toastError('Tải ảnh thất bại')
                return
              }
            }
            let rs = await this.upsertRacknThiCongRack(this.dataRack, this.dataThiCongRack);
            if (rs?.STATUS === "ERROR") {
              this.$root.toastError(rs.MESSAGE || "Lỗi chưa xác định");
              return; // DỪNG ở đây
            }

            if (rs?.RACK_ID) {
              this.$root.toastSuccess("Lưu thông tin Rack thành công");
              this.$emit('rackAllocated', this.selectedRack);
              this.$parent.loadHistoryList();
              this.closeDialog();
            }
            else {
              this.$root.toastError("Lưu thông tin Rack thất bại");
            }
            this.enableControls(!rs);
            this.isEditMode = !rs;
            this.$refs.ModalLapDatRack.hide();
          } finally {
            this.$root.showLoading(false);
          }
        }
      }
    },
    btnChonRack_Click: async function() {
      this.isEditMode = false;
      this.enableControls(true);
      this.$refs.popupDanhSachRack.dialogOpen();
    },
    btnNhapMoi_Click: async function() {
      this.clearAll();
      console.log('rack id', this.dataRack.RACK_ID);
      this.isEditMode = true;
      this.enableControls(true);
    },
    btnHuy_Click: async function () {
      try {
        this.$root.showLoading(true);
        // Hủy thay đổi icon tạm thời
        this.pendingIconFile = null
        if (this.pendingIconPreviewUrl) {
          try {
            URL.revokeObjectURL(this.pendingIconPreviewUrl)
          } catch (e) {}
        }
        this.pendingIconPreviewUrl = ''
        this.pendingRemoveIcon = false
        if (this.previousIconRelative) {
          try {
            this.loadingImageUrl = true
            const url = await this.getPresignedUrlFromMinio(this.previousIconRelative)
            this.displayIconUrl = url || ''
          } catch (e) {
            this.displayIconUrl = ''
          } finally {
            this.loadingImageUrl = false
          }
        } else {
          this.displayIconUrl = ''
        }
        this.clearAll();
        this.isEditMode = false;
        this.enableControls(false);
      } finally {
        this.$root.showLoading(false);
      }
    },

    onOverlayClick: function() {
      this.clearAll();
      this.isEditMode = false;
      this.enableControls(false);
      this.$refs.ModalLapDatRack.hide();  // hoặc xử lý khác
    },


  }
}
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
.radio-item {
  width: 150px; /* 👈 chỉnh theo ý bạn, ví dụ 100px hoặc 150px */
}

.preview img {
  max-width: 150px;
  border: 1px solid #ddd;
  padding: 2px;
  display: block;
}
/* Loading state for image */
.image-loading {
  border-radius: 4px;
}

.image-loading .spinner-border-sm {
  width: 1.5rem;
  height: 1.5rem;
}

.fixed-icon-box {
  width: 100%;
  max-height: 130px;
  overflow: hidden !important;
}
</style>
