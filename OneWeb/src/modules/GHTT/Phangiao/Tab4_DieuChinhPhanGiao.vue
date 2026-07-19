<template>
    <div class=" nocorner bor0 pad0 bg-none" style="box-shadow: none;">
        <div id="tab2" class="tab-pane">
            <div class="tab-content">
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="box-form">
                            <div class="legend-title">Thông tin nhân viên điều chỉnh</div>
                            <div class="row">
                                <div class="col-sm-12 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Chương trình</div>
                                        <div class="value">
                                            <!-- <select2 v-model="listbox.loai_gt.value" class="select-custom" :options="listbox.loai_gt.list.map(gt=>({id:gt.loaigt_id,text:gt.loai_gt}))"></select2> -->
                                            <select2></select2>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Phòng</div>
                                        <div class="value">
                                            <!-- <select2 v-model="listbox.loai_gt.value" class="select-custom" :options="listbox.loai_gt.list.map(gt=>({id:gt.loaigt_id,text:gt.loai_gt}))"></select2> -->
                                            <select2></select2>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Nhân viên điều chỉnh</div>
                                        <div class="value">
                                            <!-- <select2 v-model="listbox.loai_gt.value" class="select-custom" :options="listbox.loai_gt.list.map(gt=>({id:gt.loaigt_id,text:gt.loai_gt}))"></select2> -->
                                            <select2></select2>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--left -->
                    <div class="col-sm-6 col-12">
                        <div class="box-form">
                            <div class="row">
                                <div class="legend-title">Thông tin nhân viên tiếp nhận</div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Xử lý từ ngày</div>
                                        <div class="value">
                                            <!-- <select2 v-model="listbox.loai_gt.value" class="select-custom" :options="listbox.loai_gt.list.map(gt=>({id:gt.loaigt_id,text:gt.loai_gt}))"></select2> -->
                                            <select2></select2>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Xử lý đến ngày</div>
                                        <div class="value">
                                            <!-- <select2 v-model="listbox.loai_gt.value" class="select-custom" :options="listbox.loai_gt.list.map(gt=>({id:gt.loaigt_id,text:gt.loai_gt}))"></select2> -->
                                            <select2></select2>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-12 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Phòng</div>
                                        <div class="value">
                                            <!-- <select2 v-model="listbox.loai_gt.value" class="select-custom" :options="listbox.loai_gt.list.map(gt=>({id:gt.loaigt_id,text:gt.loai_gt}))"></select2> -->
                                            <select2></select2>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-12 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Nhân viên tiếp nhận</div>
                                        <div class="value">
                                            <!-- <select2 v-model="listbox.loai_gt.value" class="select-custom" :options="listbox.loai_gt.list.map(gt=>({id:gt.loaigt_id,text:gt.loai_gt}))"></select2> -->
                                            <select2></select2>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="box-move-select-table">
                        <div class="box-col">
                            <div class="legend-title">Danh sách thuê bao của nhân viên cần điều chỉnh</div>
                            <div class="table-content" style="height: 250px">
                                <vue-table v-model="currentRecordAreaDeliver" :options="dsDichVuVTDaGan"
                                    :config="areaHeaderDeliver" :unique="'dichvuvt_id'" :multiple="true"
                                    @input="onCheckBoxDeliver">
                                </vue-table>
                            </div>
                            <!-- :unique="'kieuld_id'"  -->
                            <!-- danhSachKhuVucDaGan -->
                            <!-- <div class="table-content" style="height: 200px;">
                                <grid-tab41></grid-tab41>
                            </div> -->
                        </div>
                        <div class="actions">
                            <button class="btn" @click="onCheckBoxDeliverRemove">
                                <span class="fa fa-angle-right"></span>
                            </button>
                            <button class="btn" @click="handDataRight">
                                <span class="fa fa-angle-double-right"></span>
                            </button>
                            <button class="btn" @click="onCheckBoxNotDeliverToDeliver">
                                <span class="fa fa-angle-left"></span>
                            </button>
                            <button class="btn" click="handDataLeft">
                                <span class="fa fa-angle-double-left"></span>
                            </button>
                        </div>
                        <div class="box-col">
                            <div class="legend-title">Danh sách thuê bao gán thêm cho nhân viên tiếp nhận</div>
                            <div class="table-content" style="height: 200px;">
                                <!-- <grid-tab42></grid-tab42> -->
                                <vue-table v-model="currentRecordAreaNotDeliver" :options="dsDichVuVTChuaGan"
                                    :config="areaHeaderNotDeliver" :unique="'dichvuvt_id'" :multiple="true"
                                    @input="onCheckBoxNotDeliver">
                                </vue-table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import GridTab41 from '@/modules/ghtt/phangiao/GridTab41.vue';
import GridTab42 from '@/modules/ghtt/phangiao/GridTab42.vue';

import { mapActions, mapMutations } from "vuex";
import gridview from "@/components/Shared/gridview.vue";
import select3 from "@/components/form/VueSelect.vue";



// import { CommonGhtt } from '@/store/ghtt/commons/index.js'
//import { promotionDetail } from './promotion/index'

// export const promotion = {
//   modules: {
//     CommonGhtt
//    // promotionDetail
//   }
// }

export default
    {
        components: {
            // 'ejs-tab': TabComponent,
            // 'e-tabitem': TabItemDirective,
            // 'e-tabitems': TabItemsDirective
            'grid-tab41': GridTab41
            , 'grid-tab42': GridTab42
            , gridview
            , select3
            // , CommonGhtt
            // , Promotion
            // , InstallationType
        },

        name: "ghtt",

        data: () => ({
            currentRecordAreaNotDeliver: null,
            currentRecordAreaDeliver: null,
            areaHeaderDeliver: [
                {
                    label: "Dịch vụ VT đã gán",
                    filterAble: true,
                    key: "ten_dvvt",
                },
            ],
            areaHeaderNotDeliver: [
                {
                    label: "Dịch vụ VT chưa gán",
                    filterAble: true,
                    key: "ten_dvvt",
                },
            ],
            tempAreaDeliver: [],
            tempAreaNotDeliver: [],
            
        }),
        computed: {
            dsDichVuVTDaGan() {
                return this.$store.getters["CommonGhtt/dsDichVuVTDaGan"];

            },
            // danhSachKhuVucDaGan() {
            //     return JSON.parse(
            //         JSON.stringify(
            //             this.$store.getters["CommonGhtt/danhSachKhuVucDaGan"]
            //         )
            //     );
            // },
            dsDichVuVTChuaGan() {
                return this.$store.getters["CommonGhtt/dsDichVuVTChuaGan"];
            },

            // listPromotion() {
            //     this.promotionsCurrentRecord =
            //         this.$store.getters["CommonGhtt/listPromotion"][0];
            //     return this.$store.getters["CommonGhtt/listPromotion"];
            // },
        },
        mounted() { },
        watch: {
            promotionsCurrentRecord(newValue, oldValue) {
                this.$emit("handTablePromotions", newValue);
            },
            // ...["infoPromotion.khuyenmai_id"].reduce(
            //     (watchers, key) => ({
            //         ...watchers,
            //         [key](newVal, oldVal) {
            //             // this.getTrangThietBi();
            //             // if (this.infoPromotion.serviceTransportation) {
            //             //     this.loadGoiThamGia();
            //             //     this.loadDanhSachTocDoTheoTB();
            //             //     this.getContractType();

            //             //     this.loadDichVuKem();
            //             // }
            //             // this.loadKhuVuc();
            //             // this.loadDoiTuongTB();
            //             // if (this.infoPromotion.khuyenmai_id) {
            //             //     this.loadGridChiTiet();

            //             //     // this.getNgayHieuLuc({
            //             //     //   list_of_cols: "khuyenmai_id,ngay_hl,ngay_cn,nguoi_cn",
            //             //     //   order: "",
            //             //     //   schema: "css",
            //             //     //   table_name: "km_ngayhl",
            //             //     //   where: `khuyenmai_id=${this.infoPromotion.khuyenmai_id}`, //9493: khuyến mại ID đang chọn trên lưới
            //             //     // });

            //             //     //SỬA ATBM
            //             //     //this.getNgayHieuLuc({ "p_param": this.infoPromotion.khuyenmai_id, "p_type": 1 });
            //             // }
            //         },
            //     }),
            //     {}
            // ),
        },
        methods: {
            ...mapActions("CommonGhtt", [
                "getDichVuVT",
            ]),

            ...mapMutations("CommonGhtt", [
                "setDichVuVTDaGan",
                "setDichVuVTChuaGan",

            ]),
            // async loadGridChiTiet() {
            //     console.log('loadGridChiTiet');
            //     this.tableDetailLoading = true;
            //     const reqChiTiet = await this.getChiTietKhuyenMai({
            //         khuyenmai_id: this.infoPromotion.khuyenmai_id,
            //     }).then((rs) => {
            //         this.$root.showLoading(false);
            //     });
            //     this.tableDetailLoading = false;
            // },
            // loadKhuVuc() {
            //     console.log("vo load khu vuc")
            //     this.getDanhSachKhuVuc({
            //         khuyenmai_id: infoPromotion.khuyenmai_id || 0,
            //         type: 0,
            //     });
            //     this.getDanhSachKhuVuc({
            //         khuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
            //         type: 1,
            //     });
            // },
            // loadDoiTuongTB() {
            //     this.getDanhSachDoiTuongTB({
            //         dichvuvt_id: this.infoPromotion.serviceTransportation,
            //         khuyenmai_id: this.infoPromotion.khuyenmai_id || 0,

            //         kieu: 1,
            //     });
            //     this.getDanhSachDoiTuongTB({
            //         dichvuvt_id: this.infoPromotion.serviceTransportation,
            //         khuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
            //         kieu: 0,
            //     });
            // },
            onCheckBoxDeliver(e) {
                this.tempAreaDeliver = e;
            },
            onCheckBoxNotDeliver(e) {
                this.tempAreaNotDeliver = e;
            },

            onCheckBoxDeliverRemove() {
                if (this.tempAreaDeliver) {
                    const oldNotDelivered = [...this.dsDichVuVTChuaGan];
                    const oldDelivered = [...this.dsDichVuVTDaGan];

                    const newNotDelivered = [...oldNotDelivered, ...this.tempAreaDeliver];
                    const arrDelete = this.tempAreaDeliver.map((obj) => obj.dichvuvt_id);

                    const res = oldDelivered.filter(
                        (f) => !arrDelete.includes(f.dichvuvt_id)
                    );
                    this.setDichVuVTChuaGan(newNotDelivered);
                    this.setDichVuVTDaGan(res);
                    this.tempAreaDeliver = [];
                    this.tempAreaNotDeliver = [];
                }
            },
            onCheckBoxNotDeliverToDeliver() {
                if (this.dsDichVuVTChuaGan) {
                    const oldNotDelivered = [...this.dsDichVuVTChuaGan];
                    const oldDelivered = [...this.dsDichVuVTDaGan];

                    const newDelivered = [...oldDelivered, ...this.tempAreaNotDeliver];

                    const arrDelete = this.tempAreaNotDeliver.map((obj) => obj.dichvuvt_id);
                    const newNotDelivered = oldNotDelivered.filter(
                        (f) => !arrDelete.includes(f.dichvuvt_id)
                    );
                    this.setDichVuVTChuaGan(newNotDelivered);
                    this.setDichVuVTDaGan(newDelivered);
                    this.tempAreaDeliver = [];
                    this.tempAreaNotDeliver = [];
                }
            },
            handDataRight() {
                const temp = [...this.dsDichVuVTDaGan, ...this.dsDichVuVTChuaGan];
                this.setDichVuVTChuaGan(temp);
                this.setDichVuVTDaGan([]);
            },
            handDataLeft() {
                const temp = [...this.dsDichVuVTChuaGan, ...this.dsDichVuVTDaGan];
                this.setDichVuVTDaGan(temp);
                this.setDichVuVTChuaGan([]);
            },
        },
    };

</script>

