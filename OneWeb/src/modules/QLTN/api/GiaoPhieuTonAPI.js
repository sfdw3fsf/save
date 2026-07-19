import qs from "qs"
export default{
    getDSPhieuTon: async (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/hoa-don-ton', { params: data, paramsSerializer: params => {return qs.stringify(params) }}),
    updateGiaoPhieuNVTC: async (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/giao-phieu-nvtc', data),
    getDSLuotGiao: async (axios, data) => axios.post('/web-thuno/api/thu-no/nhan-phieu/lay-danh-sach-luot-giao', data),
    updateHuyGiao: async (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/huy-ds-giao-phieu-nvct', data),
    getBaoCaoDSTon: (axios, data) => axios.get('/web-thuno/api/thu-no/cong-no/bao-cao-in-ds-con-no', { params: data, paramsSerializer: params => {return qs.stringify(params) }}),
    getBaoCaoDSGiao: (axios, data) => axios.get('/web-thuno/api/thu-no/bao-cao/giao-in-bao-cao', { params: data })
  }