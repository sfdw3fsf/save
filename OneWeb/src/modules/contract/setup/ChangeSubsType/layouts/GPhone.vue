<template>
    <div class="row">
        <div class="col-sm-6 col-12">
            <div class="box-form">
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
                                    <input type="text" ref="refInputMaTB" class="form-control bold highlight" @keyup.enter="onEnterMaTB" :value="input_matb" @change="e=>input_matb=e.target.value">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Trang bị</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="trangbi_selected" disabled class="form-control">
                                        <option v-for="item in ds_trangbi" :key="item.trangbi_id" :value="item.trangbi_id">
                                            {{item.tentrangbi}}
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
                            <div class="key">Vệ tinh</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="vetinh_cu_selected" disabled class="form-control">
                                        <option v-for="item in ds_vetinh" :key="item.GOICUOC_ID" :value="item.GOICUOC_ID">
                                            {{item.TENGOICUOC}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Số sim</div>
                            <div class="value">
                                <input type="text" class="form-control" readonly :value="input_sosim_cu" @change="e=>input_sosim_cu=e.target.value"/>
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
                            <div class="key w80">Loại hình</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_loaihinh_moi">
                                    <select2 :settings="{ dropdownParent: $refs['ds_loaihinh_moi'] }" ref="cboLoaiHinhTBMoi"
                                        v-model="loaihinh_tb_moi_selected" class="select2"
                                        :options="ds_loaihinh.map(e=> ({id: e.LOAITB_ID, text: e.LOAIHINH_TB}))"
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
                            <div class="key w80">Khu vực</div>
                            <div class="value">
                                <div class="input-more-button">
                                    <button class="btn" @click="clickKhuVuc">
                                        <span class="-ap icon-more_horiz"></span>
                                    </button>
                                    <input type="text" class="form-control highlight bold" @keypress="evt=>evt.preventDefault()" :value="input_khuvuc" @change="e=>input_khuvuc=e.target.value">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Trang bị</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_trangbi">
                                    <select2 :settings="{ dropdownParent: $refs['ds_trangbi'] }" ref="cboTrangBi"
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
                <div class="row">
                    <div class="col-sm-8 col-12">
                        <div class="info-row">
                            <div class="key w80">Vệ tinh</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_goicuoc">
                                    <select2 :settings="{ dropdownParent: $refs['ds_goicuoc'] }" ref="cboGoiCuoc"
                                        v-model="vetinh_moi_selected" class="select2"
                                        :options="ds_vetinh.map(e=> ({id: e.GOICUOC_ID, text: e.TENGOICUOC}))"
                                        >
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Số sim</div>
                            <div class="value">
                                <input type="text" class="form-control" :value="input_sosim_moi" @change="e=>input_sosim_moi=e.target.value"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import SearchAccountModal from '../../SurveyRequest/popups/SearchAccountModal.vue'
import ThayDoiKhuVucQuanLyThueBaoModal from '../../SurveyRequest/popups/ThayDoiKhuVucQuanLyThueBao/ThayDoiKhuVucQuanLyThueBaoModal.vue'
import ChangeSubsTypeAPI from '../ChangeSubsTypeAPI'
import {LoaiHinhTB} from '../ThamSo'

export default {
    name:'GPhone',
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
        ThayDoiKhuVucQuanLyThueBaoModal
    },
    data(){
        return {
            input_matb:'',
            ds_trangbi:[],
            trangbi_selected:null,
            vetinh_cu_selected:null,
            vetinh_moi_selected:null,
            ds_vetinh:[],

            ds_loaihinh:[],
            loaihinh_tb_moi_selected:null,
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
            trangbi_moi_selected:null,
            disableLoaiHinhTB:false,
            input_sosim_moi:'',
            input_sosim_cu:''
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
        async changeLoaiHinhTBMoi(){
            this.$emit('changeLoaiHinhTBMoi', this.loaihinh_tb_moi_selected)
        },
        onAcceptKhuVuc(data){
            this.khuvuc_selected=data
            this.input_khuvuc = this.khuvuc_selected.ma_kv
            this.$emit('onAcceptKhuVuc', this.khuvuc_selected)
        },
        successUpdateThayDoiKhuVucQuanLy(){
            this.$emit('successUpdateThayDoiKhuVucQuanLy')
        },
        getLoaiHinhTBMoi(){
            const loaihinh=this.ds_loaihinh.find(x=>x.LOAITB_ID==this.loaihinh_tb_moi_selected)
            return loaihinh!=undefined?loaihinh.LOAIHINH_TB:''
        },
        changeTrangBiMoi(){
            this.$emit('changeTrangBiMoi', this.trangbi_moi_selected)
        },
        async setDsLoaiHinhTbMoi(ds, selectedId){
            console.log('setDsLoaiHinhTbMoi', ds)
            this.ds_loaihinh=ds
            this.loaihinh_tb_moi_selected=selectedId
            await this.changeLoaiHinhTBMoi()
            
        },
        async changeDichVu(dichvuvt_id){
            
        },
        async HienThiTTMoRong_DB(item){
            //
            this.vetinh_cu_selected=item.DONVI_ID?item.DONVI_ID:0
            this.vetinh_moi_selected=item.DONVI_ID?item.DONVI_ID:0

            this.trangbi_selected=item.TRANGBI_ID
            this.trangbi_moi_selected=item.TRANGBI_ID
            this.input_sosim_cu=item.SIMCARD?item.SIMCARD.toString():''
            this.input_sosim_moi=item.SIMCARD?item.SIMCARD.toString():''

        },
        async setLoaiHinhTbMoi(loaihinh_tb_id){
            this.loaihinh_tb_moi_selected=loaihinh_tb_id
            //handle change 
            await this.changeLoaiHinhTBMoi()
            
        },
        async HienThiTTHopDongTB_B2(item, result){
            this.input_khuvuc=result.TEN_KV!=null?result.TEN_KV:''
        },
        async HienThiTTMoRong_HD(item){
            this.input_sosim_moi=item.SIMCARD?item.SIMCARD.toString():''
            this.trangbi_moi_selected=item.TRANGBI_ID?item.TRANGBI_ID:0
        },
        async changeLoaiHinhTBCu(loaihinhtb_id){
            console.log('changeLoaiHinhTBCu GP', loaihinhtb_id)
            
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
            console.log('initDuLieu di động')
            await this.clearDulieu()

            let result = await this.lay_danhmuc_cdlh_tt_chung('TRANG_BI')
            if(result){
                this.ds_trangbi=result.trangbi
                if(this.ds_trangbi.length>0){
                    this.trangbi_selected=this.ds_trangbi[0].trangbi_id
                    this.trangbi_moi_selected=this.ds_trangbi[0].trangbi_id
                }
            }
            

        },
        async clearDulieu(){
            this.input_matb=''

            // thôgn tin mới
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
            if(this.ds_trangbi.length>0){
                this.trangbi_selected=this.ds_trangbi[0].trangbi_id
                this.trangbi_moi_selected=this.ds_trangbi[0].trangbi_id
            }
            this.input_sosim_moi=''
            this.input_sosim_cu=''
        }
    }
}
</script>