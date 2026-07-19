<template>
  <div class="">
    <!-- action header -->
    <div class="list-action-top">
      <vue-bread-crumb :header="header" />
      <div class="list-actions-top">
        <ul class="list">
          <li v-if="visibleBtnNhapMoi">
            <a
              href="#"
              :class="{ disabled: disableBtnNhapMoi }"
              @click.prevent="nhapMoi"
            >
              <span class="icon one-file-plus"></span>Nhập mới
            </a>
          </li>

          <li v-if="visibleBtnGhiLai">
            <a
              href="#"
              @click.prevent="handleGhiLai"
              :class="{ disabled: disabledBtnGhiLai }"
            >
              <span class="icon one-save"></span> Ghi lại
            </a>
          </li>

          <li v-if="visibleBtnHuy">
            <a
              href="#"
              :class="{ disabled: disableBtnHuy }"
              @click.prevent="huy"
            >
              <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Huỷ
            </a>
          </li>

          <li v-if="visibleBtnGiaoPhieu">
            <a href="#" :class="{ disabled: disabledBtnGiaoPhieu }">
              <span class="icon one-file-arrow-right1"></span> Giao phiếu
            </a>
          </li>
          <li v-if="visibleBtnHoanThien">
            <a
              href="#"
              @click.prevent="handleHoanThien"
              :class="{ disabled: disableBtnHoanThien }"
            >
              <span class="icon one-file-check"></span>Hoàn thiện
            </a>
          </li>
        </ul>
      </div>
    </div>
    <!-- content page -->
    <div class="page-content">
      <div class="information-customer">
        <div class="box-form">
          <!-- thong tin Khach Hang -->
          <div class="legend-title">
            Thông tin khách hàng
          </div>

          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="row">
                <div class="col-sm-7 col-12">
                  <div class="info-row">
                    <div class="key w110">
                      Mã giao dịch
                    </div>
                    <div class="value">
                      <div class="input-more-button">
                        <button class="btn" @click="showPopupSearchContract">
                          <span class="-ap icon-more_horiz"></span>
                        </button>
                        <input
                          ref="txtMaGD"
                          v-model="maGiaoDich"
                          @keydown="btnLayMaGD_Click($event)"
                          type="text"
                          class="form-control can-enter-input"
                        />
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Ngày yêu cầu
                    </div>
                    <div class="value">
                      <date-picker
                        :disabled="true"
                        v-model="ngayYeuCau"
                        style="width: 100%"
                        placeholder="dd/mm/yyyy hh:mm:ss"
                        :formatter="momentFormat"
                        valueType="format"
                      ></date-picker>
                    </div>
                  </div>
                </div>
              </div>

              <div class="row">
                <div class="col-sm-7 col-12">
                  <div class="info-row value">
                    <div class="">
                      <vue-input
                        v-model="maHopDong"
                        :labelWidth="'110'"
                        label="Mã hợp đồng"
                      ></vue-input>
                    </div>
                  </div>
                </div>
                <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Ngày lập HĐ
                    </div>
                    <div class="value">
                      <date-picker
                        :disabled="true"
                        v-model="ngayLapHD"
                        style="width: 100%"
                        placeholder="dd/mm/yyyy"
                        :formatter="momentFormat"
                        valueType="format"
                      ></date-picker>
                    </div>
                  </div>
                </div>
              </div>

              <div class="info-row">
                <div class="key w110">
                  Ngày TH
                </div>
                <div class="value">
                  <date-picker
                    v-model="ngayTH"
                    style="width: 100%"
                    placeholder="dd/mm/yyyy"
                    :formatter="momentFormat"
                    valueType="format"
                  ></date-picker>
                </div>
              </div>
            </div>

            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="value">
                  <vue-input
                    v-model="maKH"
                    :labelWidth="'140'"
                    label="Mã khách hàng"
                  ></vue-input>
                </div>
              </div>
              <div class="info-row">
                <div class="value">
                  <vue-input
                    v-model="tenKH"
                    :labelWidth="'140'"
                    label="Tên khách hàng"
                  ></vue-input>
                </div>
              </div>
              <div class="info-row">
                <div class="value">
                  <vue-input
                    v-model="diaChiKH"
                    :labelWidth="'140'"
                    label="Địa chỉ khách hàng"
                  ></vue-input>
                </div>
              </div>
            </div>
          </div>

          <!-- thong tin Thue Bao -->
          <div class="legend-title">Thông tin thuê bao</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row value">
                <div class="select-custom">
                  <vue-select
                    v-model="selectedDichVu"
                    :labelWidth="'110'"
                    :options="dichVuListFilter"
                    labelField="TEN_DVVT"
                    valueField="DICHVUVT_ID"
                    label="Dịch vụ"
                    @input="cboDichVuVT_SelectedValueChanged"
                  ></vue-select>
                </div>
              </div>
              <div class="info-row value">
                <div class="select-custom">
                  <vue-select
                    v-model="selectedLoaiHinh"
                    :labelWidth="'110'"
                    :options="loaiHinhFilterByDichVu"
                    labelField="LOAIHINH_TB"
                    valueField="LOAITB_ID"
                    label="Loại hình"
                    @input="changeLoaiHinh"
                  ></vue-select>
                </div>
              </div>
              <div class="info-row value">
                <div class="select-custom">
                  <vue-select
                    v-model="selectedKieuYeuCau"
                    :labelWidth="'110'"
                    :options="kieuYeuCauList"
                    labelField="ten_kieuld"
                    valueField="kieuld_id"
                    label="Kiểu yêu cầu"
                  ></vue-select>
                </div>
              </div>
              <div class="info-row">
                <div class="key w110">
                  Số máy/Acc
                </div>
                <div class="value">
                  <input
                    ref="txtMaGD"
                    v-model="soMayAcc"
                    v-on:keyup.enter="txtMaTB_KeyPress"
                    type="text"
                    class="form-control can-enter-input"
                  />
                </div>
              </div>
              <div class="info-row value">
                <div class="">
                  <vue-input
                    v-model="tenThueBao"
                    :labelWidth="'110'"
                    label="Tên thuê bao"
                  ></vue-input>
                </div>
              </div>
              <div class="info-row value">
                <div class="select-custom">
                  <vue-select
                    :disable="isDisplayBangThongCoDinh"
                    v-model="selectedDVTinhCuoc"
                    :labelWidth="'110'"
                    :options="DVTinhCuocList"
                    labelField="ten_dv"
                    valueField="donvi_id"
                    label="ĐV tính cước"
                  ></vue-select>
                </div>
              </div>
              <div class="info-row value">
                <div class="">
                  <vue-input
                    v-model="diaChiLD"
                    :labelWidth="'110'"
                    label="Địa chỉ LĐ"
                  ></vue-input>
                </div>
              </div>
              <div class="info-row value">
                <div class="">
                  <vue-textarea
                    v-model="ghiChu"
                    height="120"
                    :labelWidth="'110'"
                    label="Ghi chú"
                  ></vue-textarea>
                </div>
              </div>
            </div>
            <!--Băng thông cố định-->
            <div v-if="isDisplayBangThongCoDinh" class="col-sm-6 col-12">
              <div class="row">
                <div class="col-sm-8 col-12">
                  <div class="row">
                    <div class="group-item">
                      <div class="col-sm-7 col-12">
                        <div class="value">
                          <vue-input
                            v-model="matKhau"
                            :labelWidth="'140'"
                            label="Mật khẩu"
                          ></vue-input>
                        </div>
                      </div>
                      <div class="col-sm-5 col-12">
                        <div class="info-row value">
                          <div class="key w60">
                            Mã TN
                          </div>
                          <div class="value">
                            <div class="input-more-button check-and-input">
                              <b-form-input
                                v-model="maTN"
                                :labelWidth="'60'"
                                label="Mã TN"
                                class="can-enter-input"
                              ></b-form-input>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-4 col-12">
                  <div class="info-row value">
                    <vue-input
                      v-model="email"
                      :labelWidth="'90'"
                      :disable="disableEmail"
                      label="Email"
                    ></vue-input>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-8 col-12">
                  <div class="row">
                    <div class="info-row ">
                      <div class="value">
                        <div class="select-custom">
                          <vue-select
                            :disable="isDisplayBangThongCoDinh"
                            v-model="selectedTocDo"
                            :labelWidth="'140'"
                            :options="tocDoFilter"
                            labelField="thuonghieu"
                            valueField="tocdo_id"
                            label="Tốc độ"
                          ></vue-select>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-4 col-12">
                  <div class="info-row value">
                    <div class="select-custom">
                      <vue-select
                        :disable="isDisplayBangThongCoDinh"
                        v-model="selectedMucCuoc"
                        :labelWidth="'90'"
                        :options="mucCuocList"
                        labelField="TENMUC"
                        valueField="MUCCUOC_ID"
                        label="Mức cước"
                      ></vue-select>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-8 col-12">
                  <div class="row">
                    <div class="info-row ">
                      <div class="value">
                        <div class="select-custom">
                          <vue-select
                            :disable="disableLoaiThietBi"
                            v-model="selectedLoaiThietBi"
                            :labelWidth="'140'"
                            :options="loaiThietBiList"
                            labelField="ten_tbi"
                            valueField="thietbidc_id"
                            label="Loại Tbị"
                          ></vue-select>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-4 col-12">
                  <div class="info-row value">
                    <vue-input
                      v-model="hoiCapADSL"
                      :labelWidth="'90'"
                      label="Hộp cáp"
                    ></vue-input>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-8 col-12">
                  <div class="row">
                    <div class="info-row ">
                      <div class="value">
                        <vue-input
                          v-model="doiCapADSL"
                          :labelWidth="'140'"
                          label="Đôi cáp"
                        ></vue-input>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-4 col-12">
                  <div class="info-row value">
                    <vue-input
                      v-model="metDay"
                      :labelWidth="'90'"
                      label="Mét dây"
                    ></vue-input>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="info-row">
                  <div class=" value">
                    <div class="select-custom">
                      <vue-select
                        :disable="disableBras"
                        v-model="selectedBras"
                        :labelWidth="'140'"
                        :options="brasList"
                        labelField="tenbras"
                        valueField="bras_id"
                        label="Bras"
                        @input="changeBras"
                      ></vue-select>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-8 col-12">
                  <div class="row">
                    <div class="info-row ">
                      <div class="value">
                        <div class="select-custom">
                          <vue-select
                            :disable="disbaleDslam"
                            v-model="selectedDslam"
                            :labelWidth="'140'"
                            :options="dslamList"
                            labelField="NAME"
                            valueField="ID"
                            label="Dslam"
                          ></vue-select>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-4 col-12">
                  <div class="info-row value">
                    <div class="key w80">
                      <div class="check-action">
                        <input type="checkbox" class="check" :disabled="true" />
                        <span class="name mr-1">IP tĩnh</span>
                      </div>
                    </div>
                    <div class="value">
                      <div class="input-more-button check-and-input">
                        <b-form-input
                          v-model="ipTinh"
                          class="can-enter-input"
                        ></b-form-input>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-8 col-12">
                  <div class="row">
                    <div class="group-item">
                      <div class="col-sm-7 col-12">
                        <div class="value">
                          <vue-input
                            v-model="system"
                            :labelWidth="'140'"
                            label="System"
                          ></vue-input>
                        </div>
                      </div>
                      <div class="col-rack col-sm-5 col-12">
                        <div class="value">
                          <vue-input
                            v-model="rack"
                            :labelWidth="'60'"
                            label="Rack"
                          ></vue-input>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-4 col-12">
                  <div class="value">
                    <vue-input
                      v-model="shelf"
                      :labelWidth="'90'"
                      label="Shelf"
                    ></vue-input>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-8 col-12">
                  <div class="row">
                    <div class="info-row ">
                      <div class="value">
                        <vue-input
                          v-model="slot"
                          :labelWidth="'140'"
                          label="Slot"
                        ></vue-input>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-4 col-12">
                  <div class="info-row value">
                    <vue-input
                      v-model="port"
                      :labelWidth="'90'"
                      label="Port"
                    ></vue-input>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-8 col-12">
                  <div class="row">
                    <div class="info-row ">
                      <div class="value">
                        <vue-input
                          v-model="VPI_SVLAN"
                          :labelWidth="'140'"
                          label="VPI/SVLAN"
                        ></vue-input>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-4 col-12">
                  <div class="info-row value">
                    <vue-input
                      v-model="VCI_CVLAN"
                      :labelWidth="'90'"
                      label="VCI/CVLAN"
                    ></vue-input>
                  </div>
                </div>
              </div>
            </div>

            <!-- Megawan -->
            <div v-if="isDisplayMegaWan" class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100">Hướng kết nối</div>
                <div class="value">
                  <input
                    v-model="huongKetNoi"
                    type="text"
                    class="form-control"
                  />
                </div>
              </div>
              <div class="row">
                <div class="col-sm-8 col-12">
                  <div class="info-row">
                    <div class="value">
                      <div class="select-custom">
                        <vue-select
                          v-model="selectedLoaiKenh"
                          :labelWidth="'95'"
                          :options="loaiKenhList"
                          labelField="LOAI_KENH"
                          valueField="LOAIKENH_ID"
                          label="Loại kênh"
                        ></vue-select>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-4 col-12">
                  <div class="info-row">
                    <div class="value">
                      <div class="select-custom">
                        <vue-select
                          v-model="selectedThoiHan"
                          :labelWidth="'65'"
                          :options="thoiHanList"
                          labelField="TENTHOIHAN"
                          valueField="THOIHAN_ID"
                          label="Thời hạn"
                        ></vue-select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-8 col-12">
                  <div class="info-row">
                    <div class="value">
                      <div class="select-custom">
                        <vue-select
                          v-model="selectedModem"
                          :labelWidth="'95'"
                          :options="modemList"
                          labelField="LOAI_MD"
                          valueField="LOAIMD_ID"
                          label="Modem"
                        ></vue-select>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-4 col-12">
                  <div class="info-row">
                    <div class="value">
                      <div class="select-custom">
                        <vue-select
                          v-model="selectedTocDoKenh"
                          :labelWidth="'65'"
                          :options="tocDoKenhListFilter"
                          labelField="TOCDO_DONVI"
                          valueField="TOCDO_ID"
                          label="Tốc độ"
                        ></vue-select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-8 col-12">
                  <div class="info-row">
                    <div class="value">
                      <div class="select-custom">
                        <vue-select
                          v-model="selectedLoaiThietBi"
                          :labelWidth="'95'"
                          :options="loaiThietBiList"
                          labelField="ten_tbi"
                          valueField="thietbidc_id"
                          label="Loại thiết bị"
                        ></vue-select>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-4 col-12">
                  <div class="info-row">
                    <div class="value">
                      <div class="select-custom">
                        <vue-select
                          v-model="selectedTrangBi"
                          :labelWidth="'65'"
                          :options="trangBiList"
                          labelField="TENTRANGBI"
                          valueField="TRANGBI_ID"
                          label="Trang bị"
                        ></vue-select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-8 col-12">
                  <div class="info-row">
                    <div class="value">
                      <div class="select-custom">
                        <vue-select
                          v-model="selectedChuQuan"
                          :labelWidth="'95'"
                          :options="chuQuanList"
                          labelField="TENCHUQUAN"
                          valueField="CHUQUAN_ID"
                          label="Chủ quản"
                        ></vue-select>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-4 col-12">
                  <div class="info-row">
                    <div class="key w70">Dslam</div>
                    <div class="value">
                      <input v-model="dslam" type="text" class="form-control" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-8 col-12">
                  <div class="info-row">
                    <div class="value">
                      <div class="select-custom">
                        <vue-select
                          v-model="selectedRouter"
                          :labelWidth="'95'"
                          :options="routerList"
                          labelField="pe"
                          valueField="pe_id"
                          label="Router"
                        ></vue-select>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-2 col-12">
                  <div class="info-row">
                    <div class="key w70">Slot PE</div>
                    <div class="value">
                      <input
                        v-model="slotPE"
                        type="text"
                        class="form-control"
                      />
                    </div>
                  </div>
                </div>
                <div class="col-sm-2 col-12">
                  <div class="info-row">
                    <div class="key w70">Port PE</div>
                    <div class="value">
                      <input
                        v-model="portPE"
                        type="text"
                        class="form-control"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-8 col-12">
                  <div class="info-row">
                    <div class="key w100">Tên thiết bị</div>
                    <div class="value">
                      <input
                        v-model="tenThietBi"
                        type="text"
                        class="form-control"
                      />
                    </div>
                  </div>
                </div>
                <div class="col-sm-4 col-12">
                  <div class="info-row">
                    <div class="key w70">Port</div>
                    <div class="value">
                      <input v-model="port" type="text" class="form-control" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-8 col-12">
                  <div class="info-row">
                    <div class="key w100">SvLan</div>
                    <div class="value">
                      <input v-model="svLan" type="text" class="form-control" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-4 col-12">
                  <div class="info-row">
                    <div class="key w70">CvLan</div>
                    <div class="value">
                      <input v-model="cvLan" type="text" class="form-control" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-8 col-12">
                  <div class="info-row">
                    <div class="key w100">Lan_ip</div>
                    <div class="value">
                      <input v-model="lanIP" type="text" class="form-control" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-4 col-12">
                  <div class="info-row">
                    <div class="key w70">Wan_ip</div>
                    <div class="value">
                      <input v-model="wanIP" type="text" class="form-control" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- danh sach thue bao -->
      <div class="list-thue-bao">
        <danh-sach-thue-bao-thay-doi-loai-hinh @currentRecord="currentRecord" />
      </div>
    </div>

    <!-- dialog mã giao dịch -->
    <search-contract-modal
      ref="popupSearchContract"
      @accept="onAcceptSearchContract"
      :trangthai_hd="5"
      :loai_hd_id="loaiHd"
      :dichvu_id="selectedDichVu"
    />

    <!-- <search-contract-modal
      ref="popupSearchContract"
      @accept="onAcceptSearchContract"
      :trangthai_hd="5"
      :loai_hd_id="loaiHd"
      :dichvu_id="4"
    /> -->
  </div>
</template>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import moment from "moment";
import DatePicker from "vue2-datepicker";
import XLSX from "xlsx";
import {
  DATA_CURRENT,
  TrangThaiHD,
  DICHVU_VIENTHONG,
  LOAIHINH_THUEBAO,
  LoaiHopDong,
  LOAI_DONVI
} from "./constants";
// import SearchContractModal from "./popup/SearchContract/SearchContractModal.vue";
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue";


/**
 * 1. Loại hợp đồng: 6
 *  chuyển đổi loại hình thuê bao
 *
 * 2. Trạng thái hd: Đã thi công xong: 3
 *
 * 3. Dịch vụ: Băng rộng cố định: 4
 *
 */

export default {
  name: "ChuyenDoiLoaiHinhThueBao",
  props: {
    tag: {
      type: Number,
      default: 5
    }
  },
  components: {
    DatePicker,
    SearchContractModal,
    DanhSachThueBaoThayDoiLoaiHinh: () =>
      import("./part/DanhSachThueBaoThayDoiLoaiHinh.vue")
  },

  data() {
    return {
      // HPG-LH/00543539
      /* data header action */
      header: {
        title: "HOÀN THIỆN HỒ SƠ CHUYỂN ĐỔI LOẠI HÌNH THUÊ BAO",
        list: [
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          {
            name: "Lắp đặt mới",
            link: { name: "Ui.buttons" }
          }
        ]
      },

      momentFormat: {
        stringify: date => {
          return date ? moment(date).format("DD-MM-YYYY HH:mm:ss") : "";
        },
        parse: value => {
          return value ? moment(value, "DD-MM-YYYY HH:mm:ss").toDate() : null;
        }
      },

      /* btn disable action menu */
      disableBtnHuy: true,
      disableBtnNhapMoi: true,
      disabledBtnGiaoPhieu: true,
      disableBtnHoanThien: true,
      disabledBtnGhiLai: true,

      visibleBtnHuy: true,
      visibleBtnNhapMoi: true,
      visibleBtnGiaoPhieu: true,
      visibleBtnHoanThien: true,
      visibleBtnGhiLai: true,

      tagForm: 0,
      tthd_id: 0,
      loaiHd: 6,

      /* value thong tin hop dong */
      maGiaoDich: "",
      maHopDong: "",
      ngayYeuCau: "",
      ngayTH: "",
      ngayLapHD: "",
      maKH: "",
      tenKH: "",
      diaChiKH: "",

      /* value thong tin thue bao */
      selectedDichVu: "",
      selectedLoaiHinh: "",
      selectedKieuYeuCau: "",
      soMayAcc: "",
      tenThueBao: "",
      selectedDVTinhCuoc: "",
      diaChiLD: "",
      ghiChu: "",
      matKhau: "",
      maTN: "",
      email: "",
      selectedTocDo: "",
      selectedMucCuoc: "",
      selectedLoaiThietBi: "",
      hoiCapADSL: "",
      doiCapADSL: "",
      metDay: "",
      selectedBras: "",
      selectedDslam: "",
      ipTinh: "",
      system: "",
      rack: "",
      shelf: "",
      slot: "",
      port: "",
      VPI_SVLAN: "",
      VCI_CVLAN: "",

      // thông tin thuê bao với dịch vụ là MEGAWAN
      huongKetNoi: "",
      selectedLoaiKenh: "",
      selectedThoiHan: "",
      selectedModem: "",
      selectedTocDoKenh: "",
      selectedTrangBi: "",
      selectedChuQuan: "",
      dslam: "",
      selectedRouter: "",
      slotPE: "",
      portPE: "",
      tenThietBi: "",
      port: "",
      svLan: "",
      cvLan: "",
      lanIP: "",
      wanIP: "",

      loaiHinhFilterByDichVu: [],
      kieuYeuCauFilterByLoaiHinh: [],
      current_time: "",
      tocDoFilter: [],
      tocDoKenhListFilter: [],

      dataCurrent: DATA_CURRENT,

      // disable select
      disableDVTinhCuoc: false,

      isDisplayMegaWan: false,
      isDisplayBangThongCoDinh: true,
      isDisplayGphone: false,

      dichVuListFilter: [
        {
          DICHVUVT_ID: DICHVU_VIENTHONG.ADSL,
          TEN_DVVT: "Băng rộng cố định"
        },
        {
          DICHVUVT_ID: DICHVU_VIENTHONG.MEGAWAN,
          TEN_DVVT: "MegaWan"
        },
        {
          DICHVUVT_ID: DICHVU_VIENTHONG.GPHONE,
          TEN_DVVT: "Gphone"
        }
      ],

      disableEmail: false,
      disableLoaiThietBi: false,
      disableBras: false,
      disbaleDslam: false
    };
  },

  async created() {
    this.$root.showLoading(true);
    this.tagForm = this.tag;
    this.tthd_id = this.tag;

    this.HienThiGiaoDien();
    this.setButton(0);

    let current_time = moment()
      .format("DD-MM-YYYY HH:mm:ss")
      .toString();

    this.current_time = current_time;
    this.ngayYeuCau = this.current_time;
    this.ngayTH = this.current_time;
    this.ngayLapHD = this.current_time;

    this.loadDataForm();

    this.$root.showLoading(false);
  },

  computed: {
    ...mapGetters("mapChangeSubsTypeByInstallOperation", [
      "dichVuList",
      "loaiHinhList",
      "DVTinhCuocList",
      "kieuYeuCauList",
      "tocDoList",
      "mucCuocList",
      "loaiThietBiList",
      "brasList",
      "dslamList",
      "loaiKenhList",
      "thoiHanList",
      "modemList",
      "tocDoKenhList",
      "trangBiList",
      "chuQuanList",
      "routerList"
    ])
  },
  methods: {
    ...mapActions("mapChangeSubsTypeByInstallOperation", [
      "getDichVuList",
      "getLoaiHinhList",
      "getDVTinhCuocList",
      "fetchDanhSachHopDong",
      "fetchKieuYeuCauList",
      "fetchTocDoList",
      "fetchMucCuocList",
      "fetchLoaiThietBiList",
      "fetchBrasList",
      "fetchDslamList",
      "checkMaSoTBKhoSo",
      "checkMaSoTB",
      "fetchInformationHopDongThueBaoGP",
      "saveHoSoLoaiHinhThueBao",
      "completedHoSoLoaiHinhThueBao",
      "LAY_DSHD_THEO_MA_GD",
      "LAY_DS_HDTB_THEO_HDKH_TTHD",
      "LAY_DS_HDTB_HTHS",
      "HT_LoaiHinh_TB_Combobox",
      "HT_Kieu_LD_Combobox_No_LoaiTb_Id",
      "HT_Kieu_LD_Combobox_Have_LoaiTb_Id",
      "HT_Loai_Kenh_Combobox",
      "HT_ThoiHan_MegaWan_Combobox",
      "HT_Loai_Modem_Combobox",
      "HT_Tocdo_Kenh_Combobox",
      "HT_TrangBi_MegaWan_Combobox",
      "HT_ChuQuan_MegaWan_Combobox",
      "LAY_DS_HOPDONG_THEO_MA_TB",
      "LAY_DS_HDTB_ADSL_THEO_HDTB_ID",
      "lay_ds_hdtb_GP_theo_hdtb_id",
      "LAY_DS_HDTB_MGWAN_THEO_HDTB_ID",
      "HT_ROUTER_VTN_COMBOBOX"
    ]),

    // hiện thị giao diện theo cách gọi (tagForm)
    HienThiGiaoDien() {
      this.tthd_id = this.tag;
      if (this.tag === TrangThaiHD.DIEUHANH_THI_CONG) {
        this.visibleBtnGhiLai = false;
        this.visibleBtnHuy = false;
        this.visibleBtnNhapMoi = true;
        this.visibleBtnGiaoPhieu = false;
        this.visibleBtnHoanThien = true;
      }

      if (this.tag === TrangThaiHD.DANG_THI_CONG) {
        this.visibleBtnGhiLai = true;
        this.visibleBtnHuy = true;
        this.visibleBtnNhapMoi = true;
        this.visibleBtnHoanThien = true;
      }

      if (this.tag === TrangThaiHD.DA_THI_CONG_XONG) {
        this.visibleBtnGhiLai = false;
        this.visibleBtnHuy = false;
        this.visibleBtnGiaoPhieu = false;
        this.visibleBtnNhapMoi = true;
        this.visibleBtnHoanThien = true;
      }

      if (this.tag === TrangThaiHD.SUA_HD) {
        this.visibleBtnGhiLai = true;
        this.visibleBtnHuy = true;
        this.visibleBtnNhapMoi = true;
        this.visibleBtnHoanThien = true;
      }
    },

    async loadDataForm() {
      await this.getDichVuList();
      // dichVuListFilter
      if (this.dichVuList.length) {
        this.selectedDichVu = DICHVU_VIENTHONG.ADSL;
        this.disableDVTinhCuoc = true;
      }

      await this.getLoaiHinhList();
      if (this.loaiHinhList.length) {
        this.loaiHinhFilterByDichVu = this.loaiHinhList.filter(
          item => item.DICHVUVT_ID === this.selectedDichVu
        );
        // default: MyTV
        if (this.loaiHinhFilterByDichVu.length) {
          this.selectedLoaiHinh = LOAIHINH_THUEBAO.INTERNET_MYTV;
        }
      }

      if (this.selectedDichVu) {
        await this.fetchKieuYeuCauList({
          loaiHd: this.loaiHd,
          loaiTb: 0
        });
        // this.kieuYeuCauFilterByLoaiHinh = this.kieuYeuCauList;
        // // default: Chuyển Mytv cáp đồng sang kèm fiber quang
        // if (this.kieuYeuCauList.length) {
        //   this.selectedKieuYeuCau = 16;
        // }
      }

      await this.getDVTinhCuocList({
        phanvung_id: 0,
        loaidv_id: this.selectedDichVu
      });
      if (this.DVTinhCuocList.length) {
        this.selectedDVTinhCuoc = this.DVTinhCuocList[0].donvi_id;
      }

      await this.fetchMucCuocList();
      if (this.mucCuocList.length) {
        this.selectedMucCuoc = this.mucCuocList[0].MUCCUOC_ID;
      }

      await this.fetchBrasList();
      if (this.brasList.length) {
        // default: Bras2(203.210.144.128)
        this.selectedBras = 391;
      }

      await this.fetchDslamList({
        brasId: parseInt(this.selectedBras),
        kieu: 2
      });
      if (this.dslamList.length) {
        this.selectedDslam = "1546";
      }

      await this.fetchTocDoList();
      if (this.tocDoList.length) {
        this.tocDoFilter = this.tocDoList;
        this.selectedTocDo = this.tocDoList[0].tocdo_id;
      }

      await this.fetchLoaiThietBiList({
        dichvuvt_id: this.selectedDichVu
      });
      if (this.loaiThietBiList.length) {
        this.selectedLoaiThietBi = this.loaiThietBiList[0].thietbidc_id;
      }

      await this.HT_ROUTER_VTN_COMBOBOX();
      console.log("routerList:", this.routerList);
      if (this.routerList.length) {
        this.selectedRouter = this.routerList[0].pe_id;
      }

      if (this.selectedLoaiHinh === LOAIHINH_THUEBAO.INTERNET_MYTV) {
        this.disableEmail = true;
        this.disableLoaiThietBi = true;
        this.disableBras = true;
        this.disbaleDslam = true;
      }
    },

    showPopupSearchContract() {
      this.clear();
      this.$refs.popupSearchContract.showModal();
    },

    // chấp nhận thông tin tra cứu hợp đồng
    onAcceptSearchContract(data) {
      this.dataCurrent = data;
      this.maGiaoDich = data.ma_gd;
      this.$refs.txtMaGD.focus();
      this.btnLayMaGD_Click({ key: "Enter" }, data);
    },

    // enter mã giao dịch
    async btnLayMaGD_Click(e, data) {
      if (e.key === "Enter") {
        if (this.maGiaoDich && this.maGiaoDich.length > 0) {
          this.loading(true);

          const variables = {
            in_donvi_dl_id: 0,
            in_donvi_id: 0,
            in_loaihd_id: 0,
            in_ma_gd: this.maGiaoDich,
            in_nhanvien_id: data ? data.nhanvien_id : 0,
            in_tthd_id: 0,
            phanvung_id: 0
          };

          try {
            // call api click enter mã  giao dich
            const ds = await this.LAY_DSHD_THEO_MA_GD(variables);
            await this.HienThiTTHopDongKH(ds, data);
          } catch (error) {
            console.log("error:", error);
          }

          this.loading(false);
        }
      }
    },

    // enter txtMaTB_KeyPress
    async txtMaTB_KeyPress() {
      // const item = this.kieuYeuCauList.find(item => item.kieuld_id === 182);
      const variables = {
        dichVuVtId: 0,
        donViDlId: 0,
        loaiHdId: 6,
        loaiTbId: 0,
        maTb: this.soMayAcc, //this.soMayAcc,
        ttHdId: TrangThaiHD.DA_THI_CONG_XONG
      };
      const dtList = await this.LAY_DS_HOPDONG_THEO_MA_TB(variables);
      await this.HienThiTTHopDongKH(dtList);
    },

    // Hiển thị thông tin Khách hàng của hợp đồng
    async HienThiTTHopDongKH(list, data) {
      this.selectedDichVu = list[0]?.dichvuvt_id;

      if (list[0].dichvuvt_id === DICHVU_VIENTHONG.ADSL) {
        this.isDisplayBangThongCoDinh = true;
        this.isDisplayMegaWan = false;
        this.isDisplayGphone = false;
      }

      if (list[0].dichvuvt_id === DICHVU_VIENTHONG.MEGAWAN) {
        this.isDisplayBangThongCoDinh = false;
        this.isDisplayMegaWan = true;
        this.isDisplayGphone = false;
      }

      if (list[0].dichvuvt_id === DICHVU_VIENTHONG.GPHONE) {
        this.isDisplayBangThongCoDinh = false;
        this.isDisplayMegaWan = false;
        this.isDisplayGphone = true;
      }
      if (list.length) {
        this.disableBtnHoanThien = false;

        // thông tin khách hàng
        this.maHopDong = list[0].ma_hd;
        this.ngayTH = list[0].ngay_ht ? list[0].ngay_ht : this.current_time;
        this.ngayYeuCau = list[0].ngay_yc ? list[0].ngay_yc : this.current_time;
        this.ngayLapHD = list[0].ngaylap_hd
          ? list[0].ngaylap_hd
          : this.current_time;
        this.maKH = list[0].ma_kh;
        this.tenKH = list[0].ten_kh;
        this.diaChiKH = list[0].diachi_kh;
        this.maGiaoDich = list[0].ma_gd;

        const variables = {
          in_hdkh_id: data ? data.hdkh_id : list[0].hdkh_id,
          in_tthd_id: this.tthd_id
        };
        const ds = await this.LAY_DS_HDTB_THEO_HDKH_TTHD(variables);
        await this.HienThiTTHopDongTB(ds);

        try {
          if (this.tag === 5) {
            const variables = {
              hdkh_id: data ? data.hdkh_id : list[0].hdkh_id,
              tthd_id: this.tthd_id,
              nhanvien_id: data ? data.nhanvien_id : list[0].nhanvien_id
            };
            await this.LAY_DS_HDTB_HTHS(variables);
          } else {
            const variables = {
              in_hdkh_id: data ? data.hdkh_id : list[0].hdkh_id,
              in_tthd_id: this.tthd_id
            };
            await this.LAY_DS_HDTB_THEO_HDKH_TTHD(variables);
          }
        } catch (error) {}
      } else {
        if (this.soMayAcc) {
          this.$toast.error(`Mã giao dịch ${this.soMayAcc} không có dữ liệu`);
        }
        if (this.maGiaoDich) {
          this.$toast.error(`Mã giao dịch ${this.maGiaoDich} không có dữ liệu`);
        }

        this.setButton(0);
        this.loadDataForm();
      }
    },

    // Hiển thị thông  tin hợp đồng thuê bao
    async HienThiTTHopDongTB(data) {
      console.log("ITEM:", data[0]);
      const item = data[0];
      this.tenThueBao = item.ten_tb;
      this.selectedDichVu = item.dichvuvt_id;

      this.selectedKieuYeuCau = item.kieuld_id;
      this.soMayAcc = item.ma_tb;

      this.diaChiLD = item.diachi_ld;
      this.ghiChu = item.ghichu;

      try {
        const list = await this.HT_LoaiHinh_TB_Combobox(this.selectedDichVu);
        const dataLoaiHinh = list.map(item => {
          const data = {
            LOAIHINH_TB: item.loaihinh_tb,
            LOAITB_ID: item.loaitb_id
          };
          return data;
        });
        this.loaiHinhFilterByDichVu = dataLoaiHinh;

        await this.getDVTinhCuocList({
          phanvung_id: 0,
          loaidv_id: LOAI_DONVI.DONVIQL_LD
        });

        this.selectedDVTinhCuoc = item.donvi_id;
        this.selectedLoaiHinh = item.loaitb_id;
      } catch (error) {
        console.log("error:", error);
      }

      if (data.length) {
        await this.HienThiTTMoRong(
          data[0].ma_tb,
          data[0].hdtb_id,
          data[0].dichvuvt_id
        );
      }
    },

    // Hiển thị thông tin mở rộng
    async HienThiTTMoRong(ma_tb, hdtb_id, dichvuvt_id) {
      let ds = [];
      switch (dichvuvt_id) {
        case DICHVU_VIENTHONG.ADSL:
          ds = await this.LAY_DS_HDTB_ADSL_THEO_HDTB_ID(hdtb_id);
          break;

        case DICHVU_VIENTHONG.GPHONE:
          ds = await this.lay_ds_hdtb_GP_theo_hdtb_id(hdtb_id);

          break;

        case DICHVU_VIENTHONG.MEGAWAN:
          ds = await this.LAY_DS_HDTB_MGWAN_THEO_HDTB_ID(hdtb_id);
          break;

        default:
          break;
      }

      if (ds.length) {
        const item = ds[0];

        await this.HT_Tocdo_Kenh_Combobox();
        this.tocDoKenhListFilter = this.tocDoKenhList.map(item => {
          const res = {
            ...item,
            TOCDO_DONVI: item.TOCDO + " " + item.DONVI
          };
          return res;
        });

        await this.fetchLoaiThietBiList({ dichvuvt_id: this.selectedDichVu });
        await this.getDVTinhCuocList({
          phanvung_id: 0,
          loaidv_id: LOAI_DONVI.DONVIQL_LD
        });

        await this.HT_Loai_Kenh_Combobox();
        await this.HT_ThoiHan_MegaWan_Combobox();
        await this.HT_Loai_Modem_Combobox();
        await this.HT_TrangBi_MegaWan_Combobox();
        await this.HT_ChuQuan_MegaWan_Combobox();

        switch (dichvuvt_id) {
          case DICHVU_VIENTHONG.ADSL:
            this.port = item.port_id !== "" ? item.port_id : 0;
            this.email = item.email;
            this.maTN = item.matb_tn;
            this.selectedTocDo = item.tocdo_id;
            this.selectedMucCuoc = item.muccuoc_id;
            this.selectedLoaiThietBi = item.thietbidc_id;
            this.hoiCapADSL = item.hopcap;
            this.doiCapADSL = item.doicap;
            this.matKhau = item.password;
            this.ipTinh = item.ip;
            this.slot = item.slot;
            this.VPI_SVLAN = item.vpi;
            this.VCI_CVLAN = item.vci;
            this.system = item.system;
            this.rack = item.rack;
            this.shelf = item.shelf;
            this.selectedBras = item.bras_id;

            await this.changeBras();

            this.selectedDslam = item.dslam_id;

            break;

          case DICHVU_VIENTHONG.GPHONE:
            break;

          case DICHVU_VIENTHONG.MEGAWAN:
            this.huongKetNoi = item.huong_kn;
            this.selectedTocDoKenh = item.tocdo_id;
            this.selectedModem = item.loaimd_id;
            this.selectedLoaiKenh = item.loaikenh_id;
            this.selectedLoaiThietBi = item.thietbidc_id;
            this.selectedChuQuan = item.chuquan_id;
            this.selectedThoiHan = item.thoihan_id;
            this.selectedTrangBi = item.trangbi_id;
            this.portPE = item.port_pe;
            this.slotPE = item.slot_pe;
            this.wanIP = item.wan_ip;
            this.lanIP = item.lan_ip;
            this.dslam = item.dslam;
            this.tenThietBi = item.ten_tbi;
            this.port = item.port;
            this.svLan = item.svlan;
            this.cvLan = item.cvlan;
            break;

          default:
            break;
        }
      }
    },

    // Hiển thị thông tin thue bao của hợp đồng
    async HienThiDanhSacHDTB(data) {
      this.dataCurrent = data;
    },

    async currentRecord(data) {
      if (Object.keys(data).length) {
        this.dataCurrent = data;
        this.HienThiDanhSacHDTB(data);
      }
    },

    /**
     * change dịch vụ sẽ load lại:
        1. data kiểu yêu cầu
        2. Đơn vị tính cước
        3. Loại thiết bị
    */
    async cboDichVuVT_SelectedValueChanged() {
      this.$root.showLoading(true);
      if (this.selectedDichVu === DICHVU_VIENTHONG.ADSL) {
        this.isDisplayBangThongCoDinh = true;
        this.isDisplayMegaWan = false;
        this.isDisplayGphone = false;
      }

      if (this.selectedDichVu === DICHVU_VIENTHONG.MEGAWAN) {
        this.isDisplayBangThongCoDinh = false;
        this.isDisplayMegaWan = true;
        this.isDisplayGphone = false;
        await this.HT_Loai_Kenh_Combobox();
        // default: internet trực tiếp
        this.selectedLoaiKenh = 1;

        await this.HT_ThoiHan_MegaWan_Combobox();
        // default: ngắn hạn
        this.selectedThoiHan = 1;

        await this.HT_Loai_Modem_Combobox();
        // default: Router
        this.selectedModem = 1;

        await this.HT_Tocdo_Kenh_Combobox();
        // default: phần tử đầu
        this.tocDoKenhListFilter = this.tocDoKenhList.map(item => {
          const res = {
            ...item,
            TOCDO_DONVI: item.TOCDO + " " + item.DONVI
          };
          return res;
        });
        console.log("tocDoKenhListFilter:", this.tocDoKenhListFilter);
        this.selectedTocDoKenh = this.tocDoKenhList[0].TOCDO_ID;

        await this.fetchLoaiThietBiList({ dichvuvt_id: this.selectedDichVu });
        // default: CPE
        this.selectedLoaiThietBi = "3";

        await this.HT_TrangBi_MegaWan_Combobox();
        // default: Mua thiết bị
        this.selectedTrangBi = 1;

        await this.HT_ChuQuan_MegaWan_Combobox();
        // default: phần tử đầu
        this.selectedChuQuan = this.chuQuanList[0].CHUQUAN_ID;

        // load data router nữa ( chưa xong)
      }

      if (this.selectedDichVu === DICHVU_VIENTHONG.GPHONE) {
        this.isDisplayBangThongCoDinh = false;
        this.isDisplayMegaWan = false;
        this.isDisplayGphone = true;
      }

      try {
        const list = await this.HT_LoaiHinh_TB_Combobox(this.selectedDichVu);
        const dataLoaiHinh = list.map(item => {
          const data = {
            LOAIHINH_TB: item.loaihinh_tb,
            LOAITB_ID: item.loaitb_id
          };
          return data;
        });
        this.loaiHinhFilterByDichVu = dataLoaiHinh;
        this.selectedLoaiHinh = dataLoaiHinh[0].LOAITB_ID;

        await this.getDVTinhCuocList({
          phanvung_id: 0,
          loaidv_id: LOAI_DONVI.DONVIQL_LD
        });
        if (this.DVTinhCuocList.length) {
          this.selectedDVTinhCuoc = this.DVTinhCuocList[0].donvi_id;
        }

        await this.loadDataKieuYeu();
        await this.loadLoaiThietBi();
      } catch (error) {
        console.log("error:", error);
      }

      this.$root.showLoading(false);
    },

    // hàm load lại kiểu yêu cầu khi dịch vụ thay đổi, khi loại hình thay đổi
    async loadDataKieuYeu() {
      // if (!this.loaiHinhFilterByDichVu.length) {
      //   const res = await this.HT_Kieu_LD_Combobox_No_LoaiTb_Id(this.loaiHd);
      //   this.kieuYeuCauFilterByLoaiHinh = res;
      // } else {
      //   const variables = {
      //     loaihd_id: this.loaiHd,
      //     loaitb_id: this.selectedLoaiHinh
      //   };
      //   const res = await this.HT_Kieu_LD_Combobox_Have_LoaiTb_Id(variables);
      //   this.kieuYeuCauFilterByLoaiHinh = res;
      // }

      this.selectedKieuYeuCau = this.kieuYeuCauList[0].kieuld_id;
    },

    // hàm load lại data loại thiết bị khi dịch vụ thay đổi
    async loadLoaiThietBi() {
      await this.fetchLoaiThietBiList({ dichvuvt_id: this.selectedDichVu });
      this.selectedLoaiThietBi = this.loaiThietBiList[0]?.thietbidc_id || 0;
    },

    /** change Bras thì load data Dslam */
    async changeBras() {
      this.$root.showLoading(true);
      try {
        await this.fetchDslamList({
          brasId: parseInt(this.selectedBras),
          kieu: 2
        });
        if (this.dslamList.length) {
          this.selectedDslam = this.dslamList[0].ID;
        }
      } catch (error) {
        console.log("error:", error);
      }

      this.$root.showLoading(false);
    },

    async changeLoaiHinh() {
      await this.loadDataKieuYeu();

      if (this.tocDoList.length) {
        const list = this.tocDoList.filter(
          item => item.loaitb_id === this.selectedLoaiHinh
        );
        this.tocDoFilter = list;
        this.selectedTocDo = this.tocDoFilter[0].tocdo_id;
      }
    },

    // hàm kiểm tra thông tin Khách hàng
    checkInformationCustomer() {
      if (this.maHopDong === "") {
        this.$toast.error("Hãy nhập mã hợp đồng !");
        return false;
      }
      if (this.maKH === "") {
        this.$toast.error("Kiểm tra lại mã khách hàng !");
        return false;
      }
      if (this.tenKH === "") {
        this.$toast.error("Nhập tên khách hàng !");
        return false;
      }
      if (this.diaChiKH === "") {
        this.$toast.error("Nhập địa chỉ khách hàng !");
        return false;
      }

      return true;
    },

    //hàm kiểm tra thông tin thuê bao
    checkInformationThueBao() {
      if (this.tenThueBao === "") {
        this.$toast.error("Hãy nhập tên thuê bao !");
        return false;
      }
    },

    // hàm kiểm tra dữ liệu hợp đồng thuê bao ADSL
    checkInformationThueBaoADSL() {
      if (this.port === "") {
        this.$toast.error("Hãy nhập thông số Port !");
        return false;
      }

      if (this.slot === "") {
        this.$toast.error("Hãy nhập thông số Slot !");
        return false;
      }

      if (this.doiCapADSL === "") {
        this.$toast.error("Hãy nhập thông số đôi cáp !");
        return false;
      }

      if (this.hoiCapADSL === "") {
        this.$toast.error("Hãy nhập thông số hộp cáp !");
        return false;
      }

      if (this.VCI_CVLAN === "") {
        this.$toast.error("Hãy nhập thông tin cự ly !");
        return false;
      }
      return true;
    },

    // hàm kiểm tra mã số thuê bao kho số
    async checkMaSoThueBaoKhoSo() {
      const variables = {
        maTb: this.soMayAcc ? parseInt(this.soMayAcc) : 0,
        dichvuvtId: parseInt(this.selectedDichVu)
      };

      const status = await this.checkMaSoTBKhoSo(variables);
      if (status === "Thành công") {
        return true;
      } else {
        this.$toast.error("Số máy chưa có trong kho số !");
        return false;
      }
    },

    //hàm kiểm tra mã thuê bao
    async checkMaThueBao() {
      const variables = {
        maTb: this.soMayAcc,
        hdtbId: this.maHopDong ? parseInt(this.maHopDong) : 0,
        dichVuVtId: this.selectedDichVu
      };
      const data = await this.checkMaSoTB(variables);
      if (data === 0) {
        return true;
      } else {
        this.$toast.error("Số máy hoặc Account đã được cấp !");
        return false;
      }
    },

    /* handle click btn ghi lại */
    async handleGhiLai() {
      const checkMaSoThueBaoKhoSo = await this.checkMaSoThueBaoKhoSo();
      const checkMaThueBao = await this.checkMaThueBao();

      // kiểm tra thông tin khách hàng
      if (!this.checkInformationCustomer()) return;
      if (!this.checkInformationThueBao()) return;
      if (!this.checkInformationThueBaoADSL()) return;
      if (!checkMaSoThueBaoKhoSo) return;
      if (!checkMaThueBao) return;

      const js_hdkh = `[{"PHANVUNG_ID":${this.dataCurrent.phanvung_ID},"HDKH_ID":${this.dataCurrent.hdkh_ID},"MA_GD": ${this.maGiaoDich},"MA_KH":${this.maKH},"KHACHHANG_ID":0,"TEN_KH":${this.tenKH},"DIACHI_KH":${this.diaChiKH}}]`;

      const js_hdtb = `[{"PHANVUNG_ID":${
        this.dataCurrent.phanvung_ID
      },"HDTB_ID":${this.dataCurrent.hdtb_ID},"HDKH_ID":${
        this.dataCurrent.hdkh_ID
      },"THUEBAO_ID":${this.dataCurrent.thuebao_ID},"MA_TB":${parseInt(
        this.soMayAcc
      )},"TEN_TB":${this.tenThueBao},"KIEULD_ID":${parseInt(
        this.selectedKieuYeuCau
      )},"LOAITB_ID":${parseInt(this.selectedLoaiHinh)},"NGAY_HT":${
        this.ngayTH
      },"DIACHI_LD":${this.diaChiLD},"MAY_CN":${
        this.dataCurrent.may_CN
      },"NGAY_CN":${this.dataCurrent.ngay_CN},"NGUOI_CN":${
        this.dataCurrent.nguoi_CN
      },"CHUNHOM":null,"IP_CN":${this.dataCurrent.ip_CN},"GHICHU":${
        this.ghiChu
      }}]`;

      const js_hdtb_adsl = `[{"PHANVUNG_ID":${
        this.dataCurrent.phanvung_ID
      },"HDTB_ID":${this.dataCurrent.hdtb_ID},"EMAIL":${
        this.email
      },"MUCCUOC_ID":${parseInt(this.selectedMucCuoc)},"TOCDO_ID":${parseInt(
        this.selectedTocDo
      )},"CULY":null,"PASSWORD":null,"BRAS_ID":${parseInt(
        this.selectedBras
      )},"DSLAM_ID":${parseInt(this.selectedDslam)},"IP":null,"MADOICAP":${
        this.doiCapADSL
      }}]`;

      let js_hdtb_gp = "";
      const dataHDTB_GP = await this.fetchInformationHopDongThueBaoGP(668857);
      if (dataHDTB_GP) {
        js_hdtb_gp = `[{"HDTB_ID":${dataHDTB_GP[0].hdtb_id},"SIMCARD":${dataHDTB_GP[0].simcard},"CELL1":${dataHDTB_GP[0].cell1},"CELL2":${dataHDTB_GP[0].cell2},"CELL3":${dataHDTB_GP[0].cell3},"TOADO":${dataHDTB_GP[0].toado},"GHICHU":${dataHDTB_GP[0].ghichu}, "MC_TBGP_ID": ${dataHDTB_GP[0].mc_tbgp_id}}]`;
      }

      const variables = {
        hdtb_id: this.dataCurrent.hdtb_ID,
        dichvuvt_id: this.selectedDichVu ? parseInt(this.selectedDichVu) : 0,
        ma_tb: this.soMayAcc,
        js_hdkh: js_hdkh,
        js_hdtb: js_hdtb,
        js_hdtb_adsl: js_hdtb_adsl,
        js_hdtb_gp: js_hdtb_gp
      };

      await this.saveHoSoLoaiHinhThueBao(variables);
    },

    /*hanle click btn hoàn thiện */
    async handleHoanThien() {
      try {
        const variables = {
          hdtb_id: this.dataCurrent.hdtb_id,
          dichvuvt_id: parseInt(this.dataCurrent.dichvuvt_id),
          ngay_ht: this.current_time.slice(0, 10).replace(/-/g, "/"),
          ngay_cn: this.current_time.slice(0, 10).replace(/-/g, "/"),
          ngay_tt: this.current_time.slice(0, 10).replace(/-/g, "/")
        };
        const res = await this.completedHoSoLoaiHinhThueBao(variables);
        if (res === "Success") {
          this.$toast.success("Hoàn thiện thuê bao thành công!");
        } else {
          this.$toast.error("Hợp đồng chưa được phép hoàn thiện!");
        }
      } catch (error) {
        this.$toast.error("error");
      }
    },

    // nút nhập mới
    nhapMoi() {
      this.setButton(1);
    },

    // nút hủy
    huy() {
      this.setButton(2);
    },

    setButton(num) {
      // mới khởi tạo form
      if (num === 0) {
        if (this.tag === TrangThaiHD.DA_THI_CONG_XONG) {
          this.disableBtnNhapMoi = true;
        }
      }
      // nhập mới
      if (num === 1) {
        this.disableBtnHuy = false;
        this.disableBtnNhapMoi = true;
        this.disabledBtnGiaoPhieu = true;
        this.disableBtnHoanThien = true;
        this.disabledBtnGhiLai = false;
        this.$refs.txtMaGD.focus();
      }

      // hủy
      if (num === 2) {
        this.disableBtnHuy = true;
        this.disableBtnNhapMoi = false;
        this.disabledBtnGiaoPhieu = true;
        this.disableBtnHoanThien = true;
        this.disabledBtnGhiLai = true;
      }
    },

    clear() {
      // clear thông tin KH
      this.$refs.txtMaGD.focus();
      this.maGiaoDich = "";
      this.maHopDong = "";
      this.ngayTH = this.current_time;
      this.ngayYeuCau = this.current_time;
      this.ngayLapHD = this.current_time;
      this.maKH = "";
      this.tenKH = "";
      this.diaChiKH = "";

      // clear thông tin Thue bao
      this.sel;
      this.soMayAcc = "";
      this.tenThueBao = "";
      this.doiCapADSL = 0;
      this.selectedDVTinhCuoc = 0;

      this.ghiChu = "";
      this.tenThueBao = "";
      this.soMayAcc = "";
      this.slot = "";
      this.port = "";

      this.VPI_SVLAN = "";
      this.VCI_CVLAN = "";
      this.selectedDslam = "";
    }
  },
  watch: {
    selectedLoaiHinh(val) {
      if (
        this.selectedLoaiHinh === LOAIHINH_THUEBAO.INTERNET_MYTV &&
        this.isDisplayBangThongCoDinh
      ) {
        this.disableEmail = true;
        this.disableLoaiThietBi = true;
        this.disableBras = true;
        this.disbaleDslam = true;
      } else {
        this.disableEmail = false;
        this.disableLoaiThietBi = false;
        this.disableBras = false;
        this.disbaleDslam = false;
      }
    }
  }
};
</script>
<style scoped>
.legend-title {
  display: flex;
  justify-content: space-between;
}

.group-item {
  display: flex;
}

.col-rack {
  padding-right: 0px !important;
}

.title-sub,
.color-number-input input {
  color: #d32f2f !important;
}

/* /deep/ .modal-content {
  height: 850px !important;
} */
.can-enter-input {
  background-color: #fff9eb;
}

.item-radio {
  margin-bottom: 20px;
}

.disableInput {
  cursor: pointer;
  pointer-events: none;
}

.check-and-input {
  display: flex !important;
  padding-left: 0px !important;
}

/deep/ .check-and-input .info-row {
  width: 20% !important;
}
</style>
