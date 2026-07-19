<!-- frm_Giahan_DHTT_A2P -->

<style scoped>
.list-actions-top .list>li>div.button {
  display: block;
  padding: 2px 15px;
  color: #0176ff;
  border-left: 1px solid #bae7ff;
  text-decoration: none;
  font-weight: 600;
  cursor: pointer;
}
</style>

<template>
  <ejs-dialog :enableResize="true" :isModal="true" :allowDragging="true" :visible="false"
    :animationSettings="animationSettings" ref="frm_Giahan_DHTT_A2P" :position="position"
    :header="'Kích hoạt tạo brandname'" showCloseIcon="true" width="75%" target="#app .main-wrapper">
    <frmBRNGuiMail ref="frmBRNGuiMail" />
    <div class="dialogCreateIssue">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript:;" v-on:click="btnNhapMoi_Click" :class="{ disabled: !btnNhapMoi.Enabled }">
              <span class="icon one-file-attach"></span> Gửi yêu cầu
            </a>
          </li>
          <li>
            <a href="javascript:;" v-on:click="btnCapNhat_Click" :class="{ disabled: !btnCapNhat.Enabled }">
              <span class="icon one-save"></span> Hoàn thành
            </a>
          </li>
          <li>
            <a href="javascript:;" v-on:click="tsbtnGuiMailKH_Click" :class="{ disabled: !tsbtnGuiMailKH.Enabled }">
              <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Gửi Mail KH
            </a>
          </li>
          <li>
            <a href="javascript:;" v-on:click="tsbtnUpdate_Click" :class="{ disabled: !tsbtnUpdate.Enabled }">
              <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Bổ sung hồ sơ
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin brandname</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="row">
                <div class="col-sm-7 col-12">
                  <div class="info-row">
                    <div class="key">Hợp đồng</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="controls.hopdong" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key">Mã thuê bao</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="controls.ma_tb" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key">Tên khách hàng</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="controls.ten_kh" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key">Tên nhãn</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="controls.ten_nhan" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-7 col-12">
                  <div class="info-row">
                    <div class="key">Ngày hết hạn</div>
                    <div class="value">
                      <div class="input-icon-right">
                        <date-picker input-class="form-control" :format="dateFormat" :value-type="'format'"
                          v-model="controls.ngay_hh"></date-picker>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key">Mã số thuế</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="controls.ma_so_thue" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key">File hồ sơ</div>
                    <!-- <div class="value">                                   
                                <SelectExt v-model="controls.cboHoSo.value" :allowFiltering="true" dataTextField="file_name"
                                            dataValueField="file_path" :dataSource="controls.cboHoSo.list">
                                </SelectExt>
                                
                            </div> -->
                    <div class="value select-custom">
                      <ejs-multiselect v-model="controls.cboHoSo.value" id="ds_nguyennhan_id"
                        :dataSource="controls.cboHoSo.list" placeholder="Chọn file" selectAllText="Chọn tất cả"
                        :showSelectAll="true" locale="vi-VN" :showDropDownIcon="true" unSelectAllText="Bỏ chọn tất cả"
                        mode="CheckBox" :fields="{ text: 'file_name', value: 'file_path' }">
                      </ejs-multiselect>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key">Ghi chú</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="controls.ghichu" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key">Loại nhãn Vinaphone</div>
                    <div class="value">
                      <SelectExt v-model="controls.cboLoaiVina.value" :allowFiltering="true"
                        dataTextField="label_type_name" dataValueField="label_type_id"
                        :dataSource="controls.cboLoaiVina.list">
                      </SelectExt>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key">Giá Vinaphone</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="controls.gia_vinaphone" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key">Loại nhãn Mobiphone</div>
                    <div class="value">
                      <SelectExt v-model="controls.cboLoaiMobi.value" :allowFiltering="true"
                        dataTextField="label_type_name" dataValueField="label_type_id"
                        :dataSource="controls.cboLoaiMobi.list">
                      </SelectExt>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key">Giá Mobiphone</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="controls.gia_vms" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key">Loại nhãn Viettel</div>
                    <div class="value">
                      <SelectExt v-model="controls.cboLoaiVtl.value" :allowFiltering="true"
                        dataTextField="label_type_name" dataValueField="label_type_id"
                        :dataSource="controls.cboLoaiVtl.list">
                      </SelectExt>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key">Giá Viettel</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="controls.gia_vtl" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key">Loại nhãn Vietnammobile</div>
                    <div class="value">
                      <SelectExt v-model="controls.cboLoaiVnm.value" :allowFiltering="true"
                        dataTextField="label_type_name" dataValueField="label_type_id"
                        :dataSource="controls.cboLoaiVnm.list">
                      </SelectExt>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key">Giá Vietnammobile</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="controls.gia_vnm" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key">Loại nhãn Gtel</div>
                    <div class="value">
                      <SelectExt v-model="controls.cboLoaiGtel.value" :allowFiltering="true"
                        dataTextField="label_type_name" dataValueField="label_type_id"
                        :dataSource="controls.cboLoaiGtel.list">
                      </SelectExt>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key">Giá Gtel</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="controls.gia_gtel" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key">Loại nhãn Itel</div>
                    <div class="value">
                      <SelectExt v-model="controls.cboLoaiItel.value" :allowFiltering="true"
                        dataTextField="label_type_name" dataValueField="label_type_id"
                        :dataSource="controls.cboLoaiItel.list">
                      </SelectExt>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key">Giá Itel</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="controls.gia_itel" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key">Loại nhãn Mobicast</div>
                    <div class="value">
                      <SelectExt v-model="controls.cboLoaiMobicast.value" :allowFiltering="true"
                        dataTextField="label_type_name" dataValueField="label_type_id"
                        :dataSource="controls.cboLoaiMobicast.list">
                      </SelectExt>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key">Giá Mobicast</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="controls.gia_mbicast" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Yêu cầu tạo Brandname</div>
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="table-content">
                <DataGrid ref="dgvChiTiet" class="table-result table-gachle" v-bind:columns="grcDanhSach.headers"
                  v-bind:dataSource="grcDanhSach.list" @selectedRowChanged="gridChitiet_FocusedRowChanged"
                  :enable-paging-server="false" :allowPaging="true" :showFilter="true">
                </DataGrid>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>