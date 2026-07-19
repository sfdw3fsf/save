
export default {
      
    lay_thongtin_tao_hddt: (axios, data) => axios.get(`/web-ccdv/tram-thicong-dv-cntt/lay_thongtin_tao_hddt`, data),
    
    CapNhatKhachhang: (axios, data) => axios.post(`/tichhop/hddt/CapNhatKhachhang`, data),  
}