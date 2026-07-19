<template>
  <b-modal
    ref="popupChonCongTacVien"
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
          <span class="icon one-notepad"></span> Chọn cộng tác viên
        </div>
        <div class="close -ap icon-close" @click="closePopup"></div>
      </div>
      <ActionTop :actions="actions" @onActionClick="onActionClick" />
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">
            <div class="pull-left">Thông tin tìm kiếm</div>
            <div class="pull-right bold red" v-if="rowSelected!=null">Cộng tác viên: {{ten_ht_selected}}</div>
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
          <KDataGrid
              :columns="columns"
              :dataSource="dataSources"
              :enable-paging-server="false"
              :allowPaging="true"
              :showFilter="true"
              :showColumnCheckbox="false"
              :enabledSelectFirstRow="true"
              @selectedItemsChanged="gridDanhSachCTVChanged"
          />
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import KDataGrid from '@/components/kylq_components/KDataGrid'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import API from './API'
export default {
    name:'ChonCongTacVienModal',
    components:{
        KDataGrid,
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
                    fieldName:'stt',
                    headerText:'STT',
                    allowFiltering:true,
                    width:70
                },
                {
                    fieldName:'ma_the',
                    headerText:'Mã thẻ',
                    allowFiltering:true,
                    width:120
                },
                {
                    fieldName:'ma_nv',
                    headerText:'Mã nhân viên',
                    allowFiltering:true,
                    width:120
                },
                {
                    fieldName:'ma_dv',
                    headerText:'Mã đơn vị',
                    allowFiltering:true,
                    width:120
                },
                {
                    fieldName:'ten_nv',
                    headerText:'Tên nhân viên',
                    allowFiltering:true,
                    width:120
                },
                {
                    fieldName:'ten_dv',
                    headerText:'Tên đơn vị',
                    allowFiltering:true,
                    width:120
                }
            ],
            dataSources:[],
            ds_donvi:[],
            donvi_selected:null,
            ds_nhomnv:[],
            nhomnv_selected:null,
            checkDonVi:false,
            rowSelected:null
        }
    },
    methods:{
        showModal() {
            this.$refs["popupChonCongTacVien"].show()
        },
        hideModal() {
            this.$refs["popupChonCongTacVien"].hide()
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
              //callback model
              this.$emit('accept',{
                ctv_id:this.rowSelected.nhanvien_id,
                ten_ctv:this.rowSelected.ten_nv,
                ma_ctv:this.rowSelected.ma_nv,
                loainv_id:this.nhomnv_selected,
                donvi_id:(this.checkDonVi&&this.donvi_selected!=null)?this.donvi_selected:0
              })
              this.hideModal()
            }
        },
        closePopup(){
          this.$emit('closePopup')
          this.hideModal()
        },
        gridDanhSachCTVChanged(data){
          if(this.dataSources.length<=0||data.length<=0){
            this.rowSelected=null
            return
          }
          this.rowSelected=data[0]
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
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              return response.data.data
            }else{
              return []
            }
          }catch(e){
            this.loading(false)
            this.$toast.error('Không load được danh sách đơn vị')
            return []
          }  
        },
        async sp_lay_ds_loai_nv(){
          try{
            this.loading(true)
            let response = await API.sp_lay_ds_loai_nv(this.axios)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              return response.data.data
            }else{
              return []
            }
          }catch(e){
            this.loading(false)
            this.$toast.error('Không load được danh sách loại nhóm')
            return []
          }   
        },
        async sp_lay_ds_nhanvien(donvi_id, loainv_id){
          try{
            this.loading(true)
            let response = await API.sp_lay_ds_nhanvien(this.axios, donvi_id, loainv_id)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              return response.data.data
            }else{
              return []
            }
          }catch(e){
            this.$toast.error('Không load được danh sách nhân viên')
            return[]
          }   
        },
        async hienthi_nhanvien(){
          let donvi_id=(this.checkDonVi&&this.donvi_selected!=null)?this.donvi_selected:-1
          let loainv=this.nhomnv_selected!=null?this.nhomnv_selected:-1
          this.rowSelected=null
          this.dataSources=await this.sp_lay_ds_nhanvien(donvi_id, loainv)
        },
        async initDuLieu(){
          this.ds_donvi=await this.lay_ds_donvi_ctv()
          if(this.ds_donvi.length>0){
            this.donvi_selected=this.ds_donvi[0].donvi_id
          }
          this.ds_nhomnv=await this.sp_lay_ds_loai_nv()
          if(this.ds_nhomnv.length>0){
            this.nhomnv_selected=this.ds_nhomnv[0].loainv_id
          }
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