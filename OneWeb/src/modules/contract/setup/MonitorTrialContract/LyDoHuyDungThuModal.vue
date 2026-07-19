<template>
        <b-modal 
            ref="popupLyDoHuyDungThu"
            size="lg" 
            hide-header
            hide-header-close
            hide-footer
            body-class="p-0"
            no-close-on-backdrop
            @show="handleShowModal">
            <div class="modal-content popup-box">
                <div class="popup-header">
                    <div class="title">
                        <span class="icon one-notepad"></span> Chọn lý do hủy dịch vụ
                    </div>
                    <div class="close -ap icon-close" @click="hideModal"></div>
                </div>
                <ActionTop :actions="actions" @onActionClick="onActionClick"/>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="row">
                            <div class="col-12">
                                <div class="info-row">
                                    <div class="key w120">Nhóm huỷ</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select v-model="nhomhuy_selected" @change="changeNhomHuy" class="form-control">
                                                <option v-for="item in nhomhuy" :key="item.ID" :value="item.ID">{{item.NAME}}</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w120">Lý do huỷ</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select v-model="lydohuy_selected" class="form-control">
                                                <option v-for="item in lydohuy" :key="item.lydohuy_id" :value="item.lydohuy_id">{{item.lydohuy}}</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w120">
                                        <div class="check-action">
                                            <input type="checkbox" v-model="check_thanghn" class="check">
                                            <span class="name"> Tháng hết hạn</span>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <!-- <k-date-picker format="MM/YYYY" v-model="thang_hn" :disabled="!check_thanghn"/> -->
                                            <ejs-datepicker class="form-control bg-none bor0 fw6 text-red"
                                                placeholder="Chọn tháng" :format="'MM/y'" :start="'Year'"
                                                :enabled="check_thanghn"
                                                :depth="'Year'" :showClearButton="false" v-model="thang_hn"
                                                :allowEdit="false"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w120">Ghi chú huỷ</div>
                                    <div class="value">
                                        <textarea rows="3" v-model="input_ghichu" class="form-control" style="resize: none;"></textarea>
                                    </div>
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
import moment from 'moment'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import MonitorTrialContractAPI from './MonitorTrialContractAPI'
import Vue from 'vue';
import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars';
Vue.use(DatePickerPlugin);
import { L10n } from '@syncfusion/ej2-base'
L10n.load({
    'vi-VN': {
        'datetimepicker': { placeholder: 'Chọn tháng', today: 'Hôm nay' }
    }
})
export default {
    name:'LyDoHuyDungThuModal',
    props:{
        loaitb_id:{
            type:Number,
            default: -1
        }
    },
    components:{
        ActionTop,
        KDatePicker
    },
    data(){
        return{
            actions:[
                {
                    id:'chapnhan',
                    name:'Chấp nhận',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_check-circle-08'
                }
            ],
            thang_hn:moment(new Date()).format('MM/YYYY'),
            input_ghichu:'',
            nhomhuy:[],
            nhomhuy_selected:null,
            lydohuy:[],
            lydohuy_selected:null,
            check_thanghn:false
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='chapnhan'){
                this.chapNhan()  
            }else if(action.id=='thoat'){
                this.hideModal()
            }
        },
        showModal() {
            this.$refs['popupLyDoHuyDungThu'].show()
        },
        hideModal() {
            this.$refs['popupLyDoHuyDungThu'].hide()
        },
        changeNhomHuy(){
            this.lay_ds_lydo_huy(this.nhomhuy_selected!=null?this.nhomhuy_selected:-1)
        },
        handleShowModal(){
            this.thang_hn=moment(new Date()).format('MM/YYYY')
            this.input_ghichu=''
            this.nhomhuy=[]
            this.nhomhuy_selected=null
            this.lydohuy=[]
            this.lydohuy_selected=null
            this.check_thanghn=false
            
            this.initDuLieu()

        },
        chapNhan(){
            if(this.lydohuy_selected==null||this.lydohuy_selected==-1){
                this.$toast.error('Bạn phải chọn lý do hủy')
                return
            }
            
            const lydo=this.lydohuy.find(x=>x.lydohuy_id==this.lydohuy_selected)
            if(lydo==undefined){
                return
            }
            if(lydo.ma_ld=='1'){
                if(!this.check_thanghn){
                    this.$toast.error('Bạn phải chọn tháng hết hạn trả trước của nhà mạng đối thủ')
                    return
                }
            }else if(lydo.ma_ld=='2'){
                if(this.input_ghichu.trim()!=''){
                    this.$toast.error('Bạn phải nhập ghi chú hủy')
                    return
                }
            }

            var thang_ktck=0
            if(this.check_thanghn){
                thang_ktck=parseInt(moment(this.thang_hn,'MM/YYYY').format('yyyyMM'))
            }
            console.log('thang_ktck', thang_ktck)

            this.$emit('accept',{
                lydohuy_id:this.lydohuy_selected,
                thang_ktck:thang_ktck,
                ghichu_huy:this.input_ghichu.trim()
            })
            this.hideModal()

        },
        async initDuLieu(){
            await this.lay_ds_nhom_huy()
            await this.lay_ds_lydo_huy(this.nhomhuy_selected!=null?this.nhomhuy_selected:-1)
        },
        async lay_ds_nhom_huy(){
            try{
                this.loading(true)
                let response = await MonitorTrialContractAPI.lay_ds_nhom_huy(this.axios, this.loaitb_id!=-1?this.loaitb_id:58)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.nhomhuy=response.data.data
                    if(this.nhomhuy.length>0){
                        this.nhomhuy_selected=this.nhomhuy[0].ID
                    }
                }
            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Không load được danh sách nhóm huỷ!')
                }
            }finally{
                this.loading(false)
            }
        },
        async lay_ds_lydo_huy(nhomhuy_id){
            try{
                this.loading(true)
                let response = await MonitorTrialContractAPI.lay_ds_lydo_huy(this.axios, this.loaitb_id!=-1?this.loaitb_id:58, nhomhuy_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.lydohuy=response.data.data
                    if(this.lydohuy.length>0){
                        this.lydohuy_selected=this.lydohuy[0].lydohuy_id
                    }
                }
            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Không load được danh sách lý do huỷ!')
                }
            }finally{
                this.loading(false)
            }
        },

    }
}
</script>