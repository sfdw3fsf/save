<template src="./DanhSachThueBaoKhongKhaoSat.html"></template>
<style src="./DanhSachThueBaoKhongKhaoSat.scss"></style>
<script>
import KyCuoc from "../../QLTN/components/KyCuoc.vue";
import { mapActions, mapState, mapGetters } from "vuex";
import moment from "moment";
import DSKhoaMoUploadXLS from "./popup/DSKhoaMoUploadXLS";
import DSKhoaMoUploadXLSXoa from "./popup/DSKhoaMoUploadXLSXoa";
import DanhSachThueBaoKhongKhaoSatApi from "../api/DanhSachThueBaoKhongKhaoSatApi";
import downloadexcel from "vue-json-excel";
import TableWithSearch from "../utils/TableWithSearch.vue";
import CustomDataGrid from "../utils/CustomDataGrid.vue";
import CSKHGrid from "../utils/CSKHGrid";
import ThongBaoModal from "../utils/ThongBaoModal.vue";
import YesNoModal from "../utils/YesNoModal.vue";
import SearchAccount from "@/modules/search/subscriber/SearchAccount";
import ModalXuatDuLieu from "../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu/index.vue";
import Vue from 'vue';
import { DateTimePickerPlugin } from '@syncfusion/ej2-vue-calendars';
import { currency } from '@/filters/currency'

Vue.use(DateTimePickerPlugin);
// import XuatFile from "../utils/XuatFile.vue";
const API_SUCCESS = "BSS-00000000"
export default {
  components: {
    KyCuoc,
    appDSKhoaMoUploadXLS: DSKhoaMoUploadXLS,
    appDSKhoaMoUploadXLSXoa: DSKhoaMoUploadXLSXoa,
    downloadexcel,
    TableWithSearch,
    CustomDataGrid,
    appThongBaoModal: ThongBaoModal,
    appYesNoModal: YesNoModal,
    SearchAccount,
    appModalXuatDuLieu: ModalXuatDuLieu,
    CSKHGrid
  },
  name: "DanhSachThueBaoKhongKhaoSat",
  data() {
    return {
      header: {
        title: "DANH SÁCH THUÊ BAO KHÔNG KHẢO SÁT",
        list: [
          {
            name: "Chăm sóc khách hàng",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Danh sách thuê bao không khảo sát",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      dateConfig: {
        altInput: true,
        dateFormat: "d/m/Y",
        altFormat: "d/m/Y",
      },
      loaiDSThueBaoKhongKhaoSat: [
        { value: "", text: "Loại thuê bao", disabled: true },
        { value: "6", text: "Thuê bao không khảo sát Lắp mới" },
        { value: "7", text: "Thuê bao không khảo sát Báo hỏng" },
      ],
      kyCuocDisable: true,
      kiemtra_quyen: false,
      dsChecked: [],
      dataChecked: [],
      from_date: new Date(), //fakedata
      to_date: new Date(),
      today: new Date(),
      chk_from_date: true,
      chk_to_date: true,
      canAddNew: false,
      xoaBtnEnable: true,
      nhapMoiBtnEnable: true,
      ghiLaiBtnEnable: true,
      nhapExcelBtnEnable: true,
      huyBoBtnEnable: true,
      ghiChu: "",
      lyDo: "",
      tenTB: "",
      maTB: "",
      pageNo: 1,
      record: null,
      pageNoDSThongTinThueBaoNo: 1,
      pageSize: 10,
      totalRecords: 10,
      totalPage: 0,
      loaiDSThueBaoKhongKhaoSatSelected: "6",
      ma_kh: "",
      fields2: [
        {
          fieldName: "MA_TB",
          headerText: "Số máy/Acc",
          allowFiltering: true,
          allowSorting: true,
          isSumSelected: true,
          isSum: true,
        },
        {
          fieldName: "TRANGTHAI_TB",
          headerText: "Trạng thái",
          allowFiltering: true,
          allowSorting: true,
          cellCssClass: "text-danger",
        },
        {
          fieldName: "TEN_DVVT",
          headerText: "Dịch vụ",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "LOAIHINH_TB",
          headerText: "Loại hình",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "TEN_TB",
          headerText: "Tên thuê bao",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "LYDO",
          headerText: "Lý do",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "DONVI_QL",
          headerText: "Đơn vị QL",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "NGUOI_CN",
          headerText: "Người CN",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "NHANVIEN",
          headerText: "Nhân viên",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "DONVI",
          headerText: "Đơn vị",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "NGAY_TH",
          headerText: "Ngày thực hiện",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "NGAY_TH_DEN",
          headerText: "Ngày TH đến",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "TRANGTHAI",
          headerText: "Trạng thái",
          allowFiltering: true,
          allowSorting: true,
          cellCssClass: "text-danger",
        },
      ],
      fieldsXuatDuLieu: [
        {
          fieldName: "CHON",
          headerText: "Chọn",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "MA_TB",
          headerText: "Số máy/Acc",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "TRANGTHAI_TB",
          headerText: "Trạng thái",
          allowFiltering: true,
          allowSorting: true,
          cellCssClass: "text-danger",
        },
        {
          fieldName: "TEN_DVVT",
          headerText: "Dịch vụ",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "LOAIHINH_TB",
          headerText: "Loại hình",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "TEN_TB",
          headerText: "Tên thuê bao",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "LYDO",
          headerText: "Lý do",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "DONVI_QL",
          headerText: "Đơn vị QL",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "NGUOI_CN",
          headerText: "Người CN",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "NHANVIEN",
          headerText: "Nhân viên",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "DONVI",
          headerText: "Đơn vị",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "NGAY_TH",
          headerText: "Ngày thực hiện",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "NGAY_TH_DEN",
          headerText: "Ngày TH đến",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "NGAY_CN",
          headerText: "Ngày TH đến",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "TRANGTHAI",
          headerText: "Trạng thái",
          allowFiltering: true,
          allowSorting: true,
          cellCssClass: "text-danger",
        },
      ],
      fieldsDSThongTinThueBaoNo2: [
        { fieldName: "MA_TB", headerText: "Số máy/Acc", allowFiltering: true, allowSorting: true, isSumSelected: true, isSum: true, },
        { fieldName: "TRANGTHAI_TB", headerText: "Trạng thái", allowFiltering: true, allowSorting: true, cellCssClass: "text-danger", },
        { fieldName: "TEN_DVVT", headerText: "Dịch vụ", allowFiltering: true, allowSorting: true, },
        { fieldName: "LOAIHINH_TB", headerText: "Loại hình", allowFiltering: true, allowSorting: true, },
        { fieldName: "TEN_TB", headerText: "Tên thuê bao", allowFiltering: true, allowSorting: true, },
        { fieldName: "THANGNO", headerText: "Tháng nợ", allowFiltering: true, allowSorting: true, cssClass: "text-right", },
        { fieldName: "TONGNO_TXT", headerText: "Tổng nợ", allowFiltering: true, allowSorting: true, cssClass: "text-right text-danger", },
      ],
      dsThueBaoKhongKhoaMo: [],
      dsThueBaoNo: [],
      thueBaoId: 0,
      thanhToanId: 0,
      kyCuoc: "",
      popupComponent: null,
      dgvThueBaoKhongKhoaMo: {
        dataSource: [],
        columns: [
          { fieldName: "MA_TB", headerText: "Số máy/Acc", allowFiltering: true, allowSorting: true, isSumSelected: true, isSum: true, },
          { fieldName: "TRANGTHAI_TB", headerText: "Trạng thái", allowFiltering: true, allowSorting: true, cssClass: "text-red", },
          { fieldName: "TEN_DVVT", headerText: "Dịch vụ", allowFiltering: true, allowSorting: true, },
          { fieldName: "LOAIHINH_TB", headerText: "Loại hình", allowFiltering: true, allowSorting: true, },
          { fieldName: "TEN_TB", headerText: "Tên thuê bao", allowFiltering: true, allowSorting: true, },
          { fieldName: "LYDO", headerText: "Lý do", allowFiltering: true, allowSorting: true, },
          { fieldName: "DONVI_QL", headerText: "Đơn vị QL", allowFiltering: true, allowSorting: true, },
          { fieldName: "NGUOI_CN", headerText: "Người CN", allowFiltering: true, allowSorting: true, },
          { fieldName: "NHANVIEN", headerText: "Nhân viên", allowFiltering: true, allowSorting: true, },
          { fieldName: "DONVI", headerText: "Đơn vị", allowFiltering: true, allowSorting: true, },
          { fieldName: "NGAY_TH", headerText: "Ngày thực hiện", allowFiltering: true, allowSorting: true, },
          { fieldName: "NGAY_TH_DEN", headerText: "Ngày TH đến", allowFiltering: true, allowSorting: true, },
          { fieldName: "TRANGTHAI", headerText: "Trạng thái", allowFiltering: true, allowSorting: true, cssClass: "text-red", },
        ],
        pageInfo: {
          page: 0,
          maxSize: 10,
          totalElement: 0,
          totalPages: 0,
          currentRow: 0,
          sort: null,
          propertiesSort: null
        },
        checked: [],
        enablePagingServer: false,
      },
      dgvThueBaoNo: {
        dataSource: [],
        columns: [
          { fieldName: "MA_TB", headerText: "Số máy/Acc", allowFiltering: true, allowSorting: true, width: 120, },
          { fieldName: "TRANGTHAI_TB", headerText: "Trạng thái", allowFiltering: true, allowSorting: true, cssClass: "text-red", width: 120,},
          { fieldName: "TEN_DVVT", headerText: "Dịch vụ", allowFiltering: true, allowSorting: true, width: 120,},
          { fieldName: "LOAIHINH_TB", headerText: "Loại hình", allowFiltering: true, allowSorting: true, width: 120,},
          { fieldName: "TEN_TB", headerText: "Tên thuê bao", allowFiltering: true, allowSorting: true, width: 120,},
          { fieldName: "THANGNO", headerText: "Tháng nợ", allowFiltering: true, allowSorting: true, cssClass: "text-right", width: 100,},
          { fieldName: "TONGNO_TXT", headerText: "Tổng nợ", allowFiltering: true, allowSorting: true, cssClass: "text-currency", width: 100,},
        ],
        pageInfo: {
          page: 0,
          maxSize: 10,
          totalElement: 0,
          totalPages: 0,
          currentRow: 0,
          sort: null,
          propertiesSort: null
        },
        checked: [],
        enablePagingServer: false,
      },
      customTemplate: function() {
          return  { template : Vue.component('footerTemplate', {
              template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
              data () { return { data: {data: {}}}; }
          })
          }
      }
    };
  },
  async created() {
    this.clearData();
    try {
      this.loading(true);
      // await this.getDanhMucVT()
    } catch (e) {
    } finally {
      this.loading(false);
    }
  },
  async mounted() {
    this.loading(true);

    var dsThamSo = await DanhSachThueBaoKhongKhaoSatApi.getDSThamSo(
      this.axios,
      0
    );
    this.kiemtra_quyen = dsThamSo["DOCFILE_DSTB_KHONGNHANHONG"] == "1";

    await this.getLoaiDSThueBaoKhongKhaoSat();
    // await this.fetchData();
    await this.getDsThueBao()
    if (this.dgvThueBaoKhongKhoaMo.dataSource.length <= 0) {
      this.setButton(1);
      this.thueBaoId = 0;
      this.thanhToanId = 0;
      this.ghiChu = "";
      this.tenTB = "";
    }
    this.loading(false);
  },
  computed: {
    ...mapState("qltnCommon", ["thongtinKyHoaDon"]),
    ...mapGetters("cskhCommon", ["loaiDSThueBaoKhongKhaoSatGetter"]),
    // ...mapGetters('qltnCommon', ['getKyHoaDonGetter']),
    ...mapGetters("danhsachthuebaokhongkhaosat", [
      "getDsThueBaoKhongKhoaMoGetter",
      "getDsThongTinThueBaoNoGetter",
      "getPageInfoGetter",
      "getPageInfoDsThongTinThueBaoNoGetter",
    ]),
    dataExcel() {
      return Object.values(this.getDsThueBaoKhongKhoaMoGetter);
    },
    thongBaoTimKiemMaTB() {
      return "Thuê bao " + this.maTB + " đã tồn tại trong danh sách!";
    },
    dsThuocTinh() {
    //   return this.fields2.map(x => x.fieldName)
      return ["RDS_ID","THUEBAO_ID","MA_TB","LOAITB_ID","TEN_TB","LOAIHINH_TB","TRANGTHAI","NGAY_CN","TEN_DVVT","NGAY_TH","LYDO","GHICHU","THANHTOAN_ID","DICHVUVT_ID","TRANGTHAI_TB","NGUOI_CN","NHANVIEN","DONVI","MA_TT"];
    },
    ngayTHInput() {
      return moment(this.from_date).format("DD/MM/YYYY HH:mm")
    },
    ngayTHDenInput() {
      return this.chk_to_date ? moment(this.to_date).format("DD/MM/YYYY HH:mm") : ""
    },
    lyDoInput() {
      return this.lyDo
    },
    TBaggregatesColumns () {
        return [{ field: 'MA_TB', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.TBcustomAggregate }]
    },
    TCaggregatesColumns () {
        return [{ field: 'MA_TB', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.TCcustomAggregate }]
    },
  },
  methods: {
    ...mapActions("cskhCommon", [
      "getLoaiDSThueBaoKhongKhaoSat",
      "getDanhMucVT",
    ]),
    ...mapActions("danhsachthuebaokhongkhaosat", [
      "getDSThueBaoKhongKhoaMo",
      "getDSThongTinThueBaoNo",
      "clearData",
      "getObjSearch",
      "deleteRecords",
    ]),
    deleteThueBao() {
      if (this.$refs.dgvThueBaoKhongKhoaMo.getSelectedRecords().length > 0) {
        this.$refs.xoaConfirm.show();
      } else {
        this.$root.toastError("Bạn chưa chọn thuê bao.");
      }
    },
    async onXoaConfirm() {
      let rdsList = this.$refs.dgvThueBaoKhongKhoaMo.getSelectedRecords()
      let postData = {
        rdsRId: rdsList.map((x) => x.RDS_ID).join(),
        mayHuy: this.$auth.getUserName(),
        nguoiHuy: this.$auth.getUserName(),
        ipHuy: "10.59.90.99",
      };
      try {
        this.loading(true);
        await DanhSachThueBaoKhongKhaoSatApi.xoaThueBaoKhongKhoaMo(
          this.axios,
          postData
        )
          .then(async (response) => {
            if (response.data.error_code === "BSS-00000000") {
              this.$root.toastSuccess("Xóa dữ liệu thành công!");
              this.clear()
              await this.getDsThueBao()
              // this.dsThueBaoKhongKhoaMo = []
              // this.dsThueBaoNo = []
              // this.clear()
              // await this.fetchData();
              } else {
              this.$root.toastError(respone.data.message);
            }
          })
          .catch((reject) => {
            this.$root.toastError(reject.respone.data.message);
          });
      } catch (ex) {
        console.log(ex);
      } finally {
        this.loading(false);
        this.$bvModal.hide("popup-xoaConfirm");
      }
    },
    setButton(kieu) {
      this.nhapMoiBtnEnable = false;
      // this.nhapExcelBtnEnable = false
      this.ghiLaiBtnEnable = false;
      this.xoaBtnEnable = false;
      this.huyBoBtnEnable = false;
      switch (kieu) {
        case -1:
          this.ghiLaiBtnEnable = true;
          this.huyBoBtnEnable = true;
          break;
        case 0:
          this.nhapMoiBtnEnable = true;
          break;
        case 1:
          this.ghiLaiBtnEnable = true;
          this.huyBoBtnEnable = true;
          this.clear();
          break;
        case 2:
          this.nhapMoiBtnEnable = true;
          this.xoaBtnEnable = true;
          break;
        case 3:
          this.nhapMoiBtnEnable = true;
          this.xoaBtnEnable = true;
          this.ghiLaiBtnEnable = true;
          this.huyBoBtnEnable = true;
          break;
      }
    },
    async capNhap() {
      if (!this.lyDo) {
        this.$root.toastError("Hãy nhập lý do");
        return;
      }
      if (this.to_date < this.from_date) {
        this.$root.toastError("Ngày TH không được lớn hơn Đến ngày!");
        return;
      }
      if (!this.nhapMoiBtnEnable) {
        //thêm mới
        this.$bvModal.show("popup-confirmThemMoi");
      } else {
        this.$bvModal.show("popup-confirmCapNhap");
      }
    },
    clear() {
      this.lyDo = "";
      this.ghiChu = "";
      this.tenTB = "";
      this.maTB = "";
      this.thueBaoId = 0;
      this.thanhToanId = 0;
      this.dgvThueBaoNo.dataSource = [];
    },
    async onMaTBKeyDown(event) {
      if (event.keyCode == 13) {
        this.loading(true);
        try {
          let postData = {
            tuNgay: this.chk_from_date
              ? moment(this.from_date).format("DD/MM/YYYY HH:mm")
              : "",
            denNgay: this.chk_to_date
              ? moment(this.to_date).format("DD/MM/YYYY HH:mm")
              : "",
            loaiDs: this.loaiDSThueBaoKhongKhaoSatSelected,
            maTb: this.maTB.trim(),
            pageNo: 1,
            pageSize: 1000,
          };
          let response = await this.$root.context.post(
            "/web-cskh/api/thue-bao-khong-khao-sat/khong-khoa-mo?pageNo=" +
              postData.pageNo +
              "&pageSize=" +
              postData.pageSize,
            postData
          );
           this.dsThueBaoKhongKhoaMo = [];
          if (response.error_code === "BSS-00000000") {
            let dsKoKhoaMoTmp = response.data.data || [];
            if (dsKoKhoaMoTmp.length > 0) {
              this.dsThueBaoKhongKhoaMo = dsKoKhoaMoTmp;
              this.$refs.gridDsThongTinThueBaoNo.selectAllItem = false
              this.$refs.gridThueBaoKhongKhoaMo.selectAllItem = false
              // this.$refs.gridThueBaoKhongKhoaMo.selectedValues = [];
              this.$nextTick(() => this.$refs.gridThueBaoKhongKhoaMo.doSelectItem(dsKoKhoaMoTmp[0].THUEBAO_ID))


              this.$bvModal.show("popup-thongbao");
            } else {
              this.thueBaoId = 0;
              this.thanhToanId = 0;
              this.ghiChu = "";
              this.tenTB = "";
            }
            this.HienThiThongTin(this.maTB.trim());
          }
          // await this.getDSThueBaoKhongKhoaMo(postData)
          //     .then(async (response) => {
          //         if ( this.getDsThueBaoKhongKhoaMoGetter.length == 0 ) {
          //             if (this.maTB) postData.maTb = "";
          //             await this.getDSThueBaoKhongKhoaMo(postData);
          //         }
          //         else if (this.getDsThueBaoKhongKhoaMoGetter.length > 0) {
          //             this.$bvModal.show("popup-thongbao");
          //             this.$refs.gridThueBaoKhongKhoaMo.setCurrentSelectedRow(
          //                 0
          //             );
          //             this.$refs.gridThueBaoKhongKhoaMo.selectedValues.push(
          //                 this.getDsThueBaoKhongKhoaMoGetter[0]
          //                     .THUEBAO_ID
          //             );
          //         }
          //     })
          //     .catch((response) =>
          //         this.$root.toastError( response.response.data.error || response.response.data.message )
          //     );
        } catch (error) {
          console.log(error);
        } finally {
          this.loading(false);
        }
      }
    },
    async onLoaiDsChange() {
      this.maTB = "";
      await this.fetchData();
    },
    async onLayDS() {
      this.maTB = "";
      await this.fetchData();

    },
    async HienThiDSTBKhoaMo(maTB) {
      try {
        this.dsThueBaoKhongKhoaMo = [];
        let postData = {
          tuNgay: this.chk_from_date
            ? moment(this.from_date).format("DD/MM/YYYY")
            : "",
          denNgay: this.chk_to_date
            ? moment(this.to_date).format("DD/MM/YYYY")
            : "",
          loaiDs: this.loaiDSThueBaoKhongKhaoSatSelected,
          maTb: maTB,
          pageNo: 1,
          pageSize: 10000,
        };

        let response = this.$root.context.get(
          "/web-cskh/api/thue-bao-khong-khao-sat/khong-khoa-mo?pageNo=" +
            postData.pageNo +
            "&pageSize=" +
            postData.pageSize,
          postData
        );
        if (response.error_code === "BSS-00000000") {
          this.dsThueBaoKhongKhoaMo = response.data.data || [];
        }
      } catch (e) {}
    },
    async HienThiThongTin(maTB) {
      try {
        let response = await this.$root.context.get(
          "/web-cskh/api/thue-bao-khong-khao-sat",
          { maThueBao: maTB }
        );
        if (response.error_code === "BSS-00000000") {
          let data = response.data.data || [];
          this.HienThiTT(data);
          await this.LayTBNo(this.thanhToanId);
        }
      } catch (e) {}
    },
    HienThiTT(data) {
      if (data.length > 0) {
        if (data.length == 1) {
          this.thueBaoId = data[0].THUEBAO_ID;
          this.thanhToanId = data[0].THANHTOAN_ID;
          this.tenTB = data[0].TEN_TB;
        } else {
          //mở popup frmTraCuu_ThueBao
          // frmTraCuu_ThueBao frm = new frmTraCuu_ThueBao(ds);
          // frm.ShowDialog();
          // if (frm.chapnhan)
          // {
          //     int idex = frm.index;
          //     thuebao_id = Convert.ToInt64(ds.Tables[0].Rows[idex]["thuebao_id"].ToString());
          //     thanhtoan_id = Convert.ToInt64(ds.Tables[0].Rows[idex]["thanhtoan_id"].ToString());
          //     txtTenTB.Text = ds.Tables[0].Rows[idex]["ten_tb"].ToString();
          // }
        }
      } else {
        this.thueBaoId = 0;
        this.thanhToanId = 0;
        this.tenTB = "";
      }
    },
    async LayTBNo(thanhToanId) {
      try {
        this.dsThueBaoNo = [];
        let response = await this.$root.context.post(
          "/web-cskh/api/thue-bao-khong-khao-sat/thue-bao-no",
          {
            thanhToanId: thanhToanId,
            kyHoaDon: this.kyCuoc,
            loaiDsId: this.loaiDSThueBaoKhongKhaoSatSelected,
          }
        );
        if (response.error === "BSS-00000000") {
          this.dsThueBaoNo = response.data.data || [];
          this.$refs.gridDsThongTinThueBaoNo.selectedValues = [];
          this.dsThueBaoNo.forEach((record) => {
            record.TONGNO_TXT = currency(record.TONGNO)
            // if (record.SELECTED != 1)
            //   this.$nextTick(() => this.$refs.gridDsThongTinThueBaoNo.doSelectItem(record.THUEBAO_ID))
              // this.$refs.gridDsThongTinThueBaoNo.selectedValues.push(
              //   record.THUEBAO_ID
              // );
          });
        }
      } catch (e) {
        console.log(e);
      }
    },
    async fetchData() {
      this.tenTB = "";
      this.lyDo = "";
      this.ghiChu = "";
      this.loading(true);
      try {
        let postData = {
          tuNgay: this.chk_from_date
            ? moment(this.from_date).format("DD/MM/YYYY HH:mm")
            : "",
          denNgay: this.chk_to_date
            ? moment(this.to_date).format("DD/MM/YYYY HH:mm")
            : "",
          loaiDs: this.loaiDSThueBaoKhongKhaoSatSelected,
          maTb: "",
          pageNo: 1,
          pageSize: 10000,
        };

        let response = await this.$root.context.post(
          "/web-cskh/api/thue-bao-khong-khao-sat/khong-khoa-mo?pageNo=" +
            postData.pageNo +
            "&pageSize=" +
            postData.pageSize,
          postData
        );
        this.dsThueBaoKhongKhoaMo = [];
        if (response.error_code === "BSS-00000000") {
          this.$refs.gridDsThongTinThueBaoNo.selectAllItem = false
          this.$refs.gridThueBaoKhongKhoaMo.selectAllItem = false
          this.dsThueBaoKhongKhoaMo = response.data.data || [];
        }

        if (this.dsThueBaoKhongKhoaMo.length > 0) {
          this.$refs.gridThueBaoKhongKhoaMo.setCurrentSelectedRow(0);
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.loading(false);
      }
    },
    async fetchDataThongTinThueBaoNo(record) {
      try {
        let postData = {
          thanhToanId: record.THANHTOAN_ID,
          kyHoaDon: this.kyCuoc,
          loaiDsId: this.loaiDSThueBaoKhongKhaoSatSelected,
          pageNo: 1,
          pageSize: 10000,
        };
        await this.getDSThongTinThueBaoNo(postData);
      } catch (error) {
        console.log(error);
      }
    },
    handleKyHoaDon(e) {
      this.kyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no; //"20210101"; //
    },
    async fetchDsThueBaoKhongKhoaMo(pageNo, pageSize) {
      this.loading(true);
      let response = null;
      try {
        let postData = {
          tuNgay: this.chk_from_date
            ? moment(this.from_date).format("DD/MM/YYYY")
            : "",
          denNgay: this.chk_to_date
            ? moment(this.to_date).format("DD/MM/YYYY")
            : "",
          loaiDs: this.loaiDSThueBaoKhongKhaoSatSelected,
          maTb: this.maTB,
          pageNo: this.pageNo,
          pageSize: this.getPageInfoGetter.maxSize,
        };
        response = await DanhSachThueBaoKhongKhaoSatApi.getDSThueBaoKhongKhoaMo(
          this.axios,
          pageNo,
          pageSize,
          postData
        );
      } catch (e) {
      } finally {
        this.loading(false);
      }

      return response;
      //console.log(response)
    },
    async fetchDsThongTinThueBaoNo(pageNo, pageSize) {
      this.loading(true);
      let response = null;
      try {
        let postData = {
          thanhToanId: this.record.THANHTOAN_ID,
          kyHoaDon: this.thongtinKyHoaDon.kyHoaDonSelected,
          loaiDsId: this.loaiDSThueBaoKhongKhaoSatSelected,
          pageNo: this.pageNoDSThongTinThueBaoNo,
          pageSize: this.getPageInfoDsThongTinThueBaoNoGetter.maxSize,
        };
        response = await DanhSachThueBaoKhongKhaoSatApi.getDSThongTinThueBaoNo(
          this.axios,
          pageNo,
          pageSize,
          postData
        );
      } catch (e) {
        console.log(e);
      } finally {
        this.loading(false);
      }

      return response;
    },
    async getTTThueBao(maThueBao) {
      let dsTT = []
      try {
        this.$root.showLoading(true)
        let ttThueBaoResponse = await this.$root.context.get("/web-cskh/api/thue-bao-khong-khao-sat", {maThueBao: maThueBao})
        if (ttThueBaoResponse.error_code == API_SUCCESS) {
          dsTT = ttThueBaoResponse.data.data || []
        }
      }
      catch (e) {
        console.log(e)
      }
      finally{
        this.$root.showLoading(false)
      }
      return dsTT
    },
    async onThemMoiConfirm() {
      try {
        console.log("0");
        if (this.dgvThueBaoNo.dataSource.length <= 0) {
          let ttThueBao = await this.getTTThueBao(this.maTB)
          if (ttThueBao.length <= 0) {
            this.$root.toastError("Không tồn tại thuê bao")
          }

          let record = {
            thueBaoId: ttThueBao[0].THUEBAO_ID,
            thanhToanId: ttThueBao[0].THANHTOAN_ID,
            dichVuVtId: ttThueBao[0].DICHVUVT_ID,
            loaiTbId: ttThueBao[0].LOAITB_ID,
            loaiDs: this.loaiDSThueBaoKhongKhaoSatSelected,
            ngayTh: this.ngayTHInput,
            ngayThDen: this.ngayTHDenInput,
            mayCn: this.$auth.getUserName(),
            nhanVienId: this.$auth.getNhanVienID(),
            lyDo: this.lyDo,
            ghiChu: this.ghiChu,
            nguoiCn: this.$auth.getUserName(),
            ipCn: "",
          };
          let response = await DanhSachThueBaoKhongKhaoSatApi.capNhapThueBaoKhongKhoaMo( this.axios, record );
          if (response.data.error_code == "BSS-00000000") {
            this.$root.toastSuccess("Cập nhật dữ liệu viễn thông tỉnh thành công!");
            await this.getDsThueBao();
          }
          else {
            this.$root.toastError("Cập nhập thất bại " + response.data.message);
          }
        } else {
          let dsThueBaoNoSelected = this.$refs.dgvThueBaoNo.getSelectedRecords()
          if (dsThueBaoNoSelected.length <= 0) {
            this.$root.toastError("Bạn chưa chọn thuê bao!")
            return
          }
          dsThueBaoNoSelected.forEach(
            async (obj) => {
              console.log(obj);
              try {
                let lyDo = obj.hasOwnProperty("LYDO") ? obj.LYDO : "";
                let record = {
                  thueBaoId: obj.THUEBAO_ID,
                  thanhToanId: obj.THANHTOAN_ID,
                  dichVuVtId: obj.DICHVUVT_ID,
                  loaiTbId: obj.LOAITB_ID,
                  loaiDs: this.loaiDSThueBaoKhongKhaoSatSelected,
                  ngayTh: this.ngayTHInput,
                  ngayThDen: this.ngayTHDenInput,
                  mayCn: this.$auth.getUserName(),
                  nhanVienId: this.$auth.getNhanVienID(),
                  lyDo: this.lyDo ? this.lyDo : lyDo,
                  ghiChu: this.ghiChu,
                  nguoiCn: this.$auth.getUserName(),
                  ipCn: "",
                };
                let response = await DanhSachThueBaoKhongKhaoSatApi.capNhapThueBaoKhongKhoaMo( this.axios, record );
                if (response.data.error_code == "BSS-00000000") {
                  this.$root.toastSuccess("Cập nhật dữ liệu viễn thông tỉnh thành công!");
                  await this.getDsThueBao();
                } else this.$root.toastError("Cập nhập thất bại");
              } catch (e) {
                console.log(e);
                this.$root.toastError("Cập nhập thất bại");
              }
            }
          );
        }
      } catch (e) {
        this.$root.toastError("Cập nhập thất bại");
      }
    },
    async onCapNhapConfirm() {
      try {
        this.$root.showLoading(true)
        let dsKhongKhoaMoSelected = this.$refs.dgvThueBaoKhongKhoaMo.getSelectedRecords()
        if (dsKhongKhoaMoSelected.length > 0) {
          dsKhongKhoaMoSelected.forEach(async (obj) => {
            try {
              let lyDo = obj.hasOwnProperty("LYDO") ? obj.LYDO : "";
              let record = {
                thueBaoId: obj.THUEBAO_ID,
                thanhToanId: obj.THANHTOAN_ID,
                dichVuVtId: obj.DICHVUVT_ID,
                loaiTbId: obj.LOAITB_ID,
                loaiDs: this.loaiDSThueBaoKhongKhaoSatSelected,
                ngayTh: this.ngayTHInput,
                ngayThDen: this.ngayTHDenInput,
                mayCn: this.$auth.getUserName(),
                nhanVienId: this.$auth.getNhanVienID(),
                lyDo: this.lyDo ? this.lyDo : lyDo,
                ghiChu: this.ghiChu,
                nguoiCn: this.$auth.getUserName(),
                ipCn: "",
              };
              let response = await DanhSachThueBaoKhongKhaoSatApi.capNhapThueBaoKhongKhoaMo( this.axios, record );

              if (response.data.error_code == "BSS-00000000") {
                this.$root.toastSuccess("Cập nhập thành công!");
                await this.getDsThueBao();
              } else {
                this.$root.toastError("Cập nhập thất bại");
              }
            } catch (e) {
              console.log(e);
              this.$root.toastError("Cập nhập thất bại");
            }
          });
        } else {
          this.$root.toastError("Bạn chưa chọn thuê bao!");
          return;
        }
      }
      catch (e) {
      }
      finally {
        this.$root.showLoading(false)
      }

    },
    ShowSearchAccount() {
      this.popupComponent = SearchAccount;
      this.Popup("popupComponents");
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
    },
    searchAccountClosed(val) {
      if(val){
        this.maTB = val.ma_tb || "";
        this.onKeyPress();
      }
      this.ClosePopup("popupComponents");
    },
    onXuatFileClicked() {
        if (this.dgvThueBaoKhongKhoaMo.dataSource.length <= 0) {
            this.$root.toastError("Không có dữ liệu để xuất file!")
            return
        }
        this.$bvModal.show('modal-xuat-dulieu')
    },


    async dgvThueBaoKhongKhoaMo_PageChangeHandler(e) {
      this.dgvThueBaoKhongKhoaMo.pageInfo.page = e.pageIndex;
      this.dgvThueBaoKhongKhoaMo.pageInfo.maxSize = e.pageSize;
      if (this.dgvThueBaoKhongKhoaMo.enablePagingServer) {
        await this.getDsThueBaoKhongKhaoMo()
      }
    },
    async dgvThueBaoNo_PageChangeHandler(e) {
      this.dgvThueBaoNo.pageInfo.page = e.pageIndex;
      this.dgvThueBaoNo.pageInfo.maxSize = e.pageSize;
      if (this.dgvThueBaoNo.enablePagingServer) {
        await this.getDsThueBaoNo()
      }
    },
    dgvThueBaoKhongKhoaMo_clearPageInfor() {
      this.dgvThueBaoKhongKhoaMo.pageInfo = {
          page: 0,
          maxSize: 10,
          totalElement: 0,
          totalPages: 0,
          currentRow: 0,
          sort: null,
          propertiesSort: null
        }
    },
    dgvThueBaoNo_clearPageInfor() {
      this.dgvThueBaoNo.pageInfo = {
          page: 0,
          maxSize: 10,
          totalElement: 0,
          totalPages: 0,
          currentRow: 0,
          sort: null,
          propertiesSort: null
        }
    },
    async onKeyPress() {
      let maThueBao = this.maTB.trim()
      if (maThueBao != '') {
        let tuNgay = this.chk_from_date ? moment(this.from_date).format("DD/MM/YYYY HH:mm") : ""
        let denNgay = this.chk_to_date ? moment(this.to_date).format("DD/MM/YYYY HH:mm") : ""
        let pageNo = this.dgvThueBaoKhongKhoaMo.enablePagingServer ? this.dgvThueBaoKhongKhoaMo.pageInfo.page : 0
        let pageSize = this.dgvThueBaoKhongKhoaMo.enablePagingServer ? this.dgvThueBaoKhongKhoaMo.pageInfo.maxSize : 10000
        let outputAPI = await this.getDsThueBaoKhongKhaoMo(maThueBao, tuNgay, denNgay, pageNo, pageSize)
        if (outputAPI.data.length > 0) {
          this.dgvThueBaoKhongKhoaMo.dataSource = outputAPI.data
          this.dgvThueBaoKhongKhoaMo.pageInfo = outputAPI.pageInfo
          this.$bvModal.show("popup-thongbao");
        } else {
          await this.getThueBaoKhongKhaoSat()
        }
      }
      else {
        this.$toast.error('Vui lòng nhập số máy/Acc để tra cứu!')
      }
    },
    async getThueBaoKhongKhaoSat(){
        try {
            this.loading(true);
            let maThueBao = this.maTB.trim()
            let response = await this.$root.context.get(`/web-cskh/api/thue-bao-khong-khao-sat?maThueBao=${maThueBao}`);
            if(response.error_code === API_SUCCESS && response.data.data.length > 0){
                const result = response.data.data[0]
                this.thueBaoId = result.THUEBAO_ID;
                this.thanhToanId = result.THANHTOAN_ID;
                this.tenTB = result.TEN_TB;
                this.maTB = result.MA_TB

                await this.getDsThueBaoNo();
            } else {
                this.$toast.error('Không tìm thấy thông tin thuê bao!')
                this.clear()
                this.dgvThueBaoKhongKhoaMo.dataSource = []
            }
        } catch (error) {

        } finally{
            this.loading(false)
        }
    },
    async getDsThueBao() {
      let tuNgay = this.chk_from_date ? moment(this.from_date).format("DD/MM/YYYY HH:mm") : ""
      let denNgay = this.chk_to_date ? moment(this.to_date).format("DD/MM/YYYY HH:mm") : ""
      let pageNo = this.dgvThueBaoKhongKhoaMo.enablePagingServer ? this.dgvThueBaoKhongKhoaMo.pageInfo.page : 0
      let pageSize = this.dgvThueBaoKhongKhoaMo.enablePagingServer ? this.dgvThueBaoKhongKhoaMo.pageInfo.maxSize : 10000
      this.maTB = "";
      this.tenTB = "";
      this.lyDo = "";
      this.ghiChu = "";

      let outputAPI = await this.getDsThueBaoKhongKhaoMo('', tuNgay, denNgay, pageNo, pageSize)
      this.dgvThueBaoKhongKhoaMo.dataSource = outputAPI.data
      this.dgvThueBaoKhongKhoaMo.pageInfo = outputAPI.pageInfo
      if (this.dgvThueBaoKhongKhoaMo.dataSource.length <= 0) {
        this.setButton(1);
        this.thueBaoId = 0;
        this.thanhToanId = 0;
        this.ghiChu = "";
        this.tenTB = "";
      } else {
        this.dgvThueBaoKhongKhoaMo_Click({rowData:this.dgvThueBaoKhongKhoaMo.dataSource[0]})
      }
    },
    async getDsThueBaoKhongKhaoMo(maThueBao, tuNgay, denNgay, pageNo, pageSize) {
      let output = {
        data: [],
        pageInfo: {},
      }
      this.loading(true);
      try {
        var tuNgay = this.chk_from_date ? moment(this.from_date).format("DD/MM/YYYY HH:mm") : ""
        var denNgay = this.chk_to_date ? moment(this.to_date).format("DD/MM/YYYY HH:mm") : ""
        let postData = {
          tuNgay: tuNgay,
          denNgay: denNgay,
          loaiDs: this.loaiDSThueBaoKhongKhaoSatSelected,
          maTb: maThueBao,
        };

        let response = await this.$root.context.post( "/web-cskh/api/thue-bao-khong-khao-sat/khong-khoa-mo?pageNo=" + pageNo + "&pageSize=" + pageSize, postData );

        if (response.error_code === API_SUCCESS) {
          output.data = response.data.data || [];
          output.pageInfo = {
            page: 0,
            maxSize: 10,
            totalElement: 0,
            totalPages: 0,
            currentRow: 0,
            sort: null,
            propertiesSort: null
          }
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.loading(false);
      }
      return output
    },
    async getDsThueBaoNo() {
      this.loading(true);
      let response = null;
      try {
        this.dgvThueBaoNo.dataSource = []
        let postData = {
          thanhToanId: this.thanhToanId,
          kyHoaDon: this.kyCuoc,
          loaiDsId: this.loaiDSThueBaoKhongKhaoSatSelected,
          pageNo: this.dgvThueBaoNo.enablePagingServer ? this.dgvThueBaoNo.pageInfo.page : 0,
          pageSize: this.dgvThueBaoNo.enablePagingServer ? this.dgvThueBaoNo.pageInfo.maxSize : 10000,
        };

        let response = await this.$root.context.post("/web-cskh/api/thue-bao-khong-khao-sat/thue-bao-no?pageNo=" + postData.pageNo + "&pageSize=" + postData.pageSize, postData)
        if (response.error_code == API_SUCCESS) {
          this.dgvThueBaoNo.dataSource = response.data.data || []
          this.dgvThueBaoNo.pageInfo = {
            page : response.data.page,
            maxSize: response.data.maxSize,
            totalElement: response.data.totalElement,
            currentRow: 0
          }

          this.dgvThueBaoNo.dataSource.forEach((record) => {
            record.TONGNO_TXT = currency(record.TONGNO)
          });
        }
      } catch (e) {
        console.log(e);
      } finally {
        this.loading(false);
      }

      return response;
    },
    TCcustomAggregate() {
        return this.dgvThueBaoKhongKhoaMo.checked.length + '/' + this.dgvThueBaoKhongKhoaMo.dataSource.length;
    },

    TBcustomAggregate() {
        return this.dgvThueBaoNo.checked.length + '/' + this.dgvThueBaoNo.dataSource.length;
    },

    async dgvThueBaoKhongKhoaMo_RowSelected(args) {
      this.lyDo = args.data.hasOwnProperty("LYDO") ? args.data.LYDO : "";
      this.ghiChu = args.data.GHICHU || "";
      this.tenTB = args.data.TEN_TB || "";
      this.maTB = args.data.MA_TB || "";
      this.thueBaoId = args.data.THUEBAO_ID;
      this.thanhToanId = args.data.THANHTOAN_ID;
      this.record = args.data;

      await this.getDsThueBaoNo();
      this.setButton(3);
    },
    async dgvThueBaoKhongKhoaMo_Click(data) {
      let currentObj = data.rowData;
      this.lyDo = currentObj.hasOwnProperty("LYDO") ? currentObj.LYDO : "";
      this.ghiChu = currentObj.GHICHU || "";
      this.tenTB = currentObj.TEN_TB || "";
      this.maTB = currentObj.MA_TB || "";
      this.thueBaoId = currentObj.THUEBAO_ID;
      this.thanhToanId = currentObj.THANHTOAN_ID;
      this.record = currentObj;

      await this.getDsThueBaoNo();
      this.setButton(3);
    },
    dgvThueBaoKhongKhoaMo_ItemsChecked(items) {
        this.dgvThueBaoKhongKhoaMo.checked = [];
        items.forEach(item => {
            let tb = this.dgvThueBaoKhongKhoaMo.dataSource.find(e => e.THUEBAO_ID === item);
            if (tb !== undefined)
                this.dgvThueBaoKhongKhoaMo.checked.push(tb);
        })
        this.$refs.dgvThueBaoKhongKhoaMo.refreshAggregates();
    },

    dgvThueBaoNo_dDataBound() {
      let lstSelected = this.dgvThueBaoNo.dataSource.filter(x => x.SELECTED != 1)
      let lstIndex = []
      lstSelected.forEach((record) => {
        lstIndex.push(this.dgvThueBaoNo.dataSource.map(x => x.THUEBAO_ID).indexOf(record.THUEBAO_ID))
      })
      this.$refs.dgvThueBaoNo.grid.selectRows(lstIndex)
    },
    onOpenDocFileThemPopup() {
      this.$refs.frmDsKhoaMo_DocFileThem.loaiDs = this.loaiDSThueBaoKhongKhaoSatSelected
      this.$refs.frmDsKhoaMo_DocFileThem.ngayTH = this.ngayTHInput
      this.$refs.frmDsKhoaMo_DocFileThem.ngayTHDen = this.ngayTHDenInput
      this.$refs.frmDsKhoaMo_DocFileThem.lyDo = this.lyDoInput
      this.$refs.frmDsKhoaMo_DocFileThem.ngayTHDenCheck = this.from_date
      this.$refs.frmDsKhoaMo_DocFileThem.openDialog()
    },
    onOpenDocFileXoaPopup() {
      this.$refs.frmDsKhoaMo_DocFileXoa.loaiDs = this.loaiDSThueBaoKhongKhaoSatSelected
      this.$refs.frmDsKhoaMo_DocFileXoa.openDialog()
    }
  },

  watch: {},
};
</script>
