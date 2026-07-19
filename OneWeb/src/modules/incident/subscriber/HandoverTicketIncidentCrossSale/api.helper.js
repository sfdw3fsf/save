import api from './api'

class apiHelper {

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


  async updateTrangThaiBH_BC(axios, param){
    const response = await api.SP_UPDATE_BAOHONG_BC_TRANGTHAI(axios, param);

    return this.getSrtCodeError(response)
  }

  async tiepNhanBaoHong(axios, param){
    const response = await api.tiepNhanBaoHong(axios, param);

    return this.getSrtCodeError(response)
  }

  getSrtCodeError(response) {
    if(!this.isResponseApiSuccess(response))
      return response.data.message_detail || response.data.message
    
    return ""
  }

  async getKeyBAOHONG_BC(axios, tinhthicong_id){
    const response  = await api.getKeyBH_BC(axios,{ tinhthicong_id, keyname: "BAOHONG"} )
    return this.getDataFromResponseApiReturnRaise(response)
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

  getDataFromResponseApiReturnArray(response) {
    if (!response) return []
    if (response && response.data && response.data.error_code === 'BSS-00000000') return response.data.data || []
    return []
  }
}

export default new apiHelper()
