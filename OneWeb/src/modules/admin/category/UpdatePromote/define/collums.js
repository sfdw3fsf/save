const collumns = {
    collumns: [
        {
            fieldName: "MA_TB",
            headerText: "Mã TB",
            allowSorting: false,
            allowFiltering: true,
            textAlign: 'center',
            autoFit: "true",
        },
        {
            fieldName: "SERIAL",
            headerText: "Serial",
            allowSorting: false,
            allowFiltering: true,
            textAlign: 'left',
            autoFit: "true",
        },
        {
            fieldName: "STB_TYPE",
            headerText: "STB Type",
            allowSorting: false,
            allowFiltering: true,
            textAlign: 'left',
            autoFit: "true",
        },
        {
            fieldName: "LOI",
            headerText: "Thông báo lỗi",
            allowSorting: false,
            allowFiltering: true,
            textAlign: 'left',
            autoFit: "true",
        },
        {
            fieldName: "STATUS",
            headerText: "Trạng thái",
            allowSorting: false,
            allowFiltering: true,
            visible: false,
            textAlign: 'left',
        },
        {
            fieldName: "STATUS_IMG",
            headerText: "Trạng thái",
            allowSorting: false,
            allowFiltering: true,
            allowHtml: true,
            textAlign: 'center',
            width: "100px",
        },
    ]
}

export default collumns
