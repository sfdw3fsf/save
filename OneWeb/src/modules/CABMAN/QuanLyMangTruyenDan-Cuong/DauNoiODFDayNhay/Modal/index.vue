<template>
  <b-modal
    v-model="modalShow"
    size="xl"
    no-close-on-backdrop
    hide-footer
    hide-header
    hide-header-close >
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title"><span class="icon one-notepad"></span> Đấu nối ODF</div>
            <div class="close -ap icon-close" data-dismiss="modal" @click="closeModal"></div>
        </div>
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a href="#">
                        <span class="icon one-connect"></span> Đấu nối
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon one-broken-cable"></span> Đấu tắt
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon one-file-download"></span> Xuất DL
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon one-clipart-download"></span> Xuất GĐ
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon one-book1"></span> Hướng dẫn
                    </a>
                </li>
        
            </ul>
        
        </div>
            <div class="popup-body">
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="box-form">
                            <div class="legend-title">Đấu nối mặt trước</div>
                            <div class="nav tabs tab-over">
                                <a href="#tab1" data-toggle="tab" class="minw0 ">Mã kết nối</a>
                                <a href="#tab2" data-toggle="tab" class="minw0 active">Dây nhảy</a>
                                <a href="#tab3" data-toggle="tab" class="minw0 ">Thiết bị</a>
                                <a href="#tab5" data-toggle="tab" class="minw0 ">Thuê bao truyền số liệu</a>
                            </div>
                            <div class="tab-content" style="height: 100px;">
                                <div class="tab-pane " id="tab1">
                                    <div class="info-row">
                                        <div class="key w80">Loại thiết bị</div>
                                        <div class="value">
                                            <div class="select-custom">
                                                <select name="" id="" class="form-control">
                                                    <option value=""></option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w80">Thiết bị</div>
                                        <div class="value">
                                            <div class="select-custom">
                                                <select name="" id="" class="form-control">
                                                    <option value=""></option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="tab-pane active" id="tab2">
                                    <div class="info-row">
                                        <div class="key w80">Dây nhảy</div>
                                        <div class="value">
                                            <div class="select-custom">
                                                <select name="" id="" class="form-control">
                                                    <option value=""></option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w80"></div>
                                        <div class="value">
                                            <button class="btn btn-second">
                                                <span class="-ap icon-info_outline"></span> Xem TT
                                            </button>
                                            <button class="btn btn-second">
                                                <span class="one-trash"></span> Xóa
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="box-form">
                            <div class="legend-title">Đấu nối mặt sau</div>
                            <div class="nav tabs tab-over">
                                <a href="#tab1" data-toggle="tab" class="active">Cáp gốc/cáp truyền dẫn</a>
                                
                            </div>
                            <div class="tab-content" style="height: 100px;">
                                <div class="info-row">
                                    <div class="value padl0">
                                        <div class="select-custom">
                                            <select name="" id="" class="form-control">
                                                <option value=""></option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="box-move-select-table bss-1826-odf-layout2">
                    <div class="box-col box-form -right">
                        <div class="legend-title">Sợi</div>
                        <div class="list-checks-v" style="height: 300px;overflow:auto;">
                            <div class="item marb10">
                                <div class="check-action">
                                    <input type="checkbox" class="check">
                                    <span class="name">13</span>
                                </div>
                            </div>
                            <div class="item marb10">
                                <div class="check-action">
                                    <input type="checkbox" class="check">
                                    <span class="name">13</span>
                                </div>
                            </div>
                            <div class="item marb10">
                                <div class="check-action">
                                    <input type="checkbox" class="check">
                                    <span class="name">13</span>
                                </div>
                            </div>
                            <div class="item marb10">
                                <div class="check-action">
                                    <input type="checkbox" class="check">
                                    <span class="name">13</span>
                                </div>
                            </div>
                            <div class="item marb10">
                                <div class="check-action">
                                    <input type="checkbox" class="check">
                                    <span class="name">13</span>
                                </div>
                            </div>
                            <div class="item marb10">
                                <div class="check-action">
                                    <input type="checkbox" class="check">
                                    <span class="name">13</span>
                                </div>
                            </div>
                            <div class="item marb10">
                                <div class="check-action">
                                    <input type="checkbox" class="check">
                                    <span class="name">13</span>
                                </div>
                            </div>
                            <div class="item marb10">
                                <div class="check-action">
                                    <input type="checkbox" class="check">
                                    <span class="name">13</span>
                                </div>
                            </div>
                            <div class="item marb10">
                                <div class="check-action">
                                    <input type="checkbox" class="check">
                                    <span class="name">13</span>
                                </div>
                            </div>
                            <div class="item marb10">
                                <div class="check-action">
                                    <input type="checkbox" class="check">
                                    <span class="name">13</span>
                                </div>
                            </div>
                            <div class="item marb10">
                                <div class="check-action">
                                    <input type="checkbox" class="check">
                                    <span class="name">13</span>
                                </div>
                            </div>
                            <div class="item marb10">
                                <div class="check-action">
                                    <input type="checkbox" class="check">
                                    <span class="name">13</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="actions">
                        <button class="btn">
                            <span class="-ap icon-chevron-thin-right"></span>
                        </button>
                        <button class="btn">
                            <span class="-ap icon-chevron-thin-left"></span>
                        </button>
                    </div>
                    <div class="box-col box-form -mid" >
                        <div class="legend-title">
                            <div class="pull-left">Danh sách đấu nối trên: 0 - NSTL/L01</div>
                            <div class="pull-right normal">
                                <div class="check-action">
                                    <input type="checkbox" class="check">
                                    <span class="name">Hiển thị ghi chú trên ODF trạm đối diện</span>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="table-content" style="height: 250px;">
                            <table class="table-result table-gachle">
                                <thead class="center">
                                    <tr>
                                        <th class="w20"></th>
                                        <th colspan="2">Đầu vào</th>
                                        <th colspan="2">Mặt trước</th>
                                        <th>Thông tin cổng</th>
                                        <th colspan="2">Mặt sau</th>
                                        <th colspan="2">Đầu ra</th>
                                        <th class="w40">Tra cứu</th>
                                    </tr>
                                    <tr>
                                        <th class="w20"></th>
                                        <th>Dây nhảy / thuê bao</th>
                                        <th>Sợi</th>
                                        <th class="w20">Chọn</th>
                                        <th>Vị trí</th>
                                        <th>Ghi chú</th>
                                        <th>Vị trí</th>
                                        <th class="w20">Chọn</th>
                                        <th>Cáp ra</th>
                                        <th>Sợi</th>
                                        <th class="w40">Tra cứu</th>
                                    </tr>
                                    
                                </thead>
                                <tbody>
                                    <tr class="highlight">
                                        <td class="w20 center">
                                            <span class="fa fa-play text-main"></span>
                                        </td>
                                        <td class="selected highlight1">
                                            <div class="pull-left"></div>
                                            <div class="pull-right">
                                                <button class="btn btn-second lh12 pad2">
                                                    <span class="-ap icon-more_horiz"></span>
                                                </button>
                                            </div>
                                            <div class="clearfix"></div>
                                        </td>
                                        <td></td>
                                        <td class="w20 center">
                                            <div class="check-action">
                                                <input type="checkbox" class="check">
                                                <span class="name"></span>
                                            </div>
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td class="w20 center">
                                            <div class="check-action">
                                                <input type="checkbox" class="check">
                                                <span class="name"></span>
                                            </div>
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td class="w20 center">
                                            <button class="btn btn-second lh12 pad2">
                                                <span class="-ap icon-more_horiz"></span>
                                            </button>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="w20 center">
                                        </td>
                                        <td>
                                            
                                        </td>
                                        <td></td>
                                        <td class="w20 center">
                                            <div class="check-action">
                                                <input type="checkbox" class="check">
                                                <span class="name"></span>
                                            </div>
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td class="w20 center">
                                            <div class="check-action">
                                                <input type="checkbox" class="check">
                                                <span class="name"></span>
                                            </div>
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td class="w20 center">
                                            <button class="btn btn-second lh12 pad2">
                                                <span class="-ap icon-more_horiz"></span>
                                            </button>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="w20 center">
                                        </td>
                                        <td>
                                            
                                        </td>
                                        <td></td>
                                        <td class="w20 center">
                                            <div class="check-action">
                                                <input type="checkbox" class="check">
                                                <span class="name"></span>
                                            </div>
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td class="w20 center">
                                            <div class="check-action">
                                                <input type="checkbox" class="check">
                                                <span class="name"></span>
                                            </div>
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td class="w20 center">
                                            <button class="btn btn-second lh12 pad2">
                                                <span class="-ap icon-more_horiz"></span>
                                            </button>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="w20 center">
                                        </td>
                                        <td>
                                            
                                        </td>
                                        <td></td>
                                        <td class="w20 center">
                                            <div class="check-action">
                                                <input type="checkbox" class="check">
                                                <span class="name"></span>
                                            </div>
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td class="w20 center">
                                            <div class="check-action">
                                                <input type="checkbox" class="check">
                                                <span class="name"></span>
                                            </div>
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td class="w20 center">
                                            <button class="btn btn-second lh12 pad2">
                                                <span class="-ap icon-more_horiz"></span>
                                            </button>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="w20 center">
                                        </td>
                                        <td>
                                            
                                        </td>
                                        <td></td>
                                        <td class="w20 center">
                                            <div class="check-action">
                                                <input type="checkbox" class="check">
                                                <span class="name"></span>
                                            </div>
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td class="w20 center">
                                            <div class="check-action">
                                                <input type="checkbox" class="check">
                                                <span class="name"></span>
                                            </div>
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td class="w20 center">
                                            <button class="btn btn-second lh12 pad2">
                                                <span class="-ap icon-more_horiz"></span>
                                            </button>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="w20 center">
                                        </td>
                                        <td>
                                            
                                        </td>
                                        <td></td>
                                        <td class="w20 center">
                                            <div class="check-action">
                                                <input type="checkbox" class="check">
                                                <span class="name"></span>
                                            </div>
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td class="w20 center">
                                            <div class="check-action">
                                                <input type="checkbox" class="check">
                                                <span class="name"></span>
                                            </div>
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td class="w20 center">
                                            <button class="btn btn-second lh12 pad2">
                                                <span class="-ap icon-more_horiz"></span>
                                            </button>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="w20 center">
                                        </td>
                                        <td>
                                            
                                        </td>
                                        <td></td>
                                        <td class="w20 center">
                                            <div class="check-action">
                                                <input type="checkbox" class="check">
                                                <span class="name"></span>
                                            </div>
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td class="w20 center">
                                            <div class="check-action">
                                                <input type="checkbox" class="check">
                                                <span class="name"></span>
                                            </div>
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td class="w20 center">
                                            <button class="btn btn-second lh12 pad2">
                                                <span class="-ap icon-more_horiz"></span>
                                            </button>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="page-bottom">
                            <div class="pull-left">
                                <div class="tc-pagination">
                                    <a href="#" class="btn  one-arrow-left-forward"></a>
                                    <a href="#" class="btn fa fa-angle-double-left"></a>
                                    <a href="#" class="btn fa fa-angle-left"></a>
                                    <span class="text">Bản ghi</span>
                                    <div class="dropdown inline">
                                        <div class="fw6 input-number" data-toggle="dropdown">
                                            <span class="text-main">10</span>/1033
                                        </div>
                                        <div class="dropdown-menu">
                                            <a href="#" class="dropdown-item">10</a>
                                            <a href="#" class="dropdown-item">20</a>
                                        </div>
                                    </div>
                                    <a href="#" class="btn fa fa-angle-right"></a>
                                    <a href="#" class="btn fa fa-angle-double-right"></a>
                                    <a href="#" class="btn one-arrow-right-forward"></a>
                                </div>
                            </div>
                            <div class="pull-right tab-change-layout mart5">
                                <a class="btn btn-second pad3 f16 active">
                                    <span class="one-layout1"></span>
                                </a>
                                <a class="btn btn-second pad3 f16 ">
                                    <span class="one-layout2"></span>
                                </a>
                                <a class="btn btn-second pad3 f16">
                                    <span class="one-layout3"></span>
                                </a>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
            </div>
        </div>
    </div>
  </b-modal>
</template>

<script>
export default {
  components: {},
  name: 'Modal',
  props: {
    modalShow: Boolean,
    data: Object
  },

  computed: {
    // getMarkers () {
    //   return this.markers
    // },
    // radiusObj () {
    //   return this.radius
    // },
  },

  data () {
    return {
    //   dsBlock: [],
    //   cicles: {},
    //   ketquaTK: '',
    //   bankinhTK: 1000,
    //   viDo: -1,
    //   kinhDo: -1,
    //   validated: 1,
    //   diachi: '',
    //   dataSelected: {
    //     diachi_id: null,
    //     tinh_id: null,
    //     quan_id: null,
    //     phuong_id: null,
    //     pho_id: null,
    //     ap_id: null,
    //     khu_id: null,
    //     dac_diem_id: null,
    //     so_nha: ''
    //   },
    //   markers: [],
    //   radius: [],
    //   mapType: 'ROADMAP'
    }
  },
  watch: {
    // mapType (val) {
    //   this.$refs.map.setmapType(val)
    // },
  },
  methods: {
    closeModal: function () {
      this.$emit('closeModal', this.modalShow)
    },
  },
  async mounted () {
  },
  async created() {
    
  }
}
</script>
