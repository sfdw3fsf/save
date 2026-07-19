<template>
  <ejs-dialog
    :header="'Tìm kiếm nhân viên thu cước'"
    showCloseIcon="true"
    width="50%"
    :target="target"
    :visible="false"
    ref="tracuu_popup"
  >
    <vue-nav>
      <ul class="list">
        <li @click="chapnhan()">
          <a>
            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp
            nhận
          </a>
        </li>
        <li>
          <a>
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Thoát
          </a>
        </li>
      </ul>
    </vue-nav>
    <div class="popup-body">
        <vue-card>
            <DataGrid
                v-bind:columns="columns"                    
                v-bind:dataSource="dataSource"
                @rowSelected="focusItem"
            />
        </vue-card>
    </div>
  </ejs-dialog>
</template>
<script>
export default {
  methods: {
    async showModal(khachhang_id, dichvuvt_id, ma_gd) {
      let data = await this.axios.post("web-thicong/thuebao/lay_ds_db_hdtb_theo_khid_magd", {
        "khachhang_id": khachhang_id,
        "dichvuvt_id": dichvuvt_id,
        "ma_gd": ma_gd
      })
      this.dataSource= data.data.data
      this.$refs.tracuu_popup.show();
    },
    chapnhan() {
      this.$emit('chapnhan', this.item)
      this.$refs.tracuu_popup.hide()
    },
    focusItem(item) {
      this.item = item.data
    }
  },
  data() {
    return {
      item: {},
      target: ".main-wrapper",
      columns: [
          {fieldName: 'ma_tt', headerText: 'Mã TT', allowFiltering: false, allowSorting: false, isPrimaryKey:true, width: 60},
          {fieldName: 'ma_tb', headerText: 'Account', allowFiltering: false, allowSorting: false, isPrimaryKey:true, width: 60},
          {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: false, allowSorting: false, isPrimaryKey:true, width: 60},
          {fieldName: 'loaihinh_tb', headerText: 'Loại hình TB', allowFiltering: false, allowSorting: false, isPrimaryKey:true, width: 60},
          {fieldName: 'trangthai_tb', headerText: 'Trạng thái', allowFiltering: false, allowSorting: false, isPrimaryKey:true, width: 60},
          {fieldName: '', headerText: 'Tình trạng', allowFiltering: false, allowSorting: false, isPrimaryKey:true, width: 60}
      ],
      dataSource: []
    };
  }
};
</script>