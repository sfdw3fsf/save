<template>
    <div class="info-row">
        <div class="key w150  text-center">
            <div class="check-action" >
                <input type="checkbox" class="check" @click="changeTick"/>
                <span class="name"></span>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data(){
        return{
            isCheck: false
        }
    },
    methods: {
        changeTick(){
            try {
                this.isCheck = !this.isCheck;
                // alert(this.isCheck);
                let vtrangthai = this.isCheck ? 1 : 0
                this.tsbtnSetAuto_Click(vtrangthai);
            } catch (error) {
                console.log(error);
            }
        },
        async tsbtnSetAuto_Click(vtrangthai){
            try {
                let res = await this.axios.get('/web-ccdv/kichhoat-bnm/capnhat_tudong_kichhoat_bnm', {params: {vtrangthai: vtrangthai}});
                if(res.data.error == 200){
                    this.$toast.success("Cập nhật thành công");
                }else{
                    this.$toast.warning("Đã có lỗi xảy ra")
                }
            } catch (error) {
                this.informError(error);
            }
        },
    }

}
</script>

<style>

</style>