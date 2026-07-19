<template>
  <b-modal
    id="correct_owner"
    ref="popupCorrectOwner"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    centered
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Thay đổi chủ quản
        </div>
        <div class="close -ap icon-close" @click="hideModal" ></div>
      </div>

      <ActionTop :actions="actions"  @onActionClick="onActionClick"/>

      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin hợp đồng</div>
          <div class="row">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w100">Số máy/Acc</div>
                <div class="value">
                  <div class="input-more-button">
                    <button class="btn" @click="seachContract" >
                      <span class="-ap icon-more_horiz"></span>
                    </button>
                    <input type="text" ref="inputMaTB" v-model="input_matb" @keyup.enter="onEnterMaTB($event)" class="form-control bold" />
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-8 col-12">
              <div class="info-row">
                <div class="key w100">Dịch vụ</div>
                <div class="value">
                  <ejs-dropdownlist 
                        :allowFiltering="true"
                        v-model="dichvu_selected"
                        :dataSource="dichvu"
                        :fields="{ text: 'TEN_DVVT', value: 'DICHVUVT_ID' }" />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w100">Trạng thái TB</div>
                <div class="value">
                  <input type="text" readonly v-model="input_trangthai" class="form-control text-danger font-italic" />
                </div>
              </div>
            </div>
            <div class="col-sm-8 col-12">
              <div class="info-row">
                <div class="key w100">Tên thuê bao</div>
                <div class="value">
                  <input type="text" readonly v-model="input_ten_tb" class="form-control font-weight-bold" />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w100">Chủ quản</div>
                <div class="value" >
                    <ejs-dropdownlist 
                        :allowFiltering="true"
                        v-model="chuquan_selected"
                        :dataSource="chuquan"
                        :filtering="onFilteringChuQuan"
                        :itemTemplate='itemTemplate'
                        :valueTemplate='valueTemplate'
                        :fields="{ text: 'TENCHUQUAN', value: 'CHUQUAN_ID' }" />
                </div>
              </div>
            </div>
            <div class="col-sm-8 col-12">
              <div class="info-row">
                <div class="key w100">Địa chỉ TB</div>
                <div class="value">
                  <input type="text" readonly v-model="input_diachi_tb" class="form-control font-weight-bold" />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w100">Chủ quản LĐ</div>
                <div class="value">
                    <ejs-dropdownlist 
                        :allowFiltering="true"
                        v-model="chuquan_ld_selected"
                        :dataSource="chuquan"
                        :filtering="onFilteringChuQuan"
                        :itemTemplate='itemTemplate'
                        :valueTemplate='valueTemplate'
                        :fields="{ text: 'TENCHUQUAN', value: 'CHUQUAN_ID' }" />
                </div>
              </div>
            </div>
            <div class="col-sm-8 col-12">
              <div class="info-row">
                <div class="key w100">Địa chỉ LĐ</div>
                <div class="value">
                  <input type="text" readonly v-model="input_diachi_ld" class="form-control font-weight-bold" />
                </div>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w100">Ghi chú</div>
            <div class="value">
              <input type="text" ref="inputGhiChu" v-model="input_ghichu" class="form-control" />
            </div>
          </div>
        </div>
      </div>

      <!-- Modal -->
      <SearchAccountModal ref="searchAccountModal" @form-close="formClose"/>
      <DanhSachTBCungMaModal ref="danhSachTBCungMaModal" :dataSources="danhsach_tb" @accept="accept" />
    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KSelect from '@/components/kylq_components/KSelect.vue'
import SearchAccountModal from './SearchAccountModal.vue'
import CorrectOwnerAPI from './CorrectOwnerAPI'
import moment from 'moment'
import DanhSachTBCungMaModal from './DanhSachTBCungMaModal.vue'
import Vue from "vue"
import { DataManager,Query,ODataV4Adaptor,Predicate } from "@syncfusion/ej2-data"
var itemVue = Vue.component("itemTemplate", {
  template: `<span>{{data.TENCHUQUAN}}</span>`,
  data() {
    return {
      data: {}
    }
  }
})
var valueVue = Vue.component("valueTemplate", {
  template: `<span>{{data.TEN_TAT}}</span>`,
  data() {
    return {
      data: {}
    }
  }
})
export default {
    name:'CorrectOwnerModal',
    components:{
        ActionTop,
        KSelect,
        SearchAccountModal,
        DanhSachTBCungMaModal
    },
    data(){
        return{
            actions:[
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save'
                },
                {
                    id:'huy',
                    name:'Huỷ',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_circle-remove'
                }
            ],
            input_matb:'',
            input_trangthai:'',
            input_ten_tb:'',
            input_diachi_tb:'',
            input_diachi_ld:'',
            input_ghichu:'',
            dichvu:[],
            dichvu_selected:null,
            chuquan:[],
            chuquan_selected:null,
            chuquan_ld_selected:null,
            danhsach_tb:[],
            thuebao_selected:null,
            //dịch vụ cho phép thự hiện tính năng
            DichVuVienThong:{
                CO_DINH: 1,
                IMS: 11,
                ADSL: 4,
                MEGAWAN: 8,
                METRONET: 7,
                TSL: 9,
                GPHONE: 10
            },
            chuquan_id:0,
            chuquan_ld_id:0,
            target: '.main-wrapper',
            animationSettings: { effect: 'Zoom' },
            itemTemplate : function() {
                return {template: itemVue};
            },
            valueTemplate: function() {
                return {template: valueVue};
            },
        }
    },
    methods:{
        showModal() {
          this.$refs["popupCorrectOwner"].show()
        },
        hideModal() {
          this.$refs["popupCorrectOwner"].hide()
        },
        handleShowModal(){
            this.clear()
            this.dichvu=[]
            this.dichvu_selected=null
            this.chuquan=[]
            this.danhsach_tb=[]
            this.chuquan_id=0
            this.chuquan_ld_id=0
        
            setTimeout(()=>{
              this.$refs.inputMaTB.focus()
              this.khoitao_dulieu()
            }, 500)
            
        },
        // selectValueDichVu(){
        //   console.log('selectValueDichVu', this.dichvu_selected)
        // },
        onActionClick(action){
            if(action.id=='ghilai'){
                //
                if(this.thuebao_selected==null){
                    this.$toast.error('Hãy chọn thuê bao cần sửa')
                    return
                }
                if(this.input_ghichu.trim().length>300){
                    this.$toast.error('Dữ liệu ghi chú không được phép vượt quá 300 ký tự!')
                    this.$refs.inputGhiChu.focus()
                    return
                }
                if(this.dichvu_selected==this.DichVuVienThong.CO_DINH||this.dichvu_selected==this.DichVuVienThong.IMS||
                    this.dichvu_selected==this.DichVuVienThong.ADSL||this.dichvu_selected==this.DichVuVienThong.MEGAWAN||
                    this.dichvu_selected==this.DichVuVienThong.METRONET||this.dichvu_selected==this.DichVuVienThong.TSL||
                    this.dichvu_selected==this.DichVuVienThong.GPHONE){
                        //cố định chuyển trạng thái 1
                        let data={
                            thuebao_id: this.thuebao_selected.thuebao_id,
                            ma_tb: this.input_matb.trim(),
                            dichvu_id: this.dichvu_selected,
                            chuquan_id: this.chuquan_selected!=null?this.chuquan_selected:0,
                            chuquan_ld_id: this.chuquan_ld_selected!=null?this.chuquan_ld_selected:0,
                            chuquan_id_old: this.chuquan_id,
                            chuquan_ld_id_old: this.chuquan_ld_id,
                            trangthai_id: 1,
                            ngay_dk: moment(new Date()).format('DD/MM/YYYY'),
                            ghichu: this.input_ghichu.trim()
                        }

                        this.ghilai_thay_doi_chuquan_bc(data)

                }else{
                    this.$toast.error('Không thể sửa chủ quản cho loại dịch vụ viễn thông này')
                    return
                }


            }else if(action.id=='huy'){
                this.clear()
            }
        },
        clear(){
            this.input_matb=''
            this.input_trangthai=''
            this.input_ten_tb=''
            this.input_diachi_tb=''
            this.input_diachi_ld=''
            this.input_ghichu=''
        },
        seachContract(){
            //open modal search contract UR2.8.002
            this.$refs.searchAccountModal.showModal()
        },
        //số máy/acc callback from UR2.8.002
        formClose(value){
            console.log('formClose',value)
            if(value){
              if(value.ma_tb&&value.ma_tb.toString().trim()!=''){
                  this.input_matb=value.ma_tb
                  this.lay_danhba_theo_matb(value.ma_tb, this.dichvu_selected, 0)
              }else{
                  this.$toast.error('Đối tượng chọn không chứa thông tin Số máy/Acc')
              }
            }
        },
        changeDichVuVT(data){
          this.dichvu_selected=data.DICHVUVT_ID
        },
        onEnterMaTB(event){
            let maTb=event.target.value
            if(maTb.trim().length>0){
                this.lay_danhba_theo_matb(maTb.trim(), this.dichvu_selected, 0)
            }
        },
        accept(item){
            this.thuebao_selected=item
            this.hienthi_tt_danhba(this.thuebao_selected)
        },
        onFilteringChuQuan(e) {
          var query = new Query();
          let predicate;  
          //frame the query based on search string with filter type.
          if (e.text.trim() !== ''){
            predicate  = new Predicate('TENCHUQUAN', 'contains', e.text.trim(), true);
            //predicate = predicate.or('TEN_KHO', 'contains', e.text);
            query.where(predicate)
          }
          //pass the filter data source, filter query to updateData method.
          e.updateData(this.chuquan, query);
        }, 
        async hienthi_tt_danhba(info){
            this.dichvu_selected=info.dichvuvt_id
            this.input_ten_tb=info.ten_tb
            this.input_diachi_tb=info.diachi_tb
            this.input_diachi_ld=info.diachi_ld
            this.input_trangthai=info.trangthai_tb
            
            //load chủ quản vs chủ quản ld
            this.chuquan_id=0
            this.chuquan_ld_id=0
            if(this.dichvu_selected==this.DichVuVienThong.CO_DINH||this.dichvu_selected==this.DichVuVienThong.IMS||
            this.dichvu_selected==this.DichVuVienThong.ADSL||this.dichvu_selected==this.DichVuVienThong.MEGAWAN||
            this.dichvu_selected==this.DichVuVienThong.METRONET||this.dichvu_selected==this.DichVuVienThong.TSL||
            this.dichvu_selected==this.DichVuVienThong.GPHONE){
                let thuebao_id=info.thuebao_id
                let response = await CorrectOwnerAPI.lay_ds_tt_danhba(this.axios, thuebao_id, this.dichvu_selected)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {

                    if(response.data.data.out&&response.data.data.out=='0'){

                        if(response.data.data.ds_con&&response.data.data.ds_con.length>0){
                            let result=response.data.data.ds_con[0]
                            //chuquanld_id
                            if(result.chuquan_id!=null){
                                this.chuquan_selected=result.chuquan_id
                                this.chuquan_id=result.chuquan_id
                            }else{
                                this.chuquan_selected=0
                            }
                            if(result.chuquanld_id!=null){
                                this.chuquan_ld_selected=result.chuquanld_id
                                this.chuquan_ld_id=result.chuquanld_id
                            }else{
                                this.chuquan_ld_selected=0
                            }
                        }else{
                            this.chuquan_selected=0
                            this.chuquan_ld_selected=0
                        }

                    }else{
                        this.chuquan_selected=0
                        this.chuquan_ld_selected=0
                        if(response.data.data.out){
                            this.$toast.error(response.data.data.out)
                        }
                        return
                    }
                    
                }

            }else{
                this.clear()
                this.$toast.error('Không thực hiện thay đổi chủ quản với dịch vụ này !')
                return
            }

        },
        async khoitao_dulieu(){
            try{
                this.loading(true)
                //load danh sách chủ quản
                let responseChuQuan = await CorrectOwnerAPI.lay_danhsach_chuquan(this.axios)
                if(responseChuQuan && responseChuQuan.data && responseChuQuan.data.error_code && responseChuQuan.data.error_code == 'BSS-00000000' && responseChuQuan.data.data) {
                    this.chuquan=responseChuQuan.data.data
                    if(this.chuquan.length>0){
                        this.chuquan_selected=this.chuquan[0].CHUQUAN_ID
                        this.chuquan_ld_selected=this.chuquan[0].CHUQUAN_ID
                    }
                }else{
                    this.chuquan=[]
                }
                //lấy danh sách dịch vụ viễn thông
                let responseDichVu = await CorrectOwnerAPI.lay_danhsach_dichvu(this.axios)
                if(responseDichVu && responseDichVu.data && responseDichVu.data.error_code && responseDichVu.data.error_code == 'BSS-00000000' && responseDichVu.data.data) {
                    this.dichvu=responseDichVu.data.data.sort((a, b)=>a.DICHVUVT_ID-b.DICHVUVT_ID)
                    if(this.dichvu.length>0){
                        //cố định
                        this.dichvu_selected=1
                    }
                }else{
                    this.dichvu=[]
                }

            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi khi khởi tạo dữ liệu')
                }
            }finally{
                this.loading(false)
            }
        },
        async lay_danhba_theo_matb(in_ma_tb, in_dichvuvt_id, in_donvi_dl_id){
            try{
                this.loading(true)
                this.danhsach_tb=[]
                this.thuebao_selected=null
                let response = await CorrectOwnerAPI.lay_danhba_theo_matb(this.axios, in_ma_tb, in_dichvuvt_id, in_donvi_dl_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.danhsach_tb=response.data.data
                    if(this.danhsach_tb.length>0){
                        if(this.danhsach_tb.length>1){
                            //show popup chọn ds tb cùng mã
                            this.$refs.danhSachTBCungMaModal.showModal()
                        }else{
                            this.thuebao_selected=this.danhsach_tb[0]
                            this.hienthi_tt_danhba(this.thuebao_selected)
                        }
                        
                    }else{
                        let dv=this.dichvu.find(x=>x.DICHVUVT_ID==this.dichvu_selected)
                        var mes=dv!=undefined?' trong danh bạ với dịch vụ '+dv.TEN_DVVT:''
                        this.$toast.error('Không tìm thấy thuê bao '+in_ma_tb + mes)
                    } 
                }else{
                    this.danhsach_tb=[]
                    this.thuebao_selected=null
                    let dv=this.dichvu.find(x=>x.DICHVUVT_ID==this.dichvu_selected)
                    var mes=dv!=undefined?' trong danh bạ với dịch vụ '+dv.TEN_DVVT:''
                    this.$toast.error('Không tìm thấy thuê bao '+in_ma_tb + mes)
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi không lấy được thông tin')
                }
            }
        },
        async ghilai_thay_doi_chuquan_bc(data){
            try{
                this.loading(true)
                let response=await CorrectOwnerAPI.ghilai_thay_doi_chuquan_bc(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    if(response.data.data=='0'){
                        this.$toast.success('Cập nhật thành công!')
                    }else{
                        this.$toast.error(response.data.data)
                    }
                    
                }else {
                    if(response && response.data &&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Không thành công!')
                    }
                }
            }catch(error){
                this.loading(false)
                if(error.data&&error.data.message){
                    this.$toast.error(error.data.message)
                }else if(error.response&&error.response.data&&error.response.data.message){
                    this.$toast.error(error.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, sửa chủ quản không thành công!')
                }
            }
        },


    },
    created(){
      this.$root.$on("bv::modal::shown", (bvEvent, modalId) => {
          document.getElementsByClassName("modal-content").forEach(function (v) {
              v.removeAttribute("tabindex");
          });
      });
    }
}
</script>
<style>
.e-input-value{
    padding-top: 6px;
    padding-left: 8px;
}
#idChuanHoaChuQuan{
  font-family: "Roboto", Arial, serif !important;
}
</style>
