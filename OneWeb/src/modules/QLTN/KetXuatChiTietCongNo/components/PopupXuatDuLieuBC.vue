<template>
    <ejs-dialog :enableResize='true' :allowDragging='true' :visible='false' ref="dlgXuatDuLieuBC" header="Xuất dữ liệu"
        :animationSettings='animationSettings' showCloseIcon=true width='80%' :target='target' :open="dialogOpen"
        :close="close" :isModal="true">
        <div class="">
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a href="javascript:void(0)" @click="onClickButtonXuatFile" title="Xuất file">
                            <span class="icon one-download"></span>Xuất file
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" @click="onClickButtonXemDuLieu" title="Xem dữ liệu">
                            <span class="icon one-list-search"></span>Xem dữ liệu
                        </a>
                    </li>
                </ul>
            </div>
            <div class="popup-body">
                <div class="grid-stack-box">
                    <ejs-splitter id='nested-splitter' width='100%' height='100%' :created="onCreate">
                        <e-panes>
                            <e-pane content='#leftPane' size='30%'></e-pane>
                            <e-pane content='#rightPane' size='70%'></e-pane>
                        </e-panes>
                    </ejs-splitter>

                    <div class="box-col box-form" id="leftPane">
                        <div class="legend-title">Cấu trúc bảng dữ liệu</div>
                        <DataGrid v-bind:columns="[
                            { fieldName: '', headerText: '', textAlign: 'Center', width: '50', template: getColumnTemplateChonNhieu() },
                            { fieldName: 'NAME', headerText: 'Tên cột', width: '130' },
                            { fieldName: '', headerText: '', textAlign: 'Center', width: '50', template: getColumnTemplateXoa(this) },
                        ]" panelDataHeight="430" v-bind:dataSource="dsThuocTinh" :showFilter="false"
                            :allowPaging="false">
                        </DataGrid>
                    </div>
                    <div class="box-col box-form" id="rightPane">
                        <div class="legend-title">
                            <div class="pull-left">Xem trước dữ liệu</div>
                            <div class="pull-right">
                                <div class="check-action normal">
                                    <input type="checkbox" class="check" :disabled="true">
                                    <span class="name">Xuất dữ liệu tiếng Việt không dấu</span>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="table-content">
                            <DataGrid v-bind:columns="congNo_grid.columns" panelDataHeight="330"
                                v-bind:dataSource="congNo_grid.items" :showFilter="true" :allowPaging="true"
                                :enablePagingServer="true" :totalRecords="congNo_grid.pageInfo.totalElement"
                                :pageSize="congNo_grid.pageInfo.maxSize" :pageIndex="congNo_grid.pageInfo.page"
                                @pageChanged="congNoGridpageChangeHandler" ref="congNoGrid" :key="congNo_grid.key">
                            </DataGrid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </ejs-dialog>
</template>
<script>
import XLSX from "xlsx"
import Vue from 'vue'
import API from '../../api/CongNoAPI.js'
import { SplitterPlugin } from '@syncfusion/ej2-vue-layouts'
import { Splitter } from '@syncfusion/ej2-layouts'
import { resolve } from 'path'
Vue.use(SplitterPlugin)

const MAX_RECORD_PER_REQUEST = 20000;
const DEFAULT_INTERVAL = 10000; //10s

export default {
    name: 'PopupXuatDuLieuBC',
    props: ['columns', 'apiParams', 'totalElement'],
    data() {
        return {
            target: '.main-wrapper',
            animationSettings: { effect: 'Zoom' },
            dsThuocTinh: [],
            dsThuocTinhHeader: [],
            key: 1,
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
            exportExcel: {
                sessionId: -1,
                interval: null
            },
            sessions: []
        }
    },

    methods: {
        onCreate(e) {
            this.splitterObj = new Splitter({
                paneSettings: [
                    { size: '50%', min: '20%' },
                    { size: '50%', min: '20%' }
                ],
                orientation: 'Vertical'
            });
            this.splitterObj.appendTo('#vertical_splitter');
        },

        dialogOpen() {
        },

        dialogClose() {
        },

        openDialog() {
            this.dsThuocTinh = this.columns.map(item => { return { NAME: item.fieldName } });
            this.congNo_grid.columns = [...this.columns];
            this.$refs.dlgXuatDuLieuBC.show();
        },

        close() {
            this.dsThuocTinh = [];
            this.dsThuocTinhHeader = [];
            this.resetPaging();
            ++this.key;
            this.$refs.dlgXuatDuLieuBC.hide();
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
                            }
                        }
                    }
                }
            }
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
                }
            }
        },

        /* 
         * Kiểm tra session tạo file excel đã hoàn thành hay chưa?
         * Nếu đã hoàn thành -> Clear Interval và tải file
         * parameter: id: sessionId
         * return: Không
         */
        // onCheckSession(id) {
        //     let index = this.sessions.findIndex(e => e.id === id);
        //     if (index !== -1) {
        //         this.axios({
        //             url: 'https://api-onebss-dev.vnptit3.vn/web-thuno/api/thu-no/session/' + this.sessions[index].id,
        //             method: 'GET',
        //         }).then((response) => {
        //             if (response.data.data.status === 'COMPLETED') {
        //                 clearInterval(this.sessions[index].interval);
        //                 var fURL = document.createElement('a');
        //                 fURL.href = response.data.data.description;
        //                 fURL.setAttribute('download', 'DanhSachCongNoBaoCao.xlsx');
        //                 document.body.appendChild(fURL);
        //                 fURL.click();
        //             }
        //             //resolve(true);
        //         }).catch((error) => {
        //             console.log(error);
        //             this.$toast.error(`Có lỗi xảy ra khi kiểm tra trạng thái session: ${id}!`);
        //         });
        //     } else this.$toast.error(`Không tìm thấy session: ${id}!`);
        // },

        // async onDownloadExcel(numArr, params) {
        //     const sessionPromises = numArr.map(async (num) => {
        //         let apiParams = params;
        //         apiParams.pageNum = num;
        //         let sessionId = await this.getXuatFileXlsx(apiParams);
        //         if (sessionId && sessionId !== -1) {
        //             this.sessions.push({ id: sessionId, interval: 0 });
        //             console.log('sessions', this.sessions)
        //             let index = this.sessions.findIndex(e => e.id === sessionId);
        //             this.sessions[index].interval = setInterval(async (id) => {
        //                 console.log(`${num}----${id}`)
        //                 await this.onCheckSession(id);
        //             }, DEFAULT_INTERVAL, sessionId);
        //             return true;
        //         } else return false;
        //     });
        //     return Promise.all(sessionPromises);
        // },

        // async onClickButtonXuatFile() {
        //     try {
        //         this.loading(true);
        //         this.sessions = []; //Clear sesssion
        //         if (this.dsThuocTinh.length === 0)
        //             this.$toast.error('Bạn đã xóa hết các cột lấy DL!');
        //         else {
        //             if (this.totalElement > MAX_RECORD_PER_REQUEST) {
        //                 let params = this.apiParams;
        //                 params.pageSize = MAX_RECORD_PER_REQUEST;
        //                 params.totalRow = 0;
        //                 // let count = Math.ceil(this.totalElement / MAX_RECORD_PER_REQUEST);
        //                 let count = 5;
        //                 let prmArr = [ ...Array(count).keys() ].map( i => i+1);
        //                 console.log('prmArr', prmArr)
        //                 let res = await this.onDownloadExcel(prmArr, params);
        //                 if (res)
        //                     console.log('Xuất file thành công!')
        //             }
        //         }
        //     } catch (err) {
        //         console.log(err)
        //     } finally {
        //         this.loading(false);
        //     }
        // },

        /* Chạy tuần tự */
        async onClickButtonXuatFile() {
            try {
                if (this.dsThuocTinh.length == 0)
                    this.$toast.error('Bạn đã xóa hết các cột lấy DL!');
                else {
                    if (this.totalElement > MAX_RECORD_PER_REQUEST) {
                        let params = this.apiParams;
                        params.pageSize = MAX_RECORD_PER_REQUEST;
                        params.totalRow = 0;
                        let count = Math.ceil(this.totalElement / MAX_RECORD_PER_REQUEST);
                        for (let i = 1; i <= count; i++) {
                            params.pageNum = i;
                            await this.onDownloadExcel(params);
                        }
                    } else {
                        let params = this.apiParams;
                        params.pageSize = MAX_RECORD_PER_REQUEST;
                        params.totalRow = 0;
                        params.pageNum = 1;
                        await this.onDownloadExcel(params);
                    }
                }
            } catch (err) {
                console.log(err)
            } finally { }
        },

        async onDownloadExcel(params) {
            this.loading(true);

            this.exportExcel.sessionId = await this.getXuatFileXlsx(params);
            if (this.exportExcel.sessionId && this.exportExcel.sessionId !== -1) {
                return await new Promise((resolve) => {
                    this.exportExcel.interval = setInterval(async () => {
                        const baseUrl = process.env.API;
                        await this.axios({
                            url: `${baseUrl}/web-thuno/api/thu-no/session/` + this.exportExcel.sessionId,
                            method: "GET",
                        }).then((response) => {
                            if (response.data.data.status === 'COMPLETED') {
                                clearInterval(this.exportExcel.interval);
                                var fURL = document.createElement("a");
                                fURL.href = response.data.data.description;
                                fURL.setAttribute("download", "DanhSachCongNoBaoCao.xlsx");
                                document.body.appendChild(fURL);
                                fURL.click();
                                resolve('true');
                                this.loading(false);
                            }
                        }).catch((error) => {
                            console.log(error);
                            this.$toast.error('Có lỗi xảy ra, vui lòng kiểm tra lại!');
                        });
                    }, DEFAULT_INTERVAL);
                });
            }
        },

        async onClickButtonXemDuLieu() {
            this.resetPaging();
            await this.getDanhSachCongNoBaoCao();
        },

        onClickButtonXoaThuocTinh(thuocTinh) {
            this.dsThuocTinh = this.dsThuocTinh.filter(item => item.NAME != thuocTinh);
            this.congNo_grid.columns = this.congNo_grid.columns.filter(item => item.fieldName !== thuocTinh);
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
            }
        },

        async getDanhSachCongNoBaoCao() {
            try {
                this.loading(true);
                this.congNo_grid.key++;
                let apiParams = this.apiParams;
                apiParams.pageNum = this.congNo_grid.pageInfo.page + 1;
                apiParams.pageSize = this.congNo_grid.pageInfo.maxSize;
                let response = await API.getDanhSachCongNoBaoCao(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                    this.congNo_grid.items = response.data.data.data;
                    this.congNo_grid.pageInfo = {
                        page: response.data.data.page,
                        maxSize: response.data.data.maxSize,
                        totalElement: response.data.data.totalElement ? response.data.data.totalElement : 0,
                        totalPages: response.data.data.totalPages,
                        sort: response.data.data.sort,
                        propertiesSort: response.data.data.propertiesSort,
                        currentRow: 0
                    }
                } else this.resetPaging();
            }
            catch (error) {
                this.resetPaging();
                console.log(error)
                this.$toast.error(error.data.message_detail);
            }
            finally {
                this.loading(false);
            }
        },

        async getXuatFileXlsx(apiParams) {
            try {
                let response = await API.getXuatFileXlsx(this.axios, apiParams);
                return response.data.error_code === 'BSS-00000000' && response.data.data !== undefined
                    ? response.data.data.sessionId
                    : -1;
            }
            catch (error) {
                if (error.data.message_detail)
                    this.$toast.error(error.data.message_detail);
                return -1;
            }
            finally { }
        }
    }
}
</script>
<style>

</style>

