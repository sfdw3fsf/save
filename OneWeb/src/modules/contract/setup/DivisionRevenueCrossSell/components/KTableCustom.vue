<template>
  <div>
    <div class="table-content" :style="{height:tableHeight==0?'auto':(tableHeight+'px')}">
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

            <th v-for="item in columns" :key="item.field" :class="{w40: item.field=='stt'}">{{item.label}}</th>
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
            <td v-if="allowCheckBox" class="w20 center" :class="{highlight1: (rowSelected==fromIndexPage+index+1)}">
              <div class="check-action">
                <input type="checkbox" class="check" v-model="item.checked" @change="onRowCheckChange($event, item,index)"/>
                <span class="name"></span>
              </div>
            </td>
            <td v-for="(column, i) in columns" :key="i" :class="{highlight1: (rowSelected==fromIndexPage+index+1), w40: column.field=='stt', 'text-right': typeof item[column.field] === 'number', 'center': column.isCheckColumn}">
                <div v-if="column.isCheckColumn" class="check-action">
                  <input type="checkbox" v-model="item[column.field]" :disabled="false" class="check" />
                  <span class="name"></span>
                </div>
                <div v-else>
                  <div v-if="column.allowEdit">
                      <div v-if="item.CheckEdit">
                          <select @change="Change_Dichvu($event,item)" v-model="item.dichvuvt_id">
                            <option v-for="option in listbox.dichvu_vt.list" :key="option.value" :value="option.id">
                              {{ option.text }}
                            </option>
                          </select>
                      </div>
                    <div v-else>{{column.format?column.format(item[column.field]):item[column.field]}}</div>
                  </div>
                  <!-- dơn vị -->
                  <div v-if="column.check_dovi">
                      <div v-if="item.CheckEdit_donvi">
                          <select @change="Change_Donvi($event,item)" style="width: 300px;" v-model="item.donvi_id">
                            <option v-for="option in listbox.donvi.list" :key="option.value" :value="item.donvi=option.id">
                              {{ option.text }}
                            </option>
                          </select>
                      </div>
                    <div v-else>{{column.format?column.format(item[column.field]):item[column.field]}}</div>
                  </div>
                  <!-- loại hình  -->
                  <div v-if="column.check_loaihinh">
                      <div v-if="item.CheckEdit_loaihinh">
                          <select @change="Change_Loaihinh($event,item)" style="width: 200px;" v-model="item.loaitb_id">
                            <option v-for="option in listbox.loaihinh.list" :key="option.value" :value="item.loaihinh=option.id">
                              {{ option.text }}
                            </option>
                          </select>
                      </div>
                    <div v-else>{{column.format?column.format(item[column.field]):item[column.field]}}</div>
                  </div>
                  <!-- loại phát triển -->
                  <div v-if="column.check_loaiPT">
                      <div v-if="item.CheckEdit_loai_PT">
                          <select @change="Change_Loai_pt($event,item)" style="width: 180px;" v-model="item.loai_pt">
                            <option v-for="option in listbox.loai_pt.list" :key="option.value" :value="option.id">
                              {{ option.text }}
                            </option>
                          </select>
                      </div>
                    <div v-else>{{column.format?column.format(item[column.field]):item[column.field]}}</div>
                  </div>
                  <!-- công đoạn -->
                  <div v-if="column.check_congdoan">
                      <div v-if="item.CheckEdit_congdoan">
                          <select @change="Change_congdoan($event,item)" style="width: 180px;" v-model="item.congdoan_id">
                            <option v-for="option in listbox.congdoan.list" :key="option.value" :value="option.id">
                              {{ option.text }}
                            </option>
                          </select>
                      </div>
                    <div v-else>{{column.format?column.format(item[column.field]):item[column.field]}}</div>
                  </div>
                  <!-- Tỷ lệ -->
                  <div v-if="column.check_tyle">
                      <div v-if="item.CheckEdit_tyle">
                        <input v-model="item.tyle" placeholder="" style="width: 120px;" @change="Change_Tyle($event,item)">
                      </div>
                    <div v-else>{{column.format?column.format(item[column.field]):item[column.field]}}</div>
                  </div>

                  <div v-if="column.allow_thaotac"> 
                    <div v-if="item.allow_thaotac">
                    <button class="btn" @click="Click_DeleteRow(item)" >
                      <span class="icon one-trash"></span>
                    </button>
                    <button class="btn" @click="Click_EditRow(item)" >
                      <span class="icon -ap  icon one-edit"></span>
                    </button>
                    <button class="btn" @click="Click_CopyRow(item)" >
                      <span class="icon one-copy"></span>
                    </button>
                  </div>
                  </div>
                  <!-- <div v-else>
                    <div v-else>{{column.format?column.formatDate(item[column.field]):item[column.field]}}</div>  
                  </div> -->
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
    <BssPagination  :index_currentPage="index_currentPage" :totalRows = "numberRecords" @change="onChangePage" :optionsNumberRowPerPage="optionsNumberRowPerPage" :nowrap="nowrap"/>
  </div>
</template>
<script>
// callback
// onSelectedRow return item
// onRowSelectedIndex return index row begin 1
// doubleClickRow return item
// dataCheckeds return data is checked
import KDatePicker from '@/components/kylq_components/KDatePicker.vue';
import BssPagination from './BssPagination.vue';
export default {
    name:'k-table-custom',
    components: {
      KDatePicker,
    },
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
        //     allowFilter: true,
        //     isCheckColumn:false
        // }
        listbox:{
            type:Object,
            default: ()=>[],
            required: true
        },
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
        index_currentPage:{
          type:Number,
          default: ()=>1,
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

        labelCheckColumn:{
          type:Boolean,
          default:false
        }

    },
    data(){
        return{
          dtpNgayYeuCau:'',
            filterValues:{},
            list_select:[],
            dataSourceFilters:this.dataSources,
            pagerData:[],
            fromIndexPage:0,
            rowSelected:this.rowSelectedIndex,
            allCheckbox:false,
            dataChecks:[],
            check_clickpage:false
        }
    },
    methods:{

        onChangePage(data){
         // { currentPage: this.currentPage, perPage: this.perPage }
            if(data.currentPage>0){
              if(this.check_clickpage){
                let to=data.currentPage*data.perPage
                let from =to-data.perPage
                this.fromIndexPage=from
                this.pagerData=this.dataSourceFilters.slice(from, to)
                this.$emit('onChangePage', data.currentPage)
              }
              else{
                let to=this.index_currentPage*data.perPage
                let from =to-data.perPage
                this.fromIndexPage=from
                this.pagerData=this.dataSourceFilters.slice(from, to)
                this.check_clickpage=true
              }
            }else{
              this.pagerData=[]
            }
           
        },
        Click_DeleteRow(item){
          this.$emit('Click_DeleteRow', item)
        },
        Click_EditRow(item){
          this.$emit('Click_EditRow', item)
        },
        Click_CopyRow(item){
          this.$emit('Click_CopyRow', item)
        },
        Change_Dichvu(event,item){
          this.$emit('Change_Dichvu', event.target.value,item)
        },
        Change_Loaihinh(event,item){
          this.$emit('Change_Loaihinh', event.target.value,item)
        },
        Change_Donvi(event,item){
          this.$emit('Change_Donvi', event.target.value,item)
        },
        Change_congdoan(event,item){
          this.$emit('Change_congdoan', event.target.value,item)
        },
        Change_Loai_pt(event,item){
          this.$emit('Change_Loai_pt', event.target.value,item)
        },
        Change_Tyle(event,item){
          console.log("tỷ lệ là ", event.target.value )
          this.$emit('Change_Tyle', event.target.value,item)
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
                result=result&&(item[column.field].toString().indexOf(self.filterValues[column.field].toString().trim())>-1)
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
              this.dataChecks.push(item)
            }) 
            this.$emit('dataCheckeds',this.dataChecks) // Gửi mảng các hàng được chọn đến component cha
          }else{
            this.dataSources.forEach((item)=>{
              item.checked=false
            })
            this.dataChecks=[]
            this.$emit('dataCheckeds',this.dataChecks)
          }
        },
        onRowCheckChange(event, item,index){
           if (event.target.checked) {
             this.dataChecks.push(item)
             if (!this.list_select.includes(index))
             {this.list_select.push(index)}

           }else{
             const stt = this.dataChecks.indexOf(item);
             if (stt > -1) {
               this.dataChecks.splice(stt, 1);
               this.list_select=this.list_select.filter(item => item !== index)
             }
           }
           this.allCheckbox=false
           this.$emit('dataCheckeds',this.dataChecks,this.list_select)
        },
        onRowValueChange(event, column, item){
          console.log(event)
          console.log(column)
          console.log(item)
        },
    //     formatDate(dateString) {
    //   // Chuyển đổi ngày từ định dạng dd/mm/yyyy sang yyyy-mm-dd để hiển thị đúng trong input type="date"
    //   const parts = dateString.split('/');
    //   if (parts.length === 3) {
    //     const [day, month, year] = parts;
    //     return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`;
    //   }
    //   return dateString;
    // },
    // formatDateReverse(dateString) {
    //   // Chuyển đổi ngày từ định dạng yyyy-mm-dd sang dd/mm/yyyy để gán lại giá trị vào item[column.field]
    //   const parts = dateString.split('-');
    //   if (parts.length === 3) {
    //     const [year, month, day] = parts;
    //     return `${day.padStart(2, '0')}/${month.padStart(2, '0')}/${year}`;
    //   }
    //   return dateString;
    // },
    },
    watch: { 
          dataSources: function(newVal, oldVal) { // watch it
           newVal.forEach((item)=>{
             item.checked=false
           })
           this.pagerData=[]

           this.dataSourceFilters=newVal 
           this.check_clickpage=false
           console.log("Tú ẻwertwetwt",newVal )
           
           //reset filter
           this.filterValues={
             
           }
           this.dataChecks=[]
           this.rowSelected=this.rowSelectedIndex
           this.allCheckbox=false
           this.list_select=[]
        },
        rowSelectedIndex(val){
          this.rowSelected=val
        },


    },
    computed:{
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
    components:{
        BssPagination
    },
}
</script>
<style scoped>

</style>