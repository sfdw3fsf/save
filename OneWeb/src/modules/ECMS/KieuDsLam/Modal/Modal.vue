<template src="./Modal.html"></template>

<script>

export default {
  name: 'Modal',
  computed: {
  },
  destroyed () {
  },
  methods: {
    Clear () {
      this.kieuDslamId = 0
      this.kieuDslam = ''
    },
    focusMyElement(){
      this.SetButton(1)
      this.getDsKieuDslam()
    },
    SetButton (kieu) {
      this.isDisableBtnNhapMoi = true
      this.isDisableBtnGhiLai = true
      this.isDisableBtnHuy = true
      this.isDisableBtnXoa = true
      if (kieu === -1) {
        this.isDisableBtnGhiLai = false
        this.isDisableBtnHuy = false
      }
      if (kieu === 0) {
        this.$refs.txtKieuDslam.focus()
       // this.$nextTick(() => this.$refs.txtKieuDslam.focus())
        this.isDisableBtnNhapMoi = false
        this.Clear()
      }
      if (kieu === 1) {
        this.$refs.txtKieuDslam.focus()
      //  this.$nextTick(() => this.$refs.txtKieuDslam.focus())
        this.isDisableBtnGhiLai = false
        this.isDisableBtnHuy = false
        this.Clear()
      }
      if (kieu === 2) {
        this.isDisableBtnNhapMoi = false
        this.isDisableBtnXoa = false
        this.Clear()
      }
      if (kieu === 3) {
        this.isDisableBtnNhapMoi = false
        this.isDisableBtnXoa = false
        this.isDisableBtnGhiLai = false
        this.isDisableBtnHuy = false
      }
    },
    tsbtnNhapMoi_Click () {
      this.SetButton(1)
    },
    tsbtnHuyBo_Click () { this.SetButton(2) },
    getDsKieuDslam: async function () {
      this.$root.showLoading(true)
      try {
        var rs = await this.$root.context.get(
          '/web-ecms/kieuDslam/danhsach'
        )
        this.dsDsLam = rs.data
      } catch (error) {
        this.dsDsLam = []
      } finally {
        this.$root.showLoading(false)
      }
    },
    grid1_selectedRowChanged: function (row) {
      if (row) {
        this.kieuDslam = row.KIEU_DSLAM
        this.kieuDslamId = row.KIEUDSLAM_ID
        this.SetButton(3)
      }
    },
    themMoi: async function () {
      this.$root.showLoading(true)
      try {
        if (this.isDisableBtnNhapMoi) {
          if (this.kieuDslam == null || this.kieuDslam == '') {
            this.$root.$toast.error('Trường kiểu dslam không được để trống')
            this.$refs.txtKieuDslam.focus()
          //  this.$nextTick(() => this.$refs.txtKieuDslam.focus())
            return
          }
          if (this.kieuDslam.length > 100) {
            this.$root.$toast.error('Trường kiểu dslam không được quá 100 ký tự')
            this.$refs.txtKieuDslam.focus()
           // this.$nextTick(() => this.$refs.txtKieuDslam.focus())
            return
          }
          let dataIns = {
            kieuDslam: this.kieuDslam.replace(/ +(?= )/g, '').trimEnd().trimStart()
          }
          let rs = await this.$root.context.post('/web-ecms/kieuDslam/themKieuDsLam', dataIns);
          console.log(rs)
          if(rs.data !=null){
            this.$toast.success('Thêm mới thành công!')
            this.getDsKieuDslam()
            this.SetButton(3)
          }else{
            this.$toast.error(res.message_detail)
            this.getDsKieuDslam()
            this.SetButton(3)
          }
            // .then((res) => {
            //   if (res.error_code === 'BSS-00000000') {
            //     this.$toast.success('Thêm thành công!')
            //     this.getDsKieuDslam()
            //     this.SetButton(3)
            //   } else {
            //     this.$toast.error(res.message_detail)
            //     this.getDsKieuDslam()
            //     this.SetButton(3)
            //   }
            //   this.$root.showLoading(false)
            // })
        } else {
          if (this.kieuDslam == null || this.kieuDslam == '') {
            this.$root.$toast.error('Trường kiểu dslam không được để trống')
            this.$refs.txtKieuDslam.focus()
            //this.$nextTick(() => this.$refs.txtKieuDslam.focus())
            return
          }
          if (this.kieuDslam.length > 100) {
            this.$root.$toast.error('Trường kiểu dslam không được quá 100 ký tự')
            this.$refs.txtKieuDslam.focus()
           // this.$nextTick(() => this.$refs.txtKieuDslam.focus())
            return
          }
          let dataUpd = {
            kieuDslam: this.kieuDslam,
            kieuDslamId: this.kieuDslamId
          }
          let  rs = await this.$root.context.post('/web-ecms/kieuDslam/capNhatKieuDsLam', dataUpd)
          console.log('rs',rs)
          if(rs.data !=null){
            this.$toast.success('Cập nhật thành công!')
            this.getDsKieuDslam()
            this.SetButton(3)
          }else{
            this.$toast.error(res.message_detail)
            this.getDsKieuDslam()
            this.SetButton(3)
          }
            // .then((res) => {
            //   if (res.error_code === 'BSS-00000000') {
            //     this.$toast.success('Cập nhật thành công!')
            //     this.getDsKieuDslam()
            //     this.SetButton(3)
            //   } else {
            //     this.$toast.error(res.error)
            //     this.getDsKieuDslam()
            //     this.SetButton(3)
            //   }
            //   this.$root.showLoading(false)
            // })
        }
      } catch (error) {
        console.log(error)
        let message = error.response.data.message_detail ||"";
        if(this.isDisableBtnNhapMoi)
        this.$toast.error("Thêm mới thất bại:\n"+message);
        else
        this.$toast.error("Cập nhật thất bại:\n"+message);
        this.getDsKieuDslam()
        this.SetButton(3)
      } finally {
        this.$refs.txtKieuDslam.focus()
        this.$root.showLoading(false)
      }
    },
    cancelForm: async function () {
      await this.getDsKieuDslam()
    },
    xoa: async function () {
      if (this.isDisableBtnXoa) {
        return
      }
      if (this.kieuDslamId === null || this.kieuDslamId === 0) {
        this.$toast.error('Bạn chưa chọn dslam nào để xóa.')
        return
      }
      if (!this.isDisableBtnXoa) {
        this.modalXoa = !this.modalXoa
      }
    },
    async xacNhanXoaKieuDsLam () {
      try {
        var xoa = await this.$root.context.post(`/web-ecms/kieuDslam/xoaKieuDsLam?id=${this.kieuDslamId}`)
          .then((res) => {
            if (res.error_code === 'BSS-00000000') {
              this.$toast.success('Xóa thành công!')
              this.getDsKieuDslam()
              this.SetButton(1)
            } else {
              this.$toast.error(res.error)
              this.getDsKieuDslam()
              this.SetButton(1)
            }
            this.$root.showLoading(false)
          })
      } catch (err) {
        let message = err.response.data.message
        let message_detail = err.response.data.message_detail
        if (message_detail !== null) {
          this.$toast.error(message_detail)
        } else if (message !== null) {
          this.$toast.error(message)
        }
      } finally {
        this.loading(false)
      }
    },
    setDisableBtn: function (isNhapmoi, isGhilai, isHuy, isXoa) {
      this.isDisableBtnNhapMoi = isNhapmoi
      this.isDisableBtnGhiLai = isGhilai
      this.isDisableBtnHuy = isHuy
      this.isDisableBtnXoa = isXoa
    }
  },

  created: async function () {
   // await this.getDsKieuDslam()
  },
  mounted () {
   // this.SetButton(1)
  },
  data: function () {
    return {
      dsDsLam: [],
      kieuDslam: null,
      kieuDslamId: null,
      selected: null,
      isDisableBtnNhapMoi: false,
      isDisableBtnGhiLai: true,
      isDisableBtnHuy: false,
      isDisableBtnXoa: true,
      modalXoa: false
    }
  }
}
</script>
<style>
.dashboard-header {
  z-index: 9 !important;
}
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
