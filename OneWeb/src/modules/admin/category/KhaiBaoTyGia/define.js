export default {
    data: {
        cnTyGia: [],
    },
    tygia_id_s:"",
    form: {
        TYGIA_ID: null,
        NGANHANG_ID: null,
        LOAINGOAITE_ID: null,
        TYGIA: null,
        NGAYTYGIA: new Date(),
        TEN_NH: null,
        LOAITIEN: null,
    },
    config: {
        collumnsCapNhatTyGia: [
            {
                fieldName: 'ma_loaitien',
                headerText: 'Loại ngoại tệ',
                allowFiltering: true,
                allowSorting: false,
                width: 80,
                textAlign: 'left'
            },
            {
                fieldName: 'ten_nh',
                headerText: 'Ngân hàng',
                allowFiltering: true,
                allowSorting: false,
                width: 80,
                textAlign: 'left'
            },
            {
                fieldName: 'tygia',
                headerText: 'Tỷ giá',
                allowFiltering: true,
                allowSorting: false,
                width: 80,
                textAlign: 'left'
            },
            {
                fieldName: 'ngay_tygia',
                headerText: 'Ngày tỷ giá',
                allowFiltering: true,
                allowSorting: false,
                width: 80,
                textAlign: 'left',
                type: "date",
                format: "dd/MM/y"
            },
            {
                fieldName: 'ngay_cn',
                headerText: 'Ngày cập nhật',
                allowFiltering: true,
                allowSorting: false,
                width: 80,
                textAlign: 'left'
            },
            {
                fieldName: 'nguoi_cn',
                headerText: 'Người cập nhật',
                allowFiltering: true,
                allowSorting: false,
                width: 80,
                textAlign: 'left'
            },
        ]
    },
    button: {
        taomoi : true,
        ghilai : false,
        xoa : false,
        huybo : false
    },
    current: 0
}