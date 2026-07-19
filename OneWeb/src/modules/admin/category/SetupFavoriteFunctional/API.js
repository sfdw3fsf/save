export default {
  getMenuItems: (axios,data) => axios.get(`/quantri/user/khoitao_ungdung?p_idmodule=${data}`,{headers: {'Content-Type': 'application/json'}}),
  updateFav: (axios,data) => axios.post(`/quantri/user/update_ds_chucnang_yeuthich`,data),
}