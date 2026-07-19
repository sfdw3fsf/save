<template src="./index.template.html"></template>
<style scoped src="./NhacNoThanhToan.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import KyCuoc from '@/components/KyCuoc'
import EventBus from '@/utils/eventBus'
import NhacNoAPI from '../api/NhacNoThanhToanAPI'
import Calculator from '@/components/Calculator.vue'
import SfsGrid from '../components/RTable/SfsGrid.vue'
import { mapActions, mapGetters } from 'vuex'
import moment from 'moment'
import xlsx from "xlsx";
import Vue from "vue"
import { MultiSelectPlugin } from '@syncfusion/ej2-vue-dropdowns'
import { MultiSelect, CheckBoxSelection } from '@syncfusion/ej2-dropdowns'
import { Query } from '@syncfusion/ej2-data';

MultiSelect.Inject(CheckBoxSelection);
Vue.use(MultiSelectPlugin);

export default {
    components: { breadcrumb, appKyCuoc: KyCuoc, Calculator, SfsGrid },    

    async created () {
        EventBus.$on('chukycuocChange', this.chukycuocChangeHandler);

        if (this.params.danhsachDonViGetter !== undefined || this.danhsachDonViGetter.length === 0) {
            await this.getDanhSachDonVi();                    
        }
        this.danhsachDonViGetter.forEach(item => {
            this.params.donVi_Options.push({ id: item.DONVI_ID, text: item.TEN_DV })
        });
        if (this.params.donVi_Options.length > 0) {
            this.params.donVi_Options.unshift({ id: 0, text: 'Tất cả' });
            this.params.donVi_Selected = this.params.donVi_Options[0].id;
        }

        if (this.params.danhSachLoaiPhieuGetter !== undefined || this.danhSachLoaiPhieuGetter.length === 0) {
            await this.getDanhSachLoaiPhieu();                    
        }
        this.danhSachLoaiPhieuGetter.forEach(item => {
            this.params.loaiPhieu_Options.push({ id: item.LOAIPHIEU_ID, text: item.LOAIPHIEU })
        });
        if (this.params.loaiPhieu_Options.length > 0) {
            this.params.loaiPhieu_Selected = this.params.loaiPhieu_Options[0].id;
        }

        if (this.params.danhSachKHLonGetter !== undefined || this.danhSachKHLonGetter.length === 0) {
            await this.getDanhSachKHLon();
        }
        this.danhSachKHLonGetter.forEach(item => {
            this.params.KHLon_Options.push({ id: item.KHLON_ID, text: item.TEN_LKHL })
        });

        if (this.params.danhSachLoaiKHGetter !== undefined || this.danhSachLoaiKHGetter.length === 0) {
            await this.getDanhSachLoaiKhachHang();                    
        }
        this.danhSachLoaiKHGetter.forEach(item => {
            this.params.loaiKH_Options.push({ id: item.LOAIKH_ID, text: item.TEN_LOAIKH })
        });
        if (this.params.loaiKH_Options.length > 0) {
            this.params.loaiKH_Selected = this.params.loaiKH_Options[0].id;
        }
    },

    async mounted () {
        await this.getDSLanTaoNhacNoThanhToan('01' + moment(new Date()).format('MMYYYY'));
    },

    computed: {
        ...mapGetters('qltnCommon', ['danhsachDonViGetter', 'danhSachKHLonGetter',
        'danhSachLoaiKHGetter', 'danhSachLoaiPhieuGetter']),
    },

    data() {
        return {
            header: {
                title: 'TẠO DỮ LIỆU NHẮC NỢ THEO THANH TOÁN',                
                list: [
                    { name: 'Xử lý thuê bao', link: { name: 'Ui.cards' } },
                    { name: 'Nhắc nợ thanh toán', link: { name: 'Ui.buttons' } },
                ]
            },
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

            thanhToan_Items: [],            
            lanTao_Items: [],
            lanTao_Selected: {},            

            selectFields: { text: 'text', value: 'id' },
            TTFields: [                               
                {
                    key: 'MA_TT',
                    label: 'Mã TT',
                    isPrimaryKey: true,
                    width: 150
                },
                {
                    key: 'MATB_DD',
                    label: 'Mã đại diện',
                    width: 150
                },
                {
                    key: 'SO_DT',
                    label: 'Số điện thoại',
                    width: 150
                },
                {
                    key: 'NOIDUNG',
                    label: 'Nội dung',
                    width: 200
                },
                {
                    key: 'TEN_TT',
                    label: 'Tên thanh toán',
                    width: 200
                },
                {
                    key: 'SOTIEN',
                    label: 'Số tiền',
                    footer: 'Sum',
                    tdClass: 'Right',
                    isCurrency: true,
                    width: 150
                },
                {
                    key: 'MA_NVTC',
                    label: 'Mã NVTC',
                    width: 150                    
                },
                {
                    key: 'MA_TUYEN',
                    label: 'Mã tuyến thu',
                    width: 150
                }
            ],
            TTPageInfo: {
                page: 0,
                maxSize: 10,
                totalElement: 0,
                totalPages: 0,
                currentRow: 0,
                sort: null,
                propertiesSort: null
            },
            LTPageInfo: {
                page: 0,
                maxSize: 10,
                totalElement: 0,
                totalPages: 0,
                currentRow: 0,
                sort: null,
                propertiesSort: null
            },
            LTFields: [                
                {
                    key: 'LANTAO_TT_ID',
                    label: 'ID',
                    isPrimaryKey: true,
                    width: 150
                },
                {
                    key: 'NGAY_TAO',
                    label: 'Ngày tạo',
                    width: 170,
                    tdClass: 'Right'
                },
                {
                    key: 'NGUOI_CN',
                    label: 'Người tạo',
                    width: 200
                },
                {
                    key: 'SOLUONG',
                    label: 'Số lượng',
                    width: 150,
                    tdClass: 'Right'
                },
                {
                    key: 'DANHAC',
                    label: 'Đã nhắc',
                    width: 150,
                    tdClass: 'Right'
                },
                {
                    key: 'TONGTIEN',
                    label: 'Tổng tiền',
                    footer: 'Sum',                    
                    tdClass: 'Right',
                    isCurrency: true,
                    width: 150
                },
                {
                    key: 'TU_TIEN',
                    label: 'Từ tiền',
                    width: 150,
                    tdClass: 'Right',
                    isCurrency: true
                },
                {
                    key: 'DEN_TIEN',
                    label: 'Đến tiền',
                    width: 150,
                    tdClass: 'Right',
                    isCurrency: true
                }
            ]
        }
    },

    methods: {
        ...mapActions('qltnCommon', ['getDanhSachDonVi', 'getDanhSachKHLon', 
        'getDanhSachLoaiKhachHang', 'getDanhSachLoaiPhieu']),        

        async chukycuocChangeHandler(e) {
            this.thongtinKyHoaDon.kyHoaDonSelected =moment(e.p_kyhoadon).format('YYYYMM') + '01';
            this.thongtinKyHoaDon.chuKySelected = e.p_chuky_no;

            this.resetPaging(0);
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

        async onTTGridPageChange(e) {
            this.TTPageInfo.page = e.currentPage - 1;
            this.TTPageInfo.maxSize = e.maxSize;
            await this.getDSNhacNoThanhToan(this.lanTao_Selected.LANTAO_TT_ID);
        },

        onThanhToanSelected(e) {
            try {
                let index = parseInt(e);                
                this.TTPageInfo.currentRow = index;
            } catch (err) {}
        },

        onLanTaoSelected(e) {            
            try {                
                let index = parseInt(e);
                this.lanTao_Selected = this.lanTao_Items[index-1];                
                this.LTPageInfo.currentRow = index;
            } catch (err) {}
        },

        async onLTGridPageChange(e) {
            this.LTPageInfo.page = e.currentPage - 1;
            this.LTPageInfo.maxSize = e.maxSize;
            await this.getDSLanTaoNhacNoThanhToan(this.thongtinKyHoaDon.kyHoaDonSelected);
        },

        async onCreate() {
            if (this.params.ngayHetHan === null)
                return this.$toast.error('Vui lòng chọn ngày hết hạn!');            

            if (this.params.denTien_Checked && this.params.tuTien > this.params.denTien)
                return this.$toast.error('Vui lòng nhập giá trị đến tiền phải lớn hơn hoặc bằng giá trị từ số tiền!');

            try {
                let confirmMessage = !this.params.denTien_Checked ? 'Bạn thật sự muốn tạo dữ liệu nhắc nợ với số tiền nợ >= ' + this.params.tuTien + '?'
                    : 'Bạn thật sự muốn tạo dữ liệu nhắc nợ với số tiền nợ >= ' + this.params.tuTien + ' và <= ' + this.params.denTien + '?';
                await this.$bvModal.msgBoxConfirm(confirmMessage, {
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
                        let beginTime = new Date();
                        let result = await this.updateDSNhacNoThanhToan();
                        if (result) {
                            this.LTPageInfo.page = 0;
                            await this.getDSLanTaoNhacNoThanhToan(this.thongtinKyHoaDon.kyHoaDonSelected);
                            if (this.lanTao_Items.length <= 0) {
                                this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                            } else {
                                let timeSpan = new Date() - beginTime;
                                let seconds = timeSpan / 1000;
                                let hours = parseInt( seconds / 3600 ); 
                                seconds = seconds % 3600;            
                                let minutes = parseInt( seconds / 60 );            
                                seconds = Math.round(seconds % 60);
                                this.display_params.thoiGianTao = hours.toString().padStart(2, '0') + ':' + minutes.toString().padStart(2, '0') + ':' + seconds.toString().padStart(2, '0');
                                this.display_params.soLuongTao = this.lanTao_Items[0].SOLUONG;
                            }
                        }                        
                    }
                })
                .catch(err => {
                    // An error occurred
                    console.log(err);
                })
            } catch (error) {
                this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
            }            
        },

        async onSearch() {
            if (this.lanTao_Items.length <= 0)
                return this.$toast.error('Chưa có lần tạo để lấy danh sách thanh toán!');

            await this.getDSNhacNoThanhToan(this.lanTao_Selected.LANTAO_TT_ID);
            if (this.thanhToan_Items.length <= 0) 
                return this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
        },

        async onChuyenNhac() {
            if (this.lanTao_Items.length <= 0) {
                this.$toast.error('Chưa có dữ liệu để chuyển nhắc nợ thanh toán!');
                return;
            }

            try {                
                await this.$bvModal.msgBoxConfirm('Bạn có chắc chắn muốn chuyển danh sách sang đã nhắc nợ thành công', {
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
                        let result = await this.updateChuyenDSNhacNoTT();
                        if (result)
                            this.$toast.success('Chuyển danh sách sang đã nhắc nợ thành công!');                        

                        this.clearDisplayData();
                        this.LTPageInfo.page = 0;
                        await this.getDSLanTaoNhacNoThanhToan(this.thongtinKyHoaDon.kyHoaDonSelected);
                    }
                })
                .catch(err => {
                    // An error occurred
                    console.log(err);
                })
            } catch (error) {
                this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
            }            
        },

        async onDeleteTT() {
            if (this.thanhToan_Items.length <= 0) {
                this.$toast.error('Chưa có dữ liệu để xóa!');
                return;
            }

            let TTCheckedList = this.$refs.thanhToanTable.getCheckedRecords();
            if (TTCheckedList.length <= 0) {
                this.$toast.error('Chưa chọn dữ liệu để xóa!');
                return;
            }

            try {                
                await this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa thanh toán đã chọn khỏi dữ liệu được tạo không?', {
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
                        let ds = [];
                        TTCheckedList.forEach(item => {
                            ds.push(item.THANHTOAN_ID);
                        });
                        let result = await this.deleteThanhToan(ds, TTCheckedList[0].LANTAO_TT_ID);
                        if (result)
                            this.$toast.success('Đã xóa thanh toán thành công!');                                             

                        let currentLanTaoId = this.lanTao_Selected.LANTAO_TT_ID;
                        this.LTPageInfo.page = 0;
                        await this.getDSLanTaoNhacNoThanhToan(this.thongtinKyHoaDon.kyHoaDonSelected);
                        this.LTPageInfo.currentRow = this.lanTao_Items.findIndex(i => i.LANTAO_TT_ID === currentLanTaoId);
                        this.TTPageInfo.page = 0;
                        await this.getDSNhacNoThanhToan(currentLanTaoId);
                    }
                })
                .catch(err => {
                    // An error occurred
                    console.log(err);
                })
            } catch (error) {
                this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
            }            
        },

        onTaoFile() {
            this.$toast.error("Chức năng tham chiếu tới form khác chưa hoàn thành!")
        },

        onExcelExport() {
            if (this.thanhToan_Items.length <= 0) {
                this.$toast.error('Chưa có dữ liệu để xuất excel!');
                return;
            }

            this.loading(true);
            try {
                let items = this.thanhToan_Items.slice();
                delete items.isChecked;
                delete items.CHON;
                delete items.FLAGS;
                var dsach = xlsx.utils.json_to_sheet(items);
                var wb = xlsx.utils.book_new(); // make Workbook of Excel
                xlsx.utils.book_append_sheet(wb, dsach, "Danh sách thanh toán"); // sheetAName is name of Worksheet
                // export Excel file
                xlsx.writeFile(wb, "NhacNoThanhToan.xlsx");
            } 
            catch (error) {
            } 
            finally {
                this.loading(false);
            }
        },

        async onDeleteLT(item) {            
            try {                
                await this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa dữ liệu lần tạo ' + item.NGAY_TAO + '?', {
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
                        if (item.NGUOI_CN !== this.$auth.getUserName())
                            return this.$toast.error('Lần tạo ' + item.LANTAO_TT_ID + ' do user ' + item.NGUOI_CN + '. Bạn không được phép xóa lần tạo này!');
                            
                        if (item.DANHAC !== '' && Number(item.DANHAC) !== 0)
                            return this.$toast.error('Lần tạo này đã có phiếu được nhắc nợ. Bạn không được phép xóa!');                        
                        
                        let result = await this.deleteLanTao(item.LANTAO_TT_ID);
                        if (result)
                            this.$toast.success('Xóa lần tạo thành công!');                       

                        this.LTPageInfo.page = 0;                        
                        await this.getDSLanTaoNhacNoThanhToan(this.thongtinKyHoaDon.kyHoaDonSelected);
                    }
                })
                .catch(err => {
                    // An error occurred
                })
            } catch (error) {
                this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
            }
        },

        async onRefreshLT() {     
            this.LTPageInfo.page = 0;
            await this.getDSLanTaoNhacNoThanhToan(this.thongtinKyHoaDon.kyHoaDonSelected);
        },

        clearDisplayData() {
            this.display_params.thoiGianTao = '00:00:00';
            this.display_params.soLuongTao = 0;
            this.thanhToan_Items = [];
            this.resetPaging(2);
        },

        /* type: 0 - Cả 2 bảng; 1 - Bảng lần tạo; 2 - bảng thanh toán */
        resetPaging(type) {
            if (type === 1 || type === 0) 
                this.LTPageInfo = {
                    page: 0,
                    maxSize: 10,
                    totalElement: 0,
                    totalPages: 0,
                    currentRow: 0,
                    sort: null,
                    propertiesSort: null
                };

            if (type === 2 || type === 0) 
                this.TTPageInfo = {
                    page: 0,
                    maxSize: 10,
                    totalElement: 0,
                    totalPages: 0,
                    currentRow: 0,
                    sort: null,
                    propertiesSort: null
                };
        },

        async updateDSNhacNoThanhToan() {
            try {
                this.loading(true);                
                let apiBody = {
                    denTien: this.params.denTien_Checked ? this.params.denTien : 0,
                    donViId: this.params.donVi_Selected.toString() !== '0' ? this.params.donVi_Selected : null,
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
                this.$toast.error(error.response.data.message_detail);
                return false;
            }
            finally {
                this.loading(false);
            }
        },

        async getDSLanTaoNhacNoThanhToan(kyCuoc) {
            try {
                this.loading(true);
                this.lanTao_Selected = {};
                this.lanTao_Items = [];
                this.thanhToan_Items = [];
                let apiParams = {
                    size: this.LTPageInfo.maxSize,
                    page: this.LTPageInfo.page
                };
                let response = await NhacNoAPI.getDSLanTaoNhacNoThanhToan(this.axios, kyCuoc, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.data.length > 0) {
                    this.lanTao_Items = response.data.data.data;
                    this.LTPageInfo = {
                        page : response.data.data.page + 1, // base 1
                        maxSize: response.data.data.maxSize,
                        totalElement: response.data.data.totalElement,
                        totalPages: response.data.data.totalPages,
                        sort: response.data.data.sort,
                        propertiesSort: response.data.data.propertiesSort,
                        currentRow: 0
                    }
                } else this.resetPaging(1);
            }
            catch (error) {
                this.resetPaging(1);
                this.$toast.error(error.response.data.message_detail);
            }
            finally {
                this.loading(false);
            }
        },

        async getDSNhacNoThanhToan(lanTaoId) {
            try {
                this.loading(true);
                this.thanhToan_Items = [];
                let apiParams = {
                    size: this.TTPageInfo.maxSize,
                    page: this.TTPageInfo.page
                };
                let response = await NhacNoAPI.getDSNhacNoThanhToan(this.axios, lanTaoId, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.data.length > 0) {                    
                    this.thanhToan_Items = response.data.data.data;
                    this.TTPageInfo = {
                        page : response.data.data.page + 1, // base 1
                        maxSize: response.data.data.maxSize,
                        totalElement: response.data.data.totalElement,
                        totalPages: response.data.data.totalPages,
                        sort: response.data.data.sort,
                        propertiesSort: response.data.data.propertiesSort,
                        currentRow: 0
                    }                    
                } else this.resetPaging(2);
            }
            catch (error) {
                this.resetPaging(2);
                this.$toast.error(error.response.data.message_detail);
            }
            finally {
                this.loading(false);
            }
        },

        async updateChuyenDSNhacNoTT() {
            try {
                this.loading(true);
                let response = await NhacNoAPI.updateChuyenDSNhacNoTT(this.axios, this.lanTao_Selected.LANTAO_TT_ID);
                if (response.data.error_code === 'BSS-00000000') {
                   return true;
                } else {
                    this.$toast.error(response.data.message_detail);
                    return false;
                }
            }
            catch (error) {
                this.$toast.error(error.response.data.message_detail);
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
                this.$toast.error(error.response.data.message_detail);
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
                this.$toast.error(error.response.data.message_detail);
                return false;
            }
            finally {
                this.loading(false);
            }
        }
    }
}
</script>
