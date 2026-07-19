<template src="./index.popup.html"></template>
<script>
    import {minLength,maxLength,numeric, required} from 'vuelidate/lib/validators'
    export default {
        components: {},
        name: 'ModalDMDongBo',
        props: ["modalId", "data"],
        // props: {
        //   // modalShow: Boolean,
        //   // dsTonghop: Object
        // },
        computed:{

        },
        data: function () {
            return {
                dsDongBo: [],
                dongBo: null,
                dongBoID: null,
                selected: null,
                isCreateNew: true,
                isDisableBtnNhapMoi: false,
                isDisableBtnGhiLai: true,
                isDisableBtnXoa: true,
                modalXoa: false
            }
        },
        validations: {
            dongBo: {
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
                //console.log(document.getElementById('dongBoInput'))
                this.$nextTick(function () {
                    this.$refs.dongBoInput.focus()
                })
                this.setDisableBtn(3)
                this.getdsDongBo()
                //this.$refs.dongBoInput.focus()
                //document.getElementById('dongBoInput').focus()
            },
            getdsDongBo: async function () {
                this.$root.showLoading(true)
                try {
                    let rs = await this.$root.context.get(
                        '/web-ecms/danhmuc/list-dmdongbo'
                    )
                    this.dsDongBo = rs.data
                } catch (error) {
                } finally {
                    this.$root.showLoading(false)
                }
            },
            // timdongBo: async function () {
            //     this.$root.showLoading(true)
            //     try {
            //         var rs = await this.$root.context.get(`/web-ecms/danhmuc/dongBo/DanhSach/name?${this.dongBo}`)
            //         this.dsDsLam = rs.data
            //     } catch (error) {
            //     } finally {
            //         this.$root.showLoading(false)
            //     }
            // },
            grid1_selectedRowChanged: function (row) {
                this.setDisableBtn(3)
                this.dongBo = row.DONGBO
                this.dongBoID = row.DONGBO_ID
            },
            ghiLai: async function () {
                this.$root.showLoading(true)
                try {
                    if (this.isDisableBtnNhapMoi === true) {
                        let dataIns = {
                            dong_bo: this.dongBo
                        }

                        if(this.validateData(dataIns)){
                            return
                        }
                        let res = await this.$root.context.post('/web-ecms/danhmuc/insert-dmdongbo', dataIns)
                        if (res.error_code === 'BSS-00000000') {
                            //this.dsDongBo.unshift(res.data)
                            //this.$refs.grid.firstPage()
                            //this.$refs.grid.setCurrentSelectedRow(0)
                            this.getdsDongBo()
                            this.$toast.success('Thêm thành công.')
                            this.setDisableBtn(3)
                        } else {
                            this.$toast.error(res.error)
                            this.getdsDongBo()
                        }
                    }
                    else {
                        let dataUpd = {
                            dong_bo: this.dongBo,
                            dong_bo_id: this.dongBoID
                        }
                        if(this.validateData(dataUpd)){
                            return
                        }

                        let res = await this.$root.context.post(`/web-ecms/danhmuc/update-dmdongbo/${this.dongBoID}`, dataUpd)
                        if (res.error_code === 'BSS-00000000') {
                            this.$toast.success('Cập nhật thành công.')
                            await this.getdsDongBo()
                            this.setDisableBtn(3)
                        } else {
                            this.$toast.error(res.error)
                            this.getdsDongBo()
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
                if (this.dongBoID == null || this.dongBoID == ''){
                    this.$toast.error('Chưa chọn đồng bộ.')
                    return
                }
                try {
                    // let dataDel = {
                    //     loaidbId: this.dongBoID
                    // }
                    let res = await this.$root.context.post(`/web-ecms/danhmuc/delete-dmdongbo/${this.dongBoID}`)
                    //console.log(res)
                    if (res.error_code === 'BSS-00000000') {
                        this.$toast.success('Xóa thành công.')
                        //this.$refs.grid.currentPageIndex = Math.floor(this.dsDongBo.length/10)-1
                        await this.getdsDongBo()
                        this.setDisableBtn(1)
                    } else {
                        this.$toast.error(res.message_detail)
                        //this.getdsDongBo()
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
                if (data.dong_bo == null || data.dong_bo == '') {
                    errorMessage.push("Tên đồng bộ không được trống.")
                } else if (data.dong_bo.length > 50) {
                    errorMessage.push("Tên đồng bộ không được dài quá 50 ký tự.")
                }

                if (this.isDisableBtnNhapMoi === false &&  (data.dong_bo_id == null || data.dong_bo_id == '')) {
                    errorMessage.push("Chưa chọn đồng bộ.")
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
                        this.dongBo = null
                        break
                    case 2://Huy
                        this.isDisableBtnNhapMoi = false
                        this.isDisableBtnXoa = false
                        this.dongBo = null
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

