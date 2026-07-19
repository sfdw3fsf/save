<style scoped>
.list-actions-top .list > li > div.button {
  display: block;
  padding: 2px 15px;
  color: #0176ff;
  border-left: 1px solid #bae7ff;
  text-decoration: none;
  font-weight: 600;
  cursor: pointer;
}
</style>

<template>
  <ejs-dialog
    :enableResize="true"
    :isModal="true"
    :allowDragging="true"
    :visible="false"
    :animationSettings="animationSettings"
    ref="dialogKichhoatBrandname"
    :position="position"
    :header="'Kích hoạt đăng ký Brandname'"
    showCloseIcon="true"
    width="75%"
    target="#app .main-wrapper"
  >
   
    <div class="dialogCreateIssue">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a
              href="javascript:;"
              v-on:click="btnNhapMoi_Click"
              :class="{ disabled: !btnNhapMoi.Enabled }"
            >
              <span class="icon one-file-attach"></span> Gửi yêu cầu
            </a>
          </li>
          <li>
            <a
              href="javascript:;"
              v-on:click="btnCapNhat_Click"
              :class="{ disabled: !btnCapNhat.Enabled }"
            >
              <span class="icon one-save"></span> Hoàn thành
            </a>
          </li>
         
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin brandname</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="row">
                <div class="col-sm-7 col-12">
                  <div class="info-row">
                    <div class="key">Hợp đồng</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control"
                        v-model="controls.hopdong"
                      />
                    </div>
                  </div>
                </div>
                <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key">Mã thuê bao</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="controls.ma_tb" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key">Tên khách hàng</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="controls.ten_kh" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key">Tên nhãn</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control"
                        v-model="controls.ten_nhan"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-7 col-12">
                  <div class="info-row">
                    <div class="key">Ngày hết hạn</div>
                    <div class="value">
                      <div class="input-icon-right">
                        <date-picker
                          input-class="form-control"
                          :format="dateFormat"
                          :value-type="'format'"
                          v-model="controls.ngay_hh"
                        ></date-picker>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key">Mã số thuế</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control"
                        v-model="controls.ma_so_thue"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key">File hồ sơ</div>
                    <!-- <div class="value">                                   
                                  <SelectExt v-model="controls.cboHoSo.value" :allowFiltering="true" dataTextField="file_name"
                                              dataValueField="file_path" :dataSource="controls.cboHoSo.list">
                                  </SelectExt>
                                  
                              </div> -->
                    <div class="value select-custom">
                      <ejs-multiselect
                        v-model="controls.cboHoSo.value"
                        id="ds_nguyennhan_id"
                        :dataSource="controls.cboHoSo.list"
                        placeholder="Chọn file"
                        selectAllText="Chọn tất cả"
                        :showSelectAll="true"
                        locale="vi-VN"
                        :showDropDownIcon="true"
                        unSelectAllText="Bỏ chọn tất cả"
                        mode="CheckBox"
                        :fields="{ text: 'file_name', value: 'file_path' }"
                      >
                      </ejs-multiselect>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key">Ghi chú</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="controls.ghichu" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key">Loại nhãn Vinaphone</div>
                    <div class="value">
                      <SelectExt
                        v-model="controls.cboLoaiVina.value"
                        :allowFiltering="true"
                        dataTextField="label_type_name"
                        dataValueField="label_type_id"
                        :dataSource="controls.cboLoaiVina.list"
                      >
                      </SelectExt>
                    </div>
                  </div>
                </div>
                
              </div>
           
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key">Loại nhãn Itel</div>
                    <div class="value">
                      <SelectExt
                        v-model="controls.cboLoaiItel.value"
                        :allowFiltering="true"
                        dataTextField="label_type_name"
                        dataValueField="label_type_id"
                        :dataSource="controls.cboLoaiItel.list"
                      >
                      </SelectExt>
                    </div>
                  </div>
                </div>
                
              </div>
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key">Loại nhãn Mobicast</div>
                    <div class="value">
                      <SelectExt
                        v-model="controls.cboLoaiMobicast.value"
                        :allowFiltering="true"
                        dataTextField="label_type_name"
                        dataValueField="label_type_id"
                        :dataSource="controls.cboLoaiMobicast.list"
                      >
                      </SelectExt>
                    </div>
                  </div>
                </div>
                
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Yêu cầu tạo Brandname</div>
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
  </ejs-dialog>
</template>
<script>
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import moment from "moment";
import api from "./PopUpApi";
import { DICHVU_VIENTHONG, TRANGTHAI_PH } from "@/constants";
import commonFn from "@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions";
import frmBRNGuiMail from "@/modules/contract/complete_profile/CompleteProfileITVAS/popups/frmBRNGuiMail/frmBRNGuiMail.vue";
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue";
import { data } from "vue-barcode";

export default {
  components: {
    moment,
    DatePicker,
    api,
    SearchContractModal,
    frmBRNGuiMail,
  },
  name: "frm_Create_Label_A2p",
  data() {
    return {
      hdtb_id: 0,
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: "",
      popupComponentAttr: {},
      popupComponentEvts: {
        "form-close": this.popupClosed,
        acceptSearchContract: this.acceptSearchContract,
      },
      Loading: false,
      dateFormat: "DD/MM/YYYY",
      dateFormatMonth: "MM/YYYY",
      typeFormat: "datetime",
      typeFormatMonth: "month",
      dateConfig: {
        dateFormat: "d/m/Y",
        altFormat: "d/m/Y",
      },
      target: "#app", //  .main-wrapper-payment
      targetTop: "#app", // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: "Zoom" },
      position: { X: "center", Y: "top" },
      showTimePanel: false,
      tabIndex: 0,
      state: {},
      label_id: 0,
      contract_type_id: 0,
      agent_type_id: 0,
      contract_id: 0,
      adser_id: 0,
      agent_id: 0,
      dic: [
        { id: -2, name: "Đã xóa" },
        { id: -1, name: "Đã khóa" },
        { id: 0, name: "Chờ duyệt" },
        { id: 1, name: "Đã duyệt" },
        { id: 2, name: "Từ chối" },
        { id: 3, name: "Đang cập nhật" },
        { id: 4, name: "" },
      ],
      dt: null,
      tt_nd: {
        nhanvien_id: this.$root.token.getNhanVienID() * 1,
        nguoidung_id: this.$root.token.getNguoiDungID() * 1,
        phanvung_id: this.$root.token.getPhanVungID() * 1,
        donvi_id: this.$root.token.getDonViID() * 1,
        may_cn: this.$root.token.getMachine(),
        ip_cn: this.$root.token.getIP(),
        ngay_cn: moment(new Date()).format("DD/MM/YYYY hh:mm A"),
        ngay_bg: moment(new Date().setSeconds(new Date().getSeconds() + 30)).format(
          "DD/MM/YYYY hh:mm A"
        ),
        ten_dv: "",
        nguoi_cn: this.$root.token.getUserName(),
      },

      controls: {
        cboLoaiVina: { list: [], value: 0, Enabled: true },
        cboLoaiItel: { list: [], value: 0, Enabled: true },
        cboLoaiMobicast: { list: [], value: 0, Enabled: true },
        cboHoSo: { list: [], value: 0 },
        gia_vinaphone: "",
        gia_itel: "",
        gia_mbicast: "",
        ghichu: "",
        ma_tb: "",
        thuebao_ao: "",
        ngay_hh: "",
        ma_so_thue: "",
        ten_kh: "",
        ten_nhan: "",
        hopdong: "",
      },
      btnCapNhat: { Enabled: true },
      tsbtnUpdate: { Enabled: true },
      tsbtnGuiMailKH: { Enabled: true },
      btnNhapMoi: { Enabled: true },
      grcDanhSach: {
        headers: [
          {
            fieldName: "label_id",
            headerText: "Nhãn ID",
            allowFiltering: true,
            isGroupedColumn: true,
          },
          { fieldName: "label", headerText: "Thương hiệu", allowFiltering: true },
          {
            fieldName: "expired_date",
            headerText: "Ngày hết hạn",
            allowFiltering: true,
            type: "dateTime",
            format: "dd/MM/yyyy HH:mm:ss",
            textAlign: "right",
          },
          {
            fieldName: "status_name",
            headerText: "Trạng thái Vinaphone",
            allowFiltering: true,
          },
          {
            fieldName: "status_itel_name",
            headerText: "Trạng thái Itel",
            allowFiltering: true,
          },
          {
            fieldName: "status_mobicast_name",
            headerText: "Trạng thái Mobicast",
            allowFiltering: true,
          },
          {
            fieldName: "reason_reject",
            headerText: "Lý do từ chôi",
            allowFiltering: true,
          },
        ],
        list: [],
      },
    };
  },
  created: async function () {},
  mounted: function () {},
  watch: {
    "controls.cboLoaiVina.value": async function (newValue, oldValue) {
      console.log("controls.cboLoaiVina.value");
      console.log(newValue);
      //todo: check lại result trả về
      let gia = await this.cboLoaiVina_EditValueChanged(newValue, 1);

      this.controls.gia_vinaphone = parseInt(gia);
    },
   
    "controls.cboLoaiItel.value": async function (newValue, oldValue) {
      console.log("controls.cboLoaiItel.value");
      console.log(newValue);
      let gia = await this.cboLoaiVina_EditValueChanged(newValue, 7);
      this.controls.gia_itel = parseInt(gia);
    },
    "controls.cboLoaiMobicast.value": async function (newValue, oldValue) {
      console.log("controls.cboLoaiMobicast.value");
      console.log(newValue);
      let gia = await this.cboLoaiVina_EditValueChanged(newValue, 8);
      this.controls.gia_mbicast = parseInt(gia);
    },
  },
  methods: {
    dialogClose() {},
    openDialog(data) {
      if (!data) {
        return;
      }
      this.state = {
        vhdtb_id: data.hdtb_id,
      };
      this.hdtb_id = data.hdtb_id;
      console.log(this.state);
      this.$refs.dialogKichhoatBrandname.show();
      this.pageLoad();
    },
    hideDialog() {
      this.$refs.dialogKichhoatBrandname.hide();
    },

    async pageLoad() {
      //frm_BRN_KichHoat_Brandname_Load
      try {
        await this.loading(true);
        this.tt_nd.may_cn = await this.$root.token.getMachine();
        this.tt_nd.ip_cn = await this.$root.token.getIP();
        await this.LoadDanhMuc();
        if (this.agent_type_id == 1 && this.contract_type_id == 2) {
          this.controls.cboLoaiItel.value = 0;
          //cboLoaiItel.SelectByValue(0); todo gọi event
          this.controls.cboLoaiItel.Enabled = false;
        }
        this.LoadThongTinBrandname();
        this.LoadThongtinTrangthaiBRN();
      } catch (ex) {
        console.error(ex);
        this.$toast.error(ex.message);
        console.error(ex.message);
      }
      await this.loading(false);
    },
    async LoadDanhMuc() {
      let ds = this.GetData(
        await api.sp_load_dulieu_form_tao_brn(this.axios, this.hdtb_id)
      );
      console.log(ds);
      if (!ds || ds.length == 0) {
        console.log("ds length 0 why????");
        return;
      }
      console.log("log 1");
      //ds = this.LowerCasePropertyList(ds);
      this.controls.cboHoSo.list = ds.result_ds_hoso;
      if (ds.result_data.length > 0) {
        const dt = ds.result_data[0];
        this.agent_id = parseInt(dt["agent_id"]);
        this.adser_id = parseInt(dt["adser_id"]);
        this.contract_id = parseInt(dt["contract_id"]);
        this.agent_type_id = parseInt(dt["agent_type_id"]);
        this.contract_type_id = parseInt(dt["contract_type_id"]);
        this.label_id = parseInt(dt["label_id"]);
      }
      console.log("log 2");
      if (this.label_id != 0) this.SetButton(2);
      else this.SetButton(1);
      //HT_DS_LINHVUC_NHAMANG(cboLoaiVina, 1);
      console.log("log 3");
      let data = await api.LAY_DS_LINHVUC(this.axios, {
        telco_id: 1,
        contract_type_id: this.contract_type_id,
        agent_type: this.agent_type_id,
      });
      console.log("vina");
      console.log(data);
      if (
        data &&
        data.data &&
        data.data.data &&
        data.data.data.data &&
        data.data.data.data.items &&
        data.data.data.data.items.length > 0
      ) {
        console.log("here");
        console.log(data.data.data.data.items);
        this.controls.cboLoaiVina.list = data.data.data.data.items;
      }
  
      //HT_DS_LINHVUC_NHAMANG(cboLoaiItel, 7);
      data = await api.LAY_DS_LINHVUC(this.axios, {
        telco_id: 7,
        contract_type_id: this.contract_type_id,
        agent_type: this.agent_type_id,
      });
      if (
        data &&
        data.data &&
        data.data.data &&
        data.data.data.data &&
        data.data.data.data.items &&
        data.data.data.data.items.length > 0
      ) {
        this.controls.cboLoaiItel.list = data.data.data.data.items;
      }
      //HT_DS_LINHVUC_NHAMANG(cboLoaiMobicast, 8);
      data = await api.LAY_DS_LINHVUC(this.axios, {
        telco_id: 8,
        contract_type_id: this.contract_type_id,
        agent_type: this.agent_type_id,
      });
      if (
        data &&
        data.data &&
        data.data.data &&
        data.data.data.data &&
        data.data.data.data.items &&
        data.data.data.data.items.length > 0
      ) {
        this.controls.cboLoaiMobicast.list = data.data.data.data.items;
      }
    },
    SetButton(kieu) {
      if (kieu == 1) {
        this.btnNhapMoi.Enabled = true;
        this.btnCapNhat.Enabled = false;
        this.tsbtnGuiMailKH.Enabled = false;
        this.tsbtnUpdate.Enabled = false;
      } else {
        this.btnNhapMoi.Enabled = false;
        this.btnCapNhat.Enabled = true;
        this.tsbtnGuiMailKH.Enabled = true;
        this.tsbtnUpdate.Enabled = true;
      }
      this.controls.cboHoSo.value = [];
    },
    async LoadThongTinBrandname() {
      this.Loading = true;
      let dt = this.GetData(
        await api.sp_lay_thongtin_dangky_brandname(this.axios, this.hdtb_id)
      );
      if (dt && dt.length > 0) {
        this.controls.hopdong = dt[0]["ten_pl"];
        this.controls.ten_nhan = dt[0]["label"];
        this.controls.ten_kh = dt[0]["customer"];
        this.controls.ma_so_thue = dt[0]["taxcode"];
        this.controls.ngay_hh = dt[0]["expired_date"];
        this.controls.thuebao_ao = dt[0]["mobile_alias"];
        this.controls.ghichu = dt[0]["note"];
        this.controls.ma_tb = dt[0]["username"];

        this.controls.cboLoaiVina.value =
          dt[0]["label_type_vnp"] != 0 ? dt[0]["label_type_vnp"] : -1;
        this.controls.cboLoaiItel.value =
          dt[0]["label_type_itel"] != 0 ? dt[0]["label_type_itel"] : -1;
        this.controls.cboLoaiMobicast.value =
          dt[0]["label_type_mobicast"] != 0 ? dt[0]["label_type_mobicast"] : -1;

        this.controls.gia_vinaphone = dt[0]["cuoc_sms_vnp"];
        this.controls.gia_vnm = dt[0]["cuoc_sms_itel"];
        this.controls.gia_mbicast = dt[0]["cuoc_sms_mobicast"];
      }
    },
    async LoadThongtinTrangthaiBRN() {
      if (this.label_id != 0) {
        var res = await api.LAY_DS_NHAN(this.axios, {
          adser_id: this.adser_id,
          agent_id: this.agent_id,
          contract_id: this.contract_id,
          agent_type: this.agent_type_id,
        });
        console.log(res);
        if (!res || !res.data) {
          return;
        }
        var data = res.data.data.data.items;
        console.log(data);
        this.dt = data.filter((item) => item.label_id == this.label_id);
        if (!this.dt || this.dt.length == 0) {
          return;
        }
        for (let i = 0; i < this.dt.length; i++) {
          let temp = this.dt[i].label_type;

          this.dt[i].status_name = this.dic.filter(
            (item) =>
              item.id ==
              (temp == "0" || temp == "" || temp == "-1"
                ? 4
                : parseInt(this.dt[i].status))
          )[0].name;

  

          temp = this.dt[i].label_type_itel;
          this.dt[i].status_itel_name = this.dic.filter(
            (item) =>
              item.id ==
              (temp == "0" ||
              temp == "" ||
              temp == "-1" 
              // ||
              // this.controls.cboLoaiItel.value == "-1"
                ? 4
                : parseInt(this.dt[i].status_itel))
          )[0].name;

          temp = this.dt[i].label_type_mobicast;
          this.dt[i].status_mobicast_name = this.dic.filter(
            (item) =>
              item.id ==
              (temp == "0" ||
              temp == "" ||
              temp == "-1" 
              // ||
              // this.controls.cboLoaiMobicast.value == "-1"
                ? 4
                : parseInt(this.dt[i].status_mobicast))
          )[0].name;
        }
        this.grcDanhSach.list = this.dt;
      }
    },
    async gridChitiet_FocusedRowChanged(row) {
      if (!row) return;
    },
    async GetFileURL(url) {
      return await this.axios.post(`/web-quantri/upload/link_view_file_v2`, {
        list_file: url,
      });
    },
    async btnNhapMoi_Click() {
      //gửi yêu cầu
      var dsfile = [];
      console.log("controls.cboHoSo.value");
      console.log(this.controls.cboHoSo.value);

      if (this.controls.cboHoSo.value.length > 0) {
        for (let i = 0; i < this.controls.cboHoSo.value.length; i++) {
          let item = this.controls.cboHoSo.list.find(
            (x) => x.file_path.indexOf(this.controls.cboHoSo.value[i]) != -1
          );
          if (item) {
            if (
              item.file_path.indexOf(
                "https://storage-onebss.vnpt.vn/onebss-production/"
              ) == -1
            ) {
              var url = (await this.GetFileURL(item.file_path)).data.data[0].file_url;
              item.file_path = url;
            }
            dsfile.push(item);
          }
        }
      }

      let input = {
        hdtb_id: this.hdtb_id,
        agent_id: this.agent_id,
        adser_id: this.adser_id,
        contract_id: this.contract_id,
        contract_type_id: this.contract_type_id,
        label: this.controls.ten_nhan,
        label_id: this.label_id,
        label_type_vnp: this.controls.cboLoaiVina.value,
        label_type_itel: this.controls.cboLoaiItel.value,
        label_type_mobicast: this.controls.cboLoaiMobicast.value,
        customer: this.controls.ten_kh,
        taxCode: this.controls.ma_so_thue,
        username: this.controls.ma_tb,
        expired_date: this.controls.ngay_hh,
        note: this.controls.ghichu,
        mobile_alias: this.controls.thuebao_ao,
        ds_file: dsfile,
        gia_vnp: parseInt(this.controls.gia_vinaphone),
        gia_itel: parseInt(this.controls.gia_itel),
        gia_mbicast: parseInt(this.controls.gia_mbicast),
      };
      let ds = await api.TAOBRANDNAME(this.axios, input);
      if (ds && ds.data.data && ds.data.data.message != "Success") {
        this.$toast.error(ds.data.data.message + ". Liên hệ admin để được hỗ trợ");
        this.SetButton(1);
        return;
      }

      let obj = {
        vhdtb_id: this.hdtb_id,
        vidnhan: ds.data.data.data.label_id,
        vtennhan: this.controls.ten_nhan,
        vten_kh: this.controls.ten_kh,
        vmst: this.controls.ma_so_thue,
        vngayhethan: this.controls.ngay_hh,
        vthuebaoao: this.controls.thuebao_ao,
        vghichu: this.controls.ghichu,
        vloai_vnp: this.controls.cboLoaiVina.value,
        vloai_itel: this.controls.cboLoaiItel.value,
        vloai_mobicast: this.controls.cboLoaiMobicast.value,
        vsltu: null,
        vslden: null,
        ds_file: dsfile,
      };

      let result = await api.fn_update_thongtin_brn_tao_brn(this.axios, obj);
      if (result.data && result.data.data && result.data.data != "OK") {
        this.$toast.error(result.data.data + ". Liên hệ admin để được hỗ trợ");
        this.SetButton(1);
        return;
      } else {
        this.$toast.success(result.data.data);

        this.label_id = parseInt(ds.data.data.data.label_id);
        this.LoadThongtinTrangthaiBRN();
        this.SetButton(2);
        return;
      }
    },
    async btnCapNhat_Click() {
      console.log("controls.cboHoSo.value");
      console.log(this.controls.cboHoSo.value);
      var dt = this.dt;
      console.log("dt", dt);
      if (!dt || dt.length == 0) return;

      //todo
      // if (tdan.GET_VALUE_FUNC("{?DB2}.fn_co_kiemtra_trangthai_brn").ToString() == "OK")
      // {
      for (let i = 0; i < dt.length; i++) {
        debugger;
        let temp = dt[i].label_type;
        let temp2 = dt[i].status;
        if (temp != "0" && temp != "" && temp != "-1") {
          if (temp2 == "0" || temp2 == "3" || temp2 == "2") {
            this.$toast.error(
              "Trạng thái mạng Vinaphone đang duyệt, không thể hoàn thành"
            );
            return;
          }
        }

        temp = dt[i].label_type_itel;
        temp2 = dt[i].status_itel;
        if (
          temp != "0" &&
          temp != "" &&
          temp != "-1" &&
          this.controls.cboLoaiItel.value != "-1"
        ) {
          if (temp2 == "0" || temp2 == "3" || temp2 == "2") {
            this.$toast.error("Trạng thái mạng Itel đang duyệt, không thể hoàn thành");
            return;
          }
        }

        temp = dt[i].label_type_mobicast;
        temp2 = dt[i].status_mobicast;
        if (
          temp != "0" &&
          temp != "" &&
          temp != "-1" &&
          this.controls.cboLoaiMobicast.value != "-1"
        ) {
          if (temp2 == "0" || temp2 == "3" || temp2 == "2") {
            this.$toast.error(
              "Trạng thái mạng Mobicast đang duyệt, không thể hoàn thành"
            );
            return;
          }
        }
      }
      //}
      let trangthai = dt[0];
      console.log(dt[0]);
      let input = {
        vhdtb_id: this.hdtb_id,
        vjosn_trangthai: JSON.stringify(trangthai),
      };
      let kq = await api.fn_update_trangthai_hoancong_brn(this.axios, input);
      if (kq && kq.data && kq.data.data == "OK") {
        this.$toast.success("Ghi nhận thành công");
      }
    },
    async cboLoaiVina_EditValueChanged(value, telco_id) {
      if (!value) return;
      //todo check lại api
      let gia = await api.fn_lay_thongtin_gia_brn(this.axios, {
        vagent_type_id: this.agent_type_id,
        vcontract_type_id: this.contract_type_id,
        vtelco_id: telco_id,
        vlabel_type_id: value,
      });
      if (gia && gia.data && gia.data.data) {
        return gia.data.data;
      } else return "0";
    },
    async tsbtnGuiMailKH_Click() {
      // frm_BRN_GuiMail frm = new frm_BRN_GuiMail((long)hdtb_id);
      // frm.ShowDialog();
      {
        let data = {
          hdtb_id: this.hdtb_id,
        };
        this.$refs.frmBRNGuiMail.openDialog(data);
      }
    },
    async tsbtnUpdate_Click() {
      var dsfile = [];
      console.log("controls.cboHoSo.value");
      console.log(this.controls.cboHoSo.value);
      if (this.controls.cboHoSo.value.length > 0) {
        for (let i = 0; i < this.controls.cboHoSo.value.length; i++) {
          debugger;
          let item = this.controls.cboHoSo.list.find(
            (x) => x.file_path.indexOf(this.controls.cboHoSo.value[i]) != -1
          );
          if (item) {
            if (
              item.file_path.indexOf(
                "https://storage-onebss.vnpt.vn/onebss-production/"
              ) == -1
            ) {
              var url = (await this.GetFileURL(item.file_path)).data.data[0].file_url;
              item.file_path = url;
            }

            dsfile.push(item);
          }
        }
      }
      console.log("controls.cboHoSo.value");
      console.log(this.controls.cboHoSo.value);

      let input = {
        hdtb_id: this.hdtb_id,
        agent_id: this.agent_id,
        adser_id: this.adser_id,
        contract_id: this.contract_id,
        contract_type_id: this.contract_type_id,
        label: this.controls.ten_nhan,
        label_id: this.label_id,
        label_type_vnp: this.controls.cboLoaiVina.value,
        label_type_itel: this.controls.cboLoaiItel.value,
        label_type_mobicast: this.controls.cboLoaiMobicast.value,
        customer: this.controls.ten_kh,
        taxCode: this.controls.ma_so_thue,
        username: this.controls.ma_tb,
        expired_date: this.controls.ngay_hh,
        note: this.controls.ghichu,
        mobile_alias: this.controls.thuebao_ao,
        ds_file: dsfile,
        gia_vnp: parseInt(this.controls.gia_vinaphone),
        gia_itel: parseInt(this.controls.gia_itel),
        gia_mbicast: parseInt(this.controls.gia_mbicast),
      };
      let ds = await api.UPDATEBRANDNAME(this.axios, input);
      if (ds && ds.data.data && ds.data.data.message != "Success") {
        this.$toast.error(ds.data.data.message + ". Liên hệ admin để được hỗ trợ");
        this.SetButton(2);
        return;
      }

      let obj = {
        vhdtb_id: this.hdtb_id,
        vidnhan: this.label_id,
        vtennhan: this.controls.ten_nhan,
        vten_kh: this.controls.ten_kh,
        vmst: this.controls.ma_so_thue,
        vngayhethan: this.controls.ngay_hh,
        vthuebaoao: this.controls.thuebao_ao,
        vghichu: this.controls.ghichu,
        vloai_vnp: this.controls.cboLoaiVina.value,
        vloai_itel: this.controls.cboLoaiItel.value,
        vloai_mobicast: this.controls.cboLoaiMobicast.value,
        vsltu: null,
        vslden: null,
        vgia_vnp: parseInt(this.controls.gia_vinaphone),
        vgia_itel: parseInt(this.controls.gia_itel),
        vgia_mobicast: parseInt(this.controls.gia_mbicast),
        ds_file: dsfile,
      };

      let result = await api.fn_update_thongtin_brn_tao_brn(this.axios, obj);
      if (result.data && result.data.data && result.data.data != "OK") {
        this.$toast.error(result.data.data + ". Liên hệ admin để được hỗ trợ");
        this.SetButton(2);
        return;
      } else {
        this.$toast.success(result.data.data);
        this.LoadThongtinTrangthaiBRN();
        this.SetButton(2);
        return;
      }
    },
    async Clear() {},
    GetData: function (response) {
      if (
        response.data.error === 200 ||
        response.data.error === "200" ||
        response.data.error === "204" ||
        response.data.errorCode === 0
      ) {
        return response.data.data;
      } else {
        console.log(response.data.error_code);
        console.log(response.data.faultString);
        if (response.data && response.data.faultString) {
          throw response.data.faultString;
        }
      }
      return null;
    },

    ShowAlert: function (msg) {
      this.$toast.warning(msg);
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
    async popupClosed(val) {
      console.log("popupClosed", val);
    },
    LowerCasePropertyList(obj) {
      if (!obj) return [];
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
      if (!obj) return [];
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
};
</script>

<style>
.chuyenTo .page-content {
  top: 130px !important;
}
</style>
