import QuanLyPrefixIPCuaBrasApi from '../../api/QuanLyPrefixIPCuaBrasApi'

var apiManager;

export const actions = {
    createApiManager () {
        apiManager = QuanLyPrefixIPCuaBrasApi(this._vm.$bssApiCaller)
    },
    async layDsPrefixIp ({ commit }) {
        try {
            let response = await apiManager.layDsPrefixIp()
            commit('setDsPrefixIp', response.data.data != null ? response.data.data : [])
        } catch (err) {
            throw err
        }
    },
    async layDsLoaiTbDaGan ({ commit }, prefix) {
        try {
            let response = await apiManager.layDsLoaiTbDaGan({ prefix: prefix })    
            commit('setDsLoaiTbDaGan', response.data.data != null ? response.data.data : [])
        } catch (err) {
            throw err
        }
    },
    setDsLoaiTbDaGan ({ commit }, data) {
        commit('setDsLoaiTbDaGan', data)
    },
    async layDsLoaiTbChuaGan ({ commit }, data) {
        try {
            let response = await apiManager.layDsLoaiTbChuaGan(data)
            commit('setDsLoaiTbChuaGan', response.data.data != null ? response.data.data : [])
        } catch (err) {
            throw err
        }
    },
    setDsLoaiTbChuaGan ({ commit }, data) {
        commit('setDsLoaiTbChuaGan', data)
    },
    async layDsIp ({ commit }, prefix) {
        try {
            let response = await apiManager.layDsIp({ prefix: prefix })
            commit('setDsIp', response.data.data != null ? response.data.data : [])
        } catch (err) {
            throw err
        }
    },
    setDsIp ({ commit }, data) {
        commit('setDsIp', data)
    },
    async layDsBras ({ commit }) {
        try {
            let response = await apiManager.layDsBras()
            commit('setDsBras', response.data.data != null ? response.data.data : [])
        } catch (err) {
            throw err
        }
    },
    async themMoi ({ commit }, data) {
        try {
            let response = await apiManager.themMoi(data)
            
            if (response.data.error_code === 'BSS-00000000') {
                return 'Cập nhật thành công'
            }

            throw response
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
    async capNhat ({ commit }, data) {
        try {
            let response = await apiManager.capNhat(data)
            
            if (response.data.error_code === 'BSS-00000000') {
                return 'Cập nhật thành công'
            }

            throw response
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
    async kiemTraPrefix ({ commit }, data) { 
        try {
            let response = await apiManager.kiemTraPrefix(data)
            
            if (response.data.error_code !== 'BSS-00000000') {
                throw response
            }
        } catch (err) {
            throw err.response.data.message + '\n' + err.response.data.message_detail
        }
    },
    async xoaPrefix ({ commit }, data) { 
        try {
            let response = await apiManager.xoaPrefix(data)
            
            if (response.data.error_code !== 'BSS-00000000') {
                throw response
            }
        } catch (err) {
            throw err.response.data.message + '\n' + err.response.data.message_detail
        }
    },
    async sinhIp ({ commit }, data) { 
        try {
            let response = await apiManager.sinhIp(data)
            
            if (response.data.error_code !== 'BSS-00000000') {
                throw response
            }
        } catch (err) {
            throw err.response.data.message + '\n' + err.response.data.message_detail
        }
    },
    async layDsDichVuVt ({ commit }) {
        try {
            let response = await apiManager.layDsDichVuVt()
            commit('setDsDichVuVt', response.data.data != null ? response.data.data : [])
        } catch (err) {
            throw err
        }
    },
    async layDsLoaiIp ({ commit }) {
        try {
            let response = await apiManager.layDsLoaiIp()
            commit('setDsLoaiIp', response.data.data != null ? response.data.data : [])
        } catch (err) {
            throw err
        }
    },
    clearState ({ commit }) {
        commit('clearState')
    }
}