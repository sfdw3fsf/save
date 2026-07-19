<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <ActionTop :actions="actions" @onActionClick="onActionClick"/>
        <div class="page-content">
            <div class="box-form">
                <div class="legend-title">Thông tin danh mục kênh</div>
                <div class="info-row">
                    <div class="key">Mã nhóm</div>
                    <div class="value">
                        <input type="text" readonly  :value="input_ma_nhom" @change="e=>input_ma_nhom=e.target.value" class="form-control">
                    </div>
                </div>
                <div class="info-row">
                    <div class="key">Tên nhóm <k-required-marker/></div>
                    <div class="value">
                        <input type="text" ref="inputTenNhom"  :value="input_ten_nhom" @change="e=>input_ten_nhom=e.target.value" class="form-control">
                    </div>
                </div>
                <div class="info-row">
                    <div class="key">Thuộc nhóm</div>
                    <div class="value">
                        <div class="select-custom" ref="thuocnhom">
                            <select2 :settings="{ dropdownParent: $refs['thuocnhom'] }"
                                v-model="thuocnhom_selected" class="select2"
                                :options="ds_thuocnhom.map(e=> ({id: e.nhomcd_id, text: e.ten_nhom}))"
                                >
                            </select2>
                        </div>  
                    </div>
                </div>
                <div class="info-row">
                    <div class="key">Tình trạng <k-required-marker/></div>
                    <div class="value">
                        <div class="select-custom" ref="tinhtrang">
                            <select2  :settings="{ dropdownParent: $refs['tinhtrang'] }"
                                v-model="tinhtrang_selected" class="select2"
                                :options="ds_tinhtrang"
                                >
                            </select2>
                        </div>  
                    </div>
                </div>

            </div>
            <div class="box-form">
                <div class="legend-title">Danh sách danh mục nhóm công đoạn</div>
                <KDataGrid
                    ref="gridDs"
                    :columns="columns"
                    :dataSource="dataSources"
                    :showColumnCheckbox="false"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :enabledSelectFirstRow="true"
                    @onRowSelected="onSelectedRow"
                    @rowSelected="rowGridSelected"/>
            </div>
            
        </div>
    </div>
</template>
<script>
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
import API from './API'
export default {
    name:'PhaseGroupCatalog',
    components:{
        breadcrumb,
        ActionTop,
        KDataGrid,
        KRequiredMarker
    },
    data(){
        return {
            header: {
                title: "Danh mục nhóm công đoạn",
                list: [
                    {
                        name: "Nhóm công đoạn",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ]
            },
            actions:[
                {
                    id:'nhapmoi',
                    name:'Nhập mới',
                    active: true,
                    icon_class:'one-file-plus'
                },
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save'
                },
                {
                    id:'huy',
                    name:'Huỷ',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_circle-remove'
                },
                {
                    id:'sua',
                    name:'Sửa',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_edit-74'
                },
                {
                    id:'xoa',
                    name:'Xoá',
                    active: true,
                    icon_class:'one-trash'
                },
            ],
            input_ma_nhom:'',
            input_ten_nhom:'',
            thuocnhom_selected:null,
            ds_thuocnhom:[],
            tinhtrang_selected:null,
            //1- Active, 0-Inactive
            ds_tinhtrang:[
                {
                    id:1,
                    text:'Đang sử dụng'
                },
                {
                    id:0,
                    text:'Không sử dụng'
                },
            ],
            columns:[
                {
                    fieldName:'ma_nhom',
                    headerText:'Mã nhóm',
                    allowFiltering:true,
                    allowSorting:true
                },
                {
                    fieldName:'ten_nhom',
                    headerText:'Tên nhóm',
                    allowFiltering:true,
                    allowSorting:true
                },
                {
                    fieldName:'thuocnhom',
                    headerText:'Thuộc nhóm',
                    allowFiltering:true,
                    allowSorting:true
                },
                {
                    fieldName:'tinhtrang',
                    headerText:'Tình trạng',
                    allowFiltering:true,
                    allowSorting:true
                },
            ],
            dataSources:[],
            selectIndex:0,
            nhomcd_id:0
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='nhapmoi'){
                this.setActiveActions(1)
            }else if(action.id=='ghilai'){
                this.CapNhat()
            }else if(action.id=='huy'){
                if(this.nhomcd_id!=0&&this.dataSources.length>0){
                    const index=this.dataSources.findIndex(x=>x.nhomcd_id==this.nhomcd_id)
                    if(index>-1){
                        this.onSelectedRow(this.dataSources[index])
                    }
                }else{
                    this.setActiveActions(0)
                }
            }else if(action.id=='xoa'){
                if(this.nhomcd_id==0){
                    this.$toast.error('Bạn chưa chọn nhóm công đoạn!')
                    return
                }
                if(this.dataSources.length==0){
                    this.$toast.error('Không có dữ liệu để xoá')
                    return
                }
                this.$confirm('Bạn thật sự muốn xóa dữ liệu không ?','Thông báo',{
                    confirmButtonText: 'Đồng ý',
                    cancelButtonText: 'Huỷ'
                }).then(() => {
                    this.xoaNhomCD(this.nhomcd_id)
                })
            }
        },
        setActiveActions(kieu){
            this.actions.forEach((item)=>{
                if(item.id!='sua'){
                    item.active=false
                }
            })
            if(kieu==-1){//bắt đầu
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
            }else if(kieu==0){//bắt đầu
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.clear()
            }else if(kieu==1){//thêm mới
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
                this.clear()

            }else if(kieu==2){//Huỷ
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
                this.clear()
            }else if(kieu==3){//edit
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
            }
        },
        clear(){
            this.input_ma_nhom=''
            this.input_ten_nhom=''
            this.thuocnhom_selected=-1
        },
        async initDuLieu(){
            this.loadDuLieu()
        },
        async loadDuLieu(){
            let data=await this.get_list_nhomcongdoan()

            this.ds_thuocnhom=[{
                nhomcd_id:-1,
                ten_nhom:''
            }].concat(data)
            this.dataSources=data.map(x=>{
                let tn=data.find(i=>i.nhomcd_id==x.nhomcd_cha_id)
                x.thuocnhom=tn?tn.ten_nhom:''
                let tt=this.ds_tinhtrang.find(i=>x.trangthai!=null&&(i.id==x.trangthai))
                x.tinhtrang=tt?tt.text:''
                return x
            })


        },
        async xoaNhomCD(nhomcd_id){
            let rs=await this.delete_nhomcongdoan(nhomcd_id)
            if(!rs||rs=='OK'||rs=='ok'){
                this.$toast.success('Xóa nhóm công đoạn thành công!')
                this.nhomcd_id=0
                this.selectIndex=0
                this.$refs.gridDs.flagSelectedRowIndexes=[this.selectIndex]
                await this.loadDuLieu()
            }else{
                this.$toast.error(rs)
            }
        },
       async CapNhat(){
            //Kiểm tra dữ liệu
            if(this.input_ten_nhom.trim()==''){
                this.$toast.error('Hãy nhập tên nhóm !')
                this.$refs.inputTenNhom.focus()
                return
            }
            if(this.tinhtrang_selected==null){
                this.$toast.error('Hãy chọn trạng thái nhóm công đoạn!')
                return
            }
            let isInsert=this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active?false:true
            if(isInsert){
                let data={
                    p_tennhom:this.input_ten_nhom.trim(),
                    p_nhomcd_cha_id:this.thuocnhom_selected!=-1?this.thuocnhom_selected:null,
                    p_trangthai:this.tinhtrang_selected
                }
                let rs=await this.insert_nhomcongdoan(data)
                if(rs){
                    this.$toast.success('Thêm nhóm công đoạn thành công!')
                    this.selectIndex=0
                    this.nhomcd_id=0
                    this.$refs.gridDs.flagSelectedRowIndexes=[this.selectIndex]
                    await this.loadDuLieu()
                }
            }else{
                let data={
                    p_id_nhomcongdoan:this.nhomcd_id,
                    p_tennhom:this.input_ten_nhom.trim(),
                    p_nhomcd_cha_id:this.thuocnhom_selected!=-1?this.thuocnhom_selected:null,
                    p_trangthai:this.tinhtrang_selected
                }
                let rs=await this.update_nhomcongdoan(data)
                if(rs){
                    this.$toast.success('Cập nhật nhóm công đoạn thành công!')
                    this.$refs.gridDs.flagSelectedRowIndexes=[this.selectIndex]
                    await this.loadDuLieu()
                }
            }

        },
        async onSelectedRow(data){
            if(!data){
                this.clear()
                return
            }
            let item=Array.isArray(data)?data[0]:data
            this.nhomcd_id=item.nhomcd_id

            this.input_ten_nhom=item.ten_nhom?item.ten_nhom.toString():''
            this.input_ma_nhom=item.ma_nhom?item.ma_nhom.toString():''
            this.thuocnhom_selected=item.nhomcd_cha_id!=null?item.nhomcd_cha_id:null
            this.tinhtrang_selected=item.trangthai!=null?item.trangthai:null

            this.setActiveActions(3)
        },
        rowGridSelected(args){
            this.selectIndex=args.rowIndex
            console.log('selectIndex', this.selectIndex)
        },
        async get_list_nhomcongdoan(){
            try{
                this.loading(true)
                let response = await API.get_list_nhomcongdoan(this.axios)
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
        async delete_nhomcongdoan(nhomcb_id){
            try{
                this.loading(true)
                let response = await API.delete_nhomcongdoan(this.axios, nhomcb_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return response.data.data
                }else{
                    if(response&&response.data&&response.data.message){
                        return response.data.message
                    }else{
                        return 'Đã xảy ra lỗi, xoá dữ liệu không thành công'
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    return e.data.message
                }else if(e.response&&e.response.data&&e.response.data.message){
                    return e.response.data.message
                }else{
                    return 'Đã xảy ra lỗi, xoá dữ liệu không thành công'
                }
            }
        },
        async insert_nhomcongdoan(data){
            try{
                this.loading(true)
                let response = await API.insert_nhomcongdoan(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    if(response.data.data=='ok'){
                        return true
                    }else{
                        this.$toast.error(response.data.data)
                        return false
                    }
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, thêm dữ liệu không thành công')
                    }
                    return false
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, thêm dữ liệu không thành công')
                }
                return false
            }
        },
        async update_nhomcongdoan(data){
            try{
                this.loading(true)
                let response = await API.update_nhomcongdoan(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    if(response.data.data=='ok'){
                        return true
                    }else{
                        this.$toast.error(response.data.data)
                        return false
                    }
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, thêm dữ liệu không thành công')
                    }
                    return false
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, thêm dữ liệu không thành công')
                }
                return false
            }
        },
    },
    mounted(){
        this.setActiveActions(-1)
        setTimeout(()=>{
            this.initDuLieu()
        }, 500)
    }
}
</script>