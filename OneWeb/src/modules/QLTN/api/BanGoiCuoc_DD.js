export default {
    kiemTraDuLieu: async (axios, data) => axios.post('web-thuno/api/thu-no/quan-ly-dai-ly/kiem-tra-du-lieu-ket-qua-ban-goi-di-dong', data),
    capNhatDL: async (axios, data) => axios.put('web-thuno/api/thu-no/quan-ly-dai-ly/du-lieu-ket-qua-ban-goi-di-dong', data),
  }
