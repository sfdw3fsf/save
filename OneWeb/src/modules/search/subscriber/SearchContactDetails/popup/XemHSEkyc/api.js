import axios from 'axios'
export default {
  lay_ds_hoso_ekyc_danhba: (data, header) => axios.post('/web-thicong/dvcntt/lay_ds_hoso_ekyc_danhba', data, header),
  tai_file_ekyc: (data) => axios.post( '/tichhop/cntt/SmartCA/DownFileEKYC_V2', data)
}
