export default {
    async uploadFile(ctx, data) {
        try{
            var rs = await ctx.$root.context.post("/web-suco/storage/uploadFile", data)

            if(rs.data != null && rs.data.status)  {
                return rs.data.message
            }
            throw ''
        } catch(e) {
            throw 'Có lỗi xảy ra trong quá trình upload file'
        }
    },
    async getFileUrl(ctx, src) {
        try{
            var rs = await ctx.$root.context.post(`/web-suco/storage/getPresignedUrl`, {fileSource: src})
            if(rs.data != null)  {
                return rs.data.result
            }
            throw ''
        } catch(err) {
            throw 'Có lỗi xảy ra trong quá trình lấy đường dẫn file'
        }
    },
    async deleteFile(src) {
        try{
            var rs = await ctx.$root.context.post(`/web-suco/storage/deleteFile`, {fileSource: src})
            if(rs.data != null && rs.data.status)  {
                return
            }
            throw ''
        } catch(err) {
            throw 'Có lỗi xảy ra trong quá trình xóa file'
        }
    },
}