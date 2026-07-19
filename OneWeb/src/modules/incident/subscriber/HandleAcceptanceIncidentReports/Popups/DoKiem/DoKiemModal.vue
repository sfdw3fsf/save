<template>
  <b-modal
    ref="popupDoKiem"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Kết quả đo kiểm
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>

      <ActionTop :actions="actions" @onActionClick="onActionClick" />
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Kêt quả đo chất lượng</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">Speed Up</div>
                <div class="value">
                  <input type="text" v-model="input_speed_up" @keypress="isNumber($event)" class="form-control fw6" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">Attenuation Up</div>
                <div class="value">
                  <input type="text" v-model="input_attenuation_up" @keypress="isNumber($event)" class="form-control" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">SNR Up</div>
                <div class="value">
                  <input type="text" v-model="input_snr_up" @keypress="isNumber($event)" class="form-control" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">Speed Down</div>
                <div class="value">
                  <input type="text" v-model="input_speed_down" @keypress="isNumber($event)" class="form-control fw6" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">Attenuation Down</div>
                <div class="value">
                  <input type="text" v-model="input_attenuation_down" @keypress="isNumber($event)" class="form-control" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">SNR Down</div>
                <div class="value">
                  <input type="text" v-model="input_snr_down" @keypress="isNumber($event)" class="form-control" />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="info-row">
              <div class="key w120">Ghi chú</div>
              <div class="value pl-2">
                <input type="text" v-model="input_ghichu" class="form-control fw6" />
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
import API from './API'
import moment from 'moment'
//frmKetQuaDoKiem.cs chưa có khảo sát api

export default {
    name:'DoKiemModal',
    props:{
        hdtb_id:{
          type:Number,
          default:0
        },
        baohong_id:{
          type:Number,
          default:0
        }
    },
    components:{
        ActionTop
    },
    data(){
        return {
            actions:[
                {
                    id:'ghilai',
                    name:'Cập nhật',
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
            input_speed_up:'',
            input_speed_down:'',
            input_snr_up:'',
            input_snr_down:'',
            input_attenuation_down:'',
            input_attenuation_up:'',
            input_ghichu:'',
            nhapmoi:true

        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='ghilai'){
              this.capnhat()
            }else if(action.id=='xoa'){
              this.xoa()
            }
        },
        showModal() {
            this.$refs["popupDoKiem"].show()
        },
        hideModal() {
            this.$refs["popupDoKiem"].hide()
        },
        handleShowModal(){
          this.input_speed_up=''
          this.input_speed_down=''
          this.input_snr_up=''
          this.input_snr_down=''
          this.input_attenuation_down=''
          this.input_attenuation_up=''
          this.input_ghichu=''
          this.nhapmoi=true
          setTimeout(()=>{
            this.initDuLieu()
          }, 500)
        },
        //charCode!=46 dot
        isNumber(evt) {
            evt = (evt) ? evt : window.event;
            var charCode = (evt.which) ? evt.which : evt.keyCode;
            if ((charCode > 31 && (charCode < 48 || charCode > 57)&&charCode!=46)) {
                evt.preventDefault()
            } else {
                return true
            }
        },
        async initDuLieu(){
          //ds = hoancong.Lay_DS_CHATLUONG(vhdtb_id, vbaohong_id);
          let ds=await this.sp_ds_chatluong(this.hdtb_id, this.baohong_id)
          if(ds.length>0){
            let item=ds[0]
           
            this.input_speed_up=item.speed_up!=null?item.speed_up:''
            this.input_speed_down=item.speed_down!=null?item.speed_down:''
            this.input_snr_up=item.snr_up!=null?item.snr_up:''
            this.input_snr_down=item.snr_down!=null?item.snr_down:''
            this.input_attenuation_down=item.attenuation_down!=null?item.attenuation_down:''
            this.input_attenuation_up=item.attenuation_up!=null?item.attenuation_up:''
            this.input_ghichu=item.ghichu!=null?item.ghichu:''

            this.nhapmoi=false
            this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
          }else{
            this.input_speed_up=''
            this.input_speed_down=''
            this.input_snr_up=''
            this.input_snr_down=''
            this.input_attenuation_down=''
            this.input_attenuation_up=''
            this.input_ghichu=''
            this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=false
          }
        },
        async capnhat(){
          //0:Nhập mới, 1: Cập nhật
          let kieu=this.nhapmoi?0:1
          if(this.hdtb_id!=0){
            let ds_chatluong=this.getDuLieuChatLuongHDTB()
            await this.capnhat_chatluong_hdtb(kieu, JSON.stringify(ds_chatluong))
          }
          if(this.baohong_id!=0){
            let ds_chatluong_bh=this.getDuLieuBaoHong()
            await this.capnhat_chatluong_bh(kieu, JSON.stringify(ds_chatluong_bh))
          }
          this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
          this.$toast.success('Cập nhật thông tin đo kiểm thành công !')
        },
        async xoa(){
          if(this.hdtb_id!=0){
            let CL_HDTB=[{HDTB_ID:this.hdtb_id}]
            await this.capnhat_chatluong_hdtb(2, JSON.stringify(CL_HDTB))
          }
          if(this.baohong_id!=0){
            let BaoHong=[{BAOHONG_ID:this.baohong_id}]
            await this.capnhat_chatluong_bh(2, JSON.stringify(BaoHong))
          }
          this.input_speed_up=''
          this.input_speed_down=''
          this.input_snr_up=''
          this.input_snr_down=''
          this.input_attenuation_down=''
          this.input_attenuation_up=''
          this.input_ghichu=''
          this.$toast.success('Xóa thông tin đo kiểm thành công !')
          this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=false
        },
        getDuLieuChatLuongHDTB(){
          return [
            {
              HDTB_ID:this.hdtb_id,
              SPEED_UP:this.input_speed_up.trim()!=''?Number(this.input_speed_up.trim()):null,
              SPEED_DOWN:this.input_speed_down.trim()!=''?Number(this.input_speed_down.trim()):null,
              ATTENUATION_UP:this.input_attenuation_up.trim()!=''?Number(this.input_attenuation_up.trim()):null,
              ATTENUATION_DOWN:this.input_attenuation_down.trim()!=''?Number(this.input_attenuation_down.trim()):null,
              SNR_UP:this.input_snr_up.trim()!=''?Number(this.input_snr_up.trim()):null,
              SNR_DOWN:this.input_snr_down.trim()!=''?Number(this.input_snr_down.trim()):null,
              GHICHU:this.input_ghichu.trim()!=''?this.input_ghichu.trim():null,
              NGUOI_CN:this.$root.token.getUserName(),
              NGAY_CN:moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
              MAY_CN:'localhost',
              IP_CN:'127.0.0.1',
            }
          ]
        },
        getDuLieuBaoHong(){
          return [
            {
              BAOHONG_ID:this.baohong_id,
              SPEED_UP:this.input_speed_up.trim()!=''?Number(this.input_speed_up.trim()):null,
              SPEED_DOWN:this.input_speed_down.trim()!=''?Number(this.input_speed_down.trim()):null,
              ATTENUATION_UP:this.input_attenuation_up.trim()!=''?Number(this.input_attenuation_up.trim()):null,
              ATTENUATION_DOWN:this.input_attenuation_down.trim()!=''?Number(this.input_attenuation_down.trim()):null,
              SNR_UP:this.input_snr_up.trim()!=''?Number(this.input_snr_up.trim()):null,
              SNR_DOWN:this.input_snr_down.trim()!=''?Number(this.input_snr_down.trim()):null,
              GHICHU:this.input_ghichu.trim()!=''?this.input_ghichu.trim():null,
              NGUOI_CN:this.$root.token.getUserName(),
              NGAY_CN:moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
              MAY_CN:'localhost',
              IP_CN:'127.0.0.1',
            }
          ]
        },
        async sp_ds_chatluong(hdtb_id, baohong_id){
            try{
                this.loading(true)
                let response=await API.sp_ds_chatluong(this.axios, hdtb_id, baohong_id)
                console.log(response)
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
        async capnhat_chatluong_hdtb(kieu, ds_chatluong){
            try{
                this.loading(true)
                let response=await API.capnhat_chatluong_hdtb(this.axios, kieu, ds_chatluong)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return true
                }else{
                    return false
                }
            }catch(e){
                this.loading(false)

                return false
            }
        },
        async capnhat_chatluong_bh(kieu, ds_chatluong_bh){
            try{
                this.loading(true)
                let response=await API.capnhat_chatluong_bh(this.axios, kieu, ds_chatluong_bh)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return true
                }else{
                    return false
                }
            }catch(e){
                this.loading(false)
                return false
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
</style>
