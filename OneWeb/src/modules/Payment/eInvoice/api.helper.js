import api from './api/index'

class apiHelper {

  async Sinh_MaGD(axios, loaihd_id){
    const response = await api.Sinh_MaGD(this.axios, {
      loaihd_id: loaihd_id,
      metaClass: {},
    });
    return this.getDataFromResponseApiReturnRaise(response)
  }

  getErrorMessage(response){
    if(!this.isResponseApiSuccess(response))
      return response.data.message_detail || response.data.message
    return response.data.data
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
