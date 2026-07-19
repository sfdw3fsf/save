<template>
  <div>
    <ejs-dialog
      ref="dialog"
      :enableResize="true"
      :visible="false"
      :header="'Quản lý hồ sơ'"
      showCloseIcon="true"
      :target="target"
      width="60%"
      height="700"
    >
      <vue-nav>
        <ul class="list">
          <li :class="!button.nhapmoi ? 'non-ative' : ''">
            <a> <span class="icon one-file-plus"></span>Nhập mới </a>
          </li>
          <li :class="!button.ghilai ? 'non-ative' : ''">
            <a> <span class="icon one-save"></span>Ghi lại (F9) </a>
          </li>
          <li :class="!button.downhs ? 'non-ative' : ''">
            <a> <span class="icon one-file-download"></span>Down HS </a>
          </li>
          <li :class="!button.xoahs ? 'non-ative' : ''">
            <a @click="delete_HS()"> <span class="icon one-trash"></span>Xóa HS (F8) </a>
          </li>
        </ul>
      </vue-nav>
      <div class="popup-body">
        <vue-card label="Thông tin hồ sơ khiếu nại">
          <b-row>
            <vue-element
              labelWidth="120"
              class="col-md-6"
              label="Số máy/Account"
            >
              <ejs-textbox v-model="form.ma_tb"></ejs-textbox>
            </vue-element>
            <vue-element labelWidth="100" class="col-md-3" label="Người KN">
              <ejs-textbox v-model="form.nguoi_kn"></ejs-textbox>
            </vue-element>
            <vue-element
              labelWidth="100"
              class="col-md-3"
              label="Điện thoại LH"
            >
              <ejs-textbox v-model="form.dienthoai_lh"></ejs-textbox>
            </vue-element>
            <vue-element labelWidth="120" class="col-md-6" label="Tên thuê bao">
              <ejs-textbox v-model="form.ten_tb"></ejs-textbox>
            </vue-element>
            <vue-element labelWidth="100" class="col-md-3" label="Hẹn TL từ">
              <ejs-datepicker
                v-model="form.ngay_tn"
                class="form-control bg-none bor0 fw6 text h24"
                :placeholder="waterMark"
                :value="dateVal"
                :format="dateFormat"
              ></ejs-datepicker>
            </vue-element>
            <vue-element labelWidth="100" class="col-md-3" label="Hẹn TL đến">
              <ejs-datepicker
                v-model="form.ngay_gq"
                class="form-control bg-none bor0 fw6 text h24"
                :placeholder="waterMark"
                :value="dateVal"
                :format="dateFormat"
              ></ejs-datepicker>
            </vue-element>
            <vue-element
              labelWidth="120"
              class="col-md-6"
              label="Địa chỉ thuê bao"
            >
              <ejs-textbox v-model="form.diachi_tb"></ejs-textbox>
            </vue-element>
            <vue-element labelWidth="100" class="col-md-3" label="Ngày hồ sơ">
              <ejs-datepicker
                class="form-control bg-none bor0 fw6 text h24"
                :value="dateVal"
                v-model="form.ngay_hs"
                :format="dateFormat"
              ></ejs-datepicker>
            </vue-element>
            <b-col md="12">
              <div class="info-row">
                <div class="key w80">Đường dẫn</div>
                <div class="value">
                  <div class="input-more-button">
                    <button class="btn">
                      <span class="-ap icon-more_horiz"></span>
                    </button>
                    <ejs-textbox v-model="form.duongdan"></ejs-textbox>
                  </div>
                </div>
              </div>
            </b-col>
          </b-row>
        </vue-card>
        <vue-card label="Danh sách khiếu nại">
          <DataGrid
            ref="danh_sach_khieu_nai"
            :showFilter="true"
            :allowPaging="true"
            :enablePagingServer="false"
            :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
            :dataSource="options.danhsachKN"
            :columns="config.dskn"
            @rowSelected="focusItem"
          />
        </vue-card>
        <vue-card label="Danh sách file hồ sơ">
          <DataGrid
            ref="danh_sach_file"
            :showFilter="true"
            :allowPaging="true"
            :enablePagingServer="false"
            :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
            :dataSource="options.dsFile"
            :columns="config.dsFile"
          />
        </vue-card>
      </div>
    </ejs-dialog>
  </div>
</template>
<script>
import moment from 'moment';
import VueElement from "../../../search/subscriber/components/elements/VueElement.vue";
const field = {
  ma_tb: "",
  nguoi_kn: "",
  dienthoai_lh: "",
  ten_tb: "",
  hengq_tu: "",
  hengq_den: "",
  diachi_tb: "",
  ngaycap: "",
  duongdan: "",
  ngay_tn: "",
  ngay_gq: "",
  khieunai_id: "",
  ky_kn: "",
  ngay_hs: moment().format("DD/MM/YYYY")
};
export default {
  components: { VueElement },
  data() {
    return {
      target: ".main-wrapper",
      waterMark: "__/__/____",
      dateVal: new Date(),
      dateFormat: "dd/MM/yyyy",
      options: {
        danhsachKN: [],
        dsFile: [],
      },
      input: {},
      config: {
        dskn: [
          {
            headerText: "Mã KN",
            width: "100",
            allowSorting: false,
            fieldName: "ma_kn",
          },
          {
            headerText: "Số máy/Account",
            width: "100",
            allowSorting: false,
            fieldName: "ma_tb",
          },
          {
            headerText: "Người khiếu nại",
            width: "120",
            allowSorting: false,
            fieldName: "nguoi_kn",
          },
          {
            headerText: "Điện thoại",
            width: "100",
            allowSorting: false,
            fieldName: "dienthoai_lh",
          },
          {
            headerText: "Chủ đề khiếu nại",
            width: "100",
            allowSorting: false,
            fieldName: "tenchude",
          },
          {
            headerText: "Người tiếp nhận",
            width: "100",
            allowSorting: false,
            fieldName: "ten_nv",
          },
        ],
        dsFile: [
          {
            headerText: "STT",
            width: "100",
            allowSorting: false,
            fieldName: "stt",
          },
          {
            headerText: "Ngày hồ sơ",
            width: "100",
            allowSorting: false,
            fieldName: "ngay_hs",
          },
          {
            headerText: "Đường dẫn Server",
            width: "120",
            allowSorting: false,
            fieldName: "server_url",
          },
          {
            headerText: "Người CN",
            width: "100",
            allowSorting: false,
            fieldName: "nguoi_cn",
          },
        ],
      },
      form: {},
      button: {
        nhapmoi: false,
        ghilai: false,
        downhs: false,
        xoahs: false
      }
    };
  },
  mounted() {
    this.resetForm();
  },
  methods: {
    resetForm() {
      for (let key in field) {
        this.$set(this.form, key, null);
      }
    },
    setInput(value) {
      this.input = value;
    },
    async open() {
      this.$refs.dialog.show();
      if (this.input.khieunai_id) {
        this.getHSKhieunai();
      }
    },
    async focusItem(data) {
      this.resetForm();
      if (data.data) {
        for (let key in field) {
          this.$set(this.form, key, data.data[key] ? data.data[key] : field[key]);
        }

        this.SetButton(3)
      }

    },
    async delete_HS() {
      var data = this.$refs.danh_sach_file.getSelectedRecords()
      if(data.length == 0) {
        this.$toast.error("Không có bản ghi nào được chọn")
        return false
      }
      this.$confirm(`Bạn có muốn xóa phiếu khiếu nại này không ?`,
          'Xóa phiếu khiếu nại',
          {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            type: 'warning'
          }).then(async () => {
          
          await this.axios.post("web-gqkn/TraCuuPhieuKN/sp_xoa_hoso_khieunai", {
            "v_hosokn_id": data[0].hoso_id
          })

          this.$toast.success("Xóa thành công!")
        });
    },
    async getHSKhieunai() {
      this.loading(true)
      try {
        let data = await this.axios.post(
          "web-gqkn/TraCuuPhieuKN/lay_thongtintb_khieunai_id",
          {
            vkhieunai_id: this.input.khieunai_id,
            vkyhoadon: this.input.ky_kn,
            vma_tb: this.input.ma_tb,
          }
        );

        let dataKN = await this.axios.post(
        "web-gqkn/TraCuuPhieuKN/lay_thongtin_hs_kn",
          {
            vkhieunai_id: this.input.khieunai_id,
            vkyhoadon: this.input.ky_kn,
          }
        );
        this.options.dsFile = dataKN.data ? dataKN.data.data : [];

        this.options.danhsachKN = data.data ? data.data.data : [];
        if(this.options.danhsachKN.length == 0) {
          this.SetButton(-1)
        }
      } catch (err) {
        this.$toast.error("Không tìm được bản ghi nào!");
      }
      this.loading(false)
    },
    SetButton(kieu) {
      this.button.nhapmoi = false;
      this.button.ghilai = false;
      this.button.downhs = false;
      this.button.xoahs = false;

      if (kieu == -1) {
        //Bat dau
        this.button.ghilai = true;
      }

      if (kieu == 0) {
        //Bat dau
        this.button.nhapmoi = true;
        this.resetForm()
      }

      if (kieu == 1) {
        //Them moi
        this.button.ghilai = true;
        this.form.duongdan = "";
        this.form.ngaycap = moment().format("DD/MM/YYYY");
      }

      if (kieu == 3) {
        //Edit
        this.button.nhapmoi = true;
        this.button.xoahs = true;
        this.button.ghilai = true;
        this.button.downhs = true;
      }
      if (kieu == 4) {
        //xoa
        this.button.nhapmoi = true;
        this.form.duongdan = "";
      }
    },
  },
};
</script>
<style>
  .non-ative a {
    color: #d3d3d3 !important;
  }
</style>