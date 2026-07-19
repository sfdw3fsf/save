const columns = {
    columnsDS: [
        { fieldName: "soi", headerText: "Sợi", isGroupedColumn: true },
        { fieldName: "ma_tb", headerText: "Số máy/Acc", textAlign: "left", width: "200px" },
        { fieldName: "ky_hieu", headerText: "Phiến dọc", textAlign: "left", autoFit: "true" },
        { fieldName: "jumper_vao", headerText: "Jumper", textAlign: "left", autoFit: "true" },
        { fieldName: "ten_td", headerText: "Tổng đài", textAlign: "left", autoFit: "true" },
    ],
    columnsDS_DDC: [
        { fieldName: "kcn", headerText: "Kết cuối nguồn", textAlign: "left", width: "300px" },
        { fieldName: "vtn", headerText: "VT nguồn", textAlign: "left", autoFit: "true" },
        { fieldName: "kcd", headerText: "Kết cuối đích", textAlign: "left", autoFit: "true" },
        { fieldName: "vtd", headerText: "VT đích", textAlign: "left", autoFit: "true" },
        { fieldName: "ma_tb", headerText: "Mã thuê bao", textAlign: "left", autoFit: "true" },
        { fieldName: "trangthai", headerText: "Trạng thái", textAlign: "left", autoFit: "true" },
        { fieldName: "diachi_kcn", headerText: "Địa chỉ KCN", textAlign: "left", autoFit: "true" },
        { fieldName: "diachi_kcd", headerText: "Địa chỉ KCĐ", textAlign: "left", autoFit: "true" },
    ],
}

export default columns
