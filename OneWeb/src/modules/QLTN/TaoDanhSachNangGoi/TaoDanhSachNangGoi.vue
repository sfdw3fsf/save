<template src="./TaoDanhSachNangGoi.html"></template>
<style scoped src="./TaoDanhSachNangGoi.scss"></style>
<script>
import { mapActions, mapGetters } from 'vuex'
import breadcrumb from '@/components/breadcrumb'
import Calculator from '@/components/Calculator.vue'
import PopupDocFileKhoaMoThueBao from '../components/PopupDocFile/PopupDocFileKhoaMo.vue'
import KyCuoc from '@/components/KyCuoc'
import EventBus from '@/utils/eventBus'
import TraCuuAPI from '../api/NangHaGoiAPI.js'
import CommonsAPI from '../api/Commons.js'
import moment from 'moment'
import Vue from "vue"
import { MultiSelectPlugin } from '@syncfusion/ej2-vue-dropdowns'
import { MultiSelect, CheckBoxSelection } from '@syncfusion/ej2-dropdowns'
import { Query } from '@syncfusion/ej2-data'
import XuatFile from "../../CSKH/utils/XuatFile.vue"

MultiSelect.Inject(CheckBoxSelection);
Vue.use(MultiSelectPlugin);

export default {
    components: { breadcrumb, appKyCuoc: KyCuoc, Calculator, PopupDocFileKhoaMoThueBao, appModalXuatDuLieu: XuatFile },
    name: 'TaoDanhSachNangGoi',
    async created () {
        await this.initForm();
    },

    mounted() {
        this.setButton(-1);
    },

    destroyed () {
        EventBus.$off('chukycuocChange', this.chukycuocChangeHandler)
    },

    computed: {
        ...mapGetters('qltnCommon', ['danhSachDichVuVienThongGetter', 'danhsachDonViGetter'])
    },

    data() {
        return {
            thongtinKyHoaDon: {
                kyHoaDonSelected : null,
                chuKySelected : null
            },
            params: {
                dichVu_Options: [],
                dichVu_Selected: null,
                loaiHinh_Options: [],
                loaiHinh_Selected: [],
                loaiHinh_Checked: false,
                kieuNangGoi_Options: [
                    { id: 0, text: 'Nâng gói theo từng số máy' },
                    { id: 1, text: 'Nâng gói theo mã thanh toán' } 
                ],
                kieuNangGoi_Selected: 0,
                lanNangGoi: 0,
                tienMo: 0,
                tienMo_Checked: false,
                donVi_Options: [],
                donVi_Selected: null,
                donVi_Checked: false,
                kieuNo_Options: [
                    { id: 0, text: 'Nợ đầu kỳ' },
                    { id: 5, text: 'Nợ phát sinh' },
                    { id: 1, text: 'Tổng nợ' }
                ],
                kieuNo_Selected: 1,                
                isCalculatorShow: false,
            },
            nangGoi_grid: {
                items: [],
                checked: []
            },
            khongNangGoi_grid: {
                items: [],
                checked: []
            },            
            display_params: {
                processTime: '00:00:00',
                number: 0
            },            
            taoDSBtnEnable: false,
            xuatExcelBtnEnable: false,            
            selectFields: { text: 'text', value: 'id' }
        }
    },

    methods: {
        ...mapActions('qltnCommon', ['getDanhSachDichVuVienThong', 'getDanhSachDonVi']),

        async initForm() {
            EventBus.$on('chukycuocChange', this.chukycuocChangeHandler);
            if (this.danhSachDichVuVienThongGetter === undefined || this.danhSachDichVuVienThongGetter.length === 0) {
                await this.getDanhSachDichVuVienThong();                    
            }
            this.danhSachDichVuVienThongGetter.forEach(item => {
                //Chi ha goi cho dich vu Bang rong co dinh (dichVuId = 4):
                if (item.DICHVUVT_ID === 4) {
                    this.params.dichVu_Options.push({ id: item.DICHVUVT_ID, text: item.TEN_DVVT });
                }            
            });
            this.params.dichVu_Selected = 4;
            await this.getLoaiHinhTB(this.params.dichVu_Selected);

            if (this.params.danhsachDonViGetter !== undefined || this.danhsachDonViGetter.length === 0) {
                await this.getDanhSachDonVi();                    
            }
            this.danhsachDonViGetter.forEach(item => {
                this.params.donVi_Options.push({ id: item.DONVI_ID, text: item.TEN_DV })
            });
            this.params.donVi_Selected = this.params.donVi_Options[0].id;

            await this.getLanNangGoi();
        },

        chukycuocChangeHandler(e) {
            this.thongtinKyHoaDon.kyHoaDonSelected =moment(e.p_kyhoadon).format('YYYYMM') + '01'
            this.thongtinKyHoaDon.chuKySelected = e.p_chuky_no
        },

        openDialog() {
            this.$refs.dlgDocFileKhoaMo.openDialog();
        },

        selectFiltering(e) {
            if (e.text == '') e.updateData(this.params.loaiHinh_Options);
            else {
                var query = new Query().select(["id", "text"]);
                query = (e.text !== '') ? query.where('text', 'contains', e.text, true) : query;
                e.updateData(this.params.loaiHinh_Options, query);
            }
        },

        onLanNangInput(e) {
            this.params.lanNangGoi = Math.max(0, parseInt(e.target.value) || 0);
        },

        async onSearch() {
            this.clear();
            let beginTime = new Date();
            await this.getDSNangGoi();
            if (this.nangGoi_grid.items.length <= 0)
                this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
            else 
                this.setButton(1);

            let timeSpan = new Date() - beginTime;
            let seconds = timeSpan / 1000;
            let hours = parseInt( seconds / 3600 ); 
            seconds = seconds % 3600;            
            let minutes = parseInt( seconds / 60 );            
            seconds = Math.round(seconds % 60);
            this.display_params.processTime = hours.toString().padStart(2, '0') + ':' + minutes.toString().padStart(2, '0') + ':' + seconds.toString().padStart(2, '0');
            this.display_params.number = this.nangGoi_grid.items.length;
        },

        async onReadFile(items) {
            this.clear();
            if (items.length > 0) {
                let dsNangGoi = [];                
                items.forEach(item => {
                    let ds = {
                            dichVuVTId: this.params.dichVu_Selected,
                            maTB: item.MA_TB
                        };
                    dsNangGoi.push(ds);
                })
                await this.getDSNangGoiTheoFile(dsNangGoi);

                if (this.nangGoi_grid.items.length <= 0)
                    this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                else
                    this.setButton(1);
            }
        },

        onNangGoiChecked(items) {
            this.nangGoi_grid.checked = items;
        },

        onHaGoiChecked(items) {
            this.khongNangGoi_grid.checked = items;
        },

        onRight() {
            let arr = [];
            this.nangGoi_grid.checked.map((val) => {
                let idx = this.nangGoi_grid.items.findIndex((v) => v.MA_TB === val);
                if (idx >= 0) {
                    arr.push(this.nangGoi_grid.items[idx]);
                    this.nangGoi_grid.items = this.nangGoi_grid.items.filter((v) => v.MA_TB !== val);
                }
            });
            this.khongNangGoi_grid.items = this.khongNangGoi_grid.items.concat(arr);
            this.nangGoi_grid.checked = [];
        },

        onDoubleRight() {            
            this.khongNangGoi_grid.items = this.khongNangGoi_grid.items.concat(this.nangGoi_grid.items);
            this.nangGoi_grid.items = [];
            this.nangGoi_grid.checked = [];
        },

        onLeft() {           
            let arr = [];
            this.khongNangGoi_grid.checked.map((val) => {
                let idx = this.khongNangGoi_grid.items.findIndex((v) => v.MA_TB === val);
                if (idx >= 0) {
                    arr.push(this.khongNangGoi_grid.items[idx]);
                    this.khongNangGoi_grid.items = this.khongNangGoi_grid.items.filter((v) => v.MA_TB !== val);
                }
            });
            this.nangGoi_grid.items = this.nangGoi_grid.items.concat(arr);
            this.khongNangGoi_grid.checked = [];
        },

        onDoubleLeft() {           
            this.nangGoi_grid.items = this.nangGoi_grid.items.concat(this.khongNangGoi_grid.items);
            this.khongNangGoi_grid.items = [];
            this.khongNangGoi_grid.checked = [];
        },

        clear() {
            this.nangGoi_grid.items = [];
            this.khongNangGoi_grid.items = [];
            this.display_params.processTime = '00:00:00';
            this.display_params.number = 0;
        },

        setButton(kieu) {
            this.taoDSBtnEnable = false;
            this.xuatExcelBtnEnable = false;
            
            switch(kieu) {
                case 0: //Bat dau                    
                    this.clear();
                    break;
                case 1: //Tao xong DS nhac no                    
                    this.taoDSBtnEnable = true;
                    this.xuatExcelBtnEnable = true;
                    break;
                default: //-1                    
                    break;
            }
        },

        async onUpdate() {
            if (this.nangGoi_grid.items.length <= 0)
                return this.$toast.error('Chưa có thuê bao trên lưới!');

            let dsNangGoi = [];
            this.nangGoi_grid.items.forEach(item => {
                let ds = {
                    dichVuVTId: item.DICHVUVT_ID,
                    maTB: item.MA_TB
                };
                dsNangGoi.push(ds);
            });

            let result = await this.updateChuyenDSNangTocDoSangTDTD(dsNangGoi);
            if (result)
                this.$toast.success('Tạo danh sách nâng gói thành công!');
            else
                this.$toast.error('Tạo danh sách nâng gói không thành công!');
            
            this.setButton(0);
            await this.getLanNangGoi();
        },

        onSync() {
            this.$router.push({ 
                name: 'HoanThienThayDoiTocDo', 
                params: {
                    tag: '1+8+4',
                    //Quy trình: 143 - Hạ tốc độ duy trì
                    quyTrinh_Id: 143,
                    //Kiểu lắp đặt: 14 - Hạ tốc độ duy trì; 15 - Nâng tốc độ
                    kieuLD_Id: 15,
                    //Trạng thái hoạt động: 1 - Mới; 4 - Đang thi công; 6 - Hoàn thành
                    TTHD_Id: 1,
                    //Dịch vụ: 4 - ADSL
                    dsDichVu_Id: '4',
                    //Loại hợp đồng: 8 - Thay đổi tốc độ ADSL
                    dsLoaiHD_Id: '8'
                }
            });
        },

        onFileExport() {
            if (this.nangGoi_grid.items.length <= 0)
                this.$toast.error('Không có dữ liệu thuê bao nâng gói để thực hiện xuất file!');            
            else
                this.$bvModal.show("modal-xuat-file");
        },

        async getLoaiHinhTB(id) {
            try {
                this.loading(true);
                this.params.loaiHinh_Options = [];
                let response = await CommonsAPI.getDanhSachLoaiHinhThueBao(this.axios, id);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    response.data.data.forEach(item => {
                        //Chỉ hiển thị loại hình 11-Mega, 58-Fiber
                        if (item.LOAITB_ID === 11 || item.LOAITB_ID === 58) {
                            this.params.loaiHinh_Options.push({ id: item.LOAITB_ID, text: item.TEN_LOAIHINH });
                        }
                    });                    
                }
                this.params.loaiHinh_Options = [...this.params.loaiHinh_Options];
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getLanNangGoi() {
            try {
                this.loading(true);                
                this.params.lanNangGoi = 0;
                let apiParams = {
                    dichVuVTId: this.params.dichVu_Selected,
                    ngayNG: moment(new Date()).format('DD/MM/YYYY')
                };                
                let response = await TraCuuAPI.getLanNangGoi(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {                    
                    this.params.lanNangGoi = response.data.data;                    
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDSNangGoi() {
            try {
                this.loading(true);
                this.nangGoi_grid.items = [];
                let apiParams = {
                    dichVuVTId: this.params.dichVu_Selected,
                    donViId: this.params.donVi_Checked ? this.params.donVi_Selected : 0,
                    kieu: this.params.tienMo_Checked ? 1 : 2,
                    kieuHG: this.params.kieuNo_Selected,
                    kieuNG: this.params.kieuNangGoi_Selected,
                    kyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
                    lanNG: this.params.lanNangGoi,
                    loaiTBId: this.params.loaiHinh_Checked ? this.params.loaiHinh_Selected : [11,58,79],
                    tienGHM: this.params.tienMo_Checked ? this.params.tienMo : 0,
                };
                let response = await TraCuuAPI.getDSNangGoi(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                   this.nangGoi_grid.items = response.data.data;                   
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        /* ds: [{ "dichVuVTId": 0, "maTB": "string" }] */
        async getDSNangGoiTheoFile(ds) {
            try {
                this.loading(true);
                this.nangGoi_grid.items = [];
                let apiBody = {
                    dichVuVTId: this.params.dichVu_Selected,
                    donViId: this.params.donVi_Selected,
                    kyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
                    loaiTBId: null,
                    lanNG: this.params.lanNangGoi,
                    dsNangTocDo: ds
                };
                let response = await TraCuuAPI.getDSNangGoiTheoFile(this.axios, apiBody);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                   this.nangGoi_grid.items = response.data.data;
                }
            }
            catch (error) {
                console.log(error);
                return false;
            }
            finally {
                this.loading(false);
            }
        },

        /* dsNangTocDo: [{ dichVuVTId: Number, maTB: String }] */
        async updateChuyenDSNangTocDoSangTDTD(ds) {
            try {
                this.loading(true);                
                let apiBody = {
                    dichVuVTId: this.params.dichVu_Selected,
                    donViId: this.params.donVi_Checked ? this.params.donVi_Selected : 0,
                    loaiTBTkId: this.params.loaiHinh_Checked ? this.params.loaiHinh_Selected : [11,58,79],
                    nhanVienId: this.$auth.getNhanVienID() !== -1 ? this.$auth.getNhanVienID() : 0,
                    lanNG: this.params.lanNangGoi,
                    cachNG: this.params.kieuNangGoi_Selected,
                    dsNangTocDo: ds,
                };
                let response = await TraCuuAPI.updateChuyenDSNangTocDoSangTDTD(this.axios, apiBody);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                   return true;
                } else {
                    return false;
                }
            }
            catch (error) {
                console.log(error);
                return false;
            }
            finally {
                this.loading(false);
            }
        }
    },

    watch: {        
    }
}
</script>