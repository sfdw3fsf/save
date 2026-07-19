<template src="./index.html"></template>

<script>
export default {
  props: {
    // Add proper prop definitions to fix the dataSource warning
    backupId: {
      type: String,
      default: ''
    },
    dataSource: {
      type: Array,
      default: () => [] // Use factory function for Array/Object props
    }
  },
  data: function () {
    return {
      thietbi_hinhthanh_hatang_list: [],
      loai_hatang: '',
      search_param: '', // Add missing search_param
    }
  },
  computed: {
    dialogRef: function() {
      return this.$refs.popup_them_mayao;
    }
  },
  watch: {},
  created: async function () {
    try {
      this.$root.showLoading(true)
      
    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
    apiListThietBiHinhThanh: async function () {
      let result = [];
      try {
        // Fix: use this.search_param instead of undefined search_param
        let rs = await this.$root.context.get("/web-ecms/hatang/tainguyen-backup/danhsach-mayao?nameSearch=" + this.search_param + "&backupId=" + this.backupId );
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response?.data.message_detail || e.response?.data.message || 'Lỗi khi lấy danh sách thiết bị hình thành hạ tầng');
      }
      return result;
    },
    dialogOpen: async function(loai_hatang, old_thietbi_id_list) {
      try {
        // Fix: use this.$root.showLoading() instead of this.loading()
        this.$root.showLoading(true) 
        if (this.dialogRef) {
          this.dialogRef.show() 
          this.loai_hatang = loai_hatang;
          this.thietbi_hinhthanh_hatang_list = await this.apiListThietBiHinhThanh();
        } else {
          this.$toast.error('Dialog không tồn tại hoặc không được tham chiếu đúng')
        }
      } catch (e) {
        // Hiển thị thông báo lỗi nếu có vấn đề
        console.log('loi', e)
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message)
        } else {
          this.$toast.error('Đã xảy ra lỗi khi mở dialog')
        }
      } finally {
        // Fix: use this.$root.showLoading() instead of this.loading()
        this.$root.showLoading(false) // Tắt trạng thái loading
      }
    },
    hideModal: function() {
      this.dialogRef.hide()
    },
    xacNhanThemMayAo: function() {
      let items = this.$refs.grdItems.getSelectedRecords();
      this.$root.context.post("/web-ecms/hatang/tainguyen-backup/them-mayao-backup", {
        backupId: this.backupId,
        mayAoIds: JSON.stringify(items.map(item => item.id))
      }).then(() => {
        this.$toast.success('Thêm máy ảo thành công');
        this.hideModal();
        this.$emit('reloadData'); // Emit event to parent component to reload data
      }).catch(e => {
        this.$toast.error(e.response?.data.message_detail || e.response?.data.message || 'Lỗi khi thêm máy ảo');
      });
    },
    onTimKiem: async function() {
      try {
        this.$root.showLoading(true);
        this.thietbi_hinhthanh_hatang_list = await this.apiListThietBiHinhThanh();
      } catch (e) {
        this.$toast.error(e.response?.data.message_detail || e.response?.data.message || 'Lỗi khi tìm kiếm thiết bị hình thành hạ tầng');
      } finally {
        this.$root.showLoading(false);
      }
    }
  }
}
</script>
