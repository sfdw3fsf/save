export default {
    header: {
        title: "Tra cứu 5 số",
        list: [{
          name: "Tra cứu",
          link: {
            name: "Ui.cards"
          }
        },
        {
          name: "Tra cứu 5 số",
          link: {
            name: "Ui.buttons"
          },
        },
        ],
    },
    monthFormat: 'MM/YYYY',
    

    dateFormat: 'DD/MM/YYYY',
    dateFormatMonth: 'DD/MM/YYYY',
    typeFormat: 'datetime',
    typeFormatMonth: 'month',
    dateConfig: {
      dateFormat: 'd/m/Y',
      altFormat: 'd/m/Y'
    },
    showTimePanel: false,
    success_code: "BSS-00000000",
    
    txtthongtin: {
        somay: null,
        sotimkiem1: null,
        sotimkiem2: null,
        sotimkiem3: null,
        sotimkiem4: null,
        sotimkiem5: null
    },
    txtThang: null,
    txtTuNgay: null,
    txtDenNgay: null,

    options: {
        grvLSThueBao: [],
        grvLog: []
    },
    columns: {
        lichsugoi: [
            {
                fieldName: "sogoi",
                headerText: "Chủ gọi",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "msisdn",
                headerText: "Bị gọi",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "count",
                headerText: "Tổng số",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            }
        ],
        log: [
            {
                fieldName: "ROW_STT",
                headerText: "User neo",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "USER_ID",
                headerText: "Mã thuê bao",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "LOG_DATE",
                headerText: "Ngày log",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "IP_",
                headerText: "IP",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "NAME",
                headerText: "Menu",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "MA_TB",
                headerText: "Mã thuê bao",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "GHICHU",
                headerText: "Ghi chú",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            }
        ]
    }     

}