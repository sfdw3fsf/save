<template src="./index.html"></template>

<script>
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import KBreadCrumb from '@/components/kylq_components/mirana/KBreadCrumb'
import LocDuLieuModal from './LocDuLieuModal.vue'
import DataImport from './DataImport.vue'
import {getListDonViVNP, DictDonViVNPT, columnsChuaGan, columnsCTTB} from './Utils'
import API from './API'
import XLSX from 'xlsx'
import SearchAccountModal from '../../../contract/setup/SurveyRequest/popups/SearchAccountModal.vue'
import _ from 'lodash'
import moment from 'moment';
import apiHelper from './apiHelper'
import { exportExcel } from './export-excel'
export default {
    name:'DoiSoatDuLieuIT',
    components:{
        breadcrumb,
        KDataGrid,
        LocDuLieuModal,
        DataImport,
        SearchAccountModal,
        KBreadCrumb
    },
    data(){
        return {
            file:null,
            data_import: {
              columns : [],
              list: [],
              results: []
            },
            title: "Đối soát dữ liệu thuê bao",
            thongKeShow:false,
            kieu_selected:1,
            ds_kieu:[
                {
                    id:1,
                    text:'Chọn trên lưới'
                },
                {
                    id:2,
                    text:'Danh sách chưa ghép'
                },
                {
                    id:3,
                    text:'Chi tiết thuê bao'
                }
            ],
            columns:  [
                {
                    fieldName: "MA_TINH",
                    headerText: "Tỉnh",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "THUTU",
                    headerText: "TT",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "MA_TB",
                    headerText: "Mã TB",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                //event cell click chọn
                {
                    fieldName: "MA_TB_TINH",
                    headerText: "Mã TB Tỉnh",
                    allowFiltering: true,
                    width:160,
                    visible:true,
                    template: this.columnTemplateMaTBTinh(this),
                },
                {
                    fieldName: "MA_TB_CT",
                    headerText: "Mã chi tiết",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "MA_TB_CT_TINH",
                    headerText: "Mã chi tiết Tỉnh",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "TEN_TB",
                    headerText: "Tên thuê bao tỉnh",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "DIACHI_LD",
                    headerText: "Địa chỉ lắp đặt tỉnh",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "TrangThai",
                    headerText: "Trạng thái Xử lý",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "TEN_KH",
                    headerText: "Tên KH",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "DIACHI_KH",
                    headerText: "Địa chỉ KH",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "SO_DT",
                    headerText: "Số ĐT",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "LOAIHINH",
                    headerText: "Loại hình TB",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "MST",
                    headerText: "MST",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "NGUOI_DD",
                    headerText: "Người đại diện",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "EMAIL",
                    headerText: "Email",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "DOMAIN",
                    headerText: "Domain",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "MA_BHXH",
                    headerText: "Mã BHXH",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "MA_CSYT",
                    headerText: "Mã CSYT",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "MA_TRUONG_HOC",
                    headerText: "Mã trường học",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "MA_NHA_THUOC",
                    headerText: "Mã nhà thuốc",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "HD_PHARMACY",
                    headerText: "Mã hợp đồng Pharmacy",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "ACC_ORDER",
                    headerText: "Người Order",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "SERIAL_TOKEN",
                    headerText: "Serial Token",
                    allowFiltering: true,
                    width:100
                },
                {
                    fieldName: "PARENT_SERIAL_TOKEN" ,
                    headerText: "Parent Serial Token",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "GHICHU",
                    headerText: "Ghi chú",
                    allowFiltering: true,
                    width:200,
                    allowEditing:true,
                    visible:true
                },
                {
                    fieldName: "NGAY_INS",
                    headerText: "Ngày thêm",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "NGAY_SD",
                    headerText: "Ngày sử dụng",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "NGUOI_CN",
                    headerText: "Người cập nhật",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "NGAY_CN",
                    headerText: "Ngày cập nhật",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "NGAY_INSERT",
                    headerText: "Ngày thêm",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "MAY_CN",
                    headerText: "Máy CN",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "IP_CN",
                    headerText: "IP_CN",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "FILE",
                    headerText: "File",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "ACCOUNT_TAO",
                    headerText: "Account khởi tạo",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "ACC_ORDER",
                    headerText: "Người Order",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "DVI_TAO",
                    headerText: "Đơn vị tạo yêu cầu",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "LAST_ACTIVE",
                    headerText: "Hoạt động gần nhất",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "CROSS",
                    headerText: "Nguồn",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "LOAITB_ID",
                    headerText: "LOAITB_ID",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "FILE_ID",
                    headerText: "File ID",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "SO_HOPDONG",
                    headerText: "Số hợp đồng",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "ID_PHIEU",
                    headerText: "ID phiếu",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "ID_THIETBI",
                    headerText: "ID Thiết bị",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "MA_CRM",
                    headerText: "Mã CRM",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "GOI_DV",
                    headerText: "Gói dịch vụ",
                    allowFiltering: true,
                    width:100
                },
                {
                    fieldName: "NGAYYEUCAU",
                    headerText: "Ngày yêu cầu",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "AM_AS",
                    headerText: "AM - AS",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "TINHTRANGPHIEU",
                    headerText: "Tình trạng phiếu",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "NOIDUNGPHIEU",
                    headerText: "Nội dung phiếu",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "IP",
                    headerText: "IP",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "KHONGGIAN",
                    headerText: "Không gian",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "SERIAL",
                    headerText: "Serial",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "BANGTHONG_TRONGNUOC",
                    headerText: "Băng thông trong nước",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "BANGTHONG_QUOCTE",
                    headerText: "Băng thông quốc tế",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "CONGSUAT",
                    headerText: "Công suất",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "IDC",
                    headerText: "IDC",
                    allowFiltering: true,
                    width:100
                },
                {
                    fieldName: "TANG",
                    headerText: "Tầng",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "RACK",
                    headerText: "Rack",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "VITRI",
                    headerText: "Vị trí",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "TENTHIETBI",
                    headerText: "Tên thiết bị",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "NGAYHOANTHANH",
                    headerText: "Ngày hoàn thành",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "NGUOILIENHE",
                    headerText: "Người liên hệ",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "DIENTHOAILIENHE",
                    headerText: "SĐT Liên hệ",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "NHOM_THIETBI",
                    headerText: "Nhóm thiết bị",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "SOLUONG_THIETBI",
                    headerText: "Số lượng tbi",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "DONVI_QUANLY_HD",
                    headerText: "Đơn vị QL HD",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "DONVI_BANHANG",
                    headerText: "Dvi Bán hàng",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "DONVI_YEUCAU",
                    headerText: "Dvi Yêu cầu",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"AGENT_ID",
                    headerText:"AGENT_ID",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"ADSER_ID",
                    headerText:"ADSER_ID",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"CONTRACT_ID",
                    headerText:"CONTRACT_ID",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"TEN_TB_BACKEND",
                    headerText:"TEN_TB_BACKEND",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"USERNAME",
                    headerText:"USERNAME",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"LABEL_ID",
                    headerText:"LABEL_ID",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"PHANLOAI_ID",
                    headerText:"PHANLOAI_ID",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"LINHVUC_ID",
                    headerText:"LINHVUC_ID",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"LINHVUC_VMS_ID",
                    headerText:"LINHVUC_VMS_ID",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"LINHVUC_VTL_ID",
                    headerText:"LINHVUC_VTL_ID",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"LINHVUC_GTEL_ID",
                    headerText:"LINHVUC_GTEL_ID",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"LINHVUC_VNM_ID",
                    headerText:"LINHVUC_VNM_ID",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"LINHVUC_ITEL_ID",
                    headerText:"LINHVUC_ITEL_ID",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"LINHVUC_MOBICAST_ID",
                    headerText:"LINHVUC_MOBICAST_ID",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"STATUS",
                    headerText:"STATUS",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"STATUS_VMS",
                    headerText:"STATUS_VMS",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"STATUS_VTL",
                    headerText:"STATUS_VTL",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"STATUS_GTEL",
                    headerText:"STATUS_GTEL",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"STATUS_VNM",
                    headerText:"STATUS_VNM",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"STATUS_ITEL",
                    headerText:"STATUS_ITEL",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"STATUS_MOBICAST",
                    headerText:"STATUS_MOBICAST",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"CAPDO_ID",
                    headerText:"CAPDO_ID",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"MA_PL",
                    headerText:"MA_PL",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"TEN_PL",
                    headerText:"TEN_PL",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"NGAY_KY_HD",
                    headerText:"NGAY_KY_HD",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"MA_CHUAN",
                    headerText:"MA_CHUAN",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"LOAICTS_ID",
                    headerText:"LOAICTS_ID",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"THUONGHIEU",
                    headerText:"THUONGHIEU",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"NGAY_BD",
                    headerText:"NGAY_BD",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"NGAY_KT",
                    headerText:"NGAY_KT",
                    allowFiltering:true,
                    width:100,
                    visible:true
                },
                {
                    fieldName:"SO_THANG",
                    headerText:"SO_THANG",
                    allowFiltering:true,
                    width:100,
                    visible:true
                }
            ],
            dataSources:[],
            //gviewChuaGan
            columnsChuaGan: columnsChuaGan,
            dataSourcesChuaGan:[],
            //gviewChiTietTB
            columnsCTTB: columnsCTTB,
            dataSourcesCTTB:[],
            Role:2,
            cboKieuDoiSoat_Visible:false,
            dsDanhMuc:[],
            dsKhongBanCheo:["204", "216", "136", "156", "121", "180"],
            dsMaTinh:[],
            dsLoaiHinh:[],
            contextMenuItems:[
                {
                    id: 'btnMarkFinish',
                    text: 'Đánh dấu hoàn thành',
                    target: '.e-content',
                    iconCss: 'icon nc-icon-glyph ui-1_check-circle-08'
                },
                {
                    id: 'btnUpdateStatus',
                    text: 'Đánh dấu bỏ qua',
                    target: '.e-content',
                    items:[],
                },
            ],
            btnGetData_Checked:false,
            lableGetData:'Lấy dữ liệu',
            fillterResult:null,
            currentWorkingID:0,
            sheetAName:'Sheet1',
            file_name:'export.xlsx',
            rowDoiSoatSelected:null,
            btnGhepChiTiet_Enabled:true,
            btnGhepThueBao_Enabled:true,
            editSettings:{ allowEditing: true, mode:'Batch'},
            loaitb_id:0,
            selectIndex:0,
            isNotifiRowDoiSoat:false,
            dsDonViVNP:[],
            layGroupChuaGan:true,
            layGroupChiTiet:true,
            thuebao_id:0,

            //update using socket
            stompClient: null,
            domainlocal: process.env.API + '/web-ipcc',
            socket: null,
            reconnect: false,

        }
    },
    computed:{
        heightTableChuaGan(){
            if(this.kieu_selected==2){
                return '520px'
            }
            return '200px'
        },
        getContextMenuItems(){
            if(this.dataSources.length>0){
                return this.contextMenuItems
            }
            return null
        },
        cptHeader() {
            return 'Bearer ' + JSON.parse(localStorage.getItem('OneBSS-Token')).access_token
        },
        userCode() {
            return this.$root.token.getUserName()
        }
        },
    methods:{
        btnGetData_Click(){
            if(!this.btnGetData_Checked){
                this.$refs.locDuLieuModal.showModal()
            }else{
                this.btnGetData_Checked=false
                this.lableGetData='Lấy dữ liệu'
                //fcd.CancelReadDLDoiSoat(currentWorkingID);
            }
        },
        btnExportExcel_Click(){
            if(this.dataSources.length==0){
                this.$toast.error('Không có dữ liệu để xuất Excel!')
                return
            }
            //console.log(this.dataSources);
            exportExcel(this.dataSources, "Dulieu_Doi_Soat")
            // this.$refs.gridDSDLDoiSoat.excelExport();
        },
        btn_ImportExcel_Click(){
          this.$refs.fileInput.value=null
          document.getElementById("file").click();
        },
        async onChangeFile(event){
          this.file=event.target.files[0]
          await this.readFileExcel()
        },
        checkfile: function () {
          if (this.file == null) {
            this.$toast.error('Chưa chọn file excel')
            return false
          }
          if (this.file.type != "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"){
            this.$toast.error('File upload không đúng định dạng')
            return false
          }
          return true
        },
        cutString(item) {
          return item.split('\r\n')[0];
        },
        excelDateToJSDate(excelDate) {
          // Excel base date is 30th December 1899
          const baseDate = new Date(Date.UTC(1899, 11, 30));
          const millisecondsInDay = 86400000; // 24*60*60*1000

          // Convert Excel date to JavaScript date by adding days (excelDate) to the base date
          const jsDate = new Date(baseDate.getTime() + (excelDate * millisecondsInDay));
          return jsDate;

        },
        formatDate(date) {
          const day = String(date.getUTCDate()).padStart(2, '0');
          const month = String(date.getUTCMonth() + 1).padStart(2, '0'); // getUTCMonth() is zero-based, so add 1
          const year = date.getUTCFullYear();

          let hours = date.getUTCHours();
          const minutes = String(date.getUTCMinutes()).padStart(2, '0');
          const seconds = String(date.getUTCSeconds()).padStart(2, '0');

          const ampm = hours >= 12 ? 'PM' : 'AM';
          hours = hours % 12 || 12; // Convert to 12-hour format

          return `${day}/${month}/${year} ${hours}:${minutes}:${seconds} ${ampm}`;
        },
        isEmptyObject(obj) {
          return JSON.stringify(obj) === '{}'
        },
        cleanInput(input) {
          // Convert input to a string if it's a number
          let str = typeof input === 'number' ? String(input) : input;

          // Trim the string and remove all \r characters
          return str.replace(/\r/g, '').trim();
        },
        async readFileExcel() {
          let danhsach_excel = []
          let cot_excel
          let columns

          try {
            this.loading(true)
            if (this.checkfile()) {
              const reader = new FileReader()
              reader.onload = async (e) => {
                console.log(e);
                const bstr = e.target.result
                const wb = XLSX.read(bstr, { type: "binary" })
                const wsname = wb.SheetNames[0];
                const ws = wb.Sheets[wsname];
                const data = XLSX.utils.sheet_to_json(ws, { header: 1 });

                columns = data[0] // cột

                if(columns.length > 0) {
                  cot_excel = columns.map(this.cutString);

                  for(let i = 1; i < data.length; i++) {
                    let tmp = {};
                    if (data[i].length > 0) {

                      for(let j = 0; j < cot_excel.length; j++) {
                        if (data[i][j] != '' && data[i][j] != null) {
                          let cell = data[i][j];

                          if (cot_excel[j] == 'NGAYTHANG_HD_GANNHAT') {
                            cell = this.excelDateToJSDate(cell);
                            cell = this.formatDate(cell);
                          }

                          tmp[cot_excel[j]] = this.cleanInput(cell) ?? '';
                        }
                      }
                    }

                    if (tmp !== null && !this.isEmptyObject(tmp)) danhsach_excel.push(tmp);

                  }

                }

                if(danhsach_excel.length > 0) {
                  let response = await API.fn_import_doisoat_it(this.axios, {
                    p_json_data: JSON.stringify(danhsach_excel),
                    p_file_name: this.file.name
                  });

                  if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&& response.data.data){
                    let rs = JSON.parse(response.data.data);

                    if (rs.RESULTS.length > 0) {
                      this.data_import.results = rs.RESULTS

                      let tmp_ds = [];

                      for(let i = 0; i < danhsach_excel.length; i++) {
                        danhsach_excel[i].STATUS = rs.RESULTS[i]?.STATUS == 'OK' ? 'Thành công' : 'Thất bại'
                        tmp_ds.push(danhsach_excel[i]);
                      }

                      let tmp_col = [];
                      tmp_col = cot_excel.map((e) => {
                        return {
                          fieldName: e,
                          headerText: e,
                          allowFiltering: true,
                          //width:100,
                          //visible:true
                        }
                      });



                      this.data_import.columns = [...tmp_col, {
                        fieldName: 'STATUS',
                        headerText: 'STATUS',
                        allowFiltering: true,
                        //width:100,
                        //visible:true
                      }];

                      this.data_import.list = tmp_ds;

                      this.$refs.dataImport.showModal()

                      console.log(this.data_import.columns);
                      console.log(this.data_import.list);
                    }

                  } else {

                  }
                } else{
                  return this.$toast.error('Danh sách trống!')
                }
              }

              reader.readAsBinaryString(this.file)

            }
          } catch (error) {
            this.$toast.error('Có lỗi xảy ra khi đọc file')
          } finally {
            this.loading(false)
          }
        },
        btnFlatData_Click(){

        },

        async btnCopyChiTiet_Click(){
            if(this.dataSources.length<=0||this.dataSourcesChuaGan.length<=0){
                return
            }
            let rowIT=this.$refs.gridDSDLDoiSoat.getSelectedRecords()[0]
            let rowGoc= this.$refs.gridDSChuaGan.getSelectedRecords()[0]
            let table=this.dataSourcesCTTB
            if(!rowGoc){
                return
            }

            let resultInsert=await this.sp_them_chitiet_doisoat_it(JSON.stringify({
                ROWID:rowIT.ROWID,
                THUEBAO_ID:rowGoc.THUEBAO_ID,
                LOAITB_ID:rowIT.LOAITB_ID,
                SO_DT_CT:rowIT.SO_DT,
                TEN_TB_CT:rowIT.TEN_KH,
                EMAIL_CT:rowIT.EMAIL,
                DIACHI_LD:rowIT.DIACHI_KH,
                MST_CT:rowIT.MST,
            }))
            if(resultInsert=='ok'){
                this.$toast.success('Thêm tiết thuê bao thành công!')
                //handle lưới
                this.isNotifiRowDoiSoat=true
                this.$refs.gridDSDLDoiSoat.flagSelectedRowIndexes=[this.selectIndex]
                this.btnGetData_Checked=true
                await this.BeginFetchData()
                //refresh get lưới thêm chi tiết
                let data=await this.sp_lay_ds_ct_tb_chua_doisoat_it(this.thuebao_id)
                //upper case key data source
                for(var i = 0; i < data.length; i++){
                    for (var key in data[i]) {
                        if(key.toUpperCase() !== key){
                            data[i][key.toUpperCase()] = data[i][key];
                            delete data[i][key];
                        }
                    }
                }
                this.dataSourcesCTTB=data


            }else{
                this.$toast.error(resultInsert)
            }
        },
        async btnGhepChiTiet_Click(){
            if(this.dataSources.length<=0||this.dataSourcesCTTB.length<=0){
                return
            }
            //
            let rowGhep=this.$refs.gridDSCTTB.getSelectedRecords()[0]
            let row=this.$refs.gridDSDLDoiSoat.getSelectedRecords()[0]
            if(!rowGhep||!row){
                return
            }
            if(row.TRANGTHAI_XL==6||row.TRANGTHAI_XL==7){
                return
            }
            if(row.LOAITB_ID!=rowGhep.LOAITB_ID){
                this.$toast.error('Loại hình không khớp với dữ liệu gốc')
                return
            }
            var clone={}
            Object.assign(clone, row)
            clone.MA_TB_CT_TINH=rowGhep.MA_TB_CT
            clone.THUEBAO_CT_ID=rowGhep.THUEBAO_CT_ID
            //change record ghi chú
            let changes = this.$refs.gridDSDLDoiSoat.getBatchChanges().changedRecords
            if(changes.length>0){
                const change=changes.find(x=>x.ROWID==clone.ROWID)
                if(change){
                    clone.GHICHU=change.GHICHU
                }
            }
            let result=await this.sp_ghep_thuebao_doisoat_it(JSON.stringify({
                ROWID:clone.ROWID,
                LOAITB_ID:rowGhep.LOAITB_ID,
                THUEBAO_CT_ID:clone.THUEBAO_CT_ID,
                GHICHU:clone.GHICHU
            }))
            console.log('sp_ghep_thuebao_doisoat_it', result)
            if(result=='ok'){
                this.$toast.success('Ghép chi tiết thuê bao thành công!')
                //handle lưới
                this.isNotifiRowDoiSoat=true
                this.$refs.gridDSDLDoiSoat.flagSelectedRowIndexes=[this.selectIndex]
                this.btnGetData_Checked=true
                await this.BeginFetchData()
            }else{
                this.$toast.error(result)
            }

        },
        async btnGhepThueBao_Click(){
            if(this.dataSources.length<=0||this.dataSourcesChuaGan.length<=0){
                return
            }
            //
            let rowGhep= this.$refs.gridDSChuaGan.getSelectedRecords()[0]
            let row=this.$refs.gridDSDLDoiSoat.getSelectedRecords()[0]
            if(!rowGhep||!row){
                return
            }
            if(row.TRANGTHAI_XL==6||row.TRANGTHAI_XL==7){
                return
            }
            if(row.LOAITB_ID!=rowGhep.LOAITB_ID){
                this.$toast.error('Loại hình không khớp với dữ liệu gốc')
                return
            }
            var clone={}
            Object.assign(clone, row)
            clone.MA_TB_TINH=rowGhep.MA_TB
            clone.THUEBAO_ID=rowGhep.THUEBAO_ID
            //change record ghi chú
            let changes = this.$refs.gridDSDLDoiSoat.getBatchChanges().changedRecords
            if(changes.length>0){
                const change=changes.find(x=>x.ROWID==clone.ROWID)
                if(change){
                    clone.GHICHU=change.GHICHU
                }
            }
            let result=await this.sp_ghep_thuebao_doisoat_it(JSON.stringify({
                ROWID:clone.ROWID,
                LOAITB_ID:rowGhep.LOAITB_ID,
                THUEBAO_ID:clone.THUEBAO_ID,
                GHICHU:clone.GHICHU
            }))
            console.log('sp_ghep_thuebao_doisoat_it', result)
            if(result=='ok'){
                this.$toast.success('Ghép thuê bao thành công!')
                //handle lưới
                this.isNotifiRowDoiSoat=true
                this.$refs.gridDSDLDoiSoat.flagSelectedRowIndexes=[this.selectIndex]
                this.btnGetData_Checked=true
                await this.BeginFetchData()

            }else{
                this.$toast.error(result)
            }
        },
        async onSelectedRowDsDlDoiSoat(item){
            debugger
            if(this.isNotifiRowDoiSoat){
                this.isNotifiRowDoiSoat=false
                return
            }
            this.rowDoiSoatSelected=item
            if(item.TRANGTHAI_XL!=6&&item.TRANGTHAI_XL!=7){
                this.btnGhepChiTiet_Enabled=true
                this.btnGhepThueBao_Enabled=true
            }
            if(this.layGroupChuaGan){
                //check cũ mới
                if(this.loaitb_id!=item.LOAITB_ID){
                    this.loaitb_id=item.LOAITB_ID
                    let result=await this.sp_lay_ds_thuebao_theo_loaihinhtb(item.LOAITB_ID)
                    //upper case key data source
                    for(var i = 0; i < result.length; i++){
                        for (var key in result[i]) {
                            if(key.toUpperCase() !== key){
                                result[i][key.toUpperCase()] = result[i][key];
                                delete result[i][key];
                            }
                        }
                    }
                    this.dataSourcesChuaGan=result
                }

            }

        },
        onSelectedRowCTTB(item){

        },
        async onSelectedRowChuaGan(item){
            if(this.layGroupChiTiet){
                let thuebao_id=item.THUEBAO_ID
                this.thuebao_id=item.THUEBAO_ID
                //12132112
                let result=await this.sp_lay_ds_ct_tb_chua_doisoat_it(thuebao_id)
                //upper case key data source
                for(var i = 0; i < result.length; i++){
                    for (var key in result[i]) {
                        if(key.toUpperCase() !== key){
                            result[i][key.toUpperCase()] = result[i][key];
                            delete result[i][key];
                        }
                    }
                }
                this.dataSourcesCTTB=result
            }
        },
        async contextMenuClick(args){
            console.log('contextMenuClick: ', args)
            if(args.item.id=='btnMarkFinish') {
              var rows = []
              let row = this.$refs.gridDSDLDoiSoat.getSelectedRecords()[0]
              if (!row.THUEBAO_ID && !row.THUEBAO_CT_ID) {
                this.$toast.error('Thuê bao chưa chuẩn hóa')
                return
              }
              rows.push(row)
              await this.UpdateStatusSelectedRow(6, rows, null)
            } else if(args.item.id=='btnTraTTKD_Click'){
                var rows=[]
                let row=this.$refs.gridDSDLDoiSoat.getSelectedRecords()[0]
                /*if(!row.THUEBAO_ID&&!row.THUEBAO_CT_ID){
                  this.$toast.error('Thuê bao chưa chuẩn hóa')
                  return
                }*/
                rows.push(row)
                await this.UpdateStatusSelectedRow(2, rows, null)
              }else{
                var broadcast=[]
                if(args.item.id=='ALL'){
                    let dsDonViVNP=getListDonViVNP()
                    broadcast=dsDonViVNP.filter(x=>x.DonVi_ID!=71).map(x=>x.Ma_DV)
                }else{
                    broadcast.push(args.item.id)
                }
                // List<DataRow> rows = new List<DataRow>();
                var rows=[]
                let row=this.$refs.gridDSDLDoiSoat.getSelectedRecords()[0]
                rows.push(row)

                let changes = this.$refs.gridDSDLDoiSoat.getBatchChanges().changedRecords;
                if(changes.length>0){
                    const change=changes.find(x=>x.ROWID==row.ROWID);
                    if(change){
                        row.GHICHU=change.GHICHU;
                    }
                }

                var msg='Bạn có chắc chắn muốn đánh dấu ['
                if(this.Role==1){
                    msg+='Không cần xử lý] ?'
                }else{
                    msg+='Không nhận khách hàng] ?'
                }
                let result = await this.confirm(msg,'Thông báo')
                if(result==0) return
                if(this.Role==1){
                    await this.UpdateStatusSelectedRow(8, rows, broadcast)
                }else{
                    await this.UpdateStatusSelectedRow(7, rows, broadcast)
                }
            }
        },
        async confirm(message, title){
            try{
                let result = await this.$confirm(message,title,{
                    confirmButtonText: 'Đồng ý',
                    cancelButtonText: 'Hủy'
                })
                return 1
            }catch(e){
                return 0
            }
        },
        async UpdateStatusSelectedRow(status, rows, broadcast){
            if(rows.length==0){
                return
            }
            for(let i=0; i<rows.length;i++){
                if(rows[i].TRANGTHAI_XL!=rows[0].TRANGTHAI_XL){
                    this.$toast.error('Vui lòng chọn các trường hợp có chung trạng thái xử lý')
                    return
                }
            }
            let result=await this.sp_capnhat_trangthai_doisoat_it({
                p_trangthai_xl:status,
                p_js_ds_data:JSON.stringify(rows.map(x=>{
                    return {
                        ROWID:x.ROWID,
                        MA_TB:x.MA_TB,
                        THUEBAO_ID:x.THUEBAO_ID,
                        THUEBAO_CT_ID:x.THUEBAO_CT_ID,
                        TRANGTHAI_XL:x.TRANGTHAI_XL,
                        LOAITB_ID:x.LOAITB_ID,
                        FILE_ID:x.FILE_ID,
                        ORIGINS:x.ORIGINS,
                        THUTU:x.THUTU,
                        GHICHU:x.GHICHU
                    }
                })),
                p_js_ds_tinh:broadcast?JSON.stringify(broadcast):''
            })
            if(result=='ok'){
                this.$toast.success('Thành công')
                //refresh lưới
                var dataClone=this.dataSources
                for(let k=0;k<rows.length;k++){
                    const index=dataClone.findIndex(x=>x.ROWID==rows[k].ROWID)
                    if(index>-1){
                        dataClone[index].TRANGTHAI_XL=status
                        if(!dataClone[index].TrangThai){
                            continue
                        }
                        if(dataClone[index].TRANGTHAI_XL==1){
                            dataClone[index].TrangThai='Cần xử lý'
                        }else if(dataClone[index].TRANGTHAI_XL==2){
                            dataClone[index].TrangThai=dataClone[index].MA_TB?'Sai mã thuê bao':'Thiếu mã thuê bao'
                        }else if(dataClone[index].TRANGTHAI_XL==3){
                            dataClone[index].TrangThai='Chờ nghiệm thu'
                        }else if(dataClone[index].TRANGTHAI_XL==6){
                            dataClone[index].TrangThai='Đã chuẩn hóa'
                        }else if(dataClone[index].TRANGTHAI_XL==7){
                            if(dataClone[index].ORIGINS!=1){
                                dataClone[index].TrangThai='Rà soát bán chéo'
                            }else{
                                dataClone[index].TrangThai='Không nhận khách hàng'
                            }
                        }else{
                             dataClone[index].TrangThai=''
                        }
                        if(dataClone[index].Origins!=1){
                            dataClone[index].Cross='Rà soát bán chéo'
                        }
                    }
                }
                this.isNotifiRowDoiSoat=true
                this.$refs.gridDSDLDoiSoat.flagSelectedRowIndexes=[this.selectIndex]
                this.dataSources=dataClone.concat()


                // this.btnGetData_Checked=true
                // await this.BeginFetchData()

            }else{
                this.$toast.error(result)
            }
        },
        changeKieuDoiSoat(){
            this.loaitb_id=0
            if(this.kieu_selected==1){
                this.layGroupChuaGan=false
                this.layGroupChiTiet=false
            }else if(this.kieu_selected==2){
                //btnGhepThueBao.Visible = true;
                this.layGroupChuaGan=true
                this.layGroupChiTiet=false
            }else if(this.kieu_selected==3){
                this.layGroupChuaGan=true
                this.layGroupChiTiet=true
                //btnGhepThueBao.Visible = btnGhepChiTiet.Visible = true;
            }
            if(this.rowDoiSoatSelected){
                this.onSelectedRowDsDlDoiSoat(this.rowDoiSoatSelected)
            }
        },
        cellClick(args){
            //btnChonThueBaoTinh
            if(args.column.field=='MA_TB_TINH'){
                //this.$refs.searchAccountModal.showModal()
            }
        },
        openSearchContract(item){
            console.log('openSearchContract', item)
            this.$refs.searchAccountModal.showModal()
            setTimeout(()=>{
                this.$refs.searchAccountModal.ThemDieuKienTraCuu(12, item.TEN_KH)
            }, 500)
        },
        rowSelected(args){
            this.selectIndex=args.rowIndex
            console.log('selectIndex', this.selectIndex)
        },
        async formCloseSearchAccount(data){
            console.log('formCloseSearchAccount', data)
            if(!data){
                return
            }
            if(!data.ma_tb){
                return
            }
            if(!data.thuebao||!data.thuebao.thuebao_id){
                return
            }
            let row=this.$refs.gridDSDLDoiSoat.getSelectedRecords()[0]
            if(Number(data.thuebao.loaitb_id)!=row.LOAITB_ID&&(Number(data.thuebao.loaitb_id)!=139||row.LOAITB_ID!=39)){
                var msg = "Loại hình không khớp với dữ liệu gốc"
                if(Number(data.thuebao.loaitb_id)==0){
                    this.$toast.error('Bạn chưa chọn thuê bao trên lưới')
                }else{
                    this.$toast.error('Loại hình của mã thuê bao ['+data.ma_tb+'] là ['+data.thuebao.loaitb_id+'] không khớp với ['+row.LOAITB_ID+'] của dữ liệu')
                }
                return
            }
            var clone={}
            Object.assign(clone, row)
            clone.MA_TB_TINH=data.ma_tb
            clone.THUEBAO_ID=data.thuebao.thuebao_id
            //change record ghi chú
            let changes = this.$refs.gridDSDLDoiSoat.getBatchChanges().changedRecords
            if(changes.length>0){
                const change=changes.find(x=>x.ROWID==clone.ROWID)
                if(change){
                    clone.GHICHU = change.GHICHU
                }
            }
            let result= await this.sp_ghep_thuebao_doisoat_it(JSON.stringify({
                ROWID:clone.ROWID,
                LOAITB_ID:clone.LOAITB_ID,
                THUEBAO_ID:clone.THUEBAO_ID,
                GHICHU:clone.GHICHU
            }))
            console.log('sp_ghep_thuebao_doisoat_it', result)
            if(result=='ok'){
                this.$toast.success('Ghép thuê bao thành công!')
                //handle lưới
                this.isNotifiRowDoiSoat=true
                this.$refs.gridDSDLDoiSoat.flagSelectedRowIndexes=[this.selectIndex]
                this.btnGetData_Checked=true
                this.BeginFetchData()
            }else{
                this.$toast.error(result)
            }
        },
       async acceptLocDuLieu(data){
            console.log('acceptLocDuLieu: ', data)
            this.rowDoiSoatSelected=null
            this.loaitb_id=0
            this.selectIndex=0
            this.dataSources=[]
            this.dataSourcesChuaGan=[]
            this.dataSourcesCTTB=[]
            this.fillterResult=data
            if(!this.btnGetData_Checked){
                this.btnGetData_Checked=true
                this.lableGetData='Đang xử lý dữ liệu...'
                this.dataSources=[]
                let cols = ["SO_HOPDONG", "ID_PHIEU", "ID_THIETBI", "MA_CRM", "GOI_DV", "NGAYYEUCAU", "AM_AS", "TINHTRANGPHIEU", "NOIDUNGPHIEU", "IP", "KHONGGIAN", "SERIAL", "BANGTHONG_TRONGNUOC", "BANGTHONG_QUOCTE", "CONGSUAT", "IDC", "TANG", "RACK", "VITRI", "TENTHIETBI", "NGAYHOANTHANH", "NGUOILIENHE", "DIENTHOAILIENHE", "NGAYHOANTHANH", "NHOM_THIETBI", "SOLUONG_THIETBI", "DONVI_QUANLY_HD", "DONVI_BANHANG", "DONVI_YEUCAU"]

                if(data.ds_loaihinh.length>0&&data.ds_loaihinh.findIndex( x=>x.LOAITB_ID == 39)>-1){
                    for(let i=0;i<cols.length;i++){
                        const index=this.columns.findIndex(x=>x.fieldName==cols[i])
                        if(index>-1){
                            this.columns[index].visible=true
                        }
                    }
                    this.columns=this.columns.concat([])
                    console.log('columns', this.columns)
                }
                await  this.BeginFetchData()
            }else{
                this.btnGetData_Checked=false
                this.lableGetData='Lấy dữ liệu'
                //fcd.CancelReadDLDoiSoat(currentWorkingID);
            }
        },
        async BeginFetchData(){

            let dsTinhChon = []
            if(this.Role  == 2){
                dsTinhChon.push(this.$root.token.getPhanVungID())
            }

            var file_id = 0
            var bancheo = 0
            var trangthai = 0

            var loaihinh ='-1'
            if(this.fillterResult.checkTimTheoFile){
                file_id=this.fillterResult.file_id
                dsTinhChon = dsTinhChon.concat(this.dsDonViVNP.map(x=>x.DonVi_ID))
            }else{
                loaihinh = this.fillterResult.ds_loaihinh.map(x=>x.LOAITB_ID.toString()).join(',')
                trangthai = this.fillterResult.trangthai
                bancheo = this.fillterResult.bancheo;
                dsTinhChon = dsTinhChon.concat(this.fillterResult.ds_tinhchon.map(x=>x.DonVi_ID))
            }
            this.loading(true)
            if(trangthai == 0){
                await this.handleExportExcelFile( {
                    p_dsloaitb_id: loaihinh,
                    p_trangthai_xl: 0,
                    p_bancheo: bancheo,
                    p_phanvung_id: dsTinhChon.join(','),
                })
                this.endFetchData()
                return;
            }
            apiHelper.handleGetDataDoiSoatWithBatch( this.axios, dsTinhChon, {
                p_dsloaitb_id: loaihinh,
                p_trangthai_xl: trangthai,
                p_bancheo: bancheo
            }).then(results=>{
                this.dataSources = results.map((item) => { return this.getTrangThai(item)});
                if(!this.dataSources.length)  this.$toast.warning('Không tìm thấy thuê bao thoả mãn điều kiện tìm kiếm!');
                this.endFetchData()
            }).catch(err=>{
                this.endFetchData()
                console.log(err)
            })
        },

        endFetchData(){
            this.loading(false);
            this.btnGetData_Checked=false
            this.lableGetData='Lấy dữ liệu'
        },
        columnTemplateMaTBTinh(parent) {
            return function() {
                return {
                    template: {
                        template: `
                          <div class="info-row">
                            <div class="value">
                                <div class="input-icon-right">
                                    <span class="name">{{data.MA_TB_TINH}}</span>
                                    <button v-if="isShowMore" class="btn" style="border-color: #0176FF !important;padding: 1px 5px !important;" @click="openSearchContract">
                                        <span class="-ap icon-more_horiz" style="color: #0176FF !important;"></span>
                                    </button>
                                </div>
                            </div>
                          </div>
                        `,
                        data() {
                            return {
                              data: {},
                              parent:parent
                            }
                        },
                        computed:{
                            isShowMore(){
                                if(this.parent.rowDoiSoatSelected){
                                    var left=this.parent.rowDoiSoatSelected.ROWID.toString()
                                    if(this.parent.rowDoiSoatSelected.MA_TINH){
                                        left+=this.parent.rowDoiSoatSelected.MA_TINH
                                    }
                                    var right=this.data.ROWID.toString()
                                    if(this.data.MA_TINH){
                                        right+=this.data.MA_TINH
                                    }
                                    if(left==right){
                                        return true
                                    }
                                }
                                return false
                            }
                        },
                        methods:{
                            openSearchContract(){
                                this.parent.openSearchContract(this.data)
                            }
                        }
                    }
                }
            }
        },
        async fn_lay_danh_muc_doisoat_it(ds_danhmuc_id){
            try{

                let response    = await API.fn_lay_danh_muc_doisoat_it(this.axios, ds_danhmuc_id)

                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return JSON.parse(response.data.data)
                }else {
                    return null
                }
            }catch(e){
                return null
            }
        },

        async sp_lay_ds_thuebao_theo_loaihinhtb(loaitb_id){
            try{

                let response = await API.sp_lay_ds_thuebao_theo_loaihinhtb(this.axios, loaitb_id)

                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else {
                    return []
                }
            }catch(e){
                return []
            }
        },
        async sp_lay_ds_ct_tb_chua_doisoat_it(thuebao_id){
            try{

                let response=await API.sp_lay_ds_ct_tb_chua_doisoat_it(this.axios, thuebao_id)

                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else {
                    return []
                }
            }catch(e){

                return []
            }
        },
        async sp_ghep_thuebao_doisoat_it(js_data){
            try{
                this.loading(true)
                let response=await API.sp_ghep_thuebao_doisoat_it(this.axios, js_data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data){
                    return response.data.data
                }else {
                    if(response && response.data &&response.data.message){
                        return response.data.message
                    }else{
                        return 'Đã xảy ra lỗi'
                    }
                }
            }catch(error){
                this.loading(false)
                if(error.data&&error.data.message){
                    return error.data.message
                }else if(error.response&&error.response.data&&error.response.data.message){
                    return error.response.data.message
                }else{
                    return 'Đã xảy ra lỗi'
                }
            }
        },
        async sp_them_chitiet_doisoat_it(js_data){
            try{
                this.loading(true)
                let response=await API.sp_them_chitiet_doisoat_it(this.axios, js_data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data){
                    return response.data.data
                }else {
                    if(response && response.data &&response.data.message){
                        return response.data.message
                    }else{
                        return 'Đã xảy ra lỗi'
                    }
                }
            }catch(error){
                this.loading(false)
                if(error.data&&error.data.message){
                    return error.data.message
                }else if(error.response&&error.response.data&&error.response.data.message){
                    return error.response.data.message
                }else{
                    return 'Đã xảy ra lỗi'
                }
            }
        },
        async sp_capnhat_trangthai_doisoat_it(data){
            try{
                this.loading(true)
                let response=await API.sp_capnhat_trangthai_doisoat_it(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data){
                    return response.data.data
                }else {
                    if(response && response.data &&response.data.message){
                        return response.data.message
                    }else{
                        return 'Đã xảy ra lỗi'
                    }
                }
            }catch(error){
                this.loading(false)
                if(error.data&&error.data.message){
                    return error.data.message
                }else if(error.response&&error.response.data&&error.response.data.message){
                    return error.response.data.message
                }else{
                    return 'Đã xảy ra lỗi'
                }
            }
        },
        async initDuLieu(){
            this.title='Đối soát dữ liệu thuê bao'
            this.dsLoaiHinh=[]
            this.dsMaTinh=[]
            this.dsDanhMuc=await this.fn_lay_danh_muc_doisoat_it('1, 2, 3, 4, 5, 17, 151, 150, 152')
            let cols=["SO_HOPDONG", "ID_PHIEU", "ID_THIETBI", "MA_CRM", "GOI_DV", "NGAYYEUCAU", "AM_AS", "TINHTRANGPHIEU", "NOIDUNGPHIEU", "IP", "KHONGGIAN", "SERIAL", "BANGTHONG_TRONGNUOC", "BANGTHONG_QUOCTE", "CONGSUAT", "IDC", "TANG", "RACK", "VITRI", "TENTHIETBI", "NGAYHOANTHANH", "NGUOILIENHE", "DIENTHOAILIENHE", "NGAYHOANTHANH", "NHOM_THIETBI", "SOLUONG_THIETBI", "DONVI_QUANLY_HD", "DONVI_BANHANG", "DONVI_YEUCAU"]
            for(let i=0;i<cols.length;i++){
                const index=this.columns.findIndex(x=>x.fieldName==cols[i])
                if(index>-1){
                    this.columns[index].visible=false
                }
            }
            if(this.dsDanhMuc){
                let dsLH=this.dsDanhMuc.find(x=>x.ID==1)
                this.dsLoaiHinh=dsLH?dsLH.DATA:[]
            }
            this.dsMaTinh = getListDonViVNP().map(x=>x.Ma_DV)
            // console.log('dsMaTinh', this.dsMaTinh)

            this.dsDonViVNP=getListDonViVNP().filter(x=>x.DonVi_ID!=71).map(x=>{
                if(x.Vung==1){
                    x.TenVung='Miền Bắc'
                }else if(x.Vung==2){
                    x.TenVung='Miền Trung'
                }else if(x.Vung==3){
                    x.TenVung='Miền Nam'
                }else if(x.Vung==0){
                    x.TenVung='KHDN'
                }
                x.TenHT = x.Ma_DV+' - '+x.Ten_DV
                return x
            })

            if(this.$root.token.getPhanVungID()== 0 || this.$root.token.getPhanVungID()== 97){
                this.Role=1
                this.kieu_selected=3
            }else{
                this.Role=2
            }
            if(this.$route.query.Role){
                if(this.$route.query.Role==1){
                    this.Role=1
                }else{
                    this.Role=2
                }
            }
            if(this.Role==2){
                //  layout bên popup LocDuLieuModal
                // layGroupDonVi.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Never;
                const index=this.columns.findIndex(x=>x.fieldName=='MA_TINH')
                if(index>-1){
                    this.columns[index].visible=false
                }
                this.cboKieuDoiSoat_Visible=true
                this.changeKieuDoiSoat()
                //Tương úng control checkFile bên modal LocDuLieuModal
                // layItemChkExcel.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Never;
                const indexUS=this.contextMenuItems.findIndex(x=>x.id=='btnUpdateStatus')
                this.contextMenuItems[indexUS].text='Không nhận khách hàng'
                this.contextMenuItems[indexUS].items=[]
                this.contextMenuItems[indexUS].items.push({
                    id: "NONE",
                    text: 'Không chuyển tỉnh khác',
                })
                let dsDonViVNP=getListDonViVNP()
                let maTinh=this.$root.token.getMaTinh()
                for(let i=0;i<dsDonViVNP.length;i++){
                    if(dsDonViVNP[i].DonVi_ID!=71&&dsDonViVNP!=maTinh){
                        this.contextMenuItems[indexUS].items.push({
                            id: dsDonViVNP[i].Ma_DV,
                            text: 'Chuyển '+dsDonViVNP[i].Ma_DV+' - '+dsDonViVNP[i].Ten_DV
                        })
                    }
                }
                this.contextMenuItems[indexUS].items.push({
                    id: "ALL",
                    text: 'Chuyển tất cả các tỉnh',
                })
                this.contextMenuItems=[].concat(this.contextMenuItems)
                console.log('contextMenuItems', this.contextMenuItems)
            }

            if(this.Role==1) {
              this.contextMenuItems.push({
                id: 'btnTraTTKD_Click',
                text: 'Chuyển trả TTKD',
                target: '.e-content',
                iconCss: 'icon nc-icon-glyph arrows-1_back-80'
              });
            }
        },
        getTrangThai(item){

            let trangthai = '';

            switch (item.TRANGTHAI_XL) {
                case 1:
                    trangthai = 'Cần xử lý';
                    break;
                case 2:
                    trangthai = item.MA_TB ? 'Sai mã thuê bao' : 'Thiếu mã thuê bao';
                    break;
                case 3:
                    trangthai = 'Chờ nghiệm thu';
                    break;
                case 6:
                    trangthai = 'Đã chuẩn hóa';
                    break;
                case 7:
                    if (item.ORIGINS && item.ORIGINS == 1) {
                        trangthai = 'Rà soát bán chéo';
                    } else {
                        trangthai = 'Không nhận khách hàng';
                    }
                    break;
                default:
                    trangthai = '';
                    break;
            }

            return Object.assign(item, {
                MA_TINH: DictDonViVNPT[item.PHANVUNG_ID],
                TrangThai:trangthai,
                Cross:item?.Origins==1?'Rà soát bán chéo':''
            })
        },
        async handleExportExcelFile(params){
            try {
            const response = await API.exportExcelDataDoiSoatIT(this.axios,params)
            const status = this.processExportExcelFile("DU_LIEU_DOI_SOAT_IT", response)
            if(status)
                this.$toast.success("Xuất file thành công")
            else
                this.$toast.error("Xuất file không thành công")

            } catch (error) {
              this.$toast.error("Xuất file không thành công")
              console.log(error);
            }

        },
        processExportExcelFile(namefile, response){

            if (response.headers && response.headers['content-type'] == "text/plain") {
            const reader = new FileReader();
            reader.addEventListener('loadend', (e) => {
              const text = e.srcElement.result;
              this.$toast.error(text)
              return false
            });
            let blb = new Blob([response.data], {type: 'text/plain'})
            reader.readAsText(blb);

          }

          if(response.data && response.data.size == 0) {
            this.$toast.error("Không có dữ liệu. Vui lòng kiểm tra lại")
            return false
          }
          const link = document.createElement('a')
          link.href = window.URL.createObjectURL(new Blob([response.data]))
          link.setAttribute('download', (namefile + ".xlsx"))
          document.body.appendChild(link)
          link.click()
          return  true
        },

    },
    mounted(){
        setTimeout(()=>{
            this.initDuLieu()
        }, 500)
    }
}
</script>
<style>
#doisoatdulieu_it .thong-ke:hover {
  background-color: rgba(1, 118, 255, 0.1);
  font-weight: 600
}
#doisoatdulieu_it .ui-thong-ke {
  position: absolute;
  margin-top: 8px;
  width: 200px;
  padding-left: 0rem;
  list-style: none;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid rgba(0, 0, 0, 0.15);
}
.e-contextmenu-wrapper ul.e-ul{
    height: 50% !important;
    overflow-y: scroll !important;
}
#doisoatdulieu_it .disabled{
    pointer-events:none;
    opacity:0.7;
}
#doisoatdulieu_it .disabled_color{
  color: gray !important;
}
</style>
