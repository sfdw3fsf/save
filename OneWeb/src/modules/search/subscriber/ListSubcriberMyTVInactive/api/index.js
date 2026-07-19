export default{
    mytvUserNotLoginGetV2: (axios, link) => axios.get(link),
    sp_lay_tt_tb_brcd: (axios, data) => axios.post('/web-quantri/mytv-inactive/sp_lay_tt_tb_brcd', data),
}