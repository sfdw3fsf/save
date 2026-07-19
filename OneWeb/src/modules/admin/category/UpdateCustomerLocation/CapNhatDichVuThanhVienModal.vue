<template>
  <b-modal
    ref="popupCapNhatDichVuThanhVien"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal">
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Cập nhật dịch vụ thành viên
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>

      <ActionTop :actions="actions"  @onActionClick="onActionClick"/>

      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin cập nhật</div>
          <div class="info-row">
            <div class="key w50">SL TV</div>
            <div class="value">
              <input type="text" class="form-control" />
            </div>
          </div>
        </div>
        <div class="box-move-select-table">
          <div class="box-col box-form">
            <div class="legend-title">Loại hình TB đã gán</div>
            <KDataGrid
                :columns="columns"
                :dataSource="dataSourcesGan"
                :showColumnCheckbox="true"
                :enable-paging-server="false"
                :allowPaging="false"
                :showFilter="true"
                ref="gridDaGan"
                :enabledSelectFirstRow="false"
                panelDataHeight="360px"/>

          </div>
          <div class="actions">
            <button class="btn" @click="boGan">
              <span class="fa fa-angle-right"></span>
            </button>

            <button class="btn" @click="boGanAll">
              <span class="fa fa-angle-double-right"></span>
            </button>

            <button class="btn" @click="gan">
              <span class="fa fa-angle-left"></span>
            </button>

            <button class="btn" @click="ganAll">
              <span class="fa fa-angle-double-left"></span>
            </button>
          </div>
          <div class="box-col box-form">
            <div class="legend-title">Loại hình TB chưa gán</div>
            <KDataGrid
                :columns="columns"
                :dataSource="dataSourcesChuaGan"
                :showColumnCheckbox="true"
                :enable-paging-server="false"
                :allowPaging="false"
                :showFilter="true"
                ref="gridChuaGan"
                :enabledSelectFirstRow="false"
                panelDataHeight="360px"/>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import UpdateCustomerLocationAPI from './UpdateCustomerLocationAPI'

export default {
    name:'CapNhatDichVuThanhVienModal',
    props:{
      hodan_id:{
        type:Number,
        default: -1,
        required: true
      },
      tvhd_id:{
        type:Number,
        default: -1,
        required: true
      }
    },
    components:{
        ActionTop,
        KDataGrid
    },
    data(){
        return{
            actions:[
                {
                    id:'capnhat',
                    name:'Cập nhật',
                    active: true,
                    icon_class:'one-reload1'
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
            ],
            dataSourcesGan:[],
            dataSourcesChuaGan:[]
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='capnhat'){

              let nguoi_cn=this.$root.token.getUserName()
              let danhsach=this.getDanhSach()
              let data={
                tvhd_id: this.tvhd_id,
                hodan_id: this.hodan_id,
                nguoi_cn: nguoi_cn,
                may_cn:"localhost",
                ip_cn: "127.0.0.1",
                json_data: danhsach
              }
              this.sp_qldaily_dichvu_thanhvien_update(data)
            }
        },
        showModal() {
            this.$refs["popupCapNhatDichVuThanhVien"].show()
        },
        hideModal() {
            this.$refs["popupCapNhatDichVuThanhVien"].hide()
        },
        handleShowModal(){
            //init data show modal
            this.dataSourcesGan=[]
            this.dataSourcesChuaGan=[]
            setTimeout(()=>{
              this.initDulieu()
            },500)
        },
        boGan(){
          let dataCheckedsLHGan=this.$refs.gridDaGan.getSelectedRecords()
          if(dataCheckedsLHGan.length==0){
              return
          }
          for(let i=0;i<dataCheckedsLHGan.length;i++){
              this.dataSourcesChuaGan.push(dataCheckedsLHGan[i])
              const index=this.dataSourcesGan.findIndex(x=>x.loaitb_id==dataCheckedsLHGan[i].loaitb_id&&x.dichvuvt_id==dataCheckedsLHGan[i].dichvuvt_id)
              if(index>-1){
                  this.dataSourcesGan.splice(index,1)
              }
          }
          this.dataSourcesGan=[].concat(this.dataSourcesGan)
          this.dataSourcesChuaGan=[].concat(this.dataSourcesChuaGan)
        },
        boGanAll(){
          if(this.dataSourcesGan.length==0){
              return
          }
          this.dataSourcesChuaGan=this.dataSourcesChuaGan.concat(this.dataSourcesGan)
          this.dataSourcesGan=[]
        },
        gan(){
          let dataCheckedsLHChuaGan=this.$refs.gridChuaGan.getSelectedRecords()
          if(dataCheckedsLHChuaGan.length==0){
              return
          }
          for(let i=0;i<dataCheckedsLHChuaGan.length;i++){
              this.dataSourcesGan.push(dataCheckedsLHChuaGan[i])
              const index=this.dataSourcesChuaGan.findIndex(x=>x.loaitb_id==dataCheckedsLHChuaGan[i].loaitb_id&&x.dichvuvt_id==dataCheckedsLHChuaGan[i].dichvuvt_id)
              if(index>-1){
                  this.dataSourcesChuaGan.splice(index,1)
              }
          }
          this.dataSourcesGan=[].concat(this.dataSourcesGan)
          this.dataSourcesChuaGan=[].concat(this.dataSourcesChuaGan)
        },
        ganAll(){
          if(this.dataSourcesChuaGan.length==0){
              return
          }
          this.dataSourcesGan=this.dataSourcesGan.concat(this.dataSourcesChuaGan)
          this.dataSourcesChuaGan=[]
        },
        getDanhSach(){
            //tạo chuỗi mảng
            var result="["
            if(this.dataSourcesGan.length>0){
                this.dataSourcesGan.forEach((item, index)=>{
                    var row="{'DICHVUVT_ID':#1,'LOAITB_ID':#2}"
                    row=row.replace("#1",item.dichvuvt_id)
                    row=row.replace("#2",item.loaitb_id)
                    if(index==this.dataSourcesGan.length-1){
                        result=result.concat(row)
                    }else{
                        result=result.concat(row).concat(', ')
                    }
                })
            }
            result=result.concat("]")
            return result
        },
        async initDulieu(){
            try{
              this.loading(true)
              //danh sách gán
              this.dataSourcesGan=[]
              let responseGan = await UpdateCustomerLocationAPI.lay_loaihinhtb_hodan_sudung(this.axios, this.hodan_id, this.tvhd_id, 1)
              if(responseGan && responseGan.data && responseGan.data.error_code && responseGan.data.error_code == 'BSS-00000000' && responseGan.data.data) {
                this.dataSourcesGan=responseGan.data.data
              }else{
                this.dataSourcesGan=[]
              }
              //danh sách chưa gán
              this.dataSourcesChuaGan=[]
              let responseChuaGan = await UpdateCustomerLocationAPI.lay_loaihinhtb_hodan_sudung(this.axios, this.hodan_id, this.tvhd_id, 0)
              if(responseChuaGan && responseChuaGan.data && responseChuaGan.data.error_code && responseChuaGan.data.error_code == 'BSS-00000000' && responseChuaGan.data.data) {
                this.dataSourcesChuaGan=responseChuaGan.data.data
              }else{
                this.dataSourcesChuaGan=[]
              }

            }catch(e){
              if(e.data&&e.data.message){
                this.$toast.error(e.data.message)
              }else if(e.response&&e.response.data&&e.response.data.message){
                this.$toast.error(e.response.data.message)
              }else{
                this.$toast.error('Đã có lỗi xảy ra không load được dữ liệu')
              }
            }finally{
              this.loading(false)
            }
        },
        async sp_qldaily_dichvu_thanhvien_update(data){
            try{
                this.loading(true)
                let response = await UpdateCustomerLocationAPI.sp_qldaily_dichvu_thanhvien_update(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.$toast.success('Cập nhật kết quả thành công!')
                    this.initDulieu()
                }else{
                    if(response&&response.data&&response.data.message){
                      this.$toast.error(response.data.message)
                    }else{
                      this.$toast.error('Cập nhật kết quả lỗi')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Cập nhật kết quả lỗi')
                }
            }
        }

        
    }
}
</script>