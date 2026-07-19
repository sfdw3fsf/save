<template>
    <b-modal 
        ref="popupDanhSachCacBienDong"
        size="xl" 
        title="Danh sách các biến động"
        body-class="p-0"
        @show="handleShowModal"
        hide-footer>
        <div class="modal-content">
            <ActionTop :actions="actions" @onActionClick="onActionClick"/>
            <div class="popup-body">
                <div class="box-form">
                    <KTableV3 :columns="columns"
                        :dataSources="data"
                        id="id"
                        :rowSelectedId="rowSelectedId"
                        @onSelectedRow="onSelectedRow"/>
                </div>
            </div>
        </div>    
    </b-modal>
</template>
<script>
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
export default {
    name:'DanhSachCacBienDong',
    props:{
        dataSources:{
            type:Array,
            default: [],
            required: true
        },
    },
    components:{
        KTableV3,
        ActionTop
    },
    data(){
        return {
            actions:[
                {
                    id:'chapnhan',
                    name:'Chấp nhận',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_check-circle-08'
                },
                {
                    id:'dong',
                    name:'Đóng',
                    active: true,
                    icon_class:'ui-1_circle-remove nc-icon-glyph'
                }
            ],
            columns:[
                {
                    field: "maGd",
                    label: "Mã GD",
                    allowFilter: true
                },
                {
                    field: "ngayYc",
                    label: "Ngày yêu cầu",
                    allowFilter: true
                },
                {
                    field: "trangThaiHd",
                    label: "Trạng thái",
                    allowFilter: true
                },
                 {
                    field: "tenLoaiHd",
                    label: "Loại hợp đồng",
                    allowFilter: true
                },
                {
                    field: "tenDv",
                    label: "Đơn vị lập HĐ/PL",
                    allowFilter: true
                },

            ],
            rowSelected:null,
            data:[],
            rowSelectedId:-1
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='chapnhan'){
                this.accept()
            }else if(action.id=='dong'){
                this.close()
            }
        },
        accept(){
            this.$emit('accept', this.rowSelected)
            this.$refs['popupDanhSachCacBienDong'].hide()
        },
        close(){
            this.$refs['popupDanhSachCacBienDong'].hide()
        },
        showModal() {
            this.$refs['popupDanhSachCacBienDong'].show()
        },
        hideModal() {
            this.$refs['popupDanhSachCacBienDong'].hide()
        },
        handleShowModal(){
            this.rowSelected=null
            this.data=[]
            this.rowSelectedId=-1
            this.actions[this.actions.findIndex(x=>x.id=='chapnhan')].active=false
            const index = this.actions.findIndex(x=>x.id=='chapnhan')
            if(index>-1){
                this.actions[index].active=false
            }
            setTimeout(()=>{
                this.data=this.dataSources.map((x, index)=>Object.assign(x, {id:index+1}))
                if(this.data.length>0){
                    this.rowSelectedId=this.data[0].id
                    this.onSelectedRow(this.data[0])
                }
            },500)
        },
        onSelectedRow(item){
            this.rowSelected=item
            
            //update status button chapnhan
            this.actions[this.actions.findIndex(x=>x.id=='chapnhan')].active=true
        }
    }

};
</script>
<style scoped>
.disabled{
    pointer-events:none ;
    opacity:0.7;
}
.modal-content{
    border: none;
    border-radius: none;
}
</style>