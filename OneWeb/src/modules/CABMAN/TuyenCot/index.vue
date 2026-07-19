<template src="./index.html">
</template>

<script>

export default {
    name: 'TuyenCot',
    components: {},
    data() {
        return {
            donvi_id: null,
            tuyenCot: {
                ttvt_id: null,
                tokt_id: null,
                maTuyenCot: '',
                tenTuyenCot: '',
                chieuDai: 0,
                tuyenCotId: null,
            },
            isCheckToQLDGs: [],
            isCheckToQLCGs: [],
            dsTuyenCotLoading: [],
            dsCotLoading: [],
            isDisabledChieuDai: true,
            dsTTVT: [],
            dsDonVi: [],
            dsToKT: [],
            dsTuyenCot: [],
            dsCot: [],
            dsTQLChuaGan: [],
            dsTQLDaGan: [],
            isLoading: true,
            isDisableBtnNhapMoi: false,
            isDisableBtnGhiLai: true,
            isDisableBtnHuy: true,
            isDisableBtnXoa: true,
            isDisableTTVT: true,
            isErorMaTuyen: false,
            isErorChieuDai: false,
            isErorTenTuyen: false,
            isLoadingFromId: false,
            isDisableSelectDonVi: true
        }
    },
    methods: {
        sangPhai: function() {
            var tempDsDG = [...this.dsTQLDaGan];
            var tempDsCG = [...this.dsTQLChuaGan];
            if(this.isCheckToQLDGs.length) {
                this.isCheckToQLDGs.map(id => {
                    tempDsDG.forEach((toql, index) => {
                        if(toql.DONVI_ID == id) {
                            tempDsCG.push(tempDsDG.splice(index, 1)[0]);
                        }
                    });
                })
                
                this.dsTQLDaGan = JSON.parse(JSON.stringify(tempDsDG));
                this.dsTQLChuaGan = JSON.parse(JSON.stringify(tempDsCG));
                this.isCheckToQLDGs = [];
            }
        },
        sangTrai: function() {
            var tempDsDG = [...this.dsTQLDaGan];
            var tempDsCG = [...this.dsTQLChuaGan];
            if(this.isCheckToQLCGs.length) {
                this.isCheckToQLCGs.map(id => {
                    tempDsCG.forEach((toql, index) => {
                        if(toql.DONVI_ID == id) {
                            tempDsDG.push(tempDsCG.splice(index, 1)[0]);
                        }
                    });
                })
                this.dsTQLChuaGan = JSON.parse(JSON.stringify(tempDsCG));
                this.dsTQLDaGan = JSON.parse(JSON.stringify(tempDsDG));
                this.isCheckToQLCGs = [];
            }
        },
        nhapMoi: function() {
            if(this.isDisableBtnNhapMoi) {
                return;
            }
            this.tuyenCot = {
                ...this.tuyenCot,
                maTuyenCot: '',
                tenTuyenCot: '',
                chieuDai: 0,
                tuyenCotId: null
            };
            this.isDisableSelectDonVi = false;
            this.isDisableTTVT = false;
            this.isDisabledChieuDai = false;
            this.loadDsToQLChuaGan();
            this.dsTQLDaGan = [];
            this.setDisableBtn(true, false, false, true);
        },
        checkForm: function() {
            let checks = [true, true, true, true];
            let error = '';

            if(!this.tuyenCot.tenTuyenCot) {
                checks[0] = false;
                error = 'Chưa nhập tên tuyến cột !\n';
                this.isErorTenTuyen = true;
                this.$refs.tenTuyenInp.focus();
            }
            else {
                if(this.tuyenCot.tenTuyenCot.length > 100) {
                    checks[0] = false;
                    error = 'Tên tuyến cột không được lớn hơn 100 ký tự !\n';
                    this.isErorTenTuyen = true;
                    this.$refs.tenTuyenInp.focus();
                }
                else {
                    checks[0] = true;
                    this.isErorTenTuyen = false;
                }
            }

            if(this.tuyenCot.chieuDai == null || this.tuyenCot.chieuDai === '') {
                checks[1] = false;
                error = 'Chưa nhập chiều dài !\n'.concat(error);
                this.isErorChieuDai = true;
                this.$refs.chieuDaiInp.focus();
            }
            else {
                if(this.tuyenCot.chieuDai.length > 10) {
                    checks[1] = false;
                    error = 'Chiều dài không được lớn hơn 10 số !\n'.concat(error);
                    this.isErorChieuDai = true;
                    this.$refs.chieuDaiInp.focus();
                }
                else {
                    checks[1] = true;
                    this.isErorChieuDai = false;
                }
            }

            if(!this.tuyenCot.maTuyenCot) {
                checks[2] = false;
                error = 'Chưa nhập mã tuyến cột !\n'.concat(error);
                this.isErorMaTuyen = true;
                this.$refs.maTuyenInp.focus();
            }
            else {
                if(this.tuyenCot.maTuyenCot.length > 100) {
                    checks[2] = false;
                    error = 'Mã tuyến cột không được lớn hơn 100 ký tự !\n'.concat(error);
                    this.isErorMaTuyen = true;
                    this.$refs.maTuyenInp.focus();
                }
                else {
                    this.isErorMaTuyen = false;
                    checks[2] = true;
                }
            }

            if(this.dsTQLDaGan.length == 0) {
                error = 'Không được để trống đơn vị\n'.concat(error);
                checks[3] = false;
            }
            else {
                checks[3] = true;
            }

            let kq = checks.every(v => v);
            if(!kq) {
                this.$root.toastError(error);
            }
            return kq;
        },
        ghiLai: async function() {
            if(this.isDisableBtnGhiLai) {
                return;
            }
            if(this.checkForm()) {
                var { maTuyenCot, tenTuyenCot, chieuDai, tuyenCotId } = { ...this.tuyenCot };
                var dsDonViDG_id = this.dsTQLDaGan.map(val => val.DONVI_ID);
                if(tuyenCotId) {
                    try {
                        this.loading(true);
                        var kq = await this.$root.context.post('/web-cabman/TuyenCot/CapNhatTuyenCot', {
                            chieuDai: parseFloat(chieuDai),
                            maTuyenCot,
                            tenTuyenCot,
                            donViId: this.donvi_id,
                            tuyenCotId,
                            dsDonViDGId: dsDonViDG_id
                        })
                        
                        this.$root.toastSuccess('Cập nhật thành công');
                        await this.loadTuyenCot();
                    } catch (error) {
                        this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : 'Cập nhật thất bại');
                    } finally {
                        this.loading(false);
                    }
                }
                else {
                    try {
                        this.loading(true);
                        await this.$root.context.post('/web-cabman/TuyenCot/ThemTuyenCot', {
                            chieuDai: parseFloat(chieuDai),
                            maTuyenCot,
                            tenTuyenCot,
                            donViId: this.donvi_id,
                            dsDonViDGId: dsDonViDG_id
                        });
                        this.tuyenCot = {
                            ...this.tuyenCot,
                            maTuyenCot: '',
                            tenTuyenCot: '',
                            chieuDai: 0,
                            tuyenCotId: null
                        };
                        this.setDisableBtn(false, false, false, false);
                        this.$root.toastSuccess('Thêm mới thành công');
                        await this.loadTuyenCot();
                    } catch (error) {
                        this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : 'Thêm mới thất bại');
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
            this.tuyenCot.tenTuyenCot = null;
            this.tuyenCot.maTuyenCot = null;
            this.tuyenCot.chieuDai = 0;
            this.dsTQLDaGan = [];
            this.dsTQLChuaGan = [];
            this.isDisableTTVT = true;
            this.isErorMaTuyen = false;
            this.isErorChieuDai = false;
            this.isErorTenTuyen = false;
            this.setDisableBtn(false, true, false, false);
        },
        xoa: function() {
            if(this.isDisableBtnXoa) {
                return;
            }
            if(this.tuyenCot.tuyenCotId) {
                this.$bvModal.msgBoxConfirm('Bạc có chắc chắn muốn xóa?', {
                    title: 'Thông báo',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                    centered: true
                }).then(async v => {
                    if(v) {
                        try {
                            this.loading(true);
                            await this.$root.context.post('/web-cabman/TuyenCot/XoaTuyenCot', {
                                tuyenCotId: this.tuyenCot.tuyenCotId
                            })
                            this.$root.toastSuccess('Xóa thành công');
                        } catch (error) {
                            this.$root.toastError('Xóa thất bại, có thể vẫn còn cột trong tuyến');
                        } finally {
                            this.loading(false);
                        }
                        this.loadTuyenCot();
                    }
                }).catch(err => {
                    this.$root.toastError('Xóa thất bại');
                })
            }
        },
        setDisableBtn: function(isNhapmoi, isGhilai, isHuy, isXoa) {
            this.isDisableBtnNhapMoi = isNhapmoi;
            this.isDisableBtnGhiLai = isGhilai;
            this.isDisableBtnHuy = isHuy;
            this.isDisableBtnXoa = isXoa;
        },
        loadTuyenCot: async function() {
            var rsTuyenCot = await this.$root.context.get(`web-cabman/TuyenCot/DanhSachTuyenCotTheoDonVi?donViId=${this.tuyenCot.tokt_id ? this.tuyenCot.tokt_id : -1}`);
            this.dsTuyenCot = rsTuyenCot.data;
            // if(this.dsTuyenCot.length == 0) {
            //    this.setDefault();
            // }
        },
        onPageChangeDsTuyenCot: function(datasource) {
            this.dsTuyenCotLoading = datasource;
        },
        onPageChangeDsCot: function(datasource) {
            this.dsCotLoading = datasource;
        },
        gridDsTuyenCot_selectedRowChanged: async function(data) {
            if(data && this.dsTuyenCot.length > 0) {
                this.tuyenCot.tuyenCotId = data.TUYENCOT_ID;
                this.tuyenCot.maTuyenCot = data.MA_TC;
                this.tuyenCot.tenTuyenCot = data.TUYEN_COT;
                this.tuyenCot.chieuDai = data.CHIEUDAI;
                this.isDisabledChieuDai = false;
                this.isDisableTTVT = false;
                this.donvi_id = this.tuyenCot.ttvt_id;
                this.isErorMaTuyen = false;
                this.isErorChieuDai = false;
                this.isErorTenTuyen = false;

                var rsDS = await this.$root.context.get(`web-cabman/TuyenCot/DsCotVaToQuanLyTheoTuyen?ttvtId=${this.donvi_id}&tuyenCotId=${this.tuyenCot.tuyenCotId}`);
                this.dsCot = rsDS.data.dsCot;
                this.dsTQLChuaGan = rsDS.data.toQuanLyChuaGan;
                this.dsTQLDaGan = rsDS.data.toQuanLyDaGan;
                this.setDisableBtn(false, false, false, false);
            }
        },
        gridDsDaGan_selectedRowChanged: function(data) {

        },
        gridDsChuaGan_selectedRowChanged: function(data) {

        },
        selectedDsDaGan: function(data) {
            this.isCheckToQLDGs = data;
        },
        selectedDsChuaGan: function(data) {
            this.isCheckToQLCGs = data;
        },
        // Xài ké bên tuyến cống bể
        // 2: Loại tuyến cột, 5: loaidv trạm th thi công
        loadDsToQLChuaGan: async function() {
            try {
                var rs = await this.$root.context.post('/web-cabman/danhmuc/tuyen-cong-be/ds-toql-chuagan', {
                    tuyencong_id: this.tuyenCot.tuyenCotId,
                    loaidv_id: 5,
                    loaituyen_id: 2,
                    donvi_id: this.donvi_id
                });
                this.dsTQLChuaGan = rs.data;
            } catch (error) {
                
            }
        },
        setDefault: function() {
            if(!this.isDisableBtnNhapMoi) {
                this.tuyenCot.maTuyenCot = '';
                this.tuyenCot.tenTuyenCot = '';
                this.tuyenCot.chieuDai = 0;
                this.tuyenCot.tuyenCotId = null;
                this.setDisableBtn(false, true, true, true);
            }
            this.isDisabledChieuDai = true;
            this.dsTuyenCot = [];
            this.dsCot = [];
            // this.dsTQLChuaGan = [];
            this.dsTQLDaGan = [];
        },
        initData: async function(id){
            if(id) {
                this.tuyenCot.tuyenCotId = id;
                var rsTTVT_ToKT = await this.$root.context.post(`/web-cabman/TuyenCot/get-ttvt-tokt-by-tuyencotId/${this.tuyenCot.tuyenCotId}`, {
                    loaituyen_id: 2,
                    loaidv_id: 3
                });
                if(rsTTVT_ToKT.data) {
                    this.isLoadingFromId = true;
                    this.tuyenCot.ttvt_id = rsTTVT_ToKT.data.TTVT;
                    this.tuyenCot.tokt_id = rsTTVT_ToKT.data.TOKT;
                }
            }
        }
    },
    async mounted() {
        this.loading(true);
        try{
            this.isLoading = true;
            this.isDisableSelectDonVi = true;
            var kqTTVT = await this.$root.context.get('/web-cabman/donvi/ds-ttvt-theo-nhanvien');
            this.dsTTVT = kqTTVT.data;
            this.dsDonVi = JSON.parse(JSON.stringify(kqTTVT.data));
            this.tuyenCot.ttvt_id = this.dsTTVT.length > 0 ? this.dsTTVT[0].DONVI_ID : null;
            this.donvi_id = this.dsDonVi.length > 0 ? this.dsDonVi[0].DONVI_ID : null;
            // if(this.dsTTVT.length > 0) {
            //     var kqTKT = await this.$root.context.get(`/web-cabman/donvi/ds-to-kythuat-theo-nhanvien/${this.tuyenCot.ttvt_id}`);
            //     this.dsToKT = kqTKT.data;
            //     // if(this.dsToKT.length > 0) {
            //     //     this.tuyenCot.tokt_id = this.dsToKT[0].DONVI_ID;
            //     //     this.loadTuyenCot();
            //     // }
            // }
            if(this.$route.query.id) {
                this.tuyenCot.tuyenCotId = this.$route.query.id;
                var rsTTVT_ToKT = await this.$root.context.post(`/web-cabman/TuyenCot/get-ttvt-tokt-by-tuyencotId/${this.tuyenCot.tuyenCotId}`, {
                    loaituyen_id: 2,
                    loaidv_id: 3
                });
                if(rsTTVT_ToKT.data) {
                    this.isLoadingFromId = true;
                    this.tuyenCot.ttvt_id = rsTTVT_ToKT.data.TTVT;
                    this.tuyenCot.tokt_id = rsTTVT_ToKT.data.TOKT;
                }
            }
        } catch (err) {
        } finally {
            this.loading(false);
        }
    },
    watch: {
        'tuyenCot.tuyenCotId': async function(newVal, oldVal) {
            // if(newVal) {
            //     var rsDS = await this.$root.context.get(`web-cabman/TuyenCot/DsCotVaToQuanLyTheoTuyen?ttvtId=${this.donvi_id}&tuyenCotId=${this.tuyenCot.tuyenCotId}`);
            //     this.dsCot = rsDS.data.dsCot;
            //     this.dsTQLChuaGan = rsDS.data.toQuanLyChuaGan;
            //     this.dsTQLDaGan = rsDS.data.toQuanLyDaGan;
            // }
        },
        'tuyenCot.ttvt_id': async function(newVal) {
            if(newVal) {
                // var kqTKT = await this.$root.context.get(`web-cabman/donvi/ds-to-kythuat-theo-nhanvien/${newVal}`);
                var kqTKT = await this.$root.context.post('/web-cabman/danhmuc/tuyen-cong-be/list-to-ql', {
                    donvi_id: newVal,
                    loaidv_id: 5,
                });
                this.dsToKT = kqTKT.data;
                // this.tuyenCot.tokt_id = this.dsToKT.length > 0 ? this.dsToKT[0].DONVI_ID : null;
                if(!this.isLoadingFromId) {
                    this.tuyenCot.tokt_id = null;
                }
            }
        },
        'tuyenCot.tokt_id': async function(newVal) {
            try {
                this.loading(true);
                if(newVal) {
                    await this.loadTuyenCot();
                    if(this.isLoadingFromId) {
                        let idx = this.dsTuyenCot.findIndex(v => v.TUYENCOT_ID == this.tuyenCot.tuyenCotId);
                        this.isLoadingFromId = false;
                        setTimeout(() => {
                            this.$refs.gridTC.setCurrentSelectedRow(idx ? idx : 0);
                        }, 500);
                    }
                }
                else {
                    this.setDefault();
                    this.loadDsToQLChuaGan();
                }
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        donvi_id: async function(newVal) {
            if(newVal && !this.isLoading) {
                this.loadDsToQLChuaGan();
            }
            else {
                this.isLoading = false;
            }
        }
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
