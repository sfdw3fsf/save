<template>
  <b-modal
    id="popup-ChiTietTienKM"
    size="xl"
    title="Chi tiết tiền khoản mục"
    hide-footer
    body-class="modal-body p-0"
  >
    <div class="modal-content popup-box">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a @click="tsbtnCapNhan_Click">
              <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div
            v-for="(row, i) in pnlDynamicControl"
            :key="i"
            class="row"
          >
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">{{row.lbltongtien.Text}}</div>
                <div class="value col-sm-5">
                  <input
                    type="text"
                    @change="ChangeTongTien($event,i)"
                    :readOnly="row.txttongtien.ReadOnly"
                    :ref="'txttongtien' + i"
                    :value="row.txttongtien.Text"
                    class="form-control tright"
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w100">{{row.lbltien.Text}}</div>
                <div class="value">
                  <input
                    @change="ChangeTien($event, i)"
                    type="text"
                    :ref="'txt' + i"
                    :value="row.txt.Text"
                    :readOnly="row.txt.ReadOnly"
                    class="form-control tright"
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w100">{{row.lblvat.Text}}</div>
                <div class="value">
                  <input
                    type="text"
                    :ref="'txtvat' + i"
                    :value="row.txtvat.Text"
                    :readOnly="row.txtvat.ReadOnly"
                    class="form-control tright"
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
var rs
export default {
  data() {
    return {
      ds_tien_caidat: [],   // tiền hòa mạng + cài đặt
      chapnhan: false,
      loaihd_id: 0,
      cuoc_sodep: "-1",
      bangts: {},
      dtControl: [],
      m_dsTienKM: null,
      tchopdong: null,
      lbltongtien: null,
      txttongtien: null,
      lbl: null,
      txt: null,
      lblvat: null,
      txtvat: null,
      KHONGXOA_TONGTIEN_TDTD: false,
      pnlDynamicControl: {}
    }
  },
  methods: {
    async HT_GIAODIEN_KMTT() {
      return new Promise(async (resolve, reject) => {
        try {
          rs = await this.axios.post("web-hopdong/lapdatmoi/lay_khoanmuc_tt_theo_loaihd", {
            "vloaihd_id": this.loaihd_id
          })
          this.dtControl = rs.data.data//bangts.GetDataSql(str);

          this.pnlDynamicControl = {}

          for (let i = 0; i < this.dtControl.length; i++) {
            // tổng tiền
            let item = {}
            let lbltongtien = {}

            lbltongtien.Type = "label"
            lbltongtien.Text = this.dtControl[i]["tentat_kmtt"]

            let txttongtien = {}
            txttongtien.Type = "textbox"
            txttongtien.ref = "txttongtien" + this.dtControl[i]["khoanmuctt_id"];

            if (this.KHONGXOA_TONGTIEN_TDTD == 1) {
              if (this.loaihd_id == LoaiHopDong.THAY_DOI_TOCDO_ADSL &&
                this.dtControl[i]["khoanmuctt_id"] == KHOANMUC_TT.KMTT_DATCOC) {
                txttongtien.ReadOnly = true;
              }
            }

            let lbltien = {}

            lbltien.Type = "label"
            lbltien.ref = "lbltien" + this.dtControl[i]["khoanmuctt_id"]
            lbltien.Text = "Tiền"
            // Tiền

            let txt = {}
            txt.Type = "textbox"
            txt.Text = "0";
            txt.ref = "txt" + this.dtControl[i]["khoanmuctt_id"];
            //txt[i].ReadOnly = true;
            // Vat

            let lblvat = {}
            lblvat.Type = "label"
            lblvat.ref = "lbltien" + this.dtControl[i]["khoanmuctt_id"]
            lblvat.Text = "Vat"

            let txtvat = {}
            txtvat.Type = "textbox"
            txtvat.Text = "0";
            txtvat.ref = "txtvat" + this.dtControl[i]["khoanmuctt_id"];

            txtvat.ReadOnly = true;

            if (this.dtControl[i]["flag"] == "0") {
              txttongtien.ReadOnly = true;
              txt.ReadOnly = true;
            }

            lbltongtien.width = "10%"
            item.lbltongtien = lbltongtien
            txttongtien.width = "25%"
            item.txttongtien = txttongtien
            lbltien.width = "5%"
            item.lbltien = lbltien
            txt.width = "25%"
            item.txt = txt
            lblvat.width = "5%"
            item.lblvat = lblvat
            txtvat.width = "20%"
            item.txtvat = txtvat
            this.$set(this.pnlDynamicControl,i,item)
          }
          resolve(true)
        }
        catch (err) {
          resolve(true)
          this.$toast.error("Có lỗi : " + err.message);
        }
      })
    }

    , HT_TIEN_KHOANMUC() {
      try {
        for (let i in this.dtControl) {
          for (let j = 0; j < this.ds_tien_caidat.length; j++) {
            if (this.dtControl[i]["khoanmuctt_id"] == this.ds_tien_caidat[j]["khoanmuctt_id"]) {
              this.$set(this.pnlDynamicControl[i].txt, 'Text', this.ds_tien_caidat[j]["tien"])
              this.$set(this.pnlDynamicControl[i].txtvat, 'Text', this.ds_tien_caidat[j]["vat"])
              this.$set(this.pnlDynamicControl[i].txttongtien, 'Text', (Number(this.ds_tien_caidat[j]["tien"]) + Number(this.ds_tien_caidat[j]["vat"])))
            }
          }
        }
      }
      catch (err) {
        console.log(err)
        this.$toast.error("Có lỗi : " + err.message);
      }
    }
    , frmCT_Tien_KM(vloaihd_id, in_ds_TienCatDat) {
      this.ds_tien_caidat = in_ds_TienCatDat;
      console.log(in_ds_TienCatDat)
      this.loaihd_id = vloaihd_id;
      this.$bvModal.show("popup-ChiTietTienKM")
      this.frmCT_Tien_KM_Load()
    }

    , async frmCT_Tien_KM_Load() {


      rs = await this.axios.post(
        "web-hopdong/thanhly/sp_lay_ds_thamso_md",
        { kieu_id: 0 }
      );
      var ds_kt1 = rs.data.data

      if (ds_kt1.length > 0) {
        let str_vtemp = "KHONGXOA_TONGTIEN_TDTD";
        let det = ds_kt1.filter(x => x["ma_ts"] == str_vtemp);

        if (det.length > 0) {
          if (det[0]["ten_ts"] == "1")
            this.KHONGXOA_TONGTIEN_TDTD = 1;
        }
      }

      // hoangpkn : 28/11/2018. Sửa lại tên table KHOANMUC --> danhsach do Form đặt mới Hiếu remove ds_tien_khoanmuc.Tables.Remove("danhsach"); ( cboLoaihinhTB_SelectedValueChanged)
      //KhoiTao_Dataset_TienKM("KHOANMUC");
      await this.HT_GIAODIEN_KMTT();
      this.HT_TIEN_KHOANMUC();
    },
    formatPrice(value) {
      let val = (value / 1).toFixed(0).replace(",", ".");
      return val.replace(/B(?=(d{3})+(?!d))/g, ",");
    }
    ,ChangeTongTien($event, i) {
      let value = $event.target.value
      this.$set(this.pnlDynamicControl[i].txttongtien,'Text',  (parseFloat(value)))
      this.$set(this.pnlDynamicControl[i].txt,'Text',  (parseFloat(value) * 10 / 11).toFixed(2))
      this.$set(this.pnlDynamicControl[i].txtvat,'Text',  (parseFloat(value) / 11).toFixed(2))
    },
    ChangeTien($event,i) { 
      let value = $event.target.value
      this.$set(this.pnlDynamicControl[i].txt,'Text',  (parseFloat(value)))
      this.$set(this.pnlDynamicControl[i].txttongtien,'Text',  (parseFloat(value) * 11 / 10).toFixed(2))
      this.$set(this.pnlDynamicControl[i].txtvat,'Text',  (parseFloat(value) / 10).toFixed(2))

      txtvat.Text = (Convert.ToDecimal(s) / 10).ToString(Format.FM_TIEN_VND);
      txttongtien.Text = (Convert.ToDecimal(s) * 11 / 10).ToString(Format.FM_TIEN_VND)
    }

    , tsbtnCapNhan_Click() {
      if (this.cuoc_sodep != "-1") {
        for (let i in this.dtControl) {
          if (this.dtControl[i]["khoanmuctt_id"] == "167") //Cước SD Đầu Số Dễ Nhớ (6 Tháng - Đóng Theo PA2)
          {
            if ((Number((this.dtControl[i].txt.Text)) / 6) != this.cuoc_sodep) {
              this.$toast.error("Khoản mục cước SD Đầu Số Dễ Nhớ (6 Tháng - Đóng Theo PA2) phải bằng cước số đẹp * 6");
              return;
            }
          }
        }

      }
      let arr = []
      for (let i in this.dtControl) {
          arr.push({
            khoanmuctt_id : this.dtControl[i]["khoanmuctt_id"],
            tien: this.pnlDynamicControl[i].txt.Text,
            vat:  this.pnlDynamicControl[i].txtvat.Text
          });
      }
      console.log(11111111111)
      console.log(arr)
      this.$emit("closeDialog", arr)
      this.$bvModal.hide("popup-ChiTietTienKM");
    }
  }
}
</script>
