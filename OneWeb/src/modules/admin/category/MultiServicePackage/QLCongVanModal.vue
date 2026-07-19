<template>
  <b-modal
    ref="popupQLCongVan"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Quản lý công văn các gói đa
          dịch vụ
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>

      <ActionTop :actions="actions" @onActionClick="onActionClick" />

      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin công văn gói đa dịch vụ</div>

          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100">Ngày thực hiện</div>
                <div class="value">
                  <div class="input-icon-right">
                    <k-date-picker
                      v-model="ngay_th"
                      format="DD/MM/YYYY HH:mm:ss"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w80">IP Server</div>
                <div class="value">
                  <input type="text" v-model="ip_server" class="form-control" />
                </div>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w100">Đường dẫn</div>
            <div class="value">
              <div class="input-more-button -right">
                <label class="btn" for="upload-file">
                  <span class="-ap icon-more_horiz"></span>
                  <input
                    type="file"
                    ref="files"
                    class="hidden"
                    id="upload-file"
                    @change="UploadFileHoSo"
                  />
                </label>
                <input
                  type="text"
                  class="form-control highlight"
                  v-model="server_url"
                />
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách công văn</div>
          <!-- <k-table
            :columns="columns"
            :dataSources="dataSources"
            @onSelectedRow="onSelectedRow"
            :allowFilter="true"
            :rowSelectedIndex="1"
          /> -->
          <DataGrid
            v-bind:columns="ds_congvan.header"
            v-bind:dataSource="ds_congvan.list"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            :showColumnCheckbox="false"
            @selectedRowChanged="onSelectedRow"
          />
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import KTable from "@/components/kylq_components/KTable.vue";
import ActionTop from "@/components/kylq_components/ActionTop.vue";
import KDatePicker from "@/components/kylq_components/KDatePicker.vue";
import moment from "moment";
import MultiServicePackageAPI from "./MultiServicePackageAPI";
import UploadEProfileAPI from "@/modules/contract/profile/UploadEProfile/UploadEProfileAPI.js";
export default {
  name: "QLCongVanModal",
  props: {
    goi_id: {
      type: Number,
      default: 0,
    },
  },
  components: {
    KTable,
    ActionTop,
    KDatePicker,
  },
  data() {
    return {
      animationSettings: {
        effect: "Zoom",
      },
      position: { X: "center", Y: "top" },
      actions: [
        {
          id: "nhapmoi",
          name: "Nhập mới",
          active: true,
          icon_class: "one-file-plus",
        },
        {
          id: "ghilai",
          name: "Ghi lại",
          active: true,
          icon_class: "one-save",
        },
      ],
      ngay_th: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
      l_goi_id: this.goi_id,
      file: null,
      server_url: "",
      ip_server: "",
      client_url: "",
      rowSelected: null,
      ds_congvan: {
        list: [],
        header: [
          {
            fieldName: "cv_id",
            headerText: "STT",
            allowFiltering: true,
          },
          {
            fieldName: "ten_file",
            headerText: "Tên file",
            allowFiltering: true,
          },
          {
            fieldName: "ngay_ht",
            headerText: "Ngày thực hiện",
            textAlign: "right",
            allowFiltering: true,
          },
          {
            fieldName: "server_url",
            headerText: "Đường dẫn Server",
            allowFiltering: true,
            allowHtml: true,
          },
          {
            fieldName: "server_url",
            headerText: "Xem/Tải về",
            allowFiltering: true,
            allowHtml: true,
            textAlign: "center",
            template:
              "<a rel='nofollow' target='_blank' href='${server_url}'>Tải về</a>",
          },
        ],
        value: {},
        isEnabled: false,
      },
    };
  },
  methods: {
    onActionClick(action) {
      if (action.id == "nhapmoi") {
        this.setActiveActions(1);
      } else if (action.id == "ghilai") {
        this.update_congvan();
      }
    },
    setActiveActions(kieu) {
      this.actions.forEach((item) => {
        item.active = false;
      });
      if (kieu == -1) {
        //bắt đầu
        this.actions[
          this.actions.findIndex((x) => x.id == "ghilai")
        ].active = true;
      } else if (kieu == 0) {
        //bắt đầu
        this.actions[
          this.actions.findIndex((x) => x.id == "nhapmoi")
        ].active = true;
        this.clear();
      } else if (kieu == 1) {
        //thêm mới
        this.actions[
          this.actions.findIndex((x) => x.id == "ghilai")
        ].active = true;
        this.clear();
      } else if (kieu == 2) {
        //Huỷ
        this.actions[
          this.actions.findIndex((x) => x.id == "nhapmoi")
        ].active = true;
        this.clear();
      } else if (kieu == 3) {
        //edit
        this.actions[
          this.actions.findIndex((x) => x.id == "nhapmoi")
        ].active = true;
        this.actions[
          this.actions.findIndex((x) => x.id == "ghilai")
        ].active = true;
      }
    },
    clear() {
      this.file = null;
      this.server_url = "";
      this.ngay_th = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
    },
    showModal() {
      this.$refs["popupQLCongVan"].show();
    },
    hideModal() {
      this.pdf = null;
      this.$refs["popupQLCongVan"].hide();
    },
    handleShowModal() {
      this.setActiveActions(-1);
      this.ds_congvan.list = [];
      this.ngay_th = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
      (this.file = null), (this.server_url = "");
      this.client_url = "";
      this.ip_server = "";

      this.sp_grv_congvan(this.l_goi_id);
    },
    getkey: async function (key) {
      var input = {
        keyname: key,
      };
      let response = await MultiServicePackageAPI.getKey(this.axios, input);
      if (
        response &&
        response.data &&
        response.data.error_code &&
        response.data.error_code == "BSS-00000000" &&
        response.data.data
      ) {
        return Number(response.data.data);
      } else {
        this.$toast.error("Có lỗi trong quá trình tạo key mới");
        return;
      }
    },
    // chooseFile() {
    //   this.$refs.fileInput.value = null;
    //   document.getElementById("file").click();
    // },
    // onChangeFile(event) {
    //   this.server_url = event.target.value;
    //   this.file = event.target.files[0];
    //   //upload file
    // },
    UploadFileHoSo(event) {
      this.client_url = event.target.value.toString();
      let formData = new FormData();
      for (var file of this.$refs.files.files) {
        formData.append("files", file);
      }
      this.loading(true);
      UploadEProfileAPI.upload_file_hoso(this.axios, formData, {
        headers: { "Content-Type": "multipart/form-data" },
      })
        .then((response) => {
          this.loading(false);
          if (
            response.data.error_code &&
            response.data.error_code == "BSS-00000000"
          ) {
            this.server_url = response.data.data.join("|");
            var tmp = this.server_url.split("/");
            this.file = tmp.length > 0 ? tmp[tmp.length - 1] : "";
          }
        })
        .catch(function () {
          console.log("Upload file " + this.file + " không thành công.");
        });
    },
    update_congvan: async function () {
      try {
        var themmoi =
          this.actions[this.actions.findIndex((x) => x.id == "nhapmoi")]
            .active == false
            ? true
            : false;
        if (!this.kiemtra_dulieu(themmoi)) return;
        var goi_id = this.l_goi_id;
        var cv_id = themmoi ? 0 : this.rowSelected.cv_id;
        var tenfile = this.file;
        var congvan = [
          {
            GOI_ID: goi_id,
            CV_ID: cv_id,
            TEN_FILE: this.file,
            SERVER_URL: this.server_url,
            IP_SERVER: this.ip_server,
            CLIENT_URL: this.client_url,
            NGAY_HT: this.ngay_th,
            MAY_CN: await this.$root.token.getMachine(),
            NGAY_CN: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
            NGUOI_CN: await this.$root.token.getUserName(),
          },
        ];
        console.log(congvan);
        var json_congvan = JSON.stringify(congvan);
        console.log(json_congvan);
        let response = await MultiServicePackageAPI.sp_upd_congvan(
          this.axios,
          goi_id,
          tenfile,
          json_congvan
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          this.$toast.success("Cập nhật dữ liệu thành công");
          this.setActiveActions(3);
        } else {
          this.$toast.error(
            "Lỗi cập nhật dữ liệu " + response.data.message_detail
          );
        }
      } catch (e) {
        if (e.data && e.data.message_detail)
          this.$toast.error("Lỗi cập nhật dữ liệu " + e.data.message_detail);
        else this.$toast.error("Lỗi cập nhật dữ liệu " + e);
      }
    },
    kiemtra_dulieu: function (themmoi) {
      if (themmoi) {
        if (!this.server_url || this.server_url == "") {
          this.$toast.error(
            "Bạn chưa chọn đường dẫn đến file công văn gói tích hợp !"
          );
          return false;
        }
        if (!this.file || this.file == "") {
          this.$toast.error("File không tồn tại hoặc đã bị xóa !");
          this.setActiveActions(3);
          return false;
        }
        return true;
      }
    },
    onSelectedRow(item) {
      //show thông tin
      if (item && Object.keys(item).length > 0) {
        this.rowSelected = item;
        this.server_url = item.server_url;
        this.ngay_th = moment(item.ngay_ht).format("DD/MM/YYYY HH:mm:ss");
        this.ip_server = item.ip_server;
        this.setActiveActions(3);
      }
    },
    async sp_grv_congvan(goi_id) {
      try {
        this.loading(true);
        this.ds_congvan.list = [];
        let response = await MultiServicePackageAPI.sp_grv_congvan(
          this.axios,
          goi_id
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          if (response.data.data.length > 0) {
            this.ds_congvan.list = response.data.data;
          } else {
            this.setActiveActions(1);
          }
        } else {
          this.ds_congvan.list = [];
          this.setActiveActions(1);
        }
      } catch (e) {
        this.$toast.error("Không load được danh công văn");
      } finally {
        this.loading(false);
      }
    },
  },
  watch: {
    goi_id(val) {
      this.l_goi_id = val;
    },
  },
};
</script>