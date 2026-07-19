<template>
  <b-modal
    ref="popupChiTietTienTheoKhoanMuc"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title"><span class="icon one-notepad"></span> Chi tiết tiền theo khoản mục</div>
            <div class="close -ap icon-close" @click="hideModal">
            </div>
        </div>
        <ActionTop :actions="actions" @onActionClick="onActionClick" :isMore="false"/>
        <div class="popup-body">
            <div class="box-form">
                <div class="legend-title">Chi tiết tiền theo khoản mục</div>
                <div class="row" v-for="(item, index) in ds_tien_km" :key="index">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w140">{{item.tentat_kmtt}}</div>
                            <div class="value">
                                <vue-numeric class="form-control tright" separator="," :value="item.tongtien" :disabled="item.flag==0" @blur="handleBlur($event, 1, index)" @change="changeTongTien($event, index)" placeholder="0" output-type="Number"></vue-numeric>
                                <!-- <input type="text" :value="item.tongtien"  @keypress="isNumber($event)" @blur="handleBlur($event, 1, index)" @change="changeTongTien($event, index)" :readonly="item.flag==1" class="form-control tright"> -->
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w40">Tiền</div>
                            <div class="value">
                                <vue-numeric class="form-control tright" separator="," :value="item.tien" :disabled="item.flag==0" @blur="handleBlur($event, 2, index)" @change="changeTien($event, index)" placeholder="0" output-type="Number"></vue-numeric>
                                <!-- <input type="text" :value="item.tien"  @keypress="isNumber($event)" @blur="handleBlur($event, 2, index)" @change="changeTien($event, index)" :readonly="item.flag==1" class="form-control tright" > -->
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w40">Vat</div>
                            <div class="value">
                                <vue-numeric class="form-control tright" separator="," :value="item.vat" disabled @change="changeVat($event, index)" placeholder="0" output-type="Number"></vue-numeric>
                                <!-- <input type="text" :value="item.vat"  @keypress="isNumber($event)" @change="changeVat($event, index)" class="form-control tright" readonly> -->
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
import ActionTop from '@/components/kylq_components/mirana/ActionTop.vue'
//WinUI.WinUILapHopDong.frmCT_Tien_KM
//https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=1906551485&range=B2820
export default {
    name:'ChiTietTienTheoKhoanMucModal',
    props:{
        loaihd_id:{
            type:Number,
            default:0
        },
        ds_tien_caidat:{
            type:Array,
            default:()=>[]
        }
    },
    components:{
        ActionTop
    },
    data(){
        return {
            actions:[
                {
                    id:'chapnhan',
                    name:'Chấp nhận',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_check-circle-08',
                    type:1
                } 
            ],
            ds_tien_km:[]
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='chapnhan'){
                this.$emit('accept', this.ds_tien_km)
                this.hideModal()
            }
        },
        showModal() {
            this.$refs["popupChiTietTienTheoKhoanMuc"].show()
        },
        hideModal() {
            this.$refs["popupChiTietTienTheoKhoanMuc"].hide()
        },
        handleShowModal(){
            this.ds_tien_km=[]
            setTimeout(()=>{
                this.initDuLieu()
            }, 500)
        },
        changeTongTien(event, index){
            this.ds_tien_km[index].tongtien=event.target.value
        },
        changeTien(event, index){
            this.ds_tien_km[index].tien=event.target.value
        },
        changeVat(event, index){
            this.ds_tien_km[index].vat=event.target.value
        },
        handleBlur(event, kieu, index){
            if(kieu==1){
                this.ds_tien_km[index].tien=(Math.round((Number(this.ds_tien_km[index].tongtien)*10/11) * 100+Number.EPSILON) / 100).toString()
                this.ds_tien_km[index].vat=(Math.round((Number(this.ds_tien_km[index].tongtien)/11) * 100+Number.EPSILON) / 100).toString()
            }else{
                this.ds_tien_km[index].vat=(Math.round((Number(this.ds_tien_km[index].tien)/10) * 100+Number.EPSILON) / 100).toString()
                this.ds_tien_km[index].tongtien=(Math.round((Number(this.ds_tien_km[index].tien)* 11/10) * 100+Number.EPSILON) / 100).toString()
            }
        },
        isNumber(evt) {
            evt = (evt) ? evt : window.event;
            var charCode = (evt.which) ? evt.which : evt.keyCode;

            var isDot=evt.target.value.indexOf('.')==-1
            if(isDot){
                if ((charCode > 31 && (charCode < 48 || charCode > 57))&& charCode !== 46) {
                    evt.preventDefault()
                } else {
                    return true
                }
            }else{
                if ((charCode > 31 && (charCode < 48 || charCode > 57))) {
                    evt.preventDefault()
                } else {
                    return true
                }
            }
        },
        async initDuLieu(){
            await this.hienthi_giaodien_kmtt()
            await this.hienthi_tien_km()
        },
        async hienthi_giaodien_kmtt(){
            //
            this.ds_tien_km=await this.lay_ds_tien_kmtt()
        },
        async hienthi_tien_km(){
            for(let i=0; i<this.ds_tien_km.length;i++){
                for(let j=0; j<this.ds_tien_caidat.length;j++){
                    if(this.ds_tien_km[i].khoanmuctt_id.toString()==this.ds_tien_caidat[j].khoanmuctt_id.toString()){
                        this.ds_tien_km[i].tien=this.ds_tien_caidat[j].tien.toString()
                        this.ds_tien_km[i].vat=this.ds_tien_caidat[j].vat.toString()
                        this.ds_tien_km[i].tongtien=Number(this.ds_tien_caidat[j].tien)+Number(this.ds_tien_caidat[j].vat)
                    }
                }
            }
        },
        async lay_ds_tien_kmtt(){
            try{
                this.loading(true)
                let response = await this.axios.post('/web-hopdong/lapdatmoi/lay_khoanmuc_tt_theo_loaihd',{
                    vloaihd_id: this.loaihd_id
                })
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
        }

    }
}
</script>