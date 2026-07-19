import axios from 'axios'

export default {
    async uploadImage (formData) {
        try {
            // post as a multipart/form-data
            const rs = await axios.post('/web-ecms/storage/uploadFile', formData)
            
            if(rs.data != null)  {
                return rs.data.data
            }
        } catch (error) {
            console.log(error)
            throw 'Có lỗi xảy ra trong quá trình upload ảnh'
        }
    },

    async getPresignedUrlFromMinio(src) {
        try {
            const rs = await axios.post('/web-ecms/storage/getPresignedUrl', { fileSource: src })
                
            if (rs.data && rs.data.error_code === 'BSS-00000000' && rs.data.data != undefined) {
                return rs.data.data.result
            } else return null
        } catch (error) {
            console.log(error)
            return null
        }
    },
}