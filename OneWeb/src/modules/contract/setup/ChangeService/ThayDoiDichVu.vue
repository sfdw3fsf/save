<template>
  <div class="">
    <!-- action header -->
    <div class="list-action-top">
      <vue-bread-crumb :header="header" />
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a
              href="#"
              :class="{ disabled: disabledBtnNhapMoi }"
              @click.prevent="nhapMoi"
            >
              <span class="icon one-file-plus"></span> Nhập mới
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disabledBtnGhiLai }"
              @click.prevent="tsbtnGhiLai_Click"
            >
              <span class="icon one-save"></span>
              Ghi lại
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disabledBtnHuy }"
              @click.prevent="huy"
            >
              <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Hủy
            </a>
          </li>
          <li>
            <a href="#"
              :class="{ disabled: disabledBtnXoaHD }"
              @click.prevent="tsbtnXoa_Click">
              <span class="icon one-trash"></span> Xóa HĐ
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disabledBtnThanhToan }"
              @click.prevent="tsbtnThanhToan_Click"
            >
              <span class="icon nc-icon-glyph shopping_credit-card"></span>
              Thanh toán
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disabledBtnInPL }"
              @click.prevent="btnHoSo_Click"
            >
              <span class="icon one-print"></span> In PL
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disabledBtnThemTB }"
              @click.prevent="tsbtnThemTB_Click"
            >
              <span class="icon one-mobile-plus"></span>Thêm TB
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disabledBtnXoaTB }"
              @click.prevent="tsbtnXoaTB_Click"
            >
              <span class="icon one-phone-remove"></span>Xoá TB
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disabledBtnHenLD }"
              @click.prevent="tsbtnHen_Click"
            >
              <span class="icon one-calendar-check"></span>Hẹn LĐ
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disabledBtnLienHe }"
              @click.prevent="btn_Lien_He"
            >
              <span class="icon nc-icon-glyph business_contacts"></span>Liên hệ
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disabledBtnEmail }"
              @click.prevent="btn_Email"
            >
              <span class="icon one-email"></span>Email
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disabledBtnEmail }"
              @click.prevent="btnCapNhatDVClick"
            >
              <span class="icon one-download"></span>Cập nhật ĐV
            </a>
          </li>
        </ul>
      </div>
    </div>
    <!-- content page -->
    <div class="page-content">
      <!-- content 1 -->
      <div class="box-form">
        <div class="row">
          <div class="col-sm-4 col-12"></div>
          <div class="col-sm-2 col-12">
            <div class="info-row marb0">
              <div class="key nowrap w60 text-bold">
                Mã dự án
              </div>
              <div class="value">
                <input
                  type="text"
                  class="form-control text-bold"
                  v-model="maDuAn"
                />
              </div>
            </div>
          </div>
          <div class="col-sm-2 col-12">
            <div class="info-row marb0">
              <div class="key nowrap w60">
                <div class="check-action">
                  <input
                    type="checkbox"
                    class="check"
                    v-model="checkBoxCTV"
                    @change="chkCTVChange"
                  />
                  <span class="name text-bold">Cộng tác viên</span>
                </div>
              </div>
              <div class="value">
                <input
                  type="text"
                  class="form-control text-bold"
                  v-model="inputCTV"
                  :disabled="true"
                />
              </div>
            </div>
          </div>
          <div class="col-sm-2 col-12">
            <div class="info-row marb0">
              <div class="key nowrap w60">
                <div class="check-action">
                  <input
                    type="checkbox"
                    class="check"
                    v-model="checkBoxNguoiGT"
                    @change="chkNguoiGTChange"
                  />
                  <span class="name text-bold">Người giới thiệu</span>
                </div>
              </div>
              <div class="value">
                <input
                  type="text"
                  class="form-control text-bold"
                  v-model="inputNguoiGT"
                  :disabled="true"
                />
              </div>
            </div>
          </div>
          <div class="col-sm-2 col-12">
            <div class="info-row marb0">
              <div class="key nowrap w60 text-bold">
                Tổng tiền
              </div>
              <div class="value">
                <input
                  type="text"
                  class="form-control text-bold"
                  v-model="tongTien"
                  :disabled="true"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-if="link_url" class="box-form">
        <div class="legend-title">
           <div class="key w140">D/S TB của KH: <span class="lable">{{soMayAcc}},{{matb_kt}}</span></div>
        </div>
      </div>
      <!-- content 2 -->
      <div class="box-form">
        <!-- Thông tin phiếu yêu cầu -->
        <div class="legend-title">
          Thông tin phiếu yêu cầu
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="row">
              <div class="col-sm-7 col-12">
                <div class="info-row">
                  <div class="key w100">Mã giao dịch</div>
                  <div class="value">
                    <div class="input-more-button">
                      <button class="btn" @click="showPopupSearchContract">
                        <span class="-ap icon-more_horiz"></span>
                      </button>
                      <input
                        ref="maGiaoDich"
                        id="maGiaoDich"
                        type="text"
                        class="form-control highlight text-bold"
                        v-model="maGiaoDich"
                        v-on:keyup.enter="enterClickedMaGiaoDich"
                      />
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w100">Mã hợp đồng</div>
                  <div class="value">
                    <input
                      ref="maHopDong"
                      type="text"
                      class="form-control text-bold"
                      v-model="maHopDong"
                    />
                  </div>
                </div>
              </div>
              <div class="col-sm-5 col-12">
                <div class="info-row">
                  <div class="key w100">Ngày yêu cầu</div>
                  <div class="value">
                    <div class="input-icon-right">
                      <input
                        disabled
                        type="text"
                        class="form-control"
                        :formatter="momentFormat"
                        v-model="ngayYeuCau"
                      />
                      <span class="icon one-calendar"></span>
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w100">Ngày lập HĐ</div>
                  <div class="value">
                    <div class="input-icon-right">
                      <input
                        disabled
                        type="text"
                        class="form-control"
                        v-model="ngayLapHD"
                        :formatter="momentFormat"
                      />
                      <span class="icon one-calendar"></span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="row">
              <div class="col-sm-7 col-12">
                <div class="info-row">
                  <div class="key w90">Dịch vụ</div>
                  <div class="value">
                    <div class="select-custom">
                      <!-- <vue-select
                        v-model="selectedDichVu"
                        :labelWidth="'110'"
                        :options="dichVuList"
                        labelField="TEN_DVVT"
                        valueField="DICHVUVT_ID"
                        label="Dịch vụ"
                        @input="cboDichVuVT_SelectedValueChanged"
                      ></vue-select> -->
                      <SelectExt
                        placeholder="Dịch vụ"
                        v-model="selectedDichVu"
                        :allowFiltering="true"
                        dataTextField="TEN_DVVT"
                        dataValueField="DICHVUVT_ID"
                        :dataSource="dichVuList1"
                        @change="cboDichVuVT_SelectedValueChanged">
                    </SelectExt>
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w90">Kiểu yêu cầu</div>
                  <div class="value">
                    <div class="select-custom">
                      <!-- <vue-select
                        v-model="selectedKieuYeuCau"
                        :labelWidth="'110'"
                        :options="dataKieuYeuCauList"
                        labelField="ten_kieuld"
                        valueField="kieuld_id"
                        label="Kiểu yêu cầu"
                        @input="changeKieuYeuCau"
                      ></vue-select> -->
                        <SelectExt
                          placeholder="Kiểu yêu cầu"
                          v-model="selectedKieuYeuCau"
                          :allowFiltering="true"
                          dataTextField="ten_kieuld"
                          dataValueField="kieuld_id"
                          :dataSource="dataKieuYeuCauList"
                          @change="changeKieuYeuCau">
                      </SelectExt>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-5 col-12">
                <div class="info-row">
                  <div class="key w140">Loại hình</div>
                  <div class="value">
                    <div class="select-custom">
                      <!-- <vue-select
                        :disable="true"
                        v-model="selectedLoaiHinh"
                        :labelWidth="'140'"
                        :options="loaiHinhListByFilter"
                        labelField="LOAIHINH_TB"
                        valueField="LOAITB_ID"
                        label="Loại hình"
                        @input="changeLoaiHinh"
                      ></vue-select> -->
                        <SelectExt
                            placeholder="Loại hình"
                            v-model="selectedLoaiHinh"
                            :allowFiltering="true"
                            dataTextField="LOAIHINH_TB"
                            dataValueField="LOAITB_ID"
                            :dataSource="loaiHinhListByFilter"
                            @change="changeLoaiHinh">
                        </SelectExt>
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w140">Mã HĐ - TTKD</div>
                  <div class="value">
                    <input
                      type="text"
                      class="form-control"
                      v-model="maHDTTKD"
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <!-- Thông tin thuê bao -->
          <div class="col-sm-6 col-12">
            <div class="legend-title mart20">Thông tin thuê bao</div>
            <div class="row">
              <div class="col-sm-7 col-12">
                <div class="info-row">
                  <div class="key w100">Số máy/Acc</div>
                  <div class="value">
                    <div class="input-more-button">
                      <button class="btn" @click="btnSearchAccountClick">
                        <span class="-ap icon-more_horiz"></span>
                      </button>
                      <input
                        type="text"
                        class="form-control highlight text-bold"
                        v-model="soMayAcc"
                        v-on:keyup.enter="txtMaTB_KeyPress"
                        ref="txtMaTB"
                        id="soMayAcc"
                      />
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w100">Hướng kết nối</div>
                  <div class="value">
                    <input
                      type="text"
                      class="form-control"
                      v-model="huongKetNoi"
                    />
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w100">Tên thuê bao</div>
                  <div class="value">
                    <input
                      ref="tenThueBao"
                      type="text"
                      class="form-control text-bold"
                      v-model="tenThueBao"
                    />
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w100">Địa chỉ TB</div>
                  <div class="value">
                    <input
                      type="text"
                      class="form-control text-bold"
                      v-model="diaChiTB"
                    />
                  </div>
                </div>
              </div>
              <div class="col-sm-5 col-12">
                <div class="info-row">
                  <div class="key w100">Số ảo</div>
                  <div class="value">
                    <input
                      type="text"
                      class="form-control highlight text-bold"
                      v-model="soAo"
                    />
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w100">Trạng thái TB</div>
                  <div class="value">
                    <input
                      type="text"
                      class="form-control italicized-red"
                      v-model="trangThaiTB"
                    />
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w100">Mã ERP</div>
                  <div class="value">
                    <input
                      type="text"
                      class="form-control text-bold"
                      v-model="maERP"
                    />
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w100">Mã BSS</div>
                  <div class="value">
                    <input
                      type="text"
                      class="form-control text-bold"
                      v-model="maBSS"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Địa chỉ LĐ</div>
              <div class="value">
                <input
                  type="text"
                  class="form-control text-bold"
                  v-model="diaChiLD"
                />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Địa chỉ LĐ đầu</div>
              <div class="value">
                <input
                  type="text"
                  class="form-control text-bold"
                  v-model="diaChiLDDau"
                />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Địa chỉ LĐ cuối</div>
              <div class="value">
                <input
                  type="text"
                  class="form-control text-bold"
                  v-model="diaChiLDCuoi"
                />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Ghi chú</div>
              <div class="value">
                <input type="text" class="form-control" v-model="ghiChu" />
              </div>
            </div>
          </div>
          <!-- Thông tin khách hàng -->
          <div class="col-sm-6 col-12">
            <div class="legend-title mart20">Thông tin khách hàng</div>
            <div class="row">
              <div class="col-sm-7 col-12">
                <div class="info-row">
                  <div class="key">Mã khách hàng</div>
                  <div class="value">
                    <input
                      ref="maKH"
                      type="text"
                      class="form-control text-bold"
                      v-model="maKH"
                    />
                  </div>
                </div>
              </div>
              <div class="col-sm-5 col-12">
                <div class="info-row">
                  <div class="key w140">
                    <div class="check-action">
                      <input
                        :disabled="!chkNgay_HHTD.Enabled"
                        type="checkbox"
                        class="check"
                        v-model="checkBoxNgayHetHan"
                      />
                      <span class="name">Ngày hết hạn TD</span>
                    </div>
                  </div>
                  <div class="value">
                    <div class="input-icon-right">
                      <input
                        :disabled="!checkBoxNgayHetHan"
                        type="text"
                        class="form-control"
                        v-model="ngayHetHan"
                        :formatter="momentFormat"
                      />
                      <span class="icon one-calendar"></span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key">Tên khách hàng</div>
              <div class="value">
                <input
                  ref = "tenKH"
                  type="text"
                  class="form-control text-bold"
                  v-model="tenKH"
                />
              </div>
            </div>
            <div class="info-row">
              <div class="key">Địa chỉ KH</div>
              <div class="value">
                <input
                  type="text"
                  class="form-control text-bold"
                  v-model="diaChiKH"
                />
              </div>
            </div>
            <div class="row">
              <div class="col-sm-7 col-12">                
              </div>
              <div class="col-sm-5 col-12">
                <div class="info-row">
                  <div class="key w140">
                                          
                      <span class="name">{{titleNgayTD}}</span>
                    
                  </div>
                  <div class="value">
                    <div class="input-icon-right">
                      <input                        
                        type="text"
                        class="form-control"
                        v-model="ngayTD"
                        :formatter="momentFormat"
                      />
                      <span class="icon one-calendar"></span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="nav tabs tab-over mart20">
              <!-- <a href="#" class="active">Lý do huỷ</a>
              <a href="#">Dịch vụ khác</a> -->
            </div>
            <b-tabs content-class="mt-3">
              <b-tab title="Lý do hủy">
                <div class="tab-content">
                  <div class="input-more-button">
                    <button class="btn" :disabled="!btnEditLyDoHuy.Enabled" @click="btnLyDoHuyClick">
                      <span class="-ap icon-more_horiz"></span>
                    </button>
                    <ejs-listbox
                      :cssClass="'list-lydohuy'"
                      :dataSource="
                        dsDKHUY.map(r => ({ id: r.stt, text: r.lydohuy }))
                      "
                    ></ejs-listbox>
                  </div>
                </div>
              </b-tab>
              <b-tab title="Dịch vụ khác"
                ><div class="tab-content">
                  <div class="input-more-button">
                    <button class="btn" @click="btnDichVuKhacClick">
                      <span class="-ap icon-more_horiz"></span>
                    </button>
                    <ejs-listbox
                      :cssClass="'list-dich-vu-khac'"
                      :dataSource="
                        dsDVKhac.map(r => ({ id: r.stt, text: r.noidung }))
                      "
                    ></ejs-listbox>
                  </div>
                </div>
              </b-tab>
            </b-tabs>
            <div class="legend-title mart20">Chi tiết tiền khoản mục</div>
            <div class="row">
              <div class="col-sm-7 col-12">
                <div class="info-row">
                  <div class="key">Tổng tiền</div>
                  <div class="value">
                    <div class="input-more-button">
                      <button class="btn" @click="btnTienKM_Click">
                        <span class="-ap icon-more_horiz"></span>
                      </button>
                      <input
                        type="text"
                        class="form-control tright red text-bold"
                        value="0"
                        v-model="tongTienKhoanMuc"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-5 col-12">
                <div class="info-row">
                  <div class="key w140">
                    Tổng VAT
                  </div>
                  <div class="value">
                    <input
                      type="text"
                      class="form-control tright red text-bold"
                      value="0"
                      v-model="tongVat"
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="row">
          <!-- DS thuê bao -->
          <div class="col-sm-2 col-12">
            <danh-sach-thue-bao :dsThueBao="dsThueBao" @recordClick="dsThueBao_click"/>
          </div>
          <!-- Dánh sách dịch vụ gia tăng -->
          <div class="col-sm-10 col-12">
            <danh-sach-dich-vu-tang ref="danhsachdichvugiatang"
              @recordClick="recordClickDichVuTang"
              @dataEditCompleted="dataEditCompleted"
              @UpdateGridDVGT="UpdateGridDVGT"
              @valueThuHoi="valueThuHoi"
            />
            <div class="cuoc">
              <div class="cuoc-left">
                <span>Tổng cước SD: {{lblTong}}</span>
              </div>
              <div class="cuoc-right">
                <div class="value item-cuoc-one">
                  <span>Cước SD: {{txt_SumTien}}</span>
                  <!-- <input
                    type="text"
                    class="form-control tright red text-bold"
                    value="0"
                    v-model="txt_SumTien"
                  />                   -->
                </div>
                <div class="value item-cuoc-two">
                  <span>VAT SD: {{txt_SumVat}}</span>
                  <!-- <input
                    type="text"
                    class="form-control tright red text-bold"
                    value="0"
                    v-model="txt_SumVat"
                  /> -->
                </div>
              </div>
              <div class="cuoc-right">
                <div class="value item-cuoc-one">
                  <span>Giá block: {{txtSumGiaBlock}}</span>
                  <!-- <input
                    type="text"
                    class="form-control tright red text-bold"
                    value="0"
                    v-model="txtSumGiaBlock"
                  /> -->
                </div>
                <div class="value item-cuoc-two">
                  <span>VAT block: {{txtSumVatBlock}}</span>
                  <!-- <input
                    type="text"
                    class="form-control tright red text-bold"
                    value="0"
                    v-model="txtSumVatBlock"
                  /> -->
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- dialog số máy acc -->
    <dialog-search-account
      ref="dlgSearchAccount"
      :isDialog="true"
      :vma_tb="soMayAcc"
      @onSelectCustomer="onSelectCustomer"
    />
    <popupTraCuuThueBao ref="tracuuthuebao" id="tra-cuu-thue-bao" @chapnhan="onSelectCustomer" />
    <!-- dialog mã giao dịch -->
    <search-contract-modal
      ref="popupSearchContract"
      @recordClick="recordClick"
      @accept="accept"
      @dataSelectMaGiaoDich="dataSelectMaGiaoDich"
      :trangthai_hd="1"
      :loai_hd_id="7"
      :ngay_yc="ngayYeuCauPopup"
    />
    <!-- dialog lý do hủy -->
    <dialog-ly-do-huy ref="dlgHuy" @onAccept="onDialogLyDoHuyAccepted" />
    <!-- dialog dịch vụ khác -->
    <DialogDichVuKhac
      ref="dlgDichVuKhac"
      @onAccept="onDialogDichVuKhacAccepted"
    />
    <!-- dialog tổng tiền -->
    <DialogTienKhoanMuc ref="dlgTienKhoanMuc" @onAccept="dlgTienKM_Close" />
    <!-- dialog người giới thiệu -->
    <DialogNguoiGioiThieu
      ref="dlgNguoiGioiThieu"
      @onAccept="dlgNguoiGioiThieu_Accept"
    />
    <!-- dialog cộng tác viên -->
    <DialogCongTacVien ref="dlgCongTacVien" @onAccept="dlgCongTacVien_Accept" />
    <!-- dialog hẹn ld -->
    <!-- <DialogThongTinHenKhachHang
      :statusPopupHenKH="statusPopupHenKH"
      @updateStatusPopup="updateStatusPopup"
      ref="henKH"
    /> -->
    <ThongTinHenKhachHang  ref="popupThongTinHenKhachHang" :hdkh_id="parseInt(hdkh_id)" />
    <!-- dialog liên hệ -->
    <!-- <DialogThongTinLienHe ref="dlgLienHe" /> -->
    <!-- dialog Email -->
    <!-- <DialogThongTinEmail ref="dlgEmail" /> -->
    <!-- In PL -->
    <DialogInHoSo ref="popupInHoSo" :params="popupInHopDong_Param" />
    <PopupCapNhatDV ref='popupCapNhatDV_tddv' />
    <b-modal
      id="modal-somay"
      title="Bạn chưa nhập mã thuê bao"
      ok-title="OK"
      ok-only
    >
    </b-modal>
    <ServiceOther ref="ServiceOther" idModal="ServiceOther" :dulieu="null" ></ServiceOther>
    <!-- <ejs-dialog :enableResize='true' :allowDragging='true' :visible="false" ref="thongtinemail" :header='"Thông tin Email"'
                showCloseIcon=true target=".main-wrapper" width='1000' height="800">
        <ThongTinEmail ref="popthongtinemail" :hdkh_id="parseInt(hdkh_id)" :khachhang_id="khachhang_id" />
    </ejs-dialog>
    <ejs-dialog :enableResize='true' :allowDragging='true' :visible="false" ref="thongtinlienhe" :header='"Thông tin liên hệ"'
                showCloseIcon=true target=".main-wrapper" width='1000' height="800">
      <ThongTinLienHe  ref='thongTinLienHePopUp'  :hdkh_id="parseInt(hdkh_id)" :khachhang_id="khachhang_id"/>
    </ejs-dialog> -->
    <b-modal ref="ThongTinEmail" id="ThongTinEmail" size="xl" title="Thông tin Email" hide-footer
        body-class="p-0 thanhtoan-modal">
        <ThongTinEmail :viewOnly="true" :hdkh_id="parseInt(hdkh_id)" :khachhang_id="khachhang_id" />
    </b-modal>
    <b-modal ref="ThongTinLienHe" id="ThongTinLienHe" size="xl" title="Thông tin liên hệ" hide-footer
        body-class="p-0 thanhtoan-modal">
        <ThongTinLienHe :hdkh_id="parseInt(hdkh_id)" :khachhang_id="khachhang_id" />
    </b-modal>
    <keep-alive>
        <component :is="frmTuVanMyTVCom" ref="dialogfrmTuVanMyTV" v-on="frmTuVanMyTVEvents"/>
    </keep-alive>
    <b-modal id="popupComponents" ref="popupComponents" size="xl"
      hide-footer
      hide-header
      hide-header-close
      body-class="modal-body p-0">
        <component :is="popupComponent" ref="generalPopup" :dulieu="popupComponentData" v-bind="popupComponentAttr" isPopup="true" v-on="popupComponentEvts"></component> <!--v-dynamic-events="popupComponentEvents"-->
    </b-modal>
  </div>
</template>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import moment from "moment";
// import SearchContractModal from "./popup/SearchContract/SearchContractModal.vue";
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue";
import DialogSearchAccount from "./popup/SoMayAcc/DialogSearchAccount.vue";
import DialogLyDoHuy from "./popup/LyDoHuy/DialogLyDoHuy.vue";
import DialogDichVuKhac from "./popup/DichVuKhac/DialogLyDichVuKhac.vue";
import DialogTienKhoanMuc from "./popup/popupTienKM.vue";
import DialogNguoiGioiThieu from "./popup/NguoiGioiThieu/dialogNguoiGioiThieu.vue";
import DialogCongTacVien from "./popup/CongTacVien/DialogCongTacVien.vue";
//import DialogThongTinHenKhachHang from "./ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue";
import ThongTinHenKhachHang from "@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue"
//import DialogThongTinLienHe from "./popup/LienHe/DialogLienHe.vue";
//import DialogThongTinEmail from "./popup/Email/DialogEmail.vue";
import ThongTinLienHe from "@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue"
import ThongTinEmail from "@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue"
import DanhSachThueBao from "./part/DanhSachThueBao.vue";
import api from './api/index.js'
const frmTuVanMyTV = () => import("@/modules/CSKH/TuvanMyTv/TuvanMyTv.vue");
import {
  LOAI_HOPDONG,
  TRANGTHAI_HOPDONG,
  LOAI_DONVI,
  DUNG_THU,
  DICHVU_VIENTHONG,
  KHOANMUC_TT,
  LOAIHINH_THUEBAO,
  TRANGTHAI_TB,
  TRANGTHAI_DONGBO,
  KieuCaiDatDVGT,
  KIEU_LAPDAT,
  LOAIHIN_THUEBAO
} from "../../../../constants";
import { DichVuGiaTang } from "./constants";
import { TrangThaiTB } from "../../../../const/enums";
import { formatDate, formatCurrencyNoVND } from "@/utils/format";
import { filterDialogClose } from "@syncfusion/ej2-grids";
import DialogInHoSo from "../../../print/InHopDong/InHopDong.vue";
//import popupTraCuuThueBao from "../SeparationSubscriber/popups/popupTraCuuThueBao.vue";
import popupTraCuuThueBao from "./popup/popupTraCuuThueBao.vue";
import ServiceOther from './popup/otherservices/popupServiceOthers'
import PopupCapNhatDV from '../Liquidate/components/popupCapNhatDV.vue'
export default {
  name: "ThayDoiDichVu",
  components: {
    DanhSachDichVuTang: () => import("./part/DanhSachDichVuTang.vue"),
    SearchContractModal,
    DialogSearchAccount,
    DialogLyDoHuy,
    PopupCapNhatDV,
    DialogDichVuKhac,
    DialogTienKhoanMuc,
    DialogNguoiGioiThieu,
    DialogCongTacVien,
    ThongTinHenKhachHang,
    //DialogThongTinLienHe,
    ThongTinEmail,
    ThongTinLienHe,
    DanhSachThueBao,
    DialogInHoSo,
    popupTraCuuThueBao,
    ServiceOther,
    frmTuVanMyTV
  },
  data() {
    return {
      /* data header action */
      header: {
        title: "THAY ĐỔI DỊCH VỤ",
        list: [
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          {
            name: "Thay đổi dịch vụ",
            link: { name: "Ui.buttons" }
          }
        ]
      },
      // 3889378
      momentFormat: {
        stringify: date => {
          return date ? moment(date).format("DD/MM/YYYY") : "";
        },
        parse: value => {
          return value ? moment(value, "DD/MM/YYYY").toDate() : null;
        }
      },
      isShowDialogMaGiaoDich: false,
	  dichVuList1:[],
      // data form
      selectedDichVu: null,
      selectedLoaiHinh: null,
      selectedKieuYeuCau: null,
      checkBoxCTV: false,
      checkBoxNguoiGT: false,
      checkBoxNgayHetHan: false,
      inputCTV: "",
      inputNguoiGT: "",
      tongTien: 0,
      maGiaoDich: "",
      maHopDong: "",
      ngayYeuCau: "",
      ngayLapHD: "",
      maHDTTKD: "",
      soMayAcc: "",
      huongKetNoi: "",
      tenThueBao: "",
      diaChiTB: "",
      soAo: "",
      trangThaiTB: "",
      maERP: "",
      maBSS: "",
      diaChiLD: "",
      diaChiLDDau: "",
      diaChiLDCuoi: "",
      ghiChu: "",
      maKH: "",
      ngayHetHan: "",
      ngayTD: "",
      tenKH: "",
      diaChiKH: "",
      tongTienKhoanMuc: 0,
      tongVat: 0,
      link_url: false,
      // status disable
      disabledBtnNhapMoi: true,
      disabledBtnGhiLai: false,
      disabledBtnHuy: false,
      disabledBtnXoaHD: true,
      disabledBtnThanhToan: true,
      disabledBtnInPL: false,
      disabledBtnThemTB: true,
      disabledBtnXoaTB: true,
      disabledBtnHenLD: false,
      disabledBtnLienHe: false,
      disabledBtnEmail: false,
      tagForm: 0,
      loaiHinhListByFilter: [],
      kieuYeuCauListFilterByLoaiHdId: [],
      dataSearchMaGiaoDichById: null,
      dataSearchMaGiaoDichByRow: null,
      dataSearchSoMayAccById: null,
      dataSearchSoMayAccByRow: null,
      currentRecordThueBao: null,
      configDSThueBao: [
        {
          label: "Số máy/Acc",
          filterAble: true,
          key: "ma_tb"
        }
      ],
      isLoadingDsThueBao: false,
      current_time: "",
      hdtbId: 0,
      dsDKHUY: [],
      dsBD_LYDOHUY:[],
      dsDVKhac: [],
      ds_tien_khoanmuc: [],
      dataInforMaGiaoDDich: {},
      dataInforSoMayAcc: {},
      dataKieuYeuCauList: [],
      isActiveClickMaGiaoDich: false,
      isActiveClickSoMayAcc: false,
      dataDangKy: {},
      arrayItemDaDangKy: [],
      statusPopupHenKH: false,
      dataGhiLai: {},
      valueThuHoiThietBi: false,
      themMoi: 1,
      hdkh_id_themMoi: "",
      hdtb_id_themMoi: "",
      maGD_themMoi: "",
      dataCha: {},
      themTB: false,
      ngayYeuCauPopup: "",
      dsThueBao: [],
      popupInHopDong_Param: {
        hdkh_id: "",
        ma_gd: "",
        ngay_yc: "",
        ngay_lhd: "",
        hoadon: "",
        seri_id: "",
        loaihoadon_id: ""
      },
      isEnterSoMay: false,
      btnEditLyDoHuy: {
        Enabled : false
      },
      chkThuHoi : {
        Checked : false,
        Enabled : true
      },
      chkNgay_HHTD : {
        Enabled : true,
        Checked : false
      },
      dtpNgayHH_TD :{
        Enabled : true
      },
      TICHHOP_DIDONG_DHSX:false,
      listDvKhongDangKy:[], //todo: lấy danh sách này tại hàm hiển thị danh bạ
      danhSachDichVuSoLuong:[],//todo: load tại hàm created
      lblTong:0,
      txt_SumTien:0,
      txt_SumVat:0,
      txtSumGiaBlock:0,
      txtSumVatBlock:0,
      tien_dv:0,
      vat_dv:0,
      dtien_dv:0,
      dvat_dv:0,
      kt_kenhphu_ao:false,
      tocdotn_id : 0,
      muccuoctn_id : 0,
      luong_id:0,
      _sl_cha : 0,
      ItemList:[],
      linhvuc_id:0,
      loaisochinh:0,
      isdn_sochinh:false,
      matb_isdn_chinh:"",
      phuongld_id:0,//Hiếu bổ sung lấy phuongld_id 17/09/2010
      phold_id:0,
      apld_id:0,
      khuld_id:0,
      kt_tao_hopdong_cntt:false,
      ts_ngay_hethan_tamdung:0,
      tamthu_vt:false,
      IS_USING_SERVICE_SIPTRINK:false,
      doitac_id:0,
      trangbi_id:0,
      dtThongTinTb:[],
      dtSuDungDV:[],
      hdkh_id:0,
      khachhang_id:0,
      maDuAn:"",
      chkKHKT3:{
        Checked:false
      },
      ctv_id:0,
      matb_kt: "",
      nguoigt_id:0,
      thuebao_id:0,
      doituong_id:0,
      donviql_id:0,
      hdtb_id_cha:0,
      quytrinh_id:0,
      donvi_id:0,
      ds_isdn:[],
      loaitb_id:1,
      frmTuVanMyTVCom: null,
      frmTuVanMyTVEvents: {
        'onUpdate': this.onUpdate
      },
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close': this.popupClosed,
        acceptChangeCTV: this.popupClosed,
        acceptChangeNGT: this.popupClosed,
        PopUpCongTacVien_Thoat: this.popupClosed
      },
      popupComponentName: '',
      //add check show message 
      isShowMsg_SearhTB: true,
      titleNgayTD:'Ngày TD',      
      ob_id:-1,
      loai_ob:-1,      
    };
  },
  async mounted() {
    //https://onebss.vnpt.vn/#/ext-hopdong/Cap-Nhat-Thue-Bao-Dat-Coc?ma_Tb=<p0>&loaitb_id=<loaitb_id của thuê bao p0>&dichvuvt_id=<dichvuvt_id của thuê bao p0>&matb_kt={p1;p2;...pn}
    if (this.$route.query.loaitb_id && this.$route.query.loaitb_id !== '') {
            this.selectedLoaiHinh = this.$route.query.loaitb_id.replaceAll(' ', '+')
    }
    if (this.$route.query.dichvuvt_id && this.$route.query.dichvuvt_id !== '') {
            this.selectedDichVu = this.$route.query.dichvuvt_id.replaceAll(' ', '+')
    }
    if (this.$route.query.matb_kt && this.$route.query.matb_kt !== '') {
            this.matb_kt = this.$route.query.matb_kt.replaceAll(' ', '+')
    }
    if (this.$route.query.ma_tb && this.$route.query.ma_tb !== '') {
            this.link_url = true,
            this.soMayAcc = this.$route.query.ma_tb.replaceAll(' ', '+')
            this.txtMaTB_KeyPress()
    }
    if(this.$route.query.ob_id){
      this.isShowMsg_SearhTB = false;
      this.ob_id = this.$route.query.ob_id      
    }
    if(this.$route.query.loai_ob){      
      this.loai_ob = this.$route.query.loai_ob
    }
    
	const variables = {
        loaihd_id: 7
      };
      await this.getTongTienKhoanMucList(variables);
      this.ds_tien_khoanmuc = this.dsTongTienKhoanMuc;
  },
  async created() {
    let current_time = moment()
      .format("DD/MM/YYYY")
      .toString();
    this.current_time = current_time;
    this.ngayYeuCau = this.current_time;
    this.ngayLapHD = this.current_time;
    this.ngayHetHan = this.current_time;
    this.ngayTD = this.current_time;
    this.ngayYeuCauPopup = moment()
      .format("DD/MM/YYYY")
      .toString();
    await this.loadDataFirst();
    await this.fetchDanhSachThueBao();
    // this.$route.name === "changeService"
    //   ? (this.tagForm = 1)
    //   : (this.tagForm = "");
    if (this.$route.query.tag != null && this.$route.params.tag != '' && this.$route.query.tag.length > 0) {
      this.tagForm = this.$route.query.tag
    }
    if (this.$route.query && this.$route.query.hdkh_cha_id) {
      this.loadTuHopDongTuVan(this.$route.query.hdkh_cha_id);
    }
    //todo: load tham số mặc định
    const ds_tsmd = await api.lay_ds_thamso_md(0)
    if (ds_tsmd && ds_tsmd.length>0) {
      let cnt = ds_tsmd.filter(r => r["ma_ts"] == 'TICHHOP_DIDONG_DHSX' && r["ten_ts"]=="1").length
      if (cnt>0) this.TICHHOP_DIDONG_DHSX = true
      cnt = ds_tsmd.filter(r => r["ma_ts"] == 'KT_TAO_HOPDONG_CNTT' && r["ten_ts"]=="1").length
      if (cnt>0) this.kt_tao_hopdong_cntt = true
      cnt = ds_tsmd.filter(r => r["ma_ts"] == 'USING_SERVICE_SIPTRINK_NEW').length
      if (cnt>0) this.IS_USING_SERVICE_SIPTRINK = true
      const item = ds_tsmd.filter(r => r["ma_ts"] == 'TS_NGAY_HETHAN_TAMDUNG' && r["ten_ts"]!="")
      if (item && item.length>0) {
        this.ts_ngay_hethan_tamdung = item[0].ten_ts
      }
    }
    this.danhSachDichVuSoLuong = await api.sp_tt_dichvu_gt()
    //this.ds_tien_khoanmuc = await api.sp_ds_tienkhoanmuc_loaihd({ "loaihd_id": LOAI_HOPDONG.THAY_DOI_DV})
    if (this.$route.query.ma_tb != null && this.$route.params.ma_tb != '' && this.$route.query.ma_tb.length > 0) {
      this.soMayAcc = this.$route.query.ma_tb
      const ds_tb = await api.LAY_DANHBA_THEO_MATB({
        in_dichvuvt_id : 0,
        in_donvi_dl_id : 0,
        in_ma_tb : this.$route.query.ma_tb
      })
      if (ds_tb && ds_tb.length>0 && ds_tb[0].dichvuvt_id!=0){
        this.selectedDichVu = parseInt(ds_tb[0].dichvuvt_id)
        this.txtMaTB_KeyPress()
      }
    }
  },
  computed: {
    ...mapGetters("mapChangeService", [
      "dichVuList",
      "loaiHinhList",
      "kieuYeuCauList",
      "dsSoMayAcc",
      "dsMaGiaoDich",
      "dsTongTienKhoanMuc",
      "kieuYeuCauListLD_BC",
      "dsDichVuGiaTang"
    ])
  },
  methods: {
    ...mapActions("mapChangeService", [
      "fetchDichVuList",
      "fetchLoaiHinhList",
      "fetchYeuCauList",
      "fetchDanhSachThueBao",
      "Lay_ds_tb_dvgt_theo_tt_ims_v2",
      "LAY_DS_THUEBAO_DICHVU_IMS_V2",
      "LAY_DS_THUEBAO_DICHVU_LINHVUC_V2",
      "LAY_DS_THUEBAO_DICHVU_V2",
      "lay_ds_thuebao_dichvu_v2",
      "clickSoMaAcc",
      "clickMaGiaoDich",
      "getTongTienKhoanMucList",
      "deleteHD",
      "fetchKieuYeuCauListLD_BC",
      "xoaHopDongTB",
      "ghiLaiHopDongTB",
      "mapIDDuLieuHDKH",
      "LAY_DS_DB_MEGAWAN_THEO_TBID",
      "LAY_DS_DB_TSL_THEO_TBID",
      "LAY_DS_HDTB_DV",
      "LAY_DS_DB_ADSL_THEO_TBID",
      "LAY_DS_DB_IMS_THEO_TBID",
      "LAY_DS_DB_GP_THEO_TBID",
      "LAY_DS_DB_CNTT_THEO_TBID",
      "LAY_DS_DB_LYDOHUY_THEO_TBID",
      "MAP_ID",
      "MAP_ID_HUY",
      "Kiemtra_no_thanhly_yc",
      "Ktra_DK_SD_GreenOnline",
      "LayHuongGiao_TiepNhan",
      "themMoiTB",
      "LAY_DANHBA_THEO_MATB",
      "getKeys",
      "Lay_Ma_GD_V2",
      "LayHuongGiao_TiepNhan",
      "Kiemtra_tb_mytv_chinhphu",
      "LAY_DS_THAMSO_MD_MATS",
      "LAY_DS_HOPDONG_TB_THEO_HDKH_ID",
      "LAY_DS_DK_LYDOHUY",
      "lay_ds_ly_do",
      "setDsDichVuGiaTang_new"
    ]),
    async btnCapNhatDVClick()
    {
      let self = this;
      let dulieu = {
        hdtb_id: self.hdtb_id
      };
      this.$refs.popupCapNhatDV_tddv.openDialog(dulieu)
    },
    // load data khi mới mở form
    async loadDataFirst() {
      try{
        //await this.fetchDichVuList();
		await this.fetchDichVuList1();
        this.selectedDichVu = 1;
        await this.fetchLoaiHinhList();
        await this.filterLoaiHinhList();
        await this.napThongTinDichVuThueBao(null);
      }
      catch(ex){
        console.log(ex)
      }
    },
	async fetchDichVuList1() {
      try{
        let ds = await api.getDichVuList1();
        if (ds){
          this.dichVuList1 = ds
        }
      }
      catch(ex){
        console.log(ex)
      }
    },
    async loadTuHopDongTuVan(hdkh_cha_id) {
      // Lấy thông tin từ phiếu tư vấn
      // var d = bangts.GetDataSql(
      //   "select a.*, b.ma_tb, b.thuebao_id from " +
      //     DatabaseConstants.DB2 +
      //     ".hd_khachhang a, " +
      //     DatabaseConstants.DB2 +
      //     ".hd_thuebao b where a.hdkh_id = b.hdkh_id and a.hdkh_id =" +
      //     phdkh_cha_id
      // );
      // const dt = await api.lay_dulieu_theo_dieukien(
      //   "hd_thuebao",
      //   "ma_tb",
      //   "dichvuvt_id",
      //   "hdkh_id = " + hdkh_cha_id,
      //   ""
      // );
      // if (dt.length > 0) {
      //   this.requestFormInfo.dichVu = dt[0][1];
      //   this.subscriptionFormInfo.soMayAcc = dt[0][0];
      //   this.onMaTBKeyPress({ charCode: 13 });
      // } else {
      //   this.$toast.error("Khách hàng không có thuê bao yêu cầu thanh lý");
      // }
    },
    // click checkbox CTV
    chkCTVChange() {
      if (this.checkBoxCTV) {
        this.$refs.dlgCongTacVien.show(true, "ctv");
      } else {
        this.inputCTV = "";
      }
    },
    // click checkbox  Người giới thiệu
    chkNguoiGTChange() {
      if (this.checkBoxNguoiGT) {
        this.$refs.dlgNguoiGioiThieu.show(true, "gt");
      } else {
        this.inputNguoiGT = "";
      }
    },
    btnHoSo_Click() {
      if (
        this.hdkh_id === 0 &&
        this.maGiaoDich === ""
      ) {
        this.$toast.error("Chưa có thông tin hợp đồng !");
        return;
      }
      this.popupInHopDong_Param.hdkh_id = this.hdkh_id;
      this.popupInHopDong_Param.ma_gd = this.maGiaoDich;
      this.popupInHopDong_Param.ngay_yc = this.ngayYeuCau;
      this.popupInHopDong_Param.ngay_lhd = this.ngayLapHD;
      this.$refs.popupInHoSo.showModal();
    },
    // click thu hồi
    valueThuHoi(val) {
      this.valueThuHoiThietBi = val;
    },
    // click nút chấp nhận trong dialog người giới thiệu
    dlgNguoiGioiThieu_Accept(data) {
      this.nguoigt_id = data.data.nhanvien_id
      this.inputNguoiGT = data.data.ten_ht;
    },
    // click nút chấp nhận trong dialog CTV
    dlgCongTacVien_Accept(data) {
      this.ctv_id = data.data.nhanvien_id
      this.inputCTV = data.data.ten_ht;
    },
    // đổ data vào form khi enter mã giao dịch
    dataEnterMaGiaoDich(data) {
      this.maHopDong = data.ma_hd;
      this.ngayYeuCau = data.ngay_yc;
      this.ngayLapHD = data.ngaylap_hd;
      this.soMayAcc = data.ma_tb;
      this.tenThueBao = data.ten_tb;
      this.diaChiTB = data.diachi_tb;
      this.diaChiLD = data.diachi_ld;
      this.ghiChu = data.ghichu;
      this.maKH = data.ma_kh;
      this.tenKH = data.ten_kh;
      this.ngayHetHan = this.current_time;
      this.diaChiKH = data.diachi_kh;
      this.tongTienKhoanMuc = data.tongtien;
    },
    // click enter mã giao dịch
    async enterClickedMaGiaoDich() {
      // this.disabledBtnNhapMoi = true;
      // this.disabledBtnHuy = false;
      // this.themMoi = 0;
      // this.isActiveClickMaGiaoDich = true;
      // this.$root.showLoading(true);
      // const variables = {
      //   in_donvi_dl_id: 0,
      //   in_donvi_id: 0,
      //   in_loaihd_id: 7,
      //   in_ma_gd: this.maGiaoDich,
      //   in_nhanvien_id: 0,
      //   in_tthd_id: 0
      // };
      try {
        // await this.clickMaGiaoDich(variables);
        // if (this.dsMaGiaoDich.length) {
        //   this.dsThueBao = this.dsMaGiaoDich.map(item => {
        //     const list = {
        //       ma_tb: item.ma_tb
        //     };
        //     return list;
        //   });
        //   this.dataEnterMaGiaoDich(this.dsMaGiaoDich[0]);
        //   await this.napThongTinDichVuThueBao(this.dsMaGiaoDich[0]);
        //   this.dataInforMaGiaoDDich = this.dsMaGiaoDich[0];
        //   this.disabledBtnXoaHD = false;
        //   this.disabledBtnThanhToan = false;
        //   this.disabledBtnThemTB = false;
        //   this.disabledBtnXoaTB = false;
        // } else {
        //   const objDV = this.dichVuList1.find(
        //     item => item.DICHVUVT_ID === this.selectedDichVu
        //   );
        //   this.$toast.error(
        //     `Không tìm thấy thông tin về hợp đồng có mã giao dịch ${this.maGiaoDich} đối với dịch vụ ${objDV.TEN_DVVT}`
        //   );
        // }
        this.$root.showLoading(true);
        const variables = {
              in_donvi_dl_id: 0,
              in_donvi_id: parseInt(this.$root.token.getDonViID()),
              in_loaihd_id: 7,
              in_ma_gd: this.maGiaoDich,
              in_nhanvien_id: this.$root.token.getNhanVienID(),
              in_tthd_id: 1
            };
        let ds = await api.lay_ds_hopdong_theo_ma_gd(variables);
        if (ds && ds.length > 0)
            this.HienThiTTHopDongKH(ds);
        else {
          this.$toast.error('Không tìm thấy thông tin về hợp đồng có mã giao dịch: '+this.maGiaoDich+'. Bạn hãy kiểm tra lại');
        }
        this.$root.showLoading(false);
      } catch (error) {
        console.log(error)
        this.$root.showLoading(false);
        this.$toast.error('Lỗi tra cứu theo mã giao dịch!');
      }
    },
    /* lý do là ko đủ thông tin cần thiết nên cần lấy gộ 2 data lại */
    // gắn data vào biến dataSearchMaGiaoDichById khi chọn dòng trong dialog mã giao dịch (chỉ chọn chưa bấm chấp nhận)
    dataSelectMaGiaoDich(data) {
      this.dataSearchMaGiaoDichById = data;
    },
    // gắn data vào biến dataSearchMaGiaoDichByRow khi nhấn chấp nhân trong dialog mã giao dịch
    async accept(data) {
      if (data && data.ma_gd && data.ma_gd !=""){
        this.maGiaoDich = data.ma_gd
        this.$refs.maGiaoDich.focus()
        const variables = {
          in_donvi_dl_id: 0,
          in_donvi_id: parseInt(this.$root.token.getDonViID()),
          in_loaihd_id: 7,
          in_ma_gd: this.maGiaoDich,
          in_nhanvien_id: this.$root.token.getNhanVienID(),
          in_tthd_id: 1
        };
        const ds = await api.lay_ds_hopdong_theo_ma_gd(variables)
        if (ds)
          this.HienThiTTHopDongKH(ds)
      }
      // this.isActiveClickMaGiaoDich = true;
      // this.dataInforMaGiaoDDich = this.dataSearchMaGiaoDichById;
      // this.$root.showLoading(true);
      // this.dataSearchMaGiaoDichByRow = data;
      // this.fillDataSearchMaGiaoDich(
      //   this.dataSearchMaGiaoDichById,
      //   this.dataSearchMaGiaoDichByRow
      // );
      // this.dataInforMaGiaoDDich
      //   ? (this.disabledBtnXoaHD = false)
      //   : (this.disabledBtnXoaHD = true);
      // this.$root.showLoading(false);
      // this.setButton(4);
    },
    // show dialog search mã giao dịch
    showPopupSearchContract() {
      this.$refs.popupSearchContract.showModal();
    },
    // đổ data vào form khi search mã giao dich
    // (bao data khi click chọn row trong dialog và data khi click nút chấp nhận trong dialog)
    fillDataSearchMaGiaoDich(
      dataSearchMaGiaoDichById,
      dataSearchMaGiaoDichByRow
    ) {
      this.hdtbId = dataSearchMaGiaoDichById.hdtb_id
        ? dataSearchMaGiaoDichById.hdtb_id
        : null;
      this.maGiaoDich = dataSearchMaGiaoDichById.ma_gd;
      this.maHopDong = dataSearchMaGiaoDichByRow.ma_hd;
      this.ngayYeuCau = dataSearchMaGiaoDichByRow.ngay_yc;
      this.tongTien = dataSearchMaGiaoDichById.tongtien;
      this.ngayLapHD = dataSearchMaGiaoDichById.ngaylap_hd;
      this.selectedDichVu = dataSearchMaGiaoDichById.dichvuvt_id;
      this.selectedKieuYeuCau = dataSearchMaGiaoDichById.kieuld_id;
      this.selectedLoaiHinh = dataSearchMaGiaoDichById.loaitb_id;
      this.soMayAcc = dataSearchMaGiaoDichById.ma_tb;
      this.tenThueBao = dataSearchMaGiaoDichById.ten_tb;
      this.diaChiTB = dataSearchMaGiaoDichById.diachi_tb;
      this.trangThaiTB = dataSearchMaGiaoDichById.trangthai_hd;
      this.diaChiLD = dataSearchMaGiaoDichById.diachi_ld;
      this.ghiChu = dataSearchMaGiaoDichById.ghichu;
      this.maKH = dataSearchMaGiaoDichByRow
        ? dataSearchMaGiaoDichByRow.ma_kh
        : dataSearchMaGiaoDichById.ma_kh;
      this.ngayHetHan = dataSearchMaGiaoDichById.ngayhethan_gt;
      this.tenKH = dataSearchMaGiaoDichByRow
        ? dataSearchMaGiaoDichByRow.ten_kh
        : dataSearchMaGiaoDichById.ten_kh;
      this.diaChiKH = dataSearchMaGiaoDichByRow
        ? dataSearchMaGiaoDichByRow.diachi_kh
        : dataSearchMaGiaoDichById.diachi_kh;
    },
    // button click dialog search số máy Acc
    async btnSearchAccountClick() {
      if (!this.isEnterSoMay) {
        if (this.soMayAcc) {
          this.$toast.error(
            "Không có thông tin về thuê bao" +
              this.soMayAcc +
              "." +
              "Kiểm tra lại mã thuê bao."
          );
        } else {
          this.$toast.error("Bạn chưa nhập mã thuê bao");
        }
        return;
      }
      //this.$root.showLoading(true);
      if (!this.soMayAcc) {
        this.$bvModal.show("modal-somay");
        return;
      }
      this.$refs.tracuuthuebao.showModal(
        this.dataInforMaGiaoDDich.khachhang_id,
        this.selectedDichVu,
        this.maGiaoDich,
        "Đang thay đổi dịch vụ",
        'Danh sách các thuê bao của khách hàng thay đổi dịch vụ'
      );
      //this.$root.showLoading(false);
    },
    // LAY_DS_DK_LYDOHUY
    async LAY_DS_LYDOHUY() {
      try {
        this.dsDKHUY = await this.LAY_DS_DK_LYDOHUY({
          in_hdtb_id: this.hdtb_id
        });
      } catch (error) {
        console.log("error:", error);
      }
    },
    // click enter số máy Acc
    async txtMaTB_KeyPress() {
      this.dsDKHUY = [];
      if (this.soMayAcc) {
        this.isEnterSoMay = true;
        this.soMayAcc = this.soMayAcc.trim()
      }
      this.disabledBtnNhapMoi = true;
      this.disabledBtnHuy = false;
      this.$root.showLoading(true);
      const variables = {
        dichVuVtId: this.selectedDichVu ? this.selectedDichVu : 0,
        donViDlId: 0,
        loaiHdId: 7,
        loaiTbId: 0, //this.selectedLoaiHinh ? this.selectedLoaiHinh : 0,
        maTb: this.soMayAcc,
        ttHdId: 1
      };
      if (this.soMayAcc) {
        try {
          await this.clickSoMaAcc(variables);
          if (this.dsSoMayAcc.length === 0) {
            this.themMoi = 1;
            this.dataInforSoMayAcc = null;
            this.HienThiTT_DanhBa();
          } else {
            this.HienThiTTHopDongKH(this.dsSoMayAcc)
          }
        } catch (error) {
          this.$toast.error(error);
        }
      } else {
        this.$toast.error("Bạn chưa nhập mã thuê bao!");
      }
      await this.loadDSThuBao();
      this.$root.showLoading(false);
      //this.setButton(4);
    },
    async loadDSThuBao() {
      try {
        await this.clickSoMaAcc(variables);
        this.dsThueBao = this.dsSoMayAcc.map(item => {
          const list = {
            ma_tb: item.ma_tb
          };
          return list;
        });
      } catch (error) {}
    },
    async HienThiTTHopDongKH(ds){
      try
      {
          //cũ
          //this.disabledBtnThemTB = false;
          this.dsThueBao = this.dsSoMayAcc.map(item => {
            const list = {
              ma_tb: item.ma_tb
            };
            return list;
          });
          // this.selectedLoaiHinh = ds.loaitb_id;
          // this.loaitb_id = ds.loaitb_id
          this.themMoi = 0;
          this.dataInforSoMayAcc = ds[0];
          this.dataInforMaGiaoDDich = ds[0];
          //this.ghiChu = this.dataInforMaGiaoDDich.ghichu
          this.dataCha = this.dsSoMayAcc[0];
          //this.fillDataSearchSoMayAcc(this.dsSoMayAcc[0]);
          //await this.napThongTinDichVuThueBao(this.dsSoMayAcc[0]);
          //await this.LAY_DS_LYDOHUY();
          //cũ
          //this.hdkh_id khachhang_id maDuAn chkKHKT3.Checked ctv_id nguoigt_id
          if (ds.length > 0)
          {
              this.hdkh_id = parseInt(ds[0]["hdkh_id"]);
              this.maGiaoDich = ds[0]["ma_gd"]
              this.maHopDong = ds[0]["ma_hd"]
              this.maKH = ds[0]["ma_kh"]
              if (ds[0]["khachhang_id"] && ds[0]["khachhang_id"].toString() != "")
                  this.khachhang_id = parseInt(ds[0]["khachhang_id"])
              this.tenKH = ds[0]["ten_kh"]
              this.diaChiKH = ds[0]["diachi_kh"]
              this.ngayLapHD = ds[0]["ngaylap_hd"]
              this.ngayYeuCau = ds[0]["ngay_yc"]
              if (ds[0]["ma_duan"])
                  this.maDuAn = ds[0]["ma_duan"]
              if (ds[0]["kh_kt3"] && ds[0]["kh_kt3"].toString() == "1")
                  this.chkKHKT3.Checked = true;
              else
                  this.chkKHKT3.Checked = false;
              if (ds[0]["ctv_id"] && ds[0]["ctv_id"].toString() !='')
              {
                  this.checkBoxCTV = true
                  this.ctv_id = parseInt(ds[0]["ctv_id"]);
                  this.inputCTV = await api.fn_tt_nhanvien({param:this.ctv_id,type:3})
              }
              else
              {
                  this.checkBoxCTV = false
                  this.ctv_id = 0
                  this.inputCTV = ""
              }
              if (ds[0]["nhanviengt_id"] && ds[0]["nhanviengt_id"].toString() !='')
              {
                  this.checkBoxNguoiGT = true
                  this.nguoigt_id = parseInt(ds[0]["nhanviengt_id"])
                  this.inputNguoiGT = await api.fn_tt_nhanvien({param:this.nguoigt_id,type:3})
              }
              else
              {
                  this.checkBoxNguoiGT = false
                  this.nguoigt_id = 0
                  this.inputNguoiGT = ""
              }
              this.setButton(3)
              const res = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id);
              await this.HienThiTTHopDongTB(res);
              await this.HienThiDanhSacHDTB(this.hdkh_id);
          }
          else
          {
              //Message_Box.ShowTB("Không tìm thấy thông tin về mã giao dịch " + txtMaGD.Text);
              this.setButton(0)
          }
      }
      catch (exp)
      {
          console.log(exp)
      }
    },
    async HienThiTT_DanhBa() {
      try {
        const variables = {
          maTb: this.soMayAcc,
          dichVuVtId: this.selectedDichVu,
          donViDlId: null
        };
        const res = await this.LAY_DANHBA_THEO_MATB(variables);
        this.dataInforMaGiaoDDich = res;
        if (res && Object.keys(res).length > 0) {
          const row = res
          if (res.ky_hd === 0) {
            this.$toast.error(
              `Khách hàng có mã thuê bao: ${this.soMayAcc} chưa ký lại hợp đồng gốc. Đề nghị ký lại hợp đồng gốc`
            );
            return;
          }
          if (res.trangthaitb_id === TRANGTHAI_TB.THANHLY) {
            this.$toast.error(
              `Thuê bao ${this.soMayAcc} đang ở trạng thái thanh lý Không thể thực hiện thay đổi dịch vụ cho thuê bao này!`
            );
            return;
          }
          if (res.trangthaitb_id === TRANGTHAI_TB.THANHLY_NO) {
            this.$toast.error(
              `Thuê bao ${this.soMayAcc} đang ở trạng thái thanh lý nợ Không thể thực hiện thay đổi dịch vụ cho thuê bao này!`
            );
            return;
          }
          if (res.trangthaitb_id === TRANGTHAI_TB.DOISO) {
            this.$toast.error(
              `Thuê bao ${this.soMayAcc} đang ở trạng thái đổi số Không thể thực hiện thay đổi dịch vụ cho thuê bao này!`
            );
            return;
          }
          if (
            res.trangthaitb_id === TRANGTHAI_TB.KHOA_HAICHIEU_CUONGBUC &&
            parseInt(this.selectedDichVu) === DICHVU_VIENTHONG.ADSL*1
          ) {
            this.$toast.error(
              `Thuê bao ${this.soMayAcc} đang ở trạng thái khóa do nợ cước. Không thể thực hiện thay đổi dịch vụ cho thuê bao này!`
            );
            return;
          }
          this.maHopDong = res.ma_hd;
          this.ngayLapHD = res.ngaylap_hd ? res.ngaylap_hd : this.current_time;
          this.ngayLapHD = this.getDateFormat(this.ngayLapHD);
          this.trangThaiTB = res.trangthai_tb;
          this.tenThueBao = res.ten_tb;
          this.diaChiTB = res.diachi_tb;
          this.diaChiLD = res.diachi_ld;
          this.ghiChu = res.ghichu_tb;
          this.maKH = res.ma_kh;
          this.tenKH = res.ten_kh;
          this.ngayHetHan = this.current_time;
          this.diaChiKH = res.diachi_kh;
          this.selectedLoaiHinh = res.loaitb_id;
          this.loaitb_id = res.loaitb_id;
          this.khachhang_id = parseInt(res.khachhang_id)
          if (res.ma_duan)
            this.maDuAn = res.ma_duan
          this.thuebao_id = parseInt(res.thuebao_id)
          this.LAY_DS_ISDN_THEO_THUEBAO_ID(res.thuebao_id)
          this.soMayAcc = res.ma_tb
          //#region Bổ sung nghiệp vụ Siptrunk (Lamnt:25/09/2020)
          if (parseInt(this.selectedLoaiHinh) == LOAIHINH_THUEBAO.IMS_SIPTRUNKING)
              try
              {
                  if (this.IS_USING_SERVICE_SIPTRINK) //Nếu mở tham số IMS Siptrunk mới thì sẽ kiểm tra
                  {
                      //Nếu là siptrunk, chỉ cho phép tạo hợp đồng với những thuê bao đại diện đường trunk và có dữ liệu trunkID (Do WS y/c như vậy)
                      if (!row.thuebao_cha_id)
                      {
                          //todo: check hàm gọi ship
                          let dt = await api.lay_thongtin_ims({thuebao_id:row.thuebao_cha_id})
                          if (dt && dt.length > 0)
                          {
                              this.$toast.error("Thuê bao siptrunk, nghiệp vụ thay đổi dịch vụ GTGT chỉ lập hợp đồng với thuê bao đại diện đường trunk.\n\r Mã đại diện đường trunk: " + dt[0]["ma_tb"]);
                              return;
                          }
                          else
                          {
                              this.$toast.error("Không tìm thấy thuê bao đại diện cho đường trunk !");
                              return;
                          }
                      }
                  }
              }
              catch (ex)
              {
                  console.log("Lỗi: " + ex)
              }
          // #endregion
          this.doituong_id = res.doituong_id //tuanlv
          this.donviql_id = res.donviql_id
          //todo check to remove this
          this.doituong_id = row.doituong_id
          this.donvi_id = row.donviql_id
          this.ngayYeuCau = this.current_time;
          //todo: lấy dvgt từ ccbs
          // Lấy dữ liệu DV GTGT của TBTT từ CCBS
          if (this.TICHHOP_DIDONG_DHSX && parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.DI_DONG)
          {
              if (this.soMayAcc)
              {
                  this.dtThongTinTb = await api.ts_tracuu_laytt_tb({"so_tb":"84" + this.soMayAcc.trim()});
                  if (this.dtThongTinTb && this.dtThongTinTb.length>0)
                  {
                      if (Object.keys(this.dtThongTinTb[0]).length == 1)
                      {
                          this.$toast.error("Không tìm thấy thông tin thuê bao di động : " + this.soMayAcc + " trên CCBS ");
                          this.clearData()
                          return;
                      }
                      if (this.dtThongTinTb[0]["LOAI_TB"] == "GPhone")
                      {
                          this.$toast.error("Thông tin thuê bao: " + this.soMayAcc + " trên CCBS hiện đang là thuê bao GPhone ");
                          this.clearData()
                          return;
                      }
                  }
                  else
                  {
                      this.$toast.error("Không tìm thấy thông tin thuê bao di động : " + this.soMayAcc + " trên CCBS ");
                      this.clearData()
                      return;
                  }
                  //todo: change api link
                  this.dtSuDungDV = await api.ts_thongtin_dichvu_tb({"so_tb":"84" + this.soMayAcc.trim()});
                  if (this.dtSuDungDV != null && this.dtSuDungDV.length > 0)
                  {
                      let drDvKhongDangKy = this.dtSuDungDV.filter(item=>item.CM_DISABLE=='disabled')//.Select("CM_DISABLE = 'disabled'");
                      if (drDvKhongDangKy.length>0){
                        drDvKhongDangKy.forEach(e => {
                          this.listDvKhongDangKy.push(e['MA_DV'].trim());
                        });
                      }
                      else {
                        this.$toast.error("Không tìm thấy thông tin về dịch vụ GTGT thuê bao di động : " + this.soMayAcc + " trên CCBS ");
                        this.clearData()
                      }
                  }
                  else
                  {
                      this.$toast.error("Không tìm thấy thông tin về dịch vụ GTGT thuê bao di động : " + this.soMayAcc + " trên CCBS ");
                      this.clearData()
                  }
              }
              else
                  this.dtSuDungDV = []
          }
          this._sl_cha = 0;
          // dịch vụ CNTT/GTGT nạp NapThongTinDichVuThueBao ở HienThiTTDBMoRong
          if (parseInt(this.selectedDichVu) != DICHVU_VIENTHONG.DICHVU_CNTT && parseInt(this.selectedDichVu) != DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT
              && parseInt(this.selectedDichVu) != DICHVU_VIENTHONG.TRUNGTAM_DULIEU && parseInt(this.selectedDichVu) != DICHVU_VIENTHONG.HOINGHI_TRUYENHINH)
          {
              await this.napThongTinDichVuThueBao(null)
          }
          await this.HienThiTTDBMoRong(this.thuebao_id, parseInt(this.selectedDichVu));
          if ((parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.MEGAWAN || parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.TSL) && this.doitac_id != 0)
          {
              //CheckDataFacade checkdata = new CheckDataFacade();
              const str_doitac = await api.fn_tt_doitac({ param: this.doitac_id, type: 1 })
              this.$toast.error("Thuê bao này có  Hợp đồng/PLHĐ với đối tác " + str_doitac);
          }
          await this.HienThiTTDiaChi(this.thuebao_id, 3);
          //Hiếu bổ sung hàm tính tổng tiền & hiển thị tiền lên ô text 17.09.2010
          this.TinhTong_Tien_Dich_vu()
          this.HienThiTienDichVu()
          this.HT_DichVuKhac_DB()
        } else {
          const objDV = this.dichVuList1.find(
            item => parseInt(item.DICHVUVT_ID) === parseInt(this.selectedDichVu)
          );
          //Khaivk: Lấy thông tin thuê bao từ api ccbs
          if (this.TICHHOP_DIDONG_DHSX)
          {
              if (parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.DI_DONG)
              {
                  this.listDvKhongDangKy = []
                  //Clear();
                  this.dtThongTinTb = await api.ts_tracuu_laytt_tb({"so_tb":"84" + this.soMayAcc.trim()});
                  this.dtSuDungDV = await api.ts_thongtin_dichvu_tb({"so_tb":"84" + this.soMayAcc.trim()});
                  if (this.dtSuDungDV != null && this.dtSuDungDV.length > 0)
                  {
                      let drDvKhongDangKy = this.dtSuDungDV.filter(item=>item.CM_DISABLE=='disabled')//.Select("CM_DISABLE = 'disabled'");
                      if (drDvKhongDangKy.length>0){
                        drDvKhongDangKy.forEach(e => {
                          this.listDvKhongDangKy.push(e['MA_DV'].trim());
                        });
                      }
                  }
                  else
                  {
                      this.$toast.error("Không tìm thấy thông tin về dịch vụ GTGT thuê bao di động : " + this.soMayAcc + " trên CCBS ");
                      this.clearData()
                  }
                  if (this.dtThongTinTb != null)
                  {
                      if (this.dtThongTinTb.length > 0)
                      {
                          if (Object.keys(this.dtThongTinTb[0]).length == 1)
                          {
                              this.$toast.error("Không tìm thấy thông tin thuê bao di động : " + this.soMayAcc + " trên CCBS ");
                              return;
                          }
                          if (this.dtThongTinTb[0]["LOAI_TB"] == "GPhone")
                          {
                              this.$toast.error("Thông tin thuê bao: " + this.soMayAcc + " trên CCBS hiện đang là thuê bao GPhone ");
                              this.clearData()
                              return;
                          }
                          this.tenThueBao = this.dtThongTinTb[0]["TEN_KH"]
                          this.diaChiTB = this.dtThongTinTb[0]["DCHI_KH"]
                          this.diaChiLD = this.dtThongTinTb[0]["DCHI_KH"]
                          this.tenKH = this.dtThongTinTb[0]["TEN_KH"]
                          this.diaChiKH = this.dtThongTinTb[0]["DCHI_KH"]
                          this.ghiChu = ''
                          this.maHopDong=''
                          this.maGiaoDich=''
                          this.maKH = ''
                          this.khachhang_id = 0;
                          this.hdtb_id = 0;
                          this.thuebao_id = 0;
                          this.doituong_id = 1;
                          this.ngayLapHD = this.current_time;
                          this.ngayYeuCau = this.current_time;
                          if (this.dtThongTinTb[0]["TRANG_THAI"] && this.dtThongTinTb[0]["TRANG_THAI"].toString().trim() == "0")
                              this.trangThaiTB = "Hoạt động bình thường";
                          else if (this.dtThongTinTb[0]["GOI_DI"] && this.dtThongTinTb[0]["GOI_DI"].toString().trim() == "0")
                              this.trangThaiTB = "Khóa chiều gọi đi";
                          else if (this.dtThongTinTb[0]["GOI_DEN"] && this.dtThongTinTb[0]["GOI_DEN"].toString().trim() == "0")
                              this.trangThaiTB = "Khóa chiều gọi đến";
                          let loaiThueBao = this.dtThongTinTb[0]["LOAI_TB"]
                          if (this.dtThongTinTb[0]["TRA_SAU"] && this.dtThongTinTb[0]["TRA_SAU"].toString() == "1")
                          {
                              this.selectedLoaiHinh = LOAIHINH_THUEBAO.DIDONGTRASAU;
                          }
                          else
                          {
                              this.selectedLoaiHinh = LOAIHINH_THUEBAO.DIDONGTRATRUOC;
                          }
                          await this.napThongTinDichVuThueBao(null);
                      }
                      else
                      {
                          this.$toast.error("Không tìm thấy thông tin thuê bao di động : " + this.soMayAcc + " trên CCBS ");
                          this.clearData()
                      }
                  }
                  else
                  {
                      this.$toast.error("Không tìm thấy thông tin thuê bao di động : " + this.soMayAcc + " trên CCBS ");
                      this.clearData()
                  }
              }
              else
              {
                if(this.isShowMsg_SearhTB){
                  this.$toast.error(`Không tìm thấy thông tin thuê bao : ${this.soMayAcc} đối với dịch vụ ${objDV.TEN_DVVT}`);
                  this.clearData()
                }
                  
              }
          }//end khaivk
          else
          {
            if(this.isShowMsg_SearhTB){
              this.$toast.error(`Không tìm thấy thông tin thuê bao : ${this.soMayAcc} đối với dịch vụ ${objDV.TEN_DVVT}`);
              this.clearData()
            }
             
            
          }
        }
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi khi tra cứu thông tin!');
      }
    },
    async HienThiTTDBMoRong(thuebao_id, dichvuvt_id){
      console.log('HienThiTTDBMoRong')
      //todo
      //Lấy trang bị _id từ bảng con
      this.kt_kenhphu_ao = false;
      this.doitac_id = 0;
      this.maHDTTKD = ""
      this.soAo = ""
      this.huongKetNoi = ""
      let daset = []
      if (parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.CO_DINH)
      {
          daset = await api.lay_ds_db_cd_theo_tbid(thuebao_id)
          if (daset && daset.length > 0) //tuanlv
          {
              if (daset[0]["trangbi_id"] && daset[0]["trangbi_id"].toString().trim() != "")
                  this.trangbi_id = parseInt(daset[0]["trangbi_id"].toString().trim())
              this.tocdotn_id = 0;
              this.muccuoctn_id = 0;
          }
          else
              this.trangbi_id = 0;
      }
      else if (parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.IMS)
      {
          daset = await api.lay_ds_db_ims_theo_tbid(thuebao_id)
          if (daset && daset.length > 0)
          {
              if (daset[0]["trangbi_id"] && daset[0]["trangbi_id"].toString().trim() != "")
                  this.trangbi_id = parseInt(daset[0]["trangbi_id"].toString().trim())
              this.tocdotn_id = 0;
              this.muccuoctn_id = 0;
          }
          else
              this.trangbi_id = 0;
      }
      else if (parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.ADSL)
      {
          daset = await api.lay_ds_db_adsl_theo_tbid(thuebao_id)
          if (daset && daset.length > 0)
          {
              this.soAo = daset[0]["ma_lt"]
              if (daset[0]["trangbi_id"] && daset[0]["trangbi_id"].toString().trim() != "")
                  this.trangbi_id = parseInt(daset[0]["trangbi_id"].toString().trim())
              //TuanNA : Thêm code lấy thông tin tốc độ, mức cước để tìm ra hướng giao tiếp nhận -- Ngày 14/06/2014
              if (daset[0]["tocdo_id"] && daset[0]["tocdo_id"].toString().trim() != "")
                  this.tocdotn_id = parseInt(daset[0]["tocdo_id"].toString().trim())
              if (parseInt(this.selectedLoaiHinh) != LOAIHINH_THUEBAO.INTERNET_MYTV && parseInt(this.selectedLoaiHinh) != LOAIHINH_THUEBAO.INTERNET_MYTV_B2B && parseInt(this.selectedLoaiHinh) != LOAIHINH_THUEBAO.INTERNET_SGTV
                  && parseInt(this.selectedLoaiHinh) != LOAIHINH_THUEBAO.HTVC_FIBERVNN_ANALOG && parseInt(this.selectedLoaiHinh) != LOAIHINH_THUEBAO.HTVC_FIBERVNN_DIGITAL)
              {
                  if (daset[0]["muccuoc_id"] && daset[0]["muccuoc_id"].toString().trim() != "")
                    this.muccuoctn_id = parseInt(daset[0]["muccuoc_id"].toString().trim())
              }
              else //MyTV và SGTV có muccuoc = 0 => trọn gói
              {
                  this.muccuoctn_id = 0;
              }
          }
          else
              this.trangbi_id = 0;
      }
      else if (parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.MEGA_EYES)
      {
          daset = await api.lay_ds_db_adsl_theo_tbid(thuebao_id)
          if (daset && daset.length > 0)
          {
              this.soAo = daset[0]["ma_lt"]
              if (daset[0]["trangbi_id"] && daset[0]["trangbi_id"].toString().trim() != "")
                  this.trangbi_id = parseInt(daset[0]["trangbi_id"].toString().trim())
              //TuanNA : Thêm code lấy thông tin tốc độ, mức cước để tìm ra hướng giao tiếp nhận -- Ngày 14/06/2014
              if (daset[0]["tocdo_id"] && daset[0]["tocdo_id"].toString().trim() != "")
                  this.tocdotn_id = parseInt(daset[0]["tocdo_id"].toString().trim())
              this.muccuoctn_id = 0;
          }
          else
              this.trangbi_id = 0;
      }
      else if (parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.DI_DONG)
      {
          daset = await api.lay_ds_db_dd_theo_tbid( {"ID": thuebao_id});
          if (daset && daset.length > 0)
          {
              if (daset[0]["trangbi_id"] && daset[0]["trangbi_id"].toString().trim() != "")
                  this.trangbi_id = parseInt(daset[0]["trangbi_id"].toString().trim())
              else
                  this.trangbi_id = 0;
              this.tocdotn_id = 0;
              this.muccuoctn_id = 0;
          }
          else
              this.trangbi_id = 0;
      }
      else if (parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.MEGAWAN || parseInt(this.selectedDichVu) == 26)
      {
          daset = await api.lay_ds_db_megawan_theo_tbid(thuebao_id)
          if (daset && daset.length > 0)
          {
              this.soAo = daset[0]["ma_lt"]
              this.maHDTTKD = daset[0]["ma_pl"]
              this.huongKetNoi = daset[0]["huong_kn"]
              this.maERP = daset[0]["ma_bss"]
              this.maBSS = daset[0]["id_erp"]
              if (daset[0]["trangbi_id"] && daset[0]["trangbi_id"].toString().trim() != "")
                  this.trangbi_id = parseInt(daset[0]["trangbi_id"].toString().trim())
              //TuanNA : Thêm code lấy thông tin tốc độ, mức cước để tìm ra hướng giao tiếp nhận -- Ngày 14/06/2014
              if (daset[0]["tocdo_id"] && daset[0]["tocdo_id"].toString().trim() != "")
                  this.tocdotn_id = parseInt(daset[0]["tocdo_id"].toString().trim())
              this.muccuoctn_id = 0;
              if (daset[0]["kp_ao"] && daset[0]["kp_ao"].toString() == "1")
                  this.kt_kenhphu_ao = true;
              else
                  this.kt_kenhphu_ao = false;
              if (daset[0]["doitac_id"] && daset[0]["doitac_id"].toString().trim() != "")
                  this.doitac_id = parseInt(daset[0]["doitac_id"].toString().trim())
          }
          else
              trangbi_id = 0;
      }
      else if (parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.TSL || parseInt(this.selectedDichVu) == 25)
      {
          daset = await api.lay_ds_db_tsl_theo_tbid(thuebao_id);
          if (daset && daset.length > 0)
          {
              if (daset[0]["trangbi_id"] && daset[0]["trangbi_id"].toString().trim() != "")
                  this.trangbi_id = parseInt(daset[0]["trangbi_id"].toString().trim())
                  //TuanNA : Thêm code lấy thông tin tốc độ, mức cước để tìm ra hướng giao tiếp nhận -- Ngày 14/06/2014
              if (daset[0]["tocdo_id"] && daset[0]["tocdo_id"].toString().trim() != "")
                  this.tocdotn_id = parseInt(daset[0]["tocdo_id"].toString().trim())
              this.muccuoctn_id = 0;
              this.maHDTTKD = daset[0]["ma_pl"]
              if (daset[0]["doitac_id"] && daset[0]["doitac_id"].toString().trim() != "")
                  this.doitac_id = parseInt(daset[0]["doitac_id"].toString().trim())
              this.maERP = daset[0]["ma_bss"]
              this.maBSS = daset[0]["id_erp"]
          }
          else
              this.trangbi_id = 0;
          let soao_dau = "";
          let soao_cuoi = "";
          for (let i = 0; i < daset.length; i++)
          {
              if (daset[i]["daucuoi_id"] && parseInt(daset[i]["daucuoi_id"].toString()) == 1)//Nếu là điểm đầu
              {
                  soao_dau = daset[i]["ma_lt"] + ",";
                  let donvi_dau_id = api.fn_tt_db_thuebao({param: thuebao_id, type: 6}) //Convert.ToInt32(checkdata.MAP_ID("donvi_dau_id", DatabaseConstants.DB2 + ".db_thuebao", "where thuebao_id = " + thuebao_id));
                  //todo bổ sung đơn vị tsl
                  // bangts.HT_DonVi_loaidv_Combobox(cboVetinhTSL_Dau, donvi_dau_id, LOAI_DV.TRAM_VT);
                  // string str = "select * from " + DatabaseConstants.DB2 + ".dbtb_dv where thuebao_id = " + thuebao_id + " and loaidv_id = " + LOAI_DV.TRAM_VT + " and kieudv_id = 1";
                  // DataTable dtTemp = bangts.GetDataSql(str);
                  // if (dtTemp.Rows.Count > 0)
                  //     cboVetinhTSL_Dau.SelectedValue = dtTemp.Rows[0]["donvi_id"].ToString();
                  // txtDiaChiLD_Dau.Text = daset.Tables[0].Rows[i]["diachi"].ToString();
              }
              else
              {
                  soao_cuoi = daset[i]["ma_lt"] + ",";
                  let donvi_cuoi_id = api.fn_tt_db_thuebao({param: thuebao_id, type: 6}) //Convert.ToInt32(checkdata.MAP_ID("donvi_cuoi_id", DatabaseConstants.DB2 + ".db_thuebao", "where thuebao_id = " + thuebao_id));
                  //todo bổ sung đơn vị tsl
                  // bangts.HT_DonVi_loaidv_Combobox(cboVetinhTSL_Cuoi, donvi_cuoi_id, LOAI_DV.TRAM_VT);
                  // string str = "select * from " + DatabaseConstants.DB2 + ".dbtb_dv where thuebao_id = " + thuebao_id + " and loaidv_id = " + LOAI_DV.TRAM_VT + " and kieudv_id = 2";
                  // DataTable dtTemp = bangts.GetDataSql(str);
                  // if (dtTemp.Rows.Count > 0)
                  //     cboVetinhTSL_Cuoi.SelectedValue = dtTemp.Rows[0]["donvi_id"].ToString();
                  // txtDiaChiLD_Cuoi.Text = daset.Tables[0].Rows[i]["diachi"].ToString();
              }
          }
          this.soAo = soao_dau + soao_cuoi;
      }
      else if (parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.GPHONE)
      {
          daset = await this.LAY_DS_DB_GP_THEO_TBID(thuebao_id);
          if (daset && daset.length > 0)
          {
              if (daset[0]["trangbi_id"] && daset[0]["trangbi_id"].toString().trim() != "")
                  this.trangbi_id = parseInt(daset[0]["trangbi_id"].toString().trim())
          }
          else
              this.trangbi_id = 0;
          this.tocdotn_id = 0;
          this.muccuoctn_id = 0;
      }
      else if (parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.DICHVU_CNTT || parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT
          || parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.TRUNGTAM_DULIEU || parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.HOINGHI_TRUYENHINH) //Dich vu CNTT
      {
          //Init_Properties(Convert.ToInt32(cboLoaihinhTB.SelectedValue), "HDTB_CNTT", "00000", ref ItemList);
          this.Init_Properties()
          daset = await this.LAY_DS_DB_CNTT_THEO_TBID(thuebao_id);
          if (daset && daset.length > 0)
          {
              if (daset[0]["linhvuc_id"] && daset[0]["linhvuc_id"].toString().trim() != "")
                  this.linhvuc_id = parseInt(daset[0]["linhvuc_id"].toString().trim())
              else
                  this.linhvuc_id = 0;
              this.maHDTTKD = daset[0]["ma_pl"]
              if (daset[0]["tocdo_id"] && daset[0]["tocdo_id"].toString().trim() != "")
                  this.tocdotn_id = parseInt(daset[0]["tocdo_id"].toString().trim())
              else
                  this.tocdotn_id = 0;
              if (daset[0]["muccuoc_id"] && daset[0]["muccuoc_id"].toString().trim() != "")
                  this.muccuoctn_id = parseInt(daset[0]["muccuoc_id"].toString().trim())
              else
                  this.muccuoctn_id = 0;
              this._sl_cha = 0;
              if (parseInt(this.selectedLoaiHinh) == LOAIHINH_THUEBAO.IOFFICE)
              {
                  if (daset[0]["tongso_nd"] && daset[0]["tongso_nd"].toString().trim() != "")
                      this._sl_cha = parseInt(daset[0]["tongso_nd"].toString().trim())
                  else
                      this._sl_cha = 0;
              }
              if (parseInt(this.selectedLoaiHinh) == LOAIHINH_THUEBAO.HNTH_NGN
                  || parseInt(this.selectedLoaiHinh) == LOAIHINH_THUEBAO.VNPT_MEETING)
              {
                  if (daset[0]["sl_mailing"] && daset[0]["sl_mailing"].toString().trim() != "")
                      this._sl_cha = parseInt(daset[0]["sl_mailing"].toString().trim())
                  else
                      this._sl_cha = 0;
              }
          }
          else
          {
              this.linhvuc_id = 0;
              this.tocdotn_id = 0;
              this.muccuoctn_id = 0;
          }
          await this.napThongTinDichVuThueBao(null)
      }
    },
    async HT_DichVuKhac_DB(){
      this.dsDVKhac = []
      if (parseInt(this.selectedDichVu)!=DICHVU_VIENTHONG.ADSL)
        this.dsDVKhac = await api.LAY_DS_SUDUNG_DVK_KIEU_LD({"db_id":this.thuebao_id,"kieuld_id":parseInt(this.selectedKieuYeuCau),"loaiid":1})
    },
    getDateFormat(str) {
      return str.slice(0, 10);
    },
    // đổ data vào form khi nhập/chấp nhận từ dialog so may acc
    async fillDataSearchSoMayAcc(data) {
      this.maGiaoDich = data.ma_gd;
      this.maHopDong = data.ma_hd;
      this.ngayYeuCau = data.ngay_yc;
      this.ngayLapHD = data.ngaylap_hd ? data.ngaylap_hd : this.current_time;
      this.selectedDichVu = data.dichvuvt_id;
      this.selectedKieuYeuCau = data.kieuld_id;
      this.selectedLoaiHinh = data.loaitb_id;
      this.tongTien = data.tongtien;
      this.soMayAcc = data.ma_tb;
      this.tenThueBao = data.ten_tb;
      this.diaChiTB = data.diachi_tb;
      this.diaChiLD = data.diachi_ld;
      this.ghiChu = data.ghichu;
      this.trangThaiTB = data.trangthai_hd;
      this.maKH = data.ma_kh;
      this.tenKH = data.ten_kh;
      this.diaChiKH = data.diachi_kh;
      this.ngayHetHan =  this.current_time;
      const variables = {
        maTb: this.soMayAcc,
        dichVuVtId: this.selectedDichVu,
        donViDlId: null
      };
      const res = await this.LAY_DANHBA_THEO_MATB(variables);
      // this.dataInforMaGiaoDDich = res;
      this.trangThaiTB = res.trangthai_tb;
      this.setButton(4);
    },
    // click btn hiện dialog tổng tiền
    async btnTienKM_Click() {      
      //new popup
      let ds = null
      console.log('ds tiem km')
      console.log(this.ds_tien_khoanmuc)
      await api.post_lay_khoanmuc_tt_theo_loaihd(this.axios, { vloaihd_id: 7 }).then((response) => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            ds = response.data.data
            if (this.ds_tien_khoanmuc && this.ds_tien_khoanmuc.length > 0)
              for (var item of ds) {
                var items = this.ds_tien_khoanmuc.filter((x) => x.khoanmuctt_id == item.khoanmuctt_id)
                if (items && items.length > 0) {
                  item.tien = items[0].tien ? items[0].tien : 0
                  item.vat = items[0].vat ? items[0].vat : 0
                  item.tongtien = item.tien + item.vat
                }
              }
          }
        })
        this.popupComponentData = {
          ds: ds,
          cols: [
            { fieldName: 'tentat_kmtt', headerText: 'Khoản mục', allowFiltering: false, allowSorting: false, isPrimaryKey: true },
            { fieldName: 'tongtien', textAlign: 'right', allowEditing: true, editType: 'numericedit', format: 'N', customAttributes: { class: 'tongtien' }, headerText: 'Tổng tiền', allowFiltering: false, allowSorting: false, edit: 'tongtienParam' },
            { fieldName: 'tien', textAlign: 'right', allowEditing: true, editType: 'numericedit', format: 'N', customAttributes: { class: 'cuoclapdat' }, headerText: 'Tiền', allowFiltering: false, allowSorting: false, edit: 'tienParam' },
            { fieldName: 'vat', textAlign: 'right', format: 'N', customAttributes: { class: 'vatlapdat' }, headerText: 'Vat', allowFiltering: false, allowSorting: false },
            { fieldName: 'tyle_vat', textAlign: 'right', format: 'N', headerText: 'Tỷ lệ vat', allowFiltering: false, allowSorting: false }
          ],
          keycol: 'khoanmuctt_id',
          allowExit: false,
          tieude: 'Chi tiết tiền khoản mục',
          tieudebang: 'Chi tiết khoản mục thanh toán',
          vkieuld_id: parseInt(this.selectedKieuYeuCau),
          vloaihd_id: 7,
          vloaitb_id: parseInt(this.selectedLoaiHinh),
          ds_tien_caidat: this.ds_tien_khoanmuc,
          m_dsTienCTKM: []
        }
        this.popupComponentName = 'popupTienKM'
        this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupCT_TienKM')
        this.Popup('popupComponents')
    },
    Popup(modalId) {
      this.loading(false)
      if (this.$refs[modalId]) this.$refs[modalId].show()
      else this.$bvModal.show(modalId)
    },
    // click nút chấp nhận trong dialog tổng tiền
    dlgTienKM_Close(data) {
      this.ds_tien_khoanmuc = data.map(r => ({
        ...r,
        khoanmuctt_id: parseInt(r.khoanmuctt_id)
      }));
      if (this.ds_tien_khoanmuc.length > 0) {
        for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
          if (
            this.ds_tien_khoanmuc[i]["khoanmuctt_id"] ==
            KHOANMUC_TT.KMTT_KHOIPHUCSD
          ) {
            this.dtienld = parseFloat(this.ds_tien_khoanmuc[i]["tien"]);
            this.dvatld = parseFloat(this.ds_tien_khoanmuc[i]["vat"]);
          }
          if (
            this.ds_tien_khoanmuc[i]["khoanmuctt_id"] == KHOANMUC_TT.KMTT_DICHVU
          ) {
            this.dtien_dv = parseFloat(this.ds_tien_khoanmuc[i]["tien"]);
            this.dvat_dv = parseFloat(this.ds_tien_khoanmuc[i]["vat"]);
          }
        }
        this.LayTongTien_HDTB();
      }
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
      this.popupComponent = null
    },
    popupClosed(data) {
      console.log('popupClosed')
      console.log(data)
      try {
        this.ds_tien_khoanmuc = data.map(r => ({
          ...r,
          khoanmuctt_id: parseInt(r.khoanmuctt_id)
        }));
        if (this.ds_tien_khoanmuc.length > 0) {
          for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
            if (
              this.ds_tien_khoanmuc[i]["khoanmuctt_id"] ==
              KHOANMUC_TT.KMTT_KHOIPHUCSD
            ) {
              this.dtienld = parseFloat(this.ds_tien_khoanmuc[i]["tien"]);
              this.dvatld = parseFloat(this.ds_tien_khoanmuc[i]["vat"]);
            }
            if (
              this.ds_tien_khoanmuc[i]["khoanmuctt_id"] == KHOANMUC_TT.KMTT_DICHVU
            ) {
              this.dtien_dv = parseFloat(this.ds_tien_khoanmuc[i]["tien"]);
              this.dvat_dv = parseFloat(this.ds_tien_khoanmuc[i]["vat"]);
            }
          }
          this.LayTongTien_HDTB();
        }
      }
      catch (ex) {
        this.$toast.error('' + ex)
      } finally {
        this.ClosePopup('popupComponents')
      }
    },
    // lấy tổng tiền
    LayTongTien_HDTB() {
      let dtongtien = 0;
      let dtongvat = 0;
      try {
        for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
          dtongtien += parseInt(this.ds_tien_khoanmuc[i].tien);
          dtongvat += parseInt(this.ds_tien_khoanmuc[i].vat);
        }
        this.tongTienKhoanMuc = formatCurrencyNoVND(dtongtien);
        this.tongVat = dtongvat;
      } catch (err) {
        this.$toast.error(err);
      }
    },
    // click để mở dialog Lý do hủy
    btnLyDoHuyClick() {
      this.$refs.dlgHuy.show(true,{
        dsDkLyDoHuy:[],
        hdtbId:this.hdtb_id,
        isLapMoi:true,
        loaildId:2,
        loaitbId:parseInt(this.selectedLoaiHinh),
        kieu:1
      });
    },
    // click để mở dialog Dịch vụ khác
    async btnDichVuKhacClick() {
      //this.$refs.dlgDichVuKhac.show(true);
      //this.dsDVKhac = await api.LAY_DS_SUDUNG_DVK_KIEU_LD({"db_id":this.thuebao_id,"kieuld_id":parseInt(this.selectedKieuYeuCau),"loaiid":1})
      let dbDichVu = []
      if (parseInt(this.selectedKieuYeuCau)==0){
        dbDichVu = await api.LAY_DS_DICHVU_KHAC_DBID({db_id: this.thuebao_id,loai_id: 1,loaitb_id: parseInt(this.selectedLoaiHinh)})
      }
      else {
        dbDichVu = await api.LAY_DS_SUDUNG_DVK_KIEU_LD({"db_id":this.thuebao_id,"kieuld_id":parseInt(this.selectedKieuYeuCau),"loaiid":1})
      }
      this.$refs.ServiceOther.show(dbDichVu,this.dsDVKhac).then(result => {
        if (!result) {
          return
        }
        this.dsDVKhac = []
        for(let i= 0; i<result.dangky.length; i++){
          this.dsDVKhac.push(result.dangky[i])
        }
      })
    },
    // click nút liên hệ
    btn_Lien_He() {
      if (this.hdkh_id==0) {
        this.$toast.error("Chưa có thông tin hợp đồng!");
        return
      }
      this.$bvModal.show("ThongTinLienHe");
      //this.$refs.dlgLienHe.show(true);
      // this.$refs.thongTinLienHePopUp.getThongTinLienHe()
      // this.$refs.thongtinlienhe.show();
    },
    //click nút email
    btn_Email() {
      if (this.hdkh_id==0) {
        this.$toast.error("Chưa có thông tin hợp đồng!");
        return
      }
      this.$bvModal.show("ThongTinEmail");
      // this.$refs.popthongtinemail.getDsThongTinEmail()
      // this.$refs.thongtinemail.show(false);
    },
    // khi nhấn chấp nhận trong dialog lý do hủy
    onDialogLyDoHuyAccepted(data) {
      this.dsDKHUY = data;
      let lsTmp = [];
      for (const item of data) {
        if (
          !this.dsDKHUY.find(
            i => i.lydohuy === item.lydohuy && i.noidung === item.noidung
          )
        ) {
          lsTmp.push(item);
        }
      }
      this.dsDKHUY = [...this.dsDKHUY, ...lsTmp];
    },
    // khi nhấn chấp nhận trong dialog dịch vụ khác
    onDialogDichVuKhacAccepted(data) {
      let lsTmp = [];
      for (const item of data) {
        if (
          !this.dsDVKhac.find(
            i => i.lydohuy === item.lydohuy && i.noidung === item.noidung
          )
        ) {
          lsTmp.push(item);
        }
      }
      this.dsDVKhac = [...this.dsDVKhac, ...lsTmp];
    },
    // hàm nạp thông tin (để load lại danh sách dịch vụ gia tăng)
    async napThongTinDichVuThueBao(data) {
      this.arrayItemDaDangKy = []
      if (data !== null) {
        this.arrayItemDaDangKy =[]
        if (this.selectedDichVu === DICHVU_VIENTHONG.IMS) {
          if (this.tagForm === 1) {
            const variables = {
              hdtb_id: data.hdtb_id ? data.hdtb_id : 0,
              thuebao_id: data.thuebao_id,
              loaitb_id: this.selectedLoaiHinh,
              kieu_id: 0,
              trangthaitb_id: 1,
              kieuld_id: this.selectedKieuYeuCau
            };
            await this.Lay_ds_tb_dvgt_theo_tt_ims_v2(variables);
          } else {
            const variables = {
              hdtb_id: data.hdtb_id ? data.hdtb_id : 0,
              thuebao_id: data.thuebao_id,
              loaitb_id: this.selectedLoaiHinh,
              kieu_id: 0,//data.kieutn_id ? data.kieutn_id : 0,
              kieuld_id: this.selectedKieuYeuCau
            };
            await this.LAY_DS_THUEBAO_DICHVU_IMS_V2(variables);
          }
        } else if (
          this.selectedDichVu === DICHVU_VIENTHONG.TRUNGTAM_DULIEU ||
          this.selectedDichVu === DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT ||
          this.selectedDichVu === DICHVU_VIENTHONG.DICHVU_CNTT ||
          this.selectedDichVu === DICHVU_VIENTHONG.HOINGHI_TRUYENHINH
        ) {
          if (this.selectedLoaiHinh === LOAIHINH_THUEBAO.BRANDNAME) {
            const variables = {
              hdtb_id: data.hdtb_id ? data.hdtb_id : 0,
              thuebao_id: data.thuebao_id,
              loaitb_id: this.selectedLoaiHinh,
              kieu_id: 0,//data.kieutn_id ? data.kieutn_id : 0,
              kieuld_id: this.selectedKieuYeuCau,
              linhvuc_id: 0
            };
            await this.LAY_DS_THUEBAO_DICHVU_LINHVUC_V2(variables);
          } else {
            const variables = {
              hdtb_id: data.hdtb_id ? data.hdtb_id : 0,
              thuebao_id: data.thuebao_id,
              loaitb_id: this.selectedLoaiHinh ? this.selectedLoaiHinh : 0,
              kieu_id: 0,//data.kieutn_id ? data.kieutn_id : 0,
              kieuld_id: this.selectedKieuYeuCau
            };
            await this.LAY_DS_THUEBAO_DICHVU_V2(variables);
          }
        } else {
          if (this.tagForm === 1) {
            const variables = {
              hdtb_id: data.hdtb_id ? data.hdtb_id : 0,
              thuebao_id: data.thuebao_id ? data.thuebao_id : 0,
              loaitb_id: this.selectedLoaiHinh ? this.selectedLoaiHinh : 0,
              kieu_id: 0,//data.kieutn_id ? data.kieutn_id : 0,
              kieuld_id: this.selectedKieuYeuCau
            };
            await this.lay_ds_thuebao_dichvu_v2(variables);
          } else {
            const variables = {
              hdtb_id: data.hdtb_id ? data.hdtb_id : 0,
              thuebao_id: data.thuebao_id ? data.thuebao_id : 0,
              loaitb_id: this.selectedLoaiHinh ? this.selectedLoaiHinh : 0,
              kieu_id: 0,//data.kieutn_id ? data.kieutn_id : 0,
              kieuld_id: this.selectedKieuYeuCau
            };
            await this.LAY_DS_THUEBAO_DICHVU_V2(variables);
          }
        }
      }
      if (this.soMayAcc && data === null) {
        const variables = {
          hdtb_id: 0,
          thuebao_id: this.thuebao_id!=0?this.thuebao_id:0,
          loaitb_id: this.selectedLoaiHinh ? this.selectedLoaiHinh : 0,
          kieu_id: 0,
          kieuld_id: this.selectedKieuYeuCau ? this.selectedKieuYeuCau : 0
        };
        await this.LAY_DS_THUEBAO_DICHVU_V2(variables);
        this.$root.showLoading(false);
        //return;
      }
      if (!this.soMayAcc && data === null) {
        try {
          const variables = {
            hdtb_id: 0,
            thuebao_id: this.thuebao_id!=0?this.thuebao_id:0,
            loaitb_id: this.selectedLoaiHinh ? this.selectedLoaiHinh : 0,
            kieu_id: 0,
            kieuld_id: this.selectedKieuYeuCau ? this.selectedKieuYeuCau : 0
          };
          await this.LAY_DS_THUEBAO_DICHVU_V2(variables);
        } catch (error) {
          throw error;
        }
      }
      //code lấy thông tin ccbs
      if (this.TICHHOP_DIDONG_DHSX && parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.DI_DONG)
      {
          let temp_arrayItemDaDangKy = JSON.parse(JSON.stringify(this.dsDichVuGiaTang))
          if (parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.TAMDUNG_SD_DD || parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.MOTAMDUNG_SD_DD)
          {
              if (this.dtThongTinTb && this.dtThongTinTb.length > 0)
              {
                  for (let i = 0; i < temp_arrayItemDaDangKy.length; i++)
                  {
                      if (Object.keys(this.dtThongTinTb[0]).length > 1)
                      {
                          if (this.dtThongTinTb[0]["GOI_DI"] == "0")
                          {
                              if (temp_arrayItemDaDangKy[i]["ma_dvgt"] == "KGDDDYC")
                              {
                                  temp_arrayItemDaDangKy[i]["dangky"] = "0";
                                  temp_arrayItemDaDangKy[i]["sudung"] = "Su dung";
                              }
                          }
                          if (this.dtThongTinTb[0]["GOI_DEN"] == "0")
                          {
                              if (temp_arrayItemDaDangKy[i]["ma_dvgt"] == "TDDDYC")
                              {
                                  temp_arrayItemDaDangKy[i]["dangky"] = "0";
                                  temp_arrayItemDaDangKy[i]["sudung"] = "Su dung";
                              }
                          }
                      }
                  }
              }
          }
          if (this.dtSuDungDV.length > 0)
          {
              let drDvDangKy = this.dtSuDungDV.filter(x=>x.TRANG_THAI==1)
              for (let i = 0; i < temp_arrayItemDaDangKy.length; i++)
              {
                  for (let j = 0; j<drDvDangKy.length; j++){
                    if (temp_arrayItemDaDangKy[i]["ma_dvgt"] == drDvDangKy[j].MA_DV.trim())
                      {
                          temp_arrayItemDaDangKy[i]["dangky"] = "0";
                          temp_arrayItemDaDangKy[i]["sudung"] = "Su dung";
                      }
                  }
              }
          }
          await this.setDsDichVuGiaTang_new(temp_arrayItemDaDangKy)
      }
      this.arrayItemDaDangKy = JSON.parse(JSON.stringify(this.dsDichVuGiaTang))
      if (this.dsDichVuGiaTang) {
        for (let k = 0; k < this.dsDichVuGiaTang.length; k++)
        {
            if (this.dsDichVuGiaTang[k]["dangky"].toString() == "1"
                && this.dsDichVuGiaTang[k]["trangthaitb_id"].toString() == TRANGTHAI_TB.TAMDUNG.toString())
            {
                this.btnEditLyDoHuy.Enabled = true
                this.chkNgay_HHTD.Enabled = true;
                this.$refs.danhsachdichvugiatang.thuHoiEnabled = true
            }
            else
            {
                this.btnEditLyDoHuy.Enabled = false
                this.chkNgay_HHTD.Enabled = false;
            }
        }
      }
    },
    // mapping data kiểu yêu cầu của api_35 (khi ko có loại tb id)
    mappingDataKieuYeuCau(data) {
      const res = data.map(item => {
        const list = {
          ten_kieuld: item.TEN_KIEULD,
          kieuld_id: item.KIEULD_ID
        };
        return list;
      });
      return res;
    },
    // filter load loại hinh khi mới load form
    async filterLoaiHinhList() {
      this.loaiHinhListByFilter = this.loaiHinhList.filter(
        item => item.DICHVUVT_ID === this.selectedDichVu
      );
      if (this.loaiHinhListByFilter.length) {
        const status = this.checkDichVuId(this.selectedDichVu);
        if (status) {
          this.filterLoaiHinhByDichVuID(this.selectedDichVu);
          const variables = {
            vkieu: 0,
            vloaihd_id: 7,
            vloaitb_id: this.selectedLoaiHinh
          };
          await this.fetchKieuYeuCauListLD_BC(variables);
          this.dataKieuYeuCauList = this.kieuYeuCauListLD_BC;
          if (this.dataKieuYeuCauList.length) {
            this.dataKieuYeuCauList = this.dataKieuYeuCauList.filter(item=>item.tinhkhac==0)
            this.selectedKieuYeuCau = this.dataKieuYeuCauList[0].kieuld_id;
          } else {
            this.selectedKieuYeuCau = null;
          }
        } else {
          this.selectedLoaiHinh = this.loaiHinhListByFilter[0].LOAITB_ID;
        }
      } else {
        this.selectedLoaiHinh = null;
      }
    },
    // khi loaitb_id = 0 - api_035
    filterKieuYeuCauList() {
      this.kieuYeuCauListFilterByLoaiHdId = this.kieuYeuCauList.filter(
        item => item.LOAIHD_ID === LOAI_HOPDONG.THAY_DOI_DV
      );
      this.selectedKieuYeuCau = 23;
    },
    // chọn hiển thị loại hình theo các dịch vụ tương ứng
    filterLoaiHinhByDichVuID(dichVuId) {
      if (dichVuId === DICHVU_VIENTHONG.CO_DINH) {
        this.selectedLoaiHinh = LOAIHINH_THUEBAO.DIENTHOAI_CD;
        return;
      }
      if (dichVuId === DICHVU_VIENTHONG.ADSL) {
        this.selectedLoaiHinh = LOAIHINH_THUEBAO.MEGA;
        return;
      }
      if (dichVuId === DICHVU_VIENTHONG.TSL) {
        this.selectedLoaiHinh = LOAIHINH_THUEBAO.LEASEDLINE;
        return;
      }
      if (dichVuId === DICHVU_VIENTHONG.MEGAWAN) {
        this.selectedLoaiHinh = LOAIHINH_THUEBAO.MEGAWAN_ADSL;
        return;
      }
      if (dichVuId === DICHVU_VIENTHONG.DI_DONG) {
        this.selectedLoaiHinh = LOAIHINH_THUEBAO.DIDONGTRASAU;
        return;
      }
      if (dichVuId === DICHVU_VIENTHONG.MEGA_EYES) {
        this.selectedLoaiHinh = LOAIHINH_THUEBAO.MEGAEYES_CLOUD_CAMERA;
        return;
      }
    },
    // check xem dịch vụ id có nằm trong các list này ko để chọn loại hình id mặc định hiển thị đầu tương ứng.
    checkDichVuId(id) {
      const listDichVuId = [
        DICHVU_VIENTHONG.CO_DINH,
        DICHVU_VIENTHONG.ADSL,
        DICHVU_VIENTHONG.TSL,
        DICHVU_VIENTHONG.MEGAWAN,
        DICHVU_VIENTHONG.DI_DONG,
        DICHVU_VIENTHONG.MEGA_EYES
      ];
      const check = listDichVuId.includes(id);
      return check;
    },
    //  hàm load lại data khi thay đổi dịch vụ (vì thay đổi cả loại hình và kiểu yêu cầu) hoặc khi thay đổi loại hình, hoặc thay đổi kiểu yêu cầu
    async loadDataGridWhenChange() {
      this.$root.showLoading(true);
      try {
        // khi enter số máy
        if (
          this.soMayAcc &&
          this.dataInforSoMayAcc &&
          Object.keys(this.dataInforSoMayAcc).length > 0
        ) {
          await this.napThongTinDichVuThueBao(this.dataInforSoMayAcc);
        }
        // khi enter mã giao dịch
        if (
          this.dataInforMaGiaoDDich &&
          Object.keys(this.dataInforMaGiaoDDich).length > 0
        ) {
          await this.napThongTinDichVuThueBao(this.dataInforMaGiaoDDich);
        }
        if (!this.soMayAcc && !this.maGiaoDich) {
          await this.napThongTinDichVuThueBao(null);
        }
      } catch (error) {
        throw error;
      }
      this.$root.showLoading(false);
    },
    // event khi change dịch vụ
    async cboDichVuVT_SelectedValueChanged() {
      await this.loadDataGridWhenChange();
    },
    //event khi change loai hinh
    async changeLoaiHinh() {
      await this.loadDataGridWhenChange();
    },
    // event thay đổi kiểu yêu cầu
    async changeKieuYeuCau() {
      await this.loadDataGridWhenChange();
    },
    // data khi edit trên dòng xong => cộng tiền và thuế vat vs những row có đăng ký => cộng dồn in ra 2 ô tổng tiền và tổng vat
    dataEditCompleted(data) {
      return;
      this.dataDangKy = data;
      this.dataGhiLai = data;
      this.tongTienKhoanMuc = 0;
      this.tongVat = 0;
      if (data !== null && data !== undefined) {
        // xem data có thuộc mảng đki chưa
        const itemData =
          this.arrayItemDaDangKy.length !== 0
            ? this.arrayItemDaDangKy.find(
                item => item.ten_dvgt === data.ten_dvgt
              )
            : null;
        // nếu thuộc rồi
        if (itemData !== null && itemData !== undefined) {
          // nếu thuộc rồi mà hủy đăng kí
          if (!data.dangky) {
            this.arrayItemDaDangKy = this.arrayItemDaDangKy.filter(
              item => item.ten_dvgt != data.ten_dvgt
            );
          }
          // nếu thuộc rồi mà có đăng kí rồi chỉ update thông tin (tiền, vat, nội dung,...)
          if (data.dangky) {
            const itemIndex = this.arrayItemDaDangKy.findIndex(
              item => item.ten_dvgt === data.ten_dvgt
            );
            if (itemIndex !== -1) {
              this.arrayItemDaDangKy[itemIndex] = data;
            }
          }
        }
        // nếu chưa  thuộc
        else {
          // đăng ký push vào mảng
          if (data.dangky) {
            this.arrayItemDaDangKy.push(data);
          }
        }
      }
      // if (this.arrayItemDaDangKy.length) {
      //   for (let index = 0; index < this.arrayItemDaDangKy.length; index++) {
      //     const element = this.arrayItemDaDangKy[index];
      //     this.tongTienKhoanMuc += parseInt(element.tien.replace(/,/g, ""));
      //     this.tongVat += parseInt(element.vat_ld.replace(/,/g, ""));
      //   }
      // }
    },
    async UpdateGridDVGT(data) {
      let mess=""
      try {
        if(data && data.row>=0){
          const row = data.row
          const col = data.column
          let dgvDVGT = data.list
          //return data.list
          if(col==9){ //cot elearning
            if (parseInt(this.selectedLoaiHinh)!=LOAIHINH_THUEBAO.ELEARNING) {
              this.$toast.error(`Chức năng chỉ sử dụng cho dịch vụ VNPT Elearning`)
              this.arrayItemDaDangKy[row].mua_them=0
              this.arrayItemDaDangKy[row].tanglen=0
              return this.arrayItemDaDangKy
            } else {
              if(this.arrayItemDaDangKy[row].sudung=='') {
                this.$toast.error(`Hãy đăng ký trước khi mua thêm`)
                this.arrayItemDaDangKy[row].mua_them=0
                return this.arrayItemDaDangKy
              } else {
                if(parseInt(this.arrayItemDaDangKy[row].tanglen)==1){
                  this.arrayItemDaDangKy[row].noidung = parseInt(dgvDVGT[row].noidung) - parseInt(dgvDVGT[row].mua_them)
                }
                if(parseInt(this.arrayItemDaDangKy[row].tanglen)==0){
                  this.arrayItemDaDangKy[row].noidung = parseInt(dgvDVGT[row].noidung) + parseInt(dgvDVGT[row].mua_them)
                }
              }
            }
            this.HT_TongTien()
          } else if(col==6){ // Đăng ký
            if(this.arrayItemDaDangKy[row].sudung !=null && this.arrayItemDaDangKy[row].sudung!='') {
              //Nếu đã sử dụng thì không thể đăng ký
              if(parseInt(this.arrayItemDaDangKy[row].dangky) == 0){
                this.arrayItemDaDangKy[row].dangky = 0
              }
            } else { //Nếu chưa sử dụng
              if(parseInt(this.arrayItemDaDangKy[row].dangky) == 0){
                if (this.KT_DangKy_TD()) {
                  //this.cboKieuLD.Items = await api.sp_lay_ds_kieu_ld_bc(data)
                  const ds_km = await api.lay_ds_chitiet_km_theo_tbid({
                    "vkieu": 2,
                    "vthuebao_id": this.thuebao_id
                  })
                  if (ds_km && ds_km.length>0){
                    let tb = "Thuê bao " + this.soMayAcc.trim()
                    tb += " đang hưởng khuyến mại : \n " + ds_km[0].ten_km
                    tb += " \n    Có chi tiết khuyến mại : "
                    for (let i = 0; i < ds_km.length; i++)
                    {
                        tb += "\n     - " + ds_km[i].ten_ctkm
                    }
                    tb += "\nBạn không được thực hiện tiếp với thuê bao này!"
                    this.$toast.error(tb)
                    return this.arrayItemDaDangKy
                  }
                }
                // m_dsThueBao_DichVu.Tables[0].Rows[e.RowIndex]["DANGKY"] = 1;
                // m_dsThueBao_DichVu.Tables[0].Rows[e.RowIndex]["HUY"] = 0;
                this.arrayItemDaDangKy[row].dangky = 1
                this.arrayItemDaDangKy[row].huy = 0
                if (this.KT_KieuLD_TD() != 1 && this.KT_DangKy_TD())// Nếu ko chọn kiểu yc là TD mà đăng ký TD
                {
                    mess = "Dịch vụ này chỉ áp dụng với kiểu yêu cầu Tạm dừng "
                    this.arrayItemDaDangKy[row].dangky = 0
                    //lbl1.Focus();
                }
                else if (this.KT_KieuLD_TD() == 1 && this.KT_DangKy_TD())
                {
                    this.btnEditLyDoHuy.Enabled = true
                    //this.chkThuHoi.Checked = false;
                    //this.chkThuHoi.Enabled = true;
                    this.$refs.danhsachdichvugiatang.thuHoi = false
                    this.$refs.danhsachdichvugiatang.thuHoiEnabled = true
                    this.chkNgay_HHTD.Enabled = true;
                    this.chkNgay_HHTD.Checked = false;
                    this.checkBoxNgayHetHan = false
                    this.dtpNgayHH_TD.Enabled = false;
                }
                else if (this.KT_KieuLD_TD() == 1 && !this.KT_DangKy_TD())
                {
                    mess = "Bạn đang chọn kiểu yêu cầu Tạm dừng, chỉ được chọn đăng ký dịch vụ tạm dừng! ";
                    this.arrayItemDaDangKy[row].dangky = 0
                    //lbl1.Focus();
                }
                if (mess.length > 0)
                  this.$toast.error(mess)
              } else {
                this.arrayItemDaDangKy[row].dangky = 0
                if (!this.KT_DangKy_TD())
                {
                    this.btnEditLyDoHuy.Enabled = false
                    // this.chkThuHoi.Checked = false
                    // this.chkThuHoi.Enabled = false
                    this.$refs.danhsachdichvugiatang.thuHoi = false
                    this.$refs.danhsachdichvugiatang.thuHoiEnabled = false
                    this.dsBD_LYDOHUY = []
                    this.dsDKHUY = []
                    //lvLyDoHuy.Items.Clear(); todo
                    this.chkNgay_HHTD.Enabled = false
                    this.chkNgay_HHTD.Checked = false
                    this.checkBoxNgayHetHan = false
                    this.dtpNgayHH_TD.Enabled = false
                    //dtpNgayHH_TD.Value = tt_nd.ngay_cn;
                    this.ngayHetHan = this.current_time
                }
              }
            }
            this.HT_TongTien()//SonNMP bổ sung tính tổng tiền
          } else if (col== 7) {// Hủy
              if (this.arrayItemDaDangKy[row].sudung !=null && this.arrayItemDaDangKy[row].sudung!=''){
                if (parseInt(this.arrayItemDaDangKy[row].huy) == 0){
                  this.arrayItemDaDangKy[row].dangky = 0
                  this.arrayItemDaDangKy[row].sua = 0
                  this.arrayItemDaDangKy[row].huy = 1
                  if (this.KT_KieuLD_TD() != 0 && this.KT_Mo_TD())// Nếu ko chọn kiểu yc là mở TD mà chọn hủy TD
                  {
                      mess = "Dịch vụ này chỉ áp dụng với kiểu yêu cầu Mở tạm dừng ";
                      this.arrayItemDaDangKy[row].huy = 0
                      //lbl1.Focus();
                  }
                  else if (this.KT_KieuLD_TD() == 0 && !this.KT_Mo_TD()) // Nếu chọn mở tạm dừng mà ko chọn hủy TD
                  {
                      mess = "Bạn đang chọn kiểu yêu cầu mở tạm dừng, chỉ được chọn hủy tạm dừng! ";
                      this.arrayItemDaDangKy[row].huy = 0
                      //lbl1.Focus();
                  }
                  //Kieu LD là tạm dừng
                  if (this.KT_KieuLD_TD() == 1 && !this.KT_DangKy_TD()) // Nếu chọn tạm dừng mà chọn hủy DV khác
                  {
                      mess = "Bạn đang chọn kiểu yêu cầu tạm dừng, chỉ được chọn đăng ký tạm dừng! ";
                      this.arrayItemDaDangKy[row].huy = 0
                      //lbl1.Focus();
                  }
                  //bỏ qua code cứng
                  // if (DatabaseConstants.DB1 == "ADMIN_HNI")
                  // {
                  //     if (Convert.ToInt32(m_dsThueBao_DichVu.Tables[0].Rows[e.RowIndex]["DICHVUGT_ID"]) == 286 && (txtMaTB.Text == "37337687" || txtMaTB.Text == "38225356" || txtMaTB.Text == "38286999"))
                  //     {
                  //         mess = "Thuê bao khóa IDD theo VB 3522/VNPT-VP-TL. Không được phép mở ! ";
                  //         m_dsThueBao_DichVu.Tables[0].Rows[e.RowIndex]["HUY"] = 0;
                  //         lbl1.Focus();
                  //     }
                  // }
                  if (mess.length > 0)
                    this.$toast.error(mess)
                }
                else
                    this.arrayItemDaDangKy[row].huy = 0
              }
              else
                  this.arrayItemDaDangKy[row].huy = 0
          } else if (col== 8) { // Hoàng PKN : 26/06/2018 ô check Sửa
              if (parseInt(this.arrayItemDaDangKy[row].trangthaitb_id) != TRANGTHAI_TB.THUONG){
                 this.arrayItemDaDangKy[row].sua = 0
              }
              else
              {
                if (this.arrayItemDaDangKy[row].sudung != "")
                {
                  if (parseInt(this.arrayItemDaDangKy[row].sua) == 0)
                  {
                    this.arrayItemDaDangKy[row].dangky = 0
                    this.arrayItemDaDangKy[row].huy = 0
                    this.arrayItemDaDangKy[row].sua = 1
                  }
                  else
                      this.arrayItemDaDangKy[row].sua = 0
                }
                else
                    this.arrayItemDaDangKy[row].sua = 0
              }
              this.HT_TongTien();//SonNMP bổ sung tính tổng tiền
          }
          if ((dgvDVGT[row].noidung == "" ||dgvDVGT[row].noidung == null) && dgvDVGT[row].ma_dvgt == "D5" && dgvDVGT[row].dangky == "1"){
              const _key = Math.floor(Math.random() * 9999) + 1 //new Random().Next(1, 9999);
              this.arrayItemDaDangKy[row].noidung = this.padLeft(_key,4)
          }
          this.TinhTong_Tien_Dich_vu()
          this.HienThiTienDichVu()//tien_dv, vat_dv);
          let lth = this.arrayItemDaDangKy.filter(r => ((r["ma_dvgt"] == 'CB_FF_12' || r["ma_dvgt"] == 'CB_FF_24') && r["dangky"] == 1 )).length
          if (lth > 1) {
            this.arrayItemDaDangKy[row].dangky=0
          } else {
            lth = this.arrayItemDaDangKy.filter(r => ((r["ma_dvgt"] == 'FIM' || r["ma_dvgt"] == 'FAF') && r["dangky"] == 1 )).length
              if (lth > 1)
                  this.arrayItemDaDangKy[row].dangky=0
              else {
                lth = this.arrayItemDaDangKy.filter(r => ((r["ma_dvgt"] == 'CB_FF_12' || r["ma_dvgt"] == 'FAF') && r["dangky"] == 1 )).length
                  if (lth > 1)
                      this.arrayItemDaDangKy[row].dangky=0
                  else {
                    lth = this.arrayItemDaDangKy.filter(r => ((r["ma_dvgt"] == 'CB_FF_24' || r["ma_dvgt"] == 'FAF') && r["dangky"] == 1 )).length
                      if (lth > 1)
                          this.arrayItemDaDangKy[row].dangky=0
                      else {
                        lth = this.arrayItemDaDangKy.filter(r => ((r["ma_dvgt"] == 'CB_FF_24' || r["ma_dvgt"] == 'FIM') && r["dangky"] == 1 )).length
                          if (lth > 1)
                              this.arrayItemDaDangKy[row].dangky=0
                          else {
                            lth = this.arrayItemDaDangKy.filter(r => ((r["ma_dvgt"] == 'CB_FF_12' || r["ma_dvgt"] == 'FIM') && r["dangky"] == 1 )).length
                              if (lth > 1)
                                this.arrayItemDaDangKy[row].dangky=0
                          }
                      }
                  }
              }
          }
          //khaivk: Kiểm tra các dịch vụ gtgt không được phép ĐK
          if (this.TICHHOP_DIDONG_DHSX && parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.DI_DONG && parseInt(this.selectedKieuYeuCau) == 165)
          {
              if (this.listDvKhongDangKy.includes(this.arrayItemDaDangKy[row].ma_dvgt))
              {
                  this.$toast.error(
                    `Dịch vụ [${this.arrayItemDaDangKy[row].ma_dvgt} - ${this.arrayItemDaDangKy[row].ten_dvgt}] không cho phép đăng ký!`
                  );
                  this.arrayItemDaDangKy[row].dangky = 0
              }
          }
        }
        return JSON.parse(JSON.stringify(this.arrayItemDaDangKy))
      }
      catch(ex){
        console.log(ex)
        return data.list
      }
    },
    async UpdateGridDVGT_noidung(data){
      if (data && data.item && this.arrayItemDaDangKy[data.row])
        this.arrayItemDaDangKy[data.row].noidung = data.item.noidung
    },
    async UpdateGridDVGT_cuocld(data){
      if (data && data.item && this.arrayItemDaDangKy[data.row])
        this.arrayItemDaDangKy[data.row].cuoc_ld = data.item.cuoc_ld
    },
    async UpdateGridDVGT_vatld(data){
      if (data && data.item && this.arrayItemDaDangKy[data.row])
        this.arrayItemDaDangKy[data.row].vat_ld = data.item.vat_ld
    },
    TinhTong_Tien_Dich_vu(){
      //todo
      this.tien_dv = 0;
      this.vat_dv = 0;
      for (let row = 0; row < this.arrayItemDaDangKy.length; row++)
      {
          if ((this.arrayItemDaDangKy[row].dangky == 1 || this.arrayItemDaDangKy[row].sua == 1) && this.arrayItemDaDangKy[row].huy == "0")
          {
              this.tien_dv = this.tien_dv + parseInt(this.arrayItemDaDangKy[row].cuoc_ld!=null ? this.arrayItemDaDangKy[row].cuoc_ld.toString().replace(/,/g, ""):"0");
              this.vat_dv = this.vat_dv + parseInt(this.arrayItemDaDangKy[row].vat_ld!=null ? this.arrayItemDaDangKy[row].vat_ld.toString().replace(/,/g, ""):"0");
          }
      }
    },
    HienThiTienDichVu(){
      //todo
      this.dtien_dv = this.tien_dv;
      this.dvat_dv = this.vat_dv;
      for (let j = 0; j < this.ds_tien_khoanmuc.length; j++)
      {
          if (this.ds_tien_khoanmuc[j].khoanmuctt_id == KHOANMUC_TT.KMTT_DICHVU)
          {
              this.ds_tien_khoanmuc[j].tien = this.dtien_dv
              this.ds_tien_khoanmuc[j].vat = this.dvat_dv
          }
      }
      this.LayTongTien_HDTB();
    },
    HT_TongTien(){
      //todo
      try
      {
          let stien = 0;
          let svat = 0;
          let tien_tbi = 0
          let vat_tbi = 0
          let checkdv_sl = 0
          for (let row = 0; row < this.arrayItemDaDangKy.length; row++)
          {
              if (this.arrayItemDaDangKy[row].dangky == "1" || this.arrayItemDaDangKy[row].sua == "1" || this.arrayItemDaDangKy[row].tanglen == "1"){
                  let cuocsd = this.arrayItemDaDangKy[row].cuoc_sd!=null ? this.arrayItemDaDangKy[row].cuoc_sd.toString().replace(/,/g, ""):"0"
                  let vatsd = this.arrayItemDaDangKy[row].vat_sd!=null ? this.arrayItemDaDangKy[row].vat_sd.toString().replace(/,/g, ""):"0"
                  let vtien = parseInt(cuocsd)
                  let vvat = parseInt(vatsd)
                  let count = 1;
                  //todo check danhSachDichVuSoLuong
                  checkdv_sl = this.danhSachDichVuSoLuong.filter(
                    item => item.dichvugt_id === this.arrayItemDaDangKy[row].dichvugt_id
                  ).length;
                  if (checkdv_sl>0)
                      if (this.arrayItemDaDangKy[row].tanglen == "1")
                      {
                        if (parseInt(this.selectedLoaiHinh) == LOAIHINH_THUEBAO.ELEARNING)
                        {
                          try {
                            count = parseInt(this.arrayItemDaDangKy[row].mua_them)
                          }
                          catch(ex){}
                        }
                      }
                      else
                      {
                        try {
                          count = parseInt(this.arrayItemDaDangKy[row].noidung)
                        }
                        catch(ex){}
                      }
                  if (count == 0 || isNaN(count))
                      count = 1;
                  stien += vtien * count;
                  svat += vvat * count;
                  let block_dau = 0;
                  try {
                    block_dau = parseInt(this.arrayItemDaDangKy[row].block_dau.toString().replace(/,/g, ""))
                  }
                  catch(ex){}
                  let vsl_cha = parseInt(this.arrayItemDaDangKy[row].sl_cha)
                  if (vsl_cha != -1)
                  {
                      if (this.arrayItemDaDangKy[row].block_tiep != ""
                          && this.arrayItemDaDangKy[row].gia_block_tiep != ""
                          && this.arrayItemDaDangKy[row].gia_block_tiep != "0")
                      {
                          if (vsl_cha > block_dau)
                          {
                              let vsl_conlai = vsl_cha - block_dau;
                              let t2 = vsl_conlai % parseInt(this.arrayItemDaDangKy[row].block_tiep.toString().replace(/,/g, ""))
                              let vsl_bl = 0;
                              if (t2 > 0)
                                  vsl_bl = vsl_conlai / parseInt(this.arrayItemDaDangKy[row].block_tiep.toString().replace(/,/g, "")) + 1;
                              else
                                  vsl_bl = vsl_conlai / parseInt(this.arrayItemDaDangKy[row].block_tiep.toString().replace(/,/g, ""));
                              let tien_block = 0;
                              tien_block = vsl_bl * parseInt(this.arrayItemDaDangKy[row].gia_block_tiep.toString().replace(/,/g, ""));
                              //dtien_tbi_tmp += tien_block;
                              tien_tbi = tien_tbi + (tien_block * count);
                          }
                      }
                      if (this.arrayItemDaDangKy[row].block_tiep != ""
                          && this.arrayItemDaDangKy[row].vat_block_tiep != ""
                          && this.arrayItemDaDangKy[row].vat_block_tiep != "0")
                      {
                          if (vsl_cha > block_dau)
                          {
                              let vsl_conlai = vsl_cha - block_dau;
                              let t3 = vsl_conlai % parseInt(this.arrayItemDaDangKy[row].block_tiep.toString().replace(/,/g, ""))
                              let rsl_bl = 0;
                              if (t3 > 0)
                                  rsl_bl = vsl_conlai / parseInt(this.arrayItemDaDangKy[row].block_tiep.toString().replace(/,/g, "")) + 1
                              else
                                  rsl_bl = vsl_conlai / parseInt(this.arrayItemDaDangKy[row].block_tiep.toString().replace(/,/g, ""))
                              let vat_block = 0;
                              vat_block = rsl_bl * parseInt(this.arrayItemDaDangKy[row].vat_block_tiep.toString().replace(/,/g, ""))
                              //dvat_tbi_tmp += vat_block;
                              vat_tbi = vat_tbi + (vat_block * count)
                          }
                      }
                  }
              }
          }
          this.lblTong = formatCurrencyNoVND(stien + svat + tien_tbi + vat_tbi) +'VNĐ';
          this.txt_SumTien = formatCurrencyNoVND(stien) +"VNĐ";
          this.txt_SumVat = formatCurrencyNoVND(svat) +"VNĐ";
          this.txtSumGiaBlock = formatCurrencyNoVND(tien_tbi) +"VNĐ";
          this.txtSumVatBlock = formatCurrencyNoVND(vat_tbi) +"VNĐ";
      }
      catch(e){
        console.log(e)
      }
    },
    KT_Mo_TD(){
      let kt = false
      try
      {
          for (let i = 0; i < this.arrayItemDaDangKy.length; i++)
          {
              if (this.arrayItemDaDangKy[i].huy != "")
              {
                  if (this.arrayItemDaDangKy[i].huy == 1 && (parseInt(this.arrayItemDaDangKy[i].trangthaitb_id) == TRANGTHAI_TB.TAMDUNG || parseInt(this.arrayItemDaDangKy[i].trangthaitb_id) == TRANGTHAI_TB.KHOA_MOTCHIEU_YEUCAU)) {
                      kt = true;
                      break;
                  }
              }
          }
      }
      catch (ex)
      {
          return kt
      }
      return kt
    },
    KT_DangKy_TD(){
      let kt = false
      try
      {
          for (let i = 0; i < this.arrayItemDaDangKy.length; i++)
          {
              if (this.arrayItemDaDangKy[i].dangky != "")
              {
                  if (this.arrayItemDaDangKy[i].dangky == 1 && (parseInt(this.arrayItemDaDangKy[i].trangthaitb_id) == TRANGTHAI_TB.TAMDUNG || parseInt(this.arrayItemDaDangKy[i].trangthaitb_id) == TRANGTHAI_TB.KHOA_MOTCHIEU_YEUCAU)) {
                      kt = true;
                      break;
                  }
              }
          }
      }
      catch (ex)
      {
          return kt
      }
      return kt
    },
    KT_KieuLD_TD(){
        let kt = 0;//0 :Mặc định là mở tạm dừng; 1: tạm dừng; 2: thay đổi dv thông thường
        try
        {
          switch (parseInt(this.selectedDichVu))
          {
            case DICHVU_VIENTHONG.CO_DINH:
                if (parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.TAMDUNG_SD_CD)
                    kt = 1;
                else if (parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.MOTAMDUNG_SD_CD)
                    kt = 0;
                else
                    kt = 2;
                break;
            case DICHVU_VIENTHONG.DI_DONG:
                if (parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.TAMDUNG_SD_DD)
                    kt = 1;
                else if (parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.MOTAMDUNG_SD_DD)
                    kt = 0;
                else
                    kt = 2;
                break;
            case DICHVU_VIENTHONG.MEGAWAN:
            case 26:
                if (parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.TAMDUNG_SD_MGWAN
                    || parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.TAMDUNG_SD_KP_AO)
                    kt = 1;
                else if (parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.MOTAMDUNG_SD_MGWAN
                    || parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.MOTAMDUNG_SD_KP_AO)
                    kt = 0;
                else
                    kt = 2;
                break;
            case DICHVU_VIENTHONG.METRONET:
                if (parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.TAMDUNG_SD_METRONET
                    || parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.TAMDUNG_SD_KP_AO)
                    kt = 1;
                else if (parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.MOTAMDUNG_SD_METRONET
                    || parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.MOTAMDUNG_SD_KP_AO)
                    kt = 0;
                else
                    kt = 2;
                break;
            case DICHVU_VIENTHONG.ADSL:
                if (parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.TAMDUNG_SD_MEGA)
                    kt = 1;
                else if (parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.MOTAMDUNG_SD_MEGA)
                    kt = 0;
                else
                    kt = 2;
                break;
            case DICHVU_VIENTHONG.EMAIL:
                if (parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.TAMDUNG_SD_EMAIL)
                    kt = 1;
                else if (parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.MOTAMDUNG_SD_EMAIL)
                    kt = 0;
                else
                    kt = 2;
                break;
            case DICHVU_VIENTHONG.TSL:
            case 25:
                if (parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.TAMDUNG_SD_TSL)
                    kt = 1;
                else if (parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.MOTAMDUNG_SD_TSL)
                    kt = 0;
                else
                    kt = 2;
                break;
            case DICHVU_VIENTHONG.GPHONE:
                if (parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.TAMDUNG_SD_GP)
                    kt = 1;
                else if (parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.MOTAMDUNG_SD_GP)
                    kt = 0;
                else
                    kt = 2;
                break;
            case DICHVU_VIENTHONG.IMS:
                if (parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.TAMDUNG_SD_IMS)
                    kt = 1;
                else if (parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.MOTAMDUNG_SD_IMS)
                    kt = 0;
                else
                    kt = 2;
                break;
            case DICHVU_VIENTHONG.MEGA_EYES:
                if (parseInt(this.selectedKieuYeuCau) == 126){
                    kt = 1;
                }
                else if (parseInt(this.selectedKieuYeuCau) == 127)
                    kt = 0;
                else
                    kt = 2;
                break;
            case DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT:
            case DICHVU_VIENTHONG.DICHVU_CNTT:
            case DICHVU_VIENTHONG.TRUNGTAM_DULIEU:
            case DICHVU_VIENTHONG.HOINGHI_TRUYENHINH:
                if (parseInt(this.selectedKieuYeuCau) == 714)//Tạm dừng
                    kt = 1;
                else if (parseInt(this.selectedKieuYeuCau) == 715) //Mở tạm dừng
                    kt = 0;
                else
                    kt = 2;
                break;
            default:
                if (parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.TAMDUNG_SD_CD)
                    kt = 1;
                else if (parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.MOTAMDUNG_SD_CD)
                    kt = 0;
                else
                    kt = 2;
                break;
          }
        }
        catch (ex)
        {
            console.log(ex)
            return kt
        }
        return kt
    },
    dgvDVGT_CellContentClick (data){
      //this.arrayItemDaDangKy = data.list;
    },
    // action nút nhập mới
    async nhapMoi() {
      // ds_tien_khoanmuc
      
	  const variables = {
        loaihd_id: 7
      };
      await this.getTongTienKhoanMucList(variables);
      this.ds_tien_khoanmuc = this.dsTongTienKhoanMuc;
      this.themMoi = 1;
      this.setButton(1);
      this.isEnterSoMay = false;
    },
    // action nút hủy
    async huy() {
      await this.clearData();
      this.disabledBtnNhapMoi = false;
      this.disabledBtnGhiLai = true;
      this.disabledBtnThemTB = true;
      this.disabledBtnThanhToan = true;
      this.disabledBtnXoaHD = true;
      this.disabledBtnHuy = true;
      this.themMoi = 0;
      //this.themMoiTB = false;
      this.dataCha = {};
      this.isEnterSoMay = false;
    },
    async KiemTraDangKyDichVu(data) {
      let i=0
      let vdshuy_dvgt = ""
      for(let j=0;j<this.arrayItemDaDangKy.length;j++){
        let dr=this.arrayItemDaDangKy[j]
        if (dr["huy"].toString() == "1")
        {
            vdshuy_dvgt += dr["dichvugt_id"].toString() + ",";
        }
        if (dr["dangky"].toString() == "1")
        {
            const kt = await api.kiemTraDK_DV_GT({
              thuebao_id: this.thuebao_id,
              dichvugt_id: dr.dichvugt_id
            })
            if (kt=="1")
            {
                this.$toast.error("Không đăng ký thêm dịch vụ đối với thuê bao này !")
                return false
            }
        }
        // hoangpkn : 13/06/2015
        if ((dr["dangky"].toString() == "1" || dr["sua"].toString() == "1")&& dr["flag"]&& dr["flag"].toString() == "1" && (dr["noidung"] ==null || dr["noidung"].toString() == ""))
        {
            this.$toast.error("Bạn chưa nhập '" + dr["thongbao"] + "' vào ô Nội dung cho dịch vụ '" + dr["ten_dvgt"] + "' !");
            // dgvDVGT.ClearSelection(); //todo check lại hàm clear select lưới
            // dgvDVGT.Rows[i].Selected = true;
            // dgvDVGT.CurrentCell = dgvDVGT.Rows[i].Cells["noidung"];
            return false;
        }
        if ((dr["dangky"].toString() == "1" || dr["sua"].toString() == "1") && dr["flag"]&& dr["flag"].toString() == "2" && (dr["noidung"] ==null || dr["noidung"].toString() == ""))
        {
            const cf = await this.$bvModal.msgBoxConfirm(`Bạn có nhập ${dr.thongbao} cho dịch vụ ${dr.ten_dvgt} hay không ?`,
              {
                okTitle: "Có",
                cancelTitle: "Không"
              }
            );
            if (cf) return false;
        }
        if ((dr["dangky"].toString() == "1" || dr["sua"].toString() == "1") && dr["kieu_dl"] != null && dr["kieu_dl"].toString() == "1" && dr["noidung"] != ""&& dr["noidung"] != null){
            let s = dr["noidung"].toString();//tuanlv
            if (!this.CheckIsNumber(this.Unformat_tien(s)))
            {
                this.$toast.error("Nội dung của dịch vụ '" + dr["ten_dvgt"] + " phải là kiểu số !");
                //todo check lại hàm clear select lưới
                // dgvDVGT.ClearSelection();
                // dgvDVGT.Rows[i].Selected = true;
                // dgvDVGT.CurrentCell = dgvDVGT.Rows[i].Cells["noidung"];
                return false;
            }
        }
        // end hoang
        i += 1;
      }
      if (vdshuy_dvgt.length > 0)
      {
          vdshuy_dvgt = vdshuy_dvgt.substring(0, vdshuy_dvgt.length - 1);
          const variables = {
            thuebao_id: this.thuebao_id,
            ds_dichvugt_id: vdshuy_dvgt
          };
          const kt_huy_dvgt = await api.kiemtra_km_datcoc_dvgt(variables);
          if (kt_huy_dvgt !== "OK") {
            this.$toast.error(kt_huy_dvgt);
            return false;
          }
      }
      if (parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.GPHONE)
      {
        {
          for(let j=0;j<this.arrayItemDaDangKy.length;j++){
            let dr=this.arrayItemDaDangKy[j]
            //Kiểm tra nếu thuê bao đang khóa 1 chiều thì không cho đăng ký tạm dựng hoặc khóa cước
            if (dr["dangky"].toString() == "1" &&
                (parseInt(dr["dichvugt_id"]) == DichVuGiaTang.TAMDUNG_GPHONE || parseInt(dr["dichvugt_id"]) == DichVuGiaTang.KHOACUOC_GPHONE
                || parseInt(dr["dichvugt_id"]) == DichVuGiaTang.KHOAMOTCHIEU_GPHONE))//Nếu đăng ký tạm dừng hoăc khóa cước hoặc khóa 1 chiều
            {
              for(let k=0;k<this.arrayItemDaDangKy.length;k++){
                let dr1=this.arrayItemDaDangKy[k]
                //nếu đang sử dụng khóa 1 chiều theo yêu cầu
                if (dr1["sudung"] && dr1["sudung"].toString().trim().toLowerCase() == "sử dụng" && parseInt(dr["dichvugt_id"]) == DichVuGiaTang.KHOAMOTCHIEU_GPHONE)
                {
                    this.$toast.error("Thuê bao " + this.soMayAcc +
                        " đang sử dụng dịch vụ khóa 1 chiều theo yêu cầu! \nBạn không thể đăng ký thêm dịch vụ tạm dừng hoặc khóa cước!");
                    return false;
                }
                //Nếu chọn đồng thời khóa cước hoặc khóa 1 chiều hoặc tạm dừng thì return
                //Đồng thời khóa 1 chiều và khóa cước
                if ((parseInt(dr["dichvugt_id"]) == DichVuGiaTang.KHOACUOC_GPHONE && parseInt(dr1["dichvugt_id"]) == DichVuGiaTang.KHOAMOTCHIEU_GPHONE && dr1["dangky"].toString().trim() == "1")
                      || (parseInt(dr["dichvugt_id"]) == DichVuGiaTang.KHOAMOTCHIEU_GPHONE && parseInt(dr1["dichvugt_id"]) == DichVuGiaTang.KHOACUOC_GPHONE && dr1["dangky"].toString().trim() == "1"))
                {
                    this.$toast.error("Bạn không được đăng ký đồng thời dịch vụ 'Khóa 1 chiều theo yêu cầu' và 'Khóa cước Gphone'");
                    return false;
                }
                //Đồng thời khóa cước & tạm dừng
                if ((parseInt(dr["dichvugt_id"]) == DichVuGiaTang.KHOACUOC_GPHONE && parseInt(dr1["dichvugt_id"]) == DichVuGiaTang.TAMDUNG_GPHONE && dr1["dangky"].toString().trim() == "1")
                || (parseInt(dr["dichvugt_id"]) == DichVuGiaTang.TAMDUNG_GPHONE && parseInt(dr1["dichvugt_id"]) == DichVuGiaTang.KHOACUOC_GPHONE && dr1["dangky"].toString().trim() == "1"))
                {
                    this.$toast.error("Bạn không được đăng ký đồng thời dịch vụ 'Khóa cước Gphone' và 'Tạm dừng sử dụng'");
                    return false;
                }
                //Đồng thời khóa 1 chiều & tạm dừng
                if ((parseInt(dr["dichvugt_id"]) == DichVuGiaTang.KHOAMOTCHIEU_GPHONE && parseInt(dr1["dichvugt_id"]) == DichVuGiaTang.TAMDUNG_GPHONE && dr1["dangky"].toString().trim() == "1")
                    || (parseInt(dr["dichvugt_id"]) == DichVuGiaTang.TAMDUNG_GPHONE && parseInt(dr1["dichvugt_id"]) == DichVuGiaTang.KHOAMOTCHIEU_GPHONE && dr1["dangky"].toString().trim() == "1"))
                {
                    this.$toast.error("Bạn không được đăng ký đồng thời dịch vụ 'Khóa 1 chiều theo yêu cầu' và 'Tạm dừng sử dụng'");
                    return false;
                }
                //Nếu đã đăng ký tạm dừng, khóa cước, khóa gọi đi thì không cho phép đăng ký các dịch vụ khác
                if (parseInt(dr["dichvugt_id"]) == DichVuGiaTang.TAMDUNG_GPHONE && dr1["dichvugt_id"].toString().trim() != dr["dichvugt_id"].toString().trim() && dr1["dangky"].toString().trim() == "1")
                {
                    this.$toast.error("Thuê bao đã đăng ký dịch vụ 'Tạm dừng'\n Bạn không được đăng ký các dịch vụ khác!");
                    return false;
                }
                //Nếu đã đăng ký tạm dừng, khóa cước, khóa gọi đi thì không cho phép đăng ký các dịch vụ khác
                if (parseInt(dr["dichvugt_id"]) == DichVuGiaTang.KHOACUOC_GPHONE && dr1["dichvugt_id"].toString().trim() != dr["dichvugt_id"].toString().trim() && dr1["dangky"].toString().trim() == "1")
                {
                    this.$toast.error("Thuê bao đã đăng ký dịch vụ 'Khóa cước'\n Bạn không được đăng ký các dịch vụ khác!");
                    return false;
                }
                //Nếu đã đăng ký tạm dừng, khóa cước, khóa gọi đi thì không cho phép đăng ký các dịch vụ khác
                if (parseInt(dr["dichvugt_id"]) == DichVuGiaTang.KHOAMOTCHIEU_GPHONE && dr1["dichvugt_id"].toString().trim() != dr["dichvugt_id"].toString().trim() && dr1["dangky"].toString().trim() == "1")
                {
                    this.$toast.error("Thuê bao đã đăng ký dịch vụ 'Khóa gọi đi'\n Bạn không được đăng ký các dịch vụ khác!");
                    return false;
                }
              }
            }
          }
        }
      } else if (parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.CO_DINH)//Xét đối với cố định
      {
        for(let j=0;j<this.arrayItemDaDangKy.length;j++){
            let dr=this.arrayItemDaDangKy[j]
          //Kiểm tra nếu thuê bao đang khóa 1 chiều thì không cho đăng ký tạm dựng hoặc khóa cước
          //38: Khóa cước, 39: tạm dừng, 31: Khóa gọi đi
          if (dr["dangky"].toString().trim() == "1" &&
              (parseInt(dr["dichvugt_id"]) == DichVuGiaTang.KHOACUOC_CODINH || parseInt(dr["dichvugt_id"]) == DichVuGiaTang.TAMDUNG_CODINH
              || parseInt(dr["dichvugt_id"]) == DichVuGiaTang.KHOAGOIDI_CODINH))//Nếu đăng ký tạm dừng hoăc khóa cước hoặc khóa 1 chiều
            {
              for(let k=0;k<this.arrayItemDaDangKy.length;k++){
                let dr1=this.arrayItemDaDangKy[k]
                //nếu đang sử dụng khóa 1 chiều gọi đi
                if (dr1["sudung"] && dr1["sudung"].toString().trim().toLowerCase() == "sử dụng" && parseInt(dr1["dichvugt_id"]) == DichVuGiaTang.KHOAGOIDI_CODINH)
                {
                    this.$toast.error("Thuê bao " + this.soMayAcc +
                        " đang sử dụng dịch vụ khóa gọi đi! \nBạn không thể đăng ký thêm dịch vụ tạm dừng hoặc khóa cước!");
                    return false;
                }
                //Nếu chọn đồng thời khóa cước hoặc khóa chiều goi đi  thì return
                if ((parseInt(dr["dichvugt_id"]) == DichVuGiaTang.KHOACUOC_CODINH && parseInt(dr1["dichvugt_id"]) == DichVuGiaTang.KHOAGOIDI_CODINH && dr1["dangky"].toString().trim() == "1")
                      || (parseInt(dr["dichvugt_id"]) == DichVuGiaTang.KHOAGOIDI_CODINH && parseInt(dr1["dichvugt_id"]) == DichVuGiaTang.KHOACUOC_CODINH && dr1["dangky"].toString().trim() == "1"))
                {
                    this.$toast.error("Bạn không được đăng ký đồng thời dịch vụ 'Khóa chiều gọi đi' và 'Khóa cước'");
                    return false;
                }
                //Đồng thời khóa cước & tạm dừng
                if ((parseInt(dr["dichvugt_id"]) == DichVuGiaTang.KHOACUOC_CODINH && parseInt(dr1["dichvugt_id"]) == DichVuGiaTang.TAMDUNG_CODINH && dr1["dangky"].toString().trim() == "1")
                || (parseInt(dr["dichvugt_id"]) == DichVuGiaTang.TAMDUNG_CODINH && parseInt(dr1["dichvugt_id"]) == DichVuGiaTang.KHOACUOC_CODINH && dr1["dangky"].toString().trim() == "1"))
                {
                    this.$toast.error("Bạn không được đăng ký đồng thời dịch vụ 'Khóa cước' và 'Tạm dừng'");
                    return false;
                }
                //Đồng thời khóa  chiều gọi đi & tạm dừng
                if ((parseInt(dr["dichvugt_id"]) == DichVuGiaTang.TAMDUNG_CODINH && parseInt(dr1["dichvugt_id"]) == DichVuGiaTang.KHOAGOIDI_CODINH && dr1["dangky"].toString().trim() == "1")
                    || (parseInt(dr["dichvugt_id"]) == DichVuGiaTang.KHOAGOIDI_CODINH && parseInt(dr1["dichvugt_id"]) == DichVuGiaTang.TAMDUNG_CODINH && dr1["dangky"].toString().trim() == "1"))
                {
                    this.$toast.error("Bạn không được đăng ký đồng thời dịch vụ 'Khóa chiều gọi đi' và 'Tạm dừng theo yêu cầu'");
                    return false;
                }
                //Nếu đã đăng ký tạm dừng, khóa cước, khóa gọi đi thì không cho phép đăng ký các dịch vụ khác
                if (parseInt(dr["dichvugt_id"]) == DichVuGiaTang.TAMDUNG_CODINH && dr1["dichvugt_id"].toString().trim() != dr["dichvugt_id"].toString().trim() && dr1["dangky"].toString().trim() == "1")
                {
                    this.$toast.error("Thuê bao đã đăng ký dịch vụ 'Tạm dừng'\n Bạn không được đăng ký các dịch vụ khác!");
                    return false;
                }
                //Nếu đã đăng ký tạm dừng, khóa cước, khóa gọi đi thì không cho phép đăng ký các dịch vụ khác
                if (parseInt(dr["dichvugt_id"]) == DichVuGiaTang.KHOACUOC_CODINH && dr1["dichvugt_id"].toString().trim() != dr["dichvugt_id"].toString().trim() && dr1["dangky"].toString().trim() == "1")
                {
                    this.$toast.error("Thuê bao đã đăng ký dịch vụ 'Khóa cước'\n Bạn không được đăng ký các dịch vụ khác!");
                    return false;
                }
                //Nếu đã đăng ký tạm dừng, khóa cước, khóa gọi đi thì không cho phép đăng ký các dịch vụ khác
                if (parseInt(dr["dichvugt_id"]) == DichVuGiaTang.KHOAGOIDI_CODINH && dr1["dichvugt_id"].toString().trim() != dr["dichvugt_id"].toString().trim() && dr1["dangky"].toString().trim() == "1")
                {
                    this.$toast.error("Thuê bao đã đăng ký dịch vụ 'Khóa gọi đi'\n Bạn không được đăng ký các dịch vụ khác!");
                    return false;
                }
              }
            }
          }
      }
      else if (parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.ADSL)//Xét đối với internet ( ADSL, FTTH)
      {
        for(let j=0;j<this.arrayItemDaDangKy.length;j++){
          let dr=this.arrayItemDaDangKy[j]
          //Kiểm tra nếu thuê bao đang khóa 1 chiều thì không cho đăng ký tạm dựng hoặc khóa cước
          //38: Khóa cước, 39: tạm dừng, 31: Khóa gọi đi
          if (dr["dangky"].toString().trim() == "1" &&
              (parseInt(dr["dichvugt_id"]) == DichVuGiaTang.KHOACUOC_INTERNET
              || parseInt(dr["dichvugt_id"]) == DichVuGiaTang.TAMDUNG_INTERNET))//Nếu đăng ký tạm dừng hoăc khóa cước hoặc khóa 1 chiều
          {
            for(let k=0;k<this.arrayItemDaDangKy.length;k++){
              let dr1=this.arrayItemDaDangKy[k]
              //Đồng thời khóa cước & tạm dừng
              if ((parseInt(dr["dichvugt_id"]) == DichVuGiaTang.KHOACUOC_INTERNET
                  && parseInt(dr1["dichvugt_id"]) == DichVuGiaTang.TAMDUNG_INTERNET
                  && dr1["dangky"].toString().trim() == "1"))
              {
                  this.$toast.error("Bạn không được đăng ký đồng thời dịch vụ 'Khóa cước' và 'Tạm dừng'");
                  return false;
              }
              //Nếu đã đăng ký tạm dừng, khóa cước, khóa gọi đi thì không cho phép đăng ký các dịch vụ khác
              if (parseInt(dr["dichvugt_id"]) == DichVuGiaTang.TAMDUNG_INTERNET
                  && dr1["dichvugt_id"].toString().trim() != dr["dichvugt_id"].toString().trim()
                  && dr1["dangky"].toString().trim() == "1")
              {
                  this.$toast.error("Thuê bao đã đăng ký dịch vụ 'Tạm dừng'\n Bạn không được đăng ký các dịch vụ khác!");
                  return false;
              }
              //Nếu đã đăng ký tạm dừng, khóa cước, khóa gọi đi thì không cho phép đăng ký các dịch vụ khác
              if (parseInt(dr["dichvugt_id"]) == DichVuGiaTang.KHOACUOC_INTERNET
                  && dr1["dichvugt_id"].toString().trim() != dr["dichvugt_id"].toString().trim()
                  && dr1["dangky"].toString().trim() == "1")
              {
                  this.$toast.error("Thuê bao đã đăng ký dịch vụ 'Khóa cước'\n Bạn không được đăng ký các dịch vụ khác!");
                  return false;
              }
            }
          }
          //Lamnt: Zem khi chạy chính thức sẽ mở
          //Nếu là Fiber, Mega, đăng ký dịch vụ GreenOnline
          if ((this.selectedLoaiHinh == LOAIHINH_THUEBAO.INTERNET_FTTH || this.selectedLoaiHinh == LOAIHINH_THUEBAO.MEGA) && dr["dangky"].toString().trim() == "1")
          {
            try
            {
              //Kiem tra kieumega
              const kieu_mega = await api.fn_tt_tocdo_adsl({ param: this.tocdotn_id, type: 7 })//"kieu_mega", DatabaseConstants.DB2 + ".tocdo_adsl", "where tocdo_id = " + tocdotn_id);
              if (kieu_mega != "0")
              {
                  //comment theo ý Duy, dùng tocdo của One, ko dùng của visa
                  // const tocdo_id = await api.fn_tt_tocdo_adsl({ param: this.tocdotn_id, type: 4 })//checkdata.MAP_ID("matocdo", DatabaseConstants.DB2 + ".tocdo_adsl", "where tocdo_id =" + tocdotn_id);
                  // if (tocdo_id == "-1")
                  // {
                  //     this.$toast.error("Thông tin về tốc độ chưa đồng bộ với dữ liệu Visa. Bạn không thể tiếp tục đăng ký GreenNet !");
                  //     return false;
                  // }
                  //todo: gọi service Visa
                  //Gọi hàm kiểm tra tốc độ có được đăng ký greenonline hay không
                  // VISA_COMMONService visaCommon = new VISA_COMMONService(tt_nd.user_visa, tt_nd.pass_visa);
                  // if (parseInt(dr["dichvugt_id"]) == DichVuGiaTang.GREEN_ONLINE){
                  //   let kq = await api.checkGreenNet({ tocdo_id: parseInt(tocdo_id) });
                  //   if (!kq)
                  //   {
                  //       this.$toast.error("Tốc độ hiện tại của thuê bao không hỗ trợ đăng ký GreenNet !");
                  //       return false;
                  //   }
                  // } else
                  if (parseInt(dr["dichvugt_id"]) == 8939){//greenonline net : lephuong
                    var check_greennet = 0
                    await api.get_fn_ktra_tocdo_dk_greennet(this.axios,{params:{tocdo_id: Number(this.tocdotn_id)}}).then(async (response) => {
                      if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        check_greennet = Number(response.data.data)
                      } else check_greennet = 0
                    })
                    .catch(() => {
                      check_greennet = 0
                    })
                    if (check_greennet == 0 ) {
                      this.$toast.error("Tốc độ hiện tại của thuê bao không hỗ trợ đăng ký GreenNet !");
                      return false;
                    }
                  }
              }
            }
            catch (ex)
            {
                this.$toast.error("Có lỗi trong quá trình kiểm tra điều kiện đăng ký GreenNet !");
                return false;
            }
          }
        }
        if (this.selectedLoaiHinh == LOAIHINH_THUEBAO.INTERNET_MYTV)
        {
          for(let j=0;j<this.arrayItemDaDangKy.length;j++){
            let dr=this.arrayItemDaDangKy[j]
            if (dr["dangky"].toString().trim() == "1" &&
                (dr["ma_dvgt"].toString().trim() == DichVuGiaTang.CB_FF_12
                  || dr["ma_dvgt"].toString().trim() == DichVuGiaTang.CB_FF_24))
            {
                //
                // string strKiemTraDVGT = "select count(1) ";
                // strKiemTraDVGT += " from " + DatabaseConstants.DB2 + ".dangky_dvgt a, " + DatabaseConstants.DB2 + ".hd_thuebao b, " + DatabaseConstants.DB2 + ".dichvu_gt c";
                // strKiemTraDVGT += " where a.hdtb_id=b.hdtb_id and a.dichvugt_id=c.dichvugt_id";
                // strKiemTraDVGT += "    and b.thuebao_id=" + thuebao_id + " and b.tthd_id=6";
                // strKiemTraDVGT += "    and a.kieu_yc=0 and (c.ma_dvgt='" + DichVuGiaTang.CB_FF_12
                //     + "' or c.ma_dvgt='" + DichVuGiaTang.CB_FF_24 + "')";
                // DataSet ds = bangts.GetSQL(strKiemTraDVGT, "");
                // if (ds != null && ds.Tables[0].Rows.Count > 0)//Đã từng hủy Combo Fafilm --> ko đc đký lại
                // {
                //     Message_Box.ShowWarning("Thuê bao đã từng hủy khuyến mại 'Combo-Fafilm'\n Không thể đăng ký lại!");
                //     return false;
                // }
            }
          }
        }
      }
      return true;
    },
    async KiemTraDL_KhachHang(){
      // const kq_bc = await api.kiemtra_hd_bancheo_tc({"thuebao_id":this.thuebao_id,"loaihd_id":LOAI_HOPDONG.THAY_DOI_DV});
      // if (kq_bc != "OK") {
      //     this.$toast.error(kq_bc);
      //     return false
      // }
      if (this.tenKH.trim() == "")
      {
          this.$toast.error("Hãy nhập tên khách hàng !")
          this.$refs.tenKH.focus()
          return false
      }
      if (this.maKH.trim() == "" && this.dataInforMaGiaoDDich.khachhang_id != 0)
      {
          this.$toast.error("Kiểm tra lại mã khách hàng !")
          this.$refs.maKH.focus()
          return false
      }
      //Hiếu - kiểm tra xem giao dịch viên đã chọn 1 dịch vụ gia tăng nào chưa (Đăng ký hoăc hủy 1 dịch vụ nào đó)
      //nếu chưa thì return false luôn - 14.10.2010
      let kt_chon_DVGT = false;
      for (let i=0;i<this.arrayItemDaDangKy.length;i++)
      {
        if (this.arrayItemDaDangKy[i].dangky==1 ||this.arrayItemDaDangKy[i].huy== "1" ||this.arrayItemDaDangKy[i].sua== "1"||this.arrayItemDaDangKy[i].tanglen== "1"){
              kt_chon_DVGT = true
              break;
          }
      }
      if (kt_chon_DVGT == false)
      {
          this.$toast.error("Bạn chưa đăng ký/hủy dịch vụ gia tăng nào \n Không thể cập nhật thông tin");
          return false
      }
      //Hieutc: 11.10.2011: Kiểm tra mã khách hàng đã theo đúng chuẩn chưa
      if (this.maKH.trim() != "")
      {
          const kt_makh = await api.kiemtra_makh_matt_magd({maKh:this.maKH.trim(), kieu: 1, loaiHdId: LOAI_HOPDONG.THAY_DOI_DV})
          if (kt_makh == 0)
          {
              this.$toast.error("Mã khách hàng không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!");
              this.$refs.maKH.focus()
              return false
          }
      }
      if (this.maGiaoDich.trim() != "")
      {
        const kt_magd = await api.kiemtra_makh_matt_magd({maKh:this.maGiaoDich.trim(), kieu:3, loaiHdId: LOAI_HOPDONG.THAY_DOI_DV})
          if (kt_magd == 0)
          {
              this.$toast.error("Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!");
              this.$refs.maGiaoDich.focus()
              return false
          }
      }
      return true
    },
    async KiemTraDL_ThueBao() {
      if (this.maHopDong == "" && this.dataInforMaGiaoDDich.khachhang_id != 0)
      {
          this.$toast.error("Hãy nhập mã hợp đồng !")
          this.$refs.maHopDong.focus()
          return false
      }
      if (this.tenThueBao == "")
      {
          this.$toast.error("Hãy nhập tên thuê bao !")
          this.$refs.tenThueBao.focus()
          return false
      }
      if (this.selectedKieuYeuCau == 0 || this.selectedKieuYeuCau ==null)
      {
          this.$toast.error("Hãy chọn kiểu yêu cầu cho thuê bao này !")
          return false
      }
      let kt_ldh = true;
      //todo: for loop
      for (let i=0;i<this.arrayItemDaDangKy.length;i++){
        let row = this.arrayItemDaDangKy[i]
        if (row.dangky==1) {
          if (parseInt(this.selectedDichVu) === DICHVU_VIENTHONG.CO_DINH) {
            if (parseInt(row.dichvugt_id) === DichVuGiaTang.TAMDUNG_CODINH) {
              kt_ldh = false
              break
            }
          } else if (parseInt(this.selectedDichVu) === DICHVU_VIENTHONG.ADSL) {
            if (parseInt(row.dichvugt_id) === DichVuGiaTang.TAMDUNG_INTERNET) {
              if (this.selectedLoaiHinh ==LOAIHINH_THUEBAO.INTERNET_ADSL || this.selectedLoaiHinh ==LOAIHINH_THUEBAO.INTERNET_FTTH || this.selectedLoaiHinh ==LOAIHINH_THUEBAO.WIFI_FIBER){
                const kt_greenonline = await api.fn_kiemtra_dk_sd_greenonline({"thuebao_id":this.thuebao_id,"kieu_yc":1,"kieu":0})
                if(kt_greenonline){
                  for (let j=0;j<this.arrayItemDaDangKy.length;j++){
                    if(row.dichvugt_id==DichVuGiaTang.GREEN_ONLINE){
                      this.$toast.error("Thuê bao đang sử dụng dịch vụ Green Online. Bạn phải đăng ký hủy dịch vụ Green Online !")
                      return false
                    }
                  }
                }
              }
              kt_ldh = false
              break
            }
          } else if (parseInt(this.selectedDichVu) === DICHVU_VIENTHONG.GPHONE) {
              if (parseInt(row.dichvugt_id) ===DichVuGiaTang.TAMDUNG_GPHONE) {
                kt_ldh = false;
                break
              }
          } else if (parseInt(this.selectedDichVu) === DICHVU_VIENTHONG.MEGAWAN||parseInt(this.selectedDichVu) === 26) {
            if (this.kt_kenhphu_ao == true)
            {
                if (parseInt(this.selectedKieuYeuCau) != KIEU_LAPDAT.TAMDUNG_SD_KP_AO)
                {
                    this.$toast.error("Thuê bao " + this.soMayAcc+ " là kênh phụ ảo. Bạn phải chọn kiểu lắp đặt là 'Tạm dừng Kênh phụ ảo' !");
                    return false
                }
            }
            else
            {
                if (parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.TAMDUNG_SD_KP_AO)
                {
                    this.$toast.error("Bạn không được chọn kiểu lắp đặt 'Tạm dừng Kênh phụ ảo' cho thuê bao '" + this.soMayAcc + "' !");
                    return false
                }
            }
            if (parseInt(row.dichvugt_id) == DichVuGiaTang.TAMDUNG_MEGAWAN)
            {
                kt_ldh = false; break;
            }
          } else if (this.selectedDichVu.toString() === DICHVU_VIENTHONG.TSL.toString()||this.selectedDichVu.toString() === '25')
          {
              if (parseInt(row.dichvugt_id) == DichVuGiaTang.TAMDUNG_TSL)
              {
                  kt_ldh = false; break;
              }
          }
          else if (parseInt(this.selectedDichVu) === DICHVU_VIENTHONG.IMS) {
            if (parseInt(row.dichvugt_id) === DichVuGiaTang.TAMDUNG_IMS) {
              kt_ldh = false;
            }
          } else if (
            parseInt(this.selectedDichVu) === DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT ||
            parseInt(this.selectedDichVu) === DICHVU_VIENTHONG.DICHVU_CNTT ||
            parseInt(this.selectedDichVu) === DICHVU_VIENTHONG.TRUNGTAM_DULIEU ||
            parseInt(this.selectedDichVu) === DICHVU_VIENTHONG.HOINGHI_TRUYENHINH
          ) {
            if (parseInt(row.dichvugt_id) === DichVuGiaTang.TAMDUNG_CNTT) {
              if (this.kt_tao_hopdong_cntt)
              {
                  //todo: chờ api
                  // const kt_lhd = new LapHopDongFacade2().kt_tao_hopdong_cntt(LoaiHopDong.THAY_DOI_DV, thuebao_id);
                  // if (!kt_lhd)
                  // {
                  //     this.$toast.error("Bạn chỉ được tạm dừng thuê bao " + cboLoaihinhTB.Text + " tạo hợp đồng đặt mới trong tháng");
                  //     return false;
                  // }
              }
              kt_ldh = false;
              break
            }
          }
        }
      }
      if (this.dsDKHUY.length == 0 && kt_ldh == false)
      {
          this.$toast.error("Bạn chưa chọn lý do tạm dừng!");
          // xtraTabControl1.SelectedTabPageIndex = 0;
          // btnEditLyDoHuy.Focus();
          return false
      }
      // hoangpkn : 31/10/2013. Thêm hàm kiểm tra gói theo yc a Thắng
      let kq = await api.kiemtra_thaydoi({
          "thueBaoId": this.thuebao_id,
          "loaiHdId": LOAI_HOPDONG.THAY_DOI_DV
      })
      if (kq != "1")
      {
          this.$toast.error(kq);
          return false;
      }
      // end hoàng thêm
      if (this.selectedLoaiHinh == LOAIHINH_THUEBAO.INTERNET_FTTH || this.selectedLoaiHinh == LOAIHINH_THUEBAO.INTERNET_MYTV || this.selectedLoaiHinh == LOAIHINH_THUEBAO.INTERNET_MYTV_B2B)
      {
          if (this.doituong_id == DUNG_THU.DOITUONG_TB)
          {
              this.$toast.error("Không thể lập hợp đồng với thuê bao thuộc đối tượng \"Thuê bao dùng thử\"!");
              return false;
          }
      }
      return true;
    },
    // action nút thêm tb
    async tsbtnThemTB_Click() {
      let ck = await this.KiemTraDL_ThueBao() //đã thấy kiểm tra trong DB, nhưng ko dám chắc có trả về đúng text lỗi.
      if (!ck) return;
      ck = this.KT_DK_HUY_DV()
      if (!ck) return;
      let loaikenh_id = 0
      // const inputHuongTiepNhan = {
      //   loaiHdId: LOAI_HOPDONG.THAY_DOI_DV,
      //   loaiTbId: parseInt(this.selectedLoaiHinh),
      //   tocDoId: this.tocdotn_id,
      //   mucCuocId: this.muccuoctn_id,
      //   kieuLdId: this.selectedKieuYeuCau
      // };
      if (parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.TSL || parseInt(this.selectedDichVu) == 25) {
        let kq = await this.LAY_DS_DB_TSL_THEO_TBID(this.thuebao_id);
        if (kq &&kq.data && kq.data.length>0) {          
          loaikenh_id = kq.data[0].loaikenh_id
        }
      }
      if (parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.MEGAWAN || parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.METRONET  || parseInt(this.selectedDichVu) == 26)
      {
        let kq = await api.lay_ds_db_megawan_theo_tbid(this.thuebao_id);
        if (kq && kq.length>0) {          
          loaikenh_id = kq[0].loaikenh_id
        }          
      }
      const inputHuongTiepNhan = {
          dichvuvt_id: this.selectedDichVu,
          kieuld_id: this.selectedKieuYeuCau,
          loaihd_id: LOAI_HOPDONG.THAY_DOI_DV,
          loaikenh_id: loaikenh_id,
          loaitb_id: parseInt(this.selectedLoaiHinh),
          muccuoc_id: this.muccuoctn_id,
          phanvung_id: this.$root.token.getPhanVungID(),
          tocdo_id: this.tocdotn_id,
        }
      const dset = await this.LayHuongGiao_TiepNhan(
        inputHuongTiepNhan
      );
      let huonggiao_tn = 0;
      if (!dset || Object.keys(dset).length === 0)
      {
          this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!");
          return;
      }
      huonggiao_tn = parseInt(dset[0]["huonggiao_id"]);
      this.quytrinh_id = parseInt(dset[0]["quytrinh_id"]);
      //Hiếu - kiểm tra xem giao dịch viên đã chọn 1 dịch vụ gia tăng nào chưa (Đăng ký hoăc hủy 1 dịch vụ nào đó)
      //nếu chưa thì return false luôn - 14.10.2010
      let kt_chon_DVGT = false;
      for (let i=0;i<this.arrayItemDaDangKy.length;i++)
      {
        let dr = this.arrayItemDaDangKy[i]
        if ((dr["dangky"] && dr["dangky"].toString() == "1") || (dr["huy"] && dr["huy"].toString() == "1"))
        {
            kt_chon_DVGT = true;
            break;
        }
      }
      if (kt_chon_DVGT == false)
      {
          this.$toast.error("Bạn chưa đăng ký/hủy dịch vụ gia tăng nào \n Không thể cập nhật thông tin");
          return;
      }
      const kt_hdtb_themmoi = await api.kiemtra_hdtb_khi_themmoi({"ma_tb":this.soMayAcc, "hdkh_id":this.hdkh_id, "dichvuvt_id":this.selectedDichVu, "loaitb_id":this.selectedLoaiHinh})
      if (kt_hdtb_themmoi && parseInt(kt_hdtb_themmoi)>=1) {
          this.$toast.error("Thuê bao đã tồn tại trong hợp đồng, không thêm mới được ");
          return;
      }
      const kt_hdk = await api.kiemtra_thuebao_lap_hopdong_khac({
        dichVuVtId: this.selectedDichVu,
        loaiHdId: LOAI_HOPDONG.THAY_DOI_DV,
        maGd: "0",
        maTb: this.soMayAcc
      })
      if (kt_hdk && parseInt(kt_hdk)>=1) {
          return;
      }
      const kt_db_themmoi = await api.kiemtra_db_khi_themmoi({"maTb":this.soMayAcc, "khachHangId":this.khachhang_id, "dichVuVtId":this.selectedDichVu, "thueBaoId":this.thuebao_id})
      if (!kt_db_themmoi || parseInt(kt_db_themmoi)==0) {
          this.$toast.error("Thuê bao không tồn tại trong danh bạ, không thêm mới được ");
          return;
      }
      const kt_cung_mkh = await api.kiemtra_tb_cung_makh_tb_pl({"ma_gd":this.maGiaoDich,"thuebao_id":this.thuebao_id})
      if (!kt_cung_mkh || kt_cung_mkh!=1) {
          this.$toast.error("Thuê bao không cùng mã khách hàng với thuê bao đã lập hợp đồng !");
          return;
      }
      this.themMoi = 0;
      //this.themTB = true;
      this.hdtb_id = await this.getKeys({
        keyname: "HD_THUEBAO"
      });
      const vbdtb_id = await api.fn_tt_bd_thuebao({ param: this.hdtb_id, type: 1 })
      const lydoList = this.dsDKHUY.map(item => {
        const res = {
          STT: item.stt,
          LYDOHUY_ID: item.lydohuy_id,
          LYDOHUY: item.lydohuy,
          NOIDUNG: item.noidung,
          DANGKY: item.dangky,
          HUY: item.huy,
          STATUS: item.status,
          LYDOHUY_NEO_ID: item.lydohuy_neo_id,
          MA_LD: item.ma_ld,
          HDTB_ID:this.hdtb_id,
          NGAY_YC: this.ngayYeuCau,
          BDTB_ID: vbdtb_id,
          THUEBAO_ID: this.thuebao_id,
          NGAY_DK: this.current_time
        };
        return res;
      });
      try {
        const ds_isdn = this.TaoDuLieu_HDTB_ISDN(dset[0]);
        const dgvDVGT = this.TaoDuLieu_DVGT();
        const HDKH = await this.TaoDuLieu_HDKH();
        const HDTB = await this.TaoDuLieu_HDTB(dset[0]);
        const HDTB_DV = await this.TaoDuLieu_HDTBDV();
        const dangky_dvgt = this.TaoDuLieu_HDTDDV();
        const CTTHD = await this.TaoDuLieu_CT_TIENHD();
        const HDTB_CD = await this.TaoDuLieu_HDTB_CD();
        const HDTB_ADSL = await this.TaoDuLieu_HDTB_ADSL();
        const HDTB_IMS = await this.TaoDuLieu_HDTB_IMS();
        const HDTB_GP = await this.TaoDuLieu_HDTB_GP();
        const HDTB_MGWAN = await this.TaoDuLieu_HDTB_MGWAN();
        const HDTB_TSL = await this.TaoDuLieu_HDTB_TSL();
        const HDTB_DD = await this.TaoDuLieu_HDTB_DD();
        const HDTB_CNTT = await this.TaoDuLieu_HDTB_CNTT();
        const BD_LYDOHUY = await this.TaoDuLieu_LYDOHUY();
        const inputData = {
          kt: 0,
          thuebao_id: this.thuebao_id,
          loaitb_id: parseInt(this.selectedLoaiHinh),
          ma_tb: this.soMayAcc,
          js_dgvDVGT: dgvDVGT,
          loaihd_id: 7,
          dichvuvt_id: this.selectedDichVu,
          themmoi: 1,
          tocdo_id: 0,
          hdtb_id:this.hdtb_id,
          kieuld_id: this.selectedKieuYeuCau,
          muccuoc_id: 0,
          hdkh_id:this.hdkh_id,
          khachhang_id: this.khachhang_id,
          js_ds_isdn: ds_isdn,
          js_HDKH: HDKH,
          js_HDTB: HDTB,
          js_HDTB_DV: HDTB_DV,
          js_dangky_dvgt: dangky_dvgt,
          js_TTTT: [], // empty   // TaoDuLieu_THONGTIN_TT
          js_CTTHD: CTTHD,
          js_HDTB_CD: HDTB_CD,
          js_HDTB_ADSL: HDTB_ADSL,
          js_HDTB_IMS: HDTB_IMS,
          js_HDTB_GP: HDTB_GP,
          js_HDTB_MGW: HDTB_MGWAN,
          js_HDTB_TSL: HDTB_TSL,
          js_HDTB_DD: HDTB_DD,
          js_HDTB_CNTT: HDTB_CNTT,
          js_dangky_huy: lydoList,
          js_dangky_dvk: this.dsDVKhac,
          thuhoi: this.valueThuHoiThietBi?1:0,
          nhanvien_id: this.$root.token.getNhanVienID(),
          js_BD_LYDOHUY: BD_LYDOHUY ? BD_LYDOHUY : [],
          ma_gd: this.maGiaoDich,
          loaihinhtb: this.selectedLoaiHinh
        };
        console.log("input:", inputData);
        const inputData1 = JSON.stringify({
          kt: 0,
          thuebao_id: this.thuebao_id,
          loaitb_id: parseInt(this.selectedLoaiHinh),
          ma_nd: this.$auth.getUserName(),
          may_cn: 'OneBSS', //todo
          ip_cn: '1.1.1.1', //todo
          ma_tb: this.soMayAcc,
          js_dgvDVGT: JSON.stringify(dgvDVGT),
          loaihd_id: 7,
          dichvuvt_id: this.selectedDichVu,
          themmoi: this.themMoi,
          tocdo_id: 0,
          hdtb_id: this.hdtb_id,
          kieuld_id: this.selectedKieuYeuCau,
          isdn_sochinh: 0,
          muccuoc_id: 0,
          hdkh_id: this.hdkh_id,
          khachhang_id: this.khachhang_id,
          js_ds_isdn: JSON.stringify(ds_isdn),
          js_HDKH: JSON.stringify(HDKH),
          js_HDTB: JSON.stringify(HDTB),
          js_HDTB_DV: JSON.stringify(HDTB_DV),
          js_dangky_dvgt: JSON.stringify(dangky_dvgt),
          js_TTTT: JSON.stringify([]), // TaoDuLieu_THONGTIN_TT
          js_CTTHD: JSON.stringify(CTTHD),
          js_HDTB_CD: JSON.stringify(HDTB_CD),
          js_HDTB_ADSL: JSON.stringify(HDTB_ADSL),
          js_HDTB_IMS: JSON.stringify(HDTB_IMS),
          js_HDTB_GP: JSON.stringify(HDTB_GP),
          js_HDTB_MGW: JSON.stringify(HDTB_MGWAN),
          js_HDTB_TSL: JSON.stringify(HDTB_TSL),
          js_HDTB_DD: JSON.stringify(HDTB_DD),
          js_HDTB_CNTT: JSON.stringify(HDTB_CNTT),
          js_dangky_huy: JSON.stringify(lydoList),
          js_dangky_dvk: JSON.stringify(this.dsDVKhac),
          thuhoi: this.valueThuHoiThietBi?1:0,
          nhanvien_id: this.$root.token.getNhanVienID(),
          js_BD_LYDOHUY: JSON.stringify(BD_LYDOHUY ? BD_LYDOHUY : []),
          ma_gd: this.maGiaoDich,
          loaihinhtb: this.selectedLoaiHinh
        });
        this.$root.showLoading(true);
        const para = { ds_para: inputData1 };
        const res = await this.themMoiTB(para);
        const status = JSON.parse(res);
        if (status.MESSAGE === "Thành công") {
          this.$root.toastSuccess("Thêm mới thuê bao thành công!");
          this.disabledBtnThanhToan = false;
          this.disabledBtnNhapMoi = false;
          this.disabledBtnXoaHD = false;
          this.disabledBtnThanhToan = false;
          this.disabledBtnThemTB = false;
          this.themMoi = 0;
        } else {
          this.$toast.error(`${status.MESSAGE}`);
        }
      } catch (error) {
        this.$root.showLoading(false);
        console.log("ERRRORRRR:", error);
        this.$toast.error('Có lỗi khi thêm thuê bao vào hợp đồng!');
      }
      await this.HienThiDanhSacHDTB(this.hdkh_id);//todo: change hdkh_id
      this.$root.showLoading(false);
    },
    onUpdate (result){
      console.log(result)
      if(this.frmTuVanMyTVCom && this.$refs.dialogfrmTuVanMyTV)
        this.$refs.dialogfrmTuVanMyTV.hideDialog();
      this.CapNhat()
    },
    // action nút ghi lại  - chưa xong
    async tsbtnGhiLai_Click() {
      // bật form tư vấn nếu kiểu lắp đặt là mở tạm dừng internet, thay đổi dvgt internet
      if (parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.MOTAMDUNG_SD_MEGA || parseInt(this.selectedKieuYeuCau) == KIEU_LAPDAT.THAYDOI_DVGT_MEGA){
        let params = {
          hdkh_id:0,
          loai:1,
          khachhang_id:this.khachhang_id
        }
        var tuvan = await api.get_kiemtra_tuvan_mytv(this.axios, {params: params})
          .then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000')
              return response.data.data==1
            else return false
          })
          .catch(() => {
            return false
          });
          if(tuvan) {
            if((await this.$confirm('Khách hàng chưa sử dụng MyTV / WiFi Mesh / Camera, đề nghị Anh/Chị tư vấn mời khách hàng sử dụng dịch vụ MyTV / WiFi Mesh / Camera', 'Thông báo', {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không'
            }).then(async () => {
              this.frmTuVanMyTVCom = frmTuVanMyTV;
              await this.frmTuVanMyTVCom().then(x => {
              });
              this.$nextTick(() => {
                let data = {
                  khachhang_id: this.khachhang_id,
                  loai: 1
                }
                this.$refs.dialogfrmTuVanMyTV.openDialog(data);
              });
              return 'Yes'
            })) != 'Yes') {this.CapNhat()}//do nothing}
          }
          else {
            this.CapNhat()
          }
      }
      else {
        this.CapNhat()
      }
      //lbl1.Focus();
      //đã thấy kiểm tra trong SP --tuanlv
    },
    async CapNhat(){
      let kt = false;
      for (let i = 0; i < this.arrayItemDaDangKy.length; i++)
      {
          if (this.arrayItemDaDangKy[i].dangky == 1)
          {
              if (parseInt(this.arrayItemDaDangKy[i].trangthaitb_id) == TRANGTHAI_TB.TAMDUNG)
              {
                  kt = true;
                  break;
              }
          }
      }
      if (kt) // Nếu tạm dừng hoặc khóa 2 chiều thì mới thực hiện
      {
        if (this.selectedLoaiHinh === LOAIHINH_THUEBAO.INTERNET_MYTV) {
          const variables = { ma_tb: this.soMayAcc, kieu: 2 };
          const dtKtra = await this.Kiemtra_tb_mytv_chinhphu(variables);
          if (dtKtra.length > 0) {
            const dsTS = await this.LAY_DS_THAMSO_MD_MATS({
              ma_ts: "KHONG_KIEMTRA_MYTV_CHINHPHU"
            });
            let khong_ktra = -1;
            if (dsTS && dsTS.length > 0) {
              khong_ktra = parseInt(dsTS[0].ten_ts);
              if (khong_ktra === 1) {
                const cf = await this.$bvModal.msgBoxConfirm(`Thuê bao ${this.soMayAcc} đang là thuê bao MyTV chính của thuê bao: ${dtKtra[0].ma_tb_con}
                  Bạn có đồng ý tiếp tục? Nếu tiếp tục việc tạm dừng thuê bao chính sẽ tạm dừng cả thuê bao phụ đi cùng  `,
                  {
                    okTitle: "Có",
                    cancelTitle: "Không"
                  }
                );
                if (!cf) return;
              } else {
                this.$toast.error(`Thuê bao ${this.soMayAcc} đang là thuê bao MyTV chính của thuê bao khác. Yêu cầu hủy chính phụ trước khi thực hiện!`,);
                return
              }
            }
          }
        }
      }
      if (this.KT_DK_PAYTV()) return
      if (!this.KT_DK_D7()) {
        this.$toast.error(`Đăng ký nhóm trượt phải đăng ký kèm X9 hoặc X10`,);
        return
      }
      if (this.KT_KieuLD_TD() == 1) // Kiểu LD là tạm dừng
      {
          if (this.KT_DangKy_TD())
          {
            if (parseInt(this.selectedDichVu) != DICHVU_VIENTHONG.DI_DONG)
            {
                if (this.valueThuHoiThietBi == false)
                {
                  const cf = await this.$bvModal.msgBoxConfirm("Bạn chắc chắn không thu hồi thiết bị của khách hàng?",
                    {
                      okTitle: "Có",
                      cancelTitle: "Không"
                    }
                  );
                  if (!cf) return;
                }
            }
          }
      }
      let ck = await this.KiemTraDL_KhachHang()
      if (!ck) return;
      ck = await this.KiemTraDL_ThueBao() //đã thấy kiểm tra trong DB, nhưng ko dám chắc có trả về đúng text lỗi.
      if (!ck) return;
      ck = await this.KiemTraDangKyDichVu()
      if (!ck) return;
      //if (!KiemTraDichVuDaDK()) return; //todo
      ck = this.KT_DK_HUY_DV()
      if (!ck) return;
      if (this.themMoi === 1) {
        this.hdkh_id = await this.getKeys({
          keyname: "HD_KHACHHANG"
        });
        this.hdtb_id = await this.getKeys({
          keyname: "HD_THUEBAO"
        });
        this.maGiaoDich = await this.Lay_Ma_GD_V2({
          vdonvi_id: parseInt(this.$root.token.getDonViID()),
          vloaihd_id: 7
        });
      }
      const vbdtb_id = await api.fn_tt_bd_thuebao({ param: this.hdtb_id, type: 1 }) //await this.MAP_ID_HUY({in_dk: `where hdtb_id = ${hdtb_id}`,in_id_neo: "bdtb_id",in_table: "css.bd_thuebao"});
      const lydoList = this.dsDKHUY.map(item => {
        const res = {
          STT: item.stt,
          LYDOHUY_ID: item.lydohuy_id,
          LYDOHUY: item.lydohuy,
          NOIDUNG: item.noidung,
          DANGKY: item.dangky,
          HUY: item.huy,
          STATUS: item.status,
          LYDOHUY_NEO_ID: item.lydohuy_neo_id,
          MA_LD: item.ma_ld,
          HDTB_ID:this.hdtb_id,
          NGAY_YC: this.ngayYeuCau,
          BDTB_ID: vbdtb_id,
          THUEBAO_ID: this.thuebao_id,
          NGAY_DK: this.current_time
        };
        return res;
      });
      if (this.arrayItemDaDangKy.length>0) {
        try {
          let loaikenh_id = 0
          // const inputHuongTiepNhan = {
          //   loaiHdId: LOAI_HOPDONG.THAY_DOI_DV,
          //   loaiTbId: parseInt(this.selectedLoaiHinh),
          //   tocDoId: this.tocdotn_id,
          //   mucCuocId: this.muccuoctn_id,
          //   kieuLdId: this.selectedKieuYeuCau
          // };
          if (parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.TSL || parseInt(this.selectedDichVu) == 25) {
            let kq = await this.LAY_DS_DB_TSL_THEO_TBID(this.thuebao_id);
            if (kq &&kq.data && kq.data.length>0) {              
              loaikenh_id = kq.data[0].loaikenh_id
            }
          }
          if (parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.MEGAWAN|| parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.METRONET || parseInt(this.selectedDichVu) == 26)
          {
            let kq = await api.lay_ds_db_megawan_theo_tbid(this.thuebao_id);            
            if (kq && kq.length>0) {              
              loaikenh_id = kq[0].loaikenh_id
              
            }          
          }          
          const inputHuongTiepNhan = {
              dichvuvt_id: this.selectedDichVu,
              kieuld_id: this.selectedKieuYeuCau,
              loaihd_id: LOAI_HOPDONG.THAY_DOI_DV,
              loaikenh_id: loaikenh_id,
              loaitb_id: parseInt(this.selectedLoaiHinh),
              muccuoc_id: this.muccuoctn_id,
              phanvung_id: this.$root.token.getPhanVungID(),
              tocdo_id: this.tocdotn_id,
            }
          const dsHuongTiepNhan = await this.LayHuongGiao_TiepNhan(
            inputHuongTiepNhan
          );
          let huonggiao_tn = 0;
          if (!dsHuongTiepNhan || Object.keys(dsHuongTiepNhan).length === 0)
          {
              this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!");
              this.maGiaoDich =''
              this.hdtb_id=0
              this.hdkh_id=0
              return;
          }
          const ds_isdn = this.TaoDuLieu_HDTB_ISDN(dsHuongTiepNhan[0]);
          const dgvDVGT = this.TaoDuLieu_DVGT();
          const HDKH = await this.TaoDuLieu_HDKH();
          const HDTB = await this.TaoDuLieu_HDTB(dsHuongTiepNhan[0]);
          const HDTB_DV = await this.TaoDuLieu_HDTBDV();
          const dangky_dvgt = this.TaoDuLieu_HDTDDV();
          const CTTHD = await this.TaoDuLieu_CT_TIENHD();
          const HDTB_CD = await this.TaoDuLieu_HDTB_CD();
          const HDTB_ADSL = await this.TaoDuLieu_HDTB_ADSL();
          const HDTB_IMS = await this.TaoDuLieu_HDTB_IMS();
          const HDTB_GP = await this.TaoDuLieu_HDTB_GP();
          const HDTB_MGWAN = await this.TaoDuLieu_HDTB_MGWAN();
          const HDTB_TSL = await this.TaoDuLieu_HDTB_TSL();
          const HDTB_DD = await this.TaoDuLieu_HDTB_DD();
          const HDTB_CNTT = await this.TaoDuLieu_HDTB_CNTT();
          const BD_LYDOHUY = await this.TaoDuLieu_LYDOHUY();
          const inputData = {
            kt: this.themMoi === 1 ? 1 : 0,
            thuebao_id: this.thuebao_id,
            loaitb_id: parseInt(this.selectedLoaiHinh),
            ma_nd: this.$auth.getUserName(),
            may_cn: 'OneBSS', //todo,
            ip_cn: '1.1.1.1', //todo
            ma_tb: this.soMayAcc,
            js_dgvDVGT: dgvDVGT,
            loaihd_id: 7,
            dichvuvt_id: this.selectedDichVu,
            themmoi: this.themMoi,
            tocdo_id: 0,
            hdtb_id:this.hdtb_id,
            kieuld_id: this.selectedKieuYeuCau,
            isdn_sochinh: 0,
            muccuoc_id: 0,
            hdkh_id:this.hdkh_id,
            khachhang_id: this.khachhang_id,
            js_ds_isdn: ds_isdn,
            js_HDKH: HDKH,
            js_HDTB: HDTB,
            js_HDTB_DV: HDTB_DV,
            js_dangky_dvgt: dangky_dvgt,
            js_TTTT: [], // empty   // TaoDuLieu_THONGTIN_TT
            js_CTTHD: CTTHD,
            js_HDTB_CD: HDTB_CD,
            js_HDTB_ADSL: HDTB_ADSL,
            js_HDTB_IMS: HDTB_IMS,
            js_HDTB_GP: HDTB_GP,
            js_HDTB_MGW: HDTB_MGWAN,
            js_HDTB_TSL: HDTB_TSL,
            js_HDTB_DD: HDTB_DD,
            js_HDTB_CNTT: HDTB_CNTT,
            js_dangky_huy: lydoList,
            js_dangky_dvk: this.dsDVKhac,
            thuhoi: this.valueThuHoiThietBi?1:0,
            nhanvien_id: this.$root.token.getNhanVienID(),
            js_BD_LYDOHUY: BD_LYDOHUY ? BD_LYDOHUY : [],
            ma_gd: this.maGiaoDich,
            loaihinhtb: this.selectedLoaiHinh,
            ob_id:this.ob_id,
            loai_ob:this.loai_ob
          };
          console.log("input:", inputData);
          const inputData1 = JSON.stringify({            
            kt: this.themMoi === 1 ? 1 : 0,
            thuebao_id: this.thuebao_id,
            loaitb_id: parseInt(this.selectedLoaiHinh),
            ma_nd: this.$auth.getUserName(),
            may_cn: 'OneBSS', //todo,
            ip_cn: '1.1.1.1', //todo,
            ma_tb: this.soMayAcc,
            js_dgvDVGT: JSON.stringify(dgvDVGT),
            loaihd_id: 7,
            dichvuvt_id: this.selectedDichVu,
            themmoi: this.themMoi,
            tocdo_id: 0,
            hdtb_id:this.hdtb_id,
            kieuld_id: this.selectedKieuYeuCau,
            isdn_sochinh: 0,
            muccuoc_id: 0,
            hdkh_id:this.hdkh_id,
            khachhang_id: this.khachhang_id,
            js_ds_isdn: JSON.stringify(ds_isdn),
            js_HDKH: JSON.stringify(HDKH),
            js_HDTB: JSON.stringify(HDTB),
            js_HDTB_DV: JSON.stringify(HDTB_DV),
            js_dangky_dvgt: JSON.stringify(dangky_dvgt),
            js_TTTT: JSON.stringify([]), // TaoDuLieu_THONGTIN_TT
            js_CTTHD: JSON.stringify(CTTHD),
            js_HDTB_CD: JSON.stringify(HDTB_CD),
            js_HDTB_ADSL: JSON.stringify(HDTB_ADSL),
            js_HDTB_IMS: JSON.stringify(HDTB_IMS),
            js_HDTB_GP: JSON.stringify(HDTB_GP),
            js_HDTB_MGW: JSON.stringify(HDTB_MGWAN),
            js_HDTB_TSL: JSON.stringify(HDTB_TSL),
            js_HDTB_DD: JSON.stringify(HDTB_DD),
            js_HDTB_CNTT: JSON.stringify(HDTB_CNTT),
            js_dangky_huy: JSON.stringify(lydoList),
            js_dangky_dvk: JSON.stringify(this.dsDVKhac),
            thuhoi: this.valueThuHoiThietBi?1:0,
            nhanvien_id: this.$root.token.getNhanVienID(),
            js_BD_LYDOHUY: JSON.stringify(BD_LYDOHUY ? BD_LYDOHUY : []),
            ma_gd:this.maGiaoDich,
            loaihinhtb: this.selectedLoaiHinh,
            ob_id:this.ob_id,
            loai_ob:this.loai_ob
          });
          this.$root.showLoading(true);
          const para = { ds_para: inputData1 };
          const res = await this.ghiLaiHopDongTB(para);
          const status = JSON.parse(res);
          if (status.MESSAGE === "Thành công") {
            if (this.themMoi === 1) {
              this.$root.toastSuccess("Ghi lại thành công!");
            } else {
              this.$root.toastSuccess("Cập nhật thành công!");
            }
            this.disabledBtnThanhToan = false;
            this.disabledBtnNhapMoi = false;
            this.disabledBtnXoaHD = false;
            this.disabledBtnThanhToan = false;
            this.disabledBtnThemTB = false;
            this.themMoi = 0;
            this.setButton(3);                        
            
          } else {
            this.$root.showLoading(false);
            this.$toast.error(`${status.MESSAGE}`);
            this.maGiaoDich =''
            this.hdtb_id=0
            this.hdkh_id=0
            return
          }
        } catch (error) {
          this.$root.showLoading(false);
          console.log("ERRRORRRR:", error);
          this.$toast.error("Có lỗi khi cập nhật!");
          this.maGiaoDich =''
          this.hdtb_id=0
          this.hdkh_id=0
          return
        }
      } else {
        this.$root.showLoading(false);        
        this.$toast.error(
          "Bạn chưa đăng ký/hủy dịch vụ gia tăng nào. Không thể cập nhật thông tin!"
        );
        return
      }
      await this.HienThiDanhSacHDTB(this.hdkh_id);//todo: change hdkh_id
      this.$root.showLoading(false);
    },
    KT_DK_HUY_DV()
    {
      let kt = true;
      let mess = "";
      try
      {
        const kieuyc =  this.dataKieuYeuCauList.filter(
          item => item.kieuld_id == this.selectedKieuYeuCau
        );
        if (this.KT_KieuLD_TD() == 1) // Kiểu LD là tạm dừng
        {
            if (!this.KT_DangKy_TD())
            {
                if (kieuyc)
                  mess = "Kiểu yêu cầu là " + kieuyc[0].ten_kieuld + ", bạn chỉ được chọn Đăng ký tạm dừng!";
                kt = false;
            }
            else
            {
                if (this.ts_ngay_hethan_tamdung != 0)                 {
                    if (this.checkBoxNgayHetHan == false)
                    {
                        this.$toast.error("Bạn chưa chọn ngày hết hạn tạm dừng của khách hàng !");
                        return false;
                    }
                    else
                    {
                        if (this.ngayHetHan <= this.current_time)
                        {
                            this.$toast.error("Ngày hết hạn tạm dừng phải lớn hơn ngày hiện tại!");
                            return false;
                        }
                        const interval_day = Math.abs(Math.ceil((moment(this.ngayHetHan,'DD/MM/YYYY')- (new Date()))/ (1000 * 3600 * 24)));
                        if (interval_day > this.ts_ngay_hethan_tamdung)
                        {
                            this.$toast.error("Ngày hết hạn tạm dừng không được quá " + this.ts_ngay_hethan_tamdung.toString() + " ngày so với ngày hiện tại!");
                            return false;
                        }
                    }
                }
            }
          }
          else if (this.KT_KieuLD_TD() == 0)
          {
              if (!this.KT_Mo_TD())
              {
                  if (kieuyc)
                    mess = "Kiểu yêu cầu là" + kieuyc[0].ten_kieuld + ", bạn chỉ được chọn Hủy tạm dừng!";
                  kt = false;
              }
          }
          else
          {
              if (this.KT_Mo_TD() || this.KT_DangKy_TD())
              {
                  if (kieuyc)
                    mess = "Kiểu yêu cầu là " + kieuyc[0].ten_kieuld + ", bạn chỉ được chọn Đăng ký/Hủy dịch vụ gia tăng!";
                  kt = false;
              }
          }
          if (mess.length > 0)
              this.$toast.error(mess);
        }
        catch (ex)
        {
            this.$toast.error("Có lỗi " + ex);
        }
        return kt;
    },
    KT_DK_PAYTV(){
      if (this.selectedLoaiHinh == LOAIHINH_THUEBAO.INTERNET_MYTV)
      {
        const list = this.arrayItemDaDangKy.filter(
          item => item.dangky === 1 && item.ma_dvgt ==='PAYTV'
        );
        if (list.length>0){
          const list1 = list.filter(
            item.noidung!=null &&item.noidung!=""&& parseInt(item.noidung)>=3
          );
          if (list1.length==0) {
            this.$toast.error("Thông tin đăng ký PAYTV trọn gói 20.000 không hợp lệ!");
            return true
          }
        }
      }
      return false;
    },
    KT_DK_D7(){
      if (this.selectedDichVu == DICHVU_VIENTHONG.IMS)
      {
        const list_d7 = this.arrayItemDaDangKy.filter(
          item => item.dangky === 1 && item.ma_dvgt ==='D7'                
        );
        if (list_d7.length == 0){        
          return true        
        }
        const list = this.arrayItemDaDangKy.filter(
          item => item.dangky === 1 && (item.ma_dvgt ==='X9' || item.ma_dvgt ==='X10')                
        );  
        if (list.length>0){        
          return true        
        } 
        else {
          return false
        }
      }
      return true;
    },
    async HienThiDanhSacHDTB(hdkh_id) {
      try {
        let tongtienhd = 0
        let tongtientb = 0
        const res = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(
          hdkh_id
        );
        this.dsThueBao = res;
        if (res && res.length>0){
          for(let i=0;i<res.length;i++){
            if(res[i].hdtb_id==TRANGTHAI_HOPDONG.MOI){
              tongtientb = parseInt(res[i].tongtien);
              tongtienhd = tongtienhd + tongtientb;
            }
          }
        }
        if(res && res.length<=1){
          this.disabledBtnXoaTB = true
        } else this.disabledBtnXoaTB = false
        const ds = await api.lay_ds_hopdong_tb_theo_hdtb_id({in_hdtb_id: this.hdtb_id})
        if (ds)
          this.HienThiTTHopDongTB(ds)
        this.tongTien = formatCurrencyNoVND(tongtienhd)
      } catch (error) {
        console.log(error)
        this.$toast.error('Lỗi hiển thị danh sách hợp đồng thuê bao!');
      }
    },
    async HienThiTTHopDongTB(ds){
      if(!ds || ds.length==0) return
      const row=ds[0]
      this.hdtb_id = parseInt(row.hdtb_id)
      if (row.thuebao_id && row.thuebao_id!=0)
          this.thuebao_id = parseInt(row.thuebao_id)
      this.soMayAcc = row.ma_tb
      this.tenThueBao = row.ten_tb
      this.diaChiLD = row.diachi_ld
      this.diaChiTB = row.diachi_tb
      this.selectedDichVu = await api.fn_tt_loaihinh_tb({ param: row.loaitb_id, type: 2 })
      this.maHDTTKD = ""
      this.soAo = ""
      this.huongKetNoi = ""
      this.tocdotn_id = 0
      this.muccuoctn_id = 0
      this._sl_cha = 0
      let ds_con = []
      if (this.selectedDichVu == DICHVU_VIENTHONG.ADSL)
      {
          ds_con = await api.lay_ds_hdtb_adsl_theo_hdtb_id({"in_hdtb_id": row.hdtb_id})          
          if (ds_con && ds_con.length > 0) {
            this.soAo = ds_con[0].ma_lt;
            if(ds_con[0].tocdo_id){
              this.tocdotn_id = parseInt(ds_con[0].tocdo_id)
              if (this.selectedLoaiHinh!=LOAIHINH_THUEBAO.INTERNET_MYTV && this.selectedLoaiHinh!=LOAIHINH_THUEBAO.INTERNET_MYTV_B2B && this.selectedLoaiHinh!=LOAIHINH_THUEBAO.INTERNET_SGTV
                  && this.selectedLoaiHinh!=LOAIHINH_THUEBAO.HTVC_FIBERVNN_ANALOG && this.selectedLoaiHinh!=LOAIHINH_THUEBAO.HTVC_FIBERVNN_DIGITAL){
                    if (ds_con[0].muccuoc_id)
                      this.muccuoctn_id = parseInt(ds_con[0].muccuoc_id)
              } else this.muccuoctn_id = 0
            }
          }
      } else if (this.selectedDichVu == DICHVU_VIENTHONG.MEGAWAN)
      {
          this.kt_kenhphu_ao = false
          ds_con = await api.lay_ds_hdtb_mgwan_theo_hdtb_id({in_hdtb_id: row.hdtb_id});
          if (ds_con && ds_con.length > 0) {
              this.maHDTTKD = ds_con[0].ma_pl
              this.soAo = ds_con[0].ma_lt
              this.huongKetNoi = ds_con.huong_kn
              if (ds_con[0].tocdo_id)
                  this.tocdotn_id = parseInt(ds_con[0].tocdo_id)
              this.muccuoctn_id = 0;
              if (ds_con[0].kp_ao.toString() == "1")
                  this.kt_kenhphu_ao = true
              else
                  this.kt_kenhphu_ao = false
              this.maBSS = ds_con[0].ma_bss
              this.maERP = ds_con[0].id_erp
          }
      } else if (this.selectedDichVu == DICHVU_VIENTHONG.TSL)
      {
          ds_con = await api.lay_ds_hdtb_tsl_theo_hdtb_id({'in_hdtb_id':row.hdtb_id})
          if (ds_con && ds_con.length > 0) {
              this.maHDTTKD = ds_con[0].ma_pl
              let soao_dau = "";
              let soao_cuoi = "";
              for (let i = 0; i < ds_con.length; i++)
              {
                  if (parseInt(ds_con.daucuoi_id) == 1)//Nếu là điểm đầu
                      soao_dau = ds_con[i]["ma_lt"] + ",";
                  else
                      soao_cuoi = ds_con[i]["ma_lt"];
              }
              this.soAo = soao_dau + soao_cuoi
              if (ds_con.tocdo_id)
                  this.tocdotn_id = parseInt(ds_con[0].tocdo_id)
              this.muccuoctn_id = 0;
              this.maBSS = ds_con[0].ma_bss
              this.maERP = ds_con[0].id_erp
          }
      }else if (this.selectedDichVu == DICHVU_VIENTHONG.TRUNGTAM_DULIEU || this.selectedDichVu == DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT
                    || this.selectedDichVu == DICHVU_VIENTHONG.DICHVU_CNTT || this.selectedDichVu == DICHVU_VIENTHONG.HOINGHI_TRUYENHINH) //Dich vu CNTT
      {
          this.Init_Properties()
          ds_con = await api.lay_ds_hdtb_cntt_theo_hdtb_id({
            in_hdtb_id: row.hdtb_id,
          })
          if (ds_con && ds_con.length > 0) {
              if (ds_con.linhvuc_id)
                  this.linhvuc_id = parseInt(ds_con[0].linhvuc_id)
              else
                  this.linhvuc_id = 0
              this.maHDTTKD = ds_con[0].ma_pl
          }
          else
              this.linhvuc_id = 0
          this.tocdotn_id = 0
          this.muccuoctn_id = 0
          if (parseInt(this.selectedLoaiHinh) == LOAIHINH_THUEBAO.IOFFICE)
          {
              if (ds_con.tongso_nd)
                  this._sl_cha = parseInt(ds_con[0].tongso_nd)
              else
                  this._sl_cha = 0
          }
          if (parseInt(this.selectedLoaiHinh) == LOAIHINH_THUEBAO.HNTH_NGN
              || parseInt(this.selectedLoaiHinh) == LOAIHINH_THUEBAO.VNPT_MEETING)
          {
              if (ds_con.sl_mailing)
                  this._sl_cha = parseInt(ds_con[0].sl_mailing)
              else
                  this._sl_cha = 0
          }
      }
      const variables = {
          maTb: this.soMayAcc,
          dichVuVtId: this.selectedDichVu,
          donViDlId: null
        };
      const dstrangthai = await this.LAY_DANHBA_THEO_MATB(variables);
      if (dstrangthai)
          this.trangThaiTB = dstrangthai["trangthai_tb"]
      else
          this.trangThaiTB = ""
      this.selectedLoaiHinh = parseInt(row.loaitb_id)
      this.loaitb_id = parseInt(row.loaitb_id)
      this.loaisochinh = 0; //
      if (row.hdtb_cha_id)
      {
        this.hdtb_id_cha = parseInt(row.hdtb_cha_id)
        this.loaisochinh = 1
        this.isdn_sochinh = false
      }
      if (this.selectedLoaiHinh == LOAIHINH_THUEBAO.PABX)
          this.LAY_DS_ISDN_THEO_THUEBAO_ID(row.thuebao_id)
      this.selectedKieuYeuCau = row.kieuld_id
      this.doituong_id = parseInt(row.doituong_id)
      this.donvi_id = parseInt(row.donvi_id)
      this.ghiChu = row.ghichu
      if (row.ngay_hh_td ==null || row.ngay_hh_td == "")
      {
          this.chkNgay_HHTD.Checked = false
          this.checkBoxNgayHetHan = false
          this.ngayHetHan = this.current_time
          this.dtpNgayHH_TD.Enabled = false
      }
      else
      {
          this.chkNgay_HHTD.Enabled = true
          this.chkNgay_HHTD.Checked = false
          this.checkBoxNgayHetHan = false
          this.ngayHetHan = moment(row.ngay_hh_td,'YYYY/MM/DD').format("DD-MMM-YYYY") //todo check lại
          this.dtpNgayHH_TD.Enabled = false
      }
      if (row.ngay_td && row.ngay_td !=''){
        this.ngayTD = moment(row.ngay_td,'DD/MM/YYYY HH24:mm:ss').format("DD/MM/YYYY")
      }
      await this.napThongTinDichVuThueBao(row)
      ///TuanNA: thêm code lấy thông tin lý do hủy --Ngày 01.06.2012
      await this.LAY_DS_LYDOHUY();
      ///HienThiDS_LyDoHuy(DataSet ds) //ok
      //
      await this.HienThiTTDiaChi(this.thuebao_id, 3)
      this.ds_tien_khoanmuc = await api.sp_Lay_tienhd_km_theo_hdtb({
          vhdtb_id: this.hdtb_id,
      });
      if (this.ds_tien_khoanmuc) {
        for (let i = 0; i < this.ds_tien_khoanmuc.length; i++){
          if (this.ds_tien_khoanmuc[i]["khoanmuctt_id"].toString() == KHOANMUC_TT.KMTT_TDDICHVU.toString())
          {
              this.dtienld = parseInt(this.ds_tien_khoanmuc[i]["tien"].toString())
              this.dvatld = parseInt(this.ds_tien_khoanmuc[i]["vat"].toString())
          }
          if (this.ds_tien_khoanmuc[i]["khoanmuctt_id"].toString() == KHOANMUC_TT.KMTT_DICHVU.toString())
          {
              this.dtien_dv = parseInt(this.ds_tien_khoanmuc[i]["tien"].toString())
              this.dvat_dv = parseInt(this.ds_tien_khoanmuc[i]["vat"].toString())
          }
        }
      }
      this.LayTongTien_HDTB()
      const ds_ttphieu = await api.lay_thongtin_phieu_tn({
          'phanvung_id': this.$auth.getPhanVungID(),
          'hdtb_id': this.hdtb_id
      });
      if (ds_ttphieu && ds_ttphieu.length > 0)
      {
          if (ds_ttphieu[0].kqxl_id && ds_ttphieu[0]["kqxl_id"].toString() == "3")
          {
              this.$refs.danhsachdichvugiatang.thuHoi = true
              this.$refs.danhsachdichvugiatang.thuHoiEnabled = true
          }
          else
              this.$refs.danhsachdichvugiatang.thuHoi = false
      }
      else
          this.$refs.danhsachdichvugiatang.thuHoi = false
      this.HT_TongTien()
      this.dsDVKhac = await api.LAY_DS_DANGKY_DVK({
          "hd_id": this.hdtb_id,
          "loaiid": 1
      })
      //TODO: CHECK LẠI THAM SỐ, chờ khảo sát api
            // string json_list = Newtonsoft.Json.JsonConvert.SerializeObject(dsDK_DVK.Tables[0]);
            // var dset = tchopdong.GET_DATA_SQL(@"select a.DVKHAC_ID,b.TEN_DVKHAC,decode(a.KIEU_YC,1,'Đăng ký','Huỷ') TRANGTHAI from json_table
            //                                     (:vds,'$[*]'
            //                                     columns DVKHAC_ID number PATH '$.DVKHAC_ID',
            //                                             KIEU_YC number PATH '$.KIEU_YC'
            //                                             ) a,
            //                                         {?DB2}.dichvu_khac b
            //                                 where a.dvkhac_id=b.dvkhac_id
            //                                      ", "vds", json_list);
            // gridDichVuKhac.DataSource = dset;
            // gridview_DichVuKhac.BestFitColumns();
    },
    async HienThiTTDiaChi(thuebao_id,kieu){
      const ds = await api.lay_ds_diachi_theo_dbtbid({in_thuebao_id: thuebao_id})
        if (ds && ds.length > 0)
        {
            if (kieu == 3)
            {
                if (ds[0].phuongld_id && ds[0].phuongld_id!="")
                    this.phuongld_id = parseInt(ds[0]["phuongld_id"])
                if (ds[0].phold_id && ds[0].phold_id!="")
                    this.phold_id = parseInt(ds[0]["phold_id"])
                if (ds[0].apld_id && ds[0].apld_id!="")
                    this.apld_id = parseInt(ds[0]["apld_id"])
                if (ds[0].khuld_id && ds[0].khuld_id!="")
                    this.khuld_id = parseInt(ds[0]["khuld_id"])
            }
        }
    },
    async LAY_DS_ISDN_THEO_THUEBAO_ID(vthuebao_id){
      try
      {
          this.isdn_sochinh = false;
          this.matb_isdn_chinh = "";
          this.ds_isdn =  await api.lay_ds_isdn_theo_thuebao_id(vthuebao_id)
          if (!this.ds_isdn) return
          for (let i = 0; i < this.ds_isdn.length; i++){
              if (parseInt(this.ds_isdn[i]["thuebao_id"]) == vthuebao_id)
              {
                  if (this.ds_isdn[i]["thuebao_cha_id"] == "")
                      this.isdn_sochinh = true
              }
              if (this.ds_isdn[i]["thuebao_cha_id"] == "")
              {
                  this.matb_isdn_chinh = ds_isdn[i]["ma_tb"]
              }
          }
      }
      catch (ex)
      {
          console.log(ex)
      }
    },
    // private void lvwThueBao_SelectedIndexChanged()
    //     {
    //         try
    //         {
    //             if (lvwThueBao.SelectedItems.Count > 0)
    //             {
    //                 hdtb_id = Convert.ToInt32(lvwThueBao.SelectedItems[0].SubItems[0].Text);
    //                 HienThiTTHopDongTB(tchopdong.LAY_DS_HOPDONG_TB_THEO_HDTB_ID(hdtb_id));
    //             }
    //         }
    //         catch (Exception ex)
    //         {
    //             new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, ex.Message, ex), true);
    //         }
    //     }
    async Init_Properties(){
      this.ItemList = await api.lay_danhsach_thuoctinh_v2({
        loaitb_id: this.selectedLoaiHinh,
        ten_bang: "HDTB_CNTT",
        opt_list: "00000",
      })
    },
    async loadDataDichVuGTByThueBao() {
      const variables = {
        dichVuVtId: this.selectedDichVu ? this.selectedDichVu : 0,
        donViDlId: 0,
        loaiHdId: 7,
        loaiTbId: this.selectedLoaiHinh ? this.selectedLoaiHinh : 0,
        maTb: this.soMayAcc,
        ttHdId: 1
      };
      try {
        await this.clickSoMaAcc(variables);
        this.napThongTinDichVuThueBao(this.dsSoMayAcc[0]);
      } catch (error) {
        this.$toast.error(error);
      }
    },
    TaoDuLieu_DVGT() {
      let temp = [...this.arrayItemDaDangKy]
      for (let i=0;i<temp.length;i++){
        temp[i].ten_dvgt = ''
      }
      return temp
      // return [
      //   {
      //     dichvugt_id: parseInt(this.dataGhiLai.dichvugt_id),
      //     ma_dvgt: this.dataGhiLai.ma_dvgt,
      //     ten_dvgt: this.dataGhiLai.ten_dvgt,
      //     tien: parseInt(this.dataGhiLai.tien.replace(/,/g, "")),
      //     trangthaitb_id: this.dataGhiLai.trangthaitb_id,
      //     dangky: this.dataGhiLai.dangky === true ? 1 : 0,
      //     huy: this.dataGhiLai.huy === true ? 1 : 0,
      //     sua: this.dataGhiLai.sua === true ? 1 : 0,
      //     noidung: this.dataGhiLai.noidung,
      //     cuoc_sd: parseInt(this.dataGhiLai.cuoc_sd.replace(/,/g, "")),
      //     vat_ld: parseInt(this.dataGhiLai.vat_ld.replace(/,/g, "")),
      //     ghichu: this.dataGhiLai.ghichu,
      //     sl_cha: parseInt(this.dataGhiLai.sl_cha.replace(/,/g, "")),
      //     block_tiep: parseInt(this.dataGhiLai.block_tiep.replace(/,/g, "")),
      //     gia_block_tiep: parseInt(
      //       this.dataGhiLai.gia_block_tiep.replace(/,/g, "")
      //     ),
      //     vat_block_tiep: parseInt(
      //       this.dataGhiLai.vat_block_tiep.replace(/,/g, "")
      //     )
      //   }
      // ];
    },
    // Tao du lieu cho bang HD_KHACHHANG
    async TaoDuLieu_HDKH() {
      let obj = {
        HDKH_ID:this.hdkh_id,
        MA_GD:this.maGiaoDich,
        MA_HD: this.maHopDong,
        MA_KH: this.maKH,
        KHACHHANG_ID: this.khachhang_id,
        TEN_KH: this.tenKH,
        DIACHI_KH: this.diaChiKH,
        NGAYLAP_HD: this.ngayLapHD,
        NGAY_YC: this.ngayYeuCau,
        DONVI_ID: parseInt(this.$root.token.getDonViID()),
        NHANVIEN_ID: this.$root.token.getNhanVienID(),
        LOAIHD_ID: LOAI_HOPDONG.THAY_DOI_DV,
        NGUOI_CN: this.$root.token.getUserName(),
        NGAY_CN: this.$auth.getNgayCapNhat(),
        MAY_CN: "OneBSS",
        HD_KHACHHANG: this.hdkh_id,
        MA_DUAN: this.maDuAn
      };
      if (this.dataInforMaGiaoDDich.hdtb_cha_id > 0) {
        obj = { HDKH_CHA_ID: this.dataInforMaGiaoDDich.hdtb_cha_id, ...obj };
      }
      if (this.hdkh_id > 0) {
        const variables = {
          in_id_neo: "khlon_id",
          in_table: "css.db_khachhang",
          in_dk: `where khachhang_id = ${
            this.hdkh_id
          }`
        };
        const khl_id = await this.mapIDDuLieuHDKH(variables);
        if (khl_id != "" && khl_id != "-1") {
          obj = { KHLON_ID: parseInt(khl_id), ...obj };
        } else {
          obj = { KHLON_ID: 1, ...obj };
        }
      } else {
        obj = { KHLON_ID: 1, ...obj };
      }
      if (this.checkBoxCTV) {
        obj = { CTV_ID: this.ctv_id, ...obj };
      }
      if (this.checkBoxNguoiGT) {
        obj = { NHANVIENGT_ID: this.nguoigt_id, ...obj };
      }
      return [obj];
    },
    // Tao du lieu cho bang HD_THUEBAO
    async TaoDuLieu_HDTB(dsHuongTiepNhan) {
      let obj = {
        HDTB_ID: this.hdtb_id,
        HDKH_ID: this.hdkh_id,
        MA_TB: this.soMayAcc,
        TEN_TB: this.tenThueBao,
        DIACHI_TB: this.diaChiTB,
        DIACHI_LD: this.diaChiLD,
        GHICHU: this.ghiChu,
        KIEULD_ID: this.selectedKieuYeuCau,
        DOITUONG_ID: this.doituong_id,
        TTHD_ID: TRANGTHAI_HOPDONG.MOI,
        LOAITB_ID: parseInt(this.selectedLoaiHinh),
        DICHVUVT_ID: parseInt(this.selectedDichVu),
        DONVI_ID: this.donvi_id,
        QUYTRINH_ID: dsHuongTiepNhan ? dsHuongTiepNhan.quytrinh_id : 0,
        NGUOI_CN: this.$root.token.getUserName(),
        MAY_CN: "OneBSS",
        HD_THUEBAO: this.hdtb_id,
      };
      if (this.checkBoxNgayHetHan){
        obj.NGAY_HH_TD = this.ngayHetHan;        
      }
      obj.NGAY_TD = this.ngayTD
      if (this.thuebao_id > 0) {
        obj = { THUEBAO_ID: this.thuebao_id, ...obj };
      }
      if (parseInt(this.selectedDichVu) === DICHVU_VIENTHONG.DI_DONG) {
        obj = { NGAY_HT: this.ngayYeuCau, ...obj };
      } else {
        obj = {
          NGAY_CN: this.ngayYeuCau //todo
            ? moment(this.ngayYeuCau,'DD/MM/YYYY HH24:mm:ss').format("DD/MM/YYYY")
            : this.current_time,
          ...obj
        };
      }
      if (
        parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.ADSL ||
        parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.IMS ||
        parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.CO_DINH ||
        parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.DI_DONG ||
        parseInt(this.selectedDichVu) == DICHVU_VIENTHONG.GPHONE
      )
        obj = { STATUS: TRANGTHAI_DONGBO.CHUADONGBO, ...obj };
      //todo
      const kttt = await api.kt_thaotac(dsHuongTiepNhan ? dsHuongTiepNhan.luong_id:0, 'STATUS_TONGDAI_CD')
      if (kttt=="1") obj.STATUS='0'
      // if (bangts.KT_ThaoTac(luong_id, "STATUS_TONGDAI_CD"))
      //           rowHDTB.STATUS = "0";
      if (parseInt(this.selectedLoaiHinh) === LOAIHINH_THUEBAO.PABX) {
        if (this.hdtb_id_cha > 0)
          obj = { HDTB_CHA_ID: this.hdtb_id_cha, ...obj };
      }
      return [obj];
    },
    // Tao du lieu cho bang HD_THUEBAO_ISDN
    TaoDuLieu_HDTB_ISDN(dsHuongTiepNhan) {
      let obj = {
        HDTB_ID: this.hdtb_id,
        HDKH_ID: this.hdkh_id,
        MA_TB: this.soMayAcc,
        TEN_TB: this.tenThueBao,
        DIACHI_TB: this.diaChiTB,
        DIACHI_LD: this.diaChiLD,
        GHICHU: this.ghiChu,
        KIEULD_ID: this.selectedKieuYeuCau,
        DOITUONG_ID: this.doituong_id,
        TTHD_ID: TRANGTHAI_HOPDONG.MOI,
        LOAITB_ID: parseInt(this.selectedLoaiHinh),
        DICHVUVT_ID: parseInt(this.selectedDichVu),
        DONVI_ID: this.donvi_id,
        QUYTRINH_ID: dsHuongTiepNhan ? dsHuongTiepNhan.quytrinh_id : 0,
        NGUOI_CN: this.$root.token.getUserName(),
        NGAY_CN: this.$auth.getNgayCapNhat(),
        MAY_CN: "OneBSS", //todo
        HD_THUEBAO: this.hdtb_id
      };
      if (this.ds_isdn && this.ds_isdn.length>0 && this.ds_isdn[0].thuebao_id && this.ds_isdn[0].thuebao_id > 0) {
        obj = { THUEBAO_ID: this.ds_isdn[0].thuebao_id, ...obj };
      }
      if (this.thuebao_id > 0) {
        obj = { THUEBAO_CHA_ID: this.thuebao_id, ...obj };
      }
      if (
        parseInt(this.selectedDichVu) === DICHVU_VIENTHONG.ADSL ||
        parseInt(this.selectedDichVu) === DICHVU_VIENTHONG.IMS
      )
        obj = { STATUS: TRANGTHAI_DONGBO.CHUADONGBO, ...obj };
      return [obj];
    },
    // TaoDuLieu_HDTBDV
    async TaoDuLieu_HDTBDV() {
      try {
        const dtTemp = await api.lay_ds_hdtb_dv({
          thuebao_id: this.thuebao_id
        });
        if (dtTemp && dtTemp.length) {
          let list = dtTemp.map(item => {
            const res = {
              LOAIDV_ID: item.loaidv_id,
              DONVI_ID: item.donvi_id,
              KIEUDV_ID: item.kieudv_id,
              HDTB_ID: this.hdtb_id
              // HDTB_ID: "535174619",
            };
            return res;
          });
          return list;
        } else {
          return [];
        }
      } catch (error) {
        return []
        throw error;
      }
    },
    // Tao du lieu cho bang DANGKY_DVGT
    TaoDuLieu_HDTDDV() {
      let hdtb = 0;
      let ds=[]
      for (let i=0;i<this.arrayItemDaDangKy.length;i++){
        const dr = this.arrayItemDaDangKy[i]
        let obj = {
          HDTB_ID: this.hdtb_id,
          NGAY_YC: this.ngayYeuCau,
          DICHVUGT_ID: parseInt(dr.dichvugt_id),
          NOIDUNG: dr.noidung,
          TTTH: 1,
          CUOC_SD: parseInt(dr.cuoc_sd),
          VAT_SD: dr.vat_sd
            ? parseInt(dr.vat_sd.toString().replace(/,/g, ""))
            : 0,
          TIEN: dr.tien
            ? parseInt(dr.tien.toString().replace(/,/g, ""))
            : 0
        };
        let kieu_dk_dvgt = "";
        if (dr.dangky && dr.dangky.toString()=="1") {
          kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_THEM_MOI_DVGT;
        } else if (dr.huy && dr.huy.toString()=="1")
          kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_HUY_DVGT;
        else if (dr.sua && dr.sua.toString()=="1")
          kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_SUA_DVGT;
        else if (dr.tanglen && dr.tanglen.toString()=="1") //todo: check lại biến tăng lên trên grid
          kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_MUATHEM;
        else continue
        obj = { KIEU_YC: kieu_dk_dvgt, ...obj };
        if (
          parseInt(dr.sl_cha) !== 0 &&
          (this.selectedLoaiHinh === LOAIHINH_THUEBAO.IOFFICE ||
            this.selectedLoaiHinh === LOAIHINH_THUEBAO.HNTH_NGN ||
            this.selectedLoaiHinh === LOAIHINH_THUEBAO.VNPT_MEETING ||
            this.selectedLoaiHinh === LOAIHINH_THUEBAO.VNPT_PORTAL)
        ) {
          if (parseInt(dr.block_tiep) !== 0) {
            obj = { SL_CHA: parseInt(dr.sl_cha), ...obj };
          } else {
            obj = { SL_CHA: 0, ...obj };
          }
          obj = { BLOCK_DAU: parseInt(dr.block_dau), ...obj };
          obj = { BLOCK_TIEP: parseInt(dr.block_tiep), ...obj };
          obj = {
            GIA_BLOCK_TIEP: parseInt(dr.gia_block_tiep),
            ...obj
          };
          obj = {
            VAT_BLOCK_TIEP: parseInt(dr.vat_block_tiep),
            ...obj
          };
          obj = { HE_SO: parseInt(dr.he_so), ...obj };
        }
        ds.push(obj)
      }
      return ds;
    },
    // Tạo dữ liệu cho bảng CT_TIENHD
    async TaoDuLieu_CT_TIENHD() {
	  console.log('TaoDuLieu_CT_TIENHD')
	  console.log(this.ds_tien_khoanmuc)


      let obj = {};
      let dsCTTHD = []
      let rowCTTHD = {}
      if (!this.ds_tien_khoanmuc) return []
      for (let i = 0; i < this.ds_tien_khoanmuc.length; i++)
      {
          if (this.ds_tien_khoanmuc[i]["khoanmuctt_id"].toString() == KHOANMUC_TT.KMTT_DICHVU.toString())
          {
              for (let j = 0; j < this.arrayItemDaDangKy.length; j++)
              {
                  if (this.arrayItemDaDangKy[j]["dangky"].toString() == "1"
                      && this.arrayItemDaDangKy[j]["huy"].toString() == "0")
                  {                      
                      rowCTTHD = {}
                      rowCTTHD.HDTB_ID = this.hdtb_id
                      // if (this.themMoi === 1){
                        let id = await this.getKeys({
                          keyname: "CT_TIENHD"
                        });
                        rowCTTHD.CT_TIENHD_ID = id;
                      //}
                      rowCTTHD.KHOANMUCTT_ID = KHOANMUC_TT.KMTT_DICHVU;
                      rowCTTHD.TIEN = this.arrayItemDaDangKy[j].cuoc_ld ?
                                      parseInt(this.arrayItemDaDangKy[j].cuoc_ld.toString().replace(/,/g, ""))
                                      : 0
                      rowCTTHD.VAT = this.arrayItemDaDangKy[j].vat_ld
                                    ? parseInt(this.arrayItemDaDangKy[j].vat_ld.toString().replace(/,/g, ""))
                                    : 0
					            rowCTTHD.TYLE_VAT_ID = this.ds_tien_khoanmuc[i].tyle_vat_id
                                    ? parseInt(this.ds_tien_khoanmuc[i].tyle_vat_id.toString().replace(/,/g, ""))
                                    : 0
					            rowCTTHD.TYLE_VAT = this.ds_tien_khoanmuc[i].tyle_vat
                                    ? parseInt(this.ds_tien_khoanmuc[i].tyle_vat.toString().replace(/,/g, ""))
                                    : 0
                      rowCTTHD.ID = parseInt(this.arrayItemDaDangKy[j]["dichvugt_id"].toString())
                      rowCTTHD.LOAI_ID = 2
                      dsCTTHD.push(rowCTTHD)
                  }
              }
          }
          else
          {
              rowCTTHD = {}
              rowCTTHD.HDTB_ID = this.hdtb_id
              //if (this.themMoi === 1){
                let id = await this.getKeys({
                  keyname: "CT_TIENHD"
                });
                rowCTTHD.CT_TIENHD_ID = id;
              //}
              rowCTTHD.KHOANMUCTT_ID = parseInt(this.ds_tien_khoanmuc[i]["khoanmuctt_id"].toString());
              rowCTTHD.TIEN = parseInt(this.ds_tien_khoanmuc[i]["tien"].toString());
              rowCTTHD.VAT = parseInt(this.ds_tien_khoanmuc[i]["vat"].toString());
              rowCTTHD.TYLE_VAT_ID = this.ds_tien_khoanmuc[i].tyle_vat_id
                                    ? parseInt(this.ds_tien_khoanmuc[i].tyle_vat_id.toString().replace(/,/g, ""))
                                    : 0
					    rowCTTHD.TYLE_VAT = this.ds_tien_khoanmuc[i].tyle_vat
                                    ? parseInt(this.ds_tien_khoanmuc[i].tyle_vat.toString().replace(/,/g, ""))
                                    : 0
              rowCTTHD.ID = parseInt(this.ds_tien_khoanmuc[i]["khoanmuctt_id"].toString());
              
              rowCTTHD.LOAI_ID = 1;
              dsCTTHD.push(rowCTTHD)
          }
      }
      return dsCTTHD
    },
    //Tao du lieu cho bang HDTB_CD
    async TaoDuLieu_HDTB_CD() {
      let hdtb = 0;
      if (parseInt(this.selectedDichVu) != DICHVU_VIENTHONG.CO_DINH) return []
      try {
        const res = await api.lay_ds_db_cd_theo_tbid(this.thuebao_id);
        if (res && res.length) {
          const list = res.map(item => {
            const arr = {
              PHANVUNG_ID: item.phanvung_id,
              HDTB_ID: this.hdtb_id,
              NE: item.ne,
              CULY: item.culy,
              GHICHU: item.ghichu,
              MATB_TN: item.matb_tn,
              MADOICAP: item.madoicap,
              MA_LT: item.ma_lt,
              TRANGBI_ID: item.trangbi_id,
              NE_ID: item.ne_id,
              TRAMTB_ID: item.tramtb_id,
              NAP_ID: item.nap_id,
              TUYENCAP: item.tuyencap,
              LIENTU: item.lientu,
              DOICAP_P: item.doicap_p,
              HOPCAP_P: item.hopcap_p,
              TUCAP_P: item.tucap_p,
              LOAICAP_ID: item.loaicap_id,
              CAP_GOC: item.cap_goc,
              CAP_P: item.cap_p,
              DOICAP_GOC: item.doicap_goc,
              TUCAP_GOC: item.tucap_goc,
              TG_THUESO: item.tg_thueso,
              SOTRUOT: item.sotruot,
              NOIDUNG_TB: item.noidung_tb,
              CAP_ID: item.cap_id,
              KETCUOI_ID: item.ketcuoi_id,
              DOICAP: item.doicap,
              VITRI: item.vitri,
              DOICAP_2: item.doicap_2,
              VITRI_2: item.vitri_2,
              CHUQUAN_ID: item.chuquan_id,
              CONGNGHE_ID: item.congnghe_id,
              THOIHAN_ID: item.thoihan_id,
              TOANHA_ID: item.toanha_id,
              SO_TAT: item.so_tat,
              CHUQUANLD_ID: item.chuquan_id,
              CUOC_DOITAC: item.cuoc_doitac,
              DOITAC_ID: item.doitac_id,
              TINHKHAC: item.tinhkhac,
              MA_VNPT: item.ma_vnpt
            };
            return arr;
          });
          return list;
        } else {
          return [];
        }
      } catch (error) {
        console.log(error)
        throw error;
      }
    },
    // Tao du lieu cho bang HDTB_ADSL
    async TaoDuLieu_HDTB_ADSL() {
      if (parseInt(this.selectedDichVu) != DICHVU_VIENTHONG.ADSL && parseInt(this.selectedDichVu) != DICHVU_VIENTHONG.MEGA_EYES) return []
      try {
        const res = await this.LAY_DS_DB_ADSL_THEO_TBID(
          this.thuebao_id
        );
        if (res && res.length) {
          const list = res.map(item => {
            const obj = Object.keys(item).reduce(
              (a, b) => {
                a[b.toUpperCase()] = item[b];
                return a;
              },
              {
                HDTB_ID:this.hdtb_id
                // HDTB_ID: "535174619",
              }
            );
            if (Object.keys(obj).length === 0) {
              return [];
            } else {
              return obj;
            }
          });
          if (list.length > 1) {
            return [list[0]]
          }
          else return list;
        } else {
          return [];
        }
      } catch (error) {
        throw error;
      }
    },
    // Tao du lieu cho bang TaoDuLieu_HDTB_IMS
    async TaoDuLieu_HDTB_IMS() {
      if (parseInt(this.selectedDichVu) != DICHVU_VIENTHONG.IMS) return []
      try {
        const res = await this.LAY_DS_DB_IMS_THEO_TBID(
          this.thuebao_id
        );
        if (res && res.length) {
          const list = res.map(item => {
            const obj = Object.keys(item).reduce(
              (a, b) => {
                a[b.toUpperCase()] = item[b];
                return a;
              },
              {
                HDTB_ID:this.hdtb_id
                // HDTB_ID: "535174619",
              }
            );
            return obj;
          });
          return list;
        } else {
          return [];
        }
      } catch (error) {
        throw error;
      }
    },
    // Tao du lieu cho bang
    async TaoDuLieu_HDTB_GP() {
      if (parseInt(this.selectedDichVu) != DICHVU_VIENTHONG.GPHONE) return []
      try {
        const res = await this.LAY_DS_DB_GP_THEO_TBID(
          this.thuebao_id
        );
        if (res && res.length) {
          const list = res.map(item => {
            const obj = Object.keys(item).reduce(
              (a, b) => {
                a[b.toUpperCase()] = item[b];
                return a;
              },
              {
                HDTB_ID:this.hdtb_id
                // HDTB_ID: "535174619",
              }
            );
            return obj;
          });
          return list;
        } else {
          return [];
        }
      } catch (error) {
        throw error;
      }
    },
    // Tao du lieu cho bang HDTB_MGWAN
    async TaoDuLieu_HDTB_MGWAN() {
      if (parseInt(this.selectedDichVu) != DICHVU_VIENTHONG.MEGAWAN && parseInt(this.selectedDichVu) != DICHVU_VIENTHONG.METRONET && parseInt(this.selectedDichVu) != 26) return []
      try {
        const res = await api.lay_ds_danhba_megawan(
          {thuebaoId:this.thuebao_id}
        );
        if (res && res.length) {
          const list = res.map(item => {
            const obj = Object.keys(item).reduce(
              (a, b) => {
                a[b.toUpperCase()] = item[b];
                return a;
              },
              {
                HDTB_ID:this.hdtb_id
                // HDTB_ID: "535174619",
              }
            );
            return obj;
          });
          return list;
        } else {
          return [];
        }
      } catch (error) {
        throw error;
      }
    },
    //TaoDuLieu_HDTB_TSL
    async TaoDuLieu_HDTB_TSL() {
      let res = [];
      let obj = {};
      let dsHDTB_TSL=[]
      if (parseInt(this.selectedDichVu) != DICHVU_VIENTHONG.TSL && parseInt(this.selectedDichVu) != 25) return []
      let kq = await this.LAY_DS_DB_TSL_THEO_TBID(this.thuebao_id);
      if (kq &&kq.data && kq.data.length>0) {
        res = kq.data
        for (let i=0;i<res.length;i++){
          obj = {
          HDTB_ID: this.hdtb_id,
          DAUCUOI_ID: res[i].daucuoi_id,
          MA_TB_SUB: res[i].ma_tb_sub,
          DIACHI: res[i].diachi,
          CHUQUAN_ID: res[i].chuquan_id,
          TRANGBI_ID: res[i].trangbi_id,
          TOCDO_ID: res[i].tocdo_id,
          LOAIKENH_ID: res[i].loaikenh_id,
          MA_PL: res[i].ma_pl,
          MA_LT: res[i].ma_lt,
          MA_BSS: res[i].ma_bss,
          ID_ERP: res[i].id_erp
        };
        dsHDTB_TSL.push(obj)
        }
      }
      else return []
      return dsHDTB_TSL;
    },
    //TaoDuLieu_HDTB_DD - chưa có api
    async TaoDuLieu_HDTB_DD() {
      if (parseInt(this.selectedDichVu) != DICHVU_VIENTHONG.DI_DONG) return []
      try {
        // let rowHDTBD_DD = {};
        // let data =  await api.lay_ds_db_dd_theo_tbid( {"ID": this.thuebao_id});
        // if(data && data.length>0){
        //   rowHDTBD_DD = data[0];
        //   rowHDTBD_DD.HDTB_ID = this.hdtb_id;
        //   return [rowHDTBD_DD]
        // }
        // else return []
        let res =  await api.lay_ds_db_dd_theo_tbid( {"ID": this.thuebao_id});
        if (res && res.length) {
          const list = res.map(item => {
            const obj = Object.keys(item).reduce(
              (a, b) => {
                a[b.toUpperCase()] = item[b];
                return a;
              },
              {
                HDTB_ID:this.hdtb_id                
              }
            );
            return obj;
          });
          return list;
        } else {
          return [];
        }
      }
      catch(ex){
        console.log(ex)
        throw ex;
      }
    },
    // TaoDuLieu_HDTB_CNTT
    async TaoDuLieu_HDTB_CNTT() {
      if (parseInt(this.selectedDichVu) != DICHVU_VIENTHONG.TRUNGTAM_DULIEU && parseInt(this.selectedDichVu) != DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT
                    && parseInt(this.selectedDichVu) != DICHVU_VIENTHONG.DICHVU_CNTT && parseInt(this.selectedDichVu) != DICHVU_VIENTHONG.HOINGHI_TRUYENHINH) {
        return []
      }
      try {
        const res = await this.LAY_DS_DB_CNTT_THEO_TBID(
          this.thuebao_id
        );
        if (res && res.length) {
          const list = res.map(item => {
            const obj = Object.keys(item).reduce(
              (a, b) => {
                a[b.toUpperCase()] = item[b];
                return a;
              },
              {
                HDTB_ID:this.hdtb_id
              }
            );
            return obj;
          });
          return list;
        } else {
          return [];
        }
      } catch (error) {
        this.$toast.error(error);
      }
    },
    // TaoDuLieu_BD_LYDOHUY
    async TaoDuLieu_BD_LYDOHUY() {
      try {
        const res = await this.LAY_DS_DB_LYDOHUY_THEO_TBID(
          this.thuebao_id
        );
        if (res && res.length) {
          const list = res.map(item => {
            const obj = Object.keys(item).reduce((a, b) => {
              a[b.toUpperCase()] = item[b];
              return a;
            }, {});
            return obj;
          });
          return list;
        } else {
          return [
            {
              HDTB_ID: this.hdtb_id,
              LYDOHUY_ID: this.dsDKHUY[0].lydohuy_id
            }
          ];
        }
      } catch (error) {
        throw error;
      }
    },
    // TaoDuLieu_LYDOHUY
    async TaoDuLieu_LYDOHUY() {
      try {
        const vbdtb_id = await api.fn_tt_bd_thuebao({ param: this.hdtb_id, type: 1 }) //this.MAP_ID_HUY({in_dk: `where hdtb_id = ${hdtb_id}`,in_id_neo: "bdtb_id",in_table: "css.bd_thuebao"});
        if (this.dsDKHUY && this.dsDKHUY.length) {
          let lsTmp = [];
          for (let i=0;i<this.dsDKHUY.length;i++) {
              let obj = {
              HDTB_ID: this.hdtb_id,
              LYDOHUY_ID: this.dsDKHUY[i].lydohuy_id,
              NOIDUNG: this.dsDKHUY[i].lydohuy_id,
              NGAY_YC: this.ngayYeuCau,
              PHANVUNG_ID: this.$root.token.getPhanVungID(),
              BDTB_ID: vbdtb_id,
              THUEBAO_ID: this.thuebao_id,
              NGAY_DK: this.current_time
            }
            obj.HDTB_ID = this.hdtb_id
            lsTmp.push(obj);
          }
          return lsTmp;
        }
      } catch (error) {
        console.log("EORORR:", error);
        return [];
      }
    },
    // action nút thanh toán
    async tsbtnThanhToan_Click() {
      let kq = await api.kt_donvi_quytrinh_cntt(this.hdtb_id);
      if(kq.data.data != "ok")
      {
        this.$toast.error("Đơn vị chưa đúng, bạn ấn nút 'Cập nhật ĐV' để cập nhật lại!");
        return;
      }
	  let user_ipcc = ''
      if (this.$route.query.user_ipcc && this.$route.query.user_ipcc != '') {
        user_ipcc = this.$route.query.user_ipcc
      }
      this.$router.push({
        name: "Payment",
        params: { magd: this.maGiaoDich, user_ipcc: user_ipcc }
      });
    },
    //action nút xóa Hợp đồng
    async tsbtnXoa_Click() {
      if ( this.hdkh_id == 0) return
      const cf = await this.$bvModal.msgBoxConfirm(`Bạn thật sự muốn xóa hợp đồng không ?`,{okTitle: "Có",cancelTitle: "Không"});
      if (!cf) return;
      try
      {
          let ds_tb = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id);
          if (!ds_tb){
            this.$toast.error("Không tìm thấy thông tin hợp đồng trong CSDL !");
            return;
          }
          let list = ds_tb.filter(
            item => item.tthd_id.toString() != "1"
          );
          if (list.length > 0)
          {
              this.$toast.error("Hợp đồng đã có phiếu giao đi. Bạn không thể xóa hợp đồng !");
              return;
          }
          list = ds_tb.filter(
            item => item.status && item.status.toString() == "5"
          );
          if (list.length > 0)
          {
              this.$toast.error("Hợp đồng đã có phiếu kích hoạt visa. Bạn không thể xóa hợp đồng !");
              return;
          }
          this.$root.showLoading(true);
          const variables = {
            hdkh_id: this.hdkh_id,
            loaihd_id: 7
          };
          const status = await this.deleteHD(variables);
          if (status === "Success")
            this.$toast.success("Xóa hợp đồng thành công!");
          this.setButton(0)
          this.clearData()
          this.$root.showLoading(false);
      }
      catch (exp)
      {
          console.log(exp)
          this.$toast.error("Xóa hợp đồng bị lỗi!");
          this.$root.showLoading(false);
      }
      // if ( this.hdkh_id > 0) {
      //   const variables = {
      //     hdkh_id: this.hdkh_id,
      //     loaihd_id: 7
      //   };
      //   this.$root.showLoading(true);
      //   try {
      //     const status = await this.deleteHD(variables);
      //     if (status === "Success")
      //       this.$toast.success("Xóa hợp đồng thành công!");
      //     this.setButton(2);
      //   } catch (error) {
      //     this.$toast.error("Xóa hợp đồng bị lỗi!");
      //     this.$root.showLoading(false);
      //   }
      //   this.$root.showLoading(false);
      // }
    },
    // xóa thuê bao
    async tsbtnXoaTB_Click() {
      if (this.soMayAcc) {
        const cf = await this.$bvModal.msgBoxConfirm(`Bạn thật sự muốn xóa thuê bao không ?`,{okTitle: "Có",cancelTitle: "Không"});
        if (!cf) return;
        this.$root.showLoading(true);
        //this.setButton(1);
        try {
          const res = await this.xoaHopDongTB({
            hdtb_id: this.hdtb_id
          });
          if (res === "Success") {
            this.$toast.success("Xóa thuê bao khỏi hợp đồng thành công!");
            const variables = {
              in_donvi_dl_id: 0,
              in_donvi_id: parseInt(this.$root.token.getDonViID()),
              in_loaihd_id: 7,
              in_ma_gd: this.maGiaoDich,
              in_nhanvien_id: this.$root.token.getNhanVienID(),
              in_tthd_id: 1
            };
            this.$root.showLoading(false);
            const ds = await api.lay_ds_hopdong_theo_ma_gd(variables)
            if (ds)
              this.HienThiTTHopDongKH(ds)
          }
          this.$root.showLoading(false);
        } catch (err) {
          this.$toast.error(err);
          this.$root.showLoading(false);
        }
      }
    },
    // hẹn thông tin kh
    tsbtnHen_Click() {
      if (this.hdkh_id==0) {
        this.$toast.error("Chưa có thông tin hợp đồng!");
        return
      }
      //this.$refs.henKH.showModal()
      //this.statusPopupHenKH = true;
      this.$refs.popupThongTinHenKhachHang.showModal()
    },
    updateStatusPopup(data) {
      this.statusPopupHenKH = data;
    },
    async setButton(num) {
      this.disabledBtnNhapMoi = true;
      this.disabledBtnGhiLai = true;
      this.disabledBtnXoaHD = true;
      this.disabledBtnHuy = true;
      this.disabledBtnThemTB = true
      this.disabledBtnXoaTB = true;
      this.disabledBtnThanhToan = true;
      //btnPhieuYC.Enabled = false;
      this.disabledBtnInPL = true
      if (num == -1)//Bat dau
      {
          $("#soMayAcc").focus();
          this.disabledBtnGhiLai = false;
          this.disabledBtnHuy = false;
          this.checkBoxCTV = false
          this.checkBoxNguoiGT = false
          this.inputCTV = ""
          this.inputNguoiGT = ""
      }
      if (num == 0)//Bat dau
      {
          $("#soMayAcc").focus();
          this.disabledBtnNhapMoi = false;
          await this.clearData();
      }
      // nhập mới
      if (num === 1) {
        $("#soMayAcc").focus();
        this.disabledBtnGhiLai = false;
        this.disabledBtnHuy = false;
        this.checkBoxCTV = false;
        this.checkBoxNguoiGT = false;
        this.inputCTV = "";
        this.inputNguoiGT = "";
        this.themMoi = 1;
        await this.clearData();
      }
      if (num === 2) {
        $("#maGiaoDich").focus();
        this.disabledBtnNhapMoi = false;
        this.disabledBtnXoaHD = false;
        await this.clearData();
      }
      if (num === 3) {
        //Edit
        $("#maGiaoDich").focus();
        this.disabledBtnNhapMoi = false;
        this.disabledBtnXoaHD = false;
        this.disabledBtnGhiLai = false;
        this.disabledBtnHuy = false;
        this.disabledBtnThemTB = false
        this.disabledBtnThanhToan = false;
        //btnPhieuYC.Enabled = false;
        this.disabledBtnInPL = false
      }
      // khi tồn tại mã giao dịch or số máy acc
      // if (
      //   num === 4 &&
      //   ((this.soMayAcc &&
      //     this.dataInforSoMayAcc &&
      //     Object.keys(this.dataInforSoMayAcc).length > 0) ||
      //     (this.maGiaoDich &&
      //       this.dataInforMaGiaoDDich &&
      //       Object.keys(this.dataInforMaGiaoDDich).length > 0))
      // ) {
      //   // this.disabledBtnXoaTB = false;
      //   // this.disabledBtnThanhToan = false;
      //   // this.disabledBtnGhiLai = false;
      // }
    },
    // clear data
    async clearData() {
      let current_time = moment()
        .format("DD-MM-YYYY")
        .toString();
      this.dsThueBao = [];
      this.hdtbId = 0;
      this.maHopDong = "";
      this.maGiaoDich = "";
      this.ngayYeuCau = current_time;
      this.ngayLapHD = current_time;
      this.selectedDichVu = 1;
      this.selectedKieuYeuCau = 23;
      this.selectedLoaiHinh = 1;
      this.loaitb_id=1;
      this.maHDTTKD = "";
      this.soMayAcc = "";
      this.huongKetNoi = "";
      this.tenThueBao = "";
      this.diaChiTB = "";
      this.soAo = "";
      this.trangThaiTB = "";
      this.maERP = "";
      this.maBSS = "";
      this.diaChiLD = "";
      this.diaChiLDDau = "";
      this.diaChiLDCuoi = "";
      this.ghiChu = "";
      this.maKH = "";
      this.checkBoxNgayHetHan = false;
      this.ngayHetHan = current_time;
      this.tenKH = "";
      this.diaChiKH = "";
      this.tongTienKhoanMuc = 0;
      this.dsDKHUY = [];
      this.dsDVKhac = [];
      this.nguoigt_id = 0;
      this.ctv_id = 0;
      this._sl_cha = 0;
      this.hdtb_id_cha = 0; //hdtb_id_cha
      this.trangbi_id = 3;
      this.phuongld_id = 0;
      this.phold_id = 0;
      this.apld_id = 0;
      this.khuld_id = 0;
      await this.napThongTinDichVuThueBao(null);
      this.hdkh_id = 0;
      this.khachhang_id = 0;
      this.hdtb_id = 0;
      this.thuebao_id = 0;
      this.tien_dv = 0;
      this.vat_dv = 0;
      this.HienThiTienDichVu()
      //this.LayTienTheoKhoanMuc() todo
      this.doituong_id = 1;
      this.donvi_id=0;
      this.maDuAn = '';
    },
    // data khi click 1 dòng trong danh sách dịch vụ gia tăng
    recordClickDichVuTang(data) {
    },
    async dsThueBao_click(data) {
      if (!data) return
      this.hdtb_id=data.hdtb_id
      const ds = await api.lay_ds_hopdong_tb_theo_hdtb_id({in_hdtb_id: this.hdtb_id})
      if (ds) {
        await this.HienThiTTHopDongTB(ds)
        await this.napThongTinDichVuThueBao(ds[0])
      }
    },
    // data nhận từ emit khi tìm kiếm số máy acc
    onSelectCustomer(data) {
      this.soMayAcc = data.MA_TB;
      this.txtMaTB_KeyPress();
    },
    /* chọn data từ danh sách thuê bao */
    recordClick(data) {},
    padLeft(num, size) {
        num = num.toString();
        while (num.length < size) num = "0" + num;
        return num;
    },
    Unformat_tien(s){
        s = s.toLowerCase().trim();
        let ketqua = "";
        for (let i = 0; i < s.length; i++) {
            if (String(s[i]) != "," && String(s[i]) != "." && String(s[i]) != " ") {
                ketqua = ketqua + s[i];
            }
        }
        return ketqua.trim();
    },
    CheckIsNumber: function (input) {
      return !isNaN(input);
    },
    getDaysDiff: function (date_1, date_2){
      let difference = date_1.getTime() - date_2.getTime();
      let TotalDays = Math.ceil(difference / (1000 * 3600 * 24));
      return Math.abs( TotalDays );
    },
    changeKeysToUpper: function (obj) {
      var key, upKey;
      for (key in obj) {
          if (obj.hasOwnProperty(key)) {
              upKey = key.toUpperCase();
              if (upKey !== key) {
                  obj[upKey] = obj[key];
                  delete(obj[key]);
              }
              // recurse
              if (typeof obj[upKey] === "object") {
                  this.changeKeysToUpper(obj[upKey]);
              }
          }
      }
      return obj;
    },
  },
  watch: {
    // thay đổi dịch vụ => thay đổi cả loại hình tb
    async selectedDichVu(val) {
      this.loaiHinhListByFilter = [];
      const list = this.loaiHinhList.filter(
        item => item.DICHVUVT_ID.toString() === this.selectedDichVu.toString()
      );
      if (list.length) {
        const status = this.checkDichVuId(this.selectedDichVu);
        if (status) {
          this.filterLoaiHinhByDichVuID(this.selectedDichVu);
        } else {
          this.selectedLoaiHinh = list[0].LOAITB_ID;
        }
      } else {
        this.selectedLoaiHinh = null;
      }
      this.loaiHinhListByFilter = list;
    },
    // thay đổi loại hình => thay đổi kiểu yêu cầu
    async selectedLoaiHinh(val) {
      if (val) {
        const variables = {
          vkieu: 0,
          vloaihd_id: 7,
          vloaitb_id: val
        };
        await this.fetchKieuYeuCauListLD_BC(variables);
        this.dataKieuYeuCauList = this.kieuYeuCauListLD_BC;
        if (this.dataKieuYeuCauList && this.dataKieuYeuCauList.length>0) {
          this.dataKieuYeuCauList = this.dataKieuYeuCauList.filter(item=>item.tinhkhac==0)
        }
        if (this.dataKieuYeuCauList && this.dataKieuYeuCauList.length) {
          this.selectedKieuYeuCau = this.dataKieuYeuCauList[0].kieuld_id;
        } else {
          this.selectedKieuYeuCau = 0;
        }
      } else {
        await this.fetchYeuCauList();
        this.dataKieuYeuCauList = this.mappingDataKieuYeuCau(
          this.kieuYeuCauList
        );
        if (this.dataKieuYeuCauList && this.dataKieuYeuCauList.length>0) {
          this.dataKieuYeuCauList = this.dataKieuYeuCauList.filter(item=>item.tinhkhac==0)
        }
        if (this.dataKieuYeuCauList && this.dataKieuYeuCauList.length) {
          this.selectedKieuYeuCau = this.dataKieuYeuCauList[0].kieuld_id;
        } else {
          this.selectedKieuYeuCau = 0;
        }
      }
      await this.loadDataGridWhenChange();
    },
    // thay đổi kiểu  yêu cầu => gọi hàm napThongTinDichVuThueBao để load lại data & gọi api đổ data vào thông tin form
    async selectedKieuYeuCau(val) {
      await this.loadDataGridWhenChange();
      // xử lý thay đổi text ngày TD, ngày Khôi phục: tuanlv
      if ( val == KIEU_LAPDAT.MOTAMDUNG_SD_GP || val == KIEU_LAPDAT.MOTAMDUNG_SD_CD || val == KIEU_LAPDAT.MOTAMDUNG_SD_DD 
        || val == KIEU_LAPDAT.MOTAMDUNG_SD_IMS || val == KIEU_LAPDAT.MOTAMDUNG_SD_MEGA || val == KIEU_LAPDAT.MOTAMDUNG_SD_FIBER 
        || val == KIEU_LAPDAT.MOTAMDUNG_SD_MYTV || val == KIEU_LAPDAT.MOTAMDUNG_SD_SGTV || val == KIEU_LAPDAT.MOTAMDUNG_SD_TSL 
        || val == KIEU_LAPDAT.MOTAMDUNG_SD_MGWAN || val == KIEU_LAPDAT.MOTAMDUNG_SD_METRONET || val == KIEU_LAPDAT.MOTAMDUNG_SD_KP_AO 
        || val == KIEU_LAPDAT.MOTAMDUNG_SD_EMAIL || val == KIEU_LAPDAT.MOTAMDUNG_SD_CD_TK || val == KIEU_LAPDAT.MOTAMDUNG_SD_INTERNET_TK 
        || val == KIEU_LAPDAT.MOTAMDUNG_SD_IMS_TK || val == KIEU_LAPDAT.MOTAMDUNG_SD_MGWAN_TK || val == KIEU_LAPDAT.MOTAMDUNG_SD_TSL_TK ) {

          this.titleNgayTD = 'Ngày khôi phục'
        } else {
          this.titleNgayTD = 'Ngày TD'
        }

          
    },
  }
};
</script>
<style scoped>
/deep/ .page-content .tabs .tab-content {
  padding: 0 !important;
  border: none !important;
  box-shadow: none !important;
}
/deep/ #modal-somay .modal-footer {
  border-top: none;
}
/deep/ #modal-somay .modal-body {
  display: none;
}
.text-bold {
  font-weight: 600 !important;
}
.italicized-red {
  font-style: italic;
  color: #d32f2f;
}
.lable {
  font-weight: bold;
  color: red;
}
.cuoc {
  display: flex;
  justify-content: space-between;
}
.cuoc-left {
  /* padding: 8px; */
  /* border: 1px solid #0176ff; */
  /* border-radius: 4px; */
  color: #d32f2f;
  font-weight: bold;
}
#tra-cuu-thue-bao {
  top: 12% !important;
}
.cuoc-right {
  display: flex;
}
.item-cuoc-one {
  margin-right: 10px;
}
.template_checkbox input[type=checkbox]{
  width:20px;
  height:20px;
}
.checkmark {
  display: inline-block;transform: rotate(45deg);height: 25px;width: 12px;margin-left: 60%; border-bottom: 7px solid #f7f328;border-right: 7px solid #f7f328;
}
</style>