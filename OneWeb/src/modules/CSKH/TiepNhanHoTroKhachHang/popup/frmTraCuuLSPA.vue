<template>
    <ejs-dialog
        :enableResize="true"
        :allowDragging="true"
        ref="popupTraCuuLSPA"
        :header="'Tra cứu lịch sử phản ánh'"
        :animationSettings="animationSettings"
        showCloseIcon="true"
        width="80%"
        :visible='false'
        :target="target"
        :open="openDialog"
        :close="closeDialog"
        :isModal="true"
    >
        <div class="">
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a href="javascript: void(0)" @click="doSearch()">
                            <span class="icon one-search"></span> Tìm kiếm
                        </a>
                    </li>
                    <li>
                        <a
                            href="javascript: void(0)"
                            @click="onClickBtnExcelDsPhanAnh"
                        >
                            <span class="icon one-excel"></span> Xuất Excel
                            <!-- <downloadexcel :data="this.dataExcel">
                        <span class="icon one-excel"></span> Xuất Excel
                    </downloadexcel> -->
                        </a>
                    </li>
                </ul>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <div class="legend-title">Thông tin tra cứu</div>
                    <div class="row">
                        <div class="col-sm-4 col-12">
                            <div class="info-row">
                                <div class="key w120">
                                    <div class="check-action">
                                        <input
                                            type="checkbox"
                                            class="check"
                                            v-model="chk_from_date"
                                        />
                                        <span class="name">Ngày tiếp nhận</span>
                                    </div>
                                </div>
                                <div class="value">
                                    <ejs-datepicker
                                        v-model="from_date"
                                        :value="today"
                                        :max="today"
                                        :format="'dd/MM/y'"
                                        :enabled="chk_from_date"
                                        :showClearButton="false"
                                    >
                                    </ejs-datepicker>
                                    <!-- <div class="input-icon-right">
                                <flat-pickr :disabled="!chk_from_date" v-model="from_date" class="form-control"
                                    :config="dateConfig" placeholder="Chọn ngày tiếp nhận" />
                                <span class="icon one-calendar"></span>
                            </div> -->
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w120">
                                    <div class="check-action">
                                        <input
                                            type="checkbox"
                                            class="check"
                                            v-model="chk_to_date"
                                        />
                                        <span class="name">Đến ngày</span>
                                    </div>
                                </div>
                                <div class="value">
                                    <ejs-datepicker
                                        v-model="to_date"
                                        :value="today"
                                        :max="today"
                                        :format="'dd/MM/y'"
                                        :enabled="chk_to_date"
                                        :showClearButton="false"
                                    >
                                    </ejs-datepicker>
                                    <!-- <div class="input-icon-right">
                                <flat-pickr :disabled="!chk_to_date" v-model="to_date" class="form-control"
                                    :config="dateConfig" placeholder="Chọn đến ngày" />
                                <span class="icon one-calendar"></span>
                            </div> -->
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-8 col-12">
                            <div class="info-row">
                                <div class="key w80">
                                    Mã KH
                                </div>
                                <div class="value">
                                    <div class="input-more-button">
                                        <button class="btn" v-b-modal.modal-1>
                                            <span
                                                class="-ap icon-more_horiz"
                                            ></span>
                                        </button>
                                        <input
                                            type="text"
                                            class="form-control highlight"
                                            v-model="ma_kh"
                                            ref="maKH"
                                            @keydown="onMaKHSearch"
                                        />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="box-form">
                    <div class="legend-title">Danh sách phản ánh</div>
                    <ejs-grid
                        ref="grid-dsphananh"
                        ref-value="grid-dsphananh"
                        class="table-result tree-grid-2"
                        locale="vi-VN"
                        gridLines="Both"
                        :groupSettings="{
                            showDropArea: false,
                            captionTemplate: '${key}'
                        }"
                        :selectionSettings="{ enableToggle: false }"
                        :dataSource="dsPhanAnh"
                        :allowSorting="true"
                        :allowFiltering="true"
                        :allowResizing="true"
                        :allowGrouping="true"
                        :rowSelected="onRowSelected"
                        :actionComplete="onActionCompleteTienTrinh"
                        :dataBound="onDataBoundTienTrinh"
                    >
                        <e-columns>
                            <e-column
                                headerText=""
                                textAlign="Center"
                                width="20"
                                :allowResizing="false"
                                :allowFiltering="false"
                                :template="columnTemplate"
                            ></e-column>
                            <e-column
                                field="NOIDUNGPA"
                                headerText=""
                                headerTextAlign="Center"
                                width="130"
                                :visible="false"
                                :filterBarTemplate="filterBarTemplate"
                            ></e-column>
                            <!-- <e-column field='NOIDUNGPA' headerText='Nội dung phản ánh' headerTextAlign='Center' width="400" :customAttributes= "{class: 'font-weight-bold'}" :filterBarTemplate='filterBarTemplate'></e-column> -->
                            <e-column
                                field="MA_KH"
                                headerText="Mã KH"
                                headerTextAlign="Center"
                                width="130"
                                :filterBarTemplate="filterBarTemplate"
                                :customAttributes="{
                                    class: 'font-weight-bold'
                                }"
                            ></e-column>
                            <e-column
                                field="NGAYTIEPNHAN"
                                headerText="Ngày tiếp nhận"
                                headerTextAlign="Center"
                                width="200"
                                :filterBarTemplate="filterBarTemplate"
                            ></e-column>
                            <e-column
                                field="NGAY_GQ"
                                headerText="Ngày giải quyết"
                                headerTextAlign="Center"
                                width="150"
                                :filterBarTemplate="filterBarTemplate"
                            ></e-column>
                            <e-column
                                field="GHICHU"
                                headerText="Ghi chú"
                                headerTextAlign="Center"
                                width="130"
                                :filterBarTemplate="filterBarTemplate"
                            ></e-column>
                            <e-column
                                field="TRANGTHAI_PA"
                                headerText="Trạng thái"
                                headerTextAlign="Center"
                                width="150"
                                :filterBarTemplate="filterBarTemplate"
                            ></e-column>
                        </e-columns>
                    </ejs-grid>
                </div>
            </div>
        </div>
    </ejs-dialog>
</template>
<script>
import XLSX from "xlsx";
import breadcrumb from "@/components/breadcrumb";
import TraCuuAPI from "../../api/TraCuuAPI";
import TraCuuDanhBa from "../../TraCuuDanhBa/";
import { mapActions, mapState, mapGetters } from "vuex";
import moment from "moment";
import downloadexcel from "vue-json-excel";
import { Workbook } from "@syncfusion/ej2-excel-export/index";
import { DialogPlugin } from '@syncfusion/ej2-vue-popups';
// import { Page, Filter, Sort } from "@syncfusion/ej2-vue-treegrid";
import Vue from 'vue';
import {
    Edit,
    Sort,
    Filter,
    ForeignKey,
    Resize,
    Freeze,
    Page,
    Group
} from "@syncfusion/ej2-vue-grids";
import { L10n } from "@syncfusion/ej2-base";

L10n.load({
    "vi-VN": {
        grid: {
            EmptyRecord: "Không có bản ghi nào để hiển thị"
        }
    }
});
Vue.use(DialogPlugin);
export default {
    provide: {
        grid: [Edit, Sort, Filter, ForeignKey, Resize, Freeze, Page, Group]
    },
    props: {
        maKH: {
            type: String,
            default: ''
        }
    },
    components: { downloadexcel },
    name: "frmTraCuuLSPA",
    destroyed() {},
    data() {
        return {
            target: ".main-wrapper",
            animationSettings: { effect: "Zoom" },
            dsPhanAnh: [],
            dateConfig: {
                altInput: true,
                dateFormat: "d/m/Y",
                altFormat: "d/m/Y"
            },
            from_date: new Date(),
            to_date: new Date(),
            today: new Date(),
            chk_from_date: false,
            chk_to_date: false,
            pageNo: 1,
            pageSize: 10,
            ma_kh: "",
            pageInfo: {
                page: 0,
                maxSize: 10,
                totalElement: 0,
                totalPages: 0,
                sort: null,
                propertiesSort: null
            },
            getSelectedRow: 0,
            pageSizeList: [10, 20],
            treeGridData: [],
            fields2: [
                {
                    fieldName: "NOIDUNGPA",
                    headerText: "Nội dung phản ánh",
                    allowFiltering: true,
                    allowSorting: false,
                    isGroupedColumn: true
                },
                {
                    fieldName: "MA_KH",
                    headerText: "Mã KH",
                    allowFiltering: true,
                    allowSorting: false,
                    cellCssClass: "font-weight-bold"
                },
                {
                    fieldName: "NGAYTIEPNHAN",
                    headerText: "Ngày tiếp nhận",
                    allowFiltering: true,
                    allowSorting: false
                },
                {
                    fieldName: "NGAY_GQ",
                    headerText: "Ngày giải quyết",
                    allowFiltering: true,
                    allowSorting: false
                },
                {
                    fieldName: "GHICHU",
                    headerText: "Ghi chú",
                    allowFiltering: true,
                    allowSorting: false
                },
                {
                    fieldName: "TRANGTHAI_PA",
                    headerText: "Trạng thái",
                    allowFiltering: true,
                    allowSorting: false
                }
            ],
            items: [],
            dataItems: [],
            dataItemsGetter: [],
            arraySearchInput: [
                { key: "MA_KH", value: "" },
                { key: "NGAYTIEPNHAN", value: "" },
                { key: "NGAY_GQ", value: "" },
                { key: "GHICHU", value: "" },
                { key: "TRANGTHAI_PA", value: "" }
            ],
            columnTemplate: function() {
                return {
                    template: {
                        template:
                            '<span :id="data.index" :style="{display: display }" style="font-size: 12px;" class="col-mark-selected-row fa fa-play text-main"></span>',
                        data() {
                            return {
                                data: {}
                            };
                        },
                        computed: {
                            display() {
                                return parseInt(this.data.index) ==
                                    this.$parent.getSelectedRowIndexes()[0]
                                    ? "block"
                                    : "none";
                            }
                        }
                    }
                };
            },
            filterBarTemplate: {
                create: function(args) {
                    let div = document.createElement("div");
                    div.classList.add("input-icon-right");

                    let input = document.createElement("input");
                    input.classList.add("form-control");
                    input.id = args.column.foreignKeyField;

                    let span = document.createElement("span");
                    span.classList.add("icon", "nc-icon-outline", "ui-1_zoom");

                    div.append(input);
                    div.append(span);

                    return div;
                },
                write: function(args) {
                    args.element
                        .querySelector("input")
                        .addEventListener("input", args => {
                            let value = args.currentTarget.value;
                            value = !!value ? value.trim() : value;

                            if (!value) {
                                this.parent.removeFilteredColsByField(
                                    args.currentTarget.id
                                );
                            } else {
                                this.parent.filterByColumn(
                                    args.currentTarget.id,
                                    "contains",
                                    value
                                );
                            }
                        });
                }
            },
            filterValues: []
        };
    },
    computed: {
        ...mapState("tracuulichsuphananh", ["dsLichSuPhanAnh"]),
        ...mapGetters("tracuulichsuphananh", [
            "dsLichSuPhanAnhGetter",
            "totalItemsGetter"
        ]),
        dataExcel() {
            return Object.values(this.dsLichSuPhanAnhGetter);
        }
    },
    methods: {
        ...mapActions("tracuulichsuphananh", [
            "getLichSuPhanAnh",
            "clearData",
            "getObjSearch"
        ]),
        async openDialog() {
            this.$refs.popupTraCuuLSPA.show()
            this.clearData();
            this.ma_kh = this.maKH
            this.focusInput();
            await this.doSearch()
        },

        closeDialog() {
            this.$refs.popupTraCuuLSPA.hide()
        },
        onRowSelected(items) {},
        onChangeText(txt, key) {
            let data = {
                key: key || "",
                value: txt || ""
            };
            this.getObjSearch(data);
        },
        formatData(input) {
            let output = [];

            var tmp = input.reduce(function(r, a) {
                r[a.NOIDUNGPA] = r[a.NOIDUNGPA] || [];
                r[a.NOIDUNGPA].push(a);
                return r;
            }, Object.create(null));

            output = Object.keys(tmp).map(function(key) {
                return {
                    NOIDUNGPA: key,
                    Childs: tmp[key],
                    isOpen: true,
                    isSelected: false
                };
            });
            console.log("output", JSON.stringify(output));
            return output;
        },
        onRowSelected(args) {
            let refValue = args.row
                .closest(".e-grid")
                .getAttribute("ref-value");
            this.$refs[refValue].$el
                .querySelectorAll(".col-mark-selected-row")
                .forEach(item => {
                    item.style.display =
                        args.rowIndex == parseInt(item.id) ? "block" : "none";
                });
        },
        onDataBoundTienTrinh() {
            let grid = this.$refs["grid-dsphananh"];
            grid.groupColumn("NOIDUNGPA");
            grid.selectRow(0);
        },
        onActionCompleteTienTrinh(args) {
            this.onActionComplete(args);

            this.$refs["grid-dsphananh"].$el
                .querySelectorAll(".e-indentcell")
                .forEach(item => {
                    item.classList.add("tree-first", "bg-white");

                    let div1 = document.createElement("div");
                    div1.classList.add("tree-line");

                    let div2 = document.createElement("div");
                    div2.classList.add("tree-indent");

                    item.append(div1);
                    item.append(div2);
                });
        },
        onActionComplete(args) {
            if (args.requestType == "filtering") {
                let filterObj = args.currentFilterObject;
                let grid = document
                    .querySelector(`[e-mappinguid="${filterObj.uid}"]`)
                    .closest(".e-grid");
                let filterInput = grid.querySelector(`#${filterObj.field}`);
                filterInput.click();
                filterInput.focus();
            }
        },
        onUnselected() {
            this.dataItems.forEach(element => {
                element.isSelected = false;
                element.Childs.forEach(child => {
                    if (child.isSelected) {
                        console.log(child.NOIDUNGPA + " " + child.isSelected);
                        child.isSelected = false;
                        console.log(child.NOIDUNGPA + " " + child.isSelected);
                    }
                });
            });
        },
        async fetchData() {
            let response = null;

            if (
                this.$data.chk_from_date &&
                this.$data.chk_to_date &&
                this.to_date < this.from_date
            ) {
                this.$root.toastError("Ngày TH không được lớn hơn Đến ngày!");
                return;
            }
            this.loading(true);
            try {
                this.items = [];
                let postData = {
                    ngayTiepNhan: this.$data.chk_from_date
                        ? moment(this.from_date).format("DD/MM/YYYY")
                        : "",
                    denNgay: this.$data.chk_to_date
                        ? moment(this.to_date).format("DD/MM/YYYY")
                        : "",
                    maKh: this.ma_kh.trim(),
                    pageNo: 1,
                    pageSize: 1000
                };
                let response = await TraCuuAPI.traCuuLichSuPhanAnh(
                    this.axios,
                    1,
                    10000,
                    postData
                );
                if (response.data.error_code === "BSS-00000000") {
                    this.dsPhanAnh = response.data.data.data || [];
                    this.dsPhanAnh.sort((a, b) =>
                        a.NOIDUNGPA.localeCompare(b.NOIDUNGPA)
                    );
                    this.dsPhanAnh.forEach((element, index) => {
                        element.NGAYTIEPNHAN = moment
                            .utc(element.NGAYTIEPNHAN)
                            .format("DD/MM/YYYY HH:mm:ss");
                        element.NGAY_GQ = moment
                            .utc(element.NGAY_GQ)
                            .format("DD/MM/YYYY HH:mm:ss");
                    });
                }
            } catch (e) {
                console.log(e);
                this.$root.toastError("Không tìm thấy thông tin khách hàng!");
            } finally {
                this.loading(false);
            }
            return response;
        },
        async doSearch() {
            await this.fetchData();
        },
        exportXLS() {},
        focusInput() {
            this.$refs.maKH.select();
        },
        firstPage() {},
        lastPage() {},
        nextPage() {},
        previousPage() {},
        doSelectNearby() {},
        setPageSize(value) {
            this.pageInfo.maxSize = value;
        },
        onFatherRowClick(item) {
            this.onUnselected();
            item.isOpen = !item.isOpen;
            item.isSelected = true;
            console.log(item);
        },
        onChildRowClick(item) {
            this.onUnselected();
            item.isSelected = true;
            console.log(item);
        },
        filterChange() {
            let datatmp = this.items;
            this.fields.forEach(field => {
                let search = this.filterValues[field.key];
                console.log("search", search);
                if (search) {
                    datatmp = datatmp.filter(x => {
                        const itemValue = x[field.key] + "" || "";
                        return (
                            itemValue
                                .toLowerCase()
                                .indexOf(search.toLowerCase()) > -1
                        );
                    });
                }
            });
            this.dataItemsGetter = this.formatData(datatmp);
        },
        async onMaKHSearch(event) {
            if (event.keyCode == 13) {
                if (this.ma_kh) {
                    await this.fetchData();
                }
            }
        },
        onClickBtnExcelDsPhanAnh() {
            if (!this.dsPhanAnh || this.dsPhanAnh.length == 0) {
                this.$toast.error("Không có dữ liệu để xuất");
                return;
            }

            let propName = {
                MA_KH: "Mã KH",
                NGAYTIEPNHAN: "Ngày tiếp nhận",
                NGAYGQ: "Ngày giải quyết",
                GHICHU: "Ghi chú",
                TRANGTHAI_PA: "Trạng thái"
            };

            let columns = [
                { index: 1, width: 50 },
                { index: 2, width: 130 },
                { index: 3, width: 130 },
                { index: 4, width: 130 },
                { index: 5, width: 130 },
                { index: 6, width: 130 }
            ];

            let rows = [];
            let headerRow = {
                index: 1,
                cells: [
                    {
                        index: 1,
                        value: "Mã KH",
                        colSpan: 2,
                        style: {
                            borders: {
                                color: "#00000000",
                                lineStyle: "thin"
                            }
                        }
                    },
                    {
                        index: 3,
                        value: "Ngày tiếp nhận",
                        colSpan: 1,
                        style: {
                            borders: {
                                color: "#00000000",
                                lineStyle: "thin"
                            }
                        }
                    },
                    {
                        index: 4,
                        value: "Ngày giải quyết",
                        colSpan: 1,
                        style: {
                            borders: {
                                color: "#00000000",
                                lineStyle: "thin"
                            }
                        }
                    },
                    {
                        index: 5,
                        value: "Ghi chú",
                        colSpan: 1,
                        style: {
                            borders: {
                                color: "#00000000",
                                lineStyle: "thin"
                            }
                        }
                    },
                    {
                        index: 6,
                        value: "Trạng thái",
                        colSpan: 1,
                        style: {
                            borders: {
                                color: "#00000000",
                                lineStyle: "thin"
                            }
                        }
                    }
                ]
            };

            rows.push(headerRow);
            let dsPhanAnh = this.dsPhanAnh.map(item => item.NOIDUNGPA);
            dsPhanAnh = [...new Set(dsPhanAnh)];
            console.log(dsPhanAnh);
            let rowIndex = 2;
            dsPhanAnh.forEach((element, index) => {
                let row = {
                    index: rowIndex++,
                    cells: []
                };
                row.cells.push({
                    index: 1,
                    colSpan: 6,
                    value: element,
                    style: {}
                });
                rows.push(row);
                this.dsPhanAnh.forEach((element2, index2) => {
                    if (element2.NOIDUNGPA == element) {
                        let row2 = {
                            index: rowIndex++,
                            cells: [
                                { index: 2, value: element2.MA_KH },
                                { index: 3, value: element2.NGAYTIEPNHAN },
                                { index: 4, value: element2.NGAY_GQ },
                                { index: 5, value: element2.GHICHU },
                                { index: 6, value: element2.TRANGTHAI_PA }
                            ]
                        };
                        rows.push(row2);
                    }
                });
            });
            let worksheets = [{ columns, rows }];
            let workbook = new Workbook({ worksheets }, "xlsx");
            workbook.save("Export.xlsx");
        }
    }
};
</script>
