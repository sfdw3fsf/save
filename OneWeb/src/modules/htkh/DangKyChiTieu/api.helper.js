import api from "./api"


class apiHelper {

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



    
   calculateData(dtpToDate, dtpFromDate){

    if(dtpToDate < dtpFromDate) return 0;
    const difference = Math.abs(dtpToDate - dtpFromDate);
    const daysDifference = Math.ceil(difference / (1000 * 60 * 60 * 24));
      return daysDifference 
      
    }

   
  }
  
  export default new apiHelper()
  