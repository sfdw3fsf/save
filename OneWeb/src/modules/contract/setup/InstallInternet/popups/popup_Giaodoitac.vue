<template>
   <b-modal
    id="popupGiaodoitac"
    title="Giao đối tác"
    modal-class="suakenhthu"
    header-class="popup-header"
    content-class="tracuu-content"
    body-class="none-padding tracuu-body"
    size="xl"
    hide-footer
  >
    <div class="modal-content popup-box">
      <div class="list-actions-top">
        <ul class="list">
          <li @click="tsbtnCapNhat_Click">
            <a> <span class="icon one-save"></span>Xác nhận </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="row">
              <div class="info-row">
                <div class="key w130">Đối tác</div>
                <div class="value">
                  <SelectExt ref="cboKieu_HD" v-model="cboDoitac.Value" :allowFiltering="true" dataTextField="NAME"
                      dataValueField="ID" :dataSource="cboDoitac.DataSource"></SelectExt>
                </div>
              </div>
          </div>
          <div class="row">
            <div class="info-row">
              <div class="key w130">Nội dung giao</div>
              <div class="value">
                <textarea
                  v-model="txtGhiChu"
                  type="text"
                  class="form-control fw6"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import moment from "moment";
export default {
  data() {
    return {
      txtGhiChu: "",
      phieu_id: "",
      hdtb_id: "",
      doitac_id: "",
      cboDoitac: {
        DataSource: [],
        Value: null
      }
    }
  },
  methods: {
    async tsbtnCapNhat_Click() {
      try {
        let data = this.axios.post("web-thicong/thicong-internet/giaophieu_tc_doitac", {
          "vhdtb_id" : this.hdtb_id,
          "vphieu_id" : this.phieu_id,
          "vnhanvien_id" :this.$root.token.getNhanVienID(),
          "vnoidung_giao" :this.txtGhiChu,
          "vdoitac_id" :this.cboDoitac.Value,
          "vmay_cn" : await this.$root.token.getMachine(),
          "vngay_cn" :moment(new Date()).format('MM/DD/YYYY HH:mm:ss'),
          "vnguoi_cn": await this.$root.token.getUserName()
        })
        if(data.data.data == "OK" || data.data.data == "1")
          this.$toast.success("Giao đối tác thành công")
        else
          this.$toast.error("Giao đối tác thất bại" + data.data.data)
      }catch (e) {
        this.$toast.error("Giao đối tác thất bại")
      }
    },
    async getDsDonvi() {
      try {
        this.loading(true)
        let data = await this.axios.post("web-quantri/danhmuc/donvi/theo_loai", {
          "loai_donvi_id": 582
        })
        let rs = data.data.data
        this.cboDoitac.DataSource = [...new Map(rs.map((item) => [item["NAME"], item])).values()]
        this.cboDoitac.Value = this.cboDoitac.DataSource[0].ID
        this.loading(false)
      } catch (e) {
        this.cboDoitac.DataSource = []
        this.loading(false)
      }

    },
    show(hdtb_id, phieu_id) {
      this.hdtb_id = hdtb_id
      this.phieu_id = phieu_id
      this.getDsDonvi()
      this.$bvModal.show("popupGiaodoitac")
    }
  }
}
</script>
