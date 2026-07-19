<template>
  <ejs-dialog
    :header="'Danh mục nhóm số'"
    showCloseIcon="true"
    width="40%"
    height="600px"
    :target="target"
    :visible="false"
    ref="frmNhomso"
  >
    <vue-nav>
      <ul class="list">
        <li
          :class="!button.nhapmoi ? 'non-ative' : ''"
          @click="clickButton('nhapmoi')"
        >
          <a> <span class="icon one-file-plus"></span>Nhập mới </a>
        </li>
        <li
          :class="!button.ghilai ? 'non-ative' : ''"
          @click="clickButton('ghilai')"
        >
          <a> <span class="icon one-save"></span>Ghi lại </a>
        </li>
        <li :class="!button.xoa ? 'non-ative' : ''" @click="clickButton('xoa')">
          <a> <span class="icon one-trash"></span>Xóa </a>
        </li>
      </ul>
    </vue-nav>
    <div class="popup-body">
      <vue-card>
        <div class="info-row">
          <div class="key w90">Nhóm số</div>
          <div class="value">
            <input
              ref="txtNhomSo"
              type="textbox"
              class="form-control"
              @keyup="checkNhomSo"
              v-model="form.nhom_so"
            />
          </div>
        </div>

        <div class="info-row">
          <div class="key w90">Tiền cam kết</div>
          <div class="value">
            <input
              ref="txtTienCamKet"
              type="textbox"
              v-model="form.tien_ck"
              @keyup="checkTienCK"
              class="form-control tright red"
            />
          </div>
        </div>

        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w90">Từ tháng</div>
              <div class="value">
                <input
                  ref="txtTuThang"
                  type="number"
                  v-model="form.tuthang"
                  min="0"
                  max="12"
                  @keyup="checkTuThang"
                  class="form-control tright red"
                />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w80">Đến tháng</div>
              <div class="value">
                <input
                  ref="txtDenThang"
                  type="number"
                  v-model="form.denthang"
                  min="0"
                  max="12"
                  class="form-control tright red"
                  @keyup="checkDenThang"
                  value="10"
                />
              </div>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w90">Ghi chú</div>
          <div class="value">
            <textarea
              ref="txtGhichu"
              name
              class="form-control"
              v-model="form.ghichu"
              @keyup="checkGhichu"
              style="height: 100px; resize: none"
            ></textarea>
          </div>
        </div>
      </vue-card>
      <vue-card label="Danh sách nhóm số">
        <DataGrid
          v-bind:columns="columns"
          v-bind:dataSource="options.danhsach_nhomso"
          :showColumnCheckbox="false"
          :allowPaging="true"
          :enablePagingServer="false"
          ref="danhsach"
          @selectedItemsChanged="grvDanhSach_FocusedRowChanged"
          @rowClicked="focusItem"
          :showFilter="true"
        />
      </vue-card>
    </div>
  </ejs-dialog>
</template>
<script>
export default {
  props: ["dsKhieuso"],
  watch: {
    "form.tuthang"(val) {
      if (val < 0) {
        this.form.tuthang = 0;
      }
    },
    "form.denthang"(val) {
      if (val < 0) {
        this.form.denthang = 0;
      }
    },
  },
  data() {
    return {
      options: {
        danhsach_nhomso: [],
      },
      item: {},
      target: ".main-wrapper",
      columns: [
        {
          fieldName: "nhom_so",
          headerText: "Nhóm số",
          allowFiltering: true,
          allowSorting: true,
          isPrimaryKey: true,
          width: "auto",
        },
      ],
      dataSource: [],
      form: {
        denthang: null,
        ghichu: null,
        nhom_so: null,
        tien_ck: null,
        tuthang: null,
      },
      button: {
        nhapmoi: false,
        ghilai: false,
        xoa: false,
      },
      current: 0,
    };
  },
  methods: {
    focusItem(index, data) {
      this.current = index;
    },
    async GhiLai() {
      try {
        if (!this.KiemTra_DuLieu()) return;
        this.loading(true);
        let kieu = !this.button.nhapmoi ? 1 : 0;
        let records = this.$refs.danhsach.getSelectedRecords();

        let data = [
          {
            NHOM_SO: this.form.nhom_so ? this.form.nhom_so.trim() : "",
            GHICHU: this.form.ghichu ? this.form.ghichu.trim() : "",
            TUTHANG: this.form.tuthang,
            DENTHANG: this.form.denthang,
            TIEN_CK: this.form.tien_ck,
            NHOMSO_ID: records[0].nhomso_id,
          },
        ];

        let rs = await this.axios.post(
          "/web-quantri/kieusodep/sp_nhomso_update",
          {
            is_insert: kieu,
            js_data: JSON.stringify(data),
          }
        );
        this.$toast.success(
          !this.button.nhapmoi ? "Thêm mới thành công!" : "Cập nhật thành công!"
        );
        if (rs.data.data == 1) {
          if (kieu == 0) {
            this.LayDanhSachNhomSo(this.current);
          } else {
            this.LayDanhSachNhomSo(0);
          }
          this.SetButton(3);
          this.$emit("must_update", true);
        } else {
          this.$toast.error(rs.data.data);
        }
        this.loading(false);
      } catch (err) {
        console.log(err)
        this.loading(false);
        this.$toast.error("Đã có lỗi xảy ra!");
      }
    },
    checkNhomSo() {
      let status = true;
      this.$refs.txtNhomSo.classList.remove("error");
      if (!this.form.nhom_so) {
        this.$toast.error("Tên nhóm số không được để trống !");
        this.$refs.txtNhomSo.focus();
        this.$refs.txtNhomSo.classList.add("error");
        status = false;
      }

      if (this.form.nhom_so.length > 100) {
        this.$toast.error("Độ dài tên nhóm số không được lớn hơn 100 !");
        this.$refs.txtNhomSo.focus();
        this.$refs.txtNhomSo.classList.add("error");
        status = false;
      }

      return status;
    },
    checkTienCK() {
      this.$refs.txtTienCamKet.classList.remove("error");
      let status = true;
    //   if (this.form.tien_ck == "") {
    //     this.$toast.error("Tiền cam kết không được để trống !");
    //     this.$refs.txtTienCamKet.focus();
    //     this.$refs.txtTienCamKet.classList.add("error");
    //     status = false;
    //   }
      if(!this.form.tien_ck) {
        this.form.tien_ck = 0
      }
      if (isNaN(this.form.tien_ck)) {
        this.form.tien_ck = 0;
      }

      return status;
    },
    checkTuThang() {
      this.$refs.txtTuThang.classList.remove("error");
      let status = true;
      if (Number(this.form.tuthang) > Number(this.form.denthang)) {
        this.$toast.error("Từ tháng không được lớn hơn đến tháng!");
        this.$refs.txtTuThang.focus();
        this.$refs.txtTuThang.classList.add("error");
        status = false;
      }
      if (this.form.tuthang < 0) {
        this.$toast.error("từ tháng không được nhỏ hơn 0 !");
        this.$refs.txtTuThang.focus();
        this.$refs.txtTuThang.classList.add("error");
        status = false;
      }

      return status;
    },
    checkDenThang() {
      let status = true;
      this.$refs.txtDenThang.classList.remove("error");

      if (this.form.denthang < 0) {
        this.$toast.error("đến tháng không được nhỏ hơn 0 !");
        this.$refs.txtDenThang.focus();
        this.$refs.txtDenThang.classList.add("error");
        status = false;
      }

      return status;
    },
    checkGhichu() {
      let status = true;
      this.$refs.txtGhichu.classList.remove("error");
      return status;
    },
    KiemTra_DuLieu() {
        if(!this.checkNhomSo()) {
          return false
        }
        if(!this.checkTienCK()) {
          return false
        }
        if(!this.checkTuThang()) {
          return false
        }
        if(!this.checkDenThang()) {
          return false
        }

        return true
    },
    clickButton(type) {
      if (!this.button[type]) return false;
      if (type == "nhapmoi") this.tsbtnNhapMoi_Click();
      if (type == "ghilai") this.GhiLai();
      if (type == "xoa") this.tsbtnDelete_Click();
    },
    setIndex(index) {
      this.$refs.danhsach.flagSelectedRowIndexes = [];
      this.$refs.danhsach.flagSelectedRowIndexes.push(index);
      this.$refs.danhsach.setCurrentSelectedRow(index);
    },
    tsbtnNhapMoi_Click() {
      this.SetButton(1);
      this.form.nhom_so = "";
      this.form.ghichu = "";
      this.form.tien_ck = 0;
      this.form.tuthang = 0;
      this.form.denthang = 0;
    },
    async frmNhomSo_Load() {
      try {
        this.SetButton(1);
        this.LayDanhSachNhomSo(0);
      } catch (err) {
        this.$toast.error("Lỗi " + err.message);
      }
    },
    async tsbtnDelete_Click() {
      try {
        this.$confirm(`Bạn thật sự muốn xóa dữ liệu không ?`, {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
          type: "delete",
        }).then(async () => {
          this.loading(true);
          let records = this.$refs.danhsach.getSelectedRecords();
          if (
            this.dsKhieuso.filter(
              (item) => item.NHOMSO_ID == records[0].nhomso_id
            ).length > 0
          ) {
            this.$toast.error("Nhóm số này đã được gán, không thể xóa !");
            this.loading(false);
            return false;
          }
          let rs = await this.axios.post(
            "/web-quantri/kieusodep/sp_nhomso_delete",
            {
              nhom_so_id: records[0].nhomso_id,
            }
          );
          this.$emit("must_update", true);
          this.LayDanhSachNhomSo(0);
          this.SetButton(1);
          this.loading(false);
          this.$toast.success("Xóa dữ liệu thành công");
        });
      } catch (e) {
        this.$toast.error("Xóa dữ liệu thất bại");
      }
    },
    async LayDanhSachNhomSo(key) {
      let rs = await this.axios.get("/web-quantri/kieusodep/sp_lay_ds_nhomso");
      let tempdata = [];
      let temp = rs.data.data;
      temp.forEach((item) => {
        tempdata.unshift(item);
      });
      this.$set(this.options, "danhsach_nhomso", tempdata);
      this.setIndex(key);
    },
    grvDanhSach_FocusedRowChanged(data) {
      if (data[0]) {
        this.form.denthang = data[0].denthang;
        this.form.ghichu = data[0].ghichu;
        this.form.nhom_so = data[0].nhom_so;
        this.form.tien_ck = data[0].tien_ck;
        this.form.tuthang = data[0].tuthang;
        this.SetButton(3);
      }
    },
    async showModal() {
      this.$refs.frmNhomso.show();
      this.frmNhomSo_Load();
    },
    SetButton(kieu) {
      this.button.nhapmoi = false;
      this.button.ghilai = false;
      this.button.xoa = false;
      if (kieu == -1) {
        //Bat dau
        this.button.ghilai = true;
      }
      if (kieu == 0) {
        //Bat dau
        this.button.nhapmoi = true;
      }
      if (kieu == 1) {
        //Them moi
        this.button.ghilai = true;
      }
      if (kieu == 2) {
        //Huy
        this.button.nhapmoi = true;
        this.button.xoa = true;
      }
      if (kieu == 3) {
        //Edit
        this.button.nhapmoi = true;
        this.button.ghilai = true;
        this.button.xoa = true;
      }
    },
  },
};
</script>