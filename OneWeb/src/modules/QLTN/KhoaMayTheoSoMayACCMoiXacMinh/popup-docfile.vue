<template>
    <b-modal
        id="modal-docfile"
        size="lg"
        hide-footer
        hide-header
        hide-header-close
        body-class="modal-body p-0"
        @hide="resetData"
    >
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                    <span class="icon one-notepad"></span>Danh sách thuê bao
                </div>
                <div
                    class="close -ap icon-close"
                    data-dismiss="modal"
                    @click="close()"
                ></div>
            </div>
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a href="javascript:void(0)" @click="ChapNhan()">
                            <span
                                class="icon ui-1_check-circle-08 nc-icon-glyph"
                            ></span>
                            Chấp nhận
                        </a>
                    </li>
                </ul>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <div class="legend-title">
                        Danh sách thuê bao
                    </div>
                    <DataGrid
                        :columns="fields"
                        :dataSource="listTB"
                        :showFilter="true"
                        @selectedRowChanged="onListTBChange"
                        ref="danhSach"
                    />
                </div>
            </div>
        </div>
    </b-modal>
</template>
<style></style>
<script>
import xlsx from "xlsx";
import { mapActions, mapState, mapGetters } from "vuex";

export default {
    name: "PopupDocFile",
    mounted() {},
    props: ["listTB"],
    data() {
        return {
            fields: [
                {
                    fieldName: "MATB_DD",
                    headerText: "Số máy/Acc ",
                    allowFiltering: true
                },
                {
                    fieldName: "DICHVU_VT",
                    headerText: "Dịch vụ",
                    allowFiltering: true,
                    cssClass: 'text-danger',
                },
                {
                    fieldName: "LOAIHINH_TB",
                    headerText: "Loại hình",
                    allowFiltering: true,
                    cssClass: 'text-primary'
                },
                {
                    fieldName: "NGUOI_DD",
                    headerText: "Tên thuê bao",
                    allowFiltering: true
                },
                {
                    fieldName: "",
                    headerText: "Mã TB Sub",
                    allowFiltering: true
                },
                {
                    fieldName: "TRANGTHAI_TB",
                    headerText: "Trạng thái thuê bao",
                    allowFiltering: true
                },
                {
                    fieldName: "MA_TT",
                    headerText: "Mã thanh toán",
                    allowFiltering: true
                },
                {
                    fieldName: "TEN_TT",
                    headerText: "Tên thanh toán",
                    allowFiltering: true
                },
                {
                    fieldName: "DIACHI_LD",
                    headerText: "Địa chỉ LĐ",
                    allowFiltering: true
                }
            ],
            duongDan: "",
            dsLoi: [],
            sourceFile: null,
            checkLoi: false,
            selectedItem: null
        };
    },
    computed: {
        ...mapGetters("TaoDanhSachThanhLyThueBaoTamDung", ["listDonViGetter"])
    },
    methods: {
        ...mapActions("TaoDanhSachThanhLyThueBaoTamDung", [
            "getListTDTuFile",
            "updateListTDTuFile",
            "clearData"
        ]),

        close() {
            this.$bvModal.hide("modal-docfile");
        },
        onListTBChange(dataItem) {
            console.log(dataItem);
            this.selectedItem = dataItem;
        },
        async ChapNhan() {
            console.log(this.selectedItem);
            this.$emit("sendData", this.selectedItem);
            this.$bvModal.hide("modal-docfile");
        },

        resetData() {
            this.selectedItem = null;
            this.duongDan = "";
            this.dsLoi = [];
        }
    }
};
</script>
