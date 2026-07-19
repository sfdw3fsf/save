<template src="./index.popup.html"></template>
<script>
    import {minLength,maxLength,numeric, required} from 'vuelidate/lib/validators'
export default {
  components: {},
  name: 'ModalLoaiDongBo',
    props: ["modalId", "data"],
  // props: {
  //   // modalShow: Boolean,
  //   // dsTonghop: Object
  // },
  computed:{

  },
    data: function () {
        return {
            dsLoaiDongBo: [],
            loaiDongBo: null,
            loaiDongBoID: null,
            selected: null,
            isCreateNew: true,
            isDisableBtnNhapMoi: false,
            isDisableBtnGhiLai: true,
            isDisableBtnXoa: true,
            modalXoa: false
        }
    },
    validations: {
        loaiDongBo: {
            required
        }
    },
    created: function () {

    },
    methods: {
        modalShow : function () {
            // let self = this
            // this.$nextTick()
            //     .then(function () {
            //         console.log(self.$refs)
            //     })
            // console.log(this.$refs)
            //console.log(document.getElementById('loaiDongBoInput'))
            this.$nextTick(function () {
                this.$refs.loaiDongBoInput.focus()
            })
            this.setDisableBtn(3)
            this.getDsLoaiDongBo()
            //this.$refs.loaiDongBoInput.focus()
            //document.getElementById('loaiDongBoInput').focus()
        },
        getDsLoaiDongBo: async function () {
            this.$root.showLoading(true)
            try {
                let rs = await this.$root.context.get(
                    '/web-ecms/danhmuc/loaidongbo/lay_ds_loaidongbo'
                )
                this.dsLoaiDongBo = rs.data
            } catch (error) {
            } finally {
                this.$root.showLoading(false)
            }
        },
        // timloaiDongBo: async function () {
        //     this.$root.showLoading(true)
        //     try {
        //         var rs = await this.$root.context.get(`/web-ecms/danhmuc/loaiDongBo/DanhSach/name?${this.loaiDongBo}`)
        //         this.dsDsLam = rs.data
        //     } catch (error) {
        //     } finally {
        //         this.$root.showLoading(false)
        //     }
        // },
        grid1_selectedRowChanged: function (row) {
            this.setDisableBtn(3)
            this.loaiDongBo = row.LOAI_DONGBO
            this.loaiDongBoID = row.LOAIDB_ID
        },
        ghiLai: async function () {
            this.$root.showLoading(true)
            try {
                if (this.isDisableBtnNhapMoi === true) {
                    let dataIns = {
                        loaiDongbo: this.loaiDongBo
                    }

                    if(this.validateData(dataIns)){
                        return
                    }
                    let res = await this.$root.context.post('/web-ecms/danhmuc/loaidongbo/tao_loaidongbo', dataIns)
                    if (res.error_code === 'BSS-00000000') {
                        //this.dsLoaiDongBo.unshift(res.data)
                        //this.$refs.grid.firstPage()
                        //this.$refs.grid.setCurrentSelectedRow(0)
                        this.getDsLoaiDongBo()
                        this.$toast.success('Thêm thành công.')
                        this.setDisableBtn(3)
                    } else {
                        this.$toast.error(res.error)
                        this.getDsLoaiDongBo()
                    }
                }
                else {
                    let dataUpd = {
                        loaiDongbo: this.loaiDongBo,
                        loaidbId: this.loaiDongBoID
                    }
                    if(this.validateData(dataUpd)){
                        return
                    }

                    let res = await this.$root.context.post('/web-ecms/danhmuc/loaidongbo/capnhat_loaidongbo', dataUpd)
                    if (res.error_code === 'BSS-00000000') {
                        this.$toast.success('Cập nhật thành công.')
                        await this.getDsLoaiDongBo()
                        this.setDisableBtn(3)
                    } else {
                        this.$toast.error(res.error)
                        this.getDsLoaiDongBo()
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
            if (this.loaiDongBoID == null || this.loaiDongBoID == ''){
                this.$toast.error('Chưa chọn loại đồng bộ.')
                return
            }
            try {
                // let dataDel = {
                //     loaidbId: this.loaiDongBoID
                // }
                let res = await this.$root.context.post(`/web-ecms/danhmuc/loaidongbo/xoa_loaidongbo?loaidbId=${this.loaiDongBoID}`)
                //console.log(res)
                if (res.error_code === 'BSS-00000000') {
                    this.$toast.success('Xóa thành công.')
                    //this.$refs.grid.currentPageIndex = Math.floor(this.dsLoaiDongBo.length/10)-1
                    await this.getDsLoaiDongBo()
                    this.setDisableBtn(1)
                } else {
                    this.$toast.error(res.message_detail)
                    //this.getDsLoaiDongBo()
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
            if (data.loaiDongbo == null || data.loaiDongbo == '') {
                errorMessage.push("Loại đồng bộ không được trống.")
            } else if (data.loaiDongbo.length > 20) {
                errorMessage.push("Loại đồng bộ không được dài quá 20 ký tự.")
            }

            if (this.isDisableBtnNhapMoi === false &&  (data.loaidbId == null || data.loaidbId == '')) {
                errorMessage.push("Chưa chọn loại đồng bộ.")
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
            this.isDisableBtnXoa = true

            switch (kieu) {
                case 1://Them moi
                    this.isDisableBtnGhiLai = false
                    this.loaiDongBo = null
                    break
                case 2://Huy
                    this.isDisableBtnNhapMoi = false
                    this.isDisableBtnXoa = false
                    this.loaiDongBo = null
                    break
                case 3://Cap nhat
                    this.isDisableBtnNhapMoi = false
                    this.isDisableBtnGhiLai = false
                    this.isDisableBtnXoa = false
                    break
            }
        },
        closeModal: function() {
            this.$bvModal.hide(this.modalId);
            this.$emit('closeModal');
        }
    },

}
</script>
<style>
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

