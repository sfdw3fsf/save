<template src="./index.html">
</template>

<script>

export default {
    name: 'QuyHoachVLAN',
    data() {
        var returnData = {
            disableVungQuyHoach: true,
            isDisableBtnNhapMoi: false,
            isDisableBtnGhiLai: false,
            isDisableBtnHuy: false,
            isDisableBtnXoa: false,
            isSelected: -1,
            maxrows: 0,
            loaiQuyHoachs: [
                {
                    title: 'Toàn mạng',
                    value: 0
                },
                {
                    title: 'Theo vùng',
                    value: 1
                }
            ],
            trangThais: [
                {
                    title: 'Sử dụng',
                    value: 0
                },
                {
                    title: 'Chưa sử dụng',
                    value: 1
                }
            ],
            body: {
                kieu: 1,
                vlan: 0
            },
            txtTimKiem: '',
            modalXoa: false,
            vlan: {
                quyHoachId: null,
                loaiQuyHoach: 0,
                donvi_id: null,
                dichVu: null,
                loaitb_id: null,
                trangthai: 0,
                vlan_tu: null,
                vlan_den: null
            },
            quyHoachVLANs: [],
            vungQuyHoachs: null,
            dichVus: null,
            loaiDichVus: null,
            isErorLoaiQuyHoach: false,
            isErorVungQuyHoach: false,
            isErorDichVu: false,
            isErorLoaiDichVu: false,
            isErorTrangThai: false,
            isErorVLANTu: false,
            isErorVLANDen: false
        };
        return returnData;
    },
    computed: {
        
    },
    methods: {
        timKiem: async function() {
            var tk = this.txtTimKiem.trim();
            if(!isNaN(tk)) {
                this.body = {
                    vlan: this.txtTimKiem,
                    kieu: 1
                };
                await this.loadQuyHoachVLAN(this.body);
            }
        },
        checkForm: function() {
            var check = true;
            var error = '';

            if(this.vlan.vlan_den == null || this.vlan.vlan_den === '') {
                check = false;
                error = 'VLAN đến không được để trống \n';
                this.isErorVLANDen = true;
                this.$refs.vlan_denInp.focus();
            }
            else {
                if(this.vlan.vlan_den.length > 10) {
                    error = 'VLAN đến dữ liệu vượt quá độ dài cho phép, tối đa 10 chữ số. \n';
                    check = false;
                    this.isErorVLANDen = true;
                    this.$refs.vlan_denInp.focus();
                }
                else if(this.vlan.vlan_den < 0 || this.vlan.vlan_den.toString().includes('.')){
                    check = false;
                    error = 'VLAN đến phải là số nguyên lớn hơn hoặc bằng 0 \n';
                    this.isErorVLANDen = true;
                    this.$refs.vlan_denInp.focus();
                }
                else {
                    this.isErorVLANDen = false;
                }
            }

            if(this.vlan.vlan_tu == null || this.vlan.vlan_tu === '') {
                check = false;
                error = 'VLAN từ không được để trống \n'.concat(error);
                this.isErorVLANTu = true;
                this.$refs.vlan_tuInp.focus();
            }
            else {
                if(this.vlan.vlan_tu.length > 10) {
                    check = false;
                    error = 'VLAN từ dữ liệu vượt quá độ dài cho phép, tối đa 10 chữ số. \n'.concat(error);
                    this.isErorVLANTu = true;
                    this.$refs.vlan_tuInp.focus();
                }
                else if(this.vlan.vlan_tu < 0 || this.vlan.vlan_tu.toString().includes('.')) {
                    check = false;
                    error = 'VLAN từ phải là số nguyên lớn hơn hoặc bằng 0 \n'.concat(error);
                    this.isErorVLANTu = true;
                    this.$refs.vlan_tuInp.focus();
                }
                else {
                    this.isErorVLANTu = false;
                }
            }

            if(Number.isInteger(parseInt(this.vlan.vlan_tu)) && Number.isInteger(parseInt(this.vlan.vlan_den))) {
                this.vlan.vlan_tu = parseInt(this.vlan.vlan_tu);
                this.vlan.vlan_den = parseInt(this.vlan.vlan_den);
                if(this.vlan.vlan_tu > this.vlan.vlan_den) {
                    check = false;
                    error = 'VLAN từ không được lớn hơn VLAN đến. \n'.concat(error);
                    this.isErorVLANTu = true;
                    this.isErorVLANDen = true;
                    this.$refs.vlan_tuInp.focus();
                }
                else {
                     this.isErorVLANTu = false;
                    this.isErorVLANDen = false;
                }
            }

            if(this.vlan.trangthai == null) {
                check = false;
                error = 'Trạng thái không được trống \n'.concat(error);
                this.isErorTrangThai = true;
            }
            else {
                this.isErorTrangThai = false;
            }

            if(!this.vlan.loaitb_id) {
                check = false;
                error = 'Loại dịch vụ không được để trống \n'.concat(error);
                this.isErorLoaiDichVu = true;
            }
            else {
                this.isErorLoaiDichVu = false;
            }

            if(!this.vlan.dichVu) {
                check = false;
                error = 'Dịch vụ không được để trống \n'.concat(error);
                this.isErorDichVu = true;
            }
            else {
                this.isErorDichVu = false;
            }

            if(this.vlan.loaiQuyHoach == 1 && !this.vlan.donvi_id) {
                check = false;
                error = 'Vùng quy hoạch không được để trống \n'.concat(error);
                this.isErorVungQuyHoach = true;
            }
            else {
                this.isErorVungQuyHoach = false;
            }

            if(this.vlan.loaiQuyHoach == null) {
                check = false;
                error = 'Loại quy hoạch không được để trống \n'.concat(error);
                this.isErorLoaiQuyHoach = true;
            }
            else {
                this.isErorLoaiQuyHoach = false;
            }

            var vlan = this.quyHoachVLANs.filter(v => {
                return v.QUYHOACH_ID != this.vlan.quyHoachId && v.DONVI_ID == this.vlan.donvi_id && v.DICHVUVT_ID == this.vlan.dichVu && v.LOAITB_ID == this.vlan.loaitb_id && v.TRANGTHAI == this.vlan.trangthai && v.VLAN_TU == this.vlan.vlan_tu && v.VLAN_DEN == this.vlan.vlan_den;
            })
            if(vlan.length > 0) {
                check = false;
                error = 'Thông tin quy hoạch VLAN đã tồn tại \n'.concat(error);
            }

            if(!check) {
                this.$root.toastError(error)
            }
            return check;
        },
        ghiLai: async function() {
            if(!this.isDisableBtnGhiLai) {
                if(this.checkForm()) {
                    if(this.vlan.quyHoachId > 0) {
                        try {
                            this.loading(true);
                            var kq = await this.$root.context.post(`/web-ecms/danhmuc/vlan/capnhat/${this.vlan.quyHoachId}`, 
                                {
                                    donvi_id: this.vlan.loaiQuyHoach ? this.vlan.donvi_id : -1,
                                    vlan_tu: this.vlan.vlan_tu,
                                    vlan_den: this.vlan.vlan_den,
                                    kieu: this.body.kieu,
                                    loaitb_id: this.vlan.loaitb_id,
                                    trangthai: this.vlan.trangthai
                                });
                            if(kq.data[0].KETQUA) {
                                this.$toast.success('Cập nhật thành công');
                                this.setDisableBtn(false, true, true, true);
                                this.body.vlan = 0;
                                await this.loadQuyHoachVLAN(this.body);
                            }
                            else {
                                this.$toast.error('Cập nhật thất bại!');
                            }
                        } catch (error) {
                            this.$toast.error('Cập nhật thất bại!')
                            throw(error);
                        } finally {
                            this.loading(false);
                        }
                    }
                    else {
                        try {
                            this.loading(true);
                            if(this.vlan.loaiQuyHoach == 0)
                                this.vlan.donvi_id = -1;
                            await this.$root.context.post('/web-ecms/danhmuc/vlan/them', this.vlan);
                            this.$toast.success('Thêm mới thành công');
                            this.setDisableBtn(false, true, true, true);
                            this.body.vlan = 0;
                            await this.loadQuyHoachVLAN(this.body);
                        } catch (error) {
                            this.$toast.error('Thêm mới thất bại!')
                        } finally {
                            this.loading(false);
                        }
                    }
                }
            }
        },
        nhapMoi: function() {
            this.txtTimKiem = '';
            this.vlan = {
                ...this.vlan,
                quyHoachId: null,
                loaiQuyHoach: 0,
                trangthai: 0,
                vlan_tu: null,
                vlan_den: null,
                kieu: this.body.kieu
            }
            this.$refs.vlan_tuInp.focus();
            this.setDisableBtn(true, false, false, true);
        },
        xoa: function() {
            if(!this.isDisableBtnXoa) {
                this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa dữ liệu không?', {
                    size: 'sm',
                    centered: true,
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                })
                .then(async val => {
                    if(val) {
                        try {
                            var kq = await this.$root.context.post(`/web-ecms/danhmuc/vlan/xoa/${this.vlan.quyHoachId}`);
                            if(kq.data[0].KETQUA) {
                                this.setDisableBtn(false, true, true, true);
                                this.$toast.success('Xóa thành công');
                            }
                            else {
                                this.$toast.error('Xóa thất bại!')
                            }
                        } catch (error) {
                            this.$toast.error('Xóa thất bại!')
                        }
                        this.loadQuyHoachVLAN(this.body);
                    }
                })
            }
        },
        huyBo: function() {
            this.vlan.quyHoachId = 0;
            this.setDisableBtn(false, true, true, false);
            this.isErorLoaiQuyHoach = false;
            this.isErorVungQuyHoach = false;
            this.isErorDichVu = false;
            this.isErorLoaiDichVu = false;
            this.isErorTrangThai = false;
            this.isErorVLANTu = false;
            this.isErorVLANDen = false;
        },
        setDisableBtn: function(isNhapmoi, isGhilai, isHuy, isXoa) {
            this.isDisableBtnNhapMoi = isNhapmoi;
            this.isDisableBtnGhiLai = isGhilai;
            this.isDisableBtnHuy = isHuy;
            this.isDisableBtnXoa = isXoa;
        },
        loadQuyHoachVLAN: async function(body) {
            var rs = await this.$root.context.post(`/web-ecms/danhmuc/vlan/ds-quyhoach`, body);
            this.quyHoachVLANs = rs.data;
            if(rs.length > 0) {
                this.maxrows = rs[0].MAXROWS;
            }

            this.vlan = {
                ...this.vlan,
                quyHoachId: 0,
                loaiQuyHoach: 0,
                dichVu: this.dichVus.length > 0 ? this.dichVus[0].DICHVUVT_ID : 0,
                trangthai: 0,
                vlan_tu: null,
                vlan_den: null
            }
        },
        grid_selectedRowChanged: async function(data) {
            if(data) {
                this.setDisableBtn(false, false, false, false);
                this.vlan = {
                    ...this.vlan,
                    quyHoachId: data.QUYHOACH_ID,
                    loaiQuyHoach: data.DONVI_ID ? 1 : 0, // 0: Toàn mạng, 1: Theo vùng
                    donvi_id: data.DONVI_ID,
                    dichVu: data.DICHVUVT_ID,
                    loaitb_id: data.LOAITB_ID,
                    trangthai: data.TRANGTHAI,
                    vlan_tu: data.VLAN_TU,
                    vlan_den: data.VLAN_DEN,
                }
            }
        }
    },
    watch: {
        'vlan.dichVu': async function(newValue) {
            var rs = await this.$root.context.post('/web-ecms/danhmuc/vlan/loaidichvu', {"dichvu_id": newValue});
            this.loaiDichVus = rs.data;
            this.vlan.loaitb_id = this.loaiDichVus.length > 0 ? this.loaiDichVus[0].LOAITB_ID : 0;
        },
        'vlan.loaiQuyHoach': async function(newValue) {
            if(newValue) {
                var kqVQH = await this.$root.context.get('/web-ecms/danhmuc/vlan/vungquyhoach');
                this.vungQuyHoachs = kqVQH.data;
                this.vlan.donvi_id = this.vungQuyHoachs.length > 0 ? this.vungQuyHoachs[0].DONVI_ID : null;
            }
            else {
                this.vungQuyHoachs = null;
                this.vlan.donvi_id = null;
            }
            this.disableVungQuyHoach = !this.disableVungQuyHoach;
        }
    },
    async mounted() {
        try {
            this.loading(true);
            var kqDV = await this.$root.context.get('/web-ecms/danhmuc/vlan/dichvu');
            this.dichVus = kqDV.data;
            if(this.dichVus.length > 0) {
                this.vlan.dichVu = this.dichVus[0].DICHVUVT_ID;
                var kqLDV = await this.$root.context.post('/web-ecms/danhmuc/vlan/loaidichvu',{"dichvu_id": this.dichVus[0].DICHVUVT_ID});
                this.loaiDichVus = kqLDV.data;
                this.vlan.loaitb_id = this.loaiDichVus.length > 0 ? this.loaiDichVus[0].LOAITB_ID : 0;
            }
            else {
                this.vlan.dichVu = 0;
                this.vlan.loaitb_id = -1
            }
            this.loadQuyHoachVLAN(this.body);
            this.$refs.vlanInp.focus();
        } catch (err) {
            
        } finally {
            this.loading(false);
        }
    },
    destroyed () {
        this.quyHoachVLANs = null,
        this.vungQuyHoachs = null,
        this.dichVus = null,
        this.loaiDichVus = null
    }
}
</script>

<style scoped src="./index.scss">
</style>
