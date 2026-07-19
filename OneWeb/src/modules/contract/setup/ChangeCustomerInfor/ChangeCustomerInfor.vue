<template>
  <div>
    <breadcrumb :header="header" />
    <div class="list-actions-top">
      <ul class="list">
        <li v-if="tag == 'test'">
          <a href="javascript:;" @click="btnTest_click">
            <span class="icon one-file-attach"></span>Test
          </a>
        </li>
        <li>
          <a href="javascript:;" @click="btnLayTT_click">
            <span class="icon one-file-attach"></span>Lấy TT
          </a>
        </li>
        <li>
          <a href="javascript:;" @click="btnDanhSachPhieu_click">
            <span class="icon fa fa-bars"></span>Danh sách
          </a>
        </li>
        <li v-if="tag == '0'">
          <a href="javascript:;" @click="btnNhapMoi_click" :class="{disabled: !btnNhapMoi.Enabled}">
            <span class="icon one-file-plus"></span> {{ btnNhapMoi.text }}
          </a>
        </li>
        <li v-if="tag == '0'">
          <a href="javascript:;" @click="btnGhiLai_click" :class="{disabled: !btnGhiLai.Enabled}">
            <span class="icon one-save"></span> {{ btnGhiLai.text }}
          </a>
        </li>
        <li v-if="tag == '0'">
          <a href="javascript:;" @click="btnHuy_click" :class="{disabled: !btnHuy.Enabled}">
            <span class="icon one-back"></span> {{ btnHuy.text }}
          </a>
        </li>
        <li v-if="tag == '0'">
          <a href="javascript:;" @click="btnXoa_click" :class="{disabled: !btnXoa.Enabled}">
            <span class="icon one-trash"></span> {{ btnXoa.text }}
          </a>
        </li>
        <li v-if="tag=='0'">
          <a href="javascript:;" @click="btnGiaoPhieu_click" :class="{disabled: !btnGiaoPhieu.Enabled}">
              <span class="icon one-file-arrow-right1"></span> {{ btnGiaoPhieu.text }}
          </a>
        </li>
        <li v-if="tag=='1'">
          <a href="javascript:;" @click="btnDuyetPhieu_click" :class="{disabled: !btnDuyetPhieu.Enabled}">
              <span class="icon one-file-check1"></span> {{ btnDuyetPhieu.text }}
          </a>
        </li>
        <li v-if="tag=='1'">
          <a href="javascript:;" @click="btnGiuPhieu_click" :class="{disabled: !btnGiuPhieu.Enabled}">
              <span class="icon one-eye"></span> {{ btnGiuPhieu.text }}
          </a>
        </li>
        <li v-if="tag=='1'">
          <a href="javascript:;" @click="btnHuyGiuPhieu_click" :class="{disabled: !btnHuyGiuPhieu.Enabled}">
              <span class="icon one-eye-hidden"></span> {{ btnHuyGiuPhieu.text }}
          </a>
        </li>
        <li>
          <a href="javascript:;" @click="btnLichSu_click">
            <span class="icon one-file-arrow-right"></span>Lịch sử
          </a>
        </li>
        <li>
          <a href="javascript:;" @click="btnTTthuebao_click">
            <span class="icon one-file-attach"></span>TT Thuê bao
          </a>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <div :class="formWidth >= 1400 ? 'grid-stack-box' : ''">
        <div class="box-form" :class="formWidth >= 1400 ? 'col-sm-4 col-12' : 'col-sm-12 col-12'">
          <div class="legend-title">
            <span>Thông tin khách hàng</span>
          </div>
          <div class="row">
            <div class="col-sm-12 col-12" v-if="tag == 'test'">
              <div class="info-row">
                <div class="key w98">{{ txtHDKH_ID.text }} *</div>
                <div class="value">
                  <!-- <input ref="txtMaKH" type="text" class="form-control" v-bind:value="txtMaKH.value" @change="e => txtMaKH.value = e.target.value" disabled> -->
                  <div class="input-more-button">
                    <input type="text" class="form-control highlight"  :value="txtHDKH_ID.value" @change="onChangeHDKH_ID" @keypress.enter="HienThi_TT_Phieu_THEO_HDKH">
                  </div>
                </div>                  
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w98">{{ txtMaKH.text }}</div>
                <div class="value">
                  <div class="input-more-button">
                    <button class="btn" @click.prevent="ShowSearchAccount" :title="txtMaKH.value" :disabled="!txtMaKH.Enabled">
                        <span class="-ap icon-more_horiz"></span>
                    </button>
                    <input type="text" class="form-control" :disabled="!txtMaKH.Enabled" :value="txtMaKH.value" @change="v=>txtMaKH.value=v.target.value" @keyup.enter="MaKHChanged">
                  </div>
                </div>                  
              </div>
            </div>            
            <!-- <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w98">{{ txtMaTB.text }}</div>
                <div class="value">                              
                  <div class="input-more-button">
                    <button class="btn" @click.prevent="ShowSearchAccount" :title="txtMaTB.value" :disabled="!txtMaTB.Enabled">
                        <span class="-ap icon-more_horiz"></span>
                    </button>
                    <input type="text" class="form-control" :disabled="!txtMaTB.Enabled" :value="txtMaTB.value" @change="v=>txtMaTB.value=v.target.value" @keyup.enter="MaTBChanged">
                  </div>
                </div>                  
              </div>
            </div> -->
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w98">{{ txtMaGD.text }}</div>
                <div class="value">
                  <input type="text" class="form-control" :disabled="!txtMaGD.Enabled" :value="txtMaGD.value" @change="v=>txtMaGD.value=v.target.value">
                </div>                  
              </div>
            </div>
            <div class="col-sm-12 col-12" v-if="tag == '1'">
              <div class="info-row">
                <div class="key w98">{{ dtpNgayYC.text }}</div>
                <div class="value">
                  <DatePicker :format="dateFormat" :value-type="'format'" placeholder="Chọn ngày..." v-model="dtpNgayYC.value" :disabled="!dtpNgayYC.Enabled">
                  </DatePicker>
                </div>                  
              </div>
            </div>
            <div class="col-sm-12 col-12">
              <div class="info-row">
                <div class="key w98">{{ txtDiaChiKH.text }}</div>
                <div class="value" :class="{disabled: !txtDiaChiKH.Enabled}">
                    <input ref="txtDiaChiKH" type="text" class="form-control" v-bind:value="txtDiaChiKH.value" @change="e => txtDiaChiKH.value = e.target.value" disabled>
                </div>                
              </div>
            </div>
            <div class="col-sm-6 col-12" v-if="tag == '1'">
              <div class="info-row">
                <div class="key w98">{{ txtNVYC.text }}</div>
                <div class="value">
                  <input type="text" class="form-control" :disabled="!txtNVYC.Enabled" :value="txtNVYC.value" @change="v=>txtNVYC.value=v.target.value">
                </div>                  
              </div>
            </div>    
            <div class="col-sm-6 col-12" v-if="tag == '1'">
              <div class="info-row">
                <div class="key w98">{{ txtSDTNV.text }}</div>
                <div class="value">
                  <input type="text" class="form-control" :disabled="!txtSDTNV.Enabled" :value="txtSDTNV.value" @change="v=>txtSDTNV.value=v.target.value">
                </div>                  
              </div>
            </div>  
            <!-- <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w98">{{ txtNVDuyet.text }}</div>
                <div class="value">
                  <input type="text" class="form-control" :disabled="!txtNVDuyet.Enabled" :value="txtNVDuyet.value" @change="v=>txtNVDuyet.value=v.target.value">
                </div>                  
              </div>
            </div>   -->
            <!-- <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w98">{{ dtpNgayDuyet.text }}</div>
                <div class="value">
                  <DatePicker :format="dateFormat" :value-type="'format'" placeholder="Chọn ngày..." v-model="dtpNgayDuyet.value" :disabled="!dtpNgayDuyet.Enabled">
                  </DatePicker>
                </div>                  
              </div>
            </div> -->
            <!-- <div class="col-sm-12 col-12">
              <div class="info-row">
                <div class="key w98">{{ txtGhiChu.text }}</div>
                <div class="value">
                  <input type="text" class="form-control" :disabled="!txtGhiChu.Enabled" :value="txtGhiChu.value" @change="v=>txtGhiChu.value=v.target.value">
                </div>                  
              </div>
            </div> -->
            <div class="col-sm-12 col-12" v-if="tag == '0'">
              <div class="custom-row">
                <div style="width: 98px !important">Đơn vị nhận</div>
                <select2 :z-index="2000" class="select2" style="width: calc(100% - 98px) !important; padding-left: .5rem !important;" v-model="cboDonViNhan.value"
                  :options="cboDonViNhan.Items.length > 0 ? cboDonViNhan.Items.map(e => ({ id: e.donvi_id, text: e.ten_dv })) : []"
                  :disabled="!cboDonViNhan.Enabled">
                </select2>
              </div>
            </div> 
            <div class="col-sm-6 col-12" v-if="tag == '1'">
              <div class="custom-row">
                <div style="width: 98px !important">Kết quả duyệt <span class="red">*</span></div>
                <select2 :z-index="2000" class="select2" style="width: calc(100% - 98px) !important; padding-left: .5rem !important;" v-model="cboKetQuaDuyet.value"
                  :options="cboKetQuaDuyet.Items"
                  :disabled="!cboKetQuaDuyet.Enabled">
                </select2>
              </div>
            </div> 
            <div class="col-sm-6 col-12" v-if="tag == '1'">
              <div class="info-row">
                <div class="key w98">{{ txtDVYC.text }}</div>
                <div class="value">
                  <input type="text" class="form-control" :disabled="!txtDVYC.Enabled" :value="txtDVYC.value" @change="v=>txtDVYC.value=v.target.value">
                </div>                  
              </div>
            </div>
            <div class="col-sm-12 col-12" v-if="tag == '1'">
              <div class="info-row">
                <div class="key w98">{{ txtLyDoDuyet.text }} <span class="red">*</span></div>
                <div class="value">
                  <input type="text" class="form-control" :disabled="!txtLyDoDuyet.Enabled" :value="txtLyDoDuyet.value" @change="v=>txtLyDoDuyet.value=v.target.value">
                </div>                  
              </div>
            </div>
          </div>        
        </div>
          
        <div class="box-form" :class="formWidth >= 1400 ? 'col-sm-8 col-12' : 'col-sm-12 col-12'">
          <div class="legend-title">
            <span>Thông tin thay đổi</span>
          </div>
          <div class="grid-stack-box">
            <div class="box-form col-sm-6 col-12" style="width: calc(50%);">
              <div class="legend-title">
                  <span>Thông tin cũ</span>
              </div>          
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w120">{{ txtTenKH_cu.text }}</div>
                    <div class="value" :class="{disabled: !txtTenKH_cu.Enabled}">
                      <textarea ref="txtTenKH_cu" class="form-control bold" style="height: 66px;resize: none;" v-bind:value="txtTenKH_cu.value"
                        @change="e => txtTenKH_cu.value = e.target.value"></textarea>
                    </div>                  
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w120">{{ txtSoGT_cu.text }}</div>
                    <div class="value" :class="{disabled: !txtSoGT_cu.Enabled}">
                      <input ref="txtSoGT_cu" type="text" class="form-control bold" v-bind:value="txtSoGT_cu.value" @change="e => txtSoGT_cu.value = e.target.value">
                    </div>                
                  </div>
                </div>    
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w120">{{ dtpNgayCap_cu.text }}</div>
                    <div class="value" :class="{disabled: !dtpNgayCap_cu.Enabled}">
                      <DatePicker :format="dateFormat" :value-type="'format'" placeholder="Chọn ngày..." v-model="dtpNgayCap_cu.value">
                      </DatePicker>
                    </div>                  
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w120">{{ txtSoDT_cu.text }}</div>
                    <div class="value" :class="{disabled: !txtSoDT_cu.Enabled}">
                      <input type="text" class="form-control"  :value="txtSoDT_cu.value" 
                        @change="v => txtSoDT_cu.value = v.target.value" pattern="^0\d{9,10}$" 
                        title="Số điện thoại không được ít hơn 10 số và phải bắt đầu bằng 0">
                    </div>                
                  </div>
                </div>       
              </div>        
            </div>
              <div class="box-form col-sm-6 col-12" style="width: calc(50%);">
                <div class="legend-title">
                  <span>Thông tin mới</span>
                </div>
                <div class="row">
                  <div class="col-sm-12 col-12">
                    <div class="info-row">
                      <div class="key w120">{{ txtTenKH_moi.text }}</div>
                      <div class="value" :class="{disabled: !txtTenKH_moi.Enabled}">
                        <textarea ref="txtTenKH_moi" class="form-control bold" style="height: 66px;resize: none;" v-bind:value="txtTenKH_moi.value"
                          @change="e => txtTenKH_moi.value = e.target.value"></textarea>
                      </div>                  
                    </div>
                  </div>
                  <div class="col-sm-12 col-12">
                    <div class="info-row">
                      <div class="key w120">{{ txtSoGT_moi.text }}</div>
                      <div class="value" :class="{disabled: !txtSoGT_moi.Enabled}">
                        <input type="text" class="form-control" :value="txtSoGT_moi.value" 
                          @change="v=>{ txtSoGT_moi.value = v.target.value; KiemTraSoGT(); }" 
                          required pattern="^[a-zA-Z0-9\-_\s]+$" title="Số giấy tờ gồm các ký tự từ 0->9a->z.">
                      </div>                
                    </div>
                  </div>    
                  <div class="col-sm-12 col-12">
                    <div class="info-row">
                      <div class="key w120">{{ dtpNgayCap_moi.text }}</div>
                      <div class="value" :class="{disabled: !dtpNgayCap_moi.Enabled}">
                        <DatePicker :format="dateFormat" :value-type="'format'" placeholder="Chọn ngày..." v-model="dtpNgayCap_moi.value">
                        </DatePicker>
                      </div>                  
                    </div>
                  </div>
                  <div class="col-sm-12 col-12">
                    <div class="info-row">
                      <div class="key w120">{{ txtSoDT_moi.text }}</div>
                      <div class="value" :class="{disabled: !txtSoDT_moi.Enabled}">
                        <input type="text" class="form-control" :value="txtSoDT_moi.value" 
                          @change="v =>{ txtSoDT_moi.value = v.target.value; KiemTraSoDT()}" 
                          pattern="^0\d{9,10}$" title="Số điện thoại không được ít hơn 10 số và phải bắt đầu bằng 0">
                      </div>                
                    </div>
                  </div>       
                  <div class="col-sm-12 col-12">
                    <div class="info-row" :class="{disabled: !cbb_AnhGT.Enabled}">
                      <div class="key w120">{{ cbb_AnhGT.text }}</div>
                      <div class="value">
                        <input ref="fileInputImage" id="fileImage" type="file" multiple @change="onChangeFileImage($event)" accept="image/*" style="display:none" class="form-control ">
                        <div class="input-more-button">
                          <button class="btn" @click="uploadImage" :disabled="tag == '1'">
                            <span class="-ap icon-more_horiz"></span>
                          </button>
                          <div class="form-control text pad10" @contextmenu="removeImage" style="height: 120px; width: 100%; overflow-y: scroll;">
                            <div v-for="item in cbb_AnhGT.ds_hinhanh" :key="item.id" class="value k marb10">
                              <input type="checkbox" v-model="item.check" class="check" :disabled="tag == '1'">
                              <img class="image_gt" title="Click here" @click="openImage(item)" :src="item.url">
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>       
              </div>
          </div>
        </div>
      </div>
      <div class="box-form" v-if="tag=='0'">
        <div class="legend-title">Danh sách phiếu yêu cầu</div>
        <div class="row"> 
          <DataGrid class="table-result table-gachle" :columns="grdPhieuYC.header" :allowResizing="true"
            :dataSource="grdPhieuYC.list" :enable-paging-server="false" :allowPaging="true"
            :showFilter="true" :showColumnCheckbox="false" :enabledSelectFirstRow="true" ref="grdPhieuYC" 
            @selectedItemsChanged="grdPhieuYC_SelectionChanged"/>
        </div>
      </div>
      <div class="box-form" v-if="tag=='1'">
        <div class="legend-title">Danh sách phiếu cần phê duyệt</div>
        <div class="row"> 
          <DataGrid class="table-result table-gachle" :columns="grdPhieuCanXN.header" :allowResizing="true"
            :dataSource="grdPhieuCanXN.list" :enable-paging-server="false" :allowPaging="true"
            :showFilter="true" :showColumnCheckbox="false" :enabledSelectFirstRow="true" ref="grdPhieuCanXN" 
            @selectedItemsChanged="grdPhieuCanXN_SelectionChanged"/>
        </div>
      </div>
    </div>
    <b-modal id="popupComponents" size="huge" :title="popupComponentHeader" :hide-header="popupComponentHeader?false:true"
      hide-footer modal-class="modal-custom" body-class="modal-body p-0">
      <component :is="popupComponent" ref="generalPopup" :dulieu="popupComponentData" v-bind="popupComponentAttr"
        isPopup="true" v-on="popupComponentEvts"></component>
    </b-modal>
  </div>
</template>
<style src="./ChangeCustomerInfor.scss"></style>
<style>
.image_gt {
  width:64px;
  height:64px;
}
.k{
  float: left !important;
}
.custom-row {
  display: flex !important;
  flex-direction: row !important;
}

.pad2 {
  padding-left: .5rem !important;
  padding-right: .5rem !important;
}

.disabled {
  cursor: not-allowed;
  color: #666 !important;
}
.mx-datepicker {
  width: 100% !important;
}

.mx-input.disabled,
.mx-input:disabled {
  color: #666 !important;
}

.modal-body .page-content {
  position: unset;
}

@media (min-width: 992px) {
  .modal .modal-huge {
    max-width: 95% !important;
    width: 100% !important;
  }
}
@media (min-width: 576px) {
  .modal .modal-huge {
    max-width: 95% !important;
    width: 100% !important;
  }
}
</style>
<script>
  import breadcrumb from "@/components/breadcrumb";
  import moment from 'moment'
  import DatePicker from 'vue2-datepicker'
  import 'vue2-datepicker/locale/vi.js'
  import 'vue2-datepicker/index.css'
  import DataGrid from "@/components/Controls/DataGrid";
  import api from './API.js'
  import UploadEProfileAPI from "@/modules/contract/profile/UploadEProfile/UploadEProfileAPI";
  import SuppliesAPI from '@/modules/admin/category/Supplies/SuppliesAPI.js'

  export default {
    name: 'ChangeCustomerInfor',
    components: {
      breadcrumb,
      DatePicker,
      DataGrid
    },
    props: ['dulieu'],
    data: () => ({
      header: {
        title: "Thay đổi thông tin khách hàng",
        list: [
          {
            name: 'Thay đổi thông tin khách hàng',
            link: { 
              name: 'Ui.buttons',
              active: true 
            }
          }
        ],
      },
      popupComponentName: '',
      popupComponentHeader: '',
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {},
      formWidth: 0,
      dateFormat: 'DD/MM/YYYY hh:mm:ss',
      tt_nd: {
        phanvung_id: 0, //this.$root.token.getPhanVungID(),
        donvi_id: 0, //this.$root.token.getDonviID(),
        nhanvien_id: 0, //this.$root.token.getNhanVienID(),
        ip_cn: "",
        may_cn: "",
        nguoi_cn: "",
      },
      btnNhapMoi: {text: 'Nhập mới', Visible: true, Enabled: true},
      btnGhiLai: {text: 'Ghi lại', Visible: true, Enabled: true},
      btnHuy: {text: 'Hủy', Visible: true, Enabled: true},
      btnXoa: {text: 'Xóa', Visible: true, Enabled: true},
      btnGiaoPhieu: {text: 'Giao phiếu', Visible: true, Enabled: true},
      btnDuyetPhieu: {text: 'Xác nhận', Visible: true, Enabled: true},
      btnGiuPhieu: {text: 'Giữ phiếu', Visible: true, Enabled: true},
      btnHuyGiuPhieu: {text: 'Hủy giữ phiếu', Visible: true, Enabled: true},
      tag: 0,
      tdtt_hdtb_id: 0,
      hdkh_id: 0,
      phieu_id: 0,
      valid_sgt: true,
      valid_sdt: true,
      cboDonViNhan: {value: 0, Items: [], Enabled: true}, //1005 - Xác nhận yêu cầu thay đổi TTKH)
      cboKetQuaDuyet: { text: 'Kết quả duyệt', value: 0, Items: [
        { id: 0, text: 'Từ chối duyệt' },
        { id: 1, text: 'Duyệt yêu cầu thay đổi' },
      ], Enabled: true},
      txtHDKH_ID: {text: 'HĐ khách hàng ID', value: '', Enabled: false},
      txtMaKH: {text: 'Mã KH', value: '', Enabled: false},
      txtMaTB: {text: 'Số máy/Acc', value: '', Enabled: false},
      txtMaGD: {text: 'Mã GD', value: '', Enabled: false},
      dtpNgayYC: {text: 'TG yêu cầu', value: '', Enabled: false},
      txtDiaChiKH: {text: 'Địa chỉ', value: '', Enabled: false},
      txtNVYC: {text: 'NV yêu cầu', value: '', Enabled: false},
      txtSDTNV: {text: 'SDT NVYC', value: '', Enabled: false},
      txtNVDuyet: {text: 'NV duyệt', value: '', Enabled: false},
      dtpNgayDuyet: {text: 'TG duyệt', value: '', Enabled: false},
      txtDVYC: {text: 'Đơn vị YC', value: '', Enabled: false},
      txtLyDoDuyet: {text: 'Lý do duyệt', value: '', Enabled: true},
      txtGhiChu: {text: 'Ghi chú', value: '', Enabled: false},
      // TT cu
      txtTenKH_cu: {text: 'Tên khách hàng', value: '', Enabled: false},
      txtSoGT_cu: {text: 'Số giấy tờ', value: '', Enabled: false},
      dtpNgayCap_cu: {text: 'Ngày cấp', value: '', Enabled: false},
      txtSoDT_cu: {text: 'Số điện thoại', value: '', Enabled: false},
      // TT moi
      txtTenKH_moi: {text: 'Tên khách hàng', value: '', Enabled: true},
      txtSoGT_moi: {text: 'Số giấy tờ', value: '', Enabled: true},
      dtpNgayCap_moi: {text: 'Ngày cấp', value: '', Enabled: true},
      txtSoDT_moi: {text: 'Số điện thoại', value: '', Enabled: true},
      cbb_AnhGT: {text: 'Ảnh giấy tờ', ds_hinhanh: [], Enabled: true},
      grdPhieuYC: {
        list: [],
        header: [
          // {
          //   fieldName: "tdtt_hdtb_id",
          //   headerText: "TDTT ID",
          //   allowFiltering: true,
          // },
          {
            fieldName: "ma_kh",
            headerText: "Mã KH",
            allowFiltering: true,
          },
          {
            fieldName: "ten_kh",
            headerText: "Tên KH mới",
            allowFiltering: true,
          },
          {
            fieldName: "ten_kh_cu",
            headerText: "Tên KH cũ",
            allowFiltering: true,
          },
          {
            fieldName: "so_gt",
            headerText: "Số giấy tờ mới",
            allowFiltering: true,
          },
          {
            fieldName: "so_gt_cu",
            headerText: "Số giấy tờ cũ",
            allowFiltering: true,
          },
          {
            fieldName: "so_dt",
            headerText: "Số điện thoại mới",
            allowFiltering: true,
          },
          {
            fieldName: "so_dt_cu",
            headerText: "Số điện thoại cũ",
            allowFiltering: true,
          },
          // {
          //   fieldName: "action",
          //   headerText: "",
          //   textAlign: "center",
          //   width: 70,
          //   template: () => {
          //     return {
          //       template: {
          //         template: `<div>
          //             <button type="button" class="btn btn-danger" @click="onbtnXoa_Click">
          //               Xóa
          //             </button>
          //           </div>`,
          //         data: function () {
          //           return {
          //             data: {},
          //           };
          //         },
          //         methods: {
          //           onbtnXoa_Click() {
          //             this.$parent.$parent.$parent.btnXoa_click(this.data);
          //           },
          //         },
          //       },
          //     };
          //   },
          // },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      grdPhieuCanXN: {
        list: [],
        header: [
          {
            fieldName: "phieu_id",
            headerText: "Phiếu ID",
            allowFiltering: true,
          },
          {
            fieldName: "ma_gd",
            headerText: "Mã giao dịch",
            allowFiltering: true,
          },
          {
            fieldName: "ten_kh",
            headerText: "Tên KH mới",
            allowFiltering: true,
          },
          {
            fieldName: "ten_kh_cu",
            headerText: "Tên KH cũ",
            allowFiltering: true,
          },
          {
            fieldName: "so_gt",
            headerText: "Số giấy tờ mới",
            allowFiltering: true,
          },
          {
            fieldName: "so_gt_cu",
            headerText: "Số giấy tờ cũ",
            allowFiltering: true,
          },
          {
            fieldName: "so_dt",
            headerText: "Số điện thoại mới",
            allowFiltering: true,
          },
          {
            fieldName: "so_dt_cu",
            headerText: "Số điện thoại cũ",
            allowFiltering: true,
          },
          {
            fieldName: "ngay_yc",
            headerText: "Ngày yêu cầu",
            allowFiltering: true,
          }, 
          {
            fieldName: "ngay_gui",
            headerText: "Ngày gửi",
            allowFiltering: true,
          },
          {
            fieldName: "nguoi_gui",
            headerText: "Người gửi",
            width: '500px',
            allowFiltering: true,
          }
          // {
          //   fieldName: "action",
          //   headerText: "",
          //   textAlign: "center",
          //   width: 70,
          //   template: () => {
          //     return {
          //       template: {
          //         template: `<div>
          //             <button type="button" class="btn btn-danger" @click="onbtnXoa_Click">
          //               Xóa
          //             </button>
          //           </div>`,
          //         data: function () {
          //           return {
          //             data: {},
          //           };
          //         },
          //         methods: {
          //           onbtnXoa_Click() {
          //             this.$parent.$parent.$parent.btnXoa_click(this.data);
          //           },
          //         },
          //       },
          //     };
          //   },
          // },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
    }),
    created: async function () {
        if (this.$route.params && this.$route.params.tag !== null && this.$route.params.tag !== undefined) {
          this.tag = this.$route.params.tag
        }
        if (this.$route.query && this.$route.query.tag !== null && this.$route.query.tag !== undefined) {
          this.tag = this.$route.query.tag
        }
        if (this.dulieu && this.dulieu.tag !== null && this.dulieu.tag !== undefined) {
          this.tag = this.dulieu.tag
        }
        if (this.tag == '1') {
          this.header.title = 'Phê duyệt phiếu thay đổi thông tin khách hàng'
        } else {
          this.header.title = 'Thay đổi thông tin khách hàng'
        }
        this.SetButton(0)
    },
    watch: {
      'txtTenKH_moi.value'(val) {
        if (val) this.txtTenKH_moi.value = val.trim()
      },
      'txtSoGT_moi.value'(val) {
        if (val) this.txtSoGT_moi.value = val.trim()
      },
      'txtSoDT_moi.value'(val) {
        if (val) this.txtSoDT_moi.value = val.trim()
      },
    },
    computed: {
      formAllowFileType() {
        return ".pdf,.jpg,.jpeg,.png";
      },
    },
    mounted () {
      document.addEventListener('contextmenu', event => event.preventDefault())
      this.formWidth = Number(window.innerWidth)
      this.formLoad()
    },
    methods: {
      formLoad: async function () {
        this.tt_nd = {
          phanvung_id: Number(await this.$root.token.getPhanVungID()),
          donvi_id: Number(await this.$root.token.getDonViID()),
          nhanvien_id: Number(await this.$root.token.getNhanVienID()),
          ip_cn: await this.$root.token.getIP(),
          may_cn: await this.$root.token.getMachine(),
          nguoi_cn: await this.$root.token.getUserName(),
        };
        console.log('tt_nd', this.tt_nd)
        await this.getDsDonViDaGan()
        this.cboDonViNhan.value = this.cboDonViNhan.Items && this.cboDonViNhan.Items.length > 0 ? this.cboDonViNhan.Items[0]['donvi_id'] : 0
        this.hdkh_id = (this.dulieu && this.dulieu.hdkh_id) ? this.dulieu.hdkh_id : (this.$route.query.hdkh_id?this.$route.query.hdkh_id:0)
        if(this.hdkh_id) this.txtHDKH_ID.value = this.hdkh_id;
        if (this.tag == '0') await this.HienThi_TT_Phieu_THEO_HDKH()
        if (this.tag == '1') { 
          var p_nhanvien_id = this.tt_nd.nhanvien_id
          this.grdPhieuCanXN.list = await this.lay_thongtin_phieu_can_xacnhan(p_nhanvien_id)
        }
        console.log('ChangeCustomerInfor formLoad > dulieu, hdkh_id', this.dulieu, this.hdkh_id)
      },
      getDsDonViDaGan: async function() {
        this.cboDonViNhan.Items = [];
        var loaidv_id = 1005
        await api.sp_lay_ds_donvi_ldv(this.axios, loaidv_id).then(res => {
          this.cboDonViNhan.Items = res.data.data ? res.data.data : [];
        }).catch(err =>{
          this.cboDonViNhan.Items = []
        })
      },
      //Tao moi PYC   
      HienThi_TT_Phieu_THEO_HDKH: async function() {
        try {
          this.grdPhieuYC.list = []
          var ds = await this.lay_tt_phieu_theo_hdkhid(this.hdkh_id)
          if (ds && ds.length > 0) {
            if (ds[0]['tdtt_hdtb_id'] && ds[0]['tdtt_hdtb_id'] > 0) {
              this.grdPhieuYC.list = ds
              this.SetButton(3) //edit
            } else {
              this.grdPhieuYC.list = []
              this.SetButton(1) //new
            }       
            this.hdtb_id = ds[0]['hdtb_id']
            this.tdtt_hdtb_id = ds[0]['tdtt_hdtb_id']
            this.txtMaKH.value = ds[0]['ma_kh']
            this.txtMaGD.value = ds[0]['ma_gd']
            this.txtDiaChiKH.value = ds[0]['diachi_kh']
            this.txtTenKH_cu.value = ds[0]['ten_kh_cu']
            this.txtTenKH_moi.value = ds[0]['ten_kh'] ? ds[0]['ten_kh'] : ds[0]['ten_kh_cu']
            this.txtSoGT_cu.value = ds[0]['so_gt_cu']
            this.txtSoGT_moi.value = ds[0]['so_gt'] ? ds[0]['so_gt'] : ds[0]['so_gt_cu']
            this.dtpNgayCap_cu.value = ds[0]['ngaycap_cu']
            this.dtpNgayCap_moi.value = ds[0]['ngaycap'] ? ds[0]['ngaycap'] : ds[0]['ngaycap_cu']
            this.txtSoDT_cu.value = ds[0]['so_dt_cu']
            this.txtSoDT_moi.value = ds[0]['so_dt'] ? ds[0]['so_dt'] : ds[0]['so_dt_cu']
          } else {
            this.grdPhieuYC.list = []
            this.SetButton(1) //new
          }    
        } catch (e) {

        }
      },        
      grdPhieuYC_SelectionChanged: async function (item) {
        if (this.tag == '0' && item && item.length > 0) {
          this.hdtb_id = item[0]['hdtb_id']
          this.tdtt_hdtb_id = item[0]['tdtt_hdtb_id']
          this.txtMaKH.value = item[0]['ma_kh']
          this.txtMaGD.value = item[0]['ma_gd']
          this.hdkh_id = item[0]['hdkh_id'] && item[0]['hdkh_id'] > 0 ? item[0]['hdkh_id'] : this.hdkh_id
          this.txtDiaChiKH.value = item[0]['diachi_kh']
          this.txtTenKH_cu.value = item[0]['ten_kh_cu']
          this.txtTenKH_moi.value = item[0]['ten_kh']
          this.txtSoGT_cu.value = item[0]['so_gt_cu']
          this.txtSoGT_moi.value = item[0]['so_gt']
          this.dtpNgayCap_cu.value = item[0]['ngaycap_cu']
          this.dtpNgayCap_moi.value = item[0]['ngaycap'] ? item[0]['ngaycap'] : item[0]['ngaycap_cu']
          this.txtSoDT_cu.value = item[0]['so_dt_cu']
          this.txtSoDT_moi.value = item[0]['so_dt']

          var ds_hinhanh = await this.lay_thong_tin_file_upload(item[0]['tdtt_hdtb_id'])
          this.cbb_AnhGT.ds_hinhanh = ds_hinhanh && ds_hinhanh.length > 0 ? ds_hinhanh.map(e => ({...e, check: false})) : []
          this.SetButton(3)
        }      
      },       
      grdPhieuCanXN_SelectionChanged: async function (item) {
        if (this.tag == '1' && item && item.length > 0) {
          this.phieu_id = item[0]['phieu_id']
          this.hdtb_id = item[0]['hdtb_id']
          this.tdtt_hdtb_id = item[0]['tdtt_hdtb_id']
          this.hdkh_id = item[0]['hdkh_id'] && item[0]['hdkh_id'] > 0 ? item[0]['hdkh_id'] : this.hdkh_id
          this.txtMaKH.value = item[0]['ma_kh']
          this.txtMaGD.value = item[0]['ma_gd']
          this.dtpNgayYC.value = item[0]['ngay_yc'] && moment(item[0]['ngay_yc'], 'DD/MM/YYYY hh:mm:ss').isValid() ? item[0]['ngay_yc'] : moment(item[0]['ngay_yc']).format('DD/MM/YYYY hh:mm:ss')
          this.txtDiaChiKH.value = item[0]['diachi_kh']
          this.txtNVYC.value = item[0]['ten_nv']
          this.txtSDTNV.value = item[0]['so_dt_nv']
          this.txtDVYC.value = item[0]['ten_dv']

          this.txtTenKH_cu.value = item[0]['ten_kh_cu']
          this.txtTenKH_moi.value = item[0]['ten_kh']
          this.txtSoGT_cu.value = item[0]['so_gt_cu']
          this.txtSoGT_moi.value = item[0]['so_gt']
          this.txtSoDT_cu.value = item[0]['so_dt_cu']
          this.txtSoDT_moi.value = item[0]['so_dt']
          
          var ds_hinhanh = await this.lay_thong_tin_file_upload(item[0]['tdtt_hdtb_id'])
          if(ds_hinhanh.length > 0){
            var list_file = ''
            for (let [index, item] of ds_hinhanh.entries()) {
              list_file += (index > 0 ? ',' : '') + item.url
            }
            var ds_hinhhanh_temp = []
            let resultImageMoi = await this.link_view_file(list_file)
            for(let i=0;i<resultImageMoi.length;i++){
              ds_hinhhanh_temp.push({
                name: resultImageMoi[i].file_name,
                url: resultImageMoi[i].file_url,
                check: false
              })
            }
            this.cbb_AnhGT.ds_hinhanh = ds_hinhhanh_temp.map((x, i)=>Object.assign(x, {id: i+1}))
          }
          else this.cbb_AnhGT.ds_hinhanh = []
          this.SetButton(3)
        }      
      },  
      onChangeHDKH_ID: function (e) {
        console.log(e.target.value)
        this.txtHDKH_ID.value = e.target.value
        this.hdkh_id = Number(e.target.value)
      },
      ShowSearchAccount() {
        this.popupComponent = () => import('@/modules/search/subscriber/SearchAccount')
        this.popupComponentName = 'SearchAccount'
        this.Popup('popupComponents')
        this.popupComponentEvts = {"form-close": this.popupClosed}
      },
      MaKHChanged: async function (e) {
        if (e) this.txtMaKH.value = e.target.value
      },
      MaTBChanged: async function (e) {
        if (e) this.txtMaTB.value = e.target.value
      },
      lay_tt_phieu_theo_hdkhid: async function (p_hdkh_id) {
        try {
          let response = await api.sp_lay_thongtin_phieu_xacnhan_td(this.axios, p_hdkh_id);
          if (
            response &&
            response.data &&
            response.data.error_code == "BSS-00000000"
          ) {
            var data = response.data.data
            if (data instanceof Array) return data
            if (data instanceof Object) return [data]
          } else if (response.data.error_code == "BSS-00000204") {
            // this.ShowError("Không tìm thấy thông tin khách hàng");
            return []
          } else {
            this.ShowError("Lỗi lấy thông tin khách hàng: " + response.data.message_detail);
            return [];
          }
        } catch (ex) {
          console.log(ex);
          if (ex.data && ex.data.message_detail) {
            this.ShowError("Lỗi lấy thông tin khách hàng: " + ex.data.message_detail);
          } else {
            this.ShowError("Lỗi lấy thông tin khách hàng: lỗi không xác định");// + JSON.stringify(ex));
          }
          
        }
      },    
      lay_thong_tin_file_upload: async function (p_tdtt_hdtb_id) {
        try {
          let response = await api.sp_lay_thong_tin_file_upload(this.axios, p_tdtt_hdtb_id);
          if (
            response &&
            response.data &&
            response.data.error_code == "BSS-00000000"
          ) {
            var data = response.data.data
            if (data instanceof Array) return data
            if (data instanceof Object) return [data]
          } else if (response.data.error_code == "BSS-00000204") {
            // this.ShowError("Không tìm thấy thông tin file upload");
            return []
          } else {
            this.ShowError("Lỗi lấy thông tin file upload: " + response.data.message_detail);
            return [];
          }
        } catch (ex) {
          console.log(ex);
          if (ex.data && ex.data.message_detail) {
            this.ShowError("Lỗi lấy thông tin file upload: " + ex.data.message_detail);
          } else {
            this.ShowError("Lỗi lấy thông tin file upload: lỗi không xác đinh");// + JSON.stringify(ex));
          }
          
        }
      }, 
      CAPNHAT_FILEHOSO: async function (input, type) {        
        await UploadEProfileAPI.cap_nhat_file_hoso(this.axios, input)
        .then((response) => {
          this.loading(false);
          if (response.data.error_code == "BSS-00000000") {
            var message = type == 0 ? "Xóa file thành công!" : (type == 1 ? "Thêm mới file thành công!" : "Cập nhật file thành công!")
            this.$toast.success(message);
          } else this.$toast.error(response.data.request_id);
        })
        .catch(function(e) {
          this.loading(false);
          this.$toast.error((type == 0 ? "Xóa" : (type == 1 ? "Thêm mới" : "Cập nhật")) + " file không thành công! Vui lòng thử lại hoặc liên hệ quản trị viên.");
        });        
      },
      CAPNHAT: async function (data) {
        try {
          var input = { 
            p_kieu: data.p_kieu, 
            p_tdtt_hdtb_id: data.p_tdtt_hdtb_id, 
            p_hdkh_id: data.p_hdkh_id, 
            p_json_phieuyc: data.p_json_phieuyc 
          }
          var self = this
          let response = await api.fn_capnhat_phieuyc_tdtt_kh(this.axios, input);
          if (
            response &&
            response.data &&
            response.data.error_code == "BSS-00000000"
          ) {
            if (data.p_kieu == 0) this.ShowSuccess('Xóa phiếu yêu cầu thành công!')
            if (data.p_kieu == 1 && data.p_tdtt_hdtb_id == 0) this.ShowSuccess('Tạo mới phiếu yêu cầu thành công!')
            if (data.p_kieu == 2 && data.p_tdtt_hdtb_id > 0) this.ShowSuccess('Ghi lại phiếu yêu cầu thành công!')            
            await self.btnLayTT_click()
          } else {
            this.ShowError("Lỗi: " + response.data?.message_detail || response.data?.message);
          }
        } catch (ex) {
          console.log(ex);
          this.ShowError("Lỗi: " + ex.data?.message_detail || ex.data?.message || JSON.stringify(ex));
        }
      },
      fn_gui_xacnhan_td_ttkh: async function (data) {
        try {
          var input = { 
            p_hdkh_id: data.p_hdkh_id, 
            p_tdtt_hdtb_id: data.p_tdtt_hdtb_id, 
            p_json_thongtin: data.p_json_thongtin, 
          }
          var self = this
          let response = await api.fn_gui_xacnhan_td_ttkh(this.axios, input);
          if (
            response &&
            response.data &&
            response.data.error_code == "BSS-00000000"
          ) {
            this.$toast.success('Gửi yêu cầu xác nhận thành công!')
            await self.btnLayTT_click()
          } else {
            this.ShowError("Lỗi: " + response.data?.message_detail || response.data?.message);
          }
        } catch (ex) {
          console.log(ex);
          if (ex.data && ex.data.message_detail) {
            this.ShowError("Lỗi gửi yêu cầu xác nhận: " + ex.data.message_detail);
          } else {
            this.ShowError("Lỗi gửi yêu cầu xác nhận: lỗi không xác định");// + JSON.stringify(ex));
          }
          
        }
      },
      handleFileInputClick() {
        // Gọi hàm UploadFileHoSo() ngay cả khi không có sự thay đổi trên trường input file
        this.UploadFileHoSo();
      },
      async UploadFileHoSo() {        
        let formData = new FormData();
        if (!this.$refs.files.files || this.$refs.files.files.length == 0) return

        const lastDot = this.$refs.files.files[0].name.lastIndexOf(".");
        const vfile_ext = this.$refs.files.files[0].name.substring(lastDot + 1);        
        for (var file of this.$refs.files.files) {
          formData.append("files", file);
        }
        this.loading(true);
        await UploadEProfileAPI.upload_file_hoso(this.axios, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
          .then((response) => {
            this.loading(false);
            console.log('upload_file_hoso',response.data.data)
            if (
              response.data.error_code &&
              response.data.error_code === "BSS-00000000"
            ) {
              this.cbb_AnhGT.value1 = response.data.data[0];              
            }
          })
          
          ;
        this.loading(false);
      },
      uploadImage(){
        this.$refs.fileInputImage.value=null
        document.getElementById("fileImage").click()
      },
      openImage(item){
        window.open(item.url, '_blank');
      },
      async onChangeFileImage(event){
        if (!this.hdtb_id) {
          this.$toast.error('Không có hợp đồng thuê bao ID, hãy liên hệ admin!')
          return
        }
        let dsFiles = event.target.files
        let formData = new FormData();
        let sohinh = 0;
        for(var file of dsFiles) {
          formData.append('files', file);
          sohinh++;
        }
        let result = await this.upload_file(formData, { headers: { 'Content-Type': 'multipart/form-data'}})
        if(result.length > 0){
          var ds_hinhhanh_temp = []
          let resultImageMoi = await this.link_view_file(result.join(','))
          for(let i=0;i<resultImageMoi.length;i++){
            ds_hinhhanh_temp.push({
              name: resultImageMoi[i].file_name,
              url: resultImageMoi[i].file_url,
              check: false
            })
          }
          var index = this.cbb_AnhGT.ds_hinhanh.length
          this.cbb_AnhGT.ds_hinhanh = this.cbb_AnhGT.ds_hinhanh.concat(ds_hinhhanh_temp.map((x, i)=>Object.assign(x, {id: index+i+1})))

          //cập nhật lại hình ảnh vào khách hàng          
          // Cap nhat file 
          var pdata = {};
          var vhstb_clob = [{
            "THUEBAO_ID": 0,
            "THANHTOAN_ID": 0,
            "MA_TB": '',
            "HDTB_ID": this.hdtb_id,
            "MA_KH": this.txtMaKH.value,
            "BAOHONG_ID": 0,
          }]
          pdata.vhoso_id = -1;
          pdata.vloaifile_id = 1;
          pdata.vurl = result.join(',');
          pdata.vghichu = '';
          pdata.vfile_cn = 0;
          pdata.vkieu = 1;
          pdata.vkieufile_id = 1;
          pdata.vfile_clob = 0;
          pdata.thuebao_id = 0;
          pdata.vhstb_clob = JSON.stringify(vhstb_clob)      
          await this.CAPNHAT_FILEHOSO(pdata, 1)  
        }
      },
      async removeImage(){
        let ds_check = this.cbb_AnhGT.ds_hinhanh.filter(x=>x.check==true)
        let ds_uncheck = this.cbb_AnhGT.ds_hinhanh.filter(x=>x.check==false)
        if(ds_check.length > 0){
          let comfirm = await this.confirm('Bạn thật sự muốn xóa '+ds_check.length+' được chọn','Thông báo')
          if(comfirm==0) return
          this.cbb_AnhGT.ds_hinhanh = ds_uncheck.map((x, index)=>Object.assign(x, {id:index+1}))
          var vfile_clob = [];
          ds_check.forEach((file) => {
            vfile_clob.push({
              FILE_ID: file.id,
            });
          });
          var pdata = {
            vkieu: 3,
            vhoso_id: 0,
            url: "",
            vghichu: "",
            vfile_cn: 0,
            vloaifile_id: 0,
            vfile_clob: JSON.stringify(vfile_clob),
          };
          await this.CAPNHAT_FILEHOSO(pdata, 0)
        }
      },
      async upload_file(data, header){
        try{
          this.loading(true)
          let response = await SuppliesAPI.upload_file(this.axios, data, header)
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
      async link_view_file(list_file){
        try{
          this.loading(true)
          let response = await SuppliesAPI.link_view_file(this.axios, list_file)
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
      async confirm(message, title){
        try{
          let result = await this.$confirm(message,title,{
            confirmButtonText: 'Đồng ý',
            cancelButtonText: 'Hủy'
          })
          return 1
        }catch(e){
          return 0
        }
      },
      //Phe duyet PYC
      lay_thongtin_phieu_can_xacnhan: async function (p_nhanvien_id) {
        try {
          let response = await api.sp_lay_thongtin_phieu_can_xacnhan_nv(this.axios, p_nhanvien_id);
          if (
            response &&
            response.data &&
            response.data.error_code == "BSS-00000000"
          ) {
            var data = response.data.data
            if (data instanceof Array) return data
            if (data instanceof Object) return [data]
          } else if (response.data.error_code == "BSS-00000204") {
            return [];
          } else {
            this.ShowError("Lỗi lấy thông tin phiếu: " + response.data?.message_detail || response.data?.message);
            return [];
          }
        } catch (ex) {
          console.log(ex);
          this.ShowError("Lỗi lấy thông tin phiếu: " + ex.data?.message_detail || ex.data?.message || "lỗi không xác định");//JSON.stringify(ex));
          return [];
        }
      },   
      fn_pheduyet_tdtt_kh: async function (data) {
        try {
          var input = {
            p_phieu_id: data.p_phieu_id, 
            p_hdkh_id: data.p_hdkh_id, 
            p_tdtt_hdtb_id: data.p_tdtt_hdtb_id,
            p_kq_duyet: data.p_kq_duyet,
            p_noidung_duyet: data.p_noidung_duyet,
            p_nhanvien_id: data.p_nhanvien_id,
          }
          var self = this
          let response = await api.fn_pheduyet_tdtt_kh(this.axios, input);
          if (
            response &&
            response.data &&
            response.data.error_code == "BSS-00000000"
          ) {
            this.ShowSuccess('Duyệt phiếu thành công')
            await self.btnLayTT_click()
          } else {
            this.ShowError("Lỗi: " + response.data?.message_detail || response.data?.message);
          }
        } catch (ex) {
          console.log(ex);
          if (ex.data && ex.data.message_detail) {
            this.ShowError("Lỗi Duyệt phiếu: " + ex.data.message_detail);
          } else {
            this.ShowError("Lỗi Duyệt phiếu: lỗi không xác định");// + JSON.stringify(ex));
          }
          
        }
      },
      kiemtra_capnhat: function () {
        // if (!this.hdkh_id || this.hdkh_id == 0) {
        //   this.ShowError("Không có HDKH ID");
        //   return false
        // }

        if (!this.txtTenKH_moi.value) {
          this.ShowError("Tên khách hàng mới không được để trống");
          this.$refs['txtTenKH_moi'].focus()
          return false
        }
  
        if (!this.txtSoGT_moi.value) {
          this.ShowError("Số giấy tờ mới không được để trống");
          this.$refs['txtSoGT_moi'].focus()
          return false
        }

        if (!this.valid_sgt) {
          this.ShowError("Số giấy tờ mới không hợp lệ");
          this.$refs['txtSoGT_moi'].focus()
          return false
        }
  
        if (!this.txtSoDT_moi.value) {
          this.ShowError("Số điện thoại mới không được để trống");
          this.$refs['txtSoDT_moi'].focus()
          return false
        }
        
        if (!this.valid_sdt) {
          this.ShowError("Số điện thoại mới không hợp lệ");
          this.$refs['txtSoDT_moi'].focus()
          return false
        }

        if (this.txtTenKH_moi.value != this.txtTenKH_cu.value || this.txtSoGT_moi.value != this.txtSoGT_cu.value) {
          if (!this.cbb_AnhGT.ds_hinhanh || this.cbb_AnhGT.ds_hinhanh.length == 0) {
            this.ShowError("Thay đổi tên khách hàng hoặc số giấy tờ, bạn phải up hồ sơ liên quan!");
            return false
          }          
        }

        return true
      },
      KiemTraSoGT() {
        // khachhang.sogt
        this.valid_sgt = true
        var result = true
        if (this.txtSoGT_moi.value && this.txtSoGT_moi.value.trim() != '') {
          // CUONGNM thêm .:\-\/   theo yêu cầu https://cntt.vnpt.vn/browse/IT360-223618
          if (!/^[a-zA-Z0-9.:\-\/]*$/.test(this.txtSoGT_moi.value)) {
            //&& !Char.IsControl(e.KeyChar))
            this.$toast.error('Số giấy tờ không hợp lệ.Số giấy tờ gồm các ký tự từ 0->9a->z . : - /. Hãy kiểm tra lại')
            result = false
            this.valid_sgt = result
            return
          }
        }
      },
      KiemTraSoDT() {
        // khachhang.sodt
        this.valid_sdt = true
        var result = true        
        if (this.txtSoDT_moi.value && this.txtSoDT_moi.value.trim() != '') {
          // CUONGNM thêm .:\-\/   theo yêu cầu https://cntt.vnpt.vn/browse/IT360-223618
          if (!/^[0-9]*$/.test(this.txtSoDT_moi.value)) {
            //&& !Char.IsControl(e.KeyChar))
            this.$toast.error('Số điện thoại không hợp lệ. Số điện thoại gồm các ký tự từ 0->9. Hãy kiểm tra lại')
            result = false
            this.valid_sdt = result
            return false
          }
        }
        if (this.txtSoDT_moi.value.trim().length != 10 || this.txtSoDT_moi.value.toString().substring(0, 1) != '0') {
          this.$toast.error('Số điện thoại không được ít hơn 10 số và phải bắt đầu bằng 0!')
          result = false
          this.valid_sdt = result
          return false
        }
      },    
      ShowSuccess: function (msg) {
        this.$toast.success(msg);
      },
      ShowError: function (msg) {
        this.$toast.error(msg);
      },
      Popup(modalId) {
        this.$bvModal.show(modalId);
      },
      ClosePopup(modalId) {
        this.$bvModal.hide(modalId);
        this.popupComponent = null;
      },
      async popupClosed(val) {
        if (!val) {
          this.ClosePopup("popupComponents");
          return;
        }
        this.loading(true);
        try {
          switch (this.popupComponentName) {
            case 'SearchAccount':
            if (val.ma_kh) this.txtMaKH.value = val.ma_kh
            if (val.hdkh_id) this.hdkh_id = val.hdkh_id
            if (this.txtMaKH.value && this.txtMaKH.value.trim() != '') this.MaKHChanged()
            case 'popup_TRA CỨU CHI TIẾT ĐIỀU HÀNH THI CÔNG':
              if(val.ma_gd){ 
                var data_thuebao=this.grdPhieuCanXN.list.filter(item => item.ma_gd == val.ma_gd)
                this.grdPhieuCanXN.list=data_thuebao

              }
            break
          }
        } catch (ex) {
          if (ex.data && ex.data.message_detail) {
            this.$toast.error("" + ex.data.message_detail);
          } else {
            this.$toast.error("" + ex);
          }
          
          if (this.debug) console.error(ex);
        } finally {
          this.ClosePopup("popupComponents");
          this.loading(false);
        }
      },
      clearForm: function () {
        this.cboDonViNhan.value = this.cboDonViNhan.Items && this.cboDonViNhan.Items.length > 0 ? this.cboDonViNhan.Items[0]['donvi_id'] : 0
        this.cboKetQuaDuyet.value = 0
        this.phieu_id = 0        
        this.hdtb_id = 0
        this.tdtt_hdtb_id = 0
        this.txtMaKH.value = ''
        this.txtDiaChiKH.value = ''
        this.txtTenKH_cu.value = ''
        this.txtTenKH_moi.value = ''
        this.txtSoGT_cu.value = ''
        this.txtSoGT_moi.value = ''
        this.dtpNgayCap_cu.value = ''
        this.dtpNgayCap_moi.value = ''
        this.txtSoDT_cu.value = ''
        this.txtSoDT_moi.value = ''
        this.cbb_AnhGT.ds_hinhanh = []

        this.grdPhieuYC.list = []
        this.grdPhieuCanXN.list = []
      },
      SetButton: function (kieu) {
        this.btnNhapMoi.Enabled = false;
        this.btnGhiLai.Enabled = false;
        this.btnXoa.Enabled = false;
        this.btnHuy.Enabled = false;
        if (kieu == -1) {
          //Bat dau
          this.btnGhiLai.Enabled = true;
          this.btnHuy.Enabled = true;
          this.clearForm();
        }
        if (kieu == 0) {
          //Bat dau
          this.btnNhapMoi.Enabled = true;
          this.clearForm();
        }
        if (kieu == 1) {
          //Them moi
          this.btnGhiLai.Enabled = true;
          this.btnHuy.Enabled = true;
          this.clearForm();
        }
        if (kieu == 2) {
          //Huy
          this.btnNhapMoi.Enabled = true;
          this.btnXoa.Enabled = true;
          this.clearForm();
        }
        if (kieu == 3) {
          //Edit
          this.btnNhapMoi.Enabled = true;
          this.btnXoa.Enabled = true;
          this.btnGhiLai.Enabled = true;
          this.btnHuy.Enabled = true;
        }
      },
      btnTest_click: function () {
        try {
          this.loading(true)
          // this.popupComponent.data.isPopup = true;
          this.popupComponentData = { 
            isPopup: true,
            hdkh_id: 0, 
            khachhang_id: 0, 
            dichvu_vt: 4,
            loaitb_id: 58,
            kieuld_id: 0,
            ma_tb: 'nhm-1018'
          }
          this.popupComponent = () => import('@/modules/contract/setup/PrepaidDeposite/index.js')
          this.popupComponentName = 'popupDK_TraTruoc'
          this.Popup('popupComponents')
        } catch (ex) {
          this.$toast.error('' + ex)
          if (this.debug) console.error(ex)
          this.loading(false)
        } finally {
          this.loading(false)
        }
      },
      btnLayTT_click: async function () {
        try {
          this.loading(true)
          this.SetButton(0);
          if (this.tag == '0') await this.HienThi_TT_Phieu_THEO_HDKH()
          if (this.tag == '1') { 
            var p_nhanvien_id = this.tt_nd.nhanvien_id
            this.grdPhieuCanXN.list = await this.lay_thongtin_phieu_can_xacnhan(p_nhanvien_id)
          }
        } catch (e) {
          this.loading(false)
        } finally {
          this.loading(false)
        }
      },
      btnDanhSachPhieu_click() {
        this.popupComponent = () => import('./popups/DanhSachPhieuDuyet/DanhSachPhieuDuyet.vue')
        this.popupComponentName = 'DanhSachPhieu'
        this.popupComponentData = {
          ma_gd: this.txtMaGD.value,
          ma_kh: this.txtMaKH.value
        }
        this.Popup('popupComponents')
        this.popupComponentEvts = {"form-close": this.popupClosed}
      },
      btnLichSu_click() {
        if (!this.hdkh_id) {
          this.$toast.error('Chưa lấy được HDKH ID!')
          return
        }
        this.popupComponent = () => import('./popups/LichSuPheDuyet/LichSuPheDuyet.vue')
        this.popupComponentName = 'LichSuPheDuyet'
        this.popupComponentData = {
          hdkh_id: this.hdkh_id
        }
        this.Popup('popupComponents')
        this.popupComponentEvts = {"form-close": this.popupClosed}
      },
      btnTTthuebao_click(){
        this.popupComponent = () =>import("@/modules/search/subscriber/SearchInstallDetail/index.js")
        this.popupComponentName = "popup_TRA CỨU CHI TIẾT ĐIỀU HÀNH THI CÔNG"
        this.popupComponentHeader = ""
        this.popupComponentData = {
          MA_GD: 1// Tú
        }
        this.Popup("popupComponents")
        this.popupComponentEvts = {"form-close": this.popupClosed}
      },
      btnNhapMoi_click: function () {
        this.btnLayTT_click()
      },
      btnGhiLai_click: function () {
        try {
          if (!this.kiemtra_capnhat()) {
            return;
          }
          var thongbao = !this.btnNhapMoi.Enabled ? "thêm mới" : "cập nhật";
          this.$bvModal
            .msgBoxConfirm("Bạn có muốn " + thongbao + " yêu cầu không?", {
              title: "Thông báo",
              size: "sm",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            })
            .then(async (v) => {
              if (v) {
                this.loading(true);                
                // 
                var kieu = !this.btnNhapMoi.Enabled ? 1 : 2
                var phieuyc = {
                  "HDTB_ID": this.hdtb_id,
                  "TEN_KH": this.txtTenKH_moi.value,
                  "TEN_KH_CU": this.txtTenKH_cu.value,
                  "SO_GT": this.txtSoGT_moi.value,
                  "SO_GT_CU": this.txtSoGT_cu.value,
                  "SO_DT": this.txtSoDT_moi.value,
                  "SO_DT_CU": this.txtSoDT_cu.value
                }
                var input = {
                  p_kieu: kieu, 
                  p_tdtt_hdtb_id: this.tdtt_hdtb_id, 
                  p_hdkh_id: this.hdkh_id, 
                  p_json_phieuyc: JSON.stringify(phieuyc)
                }
                await this.CAPNHAT(input);
                this.loading(false);
              }
            })
            .catch((e) => {
              console.log(e);
              this.ShowError("Lỗi ghi lại dữ liệu");
              this.loading(false);
            });
        } catch (e) {
          this.loading(false)
        } finally {
          this.loading(false)
        }
      },
      btnXoa_click: function () {
        if (!this.tdtt_hdtb_id || this.tdtt_hdtb_id == 0) {
          this.ShowError('Bạn chưa chọn phiếu để xóa!')
          return
        }
        this.$bvModal
          .msgBoxConfirm("Bạn có muốn xóa phiếu " + this.tdtt_hdtb_id + " không?", {
            title: "Thông báo",
            size: "sm",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          })
          .then(async (v) => {
            if (v) {
              this.loading(true);
              var kieu = 0
              var phieuyc = {
                "HDTB_ID": this.hdtb_id,
                "TEN_KH": this.txtTenKH_moi.value,
                "TEN_KH_CU": this.txtTenKH_cu.value,
                "SO_GT": this.txtSoGT_moi.value,
                "SO_GT_CU": this.txtSoGT_cu.value,
                "SO_DT": this.txtSoDT_moi.value,
                "SO_DT_CU": this.txtSoDT_cu.value
              }
              var input = {
                p_kieu: kieu, 
                p_tdtt_hdtb_id: this.tdtt_hdtb_id, 
                p_hdkh_id: this.hdkh_id, 
                p_json_phieuyc: JSON.stringify(phieuyc)
              }
              await this.CAPNHAT(input);
              this.loading(false);
            }
          })
          .catch((e) => {
            console.log(e);
            this.ShowError("Lỗi ghi lại dữ liệu");
            this.loading(false);
          });
      },
      btnHuy_click: function () {
        try {
          this.loading(true);
          this.SetButton(2);
          this.formLoad()
        } catch (e) {
          this.loading(false)
        } finally {
          this.loading(false)
        }
      },
      btnGiaoPhieu_click: async function () {
        try {
          this.loading(true)
          if (!this.tdtt_hdtb_id || this.tdtt_hdtb_id == 0) {
            this.$toast.error('Bạn chưa chọn phiếu để giao')
            this.loading(false)
            return
          }
          if (!this.cboDonViNhan.value || this.cboDonViNhan.value == '0') {
            this.$toast.error('Bạn chưa chọn đơn vị nhận')
            this.loading(false)
            return
          }
          var thongtin = {
            "DONVI_ID": Number(this.cboDonViNhan.value),
            "NHANVIEN_ID": this.tt_nd.nhanvien_id,
            "MAY_CN": this.tt_nd.may_cn,
            "NGUOI_CN": this.tt_nd.nguoi_cn
          }
          var input = { 
            p_hdkh_id: this.hdkh_id, //data.p_hdkh_id, 
            p_tdtt_hdtb_id: this.tdtt_hdtb_id, //data.p_tdtt_hdtb_id, 
            p_json_thongtin: JSON.stringify(thongtin), //data.p_json_thongtin, 
          }
          await this.fn_gui_xacnhan_td_ttkh(input)
        } catch (e) {
          this.$toast.error('Lỗi giao phiếu ' + e)
          this.loading(false)
        } finally {
          this.loading(false)
        }
      },
      btnDuyetPhieu_click: async function () {
        try {
          this.loading(true)
          if (!this.phieu_id || this.phieu_id == 0) {
            this.$toast.error(`Bạn chưa chọn phiếu, Phiếu ID: ${this.phieu_id}!`)
            this.loading(false)
            return
          }

          if (!this.hdkh_id || this.hdkh_id == 0) {
            this.$toast.error(`Bạn chưa chọn phiếu, HDKH ID: ${this.hdkh_id}!`)
            this.loading(false)
            return
          }

          if (!this.txtDVYC.value) {
            this.$toast.error(`Không tìm thấy đơn vị yêu cầu, bạn hãy liên hệ admin để xử lý!`)
            this.loading(false)
            return
          }

          if (this.cboKetQuaDuyet.value == 0 && (!this.txtLyDoDuyet.value || this.txtLyDoDuyet.value.toString().trim() == '')) {
            this.$toast.error(`Bạn cần nhập lý do từ chối duyệt!`)
            this.loading(false)
            return
          }
          var input = {
            p_phieu_id: this.phieu_id, //data.p_phieu_id, 
            p_hdkh_id: this.hdkh_id, //data.p_hdkh_id, 
            p_tdtt_hdtb_id: this.tdtt_hdtb_id, //data.p_tdtt_hdtb_id
            p_kq_duyet: Number(this.cboKetQuaDuyet.value),
            p_noidung_duyet: this.txtLyDoDuyet.value,
            p_nhanvien_id: this.tt_nd.nhanvien_id,
          }
          await this.fn_pheduyet_tdtt_kh(input)
        } catch (e) {
          this.$toast.error('Lỗi duyệt phiếu ' + e)
          this.loading(false)
        } finally {
          this.loading(false)
        }
      },
      btnGiuPhieu_click: async function () {
        try {
          this.loading(true)
          if (!this.phieu_id || this.phieu_id == 0) {
            this.$toast.error(`Bạn chưa chọn phiếu, Phiếu ID: ${this.phieu_id}!`)
            this.loading(false)
            return
          }

          if (!this.hdkh_id || this.hdkh_id == 0) {
            this.$toast.error(`Bạn chưa chọn phiếu, HDKH ID: ${this.hdkh_id}!`)
            this.loading(false)
            return
          }
          var input = {
            p_phieu_id: this.phieu_id, //data.p_phieu_id, 
            p_tdtt_hdtb_id: this.tdtt_hdtb_id, //data.p_tdtt_hdtb_id
            p_nhanvien_id: this.tt_nd.nhanvien_id,
          }
          await this.fn_giuphieu_pheduyet(input)
        } catch (e) {
          this.$toast.error('Lỗi Giữ phiếu ' + e)
          this.loading(false)
        } finally {
          this.loading(false)
        }
      },
      fn_giuphieu_pheduyet: async function (data) {
        try {
          var input = {
            p_phieu_id: data.p_phieu_id, 
            p_tdtt_hdtb_id: data.p_tdtt_hdtb_id,
            p_nhanvien_id: data.p_nhanvien_id,
          }
          var self = this
          let response = await api.fn_giuphieu_pheduyet(this.axios, input);
          if (
            response &&
            response.data &&
            response.data.error_code == "BSS-00000000"
          ) {
            this.ShowSuccess('Giữ phiếu thành công')
            await self.btnLayTT_click()
          } else {
            this.ShowError('Giữ phiếu không thành công: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
          }
        } catch (ex) {
          console.log(ex);
          if (ex.data && ex.data.message_detail) {
            this.ShowError("Lỗi Giữ phiếu: " + ex.data.message_detail);
          } else {
            this.ShowError("Lỗi Giữ phiếu: " + JSON.stringify(ex));
          }
          
        }
      },
      btnHuyGiuPhieu_click: async function () {
        try {
          this.loading(true)
          if (!this.phieu_id || this.phieu_id == 0) {
            this.$toast.error(`Bạn chưa chọn phiếu, Phiếu ID: ${this.phieu_id}!`)
            this.loading(false)
            return
          }

          if (!this.hdkh_id || this.hdkh_id == 0) {
            this.$toast.error(`Bạn chưa chọn phiếu, HDKH ID: ${this.hdkh_id}!`)
            this.loading(false)
            return
          }
          var input = {
            p_phieu_id: this.phieu_id, //data.p_phieu_id, 
            p_nhanvien_id: this.tt_nd.nhanvien_id,
          }
          await this.fn_huy_giuphieu_pheduyet(input)
        } catch (e) {
          this.$toast.error('Lỗi Hủy giữ phiếu ' + e)
          this.loading(false)
        } finally {
          this.loading(false)
        }
      },
      fn_huy_giuphieu_pheduyet: async function (data) {
        try {
          var input = {
            p_phieu_id: data.p_phieu_id, 
            p_nhanvien_id: data.p_nhanvien_id,
          }
          var self = this
          let response = await api.fn_huy_giuphieu_pheduyet(this.axios, input);
          if (
            response &&
            response.data &&
            response.data.error_code == "BSS-00000000"
          ) {
            this.ShowSuccess('Hủy giữ phiếu thành công')
            await self.btnLayTT_click()
          } else {
            this.ShowError('Hủy giữ phiếu không thành công: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
          }
        } catch (ex) {
          console.log(ex);
          if (ex.data && ex.data.message_detail) {
            this.ShowError("Lỗi Hủy giữ phiếu: " + ex.data.message_detail);
          } else {
            this.ShowError("Lỗi Hủy giữ phiếu: " + JSON.stringify(ex));
          }
          
        }
      },
    }
  }
</script>
  
  
