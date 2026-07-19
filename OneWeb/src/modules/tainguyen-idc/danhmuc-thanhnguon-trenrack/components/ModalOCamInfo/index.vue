<template src="./template.html"></template>

<script>
import api from '../../api';

export default {
  name: 'ModalOCamInfo',
  components: {},
  data() {
    return {
      visible: false,
      oCamData: [],
      thanhNguonId: null,
      thanhNguonTen: '',
      resolvePromise: null,
      rejectPromise: null,
      LOAI_OCAM_MAPPING: {
        2: "Ổ IEC C13",
        3: "Ổ IEC C19"
      },
      TRANGTHAI_MAPPING: {
        1: "Hoạt động"
      },
      TRANGTHAI_HD_MAPPING: {
        1: "Đã cắm nguồn"
      }
    }
  },
  methods: {
    showModal(thanhNguonId, thanhNguonTen) {
      this.thanhNguonId = thanhNguonId;
      this.thanhNguonTen = thanhNguonTen;
      this.visible = true;
      this.loadOCamData();
      
      return new Promise((resolve, reject) => {
        this.resolvePromise = resolve;
        this.rejectPromise = reject;
      });
    },
    
    async loadOCamData() {
      try {
        this.$root.showLoading(true);
        const response = await api.getOCamInfo(this.$root.context, this.thanhNguonId);
        this.oCamData = (response.data || []).map(item => ({
          ...item,
          LOAIOCAM_TEXT: this.getLoaiOCamText(item.LOAIOCAM_ID),
          TRANGTHAI_TEXT: this.getTrangThaiText(item.TRANGTHAI_ID),
          TRANGTHAI_HD_TEXT: this.getTrangThaiHoatDongText(item.TRANGTHAI_HD_ID)
        }));
      } catch (error) {
        console.error('Lỗi khi tải dữ liệu ổ cắm:', error);
        this.$root.toastError('Có lỗi khi tải dữ liệu ổ cắm');
        this.oCamData = [];
      } finally {
        this.$root.showLoading(false);
      }
    },
    
    closeModal() {
      this.visible = false;
      this.oCamData = [];
      this.thanhNguonId = null;
      this.thanhNguonTen = '';
      
      if (this.resolvePromise) {
        this.resolvePromise({ success: true });
      }
    },
    
    // Method chung để lấy text từ mapping
    getTextFromMapping(mapping, value, defaultValue = '') {
      return mapping[value] || defaultValue;
    },
    
    getColumnTrangThaiTemplate() {
      return () => ({
        template: {
          template: `<span>{{ getTrangThaiText(data.TRANGTHAI_ID) }}</span>`,
          methods: {
            getTrangThaiText: (value) => this.getTrangThaiText(value)
          },
        },
      });
    },
    
    getColumnTrangThaiHoatDongTemplate() {
      return () => ({
        template: {
          template: `<span>{{ getTrangThaiHoatDongText(data.TRANGTHAI_HD_ID) }}</span>`,
          methods: {
            getTrangThaiHoatDongText: (value) => this.getTrangThaiHoatDongText(value)
          },
        },
      });
    },

    getColumnLoaiOCamTemplate() {
      return () => ({
        template: {
          template: `<span>{{ getLoaiOCamText(data.LOAIOCAM_ID) }}</span>`,
          methods: {
            getLoaiOCamText: (value) => this.getLoaiOCamText(value)
          },
        },
      });
    },

    getLoaiOCamText(value) {
      return this.getTextFromMapping(this.LOAI_OCAM_MAPPING, value, "Ổ khác");
    },

    getTrangThaiText(value) {
      return this.getTextFromMapping(this.TRANGTHAI_MAPPING, value, "Hỏng");
    },

    getTrangThaiHoatDongText(value) {
      return this.getTextFromMapping(this.TRANGTHAI_HD_MAPPING, value, "Chưa cắm nguồn");
    }
  }
};
</script>
