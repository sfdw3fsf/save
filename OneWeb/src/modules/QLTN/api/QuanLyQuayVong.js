export default {
    getDSHuongGiao: (axios) => axios.get('/web-thuno/api/thu-no/common/ds-huong-giao-quay-vong'),
    getDSLyDo: (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/ds-nhom-ly-do-tra-truoc', {params: data}),
    getDSPhieuNVTC: (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/ds-quay-vong-giao-nvtc', {params: data}),
    getDSPhieuChuaThu: (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/ds-quay-vong-khong-thu-duoc', {params: data}),
    taoHD_QuayVong_TraTruoc: (axios, data) => axios.post('/web-thuno/api/thu-no/quan-ly-dai-ly/hop-dong-quay-vong', data),
    quayVong_GiaoPhieu: (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/quay-vong-giao-phieu', data),
    quayVong_HuyGiao: (axios, data) => axios.delete('/web-thuno/api/thu-no/quan-ly-dai-ly/huy-quay-vong/phieu/' + data),
    hoanCong_QuayVong: (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/hoan-cong-quay-vong', data),
    dieuHong_QuayVong: (axios, data) => axios.post('/web-thuno/api/thu-no/quan-ly-dai-ly/dieu-hong-tu-quay-vong', data),
    capNhat_QuayVong: (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/cap-nhat-quay-vong', data),
  }
