<template>
  <b-modal
    ref="popupChonIP"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span> Chọn IP</div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <ActionTop :actions="actions" @onActionClick="onActionClick"/>
      <div class="popup-body">
        <div class="box-form">
          <div class="info-row">
            <div class="key w90">Bras</div>
            <div class="value">
              <multiselect
                  :options="ds_bras"
                  placeholder="Chọn bras"
                  @input="changeBras"
                  :showLabels="false"
                  :allowEmpty="false"
                  :value="bras_o_selected"
                  label="tenbras"
                  track-by="bras_id">
                  <template #noOptions>
                    Không tìm thấy thông tin
                  </template>
              </multiselect>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">
              <div class="check-action">
                <input type="radio" name="ip" value="1" :checked="loai==1" @change="changeRadioIP($event)" class="check" />
                <span class="name">IPv4 LAN</span>
              </div>
            </div>
            <div class="value">
              <input type="text" v-model="result.diachiIPv4LAN" readonly class="form-control" />
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">
              <div class="check-action">
                <input type="radio" name="ip" value="2" :checked="loai==2" @change="changeRadioIP($event)"  class="check" />
                <span class="name">IPv4 WAN</span>
              </div>
            </div>
            <div class="value">
              <input type="text" v-model="result.diachiIPv4WAN" class="form-control" readonly />
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">
              <div class="check-action">
                <input type="radio" name="ip" value="3" :checked="loai==3" @change="changeRadioIP($event)"  class="check" />
                <span class="name">IPv6 LAN</span>
              </div>
            </div>
            <div class="value">
              <input type="text" v-model="result.diachiIPv6LAN" readonly class="form-control" />
            </div>
          </div>
          <KTableV3 :columns="columns"
            :dataSources="dataSources"
            @onSelectedRow="onSelectedRow"
            :rowSelectedId="rowSelectedId"
            :allowFilter="true"
            id="id"/>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import API from './API'
//frmChonIP.cs 
//UR2.4.007_button26_005 from dùng chung
// api VDC đang lỗi thử lại sau
export default {
    name:'ChonIPModal',
    props:{
        bras_id:{
            type:Number,
            default:0
        },
        in_tocdo_id:{
            type:Number,
            default:0
        },
        kieu:{
            type:Number,
            default:1
        },
        doituong_id:{
            type:Number,
            default:0
        }
    },
    components:{
        ActionTop,
        KTableV3
    },
    data(){
        return {
            actions:[
                {
                    id:'chon',
                    name:'Chọn (F5)',
                    active: true,
                    icon_class:'ui-1_check-circle-08 nc-icon-glyph'
                }
            ],
            columns:[
                {
                    field: "stt",
                    label: "STT",
                    allowFilter: true,
                    width:'10%'
                },
                {
                    field: "ip",
                    label: "Địa chỉ IP",
                    allowFilter: true
                }
            ],
            dataSources:[],
            rowSelectedId:-1,
            loai:1,
            ds_bras:[],
            bras_selected:null,
            result:{
                diachiIPv4LAN:'',
                diachiIPv4WAN:'',
                diachiIPv6LAN:''
            },
            
        }
    },
    computed:{
        bras_o_selected(){
            const bras=this.ds_bras.find(x=>x.bras_id==this.bras_selected)
            return bras!=undefined?bras:null
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='chon'){
                this.chapnhan()
            }
        },
        showModal() {
            this.$refs["popupChonIP"].show()
        },
        hideModal() {
            this.$refs["popupChonIP"].hide()
        },
        handleShowModal(){
            this.dataSources=[]
            this.rowSelectedId=-1
            this.loai=1
            this.ds_bras=[]
            this.bras_selected=null
            this.result={
                diachiIPv4LAN:'',
                diachiIPv4WAN:'',
                diachiIPv6LAN:''
            }
            this.actions[this.actions.findIndex(x=>x.id=='chon')].active=false
            setTimeout(()=>{
                this.initDuLieu()
            }, 500)
        },
        onSelectedRow(item){
            let diachiIp=item.ip!=null?item.ip:''
            if(this.loai==1){
                this.result.diachiIPv4LAN=diachiIp
            }else if(this.loai==2){
                this.result.diachiIPv4WAN=diachiIp
            }else if(this.loai==3){
                this.result.diachiIPv6LAN=diachiIp
            }
        },
        async changeRadioIP(event){
            this.loai=event.target.value
            this.result={
                diachiIPv4LAN:'',
                diachiIPv4WAN:'',
                diachiIPv6LAN:''
            } 

            await this.laythongtin_ip()
        },
        async changeBras(data){
            this.bras_selected=data.bras_id
            this.result={
                diachiIPv4LAN:'',
                diachiIPv4WAN:'',
                diachiIPv6LAN:''
            } 
            await this.laythongtin_ip()
        },
        async chapnhan(){
            if(this.dataSources.length==0){
                this.$toast.error('Chưa có danh sách IP để chọn!')
                return
            }
            //Kiểm tra IP này đã được cấp hay chưa?
            let diachi=this.loai==1?this.result.diachiIPv4LAN:this.loai==2?this.result.diachiIPv4WAN:this.result.diachiIPv6LAN
           
            let check=await this.kiemtra_ip_tb_ftth(diachi, this.loai)
            if(check>0){
                this.$toast.error('Địa chỉ IP ' + diachi + ' đã được cấp cho thuê bao khác. Bạn hãy chọn IP khác !')
                return
            }

            this.$emit('callback',this.result)

            this.hideModal()

        },
        async initDuLieu(){
            this.loai=this.kieu
            this.ds_bras=await this.lay_ds_bras_adsl()
            this.bras_selected=this.bras_id
            await this.laythongtin_ip()

        },
        async lay_ds_bras_adsl(){
            try{
                this.loading(true)
                let response=await API.lay_ds_bras_adsl(this.axios)
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
        async fn_tt_profile_id_tocdo_adsl_theo_tocdo_id(data, defaultValue){
            try{
                this.loading(true)
                let response=await API.fn_tt_profile_id_tocdo_adsl_theo_tocdo_id(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return response.data.data
                }else{
                    return defaultValue
                }
                
            }catch(e){
                this.loading(false)
                return defaultValue
            }
        },
        async fn_tt_bras_vdc_id_theo_bras_id(data, defaultValue){
            try{
                this.loading(true)
                let response=await API.fn_tt_bras_vdc_id_theo_bras_id(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return response.data.data
                }else{
                    return defaultValue
                }
                
            }catch(e){
                this.loading(false)
                return defaultValue
            }
        },
        async fn_tt_doituong_vdc_id_theo_doituong_id(data, defaultValue){
            try{
                this.loading(true)
                let response=await API.fn_tt_doituong_vdc_id_theo_doituong_id(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return response.data.data
                }else{
                    return defaultValue
                }
                
            }catch(e){
                this.loading(false)
                return defaultValue
            }
        },
        //0: chưa có ai sử dụng được phép cấp, ngược lại lớn hơn 0
        async kiemtra_ip_tb_ftth(ip, kieu){
            try{
                this.loading(true)
                let response=await API.kiemtra_ip_tb_ftth(this.axios, ip, kieu)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return Number(response.data.data)
                }else{
                    return 1
                }
                
            }catch(e){
                this.loading(false)
                return 1
            }
        },
        async lay_ip_tinh(brasId, serviceId, groupCustomerCode = 0){
            try{
                this.loading(true)
                let response=await API.lay_ip_tinh(this.axios, brasId, serviceId)
                this.loading(false)
                if(response&&response.data&&response.data.errorCode!=undefined&&response.data.errorCode==0) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_ip_v6(brasId, serviceId, groupCustomerCode = 0){
            try{
                this.loading(true)
                let response=await API.lay_ip_v6(this.axios, brasId, serviceId)
                this.loading(false)
                if(response&&response.data&&response.data.errorCode!=undefined&&response.data.errorCode==0) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async laythongtin_ip(){
            if(this.ds_bras.length==0){
                return
            }
            //
            var ma_td=0
            let s=await this.fn_tt_profile_id_tocdo_adsl_theo_tocdo_id(this.in_tocdo_id, "-1")
            if(s!="-1"){
                ma_td=Number(s)
            }
            //
            // Sửa lấy bras_vdc_id trong bảng bras chứ ko phải bras_id
            var vbras_vdc_id = -1
            let bras_vdc_id=await this.fn_tt_bras_vdc_id_theo_bras_id(this.bras_id, "-1")
            if(bras_vdc_id!="-1"){
                vbras_vdc_id=Number(bras_vdc_id)
            }

            let groupCustomerCode = await this.fn_tt_doituong_vdc_id_theo_doituong_id(this.doituong_id, "-1")
            let groupCustomerCodeNumber = this.doituong_id
            if(groupCustomerCode!="-1" && groupCustomerCode!="0"){
                groupCustomerCodeNumber=Number(groupCustomerCode)
            }


            if(this.loai==1||this.loai==2){
                //hard tạm
                // vbras_vdc_id=6573
                // ma_td=29118388
                
                this.dataSources=[]
                let ds=await this.lay_ip_tinh(vbras_vdc_id, ma_td, groupCustomerCodeNumber)
                if(ds.length>0){
                    this.dataSources=ds.map((x,index)=>Object.assign({},{
                        id:index+1,
                        stt:index+1,
                        ip:x
                    }))
                    this.rowSelectedId=this.dataSources[0].id
                    this.onSelectedRow(this.dataSources[0])
                }else{
                    this.dataSources=[]
                }

            }else{
                //ipv6
                this.dataSources=[]
                let ds=await this.lay_ip_v6(vbras_vdc_id, ma_td, groupCustomerCodeNumber)
                if(ds.length>0){
                    this.dataSources=ds.map((x,index)=>Object.assign({},{
                        id:index+1,
                        stt:index+1,
                        ip:x
                    }))
                    this.rowSelectedId=this.dataSources[0].id
                    this.onSelectedRow(this.dataSources[0])
                }else{
                    this.dataSources=[]
                }
            }


        }
    },
    watch:{
        dataSources(val){
            if(val.length>0){
                this.actions[this.actions.findIndex(x=>x.id=='chon')].active=true
            }else{
                this.actions[this.actions.findIndex(x=>x.id=='chon')].active=false
            }
        }
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
</style>