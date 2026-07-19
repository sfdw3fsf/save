<template src="./index.html">
</template>

<script>
import ModalDauNoiPhienDoc from '../../../DauNoiPhienDoc/DauNoiPhienDoc'

export default {
    components: {
        ModalDauNoiPhienDoc
    },
    props: {
        isActive: Boolean,
        donvi: String,
        tramTb_id: Number,
    },
    data() {
        return {
            
            phiendoc_id: null,
            kyHieu: null,
            vitriBD: null,
            slJumper: null,
            ghiChu: null,
            ngayCN: null,
            nguoiCN: null,
            mayCN: null,
            ipCN: null,
            dsMDF: [],
            currentMDF: {},
            isShowDinhDanh: true,
            isShowDacTinh: true,
            isShowKhac: true,
            isDisableBtnNhapMoi: false,
            isDisableBtnGhiLai: true,
            isDisableBtnHuy: true,
            isDisableBtnXoa: true,
            isDisableBtnDauNoi: true
        }
    },
    methods: {
        setDisableBtn: function (isNhapmoi, isGhilai, isHuy, isXoa, isDauNoi) {
            this.isDisableBtnNhapMoi = isNhapmoi;
            this.isDisableBtnGhiLai = isGhilai;
            this.isDisableBtnHuy = isHuy;
            this.isDisableBtnXoa = isXoa;
            this.isDisableBtnDauNoi = isDauNoi
        },
        layDsMDF: async function() {
            try {
                let rs = await this.$root.context.get(`/web-cabman/danhmuc/phiendoc/lay-ds-by-tramid/${this.tramTb_id}`);
                this.dsMDF = rs.data;
            } catch (error) {
                
            }
        },
        gridDsPhienDoc_selectedRowChanged: function(data) {
            if(data) {
                this.currentMDF = Object.assign({}, data);
                this.phiendoc_id = data.PHIENDOC_ID;
                this.kyHieu = data.KYHIEU;
                this.vitriBD = data.VITRI_BD;
                this.slJumper = data.SL_JUMPER;
                this.ghiChu = data.GHICHU;
                this.ngayCN = data.NGAY_CN;
                this.nguoiCN = data.NGUOI_CN;
                this.mayCN = data.MAY_CN;
                this.ipCN = data.IP_CN;
                this.setDisableBtn(false, false, false, false, false);
            }
        },
        setData: function(phiendoc_id, kyHieu, vitriBD, slJumper, ghiChu) {
            this.phiendoc_id = phiendoc_id;
            this.kyHieu = kyHieu;
            this.vitriBD = vitriBD;
            this.slJumper = slJumper;
            this.ghiChu = ghiChu;
        },
        nhapMoi: function() {
            if(this.isDisableBtnNhapMoi) {
                return;
            }
            this.setData(-1, null, null, 0, null);
            this.setDisableBtn(true, false, false, true, true);
        },
       
        checkForm: function() {
            let check = true;
            let error = '';

            if(this.slJumper == null || this.slJumper === '') {
                check = false;
                error = 'Số cổng không được để trống';
            }
            else if(parseInt(this.slJumper) < 1 || parseInt(this.slJumper) > 65535) {
                check = false;
                error = 'Số cổng phải nằm trong khoảng từ 1 đến 65535';
            }

            if(this.vitriBD == null || this.vitriBD === '') {
                check = false;
                error = 'Vị trí bắt đầu không được để trống\n'.concat(error);
            }
            else if(parseInt(this.vitriBD) < 1 || parseInt(this.vitriBD) > 65535) {
                check = false;
                error = 'Vị trí bắt đầu phải nằm trong khoảng từ 1 đến 65535\n'.concat(error);;
            }

            if(!this.kyHieu) {
                check = false;
                error = 'Kí hiệu không đc để trống\n'.concat(error);;
            }

            if(!check) {
                this.$root.toastError(error);
            }
            return check;
        },
        taoDuLieuPhienDoc: function() {
            return {
                donvi_id: this.tramTb_id,
                kyhieu: this.kyHieu,
                vitri_bd: this.vitriBD,
                sl_jumper: this.slJumper,
                ghichu: this.ghiChu
            }
        },
        ghiLai: async function() {
            if(this.isDisableBtnGhiLai) {
                return;
            }
            if(this.checkForm()) {
                if(this.isDisableBtnNhapMoi) {
                    try {
                        this.loading(true);
                        let rs = await this.$root.context.post('/web-cabman/danhmuc/phiendoc/them', this.taoDuLieuPhienDoc());
                        if(rs.data) {
                            this.$root.toastSuccess("Thêm phiến dọc thành công.");
                            this.layDsMDF();
                            this.setDisableBtn(false, true, true, true, true);
                        }
                    } catch (error) {
                        this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : "Thêm phiến dọc thất bại.");
                    } finally {
                        this.loading(false);
                    }
                }
                else {
                    try {
                        this.loading(true);
                        let rs = await this.$root.context.post(`/web-cabman/danhmuc/phiendoc/capnhat/${this.phiendoc_id}`, this.taoDuLieuPhienDoc());
                        if(rs.data) {
                            this.$root.toastSuccess("Cập nhật phiến dọc thành công.");
                            this.layDsMDF();
                            this.setDisableBtn(false, true, true, true, true);
                        }
                    } catch (error) {
                        this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : "Cập nhật phiến dọc thất bại.");
                    } finally {
                        this.loading(false);
                    }
                }
            }
        },
        huyBo: function() {
            if(this.isDisableBtnHuy) {
                return;
            }
            if(this.dsMDF.length == 0) {
                this.setData(null, null, null, null, null);
                this.setDisableBtn(false, true, true, true, true);
                return;
            }
            if(this.isDisableBtnNhapMoi) {
                // this.setData(-1, null, null, 0, null);
                this.$refs.gridPhienDoc.setCurrentSelectedRow(0)
            }
            else {
                this.gridDsPhienDoc_selectedRowChanged(this.currentMDF);
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
                cancelTitle: 'Hủy',
            })
            .then(async val => {
                if(val) {
                    if(this.phiendoc_id) {
                        try {
                            this.loading(true);
                            var rs = await this.$root.context.post(`/web-cabman/danhmuc/phiendoc/xoa/${this.phiendoc_id}`);
                            if(rs.data.result) {
                                this.$root.toastSuccess('Xóa phiến dọc thành công.');
                                this.setData(null, null, null, null, null);
                                this.setDisableBtn(false, true, true, true, true);
                                this.layDsMDF();
                            }
                        } catch (error) {
                            this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : 'Xóa phiến dọc thất bại.');
                        } finally {
                            this.loading(false);
                        }
                    }
                }
            })
            .catch(err => {
                this.$root.toastError('Xóa ODF thất bại.');
            })
        }
    },
    mounted: async function() {
        try {
            this.loading(true);
            await this.layDsMDF();
        } catch (error) {
            
        } finally {
            this.loading(false);
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
.disabled-inp {
    color: #adb5bd;
}
</style>
