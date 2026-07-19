<template src='./index.html'>
</template>

<script>
export default {
    data() {
        return {
            tramTbs: null,
            thietBis: null,
            quyHoachVCIDs: [],
            vlan: null,
            isThemMoi: false,
            modalXoa: false,
            ttVCID: {
                quyhoach_id: null,
                tramTbId: null,
                thietBiId: {
                    DSLAM_ID: null,
                    text: null
                },
                vlan_tu: null,
                vlan_den: null,
                vcid_tu: null,
                vcid_den: null,
                si_tu: null,
                si_den: null
            },
            isErorTramTB: false,
            isErorThietBi: false,
            isErorVlanTu: false,
            isErorVlanDen: false,
            isErorVCIDTu: false,
            isErorVCIDDen: false,
            isErorSITu: false,
            isErorSIDen: false,
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
        timKiem: async function() {
            try {
                this.loading(true)
                let rsVCID = await this.$root.context.get(`/web-ecms/quyhoach-vcid?vlan=${this.vlan ? this.vlan : 0}`);
                this.quyHoachVCIDs = rsVCID.data;
                if(this.quyHoachVCIDs.length == 0) {
                    this.$root.toastError('Không có dữ liệu, vui lòng thử lại.');
                }
                this.$refs.vlanInp.focus();
            } catch (error) {
                this.$toast.error("Lỗi tìm kiếm")
            } finally {
                this.loading(false)
            }
        },
        nhapMoi: function() {
            this.isThemMoi = true;
            this.ttVCID = {
                ...this.ttVCID,
                thietBiId: {
                    DSLAM_ID: null,
                    text: null
                },
                vlan_tu: null,
                vlan_den: null,
                vcid_tu: null,
                vcid_den: null,
                si_tu: null,
                si_den: null,
                tramTbId: null
            }
            this.isErorTramTB = false;
            this.isErorThietBi = false;
            this.isErorVlanTu = false;
            this.isErorVlanDen = false;
            this.isErorVCIDTu = false;
            this.isErorSITu = false;
            this.setDisableBtn(true, false, false, true);
        },
        ghilai: async function() {
            if(this.checkForm()) {
                var {tramTbId, thietBiId, vlan_tu, vlan_den, vcid_tu, si_tu} = {...this.ttVCID};
                var vcid_den = parseInt(vcid_tu) + (vlan_den - vlan_tu);
                var si_den = parseInt(si_tu) + (vlan_den - vlan_tu);
                if(this.isThemMoi) {
                    try {
                        var rs = await this.$root.context.post('/web-ecms/quyhoach-vcid/them-quyhoach-vcid', {
                            donvi_id: tramTbId,
                            dslam_id: thietBiId.DSLAM_ID,
                            vlan_tu,
                            vlan_den,
                            vcid_tu,
                            vcid_den,
                            si_tu,
                            si_den
                        });
                        this.$toast.success("Thêm mới thành công");
                        // this.$refs.grid.currentPageIndex = 0;
                        // this.$refs.grid.setCurrentSelectedRow(0);
                        // this.quyHoachVCIDs.unshift({...rs.data, TENDSLAM: thietBiId.text});
                        this.loadingQuyHoachVCID();
                    } catch (error) {
                        this.$toast.error(error.response.data.message_detail ? error.response.data.message_detail : "Lỗi khi thêm");
                    }
                }
                else {
                    try {
                        await this.$root.context.post(`/web-ecms/quyhoach-vcid/capnhat-quyhoach-vcid?quyhoach_id=${this.ttVCID.quyhoach_id}`, {
                            donvi_id: tramTbId,
                            dslam_id: thietBiId.DSLAM_ID,
                            vlan_tu: vlan_tu,
                            vlan_den: vlan_den,
                            vcid_tu,
                            vcid_den,
                            si_tu,
                            si_den
                        });
                        this.$toast.success("Cập nhật thành công");
                        this.loadingQuyHoachVCID();
                    } catch (error) {
                        this.$toast.error("Cập nhật không thành công!");
                    }
                }
            }
        },
        huy: function() {
            if(this.isDisableBtnHuy) {
                return;
            }
            this.ttVCID = {
                ...this.ttVCID,
                tramTbId: null,
                thietBiId: {
                    DSLAM_ID: null,
                    text: null
                },
                vlan_tu: null,
                vlan_den: null,
                vcid_tu: null,
                vcid_den: null,
                si_tu: null,
                si_den: null
            }
            // if(this.isThemMoi) {
            // } 
            // else {
            //     this.grid_selectedRowChanged(this.quyHoachVCIDs.filter(val => val.QUYHOACH_ID == this.ttVCID.quyhoach_id)[0]);
            // }
            this.isErorTramTB = false;
            this.isErorThietBi = false;
            this.isErorVlanTu = false;
            this.isErorVlanDen = false;
            this.isErorVCIDTu = false;
            this.isErorSITu = false;
            this.setDisableBtn(false, true, true, false);
        },
        xoa: function() {
            if(this.isDisableBtnXoa) {
                return;
            }
            if(this.ttVCID.quyhoach_id) {
                this.$bvModal.msgBoxConfirm('Xác nhận hành động', {
                    title: 'Bạn có chắc chắn xóa dữ liệu không ?',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then(async v => {
                    if(v) {
                        try {
                            await this.$root.context.post(`/web-ecms/quyhoach-vcid/xoa-quyhoach-vcid?quyhoach_id=${this.ttVCID.quyhoach_id}`);
                            this.$toast.success("Xóa thành công");
                            this.loadingQuyHoachVCID();
                            this.setDisableBtn(false, true, true, true);
                        } catch (error) {
                            this.$toast.success('Xóa thất bại');
                        } finally {
                            this.loading(false);
                        }
                    }
                }).catch(err => {
                    this.$toast.error('Xóa thất bại');
                })
            }
        },
        loadingQuyHoachVCID: async function() {
            var rsVCID = await this.$root.context.get('/web-ecms/quyhoach-vcid?vlan=0');
            this.quyHoachVCIDs = rsVCID.data;
        },
        grid_selectedRowChanged: function(val) {
            this.isThemMoi = false;
            this.isErorTramTB = false;
            this.isErorThietBi = false;
            this.isErorVlanTu = false;
            this.isErorVlanDen = false;
            this.isErorVCIDTu = false;
            this.isErorSITu = false;
            if(val) {
                this.ttVCID = {
                    quyhoach_id: val.QUYHOACH_ID,
                    tramTbId: val.DONVI_ID,
                    thietBiId: {
                        DSLAM_ID: val.DSLAM_ID,
                        text: val.TENDSLAM
                    },
                    vlan_tu: val.VLAN_TU,
                    vlan_den: val.VLAN_DEN,
                    vcid_tu: val.VCID_TU,
                    vcid_den: val.VCID_DEN,
                    si_tu: val.SI_TU,
                    si_den: val.SI_DEN
                }
                this.setDisableBtn(false, false, false, false)
            }
        },
        checkForm: function() {
            var {tramTbId, thietBiId, vlan_tu, vlan_den, vcid_tu, vcid_den, si_tu, si_den} = {...this.ttVCID};
            var check = true;
            var error = '';
            if(si_tu < 0 || si_tu == null || si_tu === '') {
                this.isErorSITu = true;
                check = false;
                error = 'SI từ không được để trống và phải lớn hơn hoặc bằng 0 \n';
                this.$refs.situInp.focus();
            }
            else {
                if(si_tu.length > 10) {
                    this.isErorSITu = true;
                    error = 'SI từ không được lớn hơn 10 số \n';
                    check = false;
                    this.$refs.situInp.focus();
                }
                else {
                    this.isErorSITu = false;
                }
            }

            if(vcid_tu < 0 || vcid_tu == null || vcid_tu === '') {
                this.isErorVCIDTu = true;
                check = false;
                error = 'VCID từ không được để trống và phải lớn hơn hoặc bằng 0 \n'.concat(error);
                this.$refs.vcidtuInp.focus();
            }
            else {
                if(vcid_tu.length > 10) {
                    this.isErorVCIDTu = true;
                    error = 'VCID từ không được lớn hơn 10 số \n'.concat(error);
                    check = false;
                    this.$refs.vcidtuInp.focus();
                }
                else {
                    this.isErorVCIDTu = false;
                }
            }

            if(vlan_den < 0 || vlan_den == null || vlan_den === '') {
                this.isErorVlanDen = true;
                check = false;
                error = 'VLAN đến không được để trống và phải lớn hơn hoặc bằng 0 \n'.concat(error);
                this.$refs.vlandenInp.focus();
            }
            else {
                if(vlan_den.length > 10) {
                    this.isErorVlanDen = true;
                    error = 'VLAN đến không được lớn hơn 10 số \n'.concat(error);
                    check = false;
                    this.$refs.vlandenInp.focus();
                }
                else {
                    this.isErorVlanDen = false;
                }
            }

            if(vlan_tu < 0 || vlan_tu == null || vlan_tu === '') {
                this.isErorVlanTu = true;
                check = false;
                error = 'VLAN từ không được để trống và phải lớn hơn hoặc bằng 0 \n'.concat(error);
                this.$refs.vlantuInp.focus();
            }
            else {
                if(vlan_tu.length > 10) {
                    this.isErorVlanTu = true;
                    error = 'VLAN từ không được lớn hơn 10 số \n'.concat(error);
                    check = false;
                    this.$refs.vlantuInp.focus();
                }
                else {
                    this.isErorVlanTu = false;
                }
            }

            if(thietBiId == null || thietBiId.DSLAM_ID == null || thietBiId.DSLAM_ID < 0) {
                this.isErorThietBi = true;
                check = false;
                error = 'Chưa chọn thiết bị \n'.concat(error);
            }
            else {
                this.isErorThietBi = false;
            }

            if(tramTbId < 0 || tramTbId == null) {
                this.$refs.tramTBInp.focus();
                this.isErorTramTB = true;
                check = false;
                error = 'Chưa chọn trạm thiết bị \n'.concat(error);
            }
            else {
                this.isErorTramTB = false;
            }

            if(!check) {
                this.$root.toastError(error)
            }
            return check;
        }
    },
    async mounted() {
        try {
            this.loading(true)
            this.$refs.vlanInp.focus();
            var rsTramTb = await this.$root.context.get('/web-ecms/quyhoach-vcid/ds-tram-tbi');
            this.tramTbs = rsTramTb.data;

            // if(this.tramTbs.length > 0) {
            //     var rsTb = await this.$root.context.get(`/web-ecms/quyhoach-vcid/ds-tbi-theo-tram?donvi_id=${this.tramTbs[0].DONVI_ID}`);
            //     this.ttVCID.tramTbId = this.tramTbs[0].DONVI_ID;
            //     this.thietBis = rsTb.data;
            //     this.ttVCID.thietBiId = {
            //         DSLAM_ID: this.thietBis.length > 0 ? this.thietBis[0].DSLAM_ID : null,
            //         text: this.thietBis.length > 0 ? this.thietBis[0].TENDSLAM : null
            //     }
            // }

            var rsVCID = await this.$root.context.get('/web-ecms/quyhoach-vcid?vlan=0');
            this.quyHoachVCIDs = rsVCID.data;
        } catch (error) {
            this.$toast.error("Lỗi khi tải dữ liệu");
        } finally {
            this.loading(false)
        }
    },
    watch: {
        'ttVCID.tramTbId': async function(newVal) {
            if(newVal) {
                var rsTb = await this.$root.context.get(`/web-ecms/quyhoach-vcid/ds-tbi-theo-tram?donvi_id=${newVal}`);
                this.thietBis = rsTb.data;
                this.ttVCID.thietBiId = {
                    DSLAM_ID: this.thietBis.length > 0 ? this.thietBis[0].DSLAM_ID : null,
                    text: this.thietBis.length > 0 ? this.thietBis[0].TENDSLAM : null
                }
            }
            else {
                this.thietBis = []
            }
        },
        'ttVCID.vlan_tu': function() {
            if(this.ttVCID.vcid_t == null || this.ttVCID.vcid_t === '') {
                this.ttVCID.vcid_den = null;
            }
            else {
                this.ttVCID.vcid_den = parseInt(this.ttVCID.vcid_tu) + (this.ttVCID.vlan_den - this.ttVCID.vlan_tu);
            }

            if(this.ttVCID.si_tu == null || this.ttVCID.si_tu === '') {
                this.ttVCID.si_den = null;
            }
            else {
                this.ttVCID.si_den = parseInt(this.ttVCID.si_tu) + (this.ttVCID.vlan_den - this.ttVCID.vlan_tu);
            }
        },
        'ttVCID.vlan_den': function() {
            if(this.ttVCID.vcid_t == null || this.ttVCID.vcid_t === '') {
                this.ttVCID.vcid_den = null;
            }
            else {
                this.ttVCID.vcid_den = parseInt(this.ttVCID.vcid_tu) + (this.ttVCID.vlan_den - this.ttVCID.vlan_tu);
            }

            if(this.ttVCID.si_tu == null || this.ttVCID.si_tu === '') {
                this.ttVCID.si_den = null;
            }
            else {
                this.ttVCID.si_den = parseInt(this.ttVCID.si_tu) + (this.ttVCID.vlan_den - this.ttVCID.vlan_tu);
            }
        },
        'ttVCID.vcid_tu': function(val) {
            if(val == null || val === '') {
                this.ttVCID.vcid_den = null;
            }
            else {
                this.ttVCID.vcid_den = parseInt(this.ttVCID.vcid_tu) + (this.ttVCID.vlan_den - this.ttVCID.vlan_tu);
            }
        },
        'ttVCID.si_tu': function(val) {
            if(val == null || val === '') {
                this.ttVCID.si_den = null;
            }
            else {
                this.ttVCID.si_den = parseInt(this.ttVCID.si_tu) + (this.ttVCID.vlan_den - this.ttVCID.vlan_tu);
            }
        },
    }
}
</script>

<style scoped>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
