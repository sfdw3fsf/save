<template>
    <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" ref="dlgXuatDuLieuBC" header="Xuất dữ liệu"
        :animationSettings="animationSettings" showCloseIcon="true" width="80%" :target="target" :open="dialogOpen"
        :close="close" :isModal="true">
        <div class="">
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a href="javascript:void(0)" @click="onClickButtonXuatFile">
                            <span class="icon one-download"></span>Xuất file
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" @click="onClickButtonXemDuLieu">
                            <span class="icon one-list-search"></span>Xem dữ
                            liệu
                        </a>
                    </li>
                </ul>
            </div>
            <div class="popup-body">
                <div class="grid-stack-box">
                    <ejs-splitter id="nested-splitter" width="100%" height="100%" :created="onCreate">
                        <e-panes>
                            <e-pane content="#leftPane" size="30%"></e-pane>
                            <e-pane content="#rightPane" size="70%"></e-pane>
                        </e-panes>
                    </ejs-splitter>

                    <div class="box-col box-form" id="leftPane">
                        <div class="legend-title">Cấu trúc bảng dữ liệu</div>
                        <DataGrid v-bind:columns="[
                            {
                                fieldName: '',
                                headerText: '',
                                textAlign: 'Center',
                                width: '50',
                                template: getColumnTemplateChonNhieu()
                            },
                            {
                                fieldName: 'NAME',
                                headerText: 'Tên cột',
                                width: '130'
                            },
                            {
                                fieldName: '',
                                headerText: '',
                                textAlign: 'Center',
                                width: '50',
                                template: getColumnTemplateXoa(this)
                            }
                        ]" panelDataHeight="430" v-bind:dataSource="dsThuocTinh" :showFilter="false"
                            :allowPaging="false">
                        </DataGrid>
                    </div>
                    <div class="box-col box-form" id="rightPane">
                        <div class="legend-title">
                            <div class="pull-left">Xem trước dữ liệu</div>
                            <div class="pull-right">
                                <div class="check-action normal">
                                    <input type="checkbox" class="check" v-model="isTiengVietKoDau">
                                    <span class="name">Xuất dữ liệu tiếng Việt không dấu</span>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="table-content">
                            <DataGrid3 v-bind:columns="congNo_grid.columns" panelDataHeight="330"
                                v-bind:dataSource="congNo_grid.items" :showFilter="true" :allowPaging="true"
                                :enablePagingServer="true" :totalRecords="
                                   this.apiParams ? this.apiParams.total : congNo_grid.pageInfo.totalElement
                                " :pageSize="congNo_grid.pageInfo.maxSize" :pageIndex="congNo_grid.pageInfo.page"
                                @pageChanged="congNoGridpageChangeHandler" ref="congNoGrid" :key="congNo_grid.key"
                                :allowExcelExport="true">

                            </DataGrid3>
                        </div>
                    </div>

                </div>
                <!-- <div v-show="showProgress">
                    <div>
                        <div class="legend-title">Số file đã tạo/tổng số : {{ dataDownLoad.finnishedParts }}/{{
                        dataDownLoad.totalParts
                        }}
                        </div>
                    </div>
                    <div>
                        <DataGrid
                            v-bind:columns="[
                            { fieldName: '', headerText: 'Tên file', template: getColumnTemplateLink(this), allowFiltering: true }]"
                            v-bind:dataSource="dataDownLoad.dsLinks" :showFilter="false">

                        </DataGrid>
                    </div>

                </div> -->
            </div>

        </div>
    </ejs-dialog>
</template>
<script>

import Vue from "vue";
import { mapActions, mapState, mapGetters } from "vuex";
import API from '../api/CongNoAPI.js'
import xlsx from "xlsx";
import moment from "moment";

import { SplitterPlugin } from "@syncfusion/ej2-vue-layouts";
import { Splitter } from "@syncfusion/ej2-layouts";
Vue.use(SplitterPlugin);
export default {
    name: "PopupXuatDuLieuBC",
    props: ["columns", "apiParams", "kieu", 'file'],
    data() {
        return {
            target: ".main-wrapper",
            animationSettings: { effect: "Zoom" },
            dsThuocTinh: [],
            dsThuocTinhHeader: [],
            isTiengVietKoDau: false,
            dsColNumber: [],
            key: 1,
            dataDownLoad: [],
            trangloi: [],
            showProgress: false,
            congNo_grid: {
                columns: [],
                items: [],
                key: 1,
                pageInfo: {
                    page: 0,
                    maxSize: 10,
                    totalElement: 0,
                    totalPages: 0,
                    currentRow: 0,
                    sort: null,
                    propertiesSort: null
                }
            },

        };
    },

    methods: {
        ...mapActions("TaoDuLieuXacNhanCongNo", [
            "getLanTao",
            "capNhatTuFile",
            "taoCongNo",
            "getChuKy",
            "getListCongNo",
            "getXuatFileExcel",
            "clearData"
        ]),
        onCreate(e) {
            this.splitterObj = new Splitter({
                paneSettings: [
                    { size: "50%", min: "20%" },
                    { size: "50%", min: "20%" }
                ],
                orientation: "Vertical"
            });
            this.splitterObj.appendTo("#vertical_splitter");
        },

        dialogOpen() { },

        dialogClose() { },

        openDialog() {
            this.dsThuocTinh = this.columns.map(item => {
                return { NAME: item.fieldName };
            });
            this.congNo_grid.columns = [...this.columns];

            this.isTiengVietKoDau = false
            this.$refs.dlgXuatDuLieuBC.show();

        },

        close() {
            this.dsThuocTinh = [];
            this.dsThuocTinhHeader = [];
            this.resetPaging();
            ++this.key;
            this.$refs.dlgXuatDuLieuBC.hide();
            this.showProgress = false;
        },

        getColumnTemplateXoa(parent) {
            return function () {
                return {
                    template: {
                        template: `
                            <button class="btn btn-sm btn-danger lh14 pad3" @click="parent.onClickButtonXoaThuocTinh(data.NAME)">
                                <span class="-ap icon-close"></span>
                            </button>
                        `,
                        data() {
                            return {
                                parent: parent,
                                data: {}
                            };
                        }
                    }
                };
            };
        },
        getColumnTemplateLink(parent) {
            return function () {
                return {
                    template: {
                        template: `
                             <a href="javascript:void(0)"  @click="downLoadFile(data.link)">
                                    <span>{{data.name}}</span>
                                </a>
                        `,
                        data() {
                            return {
                                parent: parent,
                                data: {}
                            }
                        },
                        methods: {
                            downLoadFile(file) {
                                var fURL = document.createElement("a");
                                fURL.href = file;
                                fURL.setAttribute(
                                    "download",
                                    "DanhSachCongNo.xlsx"
                                );
                                document.body.appendChild(fURL);
                                fURL.click();
                            }
                        }
                    }
                };
            };
        },
        getColumnTemplateChonNhieu() {
            return function () {
                return {
                    template: {
                        template: `
                            <div class="check-action">
                                <input type="checkbox" class="check" :checked="true">
                                <span class="name"></span>
                            </div>
                        `
                    }
                };
            };
        },
        async onClickButtonXuatFile() {
            if (this.dsThuocTinh.length == 0) {
                return this.$toast.error("Bạn đã xóa hết các cột lấy DL!");
            }
            if (this.kieu == 1) {
                let i = 0;
                this.loading(true);
                this.showProgress = true;
                let so_promise = Math.ceil(this.apiParams.total / 10000)
                for (i = 0; i < so_promise; i++) {
                    this.trangloi.push(i + 1);
                };
                this.dataDownLoad = [];
                do {


                    let mang_promise = [];
                    for (i = 0; i < this.trangloi.length; i++) {
                        mang_promise[i] = this.getDanhSach(this.trangloi[i]);
                    };
                    this.trangloi = [];
                    await Promise.all(mang_promise)
                        .then((values) => {
                            this.Loading = false
                        })
                        .catch((error) => {
                            this.Loading = false
                            console.log(`Error in promises ${error}`)
                        })
                } while (this.trangloi.length > 0)

                this.onExcelExport(this.dataDownLoad);
            }
            else {
                let i = 0;
                this.loading(true);
                this.showProgress = true;
                let so_promise = Math.ceil(this.apiParams.total / 10000)
                for (i = 0; i < so_promise; i++) {
                    this.trangloi.push(i + 1);
                };
                this.dataDownLoad = [];
                do {


                    let mang_promise = [];
                    for (i = 0; i < this.trangloi.length; i++) {
                        mang_promise[i] = this.getDanhSachBaoCao(this.trangloi[i]);
                    };
                    this.trangloi = [];
                    await Promise.all(mang_promise)
                        .then((values) => {
                            this.Loading = false
                        })
                        .catch((error) => {
                            this.Loading = false
                            console.log(`Error in promises ${error}`)
                        })
                } while (this.trangloi.length > 0)

                this.onExcelExport(this.dataDownLoad);
            }



            this.loading(false);
            this.$toast.success('Xuất dữ liệu thành công');


        },

        async onClickButtonXemDuLieu() {
            if (this.kieu == 1) {
                this.resetPaging();
                await this.getDanhSachCongNoBaoCao();
            }
            else
                this.congNo_grid.items = this.file;

        },

        onClickButtonXoaThuocTinh(thuocTinh) {
            this.dsThuocTinh = this.dsThuocTinh.filter(
                item => item.NAME != thuocTinh
            );
            this.congNo_grid.columns = this.congNo_grid.columns.filter(
                item => item.fieldName !== thuocTinh
            );
        },

        async congNoGridpageChangeHandler(e) {
            this.congNo_grid.pageInfo.page = e.pageIndex;
            this.congNo_grid.pageInfo.maxSize = e.pageSize;
            await this.getDanhSachCongNoBaoCao();
        },

        resetPaging() {
            this.congNo_grid.items = [];
            this.congNo_grid.pageInfo = {
                page: 0,
                maxSize: 10,
                totalElement: 0,
                totalPages: 0,
                currentRow: 0,
                sort: null,
                propertiesSort: null
            };
        },

        async getDanhSachCongNoBaoCao() {
            try {
                this.loading(true);

                this.congNo_grid.key++;
                let apiParams = this.apiParams;
                apiParams.pageNum = this.congNo_grid.pageInfo.page + 1;
                apiParams.pageSize = this.congNo_grid.pageInfo.maxSize;
                let response = null;
                if (this.kieu == 1)
                    response = await this.getListCongNo(apiParams);
                else
                    response = await API.getDanhSachCongNoBaoCao(this.axios, apiParams);


                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    if(response.data.data.hasOwnProperty('data')){
                        this.congNo_grid.items = response.data.data.data;
                        this.congNo_grid.pageInfo = {
                            page: response.data.data.page,
                            maxSize: response.data.data.maxSize,
                            totalElement: response.data.data.totalElement
                                ? response.data.data.totalElement
                                : 0,
                            totalPages: response.data.data.totalPages,
                            sort: response.data.data.sort,
                            propertiesSort: response.data.data.propertiesSort,
                            currentRow: 0
                        };
                    } else {
                        this.congNo_grid.items = response.data.data
                    }

                } else this.resetPaging();


            } catch (error) {
                this.resetPaging();
                console.log(error);
                this.$toast.error(error.data.message_detail);
            } finally {
                this.loading(false);
            }
        },
        async getDanhSach(trang) {
            try {

                let apiParams = this.apiParams;
                apiParams.pageNum = trang;
                apiParams.pageSize = 10000;
                let response = await this.getListCongNo(apiParams);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    response.data.data.forEach((item) => {
                        this.dataDownLoad.push(item);
                    });
                    if (this.dataDownLoad.length == 50000) {
                        let dulieu_xuat = this.dataDownLoad;
                        this.dataDownLoad = [];
                        this.onExcelExport(dulieu_xuat);

                    }


                }
                else
                    this.trangloi.push(trang);
            } catch (error) {
                this.trangloi.push(trang);
            }
        },
        async getDanhSachBaoCao(trang) {
            try {

                let apiParams = this.apiParams;
                apiParams.pageNum = trang;
                apiParams.pageSize = 10000;

                let response = await API.getDanhSachCongNoBaoCao(this.axios, apiParams);


                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    response.data.data.forEach((item) => {
                        this.dataDownLoad.push(item);
                    });
                    if (this.dataDownLoad.length == 50000) {
                        let dulieu_xuat = this.dataDownLoad;
                        this.dataDownLoad = [];
                        this.onExcelExport(dulieu_xuat);

                    }


                }
                else
                    this.trangloi.push(trang);
            } catch (error) {
                this.trangloi.push(trang);
            }
        },
        async getXuatFileXlsx() {
            try {
                let response = await this.getXuatFileExcel({
                    chiTiet: this.apiParams.chiTiet,
                    lanTaoId: this.apiParams.lanTaoId,
                    total: this.apiParams.total,

                });
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    return response.data.data.sessionId;
                } else return -1;
            } catch (error) {
                if (error.data.message_detail)
                    this.$toast.error(error.data.data.message_detail);
                return -1;
            }
        },

        onExcelExport(dulieu) {
            if (dulieu.length === 0) {
                this.$root.toastError("Chưa có danh sách để xuất excel!");
            } else {
                var wb = xlsx.utils.book_new();
                if (dulieu.length > 0) {
                    let exportData = []
                    this.dsColNumber = [];
                    let head = 0;
                    dulieu.forEach(item => {
                        let data_exp = {}
                        let cot_so = 0;
                        this.dsThuocTinh.forEach(item1 => {
                            if (this.isTiengVietKoDau) {
                                if (!item[item1.NAME] || item[item1.NAME] == '')
                                    data_exp[item1.NAME] = ''
                                else
                                    data_exp[item1.NAME] = this.xoa_dau('' + item[item1.NAME])
                            }
                            else {
                                data_exp[item1.NAME] = item[item1.NAME]

                            }

                            if ((item1.NAME == 'TONGNO' || item1.NAME == 'NODUONG' || item1.NAME == 'NOAM') && head == 0)
                                this.dsColNumber.push(cot_so);
                            cot_so++;
                        })
                        head++;
                        exportData.push(data_exp)

                    });
                    this.appendSheet(exportData, 0, wb);
                    xlsx.writeFile(
                        wb,
                        `CongNo_${moment(new Date()).format(
                            "DD/MM/YYYY HH:mm:ss"
                        )}.xlsx`
                    );
                }
            }
        },

        appendSheet(items, type, workbook) {

            try {
                let sheetTitle = "Danh sách";
                var ds = xlsx.utils.json_to_sheet(items);
                const currency = '#,##0'
                for (let col of this.dsColNumber) {
                    this.formatColumn(ds, col, currency)
                }
                xlsx.utils.book_append_sheet(workbook, ds, sheetTitle);
            } catch (error) {
                console.log(error);
            }
        },
        formatColumn(worksheet, col, fmt) {
            const range = xlsx.utils.decode_range(worksheet['!ref'])
            // note: range.s.r + 1 skips the header row
            for (let row = range.s.r + 1; row <= range.e.r; ++row) {
                const ref = xlsx.utils.encode_cell({ r: row, c: col })
                if (worksheet[ref] && worksheet[ref].t === 'n') {
                    worksheet[ref].z = fmt
                }
            }
        },
        xoa_dau(str) {
            try {
                str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, "a");
                str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, "e");
                str = str.replace(/ì|í|ị|ỉ|ĩ/g, "i");
                str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, "o");
                str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, "u");
                str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, "y");
                str = str.replace(/đ/g, "d");
                str = str.replace(/À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ/g, "A");
                str = str.replace(/È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ/g, "E");
                str = str.replace(/Ì|Í|Ị|Ỉ|Ĩ/g, "I");
                str = str.replace(/Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ/g, "O");
                str = str.replace(/Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ/g, "U");
                str = str.replace(/Ỳ|Ý|Ỵ|Ỷ|Ỹ/g, "Y");
                str = str.replace(/Đ/g, "D");
            }
            catch (e) {
                consolo.log(e);
            }

            return str;
        }
    }
}
</script>
<style scoped>
.legend-title{
    font-weight: 600 !important;
}
</style>
