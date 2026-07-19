<template>
  <div class="box-form">
    <div class="legend-title">Thông tin phiếu</div>
    <div class="row">
      <div class="col-sm-7 col-12">
        <div class="row">
          <div class="col-sm-8 col-12">
            <div class="info-row">
              <div class="key w80">Dịch vụ</div>
              <div class="value">
                <div class="select-custom">
                  <select v-model="dichvu_vt_selected" @change="changeDichVu" class="form-control">
                    <option v-for="item in dichvu_vt" :key="item.dichvuvt_id" :value="item.dichvuvt_id">{{item.ten_dvvt}}</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">Quy trình</div>
              <div class="value">
                <div class="select-custom">
                  <select v-model="quytrinh_selected" @change="changeQuyTrinh" class="form-control">
                    <option v-for="item in ds_quytrinh" :key="item.quytrinh_id" :value="item.quytrinh_id">{{item.quytrinh}}</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-8 col-12">
            <div class="info-row">
              <div class="key w80">Mã GD <k-required-marker/></div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn" @click="clickSearchContract" :class="{disabled_color:!enableBtnLayMaGD}" :disabled="!enableBtnLayMaGD">
                    <span class="-ap icon-more_horiz"></span>
                  </button>
                  <input type="text" @keyup.enter="onEnterMaGD($event)" :value="input_magd" @change="e=>input_magd=e.target.value" :readonly="!txtMaGDEnabled" class="form-control highlight bold" />
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">Kiểu yêu cầu</div>
              <div class="value">
                <div class="select-custom">
                  <select v-model="kieu_yc_selected" class="form-control">
                    <option v-for="item in ds_kieu_yc" :key="item.kieuld_id" :value="item.kieuld_id">{{item.ten_kieuld}}</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-8 col-12">
            <div class="info-row">
              <div class="key w80">Người GV</div>
              <div class="value">
                <div class="select-custom" ref="ds_nguoi_gv">
                    <select2 :settings="{ dropdownParent: $refs['ds_nguoi_gv'] }" ref="cboNguoiGV"
                        v-model="nguoi_gv_selected" class="select2"
                        :options="ds_nguoi_gv.map(e=> ({id: e.nhanvien_id, text: e.ten_nv}))">
                    </select2>
                </div>  

              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">Ngày GV</div>
              <div class="value w16 padt7">
                <div class="check-action">
                  <input type="checkbox" class="check" :disabled="!enabledNgayGV" v-model="checkBoxs.ngay_gv"/>
                  <span class="name"></span>
                </div>
              </div>
              <div class="value">
                <div class="input-icon-right">
                  <k-date-picker v-model="ngay_gv"
                    :disabled="!checkBoxs.ngay_gv"
                    type="datetime"
                    format="DD/MM/YYYY HH:mm:ss"/>
                    <!-- <ejs-datetimepicker class="form-control"
                      :enabled="checkBoxs.ngay_gv"
                      v-model="ngay_gv"
                      @change="changeNgayGV"
                      :format="dateFormat"/> -->
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w80">Account <k-required-marker/></div>
              <div class="value">
                <input type="text" :readonly="!txtMaTBEnabled" @keyup.enter="onEnterMaTB($event)" ref="inputMaTB" :value="input_account" @change="e=>input_account=e.target.value" class="form-control highlight bold" />
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w80">Mật khẩu <k-required-marker/></div>
              <div class="value">
                <input type="text" :readonly="!txtMatKhauEnabled" :value="input_matkhau" @change="e=>input_matkhau=e.target.value" class="form-control highlight bold" />
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">Ngày HT</div>
              <div class="value w16 padt7">
                <div class="check-action">
                  <input type="checkbox" class="check" :disabled="!enabledNgayHT" v-model="checkBoxs.ngay_ht" />
                  <span class="name"></span>
                </div>
              </div>
              <div class="value">
                <div class="input-icon-right">
                  <k-date-picker v-model="ngay_ht"
                    :disabled="!checkBoxs.ngay_ht"
                    type="datetime"
                    format="DD/MM/YYYY HH:mm:ss"/>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-8 col-12">
            <div class="info-row">
              <div class="key w80">Tốc độ mới</div>
              <div class="value">
                <ejs-dropdownlist 
                    :allowFiltering="true"
                    v-model="tocdomoi_selected"
                    :dataSource="ds_tocdo_adsl"
                    :enabled="enableSelect.tocdomoi"
                    :fields="{ text: 'THUONGHIEU', value: 'TOCDO_ID' }" />
                <!-- <div class="select-custom">
                  <select v-model="tocdomoi_selected" :disabled="!enableSelect.tocdomoi" class="form-control">
                    <option v-for="item in ds_tocdo_adsl" :key="item.TOCDO_ID" :value="item.TOCDO_ID">{{item.THUONGHIEU}}</option>
                  </select>
                </div> -->
              </div>
            </div>
            <div class="info-row">
              <div class="key w80">Tốc độ cũ</div>
              <div class="value">
                <ejs-dropdownlist 
                    :allowFiltering="true"
                    v-model="tocdocu_selected"
                    :dataSource="ds_tocdo_adsl"
                    :enabled="enableSelect.tocdocu"
                    :fields="{ text: 'THUONGHIEU', value: 'TOCDO_ID' }" />
                <!-- <div class="select-custom">
                  <select v-model="tocdocu_selected" :disabled="!enableSelect.tocdocu" class="form-control">
                    <option v-for="item in ds_tocdo_adsl" :key="item.TOCDO_ID" :value="item.TOCDO_ID">{{item.THUONGHIEU}}</option>
                  </select>
                </div> -->
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">Mức cước mới</div>
              <div class="value">
                <div class="select-custom">
                  <select v-model="muccuoc_moi_selected" :disabled="!enableSelect.muccuocmoi" class="form-control">
                    <option v-for="item in ds_muccuoc" :key="item.MUCCUOC_ID" :value="item.MUCCUOC_ID">{{item.TENMUC}}</option>
                  </select>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Mức cước cũ</div>
              <div class="value">
                <div class="select-custom">
                  <select v-model="muccuoc_cu_selected" :disabled="!enableSelect.muccuoccu" class="form-control">
                    <option v-for="item in ds_muccuoc" :key="item.MUCCUOC_ID" :value="item.MUCCUOC_ID">{{item.TENMUC}}</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="info-row">
          <div class="key w80">Tốc độ thực</div>
          <div class="value">
            <ejs-dropdownlist 
                    :allowFiltering="true"
                    v-model="tocdothuc_selected"
                    :dataSource="ds_tocdo_adsl"
                    :enabled="enableSelect.tocdothuc"
                    :fields="{ text: 'THUONGHIEU', value: 'TOCDO_ID' }" />
            <!-- <div class="select-custom">
              <select v-model="tocdothuc_selected" :disabled="!enableSelect.tocdothuc" class="form-control">
                    <option v-for="item in ds_tocdo_adsl" :key="item.TOCDO_ID" :value="item.TOCDO_ID">{{item.THUONGHIEU}}</option>
              </select>
            </div> -->
          </div>
        </div>
        <div class="row">
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w80">Bras</div>
              <div class="value">
                <div class="select-custom">
                  <select v-model="bras_selected" @change="changeBras($event)" :disabled="!enableSelect.bras" class="form-control">
                    <option v-for="item in ds_bras" :key="item.bras_id" :value="item.bras_id">{{item.tenbras}}</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="list-checks mart7">
              <div class="item">
                <div class="check-action">
                  <input type="checkbox" class="check"  :disabled="!checkGiuIP.enable" v-model="checkGiuIP.check" />
                  <span class="name">Giữ IP</span>
                </div>
              </div>
              <div class="item">
                <div class="check-action">
                  <input type="checkbox" class="check"  :disabled="!checkTuDong.enable" v-model="checkTuDong.check"/>
                  <span class="name">Tự động</span>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">IPv4 Lan</div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn" @click="clickIPv4Lan" :class="{disabled_color:!enableBtnIpV4Lan}" :disabled="!enableBtnIpV4Lan">
                    <span class="-ap icon-more_horiz"></span>
                  </button>
                  <input type="text" :class="{readonly_input_white: !txtDiaChiIPEnabled}" :readonly="!txtDiaChiIPEnabled" :value="input_ipv4_lan" @change="e=>input_ipv4_lan=e.target.value" class="form-control bold" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w80">Dslam</div>
              <div class="value">
                <div class="select-custom">
                  <select v-model="dslam_selected" :disabled="!enableSelect.dslam" class="form-control">
                    <option v-for="item in ds_dslam" :key="item.dslam_id" :value="item.dslam_id">{{item.tendslam}}</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w80">Port MDF</div>
              <div class="value">
                <input type="text" :readonly="!txtPortMDFEnabled" :value="input_port_mdf" @change="e=>input_port_mdf=e.target.value" class="red form-control fw6 bold" />
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">IPv4 Wan</div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn" @click="clickIPv4Wan" :class="{disabled_color:!enableBtnIpV4Wan}" :disabled="!enableBtnIpV4Wan">
                    <span class="-ap icon-more_horiz"></span>
                  </button>
                  <input type="text" :class="{readonly_input_white: !txtIPv4WanEnabled}" :readonly="!txtIPv4WanEnabled" :value="input_ipv4_wan" @change="e=>input_ipv4_wan=e.target.value" class="form-control bold" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-2 col-12">
             <div class="info-row">
              <div class="key w40">Rack</div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn" @click="clickChonPort" :class="{disabled_color:!enableBtnChonPort}" :disabled="!enableBtnChonPort">
                    <span class="-ap icon-check2"></span>
                  </button>
                  <input type="text" :readonly="!txtRackEnabled" :value="input_rank" @change="e=>input_rank=e.target.value" class="form-control bold red" />
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w40">Shelf</div>
              <div class="value">
                <input type="text" :readonly="!txtShelfEnabled" :value="input_sheft" @change="e=>input_sheft=e.target.value"  class="red form-control fw6 bold" />
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w80">Trạm T.Bị</div>
              <div class="value">
                <input type="text"  :value="input_tram_tb" @change="e=>input_tram_tb=e.target.value" class="red form-control fw6 bold" />
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">IPv6 Lan</div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn"  @click="clickIPv6Lan" :class="{disabled_color:!enableBtnIpV6Lan}" :disabled="!enableBtnIpV6Lan">
                    <span class="-ap icon-more_horiz"></span>
                  </button>
                  <input type="text" :class="{readonly_input_white: !txtIPv6LanEnabled}" :readonly="!txtIPv6LanEnabled" :value="input_ipv6_lan" @change="e=>input_ipv6_lan=e.target.value" class="form-control bold" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-2 col-12">
            <div class="info-row">
              <div class="key w80">Slot</div>
              <div class="value">
                <input type="text" :readonly="!txtSlotEnabled" :value="input_slot" @change="e=>input_slot=e.target.value" class="form-control bold" style="background-color: #ffffff !important;"/>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w40">Port</div>
              <div class="value">
                <input type="text" :readonly="!txtPortEnabled" :value="input_port" @change="e=>input_port=e.target.value" class="form-control fw6 bold" style="background-color: #ffffff !important;" />
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w80">System</div>
              <div class="value">
                <input type="text" :readonly="!txtSystemEnabled" :value="input_system" @change="e=>input_system=e.target.value" class="red form-control fw6 bold" />
              </div>
            </div>
          </div>
          <div class="col-sm-2 col-12">
            <div class="info-row">
              <div class="key w100">Vpi/Svlan</div>
              <div class="value">
                <input type="text" :readonly="!txtVPIADSLEnabled" :value="input_vpi" @change="e=>input_vpi=e.target.value" class="red form-control fw6 bold" />
              </div>
            </div>
          </div>
          <div class="col-sm-2 col-12">
            <div class="info-row">
              <div class="key w60">Vci/Cvlan</div>
              <div class="value">
                <input type="text" :readonly="!txtVCIADSLEnabled" :value="input_vci" @change="e=>input_vci=e.target.value" class="red form-control fw6 bold" />
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-2 col-12">
            <div class="info-row">
              <div class="key w80">Self</div>
              <div class="value">
                <input type="text" :readonly="!txtSelfid01Enabled" :value="input_self" @change="e=>input_self=e.target.value" class="form-control bold" />
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w40">OLT Slot</div>
              <div class="value w50">
                <input type="text" :readonly="!txtSelfid02Enabled" :value="input_olt_slot" @change="e=>input_olt_slot=e.target.value" class="form-control bold" />
              </div>
              <div class="key w40 padl5">OLT Port</div>
              <div class="value">
                <input type="text" :readonly="!txtSelfid03Enabled" :value="input_olt_port" @change="e=>input_olt_port=e.target.value" class="form-control bold" />
              </div>
            </div>
          </div>
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w80">ONUID</div>
              <div class="value">
                <input type="text" :readonly="!txtSelfid04Enabled" :value="input_onuid" @change="e=>input_onuid=e.target.value" class="form-control fw6 bold" />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-5 col-12">
        <div class="info-row">
          <div class="key w100">Tên thuê bao</div>
          <div class="value">
            <input type="text" :readonly="!txtTenThueBaoEnabled" :value="input_tentb" @change="e=>input_tentb=e.target.value" class="form-control bold" />
          </div>
        </div>
        <div class="info-row">
          <div class="key w100">Địa chỉ lắp đặt</div>
          <div class="value">
            <input type="text" :readonly="!txtDiaChiLDEnabled" :value="input_diachi_ld" @change="e=>input_diachi_ld=e.target.value" class="form-control bold" />
          </div>
        </div>
        <div class="info-row">
          <div class="key w100">Nội dung giao</div>
          <div class="value">
            <input type="text" :readonly="!txtNDGiaoEnabled" :value="input_noidung_giao" @change="e=>input_noidung_giao=e.target.value"  class="form-control" />
          </div>
        </div>
        <div class="info-row">
          <div class="key w100">Nội dung trả</div>
          <div class="value">
            <input type="text" :readonly="!txtThongTinTraEnabled" :value="input_noidung_tra" @change="e=>input_noidung_tra=e.target.value" class="form-control" />
          </div>
        </div>
        <div class="info-row">
          <div class="key w100">Ghi chú</div>
          <div class="value">
            <input type="text" :class="{readonly_input_white: !txtGhiChuEnabled}" :readonly="!txtGhiChuEnabled" :value="input_ghichu" @change="e=>input_ghichu=e.target.value" class="form-control" />
          </div>
        </div>
        <div class="info-row">
          <div class="key w100">Nội dung TH</div>
          <div class="value">
            <div class="input-more-button">
              <button class="btn" @click="capNhatNDTH" :class="{disabled_color:!enableBtnNDTH}" :disabled="!enableBtnNDTH">
                <span class="nc-icon-glyph ui-1_edit-76 f16"></span>
              </button>
              <input type="text" id="inputNDTH" ref="inputNDTH"  @blur="outFocusInput('inputNDTH')" :readonly="!txtNoiDungTHEnabled" :value="input_noidung_th" @change="e=>input_noidung_th=e.target.value" class="form-control" />
            </div>
          </div>
        </div>
        <div class="legend-title mart10">Danh sách nhân viên thực hiện</div>
        <KDataGrid
            :columns="columns"
            :dataSource="dataSources"
            :enable-paging-server="false"
            :allowPaging="false"
            :showFilter="false"
            :panelDataHeight="heightTableNV"
            :showColumnCheckbox="false"
            :enabledSelectFirstRow="true"/>
        <div v-if="isShowThongTinKT" class="form-control text mart5 pad10"  style="height: 80px; overflow-y: scroll;">
            <div v-html="thongsokt"/>
                <!-- Thông báo về thông số kỹ thuật ở danh bạ -->
            <div style="margin: 5px 0; text-align: center; font-weight: bold;">
                Thông số kỹ thuật ở danh bạ
            </div>
            <div v-html="thongsoktdanhba"/>
        </div>
      </div>
    </div>
    <!-- Modal -->
    <SearchContractModal ref="searchContractModal" @accept="acceptSearchContract" 
      :loai_hd_id="loai_hd" 
      :trangthai_hd="Number(tthd)"
      :donvi_id="0"
      :nhanvien_id="0"
      :donvi_dl_id="donvi_dl_id"
    />
    <DsThongSoKyThuatModal ref="dsThongSoKyThuatModal" :dataSources="ds_thongso_kythuat"/>
    <DsThongSoKyThuatModal_DanhBa ref="dsThongSoKyThuatModal_DanhBa" :dataSources="ds_thongso_kythuat_danhba"/>
    <ChonIPModal ref="chonIPModal" @callback="callbackChonIP" :kieu="loai_chon_id" :bras_id="brasId" :in_tocdo_id="in_tocdo_id"/>
    <!-- <ChonPortModal ref="chonPortModal"/> -->
    <ChonPort ref="ref-chon-port" @select="onSelectPort"/>

    <!-- <GiaoPhieuNhanVienModal ref="giaoPhieuNhanVienModal"
            :phieu_id="phieu_id"
            :huonggiao_id="0"
            :ngaygiao="ngay_gv"
            :nhanvien_giao_id="nguoi_gv_selected"
            :donvi_id="donvi_id"
            :ma_tb="ma_tb"
            @giaoviec_success="giaoviec_success"/> -->
    <GiaoPhieuNVModal ref="giaoPhieuNhanVienModal"
      :phieu_id="phieu_id"
      :huonggiao_id="0"
      :ngaygiao="ngay_gv"
      :nguoigv_id="nguoi_gv_selected"
      :donvi_id="donvi_id"
      :ma_tb="ma_tb"
      @giaoviec_success="giaoviec_success"
    />

    
  </div>
</template>
<script>
import moment from 'moment'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import DeclareServiceSpeedITSecureAPI from './DeclareServiceSpeedITSecureAPI'
import {DichVuVienThong, TRANGTHAI_DONGBO, DONGBO_TEST} from './ThamSo'
import DsThongSoKyThuatModal from './popups/DsThongSoKyThuatModal.vue'
import DsThongSoKyThuatModal_DanhBa from './popups/DsThongSoKyThuatModal_DanhBa.vue'
import ChonIPModal from './popups/ChonIP/ChonIPModal.vue'
import ChonPortModal from './popups/ChonPort/ChonPortModal.vue'
// import GiaoPhieuNhanVienModal from '../../../install/UpdateSurveyResult/popups/GiaoPhieuNhanVienModal.vue';
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import ChonPort from "@/modules/EXTS/QUANTRI/ThayDoiThongSoInternet/chon-port"
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import GiaoPhieuNVModal from './popups/GiaoPhieuNVModal.vue'
import { LoaiHopDong } from '@/const/enums'
export default {
  name: "ThongTinPhieu",
  props:{
    cty_newline: {
      type: Boolean,
      default: false
    },
    sizeDataSource:{
      type:Number,
      default:0
    }
  },
  components: {
      SearchContractModal,
      DsThongSoKyThuatModal,
      DsThongSoKyThuatModal_DanhBa,
      ChonIPModal,
      ChonPortModal,
      // GiaoPhieuNhanVienModal,
      ChonPort,
      KDatePicker,
      KRequiredMarker,
      KDataGrid,
      GiaoPhieuNVModal
  },
  data() {
    return {
        columns:[
            {
                fieldName: "ten_nv",
                headerText: "Tên nhân viên",
                allowFiltering: true,
                width:70
            },
            {
                fieldName: "so_dt",
                headerText: "Điện thoại",
                allowFiltering: true,
                width:60
            },
            {
                fieldName: "nhiemvu",
                headerText: "Nhiệm vụ",
                allowFiltering: true,
                width:60
            },
            {
                fieldName: "ghichu",
                headerText: "Ghi chú",
                allowFiltering: true,
                width:60
            }
        ],
        name_congnghe:'',
        dataSources:[],
        checkBoxs:{
            ngay_gv:false,
            ngay_ht:false
        },
        enabledNgayGV:true,
        enabledNgayHT:true,
        ngay_gv: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
        ngay_ht: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
        dateFormat: "dd/MM/yyyy HH:mm:ss",
        //DANG_THI_CONG = 4,
        //param tag
        tthd:9,
        //THAY_DOI_TOCDO_ADSL = 8
        loai_hd:8,
        //param tag
        dsloaihd_id:"1",
        //param tag, mặc định để sài UR2.6.042  Khai báo thay đổi tốc độ dịch vụ CNTT - ATBMTT-TTDL
        dsdichvuvt_id:"13,14,15,16",
        dichvu_vt:[],
        dichvu_vt_selected:null,
        input_magd:'',
        ds_nguoi_gv:[],
        nguoi_gv_selected:null,
        //txtMaTB.Text
        input_account:'',
        input_matkhau:'',

        ds_tocdo_adsl:[],
        tocdomoi_selected:null,
        tocdocu_selected:null,
        tocdothuc_selected:null,

        ds_quytrinh:[],
        quytrinh_selected:null,
        ds_kieu_yc:[],
        kieu_yc_selected:null,

        ds_muccuoc:[],
        muccuoc_moi_selected:null,
        muccuoc_cu_selected:null,

        ds_bras:[],
        bras_selected:null,
        ds_dslam:[],
        dslam_selected:null,
        checkGiuIP:{
          check:false,
          enable:true
        },
        checkTuDong:{
          check:false,
          enable:true
        },
        input_ipv4_lan:'',
        input_port_mdf:'',
        input_ipv4_wan:'',
        input_rank:'',
        input_sheft:'',
        input_tram_tb:'',
        input_ipv6_lan:'',
        input_slot:'',
        input_port:'',
        input_system:'',
        input_vpi:'',
        input_vci:'',
        input_self:'',
        input_olt_slot:'',
        input_olt_port:'',
        input_onuid:'',

        input_tentb:'',
        input_diachi_ld:'',
        input_noidung_giao:'',
        input_noidung_tra:'',
        input_ghichu:'',
        input_noidung_th:'',

        enableBtnIpV4Lan:true,
        enableBtnIpV4Wan:false,
        enableBtnIpV6Lan:false,
        // enableBtnIpV4Wan:true,
        // enableBtnIpV6Lan:true,

        enableBtnChonPort:true,

        enableSelect:{
          tocdomoi:false,
          tocdocu:false,
          tocdothuc:false,
          muccuocmoi:false,
          muccuoccu:false,
          bras:false,
          dslam:false
        },
        rowSelected:null,
        rowSelectedId:-1,
        ds_thaotac:[],
        ds_control:[],
        IS_USING_CABMAN_V2:false,
        ds_thongso_kythuat:[],
        ds_thongso_kythuat_danhba:[],
        hdtb_id:0,
        loai_chon_id:1,//1:ip tỉnh,2: ip4 wan, 3: ip6lan,
        in_tocdothuc_id:-1,
        bras_id:0,
        profile_id:0,
        in_tocdo_id:0,
        donvi_id:0,
        phieu_id:0,
        thuebao_id:0,
        tramtb_id:0,
        congnghe_id:0,
        loaitb_id:0,
        all_control:[],
        port_id:0,
        vci_vpi_id:0,
        enableBtnNDTH:true,
        enableBtnLayMaGD:true,
        //
        txtIPv6LanEnabled:false,
        txtIPv4WanEnabled:false,
        txtMaGDEnabled:true,
        txtMaTBEnabled:true,
        txtMatKhauEnabled:true,
        txtSelfid03Enabled:true,
        txtSelfid02Enabled:true,
        txtSelfid01Enabled:true,
        txtSelfid04Enabled:true,
        txtPortMDFEnabled:true,
        txtShelfEnabled:true,
        txtRackEnabled:true,
        txtSystemEnabled:true,
        txtVCIADSLEnabled:true,
        txtVPIADSLEnabled:true,
        txtSlotEnabled:false,
        txtPortEnabled:false,
        txtDiaChiIPEnabled:false,
        txtDiaChiLDEnabled:true,
        txtThongTinTraEnabled:true,
        txtTenThueBaoEnabled:true,
        txtGhiChuEnabled:false,
        txtNDGiaoEnabled:true,
        txtNoiDungTHEnabled:true,
        txtTongTienEnabled:true,
        thongsokt:'',
        thongsoktdanhba:'',
        donvi_dl_id:0,
        //cty_newline:false
    }

  },
  computed:{
    ma_tb(){
      return this.input_account.trim()
    },
    isShowThongTinKT(){
      if(this.IS_USING_CABMAN_V2&&this.thongsokt.trim()!=''){
        return true
      }
      return false
    },
    heightTableNV(){
      if(this.isShowThongTinKT){
        return '70px'
      }
      return '150px'
    },
    brasId(){
      return this.bras_id?Number(this.bras_id):0
    }
  },
  methods:{
    onEnterMaGD(event){
      if(this.input_magd.trim()==''){
        return 
      }
      this.$emit('onEnterMaTB', this.input_magd.trim())
    },
    acceptSearchContract(item){
      this.input_magd=item.ma_gd
      this.$emit('searchMaGD', item.ma_gd)
    },
    clickSearchContract(){
      this.$refs.searchContractModal.showModal()
    },
    clear(){
      this.input_magd=''
      this.input_account=''
      this.input_tentb=''
      this.input_diachi_ld=''
      this.$refs.inputMaTB.focus()
      this.checkTuDong.check=false
      this.checkTuDong.enable=false
      this.checkGiuIP.check=false
      this.checkGiuIP.enable=false
      this.enableBtnIpV4Lan=false
      this.enableBtnIpV4Wan=false
      this.enableBtnIpV6Lan=false
      this.nguoi_gv_selected=this.$root.token.getNhanVienID()
      this.checkBoxs.ngay_gv=false
      this.checkBoxs.ngay_ht=false
      this.ngay_gv=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')

      this.input_port=''
      this.input_rank=''
      this.input_sheft=''
      this.input_vci=''
      this.input_vpi=''
      this.input_port_mdf=''
      this.input_tram_tb=''
      this.input_slot=''
      this.input_system=''

      // txtSelfid01.Text
      this.input_self=''
      // txtSelfid02.Text
      this.input_olt_slot=''
      // txtSelfid03.Text
      this.input_olt_port=''
      // txtSelfid04.Text
      this.input_onuid=''

      this.dataSources=[]
      this.input_noidung_giao=''
      this.input_noidung_tra=''
    },
    async changeDichVu(){
      console.log('787 - changeDichVu')
      console.log(this.cty_newline)
      this.$emit('changeDichVu', this.dichvu_vt_selected)
      this.quytrinh_selected=null
      if (!this.cty_newline)
          if(this.dichvu_vt_selected!=null){
            this.ds_quytrinh=await this.lay_ds_quytrinh_v2({
              dsloaihd_id: this.dsloaihd_id,
              dichvuvt_id: this.dichvu_vt_selected,
              tthd_id: this.tthd,
              kieu: 0
            })
            if(this.ds_quytrinh.length>0){
              this.quytrinh_selected=this.ds_quytrinh[0].quytrinh_id
              await this.changeQuyTrinh()
            }else{
              this.$emit('clearDuLieu')
            }
          }
        else {
          if(this.dichvu_vt_selected!=null){
            this.ds_quytrinh=await this.lay_ds_quytrinh_newlife({
              vphanvung_id: null,
              vloaihd_id: LoaiHopDong.THAY_DOI_TOCDO_ADSL,
              vdichvuvt_id: DichVuVienThong.ADSL,
              vtthd_id: this.tthd  
            })
            if(this.ds_quytrinh.length>0){
              this.quytrinh_selected=this.ds_quytrinh[0].quytrinh_id
              await this.changeQuyTrinh()
            }else{
              this.$emit('clearDuLieu')
            }
          }
        }
      
    },
    async changeBras(event){
      if(this.bras_selected!=null){
        this.dslam_selected=null
        this.ds_dslam=await this.sp_ht_dslam_cbo(this.bras_selected, 2)
        if(this.ds_dslam.length>0){
          this.dslam_selected=this.ds_dslam[0].dslam_id
        }
      }
    },
    async changeQuyTrinh(){
      if(this.quytrinh_selected==null){
        return
      }
      let huonggiao=await this.lay_huonggiao_theo_quytrinh(this.quytrinh_selected,this.tthd)
      //init luồng
      await this.hienthi_giaodien((huonggiao.length>0&&huonggiao[0].luong_id!=null)?huonggiao[0].luong_id:0)
      //
      if(!this.phailam('GIAOVIEC')){
        this.checkBoxs.ngay_gv=true
        this.checkBoxs.ngay_ht=true

        this.ngay_gv=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
        this.ngay_ht=moment(new Date()).add(30, 'seconds').format('DD/MM/YYYY HH:mm:ss')

      }    
      await this.$emit('callbackHuongGiaoTheoQuyTrinh', huonggiao)
    },
    // changeNgayGV(data){
    //   this.ngay_gv=moment(new Date(data.value)).format('DD/MM/YYYY HH:mm:ss')
    // },
    // changeNgayHT(data){
    //   this.ngay_ht=moment(new Date(data.value)).format('DD/MM/YYYY HH:mm:ss')
    // },
    onEnterMaTB(event){
      if(this.input_account.trim()==''){
        return 
      }
      this.$emit('onEnterMaTB', this.input_account.trim())
    },
    // Sau check lại có api bổ sung tiếp
    async clickChonPort(){
      if(this.sizeDataSource==0){
        this.$toast.error('Không có dữ liệu để Cập nhật !')
        return
      }

      let modal = this.$refs["ref-chon-port"]

      var donvi_id=0

      //sp_chonport
      let result=await this.sp_chonport(this.hdtb_id)
      if(result.length>0){
        donvi_id=result[0].donvi_id!=null?Number(result[0].donvi_id):0
      }
      console.log('sp_chonport', result)

      let diachild_id=await this.fn_tt_diachi_tb(this.thuebao_id, "-1")
      console.log("Loại công nghê hiện tại :", this.name_congnghe)
      let list_congnghe =await this.lay_ds_congnghe()
      console.log("danh sách công nghệ:", list_congnghe)
      const object_congnghe = list_congnghe.find(obj => obj.congnghe === this.name_congnghe)
      let congnghe_id = object_congnghe? object_congnghe.congnghe_id : null

      modal.frmChonPort(donvi_id,this.tramtb_id,0,0,/*DichVuVienThong.ADSL*/5,this.loaitb_id,Number(diachild_id),'-1',this.hdtb_id,this.thuebao_id,null,this.tramtb_id,null,congnghe_id)
      modal.show()
    },
    async lay_ds_congnghe() {
      try {
          let response = await this.$root.context.get(`/web-quantri/chon-port/lay_ds_congnghe`)

          if (response.error_code === 'BSS-00000000' && response.data != null) {
              return response.data.length == 0 ? [] : [{ congnghe: " ", congnghe_id: 0 }].concat(response.data)
          }

          throw { response: { data: response } }
      } catch (err) {
          return []
      }
    },
    async onSelectPort(port){
      console.log('port', port)
      this.bras_selected=port.bras_id_vdc
      this.dslam_selected=port.dslam_id_vdc
      this.input_port=port.port!=null?port.port.toString():''
      this.input_port_mdf=port.port_mdf!=null?port.port_mdf.toString():''
      this.input_slot=port.slot!=null?port.slot.toString():''
      this.input_vpi=port.vpi!=null?port.vpi.toString():''
      this.input_vci=port.vci!=null?port.vci.toString():''
      this.input_system=port.system!=null?port.system.toString():''
      this.input_rank = port.rack!=null?port.rack.toString():''

      this.input_sheft=port.shelf!=null?port.shelf.toString():''

      this.input_self=port.selfid01!=null?port.selfid01.toString():''
      this.input_olt_slot=port.selfid02!=null?port.selfid02.toString():''
      this.input_olt_port=port.selfid03!=null?port.selfid03.toString():''
      this.input_onuid=port.selfid04!=null?port.selfid04.toString():''

      this.congnghe_id=port.congnghe_id!=null?port.congnghe_id:0
      this.port_id=port.port_id!=null?port.port_id:0
      this.vci_vpi_id=port.vci_vpi_id!=null?port.vci_vpi_id:0

      this.$emit('callPort',{
        port_id:this.port_id,
        vci_vpi_id:this.vci_vpi_id,
        congnghe_id:this.congnghe_id
      })
      this.$emit('capNhatPort')
    },
    capNhatNDTH(){
      this.$emit('capNhatNDTH',this.input_noidung_th)
    },
    outFocusInput(inputId){
        let element=document.getElementById(inputId)
        if(element){
            element.classList.remove('input_batbuoc')
        }
    },
    focusNDTH(){
      document.getElementById('inputNDTH').classList.add('input_batbuoc')
      this.$refs.inputNDTH.focus()
    },
    giaoPhieuNV(){
      if(this.nguoi_gv_selected==null||this.nguoi_gv_selected==0){
        this.$toast.error('Hãy nhập nhân viên giao việc!')
        return
      }
      if(!this.checkBoxs.ngay_gv){
        this.$toast.error('Hãy nhập ngày giao việc!')
        return
      }
      
      this.$refs.giaoPhieuNhanVienModal.showModal()
    },
    async giaoviec_success(){
      let ds_nhanvien=await this.lay_ds_nhanvien_theo_phieu(this.phieu_id, this.$root.token.getNhanVienID(),2)
      await this.hienthi_ds_nv(ds_nhanvien)
    },
    async clickIPv4Lan(){
      this.loai_chon_id=1

      if(DONGBO_TEST.TEST){
        return
      }
      this.profile_id=0
      let tocdothuc=this.tocdothuc_selected!=null?this.tocdothuc_selected.toString():'0'
      let s=await this.fn_tt_profile_id_tocdo_adsl_theo_tocdo_id(tocdothuc, "-1")
      if(s!="-1"){
        this.profile_id=Number(s)
      }
      this.bras_id = 0
      if(this.rowSelected!=null&&this.rowSelected.bras_id!=null&&this.rowSelected.bras_id.toString()!=''){
        this.bras_id=this.rowSelected.bras_id
      }

      //let ds=await this.lay_ip_tinh(6573, 29118388)
      let ds=await this.lay_ip_tinh(this.bras_id, this.profile_id)
      if(ds.length==0){
        this.input_ipv4_lan=''
        this.$toast.error('Không tìm thấy IP nào hợp lệ hoặc đã hết IP. Bạn hãy chọn Brasd khác !')
        return
      }

      if(this.in_tocdothuc_id!=-1){
        this.in_tocdo_id=this.in_tocdothuc_id
      }else{
        this.in_tocdo_id=this.tocdothuc_selected!=null?this.tocdothuc_selected:0
      }
      
      this.$refs.chonIPModal.showModal()
    },
    clickIPv4Wan(){
      this.loai_chon_id=2
      if(DONGBO_TEST.TEST){
        return
      }
      this.bras_id=this.tocdothuc_selected!=null?this.tocdothuc_selected:0

      if(this.in_tocdothuc_id!=-1){
        this.in_tocdo_id=this.in_tocdothuc_id
      }else{
        this.in_tocdo_id=this.tocdothuc_selected!=null?this.tocdothuc_selected:0
      }

      this.$refs.chonIPModal.showModal()
    },
    clickIPv6Lan(){
      this.loai_chon_id=3

      if(DONGBO_TEST.TEST){
        return
      }
      this.bras_id=this.tocdothuc_selected!=null?this.tocdothuc_selected:0

      if(this.in_tocdothuc_id!=-1){
        this.in_tocdo_id=this.in_tocdothuc_id
      }else{
        this.in_tocdo_id=this.tocdothuc_selected!=null?this.tocdothuc_selected:0
      }
      this.$refs.chonIPModal.showModal()
    },
    callbackChonIP(data){
      if(this.loai_chon_id==1){
        this.input_ipv4_lan=data.diachiIPv4LAN?data.diachiIPv4LAN.toString():''
      }else if(this.loai_chon_id==2){
        this.input_ipv4_wan=data.diachiIPv4WAN?data.diachiIPv4WAN.toString():''
      }else if(this.loai_chon_id==3){
        this.input_ipv6_lan=data.diachiIPv6LAN?data.diachiIPv6LAN.toString():''
      }
    },
    capNhatNDTH(){
      this.$emit('capNhatNDTH',this.input_noidung_th)
    },
    giaoPhieuNV(){
      if(this.nguoi_gv_selected==null||this.nguoi_gv_selected==0){
        this.$toast.error('Hãy nhập nhân viên giao việc!')
        return
      }
      if(!this.checkBoxs.ngay_gv){
        this.$toast.error('Hãy nhập ngày giao việc!')
        return
      }
      
      this.$refs.giaoPhieuNhanVienModal.showModal()
    },
    async giaoviec_success(){
      let ds_nhanvien=await this.lay_ds_nhanvien_theo_phieu(this.phieu_id, this.$root.token.getNhanVienID(),2)
      await this.hienthi_ds_nv(ds_nhanvien)
    },
    // async clickIPv4Lan(){
    //   this.loai_chon_id=1

    //   if(DONGBO_TEST.TEST){
    //     return
    //   }
    //   this.profile_id=0
    //   let tocdothuc=this.tocdothuc_selected!=null?this.tocdothuc_selected.toString():'0'
    //   let s=await this.fn_map_id({
    //     id_neo: "profile_id",
    //     table: "CSS.TOCDO_ADSL",
    //     dk:"where tocdo_id = "+tocdothuc
    //   }, "-1")
    //   if(s!="-1"){
    //     this.profile_id=Number(s)
    //   }
    //   this.bras_id = 0
    //   if(this.rowSelected!=null&&this.rowSelected.bras_id!=null&&this.rowSelected.bras_id.toString()!=''){
    //     this.bras_id=this.rowSelected.bras_id
    //   }

    //   //let ds=await this.lay_ip_tinh(6573, 29118388)
    //   let ds=await this.lay_ip_tinh(this.bras_id, this.profile_id)
    //   if(ds.length==0){
    //     this.input_ipv4_lan=''
    //     this.$toast.error('Không tìm thấy IP nào hợp lệ hoặc đã hết IP. Bạn hãy chọn Brasd khác !')
    //     return
    //   }

    //   if(this.in_tocdothuc_id!=-1){
    //     this.in_tocdo_id=this.in_tocdothuc_id
    //   }else{
    //     this.in_tocdo_id=this.tocdothuc_selected!=null?this.tocdothuc_selected:0
    //   }
      
    //   this.$refs.chonIPModal.showModal()
    // },
    clickIPv4Wan(){
      this.loai_chon_id=2
      if(DONGBO_TEST.TEST){
        return
      }
      this.bras_id=this.tocdothuc_selected!=null?this.tocdothuc_selected:0

      if(this.in_tocdothuc_id!=-1){
        this.in_tocdo_id=this.in_tocdothuc_id
      }else{
        this.in_tocdo_id=this.tocdothuc_selected!=null?this.tocdothuc_selected:0
      }

      this.$refs.chonIPModal.showModal()
    },
    clickIPv6Lan(){
      this.loai_chon_id=3

      if(DONGBO_TEST.TEST){
        return
      }
      this.bras_id=this.tocdothuc_selected!=null?this.tocdothuc_selected:0

      if(this.in_tocdothuc_id!=-1){
        this.in_tocdo_id=this.in_tocdothuc_id
      }else{
        this.in_tocdo_id=this.tocdothuc_selected!=null?this.tocdothuc_selected:0
      }
      this.$refs.chonIPModal.showModal()
    },
    callbackChonIP(data){
      if(this.loai_chon_id==1){
        this.input_ipv4_lan=data.diachiIPv4LAN
      }else if(this.loai_chon_id==2){
        this.input_ipv4_wan=data.diachiIPv4WAN
      }else if(this.loai_chon_id==3){
        this.input_ipv6_lan=data.diachiIPv6LAN
      }
    },
    changCheckGiuIP(){
      
      if(this.checkGiuIP.check){
        this.checkTuDong.check=false
        this.checkTuDong.enable=false
        this.input_ipv4_lan=''
        this.input_ipv4_wan=''
        this.input_ipv6_lan=''
        this.enableBtnIpV4Lan=false
        this.enableBtnIpV4Wan=false
        this.enableBtnIpV6Lan=false
      }else{
        this.enableBtnIpV4Lan=true
        this.enableBtnIpV4Wan=true
        this.enableBtnIpV6Lan=true
        this.checkTuDong.enable=true
      }
      
    },
    changeCheckTuDong(){
      if(this.checkTuDong.check){
        this.input_ipv4_lan=''
        this.input_ipv4_wan=''
        this.input_ipv6_lan=''
        this.enableBtnIpV4Lan=false
        this.enableBtnIpV4Wan=false
        this.enableBtnIpV6Lan=false
      }else{
        this.enableBtnIpV4Lan=true
        this.enableBtnIpV4Wan=true
        this.enableBtnIpV6Lan=true
      }
    },
    //hoàn thiện 1 phần sau check lại, bổ sung
    async hienthi_giaodien(luong_id){
      this.$emit('callbackVisibleActionTop',{
        action_id:'tsbtnKQ_DoKiem',
        value:false
      })
      this.$emit('callbackVisibleActionTop',{
        action_id:'tsbtnDuAn',
        value:false
      })
      this.$emit('callbackVisibleActionTop',{
        action_id:'tsbtnHoanCong',
        value:false
      })
      this.$emit('callbackVisibleActionTop',{
        action_id:'tsbtnHoanThienHS',
        value:false
      })
      this.$emit('callbackVisibleActionTop',{
        action_id:'tsbtnGiaoPhieu',
        value:false
      })
      this.$emit('callbackVisibleActionTop',{
        action_id:'tsbtnGiaoViec',
        value:false
      })
      this.$emit('callbackVisibleActionTop',{
        action_id:'tsbtnKichHoat',
        value:false
      })
      this.$emit('callbackStatusActionTop',{
        action_id:'tsbtnKichHoat',
        value:false
      })

      this.$emit('callbackVisibleActionTop',{
        action_id:'tsbtnSua_KenhThu',
        value:false
      })
      this.$emit('callbackVisibleActionTop',{
        action_id:'tsbtnCreatIssue',
        value:false
      })

      this.enableBtnIpV4Lan=false
      this.enableBtnIpV4Wan=false
      this.enableBtnIpV6Lan=false
      this.enableBtnChonPort=false


      this.ds_control=[]
      this.ds_thaotac=await this.lay_thaotac(luong_id)

      //các nút control
      //tsbtnNhapMoi(v:false)/btnLayTTMoi(v:true)/tsbtnChapNhan(Cập nhật, v:true)/tsbtnGiaoViec(v:true)
      //tsbtnKichHoat(v:true, e:false)/tsbtnHoanCong(v:true)/tsbtnGiaoPhieu(v:false, e:false)/btnPhieuGiaoSai(v:true, Trả phiếu)
      //tsbtnHoanThienHS(e:false)/tsbtnXuatFile(v:true)/toolStripButton1(Xuat phieu, v:true)/ tsbtnHen(v:true)/tsbtnThongTinKT(v:true)/
      //tsbtnVatTu(v:false)/tsbtnVatTuMoi(v:false)/tsbtnDuAn(v:true)/tsbtnKQ_DoKiem(v:true)/tsbtnSua_KenhThu(v:true)/tsbtnGiaoPhieu_Ton(v:true, Tồn phiếu)
      //tsbtnChuyenTo(v:true)/tsbtnNVKT(v:true, GánKV)/tsbtnCreatIssue(v:true, Gửi YC)


      for(let i=0;i<this.ds_thaotac.length;i++){
        let controls=this.all_control.filter(x=>x.thaotac_id==this.ds_thaotac[i].thaotac_id)
        this.ds_control=this.ds_control.concat(controls.map(x=>Object.assign(this.ds_thaotac[i],x)))
      }
      console.log('ds_control', this.ds_control)
      //Động toolbar
      this.$emit('loadControl', this.ds_control)
      for(let i=0;i<this.ds_control.length;i++){
        let control=this.ds_control[i]
        //check các button, input
        if(control.control_name=='btnChonPort'){
          this.enableBtnChonPort=control.enable==1?true:false
        }else if(control.control_name=='btnIPTinh'){
          this.enableBtnIpV4Lan=control.enable==1?true:false
        }else if(control.control_name=='btnIPTinhV4Wan'){
          this.enableBtnIpV4Wan=control.enable==1?true:false
        }else if(control.control_name=='btnIPTinhV6Lan'){
          this.enableBtnIpV6Lan=control.enable==1?true:false
        }else if(control.control_name=='btnNDTH'){
          this.enableBtnNDTH=control.enable==1?true:false
        }else if(control.control_name=='btnLayMaGD'){
          this.enableBtnLayMaGD=control.enable==1?true:false
        }else if(control.control_name=='txtIPv6Lan'){
          this.txtIPv6LanEnabled=control.enable==1?true:false
        }else if(control.control_name=='txtIPv4Wan'){
          this.txtIPv4WanEnabled=control.enable==1?true:false
        }else if(control.control_name=='txtMaGD'){
          this.txtMaGDEnabled=control.enable==1?true:false
        }else if(control.control_name=='txtMaTB'){
          this.txtMaTBEnabled=control.enable==1?true:false
        }else if(control.control_name=='txtMatKhau'){
          this.txtMatKhauEnabled=control.enable==1?true:false
        }else if(control.control_name=='txtSelfid03'){
          this.txtSelfid03Enabled=control.enable==1?true:false
        }else if(control.control_name=='txtSelfid02'){
          this.txtSelfid02Enabled=control.enable==1?true:false
        }else if(control.control_name=='txtSelfid01'){
          this.txtSelfid01Enabled=control.enable==1?true:false
        }else if(control.control_name=='txtSelfid04'){
          this.txtSelfid04Enabled=control.enable==1?true:false
        }else if(control.control_name=='txtPortMDF'){
          this.txtPortMDFEnabled=control.enable==1?true:false
        }else if(control.control_name=='txtShelf'){
          this.txtShelfEnabled=control.enable==1?true:false
        }else if(control.control_name=='txtRack'){
          this.txtRackEnabled=control.enable==1?true:false
        }else if(control.control_name=='txtSystem'){
          this.txtSystemEnabled=control.enable==1?true:false
        }else if(control.control_name=='txtVCIADSL'){
          this.txtVCIADSLEnabled=control.enable==1?true:false
        }else if(control.control_name=='txtVPIADSL'){
          this.txtVPIADSLEnabled=control.enable==1?true:false
        }else if(control.control_name=='txtSlot'){
          this.txtSlotEnabled=control.enable==1?true:false
        }else if(control.control_name=='txtPort'){
          this.txtPortEnabled=control.enable==1?true:false
        }else if(control.control_name=='txtDiaChiIP'){
          this.txtDiaChiIPEnabled=control.enable==1?true:false
        }else if(control.control_name=='txtDiaChiLD'){
          this.txtDiaChiLDEnabled=control.enable==1?true:false
        }else if(control.control_name=='txtThongTinTra'){
          this.txtThongTinTraEnabled=control.enable==1?true:false
        }else if(control.control_name=='txtTenThueBao'){
          this.txtTenThueBaoEnabled=control.enable==1?true:false
        }else if(control.control_name=='txtGhiChu'){
          this.txtGhiChuEnabled=control.enable==1?true:false
        }else if(control.control_name=='txtNDGiao'){
          this.txtNDGiaoEnabled=control.enable==1?true:false
        }else if(control.control_name=='txtNoiDungTH'){
          this.txtNoiDungTHEnabled=control.enable==1?true:false
        }else if(control.control_name=='txtTongTien'){
          this.txtTongTienEnabled=control.enable==1?true:false
        }
        
      }

    },
    phailam(code){
      const index=this.ds_thaotac.findIndex(x=>x.enable==1&&x.code==code)
      return index>-1
    },

    async lay_ds_dichvu_vt(dsdichvuvt_id){
      try{
          this.loading(true)
          let response = await DeclareServiceSpeedITSecureAPI.sp_ht_dichvuvt_combobox(this.axios, dsdichvuvt_id)
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
    async lay_ds_tocdo_adsl(){
      try{
          this.loading(true)
          this.ds_tocdo_adsl=[]
          let response = await DeclareServiceSpeedITSecureAPI.lay_ds_tocdo_adsl(this.axios)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            this.ds_tocdo_adsl=response.data.data
            if(this.ds_tocdo_adsl.length>0){
              this.tocdomoi_selected=this.ds_tocdo_adsl[0].tocdo_id
              this.tocdocu_selected=this.ds_tocdo_adsl[0].tocdo_id
              this.tocdothuc_selected=this.ds_tocdo_adsl[0].tocdo_id
            }
          }else{
              this.ds_tocdo_adsl=[]
          }
      }catch(e){
        if(e.data&&e.data.message){
          this.$toast.error(e.data.message)
        }else if(e.response&&e.response.data&&e.response.data.message){
          this.$toast.error(e.response.data.message)
        }else{
          this.$toast.error('Không load được danh sách tốc độ ADSL')
        }
      }finally{
        this.loading(false)
      }
    },
    async lay_ds_tocdo_adsl_vs_muccuoc(nguoidung_id){
      try{
          this.loading(true)
          let response = await DeclareServiceSpeedITSecureAPI.lay_ds_tocdo_adsl_vs_muccuoc(this.axios, nguoidung_id)
          this.loading(false)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            if(response.data.data.returning==1){
              let result=JSON.parse(response.data.data.p_js_returnds)
              if(result&&result.ERROR_CODE==1){
                return {
                  ds_tocdo:result.DS_TOCDO_ADSL,
                  ds_muccuoc:result.DS_MUCCUOC
                }
              }else{
                return null
              }
            }else{
              return null
            }
          }else{
            return null
          }
      }catch(e){
        return null
      }
    },
    async lay_ds_bras(){
      try{
          this.loading(true)
          this.ds_bras=[]
          let response = await DeclareServiceSpeedITSecureAPI.lay_ds_bras(this.axios)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            this.ds_bras=response.data.data
            if(this.ds_bras.length>0){
              this.bras_selected=this.ds_bras[0].bras_id
            }
          }else{
              this.ds_bras=[]
          }
      }catch(e){
        if(e.data&&e.data.message){
          this.$toast.error(e.data.message)
        }else if(e.response&&e.response.data&&e.response.data.message){
          this.$toast.error(e.response.data.message)
        }else{
          this.$toast.error('Không load được danh sách Bras')
        }
      }finally{
        this.loading(false)
      }
    },
    async lay_ds_muccuoc(){
      try{
          this.loading(true)
          this.ds_muccuoc=[]
          let response = await DeclareServiceSpeedITSecureAPI.lay_ds_muccuoc(this.axios)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            this.ds_muccuoc=response.data.data
            if(this.ds_muccuoc.length>0){
              //this.bras_selected=this.ds_bras[0].bras_id
              this.muccuoc_cu_selected=this.ds_muccuoc[0].muccuoc_id
              this.muccuoc_moi_selected=this.ds_muccuoc[0].muccuoc_id
            }
          }else{
              this.ds_muccuoc=[]
          }
      }catch(e){
        if(e.data&&e.data.message){
          this.$toast.error(e.data.message)
        }else if(e.response&&e.response.data&&e.response.data.message){
          this.$toast.error(e.response.data.message)
        }else{
          this.$toast.error('Không load được danh sách mức cước')
        }
      }finally{
        this.loading(false)
      }
    },
    async lay_ds_nhanvien_thuchien_theo_dv(donvi_id){
      try{
          this.loading(true)
          this.ds_nguoi_gv=[]
          let response = await DeclareServiceSpeedITSecureAPI.lay_ds_nhanvien_thuchien_theo_dv(this.axios, donvi_id)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            this.ds_nguoi_gv=response.data.data
          }else{
            this.ds_nguoi_gv=[]
          }
      }catch(e){
        if(e.data&&e.data.message){
          this.$toast.error(e.data.message)
        }else if(e.response&&e.response.data&&e.response.data.message){
          this.$toast.error(e.response.data.message)
        }else{
          this.$toast.error('Không load được danh sách nhân viên')
        }
      }finally{
        this.loading(false)
      }
    },
    async lay_ds_quytrinh_v2(data){
      try{
          this.loading(true)
          let response = await DeclareServiceSpeedITSecureAPI.lay_ds_quytrinh_v2(this.axios, data)
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
    async lay_ds_quytrinh_newlife(data){
      try{
          this.loading(true)
          let response = await DeclareServiceSpeedITSecureAPI.lay_ds_quytrinh_newlife(this.axios, data)
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
    async lay_huonggiao_theo_quytrinh(quytrinh_id, tthd_id){
      try{
          this.loading(true)
          let response = await DeclareServiceSpeedITSecureAPI.lay_huonggiao_theo_quytrinh(this.axios, quytrinh_id, tthd_id)
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
    async sp_ht_dslam_cbo(bras_id, kieu_id){
      try{
          this.loading(true)
          let response = await DeclareServiceSpeedITSecureAPI.sp_ht_dslam_cbo(this.axios, bras_id, kieu_id)
          this.loading(false)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            return response.data.data
          }else{
            return []
          }
      }catch(e){
        this.loading(false)
        if(e.data&&e.data.message){
          this.$toast.error(e.data.message)
        }else if(e.response&&e.response.data&&e.response.data.message){
          this.$toast.error(e.response.data.message)
        }else{
          this.$toast.error('Không load được danh sách Dslam')
        }
        return []
      }
    },
    async ht_phieu_tra(phieu_id){
      try{
        this.loading(true)
        let response = await DeclareServiceSpeedITSecureAPI.ht_phieu_tra(this.axios, phieu_id)
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
    async lay_tram_tb(tramtb_id){
      try{
        this.loading(true)
        let response = await DeclareServiceSpeedITSecureAPI.lay_tram_tb(this.axios, tramtb_id)
        this.loading(false)
        if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        }else{
          return ''
        }
      }catch(e){
        this.loading(false)
        return ''
      }
    },
    async lay_ds_hopdong_thuebao_adsl(hdtbId){
      try{
        this.loading(true)
        let response = await DeclareServiceSpeedITSecureAPI.lay_ds_hopdong_thuebao_adsl(this.axios, hdtbId)
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
    async lay_ds_kieu_ld( loaihd_id, loaitb_id){
      try{
        this.loading(true)
        let response = await DeclareServiceSpeedITSecureAPI.lay_ds_kieu_ld(this.axios, loaihd_id, loaitb_id)
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
    async lay_ds_nhanvien_theo_phieu(phieu_id, nhanvien_id, kieu_id){
      try{
        this.loading(true)
        let response = await DeclareServiceSpeedITSecureAPI.lay_ds_nhanvien_theo_phieu(this.axios, phieu_id, nhanvien_id, kieu_id)
        this.loading(false)
        if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data.map((x, index)=>Object.assign(x,{id:index+1}))
        }else{
          return []
        }
      }catch(e){
        this.loading(false)
        return []
      }
    },
    async lay_thaotac(luong_id){
      try{
        this.loading(true)
        let response = await DeclareServiceSpeedITSecureAPI.lay_thaotac(this.axios, luong_id)
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
    async lay_all_thaotac_control(){
      try{
        this.loading(true)
        let response = await DeclareServiceSpeedITSecureAPI.lay_all_thaotac_control(this.axios)
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
    async lay_tocdo_adsl_by_id(tocdo_id){
      try{
        this.loading(true)
        let response = await DeclareServiceSpeedITSecureAPI.lay_tocdo_adsl_by_id(this.axios, tocdo_id)
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
    async lay_thamso_cabman_v2(){
      try{
        this.loading(true)
        let response = await DeclareServiceSpeedITSecureAPI.lay_thamso_cabman_v2(this.axios)
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
    async lay_ds_thongtin_kythuat_hdtb(hdtb_id){
      try{
        this.loading(true)
        let response = await DeclareServiceSpeedITSecureAPI.lay_ds_thongtin_kythuat_hdtb(this.axios, hdtb_id)
        this.loading(false)
        if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          let list_thongtin_kythuat = response.data.data.find(obj => obj.ten_truong === "Công nghệ")
          this.name_congnghe = list_thongtin_kythuat? list_thongtin_kythuat.giatri : null
          return response.data.data
        }else{
          return []
        }
      }catch(e){
        this.loading(false)
        return []
      }
    },
    async lay_ip_tinh(brasId, serviceId){
      try{
        this.loading(true)
        let response=await DeclareServiceSpeedITSecureAPI.lay_ip_tinh(this.axios, brasId, serviceId)
        this.loading(false)
        if(response&&response.data&&response.data.errorCode!=undefined&&response.data.errorCode==0) {
          return response.data.data
        }else{
          return []
        }
      }catch(e){
        this.loading(false)
        return []
      }
    },
    async fn_tt_diachi_tb(data, defaultValue){
      try{
        this.loading(true)
        let response=await DeclareServiceSpeedITSecureAPI.fn_tt_diachi_tb(this.axios, data)
        this.loading(false)
        if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
          return response.data.data
        }else{
          return defaultValue
        }
                
      }catch(e){
        this.loading(false)
        return defaultValue
      }
    },
    async fn_tt_profile_id_tocdo_adsl_theo_tocdo_id(data, defaultValue){
      try{
        this.loading(true)
        let response=await DeclareServiceSpeedITSecureAPI.fn_tt_profile_id_tocdo_adsl_theo_tocdo_id(this.axios, data)
        this.loading(false)
        if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
          return response.data.data
        }else{
          return defaultValue
        }
                
      }catch(e){
        this.loading(false)
        return defaultValue
      }
    },

    async sp_chonport(hdtb_id){
      try{
        this.loading(true)
        let response = await DeclareServiceSpeedITSecureAPI.sp_chonport(this.axios, hdtb_id)
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
    async onSelectedRow(item){
      await this.clearDuLieu()
      this.rowSelected=item
      this.hdtb_id=item.hdtb_id
      this.phieu_id=item.phieu_id
      this.loaitb_id=item.loaitb_id
      //this.enableBtnIpV4Lan=true
      //init view
      let ds_tra=await this.ht_phieu_tra(item.phieu_id)
      if(ds_tra.length>0){
        this.input_noidung_tra=ds_tra[0].nd_tra!=null?ds_tra[0].nd_tra.toString():''
      }else{
        this.input_noidung_tra=''
      }

      this.input_magd=item.ma_gd?item.ma_gd.toString():''
      this.input_account=item.ma_tb?item.ma_tb.toString():''
      this.input_tentb=item.ten_tb?item.ten_tb.toString():''
      this.input_diachi_ld=item.diachi_ld?item.diachi_ld.toString():''

      this.input_noidung_giao=item.nd_giao?item.nd_giao.toString():''

      this.input_noidung_th=item.nd_thuchien?item.nd_thuchien.toString():''
      this.input_ghichu=item.ghichu?item.ghichu.toString():''

      this.thuebao_id=item.thuebao_id!=null?Number(item.thuebao_id):0

      if(item.tramtb_id!=null&&item.tramtb_id.toString()!=''){
        this.tramtb_id=Number(item.tramtb_id)
        let tramtb=await this.lay_tram_tb(item.tramtb_id)
        if(tramtb.length>0){
          this.input_tram_tb=tramtb
        }else{
          this.input_tram_tb=''
        }
      }else{
        this.input_tram_tb=''
        this.tramtb_id=0
      }
      //
      let hd_adsl=await this.lay_ds_hopdong_thuebao_adsl(item.hdtb_id)
      this.bras_selected=null
      if(hd_adsl.length>0){
        this.bras_selected=hd_adsl[0].bras_id
        if(this.bras_selected!=null){
          this.dslam_selected=null
          this.ds_dslam=await this.sp_ht_dslam_cbo(this.bras_selected, 2)
          if(this.ds_dslam.length>0){
            this.dslam_selected=this.ds_dslam[0].dslam_id
          }
        }
        this.dslam_selected=hd_adsl[0].dslam_id

        this.input_ipv4_lan=hd_adsl[0].ip?hd_adsl[0].ip.toString():''
        this.input_ipv4_wan=hd_adsl[0].ipv4_wan?hd_adsl[0].ipv4_wan.toString():''
        this.input_ipv6_lan=hd_adsl[0].ipv6_lan?hd_adsl[0].ipv4_wan.toString():''
        this.input_port=hd_adsl[0].port!=null?hd_adsl[0].port.toString():''
        this.input_port_mdf=hd_adsl[0].port_mdf!=null?hd_adsl[0].port_mdf.toString():''
        this.input_slot=hd_adsl[0].slot!=null?hd_adsl[0].slot.toString():''
        this.input_vci=hd_adsl[0].vci!=null?hd_adsl[0].vci.toString():''
        this.input_vpi=hd_adsl[0].vpi!=null?hd_adsl[0].vpi.toString():''

        this.input_system=hd_adsl[0].system!=null?hd_adsl[0].system.toString():''

        if(hd_adsl[0].port_id!=null&&hd_adsl[0].port_id!=''){
          this.port_id=Number(hd_adsl[0].port_id)
          this.vci_vpi_id=Number(hd_adsl[0].vci_vpi_id)
          //calback form
          this.$emit('callPort',{
            port_id:Number(hd_adsl[0].port_id),
            vci_vpi_id:Number(hd_adsl[0].vci_vpi_id)
          })
        }

        this.input_rank=hd_adsl[0].rack!=null?hd_adsl[0].rack.toString():''
        this.input_sheft=hd_adsl[0].shelf!=null?hd_adsl[0].shelf.toString():''
        this.input_matkhau=hd_adsl[0].password!=null?hd_adsl[0].password.toString():''

        // txtSelfid01.Text = hd_adsl.Tables[0].Rows[0]["selfid01"].ToString();
        this.input_self=hd_adsl[0].selfid01!=null?hd_adsl[0].selfid01.toString():''
        // txtSelfid02.Text = hd_adsl.Tables[0].Rows[0]["selfid02"].ToString();
        this.input_olt_slot=hd_adsl[0].selfid02!=null?hd_adsl[0].selfid02.toString():''
        // txtSelfid03.Text = hd_adsl.Tables[0].Rows[0]["selfid03"].ToString();
        this.input_olt_port=hd_adsl[0].selfid03!=null?hd_adsl[0].selfid03.toString():''
        // txtSelfid04.Text = hd_adsl.Tables[0].Rows[0]["selfid04"].ToString();
        this.input_onuid=hd_adsl[0].selfid04!=null?hd_adsl[0].selfid04.toString():''

      }

      this.tocdomoi_selected=item.tocdo_id!=null?Number(item.tocdo_id):null
      this.tocdocu_selected=item.tocdo_cu_id!=null?Number(item.tocdo_cu_id):null
      this.tocdothuc_selected=item.tocdothuc_id!=null?Number(item.tocdothuc_id):null

      if(item.tocdothuc_id!=null&&item.tocdothuc_id.toString()!=''){
        this.in_tocdothuc_id=Number(item.tocdothuc_id)
      }else{
        this.in_tocdothuc_id=-1
      }

      // this.muccuoc_moi_selected=null
      // this.muccuoc_cu_selected=null
      if(item.dichvuvt_id!=DichVuVienThong.ANTOAN_BAOMAT_TT&&item.dichvuvt_id!=DichVuVienThong.DICHVU_CNTT
        &&item.dichvuvt_id!=DichVuVienThong.HOINGHI_TRUYENHINH&&item.dichvuvt_id!=DichVuVienThong.TRUNGTAM_DULIEU){
          this.muccuoc_moi_selected=item.muccuoc_id!=null?Number(item.muccuoc_id):null
          this.muccuoc_cu_selected=item.muccuoc_cu_id!=null?Number(item.muccuoc_cu_id):null
      }
      //tchopdong.LAY_DS_KIEU_LD(LoaiHopDong.THAY_DOI_TOCDO_ADSL, loaitb_id).Tables[0];
      let ds_kieu_ld=await this.lay_ds_kieu_ld(8, item.loaitb_id)
      this.ds_kieu_yc=ds_kieu_ld
      this.kieu_yc_selected=item.kieuld_id

      let ds_nhanvien=await this.lay_ds_nhanvien_theo_phieu(item.phieu_id, this.$root.token.getNhanVienID(),2)
      await this.hienthi_ds_nv(ds_nhanvien)

      //Nếu phải kích hoạt tổng đài
      if(this.phailam('KICHHOAT_TD')){
        console.log('onSelectedRow KICHHOAT_TD')
        if(item.ngay_ht_gp!=null&&item.ngay_ht_gp!=''){
          //  dtpNgayBG.Enabled = true;
          //                   dtpNgayBG.Value = Convert.ToDateTime(dtList.Rows[nIndex]["ngay_ht_gp"]);
          //                   chkNgayBG.Checked = true;
          this.checkBoxs.ngay_ht=true
          this.ngay_ht=moment(item.ngay_ht_gp, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')

          let tocdo_adsl=await this.lay_tocdo_adsl_by_id(this.tocdomoi_selected)
          console.log('onSelectedRow lay_tocdo_adsl_by_id', tocdo_adsl)
          if(tocdo_adsl.length>0&&tocdo_adsl[0].iptinh!=null&&tocdo_adsl[0].iptinh==1){
            this.enableBtnIpV4Lan=true
            this.enableBtnIpV4Wan=true
            this.enableBtnIpV6Lan=true
            this.checkGiuIP.enable=true
            this.checkTuDong.enable=true
          }else{
            this.checkGiuIP.enable=false
            this.checkTuDong.enable=false
            this.checkGiuIP.check=false
            this.checkTuDong.check=false
            this.enableBtnIpV4Lan=false
            this.enableBtnIpV4Wan=false
            this.enableBtnIpV6Lan=false
          }
          //TRANGTHAI_DONGBO.DONGBO_CM
          if(item.status!=null&&item.status!=''&&item.status==TRANGTHAI_DONGBO.DONGBO_CM.toString()){
            this.checkTuDong.enable=false
            this.checkGiuIP.enable=false
            this.enableBtnIpV4Lan=false
            this.enableBtnIpV4Wan=false
            this.enableBtnIpV6Lan=false
            this.checkGiuIP.check=false
            this.checkTuDong.check=false

            if(this.phailam('GIAOPHIEU')){
              this.$emit('callbackStatusActionTop',{
                action_id:'tsbtnGiaoPhieu',
                value:true
              })
              this.$emit('callbackStatusActionTop',{
                action_id:'tsbtnKichHoat',
                value:false
              })
            }else{
              this.$emit('callbackStatusActionTop',{
                action_id:'tsbtnHoanCong',
                value:true
              })
              this.$emit('callbackStatusActionTop',{
                action_id:'tsbtnKichHoat',
                value:false
              })
            }
          }else{
            this.$emit('callbackStatusActionTop',{
              action_id:'tsbtnGiaoPhieu',
              value:false
            })
            this.$emit('callbackStatusActionTop',{
              action_id:'tsbtnKichHoat',
              value:true
            })
            this.$emit('callbackStatusActionTop',{
              action_id:'tsbtnHoanCong',
              value:false
            })

            this.enableBtnIpV4Lan=true
            this.enableBtnIpV4Wan=true
            this.enableBtnIpV6Lan=true
          }
        }else{
          if(!this.phailam('GIAOVIEC')){
            this.checkBoxs.ngay_ht=true
          }else{
            this.checkBoxs.ngay_ht=false
          }
          this.ngay_ht=moment(new Date()).add(30, 'seconds').format('DD/MM/YYYY HH:mm:ss')
          this.$emit('callbackStatusActionTop',{
            action_id:'tsbtnGiaoPhieu',
            value:false
          })
          this.$emit('callbackStatusActionTop',{
            action_id:'tsbtnKichHoat',
            value:false
          })
          this.$emit('callbackStatusActionTop',{
            action_id:'tsbtnHoanCong',
            value:false
          })
        }

      }else{
        console.log('onSelectedRow not KICHHOAT_TD')
        //Nếu không phải kích hoạt
        //Nếu phải giao phiếu
        if(this.phailam('GIAOPHIEU')){
          if(item.ngay_ht_gp!=null&&item.ngay_ht_gp!=''){
            this.checkBoxs.ngay_ht=true
            this.ngay_ht=moment(item.ngay_ht_gp, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')

            this.$emit('callbackStatusActionTop',{
              action_id:'tsbtnGiaoPhieu',
              value:true
            })
            this.$emit('callbackStatusActionTop',{
              action_id:'tsbtnKichHoat',
              value:false
            })
            this.$emit('callbackStatusActionTop',{
              action_id:'tsbtnHoanCong',
              value:false
            })
          }else{
            if(!this.phailam('GIAOVIEC')){
              this.checkBoxs.ngay_ht=true
            }else{
              this.checkBoxs.ngay_ht=false
            }
            this.ngay_ht=moment(new Date()).add(30, 'seconds').format('DD/MM/YYYY HH:mm:ss')
            this.$emit('callbackStatusActionTop',{
              action_id:'tsbtnGiaoPhieu',
              value:false
            })
            this.$emit('callbackStatusActionTop',{
              action_id:'tsbtnKichHoat',
              value:false
            })
            this.$emit('callbackStatusActionTop',{
              action_id:'tsbtnHoanCong',
              value:false
            })

          }
        }else{
          //Nếu không phải giao phiếu
          //Nếu phải kích hoạt tổng đài
          if(this.phailam('HOANCONG')){
            if(item.ngay_ht_gp!=null&&item.ngay_ht_gp!=''){
              this.checkBoxs.ngay_ht=true
              this.ngay_ht=moment(item.ngay_ht_gp, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
              this.$emit('callbackStatusActionTop',{
                action_id:'tsbtnHoanCong',
                value:true
              })
            }else{
              if(!this.phailam('GIAOVIEC')){
                this.checkBoxs.ngay_ht=true
              }else{
                this.checkBoxs.ngay_ht=false
  
              }
              this.ngay_ht=moment(new Date()).add(30, 'seconds').format('DD/MM/YYYY HH:mm:ss')
              this.$emit('callbackStatusActionTop',{
                action_id:'tsbtnHoanCong',
                value:false
              })
            }
          }

        }

      }
      //trạng thái chọn port
      if(item.status!=null&&item.status!=''&&item.status==TRANGTHAI_DONGBO.DONGBO_CM.toString()){
        this.enableBtnChonPort=false
      }else{
        if(this.phailam('CAPNHAT_PORT')){
          this.enableBtnChonPort=true
        }
      }
      //
      if(this.phailam('HOANTHIENHOSO')){
        if(item.ngay_ht_gp!=null&&item.ngay_ht_gp!=''){
          this.checkBoxs.ngay_ht=true
          this.ngay_ht=moment(item.ngay_ht_gp, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
          this.$emit('callbackStatusActionTop',{
            action_id:'tsbtnHoanThienHS',
            value:true
          })
        }else{
          if(!this.phailam('GIAOVIEC')){
            this.checkBoxs.ngay_ht=true
          }else{
            this.checkBoxs.ngay_ht=false
          }
          this.ngay_ht=moment(new Date()).add(30, 'seconds').format('DD/MM/YYYY HH:mm:ss')
          this.$emit('callbackStatusActionTop',{
            action_id:'tsbtnHoanThienHS',
            value:false
          })

        }
        this.enableBtnIpV4Lan=false
        this.enableBtnIpV4Wan=false
        this.enableBtnIpV6Lan=false
        this.checkTuDong.enable=false
        this.checkGiuIP.enable=false
      }
      //
      if(!this.phailam('GIAOVIEC')){
        this.checkBoxs.ngay_ht=true
        this.enabledNgayHT=false
        this.checkBoxs.ngay_gv=true
        this.enabledNgayGV=false
      }

      //
      //HIEN_THONGSO_KYTHUAT();
      await this.hienthi_thongso_kythuat()
      // HIENTHITHONGSOKYTHUATDANHBA
      await this.hienthi_thongso_kythuat_danhba()

    },
    async hienthi_thongso_kythuat(){
      //nếu ko dùng cabman2.0 thì không hiện
      if(!this.IS_USING_CABMAN_V2){
        this.thongsokt=''
        return
      }else{
        this.thongsokt=''
        this.ds_thongso_kythuat=await this.lay_ds_thongtin_kythuat_hdtb(this.hdtb_id)
        this.ds_thongso_kythuat.map(x=>{
            return Object.assign(x, {giatri:x.giatri!=null?x.giatri:'', ten_truong:x.ten_truong!=null?x.ten_truong:''})
        }).forEach((item)=>{
            this.thongsokt+=item.format_tt.replace('{0}', item.ten_truong)
            this.thongsokt+=item.format_gt.replace('{0}', item.giatri)
        })
        //5240522
        //Hard
        //this.ds_thongso_kythuat=await this.lay_ds_thongtin_kythuat_hdtb(5240522)
        // if(this.ds_thongso_kythuat.length>0){
        //   this.$refs.dsThongSoKyThuatModal.showModal()
        // }
      }
      console.log('hienthi_thongso_kythuat', this.thongsokt)
    },
    async hienthi_thongso_kythuat_danhba() {
      this.thongsoktdanhba = '';
      let response = await this.$root.context.post('/web-cabman/danhmuc/toadothuebao/thongsokythuat', {
          db_id: this.thuebao_id,
          kieu: 0
      });
      if (response && response.data && response.error_code === 'BSS-00000000'){
        const dataFromAPI = response.data;
        this.ds_thongso_kythuat_danhba = Array.isArray(dataFromAPI) ? dataFromAPI : [];
        const allowedFields = ['System', 'Slot/Port/Rack/Sheft', 'VPI/VCI'];
        this.ds_thongso_kythuat_danhba = this.ds_thongso_kythuat_danhba.filter(item => allowedFields.includes(item.TEN_TRUONG));
        this.ds_thongso_kythuat_danhba.map(x => {
            return Object.assign(x, {
                GIATRI: x.GIATRI != null ? x.GIATRI : '',
                TEN_TRUONG: x.TEN_TRUONG != null ? x.TEN_TRUONG : ''
            });
        }).forEach(item => {
            if (item.FORMAT_TT && item.FORMAT_GT) {
                this.thongsoktdanhba += item.FORMAT_TT.replace('{0}', item.TEN_TRUONG);
                this.thongsoktdanhba += item.FORMAT_GT.replace('{0}', item.GIATRI);
            }
        });
        console.log('hienthi_thongso_kythuat_danhba', this.thongsoktdanhba);
      }
    },

    async hienthi_ds_nv(ds_nhanvien){
      if(ds_nhanvien.length>0){
        this.dataSources=ds_nhanvien
        this.rowSelectedId=ds_nhanvien[0].id
        this.checkBoxs.ngay_gv=true
        //DD/MM/YYYY HH:mm:ss
        //"ngaygiao": "21/12/2021 18:18:57",
        this.ngay_gv=ds_nhanvien[0].ngaygiao
        this.nguoi_gv_selected=ds_nhanvien[0].nhanvien_giao_id

      }else{
        this.dataSources=[]
        if(!this.phailam('GIAOVIEC')){
          this.checkBoxs.ngay_gv=true
        }else{
          this.checkBoxs.ngay_gv=false
        }
        this.ngay_gv=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
        this.nguoi_gv_selected=this.$root.token.getNhanVienID()
      }
    },
    getThongTinPhieu(){
      return {
        dichvu_vt_selected:this.dichvu_vt_selected,
        checkBoxs:this.checkBoxs,
        ds_nhanvien_th:this.dataSources,
        ngay_gv:this.ngay_gv,
        ngay_ht:this.ngay_ht,
        nguoi_gv_selected:this.nguoi_gv_selected,
        checkGiuIP:this.checkGiuIP,
        checkTuDong:this.checkTuDong,
        input_ipv4_lan:this.input_ipv4_lan,
        input_ipv4_wan:this.input_ipv4_wan,
        input_ipv6_lan:this.input_ipv6_lan,
        tocdomoi_selected:this.tocdomoi_selected,
        tocdocu_selected:this.tocdocu_selected,
        tocdothuc_selected:this.tocdothuc_selected,
        dslam_selected:this.dslam_selected,
        bras_selected:this.bras_selected,
        input_rank:this.input_rank,
        input_slot:this.input_slot,
        input_port:this.input_port,
        input_vpi:this.input_vpi,
        input_vci:this.input_vci,
        input_account:this.input_account,
        input_magd:this.input_magd,
        input_system:this.input_system,

        input_sheft:this.input_sheft,
        // txtSelfid01.Text 
        input_self:this.input_self,
        // txtSelfid02.Text
        input_olt_slot:this.input_olt_slot,
        // txtSelfid03.Text
        input_olt_port:this.input_olt_port,
        // txtSelfid04.Text
        input_onuid:this.input_onuid
      }
    },
    async initDuLieu(){
      let tag=this.$route.query.tag
      if(tag&&tag!=''){
          let words=tag.split('+')
          if(words.length>0){
              this.tthd=words[0]
          }
          if(words.length>1){
              this.dsloaihd_id=words[1]
          }
          if(words.length>2){
              this.dsdichvuvt_id=words[2].replaceAll(';',',')
          }
          // if (words.length >= 4)
          // {
          //     if (words[3] == "1")
          //         this.cty_newline = true;
          // }
          // console.log(`cty_newline ${this.cty_newline}`)
      }
      //Khởi tạo tham số
      this.IS_USING_CABMAN_V2=(await this.lay_thamso_cabman_v2()).length>0
      console.log('IS_USING_CABMAN_V2', this.IS_USING_CABMAN_V2)

      this.dichvu_vt = await this.lay_ds_dichvu_vt(this.dsdichvuvt_id)
      if(this.dichvu_vt.length>0){
        this.dichvu_vt_selected= this.dichvu_vt[0].dichvuvt_id
        this.$emit('changeDichVu', this.dichvu_vt_selected)
      }
      //
      this.all_control=await this.lay_all_thaotac_control()
      //comment tạm
      //await this.lay_ds_tocdo_adsl()
      let nguoidung_id=this.$root.token.getNguoiDungID()
      let result=await this.lay_ds_tocdo_adsl_vs_muccuoc(nguoidung_id)
      this.ds_tocdo_adsl=result?result.ds_tocdo:[]
      console.log('ds_tocdo_adsl', this.ds_tocdo_adsl)
      if(this.ds_tocdo_adsl.length>0){
        this.tocdomoi_selected=this.ds_tocdo_adsl[0].TOCDO_ID
        this.tocdocu_selected=this.ds_tocdo_adsl[0].TOCDO_ID
        this.tocdothuc_selected=this.ds_tocdo_adsl[0].TOCDO_ID
      }
      //await this.lay_ds_muccuoc()
      this.ds_muccuoc=result?result.ds_muccuoc:[]
      if(this.ds_muccuoc.length>0){
        this.muccuoc_cu_selected=this.ds_muccuoc[0].MUCCUOC_ID
        this.muccuoc_moi_selected=this.ds_muccuoc[0].MUCCUOC_ID
      }

      await this.lay_ds_bras()
      if(this.bras_selected!=null){
        this.ds_dslam=await this.sp_ht_dslam_cbo(this.bras_selected, 2)
        if(this.ds_dslam.length>0){
          this.dslam_selected=this.ds_dslam[0].dslam_id
        }
      }
      

      let donvi_id=this.$root.token.getDonViID()
      await this.lay_ds_nhanvien_thuchien_theo_dv(donvi_id)

      this.nguoi_gv_selected=this.$root.token.getNhanVienID()
      console.log('2142 - deo chiu dc')
      console.log(this.dichvu_vt_selected)
      await this.loadQuyTrinh()
      //Bổ sung tag quy trình load cho form tra cứu phiếu giao đến
      let quytrinh=this.$route.query.quytrinh
      if(quytrinh){
          this.quytrinh_selected=Number(quytrinh)
          await this.changeQuyTrinh()
      }
      // if(this.dichvu_vt_selected!=null){
      //   this.ds_quytrinh=await this.lay_ds_quytrinh_v2({
      //     dsloaihd_id: this.dsloaihd_id,
      //     dichvuvt_id: this.dichvu_vt_selected,
      //     tthd_id: this.tthd,
      //     kieu: 0
      //   })
      //   if(this.ds_quytrinh.length>0){
      //     this.quytrinh_selected=this.ds_quytrinh[0].quytrinh_id
      //     await this.changeQuyTrinh()
      //   }
      // }

    },
    async clearDuLieu(){
      this.hdtb_id=0
      this.input_magd=''
      //txtMaTB.Text = "";
      this.input_account=''
      this.input_matkhau=''
      this.input_tentb=''
      this.input_diachi_ld=''

      this.$nextTick(()=>{
        this.$refs.inputMaTB.focus()
      })
      this.checkTuDong.check=false
      this.checkGiuIP.check=false
      this.checkTuDong.enable=false
      this.checkGiuIP.enable=false

      this.enableBtnIpV4Lan=false
      this.enableBtnIpV4Wan=false
      this.enableBtnIpV6Lan=false

      this.nguoi_gv_selected=this.$root.token.getNhanVienID()

      this.checkBoxs.ngay_gv=false
      this.checkBoxs.ngay_ht=false

      this.ngay_gv=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      this.ngay_ht=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')

      this.input_port=''
      this.input_rank=''
      this.input_sheft=''
      this.input_vci=''
      this.input_vpi=''
      this.input_port_mdf=''
      this.input_tram_tb=''
      this.input_slot=''
      this.input_system=''
      this.input_self=''
      this.input_olt_slot=''
      this.input_olt_port=''
      this.input_onuid=''

      this.input_ipv4_lan=''
      this.input_ipv4_wan=''
      this.input_ipv6_lan=''

      this.dataSources=[]
      this.input_noidung_giao=''
      this.input_noidung_tra=''
      this.input_ghichu=''
      this.input_noidung_th=''
      this.thongsokt=''
      this.thongsoktdanhba=''
    },
    async loadQuyTrinh() {
      console.log('loadQuyTrinh cty_newline', this.cty_newline)
      // if (cboDichVuVT.length > 0){
        if (!this.cty_newline){
          if(this.dichvu_vt_selected!=null){
            this.ds_quytrinh=await this.lay_ds_quytrinh_v2({
              dsloaihd_id: this.dsloaihd_id,
              dichvuvt_id: this.dichvu_vt_selected,
              tthd_id: this.tthd,
              kieu: 0
            })
            if(this.ds_quytrinh.length>0){
              this.quytrinh_selected=this.ds_quytrinh[0].quytrinh_id
              await this.changeQuyTrinh()
            }
          }
        }else{
          if(this.dichvu_vt_selected!=null){
            this.ds_quytrinh=await this.lay_ds_quytrinh_newlife({
              vphanvung_id: null,
              vloaihd_id: LoaiHopDong.THAY_DOI_TOCDO_ADSL,
              vdichvuvt_id: DichVuVienThong.ADSL,
              vtthd_id: this.tthd  
            })
            if(this.ds_quytrinh.length>0){
              this.quytrinh_selected=this.ds_quytrinh[0].quytrinh_id
              await this.changeQuyTrinh()
            }
          }
        }
    }
  },
  mounted(){
    this.donvi_dl_id=Number(this.$root.token.getDonViDuLieuID())
  },
  watch:{
    'checkGiuIP.check':function(val){
      this.changCheckGiuIP()
    },
    'checkTuDong.check':function(val){
      this.changeCheckTuDong()
    }
  }
}
</script>
<style>
.disabled_color{
  color: gray !important;
  border-color: gray !important;
}
.pl-2{
    padding-left: 2px !important;
}
.input_batbuoc:focus {
    border-color:red;
    background-color: lightcoral;
    box-shadow: none;
}
.readonly_input_white {
    background-color: white !important;
}
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
</style>