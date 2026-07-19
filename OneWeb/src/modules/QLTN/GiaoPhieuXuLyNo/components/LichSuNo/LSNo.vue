<template>
    <b-modal
        id="modal-lichsuno"
        size="lg"
        hide-footer
        hide-header
        hide-header-close
        body-class="modal-body p-0"
        no-close-on-backdrop
        @show="openModal"
        @hide="resetData"
    >
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                    <span class="icon one-notepad"></span>Tra cứu lịch sử nợ
                </div>
                <div
                    class="close -ap icon-close"
                    data-dismiss="modal"
                    @click="close()"
                ></div>
            </div>
            <div class="list-actions-top">
                <ul class="list">
                    <li @click="XuatExcel()">
                        <a href="javascript:;">
                            <span class="icon one-excel"></span> Xuất Excel
                        </a>
                    </li>
                </ul>
            </div>
            <div class="popup-body">
                <DataGrid
                    :columns="fieldsRender"
                    :dataSource="sources"
                    :showFilter="true"
                    ref="danhsach"
                    :allowPaging="true"
                    :enablePagingServer="false"
                />
            </div>
        </div>
    </b-modal>
</template>
<style></style>
<script>
import xlsx from "xlsx";
import API from "../../../api/TaoDuLieuXuLyNo";
import exportExcelCustom from "../../utils/ExportExcel";
export default {
    name: "PopupLichSuNo",
    mounted() {},
    props: {
        data: {
            type: String,
            default: ""
        },
        kyCuoc: String,
        typeXuly: {
            type: Number,
            default: 1
        }
    },
    data() {
        return {
            fields: [
                {
                    fieldName: "THANGNO",
                    headerText: "Tháng nợ",
                    allowFiltering: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "TONGNO",
                    headerText: "Tổng nợ",
                    allowFiltering: true,
                    type: "number",
                    format: "N0"
                }
            ],
            fieldsRender: [],
            sources: [],
            maXLN: ""
        };
    },
    watch: {
        typeXuly: {
            handler: function(newVal) {
                let textLabel = "Mã thanh toán";
                if (newVal == "2") {
                    textLabel = "Mã khách hàng";
                } else if (newVal == "3") {
                    textLabel = "Mã thuê bao";
                } else {
                    textLabel = "Mã thanh toán";
                }
                const columnAdd = {
                    fieldName: "THANHTOAN",
                    headerText: textLabel,
                    allowFiltering: false,
                    isGroupedColumn: true
                };
                this.fieldsRender = [...this.fields, columnAdd];
            },
            immediate: true
        },
        data: {
            handler: async function(newVal, oldVal) {
                this.maXLN = await newVal;
            },
            immediate: true
        },
        maXLN: {
            handler: async function(newVal) {
                this.fetchLichSuNo();
            }
        }
    },
    methods: {
        close() {
            this.$bvModal.hide("modal-lichsuno");
        },
        openModal() {
            // this.fetchLichSuNo()
        },
        async fetchLichSuNo() {
            const params = {
                kyCuoc: this.kyCuoc, //20210301
                kieu: this.typeXuly, //1
                maKieu: this.maXLN
            };
            try {
                const response = await API.getDsLichSuNo(this.axios, params);
                if (response.data.error_code === "BSS-00000000") {
                    this.sources = response.data.data;
                } else {
                    this.$root.toastError(
                        "Không tìm thấy dữ liệu. Bạn hãy kiểm tra lại!"
                    );
                }
            } catch (error) {
                this.$root.toastError(error.data.message_detail);
            } finally {
            }
        },
        XuatExcel() {
            if (this.sources.length <= 0) {
                this.$root.toastError("Chưa có danh sách để xuất Excel!");
                return;
            }
            this.loading(true);
            try {
                exportExcelCustom(
                    [
                        {
                            fieldName: "THANGNO",
                            headerText: "Tháng nợ",
                            allowFiltering: true,
                            cssClass: "text-right",
                            width: 130
                        },
                        {
                            fieldName: "TONGNO",
                            headerText: "Tổng nợ",
                            allowFiltering: true,
                            type: "number",
                            format: "N0",
                            width: 130
                        },
                        {
                            fieldName: "THANHTOAN",
                            headerText: "Thanh Toán",
                            allowFiltering: true,
                            cssClass: "text-right",
                            width: 300
                        }
                    ],
                    this.sources,
                    "LichSuNo",
                    "export"
                );
            } catch (error) {
            } finally {
                this.loading(false);
            }
        },
        resetData() {
            // this.sources = []
        }
    }
};
</script>
