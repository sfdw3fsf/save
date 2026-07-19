<template>
  <b-modal
    ref="popupCreateDebtTable"
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
          <span class="icon one-notepad"></span> Tạo bảng nợ cho kỳ hoá đơn mới
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <ActionTop :actions="actions"  @onActionClick="onActionClick"/>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin kỳ hóa đơn</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w30 nowrap fw6">Kỳ hóa đơn</div>
                <div class="value">
                  <div class="input-icon-right">
                    <k-date-picker v-model="ky_hoadon" :disabled="true" format="MM/YYYY"/>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w30 nowrap fw6">Kỳ cước</div>
                <div class="value">
                    <div class="select-custom">
                        <select v-model="chuky_no_selected" disabled class="form-control">
                            <option v-for="item in chuky_no" :key="item.chukyno" :value="item.chukyno">
                            {{item.chukyno}}
                            </option>
                        </select>
                    </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách bảng</div>
          <KTableV3  :columns="columns" :dataSources="dataSources" :allowFilter="true">
              <template #footer>
                <tr class="tfoot">
                    <td class="w20 center"></td>
                    <td></td>
                    <td>
                        <input type="text" class="form-control tright text fw6" value="0"/>
                    </td>
                    <td></td>
                </tr>
              </template>
          </KTableV3>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>

import ActionTop from '@/components/kylq_components/ActionTop.vue'
import moment from 'moment'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import CreateDebtTableAPI from './CreateDebtTableAPI'
import { mapActions } from 'vuex'
export default {
    name:'CreateDebtTableModal',
    components:{
        ActionTop,
        KDatePicker,
        KTableV3
    },
    data(){
        return{
            actions:[
                {
                    id:'taodl',
                    name:'Tạo DL',
                    active: true,
                    icon_class:'one-clipart-add'
                },
            ],
            ky_hoadon:moment(new Date()).format('MM/YYYY'),
            columns:[
                {
                    field: "thutu",
                    label: "STT",
                    allowFilter: true
                },
                {
                    field: "tenbang",
                    label: "Tên bảng",
                    allowFilter: true
                },
                {
                    field: "kieu",
                    label: "Kiểu",
                    allowFilter: true
                }
            ],
            dataSources:[],
            chuky_no:[],
            chuky_no_selected:null,
            kyhoadon:''
        }
    },
    computed:{
        
    },
    methods:{
        ...mapActions('admin', [
            'setKyhoadon',
            'setChuKy'
        ]),
        showModal() {
            this.$refs["popupCreateDebtTable"].show()
        },
        hideModal() {
            this.$refs["popupCreateDebtTable"].hide()
        },
        handleShowModal(){
            this.chuky_no=[]
            this.dataSources=[]
            this.chuky_no_selected=null
            this.ky_hoadon=moment(new Date()).format('MM/YYYY')
            this.kyhoadon=''
            setTimeout(()=>{
                this.initDulieu()
            },500)
        },
        async initDulieu(){
            let result=await this.sp_lay_ds_taobangno_khoitao()
            if(result.kyhoadon&&result.kyhoadon!='0'){
                this.ky_hoadon=moment(result.kyhoadon,'YYYYMMDD').format('MM/YYYY')
                this.kyhoadon=result.kyhoadon
            }else{
                this.$toast.error('Kỳ cước tiếp theo chưa được định nghĩa. Hãy tạo chu kỳ nợ trước!')
                return
            }
            // this.ky_hoadon='03/2022'
            // this.kyhoadon='20220301'
            if(result.chuky&&result.chuky.length>0){
                let kyhoadon_t=moment(this.ky_hoadon, 'MM/YYYY').format('YYYYMM')
                 console.log('kyhoadon_t', this.kyhoadon_t)
                this.chuky_no=result.chuky.filter(x=>x.chukyno.includes(kyhoadon_t)).map(x=>{
                    return {
                        chukyno:x.chukyno.substr(6)
                    }
                })

                console.log('chuky_no', this.chuky_no)
                this.chuky_no_selected= this.chuky_no[0].chukyno
            }else{
                this.$toast.error('Không có thông tin về chu kỳ!')
                return
            }
            if(result.data){
                this.dataSources=result.data.sort((a,b)=>a.thutu-b.thutu)
            }
        },
        onActionClick(action){
            if(action.id=='taodl'){
                if(this.kyhoadon==''){
                    return
                }
                this.$confirm('Bạn thật sự muốn tạo các bảng dữ liệu cho kỳ hóa đơn '+this.ky_hoadon+' không ?','Thông báo',{
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                }).then(() => {
                    let tenbang_arr=this.dataSources.map(x=>x.tenbang)
                    let data={
                        ky_hd: this.chuky_no_selected+moment(this.ky_hoadon,'MM/YYYY').format('MMYYYY'),
                        regread_ky: moment(this.kyhoadon,'YYYYMMDD').format('DD/MM/YYYY'),
                        tenbang_arr: tenbang_arr,
                        chuky: this.chuky_no_selected,
                        kyhoadon: moment(this.kyhoadon,'YYYYMMDD').format('DDMMYYYY'),
                        kyhoadon_moi: this.chuky_no_selected+moment(this.ky_hoadon,'MM/YYYY').add(1,'M').format('MMYYYY')
                    }
                    this.sp_ghilai_taobang_no(data)
                })
            }
        },
        async sp_lay_ds_taobangno_khoitao(){
            try{
                this.loading(true)
                let response = await CreateDebtTableAPI.sp_lay_ds_taobangno_khoitao(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã có lỗi khi khởi tạo dữ liệu')
                    }
                    return null
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã có lỗi khi khởi tạo dữ liệu')
                }
                return null
            }
        },
        async sp_ghilai_taobang_no(data){
            try{
                this.loading(true)
                let response = await CreateDebtTableAPI.sp_ghilai_taobang_no(this.axios, data)
                this.loading(false)
                //{"error":"200","error_code":"BSS-00000000","message":"Success","message_detail":null,"request_id":null,"data":"0"}
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.$toast.success('Tạo bảng thành công cho kỳ hóa đơn ' + this.kyhoadon + '. Kỳ cước: ' + this.chuky_no_selected.toString())
                    //push local storge tương ứng với register windows
                    this.setKyhoadon(moment(this.kyhoadon,'YYYYMMDD').format('MM/YYYY'))
                    this.setChuKy(this.chuky_no_selected!=null?this.chuky_no_selected.toString():'')
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi tạo bảng không thành công!')
                    }
                }
            }catch(e){
                console.log(e)
                this.loading(false)
                if(e.data&&e.data.message){
                    var message=e.data.message
                    if(e.data.message_detail){
                        message+=e.data.message_detail
                    }
                    this.$toast.error(message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    var message=e.response.data.message
                    if(e.response.data.message_detail){
                        message+=e.response.data.message_detail
                    }
                    this.$toast.error(message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi tạo bảng không thành công!')
                }
            }
        },


        

    }
}
</script>