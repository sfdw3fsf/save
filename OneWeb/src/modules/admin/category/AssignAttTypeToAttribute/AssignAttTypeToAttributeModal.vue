<template>
  <b-modal
    ref="popupAATT"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
    no-close-on-esc
    no-close-on-backdrop
  >
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title">
            <span class="icon one-notepad"></span> Gán thuộc tính - kiểu thuộc tính
            </div>
            <div class="close -ap icon-close" @click="hideModal"></div>
        </div>
        <ActionTop :actions="actions"  @onActionClick="onActionClick"/>
        <div class="popup-body">
            <div class="box-form padt8 padb8">
            <div class="info-row marb0">
                <div class="key w80">Thuộc tính</div>
                    <div class="value">
                        <div class="select-custom" ref="ds_thuoctinh">
                            <select2 :settings="{ dropdownParent: $refs['ds_thuoctinh'] }" ref="cboThuocTinh"
                                v-model="thuoctinh_selected" class="select2"
                                :options="danhsach_thuoctinh.map(e=> ({id: e.THUOCTINH_ID, text: e.THUOCTINH}))"
                                @change="changeThuocTinh"
                            >
                            </select2>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-move-select-table">
                <div class="box-col box-form">
                    <div class="legend-title">
                    <div class="pull-left">Danh sách kiểu thuộc tính đã gán</div>
                    <div class="pull-right">
                        <div class="check-action normal red">
                        <input type="checkbox" v-model="allCheckedDaGan" class="check" />
                        <span class="name">Chọn nhanh</span>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    </div>
                    <KTableV3 :columns="columnsDaGan"
                        :dataSources="dataSourcesDaGan"
                        :treeGrid="false"
                        :allChecked="allChecked"
                        :labelCheckColumn="true"
                        @dataCheckeds="onDataCheckedsDaGan"
                        id="kieutt_id"
                        :allowFilter="true"
                        :allowCheckBox="true"/>
                </div>
                <div class="actions">
                    <button class="btn" @click="boGanTT">
                    <span class="fa fa-angle-right"></span>
                    </button>
                    <button class="btn" @click="ganTT">
                    <span class="fa fa-angle-left"></span>
                    </button>
                </div>
                <div class="box-col box-form">
                    <div class="legend-title">Danh sách kiểu thuộc tính chưa gán</div>
                    <KTableV3 :columns="columnsChuaGan"
                        :dataSources="dataSourcesChuaGan"
                        :treeGrid="true"
                        :labelCheckColumn="true"
                        @dataCheckeds="onDataCheckedsChuaGan"
                        id="kieutt_id"
                        :allowFilter="true"
                        :allowCheckBox="true"/>
                </div>
            </div>

        </div>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KTableCustom from './KTableCustom.vue'
import AssignAttTypeToAttributeAPI from './AssignAttTypeToAttributeAPI'
import KTableV3 from './KTableV3.vue'
export default {
    name:'AssignAttTypeToAttributeModal',
    components:{
        ActionTop,
        KTableCustom,
        KTableV3
    },
    data(){
        return{
            actions:[
                {
                    id:'lammoi',
                    name:'Làm mới',
                    active: true,
                    icon_class:'one-reload1'
                },
            ],
            columnsDaGan:[
                {
                    field: "kieu_tt",
                    label: "Kiểu thuộc tính",
                    allowFilter: true
                }
            ],
            dataSourcesDaGan:[],
            allCheckedDaGan:false,
            allChecked:false,
            columnsChuaGan:[
                {
                    field: "kieu_tt",
                    label: "Kiểu thuộc tính",
                    allowFilter: true
                }
            ],
            dataSourcesChuaGan:[],
            danhsach_thuoctinh:[],
            thuoctinh_selected:null,
            dataCheckedGan:[],
            dataCheckedChuaGan:[]
        }
    },
    methods:{
        showModal() {
            this.$refs.popupAATT.show()
        },
        hideModal() {
            this.$refs.popupAATT.hide()
        },
        handleShowModal(){
            this.danhsach_thuoctinh=[]
            this.dataSourcesChuaGan=[]
            this.dataSourcesDaGan=[]
            this.thuoctinh_selected=null
            this.allCheckedDaGan=false
            this.allChecked=false
            setTimeout(()=>{
                this.initDuLieu()
            },500)
            
        },
        onActionClick(action){
            if(action.id=='lammoi'){
                if(this.thuoctinh_selected==null){
                    return
                }
                this.hienthi_ds_gan_chuagan()
            }
        },
        async initDuLieu(){
            this.danhsach_thuoctinh=await this.lay_danhsach_thuoctinh()
            if(this.danhsach_thuoctinh.length>0){
                this.thuoctinh_selected=this.danhsach_thuoctinh[0].THUOCTINH_ID
                await this.hienthi_ds_gan_chuagan()
            }
        },
        async hienthi_ds_gan_chuagan(){
            if(this.thuoctinh_selected==null){
                return
            }
            // this.dataSourcesDaGan=[]
            // this.dataSourcesChuaGan=[]
            let result = await this.lay_ds_kieu_tt_da_and_chua_gan(this.thuoctinh_selected)
            if(result){
                if(result.gan){
                    this.dataSourcesDaGan=result.gan.sort((a, b)=>a.kieu_tt.localeCompare(b.kieu_tt))
                }else{
                    this.dataSourcesDaGan=[]
                }
                if(result.chua_gan){
                    this.dataSourcesChuaGan=result.chua_gan.sort((a, b)=>a.kieu_tt.localeCompare(b.kieu_tt))
                }else{
                    this.dataSourcesChuaGan=[]
                }
            }else{
                this.dataSourcesDaGan=[]
                this.dataSourcesChuaGan=[]
            }

        },
        onDataCheckedsDaGan(data){
            this.dataCheckedGan=data
            
            if(data.length>0&&data.length==this.dataSourcesDaGan.length){
                this.allCheckedDaGan=true
            }else{
                this.allCheckedDaGan=false
            }

        },
        onDataCheckedsChuaGan(data){
            this.dataCheckedChuaGan=data
        },
        async boGanTT(){
            if(this.dataCheckedGan.length==0){
                this.$toast.error('Hãy chọn ít nhất 1 kiểu thuộc tính trước khi gỡ !')
                return
            }
            var kieu_id_arr=[]
            this.dataCheckedGan.forEach((item)=>{
                kieu_id_arr.push(item.kieutt_id)
            })
            var stringArr=kieu_id_arr.toString()
            
            let data={
                thuoctinh_id:this.thuoctinh_selected,
                kieu_id_arr:stringArr
            }
            let result = await this.sp_del_thuoctinh_kieutt(data)
            if(result){
                this.$toast.success('Bỏ gán kiểu thuộc tính vật tư thành công')
                this.dataCheckedGan=[]
                this.dataCheckedChuaGan=[]
                this.allCheckedDaGan=false
                await this.hienthi_ds_gan_chuagan()
            }else{
                this.$toast.error('Lỗi khi bỏ gán kiểu thuộc tính loại vật tư !')
            }
        },
        async ganTT(){
            if(this.dataCheckedChuaGan.length==0){
                this.$toast.error('Hãy chọn ít nhất 1 kiểu thuộc tính trước khi gán !')
                return
            }
            var kieu_id_arr=[]
            this.dataCheckedChuaGan.forEach((item)=>{
                kieu_id_arr.push(item.kieutt_id)
            })
          
            let data={
                thuoctinh_id:this.thuoctinh_selected,
                kieu_id_arr:kieu_id_arr
            }
            let result=await this.sp_ins_thuoctinh_kieutt(data)
            if(result){
                this.$toast.success('Gán kiểu thuộc tính vật tư thành công')
                this.dataCheckedGan=[]
                this.dataCheckedChuaGan=[]
                this.allCheckedDaGan=false
                await this.hienthi_ds_gan_chuagan()
            }else{
                this.$toast.error('Lỗi khi gán kiểu thuộc tính loại vật tư !')
            }
        },
        async changeThuocTinh(data){
            //this.thuoctinh_selected=data.THUOCTINH_ID
            this.allCheckedDaGan=false
            await this.hienthi_ds_gan_chuagan()
        },
        async lay_danhsach_thuoctinh(){
            try{
                this.loading(true)
                this.danhsach_thuoctinh=[]
                let response=await AssignAttTypeToAttributeAPI.lay_danhsach_thuoctinh(this.axios)
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
        compareObjects(object1, object2, key) {
            const obj1 = object1[key].toUpperCase()
            const obj2 = object2[key].toUpperCase()
            if (obj1 < obj2) {
                return -1
            }
            if (obj1 > obj2) {
                return 1
            }
            return 0
        },
        async lay_ds_kieu_tt_da_and_chua_gan(thuoctinh_id){
            try{
                this.loading(true)  
                let response=await AssignAttTypeToAttributeAPI.lay_ds_kieu_tt_da_and_chua_gan(this.axios,thuoctinh_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return null
                }
            }catch(e){
                this.loading(false)
                return null
            }
        },
        async sp_ins_thuoctinh_kieutt(data){
            try{
                this.loading(true)  
                let response=await AssignAttTypeToAttributeAPI.sp_ins_thuoctinh_kieutt(this.axios,data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return true
                }else{
                    return false
                }
            }catch(e){
                this.loading(false)
                return false
            }
        },
        async sp_del_thuoctinh_kieutt(data){
            try{
                this.loading(true)  
                let response=await AssignAttTypeToAttributeAPI.sp_del_thuoctinh_kieutt(this.axios,data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return true
                }else{
                    return false
                }
            }catch(e){
                this.loading(false)
                return false
            }
        }
    },
    watch:{
        allCheckedDaGan(val){
            this.allChecked=val
        }
    }

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