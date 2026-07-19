<template src="./template.html"></template>

<script>
import breadcrumb from '@/components/breadcrumb'
import api from "../../../search/subscriber/UpdateIntermediateSplitPoint/api/index";
import moment from "moment";

export default {
  name: "UpdateIntermediateSplitPoint",
  components: {
    breadcrumb
  },
  data() {
    return {
      header: {
        title: 'Cập nhật điểm chia trung gian',
        list: [
          {name: 'Lập hợp đồng', link: {name: 'Ui.cards'}},
          {name: 'Lắp đặt mới', link: {name: 'Ui.cards'}},
        ]
      },

      thuebao_id: null,
      key_press: false,
      tsbtnTimKiem: false,
      btnAdd: true,
      txtMaTB: "",
      cboDichVuVT: {
        option: [],
        value: null
      },
      cboLoaihinhTB: {
        option: [],
        value: null
      },
      txtTrangThai: '',
      cboDiemChia: {
        option: [],
        value: null
      },
      current: 0,
      grvDs: [],
    }
  },
  async created() {
    await this.frmCapNhatDiemChiaTG_Load();
  },
  methods: {
    focusItem(index, data) {
      this.current = index
    },
    async grvDs_FocusedRowChanged(item) {

    },
    async frmCapNhatDiemChiaTG_Load() {
      try {
        // let dk = DichVuVienThong.TSL.ToString();
        await this.HT_DichVuVT_Combobox();
      } catch (ex) {
        this.$toast.error("Lỗi khi load dữ liệu: " + ex.message);
        // Message_Box.ShowError("Lỗi khi load dữ liệu: " + ex.Message);
      }
    },
    async HD_DS_DIEMCHIA_DAGAN() {
      try {
        const response = await api.lay_ds_diemchia_tg(this.axios, this.thuebao_id)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          this.cboDiemChia.option = data.data.p_out;
          this.cboDiemChia.option.sort((a, b) => a.ten_dv.localeCompare(b.ten_dv));
          this.grvDs = data.data.p_out_dagan;

          if (this.cboDiemChia.option.length > 0) {
            this.cboDiemChia.value = this.cboDiemChia.option[0].donvi_id
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        this.$toast.error("Lỗi khi load dữ liệu: " + e.message);
      } finally {
      }
    },
    async btnChapNhan_Click() {

    },
    async btnTracuu_Click() {
      if (this.txtMaTB !== "") {
        this.loading(true)
        this.thuebao_id = 0;
        // var ds = tcdanhba.LAY_DB_THEO_MATB(txtMaTB.Text.Trim(), 0, 0);

        // if (ds.Tables[0].Rows.Count > 0)
        // {
        //   this.txtMaTB = ds.Tables[0].Rows[0]["ma_tb"].ToString();
        //   cboDichVuVT.SelectedValue = Convert.ToInt32(ds.Tables[0].Rows[0]["dichvuvt_id"].ToString());
        //   this.thuebao_id = Convert.ToInt64(ds.Tables[0].Rows[0]["thuebao_id"].ToString());
        //   cboLoaihinhTB.SelectedValue = ds.Tables[0].Rows[0]["loaitb_id"].ToString();
        //   txtTrangThai.Text = ds.Tables[0].Rows[0]["trangthai_tb"].ToString();

        try {
          const response = await api.lay_db_theo_matb(this.axios,
            {
              "ma_tb": this.txtMaTB,
              "donvi_dl_id": 0,
              "dichvuvt_id": 0
            })
          const data = await response.data
          if (data.data.length > 0) {
            let res = data.data[0]
            this.txtMaTB = res.ma_tb
            this.cboDichVuVT.value = res.dichvuvt_id
            this.thuebao_id = res.thuebao_id
            this.cboLoaihinhTB.value = res.loaitb_id
            this.txtTrangThai = res.trangthai_tb
            await this.HD_DS_DIEMCHIA_DAGAN();
          } else {
            this.$toast.error("Không tìm thấy thông tin về thuê bao: " + this.txtMaTB +
              "\nĐề nghị kiểm tra lại Số máy/Acc, Dịch vụ VT !");
          }
        } catch (e) {
          console.log('Lấy danh sách không thành công')
        } finally {
        }


        // }
        // else
        // {
        //   Message_Box.ShowTB("Không tìm thấy thông tin về thuê bao: " + txtMaTB.Text +
        //     "\nĐề nghị kiểm tra lại Số máy/Acc, Dịch vụ VT !");
        // }
        this.loading(false)
      }
    },
    async btnDelete_ButtonClick(row) {
      console.log(row.donvi_id)
      let p_diemchia_id = row.donvi_id
      try {
        const response = await api.fn_capnhat_diemchia_tg(this.axios, {
          "p_diemchia_id": p_diemchia_id,
          "p_kieu": 2,
          "p_thuebao_id": this.thuebao_id
        })
        const data = await response.data
        console.log(response)
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        this.$toast.error("Lỗi khi load dữ liệu: " + e.message);
      } finally {
      }
      await this.HD_DS_DIEMCHIA_DAGAN();
    },
    async grcPrefix_ProcessGridKey() {

    },
    async DatatableToXml() {

    },
    async grvPrefix_ValidateRow() {

    },
    async grvPrefix_RowUpdated() {

    },
    async txtMaTB_KeyPress(e) {
      if (e.keyCode === 13) {

        if (this.txtMaTB !== "") {
          this.loading(true)
          this.thuebao_id = 0;

          try {
            const response = await api.lay_db_theo_matb(this.axios,
              {
                "ma_tb": this.txtMaTB,
                "donvi_dl_id": 0,
                "dichvuvt_id": 0
              })
            const data = await response.data
            if (data.data.length > 0) {
              let res = data.data[0]
              this.txtMaTB = res.ma_tb
              this.cboDichVuVT.value = res.dichvuvt_id
              this.thuebao_id = res.thuebao_id
              this.cboLoaihinhTB.value = res.loaitb_id
              this.txtTrangThai = res.trangthai_tb
              await this.HD_DS_DIEMCHIA_DAGAN();
            } else {
              this.$toast.error("Không tìm thấy thông tin về thuê bao: " + this.txtMaTB +
                "\nĐề nghị kiểm tra lại Số máy/Acc, Dịch vụ VT !");
            }
          } catch (e) {
            console.log('Lấy danh sách không thành công')
          } finally {
            this.loading(false)
          }
        }

      }
    },
    async cboDichVuVT_SelectedValueChanged() {
      if (this.cboDichVuVT.option.length <= 0) return;
      await this.HT_LoaiHinh_TB_Combobox(this.cboDichVuVT.value);
    },
    async btnAdd_Click() {
      if (this.cboDiemChia.value === null) return;
      if (this.cboDiemChia.value === "") return;
      //Kiêm tra đơn vị đã tồn tại hay chưa
      let dt = this.grvDs.filter(item => +item.donvi_id === +this.cboDiemChia.value);
      if (dt.length > 0) {
        this.$toast.warning("Điểm chia đã được gán rồi !");
        // Message_Box.Show("Điểm chia đã được gán rồi !");
        return;
      }
      //cập nhật điểm chia hiện tại vào CSDL

      try {
        const response = await api.fn_capnhat_diemchia_tg(this.axios, {
          "p_diemchia_id": +this.cboDiemChia.value,
          "p_kieu": 1,
          "p_thuebao_id": this.thuebao_id
        })
        const data = await response.data
        console.log(response)
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        this.$toast.error("Lỗi khi load dữ liệu: " + e.message);
      } finally {
      }

      await this.HD_DS_DIEMCHIA_DAGAN();
    },
    async HT_LoaiHinh_TB_Combobox(dichvuvt_id) {
      try {
        // cbo.option = []
        this.cboLoaihinhTB.option = [];
        const response = await api.HT_LoaiHinh_TB_Combobox(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          this.cboLoaihinhTB.option = data.data.filter(item => item.DICHVUVT_ID === dichvuvt_id);
          if (this.cboLoaihinhTB.option.length > 0) {
            this.cboLoaihinhTB.value = this.cboLoaihinhTB.option[0].LOAITB_ID
            // await this.cboDichVuVT_EditValueChanged()
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_DichVuVT_Combobox() {
      try {
        // cbo.option = []
        this.cboDichVuVT.option = [];
        const response = await api.HT_DichVuVT_Combobox(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          this.cboDichVuVT.option = data.data.filter(item => item.DICHVUVT_ID === 9);
          if (this.cboDichVuVT.option.length > 0) {
            this.cboDichVuVT.value = this.cboDichVuVT.option[0].DICHVUVT_ID
            await this.cboDichVuVT_SelectedValueChanged()
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    getColumnAction(parent) {
      return function () {
        return {
          template: {
            template: `
              <div class="center">
              <button class="btn btn-main pad3 lh14" @click="parent.btnDelete_ButtonClick(data)">
                <span class="-ap icon-close"></span>
              </button>
              </div>
            `,
            data() {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
  }
}
</script>

<style>
.btn-main {
  background: #0176FF;
  color: #fff;
}

.btn-main:hover {
  background: #035FCC;
  color: #fff;
}

.e-headercell column-selected-flag e-defaultcursor {
  width: 20px;
}
</style>
