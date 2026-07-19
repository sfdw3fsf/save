<template>
  <div class="box-form">
            <div class="legend-title">
                <div class="pull-left">
                    Danh sách thuê bao khoá mở máy do nợ cước
                </div>
                <div class="pull-right">
                    <div class="list-checks">
                        <div class="item">
                            <div class="check-action">
                                <input type="radio" checked name="rad2" class="check">
                                <span class="name">Phiếu mới</span>
                            </div>
                        </div>
                        <div class="item">
                            <div class="check-action">
                                <input type="radio" name="rad2" class="check">
                                <span class="name">Phiếu trả lại</span>
                            </div>
                        </div>
                        <div class="item red">
                            Thông báo:
                        </div>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
            <vue-table
            ref="vueTable"
            v-model="table.currentRecord"
            :options="table.options"
            :config="table.config"
            :loading="table.loading"
            :unique="'PHANLOAI_ID'"
            :multiple="true"></vue-table>
            <!-- <div class="table-content">
                <table class="table-result table-gachle">
                    <thead class="center">
                        <tr>
                            <th class="w20"></th>
                            <th class="w20">
                            </th>
                            <th>Số máy/Acc</th>
                            <th>Loại hình</th>
                            <th>Tên thuê bao</th>
                            <th>Địa chỉ lắp đặt</th>
                            <th>Đơn vị gửi</th>
                            <th>Chiều khoá/mở</th>
                            <th>Kích hoạt tổng đài</th>
                            <th>Host LĐ</th>
                            <th>Ngày giao</th>
                            <th>Cách khoá/mở</th>
                            <th>NV xác nhận</th>
                            <th>KQ xác nhận</th>
                            <th>ND thực hiện</th>
                            <th>Ngày XN</th>
                        </tr>
                        <tr>
                            <th class="w20"></th>
                            <th class="w20">

                            </th>
                            <th>
                                <div class="input-icon-right">
                                    <input type="text" class="form-control">
                                    <span class="icon nc-icon-outline ui-1_zoom"></span>
                                </div>
                            </th>
                            <th>
                                <div class="input-icon-right">
                                    <input type="text" class="form-control">
                                    <span class="icon nc-icon-outline ui-1_zoom"></span>
                                </div>
                            </th>

                            <th>
                                <div class="input-icon-right">
                                    <input type="text" class="form-control">
                                    <span class="icon nc-icon-outline ui-1_zoom"></span>
                                </div>
                            </th>
                            <th>
                                <div class="input-icon-right">
                                    <input type="text" class="form-control">
                                    <span class="icon nc-icon-outline ui-1_zoom"></span>
                                </div>
                            </th>
                            <th>
                                <div class="input-icon-right">
                                    <input type="text" class="form-control">
                                    <span class="icon nc-icon-outline ui-1_zoom"></span>
                                </div>
                            </th>
                            <th>
                                <div class="input-icon-right">
                                    <input type="text" class="form-control">
                                    <span class="icon nc-icon-outline ui-1_zoom"></span>
                                </div>
                            </th>
                            <th>
                                <div class="input-icon-right">
                                    <input type="text" class="form-control">
                                    <span class="icon nc-icon-outline ui-1_zoom"></span>
                                </div>
                            </th>
                            <th>
                                <div class="input-icon-right">
                                    <input type="text" class="form-control">
                                    <span class="icon nc-icon-outline ui-1_zoom"></span>
                                </div>
                            </th>
                            <th>
                                <div class="input-icon-right">
                                    <input type="text" class="form-control">
                                    <span class="icon nc-icon-outline ui-1_zoom"></span>
                                </div>
                            </th>
                            <th>
                                <div class="input-icon-right">
                                    <input type="text" class="form-control">
                                    <span class="icon nc-icon-outline ui-1_zoom"></span>
                                </div>
                            </th>
                            <th>
                                <div class="input-icon-right">
                                    <input type="text" class="form-control">
                                    <span class="icon nc-icon-outline ui-1_zoom"></span>
                                </div>
                            </th>
                            <th>
                                <div class="input-icon-right">
                                    <input type="text" class="form-control">
                                    <span class="icon nc-icon-outline ui-1_zoom"></span>
                                </div>
                            </th>
                            <th>
                                <div class="input-icon-right">
                                    <input type="text" class="form-control">
                                    <span class="icon nc-icon-outline ui-1_zoom"></span>
                                </div>
                            </th>
                            <th>
                                <div class="input-icon-right">
                                    <input type="text" class="form-control">
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
                                    <input type="checkbox" class="check">
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
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="w20 center">
                            </td>
                            <td class="w20 center">
                                <div class="check-action">
                                    <input type="checkbox" class="check">
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
                            <td></td>
                            <td></td>

                        </tr>
                        <tr>
                            <td class="w20 center">
                            </td>
                            <td class="w20 center">
                                <div class="check-action">
                                    <input type="checkbox" class="check">
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
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="w20 center">
                            </td>
                            <td class="w20 center">
                                <div class="check-action">
                                    <input type="checkbox" class="check">
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
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="w20 center">
                            </td>
                            <td class="w20 center">
                                <div class="check-action">
                                    <input type="checkbox" class="check">
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
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="w20 center">
                            </td>
                            <td class="w20 center">
                                <div class="check-action">
                                    <input type="checkbox" class="check">
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
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="w20 center">
                            </td>
                            <td class="w20 center">
                                <div class="check-action">
                                    <input type="checkbox" class="check">
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
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="w20 center">
                            </td>
                            <td class="w20 center">
                                <div class="check-action">
                                    <input type="checkbox" class="check">
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
            </div> -->
        </div>
</template>

<script>
export default {
  data: () => ({
    table: {
      loading: false,
      currentRecord: [],
      options: [],
      config: [
        {
          label: 'Số máy/Acc',
          filterAble: true,
          key: 'a'
        },
        {
          label: 'Loại hình',
          filterAble: true,
          key: 'a'
        },
        {
          label: 'Tên thuê bao',
          filterAble: true,
          key: 'a'
        },
        {
          label: 'Địa chỉ lắp đặt',
          filterAble: true,
          key: 'a'
        },
        {
          label: 'Đơn vị gửi',
          filterAble: true,
          key: 'a'
        },
        {
          label: 'Chiều khoá/mở',
          filterAble: true,
          key: 'a'
        },
        {
          label: 'Kích hoạt tổng đài',
          filterAble: true,
          key: 'a'
        },
        {
          label: 'Host LĐ',
          filterAble: true,
          key: 'a'
        },
        {
          label: 'Ngày giao',
          filterAble: true,
          key: 'a'
        },
        {
          label: 'Cách khoá/mở',
          filterAble: true,
          key: 'a'
        },
        {
          label: 'NV xác nhận',
          filterAble: true,
          key: 'a'
        },
        {
          label: 'KQ xác nhận',
          filterAble: true,
          key: 'a'
        },
        {
          label: 'ND thực hiện',
          filterAble: true,
          key: 'a'
        },
        {
          label: 'Ngày XN',
          filterAble: true,
          key: 'a'
        }
      ]
    }
  })
}
</script>

<style>

</style>
