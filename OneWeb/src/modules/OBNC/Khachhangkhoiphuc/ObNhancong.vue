<template src="./ObNhancong.html"></template>
<style src="./ObNhancong.scss"></style>
<style>
.el-dialog {
    margin: 0 auto;
    /* Đặt dialog nằm giữa theo chiều ngang */
    top: 0;
    /* Đặt dialog nằm ở trên cùng */
}
</style>
<script>
import { TabComponent, TabItemDirective, TabItemsDirective } from '@syncfusion/ej2-vue-navigations';
//import { ComboBoxComponent as EjsCombobox } from "@syncfusion/ej2-vue-dropdowns"
import breadcrumb from '@/components/breadcrumb'
import { DatePickerComponent } from "@syncfusion/ej2-vue-calendars"
import { MultiSelectPlugin } from "@syncfusion/ej2-vue-dropdowns";
import { MultiSelect, CheckBoxSelection } from '@syncfusion/ej2-dropdowns';
import api from "./api_ObNhancong.js"
import { GridComponent, ColumnDirective, DropDownListPlugin, ColumnsDirective, GridPlugin, Page, Sort, Filter, Edit, Toolbar, ExcelExport } from "@syncfusion/ej2-vue-grids";
import PopupTracuuTb from './PopupTracuuTb.vue';
import TDataGrid from './DataGrid'
import TracuuGrid from './DataGrid'
import ThongTinKHOBNC from './GridIndex.vue'
import THONGTINTRACUU from './GridTracuu.vue'
import apiHelper from './api.helper'
import _ from 'lodash'
import moment from 'moment'
import { Icon } from '@iconify/vue2'
import util from './util'
import searchAccount from '@/modules/search/subscriber/SearchAccount'
//import Swal from 'sweetalert2'
//import ChangeService from '@/modules/contract/setup/ChangeService'


MultiSelect.Inject(CheckBoxSelection);


export default {
    name: 'OutboundNhancong',
    components: {
        breadcrumb,
        PopupTracuuTb,
        TDataGrid,
        TracuuGrid,
        ThongTinKHOBNC,
        THONGTINTRACUU,
        Icon,
        searchAccount,
        //ChangeService,
        // Đăng ký component con

        // 'ejs-tab': TabComponent,
        // 'e-tabitem': TabItemDirective,
        // 'e-tabitems': TabItemsDirective,
        'ejs-grid': GridComponent,
        'e-column': ColumnDirective,
        'e-columns': GridPlugin,
        // 'e-columns': Page,
        // 'e-columns': Sort,
        // 'e-columns': Filter,
    },

    provide: {
        grid: [Page, Sort, Filter, Edit, Toolbar, ExcelExport]
    },

    data() {
        return {
            currentComponent: null,
            currentTab: 1,
            thongtinTracuu: '',
            txttungay: null,
            txtdenngay: null,
            tungayTracuu: null,
            denngayTracuu: null,
            showPopup: false,
            isConnected: false,
            KetquaId: null,
            IdOutbound: null,

            //selectedUser: '', // Giá trị chọn từ grid
            showGrid: false,  // Hiển thị popup grid
            UserCallData: [], // Dữ liệu combobox
            UserCallFields: { text: 'user_ipcc', value: 'line' }, // Field trong combobox
            UserCallWaterMark: 'Chọn user...', // Placeholder combobox
            //userData: [],
            // userData: [ //Dữ liệu cho grid trong popup
            //     { userIPCC: 'thuyn7', line: '70001' },
            //     { userIPCC: 'thuynb.hpg', line: '70002' }
            // ],
            dialogVisible: false,
            switchStates: [false, false],
            //switchStates: [], // Trạng thái switch cho từng dòng

            selectedReading: '',
            ReadingData: [],
            ReadingFields: { text: "Reading", value: "Id" },
            ReadingWaterMark: "Chọn...",
            isConnected: false,

            isValidPhone: false,

            btnStatus: {
                btnLayttEnabled: true,
                btnCapnhatObEnabled: false,
                btnOutboundEnabled: true,
                btnCallEnabled: false,
                isCboReadingEnabled: false,
            },


            tungay: {
                value: new Date(),
                start: 'Year',
                depth: 'Year',
                format: 'dd/MM/yyyy'
            },
            denngay: {
                value: new Date(),
                start: 'Year',
                depth: 'Year',
                format: 'dd/MM/yyyy'
            },
            // grdTBkhoiphuc: {
            //     dataSource: [],
            //     value: '',
            //     selectedDate: "",
            // },
            cboTapOb: {
                dataSource: [],
                value: "1",
            },
            cboThongtinKetnoi: {
                dataSource: [],
                value: 1,
            },
            cboTracuuOb: {
                dataSource: [],
                value: 1,
            },
            cboDsKetquaOB: {
                dataSource: [],
                value: '',
                selectedDate: "",
            },


            // 
            Dauso_Id: "0888006666",
            ThongtinKetnoi_Id: "1",
            //KetquaOb_Id: "1",

            DausoData: [
                { "Id": "0888006666", "Dauso": "Tổng đài BRCĐ - 0888006666" },
                { "Id": "0918001260", "Dauso": "Tổng đài CNTT - 0918001260" },
            ],
            DausoFields: { text: "Dauso", value: "Id" },
            DausoWaterMark: "Chọn...",
            // 

            KetquaOb_Id: null,
            KetquaObData: [],
            KetquaObFields: { text: "kq_ob", value: "kqob_id" },
            KetquaObWaterMark: "Chọn...",
            selectedKqObItem: null,
            txtTenTapOb: null,
            txtGhichu: null,
            phieucskh: 0,
            txtTenDoituongOb: null,

            // Trong phần TAB 2
            TracuuData: [
                { "Id": "1", "Tracuu": "Số máy/Acc" },
                { "Id": "2", "Tracuu": "Mã khách hàng" },
                { "Id": "3", "Tracuu": "Số điện thoại OB" },
            ],
            TracuuFields: { text: "Tracuu", value: "Id" },
            TracuuWaterMark: "Chọn điều kiện tra cứu...",

            txtSdttt: '',
            txtSdtkh: '',
            txtSdttb: '',
            txtDstt: '',
            txtDskh: '',
            txtSdtob: '',
            v_userIPCC: '',
            v_lineIPCC: '',
            v_statusIPCC: '',
            chon_ready: 'NOT_READY',

        };
    },

    created() {
        this.getdsTapObNhancong();
        //this.getTBkhoiphuc();
        this.setDefaultDate();
        this.getdsThongtinKetnoi();
        this.getdsUser();
    },

    methods: {
        changeTab(tab) {
            this.currentTab = tab;
            console.log(tab);
            if (tab === 2) {
                this.getdsTracuuOb();
            }
        },

        async chonUserOB(row, activeState) {
            //alert(row.userIPCC + " " + activeState);
            console.log("Switch for userIPCC:", row.user_ipcc, "is now", activeState ? "ON" : "OFF");
            //Tắt tất cả các switch khác trước khi bật cái mới
            this.switchStates = this.switchStates.map((state, index) => {
                return this.userData[index].user_ipcc === row.user_ipcc ? true : false;
            });

            if (activeState) {
                // Nếu switch ON, gán giá trị userIPCC vào combobox

                this.selectedUser = row.user_ipcc;
                this.UserCallData = [{ user_ipcc: row.user_ipcc }]; // Cập nhật datasource của combobox
                this.v_userIPCC = row.user_ipcc;
                this.v_lineIPCC = row.line;
                this.v_statusIPCC = "LOGIN";
                this.btnStatus.isCboReadingEnabled = true;

                this.ReadingData = [
                    { "Id": "1", "Reading": "Ready" },
                    { "Id": "2", "Reading": "Not Ready" }
                ];

                this.dialogVisible = false; // Ẩn popup sau khi chọn

                try {
                    // Gọi API và đợi kết quả trả về
                    let dsAgent = {
                        username: this.v_userIPCC,
                        extension: this.v_lineIPCC,
                        status: this.v_statusIPCC
                    };
                    let result = await api.postAgentState(this.axios, dsAgent);

                    // this.btnStatus.isCboReadingEnabled = (this.chon_ready == v_ready);

                } catch (error) {
                    console.error("Error calling API:", error);
                }


            } else {
                // Nếu switch OFF, reset giá trị combobox
                const index = this.userData.findIndex(user => user.user_ipcc === row.user_ipcc);
                if (index !== -1) {
                    this.switchStates[index] = false; // Đặt trạng thái switch về OFF
                    this.selectedUser = '';
                    this.selectedReading = '';
                    this.UserCallData = [];
                    this.ReadingData = [];  // Xóa dữ liệu ban đầu

                    // Reset các giá trị khác
                    this.v_statusIPCC = "LOGOUT";
                    this.isConnected = false;
                    this.btnStatus.isCboReadingEnabled = false;

                    try {
                        // Gọi API và đợi kết quả trả về
                        let dsAgent = {
                            username: this.v_userIPCC,
                            extension: this.v_lineIPCC,
                            status: this.v_statusIPCC
                        };

                        console.log(dsAgent);
                        result = await api.postAgentState(this.axios, dsAgent);
                        console.log(result.data.data);
                        //this.btnStatus.isCboReadingEnabled = (this.chon_ready == 'LOGOUT');


                    } catch (error) {
                        console.error("Error calling API:", error);
                    }
                }
            }
        },
        openPopup() {
            this.dialogVisible = true; // Mở popup khi focus vào combobox
            this.getdsUser();
        },

        selectUser(user_ipcc) {
            this.selectedUser = user_ipcc; // Gán giá trị chọn vào combobox
            this.UserCallData = [{ user_ipcc: user_ipcc }]; // Cập nhật combobox
            this.showGrid = false; // Ẩn popup sau khi chọn
        },


        // Hàm để chuyển đổi ngày thành định dạng dd/mm/yyyy
        formatDate(date) {
            if (!date) return ''; // Trả về chuỗi trống nếu không có ngày
            const day = String(date.getDate()).padStart(2, '0');
            const month = String(date.getMonth() + 1).padStart(2, '0'); // Tháng bắt đầu từ 0
            const year = date.getFullYear();
            return `${day}/${month}/${year}`;
        },
        setDefaultDate() {
            const currentDate = new Date();
            const pastDate = new Date();
            pastDate.setDate(currentDate.getDate() - 30); // Trừ 30 ngày từ ngày hiện tại
            this.tungay.value = pastDate;
            this.txttungay = pastDate;
            this.txtdenngay = currentDate;
            this.tungayTracuu = pastDate;
            this.denngayTracuu = currentDate;

        },
        resetThongtinOb() {
            this.txtSdttt = "";
            this.txtSdtkh = "";
            this.txtSdttb = "";
            this.txtDstt = "";
            this.txtDskh = "";
            this.txtSdtob = "";
            this.txtMatb = "";
            this.btnStatus.btnCallEnabled = false;
            this.phoneNumberOb = "";
            this.cboThongtinKetnoi.value = 1;
            this.KetquaOb_Id = null,
                this.txtGhichu = "";
        },
        async getdsTapObNhancong() {
            try {
                let response = await api.getcboDanhsachTapOb(this.axios);
                this.cboTapOb.dataSource = response.data.data;
                //console.log(this.cboTapOb.value);
                const defaultItem = this.cboTapOb.dataSource.find(item => item.tapob_id == 1);
                if (defaultItem) {
                    this.cboTapOb.value = defaultItem.tapob_id;  // Gán tapob_id = 1 làm mặc định
                    this.txtTenTapOb = defaultItem.ten_tapob;  // Gán ten_tapob tương ứng
                }

            } catch (e) {
                console.log(e)
            }
        },
        async getdsThongtinKetnoi() {
            try {
                let response = await api.getcboTrangthaiKetnoi(this.axios);
                this.cboThongtinKetnoi.dataSource = response.data.data
            } catch (e) {
                console.log(e)
            }
        },

        async getdsTracuuOb() {
            try {
                let response = await api.getcboTracuuOb(this.axios);
                this.cboTracuuOb.dataSource = response.data.data
                console.log(this.cboTracuuOb.value);
                const defaultItemTracuuOb = this.cboTracuuOb.dataSource.find(item => item.doituong_id == 0);
                if (defaultItemTracuuOb) {
                    this.cboTracuuOb.value = defaultItemTracuuOb.doituong_id;  // Gán doituong_id = 0 làm mặc định
                    this.txtTenDoituongOb = defaultItemTracuuOb.ten_doituong;  // Gán ten_doituong tương ứng
                }
            } catch (e) {
                console.log(e)
            }
        },
        async getDsKetquaOB() {
            try {
                this.cboDsKetquaOB.dataSource = [];
                this.KetquaOb_Id = null;
                let dsData = { tapob_id: this.cboTapOb.value, ttkn_id: this.cboThongtinKetnoi.value };
                let result = null;
                result = await api.poscboKetquaOb(this.axios, dsData);
                this.KetquaObData = result.data.data;
            } catch (error) {
                console.error('Lỗi khi gọi API:', error);
            }
        },

        onComboBoxChangeKqOb(event) {
            // Lấy giá trị kqob_id và kq_ob khi thay đổi lựa chọn
            const selectedItem = event.itemData;
            if (selectedItem) {
                this.selectedKqObItem = selectedItem.kq_ob;  // Lấy giá trị kq_ob
                console.log(this.selectedKqObItem);
            }
        },

        onDropdownChangeTenTapOb(event) {
            // Lấy giá trị tapob_id và ten_tapob khi thay đổi lựa chọn
            const selectedItem = event.itemData;
            if (selectedItem) {
                this.resetThongtinOb();
                this.getdsThongtinKetnoi();
                this.getDsKetquaOB();
                this.txtTenTapOb = selectedItem.ten_tapob;  // Lưu giá trị ten_tapob
            }
        },

        async listData() {
            if (this.currentTab == 1) {
                let vtapob_id = this.cboTapOb.value;
                let vtungay = this.formatDate(this.txttungay);
                let vdenngay = this.formatDate(this.txtdenngay);
                //let response = await api.getDsTbKhoiphucThanhlyTamdung(this.axios, vtapob_id, vtungay, vdenngay);
                //this.grdTBkhoiphuc.dataSources = response.data.data;
                await this.$refs.refListOBNC.getListOBNC(vtapob_id, vtungay, vdenngay)
                //console.log(response.data.data);
            } else if (this.currentTab == 2) {
                let vdoituong_id = this.cboTracuuOb.value;
                let vthongtin = this.thongtinTracuu;
                let vtungay = this.formatDate(this.tungayTracuu);
                let vdenngay = this.formatDate(this.denngayTracuu);
                console.log(vdoituong_id, vthongtin, vtungay, vdenngay);
                await this.$refs.refListTTTC.getListTTTC(vdoituong_id, vthongtin, vtungay, vdenngay);
            }
        },
        async receiveDataKH(data) {
            if (this.isConnected == true) {
                this.resetThongtinOb();
                this.txtSdttt = data;
                let response = await api.getDsThongtinThuebaoOB(this.axios, this.txtSdttt, this.cboTapOb.value);
                const items = response.data.data
                const item = items[0]
                this.txtSdtkh = item.ds_sdt_kh
                this.txtSdttb = item.ds_sdt_tb
                this.txtDstt = item.ds_tt_thanhtoan
                this.txtDskh = item.ds_tt_kh
                this.phoneNumberOb = data;
                this.btnStatus.btnCapnhatObEnabled = false;
                //bo tich chon duoi luoi
                this.$refs.refListOBNC.desel_grid_RowDeselected(this.txtSdttt);
                this.getKiemTraCuocGoiOutBound(); // Gọi Api kiểm tra SDT đang OB
            } else {
                this.$root.$toast.error('Cần kết nối để thực hiện cuộc gọi');
            }
        },

        async getKiemTraCuocGoiOutBound() {

            if (this.currentTab == 1) {
                let v_sdtob = this.phoneNumberOb;
                let response = await api.getKiemtraSdtOB(this.axios, v_sdtob);
                this.KetquaId = response.data.data[0].kq_id;

                if (this.KetquaId === '1') {
                    this.$root.$toast.warning(response.data.data[0].ten_kq);
                    this.btnStatus.btnCallEnabled = false;
                } else {
                    this.isValidPhone = true;
                    this.btnStatus.btnCallEnabled = true;
                    this.$root.$toast.success('Đã có thể thực hiện cuộc gọi');
                }

            }
        },

        validatePhoneNumber() {
            const regex = /^[0-9]+$/;  // Chỉ cho phép ký tự số
            this.isValidPhone = regex.test(this.phoneNumberOb) && this.phoneNumberOb.length > 0;

            if (this.KetquaId == 1) {
                this.btnStatus.btnCallEnabled = false;
            } else {
                if (this.isValidPhone) {
                    this.btnStatus.btnCallEnabled = true;
                } else {
                    this.btnStatus.btnCallEnabled = false;
                }
            }
        },

        async makeCall() {   // Gọi OB khách hàng

            try {
                let response = await api.getThuchienCuocgoi(this.axios, this.phoneNumberOb, this.Dauso_Id);  // Gọi API thông tin thực hiện cuộc gọi
                this.ttgoiData = response.data.data;
                // console.log(response.data.data);
                const ttgois = response.data.data;
                const ttgoi = ttgois[0];
                this.IdOutbound = ttgoi.ob_id;

                let callOutbound = {
                    ob_id: ttgoi.ob_id,
                    dauso_ob: this.Dauso_Id,
                    sdt_ob: this.phoneNumberOb,
                    ten_ct: ttgoi.ten_ct,
                    ma_ct: ttgoi.ma_ct,
                    user_ipcc: this.v_userIPCC,
                    line: this.v_lineIPCC
                };
                console.log(callOutbound);
                let result = await api.postCallOutbound(this.axios, callOutbound);
                //this.KetquaObData = result.data.data;
                this.btnStatus.btnCapnhatObEnabled = true;
            } catch (e) {
                console.log(e)
            }

        },

        async saveData() {  // Ghi thông tin OB

            if (this.currentTab == 1) {
                //this.isConnected = false;

                if (this.cboThongtinKetnoi.value == 2) this.phieucskh = await this.getPhieuCSKH();
                //alert(this.phieucskh);
                const listThueBaoSelected = this.$refs.refListOBNC.getListThueBaoSlected();
                this.UpdateResultOB(_.unionBy(listThueBaoSelected, "thuebao_id"));  // Update vào danh sách

                const matb_kt = listThueBaoSelected.map(thueBao => thueBao.ma_tb).join(',');  // Nối chuỗi ma_tb
                const loaitb_id = listThueBaoSelected[0]?.loaitb_id;
                const ma_tb = listThueBaoSelected[0]?.ma_tb;
                const ob_id = this.IdOutbound;
                const loai_ob = 1;

                if (this.KetquaOb_Id == 32 && listThueBaoSelected.length > 0) {
                    //this.$refs.ChangeServiceDialog.show()
                    const url = `/contract/ChangeService?ma_tb=${ma_tb}&loaitb_id=${loaitb_id}&ob_id=${ob_id}&loai_ob=${loai_ob}&matb_kt=${encodeURIComponent(matb_kt)}`;
                    window.open(url, '_blank');
                }
                if (this.KetquaOb_Id == 8 && listThueBaoSelected.length > 0) {
                    const url = `/contract/liquidate?ma_tb=${ma_tb}&loaitb_id=${loaitb_id}&ob_id=${ob_id}&loai_ob=${loai_ob}&matb_kt=${encodeURIComponent(matb_kt)}`;
                    window.open(url, '_blank');
                }
            }

        },

        handleValidateData() {
            if (!this.cboThongtinKetnoi.value) {
                this.$toast.warning('Vui lòng chọn kết nối')
                return false;
            }

            if (!this.KetquaOb_Id) {
                this.$toast.warning('Vui lòng chọn kết quả OB')
                return false;
            }

            return true
        },

        getPhieuCSKH() {
            try {
                let titePopup = 'Bạn có chắc chắn muốn chuyển phiếu CSKH về cho NVB2A đến nhà khách hàng để xử lý không?'
                if (confirm(titePopup) == true) {
                    return 1;
                } else {
                    return 0;
                }
            } catch (error) {
                console.log(error);
            }
            return 0;
        },


        // async getPhieuCSKH() {
        //     this.loading(true)
        //     try {
        //         this.$bvModal.msgBoxConfirm ('Bạn có chắc chắn muốn chuyển phiếu CSKH về cho NVB2A đến nhà khách hàng để xử lý không?', {
        //             title: 'Thông báo',
        //             size: 'sm',
        //             okTitle: 'Đồng ý',
        //             cancelTitle: 'Hủy',
        //         });
                 
        //         alert(this.$bvModal.msgBoxConfirm)
       
        //     } catch (error) {
        //         console.log(error);
        //     } finally {
        //         this.loading(false);
        //         return 0;
        //     }
        // },


        async UpdateResultOB(listItemSelected) {

            if (listItemSelected.length == 0) {
                this.$toast.warning('Không có thông tin khách hàng!')
                return;
            }

            console.log("listItemSelected:", listItemSelected);
            if (!this.handleValidateData()) return;

            try {

                const json_params = listItemSelected.map(item => ({
                    phanvung_id: 28,
                    ob_id: this.IdOutbound,
                    nguoidung_id: this.$root.token.getNguoiDungID(),
                    ttkn_id: this.cboThongtinKetnoi.value,
                    kqob_id: this.KetquaOb_Id.length ? this.KetquaOb_Id : "",
                    hen_tu: "",
                    hen_den: "",
                    chudekn_id: "",
                    kieuld_id: item.kieuld_id,
                    // ghichu: (this.cboThongtinKetnoi.value == 2 && !this.txtGhichu) ? (this.txtTenTapOb || '') + ' - ' + (this.selectedKqObItem || '')  : (this.txtTenTapOb || '') + ' - ' + (this.txtGhichu || ''),
                    ghichu: (this.cboThongtinKetnoi.value == 2 && !this.txtGhichu)
                        ? (this.txtTenTapOb || '') + ' - ' + (this.selectedKqObItem || '')
                        : (this.cboThongtinKetnoi.value != 2)
                            ? this.txtGhichu || ''
                            : (this.txtTenTapOb || '') + ' - ' + (this.txtGhichu || ''),
                    tapob_id: this.cboTapOb.value,
                    sdt_ob: this.phoneNumberOb,
                    khachhang_id: item.khachhang_id,
                    thuebao_id: item.thuebao_id,
                    hdtb_id: 123456,
                    phieucskh_ob: this.phieucskh,
                }));

                console.log(JSON.stringify(json_params));

                const response = await api.postKetquaOBNC(this.axios, JSON.stringify(json_params))

                if (!apiHelper.isResponseApiSuccess(response)) {
                    let strErr = apiHelper.getSrtCodeError(response);
                    this.$toast.error(strErr)
                    return;
                }
                this.$toast.success('Cập nhật kết quả OB thành công')
                this.resetThongtinOb();
                this.btnStatus.btnCapnhatObEnabled = false;
                //this.handelResponseSaveOB(response, listItemSelected)
            } catch (error) {
                console.log(error);
                this.$toast.error(`Có lỗi xảy ra: ${error}`)
            }
        },

        handelResponseSaveOB(response, listItemSelected) {
            try {
                if (this.cboThongtinKetnoi.value !== 1) return;

                //lay response của hàm auto gia hạn OB
                // const autoGHData = apiHelper.getDataFromResponseApiReturnRaise(response); 

                // //vd: {data: "MGD-202108-0000000001", errorCode: ERR-1, faultString: "Khong cung thue bao"}
                // if(autoGHData.errorCode !== "ERR-0"){
                //     const message = autoGHData.faultString
                //     this.$toast.warning(message)
                // }

                // let arrayMergeByKey = listItemSelected;

                // const itemRes = util.getItemReturn(listItemSelected);
                // const data = {
                //     MA_TB_KT: itemRes.MA_TB_KT || "",
                //     MA_TB: itemRes?.MA_TB || "",
                //     LOAITB_ID: itemRes?.LOAITB_ID,
                //     TT_KETNOI: this.cbbTTKetNoi.value,
                //     KQ_OB: this.cbbKetQuaOB.value,
                //     CHUDEKN_ID: this.cbbChuDeKN.value,
                //     OB_ID: this.ob_id,
                //     AUTO_GH_DATA: autoGHData || null,
                //     DICHVUVT_ID: itemRes?.DICHVUVT_ID,
                // }

                // const handleResultOB = handleResultOBManger.getResultOB(Number(this.KetquaOb_Id));

                // if(handleResultOB)  handleResultOB.processResult(this, data)

            } catch (error) {
                console.log(error);
            }
        },

        async getdsUser() {
            try {
                let response = await api.getDanhsachUserOB(this.axios);
                this.userData = response.data.data;
                console.log(response.data.data);

                // const defaultItem = this.cboTapOb.dataSource.find(item => item.tapob_id == 1);
                // if (defaultItem) {
                //     this.cboTapOb.value = defaultItem.tapob_id;  // Gán tapob_id = 1 làm mặc định
                //     this.txtTenTapOb = defaultItem.ten_tapob;  // Gán ten_tapob tương ứng
                // }

            } catch (e) {
                console.log(e)
            }
        },
        async onCboReadingChange(args) {
            // Kiểm tra giá trị được chọn và cập nhật biến isConnected
            if (args.itemData && args.itemData.Id === "1") {
                this.v_statusIPCC = "READY_OB";
                try {
                    let dsReady = {
                        username: this.v_userIPCC,
                        line: this.v_lineIPCC,
                        status: this.v_statusIPCC
                    };

                    let result = await api.postAgentState(this.axios, dsReady);
                    this.isConnected = true; // Nếu Id là "1", gán isConnected = true
                    this.btnStatus.btnCallEnabled = (this.KetquaId === '1' || this.KetquaId === null) ? false : true;

                } catch (error) {
                    console.error("Error calling API:", error);
                }

            } else {
                this.v_statusIPCC = "NOT_READY";
                try {
                    let dsNotReady = {
                        username: this.v_userIPCC,
                        line: this.v_lineIPCC,
                        status: this.v_statusIPCC
                    };
                    let result = await api.postAgentState(this.axios, dsNotReady);
                    console.log(result.data.data)

                    this.isConnected = false; // Nếu Id là "1", gán isConnected = true
                    this.btnStatus.btnCallEnabled = false;
                    this.btnStatus.btnCapnhatObEnabled = false;

                } catch (error) {
                    console.error("Error calling API:", error);
                }
            }

        },

        SearchDanhBa: function () {
            this.$refs.searchAccountDialog.show()
        },
        searchAccountDialogClose(data) {

            if (data != null) {

                if (data.ma_tb != null && data.ma_tb != '') {
                    this.thongtinTracuu = data.ma_tb;
                }
                else {
                    this.thongtinTracuu = data.ma_kh;
                }
            }
            this.$refs.searchAccountDialog.hide()
        },
        ChangeServiceDialogClose() {
            this.$refs.ChangeServiceDialog.hide();
        },
        onEnter: async function () {
            this.listData();
        },

    },

}
</script>