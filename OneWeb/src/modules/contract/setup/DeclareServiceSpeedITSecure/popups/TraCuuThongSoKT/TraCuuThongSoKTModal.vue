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
          <span class="icon one-notepad"></span> Tra cứu thông số kỹ thuật
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>

      <ActionTop :actions="actions" @onActionClick="onActionClick" :isMore="true"/>

      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin tra cứu</div>
          <div class="row">
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w30 nowrap">Dịch vụ</div>
                <div class="value">
                  <div class="select-custom">
                    <select v-model="dichvu_selected" @change="changeDichVu" disabled class="form-control">
                      <option v-for="item in ds_dichvu" :key="item.dichvuvt_id" :value="item.dichvuvt_id">
                          {{item.ten_dvvt}}
                      </option>
                    </select>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w30 nowrap">Số máy/Acc</div>
                <div class="value">
                  <input type="text" :value="input_ma_tb" @change="e=>input_ma_tb=e.target.value" readonly class="form-control highlight" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w30 nowrap">Số ảo</div>
                <div class="value">
                  <input type="text" :value="input_so_ao" @change="e=>input_so_ao=e.target.value" readonly class="form-control highlight" />
                </div>
                <div class="value w30 nowrap">
                  <div class="list-checks mart7">
                    <div class="item">
                      <div class="check-action">
                        <input type="radio" class="check" name="loai" value="0" disabled :checked="loai==0" @change="changeLoai($event)"/>
                        <span class="name">Hợp đồng</span>
                      </div>
                    </div>
                    <div class="item">
                      <div class="check-action">
                        <input type="radio" class="check" ame="loai" value="1" disabled :checked="loai==1" @change="changeLoai($event)"/>
                        <span class="name">Danh bạ</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="legend-title mart20">Thông số kỹ thuật</div>
          <CoDinh ref="coDinh" v-if="dichvu_selected==1"/>
          <BangRongCoDinh ref="bangRongCoDinh" v-else-if="dichvu_selected==4"/>
          <MegawanMetronet ref="megawanMetronet" v-else-if="dichvu_selected==7||dichvu_selected==8"/>
          <KenhThueRieng ref="kenhThueRieng" v-else-if="dichvu_selected==9"/>
          <Gphone ref="gphone" v-else-if="dichvu_selected==10"/>
          <IMS ref="ims" v-else-if="dichvu_selected==11"/>

        </div>
      </div>
      <!--  -->
      <TraCuuVatTuThueBao ref='formVattu' idModalTraCuuVT="ModalTraCuuVT"/> 
      <b-modal
        ref="popupTraCuuVatTuHDTB"
        size="xl"
        hide-header
        hide-header-close
        hide-footer
        body-class="p-0"
      >
        <TraCuuVatTuHDTB :baohong_id="0" :hdtb_id="hdtb_id"/>
      </b-modal>

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
import {DichVuVienThong} from '../../ThamSo'
// import TraCuuVatTuThueBao from '../../../../../QLVT/components/TraCuuVatTuThueBao/TraCuuVatTuThueBao.vue'
import TraCuuVatTuThueBao from './TraCuuVatTuThueBao/TraCuuVatTuThueBao.vue'
import TraCuuVatTuHDTB from '../../../../../search/subscriber/SearchInstallDetail/popup/TraCuuVatTuHDTB.vue'
export default {
    name:'TraCuuThongSoKTModal',
    props:{
        dichvuvt_id:{
            type:Number,
            default:1
            //default:9
        },
        hdtb_id:{
            type:Number,
            default:0
            //default:4130927
        },
        kieu:{
            type:Number,
            default:0 //0 : hợp đồng, 1 : danh bạ
        },
        thuebao_id:{
            type:Number,
            default:0
            //default:1196535
        },
        ma_tb:{
            type:String,
            default:''
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
        TraCuuVatTuThueBao,
        TraCuuVatTuHDTB
    },
    data(){
        return {
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
            ds_loaithietbi:[]

        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='vattu'){
              if(this.loai==0){
                this.$refs.popupTraCuuVatTuHDTB.show()
              }else{
                //Tham chiếu UR13.1.037     
                this.$refs.formVattu.p_so_may=this.ma_tb
                this.$refs.formVattu.frmTraCuuVatTuTB(this.thuebao_id)
                this.$bvModal.show('popup-tracuuvattuthuebao')
              }
            }
        },
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

            if(this.kieu==0){
                this.loai=0
                if(this.hdtb_id != 0 && this.dichvuvt_id != 0){
                    await this.hienthi_ttkt(this.hdtb_id, this.dichvuvt_id)
                }
            }else{
                this.loai=1
                if(this.thuebao_id!=0&&this.dichvuvt_id!=0){
                    await this.hienthi_ttkt(this.thuebao_id, this.dichvuvt_id)
                }
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
        async hienthi_ttkt(id, dichvuvt_id){
          if(this.loai==0){
            let result=await this.lay_ds_hdtb_con_theo_hdtbid(id, dichvuvt_id)
            if(result.length>0){
              let item=result[0]
              if(dichvuvt_id==DichVuVienThong.CO_DINH){
                this.input_so_ao=item.matb_tn!=null?item.matb_tn.toString():''
                await this.$refs.coDinh.hienthi_dulieu_hdtb(item, id)
              }else if(dichvuvt_id==DichVuVienThong.ADSL){
                this.input_so_ao=item.ma_lt!=null?item.ma_lt.toString():''
                await this.$refs.bangRongCoDinh.hienthi_dulieu_hdtb(item, id)
              }else if(dichvuvt_id==DichVuVienThong.MEGAWAN||dichvuvt_id==DichVuVienThong.METRONET){
                this.input_so_ao=item.ma_lt!=null?item.ma_lt.toString():''
                await this.$refs.megawanMetronet.hienthi_dulieu_hdtb(item, id)
              }else if(dichvuvt_id==DichVuVienThong.TSL){
                await this.$refs.kenhThueRieng.hienthi_dulieu_hdtb(result, id)

              }else if(dichvuvt_id==DichVuVienThong.GPHONE){
                await this.$refs.gphone.hienthi_dulieu_hdtb(item, id)
              }else if(dichvuvt_id==DichVuVienThong.IMS){
                await this.$refs.ims.hienthi_dulieu_hdtb(item, id)
              }
            }else{
              this.$toast.error('Không tìm thấy dữ liệu !')
              //clear dữ liệu
              this.input_so_ao=''
              if(dichvuvt_id==DichVuVienThong.CO_DINH){
                await this.$refs.coDinh.clearDuLieu()
              }else if(dichvuvt_id==DichVuVienThong.ADSL){
                await this.$refs.bangRongCoDinh.clearDuLieu()
              }else if(dichvuvt_id==DichVuVienThong.MEGAWAN||dichvuvt_id==DichVuVienThong.METRONET){
                await this.$refs.megawanMetronet.clearDuLieu()
              }else if(dichvuvt_id==DichVuVienThong.TSL){
                await this.$refs.kenhThueRieng.clearDuLieu()
              }else if(dichvuvt_id==DichVuVienThong.GPHONE){
                 await this.$refs.gphone.clearDuLieu()
              }else if(dichvuvt_id==DichVuVienThong.IMS){
                 await this.$refs.ims.clearDuLieu()
              }
            }
          }else{
            let result = await this.lay_ds_db_con_theo_tbid(id, dichvuvt_id)
            if(result.length>0){
              let item=result[0]
              if(dichvuvt_id==DichVuVienThong.CO_DINH){
                await this.$refs.coDinh.hienthi_dulieu_thuebao(item, id)
              }else if(dichvuvt_id==DichVuVienThong.ADSL){
                this.input_so_ao=item.ma_lt!=null?item.ma_lt.toString():''
                await this.$refs.bangRongCoDinh.hienthi_dulieu_thuebao(item, id)
              }else if(dichvuvt_id==DichVuVienThong.MEGAWAN||dichvuvt_id==DichVuVienThong.METRONET){
                this.input_so_ao=item.ma_lt!=null?item.ma_lt.toString():''
                await this.$refs.megawanMetronet.hienthi_dulieu_thuebao(item, id)
              }else if(dichvuvt_id==DichVuVienThong.TSL){
                await this.$refs.kenhThueRieng.hienthi_dulieu_thuebao(result, id)
              }else if(dichvuvt_id==DichVuVienThong.GPHONE){
                await this.$refs.gphone.hienthi_dulieu_thuebao(item, id)
              }else if(dichvuvt_id==DichVuVienThong.IMS){
                await this.$refs.ims.hienthi_dulieu_thuebao(item, id)
              }

            }else{
              this.$toast.error('Không tìm thấy dữ liệu !')
              //clear dữ liệu
              this.input_so_ao=''
              if(dichvuvt_id==DichVuVienThong.CO_DINH){
                await this.$refs.coDinh.clearDuLieu()
              }else if(dichvuvt_id==DichVuVienThong.ADSL){
                await this.$refs.bangRongCoDinh.clearDuLieu()
              }else if(dichvuvt_id==DichVuVienThong.MEGAWAN||dichvuvt_id==DichVuVienThong.METRONET){
                await this.$refs.megawanMetronet.clearDuLieu()
              }else if(dichvuvt_id==DichVuVienThong.TSL){
                await this.$refs.kenhThueRieng.clearDuLieu()
              }else if(dichvuvt_id==DichVuVienThong.GPHONE){
                 await this.$refs.gphone.clearDuLieu()
              }else if(dichvuvt_id==DichVuVienThong.IMS){
                 await this.$refs.ims.clearDuLieu()
              }
            }

          }
        }
    }
}
</script>