export default {
    TRACUU_HD_THEO_DK: (axios, data) => axios.get(`/web-qlvt/api/gia-han-hop-dong/hop-dong-theo-don-hang-dang-ky?hdms-id=${data.hdmsId}`),
    LAY_LICHSU_GIAHAN_HD: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-hop-dong-don-hang/lich-su-gia-han/${data.hdmsId}`),
    giahan_hdms: (axios, data) => axios.post(`/web-qlvt/api/gia-han-hop-dong/thuc-hien-gian-han`,data),
  }