<template>
  <div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a @click="tsbtnGhiLai_Click">
            <span class="icon one-save"></span>Ghi lại
          </a>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <div class="box-form">
        <div class="legend-title">Thông tin sản phẩm</div>
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="info-row">
              <div class="key">
                Lý do
              </div>
              <div class="value">
                <textarea v-model='lydo' class="form-control" style="height: 104px;resize: none;" ></textarea>
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>

</template>

<script>
import api from '../../API'
export default {
  name: 'TuChoiDuyetSanPham',

  data:function(){
    return{
      lydo:"",
      tag:null,
      spdv_id:null,
    }
  },
  methods:{
    init_data(tag,spdv_id){
      this.tag = tag;
      this.spdv_id = spdv_id;
    },
    async tsbtnGhiLai_Click(){
      console.log("Tag popup: ", this.tag);
      if(this.tag == 2){
        let response = await api.fn_tuchoi_sanpham_cty(this.axios, { spdv_id: this.spdv_id, lydo:this.lydo })
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          this.$toast.success('Từ chối duyệt sản phẩm TCT thành công')

        } else {
          this.$toast.error(response.data.message)
        }
      }
      if(this.tag == 3){
        let response = await api.fn_tuchoi_sanpham_td(this.axios, { spdv_id: this.spdv_id, lydo:this.lydo })
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          this.$toast.success('Từ chối duyệt sản phẩm TĐ thành công')

        } else {
          this.$toast.error(response.data.message)
        }
      }
      this.$emit("accept", {});
    }
  },
}
</script>

<style scoped>

</style>
