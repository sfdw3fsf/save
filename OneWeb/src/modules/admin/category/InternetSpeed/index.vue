<template>
  <div>
    <vue-bread-crumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li @click="clickButton('nhapmoi')"
            :class="this.button.nhapmoi ? 'active' : 'non-active'">
          <a> <span class="icon one-file-plus"></span>Nhập mới</a>
        </li>
        <li @click="clickButton('ghilai')"
            :class="this.button.ghilai ? 'active' : 'non-active'">
          <a> <span class="icon one-save"></span>Ghi lại </a>
        </li>

        <li @click="clickButton('huy')"
            :class="this.button.huy ? 'active' : 'non-active'">
          <a> <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy </a>
        </li>

        <li @click="clickButton('xoa')"
            :class="this.button.xoa ? 'active' : 'non-active'">
          <a> <span class="icon one-trash"></span>Xóa </a>
        </li>

      </ul>
    </vue-nav>


    <div class="page-content">
      <div class="box-form">
        <div class="legend-title">Thông tin tốc độ</div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key">Loại hình</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt v-model="ddlLoaiHinh" :options="this.options.loaihinh">
                    <option disabled value="0">Select one</option>
                  </SelectExt>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key">Mã tốc độ (Tỉnh)</div>
              <div class="value">
                <input type="text" v-model="txtMaTocDo" class="form-control">
              </div>
            </div>
            <div class="info-row">
              <div class="key">Tốc độ</div>
              <div class="value">
                <input type="text" v-model="txtTocDo" class="form-control">
              </div>
            </div>
            <div class="info-row">
              <div class="key">Thương hiệu</div>
              <div class="value">
                <input type="text" v-model="txtThuongHieu" class="form-control">
              </div>
            </div>
            <div class="legend-title mart20">Thông tin tìm kiếm</div>
            <div class="info-row">
              <div class="key">
                <div class="check-action">
                  <input type="checkbox" v-model="chkLoaiHinh_TimKiem" class="check">
                  <span class="name">Loại hình</span>
                </div>
              </div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt v-model="ddlLoaiHinh_TimKiem" :options="this.options.loaihinh_timkiem"
                             :disabled="!this.chkLoaiHinh_TimKiem">
                    <option disabled value="0">Select one</option>
                  </SelectExt>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w140">Tốc độ VDC</div>
              <div class="value">
                <input type="text" v-model="txtTocDoVDC" class="form-control">
              </div>
              <div class="value w100 padt7">
                <div class="check-action">
                  <input type="checkbox" v-model="chkIp_Tinh" class="check">
                  <span class="name">Ip tĩnh</span>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w140">Tốc độ thực</div>
              <div class="value">
                <input type="number" v-model="txtTocDoThuc" class="form-control">
              </div>
              <div class="value w100 padt7">
                <div class="check-action">
                  <input type="checkbox" v-model="chkKichHoat" class="check">
                  <span class="name">Kích hoạt</span>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w140">Mã tốc độ (kích hoạt)</div>
              <div class="value">
                <input type="text" v-model="txtMaTocDoKichHoat" class="form-control">
              </div>

            </div>
            <div class="info-row">
              <div class="key w140">Profile ID</div>
              <div class="value">
                <input type="number" v-model="txtProfileId" class="form-control">
              </div>

            </div>
            <div class="info-row">
              <div class="key w140">Ghi chú</div>
              <div class="value">
                <textarea name="" class="form-control" v-model="txtGhiChu" style="height: 50px;resize: none;"></textarea>
              </div>

            </div>
            <div class="info-row">
              <div class="key w140">
                <div class="check-action">
                  <input type="checkbox" v-model="chkProfileDslam" class="check">
                  <span class="name">Profile dslam</span>
                </div>
              </div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt v-model="ddlProfileDslam" :options="this.options.profile_dslam"
                             :disabled="!this.chkProfileDslam">
                    <option disabled value="0">Select one</option>
                  </SelectExt>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="box-form">
        <div class="legend-title">
          <div class="pull-left">
            Danh sách tốc độ
          </div>
          <div class="pull-right">
            <div class="list-checks">
              <div class="item">
                <div class="check-action">
                  <input type="checkbox" v-model="chkHieuLuc" class="check">
                  <span class="name">Hiệu lực</span>
                </div>
              </div>
            </div>
          </div>
          <div class="clearfix"></div>
        </div>

        <div class="table-content">
          <DataGrid :columns="this.options.cols_grid"
                    v-bind:dataSource="this.options.danhsach_grid"
                    :showColumnCheckbox="false"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :enabledSelectFirstRow="true"
                    @rowSelected="rowSelected">
          </DataGrid>
        </div>
      </div>

    </div>
  </div>
</template>
<script>
  import VueInput from "../../../search/subscriber/components/form/VueInput.vue";
  import api from "./api";
  import define from "./define";
  import moment from "moment";
  import VueCheckbox from '../../../search/subscriber/components/form/VueCheckbox.vue';
  import { DataManager, ODataAdaptor, Predicate, Query } from '@syncfusion/ej2-data';
  import {
    Sort,
    Filter,
    Resize,
    Group,
    Freeze,
    CommandColumn,
    ExcelExport,
    Edit,
    Aggregate,
    Page,
    Toolbar,
  } from "@syncfusion/ej2-vue-grids";
  export default {
    components: { VueInput, VueCheckbox },
    provide: {
      grid: [
        Sort,
        Filter,
        Resize,
        Group,
        Freeze,
        CommandColumn,
        ExcelExport,
        Edit,
        Aggregate,
        Toolbar,
        Page,
        CommandColumn,
      ],
    },

    data() {
      return {
        ...define,
        txtMaTocDo: "",
        txtTocDo: "",
        txtThuongHieu: "",
        chkLoaiHinh_TimKiem: false,
        ddlLoaiHinh_TimKiem: "",
        ddlLoaiHinh: "",
        txtTocDoVDC: "",
        chkIp_Tinh: false,
        txtTocDoThuc: "",
        chkKichHoat: false,
        txtMaTocDoKichHoat: "",
        txtProfileId: "",
        txtGhiChu: "",
        chkProfileDslam: false,
        ddlProfileDslam: "",
        chkHieuLuc: false,
        toc_do_id: 0,
        loaitbid: 0,
        options: {
          loaihinh: [],
          loaihinh_timkiem: [],
          profile_dslam: [],
          danhsach_grid: [],
          cols_grid: [
            {
              fieldName: "stt",
              headerText: "STT",
              allowFiltering: true,
              textAlign: "center",
              allowSorting: true,
              width: "50",
            },
            {
              fieldName: "tocdo_id",
              headerText: "Tốc độ ID",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "ma_td",
              headerText: "Mã tốc độ",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "tocdo",
              headerText: "Tốc độ",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "thuonghieu",
              headerText: "Thương hiệu",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            }
          ],
        }
      };
    },


    created() {
      this.getComboLoaiHinh();
      this.getProfile();
    },
    watch: {
      chkLoaiHinh_TimKiem(newdata) {
        if (newdata == true) {
          this.getComboLoaiHinh_TimKiem();
        }
        else {
          this.options.loaihinh_timkiem = [];
        }
      },

      ddlLoaiHinh_TimKiem(newdata) {
        this.loaitbid = newdata;
        this.getDanhSachGrid();
      },
      ddlLoaiHinh(newdata) {
        this.loaitbid = newdata;
        this.getDanhSachGrid();
      },
    },

    methods: {

      getComboLoaiHinh() {
        api.getComboLoaiHinh(this.axios, {}).then((res) => {
          let items = [];
          res.data.data.forEach(function (item) {
            items.push({ id: item.LOAITB_ID, text: item.LOAIHINH_TB });
          });
          this.options.loaihinh = items;
          this.ddlLoaiHinh = items[0].id;
        });
      },

      getComboLoaiHinh_TimKiem() {
        api.getComboLoaiHinh(this.axios, {}).then((res) => {
          let items = [];
          res.data.data.forEach(function (item) {
            items.push({ id: item.LOAITB_ID, text: item.LOAIHINH_TB });
          });
          this.options.loaihinh_timkiem = items;
          this.ddlLoaiHinh_TimKiem = items[0].id;
        });
      },


      getProfile() {
        api.getProfile(this.axios, {}).then((res) => {
          let items = [];
          res.data.data.forEach(function (item) {
            items.push({ id: item.profiledsl_id, text: item.ten_profile });
          });
          this.options.profile_dslam = items;
          this.ddlProfileDslam = items[0].id;
        });
      },

      getDanhSachGrid() {
        this.loading(true);
        api.getDanhSachGrid(this.axios, {
          loaitb_tk_id: this.loaitbid
        })
          .then((res) => {
            this.options.danhsach_grid = res.data.data ? res.data.data : [];
            if (this.options.danhsach_grid.length < 1) {
              this.$toast.error("Không có dữ liệu hiển thị!");
            }
            this.loading(false);
          });
      },

      rowSelected(args) {
        this.chkKichHoat = false
        this.chkIp_Tinh = false
        this.chkHieuLuc = false
        this.chkProfileDslam = false
        this.ddlProfileDslam = ""
        if (args.data.iptinh == 1) {
          this.chkIp_Tinh = true
        }
        if (args.data.kieu_mega == 1) {
          this.chkKichHoat = true
        }
        if (args.data.sudung == 1) {
          this.chkHieuLuc = true
        }
        if (args.data.profiledsl_id != null && args.data.profiledsl_id != '' && args.data.profiledsl_id != "") {
          this.chkProfileDslam = true
          this.ddlProfileDslam = args.data.profiledsl_id
        }
        this.ddlLoaiHinh = args.data.loaitb_id
        this.txtMaTocDo = args.data.ma_td
        this.txtTocDo = args.data.tocdo
        this.txtThuongHieu = args.data.thuonghieu
        this.txtTocDoVDC = args.data.matocdo_vdc
        this.txtTocDoThuc = args.data.tocdothuc
        this.txtMaTocDoKichHoat = args.data.matocdo
        this.txtProfileId = args.data.profile_id
        this.txtGhiChu = args.data.ghichu
        this.toc_do_id = args.data.tocdo_id
        console.log(this.ddlProfileDslam);
      },


      Them() {
        try {
          if (!this.KiemTra_DuLieu()) return false;
          var ip_tinh = 0;
          var kich_hoat = 0;
          var su_dung = 0;
          var profile = 147;
          if (this.chkIp_Tinh == true) {
            ip_tinh = 1;
          }
          if (this.chkKichHoat == true) {
            kich_hoat = 1;
          }
          if (this.chkHieuLuc == true) {
            su_dung = 1;
          }
          if (this.chkProfileDslam == true) {
            if (this.ddlProfileDslam != null && this.ddlProfileDslam != '' && this.ddlProfileDslam != "") {
              profile = this.ddlProfileDslam;
            }
          }

          var json_data = {
            MA_TD: this.txtMaTocDo,
            TOCDO: this.txtTocDo,
            THUONGHIEU: this.txtThuongHieu,
            GHICHU: this.txtGhiChu,
            LOAITB_ID: this.ddlLoaiHinh,
            MATOCDO: this.txtMaTocDoKichHoat,
            MATOCDO_VDC: this.txtTocDoVDC,
            TOCDOTHUC: this.txtTocDoThuc,
            PROFILE_ID: this.txtProfileId,
            CHECKIPTINH: ip_tinh,
            KICHHOAT: kich_hoat,
            SUDUNG: su_dung,
            PROFILEDSL_ID: profile,
            TOCDO_ID: this.toc_do_id,
          };

        api.Them_Sua_Xoa(this.axios, {
            kieu: 0,
            ds: JSON.stringify(json_data)
          })
            .then((res) => {
              var mes = res.data.data ? res.data.data : [];
              console.log(JSON.stringify(json_data), mes);
              if (mes == '1') {
                this.getDanhSachGrid();
                this.$toast.success('Thêm dữ liệu thành công"!');
              } else {
                this.getDanhSachGrid();
                this.$toast.error('Thêm dữ liệu lỗi"!');

              }
            });
        } catch (error) { }
      },

      Sua() {
        try {
          if (!this.KiemTra_DuLieu()) return false;
          var ip_tinh = 0;
          var kich_hoat = 0;
          var su_dung = 0;
          var profile = 147;
          if (this.chkIp_Tinh == true) {
            ip_tinh = 1;
          }
          if (this.chkKichHoat == true) {
            kich_hoat = 1;
          }
          if (this.chkHieuLuc == true) {
            su_dung = 1;
          }
          if (this.chkProfileDslam == true) {
            if (this.ddlProfileDslam != null && this.ddlProfileDslam != '' && this.ddlProfileDslam != "") {
              profile = this.ddlProfileDslam;
            }
          }
          var json_data = {
            MA_TD: this.txtMaTocDo,
            TOCDO: this.txtTocDo,
            THUONGHIEU: this.txtThuongHieu,
            GHICHU: this.txtGhiChu,
            LOAITB_ID: this.ddlLoaiHinh,
            MATOCDO: this.txtMaTocDoKichHoat,
            MATOCDO_VDC: this.txtTocDoVDC,
            TOCDOTHUC: this.txtTocDoThuc,
            PROFILE_ID: this.txtProfileId,
            CHECKIPTINH: ip_tinh,
            KICHHOAT: kich_hoat,
            SUDUNG: su_dung,
            PROFILEDSL_ID: profile,
            TOCDO_ID: this.toc_do_id,
          };

          api.Them_Sua_Xoa(this.axios, {
            kieu: 2,
            ds: JSON.stringify(json_data)
          })
            .then((res) => {
              var mes = res.data.data ? res.data.data : [];
              console.log(JSON.stringify(json_data), mes);
              if (mes == '1') {
                this.getDanhSachGrid();
                this.$toast.success('Cập nhật dữ liệu thành công"!');
              } else {
                this.getDanhSachGrid();
                this.$toast.error('Cập nhật dữ liệu lỗi"!');
              }
            });
        } catch (error) { }
      },


      Xoa() {
        try {
          var json_data = {
            TOCDO_ID: this.toc_do_id,
          };
          this.$confirm(`Bạn thật sự muốn xóa dữ liệu không ?`, {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
            type: "warning",
          }).then(async () => {
            api.Them_Sua_Xoa(this.axios, {
              kieu: 1,
              ds: JSON.stringify(json_data)
            })
              .then((res) => {
                var mes = res.data.data ? res.data.data : [];
                console.log(JSON.stringify(json_data), mes, this.toc_do_id);
                if (mes == '1') {
                  this.getDanhSachGrid();
                  this.$toast.success('Xóa dữ liệu thành công"!');

                } else {
                  this.getDanhSachGrid();
                  this.$toast.error('Xóa dữ liệu lỗi"!');

                }
              });
          })

        } catch (error) { }
      },



      KiemTra_DuLieu() {
        if (!this.ddlLoaiHinh) {
          this.$toast.error('Loại hình không được để trống!');
          return false;
        }
        if (!this.txtMaTocDo) {
          this.$toast.error('Mã tốc độ (tỉnh) không được để trống!');
          return false;
        }
        if (!this.txtTocDo) {
          this.$toast.error('Tốc độ không được để trống!');
          return false;
        }
        if (!this.txtThuongHieu) {
          this.$toast.error('Thương hiệu không được để trống!');
          return false;
        }
        if (!this.txtTocDoVDC) {
          this.$toast.error('Tốc độ VDC không được để trống!');
          return false;
        }
        if (!this.txtTocDoThuc) {
          this.$toast.error('Tốc độ thực không được để trống!');
          return false;
        }
        if (!this.txtMaTocDoKichHoat) {
          this.$toast.error('Mã tốc độ (kích hoạt) không được để trống!');
          return false;
        }
        if (!this.txtProfileId) {
          this.$toast.error('Profile ID không được để trống!');
          return false;
        }

        api.KtraDuLieu(this.axios, {
          ma_ts: "BATBUOC_PROFILE_BNM"
        })
          .then((res) => {
            var ten_ts = res.data.data.ten_ts ? res.data.data.ten_ts : '-1';
            if (ten_ts.parseInt == 1) {
              if (!this.ddlProfileDslam) {
                this.$toast.error('Profile dslam không được để trống!');
                return false;
              }
            }
          });
        return true;
      },


      clear() {
        this.txtMaTocDo = ''
        this.txtTocDo = ''
        this.txtThuongHieu = ''
        this.txtTocDoVDC = ''
        this.txtTocDoThuc = ''
        this.txtMaTocDoKichHoat = ''
        this.txtProfileId = ''
        this.txtGhiChu = ''
        this.toc_do_id = ''
        this.chkHieuLuc = false
        this.chkProfileDslam = false
        this.chkIp_Tinh = false
        this.chkKichHoat = false
        this.chkLoaiHinh_TimKiem = false
      },

      clickButton(key) {
        if (key == "nhapmoi") {
          this.clear()
          this.button.nhapmoi = false
          this.button.xoa = true
          this.button.ghilai = true
          this.button.huy = true
        }
        if (key == "ghilai") {
          if (this.button.nhapmoi == true) {
            this.Sua();
          }
          else {
            this.Them();
          }
          this.button.nhapmoi = true
          this.button.xoa = true
          this.button.ghilai = true
          this.button.huy = true
        }
        if (key == "huy") {
          this.clear();
          this.button.nhapmoi = true
          this.button.xoa = false
          this.button.ghilai = false
          this.button.huy = false
        }
        if (key == "xoa") {
          this.Xoa();
          this.button.nhapmoi = true
          this.button.xoa = true
          this.button.ghilai = true
          this.button.huy = true
        }

      },
    },
  };
</script>
<style>
  li.non-active a {
    color: #d3d3d3 !important;
  }

  @import "https://cdn.syncfusion.com/ej2/material.css";
</style>
