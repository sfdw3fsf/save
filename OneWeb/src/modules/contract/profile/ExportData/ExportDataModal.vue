<template>
  <b-modal
    ref="popupExportData"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Xuất dữ liệu
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <ActionTop :actions="actions"  @onActionClick="onActionClick"/>

      <div class="popup-body">
        <div class="box-form">
          
          <div class="row">
            <div class="col-sm-4 col-12">
                <div class="legend-title">Cấu trúc dữ liệu bảng</div>
                <DataGrid 
                    ref="gridDsCot"
                    :columns="columnsDsCot" 
                    :dataSource="dataSourcesDsCot"
                    :showColumnCheckbox="true"
                    :enable-paging-server="false"
                    :allowPaging="false"
                    :showFilter="true"
                    :enabledSelectFirstRow="false"
                    :defaultColumnCheckboxChecked="true"
                    panelDataHeight="450px"
                    :commands="[{name: 'xoa', cssClass: 'btn btn-danger', width: 20, text: 'X',title:'Xóa'},]" 
                    :commandColumn="{headerText: '', width: 80, cellCssClass: 'css1', firstColumn: false, show: true}"
                    @commandClicked="commandClicked"
                />
            </div>
            <div class="col-sm-8 col-12">
                <div class="legend-title">
                    <div class="pull-left">Xem trước dữ liệu</div>
                    <div class="pull-right">
                        <div class="check-action normal">
                            <input type="checkbox" v-model="checkXuatKhongDau" class="check">
                            <span class="name">Xuất dữ liệu tiếng Việt không dấu</span>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <DataGrid 
                    ref="gridDuLieu"
                    :columns="columns"
                    :enabledSelectFirstRow="true"
                    :dataSource="dataSourcesPreview"
                    :showColumnCheckbox="false"
                    :enable-paging-server="false"
                    panelDataHeight="300px"
                    :allowPaging="true"
                    :showFilter="true"
                />
            </div>
          </div>
        </div>
      </div>
      <SaveFileModal ref="saveFileModal" :file_name="fileName" @download="download"/>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import XLSX from 'xlsx'
import SaveFileModal from './SaveFileModal.vue'
//[UR2.17.001] - Xuất dữ liệu
export default {
    name:'ExportDataModal',
    props:{
        data:{
            type:Array,
            default:()=>[]
        },
        file_name:{
            type:String,
            default:''
        }
    },
    components:{
        ActionTop,
        SaveFileModal
    },
    data(){
        return{
            actions:[
                {
                    id:'xuatfile',
                    name:'Xuất file',
                    active: true,
                    icon_class:'one-save'
                },
                {
                    id:'xemdulieu',
                    name:'Xem dữ liệu',
                    active: true,
                    icon_class:'one-list-search'
                }
            ],
            columnsDsCot:[
                {
                    fieldName:'tencot',
                    headerText:'Tên cột',
                    allowFiltering:true
                }
            ],
            dataSourcesDsCot:[],
            columns:[],
            dataSourcesPreview:[],
            dataSources:[],
            sheetAName:'Sheet1',
            fileName: '',
            checkXuatKhongDau:false
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='xuatfile'){
                if(this.data.length==0){
                    this.$toast.error('Không có dữ liệu để xuất ra!')
                    return
                }

                this.$refs.saveFileModal.showModal()

            }else if(action.id=='xemdulieu'){
                this.xemdulieu()
            }
        },
        
        showModal() {
            this.$refs["popupExportData"].show()
        },
        hideModal() {
            this.$refs["popupExportData"].hide()
        },
        handleShowModal(){
            this.dataSourcesDsCot=[]
            this.columns=[]
            this.dataSourcesPreview=[]
            //data clone
            this.dataSources=[]
            this.fileName=''
            this.checkXuatKhongDau=false
            setTimeout(()=>{
                this.initDulieu()
            }, 500)
        },
        initDulieu(){
            this.fileName=this.file_name
            if(this.data.length<=0){
                return
            }
            //clone data -->dataSources
            this.data.forEach((x)=>{
                this.dataSources.push(Object.assign({},x))
            })

            //upper case key data source
            for(var i = 0; i < this.dataSources.length; i++){ 
                for (var key in this.dataSources[i]) {
                    if(key.toUpperCase() !== key){
                        this.dataSources[i][key.toUpperCase()] = this.dataSources[i][key];
                        delete this.dataSources[i][key];
                    }
                }
            }

            //Create Key
            this.dataSourcesDsCot=Object.keys(this.dataSources[0]).map(x=>{
                return {
                    id: x,
                    tencot: x
                }
            }) 

        },
        download(value){
            //tạo ds
            let danhsach=[]
            let array=this.$refs.gridDsCot.getSelectedRecords().map(x=>x.tencot)
            
            //let array=this.columns.map(x=>x.fieldName)
            this.dataSources.forEach(item=>{
                const object={}
                for (const name of array) {
                    object[name] = this.checkXuatKhongDau?item[name]!=null?this.removeVietnameseTones(item[name].toString()):item[name]:item[name]
                }
                danhsach.push(object)
            })
            //xuất file
            if(value.extension=='.xlsx'){
                let data = XLSX.utils.json_to_sheet(danhsach);
                let wb = XLSX.utils.book_new(); // make Workbook of Excel
                XLSX.utils.book_append_sheet(wb, data, this.sheetAName); // sheetAName is name of Worksheet
                // export Excel file
                let fileFullName=value.file_name+value.extension
                XLSX.writeFile(wb, fileFullName);
            }else if(value.extension=='.txt'){
                var data=''
                danhsach.forEach((item)=>{
                    let temp=[]
                    this.columns.forEach((column)=>{
                        temp.push(this.checkXuatKhongDau?item[column.fieldName]!=null?this.removeVietnameseTones(item[column.fieldName].toString()):item[column.fieldName]:item[column.fieldName])
                    })
                    let result=temp.join(';')+'\n'
                    data+=result
                })
                console.log('dataa',data)
                let fileFullName=value.file_name+value.extension

                const blob = new Blob([data], { type: 'text/plain;charset=utf-8' })
                const link = document.createElement('a')
                link.href = URL.createObjectURL(blob)
                link.download = fileFullName
                link.click()
                URL.revokeObjectURL(link.href)

            }else if(value.extension=='.dbf'){
                this.$toast.error('Tính năng tạm thời không sử dụng trên nền tảng web')
            }
        },
        commandClicked(cmdName, rowData){
            console.log('commandClicked', cmdName)
            console.log('commandClicked', rowData)
            let dataDsCot=this.dataSourcesDsCot
            const index =dataDsCot.findIndex(x=>x.id==rowData.id)
            if(index>-1){
                dataDsCot.splice(index,1)
            }
            this.dataSourcesDsCot=[].concat(dataDsCot)
        },
        xemdulieu(){
            let dataCheckDsCot=this.$refs.gridDsCot.getSelectedRecords()
            if(dataCheckDsCot.length==0){
                this.$toast.error('Chọn cột để xem')
                return
            }
            if(this.data.length==0){
                this.$toast.error('Chưa có dữ liệu xem báo cáo')
                return
            }
            //
            this.columns=[]
            
            dataCheckDsCot.forEach((item)=>{
                this.columns.push({
                    fieldName: item.tencot,
                    headerText: item.tencot,
                    allowFiltering: true,
                    allowSorting: false,
                    width: 120
                })
            })
            //
            this.dataSourcesPreview=this.dataSources

        },
        removeVietnameseTones(str) {
            str = str.replaceAll(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, "a");
            str = str.replaceAll(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, "e");
            str = str.replaceAll(/ì|í|ị|ỉ|ĩ/g, "i");
            str = str.replaceAll(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, "o");
            str = str.replaceAll(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, "u");
            str = str.replaceAll(/ỳ|ý|ỵ|ỷ|ỹ/g, "y");
            str = str.replaceAll(/đ/g, "d");
            str = str.replaceAll(/À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ/g, "A");
            str = str.replaceAll(/È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ/g, "E");
            str = str.replaceAll(/Ì|Í|Ị|Ỉ|Ĩ/g, "I");
            str = str.replaceAll(/Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ/g, "O");
            str = str.replaceAll(/Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ/g, "U");
            str = str.replaceAll(/Ỳ|Ý|Ỵ|Ỷ|Ỹ/g, "Y");
            str = str.replaceAll(/Đ/g, "D");
            // Some system encode vietnamese combining accent as individual utf-8 characters
            // Một vài bộ encode coi các dấu mũ, dấu chữ như một kí tự riêng biệt nên thêm hai dòng này
            str = str.replaceAll(/\u0300|\u0301|\u0303|\u0309|\u0323/g, ""); // ̀ ́ ̃ ̉ ̣  huyền, sắc, ngã, hỏi, nặng
            str = str.replaceAll(/\u02C6|\u0306|\u031B/g, ""); // ˆ ̆ ̛  Â, Ê, Ă, Ơ, Ư
            // Remove extra spaces
            // Bỏ các khoảng trắng liền nhau
            str = str.replaceAll(/ + /g, " ");
            str = str.trim();
            // Remove punctuations
            // Bỏ dấu câu, kí tự đặc biệt
            // str = str.replace(/!|@|%|\^|\*|\(|\)|\+|\=|\<|\>|\?|\/|,|\.|\:|\;|\'|\"|\&|\#|\[|\]|~|\$|_|`|-|{|}|\||\\/g," ");
            return str;
        }
        
    }
}
</script>