<template>
  <div>
    <vue-bread-crumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li @click="clickButton('nhapmoi')" :class="this.button.nhapmoi ? 'active' : 'non-active'">
          <a> <span class="icon one-file-plus"></span>Nhập mới </a>
        </li>
        <li @click="clickButton('capnhat')" :class="this.button.capnhat ? 'active' : 'non-active'">
          <a> <span class="icon one-reload1"></span>Cập nhật</a>
        </li>

        <li v-if="this.button.khoaphieu" @click="clickButton('khoaphieu')" :class="this.button.khoaphieu ? 'active' : 'non-active'">
          <a> <span class="icon one-file-lock"></span>Khóa phiếu</a>
        </li>

        <li v-if="this.button.sendmail" @click="clickButton('sendmail')" :class="this.button.sendmail ? 'active' : 'non-active'">
          <a> <span class="icon one-file-lock"></span>Gửi mail</a>
        </li>
      </ul>
    </vue-nav>

    <div class="page-content">
      <div class="box-form">
        <div class="legend-title">Thông tin tra cứu</div>
        <div class="row">
          <div class="col-sm-9 col-12">
            <div class="info-row">
              <div class="key w100">Mã giao dịch</div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn" @click.prevent="btnLayMaGD">
                    <span class="-ap icon-more_horiz"></span>
                  </button>
                  <input type="text" ref="maGDRef" v-model="txtMaGD" v-on:keyup="keymonitor" class="form-control highlight" />
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Mẫu số:</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt v-model="ddlMauSo" :options="this.options.mauso">
                    <option disabled value="0">Select one</option>
                  </SelectExt>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Kiểu điều chỉnh</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt v-model="ddlKieuDieuChinh" :options="this.options.kieudieuchinh" :disabled="disabled_KieuDieuChinh">
                    <option disabled value="0">Select one</option>
                  </SelectExt>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w120">Thao tác</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt v-model="ddlThaoTac" :options="this.options.thaotac">
                    <option disabled value="0">Select one</option>
                  </SelectExt>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w120">Ký hiệu (Serial No):</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt v-model="ddlKyHieu" :options="this.options.kyhieu">
                    <option disabled value="0">Select one</option>
                  </SelectExt>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w120">Số hoá đơn:</div>
              <div class="value">
                <input type="number" v-model="txtSoHoaDon" class="form-control" />
              </div>
            </div>
          </div>
        </div>
        <div class="legend-title mart20">Thông tin hoá đơn</div>
        <div class="row">
          <div class="col-sm-9 col-12">
            <p>Tên hoá đơn: {{ lblTenHoaDon }}</p>
            <p>Mã số thuế: {{ lblMaSoThue }}</p>
            <p>Tên đơn vị: {{ lblTenDonVi }}</p>
            <p>Địa chỉ: {{ lblDiaChi }}</p>
            <div class="info-row">
              <div class="key" style="width: 270px">Tên khách hàng (Customer's name) <span class="red">(*)</span></div>
              <div class="value">
                <input type="text" ref="tenKHRef" v-model="txtTenKhachHang" class="form-control" :disabled="disabledTenKH" />
              </div>
            </div>
            <div class="info-row">
              <div class="key" style="width: 270px">Địa chỉ:</div>
              <div class="value">
                <input type="text" v-model="txtDiaChi" class="form-control" :disabled="disabledDiaChi" />
              </div>
            </div>
            <div class="row">
              <div class="col-sm-8 col-12">
                <div class="info-row">
                  <div class="key" style="width: 270px">Hình thức thanh toán (Kind of Payment) <span class="red">(*)</span></div>
                  <div class="value">
                    <input type="text" v-model="txtHinhThucThanhToan" class="form-control" :disabled="disabledHTTT" />
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key">Mã số: {{ lblMaSo }}</div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-12 col-12">
                <div class="info-row">
                  <div class="key" style="width: 270px">Biên bản đính kèm:</div>

                  <div class="value">
                    <div class="input-more-button -right">
                      <label id="btnFile" class="btn" for="upload-file" style="pointer-events: block">
                        <span class="-ap icon-more_horiz"></span>
                        <input type="file" ref="files" class="hidden" id="upload-file" @change="importFile" />
                      </label>
                      <input type="text" class="form-control" v-model="txtBienBanDinhKem" :disabled="disabledBienBanDinhKem" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <p>Mẫu số: <span class="red">(*)</span> {{ lblMauSo }}</p>
            <p>Ký hiệu (Serial No) <span class="red">(*)</span> {{ lblKyHieu }}</p>
            <p>Số (No): {{ lblSo_No }}</p>
            <p>&nbsp;</p>
            <div class="info-row">
              <div class="key w100">Mã số thuế</div>
              <div class="value">
                <input type="text" v-model="txtMaSoThue" class="form-control" :disabled="disabledMST" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Số điện thoại</div>
              <div class="value">
                <input type="text" v-model="txtSoDienThoai" class="form-control" :disabled="disabledSDT" />
              </div>
            </div>
            <div class="info-row marb10">
              <div class="key w100">Tháng hoá đơn: {{ lblThangHoaDon }}</div>
              <div class="value"></div>
            </div>
            <div class="info-row">
              <div class="key w100">Email</div>
              <div class="value">
                <input type="text" v-model="txtEmail" :disabled="disabledEmail" class="form-control" />
              </div>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key" style="width: 270px">Ghi chú hoá đơn:</div>
          <div class="value">
            <div class="input-number-custom">
              <input type="text" v-model="txtGhiCHuHoaDon" :disabled="disabledGhiCHuHoaDon" class="form-control" />
            </div>
          </div>
        </div>
        <div class="table-content">
          <ejs-grid
            ref="grid_danhsach"
            :load="load"
            :allowResizing="true"
            :actionComplete="onActionComplete"
            width="100%"
            :dataSource="this.grid_danhsach"
            locale="vi-VN"
            :allowSorting="true"
            :allowFiltering="true"
            :allowPaging="true"
            :pageSettings="{
              pageSize: 10,
              pageSizes: [10, 50, 100, 200]
            }"
            :gridLines="1"
            :editSettings="{ allowEditing: true, mode: 'Normal' }"
            :commandClick="commandClick"
          >
            <e-columns>
              <e-column headerTextAlign="center" textAlign="left" field="Commands" headerText="Xóa" width="80" :commands="commands"></e-column>
              <e-column headerTextAlign="center" textAlign="center" field="STT" headerText="STT" allowSorting="true" allowFiltering="true" width="80"></e-column>
              <e-column headerTextAlign="center" textAlign="left" field="ProdName" headerText="Dịch vụ sử dụng" allowSorting="true" allowFiltering="true"></e-column>
              <e-column headerTextAlign="center" textAlign="left" field="ProdUnit" headerText="Đơn vị tính" allowSorting="true" allowFiltering="true" width="120"></e-column>
              <e-column headerTextAlign="center" textAlign="right" field="ProdQuantity" headerText="Số lượng" allowSorting="true" allowFiltering="true" width="120"></e-column>

              <e-column headerTextAlign="center" textAlign="right" field="ProdPrice" headerText="Đơn giá" type="Number" format="N" allowSorting="true" allowFiltering="true"></e-column>
              <e-column type="Number" format="N" headerTextAlign="center" textAlign="right" field="Total" headerText="Thành tiền (Trước thuế)" allowSorting="true" allowFiltering="true" v-if="this.showgrid.Total"></e-column>

              <e-column type="Number" format="N" headerTextAlign="center" textAlign="right" field="VATRate" headerText="VAT" allowSorting="true" allowFiltering="true" width="70" v-if="this.showgrid.Vat"></e-column>

              <e-column type="Number" format="N" headerTextAlign="center" textAlign="right" field="VATAmount" headerText="Tiền thuế" allowSorting="true" allowFiltering="true" v-if="this.showgrid.VATAmount"></e-column>

              <e-column type="Number" format="N" headerTextAlign="center" textAlign="right" field="Amount" headerText="Thành tiền" allowSorting="true" allowFiltering="true" v-if="this.showgrid.Amount"></e-column>
              <e-column type="Number" format="N" headerTextAlign="center" textAlign="right" field="Amount" headerText="Thành tiền (Sau thuế)" allowSorting="true" allowFiltering="true" v-if="this.showgrid.Amount_sauthue" :customAttributes="{ class: 'customcss' }"></e-column>
            </e-columns>
          </ejs-grid>
        </div>

        <div class="row">
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key" style="width: 170px">Tổng tiền chịu thuế 10% :</div>
              <div class="value">
                <!-- <input type="text" v-model="txtTongTienChiuThue10" :disabled="true" class="form-control"> -->
                <vue-numeric class="form-control red tright" style="width: 100%" separator="," v-model="txtTongTienChiuThue10" :disabled="disabledTongTienChiuThue10" placeholder="0" output-type="Number"></vue-numeric>
              </div>
            </div>
          </div>

          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key" style="width: 170px">Tổng tiền chịu thuế 5% :</div>
              <div class="value">
                <!-- <input type="text" v-model="txtTongTienChiuThue5" :disabled="true" class="form-control"> -->
                <vue-numeric class="form-control red tright" separator="," v-model="txtTongTienChiuThue5" :disabled="disabledTongTienChiuThue5" placeholder="0" output-type="Number"></vue-numeric>
              </div>
            </div>
          </div>

          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key" style="width: 170px">Tổng tiền chịu thuế 0% :</div>
              <div class="value">
                <!-- <input type="text" v-model="txtTongTienChiuThue0" :disabled="true" class="form-control"> -->
                <vue-numeric class="form-control red tright" separator="," v-model="txtTongTienChiuThue0" :disabled="disabledTongTienChiuThue0" placeholder="0" output-type="Number"></vue-numeric>
              </div>
            </div>
          </div>

          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key" style="width: 170px">Cộng tiền dịch vụ:</div>
              <div class="value">
                <vue-numeric class="form-control red tright" separator="," v-model="txtCongTienDV" :disabled="disabledCongTienDV" placeholder="0" output-type="Number"></vue-numeric>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key" style="width: 170px">Tổng tiền thuế 10%:</div>
              <div class="value">
                <vue-numeric class="form-control red tright" separator="," v-model="txtTongTienThue10" :disabled="disabledTongTienThue10" placeholder="0" output-type="Number"></vue-numeric>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key" style="width: 170px">Tổng tiền thuế 5%:</div>
              <div class="value">
                <vue-numeric class="form-control red tright" separator="," v-model="txtTongTienThue5" :disabled="disabledTongTienThue5" placeholder="0" output-type="Number"></vue-numeric>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key" style="width: 170px">Tổng tiền không thuế:</div>
              <div class="value">
                <vue-numeric class="form-control red tright" separator="," v-model="txtTongTienKhongThue" :disabled="disabledTongTienKhongThue" placeholder="0" output-type="Number"></vue-numeric>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key" style="width: 170px">Tiền thuế GTGT:</div>
              <div class="value">
                <!-- <input type="text" v-model="txtTienTHueGTGT" class="form-control red tright" value="0" :disabled="disabledTienThueGTGT"> -->
                <vue-numeric class="form-control red tright" separator="," v-model="txtTienTHueGTGT" :disabled="disabledTienThueGTGT" placeholder="0" output-type="Number"></vue-numeric>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key" style="width: 290px">Thẻ mở rộng (Dùng cho các tỉnh có thẻ extra) :</div>
              <div class="value">
                <input type="text" v-model="txtTheMoRong" class="form-control" :disabled="disabledTheMoRong" />
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key" style="width: 170px">Tổng tiền không chịu thuế:</div>
              <div class="value">
                <!-- <input type="text" v-model="txtTienTHueGTGT" class="form-control red tright" value="0" :disabled="disabledTienThueGTGT"> -->
                <vue-numeric class="form-control red tright" separator="," v-model="txtTongTienKhongChiuThue" :disabled="disabledTongTienChiuKhongThue" placeholder="0" output-type="Number"></vue-numeric>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key" style="width: 170px">Tổng cộng tiền thanh toán:</div>
              <div class="value">
                <!-- <input type="text" v-model="txtTongCongTienThanhToan" class="form-control red tright" value="0" :disabled="disabledTongCongTienThanhToan"> -->
                <vue-numeric class="form-control red tright" separator="," v-model="txtTongCongTienThanhToan" :disabled="disabledTongCongTienThanhToan" placeholder="0" output-type="Number"></vue-numeric>
              </div>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key" style="width: 290px">Số tiền viết bằng chữ</div>
          <div class="value">
            <input type="text" v-model="txtTienVietBangChu" :disabled="disabledTienVietBangChu" class="form-control" />
          </div>
        </div>
      </div>
    </div>

    <PopupHDDT ref="popup_HDDT" @chapnhan="acceptHDDT" />
    <PopupTraCuuHDDT ref="popup_TraCuuHDDT" @chapnhan="accep_TraCuutHDDT" :vma_gd="this.vma_gd" :vtt="this.vtt" />

    <popup-hieu-chinh-hddt ref="popupHieuChinhHDDTRef" :html="this.html" />

    <!-- <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="popupHieuChinhHDDTRef"
        :header='"Hiệu chỉnh hóa đơn điện tử"'
        showCloseIcon=true
        width='60%'
        height='800'
        target='.main-wrapper'
    >
        <popup-hieu-chinh-hddt :html="this.html"/>
    </ejs-dialog> -->
  </div>
</template>
<script>
// Clone goi ham xuat hoa don gach no form thanh toan
import PopupHieuChinhHddt from '@/modules/Payment/SelfManageEinvoice/popups/PopupHieuChinhHddt'
import VueInput from '@/modules/search/subscriber/components/form/VueInput.vue'
import api from './api'
import api2 from './api/indexPayment.js'
import define from './define'
import PopupHDDT from './PopupHDDT.vue'
import PopupTraCuuHDDT from './Popup_TraCuuHDDT.vue'
import moment from 'moment'
import xml2js from 'xml2js'
import eventBus from './evenbus/eventBus'

import { Sort, Filter, Resize, Group, Freeze, CommandColumn, ExcelExport, Edit, Aggregate, Page, Toolbar } from '@syncfusion/ej2-vue-grids'

import { L10n } from '@syncfusion/ej2-base'

L10n.load({
  'vi-VN': {
    grid: {
      EmptyRecord: 'Không có bản ghi nào để hiển thị',
      Item: 'Bản ghi',
      Items: 'Bản ghi',
      FilterbarTitle: ''
    }
  }
})

export default {
  name: 'eInvoice',
  data() {
    return {
      ...define,
      commands: [
        {
          buttonOption: { content: 'Xóa', cssClass: 'custombutton e-info' }
        }
      ],

      html: '',
      disabled_KieuDieuChinh: true,
      ImportAndPublishInvErr: '',
      grid_danhsach: [],
      _ma_gd: '',
      vma_gd: '',
      kq2_errorCode:'0',
      customerInfo: {
        txtTenKhachHang: '',
        vma_gd: '',
        txtMaSoThue: '',
        txtDiaChi: '',
        txtEmail: ' ',
        txtSoDienThoai: '',
        txtMaSoThue: ''
      },
      vhdkh_id: 0,
      txtMaGD: '',
      ddlMauSo: 0,
      ddlKieuDieuChinh: '',
      ddlThaoTac: '6',
      ddlKyHieu: '',
      txtSoHoaDon: '',
      txtTenKhachHang: '',
      txtDiaChi: '',
      txtHinhThucThanhToan: '',
      txtBienBanDinhKem: '',
      txtMaSoThue: '',
      txtSoDienThoai: '',
      txtEmail: '',
      txtGhiCHuHoaDon: '',
      txtTHueGTGT: '10',
      txtCongTienDV: '',
      txtTienTHueGTGT: '',
      txtTheMoRong: '',
      txtTongCongTienThanhToan: '',
      txtTienVietBangChu: '',

      txtTongTienChiuThue10: '0',
      txtTongTienChiuThue5: '0',
      txtTongTienChiuThue0: '0',
      txtTongTienThue10: '0',
      txtTongTienThue5: '0',
      txtTongTienKhongThue: '0',
      txtTongTienKhongChiuThue: '0',

      disabledTenKH: false,
      disabledDiaChi: false,
      disabledHTTT: false,
      disabledMST: false,
      disabledSDT: true,
      disabledBienBanDinhKem: false,
      disabledEmail: false,
      disabledGhiCHuHoaDon: false,
      disabledTheMoRong: false,
      disabledTienVietBangChu: false,
      disabledCongTienDV: true,
      disabledTienThueGTGT: true,
      disabledTongCongTienThanhToan: true,
      disabledTongTienKhongThue: true,
      disabledTongTienChiuKhongThue: true,
      disabledTongTienThue5: true,
      disabledTongTienThue10: true,
      disabledTongTienChiuThue0: true,
      disabledTongTienChiuThue5: true,
      disabledTongTienChiuThue10: true,

      // txtNgayGV: moment(new Date()).format('DD/MM/YYYY h:mm a'),
      // txtNgayHT: moment(new Date()).format('DD/MM/YYYY h:mm a'),

      showgrid: {
        Amount: false,
        Amount_sauthue: true,
        Total: true,
        Vat: true,
        VATAmount: true
      },

      options: {
        mauso: [],
        kieudieuchinh: [],
        thaotac: [],
        kyhieu: [],

        danhsach_grid_nhanvien: [],
        cols_ds_nv: [
          {
            fieldName: 'ten_nv',
            headerText: 'Account',
            allowSorting: true,
            autoFit: true
          },
          {
            fieldName: 'so_dt',
            headerText: 'Account',
            allowSorting: true,
            autoFit: true
          },
          {
            fieldName: 'nhiemvu',
            headerText: 'Account',
            allowSorting: true,
            autoFit: true
          },
          {
            fieldName: 'ghichu',
            headerText: 'Account',
            allowSorting: true,
            autoFit: true
          }
        ]
      },
      Tag: 1,
      tieude: '',
      noidung: '',
      flag: 0,

      smtpServer: '',
      smtpUser: '',
      smtpPass: '',
      smtpPort: '',
      smtpAlias: '',

      vkhongguimail_td: 0,
      vchophep_gdv_dc_thongtin: 0,
      vchophep_gdv_dc_thongtin_hd_moi: 0,
      vkhongcho_gdv_huy_hddt_khac_thanh: 0,
      vthem_extra: false,
      vlamtron_dongia: false,
      vtt_hddt_hd: 0,
      vtt_hddt_hd_user: 0,
      payment_pattern: '',
      payment_serial: '',
      ktra_quyen: 0,
      kq_capnhat: [],
      lblTenHoaDon: '',
      lblMaSoThue: '',
      lblTenDonVi: '',
      lblDiaChi: '',
      lblMaSo: '',
      lblMauSo: '',
      lblKyHieu: '',
      lblSo_No: '',
      lblThangHoaDon: '',

      quytrinh_id_2: 0,
      huonggiaotn_id: 0,
      vphieutt_id: '',
      khoa: 0,
      vnhanvien_id: 0,
      vpattern_id: 0,
      vseri_id: 0,
      vsoseri: 0,
      vfkey: '',
      Fkey: '',
      da_xuathd: false,
      t_tien_tt: 0,
      bdhddt_cha_id: 0,
      vstk: '',
      vdiachi_tt: '',
      vfax: '',
      vnguoi_dd: '',
      vloai_kh: '',
      vsodt_kh: '',
      vloaihd_id: '',
      t_tien_edited: 0,
      t_tien_load: 0,
      t_tien_thoaitra: 0,
      i_thoaitra_tmp: 0,
      tiengoc: 0,
      array_byte: [],
      BDHDDT_ID: 0,
      invToken: '',
      fkey_new: '',
      vtt: false,
      dsBDHDDT: [],
      dsBDHDDTCT: [],
      ds_thoai: [],
      objHoaDonDienTu: {},
      objHoaDonDienTuEdit: {},
      dtBienDong: [],
      dtHopDong: [],
      ds_kt: [],
      ds_tb: [],
      dsTS: [],
      _dsGoi: [],
      js_thoaitra_out: [],
      kt_tongtien: 0,
      thamso: 0,
      h_doituong_id: 0,
      handatcho: '',
      first: true,
      _thaythe: 0,
      _dieuchinh: 0,
      vpayment: '',
      kieuld_id: 0,
      vxuat_hd_nhieumucthue: false,
      dslamId: 0,
      bien_kqua: '',
      json_update: [],
      dsHDKH: [],
      dsHDTB: [],
      dsHDTBGOI: [],
      hdkh_id_huygoi_mytv: 0,
      hdtb_id_huygoi_mytv: 0,

      thaydoi_doituong_visa: 0,
      doicongacccountvisa: 0,
      doitocdoadsl: 0,
      vasc_status: 0,

      newContent() {
        return {
          ArisingDate: '',
          InvoiceName: '',
          InvoicePattern: '',
          SerialNo: '',
          InvoiceNo: '',
          Kind_of_Payment: '',
          ComName: '',
          ComTaxCode: '',
          ComAddress: '',
          ComPhone: '',
          ComBankNo: '',
          CusCode: '',
          CusName: '',
          CusTaxCode: '',
          CusPhone: '',
          CusAddress: '',
          CusBankName: '',
          Total: '',
          VAT_Amount: '',
          Amount: '',
          Amount_words: '',
          Buyer: '',
          KindOfService: '',
          VAT_Rate: '',
          Discount_Rate: '',
          Discount_Amount: '',
          EmailDeliver: '',
          Extra: '',
          SignDate: '',
          Products: [],
          MaThanhToan: ''
        }
      },
      BD_HDDT_DATA() {
        return {
          THANHTOAN_LOI: 0,
          SERI_MOI: '',
          SERI_CU: '',
          LOAIHOADON_MOI: '',
          LOAIHOADON_CU: '',
          TONGTIEN: 0,
          PHIEUTT_ID: 0,
          HDKH_ID: 0,
          FILE_DINHKEM: null,
          SO_HD_CU: '',
          GHICHU: '',
          NGAY_CN: null,
          NGUOI_CN: '',
          THONGTINHD_MOI: '',
          THONGTINHD_CU: '',
          KIEU_BD: 0,
          MA_TT: '',
          BDHDDT_ID: 0,
          TIENBANGCHU: '',
          MAY_CN: '',
          TIENGOC: 0,
          SO_HD_MOI: '',
          EMAIL: '',
          HINHTHUC_TT: '',
          LOAIHOADON_MOI_ID: 0,
          NHANVIEN_ID: 0,
          DONVI_ID: 0,
          FKEY: '',
          BDHDDT_CHA_ID: 0,
          SDT: '',
          TIEN: 0,
          MST: '',
          TEN_TT: '',
          KIEU_ID: 0,
          SERI_MOI_ID: 0,
          TYLE_VAT: 0,
          TRANGTHAI: 0,
          NGUOI_TD: '',
          NGAY_TD: null,
          DIACHI_TT: '',
          VAT: 0
        }
      },

      BD_HDDT_CT_DATA() {
        return {
          THANHTIEN: '',
          SOLUONG: '',
          DONVITINH: '',
          NOIDUNG: '',
          STT: '',
          BDHDDT_ID: '',
          DONGIA: ''
        }
      },

      Product() {
        return {
          Code: '',
          Remark: '',
          Total: 0,
          ProdName: '',
          ProdUnit: '',
          ProdQuantity: 0,
          ProdPrice: 0,
          Discount: 0,
          DiscountAmount: 0,
          VATRate: 0,
          VATAmount: 0,
          Amount: 0
        }
      },

      ProductWithSTT() {
        return {
          STT: '',
          Code: '',
          Remark: '',
          Total: 0,
          ProdName: '',
          ProdUnit: '',
          ProdQuantity: 0,
          ProdPrice: 0,
          Discount: 0,
          DiscountAmount: 0,
          VATRate: 0,
          VATAmount: 0,
          Amount: 0
        }
      },

      HD_KHACHHANGRow() {
        return {
          KHACHHANG_ID: '',
          MA_KH: '',
          TEN_KH: '',
          DIACHI_KH: '',
          SO_DT: '',
          SO_FAX: '',
          EMAIL: '',
          WEBSITE: '',
          NGAY_SN: '',
          SO_GT: '',
          NGAYCAP: '',
          NOICAP: '',
          GHICHU: '',
          KHLON_ID: '',

          HDKH_ID: '',
          MA_GD: '',
          MA_HD: '',
          NGUOI_DD: '',
          CHUCDANH_DD: '',
          NGAYLAP_HD: '',
          DONVI_ID: '',
          LOAIGT_ID: '',
          NHANVIEN_ID: '',
          LOAIHD_ID: '',
          BOSUNGTB_ID: '',
          MAY_CN: '',
          NGAY_CN: '',
          NGUOI_CN: '',
          IP_CN: '',
          KH_KT3: '',
          MST_KH: '',
          HOKHAU: '',
          LOAIKH_ID: '',
          NOICAP_HK: '',
          NGAY_HK: '',
          NGAY_YC: '',
          GIOITINH: '',
          DANTOC_ID: '',
          QUOCTICH_ID: '',
          NGANHNGHE_ID: '',
          DIACHI_NDD: '',
          NGAYHETHAN_GT: '',
          MST: '',
          STK: '',
          NGANHANG_ID: '',
          CTV_ID: '',
          TNC1_ID: '',
          TNC2_ID: '',
          TNC3_ID: '',
          DACTRUNG: '',
          KIEUHD_ID: '',
          HTTTHI_ID: '',
          HDKH_CHA_ID: '',
          PHANLOAIKH_ID: '',
          NHANVIENGT_ID: '',
          KENHTN_ID: '',
          MA_DUAN: '',

          PHIEUCSKH_ID: '',
          TNC4_ID: '',
          MA_KH_VNP: '',
          TEN_KH_CU: '',
          VMATT_NEO: '',
          LOAIKH_ID_VTN: '',
          LOAIKH_ID_HDDT: ''
        }
      },

      HD_THUEBAORow() {
        return {
          HDTB_ID: '',
          THUEBAO_ID: '',
          MA_TB: '',
          TEN_TB: '',
          DIACHI_TB: '',
          GHICHU: '',
          HDKH_ID: '',
          HDTT_ID: '',
          KIEULD_ID: '',
          TTHD_ID: '',
          LOAITB_ID: '',
          DOITUONG_ID: '',
          DICHVUVT_ID: '',
          DONVI_ID: '',
          NGAY_HT: '',
          MAY_CN: '',
          NGAY_CN: '',
          NGUOI_CN: '',
          IP_CN: '',
          SOLUONG: '',
          TBHD_PL: '',
          DIACHI_LD: '',
          NGAY_SN: '',
          KIEU_TB: '',
          STATUS: '',
          DONVI_DAU_ID: '',
          DONVI_CUOI_ID: '',
          MUCUOCTB_ID: '',
          MUCUOC_TB: '',
          THUEBAO_CHA_ID: '',
          QUYTRINH_ID: '',
          CHUYENMANG: '',
          TG_THUE_TU: '',
          TG_THUE_DEN: '',
          GOIKT_ID: '',
          SOTHANG_CK: '',
          NGAY_HH_TD: '',
          NHACC_CU_ID: '',
          DOITUONG_CT_ID: '',
          HTCM_ID: '',
          TRANGTHAIYC_ID: ''
        }
      },

      HDTB_GOI_DADVRow() {
        return {
          HDTB_ID: '',
          GOI_ID: '',
          NHOMTB_ID: '',
          CHUNHOM: '',
          THANG_HUONG: '',
          HTCM_ID: '',
          KIEU_YC: ''
        }
      }
    }
  },
  components: {
    PopupHDDT,
    PopupTraCuuHDDT,
    VueInput,
    PopupHieuChinhHddt
  },
  provide: {
    grid: [Sort, Filter, Resize, Group, Freeze, CommandColumn, ExcelExport, Edit, Aggregate, Toolbar, Page, CommandColumn]
  },

  created() {
    console.log('version:indexpayment', 10001)
    this.objHoaDonDienTu = this.newContent()
    this.objHoaDonDienTuEdit = this.newContent()

    try {
      this.Tag = this.$route.query.tag
      if (this.Tag == null) {
        this.Tag = 1
      }
    } catch (e) {
      this.Tag = 1
    }

    this.Xuat_HDDT_LOAD()
  },

  watch: {
    ddlMauSo(newval) {
      if (newval != 0) this.Change_MauSo()
    },
    ddlThaoTac(newval) {
      this.ddlThaoTac = newval
      if (newval == 2) {
        this.disabled_KieuDieuChinh = false
      } else {
        this.disabled_KieuDieuChinh = true
      }

      this.ThaoTac_Changed()
    }
  },

  methods: {
    load: function() {
      this.$refs.grid_danhsach.ej2Instances.element.addEventListener('mouseup', function(e) {
        var instance = this.ej2_instances[0]

        if (e.target.classList.contains('e-rowcell')) {
          if (instance.isEdit) instance.endEdit()
          let index = parseInt(e.target.getAttribute('Index'))
          instance.selectRow(index)
          instance.startEdit()
        }
      })
    },

    onActionComplete: function(args) {
      if (args.requestType === 'save') {
        this.grid_danhsach[args.rowIndex].ProdName = Object.values(args.data)[4]
        this.grid_danhsach[args.rowIndex].ProdUnit = Object.values(args.data)[5]
        this.grid_danhsach[args.rowIndex].ProdQuantity = Object.values(args.data)[6]
        this.grid_danhsach[args.rowIndex].ProdPrice = Object.values(args.data)[7]
        this.grid_danhsach[args.rowIndex].VATRate = Object.values(args.data)[10]
        this.grid_danhsach[args.rowIndex].Total = Object.values(args.data)[11]

        if (this.grid_danhsach[args.rowIndex].VATRate < 0) {
          this.grid_danhsach[args.rowIndex].VATRate = 0
        }

        if (this.grid_danhsach[args.rowIndex].Total == 0 && this.grid_danhsach[args.rowIndex].ProdQuantity != 0 && this.grid_danhsach[args.rowIndex].ProdPrice != 0) {
          this.grid_danhsach[args.rowIndex].Total = Math.round(Number(Object.values(args.data)[6]) * Number(Object.values(args.data)[7]), 0)
        }

        let _total = Number(this.grid_danhsach[args.rowIndex].Total)
        let _vat_amout = Number(this.grid_danhsach[args.rowIndex].VATAmount)

        if (_total != 0 && _vat_amout != 0) {
          this.grid_danhsach[args.rowIndex].VATAmount = Math.Round((_total * _vat_amout) / 100, 0)
        } else {
          this.grid_danhsach[args.rowIndex].VATAmount = 0
        }

        this.grid_danhsach[args.rowIndex].Amount = _total + _vat_amout

        let tong_total = 0
        let tong_vatamount = 0
        let vat0 = 0
        let vat5 = 0
        let tien5 = 0
        let vat10 = 0
        let tien10 = 0
        for (var i = 0; i < this.grid_danhsach.length; i++) {
          tong_total = tong_total + Number(this.grid_danhsach[i].Total)
          tong_vatamount = tong_vatamount + Number(this.grid_danhsach[i].VAT_Amount)
          if (this.grid_danhsach[i].VATRate == 0) {
            vat0 = vat0 + Number(this.grid_danhsach[i].Total)
          }
          if (this.grid_danhsach[i].VATRate == 5) {
            tien5 = tien5 + Number(this.grid_danhsach[i].Total)
            vat5 = vat5 + Number(this.grid_danhsach[i].VATAmount)
          }
          if (this.grid_danhsach[i].VATRate == 10) {
            tien10 = tien10 + Number(this.grid_danhsach[i].Total)
            vat10 = vat10 + Number(this.grid_danhsach[i].VATAmount)
          }
        }
        this.objHoaDonDienTuEdit.Total = Number(tong_total)
        this.objHoaDonDienTuEdit.VAT_Amount = Number(tong_vatamount)
        this.objHoaDonDienTuEdit.Amount = Number(tong_total) + Number(tong_vatamount)

        this.txtTongTienChiuThue0 = vat0
        this.txtTongTienChiuThue5 = tien5
        this.txtTongTienThue5 = vat5
        this.txtTongTienChiuThue10 = tien10
        this.txtTongTienThue10 = vat10

        this.txtCongTienDV = Number(this.objHoaDonDienTuEdit.Total)
        this.txtTienTHueGTGT = Number(this.objHoaDonDienTuEdit.VAT_Amount)

        if (this.objHoaDonDienTuEdit.Total == 0) {
          this.txtCongTienDV = 0
          this.txtTienTHueGTGT = 0
          this.txtTongCongTienThanhToan = 0
          this.objHoaDonDienTuEdit.Amount = 0
        }

        this.doi_so_sang_chu(this.txtTongCongTienThanhToan)
        this.t_tien_edited = Number(this.txtTienVietBangChu)
      }
    },

    HienThi_GiaoDien_TT78() {
      this.showgrid.Amount = false
      this.showgrid.Amount_sauthue = true
      this.showgrid.Total = true
      this.showgrid.Vat = true
      this.showgrid.VATAmount = true
    },

    //đồng bộ test
    async get_app_config() {
      const res = await api.get_app_config(this.axios, {
        keyname: 'DONGBO_TEST'
      })
      let kq = res.data.data ? res.data.data : []
      return kq
    },

    //api lấy tham số mặc định
    async LAYTSMD() {
      let json = {
        nhanvien_id: Number(nhanvien_id),
        tag_form: this.Tag,
        nguoidung_id: Number(nguoidung_id)
      }
      console.log('json', json)
      const res = await api.Xuat_HDDT_LOAD(this.axios, {
        ds_para: JSON.stringify(json)
      })
      let danhsach = res.data.data ? res.data.data : []
      return JSON.parse(danhsach.js_returnds).DS_THAMSO_MD
    },

    //done
    async Xuat_HDDT_LOAD() {
      // Khi vừa mở chức năng, con trỏ nhập liệu chưa tự động focus vào control Mã giao dịch
      this.$nextTick(() => {
        this.$refs['maGDRef'].focus()
      })
      let nhanvien_id = this.$root.token.getNhanVienID()
      let nguoidung_id = this.$root.token.getNguoiDungID()
      let json = {
        nhanvien_id: Number(nhanvien_id),
        tag_form: this.Tag,
        nguoidung_id: Number(nguoidung_id)
      }

      const res = await api.Xuat_HDDT_LOAD(this.axios, {
        ds_para: JSON.stringify(json)
      })

      let danhsach = res.data.data ? res.data.data : []
      //console.log('danhsach',danhsach)
      this.button.sendmail = false

      if (Array.isArray(JSON.parse(danhsach.js_returnds)) && JSON.parse(danhsach.js_returnds).DS_MESSAGE.length > 0) {
        this.tieude = JSON.parse(danhsach.js_returnds).DS_MESSAGE[0].TIEUDE
        this.noidung = JSON.parse(danhsach.js_returnds).DS_MESSAGE[0].NOIDUNG
      }

      if (this.tieude != null && this.noidung != null) {
        //console.log(JSON.parse(danhsach.js_returnds))
        if (JSON.parse(danhsach.js_returnds).DS_MAIL_SERVER) {
          this.button.sendmail = false
          this.smtpServer = JSON.parse(danhsach.js_returnds).DS_MAIL_SERVER[0].SERVER
          this.smtpUser = JSON.parse(danhsach.js_returnds).DS_MAIL_SERVER[0].USERNAME
          this.smtpPass = JSON.parse(danhsach.js_returnds).DS_MAIL_SERVER[0].PASSWORD
          this.smtpPort = JSON.parse(danhsach.js_returnds).DS_MAIL_SERVER[0].PORT
          this.smtpAlias = JSON.parse(danhsach.js_returnds).DS_MAIL_SERVER[0].ALIAS
          if (this.smtpServer != null && this.smtpUser != null && this.smtpPass != null && this.smtpPort != null && this.smtpAlias != null) {
            this.button.sendmail = true
          }
          if (JSON.parse(danhsach.js_returnds).DS_MAIL_SERVER[0].FLAG != null) {
            this.flag = Number(JSON.parse(danhsach.js_returnds).DS_MAIL_SERVER[0].FLAG)
          }
        }
      }

      this.txtMaGD = this._ma_gd
      let tsmd = JSON.parse(danhsach.js_returnds).DS_THAMSO_MD

      if (Array.isArray(tsmd) && tsmd.length > 0) {
        for (var i = 0; i < tsmd.length; i++) {
          if (tsmd[i].MA_TS == 'KHONG_GUIMAIL_HDDT_TUDONG' && tsmd[i].TEN_TS == 1) {
            this.vkhongguimail_td = 1
            break
          }
        }
        for (var i = 0; i < tsmd.length; i++) {
          if (tsmd[i].MA_TS == 'CHOPHEP_GDV_DIEUCHINH_THONGTIN' && tsmd[i].TEN_TS == 1) {
            this.vchophep_gdv_dc_thongtin = 1
            break
          }
        }
        for (var i = 0; i < tsmd.length; i++) {
          if (tsmd[i].MA_TS == 'CHOPHEP_GDV_DIEUCHINH_THONGTIN_HD_MOI' && tsmd[i].TEN_TS == 1) {
            this.vchophep_gdv_dc_thongtin_hd_moi = 1
            break
          }
        }
        for (var i = 0; i < tsmd.length; i++) {
          if (tsmd[i].MA_TS == 'KHONG_CHO_GDV_HUY_HDDT_KHAC_THANG' && tsmd[i].TEN_TS == 1) {
            this.vkhongcho_gdv_huy_hddt_khac_thanh = 1
            break
          }
        }
        for (var i = 0; i < tsmd.length; i++) {
          if (tsmd[i].MA_TS == 'THEM_HDDT_EXTRA' && tsmd[i].TEN_TS == 1) {
            this.vthem_extra = true
            break
          }
        }

        //A Văn trả lời y/c bỏ cboMucThue
        for (var i = 0; i < tsmd.length; i++) {
          if (tsmd[i].MA_TS == 'XUAT_HD_NHIEUMUCTHUE' && tsmd[i].TEN_TS == 1) {
            this.vxuat_hd_nhieumucthue = true
            break
          }
        }

        for (var i = 0; i < tsmd.length; i++) {
          if (tsmd[i].MA_TS == 'LAM_TRON_DON_GIA') {
            this.vlamtron_dongia = true
            break
          }
        }

        for (var i = 0; i < tsmd.length; i++) {
          if (tsmd[i].MA_TS == 'VTT_HDDT_HOPDONG' && tsmd[i].TEN_TS == 1) {
            this.vtt_hddt_hd = 1
            // if(JSON.parse(danhsach.js_returnds).VTT_HDDT_HD_USER != null)
            // {
            //     this.vtt_hddt_hd_user = JSON.parse(danhsach.js_returnds).VTT_HDDT_HD_USER
            // }
            let tsnd = JSON.parse(danhsach.js_returnds).DS_THAMSO_NGUOIDUNG
            if (tsnd.length > 0) {
              for (var j = 0; j < tsnd.length; j++) {
                if (tsnd[j].MA_TS == 'VTT_HDDT_HOPDONG') {
                  if (tsnd[j].GIATRI != null) {
                    this.vtt_hddt_hd_user = Number(tsnd[j].GIATRI)
                  }
                  break
                }
              }
            }

            break
          }
        }

        this.HienThi_GiaoDien_TT78()
      }

      let dshd = JSON.parse(danhsach.js_returnds).DS_LOAI_HOADON
      //console.log('dshd',dshd)
      let ar_dshd = []
      if (dshd && dshd.length > 0) {
        dshd.forEach(function(hd) {
          ar_dshd.push({ id: hd.LOAIHOADON_ID, text: hd.TEN_LOAIHD })
        })
      }      
      this.options.mauso = ar_dshd
      this.ddlMauSo = ar_dshd[0]?.id || 0
      console.log('ar_dshd', ar_dshd)

      let dstt = JSON.parse(danhsach.js_returnds).DS_THAOTAC
      let ar_dstt = []
      if (dstt && dstt.length > 0) {
        dstt.forEach(function(tt) {
          if (tt.THAOTAC != null) {
            ar_dstt.push({ id: tt.THAOTAC_ID, text: tt.THAOTAC })
          }
        })
      }      
      //console.log(dstt);
      this.options.thaotac = ar_dstt
      this.ddlThaoTac = ar_dstt[0]?.id || 0

      let dsdc = JSON.parse(danhsach.js_returnds).DS_DIEUCHINH
      let ar_dsdc = []
      if (dsdc && dsdc.length > 0) {
        dsdc.forEach(function(dc) {
          ar_dsdc.push({ id: dc.DIEUCHINH_ID, text: dc.DIEUCHINH })
        })
      }      
      this.options.kieudieuchinh = ar_dsdc
      this.ddlKieuDieuChinh = ar_dsdc[0]?.id || 0

      this.txtMaGD = this._ma_gd
      if (this.Tag == 1) {
        if (this.vchophep_gdv_dc_thongtin == 1) {
          this.disabledTenKH = true
          this.disabledDiaChi = true
          this.disabledHTTT = true
          this.disabledMST = true
          this.disabledSDT = true
        } else {
          this.disabledTenKH = false
          this.disabledDiaChi = false
          this.disabledHTTT = false
          this.disabledMST = false
          this.disabledSDT = true
        }

        this.button.khoaphieu = false
      } else if (this.Tag == 2 || this.Tag == 3) {
        this.disabledCongTienDV = false
        this.disabledTienThueGTGT = false
        this.disabledTongCongTienThanhToan = false
        this.button.khoaphieu = true
      }
    },

    async api_HienThiTT() {
      let json = {
        ma_gd: this.txtMaGD,
        mucthue: this.txtTHueGTGT,
        nhanvien_id: this.$root.token.getNhanVienID()

        //   "ma_gd":"HPG-LD/00717942",
        //   "mucthue":10,
        //   "nhanvien_id":Number(nhanvien_id)
      }
      let text = ''
      try {
        console.log('json', JSON.stringify(json))

        const res = await api.HienThiThongTin(this.axios, {
          ds_para: JSON.stringify(json)
        })

        let dsach = res.data.data ? res.data.data : []
        console.log('dsach', dsach)
        if (dsach.RETURNING == '0') {
          this.$toast.error(`Lỗi xuất HDDT Không lấy được tt->fn_xuat_hddt_hienthi_thongtin: ${dsach.js_returnds}`)
          return dsach
        }
        text = dsach.js_returnds
        let danhsach = JSON.parse(dsach.js_returnds)
        return danhsach
      } catch (err) {
        this.$toast.error(`Lỗi xuất HDDT: ${err} ${text}`)
        console.log('🚀 ~ file: indexPayment.vue:1208 ~ api_HienThiTT ~ err:', err)
      }
    },

    async HienThiThongTin() {
      try {
        // this.Clean()
        this.ds_thoai = []
        this.i_thoaitra_tmp = 0
        this.objHoaDonDienTu = this.newContent()
        this.objHoaDonDienTuEdit = this.newContent()
        let tongtien = 0
        let tien = 0
        let vat = 0
        let tien0 = 0
        let tien5 = 0
        let vat5 = 0
        let tien10 = 0
        let vat10 = 0
        let tien_novat = 0
        let tien_novat_c = 0

        let counter = 1

        this.vma_gd = this.txtMaGD

        let danhsach = await this.api_HienThiTT()
        //alert(1518)
        console.log('danhsach', danhsach)

        let ds_kh = danhsach.DS_KH
        let ds_dbkh = danhsach.DS_DBKH
        //console.log('ds_kh',ds_kh)
        if (ds_kh == null ) {
          this.showErrMessage(`Lỗi xuất HDDT Không lấy được tt->fn_xuat_hddt_hienthi_thongtin `)
          return
        }

        if (danhsach.DS_KH !=null && danhsach.DS_KH.length > 0) {
          this.vnhanvien_id = 0
          this.vpattern_id = 0
          this.vseri_id = 0
          this.vsoseri = 0
          this.vfkey = ''
          this.Fkey = ''
          this.da_xuathd = false
          this.vphieutt_id = ds_kh[0].PHIEUTT_ID
          this.vhdkh_id = ds_kh[0].HDKH_ID
          this.khoa = danhsach.KHOA
          if (ds_kh[0].NHANVIEN_HD_ID != null) {
            this.vnhanvien_id = ds_kh[0].NHANVIEN_HD_ID
          }

          if (ds_kh[0].KIEU == '1') {
            this.$toast.error('Hợp đồng đã xuất hóa đơn giấy. Bạn không được thực hiện thao tác HĐĐT!')
            return
          }

          this.t_tien_tt = 0
          this.ds_tb = danhsach.DS_HOPDONG_TB_HDDT
          console.log(' this.ds_tb ', this.ds_tb)
          this.ds_thoai = danhsach.DS_THOAI
          if (!this.vxuat_hd_nhieumucthue && ds_kh[0].HDDT_FKEY != null) {
            danhsach = await this.api_HienThiTT()
            try {
              let ds_bd_cha = danhsach.DS_BDHDDT_ID
              if (danhsach.DS_BDHDDT_ID.length > 0) {
                this.bdhddt_cha_id = ds_bd_cha[0].BDHDDT_CHA_ID
              } else {
                this.bdhddt_cha_id = 0
              }
            } catch (e) {
              this.bdhddt_cha_id = 0
            }

            this.da_xuathd = true
            let fromDate = ''
            let toDate = ''
            let kq = await this.listInvByCus_VTT(this.txtMaGD, fromDate, toDate)

            //console.log('kq',kq)

            if (kq.substring(0, 3) == 'ERR') {
              this.$toast.error(kq)
              return
            } else {
              if (kq == null) {
                this.$toast.error('Không tìm thấy thông tin hóa đơn !')
                return
              } else {
                let objKetQua = this.ObjectToXML(kq)
                if (objKetQua.getElementsByTagName('Item').length > 0) {
                  //dòng 449 code cũ
                  this.vtt = false
                  if (this.vtt_hddt_hd_user == 1) {
                    this.vtt = true
                  }
                  eventBus.$emit('my-event')
                  this.$refs.popup_TraCuuHDDT.showModal()
                } else {
                  this.$toast.error('Không tìm thấy thông tin hóa đơn !')
                  return
                }
              }
            }
          } else {
            if (this.vchophep_gdv_dc_thongtin == 1 && this.vchophep_gdv_dc_thongtin_hd_moi == 1) {
              this.disabledTenKH = true
              this.disabledDiaChi = true
              this.disabledHTTT = true
              this.disabledMST = true
              this.disabledSDT = true
            } else {
              this.disabledTenKH = false
              this.disabledDiaChi = false
              this.disabledHTTT = false
              this.disabledMST = false
              this.disabledSDT = false
            }
          }

          this.lblMaSo = ds_kh[0].MA_KH
          let ds_hdtt = danhsach.DS_HDTT

          try {
            if (ds_hdtt.length > 0) {
              this.vstk = ds_hdtt[0].STK
              this.txtMaSoThue = ds_hdtt[0].MST
              this.txtTenKhachHang = ds_hdtt[0].TEN_TT
              this.txtDiaChi = ds_hdtt[0].DIACHI_CT
            }
          } catch (e) {}

          this.txtHinhThucThanhToan = 'Tiền mặt/Chuyển khoản'
          let db = false
          if (ds_kh[0].KHACHHANG_ID != null) {
            if (danhsach.DS_DBKH.length > 0) {
              db = true
            }
          }

          this.vdiachi_tt = ''
          if (ds_kh[0].DIACHI_KH != null) {
            this.vdiachi_tt = ds_kh[0].DIACHI_KH
          } else {
            if (db == true) {
              this.vdiachi_tt = ds_dbkh[0].DIACHI_KH
            }
          }

          this.txtEmail = ''
          if (ds_kh[0].EMAIL_TT != null) {
            this.txtEmail = ds_kh[0].EMAIL_TT
          } else {
            if (db == true) {
              this.txtEmail = ds_dbkh[0].EMAIL_TT
            }
          }

          this.vfax = ''
          if (ds_kh[0].SO_FAX != null) {
            this.vfax = ds_kh[0].SO_FAX
          } else {
            if (db == true) {
              this.vfax = ds_dbkh[0].SO_FAX
            }
          }

          this.vnguoi_dd = ''
          if (ds_kh[0].NGUOI_DD != null) {
            this.vnguoi_dd = ds_kh[0].NGUOI_DD
          } else {
            if (db == true) {
              this.vnguoi_dd = ds_dbkh[0].NGUOI_DD
            }
          }

          this.vloai_kh = ''
          if (ds_kh[0].LOAIKH_ID != null) {
            this.vloai_kh = ds_kh[0].LOAIKH_ID
          } else {
            if (db == true) {
              this.vloai_kh = ds_dbkh[0].LOAIKH_ID
            }
          }

          this.vsodt_kh = ''
          if (ds_kh[0].SO_DT != null) {
            this.vsodt_kh = ds_kh[0].SO_DT
          } else {
            if (db == true) {
              this.vsodt_kh = ds_dbkh[0].SO_DT
            }
          }

          if (this.vtt_hddt_hd_user == 1) {
            this.txtSoDienThoai = ''
            try {
              if (ds_kh[0].SO_DT != null) this.txtSoDienThoai = ds_kh[0].SO_DT
              else {
                if (db == true) this.txtSoDienThoai = ds_dbkh[0].SO_DT
              }
            } catch (errvtt_hddt_hd_user) {
              console.log('errvtt_hddt_hd_user', errvtt_hddt_hd_user)
            }
          } else {
            let ds_tb_hd = danhsach.DS_TB_HD
            try {
              if (danhsach.DS_TB_HD.length > 0) {
                this.txtSoDienThoai = ds_tb_hd[0].SO_DT
              } else {
                this.txtSoDienThoai = ''
              }
            } catch (e) {
              this.txtSoDienThoai = ''
            }
          }

          if (ds_kh[0].LOAIHD_ID == 1) {
            this.vloaihd_id = '0' + ds_kh[0].LOAIHD_ID
          } else if (ds_kh[0].LOAIHD_ID == 2) {
            this.vloaihd_id = ds_kh[0].LOAIHD_ID
          } else {
            this.vloaihd_id = '00'
          }
        } else {
          this.$toast.error(' Không tìm thấy thông tin hợp đồng cho HDDT !')
          this.button.capnhat = false

          // this.Clean()
          return
        }

        let ar_pro = []
        let product = this.ProductWithSTT()
        if (this.ds_tb.length > 0) {
          for (var i = 0; i < this.ds_tb.length + 3; i++) {
            if (this.ds_tb.length <= i) {
              ar_pro.push({
                STT: i + 1,
                Code: '',
                Remark: '',
                Total: '',
                ProdName: '',
                ProdUnit: '',
                ProdQuantity: '',
                ProdPrice: '',
                Discount: '',
                DiscountAmount: '',
                VATRate: '',
                VATAmount: '',
                Amount: ''
              })
            } else {
              product.STT = i + 1
              product.Amount = this.ds_tb[i].AMOUNT
              product.ProdName = this.ds_tb[i].PRODNAME
              product.ProdUnit = this.ds_tb[i].PRODUNIT
              product.ProdQuantity = this.ds_tb[i].PRODQUANTITY
              product.ProdPrice = this.ds_tb[i].PRODPRICE

              tien = Number(tien) + Number(this.ds_tb[i].TIEN)
              vat = Number(vat) + Number(this.ds_tb[i].VAT)
              tongtien = Number(tongtien) + Number(this.ds_tb[i].TONGTIEN)

              if (this.ds_tb[i].TYLE_VAT == 0) tien0 = Number(tien0) + Number(this.ds_tb[i].TIEN)
              if (this.ds_tb[i].TYLE_VAT == 5) {
                tien5 = Number(tien5) + Number(this.ds_tb[i].TIEN)
                vat5 = Number(vat5) + Number(this.ds_tb[i].VAT)
              }
              if (this.ds_tb[i].TYLE_VAT == 10) {
                tien10 = Number(tien10) + Number(this.ds_tb[i].TIEN)
                vat10 = Number(vat10) + Number(this.ds_tb[i].VAT)
              }
              if (this.ds_tb[i].TYLE_VAT == -2) {
                tien_novat = Number(tien_novat) + Number(this.ds_tb[i].TIEN)
              }
              if (this.ds_tb[i].TYLE_VAT == -1) {
                tien_novat_c = Number(tien_novat_c) + Number(this.ds_tb[i].TIEN)
              }
              product.Total = this.ds_tb[i].TOTAL
              product.VATAmount = this.ds_tb[i].VATAMOUNT
              product.VATRate = this.ds_tb[i].VATRATE
              this.txtTHueGTGT = this.ds_tb[i].VATRATE

              ar_pro.push({
                STT: product.STT,
                Code: product.Code,
                Remark: product.Remark,
                Total: product.Total,
                ProdName: product.ProdName,
                ProdUnit: product.ProdUnit,
                ProdQuantity: product.ProdQuantity,
                ProdPrice: product.ProdPrice,
                Discount: product.Discount,
                DiscountAmount: product.DiscountAmount,
                VATRate: product.VATRate,
                VATAmount: product.VATAmount,
                Amount: product.Amount
              })
            }
          }
          this.grid_danhsach = ar_pro
        }

        this.txtCongTienDV = Number(tien)
        this.txtTienTHueGTGT = Number(vat)
        this.txtTongCongTienThanhToan = Number(tongtien)

        this.txtTongTienChiuThue0 = Number(tien0)
        this.txtTongTienChiuThue5 = Number(tien5)
        this.txtTongTienThue5 = Number(vat5)
        this.txtTongTienChiuThue10 = Number(tien10)
        this.txtTongTienThue10 = Number(vat10)
        this.txtTongTienKhongThue = Number(tien_novat)
        this.txtTongTienKhongChiuThue = Number(tien_novat_c)
        //Thay đổi mẫu hóa đơn
        try {
          let pattern1 = true
          if (this.ds_tb.length > 1) {
            let vatrate = this.ds_tb[0].VATRATE
            for (var i = 1; i < this.ds_tb.length; i++) {
              if (this.ds_tb[i].PRODNAME != '' && this.ds_tb[i].PRODNAME != null && vatrate != this.ds_tb[i].VATRATE) {
                pattern1 = false
                break
              }
            }
          }

          // if (pattern1)
          //     this.ddlMauSo = 0
          // else
          //     this.ddlMauSo = 1

          if (pattern1) this.ddlMauSo = 24
          else this.ddlMauSo = 25
        } catch (e) {}

        this.t_tien_load = tongtien
        this.t_tien_edited = tongtien
        this.doi_so_sang_chu(tongtien)

        this.button.nhapmoi = false
        this.button.capnhat = true
      } catch (e) {}
    },

    async accep_TraCuutHDDT(item) {
      console.log('item_tracuu', item)
      let tien = 0
      let vat = 0
      let tongtien = 0
      this.t_tien_thoaitra = 0
      let tien0 = 0
      let tien5 = 0
      let vat5 = 0
      let tien10 = 0
      let vat10 = 0
      let tien_novat = 0
      let tien_novat_c = 0
      if (item.vchapnhan == true) {
        // Lấy mẫu số và ký hiệu theo kết quả trả về

        this.ddlMauSo = this.options.mauso.find((x) => x.text == item._pattern).id

        this.ddlKyHieu = this.options.kyhieu.find((y) => y.text == item._seri).id
        //console.log(this.ddlKyHieu)
        this._dieuchinh = item.vdieuchinh
        this._thaythe = item.vthaythe
        this.vpayment = item.vpayment
        this.Fkey = item._fkey
        //console.log('Tra Cuu');
        await this.TraCuu(item._pattern, item._seri, item._so_hd)

        for (var i = 0; i < this.ds_tb.length; i++) {
          this.t_tien_tt = Number(this.t_tien_tt) + Number(this.ds_tb[i].TONGTIEN)
        }

        this.t_tien_thoaitra = 0

        if (this.ds_thoai.length > 0) {
          this.i_thoaitra_tmp = 1

          let ar_pro = []
          let product = this.ProductWithSTT()
          let stt = 1

          for (var i = 0; i < this.ds_thoai.length + 3; i++) {
            if (this.ds_thoai.length <= i) {
              product.STT = Number(stt) + Number(i)
              ar_pro.push({
                STT: product.STT,
                Code: '',
                Remark: '',
                Total: '',
                ProdName: '',
                ProdUnit: '',
                ProdQuantity: '',
                ProdPrice: '',
                Discount: '',
                DiscountAmount: '',
                VATRate: '',
                VATAmount: '',
                Amount: ''
              })
            } else {
              product.STT = Number(stt) + Number(i)
              product.Amount = this.ds_thoai[i].AMOUNT
              product.ProdName = this.ds_thoai[i].PRODNAME
              product.ProdUnit = this.ds_thoai[i].PRODUNIT
              product.ProdQuantity = this.ds_thoai[i].PRODQUANTITY
              product.ProdPrice = this.ds_thoai[i].PRODPRICE

              tien = Number(tien) + Number(this.ds_thoai[i].TIEN)
              vat = Number(vat) + Number(this.ds_thoai[i].VAT)
              tongtien = Number(tongtien) + Number(this.ds_thoai[i].TONGTIEN)

              product.Total = this.ds_thoai[i].TOTAL

              this.t_tien_thoaitra = Number(this.t_tien_thoaitra) + Number(this.ds_thoai[i].TONGTIEN)

              if (this.ds_thoai[i].TYLE_VAT == '0') {
                tien0 += this.ds_thoai[i].TIEN
              }
              if (this.ds_thoai[i].TYLE_VAT == '5') {
                tien5 += this.ds_thoai[i].TIEN
                vat5 += this.ds_thoai[i].VAT
              }
              if (this.ds_thoai[i].TYLE_VAT == '10') {
                tien10 += this.ds_thoai[i].TIEN
                vat10 += this.ds_thoai[i].VAT
              }
              if (this.ds_thoai[i].TYLE_VAT == '-2') {
                tien_novat += this.ds_thoai[i].TIEN
              }
              if (this.ds_thoai[i].TYLE_VAT == '-1') {
                tien_novat_c += this.ds_thoai[i].TIEN
              }
              this.txtTHueGTGT = this.ds_thoai[i].TYLE_VAT
              product.VATAmount = this.ds_thoai[i].VATAMOUNT
              product.VATRate = this.ds_thoai[i].VATRATE

              ar_pro.push({
                STT: product.STT,
                Code: product.Code,
                Remark: product.Remark,
                Total: product.Total,
                ProdName: product.ProdName,
                ProdUnit: product.ProdUnit,
                ProdQuantity: product.ProdQuantity,
                ProdPrice: product.ProdPrice,
                Discount: product.Discount,
                DiscountAmount: product.DiscountAmount,
                VATRate: product.VATRate,
                VATAmount: product.VATAmount,
                Amount: product.Amount
              })
            }
          }

          this.grid_danhsach = ar_pro

          this.txtCongTienDV = Number(tien)
          this.txtTienTHueGTGT = Number(vat)
          this.txtTongCongTienThanhToan = Number(tongtien)

          this.txtTongTienChiuThue0 = Number(tien0)
          this.txtTongTienChiuThue5 = Number(tien5)
          this.txtTongTienThue5 = Number(vat5)
          this.txtTongTienChiuThue10 = Number(tien10)
          this.txtTongTienThue10 = Number(vat10)
          this.txtTongTienKhongThue = Number(tien_novat)
          this.txtTongTienKhongChiuThue = Number(tien_novat_c)

          this.t_tien_load = Number(tongtien)
          this.t_tien_edited = Number(tongtien)

          this.doi_so_sang_chu(Number(tongtien))

          this.button.capnhat = true
          return
        }
      } else {
        this.button.capnhat = false
        return
      }
    },

    async TraCuu(_Pattern, _Seri, _sohoadon) {
      this.first = true
      this.txtBienBanDinhKem = ''
      this.txtGhiCHuHoaDon = ''
      try {
        if (_Pattern != '' && _Seri != '' && _sohoadon != '') {
          this.invToken = _Pattern + ';' + _Seri + ';' + _sohoadon
          console.log('this.invToken', this.invToken)
        } else {
          if (this.ddlMauSo == 0) {
            this.ddlMauSo.Focus()
            return false
          }
          if (this.ddlKyHieu == 0) {
            this.ddlKyHieu.Focus()
            return false
          }
          if (this.txtSoHoaDon == '') {
            this.txtSoHoaDon.Focus()
            return false
          }
          this.invToken = this.ddlMauSo + ';' + this.ddlKyHieu + ';' + this.txtSoHoaDon
        }

        let xml = ''
        xml = await this.downloadInvNoPay(this.invToken)

        if (xml.substring(0, 4) == 'ERR:') {
          return false
        } else {
          let hdon = this.ObjectToXML(xml)
          let hdon2 = this.ObjectToXML(xml)

          if (hdon != null) {
            let dd_ar = hdon2
              .getElementsByTagName('DLHDon')[0]
              .getElementsByTagName('NDHDon')[0]
              .getElementsByTagName('DSHHDVu')[0]
              .getElementsByTagName('HHDVu').length

            if (dd_ar == 0) {
              this.objHoaDonDienTu.Amount = this.getFourthLevelContentXml(hdon, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'TgTTTBSo' })
              this.objHoaDonDienTu.Amount_words = this.getFourthLevelContentXml(hdon, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'TgTTTBChu' })
              this.objHoaDonDienTu.ArisingDate = this.getThirdLevelContentXml(hdon, { first: 'DLHDon', second: 'TTChung', third: 'NLap' })
              this.objHoaDonDienTu.Buyer = this.getFourthLevelContentXml(hdon, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'HVTNMHang' })
              this.objHoaDonDienTu.ComAddress = this.getFourthLevelContentXml(hdon, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'DChi' })
              this.objHoaDonDienTu.ComName = this.getFourthLevelContentXml(hdon, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'Ten' })
              this.objHoaDonDienTu.ComPhone = this.getFourthLevelContentXml(hdon, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'SDThoai' })
              this.objHoaDonDienTu.ComTaxCode = this.getFourthLevelContentXml(hdon, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'MST' })
              this.objHoaDonDienTu.CusAddress = this.getFourthLevelContentXml(hdon, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'DChi' })
              this.objHoaDonDienTu.CusBankName = this.getFourthLevelContentXml(hdon, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'TNHang' })
              this.objHoaDonDienTu.CusCode = this.getFourthLevelContentXml(hdon, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'MKHang' })
              this.objHoaDonDienTu.CusName = this.getFourthLevelContentXml(hdon, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'Ten' })
              this.objHoaDonDienTu.CusPhone = this.getFourthLevelContentXml(hdon, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'SDThoai' })
              this.objHoaDonDienTu.CusTaxCode = this.getFourthLevelContentXml(hdon, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'MST' })

              this.objHoaDonDienTu.Extra = this.getFourthLevelContentXml(hdon, { first: 'DLHDon', second: 'TTChung', third: 'TTKhac', fourth: 'TTruong' })

              this.objHoaDonDienTu.InvoiceName = this.getThirdLevelContentXml(hdon, { first: 'DLHDon', second: 'TTChung', third: 'THDon' })
              this.objHoaDonDienTu.InvoiceNo = this.getThirdLevelContentXml(hdon, { first: 'DLHDon', second: 'TTChung', third: 'SHDon' })
              this.objHoaDonDienTu.InvoicePattern = this.getThirdLevelContentXml(hdon, { first: 'DLHDon', second: 'TTChung', third: 'KHMSHDon' })
              this.objHoaDonDienTu.Kind_of_Payment = this.getThirdLevelContentXml(hdon, { first: 'DLHDon', second: 'TTChung', third: 'HTTToan' })
              this.objHoaDonDienTu.SerialNo = this.getThirdLevelContentXml(hdon, { first: 'DLHDon', second: 'TTChung', third: 'KHHDon' })
              this.objHoaDonDienTu.Total = this.getFourthLevelContentXml(hdon, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'TgTCThue' })
              this.objHoaDonDienTu.VAT_Amount = this.getFourthLevelContentXml(hdon, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'TgTThue' })

              try {
                const tSuat = this.getFifthLevelContentXml(hdon, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'THTTLTSuat', fifth: 'TSuat' })
                if (tSuat === 'KKKNT') this.objHoaDonDienTu.VAT_Rate = -2
                else if (tSuat === 'KCT') this.objHoaDonDienTu.VAT_Rate = -1
                else if (tSuat.includes('KHAC:')) this.objHoaDonDienTu.VAT_Rate = 10
                else this.objHoaDonDienTu.VAT_Rate = tSuat.substring(0, tSuat.length - 1)
              } catch (e) {
                this.objHoaDonDienTu.VAT_Rate = 0
              }

              let ar_product = []
              let vat_amout = ''
              let tan_suat = ''
              let product = this.ProductWithSTT()
              let count_t = hdon2
                .getElementsByTagName('DLHDon')[0]
                .getElementsByTagName('NDHDon')[0]
                .getElementsByTagName('DSHHDVu')

              for (var i = 0; i < count_t.length; i++) {
                product.Amount = hdon
                  .getElementsByTagName('DLHDon')[0]
                  .getElementsByTagName('NDHDon')[0]
                  .getElementsByTagName('DSHHDVu')
                  [i].getElementsByTagName('TTKhac')[0]
                  .getElementsByTagName('DLieu')[0].textContent
                product.Code = hdon
                  .getElementsByTagName('DLHDon')[0]
                  .getElementsByTagName('NDHDon')[0]
                  .getElementsByTagName('DSHHDVu')
                  [i].getElementsByTagName('MHHDVu')[0].textContent
                product.Discount = 0
                product.DiscountAmount = 0

                product.ProdName = hdon
                  .getElementsByTagName('DLHDon')[0]
                  .getElementsByTagName('NDHDon')[0]
                  .getElementsByTagName('DSHHDVu')
                  [i].getElementsByTagName('THHDVu')[0].textContent
                product.ProdPrice = hdon
                  .getElementsByTagName('DLHDon')[0]
                  .getElementsByTagName('NDHDon')[0]
                  .getElementsByTagName('DSHHDVu')
                  [i].getElementsByTagName('DGia')[0].textContent
                product.ProdQuantity = hdon
                  .getElementsByTagName(DLHDon)[0]
                  .getElementsByTagName('NDHDon')[0]
                  .getElementsByTagName('DSHHDVu')
                  [i].getElementsByTagName('SLuong')[0].textContent
                product.ProdUnit = hdon
                  .getElementsByTagName('DLHDon')[0]
                  .getElementsByTagName('NDHDon')[0]
                  .getElementsByTagName('DSHHDVu')
                  [i].getElementsByTagName('DVTinh')[0].textContent
                product.Total = hdon
                  .getElementsByTagName('DLHDon')[0]
                  .getElementsByTagName('NDHDon')[0]
                  .getElementsByTagName('DSHHDVu')
                  [i].getElementsByTagName('ThTien')[0].textContent

                vat_amout = hdon
                  .getElementsByTagName('DLHDon')[0]
                  .getElementsByTagName('NDHDon')[0]
                  .getElementsByTagName('DSHHDVu')
                  [i].getElementsByTagName('TTKhac')[0].textContent
                if (vat_amout.Length > 1) {
                  product.VATAmount = hdon
                    .getElementsByTagName('DLHDon')[0]
                    .getElementsByTagName('NDHDon')[0]
                    .getElementsByTagName('DSHHDVu')
                    [i].getElementsByTagName('TTKhac')[1]
                    .getElementsByTagName('DLieu')[0].textContent
                } else {
                  product.VATAmount = 0
                }

                tan_suat = hdon
                  .getElementsByTagName('DLHDon')[0]
                  .getElementsByTagName('NDHDon')[0]
                  .getElementsByTagName('DSHHDVu')
                  [i].getElementsByTagName('TSuat')[0].textContent
                if (tan_suat == 'KKKNT') {
                  product.VATRate = -2
                } else if (tan_suat == 'KCT') {
                  product.VATRate = -1
                } else if (tan_suat.includes('KHAC:')) {
                  product.VATRate = 10
                } else {
                  product.VATRate = tan_suat.substring(0, tan_suat.length - 1)
                }

                ar_product.push({
                  STT: Number(i) + 1,
                  Code: product.Code,
                  Remark: product.Remark,
                  Total: product.Total,
                  ProdName: product.ProdName,
                  ProdUnit: product.ProdUnit,
                  ProdQuantity: product.ProdQuantity,
                  ProdPrice: product.ProdPrice,
                  Discount: product.Discount,
                  DiscountAmount: product.DiscountAmount,
                  VATRate: product.VATRate,
                  VATAmount: product.VATAmount,
                  Amount: product.Amount
                })
              }

              this.objHoaDonDienTu.Products = ar_product
            } else {
              this.objHoaDonDienTu.Amount = this.getFourthLevelContentXml(hdon2, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'TgTTTBSo' })
              this.objHoaDonDienTu.Amount_words = this.getFourthLevelContentXml(hdon2, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'TgTTTBChu' })
              this.objHoaDonDienTu.ArisingDate = this.getThirdLevelContentXml(hdon2, { first: 'DLHDon', second: 'TTChung', third: 'NLap' })
              this.objHoaDonDienTu.ComAddress = this.getFourthLevelContentXml(hdon2, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'DChi' })
              this.objHoaDonDienTu.ComName = this.getFourthLevelContentXml(hdon2, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'Ten' })
              this.objHoaDonDienTu.ComPhone = this.getFourthLevelContentXml(hdon2, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'SDThoai   ' })
              this.objHoaDonDienTu.ComTaxCode = this.getFourthLevelContentXml(hdon2, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'MST' })
              this.objHoaDonDienTu.CusAddress = this.getFourthLevelContentXml(hdon2, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'DChi' })
              this.objHoaDonDienTu.CusCode = this.getFourthLevelContentXml(hdon2, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'MKHang' })
              this.objHoaDonDienTu.CusName = this.getFourthLevelContentXml(hdon2, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'Ten' })
              this.objHoaDonDienTu.CusPhone = this.getFourthLevelContentXml(hdon2, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'SDThoai' })
              this.objHoaDonDienTu.CusTaxCode = this.getFourthLevelContentXml(hdon2, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'MST' })
              if (this.objHoaDonDienTu.CusTaxCode == 'null') {
                this.objHoaDonDienTu.CusTaxCode = ''
              }
              this.objHoaDonDienTu.InvoiceName = this.getThirdLevelContentXml(hdon2, { first: 'DLHDon', second: 'TTChung', third: 'THDon' })
              this.objHoaDonDienTu.InvoiceNo = this.getThirdLevelContentXml(hdon2, { first: 'DLHDon', second: 'TTChung', third: 'SHDon' })
              this.objHoaDonDienTu.InvoicePattern = this.getThirdLevelContentXml(hdon2, { first: 'DLHDon', second: 'TTChung', third: 'KHMSHDon' })
              this.objHoaDonDienTu.Kind_of_Payment = this.getThirdLevelContentXml(hdon2, { first: 'DLHDon', second: 'TTChung', third: 'HTTToan' })
              this.objHoaDonDienTu.SerialNo = this.getThirdLevelContentXml(hdon2, { first: 'DLHDon', second: 'TTChung', third: 'KHHDon' })
              this.objHoaDonDienTu.Total = this.getFourthLevelContentXml(hdon2, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'TgTCThue' })
              this.objHoaDonDienTu.VAT_Amount = this.getFourthLevelContentXml(hdon2, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'TgTThue' })

              try {
                const tSuat = this.getFifthLevelContentXml(hdon2, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'THTTLTSuat', fifth: 'TSuat' })
                if (tSuat === 'KKKNT') this.objHoaDonDienTu.VAT_Rate = -2
                else if (tSuat === 'KCT') this.objHoaDonDienTu.VAT_Rate = -1
                else if (tSuat.includes('KHAC:1')) this.objHoaDonDienTu.VAT_Rate = -1
                else if (tSuat.includes('KHAC:2')) this.objHoaDonDienTu.VAT_Rate = -2
                else this.objHoaDonDienTu.VAT_Rate = tSuat.substring(0, tSuat.length - 1)
              } catch (e) {
                this.objHoaDonDienTu.VAT_Rate = 0
              }

              let ar_product = []
              let vat_amout = ''
              let tan_suat = ''
              let product = this.ProductWithSTT()

              let count_t = hdon2
                .getElementsByTagName('DLHDon')[0]
                .getElementsByTagName('NDHDon')[0]
                .getElementsByTagName('DSHHDVu')[0]
                .getElementsByTagName('HHDVu')

              for (var i = 0; i < count_t.length; i++) {
                product.Amount = hdon2
                  .getElementsByTagName('DLHDon')[0]
                  .getElementsByTagName('NDHDon')[0]
                  .getElementsByTagName('DSHHDVu')
                  [i].getElementsByTagName('TTKhac')[0]
                  .getElementsByTagName('TTin')[0]
                  .getElementsByTagName('DLieu')[0].textContent

                product.Discount = 0
                product.DiscountAmount = 0

                product.ProdName = hdon2
                  .getElementsByTagName('DLHDon')[0]
                  .getElementsByTagName('NDHDon')[0]
                  .getElementsByTagName('DSHHDVu')[0]
                  .getElementsByTagName('HHDVu')
                  [i].getElementsByTagName('THHDVu')[0].textContent

                product.ProdPrice = hdon2
                  .getElementsByTagName('DLHDon')[0]
                  .getElementsByTagName('NDHDon')[0]
                  .getElementsByTagName('DSHHDVu')[0]
                  .getElementsByTagName('HHDVu')
                  [i].getElementsByTagName('DGia')[0].textContent

                product.ProdQuantity = hdon2
                  .getElementsByTagName('DLHDon')[0]
                  .getElementsByTagName('NDHDon')[0]
                  .getElementsByTagName('DSHHDVu')[0]
                  .getElementsByTagName('HHDVu')
                  [i].getElementsByTagName('SLuong')[0].textContent

                product.Total = hdon2
                  .getElementsByTagName('DLHDon')[0]
                  .getElementsByTagName('NDHDon')[0]
                  .getElementsByTagName('DSHHDVu')[0]
                  .getElementsByTagName('HHDVu')
                  [i].getElementsByTagName('ThTien')[0].textContent

                vat_amout = hdon2
                  .getElementsByTagName('DLHDon')[0]
                  .getElementsByTagName('NDHDon')[0]
                  .getElementsByTagName('DSHHDVu')[0]
                  .getElementsByTagName('HHDVu')
                  [i].getElementsByTagName('TTKhac')[0]
                  .getElementsByTagName('TTin')

                if (vat_amout.length > 1) {
                  product.VATAmount = hdon2
                    .getElementsByTagName('DLHDon')[0]
                    .getElementsByTagName('NDHDon')[0]
                    .getElementsByTagName('DSHHDVu')[0]
                    .getElementsByTagName('HHDVu')
                    [i].getElementsByTagName('TTKhac')[0]
                    .getElementsByTagName('TTin')[1]
                    .getElementsByTagName('DLieu')[0].textContent
                } else {
                  product.VATAmount = 0
                }

                tan_suat = hdon2
                  .getElementsByTagName('DLHDon')[0]
                  .getElementsByTagName('NDHDon')[0]
                  .getElementsByTagName('DSHHDVu')[0]
                  .getElementsByTagName('HHDVu')
                  [i].getElementsByTagName('TSuat')[0].textContent
                if (tan_suat == 'KKKNT') {
                  product.VATRate = -2
                } else if (tan_suat == 'KCT') {
                  product.VATRate = -1
                } else if (tan_suat.includes('KHAC:')) {
                  product.VATRate = 10
                } else {
                  product.VATRate = tan_suat.substring(0, tan_suat.length - 1)
                }

                ar_product.push({
                  STT: Number(i) + 1,
                  Code: product.Code,
                  Remark: product.Remark,
                  Total: product.Total,
                  ProdName: product.ProdName,
                  ProdUnit: product.ProdUnit,
                  ProdQuantity: product.ProdQuantity,
                  ProdPrice: product.ProdPrice,
                  Discount: product.Discount,
                  DiscountAmount: product.DiscountAmount,
                  VATRate: product.VATRate,
                  VATAmount: product.VATAmount,
                  Amount: product.Amount
                })
              }

              this.objHoaDonDienTu.Products = ar_product
            }

            this.objHoaDonDienTuEdit = this.objHoaDonDienTu

            //Thay đổi mẫu hóa đơn
            try {
              let pattern1 = true
              if (this.objHoaDonDienTu.Products.length > 1) {
                let vatrate = this.objHoaDonDienTu.Products[0].VATRate
                for (var i = 1; i < this.objHoaDonDienTu.Products.length; i++) {
                  if (this.objHoaDonDienTu.Products[i].ProdName != null && vatrate != this.objHoaDonDienTu.Products[i].VATRate) {
                    pattern1 = false
                    break
                  }
                }
              }

              // if (pattern1)
              //     this.ddlMauSo = 0
              // else
              //     this.ddlMauSo = 1

              if (pattern1) this.ddlMauSo = 24
              else this.ddlMauSo = 25
            } catch (e) {}

            this.lblTenHoaDon = this.objHoaDonDienTuEdit.InvoiceName
            this.lblMauSo = this.objHoaDonDienTuEdit.InvoicePattern
            this.lblKyHieu = this.objHoaDonDienTuEdit.SerialNo
            this.lblSo_No = this.objHoaDonDienTuEdit.InvoiceNo
            this.lblMaSoThue = this.objHoaDonDienTuEdit.ComTaxCode
            this.lblTenDonVi = this.objHoaDonDienTuEdit.ComName
            this.lblDiaChi = this.objHoaDonDienTuEdit.CusAddress
            this.lblMaSo = this.objHoaDonDienTuEdit.CusCode
            this.lblThangHoaDon = this.objHoaDonDienTuEdit.KindOfService

            this.txtMaSoThue = this.objHoaDonDienTuEdit.CusTaxCode
            this.txtSoDienThoai = this.objHoaDonDienTuEdit.CusPhone
            this.txtHinhThucThanhToan = this.objHoaDonDienTuEdit.Kind_of_Payment
            this.txtTheMoRong = this.objHoaDonDienTuEdit.Extra
            this.txtTHueGTGT = this.objHoaDonDienTuEdit.VAT_Rate
          } else {
            this.objHoaDonDienTu.InvoiceName = hdon2.getElementsByTagName('Content')[0].getElementsByTagName('InvoiceName')[0] ? hdon2.getElementsByTagName('Content')[0].getElementsByTagName('InvoiceName')[0].textContent : ''
            this.objHoaDonDienTu.InvoicePattern = hdon2.getElementsByTagName('Content')[0].getElementsByTagName('InvoicePattern')[0] ? hdon2.getElementsByTagName('Content')[0].getElementsByTagName('InvoicePattern')[0].textContent : ''
            this.objHoaDonDienTu.SerialNo = hdon2.getElementsByTagName('Content')[0].getElementsByTagName('SerialNo')[0] ? hdon2.getElementsByTagName('Content')[0].getElementsByTagName('SerialNo')[0].textContent : ''
            this.objHoaDonDienTu.InvoiceNo = hdon2.getElementsByTagName('Content')[0].getElementsByTagName('InvoiceNo')[0] ? hdon2.getElementsByTagName('Content')[0].getElementsByTagName('InvoiceNo')[0].textContent : ''
            this.objHoaDonDienTu.ComTaxCode = hdon2.getElementsByTagName('Content')[0].getElementsByTagName('ComTaxCode')[0] ? hdon2.getElementsByTagName('Content')[0].getElementsByTagName('ComTaxCode')[0].textContent : ''
            this.objHoaDonDienTu.ComName = hdon2.getElementsByTagName('Content')[0].getElementsByTagName('ComName')[0] ? hdon2.getElementsByTagName('Content')[0].getElementsByTagName('ComName')[0].textContent : ''
            this.objHoaDonDienTu.CusAddress = hdon2.getElementsByTagName('Content')[0].getElementsByTagName('CusAddress')[0] ? hdon2.getElementsByTagName('Content')[0].getElementsByTagName('CusAddress')[0].textContent : ''
            this.objHoaDonDienTu.CusCode = hdon2.getElementsByTagName('Content')[0].getElementsByTagName('CusCode')[0] ? hdon2.getElementsByTagName('Content')[0].getElementsByTagName('CusCode')[0].textContent : ''
            this.objHoaDonDienTu.KindOfService = hdon2.getElementsByTagName('Content')[0].getElementsByTagName('KindOfService')[0] ? hdon2.getElementsByTagName('Content')[0].getElementsByTagName('KindOfService')[0].textContent : ''
            this.objHoaDonDienTu.CusTaxCode = hdon2.getElementsByTagName('Content')[0].getElementsByTagName('CusTaxCode')[0] ? hdon2.getElementsByTagName('Content')[0].getElementsByTagName('CusTaxCode')[0].textContent : ''
            this.objHoaDonDienTu.CusPhone = hdon2.getElementsByTagName('Content')[0].getElementsByTagName('CusPhone')[0] ? hdon2.getElementsByTagName('Content')[0].getElementsByTagName('CusPhone')[0].textContent : ''
            this.objHoaDonDienTu.Kind_of_Payment = hdon2.getElementsByTagName('Content')[0].getElementsByTagName('Kind_of_Payment')[0] ? hdon2.getElementsByTagName('Content')[0].getElementsByTagName('Kind_of_Payment')[0].textContent : ''
            this.objHoaDonDienTu.Extra = hdon2.getElementsByTagName('Content')[0].getElementsByTagName('Extra')[0] ? hdon2.getElementsByTagName('Content')[0].getElementsByTagName('Extra')[0].textContent : ''
            this.objHoaDonDienTu.VAT_Rate = hdon2.getElementsByTagName('Content')[0].getElementsByTagName('VAT_Rate')[0] ? hdon2.getElementsByTagName('Content')[0].getElementsByTagName('VAT_Rate')[0].textContent : ''

            this.objHoaDonDienTuEdit = this.objHoaDonDienTu

            this.lblTenHoaDon = this.objHoaDonDienTuEdit.InvoiceName
            this.lblMauSo = this.objHoaDonDienTuEdit.InvoicePattern
            this.lblKyHieu = this.objHoaDonDienTuEdit.SerialNo
            this.lblSo_No = this.objHoaDonDienTuEdit.InvoiceNo
            this.lblMaSoThue = this.objHoaDonDienTuEdit.ComTaxCode
            this.lblTenDonVi = this.objHoaDonDienTuEdit.ComName
            this.lblDiaChi = this.objHoaDonDienTuEdit.CusAddress
            this.lblMaSo = this.objHoaDonDienTuEdit.CusCode
            this.lblThangHoaDon = this.objHoaDonDienTuEdit.KindOfService

            this.txtMaSoThue = this.objHoaDonDienTuEdit.CusTaxCode
            this.txtSoDienThoai = this.objHoaDonDienTuEdit.CusPhone
            this.txtHinhThucThanhToan = this.objHoaDonDienTuEdit.Kind_of_Payment
            this.txtTheMoRong = this.objHoaDonDienTuEdit.Extra
            this.txtTHueGTGT = this.objHoaDonDienTuEdit.VAT_Rate
            this.txtTenKhachHang = this.objHoaDonDienTuEdit.CusName
            this.txtDiaChi = this.objHoaDonDienTuEdit.CusAddress
          }

          let vcount = this.objHoaDonDienTuEdit.Products.length
          let counter = 1
          let dt = this.ProductWithSTT()
          for (var i = 1; i < vcount + 3; i++) {
            this.objHoaDonDienTuEdit.Products.push({
              STT: Number(vcount) + Number(i),
              Code: '',
              Remark: '',
              Total: '',
              ProdName: '',
              ProdUnit: '',
              ProdQuantity: '',
              ProdPrice: '',
              Discount: '',
              DiscountAmount: '',
              VATRate: '',
              VATAmount: '',
              Amount: ''
            })
          }
          this.grid_danhsach = this.objHoaDonDienTuEdit.Products

          let vat_amount = 0,
            vat_amount_5,
            vat_amount_10 = 0
          let total = 0,
            total_0 = 0,
            total_novat_t = 0,
            total_novat_c = 0,
            total_5,
            total_10 = 0
          for (var i = 0; i < this.grid_danhsach.length; i++) {
            vat_amount = vat_amount + Number(this.grid_danhsach[i].VATAmount)
            if (this.grid_danhsach[i].VATRate == 5) {
              vat_amount_5 = vat_amount_5 + Number(this.grid_danhsach[i].VATAmount)
            }
            if (this.grid_danhsach[i].VATRate == 10) {
              vat_amount_10 = vat_amount_10 + Number(this.grid_danhsach[i].VATAmount)
            }
            total = total + Number(this.grid_danhsach[i].Total)
            if (this.grid_danhsach[i].VATRate == 0) {
              total_0 = total_0 + Number(this.grid_danhsach[i].Total)
            }
            if (this.grid_danhsach[i].VATRate == -1) {
              total_novat_c = total_novat_c + Number(this.grid_danhsach[i].Total)
            }
            if (this.grid_danhsach[i].VATRate == -2) {
              total_novat_t = total_novat_t + Number(this.grid_danhsach[i].Total)
            }
            if (this.grid_danhsach[i].VATRate == 5) {
              total_5 = total_5 + Number(this.grid_danhsach[i].Total)
            }
            if (this.grid_danhsach[i].VATRate == 10) {
              total_10 = total_10 + Number(this.grid_danhsach[i].Total)
            }
          }

          this.txtTongTienKhongChiuThue = Number(total_novat_c)
          this.txtTongTienKhongThue = Number(total_novat_t)
          this.txtTongTienChiuThue0 = Number(total_0)
          this.txtTongTienChiuThue5 = Number(total_5)
          this.txtTongTienChiuThue10 = Number(total_10)
          this.txtTongTienThue5 = Number(vat_amount_5)
          this.txtTongTienThue10 = Number(vat_amount_10)
          this.txtCongTienDV = Number(total)
          this.txtTienTHueGTGT = Number(vat_amount)
          this.txtTongCongTienThanhToan = Number(this.objHoaDonDienTuEdit.Amount)

          this.objHoaDonDienTuEdit.Total = Number(total)
          this.objHoaDonDienTuEdit.VAT_Amount = Number(vat_amount)
          this.objHoaDonDienTuEdit.Amount = Number(total) + Number(this.objHoaDonDienTuEdit.VAT_Amount)

          this.t_tien_load = Number(this.objHoaDonDienTuEdit.Amount)
          this.t_tien_edited = Number(this.objHoaDonDienTuEdit.Amount)
          this.doi_so_sang_chu(Number(this.txtTongCongTienThanhToan))
          this.first = false
          return true
        }
      } catch (e) {
        this.$toast.error('Lỗi: ' + e)
        return false
      }
    },

    getFourthLevelContentXml(xmlDoc, { first, second, third, fourth }) {
      return xmlDoc
        .getElementsByTagName(first)[0]
        .getElementsByTagName(second)[0]
        .getElementsByTagName(third)[0]
        .getElementsByTagName(fourth).length > 0
        ? xmlDoc
            .getElementsByTagName(first)[0]
            .getElementsByTagName(second)[0]
            .getElementsByTagName(third)[0]
            .getElementsByTagName(fourth)[0].textContent
        : ''
    },

    getThirdLevelContentXml(xmlDoc, { first, second, third }) {
      return xmlDoc
        .getElementsByTagName(first)[0]
        .getElementsByTagName(second)[0]
        .getElementsByTagName(third).length > 0
        ? xmlDoc
            .getElementsByTagName(first)[0]
            .getElementsByTagName(second)[0]
            .getElementsByTagName(third)[0].textContent
        : ''
    },

    getFifthLevelContentXml(xmlDoc, { first, second, third, fourth, fifth }) {
      return xmlDoc
        .getElementsByTagName(first)[0]
        .getElementsByTagName(second)[0]
        .getElementsByTagName(third)[0]
        .getElementsByTagName(fourth)[0]
        .getElementsByTagName(fifth).length > 0
        ? xmlDoc
            .getElementsByTagName(first)[0]
            .getElementsByTagName(second)[0]
            .getElementsByTagName(third)[0]
            .getElementsByTagName(fourth)[0]
            .getElementsByTagName(fifth)[0].textContent
        : ''
    },

    doi_so_sang_chu(tongtien) {
      api
        .doi_so_sang_chu(this.axios, {
          money: Math.abs(Number(tongtien))
        })
        .then((res) => {
          this.txtTienVietBangChu = res.data.data ? res.data.data : []
        })
    },

    Change_MauSo() {
      let phanvung_id = this.$root.token.getPhanVungID()
      api
        .Change_MauSo(this.axios, {
          kieu: 1,
          loaihd_id: this.ddlMauSo,
          phanvung_id: phanvung_id
        })
        .then((res) => {
          let danhsach = res.data.data ? res.data.data : []
          let item = []
          danhsach.forEach(function(ds) {
            if (ds.seri != null) {
              item.push({ id: ds.seri_id, text: ds.seri })
            }
          })

          this.options.kyhieu = item
          this.ddlKyHieu = item[0].id
        })
    },

    async acceptHDDT(item) {
      if (item.vchapnhan == true) {
        this._ma_gd = item.vmagd
        this.vhdkh_id = item.vhdkh_id
        this.txtMaGD = this._ma_gd
        await this.HienThiThongTin()
      }
    },

    btnLayMaGD: async function() {
      //this.$bvModal.show("hoadondt")
      this.$refs.popup_HDDT.showModal()
    },

    Clean() {
      this.bdhddt_cha_id = 0
      this._dieuchinh = 0
      this._thaythe = 0
      this.vma_gd = ''
      this.t_tien_load = 0
      this.t_tien_edited = 0
      this.t_tien_tt = 0
      this.vnhanvien_id = 0
      //lblThanhToan.Text = "";
      this.vfax = ''
      this.vnguoi_dd = ''
      this.vloai_kh = ''

      this.txtEmail = ''
      this.vstk = ''
      this.vten_nh = ''
      this.vdiachi_tt = ''
      this.lblDiaChi = ''
      this.txtDiaChiKH = ''
      this.txtTheMoRong = ''
      this.txtBienBanDinhKem = ''
      this.txtGhiCHuHoaDonu = ''
      this.txtHinhThucThanhToan = ''
      this.lblThangHoaDon = ''
      this.lblMaSo = ''
      this.lblMauSo = ''
      this.txtMaSoThue = ''
      this.lblMaSoThue = ''
      this.txtSoDienThoai = ''

      this.vsodt_kh = ''
      this.txtSoHoaDon = ''
      this.lblTenDonVi = ''
      this.lblTenHoaDon = ''
      this.txtTenKhachHang = ''
      this.txtTienVietBangChu = ''

      this.txtTHueGTGT = '10'
      this.txtCongTienDV = '0'
      this.txtTienTHueGTGT = '0'
      this.txtTongCongTienThanhToan = '0'

      this.lblKyHieu = ''
      this.lblSo_No = ''
      this.grid_danhsach = []

      this.vhdkh_id = 0
      this.vphieutt_id = ''
      this.khoa = 1
    },

    commandClick(args) {
      if (args.target.classList.contains('custombutton')) {
        this.$confirm(`Bạn có chắc chắn muốn xóa sản phẩm này ?`, {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'warning'
        }).then(async () => {
          let ar_new = []
          let stt = 1
          for (var i = 0; i < this.grid_danhsach.length; i++) {
            if (this.grid_danhsach[i].STT != args.rowData.STT) {
              ar_new.push({
                STT: Number(stt) + i,
                Code: this.grid_danhsach[i].Code,
                Total: this.grid_danhsach[i].Total,
                ProdName: this.grid_danhsach[i].ProdName,
                ProdUnit: this.grid_danhsach[i].ProdUnit,
                ProdQuantity: this.grid_danhsach[i].ProdQuantity,
                ProdPrice: this.grid_danhsach[i].ProdPrice,
                Discount: this.grid_danhsach[i].Discount,
                DiscountAmount: this.grid_danhsach[i].DiscountAmount,
                VATRate: this.grid_danhsach[i].VATRate,
                VATAmount: this.grid_danhsach[i].VATAmount,
                Amount: this.grid_danhsach[i].Amount
              })
            }
          }

          let count = ar_new.length
          for (var i = 3; i > 0; i--) {
            if (count < 3) {
              count = Number(count) + 1
              ar_new.push({
                STT: count,
                Code: '',
                Total: '',
                ProdName: '',
                ProdUnit: '',
                ProdQuantity: '',
                ProdPrice: '',
                Discount: '',
                DiscountAmount: '',
                VATRate: '',
                VATAmount: '',
                Amount: ''
              })
            }
          }

          this.grid_danhsach = ar_new

          let tong_total = 0
          let tong_vatamount = 0
          let vat0 = 0
          let vat5 = 0
          let tien5 = 0
          let vat10 = 0
          let tien10 = 0
          for (var i = 0; i < this.grid_danhsach.length; i++) {
            tong_total = tong_total + this.grid_danhsach[i].Total
            tong_vatamount = tong_vatamount + this.grid_danhsach[i].VAT_Amount
            if (this.grid_danhsach[i].VATRate == 0) {
              vat0 = vat0 + this.grid_danhsach[i].Total
            }
            if (this.grid_danhsach[i].VATRate == 5) {
              tien5 = tien5 + this.grid_danhsach[i].Total
              vat5 = vat5 + this.grid_danhsach[i].VATAmount
            }
            if (this.grid_danhsach[i].VATRate == 10) {
              tien10 = tien10 + this.grid_danhsach[i].Total
              vat10 = vat10 + this.grid_danhsach[i].VATAmount
            }
          }
          this.objHoaDonDienTuEdit.Total = Number(tong_total)
          this.objHoaDonDienTuEdit.VAT_Amount = Number(tong_vatamount)
          this.objHoaDonDienTuEdit.Amount = Number(tong_total) + Number(tong_vatamount)

          this.txtTongTienChiuThue0 = vat0
          this.txtTongTienChiuThue5 = tien5
          this.txtTongTienThue5 = vat5
          this.txtTongTienChiuThue10 = tien10
          this.txtTongTienThue10 = vat10

          this.txtCongTienDV = Number(this.objHoaDonDienTuEdit.Total)
          this.txtTienTHueGTGT = Number(this.objHoaDonDienTuEdit.VAT_Amount)

          if (this.objHoaDonDienTuEdit.Total == 0) {
            this.txtCongTienDV = 0
            this.txtTienTHueGTGT = 0
            this.txtTongCongTienThanhToan = 0
            this.objHoaDonDienTuEdit.Amount = 0
          }

          this.doi_so_sang_chu(this.txtTongCongTienThanhToan)
          this.t_tien_edited = Number(this.txtTienVietBangChu)
        })
      }
    },

    async api_CapNhat(json) {
      if (this.dsBDHDDT.length == 0) {
        this.json_update.js_bd_hddt = []
      }
      if (this.dsBDHDDTCT.length == 0) {
        this.json_update.js_bd_hddt_ct = []
      }

      console.log('ds_para', JSON.stringify(json))
      const res = await api.CapNhat(this.axios, {
        ds_para: JSON.stringify(json)
      })
      let dsach = res.data.data ? res.data.data : []
      let danhsach = JSON.parse(dsach.js_returnds)
      return danhsach
    },

    async CapNhat() {
      if (this.ddlThaoTac == 0) {
        this.$toast.error('Bạn chưa chọn thao tác !')
        return
      }
      if (this.ddlThaoTac != 1 && this.ddlThaoTac != 6 && this.da_xuathd == false && this.vpayment == 0) {
        this.$toast.error('Chưa xuất hóa đơn điện tử. Bạn không thể chọn thao tác này !')
        return
      }

      let nhanvien_id = this.$root.token.getNhanVienID()
      let nguoidung_id = this.$root.token.getNguoiDungID()
      let ma_nd = await this.$root.token.getUserName()

      this.json_update = {
        hdkh_id: this.vhdkh_id,
        tag_form: this.Tag,
        nguoidung_id: Number(nguoidung_id),
        ma_nd: ma_nd,
        may_cn: await this.$root.token.getMachine(),
        ip_cn: await this.$root.token.getIP(),
        donvi_id: this.$root.token.getDonViID(),
        ngay_cn: moment(new Date()).format('DD/MM/YYYY'),
        nhanvien_hd_id: this.vnhanvien_id,
        nhanvien_id: nhanvien_id,
        ma_gd: this.txtMaGD,
        js_bd_hddt: [],
        js_bd_hddt_ct: [],
        js_ds_thoai: []
      }

      if (this.ddlThaoTac == 2) {
        // Điều chỉnh hóa đơn
        let ktra = await this.get_app_config()
        if (ktra.dongbo == 0) {
          this.$toast.error('Bạn không có quyền kích hoạt. Vui lòng liên hệ admin để được xử lý!')
          return
        }

        this.DONGBO_TEST.TEST = ktra.keyvalue

        let seri = this.options.kyhieu[0].text
        this.json_update.thaotac = this.ddlThaoTac
        this.json_update.loaihoadon_id = this.ddlMauSo
        this.json_update.seri_id = this.ddlKyHieu
        this.json_update.seri = seri
        this.json_update.soseri = this.txtSoHoaDon

        this.kq_capnhat = await this.api_CapNhat(this.json_update)
        this.kt_tongtien = this.kq_capnhat.KT_TONGTIEN

        if (await this.Kiemtra_dulieu(this.ddlThaoTac)) {
          if (await this.Thoai_Tra()) {
            if (this.i_thoaitra_tmp == 1 && this.t_tien_thoaitra == 0 && this.t_tien_edited == 0) {
            } else {
              this.DieuChinhHoaDon()
            }
          }
        }
      }

      if (this.ddlThaoTac == 5) {
        // Hủy thanh toán + Hủy hóa đơn
        let ktra = await this.get_app_config()
        if (ktra.dongbo == 0) {
          this.$toast.error('Bạn không có quyền kích hoạt. Vui lòng liên hệ admin để được xử lý!')
          return
        }
        this.DONGBO_TEST.TEST = ktra.keyvalue

        let seri = this.options.kyhieu[0].text
        this.json_update.thaotac = this.ddlThaoTac
        this.json_update.loaihoadon_id = this.ddlMauSo
        this.json_update.seri_id = this.ddlKyHieu
        this.json_update.seri = seri
        this.json_update.soseri = this.txtSoHoaDon

        this.kq_capnhat = await this.api_CapNhat(this.json_update)

        if (await this.Kiemtra_dulieu(this.ddlThaoTac)) {
          if (this.Thoai_Tra()) {
            if (this.vtt_hddt_hd_user == 1) {
              this.HuyThanhToan_HuyHoaDon_VTT()
            } else {
              let det_tmp = this.ds_tb.filter(function(ds) {
                return ds.TYLE_VAT != 10
              })
              if (this.vxuat_hd_nhieumucthue && det_tmp.length > 0) this.HuyThanhToan_HuyHoaDon_v2()
              else this.HuyThanhToan_HuyHoaDon()
            }
          }
        }
      }

      if (this.ddlThaoTac == 6) {
        // Xuất mới hóa đơn + gạch nợ
        let seri = this.options.kyhieu[0].text

        this.json_update.thaotac = this.ddlThaoTac
        this.json_update.loaihoadon_id = this.ddlMauSo
        this.json_update.seri_id = this.ddlKyHieu
        this.json_update.seri = seri
        this.json_update.soseri = this.txtSoHoaDon

        this.kq_capnhat = await this.api_CapNhat(this.json_update)
        this.kt_tongtien = this.kq_capnhat.KT_TONGTIEN
        this.ds_kt = this.kq_capnhat.DS_KT
        this.ktra_quyen = this.kq_capnhat.KTRA_QUYEN

        if (await this.Kiemtra_dulieu(this.ddlThaoTac)) {
          let hdResult = await this.Xuathoadon_GachNo()
          return hdResult
        }

      }

      if (this.ddlThaoTac == 7) {
        // gạch nợ
        let seri = this.options.kyhieu[0].text
        this.json_update.thaotac = this.ddlThaoTac
        this.json_update.loaihoadon_id = this.ddlMauSo
        this.json_update.seri_id = this.ddlKyHieu
        this.json_update.seri = seri
        this.json_update.soseri = this.txtSoHoaDon

        this.kq_capnhat = await this.api_CapNhat(this.json_update)

        this.kt_tongtien = this.kq_capnhat.KT_TONGTIEN
        this.ds_kt = this.kq_capnhat.DS_KT
        this.ktra_quyen = this.kq_capnhat.KTRA_QUYEN

        if (await this.Kiemtra_dulieu(this.ddlThaoTac)) {
          this.GachNo()
        }
      }
      if (this.ddlThaoTac == 9) {
        // Chuyển đổi chứng minh nguồn gốc
        let seri = this.options.kyhieu[0].text
        this.json_update.thaotac = this.ddlThaoTac
        this.json_update.loaihoadon_id = this.ddlMauSo
        this.json_update.seri_id = this.ddlKyHieu
        this.json_update.seri = seri
        this.json_update.soseri = this.txtSoHoaDon

        this.kq_capnhat = await this.api_CapNhat(this.json_update)

        this.kt_tongtien = this.kq_capnhat.KT_TONGTIEN
        this.ds_kt = this.kq_capnhat.DS_KT
        this.ktra_quyen = this.kq_capnhat.KTRA_QUYEN

        if (await this.Kiemtra_dulieu(this.ddlThaoTac)) {
          this.ChuyenDoi_XacMinh()
        }
      }

      if (this.ddlThaoTac == 10) {
        // Chuyển đổi chứng minh để lưu trữ
        let seri = this.options.kyhieu[0].text
        this.json_update.thaotac = this.ddlThaoTac
        this.json_update.loaihoadon_id = this.ddlMauSo
        this.json_update.seri_id = this.ddlKyHieu
        this.json_update.seri = seri
        this.json_update.soseri = this.txtSoHoaDon

        this.kq_capnhat = await this.api_CapNhat(this.json_update)

        this.kt_tongtien = this.kq_capnhat.KT_TONGTIEN
        this.ds_kt = this.kq_capnhat.DS_KT
        this.ktra_quyen = this.kq_capnhat.KTRA_QUYEN

        if (await this.Kiemtra_dulieu(this.ddlThaoTac)) {
          if (this.vtt_hddt_hd_user == 1) this.ChuyenDoi_LuuTru_VTT()
          else this.ChuyenDoi_LuuTru()
        }
      }
    },

    async UpdateCus(xml) {
      if (this.vtt_hddt_hd_user == 1) {
        const res = await api2.updateCus_VTT(this.axios, {
          convert: 0,
          xmlCusData: xml
        })
        return res.data.data ? res.data.data : 0
      } else {
        const res = await api.UpdateCus(this.axios, {
          convert: 0,
          xmlCusData: xml
        })
        return res.data.data ? res.data.data : 0
      }
    },

    async GET_FKEY() {
      let key = ''
      const response = await api.GET_FKEY(this.axios, {})
      let data = response.data.data ? response.data.data : []
      key = data[0].fkey

      return key
    },

    async ImportAndPublishInv(account, acPass, xmlInvData, pattern, serial) {
      this.ImportAndPublishInvErr = ''
      console.log('acPass', acPass, 'account', account, 'convert', 0, 'pattern', pattern, 'serial', serial, 'xmlInvData', xmlInvData)
      if (this.vtt_hddt_hd_user == '1') {
        const res = await api2.ImportAndPublishInv_VTT(this.axios, {
          acPass: acPass,
          account: account,
          convert: 0,
          pattern: pattern,
          serial: serial,
          xmlInvData: xmlInvData
        })
        console.log('🚀 ~ file: indexPayment.vue:2500 ~ ImportAndPublishInv ~ res:', res, res.data.faultString)
        try {
          this.ImportAndPublishInvErr = res.data.faultString
        } catch (err) {
          console.log('🚀 ~ file: indexPayment.vue:2506 ~ ImportAndPublishInv ~ err:', err)
        }
        return res.data.data ? res.data.data : ''
      } else {
        const res = await api.ImportAndPublishInv(this.axios, {
          acPass: acPass,
          account: account,
          convert: 0,
          pattern: pattern,
          serial: serial,
          xmlInvData: xmlInvData
        })
        console.log('🚀 ~ file: indexPayment.vue:2500 ~ ImportAndPublishInv ~ res:', res, res.data.faultString)
        try {
          this.ImportAndPublishInvErr = res.data.faultString
        } catch (err) {
          console.log('🚀 ~ file: indexPayment.vue:2506 ~ ImportAndPublishInv ~ err:', err)
        }
        return res.data.data ? res.data.data : ''
      }
    },

    async GetInvoiceXml() {
      this.Fkey = await this.GET_FKEY()
      let xml = '<Invoices><Inv>'
      xml += `<key>${this.Fkey}</key>`
      xml += '<Invoice>'
      xml += `<CusCode>${this.vma_gd}</CusCode>`
      xml += `<CusName><![CDATA[${this.txtTenKhachHang}]]></CusName>`
      xml += `<CusAddress><![CDATA[${this.txtDiaChi}]]></CusAddress>`
      xml += `<CusPhone><![CDATA[${this.txtSoDienThoai}]]></CusPhone>`
      if (this.txtMaSoThue != null) {
        xml += `<CusTaxCode>${this.txtMaSoThue}</CusTaxCode>`
      } else {
        xml += `<CusTaxCode></CusTaxCode>`
      }
      xml += '<PaymentMethod>Tiền mặt/Chuyển khoản</PaymentMethod>'
      xml += '<KindOfService></KindOfService>'
      if (this.vthem_extra) xml += `<Extra>${this.Fkey}</Extra>`
      xml += '<Products>'

      for (var i = 0; i < this.grid_danhsach; i++) {
        if (this.grid_danhsach[i].ProdName != null) {
          xml += '<Product>'
          xml += `<ProdName><![CDATA[${this.grid_danhsach[i].ProdName}]]></ProdName>`
          if (this.grid_danhsach[i].ProdUnit == null) {
            xml += `<ProdUnit> </ProdUnit>`
          } else {
            xml += `<ProdUnit>${this.grid_danhsach[i].ProdUnit}</ProdUnit>`
          }
          xml += `<ProdQuantity>${this.grid_danhsach[i].ProdQuantity}</ProdQuantity>`
          xml += `<ProdPrice>${this.grid_danhsach[i].ProdPrice}</ProdPrice>`
          xml += `<Amount>${this.grid_danhsach[i].Amount}</Amount>`
          xml += '</Product>'
        }
      }
      xml += '</Products>'

      xml += `<Total>${Math.abs(Number(this.txtCongTienDV))}</Total>`
      xml += '<DiscountAmount></DiscountAmount>'
      xml += `<VATRate>${this.txtTHueGTGT}</VATRate>`
      xml += `<VATAmount>${Math.abs(Number(this.txtTienTHueGTGT))}</VATAmount>`
      xml += `<Amount>${Math.abs(Number(this.txtTongCongTienThanhToan))}</Amount>`
      xml += `<AmountInWords>${this.txtTienVietBangChu}</AmountInWords>`
      xml += `<Teller>${this.$root.token.getMaNhanVien()}</Teller>`
      xml += '</Invoice></Inv></Invoices>'

      return xml
    },

    async GetInvoiceXml_tt78() {
      console.log('GetInvoiceXml_tt78')
      this.Fkey = await this.GET_FKEY()
      let nhanvien = await this.$root.token.getMaNhanVien()
      let xml = '<Invoices><Inv>'
      xml += `<key>${this.Fkey}</key>`
      xml += '<Invoice>'
      xml += `<CusCode>${this.vma_gd}</CusCode>`
      xml += `<CusName><![CDATA[${this.txtTenKhachHang}]]></CusName>`
      xml += `<CusAddress><![CDATA[${this.txtDiaChi}]]></CusAddress>`
      xml += `<CusPhone><![CDATA[${this.txtSoDienThoai}]]></CusPhone>`
      if (this.txtMaSoThue != null) {
        xml += `<CusTaxCode>${this.txtMaSoThue}</CusTaxCode>`
      } else {
        xml += `<CusTaxCode></CusTaxCode>`
      }
      xml += '<PaymentMethod>Tiền mặt/Chuyển khoản</PaymentMethod>'
      xml += '<KindOfService></KindOfService>'
      if (this.vthem_extra) xml += `<Extra>${this.Fkey}</Extra>`
      xml += '<Products>'

      for (let i = 0; i < this.grid_danhsach.length; i++) {
        if (this.grid_danhsach[i].ProdName == '') {
        } else {
          xml += '<Product>'
          xml += `<ProdName><![CDATA[${this.grid_danhsach[i].ProdName}]]></ProdName>`
          if (this.grid_danhsach[i].ProdUnit == null) {
            xml += `<ProdUnit> </ProdUnit>`
          } else {
            xml += `<ProdUnit>${this.grid_danhsach[i].ProdUnit}</ProdUnit>`
          }
          xml += `<ProdQuantity>${this.grid_danhsach[i].ProdQuantity}</ProdQuantity>`
          xml += `<ProdPrice>${Math.abs(Number(this.grid_danhsach[i].ProdPrice))}</ProdPrice>`
          xml += `<Total>${Math.abs(Number(this.grid_danhsach[i].Total))}</Total>`
          xml += `<VATRate>${Math.abs(Number(this.grid_danhsach[i].VATRate))}</VATRate>`
          xml += `<VATAmount>${Math.abs(Number(this.grid_danhsach[i].VATAmount))}</VATAmount>`
          xml += `<Amount>${Math.abs(Number(this.grid_danhsach[i].Amount))}</Amount>`
          xml += '<IsSum>1</IsSum>'
          xml += '</Product>'
        }
      }

      if (this.txtCongTienDV == null) {
        this.txtCongTienDV = 0
      }
      if (this.txtTienTHueGTGT == null) {
        this.txtTienTHueGTGT = 0
      }
      if (this.txtTongCongTienThanhToan == null) {
        this.txtTongCongTienThanhToan = 0
      }
      if (this.txtTongTienKhongThue == null) {
        this.txtTongTienKhongThue = 0
      }
      if (this.txtTongTienKhongChiuThue == null) {
        this.txtTongTienKhongChiuThue = 0
      }
      if (this.txtTongTienChiuThue0 == null) {
        this.txtTongTienChiuThue0 = 0
      }
      if (this.txtTongTienChiuThue5 == null) {
        this.txtTongTienChiuThue5 = 0
      }
      if (this.txtTongTienThue5 == null) {
        this.txtTongTienThue5 = 0
      }
      if (this.txtTongTienChiuThue10 == null) {
        this.txtTongTienChiuThue10 = 0
      }
      if (this.txtTongTienThue10 == null) {
        this.txtTongTienThue10 = 0
      }
      xml += '</Products>'
      xml += `<Total>${Math.abs(Number(this.txtCongTienDV))}</Total>`
      xml += '<DiscountAmount></DiscountAmount>'
      xml += `<VATRate>${this.txtTHueGTGT}</VATRate>`
      xml += `<VATAmount>${Math.abs(Number(this.txtTienTHueGTGT))}</VATAmount>`
      xml += `<Amount>${Math.abs(Number(this.txtTongCongTienThanhToan))}</Amount>`
      xml += `<AmountInWords>${this.txtTienVietBangChu}</AmountInWords>`

      xml += `<GrossValue_NonTax>${Math.abs(Number(this.txtTongTienKhongThue))}</GrossValue_NonTax>`
      xml += `<GrossValue>${Math.abs(Number(this.txtTongTienKhongChiuThue))}</GrossValue>`
      xml += `<GrossValue0>${Math.abs(Number(this.txtTongTienChiuThue0))}</GrossValue0>`
      xml += '<VatAmount0>0</VatAmount0>'
      xml += `<GrossValue5>${Math.abs(Number(this.txtTongTienChiuThue5))}</GrossValue5>`

      xml += `<VatAmount5>${Math.abs(Number(this.txtTongTienThue5))}</VatAmount5>`
      xml += `<GrossValue10>${Math.abs(Number(this.txtTongTienChiuThue10))}</GrossValue10>`
      xml += `<VatAmount10>${Math.abs(Number(this.txtTongTienThue10))}</VatAmount10>`

      if(this.vtt_hddt_hd_user != '1') xml += `<Teller>${nhanvien}</Teller>`
      xml += '</Invoice></Inv></Invoices>'

      return xml
    },

    GetInvoiceXml_DieuChinh(obj) {
      this.fkey_new = this.GET_FKEY()
      let xml = '<AdjustInv>'
      xml += `<key>${this.fkey_new}</key>`
      xml += `<CusCode>${obj.CusCode}</CusCode>`
      xml += `<CusName><![CDATA[${obj.CusName}]]></CusName>`
      xml += `<CusAddress><![CDATA[${obj.CusAddress}]]></CusAddress>`
      xml += `<CusPhone><![CDATA[${obj.CusPhone}]]></CusPhone>`
      if (obj.CusTaxCode != null) {
        xml += `<CusTaxCode>${obj.CusTaxCode}</CusTaxCode>`
      } else {
        xml += `<CusTaxCode></CusTaxCode>`
      }

      xml += `<PaymentMethod>${obj.Kind_of_Payment}</PaymentMethod>`
      xml += `<KindOfService>${obj.KindOfService}</KindOfService>`

      let type = this.ddlKieuDieuChinh == 1 ? '2' : this.ddlKieuDieuChinh == 2 ? '3' : '4'
      xml += `<Type>${type}></Type>`
      if (this.vthem_extra) xml += `<Extra>${this.fkey_new}></Extra>`
      xml += '<Products>'

      for (var i = 0; i < obj.Products.length; i++) {
        xml += '<Product>'
        xml += `<ProdName><![CDATA[${obj.Products[i].ProdName}]]></ProdName>`
        xml += `<ProdUnit><${obj.Products[i].ProdUnit}></ProdUnit>`
        xml += `<ProdQuantity>${obj.Products[i].ProdQuantity}></ProdQuantity>`
        if (obj.Products[i].ProdPrice != null) {
          xml += `<ProdPrice>${Math.abs(Number(obj.Products[i].ProdPrice))}></ProdPrice>`
        } else {
          xml += `<ProdPrice>${obj.Products[i].ProdPrice}></ProdPrice>`
        }
        if (obj.Products[i].Amount != null) {
          if (obj.Products[i].Amount != 0) {
            xml += `<Amount>${Math.abs(Number(obj.Products[i].Amount))}></Amount>`
          } else {
            xml += '<Amount></Amount>'
          }
        } else {
          xml += `<Amount>${obj.Products[i].Amount}</Amount>`
        }
        xml += '</Product>'
      }

      xml += '</Products>'
      if (obj.Total != null) {
        xml += `<Total>${Math.abs(Number(obj.Total))}</Total>`
      } else {
        xml += `<Total>${obj.Total}</Total>`
      }
      xml += `<VATRate>${obj.VATRate}</VATRate>`

      if (obj.VAT_Amount != null) {
        if (obj.VAT_Amount != 0) {
          xml += `<VAT_Amount>${Math.abs(Number(obj.VAT_Amount))}</VAT_Amount>`
        } else {
          xml += '<VATAmount></VATAmount>'
        }
      } else {
        xml += `<VAT_Amount>${obj.VAT_Amount}</VAT_Amount>`
      }

      if (obj.Amount != null) {
        xml += `<Amount>${Math.abs(Number(obj.Amount))}</Amount>`
      } else {
        xml += `<Amount>${obj.Amount}></Amount>`
      }
      if (obj.Amount != null && obj.Amount != 0) {
        xml += `<AmountInWords>${obj.Amount_words}</AmountInWords>`
      } else {
        xml += `<AmountInWords></AmountInWords>`
      }

      xml += `<Teller>${this.$root.token.getMaNhanVien()}</Teller>`
      xml += '</AdjustInv>'

      return xml
    },

    async GetInvoiceXml_DieuChinh_tt78(obj) {
      this.fkey_new = await this.GET_FKEY()
      let xml = '<AdjustInv>'
      xml += `<key>${this.fkey_new}</key>`
      xml += `<CusCode>${obj.CusCode}</CusCode>`
      xml += `<CusName><![CDATA[${obj.CusName}]]></CusName>`
      xml += `<CusAddress><![CDATA[${obj.CusAddress}]]></CusAddress>`
      xml += `<CusPhone><![CDATA[${obj.CusPhone}]]></CusPhone>`
      xml += `<CusTaxCode><![CDATA[${obj.CusTaxCode}]]></CusTaxCode>`
      xml += `<PaymentMethod>${obj.Kind_of_Payment}</PaymentMethod>`
      xml += `<KindOfService>${obj.KindOfService}</KindOfService>`

      let type = this.ddlKieuDieuChinh == 1 ? '2' : this.ddlKieuDieuChinh == 2 ? '3' : '4'
      xml += `<Type>${type}</Type>`
      if (this.vthem_extra) xml += `<Extra>${this.fkey_new}</Extra>`
      xml += '<Products>'

      for (var i = 0; i < obj.Products.length; i++) {
        if (obj.Products[i].ProdName.trim() == null || obj.Products[i].ProdName.trim() == '') {
        } else {
          xml += '<Product>'
          xml += `<ProdName><![CDATA[${obj.Products[i].ProdName}]]></ProdName>`
          xml += `<ProdUnit>${obj.Products[i].ProdUnit}</ProdUnit>`
          xml += `<ProdQuantity>${obj.Products[i].ProdQuantity}</ProdQuantity>`
          if (obj.Products[i].ProdPrice != null) {
            xml += `<ProdPrice>${Math.abs(Number(obj.Products[i].ProdPrice))}</ProdPrice>`
          } else {
            xml += `<ProdPrice>${obj.Products[i].ProdPrice}</ProdPrice>`
          }

          if (obj.Products[i].Amount != null) {
            if (obj.Products[i].Amount != 0) {
              xml += `<Amount>${Math.abs(Number(obj.Products[i].Amount))}</Amount>`
            } else {
              xml += '<Amount>0</Amount>'
            }
          } else {
            xml += `<Amount>${obj.Products[i].Amount}</Amount>`
          }

          if (obj.Products[i].Total != null) {
            if (obj.Products[i].Total != 0) {
              xml += `<Total>${Math.abs(Number(obj.Products[i].Total))}</Total>`
            } else {
              xml += '<Total>0</Total>'
            }
          } else {
            xml += `<Total>${obj.Products[i].Total}</Total>`
          }

          if (obj.Products[i].VATRate != null) {
            if (obj.Products[i].VATRate != 0) {
              xml += `<VATRate>${Math.abs(Number(obj.Products[i].VATRate))}</VATRate>`
            } else {
              xml += '<VATRate>0</VATRate>'
            }
          } else {
            xml += `<VATRate>${obj.Products[i].VATRate}</VATRate>`
          }

          if (obj.Products[i].VATAmount != null) {
            if (obj.Products[i].VATAmount != 0) {
              xml += `<VATAmount>${Math.abs(Number(obj.Products[i].VATAmount))}</VATAmount>`
            } else {
              xml += '<VATAmount>0</VATAmount>'
            }
          } else {
            xml += `<VATAmount>${obj.Products[i].VATAmount}</VATAmount>`
          }

          xml += '</Product>'
        }
      }

      xml += '</Products>'

      if (obj.Total != null) {
        xml += `<Total>${Math.abs(Number(obj.Total))}</Total>`
      } else {
        xml += `<Total>${obj.Total}</Total>`
      }
      xml += `<VATRate>${obj.VAT_Rate}</VATRate>`

      if (obj.VAT_Amount != null) {
        if (obj.VAT_Amount != 0) {
          xml += `<VATAmount>${Math.abs(Number(obj.VAT_Amount))}</VATAmount>`
        } else {
          xml += '<VATAmount></VATAmount>'
        }
      } else {
        xml += `<VATAmount>${obj.VAT_Amount}</VATAmount>`
      }

      if (obj.Amount != null) {
        xml += `<Amount>${Math.abs(Number(obj.Amount))}</Amount>`
      } else {
        xml += `<Amount>${obj.Amount}></Amount>`
      }

      console.log('Amount_words', obj)
      if (obj.Amount != null && obj.Amount != 0) xml += `<AmountInWords>${obj.Amount_words}</AmountInWords>`
      else xml += '<AmountInWords>\\</AmountInWords>'

      xml += `<GrossValue_NonTax>${Math.abs(Number(this.txtTongTienKhongThue))}</GrossValue_NonTax>`
      xml += `<GrossValue>${Math.abs(Number(this.txtTongTienKhongChiuThue))}</GrossValue>`
      xml += `<GrossValue0>${Math.abs(Number(this.txtTongTienChiuThue0))}</GrossValue0>`
      xml += '<VatAmount0>0</VatAmount0>'
      xml += `<GrossValue5>${Math.abs(Number(this.txtTongTienChiuThue5))}</GrossValue5>`
      xml += `<VatAmount5>${Math.abs(Number(this.txtTongTienThue5))}</VatAmount5>`
      xml += `<GrossValue10>${Math.abs(Number(this.txtTongTienChiuThue10))}</GrossValue10>`
      xml += `<VatAmount10>${Math.abs(Number(this.txtTongTienThue10))}</VatAmount10>`
      xml += '<PaymentStatus>1</PaymentStatus>'

      xml += `<Teller>${this.$root.token.getMaNhanVien()}</Teller>`
      xml += '</AdjustInv>'

      return xml
    },

    GetInvoiceXml_ThayThe(obj) {
      this.fkey_new = this.GET_FKEY()
      let xml = '<ReplaceInv>'
      xml += `<key>${this.fkey_new}</key>`
      xml += `<CusCode>${obj.CusCode}</CusCode>`
      xml += `<CusName>${obj.CusName}</CusName>`
      xml += `<CusAddress>${obj.CusAddress}</CusAddress>`
      xml += `<CusPhone>${obj.CusPhone}</CusPhone>`
      if (obj.CusTaxCode != null) {
        xml += `<CusTaxCode>${obj.CusTaxCode}</CusTaxCode>`
      } else {
        xml += `<CusTaxCode></CusTaxCode>`
      }

      xml += `<PaymentMethod>${obj.Kind_of_Payment}</PaymentMethod>`
      xml += `<KindOfService>${obj.KindOfService}</KindOfService>`

      if (this.vthem_extra) xml += `<Extra>${this.fkey_new}</Extra>`
      xml += '<Products>'

      for (var i = 0; i < obj.Products.length; i++) {
        xml += '<Product>'
        xml += `<ProdName>${obj.Products[i].ProdName}</ProdName>`
        xml += `<ProdUnit><${obj.Products[i].ProdUnit}</ProdUnit>`
        xml += `<ProdQuantity>${obj.Products[i].ProdQuantity}</ProdQuantity>`
        if (obj.Products[i].ProdPrice != null) {
          xml += `<ProdPrice>${Math.abs(Number(obj.Products[i].ProdPrice))}</ProdPrice>`
        } else {
          xml += `<ProdPrice>${obj.Products[i].ProdPrice}</ProdPrice>`
        }

        if (obj.Products[i].Amount != null) {
          xml += `<Amount>${Math.abs(Number(obj.Products[i].Amount))}</Amount>`
        } else {
          xml += `<Amount>${obj.Products[i].Amount}</Amount>`
        }

        xml += '</Product>'
      }

      xml += '</Products>'

      if (obj.Total != null) {
        xml += `<Total>${Math.abs(Number(obj.Total))}</Total>`
      } else {
        xml += `<Total>${obj.Total}</Total>`
      }

      xml += `<VATRate>${obj.VATRate}</VATRate>`

      if (obj.VATAmount != null) {
        xml += `<VATAmount>${Math.abs(Number(obj.VATAmount))}</VATAmount>`
      } else {
        xml += `<VATAmount>${obj.VATAmount}</VATAmount>`
      }

      if (obj.Amount != null) {
        xml += `<Amount>${Math.abs(Number(obj.Amount))}</Amount>`
      } else {
        xml += `<Amount>${obj.Amount}</Amount>`
      }

      xml += `<AmountInWords>${obj.AmountInWords}</AmountInWords>`
      xml += `<Teller>${this.$root.token.getMaNhanVien()}</Teller>`
      xml += '</ReplaceInv>'

      return xml
    },

    GetInvoiceXml_ThayThe_tt78(obj) {
      this.fkey_new = this.GET_FKEY()
      let xml = '<ReplaceInv>'
      xml += `<key>${this.fkey_new}</key>`
      xml += `<CusCode>${obj.CusCode}</CusCode>`
      xml += `<CusName>${obj.CusName}</CusName>`
      xml += `<CusAddress>${obj.CusAddress}</CusAddress>`
      xml += `<CusPhone>${obj.CusPhone}</CusPhone>`
      if (obj.CusTaxCode != null) {
        xml += `<CusTaxCode>${obj.CusTaxCode}</CusTaxCode>`
      } else {
        xml += `<CusTaxCode></CusTaxCode>`
      }
      xml += `<PaymentMethod>${obj.Kind_of_Payment}</PaymentMethod>`
      xml += `<KindOfService>${obj.KindOfService}</KindOfService>`

      if (this.vthem_extra) xml += `<Extra>${this.fkey_new}</Extra>`
      xml += '<Products>'

      for (var i = 0; i < obj.Products.length; i++) {
        xml += '<Product>'
        xml += `<ProdName>${obj.Products[i].ProdName}</ProdName>`
        xml += `<ProdUnit><${obj.Products[i].ProdUnit}</ProdUnit>`
        xml += `<ProdQuantity>${obj.Products[i].ProdQuantity}</ProdQuantity>`
        if (obj.Products[i].ProdPrice != null) {
          xml += `<ProdPrice>${Math.abs(Number(obj.Products[i].ProdPrice))}</ProdPrice>`
        } else {
          xml += `<ProdPrice>${obj.Products[i].ProdPrice}</ProdPrice>`
        }

        if (obj.Products[i].Amount != null) {
          xml += `<Amount>${Math.abs(Number(obj.Products[i].Amount))}</Amount>`
        } else {
          xml += `<Amount>${obj.Products[i].Amount}</Amount>`
        }

        if (obj.Products[i].Total != null) {
          if (pro.Total != 0) xml += `<Total>${Math.abs(Number(obj.Products[i].Total))}</Total>`
          else xml += '</Total><Total>'
        } else {
          xml += `<Total>${obj.Products[i].Total}</Total>`
        }

        if (obj.Products[i].VATRate != null) {
          if (pro.VATRate != 0) xml += `<VATRate>${Math.abs(Number(obj.Products[i].VATRate))}</VATRate>`
          else xml += '</VATRate><VATRate>'
        } else {
          xml += `<VATRate>${obj.Products[i].VATRate}</VATRate>`
        }

        if (obj.Products[i].VATAmount != null) {
          if (pro.VATAmount != 0) xml += `<VATAmount>${Math.abs(Number(obj.Products[i].VATAmount))}</VATAmount>`
          else xml += '</VATAmount><VATAmount>'
        } else {
          xml += `<VATAmount>${obj.Products[i].VATAmount}</VATAmount>`
        }

        xml += '</Product>'
      }

      xml += '</Products>'

      if (obj.Total != null) {
        xml += `<Total>${Math.abs(Number(obj.Total))}</Total>`
      } else {
        xml += `<Total>${obj.Total}</Total>`
      }

      xml += `<VATRate>${obj.VATRate}</VATRate>`

      if (obj.VATAmount != null) {
        xml += `<VATAmount>${Math.abs(Number(obj.VATAmount))}</VATAmount>`
      } else {
        xml += `<VATAmount>${obj.VATAmount}</VATAmount>`
      }

      if (obj.Amount != null) {
        xml += `<Amount>${Math.abs(Number(obj.Amount))}</Amount>`
      } else {
        xml += `<Amount>${obj.Amount}</Amount>`
      }

      xml += `<AmountInWords>${obj.AmountInWords}</AmountInWords>`

      xml += `<GrossValue_NonTax>${Math.abs(Number(this.txtTongTienKhongThue))}</GrossValue_NonTax>`
      xml += `<GrossValue>${Math.abs(Number(this.txtTongTienKhongChiuThue))}</GrossValue>`
      xml += `<GrossValue0>${Math.abs(Number(this.txtTongTienChiuThue0))}</GrossValue0>`
      xml += '<VatAmount0>0</VatAmount0>'
      xml += `<GrossValue5>${Math.abs(Number(this.txtTongTienChiuThue5))}</GrossValue5>`
      xml += `<VatAmount5>${Math.abs(Number(this.txtTongTienThue5))}</VatAmount5>`
      xml += `<GrossValue10>${Math.abs(Number(this.txtTongTienChiuThue10))}</GrossValue10>`
      xml += `<VatAmount10>${Math.abs(Number(this.txtTongTienThue10))}</VatAmount10>`

      xml += '<PaymentStatus>1</PaymentStatus>'

      xml += `<Teller>${this.$root.token.getMaNhanVien()}</Teller>`
      xml += '</ReplaceInv>'

      return xml
    },

    GetInvoiceXml_VTT() {
      this.Fkey = this.GET_FKEY()
      let xml = '<Invoices>'
      xml += '<Inv>'
      xml += `<key>${this.Fkey}</key>`
      xml += '<Invoice>'
      xml += `<CusCode>${this.vma_gd}</CusCode>`
      xml += `<CusName><![CDATA[${this.txtTenKhachHang}]]></CusName>`
      xml += `<CusAddress><![CDATA[${this.txtDiaChi}]]></CusAddress>`
      xml += `<CusPhone><![CDATA[${this.txtSoDienThoai}]]></CusPhone>`
      if (this.txtMaSoThue != null) {
        xml += `<CusTaxCode>${this.txtMaSoThue}</CusTaxCode>`
      } else {
        xml += `<CusTaxCode></CusTaxCode>`
      }
      xml += '<PaymentMethod>Tiền mặt/Chuyển khoản</PaymentMethod>'
      xml += '<KindOfService></KindOfService>'

      if (this.vthem_extra) xml += `<Extra>${this.Fkey}</Extra>`
      xml += '<Products>'

      for (var i = 0; i < this.grid_danhsach.length; i++) {
        if (this.grid_danhsach[i].ProdName != null) {
          xml += '<Product>'
          xml += `<ProdName><![CDATA[${this.grid_danhsach[i].ProdName}]]></ProdName>`
          if (this.grid_danhsach[i].ProdUnit == null) {
            xml += `<ProdUnit> </ProdUnit>`
          } else {
            xml += `<ProdUnit>${this.grid_danhsach[i].ProdUnit}</ProdUnit>`
          }
          if (this.grid_danhsach[i].ProdQuantity == null) {
            xml += `<ProdQuantity> </ProdQuantity>`
          } else {
            xml += `<ProdQuantity>${this.grid_danhsach[i].ProdQuantity}</ProdQuantity>`
          }

          xml += `<ProdPrice>${Math.abs(Number(this.grid_danhsach[i].ProdPrice))}</ProdPrice>`
          xml += `<Amount>${Math.abs(Number(this.grid_danhsach[i].Amount))}</Amount>`
          xml += `<Total>${Math.abs(Number(this.grid_danhsach[i].Total))}</Total>`
          xml += '</Product>'
        }
      }

      xml += '</Products>'

      xml += `<Total>${Math.abs(Number(this.txtCongTienDV))}</Total>`
      xml += '<DiscountAmount></DiscountAmount>'
      xml += `<VATRate>${this.txtTHueGTGT}</VATRate>`
      xml += `<VATAmount>${Math.abs(Number(this.txtTienTHueGTGT))}</VATAmount>`
      xml += `<Amount>${Math.abs(Number(this.txtTongCongTienThanhToan))}</Amount>`
      xml += `<AmountInWords>${this.txtTienVietBangChu}</AmountInWords>`
      xml += `<EmailDeliver><![CDATA[${this.txtEmail}]]></EmailDeliver>`

      xml += '</Invoice>'
      xml += '</Inv>'
      xml += '</Invoices>'

      return xml
    },

    GetInvoiceXml_VTT_tt78() {
      this.Fkey = this.GET_FKEY()
      let xml = '<Invoices>'
      xml += '<Inv>'
      xml += `<key>${this.Fkey}</key>`
      xml += '<Invoice>'
      xml += `<CusCode>${this.vma_gd}</CusCode>`
      xml += `<CusName><![CDATA[${this.txtTenKhachHang}]]></CusName>`
      xml += `<CusAddress><![CDATA[${this.txtDiaChi}]]></CusAddress>`
      xml += `<CusPhone><![CDATA[${this.txtSoDienThoai}]]></CusPhone>`
      if (this.txtMaSoThue != null) {
        xml += `<CusTaxCode>${this.txtMaSoThue}</CusTaxCode>`
      } else {
        xml += `<CusTaxCode></CusTaxCode>`
      }

      xml += '<PaymentMethod>Tiền mặt/Chuyển khoản</PaymentMethod>'
      xml += '<KindOfService></KindOfService>'

      if (this.vthem_extra) xml += `<Extra>${this.Fkey}</Extra>`
      xml += '<Products>'

      for (var i = 0; i < this.grid_danhsach.length; i++) {
        if (this.grid_danhsach[i].ProdName != null) {
          xml += '<Product>'
          xml += `<ProdName><![CDATA[${this.grid_danhsach[i].ProdName}]]></ProdName>`
          if (this.grid_danhsach[i].ProdUnit == null) {
            xml += `<ProdUnit> </ProdUnit>`
          } else {
            xml += `<ProdUnit>${this.grid_danhsach[i].ProdUnit}</ProdUnit>`
          }
          if (this.grid_danhsach[i].ProdQuantity == null) {
            xml += `<ProdQuantity> </ProdQuantity>`
          } else {
            xml += `<ProdQuantity>${this.grid_danhsach[i].ProdQuantity}</ProdQuantity>`
          }

          xml += `<ProdPrice>${Math.abs(Number(this.grid_danhsach[i].ProdPrice))}</ProdPrice>`
          xml += `<Amount>${Math.abs(Number(this.grid_danhsach[i].Amount))}</Amount>`
          xml += `<Total>${Math.abs(Number(this.grid_danhsach[i].Total))}</Total>`
          xml += `<VATRate>${Number(this.grid_danhsach[i].VATRate)}</VATRate>`
          xml += `<VATAmount>${Math.abs(Number(this.grid_danhsach[i].VATAmount))}</VATAmount>`
          xml += '</Product>'
        }
      }

      xml += '</Products>'

      xml += `<Total>${Math.abs(Number(this.txtCongTienDV))}</Total>`
      xml += '<DiscountAmount></DiscountAmount>'
      xml += `<VATRate>${this.txtTHueGTGT}</VATRate>`
      xml += `<VATAmount>${Math.abs(Number(this.txtTienTHueGTGT))}</VATAmount>`
      xml += `<Amount>${Math.abs(Number(this.txtTongCongTienThanhToan))}</Amount>`
      xml += `<AmountInWords>${this.txtTienVietBangChu}</AmountInWords>`
      xml += `<EmailDeliver><![CDATA[${this.txtEmail}]]></EmailDeliver>`

      xml += `<GrossValue_NonTax>${Math.abs(Number(this.txtTongTienKhongThue))}</GrossValue_NonTax>`
      xml += `<GrossValue>${Math.abs(Number(this.txtTongTienKhongChiuThue))}</GrossValue>`
      xml += `<GrossValue0>${Math.abs(Number(this.txtTongTienChiuThue0))}</GrossValue0>`
      xml += '<VatAmount0>0</VatAmount0>'
      xml += `<GrossValue5>${Math.abs(Number(this.txtTongTienChiuThue5))}</GrossValue5>`
      xml += `<VatAmount5>${Math.abs(Number(this.txtTongTienThue5))}</VatAmount5>`
      xml += `<GrossValue10>${Math.abs(Number(this.txtTongTienChiuThue10))}</GrossValue10>`
      xml += `<VatAmount10>${Math.abs(Number(this.txtTongTienThue10))}</VatAmount10>`

      xml += '</Invoice>'
      xml += '</Inv>'
      xml += '</Invoices>'

      return xml
    },

    GetInvoiceXml_DieuChinh_VTT(obj) {
      this.fkey_new = this.GET_FKEY()
      let xml = '<AdjustInv>'
      xml += `<key>${this.fkey_new}</key>`
      xml += `<CusCode>${obj.CusCode}</CusCode>`
      xml += `<CusName><![CDATA[${obj.CusName}]]></CusName>`
      xml += `<CusAddress><![CDATA[${obj.CusAddress}]]></CusAddress>`
      xml += `<CusPhone><![CDATA[${obj.CusPhone}]]></CusPhone>`
      if (obj.CusTaxCode != null) {
        xml += `<CusTaxCode>${obj.CusTaxCode}</CusTaxCode>`
      } else {
        xml += `<CusTaxCode></CusTaxCode>`
      }
      xml += `<PaymentMethod>${obj.Kind_of_Payment}</PaymentMethod>`
      xml += `<KindOfService>${obj.KindOfService}</KindOfService>`

      let type = this.ddlKieuDieuChinh == 1 ? '2' : this.ddlKieuDieuChinh == 2 ? '3' : '4'
      xml += `<Type>${type}</Type>`

      if (this.vthem_extra) xml += `<Extra>${this.fkey_new}</Extra>`
      xml += '<Products>'

      for (var i = 0; i < obj.Products.length; i++) {
        xml += '<Product>'
        xml += `<ProdName><![CDATA[${obj.Products[i].ProdName}]]></ProdName>`
        xml += `<ProdUnit><${obj.Products[i].ProdUnit}</ProdUnit>`
        xml += `<ProdQuantity>${obj.Products[i].ProdQuantity}</ProdQuantity>`
        if (obj.Products[i].ProdPrice != null) {
          xml += `<ProdPrice>${Math.abs(Number(obj.Products[i].ProdPrice))}</ProdPrice>`
        } else {
          xml += `<ProdPrice>${obj.Products[i].ProdPrice}</ProdPrice>`
        }

        if (obj.Products[i].Amount != null) {
          xml += `<Amount>${Math.abs(Number(obj.Products[i].Amount))}</Amount>`
        } else {
          xml += `<Amount>${obj.Products[i].Amount}</Amount>`
        }

        if (obj.Products[i].Amount != null) {
          xml += `<Total>${Math.abs(Number(obj.Products[i].Amount))}</Total>`
        } else {
          xml += `<Total>${obj.Products[i].Amount}</Total>`
        }

        xml += '</Product>'
      }

      xml += '</Products>'

      if (obj.Total != null) {
        xml += `<Total>${Math.abs(Number(obj.Total))}</Total>`
      } else {
        xml += `<Total>${obj.Total}</Total>`
      }

      xml += `<VATRate>${obj.VATRate}</VATRate>`

      if (obj.VATAmount != null) {
        xml += `<VATAmount>${Math.abs(Number(obj.VATAmount))}</VATAmount>`
      } else {
        xml += `<VATAmount>${obj.VATAmount}</VATAmount>`
      }

      if (obj.Amount != null) {
        xml += `<Amount>${Math.abs(Number(obj.Amount))}</Amount>`
      } else {
        xml += `<Amount>${obj.Amount}</Amount>`
      }

      xml += `<AmountInWords>${obj.AmountInWords}</AmountInWords>`
      xml += '</AdjustInv>'

      return xml
    },

    GetInvoiceXml_DieuChinh_VTT_tt78(obj) {
      this.fkey_new = this.GET_FKEY()
      let xml = '<AdjustInv>'
      xml += `<key>${this.fkey_new}</key>`
      xml += `<CusCode>${obj.CusCode}</CusCode>`
      xml += `<CusName><![CDATA[${obj.CusName}]]></CusName>`
      xml += `<CusAddress><![CDATA[${obj.CusAddress}]]></CusAddress>`
      xml += `<CusPhone><![CDATA[${obj.CusPhone}]]></CusPhone>`
      if (obj.CusTaxCode != null) {
        xml += `<CusTaxCode>${obj.CusTaxCode}</CusTaxCode>`
      } else {
        xml += `<CusTaxCode></CusTaxCode>`
      }
      xml += `<PaymentMethod>${obj.Kind_of_Payment}</PaymentMethod>`
      xml += `<KindOfService>${obj.KindOfService}</KindOfService>`

      let type = this.ddlKieuDieuChinh == 1 ? '2' : this.ddlKieuDieuChinh == 2 ? '3' : '4'
      xml += `<Type>${type}</Type>`

      if (this.vthem_extra) xml += `<Extra>${this.fkey_new}</Extra>`
      xml += '<Products>'

      for (var i = 0; i < obj.Products.length; i++) {
        xml += '<Product>'
        xml += `<ProdName><![CDATA[${obj.Products[i].ProdName}]]></ProdName>`
        xml += `<ProdUnit><${obj.Products[i].ProdUnit}</ProdUnit>`
        xml += `<ProdQuantity>${obj.Products[i].ProdQuantity}</ProdQuantity>`
        if (obj.Products[i].ProdPrice != null) {
          xml += `<ProdPrice>${Math.abs(Number(obj.Products[i].ProdPrice))}</ProdPrice>`
        } else {
          xml += `<ProdPrice>${obj.Products[i].ProdPrice}</ProdPrice>`
        }

        if (obj.Products[i].Amount != null) {
          xml += `<Amount>${Math.abs(Number(obj.Products[i].Amount))}</Amount>`
        } else {
          xml += `<Amount>${obj.Products[i].Amount}</Amount>`
        }

        if (obj.Products[i].Amount != null) {
          xml += `<Total>${Math.abs(Number(obj.Products[i].Amount))}</Total>`
        } else {
          xml += `<Total>${obj.Products[i].Amount}</Total>`
        }

        xml += `<VATRate>${obj.Products[i].VATRate}</VATRate>`
        if (obj.Products[i].VATAmount != null) {
          xml += `<VATAmount>${Math.abs(Number(obj.Products[i].VATAmount))}</VATAmount>`
        } else {
          xml += `<VATAmount>${obj.Products[i].VATAmount}</VATAmount>`
        }
        xml += '</Product>'
      }

      xml += '</Products>'

      if (obj.Total != null) {
        xml += `<Total>${Math.abs(Number(obj.Total))}</Total>`
      } else {
        xml += `<Total>${obj.Total}</Total>`
      }

      xml += `<VATRate>${obj.VATRate}</VATRate>`

      if (obj.VATAmount != null) {
        xml += `<VATAmount>${Math.abs(Number(obj.VATAmount))}</VATAmount>`
      } else {
        xml += `<VATAmount>${obj.VATAmount}</VATAmount>`
      }

      if (obj.Amount != null) {
        xml += `<Amount>${Math.abs(Number(obj.Amount))}</Amount>`
      } else {
        xml += `<Amount>${obj.Amount}</Amount>`
      }

      xml += `<AmountInWords>${obj.AmountInWords}</AmountInWords>`

      xml += `<GrossValue_NonTax>${Math.abs(Number(this.txtTongTienKhongThue))}</GrossValue_NonTax>`
      xml += `<GrossValue>${Math.abs(Number(this.txtTongTienKhongChiuThue))}</GrossValue>`
      xml += `<GrossValue0>${Math.abs(Number(this.txtTongTienChiuThue0))}</GrossValue0>`
      xml += '<VatAmount0>0</VatAmount0>'
      xml += `<GrossValue5>${Math.abs(Number(this.txtTongTienChiuThue5))}</GrossValue5>`
      xml += `<VatAmount5>${Math.abs(Number(this.txtTongTienThue5))}</VatAmount5>`
      xml += `<GrossValue10>${Math.abs(Number(this.txtTongTienChiuThue10))}</GrossValue10>`
      xml += `<VatAmount10>${Math.abs(Number(this.txtTongTienThue10))}</VatAmount10>`

      xml += '</AdjustInv>'

      return xml
    },

    GetInvoiceXml_ThayThe_VTT(obj) {
      this.fkey_new = this.GET_FKEY()
      let xml = '<ReplaceInv>'
      xml += `<key>${this.fkey_new}</key>`
      xml += `<CusCode>${obj.CusCode}</CusCode>`
      xml += `<CusName>${obj.CusName}</CusName>`
      xml += `<CusAddress>${obj.CusAddress}</CusAddress>`
      xml += `<CusPhone>${obj.CusPhone}</CusPhone>`
      if (obj.CusTaxCode != null) {
        xml += `<CusTaxCode>${obj.CusTaxCode}</CusTaxCode>`
      } else {
        xml += `<CusTaxCode></CusTaxCode>`
      }

      xml += `<PaymentMethod>${obj.Kind_of_Payment}</PaymentMethod>`
      xml += `<KindOfService>${obj.KindOfService}</KindOfService>`

      if (this.vthem_extra) xml += `<Extra>${this.fkey_new}</Extra>`
      xml += '<Products>'

      for (var i = 0; i < obj.Products.length; i++) {
        xml += '<Product>'
        xml += `<ProdName>${obj.Products[i].ProdName}</ProdName>`
        xml += `<ProdUnit><${obj.Products[i].ProdUnit}</ProdUnit>`
        xml += `<ProdQuantity>${obj.Products[i].ProdQuantity}</ProdQuantity>`
        if (obj.Products[i].ProdPrice != null) {
          xml += `<ProdPrice>${Math.abs(Number(obj.Products[i].ProdPrice))}</ProdPrice>`
        } else {
          xml += `<ProdPrice>${obj.Products[i].ProdPrice}</ProdPrice>`
        }

        if (obj.Products[i].Amount != null) {
          xml += `<Amount>${Math.abs(Number(obj.Products[i].Amount))}</Amount>`
        } else {
          xml += `<Amount>${obj.Products[i].Amount}</Amount>`
        }

        if (obj.Products[i].Amount != null) {
          xml += `<Total>${Math.abs(Number(obj.Products[i].Amount))}</Total>`
        } else {
          xml += `<Total>${obj.Products[i].Amount}</Total>`
        }

        xml += '</Product>'
      }

      xml += '</Products>'

      if (obj.Total != null) {
        xml += `<Total>${Math.abs(Number(obj.Total))}</Total>`
      } else {
        xml += `<Total>${obj.Total}</Total>`
      }

      xml += `<VATRate>${obj.VATRate}</VATRate>`

      if (obj.VATAmount != null) {
        xml += `<VATAmount>${Math.abs(Number(obj.VATAmount))}</VATAmount>`
      } else {
        xml += `<VATAmount>${obj.VATAmount}</VATAmount>`
      }

      if (obj.Amount != null) {
        xml += `<Amount>${Math.abs(Number(obj.Amount))}</Amount>`
      } else {
        xml += `<Amount>${obj.Amount}</Amount>`
      }

      xml += `<AmountInWords>${obj.AmountInWords}</AmountInWords>`
      xml += `<Teller>${this.$root.token.getMaNhanVien()}</Teller>`
      xml += '</ReplaceInv>'

      return xml
    },

    GetInvoiceXml_ThayThe_VTT_tt78(obj) {
      this.fkey_new = this.GET_FKEY()
      let xml = '<ReplaceInv>'
      xml += `<key>${this.fkey_new}</key>`
      xml += `<CusCode>${obj.CusCode}</CusCode>`
      xml += `<CusName>${obj.CusName}</CusName>`
      xml += `<CusAddress>${obj.CusAddress}</CusAddress>`
      xml += `<CusPhone>${obj.CusPhone}</CusPhone>`
      if (obj.CusTaxCode != null) {
        xml += `<CusTaxCode>${obj.CusTaxCode}</CusTaxCode>`
      } else {
        xml += `<CusTaxCode></CusTaxCode>`
      }
      xml += `<PaymentMethod>${obj.Kind_of_Payment}</PaymentMethod>`
      xml += `<KindOfService>${obj.KindOfService}</KindOfService>`

      if (this.vthem_extra) xml += `<Extra>${this.fkey_new}</Extra>`
      xml += '<Products>'

      for (var i = 0; i < obj.Products.length; i++) {
        xml += '<Product>'
        xml += `<ProdName>${obj.Products[i].ProdName}</ProdName>`
        xml += `<ProdUnit><${obj.Products[i].ProdUnit}</ProdUnit>`
        xml += `<ProdQuantity>${obj.Products[i].ProdQuantity}</ProdQuantity>`
        if (obj.Products[i].ProdPrice != null) {
          xml += `<ProdPrice>${Math.abs(Number(obj.Products[i].ProdPrice))}</ProdPrice>`
        } else {
          xml += `<ProdPrice>${obj.Products[i].ProdPrice}</ProdPrice>`
        }

        if (obj.Products[i].Amount != null) {
          xml += `<Amount>${Math.abs(Number(obj.Products[i].Amount))}</Amount>`
        } else {
          xml += `<Amount>${obj.Products[i].Amount}</Amount>`
        }

        if (obj.Products[i].Amount != null) {
          xml += `<Total>${Math.abs(Number(obj.Products[i].Amount))}</Total>`
        } else {
          xml += `<Total>${obj.Products[i].Amount}</Total>`
        }

        if (obj.Products[i].VATRate != null) {
          xml += `<VATRate>${Math.abs(Number(obj.Products[i].VATRate))}</VATRate>`
        } else {
          xml += `<VATRate>${obj.Products[i].VATRate}</VATRate>`
        }

        if (obj.Products[i].VATAmount != null) {
          xml += `<VATAmount>${Math.abs(Number(obj.Products[i].VATAmount))}</VATAmount>`
        } else {
          xml += `<VATAmount>${obj.Products[i].VATAmount}</VATAmount>`
        }

        xml += '</Product>'
      }

      xml += '</Products>'

      if (obj.Total != null) {
        xml += `<Total>${Math.abs(Number(obj.Total))}</Total>`
      } else {
        xml += `<Total>${obj.Total}</Total>`
      }

      xml += `<VATRate>${obj.VATRate}</VATRate>`

      if (obj.VATAmount != null) {
        xml += `<VATAmount>${Math.abs(Number(obj.VATAmount))}</VATAmount>`
      } else {
        xml += `<VATAmount>${obj.VATAmount}</VATAmount>`
      }

      if (obj.Amount != null) {
        xml += `<Amount>${Math.abs(Number(obj.Amount))}</Amount>`
      } else {
        xml += `<Amount>${obj.Amount}</Amount>`
      }

      xml += `<AmountInWords>${obj.AmountInWords}</AmountInWords>`

      xml += `<GrossValue_NonTax>${Math.abs(Number(this.txtTongTienKhongThue))}</GrossValue_NonTax>`
      xml += `<GrossValue>${Math.abs(Number(this.txtTongTienKhongChiuThue))}</GrossValue>`
      xml += `<GrossValue0>${Math.abs(Number(this.txtTongTienChiuThue0))}</GrossValue0>`
      xml += '<VatAmount0>0</VatAmount0>'
      xml += `<GrossValue5>${Math.abs(Number(this.txtTongTienChiuThue5))}</GrossValue5>`
      xml += `<VatAmount5>${Math.abs(Number(this.txtTongTienThue5))}</VatAmount5>`
      xml += `<GrossValue10>${Math.abs(Number(this.txtTongTienChiuThue10))}</GrossValue10>`
      xml += `<VatAmount10>${Math.abs(Number(this.txtTongTienThue10))}</VatAmount10>`
      if(this.vtt_hddt_hd_user != '1')  xml += `<Teller>${this.$root.token.getMaNhanVien()}</Teller>`
      xml += '</ReplaceInv>'

      return xml
    },

    GetInvoiceXml_Cus() {
      try {
        if (this.txtTenKhachHang == null || this.txtTenKhachHang.length == 0) {
          this.txtTenKhachHang = this.customerInfo.txtTenKhachHang
        }
        if (this.vma_gd == null || this.vma_gd.length == 0) {
          this.vma_gd = this.customerInfo.vma_gd
        }
        if (this.txtMaSoThue == null || this.txtMaSoThue == '' || this.txtMaSoThue.length == 0) {
          this.txtMaSoThue = this.customerInfo.txtMaSoThue
        }
        if (this.txtDiaChi == null || this.txtDiaChi=='' || this.txtDiaChi.length == 0) {
          this.txtDiaChi = this.customerInfo.txtDiaChi
        }
        if (this.txtSoDienThoai == null || this.txtSoDienThoai.length == 0) {
          this.txtSoDienThoai = this.customerInfo.txtSoDienThoai
        }
        if (this.txtSoDienThoai == null) {
          this.txtSoDienThoai = ''
        }
        if (this.txtMaSoThue == null) {
          this.txtMaSoThue = ''
        }
      } catch (err) {
        console.log('🚀 ~ file: indexPayment.vue:3507 ~ GetInvoiceXml_Cus ~ err:', err)
      }
      let xml = '<Customers><Customer>'
      xml += `<Name><![CDATA[${this.txtTenKhachHang}]]></Name>`
      xml += `<Code>${this.vma_gd}</Code>`
      xml += `<TaxCode><![CDATA[${this.txtMaSoThue}]]></TaxCode>`
      xml += `<Address><![CDATA[${this.txtDiaChi}]]></Address>`
      xml += '<BankAccountName></BankAccountName>'
      xml += '<BankName></BankName>'
      xml += '<BankNumber></BankNumber>'
      xml += `<Email><![CDATA[${this.txtEmail}]]></Email>`
      xml += '<Fax></Fax>'
      xml += `<Phone><![CDATA[${this.txtSoDienThoai}]]></Phone>`
      xml += '<ContactPerson></ContactPerson>'
      xml += '<RepresentPerson></RepresentPerson>'
      if (this.vloai_kh == '1' || this.txtMaSoThue == '') xml += '<CusType>0</CusType>'
      else xml += '<CusType>1</CusType>'
      xml += '</Customer></Customers>'
      return xml
    },

    async Get_TTND() {
      const res = await api.thongtin_nguoidung2(this.axios, {})
      return res.data.data ? res.data.data : []
    },

    async convertForVerifyFkey(fkey) {
      const res = await api.convertForVerifyFkey(this.axios, {
        fkey: fkey
      })
      let kq = res.data
      return kq
    },

    async ChuyenDoi_XacMinh() {
      console.log('this.Fkey', this.Fkey)
      let kq = await this.convertForVerifyFkey(this.Fkey)
      if (kq.data == null) {
        this.$toast.error(kq.faultString)
        return false
      } else {
        //gọi form hiệu chỉnh HDDT frmHieuChinh_HoaDon_DienTU
        this.html = kq.data
        this.$refs.popupHieuChinhHDDTRef.showModal()
      }
    },

    async convertForStore(invToken) {
      const res = await api.convertForStore(this.axios, {
        invToken: invToken
      })
      let kq = res.data
      return kq
    },

    async convertForStoreFkey(fkey) {
      const res = await api.convertForStoreFkey(this.axios, {
        fkey: fkey
      })
      let kq = res.data
      return kq
    },

    async ChuyenDoi_LuuTru() {
      let kq = await this.convertForStore(this.invToken)
      console.log('this.invToken', this.invToken, kq)
      if (kq.data == null) {
        this.$toast.error(kq.faultString)
        return false
      } else {
        //gọi form hiệu chỉnh HDDT frmHieuChinh_HoaDon_DienTU
        this.html = kq.data
        this.$refs.popupHieuChinhHDDTRef.showModal()
      }
    },

    async unConfirmPaymentFkey(lstFkey) {
      let tt_nd = await this.Get_TTND()

      const res = await api.unConfirmPaymentFkey(this.axios, {
        acPass: tt_nd.pass_hddt_td,
        account: tt_nd.user_hddt_td,
        lstFkey: lstFkey
      })

      let kq = res.data.data ? res.data.data : null
      if (kq == null) {
        kq = res.data.faultString
      }
      return kq
    },

    async Huy_GachNo() {
      let kq = await this.unConfirmPaymentFkey(this.Fkey)
      if (kq.substring(0, 2) != 'OK') {
        this.$toast.error('Lỗi hủy thanh toán HĐĐT !')
        return
      }
      await this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, 0)
      await this.TaoDuLieuBDCT()
      this.$toast.success('Hủy thanh toán hóa đơn thành công !')
      this.button.capnhat = false
      this.invToken = ''
      this.Fkey = ''
      this.fkey_new = ''
      this.HienThiThongTin()
    },

    async ConfirmPaymentFkey(fKey, pattern, serial) {
      this.kq2_errorCode='1'
      if (this.vtt_hddt_hd_user == '1') {
        const res = await api2.ConfirmPaymentFkey_VTT(this.axios, {
          fKey: fKey,
          pattern: pattern,
          serial: serial
        })
        let kq = res.data.errorCode ? res.data.errorCode : res.data.faultString
        if (kq != 0) {
          this.kq2_errorCode=res.data.errorCode
          return JSON.stringify(res.data)
        } else {
          return 0
        }
      } else {
        const res = await api.ConfirmPaymentFkey(this.axios, {
          fKey: fKey,
          pattern: pattern,
          serial: serial
        })
        let kq = res.data.errorCode ? res.data.errorCode : res.data.faultString
        if (kq != 0) {
          this.kq2_errorCode=res.data.errorCode
          return JSON.stringify(res.data)
        } else {
          return 0
        }
      }
    },
    async sp_capnhat_phieutt_hd_v3(p_hddt_fkey, p_loaihoadon_id, p_seri_id, p_seri, p_soseri, p_phieutt_id, p_nhanvien_id, p_donvi_id, p_kqp_kq) {
      const res = await api.sp_capnhat_phieutt_hd_v3(this.axios, {
        p_hddt_fkey: p_hddt_fkey,
        p_loaihoadon_id: p_loaihoadon_id,
        p_seri_id: p_seri_id,
        p_seri: p_seri,
        p_soseri: p_soseri,
        p_phieutt_id: p_phieutt_id,
        p_nhanvien_id: p_nhanvien_id,
        p_donvi_id: p_donvi_id,
        p_kq: p_kqp_kq
      })
      let kq = res.data.error_code ? res.data.error_code : res.data.message
      if (kq != 'BSS-00000000') {
        return res.data.message
      } else {
        return 1
      }
    },
    async GachNo() {
      let pattern = ''
      let seri = ''
      let sohoadon = ''
      if (this.ddlMauSo != 0) {
        for (var i = 0; i < this.options.mauso.length; i++) {
          if (this.options.mauso[i].id == this.ddlMauSo) {
            pattern = this.options.mauso[i].text
          }
        }
      }

      if (this.ddlKyHieu != 0) {
        for (var i = 0; i < this.options.kyhieu.length; i++) {
          if (this.options.kyhieu[i].id == this.ddlKyHieu) {
            seri = this.options.kyhieu[i].text
          }
        }
      }

      let kq = await this.ConfirmPaymentFkey(this.Fkey, '', '')
      if (kq != 0) {
        this.$toast.error('Lỗi thanh toán HĐĐT : ' + kq)
        return
      }
      await this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, 0)
      await this.TaoDuLieuBDCT()

      this.json_update.thaotac = this.ddlThaoTac
      this.json_update.js_bd_hddt = this.dsBDHDDT
      this.json_update.js_bd_hddt_ct = this.dsBDHDDTCT
      this.json_update.phieutt_id = this.vphieutt_id

      let abc = await this.api_CapNhat(this.json_update)
      if (abc.ERROR_CODE == 1) {
        this.button.CapNhat = false
        this.invToken = ''
        this.fkey_new = ''
        this.Fkey = ''
        // this.$toast.success('Thanh toán hóa đơn thành công!')
        this.HienThiThongTin()
      } else {
        // this.$toast.error('Xảy ra lỗi trong quá trình thanh toán hóa đơn !')
      }
    },

    async Xuathoadon() {
      try {
        let pattern = ''
        let seri = ''
        let sohoadon = ''
        if (this.ddlMauSo == 0) {
          this.$toast.error('Bạn chưa chọn mẫu số !')
          return
        }

        if (this.ddlKyHieu == 0) {
          this.$toast.error('Bạn chưa chọn ký hiệu !')
          return
        }
        if (this.ddlMauSo != 0) {
          for (var i = 0; i < this.options.mauso.length; i++) {
            if (this.options.mauso[i].id == this.ddlMauSo) {
              pattern = this.options.mauso[i].text
            }
          }
        }

        if (this.ddlKyHieu != 0) {
          for (var i = 0; i < this.options.kyhieu.length; i++) {
            if (this.options.kyhieu[i].id == this.ddlKyHieu) {
              seri = this.options.kyhieu[i].text
            }
          }
        }

        let kq1 = await this.UpdateCus(this.GetInvoiceXml_Cus())

        if (kq1 != 1) {
          this.$toast.error('Có lỗi trong quá trình cập nhật thông tin khách hàng : ' + kq1)
          return
        }

        let tt_nd = await this.Get_TTND()

        let invoice_xml = await this.GetInvoiceXml_tt78()

        let kq = await this.ImportAndPublishInv(tt_nd.user_hddt_td, tt_nd.pass_hddt_td, invoice_xml, pattern, seri)
        this.bien_kqua = kq

        let result = kq.split(':')
        if (result[0] == 'OK') {
          let InvoicePattern1 = ''
          let SerialNo1 = ''
          let InvoiceNo1 = ''

          let result1 = result[1].split(';')
          InvoicePattern1 = result1[0]
          this.objHoaDonDienTuEdit.InvoicePattern = InvoicePattern1
          let result2 = result1[1].split('-')

          SerialNo1 = result2[0]
          this.objHoaDonDienTuEdit.SerialNo = SerialNo1
          let result3 = result2[1].split('_')

          InvoiceNo1 = result3[1]
          this.objHoaDonDienTuEdit.InvoiceNo = InvoiceNo1

          for (var i = 0; i < this.options.mauso.length; i++) {
            if (this.options.mauso[i].text == objHoaDonDienTuEdit.InvoicePattern) {
              this.ddlMauSo = this.options.mauso[i].id
            }
          }
          for (var i = 0; i < this.options.kyhieu.length; i++) {
            if (this.options.kyhieu[i].text == objHoaDonDienTuEdit.SerialNo) {
              this.ddlKyHieu = this.options.kyhieu[i].id
            }
          }
          this.txtSoHoaDon = InvoiceNo1

          if (!this.TraCuu(InvoicePattern1, SerialNo1, InvoiceNo1)) {
            //return;
          }

          await this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, this.ddlKyHieu)
          await this.TaoDuLieuBDCT()
          this.button.CapNhat = false
          this.invToken = ''
          this.fkey_new = ''
          this.Fkey = ''
          this.$toast.success('Xuất hóa đơn thành công!')
        } else {
          let err = this.ImportAndPublishInvErr
          this.$toast.error('Có lỗi trong quá trình xuất hóa đơn: ' + kq + ':' + err)
        }
      } catch (e) {
        this.$toast.error('Có lỗi: ' + e)
        this.HienThiThongTin()
      }
    },

    async Xuathoadon_GachNo() {
      let resultObject = {
        Fkey: '',
        result: true,
        message: '',
        loi_thanhtoan: 0,
        kq: '',
        kq1: '',
        kq2: '',
        showMessage: false
      }
      try {
        let tt_nd = await this.Get_TTND()
        let pattern = ''
        let seri = ''
        let sohoadon = ''
        if (this.ddlMauSo == 0) {
          this.showErrMessage('Bạn chưa chọn mẫu số !')
          return
        }

        if (this.ddlKyHieu == 0) {
          this.showErrMessage('Bạn chưa chọn ký hiệu !')
          return
        }
        if (this.ddlMauSo != 0) {
          for (var i = 0; i < this.options.mauso.length; i++) {
            if (this.options.mauso[i].id == this.ddlMauSo) {
              pattern = this.options.mauso[i].text
            }
          }
        }

        if (this.ddlKyHieu != 0) {
          for (var i = 0; i < this.options.kyhieu.length; i++) {
            if (this.options.kyhieu[i].id == this.ddlKyHieu) {
              seri = this.options.kyhieu[i].text
              // seri = this.options.kyhieu[i].id
            }
          }
        }
        try {
          if (this.payment_serial != null && this.payment_serial != '' && this.payment_serial.length > 0) {
            seri = this.payment_serial
          }
          if (this.payment_pattern != null && this.payment_pattern != '' && this.payment_pattern.length > 0) {
            pattern = this.payment_pattern
          }
        } catch (errpaymentserial) {
          console.log('~ Xuathoadon_GachNo ~ errpaymentserial:', errpaymentserial)
        }
        let kq1 = await this.UpdateCus(this.GetInvoiceXml_Cus())
        resultObject.kq1 = kq1
        if (kq1 != 1) {
          resultObject.result = false
          resultObject.message = 'Có lỗi trong quá trình cập nhật thông tin khách hàng : ' + kq1
          resultObject.showMessage = this.showErrMessage('Có lỗi trong quá trình cập nhật thông tin khách hàng : ' + kq1)
          return resultObject
        }
        let kq = ''
        try {
          let invoice_tt78 = await this.GetInvoiceXml_tt78()
          kq = await this.ImportAndPublishInv(tt_nd.user_hddt_td, tt_nd.pass_hddt_td, invoice_tt78, pattern, seri)
        } catch (e) {
          this.showErrMessage(`Lỗi xuất HDDT:ImportAndPublishInv ${e}`)
          console.log('🚀 ~ file: indexPayment.vue:3842 ~ Xuathoadon_GachNo ~ e:', e)
          //k can lam (a Văn đã tích hợp)
        }
        resultObject.kq = kq
        let result = kq.split(':')
        if (result[0] == 'OK' || result[0] == 'ok') {
          let result1 = result[1].split(';')
          let InvoicePattern1 = result1[0]
          this.objHoaDonDienTuEdit.InvoicePattern = InvoicePattern1
          let result2 = result1[1].split('-')
          let SerialNo1 = result2[0]
          this.objHoaDonDienTuEdit.SerialNo = SerialNo1
          let result3 = result2[1].split('_')
          let InvoiceNo1 = result3[1]
          this.objHoaDonDienTuEdit.InvoiceNo = InvoiceNo1

          for (var i = 0; i < this.options.mauso.length; i++) {
            if (this.options.mauso[i].text == this.objHoaDonDienTuEdit.InvoicePattern) {
              this.ddlMauSo = this.options.mauso[i].id
            }
          }
          for (var i = 0; i < this.options.kyhieu.length; i++) {
            if (this.options.kyhieu[i].text == this.objHoaDonDienTuEdit.SerialNo) {
              this.ddlKyHieu = this.options.kyhieu[i].id
            }
          }
          this.txtSoHoaDon = InvoiceNo1
          this.lblMauSo = InvoicePattern1
          this.lblKyHieu = SerialNo1
          this.lblSo_No = InvoiceNo1
          let loi_thanhtoan = 0
          resultObject.loi_thanhtoan = 0
          let kq2 = ''
          try {
            kq2 = await this.ConfirmPaymentFkey(this.Fkey, '', '')
            console.log("🚀 ~ file: indexPayment.vue:4088 ~ Xuathoadon_GachNo ~ kq2:", kq2)
            if (kq2 != '0') {
              loi_thanhtoan = 1
              resultObject.loi_thanhtoan = 1
              resultObject.result = false
              resultObject.message = 'Lỗi thanh toán HĐĐT !' + kq2
              resultObject.showMessage = this.showErrMessage('Lỗi thanh toán HĐĐT !' + kq2)
            }
          } catch (e) {
            loi_thanhtoan = 1
            resultObject.loi_thanhtoan = 1
            resultObject.showMessage = this.showErrMessage(e)
            return resultObject
          }
          resultObject.kq2 = kq2

          try {
            // let kq_capnhat = await this.sp_capnhat_phieutt_hd_v3(this.Fkey, pattern, seri, SerialNo1, InvoiceNo1, this.vphieutt_id, this.$root.token.getNhanVienID(), this.$root.token.getDonViID(), kq2)
            let _kq2 = 0;
            if (kq2 != 0) {
              try {
                _kq2=kq2.errorCode
              }
              catch (errKq2) {
                console.log("🚀 ~ file: indexPayment.vue:4111 ~ Xuathoadon_GachNo ~ errKq2:", errKq2)
                _kq2=this.kq2_errorCode
              }
            }
            let kq_capnhat = await this.sp_capnhat_phieutt_hd_v3(this.Fkey, this.ddlMauSo, this.ddlKyHieu, SerialNo1, InvoiceNo1, this.vphieutt_id, this.$root.token.getNhanVienID(), this.$root.token.getDonViID(), _kq2)
            console.log('aaaaaa', this.Fkey, this.ddlMauSo, this.ddlKyHieu, SerialNo1, this.vphieutt_id, InvoiceNo1, this.$root.token.getNhanVienID(), this.$root.token.getDonViID(), _kq2)

            if (kq_capnhat != 1) {
              this.$toast.error('Lỗi: ' + kq_capnhat)
            }
          } catch (errphieutt_hd_v3) {
            console.log('sp_capnhat_phieutt_hd_v3-err:', errphieutt_hd_v3)
          }
          // this.$toast.success("Xuất hóa đơn và thanh toán thành công!");
          // Xuat hoa don thoi
          /*
            try {
                await this.TraCuu(InvoicePattern1, SerialNo1, InvoiceNo1) }
            catch(e)
            {
            }
            */
          await this.TaoDuLieuBD_v2(SerialNo1, InvoiceNo1, this.ddlKyHieu, loi_thanhtoan)
          try {
            console.log('TaoDuLieuBDCT2()')
            await this.TaoDuLieuBDCT2()
          } catch (taodulieubdct2err) {
            console.log('taodulieubdct2err:', taodulieubdct2err)
          }
          console.log('Biendong:this.ddlThaoTac', this.ddlThaoTac, 'this.dsBDHDDT', this.dsBDHDDT, 'this.dsBDHDDTCT', this.dsBDHDDTCT)
          this.json_update.thaotac = this.ddlThaoTac
          this.json_update.js_bd_hddt = this.dsBDHDDT
          this.json_update.js_bd_hddt_ct = this.dsBDHDDTCT
          this.json_update.ketqua_capnhat = result[0]
          this.json_update.invoice_action = 'ImportAndPublishInv_V2'
          this.json_update.hddt_fkey = this.Fkey
          this.json_update.phieutt_id = this.vphieutt_id
          this.json_update.soseri = InvoiceNo1

          let abc = await this.api_CapNhat(this.json_update)
          resultObject.Fkey = this.Fkey
          if (abc.ERROR_CODE == '1') {
            // await this.HienThiThongTin();
            this.button.CapNhat = false
            this.invToken = ''
            this.fkey_new = ''
            this.Fkey = ''
            resultObject.result = true
            resultObject.message = 'Xuất hóa đơn và thanh toán thành công !'
            resultObject.showMessage = this.showMessage('Xuất hóa đơn và thanh toán thành công !')
            return resultObject
          } else {
            resultObject.result = false
            resultObject.message = 'Xảy ra lỗi trong quá trình xuất hóa đơn và thanh toán !'
            resultObject.showMessage = this.showErrMessage('Xảy ra lỗi trong quá trình xuất hóa đơn và thanh toán !')
          }
        } else {
          resultObject.result = false
          resultObject.message = 'Có lỗi trong quá trình xuất hóa đơn: ' + kq + ':' + this.ImportAndPublishInvErr
          resultObject.showMessage = this.showErrMessage(resultObject.message)
        }
        return resultObject
      } catch (err) {
        resultObject.result = false
        resultObject.message = `${err}`
        resultObject.showMessage = this.showErrMessage('Có lỗi: ' + err)
        this.HienThiThongTin()
        return resultObject
      }
    },
    showMessage(msg) {
      try {
        this.$toast.success(msg)
        return true
      } catch (err) {
        console.log('🚀 ~ file: indexPayment.vue ~ line 3856 ~ showMessage ~ err', err)
        return false
      }
    },
    showErrMessage(msg) {
      try {
        this.$toast.error(msg)
        return true
      } catch (err) {
        alert(`${msg} ${err}`)
        console.log('🚀 ~ file: indexPayment.vue ~ line 3856 ~ showMessage ~ err', err)
        return false
      }
    },
    set_vtt_hddt_hd_user(val) {
      this.vtt_hddt_hd_user = val
    },
    set_seri_pattern(serial, pattern) {
      this.payment_pattern = pattern
      this.payment_serial = serial
    },
    async AdjustInvoiceAction_TT78(acPass, account, attachFile, fkey, pattern, serial, xmlInvData) {
      console.log('acPass', acPass, 'account', account, 'attachFile', attachFile, 'convert', 0, 'fkey', fkey, 'pattern', pattern, 'serial', serial, 'xmlInvData', xmlInvData)

      const res = await api.adjustInvoiceAction_tt78(this.axios, {
        acPass: acPass,
        account: account,
        attachFile: attachFile,
        convert: 0,
        fkey: fkey,
        pattern: pattern,
        serial: serial,
        xmlInvData: xmlInvData
      })
      let kq = res.data.data ? res.data.data : null
      console.log('res.data', res.data)
      if (kq == null) {
        kq = res.data.faultString
      }
      return kq
    },

    async adjustInv_V2(acPass, account, fkey, xmlInvData) {
      const res = await api.adjustInvoiceAction_v2(this.axios, {
        acPass: acPass,
        account: account,
        convert: 0,
        fkey: fkey,
        xmlInvData: xmlInvData
      })
      let kq = res.data.data ? res.data.data : ''
      return kq
    },

    async ReplaceInvoiceAction_TT78(acPass, account, attachFile, fkey, pattern, serial, xmlInvData) {
      const res = await api.replaceInvoiceAction(this.axios, {
        acPass: acPass,
        account: account,
        attachFile: attachFile,
        convert: 0,
        fkey: fkey,
        pattern: pattern,
        serial: serial,
        xmlInvData: xmlInvData
      })
      let kq = res.data.data ? res.data.data : ''
      return kq
    },

    async DieuChinhHoaDon() {
      try {
        let pattern = ''
        let seri = ''
        let sohoadon = ''
        if (this.ddlMauSo == 0) {
          this.$toast.error('Bạn chưa chọn mẫu số !')
          return
        }

        if (this.ddlKyHieu == 0) {
          this.$toast.error('Bạn chưa chọn ký hiệu !')
          return
        }

        if (this.ddlMauSo != 0) {
          for (var i = 0; i < this.options.mauso.length; i++) {
            if (this.options.mauso[i].id == this.ddlMauSo) {
              pattern = this.options.mauso[i].text
            }
          }
        }

        if (this.ddlKyHieu != 0) {
          for (var i = 0; i < this.options.kyhieu.length; i++) {
            if (this.options.kyhieu[i].id == this.ddlKyHieu) {
              seri = this.options.kyhieu[i].text
            }
          }
        }
        let pro = this.Product
        let ar_pro = []

        let tong_Total = 0
        let tong_Amount = 0
        let tong_VAT_Amount = 0
        //Bind lại objHoaDonDienTuEdit
        for (var i = 0; i < this.grid_danhsach.length; i++) {
          if (this.grid_danhsach[i].ProdName != null) {
            tong_Total = tong_Total + Number(this.grid_danhsach[i].Total)
            tong_Amount = tong_Amount + Number(this.grid_danhsach[i].Amount)
            tong_VAT_Amount = tong_VAT_Amount + Number(this.grid_danhsach[i].VATAmount)
            ar_pro.push({
              Code: this.grid_danhsach[i].Code,
              Total: this.grid_danhsach[i].Total,
              ProdName: this.grid_danhsach[i].ProdName,
              ProdUnit: this.grid_danhsach[i].ProdUnit,
              ProdQuantity: this.grid_danhsach[i].ProdQuantity,
              ProdPrice: this.grid_danhsach[i].ProdPrice,
              Discount: this.grid_danhsach[i].Discount,
              DiscountAmount: this.grid_danhsach[i].DiscountAmount,
              VATRate: this.grid_danhsach[i].VATRate,
              VATAmount: this.grid_danhsach[i].VATAmount,
              Amount: this.grid_danhsach[i].Amount
            })
          }
        }
        this.objHoaDonDienTuEdit.Products = ar_pro
        this.objHoaDonDienTuEdit.Total = tong_Total
        this.objHoaDonDienTuEdit.VAT_Amount = tong_VAT_Amount

        this.objHoaDonDienTuEdit.Amount = tong_Amount
        await this.doi_so_sang_chu(Number(tong_Amount))
        this.objHoaDonDienTuEdit.Amount_words = this.txtTienVietBangChu

        let tt_nd = await this.Get_TTND()
        let kq = ''
        let invoice_tt78 = await this.GetInvoiceXml_DieuChinh_tt78(this.objHoaDonDienTuEdit)

        //kq = await this.AdjustInvoiceAction_TT78(tt_nd.pass_hddt_td,tt_nd.user_hddt_td,"11",this.Fkey,pattern, seri,invoice_tt78)
        //duynt.hyn 23-09-2022
        kq = await this.AdjustInvoiceAction_TT78('Einv@oi@vn#pt20', 'testnoiboadmin', '11', this.Fkey, pattern, seri, invoice_tt78)
        console.log('kq', kq)
        let result = kq.split(':')
        if (result[0] == 'OK') {
          let InvoicePattern1 = ''
          let SerialNo1 = ''
          let InvoiceNo1 = ''

          let result1 = result[1].split(';')

          InvoicePattern1 = result1[0]
          this.objHoaDonDienTuEdit.InvoicePattern = result1[0]
          this.objHoaDonDienTuEdit.SerialNo = result1[1]
          let result3 = result1[2].split('_')
          this.objHoaDonDienTuEdit.InvoiceNo = result3[1]
          this.fkey_new = result3[0]

          await this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, this.ddlKyHieu)
          await this.TaoDuLieuBDCT()

          for (var i = 0; i < this.options.mauso.length; i++) {
            if (this.options.mauso[i].text == this.objHoaDonDienTuEdit.InvoicePattern) {
              this.ddlMauSo = this.options.mauso[i].id
            }
          }
          for (var i = 0; i < this.options.kyhieu.length; i++) {
            if (this.options.kyhieu[i].text == this.objHoaDonDienTuEdit.SerialNo) {
              this.ddlKyHieu = this.options.kyhieu[i].id
            }
          }
          this.txtSoHoaDon = this.objHoaDonDienTuEdit.InvoiceNo

          this.json_update.thaotac = this.ddlThaoTac
          this.json_update.js_bd_hddt = this.dsBDHDDT
          this.json_update.js_bd_hddt_ct = this.dsBDHDDTCT
          this.json_update.ketqua_capnhat = result[0]
          this.json_update.invoice_action = 'AdjustInvoiceAction_TT78'
          this.json_update.hddt_fkey = this.fkey_new
          this.json_update.phieutt_id = this.vphieutt_id
          this.json_update.js_ds_thoai = this.ds_thoai
          this.json_update.thaydoi_doituong_visa = this.thaydoi_doituong_visa
          this.json_update.doicongacccountvisa = this.doicongacccountvisa
          this.json_update.doitocdoadsl = this.doitocdoadsl
          this.json_update.js_ds_hd_khachhang = this.dsHDKH
          this.json_update.js_ds_hd_thuebao = this.dsHDTB
          this.json_update.js_ds_hdtb_goi_dadv = this._dsGoi
          this.json_update.huonggiao_id = this.huonggiaotn_id
          this.json_update.vasc_status = this.vasc_status
          this.json_update.tien_thoaitra = this.t_tien_thoaitra
          this.json_update.tien_edited = this.t_tien_edited
          //1
          this.json_update.dongbo_test = this.DONGBO_TEST.TEST
          this.json_update.thoaitra_tmp = this.i_thoaitra_tmp

          let abc = await this.api_CapNhat(this.json_update)

          if (abc.ERROR_CODE == 1) {
            this.button.CapNhat = false
            this.invToken = ''
            this.fkey_new = ''
            this.Fkey = ''
            this.$toast.success('Điều chỉnh hóa đơn thành công!')
            this.HienThiThongTin()
          } else {
            this.$toast.error('Xảy ra lỗi trong quá trình điều chỉnh hóa đơn !')
          }

          // this.button.CapNhat= false
          // this.invToken = ""
          // this.fkey_new = ""
          // this.Fkey=""
          // this.$toast.success("Điều chỉnh đơn thành công!");
          // this.HienThiThongTin();
        } else {
          this.$toast.error('Có lỗi trong quá trình điều chỉnh hóa đơn : ' + kq)
          this.HienThiThongTin()
        }
      } catch (e) {
        this.$toast.error('Có lỗi: ' + e)
        this.HienThiThongTin()
      }
    },

    async ThayTheHoaDon() {
      try {
        let pattern = ''
        let seri = ''
        let sohoadon = ''
        if (this.ddlMauSo == 0) {
          this.$toast.error('Bạn chưa chọn mẫu số !')
          return
        }

        if (this.ddlKyHieu == 0) {
          this.$toast.error('Bạn chưa chọn ký hiệu !')
          return
        }
        if (this.ddlMauSo != 0) {
          for (var i = 0; i < this.options.mauso.length; i++) {
            if (this.options.mauso[i].id == this.ddlMauSo) {
              pattern = this.options.mauso[i].text
            }
          }
        }

        if (this.ddlKyHieu != 0) {
          for (var i = 0; i < this.options.kyhieu.length; i++) {
            if (this.options.kyhieu[i].id == this.ddlKyHieu) {
              seri = this.options.kyhieu[i].text
            }
          }
        }
        let pro = this.Product
        let ar_pro = []
        //Bind lại objHoaDonDienTuEdit
        for (var i = 0; i < this.grid_danhsach.length; i++) {
          if (this.grid_danhsach[i].ProdName != null) {
            ar_pro.push({
              Code: this.grid_danhsach[i].Code,
              Total: this.grid_danhsach[i].Total,
              ProdName: this.grid_danhsach[i].ProdName,
              ProdUnit: this.grid_danhsach[i].ProdUnit,
              ProdQuantity: this.grid_danhsach[i].ProdQuantity,
              ProdPrice: this.grid_danhsach[i].ProdPrice,
              Discount: this.grid_danhsach[i].Discount,
              DiscountAmount: this.grid_danhsach[i].DiscountAmount,
              VATRate: this.grid_danhsach[i].VATRate,
              VATAmount: this.grid_danhsach[i].VATAmount,
              Amount: this.grid_danhsach[i].Amount
            })
          }
        }

        this.objHoaDonDienTuEdit.Products = ar_pro
        let tt_nd = await this.Get_TTND()
        let kq = ''

        kq = await this.ReplaceInvoiceAction_TT78(tt_nd.pass_hddt_td, tt_nd.user_hddt_td, '11', this.Fkey, '', '', this.GetInvoiceXml_ThayThe_tt78(this.objHoaDonDienTuEdit))

        let result = kq.split(':')
        if (result[0] == 'OK') {
          let InvoicePattern1 = ''
          let SerialNo1 = ''
          let InvoiceNo1 = ''

          let result1 = result[1].split(';')
          InvoicePattern1 = result1[0]

          this.objHoaDonDienTuEdit.InvoicePattern = result1[0]
          this.objHoaDonDienTuEdit.SerialNo = result1[1]

          let result3 = result1[2].split('_')
          this.objHoaDonDienTuEdit.InvoiceNo = result3[1]

          this.fkey_new = result3[0]

          await this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, this.ddlKyHieu)
          await this.TaoDuLieuBDCT()

          this.ddlMauSo = InvoicePattern1
          this.ddlKyHieu = SerialNo1
          this.txtSoHoaDon = InvoiceNo1
          for (var i = 0; i < this.options.mauso.length; i++) {
            if (this.options.mauso[i].text == this.objHoaDonDienTuEdit.InvoicePattern) {
              this.ddlMauSo = this.options.mauso[i].id
            }
          }
          for (var i = 0; i < this.options.kyhieu.length; i++) {
            if (this.options.kyhieu[i].text == this.objHoaDonDienTuEdit.SerialNo) {
              this.ddlKyHieu = this.options.kyhieu[i].id
            }
          }
          this.txtSoHoaDon = this.objHoaDonDienTuEdit.InvoiceNo

          this.button.CapNhat = false
          this.invToken = ''
          this.fkey_new = ''
          this.Fkey = ''
          this.$toast.success('Thay thế hóa đơn thành công!')
          this.HienThiThongTin()
        } else {
          this.$toast.error('Có lỗi trong quá trình điều chỉnh hóa đơn: ' + kq)
          this.HienThiThongTin()
        }
      } catch (e) {
        this.$toast.error('Có lỗi: ' + e)
        this.HienThiThongTin()
      }
    },

    async cancelInv(acPass, account, fkey) {
      const res = await api.cancelInv(this.axios, {
        acPass: acPass,
        account: account,
        fkey: fkey
      })
      let kq = res.data.data ? res.data.data : null
      if (kq == null) {
        kq = res.data.faultString
      }
      return kq
    },

    async HuyHoaDon() {
      try {
        let tt_nd = await this.Get_TTND()
        let kq = ''

        kq = await this.cancelInv(tt_nd.pass_hddt_td, tt_nd.user_hddt_td, '11', this.Fkey, pattern, seri, this.GetInvoiceXml_DieuChinh_tt78(this.objHoaDonDienTuEdit))

        let result = kq.split(':')
        if (result[0] == 'OK') {
          let InvoicePattern1 = ''
          let SerialNo1 = ''
          let InvoiceNo1 = ''

          let result1 = result[1].split(';')

          InvoicePattern1 = result1[0]
          this.objHoaDonDienTuEdit.InvoicePattern = result1[0]
          this.objHoaDonDienTuEdit.SerialNo = result1[1]
          let result3 = result1[2].split('_')
          this.objHoaDonDienTuEdit.InvoiceNo = result3[1]
          this.fkey_new = result3[0]

          await this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, this.ddlKyHieu)
          await this.TaoDuLieuBDCT()

          this.ddlMauSo = InvoicePattern1
          this.ddlKyHieu = SerialNo1
          this.txtSoHoaDon = InvoiceNo1
          for (var i = 0; i < this.options.mauso.length; i++) {
            if (this.options.mauso[i].text == this.objHoaDonDienTuEdit.InvoicePattern) {
              this.ddlMauSo = this.options.mauso[i].id
            }
          }
          for (var i = 0; i < this.options.kyhieu.length; i++) {
            if (this.options.kyhieu[i].text == this.objHoaDonDienTuEdit.SerialNo) {
              this.ddlKyHieu = this.options.kyhieu[i].id
            }
          }
          this.txtSoHoaDon = this.objHoaDonDienTuEdit.InvoiceNo

          this.button.CapNhat = false
          this.invToken = ''
          this.fkey_new = ''
          this.Fkey = ''
          this.$toast.success('Hủy hóa đơn thành công!')
          this.HienThiThongTin()
        } else {
          this.$toast.error('Có lỗi trong quá trình hủy hóa đơn: ' + kq)
          this.HienThiThongTin()
        }
      } catch (e) {
        this.$toast.error('Có lỗi: ' + e)
        this.HienThiThongTin()
      }
    },

    async HuyThanhToan_HuyHoaDon() {
      try {
        if (this.vpayment == 1) {
          let kq2 = await this.unConfirmPaymentFkey(this.Fkey)

          if (kq2.substring(0, 2) != 'OK') {
            this.$toast.error('Lỗi hủy thanh toán HĐĐT: ' + kq2)
            return
          }
        }
        let tt_nd = await this.Get_TTND()
        let kq = ''

        kq = await this.cancelInv(tt_nd.pass_hddt_td, tt_nd.user_hddt_td, this.Fkey)

        let result = kq.split(':')
        if (result[0] == 'OK') {
          await this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, 0)
          await this.TaoDuLieuBDCT()

          this.json_update.thaotac = this.ddlThaoTac
          this.json_update.js_bd_hddt = this.dsBDHDDT
          this.json_update.js_bd_hddt_ct = this.dsBDHDDTCT
          this.json_update.ketqua_capnhat = result[0]
          this.json_update.invoice_action = 'cancelInv'
          this.json_update.hddt_fkey = ''
          this.json_update.phieutt_id = this.vphieutt_id
          this.json_update.js_ds_thoai = this.ds_thoai
          this.json_update.thaydoi_doituong_visa = this.thaydoi_doituong_visa
          this.json_update.doicongacccountvisa = this.doicongacccountvisa
          this.json_update.doitocdoadsl = this.doitocdoadsl
          this.json_update.js_ds_hd_khachhang = this.dsHDKH
          this.json_update.js_ds_hd_thuebao = this.dsHDTB
          this.json_update.js_ds_hdtb_goi_dadv = this._dsGoi
          this.json_update.huonggiao_id = this.huonggiaotn_id
          this.json_update.vasc_status = this.vasc_status
          this.json_update.tien_thoaitra = this.t_tien_thoaitra
          this.json_update.tien_edited = this.t_tien_edited
          //2
          this.json_update.dongbo_test = this.DONGBO_TEST.TEST
          this.json_update.thoaitra_tmp = this.i_thoaitra_tmp

          let abc = await this.api_CapNhat(this.json_update)
          if (abc.ERROR_CODE == 1) {
            this.$toast.success('Hủy hóa đơn thành công!')
          } else {
            this.$toast.error('Xảy ra lỗi trong quá trình hủy hóa đơn !')
          }

          this.HienThiThongTin()
        } else {
          this.$toast.error('Có lỗi trong quá trình hủy hóa đơn: ' + kq)
          this.HienThiThongTin()
        }
      } catch (e) {
        this.$toast.error('Có lỗi: ' + e)
        this.HienThiThongTin()
      }
    },

    async HuyThanhToan_HuyHoaDon_v2() {
      try {
        let tt_nd = await this.Get_TTND()
        let ds_bd = this.kq_capnhat.DS_BD
        let kq1 = ''
        let kq2 = ''
        if (ds_bd.length > 0) {
          for (var i = 0; i < ds_bd.length; i++) {
            kq1 = await this.unConfirmPaymentFkey(this.Fkey)
            if (kq1.substring(0, 2) != 'OK') {
              this.$toast.error('Lỗi hủy thanh toán HĐĐT: ' + kq1)
              return
            }
            await this.TraCuu(ds_bd[i].FKEY, ds_bd[i].SERI_MOI, ds_bd[i].SO_HD_MOI)

            let kq = await this.cancelInv(tt_nd.pass_hddt_td, tt_nd.user_hddt_td, this.Fkey)
            let result = kq.split(':')
            kq2 = result[0]
            if (result[0] == 'OK') {
              await this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, 0)
              await this.TaoDuLieuBDCT()
            } else {
              kq2 = kq
              this.$toast.error('Có lỗi trong quá trình hủy hóa đơn: ' + kq)
              return
            }
          }

          if (kq1.substring(0, 2) != 'OK' && kq2 == 'OK') {
            this.json_update.thaotac = this.ddlThaoTac
            this.json_update.js_bd_hddt = this.dsBDHDDT
            this.json_update.js_bd_hddt_ct = this.dsBDHDDTCT
            this.json_update.ketqua_capnhat = result[0]
            this.json_update.invoice_action = 'cancelInv'
            this.json_update.hddt_fkey = ''
            this.json_update.phieutt_id = this.vphieutt_id
            this.json_update.js_ds_thoai = this.ds_thoai
            this.json_update.thaydoi_doituong_visa = this.thaydoi_doituong_visa
            this.json_update.doicongacccountvisa = this.doicongacccountvisa
            this.json_update.doitocdoadsl = this.doitocdoadsl
            this.json_update.js_ds_hd_khachhang = this.dsHDKH
            this.json_update.js_ds_hd_thuebao = this.dsHDTB
            this.json_update.js_ds_hdtb_goi_dadv = this._dsGoi
            this.json_update.huonggiao_id = this.huonggiaotn_id
            this.json_update.vasc_status = this.vasc_status
            this.json_update.tien_thoaitra = this.t_tien_thoaitra
            this.json_update.tien_edited = this.t_tien_edited
            //3
            this.json_update.dongbo_test = this.DONGBO_TEST.TEST
            this.json_update.thoaitra_tmp = this.i_thoaitra_tmp

            let abc = await this.api_CapNhat(this.json_update)
            if (abc.ERROR_CODE == 1) {
              this.$toast.success('Hủy hóa đơn thành công!')
              this.HienThiThongTin()
            } else {
              this.$toast.error('Xảy ra lỗi trong quá trình hủy hóa đơn !')
            }
          }
        }
      } catch (e) {
        this.$toast.error('Có lỗi: ' + e)
        this.HienThiThongTin()
      }
    },

    //  ChuyenDoi_XacMinh_VTT()
    // {
    //     let kq = this.convertForVerifyFkey(this.Fkey)
    //     if(kq.substring(0,3) == "ERR")
    //     {
    //         this.$toast.error(kq)
    //         return false
    //     }
    //     else
    //     {
    //         //gọi form hiệu chỉnh HDDT frmHieuChinh_HoaDon_DienTU
    //     }
    // },

    async ChuyenDoi_LuuTru_VTT() {
      let kq = await this.convertForStoreFkey(this.Fkey)
      console.log('this.Fkey', this.Fkey, kq)
      if (kq.data == null) {
        this.$toast.error(kq.faultString)
        return false
      } else {
        //gọi form hiệu chỉnh HDDT frmHieuChinh_HoaDon_DienTU
        this.html = kq.data
        this.$refs.popupHieuChinhHDDTRef.showModal()
      }
    },
    setCustomerInfo(cus) {
      this.customerInfo = cus
    },
    // Huy_GachNo_VTT()
    // {
    //     let kq = this.unConfirmPaymentFkey(this.Fkey)
    //     if(kq != 1)
    //     {
    //         this.$toast.error("Lỗi hủy thanh toán HĐĐT !");
    //         return;
    //     }
    //     this.TaoDuLieuBD(this.objHoaDonDienTu,this.objHoaDonDienTuEdit,0)
    //     this.TaoDuLieuBDCT()
    //     this.$toast.success("Hủy thanh toán hóa đơn thành công !");
    //     this.button.capnhat = false;
    //     this.invToken = "";
    //     this.Fkey = "";
    //     this.fkey_new = "";
    //     this.HienThiThongTin();
    // },

    // async GachNo_VTT()
    // {
    //     let pattern = ""
    //     let seri = ""
    //     let sohoadon = ""
    //     if (this.ddlMauSo != 0)
    //         pattern = this.ddlMauSo
    //     if (this.ddlKyHieu != 0)
    //         seri = this.ddlKyHieu

    //     let kq = await this.ConfirmPaymentFkey(this.Fkey,"","")
    //     if (kq != 1)
    //     {
    //         this.$toast.error("Lỗi thanh toán HĐĐT : " + kq)
    //         return
    //     }
    //     this.TaoDuLieuBD(this.objHoaDonDienTu,this.objHoaDonDienTuEdit,0)
    //     this.TaoDuLieuBDCT()
    //     this.$toast.success("Thanh toán hóa đơn thành công !");
    //     this.button.capnhat = false;
    //     this.invToken = "";
    //     this.Fkey = "";
    //     this.fkey_new = "";
    //     this.HienThiThongTin();
    // },

    // async Xuathoadon_VTT()
    // {
    //     try
    //     {
    //         let pattern = ""
    //         let seri = ""
    //         let sohoadon = ""
    //         if (this.ddlMauSo == 0)
    //         {
    //             this.$toast.error("Bạn chưa chọn mẫu số !" )
    //             return
    //         }

    //         if (this.ddlKyHieu == 0)
    //         {
    //                 this.$toast.error("Bạn chưa chọn ký hiệu !" )
    //                 return
    //         }
    //         pattern = this.ddlMauSo
    //         seri = this.ddlKyHieu

    //         let kq1 = this.UpdateCus(this.GetInvoiceXml_Cus());
    //         if (kq1 != "ok")
    //         {
    //             this.$toast.error("Có lỗi trong quá trình cập nhật thông tin khách hàng : " + kq1)
    //             return
    //         }
    //         let kq = "";
    //         try
    //         {
    //            let tt_nd = await this.Get_TTND()
    //            kq = this.ImportAndPublishInv(tt_nd.user_hddt_td, tt_nd.pass_hddt_td, this.GetInvoiceXml(), pattern, seri);
    //         }
    //         catch(e)
    //         {
    //             //k can lam (a Văn đã tích hợp)
    //         }
    //         let result = kq.split(':');
    //         if (result[0] == "OK")
    //         {
    //             let InvoicePattern1 = "";
    //             let SerialNo1 = "";
    //             let InvoiceNo1 = "";

    //             let result1 = result[1].split(';');
    //             InvoicePattern1 = result1[0]
    //             this.objHoaDonDienTuEdit.InvoicePattern = InvoicePattern1;
    //             let result2 = result1[1].split('-');

    //             SerialNo1 = result2[0]
    //             this.objHoaDonDienTuEdit.SerialNo = SerialNo1;
    //             let result3 = result2[1].split('_');

    //             InvoiceNo1 = result3[1]
    //             this.objHoaDonDienTuEdit.InvoiceNo = InvoiceNo1;

    //             for(var i=0; i < this.options.mauso.length;i++)
    //             {
    //                 if(this.options.mauso[i].text == this.objHoaDonDienTuEdit.InvoicePattern)
    //                 {
    //                     this.ddlMauSo = this.options.mauso[i].id
    //                 }
    //             }
    //             for(var i=0; i < this.options.mauso.length;i++)
    //             {
    //                 if(this.options.kyhieu[i].text == this.objHoaDonDienTuEdit.SerialNo)
    //                 {
    //                     this.ddlKyHieu = this.options.kyhieu[i].id
    //                 }
    //             }
    //             this.txtSoHoaDon = InvoiceNo1

    //             if (!this.TraCuu(InvoicePattern1, SerialNo1, InvoiceNo1))
    //             {
    //                 //return;
    //             }
    //             this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, this.ddlKyHieu)
    //             this.TaoDuLieuBDCT()
    //             this.button.CapNhat= false
    //             this.invToken = ""
    //             this.fkey_new = ""
    //             this.Fkey=""
    //             this.$toast.success("Xuất hóa đơn thành công!");
    //         }
    //         else
    //         {
    //             this.$toast.error("Có lỗi trong quá trình xuất hóa đơn: " + kq)
    //         }
    //     }
    //     catch(e)
    //     {
    //         this.$toast.error("Có lỗi: "+e);
    //         this.HienThiThongTin();
    //     }

    // },

    // async Xuathoadon_GachNo_VTT()
    // {
    //     try
    //     {
    //         let pattern = ""
    //         let seri = ""
    //         let sohoadon = ""
    //         if (this.ddlMauSo == 0)
    //         {
    //             this.$toast.error("Bạn chưa chọn mẫu số !" )
    //             return
    //         }

    //         if (this.ddlKyHieu == 0)
    //         {
    //                 this.$toast.error("Bạn chưa chọn ký hiệu !" )
    //                 return
    //         }
    //         pattern = this.ddlMauSo
    //         seri = this.ddlKyHieu
    //         let kq1 = this.UpdateCus(this.GetInvoiceXml_Cus());
    //         if (kq1 != "ok")
    //         {
    //             this.$toast.error("Có lỗi trong quá trình cập nhật thông tin khách hàng : " + kq1)
    //             return
    //         }
    //         let kq = "";
    //         try
    //         {
    //             kq = this.ImportAndPublishInv(tt_nd.user_hddt_td, tt_nd.pass_hddt_td, this.GetInvoiceXml_tt78(), pattern, seri);
    //         }
    //         catch(e)
    //         {
    //             //k can lam (a Văn đã tích hợp)
    //         }
    //         let result = kq.split(':');
    //         if (result[0] == "OK")
    //         {
    //             let result1 = result[1].split(';');
    //             let InvoicePattern1 = result1[0]
    //             this.objHoaDonDienTuEdit.InvoicePattern = InvoicePattern1;
    //             let result2 = result1[1].split('-');
    //             let SerialNo1 = result2[0]
    //             this.objHoaDonDienTuEdit.SerialNo = SerialNo1;
    //             let result3 = result2[1].split('_');
    //             let InvoiceNo1 = result3[1]
    //             this.objHoaDonDienTuEdit.InvoiceNo = InvoiceNo1;

    //             for(var i=0; i < this.options.mauso.length;i++)
    //             {
    //                 if(this.options.mauso[i].text == this.objHoaDonDienTuEdit.InvoicePattern)
    //                 {
    //                     this.ddlMauSo = this.options.mauso[i].id
    //                 }
    //             }
    //             for(var i=0; i < this.options.mauso.length;i++)
    //             {
    //                 if(this.options.kyhieu[i].text ==this.objHoaDonDienTuEdit.SerialNo)
    //                 {
    //                     this.ddlKyHieu = this.options.kyhieu[i].id
    //                 }
    //             }
    //             this.txtSoHoaDon = InvoiceNo1

    //             this.lblMauSo = InvoicePattern1
    //             this.lblKyHieu = SerialNo1
    //             this.lblSo_No = InvoiceNo1

    //             let loi_thanhtoan = 0
    //             let kq2 = ""
    //             try
    //             {
    //                 kq2 = await this.ConfirmPaymentFkey(this.Fkey,"","")
    //                 if(kq2 != 1)
    //                 {
    //                     loi_thanhtoan = 1
    //                     this.$toast.error("Lỗi thanh toán HĐĐT !" + kq2)
    //                 }

    //             }
    //             catch(e)
    //             {
    //                 loi_thanhtoan = 1
    //                 this.$toast.error(e)
    //             }

    //             try { this.TraCuu(InvoicePattern1, SerialNo1, InvoiceNo1); }
    //             catch(e)
    //             {
    //             }

    //             this.TaoDuLieuBD_v2(SerialNo1, InvoiceNo1, this.ddlKyHieu, loi_thanhtoan);
    //             this.TaoDuLieuBDCT();

    //             this.button.CapNhat= false
    //             this.invToken = ""
    //             this.fkey_new = ""
    //             this.Fkey=""
    //             this.$toast.success("Xuất hóa đơn và thanh toán thành công!");
    //             this.HienThiThongTin();
    //         }
    //         else
    //         {
    //             this.$toast.error("Có lỗi trong quá trình xuất hóa đơn: " + kq)
    //         }
    //     }
    //     catch(e)
    //     {
    //         this.$toast.error("Có lỗi: "+e);
    //         this.HienThiThongTin();
    //     }

    // },

    // DieuChinhHoaDon_VTT()
    // {
    //     try
    //         {
    //         let pattern = ""
    //         let seri = ""
    //         let sohoadon = ""
    //         if (this.ddlMauSo == 0)
    //         {
    //             this.$toast.error("Bạn chưa chọn mẫu số !" )
    //             return
    //         }

    //         if (this.ddlKyHieu == 0)
    //         {
    //                 this.$toast.error("Bạn chưa chọn ký hiệu !" )
    //                 return
    //         }
    //         pattern = this.ddlMauSo
    //         seri = this.ddlKyHieu
    //         let pro = this.Product
    //         let ar_pro=[]
    //         //Bind lại objHoaDonDienTuEdit
    //         for(var i =0; i< this.grid_danhsach.length; i++)
    //         {
    //             if(this.grid_danhsach[i].ProdName != null)
    //             {
    //                 ar_pro.push({
    //                     "Code":grid_danhsach[i].Code,
    //                     "Total":grid_danhsach[i].Total,
    //                     "ProdName":grid_danhsach[i].ProdName,
    //                     "ProdUnit":grid_danhsach[i].ProdUnit,
    //                     "ProdQuantity":grid_danhsach[i].ProdQuantity,
    //                     "ProdPrice":grid_danhsach[i].ProdPrice,
    //                     "Discount":grid_danhsach[i].Discount,
    //                     "DiscountAmount":grid_danhsach[i].DiscountAmount,
    //                     "VATRate":grid_danhsach[i].VATRate,
    //                     "VATAmount":grid_danhsach[i].VATAmount,
    //                     "Amount":grid_danhsach[i].Amount
    //                 })
    //             }
    //         }

    //         this.objHoaDonDienTuEdit.Products = ar_pro
    //         let tt_nd = this.Get_TTND()
    //         let kq = ""

    //         kq = this.AdjustInvoiceAction_TT78(tt_nd.pass_hddt_td,tt_nd.user_hddt_td,"11",this.Fkey,pattern, seri,this.GetInvoiceXml_DieuChinh_tt78(this.objHoaDonDienTuEdit))

    //         let result = kq.split(':');
    //         if (result[0] == "OK")
    //         {

    //             let result1 = result[1].split(';');

    //             InvoicePattern1 = result1[0]
    //             this.objHoaDonDienTuEdit.InvoicePattern = result1[0]
    //             this.objHoaDonDienTuEdit.SerialNo = result1[1]
    //             let result3 = result1[2].split('_')
    //             this.objHoaDonDienTuEdit.InvoiceNo = result3[1]
    //             this.fkey_new = result3[0]

    //             this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, this.ddlKyHieu);
    //             this.TaoDuLieuBDCT();

    //             for(var i=0; i < this.options.mauso.length;i++)
    //             {
    //                 if(this.options.mauso[i].text == this.objHoaDonDienTuEdit.InvoicePattern)
    //                 {
    //                     this.ddlMauSo = this.options.mauso[i].id
    //                 }
    //             }
    //             for(var i=0; i < this.options.mauso.length;i++)
    //             {
    //                 if(this.options.kyhieu[i].text == this.objHoaDonDienTuEdit.SerialNo)
    //                 {
    //                     this.ddlKyHieu = this.options.kyhieu[i].id
    //                 }
    //             }
    //             this.txtSoHoaDon = this.objHoaDonDienTuEdit.InvoiceNo

    //             this.button.CapNhat= false
    //             this.invToken = ""
    //             this.fkey_new = ""
    //             this.Fkey=""
    //             this.$toast.success("Điều chỉnh đơn thành công!");
    //             this.HienThiThongTin();

    //         }
    //         else
    //         {
    //             this.$toast.error("Có lỗi trong quá trình điều chỉnh hóa đơn : " + kq);
    //             this.HienThiThongTin();
    //         }

    //     }
    //     catch(e)
    //     {
    //         this.$toast.error("Có lỗi: "+e);
    //         this.HienThiThongTin();
    //     }

    // },

    // ThayTheHoaDon_VTT()
    // {
    //     try
    //         {
    //         let pattern = ""
    //         let seri = ""
    //         let sohoadon = ""
    //         if (this.ddlMauSo == 0)
    //         {
    //             this.$toast.error("Bạn chưa chọn mẫu số !" )
    //             return
    //         }

    //         if (this.ddlKyHieu == 0)
    //         {
    //                 this.$toast.error("Bạn chưa chọn ký hiệu !" )
    //                 return
    //         }
    //         pattern = this.ddlMauSo
    //         seri = this.ddlKyHieu
    //         let pro = this.Product
    //         let ar_pro=[]
    //         //Bind lại objHoaDonDienTuEdit
    //         for(var i =0; i< this.grid_danhsach.length; i++)
    //         {
    //             if(this.grid_danhsach[i].ProdName != null)
    //             {
    //                 ar_pro.push({
    //                     "Code":grid_danhsach[i].Code,
    //                     "Total":grid_danhsach[i].Total,
    //                     "ProdName":grid_danhsach[i].ProdName,
    //                     "ProdUnit":grid_danhsach[i].ProdUnit,
    //                     "ProdQuantity":grid_danhsach[i].ProdQuantity,
    //                     "ProdPrice":grid_danhsach[i].ProdPrice,
    //                     "Discount":grid_danhsach[i].Discount,
    //                     "DiscountAmount":grid_danhsach[i].DiscountAmount,
    //                     "VATRate":grid_danhsach[i].VATRate,
    //                     "VATAmount":grid_danhsach[i].VATAmount,
    //                     "Amount":grid_danhsach[i].Amount
    //                 })
    //             }
    //         }

    //         this.objHoaDonDienTuEdit.Products = ar_pro
    //         let tt_nd = this.Get_TTND()
    //         let kq = ""

    //         kq = this.ReplaceInvoiceAction_TT78(tt_nd.pass_hddt_td,tt_nd.user_hddt_td,"11",this.Fkey,"", "",this.GetInvoiceXml_ThayThe_tt78(this.objHoaDonDienTuEdit))

    //         let result = kq.split(':');
    //         if (result[0] == "OK")
    //         {
    //             let InvoicePattern1 = "";
    //             let SerialNo1 = "";
    //             let InvoiceNo1 = "";

    //             let result1 = result[1].split(';');
    //             InvoicePattern1 = result1[0]

    //             this.objHoaDonDienTuEdit.InvoicePattern = result1[0]
    //             this.objHoaDonDienTuEdit.SerialNo = result1[1]

    //             let result3 = result1[2].split('_')
    //             this.objHoaDonDienTuEdit.InvoiceNo = result3[1]

    //             this.fkey_new = result3[0]

    //             this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, this.ddlKyHieu);
    //             this.TaoDuLieuBDCT();

    //             this.ddlMauSo = InvoicePattern1
    //             this.ddlKyHieu = SerialNo1
    //             this.txtSoHoaDon = InvoiceNo1
    //             for(var i=0; i < this.options.mauso.length;i++)
    //             {
    //                 if(this.options.mauso[i].text == this.objHoaDonDienTuEdit.InvoicePattern)
    //                 {
    //                     this.ddlMauSo = this.options.mauso[i].id
    //                 }
    //             }
    //             for(var i=0; i < this.options.mauso.length;i++)
    //             {
    //                 if(this.options.kyhieu[i].text == this.objHoaDonDienTuEdit.SerialNo)
    //                 {
    //                     this.ddlKyHieu = this.options.kyhieu[i].id
    //                 }
    //             }
    //             this.txtSoHoaDon = this.objHoaDonDienTuEdit.InvoiceNo

    //             this.button.CapNhat= false
    //             this.invToken = ""
    //             this.fkey_new = ""
    //             this.Fkey=""
    //             this.$toast.success("Thay thế hóa đơn thành công!");
    //             this.HienThiThongTin();

    //         }
    //         else
    //         {
    //             this.$toast.error("Có lỗi trong quá trình điều chỉnh hóa đơn: " + kq);
    //             this.HienThiThongTin();
    //         }

    //     }
    //     catch(e)
    //     {
    //         this.$toast.error("Có lỗi: "+e);
    //         this.HienThiThongTin();
    //     }

    // },

    HuyHoaDon_VTT() {
      try {
        let tt_nd = this.Get_TTND()
        let kq = ''

        kq = this.cancelInv(tt_nd.pass_hddt_td, tt_nd.user_hddt_td, '11', this.Fkey, pattern, seri, this.GetInvoiceXml_DieuChinh_tt78(this.objHoaDonDienTuEdit))

        let result = kq.split(':')
        if (result[0] == 'OK') {
          this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, 0)
          this.TaoDuLieuBDCT()
          this.$toast.success('Hủy hóa đơn thành công!')
          this.HienThiThongTin()
        } else {
          this.$toast.error('Có lỗi trong quá trình hủy hóa đơn: ' + kq)
          this.HienThiThongTin()
        }
      } catch (e) {
        this.$toast.error('Có lỗi: ' + e)
        this.HienThiThongTin()
      }
    },

    async HuyThanhToan_HuyHoaDon_VTT() {
      try {
        let tt_nd = await this.Get_TTND()
        let kq2 = ''
        if (ds_bd.length > 0) {
          for (var i = 0; i < ds_bd.length; i++) {
            kq2 = await this.unConfirmPaymentFkey(this.Fkey)
            if (kq2 != 0) {
              this.$toast.error('Lỗi hủy thanh toán HĐĐT !')
              return
            }

            this.$toast.success('Hủy thanh toán hóa đơn thành công !')

            let kq = await this.cancelInv(tt_nd.pass_hddt_td, tt_nd.user_hddt_td, this.Fkey)
            let result = kq.split(':')
            kq2 = result[0]
            if (result[0] == 'OK') {
              await this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, 0)
              await this.TaoDuLieuBDCT()

              this.json_update.thaotac = this.ddlThaoTac
              this.json_update.js_bd_hddt = this.dsBDHDDT
              this.json_update.js_bd_hddt_ct = this.dsBDHDDTCT
              this.json_update.ketqua_capnhat = result[0]
              this.json_update.invoice_action = 'cancelInv'
              this.json_update.hddt_fkey = ''
              this.json_update.phieutt_id = this.vphieutt_id
              this.json_update.js_ds_thoai = this.ds_thoai
              this.json_update.thaydoi_doituong_visa = this.thaydoi_doituong_visa
              this.json_update.doicongacccountvisa = this.doicongacccountvisa
              this.json_update.doitocdoadsl = this.doitocdoadsl
              this.json_update.js_ds_hd_khachhang = this.dsHDKH
              this.json_update.js_ds_hd_thuebao = this.dsHDTB
              this.json_update.js_ds_hdtb_goi_dadv = this._dsGoi
              this.json_update.huonggiao_id = this.huonggiaotn_id
              this.json_update.vasc_status = this.vasc_status
              this.json_update.tien_thoaitra = this.t_tien_thoaitra
              this.json_update.tien_edited = this.t_tien_edited
              //4
              this.json_update.dongbo_test = this.DONGBO_TEST.TEST
              this.json_update.thoaitra_tmp = this.i_thoaitra_tmp

              let abc = await this.api_CapNhat(this.json_update)
              if (abc.ERROR_CODE == 1) {
                this.$toast.success('Hủy hóa đơn thành công!')
                this.HienThiThongTin()
              } else {
                this.$toast.error('Xảy ra lỗi trong quá trình hủy hóa đơn !')
              }
            } else {
              this.$toast.error('Có lỗi trong quá trình hủy hóa đơn: ' + kq)
              return
            }
          }
        }
      } catch (e) {
        this.$toast.error('Có lỗi: ' + e)
        this.HienThiThongTin()
      }
    },

    async getmatocdo(hdtb_id) {
      let ar_js = {
        hdtb_id: accountName
      }
      const res = await api.getmatocdo(this.axios, {
        p_ds_para: JSON.stringify(ar_js)
      })
      let kq = res.data.data ? res.data.data : ''
      return kq
    },

    async TraCuuThueBao(array) {
      const res = await api.listInvByCus_VTT(this.axios, {
        accountModel: array
      })
      let kq = res.data.data ? res.data.data : []
      return kq
    },

    async removeVNN(array, chuthich) {
      const res = await api.listInvByCus_VTT(this.axios, {
        accountModel: array,
        note: '0',
        reason: chuthich
      })
      let kq = res.data.data ? res.data.data : []
      return kq
    },

    async GetSubscriberInfo(array) {
      const res = await api.listInvByCus_VTT(this.axios, {
        accountModel: array
      })
      let kq = res.data.data ? res.data.data : []
      return kq
    },

    async CancelSubscriberV2(array) {
      const res = await api.listInvByCus_VTT(this.axios, {
        accountModel: array,
        note: 30
      })
      let kq = res.data.data ? res.data.data : []
      return kq
    },

    async DeleteMyTV(array) {
      const res = await api.listInvByCus_VTT(this.axios, {
        accountModel: array
      })
      let kq = res.data.data ? res.data.data : []
      return kq
    },

    async ThayDoi_DoiTuong_Visa(accountName, customerType) {
      let ar_js = {
        accountName: accountName,
        customerType: customerType
      }
      let note = moment(new Date()).format('DD/MM/YYYY')
      const res = await api.ChangeCustomerType(this.axios, {
        customerModel: JSON.stringify(ar_js),
        note: 'Thay doi loai khach hang qua WS ngay: ' + note
      })
      let kq = res.data.data ? res.data.data : 0
      return kq
    },

    async Thoai_Tra() {
      try {
        if (this.i_thoaitra_tmp == 1) {
          // có thoái trả tmp
          for (var i = 0; i < this.ds_thoai.length; i++) {
            let kt_thoaitra = false // Kiem tra da reset port chua
            let h_hdkh_id = this.ds_thoai[i].HDKH_ID
            let h_loaitb_id = this.ds_thoai[i].LOAITB_ID
            let h_dichvuvt_id = this.ds_thoai[i].DICHVUVT_ID
            let h_loaihd_id = this.ds_thoai[i].LOAIHD_ID
            let h_ma_tb = this.ds_thoai[i].MA_TB
            let h_thuebao_id = 0
            if (this.ds_thoai[i].THUEBAO_ID != null) {
              h_thuebao_id = this.ds_thoai[i].THUEBAO_ID
            }
            let h_hdtb_id = this.ds_thoai[i].HDTB_ID
            let h_tt_dongbo = this.ds_thoai[i].STATUS
            if (h_tt_dongbo != null) {
              if (h_tt_dongbo == 5 && h_loaihd_id != 1 && h_loaihd_id != 6) {
                this.$toast.error('Thuê bao đã ở trạng thái kích hoạt.\nBạn không thể thực hiện thoái trả cho thuê bao này !')
                return false
              }
            }

            //thoái trả thanh toán, let kld2 = this.kieuld_id
            let kld2 = this.kq_capnhat.KIEULD_ID

            if (h_loaihd_id == 1 && (kld2 == 550 || kld2 == 551)) {
            } else {
              if (this.ds_thoai[i].HDDT_STATUS == '-9994') {
                kt_thoaitra = true
              }
              if (!kt_thoaitra) {
                // Nếu chưa thoái thì thoái
                //Thoái trả Internet
                if (h_dichvuvt_id == 4) {
                  let ktra = await this.get_app_config()
                  if (ktra.dongbo == 0) {
                    this.$toast.error('Bạn không có quyền kích hoạt. Vui lòng liên hệ admin để được xử lý!')
                    return
                  }

                  this.DONGBO_TEST.TEST = ktra.keyvalue

                  if (this.DONGBO_TEST.TEST == false) {
                    //hủy account Internet Hợp đồng đặt mới đã kích hoạt (status = 5)
                    if (h_tt_dongbo == 5 && h_loaihd_id != 1 && h_loaihd_id != 6) {
                      this.$toast.error('Chỉ thoái trả cho thuê bao đã kích hoạt đối với hợp đồng lắp mới và chuyển đổi loại hình !')
                      return false
                    }
                    if (h_tt_dongbo == 5 && h_loaihd_id == 1) {
                      let account = h_ma_tb
                      let lydohuy = 'Hủy Account'
                      let chuthich = 'Hủy Account'
                      let status = false
                      let array_json = []
                      array_json.push({
                        accountName: account
                      })
                      try {
                        if (h_loaitb_id == 58 || h_loaitb_id == 79 || h_loaitb_id == 11 || h_loaitb_id == 10) {
                          let _flag = await this.TraCuuThueBao(JSON.stringify(array_json))
                          if (_flag.Length == 6 || _flag.Length == 8) {
                            status = await this.removeVNN(JSON.stringify(array_json), chuthich)
                          }
                          if (_flag.Length == 1) {
                            status = true
                          }
                        } else if (h_loaitb_id == 61) {
                          let info = await this.GetSubscriberInfo(JSON.stringify(array_json))

                          if (info[0].IDNo === null || info[0].IDNo == null) {
                            status = true
                          } else {
                            if (info[0].StatusID == 2 && info[0].STBID != null) {
                              //StatusID =="2" => hoạt động bt, đã k/h và khai báo STB
                              let kq_cancel = await this.CancelSubscriberV2(JSON.stringify(array_json))
                              if (kq_cancel == 0) {
                                let kq = await this.DeleteMyTV(JSON.stringify(array_json))
                                if (kq != 0 && kq != 1) status = false
                                else status = true
                              } else {
                                this.$toast.error("Kích hoạt Cancel thuê bao '" + account + "' lỗi:" + kq_cancel)
                                return false
                              }
                            } else if (info[0].StatusID == 6) status = true
                            // status hủy
                            else {
                              let kq = await this.DeleteMyTV(JSON.stringify(array_json))
                              if (kq != 0 && kq != 1) status = false
                              else status = true
                            }
                          }
                        } else if (h_loaitb_id == 18 || h_loaitb_id == 19) {
                          //HTVC_FIBERVNN_DIGITAL,HTVC_FIBERVNN_ANALOG
                          status = true
                        }
                        if (status) {
                          //CapNhat_Log_HuyKH(h_hdtb_id, account);
                        } else {
                          if (h_loaitb_id == 61) this.$toast.error('Có lỗi trong quá trình hủy Account trên VASC !')
                          if (h_loaitb_id == 11 || h_loaitb_id == 58 || h_loaitb_id == 79) this.$toast.error('Có lỗi trong quá trình hủy Account trên Visa !')
                          return false
                        }
                      } catch (e) {
                        this.$toast.error('Có lỗi: ' + e)
                        return false
                      }
                    }
                    //Chuyển đổi loại hình
                    if (h_tt_dongbo == 5 && h_loaihd_id == 6) {
                      if (h_loaitb_id == 11 || h_loaitb_id == 58 || h_loaitb_id == 79) this.h_doituong_id = this.kq_capnhat.DOITUONG_ID
                      {
                        this.thaydoi_doituong_visa = await this.ThayDoi_DoiTuong_Visa(h_ma_tb, this.h_doituong_id)
                        if (this.thaydoi_doituong_visa == 0) {
                          {
                            this.$toast.error('Có lỗi trong quá trình thay đổi đối tượng trên Visa !')
                            return false
                          }
                        }

                        let dtBienDong = this.kq_capnhat.DTBIENDONG
                        let dtHopDong = this.kq_capnhat.DTHOPDONG
                        try {
                          if (this.dtBienDong.length == 0) {
                            this.$toast.error('Không tìm thấy thông tin biến động thuê bao hoặc thiếu thông tin biến động port! Liên hệ ADMIN để xử lý.')
                            return false
                          }
                        } catch (e) {
                          this.$toast.error('Không tìm thấy thông tin biến động thuê bao hoặc thiếu thông tin biến động port! Liên hệ ADMIN để xử lý.')
                          return false
                        }

                        let h_account = dtBienDong[0].MA_TB
                        let h_ip = ''
                        let h_loaicuoc_id = ''
                        let h_tucthi = 0
                        let h_tocdo_id = ''

                        //Đổi cổng
                        this.doicongacccountvisa = await this.DoiCongAcccountVisa(dtBienDong, dtHopDong[0].SO_PL)
                        if (this.doicongacccountvisa == 0) {
                          this.$toast.error('Có lỗi trong quá trình đổi cổng Account ' + h_ma_tb + ' trên Visa !')
                          return false
                        }
                        //Đổi tốc độ
                        h_tocdo_id = dtBienDong[0].MATOCDO
                        h_ip = dtBienDong[0].IP
                        h_loaicuoc_id = dtBienDong[0].MUCCUOC_ID_VDC
                        this.doitocdoadsl = await this.DoiTocDoADSL(h_account, h_tocdo_id, h_ip, h_tucthi, false, false)
                        if (this.doitocdoadsl == 0) {
                          this.$toast.error('Có lỗi trong quá trình chuyển đổi loại hình thuê bao từ Mega sang FTTH trên Visa !')
                          return false
                        }

                        //Thay đổi thông tin tính cước
                        let billingInfo = dtBienDong[0].THONGTIN_TC
                        if (billingInfo != null || billingInfo != null) {
                          let kq = this.ChangeBillingInfo(h_account, billingInfo, 'Thoái trả hợp đồng')
                          if (!kq) {
                            this.$toast.error('Có lỗi trong quá trình thay đổi thông tin tính cước cho thuê bao FTTH !')
                            return false
                          }
                        }
                        //nếu thay đổi mức cước thì gọi hàm dưới
                        if (dtBienDong[0].MUCCUOC_ID != dtHopDong[0].MUCCUOC_ID) {
                          if (h_loaicuoc_id == '') {
                            this.$toast.error('Có lỗi trong quá trình kích hoạt đổi mức cước FTTH trên Visa !')
                            return false
                          }
                          if (await this.ThayDoiHinhThucTT(h_account, h_loaicuoc_id, h_tucthi)) {
                          } else {
                            this.$toast.error('Có lỗi trong quá trình kích hoạt đổi mức cước FTTH trên Visa !')
                            return false
                          }
                        }
                      }

                      if (h_loaitb_id == 61) {
                        if (dtBienDong.length == 0) {
                          this.$toast.error('Không tìm thấy thông tin biến động thuê bao hoặc thiếu thông tin biến động port! Liên hệ ADMIN để xử lý.')
                          return false
                        }
                        let account = dtBienDong[0].MA_TB
                        let system = dtBienDong[0].SYSTEM
                        let sPortID = dtBienDong[0].IP + '/' + dtBienDong[0].SHELF + '/' + dtBienDong[0].SLOT + '/' + dtBienDong[0].PORT
                        await this.UpdateDeviceInfo(account, system, sPortID)
                      }
                    }

                    //Hủy đặt chỗ ADSL, FTTH, 1260 trường hợp hợp đồng đặt mới, đổi số chưa kích hoạt
                    if (h_tt_dongbo != 5 && (h_loaihd_id == 1 || h_loaihd_id == 14 || h_loaihd_id == 30)) {
                      if (h_loaitb_id == 11 || h_loaitb_id == 58 || h_loaitb_id == 79 || h_loaitb_id == 10) {
                        if (h_ma_tb.Length > 0) {
                          let handatcho = this.kq_capnhat.HANDATCHO
                          if (handatcho != null) {
                            if (!(await this.HuyDatChoMegaVNN(h_ma_tb))) {
                              this.$toast.error('Có lỗi xảy ra trong quá trình hủy đặt chỗ account ' + h_ma_tb + ' trên Visa!')
                              return false
                            }
                          }
                        }
                      }
                    }

                    if ((h_loaitb_id == 58 || h_loaitb_id == 61) && h_loaihd_id == 1) {
                      let ds_kt1 = await this.LayDS_ThamSoMD()
                      let dsTS = ds_kt1.filter(function(ds) {
                        return ds.MA_TS == 'DONGBO_GOI_DA_DV'
                      })

                      let dongbo_dadv = -1
                      if (dsTS.length > 0) {
                        dongbo_dadv = this.dsTS[0].TEN_TS
                      }
                      if (dongbo_dadv == 1) {
                        this._dsGoi = this.kq_capnhat.LAY_TT_GOI_GDVP
                        if (this._dsGoi[0].GOI_HOME.length > 0) {
                          let tt_nd = await this.Get_TTND()
                          let _error = ''
                          let _result = ''
                          let _dsGoi_home = this._dsGoi[0].GOI_HOME
                          for (var i = 0; i < _dsGoi_home.length; i++) {
                            _result = this.XOA_GOI_V3(_dsGoi_home[j].GOI_NEO_ID, '84' + _dsGoi_home[j].MA_TB, _dsGoi_home[j].ACC_FIBER, '1', tt_nd.tentat)
                            if (_result != 0) {
                              this.$toast.error('Có lỗi trong quá trình kích hoạt gói tích hợp trên VNP: ' + _error)
                              return false
                            }
                          }
                        }

                        if (this._dsGoi[0].GOI_GDVP.length > 0) {
                          let _error = ''
                          let _result = ''
                          let _dsGoi_gdvp = this._dsGoi[0].GOI_GDVP
                          for (var i = 0; i < _dsGoi_gdvp.length; i++) {
                            _result = _ws.XOA_GOI_V3(_dsGoi_gdvp[j].GOI_NEO_ID, '84' + _dsGoi_gdvp[j].MA_TB, _dsGoi_gdvp[j].ACC_FIBER, '1', tt_nd.tentat)
                            if (_result != 0) {
                              this.$toast.error('Có lỗi trong quá trình kích hoạt gói tích hợp trên VNP: ' + _error)
                              return false
                            }
                          }
                        }
                      }
                    }
                  }
                }

                //Thoái trả IMS
                if (h_dichvuvt_id == 11) {
                  let ktra = await this.get_app_config()
                  if (ktra.dongbo == 0) {
                    this.$toast.error('Bạn không có quyền kích hoạt. Vui lòng liên hệ admin để được xử lý!')
                    return
                  }

                  this.DONGBO_TEST.TEST = ktra.keyvalue

                  if (this.DONGBO_TEST.TEST == false) {
                    //Hủy số trên VTN Hợp đồng lắp mới đã kích hoạt (status = 5)
                    if (h_tt_dongbo == 5 && (h_loaihd_id == 1 || h_loaihd_id == 6)) {
                      if (h_loaitb_id == 62 || h_loaitb_id == 63) {
                        //IMS_SIP,IMS_POTS
                        let kq = this.HuyThueBao(h_ma_tb)
                        if (kq != 1) {
                          this.$toast.error('Có lỗi trong quá trình hủy thuê bao trên IMS mới !\n' + kq)
                          return false
                        }
                      } else {
                        let result = await this.ThanhLyThueBao(h_ma_tb)
                        if (result != 1) {
                          this.$toast.error('Có lỗi trong quá trình gửi kích hoạt VTN !\n' + result)
                          return false
                        }
                      }
                    }
                  }
                }

                //Thoái trả cố định-db xl
                //Thoái trả Fsecure
                //a hiếu y.c bỏ

                //DichVuVienThong.MEGAWAN || DichVuVienThong.TSL ||  DichVuVienThong.METRONET
                if (h_dichvuvt_id == 8 || h_dichvuvt_id == 9 || h_dichvuvt_id == 7) {
                  let hdtb_id_hni = this.kq_capnhat.HDTB_ID_HNI
                  if (hdtb_id_hni != -1) {
                    let viloai = 0
                    if (h_loaihd_id == 7) {
                      //LoaiHopDong.THAY_DOI_DV
                      viloai = 2
                    } else {
                      viloai = 1
                    }

                    //thiếu hàm bán chéo
                    let _obj = await this.ThucHienHoanCongHopDong(vhdtb_id_hni, tt_nd.ngay_cn.ToString('dd/MM/yyyy HH:mm'), tt_nd.ten_nd, h_ma_tb, '', viloai, 2, 'Thực hiện thoái trả ', '', 'pttb', 'pttb321')
                    if (obj == 0) {
                      this.$toast.error('webservice_hni : ' + _obj)
                      return false
                    }
                  }
                }
              }

              if (!kt_thoaitra) {
                if (h_loaitb_id == 58 && (h_loaihd_id == 1 || h_loaihd_id == 6)) {
                  let dsMyTV = this.kq_capnhat.DSMYTV
                  let drDaHC = dsMyTV.filter(function(ds) {
                    return ds.TRANGTHAI_HC == 1
                  })
                  if (drDaHC.length > 0) {
                    //Tạo hợp đồng xóa thuê bao khỏi gói gia đình
                    this.TaoDuLieu_huygoi_mytv(dsMyTV)
                    let kichhoat = true
                    for (var j = 0; j < this.dsHDTB.length; j++) {
                      //Kích hoạt vasc
                      let vtocdo_id = -1
                      let vmuccuoc_id = 0
                      let thuebao_mytv_id = this.dsHDTB[j].THUEBAO_ID

                      let matocdo_vasc = await this.getmatocdo(thuebao_mytv_id)
                      if (matocdo_vasc == -1) {
                        this.$toast.error('Thông tin về tốc độ chưa được đồng bộ lên VASC. Bạn hãy kiểm tra lại !')
                        return false
                      }
                      this.KichHoatVASC(this.dsHDTB[j].MA_TB, matocdo_vasc, 0, '')
                    }
                  }

                  let drChuaKH = dsMyTV.filter(function(ds) {
                    return ds.TRANGTHAI_HC == 1 && ds.STATUS == 0
                  })
                  let drDaKH = dsMyTV.filter(function(ds) {
                    return ds.TRANGTHAI_HC == 1 && ds.STATUS == 5
                  })

                  // if (drChuaKH.Length > 0)//--> xóa tb khỏi hdtb gói đa dv
                  // {
                  //     for (var j = 0; j < drChuaKH.Length; j++)
                  //     {
                  //         let hdtb_mytv_id = drChuaKH[j].HDTB_ID
                  //         let goi_id = drChuaKH[j].GOI_ID
                  //         let nhomtb_id = drChuaKH[j].NHOMTB_ID
                  //         //Xóa khỏi hdtb_goi_dadv
                  //         //db
                  //     }
                  // }

                  if (drDaKH.Length > 0) {
                    //--> xóa tb khỏi hdtb gói đa dv
                    for (var j = 0; j < drDaKH.Length; j++) {
                      let hdtb_mytv_id = drChuaKH[j].HDTB_ID
                      let goi_id = drChuaKH[j].GOI_ID
                      let nhomtb_id = drChuaKH[j].NHOMTB_ID
                      //Xóa khỏi hdtb_goi_dadv
                      let matocdo_vasc = await this.getmatocdo(hdtb_mytv_id)
                      if (matocdo_vasc == -1) {
                        this.$toast.error('Thông tin về tốc độ chưa được đồng bộ lên VASC. Bạn hãy kiểm tra lại !')
                        return false
                      }
                      this.KichHoatVASC(this.drDaKH[j].MA_TB, matocdo_vasc, 0, '')
                      //db
                    }
                  }
                }
              }
            }
            return true
          }
        } else return true
      } catch (e) {
        this.$toast.error('Có lỗi: ' + e)
        return false
      }
    },

    async KichHoatVASC(MyTVAcc, matocdo_mytv, ma_goi_tichhop, lydo) {
      let IPTVAccount = await this.ChuanHoa_XML(MyTVAcc)
      let packageNo = await this.ChuanHoa_XML(matocdo_mytv)
      if (packageNo == '' || packageNo == null) {
        this.$toast.error('Gói dịch vụ MyTV này chưa được đồng bộ với VASC. Bạn không thể tiếp tục cập nhật dữ liệu.')
        return 0
      }

      let megaMyTV = await this.ChuanHoa_XML(ma_goi_tichhop)
      let reason = await this.ChuanHoa_XML(lydo)

      const res = await api.changePackageAndMegaVNN(this.axios, {
        iptvaccount: IPTVAccount,
        megaMyTV: megaMyTV,
        packageNo: packageNo,
        reason: reason,
        serviceCD: '',
        serviceVAS: ''
      })
      this.vasc_status = res.data.data ? res.data.data : 0
    },

    async XOA_GOI_V3(goi_id_neo, matb_dd, acc_fiber, typeid, agent) {
      const res = await api.changePackageAndMegaVNN(this.axios, {
        account: acc_fiber,
        agent: agent,
        msisdn: matb_dd,
        packageid: goi_id_neo,
        typeid: typeid
      })
      let kq = res.data.data ? res.data.data : ''
      return kq
    },

    async ChuanHoa_XML(Xau) {
      let result = ''
      result = await Xau.replace('&', '&amp;')
      result = await result.replace('<', '&lt;')
      result = await result.replace('>', '&gt;')
      result = await result.replace("'", '&apos;')
      result = await result.replace('"', '&quot;')
      return result
    },

    async TaoDuLieu_huygoi_mytv(dsMyTV) {
      await this.TaoDuLieu_HDKH_huygoi_mytv(dsMyTV)
      await this.TaoDuLieu_HDTB_huygoi_mytv(dsMyTV)
    },

    async TaoDuLieu_HDKH_huygoi_mytv(dsMyTV) {
      let tt_nd = await this.Get_TTND()

      let drMyTV = dsMyTV.filter(function(ds) {
        return ds.TRANGTHAI_HC == 1
      })

      let khachhang_id = drMyTV[0].KHACHHANG_ID
      let ar_dbkh = this.kq_capnhat.DS_DBKH

      this.hdkh_id_huygoi_mytv = await this.POST_GET_KEYS('HD_KHACHHANG')
      ar_dbkh.MA_GD = await this.Sinh_MaGD(28)
      ar_dbkh.KHACHHANG_ID = khachhang_id
      ar_dbkh.NGAYLAP_HD = moment(new Date()).format('DD/MM/YYYY')
      ar_dbkh.NGAY_YC = moment(new Date()).format('DD/MM/YYYY')
      ar_dbkh.GHICHU = 'Tự động xóa thuê bao khỏi gói đa dịch vụ'
      ar_dbkh.DONVI_ID = tt_nd.donvi_id
      ar_dbkh.NHANVIEN_ID = tt_nd.nhanvien_id
      ar_dbkh.LOAIHD_ID = 28
      ar_dbkh.NGAY_CN = moment(new Date()).format('DD/MM/YYYY')
      ar_dbkh.NGUOI_CN = tt_nd.ma_nd
      ar_dbkh.MAY_CN = tt_nd.may_cn
      ar_dbkh.IP_CN = tt_nd.ip

      this.dsHDKH = ar_dbkh
    },

    async TaoDuLieu_HDTB_huygoi_mytv(dsMyTV) {
      let tt_nd = await this.Get_TTND()
      let drMyTV = dsMyTV.filter(function(ds) {
        return ds.TRANGTHAI_HC == 1
      })
      if (drMyTV.Length > 0) {
        this.dsHDTB = []
        this.dsHDTBGOI = []
        for (var i = 0; i < drMyTV.Length; i++) {
          this.hdtb_id_huygoi_mytv = await this.POST_GET_KEYS('HD_THUEBAO')
          rowHDTB = this.HD_THUEBAORow()
          rowHDTB.HDTB_ID = this.hdtb_id_huygoi_mytv
          rowHDTB.HDKH_ID = this.hdtb_id_huygoi_mytv
          rowHDTB.THUEBAO_ID = drMyTV[i].THUEBAO_ID
          rowHDTB.MA_TB = drMyTV[i].MA_TB
          rowHDTB.TEN_TB = drMyTV[i].TEN_TB
          rowHDTB.DIACHI_TB = drMyTV[i].DIACHI_TB
          rowHDTB.KIEULD_ID = 282
          rowHDTB.DOITUONG_ID = drMyTV[i].DOITUONG_ID
          rowHDTB.TTHD_ID = 1
          rowHDTB.LOAITB_ID = 61
          rowHDTB.DICHVUVT_ID = 4
          rowHDTB.DONVI_ID = tt_nd.donvi_id
          rowHDTB.NGUOI_CN = tt_nd.ma_nd
          rowHDTB.NGAY_CN = moment(new Date()).format('DD/MM/YYYY')
          rowHDTB.MAY_CN = tt_nd.may_cn

          //thiếu QUYTRINH_ID,MUCUOC_TB||MUCUOCTB_ID
          let dset = this.kq_capnhat.DS_HUONGGIAO
          if (dset.length > 0) {
            this.huonggiaotn_id = dset[0].HUONGGIAO_ID
            this.quytrinh_id_2 = dset[0].QUYTRINH_ID
          }
          rowHDTB.QUYTRINH_ID = this.quytrinh_id_2

          let mucuoctb_id = this.kq_capnhat.MUCUOCTB_ID
          let mucuoctb = this.kq_capnhat.MUCUOC_TB

          if (mucuoctb_id != -1) rowHDTB.MUCUOCTB_ID = mucuoctb_id
          if (mucuoctb != -1) rowHDTB.MUCUOC_TB = mucuoctb

          this.dsHDTB.push(rowHDTB)

          rowGOI = this.HDTB_GOI_DADVRow()
          rowGOI.GOI_ID = drMyTV[i].GOI_ID
          rowGOI.NHOMTB_ID = drMyTV[i].NHOMTB_ID
          rowGOI.CHUNHOM = 0
          rowGOI.HDTB_ID = this.hdtb_id_huygoi_mytv
          rowGOI.THANG_HUONG = 0
          this.dsHDTBGOI.push(rowGOI)
        }
      }
    },

    async Sinh_MaGD(loaihd_id) {
      const res = await api.Sinh_MaGD(this.axios, {
        loaihd_id: loaihd_id,
        metaClass: {}
      })
      let kq = res.data.data ? res.data.data : []
      return kq
    },

    async ThucHienHoanCongHopDong() {
      //chưa có api
      const res = await api.listInvByCus_VTT(this.axios, {
        accountModel: JSON.stringify(ar_ma_tb),
        attributes: ar_att
      })
      let kq = res.data.data ? res.data.data : []
      return kq
    },

    async DoiCongMegavnn(ar_ma_tb, ar_att) {
      const res = await api.listInvByCus_VTT(this.axios, {
        accountModel: JSON.stringify(ar_ma_tb),
        attributes: ar_att
      })
      let kq = res.data.data ? res.data.data : []
      return kq
    },

    async ChangeBillingInfo(accountName, billingInfo, note) {
      const res = await api.ChangeBillingInfo(this.axios, {
        accountModel: JSON.stringify(accountName),
        billingInfo: billingInfo,
        note: note
      })
      let kq = res.data.data ? res.data.data : false
      return kq
    },

    async DoiCongAcccountVisa(dt, so_pl) {
      try {
        let i_account = dt[0].MA_TB
        let password = dt[0].PASSWORD
        let cusName = dt[0].TEN_TB
        let cusAdd = dt[0].DIACHI_LD
        let phone = dt[0].MATB_TN
        let port = dt[0].PORT
        let vci = dt[0].VCI
        let vpi = dt[0].VPI
        let slot = dt[0].SLOT
        let soHd = so_pl
        let brasId = dt[0].BRAS_ID
        let systemId = dt[0].SYSTEM
        let rackId = dt[0].RACK
        let shelfId = dt[0].SHELF
        if (this.thamso == 4) {
          let ar_matb = {
            accountName: i_account
          }
          let ar_att = []
          ar_att.push({
            type: 'PORT',
            value: port,
            type: 'VCI',
            value: vci,
            type: 'VPI',
            value: vpi,
            type: 'SLOT',
            value: slot,
            type: 'BRAS-ID',
            brasId: brasId,
            type: 'DSLAM-ID',
            value: this.dslamId,
            type: 'PARAMETER',
            value: 'VISANEW'
          })

          let result = await api.DoiCongMegavnn(ar_matb, ar_att)
          if (result == 0) {
            return 0
          } else {
            this.$toast.success('Đổi cổng Account ' + i_account + ' trên Visa thành công !')
            return 1
          }
        } else if (this.thamso == 7) {
          let ar_matb = {
            accountName: i_account
          }
          let ar_att = []
          ar_att.push({
            type: 'PORT',
            value: port,
            type: 'VCI',
            value: vci,
            type: 'VPI',
            value: vpi,
            type: 'SLOT',
            value: slot,
            type: 'BRAS-ID',
            brasId: brasId,
            type: 'DSLAM-ID',
            value: this.dslamId,
            type: 'RACKID',
            value: rackId,
            type: 'SELFID',
            value: shelfId,
            type: 'SYSTEMID',
            value: systemId,
            type: 'PARAMETER',
            value: 'VISANEW'
          })

          let result = await this.DoiCongMegavnn(ar_matb, ar_att)
          if (result == 0) {
            return 0
          } else {
            this.$toast.success('Đổi cổng Account ' + i_account + ' trên Visa thành công !')
            return 1
          }
        }
        return 1
      } catch (e) {
        this.$toast.error('Có lỗi xảy ra trong quá trình đổi cổng account trên Visa.\n' + e)
        return false
      }
    },

    async DoiTocDoADSL(account, tocdo_id, ip, tucthi, autoIpFlag, oldIp) {
      if (tucthi == 0) {
        const res = await api.modifyServiceTypeMegaVNN(this.axios, {
          accountModel: JSON.stringify(account),
          accountServiceModel: JSON.stringify(tocdo_id),
          autoIpFlag: autoIpFlag,
          ip: ip,
          oldIp: oldIp
        })
        let kq = res.data.data ? res.data.data : 0
        return kq
      } else {
        const res = await api.modifyServiceTypeMegaVNN1(this.axios, {
          accountModel: JSON.stringify(account),
          accountServiceModel: JSON.stringify(tocdo_id),
          autoIpFlag: autoIpFlag,
          ip: ip,
          oldIp: oldIp
        })
        let kq = res.data.data ? res.data.data : 0
        return kq
      }
    },

    async ThayDoiHinhThucTT(account, tariffType, effectType) {
      let result = false
      const res = await api.ThayDoiHTTT(this.axios, {
        accountModel: {
          accountName: account
        },
        accountServiceModel: {
          tariffType: tariffType
        },
        effectType: effectType
      })
      result = res.data.data ? res.data.data : false
      return result
    },

    async UpdateDeviceInfo(i_account, system, sPortID) {
      let result = -1
      const res = await api.UpdateDeviceInfo(this.axios, {
        myTVAccount: i_account,
        strDeviceName: system,
        strPortID: sPortID
      })
      result = res.data.data ? res.data.data : 1
      if (result == 0) {
        return true
      } else {
        return false
      }
    },

    async HuyDatChoMegaVNN(account) {
      let result = -1
      const res = await api.unReservedAccount(this.axios, {
        accountName: account
      })
      result = res.data.data ? res.data.data : ''
      if (result.includes('chưa được đặt chỗ')) {
        return true
      } else {
        return false
      }
    },

    async HuyThueBao(account) {
      let result = -1
      const res = await api.sps_service_ims_deactive(this.axios, {
        msisdn: account
      })
      result = res.data.data ? res.data.data : false
      if (result.Status != 9) {
        if (result.StatusMessage('52000')) {
          return true
        } else {
          return false
        }
      }

      return true
    },

    async ThanhLyThueBao(account) {
      let result = -1
      const res = await api.disconnectSubscriber(this.axios, {
        account_CODE: account
      })
      result = res.data.data ? res.data.data : ''
      if (result.Status != 9) {
        if (result.StatusMessage('52000')) {
          return true
        } else {
          return false
        }
      }

      return true
    },

    async TaoDuLieuBD(objOld, objNew, _seri_id) {
      this.dsBDHDDT = []
      let row = this.BD_HDDT_DATA()
      this.BDHDDT_ID = await this.POST_GET_KEYS('BDHDDT_ID')
      row.BDHDDT_ID = this.BDHDDT_ID
      row.MA_TT = objNew.CusCode
      row.DONVI_ID = this.$root.token.getDonViID()
      row.NHANVIEN_ID = this.$root.token.getNhanVienID()
      row.TEN_TT = this.txtTenKhachHang
      row.DIACHI_TT = this.txtDiaChiKH
      row.MST = this.txtMaSoThue
      if (this.txtMaSoThue == null) {
        row.MST = ''
      }
      row.EMAIL = this.txtEmail
      row.SDT = this.txtSoDienThoai
      row.HINHTHUC_TT = this.txtHinhThucThanhToan
      row.TIEN = this.txtCongTienDV
      row.VAT = this.txtTienTHueGTGT
      row.TYLE_VAT = this.txtTHueGTGT
      row.TIENBANGCHU = this.txtTienVietBangChu
      if (this.ddlThaoTac == 1) {
        row.KIEU_BD = 4
        row.THONGTINHD_CU = ''
        row.THONGTINHD_MOI = this.GetXMLFromObject(objNew)
        row.TRANGTHAI = 1 // Đang sử dụng
      } else if (this.ddlThaoTac == 2) {
        // Chỉnh sửa hóa đơn
        row.THONGTINHD_CU = this.GetXMLFromObject(objOld)
        row.THONGTINHD_MOI = this.GetXMLFromObject(objNew)
        if (this.ddlKieuDieuChinh == 1)
          // Điều chỉnh tăng
          row.KIEU_BD = 8
        else if (this.ddlKieuDieuChinh == 2)
          // Điều chỉnh giảm
          row.KIEU_BD = 9
        else if (this.ddlKieuDieuChinh == 3)
          // Điều chỉnh thông tin
          row.KIEU_BD = 10
        else row.KIEU_BD = 1
        row.TRANGTHAI = 1 // Đang sử dụng
        row.BDHDDT_CHA_ID = this.bdhddt_cha_id
      } else if (this.ddlThaoTac == 3) {
        // Thay thế hóa đơn
        row.KIEU_BD = 2
        row.THONGTINHD_CU = this.GetXMLFromObject(objOld)
        row.THONGTINHD_MOI = this.GetXMLFromObject(objNew)
        row.TRANGTHAI = 1 // Đang sử dụng
        row.BDHDDT_CHA_ID = this.bdhddt_cha_id
      } else if (this.ddlThaoTac == 4) {
        // Hủy hóa đơn
        row.KIEU_BD = 3
        row.THONGTINHD_CU = this.GetXMLFromObject(objOld)
        row.THONGTINHD_MOI = this.GetXMLFromObject(objNew)
        row.TRANGTHAI = 4 // Hủy hóa đơn
        row.BDHDDT_CHA_ID = this.bdhddt_cha_id
      } else if (this.ddlThaoTac == 5) {
        // Hủy thanh toán + Hủy hóa đơn
        row.KIEU_BD = 11
        row.THONGTINHD_CU = this.GetXMLFromObject(objOld)
        row.THONGTINHD_MOI = this.GetXMLFromObject(objNew)
        row.TRANGTHAI = 4 // Đang sử dụng
        row.BDHDDT_CHA_ID = this.bdhddt_cha_id
      } else if (this.ddlThaoTac == 6) {
        // Xuất hóa đơn + gạch nợ
        row.KIEU_BD = 6
        row.THONGTINHD_CU = this.GetXMLFromObject(objOld)
        row.THONGTINHD_MOI = this.GetXMLFromObject(objNew)
        row.TRANGTHAI = 1 // Đang sử dụng
        row.BDHDDT_CHA_ID = this.bdhddt_cha_id
      } else if (this.ddlThaoTac == 8) {
        // hủy gạch nợ
        row.KIEU_BD = 7
        row.THONGTINHD_CU = this.GetXMLFromObject(objOld)
        row.THONGTINHD_MOI = this.GetXMLFromObject(objNew)
        row.TRANGTHAI = 1 // Đang sử dụng
        row.BDHDDT_CHA_ID = this.bdhddt_cha_id
      }
      row.LOAIHOADON_CU = objOld.InvoicePattern
      row.LOAIHOADON_MOI = objNew.InvoicePattern
      row.SERI_CU = objOld.SerialNo
      row.SERI_MOI = objNew.SerialNo
      row.SO_HD_CU = objOld.InvoiceNo
      row.SO_HD_MOI = objNew.InvoiceNo
      //duynt.hyn 16/09/2022
      row.TONGTIEN = Math.abs(Number(this.t_tien_edited))
      row.TIENGOC = this.tiengoc
      row.NGUOI_CN = await this.$root.token.getUserName()
      row.MAY_CN = await this.$root.token.getMachine()
      row.NGAY_CN = moment(new Date()).format('DD/MM/YYYY')

      if (this.Tag == 2) row.GHICHU = '(Chức năng admin) ' + this.txtGhiCHuHoaDon
      else if (this.Tag == 3) row.GHICHU = '(Chức năng admin_) ' + this.txtGhiCHuHoaDon
      else row.GHICHU = '(Chức năng XHĐ) ' + this.txtGhiCHuHoaDon

      row.HDKH_ID = this.vhdkh_id
      row.SERI_MOI_ID = _seri_id
      row.LOAIHOADON_MOI_ID = this.ddlMauSo
      row.PHIEUTT_ID = this.vphieutt_id
      if (this.txtBienBanDinhKem != '') {
        row.FILE_DINHKEM = this.array_byte
      }
      this.dsBDHDDT.push(row)
    },

    TaoDuLieuBDCT() {
      console.log('TaoDuLieuBDCT', this.grid_danhsach)
      this.dsBDHDDTCT = []
      for (let i = 0; i < this.grid_danhsach.length; i++) {
        if (this.grid_danhsach[i].ProdName != null) {
          if (this.grid_danhsach[i].ProdName.length > 0) {
            let row = this.BD_HDDT_CT_DATA()
            row.BDHDDT_ID = this.BDHDDT_ID
            row.DONGIA = this.grid_danhsach[i].ProdPrice
            row.DONVITINH = this.grid_danhsach[i].ProdUnit == null ? '' : this.grid_danhsach[i].ProdUnit
            row.NOIDUNG = this.grid_danhsach[i].ProdName
            row.SOLUONG = this.grid_danhsach[i].ProdQuantity
            row.STT = this.grid_danhsach[i].STT
            row.THANHTIEN = this.grid_danhsach[i].Amount
            this.dsBDHDDTCT.push(row)
          }
        }
      }
    },
    async TaoDuLieuBDCT2() {
      console.log('TaoDuLieuBDCT2', this.grid_danhsach)
      this.dsBDHDDTCT = []
      if (this.grid_danhsach != null && this.grid_danhsach.length > 0) {
        for (let i = 0; i < this.grid_danhsach.length; i++) {
          try {
            if (this.grid_danhsach[i].ProdName != null) {
              if (this.grid_danhsach[i].ProdName.length > 0) {
                let row = this.BD_HDDT_CT_DATA()
                row.BDHDDT_ID = this.BDHDDT_ID
                row.DONGIA = this.grid_danhsach[i].ProdPrice
                row.DONVITINH = this.grid_danhsach[i].ProdUnit == null ? '' : this.grid_danhsach[i].ProdUnit
                row.NOIDUNG = this.grid_danhsach[i].ProdName
                row.SOLUONG = this.grid_danhsach[i].ProdQuantity
                row.STT = this.grid_danhsach[i].STT
                row.THANHTIEN = this.grid_danhsach[i].Amount
                this.dsBDHDDTCT.push(row)
              }
            }
          } catch (err) {
            console.log('🚀 ~ file: indexPayment.vue:5976 ~ TaoDuLieuBDCT2 ~ err:', err)
          }
        }
        console.log('TaoDuLieuBDCT2-Result:', this.dsBDHDDTCT)
        return this.dsBDHDDTCT
      } else {
        console.log('recall api_HienThiTT ')
        let danhsach = await this.api_HienThiTT()
        this.ds_tb = danhsach.DS_HOPDONG_TB_HDDT
        let ar_pro = []
        let tongtien = 0
        let tien = 0
        let vat = 0
        let tien0 = 0
        let tien5 = 0
        let vat5 = 0
        let tien10 = 0
        let vat10 = 0
        let tien_novat = 0
        let tien_novat_c = 0
        let counter = 1
        let product = this.ProductWithSTT()
        if (this.ds_tb.length > 0) {
          for (let i = 0; i < this.ds_tb.length + 3; i++) {
            if (this.ds_tb.length <= i) {
              ar_pro.push({
                STT: i + 1,
                Code: '',
                Remark: '',
                Total: '',
                ProdName: '',
                ProdUnit: '',
                ProdQuantity: '',
                ProdPrice: '',
                Discount: '',
                DiscountAmount: '',
                VATRate: '',
                VATAmount: '',
                Amount: ''
              })
            } else {
              product.STT = i + 1
              product.Amount = this.ds_tb[i].AMOUNT
              product.ProdName = this.ds_tb[i].PRODNAME
              product.ProdUnit = this.ds_tb[i].PRODUNIT
              product.ProdQuantity = this.ds_tb[i].PRODQUANTITY
              product.ProdPrice = this.ds_tb[i].PRODPRICE

              tien = Number(tien) + Number(this.ds_tb[i].TIEN)
              vat = Number(vat) + Number(this.ds_tb[i].VAT)
              tongtien = Number(tongtien) + Number(this.ds_tb[i].TONGTIEN)

              if (this.ds_tb[i].TYLE_VAT == 0) tien0 = Number(tien0) + Number(this.ds_tb[i].TIEN)
              if (this.ds_tb[i].TYLE_VAT == 5) {
                tien5 = Number(tien5) + Number(this.ds_tb[i].TIEN)
                vat5 = Number(vat5) + Number(this.ds_tb[i].VAT)
              }
              if (this.ds_tb[i].TYLE_VAT == 10) {
                tien10 = Number(tien10) + Number(this.ds_tb[i].TIEN)
                vat10 = Number(vat10) + Number(this.ds_tb[i].VAT)
              }
              if (this.ds_tb[i].TYLE_VAT == -2) {
                tien_novat = Number(tien_novat) + Number(this.ds_tb[i].TIEN)
              }
              if (this.ds_tb[i].TYLE_VAT == -1) {
                tien_novat_c = Number(tien_novat_c) + Number(this.ds_tb[i].TIEN)
              }
              product.Total = this.ds_tb[i].TOTAL
              product.VATAmount = this.ds_tb[i].VATAMOUNT
              product.VATRate = this.ds_tb[i].VATRATE
              this.txtTHueGTGT = this.ds_tb[i].VATRATE

              ar_pro.push({
                STT: product.STT,
                Code: product.Code,
                Remark: product.Remark,
                Total: product.Total,
                ProdName: product.ProdName,
                ProdUnit: product.ProdUnit,
                ProdQuantity: product.ProdQuantity,
                ProdPrice: product.ProdPrice,
                Discount: product.Discount,
                DiscountAmount: product.DiscountAmount,
                VATRate: product.VATRate,
                VATAmount: product.VATAmount,
                Amount: product.Amount
              })
            }
          }
          this.grid_danhsach = ar_pro
          this.TaoDuLieuBDCT()
          return this.dsBDHDDTCT
        }
      }
    },

    async TaoDuLieuBD_v2(SerialNo1, InvoiceNo1, _seri_id, loi) {
      try {
        this.dsBDHDDT = []
        let row = this.BD_HDDT_DATA()
        this.BDHDDT_ID = await this.POST_GET_KEYS('BDHDDT_ID')
        row.BDHDDT_ID = this.BDHDDT_ID
        row.MA_TT = this.vma_gd
        row.DONVI_ID = this.$root.token.getDonViID()
        row.NHANVIEN_ID = this.$root.token.getNhanVienID()
        row.TEN_TT = this.txtTenKhachHang
        row.DIACHI_TT = this.txtDiaChiKH
        row.MST = this.txtMaSoThue
        if (this.txtMaSoThue == null) {
          row.MST = ''
        }
        row.EMAIL = this.txtEmail
        row.SDT = this.txtSoDienThoai
        row.HINHTHUC_TT = this.txtHinhThucThanhToan
        row.TIEN = this.txtCongTienDV
        row.VAT = this.txtTienTHueGTGT
        row.TYLE_VAT = this.txtTHueGTGT
        row.TIENBANGCHU = this.txtTienVietBangChu
        if (loi != 0) {
          row.THANHTOAN_LOI = loi
        }
        row.KIEU_BD = 5
        row.TRANGTHAI = 1 // Đang sử dụng
        row.LOAIHOADON_CU = this.ddlMauSo
        row.LOAIHOADON_MOI = this.ddlMauSo
        row.SERI_CU = SerialNo1
        row.SERI_MOI = SerialNo1
        row.SO_HD_CU = InvoiceNo1
        row.SO_HD_MOI = InvoiceNo1
        row.TONGTIEN = this.t_tien_edited
        row.TIENGOC = this.tiengoc
        row.NGUOI_CN = await this.$root.token.getUserName()
        row.MAY_CN = await this.$root.token.getMachine()
        row.NGAY_CN = moment(new Date()).format('DD/MM/YYYY')
        if (this.Tag == 2) row.GHICHU = '(Chức năng admin) ' + this.txtGhiCHuHoaDon
        else if (this.Tag == 3) row.GHICHU = '(Chức năng admin_) ' + this.txtGhiCHuHoaDon
        else row.GHICHU = '(Chức năng XHĐ) ' + this.txtGhiCHuHoaDon

        row.HDKH_ID = this.vhdkh_id
        row.SERI_MOI_ID = _seri_id
        row.LOAIHOADON_MOI_ID = this.ddlMauSo
        row.PHIEUTT_ID = this.vphieutt_id
        if (this.txtBienBanDinhKem != '') {
          row.FILE_DINHKEM = this.array_byte
        }
        this.dsBDHDDT.push(row)
      } catch (err) {
        console.log('🚀 ~ file: indexPayment.vue:6070 ~ TaoDuLieuBD_v2 ~ err:', err)
      }
    },

    async listInvByCus_VTT(magd, fromDate, toDate) {
      const res = await api.listInvByCus_VTT(this.axios, {
        cusCode: magd,
        fromDate: fromDate,
        toDate: toDate
      })
      let kq = res.data.data ? res.data.data : []

      return kq
    },

    async POST_GET_KEYS(keyname) {
      let key = ''
      await api
        .POST_GET_KEYS(this.axios, {
          keyname: keyname
        })
        .then((res) => {
          key = res.data.data ? res.data.data : []
        })
      return key
    },

    // async KhoaPhieu()
    // {
    //     const res = await api.downloadInvNoPay(this.axios, {
    //         "invToken": this.vphieutt_id
    //     })
    //     let kq =  res.data.data ? res.data.data : ''
    //     if(kq == 1)
    //     {
    //         this.$toast.success("Khóa phiếu thành công !")
    //     }
    //     else
    //     {

    //     }

    // },

    async LayDS_ThamSoMD() {
      const res = await api.ThaoTac_Changed(this.axios, {})
      return res.data.data ? res.data.data : []
    },

    async ThaoTac_Changed() {
      let ds_kt1 = await this.LayDS_ThamSoMD()
      let det = ds_kt1.filter(function(ds) {
        return ds.MA_TS == 'XUAT_HD_CHI_SUA_ND'
      })

      let XUAT_HD_CHI_SUA_ND = 0
      if (det.length > 0) {
        if (det[0].TEN_TS == 1) {
          XUAT_HD_CHI_SUA_ND = 1
        }
      }
      if (this.ddlThaoTac == 6 && XUAT_HD_CHI_SUA_ND == 1) this.Khoa_khi_xuat_HD(true)
      else this.Khoa_khi_xuat_HD(false)

      //this.Clean()
    },

    keymonitor: function(event) {
      if (event.key == 'Enter') {
        this.HienThiThongTin()
      }
    },

    Khoa_khi_xuat_HD(value) {
      this.disabledTenKH = value
      this.disabledDiaChi = value
      this.disabledMST = value
      this.disabledMST = value
      this.disabledHTTT = value

      if (value == true) {
        document.getElementById('btnFile').style.display = 'none'
      } else {
        document.getElementById('btnFile').style.display = 'block'
      }

      this.disabledEmail = value
      this.disabledGhiCHuHoaDon = value
      this.disabledTheMoRong = value
      this.disabledTienVietBangChu = value
    },

    async downloadInvNoPay(bien) {
      const res = await api.downloadInvNoPay(this.axios, {
        invToken: bien
      })
      let kq = res.data.data ? res.data.data : ''
      return kq
    },

    GetXMLFromObject(objHoaDonDienTu) {
      let builder = new xml2js.Builder()
      let xml = builder.buildObject(objHoaDonDienTu)
      return xml
    },

    ObjectToXML(filexml) {
      // var parser = new DOMParser();
      // var xmlDoc = parser.parseFromString(filexml,"text/xml");
      // return xmlDoc.getElementsByTagName(key)

      let xmlDoc
      if (window.DOMParser) {
        const parser = new DOMParser()
        xmlDoc = parser.parseFromString(filexml.trim(), 'text/xml')
      } else {
        xmlDoc = new ActiveXObject('Microsoft.XMLDOM')
        xmlDoc.async = false
        xmlDoc.loadXML(filexml.trim())
      }
      return xmlDoc
    },

    importFile(e) {
      const myFile = e.target.files
      if (!myFile.length) {
        return
      } else if (!/\.(pdf|doc|docx)$/.test(myFile[0].name.toLowerCase())) {
        return alert('File upload chưa đúng định dạng (.pdf/.doc/.docx)')
      }
      this.array_byte = []
      this.txtBienBanDinhKem = myFile[0].name

      var reader = new FileReader()
      var fileByteArray = []
      reader.readAsArrayBuffer(myFile[0])
      reader.onloadend = function(evt) {
        if (evt.target.readyState == FileReader.DONE) {
          var arrayBuffer = evt.target.result,
            array = new Uint8Array(arrayBuffer)
          for (var i = 0; i < array.length; i++) {
            fileByteArray.push(array[i])
          }
        }
      }
      this.array_byte = fileByteArray
      var input = document.getElementById('upload-file')
      input.value = ''
    },

    async Kiemtra_dulieu(_thaotac) {
      let tt_nd = await this.Get_TTND()
      if (tt_nd.user_hddt_td === null) {
        this.$toast.error('Người dùng chưa được cấp tài khoản xuất hóa đơn !')
        return false
      }
      this.tiengoc = 0

      if (this.txtDiaChi == '') {
        this.$toast.error('Lỗi xuất HĐĐT: Địa chỉ khách hàng không được để trống !')
        return false
      }
      let kt_km = false
      for (var i = 0; i < this.grid_danhsach.length; i++) {
        if (this.grid_danhsach[i].ProdName != null) {
          kt_km = true
          break
        }
      }
      if (kt_km == false) {
        // this.$toast.error('Kiểm tra lại dịch vụ sử dụng trên lưới !')
        //return false
      }

      for (var i = 0; i < this.ds_kt.length; i++) {
        if (this.ds_kt[i].KIEU_BD == '4' || this.ds_kt[i].KIEU_BD == '5' || this.ds_kt[i].KIEU_BD == '8') this.kt_tongtien = Number(this.kt_tongtien) + Number(this.ds_kt[i].TONGTIEN)
        else if (this.ds_kt[i].KIEU_BD == '3' || this.ds_kt[i].KIEU_BD == '9' || this.ds_kt[i].KIEU_BD == '11') this.kt_tongtien = Number(this.kt_tongtien) - Number(this.ds_kt[i].TONGTIEN)
      }

      this.tiengoc = this.kt_tongtien
      let vnguoicn = ''
      if (this.ds_kt.length > 0) {
        vnguoicn = this.ds_kt[0].NGUOI_CN
      }
      if (_thaotac == 1 || _thaotac == 6) {
        // Xuất hóa đơn
        if (this.Tag == 1 && this.khoa == 2) {
          this.$toast.error('Phiếu thanh toán đã bị Khóa. Bạn không thể thao tác !')
          return false
        }
        if (this.Tag != 2) {
          if (this.vlamtron_dongia) {
            if (Math.abs(Number(this.t_tien_edited) - Number(this.t_tien_load)) >= 1) {
              this.$toast.error('Tổng tiền xuất hóa đơn điện tử không đúng với tổng tiền thanh toán !')
              return false
            }
          } else {
            if (this.t_tien_edited != this.t_tien_load) {
              this.$toast.error('Tổng tiền xuất hóa đơn điện tử không đúng với tổng tiền thanh toán !')
              return false
            }
          }
          if (this.da_xuathd == true) {
            this.$toast.error('Hợp đồng đã xuất hóa đơn. Bạn không được thực hiện thao tác này !')
            return false
          }
          if (Number(this.txtTongCongTienThanhToan) < 0) {
            this.$toast.error('Tiền xuất hóa đơn không được Âm !')
            return false
          }
        }
      } else if (_thaotac == 2) {
        // Điều chỉnh hóa đơn
        if (this.Tag == 1 && this.khoa == 2) {
          this.$toast.error('Phiếu thanh toán đã bị Khóa. Bạn không thể thao tác !')
          return false
        }
        if (this.ddlKieuDieuChinh == 0) {
          this.$toast.error('Bạn chưa chọn kiểu điều chỉnh !')
          return false
        }
        if (this.da_xuathd == false) {
          this.$toast.error('Hợp đồng chưa xuất hóa đơn. Bạn không được thực hiện thao tác này !')
          return false
        }

        if (this.Tag != 2 && this.Tag != 3) {
          if (this.ktra_quyen == 0) {
            if (this.vnhanvien_id != (await this.$root.token.getNhanVienID()) || vnguoicn != (await this.$root.token.getUserName())) {
              this.$toast.error('Bạn không được điều chỉnh hóa đơn do người khác tạo !')
              return false
            }
          }
        }
        //Kiểm tra xem khách hàng đã xem hóa đơn hay chưa
        let kq = await this.listInvByCus_VTT(this.objHoaDonDienTu.CusCode, '', '')
        if (kq.substring(0, 3) == 'ERR') {
          this.$toast.error(kq)
          return false
        } else {
          let objKetQua = this.ObjectToXML(kq)
          let ar_kq = []

          for (var i = 0; i < objKetQua.getElementsByTagName('Item').length; i++) {
            if (objKetQua.getElementsByTagName('invToken')[i].textContent == this.invToken) {
              ar_kq.push({
                invToken: this.invToken,
                fkey: objKetQua.getElementsByTagName('fkey')[i].textContent,
                status: objKetQua.getElementsByTagName('status')[i].textContent
              })
              break
            }
          }
          console.log('ar_kqduy123', ar_kq, objKetQua)
          this.Fkey = ar_kq[0].fkey
          if (this.Tag != 2) {
            if (ar_kq[0].status != 1 && ar_kq[0].status != 4) {
              this.$toast.error('Hóa đơn đã bị thay thế/hủy. Không thể điều chỉnh !')
              return false
            }
          }
        }

        if (this.objHoaDonDienTu.CusCode == null) {
          this.$toast.error('Không có thông tin khách hàng để điều chỉnh')
          return false
        }
        if (this.objHoaDonDienTu.CusName == null) {
          this.$toast.error('Tên khách hàng không được để trống !')
          return false
        }
        if (this.objHoaDonDienTu.CusAddress == null) {
          this.$toast.error('Địa chỉ khách hàng không được để trống !')
          return false
        }

        if (this.Tag != 2 && this.Tag != 3) {
          if (this.ddlKieuDieuChinh == 1) {
            // điều chỉnh tăng
            if (this.i_thoaitra_tmp == 0 || (this.i_thoaitra_tmp == 1 && this.t_tien_thoaitra != 0)) {
              if (Math.abs(this.t_tien_edited) == 0) {
                this.$toast.error('Tiền điều chỉnh phải khác 0')
                return false
              }
            }

            if (Number(this.t_tien_tt) + Number(this.t_tien_thoaitra) != Number(this.kt_tongtien) + Number(this.t_tien_edited)) {
              this.$toast.error('Tiền hóa đơn điện tử lệch với tiền hợp đồng. Bạn hãy kiểm tra lại tiền điều chình !')
              return false
            }
          }

          if (this.ddlKieuDieuChinh == 2) {
            // điều chỉnh giảm
            if (this.i_thoaitra_tmp == 0 || (this.i_thoaitra_tmp == 1 && this.t_tien_thoaitra != 0)) {
              if (this.t_tien_edited == 0) {
                this.$toast.error('Tiền điều chỉnh phải khác 0')
                return false
              }
            }
            console.log('tien', Number(this.t_tien_tt), Number(this.t_tien_thoaitra), Number(this.kt_tongtien), Number(this.t_tien_edited))
            if (Number(this.t_tien_tt) + Number(this.t_tien_thoaitra) != Number(this.kt_tongtien) + Number(this.t_tien_edited)) {
              this.$toast.error('Tiền hóa đơn điện tử lệch với tiền hợp đồng. Bạn hãy kiểm tra lại tiền điều chình !')
              return false
            }
          }
          if (this.ddlKieuDieuChinh == 3) {
            // điều thông tin
            if (this.i_thoaitra_tmp == 1) {
              this.$toast.error('Hợp đồng đang có thoái trả tạm. Bạn không được điều chỉnh thông tin !')
              return false
            }
            if (this.t_tien_edited != 0) {
              this.$toast.error('Điều chỉnh thông tin. Khoản mục tiền phải = 0 !')
              return false
            }
          }
        }
      } else if (_thaotac == 3) {
        // Thay thế hóa đơn
        if (this.Tag == 1 && this.khoa == 2) {
          this.$toast.error('Phiếu thanh toán đã bị Khóa. Bạn không thể thao tác !')
          return false
        }

        if (this.Tag != 2) {
          if (this.t_tien_tt != this.t_tien_edited) {
            this.$toast.error('Tiền hóa đơn điện tử lệch với tiền hợp đồng. Bạn hãy kiểm tra lại tiền thay thế !')
            return false
          }

          if (this.ktra_quyen == 0) {
            if (this.vnhanvien_id != (await this.$root.token.getNhanVienID()) || vnguoicn != (await this.$root.token.getUserName())) {
              this.$toast.error('Bạn không được thay thế hóa đơn do người khác tạo !')
              return false
            }
          }
        }
        if (this._dieuchinh == 1) {
          this.$toast.error('Hợp đồng đã có hóa đơn bị điều chỉnh. Bạn không thể Thay thế !')
          return false
        }
        //Kiểm tra xem khách hàng đã xem hóa đơn hay chưa
        let kq = await this.listInvByCus_VTT(this.objHoaDonDienTu.CusCode, '', '')
        if (kq.substring(0, 3) == 'ERR') {
          this.$toast.error(kq)
          return false
        } else {
          let objKetQua = this.ObjectToXML(kq)
          let ar_kq = []

          for (var i = 0; i < objKetQua.getElementsByTagName('Item').length; i++) {
            if ((objKetQua.getElementsByTagName('invToken')[i].textContent = this.invToken)) {
              ar_kq.push({
                invToken: this.invToken,
                fkey: objKetQua.getElementsByTagName('fkey')[i].textContent,
                status: objKetQua.getElementsByTagName('status')[i].textContent
              })
              break
            }
          }

          this.Fkey = ar_kq[0].fkey

          if (ar_kq[0].status != 1) {
            this.$toast.error('Hóa đơn đã bị thay thế/sửa đổi/hủy. Không thể thay thế !')
            return false
          }

          if (this.objHoaDonDienTu.CusCode == null) {
            this.$toast.error('Mã khách hàng không để để trống')
            return false
          }
          if (this.objHoaDonDienTu.CusName == null) {
            this.$toast.error('Tên khách hàng không được để trống !')
            return false
          }
          if (this.objHoaDonDienTu.CusAddress == null) {
            this.$toast.error('Địa chỉ khách hàng không được để trống !')
            return false
          }

          if (this.Tag != 2) {
            if (this.ktra_quyen == 0) {
              if (this.vnhanvien_id != this.$root.token.getNhanVienID()) {
                this.$toast.error('Bạn không được thay thế hóa đơn do người khác tạo !')
                return false
              }
            }
          }
        }
      } else if (_thaotac == 4) {
        // Hủy hóa đơn
        if (this.Tag == 1 && this.khoa == 2) {
          this.$toast.error('Phiếu thanh toán đã bị Khóa. Bạn không thể thao tác !')
          return false
        }
        if (this.Tag != 2 && this.Tag != 3) {
          if (this.ktra_quyen == 0) {
            if (this.vnhanvien_id != (await this.$root.token.getNhanVienID()) || vnguoicn != (await this.$root.token.getUserName())) {
              this.$toast.error('Bạn không được hủy hóa đơn do người khác tạo !')
              return false
            }
          }
          if (this._dieuchinh == 1) {
            this.$toast.error('Hợp đồng đã có hóa đơn bị điều chỉnh. Bạn không thể hủy !')
            return false
          }
          if (this._thaythe == 1) {
            this.$toast.error('Hợp đồng đã có hóa đơn bị thay thế. Bạn không thể hủy !')
            return false
          }
          if (this.Tag == 1 && this.vkhongcho_gdv_huy_hddt_khac_thanh == 1) {
            //không cần check, db làm
          }

          //Kiểm tra xem khách hàng đã xem hóa đơn hay chưa
          let kq = await this.listInvByCus_VTT(this.objHoaDonDienTu.CusCode, '', '')
          if (kq.substring(0, 3) == 'ERR') {
            this.$toast.error(kq)
            return false
          } else {
            let objKetQua = this.ObjectToXML(kq)
            let ar_kq = []

            for (var i = 0; i < objKetQua.getElementsByTagName('Item').length; i++) {
              if ((objKetQua.getElementsByTagName('invToken')[i].textContent = this.invToken)) {
                ar_kq.push({
                  invToken: this.invToken,
                  fkey: objKetQua.getElementsByTagName('fkey')[i].textContent,
                  status: objKetQua.getElementsByTagName('status')[i].textContent,
                  payment: objKetQua.getElementsByTagName('payment')[i].textContent
                })
                break
              }
            }

            this.Fkey = ar_kq[0].fkey

            if (ar_kq[0].payment == 1) {
              this.$toast.error('Hóa đơn đã được thanh toán. Không thể hủy !')
              return false
            }

            if (ar_kq[0].status != 1) {
              this.$toast.error('Hóa đơn đã được Điều chỉnh/Thay thế/Hủy. Bạn không thể hủy !')
              return false
            }
          }
        }
      } else if (_thaotac == 9 || _thaotac == 10) {
        // Chuyển đổi hóa đơn
        if (this.Tag == 1 && this.khoa == 2) {
          this.$toast.error('Phiếu thanh toán đã bị Khóa. Bạn không thể thao tác !')
          return false
        }
        //Kiểm tra xem khách hàng đã xem hóa đơn hay chưa
        let kq = await this.listInvByCus_VTT(this.objHoaDonDienTu.CusCode, '', '')
        if (kq.substring(0, 3) == 'ERR') {
          this.$toast.error(kq)
          return false
        } else {
          let objKetQua = this.ObjectToXML(kq)
          let ar_kq = []

          for (var i = 0; i < objKetQua.getElementsByTagName('Item').length; i++) {
            if ((objKetQua.getElementsByTagName('invToken')[i].textContent = this.invToken)) {
              ar_kq.push({
                invToken: this.invToken,
                fkey: objKetQua.getElementsByTagName('fkey')[i].textContent,
                status: objKetQua.getElementsByTagName('status')[i].textContent
              })
              break
            }
          }
          this.Fkey = ar_kq[0].fkey
          if (ar_kq[0].status != 1) {
            this.$toast.error('Hóa đơn đã được Điều chỉnh/Thay thế/Hủy. Bạn không thể hủy !')
            return false
          }
        }
      } else if (_thaotac == 7) {
        // thanh toán hóa đơn
        if (this.Tag == 1 && this.khoa == 2) {
          this.$toast.error('Phiếu thanh toán đã bị Khóa. Bạn không thể thao tác !')
          return false
        }
        //Kiểm tra xem khách hàng đã xem hóa đơn hay chưa
        let kq = await this.listInvByCus_VTT(this.objHoaDonDienTu.CusCode, '', '')
        if (kq.substring(0, 3) == 'ERR') {
          this.$toast.error(kq)
          return false
        } else {
          let objKetQua = this.ObjectToXML(kq)
          let ar_kq = []

          for (var i = 0; i < objKetQua.getElementsByTagName('Item').length; i++) {
            if ((objKetQua.getElementsByTagName('invToken')[i].textContent = this.invToken)) {
              ar_kq.push({
                invToken: this.invToken,
                fkey: objKetQua.getElementsByTagName('fkey')[i].textContent,
                status: objKetQua.getElementsByTagName('status')[i].textContent,
                payment: objKetQua.getElementsByTagName('payment')[i].textContent
              })
              break
            }
          }
          this.Fkey = ar_kq[0].fkey
          if (ar_kq[0].payment == 1) {
            this.$toast.error('Hóa đơn đã được thanh toán. Bạn không thể thực hiện thao tác này !')
            return false
          }
          if (ar_kq[0].status != 1) {
            this.$toast.error('Hóa đơn đã được điều chỉnh hoặc bị hủy !')
            return false
          }
        }
      } else if (_thaotac == 8) {
        // hủy thanh toán hóa đơn
        if (this.Tag == 1 && this.khoa == 2) {
          this.$toast.error('Phiếu thanh toán đã bị Khóa. Bạn không thể thao tác !')
          return false
        }
        //Kiểm tra xem khách hàng đã xem hóa đơn hay chưa
        let kq = await this.listInvByCus_VTT(this.objHoaDonDienTu.CusCode, '', '')
        if (kq.substring(0, 3) == 'ERR') {
          this.$toast.error(kq)
          return false
        } else {
          let objKetQua = this.ObjectToXML(kq)
          let ar_kq = []

          for (var i = 0; i < objKetQua.getElementsByTagName('Item').length; i++) {
            if ((objKetQua.getElementsByTagName('invToken')[i].textContent = this.invToken)) {
              ar_kq.push({
                invToken: this.invToken,
                fkey: objKetQua.getElementsByTagName('fkey')[i].textContent,
                status: objKetQua.getElementsByTagName('status')[i].textContent,
                payment: objKetQua.getElementsByTagName('payment')[i].textContent
              })
              break
            }
          }
          this.Fkey = ar_kq[0].fkey
          if (ar_kq[0].payment == 0) {
            this.$toast.error('Hóa đơn chưa được thanh toán. Bạn không thể thực hiện thao tác này !')
            return false
          }
          if (ar_kq[0].status != 1) {
            this.$toast.error('Hóa đơn đã được điều chỉnh hoặc bị hủy !')
            return false
          }
        }
      } else if (_thaotac == 5) {
        // hủy thanh toán + hủy hóa đơn
        if (this.Tag == 1 && this.khoa == 2) {
          this.$toast.error('Phiếu thanh toán đã bị Khóa. Bạn không thể thao tác !')
          return false
        }

        if (this.Tag != 2) {
          if (this.ktra_quyen == 0) {
            if (this.vnhanvien_id != this.$root.token.getNhanVienID() || vnguoicn != (await this.$root.token.getUserName())) {
              this.$toast.error('Bạn không được hủy hóa đơn do người khác tạo !')
              return false
            }
          }
          if (this._dieuchinh == 1) {
            this.$toast.error('Hợp đồng đã có hóa đơn bị điều chỉnh. Bạn không thể hủy !')
            return false
          }
          if (this._thaythe == 1) {
            this.$toast.error('Hợp đồng đã có hóa đơn bị thay thế. Bạn không thể hủy !')
            return false
          }
          if (this.t_tien_thoaitra != this.t_tien_edited) {
            // this.$toast.error("Tiền hóa đơn điện tử lệch với tiền hợp đồng. Bạn hãy kiểm tra lại tiền hủy !");
            this.$toast.error('Tồn tại hợp đồng chưa thoái trả tạm. Ban không thể hủy !')
            return false
          }

          //Kiểm tra xem khách hàng đã xem hóa đơn hay chưa
          let kq = await this.listInvByCus_VTT(this.objHoaDonDienTu.CusCode, '', '')
          if (kq.substring(0, 3) == 'ERR') {
            this.$toast.error(kq)
            return false
          } else {
            let objKetQua = this.ObjectToXML(kq)
            let ar_kq = []

            for (var i = 0; i < objKetQua.getElementsByTagName('Item').length; i++) {
              if ((objKetQua.getElementsByTagName('invToken')[i].textContent = this.invToken)) {
                ar_kq.push({
                  invToken: this.invToken,
                  fkey: objKetQua.getElementsByTagName('fkey')[i].textContent,
                  status: objKetQua.getElementsByTagName('status')[i].textContent,
                  payment: objKetQua.getElementsByTagName('payment')[i].textContent
                })
                break
              }
            }
            this.Fkey = ar_kq[0].fkey
            if (ar_kq[0].payment == 0) {
              this.$toast.error('Hóa đơn chưa được thanh toán. Bạn không thể thực hiện thao tác này !')
              return false
            }
            if (ar_kq[0].status != 1) {
              this.$toast.error('Hóa đơn đã được điều chỉnh, thay thế hoặc bị hủy. Không thể thực hiện thao tác này !')
              return false
            }
          }
        }
      }

      return true
    },
    clickButton(key) {
      if (key == 'nhapmoi') {
        this.$nextTick(() => {
          this.$refs['tenKHRef'].focus()
        })
        // this.Clean()
      }

      if (key == 'capnhat') {
        let confirmText = 'Bạn có muốn chắc chắn cập nhật không ?'
        if (this.ddlThaoTac == 5) {
          confirmText = 'Bạn có chắc chắn muốn hủy hóa đơn không?'
        }
        this.$confirm(confirmText, {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'warning'
        }).then(async () => {
          this.CapNhat()
        })
      }

      if (key == 'khoaphieu') {
        this.$confirm(`Bạn thật sự muốn khóa phiếu  ?`, {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'warning'
        }).then(async () => {
          this.KhoaPhieu()
        })
      }
    },

    async KhoaPhieu() {
      //console.log(this.vphieutt_id)
      let obj = { phieutt_id: this.vphieutt_id }
      let data = {
        ds_para: JSON.stringify(obj),
        phanvung_id: 0
      }

      let response = await api.KhoaPhieu(this.axios, data)
      if (response.data.error_code == 'BSS-00000000') {
        this.$toast.success('Khóa phiếu thành công !')
      } else {
        this.$toast.error('Có lỗi xẩy ra !')
      }
    }
  }
}
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
.e-grid .e-rowcell.customcss {
  color: red !important;
}
.list-actions-top ul li {
  cursor: pointer;
}
</style>
