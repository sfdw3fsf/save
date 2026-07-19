<template>
  <b-modal
    ref="hopDongDaDKModal"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Danh sách hợp đồng đã ký
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
            <a href="#">
              <span class="icon one-file-attach"></span>Lấy TT (F5)
            </a>
          </li>
          <li>
            <a href="#"> <span class="icon one-reload1"></span>Cập nhật </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <span class="fw6 upper">DANH SÁCH HỢP ĐỒNG ĐÃ KÝ</span>
        </div>
        <div class="box-form">
          <div class="row">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w100">Mã giao dịch</div>
                <div class="value">
                  <input type="text" class="form-control highlight" />
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w100">Loại HĐ/PL</div>
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
                <div class="key w100">Tên thuê bao</div>
                <div class="value">
                  <input type="text" class="form-control" />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-8 col-12">
              <div class="info-row">
                <div class="key w100">Nhân viên YC</div>
                <div class="value">
                  <input type="text" class="form-control" />
                </div>
              </div>
            </div>

            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w100">Địa chỉ LĐ</div>
                <div class="value">
                  <input type="text" class="form-control" />
                </div>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w100">Ghi chú TB</div>
            <div class="value">
              <input type="text" class="form-control" />
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách thuê bao</div>
          <div class="table-content">
            <table class="table-result table-gachle">
              <thead class="center">
                <tr>
                  <th class="w20"></th>
                  <th>Mã GD</th>
                  <th>Tên TB</th>
                  <th>Điện thoại LH</th>
                  <th>Loại hình</th>
                  <th>Địa chỉ LĐ</th>
                  <th>Ngày YC</th>
                  <th>Ghi chú</th>
                </tr>
                <tr>
                  <th class="w20"></th>
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
                  <td class="selected highlight1"></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                </tr>
                <tr>
                  <td class="w20 center"></td>
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
  name: "HopDongDaDKModal",
  methods: {
    showModal: function () {
      this.$refs["hopDongDaDKModal"].show();
    },
    hideModal: function () {
      this.$refs["hopDongDaDKModal"].hide();
    },
  },
};
</script>