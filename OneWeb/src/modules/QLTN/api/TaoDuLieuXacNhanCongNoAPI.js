export default {
    getLanTao: (axios, data) =>
        axios.get("/web-thuno/api/thu-no/cong-no/ds-lan-tao-cong-no", {
            params: data
        }),

    getTuFile: (axios, data) =>
        axios.post(
            "web-thuno/api/thu-no/cong-no/du-lieu-xac-nhan-cong-no-theo-file",
            data
        ),
    taoCongNo: (axios, data) =>
        axios.post(
            "web-thuno/api/thu-no/cong-no/du-lieu-xac-nhan-cong-no",
            data
        ),
    capNhatTuFile: (axios, data) =>
        axios.put("web-thuno/api/thu-no/cong-no/xac-nhan-cong-no", data),
        
    getListCongNo: (axios, data) =>
        axios.get("web-thuno/api/thu-no/cong-no/ds-cong-no", { params: data }),

    getXuatFileExcel: (axios, data) =>
        axios.get("web-thuno/api/thu-no/cong-no/xuat-xlsx-ds-cong-no", { params: data })
};
