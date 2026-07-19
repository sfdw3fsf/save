<template>
<b-modal id="huydonhang" class="modal-dialog" size="xl"  hide-footer hide-header
                body-class="modal-body p-0">
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title">
            <span class="icon one-notepad"></span> Thông tin đơn hàng
            </div>
            <div class="close -ap icon-close" @click="hideModalGiaoPhieu"></div>
    </div>
    <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a href="#" @click.prevent="HuyDonHang">
                                <span class="icon one-file-arrow-right1"></span>Hủy đơn hàng
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="table-content">
                            <table class="table-result table-gachle table-filter" id="table">
                                <DataGrid v-bind:columns="thongtindonhang.columns"
                                    v-bind:dataSource="thongtindonhang.list"
                                    :enable-paging-server="false"
                                    :showFilter="true"
                                    :allowPaging="true"
                                    :showColumnCheckbox="false"
                                    :enabledSelectFirstRow="true"
                                    >
                                </DataGrid>
                            </table>
                        </div>
                    </div>
                </div> 
  </div>
</b-modal>
</template>
<script>
import APIGiaoLaiPhieu from './APIGiaoLaiPhieu';
export default {
  name: "GiaoLaiPhieu",
  props: {
    thongtindonhang: "",
    donhang_id: "",
  },
  components: {
  },
  data() {
    return {
      Slected: "",
    }
  },
  
  methods: {
    hideModalGiaoPhieu: function () {
      this.$bvModal.hide('huydonhang')
    },
    HuyDonHang(){
      var pdata = {};
      pdata.vdonhang_id = this.donhang_id;
      console.log(pdata);
      APIGiaoLaiPhieu.huy_don_hang(this.axios, pdata).then((response) => {
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            if(response.data.data == 1) {
              this.$toast.success("Hủy thành công đơn hàng!");
            }
            else this.$toast.error(response.data.data);
        }
      });
    },
  }
}
</script>