<template src="./index.html">
</template>

<script>
import EventBus from '../../../../utils/eventBus'
export default {
    data() {
        return {
            framedsl_id: 0,
            shelfdsl_id: 0,
            ten: null,
            vitri: null,
            soSlot: null,
            slotBD: null,
            soSlotDaDung: null,
            isThemMoi: false,
            currentItem: {}
        }
    },
    methods: {
        loadDsLamShelf: async function() {
            try {
                this.loading(true);
                var rs = await this.$root.context.get(`/web-ecms/dslamShelf/chitiet?dslshelf_id=${this.shelfdsl_id}`);
                if(rs.data) {
                    this.ten = rs.data.TEN_SHELF;
                    this.vitri = rs.data.VITRI;
                    this.soSlot = rs.data.SO_SLOT;
                    this.soSlotDaDung = rs.data.DADUNG;
                    this.slotBD = rs.data.SLOT_BD;
                }
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        clearForm: function() {
            this.ten = null;
            this.vitri = null;
            this.soSlot = null;
            this.soSlotDaDung = null;
            this.slotBD = null;
        },
        checkForm() {
            var check = true;
            var error = '';
            if(this.slotBD == null || this.slotBD == '') {
                check = false;
                error = 'Slot bắt đầu không được để trống !';
            }

            if(this.soSlot == null || this.soSlot == '') {
                check = false;
                error = 'Số slot không được để trống !\n'.concat(error);
            }

            if(this.vitri == null || this.vitri == '') {
                check = false;
                error = 'Vị trí không được để trống !\n'.concat(error);
            }

            if(!this.ten) {
                check = false;
                error = 'Tên thiết bị không được để trống !\n'.concat(error);
            }

            if(!check) {
                this.$root.toastError(error);
            }
            return check;
        },
        taoDuLieuDsLamShelf: function() {
            return {
                framedsl_id: this.framedsl_id,
                dslamshelf_id: this.shelfdsl_id,
                ten_shelf: this.ten,
                vi_tri: this.vitri,
                so_slot: this.soSlot,
                slot_bd: this.slotBD,
            }
        },
        themMoi: function() {
            this.isThemMoi = true;
            this.clearForm();
        },
        ghiLai: async function() {
            if(this.checkForm()) {
                if(this.isThemMoi) {
                    try {
                        this.loading(true);
                        var rs = await this.$root.context.post('/web-ecms/dslamShelf/them', this.taoDuLieuDsLamShelf());
                        if(rs.data) {
                            this.$root.toastSuccess('Thêm mới thành công!');
                            this.isThemMoi = false;
                            this.currentItem = {
                                shelfdsl_id: rs.data.SHELFDSL_ID,
                                framedsl_id: this.framedsl_id,
                                ten_shelf: this.ten,
                                so_slot: this.soSlot,
                                so_bd: this.slotBD
                            }
                            this.shelfdsl_id = rs.data.SHELFDSL_ID;
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
                        var rs = await this.$root.context.post('/web-ecms/dslamShelf/capnhat', this.taoDuLieuDsLamShelf());
                        if(rs.data) {
                            this.$root.toastSuccess('Cập nhật thành công!');
                            this.currentItem = {
                                shelfdsl_id: this.shelfdsl_id,
                                framedsl_id: this.framedsl_id,
                                ten_shelf: this.ten,
                                so_slot: this.soSlot,
                                so_bd: this.slotBD
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
                var rs = await this.$root.context.post(`/web-ecms/dslamShelf/xoa?dslshelf_id=${this.shelfdsl_id}`);
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
        bindData: async function(data) {
            this.shelfdsl_id = data.itemID;
            this.framedsl_id = data.framedsl_id;
            await this.loadDsLamShelf();
            if(this.shelfdsl_id == 0) {
                this.themMoi();
            }
            else {
                this.isThemMoi = false;
            }
        }
    },
    mounted: function() {
        try {
            let vm = this;
            EventBus.$on('thongTinDsLamShelf', async data => {
                this.framedsl_id = data.framedsl_id;
                this.shelfdsl_id = data.shelfdsl_id;
                await this.loadDsLamShelf();
            })

            EventBus.$on('themMoi', () => {
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
