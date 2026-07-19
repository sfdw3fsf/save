export default {
    getThongTinTBSN: (axios, data) => axios.get(`/web-qlvt/api/chuan-hoa-serial/thue-bao-su-dung-vat-tu/${data.serial}`),
    postChuanHoaSerial: (axios, data) => axios.post(`/web-qlvt/api/chuan-hoa-serial/chuan-hoa`,data),
   
}