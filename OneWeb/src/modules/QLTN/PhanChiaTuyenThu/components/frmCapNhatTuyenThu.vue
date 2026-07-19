<template>
  <b-modal
    id="modal-capnhattuyenthu"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    no-close-on-backdrop
    @show="openForm"
    @hide="hideForm"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Chèn khách hàng vào tuyến thu
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="close()"
        ></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li @click="CapNhat()">
            <a href="javascript:;">
              <span class="icon one-save"></span> Cập nhật
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form padt8 padb5">
          <div class="pull-left">
            <div class="row">
              <h4 class="title bold mart6">
                CHÈN KHÁCH HÀNG VÀO TUYẾN THU MỚI
              </h4>
            </div>
          </div>
          <div class="pull-right">
            <div class="info-row">
              <div class="key w70">Đợt giao</div>
              <div class="value">
                <div class="select-custom">
                  <select
                    v-model="idDotGiao"
                    name=""
                    id=""
                    class="form-control"
                  >
                    <option
                      v-for="item in dsDotGiao"
                      :value="item.DOTGIAO_ID"
                      :key="item.DOTGIAO_ID"
                    >
                      {{ item.DOTGIAO }}
                    </option>
                  </select>
                </div>
              </div>
            </div>
          </div>
          <div class="clearfix"></div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách khách hàng mới</div>
          <DataGrid
            :columns="columns"
            :dataSource="dsKhachHangMoi"
            :showFilter="true"
            :allowPaging="true"
            :enablePagingServer="false"
            :showColumnCheckbox="true"
            dataKeyField="THANHTOAN_ID"
            :enabledSelectFirstRow="false"
            ref="gridKHMoi"
          ></DataGrid>
        </div>
        <div class="box-form">
          <div class="legend-title">
            <span class="title marr30">Chèn vào danh sách</span>
            <div class="pull-right">
              <div class="row">
                <div class="col-sm-7 col-12">
                  <button class="btn btn-second w80" @click="onChen()">
                    <span class="icon one-reload1"></span>
                    Chèn
                  </button>
                  <button class="btn btn-second w110" @click="onHuyChen()">
                    <span class="icon one-reload1"></span>
                    Hủy chèn
                  </button>
                </div>
                <div class="col-sm-5 col-12">
                  <div class="select-custom">
                    <select v-model="cachChen" class="form-control">
                      <option value="0">Chèn đầu danh sách</option>
                      <option value="1">Chèn cuối danh sách</option>
                      <option value="2">Chèn trước khách hàng</option>
                      <option value="3">Chèn sau khách hàng</option>
                    </select>
                  </div>
                </div>
                <!-- <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key red w70">Tìm kiếm</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                </div> -->
              </div>
            </div>
            <div class="clearfix"></div>
          </div>
          <DataGrid
            :columns="columns"
            :dataSource="dsKhachHangCu"
            :showFilter="true"
            :allowPaging="true"
            :enablePagingServer="false"
            :showColumnCheckbox="true"
            dataKeyField="THANHTOAN_ID"
            :enabledSelectFirstRow="false"
            ref="gridKHCu"
            @selectedRowChanged="khCu_selectedRowChanged"
          ></DataGrid>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<style></style>
<script>
import API from "../../api/PhanChiaTuyenThu";

export default {
  props: {
    dsKHMoi: Array,
    nvtcMoi_id: String,
    tuyenThuMoi_id: String,
    dotGiao_id: String,
    ngoaiLe: Number,
    nhoThu: Number,
    kieu: String,
    dsDotGiao: Array,
  },
  data() {
    return {
      columns: [
        {
          fieldName: "STT",
          headerText: "STT",
          allowFiltering: true,
          width: 80,
        },
        {
          fieldName: "MA_TT",
          headerText: "Mã thanh toán",
          allowFiltering: true,
        },
        {
          fieldName: "MATB_DD",
          headerText: "Mã đại diện",
          allowFiltering: true,
        },
        {
          fieldName: "TEN_TT",
          headerText: "Tên thanh toán",
          allowFiltering: true,
        },
        {
          fieldName: "DIACHI_TT",
          headerText: "Địa chỉ thanh toán",
          allowFiltering: true,
        },
        {
          fieldName: "DIACHI_BC",
          headerText: "Địa chỉ báo cước",
          allowFiltering: true,
        },
        {
          fieldName: "SONHA",
          headerText: "Số nhà",
          allowFiltering: true,
        },
        {
          fieldName: "TEN_NH",
          headerText: "Ngân hàng",
          allowFiltering: true,
        },
        {
          fieldName: "THUTU_IN",
          headerText: "Thứ tự in",
          allowFiltering: true,
        },
        {
          fieldName: "DOTGIAO_ID",
          headerText: "Đợt giao",
          allowFiltering: true,
        },
        {
          fieldName: "MAIN_GHEP",
          headerText: "Mã in ghép",
          allowFiltering: true,
        },
        {
          fieldName: "DIACHI_BC",
          headerText: "Mã phát hóa đơn",
          allowFiltering: true,
        },
        {
          fieldName: "DV_KHAC",
          headerText: "DV khác",
          allowFiltering: true,
        },
      ],
      dsKhachHangMoi: [],
      dsKhachHangCu: [],
      idDotGiao: "",
      cachChen: 0,
      countKHCu: 0,
      khCu_selected: null,
    };
  },
  methods: {
    close() {
      this.$bvModal.hide("modal-capnhattuyenthu");
    },
    hideForm() {
      this.dsKhachHangMoi = [];
      this.dsKhachHangCu = [];
    },
    CapNhat() {
      if (this.countKHCu >= this.dsKhachHangCu.length) {
        this.$root.toastError("Bạn phải thực hiện 'Chèn' trước khi 'Cập Nhật'");
        return;
      }
      this.$emit("chapNhan", this.dsKhachHangCu);
      this.close();
    },
    async openForm() {
      this.idDotGiao = this.dotGiao_id;
      this.loading(true);
      let data = {
        pNhanVienTCId: this.nvtcMoi_id,
        pTuyenThuId: this.tuyenThuMoi_id,
        pDotGiaoId: this.dotGiao_id,
        pNgoaiLe: this.ngoaiLe,
        pNhoThu: this.nhoThu,
        pKieu: this.kieu,
        pQuanId: 0,
        pPhuongId: 0,
        pPhoId: 0,
        pApId: 0,
        pKhuId: 0,
      };
      try {
        const response = await API.getDanhSachPhanChiaTuyenThu(
          this.axios,
          data
        );
        if (response.data.error_code === "BSS-00000000") {
          if (response.data.data.length > 0) {
            this.dsKhachHangCu = response.data.data;
            this.countKHCu = response.data.data.length;
          } else {
            //   this.$root.toastError("Không có dữ liệu, vui lòng thử lại!");
            this.dsKhachHangCu = [];
            this.countKHCu = 0;
          }
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
        this.dsKhachHangCu = [];
        this.countKHCu = 0;
      } finally {
        this.loading(false);
      }
    },
    onChen() {
      if (this.dsKhachHangMoi.length <= 0) {
        this.$root.toastError("Không có dữ liệu để chèn!");
        return;
      }
      const idx = this.dsKhachHangCu.findIndex((v) => v == this.khCu_selected);
      const dsSelected = this.$refs.gridKHMoi.getSelectedRecords() || [];
      dsSelected.map((item, index) => {
        if (this.cachChen == 0) {
          this.dsKhachHangCu = this.insert(this.dsKhachHangCu, 0, item);
        }
        if (this.cachChen == 1) {
          this.dsKhachHangCu = this.insert(
            this.dsKhachHangCu,
            this.dsKhachHangCu.length,
            item
          );
        }
        if (this.cachChen == 2) {
          this.dsKhachHangCu = this.insert(this.dsKhachHangCu, idx, item);
        }
        if (this.cachChen == 3) {
          this.dsKhachHangCu = this.insert(this.dsKhachHangCu, idx + 1, item);
        }
        this.dsKhachHangMoi = this.dsKhachHangMoi.filter(
          (v) => v.THANHTOAN_ID != item.THANHTOAN_ID
        );
      });
    },
    onHuyChen() {
      const dsSelected = this.$refs.gridKHCu.getSelectedRecords() || [];
      dsSelected.map((item) => {
        this.dsKhachHangMoi = this.insert(this.dsKhachHangMoi, 0, item);
        this.dsKhachHangCu = this.dsKhachHangCu.filter(
          (v) => v.THANHTOAN_ID != item.THANHTOAN_ID
        );
      });
    },
    insert(arr, index, newItem) {
      return [...arr.slice(0, index), newItem, ...arr.slice(index)];
    },
    khCu_selectedRowChanged(data) {
      if (data) {
        this.khCu_selected = data;
      } else {
        this.khCu_selected = null;
      }
    },
  },
  watch: {
    dsKHMoi: {
      handler: function (val) {
        this.dsKhachHangMoi = val;
      },
    },
  },
};
</script>
