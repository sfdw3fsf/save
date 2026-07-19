

class apiHelper {

    getSrtCodeError(response) {
      if(!this.isResponseApiSuccess(response))
        return response.data.faultString  || "Lỗi hệ thống Xin vui long thử lại sau."
      return "ok"
    }
  
    getDataFromResponseApiReturnArray(response) {
      if (!response) return []
      if (response.error_code == "BSS-00000000") return response.data || []
      return []
    }
  
    getDataFromResponseApiReturnRaise(response) {
      if (!response) return 'error'
      if (response && response.error_code == "BSS-00000000") return response.data
      return 'error'
    }
  
    isResponseApiSuccess(response) {
      if (!response) return false
      if (response && response.error_code == "BSS-00000000") return true
      return false
    }
    getSrtOneBssCodeError(response) {
      if(!this.isResponseApiSuccess(response))
        return response.message_detail || response.message
      
      return "ok"
    }
  }
  
  export default new apiHelper()
  