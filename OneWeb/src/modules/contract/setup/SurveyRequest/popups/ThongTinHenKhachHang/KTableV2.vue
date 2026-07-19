<template>
  <div>
    <div class="table-content" :style="{height:tableHeight}">
      <table class="table-result table-gachle tree-grid-2" >
        <thead class="center">
          <tr>
            <th class="w20"></th>
            <th class="w20"></th>
            <th v-if="allowCheckBox" class="w20">
              <div class="check-action">
                <input type="checkbox" class="check" v-model="allCheckbox" @change="allChangeCheckbox($event)"/>
                <span class="name"></span>
              </div>
            </th>

            <th v-for="item in columns" :key="item.field">{{item.label}}</th>
          </tr>
          <tr v-if="allowFilter">
            <th class="w20"></th>
            <th class="w20"></th>

            <th v-if="allowCheckBox" class="w20">
              <div class="check-action">
                <input type="checkbox" :disabled="true" class="check" />
                <span class="name"></span>
              </div>
            </th>

            <th v-for="item in columns" :key="item.field">
              <div v-if="item.allowFilter&&(item.checkBox==undefined||!item.checkBox)&&item.field!='button'" class="input-icon-right">
                <!-- @change input after enter search -->
                <input type="text" class="form-control" @keyup="filterChange" v-model="filterValues[item.field]" />
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
              <div v-if="(item.checkBox!=undefined&&item.checkBox)&&item.field!='button'" class="check-action">
                <input type="checkbox" :disabled="true" class="check">
                <span class="name"></span>
              </div>
            </th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="(item, index) in dataSourceFilters"  :key="index" @click="onSelectedRow(index+1, item)" @dblclick="doubleClickRow(index+1, item)" :class="{highlight: (rowSelected==index+1)&&item.type==2, highlight4: item.type==1, 'tree-open':item.type==1&&item.isExpanded}">
             <!-- Type 1 -->
             <td class="w20 center" v-if="item.type==1" @click="onExpanded(item)">
                <span class="tree-arrow"></span>
             </td>
             <td class="w20 center" v-if="item.type==1"></td>
             <td class="w20 center" v-if="allowCheckBox&&item.type==1">
                <div class="check-action">
                    <input type="checkbox" v-model="item.checked" @change="onRowCheckChange($event, item)" class="check">
                    <span class="name"></span>
                </div>
            </td>
            <td v-if="item.type==1" :colspan="columns.length" class="fw6">Số máy/Acc: {{item.name}}</td>
             <!-- Type 2 -->
             <td class="tree-first w20" v-if="item.type==2&&item.isShow">
                <div class="tree-line"></div>
                <div class="tree-indent"></div>
            </td>
            <td class="w20 center" :class="{highlight1: (rowSelected==index+1)}" v-if="item.type==2&&item.isShow">
                <span class="fa fa-play text-main" v-if="rowSelected==index+1"></span>
            </td>
            <td v-if="allowCheckBox&&item.type==2&&item.isShow" class="w20 center" :class="{highlight1: (rowSelected==index+1)}">
              <div class="check-action">
                <input type="checkbox" class="check" v-model="item.checked" @change="onRowCheckChange($event, item)"/>
                <span class="name"></span>
              </div>
            </td>
            <col-custom v-for="(column, i) in columns" :key="i" :visible="item.type==2&&item.isShow" :class="{'w20 center':(column.checkBox!=undefined&&column.checkBox)}" :highlight1="rowSelected==index+1">
                <div v-if="(column.checkBox==undefined||!column.checkBox)&&column.field!='button'">
                    {{item[column.field]}}
                </div>
                <div v-if="(column.checkBox!=undefined&&column.checkBox)&&column.field!='button'" class="check-action">
                    <input type="checkbox" disabled v-model="item[column.field]" class="check">
                    <span class="name"></span>
                </div>
                <!-- Hard send sms -->
                <div v-if="column.field=='button'" class="center">
                    <button @click="sendSMS(item)" class="btn btn-main lh14">SMS</button>
                </div>
            </col-custom>
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
//Tree table group expanded
// column exam
// {
//     field: "TEN_TAIKHOAN",
//     label: "Tên tài khoản",
//     allowFilter: true,
//     cssRows:{color:'red', fontWeight: 'bold'}
//     cssHeaders:{color:'red', fontWeight: 'bold'},
//     checkBox:false
// }
import ColCustom from './ColCustom.vue'
export default {
    name:'tree-table-custom',
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
        //begin 1-> size row
        rowSelectedIndex:{
          type:Number,
          default:-1
        },
        //replace index number :key table
        fieldValue:{
            type:String,
            default:'ID',
            required: true
        },
        fieldGroup:{
            type:String,
            default:'ID',
            required: true
        },

    },
    components:{
        ColCustom
    },
    data(){
        return{
            filterValues:{},
            dataSourceFilters:this.dataSources,
            rowSelected:this.rowSelectedIndex,
            allCheckbox:false,
            dataChecks:[]
        }
    },
    methods:{
        onSelectedRow(rowIndex, item){
          if(item.type==1){
            return
          }
          this.rowSelected=rowIndex
          this.$emit('onSelectedRow', item)
          this.$emit('onRowSelectedIndex', this.rowSelected)
        },
        doubleClickRow(rowIndex, item){
          this.$emit('doubleClickRow', item)
        },
        onExpanded(item){
            if(item.type==2){
                return
            }
            const index=this.dataSources.findIndex(x=>(x["id"] !== undefined&&x.id===item.id))
            if(index>-1){
                this.dataSources[index].isExpanded=!this.dataSources[index].isExpanded
                let isShow=this.dataSources[index].isExpanded
                this.dataSources.forEach((x)=>{
                    if(x.type==2&&x[this.fieldGroup]==item.id){
                        x.isShow=isShow
                    }
                })
            }
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
        allChangeCheckbox(event){
          if (event.target.checked) {
            this.dataChecks=[]
            this.dataSources.forEach((item)=>{
              item.checked=true
              if(item.type==2){
                  this.dataChecks.push(item)
              }
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
        sendSMS(item){
          this.$emit('sendSMS',item)
        },
        onRowCheckChange(event, item){
            if (event.target.checked) {
                if(item.type==2){
                    this.dataChecks.push(item)
                }else{
                    this.dataChecks=this.dataChecks.filter(x=>x[this.fieldGroup]!=item.id)
                    this.dataSources.forEach(x=>{
                        
                        if(x.type==2&&x[this.fieldGroup]==item.id){
                            x.checked=true
                            this.dataChecks.push(x)
                        }
                    })
                }
            }else{
                if(item.type==2){
                    const index = this.dataChecks.indexOf(item);
                    if (index > -1) {
                        //clear check cha
                        let tmp=this.dataChecks[index]
                        const index1=this.dataSources.findIndex(x=>(x['id']!==undefined&&x['id']===tmp[this.fieldGroup]))
                        if(index1>-1){
                            this.dataSources[index1].checked=false
                        }
                        //
                        this.dataChecks.splice(index, 1);
                    }
                }else{
                    this.dataSources.forEach((x)=>{
                        if(x.type==2&&x[this.fieldGroup]==item.id){
                            const index = this.dataChecks.findIndex(z=>z[this.fieldValue]==x[this.fieldValue])
                            if (index > -1) {
                                x.checked=false
                                this.dataChecks.splice(index, 1);
                            }
                        }
                    })
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
           
           this.dataSourceFilters=newVal

           //reset filter
           this.filterValues={
             
           }
           this.dataChecks=[]
           this.rowSelected=this.rowSelectedIndex
           this.allCheckbox=false
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
          var total=this.columns.length+2
          if(this.allowCheckBox)
            return total+1
          return total
      }
    }
}
</script>
<style scoped>
.btn-main {
    background: #0176FF!important;
    color: #fff!important;
}

</style>