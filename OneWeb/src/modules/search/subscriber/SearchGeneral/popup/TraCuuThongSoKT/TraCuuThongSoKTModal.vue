<template>
  <b-modal
    ref="popupTraCuuThongSoKT"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Thông tin chi tiết thuê bao hiện tại trên hệ thống ĐHSX
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>      

      <div class="popup-body">
        <div class="box-form">                    
          <CoDinh ref="coDinh" v-if="dichvu_selected==1"/>
          <DiDong ref="diDong" v-if="dichvu_selected==2"/>
          <Megaeyes ref="megaEyes" v-if="dichvu_selected==12||dichvu_selected==21||dichvu_selected==22||dichvu_selected==23||dichvu_selected==24"/>
          <BangRongCoDinh ref="bangRongCoDinh" v-else-if="dichvu_selected==4"/>
          <MegawanMetronet ref="megawanMetronet" v-else-if="dichvu_selected==7||dichvu_selected==8||dichvu_selected==26"/>          
          <KenhThueRieng ref="kenhThueRieng" v-else-if="dichvu_selected==9||dichvu_selected==25"/>          
          <Gphone ref="gphone" v-else-if="dichvu_selected==10"/>
          <IMS ref="ims" v-else-if="dichvu_selected==11"/>
          <DichVuCNTT ref="dichvuCNTT" v-else-if="dichvu_selected==13||dichvu_selected==14||dichvu_selected==15||dichvu_selected==16"/>

        </div>
        <div class="box-form" v-show="dichvu_selected==1||dichvu_selected==10||dichvu_selected==4||dichvu_selected==7||dichvu_selected==8||dichvu_selected==9||dichvu_selected==11||dichvu_selected==25||dichvu_selected==26">
            <div class="legend-title">
                <span class="icon fa fa-angle-up"></span> Danh sách thuê bao đơn vị
            </div>
            <div class="table-content">
                <table class="table-result table-gachle">
                    <DataGrid
                        v-bind:columns="ds_thuebaodonvi.header"                    
                        v-bind:dataSource="ds_thuebaodonvi.list"
                        :enable-paging-server="false"                                        
                        :allowPaging="true"
                        :showFilter="true"
                        :showColumnCheckbox="false"
                        ref="nvdaGan"
                        >
                    </DataGrid> 
                </table>
            </div>
            
        </div>
        <div class="box-form" v-show="dichvu_selected==1||dichvu_selected==4||dichvu_selected==7||dichvu_selected==8||dichvu_selected==11||dichvu_selected==9||dichvu_selected==25||dichvu_selected==26">
            <div class="legend-title">
                <span class="icon fa fa-angle-up"></span> Danh sách Nhân viên khu vực theo thuê bao
            </div>
            <div class="table-content">
                <table class="table-result table-gachle">
                    <DataGrid
                        v-bind:columns="ds_nvkvtheothuebao.header"                    
                        v-bind:dataSource="ds_nvkvtheothuebao.list"
                        :enable-paging-server="false"                                        
                        :allowPaging="true"
                        :showFilter="true"
                        :showColumnCheckbox="false"
                        ref="nvdaGan"
                        >
                    </DataGrid> 
                </table>
            </div>
            
        </div>
        <div class="box-form" v-show="dichvu_selected==4||dichvu_selected==10||dichvu_selected==11">
            <div class="legend-title">
                <span class="icon fa fa-angle-up"></span> Biến động gói đa DV
            </div>
            <div class="table-content">
                <table class="table-result table-gachle">
                    <DataGrid
                        v-bind:columns="ds_biendonggoiddv.header"                    
                        v-bind:dataSource="ds_biendonggoiddv.list"
                        :enable-paging-server="false"                                        
                        :allowPaging="true"
                        :showFilter="true"
                        :showColumnCheckbox="false"
                        ref="nvdaGan"
                        >
                    </DataGrid> 
                </table>
            </div>
        
        </div>
        <div class="box-form" v-show="dichvu_selected==1||dichvu_selected==4||dichvu_selected==7||dichvu_selected==8||dichvu_selected==9||dichvu_selected==11||dichvu_selected==26||dichvu_selected==25">
            <div class="legend-title">
                <span class="icon fa fa-angle-up"></span> Thuê bao cùng đối cáp
            </div>
            <div class="table-content">
                <table class="table-result table-gachle">
                    <DataGrid
                        v-bind:columns="ds_thuebaocungdoicap.header"                    
                        v-bind:dataSource="ds_thuebaocungdoicap.list"
                        :enable-paging-server="false"                                        
                        :allowPaging="true"
                        :showFilter="true"
                        :showColumnCheckbox="false"
                        ref="nvdaGan"
                        >
                    </DataGrid> 
                </table>
            </div>
        
        </div>
        <div class="box-form" v-show="dichvu_selected==1||dichvu_selected==4||dichvu_selected==7||dichvu_selected==8||dichvu_selected==9||dichvu_selected==11||dichvu_selected==25||dichvu_selected==26">
            <div class="legend-title">
                <span class="icon fa fa-angle-up"></span> Lịch sử thay đổi thông số kỹ thuật
            </div>
            <div class="table-content">
                <table class="table-result table-gachle">
                    <DataGrid
                        v-bind:columns="ds_lichsu_tskt.header"                    
                        v-bind:dataSource="ds_lichsu_tskt.list"
                        :enable-paging-server="false"                                        
                        :allowPaging="true"
                        :showFilter="true"
                        :showColumnCheckbox="false"
                        ref="lichsu_tskt"
                        >
                    </DataGrid> 
                </table>
            </div>
        
        </div>
      </div>
      

    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import API from './API'
import CoDinh from './CoDinh.vue'
import BangRongCoDinh from './BangRongCoDinh.vue'
import Gphone from './Gphone.vue'
import IMS from './IMS.vue'
import KenhThueRieng from './KenhThueRieng.vue'
import MegawanMetronet from './MegawanMetronet.vue'
import DichVuCNTT from './DichVuCNTT.vue'
import DiDong from './DiDong.vue'
import Megaeyes from './Megaeyes.vue'
import {DichVuVienThong} from '../../ThamSo'

export default {
    name:'TraCuuThongSoKTModal',
    props:{
        dichvuvt_id:{
            type:Number,
            default:1
            //default:9
        },
        thuebao_id:{
            type:Number,
            default:0
            //default:1196535
        },
        ma_tb:{
            type:String,
            default:''
        },
        loaitb_id:{
            type:Number,
            default:58            
        }
    },
    components:{
        ActionTop,
        BangRongCoDinh,
        CoDinh,
        Gphone,
        IMS,
        KenhThueRieng,
        MegawanMetronet,
        DichVuCNTT, 
        DiDong,  
        Megaeyes,     
    },
    data(){
        return {
            success_code: "BSS-00000000",
            actions:[
                {
                    id:'vattu',
                    name:'Vật tư',
                    active: true,
                    icon_class:'one-vt',
                }
            ],
            //fix cố định số dịch vụ sử dụng
            ds_dichvu:[
              {
                dichvuvt_id: 1,
                ma_dvvt: "CD",
                ten_dvvt: "Cố định"
              },
              {
                dichvuvt_id: 4,
                ma_dvvt: "BRCD",
                ten_dvvt: "Băng rộng cố định"
              },
              {
                dichvuvt_id: 7,
                ma_dvvt: "MTN",
                ten_dvvt: "Metronet"
              },
              {
                dichvuvt_id: 8,
                ma_dvvt: "WAN",
                ten_dvvt: "MegaWan"
              },
              {
                dichvuvt_id: 9,
                ma_dvvt: "TSL",
                ten_dvvt: "Kênh thuê riêng (điểm-điểm)"
              },
              {
                dichvuvt_id: 10,
                ma_dvvt: "GPHONE",
                ten_dvvt: "Gphone"
              },
              {
                dichvuvt_id: 11,
                ma_dvvt: "IMS",
                ten_dvvt: "IMS"
              }
            ],
            dichvu_selected:1,
            input_ma_tb:'',
            input_so_ao:'',
            loai:0,
            //thông số kỹ thuật từng dịch vụ từng thông tin khác nhau
            ds_tocdokenh:[],
            ds_loaithietbi:[],
            ds_thuebaodonvi: {
            list: [],
            header: [ {fieldName: 'donvi_id', headerText: 'ID đơn vị', allowFiltering: true, width: 100}, 
                        {fieldName: 'ten_loaidv', headerText: 'Loại đơn vị', allowFiltering: true, width: 150},
                        {fieldName: 'ten_dv', headerText: 'Tên đơn vị', allowFiltering: true , allowHtml : true, width: 150},
                        {fieldName: 'ma_dv', headerText: 'Mã ĐV', allowFiltering: true , allowHtml : true, width: 100},                                
                        {fieldName: 'kieu_dv', headerText: 'Kiểu ĐV', allowFiltering: true , allowHtml : true, width: 150},                                     
                        ],
            value: {},
            isEnabled: true,
            checked: []
          },
          ds_nvkvtheothuebao: {
            list: [],
            header: [ {fieldName: 'ma_kv', headerText: 'Mã khu vực', allowFiltering: true, width: 130}, 
                        {fieldName: 'ten_kv', headerText: 'Tên khu vực', allowFiltering: true, width: 130},
                        {fieldName: 'ma_nv', headerText: 'Mã NV', allowFiltering: true , allowHtml : true, width: 100},
                        {fieldName: 'ma_nd', headerText: 'Mã ND', allowFiltering: true , allowHtml : true, width: 100},                                
                        {fieldName: 'ten_nv', headerText: 'Tên NV', allowFiltering: true , allowHtml : true, width: 150}, 
                        {fieldName: 'loai_nv', headerText: 'Chức vụ', allowFiltering: true , allowHtml : true, width: 200},                                     
                        {fieldName: 'ten_dv', headerText: 'Đơn vị', allowFiltering: true , allowHtml : true, width: 150},                                     
                        {fieldName: 'so_dt', headerText: 'Số ĐT', allowFiltering: true , allowHtml : true, width: 100},                                     
                        {fieldName: 'daucuoi', headerText: 'Thiết bị', allowFiltering: true , allowHtml : true, width: 100},                                     
                        {fieldName: 'nhiemvu', headerText: 'Nhiệm vụ', allowFiltering: true , allowHtml : true, width: 100},                                     
                                                           
                        ],
            value: {},
            isEnabled: true,
            checked: []
          },
          ds_biendonggoiddv: {
            list: [],
            header: [ {fieldName: 'ten_goi', headerText: 'Tên gói', allowFiltering: true, width: 150}, 
                        {fieldName: 'tien', headerText: 'Tiền', allowFiltering: true, width: 100},
                        {fieldName: 'vat', headerText: 'VAT', allowFiltering: true , allowHtml : true, width: 100},
                        {fieldName: 'mota', headerText: 'Mô tả', allowFiltering: true , allowHtml : true, width: 150},                                
                        {fieldName: 'ngay_dk', headerText: 'Ngày ĐK', allowFiltering: true , allowHtml : true, width: 100}, 
                        {fieldName: 'ngay_kt', headerText: 'Ngày KT', allowFiltering: true , allowHtml : true, width: 100},                                     
                        {fieldName: 'thang_bd', headerText: 'Tháng BĐ', allowFiltering: true , allowHtml : true, width: 100},                                     
                        {fieldName: 'thang_kt', headerText: 'Tháng KT', allowFiltering: true , allowHtml : true, width: 100},                                     
                        ],
            value: {},
            isEnabled: true,
            checked: []
          },
          ds_thuebaocungdoicap: {
            list: [],
            header: [ {fieldName: 'ma_tb', headerText: 'Mã TB', allowFiltering: true, width: 100}, 
                        {fieldName: 'dichvu', headerText: 'Dịch vụ', allowFiltering: true, width: 150},
                        {fieldName: 'loaihinh', headerText: 'Loại hình', allowFiltering: true , allowHtml : true, width: 100},
                        {fieldName: 'matb_tn', headerText: 'Mã TN', allowFiltering: true , allowHtml : true, width: 100},                                
                        {fieldName: 'dslam_visa', headerText: 'Dslam Visa', allowFiltering: true , allowHtml : true, width: 100}, 
                        {fieldName: 'madoicap', headerText: 'Mã đối cáp', allowFiltering: true , allowHtml : true, width: 100},                                     
                        {fieldName: 'slot', headerText: 'Slot', allowFiltering: true , allowHtml : true, width: 50},                                     
                        {fieldName: 'port', headerText: 'Port', allowFiltering: true , allowHtml : true, width: 50},    
                        {fieldName: 'rack', headerText: 'Rack', allowFiltering: true , allowHtml : true, width: 50},                                     
                        {fieldName: 'shelf', headerText: 'Shelf', allowFiltering: true , allowHtml : true, width: 50},                                     
                        {fieldName: 'dslam_tinh', headerText: 'DSLam. Tinh', allowFiltering: true , allowHtml : true, width: 150},                                     
                        {fieldName: 'vpi', headerText: 'Vpi/Svlan', allowFiltering: true , allowHtml : true, width: 150},                                     
                        {fieldName: 'vci', headerText: 'Vci/Cvlan', allowFiltering: true , allowHtml : true, width: 150},                                     
                        {fieldName: 'port_mdf', headerText: 'Port mdf', allowFiltering: true , allowHtml : true, width: 150},                                     
                        {fieldName: 'portolt', headerText: 'Port Olt', allowFiltering: true , allowHtml : true, width: 150},                                     
                        {fieldName: 'tencard', headerText: 'Tên card', allowFiltering: true , allowHtml : true, width: 150},                                     
                        ],
            value: {},
            isEnabled: true,
            checked: []
          },
          ds_lichsu_tskt: {
            list: [],
            header: [ {fieldName: 'nv', headerText: 'Nhân viên', allowFiltering: true, width: 150}, 
                        {fieldName: 'dvi', headerText: 'Đơn vị', allowFiltering: true, width: 150},
                        {fieldName: 'ngay_dk', headerText: 'Ngày đăng ký', allowFiltering: true , allowHtml : true, width: 150},
                        {fieldName: 'ngay_ht', headerText: 'Ngày hoàn thiện', allowFiltering: true , allowHtml : true, width: 150},                                
                        {fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true , allowHtml : true, width: 150},                         
                        {fieldName: 'slot_cu', headerText: 'Slot cũ', allowFiltering: true , allowHtml : true, width: 100},                                     
                        {fieldName: 'slot', headerText: 'Slot mới', allowFiltering: true , allowHtml : true, width: 100},                                     
                        {fieldName: 'port_cu', headerText: 'Port cũ', allowFiltering: true , allowHtml : true, width: 100},    
                        {fieldName: 'port', headerText: 'Port mới', allowFiltering: true , allowHtml : true, width: 100},    
                        {fieldName: 'vci_cu', headerText: 'Vci cũ', allowFiltering: true , allowHtml : true, width: 100},                                     
                        {fieldName: 'vci', headerText: 'Vci mới', allowFiltering: true , allowHtml : true, width: 100},                                    
                        {fieldName: 'vpi_cu', headerText: 'Vpi cũ', allowFiltering: true , allowHtml : true, width: 100},                                     
                        {fieldName: 'vpi', headerText: 'Vpi mới', allowFiltering: true , allowHtml : true, width: 100},  
                        {fieldName: 'tenbras_cu', headerText: 'Bras cũ', allowFiltering: true , allowHtml : true, width: 150},                                     
                        {fieldName: 'tenbras', headerText: 'Bras mới', allowFiltering: true , allowHtml : true, width: 150}, 
                        {fieldName: 'tendslam_cu', headerText: 'Dslam cũ', allowFiltering: true , allowHtml : true, width: 150},                                     
                        {fieldName: 'tendslam', headerText: 'Dslam mới', allowFiltering: true , allowHtml : true, width: 150},                         
                        ],
            value: {},
            isEnabled: true,
            checked: []
          },
          ghichu:'',
          biendong_dadv:'',

        }
    },
    methods:{
        
        showModal() {
            this.$refs["popupTraCuuThongSoKT"].show()
        },
        hideModal() {
            this.$refs["popupTraCuuThongSoKT"].hide()
        },
        handleShowModal(){
            this.dichvu_selected=this.dichvuvt_id
            this.ds_tocdokenh=[]
            setTimeout(()=>{
                this.initDuLieu()
            },500)
        },
        async initDuLieu(){
            this.input_ma_tb=this.ma_tb

            if(this.dichvu_selected==DichVuVienThong.CO_DINH){
              await this.$refs.coDinh.initDuLieu()
            }else if(this.dichvu_selected==DichVuVienThong.ADSL){
              await this.$refs.bangRongCoDinh.initDuLieu()
            }else if(this.dichvu_selected==DichVuVienThong.MEGAWAN||this.dichvu_selected==DichVuVienThong.METRONET){
              this.ds_tocdokenh=await this.lay_ds_tocdo_kenh()
              this.ds_loaithietbi=await this.sp_lay_ds_loai_tbi(this.dichvu_selected)
              await this.$refs.megawanMetronet.initDuLieu(this.ds_tocdokenh, this.ds_loaithietbi)
            }else if(this.dichvu_selected==DichVuVienThong.TSL){
              this.ds_tocdokenh=await this.lay_ds_tocdo_kenh()
              this.ds_loaithietbi=await this.sp_lay_ds_loai_tbi(9)
              await this.$refs.kenhThueRieng.initDuLieu(this.ds_tocdokenh, this.ds_loaithietbi)
            }else if(this.dichvu_selected==DichVuVienThong.GPHONE){
              await this.$refs.gphone.initDuLieu()
            }else if(this.dichvu_selected==DichVuVienThong.IMS){
              await this.$refs.ims.initDuLieu()
            }
            
            if(this.thuebao_id!=0&&this.dichvuvt_id!=0){
                await this.hienthi_ttkt(this.thuebao_id, this.dichvuvt_id)
                await this.hienthi_luoi(this.thuebao_id)
            }
            
        },
        changeLoai(event){
            this.loai=event.target.value 
        },
        async changeDichVu(){
          this.input_so_ao=''
          if(this.dichvu_selected==DichVuVienThong.CO_DINH){
            setTimeout(async()=>{
              await this.$refs.coDinh.clearDuLieu()
              await this.$refs.coDinh.initDuLieu()
            }, 500)
          }else if(this.dichvu_selected==DichVuVienThong.ADSL){
            setTimeout(async ()=>{
              await this.$refs.bangRongCoDinh.clearDuLieu()
              await this.$refs.bangRongCoDinh.initDuLieu()
            }, 500)
          }else if(this.dichvu_selected==DichVuVienThong.MEGAWAN||this.dichvu_selected==DichVuVienThong.METRONET){
            this.ds_tocdokenh=await this.lay_ds_tocdo_kenh()
            this.ds_loaithietbi=await this.sp_lay_ds_loai_tbi(this.dichvu_selected)
            await this.$refs.megawanMetronet.clearDuLieu()
            await this.$refs.megawanMetronet.initDuLieu(this.ds_tocdokenh, this.ds_loaithietbi)
          }else if(this.dichvu_selected==DichVuVienThong.TSL){
            setTimeout(async()=>{
              this.ds_tocdokenh=await this.lay_ds_tocdo_kenh()
              this.ds_loaithietbi=await this.sp_lay_ds_loai_tbi(9)
              await this.$refs.kenhThueRieng.clearDuLieu()
              await this.$refs.kenhThueRieng.initDuLieu(this.ds_tocdokenh, this.ds_loaithietbi)
            }, 500)
          }else if(this.dichvu_selected==DichVuVienThong.GPHONE){
            setTimeout(async()=>{
              await this.$refs.gphone.clearDuLieu()
              await this.$refs.gphone.initDuLieu()
            }, 500)
          }else if(this.dichvu_selected==DichVuVienThong.IMS){
            setTimeout(async()=>{
              await this.$refs.ims.clearDuLieu()
              await this.$refs.ims.initDuLieu()
            }, 500)
          }
        },
        async lay_ds_dichvu_vt(dsdichvuvt_id){
            try{
                this.loading(true)
                let response = await API.sp_ht_dichvuvt_combobox(this.axios, dsdichvuvt_id)
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
        async lay_ds_tocdo_kenh(){
            try{
                this.loading(true)
                let response = await API.lay_ds_tocdo_kenh(this.axios)
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
        async sp_lay_ds_loai_tbi(dichvuvt_id){
            try{
                this.loading(true)
                let response = await API.sp_lay_ds_loai_tbi(this.axios, dichvuvt_id)
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
        async lay_ds_hdtb_con_theo_hdtbid(hdtb_id, dichvuvt_id){
            try{
                this.loading(true)
                let response = await API.lay_ds_hdtb_con_theo_hdtbid(this.axios, hdtb_id, dichvuvt_id)
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
        async lay_ds_db_con_theo_tbid(thuebao_id, dichvuvt_id){
            try{
                this.loading(true)
                
                let response = await API.lay_ds_db_con_theo_tbid(this.axios, thuebao_id, dichvuvt_id)
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
        async sp_lay_tt_chitiet_theo_tb_id(thuebao_id){
            try{
                this.loading(true)
                var input = {};
                input.thuebao_id = thuebao_id;
                let response = await API.sp_lay_tt_chitiet_theo_tb_id(this.axios, input)
                this.loading(false)
                this.ghichu=''
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data&& response.data.data.data) {
                    console.log(response.data.data.data)
                    if (response.data.data.out_ghichu&&response.data.data.out_ghichu.length>0)
                      this.ghichu = response.data.data.out_ghichu[0].ghichu
                    return response.data.data.data[0]

                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async hienthi_ttkt(id, dichvuvt_id){
          console.log('hienthi_ttkt')
          console.log(this.loai)
          console.log(dichvuvt_id)
          
          if(dichvuvt_id==DichVuVienThong.DICHVU_CNTT||dichvuvt_id==DichVuVienThong.TRUNGTAM_DULIEU||dichvuvt_id==DichVuVienThong.ANTOAN_BAOMAT_TT||dichvuvt_id==DichVuVienThong.HOINGHI_TRUYENHINH){                
              console.log('cntt')
              await this.$refs.dichvuCNTT.hienthi_dulieu_thuebao(this.loaitb_id, id)
          } else if(dichvuvt_id==DichVuVienThong.DI_DONG){ 
                        
                await this.$refs.diDong.hienthi_dulieu_thuebao(this.ma_tb,'')
          } else if(dichvuvt_id==DichVuVienThong.MEGA_EYES||dichvuvt_id==DichVuVienThong.IMS_DOITAC
                  ||dichvuvt_id==DichVuVienThong.BRCD_DOITAC||dichvuvt_id==DichVuVienThong.MGW_DOITAC
                  ||dichvuvt_id==DichVuVienThong.TSL_DOITAC){
                await this.$refs.megaEyes.hienthi_dulieu_thuebao(this.ma_tb,'')
          } 

          else {
            //let result = await this.lay_ds_db_con_theo_tbid(id, dichvuvt_id)
            
            let result = await this.sp_lay_tt_chitiet_theo_tb_id(id)

            if(result ){
              let item=result
              if(dichvuvt_id==DichVuVienThong.CO_DINH){
                await this.$refs.coDinh.hienthi_dulieu_thuebao(item, this.ma_tb,this.ghichu)
              }else if(dichvuvt_id==DichVuVienThong.ADSL){
                this.input_so_ao=item.ma_lt!=null?item.ma_lt.toString():''
                await this.$refs.bangRongCoDinh.hienthi_dulieu_thuebao(item, this.ma_tb)
              }else if(dichvuvt_id==DichVuVienThong.MEGAWAN||dichvuvt_id==DichVuVienThong.METRONET||dichvuvt_id==26){
                this.input_so_ao=item.ma_lt!=null?item.ma_lt.toString():''
                await this.$refs.megawanMetronet.hienthi_dulieu_thuebao(item, this.ma_tb,this.ghichu)
              }else if(dichvuvt_id==DichVuVienThong.TSL||dichvuvt_id==25){
                await this.$refs.kenhThueRieng.hienthi_dulieu_thuebao(result, id,this.ghichu,this.ma_tb)
              }else if(dichvuvt_id==DichVuVienThong.GPHONE){
                await this.$refs.gphone.hienthi_dulieu_thuebao(item, this.ma_tb,this.ghichu)
              }else if(dichvuvt_id==DichVuVienThong.IMS){
                await this.$refs.ims.hienthi_dulieu_thuebao(item, this.ma_tb,this.ghichu)
              } 

            }else{
              this.$toast.error('Không tìm thấy dữ liệu !')
              //clear dữ liệu
              this.input_so_ao=''
              if(dichvuvt_id==DichVuVienThong.CO_DINH){
                await this.$refs.coDinh.clearDuLieu()
              }else if(dichvuvt_id==DichVuVienThong.ADSL){
                await this.$refs.bangRongCoDinh.clearDuLieu()
              }else if(dichvuvt_id==DichVuVienThong.MEGAWAN||dichvuvt_id==DichVuVienThong.METRONET||dichvuvt_id==26){
                await this.$refs.megawanMetronet.clearDuLieu()
              }else if(dichvuvt_id==DichVuVienThong.TSL||dichvuvt_id==25){
                await this.$refs.kenhThueRieng.clearDuLieu()
              }else if(dichvuvt_id==DichVuVienThong.GPHONE){
                await this.$refs.gphone.clearDuLieu()
              }else if(dichvuvt_id==DichVuVienThong.IMS){
                await this.$refs.ims.clearDuLieu()
              }
            }
          }

          
        },
        async hienthi_luoi(thuebao_id){
            try{
                if (this.dichvu_selected==1||this.dichvu_selected==10||this.dichvu_selected==11||this.dichvu_selected==4||this.dichvu_selected==7||this.dichvu_selected==8||this.dichvu_selected==26) {                  
                  //Lấy thông tin danh sách thuê bao đơn vị
                  var input1 = {};
                  input1.thuebao_id = thuebao_id;                  
                  this.loading(true);                  
                  await API.sp_lay_ds_thuebao_donvi_theo_tb_id(this.axios, input1).then((response) => {                      
                      this.loading(false);
                      if(response.data.error_code && response.data.error_code === this.success_code) {
                          console.log(response.data.data);
                          this.ds_thuebaodonvi.list = response.data.data;
                      }
                      else
                      {
                          this.$toast.error('Không tìm thấy thông tin thuê bao đơn vị!')                        
                      }
                  }
                  ).finally(()=>{ this.loading(false); });
                }
                //Lấy thông tin nhân viên khu vực 
                if (this.dichvu_selected==1||this.dichvu_selected==11||this.dichvu_selected==4||this.dichvu_selected==7||this.dichvu_selected==8||this.dichvu_selected==9||this.dichvu_selected==25||this.dichvu_selected==26) {                  
                  var input2 = {};
                  input2.thuebao_id = thuebao_id;                  
                  this.loading(true);                  
                  await API.sp_lay_ds_nhanvien_khuvuc_theo_tb_id(this.axios, input2).then((response) => {
                      this.loading(false);
                      if(response.data.error_code && response.data.error_code === this.success_code) {
                          console.log(response.data.data);
                          this.ds_nvkvtheothuebao.list = response.data.data;
                      }
                      else
                      {
                          this.$toast.error("Không tìm thấy thông tin nhân viên khu vực!")
                      }
                  }
                  ).finally(()=>{ this.loading(false); });
                }
                //Lấy thông tin biến động gói đa dịch vụ
                if (this.dichvu_selected==4||this.dichvu_selected==10||this.dichvu_selected==11) {                  
                  var input3 = {};
                  input3.thuebao_id = thuebao_id;                  
                  this.loading(true);                  
                  await API.sp_lay_ds_da_goi_dv_theo_tb_id(this.axios, input3).then((response) => {
                      this.loading(false);
                      if(response.data.error_code && response.data.error_code === this.success_code) {                          
                          this.ds_biendonggoiddv.list = response.data.data;
                      }
                      else
                      {
                          this.$toast.error("Không tìm thấy thông tin gói đa dịch vụ!")
                      }
                  }
                  ).finally(()=>{  this.loading(false); });
                }
                
                //Lấy thông tin thông tin kỹ thuât
                if (this.dichvu_selected==1||this.dichvu_selected==4||this.dichvu_selected==7||this.dichvu_selected==8||this.dichvu_selected==11||this.dichvu_selected==9||this.dichvu_selected==26||this.dichvu_selected==25) {                  
                  var input4 = {};
                  input4.thuebao_id = thuebao_id;                  
                  this.loading(true);                  
                  await API.sp_lay_ds_cung_doi_cap_theo_tb_id(this.axios, input4).then((response) => {                      
                      if(response.data.error_code && response.data.error_code === this.success_code) {                          
                          this.ds_thuebaocungdoicap.list = response.data.data;
                      }
                      else
                      {
                          this.$toast.error("Không tìm thấy thông tin thuê bao cùng đôi cáp!")
                      }
                  }                  
                  );
                  await API.sp_lay_ds_hist_kythuat_theo_tb_id(this.axios, input4).then((response) => {
                      
                      if(response.data.error_code && response.data.error_code === this.success_code) {                          
                          this.ds_lichsu_tskt.list = response.data.data;
                      }                      
                  }                  
                  );
                  this.loading(false);
                }
            }catch(e){
                this.loading(false) 
                console.log(e)               
            }
        },
    }
}
</script>
<style scoped>
 .pd-5{padding: 0 5px;}
</style>