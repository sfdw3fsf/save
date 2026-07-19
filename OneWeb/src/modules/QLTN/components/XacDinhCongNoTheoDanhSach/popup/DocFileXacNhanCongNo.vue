<template>
  <b-modal
    id="popup-docfilexacnhancongno"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
          <div class="title"><span class="icon one-notepad"></span>Đọc file xác nhận công nợ</div>
          <div
            class="close -ap icon-close"
            data-dismiss="modal"
            @click="$bvModal.hide('popup-docfilexacnhancongno')"
          ></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận (F5)
                </a>
            </li>
            <li>
                <a href="#">
                    <span class="icon one-excel"></span> Xuất Excel
                </a>
            </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
            <div class="info-row">
                <div class="key">Đường dẫn</div>
                <div class="value">
                    <div class="input-more-button">
                        <button class="btn">
                            <span class="-ap icon-more_horiz"></span>
                        </button>
                        <input type="text" class="form-control ">
                    </div>
                </div>
                <div class="value w80 nowrap">
                    <button class="btn btn-second normal">Tải biểu mẫu</button>
                </div>
            </div>
            <div class="legend-title mart20">
                <div class="title pull-left">Danh sách công nợ</div>
                <div class="pull-right red">00:00:00 - Thông báo</div>
                <div class="clearfix"></div>
            </div>
            <div class="nav tabs tab-over">
                <a href="#tabDS" class="active" data-toggle="tab">
                    <span class="icon f20 nc-icon-glyph design_bullet-list-67"></span> Danh sách
                </a>
                <a href="#tabDSLoi" data-toggle="tab">
                    <span class="icon f20 one-alert text-warning"></span> Lỗi
                </a>
            </div>
            <div class="tab-content">
                <div class="tab-pane active" id="tabDS">
                    <div class="table-content">
                        <table class="table-result">
                            <thead class="center">
                                <th class="w20"></th>
                                <th>STT</th>
                                <th>Mã TT</th>
                                <th>Ngày xác t...</th>
                                <th>Ngày thu hồi</th>
                                <th>Kết quả</th>
                                <th>Lý do KXN</th>
                                <th>Nguyên nhân CL</th>
                                <th>Ghi chú</th>
                            </thead>
                            <tbody>
                                <tr class="highlight2">
                                    <td class="w20 center">
                                        <span class="fa fa-play text-main"></span>
                                    </td>
                                    <td class="highlight3 selected"></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr >
                                    <td class="w20 center">
                                    </td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr >
                                    <td class="w20 center">
                                    </td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr >
                                    <td class="w20 center">
                                    </td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr >
                                    <td class="w20 center">
                                    </td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
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
                <div class="tab-pane" id="tabDSLoi">
                    <div class="table-content">
                        <table class="table-result">
                            <thead class="center">
                                <th class="w20"></th>
                                <th>Mã TT</th>
                                <th>Ngày xác t...</th>
                                <th>Ngày thu hồi</th>
                                <th>Kết quả</th>
                                <th>Lý do KXN</th>
                                <th>Số dư</th>
                                <th>Nguyên nhân CL</th>
                                <th>Ghi chú</th>
                                <th>Lý do lỗi</th>
                            </thead>
                            <tbody>
                                <tr class="highlight2">
                                    <td class="w20 center">
                                        <span class="fa fa-play text-main"></span>
                                    </td>
                                    <td class="highlight3 selected"></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr >
                                    <td class="w20 center">
                                    </td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr >
                                    <td class="w20 center">
                                    </td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr >
                                    <td class="w20 center">
                                    </td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr >
                                    <td class="w20 center">
                                    </td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
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
            </div>
        </div>
    </div>
    </div>
  </b-modal>
</template>
<script>
export default {
  name: 'DocFileXacNhanCongNo',
  data () {
    return {
      fields: [],
      items: []
    }
  },
  methods: {
    detailsClassFn (item, rowType) {
      // return 'fw6'
      return ''
    }
  }
}
</script>
