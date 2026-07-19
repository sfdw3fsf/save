<template src="./index.html">
</template>

<script>
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required} from 'vuelidate/lib/validators'
export default {
    components: { BssErrorMarker, BssRequiredMarker},
    validations: {
        tramTb: {
            tramTbId: { required }
        },
        thietBi: { required },
        loaiKetNoi: { required },
        huongKn: { required },
        phuongThucKn: { required }
    },
    data() {
        return {
            title: null,
            kieu: null,
            dsTramTb: [],
            dsThietBi: [],
            dsLoaiKn: [],
            dsHuongKn: [],
            dsPhuongThucKn: [
                {
                    METHOD_ID: 0,
                    METHOD: "PPPoE"
                },
                {
                    METHOD_ID: 1,
                    METHOD: "IPoE"
                }
            ],
            dsSCVLAN: [],
            tramTb: {
                tramTbId: null,
                vung_id: null,
            },
            thietBi: null,
            loaiKetNoi: 1,
            phuongThucKn: 0,
            huongKn: null,
            svlan: null,
            cvlan_tu: null,
            cvlan_den: null,
            svlan_tu: null,
            svlan_den: null,
            quyHoachId: null,
            isErorSvlan_tu: false,
            isErorSvlan_den: false,
            isErorTramTb: false,
            isErorSvlan: false,
            isErorThietBi: false,
            isErorCvlan_tu: false,
            isErorLoaiKn: false,
            isErorCvlan_den: false,
            isErorPhuongThucKn: false,
            isErorHuongKn: false,
            isDisableBtnNhapMoi: false,
            isDisableBtnGhiLai: true,
            isDisableBtnHuy: true,
            isDisableBtnXoa: true,
            currentItem: {},
            isLoading: false
        }
    },
    methods: {
        setDisableBtn: function (isNhapmoi, isGhilai, isHuy, isXoa) {
            this.isDisableBtnNhapMoi = isNhapmoi;
            this.isDisableBtnGhiLai = isGhilai;
            this.isDisableBtnHuy = isHuy;
            this.isDisableBtnXoa = isXoa;
        },
        layDsHuongKetNoi: async function(vung_id, loaikn_id) {
            var rsHuongKn = await this.$root.context.post('/web-ecms/danhmuc/quyhoach_scvlan/dshuongketnoi', {
                kieu: this.kieu,
                vung_id: vung_id
            })
            this.dsHuongKn = rsHuongKn.data.filter(v => v.LOAIKN_ID == loaikn_id);
            // this.huongKn = this.dsHuongKn.length > 0 ? this.dsHuongKn[0].HUONGKN_ID : null;
        },
        layDsSCVLAN: async function() {
            var rsSCVLAN = await this.$root.context.post('/web-ecms/danhmuc/quyhoach_scvlan/danhsach', {
                kieu: this.kieu,
                svlan_tu: 0,
                svlan_den: 100000
            });
            this.dsSCVLAN = rsSCVLAN.data;
        },
        grid_selectedRowChanged: function(dataItem) {
            if(dataItem) {
                this.currentItem = dataItem;
                this.quyHoachId = dataItem.QUYHOACH_ID;
                this.svlan = dataItem.SVLAN;
                this.cvlan_tu = dataItem.CVLAN_TU;
                this.cvlan_den = dataItem.CVLAN_DEN;
                this.tramTb.tramTbId = dataItem.DONVI_ID;
                this.thietBi = dataItem.DSLAM_ID;
                this.loaiKetNoi = dataItem.LOAIKN_ID;
                this.phuongThucKn = dataItem.METHOD;
                this.huongKn = dataItem.HUONGKN_ID;
            }
            this.resetError();
            this.setDisableBtn(false, false, false, false);
        },
        timKiem: async function() {
            // if(!this.svlan_tu) {
            //     this.isErorSvlan_tu = true;
            //     this.$refs.svlan_tu_inp.focus();
            //     this.$toast.error('Chưa nhập SVLAN từ');
            // }
            // else if(!this.svlan_den) {
            //     this.isErorSvlan_den = true;
            //     this.$refs.svlan_den_inp.focus();
            //     this.$toast.error('Chưa nhập SVLAN đến');
            // }
            // else {
                
            // }
            this.loading(true);
                try {
                    var rsSCVLAN = await this.$root.context.post('/web-ecms/danhmuc/quyhoach_scvlan/danhsach', {
                        kieu: this.kieu,
                        svlan_tu: this.svlan_tu ? this.svlan_tu : 0,
                        svlan_den: this.svlan_den ? this.svlan_den : 100000
                    });
                    this.dsSCVLAN = rsSCVLAN.data;
                    if(this.dsSCVLAN.length == 0) {
                        this.$toast.error('Không có dữ liệu, vui lòng thử lại.');
                    }
                    this.resetError();
            } catch (error) {
                this.$toast.error('Xảy ra lỗi');
            } finally {
                this.loading(false);
            }
        },
        checkForm: function() {
            this.$v.$touch()
            let check = true;
            let error = '';
            if(!this.cvlan_den && this.cvlan_den !== 0) {
                this.isErorCvlan_den = true;
                check = false;
                error = 'CVLAN đến bắt buộc nhập dữ liệu.';
                this.$refs.cvlan_den.focus();
            }
            else {
                if(this.cvlan_den < 0) {
                    this.isErorCvlan_den = true;
                    check = false;
                    error = 'CVLAN đến phải lơn hơn hoặc bằng 0';
                }
                else if(!Number.isInteger(parseFloat(this.cvlan_den))) {
                    this.isErorCvlan_den = true;
                    check = false;
                    error = 'CVLAN đến phải là số nguyên lớn hơn hoặc bằng 0';
                }
                else {
                    this.isErorCvlan_den = false;
                }
            }

            if(!this.cvlan_tu && this.cvlan_tu !== 0) {
                this.isErorCvlan_tu = true;
                check = false;
                error = 'CVLAN từ bắt buộc nhập dữ liệu.\n'.concat(error);
                this.$refs.cvlan_tu.focus();
            }
            else {
                if(this.cvlan_tu < 0) {
                    this.isErorCvlan_tu = true;
                    check = false;
                    error = 'CVLAN từ phải lơn hơn hoặc bằng 0 \n'.concat(error);
                }
                else if(!Number.isInteger(parseFloat(this.cvlan_tu))) {
                    this.isErorCvlan_tu = true;
                    check = false;
                    error = 'CVLAN từ phải là số nguyên lớn hơn hoặc bằng 0 \n'.concat(error);
                }
                else {
                    this.isErorCvlan_tu = false;
                }
            }

            if(this.cvlan_tu && this.cvlan_den) {
                if(this.cvlan_den - this.cvlan_tu < 0) {
                    check = false;
                    this.isErorCvlan_den = true;
                    this.isErorCvlan_tu = true;
                    error = 'CVLAN đến phải lớn hơn hoặc bằng CVLAN từ \n'.concat(error);
                    this.$refs.cvlan_tu.focus();
                }
            }

            if(!this.svlan && this.svlan !== 0) {
                this.isErorSvlan = true;
                check = false;
                error = 'SVLAN bắt buộc nhập dữ liệu.\n'.concat(error);
                this.$refs.svlan.focus();
            }
            else {
                if(this.svlan < 0) {
                    this.isErorSvlan = true;
                    check = false;
                    error = 'SVLAN phải lơn hơn hoặc bằng 0\n'.concat(error);
                }
                else if(!Number.isInteger(parseFloat(this.svlan))) {
                    this.isErorSvlan = true;
                    check = false;
                    error = 'SVLAN phải là số nguyên lớn hơn hoặc bằng 0\n'.concat(error);
                }
                else {
                    this.isErorSvlan = false;
                }
            }

            if( this.$v.$invalid && !this.$v.huongKn.required) {
                check = false;
                error = 'Hướng kết nối bắt buộc nhập dữ liệu.\n'.concat(error);
            }

            if(this.$v.$invalid && !this.$v.thietBi.required) {
                check = false;
                error = 'Thiết bị bắt buộc nhập dữ liệu.\n'.concat(error);
            }

            if(this.$v.$invalid && !this.$v.loaiKetNoi.required) {
                check = false;
                error = 'Loại kết nối bắt buộc nhập dữ liệu.\n'.concat(error);
            }

            if(this.$v.$invalid && !this.$v.tramTb.tramTbId.required) {
                check = false;
                error = 'Trạm thiết bị bắt buộc nhập dữ liệu.\n'.concat(error);
            }

            if(!check) {
                this.$root.toastError(error)
            }
            return check;
        },
        nhapMoi: function() {
            if(this.isDisableBtnNhapMoi) {
                return;
            }
            this.quyHoachId = null;
            this.$refs.svlan.focus();
            // this.resetForm();
            this.thietBi = this.dsThietBi.length > 0 ? this.dsThietBi[0].DSLAM_ID : null ;
            this.huongKn = this.dsHuongKn.length > 0 ? this.dsHuongKn[0].HUONGKN_ID : null;
            this.phuongThucKn = 0;
            this.loaiKetNoi = this.dsLoaiKn.length > 0 ? this.dsLoaiKn[0].LOAIKN_ID : null;
            this.svlan = null;
            this.cvlan_tu = null;
            this.cvlan_den = null;
            this.setDisableBtn(true, false, false, true);
        },
        ghiLai: async function() {
            if(this.isDisableBtnGhiLai) {
                return
            }
            if(this.checkForm()) {
                let existObj = this.dsSCVLAN.find(v => {
                    if( v.QUYHOACH_ID != this.quyHoachId &&
                        v.DONVI_ID == this.tramTb.tramTbId && 
                        v.DSLAM_ID == this.thietBi && 
                        v.LOAIKN_ID == this.loaiKetNoi && 
                        v.METHOD == this.phuongThucKn &&
                        v.HUONGKN_ID == this.huongKn &&
                        v.SVLAN == this.svlan &&
                        v.CVLAN_TU == this.cvlan_tu &&
                        v.CVLAN_DEN == this.cvlan_den) {
                            return true;
                        }
                })
                if(existObj) {
                    this.$toast.error('Thông tin quy hoạch SCVLAN đã tồn tại');
                    return;
                }
                if(this.quyHoachId) {
                    try {
                        this.loading(true);
                        await this.$root.context.post('/web-ecms/danhmuc/quyhoach_scvlan/sua', {
                            quyhoach_id: this.quyHoachId,
                            donvi_id: this.tramTb.tramTbId,
                            dslam_id: this.thietBi,
                            svlan: this.svlan,
                            cvlan_tu: this.cvlan_tu,
                            cvlan_den: this.cvlan_den,
                            huongkn_id: this.huongKn,
                            method: this.phuongThucKn
                        });
                        this.$toast.success('Cập nhật thành công');
                        this.layDsSCVLAN();
                        this.resetError();
                    } catch (error) {
                        this.$toast.error('Cập nhật thất bại');
                    } finally {
                        this.loading(false);
                    }
                }
                else {
                    try {
                        this.loading(true);
                        await this.$root.context.post('/web-ecms/danhmuc/quyhoach_scvlan/them', {
                            donvi_id: this.tramTb.tramTbId,
                            dslam_id: this.thietBi,
                            svlan: this.svlan,
                            cvlan_tu: this.cvlan_tu,
                            cvlan_den: this.cvlan_den,
                            huongkn_id: this.huongKn,
                            method: this.phuongThucKn
                        });
                        this.$toast.success('Thêm mới thành công');
                        this.layDsSCVLAN();
                        this.resetError();
                    } catch (error) {
                        this.$toast.error('Thêm mới thất bại');
                    } finally {
                        this.loading(false);
                    }
                }
            }
        },
        huy: function() {
            if(this.isDisableBtnHuy) {
                return;
            }
            // if(this.quyHoachId) {
            //     this.grid_selectedRowChanged(this.currentItem);
            // }
            // else {
            //     this.$refs.svlan.focus();
            //     this.svlan = null;
            //     this.cvlan_tu = null;
            //     this.cvlan_den = null;
            //     this.phuongThucKn = 0;
            //     this.setDisableBtn(false, true, true, true);
            // }
            this.$refs.svlan.focus();
            this.svlan = null;
            this.cvlan_tu = null;
            this.cvlan_den = null;
            this.phuongThucKn = 0;
            this.thietBi = this.dsThietBi.length > 0 ? this.dsThietBi[0].DSLAM_ID : null ;
            this.huongKn = this.dsHuongKn.length > 0 ? this.dsHuongKn[0].HUONGKN_ID : null;
            this.loaiKetNoi = this.dsLoaiKn.length > 0 ? this.dsLoaiKn[0].LOAIKN_ID : null;
            this.setDisableBtn(false, true, true, true);
        },
        xoa: function() {
            if(this.isDisableBtnXoa) {
                return;
            }
            if(this.quyHoachId) {
                this.$bvModal.msgBoxConfirm('Bạn có chắc chắn xóa dữ liệu không ?', {
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then(async v => {
                    if(v) {
                        try {
                            await this.$root.context.post(`/web-ecms/danhmuc/quyhoach_scvlan/xoa/${this.quyHoachId}`);
                            this.$toast.success("Xóa thành công");
                            this.layDsSCVLAN();
                        } catch (error) {
                            this.$toast.error(error.response.data.message_detail ? error.response.data.message_detail : 'Xóa thất bại');
                        } finally {
                            this.loading(false);
                        }
                    }
                }).catch(err => {
                    this.$toast.error('Xóa thất bại');
                })
            }
        },
        resetError: function() {
            this.isErorSvlan_tu = false,
            this.isErorSvlan_den = false,
            this.isErorTramTb = false,
            this.isErorSvlan = false,
            this.isErorThietBi = false,
            this.isErorCvlan_tu = false,
            this.isErorLoaiKn = false,
            this.isErorCvlan_den = false
        },
        resetForm: function() {
            this.quyHoachId = null;
            this.svlan = null;
            this.cvlan_tu = null;
            this.cvlan_den = null;
            this.tramTb = {
                tramTbId: null,
                vung_id: null
            };
            this.thietBi = null;
            this.loaiKetNoi = null;
            this.phuongThucKn = 0;
            this.huongKn = null;
            this.svlan_tu = null;
            this.svlan_den = null;
            this.resetError();
        }
    },
    // async mounted() {
    //     try {
    //         this.loading(true);
    //         var rsTramTb = await this.$root.context.get('web-ecms/danhmuc/quyhoach_scvlan/dstram_tbi');
    //         this.dsTramTb = rsTramTb.data;
    //         if(this.dsTramTb.length > 0) {
    //             this.tramTb.tramTbId = this.dsTramTb[0].DONVI_ID;
    //             this.tramTb.vung_id = this.dsTramTb[0].DONVI_QL_ID;
    //         }
    //         this.layDsSCVLAN();
    //     } catch (error) {
            
    //     } finally {
    //         this.loading(false);
    //     }
    // },
    created: async function() {
        let rs = await this.$root.context.get('web-ecms/danhmuc/quyhoach_scvlan/dsMethod');
        this.dsPhuongThucKn = rs.data;
        if(this.$route.path == '/ecms/quy-hoach-qinq-vpn-noitinh') {
            this.kieu = 1;
            this.title = 'QUY HOẠCH SCVLAN NỘI TỈNH'
        }
        else if(this.$route.path == '/ecms/quy-hoach-qinq-vpn-lientinh') {
            this.kieu = 2;
            this.title = 'QUY HOẠCH SCVLAN LIÊN TỈNH';
        }
        else if(this.$route.path == '/ecms/quy-hoach-qinq-internet-tructiep') {
            this.kieu = 3;
            this.title = 'QUY HOẠCH SCVLAN INTERNET TRỰC TIẾP';
        }
        else if(this.$route.name == 'QuyHoachSCVLANFiber') {
            this.kieu = 4;
            this.title = 'QUY HOẠCH SCVLAN FIBER';
        }
    },
    watch: {
        'tramTb.tramTbId': async function(newVal) {
            if(newVal) {
                try {
                    this.loading(true);
                    var rsThietBi = await this.$root.context.post('/web-ecms/danhmuc/quyhoach_scvlan/dmthietbi', {
                        kieu: this.kieu,
                        donvi_id: newVal
                    });
                    this.dsThietBi = rsThietBi.data;
                    this.thietBi = this.dsThietBi.length > 0 ? this.dsThietBi[0].DSLAM_ID : null;

                    var rsLoaiKn = await this.$root.context.get('/web-ecms/danhmuc/quyhoach_scvlan/dmhuongketnoi');
                    this.dsLoaiKn = rsLoaiKn.data.loai_kn;
                    // this.loaiKetNoi = this.dsLoaiKn.length > 0 ? this.dsLoaiKn[0].LOAIKN_ID : null;
                    this.tramTb.vung_id = this.dsTramTb.find(v => v.DONVI_ID == newVal).DONVI_QL_ID;
                    await this.layDsHuongKetNoi(this.tramTb.vung_id, this.loaiKetNoi); 
                    if(this.isLoading) {
                        await this.layDsSCVLAN();
                        this.isLoading = false;
                    }
                } catch (error) {
                    
                } finally {
                    this.loading(false);
                }
            }
        },
        loaiKetNoi: async function(newVal) {
            if(newVal)
                try {
                    this.loading(true);
                    await this.layDsHuongKetNoi(this.tramTb.vung_id, newVal);
                } catch (error) {
                    
                } finally {
                    this.loading(false);
                }
        },
        kieu: async function(newVal) {
            try {
                this.loading(true);
                this.$refs.svlan_tu_inp.focus();
                this.isLoading = true;
                var rsTramTb = await this.$root.context.get('web-ecms/danhmuc/quyhoach_scvlan/dstram_tbi');
                this.dsTramTb = rsTramTb.data;
                if(this.dsTramTb.length > 0) {
                    this.tramTb.tramTbId = this.dsTramTb[0].DONVI_ID;
                    this.tramTb.vung_id = this.dsTramTb[0].DONVI_QL_ID;
                    await this.layDsHuongKetNoi(this.tramTb.vung_id, this.loaiKetNoi);
                }
                else {
                    await this.layDsSCVLAN();
                }

            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        '$route.name': async function(newVal) {
            this.resetForm();
            if(this.$route.path == '/ecms/quy-hoach-qinq-vpn-noitinh') {
                this.kieu = 1;
                this.title = 'QUY HOẠCH SCVLAN NỘI TỈNH';
            }
            else if(this.$route.path == '/ecms/quy-hoach-qinq-vpn-lientinh') {
                this.kieu = 2;
                this.title = 'QUY HOẠCH SCVLAN LIÊN TỈNH';
            }
            else if(this.$route.path == '/ecms/quy-hoach-qinq-internet-tructiep') {
                this.kieu = 3;
                this.title = 'QUY HOẠCH SCVLAN INTERNET TRỰC TIẾP';
            }
            else if(this.$route.name == 'QuyHoachSCVLANFiber') {
                this.kieu = 4;
                this.title = 'QUY HOẠCH SCVLAN FIBER';
            }
        }
    }
}
</script>

<style>
a.disable-btn-scvlan {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
.text-blue {
    color: blue !important;
}
.text-red {
    color: red !important;
}
.text-green {
    color: green !important;
}
</style>
