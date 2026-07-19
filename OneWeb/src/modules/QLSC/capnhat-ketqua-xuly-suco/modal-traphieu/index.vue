<template src="./index.template.html"></template>
<script>
export default {
    props:{
        modalId: String,
        phieuId: Number
    },
    data() {
        return {
            dsLyDo: [],
            lyDoId: null,
            noiDung: null
        }
    },
    methods: {
        onModalHidden() {
            this.dsLyDo = []
            this.lyDoId = null
            this.noiDung = null
        },
        async onModalShown() {
            try {
                this.loading(true)
                await this.layDsLyDo()
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (this.dsLyDo.length > 0) {
                this.lyDoId = this.dsLyDo[0].LYDOTRA_ID
            }
        },
        async onClickBtnTraPhieu() {

            let data = {
                phieu_id: this.phieuId,
                lydotra_id: this.lyDoId,
                noi_dung: this.noiDung
            }

            try {
                this.loading(true)
                await this.traPhieu(data)
            } catch (err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.$root.toastSuccess('Trả phiếu thành công!')
            this.$emit('success')
            this.$bvModal.hide(this.modalId)
        },
        async layDsLyDo() {
            try {
                let response = await this.$root.context.get(`/web-suco/danhmuc/ds-lydo-traphieu`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsLyDo = response.data
                } else {
                    this.dsLyDo = []
                }
            } catch (err) {
                this.dsLyDo = []
            }
        },
        async traPhieu(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/ketqua-xuly-suco/traphieu-suco`, data)
                
                if (response.error_code !== 'BSS-00000000') {
                    throw {response: { data: response}}
                }
            } catch (err) {
                let responseData = err.response.data
                let message = [] 

                if (responseData.message) {
                    message.push(responseData.message)
                }

                if (responseData.message_detail) {
                    message.push(responseData.message_detail)
                }

                if (responseData.data) {
                    let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                    message.push([...new Set(detailMessage)].join('\n'))
                }

                throw message.join('\n')
            }
        },
    }
}
</script>