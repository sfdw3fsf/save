<template src="./index.html">
</template>

<script>
export default {
    props: {
        id: String,
        dataInput: {
            type: Object,
            default() {
                return {
                    // thuebao_id: 1472858,
                    // ma_tb: 'hung81268',
                    // trangthai_tb: 'Hoạt động bình thường',
                    // ten_tb: 'Bùi Duy Hưng',
                    // diachi_ld: '33/380, Đường Lê Duẩn, Phường Bắc Sơn, Quận Kiến An, Hải Phòng'
                }
            }
        }
    },
    data: function() {
        return {
            txt_sltvCu: null,
            txt_sltvMoi: null,
            txt_maTb: null,
            txt_TrangThaiTB: null,
            txt_TenTB: null,
            txt_DiaChiLD: null,
            thuebao_id: 0
        }
    },
    methods: {
        showModal: function() {
            this.thuebao_id = this.dataInput.thuebao_id ? this.dataInput.thuebao_id : 0;
            this.txt_TenTB = this.dataInput.tentb;
            this.txt_TrangThaiTB = this.dataInput.trangthaitb;
            this.txt_DiaChiLD = this.dataInput.diachi_ld;

            this.HienThiThongTin();
        },
        HienThiThongTin: async function() {
            try {
                this.loading(true);
                let rs = await this.$root.context.get(`web-quantri/thaydoisoluongtv/lay_ds_tb_tdsltv?thuebaoId=${this.dataInput.thuebao_id}`);
                if(rs.data && rs.data.length) {
                    let obj = rs.data[0];
                    this.txt_sltvCu = obj.sltv_htvc;
                    this.txt_sltvMoi = null;
                    // this.txt_sltvMoi = null;
                    // this.txt_maTb = obj.ma_tb;
                    // this.txt_TrangThaiTB = obj.trangthai_tb;
                    // this.txt_TenTB = obj.ten_tb;
                    // this.txt_DiaChiLD = obj.diachi_ld;
                }
                else {
                    this.$root.toastError(`Thuê bao ${this.dataInput.ma_tb} không thuộc dịch vụ HTVC\nĐề nghị kiểm tra lại Số máy !`);
                }
            } catch (error) {

            } finally {
                this.loading(false);
            }
        },
        checkForm: function() {
            if(this.dataInput.thuebao_id == 0) {
                this.$root.toastError('Chưa có thuê bao cần thay đổi');
                return false;
            }
            if(this.txt_sltvMoi == null || this.txt_sltvMoi === "") {
                this.$root.toastError('Bạn chưa nhập số lượng TV mới !');
                return false;
            }
            return true;
        },
        ghiLai: async function() {
            let kq = this.checkForm();
            if(kq) {
                try {
                    this.loading(true);
                    let rsCN_BienDong = await this.$root.context.post('web-quantri/thaydoisoluongtv/cn_biendong_sltv_htvc', {
                        thuebao_id: this.dataInput.thuebao_id,
                        ma_tb: this.dataInput.ma_tb,
                        trangthai_id: 1,
                        sltv_cu: this.txt_sltvCu,
                        sltv_moi: this.txt_sltvMoi,
                        ghichu: 'Thay đổi số lượng TV'
                    });
                    let rsCN_SoLuong = await this.$root.context.post('web-quantri/thaydoisoluongtv/capnhat_soluong_tv_htvc', {
                        thuebao_id: this.dataInput.thuebao_id,
                        soluong: this.txt_sltvMoi,
                        ma_tb: this.dataInput.ma_tb
                    })
                    if(rsCN_BienDong.data[0] && rsCN_SoLuong.data[0].result) {
                        this.$root.toastSuccess('Cập nhật dữ liệu thành công!');
                        this.HienThiThongTin();
                    }
                } catch (error) {
                    this.$root.toastError('Cập nhật dữ liệu thất bại');
                } finally {
                    this.loading(false);
                }
            }
        },
        clear: function() {
            this.txt_sltvCu = null;
            this.txt_sltvMoi = null;
            this.txt_maTb = null;
            this.txt_TrangThaiTB = null;
            this.txt_TenTB = null;
            this.txt_DiaChiLD = null;
            this.thuebao_id = 0;
        },
        huy: function() {
            this.clear();
        },
        keyDown: function(e) {
            if (e.key === "Backspace" || e.key === "Delete") {
                return e.preventDefault(); // Don't do anything to the input value
            }
        },
        txt_sltvMoi_keyDown: function(e) {
            if (e.key === "-") {
                return e.preventDefault(); // Don't do anything to the input value
            }
        },
        txt_sltvMoi_input: function(e) {
            if(e.target.value.length <= 2) {
                this.txt_sltvMoi = e.target.value;
            }
            this.$forceUpdate()
        },
    }
}
</script>
