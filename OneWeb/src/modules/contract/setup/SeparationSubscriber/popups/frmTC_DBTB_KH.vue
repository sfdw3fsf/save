<template>
  <ejs-dialog
    :header="'Danh sách thuê bao cùng khách hàng'"
    showCloseIcon="true"
    width="50%"
    :target="target"
    :visible="false"
    ref="frmTC_DBTB_KH"
    :allowDragging="true"
  >
    <vue-nav>
      <ul class="list">
        <li @click="chapnhan()">
          <a title="Chấp nhận">
            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp
            nhận
          </a>
        </li>
        <li @click="HT_DL()">
          <a title="Lấy TT">
            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Lấy TT
          </a>
        </li>
      </ul>
    </vue-nav>
    <div class="popup-body">
        <vue-card>
            <DataGrid
                v-bind:columns="columns"                    
                v-bind:dataSource="dataSource"
                :showColumnCheckbox="true"
                ref="grid"
                :showFilter="true"
                :enabledSelectFirstRow="false"
            />
        </vue-card>
    </div>
  </ejs-dialog>
</template>
<script>
export default {
  methods: {
    async showModal(khachhang_id = 0, thuebao_id = 0,loaitb_id = 0, hdkh_id = 0, kieu = 0) {
      this.khachhang_id = khachhang_id
      this.thuebao_id = thuebao_id
      this.loaitb_id = loaitb_id
      this.hdkh_id = hdkh_id
      this.kieu = kieu
      this.HT_DL()
      this.$refs.frmTC_DBTB_KH.show();
    },
    async HT_DL() {
      if(this.kieu == 0) {
        
        let data = await this.axios.post("/web-hopdong/tachnhap_thuebao/lay_dstb_theo_khid_loaitb_v2", {
            "vhdkh_id": this.hdkh_id,
            "vkhachhang_id": this.khachhang_id,
            "vloaitb_id": this.loaitb_id
        })
        this.dataSource= data.data.data
      } else {
          let data = await this.axios.post("/web-hopdong/tachnhap_thuebao/lay_dstb_theo_khid", {
            "vkhachhang_id": this.khachhang_id
        })
        this.dataSource= data.data.data
      }
    },
    hide() {
      this.$refs.frmTC_DBTB_KH.hide()
    },
    chapnhan() {
      this.$emit('chapnhan', this.$refs.grid.getSelectedRecords())
      this.$refs.frmTC_DBTB_KH.hide()
    }
  },
  data() {
    return {
      khachhang_id : 0, thuebao_id : 0,loaitb_id : 0, hdkh_id : 0, kieu : 0,
      item: {},
      target: ".main-wrapper",
      columns: [
          {fieldName: 'ma_tb', headerText: 'Số máy/Account', allowFiltering: true, allowSorting: false, isPrimaryKey:true, width: "auto"},
          {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: false, isPrimaryKey:true, width: "auto"},
          {fieldName: 'loaihinh_tb', headerText: 'Loại hình TB', allowFiltering: true, allowSorting: false, isPrimaryKey:true, width: "auto"}
      ],
      dataSource: []
    };
  }
};
</script>