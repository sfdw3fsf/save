export default {
    header: {
        title: "TRA CỨU GÓI CƯỚC MNP",
        list: [{
            name: "Phát triển thuê bao",
            link: "/pttb",
        }, {
            name: "Tra cứu gói cước mnp",
            link: "/pttb/tcmnp",
        }, ],
    },
    success_code: "BSS-00000000",
    isTimKiem: false,
    isPhuongAn: false,
    isDoanhNghiep: false,
    txtMaTB: null,
    listPort: [],
    
    cboPort: [
        {
            text: "PORT IN",
            value: "1"
        },
        {
            text: "PORT OUT",
            value: "2"
        }
    ],
    cboPhuongAn: [],
    cboPhuongAn12: [
        {
            text: "Phương án 1",
            value: "1"
        },
        {
            text: "Phương án 2",
            value: "2"
        }
    ],
    cboPhuongAn123: [
        {
            text: "Phương án 1",
            value: "1"
        },
        {
            text: "Phương án 2",
            value: "2"
        },
        {
            text: "Phương án 3",
            value: "3"
        }
    ],
    form: {
        port: null,
        phuongan: null
    },
    grvTTChiTiet: {
        data: [],
        header: [
            { fieldName: 'STT', headerText: 'STT', allowFiltering: true, allowSorting: true, width: "80" },
            { fieldName: 'PACKAGE', headerText: 'Tên gói', allowFiltering: true, allowSorting: true, width: 'auto' },
            { fieldName: 'DESCRIPTION', headerText: 'Mô tả', allowFiltering: true, allowSorting: true, width: 'auto' }
        ]
    }
}