<template src="./index.html">
</template>

<script>
export default {
    data() {
        return {
            donvi_id: null,
            ttvt_id: null,
            tokt_id: null,
            loaidv: {
                donviql_ld: 3,
                tram_vt: 5
            },
            loaituyen_id: 3,
            tuyenCB: {
                tuyenCB_id: null,
                maTuyen: null,
                chieuDai: 0,
                tenTuyenCB: null
            },
            disabledChieuDai: true,
            dsDonVi: [],
            dsTTVT: [],
            dsToKT: [],
            dsTuyenCB: [],
            dsCBThuocTuyen: [],
            dsTQLDaGan: [],
            dsTQLChuaGan: [],
            dsSelectedDaGan: [],
            dsSelectedChuaGan: [],
            isErorMaTuyen: false,
            isErorChieuDai: false,
            isErorTenTuyen: false,
            isDisableBtnNhapMoi: false,
            isDisableBtnGhiLai: true,
            isDisableBtnHuy: true,
            isDisableBtnXoa: true,
            isLoadingFromId: false
        }
    },
    methods: {
        setDisableBtn: function(isNhapmoi, isGhilai, isHuy, isXoa) {
            this.isDisableBtnNhapMoi = isNhapmoi;
            this.isDisableBtnGhiLai = isGhilai;
            this.isDisableBtnHuy = isHuy;
            this.isDisableBtnXoa = isXoa;
        },
        loadCongBeThuocTuyen: async function() {
            try {
                var rs = await this.$root.context.post('/web-cabman/danhmuc/tuyen-cong-be/ds-cong-va-toql-theo-tuyen', {
                    tuyencong_id: this.tuyenCB.tuyenCB_id,
                    loaidv_id: this.loaidv.tram_vt,
                    loaituyen_id: this.loaituyen_id,
                    donvi_id: this.ttvt_id
                })
                this.dsCBThuocTuyen = rs.data.dscongbe;
                this.dsTQLDaGan = rs.data.toquanlydagan;
                this.dsTQLChuaGan = rs.data.toquanlychuagan;
            } catch (error) {

            }
        },
        loadDsToQLChuaGan: async function() {
            try {
                var rs = await this.$root.context.post('/web-cabman/danhmuc/tuyen-cong-be/ds-toql-chuagan', {
                    tuyencong_id: this.tuyenCB.tuyenCB_id,
                    loaidv_id: this.loaidv.tram_vt,
                    loaituyen_id: this.loaituyen_id,
                    donvi_id: this.ttvt_id
                });
                this.dsTQLChuaGan = rs.data;
            } catch (error) {

            }
        },
        loadDsTuyenCongBe: async function() {
            try {
                var rs = await this.$root.context.post('/web-cabman/danhmuc/tuyen-cong-be/list-tuyen-cong-be', {
                    donvi_id: this.tokt_id,
                    loaituyen_id: this.loaituyen_id
                });
                this.dsTuyenCB = rs.data;
                if(this.dsTuyenCB.length == 0) {
                    this.tuyenCB.tuyenCB_id = null;
                }
            } catch (error) {

            }
        },
        sangPhai: function() {
            if(this.dsSelectedDaGan.length > 0 && this.dsTQLDaGan.length > 0) {
                var temp = [];
                this.dsSelectedDaGan.map(v => {
                    var idx = this.dsTQLDaGan.findIndex(el => el.DONVI_ID == v);
                    temp.push(this.dsTQLDaGan[idx]);
                    this.dsTQLDaGan.splice(idx, 1);
                    this.dsTQLDaGan = [...this.dsTQLDaGan];
                });
                this.dsTQLChuaGan = this.dsTQLChuaGan.concat(temp);
            }
        },
        sangTrai: function() {
            if(this.dsSelectedChuaGan.length > 0 && this.dsTQLChuaGan.length > 0) {
                var temp = [];
                this.dsSelectedChuaGan.map(v => {
                    var idx = this.dsTQLChuaGan.findIndex(el => el.DONVI_ID == v);
                    temp.push(this.dsTQLChuaGan[idx]);
                    this.dsTQLChuaGan.splice(idx, 1);
                    this.dsTQLChuaGan = [...this.dsTQLChuaGan];
                });
                this.dsTQLDaGan = this.dsTQLDaGan.concat(temp);
            }
        },
        gridDsTuyenCongBe_selectedRowChanged: function(dataItem) {
            if(dataItem && this.dsTuyenCB.length > 0) {
                this.setDisableBtn(false, false, false, false);
                this.tuyenCB.tuyenCB_id = dataItem.TUYENCB_ID;
                this.tuyenCB.maTuyen = dataItem.MA_TCB;
                this.tuyenCB.tenTuyenCB = dataItem.TUYEN_CB;
                this.tuyenCB.chieuDai = dataItem.CHIEUDAI;
                this.disabledChieuDai = false;
                this.ttvt_id = this.donvi_id;
                this.isErorMaTuyen = false;
                this.isErorChieuDai = false;
                this.isErorTenTuyen = false;
                this.loadCongBeThuocTuyen();
            }
        },
        selectedDsChuaGan: function(data) {
            this.dsSelectedChuaGan = data;
        },
        selectedDsDaGan: function(data) {
            this.dsSelectedDaGan = data;
        },
        checkForm: function() {
            var checks = [true, true, true, true];
            var error = '';
            if(this.dsTQLDaGan.length == 0) {
                error = 'Không được để trống đơn vị';
                checks[0] = false;
            }
            else {
                checks[0] = true;
            }

            if(!this.tuyenCB.tenTuyenCB) {
                this.isErorTenTuyen = true;
                this.$refs.tenTuyenInp.focus();
                checks[1] = false;
                error = 'Chưa nhập tên tuyến cống bể\n'.concat(error);
            }
            else {
                this.isErorTenTuyen = false;
                checks[1] = true;
            }

            if(this.tuyenCB.chieuDai == null || this.tuyenCB.chieuDai === '') {
                this.isErorChieuDai = true;
                this.$refs.chieuDaiInp.focus();
                checks[2] = false;
                error = 'Chưa nhập chiều dài\n'.concat(error);
            }
            else {
                this.isErorChieuDai = false;
                checks[2] = true;
            }

            if(!this.tuyenCB.maTuyen) {
                this.isErorMaTuyen = true;
                this.$refs.maTuyenInp.focus();
                checks[3] = false;
                error = 'Chưa nhập mã tuyến cống bể \n'.concat(error);
            }
            else {
                this.isErorMaTuyen = false;
                checks[3] = true;
            }

            var kq = checks.every(v => v);
            if(!kq) {
                this.$root.toastError(error);
            }
            return kq;
        },
        nhapMoi: function() {
            if(this.isDisableBtnNhapMoi) {
                return;
            }
            this.tuyenCB = {
                tuyenCB_id: null,
                maTuyen: null,
                chieuDai: 0,
                tenTuyenCB: null
            }
            this.dsTQLDaGan = [];
            this.loadDsToQLChuaGan();
            this.disabledChieuDai = false;
            this.setDisableBtn(true, false, false, true);
        },
        ghiLai: async function() {
            if(this.isDisableBtnGhiLai) {
                return;
            }
            if(this.checkForm()) {
                var toqldg_id = this.dsTQLDaGan.map(v => v.DONVI_ID);
                if(this.tuyenCB.tuyenCB_id) {
                    try {
                        this.loading(true)
                        await this.$root.context.post(`/web-cabman/danhmuc/tuyen-cong-be/update-tuyen-cong-be/${this.tuyenCB.tuyenCB_id}`, {
                            ma_tcb: this.tuyenCB.maTuyen,
                            tuyen_cb: this.tuyenCB.tenTuyenCB,
                            chieudai: this.tuyenCB.chieuDai,
                            so_becap: 0,
                            so_lopi: 1,
                            donviql_id: this.tokt_id,
                            donvidg_id: toqldg_id,
                            loaituyen_id: this.loaituyen_id
                        });
                        this.$root.toastSuccess('Cập nhật thành công');
                        this.setDisableBtn(false, true, true, true);
                    } catch (error) {
                        this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : 'Cập nhật thất bại');
                    } finally {
                        this.loading(false);
                    }
                }
                else {
                    try {
                        this.loading(true)
                        await this.$root.context.post('/web-cabman/danhmuc/tuyen-cong-be/insert-tuyen-cong-be', {
                            ma_tcb: this.tuyenCB.maTuyen,
                            tuyen_cb: this.tuyenCB.tenTuyenCB,
                            chieudai: this.tuyenCB.chieuDai,
                            so_becap: 0,
                            so_lopi: 1,
                            donviql_id: this.tokt_id,
                            donvidg_id: toqldg_id,
                            loaituyen_id: this.loaituyen_id
                        });
                        this.$root.toastSuccess('Thêm mới thành công');
                        this.setDisableBtn(false, true, true, true);
                    } catch (error) {
						this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : 'Thêm mới thất bại');
                    } finally {
                        this.loading(false);
                    }
                }
                this.disabledChieuDai = true;
                this.loadDsTuyenCongBe();
            }
        },
        huy: function() {
            if(this.isDisableBtnHuy) {
                return;
            }
            this.tuyenCB.maTuyen = null;
            this.tuyenCB.chieuDai = 0;
            this.tuyenCB.tenTuyenCB = null;
            this.dsTQLDaGan = [];
            this.dsTQLChuaGan = [];
            this.isErorMaTuyen = false;
            this.isErorChieuDai = false;
            this.isErorTenTuyen = false;
            this.setDisableBtn(false, true, true, true);
        },
        xoa: function() {
            if(this.isDisableBtnXoa) {
                return;
            }
            if(this.tuyenCB.tuyenCB_id) {
                this.$bvModal.msgBoxConfirm('Xác nhận hành động', {
                    title: 'Bạn có chắc chắn xóa dữ liệu không ?',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then(async v => {
                    if(v) {
                        try {
                            if(await this.kiemTraTonTai()) {
                                this.$root.toastError('Vẫn còn cột trong tuyến cống bể. Không thể xóa!');
                            }
                            else {
                                var rs = await this.$root.context.post(`/web-cabman/danhmuc/tuyen-cong-be/delete-tuyen-cong-be/${this.tuyenCB.tuyenCB_id}`, {
                                    loaituyen_id: this.loaituyen_id
                                });
                                if(rs.data.RESULT) {
                                    this.tuyenCB.maTuyen = null;
                                    this.tuyenCB.chieuDai = 0;
                                    this.tuyenCB.tenTuyenCB = null;
                                    this.$root.toastSuccess("Xóa thành công");
                                    this.setDisableBtn(false, true, true, true);
                                }
                                else {
                                    this.toastError('Xóa thất bại');
                                }
                            }
                        } catch (error) {
                            this.$root.toastError('Xóa thất bại');
                        } finally {
                            this.loading(false);
                        }
                        this.loadDsTuyenCongBe();
                    }
                }).catch(err => {
                    this.$root.toastError('Xóa thất bại');
                })
            }
        },
        kiemTraTonTai: async function() {
            try {
                var rs = await this.$root.context.get(`/web-cabman/danhmuc/tuyen-cong-be/check-exist-cong-be/${this.tuyenCB.tuyenCB_id}`);
                return rs.data.RESULT;
            } catch (error) {

            }
        },
      initData: async function(id){
        this.tuyenCB.tuyenCB_id = id;
        var rsTTVT_ToKT = await this.$root.context.post(`/web-cabman/danhmuc/tuyen-cong-be/get-ttvt-tokt-by-tcbid/${this.tuyenCB.tuyenCB_id}`, {
          loaituyen_id: this.loaituyen_id,
          loaidv_id: this.loaidv.donviql_ld
        });
        if(rsTTVT_ToKT.data) {
          this.isLoadingFromId = true;
          this.donvi_id = rsTTVT_ToKT.data.TTVT;
          this.tokt_id = rsTTVT_ToKT.data.TOKT;
        }
      }
    },
    mounted: async function() {
        try {
            this.loading(true);
            var rsDonVi = await this.$root.context.post('/web-cabman/danhmuc/tuyen-cong-be/list-don-vi', {
                donvi_id: 0,
                loaidv_id: this.loaidv.donviql_ld
            })
            this.dsDonVi = rsDonVi.data;
            this.donvi_id = this.dsDonVi.length > 0 ? this.dsDonVi[0].DONVI_ID : null;
            this.dsTTVT = JSON.parse(JSON.stringify(this.dsDonVi));
            this.ttvt_id = this.dsTTVT.length > 0 ? this.dsDonVi[0].DONVI_ID : null;
            if(this.$route.query.id) {
                this.tuyenCB.tuyenCB_id = this.$route.query.id;
                var rsTTVT_ToKT = await this.$root.context.post(`/web-cabman/danhmuc/tuyen-cong-be/get-ttvt-tokt-by-tcbid/${this.tuyenCB.tuyenCB_id}`, {
                    loaituyen_id: this.loaituyen_id,
                    loaidv_id: this.loaidv.donviql_ld
                });
                if(rsTTVT_ToKT.data) {
                    this.isLoadingFromId = true;
                    this.donvi_id = rsTTVT_ToKT.data.TTVT;
                    this.tokt_id = rsTTVT_ToKT.data.TOKT;
                }
            }
        } catch (error) {

        } finally {
            this.loading(false);
        }
    },
    watch: {
        donvi_id: async function(newVal) {
            if(newVal) {
                try {
                    var rsToQL = await this.$root.context.post('/web-cabman/danhmuc/tuyen-cong-be/list-to-ql', {
                        donvi_id: newVal,
                        loaidv_id: this.loaidv.tram_vt,
                    });
                    this.dsToKT = rsToQL.data;
                    if(!this.isLoadingFromId) {
                        this.tokt_id = this.dsToKT.length > 0 ? this.dsToKT[0].DONVI_ID : null;
                    }
                } catch (error) {

                }
            }
        },
        tokt_id: async function(newVal) {
            if(newVal) {
                try {
                    try {
                        this.loading(true);
                        await this.loadDsTuyenCongBe();
                        if(this.isLoadingFromId) {
                            let obj = this.dsTuyenCB.find(v => v.TUYENCB_ID == this.tuyenCB.tuyenCB_id);
                            this.isLoadingFromId = false;
                            setTimeout(() => {
                                this.$refs.gridTcb.setCurrentSelectedRow(obj ? obj.STT - 1 : 0);
                            }, 500);
                        }
                    } catch (error) {

                    } finally {
                        this.loading(false);
                    }

                } catch (error) {

                } finally {

                }
            }
            else {
                this.tuyenCB.tuyenCB_id = null;
            }
        },
        'tuyenCB.tuyenCB_id': function(newVal) {
            if(!newVal) {
                this.dsTuyenCB = [];
                this.dsCBThuocTuyen = [];
                this.dsTQLDaGan = [];
                this.loadDsToQLChuaGan()
            }
        },
        ttvt_id: function(newVal) {
            if(newVal) {
                this.loadDsToQLChuaGan()
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

