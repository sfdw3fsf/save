import axios from 'axios'
export default {
    cntt_dynamic_api: async(axios, data) => axios.post(`web-thicong/cnttdynamic/api`, data),
    lay_ds_nghiepvu_cntt_common : async(axios, data) => axios.post(`web-thicong/dvcntt/lay_ds_nghiepvu_cntt_common`, data),
}
