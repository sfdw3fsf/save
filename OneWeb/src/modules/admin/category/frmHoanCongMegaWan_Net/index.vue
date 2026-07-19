<template>
  <div>
    <div id="frmHoanCongMegaWan_Net">
      <breadcrumb :header="header" />
      <div class="list-actions-top">
        <ul class="list">
          <li v-if="btnLayTTMoi.visible" :class="{ disabledInput: !btnLayTTMoi.enabled }" @click="btnLayTTMoi_Click">
            <a> <span class="icon one-reload1"></span>Lấy TT (F5)</a>
          </li>
          <li v-if="tsbtnChapNhan.visible" :class="{ disabledInput: !tsbtnChapNhan.enabled }" @click="tsbtnChapNhan_Click">
            <a> <span class="icon one-reload1"></span>Cập nhật (F9)</a>
          </li>
          <li v-if="tsbtnGiaoViec.visible" :class="{ disabledInput: !tsbtnGiaoViec.enabled }" @click="tsbtnGiaoViec_Click">
            <a> <span class="icon one-file-edit2"></span>Giao việc</a>
          </li>
          <li v-if="tsbtnGiaoPhoiHop.visible" :class="{ disabledInput: !tsbtnGiaoPhoiHop.enabled }" @click="toolStripButton1_Click">
            <a> <span class="icon one-file-edit2"></span>Giao P/H</a>
          </li>
          <li v-if="tsbtnGiaoPhieuTinh.visible" :class="{ disabledInput: !tsbtnGiaoPhieuTinh.enabled }" @click="tsbtnGiaoPhieuTinh_Click">
            <a> <span class="icon one-file-edit2"></span>Giao tỉnh</a>
          </li>
          <li v-if="tsbtnHoanThanh.visible" :class="{ disabledInput: !tsbtnHoanThanh.enabled }" @click="tsbtnHoanThanh_Click">
            <a> <span class="icon one-reload1"></span>Hoàn thành</a>
          </li>
          <li v-if="tsbtnUploadFileNT.visible" :class="{ disabledInput: !tsbtnUploadFileNT.enabled }" @click="$refs.files.click()">
            <a><span class="icon fa fa-cloud-upload"></span>Upload File N/T</a>
            <input style="display: none" type="file" multiple="multiple" class="file" ref="files" id="upload-file" @change="changeFileUpload" />
          </li>
          <li v-if="tsbtnDownloadFileNT.visible" :class="{ disabledInput: !tsbtnDownloadFileNT.enabled }" @click="tsbtnDownloadFileNT_Click">
            <a> <span class="icon one-file-download"></span>Download File N/T</a>
          </li>
          <li v-if="tsbtnTraPhieu.visible" :class="{ disabledInput: !tsbtnTraPhieu.enabled }" @click="btnPhieuGiaoSai_Click">
            <a><span class="icon one-reload1"></span>Trả phiếu</a>
          </li>
          <li v-if="tsbtnTraPhieuTinh.visible" :class="{ disabledInput: !tsbtnTraPhieuTinh.enabled }" @click="tsbtnTraPhieuTinh_Click">
            <a><span class="icon one-reload1"></span>Trả phiếu Tỉnh</a>
          </li>
          <li v-if="tsbtnXuatFile.visible" :class="{ disabledInput: !tsbtnXuatFile.enabled }" @click="tsbtnXuatFile_Click">
            <a> <span class="icon one-reload1"></span>Xuất File</a>
          </li>
          <li v-if="tsbtnNhapMoiTT.visible" :class="{ disabledInput: !tsbtnNhapMoiTT.enabled }" @click="tsbtnNhapMoiTT_Click">
            <a> <span class="icon one-reload1"></span>Nhập mới TT</a>
          </li>
          <li v-if="tsbtnTraPhieuHNTH.visible" :class="{ disabledInput: !tsbtnTraPhieuHNTH.enabled }" @click="tsbtnTraPhieuHNTH_Click">
            <a> <span class="icon one-reload1"></span>Tra cứu HNTH</a>
          </li>
          <li v-if="tsbtnBSTT.visible" :class="{ disabledInput: !tsbtnBSTT.enabled }" @click="tsbtnBSTT_Click">
            <a> <span class="icon one-reload1"></span>Bổ sung TT</a>
          </li>
          <li v-if="tsbtnTC_CTTC.visible" :class="{ disabledInput: !tsbtnTC_CTTC.enabled }" @click="tsbtnTC_CTTC_Click">
            <a> <span class="icon one-file-search"></span>Tra cứu phiếu TC</a>
          </li>
          <!--<li v-if="tsbtnTC_TTPD.visible" :class="{ disabledInput: !tsbtnTC_TTPD.enabled }" @click="tsbtnTC_TTPD_Click">
            <a> <span class="icon one-file-search"></span>CH phân đoạn</a>
          </li> Note theo yêu cầu của Nam Nguyễn-->
          <li v-if="tsbtnGiaoViec.visible" :class="{ disabledInput: !tsbtnCapnhatKenhluong.enabled }" @click="tsbtnCapnhatKenhluong_Click">
            <a> <span class="icon one-file-edit2"></span>Cập nhật kênh luồng</a>
          </li>
        </ul>
        <!-- <div id="btn_cmenu_more" class="more dropdown" @click="contextMenuOnClicked('cmenu_more')" ref="btn_menu_more">
        <a href="javascript:void(0);"><span class="icon -ap icon-dots-three-vertical"></span></a>
      </div>
      <ejs-contextmenu id="cmenu_more" target="#btn_menu_more" :items="cmenuMoreItems" :select="contextMenuSelected" :beforeItemRender="cmenuMore_BeforeItemRender" /> -->
      </div>
      <div class="page-content">
        <div class="box-form">
          <div class="row">
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w80">NV tiếp thị</div>
                <div class="value">
                  <input :disabled="!txtNV_TiepThi.enabled" v-model="txtNV_TiepThi.value" type="text" class="form-control" />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w80">Kiểu HĐ</div>
                <div class="value">
                  <SelectExt :disabled="!cboKieu_HD.enabled" v-model="cboKieu_HD.value" :dataSource="cboKieu_HD.list" dataTextField="kieu_hd" dataValueField="kieuhd_id" />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w80">Kênh thu</div>
                <div class="value">
                  <SelectExt :disabled="!cboKenhThu.enabled" v-model="cboKenhThu.value" :dataSource="cboKenhThu.list" dataTextField="kenhthu" dataValueField="kenhthu_id" />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w80">Tổng tiền</div>
                <div class="value">
                  <input :disabled="!txtTongTien.enabled" v-model="txtTongTien.value" type="text" class="form-control tright red bold" value="0" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title" style="display: flex; align-items: center">
            <div class="pull-left">Thông tin khách hàng</div>
            <div class="col-sm-2 col-12 pull-left">
              <div class="check-action normal bold" style="color: rgb(108 104 104)">
                <input :disabled="!chkHen.enabled" v-model="chkHen.value" type="checkbox" class="check" id="chkHen" />
                <span class="name">Lắp đặt theo yêu cầu</span>
              </div>
            </div>
            <div class="col-sm-2 col-12 pull-left">
              <input :disabled="!txtSoDTLH.enabled" v-model="txtSoDTLH.value" type="text" class="form-control" />
            </div>
            <div class="col-sm-3 col-12 pull-left">
              <span v-if="lblNhanVien.enabled" class="item bold red">{{ lblNhanVien.value }}</span>
              <!-- <span class="item bold red">ThongTinKH.txtTenNV_YC</span> -->
            </div>
            <div class="clearfix"></div>
          </div>
          <div class="row">
            <div class="col-sm-7 col-12">
              <div class="row">
                <div class="col-sm-4 col-12">
                  <div class="info-row">
                    <div class="key w80">Loại HĐ/PL</div>
                    <div class="value">
                      <SelectExt :disabled="!cboLoaiHD.enabled" v-model="cboLoaiHD.value" :dataSource="cboLoaiHD.list" dataTextField="NAME" dataValueField="ID" @select="cboLoaiHD_SelectedValueChanged" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-8 col-12">
                  <div class="info-row">
                    <div class="key w80">Quy trình</div>
                    <div class="value">
                      <div class="select-custom">
                        <SelectExt :disabled="!cboQuyTrinh.enabled" class="" v-model="cboQuyTrinh.value" :dataSource="cboQuyTrinh.list" dataTextField="quytrinh" dataValueField="quytrinh_id" @select="cboQuyTrinh_SelectedValueChanged" />
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-4 col-12">
                  <div class="info-row">
                    <div class="key w80">Mã GD <span class="red">(*)</span></div>
                    <div class="value">
                      <div class="input-more-button">
                        <button class="btn" @click="btnLayMaGD_Click">
                          <span class="-ap icon-more_horiz"></span>
                        </button>
                        <input :disabled="!txtMaGD.enabled" v-model="txtMaGD.value" type="text" class="form-control highlight bold" v-on:keyup.enter="txtMaGD_KeyPress" />
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-4 col-12">
                  <div class="info-row">
                    <div class="key w80">Account <span class="red">(*)</span></div>
                    <div class="value">
                      <input :disabled="!txtMaTB.enabled" v-model="txtMaTB.value" type="text" class="form-control highlight bold" v-on:keyup.enter="txtMaTB_KeyPress" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-4 col-12">
                  <div class="info-row">
                    <div class="key w80">Mã TC <span class="red">(*)</span></div>
                    <div class="value">
                      <input :disabled="!txtMaTC.enabled" v-model="txtMaTC.value" type="text" class="form-control highlight" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-4 col-12">
                  <div class="info-row">
                    <div class="key w80">Kiểu LĐ</div>
                    <div class="value">
                      <input :disabled="!txtTenKieuLD.enabled" v-model="txtTenKieuLD.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-8 col-12">
                  <div class="info-row">
                    <div class="key w80">Loại hình <span class="red">(*)</span></div>
                    <div class="value">
                      <input :disabled="!txtLoaiHinh.enabled" v-model="txtLoaiHinh.value" type="text" class="form-control highlight bold" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-5 col-12">
              <div class="row">
                <div class="col-sm-8 col-12">
                  <div class="info-row">
                    <div class="key w100">Tên thuê bao</div>
                    <div class="value">
                      <input :disabled="!txtTenTB.enabled" v-model="txtTenTB.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-4 col-12">
                  <div class="info-row">
                    <div class="key w60">Khu vực</div>
                    <div class="value">
                      <input :disabled="!txtKhuvuc.enabled" v-model="txtKhuvuc.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w100">Địa chỉ LĐ</div>
                <div class="value">
                  <div class="input-more-button">
                    <!-- <ModalChonDiaChi modalId="popupDiaChiLD" :data="diaChiLD" @xacnhan="getDiaChiLD" /> -->
                    <button class="btn">
                      <span class="one-map-location f20"></span>
                    </button>
                    <input :disabled="!txtDiaChiLD.enabled" v-model="txtDiaChiLD.value" type="text" class="form-control bold" />
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w100">Địa chỉ LĐ cũ</div>
                <div class="value">
                  <input :disabled="!txtDiaChiLD_cu.enabled" v-model="txtDiaChiLD_cu.value" type="text" class="form-control bold" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-8 col-12">
            <div class="box-form bottom-container" style="display: flex; flex-direction: column">
              <div style="flex-grow: 1; display: flex; flex-direction: column">
                <section>
                  <div class="section-title-wrapper" style="margin: 10px 0; display: flex; align-items: center">
                    <div class="section-title" style="margin-right: 15px">Danh sách thuê bao</div>
                    <div class="item section-radio-menu">
                      <div class="check-action">
                        <input type="radio" name="chon_phieu" class="check" v-model="rdoPhieu.value" value="0" @change="rdoPhieuMoi_CheckedChanged" />
                        <span class="name">Phiếu mới</span>
                      </div>
                    </div>
                    <div class="item section-radio-menu">
                      <div class="check-action">
                        <input type="radio" name="chon_phieu" class="check" v-model="rdoPhieu.value" value="1" @change="rdoPhieuTra_CheckedChanged" />
                        <span class="name">Phiếu trả lại</span>
                      </div>
                    </div>
                    <div class="check-action section-radio-menu">
                      <input type="radio" name="chon_phieu" class="check" v-model="rdoPhieu.value" value="2" @change="rdoPhieuDaHoanThanh_CheckedChanged" />
                      <span class="name">Phiếu đã hoàn thành</span>
                    </div>
                    <div v-show="lblTongS.visible" class="" style="margin-left: 15px">
                      {{ lblTongS }}
                    </div>
                    <div style="flex-grow: 1"></div>
                    <div v-if="['1', '2'].includes(rdoPhieu.value)" style="display: flex">
                      <div style="display: flex; align-items: center; margin: 0 10px 0 0">
                        <span style="font-size: 16px">{{ dpNgayGiaoTu.label }}</span>
                        <vue-date :disabled="!dpNgayGiaoTu.enabled" v-model="dpNgayGiaoTu.value" format="DD/MM/YYYY" type="datetime"></vue-date>
                      </div>
                      <div style="display: flex; align-items: center">
                        <span style="font-size: 16px">{{ dpNgayGiaoDen.label }}</span>
                        <vue-date :disabled="!dpNgayGiaoDen.enabled" v-model="dpNgayGiaoDen.value" format="DD/MM/YYYY" type="datetime"></vue-date>
                      </div>
                    </div>
                  </div>
                  <DataGrid
                    ref="gridDanhSach"
                    panelDataHeight="285px"
                    :columns="gridDanhSach.cols"
                    :showColumnCheckbox="false"
                    :dataSource="gridDanhSach.list"
                    :showFilter="true"
                    :allowPaging="true"
                    :enablePagingServer="false"
                    :totalRecords="gridDanhSach.list.length"
                    @rowSelected="gvDanhSach_SelectionChanged"
                    :selectionSettings="{ enableToggle: false, checkboxOnly: false, checkboxMode: 'ResetOnRowClick' }"
                  />
                </section>
                <section style="margin-top: 15px; flex-grow: 1; background: transparent">
                  <ejs-tab ref="tabObj" id="tab_wizard" heightAdjustMode="None" height="100%" @selected="onTabSelected">
                    <e-tabitems>
                      <e-tabitem v-for="(item, index) in ttkttdTabs" :key="index" :header="item.header" :content="item.content"></e-tabitem>
                    </e-tabitems>
                  </ejs-tab>
                  <div id="dgvChiTiet" style="display: none; height: 100%; background: transparent">
                    <DataGrid ref="dgvChiTiet" :columns="dgvChiTiet.cols" :showColumnCheckbox="true" :dataSource="dgvChiTiet.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="dgvChiTiet.list.length" />
                  </div>
                  <div id="gridTienTrinh" style="display: none">
                    <DataGrid ref="gridTienTrinh" :columns="gridTienTrinh.cols" :showColumnCheckbox="false" :dataSource="gridTienTrinh.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="gridTienTrinh.list.length" @rowSelected="gridTienTrinhRowSelected" />
                  </div>
                  <div id="gridDangkyDV" style="display: none">
                    <DataGrid ref="gridDangkyDV" :columns="gridDangkyDV.cols" :showColumnCheckbox="false" :dataSource="gridDangkyDV.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="gridDangkyDV.list.length" />
                  </div>
                  <div id="gridNhanVien" style="display: none">
                    <DataGrid ref="gridNhanVien" :columns="gridNhanVien.cols" :showColumnCheckbox="false" :dataSource="gridNhanVien.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="gridNhanVien.list.length" />
                  </div>
                  <div id="gridMuaTbi" style="display: none">
                    <DataGrid ref="gridMuaTbi" :columns="gridMuaTbi.cols" :showColumnCheckbox="false" :dataSource="gridMuaTbi.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="gridMuaTbi.list.length" />
                  </div>
                  <div id="gridControl1" style="display: none">
                    <div class="row mt-3">
                      <div class="col-sm-6 col-12">
                        <div class="info-row">
                          <div class="key w80">Kết quả</div>
                          <div class="value">
                            <SelectExt :disabled="!cboKetQuaThucHienHangLoat.enabled" v-model="cboKetQuaThucHienHangLoat.value" :dataSource="cboKetQuaThucHienHangLoat.list" dataTextField="ten_nv" dataValueField="nhanvien_id" />
                          </div>
                        </div>
                      </div>
                      <div class="col-sm-6 col-12">
                        <div class="info-row">
                          <div class="key w80">Nội dung</div>
                          <div class="value">
                            <input :disabled="!txtNoidungTHHangLoat.enabled" v-model="txtNoidungTHHangLoat.value" type="text" class="form-control highlight bold" />
                          </div>
                        </div>
                      </div>
                    </div>
                    <DataGrid ref="gridControl1" :columns="gridControl1.cols" :showColumnCheckbox="true" :dataSource="gridControl1.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="gridControl1.list.length" />
                  </div>
                </section>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="box-form bottom-container">
              <div class="section-title-wrapper" style="margin: 0 0 5px 0">
                <div class="section-title">Thông tin hoàn công</div>
              </div>
              <section style="display: flex; flex-direction: column; flex-grow: 1">
                <div class="section-title-through-wrapper" style="margin: 0 0 0 0">
                  <div class="section-title-through-text" style="margin-right: 15px">Thông số kỹ thuật</div>
                  <!--                  <div class="line-through"></div>-->
                  <div v-if="tsbtnHoanThanh.visible" class="btn" v-b-modal.id-modal-capnhat-cntt><span class="-ap icon-settings"></span></div>
                </div>
                <div style="flex-grow: 1; background: white; margin: 0 0 10px 0;">
                  <div ref="wbrTSKT" v-html="wbrTSKT.value" class="info-wrapper" @click="handleLineClick"></div>
                </div>
              </section>
              <section>
                <div class="row">
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w80">Người GV</div>
                      <div class="value">
                        <SelectExt :disabled="!cboNguoiGV.enabled" v-model="cboNguoiGV.value" :dataSource="cboNguoiGV.list" dataTextField="ten_nv" dataValueField="nhanvien_id" />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w80">ND giao</div>
                      <div class="value">
                        <input :disabled="!txtNDGiao.enabled" v-model="txtNDGiao.value" type="text" class="form-control highlight bold" />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w80">Nội dung TH</div>
                      <div class="value">
                        <div class="input-more-button">
                          <input :disabled="!txtNoiDungTH.enabled" v-model="txtNoiDungTH.value" type="text" class="form-control highlight bold" v-on:keyup.enter="MaGD_KeyUp" id="txtMaGD" />
                          <button class="btn" @click="btnNDTH_Click">
                            <span class="-ap icon-more_horiz"></span>
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w80">ND trả</div>
                      <div class="value">
                        <input :disabled="!txtThongTinTra.enabled" v-model="txtThongTinTra.value" type="text" class="form-control bold" />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w80">Lý do trả</div>
                      <div class="value">
                        <input :disabled="!txtLyDoTra.enabled" v-model="txtLyDoTra.value" type="text" class="form-control highlight bold" />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w80" style="display: flex; justify-content: center; align-items: center">
                        Ngày GV
                        <div class="check-action" style="margin: 0 0 0 5px">
                          <input :disabled="!chkNgayGV.enabled" v-model="chkNgayGV.checked" type="checkbox" class="check" />
                          <span class="name"></span>
                        </div>
                      </div>
                      <div class="value">
                        <vue-date :disabled="!dtpNgayGV.enabled" v-model="dtpNgayGV.value" format="DD/MM/YYYY" type="datetime"></vue-date>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w80">Liên hệ</div>
                      <div class="value">
                        <input :disabled="!txtLienHe.enabled" v-model="txtLienHe.value" type="text" class="form-control highlight bold" />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w80" style="display: flex; justify-content: center; align-items: center">
                        Ngày HT
                        <div class="check-action" style="margin: 0 0 0 5px">
                          <input :disabled="!chkNgayBG.enabled" v-model="chkNgayBG.checked" type="checkbox" class="check" />
                          <span class="name"></span>
                        </div>
                      </div>
                      <div class="value">
                        <vue-date :disabled="!dtpNgayBG.enabled" v-model="dtpNgayBG.value" format="DD/MM/YYYY" type="datetime"></vue-date>
                      </div>
                    </div>
                  </div>
                </div>
              </section>
            </div>
          </div>
        </div>
      </div>
    </div>
    <frmChiTietKenhTheoPhien ref="frmChiTietKenhTheoPhien" />
    <!-- <frmDSHoSoBC ref="frmDSHoSoBC" v-bind="frmDSHoSoBC.input" /> -->
    <frmGiaoPhieu_Net ref="frmGiaoPhieu_Net" @tsbtnGiaoPhieu_Click="frmGiaoPhieu_Net_tsbtnGiaoPhieu_Click" />
    <ejs-dialog :enableResize="true" :visible="false" ref="popupfrnGiaoPhieuNV" :header="'Giao phiếu nhân viên'" showCloseIcon="true" target="#frmHoanCongMegaWan_Net" width="90%" height="800px" :allowDragging="true">
      <div style="height: 600px"><frnGiaoPhieuNV ref="frnGiaoPhieuNV" @giaoviec_success="giaoviec_success" /></div>
    </ejs-dialog>
    <ejs-dialog
      ref="ejsDialog"
      :visible="false"
      :isModal="true"
      target="#frmHoanCongMegaWan_Net"
      :enableResize="ejsDialog.config.enableResize"
      :resizeHandles="['All']"
      :header="ejsDialog.header"
      showCloseIcon="true"
      :width="ejsDialog.config.width"
      :height="ejsDialog.config.height"
      :minHeight="ejsDialog.config.minHeight"
      :allowDragging="ejsDialog.config.allowDragging"
      :position="ejsDialog.config.position"
      :animationSettings="ejsDialog.config.animationSettings"
      @beforeOpen="ejsDialogBeforeOpen"
      @beforeClose="ejsDialogBeforeClose"
    >
      <div v-if="ejsDialog.name" :key="uuidv4()" style="overflow-y: scroll">
        <frmTraCuuHopDong v-if="ejsDialog.name == 'frmTraCuuHopDong'" ref="frmTraCuuHopDong" v-bind="frmTraCuuHopDong.input" />
        <frmTraPhieuTC v-if="ejsDialog.name == 'frmTraPhieuTC'" ref="frmTraPhieuTC" v-bind="frmTraPhieuTC.input" @btnTraphieu_Click="frmTraPhieuTC_btnTraphieu_Click" />
        <frmCapNhatTSKT_CNTT v-if="ejsDialog.name == 'frmCapNhatTSKT_CNTT'" ref="frmCapNhatTSKT_CNTT" v-bind="frmCapNhatTSKT_CNTT.input" />
        <frmDanhSachSoThanhVien v-if="ejsDialog.name == 'frmDanhSachSoThanhVien'" ref="frmDanhSachSoThanhVien" v-bind="frmDanhSachSoThanhVien.input" />
        <frmTraCuuHopDongBC v-if="ejsDialog.name == 'frmTraCuuHopDongBC'" ref="frmTraCuuHopDongBC" :dulieu="frmTraCuuHopDongBC.input" @onChuyenThiCongSuccess="frmTraCuuHopDongBC_onChuyenThiCongSuccess" />
        <frmNoiDung v-if="ejsDialog.name == 'frmNoiDung'" ref="frmNoiDung" v-bind="frmNoiDung.input" @btnGhiLai_Click="frmNoiDung_btnGhiLai_Click" />
        <frmTraPhieuVIP v-if="ejsDialog.name == 'frmTraPhieuVIP'" ref="frmTraPhieuVIP" v-bind="frmTraPhieuVIP.input" @onBtnTraphieuClicked="frmTraPhieuVIP_onBtnTraphieuClicked" />
        <frmTraCuuPhieuTCBC v-if="ejsDialog.name == 'frmTraCuuPhieuTCBC'" ref="frmTraCuuPhieuTCBC" v-bind="frmTraCuuPhieuTCBC.input" />
        <frmDSHoSoBC v-if="ejsDialog.name == 'frmDSHoSoBC'" ref="frmDSHoSoBC" v-bind="frmDSHoSoBC.input" />
      </div>
    </ejs-dialog>
    <frmCapnhatCNTT
      ref="frmCapnhatCNTT"
      modal-id="id-modal-capnhat-cntt"
      :tinh-id="gridDanhSach && gridDanhSach.value ? gridDanhSach.value.tinh_id : 0"
      :hdtb-id="gridDanhSach && gridDanhSach.value ? gridDanhSach.value.hdtb_id : 0"
      :huong-giao-id="huonggiao_id"
      :parent="this"
      @xacnhan="frmCapnhatCNTT_XacNhan"
    />
  </div>
</template>
<script>
import Vue from 'vue'
import moment from 'moment'
import XLSX from 'xlsx'
import breadcrumb from '@/components/breadcrumb'
import api from './api'
import { TabPlugin } from '@syncfusion/ej2-vue-navigations'
Vue.use(TabPlugin)
import { DichVuVienThong, LoaiHopDong, TrangThaiHD, LoaiHinhTB } from './constants'
import { KIEU_HD } from '@/const/enums/index.js'
import { FORM_CONFIG, DS_QUYEN_VT, LOAI_KHO, MUCDICH_VT, LOAIPHIEU_DENGHI_VATTU } from '@/modules/QLVT/NhapXuatVT_v2/const'
import DateTimeHelper from '@/modules/QLVT/NhapXuatVT_v2/helpers/dateHelper'
import frmChiTietKenhTheoPhien from '@/modules/admin/category/frmHoanCongMegaWan_Net/components/frmChiTietKenhTheoPhien'
import { onebssIdIsValid, convertToUpcaseKey, isNullOrEmpty, getDateStringOrDefault, generateComponentKey } from '@/modules/QLVT/NhapXuatVT_v2/helpers'
// import frmDanhSachSoThanhVien from '@/modules/contract/setup/VoiceBrandname/part/DanhSachThanhVien.vue';
import { ChonInTemplate } from './components/gridDanhSachColumns'
// import { DDataGrid, DDatePicker } from '@/modules/QLVT/NhapXuatVT_v2/components/common/DDataGrid'
export default {
  name: 'frmHoanCongMegaWan_Net',
  components: {
    breadcrumb,
    // DDataGrid,
    frmTraCuuHopDong: () => import('@/modules/search/subscriber/SearchContract/SearchContract.vue'),
    frmTraPhieuTC: () => import('@/modules/contract/setup/ReturnInstallTicket/frmTraPhieuTC.vue'),
    frnGiaoPhieuNV: () => import('@/modules/contract/setup/DeclareLandline/popups/frnGiaoPhieuNV'),
    frmGiaoPhieu_Net: () => import('@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupGiaoPhieuNet/GiaoPhieuNet'),
    frmDanhSachSoThanhVien: () => import('@/modules/contract/setup/VoiceBrandname/part/DanhSachThanhVien'),
    frmCapNhatTSKT_CNTT: () => import('@/modules/admin/category/frmHoanCongMegaWan_Net/components/frmCapNhatTSKT_CNTT'),
    frmTraCuuHopDongBC: () => import('@/modules/contract/setup/LookupNavigateContract'),
    frmNoiDung: () => import('./components/frmNoiDung'),
    frmChiTietKenhTheoPhien: () => import('./components/frmChiTietKenhTheoPhien'),
    frmTraPhieuVIP: () => import('@/modules/admin/category/frmHoanCongIMS_Net/components/frmTraPhieuVIP'),
    frmDSHoSoBC: () => import('./components/frmDSHoSoBC_Net'),
    frmTraCuuPhieuTCBC: () => import('@/modules/contract/setup/LookupDetailConstructionCrossSale/LookupDetailConstructionCrossSale'),
    frmCapnhatCNTT: () => import('@/modules/admin/category/frmGiaoViecVIP/components/frmCapnhatCNTT/index.vue')
  },
  data() {
    return {
      localLoading: 0,
      header: {
        title: 'Hoàn công Megawan',
        list: [
          { name: 'Trang chủ', link: { name: 'Ui.cards' } },
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } }
        ]
      },
      btnLayTTMoi: { enabled: true, visible: true },
      tsbtnChapNhan: { enabled: true, visible: true },
      tsbtnKichHoat: { enabled: true, visible: true },
      tsbtnGiaoViec: { enabled: true, visible: true },
      tsbtnGiaoPhieu: { enabled: true, visible: true },
      tsbtnGiaoPhoiHop: { enabled: true, visible: true },
      tsbtnGiaoPhieuTinh: { enabled: true, visible: true },
      tsddbIn: { enabled: true, visible: true },
      tsbtnHoanThanh: { enabled: true, visible: true },
      tsbtnHoanCong: { enabled: true, visible: true },
      tsbtnUploadFileNT: { enabled: true, visible: true },
      tsbtnDownloadFileNT: { enabled: true, visible: true },
      tsbtnChuyenTo: { enabled: true, visible: true },
      tsbtnTraPhieu: { enabled: true, visible: true },
      tsbtnTraPhieuTinh: { enabled: true, visible: true },
      tsbtnVatTu: { enabled: true, visible: true },
      tsbtnGiaoViec_KBB: { enabled: true, visible: true },
      tsbtnVatTuMoi: { enabled: true, visible: true },
      tsbtnXuatFile: { enabled: true, visible: true },
      tsbtnHen: { enabled: true, visible: true },
      tsbtnGiaoPhieu_Ton: { enabled: true, visible: true },
      tsbtnThongTinKT: { enabled: true, visible: true },
      tsbtnNVKT: { enabled: true, visible: true },
      tsbtnDuAn: { enabled: true, visible: true },
      tsbtnHoanThienHS: { enabled: true, visible: true },
      tsbtnKQ_DoKiem: { enabled: true, visible: true },
      tsbtnSua_KenhThu: { enabled: true, visible: true },
      tsbtnThongTinVRF: { enabled: true, visible: true },
      tsbtnNhapMoiTT: { enabled: true, visible: true },
      tsbtnTraPhieuHNTH: { enabled: true, visible: true },
      tsbtnBSTT: { enabled: true, visible: true },
      tsbtnTC_CTTC: { enabled: true, visible: true },
      tsbtnTC_TTPD: { enabled: true, visible: true },
      tsbtnCapnhatKenhluong: { enabled: true, visible: true },
      cboTongDaiIMS: { enabled: true, visible: true, list: [], value: null },
      txtNV_TiepThi: { enabled: true, visible: true, value: null },
      cboKieu_HD: { enabled: false, visible: true, list: [], value: null },
      cboKenhThu: { enabled: false, visible: true, list: [], value: null },
      txtTongTien: { enabled: true, visible: true, value: null },
      chkHen: { enabled: true, visible: true, value: null },
      txtSoDTLH: { enabled: true, visible: true, value: null },
      txtSoDTLH: { enabled: true, visible: true, value: null },
      cboLoaiHD: { enabled: false, visible: true, list: [], value: null },
      cboQuyTrinh: { enabled: true, visible: true, list: [], value: null },
      txtMaGD: { enabled: true, visible: true, value: null },
      txtMaTB: { enabled: true, visible: true, value: null },
      txtMaTC: { enabled: true, visible: true, value: null },
      txtTenKieuLD: { enabled: true, visible: true, value: null },
      txtLoaiHinh: { enabled: true, visible: true, value: null },
      txtTenTB: { enabled: true, visible: true, value: null },
      txtKhuvuc: { enabled: true, visible: true, value: null },
      txtDiaChiLD: { enabled: true, visible: true, value: null },
      txtDiaChiLD_cu: { enabled: true, visible: true, value: null },
      wbrTSKT: { enabled: true, visible: true, value: null },
      rdoPhieu: { enabled: true, visible: true, value: 0 },
      rdoPhieuMoi: { enabled: true, visible: true, value: null },
      rdoPhieuTra: { enabled: true, visible: true, value: null },
      rdoPhieuDaHoanThanh: { enabled: true, visible: true, value: null },
      lblTongS: { enabled: true, visible: false, value: 'Tổng chọn 0' },
      dpNgayGiaoTu: { enabled: true, visible: true, value: null, label: 'Từ ngày:' },
      dpNgayGiaoDen: { enabled: true, visible: true, value: null, label: 'Đến ngày' },
      chkLayTSo: { enabled: true, visible: true, value: null },
      lblLienHe: { enabled: true, visible: true, value: null },
      cboKetQuaThucHienHangLoat: { enabled: true, visible: true, list: [], value: null },
      txtNoidungTHHangLoat: { enabled: true, visible: true, value: null },
      btnGhiLaiHangLoat: { enabled: true, visible: true, value: null },
      cboNguoiGV: { enabled: true, visible: true, list: [], value: null },
      txtNDGiao: { enabled: true, visible: true, value: null },
      txtNoiDungTH: { enabled: true, visible: true, value: null },
      btnNDTH: { enabled: true, visible: true, value: null },
      txtThongTinTra: { enabled: true, visible: true, value: null },
      txtLyDoTra: { enabled: true, visible: true, value: null },
      chkNgayGV: { enabled: true, visible: true, value: null },
      dtpNgayGV: { enabled: true, visible: true, value: null },
      txtLienHe: { enabled: true, visible: true, value: null },
      chkNgayBG: { enabled: true, visible: true, value: null },
      dtpNgayBG: { enabled: true, visible: true, value: null },
      lblNhanVien: { enabled: true, visible: true, value: null },
      btnPhieuTC: { enabled: true, visible: true },
      ttkttdTabs: [
        {
          header: {
            text: 'Chi tiết điều hành thi công'
          },
          content: '#dgvChiTiet'
        },
        { header: { text: 'Tiến trình thi công' }, content: '#gridTienTrinh' },
        { header: { text: 'Danh sách dịch vụ gia tăng' }, content: '#gridDangkyDV' },
        { header: { text: 'Danh sách nhân viên thực hiện' }, content: '#gridNhanVien' },
        { header: { text: 'Danh sách mua thiết bị' }, content: '#gridMuaTbi' },
        { header: { text: 'Danh sách số thuê bao' }, content: '#gridControl1' }
      ],
      gridDanhSach: {
        enabled: true,
        visible: true,
        cols: [
          { fieldName: 'chon_in', headerText: 'Chọn in', template: ChonInTemplate(this), width: '80px', freeze: 'Left', allowFiltering: true },
          { fieldName: 'ma_tb', headerText: 'Số máy/Acc', width: '120px', freeze: 'Left', allowFiltering: true },
          { fieldName: 'ma_lt', headerText: 'Số ảo', width: '115px', freeze: 'Left', allowFiltering: true },
          { fieldName: 'ma_gd', headerText: 'Mã GD', width: '135px', freeze: 'Left', allowFiltering: true },
          { fieldName: 'tt_bc', headerText: 'TT giao tỉnh', width: '150px', freeze: 'Left', allowFiltering: true },
          // { fieldName: 'ngay_yc', headerText: 'Ngày YC', allowFiltering: true },
          { fieldName: 'ngay_yc', headerText: 'Ngày YC', allowFiltering: true, format: { type: 'date', format: 'dd/MM/yyyy HH:mm:ss' }, type: 'date', allowEditing: false },
          { fieldName: 'ngaygiao', headerText: 'Ngày giao', allowFiltering: true, format: { type: 'date', format: 'dd/MM/yyyy HH:mm:ss' }, type: 'date', allowEditing: false },
          { fieldName: 'ngay_ht', headerText: 'Ngày HT', allowFiltering: true, format: { type: 'date', format: 'dd/MM/yyyy HH:mm:ss' }, type: 'date', allowEditing: false },
          { fieldName: 'ngay_tinh_ht', headerText: 'Ngày HT Tỉnh', allowFiltering: true, format: { type: 'date', format: 'dd/MM/yyyy HH:mm:ss' }, type: 'date', allowEditing: false },
          { fieldName: 'ngay_net_cn', headerText: 'Ngày HT NET', allowFiltering: true, format: { type: 'date', format: 'dd/MM/yyyy HH:mm:ss' }, type: 'date', allowEditing: false },
          { fieldName: 'ma_kh', headerText: 'Mã KH', allowFiltering: true },
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true },
          { fieldName: 'ten_loaihd', headerText: 'Loại HĐ', allowFiltering: true },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình TB', allowFiltering: true },
          { fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowFiltering: true },
          { fieldName: 'huonggiao', headerText: 'Hướng giao', allowFiltering: true },
          { fieldName: 'tentinh', headerText: 'Tỉnh LĐ', allowFiltering: true },
          { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true },
          { fieldName: 'tinh_cq', headerText: 'Tỉnh bán', allowFiltering: true },
          { fieldName: 'ma_bss', headerText: 'Mã BSS', allowFiltering: true },
          { fieldName: 'id_erp', headerText: 'Mã ERP', allowFiltering: true },
          { fieldName: 'vrf_vfi', headerText: 'VRF/VFI', allowFiltering: true },
          { fieldName: 'dv_hen', headerText: 'Đơn vị hẹn', allowFiltering: true },
          { fieldName: 'giohen_tu', headerText: 'Hẹn từ', allowFiltering: true },
          { fieldName: 'giohen_den', headerText: 'Hẹn đến', allowFiltering: true },
          { fieldName: 'noidung_hen', headerText: 'Nội dung hẹn', allowFiltering: true },
          { fieldName: 'goi_kt', headerText: 'Gói kt', allowFiltering: true },
          { fieldName: 'chitieu_tg', headerText: 'Chỉ tiêu TG', allowFiltering: true },
          { fieldName: 'thoigian_ck', headerText: 'Thời gian cam kết', allowFiltering: true },
          { fieldName: 'gio_conlai', headerText: 'Giờ còn lại', allowFiltering: true },
          { fieldName: 'dv_giao', headerText: 'Đơn vị giao', allowFiltering: true },
          { fieldName: 'in_status', headerText: 'Trạng thái in', allowFiltering: true },
          { fieldName: 'ten_status', headerText: 'Trạng thái', allowFiltering: true },
          { fieldName: 'tt_ct', headerText: 'Trạng thái CT', allowFiltering: true },
          { fieldName: 'ngay_ht', headerText: 'Ngày BG', allowFiltering: true },
          { fieldName: 'ngay_kh', headerText: 'Ngày KH', allowFiltering: true },
          { fieldName: 'lydoton_tc', headerText: 'Lý do tồn', allowFiltering: true },
          { fieldName: 'ghichu_tb', headerText: 'Ghi chú tồn', allowFiltering: true },
          { fieldName: 'url_file_nt', headerText: 'File NT', allowFiltering: true },
          { fieldName: 'so_dt_giao', headerText: 'ĐT NV giao', allowFiltering: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      dgvChiTiet: {
        cols: [
          { fieldName: 'dvg', headerText: 'Đơn vị giao', allowFiltering: true },
          { fieldName: 'ngay_giao', headerText: 'Ngày giao', allowFiltering: true },
          { fieldName: 'nv_giao', headerText: 'Người giao', allowFiltering: true },
          { fieldName: 'nd_giao', headerText: 'Nội dung giao', allowFiltering: true },
          { fieldName: 'nd_thuchien', headerText: 'Nội dung TH', allowFiltering: true },
          { fieldName: 'dvn', headerText: 'Đơn vị nhận', allowFiltering: true },
          { fieldName: 'ngay_tra', headerText: 'Ngày trả', allowFiltering: true },
          { fieldName: 'nd_tra', headerText: 'Nội dung trả', allowFiltering: true },
          { fieldName: 'lydotra', headerText: 'Lý do trả', allowFiltering: true },
          { fieldName: 'ngay_giao', headerText: 'Ngày CN', allowFiltering: true },
          { fieldName: 'nv_giao', headerText: 'Người CN', allowFiltering: true },
          { fieldName: 'so_dt', headerText: 'Điện thoại liên hệ', allowFiltering: true },
          { fieldName: 'trangthai_ph', headerText: 'Trạng thái phiếu', allowFiltering: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridTienTrinh: {
        cols: [
          { fieldName: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true },
          { fieldName: 'ngay_cn', headerText: 'Ngày CN', allowFiltering: true },
          { fieldName: 'noidung', headerText: 'Nội dung', allowFiltering: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridDangkyDV: {
        cols: [
          { fieldName: 'ma_dvgt', headerText: 'Mã DVGT', allowFiltering: true },
          { fieldName: 'ten_dvgt', headerText: 'Tên dịch vụ', allowFiltering: true },
          { fieldName: 'kieu', headerText: 'Kiểu YC', allowFiltering: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridNhanVien: {
        cols: [
          { fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true },
          { fieldName: 'so_dt', headerText: 'Số điện thoại', allowFiltering: true },
          { fieldName: 'nhiemvu', headerText: 'Nhiệm vụ', allowFiltering: true },
          { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridMuaTbi: {
        cols: [
          { fieldName: 'ma_tbi', headerText: 'Mã thiết bị', allowFiltering: true },
          { fieldName: 'loai_tbi', headerText: 'Tên thiết bị', allowFiltering: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridControl1: {
        cols: [
          { fieldName: 'HDTB_CT_ID', headerText: 'ID', allowFiltering: true },
          { fieldName: 'SO_DT_CT', headerText: 'Số thuê bao', allowFiltering: true },
          { fieldName: 'EMAIL_CT', headerText: 'Địa chỉ IP', allowFiltering: true },
          { fieldName: 'TEN_TB_CT', headerText: 'Số CF', allowFiltering: true },
          { fieldName: 'THUCHIEN', headerText: 'Thực hiện/Trả phiếu', allowFiltering: true },
          { fieldName: 'NOIDUNG', headerText: 'Nội dung thực hiện', allowFiltering: true },
          { fieldName: 'TRANGTHAI_CT', headerText: 'Trạng thái', allowFiltering: true },
          { fieldName: 'CAPNHAT', headerText: 'Cập nhật', allowFiltering: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      // variable
      tinh_khdn: [97, 100],
      phieu_id: null,
      luong_id: 0,
      dt: [],
      dsIn: [],
      _vquytrinh_id: 0,
      huonggiao_id: 0,
      //dialog
      ejsDialog: {
        header: 'Tiêu đề',
        name: null,
        visible: false,
        config: {
          width: '95%',
          height: '95%',
          minHeight: '150px',
          enableResize: true,
          allowDragging: true,
          position: { X: 'center', Y: 'center' },
          // position: { X: 420, Y: 14 },
          animationSettings: { effect: 'Fade' }
        }
      },
      frmTraCuuHopDong: { visible: false, input: {} },
      frmDanhSachSoThanhVien: { visible: false, input: {} },
      frmCapNhatTSKT_CNTT: { visible: false, input: {} },
      frmTraCuuHopDongBC: { visible: false, input: {} },
      frmNoiDung: { visible: false, input: {} },
      frmTraPhieuVIP: { visible: false, input: {} },
      frmTraPhieuTC: { visible: false, input: {} },
      frmDSHoSoBC: { visible: false, input: {} },
      frmTraCuuPhieuTCBC: { visible: false, input: {} }
    }
  },
  async mounted() {
    await this.frmHoanCongMegaWan_FormLoad()
    this.xuLyRdoPhieuMoi()
  },
  computed: {},
  watch: {
    localLoading(val) {
      if (val > 0) this.loading(true)
      else this.loading(false)
    },
    'gridDanhSach.list'(val) {
      console.log('watch gridDanhSach.list')
      console.log(val)
      for (let i of this.gridDanhSach.list) {
        // console.log('i.ngay_yc')
        // console.log(i)
        // console.log(i.ngay_yc)
        i.ngay_yc = moment(i.ngay_yc, 'DD/MM/yyyy HH:mm:ss').toDate()
        i.ngaygiao = moment(i.ngaygiao, 'DD/MM/yyyy HH:mm:ss').toDate()
        i.ngay_ht = moment(i.ngay_ht, 'DD/MM/yyyy HH:mm:ss').toDate()
        i.ngay_tinh_ht = i.ngay_tinh_ht ? moment(i.ngay_ht, 'DD/MM/yyyy HH:mm:ss').toDate() : null
        i.ngay_net_cn = i.ngay_net_cn ? moment(i.ngay_ht, 'DD/MM/yyyy HH:mm:ss').toDate() : null
      }
      console.log(val)
    }
  },
  methods: {
    uuidv4() {
      return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, (c) => (c ^ (crypto.getRandomValues(new Uint8Array(1))[0] & (15 >> (c / 4)))).toString(16))
    },
    async callApiWrapper(action, body, options = { showError: true }) {
      let result = null
      try {
        this.localLoading++
        result = await action(this.axios, body)
        return result.data.data != null ? result.data.data : null
      } catch (e) {
        if (options.showError) {
          if (e.data != null) {
            this.$toast.error(`${e.data.message}`)
          } else {
            this.$toast.error(`Có lỗi gọi API`)
          }
        }
        return 'error'
      } finally {
        setTimeout(() => {
          this.localLoading--
        }, 500)
      }
      return null
    },
    gridDanhSach_getSelectedRecords() {
      return this.gridDanhSach.list.filter((e) => e.selected && e.selected == true)
    },
    onTabSelected(e) {},
    onMenuClicked(action) {
      this.menus.find((e) => e.id == 'lay_tt').enabled = false
    },
    onKeyDown(key) {},
    getMenu(id) {
      return this.menus.find((e) => e.id == id)
    },
    //menu event handler
    async frmHoanCongMegaWan_FormLoad() {
      // try {
      this.Tag = this.$route.query.tag
      // try {
      if (this.Tag != '') {
        let words = this.Tag.split('+')
        if (words.length >= 1) this.tthd_id = words[0]
        if (words.length >= 2) {
          this.dsloaihd_id = words[1]
          this.dsloaihd_id = this.dsloaihd_id.replaceAll(';', ',')
        }
        if (words.length >= 3) {
          this.dsdichvuvt_id = words[2]
          this.dsdichvuvt_id = this.dsdichvuvt_id.replaceAll(';', ',')
        }
        if (words.length >= 4) {
          if (words[3] == '1') this.lay_phieu_con = true
        }
      } else {
        this.tthd_id = TrangThaiHD.DANG_THI_CONG
        this.dsloaihd_id = '1'
        this.dsdichvuvt_id = '8'
      }
      // } catch {
      //   this.tthd_id = TrangThaiHD.DANG_THI_CONG
      //   this.dsloaihd_id = '1'
      //   this.dsdichvuvt_id = '8'
      // }
      let dtKenhThu = await this.callApiWrapper(api.sp_get_data_kenhthu, {})
      if (dtKenhThu.length > 0) {
        this.cboKenhThu.list = dtKenhThu
        this.cboKenhThu.value = dtKenhThu[0].kenhthu_id
      } else this.cboKenhThu.list = []
      this.cboKieu_HD.list = await this.callApiWrapper(api.sp_lay_ds_kieu_hd, {})
      this.cboLoaiHD.list = await this.callApiWrapper(api.lay_ds_loai_hd, {
        loaiHdId: this.dsloaihd_id
      })
      this.dtpNgayBG.value = DateTimeHelper.NgayCN()
      this.dtpNgayGV.value = DateTimeHelper.NgayCN()
      this.dtpNgayGV.enabled = false
      this.cboNguoiGV.list = await this.callApiWrapper(api.sp_lay_ds_nhanvien_thuchien_theo_dv, {
        donvi_id: this.$root.token.getDonViID()
      })
      this.cboNguoiGV.value = this.$root.token.getNhanVienID()
      await this.HienThiGiaoDien(this.luong_id)
      await this.HienThiDanhSachHDTB()
      this.kt_load = true
      // } catch (exp) {
      //   this.$toast.error(`${exp}`)
      // }
    },
    async frmHoanCongMegaWan_Load() {
      this.tsbtnGiaoPhieu.visible = !this.lay_phieu_con
      let caption = 'VNPT Net - Khai báo dịch vụ'
      // labelFunctionTitle.value = caption.toUpperCase()
      this.value = caption.toUpperCase()
      this.tsbtnTraPhieuHNTH.enabled = false
      this.tsbtnBSTT.enabled = false
    },
    // async inDex_load
    // {
    //     get
    //     {
    //         try
    //         {
    //             if (matb_link == "") return 0;
    //             if (gridDanhSach.list.length <= 1) return 0;
    //             for (let i = 0; i < gridDanhSach.list.length; i++)
    //             {
    //                 if (gridDanhSach.GetRowCellValue(i, "MA_TB") == matb_link)
    //                     return i;
    //             }
    //         }
    //         catch
    //         {
    //             return 0;
    //         }
    //         return 0;
    //     }
    // },
    async HienThiGiaoDien(luong_id) {
      this.tsbtnKQ_DoKiem.visible = false
      this.tsbtnDuAn.visible = false
      this.tsbtnHoanThienHS.visible = false
      this.tsbtnHoanThienHS.enabled = false
      this.tsbtnTraPhieu.visible = false
      this.tsbtnHoanCong.visible = false
      this.tsbtnGiaoViec_KBB.visible = false
      this.tsbtnSua_KenhThu.visible = false
      this.tsbtnGiaoPhieu.visible = false
      this.tsbtnVatTu.visible = false
      this.tsbtnVatTuMoi.visible = false
      this.cboQuyTrinh.enabled = false
      this.dtThaoTac = await this.callApiWrapper(api.sp_lay_luong_thaotac, { luong_id: luong_id ? luong_id : 0 })
      // }
      // catch (ex)
      // {
      // }
    },
    handleLineClick(e) {
      let clickedElId = e.target.id
      if (clickedElId === 'btnXemThongTinChiTiet') {
        console.log(1);
        this.$refs.tabObj.select(5);
        document.getElementById("gridControl1").scrollIntoView();
        
      } 
    },
    async HienThiControl(loaitb_id) {},
    async Clear() {
      this.wbrTSKT.value = ''
      this.txtLoaiHinh.value = ''
      this.tramtb_id = 0
      this.txtSoDTLH.value = ''
      this.txtTenKieuLD.value = ''
      this.lblLienHe.value = ''
      this.txtMaTB.value = ''
      this.lblNhanVien.value = 'Nhân viên YC : '
      this.txtNV_TiepThi.value = ''
      this.txtMaTC.value = ''
      this.host_id = 0
      this.vma_gd = ''
      this.txtMaGD.value = ''
      this.txtTenTB.value = ''
      this.txtKhuvuc.value = ''
      this.txtDiaChiLD.value = ''
      this.txtDiaChiLD_cu.value = ''
      this.txtNDGiao.value = ''
      this.txtThongTinTra.value = ''
      this.txtLyDoTra.value = ''
      this.txtTongTien.value = '0'
      this.cboNguoiGV.value = ''
      this.chkNgayBG.value = false
      this.chkNgayGV.value = false
      this.dtpNgayGV.value = DateTimeHelper.NgayCN()
      this.gridNhanVien.list = []
      this.khachhang_id = 0
      this.thanhtoan_id = 0
      this.diachikh_id = 0
      this.apkh_id = 0
      this.phokh_id = 0
      this.phuongkh_id = 0
      this.quankh_id = 0
      this.tinhkh_id = 0
      this.sonhakh = ''
      this.diachitt_id = 0
      this.phott_id = 0
      this.aptt_id = 0
      this.phuongtt_id = 0
      this.quantt_id = 0
      this.tinhtt_id = 0
      this.sonhatt = ''
      this.hdkh_id = 0
      this.hdtt_id = 0
      this.lblTongS.value = 'Tổng số: 0/0'
      this.kt_thaydoi = false
      this.in_hdtb_id_cha = 0
      this.in_thuebao_id_cha = 0
      this.str_ma_tb_cha = ''
      this.tinh_ld = ''
    },
    async HienThiDanhSachHDTB() {
      // try {
      this.dsIn = []
      let vloai_id = this.rdoPhieu.value // 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
      // if (this.rdoPhieuMoi.value) vloai_id = 0
      // else if (this.rdoPhieuTra.value) vloai_id = 1
      // else vloai_id = 2
      this.dt = await this.callApiWrapper(api.lay_ds_phieu_giaoden_net_v3, {
        vdichvuvt_id: DichVuVienThong.MEGAWAN,
        vtthd_id: this.tthd_id,
        vma_gd: '',
        vphieu_id: 0,
        vloaihd_id: this.cboLoaiHD.value,
        vcaidat_dv: 0,
        vkieu_id: 0,
        vhuonggiao_id: 0,
        vloai_id: vloai_id,
        vngaygiao_tu: this.dpNgayGiaoTu.value ?? '0',
        vngaygiao_den: this.dpNgayGiaoDen.value ?? '0'
      })
      this.dsIn = [...this.dt, ...this.dsIn]
      this.gridDanhSach.list = this.dt
      if (this.dt.length == 0) await this.HienThiDS_PhieuTH(0)
      if (this.dt.length <= 0) await this.Clear()
      this.lblTongS.value = 'Tổng số: 0/' + this.gridDanhSach.list.length + ''
      // } catch (ex) {
      //   this.$toast.error(`${ex}`)
      // }
    },
    async HienThiTTHopDongTB(ds) {
      console.log('HienThiTTHopDongTB')
      if (ds.length > 0) {
        let nIndex = 0
        if (this.gridDanhSach.value) nIndex = ds.findIndex((e) => e.phieu_id == this.gridDanhSach.value.phieu_id)
        console.log(nIndex)
        console.log(ds[nIndex])
        this.hdtb_id = ds[nIndex].hdtb_id
        this.phieu_id = ds[nIndex].phieu_id
        let ma_tb = ds[nIndex].ma_tb
        let vhuonggiao_id = ds[nIndex].huonggiao_id
        if (vhuonggiao_id == 8888) this.lay_phieu_con = true
        this.phieu_tinh_id = ds[nIndex].phieu_tinh_id
        this.hdkh_id = ds[nIndex].hdkh_id
        this.cboLoaiHD.value = ds[nIndex].loaihd_id
        await this.cboLoaiHD_SelectedValueChanged()
        this.cboQuyTrinh.value = ds[nIndex].quytrinh_id
        await this.cboQuyTrinh_SelectedValueChanged()
        this.txtSoDTLH.value = ds[nIndex].so_dt
        await this.callApiWrapper(api.fn_lay_thongtin_lh_lapdat, {
          hdkh_id: this.hdkh_id
        })
        if (this.lblLienHe.value == '') this.lblLienHe.value = 'Liên hệ:'
        this.tinh_id = ds[nIndex].tinh_id
        this.tinh_tc = await this.callApiWrapper(api.sp_lay_tinh_tc_theo_tinh_id, {
          tinh_id: this.tinh_id
        })
        this.tinh_ld = ds[nIndex].tinh_ld
        if (this.tinh_khdn.includes(this.tinh_id)) {
          let tinh_id_thicong = ds[nIndex].tinh_thicong
          let tinh_thicong = await this.callApiWrapper(api.sp_lay_tinh_tc_theo_tinh_id, {
            tinh_id: tinh_id_thicong
          })
          let dtTTHD = await this.callApiWrapper(api.bancheo_lay_thongtin_hd_thicong, {
            // tinhthicong_id: JSON.stringify(tinh_thicong),
            tinhthicong_id: tinh_id_thicong,
            vhdtb_ban: this.hdtb_id,
            vtinh_id: this.tinh_id
          })
          if (dtTTHD.length > 0) this.txtMaTC.value = dtTTHD[0].ma_tb
          else this.txtMaTC.value = ''
          this.tsbtnTraPhieuTinh.visible = false
          this.tsbtnTraPhieu.visible = true // thêm theo Tuấn Anh bảo
        } else {
          let dtTTHD = await this.callApiWrapper(api.sp_tt_hd_bancheo, {
            vphanvung_id: this.tinh_id,
            vhdtb_id: this.hdtb_id
          })
          if (dtTTHD.length > 0) {
            let tinh_tc_id = dtTTHD[0].tinh_thicong
            let _tinh_tc = await this.callApiWrapper(api.sp_lay_tinh_tc_theo_tinh_id, {
              tinh_id: tinh_tc_id
            })
            dtTTHD = await this.callApiWrapper(api.bancheo_lay_thongtin_hd_thicong, {
              // tinhthicong_id: JSON.stringify(_tinh_tc),
              tinhthicong_id: tinh_tc_id,
              vhdtb_ban: this.hdtb_id,
              vtinh_id: this.tinh_id
            })
            if (dtTTHD.length > 0) this.txtMaTC.value = dtTTHD[0].ma_tb
            else this.txtMaTC.value = ''
          } else this.txtMaTC.value = this.ma_tb
          this.tsbtnTraPhieu.visible = false
          this.tsbtnTraPhieuTinh.visible = true
        }
        await this.HT_THONGSO_KYTHUAT(ds[nIndex].tinh_id)
        this.lblNhanVien.value = 'Nhân viên YC : ' + (await this.callApiWrapper(api.lay_thongtin_nhanvien, { nhanvien_id: ds[nIndex].nhanvien_id }))[0]?.NAME
        if (ds[nIndex].kieuhd_id != '') this.cboKieu_HD.value = ds[nIndex].kieuhd_id
        else this.cboKieu_HD.value = KIEU_HD.TAI_GD
        this.congnghe_id = 0
        this.txtTenKieuLD.value = ds[nIndex].ten_kieuld
        this.hdkh_id = ds[nIndex].hdkh_id
        this.hdtt_id = 0
        if (!isNullOrEmpty(ds[nIndex].khachhang_id)) this.khachhang_id = ds[nIndex].khachhang_id
        else this.khachhang_id = 0
        this.thanhtoan_id = 0
        this.vma_gd = ds[nIndex].ma_gd
        this.txtMaGD.value = this.vma_gd
        this.txtMaTB.value = ma_tb
        this.txtTenTB.value = this.ChuanHoaTen(ds[nIndex].ten_tb)
        this.txtKhuvuc.value = ds[nIndex].ten_kv
        this.loaitb_id = ds[nIndex].loaitb_id
        this.txtLoaiHinh.value = ds[nIndex].loaihinh_tb
        this.thuebao_id = 0
        if (this.cboLoaiHD.value != LoaiHopDong.DAT_MOI) this.thuebao_id = ds[nIndex].thuebao_id
        this.txtDiaChiLD.value = this.ChuanHoaTen(ds[nIndex].diachi_ld)
        this.txtDiaChiLD_cu.value = ''
        this.txtNoiDungTH.value = ds[nIndex].nd_thuchien
        await this.HienThiControl(this.loaitb_id)
        this.txtNDGiao.value = ds[nIndex].nd_giao
        let dtTemp = await this.callApiWrapper(api.fn_tt_hdtb_dv, { param: this.hdtb_id, type: 1 })
        if (dtTemp.length > 0) this.donvi_id = dtTemp[0].donvi_id
        this.port_id = 0
        this.tramtb_id = 0
        await this.HienThiDSNV(await this.callApiWrapper(api.lay_ds_nhanvien_theo_phieu_id, { phieu_id: this.phieu_id, kieu_id: 2 }))
        if ([96, 98].includes(this.tinh_id)) {
          this.tsbtnGiaoPhieuTinh.enabled = true
        } else {
          if (
            (await this.callApiWrapper(api.kiemtra_nut_chuyentinh_net_v2, {
              vhdtb_id: this.hdtb_id
            })) == 'OK'
          )
            this.tsbtnGiaoPhieuTinh.enabled = true
          else this.tsbtnGiaoPhieuTinh.enabled = false
        }
        this.dtpNgayBG.enabled = true
        this.chkNgayBG.value = true
        if (ds[nIndex].ngay_ht_gp) this.dtpNgayBG.value = getDateStringOrDefault(ds[nIndex].ngay_ht_gp, 'DD/MM/YYYY HH:mm:ss', 'DD/MM/YYYY')
        else this.dtpNgayBG.value = DateTimeHelper.NgayCN()
        // DateTimeHelper.NgayCN().AddSeconds(30)
        //if (PHAILAM(this.luong_id, "GIAOPHIEU"))
        {
          console.log('ds[nIndex].ngay_ht_gp')
          console.log(ds[nIndex].ngay_ht_gp)
          console.log('this.rdoPhieuDaHoanThanh.value')
          console.log(this.rdoPhieuDaHoanThanh.value)
          console.log([null, ''].includes(ds[nIndex].ngay_ht_gp))
          if (![null, ''].includes(ds[nIndex].ngay_ht_gp) && !this.rdoPhieuDaHoanThanh.value) {
            this.tsbtnGiaoPhieu.enabled = true
            this.tsbtnHoanThanh.enabled = true
          } else {
            this.tsbtnGiaoPhieu.enabled = false
            this.tsbtnHoanThanh.enabled = false
          }
        }
        if (await this.PHAILAM(this.luong_id, 'HOANTHIENHOSO')) {
          if (![null, ''].includes(ds[nIndex].ngay_ht_gp)) this.tsbtnHoanThienHS.enabled = true
          else this.tsbtnHoanThienHS.enabled = false
        }
        if (await this.PHAILAM(this.luong_id, 'HOANCONG')) {
          if (![null, ''].includes(ds[nIndex].ngay_ht_gp)) this.tsbtnHoanCong.enabled = true
          else this.tsbtnHoanCong.enabled = false
        }
        this.tsbtnHoanThanh.visible = true
        if (!(await this.PHAILAM(this.luong_id, 'GIAOVIEC'))) {
          this.chkNgayBG.value = true
          this.dtpNgayBG.enabled = true
          this.chkNgayBG.enabled = true
          this.chkNgayGV.value = true
          this.dtpNgayGV.enabled = false
          this.chkNgayGV.enabled = false
        }
        this.dichvuvt_id = ds[nIndex].dichvuvt_id
        if (
          (this.dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT ||
            this.dichvuvt_id == DichVuVienThong.DICHVU_CNTT ||
            this.dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH ||
            this.dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU ||
            this.loaitb_id == LoaiHinhTB.MegaIOT ||
            this.loaitb_id == LoaiHinhTB.MegaWanIOT) &&
          (ds[nIndex].loaihd_id == LoaiHopDong.DAT_MOI || ds[nIndex].loaihd_id == LoaiHopDong.GIAHAN)
        ) {
          this.tsbtnTraPhieuHNTH.enabled = true
          this.tsbtnBSTT.enabled = true
        } else {
          this.tsbtnTraPhieuHNTH.enabled = false
          this.tsbtnBSTT.enabled = false
        }
        let data_ds_dangky_dvgt = await this.callApiWrapper(api.pr_get_ds_dangky_dvgt_v2, {
          vhdtb_id: this.hdtb_id,
          phanvung_id: this.gridDanhSach.value.tinh_id
        })
        await this.HienThiDVGT_BanCheobase(data_ds_dangky_dvgt)
        let data_muatbi = await this.callApiWrapper(api.sp_tt_muatbi_v2, {
          vhdtb_id: this.hdtb_id,
          phanvung_id: this.gridDanhSach.value.tinh_id
        })
        await this.HienThiMuaTBI(data_muatbi)
        await this.HienThiDS_PhieuTH(this.phieu_id)
        await this.HIEN_THONGSO_KYTHUAT()
        await this.NAP_GRD_TIENTRINH()
      } else await this.HienThiDS_PhieuTH(0)
    },
    async HienThiTTHopDongTB_Index(ds, nIndex) {
      if (ds.length > 0) {
        this.hdtb_id = ds[nIndex].hdtb_id
        this.phieu_id = ds[nIndex].phieu_id
        this.hdkh_id = ds[nIndex].hdkh_id
        let ds_tien = []
        this.ds_tien = await this.callApiWrapper(api.lay_tongtien_theo_hdkh_id, {
          hdkh_id: this.hdkh_id
        })
        // if (this.ds_tien.length > 0) this.txtTongTien.value = Convert.ToDecimal(ds_tien[0].tongtien).ToString(Format.FM_TIEN_VND)
        if (this.ds_tien.length > 0) this.txtTongTien.value = ds_tien[0].tongtien
        else this.txtTongTien.value = '0'
        let dstra = []
        dstra = await this.callApiWrapper(api.sp_ht_phieu_tra, { phieu_id: this.phieu_id })
        if (dstra.length > 0) this.txtThongTinTra.value = dstra[0].nd_tra
        else this.txtThongTinTra.value = ''
        this.hdkh_id = ds[nIndex].hdkh_id
        if (!isNullOrEmpty(ds[nIndex].hdtt_id.trim())) this.hdtt_id = ds[nIndex].hdtt_id
        else this.hdtt_id = 0
        if (!isNullOrEmpty(ds[nIndex].khachhang_id.trim())) this.khachhang_id = ds[nIndex].khachhang_id
        else this.khachhang_id = 0
        if (!isNullOrEmpty(ds[nIndex].thanhtoan_id.trim())) this.thanhtoan_id = ds[nIndex].thanhtoan_id
        else this.thanhtoan_id = 0
        this.vma_gd = ds[nIndex].ma_gd
        this.txtMaGD.value = vma_gd
        this.txtMaTB.value = ds[nIndex].ma_tb
        this.txtTenTB.value = this.ChuanHoaTen(ds[nIndex].ten_tb)
        this.txtKhuvuc.value = ds[nIndex].ten_kv
        this.loaitb_id = ds[nIndex].loaitb_id
        this.thuebao_id = 0
        if (this.cboLoaiHD.value != LoaiHopDong.DAT_MOI) this.thuebao_id = ds[nIndex].thuebao_id
        if (ds[nIndex].giukenh == '-1') this.chkLuongGiuKenh.enabled = false
        else {
          this.chkLuongGiuKenh.enabled = true
          this.chkLuongGiuKenh.value = ds[nIndex].giukenh == '1'
        }
        this.txtDiaChiLD.value = this.ChuanHoaTen(ds[nIndex].diachi_ld)
        this.txtNoiDungTH.value = ds[nIndex].nd_thuchien
        await this.HienThiControl(loaitb_id)
        let dtTemp = await this.callApiWrapper(api.fn_tt_hdtb_dv, { param: this.hdtb_id, type: 1 })
        if (dtTemp.length > 0) this.donvi_id = dtTemp[0].donvi_id
        await this.HienThiDSNV(await this.callApiWrapper(api.lay_ds_nhanvien_theo_phieu_id, { phieu_id: this.phieu_id, kieu_id: 2 }))
        this.dtpNgayBG.enabled = true
        this.chkNgayBG.value = true
        if (ds[nIndex].ngay_ht_gp != '') this.dtpNgayBG.value = ds[nIndex].ngay_ht_gp
        else this.dtpNgayBG.value = DateTimeHelper.NgayCN() //.AddSeconds(30)
        if (await this.PHAILAM(this.luong_id, 'GIAOPHIEU')) {
          if (ds[nIndex].ngay_ht_gp != '') this.tsbtnGiaoPhieu.enabled = true
          else this.tsbtnGiaoPhieu.enabled = false
        }
        if (await this.PHAILAM(this.luong_id, 'HOANCONG')) {
          if (ds[nIndex].ngay_ht_gp != '') this.tsbtnHoanCong.enabled = true
          else this.tsbtnHoanCong.enabled = false
        }
        await this.HienThiDVGT(
          await this.callApiWrapper(api.pr_get_ds_dangky_dvgt_v2, {
            vhdtb_id: this.hdtb_id,
            phanvung_id: this.gridDanhSach.value.tinh_id
          })
        )
        await this.HIEN_THONGSO_KYTHUAT()
      }
    },
    async HIEN_THONGSO_KYTHUAT() {
      let dt = await this.callApiWrapper(api.lay_ds_sothuebao_net, {
        vphanvung_id: this.tinh_id,
        vhdtb_id: this.hdtb_id
      })
      this.gridControl1.list = dt
      let dt_repo = []
      // dt_repo.Columns.Add('THUCHIEN')
      // dt_repo.Rows.Add('Thực hiện')
      // dt_repo.Rows.Add('Trả phiếu')
      // repositoryItemLookUpEdit1.DisplayMember = 'THUCHIEN'
      // repositoryItemLookUpEdit1.ValueMember = 'THUCHIEN'
      // repositoryItemLookUpEdit1.list = dt_repo
      // cboKetQuaThucHienHangLoat.Properties.DisplayMember = 'THUCHIEN'
      // cboKetQuaThucHienHangLoat.Properties.ValueMember = 'THUCHIEN'
      this.cboKetQuaThucHienHangLoat.list = dt_repo
    },
    async txtMaGD_KeyPress() {
      if (this.txtMaGD.value && this.txtMaGD.value.length > 0) {
        // rdoPhieu
        let vloai_id = 0
        if (this.rdoPhieu.value == 0) vloai_id = 0
        else vloai_id = 1
        // this.dsIn = await this.callApiWrapper(api.sp_lay_ds_phieu_giao_den, {
        //   vcaidat_dv: 0,
        //   vhuonggiao_id: this.huonggiao_id,
        //   vkieu_id: 0,
        //   vloai_id: vloai_id,
        //   vloaihd_id: this.loaihd_id,
        //   vma_gd: this.txtMaGD.value,
        //   vphieu_id: 0,
        //   vtthd_id: this.tthd_id
        // })
        this.dsIn = await this.callApiWrapper(api.lay_ds_phieu_giaoden_net_v3, {
          vdichvuvt_id: DichVuVienThong.MEGAWAN,
          vtthd_id: this.tthd_id,
          vma_gd: this.txtMaGD.value,
          vphieu_id: 0,
          vloaihd_id: this.loaihd_id,
          vcaidat_dv: 0,
          vkieu_id: 0,
          vhuonggiao_id: this.huonggiao_id,
          vloai_id: vloai_id,
          vngaygiao_tu: this.dpNgayGiaoTu.value ?? '0',
          vngaygiao_den: this.dpNgayGiaoDen.value ?? '0'
        })
        this.dt = this.dsIn
        this.gridDanhSach.list = this.dsIn
      }
    },
    async txtMaTB_KeyPress() {
      if (this.txtMaTB.value != '') {
        let vloai_id = 0
        if (this.rdoPhieuMoi.value) this.vloai_id = 0
        else this.vloai_id = 1
        this.dsIn = await this.callApiWrapper(api.sp_lay_ds_phieu_giao_den, {
          vcaidat_dv: 0,
          vhuonggiao_id: this.huonggiao_id,
          vkieu_id: 0,
          vloai_id: vloai_id,
          vloaihd_id: this.loaihd_id,
          vma_gd: this.txtMaTB.value,
          vphieu_id: 0,
          vtthd_id: this.tthd_id
        })
        this.dt = this.dsIn
        this.gridDanhSach.list = this.dsIn
      }
    },
    async HT_THONGSO_KYTHUAT(tinh_id) {
      try {
        let dt
        if (this.tinh_khdn.includes(tinh_id))
          dt = await this.callApiWrapper(api.bancheo_lay_ds_thongtin_kythuat2, {
            vhdtb_id: this.hdtb_id
          })
        else
          dt = await this.callApiWrapper(api.bancheo_lay_ds_thongtin_kythuat_2, {
            vphanvung_id: tinh_id,
            vhdtb_id: this.hdtb_id
          })
        this.wbrTSKT.value = ''
        for (let dr of dt) {
          this.wbrTSKT.value += `${dr.format_tt.replace('{0}', dr.ten_truong)}${dr.format_gt.replace('{0}', dr.giatri)}`.replaceAll('null', '')
        }
      } catch (ex) {
        this.wbrTSKT.value = `Chưa có dữ liệu; ${ex}`
      }
    },
    async HienThiDS_PhieuTH(phieu_id) {
      // try {
      let myDataTables = null
      if (this.tinh_khdn.includes(this.tinh_id)) {
        myDataTables = await this.callApiWrapper(api.lay_danhsach_phieu_net_con, {
          vphieu_cha_id: phieu_id
        })
      } else {
        myDataTables = await this.callApiWrapper(api.lay_danhsach_phieu_net_con_bancheo, {
          vphieu_cha_id: phieu_id,
          vphanvung_id: this.$root.token.getPhanVungID()
        })
      }
      if (!isNullOrEmpty(myDataTables)) this.dgvChiTiet.list = myDataTables
      //this.dgvChiTiet.list = myDataTables
      // } catch (exp) {
      //   this.$toast.error(`${exp}`)
      // }
    },
    async tsbtnHoanCong_Click() {
      await this.Hoan_Cong()
    },
    async Kiemtra_vattu() {
      // try {
      let dscheck_vattu = await this.callApiWrapper(api.lay_ds_thamso_md_mats, {
        ma_ts: 'KIEMTRA_VATTU_V2'
      })
      if (dscheck_vattu.length > 0) {
        //Thực hiện kiểm tra danh sách vật tư đang sử dụng và đã cấp mới đã có chưa
        //Sau đó mới cho hoàn công
        let ds_vtu = new GiaoPhieu_TbiFacade().Lay_ds_vattu_th(thuebao_id, CURRENT_HDTB_ID)
        if (ds_vtu.length > 0) return true
        return false
      } else return true
      // } catch {
      //   return false
      // }
    },
    async LapThueBaoVaoKetCuoi() {
      try {
        let hdtb = bangts.GetDataSql('select thuebao_id from ' + DatabaseConstants.DB2 + '.hd_thuebao where hdtb_id=' + hdtb_id)
        let vthuebao_id = 0,
          vketcuoi_id = 0,
          vvitri = 0,
          vvitri2 = 0,
          vdoicap = 0,
          vdoicap2 = 0,
          vcap_id = 0,
          vdaucuoi = 0
        if (hdtb.length > 0) {
          // let.TryParse(hdtb.Rows[0].thuebao_id, out vthuebao_id);
          let hdtbCD = new TCHopDongFacade().LAY_DS_HDTB_MGWAN_THEO_HDTB_ID(hdtb_id)
          if (hdtbCD.length > 0 && vthuebao_id != 0) {
            // let.TryParse(hdtbCD.Rows[0].ketcuoi_id, out vketcuoi_id);
            // let.TryParse(hdtbCD.Rows[0].vitri, out vvitri);
            // let.TryParse(hdtbCD.Rows[0].vitri_2, out vvitri2);
            // let.TryParse(hdtbCD.Rows[0].doicap, out vdoicap);
            // let.TryParse(hdtbCD.Rows[0].doicap_2, out vdoicap2);
            // let.TryParse(hdtbCD.Rows[0].cap_id, out vcap_id);
            new KhaiThacBaoDuongFacade().Lap_dbtb_vao_ketcuoi(vthuebao_id, DichVuVienThong.MEGAWAN, vketcuoi_id, vvitri, vvitri2, vdoicap, vdoicap2, vcap_id, vdaucuoi, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip)
          }
        }
      } catch {}
    },
    async KiemTra_DL(luong_id) {
      if (this.gridDanhSach.list.length <= 0) {
        this.$toast.warning('Không có thuê bao trên lưới !')
        return false
      }
      if (this.cboNguoiGV.value == '') {
        this.$toast.warning('Hãy nhập người giao việc')
        // cboNguoiGV.Focus()
        return false
      }
      if (!this.chkNgayGV.value) {
        this.$toast.warning('Hãy nhập ngày giao việc!')
        // chkNgayGV.Focus()
        return false
      }
      if (!this.chkNgayBG.value) {
        this.$toast.warning('Hãy nhập ngày hoàn thành!')
        // chkNgayBG.Focus()
        return false
      }
      if (await this.PHAILAM(this.luong_id, 'GIAOVIEC')) {
        if (this.gridNhanVien.list.length <= 0) {
          this.$toast.warning('Hãy nhập nhân viên thực hiện  !')
          return false
        }
      }
      return true
    },
    async tsbtnChapNhan_Click() {
      // try {
      if (this.gridDanhSach_getSelectedRecords().length == 0) {
        this.$toast.warning('Chưa tích chọn phiếu')
        return
      }
      let rows = this.gridDanhSach_getSelectedRecords()
      let count = 0
      for (let row of rows) {
        if (!(await this.Cap_Nhat_Row(row))) {
          return
        }
        count++
      }
      this.$toast.success(`Cập nhật ${count} phiếu lên viễn thông tỉnh thành công`)
      await this.HienThiDanhSachHDTB()
      // } catch (ex) {
      //   this.$toast.error(`${ex}`)
      // }
    },
    async Cap_Nhat() {
      let dr = {}
      dr.LUONG_ID = this.luong_id
      dr.PORT_ID = this.port_id
      // dr.VCI_VPI_ID = this.ThongTinHC.vci_vpi_id,
      dr.HDTB_ID = this.hdtb_id
      dr.LOAIHD_ID = this.loaihd_id
      // dr.KIEULD_ID = this.DsThueBao.kieu_ld_id,
      dr.NHANVIEN_ID = this.$root.token.getNhanVienID()
      dr.PHIEU_ID = this.phieu_id
      dr.NGAY_BG = this.dtpNgayBG.value ? moment(this.dtpNgayBG.value, 'DD/MM/YYYY hh:mm A').format('DD/MM/YYYY hh:mm:ss') : ''
      dr.NGAY_HT = this.dtpNgayBG.value ? moment(this.dtpNgayBG.value, 'DD/MM/YYYY hh:mm A').format('DD/MM/YYYY hh:mm:ss') : ''
      dr.NGAY_GV = this.dtpNgayGV.value ? moment(this.dtpNgayGV.value, 'DD/MM/YYYY hh:mm A').format('DD/MM/YYYY hh:mm:ss') : ''
      dr.NGUOI_GV = this.cboNguoiGV.value
      dr.VMATB = this.txtMaTB.value
      dr.MAY_CN = 'web'
      dr.NGUOI_CN = this.$root.token.getUserName()
      // dr.CBOROUTER = this.router_id,
      // dr.SLOT_PE = this.ThongTinHC.txtSlotPE,
      // dr.PORT_PE = this.ThongTinHC.txtPortPE,
      // dr.WAN_IP = this.ThongTinHC.txtWanIP,
      // dr.LAN_IP = this.ThongTinHC.txtLanIP,
      // dr.TEXT_TBI = this.thietbi_id != null ? (this.DmThietBi.filter(x=>x.id == this.thietbi_id).length > 0 ? this.DmThietBi.filter(x=>x.id == this.thietbi_id)[0].text : '') : '',
      // dr.DSLAMWAN = this.ThongTinHC.txtDsLam,
      dr.VRF = row.vrf_vfi
      // dr.LINK = this.ThongTinHC.txtLink,
      // dr.SERIAL = this.ThongTinHC.txtSerial
      // dr.ROUTE = this.ThongTinHC.txtRoute,
      // dr.SVLAN = this.ThongTinHC.txtSvLan,
      // dr.CVLAN = this.ThongTinHC.txtCvLan,
      // dr.LOAITBIWAN = this.loaitbi_id,
      dr.CONGNGHE_ID = this.congnghe_id
      // dr.DOICAPNGON = this.txtDoiCapNgon,
      // dr.DOICAPGOC = this.txtDoiCapGoc,
      // dr.TUYENCAP = this.txtTuyenCap,
      // dr.LIENTU = this.txtLienTu,
      // dr.HOPCAPNGON = this.txtHopCapNgon,
      // dr.TUCAPNGON = this.txtTuCapNgon,
      // dr.LOAICAP = this.loaicap_id,
      // dr.CAPGOC = this.txtCapGoc,
      // dr.CAPNGON = this.txtCapNgon,
      // dr.TUCAPGOC = this.txtTuCapNgon,
      // dr.SOMETDAY = this.txtCuLy,
      // dr.LOAISOWAN = this.loainode_id,
      // dr.IN_HDTB_ID_CHA = this.DsThueBao.hdtb_cha_id,
      // dr.IN_THUEBAO_ID_CHA = this.DsThueBao.thuebao_cha_id,
      // dr.HUONGKN = this.ThongTinHC.huongkn,
      // dr.TINH_KN_ID = this.ThongTinHC.tinh_kn_id
      let pdata = { param: JSON.stringify(dr) }
      // try {
      let kq = await this.callApiWrapper(api.fn_capnhat_hoancongmegawan_net, pdata)
      kq = JSON.parse(kq)
      if (kq.ERROR_CODE == 1) {
        return true
      } else {
        this.$toast.error(`Lỗi phiếu:  ${kq.MESSAGE}`)
        return false
      }
      // } catch (e) {
      //   this.$toast.error(`${kq.MESSAGE}`)
      // }
    },
    async Cap_Nhat_Row(row) {
      let dr = {}
      dr.LUONG_ID = this.luong_id
      dr.PORT_ID = this.port_id
      // dr.VCI_VPI_ID = this.ThongTinHC.vci_vpi_id,
      dr.HDTB_ID = row.hdtb_id
      dr.LOAIHD_ID = row.loaihd_id
      // dr.KIEULD_ID = this.DsThueBao.kieu_ld_id,
      dr.NHANVIEN_ID = this.$root.token.getNhanVienID()
      dr.PHIEU_ID = row.phieu_id
      dr.NGAY_BG = this.dtpNgayBG.value ? moment(this.dtpNgayBG.value, 'DD/MM/YYYY hh:mm A').format('DD/MM/YYYY hh:mm:ss') : ''
      dr.NGAY_HT = this.dtpNgayBG.value ? moment(this.dtpNgayBG.value, 'DD/MM/YYYY hh:mm A').format('DD/MM/YYYY hh:mm:ss') : ''
      dr.NGAY_GV = this.dtpNgayGV.value ? moment(this.dtpNgayGV.value, 'DD/MM/YYYY hh:mm A').format('DD/MM/YYYY hh:mm:ss') : ''
      dr.NGUOI_GV = this.cboNguoiGV.value
      dr.VMATB = this.txtMaTB.value
      dr.MAY_CN = 'web'
      dr.NGUOI_CN = this.$root.token.getUserName()
      // dr.CBOROUTER = this.router_id,
      // dr.SLOT_PE = this.ThongTinHC.txtSlotPE,
      // dr.PORT_PE = this.ThongTinHC.txtPortPE,
      // dr.WAN_IP = this.ThongTinHC.txtWanIP,
      // dr.LAN_IP = this.ThongTinHC.txtLanIP,
      // dr.TEXT_TBI = this.thietbi_id != null ? (this.DmThietBi.filter(x=>x.id == this.thietbi_id).length > 0 ? this.DmThietBi.filter(x=>x.id == this.thietbi_id)[0].text : '') : '',
      // dr.DSLAMWAN = this.ThongTinHC.txtDsLam,
      dr.VRF = row.vrf_vfi
      // dr.LINK = this.ThongTinHC.txtLink,
      // dr.SERIAL = this.ThongTinHC.txtSerial
      // dr.ROUTE = this.ThongTinHC.txtRoute,
      // dr.SVLAN = this.ThongTinHC.txtSvLan,
      // dr.CVLAN = this.ThongTinHC.txtCvLan,
      // dr.LOAITBIWAN = this.loaitbi_id,
      dr.CONGNGHE_ID = this.congnghe_id
      // dr.DOICAPNGON = this.txtDoiCapNgon,
      // dr.DOICAPGOC = this.txtDoiCapGoc,
      // dr.TUYENCAP = this.txtTuyenCap,
      // dr.LIENTU = this.txtLienTu,
      // dr.HOPCAPNGON = this.txtHopCapNgon,
      // dr.TUCAPNGON = this.txtTuCapNgon,
      // dr.LOAICAP = this.loaicap_id,
      // dr.CAPGOC = this.txtCapGoc,
      // dr.CAPNGON = this.txtCapNgon,
      // dr.TUCAPGOC = this.txtTuCapNgon,
      // dr.SOMETDAY = this.txtCuLy,
      // dr.LOAISOWAN = this.loainode_id,
      // dr.IN_HDTB_ID_CHA = this.DsThueBao.hdtb_cha_id,
      // dr.IN_THUEBAO_ID_CHA = this.DsThueBao.thuebao_cha_id,
      // dr.HUONGKN = this.ThongTinHC.huongkn,
      // dr.TINH_KN_ID = this.ThongTinHC.tinh_kn_id
      let pdata = { param: JSON.stringify(dr) }
      // try {
      let kq = await this.callApiWrapper(api.fn_capnhat_hoancongmegawan_net, pdata)
      kq = JSON.parse(kq)
      if (kq.ERROR_CODE == 1) {
        return true
      } else {
        this.$toast.error(`${kq.MESSAGE}`)
        return false
      }
      // } catch (e) {
      //   this.$toast.error(`${kq.MESSAGE}`)
      // }
    },
    async cboLoaiHD_SelectedValueChanged() {
      // try {
      if (this.cboLoaiHD.list.length > 0) {
        this.loaihd_id = this.cboLoaiHD.value
        let ds = await this.callApiWrapper(api.sp_lay_ds_quytrinh_v2, {
          dsloaihd_id: this.dsloaihd_id,
          dichvuvt_id: this.dsdichvuvt_id,
          tthd_id: this.tthd_id,
          kieu: 0
        })
        if (ds.length > 0) {
          this.cboQuyTrinh.list = ds
          if (this._vquytrinh_id != 0) {
            this.cboQuyTrinh.value = this._vquytrinh_id
          }
        } else {
          this.quytrinh_id = -1
          this.cboQuyTrinh.list = []
          this.cboQuyTrinh.value = 0
        }
        if (this.cboLoaiHD.value == LoaiHopDong.DAT_MOI || this.cboLoaiHD.value == LoaiHopDong.DI_CHUYEN) {
          this.chkLayTSo.enabled = true
          this.btnPhieuTC.enabled = true
        } else {
          this.chkLayTSo.enabled = false
          this.chkLayTSo.value = false
          this.btnPhieuTC.enabled = false
        }
      }
      // } catch (exp) {
      //   new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true)
      // }
    },
    async tsbtnGiaoPhieu_Click() {
      await this.GiaoPhieu()
    },
    async GiaoPhieu() {
      try {
        if (!isNullOrEmpty(this.txtMaGD.value.trim())) {
          if (!this.KiemTra_PhieuCon_DaHoanThanh(this.phieu_id)) return
          let vhuonggiao_id = 0
          if (PHAILAM(this.luong_id, 'DIEUKIEN_GIAOPHIEU_BP_TIEPTHEO')) {
            vhuonggiao_id = new BangTuDienFacade().Lay_idx_huonggiao_theoquytrinh_id(quytrinh_id, hdtb_id, 0)
          }
          if (vhuonggiao_id == 0) {
            let str = ''
            str += ' select b.huonggiao_id from ' + DatabaseConstants.DB2 + '.huonggiao_ldv a, ' + DatabaseConstants.DB2 + '.huonggiao_ldv b, ' + DatabaseConstants.DB2 + '.quytrinh c'
            str += ' where a.loaidv_dich_id = b.loaidv_nguon_id'
            str += ' and a.quytrinh_id = c.quytrinh_id'
            str += ' and c.quytrinh_id =  b.quytrinh_id'
            str += ' and a.luong_id = ' + luong_id
            let dtTemp = bangts.GetDataSql(str)
            if (dtTemp.length > 0) vhuonggiao_id = dtTemp.Rows[0].huonggiao_id
          }
          if (vhuonggiao_id == 0) {
            this.$toast.success('Chưa có hướng giao được gán !')
            return
          }
          let gp = 0
          if (PHAILAM(this.luong_id, 'TUDONG_GIAOPHIEU_DUYET_CAPVT')) {
            try {
              gp = 1
            } catch (Exception) {
              gp = 0
            }
          }
          // frmGiaoPhieu f = new frmGiaoPhieu(txtMaGD.value, 1, vhuonggiao_id, hdtb_id, 0, "0");
          // f.luong_id = luong_id;
          // f.taoduyet = gp;
          // f.idx_loaihd_id = cboLoaiHD.value;
          // f.idx_quytrinh_id = cboQuyTrinh.value;
          // f.idx_dichvuvt_id = CURRENT_DICHVUVT_ID;
          // f.ShowDialog();
        }
        let vma_tb = txtMaTB.value.trim()
        await this.HienThiDanhSachHDTB()
      } catch (ex) {
        this.$toast.error('Có lỗi: ' + ex.Message)
      }
    },
    async btnLayTTMoi_Click() {
      await this.HienThiDanhSachHDTB()
    },
    async ShowBaoCao(tenfile) {},
    async KiemTraDK_HoanThanh(
      thangnam_tt,
      thangnam_sys //thangnam_sys= ngay_ht
    ) {
      let result = false
      if (thangnam_tt.substring(6, thangnam_tt.length - 6) < thangnam_sys.substring(6, thangnam_sys.length - 6))
        //So sánh năm
        return true
      //Nếu năm thanh toán < năm hoàn thành thì true;
      else if (thangnam_tt.substring(6, thangnam_tt.length - 6) == thangnam_sys.substring(6, thangnam_sys.length - 6)) {
        //So sánh năm
        //Nêu năm = nhau thì kiểm tra tiếp
        //Nếu tháng tt < thang ht -> true
        if (thangnam_tt.substring(3, 2) < thangnam_sys.substring(3, 2)) result = true
        else if (thangnam_tt.substring(3, 2) == thangnam_sys.substring(3, 2))
          if (thangnam_tt.substring(0, 2) <= thangnam_sys.substring(0, 2))
            //Nếu tháng & năm = nhau thì kiểm tra ngày
            return true
          else return false
        else result = false //tháng tt lớn hơn thang ht
      } else result = false
      return result
    },
    async btnPhieuGiaoSai_Click() {
      await this.TraPhieu()
    },
    async TraPhieu() {
      this.frmTraPhieuTC.input = {
        isPopup: true
      }
      this.ejsDialog.name = 'frmTraPhieuTC'
      this.ejsDialog.header = 'TRẢ PHIẾU THI CÔNG'
      this.$refs.ejsDialog.show()
      setTimeout(() => {
        this.$refs.frmTraPhieuTC.frmTraPhieuTC(this.gridDanhSach.value.ma_gd, this.cboLoaiHD.value, this.gridDanhSach.value.dichvuvt_id, this.$root.token.getDonViID(), this.txtMaTB.value, '0', '0', 3)
      }, 500)
      // try {
      //   frmTraPhieuTC frm = new frmTraPhieuTC(txtMaTB.value.trim(), cboLoaiHD.value, CURRENT_DICHVUVT_ID, tt_nd.donvi_id,
      //                                           txtMaTB.value.trim(), "0", "0", 3);
      //   frm.ShowDialog();
      //   if (frm.kt_thuchien)
      //   {
      //       let vma_tb = txtMaTB.value.trim();
      //       HienThiDanhSachHDTB();
      //       // Tìm kiếm row vừa cập nhật
      //       gridDanhSach.FocusedRowHandle = FindGridIndexByValue("MA_TB", vma_tb);
      //   }
      // } catch (ex) {
      //   this.$toast.error(`${ex}`)
      // }
    },
    async tsbtnGiaoViec_Click() {
      await this.GiaoViec()
    },
    async GiaoViec() {
      if (this.gridDanhSach.list.length <= 0) {
        this.$toast.warning('Chưa có danh thuê bao. Bạn hãy kiểm tra lại! ')
        return
      }
      if (this.cboNguoiGV.list.length <= 0 || this.cboNguoiGV.value == null) {
        this.$toast.warning('Hãy nhập nhân viên giao việc!')
        // cboNguoiGV.Focus()
        return
      }
      if (!this.chkNgayGV.value) {
        this.$toast.warning('Hãy nhập ngày giao việc!')
        // chkNgayGV.Focus()
        return
      }
      let vnhanvien_giao_id = 0
      let vngaygiao
      vnhanvien_giao_id = this.cboNguoiGV.value
      vngaygiao = this.dtpNgayGV.value
      this.$refs.frnGiaoPhieuNV.frnGiaoPhieuNV(this.phieu_id, this.$root.token.getDonViID(), vnhanvien_giao_id, vngaygiao)
      this.$refs.frnGiaoPhieuNV.vma_tb = this.txtMaTB.value?.trim()
      this.$refs.frnGiaoPhieuNV.vhdtb_id = this.hdtb_id
      this.$refs.frnGiaoPhieuNV.huonggiao_id = this.huonggiao_id
      this.$nextTick(() => {
        this.$refs.frnGiaoPhieuNV.load()
        this.$refs.popupfrnGiaoPhieuNV.show()
      })
    },
    async btnLayMaGD_Click() {
      this.ejsDialog.name = 'frmTraCuuHopDong'
      this.ejsDialog.header = 'Nhập nội dung tiến trình thi công'
      this.$refs.ejsDialog.show()
      // try {
      //   // frmTraCuuHopDong f = new frmTraCuuHopDong(LoaiHopDong.DAT_MOI, tthd_id, 0, 0, "0", 1, tt_nd.donvi_dl_id, 0);
      //   // f.ShowDialog();
      //   this.frmNoiDung.input = {
      //   row: this.gridDanhSach.value
      // }
      //   if (f.chapnhan) {
      //     this.vma_gd = f.ma_gd
      //     this.txtMaGD.value = this.vma_gd
      //     // txtMaGD.Focus()
      //     if (this.txtMaGD.value != '') {
      //       let vloai_id = 0
      //       if (this.rdoPhieuMoi.value) {
      //         vloai_id = 0
      //       } else {
      //         vloai_id = 1
      //       }
      //       await this.HienThiTTHopDongTB(
      //         await this.callApiWrapper(api.lay_ds_phieu_giaoden_net_v3, {
      //           vdichvuvt_id: DichVuVienThong.MEGAWAN,
      //           vtthd_id: this.tthd_id,
      //           vma_gd: this.txtMaGD.value,
      //           vphieu_id: 0,
      //           vloaihd_id: this.cboLoaiHD.value,
      //           vcaidat_dv: 1,
      //           vkieu_id: 0,
      //           vhuonggiao_id: this.huonggiao_id,
      //           vloai_id: this.vloai_id
      //         })
      //       )
      //     }
      //   }
      // } catch (ex) {
      //   this.$toast.error(`Có lỗi: ${ex}`)
      // }
    },
    async tsbtnHoanThanh_Click() {
      console.log('tsbtnHoanThanh_Click')
      console.log(this.gridDanhSach_getSelectedRecords())
      if (this.gridDanhSach_getSelectedRecords().length == 0) {
        this.$toast.warning('Chưa tích chọn phiếu')
        return
      }
      await this.Hoan_Thanh()
      await this.btnLayTTMoi_Click()
      // if(records.length > 1) {
      //   if (!confirm(`Bạn có muốn kích hoạt cho ${records.length} thuê bao này không?`)) return
      //   for(let record of records)  {
      //     await this.Hoan_Thanh_By_Phieu(record)
      //   }
      // } else {
      // }
    },
    async Hoan_Thanh_By_Phieu(phieu) {
      // if (!confirm('Bạn có muốn kích hoạt cho thuê bao này không?')) return
      this.loading(true)
      let kq = await api.hoanthanh(this.axios, {
        gvDanhSach_Chon: this.gridDanhSach_getSelectedRecords(),
        tinh_id: this.tinh_id,
        luong_id: this.luong_id,
        dichvuvt_id: this.dichvuvt_id,
        loaihd_id: this.loaihd_id,
        txtMaTB: this.txtMaTB.value
      })
      this.loading(false)
      if (kq.data.error_code == 'BSS-00000000') {
        this.$toast.success(`${kq.data.data}`)
        await this.HienThiDanhSachHDTB()
      } else {
        this.$toast.warning(`${kq.data.message}`)
      }
    },
    async Hoan_Thanh() {
      if (!confirm('Bạn có muốn kích hoạt cho thuê bao này không?')) return
      try {
        // let records = this.gridDanhSach.list
        //   .filter((e) => e.selected && e.selected == true)
        //   .map((e) => {
        //     delete e.selected
        //     return e
        //   })
        this.loading(true)
        let kq = await api.hoanthanh(this.axios, {
          gvDanhSach_Chon: this.gridDanhSach_getSelectedRecords(),
          tinh_id: this.tinh_id,
          luong_id: this.luong_id,
          dichvuvt_id: this.dichvuvt_id,
          loaihd_id: this.loaihd_id,
          txtMaTB: this.txtMaTB.value
        })
        this.loading(false)
        if (kq.data.error_code == 'BSS-00000000') {
          this.$toast.success(`${kq.data.data}`)
          await this.HienThiDanhSachHDTB()
        } else {
          this.$toast.warning(`${kq.data.message}`)
        }
      } catch (e) {
        console.log('FUNCTION_ERROR: Hoan_Thanh')
        console.log(e)
      }
    },
    async HienThiDSNV(ds) {
      //lvwNhanVien.Items.Clear();
      let myDataTables = ds
      if (myDataTables.length > 0) {
        this.chkNgayGV.value = true
        this.dtpNgayGV.value = ds[0].ngaygiao
        this.cboNguoiGV.value = ds[0].nhanvien_giao_id
        this.gridNhanVien.list = ds
        this.nhanvien_tc_id = ds[0].nhanvien_th_id
        this.congviec_th = ds[0].nhiemvu
      } else {
        this.gridNhanVien.list = []
        if (!(await this.PHAILAM(this.luong_id, 'GIAOVIEC'))) this.chkNgayGV.value = true
        else chkNgayGV.value = false
        this.dtpNgayGV.value = DateTimeHelper.NgayCN()
        this.cboNguoiGV.value = ''
        this.cboNguoiGV.value = this.$root.token.getNhanVienID()
        this.nhanvien_tc_id = -1
        this.congviec_th = ''
      }
    },
    async KiemTra_PhieuCon_DaHoanThanh(vphieu_id) {
      let dtTemp = await this.callApiWrapper(api.kiemtra_trangthai_hoanthanh_phieu_net, {
        vphieu_id: vphieu_id
      })
      if (dtTemp.length > 0) {
        this.$toast.warning('Các phiếu con chưa hoàn thành!')
        return false
      }
      return true
    },
    async UPDATE_HDKH_VINAPHONE() {
      // let status = false;
      // try
      // {
      //     let nIndex = LayIndexTheoPhieuID(CURRENT_PHIEU_ID, dsIn);
      //     strerrorVinaphone = "";
      //     XmlNode result;
      //     CheckDataFacade checkdata = new CheckDataFacade();
      //     VINAService vina = new VINAService();
      //     let PSUSERID = CommonFunction.ChuanHoa_XML(checkdata.MAP_ID("user_neo", DatabaseConstants.DB1 + ".nguoidung", "where nguoidung_id =" + tt_nd.nguoidung_id));
      //     if (PSUSERID == "-1")
      //     {
      //         this.$toast.warning("Thông tin người dùng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.");
      //         strerrorVinaphone = "error";
      //         return false;
      //     }
      //     let PSMAKHCU = dsIn[nIndex].ma_tt.trim();
      //     let PSMA_KH = dsIn[nIndex].ma_tt.trim();
      //     let PSMACQ = "";
      //     PSMACQ = Search_MaCQ_Vinaphone(PSUSERID, dsIn[nIndex].ma_tt.trim());
      //     let PSCOQUAN = CommonFunction.ChuanHoa_XML(dsIn[nIndex].ten_kh);
      //     //Tên thanh toán
      //     let PSTENTT = CommonFunction.ChuanHoa_XML(dsIn[nIndex].ten_tt.trim());
      //     let PSSODAIDIEN = dsIn[nIndex].matb_dd.trim();
      //     let PSNGUOIDAIDIEN = CommonFunction.ChuanHoa_XML(dsIn[nIndex].nguoi_dd);//danh bạ
      //     let PSDIENTHOAILH = CommonFunction.ChuanHoa_XML(dsIn[nIndex].so_dt);//danh bạ
      //     let PSPHAI = dsIn[nIndex].gioitinh;//Lấy dữ liệu từ danh bạ
      //     let PSEMAIL = CommonFunction.ChuanHoa_XML(dsIn[nIndex].email);//danh bạ
      //     let PSNGAYSINH = "";
      //     PSNGAYSINH = Convert.ToDateTime(dsIn[nIndex].ngay_sn.trim()).ToString("dd/MM/yyyy");
      //     let PSSOGIAYTO = CommonFunction.ChuanHoa_XML(dsIn[nIndex].so_gt.trim());
      //     let PSNOICAPGT = CommonFunction.ChuanHoa_XML(dsIn[nIndex].noicap.trim());//danh bạ
      //     let PSNGAYCAPGT = "";
      //     PSNGAYCAPGT = Convert.ToDateTime(dsIn[nIndex].ngaycap.trim()).ToString("dd/MM/yyyy");
      //     let PSSOGT1 = "";
      //     let PSNOICAPGT1 = "";
      //     let PSNGAYCAPGT1 = "";
      //     let PSUANCT_ID = checkdata.MAP_ID("quan_id_neo", DatabaseConstants.DB2 + ".quan", "where quan_id =" + quankh_id);
      //     if (PSUANCT_ID == "-1")
      //     {
      //         this.$toast.warning("Thông tin về Quận/Huyện  của Địa chỉ khách hàng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.");
      //         strerrorVinaphone = "error";
      //         return false;
      //     }
      //     let PSHUONGCT_ID = checkdata.MAP_ID("phuong_id_neo", DatabaseConstants.DB2 + ".phuong", "where quan_id =" + quankh_id + " and phuong_id = " + phuongkh_id);
      //     if (PSHUONGCT_ID == "-1")
      //     {
      //         this.$toast.warning("Thông tin về Phường/Xã của Địa chỉ khách hàng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.");
      //         strerrorVinaphone = "error";
      //         return false;
      //     }
      //     let PSHOCT_ID = checkdata.MAP_ID("a.pho_id_neo", DatabaseConstants.DB2 + ".pho a, " + DatabaseConstants.DB2 + ".ma_pho b", " where a.pho_id = b.pho_id and  b.phuong_id = " + phuongkh_id + " and a.pho_id = " + phokh_id);
      //     if (PSHOCT_ID == "-1")
      //     {
      //         this.$toast.warning("Thông tin về Phố/Thôn/Xóm của Địa chỉ khách hàng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.");
      //         strerrorVinaphone = "error";
      //         return false;
      //     }
      //     let PSONHACT_ID = CommonFunction.ChuanHoa_XML(sonhakh);
      //     let PSDIACHICT = CommonFunction.ChuanHoa_XML(dsIn[nIndex].diachi_kh.trim());
      //     let PSMSTHUE = CommonFunction.ChuanHoa_XML(dsIn[nIndex].mst.trim());
      //     let PSTAIKHOAN = CommonFunction.ChuanHoa_XML(dsIn[nIndex].stk.trim());
      //     let PSUANTT_ID = checkdata.MAP_ID("quan_id_neo", DatabaseConstants.DB2 + ".quan", "where quan_id =" + quantt_id);
      //     if (PSUANTT_ID == "-1")
      //     {
      //         this.$toast.warning("Thông tin về Quận/Huyện của Địa chỉ Thanh toán chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.");
      //         strerrorVinaphone = "error";
      //         return false;
      //     }
      //     let PSHUONGTT_ID = checkdata.MAP_ID("phuong_id_neo", DatabaseConstants.DB2 + ".phuong", "where quan_id =" + quantt_id + " and phuong_id = " + phuongtt_id);
      //     if (PSHUONGTT_ID == "-1")
      //     {
      //         this.$toast.warning("Thông tin về Phường/Xã của Địa chỉ Thanh toán chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.");
      //         strerrorVinaphone = "error";
      //         return false;
      //     }
      //     let PSHOTT_ID = checkdata.MAP_ID("a.pho_id_neo", DatabaseConstants.DB2 + ".pho a, " + DatabaseConstants.DB2 + ".ma_pho b", " where a.pho_id = b.pho_id and  b.phuong_id = " + phuongtt_id + " and a.pho_id = " + phott_id);
      //     if (PSHOTT_ID == "-1")
      //     {
      //         this.$toast.warning("Thông tin Phố/Thôn/Xóm của Địa chỉ Thanh toán chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.");
      //         strerrorVinaphone = "error";
      //         return false;
      //     }
      //     let PSONHATT_ID = CommonFunction.ChuanHoa_XML(sonhatt);
      //     let PSDIACHITT = CommonFunction.ChuanHoa_XML(dsIn[nIndex].diachi_tt.trim());
      //     let PSDANGKYTV = "0";
      //     let PSDANGKYDB = "1";//Bổ sung sau
      //     let PSKHRR = "0";
      //     let PSNGUOICN = CommonFunction.ChuanHoa_XML(checkdata.MAP_ID("user_neo", DatabaseConstants.DB1 + ".nguoidung", "where nguoidung_id =" + tt_nd.nguoidung_id));
      //     let PSMAYCN = CommonFunction.GetIPAndComputerName();
      //     let PSMANV = dsIn[nIndex].manv_tc.trim();
      //     //Lấy tên nhân viên thu cước
      //     let ten_nv = "";
      //     ten_nv = tcdanhba.LAY_TT_NHANVIEN_TC(PSMANV)[0].tennv_tc;
      //     let PSTENNV = ten_nv;
      //     let PSGHICHU = CommonFunction.ChuanHoa_XML(dsIn[nIndex].ghichu_tt.trim());
      //     let PSLOAIGTID = checkdata.MAP_ID("loaigt_id_neo", DatabaseConstants.DB2 + ".loai_gt", "where loaigt_id = '" + dsIn[nIndex].loaigt_id.trim() + "' ");
      //     let PSLOAIGTID1 = "1";
      //     let PSLOAIKH = checkdata.MAP_ID("loaikh_id_neo", DatabaseConstants.DB2 + ".loai_kh", "where loaikh_id ='" + dsIn[nIndex].loaikh_id.trim() + "'");//danh bạ
      //     let PSDIADIEMTT = checkdata.MAP_ID("httt_id_neo", DatabaseConstants.DB2 + ".hinhthuc_tt", "where httt_id = '" + dsIn[nIndex].httt_id.trim() + "'");
      //     let PSNGANHANG = "";
      //     let PSCHUYENKHOAN = "0";
      //     let PSDONVIQL = checkdata.MAP_ID("donvi_id_neo", DatabaseConstants.DB1 + ".donvi", "where donvi_id =" + dsIn[nIndex].donviql_id.trim());
      //     if (PSDONVIQL == "-1")
      //     {
      //         this.$toast.warning("Thông tin về đơn vị quản lý chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.");
      //         strerrorVinaphone = "error";
      //         return false;
      //     }
      //     let PSMABC = checkdata.MAP_ID("mabc_id_neo", DatabaseConstants.DB1 + ".donvi", "where donvi_id =" + dsIn[nIndex].donviql_id.trim());
      //     if (PSMABC == "-1")
      //     {
      //         this.$toast.warning("Thông tin về mã bưu cục chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.");
      //         strerrorVinaphone = "error";
      //         return false;
      //     }
      //     let PSKHLON = "1";
      //     let PSUUTIEN = "0";
      //     let PSNGANHNGHE = checkdata.MAP_ID("tnc2_id_neo", DatabaseConstants.DB2 + ".tieunganh_c2", "where tnc2_id =" + dsIn[nIndex].tnc2_id.trim()); //nganhnghe_id;
      //     let PSDOIDVQLTB = "0";
      //     let PSMAT = CommonFunction.ChuanHoa_XML(checkdata.MAP_ID("ma_tuyenthu", DatabaseConstants.DB2 + ".tuyenthu", "where tuyenthu_id ='" + dsIn[nIndex].tuyenthu_id.trim() + "'"));
      //     let PSTENT = PSMANV;
      //     let PSKYTEN = "";
      //     let PSNGUOI_GT = "";
      //     result = vina.PTTB_CAPNHAT_CAPNHAT_KH_PTTB1(PSUSERID,
      //              PSMAKHCU, PSMA_KH, PSMACQ, PSCOQUAN, PSTENTT,
      //              PSSODAIDIEN, PSNGUOIDAIDIEN, PSDIENTHOAILH, PSPHAI, PSEMAIL,
      //              PSNGAYSINH, PSSOGIAYTO, PSNOICAPGT, PSNGAYCAPGT, PSSOGT1,
      //              PSNOICAPGT1, PSNGAYCAPGT1, PSUANCT_ID, PSHUONGCT_ID, PSHOCT_ID,
      //              PSONHACT_ID, PSDIACHICT, PSMSTHUE, PSTAIKHOAN, PSUANTT_ID,
      //              PSHUONGTT_ID, PSHOTT_ID, PSONHATT_ID, PSDIACHITT, PSDANGKYTV,
      //              PSDANGKYDB, PSKHRR, PSNGUOICN, PSMAYCN, PSMANV,
      //              PSTENNV, PSGHICHU, PSLOAIGTID, PSLOAIGTID1, PSLOAIKH,
      //              PSDIADIEMTT, PSNGANHANG, PSCHUYENKHOAN, PSDONVIQL, PSMABC,
      //              PSKHLON, PSUUTIEN, PSNGANHNGHE, PSDOIDVQLTB, PSMAT,
      //              PSTENT, PSKYTEN, PSNGUOI_GT);
      //     foreach (XmlNode childNode in result.ChildNodes)
      //     {
      //         if (childNode.Name.toUpperCase() == "CAPNHAT_KH_PTTB2")
      //         {
      //             if (PSMACQ == childNode.InnerText)
      //             {
      //                 status = true;
      //                 return status;
      //             }
      //             else
      //             {
      //                 status = false;
      //                 strerrorVinaphone = childNode.InnerText;
      //             }
      //         }
      //         else if (childNode.Name.toUpperCase() == "ERROR")
      //         {
      //             status = false;
      //             strerrorVinaphone = childNode.InnerText;
      //             return status;
      //         }
      //     }
      // }
      // catch (ex)
      // {
      //     status = false;
      //     strerrorVinaphone = ex;
      //     new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, ex.Message, ex), false);
      // }
      // return status;
    },
    async CO_THUEBAO_DIDONG(thanhtoan_id) {
      let kq = false
      try {
        let str = ''
        str += ' select a.* from ' + DatabaseConstants.DB2 + '.db_thuebao a'
        str += ' where a.dichvuvt_id = ' + DichVuVienThong.DI_DONG
        str += '    and a.thanhtoan_id = ' + thanhtoan_id
        str += '    and a.trangthaitb_id not in (' + TrangThaiTB.THANHLY + ', ' + TrangThaiTB.DOISO + ' )'
        let ds = bangts.GetSQL(str, '')
        if (ds.length > 0) kq = true
        else kq = false
      } catch (ex) {
        return false
      }
      return kq
    },
    async Search_MaCQ_Vinaphone(PSUSERID, ma_tt) {
      // XmlNode result;
      // VINAService vina = new VINAService();
      // let Ma_cq = "";
      // result = vina.PTTB_CAPNHAT_LAYTT_KH(PSUSERID, "ma_kh", ma_tt);
      // foreach (XmlNode childNode in result.ChildNodes)
      // {
      //     if (childNode.Name.toUpperCase() == "LAYTT_KH")
      //     {
      //         foreach (XmlNode child_childNode in childNode.ChildNodes)
      //         {
      //             if (child_childNode.Name.toUpperCase() == "MA_CQ")
      //             {
      //                 Ma_cq = child_childNode.InnerText;
      //             }
      //         }
      //     }
      //     else if (childNode.Name.toUpperCase() == "ERROR")
      //     {
      //         Ma_cq = "-1";
      //     }
      // }
      // return Ma_cq;
    },
    async HienThiTTDiaChi(ds, kieu) {
      if (ds.length > 0) {
        if (kieu == 1) {
          diachikh_id = ds[0].diachi_id
          tinhkh_id = ds[0].tinh_id
          quankh_id = ds[0].quan_id
          phuongkh_id = ds[0].phuong_id
          if (ds[0].pho_id != '') phokh_id = ds[0].pho_id
          else phokh_id = 0
          if (ds[0].ap_id != '') apkh_id = ds[0].ap_id
          else apkh_id = 0
          sonhakh = this.ChuanHoaTen(ds[0].sonha)
        }
        if (kieu == 2) {
          diachitt_id = ds[0].diachi_id
          tinhtt_id = ds[0].tinh_id
          quantt_id = ds[0].quan_id
          phuongtt_id = ds[0].phuong_id
          if (ds[0].pho_id != '') phott_id = ds[0].pho_id
          else phott_id = 0
          if (ds[0].ap_id != '') aptt_id = ds[0].ap_id
          else aptt_id = 0
          sonhatt = this.ChuanHoaTen(ds[0].sonha)
        }
      }
    },
    async chkNgayGV_CheckedChanged() {},
    async chkNgayBG_CheckedChanged_1() {},
    async tsbtnVatTu_Click() {
      if (phieu_id != 0) {
        // frmVatTuThueBao frm = new frmVatTuThueBao();
        // frm.phieu_id = phieu_id;
        // frm.loaihd_id = cboLoaiHD.value;
        // frm.hdtb_id = hdtb_id;
        // frm.thuebao_id = thuebao_id;
        // frm.ShowDialog();
      } else {
        this.$toast.warning('Bạn chưa chọn phiếu!')
      }
    },
    async btnPhieuTC_Click() {
      try {
        txtMaGD.Focus()
        if (gridDanhSach.list.length <= 0) {
          this.$toast.success('Chọn thuê bao cần xuất phiếu !')
          return
        }
        if (cboLoaiHD.value == LoaiHopDong.DAT_MOI) {
          ShowBaoCao('INPHIEU_TC_MGWAN.rst')
        }
        if (cboLoaiHD.value == LoaiHopDong.DI_CHUYEN) {
          ShowBaoCao('INPHIEU_TC_DC_MGWAN.rst')
        }
        //HienThiDanhSachHDTB();
      } catch (exp) {
        new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true)
      }
    },
    async btnBienBanNT_Click() {
      try {
        txtMaGD.Focus()
        InPhieu()
        if (kt_thaydoi) {
          //Nếu có sự đôi port mới load lại danh sachs
          let vma_tb = txtMaTB.value.trim()
          await this.HienThiDanhSachHDTB()
          // Tìm kiếm row vừa cập nhật
          gridDanhSach.FocusedRowHandle = FindGridIndexByValue('MA_TB', vma_tb)
        }
      } catch (exp) {
        new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true)
      }
    },
    // async nhanvien_tc_id = -1;
    // let congviec_th = "";
    async InPhieu() {
      try {
        let ds_phieu = []
        let vhdkh_id = checkdata.MAP_ID('hdkh_id', DatabaseConstants.DB2 + '.hd_thuebao', 'where hdtb_id = ' + hdtb_id)
        if (vhdkh_id == '-1') {
          this.$toast.success('Không tìm thấy thông tin khách hàng. Bạn hãy kiểm tra lại!')
          return
        }
        let vphieu_id = []
        // frmInBienBan obj = new frmInBienBan();
        n_hdkh_id = ''
        n_hdtb_id = ''
        n_phieu_id = ''
        for (let k = 0; k < dsIn.length; k++) {
          if (dsIn[k].CHON == '1') {
            n_hdkh_id += dsIn[k].hdkh_id + ','
            n_hdtb_id += dsIn[k].hdtb_id + ','
            n_phieu_id += dsIn[k].phieu_id + ','
          }
        }
        if (!isNullOrEmpty(n_hdkh_id) && !isNullOrEmpty(n_phieu_id) && !isNullOrEmpty(n_hdtb_id)) {
          //nhapt thêm
          obj.n_hdkh_id = n_hdkh_id.substring(0, n_hdkh_id.length - 1) //vhdkh_id);
          obj.nvth_id = nhanvien_tc_id
          obj.cv_thuchien = congviec_th
          obj.n_phieu_id = n_phieu_id.substring(0, n_phieu_id.length - 1) //phieu_id;
          obj.n_hdtb_id = n_hdtb_id.substring(0, n_hdtb_id.length - 1) //hdtb_id;
          obj.huonggiao_id = huonggiao_id
          obj.tentram_tb = ''
          //if (obj.liBienBan.Count > 0)
          obj.ShowDialog()
        }
        kt_thaydoi = obj.kt_thaydoi
      } catch (ex) {
        this.$toast.error('' + ex)
      }
    },
    async Lay_KhuyenMaiKH(vhdkh_id) {
      let kq = ''
      try {
        let ds_ctkm = []
        let str = ''
        str += ' select c.ten_km'
        str += ' from ' + DatabaseConstants.DB2 + '.hd_thuebao a,' + DatabaseConstants.DB2 + '.khuyenmai_hdtb b,'
        str += DatabaseConstants.DB2 + '.khuyenmai c '
        str += ' where a.hdtb_id = b.hdtb_id and b.khuyenmai_id = c.khuyenmai_id '
        str += '   and a.hdkh_id = ' + vhdkh_id
        ds_ctkm = bangts.GetSQL(str, 'dvgt')
        for (let i = 0; i < ds_ctkm.length; i++) {
          kq += ds_ctkm[i].ten_km + '; '
        }
        if (kq.length > 0) {
          kq = kq.substring(0, kq.length - 2)
        }
      } catch (ex) {
        this.$toast.error('Có lỗi: ' + ex)
      }
      return kq
    },
    async Lay_DichvuGT(vhdtb_id) {
      let kq = ''
      try {
        let ds_ctkm = []
        let str = ''
        str += ' select a.ma_dvgt'
        str += ' from ' + DatabaseConstants.DB2 + '.dichvu_gt a,' + DatabaseConstants.DB2 + '.dangky_dvgt b'
        str += ' where b.hdtb_id = ' + vhdtb_id
        str += '    and a.dichvugt_id = b.dichvugt_id'
        ds_ctkm = bangts.GetSQL(str, 'dvgt')
        for (let i = 0; i < ds_ctkm.length; i++) {
          kq += ds_ctkm[i].ma_dvgt + ', '
        }
        if (kq.length > 0) {
          kq = kq.substring(0, kq.length - 2)
        }
      } catch (ex) {
        this.$toast.error('Có lỗi: ' + ex)
      }
      return kq
    },
    async KhoiTao_DS(table_name, ds_, kieu) {
      try {
        if (kieu == 0) {
          // Khởi tạo dataset thuê bao
          ds_.Tables.Add(table_name)
          ds_.Tables[table_name].Columns.Add('ten_dvvt')
          ds_.Tables[table_name].Columns.Add('diachi_ld')
          ds_.Tables[table_name].Columns.Add('ten_kieuld')
          ds_.Tables[table_name].Columns.Add('ma_tb')
          ds_.Tables[table_name].Columns.Add('thongso_nt')
          ds_.Tables[table_name].Columns.Add('taikhoan')
          ds_.Tables[table_name].Columns.Add('culy')
          ds_.Tables[table_name].Columns.Add('diachi_ld_cu')
          ds_.Tables[table_name].Columns.Add('thongso_nt_cu')
          ds_.Tables[table_name].Columns.Add('ma_dvgt')
          ds_.Tables[table_name].Columns.Add('tenmuc')
        } // dataset vật tư
        else {
          ds_.Tables.Add(table_name)
          ds_.Tables[table_name].Columns.Add('stt')
          ds_.Tables[table_name].Columns.Add('ten_dvvt')
          ds_.Tables[table_name].Columns.Add('ma_tb')
          ds_.Tables[table_name].Columns.Add('loai_tbi')
          ds_.Tables[table_name].Columns.Add('kieu_tbi')
          ds_.Tables[table_name].Columns.Add('soluong')
        }
      } catch (exp) {
        this.$toast.error('Có lỗi: ' + exp)
      }
    },
    async TaoDuLieu_NVTH() {
      dsnhanviengp = new GIAOPHIEU_NV_DATA()
      // GIAOPHIEU_NV_DATA.GIAOPHIEU_NVRow row = dsnhanviengp.GIAOPHIEU_NV.NewGIAOPHIEU_NVRow();
      row.PHIEU_ID = phieu_id
      row.NHANVIEN_TH_ID = this.$root.token.getNhanVienID()
      row.SO_DT = ''
      row.NHIEMVU = 'Khai báo thông số !'
      row.NHIEMVU_ID = 7
      row.GHICHU = ''
      row.NHANVIEN_GIAO_ID = this.$root.token.getNhanVienID()
      row.NGAYGIAO = dtpNgayGV.value
      dsnhanviengp.GIAOPHIEU_NV.AddGIAOPHIEU_NVRow(row)
    },
    async TaoDL_GiaoPhieu_NVQL_CAP(nvql_cap_id) {
      dsnhanviengp = new GIAOPHIEU_NV_DATA()
      // GIAOPHIEU_NV_DATA.GIAOPHIEU_NVRow row = dsnhanviengp.GIAOPHIEU_NV.NewGIAOPHIEU_NVRow();
      row.PHIEU_ID = phieu_id
      row.NHANVIEN_TH_ID = nvql_cap_id
      row.SO_DT = ''
      row.NHIEMVU = 'Khai báo thông số !'
      row.NHIEMVU_ID = 7
      row.GHICHU = ''
      row.NHANVIEN_GIAO_ID = this.$root.token.getNhanVienID()
      row.NGAYGIAO = dtpNgayGV.value
      dsnhanviengp.GIAOPHIEU_NV.AddGIAOPHIEU_NVRow(row)
    },
    async PHAILAM(luong_id, CODE) {
      try {
        if (this.dtThaoTac && this.dtThaoTac.length <= 0) return
        let dr = this.dtThaoTac.find((e) => e.enable == 1 && e.CODE == CODE)
        if (dr.length > 0) return true
        return false
      } catch {
        console.log(CODE, this.dtThaoTac, luong_id)
        return false
      }
    },
    async cboQuyTrinh_SelectedValueChanged() {
      // if (this.cboQuyTrinh.list.length > 0) {
      //   if (this.cboQuyTrinh.value == null) return
      //   // if (this.cboQuyTrinh.value == 'System.Data.DataRowView') return
      //   this.quytrinh_id = this.cboQuyTrinh.value
      //   // this.dichvuvt_id = checkdata.MAP_ID('dichvuvt_id', DatabaseConstants.DB2 + '.quytrinh', 'where quytrinh_id = ' + quytrinh_id)
      //   let ds = await this.callApiWrapper(api.sp_lay_huonggiao_theo_quytrinh, { quytrinh_id: this.quytrinh_id, tthd_id: this.tthd_id })
      //   if (ds.length > 0) {
      //     this.luong_id = ds[0].luong_id
      //     this.huonggiao_id = ds[0].huonggiao_id
      //   } else {
      //     this.$toast.info('Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại')
      //     return
      //   }
      //   await this.HienThiGiaoDien(this.luong_id)
      //   this.hienthi_mau_tgcamket = 0
      //   if (await this.PHAILAM(this.luong_id, 'HIENTHI_MAU_THICONG')) this.hienthi_mau_tgcamket = 1
      //   if (!(await this.PHAILAM(this.luong_id, 'GIAOVIEC'))) {
      //     this.chkNgayBG.value = true
      //     this.chkNgayGV.value = true
      //     this.dtpNgayBG.value = this.dtpNgayGV.value = DateTimeHelper.NgayCN() //DateTimeHelper.NgayCN().AddSeconds(30)
      //   }
      // }
    },
    xuLyRdoPhieuMoi() {
      this.rdoPhieuMoi.value = false
      this.rdoPhieuTra.value = false
      this.rdoPhieuDaHoanThanh.value = false
      this.tsbtnTraPhieuTinh.enabled = true
      if (this.rdoPhieu.value == 0) {
        this.rdoPhieuMoi.value = true
      }
      if (this.rdoPhieu.value == 1) this.rdoPhieuTra.value = true
      if (this.rdoPhieu.value == 2) {
        this.rdoPhieuDaHoanThanh.value = true
        this.tsbtnTraPhieuTinh.enabled = false
      }
    },
    async rdoPhieuMoi_CheckedChanged() {
      this.xuLyRdoPhieuMoi()
      if (this.rdoPhieuMoi.value) {
        this.dpNgayGiaoTu.value = null
        this.dpNgayGiaoDen.value = null
        await this.HienThiDanhSachHDTB()
      }
    },
    async rdoPhieuTra_CheckedChanged() {
      this.xuLyRdoPhieuMoi()
      if (this.rdoPhieuTra.value) {
        this.dpNgayGiaoTu.value = moment().startOf('month').format('DD/MM/YYYY')
        this.dpNgayGiaoDen.value = moment().endOf('month').format('DD/MM/YYYY')
        await this.HienThiDanhSachHDTB()
      }
    },
    async rdoPhieuDaHoanThanh_CheckedChanged() {
      this.xuLyRdoPhieuMoi()
      if (this.rdoPhieuDaHoanThanh.value) {
        this.dpNgayGiaoTu.value = moment().startOf('month').format('DD/MM/YYYY')
        this.dpNgayGiaoDen.value = moment().endOf('month').format('DD/MM/YYYY')
        await this.HienThiDanhSachHDTB()
      }
    },
    async btnNDTH_Click() {
      try {
        if (this.phieu_id == 0) return
        if (
          this.tinh_khdn.includes(this.tinh_id) &&
          (await this.callApiWrapper(api.fn_kiemtra_hoanthienphieu_bancheo_vnpti, {
            vloaitb_id: this.loaitb_id
          })) != 'OK'
        ) {
          await this.callApiWrapper(api.update_nd_thuchien, {
            phieu_id: this.phieu_id,
            nd_thuchien: this.txtNoiDungTH.value
          })
        } else {
          await this.callApiWrapper(api.capnhat_nd_thuchien_giaophieu_ph_net, {
            phanvung_id: this.$root.token.getPhanVungID(),
            phieu_id: this.phieu_id,
            nd_thuchien: this.txtNoiDungTH.value
          })
        }
        this.$toast.success('Cập nhật thành công!')
        let vma_tb = this.txtMaTB.value
        await this.HienThiDanhSachHDTB()
        // Tìm kiếm row vừa cập nhật
        // gridDanhSach.FocusedRowHandle = FindGridIndexByValue('MA_TB', vma_tb)
      } catch (ex) {
        this.$toast.error(`Có lỗi : ${ex}`)
      }
    },
    async txtCvLan_KeyPress() {
      if (e.KeyChar == Convert.ToChar(Keys.Space)) e.Handled = true
    },
    async tsbtnCTKetNoi_Click() {
      try {
        // frmCTKetNoi obj = new frmCTKetNoi();
        // obj.vma_tk = txtMaGD.value.trim();
        // obj.vma_tb = txtMaTB.value.trim();
        // obj.vhuong_kn = vhuongkn;
        // obj.vhdtb_id = hdtb_id;
        // obj.ShowDialog();
      } catch (ex) {
        this.$toast.error(`Có lỗi: ${ex}`)
      }
    },
    async HienThiMuaTBI(ds) {
      if (ds.length > 0) {
        this.gridMuaTbi.list = ds
      } else {
        this.gridMuaTbi.list = []
      }
    },
    async HienThiDVGT_BanCheobase(ds) {
      if (ds.length > 0) {
        this.gridDangkyDV.list = ds
      } else {
        this.gridDangkyDV.list = []
      }
    },
    async HienThiDVGT(ds) {
      if (ds.length > 0) {
        this.gridDangkyDV.list = ds
      } else {
        this.gridDangkyDV.list = []
      }
    },
    async tsbtnHen_Click() {
      if (hdkh_id != 0) {
        // frmHenKH f = new frmHenKH();
        // f.vhdkh_id = hdkh_id;
        // f.Quyen_Duoc_hen = 1;
        // f.ShowDialog();
        let vma_tb = this.txtMaTB.value
        await this.HienThiDanhSachHDTB()
        gridDanhSach.FocusedRowHandle = FindGridIndexByValue('MA_TB', vma_tb)
      }
    },
    async checkAll_CheckedChanged() {
      for (let k = 0; k < dsIn.length; k++) {
        if (checkAll.value) dsIn[k].CHON = 1
        else dsIn[k].CHON = 0
      }
    },
    async tsbtnThongTinKT_Click() {
      try {
        if (this.thuebao_id != 0) {
          try {
            // frmThongTinKT_2 frm = new frmThongTinKT_2();
            // frm.thuebao_id = thuebao_id;
            // frm.dichvuvt_id = DichVuVienThong.MEGAWAN;
            // frm.vma_tb = txtMaTB.value.trim();
            // frm.vkieu = 1;
            // frm.ShowDialog();
          } catch (ex) {
            this.$toast.error('Có lỗi : ' + ex)
          }
        }
      } catch (ex) {
        this.$toast.error('Có lỗi : ' + ex)
      }
    },
    async btnKetNoi_Click() {
      try {
        if (this.in_thuebao_id_cha != 0 || this.in_hdtb_id_cha != 0) {
          try {
            // frmThongTinKT_2 frm = new frmThongTinKT_2();
            // frm.dichvuvt_id = CURRENT_DICHVUVT_ID;
            if (this.in_hdtb_id_cha != 0) {
              frm.hdtb_id = in_hdtb_id_cha
              frm.vma_tb = str_ma_tb_cha
              frm.vkieu = 0
            }
            if (this.in_thuebao_id_cha != 0) {
              frm.thuebao_id = in_thuebao_id_cha
              frm.vma_tb = str_ma_tb_cha
              frm.vkieu = 1
            }
            frm.ShowDialog()
          } catch (ex) {
            this.$toast.error('Có lỗi : ' + ex)
          }
        }
      } catch (ex) {
        this.$toast.error('Có lỗi : ' + ex)
      }
    },
    async tsbtnNVKT_Click() {
      // frmTCKhuVucDiaBan obj = new frmTCKhuVucDiaBan();
      // obj.hdtb_id = hdtb_id;
      // obj.donviql_id = 0;
      // obj.ShowDialog();
    },
    async btnBanDoLD_Click() {
      try {
        let lattitude = 0
        let longtitude = 0
        let s = new CheckDataFacade().MAP_ID('kinhdo_ld', DatabaseConstants.DB2 + '.diachi_hdtb', 'where hdtb_id =' + hdtb_id)
        if (s != '-1' && s != '') longtitude = s
        s = new CheckDataFacade().MAP_ID('vido_ld', DatabaseConstants.DB2 + '.diachi_hdtb', 'where hdtb_id =' + hdtb_id)
        if (s != '-1' && s != '') lattitude = s
        // frmBanDoTB frm;
        if (lattitude * longtitude > 0) {
          // frm = new frmBanDoTB(lattitude, longtitude, txtMaTB.value, false);
          frm.title = txtDiaChiLD.value
          frm.MapZoom = 17
          frm.vkieu = 2
          if (frm.ShowDialog() == DialogResult.OK) {
            bangts.CapnhatToaDoHDTB(frm.Lat, frm.Lng, hdtb_id, 1)
          }
        } else if (txtDiaChiLD.value.trim() != '') {
          // frm = new frmBanDoTB(txtDiaChiLD.value, "Vị trí tương đối theo địa chỉ:\r\n" + txtMaTB.value, false);
          frm.title = txtDiaChiLD.value
          frm.MapZoom = 17
          frm.vkieu = 2
          if (frm.ShowDialog() == DialogResult.OK) {
            bangts.CapnhatToaDoHDTB(frm.Lat, frm.Lng, hdtb_id, 1)
          }
        } else {
          this.$toast.success('Chưa có thông tin về bản đồ địa chỉ thuê bao')
          return
        }
      } catch (ex) {
        this.$toast.success('' + ex)
      }
    },
    async tsbtnXuatFile_Click() {
      try {
        this.exportExcelByCols(this.gridDanhSach.list, this.gridDanhSach.cols)
      } catch (ex) {
        this.$toast.success('Có lỗi : ' + ex)
      }
    },
    exportExcelByCols(inputArray, arrayFields) {
      var exportData = []
      exportData = inputArray.map((e) => {
        var exportEntity = {}
        arrayFields.map((x) => {
          exportEntity[x.headerText] = e[x.fieldName]
        })
        return exportEntity
      })
      if (exportData.length > 0) {
        let worksheet = XLSX.utils.json_to_sheet(exportData)
        let workbook = XLSX.utils.book_new()
        XLSX.utils.book_append_sheet(workbook, worksheet, 'Dữ liệu')
        XLSX.writeFile(workbook, 'export.xlsx')
      } else {
        this.$toast.warning('Không có dữ liệu để xuất file!')
      }
    },
    async tsbtnHoanThienHS_Click() {
      try {
        if (gridDanhSach.list.length > 0) {
          let kt_taobang = 0
          kt_taobang = bangts.KT_TaoDB_Thang(dtpNgayBG.value.ToString('yyyyMM'))
          if (kt_taobang == 0) {
            this.$toast.success('Danh bạ tháng chưa được chốt. Liên hệ Admin xử lý trước khi thực hiện !')
            return
          }
          if (await this.PHAILAM(this.luong_id, 'KT_HOANCONG_HD_BANCHEO')) {
            try {
              let kq_bc = new BusinessFacade.BanCheoFacade.HopDong().KIEMTRA_HOANTHIEN_THOAITRA_BAN(hdtb_id, 1)
              if (kq_bc != 'OK') {
                this.$toast.warning(kq_bc)
                return
              }
            } catch (Exception) {}
          }
          if (!(await Cap_Nhat())) return
          if (this.cboLoaiHD.value == LoaiHopDong.DAT_MOI) {
            new LapHopDongFacade2().CAPNHAT_NGAYKH_NGAY_HT(hdtb_id, DateTimeHelper.NgayCN(), dtpNgayBG.value)
            //hoancong.GIAOPHIEU_TD_KHI_HC(hdtb_id, phieu_id, huonggiao_id, tt_nd.donvi_id, tt_nd.donvi_id, this.$root.token.getNhanVienID(), tt_nd.ma_nd, tt_nd.may_cn);
            hths_dm.HOAN_CONG_DATMOI(hdtb_id, phieu_id, tt_nd.donvi_id, this.$root.token.getNhanVienID(), true, tt_nd.may_cn, DateTimeHelper.NgayCN(), tt_nd.ma_nd)
            bangts.CapNhat_TT_Phieu_HTHS(hdtb_id)
            if (await this.PHAILAM(this.luong_id, 'TUDONG_GIAOPHIEU_TAO_DS_CAP_VT')) {
              try {
                hoancong.GIAOPHIEU_TD_TAOPHIEU_CAPVT(hdtb_id, phieu_id, tt_nd.donvi_id, this.$root.token.getNhanVienID(), tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip)
              } catch (Exception) {}
            }
            if (await this.PHAILAM(this.luong_id, 'TUDONG_GIAOPHIEU_DUYET_CAPVT')) {
              try {
                hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(hdtb_id, 0, phieu_id, tt_nd.donvi_id, this.$root.token.getNhanVienID(), KieuTbi.MOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip)
              } catch (Exception) {}
            }
            if (await this.PHAILAM(this.luong_id, 'TUDONG_GIAOPHIEU_QUYETTOAN_VT')) {
              try {
                hoancong.GIAOPHIEU_TD_TAOPHIEU_QTVT(1, hdtb_id, phieu_id, tt_nd.donvi_id, this.$root.token.getNhanVienID(), tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip)
              } catch (Exception) {}
            }
            if (await this.PHAILAM(this.luong_id, 'KT_HOANCONG_HD_BANCHEO')) {
              try {
                new BusinessFacade.BanCheoFacade.HopDong().hoangcong_hopdong_bancheo(hdtb_id)
              } catch (ex) {
                tdan.EXECUTE_PROC(
                  '{?DB1}.QUANTRI_HETHONG.Log_Action',
                  'vuser_name',
                  tt_nd.ma_nd,
                  'vcomputer_name',
                  tt_nd.may_cn,
                  'vaction_name',
                  'frmHoanCongMegaWan_HT',
                  'vkey_parameter',
                  'hdtb_id=' + hdtb_id + ',ngay_cn=' + DateTimeHelper.NgayCN() + ', Err : ' + ex,
                  'vserver_name',
                  tt_nd.ip,
                  'vnote',
                  ' Hoàn công cố định'
                )
              }
            }
            this.$toast.success('Hoàn thiện hồ sơ thành công! ')
            await this.HienThiDanhSachHDTB()
          }
          if (cboLoaiHD.value == LoaiHopDong.DI_CHUYEN) {
            new LapHopDongFacade2().CAPNHAT_NGAYKH_NGAY_HT(hdtb_id, DateTimeHelper.NgayCN(), dtpNgayBG.value)
            hths_dc.HOAN_CONG_DICHCHUYEN(hdtb_id, phieu_id, tt_nd.donvi_id, this.$root.token.getNhanVienID(), true, tt_nd.may_cn, DateTimeHelper.NgayCN(), tt_nd.ma_nd)
            bangts.CapNhat_TT_Phieu_HTHS(hdtb_id)
            if (await this.PHAILAM(this.luong_id, 'TUDONG_GIAOPHIEU_TAO_DS_CAP_VT')) {
              try {
                hoancong.GIAOPHIEU_TD_TAOPHIEU_CAPVT(hdtb_id, phieu_id, tt_nd.donvi_id, this.$root.token.getNhanVienID(), tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip)
              } catch (Exception) {}
            }
            if (await this.PHAILAM(this.luong_id, 'TUDONG_GIAOPHIEU_DUYET_CAPVT')) {
              try {
                hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(hdtb_id, 0, phieu_id, tt_nd.donvi_id, this.$root.token.getNhanVienID(), KieuTbi.MOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip)
              } catch (Exception) {}
            }
            if (await this.PHAILAM(this.luong_id, 'TUDONG_GIAOPHIEU_QUYETTOAN_VT')) {
              try {
                hoancong.GIAOPHIEU_TD_TAOPHIEU_QTVT(1, hdtb_id, phieu_id, tt_nd.donvi_id, this.$root.token.getNhanVienID(), tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip)
              } catch (Exception) {}
            }
            if (await this.PHAILAM(this.luong_id, 'KT_HOANCONG_HD_BANCHEO')) {
              try {
                new BusinessFacade.BanCheoFacade.HopDong().hoangcong_hopdong_bancheo(hdtb_id)
              } catch (ex) {
                tdan.EXECUTE_PROC(
                  '{?DB1}.QUANTRI_HETHONG.Log_Action',
                  'vuser_name',
                  tt_nd.ma_nd,
                  'vcomputer_name',
                  tt_nd.may_cn,
                  'vaction_name',
                  'frmHoanCongMegaWan_HT',
                  'vkey_parameter',
                  'hdtb_id=' + hdtb_id + ',ngay_cn=' + DateTimeHelper.NgayCN() + ', Err : ' + ex,
                  'vserver_name',
                  tt_nd.ip,
                  'vnote',
                  ' Hoàn công cố định'
                )
              }
            }
            this.$toast.success('Hoàn thiện hồ sơ thành công! ')
            await this.HienThiDanhSachHDTB()
          }
        } else {
          this.$toast.success('Không có thuê bao để Hoàn thiện !')
          return
        }
      } catch (ex) {
        this.$toast.error('Có lỗi : ' + ex)
      }
    },
    async btnHuongKNWan_Click() {},
    async cboLoaiSoWan_SelectedValueChanged() {},
    async tsbtnGiaoPhieu_Ton_Click() {
      if (this.phieu_id == 0) {
        this.$toast.success('Hãy chọn phiếu trước để nhập lý do tồn!')
        return
      }
      // frmGiaoPhieu_Ton frm = new frmGiaoPhieu_Ton();
      frm.phieu_id = phieu_id
      frm.tthd_id = tthd_id
      frm.ShowDialog()
    },
    async tsbtnDauNoiCap_Click() {
      try {
        if (dt.length > 0) {
          let dscheck_thamso = await this.callApiWrapper(api.lay_ds_thamso_md_mats, {
            ma_ts: 'DAUNOI_KOCAN_CAP_PORT'
          })
          if (port_id == 0 && dscheck_thamso[0].length == 0) {
            this.$toast.success('Chưa có thông tin về Port, bạn không được phép cấp cáp.')
            return
          }
          let s
          let lattitude = 0
          let longtitude = 0
          s = new CheckDataFacade().MAP_ID('kinhdo_ld', DatabaseConstants.DB2 + '.diachi_hdtb', 'where hdtb_id =' + hdtb_id)
          if (s != '-1' && s != '') lattitude = s
          s = new CheckDataFacade().MAP_ID('vido_ld', DatabaseConstants.DB2 + '.diachi_hdtb', 'where hdtb_id =' + hdtb_id)
          if (s != '-1' && s != '') longtitude = s
          let vto_ql = new CheckDataFacade().MAP_ID('ten_dv', DatabaseConstants.DB1 + '.donvi', 'where donvi_id =' + donvi_id)
          let str_tram_tbi = new CheckDataFacade().MAP_ID('ten_dv', DatabaseConstants.DB1 + '.donvi', 'where donvi_id =' + tramtb_id)
          if (IS_USING_CABMAN_V2) {
            // if (DatabaseConstants.DB2 == "CSS_HCM")
            // using (let dig = new WinUICabmanV2.frmDauNoiCapTB(-hdtb_id))
            // {
            //     dig.ShowDialog();
            //     HIEN_THONGSO_KYTHUAT();
            //     let culy = new CheckDataFacade().MAP_ID("CULY", DatabaseConstants.DB2 + ".DAUNOI_CAP", "where thuebao_id = -" + hdtb_id);
            //     if (culy == "-1" || isNullOrEmpty(culy))
            //         culy = "0";
            // }
            // else
            // using (let dig = new WinUI.WinUICabmanV2.frmDauNoiTB(hdtb_id, 1))
            // {
            //     dig.ShowDialog();
            //     HIEN_THONGSO_KYTHUAT();
            // }
          } else {
            // WinUIDieuHanhThiCong.frmDauNoiTuyenCap obj = new WinUIDieuHanhThiCong.frmDauNoiTuyenCap();
            obj.vkinhdo = longtitude
            obj.vvido = lattitude
            obj.vten_tb = txtTenTB.value.trim()
            obj.vdiachi_ld = txtDiaChiLD.value.trim()
            obj.vhdtb_id = hdtb_id
            obj.vdichvuvt_id = DichVuVienThong.MEGAWAN
            obj.vto_ql = vto_ql
            obj.vtram_tbi = str_tram_tbi
            obj.vtramvt_id = donvi_id
            obj.vtongdai_id = tramtb_id
            obj.vma_tb = txtMaTB.value.trim()
            obj.ShowDialog()
          }
        }
      } catch (ex) {
        this.$toast.error('' + ex)
      }
    },
    async tsbtnChuyenTo_Click() {
      try {
        if (await this.PHAILAM(this.luong_id, 'KIEMTRA_VT_CHUYENTO')) {
          if (CapVT.LAY_DS_PHIEUVT_HDBH(phieu_id, 0, hdtb_id, 0, 0).length > 0) {
            this.$toast.success('Bạn phải xóa hết vật tư trước khi thực hiện chuyển tổ')
            return
          }
        }
        // WinUIQLDHXLSuCo.frmChuyenTo f = new WinUIQLDHXLSuCo.frmChuyenTo();
        f.hdtb_id = hdtb_id
        f.baohong_id = 0
        f.phieu_id = phieu_id
        f.vtthd_id = tthd_id
        f.ShowDialog()
        if (f.Chapnhan) await this.HienThiDanhSachHDTB()
      } catch (ex) {
        this.$toast.error('' + ex)
      }
    },
    async tsbtnDuAn_Click() {
      try {
        // frmDuAn_HDTB f_duan = new frmDuAn_HDTB();
        f_duan.vhdtb_id = hdtb_id
        f_duan.ShowDialog()
      } catch (ex) {
        this.$toast.error('' + ex)
      }
    },
    async tsbtnKQ_DoKiem_Click() {
      if (hdtb_id != 0) {
        // frmKetQuaDoKiem frm = new frmKetQuaDoKiem();
        frm.vhdtb_id = hdtb_id
        frm.ShowDialog()
      } else {
        this.$toast.warning('Bạn chưa chọn phiếu!')
      }
    },
    async txtMaTB_TextChanged() {},
    async txtPort_TextChanged() {},
    async gvDanhSach_SelectionChanged(e) {
      console.log('gvDanhSach_SelectionChanged')
      console.log(e)
      this.gridDanhSach.value = e.data
      if (this.$refs.gridDanhSach.grid.getSelectedRecords().length < 0) {
        await this.Clear()
        return
      }
      if (this.dt.length > 0) {
        // try {
        await this.HienThiTTHopDongTB(this.dsIn)
        // } catch (ex) {
        //   this.$toast.error('Có lỗi: ' + ex.Message)
        // }
      }
    },
    async gvDanhSach_MouseDown() {
      // try
      // {
      //     let info = gridDanhSach.CalcHitInfo((e as MouseEventArgs).Location);
      //     if (info.RowHandle < 0) return;
      //     if (!info.InRow && !info.InRowCell) return;
      //     if (info.Column.FieldName == "CHON")
      //     {
      //         (e as HandledMouseEventArgs).Handled = true;
      //         if (gridDanhSach.GetRowCellValue(info.RowHandle, "CHON") == "0")
      //             gridDanhSach.SetRowCellValue(info.RowHandle, "CHON", 1);
      //         else gridDanhSach.SetRowCellValue(info.RowHandle, "CHON", 0);
      //         gridDanhSach.Update();
      //     }
      // }
      // catch
      // {
      // }
    },
    async gvDanhSach_KeyDown() {
      if (e.KeyCode == Keys.Space) {
        if (gridDanhSach.list.length == 0) return
        if (gridDanhSach.selected.length == 0) return
        let selectRows = gridDanhSach.GetSelectedRows()
        let fState = gridDanhSach.GetRowCellValue(selectRows[0], 'CHON')
        let State = fState == '0' ? 1 : 0
        for (let i = 0; i < selectRows.length; i++) {
          gridDanhSach.SetRowCellValue(selectRows[i], 'CHON', State)
        }
        gridDanhSach.Update()
      }
    },
    async gvDanhSach_FocusedRowChanged() {
      gridDanhSach.Update()
      gvDanhSach_SelectionChanged()
    },
    async FindGridIndexByValue(col_key, _value) {
      return FindGridIndexByValue(col_key, _value, false)
    },
    async FindGridIndexByValue(col_key, _value, isCase) {
      if (!isCase) _value = _value.ToLower()
      for (let i = 0; i < gridDanhSach.list.length; i++) {
        let cv = gridDanhSach.GetRowCellValue(i, col_key)
        if (!isCase) cv = cv.ToLower()
        if (_value.Equals(cv)) {
          return i
        }
      }
      return 0
    },
    async LayIndexTheoPhieuID(_phieu_id, ds) {
      if (ds == null) return -1
      return LayIndexTheoPhieuID(_phieu_id, ds)
    },
    async LayIndexTheoPhieuID(_phieu_id, dt) {
      if (dt == null) return -1
      for (let i = 0; i < dt.length; i++) {
        if (dt.Rows[i].PHIEU_ID == _phieu_id) return i
      }
      return -1
    },
    async btnChonPort_TuDong_Click() {},
    async btnChonPort_Click() {
      ChonPort()
    },
    async chkLayTSo_CheckedChanged() {},
    async txtDoiCapGoc_KeyPress() {
      if ((e.KeyChar >= '0' && e.KeyChar <= '9') || e.KeyChar == 8) e.Handled = false
      else e.Handled = true
    },
    async txtDoiCapGoc_Leave() {},
    async txtDoiCapNgon_KeyPress() {
      if ((e.KeyChar >= '0' && e.KeyChar <= '9') || e.KeyChar == 8) e.Handled = false
      else e.Handled = true
    },
    async txtDoiCapNgon_Leave() {},
    async txtSoMetDay_KeyPress() {
      if ((e.KeyChar >= '0' && e.KeyChar <= '9') || e.KeyChar == 8) e.Handled = false
      else e.Handled = true
    },
    async txtSoMetDay_Leave() {},
    async simpleButton1_Click() {
      try {
        // frmTraCuuHopDong f = new frmTraCuuHopDong(LoaiHopDong.DAT_MOI, tthd_id, 0, 0, "0", 1, tt_nd.donvi_dl_id, 0);
        f.ShowDialog()
        if (f.chapnhan) {
          this.vma_gd = f.ma_gd
          this.txtMaGD.value = this.vma_gd
          txtMaGD.Focus()
          if (this.txtMaGD.value != '') {
            let vloai_id = 0 // 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
            if (this.rdoPhieuMoi.value) {
              vloai_id = 0
            } else {
              vloai_id = 1
            }
            HienThiTTHopDongTB(giaophieutc.LAY_DS_PHIEU_GIAODEN(DichVuVienThong.MEGAWAN, tthd_id, this.$root.token.getNhanVienID(), txtMaGD.value, 0, 0, 1, 0, huonggiao_id, vloai_id))
          }
        }
      } catch (ex) {
        this.$toast.error('Có lỗi: ' + ex)
      }
    },
    async simpleButton1_Click_1() {},
    async gvDanhSach_CustomDrawCell() {
      if (e.RowHandle < 0) return
      if (e.Column.FieldName == 'TEN_STATUS') {
        let istatus = this.gridDanhSach.value.STATUS
        let img = lblKoKH.Image
        switch (istatus) {
          case '':
            img = lblKoKH.Image
            break
          case '0':
            img = lblNone.Image
            break
          case '1':
            img = lblDC.Image
            break
          case '5':
            img = lblKH.Image
            break
        }
        e.Appearance.TextOptions.HAlignment = DevExpress.Utils.HorzAlignment.Center
        e.Graphics.DrawImage(img, e.Bounds.X, e.Bounds.Y, e.Bounds.Height, e.Bounds.Height)
      } else if (e.Column.FieldName == 'IN_STATUS') {
        let istatus = gridDanhSach.GetRowCellValue(e.RowHandle, 'LUU_IN')
        if (istatus != '') {
          gridDanhSach.SetRowCellValue(e.RowHandle, 'IN_STATUS', 'Đã in')
          let img = lblDaIn.Image
          e.Appearance.TextOptions.HAlignment = DevExpress.Utils.HorzAlignment.Center
          e.Graphics.DrawImage(img, e.Bounds.X, e.Bounds.Y, e.Bounds.Height, e.Bounds.Height)
        } else gridDanhSach.SetRowCellValue(e.RowHandle, 'IN_STATUS', '')
      }
      e.Handled = false
    },
    async gvDanhSach_DataSourceChanged() {
      if (this.gridDanhSach.list != null) {
        await this.gvDanhSach_SelectionChanged()
      }
    },
    async tsbtnSua_KenhThu_Click() {
      // frmThayDoi_KenhThu f = new frmThayDoi_KenhThu(txtMaGD.value.trim());
      f.ShowDialog()
    },
    async tsbtnVatTuMoi_Click() {
      if (phieu_id != 0) {
        // frmCapVatTuTB frm = new frmCapVatTuTB();
        frm.phieu_id = phieu_id
        frm.loaihd_id = loaihd_id
        frm.hdtb_id = hdtb_id
        frm.thuebao_id = thuebao_id
        frm.loaitb_id = loaitb_id
        frm.baohong_id = 0
        frm.ShowDialog()
      } else {
        this.$toast.warning('Bạn chưa chọn phiếu!')
      }
    },
    async gridDanhSach_Paint() {
      let bo = gridDanhSach.Bounds
      let g = e.Graphics
      //g.TextRenderingHint = System.Drawing.value.TextRenderingHint.AntiAlias;
      //g.SmoothingMode = System.Drawing.Drawing2D.SmoothingMode.AntiAlias;
      //g.CompositingQuality = System.Drawing.Drawing2D.CompositingQuality.HighQuality;
      //g.InterpolationMode = System.Drawing.Drawing2D.InterpolationMode.HighQualityBicubic;
      let sf = new StringFormat()
      sf.Alignment = StringAlignment.Center
      sf.LineAlignment = StringAlignment.Center
      if (this.gridDanhSach.value < 0 && this.gridDanhSach.list.length > 0) {
        g.DrawString('Bạn chưa focus phiếu (click vào 1 phiếu trên lưới để focus)', new Font('Arial', 22, FontStyle.Bold), new SolidBrush(Color.FromArgb(180, 80, 80, 80)), bo.Width / 2, bo.Height / 2, sf)
      } else if (this.gridDanhSach.value < 0 && this.gridDanhSach.list.length == 0) {
        if (gridDanhSach.ActiveFilter.Count == 0) {
          g.DrawString('Không có dữ liệu', new Font('Arial', 22, FontStyle.Bold), new SolidBrush(Color.FromArgb(180, 80, 80, 80)), bo.Width / 2, bo.Height / 2, sf)
        } else {
          g.DrawString('Không tìm thấy thông tin', new Font('Arial', 22, FontStyle.Bold), new SolidBrush(Color.FromArgb(180, 80, 80, 80)), bo.Width / 2, bo.Height / 2, sf)
        }
      } else if (this.gridDanhSach.list.length == 0) {
      }
    },
    async tsbtnChonIPLANWAN_Click() {
      if (this.gridDanhSach.value < 0) return
      // let kq = new BangTuDienFacade().THUCTHI_FUNCTION("KT_IP_WAN_LAN", DatabaseConstants.DB8, "vhdtb_id", CURRENT_HDTB_ID);
      if (kq != '1') {
        this.$toast.success(kq)
        return
      }
      // let _dt1 = new BangTuDienFacade().THUCTHI_PROCEDURE("LAY_TT_PORT", DatabaseConstants.DB2, new let[] { "port_mgwan" }, "vhdtb_id", CURRENT_HDTB_ID).Tables.port_mgwan;
      if (_dt1 != null && _dt1.length > 0) {
        // frmIP_LAN_WAN frm = new frmIP_LAN_WAN();
        frm.hdtb_id = CURRENT_HDTB_ID
        frm.loaitb_id = CURRENT_LOAITB_ID
        frm.dichvuvt_id = CURRENT_DICHVUVT_ID
        frm.bras_id = _dt1.Rows[0].bras_id
        frm.ma_tb = this.gridDanhSach.value.MA_TB
        frm.tenbras = _dt1.Rows[0].tenbras
        frm.ShowDialog()
      }
    },
    async btnDownUpLink_Click() {
      if (port_id == 0) {
        this.$toast.success('Chưa có thông tin port')
        return
      }
    },
    async gvDanhSach_RowCellStyle() {
      try {
        let gv = []
        if (e.Column.FieldName == 'THOIGIAN_CK') {
          if (hienthi_mau_tgcamket == 1) {
            let thoigian_ck = gridDanhSach.GetRowCellValue(e.RowHandle, 'THOIGIAN_CK')
            if (!isNullOrEmpty(thoigian_ck)) {
              let dateCamKet = Convert.ToDateTime(thoigian_ck)
              if (dateCamKet < DateTime.Now) {
                e.Appearance.BackColor = Color.Red
                e.Appearance.BackColor2 = Color.Red
              } else if (DateTime.Now <= dateCamKet && dateCamKet <= DateTime.Now.AddHours(1)) {
                e.Appearance.BackColor = Color.Blue
                e.Appearance.BackColor2 = Color.Blue
              }
            }
          }
        } else if (e.RowHandle == gridDanhSach.FocusedRowHandle && e.Column != gridDanhSach.FocusedColumn) {
          e.Appearance.BackColor2 = Color.CornflowerBlue
          e.Appearance.BackColor = Color.CornflowerBlue
          e.Appearance.ForeColor = Color.White
        }
      } catch (ex) {}
    },
    async tsbtnThongTinVRF_Click() {
      // frmCTKetNoi_VFI frmvfi = new frmCTKetNoi_VFI();
      frmvfi.ShowDialog()
    },
    async toolStripButton1_Click() {
      if (!this.gridDanhSach.value) return
      let donvi_nhan_id = 0
      donvi_nhan_id = await this.gridDanhSach.value.donvi_nhan_id
      // frmGiaoPhieu_Net f;
      let input = {
        phieu_cha_id: this.phieu_id,
        phieu_tinh_id: 0,
        donvi_ql_id: donvi_nhan_id,
        vtinh_id: this.$root.token.getPhanVungID()
      }
      if (this.tinh_khdn.includes(this.tinh_id))
        input = {
          phieu_cha_id: this.phieu_id,
          phieu_tinh_id: 0,
          donvi_ql_id: donvi_nhan_id,
          vtinh_id: this.tinh_id
        }
      else
        input = {
          phieu_cha_id: this.phieu_id,
          phieu_tinh_id: this.phieu_tinh_id,
          donvi_ql_id: donvi_nhan_id,
          tinh_id: this.tinh_id
        }
      this.$refs.frmGiaoPhieu_Net.openDialog(input)
      // await this.HienThiDS_PhieuTH(this.phieu_id)
    },
    async UPLOAD_FILE() {
      if (openFileDialog1.FileNames.length > 0) {
        // let success = new Dictionary<let, let>();
        // let fail = new Dictionary<let, let>();
        // using (frmUploadDialog fu = new frmUploadDialog()
        // {
        //     fName = openFileDialog1.FileName // File trên máy client
        //     ,
        //     fDes = "BIENBANNGHIEMTHUNET" // Tên thư mục để lưu file trên server
        //     ,
        //     fRenameFileWhenExist = true
        // })
        // {
        //     fu.StartAndWait(); // Bắt đầu upload và đợi cho tới khi hoàn thành hoặc lỗi
        //     if (fu.ThanhCong)
        //     {
        //         return fu.fURL;
        //     }
        //     else
        //     {
        //         return "";
        //     }
        // }
      }
      return ''
    },
    async gridviewDanhSach_CustomRowCellEdit() {
      try {
        if (e.RowHandle < 0) return
        if (e.Column.FieldName != 'URL_FILE_NT') return
        let dr = gridDanhSach.GetDataRow(e.RowHandle)
        // if (dr.URL_FILE_NT != null)
        //     if (dr.URL_FILE_NT != "")
        //         ((DevExpress.XtraEditors.Repository.RepositoryItemHyperLinkEdit)e.RepositoryItem).Caption = "Link tải";
      } catch {}
    },
    async hplDownload_Click() {
      DOWNLOAD_FILE()
    },
    // async link_file
    // {
    //     get
    //     {
    //         try
    //         {
    //             return ((System.Data.let)gridDanhSach.list).Rows[gridDanhSach.FocusedRowHandle].URL_FILE_NT ?? "";
    //         }
    //         catch
    //         {
    //             return "";
    //         }
    //     }
    // },
    async DOWNLOAD_FILE() {
      try {
        if (link_file == '') {
          return
        }
        sfdLuuFile.FileName = link_file.substring(link_file.LastIndexOf('BIENBANNGHIEMTHUNET/') + 20)
        if (sfdLuuFile.ShowDialog() == DialogResult.OK) {
          // using (let dig = new frmDownloadDialog()
          // {
          //     fURL = link_file,
          //     fName = sfdLuuFile.FileName
          // })
          // {
          //     dig.DownloadCompleted += (o, v) =>
          //     {
          //         if (dig.ThanhCong)
          //         {
          //             try { System.Diagnostics.Process.Start(System.IO.Path.GetDirectoryName(dig.fName)); }
          //             catch { }
          //         }
          //         else if (v.Error != null)
          //         {
          //             this.$toast.error(v.Error.Message);
          //         }
          //     };
          //     dig.Start();
          // }
        }
      } catch (ex) {
        this.$toast.error('Có lỗi khi tải file: ' + ex.Message)
      }
    },
    async tsbtnFileNT_Click() {
      let formData = new FormData()
      for (var file of this.$refs.files.files) {
        formData.append('files', file)
      }
      let file_id = await this.callApiWrapper(api.get_keys, {
        phanvung_id: this.gridDanhSach.value.tinh_id,
        keyname: 'FILEHOSO_ID',
        numblocksize: 1,
        numretry: 10
      })
      let kq = await this.callApiWrapper(api.upload_file, formData, { headers: { 'Content-Type': 'multipart/form-data' } })
      if (kq && kq.length) {
        let rs = await this.callApiWrapper(api.tsbtnDownloadFileNT_Click, {
          vphanvung_id: this.gridDanhSach.value.tinh_id,
          vurl: kq[0],
          vfile_name: kq[0].substring(kq[0].lastIndexOf('/') + 1, kq[0].length),
          vthuebao_id: this.thuebao_id,
          vfile_id: file_id,
          vthanhtoan_id: this.thanhtoan_id,
          vma_tb: this.txtMaTB.value,
          vhdtb_id: this.hdtb_id
        })
        if (rs == 1) {
          this.$toast.success('Cập nhật thành công!')
          await this.HienThiDanhSachHDTB()
        } else {
          this.$toast.error(`Có lỗi khi upload file`)
        }
      }
    },
    async changeFileUpload() {
      await this.tsbtnFileNT_Click()
      // if (this.$refs.files.files[0].name.length > 0) {
      //   let result = this.$refs.files.files[0].name.substring(this.$refs.files.files[0].name.indexOf('.') + 1, this.$refs.files.files[0].name.length)
      //   if (result != 'txt') {
      //     this.ShowAlert('File bạn chọn phải có định dạng .txt!', 2)
      //     return
      //   }
      // }
      // this.listbox.ds_file.value.url = this.$refs.files.files[0].name
    },
    async tsbtnDownloadFileNT_Click() {
      if (this.gridDanhSach.value == null) {
        this.$toast.warning('Vui lòng chọn phiếu!')
        return
      }
      let hdtb_tc = 0
      if (
        this.tinh_khdn.includes(this.tinh_id) &&
        (await this.callApiWrapper(api.fn_kiemtra_hoanthienphieu_bancheo_vnpti, {
          vloaitb_id: this.loaitb_id
        })) != 'OK'
      ) {
        let rs = await this.callApiWrapper(api.lay_ds_hdbancheo_theo_hdtb_id, {
          vhdtb_id: this.gridDanhSach.value.hdtb_id,
          vphanvung_id: this.gridDanhSach.value.tinh_id
        })
        if (rs.length > 0 && rs[0].hdtb_thicong != null) {
          hdtb_tc = rs[0].hdtb_ban
          let tinh_bc_id = rs[0].tinh_thicong
          this.frmDSHoSoBC.input.vhdtb_tc = hdtb_tc
          this.frmDSHoSoBC.input.vtinh_id = tinh_bc_id
        }
      } else {
        this.frmDSHoSoBC.input.vloaifile_id = 2
        this.frmDSHoSoBC.input.vhdtb_tc = this.gridDanhSach.value.hdtb_id
        this.frmDSHoSoBC.input.vtinh_id = this.tinh_id
      }

      this.ejsDialog.name = 'frmDSHoSoBC'
      this.ejsDialog.header = 'Danh sách hồ sơ tỉnh khác'
      this.$refs.ejsDialog.show()
    },
    async tsbtnNhapMoiTT_Click() {
      await this.HT_DLG_NHAP_NOIDUNG(null)
    },
    async HT_DLG_NHAP_NOIDUNG(tientrinhtc_id) {
      this.frmNoiDung.input = {
        row: this.gridDanhSach.value,
        vtientrinhtc_id: tientrinhtc_id
      }
      this.ejsDialog.name = 'frmNoiDung'
      this.ejsDialog.header = 'Nhập nội dung tiến trình thi công'
      this.$refs.ejsDialog.show()
    },
    async gridviewTienTrinh_ShownEditor() {
      if (gridviewTienTrinh.FocusedRowHandle >= 0) {
        let vnguoi_cn = this.gridviewTienTrinh.value.NGUOI_CN
        let vtientrinhtc_id = this.gridviewTienTrinh.value.TIENTRINHTC_ID
        if (vnguoi_cn == tt_nd.ma_nd) {
          if (gridviewTienTrinh.FocusedColumn == SMS_1) {
            HT_DLG_GUI_SMS(vtientrinhtc_id)
          }
          if (gridviewTienTrinh.FocusedColumn == SUA_1) {
            HT_DLG_NHAP_NOIDUNG(vtientrinhtc_id)
          }
          if (gridviewTienTrinh.FocusedColumn == XOA_1) {
            HT_DLG_XOA_TIENTRINH(vtientrinhtc_id)
          }
        } else this.$toast.warning('Bạn không thể thao tác trên tiến trình của người khác')
        gridviewTienTrinh.FocusedColumn = TIENTRINHBH_ID
      }
    },
    async HT_DLG_GUI_SMS(vtientrinhtc_id) {
      let vhdtb_id = this.gridDanhSach.value.HDTB_TC
      let _tinh_id = this.gridDanhSach.value.TINH_ID
      let tinh_tc_id = 0
      if (this.tinh_khdn.includes(_tinh_id)) tinh_tc_id = this.gridDanhSach.value.TINH_THICONG
      else tinh_tc_id = tinh_id
      // Tinh_TC _tinh_tc = new BanCheoBase().tinh_thicong(tinh_tc_id);
      let bhi = this.gridDanhSach.value.HDTB_ID
      let tti = vtientrinhtc_id
      if (bhi != null && tti != null) {
        let frm = new WinUI.WinUIQLDHXLSuCo.frmGuiSMS(_tinh_tc)
        frm.value = 'Gửi SMS tiến trình thi công'
        frm.vhdtb_id = vhdtb_id
        frm.vnhom_id = 1
        frm.vtientrinhtc_id = tti
        frm.vma_tb = txtMaTB.value
        frm.txtNoiDung.value = this.gridviewTienTrinh.value.NOIDUNG
        frm.ShowDialog()
      }
    },
    async HT_DLG_TIENTRINH_SMS(vtientrinhtc_id) {
      let vhdtb_id = this.gridDanhSach.value.HDTB_TC
      let _tinh_id = this.gridDanhSach.value.TINH_ID
      let tinh_tc_id = 0
      if (this.tinh_khdn.includes(_tinh_id)) tinh_tc_id = this.gridDanhSach.value.TINH_THICONG
      else tinh_tc_id = this.tinh_id
      this.dgvTienTrinhSMS.list = this.callApiWrapper(api.sp_tt_tientrinh_sms, {
        vphanvung_id: this.tinh_tc_id,
        vtientrinhtc_id: vtientrinhtc_id
      })
      // dgvTienTrinhSMS.CellDoubleClick += new DataGridViewCellEventHandler(dgvTienTrinhSMS_CellDoubleClick)
    },
    async dgvTienTrinhSMS_CellDoubleClick() {
      // let dgvTienTrinhSMS = sender as DataGridView;
      let vnguoi_cn = dgvTienTrinhSMS.Rows[e.RowIndex].Cells.colNguoiGui.value
      // let vngay_cn = let.Format("{0:dd/MM/yyyy HH:mm:ss}", dgvTienTrinhSMS.Rows[e.RowIndex].Cells.colNgayGui.value);
      let vso_dt = dgvTienTrinhSMS.Rows[e.RowIndex].Cells.colSoDT.value
      let vnoidung = dgvTienTrinhSMS.Rows[e.RowIndex].Cells.colNoiDung.value
      // this.$toast.warning(let.Format("Người gửi: {0}\r\nNgày gửi: {1}\r\nSĐT nhận: {2}\r\nNội dung:\r\n{3}", vnguoi_cn, vngay_cn, vso_dt, vnoidung), "Xem tin nhắn");
    },
    // delegate void SetDataSource_S(GridControl gridcontrol, System.Data.let data);
    // void SetDataSource(GridControl gridcontrol, System.Data.let data)
    // {
    //     if (gridcontrol.InvokeRequired)
    //     {
    //         SetDataSource_S d = new SetDataSource_S(SetDataSource);
    //         gridcontrol.Invoke(d, new object[] { gridcontrol, data });
    //     }
    //     else
    //     {
    //         gridcontrol.list = data.Copy();
    //     }
    // },
    // delegate void Coltrols_SetVisible_S(Control control, let visible);
    // void Controls_SetVisible(Control control, let visible)
    // {
    //     if (control.InvokeRequired)
    //     {
    //         control.Invoke(new Coltrols_SetVisible_S(Controls_SetVisible), new object[] { control, visible });
    //     }
    //     else
    //     {
    //         control.visible = visible;
    //     }
    // },
    async NAP_GRD_TIENTRINH() {
      if (this.gridDanhSach.value) {
        // try {
        let vhdtb_id = this.gridDanhSach.value.hdtb_tc
        if (vhdtb_id < 0) {
          this.gridTienTrinh.list = []
          return
        }
        let _tinh_id = this.gridDanhSach.value.tinh_id
        let tinh_tc_id = 0
        if (this.tinh_khdn.includes(_tinh_id)) tinh_tc_id = this.gridDanhSach.value.tinh_thicong
        else tinh_tc_id = this.tinh_id
        //wtf
        // Tinh_TC _tinh_tc = new BanCheoBase().tinh_thicong(tinh_tc_id);
        // this.gridTienTrinh.list = await this.callApiWrapper(api.sp_tt_tientrinh_tc, {
        //   vhdtb_id: vhdtb_id
        // })
        this.gridTienTrinh.list = await this.callApiWrapper(api.sp_khdn_lay_ds_tientrinh_tc, {
          vphanvung_id: this.tinh_ph,
          vhdtb_id: vhdtb_id,
          vtinh_id: this.gridDanhSach.value.tinh_id,
          vhdtb_tc: this.gridDanhSach.value.hdtb_tc,
          vtinh_tc: this.gridDanhSach.value.tinh_tc
        })
        // } catch {}
      } else {
        this.gridTienTrinh.list = []
        this.txtMaGD.value = ''
        this.txtMaTB.value = ''
        this.txtTenTB.value = ''
        this.txtDiaChiLD.value = ''
      }
    },
    async HT_DLG_XOA_TIENTRINH(vtientrinhtc_id) {
      let vhdtb_id = this.gridDanhSach.value.HDTB_TC
      let _tinh_id = this.gridDanhSach.value.TINH_ID
      let tinh_tc_id = 0
      if (this.tinh_khdn.includes(_tinh_id)) tinh_tc_id = this.gridDanhSach.value.TINH_THICONG
      else tinh_tc_id = tinh_id
      // Tinh_TC _tinh_tc = new BanCheoBase().tinh_thicong(tinh_tc_id);
      let sdt = '-1'
      let dt = await this.callApiWrapper(api.sp_tt_tientrinh_sms, {
        vphanvung_id: tinh_tc_id,
        vtientrinhtc_id: vtientrinhtc_id
      })
      //bancheo.GET_DATA_SQL(_tinh_tc, str);
      if (dt.length > 0) sdt = dt.Rows[0].so_dt
      if (sdt != '-1') {
        this.$toast.warning('Bạn không thể xóa tiến trình này\r\nTiến trình đã được gửi tin nhắn đến ' + sdt)
        return
      }
      if (!confirm('Bạn có thực sự muốn xóa tiến trình được chọn không ?')) {
        try {
          await this.callApiWrapper(api.sp_delete_tientrinh_tc, {
            vphanvung_id: tinh_tc_id,
            vtientrinhtc_id: vtientrinhtc_id
          })
          await this.NAP_GRD_TIENTRINH()
        } catch (ex) {
          this.$toast.error(`${ex}`)
        }
      }
    },
    async tsbtnTraPhieuTinh_Click() {
      if (this.tinh_khdn.includes(this.tinh_id)) {
        this.$toast.success('Tính năng này chỉ áp dụng với các phiếu được chuyển từ 63 TTP.')
        return
      }
      let kq = await api.kiemtra_thoaitra_hoanthanh_net(this.axios, {
        vphanvung_id: this.tinh_id,
        vhdtb_id: this.hdtb_id,
        vphieu_tinh_id: this.gridDanhSach.value.phieu_tinh_id,
        vloai_check: 1
      })
      if (kq.data.error_code != 'BSS-00000000') {
        this.$toast.error(`${kq.data.message}`)
        return
      }
      this.frmTraPhieuVIP.input = {
        vds_phieu: [
          {
            TINH_ID: this.tinh_id,
            PHIEU_ID: this.gridDanhSach.value.phieu_id,
            PHIEU_TINH_ID: this.gridDanhSach.value.phieu_tinh_id,
            // vma_tb: this.gridDanhSach.value.ma_tb,
            // vso_dt_giao: this.gridDanhSach.value.so_dt_giao,
            HDTB_ID: this.hdtb_id,
            MA_GD: this.gridDanhSach.value.ma_gd
            // vloaitb_id: this.gridDanhSach.value.loaitb_id
          }
        ]
      }
      this.ejsDialog.name = 'frmTraPhieuVIP'
      this.ejsDialog.header = 'Trả phiếu'
      this.$refs.ejsDialog.show()
      // // frmTraPhieuVIP frm = new frmTraPhieuVIP();
      // frm.tinh = new BanCheoBase().tinh_thicong(tinh_id)
      // frm.phieu_id = this.gridDanhSach.value.PHIEU_ID
      // frm.phieu_tinh_id = this.gridDanhSach.value.PHIEU_TINH_ID
      // frm.vma_tb = this.gridDanhSach.value.MA_TB
      // frm.vso_dt_giao = this.gridDanhSach.value.SO_DT_GIAO
      // frm.hdtb_id = hdtb_id
      // frm.vloaitb_id = CURRENT_LOAITB_ID
      // frm.ShowDialog()
      // if (frm.chapnhan) {
      //   await this.HienThiDanhSachHDTB()
      // }
      //tsbtnLayTT.PerformClick();
    },
    async tsbtnGiaoPhieuTinh_Click() {
      if (this.gridDanhSach.value == null) {
        this.$toast.warning('Vui lòng chọn phiếu!')
        return
      }
      if (this.tinh_khdn.includes(this.tinh_id)) {
        this.ejsDialog.name = 'frmTraCuuHopDongBC'
        this.ejsDialog.header = 'TRA CỨU VÀ ĐIỀU HƯỚNG HỢP ĐỒNG'
        // this.$refs.frmTraCuuHopDongBC._data.thongtintimkiem.thongtin_tracuu = this.txtMaTB.value
        // this.frmTraCuuHopDongBC.input = {
        //   isPopup: true,
        //   ma_tb: this.gridDanhSach.value.ma_tb
        // }
        this.frmTraCuuHopDongBC.input = this.gridDanhSach.value
        this.frmTraCuuHopDongBC.input.isPopup = true
        this.$refs.ejsDialog.show()
        setTimeout(() => {
          this.$refs.frmTraCuuHopDongBC.thongtintimkiem.dis_tracuu_theo = true
          this.$refs.frmTraCuuHopDongBC.thongtintimkiem.dis_loaihd = true
          this.$refs.frmTraCuuHopDongBC.thongtintimkiem.dis_dvvt = true
          this.$refs.frmTraCuuHopDongBC.thongtintimkiem.dis_ngayyc = true
          this.$refs.frmTraCuuHopDongBC.thongtintimkiem.dis_thongtin_tracuu = true
          this.$refs.frmTraCuuHopDongBC.thongtintimkiem.dis_ma_hd_ttkd = true
          this.$refs.frmTraCuuHopDongBC.is_nv_net = true
          // this.$refs.frmTraCuuHopDongBC.thongtintimkiem.ma_gd = this.txtMaGD.value
        }, 500)
      } else if ([96, 98].includes(this.tinh_id)) {
        try {
          this.loading(true)
          let kq = await api.chuyen_tc_bancheo(this.axios, {
            hdtb_id: this.gridDanhSach.value.hdtb_id,
            tinh_ban: this.gridDanhSach.value.tinh_id,
            hdkh_id: this.gridDanhSach.value.hdkh_id,
            phieu_id: this.gridDanhSach.value.phieu_id,
            loaihd_id: this.gridDanhSach.value.loaihd_id,
            dichvuvt_id: this.gridDanhSach.value.dichvuvt_id
          })
          if (kq.data.error_code == 'BSS-00000000') {
            this.$toast.success(`${kq.data.data}`)
          } else {
            this.$toast.error(`${kq.data.message}`)
          }
        } catch (e) {
        } finally {
          this.loading(false)
        }
      } else this.$toast.success('Hiện giao tỉnh chỉ áp dụng cho phiếu từ ban KHDN')
    },
    async tsbtnBSTT_Click() {
      if (this.loaitb_id == LoaiHinhTB.Voice_Brandname) {
        this.frmDanhSachSoThanhVien.input = {
          hdtb_id: this.hdtb_id,
          thuebao_id: 0,
          tinh_id: this.tinhthicong_id
        }
        this.ejsDialog.name = 'frmDanhSachSoThanhVien'
        this.ejsDialog.header = 'Danh sách số thành viên'
        this.$refs.ejsDialog.show()
      } else {
        this.frmCapNhatTSKT_CNTT.input = {
          dichvu_vt_id: this.dichvuvt_id,
          loaitb_id: this.loaitb_id,
          hdtb_id: this.hdtb_id,
          // tinh: tinh_tc,
          tinh_ph: this.tinhthicong_id
        }
        this.ejsDialog.name = 'frmCapNhatTSKT_CNTT'
        this.ejsDialog.header = 'Cập nhật TSKT CNTT'
        this.$refs.ejsDialog.show()
      }
    },
    async tsbtnTC_CTTC_Click() {
      // let frm = new frmTraCuuPhieuTCBC()
      // frm.vma_tb = txtMaGD.value
      // frm.ShowDialog()
      // this.frmTraCuuPhieuTCBC.input = {
      //   isPopup: true
      // }
      this.ejsDialog.name = 'frmTraCuuPhieuTCBC'
      this.ejsDialog.header = 'TRẢ CỨU CHI TIẾT ĐIỀU HÀNH THI CÔNG TỈNH KHÁC'
      this.$refs.ejsDialog.show()
      setTimeout(() => {
        this.$refs.frmTraCuuPhieuTCBC.isPopup = true
        // this.$refs.frmTraCuuPhieuTCBC.formThongTinPhieuYeuCau.maTBTinh = this.txtMaGD.value
        this.$refs.frmTraCuuPhieuTCBC.formThongTinPhieuYeuCau.maGiaoDich = this.txtMaGD.value
        this.$refs.frmTraCuuPhieuTCBC.onClickSearch()
        // this.$refs.frmTraCuuPhieuTCBC.formThongTinPhieuYeuCau.tinhId
      }, 500)
      // this.$toast.info('frmTraCuuPhieuTCBC')
    },
    async tsbtnTC_TTPD_Click() {
      if (this.gridDanhSach.value) {
        const route = this.$router.resolve({
          name: 'DeclareChannelInfo',
          query: { hdtb_id: this.gridDanhSach.value.hdtb_id, tinh_id: this.gridDanhSach.value.tinh_id, tag: 'tag', tinhcq_id: this.gridDanhSach.value.tinh_thicong }
        })
        window.open(route.href, '_blank')
      } else {
        this.$toast.warning('Chưa chọn phiếu!')
      }
    },
    tsbtnTraPhieuHNTH_Click() {
      // this.$toast.info('Chưa có khảo sát')
      this.$refs.frmChiTietKenhTheoPhien.showModal()
      // frm.loaitb_id = loaitb_id
      // frm.hdtb_id = hdtb_id
      // frm.tinh_id = tinh_id
      // frm.tinh_ph = tinh_tc
      // frm.ShowDialog()
      setTimeout(() => {
        this.$refs.frmChiTietKenhTheoPhien.input.vloaitb_id = this.loaitb_id
        this.$refs.frmChiTietKenhTheoPhien.input.vhdtb_id = this.hdtb_id
        this.$refs.frmChiTietKenhTheoPhien.tinh_id = this.tinh_id
        this.$refs.frmChiTietKenhTheoPhien.input.vphanvung_id = this.tinh_id
        // this.$refs.frmChiTietKenhTheoPhien.handleShowModal()
        this.$refs.frmChiTietKenhTheoPhien.showModal()
      }, 500)
    },
    tsbtnCapnhatKenhluong_Click() {
      // Sử dụng `window.open()` để mở một tab/window mới
      let newTab = window.open('', '_blank')
      // Sử dụng `this.$router.resolve()` để tạo URL cho route cụ thể
      let resolvedRoute = this.$router.resolve({
        path: '/htkh/DeclareChannelInfo',
        query: {
          hdtb_id: this.hdtb_id,
          tinh_id: this.tinh_id,
          tinhcq_id: this.tinh_id
        }
      })
      // Mở URL đã tạo trong tab/window mới
      newTab.location.href = resolvedRoute.href
    },
    async gridTienTrinhRowSelected(e) {
      if (e && e.data) {
        this.gridTienTrinh.value = e.data
      }
    },
    async btnGhiLaiHangLoat_Click() {
      let listed = this.gridDanhSach.list.filter((e) => e.selected && e.selected == true)
      let noidung = this.txtNoidungTHHangLoat.value
      let thuchien = this.cboKetQuaThucHienHangLoat.value
      if (thuchien == '') {
        this.$toast.warning('Chưa chọn trạng thái thực hiện/trả phiếu')
        return
      }
      if (noidung == '') {
        this.$toast.warning('Chưa nhập nội dung thực hiện')
        return
      }
      for (let i = 0; i < this.gridView2.list.length; i++) {
        let rowHandler = i
        if (rowHandler != -1) {
          let so_dt_ct = this.gridView2.value.SO_DT_CT
          let kq = await this.callApiWrapper(api.capnhat_noidung_thuchien_thuebao, {
            vphanvung_id: this.tinh_id,
            vhdtb_id: this.hdtb_id,
            vsotb: this.so_dt_ct,
            vthuchien: this.thuchien,
            vnoidung: this.noidung
          })
          if (kq != 'OK') {
            this.$toast.error(`${kq}`)
            return
          }
        }
      }
      if (this.phieu_id == 0) return
      if (
        this.tinh_khdn.includes(this.tinh_id) &&
        (await this.callApiWrapper(api.fn_kiemtra_hoanthienphieu_bancheo_vnpti, {
          vloaitb_id: this.loaitb_id
        })) != 'OK'
      )
        await this.callApiWrapper(api.update_nd_thuchien, {
          phieu_id: this.phieu_id,
          nd_thuchien: this.txtNoidungTHHangLoat.value?.trim()
        })
      else {
        await this.callApiWrapper(api.capnhat_nd_thuchien_giaophieu_ph_net, {
          // phanvung_id: this.gridDanhSach.value.tinh_id,
          phanvung_id: this.$root.token.getPhanVungID(),
          phieu_id: this.phieu_id,
          nd_thuchien: this.txtNoidungTHHangLoat.value?.trim()
        })
        this.txtNoiDungTH.value = this.txtNoidungTHHangLoat.value
      }
      this.$toast.success('Cập nhật thành công')
      let dt = await this.callApiWrapper(api.lay_ds_sothuebao_net, {
        vphanvung_id: this.tinh_id,
        vhdtb_id: this.hdtb_id
      })
      this.gridControl1.list = dt
    },
    async giaoviec_success() {
      let ds = []
      ds = await this.callApiWrapper(api.lay_ds_nhanvien_theo_phieu_id, { phieu_id: this.phieu_id, kieu_id: 2 })
      await this.HienThiDSNV(ds)
    },
    async frmNoiDung_btnGhiLai_Click() {
      await this.NAP_GRD_TIENTRINH()
    },
    async frmTraCuuHopDongBC_onChuyenThiCongSuccess() {
      await this.btnLayTTMoi_Click()
    },
    async frmGiaoPhieu_Net_tsbtnGiaoPhieu_Click(data) {
      if (data.status) {
        // await this.btnLayTTMoi_Click()
        await this.HienThiDS_PhieuTH(this.phieu_id)
      }
    },
    async frmTraPhieuTC_btnTraphieu_Click(data) {
      if (data.status) {
        await this.btnLayTTMoi_Click()
      }
    },
    ejsDialogBeforeOpen() {
      let width = null
      if (this.ejsDialog.name == 'frmNhapText') {
        width = 'auto'
      } else {
        width = '95%'
      }
      if (this.ejsDialog.name == 'frmTraCuuHopDong') {
        this.ejsDialog.config.height = 800
      } else {
        this.ejsDialog.config.height = 'auto'
      }
      this.ejsDialog.config = {
        ...this.ejsDialog.config,
        width: width,
        // position: { X: 100, Y: 100 }
        position: { X: 'center', Y: 'center' }
      }
      // this.ejsDialog.config.height = 'auto'
      // this.$refs.ejsDialog.refreshPosition()
      // this.ejsDialog.config.enableResize = false
      // this.ejsDialog.config.allowDragging = false
      // this.ejsDialog.config.position = { X: 'center', Y: 'center' }
      // this.ejsDialog.config.position = { X: 420, Y: 14 }
      // }, 3000)
    },
    ejsDialogBeforeClose() {
      this.ejsDialog.name = null
      // this.$refs.ejsDialog.refreshPosition()
      this.ejsDialog.config.width = '0'
      this.ejsDialog.config.height = '0'
      this.ejsDialog.config.position = { X: 'center', Y: 'center' }
    },
    ChuanHoaTen(ten) {
      if (!ten) return ''
      let tenNew = ''
      let strCon = ten.trim()
      let strXL = ''
      let k = 0
      let found = true
      if (strCon.length < 1) return
      strCon = strCon.replaceAll(/\s+/g, ' ')
      try {
        while (found) {
          k = strCon.indexOf(' ')
          if (k > 0) {
            strXL = strCon.substring(0, k)
            strCon = strCon.substring(k + 1)
            strXL = this.ChuyenChuHoa(strXL)
            if (tenNew !== '') {
              tenNew += ' ' + strXL
            } else tenNew = strXL
            found = true
          } else found = false
        }
        if (strCon !== '') {
          strXL = strCon
          strXL = this.ChuyenChuHoa(strXL)
          if (tenNew !== '') {
            tenNew = tenNew + ' ' + strXL
          } else {
            tenNew = strXL
          }
        }
        return tenNew
      } catch (e) {
        return ten
      }
    },
    ChuyenChuHoa(str) {
      let kq = ''
      let regex = /[A-Z][A-Z]/g
      if (regex.test(str)) kq = str.toUpperCase()
      else {
        if (str[0] === '(') {
          kq = str[0] + str[1].toUpperCase() + str.substring(2).toLowerCase()
        } else {
          kq = str[0].toUpperCase() + str.substring(1).toLowerCase()
        }
      }
      return kq
    },
    async ChuanHoa_XML(Xau) {
      let result = ''
      result = Xau.replace('&', '&amp;')
      result = result.replace('<', '&lt;')
      result = result.replace('>', '&gt;')
      result = result.replace("'", '&apos;')
      result = result.replace('"', '&quot;')
      return result
    },
    async frmTraPhieuVIP_onBtnTraphieuClicked() {
      await this.btnLayTTMoi_Click()
      this.$refs.ejsDialog.hide()
    },
    async frmCapnhatCNTT_XacNhan() {
      await this.HT_THONGSO_KYTHUAT(this.gridDanhSach.value.tinh_id)
    }
  }
}
</script>
<style scoped>
* {
  font-size: 12px;
}
.disabledInput {
  pointer-events: none;
  color: #666 !important;
  border-color: #9999 !important;
}
.disabledInput > a {
  color: #666 !important;
}
.section-title-wrapper {
  padding: 8px;
  border-radius: 5px;
  background: #c2e6fc;
  display: flex;
}
.section-title-wrapper > .section-title {
  font-size: 14px;
  font-weight: bold;
}
.section-radio-menu {
  margin: 0 5px 0 0;
  color: red;
  font-weight: bold;
}
.bottom-container {
  height: 1000px;
  background: #ebf3fe;
  display: flex;
  flex-direction: column;
}
@media (max-width: 575.98px) {
  .bottom-container {
    height: auto;
    background: #ebf3fe;
    display: flex;
    flex-direction: column;
  }
}
.section-title-through-wrapper {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding: 10px 0;
}
.line-through {
  flex-grow: 1;
  height: 1px;
  margin: 0 0 0 3px;
  background: #c3c3cb;
  z-index: 1;
}
.section-title-through-text {
  font-size: 14px;
  font-weight: bold;
  color: #0d2097;
}
.info-wrapper {
  min-height: 700px;
  max-height: 700px;
  overflow-y: scroll;
  padding: 10px 10px;
}
#frmHoanCongMegaWan_Net {
  position: absolute !important;
  top: 0px !important;
  left: 0px !important;
  right: 0px !important;
  bottom: 0px !important;
  overflow: auto;
  overflow-x: scroll;
}
.e-dialog {
  overflow-y: auto;
}
.e-dlg-header-content {
  display: none;
}
</style>