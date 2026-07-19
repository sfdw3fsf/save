<template>
  <div class="">
    <div class="list-action-top">
      <vue-bread-crumb :header="header" />
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a
              href="#"
              :class="{ disabled: !tsbtnNhapMoi.enabled }"
              @click.prevent="createNew"
            >
              <span class="icon one-file-plus"></span>Nhập mới
            </a>
          </li>

          <li>
            <a href="#" @click.prevent="saveInfo">
              <span class="icon one-save"></span>Ghi lại
            </a>
          </li>
          <li>
            <a href="#" @click.prevent="deleteRecord">
              <span class="icon one-trash"></span>Xóa
            </a>
          </li>
        </ul>
      </div>
    </div>
    <div class="page-content">
      <div class="information-state">
        <div class="box-form">
          <div class="row">
            <div class="legend-title">Cập nhật thông tin</div>
            <div class="row">
              <div class="col-sm-8">
                <div class="row">
                  <div class="col-sm-6">
                    <div class="info-row">
                      <div class="value">
                        <vue-input
                          v-model="maDoanhThu"
                          ref="refmaDoanhThu"
                          :highlight="true"
                          :validate="['required']"
                          :labelWidth="'130'"
                          label="Mã doanh thu"
                          :required="true"
                        ></vue-input>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="info-row">
                      <div class="value">
                        <vue-input
                          v-model="muc"
                          :labelWidth="'130'"
                          label="Mức"
                          :maxlength="2"
                        ></vue-input>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="info-row">
                    <div class="value">
                      <vue-input
                        v-model="tenChiTieu"
                        :highlight="true"
                        ref="reftenChiTieu"
                        :validate="['required']"
                        :labelWidth="'130'"
                        label="Tên chỉ tiêu"
                        :required="true"
                      ></vue-input>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="info-row">
                    <div class="value">
                      <vue-input
                        v-model="ghiChu"
                        ref="refghiChu"
                        :validate="['required']"
                        :labelWidth="'130'"
                        label="Ghi chú"
                      ></vue-input>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="list-state">
        <div class="box-form">
          <div class="legend-title">Danh sách mã doanh thu dịch vụ</div>
          <div class="table-content">
            <table class="table-result table-gachle">
              <DataGrid
                v-bind:columns="[
                  {
                    fieldName: 'ma_dt',
                    headerText: 'Mã doanh thu',
                    allowFiltering: true,
                    allowSorting: false,
                    textAlign: 'left',
                  },
                  {
                    fieldName: 'ten_chitieu',
                    headerText: 'Tên chỉ tiêu',
                    allowFiltering: true,
                    allowSorting: false,
                    textAlign: 'left',
                  },
                  {
                    fieldName: 'muc',
                    headerText: 'Mức',
                    allowFiltering: true,
                    allowSorting: false,
                    textAlign: 'left',
                  },
                  {
                    fieldName: 'ghichu',
                    headerText: 'Ghi chú',
                    allowFiltering: true,
                    allowSorting: false,
                    textAlign: 'left',
                  },
                ]"
                v-bind:dataSource="lvwMaDoanhThu"
                :showFilter="true"
                @selectedRowChanged="lvwMaDoanhthu_SelectedIndexChanged"
                :showColumnCheckbox="false"
                :defaultColumnCheckboxChecked="false"
                :allowPaging="true"
                :enablePagingServer="false"
                :enabled-select-first-row="true"
              >
              </DataGrid>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
  <script>
import API from "./API";
import moment from "moment";
export default {
  name: "State",

  components: {},

  data() {
    return {
      // data header action
      header: {
        title: "Danh mục mã doanh thu dịch vụ",
        list: [
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          {
            name: "Lắp đặt mới",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      lvwMaDoanhThu: [],
      maDoanhThuDichVu: "",
      ghiChu: "",
      maDoanhThu: "",
      muc: 0,
      tenChiTieu: "",
      madt_id:0,
      madt_cha_id:0,
      tsbtnNhapMoi: {
        enabled: true,
      },
    };
  },

  async created() {
    this.getDSMaDoanhThu();
  },

  methods: {
    createNew() {
      this.tsbtnNhapMoi.enabled = false;
      this.maDoanhThu = "";
      this.tenChiTieu = "";
      this.muc = 0;
      this.ghiChu = "";
    },
    saveInfo() {
      //thêm mới
      if (this.maDoanhThu.trim() == "") {
        this.$toast.error("Hãy nhập mã doanh thu!");
        this.$refs.refmaDoanhThu.focus();
        return;
      }
      if (this.tenChiTieu.trim() == "") {
        this.$toast.error("Hãy nhập tên chỉ tiêu!");
        this.$refs.reftenChiTieu.focus();
        return;
      }
      if (this.tsbtnNhapMoi.enabled == false) {
        API.fn_capnhat_madoanhthu(this.axios, {
          vkieu: 0,//thêm mới
          vmadt_id:0,
          vdata: [
            {
              MADT_ID: 0,
              MADT_CHA_ID: 0,
              MA_DT: this.maDoanhThu,
              TEN_CHITIEU: this.tenChiTieu,
              MUC: this.muc,
              NGAY_CN: moment(new Date()).format("DD/MM/YYYY"),
              GHICHU: this.ghiChu,
            },
          ],
        })
          .then((response) => {
            if (
              response.data.error_code === "BSS-00000000" 
            ) {
              this.$toast.success("Thêm mới thành công");
              this.getDSMaDoanhThu();
            }else{
              this.$toast.error("Đã xảy ra lỗi: "+response.data.data)
            }
          })
          .catch((error) => {
            console.log(error);
          })
          .finally(() => {});
        this.tsbtnNhapMoi.enabled = true;
      } else {
        API.fn_capnhat_madoanhthu(this.axios, {
          vkieu: 1,//cập nhật
          vmadt_id: this.madt_id,
          vdata: [
            {
              MADT_ID: this.madt_id,
              MADT_CHA_ID: this.madt_cha_id,
              MA_DT: this.maDoanhThu,
              TEN_CHITIEU: this.tenChiTieu,
              MUC: this.muc,
              NGAY_CN: moment(new Date()).format("DD/MM/YYYY"),
              GHICHU: this.ghiChu,
            },
          ],
        })
          .then((response) => {
            if (
              response.data.error_code === "BSS-00000000"
            ) {
              this.$toast.success("Cập nhật thành công");
              this.getDSMaDoanhThu();
            }else{
              this.$toast.error("Cập nhật thất bại: "+response.data.data)
            }
          })
          .catch((error) => {
            console.log(error);
          })
          .finally(() => {});
      }
    },
    deleteRecord() {
      this.$alert("Bạn thật sự muốn xóa không ?", "Thông báo", {
        dangerouslyUseHTMLString: true,
        showCancelButton: true,
        confirmButtonText: "Đồng ý",
        cancelButtonText: "Hủy bỏ",
        showCloseButton: true,
        type: "warning",
      }).then(async (v) => {
        if (!v) {
          return;
        }
        API.fn_capnhat_madoanhthu(this.axios, {
          vkieu: 2,//xóa
          vmadt_id: this.madt_id,
          vdata: [
            {
              MADT_ID: this.madt_id,
              MADT_CHA_ID: this.madt_cha_id,
              MA_DT: this.maDoanhThu,
              TEN_CHITIEU: this.tenChiTieu,
              MUC: this.muc,
              NGAY_CN: moment(new Date()).format("DD/MM/YYYY"),
              GHICHU: this.ghiChu,
            },
          ],
        })
          .then((response) => {
            if (
              response.data.error_code === "BSS-00000000" &&
              response.data.data !== undefined &&
              response.data.data.length > 0
            ) {
              this.$toast.success("Xóa thành công");
              this.getDSMaDoanhThu();
            }else{
              this.$toast.error("Đã xảy ra lỗi: "+response.data.data)
            }
          })
          .catch((error) => {
            console.log(error);
          })
          .finally(() => {});
      }
      );
    },
    getDSMaDoanhThu() {
      API.sp_lay_ds_madoanhthu(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.lvwMaDoanhThu = response.data.data;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    lvwMaDoanhthu_SelectedIndexChanged(data) {
      if (Object.keys(data).length > 0) {
        this.maDoanhThu = data.ma_dt;
        this.tenChiTieu = data.ten_chitieu;
        this.muc = data.muc;
        this.ghiChu = data.ghichu;
        this.madt_id = data.madt_id;
        this.madt_cha_id = data.madt_cha_id;
      }
      console.log(JSON.stringify(data));
    },
  },
};
</script>
  