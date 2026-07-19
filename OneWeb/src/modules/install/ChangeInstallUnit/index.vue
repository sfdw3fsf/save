<template>
  <div class="main-wrapper">
    <breadcrumb></breadcrumb>
    <!-- <div class="breadcrumb-top">
      <div class="main-title">Cập nhật đơn vị thi công</div>
      <ul class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a>
        </li>
        <li class="breadcrumb-item"><a href="#">Cập nhật KQTC</a></li>
        <li class="breadcrumb-item active">Thay đổi đơn vị thi công</li>
      </ul>
    </div> -->
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="tsbtnTraCuu_Click"> <span class="icon one-file-search"></span> Tra cứu </a>
        </li>

        <li>
          <a href="#" @click.prevent="tsbtnGhiLai_Click"> <span class="icon one-save"></span> Ghi lại</a>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <div class="box-form">
        <div class="row">
          <div class="col-sm-5 col-12" style="padding-left: 15px">
            <div class="legend-title">Thông tin giao dịch</div>
            <div class="row">
              <div class="col-sm-5 col-12">
                <div class="info-row">
                  <div class="key w90">Mã GD</div>
                  <div class="value">
                    <div class="input-more-button">
                      <SearchContractModal ref="popupSearchContract" @accept="acceptSearchContract" />
                      <button id="btnSearchMaGD" class="btn" @click="ShowMaGiaoDich">
                        <span class="-ap icon-more_horiz"></span>
                      </button>
                      <input type="text" v-on:keyup.enter="TraCuu" :class="{ 'is-invalid': $v.fInput.MaGD.$error }" class="form-control highlight" ref="MaGD" v-model="fInput.MaGD" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-7 col-12">
                <div class="info-row">
                  <div class="key w90">Số máy/Acc</div>
                  <div class="value">
                    <input type="text" class="form-control highlight" v-model="fInput.MaTB" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-5 col-12">
                <div class="info-row">
                  <div class="key w90">Dịch vụ</div>
                  <div class="value">
                    <Select2 :disabled="true" name="fDichVuVTId" id="fDichVuVTId" v-model="DichVuVTId" :options="DMDichVuVT" />
                  </div>
                </div>
              </div>
              <div class="col-sm-7 col-12">
                <div class="info-row">
                  <div class="key w90">Loại hình</div>
                  <div class="value">
                    <Select2 :disabled="true" v-model="LoaiHinhId" :options="DmLoaiHinh" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-12 col-12">
                <div class="info-row">
                  <div class="key w90">Loại HĐ</div>
                  <div class="value">
                    <input type="text" :disabled="true" class="form-control" v-model="fInput.LoaiHD" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-12 col-12">
                <div class="info-row">
                  <div class="key w90">Tên thuê bao</div>
                  <div class="value">
                    <input type="text" :disabled="true" class="form-control" v-model="fInput.TenThueBao" />
                  </div>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="col-sm-12 col-12">
                <div class="info-row">
                  <div class="key w90">Trạng thái</div>
                  <div class="value">
                    <input type="text" :disabled="true" class="form-control" v-model="fInput.TrangThai" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-12 col-12">
                <div class="info-row">
                  <div class="key w90">Địa chỉ LĐ</div>
                  <div class="value">
                    <input type="text" :disabled="true" class="form-control" v-model="fInput.DiaChiLD" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-12 col-12">
                <div class="info-row">
                  <div class="key w90">Tổ</div>
                  <div class="value">
                    <input type="text" :disabled="true" class="form-control" v-model="fInput.To" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-12 col-12">
                <div class="info-row">
                  <div class="key w90">Đơn vị LĐ</div>
                  <div class="value">
                    <input type="text" :disabled="true" class="form-control" v-model="fInput.DonViLD" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-12 col-12">
                <div class="info-row">
                  <div class="key w90">Ghi chú</div>
                  <div class="value">
                    <textarea :disabled="true" name="" id="" v-model="fInput.GhiChu" cols="30" rows="10" class="form-control" style="height: 130px; resize: none"></textarea>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="col-sm-7 col-12" style="padding-left: 15px">
            <div class="legend-title">
              <div class="pull-left">Thông tin tổ thi công mới</div>
              <div class="pull-right red"><span class="one-alert text-warning f16"></span> Click chọn ô check box nếu muốn thay đổi thông tin</div>
              <div class="clearfix"></div>
            </div>
            <div class="row">
              <div class="col-sm-12 col-12">
                <div class="info-row">
                  <div class="key w160">Kiểu đơn vị</div>
                  <div class="value">
                    <Select2 :disabled="!(DichVuVTId == 9)" v-model="KieuDonViId" :options="DmKieuDonVi" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-12 col-12">
                <div class="info-row">
                  <div class="key w160">
                    <div class="check-action">
                      <input type="checkbox" class="check" v-model="fCheck.DiaChiLD" />
                      <span class="name">Địa chỉ LĐ</span>
                    </div>
                  </div>
                  <div class="value">
                    <div class="input-more-button">
                      <!-- <button class="btn" @click="ShowDiaChiLD" :disabled="!fCheck.DiaChiLD">
                        <span class="-ap icon-more_horiz"></span>
                      </button> -->
                      <button class="btn" v-b-modal.popupDiaChiNDD>
                        <span class="-ap icon-more_horiz"></span>
                      </button>
                      <ModalChonDiaChi modalId="popupDiaChiNDD" :data="diachiKH" @xacnhan="LayDiaChi" />
                      <input :disabled="!fCheck.DiaChiLD" type="text" ref="diaChiLDMoi" class="form-control" v-model="fInput.DiaChiLDMoi" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-12 col-12">
                <div class="info-row">
                  <div class="key w160">
                    <div class="check-action red fw6">
                      <input type="checkbox" class="check" v-model="fCheckToThiCong" />
                      <span class="name">Thay đổi tổ thi công</span>
                    </div>
                  </div>
                  <div class="value">
                    <button class="btn btn-main " v-bind:class="{ disabled: !fCheckToThiCong, nocorner: fCheckToThiCong }" @click.prevent="getDanhSachTo">Lấy danh sách tổ</button>
                  </div>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w160">
                    <div class="check-action">
                      <input :disabled="!fCheckToThiCong" type="checkbox" class="check" v-model="fCheck.QuanHuyen" />
                      <span class="name">Quận</span>
                    </div>
                  </div>
                  <div class="value">
                    <Select2 :disabled="!fCheck.QuanHuyen" v-model="QuanId" :options="DmQuanHuyen" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w90">
                    <div class="check-action">
                      <input :disabled="!(fCheckToThiCong && fCheck.QuanHuyen)" type="checkbox" class="check" v-model="fCheck.PhuongXa" />
                      <span class="name">Phường</span>
                    </div>
                  </div>
                  <div class="value">
                    <Select2 :disabled="!fCheck.PhuongXa" v-model="PhuongXaId" :options="DmPhuongXa" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w160">
                    <div class="check-action">
                      <input :disabled="!(fCheckToThiCong && fCheck.PhuongXa)" type="checkbox" class="check" v-model="fCheck.Pho" />
                      <span class="name">Phố</span>
                    </div>
                  </div>
                  <div class="value">
                    <Select2 :disabled="!fCheck.Pho" v-model="PhoId" :options="DmPho" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w90">
                    <div class="check-action">
                      <input :disabled="!(fCheckToThiCong && fCheck.Pho)" type="checkbox" class="check" v-model="fCheck.App" />
                      <span class="name">Ấp</span>
                    </div>
                  </div>
                  <div class="value">
                    <Select2 :disabled="!fCheck.App" v-model="ApId" :options="DmAp" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w160">
                    <div class="check-action">
                      <input :disabled="!(fCheckToThiCong && fCheck.App)" type="checkbox" class="check" v-model="fCheck.Khu" />
                      <span class="name">Khu</span>
                    </div>
                  </div>
                  <div class="value">
                    <Select2 :disabled="!fCheck.Khu" v-model="KhuId" :options="DmKhu" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w90">
                    <div class="check-action">
                      <input :disabled="!(fCheckToThiCong && fCheck.Khu)" type="checkbox" class="check" v-model="fCheck.DacDiem" />
                      <span class="name">Đặc điểm</span>
                    </div>
                  </div>
                  <div class="value">
                    <Select2 :disabled="!fCheck.DacDiem" v-model="DacDiemId" :options="DmDacDiem" />
                  </div>
                </div>
              </div>
            </div>

            <DataGrid v-bind:columns="DsTo.headers2" v-bind:dataSource="DsTo.data" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :enabledSelectFirstRow="false" @rowClicked="ToThiCongChanged"> </DataGrid>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">Danh sách thuê bao</div>
        <DataGrid v-bind:columns="DsThueBao.headers2" v-bind:dataSource="DsThueBao.data" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :enabledSelectFirstRow="true" @rowClicked="ThueBaoChanged"> </DataGrid>
      </div>
      <b-modal id="my-modal-magd" class="modal-dialog" size="lg" hide-footer :no-close-on-backdrop="true">
        <div slot="modal-title" class="popup-header">
          <div class="title">Thông tin tra cứu</div>
        </div>
        <div class="popup-body" style="height: 550px">
          <searchAccount :isPopup="true" @form-close="selectedAccount"></searchAccount>
        </div>
      </b-modal>
      <b-modal id="my-modal-diachi-lapdat" class="modal-dialog" size="lg" hide-footer :no-close-on-backdrop="true">
        <div slot="modal-title" class="popup-header">
          <div class="title">Thông tin tra cứu</div>
        </div>
        <div class="popup-body"></div>
      </b-modal>

      <ejs-dialog :enableResize="true" :position="position" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogObjDiaChiLD" :header="'Địa chỉ'" showCloseIcon="true" width="80%" target=".main-wrapper">
        <!-- <div class="popup-body"><ModalChonDiaChi modalId="popupDiaChiNDD" :data="diachiKH" @xacnhan="LayDiaChi" /></div> -->
      </ejs-dialog>
      <ejs-dialog :enableResize="true" :position="position" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogObjMaGD" :header="'Tra cứu hợp đồng phụ lục'" showCloseIcon="true" width="80%" target="#app .main-wrapper">
        <p>Chờ tham chiếu: https://cntt.vnpt.vn/browse/BSS-5637</p>
      </ejs-dialog>
      <ejs-dialog :enableResize="true" :position="position" :allowDragging="true" :visible="modalShowDshdtb" ref="dialogObjDonViNhanPhieu" :header="'Danh sách các biến động'" showCloseIcon="true" width="80%" :target="target">
        <div>
          <div class="list-actions-top">
            <ul class="list">
              <li>
                <a href="#" @click.prevent="SelectHDFromPopUpClose"> <span class="icon one-reload1"></span>Chấp nhận </a>
              </li>
            </ul>
          </div>
          <div class="popup-body">
            <DataGrid
              v-bind:columns="DsHDTBSearchResult.headers2"
              v-bind:dataSource="DsHDTBSearchResult.data"
              :showColumnCheckbox="false"
              :enable-paging-server="false"
              :allowPaging="true"
              :showFilter="true"
              :enabledSelectFirstRow="false"
              @rowClicked="SelectHDFromPopUp"
              @rowDoubleClicked="SelectHDFromPopUpDb"
            >
            </DataGrid>
          </div>
        </div>
      </ejs-dialog>
    </div>
  </div>
</template>

<script>
import gridView from '@/modules/complaint/ResovleComplaintAfterSales/components/gridview.vue'
import treegridView from '@/modules/complaint/ResovleComplaintAfterSales/components/treegridview.vue'
//import ModalChonDiaChi from '@/modules/CABMAN/BanDoTuyenCot/ChonDiaChi/Modal'
import ModalChonDiaChi from '@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi'
//import tracuuHopDong from '@/modules/search/subscriber/SearchContract'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import breadcrumb from '@/components/breadcrumb'
import { required, minLength } from 'vuelidate/lib/validators'
import { TrangThaiHD } from '@/const/enums/index.js'
import 'flatpickr/dist/plugins/monthSelect/style.css'
import bssFlatPicker from '@/components/BssDatePicker'
import XLSX from 'xlsx'
import moment from 'moment'
import api from './Api'
export default {
  components: {
    breadcrumb,
    gridView,
    treegridView,
    bssFlatPicker,
    XLSX,
    moment,
    required,
    minLength,
    ModalChonDiaChi,
    SearchContractModal
  },
  name: 'ChangeInstallUnit',
  props: ['p_tag'],
  created() {
    if (this.p_tag != null && this.p_tag != '') {
      this.Tag = this.p_tag
    }
    if (this.$route.query.tag != null && this.$route.query.tag != '' && this.$route.query.tag.length > 0) {
      console.log('Tag được gán từ query:', this.$route.query.tag)
      this.Tag = this.$route.query.tag
    }
    try {
      if (this.Tag != null && this.Tag != '') {
        this.tthd_id = parseInt(this.Tag)
      } else {
        this.tthd_id = TrangThaiHD.DANG_THI_CONG
      }
    } catch (err) {
      this.tthd_id = TrangThaiHD.DANG_THI_CONG
    }

    try {
      this.Loading = true
      Promise.all([this.getDMDichVuVT(), this.getDMLoaiHinh(), this.getDMKieuDonVi(), this.getDMQuanHuyen(), this.getDMPhuongXa(), this.getDMPho()])
    } catch (ex) {
      console.log(ex)
      this.Loading = false
    } finally {
      this.Loading = false
    }
  },
  watch: {
    fCheckToThiCong: function(val) {
      if (!val) {
        this.fCheck.ToThiCong = val
        this.fCheck.QuanHuyen = val
        this.fCheck.PhuongXa = val
        this.fCheck.DiaChiLD = val
        this.fCheck.Pho = val
        this.fCheck.App = val
        this.fCheck.Khu = val
        this.fCheck.DacDiem = val
      }
    },
    DichVuVTId: async function(val) {
      this.Loading = true
      try {
        await this.getDMLoaiHinh()
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    QuanId: async function(val) {
      this.Loading = true
      try {
        await this.getDMPhuongXa()
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    PhuongXaId: async function(val) {
      this.Loading = true
      try {
        await this.getDMPho()
        await this.getDMAp()
        await this.getDMKhu()
        await this.getDSDacDiemDieuKien()
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    PhoId: async function(val) {
      this.Loading = true
      try {
        await this.getDMAp()
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    // fCheckToThiCong: function (val) {
    //   if (!val) {
    //     this.fCheck.QuanHuyen = val
    //     this.fCheck.PhuongXa = val
    //     this.fCheck.Pho = val
    //     this.fCheck.App = val
    //     this.fCheck.Khu = val
    //     this.fCheck.DacDiem = val
    //   }
    // },
    'fCheck.QuanHuyen': function(val) {
      console.log('fCheck.QuanHuyen')
      if (!val) {
        this.fCheck.PhuongXa = false
      }
    },
    'fCheck.PhuongXa': function(val) {
      if (!val) {
        this.fCheck.Pho = false
      }
    },
    'fCheck.Pho': function(val) {
      if (!val) {
        this.fCheck.App = false
      }
    },
    'fCheck.App': function(val) {
      if (!val) {
        this.fCheck.Khu = false
      }
    },
    'fCheck.Khu': function(val) {
      if (!val) {
        this.fCheck.DacDiem = false
      }
    },
    Loading: function(val) {
      this.$root.showLoading(val)
    }
    // ,
    // 'fCheck.QuanHuyen': function (val) {
    //   if (!val) {
    //     this.fCheck.PhuongXa = false
    //     this.fCheck.Pho = false
    //     this.fCheck.App = false
    //     this.fCheck.Khu = false
    //     this.fCheck.DacDiem = false
    //   }
    // },
    // 'fCheck.PhuongXa': function (val) {
    //   if (!val) {
    //     this.fCheck.Pho = false
    //     this.fCheck.App = false
    //     this.fCheck.Khu = false
    //     this.fCheck.DacDiem = false
    //   }
    // },
    // 'fCheck.Pho': function (val) {
    //   if (!val) {
    //     this.fCheck.App = false
    //     this.fCheck.Khu = false
    //     this.fCheck.DacDiem = false
    //   }
    // }
  },
  data() {
    return {
      header: {
        title: 'Tra cứu',
        list: [
          {
            name: ' Thay đổi đơn vị thi công',
            link: {
              name: 'Ui.cards'
            }
          },
          {
            name: 'Thay đổi đơn vị thi công',
            link: {
              name: 'Ui.buttons'
            }
          }
        ]
      },
      fCheckToThiCong: false,
      Tag: 1,
      tthd_id: 0,
      diachiKH: {
        DIACHI: null,
        TINH_ID: null,
        QUAN_ID: null,
        PHUONG_ID: null,
        PHO_ID: null,
        AP_ID: null,
        KHU_ID: null,
        DAC_DIEM_ID: null,
        SO_NHA: null,
        Lng: 0,
        Lat: 0,
        BLOCK: null,
        TANG: null,
        PHONG: null,
        MOTA: null,
        TENTINH: ''
      },
      TENTINH: '',
      DsTo: {
        headers: [
          {
            name: 'tenDv',
            text: 'Tổ thi công',
            value: ''
          },
          {
            name: 'tenDvQl',
            text: 'Đơn vị QL',
            value: ''
          }
        ],
        headers2: [
          {
            fieldName: 'tenDv',
            headerText: 'Tổ thi công',
            value: ''
          },
          {
            fieldName: 'tenDvQl',
            headerText: 'Đơn vị QL',
            value: ''
          }
        ],
        lastcoltemplate: -1,
        data: []
      },
      DsThueBao: {
        headers: [
          {
            name: 'RACK',
            text: 'Số máy/Acc',
            value: ''
          },
          {
            name: 'SHELF',
            text: 'Tên TB',
            value: ''
          },
          {
            name: 'SLOT',
            text: 'Địa chỉ LĐ',
            value: ''
          }
        ],
        headers2: [
          {
            fieldName: 'maTb',
            headerText: 'Số máy/Acc',
            value: ''
          },
          {
            fieldName: 'tenTb',
            headerText: 'Tên TB',
            value: ''
          },
          {
            fieldName: 'diaChiLd',
            headerText: 'Địa chỉ LĐ',
            value: ''
          }
        ],
        lastcoltemplate: -1,
        data: []
      },
      modalShowDshdtb: false,
      DsHDTBSearchResult: {
        headers2: [
          {
            fieldName: 'maGd',
            headerText: 'Mã GD',
            value: ''
          },
          {
            fieldName: 'ngayYc',
            headerText: 'Ngày yêu cầu',
            value: ''
          },
          {
            fieldName: 'trangThaiHd',
            headerText: 'Trạng thái',
            value: ''
          },
          {
            fieldName: 'tenLoaiHd',
            headerText: 'Loại hợp đồng',
            value: ''
          },
          {
            fieldName: 'trangThaiHd',
            headerText: 'Kiểu lắp đặt',
            value: ''
          },
          {
            fieldName: 'tenDv',
            headerText: 'Tên đơn vị lập HĐ/PL',
            value: ''
          }
        ],
        data: []
      },
      PopUpRow: {},
      Loading: false,
      DMDichVuVT: [],
      DichVuVTId: '',
      DmQuanHuyen: [],
      QuanId: 0,
      DmPhuongXa: [],
      PhuongXaId: 0,
      KieuDonViId: '',
      DmKieuDonVi: [],
      DmDVVT: [],
      PhoId: '',
      DmPho: [],
      DmAp: [],
      ApId: '',
      DmKhu: [],
      KhuId: '',
      DmDacDiem: [],
      DacDiemId: '',
      DmLoaiHinh: [],
      LoaiHinhId: '',
      HdThueBaoId: '',
      HdKhId: '',
      DonViLDId: '',
      DsHopDong: [],
      ToThiCongId: 0,
      DonViLapDatId: 0,
      diaChiLdId: 0,
      fCheck: {
        ToThiCong: false,
        QuanHuyen: false,
        PhuongXa: false,
        DiaChiLD: false,
        Pho: false,
        App: false,
        Khu: false,
        DacDiem: false
      },
      fInput: {
        MaGD: '', // 'hpgmytv067302',
        MaTB: '',
        LoaiHD: '',
        TenThueBao: '',
        TrangThai: '',
        DiaChiLD: '',
        To: '',
        DonViLD: '',
        GhiChu: '',
        DiaChiLDMoi: ''
      },
      target: '#app .main-wrapper', // document.querySelector('body'),
      animationSettings: {
        effect: 'Zoom'
      },
      position: { X: 'center', Y: 'top' },
      diachild_id: 0,
      tinhld_id: '',
      sonha_ld: ''
    }
  },
  validations: {
    fInput: {
      MaGD: {
        required,
        minLength: minLength(10)
      }
    }
  },
  methods: {
    getDMDichVuVT: async function() {
      var input = {}
      this.DMDichVuVT = this.GetData(await api.getDMDichVuVT(this.axios, input)).map((x) => ({
        id: x.ID,
        text: x.NAME == null ? '' : x.NAME
      }))
    },
    getDMLoaiHinh: async function() {
      if (this.DichVuVTId > 0) {
        var input = {
          dichVuVtId: this.DichVuVTId
        }
        let data = this.GetData(await api.getDMLoaiHinh(this.axios, input))
        this.DmLoaiHinh = data.map((x) => ({
          id: x.ID,
          text: x.NAME == null ? '' : x.NAME
        }))
      } else {
        this.DmLoaiHinh = []
      }
    },
    getDMQuanHuyen: async function() {
      var input = {
        donViId: 0,
        loaiDVId: 1
      }
      this.DmQuanHuyen = this.GetData(await api.getDMQuanHuyen(this.axios, input)).map((x) => ({
        id: x.ID,
        text: x.NAME == null ? '' : x.NAME
      }))
    },
    getDMPhuongXa: async function() {
      if (this.QuanId != null && this.QuanId != '' && this.QuanId > 0) {
        var input = {
          quanId: this.QuanId
        }
        this.DmPhuongXa = this.GetData(await api.getDMPhuongXa(this.axios, input)).map((x) => ({
          id: x.ID,
          text: x.NAME == null ? '' : x.NAME
        }))
        if (this.DmPhuongXa != null && this.DmPhuongXa.length > 0) {
          this.PhuongXaId = this.DmPhuongXa[0].id
        } else {
          this.PhuongXaId = 0
        }
      }
    },
    getDMPho: async function() {
      var input = {
        phuongId: this.PhuongXaId,
        nhomPhoId: 1
      }
      this.DmPho = this.GetData(await api.getDMPho(this.axios, input)).map((x) => ({
        id: x.ID,
        text: x.NAME == null ? '' : x.NAME
      }))
      if (this.DmPho != null && this.DmPho.length > 0) {
        this.PhoId = this.DmPho[0].id
      } else {
        this.PhoId = 0
      }
    },
    getDMAp: async function() {
      var input = {
        phuongId: this.PhuongXaId,
        nhomPhoId: 2
      }
      this.DmAp = this.GetData(await api.getDMPho(this.axios, input)).map((x) => ({
        id: x.ID,
        text: x.NAME == null ? '' : x.NAME
      }))
      if (this.DmAp != null && this.DmAp.length > 0) {
        this.ApId = this.DmAp[0].id
      } else {
        this.ApId = 0
      }
    },
    getDMKhu: async function() {
      var input = {
        phuongId: this.PhuongXaId,
        nhomPhoId: 3
      }
      this.DmKhu = this.GetData(await api.getDMPho(this.axios, input))
      if (this.DmKhu != null && this.DmKhu.length > 0) {
        this.KhuId = this.DmKhu[0].id
      } else {
        this.KhuId = 0
      }
    },
    getDMKieuDonVi: async function() {
      var input = {}
      this.DmKieuDonVi = this.GetData(await api.getDMKieuDonVi(this.axios, input)).map((x) => ({
        id: x.ID,
        text: x.NAME == null ? '' : x.NAME
      }))
    },
    getDSHopDongTheoHDTBID: async function(hdtbId) {
      // hdtbId = 5165
      var input = {
        hdtbId: hdtbId
      }
      return this.GetData(await api.getDSHopDongTheoHDTBID(this.axios, input))
    },
    getDanhSachTo: async function() {
      try {
        if (this.fCheckToThiCong) {
          this.Loading = true
          await this.getDanhSachTramTheoDK()
          this.Loading = false
        }
      } catch (err) {
      } finally {
        this.Loading = false
      }
    },
    getDanhSachTramTheoDK: async function() {
      var input = {
        apId: this.fCheck.App && this.ApId > 0 ? this.ApId : 0,
        khuId: this.fCheck.Khu && this.KhuId > 0 ? this.KhuId : 0,
        phoId: this.fCheck.Pho && this.PhoId > 0 ? this.PhoId : 0,
        phuongId: this.fCheck.PhuongXa && this.PhuongXaId > 0 ? this.PhuongXaId : 0
      }
      let data = this.GetData(await api.getDanhSachTramTheoDK(this.axios, input))
      if (data == null || data.length == 0) {
        this.DsTo.data = []
        this.ShowError(`Không tìm thấy danh sách trạm thi công`)
      } else {
        this.DsTo.data = data
      }
    },
    getDSDacDiemDieuKien: async function() {
      var input = {
        apId: this.ApId,
        khuId: this.KhuId,
        phoId: this.PhoId,
        phuongId: this.PhuongXaId
      }
      this.DmDacDiem = this.GetData(await api.getDSDacDiemDieuKien(this.axios, input))
    },
    ChangedTB: async function(item) {
      if (item) {
        this.fInput.MaTB = item.maTb
        this.DichVuVTId = item.dichVuVtId
        this.LoaiHinhId = item.loaiTbId
        this.fInput.TenThueBao = item.tenTb
        this.fInput.DiaChiLD = item.diaChiLd
        this.fInput.GhiChu = item.ghiChu

        this.fInput.TrangThai = item.trangThaiHd
        this.HdThueBaoId = item.hdtbId
        this.DonViLDId = item.donViId
        try {
          let donviql = await this.layDuLieuDonViByID(item.donViId)
          if (donviql && donviql.length > 0) {
            if (donviql != -1) {
              this.fInput.DonViLD = donviql
            }
          }
          let data = await this.layBangThamso(item.hdtbId)
          this.fInput.To = ''
          if (data != null && data.loaidv_id == 5) {
            // TRAM_VT = 5
            this.fInput.To = data.ten_dv
          } else {
            // alert('aaa')
            // console.log(data.filter(x => x.loaidv_id == 5))
          }
        } catch (ex) {}
        await this.HienThiTTDiaChi(this.HdThueBaoId)
      }
    },
    HienThiTTDiaChi: async function(hdthId) {
      var hdtb = await this.getDSHopDongTheoHDTBID(hdthId)
      if (hdtb && hdtb.length > 0) {
        let hd = hdtb[0]
        this.diachild_id = hd.diaChiLdId
        this.diaChiLdId = hd.diaChiLdId
        this.tinhld_id = hd.tinhld_id
        this.QuanId = hd.quanLdId
        if (hd.quanLdId > 0) {
          this.PhuongXaId = hd.phuongLdId
        } else {
          this.PhuongXaId = 0
        }

        if (hd.phuongLdId > 0) {
          this.PhoId = hd.phoLdId
        } else {
          this.PhoId = 0
        }

        this.ApId = hd.apLdId
        this.KhuId = hd.khuLdId
        this.sonha_ld = hd.soNhaLd

        // if (hd.dacDiemLdId) { this.DacDiemId = hd.dacDiemLdId }
      }
    },
    fInputReset() {
      //fInput.MaGD = '' // 'hpgmytv067302',
      this.fInput.MaTB = ''
      this.fInput.LoaiHD = ''
      this.fInput.TenThueBao = ''
      this.fInput.TrangThai = ''
      this.fInput.DiaChiLD = ''
      this.fInput.To = ''
      this.fInput.DonViLD = ''
      this.fInput.GhiChu = ''
      this.fInput.DiaChiLDMoi = ''
      this.fCheckToThiCong = false
    },
    TraCuu: async function() {
      this.$v.$touch()
      if (this.fInput.MaGD == '') {
        this.ShowError(`Bạn chưa nhập mã giao dịch `)
        this.$refs.MaGD.focus()
        return
      }
      try {
        this.DsThueBao.data = []
        this.DsHDTBSearchResult.data = []
        this.DsTo.data = []
        this.Loading = true
        this.modalShowDshdtb = false
        var input = {
          maTb: this.fInput.MaGD
        } // hpgmytv067302
        this.DsHopDong = this.GetData(await api.getDSHopDongTheoMaTB(this.axios, input))
        let dshd = this.DsHopDong
        this.fInputReset()
        if (dshd.length > 1) {
          this.Loading = false
          // this.fInput.MaGD = dshd[0].maGd
          // this.fInput.LoaiHD = dshd[0].ten_loaihd
          // this.HdKhId = dshd[0].hdkhId
          this.DsHDTBSearchResult.data = dshd
          this.modalShowDshdtb = true
          return
        } else {
          this.fInput.MaGD = dshd[0].maGd
          this.fInput.LoaiHD = dshd[0].tenLoaiHd
          this.HdKhId = dshd[0].hdkhId
          await this.hienThiHopDongThueBao()
        }
      } catch (ex) {
        this.Loading = false
      }
      this.Loading = false
    },
    hienThiHopDongThueBao: async function() {
      let input = {
        hdkhId: this.HdKhId
      }
      let dsHDTB = this.GetData(await api.getDSHopDongTheoHDKHID(this.axios, input))
      this.DsThueBao.data = dsHDTB
      await this.ChangedTB(dsHDTB[0])
    },
    kiemtraDuLieu_old: async function() {
      var input = {
        inIdNeo: 'tthd_id',
        inTable: 'css.hd_thuebao',
        inDk: 'where hdtb_id =' + this.HdThueBaoId
      }
      let data = this.GetData(await api.kiemtraDuLieu(this.axios, input))
      return data
    },
    kiemtraDuLieu: async function() {
      var input = { type: 2, param: this.HdThueBaoId }
      let data = this.GetData(await api.fn_tt_hd_thuebao(this.axios, input))
      return data
    },
    layDuLieuDonViByID_old: async function(donviId) {
      var input = {
        inIdNeo: 'ten_dv',
        inTable: 'admin.donvi',
        inDk: `where donvi_id  = ${donviId}`
      }
      let data = this.GetData(await api.kiemtraDuLieu(this.axios, input))
      return data
    },
    layDuLieuDonViByID: async function(donviId) {
      var input = { type: 2, param: donviId }
      let data = this.GetData(await api.layDuLieuDonViByID(this.axios, input))
      return data
    },
    layBangThamso: async function(hdtbId) {
      var input = {
        hdtbId: hdtbId
      }
      let data = this.GetData(await api.layBangThamso(this.axios, input))
      return data
    },
    SelectHDFromPopUp: function(i, item) {
      if (item) {
        this.PopUpRow = item
      }
    },
    SelectHDFromPopUpDb: function(i, item) {
      if (item) {
        this.PopUpRow = item
        this.SelectHDFromPopUpClose()
      }
    },
    SelectHDFromPopUpClose: async function() {
      try {
        if (this.PopUpRow) {
          this.fInput.MaGD = this.PopUpRow.maGd
          this.modalShowDshdtb = false
          this.Loading = true
          this.HdKhId = this.PopUpRow.hdkhId
          this.LoaiHinhId = this.PopUpRow.loaiTbId
          this.fInput.LoaiHD = this.PopUpRow.tenLoaiHd
          this.HdKhId = this.PopUpRow.hdkhId
          await this.hienThiHopDongThueBao()
        }
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    async tsbtnTraCuu_Click() {
      try {
        await this.TraCuu()
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    async tsbtnGhiLai_Click() {
      try {
        await this.GhiLai()
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    async tsbtnThoat_Click() {},
    GhiLai: async function() {
      let vkieu = 0
      if (!this.fCheck.DiaChiLD && !this.fCheckToThiCong) {
        this.ShowError('Hãy chọn sửa đổi thông tin lắp đặt hoặc tổ thi công !')
        return
      }
      if (this.fCheck.DiaChiLD && !this.fCheckToThiCong) {
        vkieu = 4
        if (this.fInput.DiaChiLDMoi == '') {
          this.ShowError('Hãy nhập địa chỉ lắp đặt!!')
          this.$refs.diaChiLDMoi.focus()
          return
        }
      }
      if (!this.fCheck.DiaChiLD && this.fCheckToThiCong) {
        vkieu = 2
        if (this.DsThueBao.data.length <= 0) {
          this.ShowError('Không có thông tin về tổ thi công mới.')
          return
        }
      }
      if (this.fCheck.DiaChiLD && this.fCheckToThiCong) {
        vkieu = 5
        if (this.DsThueBao.data.length <= 0) {
          this.ShowError('Không có thông tin về tổ thi công mới.')
          return
        }
        if (this.fInput.DiaChiLDMoi.trim() == '') {
          this.ShowError('Hãy nhập địa chỉ lắp đặt!')
          this.$refs.diaChiLDMoi.focus()
          return
        }
      }
      try {
        let checkDK = await this.kiemtraDuLieu()
        if (checkDK[0] == -1 || checkDK[0] == 6 || checkDK[0] == 7) {
          this.ShowError('Thuê bao đã hoàn thành không được thay đổi thông tin! ')
          return
        }
      } catch (ex) {
        this.ShowError(`Lỗi check điều kiện trạng thái thuê bao ! ${ex}`)
        return
      }

      let confirms = []
      if (this.fCheck.DiaChiLD && this.fCheckToThiCong) {
        confirms.push(`Bạn chắc chắn muốn thay đổi địa chỉ lắp đặt ?`)
      }
      if (!this.fCheck.DiaChiLD && this.fCheckToThiCong) {
        confirms.push(`Bạn chắc chắn muốn thay đổi tổ thi công ?`)
      }
      if (!this.fCheck.DiaChiLD && this.fCheckToThiCong) {
        confirms.push(`Bạn chắc chắn muốn thay đổi địa chỉ lắp đặt và tổ thi công ?`)
      }
      confirms.push(`Bạn chắc chắn muốn thay đổi địa chỉ lắp đặt và tổ thi công ?`)
      // Check map hop dong
      if (confirms.length > 0) {
        this.$bvModal
          .msgBoxConfirm(confirms[0], {
            title: '',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          })
          .then(async (v) => {
            if (v) {
              var input = {
                apLdId: this.fInput.ApId,
                dacDiemId: this.DacDiemId,
                diaChiLd: this.fInput.DiaChiLD,
                diaChiLdId: this.DiaChiLDId,
                donViLdId: this.fCheckToThiCong ? this.DonViLapDatId : 0,
                hdTbId: this.HdThueBaoId,
                khuLdId: this.KhuId,
                kieu: vkieu,
                kieuDvId: this.KieuDonViId,
                phoLdId: this.PhoId,
                phuongLdId: this.PhuongXaId,
                quanLdId: this.QuanId,
                soNhaLd: this.sonha_ld,
                tinhLdId: this.tinhld_id,
                toTcId: this.fCheckToThiCong ? this.ToThiCongId : 0
              }

              var response = await api.updateTram(this.axios, input)
              if (response.data.error === 200 || response.data.error === '200') {
                this.ShowSuccess(`Cập nhật thành công `)
                try {
                  this.Loading = true
                  await this.hienThiHopDongThueBao()
                  this.Loading = false
                } catch (ex) {
                  this.Loading = false
                }
              } else {
                console.log(response.data.error_code)
                this.ShowError(`Cập nhật lỗi: ${response.data.error_code} `)
              }
            }
          })
      }
    },
    exportExcel: function() {
      if (this.DsPort.data != null && this.DsPort.data.length > 0) {
        let data = XLSX.utils.json_to_sheet(this.DsPort.data)
        let wb = XLSX.utils.book_new() // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data, 'DsPorts') // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, 'DsPorts.xlsx')
      } else this.$toast.error('Không có dữ liệu!')
    },
    GetData: function(response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    ToThiCongChanged: function(i, item) {
      this.ToThiCongId = item.donViId
      this.DonViLapDatId = item.donViQlId
    },
    ThueBaoChanged: async function(i, item) {
      try {
        this.Loading = true
        await this.ChangedTB(item)
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    selectedAccount() {},
    LayDiaChi(diachi) {
      this.fInput.DiaChiLDMoi = diachi.diachimoi
      console.log(diachi)
    },
    ShowDiaChiLD: function() {
      // this.$bvModal.show('my-modal-diachi-lapdat')
      this.$refs.dialogObjDiaChiLD.show()
    },
    ShowMaGiaoDich: function() {
      this.$refs.popupSearchContract.showModal()
      // this.$refs.dialogObjMaGD.show()
      // this.$bvModal.show('my-modal-magd')
    },
    acceptSearchContract(item) {
      this.fInput.MaGD = item.ma_gd
      if (this.fInput.MaGD && this.fInput.MaGD.length > 1) {
        this.TraCuu()
      }
    },
    ShowWarning: function(message) {
      this.$toast.error(message)
    },
    ShowMessage: function(message) {
      this.$toast.error(message)
    },
    ShowSuccess: function(message) {
      this.$toast.success(message)
    },
    ShowError: function(message) {
      this.$toast.error(message)
    }
  }
}
</script>

<style scoped>
#app {
  color: #008cff;
  height: 40px;
  left: 45%;
  position: absolute;
  top: 45%;
  width: 30%;
}

.control-section {
  height: 100%;
  min-height: 200px;
}
</style>
