<template src="./TaoDuLieuXacNhanCongNo.html"></template>
<style src="./TaoDuLieuXacNhanCongNo.scss">

</style>
<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import { mapActions, mapState, mapGetters } from "vuex";
import KyCuoc from "../components/KyCuoc.vue";
import xlsx from "xlsx";
import PopupDocFile from "./popup-docfile.vue";
import ModalXuatDuLieu from "./PopupXuatDuLieuBC";
// import ModalXuatDuLieu from "../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu/index.vue";

export default {
    name: "TaoDuLieuXacNhanCongNo",
    components: {
        breadcrumb,
        appKyCuoc: KyCuoc,
        PopupDocFile,
        appModalXuatDuLieu: ModalXuatDuLieu
    },
    async created() {
        $(function () {
            $(".excel").hover(
                function () {
                    console.log(123);
                    $(".neo").addClass("show");
                },
                function () {
                    $(".neo").removeClass("show");
                }
            );
        });
        this.clearData();
        // await this.getChuKyAction();
        await this.getChuKyAction(moment(this.kyHoaDon).format("YYYYMM"));
        await this.getLanTaoCongNo();
        // this.fetchData();
    },
    async mounted() { },
    data() {
        return {
            header: {
                title: "Tạo dữ liệu xác nhận công nợ",
                list: [
                    {
                        name: "Kiểm kê công nợ",
                        link: { name: "Ui.buttons" }
                    },
                    {
                        name: "Tạo dữ liệu xác nhận công nợ",
                        link: { name: "Ui.buttons" }
                    }
                ]
            },
            config: {
                dateConfig: {
                    value: new Date(),
                    format: "dd/MM/yyyy",
                    max: new Date()
                },
                max_kyhoadon: moment(new Date())
                    .subtract(1, "months")
                    .endOf("month")
                    .toDate()
            },
            kyHoaDon: moment(new Date())
                .subtract(1, "months")
                .endOf("month")
                .toDate(),
            kyCuoc:
                moment(new Date())
                    .subtract(1, "months")
                    .format("YYYYMM") + "01",
            listLanTao: [],
            ghiChu: "",
            thoiGian: " ",
            p_chuky_no_options: [],
            p_chuky_no: "01",
            soLuongTao: "",
            lanTao: "",
            Rec_lantao: 0,
            exportParams: {},
            fieldLanTao: [
                {
                    fieldName: "NGAYTAO",
                    headerText: "Ngày tạo",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "NGUOI_CN",
                    headerText: "Người tạo",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "KYHOADON",
                    headerText: "Kỳ HĐ",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "SOLUONG",
                    headerText: "Số lượng",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "GHICHU",
                    headerText: "Ghi chú",
                    allowFiltering: true,
                    allowSorting: true
                }
            ],
            fieldTongHop: [
                {
                    fieldName: "LANTAO_ID",
                    headerText: "LANTAO_ID",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "KYHOADON",
                    headerText: "KYHOADON",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "MA_TT",
                    headerText: "MA_TT",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TEN_TT",
                    headerText: "TEN_TT",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "DIACHI_TT",
                    headerText: "DIACHI_TT",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "DIACHI_CT",
                    headerText: "DIACHI_CT",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "SO_DT",
                    headerText: "SO_DT",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "FAX",
                    headerText: "FAX",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "MA_TUYEN",
                    headerText: "MA_TUYEN",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "MA_NVTC",
                    headerText: "MA_NVTC",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TEN_CTY",
                    headerText: "TEN_CTY",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "DIACHI_CTY",
                    headerText: "DIACHI_CTY",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "NGUOINHAN",
                    headerText: "NGUOINHAN",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TEN_DV",
                    headerText: "TEN_DV",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "SO_DT_NN",
                    headerText: "SO_DT_NN",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "FAX_NN",
                    headerText: "FAX_NN",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "EMAIL_NN",
                    headerText: "EMAIL_NN",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "CHUCDANH",
                    headerText: "CHUCDANH",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "NGUOIKY",
                    headerText: "NGUOIKY",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "THUTU_IN",
                    headerText: "THUTU_IN",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "MAVACH",
                    headerText: "MAVACH",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "TEN_LOAIKH",
                    headerText: "TEN_LOAIKH",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TEN_PLKH",
                    headerText: "TEN_PLKH",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "NHOM_KH",
                    headerText: "NHOM_KH",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TEN_NHOMKH",
                    headerText: "TEN_NHOMKH",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TONGNO",
                    headerText: "TONGNO",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "NODUONG",
                    headerText: "NODUONG",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "NOAM",
                    headerText: "NOAM",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "TONGNO01",
                    headerText: "TONGNO01",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },

                {
                    fieldName: "THOIHAN_IN",
                    headerText: "THOIHAN_IN",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "THOIHAN_GUI",
                    headerText: "THOIHAN_GUI",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "THOIHAN_LH",
                    headerText: "THOIHAN_LH",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "THOIHAN_PH",
                    headerText: "THOIHAN_PH",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "THOIHAN_HT",
                    headerText: "THOIHAN_HT",
                    allowFiltering: true,
                    allowSorting: true
                }
            ],

            fieldChiTiet: [
                {
                    fieldName: "KYHOADON",
                    headerText: "KYHOADON",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "MA_TT",
                    headerText: "MA_TT",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TEN_TT",
                    headerText: "TEN_TT",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "DIACHI_TT",
                    headerText: "DIACHI_TT",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "DIACHI_CT",
                    headerText: "DIACHI_CT",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "SO_DT",
                    headerText: "SO_DT",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "FAX",
                    headerText: "FAX",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "MA_TUYEN",
                    headerText: "MA_TUYEN",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "MA_NVTC",
                    headerText: "MA_NVTC",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TEN_CTY",
                    headerText: "TEN_CTY",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "DIACHI_CTY",
                    headerText: "DIACHI_CTY",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "NGUOINHAN",
                    headerText: "NGUOINHAN",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TEN_DV",
                    headerText: "TEN_DV",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "SO_DT_NN",
                    headerText: "SO_DT_NN",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "FAX_NN",
                    headerText: "FAX_NN",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "EMAIL_NN",
                    headerText: "EMAIL_NN",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "CHUCDANH",
                    headerText: "CHUCDANH",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "NGUOIKY",
                    headerText: "NGUOIKY",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "THUTU_IN",
                    headerText: "THUTU_IN",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "MAVACH",
                    headerText: "MAVACH",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "THOIHAN_IN",
                    headerText: "THOIHAN_IN",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "THOIHAN_GUI",
                    headerText: "THOIHAN_GUI",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "THOIHAN_LH",
                    headerText: "THOIHAN_LH",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "THOIHAN_PH",
                    headerText: "THOIHAN_PH",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "THOIHAN_HT",
                    headerText: "THOIHAN_HT",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TEN_LOAIKH",
                    headerText: "TEN_LOAIKH",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TEN_PLKH",
                    headerText: "TEN_PLKH",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "NHOM_KH",
                    headerText: "NHOM_KH",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TEN_NHOMKH",
                    headerText: "TEN_NHOMKH",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TEN_NHOM",
                    headerText: "TEN_NHOM",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TONGNO",
                    headerText: "TONGNO",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "NODUONG",
                    headerText: "NODUONG",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "NOAM",
                    headerText: "NOAM",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "TONGNO01",
                    headerText: "TONGNO01",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "CHUKYNO",
                    headerText: "CHUKYNO",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "THOIHAN_IN1",
                    headerText: "THOIHAN_IN1",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "THOIHAN_GUI1",
                    headerText: "THOIHAN_GUI1",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "THOIHAN_LH1",
                    headerText: "THOIHAN_LH1",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "THOIHAN_PH1",
                    headerText: "THOIHAN_PH1",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "THOIHAN_HT1",
                    headerText: "THOIHAN_HT1",
                    allowFiltering: true,
                    allowSorting: true
                }
            ],
            listTongHop: []
        };
    },
    updated: function () { },
    computed: {
        ...mapGetters("TaoDuLieuXacNhanCongNo", []),
        dsThuocTinhTongHop() {
            return this.fieldTongHop;
        },
        dsThuocTinhChiTiet() {
            return this.fieldChiTiet;
        }
    },
    watch: {},
    methods: {
        ...mapActions("TaoDuLieuXacNhanCongNo", [
            "getLanTao",
            "capNhatTuFile",
            "taoCongNo",
            "getChuKy",
            "getListCongNo",
            "clearData"
        ]),

        onKyHoaDonChange: function (args) {
            console.log(args.value);
            this.kyCuoc = moment(args.value).format("YYYYMM") + this.p_chuky_no;
            if (args.value === null) {
                this.$root.toastError("Vui lòng chọn Kỳ hóa đơn");
                return;
            }
            this.getChuKyAction(moment(args.value).format("YYYYMM"));
        },

        async fetchData() {
            // this.getLanTaoCongNo();
        },

        async getChuKyAction(data) {
            try {
                const response = await this.getChuKy(data);
                if (response.data.error_code === "BSS-00000000") {
                    console.log({ response });
                    this.p_chuky_no_options = response.data.data;
                    this.p_chuky_no = this.p_chuky_no_options[0].CHUKY;
                } else {
                    this.$root.toastWarning(response.message);
                }
            } catch (e) {
                console.log({ error: e });
            } finally {
                this.loading(false);
            }
        },

        msToHMS(duration) {
            var milliseconds = parseInt((duration % 1000) / 100),
                seconds = parseInt((duration / 1000) % 60),
                minutes = parseInt((duration / (1000 * 60)) % 60),
                hours = parseInt((duration / (1000 * 60 * 60)) % 24);

            hours = hours < 10 ? "0" + hours : hours;
            minutes = minutes < 10 ? "0" + minutes : minutes;
            seconds = seconds < 10 ? "0" + seconds : seconds;
            this.thoiGian = hours + ":" + minutes + ":" + seconds;
            this.soLuongTao = this.listLanTao.length;
        },

        async taoCongNoAction() {
            this.clickTaoDL = true;
            try {
                this.loading(true);
                const data = {
                    kyCuoc: this.kyCuoc,
                    ghiChu: this.ghiChu
                };
                var t0 = performance.now();
                const response = await this.taoCongNo(data);
                var t1 = performance.now();
                const thoiGianXyly = t1 - t0;
                this.msToHMS(thoiGianXyly);
                console.log({ time: t1 - t0 });
                if (response.data.error_code === "BSS-00000000") {
                    console.log({ response });
                    this.$refs.grid1.setCurrentSelectedRow(0);
                    // response.data.data.map(
                    //      item =>
                    //         (item.SOLUONG = Number(item.SOLUONG).toLocaleString(
                    //             "en-GB"
                    //          ))
                    //  );
                    //  this.listLanTao = response.data.data;
                    this.getLanTaoCongNo();
                    this.$root.toastSuccess("Tạo công nợ thành công!");
                } else {
                    this.$root.toastError(response.data.message_detail);
                }
            } catch (e) {
                this.$root.toastError("Đã có lỗi xảy ra");
                console.log(e);
            } finally {
                this.loading(false);
            }
        },

        async getLanTaoCongNo() {
            try {
                // this.loading(true);
                const data = {
                    kyCuoc: this.kyCuoc
                };
                const response = await this.getLanTao();

                if (response.data.error_code === "BSS-00000000") {
                    console.log({ response });
                    // this.listLanTao = response.data.data;
                    response.data.data.map(
                        item =>
                        (item.SOLUONG = Number(item.SOLUONG).toLocaleString(
                            "en-GB"
                        ))
                    );
                    this.listLanTao = response.data.data;
                    // this.$root.toastSuccess(
                    //     "Lấy danh sách lần tạo công nợ thành công!"
                    // );
                } else {
                    this.$root.toastError("Đã có lỗi xảy ra!");
                }
            } catch (e) {
                this.$root.toastError("Đã có lỗi xảy ra!");
                console.log({ error: e });
            } finally {
                this.loading(false);
            }
        },

        async getCongNoXuatFile(type) {
            try {
                this.loading(true);
                const data = {
                    lanTaoId: this.lanTao,
                    chiTiet: type,
                    pageSize: 3000,
                    pageNum: 0,
                    totalRow: 0,
                    total: this.Rec_lantao
                };
                this.exportParams = data;
                if (type == 0) {
                    this.$refs.dlgXuatDuLieuBCTongHop.openDialog();
                } else {
                    this.$refs.dlgXuatDuLieuBCChiTiet.openDialog();
                }
                // const response = await this.getListCongNo(data);

                // if (response.data.error_code === "BSS-00000000") {
                //     console.log({ response });
                //     this.listTongHop = response.data.data.data;
                //     console.log(this.listTongHop);
                //     if (type == "0") {
                //         this.$bvModal.show("modal-xuat-dulieu");
                //     } else {
                //         this.$bvModal.show("modal-xuat-dulieu-chitiet");
                //     }
                // } else {
                // }
            } catch (e) {
                this.$root.toastError("Đã có lỗi xảy ra!");
                console.log({ error: e });
            } finally {
                this.loading(false);
            }
        },

        onPhieuThanhToanChange(dataItem) {
            console.log(dataItem);
            this.listIdMaTT = dataItem;
        },

        onPhieuThanhToanRowChange(dataItem) {
            if (dataItem === null) return
            this.lanTao = dataItem.LANTAO_ID;
            let timeString = dataItem.KYHOADON;
            this.Rec_lantao = dataItem.SOLUONG.replaceAll(',', '');
            const year = timeString.substring(0, 4);
            const month = timeString.substring(4, 6);
            this.kyHoaDon = moment(new Date(year, month))
                .subtract(1, "months")
                .endOf("month")
                .toDate();
            console.log({ timeString });
            this.ghiChu = dataItem.GHICHU;
            console.log(dataItem);
        },

        DocFile() {
            this.$bvModal.show("modal-docfile");
        },

        async sendData(data) {
            this.$root.toastSuccess("Thành công!");
            console.log(data);
            const dataLog = {
                kyCuoc: this.kyCuoc,
                dsChiTiet: data,
                soCV: null,
                soLuong: data.length,
                ghiChu: this.ghiChu
            };
            const response = await this.capNhatTuFile(dataLog);
            if (response.data.error_code === "BSS-00000000") {
                this.$root.toastSuccess("Thành công!");
            } else {
                this.$root.toastError("Đã có lỗi xảy ra!");
            }
            console.log(response);
        },

        showAlert(type) {
            console.log({ type });
            // this.$bvModal.show("modal-xuat-dulieu");
            this.getCongNoXuatFile(type);
        }
    }
};
</script>
