<template>
    <div class="row">
        <div class="col-sm-6 col-12">
            <div class="box-form">
                <div class="legend-title">Loại hình thuê bao cũ</div>
                <div class="row">
                    <div class="col-sm-8 col-12">
                        <div class="info-row">
                            <div class="key w100">Số máy/Acc</div>
                            <div class="value">
                                <div class="input-more-button">
                                    <button class="btn" @click="searchAccount">
                                        <span class="-ap icon-more_horiz"></span>
                                    </button>
                                    <input type="text" ref="refInputMaTB" @keyup.enter="onEnterMaTB" class="form-control bold highlight" :value="input_matb" @change="e=>input_matb=e.target.value">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Đối tượng</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_doituong_cu">
                                    <select2 :settings="{ dropdownParent: $refs['ds_doituong_cu'] }" ref="cboDoiTuongCu"
                                        v-model="doituong_selected" class="select2"
                                        :options="ds_doituong.map(e=> ({id: e.doituong_id, text: e.ten_dt}))"
                                        >
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-8 col-12">
                        <div class="info-row">
                            <div class="key w100">Tốc độ</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_tocdo_cu">
                                    <select2 :settings="{ dropdownParent: $refs['ds_tocdo_cu'] }" ref="cboTocDoCu"
                                        v-model="tocdo_selected" class="select2"
                                        :options="ds_tocdo.map(e=> ({id: e.tocdo_id, text: e.thuonghieu}))"
                                        :disabled="disableGoiCuocADSLCu"
                                        >
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Mức cước</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_muccuoc_cu">
                                    <select2 :settings="{ dropdownParent: $refs['ds_muccuoc_cu'] }" ref="cboMucCuocCu"
                                        v-model="muccuoc_selected" class="select2"
                                        :options="ds_muccuoc.map(e=> ({id: e.muccuoc_id, text: e.tenmuc}))"
                                        >
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w100">Tốc độ thực</div>
                    <div class="value">
                        <div class="select-custom" ref="ds_tocdothuc_cu">
                            <select2 :settings="{ dropdownParent: $refs['ds_tocdothuc_cu'] }" ref="cboTocDoThucCu"
                                v-model="tocdothuc_selected" class="select2"
                                :options="ds_tocdo.map(e=> ({id: e.tocdo_id, text: e.thuonghieu}))"
                                :disabled="disableTocDoADSLCuThuc"
                                >
                            </select2>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-8 col-12">
                        <div class="info-row">
                            <div class="key w100">Loại thiết bị</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_loaithietbidc">
                                    <select2 :settings="{ dropdownParent: $refs['ds_loaithietbidc'] }" ref="cboLoaiThietBiDCCu"
                                        v-model="loaithietbi_selected" class="select2"
                                        :options="ds_loaithietbi.map(e=> ({id: e.thietbidc_id, text: e.ten_tbi}))"
                                        >
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Trang bị</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_trangbi_cu">
                                    <select2 :settings="{ dropdownParent: $refs['ds_trangbi_cu'] }" ref="cboTrangBiCu"
                                        v-model="trangbi_selected" class="select2"
                                        :options="ds_trangbi.map(e=> ({id: e.trangbi_id, text: e.tentrangbi}))">
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w100">Thông tin TC cũ</div>
                    <div class="value">
                        <textarea name="" class="form-control" :value="input_thongtin_tc_cu" @change="e=>input_thongtin_tc_cu=e.target.value" style="height: 100px;resize: none;"></textarea>
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
                                <div class="select-custom" ref="ds_loaihinh_moi">
                                    <select2 :settings="{ dropdownParent: $refs['ds_loaihinh_moi'] }" ref="cboLoaiHinhTBMoi"
                                        v-model="loaihinh_tb_moi_selected" class="select2"
                                        :options="ds_loaihinh_moi.map(e=> ({id: e.LOAITB_ID, text: e.LOAIHINH_TB}))"
                                        :disabled="disableLoaiHinhTB"
                                        @change="changeLoaiHinhTBMoi"
                                        >
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">Khu vực</div>
                            <div class="value">
                                <div class="input-more-button">
                                    <button class="btn" @click="clickKhuVuc">
                                        <span class="-ap icon-more_horiz"></span>
                                    </button>
                                    <input type="text" class="form-control bold" @keypress="evt=>evt.preventDefault()" :value="input_khuvuc" @change="e=>input_khuvuc=e.target.value">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Đối tượng</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_doituong">
                                    <select2 :settings="{ dropdownParent: $refs['ds_doituong'] }" ref="cboDoiTuong"
                                        v-model="doituong_moi_selected" class="select2"
                                        :options="ds_doituong.map(e=> ({id: e.doituong_id, text: e.ten_dt}))"
                                        :disabled="!enableDoiTuongMoi"
                                        @change="changeDoiTuongMoi"
                                        >
                                    </select2>
                                </div>
                            </div>
                            
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-8 col-12">
                        <div class="info-row">
                            <div class="key">Tốc độ</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_tocdo_moi">
                                    <select2 :settings="{ dropdownParent: $refs['ds_tocdo_moi'] }" ref="cboTocDoMoi"
                                        v-model="tocdo_moi_selected" class="select2"
                                        :options="ds_tocdo_moi.map(e=> ({id: e.tocdo_id, text: e.thuonghieu}))"
                                        :disabled="disableGoiCuocADSLMoi"
                                        @change="changeGoiCuocAdsl">
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">
                                Mức cước
                            </div>
                            <div class="value">
                                <div class="select-custom" ref="ds_muccuoc">
                                    <select2 :settings="{ dropdownParent: $refs['ds_muccuoc'] }" ref="cboMucCuoc"
                                        v-model="muccuoc_moi_selected" class="select2"
                                        :options="ds_muccuoc.map(e=> ({id: e.muccuoc_id, text: e.tenmuc}))"
                                        @change="changeMucCuoc">
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
    
                </div>
                <div class="row">
                    <div class="col-sm-8 col-12">
                        <div class="info-row">
                            <div class="key">
                                <div class="check-action">
                                    <input type="checkbox" class="check" :disabled="!enabledCheckTocDoThuc" v-model="checkTocDoThuc">
                                    <span class="name">Tốc độ thực</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom" ref="ds_tocdo_thuc">
                                    <select2 :settings="{ dropdownParent: $refs['ds_tocdo_thuc'] }" ref="cboTocDoThuc"
                                        v-model="tocdo_thuc_selected" class="select2"
                                        :options="ds_tocdo_thuc_moi.map(e=> ({id: e.tocdo_id, text: e.thuonghieu}))"
                                        :disabled="!checkTocDoThuc">
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Mã TN</div>
                            <div class="value">
                                <div class="input-more-button">
                                    <button class="btn" @click="clickMaTN" :disabled="!enabledMaTN" :class="{disabledButton: !enabledMaTN}">
                                        <span class="-ap icon-more_horiz"></span>
                                    </button>
                                    <input type="text" class="form-control highlight" ref="inputMaTN" :value="input_matn" @change="e=>input_matn=e.target.value">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-8 col-12">
                        <div class="info-row">
                            <div class="key">
                                Loại thiết bị
                            </div>
                            <div class="value">
                                <div class="select-custom" ref="ds_thietbidc">
                                    <select2 :settings="{ dropdownParent: $refs['ds_thietbidc'] }" ref="cboThietBiDC"
                                        v-model="loaithietbi_moi_selected" class="select2"
                                        :options="ds_loaithietbi.map(e=> ({id: e.thietbidc_id, text: e.ten_tbi}))"
                                        :disabled="!enableLoaiTBi">
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Trang bị</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_trangbi">
                                    <select2 :settings="{ dropdownParent: $refs['ds_trangbi'] }" ref="cboTrangbi"
                                        v-model="trangbi_moi_selected" class="select2"
                                        :options="ds_trangbi.map(e=> ({id: e.trangbi_id, text: e.tentrangbi}))"
                                        @change="changeTrangBiMoi"
                                        >
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="info-row">
                    <div class="key">
                        TTTC
                    </div>
                    <div class="value">
                        <div class="input-more-button">
                            <button class="btn" @click="clickTTTC" :disabled="!btnThongTinTCEnabled" :class="{disabledButton: !btnThongTinTCEnabled}">
                                <span class="-ap icon-more_horiz"></span>
                            </button>
                            <textarea ref="inputTTTC" readonly :value="input_tttc" @change="e=>input_tttc=e.target.value" class="form-control" style="height: 100px;resize: none;"></textarea>
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
            @form-close="formDSTBLapKem"/>
        <ThongTinTCModal ref="thongTinTCModal"
            :dulieu="dulieuPopupTTTC"
            @form-close="formCloseTTTC"
        />
    </div>
</template>
<script>
import SearchAccountModal from '../../SurveyRequest/popups/SearchAccountModal.vue'
import ThayDoiKhuVucQuanLyThueBaoModal from '../../SurveyRequest/popups/ThayDoiKhuVucQuanLyThueBao/ThayDoiKhuVucQuanLyThueBaoModal.vue'
import {DichVuVienThong, LoaiHinhTB, TRANGBI} from '../ThamSo'
import ChangeSubsTypeAPI from '../ChangeSubsTypeAPI'
//import { DichVuVienThong, LoaiHinhTB } from '../../InstallNewSubs/Enums'
import DSTBCungDoiCapModal from '../popups/DSTBCungDoiCapModal.vue'
import DSTBLapKemModal from '../popups/DSTBLapKemModal.vue'
import ThongTinTCModal from '../popups/ThongTinTCModal.vue'
export default {
    name:'BangRongCoDinh',
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
        SearchAccountModal,
        ThayDoiKhuVucQuanLyThueBaoModal,
        DSTBCungDoiCapModal,
        DSTBLapKemModal,
        ThongTinTCModal
    },
    data(){
        return{
            input_matb:'',
            ds_doituong:[],
            doituong_selected:null,
            ds_tocdo:[],
            tocdo_selected:null,
            disableGoiCuocADSLCu:false,
            ds_muccuoc:[],
            muccuoc_selected:null,
            tocdothuc_selected:null,
            disableTocDoADSLCuThuc:false,
            ds_loaithietbi:[],
            loaithietbi_selected:null,
            ds_trangbi:[], 
            trangbi_selected:null,
            input_thongtin_tc_cu:'',

            //thông tin mới
            ds_loaihinh_moi:[],
            loaihinh_tb_moi_selected:null,
            ds_tocdo_moi:[],
            ds_tocdo_thuc_moi:[],
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
                hdtb_id:0
            },
            doituong_moi_selected:null,
            tocdo_moi_selected:null,
            muccuoc_moi_selected:null,
            disableGoiCuocADSLMoi:false,
            checkTocDoThuc:false,
            tocdo_thuc_selected:null,
            loaithietbi_moi_selected:null,
            trangbi_moi_selected:3, //Default khách hàng tự trang bị
            input_matn:'',
            enabledMaTN:true,
            input_tttc:'',
            btnThongTinTCEnabled:true,
            //không gán làm biến theo giống code cũ
            chkTucThi:true,

            enabledCheckTocDoThuc:false,

            //Thông tin ngoài form
            input_email_adsl_cu:'',
            input_email_adsl:'',
            input_matkhau_adsl_cu:'',
            input_matkhau_adsl:'',
            loaihinh_tb_cu:0,
            input_soao:'',
            checkTucThi:true,
            input_madoicap_adsl:'',
            dulieuPopupMaTN:{},
            dulieuPopupTTTC:{},
            enableDoiTuongMoi:true,
            enableLoaiTBi:true

        }
    },
    methods:{
        searchAccount(){
            this.$refs.searchAccountModal.showModal()
        },
        formCloseSearchAccount(data){
            if(data&&data.ma_tb){
                this.input_matb=data.ma_tb
                this.onEnterMaTB()
            }
        },
        onEnterMaTB(){
            if(this.input_matb.trim()==''){
                return
            }
            this.$emit('onEnterMaTB', this.input_matb.trim())
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
        onAcceptKhuVuc(data){
            this.khuvuc_selected=data
            this.input_khuvuc= this.khuvuc_selected.ma_kv
            this.$emit('onAcceptKhuVuc', this.khuvuc_selected)
        },
        async getMaTb(){
            return this.input_matb.trim()
        },
        async changeLoaiHinhTBMoi(){
            this.$emit('changeLoaiHinhTBMoi', this.loaihinh_tb_moi_selected)
        },
        successUpdateThayDoiKhuVucQuanLy(){
            this.$emit('successUpdateThayDoiKhuVucQuanLy')
        },
        changeTrangBiMoi(){
            this.$emit('changeTrangBiMoi', this.trangbi_moi_selected)
        },
        async changeDoiTuongMoi(){
            this.$emit('changeDoiTuongMoi', this.doituong_moi_selected)
        },
        getLoaiHinhTBMoi(){
            const loaihinh=this.ds_loaihinh_moi.find(x=>x.LOAITB_ID==this.loaihinh_tb_moi_selected)
            return loaihinh!=undefined?loaihinh.LOAIHINH_TB:''
        },
        async setDsLoaiHinhTbMoi(ds, selectedId){
            this.ds_loaihinh_moi=ds
            this.loaihinh_tb_moi_selected=selectedId
            console.log('setDsLoaiHinhTbMoi ds_loaihinh_moi', this.ds_loaihinh_moi)
            console.log('setDsLoaiHinhTbMoi loaihinh_tb_moi_selected', this.loaihinh_tb_moi_selected)
            await this.changeLoaiHinhTBMoi()
        },
        focusInputTTTC(){
            this.$refs.inputTTTC.focus()
        },
        focusInputMaTN(){
            this.$refs.inputMaTN.focus()
        },
        focusLoaiHinhTBMoi(){
            this.$refs.cboLoaiHinhTBMoi.focus()
        },
        clickMaTN(){
            this.$emit('clickMaTN_ADSL')
        },
        clickTTTC(){
            this.$emit('clickTTTC')
        },
        showThongTinTC(dulieu){
            Object.assign(this.dulieuPopupTTTC, dulieu)
            setTimeout(()=>{
                this.$refs.thongTinTCModal.showModal()
            },500)
        },
        formCloseTTTC(value){
            console.log('formCloseTTTC', value)
            this.input_tttc=value!=null?value:''
        },
        showDSTBLapKem(dulieu){
            Object.assign(this.dulieuPopupMaTN, dulieu)
            setTimeout(()=>{
                this.$refs.dSTBLapKemModal.showModal()
            }, 500)
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
            this.input_matn=value.MA_TB.toString()
            this.input_madoicap_adsl=value.MADOICAP.toString()
        },
        formDSTBLapKem(value){
            if(value==null){
                return
            }
            console.log('formDSTBLapKem', value)
            this.input_matn=value.MA_TB.toString()
            this.input_madoicap_adsl=value.MADOICAP.toString()
        },
        async HienThiTTHopDongTB_B2(item, result){
            console.log('HienThiTTHopDongTB_B2')
            this.doituong_moi_selected=item.DOITUONG_ID!=null?item.DOITUONG_ID:null
            if(result.DOITUONG_ID_CU!='-1'&&result.DOITUONG_ID_CU!=''){
                this.doituong_selected=result.DOITUONG_ID_CU
            }else{
                this.doituong_selected=null
            }
            this.input_khuvuc=result.TEN_KV!=null?result.TEN_KV:''
        },
        async HienThiTTMoRong_DB(item){
            console.log('HienThiTTMoRong_DB ADSL', item)
            this.input_email_adsl_cu=item.EMAIL!=null?item.EMAIL:''
            if(item.EMAIL==null){
                this.input_email_adsl=this.input_matb.trim()+'@vnn.vn'
            }else{
                this.input_email_adsl=item.EMAIL.toString()
            }
            this.tocdothuc_selected=-1
            if(item.TOCDOTHUC_ID!=null){
                this.tocdothuc_selected=item.TOCDOTHUC_ID
            }else{
                this.tocdothuc_selected=-1
            }
            if(item.TOCDO_ID!=null){
                this.tocdo_selected=item.TOCDO_ID
                if(this.loaihinh_tb_cu==LoaiHinhTB.INTERNET_MYTV){
                    //this.tocdo_moi_selected=item.TOCDO_ID
                    if(this.tocdo_moi_selected!=item.TOCDO_ID){
                        this.tocdo_moi_selected=item.TOCDO_ID
                    }else{
                        this.changeGoiCuocAdsl()
                    }
                }
            }
            if(item.MUCCUOC_ID!=null){
                this.muccuoc_selected=item.MUCCUOC_ID
            }
            if(item.THIETBIDC_ID!=null){
                this.loaithietbi_selected=item.THIETBIDC_ID
            }
            if(item.TRANGBI_ID!=null){
                this.trangbi_selected=item.TRANGBI_ID
                this.trangbi_moi_selected=item.TRANGBI_ID
            }
            this.input_matkhau_adsl_cu=item.PASSWORD!=null?item.PASSWORD:''
            this.input_matkhau_adsl=this.input_matkhau_adsl_cu
            this.input_thongtin_tc_cu=item.THONGTIN_TC!=null?item.THONGTIN_TC:''

            this.input_soao=item.MA_LT!=null?item.MA_LT:''

        },
        async HienThiTTMoRong_HD(item){
            console.log('HienThiTTMoRong_HD ADSL',item)
            this.input_email_adsl=item.EMAIL!=null?item.EMAIL.toString():''
            this.input_matkhau_adsl=item.PASSWORD!=null?item.PASSWORD.toString():''
            //khúc này gán ở ngoài
            //this.tocdo_moi_selected=item.TOCDO_ID!=null?item.TOCDO_ID:-1
            // if(this.tocdo_moi_selected!=item.TOCDO_ID){
            //     this.tocdo_moi_selected=item.TOCDO_ID
            // }else{
            //     this.changeGoiCuocAdsl()
            // }
            // this.tocdo_thuc_selected=item.TOCDOTHUC_ID!=null?item.TOCDOTHUC_ID:-1
            // if(this.tocdo_thuc_selected!=null&&this.tocdo_selected!=this.tocdo_moi_selected){
            //     this.checkTocDoThuc=true
            // }
            this.muccuoc_moi_selected=item.MUCCUOC_ID!=null?item.MUCCUOC_ID:null
            this.loaithietbi_moi_selected=item.THIETBIDC_ID!=null?item.THIETBIDC_ID:null
            if(item.TUCTHI!=null&&Number(item.TUCTHI)!=0){
                this.checkTucThi=true
            }else{
                this.checkTucThi=false
            }
            if(item.MADOICAP!=null){
                this.input_madoicap_adsl=item.MADOICAP.toString()
            }else{
                this.input_madoicap_adsl='0'
            }
            this.trangbi_moi_selected=item.TRANGBI_ID!=null?item.TRANGBI_ID:null
            this.input_tttc=item.THONGTIN_TC!=null?item.THONGTIN_TC.toString():''
            this.input_matn=item.MATB_TN!=null?item.MATB_TN.toString():''
             console.log('HienThiTTMoRong_HD E ADSL',item)
        },
        async HienThiTT_DanhBaV2(item){
            this.doituong_selected=item.doituong_id!=null?item.doituong_id:null
            this.doituong_moi_selected=item.doituong_id!=null?item.doituong_id:null

            this.input_khuvuc=item.ten_kv!=undefined?item.ten_kv:''
            await this.changeDoiTuongMoi()
        },
        async getTrangBiAdslIDMoi(){
            return (this.ds_trangbi.length>0&&this.trangbi_moi_selected!=null)?this.trangbi_moi_selected:0
        },
        async getLoaiHinhTB_ID_Moi(){
            return this.loaihinh_tb_moi_selected!=null?this.loaihinh_tb_moi_selected:0
        },
        //Tốc độ mới
        async getGoiCuocADSL(){
            return this.tocdo_moi_selected!=null?this.tocdo_moi_selected:0
        },
        async getMucCuocMoi(){
            return this.muccuoc_moi_selected!=null?this.muccuoc_moi_selected:0
        },
        async getThongTin(){
            return {
                
            }
        },
        async setGoiCuocADSL(goicuocADSLCu, goicuocADSLMoi){
            this.ds_tocdo_moi=goicuocADSLMoi
            this.ds_tocdo=goicuocADSLCu
            // if(this.ds_tocdo_moi.length>0){
            //     if(this.tocdo_moi_selected!=this.ds_tocdo_moi[0].tocdo_id){
            //         this.tocdo_moi_selected=this.ds_tocdo_moi[0].tocdo_id
            //     }else{
            //         this.changeGoiCuocAdsl()
            //     }
            //     //call trigger cboGoiCuocAdsl_EditValueChanged -->Tốc độ thực mới
            //     //this.changeGoiCuocAdsl()
            // }
            if(this.ds_tocdo.length>0){
                this.tocdo_selected=this.ds_tocdo[0].tocdo_id
                this.tocdothuc_selected=this.ds_tocdo[0].tocdo_id
            }

        },
        async changeDichVu(dichvuvt_id){
            this.trangbi_moi_selected=TRANGBI.VIENTHONG_TRANGBI
            this.btnThongTinTCEnabled=true
            
        },
        async setLoaiHinhTbMoi(loaihinh_tb_id){
            this.loaihinh_tb_moi_selected=loaihinh_tb_id
            //handle change 
            //await this.changeLoaiHinhTBMoi()
            
        },
        async changeLoaiHinhTBCu(loaihinhtb_id){
            this.loaihinh_tb_cu=loaihinhtb_id
        },
        //cboGoiCuocAdsl_EditValueChanged
        changeGoiCuocAdsl(){
            this.$emit('changeGoiCuocAdsl', this.tocdo_moi_selected)
        },
        //cboMucCuoc_SelectionChangeCommitted
        changeMucCuoc(){
            this.$emit('changeMucCuoc', this.muccuoc_moi_selected)
        },
        focusMaTB(){
            this.$nextTick(()=>{
                this.$refs.refInputMaTB.focus()
            })
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
            console.log('initDuLieu Băng rộng cố định')
            await this.clearDulieu()
            //Bỏ 57/ 59 theo yêu cầu của tester
            // this.ds_loaihinh_moi=ds_loaihinh_tb.filter(x=>x.DICHVUVT_ID==4&&(x.LOAITB_ID==11||x.LOAITB_ID==58))
            // if(this.ds_loaihinh_moi.length>0){
            //     this.loaihinh_tb_moi_selected=this.ds_loaihinh_moi[0].LOAITB_ID
            //     await this.changeLoaiHinhTBMoi()
            // }
            //
            let result = await this.lay_danhmuc_cdlh_tt_chung('TRANG_BI|DOITUONG|LOAI_TB|MUCCUOC')
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
                this.ds_loaithietbi=result.loai_tb.filter(x=>x.dichvuvt_id==DichVuVienThong.ADSL)
                if(this.ds_loaithietbi.length>0){
                    this.loaithietbi_selected=this.ds_loaithietbi[0].thietbidc_id
                    this.loaithietbi_moi_selected=this.ds_loaithietbi[0].thietbidc_id
                }
                this.ds_muccuoc=result.muccuoc
                if(this.ds_muccuoc.length>0){
                    this.muccuoc_selected=this.ds_muccuoc[0].muccuoc_id
                    this.muccuoc_moi_selected=this.ds_muccuoc[0].muccuoc_id
                    await this.changeMucCuoc()
                }
            }
        },
        visibleToolStripMenuAndControl(visible){
            if((this.iKieu==1||this.iKieu==2)&&this.checkQuyen==0){
                this.enableDoiTuongMoi=visible
                this.enableLoaiTBi=visible
                this.disableGoiCuocADSLCu=!visible
                this.disableTocDoADSLCuThuc=!visible
                this.disableGoiCuocADSLMoi=!visible

            } 
        },
        async clearDulieu(){
            this.input_matb=''
            
            this.tocdo_selected=null
            this.disableGoiCuocADSLCu=false
            this.disableGoiCuocADSLThucCu=false
            this.tocdothuc_selected=null
            this.disableTocDoADSLCuThuc=false
            
            this.trangbi_selected=null
            this.input_thongtin_tc_cu=''

            if(this.ds_doituong.length>0){
                this.doituong_selected=this.ds_doituong[0].doituong_id
                this.doituong_moi_selected=this.ds_doituong[0].doituong_id
            }
            if(this.ds_trangbi.length>0){
                this.trangbi_selected=this.ds_trangbi[0].trangbi_id
                //Default khách hàng tự trang bị
                this.trangbi_moi_selected=3
            }
            if(this.ds_loaithietbi.length>0){
                this.loaithietbi_selected=this.ds_loaithietbi[0].thietbidc_id
                this.loaithietbi_moi_selected=this.ds_loaithietbi[0].thietbidc_id
            }
            if(this.ds_muccuoc.length>0){
                this.muccuoc_selected=this.ds_muccuoc[0].muccuoc_id
                this.muccuoc_moi_selected=this.ds_muccuoc[0].muccuoc_id
            }

            //thông tin mới
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
                hdtb_id:0
            }
            this.tocdo_moi_selected=null
            this.disableGoiCuocADSLMoi=false
            this.checkTocDoThuc=false
            this.tocdo_thuc_selected=null
            this.input_matn=''
            this.enabledMaTN=true
            this.input_tttc=''
            this.btnThongTinTCEnabled=true

            this.input_email_adsl_cu=''
            this.input_email_adsl=''
            this.input_matkhau_adsl_cu=''
            this.input_matkhau_adsl=''
            this.input_soao=''
            this.checkTucThi=true
            this.input_madoicap_adsl=''
            this.dulieuPopupMaTN={}
            this.dulieuPopupTTTC={}
            this.enableDoiTuongMoi=true
            this.enableLoaiTBi=true
        }

    }
}
</script>
<style>
.disabledButton{
  border-color: gray !important;
  color: gray !important;
}
</style>
