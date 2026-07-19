import ThietLapThamSoLayPhieuKhaoSatApi from '@/modules/CSKH/api/ThietLapThamSoLayPhieuKhaoSatApi'
import moment from 'moment'
export const actions = {
    async getDsThietLapThoiGian({ commit, state }, data) {
        let response = await ThietLapThamSoLayPhieuKhaoSatApi.getDsThietLapThoiGian(this._vm.axios, data.pageNo, data.pageSize, data)
        let ds = []
        console.log(response)
        if (response.data.error_code === 'BSS-00000000') {
            ds = response.data.data.data || []
            ds.forEach(element => {
                element.TU_GIO = moment(element.TU_GIO).format('hh:mm')
                element.DEN_GIO = moment(element.DEN_GIO).format('hh:mm')
            })
        } else {
            this._vm.$root.toastError(respone.data.message)
        }
        commit('setDsThietLapThoiGian', ds)
    },
    clearData({ commit, state }, data) {
        commit('clearData', data)
    },
    addThietLapThoiGian({ commit, state }, data) {
        commit('addThietLapThoiGianRecord', data)
    },
    removeThietLayThoiGianById({ commit, state }, data) {
        commit('removeThietLayThoiGianRecordById', data)
    },
}