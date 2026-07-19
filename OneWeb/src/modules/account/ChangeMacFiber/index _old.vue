<template>
  <div class="main-wrapper">
    <div class="breadcrumb-top">
      <div class="main-title">Tiếp nhận yêu cầu khoá máy cố định</div>
      <ul class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="#"
            ><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ
          </a>
        </li>
        <li class="breadcrumb-item"><a href="#">Lập hợp đồng</a></li>
        <li class="breadcrumb-item active">Lắp đặt mới</li>
      </ul>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#">
            <span class="icon ui-1_circle-add nc-icon-glyph"></span> Nhập
            mới(F5)
          </a>
        </li>
        <li>
          <a href="#">
            <span class="icon media-1_sd nc-icon-glyph"></span> Ghi lại(F9)
          </a>
        </li>
        <li>
          <a href="#">
            <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Hủy
          </a>
        </li>
        <li>
          <a href="#">
            <span class="icon ui-1_trash nc-icon-glyph"></span> Xóa HĐ (F8)
          </a>
        </li>
        <li>
          <a href="#">
            <span class="icon files_single-content-02 nc-icon-glyph"></span> Hồ
            sơ
          </a>
        </li>
        <li>
          <a href="#">
            <span class="icon shopping_credit-card nc-icon-glyph"></span> Thanh
            toán
          </a>
        </li>
        <li>
          <a href="#">
            <span class="icon ui-1_circle-add nc-icon-glyph"></span> Thêm TT
          </a>
        </li>
        <li>
          <a href="#">
            <span class="icon ui-1_trash nc-icon-glyph"></span> Xóa TT
          </a>
        </li>
        <li>
          <a href="#">
            <span class="icon ui-1_circle-add nc-icon-glyph"></span>Thêm TB
          </a>
        </li>
        <li>
          <a href="#">
            <span class="icon ui-1_trash nc-icon-glyph"></span>Xóa TB
          </a>
        </li>
        <li>
          <a href="#">
            <span class="icon objects_support-17 nc-icon-glyph"></span>Chăm sóc
            KH
          </a>
        </li>
      </ul>
      <div class="more">
        <a href="#">
          <span class="icon -ap icon-dots-three-vertical"></span>
        </a>
      </div>
    </div>
    <div class="page-content">
      <a @click="ShowPopUp" data-toggle="modal" class="btn btn-action"
        >Show modal</a
      >
    </div>
    <b-modal
      id="my-modal1"
      class="modal-dialog"
      size="lg"
      hide-footer
      :no-close-on-backdrop="true"
    >
      <div slot="modal-title" class="popup-header">
        <div class="title">Thay đổi địa chỉ MAC FIBER</div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" @click.prevent="NhapMoi">
              <span class="icon files_single-paragraph nc-icon-glyph"></span>
              Nhập mới
            </a>
          </li>
          <li>
            <a href="#" @click.prevent="ThayDoi">
              <span class="icon one-sync"></span>Thay đổi
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title mart0">
            <div class="title pull-left">Thông tin thuê bao</div>
            <div class="pull-right">
              <div class="list-checks">
                <div class="item">
                  <div class="check-action">
                    <input
                      type="radio"
                      checked
                      class="check"
                      value="0"
                      v-model="LoaiId"
                    />
                    <span class="name">Danh bạ</span>
                  </div>
                </div>
                <div class="item">
                  <div class="check-action">
                    <input
                      type="radio"
                      class="check"
                      value="1"
                      v-model="LoaiId"
                    />
                    <span class="name">Hợp đồng</span>
                  </div>
                </div>
              </div>
            </div>
            <div class="clearfix"></div>
          </div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">Account</div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control"
                    v-on:keyup.enter="Search"
                    v-bind:readonly="ReadOnly"
                    v-model="MaTb"
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">Loại hình</div>
                <div class="value">
                  <!--
                                        <div class="select-custom">
                                            <select name="" id="" class="form-control" v-model="LoaiHinhId">
                                                 <option v-for="item in DmDvVienThong" v-bind:key="item.loaitb_ID" v-bind:value="item.loaitb_ID">{{
                                                    item.loaihinh_TB}}  </option>
                                            </select>
                                        </div>
                                        -->
                  <Select2
                    :disabled="disableLoaiHinh"
                    v-model="LoaiHinhId"
                    :options="DmDvVienThong"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">Địa chỉ Mac cũ</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="Mac" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">Địa chỉ Mac mới</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="MacNew" />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">Tên thuê bao</div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control"
                    v-model="TenThueBao"
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">Mã giao dịch</div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control"
                    v-model="MaGiaoDich"
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form no-boder" style="border: none">
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="info-row">
                <div class="key">Ghi chú</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="GhiChu" />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </b-modal>
  </div>
</template>
<script>
import api from "./Api";
export default {
  components: {
    api,
  },
  name: "ChangeMacFiber",
  props: ["hdtb_id", "loaitb_id", "ma_tb", "ma_gd"],
  async created() {
    this.Loading = true;
    try {
      Promise.all([this.getDmDvVT()]);
      this.Loading = false;
    } catch (ex) {
      this.Loading = false;
    }
    this.Loading = false;
    this.HdThueBaoId = this.hdtb_id;
    this.MaTb = this.ma_tb;
    this.LoaiHinhId = this.loaitb_id;
    this.MaGiaoDich = this.ma_gd;
  },
  watch: {
    LoaiId: function (val) {
      this.NhapMoi();
    },
    Loading: function (val, oldval) {
      this.$root.showLoading(val);
    },
  },
  data() {
    return {
      header: {
        title: "Tra cứu",
        list: [
          { name: " Thay đổi địa chỉ MAC FIBER", link: { name: "Ui.cards" } },
          {
            name: "Thay đổi địa chỉ MAC FIBER",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      Loading: false,
      ReadOnly: false,
      disableLoaiHinh: true,
      dateFormat: "DD/MM/YYYY",
      typeFormat: "date",
      LoaiId: 0,
      MaTb: "",
      LoaiHinhId: "",
      Mac: "",
      MacNew: "",
      TenThueBao: "",
      MaGiaoDich: "",
      GhiChu: "",
      ThueBaoId: 0,
      HdThueBaoId: 0,
      DmDvVienThong: [],
      DsHdThueBao: [],
      LoaiHinhTB: {
        INTERNET_FTTH: 58,
      },
      TrangThaiHD: {
        HOAN_THANH: 6,
        THOAITRA: 7,
      },
      TRANGTHAI_DONGBO: {
        DONGBO_CM: 5,
      },
      ADSLData: {
        TDTT_DB_ID: "",
        THUEBAO_ID: "",
        MA_TB: "",
        TRANGTHAI_ID: "",
        HDTB_ID: "",
        NHANVIEN_ID: "",
        DONVI_ID: "",
        NGAY_DK: "",
        NGAY_HT: "",
        MAY_CN: "",
        NGAY_CN: "",
        NGUOI_CN: "",
        MAC_ADDR_CU: "",
        MAC_ADDR: "",
        GHICHU: "",
      },
    };
  },
  methods: {
    getDmDvVT: async function () {
      var input = { dichvuvt_id: 4 };
      let data = this.GetData(await api.getDmDvVT(this.axios, input));
      this.DmDvVienThong = data.map((x) => ({
        id: x.loaitb_id,
        text: x.loaihinh_tb == null ? "" : x.loaihinh_tb,
      }));
    },
    getDsHdThueBaoThayDoiMac: async function (matb) {
      var input = { ma_tb: matb };
      return this.GetData(
        await api.getDsHdThueBaoThayDoiMac(this.axios, input)
      );
    },
    getDanhBaTheoMaTb: async function (matb) {
      var input = {
        in_dichvuvt_id: this.LoaiHinhId,
        in_donvi_dl_id: 0,
        in_ma_tb: matb,
      };
      return this.GetData(await api.getDanhBaTheoMaTb(this.axios, input));
    },
    getDsThueBaoAdsl: async function (thuebaoid) {
      var input = { thuebao_id: thuebaoid };
      return this.GetData(await api.getDsThueBaoAdsl(this.axios, input));
    },
    capNhatAdsl: async function () {
      let donviId = this.$root.token.getDonViID();
      let nhanvienId = this.$root.token.getNhanVienID();
      let userName = this.$root.token.getUserName();
      let arrAdsl = [];
      let adsl = {
        TDTT_DB_ID: "",
        THUEBAO_ID: this.ThueBaoId,
        MA_TB: this.MaTb,
        TRANGTHAI_ID: this.LoaiId === 0 ? 1 : 2,
        HDTB_ID: this.HdThueBaoId,
        NHANVIEN_ID: nhanvienId,
        DONVI_ID: donviId,
        NGAY_DK: "",
        NGAY_HT: "",
        MAY_CN: "",
        NGAY_CN: "",
        NGUOI_CN: userName,
        MAC_ADDR_CU: this.Mac,
        MAC_ADDR: this.MacNew,
        GHICHU: `Đổi địa chỉ MAC mới : ${this.MacNew}`,
      };
      arrAdsl.push(adsl);
      var input = { json_db_adsl: JSON.stringify(arrAdsl), metaClass: {} };
      let response = await api.capNhatAdsl(this.axios, input);
      if (response.data.error === 200 || response.data.error === "200") {
        this.ShowAlert(`Cập nhật thành công !`);
      } else {
        console.log(response.data.error_code);
      }
    },
    capNhatTbMac: async function () {
      var input = { mac_addr: this.MacNew, thuebao_id: this.ThueBaoId };
      let response = await api.capNhatMac(this.axios, input);
      if (response.data.error === 200 || response.data.error === "200") {
        this.ShowAlert(`Cập nhật thành công !`);
      } else {
        console.log(response.data.error_code);
      }
    },
    capNhatDbMac: async function () {
      var input = { mac_addr: this.MacNew, hdtb_id: this.HdThueBaoId };
      let response = await api.capNhatMac(this.axios, input);
      if (response.data.error === 200 || response.data.error === "200") {
        this.ShowAlert(`Cập nhật thành công !`);
      } else {
        console.log(response.data.error_code);
      }
    },
    changeMac: async function () {
      var input = {
        acc_name: this.MaTb,
        mac_addr: this.MacNew,
        note: this.GhiChu,
      };
      return api.changeMac(this.axios, input);
    },
    capNhat: async function () {
      var input = { thuebao_id: this.thuebao_id };
      let response = await api.capNhat(this.axios, input);
      if (response.data.error === 200 || response.data.error === "200") {
        this.ShowAlert(`Cập nhật thành công !`);
      } else {
        console.log(response.data.error_code);
      }
    },
    ShowPopUp: function () {
      this.$bvModal.show("my-modal1");
    },
    NhapMoi: async function () {
      this.ReadOnly = false;
      this.MaTb = "";
      this.LoaiHinhId = "";
      this.Mac = "";
      this.MacNew = "";
      this.TenThueBao = "";
      this.MaGiaoDich = "";
      this.GhiChu = "";
      this.thuebao_id = "";
    },
    Search: async function () {
      this.Loading = true;
      try {
        if (this.LoaiId === 0) {
          let data = await this.getDanhBaTheoMaTb(this.MaTb);
          if (data.length > 0) {
            this.ReadOnly = true;
            let hd = data[0];
            this.ThueBaoId = hd.thuebao_id;
            this.LoaiHinhId = hd.loaitb_id;
            this.TenThueBao = hd.ten_tb;
            let adsl = await this.getDsThueBaoAdsl(this.ThueBaoId);
            if (adsl.length > 0) {
              this.Mac = adsl[0].mac_addr;
              this.MacNew = "";
            }
          } else {
            this.Loading = false;
            this.ShowAlert(`Không tìm thấy thông tin thuê bao :${this.MaTb}`);
            this.NhapMoi();
            return;
          }
        } else {
          let data = await this.getDsHdThueBaoThayDoiMac(this.MaTb);
          this.Loading = false;
          if (data.length > 0) {
            if (
              data[0].tthd_id == this.TrangThaiHD.HOAN_THANH ||
              data[0].tthd_id == this.TrangThaiHD.THOAITRA
            ) {
              this.ShowAlert(
                `Bạn không được đổi địa chỉ Mac của thuê bao có trạng thái hợp đồng là : Thoái trả hoặc Đã hoàn thành !`
              );
              this.NhapMoi();
              return;
            }
            if (data[0].status == this.TRANGTHAI_DONGBO.DONGBO_CM) {
              this.ShowAlert(
                `Bạn chỉ được đổi địa chỉ Mac của thuê bao khi đã kích hoạt lên hệ thống visa !`
              );
              this.NhapMoi();
              return;
            }
            this.ReadOnly = true;
            this.MaTb = data[0].ma_tb;
            this.HdThueBaoId = data[0].hdtb_id;
            this.TenThueBao = data[0].ten_tb;
            this.LoaiHinhId = data[0].loaitb_id;
            this.Mac = data[0].mac_addr;
            this.MacNew = "";
          } else {
            this.ShowAlert(`Không tìm thấy thông tin thuê bao :${this.MaTb}`);
            this.NhapMoi();
            return;
          }
        }
        this.Loading = false;
      } catch (ex) {
        this.Loading = false;
        this.ShowAlert(`Không tìm thấy thông tin thuê bao :${this.MaTb}`);
      }
    },
    ThayDoi: async function () {
      if (this.MacNew === "") {
        this.ShowAlert("Hãy nhập địa chỉ MAC mới");
        return;
      }
      /*
      if (this.LoaiHinhId !== this.LoaiHinhTB.INTERNET_FTTH) {
        this.ShowAlert('Chỉ được phép đổi địa chỉ MAC với loại thuê bao Fiber')
        return
      }
      */
      if (confirm("Bạn thật sự muốn cập nhật thông tin không ?")) {
        let changeMacRes = await this.changeMac();
        if (
          changeMacRes.data.error === 200 ||
          changeMacRes.data.error === "200"
        ) {
          let a = await this.capNhatAdsl();
          if (a.data.error === 200 || a.data.error === "200") {
            if (this.LoaiId === 0) {
              let response = await this.capNhatDbMac();
              if (
                response.data.error === 200 ||
                response.data.error === "200"
              ) {
                this.ShowSuccess(`Cập nhật dữ liệu thành công`);
              } else {
                console.log(response.data.error_code);
                this.ShowError(
                  `Có lỗi trong quá trình đổi địa chỉ MAC cho thuê bao : ${this.MaTb}`
                );
              }
            } else {
              let response = await this.capNhatTbMac();
              if (
                response.data.error === 200 ||
                response.data.error === "200"
              ) {
                this.ShowSuccess(`Cập nhật dữ liệu thành công`);
              } else {
                console.log(response.data.error_code);
                this.ShowError(
                  `Có lỗi trong quá trình đổi địa chỉ MAC cho thuê bao : ${this.MaTb}`
                );
              }
            }
          }
        } else {
        }
      }
    },
    TroGiup: function () {
      this.$bvModal.hide("my-modal1");
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === "200") {
        return response.data.data;
      } else {
        console.log(response.data.error_code);
      }
      return [];
    },
    ShowError: function (msg) {
      this.$toast.error(msg);
    },
    ShowAlert: function (msg) {
      this.$toast.error(msg);
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg);
    },
  },
};
</script>
<style>
.mx-datepicker {
  width: 100% !important;
}
.mx-icon-calendar {
  font-style: normal !important;
}
.modal-dialog {
  min-width: 900px;
}
.no-boder {
  border: 0px solid transparent !important;
}
</style>
