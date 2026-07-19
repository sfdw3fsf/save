<template>
    <b-modal 
        ref="popupDsThongSoKyThuat_DanhBa"
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
                        <div v-html="thongsoktdanhba" />
                    </div>
                </div>
            </div>
        </div>    
    </b-modal>
</template>
<script>
export default {
    name:'DsThongSoKyThuatModal_DanhBa',
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
                    field: "TEN_TRUONG",
                    label: "Tên trường",
                    allowFilter: false
                },
                {
                    field: "GIATRI",
                    label: "Giá trị",
                    allowFilter: false
                },
            ],
            thongsoktdanhba:''
        }
    },
    methods:{
        showModal() {
            this.$refs['popupDsThongSoKyThuat_DanhBa'].show()
        },
        hideModal() {
            this.$refs['popupDsThongSoKyThuat_DanhBa'].hide()
        },
        handleShowModal(){
            this.thongsoktdanhba=''
            setTimeout(()=>{
                this.dataSources.map(x=>{
                    return Object.assign(x, {GIATRI:x.GIATRI!=null?x.GIATRI:'', TEN_TRUONG:x.TEN_TRUONG!=null?x.TEN_TRUONG:''})
                }).forEach((item)=>{
                    this.thongsoktdanhba+=item.FORMAT_TT.replace('{0}', item.TEN_TRUONG)
                    this.thongsoktdanhba+=item.FORMAT_GT.replace('{0}', item.GIATRI)
                })
            },200)
        },
    }

};
</script>
