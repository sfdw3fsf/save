<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <ActionTop :actions="actions" @onActionClick="onActionClick"/>
        <div class="page-content">
            <div class="box-form">
                <div class="legend-title">Thông tin vị trí, chức danh</div>
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="box-form">
                            <div class="list-checks-ver">
                                <div class="item mart15 marb10">
                                    <div class="check-action">
                                        <input type="radio" name="rdb1" class="check" checked value="0" @change="changeLoai($event)">
                                        <span class="name">Chức danh</span>
                                    </div>
                                </div>
                                <div class="item mart10 marb10">
                                    <div class="check-action">
                                        <input type="radio" name="rdb1" class="check" value="1" @change="changeLoai($event)">
                                        <span class="name">Vị trí</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-8 col-12">
                        <div class="box-form">
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w100">{{lableChucDanhOrViTri}} <k-required-marker/></div>
                                        <div class="value">
                                            <input type="text" id="inputChucDanhVitri" ref="inputChucDanhVitri" @blur="outFocusInput('inputChucDanhVitri')" :value="input_chucdanh_vitri" @change="e=>input_chucdanh_vitri=e.target.value"  class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w40">Loại</div>
                                        <div class="value">
                                            <div class="row">
                                                <div class="col-12">
                                                    <div class="select-custom">
                                                        <select :disabled="disabledLoaiLD" v-model="loai_ld_selected" class="form-control">
                                                            <option v-for="item in ds_loaild" :value="item.LOAILD_ID" :key="item.LOAILD_ID">{{item.LOAI_LD}}</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w100">Ghi chú</div>
                                <div class="value">
                                    <input type="text" id="inputGhiChu" ref="inputGhiChu" @blur="outFocusInput('inputGhiChu')" :value="input_ghichu" @change="e=>input_ghichu=e.target.value" class="form-control">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">Danh sách vị trí, chức danh</div>
                <div id="tab1" :class="{hidden:loai==1}">
                    <KTableV3 :columns="columnsTab1"
                        :dataSources="dataSourcesTab1"
                        @onSelectedRow="onSelectedRowTab1"
                        :rowSelectedId="rowSelectedIdTab1"
                        :allowFilter="true"
                        id="chucdanh_id"/>
                </div>
                <div id="tab2" :class="{hidden:loai==0}">
                    <KTableV3 :columns="columnsTab2"
                        :dataSources="dataSourcesTab2"
                        @onSelectedRow="onSelectedRowTab2"
                        :rowSelectedId="rowSelectedIdTab2"
                        :allowFilter="true"
                        id="vitri_id"/>
                </div>
            </div>
        </div>
        <Footer/>
    </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import API from './API'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
export default {
    name:'UpdateEmployeePosition',
    components:{
        Footer,
        breadcrumb,
        ActionTop,
        KTableV3,
        KRequiredMarker
    },
    data(){
        return {
            header: {
                title: "CẬP NHẬT VỊ TRÍ NHÂN VIÊN",
                list: [
                    { name: "Lập hợp đồng", 
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
                },
            ],
            columnsTab1:[
                {
                    field:'chucdanh',
                    label:'Chức danh',
                    allowFilter:true,
                    allowSorting:true
                },
                {
                    field:'ghichu',
                    label:'Ghi chú',
                    allowFilter:true,
                    allowSorting:true
                },
                {
                    field:'chucdanh_id',
                    label:'CHUCDANH_ID',
                    allowFilter:true,
                    allowSorting:true
                },
            ],
            columnsTab2:[
                {
                    field:'vitri',
                    label:'Vị trí',
                    allowFilter:true,
                    allowSorting:true
                },
                {
                    field:'vitri_id',
                    label:'VITRI_ID',
                    allowFilter:true,
                    allowSorting:true
                }
            ],
            dataSourcesTab1:[],
            dataSourcesTab2:[],
            rowSelectedIdTab1:-1,
            rowSelectedIdTab2:-1,
            loai:0,
            input_chucdanh_vitri:'',
            input_ghichu:'',
            rowChucDanhSelected:null,
            rowViTriSelected:null,
            ds_loaild:[],
            loai_ld_selected:null,
            chucdanh_id:-1,
            vitri_id:-1

        }
    },
    computed:{
        lableChucDanhOrViTri(){
            return this.loai==0?'Chức danh':'Vị trí'
        },
        disabledLoaiLD(){
            return this.loai==1?true:false
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='nhapmoi'){
                this.setActiveActions(1)
            }else if(action.id=='ghilai'){
                this.ghiDuLieu()
            }else if(action.id=='huy'){
                if(this.loai==0){
                    if(this.rowChucDanhSelected!=null&&this.dataSourcesTab1.length>0){
                        this.onSelectedRowTab1(this.rowChucDanhSelected)
                    }else{
                        this.setActiveActions(0)
                    }
                }else{
                    if(this.rowViTriSelected!=null&&this.dataSourcesTab2.length>0){
                        this.onSelectedRowTab2(this.rowViTriSelected)
                    }else{
                        this.setActiveActions(0)
                    }
                }
            }else if(action.id=='xoa'){
                if(this.loai==0&&(this.rowChucDanhSelected==null||this.dataSourcesTab1.length==0)){
                    return
                }
                if(this.loai==1&&(this.rowViTriSelected==null||this.dataSourcesTab2.length==0)){
                    return
                }
                this.$confirm('Bạn thật sự muốn xóa dữ liệu không ?','Thông báo',{
                    confirmButtonText: 'Đồng ý',
                    cancelButtonText: 'Huỷ'
                }).then(() => {
                    this.xoa()
                }).catch(() => {})
            }
        },
        setActiveActions(kieu){
            this.actions.forEach((item)=>{
                item.active=false
            })
            if(kieu==-1){//bắt đầu
                this.$refs.inputChucDanhVitri.focus()
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
            }else if(kieu==0){//bắt đầu
                this.$refs.inputChucDanhVitri.focus()
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.clear()
            }else if(kieu==1){//thêm mới
                this.$refs.inputChucDanhVitri.focus()
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
                this.clear()

            }else if(kieu==2){//Huỷ
                this.$refs.inputChucDanhVitri.focus()
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
            this.input_chucdanh_vitri=''
            this.input_ghichu='0'
            this.loai_ld_selected=null
        },
        outFocusInput(inputId){
            let element=document.getElementById(inputId)
            if(element){
                element.classList.remove('input_batbuoc')
            }
        },
        onSelectedRowTab1(item){
            this.rowChucDanhSelected=item
            this.chucdanh_id=item.chucdanh_id
            this.input_chucdanh_vitri=item.chucdanh!=null?item.chucdanh:''
            this.input_ghichu=item.ghichu!=null?item.ghichu:''
            this.setActiveActions(3)
        },
        onSelectedRowTab2(item){
            this.rowViTriSelected=item
            this.vitri_id=item.vitri_id
            //init dữ liêu
            this.input_chucdanh_vitri=item.vitri!=null?item.vitri:''
            //this.input_ghichu=item.ghichu!=null?item.ghichu:''
            //trường ghi chứ không có cho vị trí
            this.input_ghichu=''

            this.setActiveActions(3)
        },
        async changeLoai(event){
            this.loai=event.target.value
            
            this.chucdanh_id=-1
            this.vitri_id=-1

            this.rowChucDanhSelected=null
            this.rowViTriSelected=null
            this.setActiveActions(-1)
            await this.hienthi_ds_chucdanh_vitri()
        },
        async initDuLieu(){
            this.setActiveActions(-1)
            //lấy ds loaild
            this.ds_loaild=await this.lay_ds_loaild()
            
            await this.hienthi_ds_chucdanh_vitri()

        },
        async hienthi_ds_chucdanh_vitri(){
            if(this.loai==0){
                //ds = _donviFacade.Lay_DS_ChucDanh();
                //this.dataSourcesTab1=[]
                this.dataSourcesTab1=await this.lay_ds_chucdanh()
                if(this.dataSourcesTab1.length>0){
                    if(this.chucdanh_id!=-1){
                        const index=this.dataSourcesTab1.findIndex(x=>x.chucdanh_id==this.chucdanh_id)
                        if(index>-1){
                            this.rowSelectedIdTab1=this.dataSourcesTab1[index].chucdanh_id
                            this.onSelectedRowTab1(this.dataSourcesTab1[index])
                        }else{
                            this.rowSelectedIdTab1=this.dataSourcesTab1[0].chucdanh_id
                            this.onSelectedRowTab1(this.dataSourcesTab1[0])
                        }
                    }else{
                        this.rowSelectedIdTab1=this.dataSourcesTab1[0].chucdanh_id
                        this.onSelectedRowTab1(this.dataSourcesTab1[0])
                    }
                }else{
                    this.setActiveActions(1)
                }
            }else{
                //ds = _donviFacade.Lay_DS_ViTri();
                //this.dataSourcesTab2=[]
                this.dataSourcesTab2=await this.lay_ds_vitri()
                if(this.dataSourcesTab2.length>0){
                    if(this.vitri_id!=-1){
                        const index=this.dataSourcesTab2.findIndex(x=>x.vitri_id==this.vitri_id)
                        if(index>-1){
                            this.rowSelectedIdTab2=this.dataSourcesTab2[index].vitri_id
                            this.onSelectedRowTab2(this.dataSourcesTab2[index])
                        }else{
                            this.rowSelectedIdTab2=this.dataSourcesTab2[0].vitri_id
                            this.onSelectedRowTab2(this.dataSourcesTab2[0])
                        }

                    }else{ 
                        this.rowSelectedIdTab2=this.dataSourcesTab2[0].vitri_id
                        this.onSelectedRowTab2(this.dataSourcesTab2[0])
                    }
                    
                }else{
                    this.setActiveActions(1)
                }
            }
        },
        async xoa(){
            let data={
                flag: this.loai,
                id: this.loai==0?this.rowChucDanhSelected.chucdanh_id:this.rowViTriSelected.vitri_id,
                kieu: 3
            }
            let result =await this.fn_frmchucdanh_xuly(data)
            if(result=='OK'){
                this.$toast.success('Xóa dữ liệu thành công !')
                this.clear()
                this.setActiveActions(-1)
                await this.hienthi_ds_chucdanh_vitri()
            }else{
                this.$toast.error(result)
            }
        },
        async ghiDuLieu(){
            let isInsert=this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active?false:true
            if(!(await this.kiemtra_dulieu())){
                return
            }
            //
            let json_data=this.loai==0?this.getDuLieuChucDanh():this.getDuLieuVitri()
            var id=0
            if(!isInsert){
                id=this.loai==0?this.rowChucDanhSelected.chucdanh_id:this.rowViTriSelected.vitri_id
            }
            let data={
                flag: this.loai,                  
                id: id,
                kieu: isInsert?1:2,      
                json_data: JSON.stringify(json_data)
            }

            let result =await this.fn_frmchucdanh_xuly(data)
            if(result=='OK'){
                this.$toast.success('Cập nhật vị trí, chức danh thành công !')
                if(isInsert){
                    this.chucdanh_id=-1
                    this.vitri_id=-1
                    
                }
                await this.hienthi_ds_chucdanh_vitri()
            }else{
                this.$toast.error(result)
            }
        },
        getDuLieuChucDanh(){
            let result = {}
            //result.CHUCDANH_ID=isInsert?0:this.rowChucDanhSelected.chucdanh_id
            result.CHUCDANH=this.input_chucdanh_vitri.trim()
            result.GHICHU=this.input_ghichu.trim()
            result.LOAILD_ID=this.loai_ld_selected==null?1:this.loai_ld_selected
            return result
        },
        getDuLieuVitri(){
            let result = {}
            result.VITRI=this.input_chucdanh_vitri.trim()
            return result
        },
        kiemtra_dulieu(){
            if(this.input_chucdanh_vitri.trim()==""){
                this.$toast.error(this.loai==0?'Hãy nhập tên chức danh !':'Nhập tên vị trí !')
                document.getElementById('inputChucDanhVitri').classList.add('input_batbuoc')
                this.$refs.inputChucDanhVitri.focus()
                return false
            }
            if(this.loai==0){
                if(this.input_chucdanh_vitri.trim().length>200){
                    this.$toast.error('Tên chức danh không được vượt quá 200 ký tự!')
                    document.getElementById('inputChucDanhVitri').classList.add('input_batbuoc')
                    this.$refs.inputChucDanhVitri.focus()
                    return false
                }
                if(this.input_ghichu.trim().length>300){
                    this.$toast.error('Ghi chú không được vượt quá 300 ký tự!')
                    document.getElementById('inputGhiChu').classList.add('input_batbuoc')
                    this.$refs.inputGhiChu.focus()
                    return false
                }
            }else{
                if(this.input_chucdanh_vitri.trim().length>150){
                    this.$toast.error('Tên vị trí không được vượt quá 150 ký tự!')
                    document.getElementById('inputChucDanhVitri').classList.add('input_batbuoc')
                    this.$refs.inputChucDanhVitri.focus()
                    return false
                }
            }
            return true

        },
        async lay_ds_chucdanh(){
            try{
                this.loading(true)
                let response=await API.lay_ds_chucdanh(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_ds_loaild(){
            try{
                this.loading(true)
                let response=await API.lay_ds_loaild(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_ds_vitri(){
            try{
                this.loading(true)
                let response=await API.lay_ds_vitri(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        //xoá
        async fn_frmchucdanh_xuly(data){
            try{
                this.loading(true)
                let response=await API.fn_frmchucdanh_xuly(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return response.data.data
                }else{
                    if(response&&response.data&&response.data.message){
                        return response.data.message
                    }else{
                        return 'Đã xảy ra lỗi'
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    return e.data.message
                }else if(e.response&&e.response.data&&e.response.data.message){
                    return e.response.data.message
                }else{
                    return 'Đã xảy ra lỗi'  
                }
            }
        },
        

    },
    mounted(){
        setTimeout(()=>{
            this.initDuLieu()
        },200)
    }
}
</script>
<style>
.input_batbuoc:focus {
    border-color:red;
    background-color: lightcoral;
    box-shadow: none;
}
</style>