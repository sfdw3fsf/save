<template>
  <div class="row">
    <div class="col-sm-6 col-12">
      <div class="row">
        <div class="col-sm-7 col-12">
          <div class="info-row">
            <div class="key w90">Loại HĐ</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt 
                    placeholder="Tìm kiếm"  
                    v-model="formProps.loaihd_id" 
                    :allowFiltering="true" 
                    dataTextField="TEN_LOAIHD"
                    dataValueField="LOAIHD_ID" 
                    :dataSource="optionProps.cbx_loaihd" 
                    @change="selectLoaihd($event)">
                </SelectExt>                                    
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Dịch vụ</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt 
                    placeholder="Tìm kiếm"  
                    v-model="formProps.dichvuvt_id" 
                    :allowFiltering="true" 
                    dataTextField="ten_dvvt"
                    dataValueField="dichvuvt_id" 
                    :dataSource="optionProps.cbx_dichvuvt" 
                    @change="selectDichvuvt($event)">
                </SelectExt> 
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Kiểu lắp đặt</div>
            <div class="value">
              <SelectExt 
                    placeholder="Tìm kiếm"  
                    v-model="formProps.kieuld_id" 
                    :allowFiltering="true" 
                    dataTextField="ten_kieuld"
                    dataValueField="kieuld_id" 
                    :dataSource="optionProps.cbx_kieuld" 
                    @change="selectKieuld($event)">
                </SelectExt> 
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Mức cước TB</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt 
                    placeholder="Tìm kiếm"  
                    v-model="formProps.mucuoctb_id" 
                    class="disabled" 
                    :allowFiltering="true" 
                    dataTextField="MUCCUOC"
                    dataValueField="MUCUOCTB_ID" 
                    :dataSource="optionProps.cbx_muccuoctb" 
                    @change="selectMuccuoctb($event)">
                </SelectExt> 
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Số máy/Acc</div>
            <div class="value">              
              <input type="text"  @keyup.enter="TimThongTinKH" class="form-control highlight" v-model="formProps.ma_tb">
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Thanh toán</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt 
                    placeholder="Tìm kiếm"  
                    v-model="formProps.thanhtoan_id" 
                    :allowFiltering="true" 
                    dataTextField="tt_thanhtoan"
                    dataValueField="thanhtoan_id" 
                    :dataSource="optionProps.cbx_thanhtoan" 
                    @change="selectThanhtoan($event)">
                </SelectExt> 
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Tên thuê bao</div>
            <div class="value">
              <input type="text" class="form-control" v-model="formProps.ten_tb">
            </div>
          </div>
        </div>
        <div class="col-sm-5 col-12">
          <div class="info-row">
            <div class="key w90">Trạng thái TB</div>
            <div class="value">
              <input type="text" class="form-control" v-model="formProps.trangthai_tb">
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Loại hình</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt 
                    placeholder="Tìm kiếm"  
                    v-model="formProps.loaitb_id" 
                    :allowFiltering="true" 
                    dataTextField="NAME"
                    dataValueField="ID" 
                    :dataSource="optionProps.cbx_loaihinh" 
                    @change="selectLoaihinh($event)">
                </SelectExt>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Khu vực</div>
            <div class="value">
              <div class="input-more-button">
                <button class="btn"  v-b-modal.popupNVDB v-show="visibles.btnKhuvuc">
                  <span class="-ap icon-more_horiz"></span>
                </button>
                <input type="text" class="form-control" v-show="visibles.btnKhuvuc" v-model="formProps.khuvuc" readonly>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Đối tượng</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt 
                    placeholder="Tìm kiếm"  
                    class="disabled" 
                    v-model="formProps.doituong_id" 
                    :allowFiltering="true" 
                    dataTextField="ten_dt"
                    dataValueField="doituong_id" 
                    :dataSource="optionProps.cbx_doituong" 
                    @change="selectDoituong($event)">
                </SelectExt>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Số ảo</div>
            <div class="value">
              <input type="text" class="form-control" v-model="formProps.ma_lt" readonly>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">
              <a href="#" class="link underline" @click="hienThiKhuyenMai"
                >Danh sách khuyến mãi đặt cọc</a
              >
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Ngày sinh(*)</div>
            <div class="value">              
              <k-date-picker ref="ngay_sn" format="DD/MM/YYYY" type="datetime" v-model="formProps.ngay_sn" readonly/>
            </div>
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key w90">Địa chỉ TB</div>
        <div class="value">
          <div class="input-more-button">
            <button class="btn" v-b-modal.popupDiaChi_TB>
              <span class="-ap icon-more_horiz"></span>
            </button>
            <input type="text" class="form-control" v-model="formProps.diachi_tb" readonly>
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key w90">Địa chỉ LĐ</div>
        <div class="value">
          <div class="input-more-button">
            <button class="btn" v-b-modal.popupDiaChi_LD >
              <span class="-ap icon-more_horiz"></span>
            </button>
            <input type="text" class="form-control" v-model="formProps.diachi_ld" readonly>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-7 col-12">
          <div class="info-row">
            <div class="key w90">Đơn vị QL</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt 
                    placeholder="Tìm kiếm"  
                    v-model="formProps.donvi_id" 
                    :allowFiltering="true" 
                    dataTextField="TEN_DV"
                    dataValueField="DONVI_ID" 
                    :dataSource="optionProps.cbx_donvi" 
                    @change="selectDonviql($event)">
                </SelectExt>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-5 col-12">
          <div class="info-row">
            <div class="key w90">Mã ĐH Vip</div>
            <div class="value">
              <input type="text" class="form-control" v-model="formProps.ma_dhvip" readonly>
            </div>
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key w90">Ghi chú</div>
        <div class="value">
          <input type="text" class="form-control" v-model="formProps.ghichu">
        </div>
      </div>
      <div class="row">
        <div class="col-sm-7 col-12">
          <div class="info-row">
            <div class="key w90">Số tháng CK</div>
            <div class="value w60">
              <input type="text" class="form-control" v-model="formProps.sothang_ck" readonly>
            </div>
            <div class="key w90 padl10">Ngày KTCK</div>
            <div class="value">
              <k-date-picker ref="ngay_ktck" class="disabled"  format="DD/MM/YYYY" type="datetime" v-model="formProps.ngay_ktck" readonly/>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">DV khác</div>

            <div class="value">
              <textarea
                name=""
                class="form-control"
                style="height: 100px; resize: none"
                v-model="formProps.dv_khac"
                 readonly
              ></textarea>
            </div>
          </div>
        </div>
        <div class="col-sm-5 col-12">
          <div class="info-row">
            <div class="key w90">DV cộng thêm</div>
            <div class="value">
              <textarea
                name=""
                class="form-control"
                style="height: 135px; resize: none"
                v-model="formProps.dv_congthem"
                 readonly
              ></textarea>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="col-sm-6 col-12" id="TTTB_DichVu">
      <div class="row">
        <div class="col-sm-8 col-12">
          <div class="info-row">
            <div class="key w90">Ngày sử dụng</div>
            <div class="value">
              <div class="input-icon-right">
                <k-date-picker ref="ngay_sd" format="DD/MM/YYYY HH:mm:ss" type="datetime" v-model="formProps.ngay_sd"/>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Cước TB</div>
            <div class="value">
              <input type="text" class="form-control" v-model="formProps.cuoc_tb">
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Trang bị</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt 
                    placeholder="Tìm kiếm"
                    class="disabled"   
                    v-model="formProps.db_con.trangbi_id" 
                    :allowFiltering="true" 
                    dataTextField="tentrangbi"
                    dataValueField="trangbi_id" 
                    :dataSource="optionProps.cbx_trangbi" 
                    @change="selectTrangbi($event)">
                </SelectExt>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-4 col-12">
          <div class="info-row">
            <div class="key w80">Gói KT</div>
            <div class="value">
              <input type="text" class="form-control" v-model="formProps.goi_kt"  readonly>
            </div>
          </div>
          <div class="info-row">
            <div class="key w80">Cước DV</div>
            <div class="value">
              <input type="text" class="form-control" v-model="formProps.cuoc_dv"  readonly>
            </div>
          </div>
          <div class="info-row">
            <div class="key w80">Số lượng</div>
            <div class="value">
              <input type="text" class="form-control" v-model="formProps.soluong" :title="titleProps.title_sl" readonly>
            </div>
          </div>
        </div>
      </div>
      <MacDinh :formProps="formProps.db_con" v-show="formProps.dichvuvt_id == DichVuVienThong.TATCA" ref="macdinh" />
      <BangRongCoDinh :formProps="formProps.db_con" :optionProps="optionProps" v-show="formProps.dichvuvt_id == DichVuVienThong.ADSL" ref="bangrongcodinh" />
      <CoDinh :formProps="formProps.db_con" :optionProps="optionProps"  v-show="formProps.dichvuvt_id == DichVuVienThong.CO_DINH" ref="codinh" />
      <DichVuCNTT :formProps="formProps" :optionProps="optionProps"  
            v-show="formProps.dichvuvt_id == DichVuVienThong.DICHVU_CNTT || formProps.dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH|| formProps.dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT || formProps.dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU" ref="cntt" />
      <DiDong :formProps="formProps.db_con" :optionProps="optionProps"  v-show="formProps.dichvuvt_id == DichVuVienThong.DI_DONG" ref="didong" />
      <Gphone :formProps="formProps.db_con" :optionProps="optionProps"  v-show="formProps.dichvuvt_id == DichVuVienThong.GPHONE" ref="gphone" />
      <IMS :formProps="formProps.db_con" :optionProps="optionProps"  v-show="formProps.dichvuvt_id == DichVuVienThong.IMS" ref="ims" />
      <KenhThueRieng :formProps="formProps.db_con" :optionProps="optionProps"  v-show="formProps.dichvuvt_id == DichVuVienThong.TSL || formProps.dichvuvt_id == 25" ref="kenhthuerieng" />
      <MegaEyes :formProps="formProps.db_con" :optionProps="optionProps"  v-show="formProps.dichvuvt_id == DichVuVienThong.MEGA_EYES" ref="megaeyes" />
      <MegaWan :formProps="formProps.db_con" :optionProps="optionProps" :dichvuvt_id="formProps.dichvuvt_id" v-show="formProps.dichvuvt_id == DichVuVienThong.MEGAWAN ||formProps.dichvuvt_id == DichVuVienThong.METRONET || formProps.dichvuvt_id == 26" ref="megawan" />
      <!-- <Metronet :formProps="formProps.db_con" :optionProps="optionProps"  v-show="formProps.dichvuvt_id == DichVuVienThong.METRONET" ref="metronet" /> -->
    </div>
    <ModalChonDiaChi modalId="popupDiaChi_TB" :data="formProps.d_diachi_tb" @xacnhan="getDiaChi_tb"/>
    <ModalChonDiaChi modalId="popupDiaChi_LD" :data="formProps.d_diachi_ld" @xacnhan="getDiaChi_ld"/>
    <popupNhanVienDiaBan modalId="popupNVDB" :data="formProps.dataPopupNVDB" @form-close="closePopupNVDB"/>
    <DanhSachKhuyenMai modalId="popupDSKhuyenMai" :thuebao_id="formProps.thuebao_id" :isShow="isShowKhuyenMai" @closeModalKhuyenMai="closeModalKhuyenMai"/>    
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

import ModalChonDiaChi from '@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi'
import popupNhanVienDiaBan from '../popup/popupNhanVienDiaBan.vue'
import DanhSachKhuyenMai from '../popup/DanhSachKhuyenMai/index.vue'
import KDatePicker from "@/components/kylq_components/KDatePicker.vue";
import moment from "moment";
import BangRongCoDinh from "./BangRongCoDinh.vue";
import MacDinh from "./MacDinh.vue";
import CoDinh from "./CoDinh.vue";
import DichVuCNTT from "./DichVuCNTT.vue";
import DiDong from "./DiDong.vue";
import Gphone from "./Gphone.vue";
import IMS from "./IMS.vue";
import KenhThueRieng from "./KenhThueRieng.vue";
import MegaEyes from "./MegaEyes.vue";
import MegaWan from "./MegaWan.vue";
import Metronet from "./Metronet.vue";

import {
  DataManager,
  Query,
  ODataV4Adaptor,
  Predicate,
} from "@syncfusion/ej2-data";
export default {
  name: "ThongTinThueBao",
  props: ["formProps","titleProps","optionProps","visibles"],
  components: {
    KDatePicker,
    ModalChonDiaChi,
    popupNhanVienDiaBan,
    DanhSachKhuyenMai,
    BangRongCoDinh,MacDinh,CoDinh,Metronet,MegaWan,MegaEyes,KenhThueRieng,IMS,Gphone,DiDong,DichVuCNTT
  },
  data() {
    return { 
      DichVuVienThong: {
        TATCA: -1,
        CO_DINH: 1,
        DI_DONG: 2,
        ADSL: 4,
        DD_TRATRUOC: 5,
        EMAIL: 6,
        METRONET: 7,
        MEGAWAN: 8,
        TSL: 9,
        GPHONE: 10,
        IMS: 11,
        MEGA_EYES: 12,
        HOINGHI_TRUYENHINH: 13,
        ANTOAN_BAOMAT_TT: 14,
        DICHVU_CNTT: 15,
        TRUNGTAM_DULIEU: 16,
        HATANG_VIENTHONG: 19,

        IMS_DOITAC: 21,
        BRCD_DOITAC: 22,
        MGW_DOITAC: 23,
        TSL_DOITAC: 24
      },
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: "",
      popupComponentEvents: {
        "form-close": "popupClosed",
        acceptChangeCTV: "popupClosed",
        acceptChangeNGT: "popupClosed",
      },    
      isShowKhuyenMai:false,  
    };
  },
  methods: {
    popupChonKhuVuc() {
      if (this.$parent.kieugoi_id!=0){
        this.popupComponent = ()=>import('@/modules/contract/setup/InstallNewSubs/Popups/popupGanKhuVucQuanLyThueBao');
        this.popupComponentName = "popupChonKhuVuc";
        this.popupComponentData = 
          {
            diachi:this.$parent.form_TTTB.d_diachi_ld, 
            khuvuc_id: this.$parent.form_TTTB.khuvuc_cuoi_id, 
            hdtb_id: 0,
            thuebao_id: this.$parent.form_TTTB.thuebao_id, 
            donvi_id: this.$parent.form_TTTB.donvi_id
          }        
        this.Popup('popupComponents');
      }
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    popupClosed(){
      this.$bvModal.hide('popupComponent')
    },
    closePopupNVDB(){},
    getDiaChi_tb: function (data) {
        for (let key in data) {
          this.$set(this.$parent.form_TTTB.d_diachi_tb, key, data[key])
        }
      },
    getDiaChi_ld: function (data) {
        console.log(data)
        for (let key in data) {
          this.$set(this.$parent.form_TTTB.d_diachi_ld, key, data[key])
        }
    },
    hienThiKhuyenMai: function () {
      this.isShowKhuyenMai = !this.isShowKhuyenMai;
    },
    closeModalKhuyenMai: function () {
      this.isShowKhuyenMai = !this.isShowKhuyenMai;
    },
    getdsKM: function (data) {
        
    },
    selectLoaihd(){

    },
    selectDichvuvt(){      
      this.$parent.changeDVVT(this.$parent.form_TTTB.dichvuvt_id)
    },
    selectKieuld(){

    },
    selectMuccuoctb(){

    },
    selectThanhtoan(){

    },
    selectLoaihinh(){

    },
    selectDoituong(){

    },
    selectDonviql(){

    },
    selectTrangbi(){

    },
    TimThongTinKH(){              
        this.$parent.lay_ds_dbkh_theo_makh(this.$parent.form_TTTB.ma_tb)
    }
  },
};
</script>            
