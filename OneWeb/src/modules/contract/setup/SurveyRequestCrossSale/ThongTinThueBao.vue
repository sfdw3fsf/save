<template>
    <div class="box-form">
        <div class="legend-title">Thông tin thuê bao</div>
        <div class="row">
            <div class="col-sm-6 col-12">
                <div class="info-row">
                    <div class="key w90">Dịch vụ VT</div>
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
                    <div class="key w90">Loại hình TB</div>
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
            <div class="col-sm-12 col-12">
                <div class="info-row">
                    <div class="key w90">Kiểu yêu cầu</div>
                    <div class="value">
                        <div class="select-custom" ref="ds_kieu_ld">
                            <select2 :settings="{ dropdownParent: $refs['ds_kieu_ld'] }" ref="cboKieuLD"
                                v-model="kieu_ld_selected" class="select2"
                                :options="ds_kieu_ld.map(e=> ({id: e.KIEULD_ID, text: e.TEN_KIEULD}))"
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
                    <div class="key w90">Tên TB (*)</div>
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
            <div class="key w90">Địa chỉ TB (*)</div>
            <div class="value">
                <div class="input-more-button">
                    <button class="btn" @click="clickChonDiaChi(1)" :disabled="disabledBtnDiaChiTb" title="Chọn địa chỉ thuê bao">
                        <span class="-ap icon-more_horiz"></span>
                    </button>
                    <input type="text" ref="inputDiaChiTB" @keypress="evt=>evt.preventDefault()" :value="input_diachi_tb" @change="e=>input_diachi_tb=e.target.value" class="form-control bold" />
                </div>
            </div>
        </div>
        <div class="info-row">
            <div class="key w90">Địa chỉ LĐ (*)</div>
            <div class="value">
                <div class="input-more-button">
                    <button class="btn" @click="clickChonDiaChi(2)" :disabled="disabledBtnDiaChiLd" title="Chọn địa chỉ lắp đặt">
                        <span class="-ap icon-more_horiz"></span>
                    </button>
                    <input type="text" ref="inputDiaChiLD" @keypress="evt=>evt.preventDefault()" :value="input_diachi_ld" @change="e=>input_diachi_ld=e.target.value" class="form-control bold" />
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-6 col-12">
                <div class="info-row">
                    <div class="key w90">Đơn vị TC</div>
                    <div class="value">
                        <div class="select-custom" ref="ds_donvi_tc">
                            <select2 :settings="{ dropdownParent: $refs['ds_donvi_tc'] }" ref="cboDonViTC"
                                v-model="donvi_tc_selected" class="select2"
                                :options="ds_donvi_tc.map(e=> ({id: e.donvi_id, text: e.ten_dv}))">
                            </select2>
                        </div>  
                    </div>
                   
                </div>
            </div>
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
        </div>
        <div class="row">
            <div class="col-sm-6 col-12">
                <div class="info-row">
                    <div class="key w90" title="Đơn vị sẽ đi khảo sát ở tỉnh thi công">ĐV tỉnh TC (*)</div>
                    <div class="value">
                        <input type="text" readonly :value="input_dvt_tc" @change="e=>input_dvt_tc=e.target.value" class="form-control bold" />
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
                <div class="select-custom">
                    <select name="" id="" class="form-control">
                        <option value=""></option>
                    </select>
                </div>
            </div>
        </div>
        <div class="info-row">
            <div class="key w90">Chi tiết YC</div>
            <div class="value">
                <textarea type="text" :value="input_chi_tiet_yc" @change="e=>input_chi_tiet_yc=e.target.value" placeholder="Tốc độ, giá tiền, cam kết quốc tế, yêu cầu khác,..." style="height: 35px;resize: none;" class="form-control "></textarea>
            </div>
        </div>
        <!-- Modal -->
        <DiaChiBCModal ref="diaChiModal" :tinh_bc="tinh_tc" :data="dataDiaChi" @accept="onAcceptDiaChi"/>
    </div>
</template>
<script>
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import {LoaiHopDong, DoiTuong, DichVuVienThong, LOAI_DV} from './ThamSo'
import SurveyRequestCrossSaleAPI from './SurveyRequestCrossSaleAPI'
import DiaChiBCModal from './DiaChiBC/DiaChiBCModal.vue'
export default {
    name:'ThongTinThueBao',
    props:{
        tinh_tc:{
            type:[Number, String],
            default: 0
        },
    },
    components:{
        KDatePicker,
        DiaChiBCModal
    },
    data(){
        return {
            ngay_sinh:'',
            loaihd_id:33,
            input_ten_tb:'',
            input_diachi_tb:'',
            input_diachi_ld:'',
            input_soluong:'',
            input_dvt_tc:'',
            input_chi_tiet_yc:'',
            disabledBtnDiaChiTb:false,
            disabledBtnDiaChiLd:false,
            ds_loaihinh:[],
            loaihinh_tb_filter:[],
            loaihinh_selected:null,
            ds_dichvu:[],
            dichvu_selected:null,
            ds_doituong_tb:[],
            doituong_selected:null,
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
              diachi:''
            },
            tinhtc_donvi_id:-1,
            ds_donvi_tc:[],
            donvi_tc_selected:null,
            ds_kieu_ld:[],
            kieu_ld_selected:null



        }   
    },
    methods:{
        isNumber(evt) {
            evt = (evt) ? evt : window.event;
            var charCode = (evt.which) ? evt.which : evt.keyCode;
            if ((charCode > 31 && (charCode < 48 || charCode > 57))) {
                evt.preventDefault()
            } else {
                return true
            }
        },
        focusTenTB(){
            this.$refs.inputTenTB.focus()
        },
        focusDiaChiLD(){
            this.$refs.inputDiaChiLD.focus()
        },
        focusDiaChiTB(){
            this.$refs.inputDiaChiTB.focus()
        },
        blurInputSoLuong(){
          if(this.input_soluong.trim()==''||this.input_soluong.trim()=='0'){
            this.input_soluong='1'
          }else{
            this.input_soluong=Number(this.input_soluong.trim()).toString()
          }
        },
        clickChonDiaChi(value){
          if(!this.tinh_tc){
            this.$toast.error('Vui lòng chọn tỉnh thi công!')
            return
          }
          
          //1: Địa chỉ TB, 2: Địa chỉ lắp đặt
          this.kieu_chon_diachi=value
          //khởi tạo dữ liệu, địa chỉ khách hàng
          if(this.kieu_chon_diachi==1){
            Object.assign(this.dataDiaChi, this.dataDiaChiTB)
            //this.dataDiaChi=this.dataDiaChiTB
          }else if(this.kieu_chon_diachi==2){
            Object.assign(this.dataDiaChi, this.dataDiaChiLD)
          }
          this.$refs.diaChiModal.showModal()
          
        },
        onAcceptDiaChi(data){
          if(this.kieu_chon_diachi==1){
            this.input_diachi_tb=data.diachi
            Object.assign(this.dataDiaChiTB, data)
            //Chọn địa chỉ TB apply luôn qua lắp đặt
            this.input_diachi_ld=data.diachi
            Object.assign(this.dataDiaChiLD, data)
            //Handle chọn đơn vị tổ thi công
            this.handleDonViThiCong()

          }else if(this.kieu_chon_diachi==2){
            this.input_diachi_ld=data.diachi
            Object.assign(this.dataDiaChiLD, data)
            //Handle chọn đơn vị tổ thi công
            this.handleDonViThiCong()

          }
        },
        async handleDonViThiCong(){
            this.$emit('handleDonViThiCong')
        },
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
            this.$emit('changeDichVu',this.dichvu_selected)
        },
        async HienThiTT_DanhBa_KHDN(item){
            this.input_ten_tb=item.full_name?item.full_name.toString():''
        },
        async HienThiTT_DanhBa(item){
            this.input_ten_tb=item.ten_tb?item.ten_tb.toString():''
            if(item.ngay_sn_tb!=null&&item.ngay_sn_tb.toString()!=''){
                this.ngay_sinh=moment(item.ngay_sn_tb,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY')
            }else{
                this.ngay_sinh=''
            }
            this.doituong_selected=item.doituong_id
            var thuebao_id=0
            if(this.loaihd_id!=LoaiHopDong.KHAOSATDATMOI){
                thuebao_id=item.thuebao_id?item.thuebao_id:0
            }
            let ds1=await this.sp_lay_ds_donvi_thicong(thuebao_id, 1, LOAI_DV.TRAM_VT, 2)
            if(ds1.length>0){
                this.donvi_tc_selected=ds1[0].donvi_id
            }
            if(this.loaihd_id!=LoaiHopDong.KHAOSATDATMOI&&item.loaitb_id){
                const loaihinh_tb=this.ds_loaihinh.find(x=>x.loaitb_id==item.loaitb_id)
                if(loaihinh_tb){
                    const dichvu=this.ds_dichvu.find(x=>x.dichvuvt_id==loaihinh_tb.dichvuvt_id)
                    if(dichvu){
                        this.dichvu_selected=dichvu.dichvuvt_id
                        await this.changeDichVu()
                        this.loaihinh_selected=item.loaitb_id
                    }
                }
            }
        },
        async HienThiTTHopDongTB(item){
            this.input_ten_tb=item.ten_tb?item.ten_tb.toString():''
            this.input_diachi_tb=item.diachi_tb?item.diachi_tb.toString():''
            this.input_diachi_ld=item.diachi_ld?item.diachi_ld.toString():''
            this.input_chi_tiet_yc=item.ghichu?item.ghichu.toString():''
            if(item.ngay_sn){
                this.ngay_sinh=moment(item.ngay_sn,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY')
            }else{
                this.ngay_sinh=''
            }
            this.donvi_tc_selected=item.donvi_id?item.donvi_id:0
            this.dichvu_selected=item.dichvuvt_id?item.dichvuvt_id:0
            await this.changeDichVu()
            this.loaihinh_selected=item.loaitb_id?item.loaitb_id:0
            this.kieu_ld_selected=item.kieuld_id?item.kieuld_id:0
            this.input_soluong=item.soluong?item.soluong.toString():''
            this.doituong_selected=item.doituong_id?item.doituong_id:0
        },
        async sp_lay_ds_donvi_thicong(thuebao_id, kieu, loaidv_id, kieudv_id){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.sp_lay_ds_donvi_thicong(this.axios, thuebao_id, kieu, loaidv_id, kieudv_id)
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
        async sp_lay_ds_donvi_ldv(loaidv_id){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.sp_lay_ds_donvi_ldv(this.axios, loaidv_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.map(x=>Object.assign(x,{donvi_id: Number(x.donvi_id)})).sort((a,b)=>a.tinh_id-b.tinh_id)
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async sp_lay_ds_doituong_theo_dieukien(){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.sp_lay_ds_doituong_theo_dieukien(this.axios)
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
        },
        async lay_ds_kieu_ld(){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.lay_ds_kieu_ld(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.filter(x=>x.LOAIHD_ID==this.loaihd_id)
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async Clear(){
            this.tinhtc_donvi_id=-1
            this.input_ten_tb=''
            this.ngay_sinh=''
            this.input_diachi_tb=''
            this.input_diachi_ld=''

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
              diachi:''
            }
            this.doituong_selected=DoiTuong.TU_NHAN
            this.input_chi_tiet_yc=''
            this.input_soluong='1'
            this.input_dvt_tc=''
        },
        async initDuLieu(){
            //Handle form trùng
            let endPath=this.$router.currentRoute.path.split('/').pop()
            if(endPath=='SurveyRequestCrossSale'){
                //Tiếp nhận yêu cầu khảo sát lắp đặt bán chéo
                this.loaihd_id=LoaiHopDong.KHAOSATDATMOI
            }
            if(this.loaihd_id==LoaiHopDong.KHAOSATDC){
                this.disabledBtnDiaChiTb=true
                this.disabledBtnDiaChiLd=true
            }

            //let result = await this.lay_danhmuc_lapmoi_tt_chung_v7('DOITUONG|DICHVU_VT|LOAIHINH_TB')
            let result = await this.lay_danhmuc_lapmoi_tt_chung_v7('DICHVU_VT|LOAIHINH_TB')
            if(result&&result.loaihinh_tb){
                this.ds_loaihinh=result.loaihinh_tb.sort((a, b)=>a.loaitb_id-b.loaitb_id)
            }
            if(result&&result.dichvu_vt){
                if(this.loaihd_id==LoaiHopDong.KHAOSATDATMOI||this.loaihd_id==LoaiHopDong.KHAOSATDC||this.loaihd_id==LoaiHopDong.KHAOSATCDLH){
                    this.ds_dichvu=result.dichvu_vt.filter(x=>x.dichvuvt_id==DichVuVienThong.ADSL||x.dichvuvt_id==DichVuVienThong.IMS||
                                                                        x.dichvuvt_id==DichVuVienThong.MEGAWAN||x.dichvuvt_id==DichVuVienThong.METRONET||x.dichvuvt_id==DichVuVienThong.CO_DINH||
                                                                        x.dichvuvt_id==DichVuVienThong.GPHONE||x.dichvuvt_id==DichVuVienThong.TSL||x.dichvuvt_id==DichVuVienThong.MEGA_EYES||x.dichvuvt_id==25||x.dichvuvt_id==26)
                    this.dichvu_selected=1
                }else if(this.loaihd_id==LoaiHopDong.TIEPNHANHD){
                    this.ds_dichvu=result.dichvu_vt.filter(x=>x.dichvuvt_id==DichVuVienThong.ADSL||x.dichvuvt_id==DichVuVienThong.IMS||
                                                                        x.dichvuvt_id==DichVuVienThong.MEGAWAN||x.dichvuvt_id==DichVuVienThong.METRONET||x.dichvuvt_id==DichVuVienThong.CO_DINH||
                                                                        x.dichvuvt_id==DichVuVienThong.GPHONE||x.dichvuvt_id==DichVuVienThong.TSL||
                                                                        x.dichvuvt_id==DichVuVienThong.MEGA_EYES||x.dichvuvt_id==DichVuVienThong.ANTOAN_BAOMAT_TT||
                                                                        x.dichvuvt_id==DichVuVienThong.TRUNGTAM_DULIEU||x.dichvuvt_id==DichVuVienThong.DICHVU_CNTT||x.dichvuvt_id==25||x.dichvuvt_id==26)
                    this.dichvu_selected=1
                }
                this.loaihinh_tb_filter=this.ds_loaihinh.filter(x=>x.dichvuvt_id==this.dichvu_selected)
                if(this.loaihinh_tb_filter.length>0){
                    this.loaihinh_selected=this.loaihinh_tb_filter[0].loaitb_id
                }
            }
            this.ds_doituong_tb=await this.sp_lay_ds_doituong_theo_dieukien()
            if(this.ds_doituong_tb.length>0){
                //TUNHAN
                this.doituong_selected=DoiTuong.TU_NHAN
            }
            this.ds_kieu_ld=await this.lay_ds_kieu_ld()
            if(this.ds_kieu_ld.length>0){
                this.kieu_ld_selected=this.ds_kieu_ld[0].KIEULD_ID
            }
            // if(result&&result.doituong){
            //     this.ds_doituong_tb=result.doituong
            //     if(this.ds_doituong_tb.length>0){
            //         //TUNHAN
            //         this.doituong_selected=DoiTuong.TU_NHAN
            //     }
            // }
            
            this.ds_donvi_tc=await this.sp_lay_ds_donvi_ldv(this.loaihd_id!=LoaiHopDong.TIEPNHANHD?5:86)
            if(this.ds_donvi_tc.length>0){
                this.donvi_tc_selected=this.ds_donvi_tc[0].donvi_id
            }

        }
    }
}
</script>