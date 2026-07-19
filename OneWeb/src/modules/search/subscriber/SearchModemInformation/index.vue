<template>
  <div>
    <vue-bread-crumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li
          @click="clickButton('laythongtin')"
          :class="this.button.laythongtin ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-attach"></span>Tra cứu (F3)</a>
        </li>
      </ul>
    </vue-nav>

    <div class="page-content">
      <div class="box-form">
        <div class="row">
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w90">Chọn chức năng</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt
                    v-model="ddlChonChucNang">
                    <option value="3">Thông tin wifi</option>
                    <option value="4">Xóa file backup</option>
                    <option value="5">Backup cấu hình</option>
                    <option value="6">Thông tin thiết bị</option>
                    <option value="7">Đổi pass wifi</option>
                    <option value="8">Lấy pass login ONT</option>
                    <option value="9">Thay đổi settopbox</option>
                  </SelectExt>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w120">Số máy/Acc</div>
              <div class="value">
                <input
                  type="text"
                  v-model="txtSomay_Acc"
                  class="form-control tright"
                />
              </div>
            </div>
          </div>
        </div>
        </div>
      </div>

      <div class="box-form">
        <div class="legend-title">Kết quả trả về</div>
        <div  class="value">
            <input
                  type="text"
                  v-model="txtThongTin"
                  class="form-control text" style="height: 600px;overflow: auto;"
                />
        </div>
      </div>

  </div>
</template>
<script>
import VueInput from "../../../search/subscriber/components/form/VueInput.vue";
import api from "./api";
import define from "./define";

export default {
  data() {
    return {
      ...define,
      txtSomay_Acc: "",
      txtThongTin:"",
      
    };
  },

  mounted() {
    this.LayTongHDTBChuaHoanThanh();
  },
  created() {
    this.getDanhSachDichVuVT();
    this.getDanhSach_LoaiHopDong_PhuLuc();
    this.getDanhSach_DonVi();
  },
  watch: {
    ddlDichVuVT(newdata) {
      this.ddlDichVuVT = newdata;
      this.LayTongHDTBChuaHoanThanh();
      this.LayDanhSachDieuHanhThiCong();
      this.LayDanhSachHDTBChuaHoanThanh();
    },
    ddlDonVi(newdata) {
      this.ddlDonVi = newdata;
      this.LayTongHDTBChuaHoanThanh();
      this.LayDanhSachDieuHanhThiCong();
      this.LayDanhSachHDTBChuaHoanThanh();
    },
    ddlLoaiHD_PL(newdata) {
      this.ddlLoaiHD_PL = newdata;
      this.LayDanhSachHDTBChuaHoanThanh();
    },
  },

  methods: {
    getDanhSachDichVuVT() {
      api.getDanhSachDichVuVT(this.axios, {}).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
          items.push({ id: item.DICHVUVT_ID, text: item.TEN_DVVT });
        });
        this.options.dichvuvienthong = items;
        this.ddlDichVuVT = items[0].id;
      });
    },

    getDanhSach_LoaiHopDong_PhuLuc() {
      api.getDanhSach_LoaiHopDong_PhuLuc(this.axios, {}).then((res) => {
        let items = [];
        items.push({ id: 0, text: 'Tất cả' });
        res.data.data.forEach(function (item) {
          items.push({ id: item.LOAIHD_ID, text: item.TEN_LOAIHD });
        });
        this.options.LoaiHopDong_PhuLuc = items;
        this.ddlLoaiHD_PL = items[0].id;
      });
    },

    getDanhSach_DonVi() {
      api.getDanhSach_DonVi(this.axios, {}).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
          items.push({ id: item.donvi_id, text: item.ten_dv });
        });
        this.options.donvi = items;
        this.ddlDonVi = items[0].id;
      });
    },

    LayTongHDTBChuaHoanThanh() {
      if (this.ddlDonVi != 0 && this.ddlDichVuVT != 0) {
        this.loading(true);
        let danhsach = [];
        let dsmoitiepnhan = [];
        let chuaphandonvi = [];
        let khaibaotongdai = [];
        let dathanhtoan = [];
        let daphandonvi = [];
        let dathicongxong = [];
        api
          .LayTongHDTBChuaHoanThanh(this.axios, {
            vdichvuvt_id: this.ddlDonVi,
            vdonvi_id: this.ddlDichVuVT,
            vtthd_id: 0,
          })
          .then((res) => {
            danhsach = res.data.data ? res.data.data : [];
            dsmoitiepnhan = danhsach.filter(function (ds) {
              return ds.tthd_id == 1;
            });
            chuaphandonvi = danhsach.filter(function (ds) {
              return ds.tthd_id == 3;
            });
            khaibaotongdai = danhsach.filter(function (ds) {
              return ds.tthd_id == 9;
            });
            dathanhtoan = danhsach.filter(function (ds) {
              return ds.tthd_id == 2;
            });
            daphandonvi = danhsach.filter(function (ds) {
              return ds.tthd_id == 4;
            });
            dathicongxong = danhsach.filter(function (ds) {
              return ds.tthd_id == 5;
            });
            this.loading(false);
            this.txtMoiTiepNhan = dsmoitiepnhan.length;
            this.txtChuaPhanDonVi = chuaphandonvi.length;
            this.txtKhaiBaoTongDai = khaibaotongdai.length;
            this.txtTongSoChuaHoanThanh = danhsach.length;
            this.txtDaThanhToan = dathanhtoan.length;
            this.txtDaPhanDonVi = daphandonvi.length;
            this.txtDaThiCongXong = dathicongxong.length + daphandonvi.length;
          });
      }
    },

    LayDanhSachHDTBChuaHoanThanh() {
      
      if (this.ddlDonVi != 0 && this.ddlDichVuVT != 0) {
        this.loading(true);
        api.LayDanhSachHDTBChuaHoanThanh(this.axios, {
            vdichvuvt_id: this.ddlDonVi,
            vdonvi_id: this.ddlDichVuVT,
            vloaihd_id: this.ddlLoaiHD_PL
          })
          .then((res) => {
            this.options.danhsach_grid_chitiet = res.data.data ? res.data.data: [];
            if (this.options.danhsach_grid_chitiet.length < 1) {
              this.$toast.error("Không có dữ liệu chi tiết hdtb chưa hoàn thành hiển thị!");
            }
            this.loading(false);
          });
      }
    },
    LayDanhSachDieuHanhThiCong() {
      if (this.ddlDonVi != 0 && this.ddlDichVuVT != 0) {
        this.loading(true);
        console.log("dvi-" + this.ddlDonVi, this.ddlDichVuVT);
        api.LayDanhSachDieuHanhThiCong(this.axios, {
            dichvuvt_id: this.ddlDichVuVT,
            donvi_id: this.ddlDonVi
          })
          .then((res) => {
            this.options.danhsach_grid_hopdong = res.data.data ? res.data.data: [];
            console.log("danhsach_grid_hopdong-" + this.options.danhsach_grid_hopdong);
            if (this.options.danhsach_grid_hopdong.length < 1) {
              this.$toast.error("Không có dữ liệu danh sách điều hành thi công hiển thị!");
            }
            this.loading(false);
          });
      }
    },

    clickButton(key) {
      if (key == "laythongtin") {
        if (this.ddlDonVi != 0 && this.ddlDichVuVT != 0) {
          this.LayTongHDTBChuaHoanThanh();
          this.LayDanhSachDieuHanhThiCong();
          this.LayDanhSachHDTBChuaHoanThanh();
        }
      }
    },
  },
};
</script>
<style>
li.non-active a {
  color: #d3d3d3 !important;
}
</style>