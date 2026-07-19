<template>
  <b-modal
    id="popup-frmKhaoSatKH_HDTB"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    show="onShow"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> KHẢO SÁT KHÁCH HÀNG BÁO HỎNG
        </div>
        <div class="close -ap icon-close" data-dismiss="modal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#"> <span class="icon one-save"></span> Ghi lại </a>
          </li>
          <li>
            <a href="#">
              <span class="icon arrows-1_curved-next nc-icon-glyph"></span> Điều
              lại
            </a>
          </li>
          <li>
            <a href="#">
              <span class="icon files_single-paragraph nc-icon-glyph"></span>
              Lịch sử KS
            </a>
          </li>
          <li>
            <a href="#">
              <span class="icon files_single-copy-04 nc-icon-glyph"></span> Lịch
              sử BH
            </a>
          </li>
          <li>
            <a href="#">
              <span class="icon business_notes nc-icon-glyph"></span> Lịch sử
              hẹn
            </a>
          </li>
          <li>
            <a href="#">
              <span class="icon files_single-content-02 nc-icon-glyph"></span>
              Trợ giúp
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="box-form">
              <div class="legend-title mart0">
                Thông tin khách hàng / Thuê bao
              </div>
              <div class="info-row">
                <div class="key w110">Số máy/Acc</div>
                <div class="value">
                  <input type="text" class="form-control highlight" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w110">Loại hình/tốc độ</div>
                <div class="value">
                  <input type="text" class="form-control" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w110">TB cùng đôi cáp</div>
                <div class="value">
                  <input type="text" class="form-control" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w110">Tên thuê bao</div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control"
                    value="Trần Quang Thành"
                  />
                </div>
              </div>
              <div class="info-row">
                <div class="key w110">Địa chỉ lắp đặt</div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control"
                    value="28, Hằng Thao, Phường Trần Hưng Đạo, Thành phố Nam Định, ti "
                  />
                </div>
              </div>
              <div class="info-row">
                <div class="key w110">Điện thoại liên hệ</div>
                <div class="value">
                  <input type="text" class="form-control" value="0944555163" />
                </div>
              </div>
            </div>
            <div class="box-form" style="height: 300px">
              <div class="legend-title mart0">
                Thông tin cập nhật
              </div>
              <div class="info-row">
                <div class="key w110">Tên người trả lời</div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control"
                    value="Trần Quang Thành"
                  />
                </div>
              </div>
              <div class="info-row">
                <div class="key w110">Số liên hệ</div>
                <div class="value">
                  <input type="text" class="form-control" value="0944555163" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w110">Tb cùng đôi cáp</div>
                <div class="value">
                  <textarea
                    class="form-control"
                    style="height: 80px;resize: vertical;"
                  ></textarea>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="box-form">
              <div class="legend-title mart0">
                <div class="title pull-left mart7">Thông báo hỏng</div>
                <div class="pull-right">
                  <div class="desc fw6 padt7 inline vtop">
                    Lần hỏng trong 30 ngày: <span class="red">0</span>
                  </div>
                  <div class="inline marl10 vtop">
                    <div class="info-row normal">
                      <div class="key w90">Thời gian VP</div>
                      <div class="value w90">
                        <input
                          type="text"
                          class="form-control red"
                          value="28,23"
                        />
                      </div>
                    </div>
                  </div>
                </div>
                <div class="clearfix"></div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Ngày báo hỏng</div>
                    <div class="value">
                      <div class="input-icon-right">
                        <input
                          type="text"
                          class="form-control"
                          value="29/12/2020 14:19:48"
                        />
                        <span class="icon one-calendar"></span>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w80">Ngày HT</div>
                    <div class="value">
                      <div class="input-icon-right">
                        <input
                          type="text"
                          class="form-control"
                          value="29/12/2020 14:19:48"
                        />
                        <span class="icon one-calendar"></span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Người báo</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control"
                        value="h 0944555163"
                      />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w80">Số ĐT</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control"
                        value="0944555163"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w100">Tình trạng</div>
                <div class="value">
                  <input type="text" class="form-control" value="Hỏng khác" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w100">Nguyên nhân</div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control"
                    value="Treo thiết bị"
                  />
                </div>
              </div>
              <div class="info-row">
                <div class="key w100">Ghi chú NT</div>
                <div class="value">
                  <textarea
                    name=""
                    id=""
                    cols="30"
                    rows="10"
                    class="form-control"
                    style="height: 48px;resize: vertical;"
                  ></textarea>
                </div>
              </div>
            </div>
            <div class="box-form">
              <div class="legend-title mart0">
                <div class="title pull-left">
                  Danh sách lần hỏi
                </div>
                <div class="pull-right">
                  <div class="info-row">
                    <div class="key">
                      <div class="check-action red normal">
                        <input type="checkbox" class="check" />
                        <span class="name">Không trả lời</span>
                      </div>
                    </div>
                    <div class="value">
                      <div class="select-custom">
                        <select name="" id="" class="form-control">
                          <option>Khách hàng từ chối tiếp</option>
                        </select>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="clearfix"></div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w80">
                      <div class="check-action">
                        <input type="checkbox" class="check" />
                        <span class="name">Hẹn từ</span>
                      </div>
                    </div>
                    <div class="value">
                      <div class="input-icon-right">
                        <input type="text" class="form-control" />
                        <span class="icon one-calendar"></span>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w80">
                      <div class="check-action">
                        <input type="checkbox" class="check" />
                        <span class="name">Đến</span>
                      </div>
                    </div>
                    <div class="value">
                      <div class="input-icon-right">
                        <input type="text" class="form-control" />
                        <span class="icon one-calendar"></span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="table-content mart10" style="height: 140px;">
                <table class="table-result">
                  <thead class="center nowrap">
                    <tr>
                      <th class="w20"></th>
                      <th>Người hỏi</th>
                      <th>Người trả lời</th>
                      <th>Điện thoại</th>
                      <th>Ngày hỏi</th>
                      <th>Trạng thái</th>
                      <th>Lý do KTL</th>
                      <th>Ghi chú</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr class="highlight2 nowrap">
                      <td class="w30" style="background: #fff"></td>
                      <td style="background: #fff"></td>
                      <td></td>
                      <td></td>
                      <td></td>
                      <td></td>
                      <td></td>
                      <td></td>
                    </tr>
                    <tr class="nowrap">
                      <td class="w20 center">
                        <span class="fa fa-play text-main"></span>
                      </td>
                      <td class="selected"></td>
                      <td></td>
                      <td></td>
                      <td></td>
                      <td></td>
                      <td></td>
                      <td></td>
                    </tr>
                    <tr class="nowrap">
                      <td class="w20 center"></td>
                      <td class=""></td>
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
        <div class="box-form">
          <div class="legend-title">Danh sách câu hỏi</div>
          <div class="box-quest marb20">
            <div class="title-bg-main">
              <span class="title underline">Câu hỏi 1</span>
            </div>
            <div class="desc marb10">
              Xin chào, anh/chị, em là (Xưng tên) nhân viên CSKH của VNPT (nêu
              tên tỉnh, thành phố). Em xin phép được hỏi dịch vụ (nêu tên dịch
              vụ) tại (nêu tên địa chỉ thuê bao) đã hoàn thành lắp đặt / sửa
              chữa cho anh/chị chưa ạ?
            </div>
            <div class="list-aw">
              <div class="item padt5 padb5">
                <div class="check-action">
                  <input type="radio" class="check" />
                  <span class="name">Không thành công</span>
                </div>
              </div>
              <div class="item padt5 padb5">
                <div class="check-action">
                  <input type="radio" class="check" />
                  <span class="name">Không tính mẫu</span>
                </div>
              </div>
              <div class="item padt5 padb5">
                <div class="check-action">
                  <input type="radio" class="check" />
                  <span class="name">Chưa hoàn thành lắp đặt /sửa chữa</span>
                </div>
              </div>
              <div class="item padt5 padb5">
                <div class="check-action">
                  <input type="radio" class="check" />
                  <span class="name">Đã hoàn thành lắp đặt/sửa chữa</span>
                </div>
              </div>
            </div>
          </div>
          <div class="box-quest marb20">
            <div class="title-bg-main">
              <span class="title underline">Câu hỏi 21</span>
            </div>
            <div class="desc marb10">
              <p>
                Anh chị có hài lòng về chất lượng dịch vụ/thái độ của nhân viên
                VNPT (nêu tên dịch vụ) không ạ?
              </p>
              <p>
                (Nếu khách hàng trả lời không hài lòng thì hỏi tiếp: “Anh/chị có
                thể cho em biết lý do tại sao a/c không hài lòng được không ạ?)
              </p>
            </div>
            <div class="list-aw">
              <div class="item padt5 padb5">
                <div class="check-action">
                  <input type="radio" class="check" />
                  <span class="name">Hài lòng</span>
                </div>
              </div>
              <div class="item padt5 padb5">
                <div class="check-action">
                  <input type="radio" class="check" />
                  <span class="name">Không hài lòng về nv kinh doanh</span>
                </div>
              </div>
              <div class="item padt5 padb5">
                <div class="check-action">
                  <input type="radio" class="check" />
                  <span class="name">Không hài lòng về nhân viên kỹ thuật</span>
                </div>
              </div>
            </div>
            <div
              class="box-quest box-form marb20 marl20"
              style="background:#FFF9EB"
            >
              <div class="title-bg-main">
                <span class="title underline">Câu hỏi phụ 1</span>
              </div>
              <div class="desc marb10">
                <p>Hài lòng</p>
              </div>
              <div class="list-aw">
                <div class="item padt5 padb5">
                  <div class="check-action">
                    <input type="radio" class="check" />
                    <span class="name">HL.1 (Khách hàng trả lời hài lòng)</span>
                  </div>
                </div>
                <div class="item padt5 padb5">
                  <div class="check-action">
                    <input type="radio" class="check" />
                    <span class="name"
                      >HL.2 (Khách hàng trả lời bình thường/được rồi/tạm
                      được)</span
                    >
                  </div>
                </div>
              </div>
              <div class="input" style="max-width: 600px;">
                <input
                  type="text"
                  class="form-control"
                  placeholder="Hài lòng"
                />
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
  name: "frmKhaoSatKH_HDTB"
};
</script>
