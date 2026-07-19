<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import api from './api';

export default {
  components: {},
  data: function () {
    return {
      dataItems: [],
      dsSelected: [],
      currentItem: {
        ocamId: null,
        thanhNguonId: null,
        ten: null,
        moTa: null,
        trangThaiId: null,
        trangThaiHdId: null,
        thietBiId: null,
        ghiChu: null,
        suDung: 1,
        loaiOcamId: null,
      },
      isEditMode: false,
      trangThaiOptions: [
        { id: 1, text: 'Hoạt động' },
        { id: 2, text: 'Hỏng' }
      ],
      trangThaiHdOptions: [
        { id: 1, text: 'Đã cắm nguồn' },
        { id: 2, text: 'Chưa cắm nguồn' }
      ],
      thietBiOptions: [],
      loaiOcamOptions: [],
    };
  },
  computed: {
    gridItems: function () {
      return this.$refs.gridItems;
    },
    controlsState() {
      return {
        selectTrangThai: { 
          enabled: this.isEditMode, 
          invalid: this.isEditMode && !this.currentItem.trangThaiId 
        },
        selectTrangThaiHd: { 
          enabled: this.isEditMode, 
          invalid: this.isEditMode && !this.currentItem.trangThaiHdId 
        },
        txtGhiChu: { 
          enabled: this.isEditMode, 
          invalid: false
        },
      };
    },
    
    buttonsState() {
      return {
        btnGhiLai: {
          enabled: this.isEditMode,
        },
        btnSua: {
          enabled: !this.isEditMode && this.currentItem.ocamId > 0,
        },
      };
    },
    
    hasValidationErrors() {
      return (this.controlsState.selectTrangThai.enabled && this.controlsState.selectTrangThai.invalid) || 
             (this.controlsState.selectTrangThaiHd.enabled && this.controlsState.selectTrangThaiHd.invalid);
    }
  },
  
  created: async function () {
    try {
      this.$root.showLoading(true);
      await this.loadInitialData();
    } finally {
      this.$root.showLoading(false);
    }
  },
  
  methods: {
    loadInitialData: async function () {
      try {
        this.$root.showLoading(true);
        this.dataItems = await this.getItems();
        if (this.dataItems && this.dataItems.length > 0) {
          this.setCurrentItem(this.dataItems[0]);
          this.$nextTick(() => {
            if (this.gridItems) {
              this.gridItems.selectRow(0);
            }
          });
        } else {
          this.setCurrentItemDefault();
        }
      } catch (error) {
        console.error('Error loading initial data:', error);
        this.$root.toastError('Không thể tải dữ liệu ban đầu');
      } finally {
        this.$root.showLoading(false);
      }
    },
    
    getItems: async function () {
      try {
        this.$root.showLoading(true);
        const rs = await api.getList(this.$root.context);
        return rs.data || [];
      } catch (error) {
        console.error('Error getting danh sach ocam:', error);
        this.$root.toastError('Không thể tải danh sách ổ cắm');
        return [];
      } finally {
        this.$root.showLoading(false);
      }
    },
    
    updateItem: async function (args) {
      try {
        this.$root.showLoading(true);
        const rs = await api.update(this.$root.context, args);
        return rs.data.item === 1 ? true : false;
      } catch(e) {
        const errorMessage = e.response?.data?.message_detail || 'Có lỗi xảy ra khi cập nhật dữ liệu';
        this.$root.toastError(errorMessage);
        return false;
      } finally {
        this.$root.showLoading(false);
      }
    },
    
    onSelectRowCheckbox(itemsCheck) {
      this.dsSelected = itemsCheck;
    },
    
    setCurrentItem: function (item) {
      this.currentItem = {
        ocamId: item.OCAM_ID || item.ocamId,
        thanhNguonId: item.THANHNGUON_ID || item.thanhNguonId,
        tenThanhNguon: item.TEN_THANHNGUON || item.tenThanhNguon,
        ten: item.TEN || item.ten,
        moTa: item.MOTA || item.moTa,
        trangThaiId: item.TRANGTHAI_ID || item.trangThaiId,
        trangThaiHdId: item.TRANGTHAI_HD_ID || item.trangThaiHdId,
        thietBiId: item.THIETBI_ID || item.thietBiId,
        ghiChu: item.GHICHU || item.ghiChu,
        suDung: item.SUDUNG !== undefined ? item.SUDUNG : 1,
        loaiOcamId: item.LOAIOCAM_ID || item.loaiOcamId,
      };
    },
    
    setCurrentItemDefault: function () {
      this.currentItem = {
        ocamId: null,
        thanhNguonId: null,
        tenThanhNguon: "",
        ten: "",
        moTa: "",
        trangThaiId: null,
        trangThaiHdId: null,
        thietBiId: null,
        ghiChu: "",
        suDung: 1,
        loaiOcamId: null,
      };
    },
    
    validateControls: function () {
      if (this.hasValidationErrors) {
        const messages = [];
        if (this.controlsState.selectTrangThai.invalid) {
          messages.push("Vui lòng chọn trạng thái ổ cắm.");
        }
        if (this.controlsState.selectTrangThaiHd.invalid) {
          messages.push("Vui lòng chọn trạng thái cắm nguồn.");
        }
        
        this.$root.toastError(messages.join(' '));
        return false;
      }
      return true;
    },
    
    gridItems_RowSelected: async function (args) {
      this.setCurrentItem(args.data);
    },
    
    gridItems_RowDeselected: async function (args) {
      const items = this.gridItems.getSelectedRecords();
      if (items && items.length > 0) {
        this.setCurrentItem(items[0]);
      } else {
        this.setCurrentItemDefault();
      }
    },
    
    btnSua_Click: function () {
      this.isEditMode = true;
    },
    
    btnGhiLai_Click: async function (args) {
      if (!this.validateControls()) return;
      
      const flag = await this.$confirm(
        "Có chắc bạn muốn lưu lại thông tin ổ cắm đang chọn?",
        "Xác nhận thực hiện",
        {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
          type: "info",
          dangerouslyUseHTMLString: true,
        }
      ).then(() => true).catch(() => false);
        
      if (!flag) return;
      
      try {
        this.$root.showLoading(true);
        const success = await this.updateItem(this.currentItem);
        
        if (success) {
          const isNewItem = !this.currentItem.ocamId;
          const message = isNewItem ? "Thêm ổ cắm thành công" : "Cập nhật ổ cắm thành công";
          this.$root.toastSuccess(message);
          
          await this.refreshDataAfterSave(isNewItem);
          this.isEditMode = false;
        } else {
          const message = !this.currentItem.ocamId ? "Thêm ổ cắm thất bại" : "Cập nhật ổ cắm thất bại";
          this.$root.toastError(message);
        }
      } finally {
        this.$root.showLoading(false);
      }
    },
    
    refreshDataAfterSave: async function (isNewItem) {
      try {
        this.$root.showLoading(true);
        this.dataItems = await this.getItems();
      
        if (isNewItem) {
          if (this.dataItems && this.dataItems.length > 0) {
            const latestItem = this.dataItems.reduce((latest, current) => {
              return (current.OCAM_ID > latest.OCAM_ID) ? current : latest;
            });
            this.setCurrentItem(latestItem);
            this.$nextTick(() => {
              if (this.gridItems) {
                const rowIndex = this.dataItems.findIndex(item => item.OCAM_ID === latestItem.OCAM_ID);
                if (rowIndex >= 0) {
                  this.gridItems.selectRow(rowIndex);
                }
              }
            });
          }
        } else {
          const updatedItem = this.dataItems.find(item => item.OCAM_ID === this.currentItem.ocamId);
          if (updatedItem) {
            this.setCurrentItem(updatedItem);
            this.$nextTick(() => {
              if (this.gridItems) {
                const rowIndex = this.dataItems.findIndex(item => item.OCAM_ID === updatedItem.OCAM_ID);
                if (rowIndex >= 0) {
                  this.gridItems.selectRow(rowIndex);
                }
              }
            });
          }
        }
      } finally {
        this.$root.showLoading(false);
      }
    },
  },
};
</script>
<style>
.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type="checkbox"]:checked:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type="checkbox"]:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
}

/* CSS cho validation error */
.error-input {
  border: 1px solid #f57f6c !important;
  animation-name: bounce;
  animation-duration: 0.5s;
  animation-delay: 0.25s;
}

@keyframes bounce {
  0% {
    transform: translateX(0px);
    timing-function: ease-in;
  }
  37% {
    transform: translateX(5px);
    timing-function: ease-out;
  }
  55% {
    transform: translateX(-5px);
    timing-function: ease-in;
  }
  73% {
    transform: translateX(4px);
    timing-function: ease-out;
  }
  82% {
    transform: translateX(-4px);
    timing-function: ease-in;
  }
  91% {
    transform: translateX(2px);
    timing-function: ease-out;
  }
  96% {
    transform: translateX(-2px);
    timing-function: ease-in;
  }
  100% {
    transform: translateX(0px);
    timing-function: ease-in;
  }
}
</style>
