<template>
  <div>
  <div class="breadcrumb-top">
    <div class="main-title">{{title}}</div>
    <ul class="breadcrumb">
      <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span>  Cập nhật KQTC </a></li>
      <li class="breadcrumb-item"><a href="#">Thi công mới</a></li>
      <li class="breadcrumb-item active">Trạm VT - Thi công dịch vụ Cơ sở hạ tầng</li>
    </ul>
  </div>
  
    <vue-nav>
      <ul class="list">
        <li
          @click="clickButton('laytt')"
          :class="this.button.laytt ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-attach"></span>Lấy TT </a>
        </li>
        <li
          @click="clickButton('capnhat')"
          :class="this.button.capnhat ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-reload1"></span>Cập nhật</a>
        </li>
        <li
          @click="clickButton('giaoviec')"
          :class="this.button.giaoviec ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-arrow-right1"></span>Giao việc</a>
        </li>
        <li
          @click="clickButton('phoihopthicong')"
          :class="this.button.phoihopthicong ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-thicong"></span>Phối hợp thi công</a>
        </li>
        <li v-if="this.show.kichhoat"
          @click="clickButton('kichhoat')" 
          :class="this.button.kichhoat ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-thicong"></span>Kích hoạt</a>
        </li>         
        <li v-if="this.show.huykichhoat"
          @click="clickButton('huykichhoat')"
          :class="this.button.huykichhoat ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-thicong"></span>Hủy kích hoạt</a>
        </li>   
        <li
          @click="clickButton('hoancong')" :style="this.button.hoancong ? '' : 'pointer-events: none'"
          :class="this.button.hoancong ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-hoancong"></span>Hoàn công</a>
        </li>
        <li
          @click="clickButton('xuatphieu')"
          :class="this.button.xuatphieu ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-download"></span>Xuất phiếu</a>
        </li>
        <li
          @click="clickButton('chuyento')"
          :class="this.button.chuyento ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-change"></span>Chuyển tổ</a>
        </li>
        <li @click="clickButton('traphieu')"
          :class="this.button.traphieu ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-back"></span>Trả phiếu</a>
        </li>
        <li
          @click="clickButton('cosohatang')"
          :class="this.button.cosohatang ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-building"></span>CS hạ tầng</a>
        </li>
        <li
          @click="clickButton('xuatfile')"
          :class="this.button.xuatfile ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-download"></span>Xuất file</a>
        </li>
        <li
          @click="clickButton('tonphieu')"
          :class="this.button.tonphieu ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-calendar-number"></span>Tồn phiếu</a>
        </li>
        <li id="li_hoanthienhoso" style="display:none"
          @click="clickButton('hoanthienhoso')"
          :class="this.button.hoanthienhoso ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-thicong"></span>Hủy kích hoạt</a>
        </li>   
        <li
          @click="clickButton('henlapdat')"
          :class="this.button.henlapdat ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-henld"></span>Hẹn LĐ</a>
        </li>
        <li
          @click="clickButton('tsbtnVatTu')"
          :class="this.button.tsbtnVatTu ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-vt"></span>Vật tư</a>
        </li>
        <li
          @click="clickButton('gankhuvuc')"
          :class="this.button.gankhuvuc ? 'active' : 'non-active'"
        >
          <a> <span class="icon -ap icon-pin"></span>Gán KV</a>
        </li>
      </ul>
    </vue-nav>
        <div class="page-content">
            <div class="box-form">
                <div class="row">
                    <div class="col-sm-2 col-12"></div>
                    <div class="col-sm-10 col-12">
                        <div class="row">
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w30 nowrap fw6">NV tiếp thị</div>
                                    <div class="value">
                                       <input type="text"  v-model="txtNhanVienTiepThi" class="form-control">                                            
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w30 nowrap fw6">Kiểu HĐ</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <SelectExt
                                                v-model="ddlKieuHD" :disabled="true"
                                                :options="this.options.kieuhd"
                                            >
                                                <option disabled value="0">Select one</option>
                                            </SelectExt>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w30 nowrap fw6">Kênh thu</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <SelectExt
                                                v-model="ddlKenhThu" :disabled="true"
                                                :options="this.options.kenhthu"
                                            >
                                                <option disabled value="0">Select one</option>
                                            </SelectExt>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w30 nowrap fw6">Tổng tiền</div>
                                    <div class="value">
                                        <input type="text" v-model="txtTongTien" class="form-control tright red fw6">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="box-form">
                        <div class="legend-title">
                            <div class="pull-left">Thông tin khách hàng</div>
                            <div class="pull-right">
                                <div class="check-action normal">
                                    <input type="checkbox" v-model="chkHenLapDat" class="check">
                                    <span class="name">Lắp đặt theo yêu cầu</span>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="row">
                            <div class="col-sm-8 col-12">
                                <div class="info-row">
                                    <div class="key w90">Loại HĐ/PL</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <SelectExt
                                                v-model="ddlLoaiHD_PL"
                                                :options="this.options.loaihd_pl"
                                            >
                                                <option disabled value="0">Select one</option>
                                            </SelectExt>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w90">Mã GD</div>
                                    <div class="value">
                                        <input type="text" v-model="txtMaGD" class="form-control highlight fw6">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-8 col-12">
                                <div class="info-row">
                                    <div class="key w90">Quy trình</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <SelectExt
                                                v-model="ddlQuyTrinh"
                                                :options="this.options.quytrinh"
                                            >
                                                <option disabled value="0">Select one</option>
                                            </SelectExt>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w90">Loại KH lớn</div>
                                    <div class="value">
                                        <input type="text" v-model="txtLoaiKHLon" class="form-control italic">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-8 col-12">
                                <div class="info-row">
                                    <div class="key w90">Kiểu lắp đặt</div>
                                    <div class="value">
                                        <input type="text"  v-model="txtKieuLapDat" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w90">Account</div>
                                    <div class="value">
                                        <input type="text" v-model="txtAccount" v-on:keyup.enter="this.txtMaTB_KeyPress()" class="form-control highlight fw6">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-8 col-12">
                                <div class="info-row">
                                    <div class="key w90">Tốc độ</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <SelectExt
                                                v-model="ddlTocDo"
                                                :options="this.options.tocdo" :disabled="true"
                                            >
                                                <option disabled value="0">Select one</option>
                                            </SelectExt>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w90">Mức cước</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <SelectExt
                                                v-model="ddlMucCuoc"
                                                :options="this.options.muccuoc" :disabled="true"
                                            >
                                                <option disabled value="0">Select one</option>
                                            </SelectExt>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-8 col-12">
                                <div class="info-row">
                                    <div class="key w90">Loại CSHT</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <SelectExt
                                                v-model="ddlLoaiCSHT" :disabled="true"
                                                :options="this.options.loaicsht"
                                            >
                                                <option disabled value="0">Select one</option>
                                            </SelectExt>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w90">Đối tác</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <SelectExt
                                                v-model="ddlDoiTac"
                                                :options="this.options.doitac" :disabled="true"
                                            >
                                                <option disabled value="0">Select one</option>
                                            </SelectExt>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-8 col-12">
                                <div class="info-row">
                                    <div class="key w90">Tên thuê bao</div>
                                    <div class="value">
                                        <input type="text" v-model="txtTenTB" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w90">Điện thoại LH</div>
                                    <div class="value">
                                        <input type="text" v-model="txtSDTLH" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                       
                        <div class="info-row">
                            <div class="key w90">Địa chỉ LĐ</div>
                            <div class="value">
                                <div class="input-more-button">
                                    <button class="btn"  @click="this.btnBanDoLD_Click()">
                                        <span class="one-map-location f20"></span>
                                    </button>
                                    <input type="text" v-model="txtDiaChiLD" class="form-control fw6">
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Địa chỉ LĐ cũ</div>
                            <div class="value">
                                <input type="text" v-model="txtDiaChiLD_Cu" class="form-control ">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Ghi chú</div>
                            <div class="value">
                                <textarea name="" class="form-control" v-model="txtGhiChu"
                                style="height: 143px;resize: none;" id="" cols="30" rows="10"></textarea>
                            </div>
                        </div>
                        
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="box-form">
                        <div class="legend-title">Thông tin giao việc nhân viên</div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100">Ngày GV</div>
                                    <div class="value w20 padt7">
                                        <div class="check-action">
                                            <input type="checkbox" v-model="chkNgayGV" class="check">
                                            <span class="name"></span>
                                        </div>
                                    </div>
                                    <div class="select-custom">
                                        <vue-date
                                            format="DD/MM/YYYY h:mm:ss"
                                            type="datetime"
                                            v-model="txtNgayGV"
                                            :disable="!chkNgayGV"
                                        ></vue-date>                                            
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100">Ngày HT</div>
                                    <div class="value w20 padt7">
                                        <div class="check-action">
                                            <input type="checkbox" v-model="chkNgayHT" class="check">
                                            <span class="name"></span>
                                        </div>
                                    </div>
                                    <div class="select-custom">
                                        <vue-date
                                            format="DD/MM/YYYY h:mm:ss"
                                            type="datetime"
                                            v-model="txtNgayHT"
                                            :disable="!chkNgayHT"
                                        ></vue-date>                                            
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Người GV</div>
                            <div class="value">
                                <div class="select-custom">
                                            <SelectExt
                                                v-model="ddlNguoiGV"
                                                :options="this.options.nguoigiaoviec"
                                            >
                                                <option disabled value="0">Select one</option>
                                            </SelectExt>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Nội dung giao</div>
                            <div class="value">
                                <input type="text" v-model="txtNoiDungGiao" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Nội dung TH</div>
                            <div class="value">
                                <div class="input-more-button" @click="this.btnNDTH_Click()">
                                    <button class="btn">
                                        <span class="one-file-edit f20"></span>
                                    </button>
                                    <input type="text" v-model="txtNoiDungTH" class="form-control ">
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Nhân viên YC</div>
                            <div class="value">
                                <input type="text" v-model="txtNhanVienYC" class="form-control ">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Thông tin trả</div>
                            <div class="value">
                                <input type="text" v-model="txtThongTinTra" class="form-control ">                                
                            </div>
                        </div>
                        
                    </div>
                    <div class="box-form">
                        <div class="legend-title">
                            <div class="pull-left">Nhân viên</div>
                            <div class="pull-right red">
                                {{lblLienHe}}
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="table-content">
                            <DataGrid
                                :columns="this.options.cols_ds_nv"
                                v-bind:dataSource="options.danhsach_grid_nhanvien"
                                :showColumnCheckbox="true"
                                :enable-paging-server="false"
                                :allowPaging="true"
                                :showFilter="true"
                                :enabledSelectFirstRow="false"
                                ref="danhsachphieu"
                            >
                            </DataGrid>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">
                    <div class="pull-left">
                        Danh sách thuê bao
                    </div>
                    <div class="pull-right">
                        <div class="list-checks">
                            <div class="item red">
                                <div class="check-action">
                                    <input type="radio"  v-model="rdo_checkphieu" value="0" class="check">
                                    <span class="name">Phiếu mới</span>
                                </div>
                            </div>
                            <div class="item red">
                                <div class="check-action">
                                    <input type="radio"  v-model="rdo_checkphieu" value="1" class="check">
                                    <span class="name">Phiếu trả lại</span>
                                </div>
                            </div>
                            <div class="item red">
                                {{lblTongS}}
                            </div>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="table-content">
                <DataGrid
                    ref="danhsach_grid"
                    :columns="this.options.cols_ds"
                    v-bind:dataSource="options.danhsach_grid"
                    :showColumnCheckbox="true"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :enabledSelectFirstRow="false"
                    @rowSelected="rowSelected"
                >
                </DataGrid>
                </div>
            </div>
        
        </div>
     <ejs-dialog :enableResize='true' :visible="false" ref="giaophieunhanvien"
    :header='"Giao phiếu nhân viên"' showCloseIcon=true :target="target" width='60%' height="800">
      <giaophieunhanvien  @giaoviec_success="giaoviec_success()"
      ref="formgiaphieu" 
      :ngaygiao="this.txtNgayGV_new" 
      :nguoigv_id="this.ddlNguoiGV" 
      :phieu_id="this.phieu_id" />
    </ejs-dialog>  
    <ChuyenTo ref="popupChuyenTo"  @closeChuyenTo="closeChuyenTo"></ChuyenTo>
    <TonPhieuGiaoModal ref="tonPhieuGiaoModal" :phieu_id="this.phieu_id" :tthd_id="Number(this.tthd_id)"/>
    <CapVatTuTB ref="popupVatTuTB" :input-data="modelVatTu"/>
  <b-modal v-model="showModalTraphieu" modal-class="popup-box traphieu" header-class="popup-header"
    content-class="traphieu-content" body-class="none-padding traphieu-body" size="xl" title="Trả phiếu" hide-footer>
    <traphieu @update_success="traphieu_success" 
    :ngaytra="0" 
    :ngaygiao="0" 
    :ma_gd="this.txtAccount" 
    :nhanvien_id="this.$root.token.getNhanVienID()"
    :ma_tb="this.txtAccount" 
    :kieu="3" 
    :loaihd_id="this.ddlLoaiHD_PL" 
    :dichvuvt_id="this.dichvuvt_id" />
  </b-modal>
<ThongTinHenKhachHangModal ref="thongTinHenKhachHangModal" :hdkh_id="this.hdkh_id"/>  
<ThayDoiKhuVucQuanLyThueBaoModal ref="thayDoiKhuVucQuanLyThueBaoModal" :data="dataPopupKhuVuc"/>
<ModalChonToaDo  idModalChonToaDo="ModalChonToaDo" 
@XacNhanToaDo="XacNhanToaDo" :position="map" :diachi="this.txtDiaChiLD" 
:ten_kc="'Vị trí tương đối theo địa chỉ:\r\n' + this.txtAccount"></ModalChonToaDo>
<ModalCSHT ref="modalCSHT"  :hdtb_id="this.hdtb_id" :phieu_id="this.phieu_id" />
<InBienBan ref="PopupInBB" :params="modelIn"/>
<PHTC ref="popup_PHTC"   :phieu_id="this.phieu_id" :kieu_id="0"/>
 <VatTuThueBao ref="vatTuThuebao" :inputData="inputDataFormVattuTB"/>
  </div>
</template>
<script>
import api from "./api";
import define from "./define";
import moment from "moment";
import giaophieunhanvien from "@/modules/contract/setup/DeclareLandline/popup/giaophieunhanvien.vue";
import ChuyenTo from "@/modules/contract/profile/CompleteProfileChangeAccount/Popups/ChuyenTo.vue";
import TonPhieuGiaoModal from "@/modules/contract/setup/DeclareServiceSpeedITSecure/popups/TonPhieuGiao/TonPhieuGiaoModal.vue";
import CapVatTuTB from "@/modules/contract/complete_profile/CompleteProfileChangeTSMyTV/popups/CapVatTuTB.vue"
import traphieu from "@/modules/contract/setup/ReturnInstallTicket/components/traphieu.vue";
import ThongTinHenKhachHangModal from "@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue";
import ThayDoiKhuVucQuanLyThueBaoModal from "../SurveyRequest/popups/ThayDoiKhuVucQuanLyThueBao/ThayDoiKhuVucQuanLyThueBaoModal.vue";
import ModalChonToaDo from "@/modules/CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonToaDo";
import ModalCSHT from "./Popup_ThueCSHT.vue";
import PHTC from "./Popup_PhoiHopThiCong.vue";
import InBienBan from "@/modules/print/InBienBan/InBienBan.vue";
import VatTuThueBao from '../ChangeServiceOMCVIPCM/popups/VatTuThueBao.vue'
export default {
  data() {
    return {
        target: '.main-wrapper',
      ...define,
      txtNhanVienTiepThi:"",
      ddlKieuHD:"",
      ddlKenhThu:"",
      txtTongTien:"0",
      chkHenLapDat:false,
      ddlLoaiHD_PL:"",
      ddlQuyTrinh:"",
      ddlTocDo:"",
      ddlMucCuoc:"",
      ddlLoaiCSHT:"",
      ddlDoiTac:"",
      ddlNguoiGV:"",
      txtNhanVienYC:"",
      txtThongTinTra:"",
      lblLienHe:"",
      lblTongS:"",
txtMaGD:"",
txtLoaiKHLon:"",
txtKieuLapDat:"",
txtAccount:"",
txtTenTB:"",
txtSDTLH:"",
txtDiaChiLD:"",
txtDiaChiLD_Cu:"",
txtGhiChu:"",
title:"HOÀN CÔNG INTERNET",
chkNgayGV:true,
txtNgayGV: moment(new Date()).format('DD/MM/YYYY h:mm:ss'),
chkNgayHT:true,
txtNgayHT: moment(new Date()).format('DD/MM/YYYY h:mm:ss'),
txtNgayGV_new:"",
txtNoiDungGiao:"",
txtNoiDungTH:"",
luong_id:"",
huonggiao_id:"",
loc_nv:"",
rdo_checkphieu:0,
vloai_id:0,
hdkh_id:0,
hdtb_id:"",
thuebao_id:0,
phieu_id:0,
loaihd_id:"",
dichvuvt_id:"",
vphailam:"",
kt_load:false,
loaitb_id:0,
dsIn:[],
ds:[],
dtThaoTac:[],
dtControl:[],
dtList:[],
current_row: 0,
kt_status:"",
showModalTraphieu: false,
matb_visa:"",
in_tocdo_id:"",
donvi_id:"",
  dataPopupKhuVuc: {
    quan_id: 0,
    phuong_id: 0,
    pho_id: 0,
    ap_id: 0,
    khu_id: 0,
    dacdiem_id: 0,
    khuvuc_id: 0,
    donviql_id: 0,
    hdtb_id: 0
  },
inputDataFormVattuTB:{
    loaitb_id:0,
    phieu_id:0,
    loaihd_id:0,
    hdtb_id:0,
    thuebao_id:0,
    vsua_vattu:0,
    vdonvi_nhan_id:0,
    vngay_ht:'',
    vttph_id:0
},
modelVatTu: {},
      options: {
          kieuhd:[],
          kenhthu:[],
          loaihd_pl:[],
          quytrinh:[],
          tocdo:[],
          muccuoc:[],
          loaicsht:[],
          doitac:[],
          nguoigiaoviec:[],
        danhsach_grid_nhanvien:[],
    cols_ds_nv:[
            {
                fieldName: "ten_nv",
                headerText: "Tên nhân viên",
                allowSorting: true,
                autoFit: true,
            },
            {
                fieldName: "so_dt",
                headerText: "Điện thoại",
                allowSorting: true,
                autoFit: true,
            },  
            {
                fieldName: "nhiemvu",
                headerText: "Nhiệm vụ",
                allowSorting: true,
                autoFit: true,
            },
            {
                fieldName: "ghichu",
                headerText: "Ghi chú",
                allowSorting: true,
                autoFit: true,
            }                      
    ],
        danhsach_grid:[],
        cols_ds: [
          {
            fieldName: "ma_tb",
            headerText: "Account",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "ten_tb",
            headerText: "Tên TB",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "diachi_ld",
            headerText: "Địa chỉ lắp đặt",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "ngay_yc",
            headerText: "Ngày YC",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "ngaygiao",
            headerText: "Ngày giao",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "dv_hen",
            headerText: "Đơn vị hẹn",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "dv_nhan",
            headerText: "Đơn vị nhận",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "giohen_tu",
            headerText: "Hẹn từ",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "giohen_den",
            headerText: "Hẹn đến",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "ma_gd",
            headerText: "Mã GD",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "goi_kt",
            headerText: "Gói KT",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "gio_ct",
            headerText: "Giờ CT",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "gio_conlai",
            headerText: "Giờ còn lại",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "ngay_ht",
            headerText: "Ngày BG",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "ngay_kh",
            headerText: "Ngày KH",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "ten_status",
            headerText: "Trạng thái",
            allowSorting: true,
            autoFit: true,
          },
          
          {
            fieldName: "dv_giao",
            headerText: "Đơn vị giao",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "lydoton",
            headerText: "Lý do tồn",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "ghichu_ton",
            headerText: "Ghi chú tồn",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "ghichu",
            headerText: "Ghi chú",
            allowSorting: true,
            autoFit: true,
          }
        ],
      },
map:{},
     kt_thaydoi:false,   
modelIn:{},
      Tag: "",
      token_donvi_id:"",
      token_nhanvien_id:"",
      tthd_id:0,
      dsloaihd_id:"",
      dsdichvuvt_id:"",
      nhanvien_tc_id:"",
      congviec_th:"",
      ngay_sys:""
    };
  },
  components: {
      giaophieunhanvien,
      ChuyenTo,
      TonPhieuGiaoModal,
      CapVatTuTB,
      traphieu,
      ThongTinHenKhachHangModal,
      ThayDoiKhuVucQuanLyThueBaoModal,
      ModalChonToaDo,
      ModalCSHT,
      InBienBan,
      PHTC,
      VatTuThueBao
      },
  created() {
        this.txtNgayGV =  moment(new Date()).format('DD/MM/YYYY h:mm:ss')
        this.txtNgayHT =  this.txtNgayGV
        this.token_donvi_id = this.$root.token.getDonViID();
        this.token_nhanvien_id = this.$root.token.getNhanVienID();
        
        try
        {
            this.Tag = this.$route.query.tag;
            if(this.Tag !== null)
            {
                let words = this.Tag.split('+');
                this.tthd_id = words[0];
                this.dsloaihd_id =  words[1];
                this.dsdichvuvt_id = words[2];
            }
            else
            {
                this.tthd_id =4;
                this.dsloaihd_id = 1;
                this.dsdichvuvt_id = 19;
            }
        }
        catch(e)
        {
            this.tthd_id =4;
            this.dsloaihd_id = 1;
            this.dsdichvuvt_id = 19;
        }
        this.getKenhThu();
        this.getKieuHD();
        this.getLoaiHD();
        this.getNguoiGiaoViec();
        this.chkNgayGV = true
        this.getTocDoADSL();
        this.getMucCuoc();
        this.getDoiTac();
        this.getLoaiCSHT();
        //this.get_QuyTrinh_V2();
        //this.HienThiDanhSachHDTB(); 
        if (this.tthd_id == 5)
        {
            this.button.hoanthienhoso = true;
        }
        this.kt_load = true
  },
  watch: {
 
    ddlQuyTrinh(newvalue)
    {
        this.ddlQuyTrinh = newvalue;
        this.cboQuyTrinh_SelectedValueChanged()
    },
    ddlKieuHD(newvalue)
    {
        this.ddlKieuHD = newvalue
        this.cboLoaiHD_SelectedValueChanged()
    },
      rdo_checkphieu(newvalue)
      {
       this.vloai_id = newvalue;
      }
  },
  methods: {
    getKenhThu() {
      api.getKenhThu(this.axios, {
      }).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
          items.push({ id: item.KENHTHU_ID, text: item.KENHTHU });
        });
        this.options.kenhthu = items;
        this.ddlKenhThu = items[0].id;
      });
    },  
    getKieuHD() {
      api.getKieuHD(this.axios, {
      }).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
          items.push({ id: item.KIEUHD_ID, text: item.KIEU_HD });
        });
        this.options.kieuhd = items;
        this.ddlKieuHD = items[0].id;
      });
    },  
    getLoaiHD() {
      api.getLoaiHD(this.axios, {
      }).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
            if(item.LOAIHD_ID == 1)
            {
                items.push({ id: item.LOAIHD_ID, text: item.TEN_LOAIHD });
            }
          
        });
        this.options.loaihd_pl = items;
        this.ddlLoaiHD_PL = items[0].id;
      });
    },  
    getNguoiGiaoViec() {
      api.getNguoiGiaoViec(this.axios, {
          "donvi_id": this.token_donvi_id
      }).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
            items.push({ id: item.nhanvien_id, text: item.ten_nv });
        });
        this.options.nguoigiaoviec = items;
        this.ddlNguoiGV = this.token_nhanvien_id;
      });
    },  
    async getTocDoADSL() {
        this.loading(true);
      await api.getTocDoADSL(this.axios, {
      }).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
            items.push({ id: item.tocdo_id, text: item.thuonghieu });
        });
        this.options.tocdo = items;
        this.ddlTocDo = items[0].id;
        this.loading(false);
      });
    },  
    getMucCuoc() {
      api.getMucCuoc(this.axios, {
      }).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
            items.push({ id: item.MUCCUOC_ID, text: item.TENMUC });
        });
        this.options.muccuoc = items;
        this.ddlMucCuoc = items[0].id;
      });
    },  
getDoiTac()
{
          api.getDoiTac(this.axios, {
      }).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
            items.push({ id: item.doitac_id, text: item.ten_dt });
        });
        this.options.doitac = items;
        this.ddlDoiTac = items[0].id;
      });
},
getLoaiCSHT()
{
          api.getLoaiCSHT(this.axios, {
      }).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
            items.push({ id: item.LOAI_CSHT_ID, text: item.TEN_LOAI_CSHT });
        });
        this.options.loaicsht = items;
        this.ddlLoaiCSHT = items[0].id;
      });
},
async cboLoaiHD_SelectedValueChanged()
{
    this.show.huykichhoat = false;
    if (this.loaihd_id == 1 || this.loaihd_id == 3 || this.loaihd_id == 6)
    {
        this.show.huykichhoat = true
        this.button.huykichhoat = false
    }
    let quytrinh = await this.get_QuyTrinh_V2()
    if(quytrinh.length > 0)
    {
        let items = []
        quytrinh.forEach(function (item) {
            items.push({ id: item.quytrinh_id, text: item.quytrinh })
        })
        this.options.quytrinh = items
        this.ddlQuyTrinh = items[0].id
    }
    else
    {
        if(this.kt_load)
        {
            this.HienThiDanhSachHDTB()
        }
    }
    // dtgDanhSach.Columns["ten_status"].ReadOnly = true;
    // dtgDanhSach.Columns["ngay_ht"].ReadOnly = true;
},
async get_QuyTrinh_V2()
{
    console.log("dsloaihd_id", this.dsloaihd_id,"dichvuvt_id", this.dsdichvuvt_id,"tthd_id", this.tthd_id,)
    const res = await api.get_QuyTrinh_V2(this.axios, {
                "dsloaihd_id": this.dsloaihd_id,
                "dichvuvt_id": this.dsdichvuvt_id,
                "tthd_id": this.tthd_id,
                "kieu": 0
      })
      return res.data.data ? res.data.data :[]
},
async cboQuyTrinh_SelectedValueChanged()
{
    this.ds = await this.get_HuongGiaoTheoQuyTrinh()
    if(this.ds.length > 0)
    {
        this.luong_id = this.ds[0].luong_id;
        this.huonggiao_id = this.ds[0].huonggiao_id;
        console.log('this.huonggiao_id',this.huonggiao_id,this.ds)
        this.title = this.ds[0].huonggiao
    }
    else
    {
        this.$toast.error("Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại");
        this.HienThiDanhSachHDTB();
        return;        
    }
    //console.log('luong_id',this.luong_id)
    //this.HienThiGiaoDien(this.luong_id)
    this.loc_nv = 0
    if (this.PHAILAM("LOCTHEO_NV") == 1)
    {
        this.loc_nv  = 1;
    }
    if (this.PHAILAM("GIAOVIEC") == 0)
    {
        this.chkNgayGV = true
        this.chkNgayHT = true
        this.txtNgayGV =  moment(new Date()).format('DD/MM/YYYY h:mm:ss')
        this.txtNgayHT =  moment(new Date()).format('DD/MM/YYYY h:mm:ss')
    }
    if (this.kt_load)
    {
        this.HienThiDanhSachHDTB()
    }
},
txtMaTB_KeyPress()
{
    try
    {
        if (this.txtAccount.trim() != "" || this.txtAccount.trim() != null)
        {
            let dr = this.dsIn.filter(function (ds) {
                return ds.ma_tb == this.txtAccount.trim()
                });  
            if (dr.Length > 0)
            {
                this.dsIn = []
                this.dsIn = dr
            }
            else
            {
                this.dtList = this.dsIn
                this.danhsach_grid = this.dtList
            }
        }
    }
    catch(e)
    {
    }
},
// async HienThiGiaoDien()
// {
//     this.button.hoanthanh = false
//     this.button.traphieu = false
//     this.button.hoancong = false
//     this.button.hoanthienhoso = false
//     this.button.giaophieu = false
//     this.button.giaoviec = false
//     this.button.kichhoat = false
//     this.button.vattu = false
//     try
//     {
//         this.dtThaoTac = await this.lay_luong_thaotac()
//         //console.log('dtThaoTac',this.dtThaoTac)
//         if (this.dtThaoTac.length > 0)
//         {
//             for (var i = 0; i < this.dtThaoTac.length; i++)
//             {
//                 this.dtControl = await this.lay_ct_thaotac_control(this.dtThaoTac[i].thaotac_id)
//                 console.log('dtControl',this.dtControl,this.dtThaoTac[i])
//                 if (this.dtControl.length > 0)
//                 {
//                     for (var j = 0; j < this.dtControl.length; j++)
//                     {
//                          if (this.dsThaoTac[i].enable === '1')
//                          {
//                             let control_name = dtControl[j].control_name
//                             if (dtControl.filter(a => a.control_name === control_name).length > 0) 
//                             {
                               
//                             }                             
//                          }
//                         l
//                         console.log('control_name',control_name)
//                     }
//                 }
//             }
//         }
//     }
//     catch(e)
//     {
//     }
// },
async  get_HuongGiaoTheoQuyTrinh()
{  
    console.log( "quytrinh_id", this.ddlQuyTrinh,"tthd_id", this.tthd_id)
        const res = await api.get_HuongGiaoTheoQuyTrinh(this.axios, {
            "quytrinh_id": this.ddlQuyTrinh,
            "tthd_id": this.tthd_id
      })
        return res.data.data ? res.data.data : []
},
async  lay_ct_thaotac_control(thaotac_id)
{       
        const res = await api.lay_ct_thaotac_control(this.axios, {
            "thaotac_id": 43
      })
        return res.data.data ? res.data.data : []
},
async  lay_luong_thaotac()
{  
    const res = await api.lay_luong_thaotac(this.axios, {
             "luong_id": this.luong_id
      })
        return res.data.data ? res.data.data : []
},
async PHAILAM(code)
{  
         const res =  await api.PHAILAM(this.axios, {
            "luong_id": this.luong_id,
            "code": code
      })
        this.vphailam = res.data.data ?  res.data.data : 0
        //console.log('vphailam', this.luong_id,this.vphailam)
        return this.vphailam
},
async HienThiDanhSachHDTB()
{
    try
    {
        //console.log('luong_id', this.luong_id)
        this.loc_nv = 0
        if(await this.PHAILAM("LOCTHEO_NV") == 1)
        {
            this.loc_nv = 1
        }
        let vloai_id = 0;// 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
        if (this.rdo_checkphieu == 0)
            vloai_id = 0
        else
            vloai_id = 1
        this.dsIn = await this.get_DSPHIEU_HC_DVVT()
        console.log('dsIn',this.dsIn)
        this.dtList = this.dsIn
        this.ClearForm()
        if(this.dsIn.length > 0)
        {
            this.options.danhsach_grid = this.dsIn
            let ts = this.$refs["danhsach_grid"].getSelectedRecords().length
            //this.HienThiAnhTrangThai()
            this.lblTongS = "Tổng số: "+ ts +"/" + this.options.danhsach_grid.length + "";
        }
        else
        {
            this.ClearForm()
            this.lblTongS = "Tổng số: 0/0"
        }
    }
    catch(e)
    {
        this.$toast.error("" + e)
    }
},
async get_DSPHIEU_HC_DVVT()
{  
         const res =  await api.get_DSPHIEU_HC_DVVT(this.axios, {
        "vtthd_id":this.tthd_id,
        "vnhanvien_id":this.$root.token.getNhanVienID(),
        "vma_gd":0,
        "vphieu_id":0,
        "vloaihd_id":this.ddlLoaiHD_PL,
        "vkieu_id":-1,
        "vhuonggiao_id":this.huonggiao_id,
        "vloc_nv":this.loc_nv,
        "vloai_id":this.vloai_id
      })
       return res.data.data ?  res.data.data : []
},
async rowSelected(subject)
{
    console.log('subject',subject);
    try
    {
        this.button.capnhat = false
        this.button.traphieu = false
        if(this.dsIn.length > 0)
        {   
            this.phieu_id = subject.data.phieu_id
            this.button.capnhat = true
            this.button.traphieu = true
            this.hdtb_id = subject.data.hdtb_id
            this.hdkh_id = subject.data.hdkh_id
            //Lấy thông tin liên hệ lắp đặt thuê bao
            this.lblLienHe = await this.get_ThongTin_LHE_LDAT(this.hdkh_id)
            if(this.lblLienHe == null || this.lblLienHe == "")
            {
                this.lblLienHe = ""
            }
            else
            {
                this.lblLienHe = "Liên hệ : " + this.lblLienHe
            }
            //nhapt thêm 06072015
            let ds_tien = []
            ds_tien = await this.get_TongTien_Theo_HDKH(this.hdkh_id)
            
            
            if (ds_tien.length > 0)
            {
                this.txtTongTien = ds_tien[0].tongtien
            }
            else{
                this.txtTongTien = 0
            }
            this.txtNhanVienYC = await this.get_TT_NhanVien(subject.data.nhanvien_id)
            
            let ctv_id = subject.data.ctv_id
            
                
            this.loaitb_id = subject.data.loaitb_id
            this.current_row = subject.rowIndex
            if((this.ddlLoaiHD_PL == 3 || this.ddlLoaiHD_PL == 30 || this.ddlLoaiHD_PL == 6) && this.loaitb_id != 24)
            {
                this.thuebao_id = subject.data.thuebao_id
            }
            
            this.phieu_id = subject.data.phieu_id
            let dt_kenhthu =[]
            dt_kenhthu = await this.get_KenhThu(this.hdkh_id)
            if(dt_kenhthu.length > 0)
            {
                this.ddlKenhThu = dt_kenhthu[0].kenhthu_id
            }
            if(subject.data.kieuhd_id != null || subject.data.kieuhd_id != "")
            {
                this.ddlKieuHD = subject.data.kieuhd_id
            }
            else
            {
                this.ddlKieuHD = 1
            }
            if (await this.KiemTra_Hen(this.hdtb_id) == 1)
            {
                this.chkHenLapDat = true
            }
            else
            {
                this.chkHenLapDat = false
            }
            let dstra=[]
            
            dstra = await this.HT_PhieuTra(this.phieu_id)
            if(dstra.length > 0)
            {
                this.txtThongTinTra = dstra[0].nd_tra
            }
            else
            {
                this.txtThongTinTra = ""
            }
            this.loaihd_id = subject.data.loaihd_id
            this.dichvuvt_id = subject.data.dichvuvt_id
            this.txtSDTLH = subject.data.so_dt_lh
            if(subject.data.tocdo_id != null || subject.data.tocdo_id != "")
            {
                this.ddlTocDo = subject.data.tocdo_id
                this.in_tocdo_id = subject.data.tocdo_id
            }
            this.txtNoiDungTH = subject.data.nd_thuchien
            let dtTemp = await this.SP_LAY_DS_HDTB_DV_KIEUDV(this.hdtb_id)
            console.log('dtTemp',dtTemp)
            if(dtTemp.length > 0)
            {
                this.donvi_id = dtTemp[0].donvi_id
                
            }
            this.kt_status = subject.data.status
            this.button.huykichhoat = false
            if(await this.PHAILAM("TSBTNHUYKH") == 1)
            {
                this.button.huykichhoat = true
            }
            if(subject.data.status != null || subject.data.status != "")
            {
                if (subject.data.status == 5)
                    this.button.huykichhoat = true
                else
                    this.button.huykichhoat = false
            }
            //phải làm
            if(this.ddlLoaiHD_PL == 6)//Chuyển đổi loại hình
            {
                alert(1464)
                if(await this.PHAILAM("KICHHOAT_TD") == 1)
                {
                    if(subject.data.ngay_ht_gp != null)
                    {
                        this.chkNgayHT= true
                        this.txtNgayHT = moment(subject.data.ngay_ht_gp).format("DD/MM/YYYY h:mm:ss")     
                        if(this.kt_status == 5)
                        {
                            this.button.giaophieu = true
                        }
                        else
                        {
                            this.button.giaophieu = false
                        }
                    }
                    else
                    {
                        
                        if(await this.PHAILAM("GIAOVIEC") == 0)
                        {      
                            this.chkNgayHT = true
                            this.txtNgayHT = moment(new Date()).format('DD/MM/YYYY h:mm:ss')
                        }
                        else
                        {
                            this.chkNgayHT = false
                            this.txtNgayHT = moment(new Date()).format('DD/MM/YYYY h:mm:ss')
                        }
                        this.button.giaophieu = false
                    }
                }
                //Nếu phải giao phiếu
               
                if(await this.PHAILAM("GIAOPHIEU") == 1)
                {
                    if(subject.data.ngay_ht_gp != null)
                    {
                        this.txtNgayHT = moment(subject.data.ngay_ht_gp).format("DD/MM/YYYY h:mm:ss"); 
                        this.chkNgayHT = true
                        if(await this.PHAILAM("KICHHOAT_TD") == 1)
                        {
                            if(this.kt_status == 5)
                            {
                                this.button.giaophieu = true
                            }
                            else
                            {
                                this.button.giaophieu = false
                            }
                        }
                        else
                        {
                            this.button.giaophieu = true
                        }
                    }
                    else
                    {
                        if(await this.PHAILAM("GIAOVIEC") == 0)
                        {
                            this.chkNgayHT = true
                            this.txtNgayHT = moment(new Date()).format('DD/MM/YYYY h:mm:ss')
                        }
                        else
                        {          
                            this.chkNgayHT = false
                            this.txtNgayHT = moment(new Date()).format('DD/MM/YYYY h:mm:ss')
                        }
                        this.button.giaophieu = false
                    }
                }
                //Nếu phải hoàn công
                if(await this.PHAILAM("HOANCONG") == 1)
                {
                    if(subject.data.ngay_ht_gp != null && this.kt_status == 5)
                    {
                        this.txtNgayHT = moment(subject.data.ngay_ht_gp).format("DD/MM/YYYY h:mm:ss")
                        this.chkNgayHT = true
                        this.button.hoancong = true 
                    }
                    else
                    {
                        if(await this.PHAILAM("GIAOVIEC") == 0)
                        {           
                            this.chkNgayHT = true
                            this.txtNgayHT = moment(new Date()).format('DD/MM/YYYY h:mm:ss')
                        }
                        else
                        {           
                            this.chkNgayHT = false
                            this.txtNgayHT = moment(new Date()).format('DD/MM/YYYY h:mm:ss')
                        }
                        this.button.hoancong = false 
                    }
                }
            }
           
            if(this.ddlLoaiHD_PL == 1 || this.ddlLoaiHD_PL == 30)//Đặt mới hoặc khôi phục sd
            {
                if(await this.PHAILAM("KICHHOAT_TD") == 1)
                {
                    if(subject.data.ngay_ht_gp != null)
                    {      
                        this.txtNgayHT = moment(subject.data.ngay_ht_gp).format("DD/MM/YYYY h:mm:ss");      
                        this.chkNgayHT = true
                        if(this.kt_status == 5)
                        {
                            this.button.giaophieu = true
                        }
                        else
                        {
                            this.button.giaophieu = false
                        }
                    }
                    else
                    {
                        if(await this.PHAILAM("GIAOVIEC") == 0)
                        {              
                            this.chkNgayHT = true
                            this.txtNgayHT = moment(new Date()).format('DD/MM/YYYY h:mm:ss')
                        }
                        else
                        {         
                            this.chkNgayHT = false
                            this.txtNgayHT = moment(new Date()).format('DD/MM/YYYY h:mm:ss')
                        }
                        this.button.giaophieu = false
                    }
                }
                //Nếu phải giao phiếu
                if(await this.PHAILAM("GIAOPHIEU") == 1)
                {
                    if(subject.data.ngay_ht_gp != null)
                    {      
                        this.chkNgayHT = true
                        this.txtNgayHT = moment(subject.data.ngay_ht_gp).format("DD/MM/YYYY h:mm:ss")
                        
                        if(await this.PHAILAM("KICHHOAT_TD") == 1)
                        {
                            if (this.kt_status == 5)
                            {
                                this.button.giaophieu = true
                            }
                            else
                            {
                                this.button.giaophieu = false
                            }
                        }   
                        else
                        {
                            this.button.giaophieu = false
                        }        
                    }
                    else
                    {
                        
                        if(await this.PHAILAM("GIAOVIEC") == 0)
                        {           
                            this.chkNgayHT = true
                            this.txtNgayHT = moment(new Date()).format('DD/MM/YYYY h:mm:ss')
                        }
                        else
                        {            
                            this.chkNgayHT = false
                            this.txtNgayHT = moment(new Date()).format('DD/MM/YYYY h:mm:ss')
                        }
                        this.button.giaophieu = false            
                    }
                
                }
                //Nếu phải hoàn công
                if(await this.PHAILAM("HOANCONG") == 1)
                {
                    // let trangthai_db = 0;
                    // if(this.loaitb_id == 24) //FSECURE
                    // {
                    //     trangthai_db = 5;
                    // }
                    let trangthai_db = 5;
                    console.log( 'subject.data.ngay_ht_gp',subject.data.ngay_ht_gp,'subject.data', subject.data)  
                    if(subject.data.ngay_ht_gp != null && this.kt_status == trangthai_db)
                    {     
                        this.chkNgayHT = true
                        this.txtNgayHT = moment(subject.data.ngay_ht_gp,).format("DD/MM/YYYY h:mm:ss")
                        this.button.hoancong = true
                    }
                    else
                    {
                        console.log(await this.PHAILAM("GIAOVIEC") , 'await this.PHAILAM("GIAOVIEC") ')
                        if(await this.PHAILAM("GIAOVIEC") == 0)
                        {          
                            this.chkNgayHT = true
                            this.txtNgayHT = moment(new Date()).format('DD/MM/YYYY h:mm:ss')
                        }
                        else
                        {            
                            this.chkNgayHT = false
                            this.txtNgayHT = moment(new Date()).format('DD/MM/YYYY h:mm:ss')
                        }
                        this.button.hoancong = false            
                    }
                
                }
                //hoàn thiện hồ sơ
                if(await this.PHAILAM("HOANTHIENHOSO") == 1)
                {
                    if(subject.data.ngay_ht_gp !== null)
                    {       
                        this.chkNgayHT = true
                        this.txtNgayHT = moment(subject.data.ngay_ht_gp).format("DD/MM/YYYY h:mm:ss")
                        this.button.hoanthienhoso = true              
                    }
                    else
                    {
                        
                        if(await this.PHAILAM("GIAOVIEC") == 0)
                        {            
                            this.chkNgayHT = true
                            this.txtNgayHT = moment(new Date()).format('DD/MM/YYYY h:mm:ss')
                        }
                        else
                        {           
                            this.chkNgayHT = false
                            this.txtNgayHT = moment(new Date()).format('DD/MM/YYYY h:mm:ss')
                        }
                        this.button.hoanthienhoso = false     
                    }
                }
            }
            
            if(this.ddlLoaiHD_PL == 3)//Di chuyển
            {
                if(await this.PHAILAM("KICHHOAT_TD") == 1)
                {
                    if(subject.data.ngay_ht_gp != null)
                    {       
                        this.txtNgayHT = moment(subject.data.ngay_ht_gp).format("DD/MM/YYYY h:mm:ss")  
                        this.chkNgayHT = true
                        if(this.kt_status == 5)
                        {
                            this.button.giaophieu = true
                        }
                        else
                        {
                            this.button.giaophieu = false
                        }
                    }
                    else
                    {
                        if(await this.PHAILAM("GIAOVIEC") == 0)
                        {             
                            this.chkNgayHT = true
                            this.txtNgayHT = moment(new Date()).format('DD/MM/YYYY h:mm:ss')
                        }
                        else
                        {           
                            this.chkNgayHT = false
                            this.txtNgayHT = moment(new Date()).format('DD/MM/YYYY h:mm:ss')
                        }
                        this.button.giaophieu = false            
                    }
                }
                //Nếu phải giao phiếu
                
                if(await this.PHAILAM("GIAOPHIEU") == 1)
                {
                    if(subject.data.ngay_ht_gp !== null)
                    {   
                        this.txtNgayHT = moment(subject.data.ngay_ht_gp).format("DD/MM/YYYY h:mm:ss"); 
                        this.chkNgayHT = true
                        
                        if(await this.PHAILAM("KICHHOAT_TD") == 1)
                        {
                            if(this.kt_status == 5)
                            {
                                this.button.giaophieu = true
                            }
                            else
                            {
                                this.button.giaophieu = false
                            }
                        }
                        else
                        {
                            this.button.giaophieu = true
                        }
                    }
                    else
                    {
                        
                        if(await this.PHAILAM("GIAOVIEC") == 0)
                        {             
                            this.chkNgayHT = true
                            this.txtNgayHT = moment(new Date()).format('DD/MM/YYYY h:mm:ss')
                        }
                        else
                        {             
                            this.chkNgayHT = false
                            this.txtNgayHT = moment(new Date()).format('DD/MM/YYYY h:mm:ss')
                        }
                        this.button.giaophieu = false
                    }
                }
                //Nếu phải hoàn công
                
                if(await this.PHAILAM("HOANCONG") == 1)
                {
                    let trangthai_db = 5
                    if(this.dichvuvt_id == 35)//IOFFICE
                    {
                        trangthai_db = 0
                    }
                    if(this.dichvuvt_id == 12)//MEGA_EYES
                    {
                        trangthai_db = ""
                    }        
                    if(subject.data.ngay_ht_gp != null &&  this.kt_status == trangthai_db)
                    {        
                        this.txtNgayHT = moment(subject.data.ngay_ht_gp).format("DD/MM/YYYY h:mm:ss")
                        this.chkNgayHT = true
                        this.button.hoancong = true 
                    }
                    else
                    {
                        if(await this.PHAILAM("GIAOVIEC") == 0)
                        {           
                            this.chkNgayHT = true
                            this.txtNgayHT = moment(new Date()).format('DD/MM/YYYY h:mm:ss')
                        }
                        else
                        {            
                            this.chkNgayHT = false
                            this.txtNgayHT = moment(new Date()).format('DD/MM/YYYY h:mm:ss')
                        }
                        this.button.hoancong = false 
                    }
                }
                //hoàn thiện hồ sơ
                
                if(await this.PHAILAM("HOANTHIENHOSO") == 1)
                {
                    if(subject.data.ngay_ht_gp !== null)
                    {      
                        this.chkNgayHT = true
                        this.txtNgayHT = moment(subject.data.ngay_ht_gp).format("DD/MM/YYYY h:mm:ss")
                        this.button.hoanthienhoso = true              
                    }
                    else
                    {
                        
                        if(await this.PHAILAM("GIAOVIEC") == 0)
                        {          
                            this.chkNgayHT = true
                            this.txtNgayHT = moment(new Date()).format('DD/MM/YYYY h:mm:ss')
                        }
                        else
                        {            
                            this.chkNgayHT = false
                            this.txtNgayHT = moment(new Date()).format('DD/MM/YYYY h:mm:ss')
                        }
                        this.button.hoanthienhoso = false     
                    }
                }
            }
            if(await this.PHAILAM("KICHHOAT_TD") == 1)
            {
                if(this.kt_status == 5)
                {
                    this.button.kichhoat = false
                }
                else
                {
                    this.button.kichhoat = true
                }
            }
            else
            {
                this.button.kichhoat = false
            }
            
            if(await this.PHAILAM("GIAOVIEC") == 0)
            {
                this.chkNgayHT = true
                this.chkNgayGV = true
            }
            else
            {
                this.chkNgayHT = false
                this.chkNgayGV = false
            }
            await this.getTocDoADSL_LoaiTB_ID(this.loaitb_id)
            this.txtMaGD = subject.data.ma_gd;
            this.txtAccount = subject.data.ma_tb;
            this.txtTenTB = subject.data.ten_tb;
            this.txtDiaChiLD = subject.data.diachi_ld
            this.txtDiaChiLD_Cu = subject.data.diachild_cu
            this.txtGhiChu = subject.data.ghichu
            this.txtNoiDungGiao = subject.data.nd_giao
            this.txtKieuLapDat = subject.data.ten_kieuld 
            
            this.ddlLoaiCSHT = subject.data.loai_csht_id
            this.ddlMucCuoc = subject.data.muccuoc_id  
            this.ddlDoiTac = subject.data.doitac_id
            this.ddlTocDo = subject.data.tocdo_id
            await this.getDanhSachNV_TheoPhieuID(this.phieu_id)
            if(this.tthd_id == 5)
            {
                this.button.hoanthienhoso = true
            }            
            let ts = this.$refs["danhsach_grid"].getSelectedRecords().length
            //this.HienThiAnhTrangThai()
            this.lblTongS = "Tổng số: "+ ts +"/" + this.options.danhsach_grid.length + "";

            if(subject.data.dichvuvt_id == 19 && subject.data.ngay_ht_gp != null){
                this.button.hoancong = true;
                this.button.kichhoat = false; 
            }
        }
    }
    catch(e)
    {
        
    }
},
async get_ThongTin_LHE_LDAT(hdkh_id)
{  
        const res =  await api.get_ThongTin_LHE_LDAT(this.axios, {
            "hdkh_id": hdkh_id
      })
        return res.data.data ? res.data.data : ""
},
async get_TongTien_Theo_HDKH(hdkh_id)
{  
        const res =  await api.get_TongTien_Theo_HDKH(this.axios, {
            "vhdkh_id": hdkh_id
      })
        return res.data.data ? res.data.data : []
},
async get_TT_NhanVien(nhanvien_id)
{  
        const res =  await api.get_TT_NhanVien(this.axios, {
            "nhanvien_id": nhanvien_id
      })
        return res.data.data ? res.data.data : []
},
async get_TT_NhanVien_TiepThi(nhanvien_id)
{  
    const res =  await api.get_TT_NhanVien(this.axios, {
            "nhanvien_id": nhanvien_id
    })
        let kq =  res.data.data ? res.data.data : ''
        if(kq.length > 0)
        {
            return kq
        }
        else
        {
            return ''
        }
},
async get_KenhThu(hdkh_id)
{  
        const res =  await api.get_KenhThu(this.axios, {
            "hdkh_id": hdkh_id
      })
          return res.data.data ? res.data.data : []
},
async KiemTra_Hen(hdtb_id)
{  
        const res =  await api.KiemTra_Hen(this.axios, {
            "hdtb_id": hdtb_id
      })
        return res.data.data ? res.data.data : 0
},
async SP_LAY_DS_HDTB_DV_KIEUDV(hdtb_id) {
            const res =  await api.SP_LAY_DS_HDTB_DV_KIEUDV(this.axios, {}, hdtb_id)
             console.log('res',res.data.data)
    return res.data.data ? res.data.data : []
},
// async SP_LAY_DS_HDTB_DV_KIEUDV(hdtb_id) {
//     const res = await this.axios.get("/web-hopdong/hopdong/lay_ds_hopdong_thuebao_tls?hdtbId="+hdtb_id, {})
   
//     return res.data.data ? res.data.data : []
// },
async HT_PhieuTra(phieu_id)
{  
    const res =   await api.HT_PhieuTra(this.axios, {
            "phieu_id": phieu_id
      })
       return res.data.data ? res.data.data : []
},
async getTocDoADSL_LoaiTB_ID(loaitb_id)
{  
     const res =  await api.getTocDoADSL_LoaiTB_ID(this.axios, {
           "loaitb_id": loaitb_id
      })
        let abc = res.data.data ? res.data.data : []
        let items = [];
        abc.forEach(function (item) {
            items.push({ id: item.tocdo_id, text: item.thuonghieu });
        });
        this.options.tocdo = items;
        this.ddlTocDo = items[0].id;
},
async getDanhSachNV_TheoPhieuID(phieu_id)
{  
    console.log("nhanvien_id", this.$root.token.getNhanVienID())
    const res =  await api.getDanhSachNV_TheoPhieuID(this.axios, {
        "phieu_id": phieu_id,
        "nhanvien_id": this.$root.token.getNhanVienID(),
        "kieu_id": 2
      })
        let items = res.data.data ? res.data.data : []
        this.HienThiDSNV(items)
},
async HienThiDSNV(ds)
{
    console.log('ds',ds)
    
    if(ds.length > 0)
    {
        // this.chkNgayGV = true
        // this.txtNgayGV =  moment(ds[0].ngaygiao).format('DD/MM/YYYY h:mm:ss')  
        this.ddlNguoiGV = ds[0].nhanvien_giao_id
        this.options.danhsach_grid_nhanvien = ds
        this.nhanvien_tc_id = ds[0].nhanvien_th_id
        this.congviec_th = ds[0].nhiemvu
    }
    else
    {
        this.options.danhsach_grid_nhanvien=[]
        
        // if(await this.PHAILAM("GIAOVIEC") == 0)   
        // {
        //     this.chkNgayGV = true 
        //     this.txtNgayGV = moment(new Date()).format('DD/MM/YYYY h:mm:ss')        
        // }  
        // else
        // {
        //     this.chkNgayGV = false
        //     this.txtNgayGV = moment(new Date()).format('DD/MM/YYYY h:mm:ss')              
        // }   
        this.ddlNguoiGV = this.$root.token.getNhanVienID()
        this.nhanvien_tc_id = -1
        this.congviec_th = ""
    }
},
GiaoViec()
{
    if(this.options.danhsach_grid.length <= 0)
    {
        this.$toast.error("Chưa có danh thuê bao. Bạn hãy kiểm tra lại!!!");
        return;
    }
    if(this.ddlNguoiGV == "" || this.ddlNguoiGV === null)
    {
        this.$toast.error("Hãy nhập nhân viên giao việc!!!");
        return;
    }
    if(this.txtNgayGV == "")
    {
        this.$toast.error("Hãy nhập ngày giao việc!!!");
        return;        
    }
    //if(moment(this.txtNgayGV).diff(moment(this.txtNgayHT),"hours") < 0) 
    if(this.txtNgayGV > this.txtNgayHT)
    {
        this.$toast.error("Ngày giao việc không được lớn hơn ngày hoàn thành!!!");
        return;   
    }
    this.$refs.formgiaphieu.init();
    this.txtNgayGV_new =  moment(this.txtNgayGV, 'DD/MM/YYYY h:mm:ss').format('DD/MM/YYYY h:mm:ss') 
    this.$refs.giaophieunhanvien.show();
},
giaoviec_success()
{
    this.getDanhSachNV_TheoPhieuID(this.phieu_id)
},
async ChapNhan()
{
    if(await this.KiemTra_DL() == false)
    {
        return
    }
    let array_json= []
    array_json.push({ 
        "HDTB_ID": this.hdtb_id, 
        "LUONG_ID": this.luong_id, 
        "PHIEU_ID": this.phieu_id,
        "NHANVIEN_ID": this.$root.token.getNhanVienID(),
        "NGUOIGV_ID": this.ddlNguoiGV,
        "NGAY_GV":  moment(this.txtNgayGV, 'DD/MM/YYYY h:mm:ss').format('DD/MM/YYYY H:mm:00'),
        "NGAY_BG": moment(this.txtNgayHT, 'DD/MM/YYYY h:mm:ss').format('DD/MM/YYYY H:mm:00'),
        "LOAIHD_ID": this.ddlLoaiHD_PL
        });
    
    
    console.log('array_json',array_json)
    let kq = await this.CapNhat(array_json)
    
    if(kq == 1)
    {
        this.$toast.success("Cập nhật thông tin thành công !");                
    }    
    else
    {
        this.$toast.error("Xảy ra lỗi trong quán trình cập nhật, " + kq);       
    }
},
async CapNhat(array_json)
{  
    console.log('array_json',array_json)
    const res = await api.CapNhat(this.axios, {
            "vds": array_json
        })
            console.log(' res.data', res.data,res)
    return  res.data.data ? res.data.data : 0
},
ChuyenTo()
{
    if (this.phieu_id > 0) {
            this.$refs.popupChuyenTo.openDialog(
            this.phieu_id,
            this.hdtb_id
          );
    }
    else
    {
          this.$toast.error("Bạn chưa chọn thuê bao để chuyển !");
          return;
    }
},
closeChuyenTo(ChapNhan)
{
    if(ChapNhan)
    this.get_DSPHIEU_HC_DVVT();
},
GiaoPhieuTon()
{
    if (this.phieu_id > 0) {
        //binding phieu_id, tthd_id
        this.$refs.tonPhieuGiaoModal.showModal();
    }
    else
    {
          this.$toast.error("Hãy chọn phiếu trước để nhập lý do tồn!");
          return;
    }
},
CapVatTu()
{
    if (this.phieu_id > 0) {
        //binding phieu_id, loaihd_id,hdtb_id,thuebao_id
      this.modelVatTu.phieu_id = this.phieu_id;
      this.modelVatTu.loaihd_id = this.loaihd_id;
      this.modelVatTu.hdtb_id = this.hdtb_id;
      this.modelVatTu.thuebao_id = this.thuebao_id;
      this.$refs.popupVatTuTB.openDialog()
    }
    else
    {
          this.$toast.error("Hãy chọn phiếu trước để cấp vật tư!");
          return;
    }
},
async HoanCong()
{
    this.$confirm(`Bạn thật sự muốn hoàn công không ?`, {
        confirmButtonText: "Có",
        cancelButtonText: "Không",
        type: "warning",
        }).then(async () => {
        let json_data = []
        json_data.push(
            {
            "LUONG_ID": this.luong_id,
            "HDTB_ID": this.hdtb_id,
            "PHIEU_ID": this.phieu_id,
            "NHANVIEN_ID": this.$root.token.getNhanVienID(),
            "NGUOIGV_ID": this.ddlNguoiGV,
            "NGAY_GV":  moment(this.txtNgayGV, 'DD/MM/YYYY h:mm:ss').format('DD/MM/YYYY H:mm:00'),
            "NGAY_BG": moment(this.txtNgayHT, 'DD/MM/YYYY h:mm:ss').format('DD/MM/YYYY H:mm:00'),
            "LOAIHD_ID": this.loaihd_id,
            "MA_TB": this.txtAccount,
            "HUONGGIAO_ID": this.huonggiao_id,
            "THUEBAO_ID": this.thuebao_id,
            "DONVI_ID": this.$root.token.getDonViID(),
            "MAY_CN":await this.$root.token.getMachine(),
            "NGUOI_CN":await  this.$root.token.getNguoiDungID(),
            "IP_CN":await this.$root.token.getIP(),
            "SDT_LH": this.txtSDTLH,
            "LOAITB_ID": this.loaitb_id
        }
        )
        
        console.log('json_data',json_data)
            const res = await api.HoanCong_HTVT(this.axios, {
                    "vds": json_data
                })
            
            if(res.data.error == '200' && res.data.error == 'BSS-00000000')
            {
                this.HienThiDanhSachHDTB(); 
                this.$toast.success('Hoàn công thành công');
            }
            else
            {
                this.$toast.error(res.data.message);
            }
        })
},
async TraPhieu()
{
    if(this.phieu_id > 0)
    {
        this.showModalTraphieu= true
    }
    else
    {
          this.$toast.error("Hãy chọn phiếu để trả lại!!!");
          return;        
    }
    
},
async traphieu_success()
{
        await this.HienThiDanhSachHDTB()
},
HenLapDat()
{
    if (this.phieu_id > 0) {
        this.$refs.thongTinHenKhachHangModal.showModal();
    }
    else
    {
          this.$toast.error("Hãy chọn phiếu trước để hẹn lắp đặt!");
          return;
    }
},
GanKhuVuc() {
    this.dataPopupKhuVuc.hdtb_id = Number(this.hdtb_id);
    this.dataPopupKhuVuc.donviql_id = 0;
    this.$refs.thayDoiKhuVucQuanLyThueBaoModal.showModal();
},
ClearForm()
{
    this.txtSDTLH = "";
    this.txtNhanVienYC = "";
    this.txtNhanVienTiepThi = "";
    this.txtLoaiKHLon = "";
    this.matb_visa = "";
    this.hdtb_id = 0;
    this.hdkh_id = 0;
    this.thuebao_id = 0;
    this.txtMaGD = "";
    this.txtTenTB = "";
    this.txtDiaChiLD = "";
    this.txtDiaChiLD_Cu = "";
    this.dichvuvt_id = 0;
    this.txtGhiChu = "";
    this.txtThongTinTra = "";
    this.chkNgayGV = false;
    this.chkNgayHT = false;
    this.options.danhsach_grid = [];
    this.txtKieuLapDat = "";
    this.options.danhsach_grid_nhanvien = [];
    this.button.hoancong= false
    this.phieu_id = 0
    this.in_tocdo_id = -1
    this.txtTongTien = "0"; //nhapt 06072015
},
async KichHoat()
{  
    const res =  await api.KichHoat(this.axios, {
            "vhdtb_id": this.hdtb_id,
            "vstatus": 5
      })
    let kq = res.data.data  ? res.data.data : 0
    if(res.data.error == '200' && res.data.error_code == 'BSS-00000000')
    {
        this.$toast.success("Đồng bộ dữ liệu lên công ty dọc thành công !");
        await this.HienThiDanhSachHDTB()
        for (var i = 0; i < this.danhsach_grid.length; i++)
        {
            if (this.danhsach_grid[i].hdtbid == this.hdtb_id)
            {
                this.$refs.danhsachphieu.setCurrentSelectedRow(i)
            }
        }
    }
},
async Lay_TT_ND()
{  
    const res =  await api.Lay_TT_ND(this.axios, {})
    return res.data.data ? res.data.data : []
},
async GiaoPhieu()
{  
    if(await this.KiemTra_DL() == false)
    {
        return
    }
    if(await this.CapNhat() == 0)
    {
        return
    }
    const res =  await api.lay_huonggiao_theo_luong_id(this.axios, {
         "luong_id": this.luong_id
    })
    let dtTemp = res.data.data ? res.data.data : []
    if(dtTemp.length > 0)
    {
        this.$router.push("/contract/handoverticket?vma_gd=" + this.txtMaGD
           +"&kieuid=1" 
           +"&vhuonggiao_id=" +dtTemp[0].huonggiao_id
           +"&hdtbid=" + this.hdtb_id
           +"&vtrangthaiphieu=0" 
           +"&luong_id=" + this.luong_id
      )
    }
},
async btnBanDoLD_Click()
{
      try {
        let long = await this.axios.post(
          "/web-hopdong/thanhly/thanh_ly_map_id",
          {
            id_neo: "kinhdo_ld",
            in_table: "css.diachi_hdtb",
            in_dk: "where hdtb_id=" + this.hdtb_id,
          }
        );
        let lat = await this.axios.post(
          "/web-hopdong/thanhly/thanh_ly_map_id",
          {
            id_neo: "vido_ld",
            in_table: "css.diachi_hdtb",
            in_dk: "where hdtb_id=" + this.hdtb_id,
          }
        );
        if (long.data.data != -1 && lat.data.data != -1)
            this.map = {
                lng: Number(long.data.data),
                lat: Number(lat.data.data),
            }
this.$refs.ModalChonToaDo.showModal();
        
      } catch (err) {
        this.$toast.error("Có lỗi xảy ra");
      }
},
XacNhanToaDo(data) {
    this.axios.post(
    "/web-thicong/khaibaocodinh/fn_capnhat_toado_hdtb",
    {
        "lat": data.lat,
        "lng": data.lng,
        "hdtb_id": this.hdtb_id,
        "mode": 1
    }
    );
},
async btnNDTH_Click()
{
    if (this.phieu_id > 0) {
        const res =  await api.update_nd_thuchien(this.axios, {
            "phieu_id":this.phieu_id,
            "nd_thuchien":thix.txtNoiDungTH
        })
        this.$toast.success("Cập nhật thành công!") 
        let ihdtb_id = hdtb_id
        await this.HienThiDanhSachHDTB()
        for (var i = 0; i < this.danhsach_grid.length; i++)
        {
            if (this.danhsach_grid[i].hdtbid == this.hdtb_id)
            {
                this.$refs.danhsachphieu.setCurrentSelectedRow(i)
            }
        }
    }
    else
    {
          this.$toast.error("Hãy chọn phiếu trước để nhập lý do tồn!");
          return;
    }
},
async KiemTra_DL()
{
    if(this.txtNgayGV == "" || this.txtNgayGV == null)
    {
        this.$toast.error("Hãy nhập ngày giao việc!!!");
        return false        
    }
    if(await this.PHAILAM("GIAOVIEC") == 1)
    {
        if(this.options.danhsach_grid_nhanvien.length <= 0)
        {
            this.$toast.error("Hãy nhập nhân viên thực hiện!!!");
            return false
        }
    }
    if(this.txtNgayHT == "" || this.txtNgayHT == null)
    {
        this.$toast.error("Hãy nhập ngày hoàn thành!!!");
        return false
    }    
    
    //if(moment(this.txtNgayGV).diff(moment(this.txtNgayHT),"hours") < 0) 
    if(this.txtNgayGV > this.txtNgayHT)
    {
        this.$toast.error("Ngày giao việc không được lớn hơn ngày hoàn thành!!!");
        return false
    }
    let ngaygv = this.txtNgayGV.substring(0,10)
    let ngayht = this.txtNgayHT.substring(0,10)
    this.ngay_sys = moment(new Date()).format('DD/MM/YYYY')
    console.log('ngaygv',ngaygv,'ngayht',ngayht,'ngay_sys', this.ngay_sys)
    // if(moment(this.ngay_sys).diff(moment(ngayht),"day") < 0) 
    if(this.ngay_sys < ngayht)
    {
        this.$toast.error("Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại!!!");
        return false          
    }
    // if(moment(this.ngay_sys).diff(moment(ngaygv),"day") < 0) 
    if(this.ngay_sys < ngaygv)
    {
        this.$toast.error("Bạn không được phép chọn ngày giao việc lớn hơn ngày hiện tại!!!");
        return false          
    }
    return true
},
async tsbtnHoanThienHS_Click()
{
    let ngayht =  moment(this.txtNgayHT).format('YYYYMM')
    let ngay_cn = moment(new Date()).format('YYYYMM')
    if(ngayht < ngay_cn)
    {
        this.$toast.error("Tháng hoàn thành không được nhỏ hơn tháng hiện tại !");
        return false          
    }
    let tt_nd = await this.Lay_TT_ND()
    let ar = []
    ar.push({
        "HDTB_ID": this.hdtb_id, 
        "LUONG_ID": this.luong_id,
        "PHIEU_ID": this.phieu_id, 
        "NHANVIEN_ID": await this.$root.token.getNhanVienID(),
        "NGAY_BG": this.txtNgayHT, 
        "LOAIHD_ID": this.ddlLoaiHD_PL, 
        "DONVI_ID": tt_nd.donvi_id, 
        "MAY_CN": tt_nd.may_cn, 
        "NGUOI_CN": tt_nd.ma_nd,
        "IP_CN": tt_nd.ip
    })
    const res =  await api.lay_huonggiao_theo_luong_id(this.axios, {
         "vds": ar
    })
    let kq = res.data.data ? res.data.data : ''
    if(kq == 1)
    {
        this.$toast.success("Cật nhật dữ liệu vào danh bạ thành công!")
        this.HienThiDanhSachHDTB()
    }
    else
    {
        this.$toast.error(kq)
    }
},
XuatPhieu()
{
    try {
            this.kt_thaydoi = false
            this.InPhieu()
            if (this.kt_thaydoi) {
                let matb = this.txtAccount.trim()
                this.HienThiDanhSachHDTB()
                for(var i = 0; i<this.danhsach_grid.length; i++)
                {
                    let s = ""
                    let s1 = ""
                    s = this.danhsach_grid[i].ma_tb
                    s1 = matb
                    if(s == s1)
                    {
                        this.$refs.danhsach_grid.setCurrentSelectedRow(i)
                        return
                    }
                }
            }
        } 
        catch (error) {
            console.log(error)
            this.$root.$toast.error('Có lỗi: ' + error.message)
        }
},
async fn_tt_hd_thuebao(vhdtb_id) {
    let result = ''
    const res =  await api.fn_tt_hd_thuebao(this.axios, {
          "type": 1,
          "param": vhdtb_id
    })
        if (res && res.data && res.data.error_code === 'BSS-00000000') {
          result = res.data.data
        }
      return result
    },
async InPhieu () {
    try {
        let ds_phieu = []
        let vhdkhid = this.fn_tt_hd_thuebao(this.hdtb_id)
        if (vhdkhid == '-1') {
            this.$root.$toast.warning('Không tìm thấy thông tin khách hàng. Bạn hãy kiểm tra lại!')
            return
        }
        if (this.$refs["danhsach_grid"].getSelectedRecords().length == 0) {
            this.$root.$toast.warning('Hãy chọn phiếu!')
            return
        }
        let n_hdkh_id= []
        let n_hdtb_id= []
        let n_phieu_id= []
        let dd = this.$refs["danhsach_grid"].getSelectedRecords()
        for(var i=0; i< dd.length; i++)
        {
            n_hdkh_id += dd[i].hdkh_id+ ","
            n_hdtb_id += dd[i].hdtb_id+ ","
            n_phieu_id += dd[i].phieu_id+ ","
        }
        this.modelIn.n_phieu_id = n_hdkh_id.substring(0, n_hdkh_id.length - 1);
        this.modelIn.nvth_id =this.nhanvien_tc_id
        this.modelIn.cv_thuchien =this.congviec_th
        this.modelIn.n_phieu_id = n_hdkh_id.substring(0, n_phieu_id.length - 1);
        this.modelIn.n_hdtb_id = n_hdkh_id.substring(0, n_hdtb_id.length - 1);
        this.modelIn.huonggiao_id =this.huonggiao_id
        this.modelIn.tentram_tb =this.huonggiao_id
        //console.log('this.modelIn',this.modelIn)
        this.$refs.PopupInBB.showModal()
    } catch (error) {
        this.$root.$toast.error('Có lỗi khi in phiếu: ' + error.message)
    }
  },
  tsbtnVatTu_Click(){
    if (this.hdtb_id != 0){
        //WinUI.WinUIDieuHanhThiCong.frmVatTuThueBao
        this.inputDataFormVattuTB.phieu_id=this.phieu_id
        this.inputDataFormVattuTB.hdtb_id=this.hdtb_id
        //LoaiHopDong.THAY_DOI_TOCDO_ADSL
        this.inputDataFormVattuTB.loaihd_id=this.loaihd_id
        this.inputDataFormVattuTB.thuebao_id=this.thuebao_id
        setTimeout(()=>{
            this.$refs.vatTuThuebao.showModal()
        }, 500)
    }else{
        this.$toast.error('Bạn chưa chọn phiếu!')
    }
  },
    clickButton(key) {
    if (key == "laytt") {
        this.HienThiDanhSachHDTB()
    }
    if (key == "hoancong") {
        this.HoanCong()
    }           
    if (key == "capnhat") {
        this.ChapNhan();
    }    
    if (key == "kichhoat") {
        this.KichHoat();
    } 
    if (key == "giaophieu") {
        this.GiaoPhieu();
    } 
    if (key == "giaoviec") {
        this.GiaoViec();
    }
    if (key == "vattu") {    
        this.CapVatTu();
    }
    if (key == "cosohatang") {    
        if (this.hdtb_id == 0 || this.phieu_id == 0)
        {
            this.$toast.error("Bạn chưa chọn phiếu!");
            return;
        }
        this.$refs.modalCSHT.showModal();
    }
    if (key == "tonphieu") {
        this.GiaoPhieuTon();
    }
    if (key == "chuyento") {
        this.ChuyenTo();
    }
    if (key == "traphieu") {
        this.TraPhieu();
    }
    if (key == "xuatphieu") {
        this.XuatPhieu();
    }
    if (key == "phoihopthicong") {
        
        this.$refs.popup_PHTC.showModal();
    }
 
    if (key == "henlapdat") {
        this.HenLapDat();
    }
    if(key == "gankhuvuc")
    {
          this.GanKhuVuc()
    
    }
    if(key=='tsbtnVatTu'){
        //this.tsbtnVatTu_Click()
        this.CapVatTu()
    }
    }
  },
};
</script>
<style>
li.non-active a {
  color: #d3d3d3 !important;
}
.traphieu .page-content {
  position: unset;
}
.modal-dialog {
  border-radius: 0px !important;
  width: 100% !important;
  min-width: 720px !important;
}
</style>