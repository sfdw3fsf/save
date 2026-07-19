export default{
    
  quan: async (axios, data) => axios.get('/web-quantri/danhmuc/diaban/quan'),
  phuong: async (axios, data) => axios.post('/web-quantri/danhmuc/diaban/phuong', data),
  nhom_pho: async (axios, data) => axios.get('/web-quantri/danhmuc/diaban/nhom-pho'),
  pho_theo_quanphuong: async (axios, data) => axios.post('/web-quantri/danhmuc/diaban/pho-theo-quanphuong', data),
  




  lay_ds_trangbi: async (axios, data) => axios.post('/web-quantri/bang-gia/sp_lay_ds_trangbi_theo_banggia', data),
  lay_ds_loaitb: async (axios, data) => axios.post('/web-quantri/goi-cuoc/bang-gia/sp_lay_ds_loaitb_theo_banggia', data),
    


  }
  