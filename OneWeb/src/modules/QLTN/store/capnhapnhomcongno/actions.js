import API from '../../api/CapNhapNhomCongNo'
import moment from "moment"

export const actions = {
    async getDanhSachNhomCongNo ({commit, state}) {
        let response = await API.getDanhSachNhomCongNo(this._vm.axios)
        let ds = []
        if (response.data.error_code === "BSS-00000000") {
            if (response.data.data !== undefined) {
                response.data.data.forEach(element => {
                    ds.push({
                        STT: element.STT,
                        NHOMCNO_ID: element.NHOMCNO_ID,
                        TEN_NHOM: element.TEN_NHOM,
                        MAY_CN: element.MAY_CN,
                        NGUOI_CN: element.NGUOI_CN,
                        NGAY_CN: element.NGAY_CN
                    })
                })
            }
        }
        try {
            commit("setDanhSachNhomCongNo", ds)
        } catch (error) {
            throw error
        }
        return response
    },
    async capNhapNhomCongNo ({commit, state}, data) {
        await API.capNhapNhomCongNo(this._vm.axios, data).then(response => {
            if (response.data.error_code === "BSS-00000000") {
                this._vm.$toast.success(response.data.data)
                let ds = []
                API.getDanhSachNhomCongNo(this._vm.axios).then(response => {
                    response.data.data.forEach(element => {
                        ds.push({
                            STT: element.STT,
                            NHOMCNO_ID: element.NHOMCNO_ID,
                            TEN_NHOM: element.TEN_NHOM,
                            MAY_CN: element.MAY_CN,
                            NGUOI_CN: element.NGUOI_CN,
                            NGAY_CN: moment(element.NGAY_CN).format('DD/MM/YYYY')
                        })
                    })
                    commit("setDanhSachNhomCongNo", ds)
                })
            }
        }).catch(error => {
            this._vm.$toast.error(error.data.message)
        })    
    },
    async xoaNhomCongNo ({commit, state}, data) {
        await API.xoaNhomCongNo(this._vm.axios, data).then(response => {
            if (response.data.error_code === "BSS-00000000") {
                this._vm.$toast.success(response.data.data)
                let ds = []
                API.getDanhSachNhomCongNo(this._vm.axios).then(response => {
                    response.data.data.forEach(element => {
                        ds.push({
                            STT: element.STT,
                            NHOMCNO_ID: element.NHOMCNO_ID,
                            TEN_NHOM: element.TEN_NHOM,
                            MAY_CN: element.MAY_CN,
                            NGUOI_CN: element.NGUOI_CN,
                            NGAY_CN: moment(element.NGAY_CN).format('DD/MM/YYYY')
                        })
                    })
                    commit("setDanhSachNhomCongNo", ds)
                })
            }
        }).catch(error => {
            this._vm.$toast.error(error.data.message_detail)
        })  
    },
}