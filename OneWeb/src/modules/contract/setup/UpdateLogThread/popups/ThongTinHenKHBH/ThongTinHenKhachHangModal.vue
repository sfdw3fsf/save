<template>
  <b-modal
    ref="popupThongTinHenKhachHang"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    enableResize="true"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Thông tin hẹn khách hàng
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>

      <ActionTop :actions="actions" @onActionClick="onActionClick" />

      <div class="popup-body">
        <div class="grid-stack-box">
          <div class="box-col box-form">
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w100">Lựa chọn</div>
                  <div class="value">
                    <input
                      type="checkbox"
                      v-model="checkTuDongNhanTin"
                      class="check"
                    />
                    <span class="name">Tự động nhắn cho KH</span>
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-12 col-12">
                <div class="info-row">
                  <div class="key w100">Nội dung hẹn</div>
                  <div class="value">
                    <input
                      type="text"
                      v-model="input_noidung"
                      class="form-control"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key w100">Từ ngày</div>
                  <div class="value">
                    <div class="input-icon-right">
                      <ejs-datetimepicker
                        v-model="dtpNgayHenTu"
                        :value="today"
                        :format="'dd/MM/yyyy HH:mm:ss'"
                        :showClearButton="false"
                      >
                      </ejs-datetimepicker>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-8 col-12">
                <div class="info-row">
                  <div class="key w80">Đến ngày</div>
                  <div class="value">
                    <div class="input-icon-right">
                      <ejs-datetimepicker
                        v-model="dtpNgayHenDen"
                        :value="today"
                        :format="'dd/MM/yyyy HH:mm:ss'"
                        :showClearButton="false"
                      >
                      </ejs-datetimepicker>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="legend-title mart20">
              <div class="pull-left">Danh sách thuê bao</div>

              <div class="clearfix"></div>
            </div>
            <DataGrid
              class="grid table-result"
              v-bind:columns="[
                {
                  fieldName: 'giohen_tu',
                  headerText: 'Ngày hẹn từ',
                  type: 'date',
                  format: 'dd/MM/yyyy HH:mm:ss',
                  allowFiltering: true,
                  allowSorting: false,
                  textAlign: 'right',
                },
                {
                  fieldName: 'giohen_den',
                  headerText: 'Ngày hẹn đến',
                  type: 'date',
                  format: 'dd/MM/yyyy HH:mm:ss',
                  allowFiltering: true,
                  allowSorting: false,
                  textAlign: 'right',
                },
                {
                  fieldName: 'ngay_cn',
                  headerText: 'Ngày CN',
                  type: 'date',
                  format: 'dd/MM/yyyy HH:mm:ss',
                  allowFiltering: true,
                  allowSorting: false,
                  textAlign: 'right',
                },
                {
                  fieldName: 'nguoi_cn',
                  headerText: 'Người CN',
                  allowFiltering: true,
                  allowSorting: false,
                  textAlign: 'left',
                },
                {
                  fieldName: 'nv_hen',
                  headerText: 'NV hẹn',
                  allowFiltering: true,
                  allowSorting: false,
                  textAlign: 'left',
                },
                {
                  fieldName: 'dv_hen',
                  headerText: 'ĐV hẹn',
                  allowFiltering: true,
                  allowSorting: false,
                  textAlign: 'left',
                },
                {
                  fieldName: 'noidung',
                  headerText: 'Nội dung',
                  allowFiltering: true,
                  allowSorting: false,
                  textAlign: 'left',
                },
              ]"
              v-bind:dataSource="dataSourcesHen"
              :showFilter="true"
              :showColumnCheckbox="false"
              :defaultColumnCheckboxChecked="false"
              :allowPaging="true"
              :enablePagingServer="false"
            >
            </DataGrid>
          </div>
          <div class="gutter gutter-horizontal" style="width: 16px"></div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from "@/components/kylq_components/ActionTop.vue";
import moment from "moment";
import API from "./API";
export default {
  name: "ThongTinHenKhachHangModal",
  props: {
    hdkh_id: {
      type: Number,
      default: 0,
    },
  },
  components: {
    ActionTop,
  },
  props: ["vbaohong_id"],
  data() {
    return {
      actions: [
        {
          id: "capnhat",
          name: "Cập nhật",
          active: true,
          icon_class: "one-save",
        },
        {
          id: "thoat",
          name: "Thoát",
          active: true,
          icon_class: "one-exit",
        },
      ],

      dtpNgayHenTu: new Date(),
      dtpNgayHenDen: new Date(),
      today: new Date(),
      kt_thoigian_hen_baohong: false,
      ischapnhan: false,
      Quyen_NVKD: 0,
      Quyen_NVTTDH: 0,
      nhantin_kythuat_sauhen: 0, // = 0 : ; = 1 : Nhắn tin cho ông kỹ thuật quản lý địa bàn sau khi hẹn khách hàng
      dataSourcesHen: [],
      input_noidung: "",

      quyen_duoc_hen: 0,
      checkTuDongNhanTin: false,

      phieu_id: 0,
      baohong_id: 0,
      thuchien: false,
      khoaphieu: 0,
    };
  },
  computed: {},
  methods: {
    showModal() {
      this.$refs["popupThongTinHenKhachHang"].show();
    },
    hideModal() {
      this.$refs["popupThongTinHenKhachHang"].hide();
    },
    onActionClick(action) {
      if (action.id == "capnhat") {
        this.tsbtnCapNhat_Click();
      } else if (action.id == "thoat") {
        this.hideModal();
      }
    },
    handleShowModal() {
      //clear dữ liệu
      this.tu_ngay = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
      this.den_ngay = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");

      this.dataSourcesHen = [];
      this.input_noidung = "";
      this.nhantin_kythuat_sauhen = 0;
      this.kt_thoigian_hen_thicong = false;
      this.quyen_duoc_hen = 0;
      this.checkTuDongNhanTin = false;
      this.baohong_id = this.vbaohong_id;
      setTimeout(()=>{
            this.khoitao_thamso()
          },200)
    },
    sendSMS(item) {
      console.log("sendSMS", item);
      //send sms kh
    },
    async HienThiDanhSach() {
      if (this.baohong_id != 0) {
        let ds = [];
        await API.lay_lichsu_hen_baohong(this.axios, {
          baohong_id: this.baohong_id,
        })
          .then((response) => {
            if (
              response &&
              response.data &&
              response.data.data &&
              response.data.error_code &&
              response.data.error_code === "BSS-00000000"
            ) {
              this.dataSourcesHen = response.data.data;
              if (this.dataSourcesHen.length == 0) {
              }
            } else {
              this.dataSourcesHen = [];
            }
          })
          .catch((error) => {
            console.log(error);
          })
          .finally(() => {});
      }
    },
    async khoitao_thamso() {
      let dsThamSo = await this.getDsThamSo();
      if (dsThamSo.length > 0) {
        //NHANTIN_KYTHUAT_KHIHEN
        let tt_nhantin_kythuat_khihen = dsThamSo.find(
          (x) => x.ma_ts == "NHANTIN_KYTHUAT_KHIHEN"
        );
        if (
          tt_nhantin_kythuat_khihen != undefined &&
          tt_nhantin_kythuat_khihen.ten_ts == "1"
        ) {
          this.nhantin_kythuat_sauhen = 1;
        }
        //KT_THOIGIAN_HEN_BAOHONG
        let tt_kt_thoigian_hen_baohong = dsThamSo.find(
          (x) => x.ma_ts == "KT_THOIGIAN_HEN_BAOHONG"
        );
        let donvi_id = this.$root.token.getDonViID();
        if (donvi_id >= 15400 && donvi_id < 1600) {
          this.quyen_duoc_hen = 1;
          this.Quyen_NVKD = 1;
          this.Quyen_NVTTDH = 1;
          return false;
        }
        if (
          tt_kt_thoigian_hen_baohong != undefined &&
          tt_kt_thoigian_hen_baohong.ten_ts == "1"
        ) {
          this.kt_thoigian_hen_baohong = true;
        }
        //CHECK_CAPNHAT_HEN
        let tt_check_capnhat_hen = dsThamSo.find(
          (x) => x.ma_ts == "CHECK_CAPNHAT_HEN"
        );
        if (
          tt_check_capnhat_hen != undefined &&
          tt_check_capnhat_hen.ten_ts == "1"
        ) {
          //lay_ts_macdinh_dv
          //quyen_duoc_hen
          let thamso_dv=[]
          await API.lay_ds_thamso_macdinh_dv(this.axios, {
            vdonvi_id: this.$root.token.getDonViID(),
            vkieu_id: 4,
          })
            .then((response) => {
              if (
                response.data.data &&
                response.data.error_code &&
                response.data.error_code === "BSS-00000000" &&
                response.data.data != -1
              ) {
                thamso_dv = response.data.data;
              }
            })
            .catch((error) => {
              console.log(error);
            })
            .finally(() => {});
          await API.fn_tt_baohong(this.axios,  { param: this.baohong_id, type: 1 })
            .then((response) => {
              if (
                response.data.data &&
                response.data.error_code &&
                response.data.error_code === "BSS-00000000" &&
                response.data.data != -1
              ) {
                this.khoaphieu = response.data.data;
              }
            })
            .catch((error) => {
              console.log(error);
            })
            .finally(() => {});
          switch (this.khoaphieu) {
            case "0": {
              let ttkd_capnhat_hen = thamso_dv.find(
                (x) => x.ma_ts == "TTKD_CAPNHAT_HEN"
              );
              if (
                ttkd_capnhat_hen != undefined &&
                ttkd_capnhat_hen.ten_ts == "1"
              ) {
                this.quyen_duoc_hen = 1;
              } else {
                this.$toast.error("Quyền chưa được thiết lập!");
              }

              break;
            }
            case "2": {
              let ttdh_capnhat_hen = thamso_dv.find(
                (x) => x.ma_ts == "TTDH_CAPNHAT_HEN"
              );
              if (
                ttdh_capnhat_hen != undefined &&
                ttdh_capnhat_hen.ten_ts == "1"
              ) {
                this.Quyen_NVTTDH = 1;
              } else {
                this.Quyen_NVTTDH = 0;
              }
              let ttkd_capnhat_hen = thamso_dv.find(
                (x) => x.ma_ts == "TTKD_CAPNHAT_HEN"
              );
              if (
                ttkd_capnhat_hen != undefined &&
                ttkd_capnhat_hen.ten_ts == "1"
              ) {
                this.Quyen_NVKD = 1;
              } else {
                this.Quyen_NVKD = 0;
              }

              break;
            }
          }
        }
      }
      this.HienThiDanhSach();
      console.log("nhantin_kythuat_sauhen", this.nhantin_kythuat_sauhen);
      console.log("kt_thoigian_hen_thicong", this.kt_thoigian_hen_thicong);
      console.log("quyen_duoc_hen", this.quyen_duoc_hen);
    },
    async getDsThamSo() {
      try {
        this.loading(true);
        let response = await API.sp_tt_thamso_md(this.axios);
        this.loading(false);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data != null
        ) {
          return response.data.data;
        } else {
          return [];
        }
      } catch (e) {
        this.loading(false);
        return [];
      }
    },
   
    async tsbtnCapNhat_Click() {
     /*  if (this.quyen_duoc_hen == 0) {
        this.$toast.error("Bạn chưa được cấp quyền hẹn xử lý với khách hàng");
        return false;
      } */
      //nhapt theem 19072017
      let ttbh = "";
      await API.fn_tt_baohong(this.axios, { param: this.baohong_id, type: 2 })
        .then((response) => {
          if (
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000" &&
            response.data.data != -1
          ) {
            ttbh = response.data.data;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
      if (ttbh == "6") {
        this.$toast.error(
          "Báo hỏng đã hoàn thành. Không thể cập nhật giờ hẹn!"
        );
        return false;
      } else if (ttbh == "-1") {
        this.$toast.error("Báo hỏng không tồn tại");
        return false;
      }
      //Lấy ngày báo hỏng
      let ngay_bh = [];
      await API.fn_tt_baohong(this.axios, { param: this.baohong_id, type: 3 })
        .then((response) => {
          if (
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000" &&
            response.data.data != -1
          ) {
            ngay_bh = response.data.data;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
        
      if(moment(this.dtpNgayHenTu,'DD/MM/YYYY HH:mm:ss').isBefore(moment(ngay_bh,'DD/MM/YYYY HH:mm:ss'))){
        this.$toast.error("Ngày hẹn phải lớn hơn ngày báo hỏng!");
        return false;
      }
      if (this.dtpNgayHenTu > this.dtpNgayHenDen) {
        this.$toast.error(
          "Ngày hẹn từ không thể lớn hơn Ngày hẹn đến. Hãy kiểm tra lại!"
        );
        return false;
      }
      if (this.dtpNgayHenTu <= this.today || this.dtpNgayHenDen <= this.today) {
        this.$toast.error("Ngày hẹn không được nhỏ hơn ngày hiện tại!");
        return false;
      }
      if (this.kt_thoigian_hen_baohong) {
        switch (this.khoaphieu) {
          case 0: {
            let songay = 2;
            await API.layso_ngay_hen(this.axios, { loaiphieu: 1 })
              .then((response) => {
                if (
                  response.data.data &&
                  response.data.error_code &&
                  response.data.error_code === "BSS-00000000" &&
                  response.data.data != -1
                ) {
                  songay = response.data.data[0].songay;
                  let totalday =
                    moment(this.dtpNgayHenDen).format("DD") -
                    moment(this.dtpNgayHenTu).format("DD");
                  if (totalday > songay) {
                    this.$toast.error(
                      "Đối với phiếu báo hỏng thời gian hẹn không quá " +
                        songay +
                        " ngày!"
                    );
                    return false;
                  }
                }
              })
              .catch((error) => {
                console.log(error);
              })
              .finally(() => {});
            break;
          }
          case 2: {
            let songay = 2;
            await API.layso_ngay_hen(this.axios, { loaiphieu: 3 })
              .then((response) => {
                if (
                  response.data.data &&
                  response.data.error_code &&
                  response.data.error_code === "BSS-00000000" &&
                  response.data.data != -1
                ) {
                  songay = response.data.data[0].songay;
                  let totalday =
                    moment(this.dtpNgayHenDen).format("DD") -
                    moment(this.dtpNgayHenTu).format("DD");
                  if (totalday > songay) {
                    this.$toast.error(
                      "Đối với phiếu báo hỏng thời gian hẹn không quá " +
                        songay +
                        " ngày!"
                    );
                    return false;
                  }
                }
              })
              .catch((error) => {
                console.log(error);
              })
              .finally(() => {});
            break;
          }
        }
      }
      let kq_cn = "";
      await API.capnhat_giohen(this.axios, {
        phanvung_id: 26,
        baohong_id: this.baohong_id,
        phieu_id: this.phieu_id,
        nhanvien_id: this.$root.token.getNhanVienID(),
        donvi_id: this.$root.token.getDonViID(),
        ghichu_ton: this.input_noidung,
        hensuatu: moment(this.dtpNgayHenTu).format("DD/MM/YYYY") + " 00:00:00",
        hensuaden:
          moment(this.dtpNgayHenDen).format("DD/MM/YYYY") + " 00:00:00",
        nguoi_cn: this.$root.token.getUserName(),
        may_cn: this.$root.token.getUserName(),
        ip_cn: "localhost",
      })
        .then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            kq_cn = response.data.message;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
      let kq_sms = "";
      if (this.checkTuDongNhanTin) {
        if (this.baohong_id <= 0) {
          this.$toast.error("Không có báo hỏng để nhắn");
        } else {
          await API.send_sms_ld(this.axios, {
            vkieu: 2,
            vrid: this.baohong_id,
            vma_tb: "",
            vsdt: "",
          })
            .then((response) => {
              if (
                response &&
                response.data &&
                response.data.data &&
                response.data.error_code &&
                response.data.error_code === "BSS-00000000"
              ) {
                kq_sms = response.data.data;
              }
            })
            .catch((error) => {
              console.log(error);
            })
            .finally(() => {
              this.loading(false);
            });
        }
      }
      if (kq_sms == "1") {
        this.$toast.success("Cập nhật và nhắn cho khách hàng thành công");
      } else {
        this.$toast.success("Cập nhật thành công");
      }
      if (this.nhantin_kythuat_sauhen == 1) {
        if (this.baohong_id <= 0) {
          this.$toast.error("Không có báo hỏng để nhắn");
        } else {
          this.$toast.error("Chức năng send sms đang xây dựng");
        }
      }
      this.HienThiDanhSach();
      this.thuchien = true;
      this.ischapnhan = true;
    },
  },
};
</script>