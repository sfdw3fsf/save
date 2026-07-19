export default {
  // API for CRUD Attribute of IDC

    getThuocTinhIdc: async (axios, data) => axios.post(`web-ecms/thuoc-tinh/get`, data),

  addThuocTinhIdc: async (axios, data) => axios.post(`web-ecms/thuoc-tinh/create`, data),

  updateThuocTinhIdc: async (axios, thuocTinhId, data) =>
    axios.post(`web-ecms/thuoc-tinh/update?thuocTinhId=${thuocTinhId}`, data),

  deleteThuocTinhIdc: async (axios, thuocTinhId) => axios.post(`web-ecms/thuoc-tinh/delete?thuocTinhId=${thuocTinhId}`)
}
