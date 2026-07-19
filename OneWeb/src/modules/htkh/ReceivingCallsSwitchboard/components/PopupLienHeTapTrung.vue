<template>
  <b-modal
    ref="PopupLienHeTapTrung"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Thêm mới liên hệ
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a href="javascript: void(0)">
                        <span class="icon one-search" @click="txt_SDT_LienHe_KeyPress"></span>Tìm kiếm
                    </a>
                </li>
                <li>
                    <a href="javascript: void(0)" @click="btnNhapMoi_click">
                        <span class="icon one-file-plus"></span>Thêm mới
                    </a>
                </li>
                <li>
                    <a href="javascript: void(0)" @click="btnGhiLai_click">
                        <span class="icon one-save"></span>Ghi lại
                    </a>
                </li>
                <li>
                    <a href="javascript: void(0)" @click="btn_XoaTB_click">
                        <span class="icon one-trash"></span>Xóa TB
                    </a>
                </li>
            </ul>
        </div>
      <div class="popup-body">
            <div class="nav tabs tab-over">
                <a v-for="(item, i) in tabList" class="minw0 pointer" :key="i" :class="{active: item.id == selectedTab}"
                    @click="() => selectedTab = item.id">{{ item.name }} </a>
            </div>
            <div class="tab-content">
                <div v-if="selectedTab == 1">
                    <div class="box-form">
                        <div class="legend-title">Thông tin liên hệ</div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Số điện thoại</div>
                                    <div class="value">
                                        <input type="text" class="form-control highlight" ref="txt_SDT_LienHe" v-model="txt_SDT_LienHe" @keyup.enter="txt_SDT_LienHe_KeyPress"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w90">
                                        <div class="check-action">
                                            <input type="checkbox" class="check" v-model="chkDaChuanHoa" />
                                            <span class="name">Đã chuẩn hóa</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w90">Tỉnh</div>
                                    <div class="value">
                                        <ejs-dropdownlist v-model="cbo_Tinh.EditValue" :dataSource="cbo_Tinh.DataSource" :fields="{ text: 'tentinh', value: 'tinh_id' }" noRecordsTemplate="Không có dữ liệu tìm kiếm" :allowFiltering="true"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Tên liên hệ</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="txt_TenLH"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w90">Giới tính</div>
                                    <div class="value">
                                        <ejs-dropdownlist v-model="cbo_GioiTinh.EditValue" :dataSource="cbo_GioiTinh.DataSource" :fields="{ text: 'text', value: 'id' }" noRecordsTemplate="Không có dữ liệu tìm kiếm" :allowFiltering="true"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w90">Ngày sinh</div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                          <!-- <date-picker ref="dtp_NgaySinh" style="width: 100%" placeholder="dd/mm/yyyy" v-model="dtp_NgaySinh.EditValue" :formatter="momentFormat" valueType="format"></date-picker> -->
                                          <DatePicker :format="momentFormat" :value-type="'format'" ref="dtp_NgaySinh" v-model="dtp_NgaySinh.EditValue"
                                            :input-attr="{required: 'true', title: dtp_NgaySinh.EditValue}">
                                          </DatePicker>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-9 col-12">
                                <div class="info-row">
                                    <div class="key w90">Số giấy tờ</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="txtSoGiayTo"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w90">Loại giấy tờ</div>
                                    <div class="value">
                                        <ejs-dropdownlist v-model="cboLoaiGT.EditValue" :dataSource="cboLoaiGT.DataSource" :fields="{ text: 'text', value: 'id' }" noRecordsTemplate="Không có dữ liệu tìm kiếm" :allowFiltering="true"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                          <div class="col-sm-12 col-12">
                           <div class="info-row">
                                <div class="key w90">Địa chỉ liên hệ</div>
                                <div class="value">
                                    <textarea v-model="txt_DiaChiLH" class="form-control" style="height: 66px;resize: none;"></textarea>
                                </div>
                            </div>
                          </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Danh sách liên hệ theo số máy</div>
                        <div class="table-content">
                            <DataGrid
                                ref="grid_LienHe"
                                v-bind:columns="grid_LienHe.config"
                                v-bind:dataSource="grid_LienHe.DataSource"
                                :enable-paging-server="false"
                                :allowPaging="true"
                                :showFilter="true"
                                :showColumnCheckbox="false"
                                :selectionSettings="{ enableToggle: false }"
                                @rowSelected="gview_LienHe_FocusedRowChanged"
                            >
                            </DataGrid>
                        </div>
                    </div>
                </div>
                <div v-if="selectedTab == 2">
                    <div class="box-form">
                        <div class="table-content">
                            <DataGrid
                                ref="grid_Thuebao_LH"
                                v-bind:columns="grid_Thuebao_LH.config"
                                v-bind:dataSource="grid_Thuebao_LH.DataSource"
                                :enable-paging-server="false"
                                :allowPaging="true"
                                :showFilter="true"
                                :showColumnCheckbox="false"
                                :selectionSettings="{ enableToggle: false }"
                            >
                            </DataGrid>
                        </div>
                    </div>
                </div>
            </div>
      </div>
    </div>
</b-modal>
</template>
<style scoped>
.mx-datepicker {
  width: 100% !important;
}
</style>

<script>
import { Page, Filter } from '@syncfusion/ej2-vue-grids'
import { pagingAndFilter } from '@/modules/admin/category/Staff/mixins/pagingAndFilter.js'
import * as moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import api from '../ReceivingCallsSwitchboardApi.js'
export default {
  name: "PopupLienHeTapTrung",
  components: { DatePicker },
  mixins: [pagingAndFilter],
  provide: {
    grid: [Page, Filter]
  },
  props: ['dulieu'],
  data: () => ({
    momentFormat: 'DD/MM/YYYY',
    grid_Thuebao_LH: {
      config: [
        {
          fieldName: 'tinh',
          headerText: 'Tỉnh',
          allowFiltering: true,
          width: 80
        },
        {
          fieldName: 'dienthoai',
          headerText: 'Số điện thoại',
          isPrimaryKey: true,
          allowFiltering: true
        },
        {
          fieldName: 'ma_tb',
          headerText: 'Số máy/Acc',
          isPrimaryKey: true,
          allowFiltering: true
        },
        {
          fieldName: 'ten_tb',
          headerText: 'Tên thuê bao',
          allowFiltering: true
        },
        {
          fieldName: 'dichvuvt',
          headerText: 'Dịch vụ',
          allowFiltering: true
        },
        {
          fieldName: 'diachi_ld',
          headerText: 'Địa chỉ lắp đặt',
          allowFiltering: true
        },
        {
          fieldName: 'ma_tt',
          headerText: 'Mã thanh toán',
          allowFiltering: true
        },
        {
          fieldName: 'ma_kh',
          headerText: 'Mã khách hàng',
          allowFiltering: true
        },
        {
          fieldName: 'diachi_tb',
          headerText: 'Địa chỉ thuê bao',
          allowFiltering: true
        },
        {
          fieldName: 'ma_lt',
          headerText: 'Số ảo',
          allowFiltering: true
        },
        {
          fieldName: 'ma_vnpt',
          headerText: 'Mã VNPT',
          allowFiltering: true
        },
      ],
      DataSource: []
    },
    grid_LienHe: {
      config: [
        {
          fieldName: 'ten_lh',
          headerText: 'Tên liên hệ',
          allowFiltering: true
        },
        {
          fieldName: 'dienthoai',
          headerText: 'Số điện thoại',
          allowFiltering: true
        },
        {
          fieldName: 'donvi',
          headerText: 'Đơn vị',
          allowFiltering: true
        },
        {
          fieldName: 'diachi',
          headerText: 'Địa chỉ',
          allowFiltering: true
        },
        {
          fieldName: 'ngaysinh',
          headerText: 'Năm sinh',
          allowFiltering: true
        },
        {
          fieldName: 'kieu_lh',
          headerText: 'Kiểu LH',
          allowFiltering: true
        }
      ],
      DataSource: []
    },
    tabList: [
      {
        id: 1,
        name: "Điện thoại liên hệ"
      },
      {
        id: 2,
        name: "Thuê bao theo liên hệ"
      }
    ],
    selectedTab: 1,
    khachhang_id: 0,
    thuebao_id: 0,
    lienhe_id: 0,
    txt_SDT_LienHe: "",
    chkDaChuanHoa: false,
    cbo_Tinh: {
      EditValue: "",
      DataSource: []
    },
    txt_TenLH: "",
    cbo_GioiTinh: {
      EditValue: "",
      DataSource: [
        { id: 0, text: "Nam"},
        { id: 1, text: "Nữ"},
        { id: -1, text: "Không xác định"},
      ]
    },
    dtp_NgaySinh: {
      EditValue: ""
    },
    txtSoGiayTo: "",
    cboLoaiGT: {
      EditValue: "",
      DataSource: [
        { id: 0, text: "Chứng minh nhân dân"},
        { id: 1, text: "Căn cước công dân"},
        { id: 2, text: "Đăng ký kinh doanh"},
        { id: 3, text: "Hộ chiếu"},
      ]
    },
    txt_DiaChiLH: "",
    tsbtnNhapMoi: { Enabled: true, }
  }),
  watch: {
    selectedTab: function (newVal, oldval) {
      this.tabpg_Main_SelectedPageChanged({Page: newVal})
    },
  },
  methods: {
    formLoad: async function (data) {
      var dr = {}
      if (this.dulieu) {        
        dr = this.dulieu
        this.tsbtnNhapMoi.Enabled = !this.dulieu["ma_tb"] ? true : false
        this.thuebao_id = this.dulieu['thuebao_id']
        this.khachhang_id = this.dulieu['khachhang_id']
        this.txt_SDT_LienHe = this.dulieu['so_dt']
        this.cbo_Tinh.EditValue = this.dulieu['tinh_id']
        this.cbo_Tinh.DataSource = this.dulieu['dsTinh'] && this.dulieu['dsTinh'].length > 0 ? this.dulieu['dsTinh'] : []
        this.txt_TenLH = this.dulieu['ten_kh']
        this.txt_DiaChiLH = this.dulieu['diachi_ld']
        this.txtSoGiayTo = this.dulieu['so_giayto']
        this.cboLoaiGT.EditValue = this.dulieu['loai_giayto']
      } else if (data) {  
        console.log('PopupLienHeTapTrung > formLoad',data)    
        this.txt_SDT_LienHe = data.so_dt
        this.cbo_Tinh.DataSource = data['dsTinh'] && data['dsTinh'].length > 0 ? data['dsTinh'] : []
        if (data.dtrow) {
          dr = data.dtrow
          this.tsbtnNhapMoi.Enabled = !data.dtrow["ma_tb"] ? true : false
          this.thuebao_id = data.dtrow['thuebao_id']
          this.khachhang_id = data.dtrow['khachhang_id']        
          this.cbo_Tinh.EditValue = data.dtrow['tinh_id']          
          this.txt_TenLH = data.dtrow['ten_kh']
          this.txt_DiaChiLH = data.dtrow['diachi_ld']
          this.txtSoGiayTo = data.dtrow['so_giayto']
          this.cboLoaiGT.EditValue = data.dtrow['loai_giayto']
        }        
      }
      this.cbo_Tinh.DataSource = this.cbo_Tinh.DataSource && this.cbo_Tinh.DataSource.length > 0 ? this.LowerCasePropertyList(this.cbo_Tinh.DataSource) : [] 
      if (this.tsbtnNhapMoi.Enabled || (dr["kieu_lh"] && (dr["kieu_lh"].toString() == "3" || dr["kieu_lh"].toString() == "4"))) {
        // this.Text = "Cập nhật liên hệ";
        this.lienhe_id = dr["thuebao_id"];
        this.chkDaChuanHoa = dr["kieu_lh"].toString() == "4";
      } else {
        // this.Text = "Thêm mới liên hệ";
        this.lienhe_id = 0;
      }
      
      var check_sdt = this.KiemTraSoCoDinh(this.txt_SDT_LienHe, "")
      if (check_sdt.ketqua) {
        this.txt_SDT_LienHe = check_sdt.so_dt
        this.cbo_Tinh.EditValue = this.cbo_Tinh.DataSource.filter(e => e['tentat'] == check_sdt.mavung)[0]['tinh_id']
      }
      await this.txt_SDT_LienHe_KeyPress();
    },
    KiemTraSoCoDinh: function (so_dt, ma_tinh) {
      var vma_tinh = ma_tinh
      var sodt_chuanhoa = so_dt
      var ketqua = true
      if (!so_dt || so_dt.length == 0) ketqua = false
      let codeLength = 4
      if (so_dt.substring(0, 3) == '028' || so_dt.substring(0, 3) == '024') codeLength = 3
      if (api.dctDSMaVung().hasOwnProperty(so_dt.substring(0, codeLength)) == false) {
        ketqua = false
      } else {
        vma_tinh = api.dctDSMaVung()[so_dt.substring(0, codeLength)]
        sodt_chuanhoa = sodt_chuanhoa.substring(codeLength)
        ketqua = true
      }

      return {ketqua: ketqua, so_dt: sodt_chuanhoa, mavung: vma_tinh }
    },
    txt_SDT_LienHe_KeyPress: async function() {
      try {
        this.grid_LienHe.DataSource = []
        var data = this.GetDataList(await api.sp_lay_thongtin_lienhe(this.axios, this.txt_SDT_LienHe));
        this.grid_LienHe.DataSource = data
      } catch (e) {
        console.log('sp_lay_thongtin_lienhe',e)
        if (e && e.data) {
          this.$toast.error('Lỗi sp_lay_thongtin_lienhe ' + JSON.stringify(e.data.message_detail ? e.data.message_detail : e.data.message))
        } else {
          this.$toast.error('Lỗi sp_lay_thongtin_lienhe ' + JSON.stringify(e))
        }
      } finally {

      }
    },
    gview_LienHe_FocusedRowChanged: async function () {
      const dr = this.$refs.grid_LienHe.$refs.grid.getSelectedRecords()
      console.log('gview_LienHe_FocusedRowChanged',dr)
      if (dr.length == 0) {
        // TODO: fuc Clear()
        // Clear()
        this.grid_LienHe.selected = {}
        return
      }

      try {
        this.grid_LienHe.selected = dr[0]
        // this.khachhang_id = Number(dr[0]['id']);
        this.lienhe_id = dr[0]["ttlh_id"]
        this.txt_TenLH = dr[0]["ten_lh"]
        this.txt_DiaChiLH = dr[0]["diachi"]
        this.txt_SDT_LienHe = dr[0]["dienthoai"]
        this.cbo_Tinh.EditValue = dr[0]["donvi"];
        this.dtp_NgaySinh.EditValue = dr[0]["ngaysinh"];
        if (dr[0]["gioitinh"]) this.cbo_GioiTinh.EditValue = dr[0]["gioitinh"];

        if (dr[0]["kieulh_id"]) this.chkDaChuanHoa = dr[0]["kieulh_id"].toString() == "2";
        else if (dr[0]["kieu_lh"]) this.chkDaChuanHoa = dr[0]["kieu_lh"].toString() == "4";

        this.txtSoGiayTo = dr[0]["so_giayto"]
        if (dr[0]["loai_giayto"]) this.cboLoaiGT.EditValue = Number(dr[0]["loai_giayto"].toString());
        else this.cboLoaiGT.EditValue = null;
        
        await this.lookUpUser_AccountAccepted()
      } catch (ex) {
        console.log('gview_DanhSach_FocusedRowChanged',ex)
        this.$toast.error("Lỗi: " + ex)
      }
    },
    lookUpUser_AccountAccepted: async function () {
      if (!this.khachhang_id) {
        this.$toast.error('Không có thông tin khách hàng!')
        return
      } else {
        if (this.thuebao_id && this.lienhe_id > 0) {
          var themTB = await this.ThemThueBao_LienHe()
          if (themTB) {
            await this.LayDSThuebao_LienHe()        
          } else this.$toast.error("Thuê bao đã được gán với liên hệ. Gán thất bại");
        }
      }      
    },
    ThemThueBao_LienHe: async function () {
      try {
        var input = {
          lienhe_id: this.lienhe_id,
          thuebao_id: this.thuebao_id,
          tinh_id: this.cbo_Tinh.EditValue,
        }
        let res = await api.sp_them_thuebao_lienhe(this.axios, input)
        if (res && res.data && res.data.error_code == 'BSS-00000000') {
          this.$toast.success('Cập nhật dữ liệu thuê bao liên hệ thành công!')
          return true
        } else {
          this.$toast.error('Lỗi sp_them_thuebao_lienhe ' + (res.data.message_detail ? res.data.message_detail : res.data.message))
          return false
        }
      } catch (e) {
        if (e && e.data) {
          this.$toast.error('Lỗi sp_them_thuebao_lienhe ' + JSON.stringify(e.data.message_detail ? e.data.message_detail : e.data.message))
        } else {
          this.$toast.error('Lỗi sp_them_lienhe ' + JSON.stringify(e))
        }        
        return false
      } finally {

      }
    },
    LayDSThuebao_LienHe: async function () {
      try {
        this.grid_Thuebao_LH.DataSource = []
        var data = this.GetDataList(await api.sp_lay_ds_thuebao_lienhe(this.axios, this.lienhe_id));
        this.grid_Thuebao_LH.DataSource = data        
      } catch (e) {
        if (e && e.data) {
          this.$toast.error('Lỗi sp_lay_ds_thuebaokh ' + JSON.stringify(e.data.message_detail ? e.data.message_detail : e.data.message))
        } else {
          this.$toast.error('Lỗi sp_lay_ds_thuebaokh ' + JSON.stringify(e))
        }        
      } finally {

      }
    },
    tabpg_Main_SelectedPageChanged: async function (e) {
      if (e.Page == 2) {
        await this.lookUpUser_AccountAccepted() 
      }
    },
    btnNhapMoi_click: function () {
      this.txt_SDT_LienHe = ""
      this.txt_TenLH = ""      
      this.txt_DiaChiLH = ""
      this.cbo_Tinh.EditValue = null;
    },
    btnGhiLai_click: async function () {
      try {
        this.loading(true)
        if (this.validate()) {
          await this.ThemLienHe()
        }
      } catch (e) {

      } finally {
        this.loading(false)
      }
    },
    validate() {
      if (!this.txt_SDT_LienHe || this.txt_SDT_LienHe.length < 7)
      {
        this.$toast.error("Số điện thoại không chính xác");
        this.$refs.txt_SDT_LienHe.focus();
        return false;
      }
      if (this.txt_SDT_LienHe.substring(0,1) == "3" && !this.cbo_Tinh.EditValue)
      {
        this.$toast.error("Số điện thoại cố định phải gắn kèm với tỉnh");
        return false;
      }
      if (!this.dtp_NgaySinh.EditValue) {
        this.$toast.error("Ngày sinh không được để trống. Hãy chọn lại ngày sinh");
        return false;
      }
      if (moment(date,'DD/MM/YYYY') > new Date())
      {
        this.$toast.error("Ngày sinh trong tương lai. Hãy chọn lại ngày sinh");
        return false;
      }
      if (this.txtSoGiayTo && this.txtSoGiayTo.length > 5) {
        if (!this.cboLoaiGT.EditValue)
        {
          this.$toast.error("Hãy chọn loại giấy tờ");
          return false;
        }
      }        
      return true
    },
    ThemLienHe: async function () {
      try {
        var index = this.cbo_Tinh.DataSource.findIndex(e => e.tinh_id == this.cbo_Tinh.EditValue)
        var ma_tinh = this.cbo_Tinh.DataSource[index].tentat
        var input = {
          ID: this.lienhe_id,
          Ten_LienHe: this.txt_TenLH,
          DiaChi: this.DiaChi,
          DienThoai: this.txt_SDT_LienHe,
          Matinh: ma_tinh,
          GioiTinh: this.cbo_GioiTinh.EditValue,
          NgaySinh: this.dtp_NgaySinh.EditValue,
          KieuLH: this.chkDaChuanHoa ? 2 : 1,
          LoaiGiayTo: this.cboLoaiGT.EditValue,
          SoGiayTo: this.txtSoGiayTo 
        }
        let res = await api.sp_them_lienhe(this.axios, input)
        if (res && res.data && res.data.error_code == 'BSS-00000000') {
          this.$toast.success('Cập nhật dữ liệu liên hệ thành công!')
        } else {
          this.$toast.error('Lỗi sp_them_lienhe ' + (res.data.message_detail ? res.data.message_detail : res.data.message))
        }
      } catch (e) {
        if (e && e.data) {
          this.$toast.error('Lỗi sp_them_lienhe ' + JSON.stringify(e.data.message_detail ? e.data.message_detail : e.data.message))
        } else {
          this.$toast.error('Lỗi sp_them_lienhe ' + JSON.stringify(e))
        }        
      } finally {

      }
    },
    btn_XoaTB_click: async function () {
      await this.XoaThueBao_LienHe()
    },
    XoaThueBao_LienHe: async function () {
      try {
        var input = {
          lienhe_id: this.lienhe_id,
          thuebao_id: this.thuebao_id,
          donvi_id: this.cbo_Tinh.EditValue
        }
        let res = await api.sp_xoa_thuebao_lienhe(this.axios, input)
        if (res && res.data && res.data.error_code == 'BSS-00000000') {
          this.$toast.success('Xóa dữ liệu thuê bao liên hệ thành công!')
          await this.LayDSThuebao_LienHe()   
        } else {
          this.$toast.error('Lỗi sp_xoa_thuebao_lienhe ' + (res.data.message_detail ? res.data.message_detail : res.data.message))
        }
      } catch (e) {
        if (e && e.data) {
          this.$toast.error('Lỗi sp_xoa_thuebao_lienhe ' + JSON.stringify(e.data.message_detail ? e.data.message_detail : e.data.message))
        } else {
          this.$toast.error('Lỗi sp_xoa_thuebao_lienhe ' + JSON.stringify(e))
        }        
      } finally {

      }
    },
    GetDataList: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return []
      } else {
        this.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
        return []
      }
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return ''
      } else {
        this.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
        return ''
      }
    },
    LowerCasePropertyList(obj) {
      return obj.map(function(item) {
        for (var key in item) {
          var upper = key.toLowerCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    UpperCasePropertyList(obj) {
      return obj.map(function(item) {
        for (var key in item) {
          var upper = key.toUpperCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    showModal (data) {
      this.formLoad(data)
      this.$refs["PopupLienHeTapTrung"].show()
    },
    hideModal () {
      this.$refs["PopupLienHeTapTrung"].hide()
    },
    handleShowModal () {

    }
  }
}
</script>
