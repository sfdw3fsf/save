import {  handleResultOB_ChuyenTraSau, handleResultOB_ChuyenTraSauKhongTamThu, handleResultOB_DongYGiaHan, handleResultOB_HenLienLacLaiSau, handleResultOB_KhongDungChuThueBao, handleResultOB_KhongHaiLongChatLuongDV, handleResultOB_KhongHaiLongVeCuoc, handleResultOB_TaoYeuCauLapDat, handleResultOB_ThayDoiTocDo, handleResultOB_TiepNhanDonHang, handleResultOB_YeuCauTamNgung, handleResultOB_YeuCauThanhLy, handleResultOB_YeuCauThanhToanGiaHan, handleResultOB_YeuCauThayDoiDatCoc } from "./handleResultOB";


//using strategy pattern
class ProcessResultOBManger {
    constructor() {
        this._results = [];
    }
    addResultOB(result) {
        this._results = [...this._results, result];
    }
    getResultOB(KQ_OB) {
        return this._results.find(result => result.id === KQ_OB)
    }
}
class ProcessResultOB {
    constructor(KQ_OB, handle) {
        this.id = KQ_OB;
        this._handle = handle
    }
    processResult(sef, data) {
        this._handle(sef, data)
    }
}

const handleResultOBManger = new ProcessResultOBManger();

handleResultOBManger.addResultOB(new ProcessResultOB(1,  (self, data) => {
     handleResultOB_DongYGiaHan(self, data)
}))

handleResultOBManger.addResultOB(new ProcessResultOB(2,  (self, data) => {
     handleResultOB_ThayDoiTocDo(self, data)
}))
handleResultOBManger.addResultOB(new ProcessResultOB(3,  (self, data) => {
     handleResultOB_YeuCauThanhToanGiaHan(self, data)
}))
handleResultOBManger.addResultOB(new ProcessResultOB(4,  (self, data) => {
     handleResultOB_ChuyenTraSau(self, data)
}))
handleResultOBManger.addResultOB(new ProcessResultOB(5,  (self, data) => {
     handleResultOB_YeuCauTamNgung(self, data)
}))
handleResultOBManger.addResultOB(new ProcessResultOB(6,  (self, data) => {
     handleResultOB_KhongHaiLongChatLuongDV(self, data)
}))
handleResultOBManger.addResultOB(new ProcessResultOB(7,  (self, data) => {
     handleResultOB_KhongHaiLongVeCuoc(self, data)
}))
handleResultOBManger.addResultOB(new ProcessResultOB(8,  (self, data) => {
     handleResultOB_YeuCauThanhLy(self, data)
}))
handleResultOBManger.addResultOB(new ProcessResultOB(9,  (self, data) => {
     handleResultOB_HenLienLacLaiSau(self, data)
}))
handleResultOBManger.addResultOB(new ProcessResultOB(10,  (self, data) => {
     handleResultOB_TiepNhanDonHang(self, data)
}))
handleResultOBManger.addResultOB(new ProcessResultOB(11,  (self, data) => {
     handleResultOB_TaoYeuCauLapDat(self, data)
}))
handleResultOBManger.addResultOB(new ProcessResultOB(12,  (self, data) => {
     handleResultOB_ChuyenTraSauKhongTamThu(self, data)
}))
handleResultOBManger.addResultOB(new ProcessResultOB(13,  (self, data) => {
     handleResultOB_KhongDungChuThueBao(self, data)
}))
handleResultOBManger.addResultOB(new ProcessResultOB(14,  (self, data) => {
     handleResultOB_KhongGapDuocKH(self, data)
}))

handleResultOBManger.addResultOB(new ProcessResultOB(15, async (self, data) => {
    await handleResultOB_YeuCauThayDoiDatCoc(self, data)
}))


export default handleResultOBManger;