<template>
  <b-modal
    ref="popupCapNhatDichVuHoDan"
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
          <span class="icon one-notepad"></span> Cập nhật dịch vụ hộ dân
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>

      <ActionTop :actions="actions"  @onActionClick="onActionClick"/>
      
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin cập nhật</div>
          <div class="row">
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w60">Dich vụ <k-required-marker/></div>
                <div class="value">
                    <div class="select-custom" ref="ds_dichvu">
                        <select2 :settings="{ dropdownParent: $refs['ds_dichvu'] }" ref="cboDichVu"
                            v-model="dichvu_selected" class="select2"
                            :options="danhsach_dichvu.map(e=> ({id: e.DICHVUVT_ID, text: e.TEN_DVVT}))"
                            @change="changeDichVu()"
                            >
                        </select2>
                    </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w60">Kiểu SD</div>
                <div class="value">
                    <div class="select-custom" ref="ds_kieu_sd">
                        <select2 :settings="{ dropdownParent: $refs['ds_kieu_sd'] }" ref="cboKieuSD"
                            v-model="kieu_sudung_selected" class="select2"
                            :options="kieu_sudung.map(e=> ({id: e.id, text: e.name}))"
                            >
                        </select2>
                    </div>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w90">Loại hình TB <k-required-marker/></div>
                <div class="value">
                    <div class="select-custom" ref="ds_loaihinh">
                        <select2 :settings="{ dropdownParent: $refs['ds_loaihinh'] }" ref="cboLoaiHinh"
                            v-model="loaihinh_tb_selected" class="select2"
                            :options="loaihinh_tb_filter.map(e=> ({id: e.LOAITB_ID, text: e.LOAIHINH_TB}))"
                            >
                        </select2>
                    </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Thời hạn CK</div>
                <div class="value">
                  <div class="input-icon-right">
                    <k-date-picker v-model="thoihan_ck"/>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w90">Chủ quản</div>
                <div class="value">
                    <div class="select-custom" ref="ds_chuquan">
                        <select2 :settings="{ dropdownParent: $refs['ds_chuquan'] }" ref="cboChuQuan"
                            v-model="chuquan_selected" class="select2"
                            :options="danhsach_chuquan.map(e=> ({id: e.CHUQUAN_ID, text: e.TENCHUQUAN}))"
                            >
                        </select2>
                    </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">SL TV</div>
                <div class="value">
                  <input type="number" v-model="sl_tv" ref="inputSL_TV" class="form-control" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Thông tin dịch vụ hộ dân</div>
          <KDataGrid
              :columns="columns"
              :dataSource="dataSources"
              :showColumnCheckbox="false"
              :enable-paging-server="false"
              :allowPaging="true"
              :showFilter="true"
              :enabledSelectFirstRow="true"
              @selectedItemsChanged="gridDsChanged"/>
        </div>
      </div>

    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
import UpdateCustomerLocationAPI from './UpdateCustomerLocationAPI'
import KDataGrid from '@/components/kylq_components/KDataGrid'
export default {
    name:'CapNhatDichVuHoDanModal',
    props:{
      hodan_id:{
        type:Number,
        default: -1,
        required: true
      }
    },
    components:{
        ActionTop,
        KDataGrid,
        KDatePicker,
        KRequiredMarker
    },
    data(){
        return{
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
                    fieldName:'ten_dvvt',
                    headerText:'Dịch vụ VT',
                    allowFiltering:true
                },
                {
                    fieldName:'loaihinh_tb',
                    headerText:'Loại hình TB',
                    allowFiltering:true
                },
                {
                    fieldName:'thoihan_ck',
                    headerText:'Thời hạn CK',
                    allowFiltering:true
                },
                {
                    fieldName:'tenchuquan',
                    headerText:'Chủ quản',
                    allowFiltering:true
                },
                {
                    fieldName:'kieu_sd',
                    headerText:'Kiểu SD',
                    allowFiltering:true
                },
                {
                    fieldName:'soluong_tv',
                    headerText:'Số lượng TV',
                    allowFiltering:true
                }
               
            ],
            dataSources:[],
            thoihan_ck: moment(new Date()).format('DD/MM/YYYY'),

            danhsach_dichvu:[],
            dichvu_selected:null,
            kieu_sudung:[
              {
                id:1,
                name:'Đang sử dụng'
              },
              {
                id:2,
                name:'Phát triển mới'
              }
            ],
            kieu_sudung_selected:1,
            danhsach_chuquan:[],
            chuquan_selected:null,
            sl_tv:'0',
            loaihinh_tb:[],
            loaihinh_tb_filter:[],
            loaihinh_tb_selected:null,
            dv_hodan_selected:null,
            dvhd_id:0

        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='nhapmoi'){
              this.setActiveActions(1)
            }else if(action.id=='ghilai'){
              this.ghiDuLieu()
            }else if(action.id=='huy'){
              this.setActiveActions(0)
            }else if(action.id=='xoa'){
              if(this.dv_hodan_selected==null){
                  this.$alert('Hãy chọn dịch vụ hộ dân để xoá!', '', {
                      dangerouslyUseHTMLString: true,
                      confirmButtonText: 'OK',
                      type: 'error'
                  })
                  return
              }
              let nguoi_cn=this.$root.token.getUserName()
              let data={
                vdvhd_id: this.dv_hodan_selected.dvhd_id,
                vnguoi_cn: nguoi_cn,
                vmay_cn: "localhost",
                vip_cn: "127.0.0.1"    
              }
              this.$confirm('Bạn thật sự muốn xóa dữ liệu không ?','Thông báo',{
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
              }).then(() => {
                    this.xoa_dichvu_hodan(data)
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
                if(this.danhsach_dichvu.length>0){
                  this.dichvu_selected=this.danhsach_dichvu[0].DICHVUVT_ID
                  this.changeDichVu()
                }
                if(this.danhsach_chuquan.length>0){
                  this.chuquan_selected=this.danhsach_chuquan[0].CHUQUAN_ID
                }
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
            this.dichvu_selected=null
            this.kieu_sudung_selected=1
            this.chuquan_selected=null
            this.sl_tv='0'
            this.thoihan_ck = moment(new Date()).format('DD/MM/YYYY')
            this.loaihinh_tb_selected=null
            this.dv_hodan_selected=null
        },
        showModal() {
            this.$refs["popupCapNhatDichVuHoDan"].show()
        },
        hideModal() {
            this.$refs["popupCapNhatDichVuHoDan"].hide()
        },
        handleShowModal(){
            
            //init data show modal
            this.setActiveActions(-1)
            this.dataSources=[]
            this.danhsach_dichvu=[]
            this.dichvu_selected=null
            this.kieu_sudung_selected=1
            this.danhsach_chuquan=[]
            this.chuquan_selected=null
            this.sl_tv=''
            this.loaihinh_tb=[]
            this.loaihinh_tb_filter=[]
            this.loaihinh_tb_selected=null
            this.dv_hodan_selected=null
            this.dvhd_id=0

            setTimeout(()=>{
              this.initDuLieu()
            },500)
        },
        async initDuLieu(){
          await this.lay_danhsach_dichvu_loaihinh_tb()
          await this.lay_danhsach_chuquan()
          await this.hienthi_ds_hodan_dv()
        },
        async hienthi_ds_hodan_dv(){
          //this.dataSources=[]
          this.dv_hodan_selected=null
          this.dataSources=await this.lay_ds_hodan_dv(this.hodan_id)
          if(this.dataSources.length<=0){
            this.setActiveActions(1)
          }
        },
        gridDsChanged(data){
          if(this.dataSources.length<=0||data.length<=0){
            this.dv_hodan_selected=null
            this.dvhd_id=0
            return
          }
          this.onSelectedRow(data[0])
        },
        onSelectedRow(item){
            this.dv_hodan_selected=item
            this.dvhd_id=item.dvhd_id

            if(item.dichvuvt_id!=null&&item.dichvuvt_id.toString().trim()!=''){
              this.dichvu_selected=item.dichvuvt_id
              this.loaihinh_tb_filter=this.loaihinh_tb.filter(x=>x.DICHVUVT_ID==this.dichvu_selected)
              
            }else {
              this.dichvu_selected=null
              this.loaihinh_tb_filter=this.loaihinh_tb
            }

            if(item.loaitb_id!=null&&item.loaitb_id.toString().trim()!=''){
              this.loaihinh_tb_selected=item.loaitb_id
            }else{
              this.loaihinh_tb_selected=null
            }

            if(item.kieu!=null&&item.kieu.toString().trim()!=''){
              this.kieu_sudung_selected=item.kieu
            }

            if(item.thoihan_ck!=null&&item.thoihan_ck.toString().trim()!=''){
              this.thoihan_ck=item.thoihan_ck
            }else{
              this.thoihan_ck=''
            }

            if(item.chuquan_id!=null&&item.chuquan_id.toString().trim()!=''){
              this.chuquan_selected=item.chuquan_id
            }else{
              this.chuquan_selected=null
            }

            if(item.soluong_tv!=null&&item.soluong_tv.toString().trim()!=''){
              this.sl_tv=item.soluong_tv.toString()
            }else{
              this.sl_tv='0'
            }

            this.setActiveActions(3)
        },
        ghiDuLieu(){
          if(this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active==true){
            if(this.dv_hodan_selected==null||this.dataSources.length==0){
              this.$toast.error('Chưa chọn dịch vụ hộ dân để cập nhật!')
              return
            } 
          }
          if(this.dichvu_selected==null){
            this.$toast.error('Hãy chọn dịch vụ!')
            return
          } 
          if(this.loaihinh_tb_selected==null){
            this.$toast.error('Hãy chọn loại hình!')
            return
          }
          let sl_tv=this.sl_tv.trim()!=''?parseInt(this.sl_tv):0
          if(sl_tv>12){
            this.$toast.error('Số lượng Tivi không được vượt quá 12!')
            this.$refs.inputSL_TV.focus()
            return
          }
          if(sl_tv<0){
            this.$toast.error('Nhập sai số lượng Tivi!')
            this.$refs.inputSL_TV.focus()
            return
          }

          let kieu=this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active?0:1

          if(kieu==1){
            let dt=this.dataSources.filter(x=>x.dichvuvt_id==this.dichvu_selected&&x.loaitb_id==this.loaihinh_tb_selected)
            if(dt.length>0){
              this.$toast.error('Hộ dân đã có dịch vụ này, không thể thêm mới!')
              return
            }
          }
          
          let nguoi_cn=this.$root.token.getUserName()
          let data={
              vhodan_id: this.hodan_id,
              vdichvuvt_id: this.dichvu_selected!=null?this.dichvu_selected:-1,
              vloaitb_id: this.loaihinh_tb_selected!=null?this.loaihinh_tb_selected:-1,
              vkieu_sd: this.kieu_sudung_selected,
              vthoihan_ck: this.thoihan_ck,
              vchuquan_id: this.chuquan_selected!=null?this.chuquan_selected:-1,
              vsoluong_tv: this.sl_tv.trim()!=''?parseInt(this.sl_tv):0,
              vdvhd_id: kieu==1?0:this.dv_hodan_selected.dvhd_id,
              vnguoi_cn: nguoi_cn,
              vmay_cn: "localhost",
              vip_cn: "127.0.0.1"
          }
          this.capnhat_dichvu_hodan(data, kieu)
        },
        changeDichVu(){
            //filter loại hình thuê bao theo dịch vụ
            if(this.dichvu_selected!=null){
                this.loaihinh_tb_filter=this.loaihinh_tb.filter(x=>x.DICHVUVT_ID==this.dichvu_selected)
                if(this.loaihinh_tb_filter.length>0){
                    this.loaihinh_tb_selected=this.loaihinh_tb_filter[0].LOAITB_ID
                }else{
                    this.loaihinh_tb_selected=null
                }
            }else{
                this.loaihinh_tb_filter=[]
                this.loaihinh_tb_selected=null
            }
        },
        async lay_danhsach_dichvu_loaihinh_tb(){
            try{
              this.loading(true)
              //lấy ds dịch vụ
              let responseDV = await UpdateCustomerLocationAPI.lay_danhsach_dichvu(this.axios)
              if(responseDV && responseDV.data && responseDV.data.error_code && responseDV.data.error_code == 'BSS-00000000' && responseDV.data.data) {
                  this.danhsach_dichvu=responseDV.data.data.sort((a, b)=>a.DICHVUVT_ID-b.DICHVUVT_ID)
                  if(this.danhsach_dichvu.length>0){
                      this.dichvu_selected=this.danhsach_dichvu[0].DICHVUVT_ID
                  }
              }else{
                  this.danhsach_dichvu=[]
              }
              //lấy danh sách loại hình thuê bao
              let response = await UpdateCustomerLocationAPI.lay_danhsach_loaihinh_tb(this.axios)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                  this.loaihinh_tb=response.data.data.sort((a, b)=>a.LOAITB_ID-b.LOAITB_ID)
              }else{
                  //this.danhsach_dichvu=[]
                  this.loaihinh_tb=[]
              }
              //filter loại hình thuê bao theo dịch vụ
              if(this.dichvu_selected!=null){
                this.loaihinh_tb_filter=this.loaihinh_tb.filter(x=>x.DICHVUVT_ID==this.dichvu_selected)
                if(this.loaihinh_tb_filter.length>0){
                  this.loaihinh_tb_selected=this.loaihinh_tb_filter[0].LOAITB_ID
                }else{
                  this.loaihinh_tb_selected=null
                }
              }else{
                this.loaihinh_tb_filter=[]
                this.loaihinh_tb_selected=null
              }
              this.loading(false)
            }catch(e){
              this.loading(false)
              if(e.data&&e.data.message){
                  this.$toast.error(e.data.message)
              }else if(e.response&&e.response.data&&e.response.data.message){
                  this.$toast.error(e.response.data.message)
              }else{
                  this.$toast.error('Không load được danh sách dịch vụ hoặc loại hình tb')
              }
            } 
        },
        async lay_danhsach_chuquan(){
            try{
              this.loading(true)
              let response = await UpdateCustomerLocationAPI.lay_danhsach_chuquan(this.axios)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                  this.danhsach_chuquan=response.data.data
                  if(this.danhsach_chuquan.length>0){
                      this.chuquan_selected=this.danhsach_chuquan[0].CHUQUAN_ID
                  }
              }else{
                  this.danhsach_chuquan=[]
              }
            }catch(e){
              this.loading(false)
              if(e.data&&e.data.message){
                  this.$toast.error(e.data.message)
              }else if(e.response&&e.response.data&&e.response.data.message){
                  this.$toast.error(e.response.data.message)
              }else{
                  this.$toast.error('Không load được danh sách chủ quản')
              }
            } 
        },
        async lay_ds_hodan_dv(hoadan_id){
          try{
              this.loading(true)
              let response = await UpdateCustomerLocationAPI.lay_ds_hodan_dv(this.axios, hoadan_id)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                  return response.data.data.map(item=>Object.assign(item,{
                    thoihan_ck:(item.thoihan_ck!=null&&item.thoihan_ck.toString().trim()!='')?moment(item.thoihan_ck,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY'):null
                  }))
              }else{
                  return []
              }
          }catch(e){
            this.loading(false)
            return []
          }
        },
        async xoa_dichvu_hodan(data){
            try{
                this.loading(true)
                let response = await UpdateCustomerLocationAPI.xoa_dichvu_hodan(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.$toast.success('Xóa dữ liệu thành công!')
                    await this.hienthi_ds_hodan_dv()
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, xoá dữ liệu không thành công')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                  this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                  this.$toast.error(e.response.data.message)
                }else{
                  this.$toast.error('Đã xảy ra lỗi, xoá dữ liệu không thành công')
                }
            }
        },
        //1 thêm mới 2 cập nhật
        async capnhat_dichvu_hodan(data, kieu){
            try{
                this.loading(true)
                let response = await UpdateCustomerLocationAPI.capnhat_dichvu_hodan(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.$toast.success('Cập nhật thành công dịch vụ cho hộ dân')
                    await this.hienthi_ds_hodan_dv()
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Cập nhật dịch vụ lỗi')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                  this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                  this.$toast.error(e.response.data.message)
                }else{
                  this.$toast.error('Cập nhật dịch vụ lỗi')
                }
            }
        },   
    }
}
</script>
<style>
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
    white-space: normal !important;
    word-break: break-all !important;
}
</style>