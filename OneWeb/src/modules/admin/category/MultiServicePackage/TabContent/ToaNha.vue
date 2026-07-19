<template>
  <div class="box-move-select-table fullh">
    <div class="box-col">
      <KDataGrid
        :columns="columnsGan"
        :dataSource="dataSourcesGan"
        :showColumnCheckbox="true"
        :enable-paging-server="false"
        :allowPaging="true"
        :showFilter="true"
        ref="gridDaGan"
        :enabledSelectFirstRow="false"
        panelDataHeight="250px"/>
    </div>
    <div class="actions">
      <button class="btn" @click="boGan">
        <span class="fa fa-angle-right"></span>
      </button>

      <button class="btn" @click="gan">
        <span class="fa fa-angle-left"></span>
      </button>
    </div>
    <div class="box-col">
      <KDataGrid
        :columns="columnsChuaGan"
        :dataSource="dataSourcesChuaGan"
        :showColumnCheckbox="true"
        :enable-paging-server="false"
        :allowPaging="true"
        :showFilter="true"
        ref="gridChuaGan"
        :enabledSelectFirstRow="false"
        panelDataHeight="250px"/>
    </div>
  </div>
</template>
<script>
import KDataGrid from '@/components/kylq_components/KDataGrid'
import MultiServicePackageAPI from '../MultiServicePackageAPI.js'
export default {
    name:'ToaNha',
    components:{
        KDataGrid
    },
    props: {
        goi_id: {
            type: Number,
            default: 0,
        },
    },
    data(){
        return{
            columnsGan:[
                {
                    fieldName: "ten_toanha",
                    headerText: "Tòa nhà gán",
                    allowFiltering: true,
                    allowSorting:true
                },
            ],
            dataSourcesGan:[],
            columnsChuaGan:[
                {
                    fieldName: "ten_toanha",
                    headerText: "Tòa nhà chưa gán",
                    allowFiltering: true,
                    allowSorting:true
                },
            ],
            dataSourcesChuaGan:[],
            goitichhop_id: this.goi_id
        }
    },
    methods:{
        LowerCasePropertyList(obj) {
            return obj.map(function (item) {
                for (var key in item) {
                    var upper = key.toLowerCase();
                    // check if it already wasn't uppercase
                    if (upper !== key) {
                        item[upper] = item[key];
                        delete item[key];
                    }
                }
                return item;
            });
        },
        boGan(){
            let dataCheckedsLHGan=this.$refs.gridDaGan.getSelectedRecords()
            if(dataCheckedsLHGan.length==0){
                return
            }
            for(let i=0;i<dataCheckedsLHGan.length;i++){
                this.dataSourcesChuaGan.push(dataCheckedsLHGan[i])
                const index=this.dataSourcesGan.findIndex(x=>x.toanha_id==dataCheckedsLHGan[i].toanha_id)
                if(index>-1){
                    this.dataSourcesGan.splice(index,1)
                }
            }
            this.dataSourcesGan=[].concat(this.dataSourcesGan)
            this.dataSourcesChuaGan=[].concat(this.dataSourcesChuaGan)
        },
        gan(){
            let dataCheckedsLHChuaGan=this.$refs.gridChuaGan.getSelectedRecords()
            if(dataCheckedsLHChuaGan.length==0){
                return
            }
            for(let i=0;i<dataCheckedsLHChuaGan.length;i++){
                this.dataSourcesGan.push(dataCheckedsLHChuaGan[i])
                const index=this.dataSourcesChuaGan.findIndex(x=>x.toanha_id==dataCheckedsLHChuaGan[i].toanha_id)
                if(index>-1){
                    this.dataSourcesChuaGan.splice(index,1)
                }
            }
            this.dataSourcesGan=[].concat(this.dataSourcesGan)
            this.dataSourcesChuaGan=[].concat(this.dataSourcesChuaGan)
        },
        async initDulieu(){
            this.dataSourcesGan=await this.sp_lay_ds_toanha_theo_goi(this.goitichhop_id, 1)
            this.dataSourcesChuaGan=await this.sp_lay_ds_toanha_theo_goi(this.goitichhop_id, 0)
        },
        getDsToaNhaGan(){
            return this.dataSourcesGan
        },
        async sp_lay_ds_toanha_theo_goi(goi_id, kieu){
            try{
                this.loading(true)
                let response = await MultiServicePackageAPI.sp_lay_ds_toanha_theo_goi(this.axios, goi_id, kieu)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data ? this.LowerCasePropertyList(response.data.data) : []
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
    },
    watch: {
        goi_id(newval, oldval) {
            if (newval) {
                this.goitichhop_id = newval;
                this.initDulieu();
            }
        },
    },
}
</script>