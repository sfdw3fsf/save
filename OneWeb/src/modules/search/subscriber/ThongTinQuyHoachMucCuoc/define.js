import Vue from 'vue'
import moment from "moment"
import { Group, Page, Sort, Aggregate, ExcelExport } from '@syncfusion/ej2-vue-grids'
export default {    
    actions: [
        {
            id: 'search',
            name: 'Tìm kiếm',
            active: true,
            icon_class: 'one-search',
            visible: true
        },
        {
            id: 'taohd',
            name: 'Tạo hợp đồng',
            active: true,
            icon_class: 'one-file-plus',
            visible: true
        },
        {
            id: 'thanhtoan',
            name: 'Thanh toán',
            active: true,
            icon_class: 'one-file-plus',
            visible: true
        },
        {
            id: 'dongbo',
            name: 'Đồng bộ',
            active: true,
            icon_class: 'one-file-plus',
            visible: true
        },
        {
            id: 'hoanthien',
            name: 'Hoàn thiện',
            active: true,
            icon_class: 'one-file-plus',
            visible: true
        },
        {
            id: 'xoahd',
            name: 'Xóa HĐ',
            active: true,
            icon_class: 'one-file-plus',
            visible: true
        }
    ],   
    filterbox: {
        ctv: {
            value: '',
            ischecked: false,
            ma_ctv: '',
            ctv_id: ''
        },
        nguoigt: {
            value: '',
            ischecked: false,
            nguoigt_id: '',
        }
    },      
    ctv_id:0,   
    khachhang_id:0,
    thuebao_id:0,
    ma_tb:'',
    isDoiTD:false,
    dsThamSoMacDinh:[],
    //TINHCUOC_NGAY:0,
    tt_tb:{
        "tengoi":"",
        "giagoi":"",
        "khuyenmai": "",
        "muccuoc": "",
        "ma_tb": "",
        "ten_tb": "",
        "tocdo": "",
        "mucuoctb_id": 0,
        "so_dt": "",
        "tt_tratruoc_hientai": "",
        "cuoc_tp":"",
        "gia_fiber":"",
        "tien_thanhtoan":"",
        "is_tb_le":1,
        "is_khdn":0,
        "is_td_chuan":0
    }, 
    tt_kh:{
        "ma_gd":"",
        "ngay_yc":"",
        "ma_kh":"",
        "ten_kh":"",
        "diachi_kh":"",
        "ghichu":"",
    },    
    radio_kieu:0,
    txt_tracuu_tu:0,
    txt_tracuu_den:0,     
    groupSettings: {
        showDropArea: false,
        showToggleButton: false,
        showUngroupButton: false,
        columns: ['sl_thanhphan']
    },   
    ds_tien_khoanmuc:[],
    ds_tien_khoanmuc_km:[],
    ds_tien_khoanmuc_dadv:[],
    selected_item_chithamgia_goi:null,
    selected_item_chithamgia_goi_tratruoc:null,
    selected_item_tocdo_muccuoc:null,
    selected_item_tocdo_muccuoc_tratruoc:null,
    selected_item_mytv:null,
    selected_item_mytv_tratruoc:null,
    arr_selected_item_tratruoc:[],
    arr_goi:[],
    arr_doitocdo:[],
    gv_tb_goi: {        
        tableCollumns: [
            {
                fieldName: "loaihinh_tb",
                headerText: "Loại hình TB",
                allowFiltering: true,
                allowSorting: false,
                width: 120,                
            },
            {
                fieldName: "ma_tb",
                headerText: "Mã TB",
                allowFiltering: true,
                allowSorting: false,
                width: 100,
            },
            {
                fieldName: "ten_goi",
                headerText: "Gói đa DV",
                allowFiltering: true,
                allowSorting: false,
                width: 100,
            },
            {
                fieldName: "trangbi",
                headerText: "Trang bị",
                allowFiltering: true,
                allowSorting: false,
                width: 100,                
            },
            {
                fieldName: "cuoc",
                headerText: "Tiền gói",
                allowFiltering: true,
                allowSorting: false,
                width: 100,
                textAlign:'Right',
                type: "Number", 
                format: "N0"
            },
            {
                fieldName: "thongtin_tt",
                headerText: "Thông tin trả trước",
                allowFiltering: true,
                allowSorting: false,
                width: 120,
            },            
        ],
        tableData:[],
    },
    gridTienHopDong: {
        header: [
          { fieldName: 'ma_kmtt', headerText: 'Mã KMTT', allowfiltering: true },
          { fieldName: 'ten_kmtt', headerText: 'Tên KMTT', allowfiltering: true },
          { fieldName: 'tyle_vat', headerText: 'Tỷ lệ VAT', allowfiltering: true },
          { fieldName: 'tien', headerText: 'Tiền', allowfiltering: true },
          { fieldName: 'vat', headerText: 'VAT', allowfiltering: true }
        ],
        list: []
    },
    listbox:{
        ds_thuebao: {
            list: [],
            header: [
            { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowfiltering: true },
            { fieldName: 'matb_tn', headerText: 'Mã TN', allowfiltering: true },                  
            { fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowfiltering: true },
            { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowfiltering: true },
            { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowfiltering: true },
            { fieldName: 'ten_kieuld', headerText: 'Kiểu lắp đặt', allowfiltering: true },
            { fieldName: 'trangthai_hd', headerText: 'Trạng thái HĐ', allowfiltering: true },            
            { fieldName: 'tocdo', headerText: 'Tốc độ', allowfiltering: true },
            { fieldName: 'trangthai_kh', headerText: 'Trạng thái KH', allowfiltering: true },
            { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowfiltering: true },            
            { fieldName: 'hinhthuc', headerText: 'HT chuyển đổi', allowfiltering: true },            
            { fieldName: 'ten_dt', headerText: 'Đối tượng', allowfiltering: true },            
            { fieldName: 'ghichu', headerText: 'Ghi chú', allowfiltering: true },
            { fieldName: 'ngay_thoai', headerText: 'Ngày thoái', allowfiltering: true },
            { fieldName: 'ngay_kh', headerText: 'Ngày kích hoạt', allowfiltering: true },
            { fieldName: 'ngay_ht', headerText: 'Ngày HT', allowfiltering: true },            
            { fieldName: 'congnghe', headerText: 'Công nghệ', allowfiltering: true },            
            ],
            value: {},
            isEnabled: false
        }
    },    
    ds_hdtb:[],
    hdkh_id:0,
    hdtb_id:0,
    ma_gd:'',
    paymentloaded: false,
    is_td_chuan:0,
    targetTop: '#app .main-wrapper',
    position: { X: 'center', Y: 'top' },
    //tham số tab thông tin hợp đồng
    dtpNgayYeuCau: new Date(),
    txtMaHD: '',
    dtpNgayLapHD: new Date(),
    cboDichVuVT: {
        value: 0, //dichvuvt_id
        data: [],
        enabled: false
    },
    cboLoaihinhTB: {
        value: 0, //loaitb_id: 0,
        data: [],
        enabled: false
    },
    cboKieuLD: {
        value: 0,
        data: null,
        enabled: false
    },
    chkTTTCVisa: {
        checked: false,
        enabled: false
    },
    chkTucThi: {
        checked: false,
        enabled: false
    },
    chkThoiHan: {
        checked: false,
        enabled: false
    },
    btnThongTinTC: {
        enabled: true
    },
    txtTT_Cuoc: {
        enabled: false,
        value: ''
    },
    txtCuoc_TBI: {
        enabled: false,
        value: 0
    },
    txtMaKH: '',
    txtNgaySN: '',
    txtTenKH: '',
    txtDiaChiKH: '',
    txtTTCuocVisa: '',
    txtGhiChu: '',
    txtSoThang: '',
    dateconfig: {
        altFormat: 'd/m/Y',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput: true
    },
    txtCuocSD: {
        enabled: false,
        value: 0
    },
    txtTongTienHD:0,
    dsHdtb:[],
    groupSettings1: {
        showDropArea: false,
        showToggleButton: false,
        showUngroupButton: false,
        columns: ['tyle_vat']
    },
    selectionSettings: {checkboxOnly: true, type: 'Multiple'},    
    gv_tocdo_muccuoc: {
        tableCollumns: [
            {
                fieldName: "thuonghieu",
                headerText: "Tên tốc độ",
                allowFiltering: true,
                allowSorting: false,
                width: 120,                
            },            
            {
                fieldName: "muccuoc",
                headerText: "Mức cước",
                allowFiltering: true,
                allowSorting: false,
                width: 100,
            },
            {
                fieldName: "tocdo",
                headerText: "Tốc độ",
                allowFiltering: true,
                allowSorting: false,
                width: 120,                
            }, 
            {
                fieldName: "ss_tienthang",
                headerText: "So sánh tiền tháng",
                allowFiltering: true,
                allowSorting: false,
                width: 100,   
                type: "Number", 
                format: "N0",                       
            },
            {
                fieldName: "ma_pa",
                headerText: "Mã PA",
                allowFiltering: true,
                allowSorting: false,
                width: 150,
            },
            {
                fieldName: "pa_tuvan",
                headerText: "Phương án",
                allowFiltering: true,
                allowSorting: false,
                width: 150,
            },
            // {
            //     fieldName: "ten_goi",
            //     headerText: "Tên gói đa dv",
            //     allowFiltering: true,
            //     allowSorting: false,
            //     width: 100,                
            // },
            // {
            //     fieldName: "tien_goi",
            //     headerText: "Tiền gói",
            //     allowFiltering: true,
            //     allowSorting: false,
            //     width: 100,
            //     textAlign:'Right',
            //     type: "Number", 
            //     format: "N0"
            // },
            // {
            //     fieldName: "tien_fiber",
            //     headerText: "Tiền thành phần fiber",
            //     allowFiltering: true,
            //     allowSorting: false,
            //     width: 120,
            //     type: "Number", 
            //     format: "N0"
            // }, 
            // {
            //     fieldName: "ss_tiengoi",
            //     headerText: "So sánh tiền gói",
            //     allowFiltering: true,
            //     allowSorting: false,
            //     width: 100,   
            //     type: "Number", 
            //     format: "N0",                       
            // },  
            // {
            //     fieldName: "soluong_dv",
            //     headerText: "Số lượng dịch vụ",
            //     allowFiltering: true,
            //     allowSorting: false,
            //     width: 120,
            // },   
            // {
            //     fieldName: "ten_kmtt",
            //     headerText: "Tên km trả trước",
            //     allowFiltering: true,
            //     allowSorting: false,
            //     width: 120,
            // },   
            // {
            //     fieldName: "sothang",
            //     headerText: "Số tháng",
            //     allowFiltering: true,
            //     allowSorting: false,
            //     width: 120,
            // },   
            // {
            //     fieldName: "tien_tt",
            //     headerText: "Tiền trả trước",
            //     allowFiltering: true,
            //     allowSorting: false,
            //     width: 120,
            //     type: "Number", 
            //     format: "N0"
            // },       
        ],
        tableData:[],
    },
    gv_goi_dadv: {
        tableCollumns: [
            {
                fieldName: "ten_goi",
                headerText: "Tên gói",
                allowFiltering: true,
                allowSorting: false,
                width: 120, 
                isGroupedColumn:true               
            },
            {
                fieldName: "sl_thanhphan",
                headerText: "Số lượng thành phần gói",
                allowFiltering: true,
                allowSorting: false,
                width: 100,                          
            },
            {
                fieldName: "loaihinh_tb",
                headerText: "Loại hình",
                allowFiltering: true,
                allowSorting: false,
                width: 100,                          
            },
            {
                fieldName: "tocdo",
                headerText: "Tốc độ",
                allowFiltering: true,
                allowSorting: false,
                width: 100,                          
            },            
            {
                fieldName: "tien_goi",
                headerText: "Tiền gói",
                allowFiltering: true,
                allowSorting: false,
                width: 100,
                type: "Number", 
                format: "N0",
            },
            {
                fieldName: "sosanh_tien",
                headerText: "So sánh tiền với gói hiện tại",
                allowFiltering: true,
                allowSorting: false,
                width: 150,  
                type: "Number", 
                format: "N0",       
                cssClass: "tien-tra"     
            },  
            {
                fieldName: "ma_pa",
                headerText: "Mã PA",
                allowFiltering: true,
                allowSorting: false,
                width: 150,
            },
            {
                fieldName: "pa_tuvan",
                headerText: "Phương án",
                allowFiltering: true,
                allowSorting: false,
                width: 150,
            },                            
        ],
        tableData:[],
    },
    gv_mytv: {
        tableCollumns: [
            {
                fieldName: "ten_goi",
                headerText: "Tên gói",
                allowFiltering: true,
                allowSorting: false,
                width: 120, 
                isGroupedColumn:true               
            },
            {
                fieldName: "sl_thanhphan",
                headerText: "Số lượng",
                allowFiltering: true,
                allowSorting: false,
                width: 100,                          
            },
            {
                fieldName: "loaihinh_tb",
                headerText: "Loại hình",
                allowFiltering: true,
                allowSorting: false,
                width: 100,                          
            },
            {
                fieldName: "tocdo",
                headerText: "Tốc độ mới",
                allowFiltering: true,
                allowSorting: false,
                width: 100,                          
            },            
            {
                fieldName: "muccuoc_tb",
                headerText: "Mức cước",
                allowFiltering: true,
                allowSorting: false,
                width: 100,
                type: "Number", 
                format: "N0",
            },
            {
                fieldName: "goi_dadv",
                headerText: "Gói đa dịch vụ",
                allowFiltering: true,
                allowSorting: false,
                width: 150,                          
            }, 
                                 
        ],
        tableData:[],
    },

    ///
    gv_tratruoc_km: {
        tableCollumns: [
            {
                fieldName: "ten_km",
                headerText: "Khuyến mãi",
                allowFiltering: true,
                allowSorting: false,
                width: 150,     
                isGroupedColumn:true           
            },
            {
                fieldName: "ten_ctkm",
                headerText: "Chi tiết km trả trước",
                allowFiltering: true,
                allowSorting: false,
                width: 150        
            },
            {
                fieldName: "sothang",
                headerText: "Số tháng",
                allowFiltering: true,
                allowSorting: false,
                width: 150,
            },
            {
                fieldName: "tien_tratruoc",
                headerText: "Tiền trả trước",
                allowFiltering: true,
                allowSorting: false,
                width: 100,
                type: "Number", 
                format: "N0"
            },
            // {
            //     fieldName: "sothang_tang",
            //     headerText: "Số tháng tặng",
            //     allowFiltering: true,
            //     allowSorting: false,
            //     width: 150,
            // },                    
        ],
        tableData:[],
    },
    gv_tratruoc_km1: {
        tableCollumns: [
            {
                fieldName: "ten_km",
                headerText: "Khuyến mãi",
                allowFiltering: true,
                allowSorting: false,
                width: 150,     
                isGroupedColumn:true           
            },
            {
                fieldName: "ten_ctkm",
                headerText: "Chi tiết km trả trước",
                allowFiltering: true,
                allowSorting: false,
                width: 150        
            },
            {
                fieldName: "sothang",
                headerText: "Số tháng",
                allowFiltering: true,
                allowSorting: false,
                width: 150,
            },
            {
                fieldName: "tien_tratruoc",
                headerText: "Tiền trả trước",
                allowFiltering: true,
                allowSorting: false,
                width: 100,
                type: "Number", 
                format: "N0"
            },
            // {
            //     fieldName: "sothang_tang",
            //     headerText: "Số tháng tặng",
            //     allowFiltering: true,
            //     allowSorting: false,
            //     width: 150,
            // },                    
        ],
        tableData:[],
    },
    gv_tratruoc_km_mytv: {
        tableCollumns: [
            {
                fieldName: "ten_km",
                headerText: "Khuyến mãi",
                allowFiltering: true,
                allowSorting: false,
                width: 150,     
                isGroupedColumn:true           
            },
            {
                fieldName: "ten_ctkm",
                headerText: "Chi tiết km trả trước",
                allowFiltering: true,
                allowSorting: false,
                width: 150        
            },
            {
                fieldName: "sothang",
                headerText: "Số tháng",
                allowFiltering: true,
                allowSorting: false,
                width: 150,
            },
            {
                fieldName: "tien_tratruoc",
                headerText: "Tiền trả trước",
                allowFiltering: true,
                allowSorting: false,
                width: 100,
                type: "Number", 
                format: "N0"
            },                              
        ],
        tableData:[],
    },
    gv_thuebao: {        
        tableCollumns: [
            {
                fieldName: "ma_tb",
                headerText: "Mã TB",
                allowFiltering: true,
                allowSorting: false,
                width: 120,                
            },            
            {
                fieldName: "loaihinh_tb",
                headerText: "Loại hình TB",
                allowFiltering: true,
                allowSorting: false,
                width: 100,
            },
            {
                fieldName: "tocdo",
                headerText: "Tốc độ",
                allowFiltering: true,
                allowSorting: false,
                width: 120,                
            }, 
            {
                fieldName: "muccuoc",
                headerText: "Mức cước TB",
                allowFiltering: true,
                allowSorting: false,
                width: 100,                
            },
            {
                fieldName: "trangthai_tb",
                headerText: "Trạng thái TB",
                allowFiltering: true,
                allowSorting: false,
                width: 100,                
            },
            {
                fieldName: "tt_thamgia_goi",
                headerText: "Tình trạng tham gia gói DV, trả trước",
                allowFiltering: true,
                allowSorting: false,
                width: 120,
            },   
                       
        ],
        tableData:[],
    },    
    ds_thongtin_thaydoi: {
        list: [],
        header: [
          {
            fieldName: "loai",
            headerText: "Nhóm",
            allowFiltering: true,
            isGroupedColumn: true,
          },
          {
            fieldName: "ten_ht",
            headerText: "Tên thông tin",
            allowFiltering: true,
          },
          {
            fieldName: "giatricu",
            headerText: "Giá trị cũ",
            allowFiltering: true,
          },
          {
            fieldName: "giatrimoi",
            headerText: "Giá trị mới",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
    },
    commands: [
        {
          headerText: 'Thao tác',
          cssClass: 'thaotac', 
          name: 'thaotac',
          text: '<a href="javascript:void(0);">Thay đổi</a>',
          title: 'Thay đổi',
          width: 150,
        }
    ],
    commands1: [
        {
          headerText: 'Thao tác',
          cssClass: 'thaotac', 
          name: 'thaotac1',
          text: '<a href="javascript:void(0);">Thay đổi</a>',
          title: 'Thay đổi',
          width: 150,
        }
    ],
    commands_km: [
        {
          headerText: 'Thao tác',
          cssClass: 'thaotac', 
          name: 'commands_km',
          text: '<a href="javascript:void(0);">Chọn</a>',
          title: 'Chọn',
          width: 150,
        }
    ],
    commands_km1: [
        {
          headerText: 'Thao tác',
          cssClass: 'thaotac', 
          name: 'commands_km1',
          text: '<a href="javascript:void(0);">Chọn</a>',
          title: 'Chọn',
          width: 150,
        }
    ],
    commandColumn: {
        show: true,
        headerText: 'Thao tác',
        width: 100,
        firstColumn: false
    },
}