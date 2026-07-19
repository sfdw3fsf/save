<template src="./index.html">
</template>

<script>
import ModalDauNoiSplitter from '../../../DauNoiSplitter/Modal'
export default {
    components: {
        ModalDauNoiSplitter
    },
    props: {
        isActive: Boolean,
        tramTb_id: Number,
    },
    data() {
        return {
            currentKC: {},
            dsCEx: [],
            dsPhanLoaiKC: [],
            dsLoaiKC: [],
            dsTrangThai: [],
            dsKieuLap: [],
            dsNoiSX: [],
            dsViTriLD: [],
            dsLoaiVo: [],
            phanLoaiKC_id: 1,
            loaiKC_id: 25,
            ketCuoi_id: null,
            maKetCuoi: null,
            kyHieu: null,
            tenKetCuoi: null,
            ghiChu: null,
            tongSoDD: null,
            dungLuong: null,
            sl_port_dd: null,
            sl_port_roi: null,
            sl_port_tb: null,
            ttsd_id: 1,
            kieulap_id: 1,
            noisx_id: 1,
            vitri_id: 1,
            loaiVo_id: 1,
            ngayLD: new Date(),
            ngaySD: new Date(),
            kinhDo: null,
            viDo: null,
            tramTB: {
                tramTb_id: null,
                ten_dv: null
            },
            nhanVien: {
                nhanvien_id: null,
                ten_nv: null
            },
            waterMark: "Chọn ngày",
            dateVal: new Date(),
            dateFormat: "dd/MM/yyyy",
            isDisableBtnNhapMoi: false,
            isDisableBtnGhiLai: true,
            isDisableBtnHuy: true,
            isDisableBtnXoa: true,
            isShowCEx: true,
            isDisableBtnChonCapGoc: true,
            isDisableBtnDauNoi: true,
            positionDefault: {
                lat: 0,
                lng: 0
            },
        }
    },
    methods: {
        setDisableBtn: function (isNhapmoi, isGhilai, isHuy, isXoa, isChonCapGoc, isDauNoi) {
            this.isDisableBtnNhapMoi = isNhapmoi;
            this.isDisableBtnGhiLai = isGhilai;
            this.isDisableBtnHuy = isHuy;
            this.isDisableBtnXoa = isXoa;
            this.isDisableBtnChonCapGoc = isChonCapGoc;
            this.isDisableBtnDauNoi = isDauNoi;
        },
        layDsKetCuoi: async function() {
            try {
                let rsSp = await this.$root.context.get(`/web-cabman/ketcuoi/ds-theo-tramtb/${this.tramTb_id}`);
                this.dsCEx = rsSp.data.filter(function (item) {return item.LOAIKC_ID == 25;});
            } catch (error) {
                
            }
        },
        layDsLoaiKC: async function() {
            try {
                let rs = await this.$root.context.post('/web-cabman/danhmuc/layDsLoaiKC');
                this.dsLoaiKC = rs.data.filter(function (item) {return item.LOAIKC_ID == 25;});
            } catch (error) {
                
            }
        },
        layDsTrangThaiSuDung: async function() {
            try {
                let rs = await this.$root.context.post('/web-cabman/danhmuc/layDsTrangThaiSuDung');
                this.dsTrangThai = rs.data;
            } catch (error) {
                
            }
        },
        layDsKieuLap: async function() {
            try {
                let rs = await this.$root.context.post('/web-cabman/danhmuc/layDsKieuLap');
                this.dsKieuLap = rs.data;
            } catch (error) {
                
            }
        },
        layDsNoiSanXuat: async function() {
            try {
                let rs = await this.$root.context.post('/web-cabman/danhmuc/layDsNoiSX');
                this.dsNoiSX = rs.data;
            } catch (error) {
                
            }
        },
        layDsViTriLapDat: async function() {
            try {
                let rs = await this.$root.context.post('/web-cabman/danhmuc/layDsViTriLapDat');
                this.dsViTriLD = rs.data;
            } catch (error) {
                
            }
        },
        layDsLoaiVo: async function() {
            try {
                let rs = await this.$root.context.post('/web-cabman/danhmuc/layDsLoaiVo');
                this.dsLoaiVo = rs.data;
            } catch (error) {
                
            }
        },
        layTramThietBi: async function() {
            try {
                let rs = await this.$root.context.post('/web-cabman/danhmuc/layTramThietBi', {
                    id: this.tramTb_id
                });
                if(rs.data) {
                    this.tramTB.donvi_id = rs.data.DONVI_ID;
                    this.tramTB.ten_dv = rs.data.TEN_DV;
                    this.tramTB.diachi_dv = rs.data.DIACHI_DV;
                    this.tramTB.vido = rs.data.VIDO;
                    this.tramTB.kinhdo = rs.data.KINHDO;
                }
            } catch (error) {
                
            }
        },
        layNhanVien: async function() {
            try {
                if(this.nhanVien.nhanvien_id) {
                    let rs = await this.$root.context.get(`/web-cabman/danhmuc/lay-nhanvien/${this.nhanVien.nhanvien_id}`);
                    if(rs.data) {
                        this.nhanVien.ten_nv = rs.data.TEN_NV;
                    }
                }
            } catch (error) {
                
            }
        },
        layThongTinKetCuoi: async function() {
            try {
                if(this.ketCuoi_id) {
                    return await this.$root.context.get(`/web-cabman/ketcuoi/layThongtinKetCuoi?ketcuoi_id=${this.ketCuoi_id}`);
                }
                return null;
            } catch (error) {
                
            }
        },
        convertDate: function (date) {
            return (
                new Date(date).toLocaleDateString("en-US", { day: "2-digit" }) +
                "/" +
                new Date(date).toLocaleDateString("en-US", { month: "2-digit" }) +
                "/" +
                new Date(date).toLocaleDateString("en-US", { year: "numeric" })
            );
        },
        setData: function(data) {
            this.currentKC = Object.assign({}, data);
            this.phanLoaiKC_id = data.PHANLOAIKC_ID;
            this.loaiKC_id = data.LOAIKC_ID;
            this.capsp_id = data.CAP_SP;
            this.maKetCuoi = data.MA_KC;
            this.kyHieu = data.KYHIEU;
            this.tenKetCuoi = data.TEN_KC;
            this.diachi_id = data.DIACHI_ID;
            this.ghiChu = data.GHICHU;
            this.tongSoDD = data.TONGSO_DD;
            this.dungLuong = data.DUNGLUONG;
            this.sl_port_dd = data.SL_PORT_DADUNG;
            this.sl_port_roi = data.SL_PORT_ROI;
            this.sl_port_tb = data.SL_PORT_TB;
            this.ttsd_id = data.TTSD_ID;
            this.ngayLD = data.NGAY_LD;
            this.ngaySD = data.NGAY_SD;
            this.kieulap_id = data.KIEULAP_ID;
            this.noisx_id = data.NOISX_ID;
            this.vitri_id = data.VITRI_ID;
            this.loaiVo_id = data.LOAIVO_ID;
            this.cot_id = data.COT_ID;
            this.beCap_id = data.BECAP_ID;
            this.capgoc_id = data.CAPGOC_ID;
            this.kinhDo = data.KINHDO;
            this.viDo = data.VIDO;
            this.duan_id = data.DUAN_ID;
            this.taisan_id = data.TAISAN_ID;
            this.chuquan_id = data.CHUQUAN_ID;
            this.toql_id = data.DONVI_ID;
            this.tramtbi_id = data.TRAMTBI_ID;
            this.nhanvien_id = data.NHANVIEN_ID;
            this.toanha_id = data.TOANHA_ID;
            this.tinh_id = data.TINH_ID;
            this.quan_id = data.QUAN_ID;
            this.phuong_id = data.PHUONG_ID;
            this.pho_id = data.PHO_ID;
            this.ap_id = data.AP_ID;
            this.khu_id = data.KHU_ID;
            this.dacdiem_id = data.DACDIEM_ID;
            this.sonha = data.SONHA;
            this.positionDefault = {
                lat: data.VIDO,
                lng: data.KINHDO
            }
        },
        gridDsSpliiter_selectedRowChanged: async function(item) {
            if(item) {
                try {
                    this.loading(true);
                    this.ketCuoi_id = item.KETCUOI_ID;
                    let rs = await this.layThongTinKetCuoi();
                    if(rs.data) {
                        this.setData(rs.data)
                        await this.layNhanVien();
                    }

                    this.setDisableBtn(false, false, false, false, false, false);
                } catch (error) {
                    
                } finally {
                    this.loading(false);
                }
            }            
        },
        taoKetCuoiTrongTram: async function() {
            try {
                this.phanLoaiKC_id = 1;
                this.loaiKC_id = 25;
            } catch (error) {
                console.log(error);
            }
        },
        xacNhanToKT: function() {
            this.toql_id = this.tokt_id;
        },
        huyChonToKT: function() {
            this.$root.toastError('Bạn chưa chọn tổ kỹ thuật quản lý thiết bị CEx, chương trình sẽ lấy tổ kỹ thuật quản lý trạm thiết bị.');
        },
        nhapMoi: function() {
            this.ketCuoi_id = null;
            this.setData({
                CAP_ID: null,
                MA_KC: null,
                KYHIEU: null,
                TEN_KC: null,
                DIACHI_ID: null,
                GHICHU: null,
                TONGSO_DD: null,
                DUNGLUONG: null,
                SL_PORT_DADUNG: null,
                SL_PORT_ROI: null,
                SL_PORT_TB: null,
                TTSD_ID: null,
                NGAY_LD: new Date(),
                NGAY_SD: new Date(),
                KIEULAP_ID: 1,
                NOISX_ID: 1,
                VITRI_ID: 1,
                LOAIVO_ID: 1,
                NHANVIEN_ID: this.$root.token.getNhanVienID()
            });
            this.taoKetCuoiTrongTram();
            this.setDisableBtn(true, false, false, true, true, true);
        },
        checkForm: function() {
            let check = true;
            let error = '';
            
            if(!this.ttsd_id) {
                check = false;
                error = 'Trạng thái sử dụng không được để trống !';
            }

            if(this.tongSoDD == null || this.tongSoDD === '') {
                check = false;
                error = 'Dung lượng không được để trống !\n'.concat(error);
            }
            else if(this.tongSoDD <= 0) {
                check = false;
                error = 'Dung lượng phải lớn hơn 0 !\n'.concat(error);
            }else if(this.tongSoDD % 2 != 0) {
                check = false;
                error = 'Dung lượng phải là số chẵn !\n'.concat(error);
            }

            if(!this.kyHieu) {
                check = false;
                error = 'Ký hiệu không được để trống !\n'.concat(error);
            }

            if(!this.tenKetCuoi) {
                check = false;
                error = 'Tên CEx không được để trống !\n'.concat(error);
            }

            if(!check) {
                this.$root.toastError(error);
            }

            return check;
        },
        taoDuLieuketCuoi: function() {
            return {
                ketcuoi_id: this.ketCuoi_id,
                diachi_id: this.diachi_id,
                ten_kc: this.tenKetCuoi,
                kyhieu: this.kyHieu,
                tongso_dd: this.tongSoDD,
                ngay_ld: this.convertDate(this.ngayLD),
                ngay_sd: this.convertDate(this.ngaySD),
                ttsd_id: this.ttsd_id,
                kieulap_id: this.kieulap_id,
                noisx_id: this.noisx_id,
                vitri_id: this.vitri_id,
                loaivo_id: this.loaiVo_id,
                capgoc_id: null,
                loaikc_id: this.loaiKC_id,
                phanloaikc_id: this.phanLoaiKC_id,
                donvi_id: this.toql_id??0,
                taisan_id: this.taisan_id,
                duan_id: this.duan_id,
                toanha_id: this.toanha_id,
                kinhdo: this.kinhdo??0,
                vido: this.vido??0,
                ketcuoi_cha_id: null,
                cap_sp: null,
                ghichu: this.ghiChu,
                diachi: this.tramTB.diachi_dv??'DIACHI_THEO_TRAM',
                tinh_id: this.tinh_id,
                quan_id: 0,
                phuong_id: 0,
                pho_id: 0,
                ap_id: 0,
                khu_id: 0,
                dacdiem_id: 0,
                sonha: this.sonha,
                tramtbi_id: this.tramTb_id,
                cap_id: null,
                nguondich: null,
                chuquan_id: this.chuquan_id,
                cot_id: null,
                becap_id: null,
                ma_csht: this.ma_csht,
                ims_id: this.ims_id
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
                        let rs = await this.$root.context.post('/web-cabman/ketcuoi/themmoi', this.taoDuLieuketCuoi())
                        if(rs.data) {
                            this.$root.toastSuccess('Thêm thiết bị CEx thành công');
                            this.setDisableBtn(false, false, false, false, false, false)
                            await this.layDsKetCuoi();
                        }
                    } catch (error) {
                        console.log(error);
                        this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : "Thêm thiết bị CEx thất bại.");
                    } finally {
                        this.loading(false);
                    }
                }
                else {
                    this.$bvModal.msgBoxConfirm('Bạn có muốn cập nhật dữ liệu không ?', {
                        size: 'sm',
                        okTitle: 'Đồng ý',
                        cancelTitle: 'Hủy',
                    }).then(async v => {
                        if(v) {
                            try {
                                this.loading(true);
                                let rs = await this.$root.context.post('/web-cabman/ketcuoi/capnhat', this.taoDuLieuketCuoi())
                                if(rs.data) {
                                    this.setDisableBtn(false, false, false, false, false, false);
                                    await this.layDsKetCuoi();
                                    this.$root.toastSuccess('Cập nhật thiết bị CEx thành công');
                                }
                            } catch (error) {
                                this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : "Cập nhật thiết bị CEx thất bại.");
                            } finally {
                                this.loading(false);
                            }
                        }
                    }).catch(err => {
                        this.$root.toastError('Cập nhật thất bại');
                    })
                }
            }
        },
        huyBo: function() {
            if(this.isDisableBtnHuy) {
                return;
            }
            if(this.dsCEx.length == 0) {
                this.setData({});
                this.setDisableBtn(false, true, true, true, true , true);
                return
            }
            if(this.isDisableBtnNhapMoi) {
                this.$refs.gridSpliiter.setCurrentSelectedRow(0);
            }
            else {
                this.gridDsSpliiter_selectedRowChanged(this.currentKC);
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
                    if(this.ketCuoi_id) {
                        try {
                            this.loading(true);
                            var rs = await this.$root.context.post(`/web-cabman/ban-do-mang-cap/xoa-ketcuoi?ketcuoi_id=${this.ketCuoi_id}&&xoa_batbuoc=1`);
                            if(rs.data.result) {
                                this.$root.toastSuccess('Xóa thiết bị CEx thành công.');
                                this.setData({});
                                this.setDisableBtn(false, true, true, true, true , true);
                                this.layDsKetCuoi();
                            }
                        } catch (error) {
                            this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : 'Xóa thiết bị CEx thất bại.');
                        } finally {
                            this.loading(false);
                        }
                    }
                    else {
                        this.$root.toastError('Không tồn tại thiết bị CEx để xóa.');
                    }
                }
            })
            .catch(err => {
                this.$root.toastError('Xóa CEx thất bại.');
            })
        },
        dauNoi: function() {
            this.$bvModal.show('popupDauNoiSplitter');
        }
    },
    mounted: async function() {
        try {
            this.loading(true);
            this.layDsLoaiKC();
            this.layDsTrangThaiSuDung();
            this.layDsKieuLap();
            this.layDsNoiSanXuat();
            this.layDsViTriLapDat();
            this.layDsLoaiVo();
            this.layTramThietBi();

            await this.layDsKetCuoi();
            if(this.dsCEx.length == 0) {
                await this.taoKetCuoiTrongTram();
                this.setDisableBtn(true, false, false, true, true, true);
            }
        } catch (error) {
            
        } finally {
            this.loading(false);
        }
    },
    computed: {
        phanLoaiKC: function() {
            let rs = this.dsPhanLoaiKC.filter(v => v.PHANLOAIKC_ID == this.phanLoaiKC_id);
            return rs.length ? rs[0].PHANLOAI_KC : '';
        }
    },
    watch: {
        'nhanVien.nhanvien_id': async function(newVal) {
            if(newVal) {
                await this.layNhanVien();
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

.d-flex {
    display: flex;
    justify-content: space-between;
}

.d-flex .left {
    flex: 0.9;
}
</style>
