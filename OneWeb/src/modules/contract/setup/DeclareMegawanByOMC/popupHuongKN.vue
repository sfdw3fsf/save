<template>
  <b-modal
    ref="popupHuongKN"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Chọn hướng kết nối
        </div>
        <div class="close -ap icon-close" data-dismiss="modal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#"> <span class="icon one-search"></span>Tìm kiếm </a>
          </li>
          <li>
            <a href="#">
              <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp
              nhận
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
                <div class="key">Tra cứu theo</div>
                <div class="value">
                  <div class="select-custom">
                    <select name="" id="" class="form-control">
                      <option value=""></option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key">Thông tin tra cứu</div>
                <div class="value">
                  <input type="text" class="form-control" />
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w80">
                  <div class="check-action">
                    <input type="checkbox" class="check" />
                    <span class="name">Dịch vụ</span>
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
              <div class="info-row">
                <div class="key w80">
                  <div class="check-action">
                    <input type="checkbox" class="check" />
                    <span class="name">Đơn vị</span>
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
            <div class="col-sm-2 col-12">
              <div class="list-checks-v">
                <div class="item marb10 mart7">
                  <div class="check-action">
                    <input type="radio" class="check" />
                    <span class="name">Hợp đồng</span>
                  </div>
                </div>
                <div class="item marb10 mart7">
                  <div class="check-action">
                    <input type="radio" class="check" />
                    <span class="name">Danh bạ</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách khách hàng</div>
          <div class="table-content">
            <table class="table-result table-gachle table-filter" id="table">
              <thead class="center">
                <tr>
                  <th class="w20"></th>
                  <th>Stt</th>
                  <th>Mã KH</th>
                  <th>Mã HĐ/PL</th>
                  <th>Tên khách hàng</th>
                  <th>Địa chỉ khách hàng</th>
                </tr>
                <tr>
                  <th class="w20"></th>
                  <th class="w60">
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
        <div class="box-form">
          <div class="legend-title">
            <div class="pull-left">Danh sách hướng kết nối</div>
            <div class="pull-right">
              <div class="info-row">
                <div class="key w30 nowrap red">Tỉnh kết nối</div>
                <div class="value">
                  <div class="select-custom" style="width: 130px">
                    <select name="" id="" class="form-control"></select>
                  </div>
                </div>
              </div>
            </div>
            <div class="clearfix"></div>
          </div>
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
                  <th>VRF/VFI</th>
                  <th>Mã thuê bao</th>
                  <th>Tên thuê bao</th>
                  <th>Địa chỉ lắp đặt</th>
                  <th>Loại hình</th>
                  <th>Tốc độ</th>
                  <th>Số ảo</th>
                  <th>Trạng thái</th>
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
import ActionTop from "@/components/ActionTop.vue";
import API from "./API";
export default {
  name: "popupHuongKN",
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
      this.$refs["popupHuongKN"].show();
    },
    hideModal() {
      this.$refs["popupHuongKN"].hide();
    },

    ghiLai() {
      // this.$emit("acceptChange", this.list);
      this.hideModal();
    },
  },
};
</script>