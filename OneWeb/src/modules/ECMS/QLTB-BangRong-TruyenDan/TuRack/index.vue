<template src="./index.html">
</template>

<script>
import EventBus from '../../../../utils/eventBus'
export default {
    data() {
        return {
            kieu: 2,
            turack_id: 0,
            donvi_id: null,
            toql_id: null,
            nhatram_id: null,
            loaitu_id: null,
            tentu: null,
            vitri: null,
            tuRack: {},
            dsLoaiTu: [],
            dsDonVi: [],
            dsToQL: [],
            dsTram: [],
            LOAI_DV: {
                DONVIQL_LD: 3,
                TRAM_VT: 5,
                TRAM_VETINH: 25,
                TRAM_PORT: 39
            },
            isLoading: false,
            isThemMoi: false,
            currentItem: {}
        }
    },
    methods: {
        getDonVi: async function (donViID) {
            try {
                let rs = await this.$root.context.post(
                    "/web-ecms/danhmuc/layThongTinDonVi",
                    {
                    id: donViID,
                    }
                );
                return rs.data;
            } catch (error) {
                
            }
        },
        loadDsLoaiTu: async function() {
            try {
                var rs = await this.$root.context.get('/web-ecms/danhmuc/loaitu/lietke_danhsach');
                this.dsLoaiTu = rs.data;
                if(!this.isLoading) {
                    this.loaitu_id = this.dsLoaiTu.length > 0 ? this.dsLoaiTu[0].LOAITU_ID : null;
                }
            } catch (error) {
                this.$root.toastError(error.response.data.message_detail);
            }
        },
        getDsTTVT: async function () {
            let rs = await this.$root.context.post(
                "/web-ecms/danhmuc/layDsTTVTTrongToken"
            );
            return rs.data;
        },
        getDsToKT: async function () {
            let rs = await this.$root.context.post("/web-ecms/danhmuc/layDsToKT", {
                id: this.donvi_id,
            });
            return rs.data;
        },
        getDsTram: async function () {
            let rs = await this.$root.context.post("/web-ecms/danhmuc/layDsVeTinh", {
                id: this.toql_id,
            });
            return rs.data;
        },
        loadDsDonVi: async function() {
            try {
                // var rs = await this.$root.context.post('/web-ecms/danhmuc/layDsDonVi', {
                //     donvi_id: 0,
                //     loaidv_id: this.LOAI_DV.DONVIQL_LD
                // });
                this.dsDonVi = await this.getDsTTVT();
                if(!this.isLoading) {
                    this.donvi_id = this.dsDonVi.length > 0 ? this.dsDonVi[0].DONVI_ID : null;
                }
            } catch (error) {
                this.$root.toastError(error.response.data.message_detail);
            }
        },
        loadDsToQuanLy: async function() {
            try {
                // var rs = await this.$root.context.post('/web-ecms/danhmuc/layDsToKT', {
                //     id: this.donvi_id,
                // });
                this.dsToQL = await this.getDsToKT();
                if(!this.isLoading) {
                    this.toql_id = this.dsToQL.length > 0 ? this.dsToQL[0].DONVI_ID : null;
                }
            } catch (error) {
                this.$root.toastError(error.response.data.message_detail);
            }
        },
        loadDsTram: async function() {
            try {
                // var rs = await this.$root.context.post('/web-ecms/danhmuc/layDsTramVT', {
                //     donvi_id: this.toql_id,
                //     loaidv_id: this.kieu == 1 ? this.LOAI_DV.TRAM_VETINH : this.LOAI_DV.TRAM_PORT
                // });
                this.dsTram = await this.getDsTram();
                if(!this.isLoading) {
                    this.nhatram_id = this.dsTram.length > 0 ? this.dsTram[0].DONVI_ID : null;
                }
            } catch (error) {
                this.$root.toastError(error.response.data.message_detail);
            }
        },
        clearForm: function() {
            this.tentu = '';
            this.vitri = '';
        },
        checkForm: function() {
            var check = true;
            var error = '';
            if(this.vitri == null || this.vitri === '') {
                error = 'Vị trí không được để trống!';
                check = false;
            }
            if(!this.nhatram_id) {
                error = 'Trạm không được để trống!\n'.concat(error);
                check = false;
            }
            if(!this.tentu) {
                error = 'Tên tủ không được để trống!\n'.concat(error);
                check = false;
            }
            if(!this.loaitu_id) {
                error = 'Chưa chọn loại tủ\n'.concat(error);
                check = false;
            }
            if(!check) {
                this.$root.toastError(error);
            }
            return check;
        },
        taoDuLieuTuRack: function() {
            return {
                turack_id: this.turack_id,
                loaitu_id: this.loaitu_id,
                vitri: this.vitri,
                kieutu: this.kieu,
                ten_tu: this.tentu,
                donvi_id: this.nhatram_id
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
                        var rs = await this.$root.context.post('/web-ecms/danhmuc/turack/them', this.taoDuLieuTuRack());
                        if(rs.data) {
                            this.$root.toastSuccess('Thêm mới thành công!');
                            this.isThemMoi = false;
                            this.currentItem = {
                                turack_id: rs.data.TURACK_ID,
                                tentu: rs.data.TEN_TU,
                                nhatram_id: rs.data.DONVI_ID
                            }
                            this.turack_id = this.currentItem.turack_id;
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
                        var rs = await this.$root.context.post('/web-ecms/danhmuc/turack/capnhat', this.taoDuLieuTuRack());
                        if(rs.data) {
                            this.$root.toastSuccess('Cập nhật thành công!');
                            this.currentItem = {
                                turack_id: this.turack_id,
                                tentu: this.tentu,
                                nhatram_id: this.nhatram_id
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
            // });
            try {
                this.loading(true);
                var rs = await this.$root.context.post('/web-ecms/danhmuc/turack/xoa', {
                    id: this.turack_id
                });
                if(rs.data.result) {
                    this.$root.toastSuccess("Xóa dữ liệu thành công!");
                    return true;
                }
            } catch (error) {
                this.$root.toastError(error.response.data.message_detail);
                return false;
            } finally {
                this.loading(false);
            }
        },
        loadTuRack: async function() {
            try {
                this.loading(true);
                await this.loadDsLoaiTu();
                await this.loadDsDonVi();
                if(this.turack_id != 0) {
                    var rs = await this.$root.context.get(`/web-ecms/danhmuc/turack/chitiet?turack_id=${this.turack_id}`);
                    this.tuRack = rs.data;
                    this.nhatram_id = this.tuRack.DONVI_ID;
                    this.loaitu_id = this.tuRack.LOAITU_ID;
                    this.tentu = this.tuRack.TEN_TU;
                    this.vitri = this.tuRack.VITRI;
                    this.kieu = this.tuRack.KIEUTU;
                }
                else {
                    this.tentu = null;
                    this.vitri = null;
                }
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        bindData: async function(data){
            try {
                this.loading(true);
                this.isLoading = true;
                this.turack_id = data.itemID;
                this.nhatram_id = data.nhatram_id;
                this.isThemMoi = this.turack_id == 0 ? true: false;
                let toql = await this.getDonVi(data.nhatram_id);
                if(toql) {
                    this.toql_id = toql.DONVI_CHA_ID;
                    let tram = await this.getDonVi(toql.DONVI_CHA_ID);
                    this.donvi_id = tram ? tram.DONVI_CHA_ID : null;
                }
                await this.loadTuRack();
                this.isLoading = false;
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
    },
    watch: {
        donvi_id: async function(val) {
            if(val) {
                try {
                    this.loading(true);
                    await this.loadDsToQuanLy();
                } catch (error) {
                    
                } finally {
                    this.loading(false);
                }
            }
            else {
                this.dsToQL = [];
            }
        },
        toql_id: async function(val) {
            if(val) {
                try {
                    this.loading(true);
                    await this.loadDsTram();
                } catch (error) {
                    
                } finally {
                    this.loading(false);
                }
            }
            else {
                this.dsTram = [];
            }
        }
    },
    created: function() {
    },
    mounted: async function() {
        try {
            let vm = this;
            EventBus.$on('thongTinTuRack', async data => {
                this.isLoading = true;
                this.turack_id = data.turack_id;
                this.nhatram_id = data.nhatram_id;
                await this.loadTuRack();
                this.isLoading = false;
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
