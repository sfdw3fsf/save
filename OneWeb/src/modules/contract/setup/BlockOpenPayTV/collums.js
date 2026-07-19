const collumns = {
    columns: [
        {
            fieldName: "ma_tb",
            headerText: "Mã TB",
            allowSorting: true,
            allowFiltering: true,
            textAlign: 'left',
            width: "150px",
        },
        {
            fieldName: "nguoi_cn",
            headerText: "Người thực hiện",
            allowSorting: true,
            allowFiltering: true,
            textAlign: 'left',
            autoFit: "true",
        },
        {
            fieldName: "ngay_cn",
            headerText: "Ngày thực hiện",
            allowSorting: true,
            allowFiltering: true,
            textAlign: 'center',
            autoFit: "true",
        },
    ],
}

export default collumns
