
export default {
    lay_ds_chitiet_km_by_id: async (axios, data) => axios.get(`/web-tracuu/danhba/lay_ds_chitiet_km_by_id?thuebaoId=${data.thuebaoId}&kieu=${data.kieu}`, data),
}