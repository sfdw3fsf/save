<template>
  <div class="col-sm-6 col-12">
    <div class="box-form">
      <div class="legend-title">Thông tin khách hàng</div>
      <div class="row">
        <div class="col-sm-8 col-12">
          <div class="info-row">
            <div class="key">Mã hợp đồng</div>
            <div class="value">
              <input type="text" class="form-control bold" ref="inputMaHD" @keypress="evt=>evt.preventDefault()" :value="input_ma_hd" @change="e=>input_ma_hd=e.target.value"/>
            </div>
          </div>
        </div>
        <div class="col-sm-4 col-12">
          <div class="info-row">
            <div class="key w80">Ngày LHĐ</div>
            <div class="value">
              <div class="input-icon-right">
                <k-date-picker v-model="ngay_lhd" :disabled="true"/>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-4 col-12">
          <div class="info-row">
            <div class="key">Mã khách hàng</div>
            <div class="value">
              <input type="text" class="form-control bold" ref="inputMaKH" @keypress="evt=>evt.preventDefault()" :value="input_makh" @change="e=>input_makh=e.target.value"/>
            </div>
          </div>
        </div>
        <div class="col-sm-4 col-12">
          <div class="info-row">
            <div class="key w80">
              <div class="check-action">
                <input type="checkbox" class="check" @change="changeCheckCV" :disabled="!checkSoCVEnabled" v-model="checkSoCV" />
                <span class="name">Số CV</span>
              </div>
            </div>
            <div class="value">
              <input type="text" class="form-control" :value="input_socv" @change="e=>input_socv=e.target.value" :disabled="!checkSoCV||!checkSoCVEnabled"/>
            </div>
          </div>
        </div>
        <div class="col-sm-4 col-12">
          <div class="info-row">
            <div class="key w80">Ngày CV</div>
            <div class="value">
              <div class="input-icon-right">
                <k-date-picker v-model="ngay_cv" :disabled="!enabledNgayCV"/>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-8 col-12">
          <div class="info-row">
            <div class="key">Tên khách hàng</div>
            <div class="value">
              <input type="text" ref="inputTenKH" class="form-control bold" @keypress="evt=>evt.preventDefault()" :value="input_ten_kh" @change="e=>input_ten_kh=e.target.value"/>
            </div>
          </div>
        </div>
        <div class="col-sm-4 col-12">
          <div class="info-row">
            <div class="key w80">Điện thoại</div>
            <div class="value">
              <input type="text" class="form-control" ref="inputSDT" @keypress="isNumber($event)" :value="input_sdt" @change="e=>input_sdt=e.target.value"/>
            </div>
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key">Địa chỉ KH</div>
        <div class="value">
          <input type="text" class="form-control bold" @keypress="evt=>evt.preventDefault()" :value="input_diachi_kh" @change="e=>input_diachi_kh=e.target.value"/>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-8 col-12">
          <div class="info-row">
            <div class="key">Tên thuê bao</div>
            <div class="value">
              <input type="text" class="form-control bold" ref="inputTenTB" @keypress="evt=>evt.preventDefault()" :value="input_ten_tb" @change="e=>input_ten_tb=e.target.value"/>
            </div>
          </div>
        </div>
        <div class="col-sm-4 col-12">
          <div class="info-row">
            <div class="key w80">Số tháng CK</div>
            <div class="value">
              <input type="text" class="form-control bold" ref="inputSoThangCK" @keypress="isNumber($event)" :value="input_thang_ck" @change="e=>input_thang_ck=e.target.value"/>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-8 col-12">
          <div class="info-row">
            <div class="key">Địa chỉ TB</div>
            <div class="value">
              <div class="input-more-button">
                <button class="btn" @click="chonDiaChi(1)" title="Chọn địa chỉ thuê bao" :disabled="!enabledDiaChiTB" :class="{disabledButton: !enabledDiaChiTB}">
                  <span class="-ap icon-more_horiz"></span>
                </button>
                <input type="text" class="form-control bold" @keypress="evt=>evt.preventDefault()" :value="input_diachi_tb" @change="e=>input_diachi_tb=e.target.value"/>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-4 col-12">
          <div class="info-row">
            <div class="key w80">Mã ERP</div>
            <div class="value">
              <input type="text" class="form-control bold" :value="input_ma_erp" @change="e=>input_ma_erp=e.target.value"/>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-8 col-12">
          <div class="info-row">
            <div class="key">Địa chỉ LĐ</div>
            <div class="value">
              <div class="input-more-button">
                <button class="btn" @click="chonDiaChi(2)" title="Chọn địa chỉ lắp đặt" :disabled="!enabledDiaChiLD" :class="{disabledButton: !enabledDiaChiLD}">
                  <span class="-ap icon-more_horiz"></span>
                </button>
                <input type="text" class="form-control bold" @keypress="evt=>evt.preventDefault()" :value="input_diachi_ld" @change="e=>input_diachi_ld=e.target.value"/>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-4 col-12">
          <div class="info-row">
            <div class="key w80">Mã BSS</div>
            <div class="value">
              <input type="text" class="form-control bold" :value="input_ma_bss" @change="e=>input_ma_bss=e.target.value"/>
            </div>
          </div>
        </div>
      </div>
      
    </div>
    <!-- Modal -->
    <DiaChiModal ref="diaChiModal" :data="dataDiaChi" @accept="onAcceptDiaChi"/>
  </div>
</template>
<script>
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import DiaChiModal from '../SurveyRequest/popups/ChonDiaChi/DiaChiModal.vue'
import {DichVuVienThong} from './ThamSo'
export default {
  name: "ThongTinKhachHang",
  components:{
      KDatePicker,
      DiaChiModal
  },
  data(){
      return {
        input_ma_hd:'',
        input_makh:'',
        input_socv:'',
        checkSoCVEnabled:false,
        checkSoCV:false,
        enabledNgayCV:false,
        input_ten_kh:'',
        input_sdt:'',
        input_diachi_kh:'',
        input_ten_tb:'',
        input_thang_ck:'',
        input_diachi_tb:'',
        input_diachi_ld:'',
        ngay_lhd:moment(new Date()).format('DD/MM/YYYY'),
        ngay_cv:moment(new Date()).format('DD/MM/YYYY'),
        kieu_chon_diachi:1,//1:Thuê bao, 2:Lắp Đặt,
        dataDiaChi:{
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
        },
        dataDiaChiTB:{
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
        },
        dataDiaChiLD:{
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
        },
        enabledDiaChiLD:true,
        enabledDiaChiTB:true,
        input_ma_erp:'',
        input_ma_bss:''

      }
  },
  methods:{
    
    chonDiaChi(value){
        this.kieu_chon_diachi=value
        //khởi tạo dữ liệu, địa chỉ khách hàng
        if(this.kieu_chon_diachi==1){
            this.dataDiaChi=this.dataDiaChiTB
        }else if(this.kieu_chon_diachi==2){
            this.dataDiaChi=this.dataDiaChiLD
        }
        this.$refs.diaChiModal.showModal()
    },
    onAcceptDiaChi(data){
        if(this.kieu_chon_diachi==1){
          let diachi_tb_id=this.dataDiaChiTB.diachi_id
          let diachi_ld_id=this.dataDiaChiLD.diachi_id
          this.input_diachi_tb=data.diachi
          Object.assign(this.dataDiaChiTB, data)
          this.dataDiaChiTB.diachi_id=diachi_tb_id
          //Chọn địa chỉ TB apply luôn qua lắp đặt
          this.input_diachi_ld=data.diachi
          Object.assign(this.dataDiaChiLD, data)
          this.dataDiaChiLD.diachi_id=diachi_ld_id
          this.$emit('onAcceptDiaChiTB', this.dataDiaChiTB)
          this.$emit('onAcceptDiaChiLD', this.dataDiaChiLD)
          //Handle chọn đơn vị tổ thi công
          //this.handleChonToThiCongSauChonTB()
        }else if(this.kieu_chon_diachi==2){
          this.input_diachi_ld=data.diachi
          Object.assign(this.dataDiaChiLD, data)
          this.$emit('onAcceptDiaChiLD', this.dataDiaChiLD)
          //Handle chọn đơn vị tổ thi công
          //this.handleChonToThiCongSauChonLD()

        }
    },
    async HienThiTTDiaChi(dsDiaChi){
      if(dsDiaChi.length>0){
          //Địa chỉ tb
          Object.assign(this.dataDiaChiTB, {
              diachi_id:dsDiaChi[0].DIACHI_ID,
              tinh_id:dsDiaChi[0].TINH_ID,
              quan_id:dsDiaChi[0].QUAN_ID,
              phuong_id:dsDiaChi[0].PHUONG_ID,
              pho_id:dsDiaChi[0].PHO_ID!=null?dsDiaChi[0].PHO_ID:0,
              ap_id:dsDiaChi[0].AP_ID!=null?dsDiaChi[0].AP_ID:0,
              khu_id:dsDiaChi[0].KHU_ID!=null?dsDiaChi[0].KHU_ID:0,
              dacdiem_id:dsDiaChi[0].DACDIEM_ID!=null?dsDiaChi[0].DACDIEM_ID:0,
              sonha:dsDiaChi[0].SONHA!=null?dsDiaChi[0].SONHA:'',
              //chưa rõ model
              block:dsDiaChi[0].hasOwnProperty('BLOCK')?dsDiaChi[0].BLOCK:'',
              tang:dsDiaChi[0].hasOwnProperty('TANG')?dsDiaChi[0].TANG:'',
              sophong:dsDiaChi[0].hasOwnProperty('PHONG')?dsDiaChi[0].BPHONGLOCK:'',
              motathem:dsDiaChi[0].hasOwnProperty('MOTA')?dsDiaChi[0].MOTA:'',
              diachi:'',
              //
              kinhdo:dsDiaChi[0].KINHDO,
              vido:dsDiaChi[0].VIDO
          })
          //Địa chỉ LD
          Object.assign(this.dataDiaChiLD, {
              diachi_id:dsDiaChi[0].DIACHILD_ID,
              tinh_id:dsDiaChi[0].TINHLD_ID,
              quan_id:dsDiaChi[0].QUANLD_ID,
              phuong_id:dsDiaChi[0].PHUONGLD_ID,
              pho_id:dsDiaChi[0].PHOLD_ID!=null?dsDiaChi[0].PHOLD_ID:0,
              ap_id:dsDiaChi[0].APLD_ID!=null?dsDiaChi[0].APLD_ID:0,
              khu_id:dsDiaChi[0].KHULD_ID!=null?dsDiaChi[0].KHULD_ID:0,
              dacdiem_id:dsDiaChi[0].DACDIEMLD_ID!=null?dsDiaChi[0].DACDIEMLD_ID:0,
              sonha:dsDiaChi[0].SONHA_LD!=null?dsDiaChi[0].SONHA_LD:'',
              //chưa rõ model
              block:dsDiaChi[0].hasOwnProperty('BLOCK_LD')?dsDiaChi[0].BLOCK:'',
              tang:dsDiaChi[0].hasOwnProperty('TANG')?dsDiaChi[0].TANG:'',
              sophong:dsDiaChi[0].hasOwnProperty('PHONG')?dsDiaChi[0].BPHONGLOCK:'',
              motathem:dsDiaChi[0].hasOwnProperty('MOTA')?dsDiaChi[0].MOTA:'',
              diachi:'',
              //
              kinhdo:dsDiaChi[0].KINHDO_LD,
              vido:dsDiaChi[0].VIDO_LD
          })
      }
    },
    async HienThiTTKhachHangTheoMaKH(item){
      this.input_ma_hd=item.ma_HD?item.ma_HD.toString():''
      this.input_makh=item.ma_KH?item.ma_KH.toString():''
      this.ngay_lhd=item.ngaylap_HD?moment(item.ngaylap_HD,'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY'):moment(new Date()).format('DD/MM/YYYY')
      this.input_ten_kh=item.ten_KH?item.ten_KH.toString():''
      this.input_diachi_kh=item.diachi_KH?item.diachi_KH.toString():''
      this.input_sdt=item.so_DT?item.so_DT.toString():''
      
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
    async changeDichVu(dichvuvt_id){
      if(dichvuvt_id==DichVuVienThong.MEGAWAN||dichvuvt_id==DichVuVienThong.TSL
        ||dichvuvt_id==DichVuVienThong.METRONET||dichvuvt_id==25||dichvuvt_id==26){
        this.checkSoCVEnabled=true
      }else{
        this.checkSoCVEnabled=false
        this.input_socv=''
      }
    },
    changeCheckCV(){
      if(this.checkSoCV){
        this.enabledNgayCV=true
      }else{
        this.enabledNgayCV=false
        this.input_socv=''
        this.ngay_cv=moment(new Date()).format('DD/MM/YYYY')
      }
    },
    enabledBtnDiaChiLD(value){
      this.enabledDiaChiLD=value
    },
    focusTenKH(){
      this.$refs.inputTenKH.focus()
    },
    focusMaKH(){
      this.$refs.inputMaKH.focus()
    },
    focusSoDT(){
      this.$refs.inputSDT.focus()
    },
    focusMaHD(){
      this.$refs.inputMaHD.focus()
    },
    focusTenTB(){
      this.$refs.inputTenTB.focus()
    },
    focusInputSoThangCK(){
      this.$refs.inputSoThangCK.focus()
    },
    async HienThiTTHopDongKH(item){
      this.input_ma_hd=item.ma_hd!=null?item.ma_hd:''
      this.input_makh=item.ma_kh!=null?item.ma_kh:''
      this.input_ten_kh=item.ten_kh!=null?item.ten_kh:''
      this.input_diachi_kh=item.diachi_kh!=null?item.diachi_kh:''
      this.input_sdt=item.so_dt!=null?item.so_dt:''


    },
    async HienThiTTHopDongTB(item){
      this.input_ten_tb=item.TEN_TB!=null?item.TEN_TB:''
      this.input_diachi_ld=item.DIACHI_LD!=null?item.DIACHI_LD:''
      this.input_diachi_tb=item.DIACHI_TB!=null?item.DIACHI_TB:''

    },
    async HienThiTTMoRong_DB(dsTT_DBTB){
      this.checkSoCVEnabled=false
      this.checkSoCV=false
      this.input_socv=''
      this.ngay_cv=moment(new Date()).format('DD/MM/YYYY')
      
    },
    async HienThiTT_DanhBaV2(item){
      this.input_makh=item.ma_kh!=null?item.ma_kh:''
      if(item.ngaylap_hd!=null&&item.ngaylap_hd!=''){
        this.ngaylap_hd=moment(item.ngaylap_hd,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY')
      }else{
        this.ngay_lhd=''
      }
      this.input_ma_hd=item.ma_hd!=null?item.ma_hd:''
      this.input_ten_kh=item.ten_kh!=null?item.ten_kh:''
      this.input_diachi_kh=item.diachi_kh!=null?item.diachi_kh:''
      this.input_sdt=item.so_dt!=null?item.so_dt:''
      //Thông tin thuê bao
      this.input_ten_tb=item.ten_tb!=null?item.ten_tb:''
      this.input_diachi_ld=item.diachi_ld!=null?item.diachi_ld:''
      this.input_diachi_tb=item.diachi_tb!=null?item.diachi_tb:''

      this.input_thang_ck=item.sothang_ck!=null?item.sothang_ck.toString():''
      
            
            
    },
    async initDuLieu(){

    },
    async clearDulieu(){
      this.input_ma_hd=''
      this.input_makh=''
      this.input_socv=''
      this.checkSoCV=false
      this.input_ten_kh=''
      this.input_sdt=''
      this.input_diachi_kh=''
      this.input_ten_tb=''
      this.input_thang_ck=''
      this.input_diachi_tb=''
      this.input_diachi_ld=''
      this.ngay_lhd=moment(new Date()).format('DD/MM/YYYY')
      this.ngay_cv=moment(new Date()).format('DD/MM/YYYY')
      this.kieu_chon_diachi=1,//1:Thuê bao, 2:Lắp Đặt,
      this.dataDiaChi={
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
        diachi:''
      }
      this.dataDiaChiTB={
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
        diachi:''
      }
      this.dataDiaChiLD={
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
        diachi:''
      }
      this.input_ma_erp=''
      this.input_ma_bss=''
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
