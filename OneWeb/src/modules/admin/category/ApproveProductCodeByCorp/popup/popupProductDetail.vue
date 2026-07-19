<template src="./popupProductDetail.html"></template>

<script>
import API from '../ApproveProductCodeByCorpAPI';

export default {
    name: "popupProductDetail",
    props: ["SanPham"],
    data() {
        return {
            DmChung: [],
            dsSpdv: [],
            dsPtPhatTrien: [],
            dsDonVi: [],
            dsTrangThai: [],
            dsDichVuCLTV: [],
            SanPhamSelected: null,
            searchInfo: {
                nhomspdv_id: 0,
                phuongthuc_id: 0,
                donvi_id: 0,
                trangthai_id: 1,
                dv_clvt_id: 1,
                tag: 1,
                ma_spdv: '',
                ten_spdv: ''
            },
        }
    },
    methods: {
        async layDanhMucChung() {
            let data = {};
            //init danh mục
            // this.dsSpdv.push({ nhomspdv_id: 0, ten_nhomspdv: "-Chọn tất cả-" });
            API.getDanhMucChung(this.axios, data)
                .then((response) => {
                    this.DmChung = response.data.data;
                    //nhóm SPDV
                    let items = [];
                    this.DmChung.NHOM_SPDV.forEach(function (item) {
                        items.push({
                            nhomspdv_id: item.nhomspdv_id,
                            ten_nhomspdv: item.ten_nhomspdv,
                        });
                    });
                    this.dsSpdv.push(...items);
                    //PT phát triển
                    items = [];
                    this.DmChung.PHUONGTHUC_PT.forEach(function (item) {
                        items.push({
                            phuongthuc_id: item.phuongthuc_id,
                            ten_phuongthuc: item.ten_phuongthuc,
                        });
                    });
                    this.dsPtPhatTrien.push(...items);
                    //đơn vị chủ quản
                    items = [];
                    this.DmChung.DONVI.forEach(function (item) {
                        items.push({
                            donvi_id: item.donvi_id,
                            ten_dv: item.ten_dv,
                        });
                    });
                    this.dsDonVi.push(...items);
                    this.searchInfo.donvi_id = this.dsDonVi[0].donvi_id;
                    //trạng thái
                    items = [];
                    this.DmChung.TRANGTHAI.forEach(function (item) {
                        items.push({
                            trangthai_id: item.trangthai_id,
                            trangthai_spdv: item.trangthai_spdv,
                        });
                    });
                    this.dsTrangThai.push(...items);
                })
                .catch((error) => {
                    // console.log(error);
                });
        },
        async pheDuyet_Tct() {
            if (this.SanPham == undefined) {
                this.$toast.warning("Vui lòng chọn sản phẩm");
                return;
            }
            let data = { "spdv_id": this.SanPham.spdv_id };
            API.pheDuyetCapTct(this.axios, data)
                .then((response) => {
                    if (response.data.error_code === "BSS-00000000") {
                        this.$toast.success(response.data.message);
                    }
                    else {
                        this.$toast.warning(response.data.message);

                    }
                })
                .catch((error) => {
                    this.$toast.show('Trạng thái: ' + response.data.data)
                });
        },
        async khongPheDuyet_Tct() {
            if (this.SanPham == undefined) {
                this.$toast.warning("Vui lòng chọn sản phẩm");
                return;
            }
            this.$bvModal.show('popupKoDuyet');
            // let data = { "spdv_id": this.SanPham.spdv_id };
            // API.tuChoiDuyetCapTct(this.axios, data)
            //     .then((response) => {
            //         if (response.data.error_code === "BSS-00000000") {
            //             this.$toast.success(response.data.message);
            //         }
            //         else {
            //             this.$toast.warning(response.data.message);

            //         }
            //     })
            //     .catch((error) => {
            //         // this.$toast.info(response.message);
            //     });
        },
        async guiTapDoanDuyet() {
            if (this.SanPham == undefined) {
                this.$toast.warning("Vui lòng chọn sản phẩm");
                return;
            }
            let data = { "spdv_id": this.SanPham.spdv_id };
            API.guiPheDuyetCapTapDoan(this.axios, data)
                .then((response) => {
                    if (response.data.error_code === "BSS-00000000") {
                        this.$toast.success(response.data.message);
                    }
                    else {
                        this.$toast.warning(response.data.message);

                    }
                })
                .catch((error) => {
                    this.$toast.warning(response.data.message);
                });
        }
    },
    async mounted() {
        await this.layDanhMucChung();
        // await console.log(this.SanPham);
        // await console.log(this.DmChung.NHOM_SPDV);
        // await console.log(this.DmChung.DM_DICHVU_CLVT);
    },
}
</script>