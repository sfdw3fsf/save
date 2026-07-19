export default {
  getDSCongDoan : (axios, data) => axios.post('/web-khdn/quanlykenh/get_list_congdoan', data),
  getDSKenhBan : (axios, data) => axios.post('/web-khdn/quanlykenh/get_list_nhomldv', data),
  getDSNhomCongDoan : (axios, data) => axios.post('/web-khdn/quanlykenh/get_list_nhomcongdoan', data),
  insertCongDoan : (axios, data) => axios.post('/web-khdn/quanlykenh/insert_congdoan', data),
  deleteCongDoan : (axios, data) => axios.post('/web-khdn/quanlykenh/delete_congdoan', data),
  updateCongDoan : (axios, data) => axios.post('/web-khdn/quanlykenh/update_congdoan', data),
  getDSDoiTuongBan: (axios, data) => axios.post('/web-khdn/kenhban/dsDoiTuongBan', data),
}