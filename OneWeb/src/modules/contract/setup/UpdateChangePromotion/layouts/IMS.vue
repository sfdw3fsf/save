<template>
    <div class="row">
        <div class="col-sm-6 col-12">
            <div class="box-form" style="min-height: 186px;">
                <div class="legend-title">Loại hình thuê bao cũ</div>
                <div class="row">
                    <div class="col-sm-8 col-12">
                        <div class="info-row">
                            <div class="key">Số máy/Acc</div>
                            <div class="value">
                                <div class="input-more-button">
                                    <button class="btn" @click="searchAccount">
                                        <span class="-ap icon-more_horiz"></span>
                                    </button>
                                    <input type="text" ref="refInputMaTB" class="form-control highlight" @keyup.enter="onEnterMaTB" :value="input_matb" @change="e=>input_matb=e.target.value">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Đối tượng</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="doituong_selected" class="form-control">
                                        <option v-for="item in ds_doituong" :key="item.doituong_id" :value="item.doituong_id">
                                            {{item.ten_dt}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-8 col-12">
                        <div class="info-row">
                            <div class="key">Trang bị</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="trangbi_selected" class="form-control">
                                        <option v-for="item in ds_trangbi" :key="item.trangbi_id" :value="item.trangbi_id">
                                            {{item.tentrangbi}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Thiết bị IMS</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="thietbi_ims_selected" class="form-control">
                                        <option v-for="item in ds_thietbi_ims" :key="item.thietbi_id" :value="item.thietbi_id">
                                            {{item.ten_tbi}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-12">
            <div class="box-form">
                <div class="legend-title">Loại hình thuê bao mới</div>
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">Loại hình</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select ref="cboLoaiHinhTBMoi" v-model="loaihinh_tb_moi_selected" @change="changeLoaiHinhTBMoi" :disabled="disableLoaiHinhTB" class="form-control">
                                        <option v-for="item in ds_loaihinh" :key="item.LOAITB_ID" :value="item.LOAITB_ID">
                                            {{item.LOAIHINH_TB}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Khu vực</div>
                            <div class="value">
                                <div class="input-more-button">
                                    <button class="btn" @click="clickKhuVuc">
                                        <span class="-ap icon-more_horiz"></span>
                                    </button>
                                    <input type="text" class="form-control highlight" @keypress="evt=>evt.preventDefault()" :value="input_khuvuc" @change="e=>input_khuvuc=e.target.value">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Đối tượng</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="doituong_moi_selected" @change="changeDoiTuongMoi" :disabled="!enableDoiTuongMoi" class="form-control">
                                        <option v-for="item in ds_doituong" :key="item.doituong_id" :value="item.doituong_id">
                                            {{item.ten_dt}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">Trang bị</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="trangbi_moi_selected" class="form-control" @change="changeTrangBiMoi">
                                        <option v-for="item in ds_trangbi" :key="item.trangbi_id" :value="item.trangbi_id">
                                            {{item.tentrangbi}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Thiết bị IMS</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="thietbi_ims_moi_selected" class="form-control">
                                        <option v-for="item in ds_thietbi_ims" :key="item.thietbi_id" :value="item.thietbi_id">
                                            {{item.ten_tbi}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Mã TN</div>
                            <div class="value">
                                <div class="input-more-button" >
                                    <button class="btn" @click="clickMaTN">
                                        <span class="-ap icon-more_horiz"></span>
                                    </button>
                                    <input type="text" class="form-control highlight" readonly ref="inputMaTN" :value="input_matn" @change="e=>input_matn=e.target.value">
                                </div>
                            </div>
                        </div>
                    </div>
                
                </div>
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">Chi tiết DV</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="chitiet_dv_selected" class="form-control">
                                        <option v-for="item in ds_chitiet_dv" :key="item.ctdv_id" :value="item.ctdv_id">
                                            {{item.chitiet_dv}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">SL cuộc gọi</div>
                            <div class="value">
                                <input type="text" ref="inputSLCuocGoi" @keypress="isNumber($event)" class="form-control highlight" :value="input_sl_cuocgoi" @change="e=>input_sl_cuocgoi=e.target.value">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">IP</div>
                            <div class="value">
                                <input type="text" ref="inputIP" class="form-control highlight" :value="input_ip" @change="e=>input_ip=e.target.value">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal -->
        <SearchAccountModal ref="searchAccountModal" @form-close="formCloseSearchAccount"/>
        <ThayDoiKhuVucQuanLyThueBaoModal ref="thayDoiKhuVucQuanLyThueBaoModal"
            :data="dataPopupKhuVuc"
            @accept="onAcceptKhuVuc"
            @success_update="successUpdateThayDoiKhuVucQuanLy"/>
        <DSTBCungDoiCapModal ref="dSTBCungDoiCapModal"
            :dulieu="dulieuPopupMaTN"
            @form-close="formCloseDSTBCungDoiCap"
        />
        <DSTBLapKemModal ref="dSTBLapKemModal"
            :dulieu="dulieuPopupMaTN"
            @form-close="formDSTBLapKem"
            
        />
    </div>
</template>
<script>
import SearchAccountModal from '../../SurveyRequest/popups/SearchAccountModal.vue'
import ThayDoiKhuVucQuanLyThueBaoModal from '../../SurveyRequest/popups/ThayDoiKhuVucQuanLyThueBao/ThayDoiKhuVucQuanLyThueBaoModal.vue'
import ChangeSubsTypeAPI from '../ChangeSubsTypeAPI'
import DSTBCungDoiCapModal from '../popups/DSTBCungDoiCapModal.vue'
import DSTBLapKemModal from '../popups/DSTBLapKemModal.vue'
export default {
    name:'IMS',
    props:{
        khachhang_id:{
            type:Number,
            default:0
        },
        hdkh_id:{
            type:Number,
            default:0
        },
        khuvuc_id:{
            type:Number,
            default:0
        },
        donviql_id:{
            type:Number,
            default:0
        },
        hdtb_id:{
            type:Number,
            default:0
        },
        thuebao_id:{
            type:Number,
            default:0
        },
        dataDiaChiLD:{
            type:Object,
            default:()=>{
                return {
                    diachi_id:0,
                    tinh_id:0,
                    quan_id:0,
                    phuong_id:0,
                    pho_id:0,
                    ap_id:0,
                    khu_id:0,
                    dacdiem_id:0,
                    sonha:'',
                    //
                    block:'',
                    tang:'',
                    sophong:'',
                    motathem:'',
                    diachi:'',

                    kinhdo:0,
                    vido:0
                }
            }
        },
        checkQuyen:{
            type:Number,
            default:0
        },
        iKieu:{
            type:Number,
            default:0
        }
    },
    components:{
        SearchAccountModal, ThayDoiKhuVucQuanLyThueBaoModal,
        DSTBCungDoiCapModal, DSTBLapKemModal
    },
    data(){
        return {
            input_matb:'',
            ds_doituong:[],
            doituong_selected:null,
            ds_trangbi:[],
            trangbi_selected:null,
            ds_thietbi_ims:[],
            thietbi_ims_selected:null,

            // thôgn tin mới
            ds_loaihinh:[],
            loaihinh_tb_moi_selected:null,
            disableLoaiHinhTB:false,
            input_khuvuc:'',
            khuvuc_selected:null,
            dataPopupKhuVuc:{
                quan_id:0,
                phuong_id:0,
                pho_id:0,
                ap_id:0,
                khu_id:0,
                dacdiem_id:0,
                khuvuc_id:0,
                donviql_id:0,
                hdtb_id:0,
                thuebao_id:0
            },
            doituong_moi_selected:null,
            trangbi_moi_selected:3, //Default khách hàng tự trang bị
            thietbi_ims_moi_selected:null,
            input_matn:'',
            //biến tạm cho giống code
            input_madoicap:'',
            ds_chitiet_dv:[
                {
                    ctdv_id:0,
                    chitiet_dv:''
                },
                {
                    ctdv_id:2,
                    chitiet_dv:'Nền Internet'
                },
                {
                    ctdv_id:3,
                    chitiet_dv:'Đường riêng'
                },
            ],
            chitiet_dv_selected:0,
            input_sl_cuocgoi:'',
            input_ip:'',
            dulieuPopupMaTN:{},
            enableDoiTuongMoi:true

        }
    },
    methods:{
        searchAccount(){
            this.$refs.searchAccountModal.showModal()
        },
        formCloseSearchAccount(data){
            this.input_matb=data.ma_tb
        },
        clickKhuVuc(){
            this.dataPopupKhuVuc.quan_id=this.dataDiaChiLD.quan_id
            this.dataPopupKhuVuc.phuong_id=this.dataDiaChiLD.phuong_id
            this.dataPopupKhuVuc.pho_id=this.dataDiaChiLD.pho_id
            this.dataPopupKhuVuc.ap_id=this.dataDiaChiLD.ap_id
            this.dataPopupKhuVuc.khu_id=this.dataDiaChiLD.khu_id
            this.dataPopupKhuVuc.dacdiem_id=this.dataDiaChiLD.dacdiem_id
            this.dataPopupKhuVuc.khuvuc_id=this.khuvuc_id
            this.dataPopupKhuVuc.donviql_id=this.donviql_id
            //sửa cho hiển thị địa chỉ theo hợp đồng thuê bao trước
            if(this.hdtb_id!=0){
                this.dataPopupKhuVuc.hdtb_id=this.hdtb_id
            }else{
                this.dataPopupKhuVuc.thuebao_id=this.thuebao_id
            }
            this.$refs.thayDoiKhuVucQuanLyThueBaoModal.showModal()
        },
        focusMaTB(){
            this.$nextTick(()=>{
                this.$refs.refInputMaTB.focus()
            })
        },
        onEnterMaTB(){
            if(this.input_matb.trim()==''){
                return
            }
            this.$emit('onEnterMaTB', this.input_matb.trim())
        },
        async getLoaiHinhTB_ID_Moi(){
            return this.loaihinh_tb_moi_selected!=null?this.loaihinh_tb_moi_selected:0
        },
        async changeDichVu(dichvuvt_id){
            
        },
        async changeLoaiHinhTBCu(loaihinhtb_id){
    
        },
        async getMaTb(){
            return this.input_matb.trim()
        },
        async changeLoaiHinhTBMoi(){
            this.$emit('changeLoaiHinhTBMoi', this.loaihinh_tb_moi_selected)
        },
        onAcceptKhuVuc(data){
            this.khuvuc_selected=data
            this.input_khuvuc = this.khuvuc_selected.ma_kv
            this.$emit('onAcceptKhuVuc', this.khuvuc_selected)
        },
        async HienThiTTMoRong_DB(item){
            console.log('HienThiTTMoRong_DB IMS', item.TRANGBI_ID)
            if(item.TRANGBI_ID!=null){
                this.trangbi_selected=item.TRANGBI_ID
            }else{
                this.trangbi_selected=null
            }
            if(item.THIETBI_ID!=null){
                this.thietbi_ims_selected=item.THIETBI_ID
            }else{
                this.thietbi_ims_selected=null
            }
        },
        async HienThiTTMoRong_HD(item){
            console.log('HienThiTTMoRong_HD', item)
            if(item.TRANGBI_ID!=null){
                this.trangbi_moi_selected=item.TRANGBI_ID
            }else{
                this.trangbi_moi_selected=null
            }
            if(item.THIETBI_ID!=null){
                this.thietbi_ims_moi_selected=item.THIETBI_ID
            }else{
                this.thietbi_ims_moi_selected=null
            }
            this.input_matn=item.MATB_TN!=null?item.MATB_TN.toString():''
            this.input_sl_cuocgoi=item.SL_CUOCGOI!=null?item.SL_CUOCGOI.toString():''
            this.input_madoicap=item.MADOICAP!=null?item.MADOICAP:''
            this.input_ip=item.IP!=null?item.IP.toString():''
            if(item.CTDV_ID!=null){
                this.chitiet_dv_selected=item.CTDV_ID
            }else{
                this.chitiet_dv_selected=0
            }
        },
        async HienThiTT_DanhBaV2(item){
            this.doituong_selected=item.doituong_id!=null?item.doituong_id:null
            this.doituong_moi_selected=item.doituong_id!=null?item.doituong_id:null

            this.input_khuvuc=item.ten_kv!=undefined?item.ten_kv:''
            await this.changeDoiTuongMoi()
        },
        successUpdateThayDoiKhuVucQuanLy(){
            this.$emit('successUpdateThayDoiKhuVucQuanLy')
        },
        getLoaiHinhTBMoi(){
            const loaihinh=this.ds_loaihinh.find(x=>x.LOAITB_ID==this.loaihinh_tb_moi_selected)
            return loaihinh!=undefined?loaihinh.LOAIHINH_TB:''
        },
        focusSLCuocGoi(){
            this.$refs.inputSLCuocGoi.focus()
        },
        focusInputIP(){
            this.$refs.inputIP.focus()
        },
        focusInputMaTN(){
            this.$refs.inputMaTN.focus()
        },
        focusLoaiHinhTBMoi(){
            this.$refs.cboLoaiHinhTBMoi.focus()
        },
        clickMaTN(){
            this.$emit('clickMaTN_IMS')
        },
        showDSTBCungDoiCap(dulieu){
            Object.assign(this.dulieuPopupMaTN, dulieu)
            setTimeout(()=>{
                this.$refs.dSTBCungDoiCapModal.showModal()
            }, 500)
        },
        formCloseDSTBCungDoiCap(value){
            if(value==null){
                return
            }
            console.log('formCloseDSTBCungDoiCap', value)
            this.input_matn=value.MA_TB
            this.input_madoicap=value.MADOICAP
        },
        showDSTBLapKem(dulieu){
            Object.assign(this.dulieuPopupMaTN, dulieu)
            setTimeout(()=>{
                this.$refs.dSTBLapKemModal.showModal()
            }, 500)
        },
        formDSTBLapKem(value){
            if(value==null){
                return
            }
            console.log('formDSTBLapKem', value)
            this.input_matn=value.MA_TB
            this.input_madoicap=value.MADOICAP
        },
        async HienThiTTHopDongTB_B2(item, result){
            this.doituong_moi_selected=item.DOITUONG_ID!=null?item.DOITUONG_ID:null
            if(result.DOITUONG_ID_CU!='-1'&&result.DOITUONG_ID_CU!=''){
                this.doituong_selected=result.DOITUONG_ID_CU
            }else{
                this.doituong_selected=null
            }
            this.input_khuvuc=result.TEN_KV!=null?result.TEN_KV:''
        },
        changeTrangBiMoi(){
            this.$emit('changeTrangBiMoi', this.trangbi_moi_selected)
        },
        async changeDoiTuongMoi(){
            this.$emit('changeDoiTuongMoi', this.doituong_moi_selected)
        },
        async changeLoaiHinhTBMoi(){
            this.$emit('changeLoaiHinhTBMoi', this.loaihinh_tb_moi_selected)
        },
        async setDsLoaiHinhTbMoi(ds, selectedId){
            console.log('setDsLoaiHinhTbMoi', ds)
            this.ds_loaihinh=ds
            this.loaihinh_tb_moi_selected=selectedId
            await this.changeLoaiHinhTBMoi()
            
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
        async setLoaiHinhTbMoi(loaihinh_tb_id){
            this.loaihinh_tb_moi_selected=loaihinh_tb_id
            //handle change 
            await this.changeLoaiHinhTBMoi()
            
        },
        async lay_danhmuc_cdlh_tt_chung(chuoi){
            try{
                let response = await ChangeSubsTypeAPI.lay_danhmuc_cdlh_tt_chung(this.axios, chuoi)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return null
                }
            }catch(e){
                return null
            }
        },
        async initDuLieu(ds_loaihinh_tb){
            console.log('initDuLieu IMS')
            await this.clearDulieu()
            this.ds_loaihinh=ds_loaihinh_tb.filter(x=>x.DICHVUVT_ID==11)
            if(this.ds_loaihinh.length>0){
                this.loaihinh_tb_moi_selected=this.ds_loaihinh[0].LOAITB_ID
                await this.changeLoaiHinhTBMoi()
            }
            let result = await this.lay_danhmuc_cdlh_tt_chung('TRANG_BI|DOITUONG|THIETBI_IMS')
            if(result){
                this.ds_trangbi=result.trangbi
                if(this.ds_trangbi.length>0){
                    this.trangbi_selected=this.ds_trangbi[0].trangbi_id
                    this.trangbi_moi_selected=this.ds_trangbi[0].trangbi_id
                }
                this.ds_doituong=result.doituong
                if(this.ds_doituong.length>0){
                    this.doituong_selected=this.ds_doituong[0].doituong_id
                    this.doituong_moi_selected=this.ds_doituong[0].doituong_id
                }
                this.ds_thietbi_ims=result.thietbi_ims
                if(this.ds_thietbi_ims.length>0){
                    this.thietbi_ims_selected=this.ds_thietbi_ims[0].thietbi_id
                    this.thietbi_ims_moi_selected=this.ds_thietbi_ims[0].thietbi_id
                }
            }
        },
        visibleToolStripMenuAndControl(visible){
            if((this.iKieu==1||this.iKieu==2)&&this.checkQuyen==0){
                this.enableDoiTuongMoi=visible
            } 
        },
        async clearDulieu(){
            this.input_matb=''
            this.disableLoaiHinhTB=false
            this.input_khuvuc=''
            this.khuvuc_selected=null
            this.dataPopupKhuVuc={
                quan_id:0,
                phuong_id:0,
                pho_id:0,
                ap_id:0,
                khu_id:0,
                dacdiem_id:0,
                khuvuc_id:0,
                donviql_id:0,
                hdtb_id:0,
                thuebao_id:0
            }
            
            this.input_matn=''
            this.input_madoicap=''
            this.chitiet_dv_selected=0
            this.input_sl_cuocgoi=''
            this.input_ip=''

            if(this.ds_trangbi.length>0){
                this.trangbi_selected=3
                this.trangbi_moi_selected=3
            }
            if(this.ds_doituong.length>0){
                this.doituong_selected=this.ds_doituong[0].doituong_id
                this.doituong_moi_selected=this.ds_doituong[0].doituong_id
            }
            if(this.ds_thietbi_ims.length>0){
                this.thietbi_ims_selected=this.ds_thietbi_ims[0].thietbi_id
                this.thietbi_ims_moi_selected=this.ds_thietbi_ims[0].thietbi_id
            }
            this.enableDoiTuongMoi=true
        }

    }
}
</script>