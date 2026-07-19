<template>
  <b-modal
    ref="popupTonPhieuGiao"
    size="lg"
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
          <span class="icon one-notepad"></span> Tồn giao phiếu
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <ActionTop :actions="actions" @onActionClick="onActionClick" />
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin chi tiết tồn</div>
          <div class="info-row">
            <div class="key w70">Nhóm tồn</div>
            <div class="value">
              <multiselect
                  :options="ds_nhomton"
                  placeholder="Chọn nhóm tồn"
                  @input="changeNhomTon"
                  :showLabels="false"
                  :allowEmpty="false"
                  :value="nhomton_o_selected"
                  label="nhom_ton"
                  track-by="nhomton_id">
                  <template #noOptions>
                    Không tìm thấy thông tin
                  </template>
              </multiselect>
            </div>
          </div>
          <div class="info-row">
            <div class="key w70">Lý do tồn</div>
            <div class="value">
              <multiselect
                  :options="ds_lydoton_tc"
                  placeholder="Chọn lý do"
                  @input="changeLyDo"
                  :showLabels="false"
                  :allowEmpty="false"
                  :value="lydoton_tc_o_selected"
                  label="lydoton_tc"
                  track-by="lydotontc_id">
                  <template #noOptions>
                    Không tìm thấy thông tin
                  </template>
              </multiselect>
            </div>
          </div>
          <div class="info-row">
            <div class="key w70">Ghi chú</div>
            <div class="value">
              <input type="text" id="inputGhiChu" ref="inputGhiChu" @blur="outFocusInput('inputGhiChu')" v-model="input_ghichu" class="form-control" />
            </div>
          </div>
          <div class="legend-title mart20">Danh sách tồn</div>
          <DataGrid
            :columns="columns"
            :dataSource="dataSources"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            :showColumnCheckbox="false"
            @selectedItemsChanged="onSelectedRow"
            :enabledSelectFirstRow="true"/>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import API from './API'
import moment from 'moment'
//UR2.4.063_033
//frmGiaoPhieu_Ton
//https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=698575146&range=E817
export default {
    name:'TonPhieuGiaoModal',
    props:{
        phieu_id:{
            type:Number,
            default:0
        },
        tthd_id:{
            type:Number,
            default:0
        },
        //kieu = 1 : tra cứu
        kieu:{
            type:Number,
            default:0
        },
        // 1 : lấy nhóm thu hồi
        vthuhoi:{
            type:Number,
            default:0
        },
        ds_phieu:{
            type:Array,
            default:()=>[]
        },
        dai_700:{
            type:Boolean,
            default:false
        }
    },
    components:{
        ActionTop
    },
    computed:{
        nhomton_o_selected(){
            const nhomton=this.ds_nhomton.find(x=>x.nhomton_id==this.nhomton_selected)
            return nhomton!=undefined?nhomton:null
        },
        lydoton_tc_o_selected(){
            const lydoton=this.ds_lydoton_tc.find(x=>x.lydotontc_id==this.lydoton_tc_selected)
            return lydoton!=undefined?lydoton:null
        }
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
            columns:[
                {
                    fieldName: "lydoton_tc",
                    headerText: "Lý do tồn",
                    allowFiltering: true
                },
                {
                    fieldName: "ngay_bt",
                    headerText: "Ngày báo tồn",
                    allowFiltering: true
                },
                {
                    fieldName: "ten_nv",
                    headerText: "NV Báo tồn",
                    allowFiltering: true
                }
            ],
            dataSources:[],
            ds_nhomton:[],
            nhomton_selected:null,
            ds_lydoton_tc:[],
            lydoton_tc_selected:null,
            dt_ton:[],
            rowSelected:null,
            input_ghichu:''

        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='nhapmoi'){
                this.setButton(1)
            }else if(action.id=='ghilai'){
                this.capnhat()
            }else if(action.id=='xoa'){
                this.xoa()
            }
        },
        showModal() {
            this.$refs["popupTonPhieuGiao"].show()
        },
        hideModal() {
            this.$emit('success')
            this.$refs["popupTonPhieuGiao"].hide()
        },
        handleShowModal(){
            this.dataSources=[]
            this.ds_nhomton=[]
            this.nhomton_selected=null
            this.ds_lydoton_tc=[]
            this.lydoton_tc_selected=null
            this.dt_ton=[]
            this.rowSelected=null
            this.input_ghichu=''

            setTimeout(()=>{
                this.initDuLieu()
            }, 500)
        },
        async onSelectedRow(data){
            if(this.dataSources.length<=0||data.length<=0){
                this.rowSelected=null
                return
            }
            let item=data[0]
            this.rowSelected=item

            if(item.nhomton_id!=null&&this.nhomton_selected!=item.nhomton_id){
                this.nhomton_selected=item.nhomton_id
                this.ds_lydoton_tc = await this.lay_ds_lydoton_tc(this.nhomton_selected, this.tthd_id)
            }
            this.lydoton_tc_selected=item.lydotontc_id
            this.input_ghichu=item.ghichu!=null?item.ghichu:''
            
            this.setButton(3)


        },
        setButton(kieu){
            //clear
            this.actions.forEach((item)=>{
                item.active=false
            })
            if(kieu==-1){
                //Bắt đầu
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
            }else if(kieu==0){
                //Bắt đầu
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
            }else if(kieu==1){
                //Thêm mới
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
            }else if(kieu==2){
                //Huỷ
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
            }else if(kieu==3){
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
            }
        },
        async initDuLieu(){
            const index=this.ds_phieu.findIndex(x=>x==this.phieu_id)
            if(index==-1){
                this.ds_phieu.push(this.phieu_id)
            }
            this.setButton(-1)
            //this.ds_nhomton=await this.lay_ds_nhomton()
            if(this.vthuhoi==0){
                //TrangThaiHD.TTVT_XACMINH_NO
                if(this.tthd_id==14){
                    this.ds_nhomton=await this.sp_cbo_nhom_ton_xac_minh_no(this.tthd_id)
                }else{
                    this.ds_nhomton=await this.sp_cbo_nhom_ton_ld(1,'')
                }
            }else{
                this.ds_nhomton=await this.sp_cbo_nhom_ton_ld(2,'7')
            }
           
            if(this.ds_nhomton.length>0){
                this.nhomton_selected=this.ds_nhomton[0].nhomton_id
                //lấy lý do tồn
                this.ds_lydoton_tc= await this.lay_ds_lydoton_tc(this.nhomton_selected, this.tthd_id)

            }
            
            if(this.phieu_id!=0){
                await this.sp_ht_grid_nguyennhan_ton(this.phieu_id)
            }
            this.dt_ton=await this.lay_lydoton_tc()
            if(this.kieu==1){
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=false
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=false
                this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=false
            }
        },
        async changeNhomTon(data){
            this.nhomton_selected=data.nhomton_id
            this.lydoton_tc_selected=null
            this.ds_lydoton_tc = await this.lay_ds_lydoton_tc(this.nhomton_selected, this.tthd_id)
        },
        changeLyDo(data){
            this.lydoton_tc_selected=data.lydotontc_id
        },
        capnhat(){
            let isInsert=this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active?0:1
            if(!this.kiemtra_dulieu()){
                return
            }
            //cập nhật
            let data={
                vkieu: isInsert,
                vdsphieu: this.ds_phieu.map(x=>{
                    return {PHIEU_ID:x}
                }),
                vphieu_id: this.phieu_id,
                vlydoton_id: this.lydoton_tc_selected,
                vthuhoi: this.vthuhoi,
                vngay_bt: moment(new Date).format('DD/MM/YYYY HH:mm:ss'),
                vnhanvien_bt_id: this.$root.token.getNhanVienID(),
                vdonvi_bt_id: this.$root.token.getDonViID(),
                vnhanvien_id: this.$root.token.getNhanVienID(),
                vghichu: this.input_ghichu.trim()
            }
            if(isInsert==1){
                console.log('data',data)
                if(this.ds_phieu.length>1){
                    this.$confirm('Trên giao diện thi công đang chọn '+ this.ds_phieu.length +' thuê bao. Thêm mới hàng loạt phiếu tồn ?','Thông báo',{
                        confirmButtonText: 'Đồng ý',
                        cancelButtonText: 'Huỷ'
                    }).then(() => {
                        //handle
                        this.fn_an_capnhat_giaophieu_ton(data)
                    }).catch(()=>{})
                }else{
                    this.fn_an_capnhat_giaophieu_ton(data)
                }
            }else{
                if(this.rowSelected.nhanvien_bt_id!=this.$root.token.getNhanVienID()){
                    this.$toast.error('Lý do tồn này không phải do bạn tạo. Không được sửa!')
                    return
                }
                this.fn_an_capnhat_giaophieu_ton(data)
                
            }



        },
        kiemtra_dulieu(){
            if(this.lydoton_tc_selected==null){
                if(this.vthuhoi==1){
                    this.$toast.error('Hãy chọn lý do tồn thu hồi!')
                }else{
                    this.$toast.error('Hãy chọn lý do tồn thi công!')
                }
                return false
            }
            let data=this.dt_ton.filter(x=>(x.kieu==1&&x.lydotontc_id==this.lydoton_tc_selected))
            if(data.length>0){
                if(this.input_ghichu.trim()==''){
                    this.$toast.error('Hãy nhập nội dung ghi chú tồn')
                    document.getElementById('inputGhiChu').classList.add('input_batbuoc')
                    this.$refs.inputGhiChu.focus()
                    return false
                }
            }
            if(this.input_ghichu.trim().length>4000){
                this.$toast.error('Ghi chú không được vượt quá 4000 ký tự!')
                document.getElementById('inputGhiChu').classList.add('input_batbuoc')
                this.$refs.inputGhiChu.focus()
                return false
            }
            return true

        },
        xoa(){
            if(this.rowSelected==null){
                return
            }
            if(this.rowSelected.nhanvien_bt_id!=this.$root.token.getNhanVienID()){
                this.$toast.error('Lý do tồn này không phải do bạn tạo. Không được xóa!')
                return
            }
            this.$confirm('Bạn có chắc chắn muốn xóa lý do tồn giao phiếu không?','Thông báo',{
                        confirmButtonText: 'Đồng ý',
                        cancelButtonText: 'Huỷ'
            }).then(() => {
                //this.fn_an_capnhat_giaophieu_ton(data)
                //chưa đúng
                let data={
                    vlydoton_id: this.rowSelected.lydotontc_id,
                    vnhanvien_bt_id: this.rowSelected.nhanvien_bt_id,
                    vngay_cn: this.rowSelected.ngay_bt,
                    vphieu_id: this.phieu_id
                }
                this.fn_an_xoa_giaophieu_ton(data)
            }).catch(()=>{})

        },
        async lay_ds_nhomton(){
            try{
                this.loading(true)
                let response = await API.lay_ds_nhomton(this.axios)
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
        async sp_cbo_nhom_ton_xac_minh_no(tthd_id){
            try{
                this.loading(true)
                let response = await API.sp_cbo_nhom_ton_xac_minh_no(this.axios, tthd_id)
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
        async sp_cbo_nhom_ton_ld(kieu_id, nhomton_id){
            try{
                this.loading(true)
                let response = await API.sp_cbo_nhom_ton_ld(this.axios, kieu_id, nhomton_id)
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
        async lay_ds_lydoton_tc(nhomton_id, tthd_id){
            try{
                this.loading(true)
                let response = await API.lay_ds_lydoton_tc(this.axios, nhomton_id, tthd_id)
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
        async sp_ht_grid_nguyennhan_ton(phieu_id){
            try{
                this.loading(true)
                this.dataSources=[]
                let response = await API.sp_ht_grid_nguyennhan_ton(this.axios, phieu_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.dataSources=response.data.data
                }else{
                    this.dataSources= []
                }
            }catch(e){
                this.$toast.error('Đã xảy ra lỗi, không lấy được dữ liệu')
                this.loading(false)
            }
        },
        async lay_lydoton_tc(){
            try{
                this.loading(true)
                let response = await API.lay_lydoton_tc(this.axios)
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
        async fn_an_capnhat_giaophieu_ton(data){
            try{
                this.loading(true)
                let response = await API.fn_an_capnhat_giaophieu_ton(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.$toast.success(response.data.data)
                    this.sp_ht_grid_nguyennhan_ton(this.phieu_id)
                }else{
                    if(response && response.data && response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã có lỗi xảy ra')
                    }
                }
            }catch(e){
                this.loading(false)
                if(error.data&&error.data.message){
                    this.$toast.error(error.data.message)
                }else if(error.response&&error.response.data&&error.response.data.message){
                    this.$toast.error(error.response.data.message)
                }else{
                    this.$toast.error('Đã có lỗi xảy ra')
                }
            }
        },
        outFocusInput(inputId){
            let element=document.getElementById(inputId)
            if(element){
                element.classList.remove('input_batbuoc')
            }
        },
        async fn_an_xoa_giaophieu_ton(data){
            try{
                this.loading(true)
                let response = await API.fn_an_xoa_giaophieu_ton(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.$toast.success(response.data.data)
                    this.setButton(1)
                    this.rowSelected=null
                    this.sp_ht_grid_nguyennhan_ton(this.phieu_id)
                }else{
                    if(response && response.data && response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã có lỗi xảy ra')
                    }
                }
            }catch(e){
                this.loading(false)
                if(error.data&&error.data.message){
                    this.$toast.error(error.data.message)
                }else if(error.response&&error.response.data&&error.response.data.message){
                    this.$toast.error(error.response.data.message)
                }else{
                    this.$toast.error('Đã có lỗi xảy ra')
                }
            }
        },
    }
}
</script>
<style>
.multiselect, .multiselect__input, .multiselect__single {
    font-size: 1rem !important;
}
.multiselect__tags{
    min-height: 32px !important;
    border: 1px solid #ced4da !important;
    padding: 6px 32px 0 6px;
    border-radius: 4px;
}
.multiselect__placeholder {
    margin-bottom: 5px!important;
    padding-top: 0px!important;
}
.multiselect__select{
    top: 0px!important;
    padding: 10px 9px!important;
}
.multiselect--disabled{
  background: none!important;
}
.multiselect--disabled .multiselect__current, .multiselect--disabled .multiselect__select{
  background: none!important;
}
.pl-2{
    padding-left: 2px!important;
}
.input_batbuoc:focus {
    border-color:red;
    background-color: lightcoral;
    box-shadow: none;
}
</style>
