<template>
  <div class="main-wrapper">
    <div class="breadcrumb-top">
      <div class="main-title">TRA CỨU THÔNG TIN HỢP ĐỒNG CHUYỂN QUYỀN</div>
      <ul class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="#"
            ><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ
          </a>
        </li>
        <li class="breadcrumb-item"><a href="#">Lập hợp đồng</a></li>
        <li class="breadcrumb-item active">Lắp đặt mới</li>
      </ul>
    </div>
    <div class="page-content" style="margin-top: -55px">
      <div class="nav tabs tab-over">
        <a href="#tab-1" data-toggle="tab" class="active"
          >Thông tin khách hàng (F2)</a
        >
        <a href="#tab-2" data-toggle="tab">Thông tin thuê bao (F3)</a>
      </div>
      <div class="tab-content">
        <div id="tab-1" class="tab-pane in active">
          <div class="box-form">
            <div class="legend-title">Thông tin khách hàng</div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="row">
                  <div class="col-sm-8 col-12">
                    <div class="info-row">
                      <div class="key w120">Mã GD (*)</div>
                      <div class="value">
                        <div class="input-more-button">
                          <button class="btn" @click.prevent="btnLayMaGD">
                            <span class="-ap icon-more_horiz"></span>
                          </button>


                          <SearchContractModal 
                          ref="popupSearchContract"  @accept="acceptSearchContract"
                          :loai_hd_id="2" 
                          :dichvu_id="1" 
                          :ngay_yc="''" 
                          :ngay_kt="''" 
                          :trangthai_hd="6" />



                          <input
                            type="text"
                            v-on:keyup="keymonitor"
                            v-model="txtMaGD"
                            class="form-control highlight"
                          />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-4 col-12">
                    <div class="info-row">
                      <div class="key w100">Ngày YC</div>
                      <div class="value">
                        <input
                          type="text"
                          v-model="txtNgayYC"
                          class="form-control"
                        />
                      </div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-8 col-12">
                    <div class="info-row">
                      <div class="key w120">Mã HĐ (*)</div>
                      <div class="value">
                        <input
                          type="text"
                          v-model="txtMAHD"
                          class="form-control"
                        />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-4 col-12">
                    <div class="info-row">
                      <div class="key w100">Ngày lập HĐ</div>
                      <div class="value">
                        <input
                          type="text"
                          v-model="txtNgayLapHD"
                          class="form-control"
                        />
                      </div>
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w120">Mã KH</div>
                  <div class="value">
                      <input
                        type="text"
                        v-model="txtMaKH"
                        class="form-control"
                      />
                  </div>
                </div>

                <div class="info-row">
                  <div class="key w120">Tên KH</div>
                  <div class="value">
                      <input
                        type="text"
                        v-model="txtTenKH"
                        class="form-control"
                      />
                  </div>
                </div>

                <div class="info-row">
                  <div class="key w120">Địa chỉ KH</div>
                  <div class="value">
                      <input
                        type="text"
                        v-model="txtDiaChiKH"
                        class="form-control"
                      />
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-8 col-12">
                    <div class="info-row">
                      <div class="key w120">Loại giấy tờ (*)</div>
                      <div class="value">
                          <SelectExt
                            v-model="ddlLoaiGiayTo"
                            :options="this.options.loaigiayto"
                          >
                            <option disabled value="0">Select one</option>
                          </SelectExt>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-4 col-12">
                    <div class="info-row">
                      <div class="key w100">Số giấy tờ (*)</div>
                      <div class="value">
                        <input
                          type="text"
                          v-model="txtSoGiayTo"
                          class="form-control"
                        />
                      </div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-8 col-12">
                    <div class="info-row">
                      <div class="key w120">Ngày hết hạn</div>
                      <div class="value">
                        <input
                          type="text"
                          v-model="txtNgayHetHan"
                          class="form-control"
                        />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-4 col-12">
                    <div class="info-row">
                      <div class="key w100">Ngày sinh (*)</div>
                      <div class="value">
                        <input
                          type="text"
                          v-model="txtNgaySinh"
                          class="form-control"
                        />
                      </div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-8 col-12">
                    <div class="info-row">
                      <div class="key w120">Ngày cấp (*)</div>
                      <div class="value">
                        <input
                          type="text"
                          v-model="txtNgayCap"
                          class="form-control"
                        />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-4 col-12">
                    <div class="info-row">
                      <div class="key w100">Nơi cấp</div>
                      <div class="value">
                        <input
                          type="text"
                          v-model="txtNoiCap"
                          class="form-control"
                          value=""
                        />
                      </div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-8 col-12">
                    <div class="info-row">
                      <div class="key w120">Giới tính</div>
                      <div class="value">
                        <div class="list-checks mart7">
                          <div class="item">
                            <div class="check-action">
                              <input
                                type="radio"
                                name="gioitinh"
                                value="2"
                                v-model="rdo_GioiTinh"
                                :disabled="true"
                                class="check"
                              />
                              <span class="name">Không</span>
                            </div>
                          </div>
                          <div class="item">
                            <div class="check-action">
                              <input
                                type="radio"
                                name="gioitinh"
                                value="0"
                                v-model="rdo_GioiTinh"
                                class="check"
                              />
                              <span class="name">Nam</span>
                            </div>
                          </div>
                          <div class="item">
                            <div class="check-action">
                              <input
                                type="radio"
                                name="gioitinh"
                                value="1"
                                v-model="rdo_GioiTinh"
                                class="check"
                              />
                              <span class="name">Nữ</span>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-4 col-12">
                    <div class="info-row">
                      <div class="key w100">Quốc tịch</div>
                      <div class="value">
                          <SelectExt
                            v-model="ddlQuocTich"
                            :options="this.options.quoctich"
                          >
                            <option disabled value="0">Select one</option>
                          </SelectExt>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-8 col-12">
                    <div class="info-row">
                      <div class="key w120">Ngành nghề</div>
                      <div class="value">
                            <SelectExt
                              v-model="ddlNganhNghe"
                              :options="this.options.nganhnghe"
                            >
                              <option disabled value="0">Select one</option>
                            </SelectExt>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-4 col-12">
                    <div class="info-row">
                      <div class="key w100">Dân tộc</div>
                      <div class="value">
                          <SelectExt
                            v-model="ddlDanToc"
                            :options="this.options.dantoc"
                          >
                            <option disabled value="0">Select one</option>
                          </SelectExt>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key">Loại KH</div>
                  <div class="value">
                    <div class="input-more-button">
                      <button class="btn">
                        <span class="-ap icon-more_horiz"></span>
                      </button>
                      <div class="custom">
                          <SelectExt
                            v-model="ddlLoaiKH"
                            :options="this.options.loaikh"
                          >
                            <option disabled value="0">Select one</option>
                          </SelectExt>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-8 col-12">
                    <div class="info-row">
                      <div class="key">Người đại diện</div>
                      <div class="value">
                        <input
                          type="text"
                          v-model="txtNguoiDaiDien"
                          class="form-control"
                        />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-4 col-12">
                    <div class="info-row">
                      <div class="key w100">Chức vụ</div>
                      <div class="value">
                        <input
                          type="text"
                          v-model="txtChucVu"
                          class="form-control"
                        />
                      </div>
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Địa chỉ NĐD</div>
                  <div class="value">
                      <input
                        type="text"
                        v-model="txtDiaChiNDD"
                        class="form-control"
                      />
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-8 col-12">
                    <div class="info-row">
                      <div class="key">Điện thoại</div>
                      <div class="value">
                        <input
                          type="text"
                          v-model="txtSDT"
                          class="form-control"
                        />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-4 col-12">
                    <div class="info-row">
                      <div class="key w100">Fax</div>
                      <div class="value">
                        <input
                          type="text"
                          v-model="txtFax"
                          class="form-control"
                        />
                      </div>
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Hộ khẩu</div>
                  <div class="value">
                      <input
                        type="text"
                        v-model="txtHoKhau"
                        class="form-control"
                      />
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-8 col-12">
                    <div class="info-row">
                      <div class="key">Nơi cấp HK</div>
                      <div class="value">
                        <input
                          type="text"
                          v-model="txtNoiCapHK"
                          class="form-control"
                        />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-4 col-12">
                    <div class="info-row">
                      <div class="key w100">Ngày cấp</div>
                      <div class="value">
                        <input
                          type="text"
                          v-model="txtNgayCapHK"
                          class="form-control"
                        />
                      </div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-8 col-12">
                    <div class="info-row">
                      <div class="key">Mã số thuế</div>
                      <div class="value">
                        <input
                          type="text"
                          v-model="txtMaSoThue"
                          class="form-control"
                        />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-4 col-12">
                    <div class="info-row">
                      <div class="key w100">Tài khoản</div>
                      <div class="value">
                        <input
                          type="text"
                          v-model="txtTaiKhoan"
                          class="form-control"
                        />
                      </div>
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Ngân hàng</div>
                  <div class="value w20 padt7">
                    <div class="check-action">
                      <input type="checkbox" v-model="chkNganHang" class="check" />
                      <span class="name"></span>
                    </div>
                  </div>
                  <div class="value">
                      <SelectExt
                        v-model="ddlNganHang"
                        :options="this.options.nganhang"
                        :disabled="!this.chkNganHang"
                      >
                        <option disabled value="0">Select one</option>
                      </SelectExt>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Đặc trưng</div>
                  <div class="value">
                    <input
                      type="text"
                      v-model="txtDacTrung"
                      class="form-control"
                    />
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Ghi chú</div>
                  <div class="value">
                    <input
                      type="text"
                      v-model="txtGhiChu"
                      class="form-control"
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="box-form">
            <div class="legend-title">Thông tin thanh toán</div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="row">
                  <div class="col-sm-8 col-12">
                    <div class="info-row">
                      <div class="key w120">Mã TT (*)</div>
                      <div class="value">
                          <input
                            type="text"
                            v-model="txtMaTT"
                            class="form-control"
                          />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-4 col-12">
                    <div class="info-row">
                      <div class="key w100">Mã đại diện</div>
                      <div class="value">
                        <input
                          type="text"
                          v-model="txtMaDaiDien"
                          class="form-control highlight"
                        />
                      </div>
                    </div>
                  </div>
                </div>

                <div class="info-row">
                  <div class="key w120">Tên thanh toán (*)</div>
                  <div class="value">
                    <input
                      type="text"
                      v-model="txtTenThanhToan"
                      class="form-control"
                    />
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w120">Địa chỉ TT (*)</div>
                  <div class="value">
                    <input
                      type="text"
                      v-model="txtDiaChiTT"
                      class="form-control"
                    />
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w120">Địa chỉ CT</div>
                  <div class="value">
                    <input
                      type="text"
                      v-model="txtDiaChiCT"
                      class="form-control"
                    />
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w120">Địa chỉ BC</div>
                  <div class="value">
                    <input
                      type="text"
                      v-model="txtDiaChiBC"
                      class="form-control"
                    />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key">ĐV thanh toán</div>
                  <div class="value">
                      <SelectExt
                        v-model="ddlDonViTT"
                        :options="this.options.donvitt"
                        :disabled="true"
                      >
                        <option disabled value="0">Select one</option>
                      </SelectExt>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-8 col-12">
                    <div class="info-row">
                      <div class="key">Hình thức TT (*)</div>
                      <div class="value">
                          <SelectExt
                            v-model="ddlHinhThucTT"
                            :options="this.options.hinhthuctt"
                          >
                            <option disabled value="0">Select one</option>
                          </SelectExt>
                      </div>
                    </div>
                  </div>

                                <div class="col-sm-4 col-12">
                                    <div class="info-row">
                                        <div class="key w100">Mã NVTC (*)</div>
                                        <div class="value">
                                            <div class="row">
                                                <div class="col-sm-6 col-12" style="padding: 0px 7px !important;">
                                                    <input type="text" v-model="txtMaNVTC_1" class="form-control highlight">
                                                </div>
                                                <div class="col-sm-6 col-12" style="padding: 0px 7px !important;">
                                                    <input type="text" v-model="txtMaNVTC_2" class="form-control highlight">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>



                </div>
                <div class="row">
                  <div class="col-sm-8 col-12">
                    <div class="info-row">
                      <div class="key">Tài khoản</div>
                      <div class="value">
                        <input
                          type="text"
                          v-model="txtTaiKhoanTT"
                          class="form-control"
                        />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-4 col-12">
                    <div class="info-row">
                      <div class="key w100">Mã số thuế</div>
                      <div class="value">
                        <input
                          type="text"
                          v-model="txtMaSoThueTT"
                          class="form-control"
                        />
                      </div>
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Ngân hàng</div>
                  <div class="value">
                    <div class="select-custom">
                      <SelectExt
                        v-model="ddlNganHangTT"
                        :options="this.options.nganhang"
                        :disabled="true"
                      >
                        <option disabled value="0">Select one</option>
                      </SelectExt>
                    </div>
                  </div>
                </div>

                <div class="info-row">
                  <div class="key">Ghi chú</div>
                  <div class="value">
                    <input
                      type="text"
                      v-model="txtGhiChuTT"
                      class="form-control"
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="box-form">
            <div class="legend-title">Danh sách thanh toán</div>
            <div class="table-content">

              <DataGrid
                :columns="options.col_chuyenquyen"
                v-bind:dataSource="options.ds_chuyenquyen"
                :showColumnCheckbox="false"
                :enable-paging-server="false"
                :allowPaging="true"
                :showFilter="true"
                :enabledSelectFirstRow="false"
              >
              </DataGrid>

            </div>
          </div>
        </div>

        <div id="tab-2" class="tab-pane fade">
                <div class="box-form">
                    <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key">Số máy/Acc</div>
                                        <div class="value">
                                            <input type="text" v-model="txtF3_Acc" class="form-control highlight">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key">Thanh toán</div>
                                        <div class="value">
                                                <SelectExt
                                                    v-model="ddlF3_ThanhToan"
                                                    :options="this.options.F3thanhtoan"
                                                >
                                                    <option disabled value="0">Select one</option>
                                                </SelectExt>
                                        </div>
                                    </div>
                                </div>
                            </div>                    
                            
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key">ĐV thuê bao</div>
                                            <div class="value">
                                                <SelectExt
                                                    v-model="ddlF3_DVThueBao"
                                                    :options="this.options.F3dvthuebao"
                                                    :disabled="true"
                                                >
                                                    <option disabled value="0">Select one</option>
                                                </SelectExt>
                                            </div>
                                    </div>
                                </div>
                            </div>                          

                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key">Tên thuê bao</div>
                                        <div class="value">
                                            <input type="text" v-model="txtF3_TenThueBao" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key">Đối tượng</div>
                                            <div class="value">
                                                <SelectExt
                                                    v-model="ddlF3_DoiTuong"
                                                    :options="this.options.F3doituong"
                                                >
                                                    <option disabled value="0">Select one</option>
                                                </SelectExt>
                                            </div>
                                    </div>
                                </div>                                
                            </div>         

                            <div class="info-row">
                                <div class="key">Địa chỉ TB (*)</div>
                                <div class="value">
                                    <input type="text" v-model="txtF3_DiaChiTB" class="form-control">
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key">Địa chỉ LĐ (*)</div>
                                <div class="value">
                                    <input type="text" v-model="txtF3_DiaChiLD" class="form-control">
                                </div>
                            </div>


                        </div>
                        <div class="col-sm-6 col-12">

                            <div class="row">
                                <div class="col-sm-5 col-12">
                                    <div class="info-row">
                                        <div class="key">Dịch vụ</div>
                                            <div class="value">
                                                <SelectExt
                                                    v-model="ddlF3_DichVu"
                                                    :options="this.options.F3dichvu"
                                                >
                                                    <option disabled value="0">Select one</option>
                                                </SelectExt>
                                            </div>
                                    </div>
                                </div>
                                <div class="col-sm-7 col-12">
                                    <div class="info-row">
                                        <div class="key">Loại hình</div>
                                            <div class="value">
                                                <SelectExt
                                                    v-model="ddlF3_LoaiHinh"
                                                    :options="this.options.F3loaihinh"
                                                >
                                                    <option disabled value="0">Select one</option>
                                                </SelectExt>
                                            </div>
                                    </div>
                                </div>                                
                            </div>    


                            <div class="info-row">
                                <div class="key">Kiểu yêu cầu</div>
                                            <div class="value">
                                                <SelectExt
                                                    v-model="ddlF3_KieuYeuCau"
                                                    :options="this.options.F3kieuyeucau"
                                                >
                                                    <option disabled value="0">Select one</option>
                                                </SelectExt>
                                            </div>
                            </div>

                            <div class="info-row">
                                <div class="key">Mức cước TB</div>
                                    <div class="value">
                                                <SelectExt
                                                    v-model="ddlF3_MucCuocTB"
                                                    :options="this.options.F3muccuoctb"
                                                >
                                                    <option disabled value="0">Select one</option>
                                                </SelectExt>
                                </div>
                            </div>

                            <div id="div_tocdo" style="display: none">
                            <div class="info-row" >
                                <div class="key">Tốc độ</div>
                                    <div class="value">
                                                <SelectExt
                                                    v-model="ddlF3_TocDo"
                                                    :options="this.options.F3tocdo"
                                                >
                                                    <option disabled value="0">Select one</option>
                                                </SelectExt>
                                </div>
                            </div>
                            </div>

                            <div id="div_didong" style="display: none">
                            <div class="info-row">
                                <div class="key">Gói cước DĐ</div>
                                <div class="value">
                                                <SelectExt
                                                    v-model="ddlF3_GoiCuocDD"
                                                    :options="this.options.F3goicuocdd"
                                                >
                                                    <option disabled value="0">Select one</option>
                                                </SelectExt>
                                </div>
                            </div>

                            <div class="info-row">
                                <div class="key">ĐT Vinaphone</div>
                                <div class="value">
                                                <SelectExt
                                                    v-model="ddlF3_DTVinaphone"
                                                    :options="this.options.F3dtvinaphone"
                                                >
                                                    <option disabled value="0">Select one</option>
                                                </SelectExt>
                                </div>
                            </div>

                        </div>
                        </div>
                    </div>
                </div>
                <div class="box-form">
                    <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="legend-title">Dịch vụ cộng thêm</div>
                            
                            <div class="info-row">
                                <div class="key"></div>
                                <div class="value">
                                    <textarea name="" class="form-control" style="height: 100px;resize: none;"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="legend-title">Dịch vụ khác</div>
                            
                            <div class="info-row">
                                <div class="key"></div>
                                <div class="value">
                                    <textarea name="" class="form-control" style="height: 100px;resize: none;"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                </div>
                <div class="box-form">
                    <div class="legend-title">Danh sách thuê bao</div>
                    <div class="table-content">
                      <DataGrid
                        :columns="options.col_F3"
                        v-bind:dataSource="options.ds_F3"
                        :showColumnCheckbox="false"
                        :enable-paging-server="false"
                        :allowPaging="true"
                        :showFilter="true"
                        :enabledSelectFirstRow="false"
                      >
                      </DataGrid>

                    </div>
                </div>
        </div>
      </div>
    </div>


  </div>
</template>
<script>
import api from "./api";
import define from "./define";
import moment from "moment";
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"



export default {
  data() {
    return {
      ...define,
      tag: "0",
      txtMaGD: "",
      txtNgayYC: "",
      txtMAHD: "",
      txtNgayLapHD: "",
      txtMaKH: "",
      txtTenKH: "",
      txtDiaChiKH: "",
      ddlLoaiGiayTo: "",
      txtSoGiayTo: "",
      txtNgayHetHan: "",
      txtNgaySinh: "",
      txtNgayCap: "",
      txtNoiCap: "",
      ddlQuocTich: 0,
      ddlNganhNghe: "",
      ddlDanToc: "",
      ddlLoaiKH: "",
      txtNguoiDaiDien: "",
      txtChucVu: "",
      txtDiaChiNDD: "",
      txtSDT: "",
      txtFax: "",
      txtHoKhau: "",
      txtNoiCapHK: "",
      txtNgayCapHK: "",
      txtMaSoThue: "",
      txtTaiKhoan: "",
      chkNganHang: false,
      ddlNganHang: "",
      txtDacTrung: "",
      txtGhiChu: "",
      txtMaTT: "",
      txtMaDaiDien: "",
      txtTenThanhToan: "",
      txtDiaChiTT: "",
      txtDiaChiCT: "",
      txtDiaChiBC: "",
      ddlDonViTT: "",
      ddlHinhThucTT: "",
      txtMaNVTC_1: "",
      txtMaNVTC_2: "",
      txtTaiKhoanTT: "",
      txtMaSoThueTT: "",
      ddlNganHangTT: "",
      txtGhiChuTT: "",
      rdo_GioiTinh: 0,
      token_donvi_id: 0,
      token_nhanvien_id: 0,
      token_donvi_dl_id: 0,
      token_nguoidung_id: 0,
      Tag: 0,

        //tab F3
        txtF3_Acc:"",
        ddlF3_ThanhToan:"",
        ddlF3_DVThueBao:"",
        txtF3_TenThueBao:"",
        ddlF3_DoiTuong:"",
        txtF3_DiaChiTB:"",
        txtF3_DiaChiLD:"",
        ddlF3_DichVu:"",
        ddlF3_LoaiHinh:"",
        ddlF3_KieuYeuCau:"",
        ddlF3_MucCuocTB:"",
        ddlF3_TocDo:"",
        ddlF3_GoiCuocDD:"",
        ddlF3_DTVinaphone:"",


      options: {
        loaigiayto: [],
        quoctich: [],
        nganhnghe: [],
        dantoc: [],
        loaikh: [],
        nganhang: [],
        donvitt: [],
        hinhthuctt: [],
        //danh sách chuyển quyền
        ds_chuyenquyen:[],
        col_chuyenquyen:[
          {
            fieldName: "stt",
            headerText: "STT",
            allowSorting: true,
            autoFit: true,
            width:"50",
            textAlign: "center",
          },
          {
            fieldName: "MA_TT",
            headerText: "Mã TT",
            allowSorting: true,
            autoFit: true,
            textAlign: "left",
          },
          {
            fieldName: "ten_tt",
            headerText: "Tên TT",
            allowSorting: true,
            autoFit: true,
            textAlign: "left",
          },
          {
            fieldName: "diachi_tt",
            headerText: "Địa chỉ TT",
            allowSorting: true,
            autoFit: true,
            textAlign: "left",
          }
        ],
        ds_F3:[],
        col_F3:[
          {
            fieldName: "MA_TT",
            headerText: "Mã TT",
            allowSorting: true,
            autoFit: true,
            textAlign: "left",
          },
          {
            fieldName: "ten_tt",
            headerText: "Tên TT",
            allowSorting: true,
            autoFit: true,
            textAlign: "left",
          },
          {
            fieldName: "diachi_tt",
            headerText: "Địa chỉ TT",
            allowSorting: true,
            autoFit: true,
            textAlign: "left",
          }
        ],


        //F3
        F3thanhtoan:[],
        F3dvthuebao:[],
        F3doituong:[],
        F3dichvu:[],
        F3loaihinh:[],
        F3kieuyeucau:[],
        F3muccuoctb:[],
        F3tocdo:[],
        F3goicuocdd:[],
        F3dtvinaphone:[],


      },
    };
  },

  props: {},

  created() {

    this.Tag = this.$route.query.tag;
    this.token_donvi_id = this.$root.token.getDonViID();
    this.token_nhanvien_id = this.$root.token.getNhanVienID();
    this.token_donvi_dl_id = this.$root.token.getDonViDuLieuID();
    this.token_nguoidung_id = this.$root.token.getNguoiDungID();


    this.LAY_HTTT_LOAD();
    this.LAY_DS_LoaiKH();
    this.LAY_DS_LoaiGiayTo();
    this.LAY_DS_QuocTich();
    this.LAY_DS_NganhNghe();
    this.LAY_DS_DanToc();
    this.LAY_DS_NganHang();
    this.LAY_DS_DVThanhToan();
    this.LAY_DS_DVLapDat();
    this.LAY_DS_DoiTuong();
    this.LAY_DS_DichVu();
    this.LAY_DS_DTVINAPHONE();
  },
  components:
  {
     SearchContractModal
  },
  watch: {
      ddlF3_DichVu(newdata)
      {
          this.ddlF3_DichVu = newdata;
          this.LAY_DS_LOAIHINH_TB(newdata);
          this.LAY_DS_TocDo(newdata);
          this.LAY_DS_KieuLD();



          document.getElementById('div_tocdo').style.display="none";
          document.getElementById('div_didong').style.display="none";
          if(newdata  == 2)//di động
          {
            document.getElementById('div_tocdo').style.display="none";
            document.getElementById('div_didong').style.display="block";
          }
          if(newdata  == 4 || newdata  == 13 || newdata  == 14 || newdata  == 15 || newdata  == 16)
          //băng rộng cố định, hội nghị truyền hình, ATBMTT,CNTT, Trung tâm dữ liệu
          {
            document.getElementById('div_tocdo').style.display="block";
            document.getElementById('div_didong').style.display="none";
          } 



      },
      ddlF3_LoaiHinh(newdata)
      {
        this.ddlF3_LoaiHinh= newdata;
        //load danh sách kiểu lắp đặt
        this.LAY_DS_KieuLD();
      }

  },

  methods: {
    LAY_HTTT_LOAD() {
      api.LAY_HTTT(this.axios, {}).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
          items.push({ id: item.HTTT_ID, text: item.HINHTHUC_TT });
        });
        this.options.hinhthuctt = items;
        this.ddlHinhThucTT = items[0].id;
      });
    },

    LAY_DS_LoaiKH() {
      api.LAY_DS_LoaiKH(this.axios, {}).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
          items.push({ id: item.LOAIKH_ID, text: item.TEN_LOAIKH });
        });
        this.options.loaikh = items;
        this.ddlLoaiKH = items[0].id;
      });
    },


    LAY_DS_LoaiGiayTo() {
      api.LAY_DS_LoaiGiayTo(this.axios, {}).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
          items.push({ id: item.LOAIGT_ID, text: item.LOAI_GT });
        });
        this.options.loaigiayto = items;
        this.ddlLoaiGiayTo = items[0].id;
      });
    },


    LAY_DS_QuocTich() {
      api.LAY_DS_QuocTich(this.axios, {}).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
          items.push({ id: item.QUOCTICH_ID, text: item.TENQUOCTICH });
        });
        this.options.quoctich = items;
        this.ddlQuocTich = items[0].id;
      });
    },


    LAY_DS_NganhNghe() {
      api.LAY_DS_NganhNghe(this.axios, {}).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
          items.push({ id: item.NGANHNGHE_ID, text: item.TENNGANHNGHE });
        });
        this.options.nganhnghe = items;
        this.ddlNganhNghe = items[0].id;
      });
    },

    LAY_DS_DanToc() {
      api.LAY_DS_DanToc(this.axios, {}).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
          items.push({ id: item.DANTOC_ID, text: item.TENDANTOC });
        });
        this.options.dantoc = items;
        this.ddlDanToc = items[0].id;
      });
    },


    LAY_DS_NganHang() {
      api.LAY_DS_NganHang(this.axios, {}).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
          items.push({ id: item.NGANHANG_ID, text: item.TEN_NH });
        });
        this.options.nganhang = items;
        this.ddlNganHang = items[0].id;
        this.ddlNganHangTT = items[0].id;
      });
    },    

    LAY_DS_DVThanhToan() {
      api.LAY_DS_DVThanhToan(this.axios, {
          "loai_donvi_id":2
      }).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
          items.push({ id: item.ID, text: item.NAME });
        });
        this.options.donvitt = items;
        this.ddlDonViTT = items[0].id;

      });
    },    

    LAY_DS_DVLapDat() {
      api.LAY_DS_DVThanhToan(this.axios, {
          "loai_donvi_id":3
      }).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
          items.push({ id: item.ID, text: item.NAME });
        });
        this.options.F3dvthuebao = items;
        this.ddlF3_DVThueBao = items[0].id;

      });
    },   

    LAY_DS_DoiTuong() {
      api.LAY_DS_DoiTuong(this.axios, {

      }).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
          items.push({ id: item.doituong_id, text: item.ten_dt });
        });
        this.options.F3doituong = items;
        this.ddlF3_DoiTuong = items[0].id;

      });
    },   

    LAY_DS_DichVu() {
      api.LAY_DS_DichVu(this.axios, {
          "p_nguoidung_id": this.token_nguoidung_id
      }).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
          items.push({ id: item.dichvuvt_id, text: item.ten_dvvt });
        });
        this.options.F3dichvu = items;
        this.ddlF3_DichVu = items[0].id;

      });
    },  

    LAY_DS_DTVINAPHONE() {
      api.LAY_DS_DTVINAPHONE(this.axios, {
      }).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
          items.push({ id: item.doituong_id_neo, text: item.ten_dt_neo });
        });
        this.options.F3dtvinaphone = items;
        this.ddlF3_DTVinaphone = items[0].id;

      });
    }, 


    LAY_DS_LOAIHINH_TB(dichvuvt_id) {
       this.axios
        .get(
          "/web-quantri/danhmuc-chung/get_loai_hinh_tb_by_dichvuvt_id/" +
            dichvuvt_id +
            ""
        )
        .then((res) => {
            let items = [];
            res.data.data.forEach(function (item) {
              items.push({ id: item.LOAITB_ID, text: item.LOAIHINH_TB });
            });
            this.options.F3loaihinh = items;
            this.ddlF3_LoaiHinh = items[0].id;
            });
    },


    LAY_DS_TocDo(dichvuvt_id) {
       this.axios
        .get(
          "web-hopdong/phattrienthuebao/get_tocdo_adsl_by_dichvuvt_id/" +
            dichvuvt_id +
            ""
        )
        .then((res) => {
            let items = [];
            res.data.data.forEach(function (item) {
              items.push({ id: item.tocdo_id, text: item.thuonghieu });
            });
            this.options.F3tocdo = items;
            this.ddlF3_TocDo = items[0].id;
            });
    },



    LAY_DS_GOICUOC_DIDONG() {
      api.LAY_DS_GOICUOC_DIDONG(this.axios, {
          "loaitb_id": this.ddlF3_LoaiHinh
      }).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
          items.push({ id: item.goicuoc_id, text: item.tengoicuoc });
        });
        this.options.F3goicuocdd = items;
        this.ddlF3_GoiCuocDD = items[0].id;

      });
    },  


    LAY_DS_KieuLD() {

      api.LAY_DS_KieuLD(this.axios, {
            "vloaihd_id":2,
            "vloaitb_id":this.ddlF3_LoaiHinh
      }).then((res) => {
        let items = [];
        res.data.data.forEach(function (item) {
          items.push({ id: item.kieuld_id, text: item.ten_kieuld });
        });
        this.options.F3kieuyeucau = items;
        this.ddlF3_KieuYeuCau = items[0].id;

      });
    },   



   LAY_DS_MATT_THEO_HDKHID(hdkh) {
      let items = [];
       this.axios
        .get(
          "/web-hopdong/hopdong/lay_ds_ma_thanhtoan?hdkhId=" +
            hdkh +
            ""
        )
        .then((response) => {
          response.data.data.forEach(function (item) {
            items.push({ id: item.ID, text: item.NAME });
          });

        this.options.F3thanhtoan = items;
        this.ddlF3_ThanhToan = items[0].id;

        });
   },

   LAY_DS_HDTT_THEO_HDKHID(hdkh) {
      let danhsach = [];
       this.axios
        .get(
          "/web-hopdong/hopdong/lay_ds_hopdong_thanhtoan_theo_hdkh?hdKhId=" +
            hdkh +
            ""
        )
        .then((response) => {
          danhsach = response.data.data;
           this.options.ds_chuyenquyen = danhsach;
            if(this.options.ds_chuyenquyen.length > 0)
            {
              this.txtMaTT = danhsach[0].ma_tt;
              this.txtTenThanhToan = danhsach[0].ten_tt;
              this.txtDiaChiTT = danhsach[0].diachi_tt;
              this.txtDiaChiBC = danhsach[0].diachi_bc;
              this.txtMaSoThueTT = danhsach[0].mst;
              this.txtTaiKhoanTT = danhsach[0].stk;
              if(danhsach[0].nganhang_id !== null)
              {
                this.ddlNganHangTT = danhsach[0].nganhang_id;
              }                
              this.ddlHinhThucTT = danhsach[0].httt_id;
              this.txtMaDaiDien = danhsach[0].matb_dd;
              this.txtDiaChiCT = danhsach[0].diachi_ct;
              this.ddlDonViTT = danhsach[0].donvi_id;
              this.txtGhiChuTT = danhsach[0].ghichu;
              this.txtMaNVTC_1 = danhsach[0].manv_tc;
              this.txtMaNVTC_2 = danhsach[0].ma_tuyen;
            }


        });

      




    },


    LAY_DS_HOPDONG_THEO_MA_GD() {
      this.loading(true);

      api
        .LAY_DS_HOPDONG_THEO_MA_GD(this.axios, {
          "donvi_dl_id": this.token_donvi_dl_id,
          "donvi_id": this.token_donvi_id,
          "loaihd_id": "2",
          "ma_gd": this.txtMaGD,
          "nhanvien_id": this.token_nhanvien_id,
          "tthd_id": this.Tag

          // donvi_dl_id: 0,
          // donvi_id: 0,
          // loaihd_id: 0,
          // ma_gd: "HPG-LD/00608240",
          // nhanvien_id: 0,
          // tthd_id: 0
        })
        .then((res) => {
          let danhsach = res.data.data ? res.data.data : [];

          let hdkh_id = danhsach[0].hdkh_id;
          this.txtMaKH = danhsach[0].ma_kh;
          this.txtTenKH = danhsach[0].ten_kh;
          this.txtDiaChiKH = danhsach[0].diachi_kh;
          this.txtSDT = danhsach[0].so_dt;
          this.txtFax = danhsach[0].so_fax;
          this.txtHoKhau = danhsach[0].hokhau;
          this.txtNoiCapHK = danhsach[0].noicap_hk;
          this.txtMaSoThue = danhsach[0].mst;
          if(danhsach[0].ngay_hk !== null)
          {
            this.txtNgayCapHK = moment(danhsach[0].ngay_hk).format("DD/MM/YYYY");       
          }
          
          if(danhsach[0].ngay_sn !== null)
          {
            this.txtNgaySinh = moment(danhsach[0].ngay_sn).format("DD/MM/YYYY");
          }

          if(danhsach[0].ngaylap_hd !== null)
          {
            this.txtNgayLapHD = moment(danhsach[0].ngaylap_hd).format("DD/MM/YYYY");
          }
          


          this.txtSoGiayTo = danhsach[0].so_gt;

          if(danhsach[0].ngaycap !== null)
          {
            this.txtNgayCap = moment(danhsach[0].ngaycap).format("DD/MM/YYYY");
          }          

          this.ddlLoaiGiayTo = danhsach[0].loaigt_id;
          this.txtNoiCap = danhsach[0].noicap;
          this.txtMAHD = danhsach[0].ma_hd;

          if(danhsach[0].ngay_yc !== null)
          {
            this.txtNgayYC = moment(danhsach[0].ngay_yc).format("DD/MM/YYYY");
          }             

          this.txtNguoiDaiDien = danhsach[0].nguoi_dd;
          this.txtChucVu = danhsach[0].chucdanh_dd;
          this.txtGhiChu = danhsach[0].ghichu;
          if(danhsach[0].loaikh_id !== null)
          {
            this.ddlLoaiKH = danhsach[0].loaikh_id;

          }
          

          if (danhsach[0].gioitinh != 1 || danhsach[0].gioitinh != 0) {
            this.rdo_GioiTinh = 2;
          } else {
            this.rdo_GioiTinh = danhsach[0].gioitinh;
          }

          if(danhsach[0].quoctich_id !== null)
          {
            this.ddlQuocTich = danhsach[0].quoctich_id;
          } else{
            this.ddlQuocTich = 0;
          }
          
          if(danhsach[0].nganhnghe_id !== null)
          {
            this.ddlNganhNghe = danhsach[0].nganhnghe_id;
          } else{
            this.ddlNganhNghe = 0;
          }

          if(danhsach[0].dantoc_id !== null)
          {
            this.ddlDanToc = danhsach[0].dantoc_id;
          } else{
            this.ddlDanToc = 0;
          }          

          this.txtDiaChiNDD = danhsach[0].diachi_ndd;

          if(danhsach[0].ngayhethan_gt !== null)
          {
            this.txtNgayHetHan = moment(danhsach[0].ngayhethan_gt).format("DD/MM/YYYY");
          }   
          this.txtDacTrung = danhsach[0].dactrung;
          this.txtTaiKhoan = danhsach[0].stk;

          if(danhsach[0].nganhang_id !== null)
          {
            this.chkNganHang = true;
            this.ddlNganHang = danhsach[0].nganhang_id;
          }  

          //this.LAY_DS_HDTT_THEO_HDKHID(hdkh_id);//822905
          this.LAY_DS_HDTT_THEO_HDKHID(hdkh_id);
          this.LAY_DS_MATT_THEO_HDKHID(hdkh_id);
          this.loading(false);
        });
    },


        btnLayMaGD: async function(){
            //this.$root.$toast.error('view popup “Tra cứu hợp đồng phụ lục” – Tham chiếu UR2.8.001 – task BSS-1229')
            this.$refs.popupSearchContract.showModal();
        },


    acceptSearchContract: async function(item) {
        this.txtMaGD = item.ma_gd;
        if(this.txtMaGD && this.txtMaGD.trim()!="")
            this.LAY_DS_HOPDONG_THEO_MA_GD();
    },


    keymonitor: function (event) {
      if (event.key == "Enter") {
        if (this.txtMaGD.trim() != "") {
          this.LAY_DS_HOPDONG_THEO_MA_GD();
        } else {
          this.$toast.error("Chưa nhập thông tin mã giao dịch!!!");
        }
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