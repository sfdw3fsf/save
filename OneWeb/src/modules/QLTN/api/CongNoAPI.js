export default {    
    getDanhSachCongNoBaoCao: (axios, data) => axios.get("/web-thuno/api/thu-no/cong-no/ds-cong-no-bao-cao", { params: data }),
    getDanhSachCongNoTheoMaVach: (axios, data) => axios.get("/web-thuno/api/thu-no/cong-no/ds-cong-no-theo-ma-vach", { params: data }),
    updateXacNhanKetQuaCongNoTheoId: (axios, data) => axios.put("/web-thuno/api/thu-no/cong-no/xac-nhan-ket-qua-cong-no", data),
    getDanhSachCongNoDaGan: (axios, data) => axios.get("/web-thuno/api/thu-no/cong-no/ds-cong-no-da-gan", { params: data }),
    getDanhSachDonViXacNhanNo: (axios) => axios.get("/web-thuno/api/thu-no/xu-ly-no/don-vi-xac-nhan-no"),
    getDanhSachNhanVienHRMTheoDonVi: (axios, id) => axios.get("/web-thuno/api/thu-no/common/ds-nhan-vien-hrm/don-vi/" + id),
    updateGiaoPhieuNhanVienXacNhan: (axios, data) => axios.put("/web-thuno/api/thu-no/cong-no/giao-phieu-nv-xac-nhan-cong-no", data),
    getXuatFileXlsx: (axios, data) => axios.get("/web-thuno/api/thu-no/cong-no/xuat-xlsx-ds-cong-no-bao-cao", { params: data })
};