export default {
    getDanhSachCongViec: (axios, phieutcId) => 
    axios.post(`/web-ecms/idc/cap-phat-thu-hoi-ip/chi-tiet-cong-viec?phieutcId=${phieutcId}`, {
        "phieutcId": phieutcId,
    }),
    getDanhSachDaiIPDaThiCong: (axios, phieutcId) => 
    axios.post(`/web-ecms/idc/cap-phat-thu-hoi-ip/danh-sach-ip-store-thi-cong`, {
        "phieutcId": phieutcId,
    }),
    getDanhSachIPTheoDaiIPDaThiCong: (axios, thicongipstoreId) => 
        axios.post(`/web-ecms/idc/cap-phat-thu-hoi-ip/chi-tiet-ip-theo-thicongipstore`, {
        "thicongipstoreId": thicongipstoreId,
    }),
    deleteDaiIP: (axios, thicongipstoreId) => axios.post(`/web-ecms/idc/cap-phat-thu-hoi-ip/xoa-cap-phat-ipstore-ip`, {
        "thicongipId": null,
        "thicongipstoreId": thicongipstoreId,
        "type": 1,
    }),
    setHoanThanhPhieu: (axios, phieutcId) => axios.post(`/web-ecms/idc/cap-phat-thu-hoi-ip/cap-nhat-thong-tin-phieu-tc`, {
        "isHoanThanh": 1,
        "lydoTraphieu": null,
        "nguoiChuyenXl": null,
        "phieutcId": phieutcId,
        "trangthai": "THICONG_HT",
    }),
}
