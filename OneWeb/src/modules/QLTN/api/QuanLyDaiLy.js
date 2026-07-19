export default{
  xoaPhieuNopNH: async (axios, data) => axios.delete('/web-thuno/api/quan-ly-thu-no/dai-ly-nop-ngan-hang/'+data),        
  CapNhatNopNganHang:async (axios, data) => axios.post('/web-thuno/api/quan-ly-thu-no/dai-ly-nop-ngan-hang',data),        
  CapNhatChiTietHD:async (axios, data) => axios.post('/web-thuno/api/thu-no/quan-ly-dai-ly/ds-dai-ly-nop-tien-hop-dong',data),
  xoaChiTietNopNH: async (axios, data) => axios.delete('/web-thuno/api/thu-no/quan-ly-dai-ly/nop-ngan-hang-tien-hd?'+data)      
  
}
