<template src="./index.template.html"></template>
<style scoped src="./style.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb"
import TDAPI from '../api/ThayDoiKenhThuAPI.js'
import { mapActions, mapGetters } from "vuex"
import PopupDSHopDongCanThuTien from './components/PopupDSHopDongCanThuTien.vue'
import { convertMoney } from "@/utils/convertMoney"
import { required } from "vuelidate/lib/validators"
import BssErrorMarker from '@/components/BssErrorMarker'
import moment from 'moment'

export default {
    components: { breadcrumb, PopupDSHopDongCanThuTien, BssErrorMarker },

    name: "ThayDoiKenhThu",

    props: {
        maGD: {
            type: String,
            default: ''
        },
    },

    async created() {
        if (this.danhSachNganHangGetter === undefined || this.danhSachNganHangGetter.length === 0) {
            await this.getDanhSachNganHang();
        }
        this.danhSachNganHangGetter.forEach(item => {
            this.params.nganHang_Options.push({ id: item.NGANHANG_ID, text: item.TEN_NH });
        });
        this.params.nganHang_Selected = this.params.nganHang_Options[0];

        await this.getDanhMucHinhThucTra();
        //await this.getDanhMucKenhThu();
    },

    mounted() {
        this.$refs.maGD.focus();
    },

    computed: {
        ...mapGetters('qltnCommon', [
            'danhSachNganHangGetter'
        ]),
        ma_gd() {
            this.params.maGD = this.maGD;
            return this.maGD;
        }
    },

    data() {
        return {
            header: {
                title: 'THAY ĐỔI KÊNH THU',
                list: [
                    { name: 'Hóa đơn', link: { name: 'Ui.cards' } },
                    { name: 'Thay đổi kênh thu', link: { name: 'Ui.buttons' } },
                ]
            },
            params: {
                today: new Date(),
                ngayYeuCau: new Date(),
                ngayNH: new Date(),
                nganHang_Options: [],
                nganHang_Selected: null,
                nganHang_Checked: false,
                hinhThucTra_Options: [],
                hinhThucTra_Selected: null,
                hinhThucTraCu_Selected: null,
                trangThaiCu_Options: [
                    { id: 0, text: 'Chưa thu tiền' },
                    { id: 1, text: 'Đã thu tiền' }
                ],
                trangThaiCu_Selected: 0,
                kenhThu_Options: [],
                kenhThu_Selected: null,
                kenhThuCu_Selected: null,
                tongTienTra: 0,
                bangChu: 'Không đồng',
                loaiHD: '',
                tenKH: '',
                diaChiKH: '',
                nguoiDD: '',
                dienThoai: '',
                nguoiBL: '',
                chungTu: '',
                chungTu_Enable: false,
                ghiChu: '',
                maGD: ''
            },

            common_params: {
                loaiHD_Id: 0,
                HDKH_Id: 0,
                phieuTT_Id: 0
            },

            phieuTT_Items: [],
            hopDong_Items: [],
            HDTB_grid: {
                items: [],
                checked: []
            }
        }
    },

    validations: {
        params: {
            kenhThu_Selected: {
                required
            }
        }
    },

    methods: {
        ...mapActions('qltnCommon', [
            'getDanhSachNganHang'
        ]),

        onThueBaoChecked(e) {
            this.HDTB_grid.checked = e;
        },

        validateData() {
            this.$v.$touch();
            let errorMessage = [];

            if (!this.$v.params.kenhThu_Selected.required)
                errorMessage.push("Bạn chưa chọn kênh thu!");
            if (this.params.nganHang_Checked && this.params.ngayNH === null)
                errorMessage.push("Ngày ngân hàng không đúng định dạng!");
            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join("\n"));
                return false;
            } else return true;
        },

        async onMaGiaoDich(e) {
            if (e !== null && e !== '') {
                this.params.maGD = e;
                await this.onKeyPress();
            }
        },

        async onKeyPress() {
            this.params.maGD = this.params.maGD.trim();
            if (this.params.maGD !== '') {
                await this.getDanhSachHopDongTheoMaGD();

                if (this.hopDong_Items.length <= 0) {
                    this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                    this.clearData();
                } else {
                    this.parseHDData(this.hopDong_Items[0]);
                }
            }
        },

        string2Date(str) {
            let a = str.split(' ');
            let s = a[0].split('/');
            if (s.length === 3)
                return new Date(s[2], Number(s[1]) - 1, s[0]);
            else
                this.$toast.error('Không đúng định dạng ngày tháng năm!');
        },

        async parseHDData(item) {
            this.common_params.HDKH_Id = Number(item.HDKH_ID);
            this.common_params.loaiHD_Id = Number(item.LOAIHD_ID);
            if (item.CTV_ID !== undefined)
                this.params.nguoiBL = await this.mapTenNV(item.CTV_ID);
            this.params.maGD = item.MA_GD;
            this.params.tenKH = item.TEN_KH;
            this.params.diaChiKH = item.DIACHI_KH;
            this.params.nguoiDD = item.NGUOI_DD;
            this.params.dienThoai = item.SO_DT;
            this.params.ngayYeuCau = this.string2Date(item.NGAY_YC)
            this.params.loaiHD = item.TEN_LOAIHD;
            this.params.nganHang_Selected = 0;
            this.params.chungTu = '';
            await this.getHopDongKhachHang(this.common_params.HDKH_Id);
            if (this.phieuTT_Items.length > 0) {
                this.common_params.phieuTT_Id = Number(this.phieuTT_Items[0].PHIEUTT_ID);
                this.params.hinhThucTraCu_Selected = this.phieuTT_Items[0].HT_TRA_ID;
                this.params.kenhThuCu_Selected = this.phieuTT_Items[0].KENHTHU_ID;
                this.params.trangThaiCu_Selected = this.phieuTT_Items[0].TRANGTHAI;
                this.params.chungTu = this.phieuTT_Items[0].SO_CT;
                this.params.ghiChu = this.phieuTT_Items[0].GHICHU;
                if (this.phieuTT_Items[0].NGANHANG_ID !== '') {
                    this.params.nganHang_Selected = this.phieuTT_Items[0].NGANHANG_ID;
                    this.params.nganHang_Checked = true;
                } else {
                    this.params.nganHang_Selected = 0;
                    this.params.nganHang_Checked = false;
                }
                if (this.phieuTT_Items[0].NGAY_NH !== null)
                    this.params.ngayNH = this.string2Date(this.phieuTT_Items[0].NGAY_NH)
                else
                    this.params.ngayNH = new Date();

                await this.parseHDTBData();
            } else {
                this.clearData();
                this.$toast.error('Không tìm thấy thông tin phiếu thanh toán!');
            }
        },

        async parseHDTBData() {
            let tongTien = 0;
            await this.getTienHDTBTheoHDKH(this.common_params.HDKH_Id);
            if (this.HDTB_grid.items.length > 0) {
                this.HDTB_grid.items.forEach(item => {
                    let tien = Number(item.TIEN_GOC.replace(new RegExp(',', 'g'), ''));
                    let tienKM = Number(item.TIEN_KMAI.replace(new RegExp(',', 'g'), ''));
                    if (item.TRANGTHAI_HD !== 'Thoái trả')
                        tongTien += tien + tienKM;
                });
                this.common_params.HDKH_Id = this.HDTB_grid.items[0].HDTB_ID;
            }
            this.params.tongTienTra = tongTien;
        },

        clearData() {
            this.common_params.HDKH_Id = 0;
            this.common_params.phieuTT_Id = 0;
            this.phieuTT_Items = [];
            this.params.maGD = '';
            this.params.tenKH = '';
            this.params.diaChiKH = '';
            this.params.loaiHD = '';
            this.params.nguoiDD = '';
            this.params.dienThoai = '';
            this.params.chungTu = '';
            this.params.ngayYeuCau = new Date();
            this.HDTB_grid.items = [];
            this.params.nganHang_Selected = 0;
            this.params.kenhThu_Selected = 0;
        },

        async onUpdate() {
            if (!this.validateData())
                return;
            if (this.params.trangThaiCu_Selected === 1)
                return this.$toast.error("Phiếu đã được thanh toán tiền. Bạn không thể sửa!");
            if (this.common_params.phieuTT_Id === 0)
                return this.$toast.error("Không tìm thấy phiếu thanh toán!");
            if (this.common_params.HDKH_Id === 0)
                return this.$toast.error("Không tìm thấy hợp đồng khách hàng!");
            try {
                await this.$bvModal.msgBoxConfirm('Bạn có muốn thay đổi kênh thu không?', {
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
                            let rs = await this.updateThayDoiKenhThu();
                            if (rs)
                                this.$toast.success("Thay đổi kênh thu thành công!")
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

        openPopup() {
            this.$refs.dlgDSHopDongCanThuTien.openDialog();
        },

        async mapTenNV(id) {
            try {
                this.loading(true);
                let apiBody = {
                    tenTruong: "ten_nv",
                    schema: "admin",
                    tenBang: "NHANVIEN",
                    dieuKien: `nhanvien_id = ${id}`
                };
                let response = await TDAPI.postMapID(this.axios, apiBody);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    return response.data.data[0].KETQUA;
                } else return ''
            }
            catch (error) {
                return ''
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDanhMucHinhThucTra() {
            try {
                this.loading(true);
                this.params.hinhThucTra_Options = [];
                let response = await TDAPI.getDanhMucHinhThucTra(this.axios);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    response.data.data.forEach(item => {
                        this.params.hinhThucTra_Options.push({ id: item.HT_TRA_ID, text: item.HT_TRA });
                    });
                    this.params.hinhThucTra_Selected = this.params.hinhThucTra_Options[0].id;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDanhMucKenhThu() {
            try {
                this.loading(true);
                this.params.kenhThu_Options = [];
                let response = await TDAPI.getDanhMucKenhThu(this.axios);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    response.data.data.forEach(item => {
                        this.params.kenhThu_Options.push({ id: item.KENHTHU_ID, text: item.KENHTHU });
                    });
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },
        async getDanhMucKenhThu2(hTTTId) {
            try {
                this.loading(true);
                this.params.kenhThu_Options = [];
                let response = await TDAPI.getDanhMucKenhThu2(this.axios, hTTTId);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    response.data.data.forEach(item => {
                        this.params.kenhThu_Options.push({ id: item.KENHTHU_ID, text: item.KENHTHU });
                    });
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDanhSachHopDongTheoMaGD() {
            try {
                this.loading(true);
                this.hopDong_Items = [];
                let apiParams = {
                    donViDLId: 0,
                    donViId: 0,
                    kieu: 2,
                    loaiHDId: 0,
                    maGD: this.params.maGD,
                    nhanVienId: 0
                };
                let response = await TDAPI.getDanhSachHopDongTheoMaGD(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    this.hopDong_Items = response.data.data;
                }
            }
            catch (error) {
                // this.$toast.error(error.data.message_detail);                
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getHopDongKhachHang(id) {
            try {
                this.loading(true);
                this.phieuTT_Items = [];
                let response = await TDAPI.getHopDongKhachHang(this.axios, id);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    this.phieuTT_Items = response.data.data;
                }
            }
            catch (error) {
                // this.$toast.error(error.data.message_detail);                
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getTienHDTBTheoHDKH(id) {
            try {
                this.loading(true);
                this.HDTB_grid.items = [];
                let apiParams = {
                    dungThu: 0,
                    tthdId: 0,
                    hdkhId: id
                };
                let response = await TDAPI.getTienHDTBTheoHDKH(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    this.HDTB_grid.items = response.data.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async updateThayDoiKenhThu() {
            try {
                this.loading(true);
                let apiBody = {
                    phieuTTId: this.common_params.phieuTT_Id,
                    htttMoiId: this.params.hinhThucTra_Selected,
                    kenhThuMoiId: this.params.kenhThu_Selected,
                    trangThaiTTId: this.params.trangThaiCu_Selected,
                    ghiChu: this.params.ghiChu,
                    nguoiBaoLanh: this.params.nguoiBL,
                    nganHangId: this.params.nganHang_Checked ? this.params.nganHang_Selected : null,
                    ngayNganHang: this.params.nganHang_Checked ? moment(this.params.ngayNH).format('DD/MM/YYYY') : null,
                    chungTu: this.params.nganHang_Checked ? this.params.chungTu : null
                };
                let response = await TDAPI.updateThayDoiKenhThu(this.axios, apiBody);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    return true;
                } else return false;
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
        'params.hinhThucTra_Selected': async function (newVal, oldVal) {
            //Hình thức trả: 4 - UY_NHIEM_CHI; 5 - UY_NHIEM_THU       
            await this.getDanhMucKenhThu2(newVal);
            this.params.kenhThu_Selected = this.params.kenhThu_Options[0].id;

            if (Number(newVal) === 4 || Number(newVal) === 5) {
                this.params.nganHang_Checked = true;
                this.params.chungTu_Enable = this.params.trangThaiCu_Selected === 0 ? false : true;
            } else {
                this.params.nganHang_Checked = false;
                this.params.chungTu_Enable = false;
            }
        },

        'params.kenhThu_Selected': async function (newVal, oldVal) {
            //Kênh thu: 5 - CHUYEN_KHOAN
            //Hình thức trả: 1 - TIEN_MAT; 2 - TIEN_SEC
            let kt = Number(newVal);
            //this.params.hinhThucTra_Selected = kt === 5 ? 2 : 1;
        },

        'params.kenhThuCu_Selected': async function (newVal, oldVal) {
            //Kênh thu: 1 - TAI_GIAO_DICH; 2 - BUU_CUC; 3 - KHONG_THU_TIEN
            let kt = Number(newVal);
            if (kt === 1 || kt === 3 || kt === 6)
                this.params.trangThaiCu_Selected = 1;
            else this.params.trangThaiCu_Selected = 0;
        },

        'params.nganHang_Checked': async function (newVal, oldVal) {
            this.params.chungTu = '';
            this.params.chungTu_Enable = newVal;
        },

        'params.tongTienTra': async function (newVal, oldVal) {
            if (newVal !== null) {
                //Loại hợp đồng: 32 - THAYDOI_DATCOC
                if (this.common_params.loaiHD_Id !== 32 && this.params.tongTienTra < 0) {
                    this.params.bangChu = '';
                    return this.$toast.error('Tổng tiền trả nhỏ hơn 0. Bạn hãy kiểm tra lại!');
                }
                this.params.bangChu = convertMoney(newVal);
            } else {
                this.params.tongTienTra = 0;
                this.params.bangChu = 'Không đồng'
            }
        }
    }
}
</script>
