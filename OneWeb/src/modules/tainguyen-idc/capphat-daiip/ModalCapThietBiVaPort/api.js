export default {
    getDanhSachThietBiCapPhat: (axios, data) => axios.post(`/web-ecms/idc/cap-phat-thu-hoi-ip/danh-sach-thiet-bi-cap-phat`, {
        "tenThietBi": data.tenThietBi && data.tenThietBi.trim() ? data.tenThietBi.trim() : null,
    }),
    getDanhSachPortTheoThietBi: (axios, data) => axios.post(`/web-ecms/idc/cap-phat-thu-hoi-ip/danh-sach-port-theo-thiet-bi`, {
        "tenPort": data.tenPort && data.tenPort.trim() ? data.tenPort.trim() : null,
        "thietBiId": data.thietBiId ?? null,
    }),
    capPhatThietBiVaPort: (axios, data) => axios.post(`/web-ecms/idc/cap-phat-thu-hoi-ip/cap-phat-ip-thiet-bi-port`, {
        "ipId": data.ipId ?? null,
        "portId": data.portId ?? null,
        "thietbiId": data.thietbiId ?? null,
        "trangThaiCapPhat": 1,
        "phieutcId": data.phieutcId ?? null,
        "thicongipstoreId": data.thicongipstoreId ?? null,
    }),
}
