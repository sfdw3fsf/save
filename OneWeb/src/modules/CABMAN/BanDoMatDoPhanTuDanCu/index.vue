<template src="./index.template.html"></template>
<script>
import XLSX from "xlsx";
import html2canvas from "html2canvas";
import GMap from "./Gmap/GMap.vue";
import GraphNode from "./structures/GraphNode";
import "./../../../utils/gmaps";
import BanDoVNPT from "./BanDoVNPT/BanDoVNPT"

let colorBar = [
  "blue",
  "gray",
  "red",
  "green",
  "#8FBC8F",
  "#8A2BE2",
  "#00FFFF",
  "#FFFF00",
];
export default {
  components: { XLSX, html2canvas, GMap, BanDoVNPT },
  data: function () {
    return {
      kqTimKiem: [],
      dskieuBanDo: [],
      dsloaiBanDo: [],
      thongtinbando: null,
      dsduLieu: [],
      dsnhaCC: [],
      dsphamVi: [],
      dskichThuocHat: [],
      dskichThuocButVe: [],
      dsdoiTuong: [],
      thongtinKhuVucPT: [],
      dsChuQuan: [],
      quyenTruyCap: 0,
      dataExcel: [],
      isViewThongTin: false,
      isDoKC: false,
      thongTinKhuVuc: {
        ToaDo: "21.019324322184655, 105.80942518391265",
        Zoom: 15,
        TongDienTich: 15.727,
        donvi: "km2",
      },
      infoMapDefault: {
        lat: 21.019324322184655,
        lng: 105.80942518391265,
        zoom: 15,
      },
      ketquaPhanTich: {
        TongSoKC_HD: "1. Tổng số hộ dân:",
        MatDoTB: "2. Mật độ trung bình:",
      },
      valuebar: 25,
      dataSelected: {
        kieuBanDo: 0,
        loaiBanDo: 0,
        dulieu: 1,
        phamvi: 0,
        doiTuong: -1,
        chuquan: "",
        kichthuocHat: 1,
        kichThuocButVe: 39,
      },
      stateButton: {
        isDisableLamMoi: false,
        isDisablePhanTich: false,
      },
      dungLuongThiTruong: {
        tongDanCu: 0,
        thiphanVNPT: 0,
        tongFiber: 0,
        tongMega: 0,
        tongMyTV: 0,
        tongCD: 0,
        thiphanViettel: 0,
        thiphanFPT: 0,
      },
      nangLucHaTang: {
        haTangVNPT: {
          tongSoKetCuoi: 0,
          dungLuongLapDat: 0,
          dungLuongDaDung: 0,
        },
        haTangViettel: {
          tongSoKetCuoi: 0,
          dungLuongLapDat: 0,
          dungLuongDaDung: 0,
        },
        haTangFPT: {
          tongSoKetCuoi: 0,
          dungLuongLapDat: 0,
          dungLuongDaDung: 0,
        },
      },
      titleKV: "Chọn công cụ để xem chi tiết từng khu vực nhỏ",
      typeKVChon: 0,
      recPolygon: "",
      areaMap: 0,
      nodes: [],
      dataPoints: [],
      recKVCon:"",
      showData:false,
      pSize:30,
      isShowPhanTich:false,
      typeTTKV:0,
      center: {
        lat: 21.019324322184655,
        lng: 105.80942518391265,
      },
    };
  },
  created: function () {
    this.getKieuBanDo();
    this.getLoaiBanDo();
    this.getMucTruyCap();
    this.getChuQuan();
    this.getKichThuocHat();
    this.getKichThuocButVe();
    this.getPhamVi();
    this.getThongTinBanDo();
  },
  watch: {
    nodes: function (val) {
      let map = this.$refs.mapcontrol;
      map.clear();
      map.setNodes(this.nodes.slice(0));
      map.setLockMap(false);
      map.setLocationEx(null);
    },
    dataPoints: function (val) {
      let map = this.$refs.mapcontrol;
      map.clear();
      let size = this.dataSelected.kichThuocButVe;
      let opacity = this.valuebar / 100;
      map.setDataPoints(this.dataPoints.slice(0),size,opacity);
      map.setLockMap(false);
      map.setLocationEx(null);
    },
    infoMapDefault: function () {},
  },
  methods: {
    //#region "Hàm lấy dữ liệu"
    getMucTruyCap: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/bando_matdo/quyen_nhanvien"
        );
        this.quyenTruyCap = rs.data.MUC_ID;
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    getKieuBanDo: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/bando_matdo/dskieubando"
        );
        this.dskieuBanDo = rs.data;
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    getLoaiBanDo: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/bando_matdo/dsloaibando"
        );
        this.dsloaiBanDo = rs.data;
        this.getDuLieu();
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    getDuLieu: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/bando_matdo/dsdulieu/" + this.dataSelected.loaiBanDo
        );
        this.dsduLieu = rs.data;
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    getChuQuan: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/bando_matdo/dschuquan"
        );
        this.dsnhaCC = rs.data;
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    getPhamVi: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/bando_matdo/dsphamvi"
        );
        this.dsphamVi = rs.data;
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    getKichThuocHat: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/bando_matdo/kich_thuoc_hat"
        );
        this.dskichThuocHat = rs.data;
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    getKichThuocButVe: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/bando_matdo/kich_thuoc_but_ve"
        );
        this.dskichThuocButVe = rs.data;
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    getThongTinBanDo: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/bando_matdo/thongtin_bando"
        );
        if (rs.data != null && rs.data.length > 0)
          this.thongtinbando = rs.data[0];
        this.infoMapDefault.lat = this.thongtinbando.C_LATITUS;
        this.infoMapDefault.lng = this.thongtinbando.C_LONGTITUS;
        this.infoMapDefault.zoom = this.thongtinbando.DEFAULT_ZOOM;
        let map = this.$refs.mapcontrol;
        map.setinfoMapDefault(this.infoMapDefault);
        this.getThongTinTrungTam();
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    layThongtinKhuVucChon: async function (rec) {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/bando_matdo/thongtin_khuvuc_chon",
          {
        //    rec: "20.7589631,106.7488718;20.7520206,106.7263412;20.7512179,106.7139816;20.7529436,106.7039824;20.7590433,106.6998196;20.7632569,106.6949272;20.7660257,106.6915798;20.7656645,106.6850567;20.7657046,106.6760874;20.7678314,106.6750145;20.7729675,106.6714954;20.7827177,106.6674614;20.7830387,106.6617107;20.7817548,106.6592216;20.7921463,106.6540289;20.7964793,106.6476774",
          rec:  this.recKVCon
          }
        );
        if(rs.data!=null &&rs.data.length>0)
        this.thongtinKhuVucPT[this.typeKVChon] = rs.data[0];

      } catch (error) {
        console.log(error)
      } finally {
        this.$root.showLoading(false);
      }
    },
    onChangeKieuBanDo: async function () {
      try {
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    onChangeLoaiBanDo: async function () {
      try {
        await this.getDuLieu();
      } catch (error) {
        console.log(error);
      } finally {
        this.$root.showLoading(false);
      }
    },
    onChangeCanBang: async function () {
      try {
        this.data.kichThuocButVe=48
      } catch (error) {
        console.log(error);
      } finally {
        this.$root.showLoading(false);
      }
    },
    lamMoi: function () {
      this.ketquaPhanTich.TongSoKC_HD = "";
      this.ketquaPhanTich.MatDoTB = "";
      this.stateButton.isDisableLamMoi = false;
      this.stateButton.isDisablePhanTich = false;
      this.kqTimKiem = [];
      let map = this.$refs.mapcontrol;
      map.setNullHeatmap();
      map.setLockMap(true);
      map.clear();
      this.isShowPhanTich=false
    },
    showThongtin: async function (rec) {
      this.$root.showLoading(true);
      try {
        await this.layThongtinKhuVucChon(rec);
        if (this.thongtinKhuVucPT != null && this.thongtinKhuVucPT.length > 0) {
          this.showData =true
          this.showThongTinChitiet(this.typeKVChon)
        }else
          this.showData =false
      } catch (error) {
        this.$toast.error("Có lỗi xảy ra!");
      } finally {
        this.$root.showLoading(false);
      }
    },
    showThongTinChitiet: function(type){
      try{
            this.typeTTKV=type
          if(this.thongtinKhuVucPT[this.typeTTKV] == "undefined"){
             console.log('i',this.dungLuongThiTruong)
         Object.assign(this.dungLuongThiTruong, this.$options.data().dungLuongThiTruong);
         Object.assign(this.nangLucHaTang, this.$options.data().nangLucHaTang);
        
            }else
          if(this.thongtinKhuVucPT[this.typeTTKV]!=null){
        //  this.showData=true
          // Lấy dữ liệu phần dung lượng
          this.dungLuongThiTruong.tongDanCu = this.thongtinKhuVucPT[this.typeTTKV].DANSO;
          this.dungLuongThiTruong.thiphanVNPT =
            this.thongtinKhuVucPT[this.typeTTKV].THIPHAN_TB_VNPT;
          this.dungLuongThiTruong.tongFiber =
            this.thongtinKhuVucPT[this.typeTTKV].TONG_FIBER_VNPT;
          this.dungLuongThiTruong.tongMega =
            this.thongtinKhuVucPT[this.typeTTKV].TONG_MEGA_VNPT;
          this.dungLuongThiTruong.tongMyTV =
            this.thongtinKhuVucPT[this.typeTTKV].TONG_MYTV_VNPT;
          this.dungLuongThiTruong.tongCD =
            this.thongtinKhuVucPT[this.typeTTKV].TONG_CD_VNPT;
          this.dungLuongThiTruong.thiphanViettel =
            this.thongtinKhuVucPT[this.typeTTKV].THIPHAN_TB_VIETTEL;
          this.dungLuongThiTruong.thiphanFPT =
            this.thongtinKhuVucPT[this.typeTTKV].THIPHAN_TB_FPT;

          // Lấy dữ liệu hạ tầng
          this.nangLucHaTang.haTangVNPT.tongSoKetCuoi =
            this.thongtinKhuVucPT[this.typeTTKV].TONG_KETCUOI_VNPT;
          this.nangLucHaTang.haTangVNPT.dungLuongLapDat =
            this.thongtinKhuVucPT[this.typeTTKV].TONG_DUNGLUONG_VNPT;
          this.nangLucHaTang.haTangVNPT.dungLuongDaDung =
            this.thongtinKhuVucPT[this.typeTTKV].TONG_DUNGLUONG_DADUNG_VNPT;

          this.nangLucHaTang.haTangViettel.tongSoKetCuoi =
            this.thongtinKhuVucPT[this.typeTTKV].TONG_KETCUOI_VIETTEL;
          this.nangLucHaTang.haTangViettel.dungLuongLapDat =
            this.thongtinKhuVucPT[this.typeTTKV].TONG_DUNGLUONG_VIETTEL;
          this.nangLucHaTang.haTangViettel.dungLuongDaDung =
            this.thongtinKhuVucPT.TONG_DUNGLUONG_DADUNG_VIETTEL;

          this.nangLucHaTang.haTangFPT.tongSoKetCuoi =
            this.thongtinKhuVucPT[this.typeTTKV].TONG_KETCUOI_FPT;
          this.nangLucHaTang.haTangFPT.dungLuongLapDat =
            this.thongtinKhuVucPT[this.typeTTKV].TONG_DUNGLUONG_FPT;
          this.nangLucHaTang.haTangFPT.dungLuongDaDung =
            this.thongtinKhuVucPT[this.typeTTKV].TONG_DUNGLUONG_DADUNG_FPT;
            }else{
             this.dungLuongThiTruong={
        tongDanCu: 0,
        thiphanVNPT: 0,
        tongFiber: 0,
        tongMega: 0,
        tongMyTV: 0,
        tongCD: 0,
        thiphanViettel: 0,
        thiphanFPT: 0,
      }
     this. nangLucHaTang= {
        haTangVNPT: {
          tongSoKetCuoi: 0,
          dungLuongLapDat: 0,
          dungLuongDaDung: 0,
        },
        haTangViettel: {
          tongSoKetCuoi: 0,
          dungLuongLapDat: 0,
          dungLuongDaDung: 0,
        },
        haTangFPT: {
          tongSoKetCuoi: 0,
          dungLuongLapDat: 0,
          dungLuongDaDung: 0,
        },
      }
            }
      }catch(r){
       console.log(r)
      }
    },
    xuatFile: function () {
      if (this.dataExcel != null) {
        let data = XLSX.utils.json_to_sheet(this.dataExcel);
        let wb = XLSX.utils.book_new(); // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data, "PHANTICH_MATDO_PHANTU_DANCU"); // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, "phantich.xlsx");
      } else this.$toast.error("Không có dữ liệu!");
    },
    toImage: async function () {
      const el = this.$refs.map;
      html2canvas(el, {
        useCORS: true,
      }).then((canvas) => {
        let imgData = canvas.toDataURL("image/png");
        this.fileDownload(imgData);
      });
    },

    fileDownload(downloadUrl) {
      let aLink = document.createElement("a");
      aLink.style.display = "none";
      aLink.href = downloadUrl;
      aLink.download = "map.png";
      document.body.appendChild(aLink);
      aLink.click();
      document.body.removeChild(aLink);
    },
    getChecked: function (value, event) {
      if (event.target.checked) {
        this.dsChuQuan.push(value);
      } else this.dsChuQuan = this.dsChuQuan.filter((item) => item !== value);
    },
    validateData: function () {
      if (this.dataSelected.kieuBanDo == -1) {
        this.$toast.error("Chưa chọn kiểu bản đồ để phân tích");
        this.$refs.cboKieuBanDo.focus();
        return fasle;
      } else if (this.dataSelected.kieuBanDo == 1) {
        if (this.dsChuQuan.length == 0) {
          this.$toast.error("Chưa chọn nhà cung cấp");
          this.$refs.chuquan.focus();
          return fasle;
        }
      }
      if (this.rec == "") {
        this.$toast.error("Chưa chọn khu vực để phân tích");
        return fasle;
      }
      return true;
    },
    //#endregion

    //region "Hàm bản đồ"
    formatNumber: function (num) {
      return num
        .toFixed(2)
        .toString()
        .replace(".", ",")
        .replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1.");
    },
    anThongTinKV: function (isShow) {
      this.isViewThongTin = isShow;
    },
    luuToaDoBanDo: function (data) {},
    vehinhtron: function () {
      this.titleKV = "Xem thông tin khu vực nhỏ theo bán kính";
      this.typeKVChon = 1;
      let map = this.$refs.mapcontrol;
      map.setTypeDraw(this.typeKVChon);
    },
    vehinhcn: function () {
      this.titleKV = "Xem thông tin khu vực nhỏ theo hình chữ nhật";
      this.typeKVChon = 2;
       let map = this.$refs.mapcontrol;
      map.setTypeDraw(this.typeKVChon);
    },
    vehinhdagiac: function () {
      this.titleKV = "Xem thông tin khu vực nhỏ theo hình tự vẽ";
      this.typeKVChon = 3;
      let map = this.$refs.mapcontrol;
      map.setTypeDraw(this.typeKVChon);
    },
    hienthiThongtin: function () {
      this.titleKV = "Chọn công cụ để xem chi tiết từng khu vực nhỏ";
      this.isViewThongTin = true;
    },
    getThongTinTrungTam: function () {
      this.thongTinKhuVuc.ToaDo =
        this.thongtinbando.C_LATITUS + "," + this.thongtinbando.C_LONGTITUS;
      this.thongTinKhuVuc.Zoom = this.thongtinbando.DEFAULT_ZOOM;
      this.thongTinKhuVuc.TongDienTich = this.formatNumber(
        this.thongtinbando.RADIUS * 3.14
      );
      if (this.thongTinKhuVuc.TongDienTich < 0)
        this.thongTinKhuVuc.donvi = "m2";
      else this.thongTinKhuVuc.donvi = "km2";
    },
    infoMapCurrent: function (obj) {
      if (obj != null) {
        this.thongTinKhuVuc.ToaDo = obj.centerMap;
        this.thongTinKhuVuc.Zoom = obj.zoomLevel;
        this.thongTinKhuVuc.TongDienTich = this.xuLyDienTich(obj.area);
        this.recPolygon = obj.recPoint;
        this.areaMap = Math.round(obj.area);
      }
    },
    xuLyDienTich: function (area) {
      try {
        if (area != null && area != 0) {
          let dt = Math.round(area);
          if (dt / 1000 < 1) {
            this.thongTinKhuVuc.donvi = "m2";
            return this.formatNumber(dt);
          } else {
            this.thongTinKhuVuc.donvi = "km2";
            return this.formatNumber(dt / 1000);
          }
        }
      } catch (e) {
        console.log("Lỗi", e);
      }
    },
    phantich: async function () {
      try {
        if (this.kqTimKiem != null && this.kqTimKiem.length > 0) {
          this.$confirm(
            `Dữ liệu này đã được phân tích, bạn có muốn thực hiện lại?`,
            "Phân tích bản đồ",
            {
              confirmButtonText: "Có",
              cancelButtonText: "Không",
            }
          )
            .then(async () => {
              this.ketquaPhanTich.TongSoKC_HD = "1. Tổng số hộ dân:";
              this.ketquaPhanTich.MatDoTB = "2. Mật độ trung bình:";
              this.stateButton.isDisableLamMoi = true;
              this.stateButton.isDisablePhanTich = true;
              if (this.dataSelected.kieuBanDo == 0)
                await this.phantichBieuDoNhietVNPT();
              else await this.phantichBieuDoHatVNPT();
            })
            .catch((e) => {});
        } else {
          this.ketquaPhanTich.TongSoKC_HD = "";
          this.ketquaPhanTich.MatDoTB = "";
          this.stateButton.isDisableLamMoi = true;
          this.stateButton.isDisablePhanTich = true;
          if (this.dataSelected.kieuBanDo == 0)
            await this.phantichBieuDoNhietVNPT();
          else await this.phantichBieuDoHatVNPT();
        }
      } catch (error) {
       // console.log(error)
        this.$toast.error('Có lỗi xảy ra');
      } finally {
        this.xuLyKetQuaPhanTich(this.kqTimKiem.length);
        this.stateButton.isDisableLamMoi = false;
        this.stateButton.isDisablePhanTich = true;
        this.isShowPhanTich=true
      }
    },
    phantichBieuDoHat: async function () {
      try {
        if (this.validateData()) {
          this.$root.showLoading(true);
          let data = {
            phamvi: this.dataSelected.phamvi, //1,
            doituong: this.dataSelected.loaiBanDo, //1,
            //rec: "20.7589631,106.7488718;20.7520206,106.7263412;20.7512179,106.7139816;20.7529436,106.7039824;20.7590433,106.6998196;20.7632569,106.6949272;20.7660257,106.6915798;20.7656645,106.6850567;20.7657046,106.6760874;20.7678314,106.6750145;20.7729675,106.6714954;20.7827177,106.6674614;20.7830387,106.6617107;20.7817548,106.6592216;20.7921463,106.6540289;20.7964793,106.6476774",
            rec: this.recPolygon,
            chuquan_id: this.dsChuQuan.toString(),
          };
          var rs = await this.$root.context.post(
            "/web-cabman/bando_matdo/bandohat",
            data
          );
          if (rs.data != null) {
            this.kqTimKiem = rs.data;
            this.veBanDoHatVNPT();
            this.dataExcel = Object.values(this.kqTimKiem);
          } else {
            this.$root.toastError(rs.message_detail);
          }
        }
      } catch (error) {
        if(error.response!=null && error.response.status == '400')
        this.$root.toastError(error.response.data.message_detail);
        else
        this.$root.toastError(error);
      } finally {
        this.$root.showLoading(false);
      }
    },
    phantichBieuDoNhiet: async function () {
      try {
        if (this.validateData()) {
          this.$root.showLoading(true);
          let data = {
            phamvi: this.dataSelected.phamvi,
            doituong: this.dataSelected.loaiBanDo,
            //rec: "20.7589631,106.7488718;20.7520206,106.7263412;20.7512179,106.7139816;20.7529436,106.7039824;20.7590433,106.6998196;20.7632569,106.6949272;20.7660257,106.6915798;20.7656645,106.6850567;20.7657046,106.6760874;20.7678314,106.6750145;20.7729675,106.6714954;20.7827177,106.6674614;20.7830387,106.6617107;20.7817548,106.6592216;20.7921463,106.6540289;20.7964793,106.6476774",
             rec:this.recPolygon,
            dulieu: 1,
          };
          var rs = await this.$root.context.post(
            "/web-cabman/bando_matdo/bandonhiet",
            data
          );
          if (rs.data != null) {
            this.kqTimKiem = rs.data;
            this.veBanDoNhiet();
            this.dataExcel = Object.values(this.kqTimKiem);
          } else {
            this.$root.toastError(rs.message_detail);
          }
        }
      } catch (error) {
              //  this.$root.toastError(error.response.data.message_detail);
      } finally {
        this.$root.showLoading(false);
      }
    },
    xuLyKetQuaPhanTich: function (count) {
      let tb = count / this.areaMap;
      if (tb < 1) tb = "~1";
      else tb = this.formatNumber(tb);
      if (this.dataSelected.loaiBanDo == 0) {
        this.ketquaPhanTich.TongSoKC_HD =
          "Tổng số hộ dân: " + this.formatNumber(count);
        this.ketquaPhanTich.MatDoTB = "Mật độ trung bình: " + tb + " hộ/Km2";
      } else {
        this.ketquaPhanTich.TongSoKC_HD =
          "Tổng số kết cuối: " + this.formatNumber(count);
        this.ketquaPhanTich.MatDoTB = "Mật độ trung bình: " + tb + " KC/Km2";
      }
    },
    veBanDoHat: function () {
      try {
        let map = this.$refs.mapcontrol;
        map.clear();
        map.setNullHeatmap();
        let nodes = [];
        let size = this.dataSelected.kichthuocHat;
        if (this.kqTimKiem != null && this.kqTimKiem.length > 0) {
          for (let i = 0; i < this.kqTimKiem.length; i++) {
            let kc = this.kqTimKiem[i];
            let color = "";
            let toado = kc.VIDO + "," + kc.KINHDO;
            if (kc.CHUQUAN_ID != null) color = colorBar[kc.CHUQUAN_ID - 1];
            let node = this.taoDuLieuNode("KC_" + i, toado, 100, color, size);
            nodes.push(node);
          }
          this.nodes = nodes;
        }
      } catch (e) {
        console.log("Lỗi: ", e);
      }
    },
    veBanDoNhiet: function () {
      try {
        let map = this.$refs.mapcontrol;
        map.clear();
        map.setNullHeatmap();
        let points = [];
        if (this.kqTimKiem != null && this.kqTimKiem.length > 0) {
          for (let i = 0; i < this.kqTimKiem.length; i++) {
            let kc = this.kqTimKiem[i];
            let point = kc.VIDO + "," + kc.KINHDO;
            points.push(point);
          }
          this.dataPoints = points;
        }
      } catch (e) {
        console.log("Lỗi: ", e);
      }
    },
    taoDuLieuNode: function (id, toado, icon, color, size) {
      let node = new GraphNode();
      try {
        if (toado != null) {
          node.id = id;
          node.setPosition(toado);
          node.setIcon(icon, color, size);
          node.draggable = false;
        }
        return node;
      } catch (e) {
        return node;
      }
    },
    dokhoangcach: function(val){
      this.isDoKC =val
    },
    getRecKVCon:function(rec){
     this.recKVCon =rec
     this.showThongtin()
    },
    xemDataKVTron:function(){
      this.showThongTinChitiet(1)
    },
    xemDataKVHcn:function(){
      this.showThongTinChitiet(2)
    }
    ,
    xemDataKVPolygon:function(){
      this.showThongTinChitiet(3)
    },
    printVNPTMap() {
      this.$toast.error("Bản đồ VNPT chưa hỗ trợ tải file ảnh");
    },
    zoomIn () {
      this.$refs.mapcontrol.zoomIn()
    },
    zoomOut () {
      this.$refs.mapcontrol.zoomOut()
    },
    phantichBieuDoNhietVNPT() {
      this.$toast.error("Bản đồ VNPT chưa hỗ trợ bản đồ nhiệt");
    },
    phantichBieuDoHatVNPT() {
      this.$toast.error("Bản đồ VNPT chưa hỗ trợ bản đồ hạt");
    },
    //#endregion
  },
};
</script>
<style>

.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}

</style>