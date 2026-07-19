export default {
    getDanhSachThietBiCapPhat: (axios, data) => axios.post(`/web-ecms/idc/cap-phat-thu-hoi-ip/danh-sach-thiet-bi-cap-phat`, {
        "tenThietBi": data.tenThietBi && data.tenThietBi.trim() ? data.tenThietBi.trim() : null,
        "thietbiId": data.thietbiId ?? null,
    }),
    getDanhSachPortTheoThietBi: (axios, data) => axios.post(`/web-ecms/idc/cap-phat-thu-hoi-ip/danh-sach-port-theo-thiet-bi`, {
        "tenPort": data.tenPort && data.tenPort.trim() ? data.tenPort.trim() : null,
        "thietBiId": data.thietBiId ?? null,
        "congId": data.congId ?? null,
    }),
}
