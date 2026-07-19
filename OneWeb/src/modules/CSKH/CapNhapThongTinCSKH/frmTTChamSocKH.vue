<template>
  <!-- <b-modal
    id="frmTTChamSocKH"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    @show="onShow"
  > -->
  <ejs-dialog
    :enableResize="true"
    :allowDragging="true"
    ref="frmTTChamSocKH"
    :header="'Cập nhật thông tin chăm sóc khách hàng'"
    showCloseIcon="true"
    width="80%"
    :visible="false"
    :open="dialogOpen"
    :close="closeDialog"
    :overlayClick="closeDialog"
    :closeOnEscape="false"
    :isModal="true"
>
    <!-- <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Cập nhật thông tin chăm sóc khách hàng
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="$bvModal.hide('frmTTChamSocKH')"
        ></div>
      </div> -->
    <div class="">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a
              href="javascript: void(0)"
              :class="{ disabled: !pBtnNhapMoiEnabled }"
              @click="onNhapMoiClicked"
            >
              <span class="icon one-file-plus"></span>Nhập mới
            </a>
          </li>
          <li>
            <a
              href="javascript: void(0)"
              :class="{ disabled: !pBtnGhiLaiEnabled }"
              @click="onCapNhapClicked"
            >
              <span class="icon one-reload1"></span>Cập nhật
            </a>
          </li>
          <li>
            <a href="javascript: void(0)" @click="onThemCSClicked">
              <span class="icon one-mobile-plus" ></span
              >Thêm CS
            </a>
          </li>
          <li>
            <a
              href="javascript: void(0)"
              :class="{ disabled: !pBtnXoaEnabled }"
              @click="onXoaCSClicked"
            >
              <span class="icon one-trash"></span>Xóa CS
            </a>
          </li>
          <li>
            <a
              href="javascript: void(0)"
              @click="onTraCuuClicked"
            >
              <span class="icon one-file-search"></span>Tra cứu
            </a>
            <frmTraCuuTTCSKH ref="frmTraCuuTTCSKH"/>
            <frmSoThichNV @onSoThichHide="getDsSoThich"/>
            <frmDoiTuongCSKH @onDoiTuongHide="getDsDoiTuongChamSoc"/>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title" style="font-weight:600 !important">
            <div class="pull-left" style="padding-top: 10px">
              Thông tin tìm kiếm
            </div>
            <div class="pull-right">
              <input
                type="text"
                value=""
                class="form-control"
                ref="txtThongtinID"
                v-model="pTxtThongtinID"
                v-if="isShowThongTinID"
              />
            </div>
            <div class="clearfix"></div>
          </div>
          <div class="row">
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key">Số máy/Acc</div>
                <div class="value">
                  <input
                    type="text"
                    value=""
                    class="form-control highlight font-weight-bold"
                    ref="txtMaTB"
                    v-model="pTxtMaTB"
                    @keydown="maTBKeyPress"
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-9 col-12">
              <div class="info-row">
                <div class="key">Tên KH</div>
                <div class="value">
                  <input
                    type="text"
                    value=""
                    class="form-control font-weight-bold"
                    ref="txtTenKH"
                    v-model="pTxtTenKH"
                    disabled
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key">Mã khách hàng <BssRequiredMarker class="ml-1"/></div>
                <div class="value">
                  <input
                    type="text"
                    value=""
                    class="form-control font-weight-bold"
                    ref="txtMaKH"
                    v-model="pTxtMaKH"
                    disabled
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-9 col-12">
              <div class="info-row">
                <div class="key">Địa chỉ</div>
                <div class="value">
                  <input
                    type="text"
                    value=""
                    class="form-control font-weight-bold"
                    ref="txtDiaChiKH"
                    v-model="pTxtDiaChiKH"
                    disabled
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title" style="font-weight:600 !important">Thông tin chăm sóc</div>
          <div class="row">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w120">Loại đối tượng</div>
                <div class="value">
                    <div class="input-more-button">
                        <button class="btn" data-toggle="tooltip" title="Cập nhập đối tượng chăm sóc" v-b-modal.frmDoiTuongCSKH>
                            <span class="-ap icon-more_horiz"></span>
                        </button>
                        <div class="">
                          <select2 v-model="pLoaiDoiTuongSelected"
                              ref="cboDoiTuong"
                              :options="pDsDoiTuongChamSoc"
                          />
                           <!-- <b-form-select
                              v-model="pLoaiDoiTuongSelected"
                              :options="pDsDoiTuongChamSoc"
                              ref="cboDoiTuong"
                              value-field="doiTuongCsId"
                              text-field="tenDoiTuongCs"
                              class="form-control"
                            >
                            </b-form-select> -->
                        </div>

                    </div>
                </div>
                <!-- <div class="value">
                  <b-form-select
                    v-model="pLoaiDoiTuongSelected"
                    :options="pDsDoiTuongChamSoc"
                    ref="cboDoiTuong"
                    value-field="doiTuongCsId"
                    text-field="tenDoiTuongCs"
                    class="form-control"
                  >
                  </b-form-select>
                </div> -->
              </div>
            </div>
            <div class="col-sm-8 col-12">
              <div class="info-row">
                <div class="key">Tên ĐT <BssRequiredMarker class="ml-1"/></div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control font-weight-bold"
                    ref="txtTenDT"
                    v-model="pTxtTenDT"
                    maxlength="25"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w120">Địa chỉ <BssRequiredMarker class="ml-1"/></div>
            <div class="value">
              <div class="input-more-button">
                  <button class="btn" data-toggle="tooltip" title="Địa chỉ" v-b-modal.idDiaChiTTKH>
                      <span class="-ap icon-more_horiz"></span>
                  </button>
                  <input
                    type="text"
                    value=""
                    class="form-control font-weight-bold"
                    ref="txtDiaChiDT"
                    maxlength="37"
                    v-model="pTxtDiaChiDT"
                  />
              </div>
              <!-- <input
                type="text"
                value=""
                class="form-control"
                ref="txtDiaChiDT"
                v-model="pTxtDiaChiDT"
              /> -->
            </div>
          </div>
          <div class="row">
            <div class="col-sm-7 col-12">
              <div class="row">
                <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key w120">Ngày sinh <BssRequiredMarker class="ml-1"/></div>
                    <div class="value">
                      <ejs-datepicker v-model="pTxtNgaySN" :value="today" :max="today" :format="'dd/MM/y'"
                                        :allowEdit="true" ref="txtNgaySN" placeholder="__/__/____">
                      </ejs-datepicker>
                      <!-- <input
                        type="text"
                        ref="txtNgaySN"
                        v-model="pTxtNgaySN"
                        class="form-control"
                      /> -->
                    </div>
                  </div>
                </div>
                <div class="col-sm-7 col-12">
                  <div class="list-checks mart5">
                    <div class="item">
                      <div class="check-action">
                        <input
                          type="radio"
                          class="check"
                          name="gioiTinh"
                          :value="1"
                          v-model="pRdoGTNam"
                        />
                        <span class="name">Nam</span>
                      </div>
                    </div>
                    <div class="item">
                      <div class="check-action">
                        <input
                          type="radio"
                          class="check"
                          name="gioiTinh"
                          :value="0"
                          v-model="pRdoGTNam"
                        />
                        <span class="name">Nữ</span>
                      </div>
                    </div>
                    <div class="item">
                      <div class="check-action">
                        <input
                          type="radio"
                          class="check"
                          name="ketHoi"
                          :value="0"
                          v-model="pRdoChuaKH"
                        />
                        <span class="name">Chưa kết hôn</span>
                      </div>
                    </div>
                    <div class="item">
                      <div class="check-action">
                        <input
                          type="radio"
                          class="check"
                          name="ketHoi"
                          :value="1"
                          v-model="pRdoChuaKH"
                        />
                        <span class="name">Đã kết hôn</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key w120">Quốc tịch</div>
                    <div class="value">
                      <div class="">
                        <select2 v-model="pQuocTichSelected"
                            ref="cboQuocTich"
                            :options="pDsQuocTich"
                        />
                        <!-- <b-form-select
                          v-model="pQuocTichSelected"
                          :options="pDsQuocTich"
                          value-field="quocTichId"
                          text-field="tenQuocTich"
                          class="form-control"
                        >
                        </b-form-select> -->
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-7 col-12">
                  <div class="info-row">
                    <div class="key w100">Dân tộc</div>
                    <div class="value">
                      <div class="">
                        <select2 v-model="pDanTocSelected"
                            ref="cboDanToc"
                            :options="pDsDanToc"
                        />
                        <!-- <b-form-select
                          v-model="pDanTocSelected"
                          :options="pDsDanToc"
                          value-field="danTocId"
                          text-field="tenDanToc"
                          class="form-control"
                        >
                        </b-form-select> -->
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key w120">Điện thoại</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control"
                        v-model="pTxtDienThoai"
                        maxlength="25"
                      />
                    </div>
                  </div>
                </div>
                <div class="col-sm-7 col-12">
                  <div class="info-row">
                    <div class="key w100">Fax</div>
                    <div class="value" ref="divFax">
                      <input
                        type="text"
                        class="form-control"
                        v-model="pTxtFax"
                        maxlength="10"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key w120">Email</div>
                    <div class="value" >
                      <input
                        type="email"
                        class="form-control"
                        v-model="pTxtEmail"
                        maxlength="50"
                      />
                    </div>
                  </div>
                </div>
                <div class="col-sm-7 col-12">
                  <div class="info-row">
                    <div class="key w100">Ngành nghề</div>
                    <div class="value">
                      <div class="" >
                        <select2 v-model="pNganhNgheSelected"
                             ref="cboNganhNghe"
                            :options="pDsNganhNghe"
                        />
                        <!-- <b-form-select
                          v-model="pNganhNgheSelected"
                          :options="pDsNganhNghe"
                          value-field="NGANHNGHE_ID"
                          text-field="TENNGANHNGHE"
                          class="form-control"
                        >
                        </b-form-select> -->
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">Trình độ</div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control"
                    v-model="pTxtTrinhDo"
                    maxlength="25"
                  />
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">Bộ phận công tác</div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control"
                    v-model="pTxtBoPhanCT"
                    maxlength="50"
                  />
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">Ghi chú</div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control"
                    v-model="pTxtGhiChu"
                    maxlength="125"
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-5 col-12">
              <div
                class="box-form"
                style="border-radius: 0px; box-shadow: none"
              >
                <div class="legend-title" style="font-weight:600 !important">
                  <span class="title pull-left"> Sở thích </span>
                  <div class="pull-right">
                    <button
                      class="btn btn-second padt2 padb2"
                      v-b-modal.frmSoThichNV
                    >
                      <span class="-ap icon-plus3 normal"></span> Thêm sở thích
                    </button>
                  </div>
                  <div class="clearfix"></div>
                </div>
                <div
                  class="list-checks-box mart10"
                  style="overflow: auto; height: 170px"
                >
                  <div
                    class="item marb10"
                    v-for="soThich in this.pDsSoThich"
                    :key="soThich.soThichId"
                  >
                    <div class="check-action">
                      <input
                        type="checkbox"
                        class="check"
                        v-model="soThich.CHECKED"
                      />
                      <span class="name">{{ soThich.tenSoThich }}</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title" style="font-weight:600 !important">Danh sách đối tượng chăm sóc</div>
          <div class="table-content">
            <DataGrid
              v-bind:columns="gridColumns"
              :gridLines="'Both'"
              ref="gridDsCSKH"
              dataKeyField="STT"
              :dataSource="pDsThongTinCSKH"
              :showFilter="true"
              :allowPaging="true"
              :enablePagingServer="false"
              @selectedRowChanged="onGridDsCSKH_RowSelected"
            >
            </DataGrid>
          </div>
        </div>
      </div>
    </div>
    <YesNoModal :content="xoaContent" idModal='popup-confirmXoa' @onOk="onXoaConfirm" />
    <ModalChonDiaChi modalId="idDiaChiTTKH" @xacnhan="getDiaChiKH"/>
  <!-- </b-modal> -->
  </ejs-dialog>
</template>
<style scoped src="../../QLTN/TaoDanhSachNangGoi/TaoDanhSachNangGoi.scss"> </style>
<style>
a.disabled {
  color: lightgrey !important;
  pointer-events: none;
  cursor: default;
  text-decoration: none;
}

.btn-custom.btn-second {
  background-color: #fff !important;
  border-color: #0176ff !important;
  color: #0176ff !important;
  font-weight: 600 !important;
}
.btn-second.btn {
  padding: 5px 10px !important;
  border-radius: 4px !important;
}
</style>
<script>
import moment from "moment";
import DanhMucApi from "../api/DanhMucApi"
import frmTraCuuTTCSKH from "./frmTraCuuTTCSKH";
import frmSoThichNV from "./frmSoThichNV";
import frmDoiTuongCSKH from "./frmDoiTuongCSKH";
import YesNoModal from "../utils/YesNoModal"
import BssRequiredMarker from '@/components/BssRequiredMarker'
import ModalChonDiaChi from "../../CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonDiaChi/Modal";

export default {
  name: "frmTTChamSocKH",
  components: {
    BssRequiredMarker,
    frmTraCuuTTCSKH,
    frmSoThichNV,
    frmDoiTuongCSKH,
    YesNoModal,
    ModalChonDiaChi
  },
  props: {
    MaKH: {
      type: String,
      default: "",
    },
    TenKH: {
      type: String,
      default: "",
    },
    DiaChiKH: {
      type: String,
      default: "",
    },
    MaTB: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      pTxtMaKH: "",
      pTxtTenKH: "",
      pTxtMaTB: "",
      pTxtDiaChiKH: "",
      pTxtTenDT: "",
      pTxtDiaChiDT: "",
      pTxtNgaySN: "",
      today: new Date(),
      pTxtDienThoai: "",
      pTxtFax: "",
      pTxtEmail: "",
      pTxtTrinhDo: "",
      pTxtBoPhanCT: "",
      pTxtGhiChu: "",
      pTxtThongtinID: "",

      pRdoGTNam: 1,
      pRdoChuaKH: 0,

      pQuanDtId: 0,
      pPhuongDtId: 0,
      pPhoDtId: 0,
      pSoNhaDt: "",

      pLoaiDoiTuongSelected: 0,
      pQuocTichSelected: 0,
      pDanTocSelected: 0,
      pNganhNgheSelected: 0,
      pSoThichSelected: 0,

      pDsDoiTuongChamSoc: [],
      pDsQuocTich: [],
      pDsDanToc: [],
      pDsNganhNghe: [],
      pDsSoThich: [],

      pDsThongTinCSKH: [],

      pBtnNhapMoiEnabled: false,
      pBtnGhiLaiEnabled: false,
      pBtnXoaEnabled: false,
      pBtnHuyBoEnabled: false,

      isShowThongTinID: false,

      thongTinId: 0,
      gridColumns: [
        {
          fieldName: "TENDOITUONGCS",
          headerText: "Loại đối tượng",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "TENDOITUONG",
          headerText: "Tên đối tượng",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "DIACHI",
          headerText: "Địa chỉ",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "TRINHDO",
          headerText: "Trình độ",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "BOPHANCONGTAC",
          headerText: "Bộ phận",
          allowFiltering: true,
          allowSorting: true,
        },
      ],

      pCurrentSelectedRow: null,
      xoaContent: '',

      fieldsName: {
          maKh: 'Mã khách hàng',
          doiTuongCsId: 'Loại đối tượng chăm sóc',
          tenDoiTuong: 'Tên đối tượng',
          diaChi: 'Địa chỉ',
          dienThoai: 'Điện thoại',
          email: 'Email',
          fax: 'Fax',
          ngaySinh: 'Ngày sinh',
          gioiTinhId: 'Giới tính',
          quocTichId: 'Quốc tịch',
          danTocId: 'Dân tộc',
          nganhNgheId: 'Ngành nghề',
          trinhDo: 'Trình độ',
          boPhanCongTac: 'Bộ phận CT',
          ketHon: 'Tình trạng hôn nhân',
          quanId: 'Quận',
          phuongId: 'Phường',
          phoId: 'Phố',
          soNha: 'Số nhà',
          ghiChu: 'Ghi chú',
          dsSoThichId: 'Danh sách sở thích',
      }
    };
  },
  computed: {},
  methods: {
    async onShow() {
      this.clearData()
      try {
        this.$root.showLoading(true);
        await this.getDsDoiTuongChamSoc();
        await this.getDsQuocTich();
        await this.getDsNganhNghe();
        await this.getDsSoThich();
        if (this.pDsDoiTuongChamSoc.length > 0)
          this.pLoaiDoiTuongSelected = this.pDsDoiTuongChamSoc[0].id;
        if (this.pDsNganhNghe.length > 0)
          this.pNganhNgheSelected = this.pDsNganhNghe[0].id;
        if (this.pDsQuocTich.length > 0)
          this.pQuocTichSelected = this.pDsQuocTich[0].id;

        this.setButton(-1);
        if (this.pTxtMaKH.trim().length == 0) {
          this.focusInput("txtMaKH");
        } else {
          this.focusInput("cboDoiTuong");
        }

        await this.onMaTBKeyPress();
      } catch (e) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    async onMaTBKeyPress() {
      try {
        if (this.pTxtMaTB.trim()) {
          let tracuukhachhang = await this.$root.context.get('/web-cskh/api/tra-cuu/khach-hang/' + this.pTxtMaTB.trim())
          if (tracuukhachhang.error_code === "BSS-00000000") {
            let ds = tracuukhachhang.data || []
            if (ds.length > 0) {
              if (ds[0].KY_HD != 1) {
                this.$root.toastError("Khách hàng có mã " + this.pTxtMaTB + " chưa ký hợp đồng gốc. Đề nghị bạn ký hợp đồng gốc trước khi lập phụ lục !")
                this.focusInput('txtMaTB')
              }
              else {
                this.pTxtMaKH = ds[0].MA_KH
                this.pTxtTenKH = ds[0].TEN_KH
                this.pTxtDiaChiKH = ds[0].DIACHI_KH
                await this.layThongTinCSKHTheoMaKH(this.pTxtMaKH.trim())
              }
            }
            else {
              this.clearData()
              this.$root.toastError("Không tìm thấy dữ liệu!")
            }
          }
        }
      }
      catch (e) {
        console.log(e)
      }
      finally {
        this.$root.showLoading(false)
      }
    },
    async maTBKeyPress(event) {
      if (event.keyCode == 13) {
        await this.onMaTBKeyPress()
      }
    },
    async getDsDoiTuongChamSoc() {
      try {
        this.pDsDoiTuongChamSoc = []
        let response = await this.$root.context.get(
          "/web-cskh/api/danh-muc/loai-doi-tuong"
        );
        if (response.error_code === "BSS-00000000") {
          // this.pDsDoiTuongChamSoc = response.data || []
          // this.pDsDoiTuongChamSoc.sort((a, b) =>
          //   a.doiTuongCsId > b.doiTuongCsId ? 1 : -1
          // );
          let ds = response.data || []
          ds.sort((a, b) => a.doiTuongCsId > b.doiTuongCsId ? 1 : -1 );
          ds.forEach(e => this.pDsDoiTuongChamSoc.push({ id: e.doiTuongCsId, text: e.tenDoiTuongCs }))
        }
      } catch (e) {
        this.pDsDoiTuongChamSoc = [];
      }
    },
    async getDsQuocTich() {
      try {
        this.pDsQuocTich = [];
        let response = await this.$root.context.get(
          "/web-cskh/api/danh-muc/quoc-tich"
        );
        if (response.error_code === "BSS-00000000") {
          // this.pDsQuocTich = response.data || [];
          // this.pDsQuocTich.sort((a, b) =>
          //   a.quocTichId > b.quocTichId ? 1 : -1
          // );
          let ds = response.data || []
          ds.sort((a, b) => a.quocTichId > b.quocTichId ? 1 : -1 );
          ds.forEach(e => this.pDsQuocTich.push({ id: e.quocTichId, text: e.tenQuocTich }))
        }
      } catch (e) {
        this.pDsQuocTich = [];
      }
    },
    async getDsDanToc(id) {
      try {
        this.pDsDanToc = [];
        let response = await this.$root.context.get(
          "/web-cskh/api/danh-muc/dan-toc/" + id
        );
        if (response.error_code === "BSS-00000000") {
          // this.pDsDanToc = response.data || [];
          // this.pDsDanToc.sort((a, b) => (a.danTocId > b.danTocId ? 1 : -1));
          let ds = response.data || []
          ds.sort((a, b) => a.danTocId > b.danTocId ? 1 : -1 );
          ds.forEach(e => this.pDsDanToc.push({ id: e.danTocId, text: e.tenDanToc }))
        }
      } catch (e) {
        this.pDsDanToc = [];
      }
    },
    async getDsNganhNghe() {
      try {
        this.pDsNganhNghe = [];
        let response = await this.$root.context.get(
          "/web-cskh/api/danh-muc/nganh-nghe"
        );
        if (response.error_code === "BSS-00000000") {
          // this.pDsNganhNghe = response.data || [];
          // this.pDsNganhNghe.forEach((e) => (e.CHECKED = false));
          // this.pDsNganhNghe.sort((a, b) =>
          //   a.NGANHNGHE_ID > b.NGANHNGHE_ID ? 1 : -1
          // );
          let ds = response.data || []
          ds.sort((a, b) => a.danTocId > b.danTocId ? 1 : -1 );
          ds.forEach(e => this.pDsNganhNghe.push({ id: e.NGANHNGHE_ID, text: e.TENNGANHNGHE }))
        }
      } catch (e) {
        this.pDsNganhNghe = [];
      }
    },
    async getDsSoThich() {
      try {
        let response = await this.$root.context.get(
          "/web-cskh/api/danh-muc/so-thich"
        );
        if (response.error_code === "BSS-00000000") {
          this.pDsSoThich = response.data || [];
          this.pDsSoThich.sort((a, b) => (a.soThichId > b.soThichId ? 1 : -1));
        }
      } catch (e) {
        this.pDsSoThich = [];
      }
    },
    clearData() {
      this.pDsDoiTuongChamSoc = [];
      this.pDsQuocTich = [];
      this.pDsDanToc = [];
      this.pDsNganhNghe = [];
      this.pDsSoThich = [];
      this.pDsThongTinCSKH = []

      this.pTxtMaKH = ""
      this.pTxtTenKH = ""
      this.pTxtMaTB = ""
      this.pTxtDiaChiKH = ""
      this.pTxtTenDT = ""
      this.pTxtDiaChiDT = ""
      this.pTxtNgaySN = ""
      this.pTxtDienThoai = ""
      this.pTxtFax = ""
      this.pTxtEmail = ""
      this.pTxtTrinhDo = ""
      this.pTxtBoPhanCT = ""
      this.pTxtGhiChu = ""
      this.pTxtThongtinID = ""
      this.pQuanDtId = 0;
      this.pPhuongDtId = 0;
      this.pPhoDtId = 0;
      this.pSoNhaDt = "";

      this.pRdoGTNam = 1
      this.pRdoChuaKH = 0
      this.pQuocTichSelected = 0
      this.pDanTocSelected = 0
      this.pNganhNgheSelected = 0
      this.pSoThichSelected = 0
      this.pLoaiDoiTuongSelected = 0

      this.pDsSoThich.forEach((e) => (e.CHECKED = false));

      if (this.pDsDoiTuongChamSoc.length > 0)
          this.pLoaiDoiTuongSelected = this.pDsDoiTuongChamSoc[0].id;
      if (this.pDsNganhNghe.length > 0)
        this.pNganhNgheSelected = this.pDsNganhNghe[0].id;
      if (this.pDsQuocTich.length > 0)
        this.pQuocTichSelected = this.pDsQuocTich[0].id;
    },
    clear() {
      this.pTxtTenDT = "";
      this.pTxtDiaChiDT = "";
      this.pTxtNgaySN = "";
      this.pTxtDienThoai = "";
      this.pTxtFax = "";
      this.pTxtEmail = "";
      this.pTxtTrinhDo = "";
      this.pTxtBoPhanCT = "";
      this.pTxtGhiChu = "";
      this.pTxtThongtinID = "";

      this.pQuanDtId = 0;
      this.pPhuongDtId = 0;
      this.pPhoDtId = 0;
      this.pSoNhaDt = "";

      this.pRdoGTNam = 1
      this.pRdoChuaKH = 0

      // this.pDsThongTinCSKH = []

      this.pDsSoThich.forEach((e) => (e.CHECKED = false));

      if (this.pDsDoiTuongChamSoc.length > 0)
          this.pLoaiDoiTuongSelected = this.pDsDoiTuongChamSoc[0].id;
      if (this.pDsNganhNghe.length > 0)
        this.pNganhNgheSelected = this.pDsNganhNghe[0].id;
      if (this.pDsQuocTich.length > 0)
        this.pQuocTichSelected = this.pDsQuocTich[0].id;
    },
    focusInput(id) {
      this.$refs[id].select();
    },
    setButton(kieu) {
      this.pBtnNhapMoiEnabled = false;
      this.pBtnGhiLaiEnabled = false;
      this.pBtnXoaEnabled = false;
      this.pBtnHuyBoEnabled = false;

      if (kieu == -1) {
        //Bat dau
        this.focusInput("txtMaTB");
        this.pBtnGhiLaiEnabled = false;
        this.pBtnHuyBoEnabled = true;
      }

      if (kieu == 0) {
        //Bat dau
        this.focusInput("txtMaTB");
        this.pBtnNhapMoiEnabled = true;
        this.clear();
      }

      if (kieu == 1) {
        //Them moi
        this.focusInput("txtTenDT");
        this.pBtnGhiLaiEnabled = true;
        this.pBtnHuyBoEnabled = true;
        this.clear();
      }

      if (kieu == 2) {
        //Huy
        this.focusInput("txtMaTB");
        this.pBtnNhapMoiEnabled = true;
        this.pBtnXoaEnabled = true;
        this.clear();
      }

      if (kieu == 3) {
        //Cat nhap
        this.pBtnNhapMoiEnabled = true;
        this.pBtnGhiLaiEnabled = true;
        this.pBtnThemEnabled = false;
        this.pBtnXoaEnabled = true;
      }
      if (kieu == 4) {
        //Them
        //this.focusInput("cboDoiTuong");
        this.pBtnGhiLaiEnabled = false;
        this.pBtnThemEnabled = true;
        this.pBtnXoaEnabled = true;
        this.pBtnNhapMoiEnabled = false;
        this.clear();
      }
    },
    kiemTraDuLieu() {
      if ((this.pTxtMaKH.trim() + '').length == 0) {
        this.$root.toastError("Mã khách hàng không được trống !");
        this.focusInput("txtMaKH");
        return false;
      }
      if ((this.pTxtTenDT.trim() + '').length == 0) {
        this.$root.toastError("Hãy nhập tên đối đượng chăm sóc !");
        this.focusInput("txtTenDT");
        return false;
      }
      if ((this.pTxtDiaChiDT + '').trim().length == 0) {
        this.$root.toastError("Hãy nhập địa chỉ đối đượng chăm sóc !");
        this.focusInput("txtDiaChiDT");
        return false;
      }
      if (!this.pTxtNgaySN) {
        this.$root.toastError("Hãy nhập ngày sinh đối tượng chăm sóc (định dạng 'DD/MM/YYYY') !");
        this.$refs.txtNgaySN.focusIn();
        return false;
      }

      // else {
      //   let dateCheck = moment(this.pTxtNgaySN, 'DD/MM/YYYY', true)
      //   if (!dateCheck.isValid()) {
      //     this.$root.toastError("Ngày sinh không đúng định dạng 'DD/MM/YYYY' !");
      //     this.$refs.txtNgaySN.focusIn();
      //     return false;
      //   }
      // }
      return true;
    },
    onNhapMoiClicked() {
      this.setButton(4)
    },
    async onCapNhapClicked() {
      try {
        this.$root.showLoading(true)
        if (!this.kiemTraDuLieu()) return;
        if ((this.pTxtThongtinID + '').trim().length > 0) {
          let data = this.taoDuLieu(false)
          console.log(data)
          let response = await this.$root.context.post('/web-cskh/api/cham-soc-khach-hang', data)
          if (response.error_code === "BSS-00000000") {
            await this.layThongTinCSKHTheoMaKH(this.pTxtMaKH)
            this.$root.toastSuccess("Cập nhật dữ liệu thành công !")
          }
        }
      }
      catch (e)
      {
        console.log(e)
        if (e && e.response && e.response.data && e.response.data.data) {
          if (typeof(e.response.data.data) == "string")
          {
            this.$root.toastError("Có lỗi: " + e.response.data.data)
            return
          }

          let errors = e.response.data.data || []
          if (errors.length > 0) {
            errors.forEach(error => {
              this.$root.toastError(`${(this.fieldsName[error.fieldName]) ? this.fieldsName[error.fieldName] : error.fieldName}: ${error.message}`)
            })
            return
          }
        }

        this.$root.toastError("Cập nhật dữ liệu thất bại !")
      }
      finally {
        this.$root.showLoading(false)
      }
    },
    async onThemCSClicked() {
      try {
        this.$root.showLoading(true)
        if (!this.kiemTraDuLieu()) return;
        if ((this.pTxtThongtinID + '').trim().length == 0) {
          let data = this.taoDuLieu(true)
          let response = await this.$root.context.post('/web-cskh/api/cham-soc-khach-hang', data)
          if (response.error_code === "BSS-00000000") {
            await this.layThongTinCSKHTheoMaKH(this.pTxtMaKH)
            .catch(e => {
              console.log(e)
            })
            this.$root.toastSuccess("Cập nhật dữ liệu thành công !")
          }
        }
      }
      catch (e)
      {
        console.log(e)
        if (e && e.response && e.response.data && e.response.data.data) {
          if (typeof(e.response.data.data) == "string")
          {
            this.$root.toastError("Có lỗi: " + e.response.data.data)
            return
          }

          let errors = e.response.data.data || []
          if (errors.length > 0) {
            errors.forEach(error => {
              this.$root.toastError(`${(this.fieldsName[error.fieldName]) ? this.fieldsName[error.fieldName] : error.fieldName}: ${error.message}`)
            })
            return
          }
        }

        this.$root.toastError("Cập nhật dữ liệu thất bại !")
      }
      finally {
        this.$root.showLoading(false)
      }
    },
    onXoaCSClicked() {
      if (this.pCurrentSelectedRow) {
        let thongTinId = this.pCurrentSelectedRow.THONGTIN_ID
        if (thongTinId > 0) {
          this.xoaContent = "Bạn có chắc chắn xóa đối tượng " + this.pCurrentSelectedRow.TENDOITUONG + " không?"
          this.$bvModal.show('popup-confirmXoa')
        }
      }
      else {
        this.$root.toastError("Hãy chọn đối tượng chăm sóc khách hàng trên lưới dữ liệu để xóa !")
      }
    },
    async onXoaConfirm() {
      try {
        this.$root.showLoading(true)
        if (this.pCurrentSelectedRow) {
          let response = await DanhMucApi.xoaCSKH(this.axios, this.pCurrentSelectedRow.THONGTIN_ID)
          if (response.data.error_code === "BSS-00000000") {
            this.setButton(0)
            this.$root.toastSuccess("Cập nhật dữ liệu thành công !")
            await this.layThongTinCSKHTheoMaKH(this.pTxtMaKH)

          }
        }

      }
      catch (e) {

      }
      finally {
        this.$root.showLoading(false)
      }

    },
    taoDuLieu(isThemMoi) {
      let data = {
        thongTinId: isThemMoi ? 0 : this.thongTinId,
        maKh: this.pTxtMaKH,
        doiTuongCsId: this.pLoaiDoiTuongSelected,
        tenDoiTuong: this.pTxtTenDT ? (this.pTxtTenDT + '').trim() : '',
        diaChi: this.pTxtDiaChiDT ? (this.pTxtDiaChiDT + '').trim() : '',
        dienThoai: this.pTxtDienThoai ? (this.pTxtDienThoai + '').trim() : '',
        email: this.pTxtEmail ? (this.pTxtEmail + '').trim() : '',
        fax: this.pTxtFax ? (this.pTxtFax + '').trim() : '',
        ngaySinh: moment(this.pTxtNgaySN).format("DD/MM/YYYY"),
        gioiTinhId: this.pRdoGTNam,
        quocTichId: this.pQuocTichSelected,
        danTocId: this.pDanTocSelected,
        nganhNgheId: this.pNganhNgheSelected,
        trinhDo: this.pTxtTrinhDo ? (this.pTxtTrinhDo + '').trim() : '',
        boPhanCongTac: this.pTxtBoPhanCT ? (this.pTxtBoPhanCT + '').trim() : '',
        ketHon: this.pRdoChuaKH,
        quanId: this.pQuanDtId,
        phuongId: this.pPhuongDtId,
        phoId: this.pPhoDtId,
        soNha: this.pSoNhaDt,
        donViId: this.$auth.getDonViID(),
        ghiChu: this.pTxtGhiChu ? (this.pTxtGhiChu + '').trim() : '',
        dsSoThichId: this.pDsSoThich.filter(x => x.CHECKED).map(x => x.soThichId).join(),
        mayCn: this.$auth.getUserName(),
        nguoiCn: this.$auth.getUserName(),
      };
      return data
    },
    async layThongTinCSKHTheoMaKH(maKH) {
      this.pDsThongTinCSKH = []
      try {
        let response = await this.$root.context.get('/web-cskh/api/cham-soc-khach-hang/lay-ds?pageNo=1&pageSize=10000', {maKhachHang: maKH})
        if (response.error_code === "BSS-00000000") {
          this.pDsThongTinCSKH = response.data.data || []
          if (this.pDsThongTinCSKH.length > 0) {
            this.$refs.gridDsCSKH.setCurrentSelectedRow(0)
            this.setButton(3)
          }
          else {
            this.setButton(4)
          }
        }
      }
      catch (e) {

      }
    },
    async onGridDsCSKH_RowSelected(dataItem) {
      this.pCurrentSelectedRow = dataItem
      if (dataItem) {
        this.thongTinId = dataItem.THONGTIN_ID
        this.pTxtMaKH = dataItem.MA_KH
        this.pTxtThongtinID = dataItem.THONGTIN_ID
        this.pLoaiDoiTuongSelected = dataItem.DOITUONGCS_ID
        this.pTxtTenDT = dataItem.TENDOITUONG
        this.pTxtDiaChiDT = dataItem.DIACHI
        this.pTxtDienThoai = dataItem.DIENTHOAI
        this.pTxtEmail = dataItem.EMAIL
        this.pTxtFax = dataItem.FAX
        this.pTxtNgaySN = moment(dataItem.NGAYSINH, "DD/MM/YYYY", true).toDate()
        this.pQuocTichSelected = dataItem.QUOCTICH_ID
        this.pDanTocSelected = dataItem.DANTOC_ID
        this.pNganhNgheSelected = dataItem.NGANHNGHE_ID
        this.pTxtTrinhDo = dataItem.TRINHDO
        this.pTxtBoPhanCT = dataItem.BOPHANCONGTAC
        this.pQuanDtId = dataItem.QUAN_ID
        this.pPhuongDtId = dataItem.PHUONG_ID
        this.pPhoDtId = dataItem.PHO_ID
        this.pSoNhaDt = dataItem.SO_NHA
        this.pTxtGhiChu = dataItem.GHICHU
        this.pRdoGTNam = dataItem.GIOITINH_ID
        this.pRdoChuaKH = dataItem.KETHON
        await this.hienThiSoThich(dataItem.THONGTIN_ID)
      }
    },
    async hienThiSoThich(thongTinId) {
      try {
        let response = await this.$root.context.get('/web-cskh/api/cham-soc-khach-hang/so-thich', {thongTinId: thongTinId})
        if (response.error_code === "BSS-00000000") {
          let ds = response.data || []
          this.pDsSoThich.forEach(e => {
            if (ds.find(x => x.SOTHICH_ID === e.soThichId)) {
              e.CHECKED = true
            }
            else {
              e.CHECKED = false
            }
          })
          this.pDsSoThich = [...this.pDsSoThich]
        }
      }
      catch (e) {
        console.log(e)
      }
    },
    async dialogOpen() {
      await this.onShow();
    },
    openDialog() {
        this.$refs.frmTTChamSocKH.show();
    },
    closeDialog() {
        this.$refs.frmTTChamSocKH.hide();
    },
    onTraCuuClicked() {
      this.$refs.frmTraCuuTTCSKH.openDialog()
    },
    getDiaChiKH(data) {
      if(data){
        this.pTxtDiaChiDT = data.diachimoi || "";
        this.pQuanDtId = data.quan_id || 0;
        this.pPhuongDtId = data.phuong_id || 0;
        this.pPhoDtId = data.pho_id || 0;
        this.pSoNhaDt = data.so_nha || ""
      }
    },
  },
  watch: {
    pQuocTichSelected: {
      handler: async function (value) {
        await this.getDsDanToc(value);
        if (this.pDsDanToc.length > 0) {
          this.pDanTocSelected = this.pDsDanToc[0].id;
        }
      },
    },
    pRdoGTNam: {
      handler: function (value) {
        console.log('pRdoGTNam', value)
      }
    }
  },
};
</script>
