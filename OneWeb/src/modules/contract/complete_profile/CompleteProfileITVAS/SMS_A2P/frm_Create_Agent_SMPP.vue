<template>
  <b-modal
    ref="frm_Create_Agent_SMPP"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Tạo đối tác
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
              <span class="icon fa fa-plus-circle f20"></span> Tạo khách hàng
            </a>
          </li>
          <li >
            <a href="javascript:;" @click="btnCapNhat_Click" v-if="btnCapNhat.Visible">
              <span class="icon one-save"></span> Cập nhật
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="title padt5 padb25 bold h5">
          TẠO TÀI KHOẢN KHÁCH HÀNG ĐẠI LÝ
        </div>       
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="box-form">
              <div class="legend-title">Thông tin khách hàng</div>
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ txtTenKH.Text }}</div>
                    <div class="value">
                      <input type="text" class="form-control" v-bind:value="txtTenKH.value"
                        @change="(e) => (txtTenKH.value = e.target.value)" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ txtMoTa.Text }}</div>
                    <div class="value">
                      <input type="text" class="form-control" v-bind:value="txtMoTa.value"
                        @change="(e) => (txtMoTa.value = e.target.value)" />                      
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w20 nowrap fw6">
                      <div class="check-action">
                        <input type="checkbox" class="check" v-model="chkLimitMT.Checked">
                        <span class="name">Limit MT</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>            
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ cboHTThanhToan.Text }}</div>
                    <div class="value">
                      <div class="select-custom" ref="dspt_HTThanhToan">
												<select2 :z-index="2000" :settings="{ dropdownParent: $refs['dspt_HTThanhToan'] }" placeholder="Chưa chọn..."	v-model="cboHTThanhToan.value" 
                          :options="cboHTThanhToan.DataSource.length > 0 ? cboHTThanhToan.DataSource.map(e => ({id: e.PAYMENT_TYPE_ID, text: e.PAYMENT_TYPE})) : []" >
												</select2>
											</div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ txtSoTienDatCoc.Text }}</div>
                    <div class="value">
                      <input type="text" class="form-control tright" v-bind:value="cp_tienDC" :disabled="!txtSoTienDatCoc.Enabled"
                        @change="(e) => (cp_tienDC = e.target.value)" @keypress="isNumber($event)"/>                      
                    </div>
                  </div>
                </div>
              </div>   
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ cboLoaiHD.Text }}</div>
                    <div class="value">
                      <div class="select-custom" ref="dspt_loaiHD">
												<select2 :z-index="2000" :settings="{ dropdownParent: $refs['dspt_loaiHD'] }" placeholder="Chưa chọn..." v-model="cboLoaiHD.value" 
                          :options="cboLoaiHD.DataSource.length > 0 ? cboLoaiHD.DataSource.map(e => ({id: e.CONTRACT_TYPE_ID, text: e.CONTRACT_TYPE})) : []" >
												</select2>
											</div>
                    </div>
                  </div>
                </div>


                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ txtGlobalTitle.Text }}</div>
                    <div class="value">
                      <input type="text" class="form-control" v-bind:value="txtGlobalTitle.value"
                        @change="(e) => (txtGlobalTitle.value = e.target.value)" />                      
                    </div>
                  </div>
                </div>
              </div>      
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ txtDiaChi.Text }}</div>
                    <div class="value">
                      <textarea
                        ref="txtDiaChi"
                        class="form-control"
                        style="height: 100px; resize: vertical"
                        v-bind:value="txtDiaChi.value"
                        @change="(e) => (txtDiaChi.value = e.target.value)"
                      ></textarea>
                    </div>
                  </div>
                </div>
              </div>      
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ txtSoGiayTo.Text }}</div>
                    <div class="value">
                      <input type="text" class="form-control" v-bind:value="txtSoGiayTo.value"
                        @change="(e) => (txtSoGiayTo.value = e.target.value)" />
                    </div>
                  </div>
                </div>
              </div>  
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ txtSoDienThoai.Text }}</div>
                    <div class="value">
                      <input type="text" class="form-control" v-bind:value="txtSoDienThoai.value"
                        @change="(e) => (txtSoDienThoai.value = e.target.value)" />                      
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ txtEmail.Text }}</div>
                    <div class="value">
                      <input type="text" class="form-control" v-bind:value="txtEmail.value"
                        @change="(e) => (txtEmail.value = e.target.value)" />                      
                    </div>
                  </div>
                </div>
              </div>       
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ txtSoHopDong.Text }}</div>
                    <div class="value">
                      <input type="text" class="form-control" v-bind:value="txtSoHopDong.value"
                        @change="(e) => (txtSoHopDong.value = e.target.value)" />                      
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ dtpNgayKyHD.Text }}</div>
                    <div class="value">
                      <DatePicker
                        ref="dtpNgayKyHD"
                        :format="dateFormat"
                        :value-type="'format'"
                        v-model="dtpNgayKyHD.value"
                        :disabled="!dtpNgayKyHD.Enabled"
                        @change="onChangeNgayKyHD"
                      >
                      </DatePicker>               
                    </div>
                  </div>
                </div>
              </div>  
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ txtTenHopDong.Text }}</div>
                    <div class="value">
                      <input type="text" class="form-control" v-bind:value="txtTenHopDong.value"
                        @change="(e) => (txtTenHopDong.value = e.target.value)" />
                    </div>
                  </div>
                </div>
              </div>   
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ dtpNgayBD.Text }}</div>                    
                    <div class="value">
                      <DatePicker
                        ref="dtpNgayBD"
                        :format="dateFormat"
                        :value-type="'format'"
                        v-model="dtpNgayBD.value"
                        :disabled="!dtpNgayBD.Enabled"
                        @change="onChangeNgayBD"
                      >
                      </DatePicker>                   
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ dtpNgayKT.Text }}</div>
                    <div class="value">
                      <DatePicker
                        ref="dtpNgayKT"
                        :format="dateFormat"
                        :value-type="'format'"
                        v-model="dtpNgayKT.value"
                        :disabled="!dtpNgayKT.Enabled"
                        @change="onChangeNgayKT"
                      >
                      </DatePicker>                  
                    </div>
                  </div>
                </div>
              </div>  
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ txtUsername.Text }}</div>
                    <div class="value">
                      <input type="text" class="form-control" v-bind:value="txtUsername.value"
                        @change="(e) => (txtUsername.value = e.target.value)" />
                    </div>
                  </div>
                </div>
              </div>   
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">{{ txtMatKhau.Text }}</div>
                    <div class="value">
                      <input type="text" class="form-control" v-bind:value="txtMatKhau.value"
                        @change="(e) => (txtMatKhau.value = e.target.value)" />
                    </div>
                  </div>
                </div>
              </div>       
            </div>
          </div>
        </div>        
      </div>
    </div>
  </b-modal>
</template>

<script>
import moment from "moment";
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import api from "./PopUpApi.js";
import DataGrid from "@/components/Controls/DataGrid";

export default {
  name: "frm_Create_Agent_SMPP",
  components: { DatePicker, DataGrid },
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
        ngay_cn: moment(new Date()).format("DD/MM/YYYY"),
      },
      dateFormat: "DD/MM/YYYY",
      txtTenKH: { Text: "Tên khách hàng", value: "" },
      chkLimitMT: { Checked: false},
      txtMoTa: { Text: "Người tạo", value: ""},
      cboHTThanhToan: { Text: "Hình thức thanh toán", value: "", DataSource: []},
      txtSoTienDatCoc: { Text: "Số tiền đặt cọc", value: "", Enabled: false},
      cboLoaiHD: { Text: "Loại hợp đồng", value: "", DataSource: []},
      txtGlobalTitle: { Text: "Global Title", value: ""},
      txtDiaChi: { Text: "Địa chỉ", value: ""},
      txtSoGiayTo: { Text: "Số giấy tờ", value: ""},
      txtSoDienThoai: { Text: "Số điện thoại", value: ""},
      txtEmail: { Text: "Email", value: ""},
      txtSoHopDong: { Text: "Số hợp đồng", value: ""},
      dtpNgayKyHD: { Text: "Ngày ký hợp đồng", value: "", Enabled: true},
      txtTenHopDong: { Text: "Tên hợp đồng", value: ""},
      dtpNgayBD: { Text: "Ngày bắt đầu", value: "", Enabled: true},
      dtpNgayKT: { Text: "Ngày kết thúc", value: "", Enabled: true},
      txtUsername: { Text: "Username", value: ""},
      txtMatKhau: { Text: "Password", value: ""},
      adser_id: 0,
      agent_id: 0,
      contract_id: 0,
      vkieu: 0,// 1 cap nhat
      send_type: 0,
    };
  },
  methods: {
    frm_BRN_TaoKHDL_Load: async function () {
      try {
        this.hdtb_id = this.vhdtb_id ? Number(this.vhdtb_id) : -1;
        // var endpoint = await this.fn_khaibao_api(this.hdtb_id);
        // brn_service = new BrandnameService(endpoint, vhdtb_id);
        await this.LoadThongTin();
        await this.LoadThongTinKhachHang();
      } catch (e) {}
    },
    fn_khaibao_api: async function (vhdtb_id) {
      try {        
        let response = await api.fn_khaibao_api(this.axios, vhdtb_id);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          return response.data.data
        } else {
          this.ShowError(response.data.message);
          return
        }
      } catch (ex) {
        console.log(ex);
        return
      }
    },
    LoadThongTinKhachHang: async function () {
      try {
        var dt = await this.sp_lay_thongtin_khoitao_acc_khdl(this.hdtb_id);
        if (dt && dt.length > 0) {
          if (dt[0]["vkieu"].toString() == "0")
          {
            this.btnNhapMoi.Visible = true;
            this.btnCapNhat.Visible = false;
          }
          else
          {
            this.btnNhapMoi.Visible = false;
            this.btnCapNhat.Visible = true;
          }
          var row = dt[0];
          this.txtTenKH.value = row["agent_name"];
          this.txtMoTa.value = row["description"];
          this.cboHTThanhToan.value = row["payment_type"] ? Number(row["payment_type"]) : 0;
          this.txtDiaChi.value = row["address"];
          this.txtSoHopDong.value = row["paper_number"];
          this.txtSoDienThoai.value = row["phone_number"];
          this.txtEmail.value = row["email"];
          this.txtSoTienDatCoc.value = row["deposits"];
          this.txtTenHopDong.value = row["contract_name"];
          this.dtpNgayKyHD.value = row["contract_date"];
          this.dtpNgayBD.value = row["contract_start_date"];
          this.dtpNgayKT.value = row["contract_end_date"];
          this.txtSoGiayTo.value = row["paper_number"];
          this.txtUsername.value = row["username"];
          this.txtMatKhau.value = row["password"];
          this.txtGlobalTitle.value = row["global_title"];
          this.cboLoaiHD.value = row["contract_type"] ? Number(row["contract_type"]) : 0;
          this.agent_id = Number(row["agent_id"]);
          this.adser_id = Number(row["adser_id"]);
          this.contract_id = Number(row["contract_id"]);
          this.chkLimitMT.Checked = row["limit_mt"] == 1 ? true : false;
          this.send_type = Number(row["send_type"]);
        }
      } catch (e) {}
    },
    sp_lay_thongtin_khoitao_acc_khdl: async function (vhdtb_id) {
      try {        
        let response = await api.sp_lay_thongtin_khoitao_acc_khdl(this.axios, vhdtb_id);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          return response.data.data
        } else if (response.data.error_code == "BSS-00000204") {

        } else {
          this.ShowError(response.data.message);
          return
        }
      } catch (ex) {
        console.log(ex);
        return
      }
    },
    LoadThongTin: async function () {
      await this.sp_lay_danhmuc_khoitao_acc_khdl();
    },
    sp_lay_danhmuc_khoitao_acc_khdl: async function () {
      try {        
        this.cboLoaiHD.DataSource = []
        this.cboHTThanhToan.DataSource = []
        let response = await api.sp_lay_danhmuc_khoitao_acc_khdl(this.axios);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {          
          if (response.data.data) {   
            this.cboLoaiHD.DataSource = this.UpperCasePropertyList(response.data.data["contract_type"]);
            this.cboHTThanhToan.DataSource = this.UpperCasePropertyList(response.data.data["payment_type"]);
          }          
        } else if (response.data.error_code == "BSS-00000204") {

        } else {
          this.ShowError(response.data.message);          
        }
      } catch (ex) {
        console.log(ex);        
      }
    },
    TAO_KHTT: async function (data) {
      try {       
        var input = {
          hdtb_id: this.hdtb_id,
          agent_id: this.agent_id,
          adser_id: this.adser_id,
          contract_id: this.contract_id,
          username: this.txtUsername.value,
          password: this.txtMatKhau.value,
          json: JSON.stringify(data)
        } 
        const link_tao_kh = "/tichhop/a2p/create_agent";
        const kq_media = await this.axios.post(link_tao_kh, data);
        if(!(kq_media.data && kq_media.data.data.message == 'OK')) {
          this.$toast.error('Có lỗi xảy ra. Liên hệ admin để được hỗ trợ. \nChi tiết lỗi: ' + JSON.stringify(kq_media.data.data)); 
          return;
        } else {
          input.agent_id = Number(kq_media.data.data.data.agentInfo.agent_id);          
          input.contract_id = Number(kq_media.data.data.data.agentInfo.contract_id);
        }

        let response = await api.fn_capnhat_khachhang_daily_brn(this.axios, input);        
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          this.ShowSuccess("Tạo khách hàng đại lý thành công");
        } else {
          this.ShowError(response.data.message + ". Liên hệ admin để được hỗ trợ");
        }
      } catch (ex) {
        console.log(ex);
        this.ShowError(ex + ". Liên hệ admin để được hỗ trợ");
      }
    },
    UPDATE_KHTT: async function (data) {
      try {       
        var input = {
          hdtb_id: this.hdtb_id,
          json: JSON.stringify(data)
        } 
        let response = await api.fn_capnhat_thongtin_khachhang_daily_brn(this.axios, input);        
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          this.ShowSuccess("Cập nhật khách hàng đại lý thành công");
        } else {
          this.ShowError(response.data.message + ". Liên hệ admin để được hỗ trợ");
        }
      } catch (ex) {
        console.log(ex);
        this.ShowError(ex + ". Liên hệ admin để được hỗ trợ");
      }
    },
    btnNhapMoi_Click: async function () {
      try {
        var data = {
          agent_name: this.txtTenKH.value,
          description: this.txtMoTa.value,
          username: this.txtUsername.value,
          agent_type: 0,
          contract_type_id: this.cboLoaiHD.value ? Number(this.cboLoaiHD.value) : 0,
          province_id: this.tt_nd.phanvung_id,
          payment_type_id: this.cboHTThanhToan.value ? Number(this.cboHTThanhToan.value) : 0,
          address: this.txtDiaChi.value,
          paper_number: this.txtSoGiayTo.value,
          phone_number: this.txtSoDienThoai.value,
          email: this.txtEmail.value,
          status: 1,
          limit_mt: this.chkLimitMT.Checked ? 1 : 0,
          deposits: Number(this.txtSoTienDatCoc.value),
          contract_number: this.txtSoHopDong.value,
          contract_name: this.txtTenHopDong.value,
          contract_date: this.dtpNgayKyHD.value,
          contract_end_date: this.dtpNgayKyHD.value,
          contract_start_date: this.dtpNgayBD.value,
          send_type: this.send_type,
          // send_type: 2
        };
        if(data != null) {          
          await this.TAO_KHTT(data);  
                 
        }
        this.LoadThongTinKhachHang();
      } catch (e) {
        console.log(e)
      }
    },
    btnCapNhat_Click: async function () {
      try {
        var objCapnhatKHTT = {
          agent_name: this.txtTenKH.value,
          description: this.txtMoTa.value,
          username: this.txtUsername.value,
          agent_type: 0,
          contract_type_id: Number(this.cboLoaiHD.value),
          province_id: this.tt_nd.phanvung_id,
          payment_type_id: Number(this.cboHTThanhToan.value),
          address: this.txtDiaChi.value,
          paper_number: this.txtSoGiayTo.value,
          phone_number: this.txtSoDienThoai.value,
          email: this.txtEmail.value,
          status: 1,
          limit_mt: this.chkLimitMT.Checked ? 1 : 0,
          deposits: Number(this.txtSoTienDatCoc.value),
          contract_number: this.txtSoHopDong.value,
          contract_name: this.txtTenHopDong.value,
          contract_date: this.dtpNgayKyHD.value,
          contract_end_date: this.dtpNgayKyHD.value,
          contract_start_date: this.dtpNgayBD.value,
          adser_id: this.adser_id,
          agent_id: this.agent_id
        };
        if (objCapnhatKHTT != null && this.agent_id != 0)
        {
          await this.UPDATE_KHTT(objCapnhatKHTT);          
        }
        this.LoadThongTinKhachHang();
      } catch (e) {
        console.log(e)
      }
    },
    btnThoat_Click: function () {
      this.hideModal();
    },
    cboHTThanhToan_EditValueChanged: function () {
      if (this.cboHTThanhToan.value.toString() == "0")
      {
        this.txtSoTienDatCoc.Enabled = true;
      } else {
        this.txtSoTienDatCoc.Enabled = false;
        this.txtSoTienDatCoc.value = "0";
      }
    },
    onChangeNgayKyHD(value_) {
      if (!moment(value_, "DD/MM/YYYY").isValid()) {
        this.dtpNgayKyHD.value = "01/01/2000";
      } else {
        this.dtpNgayKyHD.value = value_;
      }
    },
    onChangeNgayBD(value_) {
      if (!moment(value_, "DD/MM/YYYY").isValid()) {
        this.dtpNgayBD.value = "01/01/2000";
      } else {
        this.dtpNgayBD.value = value_;
      }
    },
    onChangeNgayKT(value_) {
      if (!moment(value_, "DD/MM/YYYY").isValid()) {
        this.dtpNgayKT.value = "01/01/2000";
      } else {
        this.dtpNgayKT.value = value_;
      }
    },
    clear() {
      this.txtTenKH.value = ""
      this.chkLimitMT.Checked = false
      this.txtMoTa.value = ""
      this.cboHTThanhToan.value = 0
      this.txtSoTienDatCoc.value = ""
      this.cboLoaiHD.value = 0
      this.txtDiaChi.value = ""
      this.txtSoGiayTo.value = ""
      this.txtSoDienThoai.value = ""
      this.txtEmail.value = ""
      this.txtSoHopDong.value = ""
      this.dtpNgayKyHD.value = ""
      this.txtTenHopDong.value = ""
      this.dtpNgayBD.value = ""
      this.dtpNgayKT.value = ""
      this.txtUsername.value = ""
      this.txtMatKhau.value = ""
      this.adser_id = 0
      this.agent_id = 0
      this.contract_id = 0
    },
    showModal: async function () {
      this.clear();
      this.$refs["frm_Create_Agent_SMPP"].show();
      this.loading(true);
      try {
        this.tt_nd.phanvung_id = Number(this.$root.token.getPhanVungID());
        this.tt_nd.nhanvien_id = Number(this.$root.token.getNhanVienID());
        this.tt_nd.may_cn = await this.$root.token.getMachine();
        this.tt_nd.nguoi_cn = await this.$root.token.getMaNhanVien();
        this.tt_nd.ip_cn = await this.$root.token.getIP();

        await Promise.all([this.frm_BRN_TaoKHDL_Load()]);
        this.loading(false);
      } catch (ex) {
        this.loading(false);
      }
    },
    hideModal: function () {
      this.$refs["frm_Create_Agent_SMPP"].hide();
    },
    GetData: function (response) {
      if (response.data && response.data.error_code == "BSS-00000000") {
        return response.data.data;
      } else {
        // console.log(response.data.error_code);
        // this.ShowError(response.data.message);
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
  },
  mounted() {},
  computed: {
    cp_tienDC: {
      get() {
        return this.numberWithCommas(this.txtSoTienDatCoc.value);
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.txtSoTienDatCoc.value = Number(replaced);
      },
    },
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