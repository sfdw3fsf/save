<template>
  <b-modal
    ref="popupCapNhatGiaTriThuocTinh"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    no-close-on-backdrop
    body-class="p-0"
    @show="handleShowModal"
  >
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title"><span class="icon one-notepad"></span>Cập nhập giá trị thuộc tính</div>
                <div class="close -ap icon-close" @click="hideModal"></div>
            </div>

            <ActionTop :actions="actions" @onActionClick="onActionClick"/>

            <div class="popup-body">
                <div class="box-form">
                    <div class="info-row">
                        <div class="key w80">Thuộc tính</div>
                        <div class="value">
                            <div class="select-custom" ref="ds_thuoctinh">
                                <select2 :settings="{ dropdownParent: $refs['ds_thuoctinh'] }" ref="cboThuocTinh"
                                    v-model="thuoctinh_selected" class="select2"
                                    :options="thuoctinh.map(e=> ({id: e.thuoctinh_id, text: e.ma_tt+ ' — '+e.thuoctinh}))"
                                    @change="changeThuocTinh">
                                </select2>
                            </div>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w80">Giá trị <k-required-marker/></div>
                        <div class="value">
                            <input type="text" ref="inputGiaTri" :value="input_giatri" @change="e=>input_giatri=e.target.value" class="form-control">
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w80">Ghi chú:</div>
                        <div class="value">
                            <textarea name="" :value="input_ghichu" @change="e=>input_ghichu=e.target.value" class="form-control" style="height: 130px;resize: none;"></textarea>
                        </div>
                    </div>
                </div>
                <div class="box-form">
                    <div class="legend-title">Giá trị thuộc tính</div>
                    <KTableV3 :columns="columns"
                        :dataSources="dataSources"
                        id="gttt_id"
                        :rowSelectedId="rowSelectedId"
                        @onSelectedRow="onSelectedRow"
                        :allowFilter="true"/>
                </div>
            </div>
            <!-- Modal Dọc file -->
            <DocFileModal ref="docFileModal" :thuoctinh="thuoctinh"/>
        </div>
  </b-modal>
</template>
<script>
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import DocFileModal from './DocFileModal.vue'
import UpdateAttributeValuesAPI from './UpdateAttributeValuesAPI'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
export default {
  name: "CapNhatGiaTriThuocTinhModal",
  components: {
      KTableV3,
      ActionTop,
      DocFileModal,
      KRequiredMarker
  },
  data() {
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
            {
                id:'nhap_excel',
                name:'Nhập Excel',
                active: true,
                icon_class:'one-xlsx'
            },
        ],
        columns:[
            {
                field: "giatri",
                label: "Giá trị",
                allowFilter: true
            },
            {
                field: "ghichu",
                label: "Ghi chú",
                allowFilter: true
            }
        ],
        dataSources:[],
        thuoctinh:[],
        thuoctinh_selected:null,
        rowSelected:null,
        input_giatri:'',
        input_ghichu:'',
        rowSelectedId:-1,
        gttt_id:0
    }
  },
  methods:{
    showModal() {
        this.$refs["popupCapNhatGiaTriThuocTinh"].show()
    },
    hideModal() {
        this.$refs["popupCapNhatGiaTriThuocTinh"].hide()
    },
    onActionClick(action){
        if(action.id=='nhapmoi'){
            this.setActiveActions(1)
        }else if(action.id=='ghilai'){
            this.ghiDuLieu()
        }else if(action.id=='huy'){
            if(this.rowSelected!=null){
                this.onSelectedRow(this.rowSelected)
            }else{
                this.setActiveActions(2)
            }
        }else if(action.id=='xoa'){
            if(this.rowSelected==null||this.dataSources.length==0){
                this.$toast.error('Hãy chọn giá trị thuộc tính cần xoá!')
                return
            }
            this.$confirm('Bạn thật sự muốn xóa dữ liệu không?','Thông báo',{
                confirmButtonText: 'Đồng ý',
                cancelButtonText: 'Huỷ'
            }).then(() => {
                this.sp_del_giatritt(this.rowSelected.gttt_id)    
            }).catch(()=>{})
        }else if(action.id=='nhap_excel'){
            this.$refs.docFileModal.showModal()
        }

    },
    handleShowModal(){
        this.setActiveActions(-1)
        this.rowSelected=null
        this.thuoctinh_selected=null
        this.thuoctinh=[]
        this.dataSources=[]
        this.input_giatri=''
        this.input_ghichu=''
        this.rowSelectedId=-1
        this.gttt_id=0
        setTimeout(()=>{
            this.initDuLieu()
        },500)
        
    },
    async initDuLieu(){
        this.thuoctinh=await this.lay_ds_thuoctinh()
        if(this.thuoctinh.length>0){
            this.thuoctinh_selected=this.thuoctinh[0].thuoctinh_id
            await this.hienthi_ds_giatri_thuoctinh()
        }
    },
    async hienthi_ds_giatri_thuoctinh(){
        //this.dataSources=[]
        this.dataSources=await this.sp_grv_thuoctinh(this.thuoctinh_selected)
        if(this.dataSources.length>0){
            if(this.gttt_id!=0){
                const index=this.dataSources.findIndex(x=>x.gttt_id==this.gttt_id)
                if(index>-1){
                    this.rowSelectedId=this.dataSources[index].gttt_id
                    this.onSelectedRow(this.dataSources[index])
                }else{
                    this.rowSelectedId=this.dataSources[0].gttt_id
                    this.onSelectedRow(this.dataSources[0])
                }
            }else{
                this.rowSelectedId=this.dataSources[0].gttt_id
                this.onSelectedRow(this.dataSources[0])
            }
        }else{
            this.setActiveActions(1)
        }
    },
    onSelectedRow(item){
        this.rowSelected=item
        this.gttt_id=item.gttt_id

        this.input_giatri=item.giatri?item.giatri.toString():''
        this.input_ghichu=item.ghichu?item.ghichu.toString():''

        this.setActiveActions(3)
    },
    nameWithThuocTinh (object) {
      return `${object.ma_tt} — ${object.thuoctinh}`
    },
    setActiveActions(kieu){
        this.actions.forEach((item)=>{
            if(item.id!='nhap_excel'){
                item.active=false
            }
        })
        if(kieu==-1){//bắt đầu
            this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
            this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
        }else if(kieu==0){//bắt đầu
            this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
            this.clear()
        }else if(kieu==1){//thêm mới
            this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
            this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
            this.clear()

        }else if(kieu==2){//Huỷ
            this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
            this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
            this.clear()
        }else if(kieu==3){//edit
            this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
            this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
            this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
            this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
        }
    },
    clear(){
        this.input_giatri=''
        this.input_ghichu=''
    },
    async changeThuocTinh(data){
        //this.thuoctinh_selected=data.thuoctinh_id
        this.rowSelected=null
        this.clear()
        this.setActiveActions(1)
        await this.hienthi_ds_giatri_thuoctinh()
    },
    ghiDuLieu(){
        let kieu=this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active?0:1
        if(this.checkDulieu(kieu)){
            
            // let data="[{'GTTT_ID':$1,'THUOCTINH_ID':$2,'GIATRI':'$3','GHICHU':'$4'}]"
            // data=data.replace('$1',kieu==1?-1:this.rowSelected.gttt_id)
            // data=data.replace('$2',this.thuoctinh_selected)
            // data=data.replace('$3',this.input_giatri.trim())
            // data=data.replace('$4',this.input_ghichu.trim())

            let data={
                GTTT_ID:kieu==1?-1:this.rowSelected.gttt_id,
                THUOCTINH_ID:this.thuoctinh_selected,
                GIATRI:this.input_giatri.trim(),
                GHICHU:this.input_ghichu.trim()
            }
        
            this.sp_upd_giatritt(JSON.stringify(data),kieu)
        }
    },
    checkDulieu(kieu){
        if(this.input_giatri.trim()==''){
            this.$toast.error('Chưa nhập giá trị thuộc tính!')
            this.$refs.inputGiaTri.focus()
            return false
        }
        if(kieu==0&&this.rowSelected==null){
            this.$toast.error('Hãy chọn giá trị thuộc tính cần cập nhật!')
            return
        }
        if(this.dataSources.length>0&&kieu==1){
            const index=this.dataSources.findIndex(x=>x.giatri&&x.giatri.trim()==this.input_giatri.trim())
            if(index>-1){
                this.$toast.error('Giá trị thuộc tính bị trùng!')
                this.$refs.inputGiaTri.focus()
                return false
            }
        }
        return true
    },
    async lay_ds_thuoctinh(){
        try{
            this.loading(true)
            let response = await UpdateAttributeValuesAPI.lay_ds_thuoctinh(this.axios)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
            }else{
                return []
            }
        }catch(e){
            this.loading(false)
            return []
        }
    },
    async sp_grv_thuoctinh(thuoctinh_id){
        try{
            this.loading(true)
            let response = await UpdateAttributeValuesAPI.sp_grv_thuoctinh(this.axios, thuoctinh_id)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
            }else{
                return []
            }
        }catch(e){
            this.loading(false)
            return []
        }
    },
    async sp_del_giatritt(gttt_id){
        try{
            this.loading(true)
            let response=await UpdateAttributeValuesAPI.sp_del_giatritt(this.axios, gttt_id)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                this.clear()
                this.$toast.success('Xóa giá trị thuộc tính thành công!')
                this.rowSelected=null
                await this.hienthi_ds_giatri_thuoctinh()
            }else {
                if(response && response.data && response.data.message){
                    this.$toast.error(response.data.message)
                }else{
                    this.$toast.error('Xoá giá trị thuộc tính không thành công!')
                }
            }
        }catch(error){
            console.log('lang thang',error)
            this.loading(false)
            if(error.data&&error.data.message){
                this.$toast.error(error.data.message)
            }else if(error.response&&error.response.data&&error.response.data.message){
                this.$toast.error(error.response.data.message)
            }else{
                this.$toast.error('Xoá giá trị thuộc tính không thành công!')
            }
        }
    },
    //kieu 1:nhập mới, 0 cập nhật
    async sp_upd_giatritt(data, kieu){
        try{
            this.loading(true)
            let response=await UpdateAttributeValuesAPI.sp_upd_giatritt(this.axios, data)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                this.clear()
                this.$toast.success(kieu==1?'Thêm mới giá trị thuộc tính thành công!':'Cập nhật giá trị thuộc tính thành công!')
                //
                if(kieu==1){
                    this.rowSelected=null
                }
                //refresh dữ liêu
                await this.hienthi_ds_giatri_thuoctinh()
                
            }else {
                if(response && response.data && response.data.message){
                    this.$toast.error(response.data.message)
                }else{
                    this.$toast.error(kieu==1?'Thêm mới giá trị thuộc tính không thành công!':'Cập nhật giá trị thuộc tính không thành công!')
                }
                
            }
        }catch(error){
            this.loading(false)
            if(error.data&&error.data.message){
                this.$toast.error(error.data.message)
            }else if(error.response&&error.response.data&&error.response.data.message){
                this.$toast.error(error.response.data.message)
            }else{
                this.$toast.error(kieu==1?'Thêm mới giá trị thuộc tính không thành công!':'Cập nhật giá trị thuộc tính không thành công!')
            }
        }
    }

  },


}
</script>
<style>
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
/* asset css select2 form */
.select2-container {
  width: 100% !important;
}

.select2-container--default .select2-selection--single {
  border-color: #E0E0E0;
  outline: none;
}

.select2-dropdown {
  border: 1px solid #E0E0E0;
}

.select2-container--default .select2-selection--single .select2-selection__arrow b {
  font-family: "FontAwesome";
  border: 0px;
  top: 0px;
  height: 1.428rem;
  bottom: 0px;
  line-height: 1.428rem;
  left: 5px;
  margin: auto;
}
.select2-container--default .select2-selection--single .select2-selection__arrow b:before {
  content: "";
}

.select2-container--default .select2-search--dropdown .select2-search__field {
  border: 0px;
  outline: none;
}
</style>

