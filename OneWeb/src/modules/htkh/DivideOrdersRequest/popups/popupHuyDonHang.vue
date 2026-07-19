<template src="./popupHuyDonHang.html"></template>

<script>
import apiHelper from '../api.helper'
import api from '../api'
export default {
    data() {
        return {
            phieu_ids: []
        }
    },
    methods: {
        async tsbtnGhiLai_Click() {
            let content = this.$refs.txtGhiChu.value
            if(!content) {
                this.$toast.error("Lý do huỷ đơn hàng là bắt buộc!")
                return
            }

            for(let i = 0; i < this.phieu_ids.length; i++) {
                let response = await api.ghiLaiLyDoHuy(this.axios , {
                    "vphieu_id": this.phieu_ids[i].phieu_id , "vlydohuy": content
                })

                const data =  apiHelper.getDataFromResponseApiReturnRaise(response) 
                if(data == 'OK') {
                    this.$toast.success("Hủy đơn " + this.phieu_ids[i].ma_gd +  " thành công")
                    this.$emit('success')
                } else {
                    this.$toast.error("Hủy đơn " + this.phieu_ids[i].ma_gd +  " không thành công " + response.data.message_detail)
                }
            }

            this.$refs.popupHuyDonHang.hide()

        },
        showDialog() {
            this.$refs.popupHuyDonHang.show()
        }
    }
}
</script>

<style>

</style>