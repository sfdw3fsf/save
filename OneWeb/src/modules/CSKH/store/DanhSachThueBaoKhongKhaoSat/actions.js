import DanhSachThueBaoKhongKhaoSatApi from '@/modules/CSKH/api/DanhSachThueBaoKhongKhaoSatApi'
export const actions = {
    deleteRecords({ commit, state }, data) {
        console.log('data', data)
        data.forEach(async element => {
            //@fakedata
            let postData = {
                rdsRid: element,
                mayHuy: 'pc_test',
                nguoiHuy: 'nv_test',
                ipHuy: '10.59.90.99'
            }
            let response = await DanhSachThueBaoKhongKhaoSatApi.xoaThueBaoKhongKhoaMo(this._vm.axios, postData)
            if (response.data.error_code === 'BSS-00000000') {
                this._vm.$root.toastSuccess('Xóa dữ liệu thành công!')
            }
            else {
                this._vm.$root.toastError(respone.data.message)
            }
        })
    },
    changeIsCheckStatusAll({ commit, state }) {
        commit('changeIsCheckStatusAll')
    },
    changeIsCheckStatus({ commit, state }, data) {
        commit('changeIsCheckStatus', data.item.RDS_ID)
    },
    async getDSThueBaoKhongKhoaMo({ commit, state }, data) {
        let response = await DanhSachThueBaoKhongKhaoSatApi.getDSThueBaoKhongKhoaMo(this._vm.axios, data.pageNo, data.pageSize, data)
        let ds = []
        if (response.data.error_code === 'BSS-00000000') {
            ds = response.data.data.data || []
        } else {
            this._vm.$root.toastError(respone.data.message)
        }
        commit('setDsThueBaoKhongKhoaMo', ds)
    },
    async getDSThongTinThueBaoNo({ commit, state }, data) {
        let response = await DanhSachThueBaoKhongKhaoSatApi.getDSThongTinThueBaoNo(this._vm.axios, data.pageNo, data.pageSize, data)
        let ds = []
        if (response.data.error_code === 'BSS-00000000') {
            ds = response.data.data.data || []
        } else {
            this._vm.$root.toastError(respone.data.message)
        }
        commit('setDsThongTinThueBaoNo', ds)
    },
    getObjSearch({ commit, state }, data) {
        commit('setObjSearch', data)
    },
    clearData({ commit, sate }) {
        commit('clearData')
    },
}