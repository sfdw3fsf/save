

class apiHelper {

    getSrtCodeError(response) {
      if(!this.isResponseApiSuccess(response))
        return response.data.faultString  || "Lỗi hệ thống Xin vui long thử lại sau."
      return "ok"
    }
  
    getDataFromResponseApiReturnArray(response) {
      if (!response) return []
      if (response && response.data.errorCode == 0) return response.data.data || []
      return []
    }
  
    getDataFromResponseApiReturnRaise(response) {
      if (!response) return ''
      if (response && response.data.errorCode == 0) return response.data.data
      return ''
    }
  
    isResponseApiSuccess(response) {
      if (!response) return false
      if (response.data && response.data.errorCode == 0) return true
      return false
    }
    getSrtOneBssCodeError(response) {
      if(!this.isResponseApiSuccess(response))
        return response.data.message_detail || response.data.message
      
      return "ok"
    }
    isResponseApiOneBssSuccess(response) {
      if (!response) return false
      if (response && response.data && response.data.error_code === 'BSS-00000000') return true
      return false
    }
  }
  
  export default new apiHelper()
  