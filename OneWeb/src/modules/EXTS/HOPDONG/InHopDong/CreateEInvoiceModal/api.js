export default {
    getMauHopDong: (axios, {typeSubID, typeReqID} )=> {
        return axios.get(`/tichhop/eContract/TraCuuDanhSachHopDongMau?typeSubID=${typeSubID}&typeReqID=${typeReqID}`)
    },
    getInputHopDong: (axios, hdkh_id )=> {
        return axios.get(`web-tracuu/tracuu/sp_laythongtin_hopdong_econtract?hdkh_id=${hdkh_id}`)
    },
    createEContract: (axios, {hdkh_id, template_group_id} )=>{
        return axios.get(`/web-hopdong/eContract/TaoHopDong?hdkh_id=${hdkh_id}&template_group_id=${template_group_id}`)
    }
}