<template src="./index.template.html"></template>
<style scoped src="./NhacNoThanhToan.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import KyCuoc from '@/components/KyCuoc'
import EventBus from '@/utils/eventBus'
import NhacNoAPI from '../api/NhacNoThanhToanAPI'
import Calculator from '@/components/Calculator.vue'
import { mapActions, mapGetters } from 'vuex'
import moment from 'moment'
import Vue from 'vue'
import { MultiSelectPlugin } from '@syncfusion/ej2-vue-dropdowns'
import { MultiSelect, CheckBoxSelection } from '@syncfusion/ej2-dropdowns'
import XuatFile from "../../CSKH/utils/XuatFile.vue"
import { Query } from '@syncfusion/ej2-data'

MultiSelect.Inject(CheckBoxSelection);
Vue.use(MultiSelectPlugin);

export default {
    components: { breadcrumb, appKyCuoc: KyCuoc, Calculator, appModalXuatDuLieu: XuatFile },    

    async created () {
        EventBus.$on('onDeleteRow', this.onDeleteRowHandler);
        EventBus.$on('onRefreshRow', this.onRefreshRowHandler);
        await this.initForm();
    },

    destroyed () {      
        EventBus.$off('onDeleteRow', this.onDeleteRowHandler);      
        EventBus.$off('onRefreshRow', this.onRefreshRowHandler);
    },

    computed: {
        ...mapGetters('qltnCommon', [
            'danhsachDonViGetter', 
            'danhSachKHLonGetter',
            'danhSachLoaiKHGetter', 
            'danhSachLoaiPhieuGetter']),
        LTAggregatesColumns () {
            return [{ field: 'TONGTIEN', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate }]
        },
        TTAggregatesColumns () { 
            return [{ field: 'SOTIEN', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate }]
        },
    },

    data() {
        return {
            thongtinKyHoaDon: {
                kyHoaDonSelected : null,
                chuKySelected : null
            },
            params: {
                donVi_Options: [],
                donVi_Selected: null,
                hinhThuc_Selected: 1,
                tuTien: 0,
                tuTienCalculator: false,
                denTien: 0,
                denTienCalculator: false,
                denTien_Checked: false,
                ngayHetHan: new Date(),
                today: new Date(),
                locMayDaNhacNo: true,
                loaiPhieu_Options: [],
                loaiPhieu_Selected: null,
                loaiKH_Options: [],
                loaiKH_Selected: null,
                loaiKH_Checked: false,
                KHLon_Options: [],
                KHLon_Selected: [],
                KHLon_Checked: false                
            },
            display_params: {
                thoiGianTao: null,
                soLuongTao: null
            },
            thanhToan_grid: {
                items: [],
                checked: [],
                key: 1,
                pageInfo: {
                    page: 0,
                    maxSize: 10,
                    totalElement: 0,
                    totalPages: 0,
                    currentRow: 0,
                    sort: null,
                    propertiesSort: null
                }
            },
            lanTao_grid: {
                items: [],
                selected: {},
                key: 1
            },
            exportExcel: {      
                sessionId: -1,
                interval: null
            },
            excelExport: {
                fields: [
                    {
                        fieldName: 'MOBILE',
                        headerText: 'MOBILE',
                        allowFiltering: true
                    },
                    {
                        fieldName: 'MESSAGE',
                        headerText: 'MOBILE',
                        allowFiltering: true
                    }
                ],
                items: []
            },            
            selectFields: { text: 'text', value: 'id' },
            sumTemplate: function() {
                return { template: Vue.component('sumTemplate', {
                        template: `<input type="text" class="form-control tright" :value="data.Sum" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
                        data: function () {return {data: {data: {}}};}
                    })
                }
            },                        
            deleteRefreshTemplate: function () {
                return { template : Vue.component('columnTemplate', {
                    template: `<div><button class="btn btn-danger lh14 pad3" @click="onDeleteRow" title="Xóa lần tạo">
                                    <span class="-ap icon-close"></span>
                                </button>
                                <button class="btn btn-primary lh14 pad3" @click="onRefreshRow" title="Danh sách lần tạo">
                                    <span class="btn-refresh nc-icon-outline arrows-1_refresh-68"></span>
                                </button></div>`,                        
                    data () { 
                        return { data: {} }; 
                    },
                    methods: {
                        onDeleteRow() {                  
                            EventBus.$emit('onDeleteRow', this.data);
                        },
                        onRefreshRow() {
                            EventBus.$emit('onRefreshRow', this.data);
                        }
                    }
                    })
                }
            },
        }
    },

    methods: {
        ...mapActions('qltnCommon', [
            'getDanhSachDonVi', 
            'getDanhSachKHLon', 
            'getDanhSachLoaiKhachHang', 
            'getDanhSachLoaiPhieu']),
        
        async initForm() {
            EventBus.$on('chukycuocChange', this.chukycuocChangeHandler);

            if (this.params.danhsachDonViGetter == null || this.danhsachDonViGetter.length === 0)
                await this.getDanhSachDonVi();            
            this.danhsachDonViGetter.forEach(item => {
                this.params.donVi_Options.push({ id: item.DONVI_ID, text: item.TEN_DV })
            });
            if (this.params.donVi_Options.length > 0) {
                this.params.donVi_Options.unshift({ id: 0, text: 'Tất cả' });
                this.params.donVi_Selected = this.params.donVi_Options[0].id;
            }

            if (this.params.danhSachLoaiPhieuGetter == null || this.danhSachLoaiPhieuGetter.length === 0)
                await this.getDanhSachLoaiPhieu();            
            this.danhSachLoaiPhieuGetter.forEach(item => {
                this.params.loaiPhieu_Options.push({ id: item.LOAIPHIEU_ID, text: item.LOAIPHIEU })
            });
            if (this.params.loaiPhieu_Options.length > 0)
                this.params.loaiPhieu_Selected = this.params.loaiPhieu_Options[0].id;            

            if (this.params.danhSachKHLonGetter == null || this.danhSachKHLonGetter.length === 0)
                await this.getDanhSachKHLon();            
            this.danhSachKHLonGetter.forEach(item => {
                this.params.KHLon_Options.push({ id: item.KHLON_ID, text: item.TEN_LKHL })
            });

            if (this.params.danhSachLoaiKHGetter == null || this.danhSachLoaiKHGetter.length === 0)
                await this.getDanhSachLoaiKhachHang();            
            this.danhSachLoaiKHGetter.forEach(item => {
                this.params.loaiKH_Options.push({ id: item.LOAIKH_ID, text: item.TEN_LOAIKH })
            });
            if (this.params.loaiKH_Options.length > 0)
                this.params.loaiKH_Selected = this.params.loaiKH_Options[0].id;            
        },

        async chukycuocChangeHandler(e) {
            this.thongtinKyHoaDon.kyHoaDonSelected =moment(e.p_kyhoadon).format('YYYYMM') + '01';
            this.thongtinKyHoaDon.chuKySelected = e.p_chuky_no;            
            await this.getDSLanTaoNhacNoThanhToan(this.thongtinKyHoaDon.kyHoaDonSelected);
        },
        
        selectFiltering(e) {
            if (e.text == '') e.updateData(this.params.KHLon_Options);
            else {
                var query = new Query().select(["id", "text"]);
                query = (e.text !== '') ? query.where('text', 'contains', e.text, true) : query;
                e.updateData(this.params.KHLon_Options, query);
            }
        },                

        onLanTaoSelected(e) {
            this.lanTao_grid.selected = e;            
        },

        onThanhToanChecked(e) {
            this.thanhToan_grid.checked = e;
        },
        
        async thanhToanGridpageChangeHandler(e) {
            this.thanhToan_grid.pageInfo.page = e.pageIndex;
            this.thanhToan_grid.pageInfo.maxSize = e.pageSize;
            await this.getDSNhacNoThanhToan(this.lanTao_grid.selected.LANTAO_TT_ID);
        },

        async onCreate() {
            if (this.onValidate()) {
                let confirmMessage = !this.params.denTien_Checked ? `Bạn thật sự muốn tạo dữ liệu nhắc nợ với số tiền nợ >= ${this.params.tuTien}?`
                    : `Bạn thật sự muốn tạo dữ liệu nhắc nợ với số tiền nợ >= ${this.params.tuTien} và <= ${this.params.denTien}?`;
                
                await this.showConfirmPopup(confirmMessage, async () => {
                    let beginTime = new Date();
                    let result = await this.updateDSNhacNoThanhToan();
                    if (result) {                            
                        await this.getDSLanTaoNhacNoThanhToan(this.thongtinKyHoaDon.kyHoaDonSelected);
                        if (this.lanTao_grid.items.length <= 0) {
                            this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                        } else {
                            let timeSpan = new Date() - beginTime;
                            let seconds = timeSpan / 1000;
                            let hours = parseInt( seconds / 3600 ); 
                            seconds = seconds % 3600;            
                            let minutes = parseInt( seconds / 60 );            
                            seconds = Math.round(seconds % 60);
                            this.display_params.thoiGianTao = hours.toString().padStart(2, '0') + ':' + minutes.toString().padStart(2, '0') + ':' + seconds.toString().padStart(2, '0');
                            this.display_params.soLuongTao = this.lanTao_grid.items[0].SOLUONG;
                        }
                    }
                });
            }
        },

        onValidate() {
            let errors = [];
            if (this.params.denTien_Checked && this.params.tuTien > this.params.denTien)
                errors.push('Giá trị "Đến tiền" phải lớn hơn hoặc bằng giá trị "Từ số tiền"!');
            if (this.params.ngayHetHan === null)
                errors.push('Giá trị "Ngày hết hạn" không hợp lệ!');
            
            if (errors.length > 0) {
                this.$toast.error(errors.join('\r\n'));
                return false;
            }
            return true;
        },

        async onSearch() {
            if (this.lanTao_grid.items.length <= 0)
                return this.$toast.error('Chưa có lần tạo để lấy danh sách thanh toán!');

            this.resetTTPaging();
            await this.getDSNhacNoThanhToan(this.lanTao_grid.selected.LANTAO_TT_ID);
            if (this.thanhToan_grid.items.length <= 0) 
                return this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
        },

        async onChuyenNhac() {
            if (this.lanTao_grid.items.length <= 0)
                return this.$toast.error('Chưa có dữ liệu để chuyển nhắc nợ thanh toán!');
            
            await this.showConfirmPopup(`Bạn có chắc chắn muốn chuyển danh sách sang đã nhắc nợ thành công?`, async () => {
                let result = await this.updateChuyenDSNhacNoTT(this.lanTao_grid.selected.LANTAO_TT_ID);
                if (result)
                    this.$toast.success('Chuyển danh sách sang đã nhắc nợ thành công!');

                this.clearDisplayData();
                await this.getDSLanTaoNhacNoThanhToan(this.thongtinKyHoaDon.kyHoaDonSelected);
            });
        },

        async onDeleteTT() {
            if (this.thanhToan_grid.items.length <= 0)
                return this.$toast.error('Chưa có dữ liệu để xóa!');
           
            if (this.thanhToan_grid.checked.length <= 0)
                return this.$toast.error('Chưa chọn dữ liệu để xóa!');            

            await this.showConfirmPopup(`Bạn thật sự muốn xóa thanh toán đã chọn khỏi dữ liệu được tạo không?`, async () => {
                let result = await this.deleteThanhToan(this.thanhToan_grid.checked, this.lanTao_grid.selected.LANTAO_TT_ID);
                if (result)
                    this.$toast.success('Đã xóa thanh toán thành công!');

                let currentLanTaoId = this.lanTao_grid.selected.LANTAO_TT_ID;
                await this.getDSLanTaoNhacNoThanhToan(this.thongtinKyHoaDon.kyHoaDonSelected);
                await this.getDSNhacNoThanhToan(currentLanTaoId);
                let idx = this.lanTao_grid.items.findIndex(e => e.LANTAO_TT_ID === currentLanTaoId);
                if (idx !== -1)
                    this.$refs.lanTaoGrid.setCurrentSelectedRow(idx);
            });
        },

        async onTaoFile() {
            if (this.lanTao_grid.items.length <= 0)
                this.$toast.error('Chưa có lần tạo để thực hiện xuất file!');
            else if (this.lanTao_grid.selected === {})
                this.$toast.error('Chưa chọn lần tạo để thực hiện xuất file!');
            else {
                await this.getDSSDTNoiDungNhacNo(this.lanTao_grid.selected.LANTAO_TT_ID);
                this.$bvModal.show("modal-xuat-file");
            }
        },

        async onExcelExport() {
            if (this.thanhToan_grid.items.length <= 0)
                this.$toast.error('Chưa có dữ liệu để xuất excel!');
            else {
                this.exportExcel.sessionId = await this.getXuatFileXlsx(this.lanTao_grid.selected.LANTAO_TT_ID);
                if (this.exportExcel.sessionId && this.exportExcel.sessionId !== -1) {
                    this.loading(true);
                    //0 - 4999 rows: 5s
                    //5000 - 49999 rows: 15s
                    //>= 50000 rows: 30s
                    let interval = this.lanTao_grid.selected.SOLUONG > 5000 ? (this.lanTao_grid.selected.SOLUONG > 50000 ? 30000 : 15000) : 5000;
                    this.exportExcel.interval = setInterval(async () => {
                        await this.axios({
                        url: "https://api-onebss-dev.vnptit3.vn/web-thuno/api/thu-no/session/" + this.exportExcel.sessionId,
                        method: "GET",
                        }).then((response) => {
                            if (response.data.data.status === 'COMPLETED') {
                            clearInterval(this.exportExcel.interval);
                            var fURL = document.createElement("a");
                            fURL.href = response.data.data.description;
                            fURL.setAttribute("download", "DanhSachNhacNoThanhToan.xlsx");
                            document.body.appendChild(fURL);
                            fURL.click();
                            this.loading(false);
                            }
                        });
                    }, interval);
                }
            }            
        },

        async onDeleteRowHandler(item) {
            await this.showConfirmPopup(`Bạn có thật sự muốn xóa dữ liệu lần tạo ${item.NGAY_TAO}?`, async () => {
                if (item.NGUOI_CN !== this.$auth.getUserName())
                    return this.$toast.error(`Lần tạo ${item.LANTAO_TT_ID} do user ${item.NGUOI_CN}. Bạn không được phép xóa lần tạo này!`);
                            
                if (item.DANHAC !== '' && Number(item.DANHAC) !== 0)
                    return this.$toast.error('Lần tạo này đã có phiếu được nhắc nợ. Bạn không được phép xóa!');
                        
                let result = await this.deleteLanTao(item.LANTAO_TT_ID);
                if (result)
                    this.$toast.success('Xóa lần tạo thành công!');
                        
                await this.getDSLanTaoNhacNoThanhToan(this.thongtinKyHoaDon.kyHoaDonSelected);
            });
        },

        async onRefreshRowHandler() {            
            await this.getDSLanTaoNhacNoThanhToan(this.thongtinKyHoaDon.kyHoaDonSelected);
        },

        clearDisplayData() {
            this.display_params.thoiGianTao = '00:00:00';
            this.display_params.soLuongTao = 0;
            this.resetTTPaging();
        },

        resetTTPaging() {
            this.thanhToan_grid.items = [];
            this.thanhToan_grid.pageInfo = {
                page: 0,
                maxSize: 10,
                totalElement: 0,
                totalPages: 0,
                currentRow: 0,
                sort: null,
                propertiesSort: null
            }
        },
        
        async showConfirmPopup(message, fncCallback) {
            try {                
                await this.$bvModal.msgBoxConfirm(message, {
                "title": 'Thông báo',
                "size": 'lg',
                "buttonSize": 'lg',
                "okVariant": 'primary',
                "okTitle": 'Đồng ý',
                "cancelTitle": 'Hủy bỏ',
                "hideHeaderClose": false,
                "centered": true,
                "modal-class": ["f18","text-center"],
                })
                .then(async(value) => {
                    if (value){
                        fncCallback();
                    }
                })
                .catch(err => {
                    // An error occurred
                    console.log(err);
                })
            } catch (error) {
                    this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!");
            }
        },

        async updateDSNhacNoThanhToan() {
            try {
                this.loading(true);                
                let apiBody = {
                    denTien: this.params.denTien_Checked ? this.params.denTien : 0,
                    donViId: this.params.donVi_Selected,
                    khachHangLonId: this.params.KHLon_Checked ? this.params.KHLon_Selected : [],
                    kieuNhac: this.params.hinhThuc_Selected,
                    kyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
                    loaiKHId: this.params.loaiKH_Checked ? this.params.loaiKH_Selected : null,
                    loaiPhieuId: this.params.loaiPhieu_Selected,
                    locNhac: this.params.locMayDaNhacNo ? 1 : 0,
                    ngayHetHan: moment(this.params.ngayHetHan).format('DD/MM/YYYY'),
                    tuTien: this.params.tuTien
                };
                let response = await NhacNoAPI.updateDSNhacNoThanhToan(this.axios, apiBody);
                if (response.data.error_code === 'BSS-00000000') {
                    return true;
                } else {                    
                    this.$toast.error(response.data.message_detail);
                    return false;
                }
            }
            catch (error) {                
                this.$toast.error(error.data.message_detail);
                return false;
            }
            finally {
                this.loading(false);
            }
        },

        async getDSLanTaoNhacNoThanhToan(kyCuoc) {
            try {
                this.loading(true);
                this.resetTTPaging();
                this.lanTao_grid.key++;
                this.lanTao_grid.selected = {};
                this.lanTao_grid.items = [];
                let apiParams = {
                    size: 100000,
                    page: 0
                };
                let response = await NhacNoAPI.getDSLanTaoNhacNoThanhToan(this.axios, kyCuoc, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.data.length > 0) {
                    this.lanTao_grid.items = response.data.data.data;
                }
            }
            catch (error) {                
                this.$toast.error(error.data.message_detail);
            }
            finally {
                this.loading(false);
            }
        },

        async getDSNhacNoThanhToan(lanTaoId) {
            try {
                this.loading(true);
                this.thanhToan_grid.key++;
                // this.thanhToan_grid.items = [];
                let apiParams = {
                    lanTaoTTId: lanTaoId,
                    pageNum: this.thanhToan_grid.pageInfo.page + 1,
                    pageSize: this.thanhToan_grid.pageInfo.maxSize,
                    totalRow: 1
                };
                let response = await NhacNoAPI.getDSNhacNoThanhToan(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data != null && response.data.data.data.length > 0) {                    
                    this.thanhToan_grid.items = response.data.data.data;
                    this.thanhToan_grid.pageInfo = {
                        page : response.data.data.page,
                        maxSize: response.data.data.maxSize,
                        totalElement: response.data.data.totalElement,
                        totalPages: response.data.data.totalPages,
                        sort: response.data.data.sort,
                        propertiesSort: response.data.data.propertiesSort,
                        currentRow: 0
                    }
                } else this.resetTTPaging();
            }
            catch (error) {                
                this.$toast.error(error.data.message_detail);
                this.resetTTPaging();
            }
            finally {
                this.loading(false);
            }
        },

        async updateChuyenDSNhacNoTT(lanTao_Id) {
            try {
                this.loading(true);
                let response = await NhacNoAPI.updateChuyenDSNhacNoTT(this.axios, lanTao_Id);
                if (response.data.error_code === 'BSS-00000000') {
                   return true;
                } else {
                    this.$toast.error(response.data.message_detail);
                    return false;
                }
            }
            catch (error) {
                this.$toast.error(error.data.message_detail);
                return false;
            }
            finally {
                this.loading(false);
            }
        },

        /* dsThanhToanId: [ 0 ] */
        async deleteThanhToan(dsThanhToanId, lanTaoId) {
            try {
                this.loading(true);
                let apiBody = {
                    dsThanhToanId: dsThanhToanId,
                    lanTaoNhacNoTT: lanTaoId
                };
                
                let response = await NhacNoAPI.deleteThanhToan(this.axios, apiBody);
                if (response.data.error_code === 'BSS-00000000') {
                   return true;
                } else {
                    this.$toast.error(response.data.message_detail);
                    return false;
                }
            }
            catch (error) {
                this.$toast.error(error.data.message_detail);
                return false;
            }
            finally {
                this.loading(false);
            }
        },

        /* lanTaoId: Number */
        async deleteLanTao(lanTaoId) {
            try {
                this.loading(true);                
                let response = await NhacNoAPI.deleteLanTao(this.axios, lanTaoId);
                if (response.data.error_code === 'BSS-00000000') {                    
                    return true;
                } else {
                    this.$toast.error(response.data.message_detail);
                    return false;
                }
            }
            catch (error) {
                this.$toast.error(error.data.message_detail);
                return false;
            }
            finally {
                this.loading(false);
            }
        },

        async getDSSDTNoiDungNhacNo(lanTaoId) {
            try {
                this.loading(true);
                this.excelExport.items = [];                
                let response = await NhacNoAPI.getDSSDTNoiDungNhacNo(this.axios, lanTaoId);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {                    
                    this.excelExport.items = response.data.data;                                   
                }
            }
            catch (error) {
                this.$toast.error(error.data.message_detail);
            }
            finally {
                this.loading(false);
            }
        },

        async getXuatFileXlsx(lanTaoId) {
            try {
                this.loading(true);
                let response = await NhacNoAPI.getXuatFileXlsx(this.axios, lanTaoId);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                return response.data.data.sessionId;
                } else return -1;
            }
            catch (error) {
                if (error.data.message_detail)
                this.$toast.error(error.data.message_detail);
                return -1;
            }
            finally {
                this.loading(false);
            }        
        }
    }
}
</script>
