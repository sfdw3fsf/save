<template src="./DanhSachKhachHangLon.html"></template>
<style scoped src="./DanhSachKhachHangLon.scss"></style>
<script>
import CustomDataGrid from "../utils/CustomDataGrid.vue";
import KyCuoc from "../../QLTN/components/KyCuoc.vue";
import { mapActions, mapState, mapGetters } from "vuex";
import moment from "moment";
import { currency } from "@/filters/currency"
import ThemKhachHangLonLe from "./popup/ThemKhachHangLonLe";
import ChiTietCacLanTaoDuLieu from "./popup/ChiTietCacLanTaoDuLieu";
import DanhSachMaKH from "./popup/DanhSachMaKH";
import API from "../api/DanhSachKhachHangLon";
import downloadexcel from "vue-json-excel";
import xlsx from "xlsx";
import TraCuuKhachHangLonApi from "../api/TraCuuKhachHangLonApi";
import ModalXuatDuLieu from "../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu";
import { CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns";
import BssRequiredMarker from "@/components/BssRequiredMarker";
export default {
  provide: {
    multiselect: [CheckBoxSelection],
  },
  components: {
    BssRequiredMarker,
    appKyCuoc: KyCuoc,
    appThemKhachHangLonLe: ThemKhachHangLonLe,
    appChiTiet: ChiTietCacLanTaoDuLieu,
    appDSMaKH: DanhSachMaKH,
    downloadexcel,
    datagrid: CustomDataGrid,
    ModalXuatDuLieu,
  },
  computed: {
    ...mapState("danhsachkhachhanglon", [
      "dsQuan",
      "dsLoaiKHL",
      "dsLoaiKH",
      "dsLanTao",
      "dsChuKyNo",
      "dsKhachHang",
      "dsChiTietKhachHang",
      "dataExcel",
      "totalElements",
    ]),
    ...mapGetters("danhsachkhachhanglon", ["getdsKhachHangGetter"]),
  },
  async mounted() {
    try {
      this.$root.loading(true);
      await this.fetchData();
      // this.clearData();
      this.tuKHD = this.dsChuKyNo[0].CHUKYNO;
      this.denKHD = this.dsChuKyNo[0].CHUKYNO;
      this.quanId = this.dsQuan.map(x => x.QUAN_ID);
      this.loaiKhId = this.dsLoaiKH.map(x => x.LOAIKH_ID)
      this.khlId = this.dsLoaiKHL.map(x => x.KHLON_ID)
      this.dataSelect = {
        nguoiDungId: parseInt(this.$auth.getNguoiDungID()),
        dsLoaiKhId: this.loaiKhId.toString(),
        dsKhlId: this.khlId.toString(),
        dsQuanId: this.quanId.toString(),
      };
    } catch(error) {

    } finally {
      this.$root.loading(false);
    }

  },
  data() {
    return {
      fieldsDSKhachHang: [
        {
          fieldName: "MA_KH",
          headerText: "Mã KH",
          allowFiltering: true,
          allowSorting: false,
          cssClass: 'text-bold',
          width: 130,
        },
        {
          fieldName: "TEN_KH",
          headerText: "Tên khách hàng",
          allowFiltering: true,
          allowSorting: false,
          cssClass: "text-bold",
          width: 130,
        },
        {
          fieldName: "DIACHI_KH",
          headerText: "Địa chỉ KH",
          allowFiltering: true,
          allowSorting: false,
          cssClass: "text-bold",
          width: 130,
        },
        {
          fieldName: "BINHQUAN_DT",
          headerText: "Bình quân DT",
          allowFiltering: true,
          allowSorting: false,
          cssClass: "text-red",
          width: 130,
          format: "N0",
          type: "number",
        },
      ],
      fieldsDSLanTao: [
        {
          fieldName: "NGAY_CN",
          headerText: "Ngày tạo",
          allowFiltering: true,
          allowSorting: false,
          width: 130,
        },
        {
          fieldName: "TEN_LKHL",
          headerText: "Loại KHL",
          allowFiltering: true,
          allowSorting: false,
          width: 130,
        },
        {
          fieldName: "SOLUONG",
          headerText: "Số lượng",
          allowFiltering: true,
          allowSorting: false,
          width: 130,
          format: "N0",
          type: "number",
        },
      ],
      isDisableQuan: false,
      kyCuoc: "",
      quanId: [],
      khlId: [],
      loaiKhId: [],
      dataSelect: {
        nguoiDungId: parseInt(this.$auth.getNguoiDungID()),
        dsLoaiKhId: "",
        dsKhlId: "",
        dsQuanId: "",
      },
      selectFields: { text: "TEN_QUAN", value: "QUAN_ID" },
      isShowModalXoaLanTao: false,
      isShowModalXoa: false,
      isShowModalTao: false,
      tuKHD: "",
      denKHD: "",
      pDanhSach: [],
      dataDelete: {},
      dsFile: {},
      dsThuocTinh: [],
      dsMaKHLoi: [],
    };
  },
  methods: {
    ...mapActions("danhsachkhachhanglon", [
      "getdsQuan",
      "getdsLoaiKHL",
      "getdsLoaiKH",
      "getdsLanTao",
      "getdsChuKyNo",
      "getdsKhachHang",
      "clearData",
      "chiTietdsLanTao",
      "taodsKHL",
    ]),
    async fetchData() {
      await this.getdsQuan();
      await this.getdsLoaiKHL();
      await this.getdsLoaiKH();
      await this.getdsChuKyNo();
      await this.getdsLanTao(this.kyCuoc);
    },
    DSKhachHang_gridChanged(args) {
      this.layDS(args.pageIndex + 1, args.pageSize);
    },
    async handleKyHoaDon(e) {
      if (e.p_chuky_no) {
        this.kyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no;
      } else {
        this.kyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + "01";
      }
      console.log("Kỳ cước: ", this.kyCuoc);
      await this.getdsLanTao(this.kyCuoc);
      // console.log("DS lần tạo: ", this.dsLanTao)
    },
    isNullOrEmpty(value) {
      return value === null || value === "";
    },
    filterData(){
      if (this.quanId.length !== 0 && this.isDisableQuan && this.quanId.length != this.dsQuan.length) {
        this.dataSelect.dsQuanId = this.quanId.toString();
      } else {
        this.dataSelect.dsQuanId = "";
      }
      if (this.loaiKhId.length !== 0 && this.loaiKhId.length != this.dsLoaiKH.length) {
        this.dataSelect.dsLoaiKhId = this.loaiKhId.toString();
      } else {
        this.dataSelect.dsLoaiKhId = "";
      }
      if (this.khlId.length !== 0 && this.khlId.length != this.dsLoaiKHL.length) {
        this.dataSelect.dsKhlId = this.khlId.toString();
      } else {
        this.dataSelect.dsKhlId = "";
      }
    },
    async layDS(pageNum, pageSize) {
      this.$root.loading(true);
      this.clearData();
      this.filterData();
      try {
        this.dataSelect["pageNum"] = pageNum ? pageNum : 1;
        this.dataSelect["pageSize"] = pageSize
          ? pageSize
          : this.$refs.gridDSKhachHang.pageSize;
        this.dataSelect["totalRow"] = 1;
        await this.getdsKhachHang(this.dataSelect);
      } catch (error) {
        this.$root.toastError("Đã có lỗi xảy ra");
      } finally {
        this.$root.loading(false);
      }
      // await this.getdsKhachHang(this.dataSelect)
    //   console.log("danh sách KH: ", this.getdsKhachHangGetter);
    //   console.log("người dùng id: ", this.dataSelect.nguoiDungId);
    //   console.log("phân vùng id: ", this.$auth.getPhanVungID());
    //   console.log("data select: ", this.dataSelect);
      this.$root.loading(false);
    },
    kiemTraDuLieu() {
      if (!this.tuKHD) {
        this.$toast.error("Hãy chọn kỳ hóa đơn từ!");
        return false;
      }
      if (!this.denKHD) {
        this.$toast.error("Hãy chọn kỳ hóa đơn đến!");
        return false;
      }
      if (Number.parseInt(this.tuKHD) > Number.parseInt(this.denKHD)) {
        this.$toast.error("Kỳ hóa đơn từ phải nhỏ hơn kỳ hóa đơn đến!");
        return false;
      }
      if (this.isNullOrEmpty(this.loaiKhId)) {
        this.$toast.error("Hãy chọn Loại khách hàng!");
        return false;
      }
      if (this.isNullOrEmpty(this.khlId)) {
        this.$toast.error("Hãy chọn Loại khách hàng!");
        return false;
      }
      return true;
    },
    async taoDS() {
      if (!this.kiemTraDuLieu()) {
        return;
      }
      this.$root.loading(true);
      this.filterData();
      let data = {
        tuKhd: this.tuKHD,
        denKhd: this.denKHD,
        dsKhlDtoIn: this.dataSelect,
        nguoiCn: this.$auth.getUserName(),
      };
      console.log(data);

      try {
        var res = await TraCuuKhachHangLonApi.taoDanhSachKHL(this.axios, data);
        if (res.data.error != "200") {
          this.$toast.error("Xảy ra lỗi khi tạo ds khách hàng lớn!");
          this.$root.loading(false);
          return;
        } else {
          this.$toast.success("Tạo danh sách khách hàng lớn thành công");
          this.dataSelect["pageNum"] = 1;
          this.dataSelect["pageSize"] = this.$refs.gridDSKhachHang.pageSize;
          this.dataSelect["totalRow"] = 1;
          await this.getdsKhachHang(this.dataSelect);
          this.$root.loading(false);
          return;
        }
      } catch (error) {
        this.$toast.error("Xảy ra lỗi khi tạo ds khách hàng lớn!");
        this.$root.loading(false);
        return;
      }
    },
    ghiDS() {
      this.$root.loading(true);
      this.filterData();
      let data = {
        kyHoaDon: this.kyCuoc,
        dsKhlDtoIn: this.dataSelect,
        mayCn: "test_pc",
        nguoiCn: this.$auth.getUserName(),
      };
      API.ghiDanhSachKHL(this.axios, data)
        .then(async (response) => {
          if (response.data.error_code === "BSS-00000000") {
            if (response.data.data) {
              // await this.getdsLanTao(data.kyHoaDon)
              this.$root.toastSuccess(
                "Lưu danh sách khách hàng lớn thành công"
              );
              this.clearData();
              await this.getdsLanTao(this.kyCuoc);
            } else {
              this.$root.toastError("Lưu danh sách khách hàng lớn thất bại");
            }
          } else {
            this.$root.toastError(response.data.message);
          }
          this.$root.loading(false);
        })
        .catch((reject) => {
          if (
            reject.response &&
            reject.response.data &&
            reject.response.data.message
          )
            this.$root.toastError(reject.response.data.message);
          else this.$root.toastError("Có lỗi xảy ra!");
          this.$root.loading(false);
        });
      console.log("data: ", data);
    },
    xoaDS() {
      this.$root.loading(true);
      this.filterData();
      console.log("data xoá: ", this.dataSelect);
      API.xoaDanhSachKHL(this.axios, this.dataSelect)
        .then(async (response) => {
          if (response.data.error_code === "BSS-00000000") {
            if (response.data.data) {
              this.clearData();
              this.$root.toastSuccess(
                "Xoá danh sách khách hàng lớn thành công"
              );
              await this.getdsLanTao(this.kyCuoc);
            } else {
              this.$root.toastError("Xoá danh sách khách hàng lớn thất bại");
            }
          } else {
            this.$root.toastError(response.data.message);
          }
          this.$root.loading(false);
        })
        .catch((reject) => {
          if (
            reject.response &&
            reject.response.data &&
            reject.response.data.message
          )
            this.$root.toastError(reject.response.data.message);
          else this.$root.toastError("Có lỗi xảy ra!");

          this.$root.loading(false);
        });
    },
    async doubleClicked(rowIndex) {
      this.$root.loading(true);
      let item = this.$refs.gridDSLanTao.dataItems[rowIndex];
      let data = {
        pageNo: 0,
        pageSize: 10000,
        data: {
          ngayCn: item.NGAY_CN,
          khLonId: item.KHLON_ID,
        },
      };
      await this.chiTietdsLanTao(data);
      this.$root.loading(false);
      console.log("ds chi tiết: ", this.dsChiTietKhachHang);
      console.log("chi tiết: ", data);

      this.$bvModal.show("popup-chiTietCacLanTaoDuLieu");
    },
    rowClick(rowIndex) {
      let item = this.$refs.gridDSLanTao.dataItems[rowIndex];
      this.dataDelete = {
        kyHoaDon: this.kyCuoc,
        dsKhlId: item.KHLON_ID,
        dsLoaiKhId: this.dataSelect.dsLoaiKhId,
        dsQuanId: null,
      };
    },
    xoaDanhSachLanTao() {
      this.$root.loading(true);
      console.log("zoasdasd ");
      API.xoaDanhSachLanTao(this.axios, this.dataDelete)
        .then(async (response) => {
          if (response.data.error_code === "BSS-00000000") {
            if (response.data.data) {
              this.$root.toastSuccess("Xoá danh sách lần tạo thành công");
              await this.getdsLanTao(this.kyCuoc);
            } else {
              this.$root.toastError("Xoá danh sách lần tạo thất bại");
            }
          } else {
            this.$root.toastError(response.data.message);
          }
          this.$root.loading(false);
        })
        .catch((reject) => {
          if (
            reject.response &&
            reject.response.data &&
            reject.response.data.message
          )
            this.$root.toastError(reject.response.data.message);
          else this.$root.toastError("Có lỗi xảy ra!");

          this.$root.loading(false);
        });
    },
    showModalXoa() {
      if (this.dsKhachHang.length <= 0) {
        this.$root.toastError("Hãy lấy danh sách khách hàng lớn để xóa!");
        return;
      }
      this.isShowModalXoa = !this.isShowModalXoa;
    },
    showModalXoaLanTao() {
      this.isShowModalXoaLanTao = !this.isShowModalXoaLanTao;
    },
    showModalTao() {
      if (this.dsKhachHang.length <= 0) {
        this.$root.toastError(
          "Hãy tạo danh sách khách hàng lớn trước khi ghi lại!"
        );
        return;
      }
      this.isShowModalTao = !this.isShowModalTao;
    },
    async importExcel(e) {
      const files = e.target.files;
      if (!files.length) {
        return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$root.toastWarning("File excel không đúng định dạng!");
      }
      // this.readFile1(files);
      await this.readFile(files);
    },
    async readFile(file) {
      this.$root.loading(true);
      const fileReader = new FileReader();
      fileReader.onload = async (ev) => {
        try {
          const data = ev.target.result;
          const XLSX = xlsx;
          const workbook = XLSX.read(data, {
            type: "binary",
          });
          const wsname = workbook.SheetNames[0];
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
          const excellist = [];
          let dsDanhSach = [];

          for (var i = 0; i < ws.length; i++) {
            excellist.push(ws[i]);
            dsDanhSach.push(ws[i].MA_KH);
          }

          if (!excellist[0].hasOwnProperty("MA_KH")) {
            this.$root.toastError("File cần có chính xác 1 cột MA_KH!");
            this.$root.loading(false);
            return;
          }

          //check MA_KH trùng
          let sortedarr = dsDanhSach.slice().sort();
          for (let i = 0; i < sortedarr.length - 1; i++) {
            if (sortedarr[i + 1] == sortedarr[i]) {
              this.$root.toastError("MA_KH bị lặp lại!");
              this.$root.loading(false);
              return;
            }
          }

          //get dsKHLonId
          let dsKHLId = [];
          let resultDSKHLId = "";
          await Promise.all(
            dsDanhSach.map(async (element) => {
              await TraCuuKhachHangLonApi.traCuuKHTheoMa(this.axios, element)
                .then((response) => {
                  if (response.data.error_code === "BSS-00000000") {
                    if (response.data.data[0].KHLON_ID != 0) {
                      dsKHLId.push(response.data.data[0].KHLON_ID);
                    }
                  }
                  resultDSKHLId = dsKHLId.toString();
                })
                .catch((error) => {
                  this.$root.loading(false);
                });
              resultDSKHLId = dsKHLId.toString();
            })
          );

          let result = dsDanhSach.toString();
          this.pDanhSach = result;

          //this.fieldsRender = this.fields

          let dataDocFile = {
            reset: 1,
            dsMatt: this.pDanhSach,
            nguoiDungId: this.$auth.getNguoiDungID(),
            kieuId: 10,
          };
          console.log(dataDocFile);
          API.docFile(this.axios, dataDocFile)
            .then(async (response) => {
              if (response.data.error_code === "BSS-00000000") {
                if (response.data.data) {
                  (this.dsFile.kyHoaDon = this.kyCuoc),
                    (this.dsFile.kyHDTu = this.tuKHD
                      ? this.tuKHD
                      : this.kyCuoc),
                    (this.dsFile.kyHDDen = this.denKHD
                      ? this.denKHD
                      : this.kyCuoc),
                    (this.dsFile.dsKHLonId = resultDSKHLId),
                    (this.dsFile.nguoiDungId = parseInt(
                      this.$auth.getNguoiDungID()
                    )),
                    (this.dsFile.nguoiCn = "TEST_CN");

                  console.log("dsKHLonId: ", this.dsFile.dsKHLonId);
                  API.taoDanhSachTuFile(this.axios, this.dsFile)
                    .then((result) => {
                      console.log(this.dsFile);
                      if (response.data.error_code === "BSS-00000000") {
                        console.log(result.data.data.listDataError);
                        if (result.data.data.listDataError.length == 0) {
                          // this.getdsLanTao(this.kyCuoc)
                          this.layDS();
                          this.$root.toastSuccess(
                            result.data.data.messageResult
                          );
                        } else {
                          this.dsMaKHLoi = [];
                          result.data.data.listDataError.forEach((item) => {
                            this.dsMaKHLoi.push({
                              MA_KH: item.MA_KH,
                              message: result.data.data.messageResult,
                            });
                          });
                          this.$bvModal.show("popup-maKhachHang");
                          // console.log("message: ", result.data.data.messageResult)
                          // console.log("MA_KH: ", result.data.data.listDataError)
                        }
                      }
                    })
                    .catch((error) => {
                      this.$root.toastError("Đọc file thất bại");
                      this.$root.loading(false);
                    });
                } else {
                  this.$root.toastError("Đọc file thất bại");
                }
              } else {
                this.$root.toastError(response.data.message);
              }
              this.$root.loading(false);
            })
            .catch((reject) => {
              if (
                reject.response &&
                reject.response.data &&
                reject.response.data.message
              )
                this.$root.toastError(reject.response.data.message);
              else this.$root.toastError("Có lỗi xảy ra!");

              this.$root.loading(false);
            });
        } catch (error) {
          this.$root.loading(false);
          console.log("đọc file lỗi");
          return alert("Read failure!" + error);
        }
      };
      fileReader.readAsBinaryString(file[0]);
    },
    gridViewControll_QueryCellInfo(args) {
      if (args.column.field === "BINHQUAN_DT") {
        args.cell.innerText = currency(args.data.BINHQUAN_DT);
      }
    },
    gridViewControll_ExcelQueryCellInfo(args) {
      if (args.column.field === "BINHQUAN_DT") {
        args.cell.innerText = currency(args.data.BINHQUAN_DT);
      }
    },
    xuatFile() {
      if (this.dataExcel.length === 0) {
        this.$root.toastError("Không có dữ liệu để xuất file!");
        return;
      }
      this.dsThuocTinh = [];
      for (let key in this.dataExcel[0]) {
        this.dsThuocTinh.push(key);
      }
      this.$bvModal.show("modal-xuat-dulieu");
    },
    xuatFileMau() {
      let dsFileMau = [
        {
          MA_KH: ""
        }
      ]
      const XLSX = xlsx;
      let fileMau = XLSX.utils.json_to_sheet(dsFileMau);
      let wb = XLSX.utils.book_new(); // make Workbook of Excel
      XLSX.utils.book_append_sheet(wb, fileMau, "TAO_DS_KHL_TEMP"); // sheetAName is name of Worksheet
      // export Excel file
      XLSX.writeFile(wb, "TAO_DS_KHL_TEMP.xlsx");
    },
    openPopupThemKHL() {
      this.$refs.popupThemKHL.onShown()
    }
  },
};
</script>
