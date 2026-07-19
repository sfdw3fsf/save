<template>
  <div>
    <div class="table-content" :style="{height:tableHeight}">
      <table class="table-result table-gachle" >
        <thead class="center">
          <tr>
            <th class="w20"></th>
            <th class="w20" v-if="allowCheckBox&&labelCheckColumn">Chọn</th>

            <th v-if="allowCheckBox&&!labelCheckColumn" class="w20">
              <div class="check-action">
                <input type="checkbox" class="check" v-model="allCheckbox" @change="allChangeCheckbox($event)"/>
                <span class="name"></span>
              </div>
            </th>

            <th v-for="item in columns" :key="item.field">{{item.label}}</th>
          </tr>
          <tr v-if="allowFilter">
            <th class="w20"></th>

            <th v-if="allowCheckBox" class="w20">
              <div class="check-action">
                <input type="checkbox" v-if="!labelCheckColumn" :disabled="true" class="check" />
                <input type="checkbox" v-else v-model="allCheckbox" @change="allChangeCheckbox($event)" class="check" />
                <span class="name"></span>
              </div>
            </th>

            <th v-for="item in columns" :key="item.field" >
              <div v-if="item.allowFilter&&(item.checkBox==undefined||!item.checkBox)" class="input-icon-right">
                <!-- @change input after enter search -->
                <input type="text" class="form-control" v-model="filterValues[item.field]" />
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
              <div v-if="(item.checkBox!=undefined&&item.checkBox)" class="check-action">
                <input type="checkbox" :disabled="true" class="check">
                <span class="name"></span>
              </div>
            </th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="item in dataSourceFilters"  :key="item[id]" @click="onSelectedRow(item)" @dblclick="doubleClickRow(item)" :class="{highlight: rowSelected==item[id]}">
            <td class="w20 center" :class="{highlight1: rowSelected==item[id]}">
                <span class="fa fa-play text-main" v-if="rowSelected==item[id]"></span>
            </td>
            <td v-if="allowCheckBox" class="w20 center" :class="{highlight1: rowSelected==item[id]}">
              <div class="check-action">
                <input type="checkbox" class="check" v-model="item.checked" @change="onRowCheckChange($event, item)"/>
                <span class="name"></span>
              </div>
            </td>
            <!-- selected: (rowSelected==fromIndexPage+index+1),  -->
            <td v-for="(column, i) in columns" :key="i" :style="column.cssRows" :class="{highlight1: rowSelected==item[id],'w20 center':(column.checkBox!=undefined&&column.checkBox), 'text-right': (typeof item[column.field] === 'number')&&(column.checkBox==undefined||!column.checkBox)}">
              <div v-if="(column.checkBox==undefined||!column.checkBox)">
                 {{item[column.field]}}
              </div>
              <div v-if="(column.checkBox!=undefined&&column.checkBox)" class="check-action">
                  <input type="checkbox" v-model="item[column.field]" @change="changeCheckGroup(item[id], column.field)" class="check">
                  <span class="name"></span>
              </div>
            </td>

          </tr>
          <tr v-if="numberRecords==0" class="empty">
            <td :colspan="totalColumns" class="text-center">
              Không có bản ghi nào để hiển thị
            </td>
          </tr>

        </tbody>
      </table>
    </div>
  </div>
</template>
<script>
//không có phân trang
//Truyền fieldValue thay key
// column exam
// {
//     field: "TEN_TAIKHOAN",
//     label: "Tên tài khoản",
//     allowFilter: true,
//     cssRows:{color:'red', fontWeight: 'bold'}
//     cssHeaders:{color:'red', fontWeight: 'bold'},
//     checkBox:false
// }
//check box colunm truyền 1, 0
export default {
    name:'KTableV1',
    props:{
        dataSources:{
            type:Array,
            default: [],
            required: true
        },
        columns:{
            type:Array,
            default: [],
            required: true
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
          type:String,
          default:'auto'
        },
        //selected id row
        rowSelectedId:{
          type:[Number, String],
          default:-1
        },
        labelCheckColumn:{
          type:Boolean,
          default:false
        },
        //replace index number :key table
        id:{
          type:String,
          default:'id',
          required: true
        },

    },
    computed:{
      dataSourceFilters(){
            return this.dataSources.filter(this.filterData)
      },
      numberRecords(){
        return this.dataSourceFilters.length;
      },
      totalColumns(){
          var total=this.columns.length+1
          if(this.allowCheckBox)
            return total+1
          return total
      }
    },
    data(){
        return{
            filterValues:{},
            rowSelected:this.rowSelectedId,
            allCheckbox:false,
            dataChecks:[]
        }
    },
    methods:{
        onSelectedRow(item){
          this.rowSelected=item[this.id]
          this.$emit('onSelectedRow', item)
        },
        doubleClickRow(item){
          this.$emit('doubleClickRow', item)
        },
        // Filter
        filterData:function (item){
            var result=true;
            this.columns.forEach((column)=>{
              if(column.allowFilter&&this.filterValues[column.field]){
                result=result&&((item[column.field]!=null?item[column.field].toString().trim().toLowerCase():"").indexOf(this.filterValues[column.field].toString().trim().toLowerCase())>-1)
              }
            })
            return result;
        },
        allChangeCheckbox(event){
          if (event.target.checked) {
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
        changeCheckGroup(key, column){
          const index=this.dataSources.findIndex(x=>x[this.id]==key)
          if(index>-1){
            this.columns.forEach((item)=>{
              if(item.checkBox&&item.field!=column){
                this.dataSources[index][item.field]=false
              }
            })
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
           //reset filter
           this.filterValues={}
           this.dataChecks=[]
           this.rowSelected=this.rowSelectedId
           this.allCheckbox=false
        },
        rowSelectedId(val){
          this.rowSelected=val
        },
    }
}
</script>
