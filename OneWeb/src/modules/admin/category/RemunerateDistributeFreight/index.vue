<template>
  <div>
    <vue-bread-crumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li
          @click="clickButton('nhapmoi')"
          :class="button.nhapmoi ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-plus"></span>Nhập mới </a>
        </li>
        <li
          @click="clickButton('ghilai')"
          :class="button.ghilai ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-save"></span>Ghi lại </a>
        </li>

        <li
          @click="clickButton('huybo')"
          :class="button.huybo ? 'active' : 'non-active'"
        >
          <a>
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy
          </a>
        </li>
        <li
          @click="clickButton('xoa')"
          :class="button.xoa ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-trash"></span>Xóa </a>
        </li>
      </ul>
    </vue-nav>
    <div class="page-content">
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="box-form">
            <div class="legend-title">
              Thông tin đơn giá thù lao phát giấy báo cước
            </div>

            <div class="row">
              <div class="col-sm-8 col-12">
                <div class="info-row">
                  <div class="key w90">Nội dung (*)</div>
                  <div class="value">
                    <input
                      v-model="txtDonGia_PBC"
                      type="text"
                      class="form-control"
                    />
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key w70">Đơn giá (*)</div>
                  <div class="value">
                    <input
                      v-model="txtDonGia"
                      type="number"
                      class="form-control text tright fw6"
                      value="0"
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="box-form">
            <div class="legend-title">
              Danh sách đơn giá thù lao phát giấy báo cước
            </div>
            <DataGrid
              v-bind:dataSource="options.danh_sach_thu_lao"
              :columns="collumns.danh_sach_thu_lao"
              :enablePagingServer="true"
              :allowPaging="true"
              :pageIndex="pageIndex"
              :pageSize="pageSize"
              ref="danh_sach_thu_lao"
              :totalRecords="totalRecords"
              @selectedRowChanged="selectedRowChanged"
              @pageChanged="pageChanged"
              @recordClick="setCurrent"
            />
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="box-move-select-table">
            <div class="box-col box-form">
              <div class="legend-title">Kỳ hóa đơn đã gán (*)</div>
              <div class="table-content" style="height: 200px">
                <ejs-grid
                  class="no-header"
                  height="190"
                  v-bind:dataSource="left.kyhoadon"
                  ref="kyhoadon_left"
                >
                  <e-columns>
                    <e-column type="checkbox" width="50"></e-column>
                    <e-column
                      field="CHUKYNO"
                      headerText="Chu kỳ nợ"
                      textAlign="Left"
                      width="auto"
                    ></e-column>
                  </e-columns>
                </ejs-grid>
              </div>
            </div>
            <div class="actions">
              <button @click="toRight('kyhoadon')" class="btn">
                <span class="fa fa-angle-right"></span>
              </button>
              <button @click="toAllRight('kyhoadon')" class="btn">
                <span class="fa fa-angle-double-right"></span>
              </button>
              <button @click="toLeft('kyhoadon')" class="btn">
                <span class="fa fa-angle-left"></span>
              </button>
              <button @click="toAllLeft('kyhoadon')" class="btn">
                <span class="fa fa-angle-double-left"></span>
              </button>
            </div>
            <div class="box-col box-form">
              <div class="legend-title">Kỳ hóa đơn chưa gán</div>
              <div class="table-content" style="height: 200px">
                <ejs-grid
                  v-bind:dataSource="right.kyhoadon"
                  class="no-header"
                  height="190"
                  ref="kyhoadon_right"
                >
                  <e-columns>
                    <e-column type="checkbox" width="50"></e-column>
                    <e-column
                      field="CHUKYNO"
                      headerText="Chu kỳ nợ"
                      textAlign="Left"
                      width="auto"
                    ></e-column>
                  </e-columns>
                </ejs-grid>
              </div>
            </div>
          </div>
          <div class="box-move-select-table">
            <div class="box-col box-form">
              <div class="legend-title">Loại nhân viên đã gán (*)</div>
              <div class="table-content" style="height: 200px">
                <ejs-grid
                  class="no-header"
                  v-bind:dataSource="left.loainhanvien"
                  ref="loainhanvien_left"
                  height="190"
                >
                  <e-columns>
                    <e-column type="checkbox" width="50"></e-column>
                    <e-column
                      field="TEN"
                      headerText="loại nhân viên"
                      textAlign="Left"
                      width="auto"
                    ></e-column>
                  </e-columns>
                </ejs-grid>
              </div>
            </div>
            <div class="actions">
              <button @click="toRight('loainhanvien')" class="btn">
                <span class="fa fa-angle-right"></span>
              </button>
              <button @click="toAllRight('loainhanvien')" class="btn">
                <span class="fa fa-angle-double-right"></span>
              </button>
              <button @click="toLeft('loainhanvien')" class="btn">
                <span class="fa fa-angle-left"></span>
              </button>
              <button @click="toAllLeft('loainhanvien')" class="btn">
                <span class="fa fa-angle-double-left"></span>
              </button>
            </div>
            <div class="box-col box-form">
              <div class="legend-title">Loại nhân viên chưa gán</div>
              <div class="table-content" style="height: 200px">
                <ejs-grid
                  class="no-header"
                  v-bind:dataSource="right.loainhanvien"
                  ref="loainhanvien_right"
                  height="190"
                >
                  <e-columns>
                    <e-column type="checkbox" width="50"></e-column>
                    <e-column
                      field="TEN"
                      headerText="loại nhân viên"
                      textAlign="Left"
                      width="auto"
                    ></e-column>
                  </e-columns>
                </ejs-grid>
              </div>
            </div>
          </div>
          <div class="box-move-select-table">
            <div class="box-col box-form">
              <div class="legend-title">Khu vực đã gán</div>
              <div class="table-content" style="height: 200px">
                <ejs-grid
                  class="no-header"
                  v-bind:dataSource="left.khuvuc"
                  ref="khuvuc_left"
                  height="190"
                >
                  <e-columns>
                    <e-column type="checkbox" width="50"></e-column>
                    <e-column
                      field="TEN_KV"
                      headerText="Khu vực"
                      textAlign="Left"
                      width="auto"
                    ></e-column>
                  </e-columns>
                </ejs-grid>
              </div>
            </div>
            <div class="actions">
              <button @click="toRight('khuvuc')" class="btn">
                <span class="fa fa-angle-right"></span>
              </button>
              <button @click="toAllRight('khuvuc')" class="btn">
                <span class="fa fa-angle-double-right"></span>
              </button>
              <button @click="toLeft('khuvuc')" class="btn">
                <span class="fa fa-angle-left"></span>
              </button>
              <button @click="toAllLeft('khuvuc')" class="btn">
                <span class="fa fa-angle-double-left"></span>
              </button>
            </div>
            <div class="box-col box-form">
              <div class="legend-title">Khu vực chưa gán</div>
              <div class="table-content" style="height: 200px">
                <ejs-grid
                  class="no-header"
                  v-bind:dataSource="right.khuvuc"
                  ref="khuvuc_right"
                  height="190"
                >
                  <e-columns>
                    <e-column type="checkbox" width="50"></e-column>
                    <e-column
                      field="TEN_KV"
                      headerText="Khu vực"
                      textAlign="Left"
                      width="auto"
                    ></e-column>
                  </e-columns>
                </ejs-grid>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import define from "./define";
export default {
  data() {
    return {
      ...define,
    };
  },
  mounted() {
    this.frmDonGia_PBC_Load();
  },
  watch: {
    DONGIA_PBC_ID(val) {
      this.HienThi_DonGia_Theo_ChuKyNo();
      this.HienThi_DONGIA_PBC_Theo_LoaiNV();
      this.HienThi_DONGIA_PBC_Theo_KhuVuc();
    },
  },
  methods: {
    setCurrent(data) {
      this.current = data.rowIndex;
    },
    async tsbtnXoa_Click() {
      this.$confirm(
        `Bạn chắc chắn muốn xóa đơn giá thù lao phát giấy báo cước không ?`,
        "Giản đồ mạng cáp",
        {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
          type: "warning",
        }
      ).then(async () => {
        try {
          await this.axios.post(
            "/web-quantri/dongia_thulao_phatgbc/sp_del_dongia",
            {
              dongia_pbc_id: this.DONGIA_PBC_ID,
            }
          );
          this.LoadGrid();
        } catch (error) {
          this.$toast.error("Lỗi khi xóa!");
        }
      });
    },
    clickButton(key) {
      if (!this.button[key]) {
        return false;
      }
      if (key == "ghilai") {
        this.ghilai();
      }
      if (key == "nhapmoi") {
        this.tsbtnNhapMoi_Click();
      }
      if (key == "huybo") {
        this.tsbtnHuyBo_Click();
      }
      if (key == "xoa") {
        this.tsbtnXoa_Click();
      }
    },
    tsbtnNhapMoi_Click() {
      this.SetButton(1);
    },
    tsbtnHuyBo_Click() {
      this.SetButton(0);
    },
    async ghilai() {
      try {
        if(!this.KiemTra_DuLieu())
          return false;
        this.loading(true);
        let dataKV = [];
        this.left.khuvuc.forEach((item) => {
          dataKV.push({
            KHUVUC_ID: item.KHUVUC_ID,
          });
        });

        let dataLNV = [];
        this.left.loainhanvien.forEach((item) => {
          dataLNV.push({
            LOAINV_ID: item.LOAINV_ID,
          });
        });

        let dataKHD = [];
        this.left.kyhoadon.forEach((item) => {
          dataKHD.push({
            CHUKYNO: item.ID,
          });
        });

        let rs = await this.axios.post(
          "/web-quantri/dongia_thulao_phatgbc/sp_ghilai_dongia",
          {
            dongia_pbc_id: this.DONGIA_PBC_ID,
            json_dongia: [
              {
                DONGIA_PBC_ID: this.DONGIA_PBC_ID,
                DONGIA_PBC: this.txtDonGia_PBC,
                DONGIA: this.txtDonGia,
              },
            ],
            json_dongia_kv: dataKV,
            json_dongia_lnv: dataLNV,
            json_dongia_khd: dataKHD,
          }
        );
        this.loading(false);
        this.$toast.success("Ghi lại thành công!");
        this.LoadGrid();
      } catch (error) {
        console.log(error);
        this.$toast.error(JSON.stringify(error));
        this.loading(false);
      }
    },
    toLeft(key) {
      let select_records = this.$refs[key + "_right"].getSelectedRecords();
      let select_indexs = this.$refs[key + "_right"].getSelectedRowIndexes();
      if (select_records.length > 0) {
        this.left[key] = select_records.concat(this.left[key]);


        for (var i = select_indexs.length - 1; i >= 0; i--)
          this.right[key].splice(select_indexs[i], 1);
          console.log(this.right[key].splice(select_indexs[i], 1));
        this.right[key] = [...this.right[key]];
        this.left[key] = [...this.left[key]];
      }
    },
    toRight(key) {
      let select_records = this.$refs[key + "_left"].getSelectedRecords();
      let select_indexs = this.$refs[key + "_left"].getSelectedRowIndexes();
      if (select_records.length > 0) {
        this.right[key] = select_records.concat(this.right[key]);
        for (var i = select_indexs.length - 1; i >= 0; i--)
          this.left[key].splice(select_indexs[i], 1);
        this.right[key] = [...this.right[key]];
        this.left[key] = [...this.left[key]];
      }
    },
    toAllLeft(key) {
      this.left[key] = this.right[key].concat(this.left[key]);
      this.right[key] = [];
      this.right[key] = [...this.right[key]];
      this.left[key] = [...this.left[key]];
    },
    toAllRight(key) {
      this.right[key] = this.left[key].concat(this.right[key]);
      this.left[key] = [];
      this.right[key] = [...this.right[key]];
      this.left[key] = [...this.left[key]];
    },
    selectedRowChanged(item) {
      if (item) {
        this.txtDonGia_PBC = item.dongia_pbc;
        this.txtDonGia = item.dongia;
        this.DONGIA_PBC_ID = item.dongia_pbc_id;
        this.SetButton(3);
      }
    },
    frmDonGia_PBC_Load() {
      this.SetButton(-1);
      this.LoadGrid();
    },
    SetButton(kieu) {
      this.button.nhapmoi = false;
      this.button.ghilai = false;
      this.button.xoa = false;
      this.button.huybo = false;

      if (kieu == -1) {
        //Bat dau
        this.button.ghilai = true;
        this.button.huybo = true;
      }

      if (kieu == 0) {
        //Bat dau
        //txtMaNV.Focus();
        this.button.nhapmoi = true;
        this.Clear();
      }

      if (kieu == 1) {
        //Them moi
        this.button.ghilai = true;
        this.button.huybo = true;
        this.Clear();
      }

      if (kieu == 2) {
        //Huy
        this.button.nhapmoi = true;
        this.button.xoa = true;
        this.Clear();
      }

      if (kieu == 3) {
        //Edit
        //txtTenNV.Focus();
        this.button.nhapmoi = true;
        this.button.ghilai = true;
        this.button.xoa = true;
        this.button.huybo = true;
      }
    },
    async HienThi_DONGIA_PBC_Theo_LoaiNV() {
      try {
        let chuagan = await this.axios.post(
          "/web-quantri/dongia_thulao_phatgbc/sp_lst_loainhanvien_dg",
          {
            p_dongia_pbc_id: this.DONGIA_PBC_ID ? this.DONGIA_PBC_ID : 0,
            ptype: 0,
          }
        );

        let dagan = await this.axios.post(
          "/web-quantri/dongia_thulao_phatgbc/sp_lst_loainhanvien_dg",
          {
            p_dongia_pbc_id: this.DONGIA_PBC_ID ? this.DONGIA_PBC_ID : 0,
            ptype: 1,
          }
        );

        this.left.loainhanvien = dagan.data.data;
        this.right.loainhanvien = chuagan.data.data;
      } catch (e) {
        this.$toast.error("Đã có lỗi xảy ra!");
      }
    },
    async HienThi_DONGIA_PBC_Theo_KhuVuc() {
      try {
        let chuagan = await this.axios.post(
          "/web-quantri/dongia_thulao_phatgbc/sp_lst_khuvuc_dg",
          {
            p_dongia_pbc_id: this.DONGIA_PBC_ID ? this.DONGIA_PBC_ID : 0,
            ptype: 0,
          }
        );

        let dagan = await this.axios.post(
          "/web-quantri/dongia_thulao_phatgbc/sp_lst_khuvuc_dg",
          {
            p_dongia_pbc_id: this.DONGIA_PBC_ID ? this.DONGIA_PBC_ID : 0,
            ptype: 1,
          }
        );

        this.left.khuvuc = dagan.data.data;
        this.right.khuvuc = chuagan.data.data;
      } catch (e) {
        this.$toast.error("Đã có lỗi xảy ra!");
      }
    },
    async HienThi_DonGia_Theo_ChuKyNo() {
      try {
        let chuagan = await this.axios.post(
          "/web-quantri/dongia_thulao_phatgbc/sp_lst_kyhoadon_dg",
          {
            p_dongia_pbc_id: this.DONGIA_PBC_ID ? this.DONGIA_PBC_ID : 0,
            ptype: 0,
          }
        );

        let dagan = await this.axios.post(
          "/web-quantri/dongia_thulao_phatgbc/sp_lst_kyhoadon_dg",
          {
            p_dongia_pbc_id: this.DONGIA_PBC_ID ? this.DONGIA_PBC_ID : 0,
            ptype: 1,
          }
        );

        this.left.kyhoadon = dagan.data.data;
        this.right.kyhoadon = chuagan.data.data;
      } catch (e) {
        this.$toast.error("Đã có lỗi xảy ra!");
      }
    },
    Clear() {
      (this.left.kyhoadon = []), (this.txtDonGia_PBC = null);
      this.txtDonGia = null;
      this.DONGIA_PBC_ID = null;

      this.HienThi_DonGia_Theo_ChuKyNo();
      this.HienThi_DONGIA_PBC_Theo_LoaiNV();
      this.HienThi_DONGIA_PBC_Theo_KhuVuc();
    },
    pageChanged(args) {
      this.pageSize = args.pageSize;
      this.pageIndex = args.pageIndex;
      this.current = 0
      this.LoadGrid();
    },
    async LoadGrid() {
      try {
        let rs = await this.axios.post(
          "/web-quantri/dongia_thulao_phatgbc/sp_grv_thulao_dongia",
          {
            "pageNum": this.pageIndex + 1,
            "pageRec": this.pageSize,
            "totalRow": 1
          }
        );
        this.totalRecords = rs.data.page_info.record_count
        this.options.danh_sach_thu_lao = rs.data.data;
        
        this.options.danh_sach_thu_lao = [...this.options.danh_sach_thu_lao]
        this.$refs.danh_sach_thu_lao.setCurrentSelectedRow(this.current)
        this.$refs.danh_sach_thu_lao.flagSelectedRowIndexes = [];
        this.$refs.danh_sach_thu_lao.flagSelectedRowIndexes.push(this.current);
        if (this.options.danh_sach_thu_lao.length == 0) {
          this.Clear();
        }
      } catch (e) {
        this.$toast.error("Đã có lỗi xảy ra");
      }
    },
    KiemTra_DuLieu() {
      if (!this.txtDonGia_PBC) {
        this.$toast.error(
          'Bạn chưa nhập "Nội dung đơn giá thù lao phát giấy báo cước"!'
        );
        return false;
      }
      if (!this.txtDonGia) {
        this.$toast.error('Bạn chưa nhập "Đơn giá"!');
        return false;
      }
      if (Number(this.txtDonGia) <= 0) {
        this.$toast.error('"Đơn giá" phải lớn hơn 0!');
        return false;
      }

      // if (this.left.khuvuc.length <= 0) {
      //   this.$toast.error("Bạn chưa gán khu vực!");
      //   return false;
      // }

      if (this.left.loainhanvien.length <= 0) {
        this.$toast.error("Bạn chưa gán loại nhân viên!");
        return false;
      }

      if (this.left.kyhoadon.length <= 0) {
        this.$toast.error("Bạn chưa gán kỳ hóa đơn!");
        return false;
      }

      return true
    },
  },
};
</script>
<style>
.no-header .e-columnheader {
  display: none;
}
li.non-active a {
  color: #d3d3d3 !important;
}
</style>
