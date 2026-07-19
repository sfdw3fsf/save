<template>
    <div>
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a ref="btnLayTTMoi" @click="btnLayTTMoi_Click">
                        <span class="icon one-file-attach"></span>Lấy TT (F5)
                    </a>
                </li>
                <li>
                    <a ref="btnTraphieu" @click="btnTraphieu_Click">
                        <span class="icon one-file-back"></span>Trả phiếu
                    </a>
                </li>
                <li>
                    <a ref="tsbtnHuyPhieu" @click="tsbtnHuyPhieu_Click">
                        <span class="icon one-file-back"></span>Giao phiếu
                    </a>
                </li>
                <li>
                    <a ref="tsbtnXuatFile" @click="tsbtnXuatFile_Click">
                        <span class="icon one-download"></span>Xuất file
                    </a>
                </li>

            </ul>
        </div>
        <div :class="{'page-content': !isPopup, 'popup-body': isPopup}">
            <div class="box-form">
                <div class="legend-title">Thông tin khách hàng</div>
                <div class="row">
                    <div class="col-sm-7 col-12">
                        <div class="row">
                            <div class="col-sm-8 col-12">
                                <div class="info-row">
                                    <div class="key w90">Loại HĐ/PL</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <Select2Ext ref="cboLoaiHD" @change="cboLoaiHD_SelectedValueChanged"
                                                :allowFiltering="true"></Select2Ext>
                                        </div>
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w90">Mã GD</div>
                                    <div class="value">
                                        <div class="input-more-button">
                                            <button @click="btnLayMaHD_Click" class="btn">
                                                <span class="-ap icon-more_horiz"></span>
                                            </button>
                                            <input @keyup.enter="txtMaGD_KeyUp" ref="txtMaGD" type="text"
                                                class="form-control bold">

                                            <ejs-dialog cssClass="custom_popup_cucmanh" :enableResize='true' :visible="false" ref="popupFrmTraCuuHopDong"
                                              :header='"Tra cứu hợp đồng, phụ lục"' showCloseIcon=true width="80%" :target="target" :position="position" height="800">
                                              <SearchContractModal ref="searchContractModal" :donviId="Number($root.token.getDonViID())" :nhanvienId="Number($root.token.getNhanVienID())" :loaihd_id="modalSearch.loai_hd_id" :kieu="1" :tthd_id="modalSearch.tthd_id"
                                              @accept="acceptSearchContract"/>
                                            </ejs-dialog>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w90">Dịch vụ</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <Select2Ext ref="cboDichVuVT" @change="cboDichVuVT_SelectedValueChanged"
                                                :allowFiltering="true"></Select2Ext>
                                        </div>
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w90">Số máy/Acc</div>
                                    <div class="value">
                                        <input @keyup.enter="txtMaTB_KeyUp" ref="txtMaTB" type="text"
                                            class="form-control highlight">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-5 col-12">
                        <div class="info-row">
                            <div class="key w90">Tên thuê bao</div>
                            <div class="value">
                                <input ref="txtTenTB" type="text" class="form-control bold">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Địa chỉ LĐ</div>
                            <div class="value">
                                <input ref="txtDiaChiLD" type="text" class="form-control bold">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="legend-title mart20">Thông tin phiếu giao</div>
                <div class="row">
                    <div class="col-sm-7 col-12">
                        <div class="info-row">
                            <div class="key w90">Ngày giao</div>
                            <div class="value padt7 w20">
                                <div class="check-action">
                                    <input @change="chkNgayGiao_CheckedChanged" ref="chkNgayGiao" type="checkbox"
                                        class="check">
                                    <span class="name"></span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="input-icon-right">
                                    <VueDateExt type="date" format="DD/MM/YYYY" ref="dtpNgayGiao" />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-5 col-12">
                        <div class="info-row">
                            <div class="key w90">Đơn vị</div>
                            <div class="value">
                                <input ref="txtDonViGiao" type="text" class="form-control">
                            </div>
                        </div>

                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-7 col-12">
                        <div class="info-row">
                            <div class="key w90">Số quyết định</div>

                            <div class="value">
                                <input ref="txtSoQD" type="text" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-5 col-12">
                        <div class="info-row">
                            <div class="key w90">Nhân viên</div>
                            <div class="value">
                                <input ref="txtNhanVienGiao" type="text" class="form-control">
                            </div>
                        </div>

                    </div>
                </div>
                <div class="info-row">
                    <div class="key w90">Nội dung giao</div>

                    <div class="value">
                        <input ref="txtNoidunggiao" type="text" class="form-control">
                    </div>
                </div>
                <div class="legend-title mart20">Thông tin phiếu trả</div>
                <div class="row">
                    <div class="col-sm-7 col-12">
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key w90">Lý do trả</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <Select2Ext ref="cboLyDoTra" :allowFiltering="true"></Select2Ext>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w90">Ngày trả</div>
                                    <div class="value w20 padt7">
                                        <div class="check-action">
                                            <input @change="chkNgayTra_CheckedChanged" ref="chkNgayTra" type="checkbox"
                                                class="check">
                                            <span class="name"></span>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <VueDateExt type="date" format="DD/MM/YYYY"  ref="dtpNgayTra" />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key w90">Đơn vị trả</div>
                                    <div class="value">
                                        <input ref="txtDonViTra" type="text" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w90">Kiểu trả</div>

                                    <div class="value">
                                        <div class="select-custom">
                                            <Select2Ext ref="cboTT" :allowFiltering="true"></Select2Ext>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Ghi chú</div>
                            <div class="value">
                                <textarea ref="txtGhichu" class="form-control"
                                    style="height: 62px;resize: none;"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-5 col-12">
                        <div class="info-row">
                            <div class="key w90">Nội dung trả</div>
                            <div class="value">
                                <textarea ref="txtNDTra" class="form-control"
                                    style="height: 145px;resize: none;"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="legend-title mart20">
                    <div class="pull-left">Danh sách phiếu yêu cầu</div>
                    <div class="pull-right red">
                        Tổng số phiếu: {{tongPhieu}}
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="table-content">
                    <DataGridCustom ref="gridDanhSach" v-bind:columns="collumns" :showColumnCheckbox="false"
                        :showFilter="true" :allowPaging="true" :enablePagingServer="false" :enabledSelectFirstRow="true"
                        :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }" panelDataHeight="170"
                        @queryCellInfo="gview_DanhSach_queryCellInfo"
                        @rowSelected="gridviewDanhSach_FocusedRowChanged"></DataGridCustom>
                </div>
            </div>
        </div>
        <ExportDataModal :data="dsIn" ref="ExportDataModal" />
    </div>
</template>
<script>
import moment from "moment";
import Select2Ext from "../DeclareLandline/components/Select2Ext";
import DataGridCustom from "../DeclareLandline/components/DataGridCustom";
import VueDateExt from "../DeclareLandline/components/VueDateExt";
import SearchContractModal from "@/modules/search/subscriber/SearchContract/SearchContract.vue";
import ExportDataModal from "@/modules/contract/profile/ExportData/ExportDataModal.vue";
import {
  TrangThaiHD,
  DichVuVienThong,
  LoaiHopDong,
  KIEU_HD,
  TRANGTHAI_DONGBO,
  LoaiHinhTB,
  LOAI_DV,
} from "@/const/enums";

export default {
  emits: ['btnTraphieu_Click_onSuccess'],
  props: {
    isPopup: {
      type: [Boolean],
      default: false
    },
  },
  components: {
    Select2Ext,
    DataGridCustom,
    VueDateExt,
    SearchContractModal,
    ExportDataModal
  },
  data() {
    return {
      target: document.querySelector('body'),
      //region khai bao
      position: { X: 'center', Y: 'center' },
      tongPhieu: 0,
      phieu_id: 0,
      dtList: [],
      bangts: [],
      tt_nd: [],
      modal: false,
      giaoPhieu: [],
      cmUlt: [],
      hoancong: [],
      dsTraPhieu: [],
      checkdata: [],
      tdan: [],
      kieugoi_id: 0,
      huonggiao_id: 0,
      hdtb_id: 0,
      hdtb_cha_id: 0,
      ma_gd: "",
      ngaygiao: "0",
      ngaytra: "0",
      donvi_id:0,
      kt_daucuoi: 0,
      kt_load: false,
      in_loaihd_id: 0,
      in_dichvuvt_id: 0,
      dtThaoTac: [],
      quytrinh_id: -1,
      tthd_id: -1,
      dsIn: [],
      kt_thuchien: false,
      vloaitb_id: 0,
      vdichvuvt_id: 0,
      vphieu_cha_id: 0,
      vstatus: "",
      vloaihd_id: 0,
      luong_id: 0,
      labelFunctionTitle: "",
      ngay_cn: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
      current_item: null,
      collumns: [
        {
          fieldName: "ma_gd",
          headerText: "Mã GD",
          allowSorting: false,
          width: "100",
          textAlign: 'left',
          autoFit: "true",
          freeze: "left"
        },
        {
          fieldName: "ma_tb",
          headerText: "Số máy/Acc",
          allowSorting: false,
          width: "130",
          textAlign: 'left',
          autoFit: "true",
          freeze: "left"
        },
        {
          fieldName: "ten_tb",
          headerText: "Tên TB",
          allowSorting: false,
          width: "250",
          textAlign: 'left',
          freeze: "left"
        },
        {
          fieldName: "loaihinh_tb",
          headerText: "Loại hình",
          allowSorting: false,
          width: "100",
          textAlign: 'left',
          autoFit: "true",
        },
        {
          fieldName: "ngay_giao_new",
          headerText: "Ngày giao",
          allowSorting: false,
          width: "150",
          textAlign: 'left',
          autoFit: "true",
        },
        {
          fieldName: "nv_giao",
          headerText: "Người giao",
          allowSorting: false,
          width: "100",
          textAlign: 'left',
          autoFit: "true",
        },
        {
          fieldName: "donvi_giao",
          headerText: "Đơn vị giao",
          customAttributes: {class: 'blue'},
          allowSorting: false,
          width: "300",
          textAlign: 'left',
          autoFit: "true",
        },
        {
          fieldName: "ten_loaihd",
          headerText: "Loại hợp đồng",
          allowSorting: false,
          width: "100",
          textAlign: 'left',
          autoFit: "true",
        },
        {
          fieldName: "nhanvien_lhd",
          headerText: "Nhân viên LHD",
          allowSorting: false,
          width: "100",
          textAlign: 'left',
          autoFit: "true",
        },
        {
          fieldName: "lydo_ton",
          headerText: "Lý do tồn",
          allowSorting: false,
          width: "100",
          textAlign: 'left',
          autoFit: "true",
        },
         {
          fieldName: "donvi_ph",
          headerText: "Đơn vị P/H",
          allowSorting: false,
          width: "100",
          textAlign: 'left',
          autoFit: "true",
        },
         {
          fieldName: "ketqua_xl",
          headerText: "Kết quả XL",
          allowSorting: false,
          width: "100",
          textAlign: 'left',
          autoFit: "true",
        },
         {
          fieldName: "trangthai_hd",
          headerText: "Trạng thái HĐ",
          allowSorting: false,
          width: "100",
          textAlign: 'left',
          autoFit: "true",
        },
        {
          fieldName: "diachi_ld",
          headerText: "Địa chỉ lắp đặt",
          allowSorting: false,
          width: "100",
          textAlign: 'left',
          autoFit: "true",
        }

      ],
      modalSearch: {}
      //endregion
    };
  },
  created() {
    this.$root.$on('bv::modal::shown', (bvEvent, modalId) => {
      document.getElementsByClassName('modal-content').forEach(function (v) {
        v.removeAttribute('tabindex')
      })
    })
  },
  mounted() {
    $('.modal-content').removeAttr('tabindex');
    this.frmTraPhieuTC_Load();
  },
  methods: {
    gview_DanhSach_queryCellInfo(args) {
        if (args.column.field === 'ngay_giao_new') {
            let dhh = moment(args.cell.innerHTML, "DD/MM/YYYY HH:mm A")
            if (dhh < new Date()) {
                args.cell.setAttribute('style', 'background-color:red;');
            } else {
              args.cell.setAttribute('style', 'background-color:green;');
            }
        }
    },
    async tsbtnXuatFile_Click() {
      this.$refs.ExportDataModal.showModal();
    },
    frmTraPhieuTC(
      _ma_gd,
      _loaihd_id,
      _dichvuvt_id,
      _donvi_id,
      _ma_tb,
      _ngaygiao,
      _ngaytra,
      _kieugoi_id
    ) {
      this.kieugoi_id = _kieugoi_id;
      this.ma_gd = _ma_gd;
      this.$refs.txtMaGD.value = _ma_gd;
      this.$refs.txtMaTB.value = _ma_tb;
      this.in_loaihd_id = _loaihd_id;
      this.donvi_id = _donvi_id
      this.in_dichvuvt_id = _dichvuvt_id;
      this.$nextTick(() => {
        this.frmTraPhieuTC_Load();
      })
    },
    frmTraPhieuTC_Load() {
      $('.modal-content').removeAttr('tabindex');
      this.$refs.dtpNgayTra.disabled = true
      this.$refs.dtpNgayGiao.disabled = true
      this.$refs.dtpNgayTra.value = this.ngay_cn;
      this.load();
    },
    async load() {
      try {
        let str = "";
        let Tag = this.$route.query.tag;
        console.log(Tag)
        try {
          if (this.kieugoi_id == 0)
            if (Tag) this.kieugoi_id = Number(Tag);
            else this.kieugoi_id = 0;
        } catch (err) {
          console.log(err);
          this.kieugoi_id = 1; //Lấy danh sách phiếu cần trả lại do giao sai,ko hoàn công được
        }
        if(!this.kieugoi_id) return
        this.loading(true)
        let rs_cboLoaiHD = await this.axios.post(
          "web-hopdong/traphieuthicong/ht_loai_hd_combobox_tp"
        );

        let rs_cboDichVuVT = await this.axios.get(
          "/web-quantri/danhmuc-chung/CSS_DICHVU_VT"
        );

        let rs_cboDichVuVT_arr = [{DICHVUVT_ID: 0,
                              GHICHU: "1",
                              MA_DVVT: "CD",
                              SUDUNG: 1,
                              SU_DUNG: null,
                              TEN_DVVT: "Tất cả"}].concat(rs_cboDichVuVT.data.data)
        let dts = rs_cboLoaiHD.data.data
        dts[0].ten_loaihd = "Tất cả"
        this.$refs.cboLoaiHD.HT_COMBOBOX(dts, "ten_loaihd", "loaihd_id")
        this.$refs.cboDichVuVT.HT_COMBOBOX(rs_cboDichVuVT_arr, "TEN_DVVT", "DICHVUVT_ID")

        if (this.in_loaihd_id != 0)
          this.$refs.cboLoaiHD.value = this.in_loaihd_id;

        if (this.in_dichvuvt_id != 0)
          this.$refs.cboDichVuVT.value = this.in_dichvuvt_id;

        let rs_cboTT = await this.axios.get(
          "web-quantri/danhmuc-chung/CSS_LOAI_PHIEUTRA"
        );
        this.$refs.cboTT.HT_COMBOBOX(rs_cboTT.data ? rs_cboTT.data.data.filter(item => item.LOAIPT_ID != 1 && item.LOAIPT_ID != 4) : [], "NOIDUNG", "LOAIPT_ID")


        this.HienThi_GiaoDien(this.kieugoi_id);
        this.HienThiDanhSachHDTB(this.ma_gd, this.ngaygiao, this.ngaytra);
        // this.BeginInvoke(new MethodInvoker(this.HienThi_Mau_Grid)); // Hàm này hiển thị màu lưới khi load form
        this.kt_load = true;
        console.log("Loading xong")
        this.loading(false)
      } catch (err) {
        console.log(err);
        this.loading(false)
      }
    },
    //endregion
    HienThi_Mau_Grid() {
      //if (dtgDS.Rows.Count > 0)
      //    cmUlt.Hienthi_Mau_DataGridView(dtgDS, 1, 1);
    },
    HienThi_GiaoDien(kieugoi) {
      if (kieugoi == 1) {
        this.$refs.tsbtnHuyPhieu.style.display = "none";
      }

      if (kieugoi == 3) {
        //Lấy danh sách phiếu cần trả lại do giao sai,ko hoàn công được
        this.$refs.chkNgayTra.disabled = true;
        this.$refs.chkNgayGiao.disabled = false;
        this.$refs.btnTraphieu.style.display = null;
        this.$refs.tsbtnHuyPhieu.style.display = "none";
        this.$parent.labelFunctionTitle = "TRẢ PHIẾU THI CÔNG";
      } else if (kieugoi == 2) {
        this.$refs.btnTraphieu.style.display = "none";
        this.$refs.tsbtnHuyPhieu.style.display = null;
        this.$refs.chkNgayTra.disabled = false;
        this.$refs.chkNgayGiao.disabled = true;
        this.$refs.txtNDTra.readOnly = true;
        this.$refs.txtNDTra.style.background = "#FFF9EB";
        this.$refs.txtGhichu.readOnly = true;
        this.$refs.txtGhichu.style.background = "#FFF9EB";
        this.$refs.cboTT.disabled = true;
        this.$parent.labelFunctionTitle = "NHẬN PHIẾU THI CÔNG";
      }
    },

    //region Click

    tsbtnHuyBo_Click() {
      this.Clear(0);
    },

    CapNhat() {
      this.$refs.txtNDTra.value = "";
      this.$refs.cboTT.value = 0;
      this.$refs.txtMaTB.value = "";
      this.$refs.txtMaGD.value = "";
      this.$refs.txtTenTB.value = "";
      this.$refs.txtDiaChiLD.value = "";
      this.HienThiDanhSachHDTB(this.ma_gd, this.ngaygiao, this.ngaytra);
    },

    async btnTraphieu_Click() {
      try {
        if (!this.phieu_id || this.$refs.gridDanhSach.dataSource.length <= 0) {
          this.$toast.error("Không có thông tin phiếu!");
          return;
        }
        if (!this.$refs.txtNDTra.value) {
          this.$toast.error("Bạn chưa nhập nội dung trả!");
          this.$refs.txtNDTra.focus();
          return;
        }
        if (!this.$refs.cboLyDoTra.value) {
          this.$toast.error("Hãy chọn lý do trả!");
          return;
        }

        this.$confirm("Bạn thật sự muốn trả lại phiếu đã nhận không ?", {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
          type: "warning",
        }).then(async () => {
          let data = [{
            "PHIEU_ID": this.phieu_id, "NHANVIEN_ID": this.$root.token.getNhanVienID(), "STATUS": this.vstatus,
            "LOAITB_ID": this.vloaitb_id, "DICHVUVT_ID": this.current_item.dichvuvt_id, "LOAIHD_ID": this.current_item.loaihd_id, "QUYTRINH_ID": this.quytrinh_id,
            "TTHD_ID": this.tthd_id, "HDTB_ID": this.hdtb_id, "HDTB_CHA_ID": this.hdtb_cha_id ? this.hdtb_cha_id : null,
            "NGAYGIAO": "0",
            "NGAYTRA": "0",
            "KIEUGOI_ID": this.kieugoi_id, "PHIEU_CHA_ID": this.vphieu_cha_id ? this.vphieu_cha_id : null, "ND_TRA": this.$refs.txtNDTra.value, "LOAIPT_ID": this.$refs.cboTT.value,
            "GHICHU": this.$refs.txtGhichu.value, "LYDOTRA_ID": this.$refs.cboLyDoTra.value, "DTP_NGAYTRA": this.$refs.dtpNgayTra.value
          }]

          data = JSON.stringify(data)
          this.loading(true)
          let rs = await this.axios.post("/web-hopdong/traphieuthicong/fn_traphieu_traphieutc", {
            "ds": data,
          });
          this.loading(false)
          if(rs.data && ( rs.data.data != "1" && rs.data.data != "OK" ))
            this.$toast.error(rs.data.data);
          else {
            this.HienThiDanhSachHDTB(this.ma_gd, this.ngaygiao, this.ngaytra);
            this.$emit("traphieu_success", true)
            this.$toast.success("Đã trả phiếu thành công!")
            this.CapNhat()
            this.$emit('btnTraphieu_Click', {status: 1})
          }
        });
      } catch (err) {
        this.$toast.error("Trả phiếu thất bại!");
        this.loading(false)
      }
      //int nIndex = dtgDS.CurrentRow.Index;
    },

    PHAILAM(code) {
      return this.axios
        .post("/web-hopdong/thaydoi_thongso_megawan/phailam", {
          luong_id: this.luong_id,
          code: code,
        })
        .then((response) => {
          return response.data &&
            response.data.data &&
            response.data.data == "1"
            ? true
            : false;
        });
    },

    //region Clear
    Clear(kieu) {
      if (kieu == 1) {
        // Enter Ma TB
        this.$refs.txtMaGD.value = "";
      } else if (kieu == 2) {
        // Enter Ma GD
        this.$refs.txtMaTB.value = "";
      } // Clear all
      else {
        this.$refs.txtMaGD.value = "";
        this.$refs.txtMaTB.value = "";
      }

      this.$refs.txtNoidunggiao.value = "";
      this.$refs.txtTenTB.value = "";
      this.$refs.txtDiaChiLD.value = "";
      this.$refs.txtDonViGiao.value = "";
      this.$refs.txtNhanVienGiao.value = "";
      this.$refs.txtSoQD.value = "";

      this.$refs.txtNDTra.value = "";
      this.$refs.txtGhichu.value = "";
      this.$refs.dtpNgayTra.value = this.ngay_cn;
      this.$refs.cboTT.value = 2;
      this.$refs.cboLoaiHD.value = 0;
      this.$refs.dtpNgayGiao.value = this.ngay_cn;
      this.$refs.txtDonViTra.value = "";
      this.hdtb_id = 0;
      this.huonggiao_id = 0;
      this.phieu_id = 0;
      this.vloaitb_id = 0;
      this.vdichvuvt_id = 0;
      this.vphieu_cha_id = 0;
      this.vstatus = "";
      this.vloaihd_id = 0;
    },
    //endregion

    //region CapNhat
    //endregion

    //region this.HienThiDanhSachHDTB
    async HienThiDanhSachHDTB(ma_gd, ngaygiao, ngaytra) {
      try {
        this.loading(true);

        let rs = await this.axios.post("web-hopdong/traphieuthicong/lay_ds_phieu_giao", {
          ma_gd: ma_gd ? ma_gd : "0",
          loaihd_id: Number(this.$refs.cboLoaiHD.value),
          dichvuvt_id: Number(this.$refs.cboDichVuVT.value),
          donvi_id: this.donvi_id ? this.donvi_id : this.$root.token.getNhanVienID(),
          ngay_giao: ngaygiao ? ngaygiao.split(" ")[0] : "0",
          ngaytra: ngaytra ? ngaytra.split(" ")[0] : "0",
          kieu: this.kieugoi_id,
          // kieu: 1,
          nhanvien_id: this.$root.token.getNhanVienID()
        });
        this.dsIn = rs.data ? rs.data.data : [];
        let dtList = rs.data ? rs.data.data : [];
        let dtList_r = dtList.map(item => ({
          ...item,
          ngay_giao_new : moment(item.ngaygiao).isValid() ? moment(item.ngaygiao).format("DD/MM/YYYY HH:mm:ss") : moment(item.ngaygiao, "DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY HH:mm:ss")
        }));

        this.$refs.gridDanhSach.dataSource = dtList_r
        this.tongPhieu = dtList_r.length
        this.loading(false);
      } catch (err) {
        console.log(err);
        this.loading(false);
      }
    },
    //endregion

    btnLayMaHD_Click() {
      this.modalSearch = {
        loai_hd_id : LoaiHopDong.DAT_MOI,
        trangthai_hd : TrangThaiHD.MOI
      }
      this.$refs.searchContractModal.clearData();
      this.$refs.searchContractModal.loadPopup();
      this.$refs.popupFrmTraCuuHopDong.show();
    },
    acceptSearchContract(item) {
      console.log(item)
      this.$refs.txtMaGD.value = item.hopdong.ma_gd ? item.hopdong.ma_gd : "";
      this.$refs.txtMaTB.value = item.thuebao.ma_tb ? item.thuebao.ma_tb : ""
      this.$refs.popupFrmTraCuuHopDong.hide();
      this.HienThiDanhSachHDTB(item.hopdong.ma_gd , 0 , 0)
    },

    //endregion

    //region dtgDS_SelectionChanged
    formatDate(value_) {
      if (moment(value_).isValid()) {
        return moment(value_).format("DD/MM/YYYY");
      } else {
        return value_;
      }
    },
    async gridviewDanhSach_FocusedRowChanged(ds) {
      console.log('gridviewDanhSach_FocusedRowChanged')
      console.log(ds)
      try {
        if (!ds.data)
          return
        let item = ds.data
        this.current_item = item
        this.$refs.txtNoidunggiao.value = item["nd_giao"];
        this.$refs.txtMaGD.value = item["ma_gd"];
        this.$refs.txtMaTB.value = item["ma_tb"];
        this.$refs.txtTenTB.value = item["ten_tb"];
        this.$refs.txtDiaChiLD.value = item["diachi_ld"];
        this.$refs.txtDonViGiao.value = item["donvi_giao"];
        this.$refs.txtNhanVienGiao.value = item["nv_giao"];
        this.$refs.dtpNgayGiao.value = this.formatDate(
          item["ngay_giao"]
        );
        this.$refs.txtSoQD.value = item.sophieu; //dtList.Rows[nIndex]["sophieu"];

        this.quytrinh_id = Number(item["quytrinh_id"]); //Number(dtList.Rows[nIndex]["quytrinh_id"]);
        this.tthd_id = Number(item["tthd_id"]); //Number(dtList.Rows[nIndex]["tthd_id"]);
        this.phieu_id = Number(item["phieu_id"]); //Number(dtList.Rows[nIndex]["phieu_id"]);
        if (item["phieu_cha_id"])
          this.vphieu_cha_id = Number(item["phieu_cha_id"]); //Number(dtList.Rows[nIndex]["phieu_cha_id"]);
        this.vstatus = item["status"]; //dtList.Rows[nIndex]["status"];
        if (item.loaihd_id)
          this.vloaihd_id = Number(item["loaihd_id"]); //Number(dtList.Rows[nIndex]["loaihd_id"]);
        if (item["huonggiao_id"])
          this.huonggiao_id = Number(item["huonggiao_id"]); //Number(dtList.Rows[nIndex]["huonggiao_id"]);
        if (item["hdtb_id"])
          this.hdtb_id = Number(item["hdtb_id"]); //Number(dtList.Rows[nIndex]["hdtb_id"]);
        if (item["loaitb_id"])
          this.vloaitb_id = Number(item["loaitb_id"]); //Number(dtList.Rows[nIndex]["loaitb_id"]);
        this.loading(true)
        let rs_lydotra = await this.axios.post("web-hopdong/DiemChiaTrungGianTSL/lay_ds_lydotra", {
          "loaitb_id": this.vloaitb_id
        })
        this.loading(false)
        let data_ld = rs_lydotra.data ? rs_lydotra.data.data : []
        data_ld = data_ld.filter(function( element ) {
          return (element.lydotra && element.lydotra_id)
        });
        this.$refs.cboLyDoTra.HT_COMBOBOX(data_ld, "lydotra", "lydotra_id")

        this.$refs.cboLyDoTra.value = 0;

        if (item["lydotra_id"]) {
          //if (dtList.Rows[nIndex]["lydotra_id"] != "")
          this.$refs.cboLyDoTra.value = Number(item["lydotra_id"]);
        }
        //Number(dtList.Rows[nIndex]["lydotra_id"]);
        else this.$refs.cboLyDoTra.value = 0;
        if (this.kieugoi_id == 2) {
          console.log(item["ngay_tra"])
          this.$refs.dtpNgayTra.value = item["ngay_tra"] ? this.formatDate(item["ngay_tra"]) : this.tt_nd.ngay_cn //this.formatDate(dtList.Rows[nIndex]["ngay_tra"]);
          this.$refs.txtNDTra.value = item["nd_tra"]; //dtList.Rows[nIndex]["nd_tra"];
          this.$refs.txtGhichu.value = item["ghichu"]; //dtList.Rows[nIndex]["ghichu"];
          this.$refs.cboTT.value = item["loaipt_id"]; //dtList.Rows[nIndex]["loaipt_id"];

          this.huonggiao_id = Number(item["huonggiao_id"]); //Number(dtList.Rows[nIndex]["huonggiao_id"]);
          this.hdtb_id = Number(item["hdtb_id"]); //Number(dtList.Rows[nIndex]["hdtb_id"]);

          let dv_tra = await this.axios.post(
            "/web-tratruoc/thongtin_tratruoc_dn/fn_tt_donvi",
            { param: item['donvi_nhan_id'], type: 2 }
          );

          if (dv_tra.data && dv_tra.data.data)
            this.$refs.txtDonViTra.value = dv_tra.data.data;
          else this.$refs.txtDonViTra.value = "";
        } else this.$refs.txtDonViTra.value = "";
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi xảy ra: ");
      }
    },

    DoRowDoubleClick() {
      // GridHitInfo info = view.CalcHitInfo(pt);
      // if ((info.InRow || info.InRowCell) && info.RowHandle >= 0)
      // {
      //     try
      //     {
      //         //long phieu_id = Number(gridChitiet.GetRowCellValue(info.RowHandle, "PHIEU_ID"]));
      //         //if (phieu_id > 0)
      //         //{
      //         frmChiTietXLPhieuPH frm = new frmChiTietXLPhieuPH();
      //         frm.vphieu_id = phieu_id;
      //         frm.ShowDialog();
      //         //}
      //     }
      //     catch (err)
      //     {
      //         this.$toast.error("Có lỗi: " + err.message ? err.message : "");
      //     }
      // }
    },
    //endregion

    //region KeyUp
    txtMaGD_KeyUp() {
      try {
        if (!this.$refs.chkNgayGiao.checked) this.ngaygiao = "0";
        else this.ngaygiao = this.$refs.dtpNgayGiao.value;
        if (!this.$refs.chkNgayTra.checked) this.ngaytra = "0";
        else this.ngaytra = this.$refs.dtpNgayTra.value;
        this.ma_gd = this.$refs.txtMaGD.value;
        // dtList = giaoPhieu.LAY_DS_PHIEU_GIAO(ma_gd, Number(cboLoaiHD.value), Number(cboDichVuVT.value),
        //     tt_nd.nhanvien_id, ngaygiao, ngaytra, kieugoi_id, 0).Tables[0];
        this.HienThiDanhSachHDTB(this.ma_gd, this.ngaygiao, this.ngaytra)
        this.Clear(2);
      } catch (err) {
        console.log(err);
      }
    },
    //endregion

    cboLoaiHD_SelectedValueChanged() {
      try {
        if (this.$refs.cboLoaiHD.dataSource.length > 0) {
          if (this.kt_load) {
            this.HienThiDanhSachHDTB(
              this.ma_gd,
              this.ngaygiao,
              this.ngaytra
            );
          }
        }
      } catch (err) {
        console.log(err);
      }
    },

    cboDichVuVT_SelectedValueChanged() {
      try {
        if (this.$refs.cboDichVuVT.dataSource.length > 0) {
          if (!this.$refs.cboDichVuVT.value) return;
          if (this.kt_load) this.HienThiDanhSachHDTB(this.ma_gd, this.ngaygiao, this.ngaytra);
        }
      } catch (err) { }
    },

    btnLayTTMoi_Click() {
      if (!this.$refs.chkNgayGiao.checked) this.ngaygiao = "0";
      else this.ngaygiao = this.$refs.dtpNgayGiao.value;
      if (!this.$refs.chkNgayTra.checked) this.ngaytra = "0";
      else this.ngaytra = this.$refs.dtpNgayTra.value;
      this.HienThiDanhSachHDTB("0", this.ngaygiao, this.ngaytra);
    },

    txtMaTB_KeyUp() {
      try {
        let ngaygiao = "";
        let ngaytra = "";
        if (!this.$refs.chkNgayGiao.checked) ngaygiao = "0";
        else ngaygiao = dtpNgayGiao.value;
        if (!this.$refs.chkNgayTra.checked) ngaytra = "0";
        else ngaytra = this.$refs.dtpNgayTra.value;
        this.HienThiDanhSachHDTB(this.$refs.txtMaTB.value, ngaygiao, ngaytra);

        this.Clear(1);
      } catch (err) { }
    },

    chkNgayGiao_CheckedChanged() {
      if (this.$refs.chkNgayGiao.checked == true) {
        this.$refs.dtpNgayGiao.disabled = false;
        this.ngaygiao = this.$refs.dtpNgayGiao.value;
      } else {
        this.$refs.dtpNgayGiao.disabled = true;
        this.$refs.dtpNgayGiao.value = this.ngay_cn;
        this.ngaygiao = "0";
      }
      if (this.kt_load)
        this.HienThiDanhSachHDTB(this.ma_gd, this.ngaygiao, this.ngaytra);
    },

    chkNgayTra_CheckedChanged() {
      if (this.$refs.chkNgayTra.checked == true) {
        this.$refs.dtpNgayTra.disabled = false;
        this.ngaytra = this.$refs.dtpNgayTra.value;
      } else {
        this.ngaytra = "0";
        this.$refs.dtpNgayTra.disabled = true;
        this.$refs.dtpNgayTra.value = this.ngay_cn;
      }
      if (this.kt_load) this.HienThiDanhSachHDTB(ma_gd, ngaygiao, ngaytra);
    },

    tsbtnHuyPhieu_Click() {
      this.GiaoPhieu();
    },

    GiaoPhieu() {
      this.$router.push(
        "/contract/handoverticket?vhuonggiao_id=" +
          this.huonggiao_id +
          "&vma_gd=" +
          this.$refs.txtMaGD.value
          + "&vhuonggiao_id=" + this.huonggiao_id
      );
      // try
      // {
      //     frmGiaoPhieu f1 = new frmGiaoPhieu(this.$refs.txtMaGD.value, 1, huonggiao_id, hdtb_id, 0, dtpNgayGiao.value.toString("dd/MM/yyyy"));
      //     f1.ShowDialog();
      //     if (this.$refs.txtMaGD.value != "")
      //     {
      //         this.HienThiDanhSachHDTB(ma_gd, ngaygiao, ngaytra);
      //     }
      // }
      // catch (err)
      // {
      //     this.$toast.error("" + err.message ? err.message : "");
      // }
    },

    dtpNgayGiao_ValueChanged() {
      try {
        if (this.$refs.chkNgayGiao.checked) {
          this.HienThiDanhSachHDTB(this.ma_gd, this.ngaygiao, this.ngaytra);
        }
      } catch (err) {
        this.$toast.error("" + err.message ? err.message : "");
      }
    },

    frmTraPhieuTC_KeyUp() {
      this.HienThiDanhSachHDTB("0", "", "");
    },

    txtMaGD_TextChanged() { }
  },
};
</script>
<style>
.modal-content .page-content {
  position: unset !important;
}

.none-padding {
  padding: 0;
}

.custom_popup_cucmanh {
  top: 20px !important;
}

.blue {
  color: blue !important;
}
</style>
