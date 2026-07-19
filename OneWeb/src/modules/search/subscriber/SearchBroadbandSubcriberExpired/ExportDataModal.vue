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
                <KTableV1 :columns="columnsDsCot"
                    :dataSources="dataSourcesDsCot"
                    :allowCheckBox="true"
                    @removeRow="removeRow"
                    @dataCheckeds="dataCheckeds"
                    :allowFilter="true"
                    id="id"
                    tableHeight="300px"
                />
            </div>
            <div class="col-sm-8 col-12">
                <div class="legend-title">
                    <div class="pull-left">Xem trước dữ liệu</div>
                    <div class="pull-right">
                        <div class="check-action normal">
                            <input type="checkbox" class="check">
                            <span class="name">Xuất dữ liệu tiếng Việt không dấu</span>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <KTableV3 :columns="columns"
                    :dataSources="dataSourcesPreview"
                    :allowFilter="false"
                    tableHeight="300px"
                    id="ID"
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
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import KTableV1 from './KTableV1.vue'
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
        KTableV3,
        KTableV1,
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
                    field:'tencot',
                    label:'Tên cột',
                    allowFilter:true
                },
                {
                    field:'button',
                    label:'',
                    allowFilter:true,
                    width:'20%'
                },
            ],
            dataSourcesDsCot:[],
            columns:[],
            dataSourcesPreview:[],
            dataCheckDsCot:[],
            dataSources:[],
            sheetAName:'Sheet1',
            fileName: ''
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='xuatfile'){
                if(this.dataSourcesPreview.length==0){
                    this.$toast.error('Không có dữ liệu để xuất ra!')
                    return
                }

                this.$refs.saveFileModal.showModal()

                // let danhsach_excel=[]
                // let array=this.columns.map(x=>x.field)
                // this.dataSourcesPreview.forEach(item=>{
                //     const object={}
                //     for (const name of array) {
                //         object[name] = item[name]
                //     }
                //     danhsach_excel.push(object)
                // })
                // let data = XLSX.utils.json_to_sheet(danhsach_excel);
                // let wb = XLSX.utils.book_new(); // make Workbook of Excel
                // XLSX.utils.book_append_sheet(wb, data, this.sheetAName); // sheetAName is name of Worksheet
                // // export Excel file
                // XLSX.writeFile(wb, this.fileName);
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
            this.dataCheckDsCot=[]
            //data clone
            this.dataSources=[]
            this.fileName=''
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
            this.data.forEach((x, index)=>{
                this.dataSources.push(Object.assign({id:index+1},x))
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
            this.dataSourcesDsCot=Object.keys(this.dataSources[0]).filter(x=>x!='CHECKED'&&x!='ID').map(x=>{
                return {
                    id: x,
                    tencot: x
                }
            })
            

        },
        download(value){
            //tạo ds
            let danhsach=[]
            let array=this.columns.map(x=>x.field)
            this.dataSourcesPreview.forEach(item=>{
                const object={}
                for (const name of array) {
                    object[name] = item[name]
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
                        temp.push(item[column.field])
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
        dataCheckeds(data){
            this.dataCheckDsCot=data
        },
        removeRow(item){
            const index =this.dataSourcesDsCot.findIndex(x=>x.id==item.id)
            if(index>-1){
                this.dataSourcesDsCot.splice(index,1)
            }
        },
        xemdulieu(){
            if(this.dataCheckDsCot.length==0){
                this.$toast.error('Chọn cột để xem')
                return
            }
            if(this.data.length==0){
                this.$toast.error('Chưa có dữ liệu xem báo cáo')
                return
            }
            //
            this.columns=[]
            this.dataCheckDsCot.forEach((item)=>{
                this.columns.push({
                    field: item.tencot,
                    label: item.tencot,
                    allowFilter:false
                })
            })
            //
            this.dataSourcesPreview=this.dataSources

        },
        
    }
}
</script>