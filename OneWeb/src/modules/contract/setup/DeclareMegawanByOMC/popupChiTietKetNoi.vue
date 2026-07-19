<template>
  <b-modal
    ref="popupChiTietKetNoi"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Chi tiết kết nối số chính, số
          phụ
        </div>
        <div class="close -ap icon-close" data-dismiss="modal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#"> <span class="icon one-search"></span>Lấy DS (F5) </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin tìm kiếm</div>
          <div class="info-row">
            <div class="key w50">Mã KH</div>
            <div class="value">
              <div class="input-more-button">
                <button class="btn">
                  <span class="-ap icon-more_horiz"></span>
                </button>
                <input type="text" class="form-control" />
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="box-form">
              <div class="legend-title">Danh sách danh bạ thuê bao</div>
              <div class="table-content">
                <table class="table-result table-gachle">
                  <thead class="center">
                    <tr>
                      <th class="w20"></th>
                      <th>Account</th>
                      <th>Loại node</th>
                      <th>Số ảo</th>
                      <th>Tốc độ</th>
                      <th>Trạng thái TB</th>
                      <th>Địa chỉ LĐ</th>
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
                    </tr>
                    <tr>
                      <td class="w20 center"></td>
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
                    </tr>
                    <tr>
                      <td class="w20 center"></td>
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
                    </tr>
                    <tr>
                      <td class="w20 center"></td>
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
          <div class="col-sm-6 col-12">
            <div class="box-form">
              <div class="legend-title">Danh sách hợp đồng thuê bao</div>
              <div class="table-content">
                <table class="table-result table-gachle">
                  <thead class="center">
                    <tr>
                      <th class="w20"></th>
                      <th>Account</th>
                      <th>Số ảo</th>
                      <th>Tốc độ</th>
                      <th>Trạng thái HĐ</th>
                      <th>Địa chỉ LĐ</th>
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
                    </tr>
                    <tr>
                      <td class="w20 center"></td>
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
                    </tr>
                    <tr>
                      <td class="w20 center"></td>
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
                    </tr>
                    <tr>
                      <td class="w20 center"></td>
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
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="box-form">
              <div class="legend-title">Thông tin thuê bao trong danh bạ</div>
              <div class="title marb7">Thông tin PE (router biên VTN)</div>
              <div class="row">
                <div class="col-sm-4 col-12">
                  <div class="info-row">
                    <div class="key w70">Router</div>
                    <div class="value">
                      <div class="select-custom">
                        <select name="" id="" class="form-control">
                          <option value=""></option>
                        </select>
                      </div>
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w70">Dslam</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w70">Tên Tbị</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w70">Vrf</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w70">Link</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w70">Lan_IP</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w70">Loại cáp</div>
                    <div class="value">
                      <div class="select-custom">
                        <select name="" id="" class="form-control">
                          <option value=""></option>
                        </select>
                      </div>
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w70">T.cáp gốc</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w70">Đ.cáp gốc</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w70">Cáp gốc</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-8 col-12">
                  <div class="row">
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">Slot PE</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">Port PE</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w80">Trạm Tbị</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">SvLan</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">CvLan</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">Port</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">Route</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w80">Serial</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w80">Wan_ip</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w80">H.cáp ngọn</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">T.cáp ngọn</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">Tuyến cáp</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">D.cáp ngọn</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">Liên tủ</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">Cáp ngọn</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">Cự ly</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="box-form">
              <div class="legend-title">Thông tin thuê bao trong hợp đồng</div>
              <div class="title marb7">Thông tin PE (router biên VTN)</div>
              <div class="row">
                <div class="col-sm-4 col-12">
                  <div class="info-row">
                    <div class="key w70">Router</div>
                    <div class="value">
                      <div class="select-custom">
                        <select name="" id="" class="form-control">
                          <option value=""></option>
                        </select>
                      </div>
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w70">Dslam</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w70">Tên Tbị</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w70">Vrf</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w70">Link</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w70">Lan_IP</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w70">Loại cáp</div>
                    <div class="value">
                      <div class="select-custom">
                        <select name="" id="" class="form-control">
                          <option value=""></option>
                        </select>
                      </div>
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w70">T.cáp gốc</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w70">Đ.cáp gốc</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w70">Cáp gốc</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-8 col-12">
                  <div class="row">
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">Slot PE</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">Port PE</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w80">Trạm Tbị</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">SvLan</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">CvLan</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">Port</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">Route</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w80">Serial</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w80">Wan_ip</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w80">H.cáp ngọn</div>
                    <div class="value">
                      <input type="text" class="form-control" />
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">T.cáp ngọn</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">Tuyến cáp</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">D.cáp ngọn</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">Liên tủ</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">Cáp ngọn</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">Cự ly</div>
                        <div class="value">
                          <input type="text" class="form-control" />
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from "@/components/ActionTop.vue";
import API from "./API";
export default {
  name: "popupChiTietKetNoi",
  components: {
    ActionTop,
  },
  // props: ['vghichu'],
  data() {
    return {};
  },
  mounted() {
    /* let data = { vkieu: 1 }; 
    API.taoGhiChu(this.axios, data).then((response) => {
      if (
        response &&
        response.data &&
        response.data.data &&
        response.data.error_code &&
        response.data.error_code === "BSS-00000000" &&
        response.data.data
      ) {
        let items = [];
        response.data.data.forEach(function (item) {
          items.push({ id: item.kqxl_id, text: item.ketqua_xl });
        });
        this.p_noidungxl_options = items;
        this.list.p_noidungxl = items[0].id;
      }
    }); */
  },
  methods: {
    showModal() {
      // this.list.p_ghichu = this.vghichu;
      this.$refs["popupChiTietKetNoi"].show();
    },
    hideModal() {
      this.$refs["popupChiTietKetNoi"].hide();
    },

    ghiLai() {
      // this.$emit("acceptChange", this.list);
      this.hideModal();
    },
  },
};
</script>