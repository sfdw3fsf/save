import API from '../../api/GanDinhDangChoBanKe'

export const actions = {
    async getdsVersion({commit, state}) {
        let response = await API.getDanhSachVersion(this._vm.axios)
        try {
            commit("setDanhSachVersion", response.data.data)
        } catch (error) {
            throw error
        }
    },
    async getdsInv({commit, state}, data) {
        let response = await API.getDanhSachInv(this._vm.axios, data)
        try {
            commit("setDanhSachInv", response.data.data)
        } catch (error) {
            throw error
        }
    },
    async getdsGroup({commit, state}, data) {
        let response = await API.getDanhSachGroup(this._vm.axios, data)
        try {
            commit("setDanhSachGroup", response.data.data)
        } catch (error) {
            throw error
        }
    },
    async getdsFormat({commit, state}) {
        let response = await API.getDanhSachFormat(this._vm.axios)
        try {
            commit("setDanhSachFormat", response.data.data)
        } catch (error) {
            throw error
        }
    },
    async getdsDataLayout({commit, state}, data) {
        let response
        if (data.groupId === null) {
            response = await API.getDanhSachMasterLayout(this._vm.axios, data)
        } else {
            response = await API.getDanhSachDataLayout(this._vm.axios, data)
        }
        // let response = await API.getDanhSachDataLayout(this._vm.axios, data)
        let ds = []
        response.data.data.forEach(element => {
            let display = null
            state.dsFormat.forEach(item => {
                if (element.FORMAT_ID === item.FORMAT_ID) {
                    display = item.DISPLAY
                }
            })
            ds.push({
                FORMAT_ID: element.FORMAT_ID,
                DISPLAY: display,
                VER_ID: element.VER_ID,
                INV_ID: element.INV_ID,
                GROUP_ID: element.GROUP_ID,
                NOTE: element.NOTE,
                BINDING_VALUE: element.BINDING_VALUE
            })
        })
        try {
            commit("setDanhSachDataLayout", ds)
        } catch (error) {
            throw error
        }
    }
}