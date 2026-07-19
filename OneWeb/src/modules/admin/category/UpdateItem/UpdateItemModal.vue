<template>
  <b-modal
    ref="popupUpdateItem"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Quản lý khoản mục
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <ActionTop :actions="actions" @onActionClick="onActionClick" />
      <div class="popup-body">
        <div class="box-form">
          <div class="info-row">
            <div class="key w80">Khoản mục <k-required-marker/></div>
            <div class="value">
              <input type="text" id="inputKhoanMuc" ref="inputKhoanMuc" :value="input_khoanmuc" @change="e=>input_khoanmuc=e.target.value" @blur="outFocusInput('inputKhoanMuc')" class="form-control" />
            </div>
          </div>
          <div class="info-row">
            <div class="key w80">Ghi chú</div>
            <div class="value">
              <textarea
                id="inputGhiChu"
                ref="inputGhiChu"
                name=""
                class="form-control"
                :value="input_ghichu" @change="e=>input_ghichu=e.target.value"
                @blur="outFocusInput('inputGhiChu')"
                style="height: 80px; resize: none"
              ></textarea>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Khoản mục</div>
          <KTableV3 
              :columns="columns"
              :dataSources="dataSources"
              @onSelectedRow="onSelectedRow"
              :rowSelectedId="rowSelectedId"
              id="khoanmuc_id"
              :allowFilter="true"/>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
import API from './UpdateItemAPI'
export default {
    name:'UpdateItemModal',
    components:{
        KTableV3, ActionTop, KRequiredMarker
    },
    data(){
        return {
            actions:[
                {
                    id:'nhapmoi',
                    name:'Nhập mới',
                    active: true,
                    icon_class:'one-file-plus'
                },
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save'
                },
                {
                    id:'huy',
                    name:'Huỷ',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_circle-remove'
                },
                {
                    id:'xoa',
                    name:'Xoá',
                    active: true,
                    icon_class:'one-trash'
                },

            ],
            columns:[
              {
                  field: "khoanmuc",
                  label: "Khoản mục",
                  allowFilter: true,
                  allowSorting:false
              },
              {
                  field: "ghichu",
                  label: "Ghi chú",
                  allowFilter: true,
                  allowSorting:false
              }
            ],
            dataSources:[],
            rowSelectedId:-1,
            input_khoanmuc:'',
            input_ghichu:'',
            rowSelected:null,
            khoanmuc_id:0
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='nhapmoi'){
                this.setButton(1)
            }else if(action.id=='ghilai'){
                this.ghiDuLieu()
            }else if(action.id=='huy'){
                if(this.rowSelected!=null){
                    this.onSelectedRow(this.rowSelected)
                }else{
                    this.input_khoanmuc=''
                    this.input_ghichu=''
                }
                this.setButton(0)
            }else if(action.id=='xoa'){
                if(this.rowSelected==null||this.dataSources.length==0){
                    this.$toast.error('Không có dữ liệu để xoá')
                    return
                }
                this.$confirm('Bạn có chắc chắn muốn xóa khoản mục?','Thông báo',{
                    confirmButtonText: 'Đồng ý',
                    cancelButtonText: 'Huỷ'
                }).then(() => {
                    this.xoa()
                })
            }
        },
        setButton(kieu){
            if(kieu==1){
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=false
                this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=false
                this.input_khoanmuc=''
                this.input_ghichu=''
            }else{
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
            }
        },
        showModal() {
            this.$refs["popupUpdateItem"].show()
        },
        hideModal() {
            this.$refs["popupUpdateItem"].hide()
        },
        handleShowModal(){
            this.dataSources=[]
            this.rowSelectedId=-1
            this.input_khoanmuc=''
            this.input_ghichu=''
            setTimeout(()=>{
                this.iniDuLieu()
            },500)
        },
        onSelectedRow(item){
            this.rowSelected=item
            this.khoanmuc_id=item.khoanmuc_id
            this.input_ghichu=item.ghichu!=null?item.ghichu:''
            this.input_khoanmuc=item.khoanmuc!=null?item.khoanmuc:''
        },
        outFocusInput(inputId){
            let element=document.getElementById(inputId)
            if(element){
                element.classList.remove('input_batbuoc')
            }
        },
        async iniDuLieu(){
            await this.hienthi_dulieu()
            
        },
        async hienthi_dulieu(){
            //this.dataSources=[]
            this.dataSources=await this.phutro_lay_ds_khmuc()
            if(this.dataSources.length>0){
                if(this.khoanmuc_id!=0){
                    const index=this.dataSources.findIndex(x=>x.khoanmuc_id==this.khoanmuc_id)
                    if(index>-1){
                        this.rowSelectedId=this.dataSources[index].khoanmuc_id
                        this.onSelectedRow(this.dataSources[index])
                    }else{
                        this.rowSelectedId=this.dataSources[0].khoanmuc_id
                        this.onSelectedRow(this.dataSources[0])
                    }
                }else{
                    this.rowSelectedId=this.dataSources[0].khoanmuc_id
                    this.onSelectedRow(this.dataSources[0])
                }
                
            }
        },
        async xoa(){
            let data={
                vkhoanmuc_id: this.rowSelected.khoanmuc_id,
                vghichu: this.input_ghichu.trim(),
                vkieu: 3,
                vkhoanmuc: this.input_khoanmuc.trim()
            }
            let result =await this.phutro_capnhap_kmuc(data)
            if(result=='1'){
                this.$toast.success('Xóa khoản mục thành công!')
                this.rowSelected=null
                await this.hienthi_dulieu()
            }else{
                this.$toast.error(result)
            }
        },
        async ghiDuLieu(){
            let isInsert=this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active?false:true
            if(!this.kiemtra_dulieu(isInsert)){
                return
            }
            let data={
                vkhoanmuc_id:isInsert?1:this.rowSelected.khoanmuc_id,
                vghichu: this.input_ghichu.trim(),
                vkieu: isInsert?1:2,
                vkhoanmuc: this.input_khoanmuc.trim()
            }
            let result =await this.phutro_capnhap_kmuc(data)
            if(result=='1'){
                this.$toast.success(isInsert?'Thêm mới khoản mục thành công!':'Cập nhật khoản mục thành công!')
                await this.hienthi_dulieu()
                if(isInsert){
                    this.setButton(0)
                }
            }else{
                this.$toast.error(result)
            }

        },
        kiemtra_dulieu(isInsert){
            if(this.input_khoanmuc.trim()==''){
                this.$toast.error('Chưa nhập tên khoản mục!')
                document.getElementById('inputKhoanMuc').classList.add('input_batbuoc')
                this.$refs.inputKhoanMuc.focus()
                return false
            }
            if(this.input_khoanmuc.trim().length>50){
                this.$toast.error('Tên khoản mục không được vượt quá 50 ký tự!')
                document.getElementById('inputKhoanMuc').classList.add('input_batbuoc')
                this.$refs.inputKhoanMuc.focus()
                return false
            }
            if(isInsert){
                let ds=this.dataSources.filter(x=>x.khoanmuc!=null&&x.khoanmuc.trim()==this.input_khoanmuc.trim())
                if(ds.length>0){
                    this.$toast.error('Khoản mục bị trùng!')
                    document.getElementById('inputKhoanMuc').classList.add('input_batbuoc')
                    this.$refs.inputKhoanMuc.focus()
                    return false
                }
            }else{
                let ds=this.dataSources.filter(x=>x.khoanmuc!=null&&this.rowSelected!=null&&x.khoanmuc.trim()==this.input_khoanmuc.trim()&&x.khoanmuc_id!=this.rowSelected.khoanmuc_id)
                if(ds.length>0){
                    this.$toast.error('Khoản mục bị trùng!')
                    document.getElementById('inputKhoanMuc').classList.add('input_batbuoc')
                    this.$refs.inputKhoanMuc.focus()
                    return false
                }
            }
            if(this.input_ghichu.trim().length>250){
                this.$toast.error('Ghi chú không được vượt quá 250 ký tự!')
                document.getElementById('inputGhiChu').classList.add('input_batbuoc')
                this.$refs.inputGhiChu.focus()
                return false
            }
            return true
        },
        async phutro_lay_ds_khmuc(){
            try{
                this.loading(true)
                let response=await API.phutro_lay_ds_khmuc(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async phutro_capnhap_kmuc(data){
            try{
                this.loading(true)
                let response=await API.phutro_capnhap_kmuc(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return response.data.data
                }else{
                    if(response&&response.data&&response.data.message){
                        return response.data.message
                    }else{
                        return 'Đã xảy ra lỗi'
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    return e.data.message
                }else if(e.response&&e.response.data&&e.response.data.message){
                    return e.response.data.message
                }else{
                    return 'Đã xảy ra lỗi'  
                }
            }
        },

    }

}
</script>
<style>
.input_batbuoc:focus {
    border-color:red;
    background-color: lightcoral;
    box-shadow: none;
}
</style>