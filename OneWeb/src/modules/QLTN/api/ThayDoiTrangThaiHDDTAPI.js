export default {    
    getHoaDonTheoMaTT: (axios, maTT) => axios.get("/web-thuno/api/thu-no/in-hoa-don/kiem-tra-hop-dong/ma-tt/" + maTT)    
};