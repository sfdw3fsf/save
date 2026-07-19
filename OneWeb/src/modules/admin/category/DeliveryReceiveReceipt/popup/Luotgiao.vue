
<template src='./Luotgiao.html'></template>

<style scoped src='./Luotgiao.scss'></style>

<script>
import api from "../api/index.js";
import collums from "../define/collums.js";
import moment from 'moment'
import XLSX from 'xlsx'

export default {
    name: 'Luotgiao',
    mounted() {
        this.visiable.XuatBBBL = true;
    },
    computed: {
        kho_giao_id: {
            get() {
                return Number(this.model.kho_giao_id);
            },
            set(val) {
                if (val)
                    this.$set(this.model, "kho_giao_id", val);
            },
        },
    },
    data() {
        return {
            ...collums,
            dateconfig: {
                altFormat: 'd/m/Y H:i:S',
                altInput: true,
                dateFormat: 'd/m/Y H:i:S',
                allowInput: true,
            },
            visiable: {
                XuatBBBL: true,
            },
            dateFormat: "DD/MM/YYYY",
            animationSettings: {
                effect: "None"
            },
            txtTuNgay: moment(new Date()).subtract(5, 'days').format('DD/MM/YYYY'),
            txtDenNgay: moment(new Date()).format('DD/MM/YYYY'),
            isModal: true,
            dataSelected: {
                kho_giao_id: 0,
                nv_giao_id: 0,
                nv_nhan_id: 0,
            },
            options: {
                ds_KhoGiao: [],
                ds_KhoNhan: [],
                ds_NVGiao: [],
                ds_Luotgiao: {},
                ds_NVNhan: [],
                ds_Bienlai: [],
                ds_Luotgiao: [],
            },
            perPage: 10,
            currentPage: 1,
            totalRows: 0,
            donviId: this.$root.token.getDonViID(),
            nhanvienId: this.$root.token.getNhanVienID(),
        }
    },
    methods: {
        onModalShown() {
            this.getDsNhanvien_giao();
            this.getDsNhanvien_nhan();
        },
        onModalHidden() {
            this.options.ds_KhoGiao = [];
        },
        //Get danh sách nhân viên giao
        async getDsNhanvien_giao() {
            await api.getDsNhanvien_giao(this.axios, {
                "khonhan_id": null,
                "khogiao_id": null,
                "total_row": 0,
                "tu_ngay": this.txtTuNgay,
                "page_num": 1,
                "den_ngay": this.txtDenNgay,
                "page_sz": 10
            }).then((res) => {
                this.options.ds_NVGiao = res.data.data.nv ? res.data.data.nv : [];
                if (this.options.ds_NVGiao) {
                    this.dataSelected.nv_giao_id = this.options.ds_NVGiao[0]["nhanvien_id"];
                    this.getDSKhoGiao(this.dataSelected.nv_giao_id);
                }
            });
        },
        //Get danh sách kho giao theo nhân viên 
        async getDSKhoGiao(nhanvien_id) {
            await api.getDSKhoGiao_Nhanvien(this.axios, {
                "nhanvien_id": nhanvien_id
            }).then((res) => {
                this.options.ds_KhoGiao = res.data.data ? res.data.data : [];
                if (this.options.ds_KhoGiao) {
                    this.dataSelected.kho_giao_id = this.options.ds_KhoGiao[0]["kho_id"];
                }
            });
        },
        //Get danh sách nhân viên giao
        async getDsNhanvien_nhan() {
            await api.getDsNhanvien_nhan(this.axios, {
                "khonhan_id": null,
                "khogiao_id": null,
                "total_row": 0,
                "tu_ngay": this.txtTuNgay,
                "page_num": 1,
                "den_ngay": this.txtDenNgay,
                "page_sz": 10
            }).then((res) => {
                this.options.ds_NVNhan = res.data.data.nv ? res.data.data.nv : [];
                if (this.options.ds_NVNhan) {
                    this.dataSelected.nv_nhan_id = this.options.ds_NVNhan[0]["nhanvien_id"];
                    this.getDSKhonNhan(this.dataSelected.nv_nhan_id);
                }
            });
        },
        //Get danh sách kho giao theo nhân viên 
        async getDSKhonNhan(nhanvien_id) {
            this.loading(true);
            try {
                await api.getDSKhoGiao_Nhanvien(this.axios, {
                    "nhanvien_id": nhanvien_id
                }).then((res) => {

                    this.options.ds_KhoNhan = res.data.data ? res.data.data : [];
                    if (this.options.ds_KhoNhan) {
                        this.dataSelected.kho_nhan_id = this.options.ds_KhoNhan[0]["kho_id"];
                    }
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        },
        //Get danh sách lượt giao
        async getDs_Bienlai() {
            await api.getDs_Bienlai(this.axios, {
                "khonhan_id": this.dataSelected.kho_nhan_id,
                "khogiao_id": this.dataSelected.kho_giao_id,
                "total_row": 0,
                "tu_ngay": this.txtTuNgay,
                "page_num": 1,
                "den_ngay": this.txtDenNgay,
                "page_sz": 10
            }).then((res) => {
                this.options.ds_Bienlai = res.data.data.luot ? res.data.data.luot : [];
            });
        },
        xuatExcel: async function () {
            try {
                if (this.options.ds_Luotgiao.length < 1) {
                    this.$toast.warning("Bạn chưa chọn danh sách lượt giao biên lai trên lưới để in!");
                } else {
                    let data = []
                    this.options.ds_Luotgiao.forEach(item => {
                        data.push({
                            'Lượt giao biên lai': item.ten_loaibl,
                            'Loại lượt': item.loai_luot,
                            'Từ số': item.tu_so,
                            'Đến số': item.den_so,
                            'Tên kho giao': item.ten_kho_giao,
                            'Nhân viên giao': item.nhanvien_giao,
                        })
                    })

                    let worksheet = XLSX.utils.json_to_sheet(data)
                    let workbook = XLSX.utils.book_new()
                    XLSX.utils.book_append_sheet(workbook, worksheet, "Danh sách lượt giao")
                    XLSX.writeFile(workbook, "BBBL.xlsx")
                }
            } catch (ex) {
                this.$toast.error("Có lỗi trong quá trình xuất file Excel !" + ex)
            }
        },
        setValueFromRowSelect(row) {
            this.options.ds_Luotgiao.push(row);
            this.visiable.XuatBBBL = false;
        },
        //Khi click dòng trong grid danh sách lượt giao để in
        async gridLuotgiao_FocusedRowChanged(data) {
            try {
                if (data) {
                    let row = data.data;
                    this.setValueFromRowSelect(row);
                }
            } catch (err) {
                console.log(err);
            }
        },
        async gridLuotgiao_RowDeselected(data) {
            try {
                this.options.ds_Luotgiao.splice(this.options.ds_Luotgiao.indexOf(data.data), 1);
            } catch (err) {
                console.log(err);
            }
        },
        onChangeNVGiao: async function () {
            this.getDSKhoGiao(this.dataSelected.nv_giao_id);
        },
        onChangeNVNhan: async function () {
            this.getDSKhonNhan(this.dataSelected.nv_nhan_id);
        },
    }
}
</script>
