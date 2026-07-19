<template>
  <div id="chonTinh">
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="click_chapnhan"> <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span>Chấp nhận </a>
        </li>
      </ul>
    </div>
    <div class="wrap-info-row">
      <div class="box-col box-form">
        <div class="table-content" style="height: 90%;">
          <DataGrid ref="gridDStinh" v-bind:columns="danh_sach_tinh.header" v-bind:dataSource="danh_sach_tinh.list"
          :enable-paging-server="false" :allowPaging="false" :showFilter="true" @selectedRowChanged="onSelectedRow"  :showColumnCheckbox="true"
          @checkBoxChange="checkBoxChange" :enabledSelectFirstRow="false" :allowGrouping="true"  @selectedItemsChanged="getSelectedItem"></DataGrid>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.formatted-content {
  white-space: pre-wrap; /* Preserve line breaks */
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #f9f9f9;
  font-size: 14px;
  color: #333;
}
.key {
  font-weight: bold;
  margin-bottom: 5px;
}
.tabbed-content {
  margin-left: 20px; /* Indent the detailed content */
}
</style>

<script>
import MultiServicePackageAPI from "../MultiServicePackageAPI";
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue';
import KDatePicker from '@/components/kylq_components/KDatePicker.vue';
import ActionTop from '@/components/kylq_components/ActionTop.vue';
export default {
  name: 'chonTinh',
  components: {
    ActionTop,
    KDatePicker,
    KRequiredMarker
  },
  props: ["dulieu"],
  data() {
    return {
      selected_list:'',
      countTinh:'',
      danh_sach_tinh : {
        list: [],
        header: [ 
          {fieldName: 'TENTINH', headerText: 'Danh sách tỉnh', allowFiltering: true, width: 'auto'}, 
        ],
        value: {},
        isEnabled: true,
      },
    };
  },
  mounted() {
    this.getDanhSachTinh();
  },
  methods: {
    getSelectedItem: function (data) {
        this.selected_list = data
        console.log('hàng tỉnh được chọn',this.selected_list)
        this.countTinh = data[0].PHANVUNG_ID
    },
    async click_chapnhan(){
      console.log('Thiện check copy tỉnh khác',this.dulieu.goiid,this.selected_list)
      if (this.countTinh === ''){
        this.$root.$toast.error('Chọn tỉnh cần sao chép')
        return
      }
      try{
        const params = this.selected_list.map(item => (item.PHANVUNG_ID));
        const dataInput = {
          vphanvung_id:21,
          vgoi_id:this.dulieu.goiid,
          vds_phanvung_id: params.join(',') 
        };
        const response = await MultiServicePackageAPI.nhanban_goi_dadv_tinhkhac(this.axios, JSON.stringify(dataInput))
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          const resultData = response.data.data && response.data.data[0];
          if (resultData && resultData.ketqua === 0) {
            this.$toast.success('Copy gói đa dịch vụ cho các tỉnh thành công');
            this.$emit('form-close',params )
          } else {
            this.$toast.error(resultData ? resultData.error : 'Có lỗi xảy ra');
          }
        } else {
          this.$toast.error('Có lỗi xảy ra trong quá trình thực hiện');
        }
      }catch(error){
        this.$toast.error('Xảy ra lỗi khi copy gói đa dịch vụ cho các tỉnh')
      }finally{
        this.loading(false);
      }
    },
    async getDanhSachTinh () {
      console.log('Dữ liệu popup gói tư vấn nhận được:', this.dulieu.goiid);
      try {
        this.loading(true)
        const response = await MultiServicePackageAPI.lay_ds_tinh(this.axios)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.danh_sach_tinh.list = response.data.data
        }
      } catch (e) {
        this.$toast('Không lấy được danh sách tỉnh')
      } finally {
        this.loading(false)
      }
    },
    checkBoxChange(item){
      console.log('a Khánh qq',item)
    },
    async onSelectedRow(item){
      if(item){
        this.selectedRow = true
      }else{
        this.selectedRow = false
      }
    },
  }
};
</script>