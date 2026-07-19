<template src="./template.html"></template>

<script>
import breadcrumb from '@/components/breadcrumb'
import api from "../UpdateUserPasswordMYTV/api";

export default {
  components: {
    breadcrumb
  },
  name: "UpdateUserPasswordMYTV",
  data() {
    return {
      header: {
        title: 'Cập nhật mật khẩu user',
        list: [
          {name: 'Lập hợp đồng', link: {name: 'Ui.cards'}},
          {
            name: 'Lắp đặt mới',
            link: {name: 'Ui.buttons'}
          }
        ]
      },
      btnCapNhat: true,
      btnHuyBo: true,
      txtPassWord: '',
      txtUserName: '',
      txtMaTB: '',
      grvDSUser: [],
      cboTTTK: {
        option: [],
        value: 0,
      },
      rowSelected: [],

      vkhachhang_id: null,
      vuser_id: null,
      vma_tb: null,
      current: 0,
      ma_tinh: this.$root.token.getMaTinh()
    }
  },
  mounted() {
    this.cboTTTK.option = [
      {index: 0, name: 'Mã khách hàng'},
      {index: 1, name: 'Mã Thanh toán'},
      {index: 2, name: 'Số máy/Account'}
    ]
  },
  methods: {
    focusItem(index, data) {
      this.current = index
    },
    setIndex(index) {
      // this.$refs.dataGrid.flagSelectedRowIndexes = [];
      // this.$refs.dataGrid.flagSelectedRowIndexes.push(index);
      this.$refs.dataGrid.setCurrentSelectedRow(+index)
    },
    async btnHuyBo_Click() {
      await this.HT_CT();
    },
    async HT_TT() {
      let vds_quyen_chuquan = "";
      if (this.$root.token.getProperty('ds_quyen_chuquan') === "null") {
        vds_quyen_chuquan = null;
      }
      this.grvDSUser = []
      if (this.txtMaTB === null || this.txtMaTB.toString().trim() === "") return;
      try {
        const response = await api.lay_ds_user_theo_chuquan(this.axios, {
          "vdk": this.cboTTTK.value,
          "vma_tb": this.txtMaTB,
          "vds_quyen_chuquan": vds_quyen_chuquan
        })
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          this.grvDSUser = data.data.sort((a, b) => a.user_id - b.user_id);
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }

      this.vma_tb = this.txtMaTB.toString().trim();

    },
    async btnCapNhat_Click() {

      try {
        const response1 = await api.dongbo_sla(this.axios, {
          "ACCOUNT": "string",
          "ACCOUNTS": [
            {
              "ACCOUNT": "",
              "BANDWIDTH": 0,
              "DEVICETYPE": "",
              "DISTRICTpublic": "",
              "IP": "",
              "PORT": "",
              "PROVINCEpublic": "",
              "RACK": "",
              "SERVICESPEC": "",
              "SERVICETYPE": "",
              "SHELF": "",
              "SLID": "",
              "SLOT": "",
              "SYSTEM": "",
              "TECHTYPE": "",
              "VCI": "",
              "VNPTCODE": "",
              "VPI": "",
              "WARDpublic": ""
            }
          ],
          "ADDRESS": "",
          "BANDWIDTH": 0,
          "CODE": this.rowSelected[0].ma_kh,
          "DEVICETYPE": "",
          "EMAIL": "",
          "EMAILSENDER": 0,
          "IP": "",
          "MOBILE": "",
          "NAME": this.rowSelected[0].ten_kh,
          "PASSWORD": this.txtPassWord,
          "PORT": "",
          "RACK": "",
          "SERVICESPEC": "",
          "SERVICETYPE": "",
          "SHELF": "",
          "SLID": "",
          "SLOT": "",
          "SMS": 0,
          "SYSTEM": "",
          "TECHTYPE": "",
          "TYPE": "",
          "USERNAME": this.rowSelected[0].user_name,
          "VCI": "",
          "VNPTCODE": this.ma_tinh,
          "VPI": ""
        })
        console.log(response1)
        const data1 = await response1.data
        let kq = ""
        if (data1.data.code.toString() !== "200") {
          this.$toast.error("Có lỗi đồng bộ thông tin User lên hệ thống SLA KHTCDN: " + kq);
          return;
        } else {
          this.$toast.success("Đồng bộ thông tin User lên hệ thống SLA KHTCDN thành công!");
          try {
            const response = await api.capnhat_db_user(this.axios, {
              "vuser_id": this.vuser_id,
              "vpass_word": await this.Encrypt(this.txtPassWord.toString().trim())
            })
            console.log(response)
            const data = await response.data
            if (data.data.code.toString() !== "200") {
              kq = data.data.message
              this.$toast.error(kq);
              return;
            } else {
              this.$toast.success("Cập nhật thành công");
            }
            await this.HT_TT();
          } catch (e) {
            // this.$toast.error("Lỗi khi cập nhật user: " + e.message);
            console.log('Lỗi /web-quantri/capnhat-matkhau/capnhat_db_user')
          } finally {
            this.setIndex(this.current)
          }
        }
      } catch (e) {
        this.$toast.error("Lỗi khi cập nhật user: " + e.message);
        console.log('Lỗi /tichhop/sla/UpdateAccountInfo')
      } finally {
      }

    },
    async Encrypt(plainText) {
      return plainText;
    },
    async txtMaTB_ButtonClick() {
      await this.HT_TT();
      this.setIndex(this.current)
    },
    async txtMaTB_KeyPress() {
      this.txtUserName = "";
      this.txtPassWord = "";
      await this.HT_TT();
      this.setIndex(2)
    },
    async HT_CT() {
      if (this.rowSelected.length <= 0) return;
      // DataTable dt = grcUser.DataSource as DataTable;
      // int index = grvDSUser.FocusedRowHandle;
      this.txtUserName = this.rowSelected[0].user_name;
      this.txtPassWord = this.rowSelected[0].pass_word;
      this.vuser_id = this.rowSelected[0].user_id;
    },
    async grvDSUser_FocusedRowChanged(item) {
      this.rowSelected = item;
      await this.HT_CT();
    }
  }
}
</script>

<style scoped>

</style>
