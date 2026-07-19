<template src="./template.html"></template>
<style src="./style.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import api from "./api";
import enums from "./enums";
import { LoaiHopDong, TRANGTHAI_DONGBO } from "@/utils/Enum";
import InPhieuThu from "@/modules/print/InPhieuThu/InPhieuThu.vue";
import PrintReport from "@/modules/print/InPhieuChi/PrintReport.vue";
import Vue from "vue";
export default {
  components: { breadcrumb, InPhieuThu, PrintReport },
  name: "ListOfPaymentList",
  props: {
    isPopup: {
      type: Boolean,
    },
  },
  mounted() {
    this.searchDSTT();
    // this.$refs.maKHGocInput.focus()
    // this.addKeyDownEvent()
  },
  destroyed() {},
  computed: {
    enableDeleteBnt() {
      return this.gridboxDSPTT.selected !== null;
    },
  },
  watch: {
    loading: function (val, oldval) {
      this.$root.showLoading(val);
    },
    openedPopup: function (val, oldval) {
      if (val) {
        this.removeKeyDownEvent();
      } else {
        this.addKeyDownEvent();
      }
    },
  },
  data() {
    return {
      loading: false,
      header: {
        title: "DANH SÁCH PHIẾU THANH TOÁN",
        list: [
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          { name: "Lắp đặt mới", link: { name: "Ui.cards" } },
        ],
      },
      tongtien: "0",
      headerGridboxDSPTT: enums.headerGridboxDSPTT,
      gridboxDSPTT: {
        data: [],
        selected: null,
      },
      openedPopup: false,
      xacNhanGhiLaiVisible: false,
      maGD: "",
      inKimPhieuThuData: [],
      inKimHDData: [],
      searchDate: {
        isEnabledFromDate: true,
        isEnabledToDate: false,
        fromDate: moment(new Date()).format("DD/MM/YYYY"),
        toDate: moment(new Date()).format("DD/MM/YYYY"),
        dateconfig: {
          altFormat: "d/m/Y",
          altInput: true,
          dateFormat: "d/m/Y",
          allowInput: true,
        },
      },
    };
  },
  methods: {
    addKeyDownEvent() {
      window.addEventListener("keydown", this.onF5KeyDown);
    },
    removeKeyDownEvent() {
      window.removeEventListener("keydown", this.onF5KeyDown);
    },
    onF5KeyDown(event) {
      if (event.keyCode === 116) {
        event.preventDefault();
        this.clearData();
      }
    },
    GetData(response, lowerCase = false) {
      if (response.data.error === 200 || response.data.error === "200") {
        if (lowerCase) {
          return response.data.data.map((obj) => {
            return Object.keys(obj).reduce((accumulator, key) => {
              accumulator[key.toLowerCase()] = obj[key];
              return accumulator;
            }, {});
          });
        } else return response.data.data;
      } else {
        console.log(response.data.error_code);
      }
      return [];
    },
    GetDataObj(response) {
      console.log(response);
      if (
        (response.data.error === 200 || response.data.error === "200") &&
        response.data.error_code === "BSS-00000000"
      ) {
        return response.data.data;
      } else {
        throw new Error(response.data.message_detail);
      }
    },
    async searchDSTT() {
      // if (this.maGD !== '' || this.searchDate.isEnabledFromDate) {
      this.gridboxDSPTT = { data: [], selected: null };
      this.loading = true;
      const params = {
        vnhanvien_id: this.$root.token.getNhanVienID(),
        vtungay: this.searchDate.isEnabledFromDate ? this.searchDate.fromDate : "0",
        vdenngay: this.searchDate.isEnabledFromDate ? this.searchDate.toDate : "0",
        vma_gd: this.maGD,
      };
      let data = this.GetData(await api.lay_ds_phieutt(this.axios, params));
      if (data && data.length > 0) {
        this.tongtien = Number(
          data.reduce((a, item) => {
            return a + item.tongtien;
          }, 0)
        ).toLocaleString();
        data = data.map((item) => {
          return {
            ...item,
            // ngay_yc: item.ngay_yc ? moment(item.ngay_yc).format('DD/MM/YYYY') : '',
            tongtien: Number(item.tongtien).toLocaleString(),
            ngay_tt: moment(item.ngay_tt, "DD/MM/YYYY hh:mm:ss"),
          };
        });
        data.sort((a, b) => (a.ngay_tt < b.ngay_tt ? 1 : -1));
        this.gridboxDSPTT.data = data;
      } else {
        this.$toast.warning("Không tìm thấy phiếu !");
      }
      this.loading = false;
      // } else {
      //   this.$toast.error('Hãy nhập thông tin tìm kiếm')
      // }
    },
    rowSelectedGridbox(items) {
      if (items.length > 0) {
        this.gridboxDSPTT.selected = items[0];
        this.maGD = this.gridboxDSPTT.selected.ma_gd;
      }
    },
    async XoaPhieu() {
      const vphieutt_id = parseInt(this.gridboxDSPTT.selected.phieutt_id);
      try {
        this.loading = true;
        const kiemtraDk = this.GetData(
          await api.kiemtra_xoa_phieutt_v2(this.axios, { vphieutt_id: vphieutt_id })
        );
        // const kiemtraDk = 'ok'
        this.loading = false;
        if (kiemtraDk === "ok") {
          this.$bvModal
            .msgBoxConfirm("Bạn thật sự muốn xóa phiếu không ?", {
              title: "Thông báo",
              size: "sm",
              centered: true,
              okTitle: "Xóa phiếu",
              cancelTitle: "Hủy",
            })
            .then(async (v) => {
              if (!v) {
                $this.$toast.warning("Bạn đã hủy xóa phiếu ");
              } else {
                this.okXoaPhieu();
              }
            });
        } else {
          this.$toast.error(`Không đủ điều kiện xóa phiếu: ${kiemtraDk}`);
        }
      } catch (error) {
        this.$toast.error(
          `Đã có lỗi xảy ra: ${
            error.data?.message_detail || error.data?.message || error.message
          }`
        );
        console.error(error);
      } finally {
        this.loading = false;
      }
    },
    async okXoaPhieu() {
      const vphieutt_id = parseInt(this.gridboxDSPTT.selected.phieutt_id);
      this.loading = true;
      try {
        const dsTTTB = this.GetData(
          await api.lay_tttb_theo_phieutt_id(this.axios, { vphieutt_id: vphieutt_id })
        );
        if (dsTTTB.length > 0) {
          const loaihd_id = dsTTTB[0].LOAIHD_ID;
          const dshdtb_id = dsTTTB[0].DSHDTB_ID;
          // eslint-disable-next-line eqeqeq
          if (loaihd_id.toString() === LoaiHopDong.DAT_MOI.toString()) {
            const phieutt_id_neo = this.gridboxDSPTT.selected.phieutt_id_neo;
            const ma_gd_neo = this.gridboxDSPTT.selected.ma_gd;
            if (phieutt_id_neo !== null && phieutt_id_neo !== "") {
              // Gọi CCBS Webservice: ts_thuphi_hoamangmoi_thuphi_xoaphieu
              // const kq = new frmThanhToanHD().Delete_ThanhToan_TBLD_VinaPhone(phieutt_id_neo, ma_gd_neo);

              const PSUSERID = this.$root.token.getMaCCBS();
              if (PSUSERID == null || PSUSERID == "-1") {
                $this.$toast.warning(
                  "Thông tin người dùng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu."
                );
                return null;
              } else {
                try {
                  const kq = this.GetDataObj(
                    await api.ts_thuphi_hoamangmoi_thuphi_xoaphieu(this.axios, {
                      ma_tinh: this.$root.token.getMaTinh(),
                      ip: await this.$root.token.getIP(),
                      kieu_huy: "frmTHANHTOANHDCQ",
                      phieu_id: phieutt_id_neo,
                      ma_hd: ma_gd_neo,
                      userid: PSUSERID,
                    })
                  );
                  for (const vhdtb_id of dshdtb_id.split(",")) {
                    //  obj.CAPNHAT_STATUS_HDTB(long.Parse(vhdtb_id), TRANGTHAI_DONGBO.DONGBO_LHD)
                    const resData = this.GetDataObj(
                      await api.capnhat_status_hopdong_thuebao(this.axios, {
                        hdtbId: vhdtb_id,
                        status: TRANGTHAI_DONGBO.DONGBO_LHD,
                      })
                    );
                  }
                  this.$toast.success(
                    `Xoá thành công phiếu thanh toán của hợp đồng có mã giao dịch: ${ma_gd_neo} trên VinaPhone !`
                  );
                } catch (err) {
                  // Message_Box.ShowError('Có lỗi xảy ra trong quá trình xóa phiếu thanh toán của Hợp đồng có mã giao dịch: ' + dt.Rows[dgvDanhSach.CurrentRow.Index]['ma_gd'] + ' trên VinaPhone !.\nLỗi: ' + kq)
                  this.$toast.error(
                    `Có lỗi xảy ra trong quá trình xóa phiếu thanh toán của Hợp đồng có mã giao dịch: ${ma_gd_neo} trên VinaPhone !.\nLỗi: ${kq}`
                  );
                  return;
                }
              }
            }
            // eslint-disable-next-line eqeqeq
          } else if (
            loaihd_id.toString() === LoaiHopDong.CHUYEN_QUYEN.toString() ||
            loaihd_id.toString() === LoaiHopDong.TACHNHAP_TB.toString()
          ) {
            const phieutt_id_neo = this.gridboxDSPTT.selected.phieutt_id_neo;
            const ma_gd_neo = this.gridboxDSPTT.selected.ma_gd;
            if (phieutt_id_neo !== null && phieutt_id_neo !== "") {
              {
                for (const i of [0, 1, 2]) {
                  const PSUSERID = this.$root.token.getMaCCBS();
                  if (PSUSERID == null || PSUSERID == "-1") {
                    $this.$toast.warning(
                      "Thông tin người dùng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu."
                    );
                    return null;
                  }
                  try {
                    const kq = this.GetDataObj(
                      await api.ts_laphd_chuyenquyen_sudung_xoaphieu(this.axios, {
                        ma_tinh: this.$root.token.getMaTinh(),
                        ip: await this.$root.token.getIP(),
                        phieu_id: phieutt_id_neo,
                        kieu_xoa: "0",
                        ma_hd: ma_gd_neo,
                        userid: PSUSERID,
                      })
                    );
                    this.$toast.success(
                      `Xoá thành công phiếu thanh toán của hợp đồng có mã giao dịch: ${ma_gd_neo} trên VinaPhone !`
                    );
                    break;
                  } catch (err) {
                    if (i === 2) {
                      this.$toast.error(
                        `Có lỗi xảy ra trong quá trình xóa phiếu thanh toán của Hợp đồng có mã giao dịch: ${ma_gd_neo} trên VinaPhone !.\nLỗi: ${kq}`
                      );
                      return;
                    }
                  }
                  // const kq = Ts_laphd_chuyenquyen_sudung_xoaphieu(ma_gd_neo, phieutt_id_neo);
                  // Gọi CCBS Webservice: TS_LAPHD_CHUYENQUYEN_SUDUNG_XOAPHIEU
                }
              }
            }
          }
          // thanhtoan.XOAPHIEU_TTHD(phieutt_id, tt_nd.may_cn, tt_nd.ma_nd)

          const xoaPhieuResData = this.GetDataObj(
            await api.xoaphieu_tthd(this.axios, {
              vphieutt_id: vphieutt_id,
            })
          );
          this.$toast.success(`Xoá phiếu thanh toán thành công`);
          const vtrangthai_thanhtoan_id = this.GetDataObj(
            await api.fn_tt_phieutt_hd(this.axios, {
              param: `${vphieutt_id}`,
              type: 1,
            })
          );
          const ht_tra_id = this.GetDataObj(
            await api.fn_tt_phieutt_hd(this.axios, {
              param: `${vphieutt_id}`,
              type: 2,
            })
          );
          if (ht_tra_id == 7 && vtrangthai_thanhtoan_id == 1) {
            const istest = 0; // 1 test, 0 that
            // VNPTPayController vnptpay = new VNPTPayController();
            // VNPTPayController.obj_hoanvi obj_hoanvi = new VNPTPayController.obj_hoanvi();
            const vhdkh_id = this.GetDataObj(
              await api.fn_tt_phieutt_hd(this.axios, {
                param: `${vphieutt_id}`,
                type: 3,
              })
            );
            var dsKQGachVi = this.GetData(
              await api.lay_thongtin_gachvi_theo_matt(this.axios, {
                vma_tt: this.gridboxDSPTT.selected.ma_gd,
                vphieu_id: vphieutt_id,
                vhdkh_id: vhdkh_id,
                vkyhoadon: 0,
              })
            );
            let vtransacstion_id = "";
            if (dsKQGachVi.length == 3) {
              // thuc hien hoan tien vi vs hop dong gach no qua vi VNPT-PAY
              vtransacstion_id = dsKQGachVi[0].TRANSACTIONID.toString();
              // obj_hoanvi.amount = _amount;
              // obj_hoanvi.cancelType = "1";
              // obj_hoanvi.description = "Hoan Vi PTTB";
              // obj_hoanvi.refTransId = vtransacstion_id;
              // obj_hoanvi.client_request = "";
              // obj_hoanvi.subServiceCode = "";
              // obj_hoanvi.user = tt_nd.ma_nd;
              // var kq_hoanvi = vnptpay.thuchien_hoanvi(obj_hoanvi, istest, tt_nd.tentat.ToUpper());
              const obj_hoanvi = {
                amount: this.gridboxDSPTT.selected.tongtien.toString(),
                cancelType: "1",
                description: "Hoan Vi PTTB",
                refTransId: vtransacstion_id,
                username: this.$auth.getUserName(),
              };
              const kq_hoanvi = await api.cancelTransaction(this.axios, obj_hoanvi);
              // const kq_hoanvi = { errorCode: '00' }
              // string hoanvi_json = JsonConvert.SerializeObject(obj_hoanvi);
              // const kq_log = ThanhToan.insert_logsvnptpay(txtMaGD.Text.ToString(), tt_nd.ma_nd.ToString(), hoanvi_json, kq_hoanvi.errorCode,
              //                                     "HOANVI", kq_hoanvi.errorDescription, kq_hoanvi.transactionId, 1, 14, phieutt_id, 0, vhdkh_id, tt_nd.tentat.ToUpper());
              const kq_log = this.GetDataObj(
                await api.insert_logsvnptpay_smp_v2(this.axios, {
                  vma_tt: this.gridboxDSPTT.selected.ma_gd,
                  vinput: JSON.stringify(obj_hoanvi),
                  vcode: kq_hoanvi.data.error_code,
                  vname: "HOANVI",
                  vdescription: response.data.message_detail
                    ? response.data.message_detail
                    : "",
                  vtransactionid: vtransacstion_id,
                  vkieu: 1,
                  vtransfertype: 14,
                  vphieu_id: vphieutt_id,
                  vkyhoadon: 0,
                  vhdkh_id: vhdkh_id,
                })
              );
              if (kq_hoanvi.data.error_code != 0) {
                this.$toast.error("Hoàn tiền ví thất bại!");
              } else {
                // string kq_capnhat = ThanhToan.capnhat_tt_hoanvi(vtransacstion_id, tt_nd.tentat.ToUpper());
                const kq_capnhat = this.GetDataObj(
                  await api.capnhat_tt_hoanvi(this.axios, {
                    vtransactionid: vtransacstion_id,
                  })
                );
                this.$toast.success("Hoàn tiền ví thành công!");
              }
            } else {
              this.$toast.info(
                "Hoàn tiền ví thất bại! Phiếu chưa hoàn thành gạch qua ví hoặc đã hoàn tiền"
              );
            }
          }
        } else {
          this.$toast.error("Không tìm thấy thông tin thuê bao");
        }
        // this.maGD = ''
        this.searchDSTT();
      } catch (error) {
        this.$toast.error(
          `Đã có lỗi xảy ra: ${
            error.data?.message_detail || error.data?.message || error.message
          }`
        );
        console.error(error);
      } finally {
        this.loading = false;
      }
      // Pending
    },
    async InHD() {
      this.loading = true;
      this.inKimHDData = [];

      try {
        const inHDInKim = this.$refs.inHDInKim;
        this.inKimHDData = this.GetData(
          await api.get_hoadon_vt01(this.axios, {
            vphieu_id: this.gridboxDSPTT.selected.phieutt_id,
          }),
          true
        );
        Vue.nextTick(function () {
          inHDInKim.startPrint();
        });
      } catch (error) {
        this.$toast.error(
          `Đã có lỗi xảy ra: ${
            error.data?.message_detail || error.data?.message || error.message
          }`
        );
      } finally {
        this.loading = false;
      }
    },
    async InKimPhieuThu() {
      this.loading = true;
      // this.inKimPhieuThuData = [];
      try {
        const inPhieuThuInKim = this.$refs.inPhieuThuInKim;
        this.inKimPhieuThuData = this.GetData(
          await api.in_phieuthu(this.axios, {
            in_phieutt_id: this.gridboxDSPTT.selected.phieutt_id,
          }),
          true
        );
        Vue.nextTick(function () {
          inPhieuThuInKim.startPrint();
        });
      } catch (error) {
        this.$toast.error("Không có dữ liệu");
      } finally {
        this.loading = false;
      }
    },
    async PhieuThu() {
      // this.$toast.error('Chức năng in phiếu thu đang được phát triển')
      if (this.gridboxDSPTT.selected === null) {
        this.$toast.error("Không có phiếu thu để in.");
        return;
      }
      this.$bvModal
        .msgBoxConfirm("Chọn kiểu in", {
          title: "In phiếu thu",
          size: "sm",
          centered: true,
          okTitle: "In kim",
          cancelTitle: "In thường",
        })
        .then(async (yes) => {
          if (yes) {
            this.InKimPhieuThu();
          } else {
            this.$refs.popupInPhieuThu.showModal();
          }
        });
    },
    clearData() {
      this.searchDate.fromDate = moment(new Date()).format("DD/MM/YYYY");
      this.searchDate.toDate = moment(new Date()).format("DD/MM/YYYY");
      this.gridboxDSPTT = { data: [], selected: null };
      this.maGD = "";
    },
    showGhiLaiModal() {
      this.xacNhanGhiLaiVisible = true;
    },
  },
};
</script>
