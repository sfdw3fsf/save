const collumns = {
    collumnsDS: [
        {
            fieldName: "stt",
            headerText: "STT",
            allowSorting: false,
            allowFiltering: true,
            width: "50",
            textAlign: 'left',
            autoFit: "true",
        },
        {
            fieldName: "thaotac",
            headerText: "Thao tác",
            allowFiltering: true,
            width: "100",
            autoFit: "true",
            textAlign: 'left',
        },
        {
            fieldName: "code",
            headerText: "Code",
            allowFiltering: true,
            width: "100",
            autoFit: "true",
            textAlign: 'left',
        },
    ],
    collumnsDSDagan: [
        {
            fieldName: "control_name",
            headerText: "Control đã gán",
            allowSorting: false,
            allowFiltering: true,
            width: "50",
            textAlign: 'left',
            autoFit: "true",
        },
    ],
    collumnsDSChuagan: [
        {
            fieldName: "control_name",
            headerText: "Control chưa gán",
            allowSorting: false,
            allowFiltering: true,
            width: "50",
            textAlign: 'left',
            autoFit: "true",
        },
    ],
    
}

export default collumns
