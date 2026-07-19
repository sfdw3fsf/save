<template src='./index.html'>
</template>

<script>
export default {
    props: {
        modalId: String
    },
    data() {
        return {
            ten: '',
            id: null,
            isError: false,
            dsLoaiTu: [],
            isDisableBtnNhapMoi: false,
            isDisableBtnGhiLai: true,
            isDisableBtnHuy: true,
            isDisableBtnXoa: true,
        }
    },
    methods: {
        setDisableBtn: function (isNhapmoi, isGhilai, isHuy, isXoa) {
            this.isDisableBtnNhapMoi = isNhapmoi;
            this.isDisableBtnGhiLai = isGhilai;
            this.isDisableBtnHuy = isHuy;
            this.isDisableBtnXoa = isXoa;
        },
        loadDsLoaiTu: async function() {
            try {
                var rs = await this.$root.context.get('/web-ecms/danhmuc/loaitu/lietke_danhsach');
                this.dsLoaiTu = rs.data;
            } catch (error) {
                
            }
        },
        showModal: async function() {
            // this.$bvModal.show('modal-loaitu');
            this.ten = '';
            this.id = null;
            this.isError = false;
            try {
                this.loading(true);
                await this.loadDsLoaiTu();
            } catch (error) {
                this.$root.toastError('Xảy ra lỗi');
            } finally {
                this.loading(false);
                this.$refs.tenInput.focus();
            }
        },
        closeModal: function() {
            this.$bvModal.hide(this.modalId);
        },
        grid_selectedRowChanged: function(dataItem) {
            this.ten = dataItem.LOAI_TU;
            this.id = dataItem.LOAITU_ID;
            this.isError = false;
            this.setDisableBtn(false, false, false, false);
        },
        checkForm() {
            if(this.ten) {
                var arr = this.dsLoaiTu.filter(val => val.LOAI_TU.toLowerCase() == this.ten.toLowerCase() && val.LOAITU_ID != this.id);
                if(arr.length > 0) {
                    this.$root.toastError('Loại tủ đã tồn tại, vui lòng nhập lại.');
                    return false;
                }
                else if(this.ten.length > 100){
                    this.$root.toastError('Loại tủ dữ liệu vượt quá độ dài cho phép, tối đa 100 ký tự');
                    return false;
                }
                return true;
            }
            this.$root.toastError('Tên loại tủ không được để trống!');
            return false;
        },
        nhapMoi: function() {
            if(this.isDisableBtnNhapMoi) {
                return;
            }
            this.ten = '';
            this.id = null;
            this.isError = false;
            this.setDisableBtn(true, false, false, true);
        },
        ghiLai: async function() {
            if(this.isDisableBtnGhiLai) {
                return;
            }
            if(this.checkForm()) {
                if(this.id) {
                    try {
                        var rs = await this.$root.context.post('/web-ecms/danhmuc/loaitu/capnhat', {loaitu_id: this.id, loai_tu: this.ten});
                        if(rs.data.result) {
                            var index = this.dsLoaiTu.findIndex(val => val.LOAITU_ID == this.id);
                            this.dsLoaiTu[index].LOAI_TU = this.ten;
                            this.$toast.success('Cập nhật thành công');
                            this.isError = false;
                            await this.loadDsLoaiTu();
                        }
                    } catch (error) {
                        this.$root.toastError('Xảy ra lỗi');
                    }
                }
                else {
                    try {
                        var rs = await this.$root.context.post('/web-ecms/danhmuc/loaitu/them', {loai_tu: this.ten});
                        if(rs.data) {
                            this.dsLoaiTu.push(rs.data);
                            this.$toast.success('Thêm mới thành công');
                            this.isError = false;
                            await this.loadDsLoaiTu();
                        }
                    } catch (error) {
                        this.$root.toastError('Xảy ra lỗi');
                    }
                }
                this.setDisableBtn(false, false, false, false);
            }
            else {
                this.$refs.tenInput.focus();
                this.isError = true;
            }
        },
        huy: function() {
            if(this.isDisableBtnHuy) {
                return;
            }
            // if(this.id) {
            //     var index = this.dsLoaiTu.findIndex(val => val.LOAITU_ID == this.id);
            //     this.ten = this.dsLoaiTu[index].LOAI_TU;
            // }
            // else {
            //     this.isError = false;
            //     this.ten = '';
            // }
            this.setDisableBtn(false, true, true, false);
            this.isError = false;
            this.ten = '';
            this.id = 0;
        },
        xoa: function() {
            if(this.isDisableBtnXoa) {
                return;
            }
            this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa dữ liệu không?', {
                size: 'sm',
                centered: true,
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy',
            })
            .then(async val => {
                if(val) {
                    if(this.id) {
                        try {
                            var rs = await this.$root.context.post(`/web-ecms/danhmuc/loaitu/xoa/${this.id}`);
                            if(rs.data.result) {
                                this.dsLoaiTu = this.dsLoaiTu.filter(val => val.LOAITU_ID != this.id);
                                this.$toast.success('Xóa thành công');
                                this.isError = false;
                            }
                        } catch (error) {
                            this.$root.toastError('Loại tủ đã được sử dụng cho tủ rack, không thể xóa!');
                        }
                    }
                    else {
                        this.$root.toastError('Vui lòng chọn loại tủ để xóa');
                    }
                }
            })
            .catch(err => {
                this.$root.toastError('Xảy ra lỗi');
            })
        }
    },
    // mounted() {
    //     this.showModal();
    // }
}
</script>

<style scoped>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
