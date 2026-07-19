<template>
  <b-modal
    ref="popupCancelReason"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
    no-close-on-esc
    no-close-on-backdrop
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Cập nhật lý do hủy
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <ActionTop :actions="actions"  @onActionClick="onActionClick"/>
      <div class="popup-body">
        <div class="row">
          <div class="col-sm-8 col-12">
            <div class="box-form">
              <div class="legend-title">Thông tin lý do hủy</div>
              <div class="info-row">
                <div class="key w100">Lý do hủy <k-required-marker/></div>
                <div class="value">
                  <input type="text" id="inputLyDoHuy" ref="inputLyDoHuy" :value="input_lydo_huy"  @change="e=>input_lydo_huy=e.target.value" @blur="outFocusInput('inputLyDoHuy')" class="form-control"/>
                </div>
              </div>
              <div class="info-row">
                <div class="key w100">Nội dung</div>
                <div class="value">
                  <input type="text" id="inputNoiDung" ref="inputNoiDung" @blur="outFocusInput('inputNoiDung')" :value="input_noidung"  @change="e=>input_noidung=e.target.value" class="form-control" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w100">Loại lý do <k-required-marker/></div>
                <div class="value">
                    <div class="select-custom" ref="ds_lydo">
                        <select2 :settings="{ dropdownParent: $refs['ds_lydo'] }" ref="cboLyDo"
                            v-model="loai_lydo_selected" class="select2"
                            :options="ds_loai_lydo.map(e=> ({id: e.loai_ld, text: e.ten_loai}))"
                          >
                        </select2>
                    </div>  
                </div>
                <div class="value w20 nowrap padt7">
                  <div class="check-action">
                    <input type="checkbox" v-model="checkTrangThai" class="check" />
                    <span class="name">Trạng thái</span>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Lý do hủy VDC</div>
                    <div class="value">
                      <input type="text" id="inputLyDoHuyVDC" ref="inputLyDoHuyVDC" @blur="outFocusInput('inputLyDoHuyVDC')" :value="input_lydohuy_vdc"  @change="e=>input_lydohuy_vdc=e.target.value" @keypress="isNumber($event)" class="form-control" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Lý do hủy VASC</div>
                    <div class="value">
                      <input type="text" id="inputLyDoHuyVASC" ref="inputLyDoHuyVASC" @blur="outFocusInput('inputLyDoHuyVASC')" :value="input_lydohuy_vasc"  @change="e=>input_lydohuy_vasc=e.target.value" @keypress="isNumber($event)" class="form-control" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="legend-title mart20">
                Thông tin lý do hủy - loại hình thuê bao
              </div>
              <div class="info-row">
                <div class="key w100">Dịch vụ <k-required-marker/></div>
                <div class="value">
                    <div class="select-custom" ref="ds_dichvu">
                        <select2 :settings="{ dropdownParent: $refs['ds_dichvu'] }" ref="cboDichVu"
                            v-model="dichvu_selected" class="select2"
                            :options="ds_dichvu.map(e=> ({id: e.DICHVUVT_ID, text: e.TEN_DVVT}))"
                            @change="changeDichVu"
                          >
                        </select2>
                    </div>  
                </div>
              </div>
              <div class="box-move-select-table mart20">
                <div class="box-col">
                  <div class="legend-title">Loại hình đã gán</div>
                  <KDataGrid
                      :columns="columnsLoaiHinh"
                      :dataSource="dataSourcesLoaiHinhGan"
                      :showColumnCheckbox="true"
                      :enable-paging-server="false"
                      :allowPaging="true"
                      :showFilter="true"
                      ref="gridDaGan"
                      :enabledSelectFirstRow="false"
                      panelDataHeight="240px"/>
                </div>
                <div class="actions">
                  <button class="btn" @click="boGan">
                    <span class="-ap icon-chevron-thin-right"></span>
                  </button>
                  <button class="btn" @click="boGanAll">
                      <span class="fa fa-angle-double-right"></span>
                  </button>
                  <button class="btn"  @click="gan">
                    <span class="-ap icon-chevron-thin-left"></span>
                  </button>
                  <button class="btn" @click="ganAll">
                      <span class="fa fa-angle-double-left"></span>
                  </button>
                </div>
                <div class="box-col">
                  <div class="legend-title">Loại hình chưa gán</div>
                  <KDataGrid
                    :columns="columnsLoaiHinh"
                    :dataSource="dataSourcesLoaiHinhChuaGan"
                    :showColumnCheckbox="true"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    ref="gridChuaGan"
                    :enabledSelectFirstRow="false"
                    panelDataHeight="240px"/>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="box-form">
              <div class="legend-title">Danh sách lý do hủy</div>
              <KDataGrid
                  :columns="columns"
                  :dataSource="dataSources"
                  :showColumnCheckbox="false"
                  :enable-paging-server="false"
                  :allowPaging="true"
                  :showFilter="true"
                  :enabledSelectFirstRow="true"
                  panelDataHeight="560px"
                  @onRowSelected="onRowSelected"
                  @selectedItemsChanged="gridDsChanged"/>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import API from './API'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'

export default {
    name:'CancelReasonModal',
    components:{
        ActionTop,
        KDataGrid,
        KRequiredMarker
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
            columnsLoaiHinh:[
                {
                    fieldName: "loaihinh_tb",
                    headerText: "Loại hình",
                    allowFiltering: true,
                    allowSorting:true
                }
            ],
            columns:[
                {
                    fieldName: "stt",
                    headerText: "STT",
                    allowFiltering: true,
                    allowSorting:true,
                    width:60
                },
                {
                    fieldName: "lydohuy",
                    headerText: "Lý do hủy",
                    allowFiltering: true,
                    allowSorting:true
                }
            ],
            dataSources:[],
            dataSourcesLoaiHinhGan:[],
            dataSourcesLoaiHinhChuaGan:[],
            input_lydo_huy:'',
            input_noidung:'',
            ds_loai_lydo:[],
            loai_lydo_selected:null,
            checkTrangThai:false,
            input_lydohuy_vdc:'',
            input_lydohuy_vasc:'',
            ds_dichvu:[],
            dichvu_selected:null,
            rowSelected:null,
            lydohuy_id:0,
            dataCheckGan:[],
            dataCheckChuaGan:[]
        }
    },
    methods:{
        showModal() {
            this.$refs.popupCancelReason.show()
        },
        hideModal() {
            this.$refs.popupCancelReason.hide()
        },
        handleShowModal(){
            this.dataSources=[]
            this.dataSourcesLoaiHinhGan=[]
            this.dataSourcesLoaiHinhChuaGan=[]
            this.input_lydo_huy=''
            this.input_noidung=''
            this.ds_loai_lydo=[]
            this.loai_lydo_selected=null
            this.checkTrangThai=false
            this.input_lydohuy_vdc=''
            this.input_lydohuy_vasc=''
            this.ds_dichvu=[]
            this.dichvu_selected=null
            this.rowSelected=null
            this.lydohuy_id=0
            this.dataCheckGan=[]
            this.dataCheckChuaGan=[]
            

            this.setActiveActions(-1)
            setTimeout(()=>{
                this.initDuLieu()
            }, 500)
        },
        onActionClick(action){
            if(action.id=='nhapmoi'){
              this.setActiveActions(1)
              this.hienthi_loaihinh(0)
            }else if(action.id=='ghilai'){
              this.ghiDuLieu()
            }else if(action.id=='huy'){
              if(this.rowSelected!=null){
                this.onSelectedRow(this.rowSelected)
              }else{
                this.setActiveActions(2)
              }
              
            }else if(action.id=='xoa'){
              this.$confirm('Bạn thật sự muốn xóa dữ liệu không ?','Thông báo',{
                  confirmButtonText: 'Yes',
                  cancelButtonText: 'No'
              }).then(() => {
                  this.xoa()
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
                this.$refs.inputLyDoHuy.focus()
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
          this.input_lydo_huy=''
          this.input_noidung=''
          this.input_lydohuy_vdc=''
          this.input_lydohuy_vasc=''
          this.checkTrangThai=false
          if(this.ds_loai_lydo.length>0){
            this.loai_lydo_selected=this.ds_loai_lydo[0].loai_ld
          }
        },
        outFocusInput(inputId){
            let element=document.getElementById(inputId)
            if(element){
                element.classList.remove('input_batbuoc')
            }
        },
        async initDuLieu(){
          this.ds_loai_lydo=await this.lay_ds_loai_lydo_huy()
          if(this.ds_loai_lydo.length>0){
            this.loai_lydo_selected=this.ds_loai_lydo[0].loai_ld
          }else{
            this.$toast.error('Không có thông tin về loại lý do!')
            return
          }

          this.ds_dichvu=await this.lay_ds_dichvu()
          if(this.ds_dichvu.length>0){
            //cố định
            this.dichvu_selected=1
          }
          await this.hienthi_lydohuy()
        },
        async hienthi_lydohuy(){
          this.dataSources=[]
          this.dataSources=await this.lay_ds_lydohuy()
          if(this.dataSources.length>0){
            if(this.lydohuy_id!=0){
              const index=this.dataSources.findIndex(x=>x.lydohuy_id==this.lydohuy_id)
              if(index>-1){
                this.onSelectedRow(this.dataSources[index])
              }else{
                this.onSelectedRow(this.dataSources[0])
              }
            }else{
              this.onSelectedRow(this.dataSources[0])
            }
          }else{
            this.dataSources=[]
            this.setActiveActions(1)
            await this.hienthi_loaihinh(0)
          }
        },
        async hienthi_loaihinh(lydohuy_id){
          //loại hình gán
          this.dataSourcesLoaiHinhGan=[]
          this.dataSourcesLoaiHinhGan=await this.lay_ds_lydohuy_lhtb_dagan(lydohuy_id)
          //loại hình thuê bao chưa gán
          this.dataSourcesLoaiHinhChuaGan=[]
          this.dataSourcesLoaiHinhChuaGan=await this.lay_ds_lydohuy_lhtb_chuagan(lydohuy_id, this.dichvu_selected!=null?this.dichvu_selected:0)
        },
        dataCheckedLoaiHinhGan(data){
          this.dataCheckGan=data
        },
        dataCheckedLoaiHinhChuaGan(data){
          this.dataCheckChuaGan=data
        },
        gridDsChanged(data){
          // if(this.dataSources.length<=0||data.length<=0){
          //   return
          // }
          // this.onSelectedRow(data[0])
        },
        onRowSelected(item){
          this.onSelectedRow(item)
        },
        async onSelectedRow(item){
          this.rowSelected=item
          this.lydohuy_id=item.lydohuy_id!=null?item.lydohuy_id:0
          this.input_lydo_huy=item.lydohuy!=null?item.lydohuy:''
          this.input_noidung=item.noidung!=null?item.noidung:''
          this.loai_lydo_selected=item.loai_ld
          this.input_lydohuy_vdc=item.lydohuy_id_vdc!=null?item.lydohuy_id_vdc.toString():''
          this.input_lydohuy_vasc=item.lydohuy_id_vasc!=null?item.lydohuy_id_vasc.toString():''
          if(item.status!=null&&item.status==1){
            this.checkTrangThai=true
          }else{
            this.checkTrangThai=false
          }
          await this.hienthi_loaihinh(this.lydohuy_id)
          this.setActiveActions(3)
        },
        boGan(){
          let dataCheckedsLHGan=this.$refs.gridDaGan.getSelectedRecords()
          if(dataCheckedsLHGan.length==0){
              return
          }
          for(let i=0;i<dataCheckedsLHGan.length;i++){
              this.dataSourcesLoaiHinhChuaGan.push(dataCheckedsLHGan[i])
              const index=this.dataSourcesLoaiHinhGan.findIndex(x=>x.loaitb_id==dataCheckedsLHGan[i].loaitb_id)
              if(index>-1){
                  this.dataSourcesLoaiHinhGan.splice(index,1)
              }
          }
          this.dataSourcesLoaiHinhGan=[].concat(this.dataSourcesLoaiHinhGan)
          this.dataSourcesLoaiHinhChuaGan=[].concat(this.dataSourcesLoaiHinhChuaGan)
        },
        boGanAll(){
          if(this.dataSourcesLoaiHinhGan.length==0){
              return
          }
          this.dataSourcesLoaiHinhChuaGan=this.dataSourcesLoaiHinhChuaGan.concat(this.dataSourcesLoaiHinhGan)
          this.dataSourcesLoaiHinhGan=[]

        },
        gan(){
          let dataCheckedsLHChuaGan=this.$refs.gridChuaGan.getSelectedRecords()
          if(dataCheckedsLHChuaGan.length==0){
              return
          }
          for(let i=0;i<dataCheckedsLHChuaGan.length;i++){
              this.dataSourcesLoaiHinhGan.push(dataCheckedsLHChuaGan[i])
              const index=this.dataSourcesLoaiHinhChuaGan.findIndex(x=>x.loaitb_id==dataCheckedsLHChuaGan[i].loaitb_id)
              if(index>-1){
                  this.dataSourcesLoaiHinhChuaGan.splice(index,1)
              }
          }
          this.dataSourcesLoaiHinhGan=[].concat(this.dataSourcesLoaiHinhGan)
          this.dataSourcesLoaiHinhChuaGan=[].concat(this.dataSourcesLoaiHinhChuaGan)
        },
        ganAll(){
          if(this.dataSourcesLoaiHinhChuaGan.length==0){
              return
          }
          this.dataSourcesLoaiHinhGan=this.dataSourcesLoaiHinhGan.concat(this.dataSourcesLoaiHinhChuaGan)
          this.dataSourcesLoaiHinhChuaGan=[]
        },
        async xoa(){
          if(this.lydohuy_id==0){
            return
          }
          let result = await this.xoa_lydohuy(this.lydohuy_id)
          if(result=='ok'){
            this.$toast.success('Xóa lý do hủy thành công !')
            this.rowSelected=null
            await this.hienthi_lydohuy()
          }else{
            this.$toast.error(result)
          }

        },
        async ghiDuLieu(){
          //kiểm tra dữ liệu
          if(this.input_lydo_huy.trim()==''){
            this.$toast.error('Bạn chưa nhập Lý do hủy !')
            //this.$refs.inputLyDoHuy.$el.classList.add('input_batbuoc')
            document.getElementById('inputLyDoHuy').classList.add('input_batbuoc')
            this.$refs.inputLyDoHuy.focus()
            return
          }
          console.log('input_lydo_huy size', this.input_lydo_huy.trim().length)
          if(this.input_lydo_huy.trim().length>200){
            this.$toast.error('Lý do huỷ không được vượt quá 200 ký tự!')
            document.getElementById('inputLyDoHuy').classList.add('input_batbuoc')
            this.$refs.inputLyDoHuy.focus()
            return
          }
          if(this.input_lydohuy_vdc.trim().length>3){
            this.$toast.error('Lý do huỷ VDC không được vượt quá 3 ký tự!')
            document.getElementById('inputLyDoHuyVDC').classList.add('input_batbuoc')
            this.$refs.inputLyDoHuyVDC.focus()
            return
          }
          if(this.input_lydohuy_vasc.trim().length>3){
            this.$toast.error('Lý do huỷ VASC không được vượt quá 3 ký tự!')
            document.getElementById('inputLyDoHuyVASC').classList.add('input_batbuoc')
            this.$refs.inputLyDoHuyVASC.focus()
            return
          }
          if(this.input_noidung.trim().length>200){
            this.$toast.error('Nội dung không được vượt quá 200 ký tự!')
            document.getElementById('inputNoiDung').classList.add('input_batbuoc')
            this.$refs.inputNoiDung.focus()
            return
          }
          

          //
          let is_insert=this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active?0:1
          //https://cntt.vnpt.vn/browse/BSS-75265
          //Kiểm tra trùng lý thêm lý do huỷ
          if(is_insert==1){
            let lydohuy=this.input_lydo_huy.toLowerCase().trim();
            const index=this.dataSources.findIndex(x=>x.lydohuy&&x.lydohuy.toLowerCase()==lydohuy)
            if(index>-1){
              this.$toast.error('Lý do huỷ đã tồn tại, vui lòng nhập lại lý do huỷ!')
              document.getElementById('inputLyDoHuy').classList.add('input_batbuoc')
              this.$refs.inputLyDoHuy.focus()
              return
            }
          }

          let lydohuy_lhtb=this.dataSourcesLoaiHinhGan.map(x=>{
            return {LOAITB_ID:x.loaitb_id}
          })
          let lydohuy=[
            {
              LYDOHUY:this.input_lydo_huy.trim(),
              NOIDUNG:this.input_noidung.trim(),
              STATUS:this.checkTrangThai?1:0,
              LOAI_LD:this.loai_lydo_selected!=null?this.loai_lydo_selected:0,
              LYDOHUY_ID_VDC:this.input_lydohuy_vdc.trim()==''?0:Number(this.input_lydohuy_vdc.trim()),
              LYDOHUY_ID_VASC:this.input_lydohuy_vasc.trim()==''?0:Number(this.input_lydohuy_vasc.trim())
            }
          ]
          let data={
            p_is_insert: is_insert,
            p_lydohuy_id: this.lydohuy_id,
            p_json_lydohuy: lydohuy,
            p_json_lydohuy_lhtb: lydohuy_lhtb
          }
          let result = await this.fn_insert_update_lydohuy(data)
          if(result=='ok'){
            this.$toast.success(is_insert==1?'Thêm mới lý do hủy thành công':'Cập nhật lý do hủy thành công !')
            if(is_insert==1){
              this.lydohuy_id=0
              await this.hienthi_lydohuy()
            }else{
              const index=this.dataSources.findIndex(x=>x.lydohuy_id==this.lydohuy_id)
              if(index>-1){
                this.dataSources[index].lydohuy_id=this.lydohuy_id
                this.dataSources[index].lydohuy=this.input_lydo_huy.trim()
                this.dataSources[index].noidung=this.input_noidung.trim()
                this.dataSources[index].loai_ld=this.loai_lydo_selected
                this.dataSources[index].lydohuy_id_vdc=this.input_lydohuy_vdc.trim()
                this.dataSources[index].lydohuy_id_vasc=this.input_lydohuy_vasc.trim()
                this.dataSources[index].status=this.checkTrangThai?1:0
                this.onSelectedRow(this.dataSources[index])
              }else{
                await this.hienthi_lydohuy()
              }
            }
          }else{
            this.$toast.error(result)
          }
        },
        changeDichVu(data){
          //this.dichvu_selected=data.DICHVUVT_ID
          this.hienthi_loaihinh(this.lydohuy_id)
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
        async lay_ds_loai_lydo_huy(){
            try{
                this.loading(true)
                let response=await API.lay_ds_loai_lydo_huy(this.axios)
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
        async lay_ds_dichvu(){
            try{
                this.loading(true)
                let response=await API.lay_ds_dichvu(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data.sort((a, b)=>a.DICHVUVT_ID-b.DICHVUVT_ID)
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_ds_lydohuy(){
            try{
                this.loading(true)
                let response=await API.lay_ds_lydohuy(this.axios)
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
        async lay_ds_lydohuy_lhtb_dagan(lydohuy_id){
            try{
                this.loading(true)
                let response=await API.lay_ds_lydohuy_lhtb_dagan(this.axios, lydohuy_id)
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
        async lay_ds_lydohuy_lhtb_chuagan(lydohuy_id, dichvuvt_id){
            try{
                this.loading(true)
                let response=await API.lay_ds_lydohuy_lhtb_chuagan(this.axios, lydohuy_id, dichvuvt_id)
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
        async xoa_lydohuy(lydohuy_id){
            try{
                this.loading(true)
                let response=await API.xoa_lydohuy(this.axios, lydohuy_id)
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
        async fn_insert_update_lydohuy(data){
            try{
                this.loading(true)
                let response=await API.fn_insert_update_lydohuy(this.axios, data)
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

    }
}
</script>
<style>
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
.input_batbuoc:focus {
    border-color:red;
    background-color: lightcoral;
    box-shadow: none;
}
</style>