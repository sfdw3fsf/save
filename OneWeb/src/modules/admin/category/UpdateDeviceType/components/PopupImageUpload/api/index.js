export default {
  // API
  upload: (axios, data) => axios.post('/web-quantri/upload', data, { headers: { 'Content-Type': 'multipart/form-data' } }),
  link_view_file: (axios, data) => axios.post('/web-quantri/upload/link_view_file', data)
}
