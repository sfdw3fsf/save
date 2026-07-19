<template>
    <b-modal 
        ref="popupChonDoiTuongDungChinhThuc"
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
                    <span class="icon one-notepad"></span> Chọn đối tượng dùng chính thức
                </div>
                <div class="close -ap icon-close" @click="hideModal"></div>
            </div>
            <ActionTop :actions="actions" @onActionClick="onActionClick"/>
            <div class="popup-body">
                <div class="box-form">
                    <div class="legend-title">Thông tin thuê bao</div>
                    <div class="row">
                        <div class="col-12">
                            <div class="info-row">
                                <div class="key w120">Đối tượng TB</div>
                                <div class="value">
                                    <!-- <div class="select-custom">
                                        <select v-model="doituong_selected" class="form-control">
                                            <option v-for="item in doituong" :key="item.doituong_id" :value="item.doituong_id" >{{item.ten_dt}}</option>
                                        </select>
                                    </div> -->
                                    <multiselect
                                        :options="doituong"
                                        placeholder="Chọn đối tượng"
                                        :showLabels="false"
                                        :allowEmpty="false"
                                        @input="changeDoiTuong"
                                        :value="doituong_o_selected"
                                        label="ten_dt"
                                        track-by="doituong_id">
                                        <template #noOptions>
                                            Không tìm thấy thông tin
                                        </template>
                                        <template #noResult>
                                            Không tìm thấy thông tin
                                        </template>
                                        
                                    </multiselect>
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w120">Ngày NT</div>
                                <div class="value">
                                    <div class="input-icon-right">
                                        <k-date-picker v-model="ngay_nt"/>
                                    </div>
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w120">
                                    <div class="check-action">
                                        <input type="checkbox" v-model="check_ctv" @change="chonCTV" class="check">
                                        <span class="name">CTV</span>
                                    </div>
                                </div>
                                <div class="value">
                                    <input type="text" v-model="input_ctv" readonly :disabled="!check_ctv" class="form-control highlight">
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w120">
                                    <div class="check-action">
                                        <input type="checkbox" v-model="check_nguoigt" @change="chonNguoiGT" class="check">
                                        <span class="name">Người GT</span>
                                    </div>
                                </div>
                                <div class="value">
                                    <input type="text" v-model="input_nguoigt" readonly :disabled="!check_nguoigt"  class="form-control highlight">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Modal -->
            <ChonCongTacVienModal ref="chonCongTacVienModal" @accept="acceptCTV" @closePopup="closePopupCTV"/>
            <ChonNguoiGTModal ref="chonNguoiGTModal" @accept="acceptNguoiGT" @closePopup="closeNguoiGT"/>
        </div>  
    </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import moment from 'moment'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import MonitorTrialContractAPI from './MonitorTrialContractAPI'
import ChonCongTacVienModal from '../SurveyRequest/popups/ChonCongTacVien/ChonCongTacVienModal.vue'
import ChonNguoiGTModal from '../SurveyRequest/popups/ChonNguoiGT/ChonNguoiGTModal.vue'
export default {
    name:'ChonDoiTuongDungChinhThucModal',
    props:{
        data:{
            type:Object,
            default:()=>{}
        }
    },
    components:{
        ActionTop,
        KDatePicker,
        ChonCongTacVienModal,
        ChonNguoiGTModal
    },
    computed:{
        doituong_o_selected(){
            if(this.doituong_selected==null){
                return null
            }
            let doituong=this.doituong.find(x=>x.doituong_id==this.doituong_selected)
            return doituong!=undefined?doituong:null
        }
    },
    data(){
        return {
            actions:[
                {
                    id:'chapnhan',
                    name:'Chấp nhận',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_check-circle-08'
                }
            ],
            ngay_nt: moment(new Date()).format('DD/MM/YYYY'),
            doituong:[],
            doituong_selected:null,
            check_ctv:false,
            check_nguoigt:false,
            ctv_id:-1,
            nhanviengt_id:-1,
            input_ctv:'',
            input_nguoigt:''
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='chapnhan'){
                this.chapnhan()
            }else if(action.id=='dong'){
                this.hideModal()
            }
        },
        showModal() {
            this.$refs['popupChonDoiTuongDungChinhThuc'].show()
        },
        hideModal() {
            this.$refs['popupChonDoiTuongDungChinhThuc'].hide()
        },
        handleShowModal(){
            this.doituong=[]
            this.doituong_selected=null
            this.check_ctv=false,
            this.check_nguoigt=false
            this.ngay_nt=moment(new Date()).format('DD/MM/YYYY')
            this.ctv_id=-1,
            this.nhanviengt_id=-1,
            this.input_ctv='',
            this.input_nguoigt=''

            this.lay_ds_doituong()
        },
        chonCTV(){
            if(this.check_ctv){
                this.$refs.chonCongTacVienModal.showModal()
            }else{
                this.input_ctv=''
            }
        },
        acceptCTV(data){
            this.ctv_id=data.ctv_id
            this.input_ctv=data.ten_ctv
        },
        closePopupCTV(){
            this.check_ctv=false
            this.input_ctv=''
        },
        chonNguoiGT(){
            if(this.check_nguoigt){
                this.$refs.chonNguoiGTModal.showModal()
            }else{
                this.input_nguoigt=''
            }
        },
        closeNguoiGT(){
            this.check_nguoigt=false
            this.input_nguoigt=''
        },
        acceptNguoiGT(data){
            this.nhanviengt_id=data.nguoi_gt_id
            this.input_nguoigt=data.ten_nguoi_gt
        },
        changeDoiTuong(data){
            this.doituong_selected=data.doituong_id
        },
        async lay_ds_doituong(){
            try{
                this.loading(true)
                let response = await MonitorTrialContractAPI.lay_ds_doituong(this.axios)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.doituong=response.data.data
                    if(this.doituong.length>0){
                        this.doituong_selected=this.doituong[0].doituong_id
                    }
                }
            }catch(e){
                this.$toast.error('Không load được danh sách đối tượng!')
            }finally{
                this.loading(false)
            }
        },
        async chapnhan(){
            try{
                //check
                //DUNG_THU.DOITUONG_TB=190
                if(this.doituong_selected==190){
                    this.$toast.error('Hãy chọn đối tượng thuê bao khác \"Thuê bao dùng thử\"')
                    return
                }
                if(this.ngay_nt==null||this.ngay_nt.trim()==''){
                    this.$toast.error('Hãy chọn ngày nghiệm thu')
                    return
                }
                //Bỏ khúc dưới
                //Cho phép thay đổi ngày nghiệm thu (sử dụng) trong khoảng từ ngày đầu tiên của tháng hiện tại đến ngày hiện tại
                // var date=new Date()
                // date.setDate(date.getDate() - 1);
                // if(moment(this.ngay_nt,'DD/MM/YYYY').isBefore(date)){
                //     this.$toast.error('Ngày nghiệm thu không thể nhỏ hơn ngày hiện tại')
                //     return
                // }
                // Thiện sửa theo đề xuất của anh DB Nguyễn Tuấn Minh: if(this.data.datcoc==0)  ==> if(true)
                if(true){
                    let response = await MonitorTrialContractAPI.kt_dungthu_moi_v2(this.axios, this.data.hdtb_id, moment(this.ngay_nt,'DD/MM/YYYY').format('DD/MM/YYYY HH:mm:ss'))
                    if(response && response.data && response.data.error_code && response.data.error_code != 'BSS-00000000') {
                        this.$toast.error(response.data.message)
                        return
                    }
                }
                //check ctv vs nhân viên giới thiệu
                if(this.check_ctv&&this.input_ctv.trim()==''&&this.ctv_id==-1){
                    this.$toast.error('Bạn chưa chọn cộng tác viên!')
                    return
                }
                if(this.check_nguoigt&&this.input_nguoigt.trim()==''&&this.nhanviengt_id==-1){
                    this.$toast.error('Bạn chưa chọn người giới thiệu!')
                    return
                }

                //chuyển dùng chính thức
                let p_ctv_id=this.check_ctv?this.ctv_id:-1
                let p_nhanviengt_id=this.check_nguoigt?this.nhanviengt_id:-1
                let data={
                    hdtb_id: this.data.hdtb_id,
                    doituong_id: this.doituong_selected,
                    ngay_nt: moment(this.ngay_nt, 'DD/MM/YYYY').format('DD/MM/YYYY HH:mm:ss'),
                    ctv_id: p_ctv_id,
                    nhanviengt_id: p_nhanviengt_id,
                    loaitb_id:this.data.loaitb_id,
                    ma_tb:this.data.ma_tb,
                    trial_code:this.data.tocdo
                }

                this.$confirm('Bạn chắc chắn muốn thực hiện không?','Thông báo',{
                    confirmButtonText: 'Đồng ý',
                    cancelButtonText: 'Huỷ'
                }).then(() => {
                    //call 
                    this.chuyen_hopdong_chinhthuc(data)
                })
            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi')
                }
            }finally{
                this.loading(false)
            }
        },
        async chuyen_hopdong_chinhthuc(data){
            try{
                this.loading(true)
                let response=await MonitorTrialContractAPI.chuyen_hopdong_chinhthuc(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.$emit('ketqua',response.data.message)
                    this.hideModal()
                }else {
                    if(response && response.data && response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã có lỗi xảy ra')
                    }
                    
                }
            }catch(error){
                this.loading(false)
                if(error.data&&error.data.message){
                    this.$toast.error(error.data.message)
                }else if(error.response&&error.response.data&&error.response.data.message){
                    this.$toast.error(error.response.data.message)
                }else{
                    this.$toast.error('Đã có lỗi xảy ra')
                }
            }
        }

    }

};
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
