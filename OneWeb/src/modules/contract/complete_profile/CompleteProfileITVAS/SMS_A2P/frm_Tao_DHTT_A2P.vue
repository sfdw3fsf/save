<template>

  <b-modal
    ref="frm_Tao_DHTT_A2P"
    id="frm_Tao_DHTT_A2P"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Tạo đơn hàng trả trước
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="hideModal"
        ></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li >
            <a href="javascript:;" @click="btnNhapMoi_Click" v-if="btnNhapMoi.Visible">
              <span class="icon fa fa-plus-circle f20"></span> Nhập mới
            </a>
          </li>
          <li >
            <a href="javascript:;" @click="btnCapNhat_Click" v-if="btnCapNhat.Visible">
              <span class="icon one-save"></span> Cập nhật
            </a>
          </li>
          <li >
            <a href="javascript:;" @click="btnTestGuiMail">
              <span class="icon one-email"></span> Gửi Mail
            </a>
          </li>

          <li >
            <a href="javascript:;" @click="btnActive_Click" class="hidden">
              <span class="icon one-save"></span> Kích hoạt đơn hàng
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="title padt5 padb25 bold h5">
          TẠO ĐƠN HÀNG TRẢ TRƯỚC
        </div>       
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="box-form">
              <div class="legend-title">Thông tin đơn hàng</div>
              <!-- Tên khách hàng -->
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ txtTenKhachHang.Text }}</div>
                    <div class="value">
                      <input type="text" class="form-control" v-bind:value="txtTenKhachHang.value"
                        @change="(e) => (txtTenKhachHang.value = e.target.value)" />
                    </div>
                  </div>
                </div>
              </div>
              <!-- Loại gửi tin  -->
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ cboLoaiGuiTin.Text }}</div>
                    <div class="value">
                      <div class="select-custom" ref="cboLoaiGuiTin">
												<select2 :z-index="2000" :settings="{ dropdownParent: $refs['cboLoaiGuiTin'] }" placeholder="Chưa chọn..." v-model="cboLoaiGuiTin.value" 
                          :options="cboLoaiGuiTin.DataSource.length > 0 ? cboLoaiGuiTin.DataSource.map(e => ({id: e.CONTRACT_TYPE_ID, text: e.CONTRACT_TYPE})) : []" >
												</select2>
											</div>
                    </div>
                  </div>
                </div>
              </div>
              <!-- Loại khách hàng -->
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ cboLoaiKhachHang.Text }}</div>
                    <div class="value">
                      <div class="select-custom" ref="cboLoaiKhachHang">
												<select2 :z-index="2000" :settings="{ dropdownParent: $refs['cboLoaiKhachHang'] }" placeholder="Chưa chọn..." v-model="cboLoaiKhachHang.value" 
                          :options="cboLoaiKhachHang.DataSource.length > 0 ? cboLoaiKhachHang.DataSource.map(e => ({id: e.AGENT_TYPE_ID, text: e.AGENT_TYPE})) : []" >
												</select2>
											</div>
                    </div>
                  </div>
                </div>
              </div>
              <!-- Nhà mạng -->
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ cboNhaMang.Text }}</div>
                    <div class="value">
                      <div class="select-custom" ref="cboNhaMang">
												<select2 :z-index="2000" :settings="{ dropdownParent: $refs['cboNhaMang'] }" 
                        @change="cboNhaMang_change"
                          placeholder="Chưa chọn..." v-model="cboNhaMang.value" 
                          :options="cboNhaMang.DataSource.length > 0 ? cboNhaMang.DataSource.map(e => ({id: e.TELCO_ID, text: e.TELCO_NAME})) : []" >
												</select2>
											</div>
                    </div>
                  </div>
                </div>
              </div>
              <!-- Lĩnh vực  -->
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ cboLinhVuc.Text }}</div>
                    <div class="value">
                      <div class="select-custom" ref="cboLinhVuc">
                        <select2 :z-index="2000" :settings="{ dropdownParent: $refs['cboLinhVuc'] }" placeholder="Chưa chọn..." v-model="cboLinhVuc.value" 
                          :options="cboLinhVuc.DataSource.length > 0 ? cboLinhVuc.DataSource.map(e => ({id: e.LABEL_TYPE_ID, text: e.LABEL_TYPE_NAME})) : []" >
                        </select2>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <!-- Số đơn hàng -->
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ txtTenDonHang.Text }}</div>
                    <div class="value">
                      <input type="text" class="form-control" v-bind:value="txtTenDonHang.value"
                        @change="(e) => (txtTenDonHang.value = e.target.value)" />
                    </div>
                  </div>
                </div>
              </div>
              
              <!-- Sản lượng SMS -->
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ nmLimit.Text }}</div>
                    <div class="value">
                      <input type="number" class="form-control" v-bind:value="nmLimit.value"
                        @change="(e) => (nmLimit.value = e.target.value)" />
                    </div>
                  </div>
                </div>
              </div>
              <!-- Đơn giá -->
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ nmDonGia.Text }}</div>
                    <div class="value">
                      <input type="number" class="form-control" v-bind:value="nmDonGia.value"
                        @change="(e) => (nmDonGia.value = e.target.value)" />
                    </div>
                  </div>
                </div>
              </div>
              <!-- Giá trị dơn hàng -->
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ nmGiaTriDonHang.Text }}</div>
                    <div class="value">
                      <input type="number" class="form-control" v-bind:value="nmGiaTriDonHang.value"
                        @change="(e) => (nmGiaTriDonHang.value = e.target.value)" />
                    </div>
                  </div>
                </div>
              </div>
              <!-- Ngày bắt đầu -->
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ dtpNgayBatDau.Text }}</div>
                    <div class="value">
                      <DatePicker
                        ref="dtpNgayBatDau"
                        :format="dateFormat"
                        :value-type="'format'"
                        v-model="dtpNgayBatDau.value"
                        @change="ondtpNgayBatDau"
                      >
                      </DatePicker>               
                    </div>
                  </div>
                </div>
              </div>
              <!-- thời hạn -->
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ txtSoThang.Text }}</div>
                    <div class="value">
                      <input type="number" class="form-control" v-bind:value="txtSoThang.value"
                        @change="(e) => (txtSoThang.value = e.target.value)" />
                    </div>
                  </div>
                </div>
              </div>

               <!-- Ngày hết hạn -->
               <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ dtpNgayKetThuc.Text }}</div>
                    <div class="value">
                      <DatePicker
                        ref="dtpNgayKetThuc"
                        :format="dateFormat"
                        :value-type="'format'"
                        v-model="dtpNgayKetThuc.value"
                        @change="ondtpNgayKetThuc"
                      >
                      </DatePicker>               
                    </div>
                  </div>
                </div>
              </div>

            </div>
          </div>
        </div> 
        <div class="box-form">
          <div class="legend-title">Đơn hàng đã tạo</div>
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="table-content">
                <DataGrid
                  ref="dgvChiTiet"
                  class="table-result table-gachle"
                  v-bind:columns="grcDanhSach.headers"
                  v-bind:dataSource="grcDanhSach.list"
                  @selectedRowChanged="gridChitiet_FocusedRowChanged"
                  :enable-paging-server="false"
                  :allowPaging="true"
                  :showFilter="true"
                >
                </DataGrid>
              </div>
            </div>
          </div>
        </div>       
      </div>
    </div>
    <frmBRNGuiMail ref='frmBRNGuiMail' />
  </b-modal>

</template>

<script>
import moment from "moment";
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import api from "./PopUpApi.js";
import DataGrid from "@/components/Controls/DataGrid";
import { showError } from '../../../../../utils/util.js';
import frmBRNGuiMail from '../popups/frmBRNGuiMail/frmBRNGuiMail'

export default {
  name: "frm_Tao_DHTT_A2P",
  components: { DatePicker, DataGrid, 
    frmBRNGuiMail, },
  props: ["vhdtb_id"],
  data() {
    return {
      btnNhapMoi: { Visible: true },
      btnCapNhat: { Visible: true },
      tt_nd: {
        phanvung_id: 0,
        nhanvien_id: 0,
        may_cn: "",
        nguoi_cn: "",
        ip_cn: "",
        donvi_id: 0,
        ngay_cn: moment(new Date()).format("DD/MM/YYYY"),
      },
      dateFormat: "DD/MM/YYYY",
      txtTenKhachHang: { Text: "Tên khách hàng", value: "" },
      cboLoaiGuiTin: { Text: "Loại gửi tin", value: "", DataSource: []},
      cboLoaiKhachHang: { Text: "Loại khách hàng", value: "", DataSource: []},
      cboNhaMang: { Text: "Nhà mạng", value: "", DataSource: []},
      cboLinhVuc: { Text: "Lĩnh vực", value: "", DataSource: []},
      txtTenDonHang: { Text: "Tên đơn hàng", value: ""},
      nmLimit: { Text: "Số lượng", value: 0},
      nmDonGia: { Text: "Đơn giá", value: 0},
      nmGiaTriDonHang: {Text: "Giá trị đơn hàng", value: 0},
      dtpNgayBatDau : {Text : "Ngày bắt đầu", value: ""},
      txtSoThang : {Text: "Số tháng", value: 0},
      dtpNgayKetThuc: {Text: "Ngày kết thúc", value: ""},
      adser_id: 0,
      agent_id: 0,
      contract_id: 0,
      telco_id: 0,
      agent_type_id: 0,
      contract_type_id :0,
      is_a2p: false,
      resource: '',
      vkieu: 0,// 1 cap nhat
      grcDanhSach: {
        headers: [
          {
            fieldName: "package_id",
            headerText: "Đơn hàng ID",
            allowFiltering: true,
            isGroupedColumn: true,
          },
          { 
            fieldName: "package_name", 
            headerText: "Số đơn hàng", 
            allowFiltering: true 
          },
          {
            fieldName: "telco_name",
            headerText: "Nhà mạng",
            allowFiltering: true,
          },
          {
            fieldName: "label_type_name",
            headerText: "Loại lĩnh vực",
            allowFiltering: true,
          },
          {
            fieldName: "limit",
            headerText: "Sản lượng",
            allowFiltering: true,
          },
          {
            fieldName: "value",
            headerText: "Đơn giá",
            allowFiltering: true,
          },
          {
            fieldName: "package_avlue",
            headerText: "Giá trị đơn hàng",
            allowFiltering: true,
          },
          {
            fieldName: "start_date",
            headerText: "Ngày bắt đầu",
            allowFiltering: true,
          },
          {
            fieldName: "expiried_date",
            headerText: "Ngày kết thúc",
            allowFiltering: true,
          },
          {
            fieldName: "status_name",
            headerText: "Trạng thái",
            allowFiltering: true,
          },
        ],
        list: [],
      },
    };
  },
  methods: {
    frm_TAO_DHTT_A2P_Load: async function () {
      try {
        this.hdtb_id = this.vhdtb_id ? Number(this.vhdtb_id) : -1;
        this.resource = (await this.axios.post('/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu', {
              P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
              p_nghiepvu: 'KIEMTRA_WHITELIST_BRANDNAME',
              p_ds_para: JSON.stringify({ vhdtb_id: this.hdtb_id })
            })).data.data[0].kq == 'ok' ? 'web-thicong' : 'tichhop';
        console.log(this.resource);
        await this.LoadThongTin();
        await this.LoadDonHang();
      } catch (e) {}
    },

    LoadThongTin: async function () {
      await this.sp_load_danhmuc_dhtt_smsbrn();
    },

    sp_load_danhmuc_dhtt_smsbrn: async function () {
      try {        
        this.cboLoaiKhachHang.DataSource = []
        this.cboLoaiGuiTin.DataSource = []
        this.cboNhaMang.DataSource = []
        let response = (await api.sp_load_danhmuc_dhtt_smsbrn(this.axios, {vhdtb_id: this.hdtb_id})).data.data;
               
        if (response ) {   
          console.log(this.UpperCasePropertyList(response["loai_kh"]))
          this.cboLoaiKhachHang.DataSource = this.UpperCasePropertyList(response["loai_kh"]);
          this.cboLoaiGuiTin.DataSource = this.UpperCasePropertyList(response["loai_gui"]);
          this.cboNhaMang.DataSource = this.UpperCasePropertyList(response["nha_mang"]);
        }          
        
      } catch (ex) {
        console.log(ex);        
      }
     },
     cboNhaMang_change: async function(){
      console.log(1)
      await this.LoadDSLinhVuc()
     },
    LoadDSLinhVuc: async function () {
     
      this.telco_id = parseInt(this.cboNhaMang.value);
      this.agent_type_id = 0;
      this.contract_type_id = parseInt(this.cboLoaiGuiTin.value);

      let  files_LAY_DS_LINHVUC = null 
      if(this.is_a2p) {
        files_LAY_DS_LINHVUC = this.GetData(await api.LAY_DS_LINHVUC_BRN_A2P(this.axios, 
        {
          telco_id : this.telco_id,
          contract_type_id : this.contract_type_id,
          agent_type : 0
        }, this.resource
      )).data.items;
      }
      else {
        files_LAY_DS_LINHVUC = this.GetData(await api.LAY_DS_LINHVUC_BRN(this.axios, 
        {
          telco_id : this.telco_id,
          contract_type_id : this.contract_type_id,
          agent_type : 0
        }, this.resource
      )).data.items;
      } 
         
      this.cboLinhVuc.DataSource = this.UpperCasePropertyList(files_LAY_DS_LINHVUC);

    },
    LoadDonHang: async function () {
    
      var dt = this.GetData(await api.sp_lay_dulieu_khachhang_dhtt_smsbrn(this.axios, {vhdtb_id: this.hdtb_id}));
      if (dt && dt.length > 0) {
        console.log(1)
        // if (dt[0]["vkieu"].toString() == "0")
        // {
        //   this.btnNhapMoi.Visible = true;
        //   this.btnCapNhat.Visible = false;
        // }
        // else
        // {
        //   this.btnNhapMoi.Visible = false;
        //   this.btnCapNhat.Visible = true;
        // }
        var row = dt[0];
   
        this.txtTenKhachHang.value = row["ten_kh"];
        this.cboLoaiKhachHang.value = row["agent_type_id"];
        this.cboLoaiGuiTin.value = row["contract_type_id"];
        this.cboLoaiKhachHang.value = row["agent_type_id"];
        this.cboNhaMang.value = 1
        await this.cboNhaMang_change();
        this.agent_id = Number(row["agent_id"]);
        this.adser_id = Number(row["adser_id"]);
        this.contract_id = Number(row["contract_id"]);

        var ds_donhang = this.GetData(await api.sp_lay_ds_dhtt_smsbrn(this.axios, {vhdtb_id: this.hdtb_id}));
        this.grcDanhSach.list = ds_donhang;
      }
     
    },
   
    showModal: async function () {
      this.clear();
      this.$refs["frm_Tao_DHTT_A2P"].show();
      this.loading(true);
      try {
        this.tt_nd.phanvung_id = Number(this.$root.token.getPhanVungID());
        this.tt_nd.nhanvien_id = Number(this.$root.token.getNhanVienID());
        this.tt_nd.may_cn = await this.$root.token.getMachine();
        this.tt_nd.nguoi_cn = await this.$root.token.getMaNhanVien();
        this.tt_nd.ip_cn = await this.$root.token.getIP();
        this.tt_nd.donvi_id = await this.$root.token.getDonViID();

        document.getElementById("frm_Tao_DHTT_A2P").parentElement.style.display = "block"
        await Promise.all([this.frm_TAO_DHTT_A2P_Load()]);
        this.loading(false);
      } catch (ex) {
        this.loading(false);
      }
    },
    gridChitiet_FocusedRowChanged(){},
    hideModal: function () {
      this.$refs["frm_Tao_DHTT_A2P"].hide();
    },
    GetData: function (response) {
      if (response.data && response.data.error_code == "BSS-00000000") {
        return response.data.data;
      } else {
        console.log(response.data.error_code);
        this.ShowError(response.data.message);
      }
      return [];
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg);
    },
    ShowError: function (msg) {
      this.$toast.error(msg);
    },
    isNumber(evt) {
      evt = evt ? evt : window.event;
      var charCode = evt.which ? evt.which : evt.keyCode;
      if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        evt.preventDefault();
      } else {
        return true;
      }
    },
    numberWithCommas(n) {
      var parts = n.toString().split(".");
      return (
        parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +
        (parts[1] ? "." + parts[1] : "")
      );
    },
    LowerCasePropertyList(obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toLowerCase();
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key];
            delete item[key];
          }
        }
        return item;
      });
    },
    UpperCasePropertyList(obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toUpperCase();
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key];
            delete item[key];
          }
        }
        return item;
      });
    },
    ondtpNgayKetThuc(value_) {
      if (!moment(value_, "DD/MM/YYYY").isValid()) {
        this.dtpNgayKetThuc.value = "01/01/2000";
      } else {
        this.dtpNgayKetThuc.value = value_;
      }
    },
    ondtpNgayBatDau(value_) {
      if (!moment(value_, "DD/MM/YYYY").isValid()) {
        this.dtpNgayBatDau.value = "01/01/2000";
      } else {
        this.dtpNgayBatDau.value = value_;
      }
    },
    clear() {
      this.txtTenKhachHang.value = "";
      this.cboLoaiGuiTin.value = "";
      this.cboLoaiKhachHang.value = "";
      this.cboNhaMang.value = "";
      this.cboLinhVuc.value = "";
      this.txtTenDonHang.value = "";
      this.nmLimit.value = "";
      this.nmDonGia.value = "";
      this.nmGiaTriDonHang.value = "";
      this.dtpNgayBatDau.value = "";
      this.txtSoThang.value = "";
      this.dtpNgayKetThuc.value = "";
      this.adser_id = 0;
      this.agent_id = 0;
      this.contract_id = 0;
      this.vkieu = 0;
    },

    btnNhapMoi_Click: async function ()
    {
      this.vkieu = 0;
      this.clear();
    },
    btnCapNhat_Click: async function ()
    {

      let expired_date = null;
      let start_date = null;
      if(this.is_a2p) {
        expired_date =  this.dtpNgayKetThuc.value ;
        start_date =  this.dtpNgayBatDau.value ;
      }
      else {
        expired_date =  this.dtpNgayKetThuc.value + " 00:00:00";
        start_date =  this.dtpNgayBatDau.value  + " 00:00:00";
      }
      var obj = {
        label_type_name : this.cboLinhVuc.label_type_name,
        agent_id : this.agent_id,
        agent_type : 0,
        contract_type_id : parseInt(this.cboLoaiGuiTin.value),
        label_type : parseInt(this.cboLinhVuc.value),
        telco_id : parseInt(this.cboNhaMang.value),
        package_name : this.txtTenDonHang.value,
        limit : parseInt(this.nmLimit.value),
        expired_date : expired_date,
        status : 1,
        value : parseInt(this.nmDonGia.value),
        package_value : parseInt(this.nmGiaTriDonHang.value),
        start_date : start_date,
        so_thang : parseInt(this.txtSoThang.value),

        price : parseInt(this.nmDonGia.value),
        vhdtb_id: this.hdtb_id,
        vlabel_type_name : this.cboLinhVuc.label_type_name,
        vagent_id : this.agent_id,
        vagent_type : 0,
        vcontract_type_id : parseInt(this.cboLoaiGuiTin.value),
        vlabel_type : parseInt(this.cboLinhVuc.value),
        vtelco_id : parseInt(this.cboNhaMang.value),
        vpackage_name : this.txtTenDonHang.value,
        vlimit : parseInt(this.nmLimit.value),
        vexpired_date : this.dtpNgayKetThuc.value + " 00:00:00",
        vstatus : 1,
        vvalue : parseInt(this.nmDonGia.value),
        vpackage_value : parseInt(this.nmGiaTriDonHang.value),
        vstart_date : this.dtpNgayBatDau.value + " 00:00:00",
        vso_thang : parseInt(this.txtSoThang.value),
        vprice : parseInt(this.nmDonGia.value),

      };
      await this.TAO_DHTT_Service(obj);
      await this.LoadDonHang();
    },
    btnActive_Click: async function ()
    {
      let expired_date = null;
      let start_date = null;
      if(this.is_a2p) {
        expired_date =  this.dtpNgayKetThuc.value ;
        start_date =  this.dtpNgayBatDau.value ;
      }
      else {
        expired_date =  this.dtpNgayKetThuc.value + " 00:00:00";
        start_date =  this.dtpNgayBatDau.value  + " 00:00:00";
      }
      var obj = {
        package_id: this.grcDanhSach.list[0].package_id,
        hdtb_id: this.hdtb_id,
        label_type_name: this.cboLinhVuc.label_type_name,
        agent_id: this.agent_id,
        agent_type: 0,
        contract_type_id: parseInt(this.cboLoaiGuiTin.value),
        label_type: parseInt(this.cboLinhVuc.value),
        telco_id: parseInt(this.cboNhaMang.value),
        package_name: this.txtTenDonHang.value,
        limit: parseInt(this.nmLimit.value),
        expired_date: expired_date,
        status: 1,
        value: parseInt(this.nmDonGia.value),
        package_value: parseInt(this.nmGiaTriDonHang.value),
        start_date: start_date,
        so_thang: parseInt(this.txtSoThang.value),
        price: parseInt(this.nmDonGia.value),
        vhdtb_id: this.hdtb_id,
        vpackage_id: this.grcDanhSach.list[0].package_id,
        vlabel_type_name: this.cboLinhVuc.label_type_name,
        vagent_id: this.agent_id,
        vagent_type: 0,
        vcontract_type_id: parseInt(this.cboLoaiGuiTin.value),
        vlabel_type: parseInt(this.cboLinhVuc.value),
        vtelco_id: parseInt(this.cboNhaMang.value),
        vpackage_name: this.txtTenDonHang.value,
        vlimit: parseInt(this.nmLimit.value),
        vexpired_date: this.dtpNgayKetThuc.value + " 00:00:00",
        vstatus: 1,
        vvalue: parseInt(this.nmDonGia.value),
        vpackage_value: parseInt(this.nmGiaTriDonHang.value),
        vstart_date: this.dtpNgayBatDau.value + " 00:00:00",
        vso_thang: parseInt(this.txtSoThang.value),
        vprice: parseInt(this.nmDonGia.value),
      };
      await this.Update_DHTT_Service(obj);
      this.hideModal();
    },
    TAO_DHTT_Service: async function (obj)
    {
      console.log(obj)
      // await api.sp_update_donhang_tratruoc_smsbrn(this.axios, obj);
      if (this.grcDanhSach.list.length == 0) {
        let result_response = null;
        if(this.is_a2p) {
          obj.valid_date = obj.start_date;
          result_response = this.GetData(await api.TAO_A2P_DHTT(this.axios, obj, this.resource));
          if(result_response.message != "OK" ) {
            this.$toast.error(JSON.stringify(result_response))
          }
        }
        else {
          result_response = this.GetData(await api.TAO_DHTT(this.axios, obj, this.resource));
        }
        
        console.log('response', result_response);
        obj.package_id = result_response.data.package_id;
        obj.vpackage_id = result_response.data.package_id;
      }
      else {
        obj.package_id = this.grcDanhSach.list[0].package_id;
        obj.vpackage_id = this.grcDanhSach.list[0].package_id;
      }

      console.log(obj)
      this.GetData(await api.sp_update_donhang_tratruoc_smsbrn(this.axios, obj));
      let req = {
        "vhdtb_id": this.hdtb_id,
        "vdonvi_giao_id": this.tt_nd.donvi_id,
        "vnhanvien_giao_id": this.tt_nd.nhanvien_id,
        "vmay_cn": this.tt_nd.may_cn,
        "vnguoi_cn": this.tt_nd.nguoi_cn,
        "vip_cn": this.tt_nd.ip_cn,
      }
      this.GetData(await api.lay_tt_giao_vip_giamsat_it360(this.axios, req));
      this.$toast.success('Tạo đơn hàng thành công');


    },

    Update_DHTT_Service: async function (obj)
    {
      // await api.sp_update_donhang_tratruoc_smsbrn(this.axios, obj);
      let result_response = null;
      if(this.is_a2p) {
        result_response = this.GetData(await api.UPDATE_A2P_DHTT(this.axios, obj, this.resource));
      }
      else {
        result_response = this.GetData(await api.UPDATE_DHTT(this.axios, obj, this.resource));
      }
       
      if(result_response && result_response.length > 0) {
        if(!result_response) 
        {
          showError(result_response.message);
        }
        this.$toast.success('Active đơn hàng thành công')
      }
    },
    btnTestGuiMail() {
      let data = {
        hdtb_id: this.vhdtb_id
      }
      document.getElementById("frm_Tao_DHTT_A2P").parentElement.style.display = "none"
      this.$refs.frmBRNGuiMail.openDialog(data)
    },

   
  },
  mounted() {},
  computed: {
    cp_dongia: {
      get() {
        return this.numberWithCommas(this.nmDonGia.value);
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.nmDonGia.value = Number(replaced);
      },
    },
    cp_giatridonhang: {
      get() {
        return this.numberWithCommas(this.nmGiaTriDonHang.value);
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.nmGiaTriDonHang.value = Number(replaced);
      },
    },
    cp_giatridonhang: {
      get() {
        return this.numberWithCommas(this.nmGiaTriDonHang.value);
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.nmGiaTriDonHang.value = Number(replaced);
      },
    },
    label_type_name: {
      get() {
        var filter = this.cboLinhVuc.DataSource.filter(x => x.LABEL_TYPE_ID == this.cboLinhVuc.value)
        if(filter && filter.length > 0) return filter.LABEL_TYPE_NAME;
        return "";
      }
    }
  },
  watch: {
    "cboHTThanhToan.value"(val) {
      if (val.toString() == "0") {
        this.txtSoTienDatCoc.Enabled = true;
      } else {
        this.txtSoTienDatCoc.Enabled = false;
        // this.txtSoTienDatCoc.value = "0";
      }
    }
  },
};
</script>

<style scoped>
.disabled {
  cursor: not-allowed;
  color: gray !important;
}
.mx-datepicker {
  width: 100% !important;
}
.select2 {
  display: table !important;
  width: 100% !important;
  table-layout: fixed !important;
}
</style>