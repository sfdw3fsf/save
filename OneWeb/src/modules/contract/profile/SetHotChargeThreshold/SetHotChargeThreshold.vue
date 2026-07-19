<template>
  <div>
    <vue-bread-crumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li @click="clickButton('nhapmoi')"
            :class="this.button.nhapmoi ? 'active' : 'non-active'" id="tsbtnNhapMoi">
          <a title="Nhập mới"> <span class="icon one-file-plus"></span>Nhập mới</a>
        </li>
        <li @click="clickButton('laytt')"
            :class="this.button.laytt ? 'active' : 'non-active'" id="btnLayTTMoi">
          <a title="Lấy TT"> <span class="icon one-file-attach"></span>Lấy TT</a>
        </li>
        <li @click="clickButton('capnhat')"
            :class="this.button.capnhat ? 'active' : 'non-active'" id="tsbtnChapNhan">
          <a title="Cập nhật"> <span class="icon one-reload1"></span>Cập nhật</a>
        </li>


        <li @click="clickButton('xoa')"
            :class="this.button.xoa ? 'active' : 'non-active'" id="tsbtnXoa">
          <a title="Xóa"> <span class="icon one-trash"></span>Xóa</a>
        </li>


        <li @click="clickButton('uploadfile')"
            :class="this.button.uploadfile ? 'active' : 'non-active'" id="tsbtnNhapExcel">
          <a title="Upload file"> <span class="icon one-upload"></span>Upload file</a>
        </li>

      </ul>
    </vue-nav>
    <div class="page-content">
      <div class="box-form">
        <div class="legend-title">Thông tin ngưỡng cước nóng</div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w130">Số máy</div>
              <div class="value">
                <input type="text" v-model="txtSoMay" class="form-control" id="txtSoMay" v-on:keyup="txtSoMay_KeyPress">
              </div>
            </div>
            <div class="info-row">
              <div class="key w130">Đối tượng</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt v-model="ddlDoituong" :options="this.options.doituong">
                    <option disabled value="0">Select one</option>
                  </SelectExt>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w130">Nhắn tin thông báo</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt v-model="ddlNhantin" :options="this.options.nhantin">
                    <option disabled value="0">Select one</option>
                  </SelectExt>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key">ID</div>
              <div class="value">
                <input type="text" v-model="txtID" class="form-control">
              </div>
            </div>
            <div class="info-row">
              <div class="key">Ngưỡng ĐK</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt v-model="ddlNguocCuocNong" :options="this.options.nguongcuocnong">
                    <option disabled value="0">Select one</option>
                  </SelectExt>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key">Tháng QL</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt v-model="ddlThangQL" :options="this.options.thangQL">
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
          <div class="pull-left mart7">
            Danh sách thuê bao gán ngưỡng cước nóng
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

    <!--modal upload cuoc nong-->
    <b-modal id="uploadfile">
      <template #modal-header="{ close }">
        <div class="title">
          <span class="icon nc-icon-outline ui-1_settings"></span>Upload file
        </div>
        <div @click="close"
             class="close -ap icon-close"
             data-dismiss="modal"></div>
      </template>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a @click="clickButton('douploadfile')" title="Tải lên">
              <span class="icon one-upload"></span>Tải lên
            </a>
          </li>

          <li>
            <a @click="clickButton('nhapmoiuploadfile')" title="Nhập mới">
              <span class="icon one-file-plus"></span>Nhập mới
            </a>
          </li>
          <!--<li>
            <a href="#">
              <span class="icon one-check"></span>Thực hiện
            </a>
          </li>-->
          <!--<li @click="clickButton('uploadfile')"
              :class="this.button.uploadfile ? 'active' : 'non-active'">
            <a> <span class="icon one-upload"></span>Upload file</a>
          </li>-->

        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form" id="div_chonfileupload">
          <div class="legend-title">Chọn file để upload</div>
          <div class="row">


            <div class="col-sm-8 col-12">
              <div class="info-row">
                <div class="key w80">Đường dẫn</div>
                <div class="value">
                  <div class="input-more-button -right">
                    <label class="btn" for="upload-file">
                      <span class="-ap icon-more_horiz"></span>
                      <input type="file" ref="files" class="hidden" id="upload-file" @change="importExcel">
                    </label>
                    <input type="text" value="Chọn file" class="form-control highlight" v-model="file_up_load">
                  </div>
                </div>
              </div>
            </div>

            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w-auto">Tổng số thuê bao Upload:</div>
                <div class="value w40 tright padt7">
                  <label id="lbTongTBUpload" for="html">0</label>
                </div>
              </div>
            </div>

          </div>
          <div class="row">
            <div class="col-sm-8 col-12">
              <div class="info-row">
                <div class="key w130">Kiểu upload</div>
                <div class="value">
                  <div class="select-custom">
                    <SelectExt v-model="ddlKieuUpload" :options="this.options.kieuupload">
                      <option disabled value="0">Select one</option>
                    </SelectExt>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w-auto">Số thuê bao thành công:</div>
                <div class="value w40 tright text-main">
                  <label id="lbSoTBUploadThanhCong" for="html">0</label>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-8 col-12">
              <div class="info-row">
                <div class="key w130">Nhắn tin thông báo</div>
                <div class="value">
                  <div class="select-custom">
                    <SelectExt v-model="ddlNhanTinTT" :options="this.options.nhantintt">
                      <option disabled value="0">Select one</option>
                    </SelectExt>
                  </div>
                </div>

              </div>

            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w-auto">Số thuê bao lỗi:</div>
                <div class="value w40 tright red">
                  <label id="lbSoTBUploadLoi" for="html">0</label>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title red">Số thuê bao lỗi:</div>
          <div class="article">
            <ul class="list">
              <li>File Upload phải là file *.xls* hoặc *.xlsx*</li>
              <li>File gồm có 3 trường: SO_TB, ID_NGUONG_CUOCNONG, SO_THANG_QL</li>
              <li>Số thuê bao: đang hoạt động và tồn tại trong danh bạ</li>
              <li>ID ngưỡng cước nóng: 1-> 500.000Đ; 2-> 800.000Đ; 3-> 1.000.000Đ</li>
              <li>Tháng quản lý: Số tháng quản lý khách hàng cước nóng (3,6 hoặc 9)</li>
              <li>VD: Để upload gán ngước 800.000Đ cho thuê bao 84912345678 quản lý trong 6 tháng thì cấu trúc upload: 84912345678.2.6</li>
            </ul>
          </div>
        </div>



        <div class="nav tabs tab-over">
          <a href="javascript:void(0);" class="active">Danh sách thuê bao không thành công</a>
        </div>
        <div class="table-content">
          <DataGrid :columns="this.options.cols_grid_upload_loi"
                    v-bind:dataSource="this.options.danhsach_grid_upload_loi"
                    :showColumnCheckbox="false"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :enabledSelectFirstRow="true"
                    @rowSelected="rowSelected">
          </DataGrid>
        </div>
      </div>
    </b-modal>
  </div>
</template>
<script>
  import VueInput from "../../../search/subscriber/components/form/VueInput.vue";
  import api from "./api";
  import define from "./define";
  import VueCheckbox from '../../../search/subscriber/components/form/VueCheckbox.vue';
  import XLSX from "xlsx";

  import token_var from '../../../../plugins/token.js';
  import moment from "moment";
  export default {
    components: { VueInput, VueCheckbox },

    data() {
      return {
        ...define,
        file_up_load: "",
        options: {
          doituong: [],
          nhantin: [],
          nguongcuocnong: [],
          thangQL: [],
          danhsach_grid: [],

          cols_grid: [
            {
              fieldName: "ROW_STT",
              headerText: "STT",
              allowFiltering: true,
              textAlign: "center",
              allowSorting: true,
              width: "70"
            },
            {
              fieldName: "ID",
              headerText: "ID",
              allowFiltering: true,
              textAlign: "center",
              allowSorting: true,
              width: "140"
            },
            {
              fieldName: "MSISDN",
              headerText: "Mã TB",
              allowFiltering: true,
              textAlign: "center",
              allowSorting: true,
            },
            {
              fieldName: "BL_NAME",
              headerText: "Ngưỡng",
              allowFiltering: true,
              textAlign: "center",
              allowSorting: true,
              width: "140"
            },
            {
              fieldName: "CREATE_DATE",
              headerText: "Ngày ĐK",
              allowFiltering: true,
              textAlign: "center",
              allowSorting: true,
              width: "140"
            },
            {
              fieldName: "CREATE_BY",
              headerText: "Người ĐK",
              allowFiltering: true,
              textAlign: "center",
              allowSorting: true,
            },
            {
              fieldName: "UPDATE_DATE",
              headerText: "Ngày CN",
              allowFiltering: true,
              textAlign: "center",
              allowSorting: true,
            },
            {
              fieldName: "MONTH_LIVE",
              headerText: "Tháng QL",
              allowFiltering: true,
              textAlign: "center",
              allowSorting: true,
              width: "140"
            }
          ],

          kieuupload: [],
          nhantintt: [],

          danhsach_grid_upload: {},
          danhsach_grid_upload_loi: [],
          cols_grid_upload_loi: [
            {
              fieldName: "SO_TB",
              headerText: "Số máy",
              allowFiltering: true,
              textAlign: "center",
              allowSorting: true,
              width: "70"
            },
            {
              fieldName: "ERRMSG",
              headerText: "Kết quả trả về",
              allowFiltering: true,
              textAlign: "center",
              allowSorting: true,
              width: "140"
            }
          ]

        },
        txtSoMay: "",
        txtID: "",
        ddlDoituong: "",
        ddlNhantin: "",
        ddlNguocCuocNong: "",
        ddlThangQL: "",
        maTinh: "",
        userId: "",
        user_neo: "",
        curDate: moment(new Date()).format("DD/MM/YYYY"),

        ddlKieuUpload: "",
        ddlNhanTinTT: "",

        count_all_upload: 0,



      };
    },

    created() {
      
    },

    mounted() {
      this.form_load()
    },

    methods: {
      txtSoMay_KeyPress: async function (event) {
        this.loading(true)

        try {
          if (event.key == "Enter")
            this.HienThiDanhSach()
        }
        catch (err) {
          this.$toast.error(err.message);
        }

        this.loading(false)
      },

      async layThongTinNguoiDung() {
        try {
          let rs = await this.$root.context.get("/web-ccdv/hoanthien-hs-tachnhap-tbao/thongtin_nguoidung")
          return !rs.data ? null : rs.data[0]
        } catch (error) {
          return null
        }
      },

      async form_load() {
        try {
          this.loading(true)

          this.Refresh()
          this.tsbtnNhapMoi_Click()

          this.userId = token_var.getNguoiDungID()
          let userInfo = await this.layThongTinNguoiDung()
          this.user_neo = userInfo.user_neo

          this.loading(false)
        }
        catch (err) {
          this.$toast.error(err.message);
        }
      },

      Refresh() {
        this.getDoiTuong()
        this.getNhanTin()
        this.getComboNguongCuocNong()
        this.getComboThangQL()
        this.maTinh = token_var.getMaTinh()
      },

      tsbtnNhapMoi_Click() {
        this.SetButton(1)
        this.Refresh()
      },

      getDoiTuong() {
        let items = [];
        items.push({ id: 1, text: "Thuê bao thường" });
        this.options.doituong = items;
        this.doituong = items[0].id;
        this.ddlDoituong = items[0].id;
      },

      getNhanTin() {
        let items = [];
        items.push({ id: 2, text: "Không chọn" });
        items.push({ id: 1, text: "Nhắn tin" });
        items.push({ id: 0, text: "Không nhắn tin" });
        this.options.nhantin = items;
        this.nhantin = items[0].id;
        this.ddlNhantin = items[0].id;
      },

      async getComboNguongCuocNong() {
        let items = [];
        const files = await this.axios.get("ccbs/executor/ts_laphd_hoamangmoi_layds_nguong_cn", {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        await Promise.all(files.data.data.map(async (file) => {
          items.push({ id: file.BL_ID, text: file.BL_NAME });
        }));
        this.options.nguongcuocnong = items;
        this.ddlNguocCuocNong = items[0].id;
      },

      async getComboThangQL() {
        let items = [];
        const files = await this.axios.get("ccbs/executor/ts_laphd_hoamangmoi_layds_nguong_cn", {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        await Promise.all(files.data.data.map(async (file) => {
          items.push({ id: file.MONTH_ID, text: file.MONTH_NAME });
        }));
        this.options.thangQL = items;
        this.ddlThangQL = items[0].id;
      },

      getNhanTinTT() {
        let items = [];
        items.push({ id: 1, text: "Nhắn tin thông báo ngưỡng đã gán cho KH" });
        items.push({ id: 0, text: "Không khắn tin thông báo ngưỡng đã gán cho KH" });
        this.options.nhantintt = items;
        this.nhantintt = items[0].id;
        this.ddlNhanTinTT = items[0].id;
      },

      async getComboKieuUpload() {
        let items = [];
        const files = await this.axios.get("ccbs/executor/get_kieuupload_gannguongcn", {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        await Promise.all(files.data.data.map(async (file) => {
          //console.log(file);
          items.push({ id: file.UPLOAD_TYPE, text: file.NAME });
        }));
        this.options.kieuupload = items;
        this.ddlKieuUpload = items[0].id;
      },

      async HienThiDanhSach() {
        this.loading(true);
        try {
          if (this.user_neo == "") {
            this.$toast.error("Thuê bao chưa được gán quyền truy cập Ccbs.")
            return;
          }

          var json_data_ts_nguong_cuocnong_ds = JSON.stringify({
            "trangthai": "",
            "nguoi_capnhat": "",
            "ma_tinh": "",
            "subject": "",
            "bl_id": "",
            "ip": "",
            "userid": "",
            "sms_canhbao": "",
            "ngay_capnhat": "",
            "ngaytao": "",
            "thang": "",
            "so_tb": this.txtSoMay,
            "nguoitao": "",
            "page": ""
          });
          var link_ts_nguong_cuocnong_ds = "/ccbs/executor/ts_nguong_cuocnong_ds"
          const files_ts_nguong_cuocnong_ds = await this.axios.post(link_ts_nguong_cuocnong_ds, json_data_ts_nguong_cuocnong_ds, {
            headers: {
              'Content-Type': 'application/json'
            }
          });

          if (files_ts_nguong_cuocnong_ds.data.error_code != 'BSS-00000000' && files_ts_nguong_cuocnong_ds.data.message != "NO_DATA") {
            this.SetButton(1)
            this.Refresh()
            this.$toast.error('Lấy dữ liệu lỗi, chi tiết: ' + files_ts_nguong_cuocnong_ds.data.data)
            this.loading(false)
            return;
          }

          this.options.danhsach_grid = files_ts_nguong_cuocnong_ds.data.data ? files_ts_nguong_cuocnong_ds.data.data : [];
          if (this.options.danhsach_grid.length < 1) {
            this.$toast.error("Không có dữ liệu hiển thị!");
          }
        }
        catch (err) {
          this.$toast.error("Lỗi khi lấy danh sách thuê bao gán ngưỡng cước nóng !" + err.message)
        }
        this.loading(false);
      },

      async btnLayTTMoi_Click() {
        this.HienThiDanhSach()
      },

      rowSelected(args) {
        try {
          this.txtSoMay = args.data.MSISDN
          this.txtID = args.data.ID
          this.ddlDoituong = args.data.SUBJECT
          this.ddlNhantin = args.data.SMS_NOTICE
          this.ddlNguocCuocNong = args.data.BL_ID
          this.ddlThangQL = args.data.MONTH_LIVE
          this.SetButton(3)
        }
        catch (err) {
          this.$toast.error("Có lỗi khi hiển thị thông tin !" + err.message)
        }
      },

      clear() {
        this.txtSoMay = ''
        this.txtID = ''
      },

      KiemTraDuLieuNhap() {
        if (this.txtSoMay == "") {
          this.$toast.error("Bạn chưa nhập số máy !")
          return false
        }
        //if (this.txtSoMay.trim().length != 9) {
        //  this.$toast.error("Số máy gán ngưỡng cước nóng không chính xác !")
        //  return false
        //}
        if (this.ddlNguocCuocNong == "0") {
          this.$toast.error("Hãy chọn ngưỡng cước nóng!")
          return false
        }
        if (this.ddlThangQL == "0") {
          this.$toast.error("Hãy chọn tháng gán ngưỡng!")
          return false
        }
        if (this.ddlNhantin == "" || this.ddlNhantin == "2") {
          this.$toast.error("Hãy chọn combo tin nhắn!")
          return false
        }
        return true;
      },

      async tsbtnChapNhan_Click() {
        this.loading(true)
        try {
          if (this.user_neo == "") {
            this.$toast.error("Thuê bao chưa được gán quyền truy cập Ccbs.")
            return
          }

          if (this.KiemTraDuLieuNhap() == false) {
            this.loading(false)
            return
          }

          var json_data_ts_nguong_cuocnong_ds = JSON.stringify({
            "trangthai": "",
            "nguoi_capnhat": "",
            "ma_tinh": "",
            "subject": "",
            "bl_id": "",
            "ip": "",
            "userid": "",
            "sms_canhbao": "",
            "ngay_capnhat": "",
            "ngaytao": "",
            "thang": "",
            "so_tb": this.txtSoMay,
            "nguoitao": "",
            "page": ""
          });
          var link_ts_nguong_cuocnong_ds = "/ccbs/executor/ts_nguong_cuocnong_ds"
          const files_ts_nguong_cuocnong_ds = await this.axios.post(link_ts_nguong_cuocnong_ds, json_data_ts_nguong_cuocnong_ds, {
            headers: {
              'Content-Type': 'application/json'
            }
          })
          if (files_ts_nguong_cuocnong_ds.data.message == 'NO_DATA') {
            this.$confirm(`Bạn thật sự muốn thêm dữ liệu không ?`, {
              confirmButtonText: "Có",
              cancelButtonText: "Không",
              type: "warning",
            }).then(async () => {
              await this.insertData();
            })
          }
          else {
            this.$confirm(`Bạn thật sự muốn cập nhật dữ liệu không ?`, {
              confirmButtonText: "Có",
              cancelButtonText: "Không",
              type: "warning",
            }).then(async () => {
              await this.updateData();
            })
          }
          this.SetButton(3)
        }
        catch (err) {
          this.$toast.error("Lỗi khi cập nhật thông tin gán ngưỡng cước nóng !" + err.message)
        }
        this.loading(false)

      },

      async tsbtnXoa_Click() {
        this.loading(true);

        try {
          var json_data = JSON.stringify({
            userip: "",
            id: this.txtID,
            userid: this.userId
          });
          const res = await this.axios.post("/ccbs/executor/ts_nguong_cuocnong_delete", json_data, {
            headers: {
              'Content-Type': 'application/json'
            }
          });
          var mes = res.data.error_code ? res.data.error_code : [];
          console.log(mes);
          if (mes == 'BSS-00000000') {
            this.clear()
            this.Refresh()
            this.HienThiDanhSach()
            this.$toast.success('Xóa dữ liệu thành công"!')
          } else {
            this.$toast.error('Xóa dữ liệu lỗi, chi tiết: ' + res.data.data);
          }

        }
        catch (err) {
          this.$toast.error("Lỗi khi xóa gán ngưỡng cước nóng !" + err.message)
        }

        this.loading(false);
      },

      async clickButton(key) {
        if (key == "nhapmoi") {
          this.tsbtnNhapMoi_Click()
        }

        if (key == "laytt") {
          this.btnLayTTMoi_Click()
        }

        if (key == "capnhat") {
          this.tsbtnChapNhan_Click()
        }

        if (key == "xoa") {
          if (this.user_neo == "") {
            this.$toast.error("Thuê bao chưa được gán quyền truy cập Ccbs.")
            return;
          }
          this.$confirm(`Bạn thật sự muốn xóa dữ liệu không ?`, {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
            type: "warning",
          }).then(async () => {
            this.tsbtnXoa_Click()
          })
        }

        if (key == "uploadfile") {
          this.tsbtnNhapExcel_Click()
        }

        if (key == "douploadfile") {
          if (this.options.danhsach_grid_upload.hotBillSub.length == 0)
            this.$toast.error("Chưa có dữ liệu upload!")
          else
            this.uploadData(this.options.danhsach_grid_upload);
        }

        if (key == "nhapmoiuploadfile") {
          this.enableElement("div_chonfileupload")
          this.clearUpload()
        }
      },

      async tsbtnNhapExcel_Click() {
        try {
          this.$bvModal.show("uploadfile")
          this.getNhanTinTT()
          this.getComboKieuUpload()
        }
        catch (err) {
          this.$toast.error("Lỗi khi hiển thị chức năng upload ngưỡng cước nóng" + err.message)
        }
      },

      async insertData() {
        var json_data_ts_nguong_cuocnong_insert = JSON.stringify({
          "ngay_capnhat": this.curDate,
          "ngaytao": this.curDate,
          "nguoi_capnhat": this.userId,
          "ma_tinh": this.maTinh,
          "thang": this.ddlThangQL,
          "subject": this.ddlDoituong,
          "bl_id": this.ddlNguocCuocNong,
          "ip": "",
          "so_tb": this.txtSoMay,
          "nguoitao": this.userId,
          "userid": this.userId,
          "sms_canhbao": this.ddlNhantin == 2 ? "" : this.ddlNhantin
        });
        var link_ts_nguong_cuocnong_insert = "/ccbs/executor/ts_nguong_cuocnong_insert"
        const files_ts_nguong_cuocnong_insert = await this.axios.post(link_ts_nguong_cuocnong_insert, json_data_ts_nguong_cuocnong_insert, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        var mes = files_ts_nguong_cuocnong_insert.data.error_code ? files_ts_nguong_cuocnong_insert.data.error_code : [];
        if (mes == 'BSS-00000000') {
          this.HienThiDanhSach()
          this.$toast.success('Thêm dữ liệu thành công"!');
        } else {
          this.$toast.error('Thêm dữ liệu lỗi, chi tiết: ' + files_ts_nguong_cuocnong_insert.data.message_detail);
        }
      },

      async updateData() {
        var json_data_ts_nguong_cuocnong_update = JSON.stringify({
          "nguoi_capnhat": this.userId,
          "ma_tinh": this.maTinh,
          "subject": this.ddlDoituong,
          "bl_id": this.ddlNguocCuocNong,
          "ip": "",
          "userid": this.userId,
          "sms_canhbao": this.ddlNhantin == 2 ? "" : this.ddlNhantin,
          "ngay_capnhat": this.curDate,
          "ngaytao": "",
          "thang": this.ddlThangQL,
          "so_tb": this.txtSoMay,
          "id": this.txtID,
          "nguoitao": ""
        });
        var link_ts_nguong_cuocnong_update = "/ccbs/executor/ts_nguong_cuocnong_update"
        const files_ts_nguong_cuocnong_update = await this.axios.post(link_ts_nguong_cuocnong_update, json_data_ts_nguong_cuocnong_update, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        var mes = files_ts_nguong_cuocnong_update.data.error_code ? files_ts_nguong_cuocnong_update.data.error_code : [];
        if (mes == 'BSS-00000000') {
          this.HienThiDanhSach()
          this.$toast.success('Cập nhật dữ liệu thành công"!');
        } else {
          this.$toast.error('Cập nhật dữ liệu lỗi, chi tiết: ' + files_ts_nguong_cuocnong_update.data.message_detail);
        }
      },

      disableElement(eleID) {
        var nodes = document.getElementById(eleID).getElementsByTagName('*');
        for (var i = 0; i < nodes.length; i++) {
          nodes[i].disabled = true;
        }
      },

      enableElement(eleID) {
        var nodes = document.getElementById(eleID).getElementsByTagName('*');
        for (var i = 0; i < nodes.length; i++) {
          nodes[i].disabled = false;
        }
      },

      clearUpload() {
        this.count_all_upload = 0;
        document.getElementById('lbTongTBUpload').innerHTML = "0";
        document.getElementById('lbSoTBUploadThanhCong').innerHTML = "0";
        document.getElementById('lbSoTBUploadLoi').innerHTML = "0";
        document.getElementById('upload-file').value = "";
        this.options.danhsach_grid_upload_loi = []; // Clear received data
        this.options.danhsach_grid_upload = {}; // Clear received data
      },

      async uploadData(json_data) {
        this.loading(true);

        const res = await this.axios.post("/ccbs/billing/ts_upload_nguong_cuocnong", json_data, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        this.options.danhsach_grid_upload_loi = res.data.data ? res.data.data : [];
        document.getElementById('lbSoTBUploadThanhCong').innerHTML = this.count_all_upload - res.data.data.length;
        document.getElementById('lbSoTBUploadLoi').innerHTML = res.data.data.length;
        this.disableElement("div_chonfileupload");
        this.loading(false);
      },

      async importExcel(e) {
        const files = e.target.files;
        if (!files.length) {
          return;
        } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
          return alert("File upload chưa đúng định dạng (.xls/.xlsx)");
        }
        this.file_up_load = files[0].name;
        const fileReader = new FileReader();
        fileReader.onload = ev => {
          try {
            const data = ev.target.result;
            const workbook = XLSX.read(data, {
              type: "binary"
            });
            const wsname = workbook.SheetNames[0]; // Take the first sheet，wb.SheetNames[0] :Take the name of the first sheet in the sheets
            const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
            // Generate JSON table content，wb.Sheets[Sheet名]    Get the data of the first sheet
            const excellist = []; //console.log(ws[0]);
            this.options.danhsach_grid_upload_loi = []; // Clear received data
            this.options.danhsach_grid_upload = {}; // Clear received data
            // Edit data + //Kiểm tra dữ liệu null

            let keys = Object.keys(ws[0]);
            if (keys[0] != 'SO_TB' || keys[1] != 'ID_NGUONG_CUOCNONG' || keys[2] != 'SO_THANG_QL') {
              this.options.danhsach_grid_upload = [];
              this.options.danhsach_grid_upload_loi = [];
              this.$toast.error("Tên cột trong file không đúng định dạng: SO_TB, ID_NGUONG_CUOCNONG, SO_THANG_QL");
              return;
            };

            if (ws.length == 0) {
              this.$toast.error("File không có dữ liệu!");
              return;
            }

            var data_check = true;
            var hotBillSub = []
            this.options.danhsach_grid_upload.hotBillSub = hotBillSub;

            for (var i = 0; i < ws.length; i++) {
              excellist.push(ws[i]);
              if (String(ws[i].SO_THANG_QL).trim() == "undefined") {
                this.$toast.error("Chưa có Số tháng QL tại dòng " + String(i + 1));
                data_check = false;
                continue;
              }
              if (String(ws[i].ID_NGUONG_CUOCNONG).trim() == "undefined") {
                this.$toast.error("Chưa có ID ngưỡng cước nóng tại dòng " + String(i + 1));
                data_check = false;
                continue;
              }
              if (String(ws[i].SO_TB).trim() == "undefined") {
                this.$toast.error("Chưa có Số thuê bao tại dòng " + String(i + 1));
                data_check = false;
                continue;
              }

              this.options.danhsach_grid_upload.hotBillSub.push(
                {
                  thang: ws[i].SO_THANG_QL,
                  bl_id: ws[i].ID_NGUONG_CUOCNONG,
                  so_tb: ws[i].SO_TB,
                  sms_canhbao: this.ddlNhanTinTT == 2 ? "" : this.ddlNhanTinTT
                }
              );
              this.count_all_upload = this.count_all_upload + 1;
            }
            if (data_check == false)
              return;

            var upload_type = this.ddlKieuUpload;
            this.options.danhsach_grid_upload.upload_type = upload_type;
            document.getElementById('lbTongTBUpload').innerHTML = this.count_all_upload;

          } catch (e) {
            return alert(e);
          }
        };
        fileReader.readAsBinaryString(files[0]);
        var input = document.getElementById("upload-file");
        input.value = ""
      },

      SetButton(kieu) {
        this.disableButton("tsbtnNhapMoi")
        this.disableButton("tsbtnChapNhan")
        this.disableButton("tsbtnXoa")


        if (kieu == -1)//Bat dau
        {
          document.getElementById("txtSoMay").focus()
          this.enableButton("tsbtnChapNhan")
        }

        if (kieu == 0)//Bat dau
        {
          document.getElementById("txtSoMay").focus();
          this.enableButton("tsbtnNhapMoi")
          this.clear();
        }

        if (kieu == 1)//Them moi
        {
          document.getElementById("txtSoMay").focus()
          this.enableButton("tsbtnChapNhan")
          this.clear();
        }

        if (kieu == 2)//Huy
        {
          document.getElementById("txtSoMay").focus()
          this.enableButton("tsbtnNhapMoi")
          this.enableButton("tsbtnXoa")
          this.clear();
        }

        if (kieu == 3)//Edit
        {
          this.enableButton("txtSoMay")
          this.enableButton("tsbtnNhapMoi")
          this.enableButton("tsbtnXoa")
          this.enableButton("tsbtnChapNhan")
        }
      },

      enableButton(ele) {
        let control_name = document.getElementById(ele)
        if (control_name != null) {
          if (control_name.classList.contains('non-active')) {
            control_name.classList.remove("non-active");
            control_name.classList.add("active");
          }
          document.getElementById(ele).style.pointerEvents = "auto"
        }
      },

      disableButton(ele) {
        let control_name = document.getElementById(ele)
        if (control_name != null) {
          if (control_name.classList.contains('active')) {
            control_name.classList.remove("active");
            control_name.classList.add("non-active");
          }
          document.getElementById(ele).style.pointerEvents = "none"
        }
      },

    }
  };

</script>
<style>
  .none-padding {
    padding: 0 !important;
  }

  .modal-dialog {
    border-radius: 0px !important;
    width: 100% !important;
    max-width: 850px !important;
  }

  li.non-active a {
    color: #d3d3d3 !important;
  }
</style>
