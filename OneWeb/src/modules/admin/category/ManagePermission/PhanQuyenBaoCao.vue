<template>
  <div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w80">Nhóm BC</div>
          <div class="value">
              <div class="select-custom" ref="ds_nhom_bc">
                  <select2 :settings="{ dropdownParent: $refs['ds_nhom_bc'] }" ref="cboNhomBC"
                      v-model="nhombaocao_selected" class="select2"
                      :options="danhsach_nhombaocao.map(e=> ({id: e.NHOM_BC_ID, text: e.TEN_NHOM}))"
                      @change="changeNhomBC"
                  >
                  </select2>
              </div>  
          </div>
        </div>
      </div>
    </div>
    <div class="box-move-select-table mart10">
      <div class="box-col">
        <!-- <KTableV3 :columns="columnsChuaGan"
          :dataSources="dataSourcesChuaGan"
          @dataCheckeds="onDataCheckedsChuaGan"
          :allowCheckBox="true"
          id="baocao_id"
          :allowFilter="true"/> -->
          
          <KDataGrid
            :columns="columns"
            :dataSource="dataSourcesChuaGan"
            :showColumnCheckbox="true"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            ref="gridChuaGan"
            :enabledSelectFirstRow="false"/>
      </div>
      <div class="actions">
        <button class="btn btn-main" @click="ganQuyen">
          <span class="-ap icon-plus3"></span>
        </button>
        <button class="btn btn-main" @click="boGanQuyen">
          <span class="-ap icon-minus3"></span>
        </button>
      </div>
      <div class="box-col">
        <!-- <KTableV3 :columns="columnsGan"
          :dataSources="dataSourcesGan"
          @dataCheckeds="onDataCheckedsGan"
          id="baocao_id"
          :allowCheckBox="true"
          :allowFilter="true"/> -->
          <KDataGrid
            :columns="[
            {
                fieldName:'baocao_id',
                headerText:'ID',
                allowFiltering:true,
                width:'60'
            },
            {
                fieldName:'ten_bc',
                headerText:'Báo cáo đã gán',
                allowFiltering:true
            }
          ]"
            :dataSource="dataSourcesGan"
            :showColumnCheckbox="true"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            ref="gridDaGan"
            :enabledSelectFirstRow="false"/>
          
      </div>
    </div>
  </div>
</template>
<script>
import ManagePermissionAPI from './ManagePermissionAPI'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import { DataManager,Query,ODataV4Adaptor,Predicate } from "@syncfusion/ej2-data"
export default {
    name:'PhanQuyenBaoCao',
    components:{
      KDataGrid
    },
    props:{
      loai_ct_id:{
        type:[Number, String],
        default:-1
      },
      quyen_id:{
        type:[Number, String],
        default:-1
      }
    },
    data(){
      return{
        danhsach_nhombaocao:[],
        nhombaocao_selected:null,
        columns:[
          {
              fieldName:'baocao_id',
              headerText:'ID',
              allowFiltering:true,
              width:'60'
          },
          {
              fieldName:'ten_bc',
              headerText:'Báo cáo chưa gán',
              allowFiltering:true
          }
        ],
        dataSourcesChuaGan:[],
        dataSourcesGan:[]
      }
    },
    methods:{
      changeNhomBC(){
        this.dataCheckGan=[]
        if(this.nhombaocao_selected!=null){
          this.danhsach_hienthi_gan_chuagan(this.quyen_id)
        }
      },
      changeQuyen(value){
        if(this.nhombaocao_selected!=null){
          this.danhsach_hienthi_gan_chuagan(value)
        }
      },
      ganQuyen(){
        let dataCheckedsChuaGan=this.$refs.gridChuaGan.getSelectedRecords()
        if(dataCheckedsChuaGan.length==0){
            return
        }
        let baocao_id_arr=dataCheckedsChuaGan.map(x=>x.baocao_id)
        this.sp_action_ds_quyen_baocao(this.nhombaocao_selected,  baocao_id_arr, this.quyen_id, 1)
      },
      boGanQuyen(){
        let dataCheckedsGan=this.$refs.gridDaGan.getSelectedRecords()
        if(dataCheckedsGan.length==0){
            return
        }
        let baocao_id_arr=dataCheckedsGan.map(x=>x.baocao_id)
        this.sp_action_ds_quyen_baocao(this.nhombaocao_selected,  baocao_id_arr, this.quyen_id, 0)
      },
      
      async danhsach_hienthi_gan_chuagan(quyen_id){
        this.dataSourcesGan=[]
        this.dataSourcesChuaGan=[]
        let result=await this.sp_lay_ds_baocao_quyen(this.nhombaocao_selected, quyen_id)
        if(result){
          this.dataSourcesGan=result.bc?result.bc:[]
          this.dataSourcesChuaGan=result.bc1?result.bc1:[]
        }
      },
      async sp_lay_ds_baocao_quyen(nhom_bc_id, quyen_id){
        try{
              this.loading(true)
              let response=await ManagePermissionAPI.sp_lay_ds_baocao_quyen(this.axios, nhom_bc_id, quyen_id)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                  return response.data.data
              }else{
                  return []
              }
          }catch(e){
            this.loading(false)
            if(e.data&&e.data.message){
                this.$toast.error(e.data.message)
            }else if(e.response&&e.response.data&&e.response.data.message){
                this.$toast.error(e.response.data.message)
            }else{
                this.$toast.error('Đã có lỗi khi lấy danh sách báo cáo')
            }
            return []
          }
      },
      async lay_danhsach_nhom_baocao(){
          try{
              this.loading(true)
              let response=await ManagePermissionAPI.lay_danhsach_nhom_baocao(this.axios)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                  return response.data.data
                  
              }else{
                  return []
              }
          }catch(e){
              this.loading(false)
              if(e.data&&e.data.message){
                  this.$toast.error(e.data.message)
              }else if(e.response&&e.response.data&&e.response.data.message){
                  this.$toast.error(e.response.data.message)
              }else{
                  this.$toast.error('Đã có lỗi khi lấy danh sách nhóm báo cáo')
              }
              return []
          }
      },
      async sp_phanquyen_baocao_update(data){
          try{
              this.loading(true)
              let response = await ManagePermissionAPI.sp_phanquyen_baocao_update(this.axios,data)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.$toast.success('Gán quyền báo cáo thành công!')
                    this.danhsach_hienthi_gan_chuagan(this.quyen_id)
              }else{
                  if(response && response.data &&response.data.message){
                    this.$toast.error(response.data.message)
                  }else {
                    this.$toast.error('Đã xảy ra lỗi, gán quyền báo cáo không thành công')
                  }  
              }
          }catch(error){
              this.loading(false)
              if(error.data&&error.data.message){
                this.$toast.error(error.data.message)
              }else if(error.response&&error.response.data&&error.response.data.message){
                this.$toast.error(error.response.data.message)
              }else{
                this.$toast.error('Đã xảy ra lỗi, gán quyền báo cáo không thành công')
              }
          }
      },
      async sp_phanquyen_baocao_delete(data){
          try{
              this.loading(true)
              let response = await ManagePermissionAPI.sp_phanquyen_baocao_delete(this.axios,data)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.$toast.success('Bỏ gán quyền báo cáo thành công!')
                    this.danhsach_hienthi_gan_chuagan(this.quyen_id)
              }else{
                  if(response && response.data &&response.data.message){
                    this.$toast.error(response.data.message)
                  }else {
                    this.$toast.error('Đã xảy ra lỗi, bỏ gán quyền báo cáo không thành công')
                  }  
              }
          }catch(error){
              this.loading(false)
              if(error.data&&error.data.message){
                  this.$toast.error(error.data.message)
              }else if(error.response&&error.response.data&&error.response.data.message){
                  this.$toast.error(error.response.data.message)
              }else{
                  this.$toast.error('Đã xảy ra lỗi, bỏ gán quyền báo cáo không thành công')
              }
          }
      },
      async sp_action_ds_quyen_baocao(nhom_bc_id,  baocao_id_arr, quyen_id, kieu){
          try{
              this.loading(true)
              let response = await ManagePermissionAPI.sp_action_ds_quyen_baocao(this.axios, nhom_bc_id,  baocao_id_arr, quyen_id, kieu)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.dataSourcesGan=[]
                    this.dataSourcesChuaGan=[]
                    if(kieu==1){
                      this.$toast.success('Gán quyền báo cáo thành công!')
                    }else{
                      this.$toast.success('Bỏ gán quyền báo cáo thành công!')
                    }
                    if(response.data.data.bc){
                      this.$nextTick(()=>{
                        this.dataSourcesGan=response.data.data.bc
                      })
                    }
                    if(response.data.data.bc1){
                      this.$nextTick(()=>{
                        this.dataSourcesChuaGan=response.data.data.bc1
                      })
                    }
                    
              }else{
                  if(response && response.data &&response.data.message){
                    this.$toast.error(response.data.message)
                  }else {
                    if(kieu==1){
                      this.$toast.error('Đã xảy ra lỗi, gán quyền báo cáo không thành công')
                    }else{
                      this.$toast.error('Đã xảy ra lỗi, bỏ gán quyền báo cáo không thành công')
                    }
                  }  
              }
          }catch(error){
              this.loading(false)
              if(error.data&&error.data.message){
                  this.$toast.error(error.data.message)
              }else if(error.response&&error.response.data&&error.response.data.message){
                  this.$toast.error(error.response.data.message)
              }else{
                  if(kieu==1){
                    this.$toast.error('Đã xảy ra lỗi, gán quyền báo cáo không thành công')
                  }else{
                    this.$toast.error('Đã xảy ra lỗi, bỏ gán quyền báo cáo không thành công')
                  }
              }
          }
      },
      async initData(){
        this.dataSourcesChuaGan=[]
        this.dataSourcesGan=[]
        this.danhsach_nhombaocao=[]
        this.nhombaocao_selected=null
        
        let result=await this.lay_danhsach_nhom_baocao()
        if(this.loai_ct_id==-1){
          this.danhsach_nhombaocao=result
        }else{
            this.danhsach_nhombaocao=result.filter(x=>x.LOAI_CT_ID==this.loai_ct_id)
        }
        if(this.danhsach_nhombaocao.length>0){
          this.nhombaocao_selected=this.danhsach_nhombaocao[0].NHOM_BC_ID
          await this.danhsach_hienthi_gan_chuagan(this.quyen_id)
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
</style>