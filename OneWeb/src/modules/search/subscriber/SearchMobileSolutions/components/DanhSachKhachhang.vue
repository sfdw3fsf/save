<template>
  <div>
    <div>
      <b-form @submit.prevent="">
        <div class="row mb-2 mt-2">
          <div class="col-md-6">
          <div class="row">
            <b-form-input id="searchtext" type="search" placeholder="Tìm kiếm khách hàng" style="display: inline-bock; width:80%;" v-model="filters.dk1"></b-form-input>
            <b-input-group-append style="display: inline-bock;" class="col-md-2">
              <b-button :disabled="!filters.dk1" @click="filters.dk1 = ''">Clear</b-button>
            </b-input-group-append>
            </div>
          </div>
          <div class="col-md-4">
            <div class="row">
              <label for="status" class="col-md-3">Trạng thái</label>
              <b-form-select
                class="col-md-9"
                id="status"
                :options="[
                  { text: 'Tất cả', value: 0 },
                  { text: 'Còn lưu lượng', value: 1 },
                  { text: 'Hết lưu lượng', value: 2 },
                  { text: 'Đã hủy', value: 7 }
                ]"
                :value="0"
                v-model="filters.trangthai"
              ></b-form-select>
            </div>
          </div>
          <div class="col-md-2">
            <b-button variant="primary">Tìm kiếm</b-button>
          </div>
        </div>
      </b-form>
    </div>

   

    <!-- Main table element -->
    <b-table :items="items" :fields="fields" :current-page="currentPage" :per-page="perPage" :filter="filter" :filter-included-fields="filterOn" :sort-by.sync="sortBy" :sort-desc.sync="sortDesc" :sort-direction="sortDirection" stacked="md" show-empty small @filtered="onFiltered">
      <template #cell(status)="row"> <b-badge :variant="row.item.status === 1 ? 'success' : 'secondary'">{{row.item.status === 1 ? 'Đã kích hoạt' : 'Chưa kích hoạt'}}</b-badge></template>
      <template #cell(data)="row">
        <b-progress :max="row.item.ll_da_mua" height="2rem">
          <b-progress-bar :value="row.item.tongll_da_share">
          </b-progress-bar>
        </b-progress>  
        <span><strong style="font-size:12px">{{ row.item.tongll_da_share.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") }} / {{ row.item.ll_da_mua.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") }}</strong></span>
      </template>
      <template #cell(actions)="row">
        
        <b-button size="sm" @click="row.toggleDetails"> {{ row.detailsShowing ? 'Ẩn' : 'Hiện' }} Chi tiết </b-button>
        <b-button  variant="outline-primary" size="sm" class="mr-1">
          <router-link :to="`/search/SearchMobileSolutions/khach_hang/${row.item.age}`">Lịch sử chi sẻ</router-link>
        </b-button>
      </template>

      <template #row-details="row">
        <b-card>
          <ul>
            <li v-for="(value, key) in row.item" :key="key">{{ getFieldName(key) }}: {{ value }}</li>
          </ul>
        </b-card>
      </template>
    </b-table>

    <!-- Info modal -->
    <b-modal :id="infoModal.id" :title="infoModal.title" ok-only @hide="resetInfoModal">
      <pre>{{ infoModal.content }}</pre>
    </b-modal>

    <div class="row">
      <b-col sm="5" md="6" class="my-1 ">
        <b-form-group label="Số bản ghi một trang" label-for="per-page-select" label-cols-sm="6" label-cols-md="4" label-cols-lg="3" label-align-sm="right" label-size="sm" class="mb-0">
          <b-form-select id="per-page-select" class="col-md-2" v-model="perPage" :options="pageOptions" size="sm"></b-form-select>
        </b-form-group>
      </b-col>

      <b-col sm="7" md="6" class="my-1">
        <b-pagination v-model="currentPage" :total-rows="totalRows" :per-page="perPage" align="fill" size="sm" class="my-0"></b-pagination>
      </b-col>
    </div>

  </div>
</template>

<script>
import api from '../api.js'

export default {
  computed: {
   
  },
  data() {
    return {
      filters: {
        dk1: '',
        trangthai: ''
      },
      fields: [
        { key: 'ma_tt', label: 'Mã thanh toán', sortable: true, sortDirection: 'desc' },
        { key: 'ma_tb', label: 'Mã thuê bao', sortable: true, sortDirection: 'desc' },
        { key: 'ten_tb', label: 'Tên khách hàng', sortable: true, sortDirection: 'desc' },
        { key: 'mst', label: 'Mã số thuế', sortable: true, sortDirection: 'desc' },
        { key: 'loai_ll', label: 'Loại lưu lượng', sortable: true, sortDirection: 'desc' },
        { key: 'ten_goi_cuoc', label: 'Gói cước', sortable: true, sortDirection: 'desc' },
        { key: 'ngay_bd', label: 'Ngày sử dụng', sortable: true, sortDirection: 'desc' },
        { key: 'status', label: 'Trạng thái kích hoạt', sortable: true, sortDirection: 'desc' },
        { key: 'data', label: 'Tình trạng lưu lượng', sortable: true, sortDirection: 'desc' },

        { key: 'actions', label: 'Actions' }
      ],
      columns: [
        { key: 'll_da_mua', label: 'Lưu lượng ví đã mua' },
        { key: 'ten_tb', label: 'Tên khách hàng' },
        { key: 'tongll_da_share', label: 'Tổng lưu lượng đã share' },
        { key: 'ma_tt', label: 'Mã thanh toán' },
        { key: 'loai_ll', label: 'Loại lưu lượng' },
        { key: 'ngay_kt', label: 'Ngày kết thúc' },
        { key: 'doitac', label: 'Đối tác kích hoạt' },
        { key: 'ten_kh', label: 'Tên khách hàng' },
        { key: 'tongll_con_lai', label: 'Tổng lưu lượng còn lại' },
        { key: 'mst', label: 'Mã số thuế' },
        { key: 'ngay_bd', label: 'Ngày bắt đầu' },
        { key: 'thoigian', label: 'Thời gian cập nhật' },
        { key: 'ma_goi_cuoc', label: 'Mã gói cước' },
        { key: 'ma_tb', label: 'Mã thuê bao' },
        { key: 'ngay_kh', label: 'Ngày kích hoạt' },
        { key: 'ten_goi_cuoc', label: 'Tên gói cước' },
        { key: 'sdt_kich_hoat', label: 'Số điện thọai' },
        { key: 'status', label: 'Trạng thái' },
      ],
      totalRows: 1000000,
      currentPage: 1,
      perPage: 20,
      pageOptions: [20, 50, 100, { value: 99999999999999999, text: 'Vô hạn' }],
      sortBy: '',
      sortDesc: false,
      sortDirection: 'asc',
      filter: null,
      filterOn: [],
      infoModal: {
        id: 'info-modal',
        title: '',
        content: ''
      }
    }
  }, 
  mounted() {
    this.loadDulieu();
  },
  methods: {
    getFieldName(key) {
      const item = this.columns.find(x => x.key === key) ;
      if(item) return item.label;
      return key;
    },

    LowerCasePropertyList(obj) {
      if(!obj) return [];
      return obj.map( function( item ){
          for(var key in item){
              var upper = key.toLowerCase();
              // check if it already wasn't uppercase
              if( upper !== key ){
                  item[ upper ] = item[key];
                  delete item[key];
              }
          }
          return item;
      });
    },
    async loadDulieu() {
      let req = {
          ma_thanh_toan: "",
          ma_thue_bao: "",
          tu_ngay: "",
          denngay: "",
          trans_id: ""
      }
      const res = await api.thongtin_vi_luuluong(this.axios, req);
      if(res && res.data.error_code != "BSS-00000500") {
        this.items = this.LowerCasePropertyList(res.data.data.danhsach_vi);
        this.totalRows = this.items.length;
      }

    }
  },
}
</script>
