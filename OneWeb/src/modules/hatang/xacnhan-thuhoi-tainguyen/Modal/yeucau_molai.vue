<template src="./yeucau_molai.html"> </template>
<script>
export default {
  props: {
    currentItem: {
      type: Object,
      default: () => ({})
    }
  },
  data: function() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
    //   currentItem : {
    //     phieu_id: null,
    //     socu_molai: "",
    //   },
      controls: {
        socu_molai: {
          invalid: false,
        }
      },
    }
  },
  watch: {
        'currentItem.socu_molai': function(value) {
          this.controls.socu_molai.invalid = value == ''
        },
  },
  methods: {
    clearAll() {
      this.currentItem.socu_molai = ''
    },
    onSave(formName) {
      if (!this.validateControls()) return

      try{
        this.$root.showLoading(true);
        this.currentItem.socu_molai = this.currentItem.socu_molai.trim();

        const rs = this.updateItem({
          id: this.currentItem.phieu_id,
          trangthai: 5,
          ykien_xacnhan: this.currentItem.ykien_xacnhan,
          ngattainguyen: this.currentItem.ngattainguyen,
          ykien_thuchien_thuhoi: this.currentItem.ykien_thuchien_thuhoi,
          trangthai_thuhoi_ip: this.currentItem.trangthai_thuhoi_ip,
          socu_thuhoi: this.currentItem.socu_molai
        })
        if (rs) {
            this.clearAll();
            this.$toast.success('Yêu cầu mở lại thành công!')
            this.$emit('refreshData');
            this.closeDialog();
        } else {
          this.$toast.error('Yêu cầu mở lại không thành công!')
        }
      } catch (e) {
        if (e.response && e.response.data && e.response.data.message_detail) {
          this.$toast.error(e.response.data.message_detail)
        } else {
          this.$toast.error('Đã xảy ra lỗi khi mở lại yêu cầu')
        }
      } finally {
        this.$root.showLoading(false);
      }
    },
    closeDialog() {
      this.clearAll();
      this.$refs.dialogYeuCauMoLai.hide();
    },
    validateControls: function() {
      let msg = ''
      if (this.currentItem.socu_molai == null || this.currentItem.socu_molai == '') {
        msg = 'Sở cứ mở lại không được để trống!'
      }
      if (msg) {
        this.$toast.error(msg)
      }
      return msg == ''
    },
    updateItem: async function(args) {
          let result = false
          try {
            let rs = await this.$root.context.post('/web-ecms/hatang/thu-hoi-tai-nguyen/xacnhan-phieu-yeucau-thuhoi', {
              id: args.id,
              trangThaiXacNhan: args.trangthai,
              xacNhan: args.ykien_xacnhan,
              ngat: args.ngattainguyen,
              thucHien: args.ykien_thuchien_thuhoi,
              thuHoiIp: args.trangthai_thuhoi_ip,
                soCuThuHoi: args.socu_thuhoi,
              soCuMoLai: args.socu_molai,
            })
            result = rs.data
          } catch (e) {
            if (!(e.response == null || e.response == undefined)) {
              if (!(e.response.data == null || e.response.data == undefined)) {
                this.$root.toastError(e.response.data.message_detail)
              }
            }
          }
          return result
        },
    async dialogOpen() {
      try {
        this.loading(true)
        this.clearAll();
        // this.setCurrentItem()
        // Kiểm tra sự tồn tại của ref và gọi phương thức show() trên dialog
        if (this.$refs.dialogYeuCauMoLai) {
          this.$refs.dialogYeuCauMoLai.show() // Hiển thị dialog
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
    }
  },
  mounted() {}
}
</script>
