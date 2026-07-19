<template src="./template.html"></template>
<style scoped src="./style.css"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import api from './api'
import ChangePassword from './popup/ChangePassword.vue'
import KhoaTreEm from './popup/KhoaTreEm.vue'
import ErrorCodeMyTV from './popup/TraCuuMaLoi.vue'

export default {
  components: {
    breadcrumb,
    ChangePassword,
    KhoaTreEm,
    ErrorCodeMyTV
  },
  data() {
    return {
      header: {
        title: 'Tra cứu thông tin tài khoản Mytv',
        list: [
          {name: 'Lập hợp đồng', link: {name: 'Ui.cards'}},
          {
            name: 'Lắp đặt mới',
            link: {name: 'Ui.buttons'}
          }
        ]
      },

      //UI:
      tsbtnDoiMK: true,
      tsbtnTraCuu: true,

      txtAccount: '',
      txtMatKhau: '',
      txtHinhThucTT: '',
      txtTrangThai: '',
      txtSTB: '',
      txtLoaiTB: '',
      txtMac: '',
      txtGhiChu: '',

      txtLoaiKH: '',
      txtMaHD: '',
      txtKyHD: '',
      txtNgayTC: '',
      txtGoiCuoc: '',
      txtPortDSL: '',
      txtDeviceName: '',
      txtVMP: '',
      txtGoiTichHop: '',

      txtTenKH: '',
      txtNgaySinh: '',
      txtGioiTinh: '',
      txtTinh: '',
      txtCMT: '',
      txtSoGT: '',
      txtSoDT: '',
      txtFax: '',
      txtDiaChiKH: '',
      txtEmail: '',

      txtSolienhe: '',
      radPayTV_Mo: 0,
      radPayTV_Khoa: true,

      listPayTV: {
        "myTVShow": "0",
        "ceeme": "0",
        "danet": "0",
        "cloudGame": "0",
        "phimTruyen": "0",
        "troChoi": "0",
        "karaoke": "0",
        "caNhac": "0",
        "docTruyen": "0",
        "theThao": "0",
        "sucKhoeLamDep": "0",
        "nhipCauMyTV": "0",
        "phongCachSong": "0",
        "thieuNhi": "0",
        "giaoDucDaoTao": "0",
        "daoTaoTuXa": "0",
        "ketQuaXoSo": "0",
        "binhChon": "0",
        "payTV": "0",
        "myTVID": null,
        "fimPlus": "0",
        "kplusFLG": "0",
        "fafilmFLG": "0",
        "fimPlus_USE": "0",
        "kplus_USE": "0",
        "fafilm_USE": "0",
        "myTVShow_USE": "0",
        "hbo_USE": "0",
        "vtvcab_USE": "VCTV01",
        "hboflg": "0",
        "vtvcabFlg": "0"
      },
      rdioCKb: 1,
      rdoHethong: 1,
      dsLichSuLogin: [],
      dsThietBiChayKPlus: [],
      dsGoiAddOn: []
    }
  },
  methods: {
    async tsbtnDoiMK_Click() {
      if (this.txtAccount.trim() === "")
        return;
      await this.$refs.dialogChangePassword.showModal(this.txtAccount)
    },

    async tsbtnResetKPlus_Click() {
      try {
        if (this.txtAccount.toString().trim() !== "") {
          // var response_unbindkplus = vasc.UnbindKplus(txtAccount.Text.ToString().Trim());

          const response = await api.unbindKplus(this.axios, {
            "username": this.txtAccount.toString().trim()
          });
          // var results = JsonConvert.DeserializeObject<UnbindKplus>(response_unbindkplus);
          const data = await response.data
          if (data.errorCode === 0 && data.data === 'ok') {
            this.$root.$toast.success("Đã gỡ thiết bị play k+ đầu tiên");
          } else {
            // Message_Box.Show(results.message);
            this.$root.$toast.error(data.faultString);
          }
        } else {
          this.$root.$toast.error("Bạn chưa nhập thông tin tài khoản");
          // Message_Box.Show("Bạn chứ nhập thông tin tài khoản");
          // txtAccount.Focus();
        }
      } catch (ex) {
        this.$root.$toast.error(ex.message);
      }
    },

    async tsbtnResetThietBi_Click() {
      try {
        if (this.txtAccount.toString().trim() !== "") {
          // var response_resetccu = vasc.ResetCCU(txtAccount.Text.ToString().Trim());
          // var results = JsonConvert.DeserializeObject<Result_ResetCCU>(response_resetccu);
          const response = await api.resetCCU(this.axios, {
            "username": this.txtAccount.toString().trim()
          });
          const data = await response.data
          if (data.errorCode === 0 && data.data === 'ok') {
            this.$root.$toast.success(data.faultString);
          } else {
            this.$root.$toast.error(data.faultString);
          }
        } else {
          this.$root.$toast.error("Bạn chưa nhập thông tin tài khoản");
        }
      } catch (ex) {
        this.$root.$toast.error(ex.toString());
      }
    },

    async tsbtnKhoaTreEm_Click() {
      await this.$refs.dialogKhoaTreEm.showModal()
    },

    async tsbtnTraCuuMaLoi_Click() {
      await this.$refs.dialogErrorCodeMyTV.showModal()
    },

    async acceptDoiMK(item) {
      // console.log(item)
      this.txtAccount = item.taikhoan;
      await this.tsbtnTraCuu_Click();
    },

    async frmTraCuuVASC_Load() {

    },

    txtAccount_KeyPress: async function (e) {
      if (e.keyCode === 13) {
        await this.tsbtnTraCuu_Click();
      } else {

      }
    },
    Clear() {
      this.txtMac = "";
      this.txtSoGT = "";
      this.txtVMP = "";
      this.txtMaHD = "";
      this.txtTrangThai = "";
      this.txtHinhThucTT = "";
      this.txtLoaiKH = "";
      this.txtKyHD = "";
      this.txtNgayTC = "";
      this.txtTenKH = "";
      this.txtNgaySinh = "";
      this.txtSoDT = "";
      this.txtGoiCuoc = "";
      this.txtGioiTinh = "";
      this.txtDiaChiKH = "";
      this.txtEmail = "";
      this.txtFax = "";
      this.txtCMT = "";
      this.txtTinh = "";
      this.txtSTB = "";
      this.txtMatKhau = "";
      this.txtGoiTichHop = "";
      this.txtGhiChu = "";
      this.txtPortDSL = "";

      // for (int i = 0; i < listPayTV.Items.Count; i++)
      // listPayTV.SetItemCheckState(i, CheckState.Unchecked);
      this.listPayTV = {
        "myTVShow": "0",
        "ceeme": "0",
        "danet": "0",
        "cloudGame": "0",
        "phimTruyen": "0",
        "troChoi": "0",
        "karaoke": "0",
        "caNhac": "0",
        "docTruyen": "0",
        "theThao": "0",
        "sucKhoeLamDep": "0",
        "nhipCauMyTV": "0",
        "phongCachSong": "0",
        "thieuNhi": "0",
        "giaoDucDaoTao": "0",
        "daoTaoTuXa": "0",
        "ketQuaXoSo": "0",
        "binhChon": "0",
        "payTV": "0",
        "myTVID": null,
        "fimPlus": "0",
        "kplusFLG": "0",
        "fafilmFLG": "0",
        "fimPlus_USE": "0",
        "kplus_USE": "0",
        "fafilm_USE": "0",
        "myTVShow_USE": "0",
        "hbo_USE": "0",
        "vtvcab_USE": "VCTV01",
        "hboflg": "0",
        "vtvcabFlg": "0"
      }
    },
    async tsbtnTraCuu_Click() {
      this.loading(true);
      await this.TRACUU_DK();
      this.loading(false);
    },
    async LichSuLogin() {
      this.dsLichSuLogin = [];
      try {
        const response = await api.loginHistory(this.axios, {
          "username": this.txtAccount.toString().trim()
        });
        const data = await response.data
        if (data.errorCode === 0) {
          if (data.data.result === 0) {

            if (data.data.data.device.length <= 0) {
              this.dsLichSuLogin.push({
                "name": "",
                "type_name": "",
                "last_login": data.data.data.last_login,
                "ip_address": data.data.data.ip_address,
                "version_mytv_user": "",
                "version_newest": ""
              })

            } else {
              this.dsLichSuLogin = data.data.data.device;
            }


          } else {
            this.$root.$toast.error("Có lỗi : " + data.data.message);
          }
        }
      } catch (ex) {
        console.log(ex.toString())
      }
    },
    async ThietBiChayKplus() {
      this.dsThietBiChayKPlus = [];
      try {
        const response = await api.getDeviceKplus(this.axios, {
          "username": this.txtAccount.toString().trim()
        });
        const data = await response.data
        if (data.errorCode === 0) {
          if (data.data.result === 0) {
            this.dsThietBiChayKPlus = data.data.data;
          } else {
            this.$root.$toast.error("Có lỗi : " + data.data.message);
          }
        }
      } catch (ex) {
        console.log(ex.toString())
      }
    },
    async TRACUU_DK() {
      try {
        this.Clear();
        if (this.rdioCKb.toString() === "1") {
          await this.TraCuu();
        } else {
          let ma_tb = [];
          let post = {
            "serialNumber": this.txtAccount
          }
          try {
            const response = await api.Get_DS_TB_FromSR(this.axios, post);
            const data = await response.data
            if (data.errorCode === 0) {
              if (data.data !== null) {
                ma_tb = data.data;
              } else {
                ma_tb = [];
              }
            }
          } catch (e) {
            this.$root.$toast.error("Có lỗi khi tìm theo serial thiết bị " + ma_tb);
            return;
          } finally {
          }

          if (ma_tb.length === 0) {
            this.$root.$toast.error("Không tìm thấy tài khoản nào sử dụng thiết bị " + this.txtAccount.toUpperCase())
            return;
          }
          //var ds_split = ma_tb.split(',');
          if (ma_tb.length > 0) {
            this.txtAccount = ma_tb[0].toString();
            await this.TraCuu();
          } else {
            // if (ds_acc != null) {
            //   ds_acc.Clear();
            //   foreach(
            //   var t
            // in
            //   ds_split
            // )
            //   {
            //     ds_acc.Rows.Add(t.ToString());
            //   }
            //
            // }

            // grcTT.DataSource = ds_acc;
            // pccTB.Width = 400;
            // pccTB.Height = 200;
            // grvTT.BestFitColumns();
            // txtAccount.Focus();
            // txtAccount.ShowPopup();
          }
        }
        await this.LichSuLogin();
        await this.ThietBiChayKplus();
        await this.CheckCCU();
        await this.Lay_ThongTIn_Goi_ADDON();
      } catch (ex) {
        // Message_Box.ShowError("Có lỗi: \n" + ex.Message);
        this.$root.$toast.error("Có lỗi: \n" + ex.message())
      }
    },
    async CheckCCU() {

    },
    async Lay_ThongTIn_Goi_ADDON() {

    },
    async TraCuu() {
      try {
        // if (rdoHeThong.EditValue.ToString() != "1")
        // vasc = new VASCService("ecows", "Wj3CfL6PNeuWYH+8P1rbuA==", tt_nd.diemgiaodich_vasc);
        // GetSubscriberInfoVO info = vasc.GetSubscriberInfo(txtAccount.Text.Trim());
        const response = await api.getSubscriberInfo(this.axios, {
          "iptvaccount": this.txtAccount
        })
        const data = await response.data
        if (data.errorCode === 0) {
          if (data.data !== null) {
            let info = data.data;
            console.log(info.idno)
            if (info.idno === null) {
              // Message_Box.ShowTB(info.errordesc);
              this.$toast.error(info.errordesc)
              return;
            }
            this.txtLoaiTB = info.b2BFlg === "0" ? "B2C" : "B2B";
            this.txtMaHD = info.contractCode;
            this.txtTrangThai = info.status;
            //txtHinhThucTT.Text = info.Status;
            this.txtLoaiKH = info.cateName;
            this.txtKyHD = info.contractSignDate;
            this.txtNgayTC = info.dateBill;
            this.txtTenKH = info.name;
            this.txtNgaySinh = info.birthday;
            this.txtSoDT = info.telNo;
            this.txtGoiCuoc = info.packageName;
            this.txtGioiTinh = info.sex;
            this.txtDiaChiKH = info.firstAdd;
            this.txtEmail = info.email;
            this.txtFax = info.faxNo;
            this.txtCMT = info.idno;
            this.txtTinh = info.areaName;
            this.txtSTB = info.stbid;
            this.txtMatKhau = info.iptvpw;
            this.txtSoGT = info.idno;
            this.txtSoDT = info.cellNo;
            if (info.fromSys === "1")
              this.txtVMP = "Hệ thống Mytv VMP";
            else
              this.txtVMP = "Hệ thống Mytv thường";
            this.txtMac = info.mac;
            this.txtPortDSL = info.portDSL;
            this.txtDeviceName = info.deviceName;
            this.txtGhiChu = "Lần đăng nhập đầu: " + info.dateBill
              + "\r\n" + "Lần đăng nhập cuối: " + info.last_Login
              + "\r\n" + " (IP: " + info.ip + ", MAC: " + info.mac + ")";

            try {
              const response = await api.getStatusBlockPayTV(this.axios, {"iptvaccount": info.iptvaccount})
              const data = await response.data
              if (data.errorCode === 0) {
                let infoPay = data.data;
                this.radPayTV_Mo = infoPay.payTV === "1" ? 1 : 0;

                this.listPayTV.cloudGame = (infoPay.cloudGame === "0") ? 1 : 0;
                this.listPayTV.troChoi = (infoPay.troChoi === "0");
                this.listPayTV.phimTruyen = (infoPay.phimTruyen === "0") ? 1 : 0;
                this.listPayTV.karaoke = (infoPay.karaoke === "0") ? 1 : 0;
                this.listPayTV.caNhac = (infoPay.caNhac === "0") ? 1 : 0;
                this.listPayTV.docTruyen = (infoPay.docTruyen === "0") ? 1 : 0;
                this.listPayTV.theThao = (infoPay.theThao === "0") ? 1 : 0;
                this.listPayTV.sucKhoeLamDep = (infoPay.sucKhoeLamDep === "0") ? 1 : 0;
                this.listPayTV.nhipCauMyTV = (infoPay.nhipCauMyTV === "0") ? 1 : 0;
                this.listPayTV.phongCachSong = (infoPay.phongCachSong === "0") ? 1 : 0;
                this.listPayTV.giaoDucDaoTao = (infoPay.giaoDucDaoTao === "0") ? 1 : 0;
                this.listPayTV.daoTaoTuXa = (infoPay.daoTaoTuXa === "0") ? 1 : 0;
                this.listPayTV.ketQuaXoSo = (infoPay.ketQuaXoSo === "0") ? 1 : 0;
                this.listPayTV.hboflg = (infoPay.hboflg === "0") ? 1 : 0;
                this.listPayTV.fafilmFLG = (infoPay.fafilmFLG === "0") ? 1 : 0;
                this.listPayTV.binhChon = (infoPay.binhChon === "0") ? 1 : 0;
                this.listPayTV.vtvcabFlg = (infoPay.vtvcabFlg === "0") ? 1 : 0;
                this.listPayTV.kplusFLG = (infoPay.kplusFLG === "0") ? 1 : 0;
                this.listPayTV.fimPlus = (infoPay.fimPlus === "0") ? 1 : 0;
                this.listPayTV.danet = (infoPay.danet === "0") ? 1 : 0;
                this.listPayTV.ceeme = (infoPay.ceeme === "0") ? 1 : 0;
                this.listPayTV.thieuNhi = (infoPay.thieuNhi === "0") ? 1 : 0;
              }
            } catch (e) {
              console.log('Lấy thông tin BlockPayTV không thành công')
            } finally {
            }

            let object = {
              "goi_id": "0"
            }
            let vgoi_dadv = "";
            try {
              const response = await api.getSubscriberInfoV2(this.axios, {
                objRequest: {
                  inforDevice: info.deviceName,
                  iptvaccount: info.iptvaccount
                }
              })
              const data = await response.data
              if (data.errorCode === 0) {
                if (data.data.length > 0) {
                  vgoi_dadv = data.data[0].MEGAMYTV
                }

                if (vgoi_dadv !== "") {
                  try {
                    // object.goi_id = '1'
                    const response = await api.getDsMegaMyTV(this.axios, object)
                    const data = await response.data
                    if (data.errorCode === 0) {
                      if (data.data.length > 0) {
                        let size = data.data.length - 1;
                        for (let i of data.data) {
                          if (data.data.indexOf(i) === size) {
                            this.txtGoiTichHop += i.ten_goi
                          } else {
                            this.txtGoiTichHop += i.ten_goi + ' - '
                          }
                        }
                      } else {
                        this.txtGoiTichHop = ""
                      }
                    }
                  } catch (e) {
                    console.log(e)
                  } finally {
                  }
                }
              }
            } catch (e) {
              console.log('Lấy thông tin thuê bao MyTV V2 không thành công')
            } finally {
            }
          }

        } else {
          this.$toast.error(data.faultString)
          return;
        }
      } catch (e) {
        this.$toast.error('Lấy thông tin thuê bao MyTV không thành công')
      } finally {

      }
    },
  },
  mounted() {
    let ma_tb = this.$route.query.ma_tb
    if (ma_tb) {
      this.txtAccount = ma_tb
      this.tsbtnTraCuu_Click()
    }
  }
}
</script>
