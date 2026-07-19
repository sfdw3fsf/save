<template>
    <div class="main-wrapper">

        <breadcrumb :header="header"/>

         <ActionTop :actions="actions" @onActionClick="onActionClick"/>

        <div class="page-content">
            <div class="row">
                <div class="col-sm-4 col-12">
                    <div class="box-form">
                        <div class="legend-title">Danh sách các bảng</div>
                        <KTableV3 :columns="columns"
                            :dataSources="dataSources"
                            :allowFilter="true"
                            @onSelectedRow="onSelectedRow"
                            @doubleClickRow="doubleClickRow"
                            id="id"
                            :rowSelectedId="rowSelectedId"
                            tableHeight="600px" />
                    </div>
                </div>
                <div class="col-sm-8 col-12">
                    <div class="box-form">
                        <div class="legend-title">Câu lệnh SQL <k-required-marker/></div>
                        <div class="row">
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w50">Thứ tự <k-required-marker/></div>
                                    <div class="value">
                                        <input type="text" ref="inputThuTu" @keypress="isNumber($event)" v-model="input_thutu" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-9 col-12">
                                <div class="info-row">
                                    <div class="key w70">Tên bảng <k-required-marker/></div>
                                    <div class="value">
                                        <input type="text" ref="inputTenTbang" v-model="input_tenbang" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <textarea name="" ref="inputCauLenh" v-model="textarea_caulenh" @keydown.ctrl.83.prevent.stop="saveSQL_CTR_S" class="form-control" style="height: 600px;resize: none;"></textarea>
                    </div>
                </div>
            </div>
        </div>

        <Footer/>

        <!-- Modal -->
        <KetQuaCapNhatModal ref="ket_qua_capnhat" :tenbang="tenbang" />
        <LenhLienQuanModal ref="lenh_lien_quan" :tenbang="tenbang" :module_id="module_id" />
    </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KetQuaCapNhatModal from './KetQuaCapNhatModal.vue'
import LenhLienQuanModal from './LenhLienQuanModal.vue'
import moment from 'moment'
import { mapGetters } from 'vuex'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
//JS
import '@/assets/vendor/jquery/split'
import DebtTableSQLForInvoiceCycleAPI from './DebtTableSQLForInvoiceCycleAPI'
//id="khuvuc_id" :rowSelectedId="rowSelectedIdKhuVuc"
export default {
    name:'DebtTableSQLForInvoiceCycle',
    components:{
        Footer,
        breadcrumb,
        KTableV3,
        ActionTop,
        KetQuaCapNhatModal,
        LenhLienQuanModal,
        KRequiredMarker
    },
    data(){
        return{
            header: {
                title: "Tạo bảng nợ",
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
                    id:'xoa',
                    name:'Xoá',
                    active: true,
                    icon_class:'one-trash'
                },
                {
                    id:'update_fields',
                    name:'Update Fields',
                    active: true,
                    icon_class:'one-reload1'
                },
                {
                    id:'danhsach_fields',
                    name:'Danh sách Fields',
                    active: true,
                    icon_class:'one-clipart-1'
                },

            ],
            columns:[
                {
                    field: "thutu",
                    label: "Thứ tự",
                    allowFilter: true,
                    width:"30%",
                    allowSorting:true
                },
                {
                    field: "tenbang",
                    label: "Tên bảng",
                    allowFilter: true,
                    allowSorting:true
                }
            ],
            dataSources:[
                
            ],
            input_thutu:'',
            input_tenbang:'',
            textarea_caulenh:'',
            item_selected:null,
            tenbang:'',
            module_id:0,
            rowSelectedId:-1

        }
    },
    computed:{
        ...mapGetters('admin', [
            'getKyhoadon',
            'getChuKy'
        ])
    },
    methods:{
        onActionClick(action){
            if(action.id=='nhapmoi'){
                this.setActiveActions(1)
            }else if(action.id=='ghilai'){
                this.ghiLai()
            }else if(action.id=='xoa'){
                if(this.item_selected==null){
                    // this.$alert('Hãy chọn row để xoá!', 'Thông báo', {
                    //     dangerouslyUseHTMLString: true,
                    //     confirmButtonText: 'OK',
                    //     type: 'info'
                    // })
                    this.$toast.warning('Hãy chọn row để xoá!')
                    return
                }
                this.$confirm('Bạn thật sự muốn xóa dữ liệu không ?','Thông báo',{
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                }).then(() => {
                    this.sp_del_ds_taobang({
                        thutu:this.item_selected.thutu,
                        tenbang:this.item_selected.tenbang
                    })
                })
                
            }else if(action.id=='update_fields'){
                if(this.item_selected==null){
                    // this.$alert('Hãy chọn row để xoá!', 'Thông báo', {
                    //     dangerouslyUseHTMLString: true,
                    //     confirmButtonText: 'OK',
                    //     type: 'info'
                    // })
                    this.$toast.warning('Hãy chọn bảng để update fields!')
                    return
                }
                this.update_fields()
            }else if(action.id=='danhsach_fields'){
                if(this.item_selected==null){
                    return
                }
                this.tenbang=this.item_selected.tenbang
                this.$nextTick(()=>{
                    this.$refs.ket_qua_capnhat.showModal()
                })
            }
        },
        setActiveActions(kieu){
            this.actions.forEach((item)=>{
                if(item.id=='update_fields'||item.id=='danhsach_fields'){
                    item.active=true
                }else{
                    item.active=false
                } 
            })
            if(kieu==1){//thêm mới
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.clear()
            }else if(kieu==2){//edit
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
            }
        },
        clear(){
            this.item_selected=null
            this.input_thutu=''
            this.input_tenbang=''
            this.textarea_caulenh=''
            this.rowSelectedId=-1
            this.tenbang='',
            this.module_id=0
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
        onSelectedRow(item){
            this.item_selected=item
            this.input_thutu=item.thutu!=null?item.thutu.toString():''
            this.input_tenbang=item.tenbang!=null?item.tenbang.toString():''
            this.textarea_caulenh=item.command!=null?item.command.toString():''

            this.setActiveActions(2)
        },
        kiemtra_dulieu(){

            if(this.input_thutu.trim()==''){
                // this.$alert('Bạn chưa nhập thứ tự!', '', {
                //     dangerouslyUseHTMLString: true,
                //     confirmButtonText: 'OK',
                //     type: 'warning'
                // })
                this.$toast.error('Bạn chưa nhập thứ tự!')
                this.$nextTick(()=>{
                    this.$refs.inputThuTu.focus()
                })
                return false
            }
            if(this.input_tenbang.trim()==''){
                // this.$alert('Bạn chưa nhập tên bảng!', '', {
                //     dangerouslyUseHTMLString: true,
                //     confirmButtonText: 'OK',
                //     type: 'warning'
                // })
                this.$toast.error('Bạn chưa nhập tên bảng!')
                this.$nextTick(()=>{
                    this.$refs.inputTenTbang.focus()
                })
                return false
            }
            if(this.textarea_caulenh.trim()==''){
                // this.$alert('Bạn chưa nhập SQL!', '', {
                //     dangerouslyUseHTMLString: true,
                //     confirmButtonText: 'OK',
                //     type: 'warning'
                // })
                this.$toast.error('Bạn chưa nhập SQL!')
                this.$nextTick(()=>{
                    this.$refs.inputCauLenh.focus()
                })
                return false
            }
            // if(this.textarea_caulenh!=null&&this.textarea_caulenh.trim().length>3500){
            //     this.$alert('Trường câu lệnh không được quá 3500 ký tự!', '', {
            //         dangerouslyUseHTMLString: true,
            //         confirmButtonText: 'OK',
            //         type: 'warning'
            //     })
            //     return false
            // }

            return true
        },
        saveSQL_CTR_S(){
            this.ghiLai()
        },
        doubleClickRow(item){
            //hiển thị form lệnh liên quan
            this.tenbang=item.tenbang,
            this.module_id=item.module_id

            this.$nextTick(()=>{
                this.$refs.lenh_lien_quan.showModal()
            })
            
        },
        async update_fields(){
            try{
                this.loading(true)
                //tmp phải lấy theo kỳ hoá đơn lưu trong localstorge tương ứng với register
                var tmp
                if(this.getKyhoadon.trim()!=''){
                    tmp = moment(this.getKyhoadon.trim(),'MM/YYYY').subtract(1, 'months').format('MMYYYY')
                }else{
                    tmp = moment(new Date()).subtract(1, 'months').format('MMYYYY')
                }
                
                let data={
                    tmp: tmp,
                    tenbang: this.item_selected.tenbang
                }
                let response=await DebtTableSQLForInvoiceCycleAPI.sp_capnhat_taobang_col(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    if(response.data.data=='0'){
                        this.tenbang=this.item_selected.tenbang
                        this.$nextTick(()=>{
                            this.$refs.ket_qua_capnhat.showModal()
                        })
                    }else{
                        // this.$alert(response.data.data, 'Thông báo', {
                        //     dangerouslyUseHTMLString: true,
                        //     confirmButtonText: 'OK',
                        //     type: 'error'
                        // })
                        this.$toast.error(response.data.data)
                    }
                }else {
                    if(response && response.data &&response.data.message){
                        // this.$alert(response.data.message, '', {
                        //     dangerouslyUseHTMLString: true,
                        //     confirmButtonText: 'OK',
                        //     type: 'error'
                        // })
                        this.$toast.error(response.data.message)
                    }else{
                        // this.$alert('Update Fields không thành công!', 'Lỗi', {
                        //     dangerouslyUseHTMLString: true,
                        //     confirmButtonText: 'OK',
                        //     type: 'error'
                        // })
                        this.$toast.error('Update Fields không thành công!')
                    }
                }
            }catch(error){
                this.loading(false)
                if(error.data&&error.data.message){
                    // this.$alert(error.data.message, 'Lỗi', {
                    //     dangerouslyUseHTMLString: true,
                    //     confirmButtonText: 'OK',
                    //     type: 'error'
                    // })
                    this.$toast.error(error.data.message)
                }else if(error.response&&error.response.data&&error.response.data.message){
                    // this.$alert(error.response.data.message, 'Lỗi', {
                    //     dangerouslyUseHTMLString: true,
                    //     confirmButtonText: 'OK',
                    //     type: 'error'
                    // })
                    this.$toast.error(error.response.data.message)
                }else{
                    // this.$alert('Update Fields không thành công!', 'Lỗi', {
                    //     dangerouslyUseHTMLString: true,
                    //     confirmButtonText: 'OK',
                    //     type: 'error'
                    // })
                    this.$toast.error('Update Fields không thành công!')
                }
            }
        },
        ghiLai(){
            if(!this.kiemtra_dulieu()){
                return
            }
            var is_insert=this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active==true?0:1
            if(is_insert==0&&this.item_selected==null){
                // this.$alert('Hãy chọn row để cập nhật!', 'Thông báo', {
                //     dangerouslyUseHTMLString: true,
                //     confirmButtonText: 'OK',
                //     type: 'info'
                // })
                this.$toast.error('Hãy chọn row để cập nhật!')
                return
            }
            let data={
                thutu: this.input_thutu!=null?this.input_thutu.trim():'',
                chk_new: is_insert,
                tenbang: this.input_tenbang!=null?this.input_tenbang.trim():'',
                sql:this.textarea_caulenh!=null?this.textarea_caulenh.trim():''
            }
            this.sp_ghilai_ds_taobang(data, is_insert)
        },
        async hienthi_dulieu(){
            try{
                this.loading(true)
                //this.dataSources=[]
                let response = await DebtTableSQLForInvoiceCycleAPI.sp_lay_ds_taobang(this.axios,{
                    thutu:0,
                    tenbang:''
                })
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    this.dataSources=response.data.data.map((x, index)=>Object.assign(x,{id:index+1}))
                    if(this.dataSources.length>0){
                        //focus row
                        if(this.item_selected==null){
                            this.rowSelectedId=this.dataSources[0].id
                            this.onSelectedRow(this.dataSources[0])
                        }else{
                            const index=this.dataSources.findIndex(x=>x.thutu==this.item_selected.thutu&&x.tenbang==this.item_selected.tenbang)
                            if(index>-1){
                                this.rowSelectedId=this.dataSources[index].id
                                this.onSelectedRow(this.dataSources[index])
                            }else{
                                this.rowSelectedId=this.dataSources[0].id
                                this.onSelectedRow(this.dataSources[0])
                            }
                        }
                        
                    }
                }else{
                    this.dataSources=[]
                }
            }catch(e){
                this.loading(false)
                 if(error.data&&error.data.message){
                    // this.$alert(error.data.message, 'Lỗi', {
                    //     dangerouslyUseHTMLString: true,
                    //     confirmButtonText: 'OK',
                    //     type: 'error'
                    // })
                    this.$toast.error(error.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    // this.$alert(e.response.data.message, '', {
                    //     dangerouslyUseHTMLString: true,
                    //     confirmButtonText: 'OK',
                    //     type: 'error'
                    // })
                    this.$toast.error(e.response.data.message)
                }else{
                    // this.$alert('Đã có lỗi khi khởi tạo dữ liệu', '', {
                    //         dangerouslyUseHTMLString: true,
                    //         confirmButtonText: 'OK',
                    //         type: 'error'
                    // })
                    this.$toast.error('Đã có lỗi khi khởi tạo dữ liệu')
                }
            }
        },
        //kieu=1 thêm mới, 0 cập nhật dữ liệu
        async sp_ghilai_ds_taobang(data, kieu){
            try{
                this.loading(true)
                let response=await DebtTableSQLForInvoiceCycleAPI.sp_ghilai_ds_taobang(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    // this.$alert(kieu==1?'Thêm mới thành công!':'Cập nhật thành công!', 'Thông báo', {
                    //     dangerouslyUseHTMLString: true,
                    //     confirmButtonText: 'OK',
                    //     type: 'info'
                    // })
                    this.$toast.success(kieu==1?'Thêm mới thành công!':'Cập nhật thành công!')
                    this.hienthi_dulieu()
                    this.setActiveActions(2)
                }else {
                    if(response && response.data &&response.data.message){
                        // this.$alert(response.data.message, '', {
                        //     dangerouslyUseHTMLString: true,
                        //     confirmButtonText: 'OK',
                        //     type: 'error'
                        // })
                        this.$toast.error(response.data.message)
                    }else{
                        // this.$alert(kieu==1?'Thêm mới không thành công!':'Cập nhật không thành công!', 'Lỗi', {
                        //     dangerouslyUseHTMLString: true,
                        //     confirmButtonText: 'OK',
                        //     type: 'error'
                        // })
                        this.$toast.error(kieu==1?'Thêm mới không thành công!':'Cập nhật không thành công!')
                    }
                }
            }catch(error){
                this.loading(false)
                if(error.data&&error.data.message){
                    // this.$alert(error.data.message, 'Lỗi', {
                    //     dangerouslyUseHTMLString: true,
                    //     confirmButtonText: 'OK',
                    //     type: 'error'
                    // })
                    this.$toast.error(error.data.message)
                }else if(error.response&&error.response.data&&error.response.data.message){
                    // this.$alert(error.response.data.message, 'Lỗi', {
                    //     dangerouslyUseHTMLString: true,
                    //     confirmButtonText: 'OK',
                    //     type: 'error'
                    // })
                    this.$toast.error(error.response.data.message)
                }else{
                    // this.$alert(kieu==1?'Thêm mới không thành công!':'Cập nhật không thành công!', 'Lỗi', {
                    //     dangerouslyUseHTMLString: true,
                    //     confirmButtonText: 'OK',
                    //     type: 'error'
                    // })
                    this.$toast.error(kieu==1?'Thêm mới không thành công!':'Cập nhật không thành công!')
                }
            }
        },
        async sp_del_ds_taobang(data){
            try{
                this.loading(true)
                let response = await DebtTableSQLForInvoiceCycleAPI.sp_del_ds_taobang(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    if(response.data.data=='0'){
                        // this.$alert('Xoá thành công', 'Thông báo', {
                        //     dangerouslyUseHTMLString: true,
                        //     confirmButtonText: 'OK',
                        //     type: 'info'
                        // })
                        this.$toast.success('Xoá thành công')
                        this.clear()
                        this.setActiveActions(1)
                        this.hienthi_dulieu()
                    }else{
                        // this.$alert(response.data.data, 'Thông báo', {
                        //     dangerouslyUseHTMLString: true,
                        //     confirmButtonText: 'OK',
                        //     type: 'info'
                        // })
                        this.$toast.error(response.data.data)
                    }
                }else{
                    if(response&&response.data&&response.data.message){
                        // this.$alert(response.data.message, '', {
                        //     dangerouslyUseHTMLString: true,
                        //     confirmButtonText: 'OK',
                        //     type: 'error'
                        // })
                        this.$toast.error(response.data.message)
                    }else{
                        // this.$alert('Đã xảy ra lỗi, xoá không thành công!', '', {
                        //     dangerouslyUseHTMLString: true,
                        //     confirmButtonText: 'OK',
                        //     type: 'error'
                        // })
                        this.$toast.error('Đã xảy ra lỗi, xoá không thành công!')
                    }
                }
            }catch(e){
                console.log(e)
                this.loading(false)
                if(error.data&&error.data.message){
                    // this.$alert(error.data.message, '', {
                    //     dangerouslyUseHTMLString: true,
                    //     confirmButtonText: 'OK',
                    //     type: 'error'
                    // })
                    this.$toast.error(error.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    // this.$alert(e.response.data.message, '', {
                    //     dangerouslyUseHTMLString: true,
                    //     confirmButtonText: 'OK',
                    //     type: 'error'
                    // })
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, xoá không thành công!')
                    // this.$alert('Đã xảy ra lỗi, xoá không thành công!', '', {
                    //     dangerouslyUseHTMLString: true,
                    //     confirmButtonText: 'OK',
                    //     type: 'error'
                    // })
                }
            }
        }

    },
    mounted(){
        // console.log('kyhoadon', this.getKyhoadon)
        // console.log('chuky',this.getChuKy)
        this.setActiveActions(1)
        this.hienthi_dulieu()
    }
}
</script>