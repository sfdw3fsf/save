<template>
    <span>
        <ejs-dialog
        id="popup-khachHangLonLe"
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        :header='"Thêm khách hàng lớn lẻ"'
        showCloseIcon=true
        width="95%"
        ref="dlgkhachHangLonLe"
        :target='".main-wrapper"'
        :open="onShown"
        :close="close"
        :isModal="true">
        <!-- <b-modal
            id="popup-khachHangLonLe"
            size="lg"
            no-close-on-backdrop
            hide-footer
            hide-header
            hide-header-close
            body-class="modal-body p-0"
            :enableResize='true'
            :allowDragging='true'
            @shown="onShown"
            @hidden="close"
        > -->
            <div class="modal-content popup-box">
                <!-- <div class="popup-header">
                    <div class="title">
                        <span class="icon one-notepad"></span> Thêm khách hàng
                        lớn lẻ
                    </div>
                    <div
                        class="close -ap icon-close"
                        data-dismiss="modal"
                        @click="close"
                    ></div>
                </div> -->
                <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a href="javascript: void(0)" v-on:click="ghiLai()">
                                <span class="icon one-save"></span>Ghi lại
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="legend-title">Thông tin khách hàng</div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w80">Mã KH<BssRequiredMarker class="ml-1"/></div>
                                    <div class="value">
                                        <div class="input-more-button">
                                            <a
                                                class="btn"
                                                @click="ShowSearchAccount"
                                            >
                                                <span
                                                    class="-ap icon-more_horiz"
                                                ></span>
                                            </a>
                                            <input
                                                type="text"
                                                class="form-control bold"
                                                v-model="maKH"
                                                v-on:keyup.enter="
                                                    traCuuKhachHang
                                                "
                                                ref="txtMaKH"
                                            />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w80">Gán KHL<BssRequiredMarker class="ml-1"/></div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select
                                                class="form-control"
                                                v-model="ganKHL"
                                            >
                                                <option
                                                    v-for="(item,
                                                    index) in loaiKHL"
                                                    :key="index"
                                                    :value="item.KHLON_ID"
                                                    >{{ item.TEN_LKHL }}</option
                                                >
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <div class="info-row">
                                    <div class="key w80">Tên KH<BssRequiredMarker class="ml-1"/></div>
                                    <div class="value">
                                        <input
                                            type="text"
                                            readonly
                                            class="form-control bold"
                                            :value="khachHang.TEN_KH"
                                        />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <div class="info-row">
                                    <div class="key w80">Địa chỉ<BssRequiredMarker class="ml-1"/></div>
                                    <div class="value">
                                        <input
                                            type="text"
                                            readonly
                                            class="form-control bold"
                                            :value="khachHang.DIACHI_KH"
                                        />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w80">Loại KH<BssRequiredMarker class="ml-1"/></div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select
                                                class="form-control"
                                                disabled
                                            >
                                                <option
                                                    v-for="(item,
                                                    index) in loaiKH"
                                                    :key="index"
                                                    :value="item.LOAIKH_ID"
                                                    >{{
                                                        item.TEN_LOAIKH
                                                    }}</option
                                                >
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w80">KH lớn<BssRequiredMarker class="ml-1"/></div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select
                                                class="form-control"
                                                disabled
                                            >
                                                <option>{{
                                                    khachHang.LOAI_KHLON
                                                }}</option>
                                                <!-- <option v-for="(loaiKHL, index) in dsLoaiKHL" :key="index" :value="loaiKHL.KHLON_ID">{{
                                                    khachHang.LOAI_KHLON
                                                }}</option> -->
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w80">
                                        <div class="check-action">
                                            <input
                                                type="checkbox"
                                                class="check"
                                                v-model="enableKyHD"
                                            />
                                            <span class="name">Từ KHĐ<BssRequiredMarker class="ml-1"/></span>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select
                                                class="form-control"
                                                :disabled="!enableKyHD"
                                                v-model="dataSelect.tuNgay"
                                            >
                                                <option
                                                    v-for="(item,
                                                    index) in chuKyNo"
                                                    :key="index"
                                                    :value="item.CHUKYNO"
                                                    >{{ item.CHUKY }}</option
                                                >
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w80">Đến KHĐ<BssRequiredMarker class="ml-1"/></div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select
                                                class="form-control"
                                                :disabled="!enableKyHD"
                                                v-model="dataSelect.denNgay"
                                            >
                                                <option
                                                    v-for="(item,
                                                    index) in chuKyNo"
                                                    :key="index"
                                                    :value="item.CHUKYNO"
                                                    >{{ item.CHUKY }}</option
                                                >
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">
                            <div class="pull-left">Thông tin chi tiết nợ</div>
                            <div v-if="binhQuan > 0" class="pull-right red">
                                Bình quân DT:
                                <vue-numeric :read-only="true" v-model="binhQuan">
                                </vue-numeric>
                            </div>
                            <div v-else class="pull-right red">
                                Bình quân DT
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <DataGrid
                            :columns="fields"
                            :dataSource="dsThongTinNo"
                            :allowPaging="true"
                            @queryCellInfo="gridViewControll_QueryCellInfo"
                            :aggregates="aggregatesColumns"
                        ></DataGrid>
                    </div>
                </div>
            </div>
            <!-- <b-modal id="modal-1" title="Tra cứu danh bạ" size="xl"> -->

            <!-- </b-modal> -->
        <!-- </b-modal> -->
        </ejs-dialog>
        <!-- <TraCuuDanhBa ref="dlgTraCuuDanhBa" @accept="acceptDanhBa" /> -->
        <b-modal id="popupComponents" size="xl"
            hide-footer
            hide-header
            hide-header-close
            body-class="modal-body p-0">
            <SearchAccount :isPopup="true" @form-close="searchAccountClosed"></SearchAccount>
        </b-modal>
    </span>
</template>
<script>
import Vue from "vue"
import { mapActions, mapState, mapGetters } from "vuex";
import TraCuuKhachHangLonApi from "../../api/TraCuuKhachHangLonApi";
import API from "../../api/DanhSachKhachHangLon";
import TraCuuDanhBa from "../../../QLTN/components/PopupTraCuuDanhBa/PopupTraCuuDanhBa.vue";
import SearchAccount from "@/modules/search/subscriber/SearchAccount";
import { currency } from "../../../../filters/currency";
import BssRequiredMarker from "@/components/BssRequiredMarker";
export default {
    props: ["loaiKH", "loaiKHL", "chuKyNo", "kyHoaDon"],
    components: {
        TraCuuDanhBa,
        BssRequiredMarker,
        SearchAccount
    },
    computed: {
        ...mapState("danhsachkhachhanglon", ["dsThongTinNo", "dsChuKyNo", "dsLoaiKHL"]),
        aggregatesColumns () {
            return [
                { field: 'TONGNO', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate }
            ]
        },
    },
    async mounted() {
        await this.fetchData();
        // this.dataSelect = {
        //     khachHangId: "",
        //     tuNgay: this.kyHoaDon,
        //     denNgay: this.kyHoaDon
        // }
    },
    data() {
        return {
            fields: [
                {
                    fieldName: "KYHOADON",
                    headerText: "Kỳ HĐ",
                    allowFiltering: true
                },
                {
                    fieldName: "TONGNO",
                    headerText: "Tổng nợ",
                    allowFiltering: true,
                    format: "N0",
                    type: "number",
                    cssClass: "text-red"
                }
            ],
            enableKyHD: false,
            maKH: "",
            khachHang: {},
            dataSelect: {
                khachHangId: "",
                tuNgay: "",
                denNgay: ""
            },
            binhQuan: 0,
            ganKHL: "",
            sumTemplate: function() {
                return { template: Vue.component('sumTemplate', {
                    template: `<input type="text" class="form-control text-sum" :value="data.Sum" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
                        data: function () {return {data: {data: {}}};}
                    })
                }
            }
        };
    },
    methods: {
        ...mapActions("danhsachkhachhanglon", [
            "getThongTinNo",
            "getdsLanTao",
            "getdsChuKyNo",
            "clearDSThongTinNo",
            "getdsLoaiKHL"
        ]),
        async fetchData() {
            await this.getdsChuKyNo();
            await this.getdsLoaiKHL()
            this.ganKHL = this.$props.loaiKHL[0].KHLON_ID
            this.dataSelect.tuNgay = this.kyHoaDon
            this.dataSelect.denNgay = this.kyHoaDon
            this.khachHang.LOAI_KHLON = this.dsLoaiKHL[0].TEN_LKHL
        },
        async traCuuKhachHang() {
            this.$root.loading(true);
            if (this.maKH) {
                TraCuuKhachHangLonApi.traCuuKHTheoMa(this.axios, this.maKH)
                    .then(async response => {
                        if (response.data.error_code === "BSS-00000000") {
                            if (
                                response.data.data &&
                                response.data.data.length > 0
                            ) {
                                this.khachHang = response.data.data[0];
                                this.dataSelect.khachHangId = this.khachHang.KHACHHANG_ID;
                                if (this.enableKyHD == false) {
                                    this.dataSelect.tuNgay = this.kyHoaDon;
                                    this.dataSelect.denNgay = this.kyHoaDon;
                                }
                            } else {
                                this.$root.toastError(
                                    "Không tìm thấy thông tin khách hàng!"
                                );
                                return;
                            }
                            this.binhQuan = 0;
                            await this.getThongTinNo(this.dataSelect);
                            this.dsThongTinNo.forEach(element => {
                                currency(element.TONGNO);
                                this.binhQuan += element.TONGNO;
                            });
                        } else {
                            this.$root.toastError(response.data.message);
                        }
                        this.$root.loading(false);
                    })
                    .catch(reject => {
                        if (
                            reject.response &&
                            reject.response.data &&
                            reject.response.data.message
                        )
                            this.$root.toastError(reject.response.data.message);
                        // else this.$root.toastError("Có lỗi xảy ra!");
                        this.$root.loading(false);
                    })
                    .finally(() => this.$root.loading(false));
            }
            //   this.$root.loading(false);
        },
        async ghiLai() {
            if (this.ganKHL == "") {
                this.$root.toastError("Chưa chọn trường Gán KHL!");
                return;
            }
            this.$root.loading(true);
            let data = {
                kyHoaDon: this.dataSelect.tuNgay,
                khachHangId: this.khachHang.KHACHHANG_ID,
                maKh: this.maKH,
                tenKh: this.khachHang.TEN_KH,
                diaChiKh: this.khachHang.DIACHI_KH,
                loaiKhId: this.khachHang.LOAIKH_ID,
                khLonId: this.ganKHL,
                chuKyNoTu: this.dataSelect.tuNgay,
                chuKyNoDen: this.dataSelect.denNgay,
                binhQuanDt: this.binhQuan,
                nhanVienId: this.$auth.getNhanVienID(),
                mayCn: "test_pc",
                nguoiCn: this.$auth.getUserName()
            };
            console.log("data: ", data);
            await API.luuThongTinKhachHangLe(this.axios, data)
                .then(async response => {
                    if (response.data.error_code === "BSS-00000000") {
                        if (response.data.data) {
                            this.$root.toastSuccess(
                                "Lưu khách hàng lẻ thành công"
                            );
                            await this.getdsLanTao(this.kyHoaDon);
                        } else {
                            this.$root.toastError("Lưu khách hàng lẻ thất bại!");
                        }
                    } else {
                        this.$root.toastError(response.data.message);
                    }
                })
                .catch(error => {
                    if (error.data.error_code == "BSS-00000500") {
                        this.$root.toastError("Có lỗi hệ thống!");
                    } else {
                        this.$root.toastError(error.data.message_detail);
                    }
                });
            this.$root.loading(false);
        },
        gridViewControll_QueryCellInfo(args) {
            if (args.column.field === "TONGNO") {
                args.cell.innerText = currency(args.data.TONGNO);
            }
        },
        close() {
            this.khachHang = {};
            this.maKH = "";
            this.dataSelect = {
                khachHangId: "",
                tuNgay: this.kyHoaDon,
                denNgay: this.kyHoaDon
            };
            this.ganKHL = "";
            this.clearDSThongTinNo()
            this.$refs.dlgkhachHangLonLe.hide()
            // this.$bvModal.hide("popup-khachHangLonLe");
        },
        btnShowTraCuuDanhBa() {
            this.$refs.dlgTraCuuDanhBa.showModal();
        },
        acceptDanhBa(value) {
            console.log(value.ma_kh);
            if (value.ma_kh) {
                this.maKH = value.ma_kh;
            }
        },
        ShowSearchAccount() {
            this.Popup("popupComponents");
        },
        Popup(modalId) {
            this.$bvModal.show(modalId);
        },
        ClosePopup(modalId) {
            this.$bvModal.hide(modalId);
        },
        searchAccountClosed(val) {
            if (val) {
                this.maKH = val.ma_kh;
            }
            this.ClosePopup("popupComponents");
        },
        onShown() {
            this.$refs.dlgkhachHangLonLe.show()
            this.$nextTick(() => {
                this.$refs.txtMaKH.focus()
            })
        },
    }
};
</script>
<style scope>
#popupComponents .page-content {
    position: inherit;
}
.text-sum {
    color: blue !important;
    text-align: right !important;
    font-weight: bold !important;
}
.text-red {
    color: red;
    font-weight: bold !important;
}
</style>
