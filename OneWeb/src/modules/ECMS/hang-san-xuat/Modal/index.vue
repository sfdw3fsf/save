<template src="./index.html">
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
            dsHangSanXuat: [],
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
        showModal: async function() {
            // this.$bvModal.show('modal-hangsanxuat');
            this.id = null;
            this.isError = false;
            this.ten = '';
            try {
                this.loading(true);
                var rs = await this.$root.context.get('/web-ecms/danhmuc/HangSX/DanhSach');
                this.dsHangSanXuat = rs.data;
            } catch (error) {
                this.$root.toastError('Xảy ra lỗi')
            } finally {
                this.loading(false);
            }
        },
        grid_selectedRowChanged: function(dataItem) {
            if(dataItem) {
                this.ten = dataItem.HANG_SX;
                this.id = dataItem.HANGSX_ID;
                this.isError = false;
            }
            this.setDisableBtn(false, false, false, false);
        },
        checkForm: function() {
            if(this.ten) {
                var arr = this.dsHangSanXuat.filter(val => val.HANG_SX.toLowerCase() == this.ten.toLowerCase() && val.HANGSX_ID != this.id);
                if(arr.length > 0) {
                    this.$root.toastError('Hãng sản xuất đã tồn tại, vui lòng nhập lại.');
                    return false;
                }
                if(this.ten.length > 100) {
                    this.$root.toastError('Hãng sản xuất dữ liệu vượt quá độ dài cho phép, tối đa 100 ký tự.');
                    return false;
                }
                return true;
            }
            this.$root.toastError('Hãng sản xuất bắt buộc nhập dữ liệu');
            return false;
        },
        nhapMoi: function() {
            if(this.isDisableBtnNhapMoi) {
                return;
            }
            this.id = null;
            this.isError = false;
            this.ten = '';
            this.setDisableBtn(true, false, false, true);
        },
        ghiLai: async function() {
            if(this.isDisableBtnGhiLai) {
                return;
            }
            if(this.checkForm()) {
                if(this.id) {
                    try {
                        var rs = await this.$root.context.post('/web-ecms/danhmuc/HangSX/CapNhat', {id: this.id, name: this.ten});
                        var index = this.dsHangSanXuat.findIndex(val => val.HANGSX_ID == this.id);
                        this.dsHangSanXuat[index].HANG_SX = this.ten;
                        this.$toast.success('Cập nhật thành công');
                        this.isError = false;
                    } catch (error) {
                        this.$root.toastError('Xảy ra lỗi');
                    }
                }
                else {
                    try {
                        var rs = await this.$root.context.post('/web-ecms/danhmuc/HangSX/Them', {name: this.ten});
                        this.dsHangSanXuat.unshift(rs.data);
                        this.$toast.success('Thêm mới thành công');
                        this.isError = false;
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
        xoa: function() {
            if(this.isDisableBtnXoa) {
                return;
            }
            this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa dữ liệu không?', {
                size: 'sm',
                centered: true,
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy'
            })
            .then(async val => {
                if(val) {
                    if(this.id) {
                        try {
                            var rs = await this.$root.context.post(`/web-ecms/danhmuc/HangSX/Xoa/${this.id}`);
                            if(rs.data.result) {
                                this.dsHangSanXuat = this.dsHangSanXuat.filter(val => val.HANGSX_ID != this.id);
                                this.$toast.success('Xóa thành công');
                                this.isError = false;
                            }
                        } catch (error) {
                            this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : 'Không thể xóa bản ghi này');
                        }
                    }
                    else {
                        this.$root.toastError('Vui lòng chọn hãng sản xuất để xóa');
                    }
                }
            })
            .catch(err => {
                this.$root.toastError('Xảy ra lỗi');
            })
        },
        huy: function() {
            if(this.isDisableBtnHuy) {
                return;
            }
            // if(this.id) {
            //     var index = this.dsHangSanXuat.findIndex(val => val.HANGSX_ID == this.id);
            //     this.ten = this.dsHangSanXuat[index].HANG_SX;
            // }
            // else {
            //     this.ten = null;
            // }
            this.ten = null;
            this.id = null;
            this.isError = false;
            this.setDisableBtn(false, true, true, false);
        },
        closeModal: function() {
            this.$bvModal.hide(this.modalId);
        }
    },
}
</script>

<style scoped>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
