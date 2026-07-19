<template>
  <b-modal
    ref="popupTaoLenhLienQuan"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
    @hide="handleHideModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Tạo các câu lệnh liên quan
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <ActionTop :actions="actions" @onActionClick="onActionClick" />
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin lệnh liên quan</div>
          <div class="row">
            <div class="col-sm-6 col-12">
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w70">Tên bảng</div>
                            <div class="value">
                                <input type="text" v-model="thongtinlenh.input_tenbang" :disabled="true" class="form-control" />
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w70">Thứ tự</div>
                            <div class="value">
                                <input type="text" v-model="thongtinlenh.input_thutu" :disabled="true" @keypress="isNumber($event)" class="form-control" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-12">
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w70">Kiểu lệnh</div>
                            <div class="value">
                                <k-select v-model="thongtinlenh.kieulenh_selected" :options="thongtinlenh.kieulenh" />
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w70">Sử dụng</div>
                            <div class="value">
                                <k-select v-model="thongtinlenh.sudung_selected" :options="thongtinlenh.sudung" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6 col-12">
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w20"></div>
                            <div class="value">
                                <div class="check-action">
                                    <input type="checkbox" v-model="thongtinlenh.checkBoxThucHien" class="check" />
                                    <span class="name">Thực hiện ngay sau khi tạo bảng</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w70">Lần thực hiện</div>
                            <div class="value">
                                <input type="text" v-model="thongtinlenh.input_lanthuchien" @keypress="isNumber($event)" class="form-control" />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w20"></div>
                            <div class="value">
                                <div class="check-action">
                                    <input type="checkbox" v-model="thongtinlenh.checkBoxHienThiLoi" class="check" />
                                    <span class="name">Hiển thị lỗi</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w0"></div>
                            <div class="value">
                                <div class="check-action">
                                    <input type="checkbox" v-model="thongtinlenh.checkBoxDangSuDung" class="check" />
                                    <span class="name">Đang sử dụng</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-6 col-12">
                <div class="info-row">
                    <div class="key w70">Ghi chú</div>
                        <div class="value">
                               <textarea rows="3" v-model="thongtinlenh.ghichu" class="form-control" style="resize: none;"></textarea>
                        </div>
                    </div>
                </div>
            </div>
          </div>
          
        
        <div class="box-form">
          <div class="legend-title">Câu lệnh</div>
          <textarea name="" v-model="caulenh" class="form-control" style="height: 300px;resize: none;"></textarea>
        </div>

      </div>
    </div>
  </b-modal>
</template>
<script>
import DebtTableSQLForInvoiceCycleAPI from './DebtTableSQLForInvoiceCycleAPI'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KSelect from '@/components/kylq_components/KSelect.vue'
export default {
    name:'TaoLenhLienQuanModal',
    props:{
        data:{
            type:Object,
            required: true,
            default: ()=>{}
        },
       
    },
    components:{
        ActionTop,
        KSelect
    },
    data(){
        return {
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
                }
            ],
            thongtinlenh:{
                input_tenbang:'',
                input_thutu:'0',
                checkBoxThucHien:false,
                checkBoxHienThiLoi:false,
                checkBoxDangSuDung:false,
                input_lanthuchien:'0',

                kieulenh:[
                    {
                        ID:0,
                        NAME:'Câu lệnh SQL'
                    },
                    {
                        ID:1,
                        NAME:'Thủ tục / hàm'
                    }
                ],
                kieulenh_selected:0,
                sudung:[
                    {
                        ID:1,
                        NAME:'Sử dụng tên bảng'
                    },
                    {
                        ID:2,
                        NAME:'Sử dụng tên đối tượng'
                    },
                    {
                        ID:3,
                        NAME:'Sử dụng tên bảng và tên đối tượng'
                    },
                ],
                sudung_selected:1,
                ghichu:''
            },
            caulenh:'',
            
            tt_lenh:null

        }
    },
    methods:{
        showModal() {
            this.$refs["popupTaoLenhLienQuan"].show()
        },
        hideModal() {
            this.$refs["popupTaoLenhLienQuan"].hide()
        },
        handleShowModal(){
            this.tt_lenh=null
            this.caulenh=''
            this.thongtinlenh.input_tenbang=''
            this.thongtinlenh.input_thutu='0'
            this.thongtinlenh.checkBoxThucHien=false
            this.thongtinlenh.checkBoxHienThiLoi=false
            this.thongtinlenh.checkBoxDangSuDung=false
            this.thongtinlenh.input_lanthuchien='0'
            this.thongtinlenh.sudung_selected=1
            this.thongtinlenh.kieulenh_selected=0
            this.thongtinlenh.ghichu=''
            this.setActiveActions(1)

            //init
            console.log('data', this.data)
            this.thongtinlenh.input_tenbang=this.data.tenbang
            this.thongtinlenh.input_thutu=this.data.thutu
            

            this.sp_lay_ds_taobang_cmd_all(this.data.thutu, this.data.module_id, this.data.tenbang)

        },
        handleHideModal(){
            this.$emit("refresh")
        },
        onActionClick(action){
            if(action.id=='nhapmoi'){
                this.setActiveActions(1)
            }else if(action.id=='ghilai'){
                this.ghiLai()
            }else if(action.id=='xoa'){
                this.$confirm('Bạn thật sự muốn xóa dữ liệu không ?','Thông báo',{
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                }).then(() => {
                    this.sp_del_taobang_cmd(this.thongtinlenh.input_thutu, this.data.module_id, this.data.tenbang)
                })
            }
        },
        setActiveActions(kieu){
            this.actions.forEach((item)=>{
                item.active=false
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
            this.thongtinlenh.input_lanthuchien='1'
            this.thongtinlenh.ghichu=''
            this.caulenh=''
            this.thongtinlenh.checkBoxThucHien=false
            this.thongtinlenh.checkBoxHienThiLoi=true
            this.thongtinlenh.checkBoxDangSuDung=true

        },
        khoitao_dulieu(info){
            
            this.thongtinlenh.kieulenh_selected=info.kind
            this.thongtinlenh.sudung_selected=info.sudung
            this.thongtinlenh.ghichu=info.ghichu==null?'':info.ghichu
            this.thongtinlenh.input_lanthuchien=info.repeat
            this.caulenh=info.command
            if(info.show_exception!=null&&info.show_exception==1){
                this.thongtinlenh.checkBoxHienThiLoi=true
            }else{
                this.thongtinlenh.checkBoxHienThiLoi=false
            }
            if(info.aftercreate!=null&&info.aftercreate==1){
                this.thongtinlenh.checkBoxThucHien=true
            }else{
                this.thongtinlenh.checkBoxThucHien=false
            }  
            if(info.status!=null&&info.status==1){
                this.thongtinlenh.checkBoxDangSuDung=true
            }else{
                this.thongtinlenh.checkBoxDangSuDung=false
            }  
            
            this.setActiveActions(2)

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
        ghiLai(){
            let is_insert=this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active==true?0:1
            let data={
                sudung: this.thongtinlenh.sudung_selected,
                thutu: is_insert==0?this.thongtinlenh.input_thutu:0,
                module_id: this.data.module_id,
                ghichu: this.thongtinlenh.ghichu,
                chk_new: is_insert,
                kind: this.thongtinlenh.kieulenh_selected,
                aftercreate: this.thongtinlenh.checkBoxThucHien?1:0,
                repeat: this.thongtinlenh.input_lanthuchien,
                tenbang: this.thongtinlenh.input_tenbang,
                command: this.caulenh,
                show_exception: this.thongtinlenh.checkBoxHienThiLoi?1:0,
                status: this.thongtinlenh.checkBoxDangSuDung?1:0
            }
            this.sp_ghilai_taobang_cmd(data, is_insert)
        },
        async sp_lay_ds_taobang_cmd_all(thutu, module_id, tenbang){
            try{
                this.loading(true)
                let response=await DebtTableSQLForInvoiceCycleAPI.sp_lay_ds_taobang_cmd_all(this.axios, thutu, module_id, tenbang)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    if(response.data.data.length>0){
                        this.tt_lenh=response.data.data[0]
                        this.khoitao_dulieu(this.tt_lenh)
                    }else{
                        this.tt_lenh=null
                        this.setActiveActions(1)
                        this.$toast.info('Không có thông dữ liệu lệnh')
                    }
                }else{
                    this.tt_lenh=null
                    this.setActiveActions(1)
                    this.$toast.info('Không có thông tin dữ liệu lệnh')
                }
            }catch(error){
                this.loading(false)
                if(error.data&&error.data.message){
                    this.$toast.error(error.data.message)
                }else if(error.response&&error.response.data&&error.response.data.message){
                    this.$toast.error(error.response.data.message)
                }else{
                    this.$toast.error('Không load được danh sách')
                }
                
            }
        },
        async sp_del_taobang_cmd(thutu, module_id, tenbang){
            try{
                this.loading(true)
                let response = await DebtTableSQLForInvoiceCycleAPI.sp_del_taobang_cmd(this.axios, thutu, module_id, tenbang)
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
                        //this.sp_lay_ds_taobang_cmd_all(this.data.thutu, this.data.module_id, this.data.tenbang)
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
                if(e.data&&e.data.message){
                    // this.$alert(e.data.message, '', {
                    //     dangerouslyUseHTMLString: true,
                    //     confirmButtonText: 'OK',
                    //     type: 'error'
                    // })
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    // this.$alert(e.response.data.message, '', {
                    //     dangerouslyUseHTMLString: true,
                    //     confirmButtonText: 'OK',
                    //     type: 'error'
                    // })
                    this.$toast.error(e.response.data.message)
                }else{
                    // this.$alert('Đã xảy ra lỗi, xoá không thành công!', '', {
                    //     dangerouslyUseHTMLString: true,
                    //     confirmButtonText: 'OK',
                    //     type: 'error'
                    // })
                    this.$toast.error('Đã xảy ra lỗi, xoá không thành công!')
                }
            }
        },
        async sp_ghilai_taobang_cmd(data, kieu){
            try{
                this.loading(true)
                let response=await DebtTableSQLForInvoiceCycleAPI.sp_ghilai_taobang_cmd(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    if(response.data.data.out&&response.data.data.out=='0'){
                        // this.$alert(kieu==1?'Thêm mới dữ liệu thành công!':'Cập nhật dữ liệu thành công!', 'Thông báo', {
                        //     dangerouslyUseHTMLString: true,
                        //     confirmButtonText: 'OK',
                        //     type: 'info'
                        // }).then(()=>{
                        //     if(kieu==1){
                        //         this.hideModal()
                        //     }else{
                        //         this.setActiveActions(2)
                        //     }
                        // })
                        this.$toast.success(kieu==1?'Thêm mới dữ liệu thành công!':'Cập nhật dữ liệu thành công!')
                        if(kieu==1){
                            this.hideModal()
                        }else{
                            this.setActiveActions(2)
                        }
                    }else{
                        // this.$alert(response.data.data.out, 'Thông báo', {
                        //     dangerouslyUseHTMLString: true,
                        //     confirmButtonText: 'OK',
                        //     type: 'info'
                        // })
                        if(response.data.data.out!=null){
                            this.$toast.error(response.data.data.out)
                        }else{
                            this.$toast.error(kieu==1?'Thêm mới dữ liệu không thành công!':'Cập nhật dữ liệu không thành công!')
                        }
                        
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
                        // this.$alert(kieu==1?'Thêm mới dữ liệu không thành công!':'Cập nhật dữ liệu không thành công!', 'Lỗi', {
                        //     dangerouslyUseHTMLString: true,
                        //     confirmButtonText: 'OK',
                        //     type: 'error'
                        // })
                        this.$toast.error(kieu==1?'Thêm mới dữ liệu không thành công!':'Cập nhật dữ liệu không thành công!')
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
                    // this.$alert(kieu==1?'Thêm mới dữ liệu không thành công!':'Cập nhật dữ liệu không thành công!', 'Lỗi', {
                    //     dangerouslyUseHTMLString: true,
                    //     confirmButtonText: 'OK',
                    //     type: 'error'
                    // })
                    this.$toast.error(kieu==1?'Thêm mới dữ liệu không thành công!':'Cập nhật dữ liệu không thành công!')
                }
            }
        }
    }
}
</script>