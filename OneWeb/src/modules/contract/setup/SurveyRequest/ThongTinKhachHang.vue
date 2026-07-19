<template>
  <div class="col-sm-6 col-12">
    <div class="box-form">
      <div class="legend-title">Thông tin khách hàng</div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Mã giao dịch</div>
            <div class="value">
              <div class="input-more-button">
                <button class="btn" @click="showPopupSearchContract" title="Tìm kiếm mã giao dịch">
                  <span class="-ap icon-more_horiz"></span>
                </button>
                <input type="text" @keyup.enter="onEnterMaGD" :value="input_ma_gd" @change="e=>input_ma_gd=e.target.value" ref="inputMaGD" class="form-control highlight bold" />
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Ngày YC</div>
            <div class="value">
              <div class="input-icon-right">
                <k-date-picker v-model="ngay_yc" :disabled="true"/>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Mã KH <k-required-marker/></div>
            <div class="value">
              <div class="input-more-button">
                <button class="btn" @click="showTraCuuDanhBa" title="Tìm mã khách hàng">
                  <span class="-ap icon-more_horiz"></span>
                </button>
                <input type="text" ref="inputMaKH" :value="input_ma_kh" @change="e=>input_ma_kh=e.target.value" @keyup.enter="onEnterMaKH" class="form-control bold" />
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Số máy/Acc</div>
            <div class="value">
              <input type="text" ref="inputMaTB" :value="input_ma_tb" @change="e=>input_ma_tb=e.target.value" :readonly="readonlySomay" class="form-control highlight red" />
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Tên KH <k-required-marker/></div>
            <div class="value">
              <input type="text" :value="input_ten_kh" @change="e=>input_ten_kh=e.target.value" ref="inputTenKH" @keyup.enter="onEnterTenKH" @blur="onEnterTenKH" class="form-control bold" />
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Mã số thuế</div>
            <div class="value">
              <input type="text" :value="input_ma_so_thue" @change="e=>input_ma_so_thue=e.target.value" class="form-control" />
            </div>
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key w90">Địa chỉ KH <k-required-marker/></div>
        <div class="value">
          <div class="input-more-button">
            <button class="btn" @click="chonDiaChiKH" title="Chọn địa chỉ khách hàng">
              <span class="-ap icon-more_horiz"></span>
            </button>
            <input type="text" ref="inputDiaChiKH" @keypress="evt=>evt.preventDefault()" :value="input_diachi_kh" @change="e=>input_diachi_kh=e.target.value" class="form-control bold" />
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Loại giấy tờ</div>
            <div class="value">
                <div class="select-custom" ref="loai_gt">
                    <select2 :settings="{ dropdownParent: $refs['loai_gt'] }" ref="cboLoaiGT"
                        v-model="loai_gt_selected" class="select2"
                        :options="loai_gt.map(e=> ({id: e.loaigt_id, text: e.loai_gt}))">
                    </select2>
                </div>  
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Số giấy tờ</div>
            <div class="value">
              <input type="text" :value="input_sogiayto" @change="e=>input_sogiayto=e.target.value" @keyup.enter="onBlurSoGT" ref="inputSoGT" class="form-control" />
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Ngày cấp</div>
            <div class="value">
              <div class="input-icon-right">
                <k-date-picker v-model="ngay_cap"/>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Nơi cấp</div>
            <div class="value">
              <input type="text" :value="input_noicap" @change="e=>input_noicap=e.target.value" class="form-control" />
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Ngày hết hạn</div>
            <div class="value">
              <div class="input-icon-right">
                <k-date-picker v-model="ngay_hethan"/>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Ngày sinh</div>
            <div class="value">
              <div class="input-icon-right">
                <k-date-picker v-model="ngay_sinh" @change="onChangeNgaySinhKH"/>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Giới tính</div>
            <div class="value">
              <div class="list-checks mart7">
                <div class="item">
                  <div class="check-action">
                    <input type="radio" :checked="radio_gioitinh==0" name="gioitinh" value="0" @change="radio_gioitinh=0" class="check" />
                    <span class="name">Không</span>
                  </div>
                </div>
                <div class="item">
                  <div class="check-action">
                    <input type="radio" :checked="radio_gioitinh==1"  name="gioitinh" value="1" @change="radio_gioitinh=1" class="check" />
                    <span class="name">Nam</span>
                  </div>
                </div>
                <div class="item">
                  <div class="check-action">
                    <input type="radio" :checked="radio_gioitinh==2"  name="gioitinh" value="2" @change="radio_gioitinh=2" class="check" />
                    <span class="name">Nữ</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Quốc tịch</div>
            <div class="value">
                <div class="select-custom" ref="ds_quoctich">
                    <select2 :settings="{ dropdownParent: $refs['ds_quoctich'] }" ref="cboQuocTich"
                        v-model="quoctich_selected" class="select2"
                        :options="ds_quoctich.map(e=> ({id: e.quoctich_id, text: e.tenquoctich}))">
                    </select2>
                </div>  
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Ngành nghề</div>
            <div class="value">
              <div class="input-more-button">
                <button class="btn" @click="clickButtonNganhNghe" title="Chọn chi tiết ngành nghề">
                  <span class="-ap icon-more_horiz"></span>
                </button>
                <div class="select-custom" ref="ds_nganhnghe">
                    <select2 :settings="{ dropdownParent: $refs['ds_nganhnghe'] }" ref="cboNganhNghe"
                        v-model="nganhnghe_selected" class="select2"
                        :options="ds_nganhnghe.map(e=> ({id: e.nganhnghe_id, text: e.tennganhnghe}))"
                        @change="changeNganhNghe">
                    </select2>
                </div>  
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Điện thoại <k-required-marker/></div>
            <div class="value">
              <input type="text" ref="inputSDT" :value="input_so_dt" @change="e=>input_so_dt=e.target.value" @keypress="isNumber($event)" class="form-control" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Modal -->
    <SearchContractModal ref="searchContractModal" :loai_hd_id="loaihd_id" :trangthai_hd="1" :donvi_dl_id="0" @accept="acceptSearchContract"/>
    <SearchAccountModal ref="searchAccountModal" @form-close="formCloseSearchAccount"/>
    <DiaChiKhachHangModal ref="diaChiKhachHangModal" :data="dataDiaChi" @accept="acceptDiaChiKH" />
    <ChiTietNganhNgheModal ref="chiTietNganhNgheModal" :data="dataPopupTieuNganh" @accept="acceptTieuNganh"/>
  </div>
</template>
<script>
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import SurveyRequestAPI from './SurveyRequestAPI'
import {LoaiHopDong} from './ThamSo'
import SearchAccountModal from './popups/SearchAccountModal.vue'
import DiaChiKhachHangModal from './popups/ChonDiaChi/DiaChiKhachHangModal.vue'
import ChiTietNganhNgheModal from './popups/ChiTietNganhNghe/ChiTietNganhNgheModal.vue'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
export default {
    name:'ThongTinKhachHang',
    props:{
      hdtb:{
        type:Object,
        default:()=>{}
      }
    },
    components:{
        SearchContractModal,
        KDatePicker,
        SearchAccountModal,
        DiaChiKhachHangModal,
        ChiTietNganhNgheModal,
        KRequiredMarker
    },
    data(){
        return {
            radio_gioitinh:0,
            ngay_yc:moment(new Date()).format('DD/MM/YYYY'),
            ngay_cap:'',
            ngay_hethan:'',
            ngay_sinh:'',
            loai_gt:[],
            loai_gt_selected:null,
            ds_nganhnghe:[],
            nganhnghe_selected:null,
            ds_quoctich:[],
            quoctich_selected:null,

            input_ma_gd:'',
            input_ma_kh:'',
            input_ma_tb:'',
            input_ten_kh:'',
            input_ma_so_thue:'',
            input_diachi_kh:'',
            input_sogiayto:'',
            input_noicap:'',
            input_so_dt:'',
            loaihd_id:33,
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
              diachi:''
            },
            dataDiaChiKH:{
              diachi_id:0,
              tinh_id:0,
              quan_id:0,
              phuong_id:0,
              pho_id:0,
              ap_id:0,
              khu_id:0,
              dacdiem_id:0,
              sonha:'',
              diachi:''
            },
            dataTieuNganh:{
              TNC1_ID:0,
              TNC2_ID:0,
              TNC3_ID:0,
              TNC4_ID:0,
              nganhnghe_id:0
            },
            dataPopupTieuNganh:{
              TNC1_ID:0,
              TNC2_ID:0,
              TNC3_ID:0,
              TNC4_ID:0,
              nganhnghe_id:0
            },
            //1: Type MaGD enter, 2: Type MaKH enter
            action_type:1,
            readonlySomay:true
        }
    },
    methods:{
        showPopupSearchContract(){
          //tra cứu phụ lục hợp đồng
          this.$refs.searchContractModal.showModal()
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
        focusMaGD(){
          this.$nextTick(()=>{
            this.$refs.inputMaGD.focus()
          })
        },
        focusTenKH(){
          this.$nextTick(()=>{
            this.$refs.inputTenKH.focus()
          })
        },
        focusMaKH(){
          this.$nextTick(()=>{
            this.$refs.inputMaKH.focus()
          })
        },
        focusMaTB(){
          this.$nextTick(()=>{
            this.$refs.inputMaTB.focus()
          })
        },
        focusSDT(){
          this.$nextTick(()=>{
            this.$refs.inputSDT.focus()
          })
        },
        focusDiaChiKH(){
          this.$nextTick(()=>{
            this.$refs.inputDiaChiKH.focus()
          })
        },
        focusSoGT(){
          this.$nextTick(()=>{
            this.$refs.inputSoGT.focus()
          })
        },
        setInputMaGD(maGD){
          this.input_ma_gd=maGD
        },
        acceptSearchContract(item){
          console.log(item)
          this.input_ma_gd=item.ma_gd
          this.$emit('searchMaGD', item.ma_gd)
        },
        onEnterMaGD(){
          this.$emit('searchMaGD', this.input_ma_gd.trim())
        }, 
        onEnterMaKH(){
          this.$emit('searchMaKH', this.input_ma_kh.trim())
        },
        showTraCuuDanhBa(){
          //UR2.8.002
          this.$refs.searchAccountModal.showModal()
        },
        formCloseSearchAccount(data){
          console.log(data)
          if(data&&data.ma_kh){
            this.input_ma_kh=data.ma_kh
            this.$emit('searchMaKH', this.input_ma_kh.trim())
          }
        },
        chonDiaChiKH(){
          Object.assign(this.dataDiaChi, this.dataDiaChiKH)
          //địa chỉ khách hàng
          this.$refs.diaChiKhachHangModal.showModal()
        },
        onChangeNgaySinhKH(value){
          this.$emit('onChangeNgaySinhKH', value)
        },
        //callback form địa chỉ khách hàng
        acceptDiaChiKH(data){
          //handle
          this.input_diachi_kh=data.diachi
          Object.assign(this.dataDiaChiKH, data)
          this.$emit('callbackChonDiChiKH', this.dataDiaChiKH)
        },
        clickButtonNganhNghe(){
          Object.assign(this.dataPopupTieuNganh, this.dataTieuNganh)
          this.$refs.chiTietNganhNgheModal.showModal()
        },
        acceptTieuNganh(data){
          Object.assign(this.dataTieuNganh, data)
          this.nganhnghe_selected=this.dataTieuNganh.nganhnghe_id
          
        },
        async lay_danhmuc_lapmoi_tt_chung_v7(chuoi){
            try{
                this.loading(true)
                this.loai_gt=[]
                this.ds_nganhnghe=[]
                this.ds_quoctich=[]
                let response = await SurveyRequestAPI.lay_danhmuc_lapmoi_tt_chung_v7(this.axios, chuoi)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    if(response.data.data.loai_gt){
                        this.loai_gt=response.data.data.loai_gt
                        if(this.loai_gt.length>0){
                            this.loai_gt_selected=this.loai_gt[0].loaigt_id
                        }
                    }
                    if(response.data.data.nganhnghe){
                        this.ds_nganhnghe=response.data.data.nganhnghe
                        if(this.ds_nganhnghe.length>0){
                          this.nganhnghe_selected=this.ds_nganhnghe[0].nganhnghe_id
                        }
                    }
                    if(response.data.data.quoctich){
                        this.ds_quoctich=response.data.data.quoctich
                        if(this.ds_quoctich.length>0){
                            this.quoctich_selected=this.ds_quoctich[0].quoctich_id
                        }
                    }
                }else{
                    this.loai_gt=[]
                    this.ds_nganhnghe=[]
                    this.ds_quoctich=[]
                }
            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Không load được danh mục chung')
                }
            }finally{
                this.loading(false)
            }
        },
        //Thông tin cho form chính check cập nhật
        getInfoFormKhachHang(){
          return {
            input_ma_gd:this.input_ma_gd,
            input_ma_kh:this.input_ma_kh,
            input_ma_tb:this.input_ma_tb,
            input_ten_kh:this.input_ten_kh,
            input_ma_so_thue:this.input_ma_so_thue,
            input_diachi_kh:this.input_diachi_kh,
            input_sogiayto:this.input_sogiayto,
            input_noicap:this.input_noicap,
            input_so_dt:this.input_so_dt,
            diachi_kh:this.dataDiaChiKH,
            nganhnghe_selected:this.nganhnghe_selected,
            dataTieuNganh:this.dataTieuNganh,
            loai_gt_selected:this.loai_gt_selected,
            ngay_sinh:this.ngay_sinh,
            ngay_cap:this.ngay_cap,
            ngay_yc:this.ngay_yc,
            radio_gioitinh:this.radio_gioitinh,
            quoctich_selected:this.quoctich_selected,
            ngay_hethan:this.ngay_hethan

          }
        },
        changeNganhNghe(){
          if(this.loaihd_id==LoaiHopDong.TUVAN_DV){
            return
          }
          if(this.nganhnghe_selected!=this.dataTieuNganh.nganhnghe_id){
            this.dataTieuNganh={
              TNC1_ID:0,
              TNC2_ID:0,
              TNC3_ID:0,
              TNC4_ID:0,
              nganhnghe_id:this.nganhnghe_selected!=null?this.nganhnghe_selected:0
            }
          }
          this.clickButtonNganhNghe()
        },
        onEnterTenKH(){
          this.$emit('onEnterTenKH', this.input_ten_kh.trim())
        },
        onBlurSoGT(){
          if(this.input_sogiayto.trim()==''){
            return
          }
          this.$emit('onBlurSoGT', this.input_sogiayto.trim())
        },
        //type maGD
        async initFocusRow(){
          this.action_type=1
          this.input_ma_tb=this.hdtb.ma_tb!=null?this.hdtb.ma_tb:''
          this.input_ma_gd=this.hdtb.ma_gd!=null?this.hdtb.ma_gd:''
          this.input_so_dt=this.hdtb.so_dt!=null?this.hdtb.so_dt:''
          this.input_ma_kh=this.hdtb.ma_kh!=null?this.hdtb.ma_kh:''
          this.input_ten_kh=this.hdtb.ten_kh!=null?this.hdtb.ten_kh:''
          this.input_ma_so_thue=(this.hdtb.mst!=undefined&&this.hdtb.mst!=null)?this.hdtb.mst:''
          this.input_diachi_kh=this.hdtb.diachi_kh!=null?this.hdtb.diachi_kh:''


          this.loai_gt_selected=this.hdtb.loaigt_id!=null?this.hdtb.loaigt_id:1
          this.input_sogiayto=this.hdtb.so_gt!=null?this.hdtb.so_gt:''
          // 2009-10-12 00:00:00.0
          this.ngay_cap=this.hdtb.ngaycap!=null?moment(this.hdtb.ngaycap, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY'):''
          this.input_noicap = this.hdtb.noicap!=null?this.hdtb.noicap:''

          //khởi tạo ngành nghề
          this.dataTieuNganh={
              TNC1_ID:(this.hdtb.tnc1_id!=undefined&&this.hdtb.tnc1_id!=null)?this.hdtb.tnc1_id:0,
              TNC2_ID:(this.hdtb.tnc2_id!=undefined&&this.hdtb.tnc2_id!=null)?this.hdtb.tnc2_id:0,
              TNC3_ID:(this.hdtb.tnc3_id!=undefined&&this.hdtb.tnc3_id!=null)?this.hdtb.tnc3_id:0,
              TNC4_ID:(this.hdtb.tnc4_id!=undefined&&this.hdtb.tnc4_id!=null)?this.hdtb.tnc4_id:0,
              nganhnghe_id:this.nganhnghe_selected!=null?this.nganhnghe_selected:0
          }

          if(this.nganhnghe_selected!=this.hdtb.nganhnghe_id){
            this.dataTieuNganh.nganhnghe_id=this.hdtb.nganhnghe_id!=null?this.hdtb.nganhnghe_id:0
            this.nganhnghe_selected=this.hdtb.nganhnghe_id
          }else{
            this.dataTieuNganh.nganhnghe_id=this.nganhnghe_selected!=null?this.nganhnghe_selected:0
          }
          
          if(this.hdtb.ngay_sn!=null&&this.hdtb.ngay_sn.toString()!=''){
            this.ngay_sinh=this.hdtb.ngay_sn?moment(this.hdtb.ngay_sn,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY'):''
          }else{
            this.ngay_sinh=''
          }

          //MM-DD-YYYY HH:mm:ss
          this.ngay_yc=this.hdtb.ngay_yc?this.hdtb.ngay_yc:''

          await this.hienthi_tt_diachi_kh()
        
        },
        //Type maKH
        async initInfoMaKH(item){
          this.action_type=2
          //init data
          this.input_ma_kh=item.ma_kh!=null?item.ma_kh:''
          this.input_ten_kh=item.ten_kh!=null?item.ten_kh:''
      
          this.input_diachi_kh=item.diachi_kh!=null?item.diachi_kh:''

          this.input_ma_so_thue=item.mst!=null?item.mst:''

          if(item.ngay_sn!=null&&item.ngay_sn.toString()!=''){
            this.ngay_sinh=moment(item.ngay_sn,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY')
          }else{
            this.ngay_sinh=''
          }
          this.ngay_yc=moment(new Date()).format('DD/MM/YYYY')

          if(item.gioitinh!=null){
            if(item.gioitinh.toString()=='0'){
              this.radio_gioitinh=1
            }else if(item.gioitinh.toString()=='1'){
              this.radio_gioitinh=2
            }
          }else{
            this.radio_gioitinh=0
          }
          //
          this.quoctich_selected=item.quoctich_id
          this.input_so_dt=item.so_dt!=null?item.so_dt:''

          //khởi tạo ngành nghề
          this.dataTieuNganh={
              TNC1_ID:(item.tnc1_id!=undefined&&item.tnc1_id!=null)?item.tnc1_id:0,
              TNC2_ID:(item.tnc2_id!=undefined&&item.tnc2_id!=null)?item.tnc2_id:0,
              TNC3_ID:(item.tnc3_id!=undefined&&item.tnc3_id!=null)?item.tnc3_id:0,
              TNC4_ID:(item.tnc4_id!=undefined&&item.tnc4_id!=null)?item.tnc4_id:0,
              nganhnghe_id:this.nganhnghe_selected!=null?this.nganhnghe_selected:0
          }

          if(this.nganhnghe_selected!=item.nganhnghe_id){
            this.dataTieuNganh.nganhnghe_id=item.nganhnghe_id!=null?item.nganhnghe_id:0
            this.nganhnghe_selected=item.nganhnghe_id
          }else{
            this.dataTieuNganh.nganhnghe_id=this.nganhnghe_selected!=null?this.nganhnghe_selected:0
          }

          if(item.ngayhethan_gt!=null&&item.ngayhethan_gt.toString()!=''){
            //chưa rõ format date đầu vào
            this.ngay_hethan=moment(item.ngayhethan_gt,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY')
          }else {
            this.ngay_hethan=''
          }
          this.input_sogiayto=item.so_gt!=null?item.so_gt:''

          if(item.ngaycap!=null&&item.ngaycap.toString()!=''){
            //chưa rõ format date đầu vào
            this.ngay_cap=moment(item.ngaycap,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY')
          }else{
            this.ngay_cap=''
          }
          this.loai_gt_selected=item.loaigt_id
          this.input_noicap=item.noicap!=null?item.noicap:''

          this.input_ma_tb=item.ma_tb!=null?item.ma_tb:''

          //hiển thị thông tin địa chỉ// enter maKH
          this.dataDiaChiKH={
            diachi_id:item.diachikh_id!=null?item.diachikh_id:0,
            tinh_id:item.tinhkh_id!=null?item.tinhkh_id:0,
            quan_id:item.quankh_id!=null?item.quankh_id:0,
            phuong_id:item.phuongkh_id!=null?item.phuongkh_id:0,
            pho_id:item.phokh_id!=null?item.phokh_id:0,
            ap_id:item.apkh_id!=null?item.apkh_id:0,
            khu_id:item.khukh_id!=null?item.khukh_id:0,
            dacdiem_id:item.dacdiemkh_id!=null?item.dacdiemkh_id:0,
            sonha:item.sonha_kh!=null?item.sonha_kh:'',
            diachi:item.diachi_kh!=null?item.diachi_kh:''
          }
          this.$emit('initDiaChiKH',this.dataDiaChiKH)
        },
        async hienthi_tt_diachi_kh(){
          try{
              this.loading(true)
              this.dataDiaChiKH={
                diachi_id:0,
                tinh_id:0,
                quan_id:0,
                phuong_id:0,
                pho_id:0,
                ap_id:0,
                khu_id:0,
                dacdiem_id:0,
                sonha:'',
                diachi:''
              }
              let response = await SurveyRequestAPI.lay_ds_diachi_theo_hdkh(this.axios, this.hdtb.hdkh_id)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data&&response.data.data.length>0) {
                  let result = response.data.data[0]
                  this.dataDiaChiKH={
                    diachi_id:result.diachi_id!=null?result.diachi_id:0,
                    tinh_id:result.tinh_id!=null?result.tinh_id:0,
                    quan_id:result.quan_id!=null?result.quan_id:0,
                    phuong_id:result.phuong_id!=null?result.phuong_id:0,
                    pho_id:result.pho_id!=null?result.pho_id:0,
                    ap_id:result.ap_id!=null?result.ap_id:0,
                    khu_id:result.khu_id!=null?result.khu_id:0,
                    dacdiem_id:result.dacdiem_id!=null?result.dacdiem_id:0,
                    sonha:result.sonha!=null?result.sonha:'',
                    diachi:result.diachi!=null?result.diachi:''
                  }
                  this.$emit('initDiaChiKH',this.dataDiaChiKH)
              }
          }catch(e){
              this.loading(false)
              if(e.data&&e.data.message){
                  this.$toast.error(e.data.message)
              }else if(e.response&&e.response.data&&e.response.data.message){
                  this.$toast.error(e.response.data.message)
              }else{
                  this.$toast.error('Không load được thông tin địa chỉ khách hàng')
              }
          }
        },
        async clearDuLieu(){
          this.input_ma_gd=''
          this.input_ma_kh=''
          this.input_ma_tb=''
          this.input_ten_kh=''
          this.input_ma_so_thue=''
          this.input_diachi_kh=''
          this.input_sogiayto=''
          this.input_noicap=''
          this.input_so_dt=''

          this.radio_gioitinh=0
          this.ngay_yc=moment(new Date()).format('DD/MM/YYYY')
          this.ngay_cap=''
          this.ngay_hethan=''
          this.ngay_sinh=''
          if(this.loai_gt.length>0){
            this.loai_gt_selected=this.loai_gt[0].loaigt_id
          }
          if(this.ds_quoctich.length>0){
            this.quoctich_selected=this.ds_quoctich[0].quoctich_id
          }
          this.dataTieuNganh={
              TNC1_ID:0,
              TNC2_ID:0,
              TNC3_ID:0,
              TNC4_ID:0,
              nganhnghe_id:this.nganhnghe_selected!=null?this.nganhnghe_selected:0
          }
          this.dataDiaChiKH={
              diachi_id:0,
              tinh_id:0,
              quan_id:0,
              phuong_id:0,
              pho_id:0,
              ap_id:0,
              khu_id:0,
              dacdiem_id:0,
              sonha:'',
              diachi:''
          }
        },
        async initDuLieu(){
            //Handle form trùng
            let endPath=this.$router.currentRoute.path.split('/').pop()
            if(endPath=='SurveyRequest'){
                //[UR2.1.001] - Tiếp nhận yêu cầu khảo sát lắp đặt
                this.loaihd_id=LoaiHopDong.KHAOSATDATMOI
            }else if(endPath=='800126InquiryWatchList'){
                //[UR2.1.057] - 80126- Tiếp nhận yêu cầu
                this.loaihd_id=LoaiHopDong.TIEPNHANHD
            }else if(endPath=='ReceiveSurveyRequestChangeType'){
                //[UR2.1.002] - Tiếp nhận yêu cầu khảo sát thay đổi loại hình
                this.loaihd_id=LoaiHopDong.KHAOSATCDLH
            }else if(endPath=='ReceiveRequest'){
                //[UR2.1.055] - Tiếp nhận yêu cầu
                this.loaihd_id=LoaiHopDong.TIEPNHANHD
            }
            if(this.loaihd_id!=LoaiHopDong.KHAOSATDATMOI&&this.loaihd_id!=LoaiHopDong.TIEPNHANHD&&this.loaihd_id!=LoaiHopDong.TUVAN_DV){
                //enable số máy
                this.readonlySomay=false
            }else{
                this.readonlySomay=true
            }

            await this.lay_danhmuc_lapmoi_tt_chung_v7('LOAI_GT|NGANHNGHE|QUOCTICH')
        }
    }
}
</script>
<style>
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
</style>
