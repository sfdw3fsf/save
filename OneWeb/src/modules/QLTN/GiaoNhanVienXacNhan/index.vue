<template src="./index.template.html"></template>
<style scoped src="./style.scss"></style>
<script>
import API from '../api/CongNoAPI.js'
import CommonAPI from '../api/Commons.js'
import { mapActions, mapGetters } from "vuex"
import PopupChonNhanVienXacNhan from '../components/PopupChonNhanVienXacNhan/PopupChonNhanVienXacNhan.vue'
import { maxLength } from "vuelidate/lib/validators"
import moment from 'moment'

export default {
    components: { PopupChonNhanVienXacNhan },

    async created() {
        this.initComboboxData();
    },

    computed: {
        ...mapGetters('qltnCommon', [
            'danhSachLanTaoCongNoGetter'            
        ]),
        ...mapGetters("GanNhomCongNo", [
            "listDonViGetter",
            "listHTTTGetter",
            "listLoaiKHGetter",
            "listPLKHGetter",
            "listNVQuanLyGetter",
            "listNVThuCuocGetter"
        ])
    },

    data() {
        return {
            params: {
                lanTao_Options: [],
                lanTao_Selected: null,
                donVi_Checked: false,
                donVi_Options: [],
                donVi_Selected: -1,
                hinhThucTT_Checked: false,
                hinhThucTT_Options: [],
                hinhThucTT_Selected: null,
                nhomKH_Checked: false,
                nhomKH_Options: [
                    { id: -1, text: 'Tất cả' },
                    { id: 0, text: 'Cá nhân' },
                    { id: 1, text: 'Doanh nghiệp' }
                ],
                nhomKH_Selected: null,
                nhanVienQL_Checked: false,
                nhanVienQL_Options: [],
                nhanVienQL_Selected: -1,
                NVTC_Checked: false,
                NVTC_Options: [],
                NVTC_Selected: null,
                phanLoaiKH_Checked: false,
                phanLoaiKH_Options: [],
                phanLoaiKH_Selected: null,
                loaiKH_Checked: false,
                loaiKH_Options: [],
                loaiKH_Selected: null,
                nhanVienTH_Id: 0,
                NVXL: '',
                ngayHT: new Date(),
                ngayIn: new Date(),
                ngayGui: new Date(),
                ngayLH: new Date(),
                ngayPH: new Date(),
                today: new Date(),
                ghiChu: '',
                daGan: 0,
                soLuongTao: ''
            },
            chonNVXLBtn_Enabled: true,
            giaoNVBtn_Enabled: true,
            huyGiaoBtn_Enabled: false,
            congNo_grid: {
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
            }
        }
    },

    validations: {
        params: {
            ghiChu: {                
                maxLength: maxLength(200)
            }
        }
    },

    methods: {
        ...mapActions('qltnCommon', [
            'getDanhSachLanTaoCongNo'            
        ]),
        ...mapActions("GanNhomCongNo", [
            "getDonVi",
            "getHTTT",
            "getPhanLoaiKH",
            "getDanhSachKHLon",
            "getNVQuanLy",
            "getNVThuCuoc",
            "getDanhSachLoaiKH"            
        ]),

        validateData() {
            this.$v.$touch();
            let errorMessage = [];

            if (!this.$v.params.ghiChu.maxLength)
                errorMessage.push("Trường ghi chú vượt quá độ dài cho phép!");

            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join("\n"));
                return false;
            } else return true;
        },

        async onSearch() {
            this.resetPaging();
            await this.getDanhSachCongNoDaGan();
        },

        async onGiaoNV() {
            this.params.ghiChu = this.params.ghiChu.trim();
            if (this.congNo_grid.items.length <= 0)
                return this.$toast.error('Chưa có dữ liệu để thực hiện giao');
                
            if (this.congNo_grid.checked.length <= 0) 
                return this.$toast.error('Chưa chọn phiếu để thực hiện giao');
            
            if (this.params.nhanVienTH_Id === 0)
                return this.$toast.error('Chưa chọn nhân viên để thực hiện giao');
            
            if (!this.validateData())
                return this.$refs.ghiChu.focus();

            try {                
                await this.$bvModal.msgBoxConfirm('Bạn thật sự muốn giao nhân viên không?', {
                "title": 'Thông báo',
                "size": 'sm',
                "buttonSize": 'md',
                "okVariant": 'primary',
                "okTitle": 'Đồng ý',
                "cancelTitle": 'Hủy bỏ',
                "hideHeaderClose": false,
                "centered": true,
                "modal-class": ["f18","text-center"],
                })
                .then(async(value) => {
                    if (value){                       
                        let rs = await this.updateGiaoPhieuNhanVienXacNhan(this.congNo_grid.checked, true);
                        if (rs)
                            this.$toast.success('Giao nhân viên xác nhận công nợ thành công!');                        
                        
                        this.resetPaging();
                        await this.getDanhSachCongNoDaGan(false);
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

        async onHuyGiao() {
            this.params.ghiChu = this.params.ghiChu.trim();
            if (this.congNo_grid.items.length <= 0)
                return this.$toast.error('Chưa có dữ liệu để thực hiện hủy giao');                        
            
            if (this.congNo_grid.checked.length <= 0) 
                return this.$toast.error('Chưa chọn phiếu để thực hiện hủy giao');
            
            if (!this.validateData())
                return this.$refs.ghiChu.focus();
            
            try {                
                await this.$bvModal.msgBoxConfirm('Bạn thật sự muốn hủy giao không?', {
                "title": 'Thông báo',
                "size": 'sm',
                "buttonSize": 'md',
                "okVariant": 'primary',
                "okTitle": 'Đồng ý',
                "cancelTitle": 'Hủy bỏ',
                "hideHeaderClose": false,
                "centered": true,
                "modal-class": ["f18","text-center"],
                })
                .then(async(value) => {
                    if (value){                       
                        let rs = await this.updateGiaoPhieuNhanVienXacNhan(this.congNo_grid.checked, false);
                        if (rs)
                            this.$toast.success('Hủy giao xác nhận công nợ thành công!');                        
                        
                        this.resetPaging();
                        await this.getDanhSachCongNoDaGan(false);
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

        onChonNVXL() {
            this.$refs.dlgChonNhanVienXacNhan.openDialog();
        },

        onChonNVXLProcess(e) {
            if (e != null && Object.keys(e).length !== 0) {
                this.params.nhanVienTH_Id = e.NHANVIEN_ID;
                this.params.NVXL = e.TEN_NV;
            }
        },

        selectedItemsChanged(e) {
            this.congNo_grid.checked = e;
        },

        onCongNoSelected(args) {
            if (args != null) {
                if (Array.isArray(args.data))
                    //Check all
                    this.onHienThiThongTinGiao(args.data[0]);
                else
                    //Check one row
                    this.onHienThiThongTinGiao(args.data);
                this.params.ghiChu = '';
            }
        },

        onHienThiThongTinGiao(data) {
            if (this.params.daGan === 1)
                this.params.NVXL = data.TEN_NV_NHAN;
            if (data.THOIHAN_IN != null && data.THOIHAN_IN !== '')
                this.params.ngayIn = moment(data.THOIHAN_IN, 'DD/MM/YYYY').toDate();
            if (data.THOIHAN_GUI != null && data.THOIHAN_GUI !== '')
                this.params.ngayGui = moment(data.THOIHAN_GUI, 'DD/MM/YYYY').toDate();
            if (data.THOIHAN_LH != null && data.THOIHAN_LH !== '')
                this.params.ngayLH = moment(data.THOIHAN_LH, 'DD/MM/YYYY').toDate();
            if (data.THOIHAN_PH != null && data.THOIHAN_PH !== '')
                this.params.ngayPH = moment(data.THOIHAN_PH, 'DD/MM/YYYY').toDate();
            if (data.THOIHAN_HT != null && data.THOIHAN_HT !== '')
                this.params.ngayHT = moment(data.THOIHAN_HT, 'DD/MM/YYYY').toDate();
        },

        async congNoGridpageChangeHandler(e) {
            this.congNo_grid.pageInfo.page = e.pageIndex;
            this.congNo_grid.pageInfo.maxSize = e.pageSize;
            await this.getDanhSachCongNoDaGan(false);
        },

        resetPaging() {
            this.congNo_grid.items = [];
            this.congNo_grid.checked = [];
            this.congNo_grid.pageInfo = {
                page: 0,
                maxSize: 10,
                totalElement: 0,
                totalPages: 0,
                currentRow: 0,
                sort: null,
                propertiesSort: null
            }
        },

        async initComboboxData() {
            this.params.daGan = 0;
            this.params.nhomKH_Selected = -1;

            await this.getDanhSachLanTaoCongNo();
            if (this.danhSachLanTaoCongNoGetter !== undefined && this.danhSachLanTaoCongNoGetter.length > 0) {
                this.danhSachLanTaoCongNoGetter.forEach(item => {
                    this.params.lanTao_Options.push({ id: item.LANTAO_ID, text: item.KYHOADON });
                });
                this.params.lanTao_Selected = this.params.lanTao_Options[0].id;
            }

            if (this.listDonViGetter.length <= 0)
                await this.getDonVi();
            this.listDonViGetter.forEach(item => {
                this.params.donVi_Options.push({ id: item.DONVI_ID, text: item.TEN_DV });
            });
            this.params.donVi_Options.unshift({ id: -1, text: 'Tất cả' });
            if (this.params.donVi_Options.length > 0) {            
                this.params.donVi_Selected = this.params.donVi_Options[0].id;
            }
            
            if (this.listHTTTGetter.length <= 0)
                await this.getHTTT();
            this.listHTTTGetter.forEach(item => {            
                this.params.hinhThucTT_Options.push({ id: item.HTTT_ID, text: item.HTTT })
            });
            this.params.hinhThucTT_Options.unshift({ id: -1, text: 'Tất cả' });
            if (this.params.hinhThucTT_Options.length > 0) {            
                this.params.hinhThucTT_Selected = this.params.hinhThucTT_Options[0].id;
            }
        },       

        async getDanhSachCongNoDaGan(isToast = true) {
            try {
                this.loading(true);
                this.congNo_grid.key++;
                this.params.soLuongTao = '';
                let apiParams = {
                    donViId: this.params.donVi_Checked ? (Number(this.params.donVi_Selected) === -1 ? null : this.params.donVi_Selected) : null,
                    htttId: this.params.hinhThucTT_Checked ? (Number(this.params.hinhThucTT_Selected) === -1 ? null : this.params.hinhThucTT_Selected) : null,                    
                    loaiKH: this.params.loaiKH_Checked ? (Number(this.params.loaiKH_Selected) === -1 ? null : this.params.loaiKH_Selected) : null,
                    nhanVienQLId: this.params.nhanVienQL_Checked ? (Number(this.params.nhanVienQL_Selected) === -1 ? null : this.params.nhanVienQL_Selected) : null,
                    nhomKH: this.params.nhomKH_Checked ? (Number(this.params.nhomKH_Selected) === -1 ? null : this.params.nhomKH_Selected) : null,
                    phanLoaiKHId: this.params.phanLoaiKH_Checked ? (Number(this.params.phanLoaiKH_Selected) === -1 ? null : this.params.phanLoaiKH_Selected) : null,
                    tuyenThuId: this.params.NVTC_Checked ? (Number(this.params.NVTC_Selected) === -1 ? null : this.params.NVTC_Selected) : null,
                    kieu: this.params.daGan,
                    lanTaoId: this.params.lanTao_Selected,
                    pageNum: this.congNo_grid.pageInfo.page + 1,
                    pageSize: this.congNo_grid.pageInfo.maxSize,
                    totalRow: 1
                };
                let response = await API.getDanhSachCongNoDaGan(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data.data !== undefined) {
                    this.congNo_grid.items = response.data.data.data;
                    this.params.soLuongTao = response.data.data.totalElement ? response.data.data.totalElement : 0;
                    this.congNo_grid.pageInfo = {
                        page : response.data.data.page,
                        maxSize: response.data.data.maxSize,
                        totalElement: response.data.data.totalElement ? response.data.data.totalElement : 0,
                        totalPages: response.data.data.totalPages,
                        sort: response.data.data.sort,
                        propertiesSort: response.data.data.propertiesSort,
                        currentRow: 0
                    }
                } else this.resetPaging();
                if (this.congNo_grid.items.length <= 0 && isToast)
                    this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
            }
            catch (error) {
                this.$toast.error(error.data.message_detail);
                this.resetPaging();
            }
            finally {
                this.loading(false);
            }
        },

        async updateGiaoPhieuNhanVienXacNhan(ds, isGiaoPhieu) {
            try {
                this.loading(true);                
                let apiBody = {
                    ds: ds,
                    ghiChu: this.params.ghiChu,
                    giaoPhieu: isGiaoPhieu,                    
                    ngayGiao: moment(new Date()).format('DD/MM/YYYY'),
                    nguoiGiaoId: this.$auth.getNhanVienID(),
                    nguoiNhanId: this.params.nhanVienTH_Id
                };
                let response = await API.updateGiaoPhieuNhanVienXacNhan(this.axios, apiBody);
                if (response.data.error_code === 'BSS-00000000') {
                    return true;
                } else return false;
            }
            catch (error) {
                console.log(error)
                this.$toast.error(error.data.message_detail);
                return false;
            }
            finally {
                this.loading(false);
            }
        }
    },

    watch: {
        'params.donVi_Selected': async function(newVal, oldVal) {            
            await this.getNVQuanLy(newVal);
            this.params.nhanVienQL_Options = [];
            if (this.listNVQuanLyGetter.length > 0) {
                this.listNVQuanLyGetter.forEach(item => {
                        this.params.nhanVienQL_Options.push({ id: item.NHANVIEN_ID, text: item.GHEPTEN });
                });
                this.params.nhanVienQL_Selected = this.params.nhanVienQL_Options[0].id;
            }
        },

        'params.nhanVienQL_Selected': async function(newVal, oldVal) {            
            await this.getNVThuCuoc(newVal);
            this.params.NVTC_Options = [];
            if (this.listNVThuCuocGetter.length > 0) {
                this.listNVThuCuocGetter.forEach(item => {
                    this.params.NVTC_Options.push({ id: item.TUYENTHU_ID, text: item.GHEPTUYEN });
                });
                this.params.NVTC_Selected = this.params.NVTC_Options[0].id;      
            }  
        },

        'params.nhomKH_Selected': async function(newVal, oldVal) {            
            await this.getPhanLoaiKH(newVal);
            await this.getDanhSachLoaiKH(newVal);
            this.params.phanLoaiKH_Options = [];
            this.params.loaiKH_Options = [];

            if (this.listPLKHGetter.length > 0) {
                this.listPLKHGetter.forEach(item => {
                    this.params.phanLoaiKH_Options.push({ id: item.PHANLOAIKH_ID, text: item.MA_PLKH });
                });
                //Chuyển option Tất cả (id = null) về id = -1:
                let idx = this.params.phanLoaiKH_Options.findIndex(e => e.id === null);
                if (idx !== -1)
                    this.params.phanLoaiKH_Options[idx].id = -1;
                this.params.phanLoaiKH_Selected = this.params.phanLoaiKH_Options[0].id;   
            }
            this.params.loaiKH_Options.unshift({ id: -1, text: 'Tất cả' });
            if (this.listLoaiKHGetter.length > 0) {
                this.listLoaiKHGetter.forEach(item => {
                    this.params.loaiKH_Options.push({ id: item.LOAIKH_ID, text: item.TEN_LOAIKH });
                });
                this.params.loaiKH_Selected = this.params.loaiKH_Options[0].id;   
            }
        },

        'params.daGan': async function(newVal, oldVal) {            
            if (newVal === 0) { //Chua giao
                this.chonNVXLBtn_Enabled = true;
                this.giaoNVBtn_Enabled = true;
                this.huyGiaoBtn_Enabled = false;
            } else {            //Da giao
                this.chonNVXLBtn_Enabled = false;
                this.giaoNVBtn_Enabled = false;
                this.huyGiaoBtn_Enabled = true;
            }
            this.params.ghiChu = '';
            this.params.NVXL = '';
            this.params.nhanVienTH_Id = 0;
            this.resetPaging();
            await this.getDanhSachCongNoDaGan(false);
        },

        'params.nhomKH_Checked': async function(newVal, oldVal) {
            if (!newVal) {
                this.params.phanLoaiKH_Checked = false;
                this.params.loaiKH_Checked = false;
            }
        }
    }
}
</script>
