<template>
<ejs-dialog
    :allowDragging="true"
    :animationSettings="animationSettings"
    :enableResize="true"
    :position="{X:'center', Y:'top'}"
    :visible="false"
    ref="popupDongBoVatTu"
    :showCloseIcon="true"
    width="80%"
    :isModal='true'
    :target="target"
    :header="header"
    :open="handleShowModal">
  <!-- <b-modal
    ref="popupDongBoVatTu"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  > -->
    <div class="modal-content popup-box">
      <!-- <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Đồng bộ vật tư
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div> -->
      <ActionTop :actions="actions"  @onActionClick="onActionClick"/>
      <div class="popup-body">
        <div class="grid-stack-box">
          <div class="box-col box-form" style="width: calc(30% - 8px);">
            <div class="legend-title">Thông tin tìm kiếm</div>
            <div class="info-row">
              <div class="key w80">Mã vật tư</div>
              <div class="value">
                <input type="text" v-model="input_mavt" @keyup.enter="onEnterMaVT" ref="inputMaVT" class="form-control highlight" />
              </div>
            </div>
          </div>
          <div class="gutter gutter-horizontal" style="width: 16px;"></div>
          <div class="box-col box-form" style="width: calc(70% - 8px);">
            <div class="legend-title">Gán nhanh loại thiết bị, đơn vị tính</div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                    <div class="key w80">Loại thiết bị</div>
                    <div class="value">
                      <multiselect
                        :options="ds_loaitbi"
                        placeholder="Chọn loại thiết bị"
                        @input="changeLoaiTB"
                        :showLabels="false"
                        :allowEmpty="false"
                        :value="loaitbi_o_selected"
                        label="LOAI_TBI"
                        track-by="LOAITBI_ID">
                        <template #noOptions>
                            Không tìm thấy thông tin
                        </template>
                        <template #noResult>
                            Không tìm thấy thông tin
                        </template>
                    </multiselect>
                    </div>
                    <div class="value w30 nowrap" style="padding-left: 0px!important;padding-top: 1px !important;">
                      <button class="btn" style="border: 1px solid #ced4da !important;" @click="cboGan_Loaitb_ButtonClick">
                        <span class="nc-icon-outline arrows-1_small-triangle-down text-desc"></span>
                      </button>
                    </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                    <div class="key w80">Đơn vị tính</div>
                    <div class="value">
                      <multiselect
                        :options="ds_donvi_tinh"
                        placeholder="Chọn đơn vị tính"
                        @input="changeDVTinh"
                        :showLabels="false"
                        :allowEmpty="false"
                        :value="loai_donvi_tinh_o_selected"
                        :disabled="true"
                        label="DVI_TINH"
                        track-by="DVITINH_ID">
                        <template #noOptions>
                            Không tìm thấy thông tin
                        </template>
                        <template #noResult>
                            Không tìm thấy thông tin
                        </template>
                      </multiselect>

                      <!-- <div class="select-custom" ref="ds_donvitinh">
                          <select2 :settings="{ dropdownParent: $refs['ds_donvitinh'] }"
                              v-model="loai_donvi_tinh_selected" class="select2"
                              :options="ds_donvi_tinh.map(e=> ({id: e.DVITINH_ID, text: e.DVI_TINH}))">
                          </select2>
                      </div> -->
                    </div>
                    <div class="value w30 nowrap" style="padding-left: 0px!important;padding-top: 1px !important;">
                      <button class="btn" style="border: 1px solid #ced4da !important;" @click="cboGan_DVT_ButtonClick">
                        <span class="nc-icon-outline arrows-1_small-triangle-down text-desc"></span>
                      </button>
                    </div>
                </div>
               
              </div>

            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách vật tư</div>
          <div class="info-row">
            <div class="key w130">Tìm kiếm mã vật tư</div>
            <div class="input-icon-right">
              <input type="text" v-model="input_timkiem_mavt" placeholder="Gõ tên vật tư để tìm kiếm" class="form-control" />
              <span class="icon nc-icon-outline ui-2_filter"></span>
            </div>
          </div>
          <KDataGrid
              ref="gridVT"
              :columns="columns"
              :dataSource="dataSources"
              :showColumnCheckbox="true"
              :enablePagingServer="true"
              :pageIndex="pageIndex"
              :pageSize="pageSize"
              :totalRecords="totalRecords"
              @pageChanged="pageChanged"
              :selectionSettings="{checkboxMode: 'ResetOnRowClick', checkboxOnly: true}"
              :allowPaging="true"
              :showFilter="false"
              @selectedItemsChanged="dataCheckedsVatTu"
              :enabledSelectFirstRow="false"/>
        </div>
      </div>
       <!--  -->
       <HuongDanModal ref="huongDanModal"/>
    </div>
  </ejs-dialog>
  <!-- </b-modal> -->
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import { Page, Filter, Sort} from "@syncfusion/ej2-vue-treegrid"
import { L10n } from '@syncfusion/ej2-base'
import SuppliesAPI from './SuppliesAPI'
import Vue from "vue"
import { TreeGridPlugin } from "@syncfusion/ej2-vue-treegrid"
Vue.use(TreeGridPlugin)
L10n.load({
  'vi-VN': {
    treegrid: {
      EmptyRecord: 'Không có bản ghi nào để hiển thị'
    }
  }
})
import HuongDanModal from './HuongDanModal.vue'
export default {
    name:'DongBoVatTuModal',
    components:{
      ActionTop,
      HuongDanModal,
      KDataGrid
    },
    props:{
      tentat_ccbs:{
        type:String,
        default:''
      },
      tag:{
        type:String,
        default:'0'
      }
    },
    provide: {
      treegrid: [ Page,Filter, Sort ]
    },
    data(){
        return {
          actions:[
              {
                  id:'timkiem',
                  name:'Tìm kiếm',
                  active: true,
                  icon_class:'one-search'
              },
              {
                  id:'dongbo_vt',
                  name:'Đồng bộ VT',
                  active: true,
                  icon_class:'one-sync1'
              },
              {
                  id:'huongdan',
                  name:'Hướng dẫn',
                  active: true,
                  icon_class:'one-book1'
              }
              // {
              //     id:'thoat',
              //     name:'Thoát',
              //     active: true,
              //     icon_class:'ui-1_circle-remove nc-icon-glyph'
              // }
          ],
          input_mavt:'',
          ds_loaitbi:[],
          ds_donvi_tinh:[],
          loaitbi_selected:null,
          loai_donvi_tinh_selected:null,
          input_timkiem_mavt:'',
          columns:[
              {
                  fieldName: "code",
                  headerText: "Mã TV",
                  allowFiltering: true,
                  allowSorting:true
              },
              {
                  fieldName: "name",
                  headerText: "Tên VT",
                  allowFiltering: true,
                  allowSorting:true
              },
              // {
              //     fieldName: "LOAITBI",
              //     headerText: "Loại TB",
              //     allowFiltering: true,
              //     allowSorting:true
              // },
              {
                  fieldName: "LOAITBI",
                  headerText: "Loại TB",
                  allowFiltering: true,
                  allowSorting:true,
                  template: this.columnTemplateLoaiTB(this)
              },
              {
                  fieldName: "DVI_TINH",
                  headerText: "ĐVT",
                  allowFiltering: true,
                  allowSorting:true,
                  //template: this.columnTemplateLoaiTB(this)
              },
              {
                  fieldName: "parent_code",
                  headerText: "Mã VT Cha",
                  allowFiltering: true,
                  allowSorting:true
              },
              {
                  fieldName: "is_serial",
                  headerText: "Có serial",
                  allowFiltering: true,
                  allowSorting:true
              },
              {
                  fieldName: "part_number",
                  headerText: "Part Number",
                  allowFiltering: true,
                  allowSorting:true
              },
              {
                  fieldName: "IS_SYNC",
                  headerText: "Trạng thái",
                  allowFiltering: true,
                  allowSorting:true,
                  customAttributes: { class: 'green' }
              },
              {
                  fieldName: "code_name",
                  headerText: "Tên tắt",
                  allowFiltering: true,
                  allowSorting:true
              }
              
          ],
          dataSources:[],
          dataSourcesBak:[],
          pageIndex:0,
          pageSize:10,
          totalRecords:0,
          dataCheckeds:[],
          isEnterMaVT:false,
          filterBarTemplate : {
            create: function (args) {

              let div = document.createElement('div')
              div.classList.add('input-icon-right')

              let input = document.createElement('input')
              input.classList.add('form-control')
              input.id = args.column.foreignKeyField

              let span = document.createElement('span')
              span.classList.add('icon', 'nc-icon-outline', 'ui-1_zoom')

              div.append(input)
              div.append(span)

              return div
            },
            write: function (args) {

              args.element.querySelector('input').addEventListener('input', (args) => {

                let value = args.currentTarget.value

                if (!value) {
                  this.parent.removeFilteredColsByField(args.currentTarget.id);
                } else {
                  this.parent.filterByColumn(args.currentTarget.id, 'contains', value)
                }
              })
            }
          },
          ma_vt_old:'',
          target: '.main-wrapper',
          animationSettings: { effect: 'Zoom' },
          header:`<span class="icon one-notepad"></span> Đồng bộ vật tư`
        }
    },
    computed:{
      loai_donvi_tinh_o_selected(){
        const dvt=this.ds_donvi_tinh.find(x=>x.DVITINH_ID==this.loai_donvi_tinh_selected)
        return dvt!=undefined?dvt:null
      },
      loaitbi_o_selected(){
        const loaitb=this.ds_loaitbi.find(x=>x.LOAITBI_ID==this.loaitbi_selected)
        return loaitb!=undefined?loaitb:null
      }
    },
    methods:{
      onActionClick(action){
        if(action.id=='timkiem'){
          this.TimKiem(this.input_mavt.trim())
        }else if(action.id=='dongbo_vt'){
          if(this.tag=='-1'){
            this.$toast.error('Bạn không có quyền thực hiện chức năng!')
            return
          }
          this.DongBo()
        }else if(action.id=='thoat'){
          this.hideModal()
        }else if(action.id=='huongdan'){
          this.$refs.huongDanModal.showModal()
        }
      },
      showModal() {
          this.$refs["popupDongBoVatTu"].show()
      },
      hideModal() {
          this.$refs["popupDongBoVatTu"].hide()
      },
      changeDVTinh(data){
        this.loai_donvi_tinh_selected=data.DVITINH_ID
      },
      changeLoaiTB(data){
        this.loaitbi_selected=data.LOAITBI_ID
      },
      async TimKiem(ma_vt){
        if(ma_vt==''){
          this.$toast.error('Bạn chưa nhập mã vật tư !')
          this.$refs.inputMaVT.focus()
          return
        }
        this.isEnterMaVT=true
        this.dataSources=[]
        if(ma_vt!=this.ma_vt_old){
          this.pageIndex=0
          this.ma_vt_old=ma_vt
        }
        //this.pageIndex=0
        this.totalRecords=0
        await this.LoadGrid()
      },
      onEnterMaVT(){
        this.TimKiem(this.input_mavt.trim())
      },
      // onEnterTK(){
      //   if(this.input_timkiem_mavt.trim()==''){

      //     return
      //   }
      //   let dsFilter=this.dataSources.filter(x=>x.code)
      // },
      txtLocVatTuEditValueChanged(){
        if(this.input_timkiem_mavt.trim()!=''){
          let temp=this.dataSourcesBak.filter(x=>x.code.includes(this.input_timkiem_mavt.trim()))
          this.dataSources=temp
        }else{
          this.dataSources=this.dataSourcesBak
        }
      },
      async DongBo(){
        //
        console.log('dataCheckeds',this.dataCheckeds)
        if(this.dataCheckeds.length==0){
          this.$toast.error('Bạn chưa tích chọn dòng vật tư nào để tiến hành đồng bộ!')
          return
        }
        for(let i=0;i<this.dataCheckeds.length;i++){
          if(!this.dataCheckeds[i].is_serial){
            if(this.dataCheckeds[i].LOAITBI_ID==2){
              this.$toast.error('Vật tư '+ this.dataCheckeds[i].code+'không thuộc thiết bị có serial ! Hãy chọn loại thiết bị khác !')
              return
            }
          }else{
            if(this.dataCheckeds[i].LOAITBI_ID!=2){
              this.$toast.error('Vật tư '+this.dataCheckeds[i].code+' thuộc thiết bị có serial ! Hãy chọn loại thiết bị serial !')
              return
            }
          }
          const index=this.dataSources.findIndex(x=>x.code==this.dataCheckeds[i].code)
          if(index>-1){
            Object.assign(this.dataCheckeds[i], this.dataSources[index])
          }
        }
        var ds_ma_vt=[]
        for(let i=0;i<this.dataCheckeds.length;i++){
          if(this.dataCheckeds[i].IS_SYNC=='Đã đồng bộ'){
            continue
          }
          let item=this.dataCheckeds[i]
          var ma_nhom=''
          if(this.tentat_ccbs=='NET'){
            ma_nhom=item.code?item.code:''
          }else{
            if(item.ma_nhom&&item.ma_nhom.toString()!=''){
              ma_nhom=item.ma_nhom
            }else{
              ma_nhom=item.code?item.code:''
            }
          }
          // if (tt_nd.tentat == "NET")
          //               rowdata.MA_NHOM = CommonFunction.StringToString(ln[i].GetValue("code").ToString());
          //           else
          //           {
          //               if (ln[i].GetValue("MA_NHOM").ToString() != "")
          //                   rowdata.MA_NHOM = ln[i].GetValue("MA_NHOM").ToString();
          //               else
          //                   rowdata.MA_NHOM = cha;
          //           }
          ds_ma_vt.push({
            VATTU_ID:null,
            MA_VT:item.code?item.code:'',
            TENTAT:item.code_name?item.code_name:item.name?item.name:'',
            TEN_VT:item.name?item.name:'',
            DVITINH_ID:item.DVITINH_ID?item.DVITINH_ID:0,
            PARENT_CODE:item.parent_code?item.parent_code:'',
            MA_NHOM:ma_nhom,
            LOAITBI_ID:item.LOAITBI_ID?item.LOAITBI_ID:0,
            MUC:item.category_level,
            PART_NUMBER:item.part_number?item.part_number:null,
            GHICHU:'Đồng bộ vật tư FMIS'
          })
        }
        if(ds_ma_vt.length==0){
          this.$toast.error('Không có thông tin trả về khi thực hiện đồng bộ vật tư')
          return
        }
        let ds_para={
          NGUOI_CN:this.$root.token.getUserName(),
          MAY_CN:"localhost",
          IP_CN:"127.0.0.1"
        }
        let result=await this.dongbo_vattu(JSON.stringify(ds_ma_vt),JSON.stringify(ds_para))
        if(result.code==1){
          this.$toast.success(result.message)
          this.TimKiem(this.input_mavt.trim())
          this.$emit('successSync')
        }else{
          this.$toast.error(result.message)
        }
      },
      cboGan_DVT_ButtonClick(){
        this.Gan_Nhanh("DVT")
      },
      cboGan_Loaitb_ButtonClick(){
        this.Gan_Nhanh("LOAITBI")
      },
      Gan_Nhanh(loai){
        if(this.dataSources.length==0){
          this.$toast.error('Không có dữ liệu trên lưới !')
          return
        }
        //
        if(this.dataCheckeds.length==0){
          this.$toast.error('Bạn chưa tích chọn dòng vật tư nào !')
          return
        }
        if(this.loaitbi_selected==null&&loai=='LOAITBI'){
          this.$toast.error('Chưa chọn loại thiết bị !')
          return
        }
        if(this.loai_donvi_tinh_selected==null&&loai=='DVT'){
          this.$toast.error('Chưa chọn đơn vị tính !')
          return
        }
        var dataTemp=[].concat(this.dataSources)
        
        for(let i=0;i<this.dataCheckeds.length;i++){
          const index=dataTemp.findIndex(x=>x.code==this.dataCheckeds[i].code)
          if(index>-1){
            if(loai=='LOAITBI'){
              if(!this.dataCheckeds[i].is_serial){
                if(this.loaitbi_selected==2){
                  this.$toast.error('Vật tư '+this.dataCheckeds[i].code+' không thuộc thiết bị có serial! Hãy chọn loại thiết bị khác!')
                  return
                }else{
                  dataTemp[index].LOAITBI_ID=this.loaitbi_selected
                  let loaitbi=this.ds_loaitbi.find(x=>x.LOAITBI_ID==this.loaitbi_selected)
                  dataTemp[index].LOAITBI=loaitbi?loaitbi.LOAI_TBI:''

                }
              }else{
                continue;
              }
            }
            if(loai=='DVT'){
              console.log('DVTDVTDVTDVTDVT')
              dataTemp[index].DVITINH_ID=this.loai_donvi_tinh_selected
              let dvtinh=this.ds_donvi_tinh.find(x=>x.DVITINH_ID==this.loai_donvi_tinh_selected)
              dataTemp[index].DVI_TINH=dvtinh?dvtinh.DVI_TINH:''
            }
          }

        } 
        this.dataCheckeds=[]
        this.dataSources=dataTemp
      },
      dataCheckedsVatTu(data){
        this.dataCheckeds=data

      },
      async lay_danhsach_donvitinh(){
          try{
              this.loading(true)
              let response = await SuppliesAPI.lay_danhsach_donvitinh(this.axios)
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
      async lay_danhsach_loaithietbi(){
          try{
              this.loading(true)
              let response = await SuppliesAPI.lay_danhsach_loaithietbi(this.axios)
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
      async get_asset_supplies_category_info(data){
          try{
              this.loading(true)
              let response = await SuppliesAPI.get_asset_supplies_category_info(this.axios, data)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
              }else{
                if(response && response.data && response.data.message){
                    this.$toast.error(response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi không lấy được dữ liệu')
                } 
              }
          }catch(e){
              this.loading(false)
              if(error.data&&error.data.message){
                  this.$toast.error(error.data.message)
              }else if(error.response&&error.response.data&&error.response.data.message){
                  this.$toast.error(error.response.data.message)
              }else{
                  this.$toast.error('Đã xảy ra lỗi không lấy được dữ liệu')
              }
              return null
          }
      },
      async lay_ds_tt_dadongbo(ds_ma_vt, ds_dvi_tinh){
          try{
              this.loading(true)
              let response = await SuppliesAPI.lay_ds_tt_dadongbo(this.axios, ds_ma_vt, ds_dvi_tinh)
              this.loading(false)
              if(response && response.data && response.data.data) {
                let result=JSON.parse(response.data.data)
                if(result.ERROR_CODE==1){
                  return result.RESULT
                }else{
                  return null
                }
              }else{
                return null
              }
          }catch(e){
              this.loading(false)
              return null
          }
      },
      async dongbo_vattu(ds_ma_vt, ds_para){
        try{
            this.loading(true)
            let response = await SuppliesAPI.dongbo_vattu(this.axios, ds_ma_vt, ds_para)
            this.loading(false)
            if(response && response.data && response.data.data) {
              let result=JSON.parse(response.data.data)
              if(result.ERROR_CODE==1){
                return {
                  code:1,
                  message:'Đồng bộ thành công'
                }
              }else{
                return {
                  code:0,
                  message:result.MESSAGE
                }
              }
            }else{
              if(response&&response.data&&response.data.message){
                return{
                  code:0,
                  message:response.data.message
                }
              }else{
                return{
                  code:0,
                  message:'Đã xảy ra lỗi, đồng bộ liệu không thành công'
                }
              }
            }
        }catch(e){
          this.loading(false)
          if(e.data&&e.data.message){
            return {
              code:0,
              message:e.data.message
            }
          }else if(e.response&&e.response.data&&e.response.data.message){
            return {
              code:0,
              message:e.response.data.message
            }
          }else{
            return{
              code:0,
              message:'Đã xảy ra lỗi, đồng bộ liệu không thành công'
            }
          }
        }
      },
      pageChanged(args) {
        console.log('pageChanged',args)
        this.pageSize = args.pageSize;
        this.pageIndex = args.pageIndex;
        if(this.isEnterMaVT){
          this.isEnterMaVT=false
          return
        }
        this.LoadGrid()
      },
      async LoadGrid(){
        let data={
          access_token:this.$auth.getToken(),
          code:this.input_mavt.trim(),
          page_index:this.pageIndex+1,
          page_size:this.pageSize
        }
        let result=await this.get_asset_supplies_category_info(data)
        if(result&&result.success){
          this.totalRecords=result._web_page_size
          var dataRecord=result.data.map(item=>{
            var loaitbi_id=1
            if(item.is_serial){
              loaitbi_id=2
            }
            if(item.code&&item.code.startsWith('10.')){
              loaitbi_id=3
            }
            let loaitbi=this.ds_loaitbi.find(x=>x.LOAITBI_ID==loaitbi_id)
            let dvt=this.ds_donvi_tinh.find(x=>x.DVITINH_ID==15)
            return Object.assign(item,{
              LOAITBI_ID:loaitbi_id,
              LOAITBI:loaitbi?loaitbi.LOAI_TBI:'',
              IS_SYNC:'',
              DVITINH_ID:15,
              DVI_TINH:dvt?dvt.DVI_TINH:''
            })
          })
          //map dữ liệu
          //
          let ds_ma_vt=dataRecord.map(x=>x.code)
          let ds_dvi_tinh=dataRecord.map(x=>x.unit)
          let resultTTDB=await this.lay_ds_tt_dadongbo(JSON.stringify(ds_ma_vt),JSON.stringify(ds_dvi_tinh))
          if(resultTTDB){
            console.log('resultTTDB', resultTTDB)
            let dsVTDB=resultTTDB.DS_VT_DB
            for(let i=0;i<dsVTDB.length;i++){
              const index=dataRecord.findIndex(x=>x.code==dsVTDB[i])
              if(index>-1){
                dataRecord[index].IS_SYNC='Đã đồng bộ'
              }
            }
            let dsDVT=resultTTDB.DS_DVI_TINH
            for(let i=0;i<dataRecord.length;i++){
              for(let j=0;j<dsDVT.length;j++){
                if(dataRecord[i].unit==dsDVT[j].DVI_TINH){
                  dataRecord[i].DVITINH_ID=dsDVT[j].DVITINH_ID
                  dataRecord[i].DVI_TINH=dsDVT[j].DVI_TINH
                }
              }
            }
          }
          console.log('dataRecord', dataRecord)
          this.input_timkiem_mavt=''
          this.dataSources=dataRecord
          this.dataSourcesBak=dataRecord
        }else{
          this.$toast.error(result.message)
        }
        
      },
      async initDuLieu(){
        //
        if(this.tag=='-1'){
          this.actions[this.actions.findIndex(x=>x.id=='dongbo_vt')].active=false
        }
        //
        this.ds_loaitbi=await this.lay_danhsach_loaithietbi()
        this.ds_donvi_tinh=await this.lay_danhsach_donvitinh()
        if(this.ds_loaitbi.length>0){
          this.loaitbi_selected=this.ds_loaitbi[0].LOAITBI_ID
        }
        if(this.ds_donvi_tinh.length>0){
          this.loai_donvi_tinh_selected=this.ds_donvi_tinh[0].DVITINH_ID
        }
      },  
      handleShowModal(){
        this.input_mavt=''
        this.ds_loaitbi=[]
        this.ds_donvi_tinh=[]
        this.loaitbi_selected=null
        this.loai_donvi_tinh_selected=null
        this.input_timkiem_mavt=''
        this.dataSources=[]
        this.pageIndex=0
        this.totalRecords=0
        this.dataCheckeds=[]
        this.dataSourcesBak=[]
        
        
        setTimeout(()=>{
          this.initDuLieu()
        },500)
      },
      updateIndexLoaiTbi(data){
        const index=this.dataSources.findIndex(x=>x.code==data.code)
        if(index>-1){
          this.dataSources[index].LOAITBI_ID=data.LOAITBI_ID
        }
      },
      columnTemplateLoaiTB(parent){
        return function(){
          return {
            template: {
                // template: `
                // <div class="select-custom">
                //   <select class="form-control" v-model="data.LOAITBI_ID">
                //     <option  v-for="item in parent.ds_loaitbi" :key="item.LOAITBI_ID" :value="item.LOAITBI_ID">
                //       {{item.LOAI_TBI}}
                //     </option>
                //   </select>
                // </div>
                // `,
                template: `
                  <ejs-dropdownlist 
                      :allowFiltering="true"
                      v-model="data.LOAITBI_ID"
                      :dataSource="parent.ds_loaitbi"
                      @change="changeLoaiTbi"
                      :fields="{ text: 'LOAI_TBI', value: 'LOAITBI_ID' }" />
                `,
                data() {
                    return {
                      parent: parent,
                      data: {}
                    }
                },
                methods:{
                  changeLoaiTbi(){
                    this.parent.updateIndexLoaiTbi(this.data)
                    
                  }
                },
            }
          }
        }
      },
      columnTemplateDonViTinh(parent){
        return {
          template: {
              // template: `
              //   <select class="form-control" v-model="data.DVITINH_ID">
              //     <option  v-for="item in parent.ds_donvi_tinh" :key="item.DVITINH_ID" :value="item.DVITINH_ID">
              //       {{item.DVI_TINH}}
              //     </option>
              //   </select>
              // `,
              template: `
                <ejs-dropdownlist 
                      :allowFiltering="true"
                      v-model="data.DVITINH_ID"
                      :dataSource="parent.ds_donvi_tinh"
                      @change="changeDVTinh"
                      :fields="{ text: 'DVI_TINH', value: 'DVITINH_ID' }" />  
              `,
              data() {
                  return {
                    parent: parent,
                    data: {}
                  }
              },
              methods:{
                changeDVTinh(){
                  //this.parent.sendSMS(this.data)
                }
              },
          }
        }
      }

    },
    watch:{
      input_timkiem_mavt(val){
        this.txtLocVatTuEditValueChanged()
      }
    },
    created(){
      this.$root.$on("bv::modal::shown", (bvEvent, modalId) => {
          document.getElementsByClassName("modal-content").forEach(function (v) {
              v.removeAttribute("tabindex");
          });
      });
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
.green{
  color: green!important;
}
</style>
