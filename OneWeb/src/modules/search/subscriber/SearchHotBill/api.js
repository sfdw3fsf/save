export default {
    lay_matt_neo_vnp: (axios, data) => axios.post(`/web-tracuu/tracuu-cuocnong/lay_matt_neo_vnp`, data),
    ///executor/ts_danhmuc_tracuu_cuocnong
    ts_danhmuc_tracuu_cuocnong: (axios, data) => axios.post(`/ccbs/executor/ts_danhmuc_tracuu_cuocnong`, data),
}