<template src="./TraCuuDoiChieuTraCuocVinaphoneVTT.html"></template>
<style scoped src="./TraCuuDoiChieuTraCuocVinaphoneVTT.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import rtable from '../components/RTable/RTable.vue'
import TraCuuApi from '../api/TraCuuAPI'
import TraCuuDCTTVNPApi from '../api/TraCuuDoiChieuTraCuocVNP-VTTAPI'
import CommonsAPI from '@/modules/QLTN/api/Commons'
import moment from 'moment'

export default {
    components: { breadcrumb, rtable },
    name: 'TraCuuDoiChieuTraCuocVinaphoneVTT',
    mounted () {
        this.updateChuKyHoaDon(moment(this.kyHoaDonSelected).format('YYYYMM'));
        this.focusInput();
    },
    computed: {},
    data () {
        return {
            header: {
                title: 'TRA CỨU ĐỐI CHIẾU TRẢ CƯỚC VINAPHONE - VIỄN THÔNG TỈNH',
                list: [
                    { name: 'Tra cứu', link: { name: 'Ui.cards' } },
                    { name: 'Quản lý thu nợ', link: { name: 'Ui.buttons' } },
                    { name: 'Tra cứu khác', link: { name: 'Ui.buttons' } }                    
                ]
            },
            soMay: '',
            maTT: '',
            tenTT: '',
            diaChiTT: '',
            tuNgay: new Date(),
            denNgay: new Date(),
            today: new Date(),
            tuNgayChecked: false,
            dsThanhToan: [],
            dsPhieuVinaphone: [],
            dsPhieuVTT: [],
            dsPhieuChiTietVNP: [],
            dsPhieuChiTietVTT: [],
            kyHoaDonSelected: moment(new Date()).subtract(1,'months').endOf('month').toDate(),
            max_kyhoadon: moment(new Date()).subtract(1,'months').endOf('month').toDate(),
            KCValue: moment(new Date()).subtract(1,'months').endOf('month').toDate(),
            chuKy: [],
            chuKySelected: null,      
            kyCuoc: 0,
            kyHD1: moment(new Date()).add(1,'months').format('YYYYMM'),
            kyHD2: moment(new Date()).add(2,'months').format('YYYYMM'),
            maTinh: 'HPG',
            VNPFields: [                
                {
                    key: 'PHIEU_ID',
                    label: 'Phiếu ID',
                    thClass: 'center'
                },
                {
                    key: 'TRANGTHAI',
                    label: 'Trạng thái',
                    thClass: 'center'
                },
                {
                    key: 'NGAYGACH',
                    label: 'Ngày gạch',
                    thClass: 'center'
                },
                {
                    key: 'NGUOIGACH',
                    label: 'User gạch',
                    thClass: 'center'
                },
                {
                    key: 'MA_TT',
                    label: 'Mã TT',
                    thClass: 'center'
                },
                {
                    key: 'SOPHIEU',
                    label: 'Số phiếu',
                    thClass: 'center'
                },
                {
                    key: 'NGAY_TT',
                    label: 'Ngày TT',
                    thClass: 'center'
                },
                {
                    key: 'HINHTHUC',
                    label: 'Hình thức TT',
                    thClass: 'center'
                }
            ],
            VTTFields: [                
                {
                    key: 'PHIEU_ID',
                    label: 'Phiếu ID',
                    thClass: 'center'
                },
                {
                    key: 'PHIEU_ID_NEO',
                    label: 'Phiếu ID NEO',
                    thClass: 'center'
                },
                {
                    key: 'MA_TT',
                    label: 'Mã TT',
                    thClass: 'center'
                },
                {
                    key: 'NGAYGACH',
                    label: 'Ngày gạch',
                    thClass: 'center'
                },
                {
                    key: 'SOPHIEU',
                    label: 'Số phiếu',
                    thClass: 'center'
                },
                {
                    key: 'NGUOI_CN',
                    label: 'User gạch',
                    thClass: 'center'
                },                
                {
                    key: 'TONGTRA',
                    label: 'Tổng trả',
                    thClass: 'center'
                },
                {
                    key: 'HINHTHUC',
                    label: 'Hình thức TT',
                    thClass: 'center'
                }
            ],
            chiTietFields: [                
                {
                    key: 'MA_TB',
                    label: 'Số máy',
                    thClass: 'center'
                },
                {
                    key: 'MA_TT',
                    label: 'Mã TT',
                    thClass: 'center'
                },
                {
                    key: 'THANGNO',
                    label: 'Tháng trả',
                    thClass: 'center'
                },
                {
                    key: 'TRAGOC',
                    label: 'Trả gốc',
                    thClass: 'center'
                },
                {
                    key: 'TRATHUE',
                    label: 'Trả thuế',
                    thClass: 'center'
                },
                {
                    key: 'TONGTRA',
                    label: 'Tổng trả',
                    thClass: 'center'
                },
            ]
        }
    },
    methods: {
        async onSearch() {
            await this.getThongTinThanhToanTheoThanhToan();
            if (this.dsThanhToan.length === 0) {
                await this.getThongTinThanhToanTheoThueBao();
            }
            if (this.dsThanhToan.length > 0) {
                this.maTT = this.dsThanhToan[0].MA_TT;
                this.diaChiTT = this.dsThanhToan[0].DIACHI_TT;
                this.tenTT = this.dsThanhToan[0].TEN_TT;
            } else {
                this.$toast.warning('Không tìm thấy thông tin thanh toán của thuê bao trong dữ liệu viễn thông tỉnh!')
            }

            await this.getTraCuuDoiChieuTienTraVinaphone(0, 1);
            await this.getTraCuuDoiChieuTienTraVinaphone(0, 2);
        },

        focusInput() {
            this.$refs.soMay.focus();
        },

        onRowSelectDSVNP(item) {
            console.log(item);
        },

        onRowSelectDSVTT(item) {
            console.log(item);
        },

        onKyHoaDonChange: function(args) {      
            if(args.value === null){
                this.$toast.error('Vui lòng chọn Kỳ hóa đơn')
                return
            }
            this.kyHD1 = moment(args.value).add(1,'months').format('YYYYMM');
            this.kyHD2 = moment(args.value).add(2,'months').format('YYYYMM');
            this.updateChuKyHoaDon(moment(args.value).format('YYYYMM'))      
        },    

        async updateChuKyHoaDon(pKyHoaDon) {
            try {
                this.loading(true);
                let response = await CommonsAPI.getChuKyNoTheoKyCuoc(this.axios, pKyHoaDon);
                if (response.data.error_code === 'BSS-00000000'
                    && response.data.data !== undefined
                    && response.data.data.length > 0) {
                    this.chuKy = response.data.data;
                    this.chuKySelected = this.chuKy[0].CHUKY;
                } else {
                    this.chuKy = [];
                    this.chuKySelected = null;
                }
                this.kyCuoc = moment(this.kyHoaDonSelected).format('YYYYMM') + this.chuKySelected;
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false)
            }
        },  

        async getThongTinThanhToanTheoThueBao() {          
            try {
                this.loading(true); 
                let apiParams = {
                    pKyCuoc: this.kyCuoc,
                    pPhanVungId: this.$auth.getPhanVungID() !== -1 ? this.$auth.getPhanVungID() : 26,
                    pMaTB: this.soMay
                };
                let response = await TraCuuApi.getThongTinThanhToanTheoThueBao(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {          
                    this.dsThanhToan = response.data.data;              
                }                
            } 
            catch (error) {
                console.log(error);
            } 
            finally {
                this.loading(false);
            }      
        },

        async getThongTinThanhToanTheoThanhToan() {      
            try {
                this.loading(true);
                let apiParams = {
                    pKyCuoc: this.kyCuoc,
                    pPhanVungId: this.$auth.getPhanVungID() !== -1 ? this.$auth.getPhanVungID() : 26,
                    pMaTT: this.soMay
                };                
                let response = await TraCuuApi.getThongTinThanhToanTheoThanhToan(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {          
                    this.dsThanhToan = response.data.data;         
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }        
        },

        /* kieu: 1 - Vinaphone; 2 - Vien thong tinh */
        async getTraCuuDoiChieuTienTraVinaphone(phieuId, kieu)
        {
            try {
                this.loading(true);
                let apiParams = {
                    pKyCuoc: this.kyCuoc,
                    pPhanVungId: this.$auth.getPhanVungID() !== -1 ? this.$auth.getPhanVungID() : 26,
                    pTuNgay: this.tuNgayChecked ? moment(this.tuNgay).format('DD/MM/YYYY') : null,
                    pDenNgay: this.tuNgayChecked ? moment(this.denNgay).format('DD/MM/YYYY') : null,
                    pMaTB: this.soMay,
                    pPhieuId: phieuId,
                    pKieu: kieu,
                    pKyHD1: this.kyHD1,
                    pKyHD2: this.kyHD2,
                    pMaTinh: this.maTinh,
                    pDBLVNP: this.maTinh + '_vnp',
                    pageNo: 0,
                    pageSize: 1000
                };
                let response = await TraCuuDCTTVNPApi.getTraCuuDoiChieuTienTraVinaphone(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                    if (kieu === 1) {
                        this.dsPhieuVinaphone = response.data.data.data;
                    } else {
                        this.dsPhieuVTT = response.data.data.data;
                    }
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            } 
        }
    }
}
</script>