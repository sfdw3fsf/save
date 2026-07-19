export default {
  name: 'DialogThietBi',
  props: {
    idc_id: {
      required: true
    },
    fromHTM: {
      type: Boolean,
      default: false
    }
  },
  data: function () {
    return {
      thietbi_hinhthanh_hatang_list: [],
      loai_hatang: '',
      colsTable: [
                        {fieldName: 'MATHIETBI', headerText: 'Mã thiết bị', textAlign: 'Left'},
                        {fieldName: 'TEN', headerText: 'Tên thiết bị', textAlign: 'Left'},
                        {fieldName: 'KYHIEU', headerText: 'Ký hiệu TB', textAlign: 'Left'},
                        {fieldName: 'LOAITHIETBI_TEN', headerText: 'Loại thiết bị', textAlign: 'Left'},
                        {fieldName: 'SERIAL_NUMBER', headerText: 'Serial Number', textAlign: 'Left'},
                        {fieldName: 'MODEL', headerText: 'Model thiết bị', textAlign: 'Left'},
                        {fieldName: 'PART_NUMBER', headerText: 'Part Number', textAlign: 'Left'},
                        {fieldName: 'HIEU_LUC', headerText: 'Hiệu lực', textAlign: 'Left'},
                        ],
                        colsTableHTM: [
                        {fieldName: 'MATHIETBI', headerText: 'Mã thiết bị', textAlign: 'Left'},
                        {fieldName: 'TEN', headerText: 'Tên thiết bị', textAlign: 'Left'},
                        {fieldName: 'KYHIEU', headerText: 'Ký hiệu TB', textAlign: 'Left'},
                        {fieldName: 'DS_IP', headerText: 'IP Quản trị', textAlign: 'Left'},
                        {fieldName: 'LOAITHIETBI_TEN', headerText: 'Loại thiết bị', textAlign: 'Left'},
                        {fieldName: 'LOAIVAITRO_TEN', headerText: 'Loại vai trò', textAlign: 'Left'},
                        {fieldName: 'SERIAL_NUMBER', headerText: 'Serial Number', textAlign: 'Left'},
                        {fieldName: 'MODEL', headerText: 'Model thiết bị', textAlign: 'Left'},
                        {fieldName: 'PART_NUMBER', headerText: 'Part Number', textAlign: 'Left'},
                        {fieldName: 'HIEU_LUC', headerText: 'Hiệu lực', textAlign: 'Left'},
                        ]
    }
  },
  computed: {
    dialogRef: function() {
      return this.$refs.popupChonThietBiHinhThanh;
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
    apiListThietBiHinhThanh: async function (idc_id, loai_hatang, old_thietbi_id) {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/kho-hatang-backup/thietbi-hinhthanh-khohatang",
          {
            idc_id: idc_id,
            hatang: loai_hatang,
            old_thietbi_ids: old_thietbi_id.join(',')
          });
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    dialogOpen: async function(loai_hatang, old_thietbi_id_list) {
          try {
            this.loading(true) // Bắt đầu trạng thái loading
            // Kiểm tra sự tồn tại của ref và gọi phương thức show() trên dialog
            if (this.dialogRef) {
              this.dialogRef.show() // Hiển thị dialog
              this.loai_hatang = loai_hatang;
              this.thietbi_hinhthanh_hatang_list = await this.apiListThietBiHinhThanh(this.idc_id, loai_hatang, old_thietbi_id_list);
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
            this.loading(false) // Tắt trạng thái loading
          }
    },
    hideModal: function() {
      this.dialogRef.hide()
    },
    xacNhanThemThietBi: function() {
      let items = this.$refs.grdItems.getSelectedRecords();
      let result = {
         data: items,
         loai_hatang: this.loai_hatang
      }
      this.$emit('output-data', result);
    }
  }
}
