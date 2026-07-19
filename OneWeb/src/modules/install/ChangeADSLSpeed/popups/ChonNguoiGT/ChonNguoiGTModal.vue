<template>
  <b-modal
    ref="popupChonNguoiGT"
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
          <span class="icon one-notepad"></span> Chọn người giới thiệu
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <ActionTop :actions="actions" @onActionClick="onActionClick" />
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">
            <div class="pull-left">Thông tin tìm kiếm</div>
            <div class="pull-right red">{{ten_ht_selected}}</div>
            <div class="clearfix"></div>
          </div>
          <div class="info-row">
            <div class="key w90">Nhóm</div>
            <div class="value">
              <multiselect
                  :options="ds_nhomnv"
                  placeholder="Chọn nhóm"
                  @input="changeNhomNV"
                  :showLabels="false"
                  :allowEmpty="false"
                  :value="nhomnv_o_selected"
                  label="ten"
                  track-by="loainv_id">
                  <template #noOptions>
                      Không tìm thấy thông tin
                  </template>
                  <template #noResult>
                      Không tìm thấy thông tin
                  </template>
                </multiselect>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">
              <div class="check-action">
                <input type="checkbox" v-model="checkDonVi" @change="changeCheckDonVi" class="check" />
                <span class="name">Đơn vị QL</span>
              </div>
            </div>
            <div class="value">
              <multiselect
                  :options="ds_donvi"
                  placeholder="Chọn đơn vị"
                  @input="changeDonVi"
                  :showLabels="false"
                  :allowEmpty="false"
                  :disabled="!checkDonVi"
                  :value="donvi_o_selected"
                  label="ten_dv"
                  track-by="donvi_id">
                  <template #noOptions>
                    Không tìm thấy thông tin
                  </template>
                  <template #noResult>
                      Không tìm thấy thông tin
                  </template>
                </multiselect>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách nhân viên</div>
          <KTableV3 :columns="columns"
            :dataSources="dataSources"
            :rowSelectedId="rowSelectedId"
            id="nhanvien_id"
            @onSelectedRow="onSelectedRow"
            :allowFilter="true"/>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import API from './API'
export default {
    name:'ChonNguoiGTModal',
    components:{
        KTableV3,
        ActionTop
    },
    computed:{
      donvi_o_selected(){
        if(this.donvi_selected==null){
          return null
        }
        let donvi=this.ds_donvi.find(x=>x.donvi_id==this.donvi_selected)
        return donvi!=undefined?donvi:null
      },
      nhomnv_o_selected(){
        if(this.nhomnv_selected==null){
          return null
        }
        let nhomnv=this.ds_nhomnv.find(x=>x.loainv_id==this.nhomnv_selected)
        return nhomnv!=undefined?nhomnv:null
      },
      ten_ht_selected(){
        return this.rowSelected!=null?this.rowSelected.ten_ht:''
      }
      
    },
    data(){
        return {
            actions:[
                {
                    id:'chapnhan',
                    name:'Chấp nhận',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_check-circle-08'
                }
            ],
            columns:[
                {
                    field:'stt',
                    label:'STT',
                    allowFilter:true
                },
                {
                    field:'ma_the',
                    label:'Mã thẻ',
                    allowFilter:true
                },
                {
                    field:'ma_nv',
                    label:'Mã nhân viên',
                    allowFilter:true
                },
                {
                    field:'ma_dv',
                    label:'Mã đơn vị',
                    allowFilter:true
                },
                {
                    field:'ten_nv',
                    label:'Tên nhân viên',
                    allowFilter:true
                },
                {
                    field:'ten_dv',
                    label:'Tên đơn vị',
                    allowFilter:true
                }
            ],
            dataSources:[],
            ds_donvi:[],
            donvi_selected:null,
            ds_nhomnv:[],
            nhomnv_selected:null,
            checkDonVi:false,
            rowSelected:null,
            rowSelectedId:-1
        }
    },
    methods:{
        showModal() {
            this.$refs["popupChonNguoiGT"].show()
        },
        hideModal() {
            this.$refs["popupChonNguoiGT"].hide()
        },
        handleShowModal(){
            //init clear data
            this.ds_donvi=[]
            this.donvi_selected=null
            this.ds_nhomnv=[]
            this.nhomnv_selected=null
            this.dataSources=[]
            this.checkDonVi=false
            this.rowSelected=null
            this.actions[this.actions.findIndex(x=>x.id=='chapnhan')].active=false

            //khởi tạo
            setTimeout(()=>{
                this.initDuLieu()
            },200)
        },  
        onActionClick(action){
            if(action.id=='chapnhan'){
              if(this.rowSelected==null){
                return
              }
              //callback model nhanvien
              this.$emit('accept',{
                nguoi_gt_id:this.rowSelected.nhanvien_id,
                ten_nguoi_gt:this.rowSelected.ten_nv,
                ma_nguoi_gt:this.rowSelected.ma_nv,
                loainv_id:this.nhomnv_selected,
                donvi_id:(this.checkDonVi&&this.donvi_selected!=null)?this.donvi_selected:0
              })
              this.hideModal()
            }
        },
        onSelectedRow(item){
          this.rowSelected=item

        },  
        changeDonVi(data){
          this.donvi_selected=data.donvi_id
          //handle ds
          this.hienthi_nhanvien()
        },
        changeNhomNV(data){
          this.nhomnv_selected=data.loainv_id
          //handle ds
          this.hienthi_nhanvien()
        },
        changeCheckDonVi(){
          this.hienthi_nhanvien()
        },
        async lay_ds_donvi_ctv(){
          try{
            this.loading(true)
            let response = await API.lay_ds_donvi_ctv(this.axios)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              this.ds_donvi=response.data.data
              if(this.ds_donvi.length>0){
                this.donvi_selected=this.ds_donvi[0].donvi_id
              }
            }else{
              this.ds_donvi=[]
            }
          }catch(e){
            this.$toast.error('Không load được danh sách đơn vị')
          }finally{
            this.loading(false)
          }     
        },
        async sp_lay_ds_loai_nv(){
          try{
            this.loading(true)
            let response = await API.sp_lay_ds_loai_nv(this.axios)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              this.ds_nhomnv=response.data.data
              if(this.ds_nhomnv.length>0){
                this.nhomnv_selected=this.ds_nhomnv[0].loainv_id
              }
            }else{
              this.ds_nhomnv=[]
            }
          }catch(e){
            this.$toast.error('Không load được danh sách loại nhóm')
          }finally{
            this.loading(false)
          }     
        },
        async sp_lay_ds_nhanvien(donvi_id, loainv_id){
          try{
            this.loading(true)
            this.dataSources=[]
            let response = await API.sp_lay_ds_nhanvien(this.axios, donvi_id, loainv_id)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              this.dataSources=response.data.data
              if(this.dataSources.length>0){
                this.rowSelectedId=this.dataSources[0].nhanvien_id
                this.onSelectedRow(this.dataSources[0])
              }
            }else{
              this.dataSources=[]
            }
          }catch(e){
            this.$toast.error('Không load được danh sách nhân viên')
          }finally{
            this.loading(false)
          }     
        },
        async hienthi_nhanvien(){
          let donvi_id=(this.checkDonVi&&this.donvi_selected!=null)?this.donvi_selected:-1
          let loainv=this.nhomnv_selected!=null?this.nhomnv_selected:-1
          this.rowSelected=null
          await this.sp_lay_ds_nhanvien(donvi_id, loainv)
        },
        async initDuLieu(){
            await this.lay_ds_donvi_ctv()
            await this.sp_lay_ds_loai_nv()
            await this.hienthi_nhanvien()
        }
    },
    watch:{
      rowSelected(val){
        if(val!=null){
          this.actions[this.actions.findIndex(x=>x.id=='chapnhan')].active=true
        }else{
          this.actions[this.actions.findIndex(x=>x.id=='chapnhan')].active=false
        }
      }
    }
}
</script>
<style>
.multiselect, .multiselect__input, .multiselect__single {
    font-size: 1rem !important;
}
.multiselect__tags{
    min-height: 32px !important;
    border: 1px solid #ced4da !important;
    padding: 6px 32px 0 6px;
    border-radius: 4px;
}
.multiselect__placeholder {
    margin-bottom: 5px!important;
    padding-top: 0px!important;
}
.multiselect__select{
    top: 0px!important;
    padding: 10px 9px!important;
}
.multiselect--disabled{
  background: none!important;
}
.multiselect--disabled .multiselect__current, .multiselect--disabled .multiselect__select{
  background: none!important;
}
</style>