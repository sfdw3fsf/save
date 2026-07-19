export default {
    header: {
        title: "Kiểu số đẹp",
        list: [
            { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
            {
                name: "Lắp đặt mới",
                link: { name: "Ui.buttons" },
            },
        ],
    },
    tooltip: "",
    options: {
        nhom_so_dep : [],
        kieu_so: []
    },
    data: {
        kieu_so: []
    },
    NHOMSO_ID: null,
    KIEUSO_ID: null,
    form: {
        GHICHU: null,
        MOTA: null,
        DINHNGHIA: null,
        TIEN_HM: null,
        KIEU_SO: null
    },
    config: {
        collumnsNV: [
            {
                fieldName: "KIEU_SO",
                headerText: "Kiểu số",
                allowFiltering: true,
                allowSorting: false,
                width: 'auto'
              },
              {
                fieldName: "MOTA",
                headerText: "Mô tả",
                allowFiltering: true,
                allowSorting: false,
                width: 'auto'
            },
        ]
    },
    button: {
        nhapmoi : false,
        ghilai : false,
        xoa : false,
        huybo : false,
        phanloai: true
    },
    current: 0
}