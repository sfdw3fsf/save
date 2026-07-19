<template src="./PopupTimKiemNhanVienThuCuoc.html"></template>
<style scoped src="../../TaoDanhSachNangGoi/TaoDanhSachNangGoi.scss">    
</style>
<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import API from '../../api/ChuyenPhieuXLNLacHuongAPI.js'

export default {
    components: {},
    name: 'PopupTimKiemNhanVienThuCuoc',

    props: {
        input: Object
        // { 
        //     quan_Id
        //     phuong_Id
        //     pho_Id
        //     ap_Id
        //     khu_Id
        //     dacDiem_Id
        //     nhanVienTC_Id
        //     tuyenThu_Id
        //     donViQL_Id
        //     httt_Id
        //     kt_KyLai
        // }
    },

    async created () {        
        await this.initModal();
    },

    computed: {
        ...mapGetters('qltnCommon', [
            'danhMucQuanHuyenGetter', 
            'danhSachHinhThucThanhToanGetter', 
            'danhsachDonViGetter'
        ])
    },
   
    data () {
        return {
            target: '.main-wrapper',
            animationSettings: { effect: 'Zoom' },
            params: {
                quan_Options: [],
                quan_Selected: null,
                quan_Checked: true,
                phuong_Options: [],
                phuong_Selected: null,
                phuong_Checked: false,
                pho_Options: [],
                pho_Selected: null,
                pho_Checked: false,
                ap_Options: [],
                ap_Selected: null,
                ap_Checked: false,
                khu_Options: [],
                khu_Selected: null,
                khu_Checked: false,
                dacDiem_Options: [],
                dacDiem_Selected: null,
                dacDiem_Checked: false,
                dacDiem_Enabled: false,
                donViQL_Options: [],
                donViQL_Selected: null,
                HTTT_Options: [],
                HTTT_Selected: null,
                HTTT_Checked: false,
                maNVTC: '',
                soDT: '',
                tenNVTC: '',
                locTheoNVTN_Checked: false,            
                kieu: 1
            },            
            NVTC_grid: {
                items: [],
                selected: {}                
            },
            tuyenThuId: null
        }
    },

    methods: {
        ...mapActions('qltnCommon', [
            'getDanhMucQuanHuyen', 
            'getDanhSachHinhThucThanhToan', 
            'getDanhSachDonVi'
        ]),
        
        dialogOpen() {
            this.params.quan_Checked = true;
            this.params.quan_Selected = this.input.quan_Id ? this.input.quan_Id : this.params.quan_Selected;            
            this.params.phuong_Checked = false;
            this.params.phuong_Selected = this.input.phuong_Id ? this.input.phuong_Id : this.params.phuong_Selected;
            this.params.pho_Checked = false;
            this.params.pho_Selected = this.input.pho_Id ? this.input.pho_Id : this.params.pho_Selected;
            this.params.ap_Checked = false;
            this.params.ap_Selected = this.input.ap_Id ? this.input.ap_Id : this.params.ap_Selected;
            this.params.khu_Checked = false;
            this.params.khu_Selected = this.input.khu_Id ? this.input.khu_Id : this.params.khu_Selected;
            this.params.dacDiem_Checked = false;
            this.params.dacDiem_Selected = this.input.dacDiem_Id ? this.input.dacDiem_Id : this.params.dacDiem_Selected;
            this.tuyenThuId = this.input.tuyenThu_Id ? this.input.tuyenThu_Id : this.tuyenThuId;
            this.params.HTTT_Selected = this.input.httt_Id ? this.input.httt_Id : this.params.HTTT_Selected;
            this.params.maNVTC = '';
            this.params.tenNVTC = '';
            this.params.soDT = '';
            if (this.input.donViQL_Id) {
                let idx = this.params.donViQL_Options.findIndex(e => e.id === Number(this.input.donViQL_Id))
                if (idx !== -1)
                    this.params.donViQL_Selected = this.input.donViQL_Id;
                else
                    this.$toast.error(`Không lấy được đơn vị quản lý: donviql_id = ${this.input.donViQL_Id}`);
            } else
                this.params.donViQL_Selected = this.params.donViQL_Options.length > 0 ? this.params.donViQL_Options[0].id : null;
        },

        dialogClose() {
        },

        openDialog () {
            this.$refs.dlgTimKiemNVTC.show();
        },

        async initModal() {
            if (this.danhMucQuanHuyenGetter == null || this.danhMucQuanHuyenGetter.length === 0)
                await this.getDanhMucQuanHuyen();            
            this.danhMucQuanHuyenGetter.forEach(item => {
                this.params.quan_Options.push({ id: item.QUAN_ID, text: item.TEN_QUAN });
            });

            if (this.danhsachDonViGetter == null || this.danhsachDonViGetter.length === 0)
                await this.getDanhSachDonVi();            
            if (this.danhsachDonViGetter.length > 0) {
                this.danhsachDonViGetter.forEach(item => {
                    this.params.donViQL_Options.push({ id: item.DONVI_ID, text: item.TEN_DV });
                });
                if (this.params.donViQL_Selected == null)
                    this.params.donViQL_Selected = this.params.donViQL_Options[0].id;
            }

            if (this.danhSachHinhThucThanhToanGetter == null || this.danhSachHinhThucThanhToanGetter.length === 0)
                await this.getDanhSachHinhThucThanhToan();
            if (this.danhSachHinhThucThanhToanGetter.length > 0) {
                this.danhSachHinhThucThanhToanGetter.forEach(item => {
                    this.params.HTTT_Options.push({ id: item.HTTT_ID, text: item.HTTT });
                });
                this.params.HTTT_Selected = this.params.HTTT_Options[0].id;
            }
        },

        async onSearch() {
            this.params.maNVTC = this.params.maNVTC.trim();
            this.params.tenNVTC = this.params.tenNVTC.trim();
            await this.getDSNhanVienThuCuoc(this.params.maNVTC, this.params.tenNVTC);            
        },

        /* type: 1 - Mã NVTC; 2 - Tên NVTC */
        async onKeypress(type) {
            this.params.maNVTC = this.params.maNVTC.trim();
            this.params.tenNVTC = this.params.tenNVTC.trim();
            let maNVTC = type === 1 ? this.params.maNVTC : '';
            let tenNVTC = type === 2 ? this.params.tenNVTC : '';
            await this.getDSNhanVienThuCuoc(maNVTC, tenNVTC, true);
            if (this.NVTC_grid.items.length <= 0) 
                this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
        },

        onClickAccept() {
            this.$emit('acceptChange',
            { 
                quanId: this.params.quan_Selected,
                nhanVienTC_Id: this.NVTC_grid.items.length > 0 ? this.NVTC_grid.selected.NHANVIEN_ID : null,
                maNVTC: this.NVTC_grid.items.length > 0 ? this.NVTC_grid.selected.MANV_TC : null,
                maTuyen: this.NVTC_grid.items.length > 0 ? this.NVTC_grid.selected.MA_TUYENTHU : null,
                tuyenThuId: this.NVTC_grid.items.length > 0 ? this.NVTC_grid.selected.TUYENTHU_ID : null,
                diaChi: this.NVTC_grid.items.length > 0 ? this.NVTC_grid.selected.DIACHI : null,
                tenNVTC: this.NVTC_grid.items.length > 0 ? this.NVTC_grid.selected.TENNV_TC : null,
                tenTuyen: this.NVTC_grid.items.length > 0 ? this.NVTC_grid.selected.TENTUYEN : null
            });
            this.close();
        },

        close() {            
            this.$refs.dlgTimKiemNVTC.hide();
        },

        onNhanVienSelected(e) {
            if (e !== null) {
                this.NVTC_grid.selected = e;
                this.params.maNVTC = e.MANV_TC;
                this.params.tenNVTC = e.TENNV_TC;
                this.params.soDT = e.SO_DT;
                this.tuyenThuId = e.TUYENTHU_ID;
            }
        },

        async onGetDacDiem() {
            let check = false;
            if (!this.params.pho_Checked || this.params.pho_Selected == null)
                check = true;
            if (!this.params.ap_Checked || this.params.ap_Selected == null)
                check = true;
            if (!this.params.khu_Checked || this.params.khu_Selected == null)
                check = true;
            if (check) {
                this.params.dacDiem_Options = [];
                this.params.dacDiem_Checked = false;
                this.params.dacDiem_Enabled = false;
            } else {
                await this.getDanhSachDacDiem();
                if (this.params.dacDiem_Options.length > 0) {
                    this.params.dacDiem_Checked = false;
                    this.params.dacDiem_Enabled = true;
                } else {
                    this.params.dacDiem_Options = [];
                    this.params.dacDiem_Checked = false;
                    this.params.dacDiem_Enabled = false;
                }
            }
        },

        async getDanhSachPhuongTheoQuan(quanId) {
            try {
                this.loading(true);
                this.params.phuong_Options = [];
                let apiParams = {
                    pQuanId: quanId
                }
                let response = await API.getDanhSachPhuongTheoQuan(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {          
                    response.data.data.forEach(item => {
                        this.params.phuong_Options.push({ id: item.PHUONG_ID, text: item.TEN_PHUONG });
                    });
                    this.params.phuong_Selected = this.params.phuong_Options[0].id;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        /* nhomPho: 1 - Phố; 2 - Ấp; 3 - Khu */
        async getDanhSachPhoApKhuTheoPhuong(phuongId, nhomPho) {
            try {
                this.loading(true);
                if (nhomPho === 1)
                    this.params.pho_Options = [];
                else if (nhomPho === 2)
                    this.params.ap_Options = [];
                else this.params.khu_Options = [];
                let apiParams = {
                    pPhuongId: phuongId,
                    pNhomPhoId: nhomPho
                }
                let response = await API.getDanhSachPhoApKhuTheoPhuong(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    if (nhomPho === 1) {
                        response.data.data.forEach(item => {                        
                            this.params.pho_Options.push({ id: item.ID, text: item.TEN });                        
                        });
                        this.params.pho_Selected = this.params.pho_Options[0].id;
                    }
                    else if (nhomPho === 2) {
                        response.data.data.forEach(item => {                        
                            this.params.ap_Options.push({ id: item.ID, text: item.TEN });                        
                        });
                        this.params.ap_Selected = this.params.ap_Options[0].id;
                    } else {
                        response.data.data.forEach(item => {                        
                            this.params.khu_Options.push({ id: item.ID, text: item.TEN });                        
                        });
                        this.params.khu_Selected = this.params.khu_Options[0].id;
                    }                    
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDanhSachDacDiem() {
            try {
                this.loading(true);
                this.params.dacDiem_Options = [];
                let apiParams = {
                    pPhuongId: this.params.phuong_Selected,
                    pPhoId: this.params.pho_Checked ? this.params.pho_Selected : null,
                    pApId: this.params.ap_Checked ? this.params.ap_Selected : null,
                    pKhuId: this.params.khu_Checked ? this.params.khu_Selected : null
                }
                let response = await API.getDanhSachDacDiem(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {          
                    response.data.data.forEach(item => {
                        this.params.dacDiem_Options.push({ id: item.DACDIEM_ID, text: item.DACDIEM });
                    });
                    this.params.dacDiem_Selected = this.params.dacDiem_Options[0].id;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDSNhanVienThuCuoc(maNVTC = '', tenNVTC = '', isKeyPress = false)
        {
            try {
                this.loading(true);
                this.NVTC_grid.items = [];
                let apiParams = {
                    pPhanVungId: this.$auth.getPhanVungID(),
                    pMaNVTC: maNVTC.trim() !== '' ? maNVTC.trim() : null,
                    pTenNVTC: tenNVTC.trim() !== '' ? tenNVTC.trim() : null,
                    pDonViId: this.params.donViQL_Selected,
                    pQuanId: this.params.quan_Checked & !isKeyPress ? this.params.quan_Selected : null,
                    pPhuongId: this.params.phuong_Checked & !isKeyPress ? this.params.phuong_Selected : null,
                    pPhoId: this.params.pho_Checked & !isKeyPress ? this.params.pho_Selected : null,
                    pApId: this.params.ap_Checked & !isKeyPress ? this.params.ap_Selected : null,
                    pKhuId: this.params.khu_Checked & !isKeyPress ? this.params.khu_Selected : null,
                    pDacDiemId: this.params.dacDiem_Checked & !isKeyPress ? this.params.dacDiem_Selected : null,
                    pHTTT: this.params.HTTT_Selected & !isKeyPress ? this.params.HTTT_Selected : null,
                    pageNo: 0,
                    pageSize: 1000
                }
                let response = await API.getDSNhanVienThuCuoc(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data.data !== undefined) {          
                    this.NVTC_grid.items = response.data.data.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        }
    },

    watch: {
        'params.quan_Checked': async function(newVal, oldVal) {
            if (!newVal)
                this.params.phuong_Checked = false;
            await this.getDSNhanVienThuCuoc();
        },
        'params.quan_Selected': async function(newVal, oldVal) {
            await this.getDanhSachPhuongTheoQuan(newVal);
            if (this.params.quan_Checked)
                await this.getDSNhanVienThuCuoc();
        },
        'params.phuong_Checked': async function(newVal, oldVal) {
            if (!newVal) {
                this.params.pho_Checked = false;
                this.params.ap_Checked = false;
                this.params.khu_Checked = false;
            }
            if (this.params.quan_Checked)       //Incase uncheck Quan will emit an event to checked Phuong
                await this.getDSNhanVienThuCuoc();            
        },
        'params.phuong_Selected': async function(newVal, oldVal) {
            await this.getDanhSachPhoApKhuTheoPhuong(newVal, 1);    //Pho
            await this.getDanhSachPhoApKhuTheoPhuong(newVal, 2);    //Ap
            await this.getDanhSachPhoApKhuTheoPhuong(newVal, 3);    //Khu
            if (this.params.phuong_Checked)
                await this.getDSNhanVienThuCuoc();
        },
        'params.pho_Checked': async function(newVal, oldVal) {
            await this.onGetDacDiem();
            if (this.params.phuong_Checked) {    //Incase uncheck Phuong will emit an event to checked Pho                    
                await this.getDSNhanVienThuCuoc();
            }            
        },
        'params.pho_Selected': async function(newVal, oldVal) {
            await this.onGetDacDiem();
            if (this.params.pho_Checked)
                await this.getDSNhanVienThuCuoc();            
        },
        'params.ap_Checked': async function(newVal, oldVal) {
            await this.onGetDacDiem();
            if (this.params.phuong_Checked) {    //Incase uncheck Phuong will emit an event to checked Ap                    
                await this.getDSNhanVienThuCuoc();
            }            
        },
        'params.ap_Selected': async function(newVal, oldVal) {
            await this.onGetDacDiem();
            if (this.params.ap_Checked)
                await this.getDSNhanVienThuCuoc();            
        },
        'params.khu_Checked': async function(newVal, oldVal) {
            await this.onGetDacDiem();
            if (this.params.phuong_Checked) {    //Incase uncheck Phuong will emit an event to checked Khu                    
                await this.getDSNhanVienThuCuoc();
            }            
        },
        'params.khu_Selected': async function(newVal, oldVal) {
            await this.onGetDacDiem();
            if (this.params.khu_Checked)
                await this.getDSNhanVienThuCuoc();            
        },
        'NVTC_grid.items': async function(newVal, oldVal) {
            if (newVal.length <= 0) {
                this.params.maNVTC = '';
                this.params.tenNVTC = '';
                this.params.soDT = '';
            }
        },
        'params.donViQL_Selected': async function(newVal, oldVal) {
            if (newVal)
                await this.getDSNhanVienThuCuoc();
        }
    }
}
</script>
