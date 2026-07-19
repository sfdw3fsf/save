<template src="./CapNhapHoSoCongNo.html"></template>
<style src="./CapNhapHoSoCongNo.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import Calculator from '@/components/Calculator'
import { mapActions, mapState } from "vuex";
import CommonsAPI from '../api/Commons.js'
import API from '../api/CapNhapHoSoCongNo.js'
import { maxLength } from 'vuelidate/lib/validators'
export default {
    components: { breadcrumb, Calculator },
    computed: {
        ...mapState("qltnCommon", [
            "danhSachHinhThucThanhToan",
        ]),
        ...mapState("capnhaphosocongno", [
            "dsDonVi",
            "dsNhanVienQL",
            "dsNhanVienThuCuoc",
            "dsLoaiKhachHang",
            "dsPhuongAnXL",
            "dsKyHD",
            "danhMucHoSo",
            "dsPhanLoaiKH",
            "dsCongNo",
        ])
    },
    mounted() {
        this.clearGrid()
        this.fetchData()
    },
    data() {
        return {
            header: {
                title: "CẬP NHẬP HỒ SƠ CÔNG NỢ",
                list: [
                    { name: "Kiểm kê công nợ", link: { name: "Ui.cards" } },
                    {
                        name: "Cập nhập hồ sơ công nợ",
                        link: { name: "Ui.buttons" }
                    }
                ]
            },
            fields: [
                {
                    fieldName: "MA_TT",
                    headerText: "Mã TT",
                    allowFiltering: true,
                },
                {
                    fieldName: "TEN_TT",
                    headerText: "Tên TT",
                    allowFiltering: true,
                },
                {
                    fieldName: "DIACHI_TT",
                    headerText: "Địa chỉ TT",
                    allowFiltering: true,
                },
                {
                    fieldName: "DIACHI_CT",
                    headerText: "Địa chỉ CT",
                    allowFiltering: true,
                },
                {
                    fieldName: "TRANGTHAI_XN",
                    headerText: "Trạng thái XN",
                    allowFiltering: true,
                },
                {
                    fieldName: "TEN_NHOMKH",
                    headerText: "Nhóm KH",
                    allowFiltering: true,
                },
                {
                    fieldName: "SO_DT",
                    headerText: "Số ĐT",
                    allowFiltering: true,
                },
                {
                    fieldName: "FAX",
                    headerText: "Fax",
                    allowFiltering: true,
                },
                {
                    fieldName: "TEN_NHOM",
                    headerText: "Nhóm C.nợ",
                    allowFiltering: true,
                },
                {
                    fieldName: "TONGNO",
                    headerText: "Tổng nợ",
                    allowFiltering: true,
                },
                {
                    fieldName: "NOAM",
                    headerText: "Nợ âm",
                    allowFiltering: true,
                },
                {
                    fieldName: "MA_NVTC",
                    headerText: "Mã NVTC",
                    allowFiltering: true,
                },
            ],
            isEnable: {
                donVi: false,
                hinhThucTT: false,
                nhomKH: false,
                nhanVienQL: false,
                nvtc: false,
                phanLoaiKH: false,
                loaiKH: false,
                quaHanTu: false,
                den: false,
                hoSo: false,
                phuongAnXL: false
            },
            dataSelect: {
                donViId: "-1",
                maTT: "",
                htttId: "-1",
                nhomKH: "-1",
                nhanvienQLId: "",
                tuyenThu: "",
                phanLoaiKHId: null,
                loaiKHId: "-1",
                soThangQuaHanTu: "0",
                soThangQuaHanDen: "0",
                chiTiet: "",
                daGan: "",
                lanTaoId: "",
            },
            dsNhomKH: [
                { id: -1, text: "Tất cả" },
                { id: 0, text: "Cá nhân" },
                { id: 1, text: "Doanh nghiệp" },
            ],
            isCalculatorShow: {
                quaHanTu: false,
                den: false
            },
            dataUpdate: {
                congNoId: "",
                dsHoSoId: [-1],
                dsPAXLId: [-1],
                ghiChu: null
            },
            isShowModal: false
        }
    },
    validations: {
        dataUpdate: {
            ghiChu: {
                maxLength: maxLength(200)
            },
        }
    },
    methods: {
        ...mapActions("qltnCommon", [
            "getDanhSachHinhThucThanhToan",
        ]),
        ...mapActions("capnhaphosocongno", [
            "getdsDonVi",
            "getdsNhanVienQL",
            "getdsNhanVienThuCuoc",
            "getDanhSachLoaiKhachHang",
            "getdsPhuongAnXL",
            "getdsKyHD",
            "getDanhMucHoSo",
            "getDanhSachPhanLoaiKH",
            "getDanhSachCongNo",
            "clearGrid",
            "updateHoSoCongNo"
        ]),
        async fetchData() {
            await this.getdsDonVi()
            await this.getDanhSachHinhThucThanhToan()
            await this.getDanhSachLoaiKhachHang()
            // await this.getdsNhanVienThuCuoc()
            await this.getdsPhuongAnXL()
            await this.getdsKyHD()
            await this.getDanhMucHoSo()
            await this.getDanhSachPhanLoaiKH(this.dataSelect.nhomKH)
        },
        async getDanhSachNhanVienTheoDonVi() {
            await this.getdsNhanVienQL(this.dataSelect.donViId)
            this.dataSelect.nhanvienQLId = "-1"
            await this.getdsNhanVienThuCuoc(-1)
        },
        async getDanhSachNVTC(){
            await this.getdsNhanVienThuCuoc(this.dataSelect.nhanvienQLId)
            this.dataSelect.tuyenThu = "-1"
        },
        async getPhanLoaiKH() {
            await this.getDanhSachPhanLoaiKH(this.dataSelect.nhomKH)
        },
        async timKiem() {
            // let data = {
            //     donViId: 47,
            //     maTT: "HPG-01-633214",
            //     htttId: 2,
            //     nhomKH: "",
            //     nhanvienQLId: 4502,
            //     tuyenThu: "",
            //     phanLoaiKHId: "",
            //     loaiKHId: 1,
            //     soThangQuaHanTu: "",
            //     soThangQuaHanDen: "",
            //     chiTiet: "",
            //     daGan: "",
            //     lanTaoId: 78,
            // }
            this.$root.loading(true)
            try {
                await this.getDanhSachCongNo(this.dataSelect)
                this.$root.loading(false)
            } catch (error) {
                this.$toast.error('Không tìm thấy dữ liệu thỏa điều kiện.')
                this.$root.loading(false)
            }
            // await this.getDanhSachCongNo(this.dataSelect)
            // this.$root.loading(false)
        },
        async enter() {
            let data = {
                donViId: "",
                maTT: this.dataSelect.maTT,
                htttId: "",
                nhomKH: "",
                nhanvienQLId: "",
                tuyenThu: "",
                phanLoaiKHId: "",
                loaiKHId: "",
                soThangQuaHanTu: "",
                soThangQuaHanDen: "",
                chiTiet: "",
                daGan: "",
                lanTaoId: "",
            }
            this.$root.loading(true)
            try {
                await this.getDanhSachCongNo(data)
                this.$root.loading(false)
            } catch (error) {
                this.$toast.error('Đã có lỗi xảy ra')
                this.$root.loading(false)
            }
        },
        async rowClick(rowIndex) {
            let item = this.$refs.gridDSCongNo.dataItems[rowIndex]
            this.dataUpdate.congNoId = item.CONGNO_ID
            // this.dataUpdate.dsHoSoId = []
            this.dataUpdate.ghiChu = ""
            await API.getDanhSachPhuongAnXLTheoCongNoId(this.axios, this.dataUpdate.congNoId)
                .then(response => {
                    if (response.data.data.length > 0) {
                        this.dataUpdate.dsPAXLId = []
                        response.data.data.forEach(element => {
                            this.dataUpdate.dsPAXLId.push(element.PAXL_ID)
                        })
                    } else {
                        this.dataUpdate.dsPAXLId.push(-1)
                    }
                }).catch(error => {
                    throw error
                })
        },
        checkUpdate() {
            if (this.dsCongNo.length == 0) {
                this.$toast.error("Hãy lấy danh sách trước khi thực hiện!")
                return
            }
            if (this.dataUpdate.congNoId == "") {
                this.$toast.error("Bạn chưa chọn dữ liệu trên lưới để cập nhật!")
                return
            }
            if (this.validateData(this.dataUpdate)) {
                return
            }
            this.isShowModal = true
        },
        async update() {
            await this.updateHoSoCongNo(this.dataUpdate)
        },
        validateData(data) {
            this.$v.$touch();
            let errorMessage = []

            if (this.$v.dataUpdate.ghiChu.$error) {
                this.$refs.ghiChu.focus()
                
                if (!data.ghiChu.maxLength) {
                    errorMessage.unshift("Trường ghi chú dữ liệu vượt quá độ dài cho phép.")
                }
            }

            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join('\n'))
                return true
            } else {
                return false
            }
        }
    },
}
</script>
