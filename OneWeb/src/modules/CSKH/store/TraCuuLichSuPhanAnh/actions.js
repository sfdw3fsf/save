import API from '@/modules/CSKH/api/TraCuuAPI'
import moment from 'moment'
export const actions = {
    async getLichSuPhanAnh ({ commit, state }, data) {
      console.log(data.pageNo)
      console.log(data.pageSize)
        let postData = {
          ngayTiepNhan: data.ngayTiepNhan,
          denNgay: data.denNgay,
          maKh: data.ma_kh,
        }
        let response = await API.getTraCuuLichSuPhanAnh(this._vm.axios, data.pageNo, data.pageSize, data)
        try {
          if (response.data.error_code === 'BSS-00000000') {
            let tmpData = response.data.data.data || []
            tmpData.forEach(element => {
              element.NGAYTIEPNHAN = moment.utc(element.NGAYTIEPNHAN).format("DD/MM/YYYY HH:mm:ss")
              element.NGAY_GQ = moment.utc(element.NGAY_GQ).format("DD/MM/YYYY HH:mm:ss")
            })
            commit('setDSLichSuPhanAnh', tmpData)
            commit('setTotalItems', response.data.data.totalElement || 0)
          }
          else {
            commit('setDSLichSuPhanAnh', [])
            commit('setTotalItems', 0)
          }
        } catch (error) {
          commit('setDSLichSuPhanAnh', [])
          commit('setTotalItems', 0)
          throw error
        }
        return response
      },
      getObjSearch ({commit, state}, data) {
        commit('setObjSearch', data)
      },
      clearData ({commit, sate}) {
        commit('clearData')
      },
}