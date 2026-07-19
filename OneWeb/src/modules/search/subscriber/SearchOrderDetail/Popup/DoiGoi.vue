<template>
<b-modal id="doigoi" class="modal-dialog" size="xl"  hide-footer hide-header
                body-class="modal-body p-0">
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title">
            <span class="icon one-notepad"></span> Đổi gói
            </div>
            <div class="close -ap icon-close" @click="hideModalDoiGoi"></div>
    </div>
    <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a href="#">
                                <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp nhận
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="popup-body">
                    <div class="row">
                        <div class="col-sm-7 col-12">
                            <div class="box-form">
                                <div class="legend-title">Thông tin khách hàng</div>
                                <div class="info-row">
                                    <div class="key">Mã KH</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="thongtinkhachhang[0].ma_kh">
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key">Tên khách hàng</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="thongtinkhachhang[0].ten_kh">
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key">Địa chỉ KH</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="thongtinkhachhang[0].diachi_kh">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key">Loại giấy tờ</div>
                                            <div class="value">
                                                <input type="text" class="form-control" v-model="thongtinkhachhang[0].loai_gt">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key">Số giấy tờ</div>
                                            <div class="value">
                                                <input type="text" class="form-control" v-model="thongtinkhachhang[0].so_gt">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key">Ngày cấp</div>
                                            <div class="value">
                                                <input type="text" class="form-control" v-model="thongtinkhachhang[0].ngaycap">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key">Nơi cấp</div>
                                            <div class="value">
                                                <input type="text" class="form-control" v-model="thongtinkhachhang[0].noicap">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key">Ngày hết hạn</div>
                                            <div class="value">
                                                <input type="text" class="form-control" v-model="thongtinkhachhang[0].ngayhethan_gt">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key">Ngày sinh</div>
                                            <div class="value">
                                                <input type="text" class="form-control" v-model="thongtinkhachhang[0].ngay_sn">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key">Giới tính</div>
                                    <div class="value">
                                        <div class="list-checks mart7">
                                            <div class="item">
                                                <div class="check-action">
                                                    <input type="radio" class="check" :checked="thongtinkhachhang[0].gioitinh == 0">
                                                    <span class="name">Không</span>
                                                </div>
                                            </div>
                                            <div class="item">
                                                <div class="check-action">
                                                    <input type="radio" class="check" :checked="thongtinkhachhang[0].gioitinh == 1">
                                                    <span class="name">Nam</span>
                                                </div>
                                            </div>
                                            <div class="item">
                                                <div class="check-action">
                                                    <input type="radio" class="check" :checked="thongtinkhachhang[0].gioitinh == 2">
                                                    <span class="name">Nữ</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-5 col-12">
                            <div class="box-form" style="min-height: 330px;">
                                <div class="legend-title">Thông tin gói hiện tại</div>
                                <div class="info-row">
                                    <div class="key">Mã đơn hàng</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="thongtinsp[0].ma_dh">
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key">Tên sản phẩm</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="thongtinsp[0].ten_sp">
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key">Tổng tiền</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="thongtinsp[0].tongtien">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-12">
                            <div class="box-form">
                                <div class="legend-title">Danh sách các gói phù hợp</div>
                                <div class="list-checks" style="height: 200px;overflow: auto;">
                                    <div class="item marb10">
                                        <div class="check-action" v-for="goi in dsgoi">
                                            <input type="radio" :value="goi.sanpham_id" v-model="dsgoi.sanpham_id">
                                            <span class="name">{{ goi.ten_sp }}</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-2 col-12">
                            <div class="box-form">
                                <div class="legend-title">Thông tin gói</div>
                                <div class="article" style="height: 200px;overflow: auto;">
                                    <span class="fw6">HomeTV</span> 2 là gói cước tích hợp dịch vụ Internet cáp quang và truyền hình của VNPT Hải Phòng dành cho khách hàng là cá
                                    nhân/hộ gia đình, với mức cước hấp dẫn và nhiều ưu đãi: <br/>
                                    <ul>
                                        <li>
                                            <p class="block"> Internet cáp quang tốc độ cao 50Mbps công nghệ GPON thế hệ mới, đáp ứng đầy đủ các tiện ích cần
                                            </p>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="box-form">
                                <div class="legend-title">Thông tin gói</div>
                                <div class="table-content" style="height: 160px;">
                                    <table class="table-result table-gachle">
                                        <DataGrid v-bind:columns="thongtingoi.columns"
                                            v-bind:dataSource="thongtingoi.list"
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
                </div>
    </div>    
</b-modal>
</template>
<script>
export default {
  name: "DoiGoi",
  props: {
      thongtinkhachhang: "",
      thongtinsp: "",
      dsgoi: "",
    //   donhang_id: "",
  },
  components: {
  },
  data() {
    return {
        thongtingoi: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
          columns: [
          { fieldName: 'ma_tb', headerText: 'Mã Thanh Toán', allowFiltering: true},
          { fieldName: 'matb_tn', headerText: 'Tên Khách Hàng', allowFiltering: true },
          { fieldName: '', headerText: 'Địa Chỉ', allowFiltering: true },                      
        ],
      },
    }
  },
  
  methods: {
    hideModalDoiGoi: function () {
      this.$bvModal.hide('doigoi')
    },
    
    }
}
</script>