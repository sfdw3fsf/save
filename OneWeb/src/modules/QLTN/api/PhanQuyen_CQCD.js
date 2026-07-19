export default{
    getDanhSachCQ: async (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/ds-dich-chuyen-chuyen-quyen', {params:data}),
    capNhatCQ:async (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/cap-nhat-thue-bao-dich-chuyen-chuyen-quyen',data),
  }
