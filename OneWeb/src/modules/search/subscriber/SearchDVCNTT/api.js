import axios from 'axios'

export default{
  lay_ds_tinh : async(data) => axios.get('/web-quantri/danhmuc-chung/CSS_TINH',data)
}
