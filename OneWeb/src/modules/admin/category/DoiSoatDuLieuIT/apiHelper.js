
import _ from 'lodash'
import API from "./API";

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

   async handleGetDataDoiSoatWithBatch(axios, listCity,{p_dsloaitb_id ,p_trangthai_xl, p_bancheo} ){
    if(!listCity || listCity.length === 0) return []

    const batchSize = 6 // limit browser: 6 request at a time
    
    let result = [];
    for (let index = 0; index < listCity.length; index++) {
        const p_phanvung_id = listCity[index];
        const param = { p_phanvung_id, p_dsloaitb_id, p_trangthai_xl, p_bancheo };
        // const request = API.sp_lay_ds_tb_doisoat_vnpt_it(axios, param)
        result.push(param)
    }
    const numTransactions = Math.ceil(result.length / batchSize);
    return this.processBulk(axios, result, batchSize)
   }
   handleGetDataFromBatchResponse(responses){
        let result = []
        for (let i = 0; i < responses.length; i++) {
            // console.log("responses", responses[i]);
            const response = responses[i];
            const data = this.getDataFromResponseApiReturnArray(response)
            result =  _.concat(result, data);
        }
        
        return result;
   }

   async processBatch(axios, batchParams) {
 
   return new Promise((resolve, reject) => {
      let batch = []
      for (let i = 0; i < batchParams.length; i++) {
        let request = API.sp_lay_ds_tb_doisoat_vnpt_it(axios, batchParams[i])
        batch.push(request);
      }
      Promise.all(batch).then((res) => {
        resolve(res);
      }).catch((err) => {
        reject(err);
      });
    }) 
}

async  processBulk(axios, data, batchSize) {
  return new Promise((resolve, reject) => {
      let results = [];
      const totalBatches = Math.ceil(data.length / batchSize);
      let count = 0;
      for (let i = 0; i < totalBatches; i++) {
        const batch = data.slice(i * batchSize, (i + 1) * batchSize);
      
        this.processBatch(axios, batch).then((res) => {
          results = _.concat(results, this.handleGetDataFromBatchResponse(res));

          count ++
          if (count === totalBatches) {
            console.log('results', results.length);
            resolve(results);
          }
        }).catch((err) => {
          reject(err);
        });
      }
    }
  );
}


  }
  
  export default new apiHelper()
  