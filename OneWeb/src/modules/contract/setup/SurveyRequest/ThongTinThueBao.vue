<template>
  <div class="col-sm-6 col-12">
    <div class="box-form">
      <div class="legend-title">Thông tin thuê bao</div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Dịch vụ VT <k-required-marker/></div>
            <div class="value">
                <div class="select-custom" ref="ds_dichvu">
                    <select2 :settings="{ dropdownParent: $refs['ds_dichvu'] }" ref="cboDichVu"
                        v-model="dichvu_selected" class="select2"
                        :options="ds_dichvu.map(e=> ({id: e.dichvuvt_id, text: e.ten_dvvt}))"
                        @change="changeDichVu"
                    >
                    </select2>
                </div>  
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Loại hình TB <k-required-marker/></div>
            <div class="value">
                <div class="select-custom" ref="ds_loaihinh">
                    <select2 :settings="{ dropdownParent: $refs['ds_loaihinh'] }" ref="cboLoaiHinh"
                        v-model="loaihinh_selected" class="select2"
                        :options="loaihinh_tb_filter.map(e=> ({id: e.loaitb_id, text: e.loaihinh_tb}))">
                    </select2>
                </div>  
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Kiểu yêu cầu</div>
            <div class="value">
                <div class="select-custom" ref="ds_kieu_yc">
                    <select2 :settings="{ dropdownParent: $refs['ds_kieu_yc'] }" ref="cboKieuYC"
                        v-model="kieu_yc_selected" class="select2"
                        :options="ds_kieu_yc.map(e=> ({id: e.KIEULD_ID, text: e.TEN_KIEULD}))">
                    </select2>
                </div>  
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Loại KH</div>
            <div class="value">
                <div class="select-custom" ref="ds_loai_kh">
                    <select2 :settings="{ dropdownParent: $refs['ds_loai_kh'] }" ref="cboLoaiKH"
                        v-model="loai_kh_selected" class="select2"
                        :options="ds_loai_kh.map(e=> ({id: e.loaikh_id, text: e.ten_loaikh_dis}))"
                        @change="changeLoaiKH"
                      >
                    </select2>
                </div>  
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Tên TB <k-required-marker/></div>
            <div class="value">
              <input type="text" ref="inputTenTB" :value="input_ten_tb" @change="e=>input_ten_tb=e.target.value" class="form-control bold" />
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Ngày sinh</div>
            <div class="value">
              <div class="input-icon-right">
                <k-date-picker v-model="ngay_sinh"/>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key w90">Địa chỉ TB <k-required-marker/></div>
        <div class="value">
          <div class="input-more-button">
            <button class="btn" @click="clickChonDiaChi(1)" title="Chọn địa chỉ thuê bao">
              <span class="-ap icon-more_horiz"></span>
            </button>
            <input type="text" ref="inputDiaChiTB" @keypress="evt=>evt.preventDefault()" :value="input_diachi_tb" @change="e=>input_diachi_tb=e.target.value" class="form-control bold" />
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key w90">Địa chỉ LĐ</div>
        <div class="value">
          <div class="input-more-button">
            <button class="btn" @click="clickChonDiaChi(2)" title="Chọn địa chỉ lắp đặt">
              <span class="-ap icon-more_horiz"></span>
            </button>
            <input type="text" ref="inputDiaChiLD" @keypress="evt=>evt.preventDefault()" :value="input_diachi_ld" @change="e=>input_diachi_ld=e.target.value" class="form-control bold" />
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Đơn vị xử lý</div>
            <div class="value">
                <div class="select-custom" ref="ds_donvi_xyly">
                    <select2 :settings="{ dropdownParent: $refs['ds_donvi_xyly'] }" ref="cboDonViXL"
                        v-model="donvi_xyly_selected" class="select2"
                        @change="changeDaiVT"
                        :options="ds_donvi_xyly.map(e=> ({id: e.donvi_id, text: e.ten_dv}))">
                    </select2>
                </div>  
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Khu vực</div>
            <div class="value">
              <div class="input-more-button">
                <button class="btn" @click="clickKhuVuc" title="Địa bàn quản lý thuê bao theo địa chỉ lắp đặt">
                  <span class="-ap icon-more_horiz"></span>
                </button>
                <input type="text" ref="inputKhuVuc" @keypress="evt=>evt.preventDefault()" :value="input_khuvuc" @change="e=>input_khuvuc=e.target.value" class="form-control" />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Đối tượng TB</div>
            <div class="value">
                <div class="select-custom" ref="ds_doituong_tb">
                    <select2 :settings="{ dropdownParent: $refs['ds_doituong_tb'] }" ref="cboDoiTuong"
                        v-model="doituong_selected" class="select2"
                        :options="ds_doituong_tb.map(e=> ({id: e.doituong_id, text: e.ten_dt}))">
                    </select2>
                </div>  
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Số lượng</div>
            <div class="value">
              <input type="text" @blur="blurInputSoLuong" @keypress="isNumber($event)" :value="input_soluong" @change="e=>input_soluong=e.target.value" class="form-control tright" />
            </div>
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key w90">Nhân viên</div>
        <div class="value">
              <multiselect v-model="nhanvien_selected"
                :options="ds_nhanvien"
                placeholder="Chọn nhân viên"
                @input="chonNhanVienKhuVuc"
                :multiple="true"
                :close-on-select="false"
                :clear-on-select="false"
                :preserve-search="true"
                :disabled="!enabledNhanVien"
                label="ten_nv"
                track-by="nhanvien_id">
                <template #noOptions>
                    Không tìm thấy thông tin
                </template>
              </multiselect>

        </div>
      </div>
      <div class="info-row">
        <div class="key w90">Ghi chú</div>
        <div class="value">
          <input type="text"  :value="input_ghichu" @change="e=>input_ghichu=e.target.value" class="form-control" />
        </div>
      </div>
    </div>
    <!-- Modal -->
    <DiaChiModal ref="diaChiModal" :data="dataDiaChi" @accept="onAcceptDiaChi"/>
    <ThayDoiKhuVucQuanLyThueBaoModal ref="thayDoiKhuVucQuanLyThueBaoModal"
       :data="dataPopupKhuVuc"
       @accept="onAcceptKhuVuc"
       @success_update="successUpdateThayDoiKhuVucQuanLy"/>
    <ChonDonViModal ref="chonDonViModal" :data="dsToThiCong" @accept="acceptChonDonVi"/>
  </div>
</template>
<script>
import SurveyRequestAPI from './SurveyRequestAPI'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import {DichVuVienThong, LoaiHopDong, LoaiNV} from './ThamSo'
import DiaChiModal from './popups/ChonDiaChi/DiaChiModal.vue'
import ThayDoiKhuVucQuanLyThueBaoModal from './popups/ThayDoiKhuVucQuanLyThueBao/ThayDoiKhuVucQuanLyThueBaoModal.vue'
import ChonDonViModal from './popups/ChonDonVi/ChonDonViModal.vue'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
import { DICHVU_VIENTHONG } from '../../../../constants'
export default {
    name:'ThongTinThueBao',
    props:{
      hdtb:{
          type:Object,
          default:()=>{}
      },
      //Tham số
      LOC_NVDIADANH_NVPX:{
        type:Boolean,
        default:false
      }
    },
    components:{
        KDatePicker,
        DiaChiModal,
        ThayDoiKhuVucQuanLyThueBaoModal,
        ChonDonViModal,
        KRequiredMarker
    },
    computed:{
      loainhanvien_id(){
        if(this.loai_kh_selected==null||this.ds_loai_kh.length==0){
          return 0
        }
        let loaiKH=this.ds_loai_kh.find(x=>x.loaikh_id==this.loai_kh_selected)
        if(loaiKH==undefined){
          return 0
        }
        if(loaiKH.loai==0){
          return LoaiNV.NHANVIEN_KINHDOANH
        }else if(loaiKH.loai==1){
          return LoaiNV.NHANVIEN_AM
        }else return 0
      },
      ten_loaihinh(){
        if(this.loaihinh_selected==null){
          return ''
        }
        const index=this.loaihinh_tb_filter.findIndex(x=>x.loaitb_id==this.loaihinh_selected)
        return index>-1?this.loaihinh_tb_filter[0].loaihinh_tb:''
      }
    },
    data(){
        return {
            ds_dichvu:[],
            dichvu_selected:null,
            ds_loaihinh:[],
            loaihinh_selected:null,
            ds_kieu_yc:[],
            kieu_yc_selected:null,
            ds_loai_kh:[],
            loai_kh_selected:null,
            ds_donvi_xyly:[],
            donvi_xyly_selected:null,
            ds_doituong_tb:[],
            doituong_selected:null,
            ds_nhanvien:[],
            nhanvien_selected:null,
            loaihinh_tb_filter:[],
            //KHAOSATDATMOI
            loaihd_id:33,
            ngay_sinh:'',
            //isChangeDichVu:true

            input_ten_tb:'',
            input_diachi_tb:'',
            input_diachi_ld:'',
            input_khuvuc:'',
            input_soluong:'1',
            input_ghichu:'',
            
            kieu_chon_diachi:1,
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
              diachi:''
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
              diachi:''
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
            //1: Type MaGD enter, 2: Type MaKH enter
            action_type:1,
            khuvuc_selected:null,
            //LOAI_DV.TRAM_VT=5
            loaidv_id:5,
            thuebao_id:0,
            //hard
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
            dsToThiCong:[],
            isFocusDichVu:false,
            enabledNhanVien:false
        }
    },
    methods:{
        async changeDichVu(){
            if(this.dichvu_selected==null){
              this.loaihinh_tb_filter=[]
              this.loaihinh_selected=null
              return
            }
            this.loaihinh_tb_filter=this.ds_loaihinh.filter(x=>x.dichvuvt_id==this.dichvu_selected)
            if(this.loaihinh_tb_filter.length>0){
              this.loaihinh_selected=this.loaihinh_tb_filter[0].loaitb_id
            }
        },
        changeLoaiKH(){
          if(this.dataDiaChiKH.diachi.length==0){
            return
          }
          if(this.loai_kh_selected==null){
            return
          }
          this.laydv_quanly()

        },
        nameWithNhanVien (object) {
          return `${object.ma_nv} — ${object.ten_nv}`
        },
        chonNhanVienKhuVuc(data){
          console.log('nhanvien_selected', this.nhanvien_selected)
          
        },
        clickChonDiaChi(value){
          //1: Địa chỉ TB, 2: Địa chỉ lắp đặt
          this.kieu_chon_diachi=value
          //khởi tạo dữ liệu, địa chỉ khách hàng
          if(this.kieu_chon_diachi==1){
            // this.dataDiaChi={ "type": "btnDiaChiTT", "diachi_id": 0, "tinh_id": "26", "quan_id": "1", "phuong_id": 398, 
            // "pho_id": 93, "ap_id": 0, "khu_id": 0, "dacdiem_id": 0,
            //  "sonha": "0", "block": "", "tang": "", "sophong": "", 
            //  "motathem": "",
            //   "diachi": "", "value": "Địa chỉ thanh toán" };
            Object.assign(this.dataDiaChi, this.dataDiaChiTB)
          }else if(this.kieu_chon_diachi==2){
            Object.assign(this.dataDiaChi, this.dataDiaChiLD)
          }
          this.$refs.diaChiModal.showModal()
          
        },
        onAcceptDiaChi(data){
          console.log('data', data)
          if(this.kieu_chon_diachi==1){
            this.input_diachi_tb=data.diachi
            Object.assign(this.dataDiaChiTB, data)
            //Chọn địa chỉ TB apply luôn qua lắp đặt
            this.input_diachi_ld=data.diachi
            Object.assign(this.dataDiaChiLD, data)
            //Handle chọn đơn vị tổ thi công
            this.handleChonToThiCongSauChonTB()

          }else if(this.kieu_chon_diachi==2){
            this.input_diachi_ld=data.diachi
            Object.assign(this.dataDiaChiLD, data)
            //Handle chọn đơn vị tổ thi công
            this.handleChonToThiCongSauChonLD()

          }

        },
        async handleChonToThiCongSauChonTB(){
          // phuong_id, pho_id, ap_id, khu_id, kieu
          try{
            this.loading(true)
            //let response = await SurveyRequestAPI.lay_ds_donviql(this.axios, 327, 0, 0, 0, 2)
            let response = await SurveyRequestAPI.lay_ds_donviql(this.axios, this.dataDiaChiTB.phuong_id, this.dataDiaChiTB.pho_id, this.dataDiaChiTB.ap_id, this.dataDiaChiTB.khu_id, 2)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data &&response.data.data.length>0) {
              let result=response.data.data
              console.log('result', result)
              if(result.length==1){
                //  if (hdkh_id == 0) //Nếu chưa lập hợp đồng thì thay đổi luôn cả thông tin lắp đặt
                //               cboDaiVT.SelectedValue = Convert.ToInt32(ds.Tables[0].Rows[0]["donvi_id"].ToString());
                // if(this.hdtb.hdkh_id==undefined||this.hdtb.hdkh_id==0){
                //   this.donvi_xyly_selected=result[0].donvi_id
                // }
                let donvi_id=result[0].donvi_id.toString()
                const index=this.ds_donvi_xyly.findIndex(x=>x.donvi_id==donvi_id)
                if(index>-1){
                  this.donvi_xyly_selected=donvi_id
                }else{
                  this.donvi_xyly_selected=null
                }
                //nhét tạm
                this.lay_nv_banhang_kv()
                
              }else if (result.length>1){
                this.dsToThiCong=result
                this.$refs.chonDonViModal.showModal()
              }
            }else{
              this.$toast.warning('Địa danh này chưa được gán đơn vị quản lý địa chỉ lắp đặt. Hãy liên hệ với Admin để xử lý!')
              this.donvi_xyly_selected=null
              return
            }
          }catch(e){
            this.loading(false)
          }
        },
        async handleChonToThiCongSauChonLD(){
          try{
            this.loading(true)
            //loaidvId, phuongId, phoId, apId, khuId
            var result=[]
            let response = await SurveyRequestAPI.lay_ds_donvi_theo_loaidv_diachild(this.axios, this.loaidv_id, this.dataDiaChiLD.phuong_id, this.dataDiaChiLD.pho_id, this.dataDiaChiLD.ap_id, this.dataDiaChiLD.khu_id)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              result=response.data.data
              if(result.length==0){
                let response1 = await SurveyRequestAPI.lay_ds_donvi_theo_loaidv_diachild(this.axios, this.loaidv_id, this.dataDiaChiLD.phuong_id, this.dataDiaChiLD.pho_id, this.dataDiaChiLD.ap_id, 0)
                if(response1 && response1.data && response1.data.error_code && response1.data.error_code == 'BSS-00000000' && response1.data.data) {
                  result=response1.data.data
                  if(result.length==0){
                    let response2 = await SurveyRequestAPI.lay_ds_donvi_theo_loaidv_diachild(this.axios, this.loaidv_id, this.dataDiaChiLD.phuong_id, this.dataDiaChiLD.pho_id, 0, 0)
                    if(response2 && response2.data && response2.data.error_code && response2.data.error_code == 'BSS-00000000' && response2.data.data) {
                      result=response2.data.data
                    }
                  }
                }
              }
            }
            this.loading(false)
            //handle result
            console.log('resultLD', result) 
            if(result.length==0){
              this.loading(false)
              this.$toast.warning('Địa danh này chưa được gán đơn vị quản lý địa chỉ lắp đặt. Hãy liên hệ với Admin để xử lý!')
              this.donvi_xyly_selected=null
              return 
            }else if(result.length==1){
              //this.donvi_xyly_selected=result[0].donvi_id.toString()
              
              //
              let donvi_id=result[0].donvi_id.toString()
              const index=this.ds_donvi_xyly.findIndex(x=>x.donvi_id==donvi_id)
              if(index>-1){
                this.donvi_xyly_selected=donvi_id
              }else{
                this.donvi_xyly_selected=null
              }
              // handle Lay_NV_BanHang_KV
              this.lay_nv_banhang_kv()
              
            }else{
              this.dsToThiCong=result
              this.$refs.chonDonViModal.showModal()
            }
          }catch(e){
            this.loading(false)
          }
        },
        acceptChonDonVi(data){
          if(data!=null){
            if(this.kieu_chon_diachi==1){
              //this.donvi_xyly_selected=data.donviql_id.toString()
              //
              let donvi_id=data.donviql_id.toString()
              const index=this.ds_donvi_xyly.findIndex(x=>x.donvi_id==donvi_id)
              if(index>-1){
                this.donvi_xyly_selected=donvi_id
              }else{
                this.donvi_xyly_selected=null
              }
              //nhét tạm
              this.lay_nv_banhang_kv()

            }else if(this.kieu_chon_diachi==2){
              //this.donvi_xyly_selected=data.donvi_id.toString()
              let donvi_id=data.donvi_id.toString()
              const index=this.ds_donvi_xyly.findIndex(x=>x.donvi_id==donvi_id)
              if(index>-1){
                this.donvi_xyly_selected=donvi_id
              }else{
                this.donvi_xyly_selected=null
              }
              // handle Lay_NV_BanHang_KV
              this.lay_nv_banhang_kv()
            }else if(this.kieu_chon_diachi==3){
              //calback địa chỉ KH
              //this.donvi_xyly_selected=data.donviql_id.toString()
              let donvi_id=data.donviql_id.toString()
              const index=this.ds_donvi_xyly.findIndex(x=>x.donvi_id==donvi_id)
              if(index>-1){
                this.donvi_xyly_selected=donvi_id
              }else{
                this.donvi_xyly_selected=null
              }
              // handle Lay_NV_BanHang_KV
              this.lay_nv_banhang_kv()
            }
          }
        },
        clickKhuVuc(){
          if(this.dataDiaChiLD.quan_id==0||this.dataDiaChiLD.phuong_id==0){

            this.khuvuc_selected=null
            this.ds_nhanvien=[]
            this.input_khuvuc=''
            this.$refs.inputKhuVuc.focus()

            this.$toast.warning('Chưa có thông tin về địa chỉ lắp đặt. Bạn hãy kiểm tra lại!')
            return
          }

          this.dataPopupKhuVuc.quan_id=this.dataDiaChiLD.quan_id
          this.dataPopupKhuVuc.phuong_id=this.dataDiaChiLD.phuong_id
          this.dataPopupKhuVuc.pho_id=this.dataDiaChiLD.pho_id
          this.dataPopupKhuVuc.ap_id=this.dataDiaChiLD.ap_id
          this.dataPopupKhuVuc.khu_id=this.dataDiaChiLD.khu_id
          this.dataPopupKhuVuc.dacdiem_id=this.dataDiaChiLD.dacdiem_id

          this.dataPopupKhuVuc.khuvuc_id=this.khuvuc_selected!=null?this.khuvuc_selected.khuvuc_id:0
          this.dataPopupKhuVuc.donviql_id=this.donvi_xyly_selected!=null?this.donvi_xyly_selected:0

          this.dataPopupKhuVuc.hdtb_id=(this.hdtb.hdtb_id!=undefined&&this.hdtb.hdtb_id!=null)?this.hdtb.hdtb_id:0



          this.$refs.thayDoiKhuVucQuanLyThueBaoModal.showModal()
        },
        async onAcceptKhuVuc(data){
          console.log('onAcceptKhuVuc', data)
          this.khuvuc_selected=data
          this.input_khuvuc= this.khuvuc_selected.ma_kv
          if(this.khuvuc_selected.khuvuc_id!=0){
            //cboNhanVien.Properties.DataSource = tcdanhba.LAY_DS_NHANVIEN_KHUVUC_LNV(khuvuc_id, loai_nv_xuly).Tables[0];
            try{
              this.loading(true)
              let responseNhanVien = await SurveyRequestAPI.lay_ds_nhanvien_khuvuc_lnv(this.axios, this.khuvuc_selected.khuvuc_id, this.loainhanvien_id)
              this.loading(false)
              if(responseNhanVien && responseNhanVien.data && responseNhanVien.data.error_code && responseNhanVien.data.error_code == 'BSS-00000000' && responseNhanVien.data.data) {
                this.ds_nhanvien=responseNhanVien.data.data.map(x=>Object.assign(x,{
                    ten_nv:x.ma_nv+' - '+x.ten_nv
                  }))
              }else{
                this.ds_nhanvien=[]
              }
            }catch(e){
              this.loading(false)
              this.ds_nhanvien=[]

            }
            
          }else{
            this.nhanvien_selected=null
            this.ds_nhanvien=[]
          }

        },
        async changeDaiVT(){
          this.nhanvien_selected=null
          this.ds_nhanvien=[]
          if(this.loaihd_id==LoaiHopDong.TIEPNHANHD){
            this.ds_nhanvien=await this.lay_ds_nhanvien_kd(40, this.donvi_xyly_selected)
          }
        },
        focusTenTB(){
          this.$nextTick(()=>{
            this.$refs.inputTenTB.focus()
          })
        },
        // focusTenTB(){
        //   this.$nextTick(()=>{
        //     this.$refs.inputTenTB.focus()
        //   })
        // },
        focusDiaChiLD(){
          this.$nextTick(()=>{
            this.$refs.inputDiaChiLD.focus()
          })
        },
        focusDiaChiTB(){
          this.$nextTick(()=>{
            this.$refs.inputDiaChiTB.focus()
          })
        },
        successUpdateThayDoiKhuVucQuanLy(){
          this.$emit('successUpdateThayDoiKhuVucQuanLy')
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
        blurInputSoLuong(){
          if(this.input_soluong.trim()==''||this.input_soluong.trim()=='0'){
            this.input_soluong='1'
          }else{
            this.input_soluong=Number(this.input_soluong.trim()).toString()
          }
        },
        getInfoFormThueBao(){
          return {
            input_ten_tb:this.input_ten_tb,
            input_diachi_tb:this.input_diachi_tb,
            input_diachi_ld:this.input_diachi_ld,
            input_khuvuc:this.input_khuvuc,
            input_soluong:this.input_soluong,
            input_ghichu:this.input_ghichu,
            dataDiaChiLD:this.dataDiaChiLD,
            dataDiaChiTB:this.dataDiaChiTB,
            donvi_xyly_selected:this.donvi_xyly_selected,
            kieu_yc_selected:this.kieu_yc_selected,
            loaihinh_selected:this.loaihinh_selected,
            doituong_selected:this.doituong_selected,
            ngay_sinh:this.ngay_sinh,
            khuvuc_selected:this.khuvuc_selected,
            ds_nhanvien:this.ds_nhanvien,
            nhanvien_selected:this.nhanvien_selected,
            dichvu_selected:this.dichvu_selected,
            ten_loaihinh:this.ten_loaihinh
          }
        },
        initDiaChiKH(data){
          Object.assign(this.dataDiaChiKH, data)
        },
        setDiaChiLD_TB_Theo_KH(data){
          Object.assign(this.dataDiaChiKH, data)
          let object={
            tinh_id:data.tinh_id,
            quan_id:data.quan_id,
            phuong_id:data.phuong_id,
            pho_id:data.pho_id,
            ap_id:data.ap_id,
            khu_id:data.khu_id,
            dacdiem_id:data.dacdiem_id,
            sonha:data.sonha,
            diachi:data.diachi
          }
          Object.assign(this.dataDiaChiLD, object)
          Object.assign(this.dataDiaChiTB, object)

          console.log('dataDiaChiKH', this.dataDiaChiKH)
          console.log('dataDiaChiTB', this.dataDiaChiTB)
          console.log('dataDiaChiLD', this.dataDiaChiLD)

          

          this.input_diachi_tb=data.diachi
          this.input_diachi_ld=data.diachi

          //LayDV_QuanLy
          this.laydv_quanly() 
        },
        async laydv_quanly(){
          try{
            this.loading(true)
            //handle
            let param={
              vphuong_id: this.dataDiaChiKH.phuong_id,
              vpho_id: this.dataDiaChiKH.pho_id,
              vap_id: this.dataDiaChiKH.ap_id,
              vkhu_id: this.dataDiaChiKH.khu_id,
              vkieu: 1,
              vloaikh_id: this.loai_kh_selected!=null?this.loai_kh_selected:0
            }
            var result=[]
            let response = await SurveyRequestAPI.lay_ds_donviql_v1(this.axios, param)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              result=response.data.data
              if(result.length==0){
                //
                param.vkhu_id=0
                let response1 = await SurveyRequestAPI.lay_ds_donviql_v1(this.axios, param)
                if(response1 && response1.data && response1.data.error_code && response1.data.error_code == 'BSS-00000000' && response1.data.data) {
                  result=response1.data.data
                  if(result.length==0){
                    //vap_id=0, khu_id=0
                    param.vap_id=0
                    let response2 = await SurveyRequestAPI.lay_ds_donviql_v1(this.axios, param)
                    if(response2 && response2.data && response2.data.error_code && response2.data.error_code == 'BSS-00000000' && response2.data.data) {
                      result=response2.data.data
                    }
                  }
                }
              }
            }
            this.loading(false)
            //handle result
            //console.log('resultLD', result) 
            if(result.length==0){
              if(result.length==0){
                this.loading(false)
                this.$toast.warning('Địa danh này chưa được gán đơn vị quản lý địa chỉ thanh toán. Hãy liên hệ với Admin để xử lý!')
                this.donvi_xyly_selected=null
                return 
              }
            }else if(result.length==1){
              //this.donvi_xyly_selected=result[0].donvi_id.toString()
              let donvi_id=result[0].donvi_id.toString()
              const index=this.ds_donvi_xyly.findIndex(x=>x.donvi_id==donvi_id)
              if(index>-1){
                this.donvi_xyly_selected=donvi_id
              }else{
                this.donvi_xyly_selected=null
              }
              // handle Lay_NV_BanHang_KV
              await this.lay_nv_banhang_kv()
              
              
            }else{
              this.kieu_chon_diachi=3
              this.dsToThiCong=result
              this.$refs.chonDonViModal.showModal()
            }
          }catch(e){
            this.loading(false)
          }
        },
        async lay_nv_banhang_kv(){
          //
          let data={
            ap_id: this.dataDiaChiLD.ap_id,
            dacdiem_id: this.dataDiaChiLD.dacdiem_id,
            khu_id: this.dataDiaChiLD.khu_id,
            loaikv_id: 4,
            pho_id: this.dataDiaChiLD.pho_id,
            phuong_id: this.dataDiaChiLD.phuong_id,
            quan_id: this.dataDiaChiLD.quan_id,
            tramtc_id: this.donvi_xyly_selected!=null?this.donvi_xyly_selected:null
          }
          try{
            this.loading(true)
            let response=await SurveyRequestAPI.sp_lay_khuvuc_theo_diadanh(this.axios, data)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              this.khuvuc_selected=response.data.data[0]
              this.input_khuvuc=this.khuvuc_selected.ma_kv
              if(this.LOC_NVDIADANH_NVPX){
                //lay_ds_nhanvien_khuvuc_lnv_v2
                //comment tạm
                let responseNhanVien = await SurveyRequestAPI.lay_ds_nhanvien_khuvuc_lnv_v2(this.axios, this.khuvuc_selected.khuvuc_id, this.loainhanvien_id, this.dataDiaChiLD.phuong_id, this.dataDiaChiLD.pho_id, this.dataDiaChiLD.ap_id, this.dataDiaChiLD.khu_id, this.dataDiaChiLD.dacdiem_id)
                if(responseNhanVien && responseNhanVien.data && responseNhanVien.data.error_code && responseNhanVien.data.error_code == 'BSS-00000000' && responseNhanVien.data.data) {
                  this.ds_nhanvien=responseNhanVien.data.data.map(x=>Object.assign(x,{
                    ten_nv:x.ma_nv+' - '+x.ten_nv
                  }))
                }else{
                  this.ds_nhanvien=[]
                }

              }else{
                //lay_ds_nhanvien_khuvuc_lnv
                let responseNhanVien = await SurveyRequestAPI.lay_ds_nhanvien_khuvuc_lnv(this.axios, this.khuvuc_selected.khuvuc_id, this.loainhanvien_id)
                if(responseNhanVien && responseNhanVien.data && responseNhanVien.data.error_code && responseNhanVien.data.error_code == 'BSS-00000000' && responseNhanVien.data.data) {
                  this.ds_nhanvien=responseNhanVien.data.data.map(x=>Object.assign(x,{
                    ten_nv:x.ma_nv+' - '+x.ten_nv
                  }))
                }else{
                  this.ds_nhanvien=[]
                }
              }
              
            }else{
              this.input_khuvuc=''
              this.ds_nhanvien=[]
            }
            //check
            if(this.input_khuvuc==''&&this.ds_nhanvien.length==0){
              await this.changeDaiVT()
            }

          }catch(e){
            this.loading(false)
            this.$toast.error('Không load được khu vực nhân viên')
          }
        },
        
        async lay_danhmuc_lapmoi_tt_chung_v7(chuoi){
            try{
                this.loading(true)
                this.ds_dichvu=[]
                this.ds_loaihinh=[]
                //this.ds_kieu_yc=[]
                this.ds_loai_kh=[]
                let response = await SurveyRequestAPI.lay_danhmuc_lapmoi_tt_chung_v7(this.axios, chuoi)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    if(response.data.data.loaihinh_tb){
                        this.ds_loaihinh=response.data.data.loaihinh_tb
                    }
                    if(response.data.data.dichvu_vt){
                        if(this.loaihd_id==LoaiHopDong.KHAOSATDATMOI||this.loaihd_id==LoaiHopDong.KHAOSATDC||this.loaihd_id==LoaiHopDong.KHAOSATCDLH){
                            this.ds_dichvu=response.data.data.dichvu_vt.filter(x=>x.dichvuvt_id==DichVuVienThong.ADSL||x.dichvuvt_id==DichVuVienThong.IMS||
                                                                                x.dichvuvt_id==DichVuVienThong.MEGAWAN||x.dichvuvt_id==DichVuVienThong.CO_DINH||
                                                                                x.dichvuvt_id==DichVuVienThong.GPHONE||x.dichvuvt_id==DichVuVienThong.TSL||
                                                                                x.dichvuvt_id==DichVuVienThong.MEGA_EYES||x.dichvuvt_id==DichVuVienThong.HOINGHI_TRUYENHINH||
                                                                                x.dichvuvt_id == DichVuVienThong.DICHVU_CNTT|| x.dichvuvt_id==25||x.dichvuvt_id==26)
                            this.dichvu_selected=DichVuVienThong.CO_DINH
                        }else if(this.loaihd_id==LoaiHopDong.TIEPNHANHD){
                            this.ds_dichvu=response.data.data.dichvu_vt.filter(x=>x.dichvuvt_id==DichVuVienThong.ADSL||x.dichvuvt_id==DichVuVienThong.IMS||
                                                                                x.dichvuvt_id==DichVuVienThong.MEGAWAN||x.dichvuvt_id==DichVuVienThong.CO_DINH||
                                                                                x.dichvuvt_id==DichVuVienThong.GPHONE||x.dichvuvt_id==DichVuVienThong.TSL||
                                                                                x.dichvuvt_id==DichVuVienThong.MEGA_EYES||x.dichvuvt_id==DichVuVienThong.ANTOAN_BAOMAT_TT||
                                                                                x.dichvuvt_id==DichVuVienThong.TRUNGTAM_DULIEU||x.dichvuvt_id==DichVuVienThong.DICHVU_CNTT||x.dichvuvt_id==25||x.dichvuvt_id==26)
                            this.dichvu_selected=DichVuVienThong.CO_DINH
                        }
                        // else if(this.loaihd_id==LoaiHopDong.TUVAN_DV){
                        //     this.ds_dichvu=[]
                        //     this.loaihinh_tb_filter=[]
                        // }
                    }
                    if(this.dichvu_selected){
                      this.changeDichVu()
                    }
                    if(response.data.data.doituong){
                        this.ds_doituong_tb=response.data.data.doituong
                        if(this.ds_doituong_tb.length>0){
                            //TUNHAN
                            this.doituong_selected=1
                        }
                    }
                    // if(response.data.data.kieu_ld){
                    //     //cboKieuLD.DataSource = dsDanhMuc.Tables["KIEU_LD"].Select("[LOAIHD_ID] = " + loaihd_id.ToString()).CopyToDataTable();
                    //     //không rõ api trả về loaitb_id sài tạm thay LOAIHD_ID
                    //     this.ds_kieu_yc=response.data.data.kieu_ld.filter(x=>x.)

                    //     if(this.ds_kieu_yc.length>0){
                    //         this.kieu_yc_selected=this.ds_kieu_yc[0].kieuld_id
                    //     }
                    // }
                    if(response.data.data.loai_kh){
                        this.ds_loai_kh=response.data.data.loai_kh
                        if(this.ds_loai_kh.length>0){
                          //Gán mặc định "ten_loaikh_dis": "Cá nhân - Khác",
                          this.loai_kh_selected=1
                           // this.loai_kh_selected=this.ds_loai_kh[0].loaikh_id
                        }
                    }
                }else{
                    this.ds_dichvu=[]
                    this.ds_loaihinh=[]
                    //this.ds_kieu_yc=[]
                    this.ds_loai_kh=[]
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
        async lay_danhsach_kieu_ld(){
            try{
                this.loading(true)
                this.ds_kieu_yc=[]
                let response = await SurveyRequestAPI.lay_danhsach_kieu_ld(this.axios)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    //filter loại hd
                    this.ds_kieu_yc=response.data.data.filter(x=>x.LOAIHD_ID==this.loaihd_id)
                    if(this.ds_kieu_yc.length>0){
                        this.kieu_yc_selected=this.ds_kieu_yc[0].KIEULD_ID
                    }
                }else{
                    this.ds_kieu_yc=[]
                }
            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Không load được danh sách kiểu lắp đặt')
                }
            }finally{
                this.loading(false)
            }
        },
        async sp_lay_ds_donvi_ldv(loaidv_id){
            try{
                this.loading(true)
                this.ds_donvi_xyly=[]
                let response = await SurveyRequestAPI.sp_lay_ds_donvi_ldv(this.axios, loaidv_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.ds_donvi_xyly=response.data.data
                    if(this.ds_donvi_xyly.length>0){
                      this.donvi_xyly_selected=this.ds_donvi_xyly[0].donvi_id
                    }
                }else{
                    this.ds_donvi_xyly=[]
                }
            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Không load được danh sách đơn vị')
                }
            }finally{
                this.loading(false)
            }
        },
        //type MaGD
        async initFocusRow(){
          this.action_type=1
          this.input_ten_tb=this.hdtb.ten_tb!=null?this.hdtb.ten_tb:''
          this.input_diachi_tb=this.hdtb.diachi_tb!=null?this.hdtb.diachi_tb:''
          this.input_diachi_ld=this.hdtb.diachi_ld!=null?this.hdtb.diachi_ld:''
          this.input_ghichu=this.hdtb.ghichu_tb!=null?this.hdtb.ghichu_tb:''

          
          
          if(this.dichvu_selected!=this.hdtb.dichvuvt_id){
            this.dichvu_selected=this.hdtb.dichvuvt_id
            await this.changeDichVu()
          }
          this.loaihinh_selected=this.hdtb.loaitb_id
          
          if(this.hdtb.ngay_sn_tb!=undefined&&this.hdtb.ngay_sn_tb!=null){
            this.ngay_sinh=this.hdtb.ngay_sn_tb?moment(this.hdtb.ngay_sn_tb,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY'):''
          }else{
            this.ngay_sinh=''
          }

          this.donvi_xyly_selected=null
          if(this.hdtb.donvi_id!=undefined&&this.hdtb.donvi_id!=null){
            this.donvi_xyly_selected=this.hdtb.donvi_id.toString()
          }
          
          
          this.kieu_yc_selected=this.hdtb.kieuld_id
          this.input_soluong=this.hdtb.soluong!=null?this.hdtb.soluong.toString():'0'
          this.doituong_selected=this.hdtb.doituong_id

          await this.hienthi_tt_diachi_tb_ld()

          await this.khoitao_khuvuc_nhanvien()

        },
        //Type maKH
        async initInfoMaKH(item){
          this.action_type=2
          //init data
          this.thuebao_id=0
          if(this.loaihd_id!=LoaiHopDong.TIEPNHAN_DATMOI){
            this.thuebao_id=item.thuebao_id!=null?item.thuebao_id:0
            this.dichvu_selected=item.dichvuvt_id
            await this.changeDichVu()
            this.loaihinh_selected=item.loaitb_id
          }
          this.input_ten_tb=item.ten_tb!=null?item.ten_tb:''
          this.input_diachi_tb=item.diachi_tb!=null?item.diachi_tb:''
          this.input_diachi_ld=item.diachi_ld!=null?item.diachi_ld:''
          //Hiển thị đia chỉ TB, LD
          //Tham chiếu: UR2.1.007_012
          //HienThiTTDiaChi(tcdanhba.LAY_DS_DIACHI_THEO_DBTBID(thuebao_id), 3);
          await this.hienthi_diachi_theo_dbtbid(this.thuebao_id)

          if(item.ngay_sn_tb!=null&&item.ngay_sn_tb.toString()!=''){
            this.ngay_sinh=moment(item.ngay_sn_tb,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY')
          }else{
            this.ngay_sinh=''
          }
          this.doituong_selected=item.doituong_id

          
          //Đơn vị
          await this.hienthi_donvi(this.thuebao_id, this.loaidv_id)

          //khu vực, nhân viên
          await this.khoitao_khuvuc_nhanvien_v1(this.thuebao_id)


        },
        async hienthi_tt_diachi_tb_ld(){
          try{
              this.loading(true)

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
              let response = await SurveyRequestAPI.lay_ds_diachi_theo_hdtbid(this.axios, this.hdtb.hdtb_id)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data&&response.data.data.length>0) {
                  let result = response.data.data[0]
                  // Địa chỉ TB
                  this.dataDiaChiTB={
                    diachi_id:result.diachi_id!=null?result.diachi_id:0,
                    tinh_id:result.tinh_id!=null?result.tinh_id:0,
                    quan_id:result.quan_id!=null?result.quan_id:0,
                    phuong_id:result.phuong_id!=null?result.phuong_id:0,
                    pho_id:result.pho_id!=null?result.pho_id:0,
                    ap_id:result.ap_id!=null?result.ap_id:0,
                    khu_id:result.khu_id!=null?result.khu_id:0,
                    dacdiem_id:result.dacdiem_id!=null?result.dacdiem_id:0,
                    sonha:result.sonha!=null?result.sonha:'',

                    block:(result.hasOwnProperty("block")&&result.block!=null)?result.block:'',
                    tang:(result.hasOwnProperty("tang")&&result.tang!=null)?result.tang:'',
                    sophong:(result.hasOwnProperty("phong")&&result.phong!=null)?result.phong:'',
                    motathem:(result.hasOwnProperty("mota")&&result.mota!=null)?result.mota:'',

                    diachi:(this.hdtb.hasOwnProperty("diachi_tb")&&this.hdtb.diachi_tb!=null)?this.hdtb.diachi_tb:'',
                  }
                  //Địa chỉ LD
                  this.dataDiaChiLD={
                    diachi_id:result.diachild_id!=null?result.diachild_id:0,
                    tinh_id:result.tinhld_id!=null?result.tinhld_id:0,
                    quan_id:result.quanld_id!=null?result.quanld_id:0,
                    phuong_id:result.phuongld_id!=null?result.phuongld_id:0,
                    pho_id:result.phold_id!=null?result.phold_id:0,
                    ap_id:result.apld_id!=null?result.apld_id:0,
                    khu_id:result.khuld_id!=null?result.khuld_id:0,
                    dacdiem_id:result.dacdiemld_id!=null?result.dacdiemld_id:0,
                    sonha:result.sonha_ld!=null?result.sonha_ld:'',

                    block:(result.hasOwnProperty("blockld")&&result.blockLd!=null)?result.blockLd:'',
                    tang:(result.hasOwnProperty("tangld")&&result.tangLd!=null)?result.tangLd:'',
                    sophong:(result.hasOwnProperty("phongld")&&result.phongLd!=null)?result.phongLd:'',
                    motathem:(result.hasOwnProperty("motald")&&result.motaLd!=null)?result.motaLd:'',

                    diachi:(this.hdtb.hasOwnProperty("diachi_ld")&&this.hdtb.diachi_ld!=null)?this.hdtb.diachi_ld:'',
                  }
              }
          }catch(e){
              this.loading(false)
              if(e.data&&e.data.message){
                  this.$toast.error(e.data.message)
              }else if(e.response&&e.response.data&&e.response.data.message){
                  this.$toast.error(e.response.data.message)
              }else{
                  this.$toast.error('Không load được thông tin địa chỉ TB, LD')
              }
          }
        },
        async hienthi_diachi_theo_dbtbid(thuebao_id){
          try{
              this.loading(true)

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
              let response = await SurveyRequestAPI.lay_ds_diachi_theo_dbtbid(this.axios, thuebao_id)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data&&response.data.data.length>0) {
                  let result = response.data.data[0]
                  // Địa chỉ TB
                  this.dataDiaChiTB={
                    diachi_id:result.diachi_id!=null?result.diachi_id:0,
                    tinh_id:result.tinh_id!=null?result.tinh_id:0,
                    quan_id:result.quan_id!=null?result.quan_id:0,
                    phuong_id:result.phuong_id!=null?result.phuong_id:0,
                    pho_id:result.pho_id!=null?result.pho_id:0,
                    ap_id:result.ap_id!=null?result.ap_id:0,
                    khu_id:result.khu_id!=null?result.khu_id:0,
                    dacdiem_id:result.dacdiem_id!=null?result.dacdiem_id:0,
                    sonha:result.sonha!=null?result.sonha:'',

                    block:(result.hasOwnProperty("block")&&result.block!=null)?result.block:'',
                    tang:(result.hasOwnProperty("tang")&&result.tang!=null)?result.tang:'',
                    sophong:(result.hasOwnProperty("phong")&&result.phong!=null)?result.phong:'',
                    motathem:(result.hasOwnProperty("mota")&&result.mota!=null)?result.mota:'',

                    diachi:result.diachi!=null?result.diachi:'',
                  }
                  //Địa chỉ LD
                  this.dataDiaChiLD={
                    diachi_id:result.diachild_id!=null?result.diachild_id:0,
                    tinh_id:result.tinhld_id!=null?result.tinhld_id:0,
                    quan_id:result.quanld_id!=null?result.quanld_id:0,
                    phuong_id:result.phuongld_id!=null?result.phuongld_id:0,
                    pho_id:result.phold_id!=null?result.phold_id:0,
                    ap_id:result.apld_id!=null?result.apld_id:0,
                    khu_id:result.khuld_id!=null?result.khuld_id:0,
                    dacdiem_id:result.dacdiemld_id!=null?result.dacdiemld_id:0,
                    sonha:result.sonha_ld!=null?result.sonha_ld:'',

                    block:(result.hasOwnProperty("blockLd")&&result.blockLd!=null)?result.blockLd:'',
                    tang:(result.hasOwnProperty("tangLd")&&result.tangLd!=null)?result.tangLd:'',
                    sophong:(result.hasOwnProperty("phongLd")&&result.phongLd!=null)?result.phongLd:'',
                    motathem:(result.hasOwnProperty("motaLd")&&result.motaLd!=null)?result.motaLd:'',

                    diachi:result.diachi_ld!=null?result.diachi_ld:'',
                  }
              }
          }catch(e){
              this.loading(false)
              if(e.data&&e.data.message){
                  this.$toast.error(e.data.message)
              }else if(e.response&&e.response.data&&e.response.data.message){
                  this.$toast.error(e.response.data.message)
              }else{
                  this.$toast.error('Không load được thông tin địa chỉ TB, LD')
              }
          }
        },
        //từ ds
        async khoitao_khuvuc_nhanvien(){
          this.input_khuvuc=''
          this.ds_nhanvien=[]
          this.nhanvien_selected=null
          console.log('LOC_NVDIADANH_NVPX', this.LOC_NVDIADANH_NVPX)
          try{
            this.loading(true)
            this.khuvuc_selected=null
            let responseKhuVuc = await SurveyRequestAPI.lay_ds_khuvuc_theo_hd_db(this.axios, this.hdtb.hdtb_id)
            if(responseKhuVuc && responseKhuVuc.data && responseKhuVuc.data.error_code && responseKhuVuc.data.error_code == 'BSS-00000000' && responseKhuVuc.data.data&&responseKhuVuc.data.data.length>0) {
              this.khuvuc_selected=responseKhuVuc.data.data[0]
              this.input_khuvuc=this.khuvuc_selected.ma_kv
              if(this.LOC_NVDIADANH_NVPX){
                //lay_ds_nhanvien_khuvuc_lnv_v2
                //comment tạm
                let responseNhanVien = await SurveyRequestAPI.lay_ds_nhanvien_khuvuc_lnv_v2(this.axios, this.khuvuc_selected.khuvuc_id, this.loainhanvien_id, this.dataDiaChiLD.phuong_id, this.dataDiaChiLD.pho_id, this.dataDiaChiLD.ap_id, this.dataDiaChiLD.khu_id, this.dataDiaChiLD.dacdiem_id)
                if(responseNhanVien && responseNhanVien.data && responseNhanVien.data.error_code && responseNhanVien.data.error_code == 'BSS-00000000' && responseNhanVien.data.data) {
                  this.ds_nhanvien=responseNhanVien.data.data.map(x=>Object.assign(x,{
                    ten_nv:x.ma_nv+' - '+x.ten_nv
                  }))
                }else{
                  this.ds_nhanvien=[]
                }

                //hard
                // let responseNhanVien = await SurveyRequestAPI.lay_ds_nhanvien_khuvuc_lnv(this.axios, 677, 51)
                // if(responseNhanVien && responseNhanVien.data && responseNhanVien.data.error_code && responseNhanVien.data.error_code == 'BSS-00000000' && responseNhanVien.data.data) {
                //   this.ds_nhanvien=responseNhanVien.data.data
                // }else{
                //   this.ds_nhanvien=[]
                // }

              }else{
                //lay_ds_nhanvien_khuvuc_lnv
                let responseNhanVien = await SurveyRequestAPI.lay_ds_nhanvien_khuvuc_lnv(this.axios, this.khuvuc_selected.khuvuc_id, this.loainhanvien_id)
                if(responseNhanVien && responseNhanVien.data && responseNhanVien.data.error_code && responseNhanVien.data.error_code == 'BSS-00000000' && responseNhanVien.data.data) {
                  this.ds_nhanvien=responseNhanVien.data.data.map(x=>Object.assign(x,{
                    ten_nv:x.ma_nv+' - '+x.ten_nv
                  }))
                }else{
                  this.ds_nhanvien=[]
                }
              }
            }else{
              this.input_khuvuc=''
              this.ds_nhanvien=[]
            }
            if(this.input_khuvuc==''&&this.ds_nhanvien.length==0){
              await this.changeDaiVT()
            }
            

          }catch(e){
            if(e.data&&e.data.message){
              this.$toast.error(e.data.message)
            }else if(e.response&&e.response.data&&e.response.data.message){
              this.$toast.error(e.response.data.message)
            }else{
              this.$toast.error('Không load được khu vực, nhân viên')
            }
          }finally{
            this.loading(false)
          }

        },
        //từ enter makh
        async khoitao_khuvuc_nhanvien_v1(thuebao_id){
          this.input_khuvuc=''
          this.ds_nhanvien=[]
          try{
            this.loading(true)
            this.khuvuc_selected=null
            this.nhanvien_selected=null
            let responseKhuVuc = await SurveyRequestAPI.lay_ct_khuvuc_thuebao(this.axios, thuebao_id)
            if(responseKhuVuc && responseKhuVuc.data && responseKhuVuc.data.error_code && responseKhuVuc.data.error_code == 'BSS-00000000' && responseKhuVuc.data.data&&responseKhuVuc.data.data.length>0) {
              this.khuvuc_selected=responseKhuVuc.data.data[0]
              this.input_khuvuc=this.khuvuc_selected.ma_kv
              if(this.LOC_NVDIADANH_NVPX){
                //lay_ds_nhanvien_khuvuc_lnv_v2
                let responseNhanVien = await SurveyRequestAPI.lay_ds_nhanvien_khuvuc_lnv_v2(this.axios, this.khuvuc_selected.khuvuc_id, this.loainhanvien_id, this.dataDiaChiLD.phuong_id, this.dataDiaChiLD.pho_id, this.dataDiaChiLD.ap_id, this.dataDiaChiLD.khu_id, this.dataDiaChiLD.dacdiem_id)
                if(responseNhanVien && responseNhanVien.data && responseNhanVien.data.error_code && responseNhanVien.data.error_code == 'BSS-00000000' && responseNhanVien.data.data) {
                  this.ds_nhanvien=responseNhanVien.data.data.map(x=>Object.assign(x,{
                    ten_nv:x.ma_nv+' - '+x.ten_nv
                  }))
                }else{
                  this.ds_nhanvien=[]
                }
              }else{
                //lay_ds_nhanvien_khuvuc_lnv
                let responseNhanVien = await SurveyRequestAPI.lay_ds_nhanvien_khuvuc_lnv(this.axios, this.khuvuc_selected.khuvuc_id, this.loainhanvien_id)
                if(responseNhanVien && responseNhanVien.data && responseNhanVien.data.error_code && responseNhanVien.data.error_code == 'BSS-00000000' && responseNhanVien.data.data) {
                  this.ds_nhanvien=responseNhanVien.data.data.map(x=>Object.assign(x,{
                    ten_nv:x.ma_nv+' - '+x.ten_nv
                  }))
                }else{
                  this.ds_nhanvien=[]
                }
              }
            }else{
              this.input_khuvuc=''
              this.ds_nhanvien=[]
            }
            if(this.input_khuvuc==''&&this.ds_nhanvien.length==0){
              await this.changeDaiVT()
            }
          }catch(e){
            console.log(e)
            if(e.data&&e.data.message){
              this.$toast.error(e.data.message)
            }else if(e.response&&e.response.data&&e.response.data.message){
              this.$toast.error(e.response.data.message)
            }else{
              this.$toast.error('Không load được khu vực, nhân viên')
            }
          }finally{
            this.loading(false)
          }
        },
        async lay_ds_nhanvien_kd(loainv_id, donvi_id){
            try{
                this.loading(true)
                let response = await SurveyRequestAPI.lay_ds_nhanvien_kd(this.axios, loainv_id, donvi_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async hienthi_donvi(thuebao_id, loaidv_id){
            try{
              this.loading(true)
              let response = await SurveyRequestAPI.sp_lay_ds_donvi_thicong(this.axios, thuebao_id, loaidv_id)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data &&response.data.data.length>0) {
                  let result = response.data.data[0]
                  this.donvi_xyly_selected=result.donvi_id.toString()
              }else{
                  this.donvi_xyly_selected=null
              }
            }catch(e){
              this.donvi_xyly_selected=null
              if(e.data&&e.data.message){
                  this.$toast.error(e.data.message)
              }else if(e.response&&e.response.data&&e.response.data.message){
                  this.$toast.error(e.response.data.message)
              }else{
                  this.$toast.error('Không load được đơn vị')
              }
            }finally{
              this.loading(false)
            }
        },
        async clearDuLieu(){
          this.input_ten_tb=''
          this.input_diachi_tb=''
          this.input_diachi_ld=''
          this.input_khuvuc=''
          this.input_soluong='1'
          this.input_ghichu=''
          this.ngay_sinh=''

          this.khuvuc_selected=null

          //1:DoiTuong.TU_NHAN
          this.doituong_selected=1
          this.kieu_yc_selected=this.ds_kieu_yc.length>0?this.ds_kieu_yc[0].KIEULD_ID:0
          this.ds_nhanvien=[]
          this.nhanvien_selected=null
          this.thuebao_id=0

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
            
            await this.lay_danhmuc_lapmoi_tt_chung_v7('DOITUONG|DICHVU_VT|LOAIHINH_TB|LOAI_KH')
            await this.lay_danhsach_kieu_ld()
            //LOAI_DV.TRAM_VT=5
            //var loaidv_id=5
            //mặc định 5 
            // if(this.loaihd_id==LoaiHopDong.TIEPNHANHD){
            //   
            // }
            if (this.loaihd_id == LoaiHopDong.TUVAN_DV || this.loaihd_id == LoaiHopDong.TIEPNHANHD){
              //LoaiDonViXL = LOAI_DV.DONVIQL_TT;
              this.loaidv_id=2
            }
                   
            //Tư vấn chuyển 
            // }else if(this.loaihd_id==LoaiHopDong.TUVAN_DV){
            //   //LOAI_DV.DONVIQL_TT=2
            //   loaidv_id=2
            // }
            await this.sp_lay_ds_donvi_ldv(this.loaidv_id)

        },
        
    }
}
</script>
<style>
.btn-second {
    background-color: #fff;
    border-color: #0176FF;
    color: #0176FF;
    font-weight: 600;
}
.btn-second:hover {
    background-color: #0176FF;
    border-color: #0176FF;
    color: #fff;
    font-weight: 600;
}
.btn-outline-secondary {
    background-color: #fff;
    color: #6c757d;
    border-color: #6c757d;
}
.btn-outline-secondary:hover {
    background-color: #6c757d;
    color: #fff;
    border-color: #6c757d;
}
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
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
</style>