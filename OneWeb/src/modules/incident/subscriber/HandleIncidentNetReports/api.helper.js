import api from './api'
import helper from './helper'

class apiHelper {

  //CAPNHAT_NHANVIEN_TH
  async capNhatNhanVienTH(axios, param){
    const response = await api.capNhatNhanVienTH(axios, param);
    if(!this.isResponseApiSuccess(response))
      return response.data.message_detail || response.data.message 
    return ''

  }
  // SP_CAPNHAT_GHICHU_TH
  async capNhatGhiChu(axios, param){
    const response = await api.capNhatGhiChu(axios, param);
    if(!this.isResponseApiSuccess(response))
      return response.data.message_detail || response.data.message
    return ''
  }

  async insertNguyenNhanBH(axios, { listNguyenNhan, tinhthicong_id, vphieu_id }) {
    if (!listNguyenNhan.length) return

    let arrRequest = []

    for (let i = 0; i < listNguyenNhan.length; i++) {
      const nnId = listNguyenNhan[i]

      const paramInsertNguyenNhan = { tinhthicong_id, vcthong_id: nnId, vphieu_id }

      const req = api.insertNguyenNhan(axios, paramInsertNguyenNhan)

      arrRequest.push(req)
    }
    return Promise.all(arrRequest)
      .then((values) => {
        // console.log(values);
        return values
      })
      .catch((err) => {
       console.log(err);
      })
  }


  async kiemTraDichVuHoanCong_BH_Net(axios, dichvuvt_id){
    const response =  await api.checkDichVuHoanCong_BHNet(axios, dichvuvt_id);
    return response.data.data
  }


  async layHuongGiaoTheoThuTu(axios, { VPHANVUNG_ID, VLOAITB_ID, VTHUTU}){
    const response = await api.layHuongGiaoTheoThuTu(axios, { VPHANVUNG_ID, VLOAITB_ID, VTHUTU})
    const dtQT = this.getDataFromResponseApiReturnArray(response)
    if(dtQT.length > 0){
      return dtQT.quytrinh_id
    }
    return null;
  }

  async layGoiKTId(axios,{vthuebao_id,vhdtb_id} ){
    const response = await api.layGoiKTId(axios,{vthuebao_id,vhdtb_id})
    return this.getDataFromResponseApiReturnRaise(response)
  }

  async getThongTinGiaoPhieu(axios,  { tinhthicong_id, vbaohong_id, vtype  }){
    const response = await api.getThongTinGiaoPhieu(axios,  { tinhthicong_id, vbaohong_id, vtype  })

    return this.getDataFromResponseApiReturnRaise(response)
  }

  async layDanhSachThamSoMD(axios) {
    
        const response = await api.getDanhSachThamSoMD(axios);

        return this.getDataFromResponseApiReturnArray(response)
  }




  async getUserBHInfo(axios, baohong_id){
    const response = await api.getUserBHInfo(axios, baohong_id)
    const data =  this.getDataFromResponseApiReturnArray(response)
    if(data){
      return data
    }
    return null;
  }

  async isPhieuConHoanCong(axios, phieuId) {
    const response = await api.checkPhieuConHoanCong(axios, phieuId)
    const result = this.getDataFromResponseApiReturnRaise(response)
    return result == '-1'
  }

  async getKey(axios, keyname) {
    const response = await api.getKey(axios, keyname)
    return this.getDataFromResponseApiReturnRaise(response)
  }
  async getKeyBH(axios, tinhthicong_id) {
    const response = await api.getKeyBH_TC(axios, tinhthicong_id)
    return this.getDataFromResponseApiReturnRaise(response)
  }

  //KiemTraGiaoTinh
  async checkGiaoTinh(axios, { vphanvung_id_tc, vbaohong_id }) {
    const response = await api.checkGiaoTinh(axios, { vphanvung_id_tc, vbaohong_id })

    const result = this.getDataFromResponseApiReturnRaise(response)

    return result.toUpperCase() == 'OK';

  }

  async isBaoHongTinhExits(axios, { vbaohong_id, tinhthicong_id }) {
    const response = await api.getTTBaoHong(axios, { vbaohong_id, tinhthicong_id })
    const result = this.getDataFromResponseApiReturnArray(response)
    return result.length != 0
  }


  getSrtCodeError(response) {
    if(!this.isResponseApiSuccess(response))
      return response.data.message_detail || response.data.message
    
    return ""
  }

  getDataFromResponseApiReturnArray(response) {
    if (!response) return []
    if (response && response.data && response.data.error_code === 'BSS-00000000') return response.data.data || []
    return []
  }

  getDataFromResponseApiReturnRaise(response) {
    if (!response) return ''
    if (response && response.data && response.data.error_code === 'BSS-00000000') return response.data.data
    return ''
  }

  isResponseApiSuccess(response) {
    if (!response) return false
    if (response && response.data && response.data.error_code === 'BSS-00000000') return true
    return false
  }
}

export default new apiHelper()
