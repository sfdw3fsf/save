export default {
  sp_lay_user_ipcc_theo_nguoidung_id: async (axios, nguoidung_id) => axios.get(`/web-tracuu/tracuu/sp_lay_user_ipcc_theo_nguoidung_id?nguoidung_id=${nguoidung_id}`),
  sp_capnhat_cuocgoi_ipcc: async (axios, input) => axios.post(`/web-bancheo/tiepnhan_cuocgoi_tongdai/sp_capnhat_cuocgoi_ipcc`, input),
  sp_batdau_cuocgoi_ipcc: async (axios, input) => axios.post(`/web-bancheo/tiepnhan_cuocgoi_tongdai/sp_batdau_cuocgoi_ipcc`, input),
  sp_ketthuc_cuocgoi_ipcc: async (axios, input) => axios.post(`/web-bancheo/tiepnhan_cuocgoi_tongdai/sp_ketthuc_cuocgoi_ipcc`, input),
  sp_lay_ds_cuocgoi: async (axios, input) => axios.post(`/web-bancheo/tiepnhan_cuocgoi_tongdai/lay_ds_cuocgoi`, input),
  changeAgentState: (axios, data) => axios.post('/tichhop/ipcc/changeAgentState', data),
}
