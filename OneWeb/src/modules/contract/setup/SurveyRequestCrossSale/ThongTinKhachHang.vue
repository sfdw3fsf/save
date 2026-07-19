<template>
    <div class="box-form">
        <div class="legend-title">Thông tin khách hàng</div>
        <div class="row">
            <div class="col-sm-6 col-12">
                <div class="info-row">
                    <div class="key w90">Mã giao dịch</div>
                    <div class="value">
                        <div class="input-more-button">
                            <button class="btn" @click="showPopupSearchContract" title="Tìm kiếm thông tin giao dịch">
                                <span class="-ap icon-more_horiz"></span>
                            </button>
                            <input type="text" ref="inputMaGD" @keyup.enter="onEnterMaGD" class="form-control bold" :value="input_ma_gd" @change="e=>input_ma_gd=e.target.value">
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
                    <div class="key w90">Mã KH (*)</div>
                    <div class="value">
                        <div class="input-more-button">
                            <button class="btn" @click="showTraCuuDanhBa" title="Tìm kiếm mã khách hàng">
                                <span class="-ap icon-more_horiz"></span>
                            </button>
                            <input type="text" ref="inputMaKH" @keyup.enter="onEnterMaKH" :readonly="readonlyMaKH"  :value="input_ma_kh" @change="changeMaKH($event)" class="form-control bold" >
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-12">
                <div class="info-row">
                    <div class="key w90">Số máy/Acc</div>
                    <div class="value">
                        <input type="text" @keyup.enter="onEnterMaTB" :value="input_matb" @change="e=>input_matb=e.target.value" :readonly="readonlySomay" class="form-control highlight">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-12">
                <div class="info-row">
                    <div class="key w90">Tên KH (*)</div>
                    <div class="value">
                        <input type="text" ref="inputTenKH" @keyup.enter="onEnterTenKH" @blur="onBlurTenKH" :value="input_tenkh" @change="e=>input_tenkh=e.target.value" class="form-control bold">
                    </div>
                </div>
            </div>
        </div>
        <div class="info-row">
            <div class="key w90">Địa chỉ KH (*)</div>
            <div class="value">
                <div class="input-more-button">
                    <button class="btn" :disabled="disabledBtnDiaChiKH" @click="chonDiaChiKH" title="Chọn địa chỉ khách hàng">
                        <span class="-ap icon-more_horiz"></span>
                    </button>
                    <input type="text" ref="inputDiaChiKH" @keypress="evt=>evt.preventDefault()" :value="input_diachi_kh" @change="e=>input_diachi_kh=e.target.value" class="form-control bold">
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
                        <input type="text" ref="inputSoGT"  :value="input_sogiayto" @change="e=>input_sogiayto=e.target.value" @keyup.enter="onBlurSoGT" class="form-control">
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
                        <input type="text" :value="input_noicap" @change="e=>input_noicap=e.target.value" class="form-control">
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
                            <button class="btn" @click="clickButtonNganhNghe">
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
                    <div class="key w90">Điện thoại (*)</div>
                    <div class="value">
                        <input type="text" ref="inputSDT" :value="input_so_dt" @change="e=>input_so_dt=e.target.value" @keypress="isNumber($event)" class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal -->
        <ChiTietNganhNgheModal ref="chiTietNganhNgheModal" :data="dataPopupTieuNganh" @accept="acceptTieuNganh"/>
        <DiaChiKhachHangModal ref="diaChiKhachHangModal" :data="dataDiaChi" @accept="acceptDiaChiKH" />
        <SearchAccountModal ref="searchAccountModal" @form-close="formCloseSearchAccount"/>
        <SearchContractModal ref="searchContractModal" :loai_hd_id="loaihd_id" :trangthai_hd="1" :donvi_dl_id="0" @accept="acceptSearchContract"/>
    </div>
</template>
<script>
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import {LoaiHopDong} from './ThamSo'
import SurveyRequestCrossSaleAPI from './SurveyRequestCrossSaleAPI'
import ChiTietNganhNgheModal from '../SurveyRequest/popups/ChiTietNganhNghe/ChiTietNganhNgheModal.vue'
import DiaChiKhachHangModal from '../SurveyRequest/popups/ChonDiaChi/DiaChiKhachHangModal.vue'
import SearchAccountModal from '../SurveyRequest/popups/SearchAccountModal.vue'
// import SearchContractModal from '../ChangeInstallUnit/popup/SearchContract/SearchContractModal.vue'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
export default {
    name:'ThongTinKhachHang',
    components:{
        KDatePicker,
        ChiTietNganhNgheModal,
        DiaChiKhachHangModal,
        SearchAccountModal,
        SearchContractModal
    },
    data(){
        return {
            ngay_yc:moment(new Date()).format('DD/MM/YYYY'),
            ngay_cap:'',
            ngay_hethan:'',
            ngay_sinh:'',
            radio_gioitinh:0,
            loaihd_id:33,
            input_ma_gd:'',
            input_ma_kh:'',
            input_matb:'',
            readonlySomay:false,
            readonlyMaKH:false,
            input_tenkh:'',
            input_diachi_kh:'',
            input_sogiayto:'',
            input_noicap:'',
            input_so_dt:'',
            disabledBtnDiaChiKH:false,
            loai_gt:[],
            loai_gt_selected:null,
            ds_nganhnghe:[],
            nganhnghe_selected:null,
            ds_quoctich:[],
            quoctich_selected:null,
            dataTieuNganh:{
              TNC1_ID:0,
              TNC2_ID:0,
              TNC3_ID:0,
              TNC4_ID:0,
              nganhnghe_id:0
            },
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
            dataPopupTieuNganh:{
              TNC1_ID:0,
              TNC2_ID:0,
              TNC3_ID:0,
              TNC4_ID:0,
              nganhnghe_id:0
            },

        }
    },
    methods:{
        onChangeNgaySinhKH(value){
          this.$emit('onChangeNgaySinhKH', value)
        },
        onEnterTenKH(){
            if(this.input_tenkh.trim().length>0){
                this.chonDiaChiKH()
            }
        },
        onBlurTenKH(){
            if(this.input_tenkh.trim().length>0){
                this.$emit('onBlurTenKH', this.input_tenkh.trim())
            }
        },
        onEnterMaGD(){
            if(this.input_ma_gd.trim().length>0){
                this.$emit('searchMaGD', this.input_ma_gd.trim())
            }
        },
        onEnterMaTB(){
            if(this.input_matb.trim().length>0){
                this.$emit('onEnterMaTB', this.input_matb.trim())
            }
        },
        acceptSearchContract(item){
          console.log(item)
          this.input_ma_gd=item.ma_gd
          this.$emit('searchMaGD', item.ma_gd)
        },
        showPopupSearchContract(){
          //tra cứu phụ lục hợp đồng
          this.$refs.searchContractModal.showModal()
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
        focusMaKH(){
            this.$refs.inputMaKH.focus()
        },
        focusTenKH(){
            this.$refs.inputTenKH.focus()
        },
        focusMaGD(){
            this.$refs.inputMaGD.focus()
        },
        focusSoDT(){
            this.$refs.inputSDT.focus()
        },
        focusDiaChiKH(){
            this.$refs.inputDiaChiKH.focus()
        },
        focusSoGT(){
            this.$refs.inputSoGT.focus()
        },
        onEnterMaKH(){
            if(this.input_ma_kh.trim().length>0){
                this.$emit('searchMaKH', this.input_ma_kh.trim())
            }
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
        onBlurSoGT(){
          if(this.input_sogiayto.trim()==''){
            return
          }
          this.$emit('onBlurSoGT', this.input_sogiayto.trim())
        },
        chonDiaChiKH(){
            Object.assign(this.dataDiaChi, this.dataDiaChiKH)
            //địa chỉ khách hàng
            this.$refs.diaChiKhachHangModal.showModal()
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
        changeMaKH(e){
            this.input_ma_kh=e.target.value
            this.$emit('changeMaKH', e.target.value)
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
        acceptTieuNganh(data){
          Object.assign(this.dataTieuNganh, data)
          this.nganhnghe_selected=this.dataTieuNganh.nganhnghe_id
        },
        async HienThiTT_DanhBa_KHDN(item){
            this.input_ma_kh=item.ma_kh?item.ma_kh.toString():''
            this.input_tenkh=item.full_name?item.full_name.toString():''
            this.input_diachi_kh=item.addr?item.addr.toString():''

            if(item.ngay_thanh_lap!=null&&item.ngay_thanh_lap.toString()!=''){
                this.ngay_sinh=moment(item.ngay_thanh_lap,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY')
            }else{
                this.ngay_sinh=''
            }
            


            this.ngay_yc=moment(new Date()).format('DD/MM/YYYY')

            this.radio_gioitinh=0
            this.input_so_dt=item.phone?item.phone.toString():''

            if(item.nganh_nghe){
                this.dataTieuNganh.nganhnghe_id=item.nganh_nghe!=null?Number(item.nganh_nghe):0
                this.nganhnghe_selected=item.nganh_nghe!=null?Number(item.nganh_nghe):0
            }

            this.dataTieuNganh.TNC1_ID=item.tnc1_id?item.tnc1_id:0
            this.dataTieuNganh.TNC2_ID=item.tnc2_id?item.tnc2_id:0
            this.dataTieuNganh.TNC3_ID=item.tnc3_id?item.tnc3_id:0
            this.dataTieuNganh.TNC4_ID=item.tnc4_id?item.tnc4_id:0

            this.ngay_hethan=''
            this.input_sogiayto=item.so_gt?item.so_gt.toString():''

            this.ngay_cap = null
            if(item.loai_gt&&(item.loai_gt=='2'||item.loai_gt=='1')){
                this.loai_gt_selected=item.loai_gt
                if(item.ngay_thanh_lap!=null&&item.ngay_thanh_lap.toString()!=''){
                    this.ngay_cap=moment(item.ngay_thanh_lap,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY')
                }else{
                    this.ngay_cap=''
                }
            }

        },
        async HienThiTT_DanhBa(item){
            this.input_ma_kh=item.ma_kh?item.ma_kh.toString():''
            this.input_tenkh=item.ten_kh?item.ten_kh.toString():''
            this.input_diachi_kh=item.diachi_kh?item.diachi_kh.toString():''
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
            this.quoctich_selected=item.quoctich_id
            this.input_so_dt=item.so_dt?item.so_dt.toString():''

            if(item.nganhnghe_id){
                this.dataTieuNganh.nganhnghe_id=item.nganhnghe_id!=null?item.nganhnghe_id:0
                this.nganhnghe_selected=item.nganhnghe_id
            }else{
                this.dataTieuNganh.nganhnghe_id=999
                this.nganhnghe_selected=999
                this.dataTieuNganh.TNC1_ID=9991
                this.dataTieuNganh.TNC2_ID=99911
                this.dataTieuNganh.TNC3_ID=999111
                this.dataTieuNganh.TNC4_ID=9991111
            }
            this.dataTieuNganh.TNC1_ID=item.tnc1_id?item.tnc1_id:0
            this.dataTieuNganh.TNC2_ID=item.tnc2_id?item.tnc2_id:0
            this.dataTieuNganh.TNC3_ID=item.tnc3_id?item.tnc3_id:0
            this.dataTieuNganh.TNC4_ID=item.tnc4_id?item.tnc4_id:0

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
            if(this.loaihd_id!=LoaiHopDong.KHAOSATDATMOI){
                this.input_matb=item.ma_tb!=null?item.ma_tb:''
            }else{
                this.input_matb=''
            }
           
        },
        async HienThiTTHopDongKH(item){
            this.ngay_yc=item.ngay_yc?moment(item.ngay_yc,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY'):''
            this.input_ma_kh=item.ma_kh?item.ma_kh.toString():''
            this.input_tenkh=item.ten_kh?item.ten_kh.toString():''
            this.input_diachi_kh=item.diachi_kh?item.diachi_kh.toString():''
            this.input_ma_gd=item.ma_gd?item.ma_gd.toString():''
            if(item.ngay_sn!=null&&item.ngay_sn.toString()!=''){
                this.ngay_sinh=item.ngay_sn?moment(item.ngay_sn,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY'):''
            }else{
                this.ngay_sinh=''
            }
            if(item.gioitinh){
                if(item.gioitinh==0){
                    this.radio_gioitinh=1
                }else{
                    this.radio_gioitinh=2
                }
            }else{
                this.radio_gioitinh=0
            }
            this.quoctich_selected=item.quoctich_id?item.quoctich_id:1
            this.input_so_dt=item.so_dt?item.so_dt.toString():''
            this.nganhnghe_selected=item.nganhnghe_id?item.nganhnghe_id:0
            this.dataTieuNganh.nganhnghe_id=item.nganhnghe_id?item.nganhnghe_id:0
            this.dataTieuNganh.TNC1_ID=item.tnc1_id?item.tnc1_id:0
            this.dataTieuNganh.TNC2_ID=item.tnc2_id?item.tnc2_id:0
            this.dataTieuNganh.TNC3_ID=item.tnc3_id?item.tnc3_id:0
            this.dataTieuNganh.TNC4_ID=item.tnc4_id?item.tnc4_id:0
            if(item.ngayhethan_gt!=null&&item.ngayhethan_gt.toString()!=''){
                //chưa rõ format date đầu vào
                this.ngay_hethan=moment(item.ngayhethan_gt,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY')
            }else {
                this.ngay_hethan=''
            }
            this.input_sogiayto=item.so_gt!=null?item.so_gt.toString():''
            if(item.ngaycap!=null&&item.ngaycap.toString()!=''){
                //chưa rõ format date đầu vào
                this.ngay_cap=moment(item.ngaycap,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY')
            }else{
                this.ngay_cap=''
            }
            this.loai_gt_selected=item.loaigt_id
            this.input_noicap=item.noicap!=null?item.noicap:''
        },
        async HienThiTTKhachHang(item){
            this.ngay_yc=item.ngay_yc?moment(item.ngay_yc,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY'):''
            this.input_ma_kh=item.ma_kh?item.ma_kh.toString():''
            this.input_tenkh=item.ten_kh?item.ten_kh.toString():''
            this.input_diachi_kh=item.diachi_kh?item.diachi_kh.toString():''

            if(item.ngay_sn!=null&&item.ngay_sn.toString()!=''){
                this.ngay_sinh=item.ngay_sn?moment(item.ngay_sn,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY'):''
            }else{
                this.ngay_sinh=''
            }
            if(item.gioitinh){
                if(item.gioitinh==0){
                    this.radio_gioitinh=1
                }else{
                    this.radio_gioitinh=2
                }
            }else{
                this.radio_gioitinh=0
            }
            this.quoctich_selected=item.quoctich_id?item.quoctich_id:1
            this.input_so_dt=item.so_dt?item.so_dt.toString():''
            this.nganhnghe_selected=item.nganhnghe_id?item.nganhnghe_id:0

            this.dataTieuNganh.nganhnghe_id=item.nganhnghe_id?item.nganhnghe_id:0
            this.dataTieuNganh.TNC1_ID=item.tnc1_id?item.tnc1_id:0
            this.dataTieuNganh.TNC2_ID=item.tnc2_id?item.tnc2_id:0
            this.dataTieuNganh.TNC3_ID=item.tnc3_id?item.tnc3_id:0
            this.dataTieuNganh.TNC4_ID=item.tnc4_id?item.tnc4_id:0

            if(item.ngayhethan_gt!=null&&item.ngayhethan_gt.toString()!=''){
                //chưa rõ format date đầu vào
                this.ngay_hethan=moment(item.ngayhethan_gt,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY')
            }else {
                this.ngay_hethan=''
            }
            this.input_sogiayto=item.so_gt!=null?item.so_gt.toString():''

            if(item.ngaycap!=null&&item.ngaycap.toString()!=''){
                //chưa rõ format date đầu vào
                this.ngay_cap=moment(item.ngaycap,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY')
            }else{
                this.ngay_cap=''
            }
            this.loai_gt_selected=item.loaigt_id
            this.input_noicap=item.noicap!=null?item.noicap:''
        },
        async lay_danhmuc_lapmoi_tt_chung_v7(chuoi){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.lay_danhmuc_lapmoi_tt_chung_v7(this.axios, chuoi)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return null
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Không load được danh mục chung')
                }
                return null
            }
        },
        async Clear(){
            this.input_matb=''
            this.input_ma_kh=''
            this.input_ma_gd=''
            this.input_tenkh=''
            this.input_diachi_kh=''
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

            this.ngay_sinh=''
            this.ngay_yc=moment(new Date()).format('DD/MM/YYYY')
            this.input_sogiayto=''
            this.ngay_cap=''
            this.input_noicap=''
            this.radio_gioitinh=0
            if(this.loai_gt.length>0){
                this.loai_gt_selected=this.loai_gt[0].loaigt_id
            }
            if(this.ds_quoctich.length>0){
                this.quoctich_selected=this.ds_quoctich[0].quoctich_id
            }
            this.ngay_hethan=''
            this.input_so_dt=''
            this.dataTieuNganh={
              TNC1_ID:0,
              TNC2_ID:0,
              TNC3_ID:0,
              TNC4_ID:0,
              nganhnghe_id:this.nganhnghe_selected?this.nganhnghe_selected:0
            }
            

        },
        async initDuLieu(){
            //Handle form trùng
            let endPath=this.$router.currentRoute.path.split('/').pop()
            if(endPath=='SurveyRequestCrossSale'){
                //Tiếp nhận yêu cầu khảo sát lắp đặt bán chéo
                this.loaihd_id=LoaiHopDong.KHAOSATDATMOI
            }
            if(this.loaihd_id==LoaiHopDong.KHAOSATDATMOI||this.loaihd_id==LoaiHopDong.TIEPNHANHD){
                //enable số máy
                this.readonlySomay=true
            }
            if(this.loaihd_id==LoaiHopDong.KHAOSATDC||this.loaihd_id==LoaiHopDong.KHAOSATCDLH){
                this.readonlyMaKH=true
            }
            if(this.loaihd_id==LoaiHopDong.KHAOSATDC){
                this.disabledBtnDiaChiKH=true
            }

            let result = await this.lay_danhmuc_lapmoi_tt_chung_v7('LOAI_GT|NGANHNGHE|QUOCTICH')
            if(result&&result.loai_gt){
                this.loai_gt=result.loai_gt
                if(this.loai_gt.length>0){
                    this.loai_gt_selected=this.loai_gt[0].loaigt_id
                }
            }
            if(result&&result.nganhnghe){
                this.ds_nganhnghe=result.nganhnghe
                if(this.ds_nganhnghe.length>0){
                    this.nganhnghe_selected=this.ds_nganhnghe[0].nganhnghe_id
                    
                }
            }
            if(result&&result.quoctich){
                this.ds_quoctich=result.quoctich
                if(this.ds_quoctich.length>0){
                    this.quoctich_selected=this.ds_quoctich[0].quoctich_id
                }
            }
            console.log('result', result)

        }
    }

}
</script>