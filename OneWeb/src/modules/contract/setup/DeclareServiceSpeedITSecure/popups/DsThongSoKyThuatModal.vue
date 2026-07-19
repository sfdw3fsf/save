<template>
    <b-modal 
        ref="popupDsThongSoKyThuat"
        size="lg"
        hide-header
        hide-header-close
        hide-footer
        body-class="p-0"
        @show="handleShowModal">
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                <span class="icon one-notepad"></span> Thông số kỹ thuật
                </div>
                <div class="close -ap icon-close" @click="hideModal"></div>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <div class="legend-title">Thông số kỹ thuật thuê bao</div>
                    <div class="article">
                        <div v-html="thongsokt" />
                    </div>
                </div>
            </div>
        </div>    
    </b-modal>
</template>
<script>
export default {
    name:'DsThongSoKyThuatModal',
    props:{
        dataSources:{
            type:Array,
            default: [],
            required: true
        },
    },
    data(){
        return {
            columns:[
                {
                    field: "ten_truong",
                    label: "Tên trường",
                    allowFilter: false
                },
                {
                    field: "giatri",
                    label: "Giá trị",
                    allowFilter: false
                },
            ],
            thongsokt:''
        }
    },
    methods:{
        showModal() {
            this.$refs['popupDsThongSoKyThuat'].show()
        },
        hideModal() {
            this.$refs['popupDsThongSoKyThuat'].hide()
        },
        handleShowModal(){
            this.thongsokt=''
            setTimeout(()=>{
                this.dataSources.map(x=>{
                    return Object.assign(x, {giatri:x.giatri!=null?x.giatri:'', ten_truong:x.ten_truong!=null?x.ten_truong:''})
                }).forEach((item)=>{
                    this.thongsokt+=item.format_tt.replace('{0}', item.ten_truong)
                    this.thongsokt+=item.format_gt.replace('{0}', item.giatri)
                })
            },200)
        },
    }

};
</script>
