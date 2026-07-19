<template>  
    <div class="modal-content popup-box">
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a @click="btnGhiLai_Click">
                        <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span>Ghi lại
                    </a>
                </li>
            </ul>
        </div>
        <div class="popup-body">
            <div class="box-form">
                <div class="info-row">
                    <textarea class="form-control" style="height: 120px;resize: vertical;" v-model="txtNoiDung"></textarea>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name:'NoiDung',
    components:{

    },
    props:{
        tientrinhbh_id: {
            type:String,
            default: ""
        }
    },
    data() {
        return {
            txtNoiDung: ""
        }
    },
    methods: {
        async btnGhiLai_Click() {
            this.$emit("btnGhiLai_Click", {tientrinhbh_id: this.tientrinhbh_id, noidung: this.txtNoiDung})
        },
    },
}
</script>