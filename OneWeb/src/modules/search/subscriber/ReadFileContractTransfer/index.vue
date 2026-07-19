<template src="./index.html"></template>

<script>
import define from "./define/index.js";
import api from "./api/index.js";
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
// import TTKHLeft from './components/TTKHLeft.vue';
// import TTKHRight from './components/TTKHRight.vue';
// import MuaTBI from './components/MuaTBI.vue';
// import ChonTBI from './components/ChonTBI.vue';
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue";
import ModalNormalizeCAinformation from "@/modules/contract/setup/NormalizeCAinformation/ModalNormalizeCAinformation.vue";
import ModalBuyDevices from "@/modules/contract/setup/InstallNewSubs/Popups/popupBuyDevices.vue";
import ModalTienKM from "@/modules/contract/setup/InstallNewSubs/Popups/popupTienKM.vue";
import PayMent from "@/modules/contract/setup/Payment/Payment.vue";
import { GridPlugin, Toolbar, ExcelExport } from "@syncfusion/ej2-vue-grids";
Vue.use(GridPlugin);
export default {
    provide: {
        grid: [Toolbar, ExcelExport]
    },
    components: {
        api,
        SearchContractModal,
        ModalNormalizeCAinformation,
        ModalBuyDevices,
        ModalTienKM,
        PayMent
    },
    props: ['tag'],
    created() {
        // this.initialLoad = false;
        
        if (this.$route.params.tag) {
            this.tag = this.$route.params.tag
        } else {
            this.tag = 90;
        }
        // console.log('tag : ', this.tag);
        // this.getDanhMucChung();
        this.initData();
        // this.LayDsBNM();
    },
    data() {
        return {
            ...define,
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
                this.nhanvien_id = this.$root.token.getNhanVienID();
                this.phanvung_id = this.$root.token.getPhanVungID();
                this.form.ngay_yc = moment(new Date()).format("DD/MM/YYYY");
                await this.getTTND();
            } catch (error) {
                console.log(error);
            }
        },
        async getTTND(){
            try {
                let res = await this.axios.post('/quantri/user/thongtin_nguoidung2', {});
                this.tt_nd = res.data.data;
            } catch (error) {
                console.log(error);
            }
        },
        clickButton(key) {
            if (!this.button[key]) {
                return false;
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
                if (this.options.hopdong.length == 0)
                {
                    this.$toast.warning("Danh sách trống, không có dữ liệu để cập nhật!");
                    return;
                }

                let dtLoi = [];
                // dtLoi.Columns.Add("MA_TB", typeof(string), "");
                // dtLoi.Columns.Add("DOITUONG_ID", typeof(string), "");
                // dtLoi.Columns.Add("NGAY_NT", typeof(string), "");
                // dtLoi.Columns.Add("TRANGTHAI", typeof(string), "");
                // let obloi = {
                //     MA_TB: '',
                //     DOITUONG_ID: '',
                //     NGAY_NT: '',
                //     TRANGTHAI: '',
                // }

                for (let idx = 0; idx < this.options.hopdong.length; idx++)
                {
                    let hdtb_id = Number(this.options.hopdong[idx]["hdtb_id"]);
                    let loaitb_id = Number(this.options.hopdong[idx]["loaitb_id"]);
                    let ma_tb = this.options.hopdong[idx]["ma_tb"];
                    let vthuebao_id = Number(this.options.hopdong[idx]["thuebao_id"]);
                    let vdoituong_id = Number(this.options.hopdong[idx]["doituong_id"]);
                    let vngay_nt = this.options.hopdong[idx]["ngay_nt"].slice(0,10).split("-").reverse().join('/');
                    let trial_code = this.options.hopdong[idx]["tocdo"];
                    // let common = new FacadeCommon_v2();
                    // TraCuuDanhBaFacade tcdanhba = new TraCuuDanhBaFacade();

                    if (loaitb_id == 58) // LoaiHinhTB.INTERNET_FTTH: 58
                    {
                        let res = await this.ThayDoi_DoiTuong_Visa(vdoituong_id, ma_tb, vngay_nt)
                        if (res  != 'ok')
                        {
                            let r = {};
                            r["ma_tb"] = ma_tb;
                            r["doituong_id"] = vdoituong_id;
                            r["ngay_nt"] = vngay_nt;
                            r["trangthai"] = "Có lỗi trong quá trình thay đổi đối tượng khách hàng trên Visa!";
                            dtLoi.push(r);
                        }
                        else
                        {
                            // VDCService vdc = new VDCService(tt_nd.user_visa, tt_nd.pass_visa);
                            if (!this.ChangeAcceptanceTime(ma_tb, vngay_nt, ""))
                            {
                                let r = {};
                                r["ma_tb"] = ma_tb;
                                r["doituong_id"] = vdoituong_id;
                                r["ngay_nt"] = vngay_nt;
                                r["trangthai"] = "Có lỗi trong quá trình thay đổi ngày hoàn thiện trên Visa!";
                                dtLoi.push(r);
                            }

                            try
                            {
                                // new LapHopDongFacade2().Chuyen_hd_chinhthuc_v2(hdtb_id, vdoituong_id,
                                //                         tt_nd.may_cn, tt_nd.ma_nd, tt_nd.ip, vngay_nt);
                                await this.axios.post('/web-hopdong/ds-doi-tam-dung-thanh-ly/sp_chuyen_hd_chinhthuc_v2', {
                                    p_phanvung_id: this.p_phanvung_id,
                                    p_hdtb_id: hdtb_id,
                                    p_doituong_id: vdoituong_id,
                                    p_may_cn: '',
                                    p_nguoi_cn: '',
                                    p_ip_cn: '1.1.1.1',
                                    p_ngay_nt: vngay_nt
                                });
                            }
                            catch (ex)
                            {
                                let r = {}
                                r["ma_tb"] = ma_tb;
                                r["doituong_id"] = vdoituong_id;
                                r["ngay_nt"] = vngay_nt;
                                r["trangthai"] = "Lỗi Chuyen_hd_chinhthuc Fiber: " + ex;

                                dtLoi.push(r);
                            }
                        }
                    }
                    if (loaitb_id == 61) // LoaiHinhTB.INTERNET_MYTV)
                    {
                        
                        // TruyenDanFacade tdan = new TruyenDanFacade();
                        let boqua = true;
                        try
                        {
                            
                            // VASCService vasc = new VASCService(tt_nd.user_vasc, tt_nd.pass_vasc, tt_nd.diemgiaodich_vasc);

                            let kq = await this.Cust_Trial_UpdateInfo(ma_tb, "3", "0", trial_code);
                            // if (kq[0].toString() != "1" && kq[0].toString() != "-7") //-7: Thuê bao đã chuyển sang dùng thật
                            // {
                            //     // DataRow r = dtLoi.NewRow();
                            //     let r = {};
                            //     r["ma_tb"] = ma_tb;
                            //     r["doituong_id"] = vdoituong_id;
                            //     r["ngay_nt"] = vngay_nt;
                            //     r["trangthai"] = kq[0].ToString() + ": " + kq[1].ToString();

                            //     dtLoi.Rows.Add(r);

                            //     tdan.EXECUTE_PROC("{?DB1}.QUANTRI_HETHONG.Log_Action", "vuser_name", tt_nd.ma_nd, "vcomputer_name", tt_nd.may_cn, "vaction_name", "frmDocFile_Chuyen_HD_That", "vkey_parameter",
                            //         "ma_tb=" + ma_tb, "vserver_name", tt_nd.ip, "vnote", " Ln: 786, Lỗi: " + kq[1].ToString());
                            //     continue;
                            // }
                            // debugger
                            if(kq.result != '1' && kq.result != '-7'){
                                let r = {};
                                r["ma_tb"] = ma_tb;
                                r["doituong_id"] = vdoituong_id;
                                r["ngay_nt"] = vngay_nt;
                                r["trangthai"] = kq.data.faultString;
                                dtLoi.push(r);
                            }

                            // new LapHopDongFacade2().Chuyen_hd_chinhthuc_v2(hdtb_id, vdoituong_id,
                            //             tt_nd.may_cn, tt_nd.ma_nd, tt_nd.ip, vngay_nt);
                            let res1= await this.axios.post('/web-hopdong/ds-doi-tam-dung-thanh-ly/sp_chuyen_hd_chinhthuc_v2', {
                                p_phanvung_id: this.p_phanvung_id,
                                p_hdtb_id: hdtb_id,
                                p_doituong_id: vdoituong_id,
                                p_may_cn: '',
                                p_nguoi_cn: '',
                                p_ip_cn: '1.1.1.1',
                                p_ngay_nt: vngay_nt
                            });
                            if(res1.data.error=='200' && res2.data.error_code=='BSS-00000000'){
                                this.$toast.success("Thành công")
                            }else{
                                this.$toast.warning(res1.data.message)
                            }
                        }
                        catch (ex)
                        {
                            console.log(e);
                        }
                    }
                }

                if (dtLoi.length > 0)
                {
                    // lblKetQua.Text = "Có lỗi khi thực hiện chuyển hợp đồng chính thức";
                    // gridLoi.DataSource = dtLoi;
                    // tabIn.SelectedTabPageIndex = 1;
                    // gridView1.BestFitColumns();
                    this.$toast.warning("Có lỗi khi thực hiện chuyển hợp đồng chính thức");
                    this.options.loi = dtLoi;
                }
                else
                {
                    this.$toast.success("Cập nhật kết quả thành công!");

                    this.options.loi = [];
                    this.form.duongdan = "";
                }

                this.options.hopdong =  [];
            }
            catch (ex)
            {
                this.$toast.error("Lỗi khi cập nhật kết quả: " + ex);
            }
        },
        async Cust_Trial_UpdateInfo( username,  method_flg, nomonth, trial_code)
        {
            try {
                let kq = [];
                let request = {};
                request['username'] = username;
                request['method_FLG'] = method_flg;
                request['nomonth'] = nomonth;
                request['trial_code'] = trial_code;
                // CommonVO obj = vasc.Cust_Trial_UpdateInfo(request);
                let res = await this.axios.post('/tichhop/vasc/cust_Trial_UpdateInfo', {
                    custDTO: request
                });
                // debugger
                let ob = res.data.data;
                
                if (res.errorCode != 0) {
                    this.$toast.warning(res.faultString)
                } else {
                    this.$toast.success(ob.errordesc)
                }
                return ob;
            } catch (error) {
                console.log(error);
            }
            
            // let message = "";
            // if (obj.Result != 1 && obj.Result != -7)
            // {
            //     if (obj.Result == -2)
            //     {
            //         message = "Dữ liệu đầu vào không hợp lệ. (" + obj.Errordesc + ")";
            //     }

            //     else if (obj.Result == -3)
            //     {
            //         message = "Dữ liệu vào DB không thành công (" + obj.Errordesc + ")";
            //     }
            //     else if (obj.Result == 1080)
            //     {
            //         message = "Lỗi VACS trả về: Lỗi ngoại lệ (" + obj.Errordesc + ")";
            //     }                
            //     else
            //     {
            //         message = "Nguyên nhân gây lỗi: " + obj.Errordesc;
            //     }
            // }

            // kq[0] = obj.Result;
            // kq[1] = message;

            // return kq;
           
        },
        async ChangeAcceptanceTime( account,  activeDate, note)
        {
            try
            {
                // DangNhapVisa();
                let myaccount = {};
                myaccount["accountName"] = account;
                myaccount["activeDate"] = activeDate;
                myaccount["reason"] = note;
                let res = await this.axios.post("/tichhop/visa-app/changeAcceptanceTime", {
                    accountModel: myaccount
                })
                // myws.changeAcceptanceTime(mylogin, myaccount);
                if(res.data.data == 'ok')
                    return true;
                return false;
            }
            catch (ex)
            {
                console.log(ex);
            }
        },
        async ThayDoi_DoiTuong_Visa( doituong_moi,  ma_tb, ngay_nt)
        {
            let cusType;
            try
            {
                // CheckDataFacade obj = new CheckDataFacade();
                // VDCService vdcService = new VDCService(tt_nd.user_visa, tt_nd.pass_visa);
                // string account = CommonFunction.ChuanHoa_XML(ma_tb);
                cusType = Number(await this.MAP_ID(doituong_moi));
                let res = await this.ChangeCustomerType(ma_tb, ngay_nt, cusType);
                return res;
                
                // return vdcService.ChangeCustomerType(account, ngay_nt, cusType);
            }
            catch (ex)
            {
                return false;
            }
        },
        async ChangeCustomerType(ma_tb, activeDate, cusType){
            try {
                let note = "Thay doi loai khach hang qua WS ngay : " + activeDate;
                let res = await this.axios.post('/tichhop/visa-app/changeCustomerType', {
                    customerModel: {
                        accountName: ma_tb,
                        customerType: cusType,
                        activeDate: activeDate,
                    },
                    note: note
                });
                if(res.data.data)
                    return res.data.data
                return res.data.faultString;
            } catch (error) {
                console.log(error);
            }
        },

        // đổi fix lỗi ATTT
        async MAP_ID(doituong_moi){
            try {
                // let res = await this.axios.post('/web-thicong/hths-thaydoi-tocdo/fn_map_id', {
                //     id_neo: 'doituong_id_vdc',
                //     table: 'doituong',
                //     dk: "where doituong_id = " + doituong_moi
                // });
                // /web-tratruoc/thongtin_tratruoc_dn/fn_tt_doituong
                let res = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_doituong', {
                    param: doituong_moi,
                    type: 1
                });
                return res.data.data;
            } catch (error) {
                console.log(error);
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
                            if(!keys.includes("MA_TB") || !keys.includes("DOITUONG_ID") || !keys.includes("NGAY_NT")){
                                this.$toast.warning("File không đúng định dạng, vui lòng tải lại file mẫu!");
                                return;
                            }
                            // }
                            let obhople = {
                                MA_TB: '',
                                DOITUONG_ID: '',
                                NGAY_NT: ''
                            },
                            obloi = {
                                MA_TB: '',
                                DOITUONG_ID: '',
                                NGAY_NT: '',
                                TRANGTHAI: '',
                            } 
                            let dtLoi = []
                            let dtHopLe = []

                            for (let r of dt_docfile)
                            {
                                // console.log(r);
                                let ss = this.ktra_dulieu(r["MA_TB"].toString().trim());
                                if (ss != "OK")
                                {
                                    let tem = {...obloi};
                                    tem.MA_TB = r["MA_TB"].toString().trim();
                                    tem.DOITUONG_ID = r["DOITUONG_ID"];
                                    tem.NGAY_NT = r["NGAY_NT"];
                                    tem["TRANGTHAI"] = ss;
                                    dtLoi.push(tem);
                                }
                                else
                                {
                                    let tem = {...obhople};
                                    tem.MA_TB = r["MA_TB"].toString().trim();
                                    tem.DOITUONG_ID = r["DOITUONG_ID"];
                                    tem.NGAY_NT = r["NGAY_NT"];
                                    dtHopLe.push(tem);
                                    
                                }
                            }
                            if(dtLoi.length > 0){
                                this.$toast.warning("Dữ liệu từ file chưa chính xác !!");
                                return;
                            }
                            console.log(dtHopLe);
                            //Kiem tra logic du lieu
                            // DataSet dsKQ = new TCHopDongFacade2().Kiemtra_dl_docfile_dungthu_v2(tt_nd.nguoidung_id);
                            // if (dsKQ.Tables["dtLoi"].Rows.Count > 0)
                            // {
                            //     lblKetQua.Text = "Có thông tin lỗi";
                            //     gridLoi.DataSource = dsKQ.Tables["dtLoi"];
                            //     tabIn.SelectedTabPageIndex = 1;
                            //     gridView1.BestFitColumns();
                            // }
                            // gridThueBao.DataSource = dsKQ.Tables["dtHopLe"];
                            // gridViewThueBao.BestFitColumns();

                            let res = await this.axios.post('/web-hopdong/docfilechuyenhdthat/sp_doc_file_chuyen_hd_that', {
                                p_phanvung_id: this.phanvung_id,
                                p_nguoidung_id: this.tt_nd.nguoidung_id,
                                p_data: JSON.stringify(dtHopLe)
                            });
                            if(res.data.error == '200' && res.data.error_code=='BSS-00000000'){
                                this.options.hopdong = res.data.data['rs1'];
                                this.options.loi = res.data.data['rs2'];
                            }else{
                                this.$toast.warning(res.data.message)
                            }
                        }else{
                            this.$toast.warning('File không có dữ liệu !!')
                        }
                    };
                    reader.readAsBinaryString(fileupload);
                }
            }
            catch (error) {
                console.log(error);
            }
        },
        ktra_dulieu(ma_tb){
            if (!ma_tb)
            {
                return "Mã TB (MA_TB) không được để trống!";
            }

            return "OK";
        }
        
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