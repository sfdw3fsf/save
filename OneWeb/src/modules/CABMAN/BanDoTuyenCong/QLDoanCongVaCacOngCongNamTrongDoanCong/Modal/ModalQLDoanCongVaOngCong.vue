<template src="./ModalQLDoanCongVaOngCong.html"></template>
<script>
import Vue from "vue";
import moment from "moment";
import ModalChuiCap from "../../ChuiCapQuaOngCong/Modal";
import Panzoom from "@panzoom/panzoom";
import ModalCsht from "../../Csht/Modal";
import ModalNVQLDoanCong from '../../../gan-nhanvien-quanly-cap/Modal'

export default {
  name: "ModalQLDoanCongVaOngCong",
  props: ["modalId", "data"],
  components: { ModalCsht, ModalChuiCap, moment, Panzoom, ModalNVQLDoanCong },
  data: function () {
    return {
      dsTuyenCongBe: [],
      componentName: null,
      fields: { text: "TEN_DV", value: "donViId" },
      // cotid: 0,
      dsChuQuan: [],
      dsLoaiCong: [],
      dsTaiSan: [],
      dsDuAn: [],
      dsTTSD: [],
      dsHuongBeA: [],
      dsHuongBeB: [],
      dataSend: {},
      dsOngCong: [],
      nodesGianDo: [],
      connectorsGianDo: [],
      dataSelected: {
        doanCongId: null,
        loaiCongId: 1,
        chuQuanId: 1,
        taiSanId: null,
        duAnId: null,
        trangThaiSuDungId: 1,
        maDoanCong: "",
        tenDoanCong: "",
        kyHieu: "",
        soDongMatCat: 1,
        soCotMatCat: 1,
        soOngCong: 1,
        matCat: "1x2",
        duongKinhOngCong: 1,
        chieuDai: 0,
        doSau: 0,
        soCapDiQua: 0,
        beCapA: "",
        beCapAId: null,
        beCapB: "",
        beCapBId: null,
        huongBeA: null,
        huongBeB: null,
        ngayLapDat: Date,
        ngaySuDung: Date,
        tuyenCongBeId: null,
        maTuyenCongBe: null,
        donViQlId: null,
        maCSHT: null,
        ghiChu: "",
        TEN_NVQL: "",
      },
      category: {
        phanloai: 0,
        dinhdanh: 0,
        dactinh: 0,
        boTri: 0,
        ketNoiA: 0,
        ketNoiB: 0,
        lienket: 0,
        kiemke: 0,
        chuquan: 0,
        khac: 0,
        ngaythang: 0,
        macsht: 0,
      },
      categoryOngCong: {
        dinhDanh: 0,
        dacTinh: 0,
        viTri: 0,
      },
      dsTrangThaiOngCong: null,
      dsLoaiOngCong: null,
      stateBtn: { isDisableBtnCong: true },
      ongCongSelected: {
        doanCongId: null,
        ongCongId: null,
        soThuTu: null,
        soCap: null,
        kyHieu: null,
        duongKinhOngCong: null,
        soDong: null,
        soCot: null,
        doDayOngCong: null,
        loaiOngCong: null,
        trangThaiOngCong: null,
        x: null,
        y: null,
      },
      kyHieuDong: [
        { soDong: 1, kyHieu: "A" },
        { soDong: 2, kyHieu: "B" },
        { soDong: 3, kyHieu: "C" },
        { soDong: 4, kyHieu: "D" },
        { soDong: 5, kyHieu: "E" },
        { soDong: 6, kyHieu: "F" },
        { soDong: 7, kyHieu: "G" },
        { soDong: 8, kyHieu: "H" },
        { soDong: 9, kyHieu: "I" },
        { soDong: 10, kyHieu: "J" },
      ],
      showOngCong: false,
      titleTTOngCong:
        "Click vào 1 ống cống phía trên để xem và cập nhật thông tin",
      idCong: null,
      idIconCong: null,
      picked: "1",
      waterMark: "Chọn ngày",
      dateVal: new Date(),
      dateFormat: "dd/MM/yyyy",
      donviql: [],
      idPopupNVQLDoanCong: 'idPopupNVQLDoanCong',
      doiTuongObj: {
        LOAI_DT: 'DOANCONG',
        DOITUONG_ID: -1,
      },
    };
  },
  created: async function () {},
  watch: {
    picked: function () {
      this.dataSend.picked = this.picked;
    },
    async "dataSelected.duAnId"(val) {
      let rs = await this.$root.context.get(
        "/web-cabman/ban-do-mang-cap/danh-muc",
        { loai: "TAISAN", option: val === null ? "" : val }
      );
      console.log("🚀 ~ file: ModalQLDoanCongVaOngCong.vue:125 ~ rs:", rs);
      if (rs.data != null) {
        this.dsTaiSan = rs.data;
      } else {
        this.dsTaiSan = [];
      }
    },
  },
  computed: {
    // Tạo chuỗi mặt cắt
    matCatValue() {
    // Nối hai giá trị bằng ký tự 'x'
      return this.dataSelected.soDongMatCat + 'x' + this.dataSelected.soCotMatCat;
    }
  },
  methods: {
    modalNVQLDoanCongShow(){

    },
    closeModalNVQLDoanCong() {
      this.chiTietDoanCong()
    },
    showModalNVQLDoanCong() {
      this.$bvModal.show(this.idPopupNVQLDoanCong)
    },
    resetModal() {
      this.dsDuAn = [];
      this.dsTaiSan = [];
      this.dsTuyenCongBe = [];
      this.dsChuQuan = [];
      this.dsLoaiCong = [];
      this.dsTTSD = [];
      this.dsHuongBeA = [];
      this.dsHuongBeB = [];
      this.dsOngCong = [];
      this.dataSelected = {
        doanCongId: null,
        loaiCongId: 1,
        chuQuanId: 1,
        taiSanId: null,
        duAnId: null,
        trangThaiSuDungId: 1,
        maDoanCong: "",
        tenDoanCong: "",
        kyHieu: "",
        soDongMatCat: 1,
        soCotMatCat: 1,
        soOngCong: 1,
        matCat: "1x2",
        duongKinhOngCong: 1,
        chieuDai: 0,
        doSau: 0,
        soCapDiQua: 0,
        beCapA: "",
        beCapAId: null,
        beCapB: "",
        beCapBId: null,
        huongBeA: null,
        huongBeB: null,
        ngayLapDat: Date,
        ngaySuDung: Date,
        tuyenCongBeId: null,
        maTuyenCongBe: null,
        donViQlId: null,
        maCSHT: null,
        ghiChu: "",
      };
    },
    focusMyElement: async function () {
      this.showOngCong = false;
      try {
        this.$root.showLoading(true);
        await this.initForm();
        if (this.data != null) {
          this.dataSelected.doanCongId = this.data.doanCongId;
          this.dataSend.doanCongId = this.data.doanCongId;
          this.dataSelected.tuyenCongBeId = this.data.tuyenCongId;
          this.donviql = this.data.dvql;
          this.dataSelected.donViQlId = this.donviql.id;
          if (this.dataSelected.doanCongId != null) {
            await this.chiTietDoanCong();
            await this.getDsOngCong();
          }
        }
      } catch (er) {
        console.log("🚀 ~ file: ModalQLDoanCongVaOngCong.vue:152 ~ er:", er);
      } finally {
        this.$root.showLoading(false);
      }
    },

    getDsOngCong: async function () {
      try {
        let rs = await this.$root.context.get(
          "/web-cabman/BanDoTuyenCong/danh-sach-ong-cong?doanCongId=" +
            this.dataSelected.doanCongId
        );
        console.log("ongCOng", rs.data);
        if (rs.data != null) {
          this.dsOngCong = rs.data;
          // Gán giá trị length vào dataSelected.soOngCong để tính số ống cống
          this.dataSelected.soOngCong = rs.data.length;
        }
      } catch (error) {
      } finally {
        this.veGianDoOngCong();
      }
    },

    veGianDoOngCong: function () {
      try {
        this.dataSend.picked = this.picked;
        this.showOngCong = false;
        let codetr = "";
        let codetd = "";
        for (let i = 0; i < this.dataSelected.soDongMatCat; i++) {
          let idDong = "tr-giando-" + i;
          for (let j = 0; j < this.dataSelected.soCotMatCat; j++) {
            let ongCong =
              this.dsOngCong != null
                ? this.dsOngCong.find(
                    (item) =>
                      item.THUTU == j + i * this.dataSelected.soCotMatCat + 1
                  )
                : null;
            console.log("item", ongCong);
            let icon = "one-tc-ctoc"; // icon chua tao
            let id = "";
            let thuTu = "";
            let soCap = "";
            let kyHieu = "";
            let checkIcon = false;
            let srcIcon = "";
            if (ongCong != null) {
              id = ongCong.ONGCONG_ID;
              thuTu = ongCong.THUTU;
              soCap = ongCong.SOCAP;
              kyHieu = ongCong.KYHIEU;
              switch (ongCong.TTOC_ID) {
                case 1:
                  checkIcon = false;
                  icon = "one-tc-icon4"; // dang dung
                  break;
                case 2:
                  checkIcon = false;
                  icon = "one-tc-icon6"; // tac
                  break;
                case 3:
                  checkIcon = false;
                  icon = "one-tc-icon5"; // hong
                  break;
                case 4:
                  checkIcon = false;
                  icon = "one-tc-icon3"; // chua dung
                  break;
                case 5:
                  checkIcon = true;
                  srcIcon =
                    "../../../../../../static/icons/icon100x100/CONG_HONG_CHO_SUA.png"; // Cong hong cho sua chua
                  break;
                case 6:
                  checkIcon = true;
                  srcIcon =
                    "../../../../../../static/icons/icon100x100/CONG_HONG_DA_SUA.png"; // Cong hong da sua chua
                  break;
              }
            }
            codetd =
              codetd +
              "<td>\n" +
              '                                            <div class="post-info outer" @click="onClickSoDoMatCat" id="' +
              id +
              "-" +
              j +
              "-" +
              i +
              '">\n' +
              '                                              <span class="charity">\n' +
              '                                                <p v-show="' +
              !checkIcon +
              '" class="' +
              icon +
              ' inner-icon" id="clickIcon-' +
              id +
              "-" +
              j +
              "-" +
              i +
              "\" :class=\"{'clickBieuDo': idIconCong=='" +
              id +
              "-" +
              j +
              "-" +
              i +
              "'}\"></p>\n" +
              '                                                <img v-show="' +
              checkIcon +
              '" src="' +
              srcIcon +
              '"></img>' +
              '                                                <span class="inner-num" v-show="showBieuDo.soThuTu">' +
              thuTu +
              "</span>\n" +
              '                                                <span class="inner-num" v-show="showBieuDo.soCap">' +
              soCap +
              "</span>\n" +
              '                                                <span class="inner-num" v-show="showBieuDo.kyHieu">' +
              kyHieu +
              "</span>\n" +
              "                                              </span>\n" +
              "                                            </div>\n" +
              "                                          </td>";
          }
          codetr = "<tr id='" + idDong + "'>" + codetd + "</tr>" + codetr;
          codetd = "";
        }
        let codetable = "<table id='insert-giando'>" + codetr + "</table>";

        let comp = Vue.component("comp", {
          template: codetable,
          props: {
            picked: null,
          },
          data: function () {
            return {
              idIconCong: null,
              showBieuDo: {
                soThuTu: true,
                soCap: false,
                kyHieu: false,
              },
            };
          },
          mounted: function () {},
          watch: {
            picked: function (val) {
              switch (val) {
                case "1":
                  this.showBieuDo.soThuTu = true;
                  this.showBieuDo.soCap = false;
                  this.showBieuDo.kyHieu = false;
                  break;
                case "2":
                  this.showBieuDo.soThuTu = false;
                  this.showBieuDo.soCap = true;
                  this.showBieuDo.kyHieu = false;
                  break;
                case "3":
                  this.showBieuDo.soThuTu = false;
                  this.showBieuDo.soCap = false;
                  this.showBieuDo.kyHieu = true;
                  break;
              }
            },
          },
          methods: {
            onClickSoDoMatCat: function (e) {
              let obj = {
                idOngCong: e.currentTarget.id.split("-")[0],
                x: Number(e.currentTarget.id.split("-")[1]),
                y: Number(e.currentTarget.id.split("-")[2]),
              };
              this.idIconCong = e.currentTarget.id;
              this.$emit("clickOngCong", obj);
            },
          },
        });
        this.componentName = comp;
        this.panzoom = Panzoom(document.getElementById("giando"), {
          maxScale: 5,
        });
      } catch (e) {
        console.log(e);
      }
    },

    onClickZoomIn: async function () {
      this.onClickZoom(1);
    },
    onClickZoomOut: async function () {
      this.onClickZoom(2);
    },
    onClickZoom(loai) {
      switch (loai) {
        case 1:
          this.panzoom.zoomIn();
          break;
        case 2:
          this.panzoom.zoomOut();
          break;
        case 3:
          this.panzoom.reset();
          break;
      }
    },

    chiTietOngCong: async function () {
      if (this.ongCongSelected.ongCongId) {
        console.log(
          "this.ongCongSelected.ongCongId",
          this.ongCongSelected.ongCongId
        );
        this.titleTTOngCong = "Thông tin ống cống đang chọn";
        this.stateBtn.isDisableBtnCong = false;
        this.dataSend.ongCongId = this.ongCongSelected.ongCongId;
        let ongCong = this.dsOngCong.find(
          (item) => item.ONGCONG_ID == this.ongCongSelected.ongCongId
        );
        this.ongCongSelected.soThuTu = ongCong.THUTU;
        this.ongCongSelected.soCap = ongCong.SOCAP;
        this.ongCongSelected.kyHieu = ongCong.KYHIEU;
        this.ongCongSelected.duongKinhOngCong = ongCong.DUONGKINH_OC;
        this.ongCongSelected.soDong = ongCong.DONG_MC;
        this.ongCongSelected.soCot = ongCong.COT_MC;
        this.ongCongSelected.doDayOngCong = ongCong.DODAY_OC;
        this.ongCongSelected.loaiOngCong = ongCong.LOAIOC_ID;
        this.ongCongSelected.trangThaiOngCong = ongCong.TTOC_ID;
      } else {
        this.titleTTOngCong = "Tạo mới ống cống tại vị trí lỗ PI đang chọn";
        this.stateBtn.isDisableBtnCong = true;
        this.ongCongSelected.ongCongId = null;
        this.ongCongSelected.soThuTu =
          this.ongCongSelected.x +
          this.ongCongSelected.y * this.dataSelected.soCotMatCat +
          1;
        this.ongCongSelected.soCap = 0;
        this.ongCongSelected.kyHieu =
          this.kyHieuDong.find(
            (item) => item.soDong == this.ongCongSelected.y + 1
          ).kyHieu +
          (this.ongCongSelected.x + 1);
        this.ongCongSelected.duongKinhOngCong =
          this.dataSelected.duongKinhOngCong;
        this.ongCongSelected.soDong = this.ongCongSelected.y + 1;
        this.ongCongSelected.soCot = this.ongCongSelected.x + 1;
        this.ongCongSelected.doDayOngCong = null;
        this.ongCongSelected.loaiOngCong = null;
        this.ongCongSelected.trangThaiOngCong = null;
      }
    },

    clickOngCong: async function (obj) {
      console.log("clickOngCong", obj);
      this.showOngCong = true;
      this.ongCongSelected.ongCongId = obj.idOngCong;
      this.ongCongSelected.x = obj.x;
      this.ongCongSelected.y = obj.y;
      await this.chiTietOngCong();
    },

    chiTietDoanCong: async function () {
      try {
        let rs = await this.$root.context.get(
          "/web-cabman/BanDoTuyenCong/chi-tiet-doan-cong?doanCongId=" +
            this.dataSelected.doanCongId
        );
        if (rs.data != null) {
          await this.getHuongBe(rs.data.BECAP_A_ID, 1);
          await this.getHuongBe(rs.data.BECAP_B_ID, 2);
          await this.mapDataDoanCong(rs.data);
          if (this.dataSelected.tuyenCongBeId) {
            let kq = await this.getMaTuyenCongBe(
              this.dataSelected.tuyenCongBeId
            );
            if (kq.data) {
              this.dataSelected.maTuyenCongBe = kq.data.MA_TCB;
            }
          }
        }
      } catch (error) {
        console.log(
          "🚀 ~ file: ModalQLDoanCongVaOngCong.vue:364 ~ error:",
          error
        );
      } finally {
      }
    },
    getMaTuyenCongBe: async function (tuyenCongBeId) {
      let ma = await this.$root.context.get(
        "/web-cabman/BanDoTuyenCong/getMaTuyenCongBe",
        { tuyenCongBeId: tuyenCongBeId }
      );
      return ma;
    },
    getHuongBe: async function (beCapId, loai) {
      try {
        let rs = await this.$root.context.get(
          "/web-cabman/BanDoTuyenCong/danh-sach-huong-be?beCapId=" + beCapId
        );
        if (rs.data != null) {
          switch (loai) {
            case 1:
              this.dsHuongBeA = rs.data;
              break;
            case 2:
              this.dsHuongBeB = rs.data;
              break;
          }
        }
      } catch (error) {
        console.log(
          "🚀 ~ file: ModalQLDoanCongVaOngCong.vue:388 ~ error:",
          error
        );
      } finally {
      }
    },

    initForm: async function () {
      try {
        let rs = await this.$root.context.get(
          "/web-cabman/BanDoTuyenCong/lay-cac-danh-muc-doan-cong"
        );
        console.log("🚀 ~ file: ModalQLDoanCongVaOngCong.vue:399 ~ rs:", rs);
        if (rs.data != null) {
          this.dsChuQuan = rs.data.dsChuQuan;
          this.dsLoaiCong = rs.data.dsLoaiCong;
          this.dsTTSD = rs.data.dsTTSD;
          this.dsDuAn = rs.data.dsDuAn;
          console.log(
            "🚀 ~ file: ModalQLDoanCongVaOngCong.vue:405 ~ this.dsDuAn:",
            this.dsDuAn
          );
        }
        rs = await this.$root.context.get(
          "/web-cabman/ban-do-mang-cap/danh-muc",
          { loai: "TINHTRANG_ONGCONG", option: "" }
        );
        if (rs.data != null) {
          this.dsTrangThaiOngCong = rs.data;
        }
        console.log(this.dsTrangThaiOngCong);

        rs = await this.$root.context.get(
          "/web-cabman/ban-do-mang-cap/danh-muc",
          { loai: "LOAI_ONGCONG", option: "" }
        );
        if (rs.data != null) {
          this.dsLoaiOngCong = rs.data;
        }
        console.log(this.dsLoaiOngCong);
      } catch (error) {
        console.log(
          "🚀 ~ file: ModalQLDoanCongVaOngCong.vue:423 ~ error:",
          error
        );
      } finally {
      }
    },
    mapDataDoanCong: async function (data) {
      if (data != null) {
        this.doiTuongObj.DOITUONG_ID = data.DOANCONG_ID;
        this.dataSelected.TEN_NVQL = data.DS_NHANVIEN_QL;
        this.dataSelected.loaiCongId = data.LOAICONG_ID;
        this.dataSelected.chuQuanId = data.CHUQUAN_ID;

        this.dataSelected.duAnId = data.DUAN_ID;

        let rs = await this.$root.context.get(
          "/web-cabman/ban-do-mang-cap/danh-muc",
          { loai: "TAISAN", option: data.DUAN_ID === null ? "" : data.DUAN_ID }
        );
        if (rs.data != null) {
          this.dsTaiSan = rs.data;
        } else {
          this.dsTaiSan = [];
        }

        this.dataSelected.taiSanId = data.TAISAN_ID;
        this.dataSelected.trangThaiSuDungId = data.TTSD_ID;
        this.dataSelected.maDoanCong = data.MA_DC;
        this.dataSelected.tenDoanCong = data.TEN_DC;
        this.dataSelected.kyHieu = data.KYHIEU;
        this.dataSelected.soDongMatCat = data.SODONG_MC;
        this.dataSelected.soCotMatCat = data.SOCOT_MC;
        this.dataSelected.soOngCong = data.SOONGCONG;
        this.dataSelected.matCat = data.MATCAT;
        this.dataSelected.duongKinhOngCong = data.DUONGKINH_OC;
        this.dataSelected.chieuDai = data.CHIEUDAI;
        this.dataSelected.doSau = data.DOSAU;
        this.dataSelected.soCapDiQua = data.SOCAP;
        this.dataSelected.beCapA = data.BECAP_A;
        this.dataSelected.beCapB = data.BECAP_B;
        this.dataSelected.beCapAId = data.BECAP_A_ID;
        this.dataSelected.beCapBId = data.BECAP_B_ID;
        this.dataSelected.huongBeA = data.HUONGBE_A;
        this.dataSelected.huongBeB = data.HUONGBE_B;
        this.dataSelected.ngayLapDat = data.NGAY_LD
          ? new Date(data.NGAY_LD)
          : null;
        this.dataSelected.ngaySuDung = data.NGAY_SD
          ? new Date(data.NGAY_SD)
          : null;
        this.dataSelected.maCSHT = data.MA_CSHT;
        this.dataSelected.ghiChu = data.GHICHU;
      }
    },
    ghiLai: async function () {
      if (this.dataSelected.doanCongId != null) {
        await this.capNhat();
        this.ongCongSelected.doanCongId = this.dataSelected.doanCongId;
        if (this.showOngCong == true) {
          if (this.ongCongSelected.ongCongId) {
            await this.capNhatOngCong();
          } else {
            await this.themMoiOngCong();
          }
        }
        this.getDsOngCong();
      }
    },
    capNhat: async function () {
      try {
        this.$root.showLoading(true);
        console.log(this.dataSelected);
        // this.dataSelected.ngayLapDat= this.convertDate(this.dataSelected.ngayLapDat);
        // this.dataSelected.ngaySuDung= this.convertDate(this.dataSelected.ngaySuDung);

        var rs = await this.$root.context.post(
          "/web-cabman/BanDoTuyenCong/cap-nhat-doan-cong",
          this.dataSelected
        );
        if (rs.error_code == "BSS-00000000") {
          this.$toast.success("Cập nhật đoạn cống thành công.");
        } else {
          this.$toast.error("Cập nhật đoạn cống thất bại.");
        }
      } catch (error) {
        this.$toast.error(error.response.data.message_detail);
      } finally {
        this.$root.showLoading(false);
      }
    },

    themMoiOngCong: async function () {
      try {
        this.$root.showLoading(true);
        console.log(this.ongCongSelected);
        // this.dataSelected.ngayLapDat= this.convertDate(this.dataSelected.ngayLapDat);
        // this.dataSelected.ngaySuDung= this.convertDate(this.dataSelected.ngaySuDung);

        var rs = await this.$root.context.post(
          "/web-cabman/BanDoTuyenCong/them-ong-cong",
          this.ongCongSelected
        );
        if (rs.error_code == "BSS-00000000") {
          this.$toast.success("Thêm mới ống cống thành công.");
        } else {
          this.$toast.error("Thêm mới ống cống thất bại.");
        }
      } catch (error) {
        this.$toast.error(error.response.data.message_detail);
      } finally {
        this.$root.showLoading(false);
      }
    },

    capNhatOngCong: async function () {
      try {
        this.$root.showLoading(true);
        console.log(this.ongCongSelected);
        // this.dataSelected.ngayLapDat= this.convertDate(this.dataSelected.ngayLapDat);
        // this.dataSelected.ngaySuDung= this.convertDate(this.dataSelected.ngaySuDung);

        var rs = await this.$root.context.post(
          "/web-cabman/BanDoTuyenCong/cap-nhat-ong-cong",
          this.ongCongSelected
        );
        if (rs.error_code == "BSS-00000000") {
          this.$toast.success("Cập nhật ống cống thành công.");
        } else {
          this.$toast.error("Cập nhật ống cống thất bại.");
        }
      } catch (error) {
        this.$toast.error(error.response.data.message_detail);
      } finally {
        this.$root.showLoading(false);
      }
    },

    xoaTatCaOngCong: async function () {
      try {
        this.$root.showLoading(true);
        // this.dataSelected.ngayLapDat= this.convertDate(this.dataSelected.ngayLapDat);
        // this.dataSelected.ngaySuDung= this.convertDate(this.dataSelected.ngaySuDung);

        let rs = await this.$root.context.post(
          "/web-cabman/BanDoTuyenCong/xoa-tat-ca-ong-cong",
          {
            doanCongId: this.dataSelected.doanCongId,
          }
        );
        if (rs.error_code == "BSS-00000000") {
          this.getDsOngCong();
          this.$toast.success("Xóa tất cả ống cống thành công.");
        } else {
          this.$toast.error("Xóa tất cả ống cống thất bại.");
        }
      } catch (error) {
        this.$toast.error("Xóa tất cả ống cống thất bại.");
      } finally {
        this.$root.showLoading(false);
      }
    },

    xoaOngCong: async function () {
      try {
        this.$root.showLoading(true);
        // this.dataSelected.ngayLapDat= this.convertDate(this.dataSelected.ngayLapDat);
        // this.dataSelected.ngaySuDung= this.convertDate(this.dataSelected.ngaySuDung);

        let rs = await this.$root.context.post(
          "/web-cabman/BanDoTuyenCong/xoa-ong-cong",
          {
            ongCongId: this.ongCongSelected.ongCongId,
          }
        );
        if (rs.error_code == "BSS-00000000") {
          this.getDsOngCong();
          this.$toast.success("Xóa ống cống thành công.");
        } else {
          this.$toast.error("Xóa ống cống thất bại.");
        }
      } catch (error) {
        this.$toast.error("Xóa ống cống thất bại.");
      } finally {
        this.$root.showLoading(false);
      }
    },

    tuDongSinhOngCong: async function () {
      try {
        this.$root.showLoading(true);
        // this.dataSelected.ngayLapDat= this.convertDate(this.dataSelected.ngayLapDat);
        // this.dataSelected.ngaySuDung= this.convertDate(this.dataSelected.ngaySuDung);

        let rs = await this.$root.context.post(
          "/web-cabman/BanDoTuyenCong/tu-dong-sinh-ong-cong",
          {
            doanCongId: this.dataSelected.doanCongId,
          }
        );
        if (rs.error_code == "BSS-00000000") {
          this.getDsOngCong();
          this.$toast.success("Tạo ống cống tự động thành công");
        } else {
          this.$toast.error("Tạo ống cống tự động thất bại");
        }
      } catch (error) {
        this.$toast.error("Tạo ống cống tự động thất bại");
      } finally {
        this.$root.showLoading(false);
      }
    },

    getdiaChi: function (diaChi) {
      // Chờ lấy địa chỉ
      // console.log(diaChi);
    },
    convertDate: function (date) {
      return (
        new Date(date).toLocaleDateString("en-US", { day: "numeric" }) +
        "/" +
        new Date(date).toLocaleDateString("en-US", { month: "numeric" }) +
        "/" +
        new Date(date).toLocaleDateString("en-US", { year: "numeric" })
      );
    },
    xoaMaTS: function () {
      this.dataSelected.taiSanId = "";
    },
    xoaMaDA: function () {
      this.dataSelected.duAnId = "";
    },
    xoaMaCSHT: function () {
      this.dataSelected.maCSHT = "";
    },
    getMaCsht: function (args) {
      this.dataSelected.maCSHT = args;
    },
    chuiCap: function () {
      this.$bvModal.show("popupChuiCap");
    },
  },
  mounted: function () {
    // document.addEventListener('click',function(event){
    //     console.log('target',event.target)
    //     if (event.target && event.target.id.split("-")[0] == 'clickIcon') {
    //         console.log('event',event)
    //         this.$toast.success("test click so do "+event.target.id)
    //     }
    // })
  },
};
</script>
<style>
div#popupQLBe___BV_modal_outer_ {
  z-index: 10 !important;
  margin-top: 100px;
}
.dashboard-header {
  z-index: 9 !important;
}
a.disabled {
  pointer-events: none;
  cursor: default;
}
p.clickBieuDo {
  color: red !important;
}

.modal-fullscreen .modal-dialog {
  max-width: 100%;
  margin: 0;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  height: 100vh;
  display: flex;
  position: fixed;
  z-index: 100000;
}

.gianDoCong {
  margin: 0;
  position: absolute;
  top: 50%;
  left: 50%;
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
}

.post-info {
  width: 45px;
  height: 45px;
}
.outer {
  position: relative;
}
.charity > .inner-icon {
  position: absolute;
  font-size: 50px;
  color: #0176ff;
}
.charity > .inner-num {
  position: absolute;
  left: 12px;
  top: 12px;
  width: 20px;
  font-size: 17px;
  text-align: center;
}
</style>
