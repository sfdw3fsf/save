<template>
  <b-modal
    ref="dongBoTTModal"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Đồng bộ trạng thái danh bạ VNP
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="hideModal"
        ></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#"> <span class="icon one-chart-area"></span>Lấy DL </a>
          </li>
          <li>
            <a href="#"> <span class="icon one-sync1"></span>Đồng bộ </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form padt7 padb7">
          <div class="row">
            <div class="col-sm-8 col-12">
              <div class="title mart7 fw6">
                ĐỒNG BỘ TRẠNG THÁI DANH BẠ VINAPHONE VỀ TỈNH
              </div>
            </div>
            <div class="col-sm-2 col-12">
              <div class="info-row marb0">
                <div class="key w30 nowrap">Trạng thái</div>
                <div class="value">
                  <div class="select-custom">
                    <select name="" id="" class="form-control">
                      <option value=""></option>
                    </select>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-2 col-12">
              <div class="info-row marb0">
                <div class="key w30 nowrap">Từ ngày</div>
                <div class="value">
                  <div class="input-icon-right">
                    <input type="text" class="form-control" />
                    <span class="icon one-calendar"></span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Thông tin cập nhật</div>
          <div class="row">
            <div class="col-sm-8 col-12">
              <div class="info-row">
                <div class="key w100">
                  <div class="check-action">
                    <input type="checkbox" class="check" />
                    <span class="name">Nhóm hủy</span>
                  </div>
                </div>
                <div class="value">
                  <div class="select-custom">
                    <select name="" id="" class="form-control">
                      <option value=""></option>
                    </select>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w100">Lý do hủy</div>
                <div class="value">
                  <div class="select-custom">
                    <select name="" id="" class="form-control">
                      <option value=""></option>
                    </select>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w100">Ghi chú</div>
            <div class="value">
              <input type="text" class="form-control" />
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="table-content">
            <table class="table-result table-gachle">
              <thead class="center">
                <tr>
                  <th class="w20"></th>
                  <th class="w20">
                    <div class="check-action">
                      <input type="checkbox" class="check" />
                      <span class="name"></span>
                    </div>
                  </th>
                  <th>Số máy</th>
                  <th>Trạng thái</th>
                  <th>Datesync</th>
                  <th>Tên TB</th>
                  <th>Địa chỉ TB</th>
                  <th>Ghi chú</th>
                  <th>Nhóm lý do hủy</th>
                  <th>Lý do hủy</th>
                  <th>User Neo</th>
                  <th>Người đồng bộ</th>
                  <th>Ngày đồng bộ</th>
                  <th>Đơn vị đồng bộ</th>
                </tr>
                <tr>
                  <th class="w20"></th>
                  <th class="w20">
                    <div class="check-action">
                      <input type="checkbox" disabled="" class="check" />
                      <span class="name"></span>
                    </div>
                  </th>
                  <th>
                    <div class="input-icon-right">
                      <input type="text" class="form-control" />
                      <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                  </th>
                  <th>
                    <div class="input-icon-right">
                      <input type="text" class="form-control" />
                      <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                  </th>
                  <th>
                    <div class="input-icon-right">
                      <input type="text" class="form-control" />
                      <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                  </th>
                  <th>
                    <div class="input-icon-right">
                      <input type="text" class="form-control" />
                      <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                  </th>
                  <th>
                    <div class="input-icon-right">
                      <input type="text" class="form-control" />
                      <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                  </th>
                  <th>
                    <div class="input-icon-right">
                      <input type="text" class="form-control" />
                      <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                  </th>
                  <th>
                    <div class="input-icon-right">
                      <input type="text" class="form-control" />
                      <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                  </th>
                  <th>
                    <div class="input-icon-right">
                      <input type="text" class="form-control" />
                      <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                  </th>
                  <th>
                    <div class="input-icon-right">
                      <input type="text" class="form-control" />
                      <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                  </th>
                  <th>
                    <div class="input-icon-right">
                      <input type="text" class="form-control" />
                      <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                  </th>
                  <th>
                    <div class="input-icon-right">
                      <input type="text" class="form-control" />
                      <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                  </th>
                  <th>
                    <div class="input-icon-right">
                      <input type="text" class="form-control" />
                      <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr class="highlight">
                  <td class="w20 center">
                    <span class="fa fa-play text-main"></span>
                  </td>
                  <td class="w20 center">
                    <div class="check-action">
                      <input type="checkbox" class="check" />
                      <span class="name"></span>
                    </div>
                  </td>
                  <td class="selected highlight1"></td>
                  <td></td>
                  <td></td>
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
                <tr>
                  <td class="w20 center"></td>
                  <td class="w20 center">
                    <div class="check-action">
                      <input type="checkbox" class="check" />
                      <span class="name"></span>
                    </div>
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
                  <td></td>
                  <td></td>
                  <td></td>
                </tr>
                <tr>
                  <td class="w20 center"></td>
                  <td class="w20 center">
                    <div class="check-action">
                      <input type="checkbox" class="check" />
                      <span class="name"></span>
                    </div>
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
                  <td></td>
                  <td></td>
                  <td></td>
                </tr>
                <tr>
                  <td class="w20 center"></td>
                  <td class="w20 center">
                    <div class="check-action">
                      <input type="checkbox" class="check" />
                      <span class="name"></span>
                    </div>
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
                  <td></td>
                  <td></td>
                  <td></td>
                </tr>
                <tr>
                  <td class="w20 center"></td>
                  <td class="w20 center">
                    <div class="check-action">
                      <input type="checkbox" class="check" />
                      <span class="name"></span>
                    </div>
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
                  <td></td>
                  <td></td>
                  <td></td>
                </tr>
              </tbody>
            </table>
          </div>
          <div class="tc-pagination">
            <a href="#" class="btn one-arrow-left-forward"></a>
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
  </b-modal>
</template>
<script>
export default {
  name: "DongBoTTModal",
  data() {
    return {
      ds_trangthai: [
        {
          id: 1,
          text: "Chưa đồng bộ các trạng thái",
        },
        {
          id: 2,
          text: "Chưa đồng bộ trạng thái thanh lý, khóa 2 chiều",
        },
        {
          id: 3,
          text: "Đã đồng bộ trạng thái thanh lý",
        },
      ],
    };
  },
  methods: {
    showModal: function () {
      this.$refs["dongBoTTModal"].show();
    },
    hideModal: function () {
      this.$refs["dongBoTTModal"].hide();
    },
  },
};
</script>