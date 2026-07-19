<template src="./index.html">
</template>

<script>
import ModalChonToaDo from '../BanDo'
import ModalChonDiaChi from '../ModalChonDiaChi'
import ModalDSCotCap from '../../../QuanLyMangTruyenDan-Hieu/DanhSachCot/Modal'
import ModalDSBeCap from '../../../QuanLyMangTruyenDan-Hieu/DanhSachBe/Modal'
import ModalThongTinToaNha from '../../../ThongTinToaNha/ThongTinToaNha'
import ModaCSHT from '../../../BanDoTuyenCong/Csht/Modal'
import ModalDauNoiSplitter from '../../../DauNoiSplitter/Modal'
import ModalChonCapGoc from '../ChonCapGoc'
export default {
    components: {
        ModalChonToaDo,
        ModalChonDiaChi,
        ModalDSCotCap,
        ModalDSBeCap,
        ModalThongTinToaNha,
        ModaCSHT,
        ModalDauNoiSplitter,
        ModalChonCapGoc
    },
    props: {
        isActive: Boolean,
        tramTb_id: Number,
    },
    data() {
        return {
            currentKC: {},
            dsSplitter: [],
            dsToKT: [],
            dsPhanLoaiKC: [],
            dsLoaiKC: [],
            dsCapSplitter: [],
            dsTrangThai: [],
            dsKieuLap: [],
            dsNoiSX: [],
            dsViTriLD: [],
            dsLoaiVo: [],
            dsMaDuAn: [],
            dsMaTaiSan: [],
            dsChuQuan: [],
            dsToQL: [],
            dsToaNha: [],
            tokt_id: null,
            phanLoaiKC_id: null,
            loaiKC_id: null,
            capsp_id: null,
            ketCuoi_id: null,
            maKetCuoi: null,
            kyHieu: null,
            tenKetCuoi: null,
            diachi: {
                diachi_id: null,
                diachi_dv: null
            },
            ghiChu: null,
            tongSoDD: null,
            dungLuong: null,
            sl_port_dd: null,
            sl_port_roi: null,
            sl_port_tb: null,
            ttsd_id: null,
            kieulap_id: null,
            noisx_id: null,
            vitri_id: null,
            loaiVo_id: null,
            ngayLD: new Date(),
            ngaySD: new Date(),
            cot: {
                cot_id: null,
                so_hieu: null,
            },
            beCap: {
                beCap_id: null,
                kyHieu: null
            },
            capGoc: {
                cap_id: null,
                kyHieu: null,
                capgoc_id: null
            },
            ketCuoiCha: {
                ketCuoi_id: null,
                kyHieu: null
            },
            kinhDo: null,
            viDo: null,
            duan_id: null,
            taisan_id: null,
            chuquan_id: null,
            toql_id: null,
            tramTB: {
                tramTb_id: null,
                ten_dv: null
            },
            nhanVien: {
                nhanvien_id: null,
                ten_nv: null
            },
            toanha_id: null,
            tinh_id: null,
            quan_id: null,
            phuong_id: null,
            pho_id: null,
            ap_id: null,
            khu_id: null,
            dacdiem_id: null,
            sonha: null,
            ma_csht: null,
            waterMark: "Chọn ngày",
            dateVal: new Date(),
            dateFormat: "dd/MM/yyyy",
            isDisableBtnNhapMoi: false,
            isDisableBtnGhiLai: true,
            isDisableBtnHuy: true,
            isDisableBtnXoa: true,
            isShowModalChonToKT: false,
            isShowModalToaDo: false,
            isShowDiaChi: false,
            isShowSpliter: true,
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
                this.dsSplitter = rsSp.data.filter(function (item) {return item.LOAIKC_ID != 25;});
            } catch (error) {
                
            }
        },
        layDsPhanLoaiKC: async function() {
            try {
                let rs = await this.$root.context.post('/web-cabman/danhmuc/layDsPhanLoaiKC');
                this.dsPhanLoaiKC = rs.data;
            } catch (error) {
                
            }
        },
        layDsLoaiKC: async function() {
            try {
                let rs = await this.$root.context.post('/web-cabman/danhmuc/layDsLoaiKC');
                this.dsLoaiKC = rs.data;
            } catch (error) {
                
            }
        },
        layDsCapSP: async function() {
            try {
                if(this.loaiKC_id) {
                    let rs = await this.$root.context.get(`/web-cabman/danhmuc/layDsCapSPTheoKetCuoi/${this.loaiKC_id}`);
                    this.dsCapSplitter = rs.data;
                }
            } catch (error) {
                
            }
        },
        layDiaChi: async function() {
            try {
                if(this.diachi.diachi_id) {
                    let rs = await this.$root.context.get(`/web-cabman/danhmuc/layDiaChi/${this.diachi.diachi_id}`);
                    if(rs.data) {
                        this.diachi.diachi_dv = rs.data.DIACHI;
                    }
                }
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
        layCotCap: async function() {
            try {
                if(this.cot.cot_id) {
                    let rs = await this.$root.context.post('/web-cabman/danhmuc/layCotSoHieu', {
                        id: this.cot.cot_id
                    });
                    if(rs.data) {
                        this.cot.so_hieu = rs.data.SO_HIEU;
                    }
                }
            } catch (error) {
                
            }
        },
        layBeCap: async function() {
            try {
                if(this.beCap.beCap_id) {
                    let rs = await this.$root.context.post('/web-cabman/danhmuc/layBeCapKyHieu', {
                        id: this.beCap.beCap_id
                    });
                    if(rs.data) {
                        this.beCap.kyHieu = rs.data.KYHIEU;
                    }
                }
            } catch (error) {
                
            }
        },
        layCapGoc: async function() {
            try {
                if(this.capGoc.capgoc_id) {
                    let rs = await this.$root.context.get(`/web-cabman/danhmuc/lay-capgoc/${this.capGoc.capgoc_id}`);
                    if(rs.data)
                        this.capGoc.kyHieu = rs.data.KYHIEU;
                        this.capGoc.cap_id = rs.data.CAP_ID;
                }
            } catch (error) {
                
            }
        },
        layKetCuoiCha: async function() {
            try {
                if(this.ketCuoiCha.ketCuoi_id) {
                    let rs = await this.$root.context.get(`/web-cabman/danhmuc/lay-ketcuoi-cha/${this.ketCuoiCha.ketCuoi_id}`);
                    this.ketCuoiCha.kyHieu = rs.data;
                }
            } catch (error) {
                
            }
        },
        layDsMaDuAn: async function() {
            try {
                let rs = await this.$root.context.post('/web-cabman/danhmuc/layDsDuAn');
                this.dsMaDuAn = rs.data;
            } catch (error) {
                
            }
        },
        layDsMaTaiSan: async function() {
            try {
                if(this.duan_id) {
                    let rs = await this.$root.context.get(`/web-cabman/taisan-duan/lay-ds-taisan-by-duanid/${this.duan_id}`);
                    this.dsMaTaiSan = rs.data;
                }
            } catch (error) {
                
            }
        },
        layDsChuQuan: async function() {
            try {
                let rs = await this.$root.context.post('/web-cabman/danhmuc/layDsChuQuan');
                this.dsChuQuan = rs.data;
            } catch (error) {
                
            }
        },
        layDsToQL: async function() {
            try {
                let rs = await this.$root.context.post('/web-cabman/danhmuc/layDsToQuanLy');
                this.dsToQL = rs.data;
            } catch (error) {
                
            }
        },
        layTramThietBi: async function() {
            try {
                let rs = await this.$root.context.post('/web-cabman/danhmuc/layTramThietBi', {
                    id: this.tramTb_id
                });
                if(rs.data) {
                    this.tramTB.ten_dv = rs.data.TEN_DV;
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
        layDsToaNha: async function() {
            try {
                let rs = await this.$root.context.post('/web-cabman/danhmuc/layDsToaNha');
                this.dsToaNha = rs.data;
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
            this.diachi.diachi_id = data.DIACHI_ID;
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
            this.cot.cot_id = data.COT_ID;
            this.beCap.beCap_id = data.BECAP_ID;
            this.capGoc.capgoc_id = data.CAPGOC_ID;
            this.ketCuoiCha.ketCuoi_id = data.KETCUOI_CHA_ID;
            this.kinhDo = data.KINHDO;
            this.viDo = data.VIDO;
            this.duan_id = data.DUAN_ID;
            this.taisan_id = data.TAISAN_ID;
            this.chuquan_id = data.CHUQUAN_ID;
            this.toql_id = data.DONVI_ID;
            this.tramTB.tramTb_id = data.TRAMTBI_ID;
            this.nhanVien.nhanvien_id = data.NHANVIEN_ID;
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
                        await this.layDiaChi();
                        await this.layCotCap();
                        await this.layBeCap();
                        await this.layCapGoc();
                        await this.layKetCuoiCha();
                        await this.layNhanVien();
                        await this.layDsCapSP();
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
                let rsTo = await this.$root.context.get(`/web-cabman/mang-truyen-dan/ds-tokt-ql-theo-tramtb/${this.tramTb_id}`);
                this.dsToKT = rsTo.data;
                if(this.dsToKT.length > 0) {
                    this.toql_id = this.dsToKT[0].TOKT_ID;
                    this.kinhDo = this.dsToKT[0].KINHDO;
                    this.viDo = this.dsToKT[0].VIDO;
                    this.diachi.diachi_dv = this.dsToKT[0].DIACHI_DV;
                    this.tinh_id = this.dsToKT[0].TINH_ID;
                    this.phanLoaiKC_id = 1;
                    this.loaiKC_id = 5;
                    this.quan_id = 0;
                    this.phuong_id = 0;
                    this.pho_id = 0;
                    this.positionDefault = {
                        lat: this.viDo,
                        lng: this.kinhDo
                    }
                    // this.dsToKT.push(
                    //         {
                    //             "TOKT": "Tổ Kỹ thuật Thủy Nguyên 2",
                    //             "TOKT_ID": 185
                    //         },
                    //         {
                    //             "TOKT": "Thủy Nguyên 1",
                    //             "TOKT_ID": 942
                    //         },
                    //     )
                    if(this.dsToKT.length > 1) {
                        this.tokt_id = this.dsToKT[0].TOKT_ID;
                        this.isShowModalChonToKT = !this.isShowModalChonToKT;
                    }
                }
                else {
                    this.$root.toastError('Trạm thiết bị chưa được phân tổ quản lý. Không được phép tạo splitter.');
                    this.setDisableBtn(true, true, true, true, true, true);
                }
            } catch (error) {
                console.log(error);
            }
        },
        xacNhanToKT: function() {
            this.toql_id = this.tokt_id;
        },
        huyChonToKT: function() {
            this.$root.toastError('Bạn chưa chọn tổ kỹ thuật quản lý kết cuối, chương trình sẽ lấy tổ kỹ thuật quản lý trạm thiết bị.');
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
                COT_ID: null,
                BECAP_ID: null,
                CAPGOC_ID: 0,
                KETCUOI_CHA_ID: null,
                KINHDO: null,
                VIDO: null,
                DUAN_ID: null,
                TAISAN_ID: null,
                CHUQUAN_ID: null,
                DONVI_ID: null,
                NHANVIEN_ID: this.$root.token.getNhanVienID(),
                TOANHA_ID: null,
                TINH_ID: null,
                QUAN_ID: null,
                PHUONG_ID: null,
                PHO_ID: null,
                AP_ID: null,
                KHU_ID: null,
                DACDIEM_ID: null,
                SONHA: null
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
            }

            if(!this.diachi.diachi_dv) {
                check = false;
                error = 'Địa chỉ không được để trống !\n'.concat(error);
            }

            if(!this.tenKetCuoi) {
                check = false;
                error = 'Tên kết cuối không được để trống !\n'.concat(error);
            }

            if(!this.kyHieu) {
                check = false;
                error = 'Ký hiệu không được để trống !\n'.concat(error);
            }

            if(!check) {
                this.$root.toastError(error);
            }

            return check;
        },
        taoDuLieuketCuoi: function() {
            return {
                ketcuoi_id: this.ketCuoi_id,
                diachi_id: this.diachi.diachi_id,
                ten_kc: this.tenKetCuoi,
                kyhieu: this.kyHieu,
                tongso_dd: this.tongSoDD,
                dungluong: this.dungLuong,
                ngay_ld: this.convertDate(this.ngayLD),
                ngay_sd: this.convertDate(this.ngaySD),
                ttsd_id: this.ttsd_id,
                kieulap_id: this.kieulap_id,
                noisx_id: this.noisx_id,
                vitri_id: this.vitri_id,
                loaivo_id: this.loaiVo_id,
                capgoc_id: this.capGoc.capgoc_id,
                loaikc_id: this.loaiKC_id,
                phanloaikc_id: this.phanLoaiKC_id,
                donvi_id: this.toql_id,
                taisan_id: this.taisan_id,
                duan_id: this.duan_id,
                toanha_id: this.toanha_id,
                kinhdo: this.kinhDo,
                vido: this.viDo,
                ketcuoi_cha_id: this.ketCuoiCha.ketCuoi_id,
                cap_sp: this.capsp_id,
                ghichu: this.ghiChu,
                diachi: this.diachi.diachi_dv,
                tinh_id: this.tinh_id,
                quan_id: this.quan_id,
                phuong_id: this.phuong_id,
                pho_id: this.pho_id,
                ap_id: this.ap_id,
                khu_id: this.khu_id,
                dacdiem_id: this.dacdiem_id,
                sonha: this.sonha,
                tramtbi_id: this.tramTb_id,
                cap_id: this.capGoc.cap_id,
                nguondich: null,
                chuquan_id: this.chuquan_id,
                cot_id: this.cot.cot_id,
                becap_id: this.beCap.becap_id,
                ma_csht: this.ma_csht,
                ims_id: null
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
                            this.$root.toastSuccess('Thêm kết cuối thành công');
                            this.setDisableBtn(false, false, false, false, false, false)
                            await this.layDsKetCuoi();
                        }
                    } catch (error) {
                        this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : "Thêm kết cuối thất bại.");
                    } finally {
                        this.loading(false);
                    }
                }
                else {
                    // try {
                    //     this.loading(true);
                    //     let rs = await this.$root.context.post('/web-cabman/ketcuoi/capnhat', this.taoDuLieuketCuoi())
                    //     if(rs.data) {
                    //         await this.layDsKetCuoi();
                    //         this.$root.toastSuccess('Cập nhật kết cuối thành công');
                    //         this.setDisableBtn(false, false, false, false)
                    //     }
                    // } catch (error) {
                    //     this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : "Cập nhật kết cuối thất bại.");
                    // } finally {
                    //     this.loading(false);
                    // }
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
                                    this.$root.toastSuccess('Cập nhật kết cuối thành công');
                                }
                            } catch (error) {
                                this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : "Cập nhật kết cuối thất bại.");
                            } finally {
                                this.loading(false);
                            }
                        }
                    }).catch(err => {
                        this.$root.toastError('Xóa thất bại');
                    })
                }
            }
        },
        huyBo: function() {
            if(this.isDisableBtnHuy) {
                return;
            }
            if(this.dsSplitter.length == 0) {
                // this.isShowSpliter = false;
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
                                this.$root.toastSuccess('Xóa kết cuối thành công.');
                                this.setData({});
                                this.setDisableBtn(false, true, true, true, true , true);
                                this.layDsKetCuoi();
                            }
                        } catch (error) {
                            this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : 'Xóa kết cuối thất bại.');
                        } finally {
                            this.loading(false);
                        }
                    }
                    else {
                        this.$root.toastError('Không tồn tại kết cuối để xóa.');
                    }
                }
            })
            .catch(err => {
                this.$root.toastError('Xóa ODF thất bại.');
            })
        },
        XacNhanToaDo: async function(position) {
            if(position) {
                this.kinhDo = position.lng;
                this.viDo = position.lat;
                this.isShowModalToaDo = !this.isShowModalToaDo;
            }
        },
        closeModalDiaChi: async function(data) {
            this.isShowDiaChi = !this.isShowDiaChi;
            if(data.isUpdated) {
                this.diachi.diachi_dv = data.diaChi;
                this.tinh_id = data.chiTietDiaChi.tinhThanh_id;
                this.quan_id = data.chiTietDiaChi.quanHuyen_id;
                this.phuong_id = data.chiTietDiaChi.phuongXa_id;
                this.pho_id = data.chiTietDiaChi.duongPho_id;
                this.ap_id = data.chiTietDiaChi.ap_id;
                this.khu_id = data.chiTietDiaChi.khu_id;
                this.dacdiem_id = data.chiTietDiaChi.dacDiem_id;
                this.sonha = data.chiTietDiaChi.soNha;
                try {
                    this.loading(true);
                    let rs = await this.$root.context.post('/web-cabman/ketcuoi/capnhat', this.taoDuLieuketCuoi());
                    if(rs.data) {
                        this.setDisableBtn(false, false, false, false, false, false);
                        await this.layDsKetCuoi();
                        this.$root.toastSuccess('Cập nhật kết cuối thành công');
                    }
                } catch (error) {
                    
                } finally {
                    this.loading(false);
                }
            }
        },
        xacNhanCot: function(data) {
            this.cot = {
                cot_id: data.COT_ID,
                so_hieu: data.SO_HIEU,
            }
        },
        xacNhanBe: function(data) {
            this.beCap = {
                beCap_id: data.BECAP_ID,
                kyHieu: data.KYHIEU
            }
        },
        showThongTinToaNha: function() {
            if(!this.toanha_id) {
                this.$root.toastError('Chưa chọn tòa nhà!');
            }
            else {
                this.$bvModal.show('popupThongTinToaNha');
            }
        },
        xacNhanCSHT: function(data) {
            this.ma_csht = data;
        },
        dauNoi: function() {
            this.$bvModal.show('popupDauNoiSplitter');
        },
        btnChonCapGoc_Click: function() {
            this.$bvModal.show('popupChonCapGoc');
        },
        xacNhanCapGoc: async function(data) {
            try {
                this.loading(true);
                let rs = await this.$root.context.post('/web-cabman/ketcuoi/chuyen-ket-cuoi-ra-ngoai', {
                    ketcuoi_id: this.ketCuoi_id,
                    capgoc_id: data
                });
                if(rs.data.result) {
                    await this.layDsKetCuoi();
                    this.$root.toastSuccess('Chuyển kết cuối ra ngoài mạng thành công.');
                }
            } catch (error) {
                this.$root.toastSuccess('Có lỗi khi chuyển ra ngoài mạng.');
            } finally {
                this.loading(false);
            }
        }
    },
    mounted: async function() {
        try {
            this.loading(true);
            this.layDsPhanLoaiKC();
            this.layDsLoaiKC();
            this.layDsTrangThaiSuDung();
            this.layDsKieuLap();
            this.layDsNoiSanXuat();
            this.layDsViTriLapDat();
            this.layDsLoaiVo();
            this.layDsMaDuAn();
            this.layDsChuQuan();
            this.layDsToQL();
            this.layDsToaNha();
            this.layTramThietBi();

            await this.layDsKetCuoi();
            if(this.dsSplitter.length == 0) {
                await this.taoKetCuoiTrongTram();
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
        },
        getChiTietDiaChi: function() {
            return {
                tinhThanh_id: this.tinh_id,
                quanHuyen_id: this.quan_id,
                phuongXa_id: this.phuong_id,
                duongPho_id: this.pho_id,
                ap_id: this.ap_id,
                khu_id: this.khu_id,
                dacDiem_id: this.dacdiem_id,
                soNha: this.sonha
            }
        }
    },
    watch: {
        duan_id: async function(newVal) {
            if(newVal) {
                await this.layDsMaTaiSan();
                // this.taisan_id = null;
            }
        },
        'nhanVien.nhanvien_id': async function(newVal) {
            if(newVal) {
                await this.layNhanVien();
            }
        },
        loaiKC_id: function(newVal) {
            if(newVal) {
                this.layDsCapSP();
                // this.capsp_id = null;
            }
        }
    }
}
</script>

<style scoped>
.dsToKT {
    max-height: 500px;
    overflow-y: scroll;
}
.dsToKT .ToKT {
    list-style: none;
    padding: 10px 0px;
    display: flex;
    align-items: center;
}
.disabled-inp {
    color: #adb5bd;
}

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
