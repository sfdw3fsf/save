<template src="./TraCuuChuyenPhieuLacHuongPhanChiaKH.html"></template>
<style scoped src="./TraCuuChuyenPhieuLacHuongPhanChiaKH.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import CommonsAPI from '@/modules/QLTN/api/Commons'
import API from '../api/TraCuuChuyenPhieuLacHuongPhanChiaKHAPI'
import ComboboxGrid from "../components/ComboboxGrid"
import XuatFile from "../../CSKH/utils/XuatFile.vue"
import { mapActions, mapGetters } from "vuex"
import moment from 'moment'
import xlsx from "xlsx"

export default {
    components: { breadcrumb, appModalXuatDuLieu: XuatFile, appCombobox: ComboboxGrid },
    name: 'TraCuuChuyenPhieuLacHuongPhanChiaKH',

    async created () {
        if (Object.keys(this.kyHoaDonHienTaiGetter).length <= 0)
            await this.getKyHoaDonHienTai();
        if (Object.keys(this.kyHoaDonHienTaiGetter).length > 0)
            this.params.kyHoaDonSelected = moment(this.kyHoaDonHienTaiGetter.KYCUOC, 'YYYYMMDD').toDate();
        await this.getDSNhanVienTinhCuoc();        
    },

    mounted () {
        this.updateChuKyHoaDon(moment(this.kyHoaDonSelected).format('YYYYMM'));
        this.$refs.maTT.focus();
    },

    computed: {
        ...mapGetters('qltnCommon', ['kyHoaDonHienTaiGetter'])
    },

    data () {
        return {
            params: {
                kyCuoc: 0,
                kyHoaDonSelected: moment(new Date()).subtract(1,'months').endOf('month').toDate(),
                max_kyhoadon: moment(new Date()).subtract(1,'months').endOf('month').toDate(),
                chuKy: [],
                chuKySelected: null,
                nhanVienTC_Checked: true,
                nhanVienTC_Options: [],
                nhanVienTC_Selected: null,
                maTT: '',                
                loaiPhieu: 1,
                kieu: 1,
                isFileInput: false
            },                              
            dsPhieuLH: [],
            dsPhanChiaKH: [],
            dsThayDoiKH: [],
            dsExport: []                       
        }
    },

    methods: {
        ...mapActions('qltnCommon', ['getKyHoaDonHienTai']),

        nhanVienTC_selectedChanged(args) {
            if (args != null) 
                this.params.nhanVienTC_Selected = args.NHANVIEN_ID;
        },

        onChangeFile(e) {
            const files = e.target.files;
            if (!files.length)
                return;
            else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase()))
                return this.$toast.error("File excel không đúng định dạng!");
            
            const fileReader = new FileReader();
            fileReader.onload = async (ev) => {
                try {                    
                    const data = ev.target.result;                    
                    const workbook = xlsx.read(data, { type: "binary" });
                    const wsname = workbook.SheetNames[0];
                    const ws = xlsx.utils.sheet_to_json(workbook.Sheets[wsname]);                    
                    
                    let dsMaTT = '';
                    if (ws.length > 0) {
                        ws.forEach(item => {
                            if (item.hasOwnProperty("MA_TT"))                                
                                dsMaTT += item.MA_TT + ',';                            
                        })
                    }
                    this.params.maTT = dsMaTT.slice(0, -1);
                    this.$refs.maTT.focus();
                } catch (e) {
                    return this.$toast.error("Kiểm tra dữ liệu file đầu vào!");
                }
            };
            fileReader.readAsBinaryString(files[0]);            
        },                

        changeTab(value) {
            this.params.kieu = value;
        },

        async onSearch() {
            this.params.maTT = this.params.maTT.trim();                
            let rs = await this.getDSPhieuLacHuongPhanChiaKH(this.params.maTT);
            if (!rs)
                this.$toast.error('Không có dữ liệu, vui lòng thử lại.');
        },

        onFileExport() {
            if (this.params.kieu === 1) {
                if (this.dsPhieuLH.length > 0) {
                    this.dsExport = this.dsPhieuLH;
                    this.$bvModal.show("modal-xuat-file");
                } else 
                    return this.$toast.error('Chưa có dữ liệu để xuất file!');
            } else {
                if (this.dsPhanChiaKH.length > 0) {
                    this.dsExport = this.dsPhanChiaKH;
                    this.$bvModal.show("modal-xuat-file");
                } else 
                    return this.$toast.error('Chưa có dữ liệu để xuất file!');
            }            
        },

        onKyHoaDonChange: function(args) {      
            if(args.value === null)
                return this.$toast.error('Vui lòng chọn Kỳ hóa đơn');
            this.updateChuKyHoaDon(moment(args.value).format('YYYYMM'))      
        },

        async getDSNhanVienTinhCuoc() {
            try {
                this.loading(true);
                let apiParams = {                    
                    pageNo: 0,
                    pageSize: 100000
                };
                let response = await API.getDSNhanVienTinhCuoc(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' 
                    && response.data.data.data !== undefined 
                    && response.data.data.data.length > 0) {
                        this.params.nhanVienTC_Options = response.data.data.data;
                        this.params.nhanVienTC_Selected = this.params.nhanVienTC_Options[0].NHANVIEN_ID;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false)
            }
        },

        async getDSPhieuLacHuongPhanChiaKH(maTT) {
            try {
                this.loading(true);
                if (this.params.kieu === 1)
                    this.dsPhieuLH = [];
                else if (this.params.kieu === 2)
                    this.dsPhanChiaKH = [];
                else this.dsThayDoiKH = [];
                let apiParams = {                    
                    pMaTT: maTT,
                    pKieu: this.params.kieu,
                    pNVTCId: this.params.nhanVienTC_Checked ? this.params.nhanVienTC_Selected : null,                    
                    pLoaiPhieuId: this.params.loaiPhieu,
                    pageNo: 0,
                    pageSize: 100000
                };
                let response = await API.getDSPhieuLacHuongPhanChiaKH(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data.data !== undefined) {
                    if (this.params.kieu === 1)
                        this.dsPhieuLH = response.data.data.data;
                    else if (this.params.kieu === 2)
                        this.dsPhanChiaKH = response.data.data.data;
                    else this.dsThayDoiKH = response.data.data.data;                    
                }
                return response.data.data.data.length > 0;
            }
            catch (error) {
                console.log(error);
                return false;
            }
            finally {
                this.loading(false)
            }
        },

        async updateChuKyHoaDon(pKyHoaDon) {
            try {
                this.loading(true);
                let response = await CommonsAPI.getChuKyNoTheoKyCuoc(this.axios, pKyHoaDon);
                if (response.data.error_code === 'BSS-00000000'
                    && response.data.data !== undefined
                    && response.data.data.length > 0) {
                    this.params.chuKy = response.data.data;
                    this.params.chuKySelected = this.params.chuKy[0].CHUKY;
                } else {
                    this.params.chuKy = [];
                    this.params.chuKySelected = null;
                }
                this.params.kyCuoc = moment(this.params.kyHoaDonSelected).format('YYYYMM') + this.params.chuKySelected;
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false)
            }      
        }  
    }
}
</script>
