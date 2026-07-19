<template src="./index.html">
</template>

<script>
export default {
    props: {
        modalId: String,
        vanhanh_id_ts: Number,
        vphieuvh_cha_id: Number,
        trangthai: Number
    },
    data: function() {
        return {
            cboTrangthaivh: null,
            txtThietbi: null,
            dtpNgayGiao: new Date(),
            txtNoiDung: null,
            cbDonvi: null,
            cbTrangthaigiao: 1,

            dsTrangThai: [],
            dsTiepNhan: [],
            dsDonVi: [],
            dsTrangThaiGiao:  [],

            vthangthai: 0,
            vvanhanh_id: 0,
            vttvh_id: 0,
            vphieuvh_id: 0,
            vnhanvien_nhan_id: 0,

            isEnableTrangThaiVh: true,
            isEnableBtnGiaoPhieu: true,
            isEnableBtnHuyGiao: false,
            isEnableND: true,


            waterMark: "Chọn ngày",
            dateVal:  Date,
            dateFormat: "dd/MM/yyyy",
        }
    },
    methods: {
        showForm: function() {
            this.vthangthai = this.trangthai;
            this.vvanhanh_id = this.vanhanh_id_ts;
            this.load_grid();
            this.load_cbotrangthaivh(2);
        },
        load_grid: async function() {
            try {
                let rs =  await this.$root.context.get(`/web-ecms/giaophieubaohongthietbi/lay-ds-thietbi-theo-vhanhid/${this.vphieuvh_cha_id}`);
                this.dsTiepNhan = rs.data;
            } catch (error) {
                this.$root.toastError('Có lỗi lấy danh sách tiếp nhận');
            }
        },
        load_cbotrangthaivh: async function(idx) {
            try {
                let rs =  await this.$root.context.get('/web-ecms/giaophieubaohongthietbi/lay-ds-trangthai-vanhanh');
                this.dsTrangThai = rs.data;
                this.cboTrangthaivh = idx;
                if(this.vthangthai == 1) {
                    this.isEnableTrangThaiVh = false;
                }
                else {
                    this.isEnableTrangThaiVh = true;
                }
            } catch (error) {
                this.$root.toastError('Có lỗi lấy danh sách trạng thái vận hành');
            }
        },
        load_cbDonVi: async function(vvanhanh_id) {
            try {
                let rs =  await this.$root.context.get(`/web-ecms/giaophieubaohongthietbi/lay-ds-donvi?phieuvh_id=${this.vphieuvh_cha_id}&vanhanh_id=${vvanhanh_id}`);
                this.dsDonVi = rs.data;
                if(this.dsDonVi.length > 0) {
                    this.cbDonvi = this.dsDonVi[0].DONVI_ID;
                }
            } catch (error) {
                this.$root.toastError('Có lỗi lấy danh sách đơn vị');
            }
        },
        gridDsTiepNhan_rowSelected: function(args) {
            this.vvanhanh_id = 0;
            this.vttvh_id = 0;
            if(args.rowIndex >= 0) {
                console.log(args);
                let data = args.data;
                this.vvanhanh_id = data.VANHANH_ID;
                this.vttvh_id = data.TTVH_ID;
                this.txtThietbi = data.MA_TBI;
                this.vphieuvh_id = data.PHIEUVH_ID;
                this.load_cbDonVi(this.vvanhanh_id);
            }
        },
        tsbtnGiaoPhieu_Click: async function() {
            try {
                if(this.dsTiepNhan.length == 0) {
                    this.$root.toastError('Không có thiết bị báo hỏng để giao phiếu!');
                    return;
                }
                let rs = await this.$root.context.post('/web-ecms/giaophieubaohongthietbi/capnhat-giaophieu-vanhanh', {
                    kieu: 1,
                    phieuvh_id: 1,
                    vanhanh_id: this.vvanhanh_id,
                    donvi_nhan_id: this.cbDonvi,
                    nhanvien_nhan_id: this.vnhanvien_nhan_id,
                    ngay_giao: this.dtpNgayGiao,
                    noidung_giao: this.txtNoiDung,
                    phieuvh_cha_id: this.vphieuvh_cha_id,
                    ttvh_id: this.cboTrangthaivh,
                });
                if(rs.data.result) {
                    this.$root.toastSuccess('Giao phiếu báo hỏng thành công!');
                    await this.load_grid();
                }
                else {
                    this.$root.toastError('Giao phiếu báo hỏng thất bại!');
                }
            } catch (error) {
                this.$root.toastError('Giao phiếu báo hỏng thất bại!');
            }
        },
        setbutton: function(thamso) {
            if(thamso == 1) {
                this.isEnableBtnGiaoPhieu = false;
                this.isEnableBtnHuyGiao = true;
                this.isEnableND = true;
            }
            else {
                this.isEnableBtnGiaoPhieu = true;
                this.isEnableBtnHuyGiao = false;
                this.isEnableND = false;
            }
        },
        load_grid_dagiao: async function() {
            try {
                let rs =  await this.$root.context.get(`/web-ecms/giaophieubaohongthietbi/lay-ds-phieuvanhanh-dagiao/${this.vvanhanh_id}`);
                this.dsTiepNhan = rs.data;
                if(this.dsTiepNhan.length > 0) {
                    this.$refs.gridTiepNhan.setCurrentSelectedRow(0);
                }
            } catch (error) {
                this.$root.toastError('Có lỗi lấy danh sách');
            }
        },
        tsbtnHuyGiao_Click: function() {
            try {
                this.$bvModal.msgBoxConfirm('Bạn có chắc chắn muốn hủy phiếu giao!', {
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then(async v => {
                    if(v) {
                        let rs =  await this.$root.context.post(`/web-ecms/giaophieubaohongthietbi/huy-giao?vanhanh_id=${this.vvanhanh_id}&vanhanh_id=${this.vphieuvh_id}`);
                        if(rs.data.result) {
                            this.$root.toastSuccess('Hủy phiếu báo hỏng thành công!');
                            this.load_grid_dagiao();
                        }
                    }
                }).catch(error => {
                    this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : 'Xóa thất bại');
                })
            } catch (error) {
                
            }
        },
        closeForm: function() {
            this.$bvModal.hide(this.modalId);
            this.$emit('closeForm');
        }
    },
    watch: {
        cbTrangthaigiao: function(val) {
            if(val == 1) {
                this.setbutton(2);
                this.load_grid();
            }
            else {
                this.setbutton(1);
                this.load_grid_dagiao();
            }
        }
    }
}
</script>
