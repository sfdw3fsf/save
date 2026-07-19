<template>
    <div class="modal-content popup-box" style="background: #fff; width: 99.5% !important; margin-left: 1px">
        <div class="popup-body mart10">
            <div class="info-row">
                <div class="key" style="width: 160px;">
                    Số ngày
                </div>
                <div class="value">
                    <input type="text" class="form-control" v-model="songay">
                </div>
            </div>
            <div class="group-buttons -bottom tright">
                <button class="btn btn-main" @click="xac_nhan">Xác nhận</button>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name:'ChonSoNgay',
    components:{
        
    },
    data() {
        return {
            songay: "",
        }
    },
    methods: {
        xac_nhan() {
            if (this.songay == null || this.songay == "") {
                this.$toast.error("Bạn chưa nhập số ngày!");
                return;
            }

            if (isNaN(this.songay)) {
                this.$toast.error("Số ngày phải là kiểu số!");
                return;
            }

            if (Number(this.songay) <= 0) {
                this.$toast.error("Hãy nhập số ngày > 0");
                return;
            }
            
            this.$emit("xac_nhan", this.songay);
        },
        async init() {
            this.songay = "";
        },
    },
}
</script>