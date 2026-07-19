<template>
  <div class="box-form">
    <div class="legend-title">
      <span @click="clickShowHideThuNgan" class="fa icon" :class="{'fa-angle-up':expadedThuNgan, 'fa-angle-down':!expadedThuNgan,}"></span> Thu ngân
    </div>
    <transition name="slide">
      <div class="box-move-select-table" v-show="expadedThuNgan">
        <div class="box-col">
          <div class="title-bg-main">
            <span class="title">Thu ngân đã gán</span>
          </div>
          <KDataGrid
            :columns="columnsGan"
            :dataSource="dataSourcesGan"
            :showColumnCheckbox="true"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            ref="gridDaGan"
            :enabledSelectFirstRow="false"
            panelDataHeight="160px"/>
        </div>
        <div class="actions">
          <button class="btn" @click="boGan">
            <span class="-ap icon-chevron-thin-right"></span>
          </button>
          <button class="btn" @click="gan">
            <span class="-ap icon-chevron-thin-left"></span>
          </button>
        </div>
        <div class="box-col">
          <div class="title-bg-main">
            <span class="title">Thu ngân chưa gán</span>
          </div>
          <div class="info-row">
            <div class="key w70">
              <div class="check-action">
                <input type="checkbox" v-model="check_donvi" @change="changeCheckDonvi" class="check" />
                <span class="name">Đơn vị</span>
              </div>
            </div>
            <div class="value">
                <div class="select-custom" ref="ds_donvi">
                    <select2 :settings="{ dropdownParent: $refs['ds_donvi'] }" ref="cboDonVi"
                        v-model="donvi_selected" class="select2"
                        :options="danhsach_donvi.map(e=> ({id: e.donvi_id, text: e.ten_ht}))"
                        @change="changeDonVi"
                        :disabled="!check_donvi"
                    >
                    </select2>
                </div>  
            </div>
          </div>
          <KDataGrid
              :columns="columnsChuaGan"
              :dataSource="dataSourcesChuaGan"
              :showColumnCheckbox="true"
              :enable-paging-server="false"
              :allowPaging="true"
              :showFilter="true"
              ref="gridChuaGan"
              :enabledSelectFirstRow="false"
              panelDataHeight="120px"/>
        </div>
      </div>
    </transition>
    
  </div>
</template>
<script>
import UserAPI from './UserAPI'
import KDataGrid from '@/components/kylq_components/KDataGrid'
export default {
    name:'ThuNgan',
    components:{
        KDataGrid
    },
    data(){
        return {
            columnsGan:[
                {
                    fieldName:'ma_nd',
                    headerText:'Mã ND',
                    allowFiltering:true,
                    width:100
                },
                {
                    fieldName:'ma_nv',
                    headerText:'Mã NV',
                    allowFiltering:true,
                    width:100
                },
                 {
                    fieldName:'ten_nd',
                    headerText:'Tên người dùng',
                    allowFiltering:true,
                    width:100
                },
                {
                    fieldName:'ten_nv',
                    headerText:'Tên nhân viên',
                    allowFiltering:true,
                    width:100
                },
                {
                    fieldName:'ten_dv',
                    headerText:'Đơn vị',
                    allowFiltering:true,
                    width:100
                }
            ],
            dataSourcesGan:[],
            columnsChuaGan:[
                {
                    fieldName:'ma_nd',
                    headerText:'Mã ND',
                    allowFiltering:true,
                    width:100
                },
                {
                    fieldName:'ma_nv',
                    headerText:'Mã NV',
                    allowFiltering:true,
                    width:100
                },
                 {
                    fieldName:'ten_nd',
                    headerText:'Tên người dùng',
                    allowFiltering:true,
                    width:100
                },
                {
                    fieldName:'ten_nv',
                    headerText:'Tên nhân viên',
                    allowFiltering:true,
                    width:100
                },
                {
                    fieldName:'ten_dv',
                    headerText:'Đơn vị',
                    allowFiltering:true,
                    width:100
                }
            ],
            dataSourcesChuaGan:[],
            danhsach_donvi:[],
            donvi_selected:null,
            check_donvi:true,
            nguoidung_id:-1,
            expadedThuNgan:true,
            loiLoadThuNgan:false
        }
    },
    methods:{
        clickShowHideThuNgan(){
          this.expadedThuNgan=!this.expadedThuNgan
          this.$emit('expadedThuNgan', this.expadedThuNgan)
        },
        boGan(){
          let dataCheckGan=this.$refs.gridDaGan.getSelectedRecords()
          if(dataCheckGan.length==0){
              return
          }
          for(let i=0;i<dataCheckGan.length;i++){
              this.dataSourcesChuaGan.push(dataCheckGan[i])
              const index=this.dataSourcesGan.findIndex(x=>x.nguoidung_id==dataCheckGan[i].nguoidung_id)
              if(index>-1){
                  this.dataSourcesGan.splice(index,1)
              }
          }
          this.dataSourcesGan=[].concat(this.dataSourcesGan)
          this.dataSourcesChuaGan=[].concat(this.dataSourcesChuaGan)
        },
        gan(){
          let dataCheckeds=this.$refs.gridChuaGan.getSelectedRecords()
          if(dataCheckeds.length==0){
              return
          }
          for(let i=0;i<dataCheckeds.length;i++){
              this.dataSourcesGan.push(dataCheckeds[i])
              const index=this.dataSourcesChuaGan.findIndex(x=>x.nguoidung_id==dataCheckeds[i].nguoidung_id)
              if(index>-1){
                  this.dataSourcesChuaGan.splice(index,1)
              }
          }
          this.dataSourcesGan=[].concat(this.dataSourcesGan)
          this.dataSourcesChuaGan=[].concat(this.dataSourcesChuaGan)
        },
        async initDuLieu(){

          this.danhsach_donvi=await this.lay_danhsach_donvicha()
          if(this.danhsach_donvi.length>0){
            this.donvi_selected=this.danhsach_donvi[0].donvi_id
          }

          await this.hienthi_ds_thungan(-1)
        },
        async clearDuLieu(){
          this.dataSourcesGan=[]
          this.dataSourcesChuaGan=[]
          this.dataCheckGan=[]
          this.dataCheckChuaGan=[]

        },
        async changeCheckDonvi(){
          this.dataSourcesChuaGan = []
          this.dataSourcesChuaGan = await this.sp_ds_nguoidung_thungan(this.nguoidung_id, this.donvi_selected, 0, this.check_donvi?1:0)
        },
        async changeDonVi(){
          this.dataSourcesChuaGan = []
          this.dataSourcesChuaGan = await this.sp_ds_nguoidung_thungan(this.nguoidung_id, this.donvi_selected, 0, this.check_donvi?1:0)
        },
        getDsThuNganGan(){
          return this.dataSourcesGan
        },
        async hienthi_ds_thungan(nguoidung_id){
          this.nguoidung_id=nguoidung_id
          //gán
          this.dataSourcesGan=[]
          this.dataSourcesGan=await this.sp_ds_nguoidung_thungan(this.nguoidung_id, this.donvi_selected, 1, 0)
          //chưa gán
          this.dataSourcesChuaGan = []
          this.dataSourcesChuaGan = await this.sp_ds_nguoidung_thungan(this.nguoidung_id, this.donvi_selected, 0, this.check_donvi?1:0)
         
        },
        async lay_danhsach_donvicha(){
            try{
                this.loading(true)
                let response= await UserAPI.lay_danhsach_donvicha(this.axios)
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
        async sp_ds_nguoidung_thungan(nguoidung_id, donvi_id, type, checked){
            try{
                this.loading(true)
                let response= await UserAPI.sp_ds_nguoidung_thungan(this.axios, nguoidung_id, donvi_id, type, checked)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                  if(response.data.error_code != 'BSS-00000204'){
                    this.loiLoadThuNgan=true
                    //this.$toast.error(response.data.message)
                  }
                  return []
                    
                }
            }catch(e){
               this.loading(false)
               this.loiLoadThuNgan=true
              //  if(e.data&&e.data.message){
              //       this.$toast.error(e.data.message)
              //   }else if(e.response&&e.response.data&&e.response.data.message){
              //       this.$toast.error(e.response.data.message)
              //   }else{
              //       this.$toast.error('Đã xảy ra lỗi, không load được danh sách thu ngân')
              //   }
               return []
            }
        },
    }
}
</script>
<style>
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
</style>