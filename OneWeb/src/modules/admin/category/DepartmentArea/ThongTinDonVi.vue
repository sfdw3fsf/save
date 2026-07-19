<template>
  <div class="col-sm-6 col-12">
    <div class="box-form" style="height: 900px">
      <div class="legend-title">Thông tin đơn vị</div>

      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w80">Loại đơn vị</div>
            <div class="value">
                <div class="select-custom" ref="ds_loai_dv">
                    <select2 :z-index="2000" :settings="{ dropdownParent: $refs['ds_loai_dv'] }" ref="cboLoaiDV"
                        v-model="loai_donvi_selected" class="select2"
                        :options="ds_loai_donvi.map(e=> ({id: e.LOAIDV_ID, text: e.TEN_LOAIDV}))"
                        @change="onChangeLoaiDonvi">
                    </select2>
                </div>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w70">Đơn vị QL</div>
            <div class="value">
                <div class="select-custom" ref="ds_donvi_ql">
                    <select2 :z-index="2000" :settings="{ dropdownParent: $refs['ds_donvi_ql'] }" ref="cboDonViQL"
                        v-model="donvi_ql_selected" class="select2"
                        :options="ds_donvi_ql.map(e=> ({id: e.DONVI_ID, text: e.TEN_DV}))"
                        @change="changeDonViQL">
                    </select2>
                </div>
            </div>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="title-bg-main">
            <div class="pull-left title">Danh sách đơn vị</div>
            <div class="pull-right">
              <button class="btn btn-main pad4 lh14 btn-main11" @click="loadDanhsachDonvi">
                <span class="nc-icon-glyph arrows-1_refresh-69"></span>
              </button>
            </div>
            <div class="clearfix"></div>
          </div>
          <KDataGrid 
              ref="gridDonVi"
              :columns="columnsDV" 
              :dataSource="dataSourcesDV"
              :showColumnCheckbox="false"
              :enable-paging-server="false"
              :allowPaging="true"
              :showFilter="true"
              :enabledSelectFirstRow="true"
              @selectedItemsChanged="selectedItemsChangedDV"
          />
        </div>
        <div class="col-sm-6 col-12">
          <div class="title-bg-main">
            <div class="pull-left title">Dữ liệu nạp</div>
            <div class="pull-right">
              <button class="btn btn-main pad4 lh14 btn-main11" @click="loadDuLieuNap">
                <span class="nc-icon-glyph arrows-1_refresh-69"></span>
              </button>
            </div>
            <div class="clearfix"></div>
          </div>
            <KDataGrid 
              ref="gridDuLieuNap"
              :columns="columns" 
              :dataSource="dataSources"
              :showColumnCheckbox="true"
              :enable-paging-server="false"
              :allowPaging="true"
              :showFilter="true"
              :enabledSelectFirstRow="false"
            />
        </div>
      </div>

    </div>
  </div>
</template>
<script>
import DepartmentAreaAPI from './DepartmentAreaAPI'
import KDataGrid from '@/components/kylq_components/KDataGrid'
export default {
    name:'DepartmentArea',
    components:{
        KDataGrid
    },
    data(){
        return {
            loai_donvi_selected:null,
            ds_loai_donvi:[],
            donvi_ql_selected:null,
            ds_donvi_ql:[],

            //donvi_id, ten_dv
            columnsDV:[
                {
                  fieldName: 'ten_dv',
                  headerText: 'Tên đơn vị',
                  allowFiltering: true,
                  allowSorting: true,
                  width:120
                },
            ],
            dataSourcesDV:[],
            //Tên phường, Tên phố, Tên ấp, Tên khu, Tên Quận ,Đặc điểm, Đơn vị quản lý
            columns:[
                {
                    fieldName: "ten_phuong",
                    headerText: "Tên phường",
                    allowFiltering: true,
                    allowSorting: true,
                    width:120
                },
                {
                    fieldName: "ten_pho",
                    headerText: "Tên phố",
                    allowFiltering: true,
                    allowSorting: true,
                    width:120
                },
                {
                    fieldName: "ten_ap",
                    headerText: "Tên ấp",
                    allowFiltering: true,
                    allowSorting: true,
                    width:120
                },
                {
                    fieldName: "ten_khu",
                    headerText: "Tên khu",
                    allowFiltering: true,
                    allowSorting: true,
                    width:120
                },
                {
                    fieldName: "ten_quan",
                    headerText: "Tên Quận",
                    allowFiltering: true,
                    allowSorting: true,
                    width:120
                },
                {
                    fieldName: "dacdiem",
                    headerText: "Đặc điểm",
                    allowFiltering: true,
                    allowSorting: true,
                    width:120
                },
                {
                    fieldName: "donvi_ql",
                    headerText: "Đơn vị quản lý",
                    allowFiltering: true,
                    allowSorting: true,
                    width:120
                },
            ],
            dataSources:[],
            donvi_id_selected:null,
            donvi_id:0
        }
    },
    methods:{
        async lay_danhsach_loai_donvi(){
            try{
              let response=await DepartmentAreaAPI.lay_danhsach_loai_donvi(this.axios,{})
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                this.ds_loai_donvi=response.data.data
                if(this.ds_loai_donvi.length>0){
                  this.loai_donvi_selected=this.ds_loai_donvi[0].LOAIDV_ID
                  this.loadDanhsachDonvi()
                  //TEN_LOAIDV
                }
                
              }
            }catch(e){
              this.$toast.error('Không load được danh sách loại đơn vị')
            }
        },
        async lay_danhsach_donvi_quanly(){
            try{
              let response=await DepartmentAreaAPI.lay_danhsach_donvi_quanly(this.axios,{})
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                this.ds_donvi_ql=response.data.data
                if(this.ds_donvi_ql.length>0){
                  this.donvi_ql_selected=this.ds_donvi_ql[0].DONVI_ID
                }
                
              }
            }catch(e){
              this.$toast.error('Không load được danh sách đơn vị quản lý')
            }
        },
        async lay_danhsach_donvi(loai_dv){
            try{
              let response=await DepartmentAreaAPI.lay_danhsach_donvi(this.axios,loai_dv)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
              }else{
                return []
              }
            }catch(e){
              return []
            }
        },
        async lay_danhsach_donvi_px(loai_dv, donvi_id){
            try{
              this.loading(true)
              let response=await DepartmentAreaAPI.lay_danhsach_donvi_px(this.axios,loai_dv, donvi_id)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                this.dataSources=[]

                response.data.data.forEach((item)=>{
                  this.dataSources.push({
                    id: this.getKey(item.phuong_id, item.pho_id, item.ap_id, item.khu_id, item.dacdiem_id),
                    phuong_id: item.phuong_id,
                    pho_id: item.pho_id,
                    ap_id: item.ap_id,
                    khu_id: item.khu_id,
                    ten_phuong: item.ten_phuong!=null?item.ten_phuong:'',
                    ten_pho: item.ten_pho!=null?item.ten_pho:'',
                    ten_ap: item.ten_ap!=null?item.ten_ap:'',
                    ten_khu: item.ten_khu!=null?item.ten_khu:'',
                    ten_quan: item.ten_quan!=null?item.ten_quan:'',
                    donvi_ql_id: item.donviql_id,
                    donvi_ql:item.donvi_ql!=null?item.donvi_ql:'',
                    dacdiem_id: item.dacdiem_id,
                    dacdiem: item.dacdiem
                  })
                })

              }else{
                this.dataSources=[]
              }
            }catch(e){
              this.loading(false)
              this.$toast.error('Không load được dữ liệu')
            }
        },
        getKey(phuong_id, pho_id, ap_id, khu_id, dacdiem_id){
            return phuong_id+'-'+pho_id+'-'+ap_id+'-'+khu_id+'-'+dacdiem_id
        },
        async loadDanhsachDonvi(){
            this.dataSourcesDV=[]
            this.dataSources=[]
            this.donvi_id_selected=null
            await this.hienthi_ds_donvi()

        },
        async hienthi_ds_donvi(){
          this.dataSourcesDV=await this.lay_danhsach_donvi(this.loai_donvi_selected)
        },
        loadDuLieuNap(){
          //this.dataSources=[]
           //this.lay_danhsach_donvi_px(this.loai_donvi_selected, this.donvi_id_selected)
          this.dataSources=[]
          this.lay_danhsach_donvi_px(this.loai_donvi_selected, this.donvi_id_selected)
        },
        async onChangeLoaiDonvi(){
            this.donvi_id_selected=null
            this.dataSourcesDV=[]
            this.dataSources=[]
            await this.hienthi_ds_donvi()
        },
        selectedItemsChangedDV(data){
          if(this.dataSourcesDV.length<=0||data.length<=0){
            this.donvi_id_selected=null
            return
          }
          this.selectedRowDV(data[0])
        },
        selectedRowDV(item){
            this.donvi_id_selected=item.donvi_id
            this.dataSources=[]
            this.lay_danhsach_donvi_px(this.loai_donvi_selected, this.donvi_id_selected)
        },
        getDonViIDSleceted(){
          return this.dataSourcesDV.length>0?this.donvi_id_selected:0
        },
        changeDonViQL(){
          // console.log('value', value)
          // console.log('model', this.donvi_ql_selected)
        },
        xoaNap(){
          let dataCheckeds=this.$refs.gridDuLieuNap.getSelectedRecords()

          if(dataCheckeds&&dataCheckeds.length>0){
              dataCheckeds.forEach((item)=>{
                const index = this.dataSources.indexOf(item)
                if(index>-1){
                  this.dataSources.splice(index,1)
                }
              })
              
          }
          
        },
        async capNhatDuLieu(){
          if(this.donvi_id_selected==null){
            this.$toast.error('Bạn chưa chọn đơn vị để gán địa danh')
            return
          }
          // if(this.dataSources.length==0){
          //   this.$toast.error('Không có danh dữ liệu nạp')
          //   return
          // }
          try{
              let data={
                danhsach: this.dataSources.map(x=>{
                  return {
                    phuong_id: x.phuong_id,
                    pho_id: x.pho_id,
                    ap_id: x.ap_id,
                    khu_id: x.khu_id,
                    donvi_ql_id: x.donvi_ql_id,
                    dacdiem_id: x.dacdiem_id!=null?x.dacdiem_id:-1
                  }
                }),
                vdonvi_id: this.donvi_id_selected,
                vdonvi_ql: this.donvi_ql_selected!=null?this.donvi_ql_selected:-1,
                vloaidv_id: this.loai_donvi_selected
              }
              this.loading(true)
              let response=await DepartmentAreaAPI.capnhat_donvi_diadanh(this.axios,data)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                this.$toast.success('Cập nhật dữ liệu thành công')
                await this.lay_danhsach_donvi_px(this.loai_donvi_selected, this.donvi_id_selected)
              }else{
                if(response&&response.data&&response.data.message){
                    this.$toast.error(response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi không thể cập nhật được dữ liệu')
                }
              }
            }catch(e){
              this.loading(false)
              if(e.data&&e.data.message){
                  this.$toast.error(e.data.message)
              }else if(e.response&&e.response.data&&e.response.data.message){
                  this.$toast.error(e.response.data.message)
              }else{
                  this.$toast.error('Đã xảy ra lỗi không thể cập nhật được dữ liệu')
              }
            }
        },
        nhanDuLieuNap(danhsach){
          var count=0;
          // Chỉnh lại theo yêu cầu, trong dữ liệu nạp, chọn check box trong danh sách rùi nhấn gán dvql
          // const dv_ql=this.ds_donvi_ql.find(x=>x.DONVI_ID==this.donvi_ql_selected)
          // let donvi_ql_id=dv_ql!=undefined?dv_ql.DONVI_ID:0
          // let donvi_ql=dv_ql!=undefined?dv_ql.TEN_DV:''

          danhsach.forEach((item)=>{
            // item.donvi_ql_id=donvi_ql_id
            // item.donvi_ql=donvi_ql
            const index =this.dataSources.findIndex(x=>x.pho_id==item.pho_id&&x.phuong_id==item.phuong_id&&x.ap_id==item.ap_id&&x.khu_id==item.khu_id&&x.dacdiem_id==item.dacdiem_id)
            if(index>-1){
               count++
            }else{
              this.dataSources.push(Object.assign(item,{
                id:this.getKey(item.phuong_id, item.pho_id, item.ap_id, item.khu_id, item.dacdiem_id)
              }))
            }
          })
          if(count>0){
            this.$toast.error('Có '+count+' bản ghi không thể nạp do sai dữ liệu hoặc dữ liệu đã có không thể thêm.')
          }
        },
        ganDVQL(){
          let dataCheckeds=this.$refs.gridDuLieuNap.getSelectedRecords()
          
          if(dataCheckeds.length==0){
            this.$toast.error('Hãy chọn dữ liệu nạp cần đơn vị quản lý!')
            return
          }
          const dv_ql=this.ds_donvi_ql.find(x=>x.DONVI_ID==this.donvi_ql_selected)
          if(dv_ql!=null&&dv_ql!=undefined){
            for(let i=0;i<dataCheckeds.length;i++){
              const index = this.dataSources.indexOf(dataCheckeds[i])
              if(index>-1){
                this.dataSources[index].donvi_ql_id = dv_ql.DONVI_ID
                this.dataSources[index].donvi_ql = dv_ql.TEN_DV
              }
            }
            this.dataSources=[].concat(this.dataSources)
            this.$toast.success('Đã gán đơn vị quản lý cho dữ liệu nạp!')
          }else{
            this.$toast.error('Hãy chọn đơn vị quản lý để gán vào dữ liệu nạp!')
          }
        },
        initDuLieu(){
          this.lay_danhsach_loai_donvi()
          this.lay_danhsach_donvi_quanly()
        }   
    },
    mounted(){
        this.initDuLieu()
    }
}
</script>
<style>
.btn-main11{
    background: #0176FF;
    color: #fff;
}
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
</style>
