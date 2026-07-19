export default{
    ts_tracuu_tonghop_hd_db: async (axios) => axios.post('/ccbs/tracuu/ts_tracuu_tonghop_hd_db'),
    ts_tracuu_tonghop_kh: async (axios) => axios.post('/ccbs/tracuu/ts_tracuu_tonghop_kh'),
    ts_tracuu_tonghop_tt: async (axios,data) => axios.post('/ccbs/executor/ts_tracuu_tonghop_tt',data),
    ts_tracuu_tonghop: async (axios,data) => axios.post('/ccbs/tracuu/ts_tracuu_tonghop',data)
}