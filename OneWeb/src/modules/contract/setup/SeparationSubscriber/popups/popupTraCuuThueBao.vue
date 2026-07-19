<template>
  <ejs-dialog
    :header="'Danh sách các thuê bao của khách hàng đang lập Hợp đồng/Phụ lục'"
    showCloseIcon="true"
    width="50%"
    :target="target"
    :position="{ X: 'center', Y: 'top' }"
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
          <a style="display:none">
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Lấy TT
          </a>
        </li>
        <li>
          <a style="display:none">
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Thoát
          </a>
        </li>
      </ul>
    </vue-nav>
    <div class="popup-body">
        <vue-card>
            <DataGrid
                ref="danhsach"
                v-bind:columns="columns"                    
                v-bind:dataSource="dataSource"
                :allowFiltering="true"
                :showColumnCheckbox="true"
                @rowSelected="focusItem"
            />
        </vue-card>
    </div>
  </ejs-dialog>
</template>
<script>
export default {
  methods: {
    async showModal(khachhang_id, dichvuvt_id, ma_gd, tinhtrang = "") {
      let rs = await this.axios.post("web-ccdv/tien_trinh_bao_hong/lay_ds_db_hdtb_theo_khid_magd", {
        "vkhachhang_id": khachhang_id,
        "vdichvuvt_id": dichvuvt_id,
        "vma_gd": ma_gd
      })
      this.dataSource= rs.data.data.map(item => ({
        ...item,
        tinhtrang : item.thuoc_pl == 1? tinhtrang : ""
      }))
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
      tinhtrang: "",
      item: {},
      target: ".main-wrapper",
      columns: [
          {fieldName: 'ma_tt', headerText: 'Mã TT', allowFiltering: true, allowSorting: false, isPrimaryKey:true, width: 60},
          {fieldName: 'ma_tb', headerText: 'Account', allowFiltering: true, allowSorting: false, isPrimaryKey:true, width: 60},
          {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: false, isPrimaryKey:true, width: 60},
          {fieldName: 'loaihinh_tb', headerText: 'Loại hình TB', allowFiltering: true, allowSorting: false, isPrimaryKey:true, width: 60},
          {fieldName: 'trangthai_tb', headerText: 'Trạng thái', allowFiltering: true, allowSorting: false, isPrimaryKey:true, width: 60},
          {fieldName: 'tinhtrang', headerText: 'Tình trạng', allowFiltering: true, allowSorting: false, isPrimaryKey:true, width: 60}
      ],
      dataSource: []
    };
  }
};
</script>