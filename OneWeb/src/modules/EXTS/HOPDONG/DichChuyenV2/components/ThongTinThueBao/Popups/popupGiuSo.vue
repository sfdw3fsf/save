<template>
<div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>Giữ số</div>
        <div class="close -ap icon-close hidden" data-dismiss="modal">
        </div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="tsbtnHuy_Click">
                    <span class="icon nc-icon-glyph ui-2_ban_bold"></span> Xoá
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="closeForm">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Thoát
                </a>
            </li>
        </ul>
    </div>
    <div class="popup-body">
        <div class="row">
            <div class="col-12">
                <div class="box-form">
                    <DataGrid
                        v-bind:columns="ds.cols"
                        v-bind:dataSource="ds.list" :showFilter="false" :showColumnCheckbox="true"
                        :dataKeyField="ds.keycol" :defaultColumnCheckboxChecked="false" :panelDataHeight="200" @selectedItemsChanged="selectedItemsChanged">
                    </DataGrid>
                </div>
            </div>
        </div>
    </div>
</div>
</template>
<style scoped>

</style>
<script>
export default {
  name: 'popupGiuSo',
  props: [ 'dulieu' ],
  watch: {
  },
  data () {
    return {
      ds: {
        list: [],
        cols: [
          {fieldName: 'SOMAY', headerText: 'Số máy', allowFiltering: true, allowSorting: true}
        ],
        keycol: 'SOMAY',
        value: {},
        valuearr: []
      },
      btnThoat: false,
      tieude: 'popup',
      tieudebang: 'datapopup'
    }
  },
  mounted () {
    // this.ds.list = this.dulieu.ds;
    // if(this.ds.list && this.ds.list.length>0) this.ds.value = this.ds.list[0];
    // this.ds.cols = this.dulieu.cols;
    // this.ds.keycol = this.dulieu.keycol;
    // if(this.dulieu.allowExit) this.btnThoat = this.dulieu.allowExit;
    // if(this.ds.list && this.ds.list.length>0) this.ds.value = this.ds.list[0];
    // if(this.dulieu.tieude) this.tieude = this.dulieu.tieude;
    // if(this.dulieu.tieudebang) this.tieudebang = this.dulieu.tieudebang;
    this.HienThiDanhSach()
  },
  computed: {
  },
  methods: {
    closeForm () {
      this.$emit('form-close', this.ds.value)
    },
    terminateForm () {
      this.$emit('form-close', null)
    },
    rowChanged (idx, item) {
      this.ds.value = item
    },
    async HienThiDanhSach () {
      var rs = await this.$root.context.post('/web-hopdong/lapdatmoi/sp_lay_ds_giuso', {
        'dichvuvt_id': this.dulieu.dichvuvt_id,
        'nguoidung_id': this.dulieu.nguoidung_id
      })
      if (rs && rs.data && rs.data.length > 0) {
        this.ds.list = rs.data
      }
    },
    selectedItemsChanged (item) {
      this.ds.valuearr = items
    },
    async tsbtnHuy_Click () {
      try {
        var rs = await this.$root.context.post('/web-hopdong/lapdatmoi/sp_capnhat_huygiuso', {
          'dichvuvt_id': this.dulieu.dichvuvt_id,
          'js_somay': JSON.stringify(this.ds.valuearr.map(x => ({'SOMAY': x})))
        })
        if (rs && rs.data && rs['error'] == 200) {
          this.$toast.success(rs['message'])
          this.HienThiDanhSach()
        } else this.$toast.error(rs.message + (rs.message_detail ? ': ' + rs.message_detail : ''))
      } catch (ex) { this.$toast.error('Có lỗi trong quá trình giữ số. Lỗi: ' + ex) }
    }
  }
}
</script>
