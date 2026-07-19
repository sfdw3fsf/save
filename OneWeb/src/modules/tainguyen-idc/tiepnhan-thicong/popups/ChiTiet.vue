<template>
  <div>
    <ejs-dialog v-show="isModal" :enableResize="false" :allowDragging="false" :visible="false"
      :animationSettings="animationSettings" ref="dlgChiTietCongViec" :header="'Thông tin công việc thực hiện'"
      :showHeader="false" :showCloseIcon="true" width="80%" height="auto" :target="target" :close="onDialogClosePopup"
      :position="dialogPosition">
      <div class="modal-content popup-box">
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <b-link :class="{ disabled: pPhieuThiCongId != null && currentItem.TENTAT_TT != 'THICONG_YCM' }"
                href="javascript:void(0)" @click="onGhiLai">
                <a><span class="icon one-save"></span>Ghi lại </a>
              </b-link>
            </li>
          </ul>
        </div>
        <div class="popup-body">
          <div class="box-form" v-if="pLoaiPhieu == 1">
            <div class="legend-title">
              <div>
                Thông tin phiếu SO
              </div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w160">Mã thuê bao</div>
                  <div class="value">
                    <!-- <div class="input-more-button -right">
                    <button :disabled="pPhieuThiCongId != null" class="btn" @click="onTraCuuThueBao()">
                      <span class="-ap icon-more_horiz"></span>
                    </button>
                  </div> -->
                    <input type="text" class="form-control" v-model="currentItem.MA_TB" :disabled="true" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w160">Mã giao dịch</div>
                  <div class="value">
                    <input type="text" class="form-control" :disabled="true" v-model="currentItem.MA_GD" />
                  </div>
                </div>
              </div>
            </div>

            <div class="row">
              <!-- <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w160">Loại phối hợp</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="currentItem.LOAIPH" :disabled="true" />
                </div>
              </div>
            </div> -->
              <div class="col-sm-12 col-12">
                <div class="info-row">
                  <div class="key w160">Tên khách hàng</div>
                  <div class="value">
                    <input type="text" class="form-control" :disabled="true" v-model="currentItem.TEN_KH" />
                  </div>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w160">Số hợp đồng</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="currentItem.SO_HD" :disabled="true" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w160">Tên gói cước</div>
                  <div class="value">
                    <input type="text" class="form-control" :disabled="true" v-model="currentItem.GOICUOC" />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="box-form">
            <div class="legend-title">
              <div>
                Thông tin phiếu thi công
              </div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w160">Mã công việc <span class="required">(*)</span></div>
                  <div class="value">
                    <input type="text" class="form-control" :disabled="true" v-model="currentItem.MA_CV" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w160">Đơn vị thực hiện <span class="required">(*)</span></div>
                  <div class="value">
                    <SelectExt v-model="currentItem.DONVI_ID" placeholder="-- Chọn đơn vị thực hiện --"
                      dataTextField="TEN" dataValueField="ID" :dataSource="donViOptions" @change="onChangeDonViThucHien"
                      :disabled="pPhieuThiCongId != null" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w160">Loại công việc <span class="required">(*)</span></div>
                  <div class="value">
                    <SelectExt v-model="currentItem.LOAICV_ID" placeholder="-- Chọn loại công việc --"
                      dataTextField="TEN" dataValueField="LOAICV_ID" :dataSource="loaiCongVienOptions"
                      :disabled="pPhieuThiCongId != null" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w160">Người thực hiện <span class="required">(*)</span></div>
                  <div class="value">
                    <SelectExt v-model="currentItem.NHANVIEN_TH_ID" placeholder="-- Chọn nhân viên thực hiện --"
                      dataTextField="TEN_NV" dataValueField="NHANVIEN_ID" :dataSource="dsNhanVienThucHien"
                      :disabled="!currentItem.DONVI_ID" />
                  </div>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w160">Hạng mục triển khai <span class="required">(*)</span></div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="currentItem.TEN_CV" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w160">Trạng thái thực hiện <span class="required">(*)</span></div>
                  <div class="value">
                    <SelectExt v-model="currentItem.TRANGTHAI_ID" dataTextField="TEN" dataValueField="ID"
                      :dataSource="danhMucTrangThaiOptions" :disabled="true" />
                  </div>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="col-sm-3 col-12">
                <div class="info-row">
                  <div class="key w160">Tại IDC# <span class="required">(*)</span></div>
                  <div class="value">
                    <SelectExt v-model="currentItem.IDC_ID" dataTextField="TEN" dataValueField="ID"
                      :placeHolder="'-- Chọn IDC --'" :dataSource="danhMucIDCOptions" :disabled="!currentItem.DONVI_ID"
                      @change="onChangeIDC" />
                  </div>
                </div>
              </div>
              <div class="col-sm-3 col-12">
                <div class="info-row">
                  <div class="key w160">Mặt sàn <span class="required">(*)</span></div>
                  <div class="value">
                    <SelectExt v-model="currentItem.MATSAN_ID" dataTextField="TEN" dataValueField="ID"
                      :dataSource="danhMucMatSanOptions" :disabled="!currentItem.IDC_ID" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w160">Giá trị khai báo <span class="required">(*)</span></div>
                  <div class="value">
                    <input type="number" class="form-control" v-model="currentItem.GIATRI_KHAIBAO" />
                  </div>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w160">Căn cứ yêu cầu</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="currentItem.CANCU_YC" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w160">Thời điểm yêu cầu</div>
                  <div class="value">
                    <ejs-datepicker v-model="currentItem.NGAY_YC" :format="'dd/MM/yyyy hh:mm:ss'" :allowEdit="false"
                      style="background: white; color: #495057" ref="thoiDiemYC" />
                  </div>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w160">Mã công việc cha</div>
                  <div class="value">
                    <!-- <ComboboxGrid
                    :dataSource="dsPhieuCongViecCha"
                    :enabled="false"
                    :allowPaging="true"
                    :showColumnCheckbox="true"
                    :placeHolder="'-- Chọn phiếu công việc --'"
                    v-model="currentItem.MACV_CHA_ID"
                    textField="TEN_NV"
                    valueField="NHANVIEN_ID"
                    :enabledSelectFirstRow="false"
                    :columns="[
                      { fieldName: 'MA_NV', headerText: 'Mã nhân viên' },
                      { fieldName: 'TEN_NV', headerText: 'Tên nhân viên' },
                      { fieldName: 'TEN_DV', headerText: 'Đơn vị' }
                    ]"
                  /> -->
                    <input type="text" class="form-control" :disabled="true" v-model="currentItem.MA_CV_CHA" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12" v-if="pLoaiPhieu == 0">
                <div class="info-row">
                  <div class="key w160">Người phối hợp</div>
                  <div class="value">
                    <NhanVienPhoiHopSelector v-model="currentItem.NHANVIEN_PH_ID_LIST" :dataSource="dsNhanVienThucHien"
                      textField="TEN_NV" valueField="NHANVIEN_ID" :disabled="!currentItem.DONVI_ID"
                      :placeHolder="'-- Chọn nhân viên phối hợp --'" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w160">File đính kèm</div>
                  <div class="value">
                    <div class="input-more-button -right">
                      <label for="upload" class="btn"
                        v-if="!(pPhieuThiCongId != null) || currentItem.TENTAT_TT == 'THICONG_YCM'">
                        <span class="-ap icon-more_horiz"></span>
                        <input multiple ref="fileInput" type="file" id="upload" style="display: none"
                          @change="onChangeFile" @click="$refs.fileInput.value = null" />
                      </label>
                      <div class="input-more-button -right">
                        <label class="btn">
                          <span class="icon nc-icon-outline ui-1_eye-17" @click="onViewFile"></span>
                        </label>
                        <input type="text" class="form-control" :value="getFileDisplayText" readonly />
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w160">{{ getTextChiTiet(currentItem.LOAITN_ID) }}</div>
                  <div class="value">
                    <!-- <ejs-datepicker
                    v-model="currentItem.NGAY_HT"
                    :format="'dd/MM/yyyy hh:mm:ss'"
                    :allowEdit="false"
                    style="background: white; color: #495057"
                    ref="ngayHT"
                  /> -->
                    <input type="text" class="form-control" :disabled="true" v-model="currentItem.CHITIET" />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="box-form" v-if="dsPhieuTCCon && dsPhieuTCCon.length > 0">
            <div class="legend-title">
              <div>
                Danh sách phiếu thi công con
              </div>
            </div>
            <div class="row">
              <div class="col-12">
                <DataGrid ref="grdDSPhieuTCCon" :allowPaging="true" :enablePagingServer="false"
                  :showColumnCheckbox="false" :dataSource="dsPhieuTCCon" v-bind:columns="[
                    { fieldName: 'LOAITN', headerText: 'Loại tài nguyên', textAlign: 'Left', width: 135 },
                    { fieldName: 'LOAICV', headerText: 'Loại công việc', textAlign: 'Left', width: 135 },
                    { fieldName: 'MA_DVGT', headerText: 'Mã DVGT', textAlign: 'Left', width: 100 },
                    { fieldName: 'TEN_DVGT', headerText: 'Tên DVGT', textAlign: 'Left', width: 160 },
                    {
                      fieldName: 'TEN_CV',
                      headerText: 'Tên công việc',
                      textAlign: 'Center',
                      width: 140,
                      template: tenCVTemplate(this)
                    },
                    {
                      fieldName: 'DVTH_ID',
                      headerText: 'Đơn vị thực hiện',
                      textAlign: 'Center',
                      width: 150,
                      template: donViThucHienTemplate(this)
                    },
                    {
                      fieldName: 'NV_TH_ID',
                      headerText: 'Người thực hiện',
                      textAlign: 'Center',
                      width: 150,
                      template: nguoiThucHienTemplate(this)
                    },
                    {
                      fieldName: 'GIATRI_KHAIBAO',
                      headerText: 'Giá trị khai báo',
                      textAlign: 'Center',
                      width: 140,
                      template: giaTriKhaiBaoTemplate(this)
                    },
                    {
                      fieldName: 'NGAY_YCHT',
                      headerText: 'Ngày yêu cầu hoàn thành',
                      textAlign: 'Center',
                      width: 200,
                      template: ngayYCHTTemplate(this)
                    },
                    { fieldName: 'NGAY_YC', headerText: 'Ngày yêu cầu', textAlign: 'Center', width: 150 },
                    {
                      fieldName: 'NOIDUNG',
                      headerText: 'Nội dung',
                      textAlign: 'Center',
                      width: 120,
                      template: noiDungTemplate(this)
                    },
                    {
                      fieldName: 'CHITIET',
                      headerText: 'Chi tiết',
                      textAlign: 'Center',
                      width: 120,
                      template: chiTietTemplate(this)
                    }
                  ]" />
              </div>
            </div>
          </div>
        </div>
      </div>
      <SearchPhieuKenhBan ref="refSearchPhieuKenhBan" @beforClosePopup="onBeforClosePopupSearchPhieuSO">
      </SearchPhieuKenhBan>
      <ModalUploadFile ref="refModalUploadFile" @deleteFile="onDeleteFile"></ModalUploadFile>
    </ejs-dialog>
    <div v-show="!isModal">
      <div class="box-form" v-if="pLoaiPhieu == 1">
        <div class="legend-title">
          <div>
            Thông tin phiếu SO
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w160">Mã thuê bao</div>
              <div class="value">
                <!-- <div class="input-more-button -right">
                    <button :disabled="pPhieuThiCongId != null" class="btn" @click="onTraCuuThueBao()">
                      <span class="-ap icon-more_horiz"></span>
                    </button>
                  </div> -->
                <input type="text" class="form-control" v-model="currentItem.MA_TB" :disabled="true" />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w160">Mã giao dịch</div>
              <div class="value">
                <input type="text" class="form-control" :disabled="true" v-model="currentItem.MA_GD" />
              </div>
            </div>
          </div>
        </div>

        <div class="row">
          <!-- <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w160">Loại phối hợp</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="currentItem.LOAIPH" :disabled="true" />
                </div>
              </div>
            </div> -->
          <div class="col-sm-12 col-12">
            <div class="info-row">
              <div class="key w160">Tên khách hàng</div>
              <div class="value">
                <input type="text" class="form-control" :disabled="true" v-model="currentItem.TEN_KH" />
              </div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w160">Số hợp đồng</div>
              <div class="value">
                <input type="text" class="form-control" v-model="currentItem.SO_HD" :disabled="true" />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w160">Tên gói cước</div>
              <div class="value">
                <input type="text" class="form-control" :disabled="true" v-model="currentItem.GOICUOC" />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">
          <div>
            Thông tin phiếu thi công
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w160">Mã công việc <span class="required">(*)</span></div>
              <div class="value">
                <input type="text" class="form-control" :disabled="true" v-model="currentItem.MA_CV" />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w160">Đơn vị thực hiện <span class="required">(*)</span></div>
              <div class="value">
                <SelectExt v-model="currentItem.DONVI_ID" placeholder="-- Chọn đơn vị thực hiện --" dataTextField="TEN"
                  dataValueField="ID" :dataSource="donViOptions" @change="onChangeDonViThucHien"
                  :disabled="pPhieuThiCongId != null" />
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w160">Loại công việc <span class="required">(*)</span></div>
              <div class="value">
                <SelectExt v-model="currentItem.LOAICV_ID" placeholder="-- Chọn loại công việc --" dataTextField="TEN"
                  dataValueField="LOAICV_ID" :dataSource="loaiCongVienOptions" :disabled="pPhieuThiCongId != null" />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w160">Người thực hiện <span class="required">(*)</span></div>
              <div class="value">
                <SelectExt v-model="currentItem.NHANVIEN_TH_ID" placeholder="-- Chọn nhân viên thực hiện --"
                  dataTextField="TEN_NV" dataValueField="NHANVIEN_ID" :dataSource="dsNhanVienThucHien"
                  :disabled="!currentItem.DONVI_ID || !isEditMode" />
              </div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w160">Hạng mục triển khai <span class="required">(*)</span></div>
              <div class="value">
                <input type="text" class="form-control" v-model="currentItem.TEN_CV"  :disabled="!isEditMode"/>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w160">Trạng thái thực hiện <span class="required">(*)</span></div>
              <div class="value">
                <SelectExt v-model="currentItem.TRANGTHAI_ID" dataTextField="TEN" dataValueField="ID"
                  :dataSource="danhMucTrangThaiOptions" :disabled="true" />
              </div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w160">Tại IDC# <span class="required">(*)</span></div>
              <div class="value">
                <SelectExt v-model="currentItem.IDC_ID" dataTextField="TEN" dataValueField="ID"
                  :placeHolder="'-- Chọn IDC --'" :dataSource="danhMucIDCOptions" :disabled="!currentItem.DONVI_ID || !isEditMode"
                  @change="onChangeIDC" />
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w160">Mặt sàn <span class="required">(*)</span></div>
              <div class="value">
                <SelectExt v-model="currentItem.MATSAN_ID" dataTextField="TEN" dataValueField="ID"
                  :dataSource="danhMucMatSanOptions" :disabled="!currentItem.IDC_ID || !isEditMode" />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w160">Giá trị khai báo <span class="required">(*)</span></div>
              <div class="value">
                <input type="number" class="form-control" v-model="currentItem.GIATRI_KHAIBAO" :disabled="!isEditMode" />
              </div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w160">Căn cứ yêu cầu</div>
              <div class="value">
                <input type="text" class="form-control" v-model="currentItem.CANCU_YC" :disabled="!isEditMode" />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w160">Thời điểm yêu cầu</div>
              <div class="value">
                <ejs-datepicker v-model="currentItem.NGAY_YC" :format="'dd/MM/yyyy hh:mm:ss'" :allowEdit="false"
                  style="background: white; color: #495057" ref="thoiDiemYC" :enabled="isEditMode"/>
              </div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w160">Mã công việc cha</div>
              <div class="value">
                <!-- <ComboboxGrid
                    :dataSource="dsPhieuCongViecCha"
                    :enabled="false"
                    :allowPaging="true"
                    :showColumnCheckbox="true"
                    :placeHolder="'-- Chọn phiếu công việc --'"
                    v-model="currentItem.MACV_CHA_ID"
                    textField="TEN_NV"
                    valueField="NHANVIEN_ID"
                    :enabledSelectFirstRow="false"
                    :columns="[
                      { fieldName: 'MA_NV', headerText: 'Mã nhân viên' },
                      { fieldName: 'TEN_NV', headerText: 'Tên nhân viên' },
                      { fieldName: 'TEN_DV', headerText: 'Đơn vị' }
                    ]"
                  /> -->
                <input type="text" class="form-control" :disabled="true" v-model="currentItem.MA_CV_CHA" />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12" v-if="pLoaiPhieu == 0">
            <div class="info-row">
              <div class="key w160">Người phối hợp</div>
              <div class="value">
                <NhanVienPhoiHopSelector v-model="currentItem.NHANVIEN_PH_ID_LIST" :dataSource="dsNhanVienThucHien"
                  textField="TEN_NV" valueField="NHANVIEN_ID" :disabled="!currentItem.DONVI_ID || !isEditMode"
                  :placeHolder="'-- Chọn nhân viên phối hợp --'" />
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w160">File đính kèm</div>
              <div class="value">
                <div class="input-more-button -right">
                  <label for="upload" class="btn"
                    v-if="!(pPhieuThiCongId != null) || currentItem.TENTAT_TT == 'THICONG_YCM' || !isEditMode">
                    <span class="-ap icon-more_horiz"></span>
                    <input multiple ref="fileInput" type="file" id="upload" style="display: none" @change="onChangeFile"
                      @click="$refs.fileInput.value = null" />
                  </label>
                  <div class="input-more-button -right">
                    <label class="btn">
                      <span class="icon nc-icon-outline ui-1_eye-17" @click="onViewFile"></span>
                    </label>
                    <input type="text" class="form-control" :value="getFileDisplayText" readonly />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w160">{{ getTextChiTiet(currentItem.LOAITN_ID) }}</div>
              <div class="value">
                <!-- <ejs-datepicker
                    v-model="currentItem.NGAY_HT"
                    :format="'dd/MM/yyyy hh:mm:ss'"
                    :allowEdit="false"
                    style="background: white; color: #495057"
                    ref="ngayHT"
                  /> -->
                <input type="text" class="form-control" :disabled="true" v-model="currentItem.CHITIET" />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form" v-if="dsPhieuTCCon && dsPhieuTCCon.length > 0">
        <div class="legend-title">
          <div>
            Danh sách phiếu thi công con
          </div>
        </div>
        <div class="row">
          <div class="col-12">
            <DataGrid ref="grdDSPhieuTCCon" :allowPaging="true" :enablePagingServer="false" :showColumnCheckbox="false"
              :dataSource="dsPhieuTCCon" v-bind:columns="[
                { fieldName: 'LOAITN', headerText: 'Loại tài nguyên', textAlign: 'Left', width: 135 },
                { fieldName: 'LOAICV', headerText: 'Loại công việc', textAlign: 'Left', width: 135 },
                { fieldName: 'MA_DVGT', headerText: 'Mã DVGT', textAlign: 'Left', width: 100 },
                { fieldName: 'TEN_DVGT', headerText: 'Tên DVGT', textAlign: 'Left', width: 160 },
                {
                  fieldName: 'TEN_CV',
                  headerText: 'Tên công việc',
                  textAlign: 'Center',
                  width: 140,
                  template: tenCVTemplate(this)
                },
                {
                  fieldName: 'DVTH_ID',
                  headerText: 'Đơn vị thực hiện',
                  textAlign: 'Center',
                  width: 150,
                  template: donViThucHienTemplate(this)
                },
                {
                  fieldName: 'NV_TH_ID',
                  headerText: 'Người thực hiện',
                  textAlign: 'Center',
                  width: 150,
                  template: nguoiThucHienTemplate(this)
                },
                {
                  fieldName: 'GIATRI_KHAIBAO',
                  headerText: 'Giá trị khai báo',
                  textAlign: 'Center',
                  width: 140,
                  template: giaTriKhaiBaoTemplate(this)
                },
                {
                  fieldName: 'NGAY_YCHT',
                  headerText: 'Ngày yêu cầu hoàn thành',
                  textAlign: 'Center',
                  width: 200,
                  template: ngayYCHTTemplate(this)
                },
                { fieldName: 'NGAY_YC', headerText: 'Ngày yêu cầu', textAlign: 'Center', width: 150 },
                {
                  fieldName: 'NOIDUNG',
                  headerText: 'Nội dung',
                  textAlign: 'Center',
                  width: 120,
                  template: noiDungTemplate(this)
                },
                {
                  fieldName: 'CHITIET',
                  headerText: 'Chi tiết',
                  textAlign: 'Center',
                  width: 120,
                  template: chiTietTemplate(this)
                }
              ]" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import API from '../api'
import { CONFIG } from '../config'
import SearchPhieuKenhBan from './SearchPhieuKenhBan.vue'
import DateTimeLib from '../../../../utils/DateTimeLib'
import ModalUploadFile from './ModalUploadFile.vue'
import NhanVienPhoiHopSelector from './NhanVienPhoiHopSelector.vue'
import EventBus from '@/utils/eventBus'

export default {
  name: 'ChiTietCongViec',
  components: {
    SearchPhieuKenhBan,
    ModalUploadFile,
    NhanVienPhoiHopSelector
  },
  props: {
    isModal: {
      type: Boolean,
      default: true
    },
    pPhieuThiCongId: {
      type: [Number, null],
      default: null
    },
  },
  data: function () {
    return {
      dsDeleteFile: [],
      dsPhieuTCCon: [],
      pLoaiPhieu: 0,
      isLoadingDetail: false,
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      dialogPosition: { X: 'center', Y: 'center' },
      currentItem: {
        MA_CV: null,
        DONVI_ID: null,
        NHANVIEN_TH_ID: null,
        LOAICV_ID: null,
        NHANVIEN_PH_ID_LIST: null,
        TEN_CV: null,
        NGAY_YC: null,
        CANCU_YC: null,
        TRANGTHAI_ID: null,
        MACV_CHA_ID: null,
        NGAY_HT: null,
        GIATRI_KHAIBAO: 0,
        IDC_ID: null,
        MATSAN_ID: null,
        FILE_DATA: [],
        FILE_TEXT: '',

        // THÔNG TIN PHIÊU SO - KÊNH BÁN
        TINH_ID: null,
        HDTB_ID: null,
        THUEBAO_ID: null,
        LOAIPH: null,
        LOAIPH_ID: null,
        MA_TB: null,
        MA_GD: null,
        TEN_KH: null,
        SO_HD: null,
        GOICUOC_ID: null,
        GOICUOC: null,
        LOAITN_ID: null
      },
      loaiCongVienOptions: [],
      donViOptions: [],
      dsNhanVienThucHien: [],
      danhMucTrangThaiOptions: [],
      danhMucIDCOptions: [],
      danhMucMatSanOptions: [],
      dsPhieuCongViecCha: [],
      isUpdate: false,
      isEditMode: false
    }
  },
  computed: {
    getFileDisplayText() {
      if (this.currentItem.FILE_DATA && this.currentItem.FILE_DATA.length > 0) {
        return this.currentItem.FILE_DATA.map((file) => file.TEN).join(', ')
      }
      return ''
    }
  },
  created() {
    this.isEditMode = this.$route.query.isEditMode === 'true'
  },
  watch: {},
  methods: {

    // DELETE FILE
    onDeleteFile(file) {
      this.dsDeleteFile.push(file)
      const index = this.currentItem.FILE_DATA.findIndex((f) => f.FILE_ID === file.FILE_ID)
      if (index !== -1) {
        this.currentItem.FILE_DATA.splice(index, 1)
      }
    },
    // API
    async apiGetDanhMucTaiNguyenIDC(params) {
      this.loading(true)
      let dsResult = []
      try {
        const response = await API.getDanhMucTaiNguyenIDC(this.axios, params)
        if (response.data.error_code === 'BSS-00000000') {
          dsResult = response.data.data
        } else {
          dsResult = []
        }
      } catch (error) {
        console.log('apiGetDanhMucTaiNguyenIDC error: ', error)
        dsResult = []
      } finally {
        this.loading(false)
      }
      return dsResult
    },
    async apiSavePhieuThiCong(params) {
      let result = {}
      this.loading(true)
      try {
        const response = await API.createUpdatePhieuThiCong(this.axios, params)
        if (response.data.error_code === 'BSS-00000000') {
          if (this.pPhieuThiCongId == null) {
            // this.$root.toastSuccess('Tạo mới phiếu thành công!')
            this.isUpdate = false
          } else {
            this.$root.toastSuccess('Cập nhật phiếu thành công!')
            this.isUpdate = true
          }
          result = response.data.data[0]
        } else {
          this.$root.toastError('Chức năng lỗi!')
        }
      } catch (error) {
        console.log('apiSavePhieuThiCong', error)
        this.$root.toastError('Chức năng lỗi!')
      } finally {
        this.loading(false)
      }
      return result
    },
    async apiUploadFileMultiple(formData) {
      let result = []
      this.loading(true)
      try {
        const response = await API.multipleUploadFile(this.axios, formData)
        result = response.data.data
      } catch (error) {
        console.log('apiUploadFileMultiple', error)
        this.$root.toastError('Chức năng lỗi!')
      } finally {
        this.loading(false)
      }
      return result
    },
    async apiGetDetailPhieuThiCong(params) {
      this.loading(true)
      let result = {}
      try {
        const response = await API.getDetailPhieuThiCong(this.axios, params)
        if (response.data.error_code === 'BSS-00000000') {
          result = response.data.data[0]
          const dsFileDinhKem = JSON.parse(result.DS_FILE)
          console.log('dsFileDinhKem', dsFileDinhKem)
          result.FILE_DATA = dsFileDinhKem || []
        }
      } catch (error) {
        console.log('apiGetDetailPhieuThiCong error: ', error)
      } finally {
        this.loading(false)
      }
      return result
    },
    clearForm() {
      this.pLoaiPhieu = 0
      this.pPhieuThiCongId = null
      this.currentItem = {
        MA_CV: null,
        DONVI_ID: null,
        NHANVIEN_TH_ID: null,
        LOAICV_ID: null,
        NHANVIEN_PH_ID_LIST: null,
        TEN_CV: null,
        NGAY_YC: null,
        CANCU_YC: null,
        TRANGTHAI_ID: null,
        MACV_CHA_ID: null,
        NGAY_HT: null,
        FILE_DATA: [],
        FILE_TEXT: '',

        TINH_ID: null,
        HDTB_ID: null,
        THUEBAO_ID: null,
        LOAIPH: null,
        LOAIPH_ID: null,
        MA_TB: null,
        MA_GD: null,
        TEN_KH: null,
        SO_HD: null,
        GOICUOC_ID: null,
        GOICUOC: null,
        LOAITN_ID: null
      }
      this.loaiCongVienOptions = []
      this.donViOptions = []
      this.dsNhanVienThucHien = []
      this.danhMucTrangThaiOptions = []
      this.dsPhieuCongViecCha = []
      this.dsPhieuTCCon = []
    },
    onDialogClosePopup() {
      this.$refs.dlgChiTietCongViec.hide()
      this.clearForm()
    },
    async onLoadDanhMuc() {
      this.loaiCongVienOptions = await this.apiGetDanhMucTaiNguyenIDC({ loaiDanhMuc: 'IDC-LOAICV' })
      this.donViOptions = await this.apiGetDanhMucTaiNguyenIDC({ loaiDanhMuc: 'IDC_LOAI_DV_IDC' })
      this.danhMucTrangThaiOptions = await this.apiGetDanhMucTaiNguyenIDC({
        loaiDanhMuc: 'TTHAI-BY-NHOMTT',
        thamSo1: 'THICONG'
      })
    },
    async onDialogOpen() {
      await this.onLoadDanhMuc()

      if (this.pPhieuThiCongId) {
        this.isLoadingDetail = true
        this.currentItem = await this.apiGetDetailPhieuThiCong({ pPhieuTCId: this.pPhieuThiCongId })
        try {
          const raw = this.currentItem?.DS_NV_PHOIHOP
          if (raw) {
            const parsed = typeof raw === 'string' ? JSON.parse(raw) : raw
            const nhanVienPhoiHopData = Array.isArray(parsed)
              ? parsed.map((item) => ({ NHANVIEN_ID: item.NHANVIEN_ID, TEN_NV: item.NHANVIEN || item.TEN_NV, MA_NV: item.MA_NV }))
              : []
            const idList = nhanVienPhoiHopData
              .map((x) => x.NHANVIEN_ID)
              .filter((x) => x != null)
            this.$set(this.currentItem, 'NHANVIEN_PH_ID_LIST', idList)
          } else {
            this.$set(this.currentItem, 'NHANVIEN_PH_ID_LIST', [])
          }
        } catch (e) {
          console.error('Error parsing DS_NV_PHOIHOP:', e)
          this.$set(this.currentItem, 'NHANVIEN_PH_ID_LIST', [])
        }

        if (this.currentItem.DS_PHIEU_CON) {
          // Parse JSON string if needed
          let dsPhieuCon = this.currentItem.DS_PHIEU_CON
          if (typeof dsPhieuCon === 'string') {
            try {
              dsPhieuCon = JSON.parse(dsPhieuCon)
            } catch (error) {
              console.error('Error parsing DS_PHIEU_CON:', error)
              dsPhieuCon = []
            }
          }

          this.dsPhieuTCCon = dsPhieuCon.map((item, index) => {
            return {
              PHIEUTC_ID: item.PHIEUTC_ID,
              DICHVUGT_ID: item.DICHVUGT_ID,
              MA_DVGT: item.MA_DVGT,
              TEN_DVGT: item.TEN_DVGT,
              HDTB_ID: item.HDTB_ID,
              MA_TB: item.MA_TB,
              LOAITN_ID: item.LOAITN_ID,
              LOAITN: item.LOAITN,
              TT_LOAITN: item.TT_LOAITN,
              LOAICV_ID: item.LOAICV_ID,
              LOAICV: item.LOAICV,
              TT_LOAICV: item.TT_LOAICV,
              NHOMCV_ID: item.NHOMCV_ID,
              CHITIET: item.CHITIET,
              NGAY_YC: item.NGAY_YC ? item.NGAY_YC.split(' ')[0] : null,
              NOIDUNG: item.NOIDUNG,
              KIEU_YC: item.KIEU_YC,
              TTTH: item.TTTH,
              ID: index + 1,
              TEN_CV: item.TEN_CV,
              GIATRI_KHAIBAO: item.GIATRI_KHAIBAO,
              DVTH_ID: item.DVTH_ID,
              NV_TH_ID: item.NHANVIEN_TH_ID,
              NGAY_YCHT: item.NGAY_YCHT ? item.NGAY_YCHT.split(' ')[0] : null
            }
          })
        } else {
          this.dsPhieuTCCon = []
        }
        let originalMatSanId = this.currentItem.MATSAN_ID
        let originalNhanVienThId = this.currentItem.NHANVIEN_TH_ID
        if (this.currentItem.DONVI_ID) {
          await this.onChangeDonViThucHien(this.currentItem.DONVI_ID)
        }

        if (this.currentItem.IDC_ID) {
          await this.onChangeIDC(this.currentItem.IDC_ID)
        }
        this.currentItem.MATSAN_ID = originalMatSanId
        this.currentItem.NHANVIEN_TH_ID = originalNhanVienThId
      } else {
        this.currentItem.TRANGTHAI_ID = this.danhMucTrangThaiOptions.find(
          (item) => item.TENTAT == CONFIG.TENTAT_TT.THICONG_KT
        )?.ID
      }

      this.$refs.dlgChiTietCongViec.show()
      this.isLoadingDetail = false
    },
    async onGhiLai() {
      if (!this.paramsCreateValid()) return
      const params = {
        pPhieuTCId: this.pPhieuThiCongId,
        pLoaiCVId: this.currentItem.LOAICV_ID,
        pDonViId: this.currentItem.DONVI_ID,
        pMatSanId: this.currentItem.MATSAN_ID,
        pTenCV: this.currentItem.TEN_CV,
        pCanCuYC: this.currentItem.CANCU_YC,
        pNoiDung: '', //?? lấy chỗ nào ra
        pGiaTriKhaiBao: this.currentItem.GIATRI_KHAIBAO,
        pNhanVienTHId: this.currentItem.NHANVIEN_TH_ID,
        pNgayYCHT: DateTimeLib.toDateDisplay(this.currentItem.NGAY_YC),
        pMoTa: '', //??
        pLoaiTNId: this.currentItem.LOAITN_ID,
        pChiTiet: '', //?
        pDSFile: this.currentItem.FILE_DATA.map((item) => ({
          ten: item.TEN,
          path: item.FILE_PATH
        })),
        pDsNvPhoiHop: this.currentItem.NHANVIEN_PH_ID_LIST?.join(',') ?? null
      }
      if (this.pLoaiPhieu == 1) {
        // kênh bán hàng
        params.pCongViecId = 1 //Tạm
        params.pLoaiPhId = this.currentItem.LOAIPH_ID
        params.pTinhId = this.currentItem.TINH_ID
        params.pHdtbId = this.currentItem.HDTB_ID
        params.pThueBaoId = this.currentItem.THUEBAO_ID
        params.pMaTb = this.currentItem.MA_TB
        params.pMaGd = this.currentItem.MA_GD
        params.pTenKh = this.currentItem.TEN_KH
        params.pSoHd = this.currentItem.SO_HD
        params.pGoiCuocId = this.currentItem.GOICUOC_ID
        params.pDsPhieuTCCon = JSON.stringify(
          this.dsPhieuTCCon.map((item) => ({
            phieuTCId: item.PHIEUTC_ID,
            loaiCvId: item.LOAICV_ID,
            donViId: item.DVTH_ID,
            matSanId: this.currentItem.MATSAN_ID,
            maCv: item.MA_CV,
            tenCv: item.TEN_CV,
            canCuYc: item.CANCU_YC,
            noiDung: item.NOIDUNG,
            giaTriKhaiBao: item.GIATRI_KHAIBAO,
            nhanVienTHId: item.NV_TH_ID,
            ngayYCHT: item.NGAY_YCHT,
            dichVuGTId: item.DICHVUGT_ID,
            //------------
            chiTiet: item.CHITIET,
            maDVGT: item.MA_DVGT,
            tenDVGT: item.TEN_DVGT
          }))
        )
      }
      const response = await this.apiSavePhieuThiCong(params)
      if (!this.isModal) {
        return;
      }
      if (response.PHIEUTC_ID) {
        this.pPhieuThiCongId = response.PHIEUTC_ID
      }
      if (this.dsDeleteFile.length > 0) {
        this.dsDeleteFile.forEach(async (file) => {
          try {
            let rs = await API.deleteFile(this.axios, { fileSource: file.FILE_PATH })
          } catch (error) {
            console.log('Error deleting file:', error)
          }
        })
        this.dsDeleteFile = []
      }
      const savedData = {
        phieuYCId: this.pPhieuThiCongId || response.PHIEUTC_ID,
        loaiCongViecId: this.currentItem.LOAICV_ID,
        isNewRecord: !this.isUpdate,
        currentItem: this.currentItem
      }
      if(this.currentItem.LOAICV_ID == 31) {
        this.$root.toastSuccess('Tạo phiếu thành công!')
      } 
      this.onDialogClosePopup()
      this.$emit('afterSave', savedData)
    },
    paramsCreateValid() {
      let flag = true
      if (this.pLoaiPhieu == 1 && !this.currentItem.HDTB_ID) {
        this.$root.toastError('Thông tin phiếu SO là bắt buộc!')
        flag = false
      }
      if (!this.currentItem.LOAICV_ID) {
        this.$root.toastError('Loại công việc là bắt buộc!')
        flag = false
      }
      if (!this.currentItem.DONVI_ID) {
        this.$root.toastError('Đơn vị thực hiện là bắt buộc!')
        flag = false
      }
      if (!this.currentItem.NHANVIEN_TH_ID) {
        this.$root.toastError('Nhân viên thực hiện là bắt buộc!')
        flag = false
      }
      if (!this.currentItem.TEN_CV || this.currentItem.TEN_CV.trim() == '') {
        this.$root.toastError('Hạng mục triển khai là bắt buộc!')
        flag = false
      }
      if (!this.currentItem.IDC_ID || !this.currentItem.MATSAN_ID) {
        this.$root.toastError('IDC và mặt sàn là bắt buộc!')
        flag = false
      }
      if (!this.currentItem.GIATRI_KHAIBAO || this.currentItem.GIATRI_KHAIBAO <= 0) {
        this.$root.toastError('Giá trị khai báo là bắt buộc và lớn hơn 0!')
        flag = false
      }
      return flag
    },
    async onChangeDonViThucHien(value) {
      if (!value) return
      this.currentItem.NHANVIEN_TH_ID = null
      // Chỉ reset NHANVIEN_PH_ID_LIST khi không phải đang load detail phiếu thi công
      if (!this.isLoadingDetail) {
        this.currentItem.NHANVIEN_PH_ID_LIST = null
      }
      this.danhMucIDCOptions = null
      this.danhMucMatSanOptions = null

      this.dsNhanVienThucHien = await this.apiGetDanhMucTaiNguyenIDC({
        loaiDanhMuc: 'NHANVIEN-DONVI-IDC',
        thamSo1: value
      })

      this.danhMucIDCOptions = await this.apiGetDanhMucTaiNguyenIDC({
        loaiDanhMuc: 'DANHMUC_IDC',
        thamSo1: value
      })

      if (this.dsPhieuTCCon && this.dsPhieuTCCon.length > 0) {
        this.dsPhieuTCCon.forEach((item, index) => {
          this.$set(this.dsPhieuTCCon[index], 'DVTH_ID', value)
          this.$set(this.dsPhieuTCCon[index], 'NV_TH_ID', '')
        })

        this.$nextTick(() => {
          if (this.$refs.grdDSPhieuTCCon) {
            this.$refs.grdDSPhieuTCCon.refresh()
          }
        })
      }
    },
    async onChangeIDC(value) {
      if (!value) return
      this.currentItem.MATSAN_ID = null
      EventBus.$emit('update-select-idc', value)
      this.danhMucMatSanOptions = await this.apiGetDanhMucTaiNguyenIDC({
        loaiDanhMuc: 'MATSAN-BY-IDC',
        thamSo1: this.currentItem.IDC_ID
      })
    },
    async onTraCuuThueBao() {
      console.log('onTraCuuThueBao')
      this.$refs.refSearchPhieuKenhBan.show()
    },
    async onBeforClosePopupSearchPhieuSO(data) {
      console.log('onBeforClosePopupSearchPhieuSO', data)
      this.currentItem.TINH_ID = data.PHANVUNG_ID
      this.currentItem.HDTB_ID = data.HDTB_ID
      this.currentItem.MA_GD = data.MA_GD
      this.currentItem.SO_HD = data.SO_HD
      this.currentItem.MA_TB = data.MA_TB
      this.currentItem.GOICUOC_ID = data.GOICUOC_ID
      this.currentItem.GOICUOC = data.GOICUOC
      this.currentItem.LOAITN_ID = data.LOAITN_ID
      this.currentItem.LOAITN = data.LOAITN

      this.currentItem.THUEBAO_ID = data.THUEBAO_ID
      this.currentItem.TEN_KH = data.TEN_KH
      this.currentItem.LOAIPH = data.LOAIPH
      this.currentItem.LOAIPH_ID = data.LOAIPH_ID

      if (this.donViOptions && this.donViOptions.some((item) => item.ID === data.DONVI_NHAN_ID)) {
        this.currentItem.DONVI_ID = data.DONVI_NHAN_ID
        await this.onChangeDonViThucHien(this.currentItem.DONVI_ID)

        if (this.danhMucIDCOptions && this.danhMucIDCOptions.some((item) => item.ID === data.IDC_ID)) {
          this.currentItem.IDC_ID = data.IDC_ID
          await this.onChangeIDC(this.currentItem.IDC_ID)
        }
        this.currentItem.NHANVIEN_TH_ID = data.NHANVIEN_NHAN_ID
      }

      this.currentItem.GIATRI_KHAIBAO = data.KHONGGIAN
      this.currentItem.NGAY_YC = data.NGAY_YC

      let rs = await API.getDSPhieuSOCon(this.axios, { pPhanvungId: data.TINH_QLHD_ID, pHdtbId: data.HDTB_ID })
      if (rs && rs.data.error_code === 'BSS-00000000') {
        this.dsPhieuTCCon = rs.data.data
        this.dsPhieuTCCon.forEach((item, index) => {
          item.ID = index + 1
          if (this.currentItem.DONVI_ID) {
            item.DVTH_ID = this.currentItem.DONVI_ID
          }
        })

        this.$nextTick(() => {
          if (this.$refs.grdDSPhieuTCCon) {
            this.$refs.grdDSPhieuTCCon.refresh()
          }
        })
      }
    },
    async onChangeFile(e) {
      let fileList = Array.from(e.target.files)
      const formData = new FormData()
      fileList.forEach((file, i) => {
        // valid
        const extension = file.name.slice(-3)
        if (extension === 'exe' || extension === 'msi' || extension === 'bat' || extension === '.js') {
          return this.$toast.error('Vui lòng không upload file có định dạng .exe/.msi/.bat!')
        }
        formData.append('files', file)
      })
      const response = await this.apiUploadFileMultiple(formData)
      if (response && response.length > 0) {
        this.$root.toastSuccess('Upload file thành công!')
        response.forEach((item) => {
          this.currentItem.FILE_TEXT = this.currentItem.FILE_TEXT + item.message.fileName + ', '
          this.currentItem.FILE_DATA.push({
            URL: item.message.presignedUrl,
            FILE_PATH: item.message.relativeUrl,
            TEN: item.message.fileName
          })
        })
      }
    },
    onViewFile() {
      this.$refs.refModalUploadFile.danhSachFile = this.currentItem.FILE_DATA
      this.$refs.refModalUploadFile.showModal()
    },
    tenCVTemplate(parent) {
      return function () {
        return {
          //
          template: {
            template: `<div>
                            <input type="text" v-model="localValue" class="form-control" @input="onValueChange" />
                          </div>`,
            data() {
              return {
                parent: parent,
                data: this.data || {},
                localValue: this.data?.TEN_CV || ''
              }
            },
            watch: {
              'data.TEN_CV'(newVal) {
                this.localValue = newVal || ''
              }
            },
            mounted() {
              this.localValue = this.data?.TEN_CV || ''
            },
            computed: {},
            methods: {
              onValueChange() {
                if (this.data) {
                  this.data.TEN_CV = this.localValue
                  const index = this.parent.dsPhieuTCCon.findIndex((item) => item.ID === this.data.ID)
                  if (index !== -1) {
                    this.parent.$set(this.parent.dsPhieuTCCon[index], 'TEN_CV', this.localValue)
                  }
                }
              }
            }
          }
        }
      }
    },
    donViThucHienTemplate(parent) {
      return function () {
        return {
          //
          template: {
            template: `<div>
                            <input type="text" class="form-control" :value="getTenDonVi()" readonly
                                   style="background-color: #f8f9fa; cursor: not-allowed;"
                                   title="Đơn vị được kế thừa từ phiếu cha" />
                          </div>`,
            data() {
              return {
                parent: parent,
                data: this.data || {}
              }
            },
            watch: {
              'data.DVTH_ID': {
                handler(newVal, oldVal) {
                  this.$forceUpdate()
                },
                immediate: true
              },
              'parent.donViOptions': {
                handler(newVal) {
                  this.$forceUpdate()
                },
                deep: true
              }
            },
            mounted() {
              this.$forceUpdate()
            },
            computed: {},
            methods: {
              getTenDonVi() {
                const dvthId = this.data?.DVTH_ID
                const donViOptions = this.parent?.donViOptions

                if (!dvthId || !donViOptions || !Array.isArray(donViOptions)) {
                  return '-- Chọn đơn vị --'
                }

                const donVi = donViOptions.find((dv) => dv.ID == dvthId)
                return donVi ? donVi.TEN : '-- Chọn đơn vị --'
              }
            }
          }
        }
      }
    },
    nguoiThucHienTemplate(parent) {
      return function () {
        return {
          //
          template: {
            template: `<div>
                            <select class="form-control" v-model="localValue" @change="onValueChange" :disabled="!data.DVTH_ID">
                              <option value="">-- Chọn nhân viên --</option>
                              <option v-for="nv in dsNhanVienThucHien" :key="nv.NHANVIEN_ID" :value="nv.NHANVIEN_ID">{{ nv.TEN_NV }}</option>
                            </select>
                          </div>`,
            data() {
              return {
                parent: parent,
                data: this.data || {},
                localValue: this.data?.NV_TH_ID || '',
                dsNhanVienThucHien: []
              }
            },
            watch: {
              'data.NV_TH_ID'(newVal) {
                this.localValue = newVal || ''
              },
              'data.DVTH_ID': {
                handler(newVal, oldVal) {
                  if (newVal && newVal !== oldVal) {
                    this.fetchNhanVienData(newVal)
                  } else if (!newVal) {
                    this.dsNhanVienThucHien = []
                    this.localValue = ''
                  }
                },
                immediate: true
              }
            },
            mounted() {
              this.localValue = this.data?.NV_TH_ID || ''
              if (this.data?.DVTH_ID) {
                this.fetchNhanVienData(this.data.DVTH_ID)
              }
            },
            computed: {},
            methods: {
              async fetchNhanVienData(donViId) {
                if (!donViId) return
                try {
                  this.dsNhanVienThucHien = await this.parent.apiGetDanhMucTaiNguyenIDC({
                    loaiDanhMuc: 'NHANVIEN-DONVI-IDC',
                    thamSo1: donViId
                  })
                } catch (error) {
                  console.log('Error fetching nhan vien data:', error)
                  this.dsNhanVienThucHien = []
                }
              },
              onValueChange() {
                if (this.data) {
                  this.data.NV_TH_ID = this.localValue
                  const index = this.parent.dsPhieuTCCon.findIndex((item) => item.ID === this.data.ID)
                  if (index !== -1) {
                    this.parent.$set(this.parent.dsPhieuTCCon[index], 'NV_TH_ID', this.localValue)
                  }
                }
              }
            }
          }
        }
      }
    },
    giaTriKhaiBaoTemplate(parent) {
      return function () {
        return {
          //
          template: {
            template: `<div>
                            <input type="number" v-model="localValue" class="form-control" @input="onValueChange" />
                          </div>`,
            data() {
              return {
                parent: parent,
                data: this.data || {},
                localValue: this.data?.GIATRI_KHAIBAO || 0
              }
            },
            watch: {
              'data.GIATRI_KHAIBAO'(newVal) {
                this.localValue = newVal || 0
              }
            },
            mounted() {
              this.localValue = this.data?.GIATRI_KHAIBAO || 0
            },
            computed: {},
            methods: {
              onValueChange() {
                if (this.data) {
                  this.data.GIATRI_KHAIBAO = this.localValue
                  const index = this.parent.dsPhieuTCCon.findIndex((item) => item.ID === this.data.ID)
                  if (index !== -1) {
                    this.parent.$set(this.parent.dsPhieuTCCon[index], 'GIATRI_KHAIBAO', this.localValue)
                  }
                }
              }
            }
          }
        }
      }
    },
    ngayYCHTTemplate(parent) {
      return function () {
        return {
          //
          template: {
            template: `<div>
                            <ejs-datepicker v-model="localValue" :format="'dd/MM/yyyy'" :allowEdit="false"
                              style="background: white; color: #495057" @change="onValueChange" />
                          </div>`,
            data() {
              return {
                parent: parent,
                data: this.data || {},
                localValue: this.data?.NGAY_YCHT || null
              }
            },
            watch: {
              'data.NGAY_YCHT'(newVal) {
                this.localValue = newVal
              }
            },
            mounted() {
              this.localValue = this.data?.NGAY_YCHT || null
            },
            computed: {},
            methods: {
              onValueChange() {
                if (this.data) {
                  this.data.NGAY_YCHT = this.localValue
                  const index = this.parent.dsPhieuTCCon.findIndex((item) => item.ID === this.data.ID)
                  if (index !== -1) {
                    this.parent.$set(this.parent.dsPhieuTCCon[index], 'NGAY_YCHT', this.localValue)
                  }
                }
              }
            }
          }
        }
      }
    },
    noiDungTemplate(parent) {
      return function () {
        return {
          //
          template: {
            template: `<div>
                            <input type="text" v-model="localValue" class="form-control" @input="onValueChange" />
                          </div>`,
            data() {
              return {
                parent: parent,
                data: this.data || {},
                localValue: this.data?.NOIDUNG || ''
              }
            },
            watch: {
              'data.NOIDUNG'(newVal) {
                this.localValue = newVal || ''
              }
            },
            mounted() {
              this.localValue = this.data?.NOIDUNG || ''
            },
            computed: {},
            methods: {
              onValueChange() {
                if (this.data) {
                  this.data.NOIDUNG = this.localValue
                  const index = this.parent.dsPhieuTCCon.findIndex((item) => item.ID === this.data.ID)
                  if (index !== -1) {
                    this.parent.$set(this.parent.dsPhieuTCCon[index], 'NOIDUNG', this.localValue)
                  }
                }
              }
            }
          }
        }
      }
    },
    chiTietTemplate(parent) {
      return function () {
        return {
          template: {
            template: `<div>
                            <input type="text" v-model="localValue" class="form-control" @input="onValueChange" />
                          </div>`,
            data() {
              return {
                parent: parent,
                data: this.data || {},
                localValue: this.data?.CHITIET || ''
              }
            },
            watch: {
              'data.CHITIET'(newVal) {
                this.localValue = newVal || ''
              }
            },
            mounted() {
              this.localValue = this.data?.CHITIET || ''
            },
            computed: {},
            methods: {
              onValueChange() {
                if (this.data) {
                  this.data.CHITIET = this.localValue
                  const index = this.parent.dsPhieuTCCon.findIndex((item) => item.ID === this.data.ID)
                  if (index !== -1) {
                    this.parent.$set(this.parent.dsPhieuTCCon[index], 'CHITIET', this.localValue)
                  }
                }
              }
            }
          }
        }
      }
    },
    getTextChiTiet(loaitn_id) {
      switch (loaitn_id) {
        case 1:
        case 2:
          return 'Không gian'
        case 4:
        case 8:
          return 'Địa chỉ IP'
        case 5:
          return 'Công suất'
        default:
          return 'Chi tiết'
      }
    }
  },
  mounted() {
    if (!this.isModal) {
      this.onDialogOpen()
    }
  }
}
</script>
<style scoped>
.e-dialog {
  position: fixed !important;
  top: 40% !important;
  left: 50% !important;
  transform: translate(-50%, -50%) !important;
}

.custom-dialog .e-dlg-content {
  overflow: visible !important;
  overflow-x: visible !important;
}
</style>
