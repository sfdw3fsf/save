<template>
  <div>
    <div class="table-content" :style="{height:tableHeight==0?'auto':(tableHeight+'px')}">
      <table class="table-result table-gachle" >
        <thead class="center">
          <tr v-if="!hideColumns">
            <th class="w20"></th>
            <th v-for="item in columns" :key="item.field" :class="{w40: item.field.toLowerCase()=='stt'}">{{item.label}}</th>
          </tr>
          <tr v-if="allowFilter">
            <th class="w20"></th>
            <th v-for="item in columns" :key="item.field">
              <div v-if="item.allowFilter" class="input-icon-right">
                <!-- @change input after enter search -->
                <input type="text" class="form-control" @keyup="filterChange" v-model="filterValues[item.field]" />
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
            </th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="(item, index) in pagerData"  :key="index" @click="onSelectedRow(fromIndexPage+index+1, item)" @dblclick="doubleClickRow(fromIndexPage+index+1, item)" :class="{highlight: rowSelected==fromIndexPage+index+1}">
            <td class="w20 center" :class="{highlight1: (rowSelected==fromIndexPage+index+1)}">
                <span class="fa fa-play text-main" v-if="rowSelected==fromIndexPage+index+1"></span>
            </td>
            <!-- selected: (rowSelected==fromIndexPage+index+1),  -->
            <td v-for="(column, i) in columns" :key="i" :class="{highlight1: (rowSelected==fromIndexPage+index+1), w40: column.field.toLowerCase()=='stt', 'text-right': typeof item[column.field] === 'number'}">{{item[column.field]}}</td>
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
// callback
// onSelectedRow return item
// onRowSelectedIndex return index row begin 1
// doubleClickRow return item
import BssPagination from '@/components/kylq_components/BssPagination.vue'
export default {
    name:'k-table-custom',
    props:{
        dataSources:{
            type:Array,
            default: ()=>[],
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
            default: ()=>[],
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
        tableHeight:{
          //exm: 120px
          type:Number,
          default:0
        },
        nowrap:{
          type:Boolean,
          default: false
        },
        //begin 1-> size row
        rowSelectedIndex:{
          type:Number,
          default:-1
        },
        hideColumns:{
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
            rowSelected:this.rowSelectedIndex
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
          this.$emit('onRowSelectedIndex', this.rowSelected)
        },
        doubleClickRow(rowIndex, item){
          this.$emit('doubleClickRow', item)
        },
        filterChange(){
          self=this;
          this.dataSourceFilters=this.dataSources.filter((item)=>{
            var result=true;
            self.columns.forEach((column)=>{
              if(column.allowFilter&&self.filterValues[column.field]&&item[column.field]){
                result=result&&(item[column.field].toString().toLowerCase().indexOf(self.filterValues[column.field].toString().trim().toLowerCase())>-1)
              }
            })
            return result;
          })
        },
    },
    watch: { 
      	dataSources: function(newVal, oldVal) { // watch it
           this.pagerData=[]

           this.dataSourceFilters=newVal 

           //reset filter
           this.filterValues={
             
           }
           this.rowSelected=this.rowSelectedIndex
        },
        rowSelectedIndex(val){
          this.rowSelected=val
        }
    },
    computed:{
      numberRecords(){
        return this.dataSourceFilters.length;
      },
      totalColumns(){
          var total=this.columns.length+1
          return total
      }
    },
    components:{
        BssPagination
    },
}
</script>