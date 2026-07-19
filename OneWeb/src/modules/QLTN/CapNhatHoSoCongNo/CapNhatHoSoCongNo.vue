<template src="./CapNhatHoSoCongNo.html"></template>
<style src="./CapNhatHoSoCongNo.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import Calculator from '@/components/Calculator'
import { mapActions, mapState } from "vuex";
import CommonsAPI from '../api/Commons.js'
import API from '../api/CapNhapHoSoCongNo.js'
import { maxLength } from 'vuelidate/lib/validators'
import { currency } from "@/filters/currency";
import { CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns";
export default {
    provide: {
        multiselect: [CheckBoxSelection],
    },
    components: { breadcrumb, Calculator },
    computed: {
        ...mapState("GanNhomCongNo", [
            "listHTTT",
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
                title: "CẬP NHẬT HỒ SƠ CÔNG NỢ",
                list: [
                    { name: "Kiểm kê công nợ", link: { name: "Ui.cards" } },
                    {
                        name: "Cập nhật hồ sơ công nợ",
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
                    cellCssClass: 'text-right',
                    type: "number",
                    format: "N0"
                },
                {
                    fieldName: "NOAM",
                    headerText: "Nợ âm",
                    allowFiltering: true,
                    cellCssClass: 'text-right',
                    type: "number",
                    format: "N0"
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
                nhomKH: "",
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
            isShowModal: false,
            params: {
                phanLoaiKH_Options: [],
                loaiKH_Options: [],
            },
            gridDSCongNoSelected: [],
            result: false,
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
        ...mapActions("GanNhomCongNo", [
            "getHTTT",           
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
            await this.getHTTT()
            await this.getDanhSachLoaiKhachHang()
            // await this.getdsNhanVienThuCuoc()
            await this.getdsPhuongAnXL()
            await this.getdsKyHD()
            await this.getDanhMucHoSo()
            // await this.getDanhSachPhanLoaiKH(this.dataSelect.nhomKH)
            this.dataSelect.lanTaoId = this.dsKyHD[0].LANTAO_ID
            this.dataSelect.nhomKH = "-1"
            console.log(this.dsKyHD)
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
        // async getPhanLoaiKH() {
        //     await this.getDanhSachPhanLoaiKH(this.dataSelect.nhomKH)
        // },
        async timKiem() {
            let data = {
                donViId: this.isEnable.donVi && this.dataSelect.donViId != -1 ? this.dataSelect.donViId : null,
                maTT: this.dataSelect.maTT ? this.dataSelect.maTT : null,
                htttId: this.isEnable.hinhThucTT && this.dataSelect.htttId != -1 ? this.dataSelect.htttId : null,
                nhomKH: this.isEnable.nhomKH && this.dataSelect.nhomKH != -1 ? this.dataSelect.nhomKH : null,
                nhanvienQLId: this.isEnable.nhanVienQL && this.dataSelect.nhanvienQLId != -1 ? this.dataSelect.nhanvienQLId : null,
                tuyenThu: this.isEnable.nvtc && this.dataSelect.tuyenThu != -1 ? this.dataSelect.tuyenThu : null,
                phanLoaiKHId: this.isEnable.phanLoaiKH && this.dataSelect.phanLoaiKHId != -1 ? this.dataSelect.phanLoaiKHId : null,
                loaiKHId: this.isEnable.loaiKH && this.dataSelect.loaiKHId != -1 ? this.dataSelect.loaiKHId : null,
                soThangQuaHanTu: this.isEnable.quaHanTu ? this.dataSelect.soThangQuaHanTu : null,
                soThangQuaHanDen: this.isEnable.den ? this.dataSelect.soThangQuaHanDen : null,
                lanTaoId: this.dataSelect.lanTaoId,
                daGan: 1,
                chiTiet: this.isEnable.phanLoaiKH ? 0 : 1
            }
            console.log(data)
            this.$root.loading(true)
            try {
                await this.getDanhSachCongNo(data)
                this.$root.loading(false)
            } catch (error) {
                this.clearGrid()
                this.$toast.error('Không tìm thấy dữ liệu thỏa điều kiện.')
                this.$root.loading(false)
            }
            this.dataUpdate.dsHoSoId = []
            this.dataUpdate.dsPAXLId = []
            this.dataUpdate.ghiChu = ""
        },
        async enter() {
            if (!this.dataSelect.maTT) return
            let data = {
                maTT: this.dataSelect.maTT,
                lanTaoId: this.dataSelect.lanTaoId,
                daGan: 1
            }
            this.$root.loading(true)
            try {
                await this.getDanhSachCongNo(data)
                console.log(this.dsCongNo)
                this.$root.loading(false)
            } catch (error) {
                this.clearGrid()
                this.$toast.error('Đã có lỗi xảy ra')
                this.$root.loading(false)
            }
        },
        async onRowChanged(dataItem) {
            // let item = this.$refs.gridDSCongNo.dataItems[rowIndex]
            let item = dataItem
            if (this.dataUpdate.congNoId !== item.CONGNO_ID) {
                // this.dataUpdate.dsHoSoId = []
                // this.dataUpdate.ghiChu = ""    

                this.dataUpdate.congNoId = item.CONGNO_ID
                API.getDanhSachPhuongAnXLTheoCongNoId(this.axios, this.dataUpdate.congNoId)
                    .then(response => {
                        this.dataUpdate.dsPAXLId = []
                        if (response.data.data.length > 0) {
                            // this.dataUpdate.dsPAXLId = []
                            response.data.data.forEach(element => {
                                this.dataUpdate.dsPAXLId.push(element.PAXL_ID)
                            })
                        } else {
                            if (this.dataUpdate.dsPAXLId.length === 0)
                                this.dataUpdate.dsPAXLId.push(-1)
                        }
                    }).catch(error => {
                        throw error
                    })
                API.getDanhSachHoSoCongNoTheoCongNoId(this.axios, this.dataUpdate.congNoId)
                    .then(response => {
                        this.dataUpdate.dsHoSoId = []
                        this.dataUpdate.ghiChu = ""
                        if (response.data.data.length > 0) {
                            this.dataUpdate.ghiChu = response.data.data[0].GHICHU
                            response.data.data.forEach(element => {
                                this.dataUpdate.dsHoSoId.push(element.HOSOCN_ID)
                            })
                        } else {
                            if (this.dataUpdate.dsHoSoId.length === 0) {
                                this.dataUpdate.dsHoSoId.push(-1)
                            }
                        }
                    })
            }
        },
        checkUpdate() {
            if (this.dsCongNo.length == 0) {
                this.$toast.error("Hãy lấy danh sách trước khi thực hiện!")
                return
            }
            if (this.gridDSCongNoSelected.length == 0) {
                this.$toast.error("Bạn chưa chọn dữ liệu trên lưới để cập nhật!")
                return
            }
            if (this.validateData(this.dataUpdate)) {
                return
            }
            this.isShowModal = true
        },
        async update() {
            for (let i = 0; i < this.gridDSCongNoSelected.length; i++) {
                let data = {
                    congNoId: this.gridDSCongNoSelected[i].toString(),
                    dsHoSoId: this.isEnable.hoSo ? this.dataUpdate.dsHoSoId : [-1],
                    dsPAXLId: this.isEnable.phuongAnXL ? this.dataUpdate.dsPAXLId : [-1],
                    ghiChu: this.dataUpdate.ghiChu,
                }
                API.updateHoSoCongNo(this.axios, data).then(response => {
                    if (response.data.error_code === 'BSS-00000000') {
                        this.result = true
                    } else {
                        this.result = false
                    }

                    if (i == this.gridDSCongNoSelected.length - 1) {
                        if (this.result) {
                            this.$toast.success("Cập nhật hồ sơ công nợ thành công!")
                        } else {
                            this.$toast.error(response.data.message)
                        }
                    }
                }).catch(reject => {
                    this.$toast.error(reject.data.message)
                })
            }
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
    watch: {
        "isEnable.nhomKH": async function(newVal, oldVal) {
            if (!newVal) {
                this.isEnable.phanLoaiKH = false;
                this.isEnable.loaiKH = false;
            }
        },
        "dataSelect.nhomKH": async function(newVal, oldVal) {            
            await this.getDanhSachPhanLoaiKH(newVal);
            await this.getDanhSachLoaiKhachHang(newVal);
            this.params.phanLoaiKH_Options = [];
            this.params.loaiKH_Options = [];

            if (this.dsPhanLoaiKH.length > 0) {
                this.dsPhanLoaiKH.forEach(item => {
                    this.params.phanLoaiKH_Options.push({ id: item.PHANLOAIKH_ID, text: item.MA_PLKH });
                });
                //Chuyển option Tất cả (id = null) về id = -1:
                let idx = this.params.phanLoaiKH_Options.findIndex(e => e.id === null);
                if (idx !== -1)
                    this.params.phanLoaiKH_Options[idx].id = -1;
                this.dataSelect.phanLoaiKHId = this.params.phanLoaiKH_Options[0].id;
                console.log(this.params.phanLoaiKH_Options[0].id)   
            }
            this.params.loaiKH_Options.unshift({ id: -1, text: 'Tất cả' });
            if (this.dsLoaiKhachHang.length > 0) {
                this.dsLoaiKhachHang.forEach(item => {
                    this.params.loaiKH_Options.push({ id: item.LOAIKH_ID, text: item.TEN_LOAIKH });
                });
                this.dataSelect.loaiKHId = this.params.loaiKH_Options[0].id;   
            }
        },
    }
}
</script>
