<template>
<b-modal id="biendong" class="modal-dialog" size="xl"  hide-footer hide-header
                body-class="modal-body p-0">
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title">
            <span class="icon one-notepad"></span> Danh sách biến động
            </div>
            <div class="close -ap icon-close" @click="hideModalGiaoPhieu"></div>
    </div>
    <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a href="#" @click.prevent="ChapNhan">
                                <span class="icon one-file-arrow-right1"></span>Chấp Nhận
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="table-content">
                                <DataGrid v-bind:columns="dsbiendong.columns"
                                    v-bind:dataSource="dsbiendong.list"
                                    :enable-paging-server="false"
                                    :showFilter="true"
                                    :allowPaging="true"
                                    :showColumnCheckbox="false"
                                    :enabledSelectFirstRow="true"
                                    >
                                </DataGrid>
                        </div>
                    </div>
                </div> 
  </div>
</b-modal>
</template>
<script>
import API from './../SearchOrderDetailAPI';
export default {
  name: "BienDong",
  props: {
    dsbiendong: ""
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
      this.$bvModal.hide('biendong')
    },
    ChapNhan(){
      this.$bvModal.hide('biendong')
    },
  }
}
</script>