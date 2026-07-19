<template>
    <div class="main-wrapper">
       
        <div class="main-title">PHÂN GIAO</div>
        <!--  -->
        <div class="list-actions-top">
            <ul class="list">
                <li title="Lấy TT">
                    <a href="#">
                        <span class="icon one-file-attach"></span>Lấy TT
                    </a>
                </li>
                <li title="Ghi lại thông tin hợp đồng">
                    <a href="#">
                        <span class="icon one-save"></span>Ghi lại (F9)
                    </a>
                </li>
                <li title="Thực hiện phân giao">
                    <a href="#">
                        <span class="icon one-file-arrow-right1"></span>Thực hiện phân giao
                    </a>
                </li>
                <li title="Export DS phân giao">
                    <a href="#">
                        <span class="icon one-download"></span>Export DS phân giao
                    </a>
                </li>
                <li title="Import phân giao">
                    <a href="#">
                        <span class="icon one-upload"></span>Import phân giao
                    </a>
                </li>
            </ul>
        </div>

        <!--  -->
        <div class="page-content">
            <div class="nav tabs tab-over">
                <ejs-tab id='element'>
                    <div class="e-tab-header">
                        <div>Phân giao (F2)</div>
                        <div>Cấu hình định mức (F3) </div>
                        <div>Cấu hình hệ số (F4)</div>
                        <div>Điều chỉnh phân giao (F5)</div>
                        <div>Xác nhận điều chỉnh (F6)</div>
                    </div>
                    <div class="e-content">
                        <div>
                            <div id="tab1" class="tab-pane active">
                                <div class="tab-content">
                                    <form-tab1></form-tab1>
                                    <div class="legend-title mart20">
                                        Danh sách chỉ số dự kiến giao các đơn vị
                                    </div>
                                    <div class="table-content" style="height: 200px;">
                                        <grid-tab11></grid-tab11>
                                    </div>
                                    <!--  -->
                                    <div class="legend-title mart20">
                                        Thông tin dữ liệu giao nhân viên
                                    </div>
                                    <div class="table-content" style="height: 200px;">
                                        <grid-tab12></grid-tab12>
                                    </div>
                                    <!--  -->
                                    <div class="legend-title mart20">
                                        Danh sách phân giao
                                    </div>
                                    <div class="table-content" style="height: 200px;">
                                        <grid-tab13></grid-tab13>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div>
                            <div id="tab2" class="tab-pane">
                                <div class="tab-content">
                                    <div class="row">
                                        <div class="col-sm-6 col-12">
                                            <form-tab2></form-tab2>
                                        </div>
                                        <div class="col-sm-6 col-12">
                                            <div class="legend-title mart20">
                                                Danh sách nhân viên cần cấu hình định mức
                                            </div>
                                            <div style="height: 200px;">
                                                <grid-tab2></grid-tab2>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div>
                            <div id="tab3" class="tab-pane">
                                <div class="tab-content">
                                 <form-tab3></form-tab3>
                                </div>
                            </div>
                        </div>
                        <div>
                            <div id="tab4" class="tab-pane">
                                <div class="tab-content">
                                    <form-tab4></form-tab4>
                                </div>
                            </div>
                        </div>
                        <div></div>
                    </div>
                </ejs-tab>
            </div>
        </div>
    </div>
</template>
<script>
import { TabComponent, TabItemDirective, TabItemsDirective } from '@syncfusion/ej2-vue-navigations';
import FormTab1 from '@/modules/ghtt/phangiao/Tab1_PhanGiao.vue'
import FormTab2 from '@/modules/ghtt/phangiao/Tab2_CauHinhDinhMuc.vue'
import FormTab3 from '@/modules/ghtt/phangiao/Tab3_CauHinhHeSo.vue'
import FormTab4 from '@/modules/ghtt/phangiao/Tab4_DieuChinhPhanGiao.vue'
import GridTab11 from '@/modules/ghtt/phangiao/GridTab11.vue'
import GridTab12 from '@/modules/ghtt/phangiao/GridTab12.vue'
import GridTab13 from '@/modules/ghtt/phangiao/GridTab13.vue'
import GridTab2 from '@/modules/ghtt/phangiao/GridTab2.vue'
import { ComboBoxComponent as EjsCombobox } from "@syncfusion/ej2-vue-dropdowns"

// import { mapActions, mapMutations } from "vuex";
// import gridview from "@/components/Shared/gridview.vue";
// import select3 from "@/components/form/VueSelect.vue";

// import { CommonGhtt } from '@/store/ghtt/commons/index.js'
// //import { promotionDetail } from './promotion/index'

// export const promotion = {
//   modules: {
//     promotionCommonGhtt
//    // promotionDetail
//   }
// }



export default {
    components: {
        'ejs-tab': TabComponent,
        'e-tabitem': TabItemDirective,
        'e-tabitems': TabItemsDirective
        , 'form-tab1': FormTab1
        , 'form-tab2': FormTab2
        , 'form-tab3': FormTab3
        , 'form-tab4': FormTab4
        , 'grid-tab11': GridTab11
        , 'grid-tab12': GridTab12
        , 'grid-tab13': GridTab13
        , 'grid-tab2': GridTab2
        // , gridview
        // , select3

    },
    //name: 'app',
    // data: () => ({
    //         currentRecordAreaNotDeliver: null,
    //         currentRecordAreaDeliver: null,
    //         areaHeaderDeliver: [
    //             {
    //                 label: "Dịch vụ VT đã gán",
    //                 filterAble: true,
    //                 key: "ten_dvvt",
    //             },
    //         ],
    //         areaHeaderNotDeliver: [
    //             {
    //                 label: "Dịch vụ VT chưa gán",
    //                 filterAble: true,
    //                 key: "ten_dvvt",
    //             },
    //         ],
    //         tempAreaDeliver: [],
    //         tempAreaNotDeliver: [],
            
    //     }),
    //     computed: {
    //         dsDichVuVTDaGan() {
    //             return this.$store.getters["CommonGhtt/dsDichVuVTDaGan"];

    //         },
    //         // danhSachKhuVucDaGan() {
    //         //     return JSON.parse(
    //         //         JSON.stringify(
    //         //             this.$store.getters["CommonGhtt/danhSachKhuVucDaGan"]
    //         //         )
    //         //     );
    //         // },
    //         dsDichVuVTChuaGan() {
    //             return this.$store.getters["CommonGhtt/dsDichVuVTChuaGan"];
    //         },

    //         // listPromotion() {
    //         //     this.promotionsCurrentRecord =
    //         //         this.$store.getters["CommonGhtt/listPromotion"][0];
    //         //     return this.$store.getters["CommonGhtt/listPromotion"];
    //         // },
    //     },
    //     mounted() { },
    //     // watch: {
    //     //     promotionsCurrentRecord(newValue, oldValue) {
    //     //         this.$emit("handTablePromotions", newValue);
    //     //     },
    //     //     ...["infoPromotion.khuyenmai_id"].reduce(
    //     //         (watchers, key) => ({
    //     //             ...watchers,
    //     //             [key](newVal, oldVal) {
    //     //                 // this.getTrangThietBi();
    //     //                 // if (this.infoPromotion.serviceTransportation) {
    //     //                 //     this.loadGoiThamGia();
    //     //                 //     this.loadDanhSachTocDoTheoTB();
    //     //                 //     this.getContractType();

    //     //                 //     this.loadDichVuKem();
    //     //                 // }
    //     //                 // this.loadKhuVuc();
    //     //                 // this.loadDoiTuongTB();
    //     //                 // if (this.infoPromotion.khuyenmai_id) {
    //     //                 //     this.loadGridChiTiet();

    //     //                 //     // this.getNgayHieuLuc({
    //     //                 //     //   list_of_cols: "khuyenmai_id,ngay_hl,ngay_cn,nguoi_cn",
    //     //                 //     //   order: "",
    //     //                 //     //   schema: "css",
    //     //                 //     //   table_name: "km_ngayhl",
    //     //                 //     //   where: `khuyenmai_id=${this.infoPromotion.khuyenmai_id}`, //9493: khuyến mại ID đang chọn trên lưới
    //     //                 //     // });

    //     //                 //     //SỬA ATBM
    //     //                 //     //this.getNgayHieuLuc({ "p_param": this.infoPromotion.khuyenmai_id, "p_type": 1 });
    //     //                 // }
    //     //             },
    //     //         }),
    //     //         {}
    //     //     ),
    //     // },
    //     methods: {
    //         ...mapActions("CommonGhtt", ["getDichVuVT",]),

    //         ...mapMutations("CommonGhtt", [
    //             "setDichVuVTDaGan",
    //             "setDichVuVTChuaGan",

    //         ]),
    //         // async loadGridChiTiet() {
    //         //     console.log('loadGridChiTiet');
    //         //     this.tableDetailLoading = true;
    //         //     const reqChiTiet = await this.getChiTietKhuyenMai({
    //         //         khuyenmai_id: this.infoPromotion.khuyenmai_id,
    //         //     }).then((rs) => {
    //         //         this.$root.showLoading(false);
    //         //     });
    //         //     this.tableDetailLoading = false;
    //         // },
    //         // loadKhuVuc() {
    //         //     console.log("vo load khu vuc")
    //         //     this.getDanhSachKhuVuc({
    //         //         khuyenmai_id: infoPromotion.khuyenmai_id || 0,
    //         //         type: 0,
    //         //     });
    //         //     this.getDanhSachKhuVuc({
    //         //         khuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
    //         //         type: 1,
    //         //     });
    //         // },
    //         // loadDoiTuongTB() {
    //         //     this.getDanhSachDoiTuongTB({
    //         //         dichvuvt_id: this.infoPromotion.serviceTransportation,
    //         //         khuyenmai_id: this.infoPromotion.khuyenmai_id || 0,

    //         //         kieu: 1,
    //         //     });
    //         //     this.getDanhSachDoiTuongTB({
    //         //         dichvuvt_id: this.infoPromotion.serviceTransportation,
    //         //         khuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
    //         //         kieu: 0,
    //         //     });
    //         // },
    //         onCheckBoxDeliver(e) {
    //             this.tempAreaDeliver = e;
    //         },
    //         onCheckBoxNotDeliver(e) {
    //             this.tempAreaNotDeliver = e;
    //         },

    //         onCheckBoxDeliverRemove() {
    //             if (this.tempAreaDeliver) {
    //                 const oldNotDelivered = [...this.dsDichVuVTChuaGan];
    //                 const oldDelivered = [...this.dsDichVuVTDaGan];

    //                 const newNotDelivered = [...oldNotDelivered, ...this.tempAreaDeliver];
    //                 const arrDelete = this.tempAreaDeliver.map((obj) => obj.dichvuvt_id);

    //                 const res = oldDelivered.filter(
    //                     (f) => !arrDelete.includes(f.dichvuvt_id)
    //                 );
    //                 this.setDichVuVTChuaGan(newNotDelivered);
    //                 this.setDichVuVTDaGan(res);
    //                 this.tempAreaDeliver = [];
    //                 this.tempAreaNotDeliver = [];
    //             }
    //         },
    //         onCheckBoxNotDeliverToDeliver() {
    //             if (this.dsDichVuVTChuaGan) {
    //                 const oldNotDelivered = [...this.dsDichVuVTChuaGan];
    //                 const oldDelivered = [...this.dsDichVuVTDaGan];

    //                 const newDelivered = [...oldDelivered, ...this.tempAreaNotDeliver];

    //                 const arrDelete = this.tempAreaNotDeliver.map((obj) => obj.dichvuvt_id);
    //                 const newNotDelivered = oldNotDelivered.filter(
    //                     (f) => !arrDelete.includes(f.dichvuvt_id)
    //                 );
    //                 this.setDichVuVTChuaGan(newNotDelivered);
    //                 this.setDichVuVTDaGan(newDelivered);
    //                 this.tempAreaDeliver = [];
    //                 this.tempAreaNotDeliver = [];
    //             }
    //         },
    //         handDataRight() {
    //             const temp = [...this.dsDichVuVTDaGan, ...this.dsDichVuVTChuaGan];
    //             this.setDichVuVTChuaGan(temp);
    //             this.setDichVuVTDaGan([]);
    //         },
    //         handDataLeft() {
    //             const temp = [...this.dsDichVuVTChuaGan, ...this.dsDichVuVTDaGan];
    //             this.setDichVuVTDaGan(temp);
    //             this.setDichVuVTChuaGan([]);
    //         },
    //     },
}
</script>
<style>
.e-tab .e-tab-header .e-toolbar-item.e-active {
    background: #b4d62d;
}
</style>