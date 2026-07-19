export default {
  getDSSerialCN: (axios, data) =>
    axios.post("/web-qlvt/api/vat-tu-thue-bao/ds-serial-cn", data),
  capNhatSerialTraCuu: (axios, data) =>
    axios.post("/web-qlvt/api/vat-tu-thue-bao/cap-nhat-doi-serial", data),
  getLogCapNhatSerial: (axios, data) =>
    axios.post("/web-qlvt/api/vat-tu-thue-bao/log-cap-nhat-serial", data),
  getKeySessionIDVT: (axios, data) =>
    axios.post("/web-qlvt/api/cap-phat-ccdc/get-key", data),
  getKetQuaCN: (axios, sessionId) =>
    axios.get(`/web-qlvt/api/vat-tu-thue-bao/cap-nhat-ketqua-sr/${sessionId}`)
};
