<template src="./index.popup.html"></template>
<script>
    import {minLength,maxLength,numeric, required} from 'vuelidate/lib/validators'
    export default {
        components: {},
        name: 'ModalDMring',
        props: ["modalId", "data"],
        // props: {
        //   // modalShow: Boolean,
        //   // dsTonghop: Object
        // },
        computed:{

        },
        data: function () {
            return {
                dsRing: [],
                ring: null,
                ringID: null,
                ghiChu: null,
                selected: null,
                isCreateNew: true,
                isDisableBtnNhapMoi: false,
                isDisableBtnGhiLai: true,
                isDisableBtnXoa: true,
                modalXoa: false
            }
        },
        validations: {
            ring: {
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
                //console.log(document.getElementById('ringInput'))
                this.$nextTick(function () {
                    this.$refs.ringInput.focus()
                })
                this.setDisableBtn(3)
                this.getdsRing()
                //this.$refs.ringInput.focus()
                //document.getElementById('ringInput').focus()
            },
            getdsRing: async function () {
                this.$root.showLoading(true)
                try {
                    let rs = await this.$root.context.get(
                        '/web-ecms/ring/danh-sach'
                    )
                    this.dsRing = rs.data
                } catch (error) {
                } finally {
                    this.$root.showLoading(false)
                }
            },
            // timring: async function () {
            //     this.$root.showLoading(true)
            //     try {
            //         var rs = await this.$root.context.get(`/web-ecms/danhmuc/ring/DanhSach/name?${this.ring}`)
            //         this.dsDsLam = rs.data
            //     } catch (error) {
            //     } finally {
            //         this.$root.showLoading(false)
            //     }
            // },
            grid1_selectedRowChanged: function (row) {
                this.setDisableBtn(3)
                this.ring = row.TEN_RING
                this.ringID = row.RING_ID
                this.ghiChu = row.GHICHU
            },
            ghiLai: async function () {
                this.$root.showLoading(true)
                try {
                    if (this.isDisableBtnNhapMoi === true) {
                        let dataIns = {
                            tenRing: this.ring,
                            ghiChu: this.ghiChu
                        }

                        if(this.validateData(dataIns)){
                            return
                        }
                        let res = await this.$root.context.post('/web-ecms/ring/them', dataIns)
                        if (res.error_code === 'BSS-00000000') {
                            //this.dsRing.unshift(res.data)
                            //this.$refs.grid.firstPage()
                            //this.$refs.grid.setCurrentSelectedRow(0)
                            this.getdsRing()
                            this.$toast.success('Thêm thành công.')
                            this.setDisableBtn(3)
                        } else {
                            this.$toast.error(res.error)
                            this.getdsRing()
                        }
                    }
                    else {
                        let dataUpd = {
                            tenRing: this.ring,
                            ghiChu: this.ghiChu,
                            ringId: this.ringID
                        }
                        if(this.validateData(dataUpd)){
                            return
                        }

                        let res = await this.$root.context.post(`/web-ecms/ring/cap-nhat`, dataUpd)
                        if (res.error_code === 'BSS-00000000') {
                            this.$toast.success('Cập nhật thành công.')
                            await this.getdsRing()
                            this.setDisableBtn(3)
                        } else {
                            this.$toast.error(res.error)
                            this.getdsRing()
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
                if (this.ringID == null || this.ringID == ''){
                    this.$toast.error('Chưa chọn ring.')
                    return
                }
                try {
                    // let dataDel = {
                    //     loaidbId: this.ringID
                    // }
                    let res = await this.$root.context.post(`/web-ecms/ring/xoa/${this.ringID}`)
                    //console.log(res)
                    if (res.error_code === 'BSS-00000000') {
                        this.$toast.success('Xóa thành công.')
                        //this.$refs.grid.currentPageIndex = Math.floor(this.dsRing.length/10)-1
                        await this.getdsRing()
                        this.setDisableBtn(1)
                    } else {
                        this.$toast.error(res.message_detail)
                        //this.getdsRing()
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
                if (data.tenRing == null || data.tenRing == '') {
                    errorMessage.push("Tên ring không được trống.")
                } else if (data.tenRing.length > 50) {
                    errorMessage.push("Tên ring không được dài quá 50 ký tự.")
                }

                if (data.ghiChu.length > 200) {
                    errorMessage.push("Ghi chú không được dài quá 200 ký tự.")
                }

                if (this.isDisableBtnNhapMoi === false &&  (data.ringId == null || data.ringId == '')) {
                    errorMessage.push("Chưa chọn ring.")
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
                        this.ring = null
                        break
                    case 2://Huy
                        this.isDisableBtnNhapMoi = false
                        this.isDisableBtnXoa = false
                        this.ring = null
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

