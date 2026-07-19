<template>
  <b-modal
    ref="popupDuAn"
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
          <span class="icon one-notepad"></span> Cập nhật dự án cho hợp đồng
          thuê bao
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>

      <ActionTop :actions="actions" @onActionClick="onActionClick"/>

      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Danh sách dự án</div>
          <div class="info-row">
            <div class="key w120">
              <span class="name">Dự án</span>
            </div>
            <div class="value">
              <!-- <div class="select-custom">
                <select name="" id="" class="form-control">
                  <option value=""></option>
                </select>
              </div> -->
              <multiselect
                  :options="ds_duan"
                  placeholder="Chọn dự án"
                  @input="changeDuAn"
                  :showLabels="false"
                  :allowEmpty="false"
                  :value="duan_o_selected"
                  label="duan_id"
                  track-by="duan_id">
                  <template #noOptions>
                    Không tìm thấy thông tin
                  </template>
              </multiselect>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
//frmDuAn_HDTB.cs chưa có api
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import API from './API'
//UR2.5.010_048_001 from dùng chung
//Chưa có cột tên dự án, bổ sung sau
export default {
    name:'DuAnModal',
    props:{
      hdtb_id:{
        type:Number,
        default:0
      }
    },
    components:{
        ActionTop
    },
    data(){
        return {
            actions:[
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save'
                },
                {
                    id:'xoa',
                    name:'Xoá',
                    active: true,
                    icon_class:'one-trash'
                }
            ],
            ds_duan:[],
            duan_selected:null
        }
    },
    computed:{
      duan_o_selected(){
        const duan=this.ds_duan.find(x=>x.duan_id==this.duan_selected)
        return duan!=undefined?duan:null
      }
    },
    methods:{
        async onActionClick(action){
            if(action.id=='ghilai'){
              if(this.duan_selected==null){
                this.$toast.error('Bạn chưa chọn dự án !')
                return
              }
              let result =await this.update_duan_hdtb(this.hdtb_id, this.duan_selected)
              if(result){
                this.$toast.success('Cập nhật dự án thành công !')
              }else{
                this.$toast.error('Đã xảy ra lỗi cập nhật không thành công!')
              }

            }else if(action.id=='xoa'){
              let result =await this.update_duan_hdtb(this.hdtb_id, "")
              if(result){
                this.$toast.success('Xóa dự án thành công !')
                this.duan_selected=null
              }else{
                this.$toast.error('Xoá dự án không thành công!')
              }
            }
        },
        showModal() {
            this.$refs["popupDuAn"].show()
        },
        hideModal() {
            this.$refs["popupDuAn"].hide()
        },
        handleShowModal(){
          this.ds_duan=[]
          this.duan_selected=null

          //
          setTimeout(()=>{
            this.initDuLieu()
          },500)



        },
        
        async initDuLieu(){
          this.ds_duan=await this.lay_ds_duan()
          let duan_hdtb=await this.lay_ds_hdtb_by_id(this.hdtb_id)
          if(duan_hdtb.length>0){
            this.duan_selected=duan_hdtb[0].duan_id
          }
        },
        changeDuAn(data){
          this.duan_selected=data.duan_id
        },
        async lay_ds_duan(){
            try{
                this.loading(true)
                let response=await API.lay_ds_duan(this.axios)
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
        async lay_ds_hdtb_by_id(hdtbId){
            try{
                this.loading(true)
                let response=await API.lay_ds_hdtb_by_id(this.axios, hdtbId)
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
        async update_duan_hdtb(hdtb_id, duan_id){
            try{
                this.loading(true)
                let response=await API.update_duan_hdtb(this.axios, hdtb_id, duan_id)
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