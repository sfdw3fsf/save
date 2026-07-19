export default {
  tracuuHDDT: (axios, data) =>
    axios.get(
      "/web-thuno/api/thu-no/hoa-don-dien-tu/tra-cuu-hddt",
      { params: data }
    ),
};
