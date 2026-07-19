<template src="./DongBoThanhLyInternet.html"></template>
<style src="./DongBoThanhLyInternet.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import { mapActions, mapState } from 'vuex'
export default {
    components: {
        breadcrumb,
    },
    computed: {
        ...mapState("dongbothanhlyinternet", [
            "dsDichVu",
            "dsLoaiHD",
            "dsQuyTrinh",
            "dsLoaiHinh",
            "dsLyDoHuy"
        ])
    },
    mounted() {
        this.fetchData()
    },
    data() {
        return {
            header: {
                title: "ĐỒNG BỘ DỮ LIỆU THUÊ BAO INTERNET TRÊN VISA/VASC",
                list: [
                    { name: "Xử lý thuê bao", link: { name: "Ui.cards" } },
                    {
                        name: "Đồng bộ thanh lý Internet", 
                        link: { name: "Ui.cards" }
                    }
                ]
            },
            fieldsLyDoHuy: [
                {
                    fieldName: "",
                    headerText: "STT",
                    allowFiltering: true,
                    cellCssClass: 'text-right',
                    width: '100'
                },
                {
                    fieldName: "",
                    headerText: "Lý do hủy",
                    allowFiltering: true,
                },
            ],
            fieldsThueBao: [
                {
                    fieldName: "",
                    headerText: "Mã giao dịch",
                    allowFiltering: true,
                },
                {
                    fieldName: "",
                    headerText: "Account",
                    allowFiltering: true,
                },
                {
                    fieldName: "",
                    headerText: "Tên thuê bao",
                    allowFiltering: true,
                },
                {
                    fieldName: "",
                    headerText: "Địa chỉ lắp đặt",
                    allowFiltering: true,
                },
                {
                    fieldName: "",
                    headerText: "Loại hình",
                    allowFiltering: true,
                },
                {
                    fieldName: "",
                    headerText: "Trạng thái đồng bộ",
                    allowFiltering: true,
                },
            ],
            //Thông tin tìm kiếm
            dataTimKiem: {
                checkKichHoat: 0,
                dichVu: null,
                loaiHD: null,
                quyTrinh: null,
                ngayThucHien: new Date(),
            },
            //Thông tin khách hàng
            dataKhachHang: {
                maGiaoDich: null,
                ngayYeuCau: new Date(),
                maKH: null,
                tenKH: null,
                diaChiKH: null,
                ghiChu: null,
            },
            //Thông tin thuê bao
            checkLoaiHinh: false,
            dataThueBao: {
                account: null,
                matKhau: null,
                kieuYeuCau: null,
                loaiHinh: null
            },
            //Trạng thái button
            buttonState: {
                disabledbtnLayDS: false,
                disabledbtnDongBo: false,
                disabledbtnHoanCong: false,
                disabledbtnHuyKichHoat: false,
            },
            isShowModalKichHoat: false,
            isShowModalHuyKichHoat: false,
            dsThueBao: []
        }
    },
    methods: {
        ...mapActions("dongbothanhlyinternet", [
            "getdsDichVu",
            "getdsLoaiHD"
        ]),
        async fetchData() {
            await this.getdsDichVu()
            await this.getdsLoaiHD()
        },
        setButtonState(layDS, dongBo, hoanCong, huyKichHoat) {
            this.buttonState.disabledbtnLayDS = layDS
            this.buttonState.disabledbtnDongBo = dongBo
            this.buttonState.disabledbtnHoanCong = hoanCong
            this.buttonState.disabledbtnHuyKichHoat = huyKichHoat
        },
        setButton(type) {
            switch (type) {
                //mặc định
                //không có bản ghi
                case 0:
                    this.setButtonState(false, true, true, true)
                    break
                //có bản ghi khi chọn trạng thái chưa kích đã check chọn bản ghi
                //chưa check chọn bản ghi là case 0
                case 1:
                    this.setButtonState(false, false, true, true)
                    break
                //có bản ghi khi chọn trạng thái đã kích hoạt đã check chọn bản ghi
                //chưa check chọn bản ghi là case 0
                case 2:
                    this.setButtonState(false, true, false, false)
                    break
            }
        },
        layDS() {
            if (this.dsThueBao.length === 0) {
                this.setButton(0)
            }
        },
        dongBo() {

        },
        hoanCong() {

        },
        huyKichHoat(){

        }
    },
}
</script>
