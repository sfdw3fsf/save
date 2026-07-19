<template src="./Modal.html"></template>

<script>
    import {mapActions, mapState} from 'vuex'
    import {minLength,maxLength,numeric, required} from 'vuelidate/lib/validators'

export default {
    name: 'Modal',
    props: ['modalId'],
    data: function () {
        return {
            dsDsLam: [],
            nhomDslam: null,
            nhomDslamId: null,
            selected: null,
            isCreateNew: true,
            isDisableBtnNhapMoi: false,
            isDisableBtnGhiLai: true,
            isDisableBtnHuy: true,
            isDisableBtnXoa: true,
            modalXoa: false
        }
    },
    validations: {
        nhomDslam: {
            required
        }
    },
    created: function () {
        this.setDisableBtn(3)
        this.getDsNhomDslam()
    },
    methods: {
        modalShow : function () {
            // let self = this
            // this.$nextTick()
            //     .then(function () {
            //         console.log(self.$refs)
            //     })
            // console.log(this.$refs)
            //console.log(document.getElementById('nhomDslamInput'))
            this.$nextTick(function () {
                this.$refs.nhomDslamInput.focus()
            })
            //this.$refs.nhomDslamInput.focus()
            //document.getElementById('nhomDslamInput').focus()
        },
        getDsNhomDslam: async function () {
            this.$root.showLoading(true)
            try {
                let rs = await this.$root.context.get(
                    '/web-ecms/danhmuc/NhomDslam/DanhSach'
                )
                this.dsDsLam = rs.data
            } catch (error) {
            } finally {
                this.$root.showLoading(false)
            }
        },
        // timNhomDslam: async function () {
        //     this.$root.showLoading(true)
        //     try {
        //         var rs = await this.$root.context.get(`/web-ecms/danhmuc/NhomDslam/DanhSach/name?${this.nhomDslam}`)
        //         this.dsDsLam = rs.data
        //     } catch (error) {
        //     } finally {
        //         this.$root.showLoading(false)
        //     }
        // },
        grid1_selectedRowChanged: function (row) {
          this.setDisableBtn(3)
          this.nhomDslam = row.NHOM_DSLAM
          this.nhomDslamId = row.NHOM_DSLAM_ID
        },
        ghiLai: async function () {
          this.$root.showLoading(true)
          try {
              if (this.isDisableBtnNhapMoi === true) {
                  let dataIns = {
                      name: this.nhomDslam
                  }

                  if(this.validateData(dataIns)){
                      return
                  }
                  let res = await this.$root.context.post('/web-ecms/danhmuc/NhomDslam/Them', dataIns)
                  if (res.error_code === 'BSS-00000000') {
                      //this.dsDsLam.unshift(res.data)
                      //this.$refs.grid.firstPage()
                      //this.$refs.grid.setCurrentSelectedRow(0)
                      this.getDsNhomDslam()
                      this.$toast.success('Thêm thành công!')
                      this.setDisableBtn(3)
                  } else {
                      this.$toast.error(res.error)
                      this.getDsNhomDslam()
                  }
              }
              else {
                  let dataUpd = {
                      name: this.nhomDslam,
                      id: this.nhomDslamId
                  }
                  if(this.validateData(dataUpd)){
                      return
                  }

                  let res = await this.$root.context.post('/web-ecms/danhmuc/NhomDslam/CapNhat', dataUpd)
                  if (res.error_code === 'BSS-00000000') {
                      this.$toast.success('Cập nhật thành công!')
                      await this.getDsNhomDslam()
                      this.setDisableBtn(3)
                  } else {
                      this.$toast.error(res.error)
                      this.getDsNhomDslam()
                  }
              }
          } catch (error) {
          } finally {
            this.$root.showLoading(false)
          }
        },
        xoaClick() {
            if (!this.isDisableBtnXoa) {
                this.modalXoa = !this.modalXoa;
            }
        },
        xacNhanXoa: async function () {
          this.$root.showLoading(true)
            if (this.nhomDslamId == null || this.nhomDslamId == ''){
                this.$toast.error('Chưa chọn nhóm dslam')
                return
            }
            try {
                let res = await this.$root.context.post(`/web-ecms/danhmuc/NhomDslam/Xoa/${this.nhomDslamId}`)
                //console.log(res)
                if (res.error_code === 'BSS-00000000') {
                    this.$toast.success('Xóa thành công!')
                    //this.$refs.grid.currentPageIndex = Math.floor(this.dsDsLam.length/10)-1
                    await this.getDsNhomDslam()
                    this.setDisableBtn(1)
                } else {
                    this.$toast.error(res.message_detail)
                    this.getDsNhomDslam()
                }
            } catch (error) {
                this.$toast.error(error.response.data.message_detail)
            } finally {
              this.$root.showLoading(false)
            }
        },
        validateData(data){
            this.$v.$touch();
            let errorMessage = []
            if (data.name == null || data.name == '') {
                errorMessage.push("Nhóm dslam không được trống.")
            } else if (data.name.length > 80) {
                errorMessage.push("Nhóm dslam không được dài quá 80 ký tự.")
            }

            if (this.isDisableBtnNhapMoi === false &&  (data.id == null || data.id == '')) {
                errorMessage.push("Chưa chọn nhóm dslam.")
            }

            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join('\n'))
                return true
            }else {
                return false
            }
        },

        setDisableBtn: function (kieu) {
            this.isDisableBtnNhapMoi = true
            this.isDisableBtnGhiLai = true
            this.isDisableBtnHuy = true
            this.isDisableBtnXoa = true

            switch (kieu) {
                case 1://Them moi
                    this.isDisableBtnGhiLai = false
                    this.isDisableBtnHuy = false
                    this.nhomDslam = null
                    break
                case 2://Huy
                    this.isDisableBtnNhapMoi = false
                    this.isDisableBtnXoa = false
                    this.nhomDslam = null
                    break
                case 3://Cap nhat
                    this.isDisableBtnNhapMoi = false
                    this.isDisableBtnGhiLai = false
                    this.isDisableBtnHuy = false
                    this.isDisableBtnXoa = false
                    break
            }
        }
    },

}
</script>
<style>
  div#popupTCTTTB___BV_modal_outer_ {
    z-index: 10 !important;
    margin-top: 100px;
  }
  .dashboard-header {
    z-index: 9 !important;
  }
  .disabled a {
    pointer-events: none;
    cursor: default;
    color: #6c757d !important;
  }

  .disabled a:hover {
    cursor: default;
    color: #6c757d;
  }
</style>
