<template src="./index.template.html"></template>
<script>
import XLSX from "xlsx";
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required, minValue, maxValue, maxLength } from 'vuelidate/lib/validators'
import DateTimeLib from "../../../utils/DateTimeLib";
import visa from "./service/visaService";
import {Workbook} from "@syncfusion/ej2-excel-export/index"
import EventBus from '@/utils/eventBus'
export default {
  components: { BssErrorMarker, BssRequiredMarker, DateTimeLib},
    validations: {
      arrayCBO: {
        cboTrangThai: {
          required
        },
        cboKieuCC: {
          required
        },
        cboHoSo: {
            required
        },
      },
      arrayInputTab1: {
        tenHoSo: {
          required,
          maxLength: maxLength(200)
        },
        ghiChu: {
          maxLength: maxLength(2000)
        }
      },
      arrayDate: {
        startDate: {
          required,
          maxValue(val, {endDate}){
            return new Date(endDate) > new Date(val);
          }
        },
        endDate: {
          required,
        },
        fromDate: {
          required,
          maxValue(val, {toDate}){
            return new Date(toDate) > new Date(val);
          }
        },
        toDate: {
          required,
        },
      },

    },
  props: {
    tag: {
      type: Number,
      default: 0
    },
    ma_tb_ts_internet: {
      type: String,
      default: ""
    }
  },
  data: function () {
    return {
      DONGBO_TEST: false,
      create_done: false,
      div_search: true,
      activetab: 1,
      ttcc_id: "",
      hscc_id: "",
      enableButtonTab1: true,
      enableSelectFollowPortTD: false,
      sPort_TD: false,
      reloadAfterDelete: 0,
      reloadAfterUpdate: 0,
      isNew: false,
      checkMaTB: false,
      visibleGridTab3: false,
      SetOutput: "",
      ok: true,
      isError: false,
      resultKichHoat: "",
      selectedDSTab3: [],
      selectedPortFix: [],
      selectedDSTab2: [],
      buttonState: {
        isDisableBtnXoa: true,
        isDisableCbo: false,
        isDisableCboTT: false
      },
      arraySelectedItem: {
        soChonAcc: 0,
        soPort: 0,
        soVciVpi: 0,
      },
      arrayImportFile: {
        filexcel: null,
        ok: false,
        dsImportExcel: [],
        dataExcel: [],
        ds_file: [],
      },
      arrayDataXoa: {
        modalXoa: false,
        soTBDK: "",
      },
      arrayDataExcelExport: {
        dataExport: [],
        dataVisa: [],
        dataVisaCu: [],
      },
      arrayDataToCbo: {
        dsTTCC: [],
        dsTTCCIn: [],
        dsHoSoCC: [],
        dsKieuCC: [],
        dsHoSoCCTab2: [],
        dsHSCCTab2Inp: [],
        dsHoSoCCTab3: [],
        dsTramTBTab3: [],
        dsDslamTab3: [],
        dsKieuQuyhoachTab3: [],
        dsLoaiQuyhoachTab3: [],
        dsTTVciVpiTab3: [],
      },
      arrayCBO: {
        cboTTHS: 1,
        cboKieuCC: -1,
        cboTrangThai: -1,
        cboHoSo: -1,
        cboHoSoTab2: -1,
        cboHoSoTab3: 1,
        cboTramTBTab3: 1,
        cboDslamTab3: -1,
        cboKieuQuyhoachTab3: 0,
        cboLoaiQuyhoachTab3: 0,
        cboTrangthaiVCIVPITab3: null,
        cboDslamIDDsPort: -1,
        cboDslamPort: [],
        cboSelectdRowPort:[],
        cboSelectdRowVciVpi:[]
      },
      arrayDate: {
        toDate: new Date(),
        fromDate: new Date(new Date().setMonth(new Date().getMonth() - 1)),
        startDate: new Date(),
        endDate: new Date(),
        dateFormat: 'dd/MM/yyyy',
      },
      arrayInputTab1: {
        soMayAcc: "",
        tenHoSo: "",
        chkChuyen119: false,
        chkCungDoiCap: true,
        chkGiuVciVpi: false,
        ghiChu: "",
      },
      arrayInputTab3: {
        ckbAuto_BNM: false,
        ckbAuto_KH: false,
      },
      arrayDataGrids: {
        dsHoSoTab1: [],
        dsHoSoTab2: [],
        dsHoSoTab3: [],
        dsLuoiPortTab3: [],
        dsVCIVPITab3: [],
        dsPortFixTab3: [],
        SelectedValue: []
      },
      dataExcelEx: [],
      errExcel: {
        dataEx: [],
        dataIn: [],
      },
      isVisiblexTabHoSo: true,
      isVisibleVCIVPI: false,
      checkedGiuVciVpi: [],
    };
  },
  computed: {
        isDisableBtnWhenEmpty: function () {
        return !(
            this.arrayDataGrids.dsHoSoTab1 == 0
        );
        },
    },
  created: function () {
    EventBus.$on('check', this.getGiuVciVpi);
    this.$root.context.get(
      "/web-ecms/doicong-danhsach/kiemtra_giu_vci_vpi"
    ).then(res => {
      if (res != null && res.data != null) {
        this.isVisibleVCIVPI = res.data.result
      }
    });
  },
  async mounted() {
    await this.frmCatChuyenCong_FormLoad()
  },
  methods: {
    async frmCatChuyenCong_FormLoad() {
      try {
        this.$root.showLoading(true);
        let ttnd = await this.lay_thongtin_nguoidung()
        await this.layDSTrangThaiCC();
        await this.layDSKieuCC();
        await this.layDSHoSoCC();
        await this.NAP_DS_HOSO();
        this.DONGBO_TEST = await this.checkFlag("DONGBO_TEST")
        this.arrayInputTab1.soMayAcc = this.ma_tb_ts_internet
        await this.hienThiDanhSachCatChuyen(1, 0);
        if (this.tag == 3) {
          this.isVisiblexTabHoSo = false
          document.getElementById("tabB2").click();
        }
        let login = {
          userId: ttnd.user_visa,
          password: ttnd.pass_visa
        }
        visa.setupRoot(this.$root)
        visa.setLogin(login)
      } catch (e) {
        console.log(e)
      } finally {
        this.$root.showLoading(false);
      }
    },
    tonggle_div: function () {
      this.div_search = !this.div_search;
    },
    layDSTrangThaiCC: async function () {
      let rs = await this.$root.context.get(
        "/web-ecms/doicong-danhsach/lay_ds_trangthai_cc"
      );
      this.arrayDataToCbo.dsTTCC = rs.data;
      this.arrayDataToCbo.dsTTCCIn = rs.data;
    },
    onclickTab:  function (currentActivetab) {
      try {
        this.$root.showLoading(true);
        this.activetab = currentActivetab;
          this.hienThiDanhSachCatChuyen(currentActivetab, 0);
      } catch (error) {
        this.$root.toastError(' '+error);
      }
      finally {
          this.$root.showLoading(false);
      }

    },
    layDSKieuCC: async function () {
      let rs = await this.$root.context.get(
        "/web-ecms/doicong-danhsach/nap_cbo_kieu_cc"
      );
      this.arrayDataToCbo.dsKieuCC = rs.data;
    },
    layDSHoSoCC: async function () {
      let rs = await this.$root.context.get(
        "/web-ecms/doicong-danhsach/nap_cbo_hoso_cc"
      );
      this.arrayDataToCbo.dsHoSoCC = rs.data;
    },
    nhapMoiAction: async function () {
      this.clear();
      this.enableButtonTab1 = false;
      this.buttonState.isDisableCbo = false;
      this.buttonState.isDisableCboTT = true;
      this.isNew = true;
      this.arrayCBO.cboKieuCC = 7
      this.arrayCBO.cboHoSo = 3
      this.arrayCBO.cboTrangThai = 1
      this.arrayInputTab1.tenHoSo = this.$root.token.getUserName() + ' ' + this.convertDate2String(this.arrayDate.startDate);
      this.arrayInputTab1.chkChuyen119 = true;
      this.$refs['tenHoSo'].focus();

    },
    clear: function () {
      this.arrayInputTab1.tenHoSo = "";
      this.arrayCBO.cboHoSo = -1;
      this.arrayCBO.cboKieuCC = -1;
      this.arrayCBO.cboTrangThai = -1;
      this.arrayInputTab1.chkChuyen119 = false;
      this.arrayInputTab1.ghiChu = "";
      this.arrayDate.startDate = new Date();
      this.arrayDate.endDate = new Date();
    },
    huybo: function () {
      this.clear();
    },
    xoaPhieu: async function () {
      if (!this.buttonState.isDisableBtnXoa) {
        let checkPopup = true;
        //this.arrayDataXoa.modalXoa = !this.arrayDataXoa.modalXoa;
        try {
          //this.arrayDataXoa.modalXoa = !this.arrayDataXoa.modalXoa;
          //this.loading(true);
          let sl_tb = this.arrayDataXoa.soTBDK;
          if (sl_tb.toLowerCase() !== "0 thuê bao") {
            checkPopup = false;
            this.$toast.error("Hồ sơ đang có thuê bao đăng ký, không thể xóa!");
            return;
          }
          else {
            let ck = await this.$root.context.get(
              "/web-ecms/doicong-danhsach/lay_trangthai_catchuyen_id",
              { hscc_id: this.ttcc_id }
            );
            if (ck == null || ck.data.length == 0) {
              checkPopup = false;
              this.$root.$toast.error(
                "Không lấy được trạng thái thông tin hồ sơ"
              );
              return;
            }
            if (ck.data.TTCC_ID == 6) {
              checkPopup = false;
              this.$root.$toast.error(
                "Trạng thái hồ sơ đã hoàn thành không thể cập nhật"
              );
              return;
            }
          }
          if (checkPopup) {
            this.$bvModal
              .msgBoxConfirm(
                "Bạn có chắc muốn xóa hồ sơ này?",
                {
                  title: "Xác nhận hành động",
                  centered: true,
                  okTitle: 'Đồng ý',
                  cancelTitle: 'Hủy',
                  size: "sm",
                }
              )
              .then(async (value) => {
                if (value) {
                  await this.xacNhanXoa();
                } else {
                  return;
                }
              });
          }
        } catch (error) {
          this.$toast.error("Xóa thất bại!");
        } finally {
          //this.loading(false);
        }
      }
    },
    xacNhanXoa: async function () {
      try {
        //this.arrayDataXoa.modalXoa = !this.arrayDataXoa.modalXoa;
        this.loading(true);
        let kq = await this.$root.context.post(
          `/web-ecms/doicong-danhsach/xoa-phieu-tab1?hsccId= ${this.hscc_id}`
        );
        if (kq.data.result) {
          this.$toast.success("Xóa thành công");
          this.reloadAfterDelete = 1;
          this.NAP_DS_HOSO();
        } else {
          this.$toast.error("Xóa thất bại!");
        }
      } catch (error) {
        this.$toast.error("Xóa thất bại!");
      } finally {
        this.loading(false);
      }
    },
    capNhatAction: async function () {
      try {
        this.loading(true);
        if (this.kiemTra()) {
          console.log('isNew',this.isNew)
          console.log('this.create_done',this.create_done)
          let result = null;
          if (this.isNew) {
            let dataInsert = {
              tenHoso: this.arrayInputTab1.tenHoSo,
              loaihsId: this.arrayCBO.cboHoSo,
              kieuhsId: this.arrayCBO.cboKieuCC,
              ttccId: this.arrayCBO.cboTrangThai,
              ngayBd: this.convertDate2String(this.arrayDate.startDate),
              ngayKt: this.convertDate2String(this.arrayDate.endDate),
              ttvtId: -1,
              chanBaohong: this.arrayInputTab1.chkChuyen119 ? 1 : 0,
              ghiChu: this.arrayInputTab1.ghiChu,
            };
            result = await this.themMoi(dataInsert);
            this.isNew = false
           // this.create_done = true
          } else {
            let ck = await this.$root.context.get(
              "/web-ecms/doicong-danhsach/lay_trangthai_catchuyen_id",
              { hscc_id: this.ttcc_id }
            );
            console.log('ckckckckckckck',ck)
            if (ck == null || ck.data.length == 0) {
              this.$root.$toast.error(
                "Không lấy được trạng thái thông tin hồ sơ"
              );
              return;
            }
            if (ck.data.TTCC_ID == 6) {
              this.$root.$toast.error(
                "Trạng thái hồ sơ đã hoàn thành không thể cập nhật"
              );
              return;
            }
            console.log('this.reloadAfterUpdate',this.reloadAfterUpdate)
            let dataUpdate = {
              tenHoso: this.arrayInputTab1.tenHoSo,
              hsccId: this.hscc_id,
              thietbiId: null,
              ngayBd: this.convertDate2String(this.arrayDate.startDate),
              ngayKt: this.convertDate2String(this.arrayDate.endDate),
              ghiChu: this.arrayInputTab1.ghiChu,
            };
           console.log('dataUpdate',dataUpdate)

            result = await this.capNhat(dataUpdate);
            this.reloadAfterUpdate = 1;

          }
          this.$toast.success(result);
          this.NAP_DS_HOSO();
        }
      } catch (err) {
        this.$alert(err.response.data.message_detail, error.response.data.message, {
          dangerouslyUseHTMLString: true,
          confirmButtonText: 'Đóng',
          type: 'error'
        })
        return;
      } finally {
        this.loading(false);
      }
    },
    async themMoi(data) {
      try {
        let response = await this.$root.context.post(
          "/web-ecms/doicong-danhsach/them-hoso-catchuyen",
          data
        );
        if (response.error_code === "BSS-00000000") {
          return "Nhập mới thành công";
        }

        let message = response.message;

        if (response.message_detail) {
          message += "\n" + response.message_detail;
        }

        if (response.data) {
          let detailMessage = Object.getOwnPropertyNames(response.data).map(
            (item) => response.data[item]
          );
          message = message + "\n" + [...new Set(detailMessage)].join("\n");
        }

        throw message;
      } catch (err) {
        let responseData = err.response.data;
        let message = responseData.message;

        if (responseData.message_detail) {
          message += "\n" + responseData.message_detail;
        }

        if (responseData.data) {
          let detailMessage = Object.getOwnPropertyNames(responseData.data).map(
            (item) => responseData.data[item]
          );
          message = message + "\n" + [...new Set(detailMessage)].join("\n");
        }

        throw message;
      }
    },
    async capNhat(data) {
      try {
        let response = await this.$root.context.post(
          "/web-ecms/doicong-danhsach/capnhat-hoso-catchuyen",
          data
        );
        if (response.error_code === "BSS-00000000") {
          return "Cập nhật thành công";
        }

        let message = response.message;

        if (response.message_detail) {
          message += "\n" + response.message_detail;
        }

        if (response.data) {
          let detailMessage = Object.getOwnPropertyNames(response.data).map(
            (item) => response.data[item]
          );
          message = message + "\n" + [...new Set(detailMessage)].join("\n");
        }

        throw message;
      } catch (err) {
        let responseData = err.response.data;
        let message = responseData.message;

        if (responseData.message_detail) {
          message += "\n" + responseData.message_detail;
        }

        if (responseData.data) {
          let detailMessage = Object.getOwnPropertyNames(responseData.data).map(
            (item) => responseData.data[item]
          );
          message = message + "\n" + [...new Set(detailMessage)].join("\n");
        }

        throw message;
      }
    },
    linkTaiFileMau_Click: function () {
      window.location.href = "/static/files/MauFile_Import_Thue_Bao.xlsx";
    },
    checkSearch: function(){
      this.$v.$touch()
      if (this.$v.$invalid && !this.$v.arrayDate.fromDate.required) {
        this.$root.$toast.error("Trường từ ngày bắt buộc nhập dữ liệu hoặc không đúng định dạng dd/mm/yyyy.");
        return false
      }

      if (this.$v.$invalid && !this.$v.arrayDate.toDate.required) {
        this.$root.$toast.error("Trường đến ngày bắt buộc nhập dữ liệu hoặc không đúng định dạng dd/mm/yyyy.");
        return false
      }

      if (this.arrayDate.fromDate > this.arrayDate.toDate) {
        this.$root.$toast.error("Từ ngày không được sau đến ngày");
        return false;
      }

      return true;
    },
    kiemTra: function () {
      this.$v.$touch()
      console.log('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')
      if (this.$v.$invalid && !this.$v.arrayInputTab1.tenHoSo.required) {
        this.$root.$toast.error('Trường tên hồ sơ bắt buộc nhập dữ liệu.')
        return false
      }

      if (this.$v.$invalid && !this.$v.arrayInputTab1.tenHoSo.maxLength) {
        this.$root.$toast.error('Trường tên hồ sơ dữ liệu vượt quá độ dài cho phép.')
        return false
      }

      if (this.$v.$invalid && !this.$v.arrayInputTab1.ghiChu.maxLength) {
        this.$root.$toast.error('Trường ghi chú dữ liệu vượt quá độ dài cho phép.')
        return false
      }

      if (this.isNew) {

        if (this.$v.$invalid && !this.$v.arrayCBO.cboHoSo.required) {
          this.$root.$toast.error("Trường loại hồ sơ bắt buộc nhập dữ liệu.");
          return false
        }

        if (this.$v.$invalid && !this.$v.arrayCBO.cboKieuCC.required) {
          this.$root.$toast.error("Trường hình thức đấu chuyển bắt buộc nhập dữ liệu.");
          return false
        }

        if (this.$v.$invalid && !this.$v.arrayCBO.cboTrangThai.required) {
          this.$root.$toast.error("Trường trạng thái cắt chuyển bắt buộc nhập dữ liệu.");
          return false
        }
      }

      if (this.$v.$invalid && !this.$v.arrayDate.startDate.required) {
        this.$root.$toast.error("Trường ngày bắt đầu bắt buộc nhập dữ liệu hoặc không đúng định dạng dd/mm/yyyy.");
        return false
      }

      if (this.$v.$invalid && !this.$v.arrayDate.endDate.required) {
        this.$root.$toast.error("Trường ngày kết thúc bắt buộc nhập dữ liệu hoặc không đúng định dạng dd/mm/yyyy.");
        return false
      }

      if (this.arrayDate.startDate > this.arrayDate.endDate) {
        this.$root.$toast.error("Ngày bắt đầu không được sau ngày kết thúc");
        return false;
      }

      return true;
    },
    layThongTin: async function () {
      try {
        this.$root.showLoading(true);
       // this.loading(true);
        if (this.checkSearch()){
          let tabactive = this.activetab;
          this.hienThiDanhSachCatChuyen(tabactive, 1);
        }
      } catch (error) {
        console.log(error)
        this.$alert(error.response.data.message_detail, error.response.data.message, {
          dangerouslyUseHTMLString: true,
          confirmButtonText: 'Đóng',
          type: 'error'
        })
      }
      finally {
        this.$root.showLoading(false);
      }
    },
    convertDate2String(date) {
      if (date.length == 10){
        return date
      }
      let dd = String(date.getDate()).padStart(2, "0");
      let mm = String(date.getMonth() + 1).padStart(2, "0"); //January is 0!
      let yyyy = date.getFullYear();
      return dd + "/" + mm + "/" + yyyy;

    },
    checkDateInput: function (date){
      let dd = String(date.getDate()).padStart(2, "0");
      let mm = String(date.getMonth() + 1).padStart(2, "0"); //January is 0!
      let yyyy = date.getFullYear();
      let newDate =  dd + "/" + mm + "/" + yyyy;
      let val = newDate ? true : false;
      return val
    },
    minusOneMonth: function (d) {
      const month = d.getMonth();
      d.setMonth(d.getMonth() - 1);
      while (month === d.getMonth()) {
        d.setDate(d.getDate() - 1);
      }
      return d;
    },
    xuatVisa: async function () {
      try {
        this.$root.showLoading(true);
        let dtDanhSachCapCong = this.selectedDSTab3;
        if (dtDanhSachCapCong.length <= 0){
          this.$root.$toast.error("Chưa chọn danh sách để tải.");
          return;
        }
        let lstDanhSachThueBaoCatChuyen = dtDanhSachCapCong.filter(function (el) {
        return el.CHON == "s1" &&
            el.PORTVL_ID != null &&
            (el.LOAITB_ID == 58 || el.LOAITB_ID == 11)
      });
        if (lstDanhSachThueBaoCatChuyen.length == 0) {
          this.$root.$toast.error("Không có dữ liệu để xuất");
          return;
        }
        this.phanvung_id = this.$root.token.getPhanVungID(); // this.phanvung_id =21 là HNI
        let data = lstDanhSachThueBaoCatChuyen.map((item) => ({ DKCC_ID: item.DKCC_ID }));
        let temp = [];
        let dkcc_id = 0;
        for (let i = 0; i < data.length; i++) {
          dkcc_id = data[i].DKCC_ID;
          let ck = await this.$root.context.post(`/web-ecms/doicong-danhsach/tao-dulieu-visa?dkcc_id=${dkcc_id}`);
          if (ck.data.length > 0) {
            let temp2 = ck.data;
            for (let j = 0; j < temp2.length; j++) {
              let data = {
                ACCOUNT: temp2[j].ACCOUNT,
                IP_BRAS: temp2[j].IP_BRAS,
                MA_DSLAM: temp2[j].MA_DSLAM,
                THAMSO_DSLAM: this.phanvung_id == 21 ? temp2[j].THAMSO_DSLAM_4: temp2[j].THAMSO_DSLAM
              };
              console.log("THAMSO_DSLAM: " + temp2[j].THAMSO_DSLAM)
              temp.push(data);
            }
          }
        }
        //Xuất datatable này ra file excel


        if (temp.length > 0) {
          var ma_thuebao = XLSX.utils.json_to_sheet(Object.values(temp));
          var wb = XLSX.utils.book_new(); // make Workbook of Excel
          XLSX.utils.book_append_sheet(wb, ma_thuebao, "VISA"); // sheetAName is name of Worksheet
          // export Excel file
          XLSX.writeFile(wb, "visa.xlsx");
        } else {
          this.$root.toastError('Không tìm thấy dữ liệu để xuất');
          return;
          // let data = {ACCOUNT: '',
          //             IP_BRAS:'',
          //             MA_DSLAM: '',
          //             THAMSO_DSLAM: ''}
          // let arr = []
          // arr.push(data)
          // this.dataExcelEx = Object.values(arr);
          // var ma_thuebao = XLSX.utils.json_to_sheet(Object.values(this.dataExcelEx));
          // var wb = XLSX.utils.book_new(); // make Workbook of Excel
          // XLSX.utils.book_append_sheet(wb, ma_thuebao, "VISA"); // sheetAName is name of Worksheet
          // // export Excel file
          // XLSX.writeFile(wb, "visa.xlsx");
        }

      } catch (ex) {
        this.$root.$toast.error(
          "Có lỗi trong quá trình tạo file Visa. Lỗi: " + ex
        );
      } finally {
        this.$root.showLoading(false);
      }
    },
    xuatVisaCu: async function () {
      try {
        this.$root.showLoading(true);
        let dtDanhSachCapCong = this.selectedDSTab3;
        if (dtDanhSachCapCong.length <= 0){
          this.$root.$toast.error("Chưa chọn danh sách để tải.");
          return;
        }
        console.log('selectedDSTab3',dtDanhSachCapCong)
        let lstDanhSachThueBaoCatChuyen = dtDanhSachCapCong.filter(function (el) {
        return el.CHON == "s1" &&
            el.PORTVL_ID_CU != null &&
            (el.LOAITB_ID == 58 || el.LOAITB_ID == 11)
      });

        if (lstDanhSachThueBaoCatChuyen.length == 0) {
          this.$root.$toast.error("Không có dữ liệu để xuất");
          return;
        }
        let data = lstDanhSachThueBaoCatChuyen.map((item) => ({ DKCC_ID: item.DKCC_ID }));
        //let data = lstDanhSachThueBaoCatChuyen.map((item) => ({ DKCC_ID: '573142' }));
        let json_list =JSON.stringify(data);
        console.log('json_list',json_list)

        let ck = await this.$root.context.post(
          "/web-ecms/doicong-danhsach/tao-dulieu-visa-cu_1",
          {"MA_LT":json_list}
        );
            console.log('ck',ck)
        if (ck.data.length > 0) {
          this.xuatExcelDangGrid(ck.data)
          // var ma_thuebao = XLSX.utils.json_to_sheet(Object.values(ck.data));
          // var wb = XLSX.utils.book_new(); // make Workbook of Excel
          // XLSX.utils.book_append_sheet(wb, ma_thuebao, "VISA_CU"); // sheetAName is name of Worksheet
          // // export Excel file
          // XLSX.writeFile(wb, "visa_cu.xlsx");
        } else {
          this.$root.toastError('Không tìm thấy dữ liệu để xuất');
          return;
          // let data = {ACCOUNT: '',
          //             IP_BRAS:'',
          //             MA_DSLAM: '',
          //             THAMSO_DSLAM: ''}
          // let arr = []
          // arr.push(data)
          // this.dataExcelEx = Object.values(arr);
          // var ma_thuebao = XLSX.utils.json_to_sheet(Object.values(this.dataExcelEx));
          // var wb = XLSX.utils.book_new(); // make Workbook of Excel
          // XLSX.utils.book_append_sheet(wb, ma_thuebao, "VISA_CU"); // sheetAName is name of Worksheet
          // // export Excel file
          // XLSX.writeFile(wb, "visa_cu.xlsx");
        }
      } catch (ex) {
        this.$root.$toast.error("Có lỗi trong quá trình tạo file Visa ! " + ex);
      } finally {
        this.$root.showLoading(false);
      }
    },
     xuatExcelDangGrid(data) {
      try{
            let columns = [
                { index: 1, width: 100 },
                { index: 2, width: 200 },
                { index: 3, width: 100 },
                { index: 4, width: 1000 },
            ]
            let propName = {
                ACCOUNT: 'Tài khoản',
                IP_BRAS: 'IP của BRAS',
                MA_DSLAM: 'DSLAM *',
                THAMSO_DSLAM: 'Tham số DSLAM *'
            }

            let rows = []

            let titleRow = {
                index: 1,
                cells: []
            }
            titleRow.cells.push({
                    index: 3,
                    value: 'Đổi DSLAM dịch vụ',
                    style: {
                        borders: {
                            color: '#00000000',
                            lineStyle: 'thin'
                        }
                    }
                })
            rows.push(titleRow)

            let headerRow = {
                index: 2,
                cells: []
            }
            Object.keys(propName).forEach((item, index) => {
                headerRow.cells.push({
                    index: index + 1,
                    value: propName[item],
                    style: {
                        borders: {
                            color: '#00000000',
                            lineStyle: 'thin'
                        }
                    }
                })
            })

            rows.push(headerRow)

            data.forEach((item, index) => {

                let row = {
                    index: index + 3,
                    cells: []
                }

                Object.keys(propName).forEach((element, index) => {
                    row.cells.push({
                        index: index + 1,
                        value: item[element],
                        style: {
                            borders: {
                                color: '#00000000',
                                lineStyle: 'thin'
                            }
                        }
                    })
                })
                rows.push(row)
            })

            let worksheets = [{ columns, rows }]
            let workbook = new Workbook({worksheets}, 'xlsx')
            workbook.save('visa_cu.xlsx')
      } catch(e){
        console.log(e)
      }
        },
    xuatExcel: function () {
      try {
        if (this.activetab == 2) {
          if (this.arrayDataGrids.dsHoSoTab2.length == 0) {
            this.$toast.error("Không có dữ liệu để xuất Excel !");
            return;
          }
          //this.dataExcelEx = Object.values(this.arrayDataGrids.dsHoSoTab2);

          var datasource = this.arrayDataGrids.dsHoSoTab2.map(item => {
            return {
              "Slot": item.SLOT,
              "Port": item.PORT,
              "Ont ID": item.ONT_ID,
              "Vpi": item.VPI,
              "Vci": item.VCI,
              "Số máy/Acc": item.MA_TB,
              "Mã LT": item.MA_LT,
              "Mã đôi cáp": item.MADOICAP,
              "Dịch vụ": item.DICHVU_VT,
              "Loại hình ": item.LOAIHINH_TB,
              "Gói cước": item.TOCDO,
              "Tên thuê bao": item.TEN_TB,
              "Địa chỉ lắp đặt": item.DIACHI_LD,
              "Giữ VciVpi": !item.HOLD_VCI_VPI ? 'Unchecked' : 'Checked',
              "Trạng thái": item.TRANGTHAI_TB
            }
          })

          var ma_thuebao = XLSX.utils.json_to_sheet(datasource);
          var wb = XLSX.utils.book_new(); // make Workbook of Excel
          XLSX.utils.book_append_sheet(wb, ma_thuebao, "Bước 2");
          XLSX.writeFile(wb, "B2_DS thue bao cat chuyen.xls");
        } else if (this.activetab == 3) {
          if (this.arrayDataGrids.dsHoSoTab3 == 0) {
            this.$toast.error("Không có dữ liệu để xuất Excel !");
            return;
          }
          //this.dataExcelEx = Object.values(this.arrayDataGrids.dsHoSoTab3);
          var datasource = this.arrayDataGrids.dsHoSoTab3.map(item => {
            return {
              "Số máy/Acc": item.MA_TB,
              "Mã LT": item.MA_LT,
              "IP N33.G22 cũ": item.DSLAM_CU,
              // "Dslam cũ": item.DSLAM_CU,
              "Slot/port cũ": item.PORT_CU,
              "ONTID cũ": item.ONT_ID_CU,
              "Svlan/cvlan cũ": item.VPI_VCI_CU,
              // "Dslam mới": item.DSLAM,
              "IP N33.G22 mới": item.DSLAM,
              "Slot/port mới": item.PORT_MOI,
              "ONTID mới": item.ONT_ID_MOI,
              "Svlan/cvlan mới": item.VPI_VCI_MOI,
              // "Mã đôi cáp": item.MADOICAP,
              // "Dịch vụ": item.LOAIHINH_TB,
              // "Ngày KH": item.NGAY_KH,
              // "Ngày CH": item.NGAY_CH,
              // "Trạng thái TB": item.TRANGTHAI_TB,
              // "Log KH": item.LOG_KH,
              // "Temp KH": item.TEMP_KH,
              // "Gói cước": item.TOCDO,
              // "Tên thuê bao": item.TEN_TB,
              // "Địa chỉ lắp đặt": item.DIACHI_LD,
              // "Cấu hình": !item.TRANGTHAI_KH_ID ? 'Unchecked' : 'Checked',
              // "Trạng thái cổng": item.TRANGTHAI_CC
            }
          })
          var ma_thuebao = XLSX.utils.json_to_sheet(datasource);
          var wb = XLSX.utils.book_new(); // make Workbook of Excel
          XLSX.utils.book_append_sheet(wb, ma_thuebao, "Bước 3");
          XLSX.writeFile(wb, "B3_DS thue bao cat chuyen.xls");
        }
        //console.log(this.dataExcelEx)
        //var ma_thuebao = XLSX.utils.json_to_sheet(Object.values(this.dataExcelEx));
        //var wb = XLSX.utils.book_new(); // make Workbook of Excel
        //XLSX.utils.book_append_sheet(wb, ma_thuebao, "MA_TB"); // sheetAName is name of Worksheet
        // export Excel file
        //XLSX.writeFile(wb, "export.xlsx");
      } catch (ex) {
        this.$toast.error("Có lỗi trong quá trình xuất file Excel !" + ex);
      }
    },
    kichHoat: async function () {
      try {
        let hscc_id = this.arrayCBO.cboHoSoTab3;
        var check = await this.KICHHOAT();
        if (check !== "1") {
          let loi = this.resultKichHoat;
          if (loi != "") {
            this.NAP_DS_THUEBAO_CAPCONG(hscc_id);
          }
        } else {
          this.NAP_DS_THUEBAO_CAPCONG(hscc_id);
        }
      } catch (ex) {
        this.$root.$toast.error("Lỗi: " + ex);
      }
    },
    KICHHOAT: async function () {
      try {
        this.$root.showLoading(true);
        var check = await this.KIEMTRA_GANCONG(0);
        if (!check) {
          return "-404";
        }

        this.$root.showLoading(false);
        let confirmation = await this.$bvModal
            .msgBoxConfirm(
              "Bạn có chắc chắn thực hiện đổi cổng danh sách các Account lên trên Visa hay không?",
              {
                title: "Xác nhận hành động",
                centered: true,
                size: "sm",
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy',
              }
            )

        if (!confirmation) {
          return "-404";
        }

        this.$root.showLoading(true);

        if (await this.fKICHHOAT() === "1") {
          return "1";
        }

        return "-404";

      } catch (ex) {
        this.resultKichHoat = "Có lỗi khi kích hoạt " + ex;
        return "-404";
      } finally {
        this.$root.showLoading(false);
      }
    },
    async checkFlag(keyname) {
      let kq = await this.$root.context.get("web-ccdv/hoanthienhschuyenquyen/kiemtra_open_sync_vina", {
        keyname: keyname
      })
      if (kq.data && kq.data.length > 0) {
        return kq.data[0].KEY_VALUE
      } else {
        return false
      }
    },
    fKICHHOAT: async function () {
      try {
        let hscc_id = this.arrayCBO.cboHoSoTab3;
        let ds = [...this.selectedDSTab3];
        let cop = ds.filter(
          (item) => item.CHON === "s1" && item.DKCC_CHA_ID === null
        );
        let json_list = cop.map((item) => ({ DKCC_ID: item.DKCC_ID }));
        let dem = await this.$root.context.post(
          "/web-ecms/doicong-danhsach/kiemtra-list-inp-gocong-tab3",
          json_list
        );
        if (dem.data[0].COUNT !== 0) {
          this.resultKichHoat =
            "Trong danh sách có thuê bao có trạng thái hoàn thành hoặc chưa cấp cổng. Bạn hãy kiểm tra lại";
          this.$root.$toast.error(this.resultKichHoat);

          return "-404";
        }

        let loi_kh = [];
        let header = {
          MA_TB: "MA_TB",
          THONGTIN_LOI: "THONGTIN_LOI",
        };
        loi_kh.push(header);
        var ds_con = [...this.arrayDataGrids.dsHoSoTab3];
        let ds_con_dc = [];
        //VASCService vasc = new VASCService(tt_nd.user_vasc, tt_nd.pass_vasc, tt_nd.diemgiaodich_vasc);
        let count = ds_con.filter(function (item) {
          return item.DKCC_CHA_ID != null;
        });
        if (count.length > 0) {
          ds_con_dc = ds_con;
        }
        for (let i = 0; i < ds.length; i++) {
          let dr = ds[i];
          if (ds_con_dc != null && ds_con_dc.length > 0) {
            var l = ds_con_dc.filter(x => x.DKCC_CHA_ID == ds[i].DKCC_ID);

            // var l = ds_con_dc.filter(function (item) {
            //   return item.DKCC_CHA_ID == ds[i].DKCC_ID;
            // });
            console.log("l:",l)
            console.log("l.length:",l)

            if (l.length > 0) {
              console.log("start 1:")

              for (let i = 0; i < l.length; i++) {
                //#region Kích hoạt con
                let row = l[i];
                if (row.PORT_ID == null || row.NGAY_KH != null) {
                  continue;
                }
                if (
                  row.LOAITB_ID == 11 ||
                  row.LOAITB_ID == 58 ||
                  row.LOAITB_ID == 79
                ) {
                  //#region Thực hiện đổi cổng
                  let kieu_mega = row.KIEU_MEGA;
                  if (this.DONGBO_TEST == false && kieu_mega != 0) {
                    console.log('1111',row)
                    let res = await this.DoiCongAcccountVisa(row);
                    if (res != "") {
                      console.log('33',res)
                      let errr = {
                        MA_TB: row.MA_TB,
                        THONGTIN_LOI: "Lỗi kích hoạt :" + res,
                      };
                      loi_kh.push(errr);
                      let log = {
                        ma_tb: row.MA_TB,
                        dkcc_id: row.DKCC_ID,
                        port_id: row.PORT_ID,
                        vci_vpi_id: row.VCI_VPI_ID,
                        dslam_vdc_id: row.DSLAM_VDC_ID,
                        flag: 2,
                        nd: "Lỗi kích hoạt visa " + res,
                        status: 0,
                      };
                      await this.$root.context.post(
                        "/web-ecms/doicong-danhsach/them-log-catchuyen",
                        log
                      );
                    } else {
                      //#region cập nhật status và lưu log
                      try {
                        await this.$root.context.post(
                          `/web-ecms/doicong-danhsach/capnhat9-catchuyen-hoanthien-tab3?dkcc_id=${row.DKCC_ID}`
                        );
                        let log = {
                          ma_tb: row.MA_TB,
                          dkcc_id: row.DKCC_ID,
                          port_id: row.PORT_ID,
                          vci_vpi_id: row.VCI_VPI_ID,
                          dslam_vdc_id: row.DSLAM_VDC_ID,
                          flag: 2,
                          nd: "Kích hoạt thành công",
                          status: 1,
                        };
                        console.log('44',log)

                        await this.$root.context.post(
                          "/web-ecms/doicong-danhsach/them-log-catchuyen",
                          log
                        );
                      } catch (ex) {
                        console.log('55',ex)

                        let errr = {
                          MA_TB: row.MA_TB,
                          THONGTIN_LOI: "Lỗi đổi trạng thái " + ex,
                        };
                        loi_kh.push(errr);
                        let log = {
                          ma_tb: row.MA_TB,
                          dkcc_id: row.DKCC_ID,
                          port_id: row.PORT_ID,
                          vci_vpi_id: row.VCI_VPI_ID,
                          dslam_vdc_id: row.DSLAM_VDC_ID,
                          flag: 2,
                          nd: "Kích hoạt thành công, lỗi thay đổi trạng thái",
                          status: 1,
                        };
                        await this.$root.context.post(
                          "/web-ecms/doicong-danhsach/them-log-catchuyen",
                          log
                        );
                      }
                      //#endregion
                    }
                  }
                  //#endregion
                } else if (row.LOAITB_ID == 61) {
                  //LoaiHinhTB.INTERNET_MYTV
                  //#region Vasc
                  console.log('66',row.LOAITB_ID)

                  if (row.IP == "") {
                    console.log('77',row.IP)
                    let errr = {
                      MA_TB: row.MA_TB,
                      THONGTIN_LOI:
                        "Chưa có thông tin về IP. Hãy kiểm tra lại !",
                    };
                    loi_kh.push(errr);
                    continue;
                  }
                  console.log('DONGBO_TEST: ',this.DONGBO_TEST )
                  if (this.DONGBO_TEST == false) {
                    console.log('88',this.DONGBO_TEST )

                    let sPortDSL = "";
                    let shelf = "0";
                    if (row.SHELF !== null && row.SHELF.trim() !== "")
                      shelf = row.SHELF.trim();
                    let vloaitbi_id = row.LOAITBI_ID;
                    if (vloaitbi_id == 3) {
                      sPortDSL =
                        row.IP +
                        "/" +
                        row.RACK +
                        "/" +
                        row.SLOT +
                        "/" +
                        row.PORT +
                        "/" +
                        row.VPI;
                        console.log('sPortDSL',sPortDSL)

                    } else{
                      sPortDSL =
                        row.IP + "/" + shelf + "/" + row.SLOT + "/" + row.PORT;
                        console.log('sPortDSL2',sPortDSL)}


                    let sDeviceName = row.SYSTEM || '';

                    // hoangpkn : 30/07/2015 : Không cần truyền sPortDSL vẫn chạy (HCM vẫn truyền null). Do có truyền mà IP dslam sai. Vasc sẽ báo lỗi
                    //int result = vasc.UpdateDeviceInfo(txtMaTB.Text.Trim(), sDeviceName, "");
                    let loi_ = "";
                    //int result = vasc.UpdateDeviceInfo(row["MA_TB"].ToString(), sDeviceName, sPortDSL, out loi_); //nhapt mở 29/09/2016
                    let result = await this.UpdateDeviceInfo(
                      row.MA_TB,
                      sDeviceName,
                      sPortDSL
                    ); //nhapt mở 29/09/2016
                    console.log('result22 : ',result)
                    if (result.res != 0) {
                      //lỗi
                      console.log('result.res : ',result.res)
                      let errr = {
                        MA_TB: row.MA_TB,
                        THONGTIN_LOI:
                          "Có lỗi trong quá trình cập nhật thông tin cổng thuê bao " +
                          row.MA_TB +
                          " trên VASC !",
                      };
                      loi_kh.push(errr);
                      let log = {
                        ma_tb: row.MA_TB,
                        dkcc_id: row.DKCC_ID,
                        port_id: row.PORT_ID,
                        vci_vpi_id: row.VCI_VPI_ID,
                        dslam_vdc_id: row.DSLAM_VDC_ID,
                        flag: 2,
                        nd: "Lỗi kích hoạt  " + result.err + "",
                        status: 0,
                      };
                      await this.$root.context.post(
                        "/web-ecms/doicong-danhsach/them-log-catchuyen",
                        log
                      );
                    } else {
                      //#region cập nhật status và lưu log
                      try {
                        await this.$root.context.post(
                          `/web-ecms/doicong-danhsach/capnhat9-catchuyen-hoanthien-tab3?dkcc_id=${row.DKCC_ID}`
                        );
                        let log = {
                          ma_tb: row.MA_TB,
                          dkcc_id: row.DKCC_ID,
                          port_id: row.PORT_ID,
                          vci_vpi_id: row.VCI_VPI_ID,
                          dslam_vdc_id: row.DSLAM_VDC_ID,
                          flag: 2,
                          nd: "Kích hoạt thành công",
                          status: 1,
                        };
                        console.log('log23 : ',log)
                        await this.$root.context.post(
                          "/web-ecms/doicong-danhsach/them-log-catchuyen",
                          log
                        );
                      } catch (ex) {
                        console.log('log3546 : ',ex)

                        let errr = {
                          MA_TB: row.MA_TB,
                          THONGTIN_LOI: "Lỗi đổi trạng thái " + ex,
                        };
                        loi_kh.push(errr);
                        let log = {
                          ma_tb: row.MA_TB,
                          dkcc_id: row.DKCC_ID,
                          port_id: row.PORT_ID,
                          vci_vpi_id: row.VCI_VPI_ID,
                          dslam_vdc_id: row.DSLAM_VDC_ID,
                          flag: 2,
                          nd: "Kích hoạt thành công, lỗi thay đổi trạng thái",
                          status: 0,
                        };
                        await this.$root.context.post(
                          "/web-ecms/doicong-danhsach/them-log-catchuyen",
                          log
                        );
                      }
                      //#endregion
                    }
                  }
                  //#endregion
                } else {
                  //#region cập nhật status và lưu log
                  try {
                    await this.$root.context.post(
                      `/web-ecms/doicong-danhsach/capnhat9-catchuyen-hoanthien-tab3?dkcc_id=${row.DKCC_ID}`
                    );
                    let log = {
                      ma_tb: row.MA_TB,
                      dkcc_id: row.DKCC_ID,
                      port_id: row.PORT_ID,
                      vci_vpi_id: row.VCI_VPI_ID,
                      dslam_vdc_id: row.DSLAM_VDC_ID,
                      flag: 2,
                      nd: "Kích hoạt thành công",
                      status: 1,
                    };
                    console.log('log23341 : ',log)

                    await this.$root.context.post(
                      "/web-ecms/doicong-danhsach/them-log-catchuyen",
                      log
                    );
                  } catch (ex) {
                    console.log('log23đf : ',ex)

                    let errr = {
                      MA_TB: row.MA_TB,
                      THONGTIN_LOI: "Lỗi đổi trạng thái " + ex,
                    };
                    loi_kh.push(errr);
                    let log = {
                      ma_tb: row.MA_TB,
                      dkcc_id: row.DKCC_ID,
                      port_id: row.PORT_ID,
                      vci_vpi_id: row.VCI_VPI_ID,
                      dslam_vdc_id: row.DSLAM_VDC_ID,
                      flag: 2,
                      nd: "Kích hoạt thành công, lỗi thay đổi trạng thái",
                      status: 0,
                    };
                    await this.$root.context.post(
                      "/web-ecms/doicong-danhsach/them-log-catchuyen",
                      log
                    );
                  }
                  //#endregion
                }
                //#endregion
              }
            }
          }
          if (dr.PORT_ID == null || dr.NGAY_KH != null) {
            continue;
          }
          if (dr.LOAITB_ID == 11 || dr.LOAITB_ID == 58 || dr.LOAITB_ID == 79) {
            // dr.THAMSO_CU = 4
            //#region Thực hiện đổi cổng
            let kieu_mega = dr.KIEU_MEGA;
            if (this.DONGBO_TEST === false && kieu_mega !== 0) {
              let res = await this.DoiCongAcccountVisa(dr);
              console.log(res)
              if (res !== "") {
                let errr = {
                  MA_TB: dr.MA_TB,
                  THONGTIN_LOI: "Lỗi kích hoạt :" + res,
                };
                loi_kh.push(errr);
                let log = {
                  ma_tb: dr.MA_TB,
                  dkcc_id: dr.DKCC_ID,
                  port_id: dr.PORT_ID,
                  vci_vpi_id: dr.VCI_VPI_ID,
                  dslam_vdc_id: dr.DSLAM_VDC_ID,
                  flag: 2,
                  nd: "Lỗi kích hoạt visa " + res,
                  status: 0,
                };

                await this.$root.context.post(
                  "/web-ecms/doicong-danhsach/them-log-catchuyen",
                  log
                );
              } else {
                //#region cập nhật status và lưu log
                try {
                  await this.$root.context.post(
                    `/web-ecms/doicong-danhsach/capnhat9-catchuyen-hoanthien-tab3?dkcc_id=${dr.DKCC_ID}`
                  );
                  let log = {
                    ma_tb: dr.MA_TB,
                    dkcc_id: dr.DKCC_ID,
                    port_id: dr.PORT_ID,
                    vci_vpi_id: dr.VCI_VPI_ID,
                    dslam_vdc_id: dr.DSLAM_VDC_ID,
                    flag: 2,
                    nd: "Kích hoạt thành công",
                    status: 1,
                  };

                  await this.$root.context.post(
                    "/web-ecms/doicong-danhsach/them-log-catchuyen",
                    log
                  );
                } catch (ex) {

                  let errr = {
                    MA_TB: dr.MA_TB,
                    THONGTIN_LOI: "Lỗi đổi trạng thái " + ex,
                  };
                  loi_kh.push(errr);
                  let log = {
                    ma_tb: dr.MA_TB,
                    dkcc_id: dr.DKCC_ID,
                    port_id: dr.PORT_ID,
                    vci_vpi_id: dr.VCI_VPI_ID,
                    dslam_vdc_id: dr.DSLAM_VDC_ID,
                    flag: 2,
                    nd: "Kích hoạt thành công, lỗi thay đổi trạng thái",
                    status: 0,
                  };
                  await this.$root.context.post(
                    "/web-ecms/doicong-danhsach/them-log-catchuyen",
                    log
                  );
                }
                //#endregion
              }
            }
            //#endregion
          } else if (dr.LOAITB_ID == 61) {
            //LoaiHinhTB.INTERNET_MYTV
            //#region Vasc
            if (dr.IP == "") {
              let errr = {
                MA_TB: dr.MA_TB,
                THONGTIN_LOI: "Chưa có thông tin về IP. Hãy kiểm tra lại !",
              };
              loi_kh.push(errr);
              continue;
            }
            if (this.DONGBO_TEST == false) {
              let sPortDSL = "";
              let shelf = "0";
              if (dr.SHELF !== null && dr.SHELF.trim() !== "")
                shelf = dr.SHELF.trim();
              let vloaitbi_id = dr.LOAITBI_ID;
              if (vloaitbi_id == 3) {
                sPortDSL =
                  dr.IP +
                  "/" +
                  dr.RACK +
                  "/" +
                  dr.SLOT +
                  "/" +
                  dr.PORT +
                  "/" +
                  dr.VPI;

              } else
                sPortDSL = dr.IP + "/" + shelf + "/" + dr.SLOT + "/" + dr.PORT;
              let sDeviceName = dr.SYSTEM;

              let result = await this.UpdateDeviceInfo(
                dr.MA_TB,
                sDeviceName,
                sPortDSL
              ); //nhapt mở 29/09/2016

              if (result.res != 0) {
                //lỗi
                let errr = {
                  MA_TB: dr.MA_TB,
                  THONGTIN_LOI:
                    "Có lỗi trong quá trình cập nhật thông tin cổng thuê bao " +
                    dr.MA_TB +
                    " trên VASC !",
                };
                loi_kh.push(errr);
                let log = {
                  ma_tb: dr.MA_TB,
                  dkcc_id: dr.DKCC_ID,
                  port_id: dr.PORT_ID,
                  vci_vpi_id: dr.VCI_VPI_ID,
                  dslam_vdc_id: dr.DSLAM_VDC_ID,
                  flag: 2,
                  nd: "Lỗi kích hoạt  " + result.err,
                  status: 0,
                };
                await this.$root.context.post(
                  "/web-ecms/doicong-danhsach/them-log-catchuyen",
                  log
                );
              } else {
                //#region cập nhật status và lưu log
                try {
                  await this.$root.context.post(
                    `/web-ecms/doicong-danhsach/capnhat9-catchuyen-hoanthien-tab3?dkcc_id=${dr.DKCC_ID}`)
                  let log = {
                    ma_tb: dr.MA_TB,
                    dkcc_id: dr.DKCC_ID,
                    port_id: dr.PORT_ID,
                    vci_vpi_id: dr.VCI_VPI_ID,
                    dslam_vdc_id: dr.DSLAM_VDC_ID,
                    flag: 2,
                    nd: "Kích hoạt thành công",
                    status: 1,
                  };
                  await this.$root.context.post(
                    "/web-ecms/doicong-danhsach/them-log-catchuyen",
                    log
                  );
                } catch (ex) {
                  let errr = {
                    MA_TB: dr.MA_TB,
                    THONGTIN_LOI: "Lỗi đổi trạng thái " + ex,
                  };
                  loi_kh.push(errr);
                  let log = {
                    ma_tb: dr.MA_TB,
                    dkcc_id: dr.DKCC_ID,
                    port_id: dr.PORT_ID,
                    vci_vpi_id: dr.VCI_VPI_ID,
                    dslam_vdc_id: dr.DSLAM_VDC_ID,
                    flag: 2,
                    nd: "Kích hoạt thành công, lỗi thay đổi trạng thái",
                    status: 0,
                  };
                  await this.$root.context.post(
                    "/web-ecms/doicong-danhsach/them-log-catchuyen",
                    log
                  );
                }
                //#endregion
              }
            }
            //#endregion
          } else {
            //#region cập nhật status và lưu log
            try {

              await this.$root.context.post(
                `/web-ecms/doicong-danhsach/capnhat9-catchuyen-hoanthien-tab3?dkcc_id=${dr.DKCC_ID}`
              );
              let log = {
                ma_tb: dr.MA_TB,
                dkcc_id: dr.DKCC_ID,
                port_id: dr.PORT_ID,
                vci_vpi_id: dr.VCI_VPI_ID,
                dslam_vdc_id: dr.DSLAM_VDC_ID,
                flag: 2,
                nd: "Kích hoạt ảo thành công",
                status: 1,
              };
              await this.$root.context.post(
                "/web-ecms/doicong-danhsach/them-log-catchuyen",
                log
              );
            } catch (ex) {

              let errr = {
                MA_TB: dr.MA_TB,
                THONGTIN_LOI: "Lỗi đổi trạng thái " + ex,
              };
              loi_kh.push(errr);
              let log = {
                ma_tb: dr.MA_TB,
                dkcc_id: dr.DKCC_ID,
                port_id: dr.PORT_ID,
                vci_vpi_id: dr.VCI_VPI_ID,
                dslam_vdc_id: dr.DSLAM_VDC_ID,
                flag: 2,
                nd: "Kích hoạt thành công, lỗi thay đổi trạng thái",
                status: 0,
              };
              await this.$root.context.post(
                "/web-ecms/doicong-danhsach/them-log-catchuyen",
                log
              );
            }
            //#endregion
          }
        }
        this.errExcel.dataEx = loi_kh;
        let dataExcel = this.errExcel.dataEx;

        if (dataExcel != null && dataExcel.length > 1) {
          this.$bvModal
            .msgBoxConfirm(
              "Đã thực hiện kích hoạt. Bạn có muốn xuất file danh sách bị lỗi không?",
              {
                title: "Xác nhận hành động",
                centered: true,
                size: "sm",
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy',
              }
            )
            .then(async (value) => {
              if (value) {
                document.getElementById("exporterror").click();
              } else {
                return "404";
              }
            });
        } else {
          this.$toast.success("Đã thực hiện kích hoạt");
        }
        await this.NAP_DS_THUEBAO_CAPCONG(hscc_id);
        return "1";
      } catch (error) {
        this.SetOutput = "Có lỗi khi cấu hình " + error;
        console.log(this.SetOutput);
      } finally {
      }
    },
    guiCauHinh: async function () {
      await this.CAUHINH_BNM();
    },
    CAUHINH_BNM: async function () {
      try {
        let check = await this.KIEMTRA_GANCONG(0);
        if (!check) {
          return;
        }
         this.$confirm(
            `Bạn có chắc chắn thực hiện gửi tự động cấu hình không?`,
            "Xác nhận hành động",
            {
              confirmButtonText: "Đồng ý",
              cancelButtonText: "Hủy",
            }
          )
            .then(async () => {
                     //let ds = this.selectedDSTab3;
        let ds = [...this.arrayDataGrids.dsHoSoTab3];//this.$refs['grid-hoso-tab3'].getSelectedRecords(); //this.selectedDSTab3;
        let cop = this.$refs['grid-hoso-tab3'].getSelectedRecords().filter(item=>item.DKCC_CHA_ID == null)
        // let cop = ds.filter(function (item) {
        //   return item.CHON === "s1" && item.DKCC_CHA_ID === null;
        // });
        let json_list = cop.map((item) => ({ DKCC_ID: item.DKCC_ID }));

        let dem = await this.$root.context.post(
          "/web-ecms/doicong-danhsach/kiemtra-list-inp-gocong-tab3",
          json_list
        );

        if (dem.data[0].COUNT !== 0) {
          this.$root.$toast.error(
            "Trong danh sách có thuê bao có trạng thái hoàn thành hoặc chưa cấp cổng không thể gửi cấu hình"
          );
          return;
        }
        let dsSend = this.$refs['grid-hoso-tab3'].getSelectedRecords().filter(item=>item.DKCC_CHA_ID == null)
        await this.fCAUHINH_BNM(dsSend);
        })
        .catch((e) => {console.log("lỗi:",e);return});
      } catch (ex) {
        this.$root.$toast.error("Có lỗi khi cấu hình BNM " + ex);
      } finally {
      }
    },
    fCAUHINH_BNM: async function (ds) {
      try {
        this.loading(true);
        let loi_bnm = [];
        let header = {
          MA_TB: "MA_TB",
          THONGTIN_LOI: "THONGTIN_LOI",
        };

        loi_bnm.push(header);
        let dem = 0;
        let SetOutput = "";

        var ds_con = [...this.arrayDataGrids.dsHoSoTab3];
        let ds_con_dc = [];

        let count = ds_con.filter(function (item) {
          return item.DKCC_CHA_ID != null;
        });

        if (count.length > 0) {
          ds_con_dc = ds_con.filter(function (item) {
            return item.DKCC_CHA_ID != null;
          });
        }

        let tong = ds.length;
        console.log("tong: ", tong)
        for (let i = 0; i < tong; i++) {

          let dr = ds[i];
            console.log('dr.PORT_ID',dr.PORT_ID,'dr.NGAY_CH',dr.NGAY_CH)
          if (dr.PORT_ID == null || dr.NGAY_CH != null) {
            continue;
          }

          try {
            let dstb = ds[i].THUEBAO_ID + ";";
            let vchuyenall = 0;
            console.log('ds[i].THUEBAO_ID: ',ds[i].THUEBAO_ID)

            if (ds_con_dc != null && ds_con_dc.length > 0) {

              var l = ds_con_dc.filter(function (item) {
                return item.DKCC_CHA_ID == ds[i].DKCC_ID;
              });

              if (l.length > 0) {
                vchuyenall = 1;
                l.forEach(row => { dstb = dstb + row.THUEBAO_ID + ";"; })
                // for (let row in l) {
                //   dstb = dstb + row.THUEBAO_ID + ";";
                // }
              }
            }

            dstb = dstb.slice(0, -1);

            let pass_ftth2 = await visa.lookupRASAccount(dr.MA_TB)
            console.log('pass_ftth2',pass_ftth2)
            if (pass_ftth2.startsWith("Lỗi")) {
              throw pass_ftth2
            }

            let data = {
              thuebao_id: dr.THUEBAO_ID,
              port_id: dr.PORT_ID,
              vci_vpi_id: dr.VCI_VPI_ID,
              loai_td: 1,
              nguoi_kh: this.$auth.getUserName(),
              chuyen_all: vchuyenall,
              ds_tb: dstb,
              trangthai: 0,
              dkcc_id: dr.DKCC_ID,
              pass_ftth: 'pass_ftth2',
            };

            let kq = null

            try {
              console.log('web-ecms/doicong-danhsach/cauhinh-bnm')
                kq = await this.$root.context.post(
                  "/web-ecms/doicong-danhsach/cauhinh-bnm",
                  data
                );
            } catch (err) {
               console.log('err',err)
                let responseData = err.response.data
                let message = []

                if (responseData.message) {
                    message.push(responseData.message)
                }

                if (responseData.message_detail) {
                    message.push(responseData.message_detail)
                }

                if (responseData.data) {
                    let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => `${item}: ${responseData.data[item]}`)
                    message.push([...new Set(detailMessage)].join('\n'))
                }

                throw message.join('\n')
            }
            console.log("kq.data.RESULT: ",kq.data.RESULT )
            if (kq.data.RESULT == "ok") {
              try {
                await this.$root.context.post(
                  `/web-ecms/doicong-danhsach/capnhat10-catchuyen-hoanthien-tab3?dkcc_id=${dr.DKCC_ID}`
                );

                let log = {
                  ma_tb: dr.MA_TB,
                  dkcc_id: dr.DKCC_ID,
                  port_id: dr.PORT_ID,
                  vci_vpi_id: dr.VCI_VPI_ID,
                  dslam_vdc_id: dr.DSLAM_VDC_ID,
                  flag: 3,
                  nd:
                    "Đã gửi cấu hình thành công " +
                    dstb +
                    ": Cấu hình " +
                    vchuyenall,
                  status: 1,
                };

                await this.$root.context.post(
                  "/web-ecms/doicong-danhsach/them-log-catchuyen",
                  log
                );
              } catch (exxx) {
                dem += 1;
                let errr = {
                  MA_TB: dr.MA_TB,
                  THONGTIN_LOI:
                    "Cấu hình thành công không lưu được trạng thái !" + exxx,
                };
                loi_bnm.push(errr);
                let log = {
                  ma_tb: dr.MA_TB,
                  dkcc_id: dr.DKCC_ID,
                  port_id: dr.PORT_ID,
                  vci_vpi_id: dr.VCI_VPI_ID,
                  dslam_vdc_id: dr.DSLAM_VDC_ID,
                  flag: 3,
                  nd:
                    "Cấu hình thành công " +
                    dstb +
                    " (" +
                    vchuyenall +
                    ") không lưu được trạng thái !" +
                    exxx +
                    "",
                  status: 0,
                };
                await this.$root.context.post(
                  "/web-ecms/doicong-danhsach/them-log-catchuyen",
                  log
                );
              }
            } else {
              console.log("333")
              dem += 1;
              let errr = {
                MA_TB: dr.MA_TB,
                THONGTIN_LOI:
                  "Có lỗi trong quá trình cấu hình !" + kq.data.RESULT,
              };
              loi_bnm.push(errr);
              let log = {
                ma_tb: dr.MA_TB,
                dkcc_id: dr.DKCC_ID,
                port_id: dr.PORT_ID,
                vci_vpi_id: dr.VCI_VPI_ID,
                dslam_vdc_id: dr.DSLAM_VDC_ID,
                flag: 3,
                nd:
                  "Có lỗi trong quá trình gửi cấu hình !" +
                  kq.data.RESULT +
                  "",
                status: 0,
              };
              await this.$root.context.post(
                "/web-ecms/doicong-danhsach/them-log-catchuyen",
                log
              );
            }

            // 17-10-2019 AnhDt: Cập nhật thông tin view temp sau khi gửi kích hoạt
            //Bo
            let _kq = ""; //need check//_temp_content(Convert.ToInt64(dr["THUEBAO_ID"]), Convert.ToInt64(dr["PORT_ID"]), Convert.ToInt64(dr["VCI_VPI_ID"]));
            //End AnhDt
          } catch (exx) {
            dem += 1;
            let errr = {
              MA_TB: dr.MA_TB,
              THONGTIN_LOI: "Có lỗi trong quá trình cấu hình !" + exx,
            };
            loi_bnm.push(errr);
            let log = {
              ma_tb: dr.MA_TB,
              dkcc_id: dr.DKCC_ID,
              port_id: dr.PORT_ID,
              vci_vpi_id: dr.VCI_VPI_ID,
              dslam_vdc_id: dr.DSLAM_VDC_ID,
              flag: 3,
              nd: "Có lỗi trong quá trình cấu hình !" + exx + "",
              status: 0,
            };
            await this.$root.context.post(
              "/web-ecms/doicong-danhsach/them-log-catchuyen",
              log
            );
          }
        }
        if (dem > 0) {
          SetOutput = "0";
        } else {
          SetOutput = "1";
        }
        console.log("SetOutput11:",SetOutput)
        if (SetOutput == "1") {
          this.$toast.success("Đã thực hiện xong cấu hình");
          await this.NAP_DS_THUEBAO_CAPCONG(this.arrayCBO.cboHoSoTab3);
        } else {

          if (loi_bnm != null && loi_bnm.length > 0) {
            this.errExcel.dataEx = [];
            this.errExcel.dataEx = loi_bnm;
            this.$bvModal
                .msgBoxConfirm(
                  "Đã thực hiện cấu hình. Bạn có muốn xuất file danh sách bị lỗi không?",
                  {
                    title: "Xác nhận hành động",
                    centered: true,
                    size: "sm",
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                  }
                )
                .then(async (value) => {
                  if (value) {
                    document.getElementById("exporterror").click();
                  } else {
                    return;
                  }
                });
          } else {
            this.$root.toastError("Có lỗi khi cấu hình BNM " + SetOutput);
          }
        }
      } catch (ex) {
        console.log(ex)
         this.$root.toastError("Có lỗi khi cấu hình BNM");
      }
      finally {
        this.loading(false);
      }
      //Lấy ra bất kỳ thuê bao nào
    },
    huyCongCu: async function () {
      await this.HUY_CAUHINH_BNM();
    },
    HUY_CAUHINH_BNM: async function () {
      try {
        let check = await this.KIEMTRA_GANCONG(0);

        if (!check) {
          return;
        }

        //#region Danh sách đổi cổng
        let ds = this.selectedDSTab3;
        let ds_check = this.$refs['grid-hoso-tab3'].grid.getSelectedRecords().filter(x=>x.DKCC_CHA_ID == null);
        // let ds_check = ds.filter(function (item) {
        //   return (item.CHON = "s1" && item.DKCC_CHA_ID == null);
        // });
        //#endregion

        let confirmation = await this.$bvModal
          .msgBoxConfirm(
            "Bạn có chắc chắn thực hiện hủy cấu hình cổng cũ không?",
            {
              title: "Xác nhận hành động",
              centered: true,
              size: "sm",
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy',
            }
          ).then(async ()=>{
                 await this.fHUY_CAUHINH_BNM(ds, ds_check);
          })

        // if (!confirmation) {
        //   return;
        // }


      } catch (ex) {
        console.log(ex)
        this.$root.toastError("Có lỗi khi hủy cấu hình BNM " + ex);
      } finally {
        this.$root.showLoading(false);
      }
    },
    fHUY_CAUHINH_BNM: async function (dsi, ds_check) {
      //#region Tự động giải phóng nếu có
      try {
        this.$root.showLoading(true);

        let loi_bnm = [];
        let ds = ds_check;
        let ds_con_dc = dsi;

        let count = ds_con_dc.filter(function (item) {
          return item.DKCC_CHA_ID != null;
        });

        if (count.length > 0) {
          ds_con_dc = count;
        }

        let tong = ds.length;
        console.log('tong',tong)
        for (let i = tong - 1; i >= 0; i--) {
          let dr = ds[i];
          if (
            dr.PORT_ID == null ||
            dr.NGAY_CH == null ||
            dr.PORT_ID_CU == 0 ||
            dr.VCI_VPI_ID_CU == 0 ||
            dr.NGAY_GP != null
          ) {
            let errr = {
              MA_TB: dr.MA_TB,
              THONGTIN_LOI:
                "Chưa có ngày cấu hình cổng mới hoặc không có port cũ, hoặc đã có ngày hủy cấu hình rồi không thể thực hiện tiếp",
            };
            loi_bnm.push(errr);
            continue;
          }
          try {
            let dstb = ds[i].THUEBAO_ID + ";";
            let vchuyenall = 0;
            if (ds_con_dc != null && ds_con_dc.length > 0) {
              var l = ds_con_dc.filter(function (item) {
                return item.DKCC_CHA_ID == ds[i].DKCC_ID;
              });
              if (l.length > 0) {
                vchuyenall = 1;
                l.forEach(row => { dstb = dstb + row.THUEBAO_ID + ";"; })
                // for (let row in l) {
                //   dstb = dstb + row.THUEBAO_ID + ";";
                // }
              }
            }
            dstb = dstb.slice(0, -1);

            let pass_ftth3 = await visa.lookupRASAccount(dr.MA_TB)
            console.log('pass_ftth3',pass_ftth3)
            if (pass_ftth3.startsWith("Lỗi")) {
              throw pass_ftth3
            }

            let data = {
              thuebao_id: dr.THUEBAO_ID,
              port_id: dr.PORT_ID_CU,
              vci_vpi_id: dr.VCI_VPI_ID_CU,
              loai_td: 2,
              nguoi_kh: this.$auth.getUserName(),
              chuyen_all: vchuyenall,
              ds_tb: dstb,
              trangthai: 0,
              pass_ftth: pass_ftth3,
            };

            let kq = null

            try {
                kq = await this.$root.context.post(
                  "/web-ecms/doicong-danhsach/cauhinh-bnm",
                  data
                );
            } catch (err) {
              console.log(err)
                let responseData = err.response.data
                let message = []

                if (responseData.message) {
                    message.push(responseData.message)
                }

                if (responseData.message_detail) {
                    message.push(responseData.message_detail)
                }

                if (responseData.data) {
                    let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => `${item}: ${responseData.data[item]}`)
                    message.push([...new Set(detailMessage)].join('\n'))
                }

                throw message.join('\n')
            }
             console.log('kq',kq)
            if (kq.data.RESULT == "ok") {
              try {
                await this.$root.context.post(`/web-ecms/doicong-danhsach/capnhat11-catchuyen-huycauhinh-tab3?dkcc_id=${dr.DKCC_ID}`);

                let log = {
                  ma_tb: dr.MA_TB,
                  dkcc_id: dr.DKCC_ID,
                  port_id: dr.PORT_ID_CU,
                  vci_vpi_id: dr.VCI_VPI_ID_CU,
                  dslam_vdc_id: 0,
                  flag: 3,
                  nd:
                    "Đã gửi giải phóng cổng thành công " +
                    dstb +
                    ": Cấu hình " +
                    vchuyenall,
                  status: 1,
                };

                await this.$root.context.post(
                  "/web-ecms/doicong-danhsach/them-log-catchuyen",
                  log
                );
              } catch (exxx) {
                let errr = {
                  MA_TB: dr.MA_TB,
                  THONGTIN_LOI:
                    "Giải phóng cổng cũ thành công không lưu được trạng thái !" +
                    exxx,
                };
                loi_bnm.push(errr);
                let log = {
                  ma_tb: dr.MA_TB,
                  dkcc_id: dr.DKCC_ID,
                  port_id: dr.PORT_ID_CU,
                  vci_vpi_id: dr.VCI_VPI_ID_CU,
                  dslam_vdc_id: 0,
                  flag: 3,
                  nd:
                    "Giải phóng cổng thành công " +
                    dstb +
                    " (" +
                    vchuyenall +
                    ") không lưu được trạng thái !" +
                    exxx +
                    "",
                  status: 0,
                };
                await this.$root.context.post(
                  "/web-ecms/doicong-danhsach/them-log-catchuyen",
                  log
                );
              }
            } else {
              let errr = {
                MA_TB: dr.MA_TB,
                THONGTIN_LOI:
                  "Có lỗi trong quá trình giải phóng cổng: " +
                  kq.message_detail,
              };
              loi_bnm.push(errr);
              let log = {
                ma_tb: dr.MA_TB,
                dkcc_id: dr.DKCC_ID,
                port_id: dr.PORT_ID_CU,
                vci_vpi_id: dr.VCI_VPI_ID_CU,
                dslam_vdc_id: 0,
                flag: 3,
                nd:
                  "Có lỗi trong quá trình gửi giải phỏng cổng !" +
                  kq.message_detail +
                  "",
                status: 0,
              };
              await this.$root.context.post(
                "/web-ecms/doicong-danhsach/them-log-catchuyen",
                log
              );
            }
          } catch (exx) {
            let errr = {
              MA_TB: dr.MA_TB,
              THONGTIN_LOI: "Có lỗi trong quá trình giải phóng cổng !" + exx,
            };
            loi_bnm.push(errr);
            let log = {
              ma_tb: dr.MA_TB,
              dkcc_id: dr.DKCC_ID,
              port_id: dr.PORT_ID_CU,
              vci_vpi_id: dr.VCI_VPI_ID_CU,
              dslam_vdc_id: 0,
              flag: 3,
              nd: "Có lỗi trong quá trình giải phóng cổng !" + exx + "",
              status: 0,
            };
            await this.$root.context.post(
              "/web-ecms/doicong-danhsach/them-log-catchuyen",
              log
            );
          }
        }

        if (loi_bnm.length > 0) {
          this.SetOutput = "0";
        } else {
          this.SetOutput = "1";
        }
        if (this.SetOutput == "1") {
          this.$toast.success("Đã thực hiện xong hủy cấu hình");
          await this.NAP_DS_THUEBAO_CAPCONG(this.arrayCBO.cboHoSoTab3);
        }
        else {
          if (loi_bnm != null && loi_bnm.length > 0)
          {
            this.errExcel.dataEx = [];
            this.errExcel.dataEx = loi_bnm;
            this.$bvModal
                .msgBoxConfirm(
                  "Đã thực hiện hủy cấu hình. Bạn có muốn xuất file danh sách bị lỗi không?",
                  {
                    title: "Xác nhận hành động",
                    centered: true,
                    size: "sm",
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                  }
                )
                .then(async (value) => {
                  if (value) {
                    document.getElementById("exporterror").click();
                  } else {
                    return;
                  }
                });
          }
          else
          {
            this.$root.$toast.success(
              "Tính năng không thực hiện ở giai đoạn này"
            );
          }

        }

      } catch (ex) {
        console.log(ex)
        this.$root.$toast.error("Có lỗi khi cấu hình " +ex);
      }
      finally {
        this.$root.showLoading(false);
      }
      //#endregion
    },
    themDS: function () {
      this.$root.$toast.success("Tính năng không thực hiện ở giai đoạn này");
      return;
    },
    NAP_DS_HOSO: async function () {
      //this.loading(true);
      try {
        let maTB = "";
        let input = this.arrayInputTab1.soMayAcc;

        if (this.tag != 3 && input != "" && this.activetab == 1) { // update source BSS-77693 - 27-12-2022
          let dsTB = await this.$root.context.get(
            "/web-ecms/doicong-danhsach/lay-ds-thuebao",
            { matb: input }
          );
          if (dsTB.data.length > 0) {
            maTB = dsTB.data[0].MA_TB;
          } else {
            this.$root.toastError(
              "Không tìm thấy Số máy/ACC " + input + " "
            );
            return;
          }
        }
        let data = {
          tag: this.tag,
          maThueBao: maTB,
          cboTths: this.arrayCBO.cboTTHS,
          tuNgay:
            this.reloadAfterDelete == 1
              ? this.convertDate2String(this.arrayDate.fromDate)
              : this.reloadAfterUpdate == 1
              ? this.convertDate2String(this.arrayDate.fromDate)
              : this.convertDate2String(this.arrayDate.fromDate),
          denNgay:
            this.reloadAfterDelete == 1
              ? this.convertDate2String(this.arrayDate.toDate)
              : this.reloadAfterUpdate == 1
              ? this.convertDate2String(this.arrayDate.toDate)
              : this.convertDate2String(this.arrayDate.toDate),
        };
        let rs = await this.$root.context.post(
          "/web-ecms/doicong-danhsach/lay-ds-hoso1",
          data
        );
        if (rs.error_code === "BSS-00000000") {
          if (rs.data.length > 0){
            this.arrayDataGrids.dsHoSoTab1 = rs.data.map((i,index) => ({...i, STT:++index}));
          }
          else {
            this.arrayDataGrids.dsHoSoTab1 = []
            this.clear();
            this.enableButtonTab1 = false;
            this.buttonState.isDisableCbo = false;
            this.buttonState.isDisableCboTT = true;
            this.isNew = true;
            this.arrayCBO.cboKieuCC = 7
            this.arrayCBO.cboHoSo = 3
            this.arrayCBO.cboTrangThai = 1
            this.arrayInputTab1.tenHoSo = this.$root.token.getUserName() + ' ' + this.convertDate2String(this.arrayDate.startDate);
            this.arrayInputTab1.chkChuyen119 = true;
          }
        } else {
          this.$root.$toast.error("Có lỗi xẩy ra trong quá trình lấy dữ liệu.");
        }
      } catch (e) {
        console.log(e)
        // this.$root.$toast.error('err '+e)
        this.$root.toastError(e.response.data.message_detail);
      } finally {
       // this.loading(false);
      }
    },
    txtMaTB_KeyPress() {
      if (this.activetab == 1) {
        if (this.arrayInputTab1.soMayAcc == null || this.arrayInputTab1.soMayAcc == "") {
          this.$root.$toast.error("Bạn phải nhập thông tin số máy/acc hoặc mã MEN");
          return
        }
      }
      if (this.tag != "3") {
        this.NAP_DS_HOSO();
      } else {
        this.NAP_DS_THUEBAO_VAO_HS();
      }
    },
    NAP_DS_THUEBAO_VAO_HS() {
      try {
        if (this.arrayInputTab1.soMayAcc != this.ma_tb_ts_internet) {
          this.$root.$toast.error("Bạn chỉ được đổi cổng với thuê bao " + this.ma_tb_ts_internet + "");
          return;
        }
        this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn thêm " + this.ma_tb_ts_internet + " vào danh sách cắt chuyển không?",
          {
            title: "VNPT",
            centered: true,
            size: "sm",
            okTitle: 'Đồng ý',
            cancelTitle: 'Không đồng ý',
          }).then(async (value) => {
          if (value) {
            let hscc_id = this.arrayCBO.cboHoSoTab2
            if (hscc_id == -1 || hscc_id == 0) {
              this.$root.$toast.error("Không lấy được thông tin hồ sơ");
              return;
            }
            let MATB = true;
            let dichvu_fix = "4,8,7,11";
            let dem = await this.$root.context.get("web-ecms/doicong-danhsach/kiemtra_thongtin_matb_dichvu_danhba", {
              ma_tb: this.ma_tb_ts_internet,
              dichvuvt_ids: dichvu_fix
            })
            if (dem && dem.data && dem.data.length > 0) {
              this.$root.$toast.error("Mã thuê bao " + this.ma_tb_ts_internet + " chưa có trong trong danh bạ\r\n" + " hoặc không đúng dịch vụ");
              return;
            }
            dem = await this.$root.context.get("web-ecms/doicong-danhsach/kiemtra_hoso_theo_matb", {
              ma_tb: this.ma_tb_ts_internet,
            })
            if (dem && dem.data && dem.data.length > 0) {
              this.$root.$toast.error("Mã thuê bao đang thực hiển dở tại " + dem.data[0]["HOSO"] + " bạn không thể tiếp tục đổi cổng.");
              return;
            }
            dem = await this.$root.context.get("web-ecms/doicong-danhsach/kiemtra_dieukien_doicong_theo_matb", {
              ma_tb: this.ma_tb_ts_internet,
            })
            if (dem && dem.data == 0) {
              this.$root.$toast.error("Mã thuê bao " + this.ma_tb_ts_internet + " không thuộc dịch vụ cho phép thực hiện \r\n" + " hoặc không đúng dịch vụ.");
              return;
            }
            let dsDL_imp = await this.$root.context.get("web-ecms/doicong-danhsach/lay_ds_thuebao_doicong_internet", {
              ma_tb: this.ma_tb_ts_internet,
            })
            this.arrayImportFile.ds_file = dsDL_imp.data
            let cungdoicap = this.arrayInputTab1.chkCungDoiCap ? 1 : 0;
            let kq = await this.$root.context.post(
              `/web-ecms/doicong-danhsach/them-ds-madoicap?hsccId=${this.arrayCBO.cboHoSoTab2}&cungdoiCap=${cungdoicap}`,
              this.arrayImportFile.ds_file
            );

            if (kq.error_code === "BSS-00000000") {
              this.$toast.success("Thêm thuê bao thành công");
              this.NAP_DS_THUEBAO_CATCHUYEN();
            } else {
              this.$root.$toast.error(kq.message_detail);
            }
          } else {
            return
          }
        }).catch(e => { throw e})
      } catch (e) {
        console.log(e)
        this.$root.$toast.error("Có lỗi khi tìm kiếm thuê bao ");
      }
    },
    cbb_selectedChanged: async function (data) {
      try {
        this.loading(true);
        if(data !=null){
        this.arrayCBO.cboHoSoTab2 = data.HSCC_ID;
        console.log("data", data);
        await this.NAP_DS_THUEBAO_CATCHUYEN();
        }
      } catch (error) {
        console.log(error)
        // this.$toast.error(error)
       // this.$root.toastError(error.response.data.message_detail);
      } finally {
        this.loading(false);
      }
      this.arrayInputTab1.chkGiuVciVpi = false;
    },
    cbbDslam_selectedChanged: async function (data) {
      try {
        this.enableSelectFollowPortTD = false;
        this.loading(true);
        let selectedDslam = data;
        if (data == null) {
          this.arrayDataGridsdsLuoiPortTab3 = [];
          return;
        }
        this.arrayCBO.cboDslamTab3 = data.DSLAM_ID;
        if (selectedDslam != null) {
          this.arrayCBO.cboDslamIDDsPort = selectedDslam.DSLAM_ID;
          this.arrayCBO.cboDslamPort = selectedDslam;
          let port_td = selectedDslam.PORT_TD;
          this.sPort_TD = selectedDslam.PORT_TD;
          if (port_td == -1) return;
          if (port_td == 1) {
            this.sPort_TD = true;
            this.enableSelectFollowPortTD = false;
            await this.NAP_DS_PORT_FIX();

          }
          else {
            this.sPort_TD = false;
            this.enableSelectFollowPortTD = true;
            await this.NAP_DS_LUOI_PORT();
            await this.NAP_DS_LUOI_VCIVPI();
          }
        }
      } catch (ex) {
         console.log(error)
        //this.$root.toastError(ex.response.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    NAP_DS_THUEBAO_CATCHUYEN: async function () {
      try {
        this.loading(true);
        let hscc_id = this.arrayCBO.cboHoSoTab2;
        let rs
        if (this.tag == 3) {
          rs = await this.$root.context.get("web-ecms/doicong-danhsach/lay_ds_tb_catchuyen_thaydoi_internet", {
            hsccId: hscc_id,
            vma_tb: this.ma_tb_ts_internet
          })
        } else {
          rs = await this.$root.context.get(
            `/web-ecms/doicong-danhsach/lay-ds-thuebao-catchuye-tab2?hsccId=${hscc_id}`
          );
        }
        if (rs && rs.data && rs.data.length > 0) {
          let result = rs.data;
          const newState = result.map((obj, index) =>
            obj.HOLD_VCI_VPI == "1"
              ? {
                  ...obj,
                  STT: ++index,
                  // HOLD_VCI_VPI:
                  //   '<span style="margin-left: 45%"><i class="fa fa-check"></i></span>',
                  // HOLD_VCI_VPI:
                  //   '<div class="check-action"><input type="checkbox" class="check"><span class="name"></span> </div>',
                }
              : {
                  ...obj,
                  STT: ++index,
                  // HOLD_VCI_VPI:
                  //   '<div class="check-action"><input type="checkbox" class="check"><span class="name"></span> </div>',
                }
          );
          this.arrayDataGrids.dsHoSoTab2 = newState;
        } else {
          this.arrayDataGrids.dsHoSoTab2 = [];
        }
      } catch (error) {
        // this.$toast.error(error)
       // this.$root.toastError(error.response.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    chooseFiles: function () {
      try {
        if (this.arrayCBO.cboHoSoTab2 == 0 || this.arrayCBO.cboHoSoTab2 == -1) {
          this.$refs.cboHSCC.focus();
          this.isError = true;
          this.$toast.error("Chưa chọn hồ sơ cắt chuyển");
          return;
        }
        document.getElementById("fileUpload").click();
      } catch (error) {
        this.$toast.error(' '+error);
      }
    },
    onChange: async function () {
      try {
        this.$root.showLoading(true);

        this.arrayImportFile.filexcel = this.$refs.fileUpload.$el.querySelector('input').files[0]
        this.$refs.fileUpload.reset()

        if (!this.arrayImportFile.filexcel) {
          return
        }

        let checkPopup = true;
        let json_list = [];
        if (this.checkfile()) {
          let tencot;
          const reader = new FileReader();
          reader.onload = (e) => {
            /* Parse data */
            const bstr = e.target.result;
            const wb = XLSX.read(bstr, { type: "binary" });
            /* Get first worksheet */
            const wsname = wb.SheetNames[0];
            const ws = wb.Sheets[wsname];
            /* Convert array of arrays */
            const data = XLSX.utils.sheet_to_json(ws, { header: 1 });
            tencot = data[0];
            if (tencot.length > 1) {
              checkPopup = false
              this.$toast.error("Dữ liệu file excel chưa đúng số lượng cột");
              return false;
            }
            if (tencot == null) {
              checkPopup = false
              this.$toast.error("Không đọc được dữ liệu từ file");
              return false;
            }
            if (tencot.length == 1) {
              if (tencot[0] != "MA_TB" && tencot[0] != "MA_LT") {
                checkPopup = false
                this.$toast.error("Không tìm thấy cột mã TB hoặc mã LT");
                return false;
              }
            }

            for (let i = 1; i < data.length; i++) {
              json_list.push(data[i]);
            }
          };
          reader.readAsBinaryString(this.arrayImportFile.filexcel);
          let ck = await this.$root.context.get(
            "/web-ecms/doicong-danhsach/lay_trangthai_catchuyen_id",
            { hscc_id: this.ttcc_id }
          );
          if (ck == null || ck.data.length == 0) {
            this.$root.$toast.error(
              "Không lấy được trạng thái thông tin hồ sơ"
            );
            return;
          }
          if (ck.data.TTCC_ID == 6) {
            this.$root.$toast.error(
              "Trạng thái hồ sơ đã hoàn thành không thể cập nhật"
            );
            return;
          }
          //Check Mã TB

          if (tencot[0].toUpperCase() == "MA_TB") {
            json_list = json_list.map(function (x) {
              return {
                MA_TB: x[0],
              };
            });
            if (json_list.length == 0) {
              checkPopup = false;
              this.$root.$toast.error("Không có dữ liệu trong file excel");
              return;
            }
            this.checkMaTB = true;
            let dem = await this.$root.context.post(
              "/web-ecms/doicong-danhsach/kiemtra-matb1-madoicap-ins-tab2",
              json_list
            );
            if (dem != null && dem.data.length > 0) {
              this.errExcel.dataEx = Object.values(dem.data)
              checkPopup = false;
              this.$bvModal
                .msgBoxConfirm(
                  "Có " +
                    dem.data.length +
                    " mã thuê bao chưa có trong trong danh bạ\r\n" +
                    " hoặc không đúng dịch vụ. Bạn có muốn xuất file không?",
                  {
                    title: "Xác nhận hành động",
                    centered: true,
                    size: "sm",
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                  }
                )
                .then((value) => {
                  if (value) {
                    var ma_thuebao = XLSX.utils.json_to_sheet(this.errExcel.dataEx);
                    var wb = XLSX.utils.book_new(); // make Workbook of Excel
                    XLSX.utils.book_append_sheet(wb, ma_thuebao, "MA_TB"); // sheetAName is name of Worksheet
                    // export Excel file
                    XLSX.writeFile(wb, "ma_thue_bao.xlsx");
                    return;
                  } else {
                    return;
                  }
                });
                return
            }

            dem = await this.$root.context.post(
              "/web-ecms/doicong-danhsach/kiemtra-matb2-madoicap-ins-tab2",
              json_list
            );

            if (dem != null && dem.data.length > 0) {
              this.errExcel.dataEx = Object.values(dem.data)
              checkPopup = false;
              this.$bvModal
                .msgBoxConfirm(
                  "Có " +
                    dem.data.length +
                    " mã thuê bao đang thực hiện dở, bạn không thể tiếp tục đổi cổng. Bạn có muốn xuất file excel không?",
                  {
                    title: "Xác nhận hành động",
                    centered: true,
                    size: "sm",
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                  }
                )
                .then((value) => {
                  if (value) {
                    // this.errExcel.dataEx = Object.values(dem.data)
                    var ma_thuebao = XLSX.utils.json_to_sheet(Object.values(this.errExcel.dataEx));
                    var wb = XLSX.utils.book_new(); // make Workbook of Excel
                    XLSX.utils.book_append_sheet(wb, ma_thuebao, "MA_TB"); // sheetAName is name of Worksheet
                    // export Excel file
                    XLSX.writeFile(wb, "ma_thue_bao.xlsx");
                    return;
                  } else {
                    return;
                  }
                });
                return
            }

            dem = await this.$root.context.post(
              "/web-ecms/doicong-danhsach/kiemtra-matb3-madoicap-ins-tab2",
              json_list
            );
            if (dem != null && dem.data.length > 0) {
              this.errExcel.dataEx = Object.values(dem.data)
              checkPopup = false;
              this.$bvModal
                .msgBoxConfirm(
                  "Có " +
                    dem.data.length +
                    " mã thuê bao không thuộc dịch vụ cho phép thực hiện \r\n" +
                    " hoặc không đúng dịch vụ. Bạn có muốn xuất file không?",
                  {
                    title: "Xác nhận hành động",
                    centered: true,
                    size: "sm",
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                  }
                )
                .then((value) => {
                  if (value) {
                    var ma_thuebao = XLSX.utils.json_to_sheet(this.errExcel.dataEx);
                    var wb = XLSX.utils.book_new(); // make Workbook of Excel
                    XLSX.utils.book_append_sheet(wb, ma_thuebao, "MA_TB"); // sheetAName is name of Worksheet
                    // export Excel file
                    XLSX.writeFile(wb, "ma_thue_bao.xlsx");
                    return;
                  } else {
                    return;
                  }
                });
                return
            }
          }
          // Check Mã LT
          if (tencot[0].toUpperCase() == "MA_LT") {
            json_list = json_list.map(function (x) {
              return {
                MA_LT: x[0],
              };
            });

            if (json_list.length == 0) {
              checkPopup = false;
              this.$root.$toast.error("Không có dữ liệu trong file excel");
              return;
            }
            this.checkMaTB = false;

            let dem = await this.$root.context.post(
              "/web-ecms/doicong-danhsach/kiemtra-malt1-madoicap-ins-tab2",
              json_list
            );
            if (dem != null && dem.data.length > 0) {
              this.errExcel.dataEx = Object.values(dem.data)
              checkPopup = false;
              this.$bvModal
                .msgBoxConfirm(
                  "Có " +
                    dem.data.length +
                    " mã ảo chưa có trong trong danh bạ\r\n" +
                    " hoặc không đúng dịch vụ. Bạn có muốn xuất file không?",
                  {
                    title: "Xác nhận hành động",
                    centered: true,
                    size: "sm",
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                  }
                )
                .then((value) => {
                  if (value) {
                    var ma_thuebao = XLSX.utils.json_to_sheet(this.errExcel.dataEx);
                    var wb = XLSX.utils.book_new(); // make Workbook of Excel
                    XLSX.utils.book_append_sheet(wb, ma_thuebao, "MA_TB"); // sheetAName is name of Worksheet
                    // export Excel file
                    XLSX.writeFile(wb, "ma_thue_bao.xlsx");
                    return;
                  } else {
                    return;
                  }
                });
                return
            }

            dem = await this.$root.context.post(
              "/web-ecms/doicong-danhsach/kiemtra-malt2-madoicap-ins-tab2",
              json_list
            );
            if (dem != null && dem.data.length > 0) {
              this.errExcel.dataEx = Object.values(dem.data)
              checkPopup = false;
              this.$bvModal
                .msgBoxConfirm(
                  "Có " +
                    dem.data.length +
                    " mã thuê bao đang thực hiển dở bạn không thể tiếp tục đổi cổng.Bạn có muốn file excel không?",
                  {
                    title: "Xác nhận hành động",
                    centered: true,
                    size: "sm",
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                  }
                )
                .then((value) => {
                  if (value) {
                    var ma_thuebao = XLSX.utils.json_to_sheet(this.errExcel.dataEx);
                    var wb = XLSX.utils.book_new(); // make Workbook of Excel
                    XLSX.utils.book_append_sheet(wb, ma_thuebao, "MA_TB"); // sheetAName is name of Worksheet
                    // export Excel file
                    XLSX.writeFile(wb, "ma_thue_bao.xlsx");
                    return;
                  } else {
                    return;
                  }
                });
                return
            }

            dem = await this.$root.context.post(
              "/web-ecms/doicong-danhsach/kiemtra-malt3-madoicap-ins-tab2",
              json_list
            );
            if (dem != null && dem.data.length > 0) {
              this.errExcel.dataEx = Object.values(dem.data)
              checkPopup = false;
              this.$bvModal
                .msgBoxConfirm(
                  "Có " +
                    dem.data.length +
                    " mã thuê bao không thuộc dịch vụ cho phép thực hiện \r\n" +
                    " hoặc không đúng dịch vụ. Bạn có muốn xuất file không?",
                  {
                    title: "Xác nhận hành động",
                    centered: true,
                    size: "sm",
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                  }
                )
                .then((value) => {
                  if (value) {
                    var ma_thuebao = XLSX.utils.json_to_sheet(this.errExcel.dataEx);
                    var wb = XLSX.utils.book_new(); // make Workbook of Excel
                    XLSX.utils.book_append_sheet(wb, ma_thuebao, "MA_TB"); // sheetAName is name of Worksheet
                    // export Excel file
                    XLSX.writeFile(wb, "ma_thue_bao.xlsx");
                    return;
                  } else {
                    return;
                  }
                });
                return
            }
          }

          this.arrayImportFile.dsImportExcel = json_list;
          if (checkPopup) {
            this.$root.showLoading(false);
            let confirmation = await this.$bvModal
              .msgBoxConfirm(
                "Bạn có muốn thêm danh sách thuê bao vào hồ sơ " +
                  this.arrayCBO.cboHoSoTab2 +
                  " không?",
                {
                  title: "Xác nhận hành động",
                  centered: true,
                  size: "sm",
                  okTitle: 'Đồng ý',
                  cancelTitle: 'Hủy',
                }
              )

            if (!confirmation)
            {
              return
            }
            this.$root.showLoading(true);
            await this.okThemDS();
          }
          this.arrayImportFile.filexcel = []
        }
      } catch (error) {
        //this.$toast.error("Có lỗi xảy ra: " + error)
        this.$root.toastError(
          "Có lỗi xảy ra: " + error.response.data.message_detail
        );
        return;
      } finally {
        this.$root.showLoading(false);
      }
    },
    okThemDS: async function () {
      let json_list = this.arrayImportFile.dsImportExcel;
      let dsDL_imp;
      if (this.checkMaTB) {
        dsDL_imp = await this.$root.context.post(
          "/web-ecms/doicong-danhsach/get-infor-matb-madoicap-ins-tab2",
          json_list
        );
      } else {
        dsDL_imp = await this.$root.context.post(
          "/web-ecms/doicong-danhsach/get-infor-malt-madoicap-ins-tab2",
          json_list
        );
      }
      this.arrayImportFile.ds_file = []
      for (let i = 0; i < dsDL_imp.data.length; i++) {
        let data = {
          THUEBAO_ID: dsDL_imp.data[i].THUEBAO_ID,
          MA_TB: dsDL_imp.data[i].MA_TB,
          TEN_TB: dsDL_imp.data[i].TEN_TB,
          DIACHI_LD: dsDL_imp.data[i].DIACHI_LD,
          PORTVL_ID: dsDL_imp.data[i].PORTVL_ID,
          VCI_VPI_ID: dsDL_imp.data[i].VCI_VPI_ID,
          PORT_ID: dsDL_imp.data[i].PORT_ID,
          MADOICAP: dsDL_imp.data[i].MADOICAP,
        };
        this.arrayImportFile.ds_file.push(data);
      }
      let cungDoiCap = this.arrayInputTab1.chkCungDoiCap ? 1 : 0
      let kq = await this.$root.context.post(
        `/web-ecms/doicong-danhsach/them-ds-madoicap?hsccId=${this.arrayCBO.cboHoSoTab2}&cungDoiCap=${cungDoiCap}`,
        this.arrayImportFile.ds_file
      );

      if (kq.error_code === "BSS-00000000") {
        this.$toast.success("Thêm danh sách thuê bao thành công!");
        this.NAP_DS_THUEBAO_CATCHUYEN();
      } else {
        this.$toast.error(kq.message_detail);
      }
    },
    kiemTraDuLieu: function (tencot) {
      if (tencot.length > 1) {
        this.$toast.error("Dữ liệu file excel chưa đúng số lượng cột");
        return false;
      }
      if (tencot === null) {
        this.$toast.error("Không đọc được dữ liệu từ file");
        return false;
      }
      if (tencot.length == 1) {
        if (tencot[0] != "MA_TB" && tencot[0] != "MA_LT") {
          this.$toast.error("Không tìm thấy cột mã TB hoặc mã LT");
          return false;
        }
      }
      return true;
    },
    xoaDSHS: async function () {
      let checkPopup = true;
      if (this.arrayDataGrids.dsHoSoTab2 == null) {
        this.$toast.error("Không có danh sách để thực hiện xóa");
        return;
      }
      if (this.arrayCBO.cboHoSoTab2 == 0 || this.arrayCBO.cboHoSoTab2 == -1) {
        checkPopup = false
        this.$refs.cboHSCC.focus();
        this.isError = true;
        this.$toast.error("Chưa chọn hồ sơ cắt chuyển");
        return;
      }
      let ck = await this.$root.context.get(
        "/web-ecms/doicong-danhsach/lay_trangthai_catchuyen_id",
        { hscc_id: this.ttcc_id }
      );
      if (ck == null || ck.data.length == 0) {
        checkPopup = false
        this.$root.$toast.error("Không lấy được trạng thái thông tin hồ sơ");
        return;
      }
      if (ck.data.TTCC_ID == 6) {
        checkPopup = false
        this.$root.$toast.error(
          "Trạng thái hồ sơ đã hoàn thành không thể cập nhật"
        );
        return;
      }
      let dshoso = this.selectedDSTab2;
      let ds_check = dshoso.filter(function (item) {
        return (item.CHON = "s1");
      });
      if (ds_check.length == 0) {
        checkPopup = false
        this.$toast.error("Bạn chưa chọn thuê bao để xóa");
        return;
      }

      let result = ds_check.map((item) => ({
            DKCC_CHA_ID: item.DKCC_CHA_ID,
            DKCC_ID: item.DKCC_ID,
            HSCC_ID: item.HSCC_ID
          }));
      let rs = await this.$root.context.post(
        "/web-ecms/doicong-danhsach/check-infor-before-xoads-tab2",
        result
      );
      if (rs.data.RESULTS != 0) {
        checkPopup = false
        this.$toast.error(
          "Có thuê bao đã bị thay đổi trạng thái bạn không thể xóa khỏi danh sách"
        );
      }
      if (checkPopup) {
        this.$bvModal
          .msgBoxConfirm(
            "Bạn có muốn xóa những thuê bao đã chọn khỏi danh sách cắt chuyển cổng ?",
            {
              title: "Xác nhận hành động",
              centered: true,
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy',
              size: "sm",
            }
          )
          .then(async (value) => {
            if (value) {
              await this.confirmXoaDSHS(result);
            } else {
              return;
            }
          });
      }
    },
    confirmXoaDSHS: async function (result) {
      await this.$root.context.post(
        "/web-ecms/doicong-danhsach/xoa-ds-catchuyen",
        result
      ).then( async rs => {
        if (rs.error_code === "BSS-00000000") {
          await this.NAP_DS_THUEBAO_CATCHUYEN();
          this.$toast.success("Xóa thành công.");
        }
      }).catch(e => {
          this.$toast.error(e.response.data.message_detail);
      })

    },
    confirmGiuVCICPI: async function (result) {
      try {
        this.loading(true);

        let rs = await this.$root.context.post(
          "/web-ecms/doicong-danhsach/giu-vci-cpi",
          result
        );
        console.log('ressult ',rs)
        if (rs.error_code === "BSS-00000000") {
          await this.NAP_DS_THUEBAO_CATCHUYEN();
          this.$toast.success(
            "Đã giữ vci vpi thành công " +
              rs.data[0].RESULT +
              " / " +
              result.length +
              " thuê bao"
          );
        } else {
          this.$toast.error(rs.message_detail);
        }
      } catch (error) {
        this.$root.toastError("Error " + error.response.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    checkfile: function () {
      if (this.arrayImportFile.filexcel == null) {
        this.$toast.error("Chưa chọn file nào!");
        return false;
      }
      console.log('type ',this.arrayImportFile.filexcel.type)
      if (
        this.arrayImportFile.filexcel.type !=
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" && this.arrayImportFile.filexcel.type !== "application/vnd.ms-excel"
      ) {
        this.$toast.error("File không phải định dạng xlsx hoặc xls!");
        return false;
      }
      return true;
    },
    onClickdsHoSoTab1: async function (data) {
      if (data != null) {
        this.create_done = false
        this.buttonState.isDisableCbo = true;
        this.buttonState.isDisableCboTT = true;
        this.buttonState.isDisableBtnXoa = false;
        this.hscc_id = data.HSCC_ID;
        this.ttcc_id = data.HSCC_ID;
        this.arrayCBO.cboKieuCC = data.KIEUCC_ID
        this.arrayCBO.cboHoSo = data.LOAIHSCC_ID
        this.arrayCBO.cboTrangThai = data.TTCC_ID
        this.arrayInputTab1.tenHoSo = data.TEN_HS;
        this.arrayDate.startDate = data.NGAYBD_DK;
        this.arrayDate.endDate = data.NGAYKT_DK;
        this.arrayInputTab1.ghiChu = data.GHICHU;
        this.arrayDataXoa.soTBDK = data.SO_TB_DK;
      }
    },
    onClickdsHoSoTab2: async function (data) {
      if (data != null) {
      }
    },

    selectedItemsTab2: async function (data) {
      this.selectedDSTab2 = [];
      this.arrayDataGrids.dsHoSoTab2.forEach((item) => {
          if (data.includes(item.MA_TB)) {
            item.CHON = "s1";
          } else {
            item.CHON = "s0";
          }
      });
     this.selectedDSTab2 = this.arrayDataGrids.dsHoSoTab2.filter(function (
        el
      ) {
        return el.CHON != "s0";
      });
    },
    selectedItemsHso: async function (data) {
      this.selectedDSTab3 = [];
      this.arrayDataGrids.dsHoSoTab3.forEach((item) => {
        if (data.includes(item.DKCC_ID) && item.DKCC_CHA_ID == null) { // DKCC_CHA_ID != null là thuê bao cùng đôi cáp
          item.CHON = "s1";
        } else {
          item.CHON = "s0";
        }
      });
      this.selectedDSTab3 = this.arrayDataGrids.dsHoSoTab3.filter(function (
        el
      ) {
        return el.CHON != "s0";
      });
      this.arraySelectedItem.soChonAcc = this.selectedDSTab3.length;
    },
    selectedItemsPort: async function (data) {
      this.arrayCBO.cboSelectdRowPort = [...data]
      this.arraySelectedItem.soPort = data.length;
    },
    selectedItemsPortFix: async function (data) {
      this.arraySelectedItem.soPort = data.length;
      this.selectedPortFix = [];
      let arraySelected = [];
      // this.selectedPortFix = this.arrayDataGrids.dsPortFixTab3.forEach(item =>{
      //   if(data.includes(item.PORT_ID)){
      //     item.CHON = "1"
      //   }else{
      //     item.CHON = "0"
      //   }
      // })
      arraySelected = this.arrayDataGrids.dsPortFixTab3;
      for (let i = 0; i < data.length; i++) {
        let row = data[i];
        const check = (obj) => obj.PORT_ID == row;
        if (arraySelected.some(check)) {
          //Find index of specific object using findIndex method.
          let objIndex = arraySelected.findIndex(check);
          //Update object's name property.
          arraySelected[objIndex].ISCHECK = "1";
        }
      }
      var filtered = arraySelected.filter(function (el) {
        return el.ISCHECK != "0";
      });
      this.selectedPortFix = filtered;
    },
    selectedItemsVCI: async function (data) {
      this.arrayCBO.cboSelectdRowVciVpi = [...data]
      this.arraySelectedItem.soVciVpi = data.length;
    },
    giuVciVpi: async function () {
      let checkPopup = true;
      if (this.arrayDataGrids.dsHoSoTab2 == null || this.arrayDataGrids.dsHoSoTab2.length == 0) {
        checkPopup = false
        this.$toast.error("Không có danh sách để thực hiện giữ vci vpi");
        return;
      }
      if (this.arrayCBO.cboHoSoTab2 == 0 || this.arrayCBO.cboHoSoTab2 == -1) {
        checkPopup = false
        this.$refs.cboHSCC.focus();
        this.isError = true;
        this.$toast.error("Chưa chọn hồ sơ cắt chuyển");
        return;
      }
      let ck = await this.$root.context.get(
        "/web-ecms/doicong-danhsach/lay_trangthai_catchuyen_id",
        { hscc_id: this.ttcc_id }
      );
      if (ck == null || ck.data.length == 0) {
        checkPopup = false
        this.$root.$toast.error("Không lấy được trạng thái thông tin hồ sơ");
        return;
      }
      if (ck.data.TTCC_ID == 6) {
        checkPopup = false
        this.$root.$toast.error(
          "Trạng thái hồ sơ đã hoàn thành không thể cập nhật"
        );
        return;
      }
      let ds_check = this.selectedDSTab2.filter(item => item.CHECKED == "1" && item.CHON == "s1");
      if (ds_check.length == 0) {
        checkPopup = false
        this.$toast.error("Bạn chưa chọn thuê bao để giữ danh sách");
        return;
      }
      let result = ds_check.map((item) => ({
            DKCC_CHA_ID: item.DKCC_CHA_ID,
            DKCC_ID: item.DKCC_ID
          }));
      let rs = await this.$root.context.post(
        "/web-ecms/doicong-danhsach/check-infor-before-xoads-tab2",
        result
      );
      // if (rs.data.RESULTS != 0) {
      //   checkPopup = false
      //   this.$toast.error(
      //     "Có thuê bao đã bị thay đổi trạng thái bạn không thể chọn giữ port vci vpi hay không"
      //   );
      // }
      if (checkPopup) {
        this.$bvModal
          .msgBoxConfirm(
            "Bạn có muốn giữ vci vpi các thuê bao đã chọn trong danh sách cắt chuyển cổng không?",
            {
              title: "Xác nhận hành động",
              centered: true,
              size: "sm",
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy',
            }
          )
          .then(value => {
            if (value) {
              this.confirmGiuVCICPI(result);
            } else {
              return;
            }
          });
      }
    },
    LOAD_HOSO_CATCHUYEN: async function () {
      try {
      //  this.loading(true);
       console.log('LOAD_HOSO_CATCHUYEN')
        let maTB = "";
        let input = this.arrayInputTab1.soMayAcc;
        if (this.tag != 3 && input != "" && this.activetab == 2) {
          let dsTB = await this.$root.context.get(
            "/web-ecms/doicong-danhsach/lay-ds-thuebao",
            { matb: input }
          );
          if (dsTB.data != null && dsTB.data.length > 0) {
            maTB = dsTB.data[0].MA_TB;
          } else {
            this.$root.toastError(
              "Không tìm thấy số Số máy/ACC " + input + " "
            );
            return;
          }
        }
        let data = {
          tag: this.tag,
          maThueBao: maTB,
          cboTths: this.arrayCBO.cboTTHS,
          tuNgay:
            this.activetab == 2
              ? this.convertDate2String(this.arrayDate.fromDate)
              : this.convertDate2String(this.arrayDate.fromDate),
          denNgay:
            this.activetab == 2
              ? this.convertDate2String(this.arrayDate.toDate)
              : this.convertDate2String(this.arrayDate.toDate),
        };
        let rs = await this.$root.context.post(
          "/web-ecms/doicong-danhsach/lay-ds-hoso-catchuyen1",
          data
        );
         console.log('lay-ds-hoso-catchuyen',rs)
        if (rs.error_code === "BSS-00000000") {
          this.arrayDataToCbo.dsHoSoCCTab2 = rs.data;
          if (rs.data != null &&  rs.data.length > 0) {
            this.setcboHSTab2(this.hscc_id)
          }
          this.arrayDataGrids.dsHSCCTab2Inp = rs.data;
        } else {
          this.$root.$toast.error("Có lỗi xẩy ra trong quá trình lấy dữ liệu.");
        }
      } catch (e) {
        console.log(e)
       // this.$root.toastError("Error " + e.response.data.message_detail);
      } finally {
       // this.loading(false);
      }
    },
    setcboHSTab2(hscc_id) {
      let grid = this.$refs['cboHSTab2'].$children[0].grid
      this.$nextTick(() => {
        let idx = this.arrayDataToCbo.dsHoSoCCTab2.findIndex(item => item.HSCC_ID == hscc_id)
        if (idx > -1) setTimeout(()=> grid.selectRow(idx),200)
      })
    },
    setcboHSTab3(hscc_id) {
      let grid = this.$refs['cboHSTab3'].$children[0].grid
      this.$nextTick(() => {
        let idx = this.arrayDataToCbo.dsHoSoCCTab3.findIndex(item => item.HSCC_ID == hscc_id)
        if (idx > -1) setTimeout(()=> grid.selectRow(idx),200)
      })
    },
    NAP_DS_TRAMTBI: async function () {
      try {
        let ck = await this.$root.context.get(
          "/web-ecms/doicong-danhsach/lay-ds-tramtbi"
        );
        console.log('NAP_DS_TRAMTBI ',ck)
        this.arrayDataToCbo.dsTramTBTab3 = ck.data;
        if (ck.data != null &&  ck.data[0].DONVI_ID > 0) {
          this.arrayCBO.cboTramTBTab3 = ck.data[0].DONVI_ID;
           this.NAP_DS_DSLAM();
        }
      } catch (e) {
        this.$root.toastError("Error " + e.response.data.message_detail);
      } finally {
      }
    },
    NAP_DS_TRANGTHAI_VCIVPI: async function () {
      try {
        let ck = await this.$root.context.get(
          "/web-ecms/doicong-danhsach/lay-ds-trangthai-vcivpi"
        );
        this.arrayDataToCbo.dsTTVciVpiTab3 = ck.data;
        console.log("dstrangthai ", ck.data);
        if (ck.data != null &&  ck.data[0].DONVI_ID > 0)
        this.arrayCBO.cboTrangthaiVCIVPITab3 = ck.data[0].TT_PORT_ID;
      } catch (e) {
        this.$root.toastError("Error " + e.response.data.message_detail);
      } finally {
      }
    },
    NAP_DS_QUYHOACH: async function () {
      this.arrayDataToCbo.dsKieuQuyhoachTab3 = [
        { QUYHOACH_ID: "2", QUYHOACH: "2 lớp" },
        { QUYHOACH_ID: "1", QUYHOACH: "1 lớp" },
      ];
      this.arrayCBO.cboKieuQuyhoachTab3 =
        this.arrayDataToCbo.dsKieuQuyhoachTab3[0].QUYHOACH_ID;
    },
    NAP_DS_QH: async function () {
      this.arrayDataToCbo.dsLoaiQuyhoachTab3 = [
        { LOAIKN_ID: "1", KETNOI: "Nội tỉnh" },
        { LOAIKN_ID: "2", KETNOI: "Liên tỉnh" },
        { LOAIKN_ID: "3", KETNOI: "Internet trực tiếp" },
        { LOAIKN_ID: "4", KETNOI: "Fiber" },
      ];
      console.log("a ", this.arrayDataToCbo.dsLoaiQuyhoachTab3);
      this.arrayCBO.cboLoaiQuyhoachTab3 =
        this.arrayDataToCbo.dsLoaiQuyhoachTab3[0].LOAIKN_ID;
    },
    LAY_TS_PORTD:  function () {
      try {
        let t = this.arrayDataToCbo.dsDslamTab3;//this.arrayCBO.cboDslamPort;
        if (t == null || t.length == 0) {
          return "-1";
        }
        return this.arrayDataToCbo.dsDslamTab3[0].PORT_TD
        // if(this.arrayCBO.cboDslamTab3 > 0){
        //   let selected = t.filter(obj => {
        //     return obj.PORT_TD === this.arrayCBO.cboDslamTab3
        //   })
        //   return selected.PORT_TD
        //}
      } catch (e) {
        return "-1";
      }
    },
    NAP_DS_PORT_FIX: async function () {
      try {
        //this.loading(true);
        let dslamID = this.arrayCBO.cboDslamIDDsPort;
        if (dslamID == null || dslamID == -1) {
          return;
        }
        let ck = await this.$root.context.post(
          `web-ecms/doicong-danhsach/lay-ds-port-fix?dslamId=${dslamID}`
        );
        this.arrayDataGrids.dsPortFixTab3 = ck.data.map((i, index) => ({...i, STT: ++index}));
      } catch (e) {
        this.$root.$toast.error(e.error);
      } finally {
        //this.loading(false);
      }
    },
    NAP_DS_LUOI_VCIVPI: async function () {
      try {
        if (
          this.arrayDataToCbo.dsDslamTab3 == null ||
          this.arrayDataToCbo.dsKieuQuyhoachTab3 == null ||
          this.arrayDataToCbo.dsLoaiQuyhoachTab3 == null
        )
          return;
        this.arrayDataGrids.dsVCIVPITab3 = []
        //grcVciVpi.DataSource = null;
        //colVPI.Visible = true;
        // if (cboQuyHoach.EditValue == "1")
        // {
        //     colVPI.Visible = false;
        // }
        let trangthai = 1;
        if (this.arrayDataToCbo.dsTTVciVpiTab3 != null) {
          trangthai = this.arrayCBO.cboTrangthaiVCIVPITab3 ? this.arrayCBO.cboTrangthaiVCIVPITab3 : 0;
        }
        if (trangthai == 4) {
          // giỮ vci vpi THÌ KO VIEW RA NỮA
          this.arrayDataGrids.dsVCIVPITab3 = [];
          return;
        }
        console.log("trangthai ", trangthai);
        let data = {
          dslamId: this.arrayCBO.cboDslamTab3,
          quyhoachId: this.arrayCBO.cboKieuQuyhoachTab3,
          kieuKn: this.arrayCBO.cboLoaiQuyhoachTab3,
          trangThaiId: trangthai,
          vciVpiId: 0,
        };
        let ck = await this.$root.context.post(
          "/web-ecms/doicong-danhsach/nap_ds_luoi_vcivpi",
          data
        );
        console.log('data ',data)
       // if (ck.data != null && ck.data.length > 0) {
          this.arrayDataGrids.dsVCIVPITab3 = ck.data.map((i, index) => ({...i, STT: ++index}));
       // }
      } catch (e) {
        console.log('eeeeeeeeeeeeeeee',e.response.data.message_detail)
        if (e.response.data.message_detail != 'no data found'){
          this.$root.toastError("Có lỗi khi nạp danh sách port " + e.response.data.message_detail);
        }

      }
    },
    hoanThien: async function () {
      try {
        this.$root.showLoading(true);
        await this.THUCTHI();
      } catch (ex) {

        this.$root.toastError("Lỗi " + ex.response.data.message_detail);
      } finally {
        this.$root.showLoading(false);
      }
    },
    THUCTHI: async function () {
      try {
        this.loading(true);
        let check = await this.KIEMTRA_GANCONG(0);
        if (!check) {
          return;
        }
        let thongbao = "Bạn có muốn";
        if (this.arrayInputTab3.ckbAuto_KH) {
          thongbao += " tự động kích hoạt";
        }
        if (this.arrayInputTab3.ckbAuto_BNM) {
          thongbao += " tự động gửi cấu hình";
        }
        if (this.arrayInputTab3.ckbAuto_BNM || this.arrayInputTab3.ckbAuto_KH) {
          thongbao += " khi";
        }
        thongbao += " hoàn thiện đổi cổng không?";

        this.loading(false);

        let confirmation = await this.$bvModal
          .msgBoxConfirm(thongbao, {
            title: "Xác nhận hành động",
            centered: true,
            size: "sm",
            okTitle: 'Đồng ý',
              cancelTitle: 'Hủy',
          })

        if (!confirmation) {
          return
        }

        this.loading(true);
        await this.xacNhanThucThi();
      } catch (ex) {
        this.$root.toastError("Lỗi " + ex.response.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    xacNhanThucThi: async function () {
      try {
        let loi_bnm = [];
        let header = {
          MA_TB: "MA_TB",
          THONGTIN_LOI: "THONGTIN_LOI",
        };
        this.$root.showLoading(true);
        loi_bnm.push(header);
        //#region Danh sách đổi cổng

        let ds = this.selectedDSTab3.map(item => { return {...item} });
        let ds_check = ds.filter(function (item) {
          return item.DKCC_CHA_ID == null;
        });

        let dt_ch = ds_check;
        //#endregion

        //#region Thực thi
        let ds_con_dc = ds_check;
        let countDKCC = ds_con_dc.filter(function (item) {
          return item.DKCC_CHA_ID != null;
        });

        if (countDKCC.length > 0) {
          ds_con_dc = ds_con_dc.filter(function (item) {
            return item.DKCC_CHA_ID != null;
          });
        }

        if (this.arrayInputTab3.ckbAuto_BNM) {
          //#region Tự động cấu hình BNM
          try {
            let tong = ds.length;
            for (let i = tong - 1; i >= 0; i--) {
              let dr = ds[i];

              if (dr.PORT_ID == null || dr.NGAY_CH != null) {
                continue;
              }
              console.log('cấu hình BNM')
              try {
                let dstb = ds[i].THUEBAO_ID + ";";
                let vchuyenall = 0;

                if (ds_con_dc != null && ds_con_dc.length > 0) {

                  let l = ds_con_dc.filter(function (item) {
                    return item.DKCC_CHA_ID == ds[i].DKCC_ID;
                  });

                  if (l.length > 0) {
                    vchuyenall = 1;
                    l.forEach(row => { dstb = dstb + row.THUEBAO_ID + ";"; })
                  }
                }

                dstb = dstb.slice(0, -1);

                let pass_ftth4 = await visa.lookupRASAccount(dr.MA_TB)
                console.log('pass_ftth4',pass_ftth4)
                if (pass_ftth4.startsWith("Lỗi")) {
                  throw pass_ftth4
                }

                let data = {
                  thuebao_id: dr.THUEBAO_ID,
                  port_id: dr.PORT_ID,
                  vci_vpi_id: dr.VCI_VPI_ID,
                  loai_td: 1,
                  nguoi_kh: this.$auth.getUserName(),
                  chuyen_all: vchuyenall,
                  ds_tb: dstb,
                  trangthai: 0,
                  dkcc_id: dr.DKCC_ID,
                  pass_ftth: pass_ftth4,
                };

                let kq = null

                try {
                  kq = await this.$root.context.post(
                    "/web-ecms/doicong-danhsach/cauhinh-bnm",
                    data
                  );
                } catch (err) {
                  let responseData = err.response.data
                  let message = []

                  if (responseData.message) {
                      message.push(responseData.message)
                  }

                  if (responseData.message_detail) {
                      message.push(responseData.message_detail)
                  }

                  if (responseData.data) {
                      let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => `${item}: ${responseData.data[item]}`)
                      message.push([...new Set(detailMessage)].join('\n'))
                  }

                  throw message.join('\n')
                }

                if (kq.data.RESULT === "ok") {
                  try {
                    await this.$root.context.post(`/web-ecms/doicong-danhsach/capnhat10-catchuyen-hoanthien-tab3?dkcc_id=${dr.DKCC_ID}`);

                    let log = {
                      ma_tb: dr.MA_TB,
                      dkcc_id: dr.DKCC_ID,
                      port_id: dr.PORT_ID,
                      vci_vpi_id: dr.VCI_VPI_ID,
                      dslam_vdc_id: dr.DSLAM_VDC_ID,
                      flag: 3,
                      nd: "Đã gửi cấu hình thành công " + dstb + ": Cấu hình " + vchuyenall,
                      status: 1,
                    };

                    await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                  } catch (exxx) {

                    let errr = {
                      MA_TB: dr.MA_TB,
                      THONGTIN_LOI: "Cấu hình thành công không lưu được trạng thái !" + exxx,
                    };

                    loi_bnm.push(errr);

                    let log = {
                      ma_tb: dr.MA_TB,
                      dkcc_id: dr.DKCC_ID,
                      port_id: dr.PORT_ID,
                      vci_vpi_id: dr.VCI_VPI_ID,
                      dslam_vdc_id: dr.DSLAM_VDC_ID,
                      flag: 3,
                      nd: "Cấu hình thành công " + dstb + " (" + vchuyenall + ") không lưu được trạng thái !" + exxx + "",
                      status: 0,
                    };

                    await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                  }
                } else {
                  let errr = {
                    MA_TB: dr.MA_TB,
                    THONGTIN_LOI: "Có lỗi trong quá trình cấu hình !" + kq.data.message_detail,
                  };

                  loi_bnm.push(errr);

                  let log = {
                    ma_tb: dr.MA_TB,
                    dkcc_id: dr.DKCC_ID,
                    port_id: dr.PORT_ID,
                    vci_vpi_id: dr.VCI_VPI_ID,
                    dslam_vdc_id: dr.DSLAM_VDC_ID,
                    flag: 3,
                    nd: "Có lỗi trong quá trình gửi cấu hình ! " + kq + "",
                    status: 0,
                  };

                  await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                }
              } catch (exx) {

                let errr = {
                  MA_TB: dr.MA_TB
                };

                if (typeof exx == 'string') {
                  errr.THONGTIN_LOI = "Có lỗi trong quá trình cấu hình !" + exx
                } else {
                  errr.THONGTIN_LOI = "Có lỗi trong quá trình cấu hình !" + exx.response.data.message_detail
                }

                loi_bnm.push(errr);

                let log = {
                  ma_tb: dr.MA_TB,
                  dkcc_id: dr.DKCC_ID,
                  port_id: dr.PORT_ID,
                  vci_vpi_id: dr.VCI_VPI_ID,
                  dslam_vdc_id: dr.DSLAM_VDC_ID,
                  flag: 3,
                  nd: "Có lỗi trong quá trình cấu hình !" + errr.THONGTIN_LOI + "",
                  status: 0,
                };
                await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                ds.splice(i, 1);
              }
            }
          } catch (ex) {
            let errr = {
              MA_TB: "ALL",
              THONGTIN_LOI: "Lỗi thoát vòng lặp " + ex,
            };
            loi_bnm.push(errr);
          }
        }

        if (this.arrayInputTab3.ckbAuto_KH) {
          try {
            //#region Tự động kích hoạt
            let tong = ds.length;

            for (let i = tong - 1; i >= 0; i--) {

              let dr = ds[i];
              let dem = 0;

              if (ds_con_dc != null && ds_con_dc.length > 0) {

                let l = ds_con_dc.filter(function (item) {
                  return item.DKCC_CHA_ID == ds[i].DKCC_ID;
                });

                if (l.length > 0) {

                  for (let j = 0; j < l.length; j++) {

                    let row = l[j]

                    //#region Kích hoạt con
                    if (row.PORT_ID == null || row.NGAY_KH != null) {
                      continue;
                    }

                    if (row.LOAITB_ID == 11 || row.LOAITB_ID == 58 || row.LOAITB_ID == 79) {
                      //#region Thực hiện đổi cổng
                      let kieu_mega = row.KIEU_MEGA;

                      if (this.DONGBO_TEST == false && kieu_mega != 0) {

                        let loi_ = "";
                        let res = await this.DoiCongAcccountVisa(row);

                        if (res != "") {
                          dem += 1;
                          let errr = {
                            MA_TB: row.MA_TB,
                            THONGTIN_LOI: "Lỗi kích hoạt :" + res,
                          };

                          loi_bnm.push(errr);

                          let log = {
                            ma_tb: row.MA_TB,
                            dkcc_id: row.DKCC_ID,
                            port_id: row.PORT_ID,
                            vci_vpi_id: row.VCI_VPI_ID,
                            dslam_vdc_id: row.DSLAM_VDC_ID,
                            flag: 2,
                            nd: "Lỗi kích hoạt visa " + res,
                            status: 0,
                          };

                          await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                        } else {
                          //#region cập nhật status và lưu log
                          try {
                            await this.$root.context.post(`/web-ecms/doicong-danhsach/capnhat9-catchuyen-hoanthien-tab3?dkcc_id=${row.DKCC_ID}`);

                            let log = {
                              ma_tb: row.MA_TB,
                              dkcc_id: row.DKCC_ID,
                              port_id: row.PORT_ID,
                              vci_vpi_id: row.VCI_VPI_ID,
                              dslam_vdc_id: row.DSLAM_VDC_ID,
                              flag: 2,
                              nd: "Kích hoạt thành công",
                              status: 1,
                            };

                            await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                          } catch (ex) {
                            dem += 1;
                            let errr = {
                              MA_TB: row.MA_TB,
                              THONGTIN_LOI: "Lỗi đổi trạng thái " + ex,
                            };

                            loi_bnm.push(errr);

                            let log = {
                              ma_tb: row.MA_TB,
                              dkcc_id: row.DKCC_ID,
                              port_id: row.PORT_ID,
                              vci_vpi_id: row.VCI_VPI_ID,
                              dslam_vdc_id: row.DSLAM_VDC_ID,
                              flag: 2,
                              nd: "Kích hoạt thành công, lỗi thay đổi trạng thái",
                              status: 0,
                            };

                            await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                          }
                          //#endregion
                        }
                      }
                      //#endregion
                    } else if (row.LOAITB_ID == 61) {
                      //#region Vasc
                      if (row.IP == "") {
                        dem += 1;
                        let errr = {
                          MA_TB: row.MA_TB,
                          THONGTIN_LOI: "Chưa có thông tin về IP. Hãy kiểm tra lại !",
                        };
                        loi_bnm.push(errr);
                        continue;
                      }
                      if (this.DONGBO_TEST == false) {
                        let sPortDSL = "";
                        let shelf = "0";
                        if (row.SHELF.trim() != "") shelf = row.SHELF.trim();
                        let vloaitbi_id = row.LOAITBI_ID;
                        if (vloaitbi_id == 3) {
                          sPortDSL = row.IP + " / " + row.RACK + " / " + row.SLOT + " / " + row.PORT + " / " + row.VPI;
                        } else
                          sPortDSL = row.IP + "/" + shelf + "/" + row.SLOT + "/" + row.PORT;

                        let sDeviceName = row.SYSTEM;

                        let result = await this.UpdateDeviceInfo(
                          row.MA_TB,
                          sDeviceName,
                          sPortDSL
                        ); //nhapt mở 29/09/2016

                        if (result.res != 0) {
                          dem += 1;
                          let errr = {
                            MA_TB: row.MA_TB,
                            THONGTIN_LOI:
                              "Có lỗi trong quá trình cập nhật thông tin cổng thuê bao " + row.MA_TB + " trên VASC !",
                          };

                          loi_bnm.push(errr);
                          let log = {
                            ma_tb: row.MA_TB,
                            dkcc_id: row.DKCC_ID,
                            port_id: row.PORT_ID,
                            vci_vpi_id: row.VCI_VPI_ID,
                            dslam_vdc_id: row.DSLAM_VDC_ID,
                            flag: 2,
                            nd: "Lỗi kích hoạt  " + result.err + "",
                            status: 0,
                          };

                          await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                        } else {
                          //#region cập nhật status và lưu log
                          try {
                            await this.$root.context.post(`/web-ecms/doicong-danhsach/capnhat9-catchuyen-hoanthien-tab3?dkcc_id=${row.DKCC_ID}`);

                            let log = {
                              ma_tb: row.MA_TB,
                              dkcc_id: row.DKCC_ID,
                              port_id: row.PORT_ID,
                              vci_vpi_id: row.VCI_VPI_ID,
                              dslam_vdc_id: row.DSLAM_VDC_ID,
                              flag: 2,
                              nd: "Kích hoạt thành công",
                              status: 1,
                            };

                            await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                          } catch (ex) {
                            dem += 1;
                            let errr = {
                              MA_TB: row.MA_TB,
                              THONGTIN_LOI: "Lỗi đổi trạng thái " + ex,
                            };
                            loi_bnm.push(errr);
                            let log = {
                              ma_tb: row.MA_TB,
                              dkcc_id: row.DKCC_ID,
                              port_id: row.PORT_ID,
                              vci_vpi_id: row.VCI_VPI_ID,
                              dslam_vdc_id: row.DSLAM_VDC_ID,
                              flag: 2,
                              nd: "Kích hoạt thành công, lỗi thay đổi trạng thái",
                              status: 0,
                            };
                            await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                          }
                        }
                      }
                      //#endregion
                    } else {
                      //#region cập nhật status và lưu log
                      try {
                        await this.$root.context.post(`/web-ecms/doicong-danhsach/capnhat9-catchuyen-hoanthien-tab3?dkcc_id=${row.DKCC_ID}`);
                        let log = {
                          ma_tb: row.MA_TB,
                          dkcc_id: row.DKCC_ID,
                          port_id: row.PORT_ID,
                          vci_vpi_id: row.VCI_VPI_ID,
                          dslam_vdc_id: row.DSLAM_VDC_ID,
                          flag: 2,
                          nd: "Kích hoạt ảo thành công",
                          status: 1,
                        };
                        await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                      } catch (ex) {
                        let errr = {
                          MA_TB: row.MA_TB,
                          THONGTIN_LOI: "Lỗi đổi trạng thái " + ex,
                        };
                        loi_bnm.push(errr);
                        let log = {
                          ma_tb: row.MA_TB,
                          dkcc_id: row.DKCC_ID,
                          port_id: row.PORT_ID,
                          vci_vpi_id: row.VCI_VPI_ID,
                          dslam_vdc_id: row.DSLAM_VDC_ID,
                          flag: 2,
                          nd: "Kích hoạt thành công, lỗi thay đổi trạng thái",
                          status: 0,
                        };
                        await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                      }
                      //#endregion
                    }
                    //#endregion
                  }
                }
              }

              if (dem > 0) {
                ds.splice(i, 1);
                continue;
              }

              if (dr.PORT_ID == null || dr.NGAY_KH != null) {
                continue;
              }

              if (dr.LOAITB_ID == 11 || dr.LOAITB_ID == 58 || dr.LOAITB_ID == 79) {
                //#region Thực hiện đổi cổng
                let kieu_mega = dr.KIEU_MEGA;

                if (this.DONGBO_TEST == false && kieu_mega != 0) {
                  let loi_ = "";
                  let res = await this.DoiCongAcccountVisa(dr);
                  if (res != "") {
                    let log = {
                      ma_tb: dr.MA_TB,
                      dkcc_id: dr.DKCC_ID,
                      port_id: dr.PORT_ID,
                      vci_vpi_id: dr.VCI_VPI_ID,
                      dslam_vdc_id: dr.DSLAM_VDC_ID,
                      flag: 2,
                      nd: "Lỗi kích hoạt visa " + res,
                      status: 0,
                    };
                    await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                    let errr = {
                      MA_TB: dr.MA_TB,
                      THONGTIN_LOI: "Lỗi kích hoạt :" + res,
                    };
                    loi_bnm.push(errr);
                    ds.splice(i, 1);
                  } else {
                    //#region cập nhật status và lưu log
                    try {
                      await this.$root.context.post(`/web-ecms/doicong-danhsach/capnhat9-catchuyen-hoanthien-tab3?dkcc_id=${dr.DKCC_ID}`);
                      let log = {
                        ma_tb: dr.MA_TB,
                        dkcc_id: dr.DKCC_ID,
                        port_id: dr.PORT_ID,
                        vci_vpi_id: dr.VCI_VPI_ID,
                        dslam_vdc_id: dr.DSLAM_VDC_ID,
                        flag: 2,
                        nd: "Kích hoạt thành công",
                        status: 1,
                      };
                      await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                    } catch (ex) {
                      let errr = {
                        MA_TB: dr.MA_TB,
                        THONGTIN_LOI: "Lỗi đổi trạng thái " + ex,
                      };
                      loi_bnm.push(errr);
                      let log = {
                        ma_tb: dr.MA_TB,
                        dkcc_id: dr.DKCC_ID,
                        port_id: dr.PORT_ID,
                        vci_vpi_id: dr.VCI_VPI_ID,
                        dslam_vdc_id: dr.DSLAM_VDC_ID,
                        flag: 2,
                        nd: "Kích hoạt thành công, lỗi thay đổi trạng thái",
                        status: 0,
                      };
                      await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                      ds.splice(i, 1);
                    }
                    //#endregion
                  }
                }
                //#endregion
              } else if (dr.LOAITB_ID == 61) {
                //#region Vasc
                if (dr.IP == "") {
                  let errr = {
                    MA_TB: dr.MA_TB,
                    THONGTIN_LOI: "Chưa có thông tin về IP. Hãy kiểm tra lại !",
                  };
                  loi_bnm.push(errr);
                  ds.splice(i, 1);
                  continue;
                }
                if (this.DONGBO_TEST == false) {
                  let sPortDSL = "";
                  let shelf = "0";
                  if (dr.SHELF.trim() != "") shelf = dr.SHELF.trim();
                  let vloaitbi_id = dr.LOAITBI_ID;
                  if (vloaitbi_id == 3) {
                    sPortDSL = dr.IP + "/" + dr.RACK + "/" + dr.SLOT + "/" + dr.PORT + "/" + dr.VPI;
                  } else
                    sPortDSL = dr.IP + "/" + shelf + "/" + dr.SLOT + "/" + dr.PORT;

                  let sDeviceName = dr.SYSTEM;
                  let result = await this.UpdateDeviceInfo(
                    dr.MA_TB,
                    sDeviceName,
                    sPortDSL
                  ); //nhapt mở 29/09/2016

                  if (result.res != 0) {
                    //lỗi
                    let log = {
                      ma_tb: dr.MA_TB,
                      dkcc_id: dr.DKCC_ID,
                      port_id: dr.PORT_ID,
                      vci_vpi_id: dr.VCI_VPI_ID,
                      dslam_vdc_id: dr.DSLAM_VDC_ID,
                      flag: 2,
                      nd: "Lỗi kích hoạt  " + result.err + "",
                      status: 0,
                    };
                    await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                    let errr = {
                      MA_TB: dr.MA_TB,
                      THONGTIN_LOI: "Có lỗi trong quá trình cập nhật thông tin cổng thuê bao " + dr.MA_TB + " trên VASC !",
                    };
                    loi_bnm.push(errr);
                    ds.splice(i, 1);
                  } else {
                    //#region cập nhật status và lưu log
                    try {
                      await this.$root.context.post(`/web-ecms/doicong-danhsach/capnhat9-catchuyen-hoanthien-tab3?dkcc_id=${dr.DKCC_ID}`);
                      let log = {
                        ma_tb: dr.MA_TB,
                        dkcc_id: dr.DKCC_ID,
                        port_id: dr.PORT_ID,
                        vci_vpi_id: dr.VCI_VPI_ID,
                        dslam_vdc_id: dr.DSLAM_VDC_ID,
                        flag: 2,
                        nd: "Kích hoạt thành công",
                        status: 1,
                      };
                      await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                    } catch (ex) {
                      let errr = {
                        MA_TB: dr.MA_TB,
                        THONGTIN_LOI: "Lỗi đổi trạng thái " + ex,
                      };
                      loi_bnm.push(errr);
                      let log = {
                        ma_tb: dr.MA_TB,
                        dkcc_id: dr.DKCC_ID,
                        port_id: dr.PORT_ID,
                        vci_vpi_id: dr.VCI_VPI_ID,
                        dslam_vdc_id: dr.DSLAM_VDC_ID,
                        flag: 2,
                        nd: "Kích hoạt thành công, lỗi thay đổi trạng thái",
                        status: 0,
                      };
                      await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                      ds.splice(i, 1);
                    }
                  }
                }
              } else {
                //#region cập nhật status và lưu log
                try {
                  await this.$root.context.post(
                    `/web-ecms/doicong-danhsach/capnhat9-catchuyen-hoanthien-tab3?dkcc_id=${dr.DKCC_ID}`
                  );
                  let log = {
                    ma_tb: dr.MA_TB,
                    dkcc_id: dr.DKCC_ID,
                    port_id: dr.PORT_ID,
                    vci_vpi_id: dr.VCI_VPI_ID,
                    dslam_vdc_id: dr.DSLAM_VDC_ID,
                    flag: 2,
                    nd: "Kích hoạt ảo thành công",
                    status: 1,
                  };
                  await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                } catch (ex) {
                  let errr = {
                    MA_TB: dr.MA_TB,
                    THONGTIN_LOI: "Lỗi đổi trạng thái " + ex,
                  };
                  loi_bnm.push(errr);
                  let log = {
                    ma_tb: dr.MA_TB,
                    dkcc_id: dr.DKCC_ID,
                    port_id: dr.PORT_ID,
                    vci_vpi_id: dr.VCI_VPI_ID,
                    dslam_vdc_id: dr.DSLAM_VDC_ID,
                    flag: 2,
                    nd: "Kích hoạt thành công, lỗi thay đổi trạng thái",
                    status: 1,
                  };
                  await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                }
                //#endregion
              }
            }
            //#endregion
          } catch (ex) {
            let errr = {
              MA_TB: "ALL",
              THONGTIN_LOI: "Lỗi thoát vòng lặp  " + ex,
            };
            loi_bnm.push(errr);
          }
          //#endregion
        }

        this.errExcel.dataEx = [];
        this.errExcel.dataEx = loi_bnm;

        if (ds.length > 0) {
          let dt_ch = ds;
          let json = dt_ch.map((item) => ({
            DKCC_ID: item.DKCC_ID,
            PORT_ID: item.PORT_ID,
            PORTVL_ID: item.PORTVL_ID,
            VCI_VPI_ID: item.VCI_VPI_ID,
            ONT_ID: item.ONT_ID,
            PORTVL_ID_CU: item.PORTVL_ID_CU,
            PORT_ID_CU: item.PORT_ID_CU,
            VCI_VPI_ID_CU: item.VCI_VPI_ID_CU,
            LOAITBI_ID: item.LOAITBI_ID,
            MA_TB: item.MA_TB,
            THIETBI_ID: item.THIETBI_ID,
            THUEBAO_ID: item.THUEBAO_ID,
            DICHVUVT_ID: item.DICHVUVT_ID,
          }));
          //#region TẠo dữ liệu phiếu hoàn thiện
          try {
             let kq = await this.$root.context.post(`/web-ecms/doicong-danhsach/hoanthien-catchuyen?hsccId=${this.arrayCBO.cboHoSoTab3}`, json);
            if (kq.data.KETQUA == 1) {

              if (loi_bnm.length == 1) {
                this.$toast.success("Hoàn thiện đổi cổng thành công");
              } else {
                this.loading(false)
                let confirm = await this.$bvModal
                  .msgBoxConfirm(
                    "Đã thực hiện xong. Bạn có muốn xuất file danh sách bị lỗi không?",
                    {
                      title: "Xác nhận hành động",
                      centered: true,
                      size: "sm",
                      okTitle: 'Đồng ý',
                      cancelTitle: 'Hủy',
                    }
                  )

                  if (!confirm) {
                    return
                  }

                  document.getElementById("exporterror").click();
              }
              await this.NAP_DS_THUEBAO_CAPCONG(this.arrayCBO.cboHoSoTab3);
              await this.NAP_DS_PORT_FQH();
            } else {
              if (loi_bnm.length == 1) {
                this.$toast.error("Có lỗi khi hoàn thiện " + kq.message_detail);
                await this.NAP_DS_THUEBAO_CAPCONG(this.arrayCBO.cboHoSoTab3);
                await this.NAP_DS_PORT_FQH();
              } else {
                this.loading(false)
                let confirm = await this.$bvModal
                  .msgBoxConfirm(
                    "Đã thực hiện xong. Bạn có muốn xuất file danh sách bị lỗi không?",
                    {
                      title: "Xác nhận hành động",
                      centered: true,
                      size: "sm",
                      okTitle: 'Đồng ý',
                      cancelTitle: 'Hủy',
                    }
                  )

                if (!confirm) {
                  return
                }

                document.getElementById("exporterror").click();
                await this.NAP_DS_THUEBAO_CAPCONG(this.arrayCBO.cboHoSoTab3);
                await this.NAP_DS_PORT_FQH();
              }
            }
          } catch (error) {
            console.log(error)
            this.$root.toastError("" + error.response.data.message_detail);
          }
        }
        else {
          if (loi_bnm != null && loi_bnm.length > 1) {
            this.loading(false)
            let confirm = await this.$bvModal
                  .msgBoxConfirm(
                    "Đã thực hiện xong. Bạn có muốn xuất file danh sách bị lỗi không?",
                    {
                      title: "Xác nhận hành động",
                      centered: true,
                      size: "sm",
                      okTitle: 'Đồng ý',
                      cancelTitle: 'Hủy',
                    }
                  )

                if (!confirm) {
                  return
                }

                document.getElementById("exporterror").click();
          } else {
            this.$toast.error("Có lỗi khi kích hoạt/ cấu hình ");
          }
        }
      } catch (error) {
          console.log(error)
        this.$root.toastError("" + error.response.data.message_detail);
      } finally {
        this.$root.showLoading(false);
      }
    },
    NAP_DS_PORT_FQH: async function () {
      try {
        this.$root.showLoading(true);
        let port_td = this.arrayCBO.cboDslamPort.PORT_TD; //LAY_TS_PORTD();
        if (port_td == "-1") {
          return;
        }
        if (port_td == "0") {
          await this.NAP_DS_LUOI_PORT();
          await this.NAP_DS_LUOI_VCIVPI();
        } else {
          await this.NAP_DS_PORT_FIX();
        }
      } catch (error) {
        this.$root.toastError("Lỗi " + error);
      } finally {
        this.$root.showLoading(false);
      }
    },
    dongHoSo: async function () {
      try {
        let hscc_id = this.arrayCBO.cboHoSoTab3;
        if (hscc_id == -1 || hscc_id == 0) {
          this.$root.$toast.error("Không lấy được thông tin hồ sơ");
          return;
        }
        let ck = await this.$root.context.get(
          "/web-ecms/doicong-danhsach/lay_trangthai_catchuyen_id",
          { hscc_id: hscc_id }
        );
        if (ck == null || ck.data.length == 0) {
          this.$root.$toast.error("Không lấy được trạng thái thông tin hồ sơ");
          return;
        }
        if (ck.data.TTCC_ID == 6) {
          this.$root.$toast.error(
            "Trạng thái hồ sơ đã hoàn thành không thể cập nhật"
          );
          return;
        }

        let ck_sl = await this.$root.context.get(
          "/web-ecms/doicong-danhsach/check-ttcc-donghoso-tab3",
          { hscc_id: hscc_id }
        );
        if (ck_sl == null) {
          this.$root.$toast.error("Không lấy được danh sách cắt chuyển");
          return;
        }
        if (ck_sl.data.length > 0) {
          this.$root.$toast.error(
            "Trong danh sách hồ sơ vẫn còn thuê bao chưa hoàn thiện không thể đóng hồ sơ"
          );
          return;
        }
        this.$bvModal
          .msgBoxConfirm("Bạn có chắc chắn muốn đóng hồ sơ này ", {
            title: "Xác nhận hành động",
            centered: true,
            size: "sm",
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy',
          })
          .then(async (value) => {
            if (value) {
              try {
                await this.$root.context.post(
                  `/web-ecms/doicong-danhsach/donghoso-tab3?hscc_id=${hscc_id}`
                );
                this.$toast.success("Đóng hồ sơ thành công");
                this.LOAD_HOSO_CATCHUYEN2();
              } catch (ex1) {
                this.$root.$toast.error("Có lỗi khi đóng hồ sơ " + ex1);
              }
            } else {
              return;
            }
          });
      } catch (ex) {
        this.$root.toastError(
          "Có lỗi khi hoàn thiện hồ sơ " + ex.response.data.message_detail
        );
      }
    },
    GO_CONG: async function () {
      try {
        let loi_bnm = [];
        let header = {
          MA_TB: "MA_TB",
          THONGTIN_LOI: "THONGTIN_LOI",
        };

        loi_bnm.push(header);

        let check = await this.KIEMTRA_GANCONG(0);

        if (!check) {
          return;
        }

        //#region Lấy thông tin mã tb cần gán cổng
        let ds1 = this.selectedDSTab3;

        let ds_check = ds1.map((item) => {
          return { ...item };
        });

        ds_check = ds_check.filter(function (item) {
          return item.CHON === "s1" && item.DKCC_CHA_ID === null;
        });

        let json_list = ds_check.map((item) => ({ DKCC_ID: item.DKCC_ID }));

        let dem = await this.$root.context.post("/web-ecms/doicong-danhsach/kiemtra-list-inp-gocong-tab3", json_list);

        if (dem.data[0].COUNT !== 0) {
          this.$root.$toast.error("Trong danh sách có thuê bao có trạng thái hoàn thành hoặc chưa cấp cổng không thể gỡ cổng");
          return;
        }
        //#endregion

        this.$root.showLoading(true);

        let ds_con = this.selectedDSTab3;

        let ds_con_dc = ds_con.filter(function (item) {
          return item.DKCC_CHA_ID != null;
        });

        let ds = ds_con.map((item) => {
          return { ...item };
        });

        //#region Quét từng row kiểm tra để quay ngược
        for (let i = ds.length - 1; i >= 0; i--) {

          //#region Check dữ liệu con
          let dem = 0;

          if (ds_con_dc != null && ds_con_dc.length > 0) {

            let dr = ds[i];

            let l = ds_con_dc.filter(function (item) {
              return (
                item.DKCC_CHA_ID !== dr.DKCC_ID &&
                item.PORT_ID_CU != 0 &&
                item.VCI_VPI_ID_CU != 0 &&
                (item.PORT_ID_CU != dr.PORT_ID_CU || item.VCI_VPI_ID_CU != dr.VCI_VPI_ID_CU)
              );
            });

            if (l.length > 0) {

              for (let j = 0; j < l.length; j++) {

                let row = l[j];

                if (row.NGAY_GP != null) {
                  try {
                    //#region cấu hình cổng cũ
                    let pass_ftth5 = await visa.lookupRASAccount(row.MA_TB)

                    if (pass_ftth5.startsWith("Lỗi")) {
                      throw pass_ftth5
                    }

                    let data = {
                      thuebao_id: row.THUEBAO_ID,
                      port_id: row.PORT_ID_CU,
                      vci_vpi_id: row.VCI_VPI_ID_CU,
                      loai_td: 1,
                      nguoi_kh: this.$auth.getUserName(),
                      chuyen_all: 0,
                      ds_tb: "",
                      trangthai: 0,
                      pass_ftth: pass_ftth5,
                    };

                    let kq = null

                    try {
                      kq = await this.$root.context.post("/web-ecms/doicong-danhsach/cauhinh-bnm", data);
                    } catch (err) {
                      let responseData = err.response.data
                      let message = []

                      if (responseData.message) {
                          message.push(responseData.message)
                      }

                      if (responseData.message_detail) {
                          message.push(responseData.message_detail)
                      }

                      if (responseData.data) {
                          let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => `${item}: ${responseData.data[item]}`)
                          message.push([...new Set(detailMessage)].join('\n'))
                      }

                      throw message.join('\n')
                    }

                    if (kq.data.RESULT == "ok") {
                      try {
                        let log = {
                          ma_tb: row.MA_TB,
                          dkcc_id: row.DKCC_ID,
                          port_id: row.PORT_ID_CU,
                          vci_vpi_id: row.VCI_VPI_ID_CU,
                          dslam_vdc_id: row.DSLAM_VDC_ID_CU,
                          flag: 3,
                          nd: "Đã gửi cấu hình cổng cũ thành công " + row.THUEBAO_ID + ": Cấu hình " + 0,
                          status: 1,
                        };

                        await this.$root.context.post(
                          `/web-ecms/doicong-danhsach/capnhat-dk-catchuyen?dkcc_id=${row.DKCC_ID}&type=1`
                        );

                        await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                      } catch (exxx) {

                        let errr = {
                          MA_TB: row.MA_TB,
                          THONGTIN_LOI: "Cấu hình cổng cũ thành công không lưu được trạng thái !" + exxx.response.data.message_detail
                        };

                        loi_bnm.push(errr);

                        let log = {
                          ma_tb: row.MA_TB,
                          dkcc_id: row.DKCC_ID,
                          port_id: row.PORT_ID_CU,
                          vci_vpi_id: row.VCI_VPI_ID_CU,
                          dslam_vdc_id: row.DSLAM_VDC_ID_CU,
                          flag: 3,
                          nd: "Cấu hình cổng cũ thành công " + row.THUEBAO_ID + " không lưu được trạng thái ! " + exxx,
                          status: 0,
                        };

                        await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);

                        dem += 1;
                      }
                    } else {
                      dem += 1;

                      let errr = {
                        MA_TB: row.MA_TB,
                        THONGTIN_LOI: "Có lỗi trong quá trình cấu hình !" + kq.data.message_detail,
                      };

                      loi_bnm.push(errr);

                      let log = {
                        ma_tb: row.MA_TB,
                        dkcc_id: row.DKCC_ID,
                        port_id: row.PORT_ID_CU,
                        vci_vpi_id: row.VCI_VPI_ID_CU,
                        dslam_vdc_id: row.DSLAM_VDC_ID_CU,
                        flag: 3,
                        nd: "Có lỗi trong quá trình gửi cấu hình cổng cũ ! " + kq.data.message_detail,
                        status: 0,
                      };

                      await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                    }
                  } catch (ex) {
                    dem += 1;

                    let errr = {
                      MA_TB: row.MA_TB,
                      THONGTIN_LOI: "Có lỗi trong quá trình cấu hình !" + ex.response.data.message_detail,
                    };

                    loi_bnm.push(errr);

                    let log = {
                      ma_tb: row.MA_TB,
                      dkcc_id: row.DKCC_ID,
                      port_id: row.PORT_ID_CU,
                      vci_vpi_id: row.VCI_VPI_ID_CU,
                      dslam_vdc_id: row.DSLAM_VDC_ID_CU,
                      flag: 3,
                      nd: "Có lỗi trong quá trình gửi cấu hình cổng cũ !" + ex.response.data.message_detail,
                      status: 0,
                    };

                    await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                  }
                }
              }
            } else {
              let l_ = ds_con_dc.filter(function (item) {
                return (
                  item.DKCC_CHA_ID == ds[i].DKCC_ID &&
                  item.PORT_ID_CU != 0 &&
                  item.VCI_VPI_ID_CU != 0 &&
                  (item.PORT_ID_CU == ds[i].PORT_ID_CU || item.VCI_VPI_ID_CU == ds[i].VCI_VPI_ID_CU)
                );
              });

              if (l_.length > 0) {

                for (let j = 0; j < l_.length; j++) {

                  let row = l_[j]

                  if (row.NGAY_GP != null) {
                    try {
                      //#region cấu hình cổng cũ
                      let pass_ftth_1 = await visa.lookupRASAccount(row.MA_TB)

                      if (pass_ftth_1.startsWith("Lỗi")) {
                        throw pass_ftth_1
                      }

                      let data = {
                        thuebao_id: row.THUEBAO_ID,
                        port_id: row.PORT_ID_CU,
                        vci_vpi_id: row.VCI_VPI_ID_CU,
                        loai_td: 1,
                        nguoi_kh: this.$auth.getUserName(),
                        chuyen_all: 0,
                        ds_tb: "",
                        trangthai: 0,
                        pass_ftth: pass_ftth_1,
                      };

                      let kq = null

                      try {
                          kq = await this.$root.context.post("/web-ecms/doicong-danhsach/cauhinh-bnm", data);
                      } catch (err) {
                          let responseData = err.response.data
                          let message = []

                          if (responseData.message) {
                              message.push(responseData.message)
                          }

                          if (responseData.message_detail) {
                              message.push(responseData.message_detail)
                          }

                          if (responseData.data) {
                              let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => `${item}: ${responseData.data[item]}`)
                              message.push([...new Set(detailMessage)].join('\n'))
                          }

                          throw message.join('\n')
                      }

                      if (kq.data.RESULT == "ok") {
                        try {
                          await this.$root.context.post(
                            `/web-ecms/doicong-danhsach/capnhat-dk-catchuyen?dkcc_id=${row.DKCC_ID}&type=1`
                          );

                          let log = {
                            ma_tb: row.MA_TB,
                            dkcc_id: row.DKCC_ID,
                            port_id: row.PORT_ID_CU,
                            vci_vpi_id: row.VCI_VPI_ID_CU,
                            dslam_vdc_id: row.DSLAM_VDC_ID_CU,
                            flag: 3,
                            nd: "Đã gửi cấu hình cổng cũ thành công " + row.THUEBAO_ID + ": Cấu hình " + 0,
                            status: 1,
                          };

                          await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                        } catch (exxx) {

                          let errr = {
                            MA_TB: row.MA_TB,
                            THONGTIN_LOI: "Cấu hình cổng cũ thành công không lưu được trạng thái !" + exxx
                          };

                          loi_bnm.push(errr);

                          let log = {
                            ma_tb: row.MA_TB,
                            dkcc_id: row.DKCC_ID,
                            port_id: row.PORT_ID_CU,
                            vci_vpi_id: row.VCI_VPI_ID_CU,
                            dslam_vdc_id: row.DSLAM_VDC_ID_CU,
                            flag: 3,
                            nd: "Cấu hình cổng cũ thành công " + row.THUEBAO_ID + " không lưu được trạng thái !" + exxx ,
                            status: 0,
                          };

                          await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);

                          dem += 1;
                        }
                      } else {
                        dem += 1;

                        let errr = {
                          MA_TB: row.MA_TB,
                          THONGTIN_LOI: "Có lỗi trong quá trình cấu hình !" + kq.data.message_detail,
                        };

                        loi_bnm.push(errr);

                        let log = {
                          ma_tb: row.MA_TB,
                          dkcc_id: row.DKCC_ID,
                          port_id: row.PORT_ID_CU,
                          vci_vpi_id: row.VCI_VPI_ID_CU,
                          dslam_vdc_id: row.DSLAM_VDC_ID_CU,
                          flag: 3,
                          nd: "Có lỗi trong quá trình gửi cấu hình cổng cũ !" + kq.data.message_detail,
                          status: 0,
                        };

                        await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                      }
                    } catch (ex) {

                      dem += 1;

                      let errr = {
                        MA_TB: row.MA_TB,
                        THONGTIN_LOI: "Có lỗi trong quá trình cấu hình !" + ex.response.data.message_detail,
                      };

                      loi_bnm.push(errr);

                      let log = {
                        ma_tb: row.MA_TB,
                        dkcc_id: row.DKCC_ID,
                        port_id: row.PORT_ID_CU,
                        vci_vpi_id: row.VCI_VPI_ID_CU,
                        dslam_vdc_id: row.DSLAM_VDC_ID_CU,
                        flag: 3,
                        nd: "Có lỗi trong quá trình gửi cấu hình cổng cũ !" + ex,
                        status: 0,
                      };

                      await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                    }
                  }
                }
              }
            }
          }

          if (dem > 0) {
            ds.splice(i, 1);
            continue;
          }

          //#region Nếu ngày giải phóng có thì cấu hình lại cổng cũ
          if (ds[i].NGAY_GP != null) {
            try {
              //#region cấu hình cổng cũ
              let pass_ftth_ = await visa.lookupRASAccount( ds[i].MA_TB)

              if (pass_ftth_.startsWith("Lỗi")) {
                throw pass_ftth_
              }

              let data = {
                thuebao_id: ds[i].THUEBAO_ID,
                port_id: ds[i].PORT_ID_CU,
                vci_vpi_id: ds[i].VCI_VPI_ID_CU,
                loai_td: 1,
                nguoi_kh: this.$auth.getUserName(),
                chuyen_all: 0,
                ds_tb: "",
                trangthai: 0,
                pass_ftth: pass_ftth_,
              };

              let kq = null

              try {
                  kq = await this.$root.context.post("/web-ecms/doicong-danhsach/cauhinh-bnm", data);
              } catch (err) {
                  let responseData = err.response.data
                  let message = []

                  if (responseData.message) {
                      message.push(responseData.message)
                  }

                  if (responseData.message_detail) {
                      message.push(responseData.message_detail)
                  }

                  if (responseData.data) {
                      let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => `${item}: ${responseData.data[item]}`)
                      message.push([...new Set(detailMessage)].join('\n'))
                  }

                  throw message.join('\n')
              }

              if (kq.data.RESULT == "ok") {
                try {
                  await this.$root.context.post(
                    `/web-ecms/doicong-danhsach/capnhat-dk-catchuyen?dkcc_id=${ds[i].DKCC_ID}&type=0`
                  );

                  let log = {
                    ma_tb: ds[i].MA_TB,
                    dkcc_id: ds[i].DKCC_ID,
                    port_id: ds[i].PORT_ID_CU,
                    vci_vpi_id: ds[i].VCI_VPI_ID_CU,
                    dslam_vdc_id: ds[i].DSLAM_VDC_ID_CU,
                    flag: 3,
                    nd: "Đã gửi cấu hình cổng cũ thành công " + ds[i].THUEBAO_ID + ": Cấu hình " + 0,
                    status: 1,
                  };

                  await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                } catch (exxx) {

                  let errr = {
                    MA_TB: ds[i].MA_TB,
                    THONGTIN_LOI: "Cấu hình cổng cũ thành công không lưu được trạng thái !" + exxx,
                  };

                  loi_bnm.push(errr);

                  let log = {
                    ma_tb: ds[i].MA_TB,
                    dkcc_id: ds[i].DKCC_ID,
                    port_id: ds[i].PORT_ID_CU,
                    vci_vpi_id: ds[i].VCI_VPI_ID_CU,
                    dslam_vdc_id: ds[i].DSLAM_VDC_ID_CU,
                    flag: 3,
                    nd: "Cấu hình cổng cũ thành công " + ds[i].THUEBAO_ID + " không lưu được trạng thái !" + exxx,
                    status: 0,
                  };

                  await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);

                  ds.splice(i, 1);
                  continue;
                }
              } else {

                let errr = {
                  MA_TB: ds[i].MA_TB,
                  THONGTIN_LOI: "Có lỗi trong quá trình cấu hình cổng cũ !" + kq
                };

                loi_bnm.push(errr);

                let log = {
                  ma_tb: ds[i].MA_TB,
                  dkcc_id: ds[i].DKCC_ID,
                  port_id: ds[i].PORT_ID_CU,
                  vci_vpi_id: ds[i].VCI_VPI_ID_CU,
                  dslam_vdc_id: ds[i].DSLAM_VDC_ID_CU,
                  flag: 3,
                  nd: "Cấu hình cổng cũ thành công " + ds[i].THUEBAO_ID + " không lưu được trạng thái !" + exxx,
                  status: 0,
                };

                await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);

                ds.splice(i, 1);
                continue;
              }
              //#endregion
            } catch (ex) {

              let errr = {
                MA_TB: ds[i].MA_TB,
                THONGTIN_LOI: "Có lỗi trong quá trình cấu hình cổng cũ  !" + ex,
              };

              loi_bnm.push(errr);

              let log = {
                ma_tb: ds[i].MA_TB,
                dkcc_id: ds[i].DKCC_ID,
                port_id: ds[i].PORT_ID_CU,
                vci_vpi_id: ds[i].VCI_VPI_ID_CU,
                dslam_vdc_id: ds[i].DSLAM_VDC_ID_CU,
                flag: 3,
                nd: "Có lỗi trong quá trình gửi cấu hình cổng cũ !" + ex + "",
                status: 0,
              };

              await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);

              ds.splice(i, 1);
              continue;
            }
          }

          //#region Nếu ngày cấu hình có thì giải phóng cổng mới
          if (ds[i].NGAY_CH != null) {
            try {
              let dstb = ds[i].THUEBAO_ID + ";";
              let vchuyenall = 0;

              if (ds_con_dc != null && ds_con_dc.length > 0) {

                var l = ds_con_dc.filter(function (item) {
                  return item.DKCC_CHA_ID == ds[i].DKCC_ID;
                });

                if (l.length > 0) {
                  vchuyenall = 1;
                  l.forEach(row => { dstb = dstb + row.THUEBAO_ID + ";"; })
                }
              }

              dstb = dstb.slice(0, -1);

              //#region cấu hình giải phóng cổng mới
              let _pass_ftth_ = await visa.lookupRASAccount( ds[i].MA_TB)

              if (_pass_ftth_.startsWith("Lỗi")) {
                throw _pass_ftth_
              }

              let data = {
                thuebao_id: ds[i].THUEBAO_ID,
                port_id: ds[i].PORT_ID,
                vci_vpi_id: ds[i].VCI_VPI_ID,
                loai_td: 1,
                nguoi_kh: this.$auth.getUserName(),
                chuyen_all: 0,
                ds_tb: "",
                trangthai: 0,
                pass_ftth: _pass_ftth_,
              };

              let kq = null

              try {
                  kq = await this.$root.context.post("/web-ecms/doicong-danhsach/cauhinh-bnm", data);
              } catch (err) {
                  let responseData = err.response.data
                  let message = []

                  if (responseData.message) {
                      message.push(responseData.message)
                  }

                  if (responseData.message_detail) {
                      message.push(responseData.message_detail)
                  }

                  if (responseData.data) {
                      let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => `${item}: ${responseData.data[item]}`)
                      message.push([...new Set(detailMessage)].join('\n'))
                  }

                  throw message.join('\n')
              }

              if (kq.data.RESULT == "ok") {
                try {
                  await this.$root.context.post(
                    `/web-ecms/doicong-danhsach/capnhat-dk-catchuyen?dkcc_id=${ds[i].DKCC_ID}&type=0`
                  );

                  let log = {
                    ma_tb: ds[i].MA_TB,
                    dkcc_id: ds[i].DKCC_ID,
                    port_id: ds[i].PORT_ID,
                    vci_vpi_id: ds[i].VCI_VPI_ID,
                    dslam_vdc_id: ds[i].DSLAM_VDC_ID,
                    flag: 3,
                    nd: "Đã gửi cấu hình giải phóng cổng mới thành công " + ds[i].THUEBAO_ID + ": Cấu hình " + 0,
                    status: 1,
                  };

                  await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                } catch (exxx) {

                  let errr = {
                    MA_TB: ds[i].MA_TB,
                    THONGTIN_LOI: "Cấu hình cổng cũ thành công không lưu được trạng thái !" + exxx,
                  };

                  loi_bnm.push(errr);

                  let log = {
                    ma_tb: ds[i].MA_TB,
                    dkcc_id: ds[i].DKCC_ID,
                    port_id: ds[i].PORT_ID,
                    vci_vpi_id: ds[i].VCI_VPI_ID,
                    dslam_vdc_id: ds[i].DSLAM_VDC_ID,
                    flag: 3,
                    nd: "Cấu hình giải phóng cổng mới thành công " + ds[i].THUEBAO_ID + " không lưu được trạng thái !" + exxx,
                    status: 0,
                  };

                  await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);

                  ds.splice(i, 1);
                  continue;
                }
              } else {

                let errr = {
                  MA_TB: ds[i].MA_TB,
                  THONGTIN_LOI: "Có lỗi trong quá trình cấu hình giải phóng cổng mới !" + kq,
                };

                loi_bnm.push(errr);
                let log = {
                  ma_tb: ds[i].MA_TB,
                  dkcc_id: ds[i].DKCC_ID,
                  port_id: ds[i].PORT_ID,
                  vci_vpi_id: ds[i].VCI_VPI_ID,
                  dslam_vdc_id: ds[i].DSLAM_VDC_ID,
                  flag: 3,
                  nd:"Có lỗi trong quá trình gửi cấu hình giải phóng cổng mới ! " + kq,
                  status: 0,
                };

                await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);

                ds.splice(i, 1);
                continue;
              }
            } catch (ex) {

              let errr = {
                MA_TB: ds[i].MA_TB,
                THONGTIN_LOI: "Có lỗi trong quá trình cấu hình giải phóng cổng mới  !" + ex,
              };

              loi_bnm.push(errr);

              let log = {
                ma_tb: ds[i].MA_TB,
                dkcc_id: ds[i].DKCC_ID,
                port_id: ds[i].PORT_ID,
                vci_vpi_id: ds[i].VCI_VPI_ID,
                dslam_vdc_id: ds[i].DSLAM_VDC_ID,
                flag: 3,
                nd: "Có lỗi trong quá trình gửi cấu hình giải phóng cổng mới! " + ex,
                status: 0,
              };

              await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);

              ds.splice(i, 1);
              continue;
            }
          }
          //#endregion

          //#region Nếu ngày kích hoạt có
          if (ds[i].NGAY_KH != null) {

            let dem_vasc = 0;

            if (ds_con_dc != null && ds_con_dc.length > 0) {

              var l = ds_con_dc.filter(function (item) {
                return item.DKCC_CHA_ID == ds[i].DKCC_ID;
              });

              if (l.length > 0) {

                for (let j = 0; j < l.length; j++) {

                  let row = l[j]

                  //#region Kích hoạt con
                  if (row.PORT_ID_CU == null || row.NGAY_KH != null) {
                    continue;
                  }

                  if (row.LOAITB_ID == 11 ||
                      row.LOAITB_ID == 58 ||
                      row.LOAITB_ID == 79
                  ) {
                    //#region Thực hiện đổi cổng
                    let kieu_mega = row.KIEU_MEGA;

                    if (this.DONGBO_TEST == false && kieu_mega != 0) {

                      let res = await this.DoiCongAcccountVisa_cu(row);

                      if (res != "") {

                        dem_vasc += 1;

                        let errr = {
                          MA_TB: row.MA_TB,
                          THONGTIN_LOI: "Lỗi kích hoạt :" + res,
                        };

                        loi_bnm.push(errr);

                        let log = {
                          ma_tb: row.MA_TB,
                          dkcc_id: row.DKCC_ID,
                          port_id: row.PORT_ID,
                          vci_vpi_id: row.VCI_VPI_ID,
                          dslam_vdc_id: row.DSLAM_VDC_ID,
                          flag: 2,
                          nd: "Lỗi kích hoạt visa " + res,
                          status: 0,
                        };

                        await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                      } else {
                        //#region cập nhật status và lưu log
                        try {
                          await this.$root.context.post(
                            `/web-ecms/doicong-danhsach/capnhat-dk-catchuyen?dkcc_id=${row.DKCC_ID}&type=1`
                          );

                          let log = {
                            ma_tb: row.MA_TB,
                            dkcc_id: row.DKCC_ID,
                            port_id: row.PORT_ID,
                            vci_vpi_id: row.VCI_VPI_ID,
                            dslam_vdc_id: row.DSLAM_VDC_ID,
                            flag: 2,
                            nd: "Kích hoạt thành công",
                            status: 1,
                          };

                          await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                        } catch (ex) {
                          dem_vasc += 1;

                          let errr = {
                            MA_TB: row.MA_TB,
                            THONGTIN_LOI: "Lỗi đổi trạng thái " + ex,
                          };

                          loi_bnm.push(errr);

                          let log = {
                            ma_tb: row.MA_TB,
                            dkcc_id: row.DKCC_ID,
                            port_id: row.PORT_ID,
                            vci_vpi_id: row.VCI_VPI_ID,
                            dslam_vdc_id: row.DSLAM_VDC_ID,
                            flag: 2,
                            nd: "Kích hoạt thành công, lỗi thay đổi trạng thái",
                            status: 1,
                          };

                          await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                        }
                      }
                    }
                  } else if (row.LOAITB_ID == 61) {

                    //#region Vasc
                    if (row.IP_CU == "") {

                      dem_vasc += 1;

                      let errr = {
                        MA_TB: row.MA_TB,
                        THONGTIN_LOI: "Chưa có thông tin về IP. Hãy kiểm tra lại !",
                      };

                      loi_bnm.push(errr);
                      continue;
                    }

                    if (this.DONGBO_TEST == false) {

                      let sPortDSL = "";
                      let shelf = "0";

                      if (!!row.SHELF_CU && row.SHELF_CU.trim() != "") {
                        shelf = row.SHELF_CU.trim();
                      }

                      let vloaitbi_id = row.LOAITBI_ID_CU;

                      if (vloaitbi_id == 3) {
                        sPortDSL = row.IP_CU + " / " + row.RACK_CU + " / " + row.SLOT_CU + " / " + row.PORT_OLD + " / " + row.VPI_CU;
                      } else {
                        sPortDSL = row.IP_CU + " / " + shelf + " / " + row.SLOT_CU + " / " + row.PORT_OLD;
                      }

                      let sDeviceName = row.SYSTEM_CU;

                      let result = await this.UpdateDeviceInfo(
                        row.MA_TB,
                        sDeviceName,
                        sPortDSL
                      ); //nhapt mở 29/09/2016

                      if (result.res != 0) {

                        dem_vasc += 1;

                        let errr = {
                          MA_TB: row.MA_TB,
                          THONGTIN_LOI: "Có lỗi trong quá trình cập nhật thông tin cổng thuê bao " + row.MA_TB + " trên VASC !",
                        };

                        loi_bnm.push(errr);

                        let log = {
                          ma_tb: row.MA_TB,
                          dkcc_id: row.DKCC_ID,
                          port_id: row.PORT_ID,
                          vci_vpi_id: row.VCI_VPI_ID,
                          dslam_vdc_id: row.DSLAM_VDC_ID,
                          flag: 2,
                          nd: "Lỗi kích hoạt  " + result.err + "",
                          status: 0,
                        };

                        await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                      } else {
                        //#region cập nhật status và lưu log
                        try {
                          await this.$root.context.post(
                            `/web-ecms/doicong-danhsach/capnhat-dk-catchuyen?dkcc_id=${row.DKCC_ID}&type=1`
                          );

                          let log = {
                            ma_tb: row.MA_TB,
                            dkcc_id: row.DKCC_ID,
                            port_id: row.PORT_ID_CU,
                            vci_vpi_id: row.VCI_VPI_ID_CU,
                            dslam_vdc_id: row.DSLAM_VDC_ID_CU,
                            flag: 2,
                            nd: "Kích hoạt thành công",
                            status: 1,
                          };

                          await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                        } catch (ex) {

                          dem_vasc += 1;

                          let errr = {
                            MA_TB: row.MA_TB,
                            THONGTIN_LOI: "Lỗi đổi trạng thái " + ex,
                          };

                          loi_bnm.push(errr);

                          let log = {
                            ma_tb: row.MA_TB,
                            dkcc_id: row.DKCC_ID,
                            port_id: row.PORT_ID_CU,
                            vci_vpi_id: row.VCI_VPI_ID_CU,
                            dslam_vdc_id: row.DSLAM_VDC_ID_CU,
                            flag: 2,
                            nd: "Kích hoạt thành công, lỗi thay đổi trạng thái",
                            status: 0,
                          };

                          await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                        }
                        //#endregion
                      }
                    }
                    //#endregion
                  } else {
                    //#region cập nhật status và lưu log
                    try {
                      await this.$root.context.post(
                        `/web-ecms/doicong-danhsach/capnhat-dk-catchuyen?dkcc_id=${row.DKCC_ID}&type=1`
                      );

                      let log = {
                        ma_tb: row.MA_TB,
                        dkcc_id: row.DKCC_ID,
                        port_id: row.PORT_ID_CU,
                        vci_vpi_id: row.VCI_VPI_ID_CU,
                        dslam_vdc_id: row.DSLAM_VDC_ID_CU,
                        flag: 2,
                        nd: "Kích hoạt ảo thành công",
                        status: 1,
                      };

                      await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                    } catch (ex) {

                      dem_vasc += 1;

                      let errr = {
                        MA_TB: row.MA_TB,
                        THONGTIN_LOI: "Lỗi đổi trạng thái " + ex,
                      };

                      loi_bnm.push(errr);

                      let log = {
                        ma_tb: row.MA_TB,
                        dkcc_id: row.DKCC_ID,
                        port_id: row.PORT_ID_CU,
                        vci_vpi_id: row.VCI_VPI_ID_CU,
                        dslam_vdc_id: row.DSLAM_VDC_ID_CU,
                        flag: 2,
                        nd: "Kích hoạt thành công, lỗi thay đổi trạng thái",
                        status: 0,
                      };

                      await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                    }
                    //#endregion
                  }
                  //#endregion
                }
              }
            }

            if (dem_vasc > 0) {
              ds.splice(i, 1);
              continue;
            }

            //#region Kích hoạt con
            if (ds[i].PORT_ID_CU == null) {
              continue;
            }

            if (
              ds[i].LOAITB_ID == 11 ||
              ds[i].LOAITB_ID == 58 ||
              ds[i].LOAITB_ID == 79
            ) {
              //#region Thực hiện đổi cổng
              let kieu_mega = ds[i].KIEU_MEGA;

              if (this.DONGBO_TEST == false && kieu_mega != 0) {

                let res = await this.DoiCongAcccountVisa_cu(ds[i]);

                if (res != "") {

                  let errr = {
                    MA_TB: ds[i].MA_TB,
                    THONGTIN_LOI: "Lỗi kích hoạt :" + res,
                  };

                  loi_bnm.push(errr);

                  let log = {
                    ma_tb: ds[i].MA_TB,
                    dkcc_id: ds[i].DKCC_ID,
                    port_id: ds[i].PORT_ID,
                    vci_vpi_id: ds[i].VCI_VPI_ID,
                    dslam_vdc_id: ds[i].DSLAM_VDC_ID,
                    flag: 2,
                    nd: "Lỗi kích hoạt visa " + res,
                    status: 0,
                  };

                  await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);

                  ds.splice(i, 1);
                  continue;
                } else {
                  //#region cập nhật status và lưu log
                  try {
                    await this.$root.context.post(
                      `/web-ecms/doicong-danhsach/capnhat-dk-catchuyen?dkcc_id=${ds[i].DKCC_ID}&type=1`
                    );

                    let log = {
                      ma_tb: ds[i].MA_TB,
                      dkcc_id: ds[i].DKCC_ID,
                      port_id: ds[i].PORT_ID,
                      vci_vpi_id: ds[i].VCI_VPI_ID,
                      dslam_vdc_id: ds[i].DSLAM_VDC_ID,
                      flag: 2,
                      nd: "Kích hoạt thành công",
                      status: 1,
                    };

                    await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                  } catch (ex) {

                    let errr = {
                      MA_TB: ds[i].MA_TB,
                      THONGTIN_LOI: "Lỗi đổi trạng thái " + ex,
                    };

                    loi_bnm.push(errr);

                    let log = {
                      ma_tb: ds[i].MA_TB,
                      dkcc_id: ds[i].DKCC_ID,
                      port_id: ds[i].PORT_ID,
                      vci_vpi_id: ds[i].VCI_VPI_ID,
                      dslam_vdc_id: ds[i].DSLAM_VDC_ID,
                      flag: 2,
                      nd: "Kích hoạt thành công, lỗi thay đổi trạng thái",
                      status: 0,
                    };

                    await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);

                    ds.splice(i, 1);
                    continue;
                  }
                }
              }
            } else if (ds[i].LOAITB_ID == 61) {
              //#region Vasc
              if (ds[i].IP_CU == "") {

                let errr = {
                  MA_TB: ds[i].MA_TB,
                  THONGTIN_LOI: "Chưa có thông tin về IP. Hãy kiểm tra lại !",
                };

                loi_bnm.push(errr);

                ds.splice(i, 1);
                continue;
              }

              if (this.DONGBO_TEST == false) {

                let sPortDSL = "";
                let shelf = "0";

                if (!!ds[i].SHELF_CU && ds[i].SHELF_CU.trim() != "") {
                  shelf = ds[i].SHELF_CU.trim();
                }

                let vloaitbi_id = ds[i].LOAITBI_ID_CU;

                if (vloaitbi_id == 3) {
                  sPortDSL = ds[i].IP_CU + " / " + ds[i].RACK_CU + " / " + ds[i].SLOT_CU + " / " + ds[i].PORT_OLD + " / " + ds[i].VPI_CU;
                } else
                  sPortDSL = ds[i].IP_CU + " / " + shelf + " / " + ds[i].SLOT_CU + " / " + ds[i].PORT_OLD;

                let sDeviceName = ds[i].SYSTEM_CU;

                // hoangpkn : 30/07/2015 : Không cần truyền sPortDSL vẫn chạy (HCM vẫn truyền null). Do có truyền mà IP dslam sai. Vasc sẽ báo lỗi
                let result = await this.UpdateDeviceInfo(
                  ds[i].MA_TB,
                  sDeviceName,
                  sPortDSL
                ); //nhapt mở 29/09/2016

                if (result.res != 0) {
                  //lỗi
                  let errr = {
                    MA_TB: ds[i].MA_TB,
                    THONGTIN_LOI: "Có lỗi trong quá trình cập nhật thông tin cổng thuê bao " + dr["MA_TB"] + " trên VASC !",
                  };

                  loi_bnm.push(errr);

                  let log = {
                    ma_tb: ds[i].MA_TB,
                    dkcc_id: ds[i].DKCC_ID,
                    port_id: ds[i].PORT_ID_CU,
                    vci_vpi_id: ds[i].VCI_VPI_ID_CU,
                    dslam_vdc_id: ds[i].DSLAM_VDC_ID_CU,
                    flag: 2,
                    nd: "Lỗi kích hoạt  " + result.err + "",
                    status: 0,
                  };

                  await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);

                  ds.splice(i, 1);
                  continue;
                } else {
                  //#region cập nhật status và lưu log
                  try {
                    await this.$root.context.post(
                      `/web-ecms/doicong-danhsach/capnhat-dk-catchuyen?dkcc_id=${ds[i].DKCC_ID}&type=1`
                    );

                    let log = {
                      ma_tb: ds[i].MA_TB,
                      dkcc_id: ds[i].DKCC_ID,
                      port_id: ds[i].PORT_ID_CU,
                      vci_vpi_id: ds[i].VCI_VPI_ID_CU,
                      dslam_vdc_id: ds[i].DSLAM_VDC_ID_CU,
                      flag: 2,
                      nd: "Kích hoạt thành công",
                      status: 1,
                    };

                    await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
                  } catch (ex) {

                    let errr = {
                      MA_TB: ds[i].MA_TB,
                      THONGTIN_LOI: "Lỗi đổi trạng thái " + ex,
                    };

                    loi_bnm.push(errr);
                    let log = {
                      ma_tb: ds[i].MA_TB,
                      dkcc_id: ds[i].DKCC_ID,
                      port_id: ds[i].PORT_ID_CU,
                      vci_vpi_id: ds[i].VCI_VPI_ID_CU,
                      dslam_vdc_id: ds[i].DSLAM_VDC_ID_CU,
                      flag: 2,
                      nd: "Kích hoạt thành công, lỗi thay đổi trạng thái",
                      status: 0,
                    };

                    await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);

                    ds.splice(i, 1);
                    continue;
                  }
                }
              }
            } else {
              //#region cập nhật status và lưu log
              try {
                await this.$root.context.post(
                  `/web-ecms/doicong-danhsach/capnhat-dk-catchuyen?dkcc_id=${ds[i].DKCC_ID}&type=1`
                );

                let log = {
                  ma_tb: ds[i].MA_TB,
                  dkcc_id: ds[i].DKCC_ID,
                  port_id: ds[i].PORT_ID_CU,
                  vci_vpi_id: ds[i].VCI_VPI_ID_CU,
                  dslam_vdc_id: ds[i].DSLAM_VDC_ID_CU,
                  flag: 2,
                  nd: "Kích hoạt ảo thành công",
                  status: 1,
                };

                await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);
              } catch (ex) {

                let errr = {
                  MA_TB: ds[i].MA_TB,
                  THONGTIN_LOI: "Lỗi đổi trạng thái " + ex,
                };

                loi_bnm.push(errr);

                let log = {
                  ma_tb: ds[i].MA_TB,
                  dkcc_id: ds[i].DKCC_ID,
                  port_id: ds[i].PORT_ID_CU,
                  vci_vpi_id: ds[i].VCI_VPI_ID_CU,
                  dslam_vdc_id: ds[i].DSLAM_VDC_ID_CU,
                  flag: 2,
                  nd: "Kích hoạt thành công, lỗi thay đổi trạng thái",
                  status: 0,
                };

                await this.$root.context.post("/web-ecms/doicong-danhsach/them-log-catchuyen", log);

                ds.splice(i, 1);
                continue;
              }
            }
          }
        }

        if (ds.length > 0) {

          let json_list = ds.map((item) => {
            return {
              DKCC_ID: item.DKCC_ID,
              MA_TB: item.MA_TB,
              ONT_ID: item.ONT_ID,
              PORTVL_ID: item.PORTVL_ID,
              PORT_ID: item.PORT_ID,
              THIETBI_ID: item.THIETBI_ID,
              VCI_VPI_ID: item.VCI_VPI_ID
            }
          });

          let hscc_id = this.arrayCBO.cboHoSoTab3;

          try {

            let kq = null

            try {
              kq = await this.$root.context.post(`/web-ecms/doicong-danhsach/capnhat-go-cong-catchuyen?kieu=2&hsccId=${hscc_id}`, json_list);
            } catch (err) {
              let responseData = err.response.data
              let message = []

              if (responseData.message) {
                  message.push(responseData.message)
              }

              if (responseData.message_detail) {
                  message.push(responseData.message_detail)
              }

              if (responseData.data) {
                  let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => `${item}: ${responseData.data[item]}`)
                  message.push([...new Set(detailMessage)].join('\n'))
              }

              throw message.join('\n')
            }

            if (kq.data.result == true) {

              this.$root.$toast.success("Đã thực hiện xong gỡ cổng");

              if (loi_bnm != null && loi_bnm.length > 1) {

                this.errExcel.dataEx = []
                this.errExcel.dataEx = loi_bnm;

                let confirm = await this.$bvModal
                  .msgBoxConfirm(
                    "Đã thực hiện xong. Bạn có muốn xuất file danh sách bị lỗi không?",
                    {
                      title: "Xác nhận hành động",
                      centered: true,
                      size: "sm",
                      okTitle: 'Đồng ý',
                      cancelTitle: 'Hủy',
                    }
                  )

                  if (!confirm) return "404"

                  document.getElementById("exporterror").click();
              }

              await this.NAP_DS_THUEBAO_CAPCONG(hscc_id);
              await this.NAP_DS_PORT_FQH();
              await this.NAP_DS_LUOI_VCIVPI();
            } else {
              if (loi_bnm != null && loi_bnm.length > 1) {

                this.errExcel.dataEx = []
                this.errExcel.dataEx = loi_bnm;

                this.loading(false)

                let confirm = await this.$bvModal
                  .msgBoxConfirm(
                    "Đã thực hiện xong. Bạn có muốn xuất file danh sách bị lỗi không?",
                    {
                      title: "Xác nhận hành động",
                      centered: true,
                      size: "sm",
                      okTitle: 'Đồng ý',
                      cancelTitle: 'Hủy',
                    }
                  )

                  if (!confirm) return "404"

                  document.getElementById("exporterror").click();
              } else {
                this.$root.$toast.error("Có lỗi khi gỡ cổng: " + kq.message_detail);
              }
            }
          } catch (error) {
            this.$root.$toast.error(error);
          }
        } else {
          if (loi_bnm != null && loi_bnm.length > 1) {

            this.errExcel.dataEx = []
            this.errExcel.dataEx = loi_bnm;

            this.loading(false)

            let confirm = await this.$bvModal
              .msgBoxConfirm(
                "Đã thực hiện xong. Bạn có muốn xuất file danh sách bị lỗi không?",
                {
                  title: "Xác nhận hành động",
                  centered: true,
                  size: "sm",
                  okTitle: 'Đồng ý',
                  cancelTitle: 'Hủy',
                }
              )

              if (!confirm) return "404"

              document.getElementById("exporterror").click();
          } else {
            this.$root.$toast.error("Có lỗi khi gỡ cổng");
          }
        }
      } catch (ex) {
        this.$root.toastError("Có lỗi khi gỡ cổng: " + ex.response.data.message_detail);
      } finally {
        this.$root.showLoading(false);
      }
    },
    goCong: async function () {
      this.GO_CONG();
    },
    ganCong: async function () {
      try {
        let check = await this.KIEMTRA_GANCONG(1);

        if (!check) {
          return;
        }

        this.loading(true)

        //Lấy thông tin mã tb cần gán cổng
        let ds = this.selectedDSTab3;

        let ds_check = ds.map((item) => {
          return { ...item };
        });

        ds_check = ds_check.filter(function (item) {
          return item.DKCC_CHA_ID == null;
        });

        const key = [
          "BRAS_VDC_ID",
          "BRAS_VDC_ID_CU",
          "CHON",
          "DIACHI_LD",
          "DICHVUVT_ID",
          "DKCC_CHA_ID",
          "DSLAM",
          "DSLAM_CU",
          "DSLAM_VDC_ID",
          "DSLAM_VDC_ID_CU",
          "IP",
          "IP_CU",
          "KIEU_MEGA",
          "LOAIHINH_TB",
          "LOAITBI_ID",
          "LOAITBI_ID_CU",
          "LOAITB_ID",
          "LOG_KH",
          "MADOICAP",
          "MA_LT",
          "ONT_ID",
          "ONT_ID_CU",
          "ONT_ID_MOI",
          "PORT",
          "PORTVL_ID",
          "PORTVL_ID_CU",
          "PORT_CU",
          "PORT_ID",
          "PORT_ID_CU",
          "PORT_MOI",
          "PORT_OLD",
          "RACK",
          "RACK_CU",
          "RACK_VDC",
          "TRANGTHAI_KH_ID",
          "TRANGTHAI_CC",
          "TOCDO",
          "THUEBAO_ID",
          "THIETBI_ID",
          "THAMSO_CU",
          "THAMSO",
          "TEN_TB",
          "TEMP_KH",
          "SYSTEM_VDC",
          "SYSTEM_CU",
          "SYSTEM",
          "STATUS",
          "SLOT_CU",
          "SLOT",
          "SHELF_CU",
          "SHELF",
          "TRANGTHAI_TB",
          "VCI",
          "VCI_CU",
          "VCI_VPI_ID",
          "VCI_VPI_ID_CU",
          "VPI",
          "VPI_CU",
          "VPI_VCI_CU",
          "VPI_VCI_MOI",
        ];

        let dt_ch = ds_check.map((item) => {
          return { ...item };
        });

        dt_ch.forEach((entry) => {
          key.forEach((e) => delete entry[e]);
        });
        let ds_gancong = [];
        let port_td =  this.LAY_TS_PORTD()//this.arrayCBO.cboDslamPort.PORT_TD; //this.LAY_TS_PORTD()//
        let ds_p = [];
        let ds_vcivpi = [];
        if (port_td == 0) {
          //#region Lấy thông tin port và vci_vpi quy hoạch
          ds_p = this.arrayDataGrids.dsLuoiPortTab3;
          let ds_check_vcivpi = [];
          //let ds_check_port =this.arrayCBO.cboSelectdRow
          let ds_check_port = this.$refs['grid-port-tab3'].grid.getSelectedRecords();
          console.log('ds_check_port',ds_check_port)
          //ds_p.filter(function (item) {
          //   return item.ISCHECK == "1";
          // });
          if (this.arrayCBO.cboTrangthaiVCIVPITab3 != 4) {
            ds_vcivpi = this.arrayDataGrids.dsVCIVPITab3;
            ds_check_vcivpi =  this.$refs['grid-vcivpi-tab3'].grid.getSelectedRecords();
              console.log('ds_check_vcivpi',ds_check_vcivpi)
            //  ds_vcivpi.filter(function (item) {
            //   return item.CHON == "1";
            // });
          } else {
            for (let i = 0; i < ds_check.length; i++) {
              let data = {
                DKCC_ID: ds_check[i].DKCC_ID,
                VCI_VPI_ID: ds_check[i].VCI_VPI_ID_CU,
              };
              ds_check_vcivpi.push(data);
            }
          }

          for (let i = 0; i < dt_ch.length; i++) {
            let id_rp = await this.get_key_rp();
            let data = {
               RP_ID: id_rp,
              DKCC_ID: dt_ch[i].DKCC_ID,
              PORTVL_ID: ds_check_port[i].PORTVL_ID,
              PORT_ID: ds_check_port[i].PORT_ID,
              VCI_VPI_ID:
                this.arrayDataToCbo.cboTrangthaiVCIVPITab3 !== "4"
                  ? ds_check_vcivpi[i].VCI_VPI_ID
                  : ds_check_vcivpi.filter(function (item) {
                      return item.DKCC_ID == dt_ch[i].DKCC_ID;
                    })[0].VCI_VPI_ID,
              MA_TB: dt_ch[i].MA_TB,
              ONT_ID: ds_check_port[i].ONT_ID,
              THIETBI_ID: this.arrayCBO.cboDslamTab3,
              ADSL_PORT: "",
            };
            ds_gancong.push(data);
          }
          //#endregion
        } else {
          //#region Lấy thông tin port và vci_vpi fix
          //ds_p = this.selectedPortFix;

        let  ds_check_port = this.$refs['ref-ds-port'].grid.getSelectedRecords()

          // let ds_check_port = ds_p.filter(function (item) {
          //   return item.ISCHECK == "1";
          // }); //1
          for (let i = 0; i < dt_ch.length; i++) {
            let id_rp = await this.get_key_rp();
            let data = {
              RP_ID: id_rp,
              DKCC_ID: dt_ch[i].DKCC_ID,
              PORTVL_ID: ds_check_port[i].PORTVL_ID,
              PORT_ID: ds_check_port[i].PORT_ID,
              VCI_VPI_ID: ds_check_port[i].VCI_VPI_ID,
              MA_TB: dt_ch[i].MA_TB,
              ONT_ID: ds_check_port[i].PORT_MDF,
              THIETBI_ID: this.arrayCBO.cboDslamIDDsPort,
              ADSL_PORT: ds_check_port[i].ADSL_PORT,
            };

            ds_gancong.push(data);
          }
          //#endregion
        }
        let hscc_id = this.arrayCBO.cboHoSoTab3;
        let json_list = JSON.stringify(ds_gancong); //ds_gancong;
        console.log('json_list',json_list)
        // let rs = await this.$root.context.post(
        //   `/web-ecms/doicong-danhsach/capnhat-gan-cong-catchuyen?kieu=1&hsccId=${hscc_id}`,
        //   json_list
        // );
          let rs = await this.$root.context.post(
          '/web-ecms/doicong-danhsach/capnhat-gan-cong-catchuyen_1',{
            Kieu: 1,
            HsccId: hscc_id,
            Json_list: json_list
          }
        );
        if (rs.data.result === true) {
          this.$root.$toast.success("Gán cổng thành công");
          if (port_td == 0) {
            //#region Port QH
            let valuesToRemove = this.$refs['grid-port-tab3'].grid.getSelectedRecords();
            let t = this.arrayDataGrids.dsLuoiPortTab3.filter(item => !valuesToRemove.includes(item))
            // let t = ds_p.filter(function (item) {
            //   return item.ISCHECK == "0";
            // });
            if (t.length == 0) {
              this.arrayDataGrids.dsLuoiPortTab3 = [];
            } else {
              this.arrayDataGrids.dsLuoiPortTab3 = t.map((i, index) => ({...i, STT: ++index}));
            }
            if (this.arrayCBO.cboTrangthaiVCIVPITab3 != 4) {
               let vci_suc= this.$refs['grid-vcivpi-tab3'].grid.getSelectedRecords();
              let t_ = this.arrayDataGrids.dsVCIVPITab3.filter(item => !vci_suc.includes(item))
              if (t_.length == 0) {
                this.arrayDataGrids.dsVCIVPITab3 = [];
              } else {
                this.arrayDataGrids.dsVCIVPITab3 = t_.map((i, index) => ({...i, STT: ++index}));
              }
            } else {
              this.arrayDataGrids.dsVCIVPITab3 = [];
            }
           await this.NAP_DS_LUOI_VCIVPI();
            //#endregion
          } else {
            //#region Port Fixx
             let  ds_check_port = this.$refs['ref-ds-port'].grid.getSelectedRecords()
            var t = this.arrayDataGrids.dsPortFixTab3.filter(item => !ds_check_port.includes(item))
            if (t.length == 0) {
              this.arrayDataGrids.dsPortFixTab3 = [];
            } else {
              this.arrayDataGrids.dsPortFixTab3 = t.map((i, index) => ({...i, STT: ++index}));
            }
            //#endregion
          }
         await this.NAP_DS_THUEBAO_CAPCONG(hscc_id);
        } else {
          this.$root.$toast.error("Có lỗi khi gán cổng: " + rs.message_detail);
        }
      } catch (error) {
        console.log(error)
        this.$root.toastError(
          "Có lỗi khi gán cổng " + error.response.data.message_detail
        );
      } finally {
        this.loading(false)
      }
    },
    KIEMTRA_GANCONG: async function (kieu) {
      let hscc_id = this.arrayCBO.cboHoSoTab3
      try {
        let ck = await this.$root.context.get(
          "/web-ecms/doicong-danhsach/lay_trangthai_catchuyen_id",
          { hscc_id: hscc_id }
        );

        if (ck == null || ck.data.length == 0) {
          this.$root.$toast.error("Không lấy được trạng thái thông tin hồ sơ");
          return false;
        }

        if (ck.data.TTCC_ID == 6) {
          this.$root.$toast.error(
            "Trạng thái hồ sơ đã hoàn thành không thể cập nhật"
          );
          return false;
        }

        //#region Kiểm tra số lượng cổng chọn với số lượng thuê bao khớp nhau không
        if (this.arrayDataGrids.dsHoSoTab3 == null || this.arrayDataGrids.dsHoSoTab3.length == 0) {
          this.$root.$toast.error(
            "Chưa có thuê bao không thể gán/gỡ/hoàn thiện/kích hoạt khi đổi cổng"
          );
          return false;
        }

        let ds = this.$refs['grid-hoso-tab3'].grid.getSelectedRecords();

        let t = ds.filter(function (item) {
          return item.DKCC_CHA_ID == null;
        });

        if (t.length == 0) {
          this.$root.$toast.error(
            "Chưa có thuê bao được chọn không thể gán/gỡ/hoàn thiện/kích hoạt khi đổi cổng"
          );
          return false;
        }

        let dem_tb = t.length;
        let ds_port_chon = 0;
        let ds_vci_chon = 0;

        if (kieu == 1) {
          // gán
         let port_td = this.LAY_TS_PORTD();

          if (port_td == -1) {
            this.$root.$toast.error("Không lấy được thông tin port td");
            return false;
          }
          if (port_td == 0) {
            //#region Port Quy hoạch
            let port = this.arrayDataGrids.dsLuoiPortTab3;

            if (port == null) {
              this.$root.$toast.error("Chưa có port không thể gán cổng");
              return false;
            }

            let t_p =this.arrayCBO.cboSelectdRowPort;

            if (t_p.length == 0) {
              this.$root.$toast.error(
                "Chưa có port được chọn không thể gán cổng"
              );
              return false;
            }
            ds_port_chon = t_p.length;

            let vci = this.arrayDataGrids.dsVCIVPITab3;
            if (this.arrayCBO.cboTrangthaiVCIVPITab3 != 4) {
              if (vci == null) {
                this.$root.$toast.error("Chưa có vci/vpi không thể gán cổng");
                return false;
              }
               let t_v =      this.arrayCBO.cboSelectdRowVciVpi;
              // let t_v = vci.filter(function (item) {
              //   return item.CHON == "1";
              // });

              if (
                t_v.length == 0 &&
                this.arrayCBO.cboTrangthaiVCIVPITab3 != 4
              ) {
                this.$root.$toast.error(
                  "Chưa có vci/vpi được chọn không thể gán cổng"
                );
                return false;
              }
              ds_vci_chon = t_v.length;
            } else {
              if (vci != null && vci.length > 0) {
                this.$root.$toast.error(
                  "Với trạng thái giữ vci/vpi. Không được phép chọn"
                );
                return false;
              }
            }

            if (
              dem_tb != ds_port_chon ||
              (dem_tb != ds_vci_chon &&
                this.arrayCBO.cboTrangthaiVCIVPITab3 != 4)
            ) {
              this.$root.$toast.error(
                "Số lượng port, vci vpi không khớp với số lượng thuê bao"
              );
              return false;
            }
            //#endregion
          } else {
            //#region Port fix
            var port = this.selectedPortFix;

            if (port.length == 0) {
              this.$root.$toast.error("Chưa có port không thể gán cổng");
              return false;
            }

            let t_p = this.selectedPortFix.filter(
              (item) => item.ISCHECK === "1"
            );
            if (t_p.length == 0) {
              this.$root.$toast.error(
                "Chưa có port được chọn không thể gán cổng"
              );
              return false;
            }
            ds_port_chon = t_p.length;
            console.log("dem_tb ", dem_tb);
            console.log("ds_port_chon ", ds_port_chon);
            if (dem_tb != this.arraySelectedItem.soPort) {
              this.$root.$toast.error(
                "Số lượng port vci vpi không khớp với số lượng thuê bao"
              );
              return false;
            }
            //#endregion
          }
        }
        //#endregion
        // Kiểm tra trạng thái port chọn có đúng ko, đúng mới làm

        return true;
      } catch (ex) {
        this.$root.toastError(
          "Có lỗi khi kiểm tra gán cổng " + ex.response.data.message_detail
        );
        return false;
      }
    },
    ChuanHoa_XML: function (Xau) {

      if (!Xau) return ""

      let result = Xau + "";
      result = result.replace("&", "&amp;");
      result = result.replace("<", "&lt;");
      result = result.replace(">", "&gt;");
      result = result.replace("'", "&apos;");
      return result;
    },
    UpdateDeviceInfo: async function (iptvac, devicename, portID) {

      // let kq = await this.$root.context.post(
      //   `/web-ecms/doicong-danhsach/update-device-info?iptvac=${iptvac}&devicename=${devicename}&portID=${portID}`
      // );
      let kq = await visa.UpdateDeviceInfo(iptvac, devicename, portID, this);

      return {
        res: kq,
        err: "",
      };
    },
    DoiCongMegavnn4: async function (
      account,
      PORT,
      VCI,
      VPI,
      SLOT,
      BrasId,
      DslamId
    ) {
      try {
        await visa.changePortMegaVNN(account, BrasId, DslamId, PORT, SLOT, VCI, VPI)
      } catch (error) {
        if (
          error.includes("Không có sự thay đổi") ||
          error.includes("Đối tượng [Dịch vụ khách hàng] không tồn tại")
        )
          //Đã tồn tại ngày rồi
          return {
            rs: true,
            errr: "",
          };
        else {
          this.$root.$toast.error("Có lỗi xảy ra: " + error);
          return {
            rs: false,
            errr: error,
          };
        }
      }
      return {
        rs: true,
        errr: "",
      };
    },
    async DoiCongMegavnn7(account, PORT, VCI, VPI, SLOT, BrasId, DslamId, SYSTEMID, RACKID, SHELFID) {
      let data = {
        accountModel: {
          accountName: account
        },
        attributes: [
          {
            type: "PORT",
            value: PORT != null ? PORT : ""
          },
          {
            type: "VCI",
            value: VCI != null ? VCI : ""
          },
          {
            type: "VPI",
            value: VPI != null ? VPI : ""
          },
          {
            type: "SLOT",
            value: SLOT != null ? SLOT : ""
          },
          {
            type: "DSLAM-ID",
            value: DslamId != null ? DslamId : ""
          },
          {
            type: "BRAS-ID",
            value: BrasId != null ? BrasId : ""
          },
          {
            type: "RACKID",
            value: RACKID != null ? RACKID :  ""
          },
          {
            type: "SYSTEMID",
            value: SYSTEMID != null ? SYSTEMID : ""
          },
          {
            type: "SELFID",
            value: SHELFID != null ? SHELFID : ""
          },
          {
            type: "PARAMETER",
            value: "VISANEW"
          }
        ]
      }
      return await this.changePortMegaVNN(data)
    },
    async changePortMegaVNN(data) {
      try {
        let response = await this.$root.context.post(`/tichhop/visa-app/changePortMegaVNN`, data)

        if(response.errorCode == 0 || response.faultString.includes("Không có sự thay đổi")) {
          return {
            hasError: false,
            message: response.faultString
          }
        } else {
          return {
            hasError: true,
            message: response.faultString
          }
        }
      } catch (err) {
        return {
          hasError: true,
          message: ""
        }
      }
    },
    DoiCongAcccountVisa: async function (dr) {
      let loi = "";
      try {
        // CheckDataFacade obj = new CheckDataFacade();
        // VDCService vdcService = new VDCService(tt_nd.user_visa, tt_nd.pass_visa);
        console.log("dr",dr)
        let account = dr.MA_TB;
        let port = dr.PORT;
        // let vci = this.ChuanHoa_XML(dr.VCI);
        // let vpi = this.ChuanHoa_XML(dr.VPI);
        // let slot = this.ChuanHoa_XML(dr.SLOT);
        let vci = dr.VCI;
        let vpi = dr.VPI;
        let slot = dr.SLOT;
        let brasId = dr.BRAS_VDC_ID;
        let dslam_vdc_Id = dr.DSLAM_VDC_ID;
        //Tham so them cho dang ky 7 tham so
        let dslamId = dr.THIETBI_ID
        let systemId = dr.SYSTEM
        let rackId = dr.RACK
        let shelfId = dr.SHELF
        //Kiem tra xem dang ky 4 tham so hay 7 tham so
        let thamso = dr.THAMSO;
        console.log("thamso:", thamso)

        let l = "";
        if (thamso == 4) {
          let res = await this.DoiCongMegavnn4(
            account,
            port,
            vci,
            vpi,
            slot,
            brasId,
            dslam_vdc_Id
          );
          if (!res.rs) {
            loi = res.errr;
            return loi;
          } else {
            loi = "";
            return loi;
          }
        } else if (thamso == 7) { // BSS-90932 - BO SUNG Hieu ERP
          let DoiCongMegavnn7 = await this.DoiCongMegavnn7(account, port, vci, vpi, slot, brasId, dslam_vdc_Id, systemId, rackId, shelfId)

          if (DoiCongMegavnn7.hasError) {
            loi = DoiCongMegavnn7.message;
          } else {
            loi = "";
            return loi;
          }
        } else {
          loi = "Khác 4,7 tham số chưa được xây dựng";
          return loi;
        }
        return loi;
      } catch (ex) {
        loi =
          "Có lỗi xảy ra trong quá trình đổi cổng account trên Visa.\n" + ex;
        return loi;
      }
    },
    DoiCongAcccountVisa_cu: async function (dr) {
      let loi = "";
      try {
        // CheckDataFacade obj = new CheckDataFacade();
        // VDCService vdcService = new VDCService(tt_nd.user_visa, tt_nd.pass_visa);
        let account = this.ChuanHoa_XML(dr.MA_TB);
        let port = dr.PORT_OLD;
        let vci = this.ChuanHoa_XML(dr.VCI_CU);
        let vpi = this.ChuanHoa_XML(dr.VPI_CU);
        let slot = this.ChuanHoa_XML(dr.SLOT_CU);
        let brasId = dr.BRAS_VDC_ID_CU;
        let dslam_vdc_Id = dr.DSLAM_VDC_ID_CU;

        //Tham so them cho dang ky 7 tham so

        //Kiem tra xem dang ky 4 tham so hay 7 tham so
        let thamso = dr.THAMSO_CU;
        let l = "";
        if (thamso == 4) {
          let res = await this.DoiCongMegavnn4(
            account,
            port,
            vci,
            vpi,
            slot,
            brasId,
            dslam_vdc_Id
          );
          if (!res.rs) {
            loi = res.errr;
            return loi;
          } else {
            loi = "";
            return loi;
          }
        } else {
          loi = "Khác 4 tham số chưa được xây dựng";
          return loi;
        }
      } catch (ex) {
        loi =
          "Có lỗi xảy ra trong quá trình đổi cổng account trên Visa.\n" + ex;
        return loi;
      }
    },
    changeTramTbi: async function () {
      try{
      this.$root.showLoading(true)
      await this.NAP_DS_DSLAM();
      }finally{
       this.$root.showLoading(false)
      }
    },
    // changeDslam: async function (selectedIndex) {
    //   try {
    //     this.loading(true);
    //     let dslamTinh = this.arrayDataToCbo.dsDslamTab3;

    //     let selectedDslam = dslamTinh[selectedIndex];
    //     this.enableSelectFollowPortTD = false;
    //     this.enableSelectFollowPortTD = false;

    //     console.log("dslamTinh ", dslamTinh);
    //     console.log("selectedIndex ", selectedIndex);
    //     console.log("selectedDslam ", selectedDslam);
    //     if (dslamTinh == null) {
    //       this.arrayDataGridsdsLuoiPortTab3 = [];
    //       return;
    //     }
    //     if (dslamTinh.length > 0) {
    //       if (selectedDslam != null) {
    //         this.arrayCBO.cboDslamIDDsPort = selectedDslam.DSLAM_ID;
    //         this.arrayCBO.cboDslamPort = selectedDslam;
    //         let port_td = selectedDslam.PORT_TD;
    //         console.log("aaaaaaaaaaaaaaaaaaaaaaa ", port_td);
    //         if (port_td == -1) return;
    //         if (port_td === 1) {
    //           this.NAP_DS_PORT_FIX();
    //         } else {
    //           this.enableSelectFollowPortTD = true;
    //           this.enableSelectFollowPortTD = true;
    //           this.NAP_DS_LUOI_PORT();
    //           this.NAP_DS_LUOI_VCIVPI();
    //         }
    //       }
    //     }
    //   } catch (ex) {
    //     this.$root.toastError(
    //       "Có lỗi khi kiểm tra gán cổng " + ex.response.data.message_detail
    //     );
    //   } finally {
    //     this.loading(false);
    //   }
    // },
    changeCboTrangthaiVCIVPI: async function () {
      this.NAP_DS_LUOI_VCIVPI();
    },
    changeCboKieuQH: async function () {
      this.NAP_DS_LUOI_VCIVPI();
    },
    changeCboLoaiQH: async function () {
      this.NAP_DS_LUOI_VCIVPI();
    },
    NAP_DS_DSLAM: async function () {
      try {
        //this.loading(true);
        let tramtbId = this.arrayCBO.cboTramTBTab3;
        let ck = await this.$root.context.get("/web-ecms/doicong-danhsach/lay-ds-dslam",{ tramtbId: tramtbId });
        this.arrayDataToCbo.dsDslamTab3 = ck.data;
        if (ck.data !=null && ck.data.length > 0) {
          this.enableSelectFollowPortTD = true;
          this.visibleGridTab3 = true;

          this.arrayCBO.cboDslamTab3 = ck.data[0].DSLAM_ID;
          let port_td = ck.data[0].PORT_TD;
          if (port_td === 1) {
            this.sPort_TD = true;
            this.enableSelectFollowPortTD = false;
          } else {
            this.enableSelectFollowPortTD = true;
            this.sPort_TD = false;
          }
          console.log('this.enableSelectFollowPortTD',this.enableSelectFollowPortTD)
        }
        else {
          console.log('this.enableSelectFollowPortTD',this.enableSelectFollowPortTD)
          this.enableSelectFollowPortTD = false;
          this.visibleGridTab3 = false;
          this.arrayDataToCbo.dsDslamTab3 = [];
          this.arrayCBO.cboDslamTab3 = -1;
        }

      } catch (e) {
        this.$root.toastError("Có lỗi " + e.response.data.message_detail);
      } finally {
        //this.loading(false);
      }
    },
    NAP_DS_LUOI_PORT: async function () {
      try {
      //  this.loading(true);
        let dtDslam = this.arrayCBO.cboDslamPort
        let dslamId;
        let loatb_id;
        if (dtDslam != null) {
          loatb_id = dtDslam.LOAITBI_ID;
          dslamId = dtDslam.DSLAM_ID;
        }
         console.log('loatb_id ',loatb_id)
        if (loatb_id != null && dslamId != null) {

          if (loatb_id == 1){
            this.arrayDataGrids.dsLuoiPortTab3 = []
            this.$root.$toast.error("Chức năng chưa được hỗ trợ");
            return;
            // let ck = await this.$root.context.get(`/web-ecms/doicong-danhsach/lay-ds-luoi-port?dslamId=40284&loaitbId=3`)
            // if (ck.error_code == "BSS-00000000") {
            //   this.arrayDataGrids.dsLuoiPortTab3 = ck.data;
            // } else {
            //   this.$root.$toast.error(ck.message_detail);
            // }
          }
          else {
            let ck = await this.$root.context.get(
            `/web-ecms/doicong-danhsach/lay-ds-luoi-port?dslamId=${dslamId}&loaitbId=${loatb_id}`
            );
            if (ck.error_code == "BSS-00000000") {
              this.arrayDataGrids.dsLuoiPortTab3 = ck.data.map((i, index) => ({...i, STT: ++index}));
            } else {
              this.$root.$toast.error(ck.message_detail);
            }
          }

        }
      } catch (e) {
        this.$root.toastError(
          "Có lỗi xẩy ra: " + e.response.data.message_detail
        );
      } finally {
       // this.loading(false);
      }
    },
    LOAD_HOSO_CATCHUYEN2: async function () {
      try {
        //this.loading(true);
        let maTB = "";
        let input = this.arrayInputTab1.soMayAcc;
        if (input != "" && this.activetab == 3) {
          let dsTB = await this.$root.context.get(
            "/web-ecms/doicong-danhsach/lay-ds-thuebao",
            { matb: input }
          );
          if (dsTB.data.length > 0) {
            maTB = dsTB.data[0].MA_TB;
          } else {
            this.$root.toastError(
              "Không tìm thấy Số máy/ACC " + input + " "
            );
            return;
          }
        }
        let data = {
          maThueBao: maTB,
          cboTths: this.arrayCBO.cboTTHS,
          tuNgay:
            this.activetab == 3
              ? this.convertDate2String(this.arrayDate.fromDate)
              : this.convertDate2String(this.arrayDate.fromDate),
          denNgay:
            this.activetab == 3
              ? this.convertDate2String(this.arrayDate.toDate)
              : this.convertDate2String(this.arrayDate.toDate),
        };
        let rs = await this.$root.context.post(
          "/web-ecms/doicong-danhsach/lay-ds-hoso-catchuyen",
          data
        );
        if (rs.error_code === "BSS-00000000") {
          this.arrayDataToCbo.dsHoSoCCTab3 = rs.data;
          if (rs.data !=null && rs.data.length > 0) {
            if (this.hscc_id) {
              this.setcboHSTab3(this.hscc_id)
              //this.arrayCBO.cboHoSoTab3 = rs.data[0].HSCC_ID;
            }
          await  this.NAP_DS_THUEBAO_CAPCONG(this.arrayCBO.cboHoSoTab3);
          }
        } else {
          this.$root.$toast.error("Có lỗi xẩy ra trong quá trình lấy dữ liệu.");
        }
      } catch (e) {
        this.$root.toastError(
          "Có lỗi xẩy ra: " + e.response.data.message_detail
        );
      } finally {
        // this.loading(false);
      }
    },
    changeHSCCTab3: async function (data) {
      try {
        if(data!=null){
        this.arrayCBO.cboHoSoTab3 = data.HSCC_ID
        await this.NAP_DS_THUEBAO_CAPCONG(this.arrayCBO.cboHoSoTab3);
        }
      } catch (e) {
        this.$root.toastError(
          "Có lỗi xẩy ra: " + e.response.data.message_detail
        );
      } finally {
        this.loading(false);
      }
    },
    NAP_DS_THUEBAO_CAPCONG: async function (hscc_id) {
      try {
        this.loading(true);
        let ck
        if (this.tag == 3) {
          console.log('mm : ',this.tag )
          ck = await this.$root.context.get("/web-ecms/doicong-danhsach/lay_ds_thuebao_capcong_thaydoi_ts_internet", {
            hsccId: hscc_id,
            vma_tb: this.ma_tb_ts_internet
          })
        } else {
          console.log('nn : ',hscc_id)
          ck = await this.$root.context.get(
            "/web-ecms/doicong-danhsach/lay-ds-thuebao-catchuyen-tab3",
            { hsccId: hscc_id }
          );
        }
         let result = ck.data;
        if (ck.data != null) {
          //this.arrayDataGrids.dsHoSoTab3 = ck.data;
          const newState = result.map((obj, index) =>
            obj.TRANGTHAI_KH_ID == "10"
              ? {
                  ...obj,
                  STT: ++index,
                  TRANGTHAI_KH_ID:'1'
                   // '<span style="margin-left: 45%"><i class="fa fa-check"></i></span>',
                }
              : {
                  ...obj,
                  STT: ++index,
                  TRANGTHAI_KH_ID:
                    '',
                }
          );
          this.arrayDataGrids.dsHoSoTab3 = newState;
          console.log('uuu : ',newState)

        }
      } catch (e) {
        console.log(e)
        this.$root.toastError(
          "Có lỗi xẩy ra: " + e.response.data.message_detail
        );
      } finally {
        this.loading(false);
      }
    },
    hienThiDanhSachCatChuyen: async function (index, kieu) {
        if (index == 1) {
          await this.NAP_DS_HOSO();
        } else if (index == 2) {
          await this.LOAD_HOSO_CATCHUYEN();

          // if (tt_nd.tentat == "HNI")
          // {
          //   tsbtnGiuVciVpi.Visible = true;
          // }
        } else {
          if (kieu == 0) {
            // load het
             this.NAP_DS_QUYHOACH();
            await this.NAP_DS_TRAMTBI();
            await this.NAP_DS_TRANGTHAI_VCIVPI();
            this.NAP_DS_QH();
            //region Load lưới port fix và vci vpi
            try {
              let port_td =  this.LAY_TS_PORTD()//this.arrayCBO.cboDslamPort;
                if (port_td == "1")
                {
                  await this.NAP_DS_PORT_FIX();
                }
                else
                {
                 // await Promise.all([this.NAP_DS_LUOI_VCIVPI,this.NAP_DS_LUOI_PORT]);
                  await this.NAP_DS_LUOI_VCIVPI();
                  await this.NAP_DS_LUOI_PORT();
                }
                await this.LOAD_HOSO_CATCHUYEN2();
              // }
              // else
              // {
              // }
            }
            catch (error) {
            }
            finally {
            }
          } else if (kieu == 1) {
            //if (ttcc_ht_id != cboTTHS.EditValue.ToString())
            {
              await this.LOAD_HOSO_CATCHUYEN2();
            }
            //#region Load lưới port fix và vci vpi

            var port_td =  this.LAY_TS_PORTD()//this.arrayCBO.cboDslamPort.PORT_TD; //this.LAY_TS_PORTD();
            if (port_td == "-1") return;
            if (port_td == "1") {
              await this.NAP_DS_PORT_FIX();
            } else {
              await this.NAP_DS_LUOI_VCIVPI();
              await this.NAP_DS_LUOI_PORT();
            }
            //#endregion
            if (this.arrayDataToCbo.dsHoSoCCTab3 !== null) {
              await this.NAP_DS_THUEBAO_CAPCONG(this.arrayCBO.cboHoSoTab3); //Convert.ToInt64(cboHS.EditValue)
            }
          }
        }
    },
    lay_thongtin_nguoidung: async function () {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/thongtin_nguoidung"
        );
        return rs.data[0]
      } catch (error) {
        return null;
      }
    },
    onQueryCellInfodsHoSoTab3(args){

      if (!!args.column.field && args.column.field == 'TRANGTHAI_KH_ID' && args.data.TRANGTHAI_KH_ID != '') {
        args.cell.innerHTML = '<span><i class="fa fa-check" style="color:green;"></i></span>'
        return
      }

      if (!args.column.field) {

        let hasCheckBox = args.cell.querySelector('[type="checkbox"]')

        if (hasCheckBox && !!args.data.DKCC_CHA_ID) {
          args.cell.innerHTML = ""
        }
      }
    },
    grid_dataGridCellCreated(e) {
      // console.log(e)
      e.cellAllowHtml = true;
      if(e.cellDataItem.TRANGTHAI_KH_ID !=''){
      $(e.cellElement).empty();
      $(e.cellElement).append(
        '<span><i class="fa fa-check" style="color:green;"></i></span>'
      );
      }
    },
    get_key_rp: async function(){
       let rs = await this.$root.context.get(
            "/web-ecms/doicong-danhsach/get_keys_rp"
          );
       return rs.data
    },
    addRowPort(number) {
      let data = this.$refs['grid-port-tab3'].grid.getSelectedRecords();
      if(data.length != this.arraySelectedItem.soChonAcc){
        let rowIndexes = [];
        for (let i = 0; i < number; i++) {
          rowIndexes.push(i);
        }
        this.$refs['grid-port-tab3'].selectRows(rowIndexes);
      }
    },
    addRowVci(number) {
      let data = this.$refs['grid-vcivpi-tab3'].grid.getSelectedRecords();
      if(data.length != this.arraySelectedItem.soChonAcc){
        let rowIndexes = [];
        for (let i = 0; i < number; i++) {
          rowIndexes.push(i);
        }
        this.$refs['grid-vcivpi-tab3'].selectRows(rowIndexes);
      }
    },
    colCheckboxGiuVciVpi(parent) {
                return function(rowData) {
                    return {
                        template: {
                            template: `
                                <div class="check-action">
                                  <input
                                      type="checkbox"
                                      class="check"
                                      :value="rowData.MA_TB"
                                      :checked="rowData.CHECKED === '1'"
                                      @change="check($event)"
                                      />
                                  <span class="name checkvci"></span>
                              </div>
                            `,
                            data() {
                                return {
                                    parent: parent,
                                    rowData: rowData,
                                }
                            },
                            methods: {
                              check(e) {
                                let isChecked = e.target.checked;
                                this.parent.selectedDSTab2.forEach((item, i) => {
                                    if (item.MA_TB == this.rowData.MA_TB) {
                                        item.CHECKED  = isChecked ? "1" : "0";
                                    }
                                });
                               EventBus.$emit('check', this.parent.selectedDSTab2);
                              }
                            },
                            watch: {
                              'data': {
                                handler: function (val) {
                                  this.rowData = val;
                                },
                                immediate: true
                              }
                            }
                        },

                    }
                }
    },
    allChangeCheckbox(event){
      let isChecked = event.target.checked ? "1" : "0";

      // Chỉ cập nhật những item có CHON === "s1"
      this.selectedDSTab2.forEach((item, index) => {
        if (item.CHON === "s1") {
          this.$set(this.arrayDataGrids.dsHoSoTab2, index, { ...item, CHECKED: isChecked });
        }
      });
      console.log('dsHoSoTab2 ', this.arrayDataGrids.dsHoSoTab2); // Chi thu thay doi gia tri o mang ni xem
      console.log('selectedDSTab2 ', this.selectedDSTab2);
    },
    getGiuVciVpi(data){
      this.selectedDSTab2 = [];
      this.selectedDSTab2 = data;
      console.log('event ', this.selectedDSTab2);
    },
    destroyed () {
        EventBus.$off('check', this.getGiuVciVpi);
    },
  },

};
</script>
<style scoped>
a.disabled {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
.error-boarder {
  border-color: red;
}
.greentext {
  color: rgb(27, 123, 248) !important;
}
.grid-stack-box .legend-title {
    font-weight: 600;
    padding-bottom: 5px;
    margin-bottom: 16px;
    border-bottom: 1px solid #0176FF;
    min-height: 38px;
    line-height: 20px;
    display: flex;
    align-items: end;

}
.grid-stack-box .legend-title  .select2vpi{
  position: absolute;
    right: 0;
}
</style>

