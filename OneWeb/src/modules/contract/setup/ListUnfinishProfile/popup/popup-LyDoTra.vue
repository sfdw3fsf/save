<template>  
    <div class="modal-content popup-box">
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a @click="xac_nhan">
                        <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Xác nhận
                    </a>
                </li>
            </ul>
        </div>
        <div class="popup-body">
            <div class="box-form">
                <div class="info-row">
                    <div class="key w90">
                        Nội dung trả
                    </div>
                    <div class="value">
                        <textarea class="form-control" style="height: 120px;resize: vertical;" v-model="noiDungTra"></textarea>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name:'popup-LyDoTra',
    components:{
        
    },
    data() {
        return {
            noiDungTra: "",
        }
    },
    computed: {

    },
    methods: {
        xac_nhan() {
            if (this.noiDungTra.trim() == "") {
                this.$toast.error("Chưa nhập nội dung trả");
                return;
            }
            this.$emit("xac_nhan", this.noiDungTra);
        }
    },
    mounted() {
        
    },
}
</script>