<template src="./ThietLapDoiTuongKhaoSat.html"></template>
<style src="./ThietLapDoiTuongKhaoSat.scss"></style>
<script>
import { mapActions, mapGetters } from "vuex";
export default {
    components: {},
    computed: {
        ...mapGetters("thietlapdoituongkhaosat", [
            "getdsDichVuKSGetter",
            "getdsDichVuKhongKSGetter",
            "getdsKieuLDKSGetter",
            "getdsKieuLDKhongKSGetter",
            "getdsTinhTrangHongKSGetter",
            "getdsTinhTrangHongKhongKSGetter"
        ])
    },
    mounted() {
        this.fetchData()
    },
    data() {
        return {
            header: {
                title: "THIẾT LẬP ĐỐI TƯỢNG KHẢO SÁT",
                list: [
                    { name: "Chăm sóc khách hàng", link: { name: "Ui.cards" } },
                    {
                        name: "Thiết lập đối tượng khảo sát",
                        link: { name: "Ui.buttons" }
                    }
                ]
            },
            fieldsDichVu: [
                {
                    fieldName: "TEN_DVVT",
                    headerText: "Dịch vụ",
                    allowFiltering: true
                },
            ],
            fieldsfieldsKieuLD: [
                {
                    fieldName: "TEN_KIEULD",
                    headerText: "Kiểu LĐ",
                    allowFiltering: true
                },
            ],
            fieldsTinhTrang: [
                {
                    fieldName: "TINHTRANG",
                    headerText: "Tình Trạng",
                    allowFiltering: true
                },
            ],
            loaiGrid: {
                DICH_VU: 1,
                KIEU_LD: 2,
                TINH_TRANG: 3,
            },
            gridDichVuKSSelectedValues: [],
            gridDichVuKhongKSSelectedValues: [],
            gridKieuLDKSSelectedValues: [],
            gridKieuLDKhongKSSelectedValues: [],
            gridTinhTrangHongKSSelectedValues: [],
            gridTinhTrangHongKhongKSSelectedValues: [],
        }
    },
    methods: {
        ...mapActions("thietlapdoituongkhaosat", [
            "getdsDichVu",
            "getdsKieuLD",
            "getdsTinhTrangHong",
            "setDanhSachKSKhongKS",
            "capNhapThietLap",
        ]),
        async fetchData () {
            await this.getdsDichVu(2)
            await this.getdsDichVu(1)
            await this.getdsKieuLD(2)
            await this.getdsKieuLD(1)
            await this.getdsTinhTrangHong(2)
            await this.getdsTinhTrangHong(1)
        },
        thayDoiTrangThaiKS (loaiGrid, setGan) {
            let dsKhongKSSelected = [];
            let dsKSSelected = [];
            let data = {};
            switch (loaiGrid) {
                //Dịch vụ
                case this.loaiGrid.DICH_VU:
                    dsKhongKSSelected = this.getListItem(
                        this.getdsDichVuKhongKSGetter,
                        "DICHVUVT_ID",
                        this.gridDichVuKhongKSSelectedValues
                    );
                    dsKSSelected = this.getListItem(
                        this.getdsDichVuKSGetter,
                        "DICHVUVT_ID",
                        this.gridDichVuKSSelectedValues
                    );
                    data = {
                        typeDanhSach: "dsDichVu",
                        field: "DICHVUVT_ID",
                        setGan: setGan,
                        itemArr: setGan == 1 ? dsKhongKSSelected : dsKSSelected,
                    }
                    if (setGan == 1) this.$refs.gridDichVuKhongKS.selectAllItem = false;
                    else this.$refs.gridDichVuKS.selectAllItem = false;
                    break;
                //Kiểu lắp đặt
                case this.loaiGrid.KIEU_LD:
                    dsKhongKSSelected = this.getListItem(
                        this.getdsKieuLDKhongKSGetter,
                        "KIEULD_ID",
                        this.gridKieuLDKhongKSSelectedValues
                    );
                    dsKSSelected = this.getListItem(
                        this.getdsKieuLDKSGetter,
                        "KIEULD_ID",
                        this.gridKieuLDKSSelectedValues
                    );
                    data = {
                        typeDanhSach: "dsKieuLD",
                        field: "KIEULD_ID",
                        setGan: setGan,
                        itemArr: setGan == 1 ? dsKhongKSSelected : dsKSSelected,
                    }
                    if (setGan == 1) this.$refs.gridKieuLDKhongKS.selectAllItem = false;
                    else this.$refs.gridKieuLDKS.selectAllItem = false;
                    break;
                //Tình trạng hỏng
                case this.loaiGrid.TINH_TRANG:
                    dsKhongKSSelected = this.getListItem(
                        this.getdsTinhTrangHongKhongKSGetter,
                        "TINHTRANG_ID",
                        this.gridTinhTrangHongKhongKSSelectedValues
                    );
                    dsKSSelected = this.getListItem(
                        this.getdsTinhTrangHongKSGetter,
                        "TINHTRANG_ID",
                        this.gridTinhTrangHongKSSelectedValues
                    );
                    data = {
                        typeDanhSach: "dsTinhTrangHong",
                        field: "TINHTRANG_ID",
                        setGan: setGan,
                        itemArr: setGan == 1 ? dsKhongKSSelected : dsKSSelected,
                    }
                    if (setGan == 1) this.$refs.gridTinhTrangHongKhongKS.selectAllItem = false;
                    else this.$refs.gridTinhTranHongKS.selectAllItem = false;
                    break;
            }
            this.setDanhSachKSKhongKS(data);
        },
        getListItem(arr, id, ids) {
            return arr.filter((x) => ids.includes(x[id]));
        },
        async capNhap () {
            let data = {
                dsDichVuVtId: this.getdsDichVuKhongKSGetter
                    .map(x => x.DICHVUVT_ID)
                    .join(),
                dsKieuLdId: this.getdsKieuLDKhongKSGetter
                    .map(x => x.KIEULD_ID)
                    .join(),
                dsTinhTrang: this.getdsTinhTrangHongKhongKSGetter
                    .map(x => x.TINHTRANG_ID)
                    .join(),
            }
            console.log("data: ", data)
            await this.capNhapThietLap(data)
        }
    },
}
</script>
