export default {
    header: {
        title: "Tra cứu cảnh báo cước nóng",
        list: [{
          name: "Lập hợp đồng",
          link: {
            name: "Ui.cards"
          }
        },
        {
          name: "Lắp đặt mới",
          link: {
            name: "Ui.buttons"
          },
        },
        ],
    },
    dateFormat: 'DD/MM/YYYY',
    dateFormatMonth: 'MM/YYYY',
    typeFormat: 'datetime',
    typeFormatMonth: 'month',
    dateConfig: {
      dateFormat: 'd/m/Y',
      altFormat: 'd/m/Y'
    },
    showTimePanel: false,
    radio_loaicuoc: 0,
    cbotrangthaixuly: [],
    cbokieunhantin: [],
    txtMaThueBao: null,
    txtTuNgay: null,
    txtDenNgay: null,

    ID: null,
    ma_tb: null,

    success_code: "BSS-00000000",

    current: {
        mathuebao: null,
        kieutinnhan: null,
        tungay: null,
        denngay: null,
        trangthaixuly: null,            
    },

    form: {
        kieutinnhan: null,
        trangthaixuly: null,
    },

    options: {
        grvTTChiTiet_DK: [],
        grvTTChiTiet_NT: [],
        grvTTChiTiet_CNTN: []
    },

    kieusmm: [
        {
            id_sms: 1,
            kieu_sms: "Không chọn"
        },
        {
            id_sms: 2,
            kieu_sms: "Quảng bá"
        },
        {
            id_sms: 2,
            kieu_sms: "Cảnh báo"
        },
         {
            id_sms: 4,
            kieu_sms: "Thông báo khóa máy"
        },
        {
            id_sms: 5,
            kieu_sms: "Thông báo mở máy"
        },                               
    ],
    trangthai: [
        {
            id: 1,
            trangthai: "Không chọn"
        },
        {
            id: 2,
            trangthai: "Chưa xử lý"
        },
        {
            id: 3,
            trangthai: "An toàn"
        },
        {
            id: 4,
            trangthai: "Không an toàn"
        },
        {
            id: 5,
            trangthai: "Khóa máy"
        },                      
    ],

    columns: {
        grvTTChiTiet_DK: [
            {
                fieldName: "STT",
                headerText: "STT",
                allowFiltering: true,
                allowSorting: true,
                width: '70'
            },
            {
                fieldName: "MSISDN",
                headerText: "Mã thuê bao",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "TEN_TB",
                headerText: "Tên thuê bao",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "DIACHI",
                headerText: "Địa chỉ",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "BL_NAME",
                headerText: "Ngưỡng",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto',
                customAttributes: { class: 'font-weight-bold' }
            },
            {
                fieldName: "DATE_INS",
                headerText: "Ngày TH",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto',
                customAttributes: { class: 'font-weight-bold' }
            },
            {
                fieldName: "USER_INS",
                headerText: "Người TH",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto',
                customAttributes: { class: 'font-weight-bold' }
            },
            {
                fieldName: "MONTH_LIVE",
                headerText: "Tháng QL",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "NOTE",
                headerText: "Ghi chú",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            }
        ],
        grvTTChiTiet_NT: [
            {
                fieldName: "STT",
                headerText: "STT",
                allowFiltering: true,
                allowSorting: true,
                width: '70'
            },
            {
                fieldName: "MSISDN",
                headerText: "Mã thuê bao",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "TEN_TB",
                headerText: "Tên thuê bao",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "DIACHI",
                headerText: "Địa chỉ",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "CONTENT",
                headerText: "Nội dung",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "DATE_INS",
                headerText: "Ngày nhắn",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "TYPE_SMS",
                headerText: "Kiểu nhắn",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "HOTBILL_LIMIT",
                headerText: "Ngưỡng",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
        ]
    },

    Data_CNTN: {
        headers: [         
            {
                fieldName: "STT",
                headerText: "STT",
                allowFiltering: true,
                allowSorting: true,
                width: '70'
            },
            {
                fieldName: "MSISDN",
                headerText: "Mã TB",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "TEN_TB",
                headerText: "Tên TB",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "DIACHI",
                headerText: "Địa chỉ",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "CONTENT_SMS",
                headerText: "Nội dung SMS",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "DATE_INS",
                headerText: "Ngày TH",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "TRANGTHAI",
                headerText: "TT xử lý",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "UPDATE_BY",
                headerText: "Người xử lý",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
        ],
        data: [],
        selected: 0,
        checked: []
    }
    
    
        
}