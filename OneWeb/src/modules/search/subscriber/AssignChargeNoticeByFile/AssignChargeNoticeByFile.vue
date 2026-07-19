<template src="./AssignChargeNoticeByFile.html"></template>

<script>
import define from "./define/index.js";
import api from "./API.js";
import * as moment from 'moment';
import Vue from "vue";
import { SplitterPlugin } from '@syncfusion/ej2-vue-layouts';
import { ButtonPlugin } from '@syncfusion/ej2-vue-buttons';
import { TabPlugin } from '@syncfusion/ej2-vue-navigations';
import { ListBoxPlugin } from "@syncfusion/ej2-vue-dropdowns";
import { L10n, setCulture } from '@syncfusion/ej2-base';
import XLSX from 'xlsx';

Vue.use(ListBoxPlugin);
Vue.use(SplitterPlugin);
Vue.use(ButtonPlugin);
Vue.use(TabPlugin);
L10n.load({
    'vi-VN': {
        'listbox': {
            'noRecordsTemplate': "", // provide your own text here 
        }
    }
});

import { GridPlugin, Toolbar, ExcelExport } from "@syncfusion/ej2-vue-grids";
Vue.use(GridPlugin);
export default {
    provide: {
        grid: [Toolbar, ExcelExport]
    },
    components: {
        api,
    },
    props: ['tag'],
    created() {
        this.initData();
    },
    data() {
        return {
            ...define,
            Tag: '',
            checkboxTemplate: function () {
                return {
                    template: t
                }
            },
            filterBarMode: { filterBarMode: "Immediate" },
            currentRows: [],
            selectionOptions: { checkboxMode: 'ResetOnRowClick' },
            formatOptions: { type: 'date', format: 'dd/MM/yyyy' },
            contentTTKHLeft: function () {
                return {
                    template: TTKHLeft
                }
            },
            contentTTKHRight: function () {
                return {
                    template: TTKHRight
                }
            },
            headerText0: { text: 'Danh sách' },
            headerText1: { text: 'Lỗi' },
        }
    },
    methods: {
        
        async initData() {
            try {
                await this.getDSLoaiYC()

                this.cboLoaiYC.value = this.cboLoaiYC.Items[0].loaitin_id
            } catch (error) {
                console.log(error);
            }
        },
        async getDSLoaiYC() {
            try {
                this.cboLoaiYC.Items = []
                let res = await api.lay_loaitin_test_tbc(this.axios);
                if (res.data && res.data.error_code == 'BSS-00000000' && res.data.data) {
                    this.cboLoaiYC.Items = res.data.data
                }
            } catch (error) {
                console.log(error);
            }
        },
        clickButton(key) {
            if (!this.button[key]) {
                return false;
            }
            if (key == 'laytt') {
                this.loading(true)
                this.Kiemtra_dl_test_tbc();
                this.loading(false)
            }
            if (key == "capnhat") {
                // this.$refs.popupBuyDevices.show();
                this.tsbtnCapNhat_Click();
            }
            if (key == "xuatexcel") {
                this.tsbtnXuat_Click();
            }
        },
        async tsbtnCapNhat_Click(){
            try
            {
                this.loading(true)
                if (this.options.hopdong.length == 0)
                {
                    this.$toast.error("Danh sách trống, không có dữ liệu để cập nhật!");
                    this.loading(false)
                    return;
                }
                var input = {
                    vloaitin_id: this.cboLoaiYC.value ? Number(this.cboLoaiYC.value) : 0
                }
                var res = await api.capnhat_dl_test_tbc(this.axios, input)
                if (res && res.data && res.data.error_code == 'BSS-00000000') {
                    this.$toast.success('Cập nhật dữ liệu thành công!')
                } else {
                    this.$toast.error(res.data.message_detail)
                }
                this.loading(false)
            }
            catch (ex)
            {
                this.$toast.error("Lỗi khi cập nhật kết quả: " + JSON.stringify(ex));
                this.loading(false)
            }
        },
        
        tsbtnXuat_Click(){
            this.$refs.gridEr.excelExport({fileName: "LOI.xlsx"})
        },
        async UploadFileHoSo(e) {
            try {
                let fileupload = this.$refs.files.files[0];
                this.form.duongdan = e.target.files[0].name;
                let nhanvien_id = this.nhanvien_id;
                let nguoi_cn = this.nguoi_cn;
                if(this.form.duongdan){
                    var reader = new FileReader();
                    reader.onload = async (e) => {
                        
                        const readOpts = { // <--- need these settings in readFile options
                            cellText:false, 
                            cellDates:true
                        };

                        const jsonOpts = {
                            header: 1,
                            defval: '',
                            blankrows: true,
                            raw: false,
                            dateNF: 'dd"/"mm"/"yyyy' // <--- need dateNF in sheet_to_json options (note the escape chars)
                        };
                        var workbook = XLSX.read(e.target.result, {type: 'binary', cellText:false,cellDates:true});

                        //get the name of First Sheet.
                        var Sheet = workbook.SheetNames[0];
    
                        //Read all rows from First Sheet into an JSON array.
                        var excelRows = XLSX.utils.sheet_to_json(workbook.Sheets[Sheet], {raw:false,dateNF:'dd/mm/yyyy'});
                        console.log(excelRows);
                        if (excelRows && excelRows.length > 0) {
                            // lblKetQua.Text = "";
                            
                            // #region Đọc file
                            let dt_docfile = excelRows;
                            console.log(dt_docfile);

                            // gridThueBao.DataSource = null;
                            // gridLoi.DataSource = null;
                            this.options.hopdong = [];
                            this.options.loi = [];
                            //Kiểm tra cấu trúc file
                            let keys = Object.keys(dt_docfile[0]);
                            if(!keys.includes("MA_TT") || !keys.includes("SO_DT")){
                                this.$toast.error("File không đúng định dạng, vui lòng tải lại file mẫu!");
                                return;
                            }
                            // }
                            let obhople = {
                                MA_TT: '',
                                SO_DT: '',
                            },
                            obloi = {
                                MA_TT: '',
                                SO_DT: '',
                                TRANGTHAI: '',
                            } 
                            let dtLoi = []
                            let dtHopLe = []

                            for (let r of dt_docfile)
                            {
                                // console.log(r);
                                let ss = this.ktra_dulieu(r["MA_TT"].toString().trim(), r["SO_DT"].toString().trim());
                                if (ss != "OK")
                                {
                                    let tem = {...obloi};
                                    tem.MA_TT = r["MA_TT"].toString().trim();
                                    tem.SO_DT = r["SO_DT"];
                                    tem["TRANGTHAI"] = ss;
                                    dtLoi.push(tem);
                                }
                                else
                                {
                                    let tem = {...obhople};
                                    tem.MA_TT = r["MA_TT"].toString().trim();
                                    tem.SO_DT = r["SO_DT"];
                                    dtHopLe.push(tem);
                                    
                                }
                            }
                            if(dtLoi.length > 0){
                                this.$toast.error("Dữ liệu từ file chưa chính xác !!");
                                return;
                            }
                            //Kiem tra logic du lieu
                            // let res = await this.axios.post('/web-hopdong/docfilechuyenhdthat/sp_doc_file_chuyen_hd_that', {
                            //     p_phanvung_id: this.phanvung_id,
                            //     p_nguoidung_id: this.tt_nd.nguoidung_id,
                            //     p_data: JSON.stringify(dtHopLe)
                            // });
                            // this.options.hopdong = res.data.data['rs1'];
                            // this.options.loi = res.data.data['rs2'];

                            if (dtHopLe && dtHopLe.length > 0) {
                                var input = {
                                    vds_tmp_thuchien_tbc: JSON.stringify(dtHopLe)
                                }
                                let res1 = await api.fn_ins_dl_tmp_thuchien_tbc(this.axios, input);
                                if (res1.data && res1.data.error_code == 'BSS-00000000' && res1.data.data == 'ok') {
                                    // this.options.hopdong = dtHopLe
                                }
                            }
                            
                            if (dtLoi && dtLoi.length > 0) {
                                input = {
                                    vds_loi_thuchien_tbc: JSON.stringify(dtLoi)
                                }
                                let res2 = await api.fn_ins_dl_loi_thuchien_tbc(this.axios, input);
                                if (res2.data && res2.data.error_code == 'BSS-00000000' && res2.data.data == 'ok') {
                                    // this.options.loi = dtLoi
                                }
                            }

                            await this.Kiemtra_dl_test_tbc()
                            
                        }else{
                            this.$toast.error('File không có dữ liệu !!')
                        }
                    };
                    reader.readAsBinaryString(fileupload);
                }
            }
            catch (error) {
                console.log(error);
            }
        },
        Kiemtra_dl_test_tbc: async function () {
            try {
                this.options.hopdong = []
                this.options.loi = []
                var res3 = await api.kiemtra_dl_test_tbc(this.axios)
                if (res3.data && res3.data.error_code == 'BSS-00000000' && res3.data.data) {
                    this.options.hopdong = res3.data.data.returnds1 ? this.UpperCasePropertyList(res3.data.data.returnds1) : []
                    this.options.loi = res3.data.data.returnds2 ? this.UpperCasePropertyList(res3.data.data.returnds2) : []
                }
            } catch (e) {
                this.$toast.error(JSON.stringify(e))
            }
        },
        ktra_dulieu(ma_tt, so_dt){
            if (!ma_tt) {
                return "Mã TT (MA_TT) không được để trống!";
            }

            if (!so_dt) {
                return "Số ĐT (SO_DT) không được để trống!";
            }

            return "OK";
        },
        LowerCasePropertyList(obj) {
            return obj.map(function(item) {
                for (var key in item) {
                var upper = key.toLowerCase()
                // check if it already wasn't uppercase
                if (upper !== key) {
                    item[upper] = item[key]
                    delete item[key]
                }
                }
                return item
            })
        },
        UpperCasePropertyList(obj) {
            return obj.map(function(item) {
                for (var key in item) {
                var upper = key.toUpperCase()
                // check if it already wasn't uppercase
                if (upper !== key) {
                    item[upper] = item[key]
                    delete item[key]
                }
                }
                return item
            })
        },
        
    },
    mounted() {
        // this.handleTag()
        // console.log(this.$root.token.getMaTinh());
    }
}
</script>

<style>
/* @import "../../../../../node_modules/@syncfusion/ej2-vue-layouts/styles/material.css"; */
/* @import "../../../../../node_modules/@syncfusion/ej2-vue-buttons/styles/material.css"; */

.e-splitter .e-split-bar.e-split-bar-horizontal {
    /* background: blue; */
    border: solid rgba(41 42 71 / 54%);
}
.e-splitter .e-split-bar.e-split-bar-horizontal .e-resize-handler {
    color: rgb(255, 255, 255);
    font-weight: bolder;
}
/* .e-splitter .e-split-bar.e-split-bar-horizontal .e-resize-handler {
    color: rgba(20, 27, 233, 0.54);
}
.e-splitter .e-split-bar.e-split-bar-horizontal.e-split-bar-hover .e-navigate-arrow.e-arrow-left::before, .e-splitter .e-split-bar.e-split-bar-horizontal.e-split-bar-active .e-navigate-arrow.e-arrow-left::before, .e-splitter .e-split-bar.e-split-bar-horizontal.e-split-bar-hover .e-navigate-arrow.e-arrow-left::after, .e-splitter .e-split-bar.e-split-bar-horizontal.e-split-bar-active .e-navigate-arrow.e-arrow-left::after, .e-splitter .e-split-bar.e-split-bar-horizontal.e-split-bar-hover .e-navigate-arrow.e-arrow-right::before, .e-splitter .e-split-bar.e-split-bar-horizontal.e-split-bar-active .e-navigate-arrow.e-arrow-right::before, .e-splitter .e-split-bar.e-split-bar-horizontal.e-split-bar-hover .e-navigate-arrow.e-arrow-right::after, .e-splitter .e-split-bar.e-split-bar-horizontal.e-split-bar-active .e-navigate-arrow.e-arrow-right::after {
    background-color: green;
} */

/* #app-split {
        text-align: center;
        margin: 45px auto;
    }

    .content {
        justify-content: center;
        text-align: center;
        align-items: center;
        padding: 9px;
    }

    .e-splitter {
        margin: 0 auto;
    }

    .e-pane {
        text-align: center;
        align-items: center;
        display: grid;
    }

    #collapse {
        margin-left: 10px;
    } */

@font-face {
    font-family: "e-listbox-icons";
    src: url(data:application/x-font-ttf;charset=utf-8;base64,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)
        format("truetype");
    font-weight: normal;
    font-style: normal;
}

.e-list-icons {
    font-family: "e-listbox-icons";
    speak: none;
    font-style: normal;
    font-weight: normal;
    font-variant: normal;
    text-transform: none;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
}

.e-list-touch:before {
    content: "\e700";
}

.e-list-reply-all:before {
    content: "\e701";
}
.e-list-reply:before {
    content: "\e702";
}

.e-list-save-all-attachments:before {
    content: "\e703";
}

.e-list-icon-save-as:before {
    content: "\e704";
}

.e-list-user-settings:before {
    content: "\e705";
}

.e-list-address-book:before {
    content: "\e706";
}

.e-list-delete:before {
    content: "\e707";
}

.e-list-forward:before {
    content: "\e708";
}

.e-list-undo:before {
    content: "\e709";
}
.e-listbox-wrapper:not(.e-list-template) .e-list-item {
    padding: 4px 16px;
    height: 30px;
}
.e-listbox-wrapper .e-list-item,
.e-listbox-container .e-list-item {
    background-color: #fff;
    border: none;
    /* padding: 2px 16px; */
    /* color: #212529; */
}
</style>