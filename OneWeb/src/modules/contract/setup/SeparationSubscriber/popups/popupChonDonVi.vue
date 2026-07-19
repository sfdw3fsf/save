<template>
    <div>
        <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="popupChonDonVi"
        :animationSettings='animationSettings'
        width='80%'
        :target='target'
        :open="dialogOpen"
        :isModal="true">
            <div class="modal-content popup-box">
                <div class="popup-header">
                    <div class="title"><span class="icon one-notepad"></span>Chọn đơn vị quản lý</div>
                    <div class="close -ap icon-close" style="cursor: pointer" @click="closeModal"></div>
                </div>
                <ActionTop :actions="actions" @onActionClick="onActionClick"/>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="legend-title">Thông tin tham số</div>
                        <div class="row">
                            <div class="col-sm-12 col-12">
                                <div class="info-row">
                                    <div class="key">Đơn vị quản lý</div>
                                    <div class="value">
                                        <select2 v-model="cboDonViQL.value" :options="cboDonViQL.list"></select2>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </ejs-dialog>
    </div>
</template>

<script>
import ActionTop from "@/components/kylq_components/ActionTop.vue";
export default {
    name: "popupChonDonVi",
    components: {
        ActionTop,
    },
    data(){
        return {
            target: '.main-wrapper',
            animationSettings: { effect: 'Zoom' },
            cboDonViQL: {
                list: [],
                value: ""
            },
            dsDVQL: [],
            dvChon: "",
            actions: [
                {
                    id: "chapnhan",
                    name: "Chấp nhận",
                    active: true,
                    icon_class: "one-check"
                },
            ],
        }
    },
    watch:{
    },
    methods: {
        onActionClick(action){
            switch(action.id){
                case "chapnhan":
                    this.ChapNhan()
                    break;
                default:
                    break;
            }
        },
        async dialogOpen () {
            this.cboDonViQL.list = this.dsDVQL.map((e) => ({
                id: e.donviql_id || e.donvi_id,
                text: e.ten_dvql || e.ten_dv
            }))
            this.cboDonViQL.value = this.dvChon
        },
        ChapNhan(){
            this.$emit('accept',this.cboDonViQL.value)
            this.closeModal()
        },
        openModal() {
            this.$refs.popupChonDonVi.show()
        },
        closeModal () {
            this.$refs.popupChonDonVi.hide()
        },
    }
}
</script>

<style scoped>
/* Hide the number input arrows */
</style>