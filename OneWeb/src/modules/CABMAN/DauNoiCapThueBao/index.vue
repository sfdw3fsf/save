<template src='./index.html'>
</template>

<script>
import ModalDauNoi from './modal'
// import SearchSubsAccount from '../../EXTS/HOPDONG/ThayDoiThongTinKhuyenMai/Popups/SearchSubsAccount'
import SearchSubsAccount from '@/modules/search/subscriber/SearchSubsAccount'
import ClickOutside from "vue-click-outside";

let templateChucNang = function() {
    return {
        template: {
            template: `
                    <div style="text-align: center;">
                        <button class="btn btn-second" @click="repoCHUCNANG_ButtonClick">
                            <span class="fa fa-times"></span>
                        </button>
                    </div> 
                `,
            data() {
                return {
                    data: {

                    },
                }
            },
            computed: {
                parent() {
                    return this.$parent.$parent.$parent;
                },
            },
            methods: {
                repoCHUCNANG_ButtonClick: function() {
                    this.parent.isDelete = true;
                    this.parent.repoCHUCNANG_ButtonClick(this.data.DAUNOI_CAP_ID);
                }
            },
        },
    }
}

let templateGhiChu = function() {
    return {
        template: {
            template: `
                    <div class="input-more-button -right" v-click-outside="onClickOutsideGhiChu">
                        <button class="btn btn-second" @click="repoGHICHU_ButtonClick" v-if="isVisibleGhiChu">
                            <span class="fa fa-check"></span>
                        </button>
                        <input type="text" class="form-control" v-model="data.GHICHU" @click="ghichu_click"></input>
                    </div> 
                `,
            data() {
                return {
                    data: {

                    },
                    isVisibleGhiChu: false
                }
            },
            computed: {
                parent() {
                    return this.$parent.$parent.$parent;
                },
            },
            methods: {
                repoGHICHU_ButtonClick: function() {
                    this.parent.repoGHICHU_ButtonClick(this.data.DAUNOI_CAP_ID, this.data.GHICHU);
                },
                ghichu_click: function() {
                    this.isVisibleGhiChu = true;
                },
                onClickOutsideGhiChu: function() {
                    this.isVisibleGhiChu = false;
                }
            },
        },
    }
}

let templateCuLy = function() {
    return {
        template: {
            template: `
                    <div class="input-more-button -right" v-click-outside="onClickOutsideCuLy">
                        <button class="btn btn-second" @click="repoCULY_ButtonClick" v-if="isVisibleCuLy">
                            <span class="fa fa-check"></span>
                        </button>
                        <input type="text" class="form-control" v-model="data.CULY" @click="culy_click">
                    </div> 
                `,
            data() {
                return {
                    data: {

                    },
                    isVisibleCuLy: false
                }
            },
            computed: {
                parent() {
                    return this.$parent.$parent.$parent;
                },
            },
            methods: {
                repoCULY_ButtonClick: function() {
                    this.parent.repoCULY_ButtonClick(this.data.DAUNOI_CAP_ID, this.data.CULY);
                },
                culy_click: function() {
                    this.isVisibleCuLy = true;
                },
                onClickOutsideCuLy: function() {
                    this.isVisibleCuLy = false;
                }
            },
        },
    }
}
export default {
    components: {
        ModalDauNoi,
        SearchSubsAccount
    },
    data: function() {
        return {
            ketcuoi_id: 0,
            vitri: null,
            mat: 2,
            thuebao: null,
            hopdong: null,

            rd_tracuu: 0,
            txtMaTB: null,
            txtTenTB: null,
            txtDiaChiLD: null,
            txtLoaiHinh: null,
            txtTrangThai: null,
            txtToQL: null,
            txtTramTB: null,
            txtLoaiHD: null,
            txtKieuLD: null,
            txtGhiChu: null,

            dsDauNoi: [],
            dsDanDo: [],
            isDisableBtnDauNoi: true,
            isDisableBtnCapNhatGC: true,
            isReadonlyGhiChu: true,

            selectedDauNoi: null,
            rowIndexDsDauNoi: -1,
            templateChucNang: templateChucNang,
            templateGhiChu: templateGhiChu,
            templateCuLy: templateCuLy,
            isDelete: false
        }
    },
    methods: {
        frmDauNoiCapTB: function(data) {
            if(Object.keys(data).length > 0) {
                if(data.thuebao_id) {
                    this.rd_tracuu = data.thuebao_id > 0 ? 0 : 1;
                    this.NAP_TT_THUEBAO(data.thuebao_id)
                }
                else {
                    this.ketcuoi_id = data.ketcuoi_id;
                    this.vitri = data.vitri;
                    this.mat = data.mat
                }
            }
        },
        NAP_TT_THUEBAO: async function(thuebao_id = 0) {
            try {
                this.loading(true);
                if(!this.txtMaTB) {
                    this.$root.toastError('Số máy / Account không được để trống');
                    return;
                }
                let rs = await this.$root.context.post('/web-cabman/daunoi-cap-thuebao/lay-thongtin-thuebao', {
                    loai: this.rd_tracuu,
                    ma_tb: this.txtMaTB,
                    thuebao_id: thuebao_id
                });
                let idx = 0;
                if(rs.data.length > 0) {
                    this.thuebao = rs.data[idx];
                    if(rs.data.length > 1) {
                        let ret = rs.data.filter(v => v.MA_TB_SUB == this.txtMaTB);
                        if(ret.length == 0) {
                            //this.thuebao = rs.data[idx];    
                        }
                        else {
                            this.thuebao = ret[0];
                        }
                    }
                    this.hopdong = this.thuebao.THUEBAO_ID < 0;
                    this.txtMaTB = this.thuebao.MA_TB_SUB ? this.thuebao.MA_TB_SUB : this.thuebao.MA_TB;
                    this.txtLoaiHinh = this.thuebao.LOAI_HINH;
                    this.txtTenTB = this.thuebao.TEN_TB;
                    this.txtTrangThai = this.thuebao.TRANGTHAI_TB;
                    this.txtDiaChiLD = this.thuebao.DIACHI_LD;
                    this.txtToQL = this.thuebao.TO_QL;
                    this.txtTramTB = this.thuebao.TRAM_TBI;
                    this.txtLoaiHD = this.thuebao.LOAI_HD;
                    this.txtKieuLD = this.thuebao.KIEU_LD;
                    this.txtGhiChu = this.thuebao.GHICHU;

                    await this.NAP_DS_DAUNOI();
                    let flag = this.hopdong ? [6, 7].some(v => v == this.thuebao.TRANGTHAITB_ID) : [7, 8, 9].some(v => v == this.thuebao.TRANGTHAITB_ID);
                    this.isDisableBtnDauNoi = flag;
                    this.isDisableBtnCapNhatGC = flag;
                    this.isReadonlyGhiChu = flag;
                }
                else {
                    this.$root.toastError(`Không tìm thấy thuê bao với mã ${this.txtMaTB}`);
                    this.txtLoaiHinh = null;
                    this.txtTenTB = null;
                    this.txtTrangThai = null;
                    this.txtDiaChiLD = null;
                    this.txtToQL = null;
                    this.txtTramTB = null;
                    this.txtLoaiHD = null;
                    this.txtKieuLD = null;
                    this.txtGhiChu = null;
                    this.dsDauNoi = [];
                    this.dsDanDo = [];
                    this.isDisableBtnDauNoi = true;
                    this.isDisableBtnCapNhatGC = true;
                    this.isReadonlyGhiChu = true;
                    return;
                }
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        NAP_DS_DAUNOI: async function() {
            try {
                let rs = await this.$root.context.post('/web-cabman/daunoi-cap-thuebao/lay-ds-daunoi', {
                    thuebao_id: this.thuebao.THUEBAO_ID,
                    daucuoi_id: this.thuebao.DAUCUOI_ID
                });
                this.rowIndexDsDauNoi = -1;
                this.dsDauNoi = rs.data;
                if(this.dsDauNoi.length == 0) {
                    this.dsDanDo = [];
                }
            } catch (error) {
                
            }
        },
        NAP_DS_DANDO: async function() {
            try {
                let rs = await this.$root.context.post('/web-cabman/daunoi-cap-thuebao/lay-ds-dando', {
                    loaitb_id: this.selectedDauNoi.LOAITBI_ID,
                    thietbi_id: this.selectedDauNoi.THIETBI_ID,
                    vitri: this.selectedDauNoi.VITRI,
                    mat: this.mat
                });
                this.dsDanDo = rs.data;
            } catch (error) {
                
            }
        },
        gridDsDauNoi_rowSelected: async function(args) {
            if(this.isDelete) {
                return;
            }
            if(this.rowIndexDsDauNoi != args.rowIndex) {
                try {
                    this.loading(true);
                    this.rowIndexDsDauNoi = args.rowIndex;
                    this.selectedDauNoi = args.data;
                    await this.NAP_DS_DANDO();
                } catch (error) {
                    
                } finally {
                    this.loading(false);
                }
            }
        },
        gridDsDanDo_rowSelected: function() {

        },
        btnLayTT_Click: function() {
            this.NAP_TT_THUEBAO();
        },
        btnDauNoi_Click: async function() {
            if(this.ketcuoi_id != 0) {
                let kq = await this.$refs.formModalDauNoi.KIEMTRA_DANDO(null, this.thuebao, 25, this.ketcuoi_id, this.vitri, this.mat);
                if(!kq) {
                    return;
                }
                kq = await this.$refs.formModalDauNoi.DAUNOI_CAP_THUEBAO(this.thuebao, 25, this.ketcuoi_id, this.vitri, 0, "");
                if(!kq) {
                    return;
                }
            }
            else {
                this.$bvModal.show('popup-daunoicapthuebao');

            }
        },
        btnCapNhatGC_Click: async function() {
            try {
                if(!this.txtGhiChu) {
                    this.$root.toastError("Bạn chưa nhập ghi chú");
                    return;
                }
                else if(this.txtGhiChu.length > 800) {
                    this.$root.toastError("Độ dài ghi chú tối đa 800 ký tự");
                    return;
                }
                let dc = false;
                let db;
                switch (this.thuebao.DICHVUVT_ID) {
                    case 1:
                        // db = this.hopdong ? "hdtb_cd" : "db_cd";
                        db = this.hopdong ? 1 : 2;
                        break;
                    case 4:
                        // db = this.hopdong ? "hdtb_adsl" : "db_adsl";
                        db = this.hopdong ? 3 : 4;
                        break;
                    case 7:
                    case 8:
                        // db = this.hopdong ? "hdtb_mgwan" : "db_mgwan";
                        db = this.hopdong ? 5 : 6;
                        break;
                    case 9:
                        // db = this.hopdong ? "hdtb_tsl" : "db_tsl";
                        db = this.hopdong ? 7 : 8;
                        dc = true;
                        break;    
                    case 10:
                        // db = this.hopdong ? "hdtb_gp" : "db_gp";
                        db = this.hopdong ? 9 : 10;
                        break;    
                    case 11:
                        // db = this.hopdong ? "hdtb_ims" : "db_ims";
                        db = this.hopdong ? 11 : 12;
                        break;    
                    default:
                        this.$root.toastError(`Dịch vụ ${this.thuebao.DICHVUVT_ID} chưa được hỗ trợ!`);
                        return;
                }
                let rs = await this.$root.context.post('/web-cabman/daunoi-cap-thuebao/capnhat-ghichu-thongtin-thuebao', {
                    thuebao_id: this.thuebao.THUEBAO_ID,
                    daucuoi_id: this.thuebao.DAUCUOI_ID,
                    ghichu: this.txtGhiChu,
                    ishopdong: this.hopdong ? 1 : 0,
                    dc: dc ? 1 : 0,
                    db
                });
                this.$root.toastSuccess('Cập nhật ghi chú thành công!');
                await this.NAP_TT_THUEBAO();
            } catch (error) {
                
            }
        },
        repoCHUCNANG_ButtonClick: function(doinoi_cap_id) {
             this.$bvModal.msgBoxConfirm('Bạn có muốn gỡ đấu nối đường cáp đang chọn không?', {
                size: 'sm',
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy',
            }).then(async v => {
                if(v) {
                    try {
                        this.loading(true);
                        let rs = await this.$root.context.post(`/web-cabman/daunoi-cap-thuebao/go-daunoi/${doinoi_cap_id}`);
                        if(rs.data.result) {
                            this.$root.toastSuccess('Gỡ đấu nối thành công!');
                            await this.NAP_DS_DAUNOI();
                        }
                    } catch (error) {
                        this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : 'Xóa thất bại');
                    } finally {
                        this.loading(false);
                    }
                }
                this.isDelete = false;
            }).catch(error => {

            })
        },
        repoGHICHU_ButtonClick: async function(doinoi_cap_id, ghichu) {
            try {
                let rs = await this.$root.context.post('/web-cabman/daunoi-cap-thuebao/capnhat-ghichu-daunoi', {
                    daunoi_cap_id: doinoi_cap_id,
                    ghichu: ghichu
                });
                if(rs.data.result) {
                    this.$root.toastSuccess('Cập nhật ghi chú thành công!');
                    await this.NAP_DS_DAUNOI();
                }
            } catch (error) {

                this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : 'Cập nhật ghi chú thất bại');
            }
        },
        repoCULY_ButtonClick: async function(doinoi_cap_id, culy) {
            try {
                let rs = await this.$root.context.post('/web-cabman/daunoi-cap-thuebao/capnhat-culy-daunoi', {
                    daunoi_cap_id: doinoi_cap_id,
                    culy: culy
                });
                if(rs.data.result) {
                    this.$root.toastSuccess('Cập nhật cự ly thành công!');
                    await this.NAP_DS_DAUNOI();
                }
            } catch (error) {
                this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : 'Cập nhật cự ly thất bại');
            }
        },
        xacnhan: function(data) {
            if(data != 'OK') {
                return;
            }
            this.$root.toastSuccess('Đã đấu nối thuê bao vào tuyến cáp!');
            this.NAP_TT_THUEBAO();
        },
        bntLayMaKh_Click: function() {
            // this.popupComponent = () => import('@/modules/search/subscriber/SearchAccount')
            this.$refs.SearchSubsAccountModal.show()
        },
        modelClose: function(data) {
            this.$refs.SearchSubsAccountModal.hide();
            this.txtMaTB = data.ma_tb;
        },
    },
    mounted: function() {
        // this.dsDauNoi = [
        //     {
        //         'STT': 1,
        //         'LOAI_TBI': 2,
        //         'TEN_TBI': 3,
        //         'VITRI': 4,
        //         'CULY': 5,
        //         'GHICHU': 6,
        //         'NGAY_CN': 7,
        //         'NGUOI_CN': 8
        //     },
        //     {
        //         'STT': 1,
        //         'LOAI_TBI': 'a',
        //         'TEN_TBI': 3,
        //         'VITRI': 4,
        //         'CULY': 5,
        //         'GHICHU': 6,
        //         'NGAY_CN': 7,
        //         'NGUOI_CN': 8
        //     }
        // ]
    },
    watch: {
        rd_tracuu: function(val) {
            this.txtLoaiHinh = null;
            this.txtTenTB = null;
            this.txtTrangThai = null;
            this.txtDiaChiLD = null;
            this.txtToQL = null;
            this.txtTramTB = null;
            this.txtLoaiHD = null;
            this.txtKieuLD = null;
            this.txtGhiChu = null;

            this.dsDauNoi = [];
            this.dsDanDo = [];

            this.isDisableBtnDauNoi = true;
            this.isDisableBtnCapNhatGC = true;
            this.isReadonlyGhiChu = true;
        }
    },
    directives: {
        ClickOutside,
    },
}
</script>

<style scoped>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
