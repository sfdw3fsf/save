<template src="./TraCuuLichSuDieuChinhHoaDon.html"></template>
<style src="./TraCuuLichSuDieuChinhHoaDon.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import { mapActions, mapState, mapGetters } from "vuex";
import KyCuoc from "../components/KyCuoc.vue";
import PopupDocFile from "./popup-docfile.vue";
import xlsx from "xlsx";

export default {
    name: "TraCuuLichSuDieuChinhHoaDon",
    components: {
        breadcrumb,
        appKyCuoc: KyCuoc,
        PopupDocFile
    },
    async created() {
        this.clearData();
        this.fetchData();
    },
    async mounted() {},
    data() {
        return {
            header: {
                title: "Tra cứu lịch sử điều chỉnh hóa đơn",
                list: [
                    {
                        name: "Hóa đơn",
                        link: { name: "Ui.buttons" }
                    },
                    {
                        name: "Tra cứu lịch sử điều chỉnh hóa đơn",
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
            maTT: "",
            tuNgay: new Date(),
            denNgay: new Date(),
            checkTuNgay: false,
            checkDenNgay: false,
            listLichSu: [],
            listChiTiet: [],
            fieldDsCongNo: [
                {
                    fieldName: "STT",
                    headerText: "STT",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "KIEU_BIENDONG",
                    headerText: "Kiểu biến động",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "NGUOI_CN",
                    headerText: "Người tác động",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "NGAY_CN",
                    headerText: "Ngày tác động",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "MAY_CN",
                    headerText: "Máy tác động",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "GHICHU",
                    headerText: "Ghi chú",
                    allowFiltering: true,
                    allowSorting: true
                }
            ],
            fieldDsChiTiet: [
                {
                    fieldName: "KIEU_BIENDONG",
                    headerText: "Tên trường",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "THONGTINHD_CU",
                    headerText: "Giá trị cũ",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "THONGTINHD_MOI",
                    headerText: "Giá trị mới",
                    allowFiltering: true,
                    allowSorting: true
                }
            ]
        };
    },
    watch: {
        "thongTinTimKiem.selectedDonVi": {
            handler: async function(value) {
                await this.getNVQuanLy(value);
                this.getTuyenThu({
                    pNhanVienId: value
                });
            }
        },
        "thongTinTimKiem.selectedNhomKH": {
            handler: async function(value) {
                await this.getPhanLoaiKH(value);
                this.getDanhSachLoaiKH(value);
            }
        }
    },
    computed: {
        ...mapGetters("TraCuuLichSuDieuChinhHoaDon", ["listHTTTGetter"])
    },
    methods: {
        ...mapActions("TraCuuLichSuDieuChinhHoaDon", [
            "traCuuLichSu",
            "traCuuChiTiet",
            "clearData"
        ]),

        handleKyCuoc(e) {
            this.pKyCuoc = moment(e.p_kyhoadon).format("yyyyMM") + e.p_chuky_no;
        },

        async fetchData() {},

        onCongNoChange(dataItem) {},

        onCongNoRowChange(dataItem) {
            console.log({ dataItem });
            if (dataItem) {
                this.traCuuChiTietAction(dataItem.BDHDDT_ID);
            }
        },

        async traCuu() {
            if (this.maTT === "") {
                this.$root.toastError("Chưa nhập mã thanh toán!");
                this.listLichSu = [];
                this.listChiTiet = [];
                return;
            }
            this.loading(true);
            const data = {
                maTT: this.maTT,
                tuNgay: this.checkTuNgay
                    ? moment(this.tuNgay).format("DD/MM/yyyy")
                    : null,
                denNgay: this.checkDenNgay
                    ? moment(this.denNgay).format("DD/MM/yyyy")
                    : null
            };
            console.log({ data });
            try {
                const response = await this.traCuuLichSu(data);
                console.log({ response });
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data.length === 0
                ) {
                    this.$root.toastError(
                        "Không tìm thấy danh sách thỏa mãn điều kiện!"
                    );
                    this.listLichSu = [];
                    this.listChiTiet = [];
                } else {
                    response.data.data.forEach((item, index) => {
                        item.STT = index + 1;
                    });
                    this.listLichSu = response.data.data;
                    this.traCuuChiTietAction(this.listLichSu[0].BDHDDT_ID);
                    // this.$refs.grid1.setCurrentSelectedRow(0);
                }
            } catch (e) {
                this.$root.toastError(
                    "Không tìm thấy danh sách thỏa mãn điều kiện!"
                );
                console.log(e);
            } finally {
                this.loading(false);
            }
        },

        async traCuuChiTietAction(data) {
            this.loading(true);
            try {
                const response = await this.traCuuChiTiet(data);
                console.log({ response });
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data.length === 0
                ) {
                } else {
                    this.listChiTiet = response.data.data;
                    // this.$refs.grid2.setCurrentSelectedRow(0);
                }
            } catch (e) {
                console.log(e);
            } finally {
                this.loading(false);
            }
        },

        sendData(data) {},

        docFile() {
            this.$bvModal.show("modal-docfile");
        }
    }
};
</script>
