export default {
  updateLocDSChuyenKhoan: async (axios, data) => axios.put('web-thuno/api/quan-ly-thu-no/ds-chuyen-khoan', data),
  updateGachNoChuyenKhoan: async (axios, data) => axios.put('web-thuno/api/quan-ly-thu-no/gach-no-chuyen-khoan', data),
  getMaVungCd: async (axios) => axios.get('web-thuno/api/thu-no/common/ma-vung-cd'),
  updateLocDSChuyenKhoanND: async (axios, data) => axios.post('web-thuno/api/quan-ly-thu-no/lay-ds-chuyen-khoan-nd', data),
  layDSChuyenKhoan2: async (axios, data) => axios.post('web-thuno/api/quan-ly-thu-no/lay-ds-chuyen-khoan-2', data)
};
  