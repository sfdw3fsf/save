<template>
<b-modal id="giaolaiphieu" class="modal-dialog" size="xl"  hide-footer hide-header
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
                            <a href="#" @click.prevent="GiaoPhieu">
                                <span class="icon one-file-arrow-right1"></span>Giao phiếu
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
import APIGiaoLaiPhieu from './APIGiaoLaiPhieu'
export default {
  name: "GiaoPhieuTC",
  props: {
    thongtindonhang: "",
    donhang_id: "",
  },
  components: {
  },
  mounted () {

  },
  data() {
    return {
      mota: "",
    }
  },
  
  methods: {
    hideModalGiaoPhieu: function () {
      this.$bvModal.hide('giaolaiphieu')
    },
    GiaoPhieu(){
      var pdata = {};
      pdata.vdonhang_id = this.donhang_id;
      pdata.vkenhthu_id = "1";
      APIGiaoLaiPhieu.giao_phieu_tc(this.axios, pdata).then((response) => {
        if(response && response.data && response.data.data) {
            if(response.data.data[0].thongbao == "Ok!"){
              this.$toast.success(response.data.data[0].thongbao);
            }
            else this.$toast.error(response.data.data[0].thongbao);
        }
      });
    },
    LayTTMoTa(){
      APIGiaoLaiPhieu.lay_mo_ta_sp(this.axios, this.phieu_yc.donhang_id).then((response) => {
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.dsgoi = response.data.data;
        }
      });
    }
  }
}
</script>