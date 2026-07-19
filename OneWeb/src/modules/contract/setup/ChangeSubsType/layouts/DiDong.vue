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
                    <div class="col-12">
                        <div class="info-row">
                            <div class="key">Gói cước</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="goicuoc_dd_selected" disabled class="form-control">
                                        <option v-for="item in ds_goicuoc_dd_cu" :key="item.GOICUOC_ID" :value="item.GOICUOC_ID">
                                            {{item.TENGOICUOC}}
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
                    <div class="col-12">
                        <div class="info-row">
                            <div class="key w80">Gói cước</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_goicuoc">
                                    <select2 :settings="{ dropdownParent: $refs['ds_goicuoc'] }" ref="cboGoiCuoc"
                                        v-model="goicuoc_dd_moi_selected" class="select2"
                                        :options="ds_goicuoc_dd_moi.map(e=> ({id: e.GOICUOC_ID, text: e.TENGOICUOC}))"
                                        >
                                    </select2>
                                </div>
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
    </div>
</template>
<script>
import SearchAccountModal from '../../SurveyRequest/popups/SearchAccountModal.vue'
import ThayDoiKhuVucQuanLyThueBaoModal from '../../SurveyRequest/popups/ThayDoiKhuVucQuanLyThueBao/ThayDoiKhuVucQuanLyThueBaoModal.vue'
import ChangeSubsTypeAPI from '../ChangeSubsTypeAPI'
import {LoaiHinhTB} from '../ThamSo'
export default {
    name:'DiDong',
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
            ds_goicuoc_dd_cu:[],
            goicuoc_dd_selected:null,
            txtCuocTamThu:'50000',
            textBox7:'',

            // thôgn tin mới
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
            txtSimCard:'',
            ds_goicuoc_dd_moi:[],
            goicuoc_dd_moi_selected:null,
            disableLoaiHinhTB:false,
            dsGoiCuocDD:[]
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
            console.log('changeLoaiHinhTBCu DD', loaihinhtb_id)
            this.ds_goicuoc_dd_cu=this.dsGoiCuocDD.filter(x=>x.LOAITB_ID==loaihinhtb_id)
            if(this.ds_goicuoc_dd_cu.length>0){
                this.goicuoc_dd_selected=this.ds_goicuoc_dd_cu[0].GOICUOC_ID
            }

        },
        async setGoiCuocDDMoi(loaihinhtb_id){
            this.ds_goicuoc_dd_moi=this.dsGoiCuocDD.filter(x=>x.LOAITB_ID==loaihinhtb_id)
            if(this.ds_goicuoc_dd_moi.length>0){
                this.goicuoc_dd_moi_selected=this.ds_goicuoc_dd_moi[0].GOICUOC_ID
            }
        },
        async HienThi_GoiCuoc_DDTraTruoc_CoTheChuyenSang(ma_tb){
            let dt=await this.ts_laphd_thanhly_loai_tb_tt('84'+ma_tb)
            var ds_goi=[]
            for(let i=0;i<dt.length;i++){
                ds_goi=ds_goi.concat(this.dsGoiCuocDD.filter(x=>x.LOAITB_ID==LoaiHinhTB.DIDONGTRATRUOC&&x.MA_LOAI==dt[i].MALOAITB))
            }
            this.ds_goicuoc_dd_moi=ds_goi
            if(this.ds_goicuoc_dd_moi.length>0){
                this.goicuoc_dd_moi_selected=this.ds_goicuoc_dd_moi[0].GOICUOC_ID
            }
        },
        async HienThiTTMoRong_DB(item){
            //
            this.goicuoc_dd_selected=item.GOICUOC_ID
            this.trangbi_selected=item.TRANGBI_ID
            this.textBox7=item.SIMCARD
        },
        async HienThiTTMoRong_HD(item){
            this.goicuoc_dd_moi_selected=item.GOICUOC_ID
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
        async HienThiTTHopDongTB_B2(item, result){
            this.input_khuvuc=result.TEN_KV!=null?result.TEN_KV:''
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
        async lay_ds_goicuoc_dd(){
            try{
                let response = await ChangeSubsTypeAPI.lay_ds_goicuoc_dd(this.axios)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async ts_laphd_thanhly_loai_tb_tt(so_tb){
            try{
                let response = await ChangeSubsTypeAPI.ts_laphd_thanhly_loai_tb_tt(this.axios, so_tb)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        //
        async initDuLieu(ds_loaihinh_tb){
            console.log('initDuLieu di động')
            await this.clearDulieu()
            this.dsGoiCuocDD=await this.lay_ds_goicuoc_dd()
            //
            // this.ds_loaihinh=ds_loaihinh_tb.filter(x=>(x.DICHVUVT_ID==2&&x.LOAITB_ID==21))
            // if(this.ds_loaihinh.length>0){
            //     this.loaihinh_tb_moi_selected=this.ds_loaihinh[0].LOAITB_ID
            //     await this.changeLoaiHinhTBMoi()
            // }
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
            this.txtCuocTamThu='50000'
            this.textBox7=''

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
            this.txtSimCard=''
            if(this.ds_trangbi.length>0){
                this.trangbi_selected=this.ds_trangbi[0].trangbi_id
                this.trangbi_moi_selected=this.ds_trangbi[0].trangbi_id
            }
        }


    }
}
</script>