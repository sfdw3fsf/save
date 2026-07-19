export default {
    //Lấy danh mục
    getDanhmuc: (axios, data) => axios.post('/web-thicong/tracnghiem-atvsld/danhmuc', data),
    
    //web-hopdong/ds-doi-tam-dung-thanh-ly/lay_ds_cho_td_thanhly
    lay_ds_cho_td_thanhly: (axios, data) => axios.post('/web-hopdong/ds-doi-tam-dung-thanh-ly/lay_ds_cho_td_thanhly', data),
    /*
    {
  "vdenngay": "string",
  "vdonvi_id": 0,
  "vphanvung_id": 0,
  "vtungay": "string"
}
    */
}
  