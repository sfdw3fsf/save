import axios from 'axios'

export default {
  getList: () => axios.get('/web-quantri/danhmuc-chung/CSS_LINHVUC').then(rs => rs.data.data || []),
  create: (name) => axios.get(`/web-quantri/danhmuc-chung/CSS_LINHVUC_NHAP?linhVucId=0&linhVuc=${name}`),
  update: (id, name) => axios.get(`/web-quantri/danhmuc-chung/CSS_LINHVUC_NHAP?linhVucId=${id}&linhVuc=${name}`),
  delete: (id) => axios.get(`/web-quantri/danhmuc-chung/CSS_LINHVUC_XOA?linhVucId=${id}`)
}
