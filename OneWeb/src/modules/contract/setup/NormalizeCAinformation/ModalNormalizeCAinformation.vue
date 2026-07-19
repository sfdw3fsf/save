<template>
  <b-modal ref="popupModalNormalizeCAinformation" id="frmTimKiemHDTB" size="xl" hide-header hide-header-close hide-footer body-class="p-0" no-close-on-esc no-close-on-backdrop @show="handleShowModal">
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span> Tìm kiếm hợp đồng CNTT</div>
        <div class="close -ap icon-close" data-dismiss="modal" @click="hideModal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript:;" @click="btnChapNhan_click"> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp nhận </a>
          </li>
          <li>
            <a href="javascript:;" @click="btnTimKiem_click"> <span class="icon one-search"></span>Tìm kiếm </a>
          </li>
          <li>
            <a href="javascript:;"> <span class="icon one-download"></span>Lấy thông tin từ Token </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">
            <span
              @click="isShowForm = !isShowForm"
              class="icon fa"
              :class="{
                'fa-angle-up': isShowForm,
                'fa-angle-down': !isShowForm
              }"
            ></span>
            Thông tin tìm kiếm
          </div>
          <div class="info-row" v-if="isShowForm">
            <div class="key" style="width: 180px">Loại hình thuê bao</div>
            <div class="value">
              <ComboboxGrid
                class="select-custom"
                :columns="[
                  { fieldName: 'loaitb_id', headerText: 'ID', allowFiltering: true, width: '100' },

                  { fieldName: 'loaihinh_tb', headerText: 'Loại hình thuê bao', allowFiltering: true, width: '300' }
                ]"
                :dataSource="loaihinh_tb_cntt"
                textField="loaihinh_tb"
                valueField="loaitb_id"
                v-model="loaitb_id"
                :enabledSelectFirstRow="true"
                @selectedChanged="comboxChange"
              >
              </ComboboxGrid>
            </div>
          </div>
          <div class="info-row" v-if="isShowForm">
            <div class="key" style="width: 180px">Serial Number</div>
            <div class="value">
              <input type="text" class="form-control" v-model="form.serial" />
            </div>
          </div>
          <div class="info-row" v-if="isShowForm">
            <div class="key" style="width: 180px">USB TOKEN INFOR</div>
            <div class="value">
              <input type="text" class="form-control" v-model="form.usb_token_info" />
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row" v-if="isShowForm">
                <div class="key" style="width: 180px">Tên thuê bao</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="form.ten_tb" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row" v-if="isShowForm">
                <div class="key" style="width: 180px">Tên khách hàng</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="form.ten_kh" />
                </div>
              </div>
            </div>
          </div>

          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row" v-if="isShowForm">
                <div class="key" style="width: 180px">Mã số thuế (Thuê bao)</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="form.mst_tb" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row" v-if="isShowForm">
                <div class="key" style="width: 180px">Mã số thuế (Khách hàng)</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="form.mst_kh" />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row" v-if="isShowForm">
                <div class="key" style="width: 180px">Số điện thoại (Thuê bao)</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="form.sdt_tb" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row" v-if="isShowForm">
                <div class="key" style="width: 180px">
                  Số điện thoại (Khách hàng)
                </div>
                <div class="value">
                  <input type="text" class="form-control" v-model="form.sdt_kh" />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row" v-if="isShowForm">
                <div class="key" style="width: 180px">Số giấy tờ (Thuê bao)</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="form.sogt_tb" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row" v-if="isShowForm">
                <div class="key" style="width: 180px">Số giấy tờ (Khách hàng)</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="form.sogt_kh" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Kết quả tìm kiếm</div>
          <div class="row">
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w30 nowrap">Mã giao dịch</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="result_magd" />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w30 nowrap">Mã thuê bao</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="result_matb" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w30 nowrap">Tên khách hàng</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="result_tenkh" />
                </div>
              </div>
            </div>
          </div>
          <DataGrid
            class="table-result table-gachle"
            :columns="grcKetQuaTimKiem.header"
            :dataSource="grcKetQuaTimKiem.list"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            :showColumnCheckbox="false"
            :enabledSelectFirstRow="true"
            ref="grcKetQuaTimKiem"
            @selectedItemsChanged="selectedHDTB_click"
          />
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import moment from 'moment'
import api from './API.js'
import DataGrid from '@/components/Controls/DataGrid'

export default {
  name: 'ModalNormalizeCAinformation',
  components: { DataGrid },
  props: {
    data: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      isShowForm: true,
      ketqua: {},
      result_magd: '',
      result_matb: '',
      result_tenkh: '',
      loaihinh_tb_cntt: [
        {
          loaihinh_tb: 'VNPT Meeting',
          loaitb_id: 89
        },
        {
          loaihinh_tb: 'Hội nghị truyền hình NGN',
          loaitb_id: 90
        },
        {
          loaihinh_tb: 'Mega Vmeeting HD',
          loaitb_id: 91
        },
        {
          loaihinh_tb: 'Mega Vmeeting Nội bộ',
          loaitb_id: 92
        },
        {
          loaihinh_tb: 'Mega Vmeeting SD',
          loaitb_id: 113
        },
        {
          loaihinh_tb: 'Truyền dẫn tín hiệu truyền hình',
          loaitb_id: 146
        },
        {
          loaihinh_tb: 'Thu phát hình quốc tế',
          loaitb_id: 311
        },
        {
          loaihinh_tb: 'Cho thuê băng tần vệ tinh',
          loaitb_id: 312
        },
        {
          loaihinh_tb: 'Uplink',
          loaitb_id: 313
        },
        {
          loaihinh_tb: 'VSAT-IP',
          loaitb_id: 314
        },
        {
          loaihinh_tb: 'VSAT-PAMA',
          loaitb_id: 315
        },
        {
          loaihinh_tb: 'An toàn thông tin - DVGP và dự án khác',
          loaitb_id: 594
        },
        {
          loaihinh_tb: 'VNPT-WAF',
          loaitb_id: 2128
        },
        {
          loaihinh_tb: 'F-secure',
          loaitb_id: 24
        },
        {
          loaihinh_tb: 'CA-TAX',
          loaitb_id: 55
        },
        {
          loaihinh_tb: 'VNPT SSL',
          loaitb_id: 78
        },
        {
          loaihinh_tb: 'Mega Green',
          loaitb_id: 114
        },
        {
          loaihinh_tb: 'Security',
          loaitb_id: 115
        },
        {
          loaihinh_tb: 'VNPT CA',
          loaitb_id: 116
        },
        {
          loaihinh_tb: 'CA-IVAN',
          loaitb_id: 117
        },
        {
          loaihinh_tb: 'Antivirus',
          loaitb_id: 126
        },
        {
          loaihinh_tb: 'VNPT BHXH',
          loaitb_id: 132
        },
        {
          loaihinh_tb: 'VNPT TAX',
          loaitb_id: 154
        },
        {
          loaihinh_tb: 'VNPT Ký số',
          loaitb_id: 181
        },
        {
          loaihinh_tb: 'VNPT eKYC',
          loaitb_id: 276
        },
        {
          loaihinh_tb: 'VNPT AntiDDoS ',
          loaitb_id: 279
        },
        {
          loaihinh_tb: 'VNPT SmartCA',
          loaitb_id: 288
        },
        {
          loaihinh_tb: 'VNPT BioID',
          loaitb_id: 293
        },
        {
          loaihinh_tb: 'VNPT MSS',
          loaitb_id: 297
        },
        {
          loaihinh_tb: 'SmartCa PS',
          loaitb_id: 318
        },
        {
          loaihinh_tb: 'VNPT Smart IR',
          loaitb_id: 324
        },
        {
          loaihinh_tb: 'VNPT DNS Protection',
          loaitb_id: 325
        },
        {
          loaihinh_tb: 'Mobile ID',
          loaitb_id: 333
        },
        {
          loaihinh_tb: 'VNPT Pentest',
          loaitb_id: 334
        },
        {
          loaihinh_tb: 'Đào tạo ATTT',
          loaitb_id: 335
        },
        {
          loaihinh_tb: 'Tư vấn ATTT',
          loaitb_id: 350
        },
        {
          loaihinh_tb: 'Bảo mật KAS',
          loaitb_id: 2113
        },
        {
          loaihinh_tb: 'Hóa đơn điện tử - Máy tính tiền',
          loaitb_id: 2116
        },
        {
          loaihinh_tb: 'VCC cố định',
          loaitb_id: 2117
        },
        {
          loaihinh_tb: 'VNedu Học bạ điện tử',
          loaitb_id: 370
        },
        {
          loaihinh_tb: 'HP Wifi',
          loaitb_id: 371
        },
        {
          loaihinh_tb: 'VNEdu QLTB',
          loaitb_id: 378
        },
        {
          loaihinh_tb: 'VNPT ID CHECK',
          loaitb_id: 372
        },
        {
          loaihinh_tb: 'VNPT Invoice - Inbot',
          loaitb_id: 373
        },
        {
          loaihinh_tb: 'VNPT SmartCloud VPC',
          loaitb_id: 374
        },
        {
          loaihinh_tb: 'VNPT CLOUD PACS',
          loaitb_id: 375
        },
        {
          loaihinh_tb: 'VNPT iAlert',
          loaitb_id: 376
        },
        {
          loaihinh_tb: 'VNEdu DigiLib',
          loaitb_id: 377
        },
        {
          loaihinh_tb: 'VNPT Mobile Solutions',
          loaitb_id: 2120
        },
        {
          loaihinh_tb: 'VNPT Family Safe',
          loaitb_id: 386
        },
        {
          loaihinh_tb: 'VNPT Smart Reader',
          loaitb_id: 387
        },
        {
          loaihinh_tb: 'VNPT Smart Voice',
          loaitb_id: 388
        },
        {
          loaihinh_tb: 'VNPT Smart Bot',
          loaitb_id: 389
        },
        {
          loaihinh_tb: 'VNPT TSA',
          loaitb_id: 390
        },
        {
          loaihinh_tb: 'VNPT iNRES',
          loaitb_id: 589
        },
        {
          loaihinh_tb: 'VNPT AIMS',
          loaitb_id: 590
        },
        {
          loaihinh_tb: 'VNPT iVMS',
          loaitb_id: 591
        },
        {
          loaihinh_tb: 'VNPT TrustedDoc',
          loaitb_id: 2121
        },
        {
          loaihinh_tb: 'VNPT iWorkplace',
          loaitb_id: 2122
        },
        {
          loaihinh_tb: 'VNPT VCP',
          loaitb_id: 2123
        },
        {
          loaihinh_tb: 'VNPT SCCS',
          loaitb_id: 2124
        },
        {
          loaihinh_tb: 'Dịch vụ chuyên gia - DVGP và dự án khác',
          loaitb_id: 603
        },
        {
          loaihinh_tb: 'Phân phối bán lẻ - DVGP và dự án khác',
          loaitb_id: 602
        },
        {
          loaihinh_tb: 'Nông nghiệp thông minh - DVGP và dự án khác',
          loaitb_id: 601
        },
        {
          loaihinh_tb: 'Du lịch thông minh - DVGP và dự án khác',
          loaitb_id: 600
        },
        {
          loaihinh_tb: 'Vận tải và logistics - DVGP và dự án khác',
          loaitb_id: 599
        },
        {
          loaihinh_tb: 'Quản trị doanh nghiệp - DVGP và dự án khác',
          loaitb_id: 598
        },
        {
          loaihinh_tb: 'Giáo dục số - DVGP và dự án khác',
          loaitb_id: 597
        },
        {
          loaihinh_tb: 'Y tế số - DVGP và dự án khác',
          loaitb_id: 596
        },
        {
          loaihinh_tb: 'Chính quyền số - DVGP và dự án khác',
          loaitb_id: 595
        },
        {
          loaihinh_tb: 'Công nghệ nền tảng - DVGP và dự án khác',
          loaitb_id: 593
        },
        {
          loaihinh_tb: 'Hạ tầng Số - DVGP và dự án khác',
          loaitb_id: 592
        },
        {
          loaihinh_tb: 'VNPT DMIT',
          loaitb_id: 391
        },
        {
          loaihinh_tb: 'Cổng dịch vụ công Quốc Gia',
          loaitb_id: 2125
        },
        {
          loaihinh_tb: 'VNPT Waterway GIS',
          loaitb_id: 2126
        },
        {
          loaihinh_tb: 'Nền tảng Cửa khẩu số',
          loaitb_id: 2127
        },
        {
          loaihinh_tb: 'VNPT STĐV - sổ tay đảng viên',
          loaitb_id: 604
        },
        {
          loaihinh_tb: 'VNPT iWorkplace',
          loaitb_id: 392
        },
        {
          loaihinh_tb: 'VNPT Mobile Solutions - M2M',
          loaitb_id: 2130
        },
        {
          loaihinh_tb: 'Dịch vụ đào tạo ATTT',
          loaitb_id: 605
        },
        {
          loaihinh_tb: 'VNPT POS',
          loaitb_id: 606
        },
        {
          loaihinh_tb: 'VNPT HOC',
          loaitb_id: 2129
        },
        {
          loaihinh_tb: 'vnEdu EzStudy - Phần mềm học trực tuyến',
          loaitb_id: 395
        },
        {
          loaihinh_tb: 'Dịch vụ xây dựng CSDL Tài nguyên môi trường',
          loaitb_id: 2110
        },
        {
          loaihinh_tb: 'Dịch vụ tư vấn/vận hành hệ thống CNTT',
          loaitb_id: 607
        },
        {
          loaihinh_tb: 'vnPortal',
          loaitb_id: 237
        },
        {
          loaihinh_tb: 'VNPT iOffice',
          loaitb_id: 35
        },
        {
          loaihinh_tb: '1800',
          loaitb_id: 38
        },
        {
          loaihinh_tb: 'VNPT Check',
          loaitb_id: 52
        },
        {
          loaihinh_tb: 'VNPT Kios',
          loaitb_id: 53
        },
        {
          loaihinh_tb: 'VNPT Pos',
          loaitb_id: 54
        },
        {
          loaihinh_tb: 'VNPT SmartAds',
          loaitb_id: 56
        },
        {
          loaihinh_tb: 'VNPT CA Plugin',
          loaitb_id: 80
        },
        {
          loaihinh_tb: 'vnEdu',
          loaitb_id: 118
        },
        {
          loaihinh_tb: 'VNPT Portal',
          loaitb_id: 120
        },
        {
          loaihinh_tb: 'VNPT iGate',
          loaitb_id: 121
        },
        {
          loaihinh_tb: 'VNPT Invoice',
          loaitb_id: 122
        },
        {
          loaihinh_tb: '1900',
          loaitb_id: 127
        },
        {
          loaihinh_tb: 'SMS Brandname',
          loaitb_id: 131
        },
        {
          loaihinh_tb: 'VNPT HIS',
          loaitb_id: 136
        },
        {
          loaihinh_tb: 'VNPT CA Sign Server',
          loaitb_id: 140
        },
        {
          loaihinh_tb: 'Wifi Marketing',
          loaitb_id: 141
        },
        {
          loaihinh_tb: 'Vpoint',
          loaitb_id: 142
        },
        {
          loaihinh_tb: 'VNPT DMS',
          loaitb_id: 143
        },
        {
          loaihinh_tb: 'VNPT Karo',
          loaitb_id: 145
        },
        {
          loaihinh_tb: 'Tên miền Việt Nam',
          loaitb_id: 147
        },
        {
          loaihinh_tb: 'Tên miền Quốc tế',
          loaitb_id: 148
        },
        {
          loaihinh_tb: 'VCC',
          loaitb_id: 149
        },
        {
          loaihinh_tb: 'Thiết kế web',
          loaitb_id: 151
        },
        {
          loaihinh_tb: 'VNPT HMIS',
          loaitb_id: 156
        },
        {
          loaihinh_tb: 'VNPT ORM',
          loaitb_id: 160
        },
        {
          loaihinh_tb: 'VNPT Pharmacy',
          loaitb_id: 161
        },
        {
          loaihinh_tb: 'VietLot',
          loaitb_id: 167
        },
        {
          loaihinh_tb: 'My English',
          loaitb_id: 168
        },
        {
          loaihinh_tb: 'VNPT eReceipt - Biên lai điện tử',
          loaitb_id: 175
        },
        {
          loaihinh_tb: 'VNPT CCVC',
          loaitb_id: 176
        },
        {
          loaihinh_tb: 'VNPT HRM',
          loaitb_id: 177
        },
        {
          loaihinh_tb: 'VNPT BSC',
          loaitb_id: 178
        },
        {
          loaihinh_tb: 'VNPT SmartEdu',
          loaitb_id: 179
        },
        {
          loaihinh_tb: 'VNEdu SmartID',
          loaitb_id: 180
        },
        {
          loaihinh_tb: 'Số ngôi sao',
          loaitb_id: 182
        },
        {
          loaihinh_tb: 'VNPT eCabinet',
          loaitb_id: 200
        },
        {
          loaihinh_tb: 'VNPT HCSN',
          loaitb_id: 202
        },
        {
          loaihinh_tb: 'VNEdu IOC',
          loaitb_id: 203
        },
        {
          loaihinh_tb: 'VNEdu Enrollment',
          loaitb_id: 204
        },
        {
          loaihinh_tb: 'VNPT LIB',
          loaitb_id: 205
        },
        {
          loaihinh_tb: 'Quảng cáo doanh nghiệp',
          loaitb_id: 206
        },
        {
          loaihinh_tb: 'Thu âm Tổng đài',
          loaitb_id: 207
        },
        {
          loaihinh_tb: 'VNPT E-Learning',
          loaitb_id: 208
        },
        {
          loaihinh_tb: 'Tư vấn du lịch',
          loaitb_id: 209
        },
        {
          loaihinh_tb: 'VNEdu TKB',
          loaitb_id: 211
        },
        {
          loaihinh_tb: 'VNEdu NMS',
          loaitb_id: 212
        },
        {
          loaihinh_tb: 'VNEdu FMS',
          loaitb_id: 213
        },
        {
          loaihinh_tb: 'VNEdu COC',
          loaitb_id: 214
        },
        {
          loaihinh_tb: 'VNEdu SLL',
          loaitb_id: 215
        },
        {
          loaihinh_tb: 'VNEdu QoE',
          loaitb_id: 216
        },
        {
          loaihinh_tb: 'VNEdu eLessionTool',
          loaitb_id: 217
        },
        {
          loaihinh_tb: 'VNEdu Preschool',
          loaitb_id: 218
        },
        {
          loaihinh_tb: 'VNEdu eCert',
          loaitb_id: 219
        },
        {
          loaihinh_tb: 'VNEdu-Quiz',
          loaitb_id: 220
        },
        {
          loaihinh_tb: 'VNEdu FAS',
          loaitb_id: 221
        },
        {
          loaihinh_tb: 'VNPT Smart Tourist',
          loaitb_id: 223
        },
        {
          loaihinh_tb: 'vnEdu - Portal',
          loaitb_id: 225
        },
        {
          loaihinh_tb: 'VNEdu eLession',
          loaitb_id: 227
        },
        {
          loaihinh_tb: 'VNPT-eDoc',
          loaitb_id: 234
        },
        {
          loaihinh_tb: 'VNPT VXP',
          loaitb_id: 235
        },
        {
          loaihinh_tb: 'Phần mềm SALESPRO',
          loaitb_id: 236
        },
        {
          loaihinh_tb: 'VNPT LIS',
          loaitb_id: 261
        },
        {
          loaihinh_tb: 'Phần mềm hợp tác',
          loaitb_id: 262
        },
        {
          loaihinh_tb: 'VNPT RIS/PACS',
          loaitb_id: 263
        },
        {
          loaihinh_tb: 'VNPT VSR (LRIS)',
          loaitb_id: 264
        },
        {
          loaihinh_tb: 'VNPT MCAP',
          loaitb_id: 267
        },
        {
          loaihinh_tb: 'VNPT SMCC',
          loaitb_id: 268
        },
        {
          loaihinh_tb: 'Quản lý công chứng',
          loaitb_id: 269
        },
        {
          loaihinh_tb: 'IT CARE',
          loaitb_id: 270
        },
        {
          loaihinh_tb: 'Moblie Money',
          loaitb_id: 272
        },
        {
          loaihinh_tb: 'VNPT AsMe',
          loaitb_id: 277
        },
        {
          loaihinh_tb: 'VNPT-iDVC',
          loaitb_id: 278
        },
        {
          loaihinh_tb: 'eZoZo',
          loaitb_id: 280
        },
        {
          loaihinh_tb: 'SME-HRM: Quản lý nhân sự',
          loaitb_id: 281
        },
        {
          loaihinh_tb: 'SME-SALES: Quản lý bán hàng',
          loaitb_id: 282
        },
        {
          loaihinh_tb: 'SME-WEBSITE: Dịch vụ thiết kế Website',
          loaitb_id: 283
        },
        {
          loaihinh_tb: 'vnFace',
          loaitb_id: 284
        },
        {
          loaihinh_tb: 'VNPT AI Camera ',
          loaitb_id: 285
        },
        {
          loaihinh_tb: 'VNPT BMIS',
          loaitb_id: 286
        },
        {
          loaihinh_tb: 'VNPT eDIG',
          loaitb_id: 287
        },
        {
          loaihinh_tb: 'VNPT eContract',
          loaitb_id: 290
        },
        {
          loaihinh_tb: 'vnEdu HSS',
          loaitb_id: 291
        },
        {
          loaihinh_tb: 'VNPT one Business',
          loaitb_id: 292
        },
        {
          loaihinh_tb: 'POSIO',
          loaitb_id: 294
        },
        {
          loaihinh_tb: 'VNPT Holio',
          loaitb_id: 295
        },
        {
          loaihinh_tb: 'VNPT Home & Clinic',
          loaitb_id: 296
        },
        {
          loaihinh_tb: 'VNCARE',
          loaitb_id: 299
        },
        {
          loaihinh_tb: 'Bảo hiểm số kèm fiber',
          loaitb_id: 300
        },
        {
          loaihinh_tb: 'Phần mềm tự phát triển',
          loaitb_id: 301
        },
        {
          loaihinh_tb: 'VNPT HKD',
          loaitb_id: 302
        },
        {
          loaihinh_tb: 'VNPT IQMS',
          loaitb_id: 303
        },
        {
          loaihinh_tb: 'Nhắn tin P2P',
          loaitb_id: 304
        },
        {
          loaihinh_tb: 'VNPT LMS ETEP',
          loaitb_id: 307
        },
        {
          loaihinh_tb: 'SD Wan',
          loaitb_id: 308
        },
        {
          loaihinh_tb: 'VNPT HSSK',
          loaitb_id: 309
        },
        {
          loaihinh_tb: 'Nhắn tin A2P',
          loaitb_id: 316
        },
        {
          loaihinh_tb: 'VNPT IOC',
          loaitb_id: 317
        },
        {
          loaihinh_tb: 'VNPT eTicket',
          loaitb_id: 319
        },
        {
          loaihinh_tb: 'vnEdu Mobile App',
          loaitb_id: 321
        },
        {
          loaihinh_tb: 'VNPT GoMeet',
          loaitb_id: 322
        },
        {
          loaihinh_tb: 'vnEdu Content',
          loaitb_id: 323
        },
        {
          loaihinh_tb: 'VNPT iLIS',
          loaitb_id: 326
        },
        {
          loaihinh_tb: 'VNPT ODP',
          loaitb_id: 327
        },
        {
          loaihinh_tb: 'Giải pháp tổng đài dịch vụ công 1022',
          loaitb_id: 328
        },
        {
          loaihinh_tb: 'VNPT Ehc',
          loaitb_id: 329
        },
        {
          loaihinh_tb: 'VNPT IOT Platform',
          loaitb_id: 330
        },
        {
          loaihinh_tb: 'VNPT ASXH',
          loaitb_id: 331
        },
        {
          loaihinh_tb: 'VNPT MIS',
          loaitb_id: 332
        },
        {
          loaihinh_tb: 'VNPT ORIM-X',
          loaitb_id: 337
        },
        {
          loaihinh_tb: 'vnSocial',
          loaitb_id: 338
        },
        {
          loaihinh_tb: 'VNPT IDP',
          loaitb_id: 339
        },
        {
          loaihinh_tb: 'VNPT EOC',
          loaitb_id: 340
        },
        {
          loaihinh_tb: 'VNPT-CONG-DLYT',
          loaitb_id: 341
        },
        {
          loaihinh_tb: 'Cơ sở dữ liệu quốc gia dân cư',
          loaitb_id: 342
        },
        {
          loaihinh_tb: 'Phần mềm phổ cập giáo dục',
          loaitb_id: 344
        },
        {
          loaihinh_tb: 'VNPT ERP',
          loaitb_id: 345
        },
        {
          loaihinh_tb: 'Sàn nông sản',
          loaitb_id: 346
        },
        {
          loaihinh_tb: 'VNPT EMR',
          loaitb_id: 348
        },
        {
          loaihinh_tb: 'VNPT iSee',
          loaitb_id: 349
        },
        {
          loaihinh_tb: 'Chuyển vùng quốc tế',
          loaitb_id: 351
        },
        {
          loaihinh_tb: 'vnEdu Connect',
          loaitb_id: 352
        },
        {
          loaihinh_tb: 'Bảo hiểm hỗ trợ viện phí',
          loaitb_id: 353
        },
        {
          loaihinh_tb: 'Bảo hiểm tai nạn',
          loaitb_id: 354
        },
        {
          loaihinh_tb: 'Bảo hiểm học sinh, sinh viên',
          loaitb_id: 355
        },
        {
          loaihinh_tb: 'Bảo hiểm nhà',
          loaitb_id: 356
        },
        {
          loaihinh_tb: 'Bảo hiểm TNDS xe cơ giới',
          loaitb_id: 357
        },
        {
          loaihinh_tb: 'Voice Brandname',
          loaitb_id: 358
        },
        {
          loaihinh_tb: 'Thoại quốc tế',
          loaitb_id: 359
        },
        {
          loaihinh_tb: 'VNPT ICS',
          loaitb_id: 360
        },
        {
          loaihinh_tb: 'Microsoft 1',
          loaitb_id: 361
        },
        {
          loaihinh_tb: 'Microsoft 2',
          loaitb_id: 362
        },
        {
          loaihinh_tb: 'Microsoft 3',
          loaitb_id: 363
        },
        {
          loaihinh_tb: 'Microsoft 4',
          loaitb_id: 364
        },
        {
          loaihinh_tb: 'Microsoft 5',
          loaitb_id: 365
        },
        {
          loaihinh_tb: 'Adobe',
          loaitb_id: 366
        },
        {
          loaihinh_tb: 'Kaspersky',
          loaitb_id: 367
        },
        {
          loaihinh_tb: 'Báo hiệu quốc tế',
          loaitb_id: 2114
        },
        {
          loaihinh_tb: 'One Bussiness triển khai On-Premise',
          loaitb_id: 2115
        },
        {
          loaihinh_tb: 'VNPT SCC',
          loaitb_id: 369
        },
        {
          loaihinh_tb: 'VNPT CLOUDWAF',
          loaitb_id: 608
        },
        {
          loaihinh_tb: 'Dịch vụ Xây dựng CSDL Đất đai',
          loaitb_id: 397
        },
        {
          loaihinh_tb: 'VNPT vFarm',
          loaitb_id: 400
        },
        {
          loaihinh_tb: 'VNPT Safe Motor',
          loaitb_id: 2133
        },
        {
          loaihinh_tb: 'Thanh toán viện phí điện tử',
          loaitb_id: 2132
        },
        {
          loaihinh_tb: 'Hệ sinh thái SME',
          loaitb_id: 609
        },
        {
          loaihinh_tb: 'vnCitizens 2.0.0',
          loaitb_id: 396
        },
        {
          loaihinh_tb: 'VNPT IWS',
          loaitb_id: 2131
        },
        {
          loaihinh_tb: 'VNPT Data Lakehouse',
          loaitb_id: 402
        },
        {
          loaihinh_tb: '3G VPN',
          loaitb_id: 404
        },
        {
          loaihinh_tb: 'VNPT SmartUX',
          loaitb_id: 405
        },
        {
          loaihinh_tb: 'VNPT Cloud Object Storage',
          loaitb_id: 393
        },
        {
          loaihinh_tb: 'VNPT Cloud File Storage',
          loaitb_id: 394
        },
        {
          loaihinh_tb: 'VNPT Email SMD',
          loaitb_id: 9
        },
        {
          loaihinh_tb: 'Web Hosting',
          loaitb_id: 12
        },
        {
          loaihinh_tb: 'VNPT Colocation',
          loaitb_id: 39
        },
        {
          loaihinh_tb: 'VNPT Cloud SBĐ',
          loaitb_id: 40
        },
        {
          loaihinh_tb: 'FTP',
          loaitb_id: 41
        },
        {
          loaihinh_tb: 'Mail Plus',
          loaitb_id: 42
        },
        {
          loaihinh_tb: 'VNPT Email Secure',
          loaitb_id: 43
        },
        {
          loaihinh_tb: 'VNPT Email Quốc tế',
          loaitb_id: 44
        },
        {
          loaihinh_tb: 'Fmail SERVER',
          loaitb_id: 45
        },
        {
          loaihinh_tb: 'Vnmail',
          loaitb_id: 46
        },
        {
          loaihinh_tb: '8x83',
          loaitb_id: 47
        },
        {
          loaihinh_tb: 'PRM taxi',
          loaitb_id: 48
        },
        {
          loaihinh_tb: 'Mail VNN',
          loaitb_id: 50
        },
        {
          loaihinh_tb: 'Telehosting ',
          loaitb_id: 110
        },
        {
          loaihinh_tb: 'VNPT VPS',
          loaitb_id: 111
        },
        {
          loaihinh_tb: 'VNPT CDN',
          loaitb_id: 112
        },
        {
          loaihinh_tb: 'Mail Offline',
          loaitb_id: 124
        },
        {
          loaihinh_tb: 'VNPT Dedicated Server',
          loaitb_id: 139
        },
        {
          loaihinh_tb: 'VNPT Tracking',
          loaitb_id: 144
        },
        {
          loaihinh_tb: 'Fmail',
          loaitb_id: 150
        },
        {
          loaihinh_tb: 'My Cloud',
          loaitb_id: 152
        },
        {
          loaihinh_tb: 'VNPT SmartCloud',
          loaitb_id: 153
        },
        {
          loaihinh_tb: 'Dịch vụ 6x65, 8x88',
          loaitb_id: 155
        },
        {
          loaihinh_tb: 'Mail Hosting',
          loaitb_id: 159
        },
        {
          loaihinh_tb: 'Tổng đài ảo 3CX',
          loaitb_id: 173
        },
        {
          loaihinh_tb: 'VNPT OLAP',
          loaitb_id: 174
        },
        {
          loaihinh_tb: 'Mail Enterprise',
          loaitb_id: 183
        },
        {
          loaihinh_tb: 'Contact Center',
          loaitb_id: 184
        },
        {
          loaihinh_tb: 'VNPT MAIL Solutions',
          loaitb_id: 310
        },
        {
          loaihinh_tb: 'VNPT iStorage',
          loaitb_id: 343
        },
        {
          loaihinh_tb: 'Đặt máy chủ PMVT',
          loaitb_id: 368
        },
        {
          loaihinh_tb: 'VNPT DocumentDB for MongoDB',
          loaitb_id: 403
        },
        {
          loaihinh_tb: 'VNPT Cloud Load Balancer',
          loaitb_id: 398
        },
        {
          loaihinh_tb: 'VNPT Kubernete Service ',
          loaitb_id: 399
        }
      ],
      loaitb_id: '',
      form: {
        serial: '',
        ten_tb: 'Ủy Ban Nhân Dân Xã Đông Sơn',
        mst_tb: '',
        sogt_tb: '',
        sdt_tb: '',
        ten_kh: '',
        mst_kh: '',
        sogt_kh: '',
        sdt_kh: '',
        usb_token_info: ''
      },
      grcKetQuaTimKiem: {
        list: [],
        header: [
          {
            fieldName: 'ma_tb',
            headerText: 'Mã thuê bao',
            allowFiltering: true
          },
          {
            fieldName: 'serial',
            headerText: 'Serial Number',
            allowFiltering: true
          },
          {
            fieldName: 'ten_tb',
            headerText: 'Tên thuê bao',
            allowFiltering: true
          },
          {
            fieldName: 'ten_kh',
            headerText: 'Tên khách hàng',
            allowFiltering: true
          },
          {
            fieldName: 'ngay_ky_hd',
            headerText: 'Ngày ký HĐ',
            allowFiltering: true,
            type: 'date',
            format: 'dd/MM/y'
          },
          {
            fieldName: 'ngay_bd',
            headerText: 'Ngày bắt đầu',
            allowFiltering: true,
            type: 'date',
            format: 'dd/MM/y'
          },
          {
            fieldName: 'ngay_kt',
            headerText: 'Ngày kết thúc',
            allowFiltering: true,
            type: 'date',
            format: 'dd/MM/y'
          },
          {
            fieldName: 'so_thang',
            headerText: 'Số tháng',
            allowFiltering: true,
            type: 'Number',
            format: 'N0'
          },
          {
            fieldName: 'so_dt',
            headerText: 'Số ĐT Thuê bao',
            allowFiltering: true
          },
          {
            fieldName: 'sodt_kh',
            headerText: 'Số ĐT Khách hàng',
            allowFiltering: true
          },
          {
            fieldName: 'mst',
            headerText: 'Mã số thuế TB',
            allowFiltering: true
          },
          {
            fieldName: 'mst_kh',
            headerText: 'Mã số thuế KH',
            allowFiltering: true
          },
          {
            fieldName: 'so_gt',
            headerText: 'Số giấy tờ',
            allowFiltering: true
          },
          {
            fieldName: 'sogt_kh',
            headerText: 'Số giấy tờ KH',
            allowFiltering: true
          }
        ],
        value: {},
        isEnabled: true,
        checked: []
      }
    }
  },
  async created() {
    this.loading(true)
    try {
      Promise.all([])
      this.loading(false)
    } catch (ex) {
      this.loading(false)
    }
    this.loading(false)
  },
  computed: {},
  methods: {
    comboxChange(item) {
      console.log(item)
      this.loaitb_id = item.loaitb_id
    },
    async get_loaihinh_tb_cntt() {
      const sp_lay_ds_nghiepvu_cntt_common = await api.sp_lay_ds_nghiepvu_cntt_common(this.axios, {
        p_nghiepvu: 'LAY_DS_LOAIHINH_TB_CNTT',
        p_ds_para: JSON.stringify({
          loaihd_id: 41
        })
      })
      this.loaihinh_tb_cntt = sp_lay_ds_nghiepvu_cntt_common.data.data.result
    },
    getds_timkiem_tb: async function() {
      var input = {
        vserial: this.form.serial ? this.form.serial : '',
        vtentb: this.form.ten_tb ? this.form.ten_tb : '',
        vmsttb: this.form.mst_tb ? this.form.mst_tb : '',
        vsogttb: this.form.sogt_tb ? this.form.sogt_tb : '',
        vsodttb: this.form.sdt_tb ? this.form.sdt_tb : '',
        vtenkh: this.form.ten_kh ? this.form.ten_kh : '',
        vmstkh: this.form.mst_kh ? this.form.mst_kh : '',
        vsogtkh: this.form.sogt_kh ? this.form.sogt_kh : '',
        vsdtkh: this.form.sdt_kh ? this.form.sdt_kh : '',
        vloaitb_id: this.loaitb_id ? this.loaitb_id : ''
      }
      let data = this.GetData(await api.getds_timkiem_tb(this.axios, input))
      if (data.length > 0) {
        this.grcKetQuaTimKiem.list = data
      } else {
        this.grcKetQuaTimKiem.list = []
        this.ShowError('Không tìm thấy thông tin hợp đồng thê bao!')
      }
    },
    btnTimKiem_click: function() {
      this.loading(true)
      this.getds_timkiem_tb()
      this.loading(false)
    },
    btnChapNhan_click: function() {
      if (!this.ketqua || this.ketqua.length == 0) {
        return
      } else {
        this.$emit('xacnhan', this.ketqua)
        this.$bvModal.hide('frmTimKiemHDTB')
      }
    },
    selectedHDTB_click: function(value) {
      if (value.length > 0) {
        this.form.serial = value[0].serial ? value[0].serial : ''
        this.form.ten_tb = value[0].ten_tb ? value[0].ten_tb : ''
        this.form.mst_tb = value[0].mst ? value[0].mst : ''
        this.form.sogt_tb = value[0].so_gt ? value[0].so_gt : ''
        this.form.sdt_tb = value[0].so_dt ? value[0].so_dt : ''
        this.form.ten_kh = value[0].ten_kh ? value[0].ten_kh : ''
        this.form.mst_kh = value[0].mst_kh ? value[0].mst_kh : ''
        this.form.sogt_kh = value[0].sogt_kh ? value[0].sogt_kh : ''
        this.form.sdt_kh = value[0].sodt_kh ? value[0].sodt_kh : ''
        this.result_magd = value[0].ma_gd ? value[0].ma_gd : ''
        this.result_matb = value[0].ma_tb ? value[0].ma_tb : ''
        this.result_tenkh = value[0].ten_kh ? value[0].ten_kh : ''
        this.ketqua = value[0]
      }
    },
    khoitaodulieu: function() {
      this.result_magd = ''
      this.result_matb = ''
      this.result_tenkh = ''
      //this.loaihinh_tb_cntt = {};
      this.form = {
        serial: '',
        ten_tb: '',
        mst_tb: '',
        sogt_tb: '',
        sdt_tb: '',
        ten_kh: '',
        mst_kh: '',
        sogt_kh: '',
        sdt_kh: '',
        usb_token_info: ''
      }
      this.grcKetQuaTimKiem.list = []
      this.ketqua = {}
    },
    handleShowModal: async function() {
      this.loading(true)
      try {
        await Promise.all([this.khoitaodulieu()])
        //await this.get_loaihinh_tb_cntt()
        this.loading(false)
      } catch (ex) {
        this.loading(false)
      }
    },
    showModal: function() {
      this.$refs['popupModalNormalizeCAinformation'].show()
    },
    hideModal: function() {
      this.$refs['popupModalNormalizeCAinformation'].hide()
    },
    GetData: function(response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        // console.log(response.data.error_code);
        // this.ShowError(response.data.message);
      }
      return []
    },
    ShowSuccess: function(msg) {
      this.$toast.success(msg)
    },
    ShowError: function(msg) {
      this.$toast.error(msg)
    }
  }
}
</script>

<style scoped>
.disabled {
  cursor: not-allowed;
  color: gray !important;
}
</style>
