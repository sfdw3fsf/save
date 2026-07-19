<template>
    <b-modal 
        ref="popupDanhSachTBCungMa"
        size="xl" 
        title="Chọn thuê bao"
        body-class="p-0"
         @show="handleShowModal"
        hide-footer>
        <div class="modal-content">
            <ActionTop :actions="actions" @onActionClick="onActionClick"/>
            <div class="popup-body">
                <div class="box-form">
                    <div class="legend-title">Danh sách thuê bao</div>
                    <k-table :columns="columns" :dataSources="danhsach" @onSelectedRow="onSelectedRow"/>
                </div>
            </div>
        </div>    
    </b-modal>
</template>
<script>
import KTable from '@/components/kylq_components/KTable.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import CorrectOwnerAPI from './CorrectOwnerAPI'
export default {
    name:'DanhSachTBCungMaModal',
    props:{
        dataSources:{
            type:Array,
            default: [],
            required: true
        },
    },
    components:{
        KTable,
        ActionTop
    },
    data(){
        return {
            actions:[
                {
                    id:'chapnhan',
                    name:'Chấp nhận',
                    active: false,
                    icon_class:'nc-icon-glyph ui-1_check-circle-08'
                },
                {
                    id:'thoat',
                    name:'Thoát',
                    active: true,
                    icon_class:'ui-1_circle-remove nc-icon-glyph'
                }
            ],
            columns:[
                {
                    field: "ma_tb",
                    label: "Số máy/Acc",
                    allowFilter: true
                },
                {
                    field: "dichvu",
                    label: "Dịch vụ",
                    allowFilter: true
                },
                {
                    field: "loaihinh",
                    label: "Loại hình",
                    allowFilter: true
                },
            ],
            rowSelected:null,
            danhsach:[],
            dichvu:[],
            loaihinh_tb:[]
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='chapnhan'){
                this.accept()
            }else if(action.id=='thoat'){
                this.hideModal()
            }
        },
        accept(){
            this.$emit('accept', this.rowSelected)
            this.$refs['popupDanhSachTBCungMa'].hide()
        },
        showModal() {
            this.$refs['popupDanhSachTBCungMa'].show()
        },
        hideModal() {
            this.$refs['popupDanhSachTBCungMa'].hide()
        },
        handleShowModal(){
            this.danhsach=[]
            this.rowSelected=null
            this.loaihinh_tb=[]
            this.dichvu=[]
            this.actions[this.actions.findIndex(x=>x.id=='chapnhan')].active=false

            this.hienthi_dulieu()

        },
        async hienthi_dulieu(){
            try{
                this.loading(true)
                //lấy danh sách dịch vụ viễn thông
                let responseDichVu = await CorrectOwnerAPI.lay_danhsach_dichvu(this.axios)
                if(responseDichVu && responseDichVu.data && responseDichVu.data.error_code && responseDichVu.data.error_code == 'BSS-00000000' && responseDichVu.data.data) {
                    this.dichvu=responseDichVu.data.data
                }else{
                    this.dichvu=[]
                }
                //lấy danh sách loại hình thuê bao
                let responseLoaiHinh = await CorrectOwnerAPI.lay_danhsach_loaihinh_tb(this.axios)
                if(responseLoaiHinh && responseLoaiHinh.data && responseLoaiHinh.data.error_code && responseLoaiHinh.data.error_code == 'BSS-00000000' && responseLoaiHinh.data.data) {
                    this.loaihinh_tb=responseLoaiHinh.data.data
                }else{
                    this.loaihinh_tb=[]
                }
                //đưa dữ liệu hiễn thị lên bảng
                this.dataSources.forEach((item)=>{
                    //loaitb_id dichvuvt_id
                    var ten_dichvu=''
                    var loaihinh=''
                    let dv=this.dichvu.find(x=>x.DICHVUVT_ID==item.dichvuvt_id)
                    if(dv!=undefined){
                        ten_dichvu=dv.TEN_DVVT
                    }
                    let lh=this.loaihinh_tb.find(x=>x.LOAITB_ID==item.loaitb_id)
                    if(lh!=undefined){
                        loaihinh=lh.LOAIHINH_TB
                    }
                    this.danhsach.push(Object.assign(item,{dichvu:ten_dichvu, loaihinh:loaihinh}))
                })

            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi khi khởi tạo dữ liệu')
                }
            }finally{
                this.loading(false)
            }
        },
        onSelectedRow(item){
            this.rowSelected=item
            //update status button chapnhan
            const index = this.actions.findIndex(x=>x.id=='chapnhan')
            if(index>-1){
                this.actions[index].active=true
            }
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