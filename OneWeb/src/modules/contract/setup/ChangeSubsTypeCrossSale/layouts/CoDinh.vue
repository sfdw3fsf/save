<template>
  <div class="row">
    <div class="col-sm-6 col-12">
      <div class="box-form">
        <!-- <div class="legend-title">Loại hình thuê bao cũ</div> -->
        <div class="legend-title">
            <div class="pull-left">Loại hình thuê bao cũ</div>
            <div class="pull-right tb-isdn" v-if="visibleTbIsdn">
                <span class="name">{{lable_tb_isdn}}</span>
            </div>
            <div class="clearfix"></div>
        </div>
        <div class="row">
          <div class="col-sm-8 col-12">
            <div class="info-row">
              <div class="key">Số máy/Acc</div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn" @click="searchAccount">
                    <span class="-ap icon-more_horiz"></span>
                  </button>
                  <input type="text" ref="refInputMaTB" class="form-control bold highlight" @keyup.enter="onEnterMaTB" :value="input_matb" @change="e=>input_matb=e.target.value"/>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w80">Đối tượng</div>
              <div class="value">
                <div class="select-custom">
                  <select v-model="doituong_selected" class="form-control" disabled>
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
                  <select v-model="trangbi_selected" class="form-control" disabled>
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
              <div class="key w80">Loại số</div>
              <div class="value">
                <div class="select-custom">
                  <select v-model="loai_so_selected" class="form-control" disabled>
                    <option v-for="item in ds_loaiso" :key="item.loaiso_id" :value="item.loaiso_id">
                      {{item.loaiso}}
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
              <div class="key">Số chính</div>
              <div class="value">
                <input type="text" class="form-control highlight" readonly :value="input_sochinh" @change="e=>input_sochinh=e.target.value"/>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w80">Cước TB</div>
              <div class="value">
                <vue-numeric ref="inputCuocTBMoi" class="form-control tright bold" separator="," :value="input_cuoctb" disabled @change="v=>input_cuoctb=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                <!-- <input type="text" class="form-control tright bold" disabled :value="input_cuoctb" @change="e=>input_cuoctb=e.target.value"/> -->
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
                  <div class="select-custom" ref="ds_loaihinh_moi">
                      <select2 :settings="{ dropdownParent: $refs['ds_loaihinh_moi'] }" ref="cboLoaiHinhTBMoi"
                          v-model="loaihinh_tb_moi_selected" class="select2"
                          :options="ds_loaihinh_tb_moi.map(e=> ({id: e.LOAITB_ID, text: e.LOAIHINH_TB}))"
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
              <div class="key w60">Khu vực</div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn" @click="clickKhuVuc">
                    <span class="-ap icon-more_horiz"></span>
                  </button>
                  <input type="text" class="form-control highlight bold"  @keypress="evt=>evt.preventDefault()" :value="input_khuvuc" @change="e=>input_khuvuc=e.target.value"/>
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
              <div class="key">Trang bị</div>
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
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w80">Loại số</div>
              <div class="value">
                  <div class="select-custom" ref="ds_loaiso">
                      <select2 :settings="{ dropdownParent: $refs['ds_loaiso'] }" ref="cboLoaiSo"
                          v-model="loai_so_moi_selected" class="select2"
                          :options="ds_loaiso.map(e=> ({id: e.loaiso_id, text: e.loaiso}))"
                          @change="changeLoaiSoMoi"
                          :disabled="inputLoaiSoMoiDisabled"
                          >
                      </select2>
                  </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key">Số chính</div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn" @click="clickSoChinh" :disabled="!enableBtnChonSo" :class="{disabledButton: !enableBtnChonSo}">
                    <span class="-ap icon-more_horiz"></span>
                  </button>
                  <input type="text" class="form-control highlight" @keypress="evt=>evt.preventDefault()" :value="input_sochinh_moi" @change="e=>input_sochinh_moi=e.target.value"/>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w60">Số tắt</div>
              <div class="value">
                <input type="text" class="form-control" :disabled="inputSoTatDisabled" :value="input_sotat" @change="e=>input_sotat=e.target.value"/>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w80">Cước TB</div>
              <div class="value">
                <vue-numeric ref="inputCuocTBMoi" class="form-control tright bold" separator="," :value="input_cuoctb_moi" :disabled="inputCuocTBMoiDisabled" @change="v=>input_cuoctb_moi=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                <!-- <input type="text" class="form-control tright" ref="inputCuocTBMoi" :disabled="inputCuocTBMoiDisabled"  @keypress="isNumber($event)" @blur="handleBlur" :value="input_cuoctb_moi" @change="e=>input_cuoctb_moi=e.target.value"/> -->
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
    <ChonSoChinhModal ref="chonSoChinhModal"
      :dichvuvt_id="dichvuvt_id"
      :id="idChonSo"
      :loai="loai"
      :loaitb_id="loaitb_moi"
      @accept="acceptChonSoChinh"
    />
  </div>
</template>
<script>
import SearchAccountModal from '../../SurveyRequest/popups/SearchAccountModal.vue'
import ThayDoiKhuVucQuanLyThueBaoModal from '../../SurveyRequest/popups/ThayDoiKhuVucQuanLyThueBao/ThayDoiKhuVucQuanLyThueBaoModal.vue'
import ChangeSubsTypeCrossSaleAPI from '../ChangeSubsTypeCrossSaleAPI'
import {LOAISO_ISDN, LoaiHinhTB} from '../ThamSo'
import ChonSoChinhModal from '../../ChangeSubsType/popups/ChonSoChinhModal.vue'

export default {
    name:'CoDinh',
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
      ChonSoChinhModal
    },
    computed:{
      idChonSo(){
        return this.loai==1?this.khachhang_id:this.hdkh_id
      }
    },
    data(){
      return {
        //mặc định layout dv cố định
        dichvuvt_id:1,
        input_matb:'',
        ds_doituong:[],
        doituong_selected:null,
        ds_trangbi:[],
        trangbi_selected:null,
        ds_loaiso:[
          {
            loaiso_id:1,
            loaiso:'Số chính'
          },
          {
            loaiso_id:2,
            loaiso:'Số phụ'
          },
        ],
        loai_so_selected:null,
        input_sochinh:'',
        input_cuoctb:'',

        ds_loaihinh_tb_moi:[],
        loaihinh_tb_moi_selected:null,
        disableLoaiHinhTB:false,
        input_khuvuc:'',
        doituong_moi_selected:null,
        trangbi_moi_selected:3, //Default khách hàng tự trang bị
        inputLoaiSoMoiDisabled:true,
        loai_so_moi_selected:null,
        input_sochinh_moi:'',
        input_sotat:'',
        inputSoTatDisabled:true,
        input_cuoctb_moi:'0',
        inputCuocTBMoiDisabled:true,
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
        lable_tb_isdn:'Số chính/ phụ',
        visibleTbIsdn:false,
        loai:1,
        loaitb_moi:1,

        enableBtnChonSo:true,
        enableDoiTuongMoi:true,

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
      clickSoChinh(){
        if(this.khachhang_id==0&&this.hdkh_id==0){
          return
        }
        this.loaitb_moi=Number(this.loaihinh_tb_moi_selected!=null?this.loaihinh_tb_moi_selected:0)
        if(this.khachhang_id>0){
          this.loai=1
        }
        if(this.hdkh_id>0){
          this.loai=2
        }
        this.$refs.chonSoChinhModal.showModal()
      },
      acceptChonSoChinh(data){
        this.input_sochinh_moi=data.MA_TB
        this.$emit('acceptChonSoChinh',data)
      },
      async setLoaiHinhTbMoi(loaihinh_tb_id){
        console.log('setLoaiHinhTbMoi', loaihinh_tb_id)
        this.loaihinh_tb_moi_selected=loaihinh_tb_id
        //handle change 
        await this.changeLoaiHinhTBMoi()

      },
      async changeLoaiHinhTBMoi(){
        this.$emit('changeLoaiHinhTBMoi', this.loaihinh_tb_moi_selected)
      },
      successUpdateThayDoiKhuVucQuanLy(){
        this.$emit('successUpdateThayDoiKhuVucQuanLy')
      },
      onAcceptKhuVuc(data){
        this.khuvuc_selected=data
        this.input_khuvuc= this.khuvuc_selected.ma_kv
        this.$emit('onAcceptKhuVuc', this.khuvuc_selected)
      },
      async changeDoiTuongMoi(){
        this.$emit('changeDoiTuongMoi', this.doituong_moi_selected)
      },
      //chuyển watch
      changeLoaiSoMoi(){
        
      },
      async changeDichVu(dichvuvt_id){

      },
      async setDsLoaiHinhTbMoi(ds, selectedId){
        console.log('setDsLoaiHinhTbMoi', ds)
        this.ds_loaihinh_tb_moi=ds
        this.loaihinh_tb_moi_selected=selectedId
        await this.changeLoaiHinhTBMoi()

      },
      async getLoaiHinhTB_ID_Moi(){
        return this.loaihinh_tb_moi_selected!=null?this.loaihinh_tb_moi_selected:0
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
      async getMaTb(){
        return this.input_matb.trim()
      },
      getLoaiHinhTBMoi(){
        const loaihinh=this.ds_loaihinh_tb_moi.find(x=>x.LOAITB_ID==this.loaihinh_tb_moi_selected)
        return loaihinh!=undefined?loaihinh.LOAIHINH_TB:''
      },
      async HienThiTT_DanhBaV2(item){
        this.input_cuoctb=item.mucuoc_tb!=null?item.mucuoc_tb:''
        this.doituong_selected=item.doituong_id!=null?item.doituong_id:null
        this.doituong_moi_selected=item.doituong_id!=null?item.doituong_id:null

        this.input_khuvuc=item.ten_kv!=undefined?item.ten_kv:''

        await this.changeDoiTuongMoi()
        if(item.thuebao_cha_id!=null&&item.thuebao_cha_id!=''){
          this.input_sochinh_moi=await this.fn_map_db_thuebao(item.thuebao_cha_id, 2,'')
          this.loai_so_moi_selected=LOAISO_ISDN.SOPHU
        }else{
          this.input_sochinh_moi=''
          this.loai_so_moi_selected=LOAISO_ISDN.SOCHINH
        }

       
      },
      async HienThiTTMoRong_DB(item){
        console.log('HienThiTTMoRong_DB CoDinh', item.trangbi_id)
        if(item.trangbi_id!=null){
          this.trangbi_selected=item.trangbi_id
        }else{
          this.trangbi_selected=null
        }
      },
      async HienThiTTHopDongTB_B2(item){
        this.doituong_moi_selected=item.DOITUONG_ID!=null?item.DOITUONG_ID:null
        if(item.DOITUONG_ID_CU!='-1'&&item.DOITUONG_ID_CU!=''){
          this.doituong_selected=item.DOITUONG_ID_CU
        }else{
          this.doituong_selected=null
        }
        //txtMucCuocISDN.Text = dst.Rows[rowIndex]["mucuoc_tb"].ToString();
        this.input_cuoctb_moi=item.MUCUOC_TB!=null?item.MUCUOC_TB.toString():'0'
        //txtKhuVuc.Text = _kvhd.Rows[0]["ten_kv"].ToString();
        this.input_khuvuc=item.TEN_KV!=null?item.TEN_KV:''

        if(item.MA_TB_CHA!=null){
          this.input_sochinh=item.MA_TB_CHA.toString()
          this.loai_so_moi_selected=LOAISO_ISDN.SOPHU
        }else{
          this.input_sochinh=''
          this.loai_so_moi_selected=LOAISO_ISDN.SOCHINH
        }

      },
      async ISDN_LOAISO(label, loaiso_id){
        this.lable_tb_isdn=label
        this.loai_so_selected=loaiso_id
        
      },
      async changeLoaiHinhTBCu(loaihinhtb_id){
        if(loaihinhtb_id==LoaiHinhTB.ISDN2B_CD||
          loaihinhtb_id==LoaiHinhTB.ISDN2B_CQ||
          loaihinhtb_id==LoaiHinhTB.ISDN30B_CD||
          loaihinhtb_id==LoaiHinhTB.ISDN30B_CQ){
          this.visibleTbIsdn=true
        }else{
          this.visibleTbIsdn=false
        }
      },
      changeTrangBiMoi(){
        this.$emit('changeTrangBiMoi', this.trangbi_moi_selected)
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
      handleBlur(){
        if(this.input_cuoctb_moi.trim()==''){
          this.input_cuoctb_moi='0'
        }
      },
      focusCuocTBMoi(){
        //this.$refs.inputCuocTBMoi.focus()
        this.$refs.inputCuocTBMoi.$refs.numeric.focus()
      },
      focusLoaiHinhTBMoi(){
        this.$refs.cboLoaiHinhTBMoi.focus()
      },
      async fn_map_db_thuebao(thuebao_id, type, defaultValue){
          try{
              let response = await ChangeSubsTypeAPI.fn_map_db_thuebao(this.axios, thuebao_id, type)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                  return response.data.data
              }else{
                  return defaultValue
              }
          }catch(e){
              return defaultValue
          }
      },
      async lay_danhmuc_cdlh_tt_chung(chuoi){
          try{
              let response = await ChangeSubsTypeCrossSaleAPI.lay_danhmuc_cdlh_tt_chung(this.axios, chuoi)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                  return response.data.data
              }else{
                  return null
              }
          }catch(e){
              return null
          }
      },
      async getThongTin(){
        return {
          ds_doituong:this.ds_doituong,
          doituong_moi_selected:this.doituong_moi_selected,
          ds_trangbi:this.ds_trangbi,
          trangbi_moi_selected:this.trangbi_moi_selected,
          loaihinh_tb_moi_selected:this.loaihinh_tb_moi_selected
        }
      },
      async initDuLieu(ds_loaihinh_tb){
        console.log('initDuLieu Cố định')
        await this.clearDulieu()
        // this.ds_loaihinh_tb_moi=ds_loaihinh_tb.filter(x=>(x.DICHVUVT_ID==1)&&(x.LOAITB_ID==1||x.LOAITB_ID==14||x.LOAITB_ID==15||x.LOAITB_ID==16||x.LOAITB_ID==17))
        // if(this.ds_loaihinh_tb_moi.length>0){
        //   this.loaihinh_tb_moi_selected=this.ds_loaihinh_tb_moi[0].LOAITB_ID
        //   await this.changeLoaiHinhTBMoi()
        // }

        let result = await this.lay_danhmuc_cdlh_tt_chung('TRANG_BI|DOITUONG')
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
          
        }
      },
      visibleToolStripMenuAndControl(visible){
        if((this.iKieu==1||this.iKieu==2)&&this.checkQuyen==0){
          this.enableDoiTuongMoi=visible
        } 
      },
      async clearDulieu(){
        this.input_matb=''
        this.trangbi_selected=3
        
        this.input_sochinh=''
        this.input_cuoctb=''

        if(this.ds_doituong.length>0){
          this.doituong_selected=this.ds_doituong[0].doituong_id
          this.doituong_moi_selected=this.ds_doituong[0].doituong_id
        }
        if(this.ds_loaiso.length>0){
          this.loai_so_selected=this.ds_loaiso[0].loaiso_id
          this.loai_so_moi_selected=this.ds_loaiso[0].loaiso_id
        }

        this.input_khuvuc=''
        
        this.trangbi_moi_selected=3//Default khách hàng tự trang bị
        
        this.input_sochinh_moi=''
        this.input_sotat=''
        this.input_cuoctb_moi='0'
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
        this.visibleTbIsdn=false
        this.lable_tb_isdn='Số chính/ phụ'
        this.enableDoiTuongMoi=true
        
      }

    },
    watch:{
      //cboLoaiSo_SelectedValueChanged
      loai_so_moi_selected(val){
        if(val!=null&&val==LOAISO_ISDN.SOCHINH){
          this.enableBtnChonSo=false
          this.input_sochinh=''
          this.input_sotat=''
          this.inputSoTatDisabled=true
        }else{
          this.enableBtnChonSo=true
          if(this.loaihinh_tb_moi_selected==LoaiHinhTB.PABX){
            this.inputSoTatDisabled=false
          }
        }
        this.$emit('changeLoaiSoMoi')
      }
    }
}
</script>
<style scoped>
.tb-isdn {
    font-style: italic !important;
    color: red !important;
}
.disabledButton{
  border-color: gray !important;
  color: gray !important;
}
</style>