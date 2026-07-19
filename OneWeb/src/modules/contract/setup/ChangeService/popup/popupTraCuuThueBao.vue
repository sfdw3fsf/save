<template>
  <ejs-dialog
    :header="tieude"
    showCloseIcon="true"
    :enableResize='true'  
    :allowDragging='true'
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
                @rowSelected="focusItem"
                :multiple="false" 
            />
        </vue-card>
    </div>
  </ejs-dialog>
</template>
<script>
export default {
  methods: {
    async showModal(khachhang_id, dichvuvt_id, ma_gd, tinhtrang = "",tieude) {
      // let rs = await this.axios.post("web-ccdv/tien_trinh_bao_hong/lay_ds_db_hdtb_theo_khid_magd", {
      //   "vkhachhang_id": khachhang_id,
      //   "vdichvuvt_id": dichvuvt_id,
      //   "vma_gd": ma_gd
      // })
      this.tieude = tieude
      let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/chuyendich/lay_ds_db_hdtb_theo_khid_magd?khachhang_id=' + khachhang_id + '&ma_gd=' + ma_gd)
      this.dataSource= rs.data.map(item => ({
        ...item,
        tinhtrang : item.THUOC_PL == 1? tinhtrang : ""
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
      tieude:"",
      tinhtrang: "",
      item: {},
      target: ".main-wrapper",
      columns: [
          {fieldName: 'MA_TT', headerText: 'Mã TT', allowFiltering: true, allowSorting: false, isPrimaryKey:true, width: 60},
          {fieldName: 'MA_TB', headerText: 'Account', allowFiltering: true, allowSorting: false, isPrimaryKey:true, width: 60},
          {fieldName: 'TEN_TB', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: false, isPrimaryKey:true, width: 60},
          {fieldName: 'LOAIHINH_TB', headerText: 'Loại hình TB', allowFiltering: true, allowSorting: false, isPrimaryKey:true, width: 60},
          {fieldName: 'TRANGTHAI_TB', headerText: 'Trạng thái', allowFiltering: true, allowSorting: false, isPrimaryKey:true, width: 60},
          {fieldName: 'tinhtrang', headerText: 'Tình trạng', allowFiltering: true, allowSorting: false, isPrimaryKey:true, width: 60}
      ],
      dataSource: []
    };
  }
};
</script>