<template src="./SyncChangeEPGPackage.html"></template>
<style scoped src="./SyncChangeEPGPackage.scss"></style>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import breadcrumb from "@/components/breadcrumb";
import API from "./API";
import moment from "moment";
import popupTraCuuAccount from "./popupTraCuuAccount.vue";
export default {
  components: { breadcrumb, moment, popupTraCuuAccount },
  data: function () {
    return {
      header: {
        title: "",
      },
      list: [
        { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
        {
          name: "Lắp đặt mới",
          link: { name: "Ui.buttons" },
        },
      ],
      checkdata: [],
      tcdanhba: [],
      dsepg: [],
      objepgfacade: [],
      dstbepg: [],
      vdongbo: 1,
      dtpNgayKT: new Date(),
      p_today: new Date(),
      txtMaTB: "",
      txtTenTB: "",
      txtDiaChiTB: "",
      chkAll: false,
      rdoDongBo: 0,
      dgvDanhSach: [],
      lblTongsoTB: 0,
      tsbtnTaoDS: true,
      tsbtnDongBo: true,
      pageinfoDSmaxSize: 10,
      DichVuVienThong: {
        ADSL: 4,
        IMS: 11,
        BRCD_DOITAC: 22,
        IMS_DOITAC: 21,
        MEGAWAN: 8,
        METRONET: 7,
        MGW_DOITAC: 23,
        TSL: 9,
        TSL_DOITAC: 24,
      },
      LoaiHopDong: {
        DAT_MOI: 1,
        DI_CHUYEN: 3,
        CHUYENDOI_LH: 6,
        KHOIPHUC_SD: 30,
        THAY_DOI_TOCDO_ADSL: 8,
        THAYDOI_THONGSO_MEGAWAN: 5,
      },
      getHeaderTemplatebtnF3(parent) {
        return function () {
          return {
            template: {
              template: `
                         <button v-on:click="parent.btnTK_CT_Click">F3</button>
                        `,
              data() {
                return {
                  parent: parent,
                };
              },
            },
          };
        };
      },
    };
  },
  created() {},
  methods: {
    tsbtnTaoDS_Click() {
      this.TaoDS(1);
    },
    async TaoDS(vkieu) {
      /* let tt_nd = this.$root.token.getThongTinNguoiDung()
      console.log("ttnd: "+JSON.stringify(tt_nd)); */
      let ngay_epg;
      this.loading(true);
      await API.thongtin_nguoidung2(this.axios, {})
        .then((response) => {
          if (response && response.data && response.data.data) {
            ngay_epg = response.data.data.ngay_epg;
          } else {
            this.$toast.error("Có lỗi khi lấy thông tin người dùng");
            return false;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});

      let ds = [];
      await API.getPackageEPG(this.axios, {
        endtime: moment(this.dtpNgayKT, "DD/MM/YYYY").format("DD/MM/YYYY"),
        iptvaccount: this.txtMaTB.trim(),
        starttime: ngay_epg,
        strAll: vkieu,
      })
        .then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.errorCode === 0
          ) {
            if (response.data.data.result != 0) {
              this.$toast.error("" + response.data.data.errordesc);
              return false;
            }
            ds = response.data.data.getData._any[1];
            let convert = require("xml-js");
            let result = convert.xml2json(ds, { compact: false, spaces: 1 });
            let rs = JSON.parse(result);
            let rs2 = rs.elements[0].elements[0].elements;
            this.dsepg = [];
            let rowepg = {};
            if (rs2.length > 0) {
              for (let i = 0; i < rs2.length; i++) {
                rowepg.account = rs2[i].elements[0].elements[0]["text"];
                rowepg.ngay_th = rs2[i].elements[1].elements[0]["text"];
                rowepg.goi_cu = rs2[i].elements[2].elements[0]["text"];
                rowepg.goi_moi = rs2[i].elements[3].elements[0]["text"];
                /*  let key= rs2[i].elements[3]["name"];
                let val= rs2[i].elements[3].elements[0]["text"];
                console.log(key+" - "+val) */
                this.dsepg.push(rowepg);
              }
              this.HienThiDS(this.vdongbo);
              //console.log("dsepg: " + JSON.stringify(this.dsepg));
            } else {
              this.$toast.error("Không có thuê bao đồng bộ EPG !");
            }
          }
        })
        .catch((error) => {
          this.$toast.error("Đã xảy ra lỗi: " + JSON.stringify(error));
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    async HienThiDS(vdongbo) {
      let dstbepg = [];
      this.loading(true);
      await API.lay_ds_thuebao_dongbo_epg(this.axios, { kieu: vdongbo })
        .then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            response.data.data.forEach(function (item) {
              dstbepg.push({
                chon: 0,
                account: item.account,
                ten_tb: item.ten_tb,
                diachi_ld: item.diachi_ld,
                ngay_th: moment(item.ngay_th).format("DD/MM/YYYY HH:mm:ss"),
                goi_cu: item.goi_cu,
                goi_moi: item.goi_moi,
                mtd_vtt: item.mtd_vtt,
              });
            });
            if (dstbepg.length > 0) {
              this.dgvDanhSach = dstbepg;
              this.lblTongsoTB = this.dgvDanhSach.length;
            } else {
              this.dgvDanhSach = [];
              this.txtMaTB = "";
              this.txtDiaChiTB = "";
              this.txtTenTB = "";
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    TaoDuLieu_EPG_TMP(
      account,
      ngay_th,
      goi_cu,
      ten_goi_cu,
      goi_moi,
      ten_goi_moi
    ) {
      this.dsepg = [];
      let rowepg = {};
      rowepg.ACCOUNT = account;
      rowepg.NGAY_TH = ngay_th;
      rowepg.GOI_CU = goi_cu;
      rowepg.TEN_GOI_CU = ten_goi_cu;
      rowepg.GOI_MOI = goi_moi;
      rowepg.TEN_GOI_MOI = ten_goi_moi;
      rowepg.MAY_CN = this.$root.token.getMaNhanVien();
      rowepg.NGAY_CN = moment(new Date()).format("DD/MM/YYYY");
      rowepg.NGUOI_CN = this.$root.token.getUserName();
      this.dsepg.push(rowepg);
    },
    txtMaTB_KeyPress() {
      if (this.txtMaTB.trim() == "") {
        this.$toast.error("vui lòng nhập IPTV Acoount");
        this.$refs.inputMaTB.focus();
        return false;
      } else {
        this.TaoDS(0);
      }
    },
    async tsbtnGhiLai_Click() {
      if (this.dgvDanhSach.length > 0) {
        let dem = 0;
        let count = 0;
        let thangnam = [];
        let _result = [];
        //dungpa - bổ sung cảnh báo tb đk gói da dịch vụ - 03/05/2017
        // this.loading(true);
        await API.kt_epg_dk_goi(this.axios)
          .then((response) => {
            if (
              response &&
              response.data &&
              response.data.data &&
              response.data.error_code &&
              response.data.error_code === "BSS-00000000"
            ) {
              _result = response.data.data;
              //console.log("ktepg: " + _result);
            }
          })
          .catch((error) => {
            console.log(error);
          })
          .finally(() => {
            // this.loading(false);
          });
        if (_result > 0) {
          this.$toast.error("Bạn phải hủy gói trước khi đồng bô EPG");
          return false;
        }
        for (let idex = 0; idex < this.dgvDanhSach.length; idex++) {
          if (this.dgvDanhSach[idex]["chon"] == "s1") {
            if (this.dgvDanhSach[idex]["thuebao_id"] != null) {
              if (this.KiemTraDongBo(this.dgvDanhSach[idex]["account"], idex)) {
                let matb = this.dgvDanhSach[idex]["account"];
                let ngay_th = moment(this.dgvDanhSach[idex]["ngay_th"]).format(
                  "DD/MM/YYYY"
                );
                /*  let data =
                  'ds_para: [{"ACCOUNT": "' +matb +
                  '","NGAY_TH":  "' +ngay_th +
                  '","GOI_CU":  "' +this.dgvDanhSach[idex]["goi_cu"] +
                  '","TEN_GOI_CU": "' +this.dgvDanhSach[idex]["ten_goi_cu"] +
                  '","GOI_MOI": "' +this.dgvDanhSach[idex]["goi_moi"] +
                  '","TEN_GOI_MOI": "' +this.dgvDanhSach[idex]["ten_goi_moi"] +
                  '","MAY_CN": "' +this.$root.token.getUserName() +
                  '","NGUOI_CN": "' +this.$root.token.getUserName() +
                  '"}]'; */
                let thuebao_id = this.dgvDanhSach[idex]["thuebao_id"]
                  ? this.dgvDanhSach[idex]["thuebao_id"]
                  : 0;
                let tocdoidmoi = this.dgvDanhSach[idex]["tocdo_id_moi"]
                  ? this.dgvDanhSach[idex]["tocdo_id_moi"]
                  : 0;
                let tocdoidcu = this.dgvDanhSach[idex]["tocdo_id"]
                  ? this.dgvDanhSach[idex]["tocdo_id"]
                  : 0;
                let matocdocu = this.dgvDanhSach[idex]["mtd_vtt"]
                  ? this.dgvDanhSach[idex]["mtd_vtt"]
                  : 0;
                let tengoicu = this.dgvDanhSach[idex]["ten_goi_cu"]
                  ? this.dgvDanhSach[idex]["ten_goi_cu"]
                  : "";
                let tengoimoi = this.dgvDanhSach[idex]["ten_goi_moi"]
                  ? this.dgvDanhSach[idex]["ten_goi_moi"]
                  : "";
                let data =
                  '[{"THUEBAO_ID": "' +
                  thuebao_id +
                  '","TOCDOIDMOI":  "' +
                  tocdoidmoi +
                  '","TOCDOIDCU":  "' +
                  tocdoidcu +
                  '","MATOCDO_CU":  "' +
                  matocdocu +
                  '","GOI_CU":  "' +
                  this.dgvDanhSach[idex]["goi_cu"] +
                  '","TEN_GOI_CU": "' +
                  tengoicu +
                  '","GOI_MOI": "' +
                  this.dgvDanhSach[idex]["goi_moi"] +
                  '","TEN_GOI_MOI": "' +
                  tengoimoi +
                  '","NGAY_TH": "' +
                  ngay_th +
                  '","MA_TB": "' +
                  matb +
                  '","MAY_CN": "' +
                  this.$root.token.getUserName() +
                  '","NGUOI_CN": "' +
                  this.$root.token.getUserName() +
                  '"}]';
                //console.log("data: " + data);
                await API.fn_dongbo_thaydoi_epg(this.axios, { ds_para: data })
                  .then((response) => {
                    if (
                      response &&
                      response.data &&
                      response.data.data &&
                      response.data.error_code &&
                      response.data.error_code === "BSS-00000000"
                    ) {
                      // console.log("fn_dongbo_thaydoi_epg: " + response.data.data);
                      if (response.data.data != "1") {
                        this.$toast.error(response.data.data);
                        return false;
                      }
                      dem = dem + 1;
                      console.log("dem: "+dem)
                    }
                  })
                  .catch((error) => {
                    console.log(error);
                  })
                  .finally(() => {});
              } else {
                return false;
              }
            } else {
              let vmatb = this.dgvDanhSach[idex]["account"];
              this.$toast.error(
                "Thuê bao : " +
                  vmatb +
                  " không có trong dữ liệu Viễn thông tỉnh !"
              );
              HienThiDS(vdongbo);
              /*  for (int i = 0; i < dgvDanhSach.RowCount; i++)
                                {
                                    if (vmatb == dgvDanhSach.Rows[i].Cells["ma_tb"].Value.ToString())
                                    {
                                        dgvDanhSach.Rows[i].Selected = true;
                                        dgvDanhSach.CurrentCell = dgvDanhSach.Rows[i].Cells[0];
                                        break;
                                    }
                                } */
              return false;
            }
          }
        }
        if (dem > 0) {
          this.$toast.success(
            "Đồng bộ dữ liệu thay đổi gói cước trên EPG về Viễn thông tỉnh thành công !"
          );
          this.HienThiDS(this.vdongbo);
        } else {
          this.$toast.error(
            "Bạn chưa chọn thuê bao để đồng bộ EPG về Viễn thông tỉnh !"
          );
        }
      } else {
        this.$toast.error("Không có thuê bao để thực hiện đồng bộ !");
      }
    },
    rdoDongBo_CheckedChanged() {
      this.chkAll = false;
      if (this.rdoDongBo == 0) {
        //Chua dong bo
        this.tsbtnTaoDS = true;
        this.tsbtnDongBo = true;
        this.dgvDanhSach = [];
        this.vdongbo = 1;
        this.HienThiDS(this.vdongbo);
        this.lblTongsoTB = this.dgvDanhSach.length;
      } else {
        //Da dong bo
        this.tsbtnTaoDS = false;
        this.tsbtnDongBo = false;
        this.vdongbo = 2;
        this.HienThiDS(this.vdongbo);
      }
    },
    chkAll_CheckedChanged() {
      if (this.chkAll === true) {
        //checked
        for (let i = 0; i < this.dgvDanhSach.length; i++) {
          this.dgvDanhSach[i].chon = "s1";
        }
        this.dgvDanhSach = [...this.dgvDanhSach];
      } else {
        //uncheck
        for (let i = 0; i < this.dgvDanhSach.length; i++) {
          this.dgvDanhSach[i].chon = 0;
        }
        this.dgvDanhSach = [...this.dgvDanhSach];
      }
    },
    btnTK_CT_Click() {
      this.chkAll = false;
      for (let i = 0; i < this.dgvDanhSach.length; i++) {
        this.dgvDanhSach[i].chon = 0;
      }
      this.dgvDanhSach = [...this.dgvDanhSach];
      this.$refs.popupTraCuuAccount.showModal();
    },
    dgvDanhSach_SelectionChanged(dataKeys) {
      for(let i=0 ;i<dataKeys.length;i++){
        if(dataKeys[i].chon != "s1"){
          this.onAccountChange(dataKeys[i].account)
        }
      }
    },
    dgvDanhSach_DeSelectionChanged(dataKeys){
      if (!dataKeys.data) {
        return;
      }
      if(dataKeys.isInteracted){
        this.onAccountChangeDeSelect(dataKeys.data.account);
      }
    },
    DblClickRow: function (i, item) {
      this.btnTK_CT_Click();
    },
    onAccountChange(data) {
      let dem = 0;
      for (let r of this.dgvDanhSach) {
        if (r["account"] == data) {
          r["chon"] = "s1";
         /*  let index = this.dgvDanhSach.findIndex((a) => a.account == data);
          let page = parseInt(index / this.pageinfoDSmaxSize);
          this.$refs.gridviewDanhSach.$refs.pagination.gotoPage(page); */
          dem++;
        }
      }
      this.dgvDanhSach = [...this.dgvDanhSach];
      if (dem == 0) {
        this.$toast.error("Không tìm thấy thông tin account: " + data);
      }
    },
    onAccountChangeDeSelect(data) {
      for (let r of this.dgvDanhSach) {
        if (r["account"] == data) {
          r["chon"] = 0;
        }
      }
      this.dgvDanhSach = [...this.dgvDanhSach];
    },
    async KiemTraDongBo(acc, idex) {
      let ds = [];
      this.loading(true);
      //ds = tchopdong.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(acc, "0", DichVuVienThong.ADSL, LoaiHopDong.THAY_DOI_TOCDO_ADSL);
      await API.kiemtra_thuebao_lap_hopdong_khac(this.axios, {
        dichVuVtId: 4,
        loaiHdId: 8,
        maGd: "0",
        maTb: acc,
      })
        .then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            ds = response.data.data;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
      if (ds.length > 0) {
        let loaihdid = ds[0]["loaihd_id"];
        if (
          loaihdid == LoaiHopDong.CHAMDUT_SD ||
          loaihdid == LoaiHopDong.THAY_DOI_TOCDO_ADSL
        ) {
          let status = ds[0]["status"];
          if (status == TRANGTHAI_DONGBO.DONGBO_CM) {
            let message =
              "Số máy/Acc: " +
              acc +
              " đang được lập bởi hợp đồng (phụ lục) " +
              ds[0]["ten_loaihd"] +
              " có mã giao dịch " +
              ds[0]["ma_gd"] +
              " do User " +
              ds[0]["nguoi_cn"] +
              " thuộc đơn vị " +
              ds[0]["ten_dv"] +
              " thực hiện vào ngày " +
              ds[0]["ngay_yc"] +
              "";

            this.$toast.error(
              message + " \n Phải hoàn thiện phục lục trước khi đồng bộ EPG"
            );
          } else {
            let message =
              "Số máy/Acc: " +
              acc +
              " đang được lập bởi hợp đồng (phụ lục) " +
              ds[0]["ten_loaihd"] +
              " có mã giao dịch " +
              ds[0]["ma_gd"] +
              " do User " +
              ds[0]["nguoi_cn"] +
              " thuộc đơn vị " +
              ds[0]["ten_dv"] +
              " thực hiện vào ngày " +
              ds[0]["ngay_yc"] +
              "";
            this.$toast.error(
              message + " \n Phải thoái trả phụ lục trước khi đồng bộ EPG"
            );
          }
          /*  dgvDanhSach.CurrentCell = dgvDanhSach.Rows[idex].Cells["ma_tb"];
          dgvDanhSach.CurrentRow.Selected = false;
          dgvDanhSach.Rows[idex].Selected = true; */
          return false;
        }
      } else {
        return true;
      }
    },
  },
};
</script>