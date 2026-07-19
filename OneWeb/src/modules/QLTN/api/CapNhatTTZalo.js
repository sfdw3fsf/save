export default {
    getIDZalo: async (axios, data) => axios.get('web-thuno/api/thu-no/quan-ly-dai-ly/id-zalo/' + data),
    kiemtraCNZalo: async (axios, data) => axios.get('web-thuno/api/thu-no/quan-ly-dai-ly/kiem-tra-cap-nhat-zalo/' + data),
    getLichSuCN: async (axios, data) => axios.get('web-thuno/api/thu-no/quan-ly-dai-ly/lich-su-cap-nhat-zalo/' + data),
    capNhatZalo: async (axios, data) => axios.put('web-thuno/api/thu-no/quan-ly-dai-ly/cap-nhat-tt-zalo-di-dong-tra-truoc', data),
  }
