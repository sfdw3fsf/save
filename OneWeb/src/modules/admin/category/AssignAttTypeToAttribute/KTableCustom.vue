<template>
  <div>
    <div class="table-content" :style="{height:tableHeight==0?'auto':(tableHeight+'px')}">
      <table class="table-result table-gachle" :class="{'tree-grid-2':treeGrid}" >
        <thead class="center">
          <tr>
            <th v-if="treeGrid" class="w20"></th>
            <th class="w20"></th>
            <th v-if="allowCheckBox" class="w20">Chọn</th>
            <!-- notifi change -->
            <th v-show="false" class="w20">
              <div class="check-action">
                <input type="checkbox" class="check" v-model="allCheckbox"/>
                <span class="name"></span>
              </div>
            </th>

            <th v-for="item in columns" :key="item.field">{{item.label}}</th>
          </tr>
          <tr v-if="allowFilter">
            <th v-if="treeGrid" class="w20"></th>
            <th class="w20"></th>
            <th v-if="allowCheckBox" class="w20">
              <div class="check-action">
                <input type="checkbox" disabled="" class="check" />
                <span class="name"></span>
              </div>
            </th>

            <th v-for="item in columns" :key="item.field">
              <div v-if="item.allowFilter" class="input-icon-right">
                <input type="text" class="form-control" @keyup="filterChange" v-model="filterValues[item.field]" />
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
            </th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="(item, index) in pagerData"  :key="index" @click="onSelectedRow(fromIndexPage+index+1, item)" :class="{highlight: rowSelected==fromIndexPage+index+1}">
            <td v-if="treeGrid" class="tree-first w20">
                <div class="tree-line"></div>
                <div class="tree-indent"></div>
            </td>
            <td class="w20 center" :class="{highlight1: (rowSelected==fromIndexPage+index+1)}">
                <span class="fa fa-play text-main" v-if="rowSelected==fromIndexPage+index+1"></span>
            </td>
            <td v-if="allowCheckBox" class="w20 center" :class="{highlight1: (rowSelected==fromIndexPage+index+1)}">
              <div class="check-action">
                <input type="checkbox" class="check" v-model="item.checked" @change="onRowCheckChange($event, item)"/>
                <span class="name"></span>
              </div>
            </td>

            <td v-for="(column, i) in columns" :key="i" :class="{highlight1: (rowSelected==fromIndexPage+index+1)}">{{item[column.field]}}</td>
        
          </tr>
          <tr v-if="numberRecords==0" class="empty">
            <td :colspan="totalColumns" class="text-center">
              Không có bản ghi nào để hiển thị
            </td>
          </tr>

        </tbody>
      </table>
    </div>
    <BssPagination :totalRows = "numberRecords" @change="onChangePage" :optionsNumberRowPerPage="optionsNumberRowPerPage" :nowrap="nowrap"/>
  </div>
</template>
<script>
import BssPagination from '@/components/kylq_components/BssPagination.vue'
export default {
    name:'KTableCustom',
    props:{
        dataSources:{
            type:Array,
            default: [],
            required: true
        },
        // column exam
        // {
        //     field: "TEN_TAIKHOAN",
        //     label: "Tên tài khoản",
        //     allowFilter: true
        // }
        columns:{
            type:Array,
            default: [],
            required: true
        },
        optionsNumberRowPerPage:{
            type:Array,
            default: function () {
                return [10, 20]
            }
        },
        allowFilter:{
            type:Boolean,
            default:false
        },
        allowCheckBox:{
            type:Boolean,
            default:false
        },
        tableHeight:{
          //exm: 120px
          type:Number,
          default:0
        },
        nowrap:{
          type:Boolean,
          default: false
        },
        treeGrid:{
            type:Boolean,
            default: false
        },
        allChecked:{
            type:Boolean,
            default:false
        }
    },
    data(){
        return{
            filterValues:{},
            dataSourceFilters:this.dataSources,
            pagerData:[],
            fromIndexPage:0,
            rowSelected:-1,
            allCheckbox:false,
            dataChecks:[]
        }
    },
    methods:{
        onChangePage(data){
         // { currentPage: this.currentPage, perPage: this.perPage }
            if(data.currentPage>0){
             
              let to=data.currentPage*data.perPage
              let from =to-data.perPage
              this.fromIndexPage=from
              this.pagerData=this.dataSourceFilters.slice(from, to)
            }else{
              this.pagerData=[]
            }
           
        },
        onSelectedRow(rowIndex, item){
          this.rowSelected=rowIndex
          this.$emit('onSelectedRow', item)
        },
        filterChange(){
          self=this;
          this.dataSourceFilters=this.dataSources.filter((item)=>{
            var result=true;
            self.columns.forEach((column)=>{
              if(column.allowFilter&&self.filterValues[column.field]&&item[column.field]){
                result=result&&(item[column.field].toString().toLowerCase().indexOf(self.filterValues[column.field].toString().toLowerCase())>-1)
              }
            })
            return result;
          })

        },
        // allChangeCheckbox(event){
        //   if (event.target.checked) {
        //     this.dataChecks=[]
        //     this.dataSources.forEach((item)=>{
        //       item.checked=true
        //       this.dataChecks.push(item)
        //     }) 
        //     this.$emit('dataCheckeds',this.dataChecks)
        //   }else{
        //     this.dataSources.forEach((item)=>{
        //       item.checked=false
        //     })
        //     this.dataChecks=[]
        //     this.$emit('dataCheckeds',this.dataChecks)
        //   }
        // },
        allChangeCheckbox(value){
          if (value) {
            this.dataChecks=[]
            this.dataSources.forEach((item)=>{
              item.checked=true
              this.dataChecks.push(item)
            }) 
            this.$emit('dataCheckeds',this.dataChecks)
          }else{
            this.dataSources.forEach((item)=>{
              item.checked=false
            })
            this.dataChecks=[]
            this.$emit('dataCheckeds',this.dataChecks)
          } 
        },
        onRowCheckChange(event, item){
           if (event.target.checked) {
             this.dataChecks.push(item)
           }else{
             const index = this.dataChecks.indexOf(item);
             if (index > -1) {
               this.dataChecks.splice(index, 1);
             }
           }
           this.allCheckbox=false
           this.$emit('dataCheckeds',this.dataChecks)
        }
    },
    watch: { 
      	dataSources: function(newVal, oldVal) { // watch it
           newVal.forEach((item)=>{
             item.checked=false
           })
           this.pagerData=[]

           this.dataSourceFilters=newVal 

           //reset filter
           this.filterValues={
             
           }
           this.dataChecks=[]
           this.rowSelected=-1
           this.allCheckbox=false
           this.allChecked=false
        },
        allChecked: function(newVal, oldVal){
            this.allCheckbox=newVal
            this.allChangeCheckbox(newVal)
        }
    },
    computed:{
      numberRecords(){
        return this.dataSourceFilters.length;
      },
      totalColumns(){
          var total=this.columns.length+1
          if(this.allowCheckBox){
              total+=1
          }
          if(this.treeGrid){
              total+=1
          }
          return total
      }

    },
    components:{
        BssPagination
    },
}
</script>
<style scoped>

</style>