<template src="./template.html"></template>
<style src="./style.scss"></style>
<script>
import api from "./api";
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
export default {
  components: { breadcrumb },
  name: "popupTaoKHTT",
  props: {
    isPopup: {
      type: Boolean,
      default: false,
    },
    inputData: {
      type: Object,
      default: () => {
        return { vhdtb_id: 0 };
      },
    },
  },
  mounted() {
    this.loadData();
  },
  data() {
    return {
      header: {
        title: "TẠO TÀI KHOẢN KHÁCH HÀNG TRUNG TÂM KINH DOANH",
        list: [],
      },
      loading: false,
      vkieu: null,
      formData: {
        comboBoxAccKH: {
          data: [],
          selected: null,
        },
        tenKH: "",
        moTa: "",
        diaChi: "",
        soGiayTo: "",
        soDienThoai: "",
        email: "",
        soHD: "",
        ngayKyHD: null,
        tenHD: "",
        ngayBD: null,
        ngayKT: null,
        tenDN: "",
        matkhau: "",
      },
      dateconfig: {
        altFormat: "d/m/Y",
        altInput: true,
        dateFormat: "d/m/Y",
        allowInput: true,
      },
    };
  },
  watch: {
    loading: function (val, oldval) {
      this.$root.showLoading(val);
    },
    // "CuocCP.Giam": {
    //   handler: function (val, oldval) {
    //     this.CuocCP.Tien = Math.round(val / 1.1);
    //     this.CuocCP.Vat = val - this.CuocCP.Tien;
    //   },
    //   deep: true,
    // },
  },
  computed: {},
  methods: {
    GetData(response) {
      if (response.data.error === 200 || response.data.error === "200") {
        return response.data.data;
      } else {
        console.log(response.data.error_code);
      }
      return [];
    },

    async loadData() {
      await this.getComboboxData();
      await this.LoadThongTinKhachHang();
    },
    async LoadThongTinKhachHang() {
      this.loading = true;
      try {
        this.formData.comboBoxAccKH.selected = null;
        const data = this.GetData(
          await api.sp_lay_thongtin_khoitao_acc_khtt(this.axios, {
            vhdtb_id: this.inputData.vhdtb_id,
          })
        );
        if (data.length > 0) {
          const row = data[0];
          this.vkieu = row.vkieu;
          const mapData = {
            contract_id: row.contract_id,
            agent_id: row.agent_id,
            adser_id: row.adser_id,
            tenKH: row.agent_name,
            moTa: row.description,
            diaChi: row.address,
            soGiayTo: row.paper_number,
            soDienThoai: row.phone_number,
            email: row.email,
            soHD: row.contract_number,
            ngayKyHD: row.contract_date,
            tenHD: row.contract_name,
            ngayBD: row.contract_start_date,
            ngayKT: row.contract_end_date,
            tenDN: row.username,
            matkhau: row.password,
          };
          this.formData = { ...this.formData, ...mapData };

          // var luachon = "VNPT-"+res_tt_nd.data.data.tentat_ccbs;
          // let temp = await this.axios.post(`web-hopdong/khaibaotsl/map_id`,
          //   { id_neo: "PHANLOAI_ID", table: "CSS.HDTB_CNTT", dk: `WHERE HDTB_ID = ${this.inputData.vhdtb_id}` });
          // if (temp.data)
          // {
          //   console.log("test:  ", temp.data);
          //   var phanloai_id = temp.data.data;
          //   luachon += phanloai_id == "1" ? "-qc" : "-cskh";
          // }
          // this.formData.comboBoxAccKH.data = 
          // this.formData.comboBoxAccKH.selected = this.formData.comboBoxAccKH.data.filter(x => x.agent_name == luachon)
          let loai_sms = '';
          const req = {
            P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
            p_nghiepvu: 'LAY_DULIEU_CNTT',
            p_ds_para: JSON.stringify({ HDTB_ID: this.inputData.vhdtb_id, TINH_ID: this.$root.token.getPhanVungID() })
          }
          this.axios.post('/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu', req).then(response => {
            const hdtb_cntt = response.data.data[0];
            loai_sms = hdtb_cntt['phanloai_id'] == 1 ? 'QC' : 'CSKH';
          });
          let lc_response = await this.axios.get(`/web-hopdong/vbn/fn_lay_account_admin_ads?hdtb_id=${this.inputData.vhdtb_id}`);
          if (lc_response.data && lc_response.data.data) {
            this.formData.comboBoxAccKH.data = this.formData.comboBoxAccKH.data.filter(
              (x) => {
                if (this.$root.token.getUserName() == 'hoantq.hni') {
                  return x.agent_name == lc_response.data.data || x.agent_name.includes('MEDIA-VTT-' + loai_sms);
                }
                else {
                  return x.agent_name == lc_response.data.data
                }
              }
            );
          }

          console.log(this.formData.comboBoxAccKH.data);

        }
      } catch (err) {
        console.error(err);
        // setTimeout(() => {
        //   this.getComboboxData()
        // }, 3000)
      } finally {
        this.loading = false;
      }
    },
    async getComboboxData() {
      this.loading = true;
      try {
        this.formData.comboBoxAccKH.selected = null;
        const res = await api.TraCuuKhachHang(this.axios, {
          agent_id: -1,
        });
        this.formData.comboBoxAccKH.data = res.data.data.data
          ? res.data.data.data.items
          : [];
      } catch (err) {
        console.error(err);
        // setTimeout(() => {
        //   this.getComboboxData()
        // }, 3000)
      } finally {
        this.loading = false;
      }
    },
    async TAO_KHTTKD_SERIVCE(req) {

      var kq_return = await api.TAO_KHTTKD(this.axios, req);
      console.log(kq_return)
      if (kq_return.data.data.status == 200) {
        if (kq_return.data.data.data.agentInfo != null) {

          api.fn_capnhat_khachhang_ttkd_brn(this.axios, {
            "vhdtb_id": this.inputData.vhdtb_id,
            "vagent_id": kq_return.data.data.data.agentInfo.agent_id,
            "vadser_id": kq_return.data.data.data.agentInfo.adser_id,
            "vcontract_id": kq_return.data.data.data.agentInfo.contract_id,
            "vusername": kq_return.data.data.data.agentLogin.username,
            "vpassword": kq_return.data.data.data.agentLogin.password,
            "vagent_cha": req.agent_cha,
            "vjson": (req)
          });
        }
        this.GetData(await api.cap_nhat_status_hdtb(this.axios, { "vhdtb_id": this.inputData.vhdtb_id, "vstatus": 3 }))
      }
      return kq_return;
    },
    async TaoKHBnt() {

      // this.loading = true;
      try {
        const res = await this.TAO_KHTTKD_SERIVCE({
          agent_id: this.formData.comboBoxAccKH.selected.agent_id,
          agent_name: this.formData.tenKH,
          description: this.formData.moTa,
          username: this.formData.tenDN,
          agent_type: 1,
          agent_cha: this.formData.comboBoxAccKH.selected.agent_name,
          address: this.formData.diaChi,
          paper_number: this.formData.soGiayTo,
          phone_number: this.formData.soDienThoai,
          email: this.formData.email,
          status: 1,
          contract_number: this.formData.soHD,
          contract_name: this.formData.tenHD,
          contract_date: this.formData.ngayKyHD,
          contract_end_date: this.formData.ngayKT,
          contract_start_date: this.formData.ngayBD,
        });

        const message =
          !res.data.data.data ? res.data.data.message : res.data.data.data.message;

        if (res.data.data.status != 200) {
          this.$toast.error(
            message + ". Liên hệ admin để được hỗ trợ"
          );
        }
        else {
          if (message == "") {
            this.$toast.info("Tạo khách hàng trực tiếp thành công.");
            await this.LoadThongTinKhachHang();
          }
        }
        this.loading = false;

      } catch (err) {
        console.error(err);
        // setTimeout(() => {
        //   this.getComboboxData()
        // }, 3000)
      } finally {
        this.loading = false;
      }
    },
    async CNKHBnt() {
      if (this.formData.agent_id) {
        this.loading = true;
        try {
          const res = await api.UPDATE_KHTTKD(this.axios, {
            agent_name: this.formData.tenKH,
            description: this.formData.moTa,
            username: this.formData.tenDN,
            agent_type: 1,
            agent_cha: this.formData.comboBoxAccKH.selected.agent_name,
            address: this.formData.diaChi,
            paper_number: this.formData.soGiayTo,
            phone_number: this.formData.soDienThoai,
            email: this.formData.email,
            status: 1,
            contract_number: this.formData.soHD,
            contract_name: this.formData.tenHD,
            contract_date: this.formData.ngayKyHD,
            contract_end_date: this.formData.ngayKT,
            contract_start_date: this.formData.ngayBD,
            adser_id: this.formData.adser_id,
            agent_id: this.formData.agent_id,
            contract_id: this.formData.agent_id,
          });
          if (res.data.status != 200) {
            this.$toast.error(
              res.data.data == null
                ? res.data.message
                : res.data.data.message + ". Liên hệ admin để được hỗ trợ"
            );
          } else {
            const message =
              res.data.data == null ? res.data.message : res.data.data.message;
            if (message == "") {
              this.$toast.info("Tạo khách hàng trực tiếp thành công.");
            } else {
              this.$toast.info(
                res.data.data == null ? res.data.message : res.data.data.message
              );
            }
          }
          await LoadThongTinKhachHang();
        } catch (err) {
          console.error(err);
          // setTimeout(() => {
          //   this.getComboboxData()
          // }, 3000)
        } finally {
          this.loading = false;
        }
      }
    },
  },
};
</script>
