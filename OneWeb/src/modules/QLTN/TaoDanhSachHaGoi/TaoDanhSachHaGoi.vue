<template src="./TaoDanhSachHaGoi.html"></template>
<style scoped src="../TaoDanhSachNangGoi/TaoDanhSachNangGoi.scss"></style>
<script>
import { mapActions, mapGetters } from 'vuex'
import breadcrumb from '@/components/breadcrumb'
import Calculator from '@/components/Calculator.vue'
import PopupDocFileKhoaMoThueBao from '../components/PopupDocFile/PopupDocFileKhoaMo.vue'
import KyCuoc from '../components/KyCuoc'
import EventBus from '@/utils/eventBus'
import TraCuuAPI from '../api/NangHaGoiAPI.js'
import CommonsAPI from '../api/Commons.js'
import moment from 'moment'
import Vue from "vue"
import { MultiSelectPlugin } from '@syncfusion/ej2-vue-dropdowns'
import { MultiSelect, CheckBoxSelection } from '@syncfusion/ej2-dropdowns'
import { Query } from '@syncfusion/ej2-data'
import { currency } from '@/filters/currency'
import XuatFile from "../../CSKH/utils/XuatFile.vue"
import { required } from "vuelidate/lib/validators"

MultiSelect.Inject(CheckBoxSelection);
Vue.use(MultiSelectPlugin);

export default {
    components: { breadcrumb, appKyCuoc: KyCuoc, Calculator, PopupDocFileKhoaMoThueBao, appModalXuatDuLieu: XuatFile },
    name: 'TaoDanhSachHaGoi',
    async created () {
        await this.initForm();
    },

    destroyed () {
        EventBus.$off('chukycuocChange', this.chukycuocChangeHandler)
    },

    computed: {
        ...mapGetters('qltnCommon', ['danhSachDichVuVienThongGetter', 'danhsachDonViGetter',
        'danhSachKHLonGetter', 'danhSachLoaiKHGetter', 'danhMucQuanHuyenGetter']),
    },

    data() {
        return {
            header: {
                title: 'TẠO DANH SÁCH HẠ GÓI',
                list: [
                    { name: 'Xử lý thuê bao', link: { name: 'Ui.cards' } },
                    { name: 'Tạo DS hạ gói', link: { name: 'Ui.buttons' } },
                ]
            },
            thongtinKyHoaDon: {
                kyHoaDonSelected : null,
                chuKySelected : null
            },
            params: {
                donVi_Options: [],
                donVi_Selected: null,
                dichVu_Options: [],
                dichVu_Selected: null,
                loaiHinh_Options: [],
                loaiHinh_Selected: [],
                loaiHinh_Checked: false,
                hinhThuc_Options: [
                    { id: 0, text: 'Nợ đầu kỳ' },
                    { id: 5, text: 'Nợ phát sinh' },
                    { id: 1, text: 'Tổng nợ' }
                ],
                hinhThuc_Selected: 1,
                KHLon_Options: [],
                KHLon_Selected: [],
                KHLon_Checked: false,
                moMayTrongNgay: false,
                tuTien: 0,
                tuTienCalculator: false,
                denTien_Checked: false,
                denTien: 0,
                denTienCalculator: false,
                loloaiKH_Checked: false,
                loaiKH_Options: [],
                loaiKH_Selected: null,
                quan_Checked: false,
                quan_Options: [],
                quan_Selected: [],
            },

            display_params: {
                processTime: '00:00:00',
                number: 0,
                timKiemBtn_Enable: true,
                taoDSBtn_Enable: true,
                xuatFileBtn_Enable: true
            },
            haGoi_grid: {
                items: [],
                checked: [],
                key: 1
            },
            khongHaGoi_grid: {
                items: [],
                checked: [],
                key: 1
            },
            dsLapHDKhac: [],
            selectFields: { text: 'text', value: 'id' }
        }
    },

    validations: {
        params: {
            tuTien: {
                required
            },
            denTien: {
                required
            },
            KHLon_Selected: {
                required
            },
            quan_Selected: {
                required
            }
        }
    },

    methods: {
        ...mapActions('qltnCommon', ['getDanhSachDichVuVienThong', 'getDanhSachDonVi',
        'getDanhSachKHLon', 'getDanhSachLoaiKhachHang', 'getDanhMucQuanHuyen']),

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

            if (this.params.danhSachKHLonGetter !== undefined || this.danhSachKHLonGetter.length === 0) {
                await this.getDanhSachKHLon();
            }
            this.danhSachKHLonGetter.forEach(item => {
                this.params.KHLon_Options.push({ id: item.KHLON_ID, text: item.TEN_LKHL });
                this.params.KHLon_Selected.push(item.KHLON_ID);
            });

            if (this.params.danhSachLoaiKHGetter !== undefined || this.danhSachLoaiKHGetter.length === 0) {
                await this.getDanhSachLoaiKhachHang();
            }
            this.danhSachLoaiKHGetter.forEach(item => {
                this.params.loaiKH_Options.push({ id: item.LOAIKH_ID, text: item.TEN_LOAIKH })
            });
            this.params.loaiKH_Selected = this.params.loaiKH_Options[0].id;

            if (this.params.danhMucQuanHuyenGetter !== undefined || this.danhMucQuanHuyenGetter.length === 0) {
                await this.getDanhMucQuanHuyen();
            }
            this.danhMucQuanHuyenGetter.forEach(item => {
                this.params.quan_Options.push({ id: item.QUAN_ID, text: item.TEN_QUAN });
                this.params.quan_Selected.push(item.QUAN_ID);
            });
        },

        chukycuocChangeHandler(e) {
            this.thongtinKyHoaDon.kyHoaDonSelected =moment(e.p_kyhoadon).format('YYYYMM') + '01'
            this.thongtinKyHoaDon.chuKySelected = e.p_chuky_no
        },

        selectDistrictFiltering(e) {
            if (e.text == '') e.updateData(this.params.quan_Options);
            else {
                var query = new Query().select(["id", "text"]);
                query = (e.text !== '') ? query.where('text', 'contains', e.text, true) : query;
                e.updateData(this.params.quan_Options, query);
            }
        },

        selectCustomerFiltering(e) {
            if (e.text == '') e.updateData(this.params.KHLon_Options);
            else {
                var query = new Query().select(["id", "text"]);
                query = (e.text !== '') ? query.where('text', 'contains', e.text, true) : query;
                e.updateData(this.params.KHLon_Options, query);
            }
        },

        selectTypeFiltering(e) {
            if (e.text == '') e.updateData(this.params.loaiHinh_Options);
            else {
                var query = new Query().select(["id", "text"]);
                query = (e.text !== '') ? query.where('text', 'contains', e.text, true) : query;
                e.updateData(this.params.loaiHinh_Options, query);
            }
        },

        validateData() {
            this.$v.$touch();
            let errorMessage = [];

            if (!this.$v.params.tuTien.required) {
                errorMessage.push("Từ tiền không được để trống!");
                this.$refs.tuTien.focus();
            }

            if (this.params.denTien_Checked && !this.$v.params.tuTien.required)
                errorMessage.push("Đến tiền không được để trống!");

            if (!this.$v.params.KHLon_Selected.required)
                errorMessage.push("Chưa chọn loại khách hàng lớn!");

            if (!this.$v.params.quan_Selected.required)
                errorMessage.push("Chưa chọn quận!");

            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join("\n"));
                return false;
            } else return true;
        },

        onDSHaQueryCellInfo(args) {
            if (args.column.field === 'MA_TT') {
                if (args.data.TRANGTHAI === 1) {
                    args.cell.classList.add('hd-khac');
                    args.cell.setAttribute('title', 'Thuê bao đang lập hợp đồng/phụ lục khác. Hãy tra cứu chi tiết thi công để biết thêm chi tiết');
                } else {
                    args.cell.classList.remove('hd-khac');
                }
            }
        },

        async onSearch() {
            if (this.validateData()) {
                let message = `Bạn thật sự muốn tạo dữ liệu hạ gói với số tiền nợ >= ${currency(this.params.tuTien)}`;
                message += (this.params.denTien_Checked) ? ` và <= ${currency(this.params.denTien)}?` : `?`;
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
                            this.clear();
                            let beginTime = new Date();
                            await this.getDSHaGoi();
                            if (this.haGoi_grid.items.length <= 0) {
                                this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                                this.display_params.taoDSBtn_Enable = false;
                            } else this.display_params.taoDSBtn_Enable = true;

                            let timeSpan = new Date() - beginTime;
                            let seconds = timeSpan / 1000;
                            let hours = parseInt( seconds / 3600 );
                            seconds = seconds % 3600;
                            let minutes = parseInt( seconds / 60 );
                            seconds = Math.round(seconds % 60);
                            this.display_params.processTime = hours.toString().padStart(2, '0') + ':' + minutes.toString().padStart(2, '0') + ':' + seconds.toString().padStart(2, '0');
                            this.display_params.number = this.haGoi_grid.items.length;
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

        clear() {
            this.haGoi_grid.items = [];
            this.khongHaGoi_grid.items = [];
            this.display_params.processTime = '00:00:00';
            this.display_params.number = 0;
            this.$refs.tuTien.focus();
        },

        async kiemTraTB() {
            await this.getDSLapHopDongKhacHaGoi();
            let isHDKhac = false;
            if (this.dsLapHDKhac.length > 0) {
                this.haGoi_grid.items.forEach(item => {
                    if (this.dsLapHDKhac.findIndex(e => e.THUEBAO_ID === item.THUEBAO_ID) !== -1) {
                        item.TRANGTHAI = 1;
                        isHDKhac = true;
                    }
                });
                if (isHDKhac)
                    this.$toast.error("Có thuê bao đang lập hợp đồng/phụ lục khác!");
            }
            return isHDKhac;
        },

        async onUpdate() {
            if (this.haGoi_grid.items.length <= 0)
                return this.$toast.error('Chưa có thuê bao trên lưới!');

            let rs = await this.kiemTraTB();
            if (rs) return;
            else {
                try {
                    await this.$bvModal.msgBoxConfirm(`Bạn thật sự muốn tạo danh sách hạ gói?`, {
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
                            let dsHaGoi = [];
                            this.haGoi_grid.items.forEach(item => {
                                let ds = {
                                    dichVuVTId: item.DICHVUVT_ID,
                                    maTB: item.MA_TB
                                };
                                dsHaGoi.push(ds);
                            });

                            let result = await this.updateChuyenDSHaTocDoSangTDTD(dsHaGoi);
                            if (result)
                                this.$toast.success('Tạo danh sách hạ gói thành công!');
                        }
                    })
                    .catch(err => {
                        // An error occurred
                        console.log(error)
                    })
                } catch (error) {
                    this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
                    console.log(error)
                }
            }
        },

        onSync() {
            this.$router.push({
                name: 'HoanThienThayDoiTocDo',
                params: {
                    tag: '1+8+4',
                    //Quy trình: 143 - Hạ tốc độ duy trì
                    quyTrinh_Id: 143,
                    //Kiểu lắp đặt: 14 - Hạ tốc độ duy trì; 15 - Nâng tốc độ
                    kieuLD_Id: 14,
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
            if (this.haGoi_grid.items.length <= 0)
                this.$toast.error('Không có dữ liệu thuê bao hạ gói để thực hiện xuất file!');
            else
                this.$bvModal.show("modal-xuat-file");
        },

        openDialog() {
            this.$refs.dlgDocFileKhoaMo.openDialog();
        },

        async onReadFile(items) {
            this.clear();
            if (items.length > 0) {
                let dsHaGoi = [];
                items.forEach(item => {
                    let ds = {
                            dichVuVTId: this.params.dichVu_Selected,
                            maTB: item.MA_TB
                        };
                    dsHaGoi.push(ds);
                })
                await this.getDSHaGoiTheoFile(dsHaGoi);

                if (this.haGoi_grid.items.length <= 0) {
                    this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                    this.display_params.taoDSBtn_Enable = false;
                } else this.display_params.taoDSBtn_Enable = true;
            }
        },

        onRight() {
            let arr = [];
            this.haGoi_grid.checked.map((val) => {
                let idx = this.haGoi_grid.items.findIndex((v) => v.MA_TB === val);
                if (idx >= 0) {
                    arr.push(this.haGoi_grid.items[idx]);
                    this.haGoi_grid.items = this.haGoi_grid.items.filter((v) => v.MA_TB !== val);
                }
            });
            this.khongHaGoi_grid.items = this.khongHaGoi_grid.items.concat(arr);
            this.haGoi_grid.checked = [];
            this.haGoi_grid.key++;
            this.khongHaGoi_grid.key++;
        },

        onDoubleRight() {
            this.khongHaGoi_grid.items = this.khongHaGoi_grid.items.concat(this.haGoi_grid.items);
            this.haGoi_grid.items = [];
            this.haGoi_grid.checked = [];
            this.haGoi_grid.key++;
            this.khongHaGoi_grid.key++;
        },

        onLeft() {
            let arr = [];
            this.khongHaGoi_grid.checked.map((val) => {
                let idx = this.khongHaGoi_grid.items.findIndex((v) => v.MA_TB === val);
                if (idx >= 0) {
                    arr.push(this.khongHaGoi_grid.items[idx]);
                    this.khongHaGoi_grid.items = this.khongHaGoi_grid.items.filter((v) => v.MA_TB !== val);
                }
            });
            this.haGoi_grid.items = this.haGoi_grid.items.concat(arr);
            this.khongHaGoi_grid.checked = [];
            this.haGoi_grid.key++;
            this.khongHaGoi_grid.key++;
        },

        onDoubleLeft() {
            this.haGoi_grid.items = this.haGoi_grid.items.concat(this.khongHaGoi_grid.items);
            this.khongHaGoi_grid.items = [];
            this.khongHaGoi_grid.checked = [];
            this.haGoi_grid.key++;
            this.khongHaGoi_grid.key++;
        },

        onHaGoiChecked(items) {
            this.haGoi_grid.checked = items;
        },

        onKhongHaGoiChecked(items) {
            this.khongHaGoi_grid.checked = items;
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

        async getDSHaGoi() {
            try {
                this.loading(true);
                this.haGoi_grid.key++;
                this.haGoi_grid.items = [];
                let apiParams = {
                    denTien: this.params.denTien_Checked ? this.params.denTien : null,
                    dichVuVTId: this.params.dichVu_Selected,
                    donViId: this.params.donVi_Selected,
                    kieuHaGia: this.params.hinhThuc_Selected,
                    kyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
                    loaiKHId: this.params.loaiKH_Checked ? this.params.loaiKH_Selected : null,
                    moMay: this.params.moMayTrongNgay,
                    tuTien: this.params.tuTien,
                    dsKHLon: this.params.KHLon_Checked ? this.params.KHLon_Selected : null,
                    dsLoaiTB: this.params.loaiHinh_Checked ? this.params.loaiHinh_Selected : null,
                    dsQuan: this.params.quan_Checked ? this.params.quan_Selected : null
                };
                let response = await TraCuuAPI.getDSHaGoi(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                   this.haGoi_grid.items = response.data.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDSLapHopDongKhacHaGoi() {
            try {
                this.loading(true);
                this.dsLapHDKhac = [];
                let apiParams = {
                    kieuYC: 1,
                    loaiTBId: this.params.loaiHinh_Checked ? this.params.loaiHinh_Selected : ''
                };
                let response = await TraCuuAPI.getDSLapHopDongKhacHaGoi(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                   this.dsLapHDKhac = response.data.data;
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
        async getDSHaGoiTheoFile(ds) {
            try {
                this.loading(true);
                this.haGoi_grid.key++;
                this.haGoi_grid.items = [];
                let apiBody = {
                    dichVuVTId: this.params.dichVu_Selected,
                    donViId: this.params.donVi_Selected,
                    kyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
                    loaiTBId: 0,
                    dsHaTocDo: ds
                };
                let response = await TraCuuAPI.getDSHaGoiTheoFile(this.axios, apiBody);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                   this.haGoi_grid.items = response.data.data;
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

        /* dsHaTocDo: [{ dichVuVTId: Number, maTB: String }] */
        async updateChuyenDSHaTocDoSangTDTD(ds) {
            try {
                this.loading(true);
                let apiBody = {
                    dichVuVTId: this.params.dichVu_Selected,
                    donViId: this.params.donVi_Selected,
                    dsHaTocDo: ds,
                    loaiTBTKId: this.params.loaiHinh_Checked ? this.params.loaiHinh_Selected : null,
                    nhanVienId: this.$auth.getNhanVienID() !== -1 ? this.$auth.getNhanVienID() : 0,
                };
                let response = await TraCuuAPI.updateChuyenDSHaTocDoSangTDTD(this.axios, apiBody);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                   return true;
                } else {
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
        }
    },

    watch: {
    }
}
</script>
