<template src="./GanNVChamSocKHL.html"></template>
<style src="./GanNVChamSocKHL.scss"></style>
<script>
import API from "../api/GanNVChamSocKHLApi";
import TiepNhanPhanAnhKhachHangApi from "../api/TiepNhanPhanAnhKhachHangApi";
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import KyCuoc from "@/components/KyCuoc.vue";
import { mapActions, mapState, mapGetters } from "vuex";
import select2 from "@/components/Select2.vue";
import { CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns";
import { L10n } from "@syncfusion/ej2-base";
import EventBus from "@/utils/eventBus";
import UploadDSGan from "./popup/UploadDSGan.vue";
import downloadexcel from "vue-json-excel";
L10n.load({
    "vi-VN": {
        dropdowns: {
            noRecordsTemplate: "Không có dữ liệu",
            actionFailureTemplate: ""
        }
    }
});
export default {
    name: "GanNVChamSocKHL",
    provide: {
        multiselect: [CheckBoxSelection]
    },
    components: {
        breadcrumb,
        KyCuoc,
        appSelect2: select2,
        appUploadDSGan: UploadDSGan,
        downloadexcel: downloadexcel
    },
    data() {
        return {
            optChuongTrinhCSKH: [],
            optChuongTrinhCSKHSelected: 0,
            optLoaiKHSelected: [],
            optLoaiKHLonSelected: [],
            optQuanHuyenSelected: [],
            optDonViSelected: -1,
            optNhanVienSelected: -1,
            optChuongTrinhCSKHSelected: -1,
            cboQuanHuyenEnabled: false,
            cboDaGan: 0,
            btnStatus: {
                tsbtnLayDS: true,
                tsbtnGhiLai: true
            },
            exportExcelFields: {
                "Tên chương trình": "TEN_CT",
                "Mã KH": "MA_KH",
                "Tên khách hàng": "TEN_KH",
                "Địa chỉ KH": "DIACHI_KH",
                "Bình quân DT": "BINHQUAN_DT"
            },
            dsKHLSelecteds: []
        };
    },
    computed: {
        ...mapState("qltnCommon", ["thongtinKyHoaDon"]),
        ...mapState("tiepnhanphananhkhachhang", ["dsLoaiKH", "dsLoaiKHLon"]),
        ...mapState("gannvchamsockhl", [
            "dsQuanHuyen",
            "dsDonVi",
            "dsNhanVien",
            "dsChuongTrinhCSKH",
            "dsKHL",
            "dsKHLTotalElements"
        ]),
        chuKyHoaDon() {
            return moment(
                this.thongtinKyHoaDon.kyHoaDonSelected,
                "YYYYMMDD"
            ).format("MM/YYYY");
        },
        dsQuan() {
            return this.cboQuanHuyenEnabled
                ? this.optQuanHuyenSelected.join()
                : "";
        }
    },
    created() {
        this.clearData();
        EventBus.$on("chukycuocChange", this.chukycuocChangeHandler);
    },
    async mounted() {
        try {
            this.loading(true);
            // await this.getDsChuongTrinhCSKH();
            await Promise.all([
                this.getDsLoaiKH(),
                this.getDsLoaiKHLon(),
                this.getDsQuanHuyen(),
                this.getDsDonVi({ loaiDichVuId: 2, phongDichVuId: 0 }),
                this.getDsChuongTrinhCSKH({
                    kieu: 1,
                    pageNo: 1,
                    pageSize: 1000
                })
            ]);
            // await this.getDsLoaiKH();
            // await this.getDsLoaiKHLon();
            // await this.getDsQuanHuyen();
            // await this.getDsDonVi({ loaiDichVuId: 2, phongDichVuId: 0 });
            // await this.getDsChuongTrinhCSKH({
            //     kieu: 1,
            //     pageNo: 1,
            //     pageSize: 1000,
            // });

            this.optLoaiKHSelected = this.dsLoaiKH.map(x => x.id);
            this.optLoaiKHLonSelected = this.dsLoaiKHLon.map(x => x.id);
            this.optQuanHuyenSelected = this.dsQuanHuyen.map(x => x.id);
            this.optDonViSelected =
                this.dsDonVi.length > 0 ? this.dsDonVi[0].id : -1;
            this.optChuongTrinhCSKHSelected =
                this.dsChuongTrinhCSKH.length > 0
                    ? this.dsChuongTrinhCSKH[0].id
                    : -1;
        } catch (e) {
        } finally {
            this.loading(false);
        }
    },
    methods: {
        ...mapActions("tiepnhanphananhkhachhang", [
            "getDsLoaiKH",
            "getDsLoaiKHLon"
        ]),
        ...mapActions("gannvchamsockhl", [
            "getDsQuanHuyen",
            "getDsDonVi",
            "getDsNhanVien",
            "getDsChuongTrinhCSKH",
            "getDsKHL",
            "clearData"
        ]),
        dsKHLSelectedItems(value) {
            this.dsKHLSelecteds = value;
        },
        chukycuocChangeHandler(e) {
            this.thongtinKyHoaDon.kyHoaDonSelected =
                moment(e.p_kyhoadon).format("YYYYMM") + "01";
            this.thongtinKyHoaDon.chuKySelected = e.p_chuky_no;
        },
        DSKhachHang_gridChanged(args) {
            this.layDS(args.pageIndex + 1, args.pageSize);
        },
        setButton(kieu) {
            if (kieu == 4) {
                this.btnStatus.tsbtnLayDS = false;
                this.btnStatus.tsbtnGhiLai = false;
            } else if (kieu == 5) {
                this.btnStatus.tsbtnLayDS = true;
                this.btnStatus.tsbtnGhiLai = true;
            }
        },
        async onDsDonViChanged() {
            // this.loading(true)
            try {
                await this.getDsNhanVien({
                    donViId: this.optDonViSelected,
                    pageNo: 1,
                    pageSize: 10000
                });
                this.optNhanVienSelected =
                    this.dsNhanVien.length > 0 ? this.dsNhanVien[0].id : -1;
            } catch (e) {
                console.log(e);
               this.optNhanVienSelected = -1;
            } finally {
                // this.loading(false)
            }
        },
        async onLayDSClicked() {
            this.setButton(4);
            try {
                this.loading(true);
                let data = {
                    kieuNv: this.cboDaGan,
                    kyHoaDon: this.thongtinKyHoaDon.kyHoaDonSelected,
                    dsLoaiKhId: this.optLoaiKHSelected.join(),
                    dsKhLonId: this.optLoaiKHLonSelected.join(),
                    dsQuanId: this.dsQuan,
                    donViId: this.optDonViSelected,
                    pageNum: 1,
                    pageSize: this.$refs.gridKHL.pageSizeData
                        ? this.$refs.gridKHL.pageSizeData
                        : this.$refs.gridKHL.pageSize,
                    totalRow: 1
                };
                await this.getDsKHL(data);
            } catch (e) {
            } finally {
                this.loading(false);
                this.setButton(5);
            }
        },
        async layDS(pageNum, pageSize) {
            this.setButton(4);
            try {
                this.loading(true);
                let data = {
                    kieuNv: this.cboDaGan,
                    kyHoaDon: this.thongtinKyHoaDon.kyHoaDonSelected,
                    dsLoaiKhId: this.optLoaiKHSelected.join(),
                    dsKhLonId: this.optLoaiKHLonSelected.join(),
                    dsQuanId: this.dsQuan,
                    donViId: this.optDonViSelected,
                    pageNum: pageNum,
                    pageSize: pageSize ? pageSize : this.$refs.gridKHL.pageSize,
                    totalRow: 1
                };
                await this.getDsKHL(data);
            } catch (e) {
            } finally {
                this.loading(false);
                this.setButton(5);
            }
        },
        async onGhiLaiClicked() {
            console.log(this.dsKHLSelecteds);
            if (this.dsKHL.length <= 0) {
                this.$root.toastError(
                    "Hãy lấy danh sách khách hàng trước khi ghi lại!"
                );
                return;
            }
            if (this.dsKHLSelecteds.length <= 0) {
                this.$root.toastError(
                    "Bạn chưa chọn khách hàng trên lưới để cập nhật!"
                );
                return;
            }
            if (this.optNhanVienSelected == -1) {
                this.$root.toastError("Bạn chưa chọn nhân viên để gán!");
                return;
            }
            if (this.optChuongTrinhCSKHSelected == -1) {
                this.$root.toastError("Bạn chưa chọn chương trình để gán!");
                return;
            }
            this.setButton(4);
            try {
                this.loading(true);

                let data = {
                    kyHoaDon: this.thongtinKyHoaDon.kyHoaDonSelected,
                    dsKhachHangId: this.dsKHLSelecteds.join(),
                    nhanVienId: this.optNhanVienSelected,
                    ctcsKhId: this.optChuongTrinhCSKHSelected
                };
                let response = await API.ganNVChamSocKHL(this.axios, data);
                if (response.data.error_code === "BSS-00000000") {
                    this.$root.toastSuccess(
                        "Gán nhân viên, chương trình chăm sóc khách hàng lớn thành công!"
                    );
                    let data = {
                        kieuNv: this.cboDaGan,
                        kyHoaDon: this.thongtinKyHoaDon.kyHoaDonSelected,
                        dsLoaiKhId: this.optLoaiKHSelected.join(),
                        dsKhLonId: this.optLoaiKHLonSelected.join(),
                        dsQuanId: this.dsQuan,
                        donViId: this.optDonViSelected,
                        pageNum: 1,
                        pageSize: this.$refs.gridKHL.pageSizeData
                            ? this.$refs.gridKHL.pageSizeData
                            : this.$refs.gridKHL.pageSize,
                        totalRow: 1
                    };
                    await this.getDsKHL(data);
                } else {
                    this.$root.toastError(
                        "Có lỗi: " + response.data.message ||
                            response.data.error
                    );
                }
            } catch (e) {
                console.log(e);
                this.$root.toastError("Có lỗi: " + e.data.message);
            } finally {
                this.loading(false);
                this.setButton(5);
                // await this.onGhiLaiClicked()
            }
        }
    },
    destroyed() {
        EventBus.$off("chukycuocChange", this.chukycuocChangeHandler);
    }
};
</script>
