export default {
    uploadDSBoSungTheoFile: (axios, data) => axios.post("/web-qlvt/api/bo-sung-vat-tu-file/upload_ds_bosungvt_file",data),
    dayDanhSachBoSung: (axios, data) => axios.post("/web-qlvt/api/bo-sung-vat-tu-file/day_vattu_bs",data),
};
