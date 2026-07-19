<template>
  <div class="box-form">
    <div class="legend-title">
      <div class="pull-left">Danh sách thuê bao</div>
      <div class="pull-right">
        <div class="list-checks red normal">
          <div class="item">
            <div class="check-action">
              <input
                type="radio"
                v-model="formProps.rdoPhieu"
                class="check"
                value="0"
              />
              <span class="name">Phiếu mới</span>
            </div>
          </div>
          <div class="item">
            <div class="check-action">
              <input
                type="radio"
                v-model="formProps.rdoPhieu"
                class="check"
                value="1"
              />
              <span class="name">Phiếu trả lại</span>
            </div>
          </div>
          <div class="item" v-if="controls.rdoPhieu_Da_TH.visible">
            <div class="check-action">
              <input
                type="radio"
                v-model="formProps.rdoPhieu"
                class="check"
                value="2"
              />
              <span class="name">Phiếu đã TH</span>
            </div>
          </div>
        </div>
      </div>
      <div class="clearfix"></div>
    </div>
    
    <DataGrid v-bind:columns="[ 
                    {fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, width: 150},
                    {fieldName: 'ma_lt', headerText: 'Số ảo', allowFiltering: true}, 
                    {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true}, 
                    {fieldName: 'diachi_dau', headerText: 'Địa chỉ LĐ đầu', allowFiltering: true},                    
                    {fieldName: 'diachi_cuoi', headerText: 'Địa chỉ LĐ cuối', allowFiltering: true},   
                    {fieldName: 'daucuoi', headerText: 'Điểm', allowFiltering: true, width: 100}, 
                    {fieldName: 'ma_kv', headerText: 'Mã khu vực', allowFiltering: true, width: 100}, 
                    {fieldName: 'ten_kv', headerText: 'Tên khu vực', allowFiltering: true, width: 100}, 
                    {fieldName: 'ngay_yc', headerText: 'Ngày YC', textAlign: 'right', allowFiltering: true, format: {type: 'date', format: 'dd/MM/yyyy HH:mm:ss'},type: 'date',},
                    {fieldName: 'ngaygiao', headerText: 'Ngày giao', textAlign: 'right' , allowFiltering: true, customAttributes: {class: 'red'}}, 
                    {fieldName: 'dv_hen', headerText: 'Đơn vị hẹn', allowFiltering: true},
                    {fieldName: 'giohen_tu', headerText: 'Hẹn từ', allowFiltering: true},
                    {fieldName: 'giohen_den', headerText: 'Hẹn đến', allowFiltering: true},     
                    {fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true, width: 150},               
                    {fieldName: 'thoigian_ck', headerText: 'Thời gian cam kết', allowFiltering: true},
                    {fieldName: 'goi_kt', headerText: 'Gói KT', allowFiltering: true},
                    {fieldName: 'chitieu_tg', headerText: 'Chỉ tiêu TG', allowFiltering: true},
                    {fieldName: 'gio_conlai', headerText: 'Giờ còn lại', allowFiltering: true},
                    {fieldName: 'trangthai_in', headerText: 'Trạng thái in', allowFiltering: true},                     
                    {fieldName: 'ngay_ht', headerText: 'Ngày BG', allowFiltering: true, textAlign: 'right', format: {type: 'date', format: 'dd/MM/yyyy HH:mm:ss'},type: 'date',},
                    {fieldName: 'ngay_kh', headerText: 'Ngày KH', allowFiltering: true, textAlign: 'right'},
                    {fieldName: 'dv_giao', headerText: 'Đơn vị giao', allowFiltering: true}, 
                    {fieldName: 'ten_status', headerText: 'Trạng thái', allowFiltering: true}, 
                    {fieldName: 'lydoton_tc', headerText: 'Lý do tồn', allowFiltering: true},
                    {fieldName: 'ghichu_ton', headerText: 'Ghi chú tồn', allowFiltering: true},
                    {fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true},
                ]"
              ref="vueTableDetail"
              :showColumnCheckbox='true'
              v-bind:dataSource="formProps.table.options" 
              @rowClicked="rowClicked"
              @selectedRowChanged="rowSelected"
              :enable-paging-server="false"
              :allowPaging="true"
              :allowSelection='true'
              :multiple="false"
              :unique="'ma_tb'" 
              :enabledSelectFirstRow="false"
              :showFilter="true"> 
              
              <!-- :enabledSelectFirstRow="false" -->
    </DataGrid>
  </div>
</template>
<script>
import { mixin } from "./mixins";

// import { customPaging } from "../../../../admin/category/Promotion/components/UpdatePromotionDetail/components/plugins/customPaging";
export default {
  mixins: [mixin],

  components: {},
  props: ["formProps", "controls"],
  data: () => ({
    formatOptions: { type: "date", format: "dd/MM/yyyy" },
    customAttributes: { class: "customcss" },
    options: {
      options_dsDichVuVT: [],
    },
  }),
  watch: {},
  computed: {},
  created() {},
  mounted() {
    console.log(this.formProps.table.options);
  },
  methods: {
    rowClicked(i, data) {      
      console.log(data);
      console.log(data.ma_tb);
      this.$emit("onSelectedRow", data);      
    },
    rowSelected(data) {      
      
      this.$emit("onSelectedRow", data);

      var grid = this.$refs.vueTableDetail;
      var seletedRows = grid.getSelectedRecords();

      this.$emit("onSelectedMultiRows", seletedRows);
    },
  },
};
</script>
