<template>
    <div class="box-form">
          <div class="legend-title">Thông tin thanh toán</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="row">
                <div class="col-sm-7 col-12">
                  <div class="info-row">
                    <div class="key w120">Mã thanh toán</div>
                    <div class="value">
                      <div class="input-more-button">
                        <button class="btn">
                          <span class="-ap icon-more_horiz"></span>
                        </button>
                        <input type="text" class="form-control" v-model="formProps.ma_tt" readonly>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key w90">Mã đại diện</div>
                    <div class="value">
                      <div class="select-custom">
                        <SelectExt 
                          placeholder="Tìm kiếm"  
                          v-model="formProps.matb_dd" 
                          :allowFiltering="true" 
                          dataTextField="ma_tb"
                          dataValueField="ma_tb" 
                          :dataSource="optionProps.cbx_thuebao_dd" 
                          @change="selectThuebao($event)">
                      </SelectExt>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">Tên thanh toán</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="formProps.ten_tt">
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">Địa chỉ TT</div>
                <div class="value">
                  <div class="input-more-button">
                    <button class="btn"  v-b-modal.popupDiaChi_TT>
                      <span class="-ap icon-more_horiz"></span>
                    </button>
                    <input type="text" class="form-control" v-model="formProps.diachi_tt">
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">Địa chỉ CT</div>
                <div class="value">
                  <div class="input-more-button">
                    <button class="btn" v-b-modal.popupDiaChi_CT>
                      <span class="-ap icon-more_horiz"></span>
                    </button>
                    <input type="text" class="form-control" v-model="formProps.diachi_ct">
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">Địa chỉ BC</div>
                <div class="value">
                  <div class="input-more-button">
                    <button class="btn" v-b-modal.popupDiaChi_BC>
                      <span class="-ap icon-more_horiz"></span>
                    </button>
                    <input type="text" class="form-control" v-model="formProps.diachi_bc" readonly>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="row">
                <div class="col-sm-7 col-12">
                  <div class="info-row">
                    <div class="key w120">Mã in ghép</div>
                    <div class="value">
                      <div class="input-more-button">
                        <button class="btn" @click="btnTaoMIG_Click">
                          <span class="-ap icon-more_horiz"></span>
                        </button>
                        <input type="text" class="form-control" v-model="formProps.main_ghep" readonly>
                      </div>
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w120">Hình thức TT (*)</div>
                    <div class="value">
                      <div class="select-custom">                        
                        <SelectExt 
                          placeholder="Tìm kiếm"  
                          v-model="formProps.httt_id" 
                          :allowFiltering="true" 
                          dataTextField="hinhthuc_tt"
                          dataValueField="httt_id" 
                          :dataSource="optionProps.cbx_httt" 
                          @change="selectHttt($event)">
                        </SelectExt>
                      </div>
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w120">Ngân hàng</div>
                    <div class="value w20 padt7">
                      <div class="check-action">
                        <input type="checkbox" class="check" v-model="optionProps.chknganhang_tt">
                        <span class="name"></span>
                      </div>
                    </div>
                    <div class="value">
                      <div class="input-more-button">
                        <button class="btn">
                          <span class="-ap icon-more_horiz"></span>
                        </button>
                        <div class="select-custom">
                          <SelectExt 
                            placeholder="Tìm kiếm"  
                            v-model="formProps.nganhang_id" 
                            :disabled="!optionProps.chknganhang_tt" 
                            :allowFiltering="true" 
                            dataTextField="ten_nh"
                            dataValueField="nganhang_id" 
                            :dataSource="optionProps.cbx_nganhangtt" 
                            @change="selectNganhangTT($event)">
                          </SelectExt>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w120">Chủ tài khoản</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="formProps.chu_tk"  readonly>
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w120">Ghi chú</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="formProps.ghichu">
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w120">Email</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="formProps.email" readonly>
                    </div>
                  </div>
                </div>
                <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key w90">Đơn vị QL</div>
                    <div class="value">
                      <div class="select-custom">
                        <SelectExt 
                            placeholder="Tìm kiếm"  
                            v-model="formProps.donvi_id" 
                            :allowFiltering="true" 
                            dataTextField="NAME"
                            dataValueField="ID" 
                            :dataSource="optionProps.cbx_donvitt" 
                            @change="selectDonvi($event)"
                            class="disabled"
                            >
                          </SelectExt>
                      </div>
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w90">Mã NVTC(*)</div>
                    <div class="value">
                      <div class="input-more-button">
                        <button class="btn" @click="onMaNhanVienThuCuoc">
                          <span class="-ap icon-more_horiz"></span>
                        </button>
                        <div class="row" >
                          <div class="col-sm-6 col-12">
                            <input type="text" class="form-control highlight" v-model="formProps.manv_tc"  readonly>
                            
                          </div>
                          <div class="col-sm-6 col-12">
                            <input type="text" class="form-control highlight" v-model="formProps.ma_tuyen"  readonly>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w90">Tài khoản</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="formProps.stk">
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w90">Mã số thuế</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="formProps.mst">
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w90">Mã NVQL</div>
                    <div class="value">
                      <input type="text" class="form-control highlight" v-model="formProps.ma_nvql"  readonly>
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w90">SĐT</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="formProps.so_dt">
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
              <ModalChonDiaChi modalId="popupDiaChi_TT" :data="formProps.d_diachi_tt" @xacnhan="getDiaChi_tt"/>
              <ModalChonDiaChi modalId="popupDiaChi_CT" :data="formProps.d_diachi_ct" @xacnhan="getDiaChi_ct"/>
              <ModalChonDiaChi modalId="popupDiaChi_BC" :data="formProps.d_diachi_bc" @xacnhan="getDiaChi_bc"/>
              <!-- <PopupTimKiemNhanVienThuCuoc 
                    :input="params_NVTC" 
                    :dvql="formProps.donvi_id"
                    @acceptChange="getNVTCProcess($event)"
                    ref="dlgTimKiemNVTC">
               </PopupTimKiemNhanVienThuCuoc> -->
               <TimKiemNhanVienThuCuoc 
                ref="dlgTimKiemNVTC" 
                :vkieu="1" 
                :vdonvi="parseInt(formProps.donvi_id)" 
                :vquan_id="formProps.d_diachi_tt.quan_id"
                :vphuong_id="formProps.d_diachi_tt.phuong_id"
                :vpho_id="formProps.d_diachi_tt.pho_id"
                :vnvtc="formProps.manv_tc"  
                @chonNVTC="getNVTCProcess($event)"
            ></TimKiemNhanVienThuCuoc>

              <b-modal id="popupComponents" size="xl"
                hide-footer
                hide-header
                hide-header-close
                body-class="modal-body p-0">
                <component :is="popupComponent" ref="popupComponent" :dulieu="popupComponentData" isPopup="true" @form-close="popupClosed" @acceptChangeCTV="popupClosed" @acceptChangeNGT="popupClosed"></component> <!--v-dynamic-events="popupComponentEvents"-->
              </b-modal>
        </div>
    
</template>
<script>
import PopupTimKiemNhanVienThuCuoc from '@/modules/contract/setup/InstallNewSubs/Popups/PopupTimKiemNhanVienThuCuoc';
import TimKiemNhanVienThuCuoc from '@/modules/QLTN/GachNo/components/TimNhanVienThuCuoc'
import ModalChonDiaChi from '@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi'
import KDatePicker from "@/components/kylq_components/KDatePicker.vue";
import moment from "moment";
import {
  DataManager,
  Query,
  ODataV4Adaptor,
  Predicate,
} from "@syncfusion/ej2-data";
export default {
  name: "ThongTinThanhToan",
  props: ["formProps","optionProps"],
  components: {
    KDatePicker,ModalChonDiaChi,PopupTimKiemNhanVienThuCuoc,TimKiemNhanVienThuCuoc
  },
  data() {
    return {   
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: "",
      popupComponentEvents: {
        "form-close": "popupClosed",
        acceptChangeCTV: "popupClosed",
        acceptChangeNGT: "popupClosed",
      }, 
      params_NVTC:{
        QUAN_ID:0,
        PHUONG_ID:0,
        PHO_ID:0
      }      
    };
  },
  methods: {
    getDiaChi_tt: function (data) {
        for (let key in data) {
          this.$set(this.$parent.form_TTKH.d_diachi_tt, key, data[key])
        }
    },
    getDiaChi_ct: function (data) {
        for (let key in data) {
          this.$set(this.$parent.form_TTKH.d_diachi_ct, key, data[key])
        }
    },
    getDiaChi_bc: function (data) {
        for (let key in data) {
          this.$set(this.$parent.form_TTKH.d_diachi_bc, key, data[key])
        }
    },
    btnTaoMIG_Click: function() {
        return;
        //TODO: CẬP NHẬT DANH BẠ
        try {
          if(this.hdkh_id != 0) {
            let obj = {};
            obj.hdkh_id = 0; //chờ form cập nhật
            obj.khachhang_id = this.$parent.form_TTKH.khachhang_id
            obj.hdtt_id = 0; //chờ form cập nhật
            obj.thanhtoan_id = this.$parent.form_TTTT.thanhtoan_id;
            obj.fmig_id = this.mig_id;
            obj.fhdmig_id = this.hdmig_id;
            obj.ma_tt = this.$parent.form_TTTT.ma_tt;
            obj.ten_tt = this.$parent.form_TTTT.ten_tt;

            this.popupComponent = ()=>import('../../../../contract/setup/InstallNewSubs/Popups/popupChonMaInGhep.vue');
            this.popupComponentName = "popupChonMaInGhep";
            this.popupComponentData = obj;
            this.$bvModal.show('popupComponents');
          }
          else {
            this.$root.toastError('Chưa có thông tin về hợp đồng khách hàng!');
            return;
          }
        } catch (error) {

        }
    },
    onMaNhanVienThuCuoc: function () {
      console.log('onMaNhanVienThuCuoc')
      //console.log(this.$parent.form_TTTT)
      // this.params_NVTC.QUAN_ID = this.$parent.form_TTTT.d_diachi_tt.quan_id
      // this.params_NVTC.PHUONG_ID = this.$parent.form_TTTT.d_diachi_tt.phuong_id
      // this.params_NVTC.PHO_ID = this.$parent.form_TTTT.d_diachi_tt.pho_id
      this.$refs.dlgTimKiemNVTC.openDialog();      
    },
    getNVTCProcess(value) {
      // this.nhanvientc_id = value.rowData.NHANVIEN_ID;
      // this.txtMaNVTC_KHNQ = value.maNVTC;
      // this.tuyenthu_id = value.rowData.TUYENTHU_ID;
      // this.txtMaTuyen_KHNQ = value.rowData.MA_TUYENTHU;
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    popupClosed: async function(data) {
        try {
          this.loading(true);
          switch (this.popupComponentName) {
            case "popupChonMaInGhep":
              if(data.hdmig_id) {
                this.hdmig_id = data.hdmig_id;
                this.txt_maIG.Value = data.main_ghep;
              }              
              break;
            default:
              break;
          }
        } catch (error) {

        } finally {
          this.$bvModal.hide('popupComponents')
          this.loading(false);
        }
      },
    selectThuebao(){

    },
    selectHttt(){

    },
    selectNganhangTT(){

    },
    selectDonvi(){

    },
  },
};
</script>      