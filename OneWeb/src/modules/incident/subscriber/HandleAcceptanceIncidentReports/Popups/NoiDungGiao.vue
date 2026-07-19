<template>
    <div class="modal-content popup-box" style="background: #fff; width: 99.5% !important; margin-left: 1px">
        <div class="popup-body mart10">
            <div class="info-row">
                <div class="key" style="width: 160px;">
                    Nhập nội dung giao
                </div>
                <div class="value">
                    <input type="text" class="form-control" v-model="nd_giao">
                </div>
            </div>
            <div class="group-buttons -bottom tright">
                <button class="btn btn-main" @click="giao_phieu">Giao phiếu</button>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name:'NoiDungGiao',
    components:{
        
    },
    data() {
        return {
            nd_giao: "",
        }
    },
    methods: {
        giao_phieu() {
            this.$emit("giao_phieu", this.nd_giao);
        },
        async init() {
            this.nd_giao = "";
        },
    },
}
</script>