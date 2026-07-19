<template src="./TaoDanhSachThanhLyThueBaoTamDung.html"></template>
<style src="./TaoDanhSachThanhLyThueBaoTamDung.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import { mapActions, mapState, mapGetters } from "vuex";
import KyCuoc from "../components/KyCuoc.vue";
import xlsx from "xlsx";
import PopupDocFilePhieuThanhLy from "./popup-docfile.vue";
import PopupGiaoPhieu from '../XLTBGiaoPhieu'
import KhoaMayAcc from '../api/KhoaMayTheoSoMayAcc.js'
import LyDoHuyModal from "../CapNhatKetQuaKhoaMayNoCuoc/components/LyDoHuy";

import { CheckBoxSelection } from '@syncfusion/ej2-vue-dropdowns'
import { L10n } from '@syncfusion/ej2-base'

const baseUrl = process.env.API;

L10n.load({
    'vi-VN': {
        'multi-select': {
            noRecordsTemplate: 'Không có dữ liệu',
            overflowCountTemplate: 'và ${count} giá trị khác..',
            totalCountTemplate: '${count} giá trị'
        }
    }
})
export default {
    name: "TaoDanhSachThanhLyThueBaoTamDung",
    components: {
        breadcrumb,
        appKyCuoc: KyCuoc,
        PopupDocFilePhieuThanhLy,
        PopupGiaoPhieu,
        LyDoHuyModal,
    },
    provide: {
        multiselect: [CheckBoxSelection]
    },
    async mounted() { },
    data() {
        return {
            header: {
                title: "Tạo danh sách thanh lý thuê bao tạm dừng",
                list: [
                    {
                        name: "Xử lý thuê bao",
                        link: { name: "Ui.buttons" }
                    },
                    {
                        name: "Tạo danh sách thanh lý thuê bao tạm dừng",
                        link: { name: "Ui.buttons" }
                    }
                ]
            },
            config: {
                dateConfig: {
                    value: new Date(),
                    format: "dd/MM/y",
                    max: new Date()
                }
            },
            fieldsKHLon: {
                text: "TEN_LKHL",
                value: "KHLON_ID"
            },
            fieldsLoaiHinh: {
                text: "TEN_LOAIHINH",
                value: "LOAITB_ID"
            },
            listDichVu: [],
            listTamDung: [],
            listDonVi: [],
            listTrungTamVT: [],
            listLoaiKH: [],
            listThanhLy: [],
            pKyCuoc: "",
            checkLoaiHinh: false,
            checkTrungTamVT: false,
            checkKieuTamDung: false,
            checkLoaiKH: false,
            checkKHLon: false,
            tuNgay: new Date(),
            denNgay: new Date(),
            quytrinh_id: null,
            selectedDonVi: -1,
            selectedDichVu: null,
            selectedLoaiHinh: [],
            selectedTrungTamVT: -1,
            selectedKieuTamDung: null,
            selectedLoaiKhachHang: -1,


            btnLyDoHuyVisibled: true,
            loaiTbId: 0,
            dsLyDoHuyID: [],
            selectedKHLon: [],
            ghiChu: "",
            lyDoCat: "",
            selectedTaoHD: "1",
            listIdMaTT: [],
            dichVu: { id: "", name: "" },
            fieldDsThanhLy: [
                {
                    fieldName: "ISCHECK",
                    headerText: "",
                    allowSorting: false,
                    allowFiltering: false,
                    textAlign: "Center",
                    width: 40,
                    headerTemplate: () => { return this.columnsCheckboxAll() },
                    template: () => { return this.columnsCheckbox() }
                },
                {
                    fieldName: "MA_TB",
                    headerText: "Số máy/Acc",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "LOAIHINH_TB",
                    headerText: "Loại hình",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "NGAY_TD",
                    headerText: "Ngày TD",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "MA_TT",
                    headerText: "Mã TT",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TEN_TB",
                    headerText: "Tên thuê bao",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "DIACHI_LD",
                    headerText: "Địa chỉ LĐ",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "MA_TUYEN",
                    headerText: "Tuyến thu",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TRANGTHAI_TB",
                    headerText: "Trạng thái TB",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TRANGTHAI_DB",
                    headerText: "Trạng thái",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "NODK",
                    headerText: "Nợ ĐK",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "NOPS",
                    headerText: "Nợ PS",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TONGNO",
                    headerText: "Tổng nợ",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "DIACHI_CT",
                    headerText: "Loại",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "DIACHI_CT",
                    headerText: "Chủ quản",
                    allowFiltering: true,
                    allowSorting: true
                }
            ],
            dsGiaoPhieu: {
                luong_id: 0,
                huonggiao_id: 0,
                ma_gd: '',
                kieu_id: null,
                hdtb_id: null,
                ttph: null,
                dichvuvt_id: null,
                ngaygiao: '',
                giaoviec_nvql_cap: false,
                giaophieu_gd_pbh: false,
            },
            checkAll: false,
            columnsCheckboxAll: function () {
                return {
                    template: {
                        template: `
                                <div class="check-action">
                                    <input type="checkbox" v-model="parent.checkAll" class="uncheck"/>
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
            columnsCheckbox: function () {
                return {
                    template: {
                        template: `
                                <div class="check-action">
                                    <input
                                        type="checkbox"
                                        class="uncheck"
                                        v-model="parent.listIdMaTT"
                                        :value="data.THUEBAO_ID"/>
                                    <span class="name"></span>
                                </div>
                                `,
                        data() {
                            return {
                                data: {},
                            };
                        },
                        computed: {
                            parent() {
                                return this.$parent.$parent.$parent;
                            },
                        },
                    },
                };
            },
        };
    },
    updated() {
        // this.$nextTick(function () {
        //     const remain = this.$el.getElementsByClassName("e-remain");
        //     const values = this.$el.getElementsByClassName(
        //         "e-delim-view e-delim-values"
        //     );
        //     const listTB = this.listKHLonGetter.filter(itemNewVal =>
        //         this.selectedKHLon.some(
        //             itemOldVal => itemNewVal.KHLON_ID === itemOldVal
        //         )
        //     );
        //     // console.log({ value: values[1].innerText });
        //     // console.log({ remain: remain[1].innerText });
        //     if (values[1].innerText === remain[1].innerText) {
        //         remain[1].innerHTML = listTB[0].TEN_LKHL + ",...";
        //     } else {
        //         values[1].innerHTML = listTB[0].TEN_LKHL + ",...";
        //     }
        // });
    },
    mounted() { },
    computed: {
        ...mapGetters("TaoDanhSachThanhLyThueBaoTamDung", [
            "listDonViGetter",
            "listDichVuVTGetter",
            "listLoaiHinhTBGetter",
            "listTrungTamVTGetter",
            "listLoaiKHGetter",
            "listKHLonGetter",
            "listThanhLyThueBaoGetter",
            "listDMKieuTamDungGetter"
        ])
    },
    watch: {
        selectedDichVu: {
            handler: async function (value) {
                var result = this.listDichVuVTGetter.filter(item => {
                    return item.DICHVUVT_ID == value;
                });
                this.dichVu = {
                    id: result[0].DICHVUVT_ID,
                    name: result[0].TEN_DVVT
                };
                await this.getLoaiHinhTB(value);
                await this.getDMKieuTamDung({
                    pPhanVungId: this.$auth.getPhanVungID(),
                    pDichVuVTId: value
                });
                this.makeTamDungList();
            }
        },
        selectedTaoHD: {
            handler: async function (value) {
                //console.log("value", value);
                if (value == 1) {
                    this.taoDanhSachThanhLy();
                } else {
                    this.getDanhSachThanhLy();
                }
            }
        },
        listDonViGetter: function (newValue) {
            // this.selectedDonVi = newValue.length > 0 && newValue[0].DONVI_ID;
        },
        listDichVuVTGetter: function (newValue) {
            this.selectedDichVu =
                newValue.length > 0 && newValue[0].DICHVUVT_ID;
            this.getLoaiHinhTB(newValue[0].DICHVUVT_ID);
            this.dichVu = {
                id: newValue[0].DICHVUVT_ID,
                name: newValue[0].TEN_DVVT
            };
            //console.log({ idchvu: this.dichVu });
        },
        listLoaiHinhTBGetter: function (newValue) {
            if (newValue.length > 0) {
                this.selectedLoaiHinh = newValue.map(item => item.LOAITB_ID);
            }
        },
        listTrungTamVTGetter: function (newValue) {
            //this.selectedTrungTamVT =
            //    newValue.length > 0 && newValue[0].DONVI_ID;
        },
        listDMKieuTamDungGetter: function (newValue) {
            // if (newValue.length > 0) {
            //     this.selectedKieuTamDung = newValue[0].DICHVUGT_ID;
            // }
        },
        listLoaiKHGetter: function (newValue) {
            // this.selectedLoaiKhachHang =
            //    newValue.length > 0 && newValue[0].LOAIKH_ID;
        },
        listKHLonGetter: function (newValue) {
            this.selectedKHLon = newValue.map(item => item.KHLON_ID);
        },
        listThanhLyThueBaoGetter: function (newValue) { },
        listIdMaTT: function (value) {
            this.listIdMaTT = value
        },
        checkAll: function (value) {
            this.listIdMaTT = [];
            if (value) {
                for (const e of this.listThanhLy) {
                    this.listIdMaTT.push(e.THUEBAO_ID);
                }
            }
        },
    },
    methods: {
        ...mapActions("TaoDanhSachThanhLyThueBaoTamDung", [
            "getDonVi",
            "getDichVuVT",
            "getLoaiHinhTB",
            "getTrungTamVT",
            "getDanhSachLoaiKhachHang",
            "getDanhSachKHLon",
            "getDanhSachThanhLyThueBao",
            "getDMKieuTamDung",
            "setDanhSachThanhLyThueBao",
            "taoPhieuThanhLy",
            "taoDanhSachThanhLyThueBao",
            "xoaThueBao",
            "xoaHopDong",
            "clearData"
        ]),

        handleKyCuoc(e) {
            this.pKyCuoc = moment(e.p_kyhoadon).format("yyyyMM") + e.p_chuky_no;
        },

        async fetchData() {
            await this.getList();
        },

        async getList() {
            this.loading(true);
            //  let data = {
            //     donViId: this.$auth.getDonViID(),
            //     nhanVienId: this.$auth.getNhanVienID()
            // };
            try {
                await Promise.all([
                    this.getDonVi(),
                    this.getDichVuVT(),
                    this.getTrungTamVT(),
                    this.getDanhSachLoaiKhachHang(-1),
                    this.getDanhSachKHLon()
                ]);
            } catch (e) {
                console.log(e);
                // this.$root.toastError(e.data.message);
            } finally {
                this.loading(false);
            }
        },

        onPhieuThanhToanChange(dataItem) {
            //console.log(dataItem);
            this.listIdMaTT = dataItem;
        },

        onPhieuThanhToanRowChange(dataItem) {
            //console.log(dataItem);
            this.quytrinh_id = dataItem.QUYTRINH_ID;
            //this.dsGiaoPhieu.ma_gd=dataItem.MA_GD;
        },

        // handleChangeDichVu(e) {
        //     var id = e.target.value;
        //     var name = e.target.options[e.target.options.selectedIndex].text;
        //     this.dichVu = { id: id, name: name };
        //     console.log("id ", this.dichVu);
        // },

        checkNull(data) {
            if (data == -1) {
                return null;
            } else {
                return data;
            }
        },

        makeTamDungList() {
            this.listTamDung = [];
            if (
                this.listDMKieuTamDungGetter !== undefined &&
                this.listDMKieuTamDungGetter.length > 0
            ) {
                this.listDMKieuTamDungGetter.forEach(item => {
                    this.listTamDung.push({
                        id: item.DICHVUGT_ID,
                        text: item.TEN_DVGT
                    });
                });
                this.listTamDung.sort((a, b) => a.text.localeCompare(b.text));
                this.selectedKieuTamDung = this.listTamDung[0].id;
            }
        },

        makeDichVuList() {
            if (
                this.listDichVuVTGetter !== undefined &&
                this.listDichVuVTGetter.length > 0
            ) {
                this.listDichVuVTGetter.forEach(item => {
                    this.listDichVu.push({
                        id: item.DICHVUVT_ID,
                        text: item.TEN_DVVT
                    });
                });
            }
        },

        makeDonViList() {
            if (
                this.listDonViGetter !== undefined &&
                this.listDonViGetter.length > 0
            ) {
                this.listDonVi.push({ id: -1, text: "Tất cả" });
                this.listDonViGetter.forEach(item => {
                    this.listDonVi.push({
                        id: item.DONVI_ID,
                        text: item.TEN_DV
                    });
                });
            }
        },

        makeTrungTamVTList() {
            if (
                this.listTrungTamVTGetter !== undefined &&
                this.listTrungTamVTGetter.length > 0
            ) {
                this.listTrungTamVT.push({ id: -1, text: "Tất cả" });
                this.listTrungTamVTGetter.forEach(item => {
                    this.listTrungTamVT.push({
                        id: item.DONVI_ID,
                        text: item.TEN_DV
                    });
                });
                this.listTrungTamVT.sort((a, b) =>
                    a.text.localeCompare(b.text)
                );
            }
        },
        makeLoaiKHList() {
            if (
                this.listLoaiKHGetter !== undefined &&
                this.listLoaiKHGetter.length > 0
            ) {
                this.listLoaiKH.push({ id: -1, text: "Tất cả" });
                this.listLoaiKHGetter.forEach(item => {
                    this.listLoaiKH.push({
                        id: item.LOAIKH_ID,
                        text: item.TEN_LOAIKH
                    });
                });
            }
        },
        async phaiLam(luong_id, code) {
            try {
                let response = await KhoaMayAcc.getThaoTacTheoLuong(this.axios, luong_id);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    if (response.data.data[0].CODE === code)
                        return true;
                }
                return false;
            }
            catch (error) {
                console.log(error);
            }
            finally {
            }
        },
        async getHuongGiaoID(pLuongID) {
            try {
                let huogngiao_id = null;
                let response = await KhoaMayAcc.getHuongGiaoTheoLuong(this.axios, pLuongID);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    huogngiao_id = response.data.data[0].HUONGGIAO_ID;
                }
                else {
                    this.$toast.error('Không tìm thấy luồng quy trình nghiệp vụ này.\n Bạn hãy liên hệ Admin kiểm tra lại');
                }
                return huogngiao_id;
            }
            catch (error) {
                console.log(error);
            }
            finally {
            }
        },
        async getLuongID(pQuytrinhID, pTTHDID) {
            try {
                let luong_id = null;
                let response = await KhoaMayAcc.getHuongGiaoTheoQuyTrinh(this.axios, { quyTrinhId: pQuytrinhID, tthdId: pTTHDID });
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    luong_id = response.data.data[0].LUONG_ID;
                }
                return luong_id;
            }
            catch (error) {

                console.log(error);
            }
            finally {
            }
        },
        async onGiaoPhieu() {
            try {
                let luong_id = await this.getLuongID(this.quytrinh_id, 1);
                let phuonggiao_id = await this.getHuongGiaoID(luong_id);
                //console.log(phuonggiao_id);

                if (phuonggiao_id > 0) {
                    this.dsGiaoPhieu.huonggiao_id = phuonggiao_id;
                    this.dsGiaoPhieu.luong_id = luong_id;
                    this.dsGiaoPhieu.dichvuvt_id = this.selectedDichVu;
                    // this.dsGiaoPhieu.giaoviec_nvql_cap = await this.phaiLam(luong_id, 'GIAOVIEC_NVQL_CAP');
                    // this.dsGiaoPhieu.giaophieu_gd_pbh = await this.phaiLam(luong_id, 'GIAOPHIEU_GIAMDOC_PBHKV');
                    this.$refs.PopupGiaoPhieu.show();
                }
                else
                    this.$toast.error('Chưa có hướng giao được gán!');
                //await this.onHienThiDanhSach2();


            }
            catch (e) {
                console.log(e);
            }
        },
        async taoDanhSachThanhLy() {
            this.loading(true);
            const data = {
                pDonViId: this.checkNull(this.selectedDonVi),
                pLoaiKHId: this.checkLoaiKH
                    ? this.checkNull(this.selectedLoaiKhachHang)
                    : null,
                pDichVuVTId: this.selectedDichVu,
                pLoaiTBId: this.checkLoaiHinh
                    ? this.checkNull(this.selectedLoaiHinh).toString()
                    : null,
                pKieuTD: this.checkKieuTamDung
                    ? this.checkNull(this.selectedKieuTamDung)
                    : null,
                pTuNgay: moment(this.tuNgay).format("DD/MM/yyyy"),
                pDenNgay: moment(this.denNgay).format("DD/MM/yyyy"),
                pNguoiCN: this.$auth.getUserName(),
                pMayCN: "chinhtn",
                pLyDoCat: this.lyDoCat,
                pKHLonId: this.checkKHLon
                    ? this.checkNull(this.selectedKHLon).toString()
                    : null,
                pTTVTId: this.checkTrungTamVT
                    ? this.checkNull(this.selectedTrungTamVT)
                    : null,
                pDSKhuVuc: ["993", "10399"].toString(),
                pDSQuay: ["993", "974"].toString(),
                pKyHoaDon: this.pKyCuoc
            };

            try {
                const response = await this.taoDanhSachThanhLyThueBao(data);
                //console.log("res", response);
                if (response.data.error_code === "BSS-00000000") {
                    this.getDanhSachThanhLy();
                } else {
                    this.$root.toastError(response.data.message);
                }
            } catch (e) {
                console.log(e);
            } finally {
                this.loading(false);
            }
        },

        async getDanhSachThanhLy() {
            this.loading(true);
            const data = {
                pDichVuVTId: this.selectedDichVu,
                pNguoiCN: this.$auth.getUserName(),
                pKyHoaDon: this.pKyCuoc,
                pKieu: this.selectedTaoHD,
                pageNo: 1,
                pageSize: 2000,
                totalRow: 1
            };
            //console.log({ data });
            try {
                const response = await this.getDanhSachThanhLyThueBao(data);
                //console.log("res", response.data.data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data.length === 0
                ) {
                    this.$root.toastError(
                        "Không tìm thấy thuê bao nào thỏa mãn điều kiện!!"
                    );
                    this.listThanhLy = [];
                } else if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data.length > 0
                ) {
                    if (Array.isArray(response.data.data)) {
                        this.listThanhLy = response.data.data;
                    } else {
                        this.listThanhLy = response.data.data.data;
                    }
                    this.listThanhLy.sort((a, b) =>
                        a.MA_TT.localeCompare(b.MA_TT)
                    );
                } else {
                    this.$root.toastError(response.data.message_detail);
                }
            } catch (e) {
                console.log(e);
            } finally {
                this.loading(false);
            }
        },

        sendData(data) {
            this.getDanhSachThanhLy();
        },

        docFile() {
            this.$bvModal.show("modal-docfilePhieuThanhLy");
        },

        taoPhieu() {
            if (
                this.listThanhLyThueBaoGetter.length === 0 ||
                this.listIdMaTT.length === 0
            ) {
                this.$root.toastError("Chưa có thuê bao để tạo hợp đồng!");
                return;
            }
            if (this.lyDoCat == "") {
                this.$root.toastError("Bạn chưa cập nhật lý do hủy!");
                return;
            };
            this.$confirm(
                `Bạn thật sự muốn chuyển danh sách tạm dừng sang hợp đồng thanh lý?`,
                "Xác nhận",
                {
                    confirmButtonText: "Có",
                    cancelButtonText: "Không"
                }
            )
                .then(() => {
                    this.taoPhieuThanhLyHD();
                })
                .catch(() => { });
        },

        async taoPhieuThanhLyHD() {

            const data = {
                // pDonViId: parseInt(this.selectedDonVi),
                pDichVuVTId: parseInt(this.selectedDichVu),
                pDSLoaiTBId: this.selectedLoaiHinh.toString(),
                pNhanVienId: parseInt(this.$auth.getNhanVienID()),
                pNguoiCN: this.$auth.getUserName(),
                pMayCN: "chinhtn",
                pDSTBId: this.listIdMaTT.toString(),
                dsLyDoHuyID: this.dsLyDoHuyID.toString(),
                pGhiChu:this.ghiChu
            };

            try {
                this.loading(true);
                const response = await this.taoPhieuThanhLy(data);
                if (response.data.error_code === "BSS-00000000") {
                    this.$root.toastSuccess(
                        "Chuyển thành công các thuê bao sang hợp đồng thanh lý!"
                    );
                    await this.getDanhSachThanhLy();
                } else {
                    this.$root.toastError(response.data.message_detail);
                }
            } catch (e) {
                console.log({ error: e });
            } finally {
                this.loading(false);
            }
        },

        handleXoaThueBao() {
            if (this.listThanhLyThueBaoGetter.length === 0) {
                this.$root.toastError(
                    "Chưa có dữ liệu để xóa. Hãy kiểm tra lại!"
                );
                return;
            }
            if (this.listIdMaTT.length === 0) {
                this.$root.toastError("Bạn chưa chọn thuê bao để xóa!");
                return;
            }
            this.$confirm(
                `Bạn thật sự muốn xóa thuê bao đã chọn khỏi dữ liệu được tạo không?`,
                "Xác nhận",
                {
                    confirmButtonText: "Có",
                    cancelButtonText: "Không"
                }
            )
                .then(() => {
                    this.xoaThueBaoThanhLy();
                })
                .catch(() => { });
        },

        async xoaThueBaoThanhLy() {
            // const listTB = this.listThanhLyThueBaoGetter.filter((itemNewVal) =>
            // 	this.listIdMaTT.some(
            // 		(itemOldVal) => itemNewVal.THUEBAO_ID === itemOldVal
            // 	)
            // );
            // const listMaTB = listTB.map((item) => item.MA_TB);
            const data = {
                pMaND: this.$auth.getUserName(),
                pDSThueBaoId: this.listIdMaTT.toString()
            };


            try {
                this.loading(true);
                const response = await this.xoaThueBao(data);
                if (response.data.error_code === "BSS-00000000") {
                    this.$root.toastSuccess(
                        "Xóa thuê bao trên dữ liệu  thành công!"
                    );
                    await this.getDanhSachThanhLy();
                } else {
                    this.$root.toastError(response.data.message_detail);
                }
            } catch (e) {
                console.log({ error: e });
            } finally {
                this.loading(false);
            }
        },

        handleXoaHopDong() {
            if (this.listThanhLyThueBaoGetter.length === 0) {
                this.$root.toastError(
                    "Danh sách hợp đồng trống, bạn không thể Xóa hợp đồng. Hãy kiểm tra lại!"
                );
                return;
            }
            if (this.listIdMaTT.length === 0) {
                this.$root.toastError("Bạn chưa chọn hợp đồng để xóa!");
                return;
            }
            this.$confirm(`Bạn thật sự muốn xóa hợp đồng không?`, "Xác nhận", {
                confirmButtonText: "Có",
                cancelButtonText: "Không"
            })
                .then(() => {
                    this.xoaHopDongTB();
                })
                .catch(() => { });
        },

        async xoaHopDongTB() {
            const listTB = this.listThanhLyThueBaoGetter.filter(itemNewVal =>
                this.listIdMaTT.some(
                    itemOldVal => itemNewVal.THUEBAO_ID === itemOldVal
                )
            );
            const listMaTB = listTB.map(item => {
                return {
                    hdkh_id: item.HDKH_ID,
                    ma_tb: item.MA_TB,
                    loaitb_id: item.LOAITB_ID
                };
            });

            const data = {
                pDSMaTB: listMaTB
            };
            try {
                this.loading(true);
                const response = await this.xoaHopDong(data);
                if (response.data.error_code === "BSS-00000000") {
                    this.$root.toastSuccess(
                        "Xóa hợp đồng trên dữ liệu viễn thông tỉnh thành công!"
                    );
                    await this.getDanhSachThanhLy();
                } else {
                    this.$root.toastError(response.data.message_detail);
                }
            } catch (e) {
                console.log({ error: e });
            } finally {
                this.loading(false);
            }
            console.log({ dataxoa: data });
        },

        onExcelExport() {
            if (this.listThanhLyThueBaoGetter.length === 0) {
                this.$root.toastError("Chưa có danh sách để xuất excel!");
            } else {
                var wb = xlsx.utils.book_new();
                if (this.listThanhLyThueBaoGetter.length > 0) {
                    const dsIn = this.listThanhLyThueBaoGetter.filter(
                        itemNewVal =>
                            this.listIdMaTT.some(
                                itemOldVal =>
                                    itemNewVal.THUEBAO_ID === itemOldVal
                            )
                    );
                    if (dsIn.length === 0) {
                        this.$root.toastError(
                            "Vui lòng chọn hàng phiếu muốn in!"
                        );
                    } else {
                        this.appendSheet(dsIn, 0, wb);
                    }
                }
                xlsx.writeFile(
                    wb,
                    `Export_${moment(new Date()).format(
                        "DD/MM/YYYY HH:mm:ss"
                    )}.xlsx`
                );
            }
        },

        appendSheet(items, type, workbook) {
            this.loading(true);
            try {
                let sheetTitle = "Danh sách phiếu";
                var ds = xlsx.utils.json_to_sheet(items);

                xlsx.utils.book_append_sheet(workbook, ds, sheetTitle);
            } catch (error) {
                console.log(error);
            } finally {
                this.loading(false);
            }
        },

        showAlert() {
            this.$router.push({ name: "GiaoPhieuDieuHanhThiCong" });
        },

        setDSMaTTLoi(ds) {
            this.dsMaTTLoi = ds || [];
            this.$bvModal.show("popup-matt-loi");
        },
        async showLyDoHuy() {


            if (this.selectedLoaiHinh == []) {
                return this.$toast.error(
                    "Bạn chưa chọn loai hình thuê bao!");
            }
            else {
                this.loaiTbId = this.selectedLoaiHinh[0];
                this.$bvModal.show("modal-lydohuy");
            }


        },
        getDSLYDOHUY(data) {
            this.lyDoCat = data.dslydo.map(item => item.NOIDUNG).toString();
            this.dsLyDoHuyID = data.dslydo.map(item => item.LYDOHUY_ID);
            this.$bvModal.hide("modal-lydohuy");
        },
    },
    async created() {
        this.clearData();
        await this.fetchData();
        this.makeDonViList();
        this.makeTrungTamVTList();
        this.makeLoaiKHList();
        this.makeDichVuList();
        this.makeTamDungList();
    }
};
</script>
