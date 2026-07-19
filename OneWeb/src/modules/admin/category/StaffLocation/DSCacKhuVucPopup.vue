<template>
  <b-modal
    ref="popupDSCacKhuVuc"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          Danh sách các khu vực của nhân viên đang phụ trách quản lý
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>

      <ActionTop :actions="actions" @onActionClick="onActionClick" />

      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin mã nhân viên tìm kiếm</div>
          <div class="info-row">
            <div class="key w60">Mã NV</div>
            <div class="value">
              <input type="text" v-model="input_nv" @keyup.enter="onEnterMaNV" class="form-control" />
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">
            Danh sách khu vực nhân viên đang quản lý
          </div>
          <KTableV3 :columns="columns" :dataSources="dataSources" id="id" @onSelectedRow="selectedRowDV" :allowFilter="true"/>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import StaffLocationAPI from './StaffLocationAPI'
export default {
    name:'DSCacKhuVucPopup',
    props:{
      ma_nv:{
        type:String,
        default: ''
      }
    },
    components:{
        KTableV3,
        ActionTop
    },
    data(){
        return{
            actions:[
                {
                    id:'chapnhan',
                    name:'Chấp nhận',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_check-circle-08'
                },
            ],
            columns:[
                {
                    field: "nhanvien_id",
                    label: "ID Nhân viên",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "ten_nv",
                    label: "Tên nhân viên",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "ma_kv",
                    label: "Mã KV",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "ten_kv",
                    label: "Tên KV",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "loai_kv",
                    label: "Loại khu vực",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "loai_nv",
                    label: "Loại NV",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "ma_kv_cha",
                    label: "Mã KV cha",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "ten_kv_cha",
                    label: "Tên KV cha",
                    allowFilter: true,
                    allowSorting:true
                },
            ],
            dataSources:[],
            input_nv:this.ma_nv,
            rowSelected:null
        }
    },
    methods:{
        onActionClick(action){
          if(action.id=='chapnhan'){
            if(this.rowSelected==null){
              return
            }
            //item callback: nhanvien_id, ten_nv, khuvuc_id, ma_kv, ten_kv, ma_kv_cha, ten_kv_cha, loai_kv, loai_nv
            this.$emit('accept',this.rowSelected)
            this.hideModal()
          }
        },
        selectedRowDV(item){
          this.rowSelected=item
        },
        handleShowModal(){
          this.input_nv=this.ma_nv
          this.rowSelected=null
          this.dataSources=[]
          if(this.ma_nv.trim().length>0){
            setTimeout(()=>{
              this.sp_lay_khuvuc_by_manv(this.ma_nv.trim())
            },500)
          }
        },
        showModal() {
            this.$refs["popupDSCacKhuVuc"].show()
        },
        hideModal() {
            this.$refs["popupDSCacKhuVuc"].hide()
        },
        onEnterMaNV(){
          if(this.input_nv.trim().length>0){
            this.sp_lay_khuvuc_by_manv(this.input_nv.trim())
          }
        },
        async sp_lay_khuvuc_by_manv(ma_nv){
            try{
                this.loading(true)
                this.dataSources=[]
                let response = await StaffLocationAPI.sp_lay_khuvuc_by_manv(this.axios,ma_nv)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.dataSources=response.data.data.map((item,index)=>{
                      return Object.assign(item, {id:index})
                    })
                    if(this.dataSources.length>0){
                      this.rowSelected=this.dataSources[0]
                    }
                }else{
                    this.dataSources=[]
                }
            }catch(e){
                this.$toast.error('Không load được danh sách khu vực')
            }finally{
                this.loading(false)
            }
        }
    }
}
</script>