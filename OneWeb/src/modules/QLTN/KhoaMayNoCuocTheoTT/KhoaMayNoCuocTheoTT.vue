<template src="./KhoaMayNoCuocTheoTT.html"></template>
<style scoped src="../MoMayTheoDanhSach/style.scss">

</style>
<script>
import API from '../api/KhoaMayNoCuocTTAPI.js'
import PopupDanhSachLoi from './components/PopupDanhSachLoi'
import PopupTraCuuDanhBa from '../components/PopupTraCuuDanhBa/PopupTraCuuDanhBa.vue'
import { mapActions, mapGetters } from "vuex"
import { required, maxLength } from "vuelidate/lib/validators"
import EventBus from '@/utils/eventBus'
import moment from 'moment'
import xlsx from "xlsx"
import Vue from 'vue'
import {
KIEUKHOAMO,
KieuKhoa,
TrangThaiTB,
DichVuVienThong
}
    from '@/utils/Enum'
import { SplitterPlugin } from '@syncfusion/ej2-vue-layouts'
import { Splitter } from '@syncfusion/ej2-layouts'
Vue.use(SplitterPlugin)

export default {
    components: { PopupDanhSachLoi, PopupTraCuuDanhBa },
    name: 'KhoaMayNoCuocTheoTT',

    async created() {
        EventBus.$on('onTBCheckboxChanged', this.onTBCheckboxChangedHandler);
        EventBus.$on('onTTCheckboxChanged', this.onTTCheckboxChangedHandler);

        if (Object.keys(this.kyHoaDonHienTaiGetter).length <= 0)
            await this.getKyHoaDonHienTai();
        if (Object.keys(this.kyHoaDonHienTaiGetter).length > 0)
            this.common_params.kyCuoc = this.kyHoaDonHienTaiGetter.KYCUOC;
        await this.getThamSoMD();
        if (this.common_params.khoaMay_LoaiNV_DiemTN === 1)
            await this.getDiemTinNhiemNhanVien();
        this.params.kieuYC_Selected = 1;
    },

    destroyed() {
        EventBus.$off('onTBCheckboxChanged', this.onTBCheckboxChangedHandler);
        EventBus.$off('onTTCheckboxChanged', this.onTTCheckboxChangedHandler);
    },

    mounted() {
        this.$refs.maTT.focus();
    },

    computed: {
        ...mapGetters('qltnCommon', [
            'kyHoaDonHienTaiGetter'
        ]),
        TTaggregatesColumns() {
            return [{ field: 'MA_TT', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.TTcustomAggregate }]
        },
        TBaggregatesColumns() {
            return [{ field: 'MA_TT', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.TBcustomAggregate }]
        },
    },

    data() {
        return {
            params: {
                kieuYC_Options: [
                    { id: 0, text: 'Mở máy nợ cước' },
                    { id: 1, text: 'Khóa máy do nợ cước' }
                ],
                kieuYC_Selected: null,
                chieuKhoaMo_Title: '',
                chieuKhoaMo_Options: [],
                chieuKhoaMo_Selected: 2,
                filePath: '',
                maTT_Title: 'Mã TT',
                maTT_Checked: false,
                maTT: '',
                maTB_DD: '',
                tenTT: '',
                diaChiTT: '',
                lyDoKhoa: '',
                lyDoKhoa_Title: '',
                tongTT_Title: 'Tổng số thanh toán',
                kieuFile: 1
            },
            thanhToan_grid: {
                items: [],
                checked: [],
                selected: {}
            },
            thueBao_grid: {
                items: [],
                checked: []
            },
            thongTinTT: [],
            thongTinTB: [],
            diemTinNhiemKH: 0,
            thanhToanId_Items: [],
            dsLoi_Items: [],
            selectIdx: [],
            dsTB: [],
            common_params: {
                khoaMay_LoaiNV_DiemTN: -1,
                khoaMay_TB_Theo_TuyenThu: -1,
                khoaMay_File_LyDoKhoa: -1,
                diemTN_Tu: 0,
                diemTN_Den: 0,
                kyCuoc: moment(new Date()).subtract(1, 'months').startOf('month').format('YYYYMMDD').toString(),
                isReadFile: false
            },
            maTT_Items: [],
            customTemplate: function () {
                return {
                    template: Vue.component('footerTemplate', {
                        template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
                        data() { return { data: { data: {} } }; }
                    })
                }
            },
            checkboxColumnTBTemplate: function () {
                return {
                    template: {
                        template: `<div class="check-action" style="vertical-align: middle;">
                                        <input type="checkbox" class="check" @change="onChanged" v-model="data.CHON"/>
                                        <span class="name"></span>
                                    </div>`,
                        data() {
                            return { data: {} }
                        },
                        methods: {
                            onChanged() {
                                EventBus.$emit('onTBCheckboxChanged', this.data);
                            }
                        }
                    }
                }
            },
            checkboxColumnTTTemplate: function () {
                return {
                    template: {
                        template: `<div class="check-action" style="vertical-align: middle;">
                                        <input type="checkbox" class="check" @change="onChanged" v-model="data.CHON"/>
                                        <span class="name"></span>
                                    </div>`,
                        data() {
                            return { data: {} }
                        },
                        methods: {
                            onChanged() {
                                EventBus.$emit('onTTCheckboxChanged', this.data);
                            }
                        }
                    }
                }
            },

            checkAllMaTT: false,
            colCheckboxAllMaTT: function () {
                return {
                    template: {
                        template: `
                                    <div class="check-action">
                                        <input type="checkbox" v-model="parent.checkAllMaTT" class="uncheck"/>
                                        <span class="name"></span>
                                    </div>
                                  `,
                        data: function () {
                            return {};
                        },
                        computed: {
                            parent() {
                                return this.$parent.$parent.$parent;
                            },
                        },
                    },
                };
            },

            checkAllMaTB: false,
            colCheckboxAllMaTB: function () {
                return {
                    template: {
                        template: `
                                    <div class="check-action">
                                        <input type="checkbox" v-model="parent.checkAllMaTB" class="uncheck"/>
                                        <span class="name"></span>
                                    </div>
                                  `,
                        data: function () {
                            return {};
                        },
                        computed: {
                            parent() {
                                return this.$parent.$parent.$parent;
                            },
                        },
                    },
                };
            },
        }
    },

    validations: {
        params: {
            lyDoKhoa: {
                required,
                maxLength: maxLength(500)
            }
        }
    },

    methods: {
        ...mapActions('qltnCommon', [
            'getKyHoaDonHienTai'
        ]),
        onCreate(e) {
            this.splitterObj = new Splitter({
                paneSettings: [
                    { size: '50%', min: '20%' },
                    { size: '50%', min: '20%' }
                ],
                orientation: 'Vertical'
            });
            this.splitterObj.appendTo('#vertical_splitter');
        },

        validateData() {
            this.$v.$touch();
            let errorMessage = [];

            if (!this.$v.params.lyDoKhoa.required) {
                if (this.params.kieuYC_Selected === 0)
                    errorMessage.push('Hãy nhập lý do mở máy!');
                else {
                    if (this.common_params.khoaMay_File_LyDoKhoa !== 1)
                        errorMessage.push('Hãy nhập lý do khóa máy!');
                }
            } else if (!this.$v.params.lyDoKhoa.maxLength)
                errorMessage.push("Trường lý do khóa có dữ liệu vượt quá độ dài cho phép!");

            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join("\r\n"));
                this.$refs.lyDo.focus();
                return false;
            } else return true;
        },

        onTaoPhieu() {
            this.$router.push({
                name: 'TiepNhanThongTinKhoaMoNoCuoc',
                params: {
                    tag: '1+7+1;4;8;9;10;11;12',
                    dichvuID: 1,
                    kieuYCID: this.params.kieuYC_Selected,
                    tthdID: 1
                }
            });
        },

        TTcustomAggregate() {
            return this.thanhToan_grid.checked.length + '/' + this.thanhToan_grid.items.length;
        },

        TBcustomAggregate() {
            return this.thueBao_grid.checked.length + '/' + this.thueBao_grid.items.length;
        },

        showTraCuuDanhBa() {
            //UR2.8.002
            this.$refs.dlgTraCuuDanhBa.showModal();
        },

        async acceptDanhBa(args) {
            if (args) {
                this.params.maTT = args.ma_tt ? args.ma_tt.trim() : '';
                this.$refs.maTT.focus();
            }
        },

        async onSearch(type) {
            if (type === 1) {   //Read file
                this.common_params.isReadFile = true;
                this.thanhToan_grid.items = this.maTT_Items;
                this.params.tongTT_Title = 'Tổng: ' + this.thanhToan_grid.items.length + ' bản ghi';
            } else {            //Enter maTT
                this.common_params.isReadFile = false;
                this.params.maTT = this.params.maTT.trim();
                if (this.params.maTT !== '') {
                    this.params.lyDoKhoa = '';
                    let tk = await this.getMaVach();
                    if (!tk) return;

                    await this.getThongTinTTTheoDK();
                    if (this.thongTinTT.length <= 0)
                        return this.$toast.error('Không có dữ liệu, vui lòng thử lại!');

                    if (this.common_params.khoaMay_LoaiNV_DiemTN === 1) {
                        await this.getDiemTinNhiemKhachHang(this.thongTinTT[0].KHACHHANG_ID);
                        if (this.diemTinNhiemKH !== -1 && this.diemTinNhiemKH !== 0 && (this.common_params.diemTN_Tu !== 0 || this.common_params.diemTN_Den !== 0))
                            if (this.diemTinNhiemKH < this.common_params.diemTN_Tu || this.diemTinNhiemKH > this.common_params.diemTN_Den)
                                return this.$toast.error(`Bạn chỉ được khóa máy với khách hàng có điểm tín nhiệm từ ${this.common_params.diemTN_Tu} đến ${this.common_params.diemTN_Den}. \r\nKhách hàng này có điểm tín nhiệm: ${this.diemTinNhiemKH}`);
                    }

                    if (this.common_params.khoaMay_TB_Theo_TuyenThu === 1) {
                        let result = await this.getKiemTraKhoaMoTBTheoTuyenThu(this.thongTinTT[0].THANHTOAN_ID);
                        if (result)
                            return this.$toast.error(`Mã TT: ${this.params.maTT} không nằm trong tuyến thu được quản lý. \r\nBạn không thể khóa thuê bao của thanh toán này!`);
                    }

                    if (this.thanhToan_grid.items.length <= 0) {
                        this.thanhToan_grid.items = this.thongTinTT.map((item) => ({
                            ...item,
                            CHON: true
                        }));
                        this.params.maTB_DD = this.thongTinTT[0].MATB_DD;

                        for (let item of this.thanhToan_grid.items) {
                            await this.onTTCheckboxChangedHandler(item);
                        }
                    } else {
                        this.params.maTT = this.thongTinTT[0].MA_TT.trim();
                        this.params.maTB_DD = this.thongTinTT[0].MATB_DD;
                        let idx = this.thanhToan_grid.items.findIndex(e => e.MA_TT === this.params.maTT);
                        if (idx !== -1) {
                            this.$refs.thanhToanGrid.setCurrentSelectedRow(idx);
                        } else {
                            let ds = this.thongTinTT.map((item) => ({
                                ...item,
                                CHON: true
                            }));
                            this.thanhToan_grid.items = this.thanhToan_grid.items.concat(ds);
                            this.params.tongTT_Title = 'Tổng: ' + this.thanhToan_grid.items.length + ' bản ghi';
                            for (let item of ds) {
                                await this.onTTCheckboxChangedHandler(item);
                            }
                        }
                    }

                    this.thanhToan_grid.checked = this.thanhToan_grid.items.filter(e => e.CHON).map(i => i.MA_TT);
                }
            }
        },

        async getMaVach() {
            if (this.params.maTT_Checked) {
                await this.getThanhToanIdTheoMaVach();
                if (this.thanhToanId_Items.length > 0) {
                    this.params.maTT = this.thanhToanId_Items[0].MA_TT;
                    return true;
                } else {
                    this.$toast.error(`Không tìm thấy mã thanh toán từ mã vạch ${this.params.maTT.trim()} (${this.common_params.kyCuoc})`);
                    return false;
                }
            } else
                return true;
        },

        async onChuyenDS() {
            if (this.thueBao_grid.items.length <= 0)
                return this.$toast.error('Không có thuê bao để chuyển danh sách!');

            if (this.thueBao_grid.checked.length <= 0)
                return this.$toast.error('Chưa chọn thuê bao để chuyển!');

            this.params.lyDoKhoa = this.params.lyDoKhoa.trim();
            if (this.validateData()) {
                try {
                    await this.$bvModal.msgBoxConfirm('Bạn có thật sự muốn tạo dữ liệu không?', {
                        "title": 'Thông báo',
                        "size": 'lg',
                        "buttonSize": 'lg',
                        "okVariant": 'primary',
                        "okTitle": 'Đồng ý',
                        "cancelTitle": 'Hủy bỏ',
                        "hideHeaderClose": false,
                        "centered": true,
                        "modal-class": ["f18", "text-center"],
                    })
                        .then(async (value) => {
                            if (value) {
                                let dsKhoaMay = [];
                                this.thueBao_grid.checked.forEach(item => {
                                    let idx = this.thueBao_grid.items.findIndex(e => e.MA_TB === item);
                                    let lydo = this.params.kieuYC_Selected === 1 && this.common_params.khoaMay_File_LyDoKhoa === 1
                                        ? this.thueBao_grid.items[idx].LYDO_KHOA : this.params.lyDoKhoa.trim();
                                    let ds = {
                                        ma_tb: this.thueBao_grid.items[idx].MA_TB,
                                        ma_tt: this.thueBao_grid.items[idx].MA_TT,
                                        dichvuvt_id: this.thueBao_grid.items[idx].DICHVUVT_ID,
                                        loaitb_id: this.thueBao_grid.items[idx].LOAITB_ID,
                                        thuebao_id: this.thueBao_grid.items[idx].THUEBAO_ID,
                                        lydo_khoa: lydo
                                    };
                                    dsKhoaMay.push(ds);
                                });
                                let rs = await this.updateDanhSachKhoaMoMay(dsKhoaMay);
                                this.onCancel();
                            }
                        })
                        .catch(err => {
                            // An error occurred
                        })
                } catch (error) {
                    this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
                }
            }
        },

        onCancel() {
            this.params.maTT = '';
            this.params.maTB_DD = '';
            this.params.tenTT = '';
            this.params.lyDoKhoa = '';
            this.params.diaChiTT = '';
            this.params.filePath = '';
            this.thanhToan_grid.items = [];
            this.thueBao_grid.items = [];
            this.dsTB = [];
            this.params.kieuYC_Selected = 1;
            this.params.chieuKhoaMo_Selected = 2;
            this.$v.$reset(); //Reset validator
        },

        async onChangeFile(e) {
            const files = e.target.files;
            if (!files.length) {
                return;
            } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase()))
                return this.$toast.error("File excel không đúng định dạng!");

            const fileReader = new FileReader();
            fileReader.onload = async (ev) => {
                try {
                    this.maTT_Items = [];
                    this.params.lyDoKhoa = '';
                    this.thanhToan_grid.items = [];
                    this.thueBao_grid.items = [];
                    this.dsTB = [];
                    const data = ev.target.result;
                    const workbook = xlsx.read(data, { type: "binary" });
                    const wsname = workbook.SheetNames[0];
                    const ws = xlsx.utils.sheet_to_json(workbook.Sheets[wsname]);

                    if (this.params.kieuYC_Selected === 1 && this.common_params.khoaMay_File_LyDoKhoa === 1) {
                        if (Object.keys(ws[0]).length !== 2)
                            return this.$toast.error('File khóa máy cần các cột: MA_TT, LYDO_KHOA. Hãy kiểm tra lại!');
                        if (!ws[0].hasOwnProperty("MA_TT") || !ws[0].hasOwnProperty("LYDO_KHOA"))
                            return this.$toast.error('Tên cột trong file không đúng định dạng: MA_TT, LYDO_KHOA');
                    } else {
                        if (Object.keys(ws[0]).length !== 1)
                            return this.$toast.error('File chỉ được phép có 1 cột MA_TT. Hãy kiểm tra lại!');
                        if (!ws[0].hasOwnProperty("MA_TT"))
                            return this.$toast.error('Tên cột trong file không đúng định dạng: MA_TT');
                    }

                    if (ws[0].hasOwnProperty("MA_TT"))
                        this.params.kieuFile = 1;
                    else if (ws[0].hasOwnProperty("MATB_DD"))
                        this.params.kieuFile = 2;

                    if (ws.length > 0) {
                        ws.forEach(item => {
                            this.maTT_Items.push({
                                MA_TT: item.MA_TT,
                                TEN_TT: '',
                                DIACHI_TT: '',
                                TONGNO: 0,
                                TRANGTHAI_ID: '0',
                                MATB_DD: '',
                                TRANGTHAI: ''
                            });
                        });
                        if (this.onCheckLoiDocFile(this.maTT_Items))
                            await this.onSearch(1);
                    }
                } catch (e) {
                    console.log(e)
                    return this.$toast.error("Kiểm tra dữ liệu file đầu vào!");
                }
            };
            fileReader.readAsBinaryString(files[0]);
            this.params.filePath = files[0].name;
        },

        onCheckLoiDocFile(ds) {
            this.dsLoi_Items = [];
            let idx = 1;
            ds.forEach(item => {
                if ((item.MA_TT === null || item.MA_TT === '')
                    || (this.common_params.khoaMay_File_LyDoKhoa === 1
                        && (item.LYDO_KHOA === null || item.LYDO_KHOA === ''))) {
                    this.dsLoi_Items.push({
                        STT: idx++,
                        MA_TT: item.MA_TT,
                        LYDO_KHOA: item.LYDO_KHOA,
                        LYDO_LOI: item.MA_TT === null || item.MA_TT === '' ? 'Mã TT null'
                            : item.LYDO_KHOA === null || item.LYDO_KHOA === '' ? 'Lý do khóa null' : ''
                    });
                }
            });
            if (this.dsLoi_Items.length > 0) {
                this.$refs.dlgDanhSachLoi.openDialog();
                return false;
            }
            return true;
        },

        async onTTCheckboxChangedHandler(args) {
            let index = this.thanhToan_grid.items.findIndex(e => e.MA_TT === args.MA_TT);
            if (index !== -1) {
                //CHECKED
                if (args.CHON) {
                    this.thanhToan_grid.items[index].CHON = true;
                    this.thanhToan_grid.checked.push(args.MA_TT);
                } else {
                    //UN-CHECKED
                    this.thanhToan_grid.items[index].CHON = false;
                    let indexChecked = this.thanhToan_grid.checked.findIndex(e => e === args.MA_TT);
                    if (indexChecked !== -1)
                        this.thanhToan_grid.checked.splice(indexChecked, 1);
                }

                await this.onThanhToanGrid_CellContentClick(index);

                //REFRESH DATASOURCE & FOOTER
                this.thanhToan_grid.items = [...this.thanhToan_grid.items];
                let dataGridObj = this.$refs.thanhToanGrid.$refs.grid.ej2Instances;
                dataGridObj.aggregateModule.refresh();

                //SELECT ROW
                let timeOut = setInterval(() => {
                    this.$refs.thanhToanGrid.setCurrentSelectedRow(index);
                    clearInterval(timeOut);
                }, 40);
            }
        },

        async onThanhToanGrid_CellContentClick(idx) {
            let lyDoKhoa = this.common_params.khoaMay_File_LyDoKhoa === 1 ? this.thanhToan_grid.items[idx].LYDO_KHOA : this.params.lyDoKhoa.trim();
            let maTT = '';
            if (this.params.kieuFile === 1)
                maTT = this.thanhToan_grid.items[idx].MA_TT;
            if (this.params.kieuFile === 2)
                maTT = this.thanhToan_grid.items[idx].MATB_DD;

            if (this.thanhToan_grid.items[idx].CHON) {
                let ds = await this.getThongTinTBTheoDK(maTT, this.params.kieuFile);
                if (ds.length <= 0) {
                    this.thanhToan_grid.items[idx].TRANGTHAI = 'Không tìm thấy thông tin thanh toán';
                    return false;
                } else {
                    this.thanhToan_grid.items[idx].TEN_TT = ds[0].TEN_TT;
                    this.thanhToan_grid.items[idx].DIACHI_TT = ds[0].DIACHI_TT;
                    this.thanhToan_grid.items[idx].LYDO_KHOA = lyDoKhoa;
                    if (this.params.kieuFile === 1)
                        this.thanhToan_grid.items[idx].MATB_DD = ds[0].MATB_DD;
                    if (this.params.kieuFile === 2)
                        this.thanhToan_grid.items[idx].MA_TT = ds[0].MA_TT;
                }

                if (this.thueBao_grid.items.length <= 0) {
                    this.dsTB = ds;
                    this.dsTB.forEach(item => {
                        item.TRANGTHAI = '';
                        item.LYDO_KHOA = '';
                    });
                } else {
                    ds.forEach(item => {
                        let checkIndex = this.dsTB.findIndex(e => e.THUEBAO_ID === item.THUEBAO_ID);
                        if (checkIndex === -1) {
                            this.dsTB.push({
                                MA_TT: item.MA_TT,
                                MA_TB: item.MA_TB,
                                LOAIHINH_TB: item.LOAIHINH_TB,
                                TRANGTHAI_TB: item.TRANGTHAI_TB,
                                TRANGTHAITB_ID: item.TRANGTHAITB_ID,
                                DICHVUVT_ID: item.DICHVUVT_ID,
                                LOAITB_ID: item.LOAITB_ID,
                                TEN_TT: item.TEN_TT,
                                DIACHI_TT: item.DIACHI_TT,
                                MATB_DD: item.MATB_DD,
                                THUEBAO_ID: item.THUEBAO_ID,
                                LYDO_KHOA: lyDoKhoa
                            });
                        }
                    });
                }

                this.thueBao_grid.items = this.dsTB;
                this.onChangeColor(maTT, lyDoKhoa);
            } else {
                this.thanhToan_grid.items[idx].TRANGTHAI = '';
                if (this.dsTB.length <= 0)
                    return true;
                let ds = this.dsTB.slice();
                ds.forEach(item => {
                    if (this.params.kieuFile === 1 && item.MA_TT === maTT)
                        this.dsTB = this.dsTB.filter(e => e.MA_TT !== item.MA_TT);
                    if (this.params.kieuFile === 2 && item.MATB_DD === maTT)
                        this.dsTB = this.dsTB.filter(e => e.MATB_DD !== item.MATB_DD);
                });
                this.thueBao_grid.items = this.dsTB;
            }
            return true;
        },

        async onChangeColor(maTT, lyDoKhoa) {
            for (let [i, item] of this.dsTB.entries()) {
                if ((this.params.kieuFile === 1 && item.MA_TT.trim() === maTT)
                    || (this.params.kieuFile === 2 && item.MATB_DD.trim() === maTT)) {
                    if (this.thueBao_grid.items[i].MA_TB !== null && this.thueBao_grid.items[i].MA_TB !== '') {
                        let rs = await this.getKiemTraTBLapHDKhac(this.thueBao_grid.items[i].MA_TB, '0', this.thueBao_grid.items[i].DICHVUVT_ID, 7, false);
                        if (rs) {
                            if (this.params.kieuYC_Selected === KIEUKHOAMO.KHOAMAY) {
                                if (this.params.chieuKhoaMo_Selected === KieuKhoa.KHOA_MOT_CHIEU) {
                                    if (this.thueBao_grid.items[i].TRANGTHAITB_ID === TrangThaiTB.THUONG) {
                                        this.thueBao_grid.items[i].CHON = true;
                                        this.thueBao_grid.items[i].TRANGTHAI = '';
                                        this.thueBao_grid.items[i].LYDO_KHOA = lyDoKhoa;
                                    } else {
                                        this.thueBao_grid.items[i].CHON = false;
                                        this.thueBao_grid.items[i].TRANGTHAI = 'Trạng thái thuê bao không thỏa mãn';
                                    }
                                } else {
                                    if (this.thueBao_grid.items[i].TRANGTHAITB_ID === TrangThaiTB.THUONG || this.thueBao_grid.items[i].TRANGTHAITB_ID === TrangThaiTB.KHOA_MOTCHIEU_YEUCAU
                                        || this.thueBao_grid.items[i].TRANGTHAITB_ID === TrangThaiTB.KHOA_MOTCHIEU_CUONGBUC) {
                                        this.thueBao_grid.items[i].CHON = true;
                                        this.thueBao_grid.items[i].TRANGTHAI = '';
                                        this.thueBao_grid.items[i].LYDO_KHOA = lyDoKhoa;
                                    } else {
                                        this.thueBao_grid.items[i].CHON = false;
                                        this.thueBao_grid.items[i].TRANGTHAI = 'Trạng thái thuê bao không thỏa mãn';
                                    }
                                }
                            } else {    //Mở máy
                                //nếu chiều mở : 1 chiều => chỉ cho check chọn những thuê bao có trạng thái khóa 1 chiều nợ cước
                                if (this.params.chieuKhoaMo_Selected === 1) {
                                    if (this.thueBao_grid.items[i].TRANGTHAITB_ID === TrangThaiTB.KHOA_MOTCHIEU_CUONGBUC) {
                                        this.thueBao_grid.items[i].CHON = true;
                                        this.thueBao_grid.items[i].TRANGTHAI = '';
                                    } else {
                                        this.thueBao_grid.items[i].CHON = false;
                                        this.thueBao_grid.items[i].TRANGTHAI = 'Trạng thái thuê bao không thỏa mãn';
                                    }
                                } else {
                                    //nếu chiều mở : 2 chiều => chỉ cho check chọn những thuê bao có trạng thái khóa 2 chiều nợ cước
                                    if (this.thueBao_grid.items[i].TRANGTHAITB_ID === TrangThaiTB.KHOA_HAICHIEU_CUONGBUC) {
                                        this.thueBao_grid.items[i].CHON = true;
                                        this.thueBao_grid.items[i].TRANGTHAI = '';
                                    } else {
                                        this.thueBao_grid.items[i].CHON = false;
                                        this.thueBao_grid.items[i].TRANGTHAI = 'Trạng thái thuê bao không thỏa mãn';
                                    }
                                }
                            }
                        } else {
                            this.thueBao_grid.items[i].CHON = false;
                            this.thueBao_grid.items[i].TRANGTHAI = 'Trạng thái thuê bao không thỏa mãn';
                        }
                    }
                } else continue;
            }
            this.thueBao_grid.items = [...this.thueBao_grid.items];
            this.thueBao_grid.checked = this.thueBao_grid.items.filter(e => e.CHON).map(i => i.MA_TB);
        },

        async onTBCheckboxChangedHandler(args) {
            let index = this.thueBao_grid.items.findIndex(e => e.MA_TB === args.MA_TB);
            if (index !== -1) {
                //CHECKED
                if (args.CHON) {
                    this.thueBao_grid.items[index].CHON = true;
                    this.thueBao_grid.checked.push(args.MA_TB);
                    let rs = await this.onThueBaoGrid_CellContentClick(index);
                    if (!rs) {
                        let indexChecked = this.thueBao_grid.checked.findIndex(e => e === args.MA_TB);
                        if (indexChecked !== -1)
                            this.thueBao_grid.checked.splice(indexChecked, 1);
                    }
                } else {
                    //UN-CHECKED
                    this.thueBao_grid.items[index].CHON = false;
                    let indexChecked = this.thueBao_grid.checked.findIndex(e => e === args.MA_TB);
                    if (indexChecked !== -1)
                        this.thueBao_grid.checked.splice(indexChecked, 1);
                }

                //REFRESH DATASOURCE & FOOTER
                this.thueBao_grid.items = [...this.thueBao_grid.items];
                let dataGridObj = this.$refs.thueBaoGrid.$refs.grid.ej2Instances;
                dataGridObj.aggregateModule.refresh();
            }
        },

        async onThueBaoGrid_CellContentClick(idx) {
            let dichVuVT_Id = Number(this.thueBao_grid.items[idx].DICHVUVT_ID);
            let trangThaiTB_Id = Number(this.thueBao_grid.items[idx].TRANGTHAITB_ID);
            if (dichVuVT_Id !== DichVuVienThong.DI_DONG) {
                let rs = await this.getKiemTraTBLapHDKhac(this.thueBao_grid.items[idx].MA_TB, '0', this.thueBao_grid.items[idx].DICHVUVT_ID, 7, false);
                if (!rs) {
                    this.thueBao_grid.items[idx].CHON = false;
                    this.thueBao_grid.items[idx].TRANGTHAI = 'Thuê bao đã lập hợp đồng khác';
                    this.$toast.error('Thuê bao đã lập hợp đồng khác, bạn không thể thực hiện tiếp');
                    return false;
                }

                let kieuKhoa_Text = this.params.chieuKhoaMo_Options.find(e => e.id === this.params.chieuKhoaMo_Selected).text;
                if (this.params.kieuYC_Selected === KIEUKHOAMO.KHOAMAY) {
                    if (this.params.chieuKhoaMo_Selected === KieuKhoa.KHOA_MOT_CHIEU) {
                        if (trangThaiTB_Id !== TrangThaiTB.THUONG) {
                            this.thueBao_grid.items[idx].CHON = false;
                            this.thueBao_grid.items[idx].TRANGTHAI = 'Trạng thái thuê bao không thỏa mãn';
                            this.$toast.error(`Bạn đang chọn chiều khóa: ${kieuKhoa_Text}. \r\nChỉ được phép thực hiện với thuê bao trạng thái hoạt động bình thường!`);
                            return false;
                        }
                    } else {
                        if (trangThaiTB_Id !== TrangThaiTB.THUONG && trangThaiTB_Id !== TrangThaiTB.KHOA_MOTCHIEU_YEUCAU
                            && trangThaiTB_Id !== TrangThaiTB.KHOA_MOTCHIEU_CUONGBUC) {
                            this.thueBao_grid.items[idx].CHON = false;
                            this.thueBao_grid.items[idx].TRANGTHAI = 'Trạng thái thuê bao không thỏa mãn';
                            this.$toast.error(`Bạn đang chọn chiều khóa: ${kieuKhoa_Text}. \r\nChỉ được phép thực hiện với thuê bao trạng thái hoạt động bình thường hoặc khóa 1 chiều!`);
                            return false;
                        }
                    }
                } else {
                    if (dichVuVT_Id === DichVuVienThong.CO_DINH || dichVuVT_Id === DichVuVienThong.GPHONE || dichVuVT_Id === DichVuVienThong.IMS) {
                        if (this.params.chieuKhoaMo_Selected === 1) {   // mở 1 chiều
                            //nếu chiều mở : 1 chiều => chỉ cho check chọn những thuê bao có trạng thái khóa 1 chiều nợ cước
                            if (trangThaiTB_Id !== TrangThaiTB.KHOA_MOTCHIEU_CUONGBUC) {
                                this.thueBao_grid.items[idx].CHON = false;
                                this.thueBao_grid.items[idx].TRANGTHAI = 'Trạng thái thuê bao không thỏa mãn';
                                this.$toast.error(`Bạn đang chọn chiều mở: ${kieuKhoa_Text}. \r\nChỉ được phép thực hiện với thuê bao có trạng thái Khóa 1 chiều nợ cước!`);
                                return false;
                            }
                        } else {
                            //nếu chiều mở : 2 chiều => chỉ cho check chọn những thuê bao có trạng thái khóa 2 chiều nợ cước
                            if (trangThaiTB_Id !== TrangThaiTB.KHOA_HAICHIEU_CUONGBUC) {
                                this.thueBao_grid.items[idx].CHON = false;
                                this.thueBao_grid.items[idx].TRANGTHAI = 'Trạng thái thuê bao không thỏa mãn';
                                this.$toast.error(`Bạn đang chọn chiều mở: ${kieuKhoa_Text}. \r\nChỉ được phép thực hiện với thuê bao có trạng thái Khóa 2 chiều nợ cước!`);
                                return false;
                            }
                        }
                    } else {
                        if (trangThaiTB_Id !== TrangThaiTB.KHOA_HAICHIEU_CUONGBUC) {
                            this.thueBao_grid.items[idx].CHON = false;
                            this.thueBao_grid.items[idx].TRANGTHAI = 'Trạng thái thuê bao không thỏa mãn';
                            this.$toast.error(`Đối với thuê bao không phải là Cố định, Gphone, bạn chỉ được phép thực hiện với thuê bao có trạng thái khóa 2 chiều nợ cước!`);
                            return false;
                        }
                    }
                }
            }
            return true;
        },

        onTTSelected(arg) {
            this.thanhToan_grid.selected = this.thanhToan_grid.items[arg.rowIndex];
            this.params.tenTT = this.thanhToan_grid.selected.TEN_TT;
            this.params.diaChiTT = this.thanhToan_grid.selected.DIACHI_TT;
        },

        onTTQueryCellInfo(args) {
            if (args.column.field === 'TRANGTHAI') {
                if (args.data.TRANGTHAI !== '' && args.data.TRANGTHAI)
                    args.cell.classList.add('hd-khac');
                else
                    args.cell.classList.remove('hd-khac');
            }
        },

        onTBQueryCellInfo(args) {
            if (args.column.field === 'TRANGTHAI') {
                if (args.data.TRANGTHAI !== '' && args.data.TRANGTHAI)
                    args.cell.classList.add('hd-khac');
                else
                    args.cell.classList.remove('hd-khac');
            }
        },

        async getThamSoMD() {
            try {
                this.loading(true);
                this.common_params.khoaMay_LoaiNV_DiemTN = -1;
                this.common_params.khoaMay_TB_Theo_TuyenThu = -1;
                this.common_params.khoaMay_File_LyDoKhoa = -1;
                let response = await API.getAllThamSoMD(this.axios);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    let tsData = response.data.data.find(e => e.MA_TS === 'KHOAMAY_LOAINV_DIEMTN');
                    if (tsData !== undefined)
                        if (tsData.TEN_TS.trim() === '1')
                            this.common_params.khoaMay_LoaiNV_DiemTN = 1;
                    tsData = response.data.data.find(e => e.MA_TS === 'KHOAMAY_TB_THEO_TUYENTHU');
                    if (tsData !== undefined)
                        if (tsData.TEN_TS.trim() === '1')
                            this.common_params.khoaMay_TB_Theo_TuyenThu = 1;
                    tsData = response.data.data.find(e => e.MA_TS === 'KHOAMAY_FILE_LYDOKHOA');
                    if (tsData !== undefined)
                        if (tsData.TEN_TS.trim() === '1')
                            this.common_params.khoaMay_File_LyDoKhoa = 1;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getThongTinTTTheoDK() {
            try {
                this.loading(true);
                this.thongTinTT = [];
                let apiParams = {
                    pMa: this.params.maTT
                };
                let response = await API.getThongTinThanhToanTheoDK(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    this.thongTinTT = response.data.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getThongTinTBTheoDK(maTT, kieuFile) {
            try {
                this.loading(true);
                let apiParams = {
                    pMa: maTT,
                    pKieu: kieuFile
                };
                let response = await API.getThongTinThueBaoTheoDK(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0)
                    return response.data.data.map((item) => ({
                        ...item,
                        CHON: false
                    }));
                else return [];
            }
            catch (error) {
                return [];
            }
            finally {
                this.loading(false);
            }
        },

        async getDiemTinNhiemKhachHang(khachHangId) {
            try {
                this.loading(true);
                this.diemTinNhiemKH = 0;
                let apiParams = {
                    pKyCuoc: this.common_params.kyCuoc,
                    pKhachHangId: khachHangId
                };
                let response = await API.getDiemTinNhiemKhachHang(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    this.diemTinNhiemKH = Number(response.data.data[0].DIEMTINNHIEM);
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDiemTinNhiemNhanVien() {
            try {
                this.loading(true);
                this.common_params.diemTN_Tu = 0;
                this.common_params.diemTN_Den = 0;
                let apiParams = {
                    pNhanVienId: this.$auth.getNhanVienID() !== -1 ? this.$auth.getNhanVienID() : 1
                };
                let response = await API.getDiemTinNhiemNhanVien(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    this.common_params.diemTN_Tu = Number(response.data.data[0].DIEMTN_TU);
                    this.common_params.diemTN_Den = Number(response.data.data[0].DIEMTN_DEN);
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getThanhToanIdTheoMaVach() {
            try {
                this.loading(true);
                this.thanhToanId_Items = [];
                let apiParams = {
                    pKyCuoc: this.common_params.kyCuoc,
                    pMaVach: this.params.maTT
                };
                let response = await API.getThanhToanIdTheoMaVach(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    this.thanhToanId_Items = response.data.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        /* Return: true/false */
        async getKiemTraKhoaMoTBTheoTuyenThu(thanhToanId) {
            try {
                this.loading(true);
                let apiParams = {
                    pPhanVungId: this.$auth.getPhanVungID() !== -1 ? this.$auth.getPhanVungID() : 26,
                    pId: thanhToanId,
                    pNguoiDungId: this.$auth.getNguoiDungID() !== -1 ? this.$auth.getNguoiDungID() : 1,
                    pKieu: 2
                };
                let response = await API.getKiemTraKhoaMoTBTheoTuyenThu(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data === 'ok') {
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
        },

        /* true/false: OK/Mã TB đang được lập hợp đồng khác */
        async getKiemTraTBLapHDKhac(maTB, maGD, dichVuId, loaiHDId, isShowError = true) {
            try {
                this.loading(true);
                let apiParams = {
                    maTB: maTB,
                    maGD: maGD,
                    dichVuVTId: dichVuId,
                    loaiHDI: loaiHDId
                };
                let response = await API.getKiemTraTBLapHDKhac(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data === null)
                    return true;
                else return false;
            }
            catch (error) {
                if (isShowError)
                    this.$toast.error(error.data.message_detail);
                return false;
            }
            finally {
                this.loading(false);
            }
        },

        /*ds: [{ ma_tb: String, ma_tt: String, dichvuvt_id: Number, loaitb_id: Number, thuebao_id: Number, lydo_khoa: String }]*/
        async updateDanhSachKhoaMoMay(ds) {
            try {
                this.loading(true);
                let apiBody = {
                    pKieuYC: this.params.kieuYC_Selected,
                    pChieuKhoa: this.params.chieuKhoaMo_Selected,
                    pNguoiCN: this.$auth.getUserName() !== -1 ? this.$auth.getUserName() : 'admin',
                    pMayCN: this.$auth.getUserName() !== -1 ? this.$auth.getUserName() : 'chinhtn.dng',
                    pDSKhoaMay: ds
                };
                let response = await API.updateDanhSachKhoaMoMay(this.axios, apiBody);
                if (response.data.error_code === 'BSS-00000000') {
                    this.$toast.success(`Chuyển thành công danh sách ${this.thueBao_grid.checked.length} thuê bao sang hợp đồng thay đổi dịch vụ!`);
                    return true;
                } else {
                    this.$toast.error('Có lỗi trong quá trình chuyển danh sách thuê bao sang hợp đồng thay đổi dịch vụ!');
                    return false;
                }
            }
            catch (error) {
                console.log(error);
                this.$toast.error(error.data.message_detail);
                return false;
            }
            finally {
                this.loading(false);
            }
        },
    },

    watch: {
        'params.kieuYC_Selected': async function (newVal, oldVal) {
            if (newVal === 0) {
                this.params.chieuKhoaMo_Title = 'Chiều mở';
                this.params.lyDoKhoa_Title = 'Lý do mở';
                this.params.chieuKhoaMo_Options = [
                    { id: 1, text: 'Mở 1 chiều do nợ cước' },
                    { id: 2, text: 'Mở 2 chiều do nợ cước' }
                ];
            } else {
                this.params.chieuKhoaMo_Title = 'Chiều khóa';
                this.params.lyDoKhoa_Title = 'Lý do khóa';
                this.params.chieuKhoaMo_Options = [
                    { id: 1, text: 'Khóa 1 chiều do nợ cước' },
                    { id: 2, text: 'Khóa 2 chiều do nợ cước' }
                ];
            }
            this.params.chieuKhoaMo_Selected = 2
        },

        'params.maTT_Checked': async function (newVal, oldVal) {
            if (newVal) {
                this.params.maTT_Title = 'Mã vạch';
            } else {
                this.params.maTT_Title = 'Mã TT';
            }
        },

        'params.chieuKhoaMo_Selected': async function (newVal, oldVal) {
            this.thueBao_grid.items = [];
            this.dsTB = [];
        },

        checkAllMaTT: function (value) {
            this.thanhToan_grid.items = this.thanhToan_grid.items.map(item => ({ ...item, CHON: value }))
            for(let i = 0;i < this.thanhToan_grid.items.length; i++){
                this.onThanhToanGrid_CellContentClick(i)
            }
            this.thanhToan_grid.items = [...this.thanhToan_grid.items];
            this.thanhToan_grid.checked = this.thanhToan_grid.items.filter(e => e.CHON).map(i => i.MA_TT);
        },

        checkAllMaTB: function (value) {
            this.thueBao_grid.items = this.thueBao_grid.items.map(item => ({ ...item, CHON: value }))
            this.thueBao_grid.items = [...this.thueBao_grid.items];
            this.thueBao_grid.checked = this.thueBao_grid.items.filter(e => e.CHON).map(i => i.MA_TB);
        }
    }
}
</script>
