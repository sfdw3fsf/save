<template>
    <ejs-dialog
        :enableResize="true"
        :allowDragging="true"
        :header="'Thêm, xóa thanh toán khỏi mã in ghép'"
        ref="popupThemXoaMaTT"
        showCloseIcon="true"
        width="90%"
        :visible="false"
        :open="dialogOpen"
        :close="closeDialog"
        :overlayClick="closeDialog"
        :isModal="true"
    >
    <!-- <b-modal
        id="popup-themXoaMaTT"
        size="xl"
        hide-footer
        hide-header
        hide-header-close
        body-class="modal-body p-0"
        @show="onShow"
        @hide="onHide"
    > -->
        <div class="modal-content popup-box">
            <!-- <div class="popup-header">
                <div class="title">
                    <span class="icon one-notepad"></span>Thêm, xóa thanh toán khỏi mã in ghép
                </div>
                <div
                    class="close -ap icon-close"
                    data-dismiss="modal"
                    @click="$bvModal.hide('popup-themXoaMaTT')"
                ></div>
            </div> -->
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a href="javascript: void(0)" @click="nhapMoi" data-toggle="tooltip" title="Nhập mới">
                            <span class="icon one-file-plus"></span> Nhập mới
                        </a>
                    </li>
                    <li>
                        <a href="javascript: void(0)" @click="onCapNhap" data-toggle="tooltip" title="Ghi lại thông tin hợp đồng">
                            <span
                                class="icon nc-icon-glyph ui-1_circle-add"
                            ></span
                            >Thêm TT
                        </a>
                    </li>
                    <li>
                        <a href="javascript: void(0)" @click="xoa" data-toggle="tooltip" title="Xóa thanh toán khỏi danh sách mã in ghép">
                            <span class="icon one-phone-remove"></span> Xóa TT
                        </a>
                    </li>
                </ul>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <div class="legend-title">Thông tin thông tin in ghép</div>
                    <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w100">Mã in ghép</div>
                                <div class="value">
                                    <input
                                        type="text"
                                        class="form-control highlight font-weight-bold"
                                        style="color: red"
                                        v-model="txtMaIG"
                                    />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-3 col-12">
                            <div class="info-row">
                                <div class="key w80">Mã NVTC</div>
                                <div class="value">
                                    <input
                                        type="text"
                                        class="form-control highlight font-weight-bold" disabled
                                        v-model="txtMaNVTC"
                                    />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-3 col-12">
                            <div class="info-row">
                                <div class="value padl0">
                                    <input
                                        type="text"
                                        class="form-control highlight font-weight-bold" disabled
                                        v-model="txtMaTuyen"
                                    />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w100">Đ.C In ghép</div>
                        <div class="value">
                            <input
                                type="text"
                                class="form-control font-weight-bold" disabled
                                v-model="txtDiaChiIG"
                            />
                        </div>
                    </div>
                </div>
                <div class="box-form">
                    <div class="legend-title">Thông tin thanh toán</div>
                    <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w100">Mã thanh toán</div>
                                <div class="value">
                                    <input
                                        type="text"
                                        class="form-control highlight"
                                        v-model="txtMaTT"
                                        ref="inputMaTT"
                                        keydown="onMaTTKeyDown"
                                    />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="table-content">
                        <DataGrid3
                            :columns="fieldDsThanhToan"
                            ref="gridThanhToan"
                            :dataSource="dsMaTTTheoMaInGhepGetter"
                            :showColumnCheckbox="false"
                            dataKeyField="MA_TT"
                            :showFilter="false"
                            :allowPaging="true"
                            :enablePagingServer="false"
                            :enabledSelectFirstRow="true"
                            :aggregates="LGAggregatesColumns"
                            @selectedRowChanged="onRowFocus"
                            @queryCellInfo="onDSThanhToanQueryCellInfo"
                        >
                        </DataGrid3>
                    </div>
                </div>
            </div>
        </div>
        <b-modal
            id="popup-matt-loi"
            ref="popup-matt-loi"
            centered
            title="Danh sách mã thanh toán lỗi"
            size="lg"
            hide-footer
        >
            <div class="table-content">
                <DataGrid2
                    :columns="fieldDsMaTTLoi"
                    ref="gridMaTTLoi"
                    :dataSource="dsMaTTLoi"
                    
                    dataKeyField="MA_TT"
                >
                </DataGrid2>
            </div>
        </b-modal>
    <!-- </b-modal> -->
    </ejs-dialog>
</template>
<style>
.ma-in-ghep {
    color: blue !important;
    font-weight: bold !important;
}
</style>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import TraCuuKhachHangLonApi from "../../api/TraCuuKhachHangLonApi";
import Vue from "vue"
export default {
    props: {
        // maInGhep: {
        //     type: String,
        // },
        // maInGhepId: {
        //     type: Number,
        // },
        // khachHangId: {
        //     type: Number,
        // },
    },
    created() {},
    data() {
        return {
            maInGhep: '',
            maInGhepId: -1,
            khachHangId: -1,

            thanhToan: null,
            txtMaIG: "",
            txtMaTT: "",
            txtDiaChiIG: "",
            txtMaNVTC: "",
            txtMaTuyen: "",
            gridDonViKSDaGanSelectedValues: [],
            gridDonViKSChuaGanSelectedValues: [],
            fieldDsThanhToan: [
                {
                    fieldName: "STT",
                    headerText: "STT",
                    allowFiltering: true,
                    allowSorting: false,
                },

                {
                    fieldName: "MA_TT",
                    headerText: "Mã TT",
                    allowFiltering: true,
                    allowSorting: false,
                    isSumSelected: true,
                    isSum: true,
                },
                {
                    fieldName: "MA_KH",
                    headerText: "Mã KH",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "MATB_DD",
                    headerText: "Mã đại diện",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "MAIN_GHEP",
                    headerText: "Mã in ghép",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "DIACHI_IG",
                    headerText: "Đ.C In ghép",
                    allowFiltering: true,
                    allowSorting: false,
                },
            ],
            fieldDonViKSChuaGan: [
                {
                    fieldName: "TEN_DV",
                    headerText: "Loại đơn vị chưa gán",
                    allowFiltering: true,
                    allowSorting: false,
                },
            ],
            fieldDsMaTTLoi: [
                {
                    fieldName: "MA_TT",
                    headerText: "Mã thanh toán",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "LYDO_LOI",
                    headerText: "Lý do lỗi",
                    allowFiltering: true,
                    allowSorting: false,
                },
            ],
            dsMaTTLoi: [],
            countTemplate: function() {
                return { template: Vue.component('countTemplate', {
                        template: `<input type="text" class="form-control tright" :value="data.Count" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
                        data: function () {return {data: {data: {}}};}
                    })
                }
            }
        };
    },
    computed: {
        ...mapGetters("tracuukhachhanglon", ["dsMaTTTheoMaInGhepGetter"]),
        LGAggregatesColumns () {
            return [
                { field: 'MA_TT', type: 'Count', footerTemplate: this.countTemplate },   
            ]
        },
    },
    methods: {
        ...mapActions("tracuukhachhanglon", [
            "getDsMaTTTheoMaInGhep",
            "setDsMaTTTheoMaInGhepManual",
        ]),
        async dialogOpen() {
            await this.onShow();
        },
        openDialog() {
            this.$refs.popupThemXoaMaTT.show();
        },
        closeDialog() {
            this.$emit("onHideEvent");
            this.$refs.popupThemXoaMaTT.hide();
        },
        onRowFocus(dataItem) {
            this.thanhToan = dataItem;
            this.txtMaTT = dataItem ? dataItem.MA_TT : "";
            this.txtMaIG = dataItem ? dataItem.MAIN_GHEP : ""
            this.txtDiaChiIG = dataItem ? dataItem.DIACHI_IG : ""
        },
        nhapMoi() {
            this.txtMaIG = "";
            this.txtDiaChiIG = "";
            this.txtMaNVTC = "";
            this.txtMaTuyen = "";
            this.txtMaTT = "";
            this.setDsMaTTTheoMaInGhepManual([]);
        },
        async onShow() {
            this.loading(true);
            try {
                this.txtMaInGhep = this.maInGhep;
                if (this.maInGhepId != 0) {
                    let data = {
                        migId: this.maInGhepId,
                    };
                    await this.getDsMaTTTheoMaInGhep(data);
                }
            } catch (e) {
            } finally {
                this.loading(false);
            }
        },
        async xoa() {
            if (
                !this.dsMaTTTheoMaInGhepGetter ||
                this.dsMaTTTheoMaInGhepGetter.length == 0
            ) {
                this.$root.toastError("Không có dữ liệu thanh toán để xóa");
            }
            let dsMaTT = this.txtMaTT.trim().toUpperCase();
            //let dsMaTT = this.txtMaTT.split(",").map((x) => x.trim());
            try {
                this.$root.showLoading(true)
                let rs = await this.$root.context.post('/web-cskh/api/mig/xoa-ma-thanh-toan', {dsMaTT: dsMaTT})
                if (rs.error_code === "BSS-00000000")
                {
                    this.$root.toastSuccess("Xóa mã thanh toán thành công!")
                    await this.onShow()
                    return
                } 
                else {
                    this.$root.toastError("Xóa mã thanh toán thất bại! " + e.message)
                    return
                }
            }
            catch(e) {
                console.log(e)
            }
            finally {
                this.$root.showLoading(false)
            }
            // let data = {
            //   khachHangId: this.khachHangId,
            //   mig
            // }
            // TraCuuKhachHangLonApi.xoaTTKhoiMaInGhep()
        },
        async capNhapTT(dsTT) {
            this.loading(true);
            let ds = [];
            try {
                await dsTT.forEach(async (x) => {
                    let postData = {
                        migId: this.maInGhepId,
                        mtt: x,
                    };
                    await TraCuuKhachHangLonApi.themMaTT(this.axios, postData)
                        .then(async (response) => {
                            if (response.data.error_code === "BSS-00000000") {
                                this.$root.toastSuccess("Cập nhập thành công!")
                                await this.onShow()
                                return
                            }
                            else {
                                this.$root.toastError("Cập nhập thất bại! " + response.data.message)
                                return
                            }
                        })
                        .catch((reject) => {
                            console.log(reject)
                            // this.$root.toastError("Có lỗi xảy ra!")
                            ds.push({
                                MA_TT: x,
                                LYDO_LOI: reject.data.message,
                            });
                        });
                });
            } catch (e) {
            } finally {
                this.loading(false);
            }
            this.dsMaTTLoi = ds;
        },
        async onCapNhap() {
            if (!this.txtMaTT) {
                this.$root.toastError("Hãy nhập mã thanh toán!");
                this.$refs.inputMaTT.select();
                return;
            }
            this.dsMaTTLoi = [];
            let maTT = this.txtMaTT.trim();
            let dsMaTT = maTT.split(",").map((x) => x.trim());
            await this.capNhapTT(dsMaTT);
        },
        onMaTTKeyDown(event) {
            if (event.keyCode == 13) {
            }
        },
        onHide(){
            this.$emit('onHide')
        },
        onDSThanhToanQueryCellInfo(args) {
            if (args.column.field === 'MAIN_GHEP') {
                args.cell.classList.add('ma-in-ghep');
            }
        }
    },
    watch: {
        dsMaTTLoi(value) {
            if (value.length > 0) {
                this.$bvModal.show('popup-matt-loi')
            }
        },
    },
};
</script>
