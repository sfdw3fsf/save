<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <ActionTop :actions="actions" @onActionClick="onActionClick" />

        <div class="page-content">
            <div class="box-form">
                
                <div class="info-row">
                    <div class="key w100">Mã thuộc tính <k-required-marker/></div>
                    <div class="value">
                        <input type="text" v-model="input_ma_tt" class="form-control">
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w100">Thuộc tính <k-required-marker/></div>
                    <div class="value">
                        <input type="text" v-model="input_thuoctinh" class="form-control">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w100 k-w">Kiểu DL <k-required-marker/></div>
                            <div class="value">
                                <div class="select-custom" ref="ds_kieudl">
                                    <select2 :settings="{ dropdownParent: $refs['ds_kieudl'] }" ref="cboKieuDL"
                                        v-model="kieudl_selected" class="select2"
                                        :options="kieudl.map(e=> ({id: e.kieudl, text: e.ten_kieudl}))"
                                    >
                                    </select2>
                                </div>  
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w80 k-w">Độ dài</div>
                            <div class="value k">
                                <input type="text" v-model="input_dodai" @keypress="isNumber($event)" class="form-control">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">Thuộc tính</div>
                <KDataGrid
                    ref="gridThuocTinh"
                    :columns="columns"
                    :dataSource="dataSources"
                    :showColumnCheckbox="false"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :enabledSelectFirstRow="true"
                    @rowSelected="rowSelected"
                    @selectedItemsChanged="gridDsChanged"/>
            </div>
            
        </div>

        <Footer/>
    
    </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import AttributeAPI from './AttributeAPI'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
export default {
    name:'Attribute',
    components:{
        Footer,
        breadcrumb,
        KDataGrid,
        ActionTop,
        KRequiredMarker
    },
    data(){
        return {
            header: {
                title: "Thuộc tính",
                list: [
                    { 
                        name: "Lập hợp đồng", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    {
                        name: "Lắp đặt mới",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ],
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
                    id:'xoa',
                    name:'Xoá',
                    active: true,
                    icon_class:'one-trash'
                }
               
            ],
            columns:[
                {
                    fieldName: "ma_tt",
                    headerText: "Mã thuộc tính",
                    allowFiltering: true
                },
                {
                    fieldName: "thuoctinh",
                    headerText: "Thuộc tính",
                    allowFiltering: true
                },
                {
                    fieldName: "kieu_dl",
                    headerText: "Kiểu DL",
                    allowFiltering: true
                },
                {
                    fieldName: "dodai",
                    headerText: "Độ dài",
                    allowFiltering: true
                }
            ],
            dataSources:[],
            kieudl:[],
            kieudl_selected:null,
            input_ma_tt:'',
            input_thuoctinh:'',
            input_dodai:'',
            thuoctinh_id:0,
            thuoctinh_selected:null,
            selectIndex:0

        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='nhapmoi'){
                this.setActiveActions(1)
            }else if(action.id=='ghilai'){
                this.ghiDuLieu()
            }else if(action.id=='huy'){
                if(this.thuoctinh_selected!=null){
                    this.onSelectedRow(this.thuoctinh_selected)
                }else{
                    this.setActiveActions(0)
                }
            }else if(action.id=='xoa'){
                if(this.thuoctinh_id==0){
                    return
                }
                this.$confirm('Bạn thật sự muốn xóa dữ liệu không?','',{
                    confirmButtonText: 'Đồng ý',
                    cancelButtonText: 'Huỷ',
                    type: 'info'
                }).then(() => {
                    let data={
                        vthuoctinh_id: this.thuoctinh_id,
                        vkieu: 2,
                    }
                    this.capnhat_thuoctinh(data)
                })
            }
        },
        setActiveActions(kieu){
            this.actions.forEach((item)=>{
                item.active=false
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
            this.input_ma_tt=''
            this.input_thuoctinh=''
            this.input_dodai=''
            this.thuoctinh_id=0
        },
        gridDsChanged(data){
            if(this.dataSources.length<=0||data.length<=0){
                this.clear()
                this.thuoctinh_selected=null
                this.thuoctinh_id=0
                return
            }
            this.onSelectedRow(data[0])
        },
        rowSelected(args){
            this.selectIndex=args.rowIndex
        },
        onSelectedRow(item){
            this.thuoctinh_selected=item
            this.thuoctinh_id=item.thuoctinh_id

            this.input_ma_tt=item.ma_tt
            this.input_thuoctinh=item.thuoctinh
            this.input_dodai=item.dodai

            //combox dl

            const index=this.kieudl.findIndex(x=>(item.kieu_dl&&x.kieudl==item.kieu_dl))
            if(index>-1){
                this.kieudl_selected=item.kieu_dl
            }else{
                this.kieudl_selected=null
            }

            
            this.setActiveActions(3)
        },
        async hienthi_ds_thuoctinh(){
            this.thuoctinh_selected=null
            //this.dataSources=[]
            this.dataSources=await this.lay_danhsach_thuoctinh()
            if(this.dataSources.length>0){

            }else{
                this.setActiveActions(1)
            }

        },
        ghiDuLieu(){
            if(this.input_ma_tt.trim()==''){
                this.$toast.error('Chưa nhập mã thuộc tính!')
                return
            }
            if(this.input_thuoctinh.trim()==''){
                this.$toast.error('Chưa nhập tên thuộc tính!')
                return
            }
            if(this.kieudl_selected==null){
                this.$toast.error('Chưa chọn kiểu dữ liệu!')
                return
            }
            this.input_dodai=this.input_dodai!=null?this.input_dodai:''
            if(this.input_dodai.toString().trim().length>5){
                this.$toast.error('Hãy nhập độ dài thuộc tính không vượt quá 5 ký tự!')
                return
            }

            let is_insert=this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active?0:1
            if(is_insert==1){
                const index=this.dataSources.findIndex(x=>(x.ma_tt&&x.ma_tt.toString().trim()==this.input_ma_tt.trim()))
                if(index>-1){
                    this.$toast.error('Mã thuộc tính bị trùng!')
                    return
                }
            }else{
                const index=this.dataSources.findIndex(x=>(x.ma_tt&&x.ma_tt.toString().trim()==this.input_ma_tt.trim()&&x.thuoctinh_id!=this.thuoctinh_id))
                if(index>-1){
                    this.$toast.error('Mã thuộc tính bị trùng!')
                    return
                }
            }
            this.$confirm('Bạn có muốn cập nhật dữ liệu không?','',{
                confirmButtonText: 'Đồng ý',
                cancelButtonText: 'Huỷ',
                type: 'info'
            }).then(() => {
                let data={
                    vthuoctinh_id: this.thuoctinh_id,
                    vkieudl: this.kieudl_selected,
                    vkieu: is_insert,
                    vmathuoctinh: this.input_ma_tt,
                    vdodai: Number(this.input_dodai),
                    vthuoctinh: this.input_thuoctinh
                }
                this.capnhat_thuoctinh(data)
            }).catch(()=>{})
            //
            

        },
        isNumber(evt) {
            evt = (evt) ? evt : window.event;
            var charCode = (evt.which) ? evt.which : evt.keyCode;
            if ((charCode > 31 && (charCode < 48 || charCode > 57))) {
                evt.preventDefault()
            } else {
                return true
            }
        },
        async lay_danhsach_kieu_dl(){
            try{
                this.loading(true)
                this.kieudl=[]
                let response=await AttributeAPI.lay_danhsach_kieu_dl(this.axios)
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
        async lay_danhsach_thuoctinh(){
            try{
                this.loading(true)
                let response=await AttributeAPI.lay_danhsach_thuoctinh(this.axios)
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
        async capnhat_thuoctinh(data){
            try{
                this.loading(true)
                let mes_error=data.vkieu==0?'Đã xảy ra lỗi, cập nhật thuộc tính không thành công':data.vkieu==1?'Đã xảy ra lỗi, thêm mới thuộc tính không thành công':'Đã xảy ra lỗi, xoá thuộc tính không thành công'
                let mes_succ=data.vkieu==0?'Cập nhật thuộc tính thành công!':data.vkieu==1?'Thêm mới thuộc tính thành công!':'Xóa thuộc tính thành công!'
                let response=await AttributeAPI.capnhat_thuoctinh(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                   if(response.data.data=='1'){
                        this.$toast.success(mes_succ)
                        if(data.vkieu==1){
                            this.dataSources=[]
                            this.selectIndex=0
                        }else if(data.vkieu==0){
                            this.$refs.gridThuocTinh.flagSelectedRowIndexes=[this.selectIndex]
                        }else{
                            this.selectIndex=0
                        }
                        await this.hienthi_ds_thuoctinh()
                   }else{
                       this.$toast.error(response.data.data)
                   }
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error(mes_error)
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error(mes_error)
                }
            }
        },
        async initDulieu(){
            this.kieudl=await this.lay_danhsach_kieu_dl()
            if(this.kieudl.length>0){
                this.kieudl_selected=this.kieudl[0].kieudl
            }
            await this.hienthi_ds_thuoctinh()
        }
    },
    mounted(){
        this.setActiveActions(-1)
        setTimeout(()=>{
            this.initDulieu()
        },500)
    }


}
</script>
<style>
.k-w{
    padding: 4px 2px 0px 8px !important;
}
.k{
    padding: 1px 2px 0px 8px !important;
}
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
    white-space: normal !important;
    word-break: break-all !important;
}
/* asset css select2 form */
.select2-container {
    width: 100% !important;
}

.select2-container--default .select2-selection--single {
    border-color: #E0E0E0;
    outline: none;
}

.select2-dropdown {
    border: 1px solid #E0E0E0;
}

.select2-container--default .select2-selection--single .select2-selection__arrow b {
    font-family: "FontAwesome";
    border: 0px;
    top: 0px;
    height: 1.428rem;
    bottom: 0px;
    line-height: 1.428rem;
    left: 5px;
    margin: auto;
}
.select2-container--default .select2-selection--single .select2-selection__arrow b:before {
    content: "";
}

.select2-container--default .select2-search--dropdown .select2-search__field {
    border: 0px;
    outline: none;
}
.select2-container--default.select2-container--disabled .select2-selection--single{
    background-color: #e9ecef!important
}
</style>