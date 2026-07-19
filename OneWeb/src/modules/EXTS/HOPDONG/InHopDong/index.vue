<template src="./index.html">
</template>

<script>
export default {
    props: {
        dulieu: {
            type: Object,
            default: function () {
                return {
                    donvi_id: 0,
                    nhanvien_id: 0,
                    trangThaiId: null,
                    loaiHDId: null,
                    dichVuId: null,
                    kieuTKId: null,
                    ngayYc: null,
                    kieu: 0
                }
            }
        }
    },
    data: function() {
        return {
            LOAIHOPDONG: {
                DAT_MOI: 1,
                CHUYEN_QUYEN: 2,
                DI_CHUYEN: 3,
                CHAMDUT_SD: 4,
                THAY_DOI_DV: 7,
                THAY_DOI_TOCDO_ADSL: 8,
                DOISO_TB: 14,
                KY_LAI_HD_GOC: 24,
                TIEPNHAN_DATMOI: 26
            },
            DICHVUVIENTHONG: {
                MEGAWAN: 8,
                TSL: 9
            },

            dsTrangThaiHD: [],
            dsLoaiHD: [],
            dsDichVu: [],
            dsKieuTK: [],
            dsKhachHang: [],
            dsHopDongTT: [],
            dsHopDongTB: [],

            totalRowDsKhachHang: 0,
            paging_dskhachhang: {
                pNo: 1,
                pSize: 10,
                pNoBefore: 1,
            },

            trangThaiId: null,
            loaiHDId: null,
            dichVuId: null,
            kieuTKId: null,
            donvi_id: 0,
            nhanvien_id: 0,
            in_hd: 1,
            kieu: 0,

            isDisableLoaiHD: false,
            isDisableDichvu: false,
            isDisableNgay: false,
            isDisableTrangthai: false,
            isDisableBtnInHopDong: false,
            isDisableBtnInPL: false,

            isVisibleBtnInBB: true,
            isVisibleBtnInPL: true,
            isVisibleBtnInHD: false,
            isVisibleBtnChapNhan: true,

            chk_loaiHD: null,
            chk_dichvu: null,
            chk_ngay: null,
            chk_trangthai: null,

            txtThongTinTC: null,
            txtMaGD: null,
            txtMaHD_TTKD: null,
            dtTuNgay: null,
            dtDenNgay: null,

            selected_loaihd_id: 0,
            dieukien: null,
            tungay: null,
            denngay: null,
            isPaging: true
        }
    },
    computed: {
        dieukienTK: function() {
            let obj = this.dsKieuTK.find(v => v.kieutk_id == this.kieuTKId);
            return obj.dieukien;
        }
    },
    methods: {
        initForm: function() {
            this.trangThaiId = this.dulieu.trangThaiId;
            this.loaiHDId = this.dulieu.loaiHDId;
            this.dichVuId = this.dulieu.dichVuId
            this.kieuTKId = this.dulieu.kieuTKId;
            this.donvi_id = this.dulieu.donvi_id;
            this.nhanvien_id = this.dulieu.nhanvien_id;
            this.kieu = this.dulieu.kieu;
            if(this.dulieu.ngayYc) {
                this.dtTuNgay = this.dulieu.ngayYc;
                this.dtDenNgay = this.dulieu.ngayYc;
            }
            else {
                let date = new Date();
                let currentDate = date.getFullYear() + '-' + ((date.getMonth() > 8) ? (date.getMonth() + 1) : ('0' + (date.getMonth() + 1))) + '-' + ((date.getDate() > 9) ? date.getDate() : ('0' + date.getDate()));;
                this.dtTuNgay = currentDate;
                this.dtDenNgay = currentDate;
            }
        },
        traCuuHopDong: async function(pageNo = 1, pageSize = 10) {
            this.isPaging = true;
            try {
                this.loading(true);
                this.paging_dskhachhang.pNoBefore = pageNo;
                // tracuu_hopdong_paging: tìm kiếm có phân trang
                let rsDsKH = await this.$root.context.post('/web-hopdong/inhopdong/tracuu_hopdong', {
                    dieukien: this.dieukien,
                    loaihd_id: this.chk_loaiHD ? this.loaiHDId ? this.loaiHDId : 0 : 0,
                    tthd_id: this.chk_trangthai ? this.trangThaiId ? this.trangThaiId : 0 : 0,
                    donvi_id: this.donvi_id,
                    nhanvien_id: this.nhanvien_id,
                    dichvuvt_id: this.chk_dichvu ? this.dichVuId ? this.dichVuId : 0 : 0,
                    tungay: this.chk_ngay ? this.tungay : null,
                    denngay: this.chk_ngay ? this.denngay : null,
                    in_hd: this.in_hd,
                    page_num: pageNo,
                    page_size: pageSize
                });
                this.dsKhachHang = rsDsKH.data;
                // this.dsKhachHang = rsDsKH.data.dsKhachHang;
                // if(this.dsKhachHang.length == 0) {
                //     this.totalRowDsKhachHang = 0;
                // }
                // else {
                //     this.totalRowDsKhachHang = rsDsKH.data.paging[0].record_count;
                // }
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        layDsHDKH: async function() {
            try {
                // this.loading(true);
                this.dieukien = this.txtThongTinTC ? this.dieukienTK.replace(':values', this.txtThongTinTC) : "0";
                let obj = this.dsKieuTK.find(v => v.kieutk_id == this.kieuTKId);
                this.tungay = this.dtTuNgay ? this.dtTuNgay.split('-') : "";
                this.denngay = this.dtDenNgay ? this.dtDenNgay.split('-') : "";
                if(this.tungay) {
                    this.tungay = this.tungay.reverse();
                    this.tungay = this.tungay.join('/');
                }
                if(this.denngay) {
                    this.denngay = this.denngay.reverse();
                    this.denngay = this.denngay.join('/');
                }
                
                if(obj.kieutk_id == 'Số ảo') {
                    let rsTbDB = await this.$root.context.get(`/web-hopdong/inhopdong/lay_matb_theo_soao_db?soao=${this.txtThongTinTC}`);
                    if(rsTbDB.data.length > 0) {
                        let ma_tb = rs.data[0].ma_tb;
                        this.dieukien = " tb.ma_tb = '" + ma_tb + "'";
                        await this.traCuuHopDong();
                    }
                    else {
                        let rsTbHD = await this.$root.context.get(`/web-hopdong/inhopdong/lay_matb_theo_soao_hd?soao=${this.txtThongTinTC}`);
                        if(rsTbHD.data.length > 0) {
                            let ma_tb = rs.data[0].ma_tb;
                            this.dieukien = " tb.ma_tb = '" + ma_tb + "'";
                            await this.traCuuHopDong();
                        }
                        else {
                            this.dsKhachHang = [];
                            this.totalRowDsKhachHang = 0;
                        }
                    }
                }
                else if(obj.kieutk_id == 'Site ID') {
                    if(!this.txtThongTinTC) {
                        this.$root.toastError("Bạn chưa nhập thông tin Site ID để tra cứu !");
                        return;
                    }
                    let rs = await this.$root.context.get(`/web-hopdong/inhopdong/lay_ds_kh_theo_site_id?siteid=${this.txtThongTinTC}`);
                    this.dsKhachHang = rs.data;
                    this.isPaging = false;
                }
                else if(obj.kieutk_id == 'Mã HĐ TTKD') {
                    if(!this.txtThongTinTC) {
                        this.$root.toastError("Bạn chưa nhập thông tin Mã hợp đồng TTKD để tra cứu !");
                        return;
                    }
                    let rs = await this.$root.context.get(`/web-hopdong/inhopdong/lay_ds_kh_theo_mahd_ttkd?mahd=${this.txtThongTinTC}`);
                    this.dsKhachHang = rs.data;
                    this.isPaging = false;
                }
                else if(obj.kieutk_id == 'Số hợp đồng KHDN') {
                    if(!this.txtThongTinTC) {
                        this.$root.toastError("Bạn chưa nhập thông tin Số hợp đồng để tra cứu !");
                        return;
                    }
                    let rs = await this.$root.context.get(`/web-hopdong/inhopdong/lay_ds_kh_theo_sohd_khdn?sohd=${this.txtThongTinTC}`);
                    this.dsKhachHang = rs.data;
                    this.isPaging = false;
                }
                else if(obj.kieutk_id == 'Số phụ lục KHDN') {
                    if(!this.txtThongTinTC) {
                        this.$root.toastError("Bạn chưa nhập thông tin Số phụ lục KHDN để tra cứu !");
                        return;
                    }
                    let rs = await this.$root.context.get(`/web-hopdong/inhopdong/lay_ds_kh_theo_sophuluc_khdn?sophuluc=${this.txtThongTinTC}`);
                    this.dsKhachHang = rs.data;
                    this.isPaging = false;
                }
                else if(obj.kieutk_id == 'Tên miền') {
                    if(!this.txtThongTinTC) {
                        this.$root.toastError("Bạn chưa nhập thông tin Tên miền để tra cứu !");
                        return;
                    }
                    let rs = await this.$root.context.get(`/web-hopdong/inhopdong/lay_ds_kh_theo_tenmien?tenmien=${this.txtThongTinTC}`);
                    this.dsKhachHang = rs.data;
                    this.isPaging = false;
                }
                else {
                    // let rsDsKH = await this.$root.context.post('/web-hopdong/inhopdong/tracuu_hopdong_paging', {
                    //     dieukien,
                    //     loaihd_id: this.chk_loaiHD ? this.loaiHDId ? this.loaiHDId : 0 : 0,
                    //     tthd_id: this.chk_trangthai ? this.trangThaiId ? this.trangThaiId : 0 : 0,
                    //     donvi_id: this.donvi_id,
                    //     nhanvien_id: this.nhanvien_id,
                    //     dichvuvt_id: this.chk_dichvu ? this.dichVuId ? this.dichVuId : 0 : 0,
                    //     tungay: tungay,
                    //     denngay: denngay,
                    //     in_hd: this.in_hd,
                    //     page_num: pageNo,
                    //     page_size: pageSize
                    // });
                    // this.dsKhachHang = rsDsKH.data.dsKhachHang;
                    // if(this.dsKhachHang.length == 0) {
                    //     this.totalRowDsKhachHang = 0;
                    // }
                    // else {
                    //     this.totalRowDsKhachHang = rsDsKH.data.paging[0].record_count;
                    // }
                    await this.traCuuHopDong();
                }
            } catch (error) {
                
            } finally {
                // this.loading(false);
            }
        },
        layDsHopDongTT: async function(hdkh_id) {
            try {
                this.loading(true);
                let rs = await this.$root.context.get(`/web-hopdong/inhopdong/lay_ds_hopdong_tt_theo_hdkhid?hdkhid=${hdkh_id}`);
                this.dsHopDongTT = rs.data;
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        layDsHopDongTB: async function(hdkh_id) {
            try {
                this.loading(true);
                let rs = await this.$root.context.get(`/web-hopdong/inhopdong/lay_ds_hopdong_tb_theo_hdkhid?hdkhid=${hdkh_id}`);
                this.dsHopDongTB = rs.data;
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        gridDsKH_rowSelected: async function(data) {
            if(data) {
                let hdkh_id = data.hdkh_id;
                this.selected_loaihd_id = data.loaihd_id;
                this.txtMaGD = data.ma_gd;

                if(this.selected_loaihd_id == this.LOAIHOPDONG.CHUYEN_QUYEN || this.selected_loaihd_id == this.LOAIHOPDONG.DAT_MOI || this.selected_loaihd_id == this.LOAIHOPDONG.KY_LAI_HD_GOC) {
                    this.isDisableBtnInHopDong = false;
                }
                else {
                    this.isDisableBtnInHopDong = true;
                }

                if(this.selected_loaihd_id == this.LOAIHOPDONG.CHUYEN_QUYEN || this.selected_loaihd_id == this.LOAIHOPDONG.CHAMDUT_SD || this.selected_loaihd_id == this.LOAIHOPDONG.THAY_DOI_DV 
                || this.selected_loaihd_id == this.LOAIHOPDONG.THAY_DOI_TOCDO_ADSL || this.selected_loaihd_id == this.LOAIHOPDONG.DI_CHUYEN || this.selected_loaihd_id == this.LOAIHOPDONG.DOISO_TB) {
                    this.isDisableBtnInPL = false;
                }
                else {
                    this.isDisableBtnInPL = true;
                }

                this.isVisibleBtnInBB = false;
                await this.layDsHopDongTT(hdkh_id);
                await this.layDsHopDongTB(hdkh_id);
            }
            else {
                this.dsHopDongTT = [];
                this.dsHopDongTB = [];
            }
        },
        gridDsTB_rowSelected: async function(data) {
            if(data) {
                let hdtb_id = data.hdtb_id;
                let dichvuvt_id = data.dichvuvt_id;
                if(dichvuvt_id == this.DICHVUVIENTHONG.MEGAWAN || this.DICHVUVIENTHONG.TSL) {
                    let mahd_ttkd;
                    if(dichvuvt_id == this.DICHVUVIENTHONG.MEGAWAN) {
                        // let rs = await this.$root.context.post('/web-hopdong/goi-da-dichvu/fn_map_id', {
                        //     id_neo: 'ma_pl',
                        //     table: 'hdtb_mgwan',
                        //     dk: 'where hdtb_id = ' + hdtb_id
                        // })
                        let rs = await this.$root.context.get(`/web-hopdong/chuyendich/fn_map_custom?loai=2&id=${hdtb_id}`);
                        mahd_ttkd = rs.data;
                    }
                    else {
                        // let rs = await this.$root.context.post('/web-hopdong/goi-da-dichvu/fn_map_id', {
                        //     id_neo: 'ma_pl',
                        //     table: 'hdtb_tsl',
                        //     dk: `where hdtb_id = ${hdtb_id} and daucuoi_id = 1 `
                        // })
                        let rs = await this.$root.context.get(`/web-hopdong/chuyendich/fn_map_custom?loai=3&id=${hdtb_id}`);
                        mahd_ttkd = rs.data;
                    }

                    if(mahd_ttkd != -1 && mahd_ttkd) {
                        this.txtMaHD_TTKD = mahd_ttkd;
                    }
                    else {
                        this.txtMaHD_TTKD = "";
                    }
                }
                else {
                    this.txtMaHD_TTKD = "";
                }
            }
        },
        dsKhachHang_PageChanged: async function(args) {
            try {
                this.loading(true);
                this.paging_dskhachhang.pNo = args.pageIndex + 1;
                this.paging_dskhachhang.pSize = args.pageSize ;
                if(this.paging_dskhachhang.pNoBefore != this.paging_dskhachhang.pNo) {
                    await this.traCuuHopDong(this.paging_dskhachhang.pNo, this.paging_dskhachhang.pSize);
                }
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        timKiem: async function() {
            this.dsHopDongTT = [];
            this.dsHopDongTB = [];
            await this.layDsHDKH();
        },
        inHoSo: function() {
            this.$root.toastInfo('Chức năng đang được thực hiện.');
        },
        inPhieuTT: function() {
            this.$root.toastInfo('Chức năng đang được thực hiện.');
        },
        khuyenMai: function() {
            this.$root.toastInfo('Chức năng đang được thực hiện.');
        },
        loadForm: async function() {
            this.initForm();
            
            if(this.in_hd == 1) {
                this.isVisibleBtnInPL = true;
                this.isVisibleBtnInBB = true;
            }
            else {
                this.isVisibleBtnInPL = false;
                this.isVisibleBtnInBB = false;
            }

            this.chk_loaiHD = this.loaiHDId ? true : false;
            this.chk_dichvu = this.dichVuId ? true : false;
            this.chk_trangthai = this.trangThaiId ? true : false;
            this.chk_ngay = this.dtTuNgay ? true : false;

            let rsTT = await this.$root.context.get('/web-hopdong/inhopdong/lay_ds_trangthai_hd');
            this.dsTrangThaiHD = rsTT.data;
            this.trangThaiId = this.dsTrangThaiHD.length > 0 ? this.dsTrangThaiHD[0].tthd_id : null;

            let rsLoaiHD = await this.$root.context.get('/web-hopdong/danhmuc/loai_hopdong');
            this.dsLoaiHD = rsLoaiHD.data;
            this.loaiHDId = this.dsLoaiHD.length > 0 ? this.dsLoaiHD[0].ID : null;
            
            let rsDichVu = await this.$root.context.get('/web-hopdong/inhopdong/lay_ds_dichvu_vt');
            this.dsDichVu = rsDichVu.data;
            this.dichVuId = this.dsDichVu.length > 0 ? this.dsDichVu[0].dichvuvt_id : null;

            let rsKieuTK = await this.$root.context.get('/web-hopdong/inhopdong/lay_ds_kieu_tk?nhomId=1');
            this.dsKieuTK = rsKieuTK.data;
            this.kieuTKId = this.dsKieuTK.length > 0 ? this.dsKieuTK[0].kieutk_id : null;
           
            if(this.kieu == 1) {
                await this.layDsHDKH();
            }
            else if(this.kieu == 0) {
                this.isVisibleBtnChapNhan = false;
            }
        },
        
        xuatHoaDonDT_Click: function() {
            
        },

        xacNhan: function() {
            this.$emit('form-close', {
                ma_gd: this.txtMaGD
            })
        }
    },
    mounted: async function() {
        try {
            this.loading(true);
            await this.loadForm();
        } catch (error) {
            
        } finally {
            this.loading(false);
        }
    },
    watch: {
        chk_loaiHD: function(val) {
            this.isDisableLoaiHD = !val;
        },
        chk_dichvu: function(val) {
            this.isDisableDichvu = !val;
        },
        chk_trangthai: function(val) {
            this.isDisableTrangthai = !val;
        },
        chk_ngay: function(val) {
            this.isDisableNgay = !val;
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
