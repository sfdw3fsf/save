<template src="./index.html"></template>

<script>
import breadcrumb from "@/components/breadcrumb";
import gridview from "@/components/Shared/gridview";
import api from "./api/index.js";
export default {
  components: { breadcrumb, gridview },
  name: "",

  data() {
    return {
      header: {
        title: "Hoàn thiện hồ sơ thay đổi thông số MegaWan",
        list: [
          { name: "Hợp đồng", link: { name: "Ui.cards" } },
          {
            name: "Hoàn thiện hồ sơ thay đổi thông số MegaWan",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      optionTrangThai: '0', // default là chọn phiếu mới
      dsHBTB: [],
      searchDate: {
        isEnabled: true,
        isEnabledFromDate: false,
        isEnabledToDate: false,
        fromDate: new Date(),
        toDate: "",
        dateconfig: {
          altFormat: "d/m/Y H:i:S",
          altInput: true,
          dateFormat: "d/m/Y H:i:S",
          allowInput: true,
        },
      },
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close':this.popupClosed,
        'acceptChangeCTV':this.popupClosed,
        'acceptChangeNGT':this.popupClosed,
      },
      popupComponentName: '',
      gridbox: {
        bohoso: {
          list: [],
          header: [],
          value: {},
        },
        filehoso: {
          list: [],
          header: [],
          value: {},
        },
        hdthuebao: {
          list: [],
          header: [],
          value: {},
        },
      },
      dateconfig: {
        altFormat: "d/m/Y H:i:S",
        altInput: true,
        dateFormat: "d/m/Y H:i:S",
        allowInput: true,
        enableTime: true,
      },
      dsDichVu: [],
      dsQuyTrinh: [],
      formViewTTKH: {
        dichVu: [],
        dichVuSelected: "",
        maGiaoDich: "",
        kieuLD: "",
        quyTrinh: [],
        quyTrinhSelected: "",
        account: "",
        soAo: "",
        tenThueBao: "",
        diaChiLd: "",
        noiDungGiao: "",
        noiDungTra: "",
        ghiChu: "",
      },

      formViewGiaoViec: {
        ngayGV: "",
        ngayHT: "",
        nguoiGVSelected: "",
        nguoiGV: [],
      },

      formViewHoanCong: {
        loaiKenh: [],
        loaiKenhSelected: "",
        chuQuan: [],
        chuQuanSelected: "",
        trangBi: [],
        trangBiSelected: "",
        loaiThietBi: [],
        loaiThietBiSelected: "",
        modem: [],
        modemSelected: "",
        nhanVienYC: "",
        tocDoCir: [],
        tocDoCirSelected: "",
        tocDoPir: [],
        tocDoPirSelected: "",
        tocDoNix: [],
        tocDoNixSelected: "",
        tocDoIsp: [],
        tocDoIspSelected: "",
        thoiHan: [],
        thoiHanSelected: "",
        thayDoiTD: "",
        diaChiKnMoi: "",
        huongKN: "",
        dslam: "",
        adslPort: "",
        nasportID: "",
        tenThietBi: [],
        wanIP: "",
        lanIP: "",
        dslamTinh: "",
        link: "",
        siteID: "",
        tinhKN: [],
        portMDF: "",
        ipDslam: "",
        svlan: "",
        cvlan: "",
        vrfVfi: "",
        route: "",
      },
      tableConfigNGV: {
        tableCollumns: [
          {
            fieldName: "ten_nv",
            headerText: "Tên nhân viên",
            allowFiltering: true,
            allowSorting: false,
            width: 200,
          },
          {
            fieldName: "so_dt",
            headerText: "Số điện thoại",
            allowFiltering: true,
            allowSorting: false,
            width: 200,
          },
          {
            fieldName: "nhiem_vu",
            headerText: "Nhiệm vụ",
            allowFiltering: true,
            allowSorting: false,
            width: 200,
          },
          {
            fieldName: "ghi_chu",
            headerText: "Ghi chú",
            allowFiltering: true,
            allowSorting: false,
            width: 200,
          },
        ],
        tableData: [
          {
            ten_nv: "123",
            so_dt: "123",
            nhiem_vu: "xxx",
            ghi_chu: "1231",
          },
        ],
      },
      tableConfigDSThueBao: {
        tableCollumns: [
          {
            fieldName: "ngay_yc",
            headerText: "Ngày Yc",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "ngay_giao",
            headerText: "Ngày giao",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "hen_tu",
            headerText: "Hẹn từ",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "hen_den",
            headerText: "Hẹn đến",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "ma_gd",
            headerText: "Mã GD",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "account",
            headerText: "Account",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "so_ao",
            headerText: "Số ảo",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "loai_hinh",
            headerText: "Loại hình",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "ten_tb",
            headerText: "Tên thuê bao",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "khu_vuc",
            headerText: "Khu vực",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "diem_cha",
            headerText: "Điểm cha",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "hen_den",
            headerText: "Ghi chú",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "dia_chi_ld",
            headerText: "Địa chỉ lắp đặt",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "tt_in",
            headerText: "Trạng thái in",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "ngay_ban_giao",
            headerText: "Ngày bàn giao",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "dv_giao",
            headerText: "Đơn vị giao",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "ghi_chu",
            headerText: "Ghi chú",
            allowFiltering: true,
            allowSorting: false,
          },
        ],
        tableData : []
      },
    };
  },
  created() {
    // lay combobox dich vu
    api.getDichVu(this.axios).then((res) => {
      console.log(res.data.data);
      if (res.data.data && res.data.data.length > 0) {       
        this.formViewTTKH.dichVu = res.data.data.filter((item => item.DICHVUVT_ID == 8));
        this.formViewTTKH.dichVuSelected = res.data.data.filter((item => item.DICHVUVT_ID == 8))[0].DICHVUVT_ID;
      }
    });
    // lay combobox quy trinh
    let dataQuyTrinh = {
      dsloaihd_id: "1",
      dichvuvt_id: 8,
      tthd_id: 4,
      kieu: 0,
    };

    api.getQuyTrinh(this.axios, dataQuyTrinh).then((res) => {
      console.log("quy trinh");
      console.log(res.data.data);

      if (res.data.data && res.data.data.length > 0) {
        this.formViewTTKH.quyTrinh = res.data.data;
        this.formViewTTKH.quyTrinhSelected = res.data.data[0].quytrinh_id;
      }
    });

    // api get ds nguoi GV
    let dataNGV = {
      nghiepvu: "DS_NGUOI_GIAOVIEC",
      ds_para: '[{"DONVI_ID":"1"}]',
    };
    api.getDanhSachNguoiGV(this.axios, dataNGV).then((res) => {
      console.log("Nguoi GV");
      console.log(res.data.data);
      if (res.data.data && res.data.data.length > 0) {
        this.formViewTTKH.nguoiGV = res.data.data;
      }
    });

    // api  lay loai kenh
    api.getLoaiKenh(this.axios).then((res) => {
      console.log("loai kenh");
      console.log(res.data.data);
      if (res.data.data && res.data.data.length > 0) {
        this.formViewHoanCong.loaiKenh = res.data.data;
        this.formViewHoanCong.loai;
      }
    });

    // api get trang bi
    api.getTrangBiMEGAWAN(this.axios).then((res) => {
      console.log("trang bi");
      console.log(res.data.data);
      if (res.data.data && res.data.data.length > 0) {
        this.formViewHoanCong.trangBi = res.data.data;
      }
    });

    // api get toc do
    api.getTocDoPir(this.axios).then((res) => {
      console.log("toc do");
      console.log(res.data.data);
      if (res.data.data && res.data.data.length > 0) {
        this.formViewHoanCong.tocDoCir = res.data.data;
        this.formViewHoanCong.tocDoPir = res.data.data;
        this.formViewHoanCong.tocDoNix = res.data.data;
        this.formViewHoanCong.tocDoIsp = res.data.data;
      }
    });
    // api get loai chu quan
    api.getChuQuanMEGAWAN(this.axios).then((res) => {
      console.log("chu_quan");
      console.log(res.data.data);
      if (res.data.data && res.data.data.length > 0) {
        this.formViewHoanCong.chuQuan = res.data.data;
      }
    });

    // api get loai thiet bi
    let dataLoaiTB = { nghiepvu: "DS_THIETBI_DAUCUOI", ds_para: "" };
    api.getLoaiTBWan(this.axios, dataLoaiTB).then((res) => {
      console.log("loai tb");
      console.log(res.data.data);
      if (res.data.data && res.data.data.length > 0) {
        this.formViewHoanCong.loaiThietBi = res.data.data;
      }
    });

    // api get mode,
    api.getModem(this.axios).then((res) => {
      console.log("modem");
      console.log(res.data.data);
      if (res.data.data && res.data.data.length > 0) {
        this.formViewHoanCong.modem = res.data.data;
      }
    });

    // api get Thoi Han
    api.getThoiHanMEGAWAN(this.axios).then((res) => {
      console.log("thoi han");
      console.log(res.data.data);
      if (res.data.data && res.data.data.length > 0) {
        this.formViewHoanCong.thoiHan = res.data.data;
      }
    });

    // api get Tinh ket noi
    api.getTinhKN(this.axios).then((res) => {
      console.log("Tinh KN");
      console.log(res.data.data);
      if (res.data.data && res.data.data.length > 0) {
        this.formViewHoanCong.tinhKN = res.data.data;
      }
    });

    this.layDanhSachHDTB()
   
  },
  mounted() {},
  computed: {},
  watch: {   
    optionTrangThai(){
      console.log('thay đổi rồi đó');
      this.layDanhSachHDTB()
    }
    
  },
  methods: {
    layDanhSachHDTB(){
      this.loading(true)
      let dataHDTB = {
          "dichvuvt_id":4,
          "tthd_id":1,
          "nhanvien_id":this.$root.token.getNhanVienID(),
          "ma_gd":0,
          "phieu_id":0,
          "loaihd_id":1,
          "caidat_dv":0,
          "kieu_id":0,
          "huonggiao_id":0,
          "loai_id":this.optionTrangThai
      }
      api.getDanhSachHDTB(this.axios, dataHDTB).then(res => {
          console.log('ds HDTB');
          console.log(res.data.data)
          let temp = res.data.data
          this.dsHBTB = res.data.data
          if (res.data.data && temp.length > 0) {
            this.tableConfigDSThueBao.tableData = []
              for (let i = 0; i < temp.length; i++) {             
                  const element = temp[i];
                  let obj = {
                      ngay_yc: element.ngay_yc,
                      ngay_giao: element.ngaygiao,
                      hen_tu: element.giohen_tu,
                      hen_den: element.giohen_den,
                      ma_gd: element.ma_gd,
                      account: element.ma_tb,
                      so_ao: element.ma_lt,
                      loai_hinh: element.loaihinh_tb,
                      ten_tb: element.ten_tb,
                      khu_vuc: element.ten_kv,
                      diem_cha: '',
                      dia_chi_ld: element.diachi_ld,
                      tt_in: element.luu_in,
                      ngay_ban_giao: element.ngay_ht,
                      dv_giao: element.dv_giao,
                      ghi_chu: element.ghichu_tb,

                  }
                  this.tableConfigDSThueBao.tableData.push(obj);
                  this.loading(false)
              }
          }
      })
    },   
    selectItem(data) {
      console.log(data.rowData)
    },
    selectItemThueBao(item) {
      console.log(item.rowData)
      console.log(this.dsHBTB);
      let dataFind = this.dsHBTB.filter(it => it.ma_tb == item.rowData.account)[0];
      console.log(dataFind);
      try {

      } catch (ex) {

      }
    },
    showPopup() {
      this.$refs.Thaydoimk.show();
    },

    alertMessage(message) {
      this.$bvToast.toast(message, {
        title: "Thông báo",
        toaster: "b-toaster-bottom-right",
        autoHideDelay: 5000,
        appendToast: false,
      });
    },

    showModal() {
      this.$refs["ref-modal"].show();
    },

    hideModal() {
      this.$refs["ref-modal"].hide();
    },
    enableFromDate() {
      this.searchDate.isEnabledFromDate = !this.searchDate.isEnabledFromDate;
    },
    enableToDate() {
      this.searchDate.isEnabledToDate = !this.searchDate.isEnabledToDate;
    },
    async Popup(modalId) { 
      this.$bvModal.show(modalId); 
    }, 
    async ClosePopup(modalId) { 
      this.$bvModal.hide(modalId); 
    }, 

    popupClosed(modalId){
      this.$bvModal.hide('popupComponents'); 
    },
    
    popupDSHuongKN() {
      this.popupComponent = ()=>import('@/modules/contract/setup/InstallNewSubs/Popups/popupDSHuongKN');
      this.popupComponentName = "popupDSHuongKN";
      this.popupComponentData = { kieu:9, dichvuvt_id: 0, tinhkn_id:0 }; // O day dung enums CHUQUAN.VNPT_TINH nhung chi co 1 gia tri duy nhat = 9 nen loai bo enums luon!
      this.Popup('popupComponents');
    },
  },
};
</script>

<style>
</style>