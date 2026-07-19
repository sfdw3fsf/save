import API from "../../api/ChuongTrinhCSKH"
import moment from 'moment'

export const actions = {
    async getDSChuongTrinhCSKH ({commit, state}, data) {
        let response = await API.getDSChuongtrinhCSKH(this._vm.axios, data.pageNo, data.pageSize)
        let ds = []
        // let page = {}
        if (response.data.error_code === 'BSS-00000000') {
            if (response.data.data !== undefined) {
                response.data.data.data.forEach((element, index) => {
                    ds.push({
                        INDEX: index+1,
                        ID: element.CTCSKH_ID,
                        MA_CT: element.MA_CT,
                        TEN_CT: element.TEN_CT,
                        NGAY_BD: element.NGAY_BD,
                        NGAY_KT: element.NGAY_KT,
                        NgayBD: element.NGAY_BD ? moment(element.NGAY_BD).format('DD/MM/YYYY') : "",
                        NgayKT: element.NGAY_KT ? moment(element.NGAY_KT).format('DD/MM/YYYY') : "",
                        TRANG_THAI: element.TRANGTHAI
                    })
                })
                // page = {
                //     page: response.data.data.page,
                //     maxSize: response.data.data.maxSize,
                //     totalElement: response.data.data.totalElement,
                //     totalPages: response.data.data.totalPages,
                //     sort: response.data.data.sort,
                //     propertiesSort: response.data.data.propertiesSort
                // }
            } else {
                this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
            }
        } else {
            this._vm.$toast.error(response.data.message)
        }
        commit('setDSChuongTrinhCSKH', ds)
    },
    async deleteChuongTrinhCSKH({commit, state}, data) {
        console.log('data', data)
        let response = await API.XoaChuongTrinhCSKH(this._vm.axios, data)
        console.log(response)
        if (response.data.error_code === 'BSS-00000000') {
            this._vm.$toast.success('Xóa chương trình thành công!')
        }
        else {
            this._vm.$toast.error(respone.data.message)
        }
    },
}