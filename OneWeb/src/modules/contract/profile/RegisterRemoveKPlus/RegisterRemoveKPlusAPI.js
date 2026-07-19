export default{    
    get_subscriberinfo: async (axios, data) => axios.post('/tichhop/vasc/getSubscriberInfo', data),
    get_getStatusBlockPayTV: async (axios, data) => axios.post('/tichhop/vasc/getStatusBlockPayTV', data),   
    post_registkplus: async (axios, data) => axios.post('/tichhop/vasc/registKplus', data),    
    
    /*
    "iptvaccount": "hpgmytv048744"
    {
      "kplusDTO": {
        "method_FLG": 0,
        "price": 0,
        "reason": "string",
        "subpack": "string",
        "username": "string"
      }
    }
    */
    
  }