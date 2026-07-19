export default {
    getDanhSachDiaChiIPThuHoi: (axios, data) => 
    axios.post(`/web-ecms/idc/cap-phat-thu-hoi-ip/danh-sach-ip-cap-phat`, {
        "ipAddress": data.ipAddress && data.ipAddress.trim() ? data.ipAddress.trim() : null,
        "tenSubnet": data.tenSubnet && data.tenSubnet.trim() ? data.tenSubnet.trim() : null,
        "trangThaiId": 1,
        "phieutcId": data.phieutcId,
    }),
    thuHoiIP: (axios, data) => 
    axios.post(`/web-ecms/idc/cap-phat-thu-hoi-ip/cap-phat-ip`, {
        "ghiChu": data.ghiChu && data.ghiChu.trim() ? data.ghiChu.trim() : null,
        "ipstoreId": data.ipstoreId ?? null,
        "moTa": data.moTa && data.moTa.trim() ? data.moTa.trim() : null,
        "nhanvienId": data.nhanvienId ?? null,
        "noiDung": data.noiDung && data.noiDung.trim() ? data.noiDung.trim() : null,
        "phieutcId": data.phieutcId ?? null,
        "thicongipstoreId": data.thicongipstoreId ?? null,
        "trangThaiId": data.trangThaiId ?? null,
        "ipID": data.ipID ?? null,
        "thietbiMangId": data.thietbiMangId ?? null,
        "loaiCv": "THUHOI",
    }),
}
