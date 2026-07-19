<template #default="{ ok }">
  <b-modal id="popupTraCuuTB" modal-class="fullscreen-modal" body-class="body-content" header-class="popup-header"
    content-class="popup-box" hide-footer no-close-on-backdrop no-close-on-esc v-model="showModal"
    title="Tra cứu thuê bao hỗ trợ">
  <!-- <template #modal-header>
      <div class="title">
        <span class="icon icon-notepad"></span>
        Tra cứu thuê bao hỗ trợ
      </div>
      <div class="close -ap icon-close" data-dismiss="modal" @click="closeModal"></div>
    </template> -->

  <!-- <div class="popup-body"> -->
  <div class="list-actions-top">
    <ul class="list">
      <li>
        <a href="#/htkh/TraCuuTienDoHoTro" title="Tìm kiếm" @click="this.btnTimKiemTB">
          <span class="icon one-search"></span>Tìm kiếm
        </a>
      </li>
      <li>
        <a href="#/htkh/TraCuuTienDoHoTro" title="Chấp nhận" @click="this.btnChapNhan">
          <span class="icon -ap icon-ion-android-checkmark-circle"></span>Chấp nhận
        </a>
      </li>
      <li>
        <a href="#/htkh/TraCuuTienDoHoTro" title="Xuất DS" @click="this.btnXuatDSTB">
          <span class="icon one-download"></span>Xuất DS
        </a>
      </li>
    </ul>
  </div>
  <div class="box-form popup">
    <div class="legend-title">Thông tin tra cứu</div>
    <div class="row">
      <div class="col-sm-7 col-7">
        <div class="info-row">
          <div class="key">Tra cứu theo</div>
          <div class="value">
            <div class="select-custom">
              <SelectExt v-model.trim="cboLoaiTraCuu" :dataSource="dsLoaiTraCuu" dataTextField="NAME"
                dataValueField="ID" />
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-5 col-5">
        <div class="info-row">
          <div class="key">Thông tin tra cứu</div>
          <div class="value">
            <input v-model="thongTinTraCuuInput" type="text" class="form-control highlight">
          </div>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-sm-7 col-7">
        <div class="info-row">
          <div class="key">
            <div class="check-action">
              <input v-model="checkBoxDV" type="checkbox" class="check">
              <span class="name">Dịch vụ VT</span>
            </div>
          </div>
          <div class="value">
            <div class="select-custom">
              <SelectExt v-model.trim="cboDichVuVT" :dataSource="dsDichVuVT" dataTextField="TEN_DVVT"
                dataValueField="DICHVUVT_ID" :disabled="!allowDV" />
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-5 col-5">
        <div class="info-row">
          <div class="key">
            <div class="check-action">
              <input v-model="checkBoxTTHT" type="checkbox" class="check">
              <span class="name">Trạng thái hỗ trợ</span>
            </div>
          </div>
          <div class="value">
            <div class="select-custom">
              <SelectExt v-model.trim="cboTrangThaiHT" :dataSource="dsTrangThaiHT" dataTextField="TRANGTHAI_HT"
                dataValueField="TTHT_ID" :disabled="!allowTT" />
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-sm-7 col-7">
        <div class="info-row">
          <div class="key">
            <div class="check-action">
              <input v-model="checkBoxNgayTN" type="checkbox" class="check">
              <span class="name">Ngày tiếp nhận</span>
            </div>
          </div>
          <div class="value ngaythang">
            <!-- <div class="info-row123"> -->
            <div class="col-sm-5 col-5 tungay">
              <ejs-datepicker class="form-control bg-none bor0 fw6 text h24" :enabled="allowTNDate"
                :placeholder="waterMark" :value="dateVal" :format="dateFormat" v-model="cboNgayTNTu"></ejs-datepicker>
            </div>
            <div class="key col-sm-2 col-2">
              Đến ngày
            </div>
            <div class="col-sm-5 col-5 denngay">
              <ejs-datepicker class="form-control bg-none bor0 fw6 text h24" :enabled="allowTNDate"
                :placeholder="waterMark" :value="dateVal" :format="dateFormat" v-model="cboNgayTNDen"></ejs-datepicker>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-5 col-5">
        <div class="info-row">
          <div class="key">Số máy/Acc</div>
          <div class="value">
            <input v-model="soMayAccInput" type="text" class="form-control highlight">
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="box-form popup">
    <div class="legend-title">Danh sách thuê bao hỗ trợ</div>
    <div class="table-content">
      <DataGrid v-if="showGridThueBao" class="table-result table-gachle" :showFilter="true" ref='grid'
        :dataSource="this.dsThueBao" height='400px' gridLines='Both' :allowScrolling='true' :allowSorting='true'
        :allowPaging="true" :allowResizing='true' :enablePagingServer="false" @rowSelected="SCrow_selected_PU"
        @queryCellInfo='queryCellInfoEventPopUp' v-bind:columns="[
          { fieldName: 'STT', freeze: 'Left', headerText: 'Stt', width: '50px', textAlign: 'Center' },
          { fieldName: 'MA_TB', headerText: 'Số máy/Acc', width: '120px', textAlign: 'Center' },
          { fieldName: 'TEN_TB', headerText: 'Tên thuê bao', width: '200px', textAlign: 'Center' },
          { fieldName: 'DIACHI_TB', headerText: 'Địa chỉ thuê bao', width: '300px', textAlign: 'Center' },
          { fieldName: 'TEN_DVVT', headerText: 'Dịch vụ VT', width: '150px', textAlign: 'Center' },
          { fieldName: 'TRANGTHAI_TB', headerText: 'Trạng thái', width: '150px', textAlign: 'Center' }
        ]">
      </DataGrid>

    </div>
  </div>
  <!-- </div> -->
  </b-modal>
</template>


<script>
import { required } from 'vuelidate/lib/validators'
import { Page, Filter, CommandColumn, Resize, Sort, Freeze } from "@syncfusion/ej2-vue-grids"
import XLSX from "xlsx";
import { L10n } from '@syncfusion/ej2-base'
L10n.load({
  'vi-VN': {
    treegrid: {
      EmptyRecord: 'Không có bản ghi nào để hiển thị'
    }
  }
})

export default {
  name: "ModalPopupDSThueBao",
  props: ["modalId"],
  provide: {
    grid: [Filter, Page, CommandColumn, Resize, Sort, Freeze]
  },
  data() {
    return {
      showGridThueBao: true,
      isDisabledNhapMoi: false,
      showModal: false,
      thueBaoSelected: null,
      dsThueBao: [],
      cboLoaiTraCuu: null,
      dsLoaiTraCuu: [],
      thongTinTraCuuInput: null,
      checkBoxDV: false,
      cboDichVuVT: null,
      dsDichVuVT: [],
      allowDV: false,
      allowTT: false,
      checkBoxTTHT: false,
      cboTrangThaiHT: null,
      dsTrangThaiHT: [],
      checkBoxNgayTN: false,
      allowTNDate: false,
      waterMark: 'Chọn ngày',
      dateVal: new Date(),
      dateFormat: 'dd/MM/yyyy',
      cboNgayTNTu: new Date(Date.now() - 86400000),// ngày hôm qua
      cboNgayTNDen: new Date(),
      soMayAccInput: null,
    };
  },
  watch: {
    async showModal() {
      this.showGridThueBao = true;
      try {
        this.loading(true);
        await this.loadDsLoaiTraCuu();
        await this.loadDsTrangThai();
        await this.loadDsDichVu();
      } catch (e) {
      } finally {
        this.loading(false);
      }
      this.onModalShow();
    },
    "checkBoxNgayTN": function (newBH) {
      if (newBH)
        this.allowTNDate = true
      else this.allowTNDate = false
    },
    "checkBoxDV": function (newDV) {
      if (newDV)
        this.allowDV = true
      else this.allowDV = false
    },
    "checkBoxTTHT": function (newTT) {
      if (newTT)
        this.allowTT = true
      else this.allowTT = false
    },

  },
  computed: {
    isDisabled: function () {
      return !this.isDisabledNhapMoi;
    }
  },
  created: async function () {

  },
  destroyed() {
    this.showGridThueBao = false;
  },
  methods: {
    onModalShow() {
    },

    closeModal() {
      this.$bvModal.hide("popupTraCuuTB");
    },
    validateData(data) {
      let errorMessage = []
      if (data.p_loai_tracuu_id == null) errorMessage.push("Bạn chưa chọn loại tra cứu")
      if (data.p_tiepnhan_tu == null && this.allowTNDate === true) errorMessage.push("Bạn chưa chọn Từ ngày tiếp nhận")
      if (data.p_tiepnhan_den == null && this.allowTNDate === true) errorMessage.push("Bạn chưa chọn Đến ngày tiếp nhận")
      if (data.p_dichvuvt_id == null && this.allowDV === true) errorMessage.push("Bạn chưa chọn dịch vụ")
      if (data.p_trangthai_ht == null && this.allowTT === true) errorMessage.push("Bạn chưa chọn trạng thái")
      if (errorMessage.length > 0) {
        this.$root.toastError(errorMessage.join('\n'))
        return true
      } else {
        return false
      }
    },
    btnXuatDSTB: function () {
      try {
        this.loading(true);
        let dsXuatExcel = [];
        if (this.dsThueBao.length > 0) {
          dsXuatExcel = [...this.dsThueBao];
          dsXuatExcel = dsXuatExcel.map(item => ({
            "Stt": item.STT,
            "Số máy/Acc": item.MA_TB,
            "Tên thuê bao": item.TEN_TB,
            "Địa chỉ thuê bao": item.DIACHI_TB,
            "Dịch vụ": item.TEN_DVVT,
            "Trạng thái": item.TRANGTHAI_TB
          }));
        } else {
          // this.$root.toastError("Danh sách không có dữ liệu để xuất!")
          dsXuatExcel = [{
            "Stt": null,
            "Số máy/Acc": null,
            "Tên thuê bao": null,
            "Địa chỉ thuê bao": null,
            "Dịch vụ": null,
            "Trạng thái": null
          }];
        }
        let ds = XLSX.utils.json_to_sheet(dsXuatExcel);
        let wb = XLSX.utils.book_new(); // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, ds, "DS_THUBAO"); // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, "Tra cứu danh sách thuê bao hỗ trợ_" + this.convertDate(new Date()) + ".xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    queryCellInfoEventPopUp: function (args) {
      if (!!args.column.field) {
        args.cell.title = args.cell.innerHTML
      }
    },
    async SCrow_selected_PU(args) {
      this.isDisabledNhapMoi = true
      this.thueBaoSelected = args.data
    },
    convertDate: function (date) {
      var date = new Date(date)
      var dd = String(date.getDate()).padStart(2, '0')
      var mm = String(date.getMonth() + 1).padStart(2, '0') //January is 0!
      var yyyy = date.getFullYear()
      return date = dd + '/' + mm + '/' + yyyy
    },
    loadDsLoaiTraCuu: async function () {
      try {
        this.loading(true);
        this.dsLoaiTraCuu = await this.getDsLoaiTraCuu();
        this.cboLoaiTraCuu = this.dsLoaiTraCuu[0].ID;
      } catch (e) {
        this.$root.toastError(e);
      } finally {
        this.loading(false);
      }
    },

    getDsLoaiTraCuu: async function () {
      let rs = [];
      try {
        let r = await this.$root.context.post(
          "/web-cskh/api/xuly-hotro-kh/dm-tracuu"
        );
        rs = r.data;
      }
      catch (e) {
        if (e) {
          console.log(e)
          this.$root.toastError(e);
        } else {
          this.$root.toastError("Có lỗi xảy ra khi tải danh sách sự cố");
        }
      }
      return rs;
    },

    loadDsTrangThai: async function () {
      try {
        this.loading(true);
        this.dsTrangThaiHT = await this.getDsTrangThai();
        this.cboTrangThaiHT = this.dsTrangThaiHT[0].TTHT_ID;
      } catch (e) {
        this.$root.toastError(e);
      } finally {
        this.loading(false);
      }
    },

    getDsTrangThai: async function () {
      let rs = [];
      try {
        let r = await this.$root.context.post(
          "/web-cskh/api/xuly-hotro-kh/dm-tt-hotro"
        );
        rs = r.data;
      }
      catch (e) {
        if (e) {
          console.log(e)
          this.$root.toastError(e);
        } else {
          this.$root.toastError("Có lỗi xảy ra khi tải danh sách sự cố");
        }
      }
      return rs;
    },
    loadDsDichVu: async function () {
      try {
        this.loading(true);
        this.dsDichVuVT = await this.getDsDichVu();
        this.cboDichVuVT = this.dsDichVuVT[0].DICHVUVT_ID;
      } catch (e) {
        this.$root.toastError(e);
      } finally {
        this.loading(false);
      }
    },

    getDsDichVu: async function () {
      let rs = [];
      try {
        let r = await this.$root.context.get(
          "/web-quantri/danhmuc-chung/CSS_DICHVU_VT"
        );
        rs = r.data;
      }
      catch (e) {
        if (e) {
          console.log(e)
          this.$root.toastError(e);
        } else {
          this.$root.toastError("Có lỗi xảy ra khi tải danh sách sự cố");
        }
      }
      return rs;
    },

    getDsThueBao: async function (objectSearch) {
      let rs = [];
      try {
        let r = await this.$root.context.post(
          "/web-cskh/api/xuly-hotro-kh/get-tb-ht-kh", objectSearch
        );
        rs = r.data;
      }
      catch (e) {
        if (e) {
          console.log(e)
          this.$root.toastError(e);
        } else {
          this.$root.toastError("Có lỗi xảy ra khi tải danh sách sự cố");
        }
      }
      return rs;
    },
    loadDsThueBao: async function (objectSearch) {
      try {
        this.loading(true);
        this.dsThueBao = await this.getDsThueBao(objectSearch);
      } catch (e) {
        this.$root.toastError(e);
      } finally {
        this.loading(false);
      }
    },
    btnTimKiemTB: async function () {
      try {
        this.loading(true);
        let objectSearch = new Object();
        objectSearch.p_loai_tracuu_id = this.cboLoaiTraCuu ? this.cboLoaiTraCuu : null;
        objectSearch.p_thongtin_tracuu = this.thongTinTraCuuInput ? this.thongTinTraCuuInput : "";
        objectSearch.p_dichvuvt_id = this.cboDichVuVT && this.allowDV ? this.cboDichVuVT : null;
        objectSearch.p_trangthai_ht = this.cboTrangThaiHT && this.allowTT ? this.cboTrangThaiHT : null;
        objectSearch.p_tiepnhan_tu = this.cboNgayTNTu && this.allowTNDate ? this.convertDate(this.cboNgayTNTu) : null;
        objectSearch.p_tiepnhan_den = this.cboNgayTNDen && this.allowTNDate ? this.convertDate(this.cboNgayTNDen) : null;
        objectSearch.p_ma_tb = this.soMayAccInput ? this.soMayAccInput : "";
        if (this.validateData(objectSearch)) return;
        await this.loadDsThueBao(objectSearch);
      } catch (e) {
        if (e) {
          console.log(e)
          this.$root.toastError(e);
        } else {
          this.$root.toastError("Có lỗi xảy ra khi tải danh sách sự cố");
        }
      } finally {
        this.loading(false);
      }
    },
    btnChapNhan: async function () {
      if (this.thueBaoSelected) {
        this.$emit('ChapNhanTB', { ...this.thueBaoSelected });
        this.closeModal();
      }
    },
  }
};
</script>
<style>
.cursor-pointer {
  cursor: pointer;
}

.info-row .value.calender-tientrinhsuco {
  display: flex;
  align-items: center;
  flex-wrap: nowrap;
}

.fullscreen-modal .modal-dialog {
  max-width: 70%;
  margin: auto;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  height: 80vh;
  padding: 25px;
  display: flex;
  position: fixed;
  z-index: 100000;
}

.value.ngaythang {
  display: flex;
}

.denngay {
  padding-right: 0px;
}

.tungay {
  padding-left: 0px;
}
</style>
