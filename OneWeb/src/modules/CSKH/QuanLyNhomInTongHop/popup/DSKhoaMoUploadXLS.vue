<template>
  <div class="modal-dialog" style="width: 100%; max-width: 1200px">
    <div class="modal-content popup-box">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" @click="importExcel">
              <span class="icon design_bullet-list-67 nc-icon-glyph"></span>
              Danh sách
            </a>
          </li>
          <li>
            <a href="#" @click="CapNhap">
              <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp
              nhận
            </a>
          </li>
          <li>
            <a href="#">
              <downloadexcel :data="this.dataExcel">
                <span class="icon one-excel"></span> Xuất Excel
              </downloadexcel>
            </a>
          </li>
        </ul>
        <div class="more">
          <a href="#">
            <span class="icon -ap icon-dots-three-vertical"></span>
          </a>
        </div>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="list-checks fw6 marb10">
            <div class="item">
              <span class="one-alert f24 text-warning inline vcenter marr7" ></span>
              <span class="text-main">File excel gồm 2 trường: </span>
            </div>
            <div class="item">
              <div class="check-action red">
                <input type="radio" checked v-model="loaiCot" value="1" />
                <span class="name">THUE BAO_ID, MA_TB</span>
              </div>
            </div>
            <div class="item">
              <div class="check-action red">
                <input type="radio" class="check" v-model="loaiCot" value="2" />
                <span class="name">MA_TB, LYDO</span>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key">Dịch vụ</div>
            <div class="value">
              <div class="select-custom">
                <b-form-select
                  v-model="loaiDanhMucVTSelected"
                  class="form-control"
                  :options="this.loaiDanhMucVTGetter"
                >
                </b-form-select>
                <!-- <select name= id= class="form-control">
                                <option value=>HPG-30-802626</option>
                            </select> -->
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key">Đường dẫn</div>
            <div class="value">
              <div class="input-more-button">
                <!-- <button class="btn">
                                <span class="-ap icon-more_horiz"></span>
                            </button> -->
                <label for="upload" class="btn">
                  <span class="-ap icon-more_horiz"></span>
                  <input
                    type="file"
                    id="upload"
                    style="display: none"
                    @change="onChangeFile"
                  />
                </label>
                <input
                  type="text"
                  class="form-control"
                  id="showFileNameUpload"
                  v-model="txtFileName"
                />
              </div>
            </div>
          </div>
          <div class="legend-title mart20">
            <div class="title pull-left">Danh sách thuê bao</div>
            <div class="pull-right fw6 red">
              <span class="inline">Thông báo: </span>
              <span class="inline marl20">00:00:00</span>
            </div>
            <div class="clearfix"></div>
          </div>
          <div class="table-content">
            <b-table
              class="table-result nowrap table-gachle"
              :fields="fields"
              :items="dsLoi"
              selectable
              select-mode="multi"
              @row-clicked="gridRowClicked"
            >
              <template #cell(Id)="{ rowSelected }">
                <template v-if="rowSelected">
                  <span aria-hidden="true" class="fa fa-play text-main"></span>
                </template>
                <template v-else>
                  <span aria-hidden="true">&nbsp;</span>
                </template>
              </template>
              <template v-slot:cell(isCheck)="row">
                <b-form-checkbox v-model="row.item.isCheck"></b-form-checkbox>
              </template>
            </b-table>
          </div>
          <div class="tc-pagination">
            <a href="#" class="btn one-arrow-left-forward"></a>
            <a href="#" class="btn fa fa-angle-double-left"></a>
            <a href="#" class="btn fa fa-angle-left"></a>
            <span class="text">Bản ghi</span>
            <div class="dropdown inline">
              <div class="fw6 input-number" data-toggle="dropdown">
                <span class="text-main">10</span>/1033
              </div>
              <div class="dropdown-menu">
                <a href="#" class="dropdown-item">10</a>
                <a href="#" class="dropdown-item">20</a>
              </div>
            </div>
            <a href="#" class="btn fa fa-angle-right"></a>
            <a href="#" class="btn fa fa-angle-double-right"></a>
            <a href="#" class="btn one-arrow-right-forward"></a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import xlsx from "xlsx";
import { mapActions, mapState, mapGetters } from "vuex";
import DanhSachThueBaoKhongKhaoSatApi from "../../api/DanhSachThueBaoKhongKhaoSatApi";
import downloadexcel from "vue-json-excel";
export default {
  name: "DSKhoaMoUploadXLS",
  props: ["loaiDs"],
  components: { downloadexcel },
  data() {
    return {
      loaiCot: 1,
      dsRow: [],
      items: [],
      loaiDanhMucVTSelected: "",
      dsLoi: [],
      files: [],
      txtFileName: "",
      fields: [
        {
          key: "Id",
          label: "",
          tdClass: "w20 center",
        },
        {
          key: "isCheck",
          label: "",
          tdClass: "w20 center",
        },
        {
          key: "Acc",
          label: "Số máy/Acc",
        },
        {
          key: "TrangThaiTB",
          label: "Trạng thái TB",
        },
        {
          key: "LyDo",
          label: "Lý do",
        },
        {
          key: "TRANGTHAI",
          label: "Trạng thái",
        },
      ],
    };
  },
  created() {},
  computed: {
    ...mapGetters("cskhCommon", ["loaiDanhMucVTGetter"]),
    dataExcel() {
      return Object.values(this.dsLoi);
    },
  },
  methods: {
    gridRowClicked(record, index) {
      record.isCheck = !record.isCheck;
    },
    countMaTB(maTB) {},
    onChangeFile(e) {
      console.log(e);
      this.files = e.target.files;
      this.txtFileName = e.target.files[0].name;
    },
    async CapNhap() {
      let dsCapNhap = this.dsLoi.filter((element) => element.isCheck == true);
      let check = true;
      if (dsCapNhap.length == 0) {
        alert("Bạn chưa chọn thuê bao.");
        return;
      }
      this.dsLoi.forEach((element) => {
          console.log('check', check)
        if (!check) return;
        if (element.TRANGTHAI != "OK" && element.isCheck) {
          check = false;
          alert("Trạng thái không thỏa mãn điều kiện!");
          return;
        }
      });
      let record = null;
      if (check) {
        this.dsLoi.forEach(async (element) => {
          if (element.isCheck) {
            record = {
              thueBaoId: element.THUEBAO_ID,
              thanhToanId: element.THANHTOAN_ID,
              dichVuVtId: element.DICHVUVT_ID,
              loaiTbId: 1,
              loaiDs: this.loaiDs,
              lyDo: element.LYDO,
              ghiChu: "Nạp file",
              nhanVienId: 44370,
              mayCn: "DungNLA",
              nguoiCn: "DungNLA",
              ipCn: "1.2.3.4",
            };
            let response =
              await DanhSachThueBaoKhongKhaoSatApi.capNhapThueBaoKhongKhoaMo(
                this.axios,
                record
              );
            if (response.data.error_code === "BSS-00000000")
              this.$root.toastSuccess("Cập nhật dữ liệu thành công!");
            else this.$root.toastError("Cập nhật dữ liệu thất bại!");
          }
        });
      }

      console.log(dsCapNhap);
    },
    async importExcel() {
      const files = this.files;
      console.log(files);
      if (!files.length) {
        return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return alert(
          "The upload format is incorrect. Please upload xls or xlsx format"
        );
      }
      const fileReader = new FileReader();
      fileReader.onload = async (ev) => {
        try {
          this.dsLoi = [];
          const data = ev.target.result;
          const XLSX = xlsx;
          const workbook = XLSX.read(data, {
            type: "binary",
          });
          const wsname = workbook.SheetNames[0];
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
          const excellist = [];
          let dsMaTB = [];
          let dsDuplicateMaTB = [];
          // Edit data
          for (var i = 0; i < ws.length; i++) {
            excellist.push(ws[i]);
            dsMaTB.push(ws[i].MA_TB);
          }

          var propCount = 0;
          if (excellist && excellist.length > 0) {
            for (var prop in excellist[0]) {
              if (excellist[0].hasOwnProperty(prop)) propCount++;
            }
          }

          if (propCount != 2) {
            alert(
              "Số lượng cột phải = 2 cột, hiện đang có " + propCount + " cột!"
            );
            return;
          }

          if (
            this.loaiCot == 1 &&
            !(
              excellist[0].hasOwnProperty("THUEBAO_ID") &&
              excellist[0].hasOwnProperty("MA_TB")
            )
          ) {
            alert("File cần có chính xác 2 cột THUEBAO_ID và MA_TB");
            return;
          } else if (
            this.loaiCot == 2 &&
            !(
              excellist[0].hasOwnProperty("THUEBAO_ID") &&
              excellist[0].hasOwnProperty("MA_TB")
            )
          ) {
            alert("File cần có chính xác 2 cột LYDO và MA_TB");
            return;
          }

          dsMaTB.forEach((element) => {
            if (
              dsMaTB.indexOf(element) != dsMaTB.lastIndexOf(element) &&
              !dsDuplicateMaTB.includes(element)
            )
              dsDuplicateMaTB.push(element);
          });

          dsDuplicateMaTB.forEach((element) => {
            this.dsLoi.push({
              Acc: element,
              TrangThaiTB: "",
              LyDo: "",
              TRANGTHAI: "Thuê bao bị lặp lại",
              isCheck: false,
            });
          });

          if (this.dsLoi.length > 0) return;

          let dsMaTTLoi = [];
          dsMaTTLoi = await DanhSachThueBaoKhongKhaoSatApi.getDSMaTTLoi(
            this.axios,
            {}
          );
          dsMaTTLoi.forEach((element) => {
            this.dsLoi.push({
              Acc: element.THUEBAO_ID,
              TrangThaiTB: "",
              LyDo: element.LYDO,
              TRANGTHAI: element.TRANGTHAI,
              isCheck: false,
            });
          });

          //console.log("Read results", excellist); // At this point, you get an array containing objects that need to be processed
        } catch (e) {
          return alert("Read failure!" + e);
        }
      };

      fileReader.readAsBinaryString(files[0]);
      var input = document.getElementById("upload");
    },
  },
};
</script>