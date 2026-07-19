<template src="./GanDVChamSocKHL.html"></template>
<style src="./GanDVChamSocKHL.scss"></style>
<script>
import API from "../api/GanNVChamSocKHLApi";
import TiepNhanPhanAnhKhachHangApi from "../api/TiepNhanPhanAnhKhachHangApi";
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
    name: "GanDVChamSocKHL",
    provide: {
        multiselect: [CheckBoxSelection]
    },
    components: {
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
                "Mã KH": "MA_KH",
                "Tên khách hàng": "TEN_KH",
                "Địa chỉ KH": "DIACHI_KH",
                "Bình quân DT": "BINHQUAN_DT"
            },
            dsKHLSelecteds: [],
            dsKHLSelectCacheds: new Set()
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
            "dsDVKHL",
            "totalElements"
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
        this.clearDataDV();
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
                this.getDsDonVi({ loaiDichVuId: 2, phongDichVuId: 0 })
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
            "getDsDVKHL",
            "clearDataDV"
        ]),
        async DsDVKHL_gridChanged(args) {
            this.layDS(args.pageIndex + 1, args.pageSize);
        },
        dsDVKHLSelectedItemsChanged(value) {
            // for (const iterator of value) {
            //     this.dsKHLSelectCacheds.add(iterator);
            // }
            this.dsKHLSelecteds = value;
        },
        chukycuocChangeHandler(e) {
            this.thongtinKyHoaDon.kyHoaDonSelected =
                moment(e.p_kyhoadon).format("YYYYMM") + "01";
            this.thongtinKyHoaDon.chuKySelected = e.p_chuky_no;
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
        async onLayDSClicked() {
            this.setButton(4);
            this.layDS(1, null);
        },
        async layDS(pageNum, pageSize) {
            try {
                this.loading(true);
                let data = {
                    kieuDv: this.cboDaGan,
                    kyHoaDon: this.thongtinKyHoaDon.kyHoaDonSelected,
                    dsLoaiKhId: this.optLoaiKHSelected.join(),
                    dsKhLonId: this.optLoaiKHLonSelected.join(),
                    dsQuanId: this.dsQuan,
                    //donViId: this.optDonViSelected,
                    pageNum: pageNum,
                    pageSize: pageSize ? pageSize : this.$refs.gridKHL.pageSize,
                    totalRow: 1
                };
                await this.getDsDVKHL(data);
            } catch (e) {
                console.log(e);
            } finally {
                this.loading(false);
                this.setButton(5);
            }
        },
        async onGhiLaiClicked() {
            if (this.dsDVKHL.length <= 0) {
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
            if (this.optDonViSelected == -1) {
                this.$root.toastError("Bạn chưa chọn đơn vị để gán!");
                return;
            }
            this.setButton(4);
            try {
                this.loading(true);
                let data = {
                    donViId: this.optDonViSelected,
                    kyHoaDon: this.thongtinKyHoaDon.kyHoaDonSelected,
                    dsKhachHangId: this.dsKHLSelecteds.join()
                };
                let response = await API.ganDVChamSocKHL(this.axios, data);
                if (response.data.error_code === "BSS-00000000") {
                    this.$root.toastSuccess(
                        "Gán đơn vị chăm sóc khách hàng lớn thành công!"
                    );
                    this.cboDaGan = 1;
                    let data = {
                        kieuDv: this.cboDaGan,
                        kyHoaDon: this.thongtinKyHoaDon.kyHoaDonSelected,
                        dsLoaiKhId: this.optLoaiKHSelected.join(),
                        dsKhLonId: this.optLoaiKHLonSelected.join(),
                        dsQuanId: this.dsQuan,
                        //donViId: this.optDonViSelected,
                        pageNum: 1,
                        pageSize: this.$refs.gridKHL.pageSize,
                        totalRow: 1
                    };
                    await this.getDsDVKHL(data);
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
