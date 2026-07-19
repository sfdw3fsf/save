<template src="./TraCuuLichSuThueBaoGHTT.html"></template>
<style scoped src="./TraCuuLichSuThueBaoGHTT.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import DataGrid from "@/components/Controls/DataGrid";
import api from "./API.js";
import VueFlatPickr from 'vue-flatpickr-component'
import SearchAccountModal from './SearchAccountModal'
export default {
    name: "TraCuuLichSuThueBaoGHTT",
    components: { breadcrumb, DataGrid, api, VueFlatPickr, SearchAccountModal },
    data() {
        return {
            ds_dichvu: [],
            DichVu_ID: '',
            ds_loaihinhtb: [],
            ds_doituong: [],
            ds_trangthai: [],
            ds_thaydoi: [],
            LoaitTB_id: '',
            doituong_id: '',
            header: {
                title: "TRA CỨU LỊCH SỬ THUÊ BAO GHTT",
                list: [
                    //{ name: 'Trang chủ', link: { name: 'Ui.cards' } },
                    { name: "Tra cứu", link: { name: "Ui.cards" } },
                    { name: "Phát triển thuê bao", link: { name: "Ui.cards" } },
                    {
                        name: "TRA CỨU LỊCH SỬ THUÊ BAO GHTT",
                        link: { name: "Ui.cards" },
                        active: true,
                    },
                ],
            },
            map: {
                lat: null,
                lng: null
            },
            map_info: {
                dia_chi: '',
                title: ''
            },
            txtkhuvuc: {
                enabbled: false,
                value: ''
            },
            txtma_thanhtoan: {
                enabbled: false,
                value: ''
            },
            txtso_lh: {
                enabbled: false,
                value: ''
            },
            txtTrangThai: {
                enabbled: false,
                value: ''
            },
            txtma_tb: {
                enabbled: false,
                value: ''
            },
            DiaChiKH: '',
            // dataSelected: {
            //     thuebao_id: 0,
            ma_tb: '',
            ten_KH: "",
            thoigian_goi: "",
            //     soao: "",
            //     ten_tb: "",
            //     diachi_ld: "",
            //     dichvuvt_id: 4,
            //     loaitb_id: 58,
            //     doituong_id: 1,
            trangthaitb_id: 0,
            //     ma_kh: "",
            //     ten_kh: "",
            //     diachi_kh: "",
            //     ma_tt: "",
            //     ten_tt: "",
            //     diachi_tt: "",
            // },
            gridDC_KM: {
                header: [
                    { fieldName: 'loai_km', headerText: 'Loại KM', allowFiltering: true, width: 150 },
                    { fieldName: 'ten_chitietkm', headerText: 'Chi tiết KM	', allowFiltering: true, width: 200 },
                    { fieldName: 'ngay_dkdc', headerText: 'Ngày ĐK', format: { type: 'date', format: 'dd/MM/yyyy' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'ngay_bd', headerText: 'Ngày BĐ', format: { type: 'date', format: 'dd/MM/yyyy' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'ngay_kt', headerText: 'Ngày KT', format: { type: 'date', format: 'dd/MM/yyyy' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'ngay_bddc', headerText: 'Ngày đóng cước', format: { type: 'date', format: 'dd/MM/yyyy' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 150 },
                    // { fieldName: 'ngay_ktdc', headerText: 'Ngày KT', format: { type: 'date', format: 'dd/MM/yyyy' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    // { fieldName: 'thang_bd', headerText: 'Tháng BĐ', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    // { fieldName: 'thang_kt', headerText: 'Tháng KT', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    // { fieldName: 'thang_bddc', headerText: 'Tháng BĐ', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    // { fieldName: 'thang_ktdc', headerText: 'Tháng KT', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'ngay_thoai', headerText: 'Ngày thoái', format: { type: 'date', format: 'dd/MM/yyyy' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    // { fieldName: 'thang_thoaitra', headerText: 'Tháng thoái', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'tien_thoai', headerText: 'Tiền thoái', format: 'N0', type: 'Number', textAlign: 'Right', allowFiltering: true, width: 100 },
                    { fieldName: 'hieuluc', headerText: 'Hiệu lực', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'thang_huy', headerText: 'Tháng hủy', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'datcoc_csd', headerText: 'Tiền đặt cọc', format: 'N0', type: 'Number', textAlign: 'Right', allowFiltering: true, width: 150 },
                    // { fieldName: 'tyle_sd', headerText: 'Tỷ lệ SD', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'tien_sd', headerText: 'Tiền SD', format: 'N0', type: 'Number', textAlign: 'Right', allowFiltering: true, width: 100 },
                    { fieldName: 'cuoc_sd', headerText: 'Giảm cước SD', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 150 },
                    // { fieldName: 'tien_td', headerText: 'Tiền TD', format: 'N0', type: 'Number', textAlign: 'Right', allowFiltering: true, width: 100 },
                    //{ fieldName: 'tyle_tb', headerText: 'Tỷ lệ TB', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    //{ fieldName: 'tien_tb', headerText: 'Tiền TB', format: 'N0', type: 'Number', textAlign: 'Right', allowFiltering: true, width: 100 },
                    { fieldName: 'cuoc_tb', headerText: 'Giảm cước TB', format: 'N0', type: 'Number', textAlign: 'Right', allowFiltering: true, width: 150 },
                    // { fieldName: 'tyle_tk', headerText: 'Tỷ lệ TK', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    // { fieldName: 'tyle_tc', headerText: 'Tỷ lệ TC', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    // { fieldName: 'noidung', headerText: 'Nội dung', allowFiltering: true, allowHtml: true, width: 100 }
                ],
                listDC_KM: [],
                selected: [],
                value: null,
            },
            gridOB_GHTT: {
                header: [
                    { fieldName: '', headerText: 'Tên kết quả OB', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'Kết quả cập nhật', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'Ghi chú', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'Ngày hẹn từ', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'Ngày hẹn đến', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'Ngày cập nhật', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'NV cập nhật', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'NV cập nhật', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'DV NV cập nhật', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'Thời gian gọi', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'Kết quả gọi', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'Trạng thái phiếu', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'Ngày hoàn công', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'KQ hoàn công', allowFiltering: true, allowSorting: false, width: 150 },
                ],
                listOB_GHTT: [],
                selected: [],
                value: null,
            },
            gridPhieu_YC: {
                header: [
                    { fieldName: '', headerText: 'Loại phiếu YC', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'Mã YC', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'Ngày yêu cầu', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'Trạng thái TH', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'Ngày HT', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'Người CN', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'Máy CN', allowFiltering: true, allowSorting: false, width: 150 },
                ],
                listPhieu_YC: [],
                selected: [],
                value: null,
            },
            gridChiTiet_PYC: {
                header: [
                    { fieldName: '', headerText: 'Đơn vị giao', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'Nhân viên giao', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'Ngày giao', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'Nội dung giao', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'Đơn vị nhận', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'Nhân viên nhận', allowFiltering: true, allowSorting: false, width: 110 },
                    { fieldName: '', headerText: 'Ngày trả', allowFiltering: true, allowSorting: false, width: 150 },
                    { fieldName: '', headerText: 'Nội dung trả', allowFiltering: true, allowSorting: false, width: 150 },
                ],
                listChiTiet_PYC: [],
                selected: [],
                value: null,
            },
        };
    },
    async mounted() {
        await this.getListDichVu()
        await this.getListLoaiHinhTB(this.DichVu_ID)
        await this.getDSDoituong()
        // await this.getDSTrangThai()
    },
    methods: {
        showPopupSearchAccount() {
            this.$refs.popupSearchAccount.showModal()
        },
        async searchAccountModalClose(data) {
            this.txtma_tb.value = data.ma_tb
            // this.onClickSearch()

        },
        async openMapTB(dia_chi, type) {
            let data = {
                lat: null, //vi do
                lng: null //kinh do
            }
            this.map_info = {
                dia_chi: dia_chi,
                title: dia_chi
            }
            if (type == 'DiaChiKH') {
                let toado = await api.sp_lay_tt_kinhdo_vido(this.axios, {
                    kieu: 1,
                    id: this.khachhang_id
                })
                if (toado.data.data) {
                    data.lng = Number(toado.data.data.out_kinhdo) > 0 ? Number(toado.data.data.out_kinhdo) : null
                    data.lat = Number(toado.data.data.out_vido) > 0 ? Number(toado.data.data.out_vido) : null
                }
            } else if (type == 'DiaChiTT') {
                let toado = await api.sp_lay_tt_kinhdo_vido(this.axios, {
                    kieu: 2,
                    id: this.khachhang_id
                })
                if (toado.data.data) {
                    data.lng = Number(toado.data.data.out_kinhdo) > 0 ? Number(toado.data.data.out_kinhdo) : null
                    data.lat = Number(toado.data.data.out_vido) > 0 ? Number(toado.data.data.out_vido) : null
                }
            } else if (type == 'DiaChiBC') {
                let toado = await api.sp_lay_tt_kinhdo_vido(this.axios, {
                    kieu: 5,
                    id: this.khachhang_id
                })
                if (toado.data.data) {
                    data.lng = Number(toado.data.data.out_kinhdo) > 0 ? Number(toado.data.data.out_kinhdo) : null
                    data.lat = Number(toado.data.data.out_vido) > 0 ? Number(toado.data.data.out_vido) : null
                }
            } else if (type == 'DiaChiTB') {
                let toado = await api.sp_lay_tt_kinhdo_vido(this.axios, {
                    kieu: 3,
                    id: this.khachhang_id
                })
                if (toado.data.data) {
                    data.lng = Number(toado.data.data.out_kinhdo) > 0 ? Number(toado.data.data.out_kinhdo) : null
                    data.lat = Number(toado.data.data.out_vido) > 0 ? Number(toado.data.data.out_vido) : null
                }
            } else if (type == 'DiaChiLD') {
                let toado = await api.sp_lay_tt_kinhdo_vido(this.axios, {
                    kieu: 4,
                    id: this.khachhang_id
                })
                if (toado.data.data) {
                    data.lng = Number(toado.data.data.out_kinhdo) > 0 ? Number(toado.data.data.out_kinhdo) : null
                    data.lat = Number(toado.data.data.out_vido) > 0 ? Number(toado.data.data.out_vido) : null
                }
            }
            this.map = data
            //this.$refs.chonbando.searchLocation(this.map_info.dia_chi);
            this.$bvModal.show('ModalChonToaDo')
        },
        getListDichVu: async function () {
            let data = this.GetData(await api.getDSDichVu(this.axios));
            this.ds_dichvu = data.map((x) => ({
                id: x.DICHVUVT_ID,
                text: x.TEN_DVVT == null ? '' : x.TEN_DVVT
            }))
            if (this.ds_dichvu.length > 0) this.DichVu_ID = this.ds_dichvu[0].id
            console.log(this.ds_dichvu);
        },
        getListLoaiHinhTB: async function (value) {
            let data = this.GetData(await api.getDSLoaiHinhTB(this.axios, value));
            this.ds_loaihinhtb = data.map((x) => ({
                id: x.ID,
                text: x.NAME == null ? '' : x.NAME
            }))
            if (this.ds_loaihinhtb.length > 0) this.LoaitTB_id = this.ds_loaihinhtb[0].id

        },
        getDSDoituong: async function () {
            let data = this.GetData(await api.getDSDoiTuong(this.axios));
            this.ds_doituong = data;
            this.ds_doituong = data.map((x) => ({
                id: x.doituong_id,
                text: x.ten_dt == null ? '' : x.ten_dt
            }))
            if (this.ds_doituong.length > 0) this.doituong_id = this.ds_doituong[0].id
        },
        getDSTrangThai: async function () {
            let data = this.GetData(await api.getDSTrangThai(this.axios));
            this.ds_trangthai = data.map((x) => ({
                id: x.TRANGTHAITB_ID,
                text: x.TRANGTHAI_TB == null ? '' : x.TRANGTHAI_TB
            }))
            console.log(this.ds_trangthai);
            if (this.ds_trangthai.length > 0) this.trangthaitb_id = this.ds_trangthai[0].id
            console.log(this.trangthaitb_id);
        },
        onChangeDVVT: async function (value) {
            this.loading(true);
            try {
                var DichVu_ID = value ? Number(value) : 0;
                await this.getListLoaiHinhTB(DichVu_ID);
            } catch (e) {
                this.loading(false);
            }
            this.loading(false);
        },
        onChangeLoaiHinhTB: async function (value) {
            console.log(value);
        },
        onChangeDT: async function (value) {
            console.log(value);
        },
        onChangeTT: async function (value) {
            console.log(value);
        },
        async onMaTBKeyPress() {
            if (this.txtma_tb.value == "") {
                this.$toast.error("Bạn phải nhập Số máy/Acc!")
                return
            }
        },
        // handleSearch: async function () {
        //     this.loading(true);
        //     try {
        //         var checked = this.validateInput(this.dataSelected);
        //         if (checked == true) {
        //             // await this.getDanhBaMaTB();
        //         }
        //         // await this.sp_lay_ds_tratruoc_trasau()// thêm đoạn gọi hàm tra cứu ở phía trên 
        //     } catch (e) {
        //         this.loading(false);
        //     }
        //     this.loading(false);
        // },
        // validateInput: function (data) {
        //     if (!data.ma_tb || data.ma_tb == "") {
        //         this.$toast.error("Bạn phải nhập Số máy/Acc!");
        //         return false;
        //     }
        //     // if (!data.loaitb_id || data.loaitb_id == -1) {
        //     //   this.$toast.error("Bạn chưa chọn Loại hình thuê bao!");
        //     //   return false;
        //     // }
        //     return true;
        // },
        GetData: function (response) {
            if (
                response.data.error === 200 ||
                response.data.error === "200"
            ) {
                return response.data.data;
            } else if (
                response.data.error === 204 ||
                response.data.error === "204"
            ) {
                return []
            } else {
                console.log(response.data.error_code);
                return []
            }
        },
    },
};
</script>