<template>
<b-modal
    ref="PopupGanTrachNhiemDonVi"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal">
  <div class="modal-content popup-box">
     <div class="popup-header">
                <div class="title">
                <span class="icon one-notepad"></span> Gán trách nhiệm đơn vị cho câu trả lời
                </div>
                 <div class="close -ap icon-close" @click="hideModal"></div>
            </div>
              <ActionTop :actions="actions"  @onActionClick="onActionClick"/>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="legend-title">Thông tin câu hỏi / Trả lời</div>
                        <div class="info-row">
                            <div class="key w80">Câu hỏi</div>
                            <div class="value">
                                <div class="select-custom">
                                    <SelectExt v-model="cauhoi_selectted" :options="list_cauhoi" />  
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w80">Câu trả lời</div>
                            <div class="value">
                                <div class="select-custom">
                                    <SelectExt v-model="cautralloi_selectted" :options="list_tralloi" />  
                                </div>
                            </div>
                        </div>
                    </div>
                     <div class="box-form">
                        <div class="box-move-select-table">
                        <div class="box-col box-form">
                            <div class="legend-title">Loại đơn vị chưa gán</div>
                             <div class="table-content">
                               <k-table :columns="columnsChuaGan" 
                                 :dataSources="dataSourcesChuaGan" 
                                 @dataCheckeds="onDataCheckedsChuaGan" 
                                 :allowCheckBox="true" 
                                 :allowFilter="true"/>
                            </div>
                        </div>
                        <div class="actions">
                               <button class="btn" @click="gan">
                            <span class="fa fa-angle-right"></span>
                            </button>

                            <button class="btn" @click="ganAll">
                            <span class="fa fa-angle-double-right"></span>
                            </button>

                            <button class="btn" @click="boGan">
                            <span class="fa fa-angle-left"></span>
                            </button>

                            <button class="btn" @click="boGanAll">
                            <span class="fa fa-angle-double-left"></span>
                            </button>
                        </div>
                        <div class="box-col box-form">
                            <div class="legend-title">Loại đơn vị đã gán</div>
                            <div class="table-content">
                             <k-table :columns="columnsGan" 
                               :dataSources="dataSourcesGan" 
                               @dataCheckeds="onDataCheckedsGan" 
                               :allowCheckBox="true" 
                               :allowFilter="true"/>
                            </div>
                        </div>
                    </div>
                    </div>
                    
                   
                    
                </div>
            </div>
  </b-modal>
</template>

<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KTable from '@/components/kylq_components/KTable.vue'
import API from './API'

export default {
    name:'PopupGanTrachNhiemDonVi',
     props:{
      vrch_id:{
        type:Number,
        default: 110,
        required: true
      }
    },
    components:{
        ActionTop,
        KTable
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
            columnsGan:[
                {
                    field:'ten_loaidv',
                    label:'Tên loại đơn vị',
                    allowFilter:true
                },
            ],
            dataSourcesGan:[],
            columnsChuaGan:[
                {
                      field:'ten_loaidv',
                    label:'Tên loại đơn vị',
                    allowFilter:true
                },
            ],
            dataSourcesChuaGan:[],
            dataCheckedsGan:[],
            dataCheckedsChuaGan:[],
            list_cauhoi: [],
            cauhoi_selectted: null,
            list_tralloi: [],
            cautralloi_selectted: null,
           
        }
    }, 
    methods:{
       
        onActionClick(action){
            if(action.id=='capnhat'){
              let danhsach=this.getDanhSach()
              let data={
                vrch_id: 110,
                danhsach: danhsach
              }
              this.sp_update(data)
            }
        },
        showModal() {
            this.$refs["PopupGanTrachNhiemDonVi"].show()
        },
        hideModal() {
            this.$refs["PopupGanTrachNhiemDonVi"].hide()
        },
        handleShowModal(){
            //init data show modal
            this.dataSourcesGan=[]
            this.dataSourcesChuaGan=[]
            this.dataCheckedsGan=[]
            this.dataCheckedsChuaGan=[]
            this.list_cauhoi=[]
            this.cauhoi_selectted=null
            this.list_tralloi= []
            this.cautralloi_selectted= null

            this.lay_danhsach_cauhoi_option()
            this.lay_danhsach_cautraloi_option()
            this.layDanhsachDavaChuaGan(0);
            this.layDanhsachDavaChuaGan(1);

        },
         onDataCheckedsGan(data){
          this.dataCheckedsGan=data
        },
        onDataCheckedsChuaGan(data){
          this.dataCheckedsChuaGan=data
        },
         boGan(){
          if(this.dataCheckedsGan.length==0){
            return
          }
          debugger;
          this.dataCheckedsGan.forEach((item)=>{
            this.dataSourcesChuaGan.push(item)

            const index =this.dataSourcesGan.findIndex(x=>x.loaidv_id==item.loaidv_id)
            if(index>-1){
              this.dataSourcesGan.splice(index,1)
            }

          })
          this.dataCheckedsGan=[]
        },
        boGanAll(){
          if(this.dataSourcesGan.length==0){
            return
          }
          this.dataSourcesGan.forEach((item)=>{
            this.dataSourcesChuaGan.push(item)
          })
          this.dataSourcesGan=[]
        },
        gan(){
          if(this.dataCheckedsChuaGan.length==0){
            return
          }
          debugger;
          this.dataCheckedsChuaGan.forEach((item)=>{
            this.dataSourcesGan.push(item)

            const index =this.dataSourcesChuaGan.findIndex(x=>x.loaidv_id==item.loaidv_id)
            if(index>-1){
              this.dataSourcesChuaGan.splice(index,1)
            }
            
          })
          this.dataCheckedsChuaGan=[]
        },
        ganAll(){
          if(this.dataSourcesChuaGan.length==0){
            return
          }
          this.dataSourcesChuaGan.forEach((item)=>{
            this.dataSourcesGan.push(item)
          })
          this.dataSourcesChuaGan=[]
        },
         getDanhSach(){
            //tạo chuỗi mảng
            var result="["
            if(this.dataSourcesGan.length>0){
                this.dataSourcesGan.forEach((item, index)=>{
                    var row="#1"
                    row=row.replace("#1",item.loaidv_id)
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
         async layDanhsachDavaChuaGan(loai) {


                this.loading(true)
                let rs = await this.axios.post("web-quantri/capnhatcauhoi/sp_lay_ds_loai_dvi_in_cautraloi", {
                          "vrch_id": 110
             })
                if(loai == 0)
                    this.dataSourcesChuaGan = rs.data.data.chuagan
                if(loai == 1)
                    this.dataSourcesGan = rs.data.data.dagan

                this.loading(false)
            
        },
       
          async lay_danhsach_cauhoi_option() {
                API.popup_CHP_layDSCauHoi(this.axios)
                    .then((response) => {
                    if (
                        response.data.error_code === "BSS-00000000" &&
                        response.data.data !== undefined &&
                        response.data.data.length > 0
                    ) {
                        let items = [];
                        if (response.data.data !== undefined) {
                        response.data.data.forEach(function (item) {
                            items.push({ id: item.cauhoi_id, text: item.noidung });
                        });
                        this.list_cauhoi = items;
                        this.cauhoi_selectted = items[0].id;
                        }
                    }
                    })
                    .catch((error) => {
                    console.log(error);
                    })
                    .finally(() => {});
                },
             async lay_danhsach_cautraloi_option() {
                API.getDSCauTraLoi(this.axios)
                    .then((response) => {
                    if (
                        response.data.error_code === "BSS-00000000" &&
                        response.data.data !== undefined &&
                        response.data.data.length > 0
                    ) {
                        let items = [];
                        if (response.data.data !== undefined) {
                        response.data.data.forEach(function (item) {
                            items.push({ id: item.traloi_id, text: item.noidung });
                        });
                        this.list_tralloi = items;
                        this.cautralloi_selectted = items[0].id;
                        }
                    }
                    })
                    .catch((error) => {
                    console.log(error);
                    })
                    .finally(() => {});
                },
            async sp_update(data){
           
                this.loading(true)
                let response = await API.popup_TNDV_ghilai(this.axios, data)
                //this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.$alert('Cập nhật thành công!', 'Thông báo', {
                        dangerouslyUseHTMLString: true,
                        confirmButtonText: 'OK',
                        type: 'info'
                    })
                    this.handleShowModal()
                }else{
                    
                        this.$alert(response.data.message, '', {
                            dangerouslyUseHTMLString: true,
                            confirmButtonText: 'OK',
                            type: 'error'
                        })
                    
                }
        }

        
    }
}
</script>

