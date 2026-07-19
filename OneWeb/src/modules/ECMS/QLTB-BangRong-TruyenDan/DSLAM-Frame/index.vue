<template src="./index.html">
</template>

<script>
import EventBus from '../../../../utils/eventBus'
export default {
    data() {
        return {
            dslam_id: 0,
            framedsl_id: 0,
            ten: null,
            vitri: null,
            isThemMoi: false,
            currentItem: {}
        }
    },
    methods: {
        clearForm: function() {
            this.ten = null;
            this.vitri = null;
        },
        checkForm: function() {
            var check = true;
            var error = '';

            if(this.vitri == null || this.vitri == '') {
                error = 'Vị trí không được để trống !';
                check = false;
            }

            if(!this.ten) {
                error = 'Tên frame không được để trống !\n'.concat(error);
                check = false;
            }

            if(!check) {
                this.$root.toastError(error);
            }
            return check;
        },
        taoDuLieuDsLamFrame: function() {
            return {
                dsLamId: this.dslam_id,
                frameDslId: this.framedsl_id,
                tenFrame: this.ten,
                viTri: this.vitri
            }
        },
        themMoi: function() {
            this.isThemMoi = true;
            this.clearForm();
        },
        ghiLai: async function() {
            if(this.checkForm()){
                if(this.isThemMoi) {
                    try {
                        this.loading(true);
                        var rs = await this.$root.context.post('/web-ecms/danhmuc/dslam-frame/themDsLamFrame', this.taoDuLieuDsLamFrame());
                        if(rs.data) {
                            this.$root.toastSuccess('Thêm mới thành công!');
                            this.isThemMoi = false;
                            this.currentItem = {
                                framedsl_id: rs.data.FRAMEDSL_ID,
                                dslam_id: this.dslam_id,
                                tenFrame: this.ten,
                                status: 1
                            }
                            this.framedsl_id = this.currentItem.framedsl_id;
                            return true;
                        }
                    } catch (error) {
                        this.$root.toastError(error.response.data.message_detail);
                    } finally {
                        this.loading(false);
                    }
                }
                else {
                    try {
                        this.loading(true);
                        var rs = await this.$root.context.post('/web-ecms/danhmuc/dslam-frame/capNhatDsLamFrame', this.taoDuLieuDsLamFrame());
                        if(rs.data) {
                            this.$root.toastSuccess('Cập nhật thành công!');
                            this.currentItem = {
                                framedsl_id: this.framedsl_id,
                                dslam_id: this.dslam_id,
                                tenFrame: this.ten,
                                status: 1
                            }
                            return true;
                        }
                    } catch (error) {
                        this.$root.toastError(error.response.data.message_detail);
                    } finally {
                        this.loading(false);
                    }
                }
            }
        },
        xoa: async function() {
            // this.$bvModal.msgBoxConfirm('Bạn chắc chắn muốn xóa?', {
            //     size: 'sm',
            //     okTitle: 'Đồng ý',
            //     cancelTitle: 'Hủy',
            // }).then(async v => {
            //     if(v) {
                   
            //     }
            // }).catch(err => {
            //     this.$toast.toastError('Xóa thất bại');
            // })
            try {
                this.loading(true);
                var rs = await this.$root.context.post(`/web-ecms/danhmuc/dslam-frame/xoaDsLamFrame/?frameId=${this.framedsl_id}`);
                if(rs.data.result) {
                    this.$root.toastSuccess("Xóa dữ liệu thành công!");
                    return true;
                }
            } catch (error) {
                this.$root.toastError(error.response.data.message_detail);
            } finally {
                this.loading(false);
            }
        },
        loadDsLamFrame: async function() {
            try {
                this.loading(true);
                var rs = await this.$root.context.get(`/web-ecms/danhmuc/dslam-frame/getDsDsLamFrameId/${this.framedsl_id}`);
                if(rs.data) {
                    this.ten = rs.data.TEN_FRAME;
                    this.vitri = rs.data.VITRI;
                }
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        bindData: async function(data) {
            this.framedsl_id = data.itemID;
            this.dslam_id = data.dslam_id;
            await this.loadDsLamFrame();
            if(this.framedsl_id == 0) {
                this.themMoi()
            }
            else {
                this.isThemMoi = false;
            }
        }
    },
    mounted: async function() {
        try {
            let vm = this;
            EventBus.$on('thongTinDsLamFrame', async data => {
                this.dslam_id = data.dslam_id;
                this.framedsl_id = data.framedsl_id;
                await this.loadDsLamFrame()
            })

            EventBus.$on('themMoi', () => {
                console.log('them');
                vm.clearForm();
                vm.isThemMoi = true;
            })

            EventBus.$on('ghiLai', () => {
                vm.ghiLai();
            })

            EventBus.$on('xoa', () => {
                vm.xoa();
            })

            EventBus.$on('huyBo', () => {
                vm.clearForm();
            })
        } catch (error) {
            
        }
    },
    destroyed() {
        EventBus.$off("themMoi");
        EventBus.$off("ghiLai");
        EventBus.$off("huyBo");
        EventBus.$off("xoa");
    }
}
</script>
