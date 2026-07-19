import QuanLyTaiSanApi from '../../api/QuanLyTaiSanApi'

var apiManager;

export const actions = {
    createApiManager () {
        apiManager = QuanLyTaiSanApi(this._vm.$bssApiCaller)
    },
    clearState ({ commit }) {
        commit('clearState')
    },
    async layDsDuAn ({ commit }) {
        try {
            let response = await apiManager.layDsDuAn()

            if (response.data.error_code === 'BSS-00000000' && response.data.data != null) {
                commit('setDsDuAn', response.data.data)
            } else {
                commit('setDsDuAn', [])
            }
        } catch (err) {
            commit('setDsDuAn', [])
        }
    },
    async layDsTTVT ({ commit }) {
        try {
            let response = await apiManager.layDsTTVT()

            if (response.data.error_code === 'BSS-00000000' && response.data.data != null) {
                commit('setDsTTVT', response.data.data)
            } else {
                commit('setDsTTVT', [])
            }
        } catch (err) {
            commit('setDsTTVT', [])
        }
    },
    async layDsTaiSanTheoDuAn ({ commit }, duAnId) {
        try {
            let response = await apiManager.layDsTaiSanTheoDuAn(duAnId)

            if (response.data.error_code === 'BSS-00000000' && response.data.data != null) {
                commit('setDsTaiSan', response.data.data)
            } else {
                commit('setDsTaiSan', [])
            }
        } catch (err) {
            commit('setDsTaiSan', [])
        }
    },
    async layDsTaiSanTheoMaTaiSan ({ commit }, maTaiSan) {
        try {
            let response = await apiManager.layDsTaiSanTheoMaTaiSan(maTaiSan)

            if (response.data.error_code === 'BSS-00000000' && response.data.data != null) {
                commit('setDsTaiSanTimKiem', response.data.data)
            } else {
                commit('setDsTaiSanTimKiem', [])
            }
        } catch (err) {
            commit('setDsTaiSanTimKiem', [])
        }
    },
    async themTaiSan ({ commit }, data) {
        try {
            let response = await apiManager.themTaiSan(data)
            
            if (response.data.error_code !== 'BSS-00000000') {
                throw response
            }
        } catch (err) {
            let responseData = err.response.data
            let message = responseData.message

            if (responseData.message_detail) {
                message += '\n' + responseData.message_detail
            }

            if (responseData.data) {
                let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                message = message + '\n' +  [...new Set(detailMessage)].join('\n')
            }

            throw message
        }
    },
    async capNhatTaiSan ({ commit }, data) {
        try {
            let response = await apiManager.capNhatTaiSan(data)
            
            if (response.data.error_code !== 'BSS-00000000') {
                throw response
            }
        } catch (err) {
            let responseData = err.response.data
            let message = responseData.message

            if (responseData.message_detail) {
               message += '\n' + responseData.message_detail
            }

            if (responseData.data) {
                let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                message = message + '\n' +  [...new Set(detailMessage)].join('\n')
            }

            throw message
        }
    },
    async chuyenDuAn ({ commit }, data) {
        try {
            let response = await apiManager.chuyenDuAn(data)
            
            if (response.data.error_code !== 'BSS-00000000') {
                throw response
            }
        } catch (err) {
            let responseData = err.response.data
            let message = responseData.message

            if (responseData.message_detail) {
                message += '\n' + responseData.message_detail
            }

            if (responseData.data) {
                let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                message = message + '\n' +  [...new Set(detailMessage)].join('\n')
            }

            throw message
        }
    },
    async xoaTaiSan ({ commit }, taiSanId) { 
        try {
            let response = await apiManager.xoaTaiSan(taiSanId)
            
            if (response.data.error_code !== 'BSS-00000000') {
                throw response
            }
        } catch (err) {
            throw err.response.data.message + '\n' + err.response.data.message_detail
        }
    }
}


