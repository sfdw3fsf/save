import axios from "axios"
export default {
    async getDs(parent, path, defaultValue){
        try{
            parent.loading(true)
            let response = await axios.get(path)
            parent.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
            }else{
                return defaultValue
            }
        }catch(e){
            parent.loading(false)
            return defaultValue
        }
    },
    async postDs(parent, path, data, defaultValue){
        try{
            parent.loading(true)
            let response = await axios.post(path, data)
            parent.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
            }else{
                return defaultValue
            }
        }catch(e){
            parent.loading(false)
            return defaultValue
        }
    },
    async getDsChuKyCNRR(parent, data){
        return await this.postDs(parent, 'web-thicong/chukicnrr/lay_ds_chuki_cnrr', data, [])
    },
    async getDsMucCuocCNRR(parent, data){
        return await this.postDs(parent, 'web-thicong/chukicnrr/sp_laymucuoc_cnrr', data, [])
    },
    async getThangBDTC_Theo_MaTT(parent, data){
        return await this.postDs(parent, 'web-thicong/chukicnrr/lay_thang_bdtc_theo_mtt', data, [])
    },

    
    async insChukyCNRR(parent, data){
        try{
            parent.loading(true)
            let response = await axios.post('web-thicong/chukicnrr/ins_chuky_cnrr', data)
            parent.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                return response.data.data
            }else{
                if(response&&response.data&&response.data.message){
                    return response.data.message
                }else{
                    return 'Xảy ra lỗi'
                }
            }
        }catch(e){
            parent.loading(false)
            if(e.data&&e.data.message){
                return e.data.message
            }else if(e.response&&e.response.data&&e.response.data.message){
                return e.response.data.message
            }else{
                return 'Xảy ra lỗi'
            }
        }
    },
    async capNhatChukyCNRR(parent, data){
        try{
            parent.loading(true)
            let response = await axios.post('web-thicong/chukicnrr/capnhat_chuky_cnrr', data)
            parent.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                return response.data.data
            }else{
                if(response&&response.data&&response.data.message){
                    return response.data.message
                }else{
                    return 'Xảy ra lỗi'
                }
            }
        }catch(e){
            parent.loading(false)
            if(e.data&&e.data.message){
                return e.data.message
            }else if(e.response&&e.response.data&&e.response.data.message){
                return e.response.data.message
            }else{
                return 'Xảy ra lỗi'
            }
        }
    },
    async xoaChukyCNRR(parent, data){
        try{
            parent.loading(true)
            let response = await axios.post('web-thicong/chukicnrr/xoa_chuky_cnrr', data)
            parent.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                return response.data.data
            }else{
                if(response&&response.data&&response.data.message){
                    return response.data.message
                }else{
                    return 'Xảy ra lỗi'
                }
            }
        }catch(e){
            parent.loading(false)
            if(e.data&&e.data.message){
                return e.data.message
            }else if(e.response&&e.response.data&&e.response.data.message){
                return e.response.data.message
            }else{
                return 'Xảy ra lỗi'
            }
        }
    },
    async importChukyCNRR(parent, data){
        try{
            parent.loading(true)
            let response = await axios.post('web-thicong/chukicnrr/import_chuky_cnrr', data)
            parent.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                return response.data.data
            }else{
                if(response&&response.data&&response.data.message){
                    return response.data.message
                }else{
                    return 'Xảy ra lỗi'
                }
            }
        }catch(e){
            parent.loading(false)
            if(e.data&&e.data.message){
                return e.data.message
            }else if(e.response&&e.response.data&&e.response.data.message){
                return e.response.data.message
            }else{
                return 'Xảy ra lỗi'
            }
        }
    },
}