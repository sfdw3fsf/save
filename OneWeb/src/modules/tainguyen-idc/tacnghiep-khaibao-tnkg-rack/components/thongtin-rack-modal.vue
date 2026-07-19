<template src="./thongtin-rack-modal.html"></template>

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
      // Ảnh icon
      displayIconUrl: '',
      previousIconRelative: '',
      pendingIconFile: null,
      pendingIconPreviewUrl: '',
      pendingRemoveIcon: false,
      uploadingIcon: false,
      loadingImageUrl: false,
      displayImageUrl: '', // ảnh có thể hiển thị được

      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' }
    }
  },
  watch: {
    'dataRack.HINHANH': {
      immediate: true,
      handler(newVal) {
        if (newVal) {
          this.loadPresignedImage(newVal);
        } else {
          this.displayImageUrl = '';
        }
      },
    },
  },
  computed: {
    nguoiQuanLyText() {
      try {
        const ds = this.dataRack?.DS_NGUOIQL;
        if (!ds) return '';

        const arr = JSON.parse(ds); // chuyển chuỗi JSON → mảng
        if (!Array.isArray(arr)) return '';

        return arr.map(x => x.name).join(', ');
      } catch (e) {
        console.warn('Lỗi parse DS_NGUOIQL:', e);
        return '';
      }
    },
  },

  methods: {
    async loadPresignedImage(src) {
      try {
        const url = '/web-ecms/storage/getPresignedUrl';
        const response = await this.$root.context.post(url, { fileSource: src });
        if (response.error_code === 'BSS-00000000' && response.data?.result) {
          this.displayImageUrl = response.data.result;
        } else {
          console.warn('Không lấy được presigned URL');
          this.displayImageUrl = '';
        }
      } catch (err) {
        console.error('Lỗi khi gọi presigned URL:', err);
        this.displayImageUrl = '';
      }
    },
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
      try {
        this.$root.showLoading(true);
        let rs = await this.getChiTietRack(id);
        if (rs) {
          this.clearAll();
          this.dataRack = rs[0] || {}; // Giả sử API trả về mảng, lấy phần tử đầu tiên
        } else {
          this.$toast.error('Tải dữ liệu không thành công!');
        }
      } finally {
        this.$root.showLoading(false);
      }
    },

    clearAll() {
      this.dataRack = {};
    },
    closeDialog() {
      this.$refs.dialogChiTietRack.hide();
    },
    async dialogOpen(id) {
      try {
        this.$root.showLoading(true);
        this.clearAll();
        if(id >0){
          await this.getData(id);
        }
        this.$refs.dialogChiTietRack.show()
      } catch (e) {
        console.log('Lỗi:', e);
        this.$toast.error('Đã xảy ra lỗi khi mở dialog');
      } finally {
        this.$root.showLoading(false);
      }
    },

    onOverlayClick: function() {
      this.$refs.dialogChiTietRack.hide();
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

  }
}
</script>
<style>
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
  width: 150px;
  height: 150px;
  object-fit: cover;
  border: 1px solid #ddd;
  border-radius: 8px;
}
</style>
