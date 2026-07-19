<template>
    <b-modal
    ref="popupTimKiemMaNhom"
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
        <div class="title">
          <span class="icon one-notepad"></span>Danh sách VT
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <ActionTop :actions="actions"  @onActionClick="onActionClick"/>
      <div class="popup-body">
        <div class="box-col box-form">
            <div class="row">
                <div class="col-sm-8 col-12">
                    <div class="info-row">
                        <div class="key w150">Tìm mã vật tư</div>
                        <div class="value">
                            <input type="text" ref="inputMaVT" @keyup.enter="onEnterMaVT"  :value="input_ma_vt" @change="e=>input_ma_vt=e.target.value" class="form-control highlight" />
                        </div>
                    </div>
                </div>
                <div class="col-sm-4 col-12" style="text-align: center!important;">
                    <div class="actions" style="display: inline-flex !important;">
                        <button class="btn btn-second" style="margin-right: 10px !important;" @click="backPage">
                            <span class="fa fa-angle-left"></span>
                        </button>
                        <input type="text" v-model="input_page_number" readonly style="width:50px!important;" class="form-control highlight" />
                        <button class="btn btn-second" style="margin-left: 10px !important;" @click="nextPage">
                            <span class="fa fa-angle-right"></span>
                        </button>
                    </div>
                </div>
            </div>
            
        </div>
        <div class="box-col box-form">
            <KDataGrid
                :columns="columns"
                :dataSource="dataSources"
                :showColumnCheckbox="false"
                :enable-paging-server="false"
                :allowPaging="true"
                :showFilter="true"
                :enabledSelectFirstRow="true"
                @onRowSelected="onRowSelected"/>
        </div>
        
      </div>
    </div>
    </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import SuppliesAPI from './SuppliesAPI'
export default {
    name:'TimKiemMaNhomModal',
    components:{
        ActionTop,
        KDataGrid
    },
    data(){
        return {
            actions:[
              {
                  id:'lammoi',
                  name:'Làm mới',
                  active: true,
                  icon_class:'one-sync1'
              },
              {
                  id:'chapnhan',
                  name:'Chấp nhận',
                  active: true,
                  icon_class:'ui-1_check-circle-08 nc-icon-glyph'
              }
            ],
            columns:[
                {
                    fieldName:'code',
                    headerText:'Mã VT',
                    allowFiltering:true
                },
                {
                    fieldName:'name',
                    headerText:'Tên VT',
                    allowFiltering:true
                },
                {
                    fieldName:'part_number',
                    headerText:'Part Number',
                    allowFiltering:true
                }
            ],
            dataSources:[],
            input_ma_vt:'',
            input_page_number:'1',
            rowSelected:null
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='lammoi'){
                this.dataSources=[]
                this.input_ma_vt=''
                this.input_page_number='1'
                this.$refs.inputMaVT.focus()
            }else if(action.id=='chapnhan'){
                this.$emit('accept', this.rowSelected)
                this.hideModal()
            }
        },
        showModal() {
            this.$refs["popupTimKiemMaNhom"].show()
        },
        hideModal() {
            this.$refs["popupTimKiemMaNhom"].hide()
        },
        handleShowModal(){
            this.dataSources=[]
            this.actions[1].active=false
            this.input_page_number='1'
            this.input_ma_vt=''
        },
        async backPage(){
            var page_number=Number(this.input_page_number)
            if(page_number<=1){
                return
            }
            page_number-=1
            this.input_page_number=page_number.toString()
            //handled
            await this.hienthi_danhsach()
        },
        async nextPage(){
            var page_number=Number(this.input_page_number)
            page_number+=1
            this.input_page_number=page_number.toString()
            //handler
            await this.hienthi_danhsach()
        },
        onRowSelected(item){    
            this.rowSelected=item
        },
        onEnterMaVT(){
            this.hienthi_danhsach()
        },
        async hienthi_danhsach(){
            if(this.input_ma_vt.trim()==''){
                return
            }
            let data={
                access_token:this.$auth.getToken(),
                code:this.input_ma_vt.trim(),
                page_index:Number(this.input_page_number),
                page_size:100
            }
            let result=await this.get_asset_supplies_category_info(data)
            if(result&&result.success){
                let ds=result.data.filter(x=>x.category_level=='5')
                console.log('ds', ds)
                this.dataSources=ds
            }else{
                this.$toast.error(result.message)
            }
        },
        async get_asset_supplies_category_info(data){
            try{
                this.loading(true)
                let response = await SuppliesAPI.get_asset_supplies_category_info(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    if(response && response.data && response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi không lấy được dữ liệu')
                    } 
                }
            }catch(e){
                this.loading(false)
                if(error.data&&error.data.message){
                    this.$toast.error(error.data.message)
                }else if(error.response&&error.response.data&&error.response.data.message){
                    this.$toast.error(error.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi không lấy được dữ liệu')
                }
                return null
            }
        },
    },
    watch:{
        dataSources(val){
            this.actions[1].active=val.length>0
        }
    }
}
</script>
<style scoped>
.btn-second {
  background-color: #fff;
  border-color: #0176FF;
  color: #0176FF;
  font-weight: 600;
}
.btn-second:disabled {
  border-color: #8c8c8c;
  color: #8c8c8c;
  cursor: not-allowed;
}
.btn-second:hover, .btn-second.active {
  background: #0176FF;
  color: #fff;
}
.btn-second.button-note-bg, .btn-second.button-not-bg {
  background-color: transparent;
}
.btn-second.button-note-bg:hover, .btn-second.button-not-bg:hover {
  background-color: #0176FF;
}
</style>