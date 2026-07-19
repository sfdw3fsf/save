<template>
<b-modal id="dsdonhang" class="modal-dialog" size="xl"  hide-footer hide-header
                body-class="modal-body p-0">
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title">
            <span class="icon one-notepad"></span> Danh sách đơn hàng
            </div>
            <div class="close -ap icon-close" @click="hideModalGiaoPhieu"></div>
    </div>
    <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a href="#" @click.prevent="Lay_DS_don_hang">
                                <span class="icon one-search"></span>Tra Cứu
                            </a>
                        </li>
                        <li>
                            <a href="#" @click.prevent="ChapNhan">
                                <span class="icon one-file-arrow-right1"></span>Chấp Nhận
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="popup-body">
                    <div class="box-form">
            <div class="legend-title">Thông tin tìm kiếm</div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                                <div class="key w100" style="width: 130px!important;">Trạng thái đơn hàng</div>
                                <div class="value">
                                    <div class="select-custom">
                                        <select name="" id="" class="form-control" v-model="tt_donhang.value">
                                            <option v-for="dv in tt_donhang.list" v-bind:value="dv.id">{{ dv.ten }}</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                   <div class="info-row">
                                <div class="key w100" style="width: 130px!important;">Loại thanh toán</div>
                                <div class="value">
                                    <div class="select-custom">
                                        <select name="" id="" class="form-control" v-model="loai_tt.value">
                                            <option v-for="dv in loai_tt.list" v-bind:value="dv.id">{{ dv.thanhtoan }}</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Từ Ngày</div>
                        <div class="value">
                            <div class="input-icon-right">
                                <ejs-datepicker
                                    :format="config.dateConfig.format"
                                    :showClearButton="false"
                                    v-model = "TuNgay"                
                                />
                            </div>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w100">Đến Ngày</div>
                        <div class="value">
                            <div class="input-icon-right">
                                <ejs-datepicker
                                    :format="config.dateConfig.format"
                                    :showClearButton="false"
                                    v-model = "DenNgay"
                                />
                            </div>
                        </div>
                    </div>
                </div>
            </div> 
        </div>
                    <div class="box-form">
                        <div class="table-content">
                                <DataGrid v-bind:columns="dsdonhang.columns"
                                    v-bind:dataSource="dsdonhang.list"
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
import API from './APIGiaoLaiPhieu'
import moment from 'moment'
export default {
  name: "DSDonHang",
//   props: {
//       tt_donhang: "",
//       loai_tt: "",
//   },
  mounted () {
    //load ds trạng thái đơn hàng
    API.lay_ds_don_hang(this.axios, {
            "vtrangthaidh_id": 3,
            "vloaithanhtoan": 4,
            "vloai": 2,
            "vtungay": "01/01/2020",
            "vdenngay": "01/01/2022"
        }).then((response) => {
    if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.tt_donhang.list = response.data.data;
        console.log(this.tt_donhang.list);
        }
      }
    );
    // //load ds loại thanh toán
    API.lay_ds_don_hang(this.axios, {
            "vtrangthaidh_id": 3,
            "vloaithanhtoan": 4,
            "vloai": 1,
            "vtungay": "01/01/2020",
            "vdenngay": "01/01/2022"
        }).then((response) => {
    if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.loai_tt.list = response.data.data;
        }
      }
    );
  },
  data() {
    return {
        Slected: "",
        TuNgay: moment().startOf('month').format("DD/MM/YYYY"),
        DenNgay: moment().startOf('month').format("DD/MM/YYYY"),
        tt_donhang:{
          list:[],
          value: "-1",
          isEnabled:false,
        },
        loai_tt:{
          list:[],
          value: "-1",
          isEnabled:false,
        },
        dsdonhang: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
          columns: [
          { fieldName: 'ma_dh', headerText: 'Mã ĐH', allowFiltering: true, freeze:'Left'},
          { fieldName: 'ten_sp', headerText: 'Tên sản phẩm', allowFiltering: true, freeze:'Left' },
          { fieldName: 'ngay_cn', headerText: 'Ngày cập nhật', allowFiltering: true },
          { fieldName: 'trangthai', headerText: 'Trạng thái đơn hàng', allowFiltering: true},
          { fieldName: 'hinhthuc', headerText: 'Hình thức thanh toán', allowFiltering: true },
          { fieldName: 'tongtien', headerText: 'Tổng tiền', allowFiltering: true },                      
        ],
      },
      config: {
                dateConfig: {
                    value: new Date(),
                    format: "dd/MM/yyyy",
                    max: new Date()
                }
            },
    }
  },
  
  methods: {
    hideModalGiaoPhieu: function () {
      this.$bvModal.hide('dsdonhang')
    },
    Lay_DS_don_hang(){
      var pdata = {};
      pdata.vtrangthaidh_id = this.tt_donhang.value;
      pdata.vloaithanhtoan = this.loai_tt.value;
      pdata.vloai = 3;
      pdata.vtungay = moment(this.TuNgay).format('DD/MM/YYYY');
      pdata.vdenngay = moment(this.DenNgay).format('DD/MM/YYYY');
      console.log(pdata);
      API.lay_ds_don_hang(this.axios, pdata).then((response) => {
        if(response && response.data && response.data.data) {
            this.dsdonhang.list = response.data.data;
            console.log(response.data.data);
        }
        if(response.data.data == "" || response.data.data == null){
            this.$toast.error('Không tìm thấy thông tin đơn hàng!');
        }
      });
    },
    format_date(value){
         if (value) {
           return moment(String(value)).format('DD/MM/YYYY')
          }
    },
    ChapNhan(){
      this.$bvModal.hide('dsdonhang')
    },
  }
}
</script>