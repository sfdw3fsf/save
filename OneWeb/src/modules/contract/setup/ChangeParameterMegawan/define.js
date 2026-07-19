import moment from "moment"
export default {
    DichVuVienThong: {
        TATCA: -1,
        CO_DINH: 1,
        DI_DONG: 2,
        ADSL: 4,
        DD_TRATRUOC: 5,
        EMAIL: 6,
        METRONET: 7,
        MEGAWAN: 8,
        TSL: 9,
        GPHONE: 10,
        IMS: 11,
        MEGA_EYES: 12,
        HOINGHI_TRUYENHINH: 13,
        ANTOAN_BAOMAT_TT: 14,
        DICHVU_CNTT: 15,
        TRUNGTAM_DULIEU: 16,
        HATANG_VIENTHONG: 19,
    
        IMS_DOITAC: 21,
        BRCD_DOITAC: 22,
        MGW_DOITAC: 23,
        TSL_DOITAC: 24
      },
      actions: [
        {
          id: 'laythongtin',
          name: 'Lấy TT (F5)',
          active: true,
          icon_class: 'one-file-attach',
          visible: true
        },
        {
          id: 'capnhat',
          name: 'Cập nhật',
          active: true,
          icon_class: 'one-reload1',
          visible: true
        },
        {
          id: 'giaoviec',
          name: 'Giao việc',
          active: true,
          icon_class: 'one-file-edit2',
          visible: true
        },
        {
            id: 'kichhoat',
            name: 'Kích hoạt',
            active: false,
            icon_class: 'one-file-arrow-up',
            visible: true
        },
        {
            id: 'giaophieu',
            name: 'Giao phiếu',
            active: true,
            icon_class: 'one-file-arrow-right1',
            visible: true
        },
        {
            id: 'daunoi',
            name: 'Đấu nối',
            active: true,
            icon_class: 'one-connect',
            visible: true
        },
        {
            id: 'hoancong',
            name: 'Hoàn công',
            active: false,
            icon_class: 'one-file-check',
            visible: true
        },
        {
            id: 'hoanthien',
            name: 'Hoàn thiện',
            active: true,
            icon_class: 'one-file-check',
            visible: true
        },
        {
          id: 'traphieu',
          name: 'Trả phiếu',
          active: true,
          icon_class: 'one-file-back',
          visible: true
        },
        {
            id: 'xuatphieu',
            name: 'Xuất phiếu',
            active: true,
            icon_class: 'one-file-download',
            visible: true
        },
        {
            id: 'henlapdat',
            name: 'Hẹn LĐ',
            active: true,
            icon_class: 'dropdown-icon  one-calendar-check',
            visible: true
        },
        {
            id: 'thongsokythuat',
            name: 'Thông số KT',
            active: true,
            icon_class: 'dropdown-icon  nc-icon-glyph ui-1_settings',
            visible: true
        },
        {
            id: 'gankhuvuc',
            name: 'Gán KV',
            active: true,
            icon_class: 'dropdown-icon  one-users',
            visible: true
        },
      ],
      actionsDropdown:[
        
          {
            id: 'vattu',
            name: 'Vật tư',
            active: true,
            icon_class: 'dropdown-icon  one-vt',
            visible: true
          },
          {
              id: 'duan',
              name: 'Dự án',
              active: true,
              icon_class: 'dropdown-icon  dropdown-icon one-clipart-edit',
              visible: true
          },
          {
              id: 'dokiem',
              name: 'Đo kiểm',
              active: true,
              icon_class: 'dropdown-icon  dropdown-icon nc-icon-glyph design_design',
              visible: true
          },
          {
              id: 'suakenhthu',
              name: 'Sửa kênh thu',
              active: true,
              icon_class: 'dropdown-icon  one-clipart-edit',
              visible: true
          },
          
          {
            id: 'giaonet',
            name: 'Giao Net',
            active: true,
            icon_class: 'one-file-arrow-right1',
            visible: true
        },
      ],
    dsThaoTac:[],
    optionTrangThai: '0', // default là chọn phiếu mới
    IS_USING_CABMAN_V2:false,
    searchOne:false,
    search_GD:'',
    search_TB:'',
    ma_tb:'',
    dichvuvt_id:0,
    quytrinh_id:0,
    huonggiao_id:0, // load khi combobox quy trình thay đổi
    luong_id:0, // load khi combobox quy trình thay đổi
    port_id:0,  //port mới
    vci_vpi_id:0, //v mới
    bras_id:0,
    dslam_id:0,
    lan_ip:0,
    wan_ip:0,
    dsHBTB: [],
    selectItems:[],
    searchDate: {
        isEnabled: true,
        isEnabledFromDate: false,
        isEnabledToDate: false,
        fromDate: moment(new Date()).format('DD/MM/yyyy hh:mm a'),
        toDate: moment(new Date()).format('DD/MM/yyyy hh:mm a'),
        checkFromDate:false,
        checkToDate:false,
        isEnabledcheckFromDate: true,
        isEnabledcheckToDate: true,
        dateconfig: {
            altFormat: "d/m/Y h:i K",
            altInput: true,
            dateFormat: "d/m/Y h:i K",
            allowInput: true,
        },
    },
    dateFormat:'DD/MM/YYYY HH:mm A',
    popupComponent: null,
    popupComponentData: null,
    popupComponentAttr: {},    
    popupComponentName: '',
    gridbox: {
        bohoso: {
            list: [],
            header: [],
            value: {},
        },
        filehoso: {
            list: [],
            header: [],
            value: {},
        },
        hdthuebao: {
            list: [],
            header: [],
            value: {},
        },
    },
    dateconfig: {
        altFormat: "d/m/Y H:i:S",
        altInput: true,
        dateFormat: "d/m/Y H:i:S",
        allowInput: true,
        enableTime: true,
    },    
    in_thuebao_cha_id:0,
    vhuongkn:"0",
    options:{
        tinhKN: [],
        loaiKenh: [],
        chuQuan: [],
        trangBi: [],
        loaiThietBi: [],
        cbx_thietbi:[],
        modem: [],
        tocDoCir: [],
        tocDoPir: [],
        tocDoNix: [],
        tocDoIsp: [],
        thoiHan: [],
        tenThietBi: [],
        dsDichVu: [],//?
        dichVu: [],
        dsQuyTrinh: [],//?
        quyTrinh: [], //có thể load lại theo dịch vụ
        nguoiGV: [],
    },
    enable:{
        btnHoanCong:true,
        btnGiaoPhieu:true,
        btnHoanThienHS:true,
        btnGiaoViec:true,
        btnCapPort:false,
        btnHuongKN:false,
        TocdoPir:false,
        TocdoNix:false,
        TocdoIsp:false,
    },
    checkbox:{
        ckbTocdoPir:false,
        ckbTocdoNix:false,
        ckbTocdoIsp:false,
    },
    modelIn:{
        phieu_id:0,
        ma_gd:'',
        hdkh_id:0,
        nvth_id:-1,
        huonggiao_id:0,
        tentram_tb:''
    },
    formViewTTKH: {
        phieu_id:0,
        hdtb_id:0,
        hdkh_id:0,
        loaihd_id:0,
        dichvuvt_id: 0,
        loaitb_id: 0,
        ma_gd: "",
        ten_kieuld: "",
        quytrinh_id: 0,
        ma_tb: "",
        ma_lt: "",
        ten_tb: "",
        diachi_ld: "",
        nd_giao: "",
        nd_tra: "", //xem lại
        ghichu_tb: "",
        thuebao_id:0,
        diachi_id:0,
        ngaygiao:"",
    },

    formViewGiaoViec: {
        ngayGV: "",
        ngayHT: "",
        nguoi_gv_id: 0,
        
    },

    formViewHoanCong: {
        loaikenh_id: "",
        chuquan_id: "",
        trangbi_id: "",
        thietbidc_id: "",
        loaimd_id: "",
        nhanvien_id:0,
        nhanvien_yc: "",
        tocdo_id: "",
        tocdo_pir_id: "",
        tocdo_nix: "",
        tocdo_isp: "",
        thoihan_id: "",
        thaydoi_td: "",
        diachi_kn: "",
        huong_kn: "",
        tendslam: "",
        adsl_port: "",
        nasport_id: "",
        wan_ip: "",
        lan_ip: "",
        dslam_tinh_id: "",
        dslam_tinh:"",
        link: "",
        site_id: "",        
        tinh_kn_id:0,
        port_mdf: "",
        ipdslam: "",
        svlan: "",
        cvlan: "",
        vrf: "",
        route: "",
        tramtb_id:0, //trạm port
        donvi_id:0, //trạm thi công loaidv=5
        ten_tbi:"",
        lbldslam:"Dslam",
        port_id:0,
        bras_id:0,
		slot:"",
		system:"",
		rack:"",
		shelf:"",
		shelf01:"",
		shelf02:"",
		shelf03:"",
		shelf04:"",
		congnghe_id:0,
		vci_vpi_id:0,
		cuoc_tc:0,
		cuoc_tk:0,		
        nd_thuchien:""
    },
    dataPopupKhuVuc:{
        quan_id:0,
        phuong_id:0,
        pho_id:0,
        ap_id:0,
        khu_id:0,
        dacdiem_id:0,
        khuvuc_id:0,
        donviql_id:0,
        hdtb_id:0
    },
    tableConfigNGV: {
        tableCollumns: [
            {
                fieldName: "ten_nv",
                headerText: "Tên nhân viên",
                allowFiltering: true,
                allowSorting: false,
                width: 200,
            },
            {
                fieldName: "so_dt",
                headerText: "Số điện thoại",
                allowFiltering: true,
                allowSorting: false,
                width: 200,
            },
            {
                fieldName: "nhiemvu",
                headerText: "Nhiệm vụ",
                allowFiltering: true,
                allowSorting: false,
                width: 200,
            },
            {
                fieldName: "ghichu",
                headerText: "Ghi chú",
                allowFiltering: true,
                allowSorting: false,
                width: 200,
            },
        ],
        tableData:[],
    },
    tableConfigDSThueBao: {
        tableCollumns: [
            {
                fieldName: "ngay_yc",
                headerText: "Ngày Yc",
                format: {type: 'date', format: 'dd/MM/yyyy HH:mm:ss'},
                type: 'date',
                allowFiltering: true,
                allowSorting: false,
                textAlign:'Right'
            },
            {
                fieldName: "ngaygiao",
                headerText: "Ngày giao",
                
                allowFiltering: true,
                allowSorting: false,
                textAlign:'Right'
            },
            {
                fieldName: "giohen_tu",
                headerText: "Hẹn từ",
                allowFiltering: true,
                allowSorting: false,
            },
            {
                fieldName: "giohen_den",
                headerText: "Hẹn đến",
                allowFiltering: true,
                allowSorting: false,
            },
            {
                fieldName: "ma_gd",
                headerText: "Mã GD",
                allowFiltering: true,
                allowSorting: false,
            },
            {
                fieldName: "ma_tb",
                headerText: "Account",
                allowFiltering: true,
                allowSorting: false,
            },
            {
                fieldName: "ma_lt",
                headerText: "Số ảo",
                allowFiltering: true,
                allowSorting: false,
            },
            {
                fieldName: "loaihinh_tb",
                headerText: "Loại hình",
                allowFiltering: true,
                allowSorting: false,
            },
            {
                fieldName: "ten_tb",
                headerText: "Tên thuê bao",
                allowFiltering: true,
                allowSorting: false,
            },
            {
                fieldName: "ten_kv",
                headerText: "Khu vực",
                allowFiltering: true,
                allowSorting: false,
            },
            {
                fieldName: "diem_chia",
                headerText: "Điểm chia",
                allowFiltering: true,
                allowSorting: false,
            },            
            {
                fieldName: "diachi_ld",
                headerText: "Địa chỉ lắp đặt",
                allowFiltering: true,
                allowSorting: false,
            },
            {
                fieldName: "luu_in",
                headerText: "Trạng thái in",
                allowFiltering: true,
                allowSorting: false,
            },
            {
                fieldName: "ngay_ht",
                headerText: "Ngày bàn giao",
                allowFiltering: true,
                allowSorting: false,
                format: {type: 'date', format: 'dd/MM/yyyy HH:mm:ss'},
                type: 'date',
                textAlign:'Right'
            },
            {
                fieldName: "dv_giao",
                headerText: "Đơn vị giao",
                allowFiltering: true,
                allowSorting: false,
            },
            {
                fieldName: "ghi_chu",
                headerText: "Ghi chú",
                allowFiltering: true,
                allowSorting: false,
            },
        ],
        tableData: []
    },
}