<template>
  <div>
    <vue-bread-crumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li
          @click="clickButton('laytt')"
          :class="this.button.laytt ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-attach1"></span>Lấy thông tin</a>
        </li>
        <li
          @click="clickButton('ghilai')"
          :class="this.button.ghilai ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-save"></span>Ghi lại </a>
        </li>

      </ul>
    </vue-nav>


        <div class="page-content">
            <div class="box-form padt8 padb5">
                <div class="pull-right">
                    <div class="inline">
                        <div class="info-row marb0">
                            <div class="key fw6 w80">Tổng tiền</div>
                            <div class="value">
                                <input type="text" class="form-control red tright" value="0">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="box-form">
                <div class="legend-title">Thông tin phiếu</div>
                <div class="row">
                    <div class="col-sm-7 col-12">
                        <div class="row">
                            <div class="col-sm-8 col-12">
                                <div class="info-row">
                                    <div class="key w80">Dịch vụ</div>
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
                                    <div class="key w100">Quy trình</div>
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
                        <div class="row">
                            <div class="col-sm-8 col-12">
                                <div class="info-row">
                                    <div class="key w80">Mã GD</div>
                                    <div class="value">
                                        <div class="input-more-button">
                                            <button class="btn">
                                                <span class="-ap icon-more_horiz"></span>
                                            </button>
                                            <input type="text" class="form-control highlight">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w100">Kiểu yêu cầu</div>
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
                        <div class="row">
                            <div class="col-sm-8 col-12">
                                <div class="info-row">
                                    <div class="key w80">Người GV</div>
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
                                    <div class="key w100">Ngày GV</div>
                                    <div class="value w16 padt7">
                                        <div class="check-action">
                                            <input type="checkbox" class="check">
                                            <span class="name"></span>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <input type="text" class="form-control">
                                            <span class="icon one-calendar"></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w80">Account</div>
                                    <div class="value">
                                        <input type="text" class="form-control highlight">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w80">Mật khẩu</div>
                                    <div class="value">
                                        <input type="text" class="form-control highlight">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w100">Ngày HT</div>
                                    <div class="value w16 padt7">
                                        <div class="check-action">
                                            <input type="checkbox" class="check">
                                            <span class="name"></span>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <input type="text" class="form-control">
                                            <span class="icon one-calendar"></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-8 col-12">
                                <div class="info-row">
                                    <div class="key w80">Tốc độ mới</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select name="" id="" class="form-control">
                                                <option value=""></option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w80">Tốc độ cũ</div>
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
                                    <div class="key w100">Mức cước mới</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select name="" id="" class="form-control">
                                                <option value=""></option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w100">Mức cước cũ</div>
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
                            <div class="key w80">Tốc độ thực</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select name="" id="" class="form-control">
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w80">Bras</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select name="" id="" class="form-control">
                                                <option value=""></option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="list-checks mart7">
                                    <div class="item">
                                        <div class="check-action">
                                            <input type="checkbox" class="check">
                                            <span class="name">Giữ IP</span>
                                        </div>
                                    </div>
                                    <div class="item">
                                        <div class="check-action">
                                            <input type="checkbox" class="check">
                                            <span class="name">Tự động</span>
                                        </div>
                                    </div>
                                </div>
                                
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w100">IPv4 Lan</div>
                                    <div class="value">
                                        <div class="input-more-button">
                                            <button class="btn">
                                                <span class="-ap icon-more_horiz"></span>
                                            </button>
                                            <input type="text" class="form-control ">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w80">Dslam</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select name="" id="" class="form-control">
                                                <option value=""></option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w80">Port MDF</div>
                                    <div class="value">
                                        <input type="text" class="red form-control fw6">
                                    </div>
                                </div>
                                
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w100">IPv4 Wan</div>
                                    <div class="value">
                                        <div class="input-more-button">
                                            <button class="btn">
                                                <span class="-ap icon-more_horiz"></span>
                                            </button>
                                            <input type="text" class="form-control ">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-2 col-12">
                                <div class="info-row">
                                    <div class="key w40">Rack</div>
                                    <div class="value">
                                        <div class="input-more-button">
                                            <button class="btn">
                                                <span class="-ap icon-check2"></span>
                                            </button>
                                        </div>
                                        <div class="input-more-button">
                                            <input type="text" class="form-control red">
                                        </div>
                                    </div>
                                </div>
                                
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w60">Shelf</div>
                                    <div class="value">
                                        <input type="text" class="red form-control fw6">
                                    </div>
                                </div>
                                
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w80">Trạm T.Bị</div>
                                    <div class="value">
                                        <input type="text" class="red form-control fw6">
                                    </div>
                                </div>
                                
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w100">IPv6 Lan</div>
                                    <div class="value">
                                        <div class="input-more-button">
                                            <button class="btn">
                                                <span class="-ap icon-more_horiz"></span>
                                            </button>
                                            <input type="text" class="form-control ">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-2 col-12">
                                <div class="info-row">
                                    <div class="key w80">Slot</div>
                                    <div class="value">
                                        <input type="text" class="form-control">
                                    </div>
                                </div>
                                
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w60">Port</div>
                                    <div class="value">
                                        <input type="text" class="form-control fw6">
                                    </div>
                                </div>
                                
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w80">System</div>
                                    <div class="value">
                                        <input type="text" class="red form-control fw6">
                                    </div>
                                </div>
                                
                            </div>
                            <div class="col-sm-2 col-12">
                                <div class="info-row">
                                    <div class="key w100">Vpi/Svlan</div>
                                    <div class="value">
                                        <input type="text" class="red form-control fw6">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-2 col-12">
                                <div class="info-row">
                                    <div class="key w60">Vci/Cvlan</div>
                                    <div class="value">
                                        <input type="text" class="red form-control fw6">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-2 col-12">
                                <div class="info-row">
                                    <div class="key w80">Self</div>
                                    <div class="value">
                                        <input type="text" class="form-control">
                                    </div>
                                </div>
                                
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w60">OLT Slot</div>
                                    <div class="value w50">
                                        <input type="text" class="form-control">
                                    </div>
                                    <div class="key w60 padl5 nowrap">OLT Port</div>
                                    <div class="value">
                                        <input type="text" class="form-control">
                                    </div>
                                </div>
                                
                            </div>
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key w80">ONUID</div>
                                    <div class="value">
                                        <input type="text" class="form-control fw6">
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="col-sm-5 col-12">
                        <div class="info-row">
                            <div class="key w100">Tên thuê bao</div>
                            <div class="value">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Địa chỉ lắp đặt</div>
                            <div class="value">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Nội dung giao</div>
                            <div class="value">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Nội dung trả</div>
                            <div class="value">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Ghi chú</div>
                            <div class="value">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Nội dung TH</div>
                            <div class="value">
                                <div class="input-more-button">
                                    <button class="btn">
                                        <span class="nc-icon-glyph ui-1_edit-76 f16"></span>
                                    </button>
                                    <input type="text" class="form-control ">
                                </div>
                            </div>
                        </div>
                        <div class="legend-title mart10">Danh sách nhân viên thực hiện</div>
                        <div class="table-content">
                            <table class="table-result table-gachle">
                                <thead class="center">
                                    <tr>
                                        <th class="w20"></th>
                                        <th>Tên nhân viên</th>
                                        <th>Điện thoại</th>
                                        <th>Nhiệm vụ</th>
                                        <th>Ghi chú</th>
                                    </tr>
                                    <tr>
                                        <th class="w20"></th>
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
                                        <td class="selected highlight1"></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td class="w20 center">
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td class="w20 center">
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">
                    <div class="pull-left">Danh sách thuê bao thay đổi tốc độ</div>
                    <div class="pull-right">
                        <div class="list-checks">
                            <div class="item">
                                <div class="check-action">
                                    <input type="radio" class="check">
                                    <span class="name">Phiếu mới</span>
                                </div>
                            </div>
                            <div class="item">
                                <div class="check-action">
                                    <input type="radio" class="check">
                                    <span class="name">Phiếu trả lại</span>
                                </div>
                            </div>
                            <div class="item red">
                                Tổng số : 4 thuê bao
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
                                <th>Mã GD</th>
                                <th>Account</th>
                                <th>Tên thuê bao</th>
                                <th>Địa chỉ LĐ</th>
                                <th>Trạng thái in</th>
                                <th>Ngày KH</th>
                                <th>Ngày HT</th>
                                <th>Loại hình</th>
                                <th>IP</th>
                                <th>Kiểu</th>
                                <th>Kích hoạt Vasc</th>
                                <th>Kiểu trả</th>
                                <th>Tháng trả</th>
                                <th>Ngày trả</th>
                                <th>Đơn vị giao</th>
                                <th>Ghi chú</th>
                            </tr>
                            <tr>
                                <th class="w20"></th>
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
                                <td class="center">
                                    <span class="text-main f20 one-alert"></span> Chưa kích hoạt
                                </td>
                                <td></td>
                                
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
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
                                <td></td>
                                <td class="center">
                                    <span class="text-main f20 -ap icon-check2"></span> Chưa kích hoạt
                                </td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
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
                                <td></td>
                                <td class="center">
                                    <span class="text-main f20 -ap icon-check2"></span> Chưa kích hoạt
                                </td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
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
                                <td></td>
                                <td class="center">
                                    <span class="text-main f20 -ap icon-check2"></span> Chưa kích hoạt
                                </td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
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
                                <td></td>
                                <td class="center">
                                    <span class="text-main f20 -ap icon-check2"></span> Chưa kích hoạt
                                </td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
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
                                <td></td>
                                <td class="center">
                                    <span class="text-main f20 -ap icon-check2"></span> Chưa kích hoạt
                                </td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
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
                                <td></td>
                                <td class="center">
                                    <span class="text-main f20 -ap icon-check2"></span> Chưa kích hoạt
                                </td>
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
</template>
<script>

import api from "./api";
import define from "./define";




export default {

  data() {
    return {
      ...define,
    
    };
  },


  created() {

  },
  watch: {

},
  provide: {
  
  },
  methods: {


    clickButton(key) {

      if (key == "laytt") {    

      }
      if (key == "ghilai") {    

      }
    },
  },
};
</script>
<style>
li.non-active a {
  color: #d3d3d3 !important;
}

@import "https://cdn.syncfusion.com/ej2/material.css";
</style>