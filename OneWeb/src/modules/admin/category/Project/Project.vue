<template>
  <div class="">
    <!-- action header -->
    <div class="list-action-top">
      <vue-bread-crumb :header="header" />
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a
              href="#"
              :class="{ disabled: disabledCreate }"
              @click.prevent="clickCreate"
            >
              <span class="icon one-file-plus"></span>Nhập mới
            </a>
          </li>
          <li>
            <a href="#" @click.prevent="saveProject">
              <span class="icon one-save"></span>Ghi lại
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: false }"
              @click.prevent="clickCancel"
            >
              <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Huỷ
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disabledDelete }"
              @click.prevent="clickDelete"
            >
              <span class="icon one-trash"></span>Xóa
            </a>
          </li>
          <li>
            <a href="#" :class="{ disabled: false }">
              <span class="icon one-circle-question"></span>
              Trợ giúp
            </a>
          </li>
          <input ref="file" type="file" hidden @change="importExcel($event)" />
        </ul>
      </div>
    </div>
    <!-- content page -->
    <div class="page-content">
      <!-- Thông tin dự án -->
      <div class="information-project">
        <div class="box-form">
          <div class="legend-title">
            <div class="title-main">Thông tin dự án</div>
          </div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="row">
                <div class="col-sm-6 col-12">
                  <vue-input
                    :labelWidth="'120'"
                    ref="refTenTB"
                    label="Số hiệu công trình"
                    v-model="soHieu"
                  ></vue-input>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">
                      Ngày nghiệm thu
                    </div>
                    <div class="value">
                      <date-picker
                        style="width: 100%"
                        placeholder="dd/mm/yyyy"
                        :formatter="momentFormat"
                        valueType="format"
                        v-model="ngayNghiemThu"
                      ></date-picker>
                    </div>
                  </div>
                </div>
              </div>
              <div class="info-row value">
                <vue-input
                  :labelWidth="'120'"
                  ref="refTenTB"
                  label="Tên công trình"
                  v-model="tenCongTrinh"
                ></vue-input>
              </div>
              <div class="info-row value">
                <div class="select-custom">
                  <vue-select
                    :labelWidth="'120'"
                    :options="chuTruongList"
                    labelField="chutruong"
                    valueField="chutruong_id"
                    label="Chủ trương"
                    v-model="selectedChuTruong"
                  ></vue-select>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="row">
                <div class="col-sm-6 col-12">
                  <vue-input
                    :labelWidth="'120'"
                    ref="refTenTB"
                    label="Số QĐ đầu tư"
                    v-model="soQDDauTu"
                  ></vue-input>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">
                      Ngày QĐ đầu tư
                    </div>
                    <div class="value">
                      <date-picker
                        style="width: 100%"
                        placeholder="mm/yyyy"
                        :formatter="momentFormat"
                        valueType="format"
                        v-model="ngayQDDauTu"
                      ></date-picker>
                    </div>
                  </div>
                </div>
              </div>
              <div class="info-row value">
                <vue-input
                  :labelWidth="'120'"
                  ref="refTenTB"
                  label="Số QĐ quyết toán"
                  v-model="soQDQuyetToan"
                ></vue-input>
              </div>
              <div class="info-row value">
                <vue-input
                  :labelWidth="'120'"
                  ref="refTenTB"
                  label="Công năng"
                  v-model="congNang"
                ></vue-input>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- Danh sách đơn vị -->
      <div class="information-don-vi">
        <div class="row">
          <div class="col-sm-6 col-12">
            <danh-sach-don-vi-da-gan :dataCurrent="dataCurrent" />
          </div>
          <div class="col-sm-6 col-12">
            <danh-sach-don-vi-chua-gan :dataCurrent="dataCurrent" />
          </div>
        </div>
      </div>
      <!-- Danh sách dự án -->
      <div class="project-list">
        <!-- table -->
        <danh-sach-du-an @currentRecord="currentRecord" />
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import moment from "moment";
import DatePicker from "vue2-datepicker";
export default {
  name: "QuanLyDuAn",
  components: {
    DatePicker,
    DanhSachDuAn: () => import("./part/DanhSachDuAn.vue"),
    DanhSachDonViDaGan: () => import("./part/DanhSachDonViDaGan.vue"),
    DanhSachDonViChuaGan: () => import("./part/DanhSachDonViChuaGan.vue")
  },
  data() {
    return {
      config: [
        {
          label: "Số hiệu công trình",
          filterAble: true,
          key: "so_hieu"
        },
        {
          label: "Tên công trình",
          filterAble: true,
          key: "ten_tb"
        }
      ],
      /* data header action */
      header: {
        title: "QUẢN LÝ DỰ ÁN",
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
          return date ? moment(date).format("DD/MM/YYYY") : "";
        },
        parse: value => {
          return value ? moment(value, "DD/MM/YYYY").toDate() : null;
        }
      },
      // button
      disabledCreate: false,
      disabledDelete: false,
      dataCurrent: {
        CHUTRUONG_ID: 0,
        CONGNANG: null,
        DONGBO: "0",
        DUAN_ID: 0,
        DUAN_IMS_ID: null,
        GHICHU: null,
        IP_CN: null,
        MAY_CN: null,
        MA_CT: "",
        NGAY_CN: null,
        NGAY_DT: "",
        NGAY_NT: "",
        NGUOI_CN: null,
        SOHIEU_CT: "",
        SOQD_DT: null,
        SOQD_QT: null,
        phanvung_id: 0
      },
      // data thong tin du an
      soHieu: "",
      ngayNghiemThu: "",//moment(new Date()).format("DD/MM/YYYY").toString(),
      tenCongTrinh: "",
      soQDDauTu: "",
      ngayQDDauTu: "",//moment(new Date()).format("DD/MM/YYYY").toString(),
      soQDQuyetToan: "",
      congNang: "",
      selectedChuTruong: null
    };
  },
  async created() {
    await this.fetchChuTruongList();
  },
  computed: {
    ...mapGetters("mapProject", ["chuTruongList", "danhSachDuAn"])
  },
  methods: {
    ...mapActions("mapProject", [
      "fetchChuTruongList",
      "fetchDanhSachDuAn",
      "saveUpdateProject",
      "removeProject",
      'fetchListProjects'
    ]),
    convertDate(str) {
      let cleanStringDate = str ? str.replace("ICT", "") : "";
      if (cleanStringDate.length) {
        let dateToFormat = new Date(cleanStringDate);
        let date =
          dateToFormat.getDate() < 10
            ? "0" + dateToFormat.getDate()
            : dateToFormat.getDate();
        let month =
          dateToFormat.getMonth() < 10
            ? "0" + dateToFormat.getMonth()
            : dateToFormat.getMonth();
        if (dateToFormat.toString().includes("Jan")) {
          month = "01";
        }
        let year = dateToFormat.getFullYear();
        let fullDate = date + "/" + month + "/" + year;
        return fullDate;
      }
    },
    /* chọn data từ danh sách thuê bao */
    currentRecord(data) {
      console.log(data);
      this.dataCurrent = data;
      this.soHieu = data.sohieu_ct;
      this.ngayNghiemThu = moment(data.ngay_nt).format('DD/MM/YYYY');
      this.tenCongTrinh = data.ma_ct;
      this.soQDDauTu = data.soqd_dt;
      this.ngayQDDauTu = moment(data.ngay_dt).format('DD/MM/YYYY');
      this.soQDQuyetToan = data.soqd_qt;
      this.congNang = data.congnang;
      this.selectedChuTruong = data.chutruong_id;
    },
    clickCreate() {
      this.disabledCreate = true;
      this.disabledDelete = true;
      this.resetData();
    },
    changeFormatDate(str) {
      let date = str.slice(0, 2);
      let month = str.slice(3, 5);
      let year = str.slice(6, 10);
      // const fullDate = month + "/" + date + "/" + year;
      const fullDate = year + "-" + month + "-" + date;
      return fullDate;
    },
    checkDate() {
      if(!this.ngayQDDauTu){
        this.$toast.error("Ngày đầu tư không được bỏ trống");
        return false;
      }
      if(!this.ngayNghiemThu){
        this.$toast.error("Ngày nghiem thu không được bỏ trống");
        return false;
      }
      let current_time = moment()
        .format("DD/MM/YYYY")
        .toString();
      var datePickNgayDauTu = Date.parse(
        this.changeFormatDate(this.ngayQDDauTu)
      );
      var datePickNgayNghiemThu = Date.parse(
        this.changeFormatDate(this.ngayNghiemThu)
      );
      var dateCurrent = Date.parse(this.changeFormatDate(current_time));
      if (datePickNgayDauTu < dateCurrent) {
        this.$toast.error("Ngày đầu tư không được nhỏ hơn ngày hiện tại!");
        return false;
      }
      if (datePickNgayNghiemThu < dateCurrent) {
        this.$toast.error("Ngày nghiệm thu không được nhỏ hơn ngày hiện tại!");
        return false;
      }
      if (datePickNgayNghiemThu < datePickNgayDauTu) {
        this.$toast.error("Ngày nghiệm không được nhỏ hơn ngày đầu tư!");
        return false;
      }
      return true;
    },
    async saveProject() {
      if(!this.soHieu){
        this.$toast.warning("Số hiệu công trình không được bỏ trống!");
        return;
      }
      if(!this.tenCongTrinh){
        this.$toast.warning("Tên công trình không được bỏ trống!");
        return;
      }
      if(!this.selectedChuTruong){
        this.$toast.warning("Chưa chọn chủ trương!");
        return;
      }
      if(!this.checkDate())
        return;
      let DUAN_ID = this.disabledCreate
        ? null
        : this.dataCurrent.duan_id.toString();
      let SOHIEU_CT = this.soHieu ? this.soHieu.trim().toString() : "";
      let MA_CT = this.tenCongTrinh ? this.tenCongTrinh.trim().toString() : "";
      let SOQD_DT = this.soQDDauTu ? this.soQDDauTu.trim().toString() : "";
      let SOQD_QT = this.soQDQuyetToan
        ? this.soQDQuyetToan.trim().toString()
        : "";
        
      let NGAY_NT = this.ngayNghiemThu.toString();
      let CHUTRUONG_ID = this.selectedChuTruong.toString();
      let CONGNANG = this.congNang ? this.congNang.trim().toString() : "";
      let GHICHU = this.dataCurrent.ghichu
        ? this.dataCurrent.ghichu.toString()
        : "";
      let NGAY_DT = this.ngayQDDauTu.toString();
      const variables = {
        kieu: this.disabledCreate ? 1 : 2,
        ds: `{"DUAN_ID": "${DUAN_ID}","SOHIEU_CT":"${SOHIEU_CT.toString()}","MA_CT":"${MA_CT}","SOQD_DT":"${SOQD_DT}","SOQD_QT":"${SOQD_QT}","NGAY_NT":"${NGAY_NT}","CHUTRUONG_ID":"${CHUTRUONG_ID}","CONGNANG":"${CONGNANG}","GHICHU":"${GHICHU}","NGAY_DT":"${NGAY_DT}"}`
      };
      try {
        this.loading(true)
        const res = await this.saveUpdateProject(variables);
        this.loading(false)
        if (res) {
          const checkUpdate = res.includes("Cập nhật thành công");
          if (checkUpdate) {
            this.$toast.success("Cập nhật thành công!");
          }
          const checkCreate = res.includes("Thêm mới thành công");
          if (checkCreate) {
            this.$toast.success("Thêm mới thành công!");
          }
          // await this.fetchDanhSachDuAn();
          await this.fetchListProjects(); //fix SQL injecttion
        }
      } catch (error) {
        this.loading(false)
        this.$toast.error("Lỗi server!");
      }
      
      this.resetDataCurrent();
      this.disabledCreate = false;
      this.disabledDelete = false;
    },
    async clickDelete() {
      const variables = {
        kieu: 3,
        ds: `{"DUAN_ID":${parseInt(this.dataCurrent.duan_id)}}`
      };
      const res = await this.removeProject(variables);
      // await this.fetchDanhSachDuAn();
       await this.fetchListProjects(); //fix SQL injecttion
      if (res) {
        const checkDeleted = res.data.includes("Xóa dữ liệu thành công");
        if (checkDeleted) {
          this.$toast.success("Xóa dữ liệu thành công!");
        }
        const checkNoDeleted = res.request_id.includes(
          "Thực hiện không thành công"
        );
        if (checkNoDeleted) {
          this.$toast.error("Dự án này đã có tài sản, không thể xóa!");
        }
      }
    },
    clickCancel() {
      this.resetDataCurrent();
      this.disabledCreate = false;
      this.disabledDelete = false;
    },
    /* reset data */
    resetData() {
      let current_time = moment()
        .format("DD/MM/YYYY")
        .toString();
      this.soHieu = "";
      this.ngayNghiemThu = current_time;
      this.tenCongTrinh = "";
      this.soQDDauTu = "";
      this.ngayQDDauTu = current_time;
      this.soQDQuyetToan = "";
      this.congNang = "";
      this.selectedChuTruong = this.chuTruongList[0].chutruong_id;
    },
    // reset dataCurrent
    resetDataCurrent() {
      this.soHieu = this.dataCurrent.SOHIEU_CT;
      this.ngayNghiemThu = this.convertDate(this.dataCurrent.NGAY_NT);
      this.tenCongTrinh = "";
      this.soQDDauTu = this.dataCurrent.SOQD_DT;
      this.ngayQDDauTu = this.convertDate(this.dataCurrent.NGAY_DT);
      this.soQDQuyetToan = this.dataCurrent.SOQD_QT;
      this.congNang = this.dataCurrent.CONGNANG;
      this.selectedChuTruong = this.dataCurrent.CHUTRUONG_ID;
    }
  },
  watch: {}
};
</script>
<style scoped>
.legend-title {
  display: flex;
  justify-content: space-between;
}
.title-sub,
.color-number-input input {
  color: #d32f2f !important;
}
/deep/ .modal-content {
  height: 850px !important;
}
.so-may-acc {
  background-color: #fff9eb;
}
.item-radio {
  margin-bottom: 20px;
}
.disableInput {
  cursor: pointer;
  pointer-events: none;
}
</style>