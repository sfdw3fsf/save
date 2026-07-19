<template>
  <b-modal
    ref="popupGanNguoiDung"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">Gán người dùng VNP</div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <ActionTop :actions="actions" @onActionClick="onActionClick"/>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin UserNeo</div>
          <div class="info-row">
            <div class="key w80">UserNeo</div>
            <div class="value">
               <input type="text" ref="inputUserNeo" v-model="input_user_neo" class="form-control">
            </div>
            <div class="value w30 nowrap padt7">
              <div class="check-action">
                <input type="checkbox" v-model="checkDongBo" class="check" />
                <span class="name">Đồng bộ</span>
              </div>
            </div>
          </div>
            <KDataGrid
                :columns="columns"
                :dataSource="dataSources"
                :showColumnCheckbox="false"
                :enable-paging-server="false"
                :allowPaging="true"
                :showFilter="true"
                :enabledSelectFirstRow="true"
                @selectedItemsChanged="gridDsChanged"/>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
export default {
    name:'GanNguoiDungModal',
    props:{
        danhsach:{
            type:Array,
            default:()=>[]
        },
        nguoidung_id:{
            type:Number,
            default:-1
        }
    },
    components:{
        ActionTop,
        KDataGrid
    },
    data(){
        return {
            actions:[
                {
                    id:'them',
                    name:'Thêm',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_circle-add'
                },
                {
                    id:'xoa',
                    name:'Xoá',
                    active: true,
                    icon_class:'one-trash'
                },
                {
                    id:'chapnhan',
                    name:'Chấp nhận',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_check-circle-08'
                }
            ],
            columns:[
                {
                    fieldName:'user_vnp',
                    headerText:'UserNeo',
                    allowFiltering:true
                },
                {
                    fieldName:'dongbo',
                    headerText:'Đồng bộ',
                    allowFiltering:true
                }
            ],
            dataSources:[],
            rowSelected:null,
            input_user_neo:'',
            checkDongBo:false

        }
    },
    methods:{
        showModal() {
            this.$refs["popupGanNguoiDung"].show()
        },
        hideModal() {
            this.$refs["popupGanNguoiDung"].hide()
        },
        handleShowModal(){
            this.dataSources=[]
            this.rowSelected=null
            this.input_user_neo=''
            this.checkDongBo=false

            //init dữ liệu
            setTimeout(()=>{
                this.dataSources=[].concat(this.danhsach)
                if(this.dataSources.length>0){
                    this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
                }else{
                    this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=false
                }
            },200)
        },
        onActionClick(action){
            if(action.id=='them'){
                this.themDuLieu()
            }else if(action.id=='xoa'){
                if(this.rowSelected==null){
                    return
                }
                const index=this.dataSources.findIndex(x=>x.user_vnp==this.rowSelected.user_vnp)
                if(index>-1){
                    //Bạn có muốn xóa dữ liệu không?
                    this.$confirm('Bạn muốn xóa dữ liệu không ?','Thông báo',{
                        confirmButtonText: 'Có',
                        cancelButtonText: 'Không'
                    }).then(() => {
                        this.dataSources.splice(index,1)
                        if(this.dataSources.length>0){
                            this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
                        }else{
                            this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=false
                        }
                    }).catch(()=>{})
                }
            }else if(action.id=='chapnhan'){
                this.$emit('onAccept',this.dataSources)
                this.hideModal()
            }
        },
        gridDsChanged(data){
            if(this.dataSources.length<=0||data.length<=0){
                this.rowSelected=0
                return
            }
            this.rowSelected=data[0]
        },
        themDuLieu(){
            if(this.input_user_neo.trim()==''){
                this.$toast.error('Bạn phải nhập ô mã User Neo')
                this.$nextTick(()=>{
                    this.$refs.inputUserNeo.focus()
                })
                return
            }
            const regex = RegExp('^[a-zA-Z0-9*.*-*_]*$')
            if(!regex.test(this.input_user_neo.trim())){
                this.$toast.error('User không đúng quy ước: Chỉ bao gồm các ký tự chữ cái viết thường, các chữ số từ 0-9, dấu : -, _, .')
                this.$nextTick(()=>{
                    this.$refs.inputUserNeo.focus()
                })
                return
                
            }
            const index=this.dataSources.findIndex(x=>x.user_vnp==this.input_user_neo.trim())
            if(index>-1){
                this.$toast.error('Tên User VNP đã tồn tại')
                return
            }

            this.dataSources.unshift({
                dongbo: this.checkDongBo?1:0,
                user_vnp: this.input_user_neo.trim(),
                nguoidung_id: this.nguoidung_id
            })
            this.input_user_neo=''
            this.checkDongBo=false
            this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
        }
    }
}
</script>