<template>
	<div class="modal-content popup-box">
		<div class="popup-header">
			<div class="title">
				<span class="icon one-notepad"></span>Thông tin kênh trắng
			</div>
			<div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide('popup-ThongTinKenhTrang')"></div>
		</div>
		<div class="list-actions-top">
			<ul class="list">
				<li>
					<a href="#" @click.prevent="tsbtnCapNhan_Click" :class="tsbtnCapNhan.Enabled ? '' : 'disabled'">
						<span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp
						nhận
					</a>
				</li>
				<li>
					<a href="#" @click.prevent="tsbtnNhapMoi_Click" :class="tsbtnNhapMoi.Enabled ? '' : 'disabled'">
						<span class="icon one-file-plus"></span>Nhập mới (F5)
					</a>
				</li>
				<li>
					<a href="#" @click.prevent="tsbtnCapNhatDoThu_Click" :class="tsbtnCapNhatDoThu.Enabled ? '' : 'disabled'">
						<span class="icon one-reload1"></span>Cập nhật
					</a>
				</li>
				<li>
					<a href="#" @click.prevent="tsbtnHuyBo_Click" :class="tsbtnHuyBo.Enabled ? '' : 'disabled'">
						<span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy
					</a>
				</li>
				<li>
					<a href="#">
						<span class="icon one-circle-question"></span>Trợ giúp
					</a>
				</li>
			</ul>
		</div>
		<div class="popup-body">
			<div class="box-form">
				<div class="legend-title">Thông tin khách hàng</div>
				<div class="row" style="font-size: 0.875rem">
					<div class="col-sm-6 col-12">
						<div class="row">
							<div class="col-sm-6 col-12">
								<div class="info-row">
									<div class="key w80">Mã KH (*)</div>
									<div class="value">
										<div class="input-more-button">
											<button class="btn btn-sm" @click="$refs.searchAccountModal.showModal()">
												<span class="-ap icon-more_horiz"></span>
											</button>
											<SearchAccountModal ref="searchAccountModal" @form-close="searchAccountModalClose"
											:vdichvuvt_id="formThongTinThueBao.dichVuId"
											>
											</SearchAccountModal>
											<input type="text" ref="txtMaKH" v-model="formThongTinKhachHang.maKH"
												v-on:keyup.enter="txtMaKH_KeyPress" class="form-control form-control-sm highlight fw6" />
										</div>
									</div>
								</div>
							</div>
							<div class="col-sm-6 col-12">
								<div class="info-row">
									<div class="key w80">Điện thoại</div>
									<div class="value">
										<input type="text" v-model="formThongTinKhachHang.dienThoai"
											class="form-control form-control-sm fw6" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6 col-12">
								<div class="info-row">
									<div class="key w80">Tên KH</div>
									<div class="value">
										<input type="text" v-model="formThongTinKhachHang.tenKhachHang"
											class="form-control form-control-sm fw6" />
									</div>
								</div>
							</div>
							<div class="col-sm-6 col-12">
								<div class="info-row">
									<div class="key w80">Số giấy tờ</div>
									<div class="value">
										<input type="text" v-model="formThongTinKhachHang.soGiayTo"
											class="form-control form-control-sm fw6" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6 col-12">
								<div class="info-row">
									<div class="key w80">Địa chỉ KH(*)</div>
									<div class="value">
										<div class="input-more-button">
											<button class="btn btn-sm" v-b-modal.popupDiaChiKH >
												<span class="-ap icon-more_horiz"></span>
											</button>
											<ModalChonDiaChi modalId="popupDiaChiKH" :data="diachiKH" @xacnhan="getDiaChiKH">
											</ModalChonDiaChi>
											<input type="text" v-model="formThongTinKhachHang.diaChiKH"
												class="form-control form-control-sm fw6" />
										</div>
									</div>
								</div>
							</div>
							<div class="col-sm-6 col-12">
								<div class="info-row">
									<div class="key w80">Ngày sinh/TL</div>
									<div class="value">
										<k-date-picker format="DD/MM/YYYY" v-model="formThongTinKhachHang.ngaySinh" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6 col-12">
								<div class="info-row">
						
									<vue-select 
										v-model="formThongTinKhachHang.loaiGiayToId" :labelWidth="'80'" :options="formThongTinKhachHang.loaiGiayToOptions"
										labelField="LOAI_GT" valueField="LOAIGT_ID" label="Loại giấy tờ"
										ref="">
									</vue-select>
								</div>
							</div>
							<div class="col-sm-6 col-12">
								<div class="info-row">
									<vue-select 
										v-model="formThongTinKhachHang.quocTichId" :labelWidth="'80'" :options="formThongTinKhachHang.quocTichOptions"
										labelField="TENQUOCTICH" valueField="QUOCTICH_ID" label="Quốc tịch"
										ref="">
									</vue-select>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6 col-12">
								<div class="info-row">
									<div class="key w80">Ngày cấp</div>
									<div class="value">
										<div class="select-custom">
											<k-date-picker format="DD/MM/YYYY" v-model="formThongTinKhachHang.ngayCap" />
										</div>
									</div>
								</div>
							</div>
							<div class="col-sm-6 col-12">
								<div class="info-row">
									<vue-select 
										v-model="formThongTinKhachHang.danTocId" :labelWidth="'80'" :options="formThongTinKhachHang.danTocOptions"
										valueField="DANTOC_ID" labelField="TENDANTOC"  label="Dân tộc"
										ref="">
									</vue-select>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6 col-12">
								<div class="info-row">
									<div class="key w80">Ngày hết hạn</div>
									<div class="value">
										<div class="select-custom">
											<k-date-picker format="DD/MM/YYYY" v-model="formThongTinKhachHang.ngayHetHan" />
										</div>
									</div>
								</div>
							</div>
							<div class="col-sm-6 col-12">
								<div class="info-row">
									<div class="key w80">Giới tính</div>
									<div class="value">
										<b-form-select class="d-inline-block" v-model="formThongTinKhachHang.gioiTinh"
											:options="formThongTinKhachHang.gioiTinhOptions"></b-form-select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6 col-12">
								<div class="info-row">
									<div class="key w80">Ngành nghề</div>
									<div class="value">
										<div class="input-more-button">
											<button class="btn" @click.prevent="popupTieuNganh">
												<span class="-ap icon-more_horiz"></span>
											</button>
											<ComboboxGrid class="select-custom" :columns="[
												{
													fieldName: 'TENNGANHNGHE',
													headerText: '',
													allowFiltering: true,
													width: '400',
												},
												{
													fieldName: 'MANGANH',
													headerText: '',
													allowFiltering: true,
													width: '70',
												},
											]" :dataSource="listbox.nganhngheloc.list" textField="TENNGANHNGHE"
												valueField="NGANHNGHE_ID" @selectedChanged="
													(item) => {
												
														if (item && item.NGANHNGHE_ID) {
												
															listbox.nganhngheloc.value = item.NGANHNGHE_ID;
															if (listbox.nganhngheloc.value != -1) {
																popupTieuNganh();
															}
														}
												
													}
												">
											</ComboboxGrid>
										</div>
									</div>
								</div>
							</div>
							<div class="col-sm-6 col-12">
								<div class="info-row">
									<div class="key w80">Loại KH</div>
									<div class="value">
										<div class="select-custom">
											<!-- <button class="btn btn-sm" @click="">
                                                <span class="-ap icon-more_horiz"></span>
                                            </button> -->
											<ejs-dropdownlist :allowFiltering="true" v-model="formThongTinKhachHang.loaiKHId"
												:fields="{ value: 'loaikh_id', text: 'ten_loaikh_dis' }"
												:dataSource="formThongTinKhachHang.loaiKhachHangOptions" />
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-6 col-12">
						<div class="khach_hang_tsl" v-if="this.dichvuvt_id == 7 || this.dichvuvt_id == 8|| this.dichvuvt_id == 26">
							<div class="row">
								<div class="col-4">
									<div class="info-row">
										<div class="key w65">Account</div>
										<div class="value">
											<input type="text" v-model="formThongTinKhachHang.accountId"
												class="form-control form-control-sm fw6" disabled="tslMaTBWanIsDisabled" />
										</div>
									</div>
								</div>
								<div class="col-4">
									<div class="info-row">
										<div class="key w45">Số ảo</div>
										<div class="value">
											<div class="input-more-button">
												<button class="btn btn-sm" @click="btnSoAoWan_Click">
													<span class="-ap icon-more_horiz"></span>
												</button>
												<input type="text" ref="inputSoAo" v-model="formThongTinKhachHang.soAo"
													class="form-control form-control-sm fw6" />
											</div>
										</div>
									</div>
								</div>
								<div class="col-4">
									<div class="info-row">
										<div class="key w45">Mã TN</div>
										<div class="value">
											<div class="input-more-button">
												<button class="btn btn-sm" @click="btnMaTN_Wan_Click">
													<span class="-ap icon-more_horiz"></span>
												</button>
												<input type="text" v-model="formThongTinKhachHang.maTN"
													class="form-control form-control-sm fw6" />
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-4">
									<div class="info-row">
										<div class="key w65">Loại kênh</div>
										<div class="value">
											<ejs-dropdownlist :allowFiltering="true" v-model="formThongTinKhachHang.loaiKenhId"
												:fields="{ value: 'LOAIKENH_ID', text: 'LOAI_KENH' }"
												:dataSource="formThongTinKhachHang.loaiKenhOptions" />
										</div>
									</div>
								</div>
								<div class="col-4">
									<div class="info-row">
										<div class="key w65">Tốc độ CIR</div>
										<div class="value">
											<ejs-dropdownlist :allowFiltering="true" v-model="formThongTinKhachHang.tocDoCirId"
												:fields="{ value: 'TOCDO_ID', text: 'TOC_DO' }"
												:dataSource="formThongTinKhachHang.tocDoCirOptions" :disabled="
													formThongTinKhachHang.tocDoPirCheck == true
														? true
														: false
												" />
										</div>
									</div>
								</div>
								<div class="col-4">
									<div class="info-row">
										<div class="check-action normal padt7 w90">
											<input v-model="formThongTinKhachHang.tocDoPirCheck" type="checkbox" class="check"
												@change="changeTocDoPIR()" />
											<span class="name">Tốc độ PIR</span>
										</div>
										<div class="value">
											<ejs-dropdownlist :allowFiltering="true" v-model="formThongTinKhachHang.tocDoPirId"
												:fields="{ value: 'TOCDO_ID', text: 'TOC_DO' }"
												:dataSource="formThongTinKhachHang.tocDoPirOptions" :enabled="!this.disabledPIR" />
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-4">
									<div class="info-row">
										<div class="key w65">Loại node</div>
										<div class="value">
											<ejs-dropdownlist :allowFiltering="true" v-model="formThongTinKhachHang.loaiNodeId"
												:dataSource="formThongTinKhachHang.loaiNodeOptions"  :enabled="true"
											/>
											
						
											
										</div>
									</div>
								</div>
								<div class="col-4">
									<div class="info-row">
										<div class="key w65">Chủ quản</div>
										<div class="value">
											<ejs-dropdownlist :allowFiltering="true" v-model="formThongTinKhachHang.chuQuanId"
												:fields="{ value: 'CHUQUAN_ID', text: 'TENCHUQUAN' }"
												:dataSource="formThongTinKhachHang.chuQuanOptions" />
										</div>
									</div>
								</div>
								<div class="col-4">
									<div class="info-row">
										<div class="key w65">Modem</div>
										<div class="value">
											<ejs-dropdownlist :allowFiltering="true" v-model="formThongTinKhachHang.modemId"
												:fields="{ value: 'LOAIMD_ID', text: 'LOAI_MD' }"
												:dataSource="formThongTinKhachHang.modemOptions" />
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-8">
									<div class="info-row">
										<div class="key w65">Hướng KN</div>
										<div class="value">
											<div class="input-more-button">
												<button class="btn btn-sm" @click="showModalHuongKetNoi">
													<span class="-ap icon-more_horiz"></span>
												</button>
												<PopupDSHuongKNV2 ref="popupDSHuongKNV2" :dulieu="duLieuPopupHuongKetNoi"
													@form-close="popupDSHuongKNV2Close"></PopupDSHuongKNV2>
												<input type="text" ref="txtMaKH" v-model="formThongTinKhachHang.huongKN"
													class="form-control form-control-sm fw6" />
											</div>
										</div>
									</div>
								</div>
								<div class="col-4">
									<div class="info-row">
										<div class="key w65">Loại Tbị</div>
										<div class="value">
											<ejs-dropdownlist :allowFiltering="true" v-model="formThongTinKhachHang.loaiThietBiId"
												:fields="{ value: 'LOAITBI_ID', text: 'LOAI_TBI' }"
												:dataSource="formThongTinKhachHang.loaiThietBiOptions" />
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-8">
									<div class="info-row">
										<div class="key w65">Địa chỉ KN</div>
										<div class="value">
											<div class="input-more-button">
												<button class="btn btn-sm" v-b-modal.diaChiKetNoiModal>
													<span class="-ap icon-more_horiz"></span>
												</button>
												<ModalChonDiaChi modalId="diaChiKetNoiModal" :data="formThongTinKhachHang.diaChiKN"
													@xacnhan="getDiaChiKN"></ModalChonDiaChi>
												<input type="text" ref="txtMaKH" v-model="formThongTinKhachHang.diaChiKN"
													class="form-control form-control-sm fw6" />
											</div>
										</div>
									</div>
								</div>
								<div class="col-4">
									<div class="info-row">
										<div class="key w65">Trang bị</div>
										<div class="value">
											<ejs-dropdownlist :allowFiltering="true" v-model="formThongTinKhachHang.trangBiId"
												:fields="{ value: 'TRANGBI_ID', text: 'TENTRANGBI' }"
												:dataSource="formThongTinKhachHang.trangBiOptions" />
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-8">
									<div class="info-row">
										<div class="key w65">Loại cáp</div>
										<div class="value">
											<ejs-dropdownlist :allowFiltering="true" v-model="formThongTinKhachHang.loaiCapId"
												:fields="{ value: 'LOAICAP_ID', text: 'LOAI_CAP' }"
												:dataSource="formThongTinKhachHang.loaiCapOptions" />
										</div>
									</div>
								</div>
								<div class="col-4">
									<div class="info-row">
										<vue-select 
											v-model="formThongTinKhachHang.thoiHanId" 
											:labelWidth="'65'" 
											:options="formThongTinKhachHang.thoiHanOptions"
											valueField="THOIHAN_ID" labelField="TENTHOIHAN"  label="Thời hạn"
											ref="">
										</vue-select>
									</div>
								</div>
							</div>
						</div>
						<!-- Thue bao TSL -->
						<div class="khach_hang_tsl" v-if="this.dichvuvt_id == 9||this.dichvuvt_id == 25">
							<div class="row">
								<div class="col-4">
									<div class="info-row">
										<div class="key w65">Account</div>
										<div class="value">
											<input type="text" v-model="this.formThongTinThueBao.TSL.maTSL"
												class="form-control form-control-sm fw6" :disabled="tslMaTBTslIsDisabled" />
										</div>
									</div>
								</div>
								<div class="col-4">
									<div class="info-row">
										<vue-select 
											v-model="formThongTinThueBao.TSL.loaiKenhId" 
											:labelWidth="'65'" 
											:options="formThongTinKhachHang.loaiKenhOptions"
											valueField="LOAIKENH_ID" labelField="LOAI_KENH"  label="Loại kênh"
											ref="">
										</vue-select>
									</div>
								</div>
								<div class="col-4">
									<vue-select 
										v-model="formThongTinThueBao.TSL.chuQuanId" 
										:labelWidth="'65'" 
										:options="formThongTinKhachHang.chuQuanOptions"
										valueField="CHUQUAN_ID" labelField="TENCHUQUAN"  label="Chủ quản"
										ref="">
									</vue-select>
								</div>
							</div>
							<div class="row">
								<div class="col-4">
									<div class="info-row">
										<div class="key w65">Tốc độ kênh</div>
										<div class="value">
											<ejs-dropdownlist :allowFiltering="true" 
												v-model="formThongTinThueBao.TSL.tocDoKenhId"
												:fields="{ value: 'tocdo_id', text: 'toc_do' }"
												:dataSource="formThongTinThueBao.tocDoKenhOptions" 
											/>
										</div>
									</div>
								</div>
								<div class="col-4">
									<vue-select 
										v-model="formThongTinThueBao.TSL.loaiCapDauId" 
										:labelWidth="'65'" 
										:options="formThongTinKhachHang.loaiCapOptions"
										valueField="LOAICAP_ID" labelField="LOAI_CAP"  label="Loại cáp đầu"
										ref="">
									</vue-select>
								</div>
								<div class="col-4">
									<vue-select 
										v-model="formThongTinThueBao.TSL.loaiThietBiDauId" 
										:labelWidth="'65'" 
										:options="formThongTinKhachHang.loaiThietBiOptions"
										valueField="LOAITBI_ID" labelField="LOAI_TBI"  label="Loại TB đầu"
										ref="">
									</vue-select>
								</div>
							</div>
							<div class="row">
								<div class="col-4">
									<vue-select 
										v-model="formThongTinThueBao.TSL.thoiHanId" 
										:labelWidth="'65'" 
										:options="formThongTinKhachHang.thoiHanOptions"
										valueField="THOIHAN_ID" labelField="TENTHOIHAN"  label="Thời hạn"
										ref="">
									</vue-select>
								</div>
								<div class="col-4">
									<vue-select 
										v-model="formThongTinThueBao.TSL.loaiCapCuoiId" 
										:labelWidth="'65'" 
										:options="formThongTinKhachHang.loaiCapOptions"
										valueField="LOAICAP_ID" labelField="LOAI_CAP"  label="Loại cáp cuối"
										ref="">
									</vue-select>
								</div>
								<div class="col-4">
									
									<vue-select 
										v-model="formThongTinThueBao.TSL.loaiThietBiCuoiId" 
										:labelWidth="'65'" 
										:options="formThongTinKhachHang.loaiThietBiOptions"
										valueField="LOAITBI_ID" labelField="LOAI_TBI"  label="Loại TB cuối"
										ref="">
									</vue-select>
								</div>
							</div>
							<div class="row">
								<div class="col-4">
									<div class="info-row">
										<div class="key w65">Số ảo đầu</div>
										<div class="value">
											<div class="input-more-button">
												<button class="btn btn-sm" @click="btnSoAoTSL_Dau_Click" :disabled="statusButtonSinhSoAo">
													<span class="-ap icon-more_horiz"></span>
												</button>
												<input type="text" v-model="formThongTinThueBao.TSL.soAoDau"
													class="form-control form-control-sm fw6" :disabled="statusButtonSinhSoAo" />
											</div>
										</div>
									</div>
								</div>
								<div class="col-4">
									<div class="info-row">
										<div class="key w65">Số ảo cuối</div>
										<div class="value">
											<div class="input-more-button">
												<button class="btn btn-sm" @click="btnSoAoTSL_Cuoi_Click" :disabled="statusButtonSinhSoAo">
													<span class="-ap icon-more_horiz"></span>
												</button>
												<input type="text" v-model="formThongTinThueBao.TSL.SoAoCuoi"
													class="form-control form-control-sm fw6" :disabled="statusButtonSinhSoAo" />
											</div>
										</div>
									</div>
								</div>
								<div class="col-4">
									<div class="info-row">
										<div class="key w65">Trang bị</div>
										<div class="value">
											<ejs-dropdownlist :allowFiltering="true" v-model="formThongTinThueBao.TSL.loaiThietBiDauId"
												:fields="{ value: 'TRANGBI_ID', text: 'TENTRANGBI' }"
												:dataSource="formThongTinKhachHang.trangBiOptions" />
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12 col-12">
									<div class="info-row">
										<div class="key w65">Địa chỉ đầu</div>
										<div class="value">
											<div class="input-more-button">
												<button class="btn btn-sm" @click="btnChonDCDau_Click" >
													<span class="-ap icon-more_horiz"></span>
												</button>
												<ModalChonDiaChi modalId="popupDiaChiLD_TSL_Dau" ref="popupDiaChiLD_TSL_Dau" :data="formThongTinThueBao.diaChiLD_TSL_Dau"
													@xacnhan="get_formThongTinThueBao_diaChi_LD_TSL_Dau"></ModalChonDiaChi>
												<input type="text" v-model="
													formThongTinThueBao.TSL.diaChiLD_TSL_Dau.DIACHI
												" class="form-control form-control-sm fw6" />
											</div>
										</div>
									</div>
								</div>
								<div class="col-sm-12 col-12">
									<div class="info-row">
										<div class="key w65">Địa chỉ cuối</div>
										<div class="value">
											<div class="input-more-button">
												<button class="btn btn-sm" @click="btnChonDCCuoi_Click">
													<span class="-ap icon-more_horiz"></span>
												</button>
												<ModalChonDiaChi modalId="popupDiaChiLD_TSL_Cuoi" ref="popupDiaChiLD_TSL_Cuoi" :data="formThongTinThueBao.diaChiLD_TSL_Cuoi"
													@xacnhan="get_formThongTinThueBao_diaChi_LD_TSL_Cuoi"></ModalChonDiaChi>
												<input type="text" v-model="
													formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.DIACHI
												" class="form-control form-control-sm fw6" />
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
		<div class="box-form">
			<div class="legend-title">Thông tin thuê bao</div>
			<div class="row" style="font-size: 0.875rem">
				<div class="col-sm-6 col-12">
					<div class="row">
						<div class="col-sm-6 col-12">
							<div class="info-row">
								<vue-select 
									v-model="formThongTinThueBao.dichVuId" :labelWidth="'90'" :options="formThongTinThueBao.dichVuOptions"
									labelField="TEN_DVVT" valueField="DICHVUVT_ID" label="Dịch vụ"
									ref=""
									@input="dichVuThueBaoChange" >
								</vue-select>
							</div>
						</div>
						<div class="col-sm-6 col-12">
							<div class="info-row">
								
								<vue-select 
									v-model="formThongTinThueBao.loaiHinhId" :options="formThongTinThueBao.loaiHinhOptions"
									labelField="LOAIHINH_TB" valueField="LOAITB_ID" label="Loại hình">
								</vue-select>
								
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6 col-12">
							<div class="info-row">
								<div class="key w90">Tên TB(*)</div>
								<div class="value">
									<input type="text" v-model="formThongTinThueBao.tenThueBao"
										class="form-control form-control-sm fw6" />
								</div>
							</div>
						</div>
						<div class="col-sm-6 col-12">
							<div class="info-row">
								<div class="key w80">Ngày sinh(*)</div>
								<div class="value">
									<k-date-picker format="DD/MM/YYYY" v-model="formThongTinThueBao.ngaySinh" />
								</div>
							</div>
						</div>
					</div>
					<div class="info-row">
						<div class="key w90">Địa chỉ TB(*)</div>
						<div class="value">
							<div class="input-more-button">
								<button class="btn btn-sm" v-b-modal.popupDiaChiHDTB>
									<span class="-ap icon-more_horiz"></span>
								</button>
								<ModalChonDiaChi modalId="popupDiaChiHDTB" ref="diaChiThueBaoModal" :data="dsDiaChiHDTB[0]"
									@xacnhan="getDiaChiTB"></ModalChonDiaChi>
								<input type="text" v-model="formThongTinThueBao.diaChiThueBao"
									class="form-control form-control-sm fw6" />
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w90">Địa chỉ LĐ(*)</div>
						<div class="value">
							<div class="input-more-button">
								<button class="btn btn-sm" v-b-modal.popupDiaChiLD>
									<span class="-ap icon-more_horiz"></span>
								</button>
								<ModalChonDiaChi modalId="popupDiaChiLD" :data="dsDiaChiHDLD[0]" @xacnhan="getDiaChiLD"></ModalChonDiaChi>
								<input type="text" v-model="formThongTinThueBao.diaChiLD" class="form-control form-control-sm fw6" />
							</div>
						</div>
					</div>
					<div class="form-row">
						<div class="col-8">
							<div class="info-row">
								<div class="key w90">Khu vực(*)</div>
								<!-- btnMaNVKD_Click -->
								<div class="value">
									<div class="input-more-button">
										<button class="btn btn-sm" @click.prevent="popupChonKhuVuc">
											<span class="-ap icon-more_horiz"></span>
										</button>
										<input type="text" v-model="formThongTinThueBao.khuVuc" class="form-control form-control-sm fw6" />
									</div>
								</div>
							</div>
						</div>
						<div class="col-4">
							<div class="info-row">
								<vue-select 
									v-model="formThongTinThueBao.doiTuongId" :labelWidth="'80'" :options="formThongTinThueBao.doiTuongOptions"
									labelField="TEN_DT" valueField="DOITUONG_ID" label="Đối tượng"
								></vue-select>	
							</div>
						</div>
					</div>
					<div class="info-row">
						<div class="key w90">Ghi chú</div>
						<div class="value">
							<input type="text" v-model="formThongTinThueBao.ghiChu" class="form-control form-control-sm fw6" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="box-form">
			<div class="legend-title">Danh sách thuê bao</div>
			<div class="table-content">
				<DataGrid :columns="dSHopDongHeader" ref="DSHopDong" :dataSource="dsDanhBaThueBaoDataSource"
					:enable-paging-server="false" :allowPaging="true" :showFilter="true" 
					@selectedRowChanged="gridviewThueBao_FocusedRowChanged"
					@rowSelected="gridviewThueBao_rowSelected"
					:totalRecords="danhSachHDLength">
				</DataGrid>
			</div>
		</div>
		<b-modal id="gankhuvuc" modal-class="" header-class="popup-header" body-class="none-padding" class="modal-dialog"
			title="Gán khu vực quản lý thuê bao" size="xl">
			<Gankhuvuc ref="ganKhuVucModal" dulieu="duLieuGanKhuVuc"></Gankhuvuc>
		</b-modal>
		<b-modal id="popupComponents" size="xl" hide-footer hide-header hide-header-close body-class="modal-body p-0">
			<component :is="popupComponent" ref="popupComponent" :dulieu="popupComponentData" isPopup="true"
				@form-close="popupClosed" @acceptChangeCTV="popupClosed" @acceptChangeNGT="popupClosed"></component>
		</b-modal>
		<ChonDonVi ref="popupChonDonViQuanLyTSL" id="popupChonDonViQuanLyTSL" isPopup="true"></ChonDonVi>
	</div>
</template>
<style>
.value:has(.select-custom) {
	padding-left: 0px;
}
.grid-ext {
    min-width: fit-content;
	max-width: 100% !important;
}
#popup-ThongTinKenhTrang .modal-dialog {
	max-width: 100%;
}
.combobox-grid .grid-panel {
	width: auto;
}
.modal-body {
	padding: none !important;
}
.disabled {
	pointer-events: none;
	opacity: 0.6;
}
.e-dropdownbase .e-list-item {
		text-indent: -1px;
    padding-left: 12px;
}
.e-dropdownbase .e-list-item {
	    white-space: normal;
}
.e-dropdownbase .e-list-item:hover {
    background-color: #5897fb;
		color:#fff;
}
</style>
<script>
import KDatePicker from "@/components/kylq_components/KDatePicker.vue";
import SearchAccountModal from "./SearchAccountModal";
import popupDSTBCungDoiCap from '../../InstallNewSubs/Popups/popupDSTBCungDoiCap';
import ModalChonDiaChi from "../../InstallNewSubs/Popups/popupChonDiaChi/ModalChonDiaChi";
import PopupDSHuongKNV2 from "./popupDSHuongKNV2_2.vue";
import Gankhuvuc from "@/modules/contract/setup/InstallNewSubs/Popups/popupGanKhuVucQuanLyThueBao";
import ThongTinKenhTrangEnums from "../ThongTinKenhTrangEnums.js";
import ChonDonVi from "./ChonDonVi/ChonDonViModal.vue";
import api from "../api_popups";
import moment from "moment";
import axios from "axios";
import cmUlt from "@/utils/commonFunction"
export default {
	name: "ThongTinKenhTrangPopup",
	components: {
		KDatePicker,
		SearchAccountModal,
		ModalChonDiaChi,
		PopupDSHuongKNV2,
		Gankhuvuc,
		ThongTinKenhTrangEnums,
		ChonDonVi,
		popupDSTBCungDoiCap
	},
	props: {
		dichvuvt_id: Number,
		ma_vnpt: String,
		ten_tb: String,
		hdtb_id_hni_prop: Number,
		diachi_ld: String,
		diachi_tb: String,
		sodt_lh: String,
		loaitb_id: Number // loai hinh tb
	},
	created() {
		// EventBus.$on('getTTTB', this.getTTTBHandler);
		// this.$root.$on('bv::modal::shown', (bvEvent, modalId) => {
		//     document.getElementsByClassName('modal-content').forEach(function(v){ v.removeAttribute('tabindex'); });
		// });
		// this.$root.$on('bv::modal::hidden', (bvEvent, modalId) => {
		//     this.ValidateModal(bvEvent,modalId);
		// });
		this.$root.$on('bv::modal::shown', (bvEvent, modalId) => {
      		document.getElementsByClassName('modal-content').forEach(function(v){ v.removeAttribute('tabindex'); });
		});
		this.$root.$on('bv::modal::hidden', (bvEvent, modalId) => {
			//this.ValidateModal(bvEvent,modalId);
		});
	},
	async mounted() {
		// InitializeComponent
		// frmTTKenhTrang_Load
		// TODO: hardcode data
		
			
		await this.loadDanhMuc();
		this.$bvModal.hide("popupComponents");
		const itemInfoStr = localStorage.getItem("OneBSS-Client");
		// this.formThongTinKhachHang.tenKhachHang = "abc";
		// this.formThongTinKhachHang.dienThoai = "0512111222"
		
		if (itemInfoStr) {
			const infoObj = JSON.parse(itemInfoStr);
			this.canBoThaoTacTinhId = infoObj.id_tinh;
			this.maCCBS = infoObj.tentat_ccbs;
		}
		
	},
	watch: {},
	computed: {
		
		danhSachHDLength: function () {
			
			(this.DSHopDong !== null && this.DSHopDong !== undefined) ? this.DSHopDong.length : 0
		},
		statusButtonSinhSoAo: function () {
			let vReturn = false;
			if (this.TUDONG_SINH_SOAO == true) {
				vReturn = true;
			} else {
				vReturn = false;
			}
			return vReturn;
		},
	},
	data() {
		return {
			trangthaitb_id : 0,
			khachhang_id : 0,
			thanhtoan_id : 0,
			thuebao_id : 0,
			ma_tb : "",
			_maLT : "",
			kieugoi_id : 0,
			tuyenthu_id : 0,
			donvi_id : 0,
			port_id : 0,
			vci_vpi_id : 0,
			system : "",
			ma_kh : "",
			ma_tt : "", 
			ma_hd : "",
			themdc : false,
			diachikh_id : 0,
			dacdiemkh_id : 0,
			khukh_id : 0,
			apkh_id : 0,
			phokh_id : 0,
			phuongkh_id : 0,
			quankh_id : 0,
			tinhkh_id  : 0,
			sonhakh : "",
			diachitt_id : 0,
			dacdiemtt_id : 0,
			khutt_id : 0,
			aptt_id : 0,
			phott_id : 0,
			phuongtt_id : 0,
			quantt_id : 0,
			tinhtt_id : 0,
			sonhatt : "",
			diachitb_id : 0,
			dacdiemtb_id : 0,
			khutb_id : 0,
			aptb_id : 0,
			photb_id : 0,
			phuongtb_id : 0,
			quantb_id : 0,
			tinhtb_id : 0,
			sonhatb : "",
			diachidau_id : 0,
			dacdiemdau_id : 0,
			khudau_id : 0,
			phodau_id : 0,
			apdau_id : 0,
			phuongdau_id : 0,
			quandau_id : 0,
			tinhdau_id : 0,
			sonhadau : "",
			diachicuoi_id : 0,
			dacdiemcuoi_id : 0,
			khucuoi_id : 0,
			phocuoi_id : 0,
			apcuoi_id : 0,
			phuongcuoi_id : 0,
			quancuoi_id : 0,
			tinhcuoi_id : 0,
			sonhacuoi : "",
			dacdiemndd_id : 0,
			khundd_id : 0,
			apndd_id : 0,
			phondd_id : 0,
			phuongndd_id : 0,
			quanndd_id : 0,
			tinhndd_id : 0,
			sonhandd : 0,
			dacdiemct_id : 0,
			khuct_id : 0,
			apct_id : 0,
			phoct_id : 0,
			phuongct_id : 0,
			quanct_id : 0,
			tinhct_id : 0,
			sonhact : "",
			donvi_dau_id : 0,
			donvi_cuoi_id : 0,
			dacdiemld_id : 0,
			diachild_id : 0,
			khuld_id : 0,
			phold_id : 0,
			apld_id : 0,
			phuongld_id : 0,
			quanld_id : 0,
			tinhld_id : 0,
			sonhald : "",
			TNC1_ID : 9991,
			TNC2_ID : 99911,
			TNC3_ID : 999111,
			TNC4_ID : 0,
			nganhnghe_id : 0,
			KetCuoi_ID : -1,
			mucuoctb_id : 0,
			kt_load : true,
			//ten_tb : "",
			//diachi_tb : "",
			//diachi_ld : "",
			doituong_id : 0,
			Lat_TB : 0,
			Lng_TB : 0,
			Lat_LD : 0,
			Lng_LD : 0,
			Lat_TT : 0,
			Lng_TT : 0,
			Lat_BC : 0, 
			Lng_BC : 0,
			Lat_KH : 0,
			Lng_KH : 0,
			Lat_DD : 0,
			Lng_DD : 0,
			Lat_DC : 0,
			Lng_DC : 0,
			vhuongkn : "",
			hdtb_id_cha : 0,
			khuvuc_id : 0,
			nhanvientc_id : 0,
			_donviql_id : 0,
			_trangthaitb_id : 0,
			nhanvien_ql_am_id : 0,
			chapnhan : false,
			loaisochinh : 0,
			thuebao_id_cha : 0,
			//ma_vnpt : "",
			ma_tb_ao_ht : "",
			ma_tb_ao_ht_cuoi : "",
			ma_tb_ao_cuoi : "",
			ma_tb_ht : "",
			baohong_id_hni : 0,
			hdtb_id_hni : 0,
			ma_bh : "",
			// bo sung cac bien vds
			tslMaTBWanIsDisabled: false,
			tslMaTBTslIsDisabled: false,
			ma_bh: "",
			canBoThaoTacTinhId: 0,
			popupComponent: null,
			popupComponentData: null,
			popupComponentName: "",
			popupComponentEvents: {
				"form-close": "popupClosed",
			},
			themdc: false,
			maCCBS: "",
			hdtb_id_hni: 0,
			cboDichVuId_Selected: 0,
			duLieuGanKhuVuc: {}, // Dung de day du lieu sang modal gan khu vuc
			thuebao_id_cha: 0,
			hdtb_id_cha: 0,
			_donviql_id: 0,
			tuyenthu_id: -1,
			donvi_dau_id: 0, // Su dung de gan don vi quan ly TSL diem dau
			donvi_cuoi_id: 0, // Su dung de gan don vi quan ly TSL diem cuoi
			disabledPIR: true,
			dataTieuNganh: {},
			// Cac bien ds dung de day vao params api
			// Ben trong chua cac bien diachi
			dsDiaChi: [],
			diachiTB: {},
			dSDBTB: [],
			dsDiaChiHDKH: [],
			dsDiaChiHDTT: [],
			dsDiaChiHDTB: [],
			dsDiaChiHDLD: [],
			diachikh_id : 0,
			// Cac bien lay gia tri tu cac model
			// Duoc dua vao cac bien ds truoc khi dua len api
			diachiKN: {
				DIACHI_ID: 0,
				TINH_ID: 0,
				QUAN_ID: 0,
				PHUONG_ID: 0,
				PHO_ID: 0,
				AP_ID: 0,
				KHU_ID: 0,
				DACDIEM_ID: 0,
				SONHA: "",
				DIACHI: "",
			},
			diachiLD: {
				DIACHI_ID: 0,
				TINH_ID: 0,
				QUAN_ID: 0,
				PHUONG_ID: 0,
				PHO_ID: 0,
				AP_ID: 0,
				KHU_ID: 0,
				DACDIEM_ID: 0,
				SONHA: "",
				DIACHI: "",
			},
			diachiTT: {
				DIACHI_ID: 0,
				TINH_ID: 0,
				QUAN_ID: 0,
				PHUONG_ID: 0,
				PHO_ID: 0,
				AP_ID: 0,
				KHU_ID: 0,
				DACDIEM_ID: 0,
				SONHA: "",
				DIACHI: "",
			},
			diachiTB: {
				DIACHI_ID: 0,
				TINH_ID: 0,
				QUAN_ID: 0,
				PHUONG_ID: 0,
				PHO_ID: 0,
				AP_ID: 0,
				KHU_ID: 0,
				DACDIEM_ID: 0,
				SONHA: "",
				DIACHI: "",
			},
			diachiKH: {
				DIACHI_ID: 0,
				TINH_ID: 0,
				QUAN_ID: 0,
				PHUONG_ID: 0,
				PHO_ID: 0,
				AP_ID: 0,
				KHU_ID: 0,
				DACDIEM_ID: 0,
				SONHA: "",
				DIACHI: "",
			},
			dsDBKH: [],
			dsDBTT: [],
			dsDBTB: [],
			dsDB_MGWAN: [],
			dsDB_TSL: [],
			dSHopDongHeader: [
				{ fieldName: "ma_TT", headerText: "Mã TT", allowFiltering: true },
				{
					fieldName: "ma_TT_NEO",
					headerText: "Mã TT NEO",
					allowFiltering: true,
				},
				{
					fieldName: "ma_TB",
					headerText: "Số máy/Acc",
					allowFiltering: true,
					allowHtml: true,
				},
				{
					fieldName: "sochinh",
					headerText: "Số chính",
					allowFiltering: true,
					allowHtml: true,
				},
				{
					fieldName: "ma_LT",
					headerText: "Số ảo",
					allowFiltering: true,
					allowHtml: true,
				},
				{
					fieldName: "tocdo",
					headerText: "Tốc độ",
					allowFiltering: true,
					allowHtml: true,
				},
				{
					fieldName: "trangthai_TB",
					headerText: "Trạng thái",
					allowFiltering: true,
					allowHtml: true,
				},
				{
					fieldName: "ngay_SD",
					headerText: "Ngày SD",
					allowFiltering: true,
					allowHtml: true,
				},
				{
					fieldName: "ngay_CAT",
					headerText: "Ngày cắt",
					allowFiltering: true,
					allowHtml: true,
				},
				{
					fieldName: "ngay_TD",
					headerText: "Ngày TD",
					allowFiltering: true,
					allowHtml: true,
				},
				{
					fieldName: "ngay_KP",
					headerText: "Ngày KP",
					allowFiltering: true,
					allowHtml: true,
				},
				{
					fieldName: "ma_TB",
					headerText: "Mã VNPT",
					allowFiltering: true,
					allowHtml: true,
				},
				
			],
			thongTinThanhToan: {
				thanhtoan_id: 0,
				khachhang_id: 0,
				ma_tt: "",
				ten_tt: "",
				diachi_tt: "",
				diachi_ct: "",
				diachi_bc: "",
				matb_dd: "",
				tuyenthu_id: "",
				dotgiao_id: 1,
				mst: "",
				httt_id: 1,
				ghiChu: "",
				stk: "",
				chu_tk: "",
				donviql_id: "",
			},
			hdtb_id: -1,
			watcherActive: false,
			model: {
				hdtb_id: -1,
			},
			TUDONG_SINH_SOAO: false,
			popupComponentName: "",
			popupComponentAttr: {},
			popupComponentData: {},
			popupComponentEvts: {
				"form-close": this.popupClosed,
			},
			kh_loaiGiayToId: null,
			formThongTinKhachHang: {
				khachhang_id: 0,
				maKH: "",
				dienThoai: "",
				tenKhachHang: "",
				soGiayTo: "",
				diaChiKH: "",
				ngaySinh: "",
				diachikh_id: 0,
				tinhkh_id: 0,
				quankh_id: 0,
				phuongkh_id: 0,
				phokh_id: 0,
				apkh_id: 0,
				khukh_id: 0,
				dacdiemkh_id: 0,
				sonhakh: 0,
				loaiGiayToOptions: [],
				loaiGiayToId: null,
				quocTichOptions: [],
				quocTichId: 1,
				ngayCap: "",
				danTocOptions: [],
				danTocId: null,
				ngayHetHan: "",
				gioiTinhOptions: [
					{
						text: "Không",
						value: 0,
					},
					{
						text: "Nam",
						value: 1,
					},
					{
						text: "Nữ",
						value: 2,
					},
				],
				gioiTinh: 1,
				nganhNgheOptions: [],
				nganhNgheId: null,
				loaiKHId: null,
				accountId: "",
				soAo: "",
				maTN: "",
				loaiKenhOptions: [],
				loaiKenhId: null,
				tocDoCirOptions: [],
				tocDoCirId: null,
				tocDoPirCheck: false,
				tocDoPirOptions: [],
				tocDoPirId: null,
				loaiNodeOptions: [
					{
						value: "1",
						text: "Node chính",
					}, //VnID_15504: Node chính
					{
						value: "2",
						text: "Node phụ",
					}, //VnID_26672: Node phụ
					{
						value: "3",
						text: "Node dự phòng cổng + tốc độ",
					}, //VnID_26673: Node dự phòng cổng + tốc độ
					{
						value: "4",
						text: "Node dự phòng tốc độ",
					}, //VnID_26674: Node dự phòng tốc độ
				],
				loaiNodeId: null,
				chuQuanOptions: [],
				chuQuanId: null,
				modemOptions: [],
				modemId: null,
				huongKN: "",
				loaiThietBiOptions: [],
				loaiThietBiId: null,
				diaChiKN: "",
				trangBiOptions: [],
				trangBiId: null,
				loaiCapOptions: [],
				loaiCapId: null,
				thoiHanOptions: [],
				thoiHanId: null,
				loaiKhachHangOptions: [],
				// Bo sung 16/04/2022
				donViQuanLyId: 0,
				tuyenThuId: 0,
				isDoneMounted: false
			},
			listbox: {
				nganhnghe: {
					list: [],
					value: 0,
				},
				nganhngheloc: {
					list: [],
					value: 0,
				},
			},
			tieunganh: {
				dsTNC1: [],
				dsTNC2: [],
				dsTNC3: [],
				dsTNC4: [],
				TNC1_ID: 0,
				TNC2_ID: 0,
				TNC3_ID: 0,
				TNC4_ID: 0,
				TNCMOTA: "",
			},
			formThongTinThueBao: {
				TSL: {
					maTSL: "",
					accountId: "",
					loaiKenhOptions: [],
					loaiKenhId: 0,
					chuQuanOptions: [],
					chuQuanId: 0,
					tocDoKenhOptions: [],
					tocDoKenhId: 0,
					loaiCapDauOptions: [],
					loaiCapDauId: 0,
					loaiCapCuoiOptions: [],
					loaiCapCuoiId: 0,
					loaiThietBiDauOptions: [],
					loaiThietBiDauId: 0,
					loaiThietBiCuoiOptions: [],
					loaiThietBiCuoiId: 0,
					soAoDau: "",
					SoAoCuoi: "",
					diaChiLD_TSL_Dau: {
						DIACHI_ID: 0,
						TINH_ID: 0,
						QUAN_ID: 0,
						PHUONG_ID: 0,
						PHO_ID: 0,
						AP_ID: 0,
						KHU_ID: 0,
						DACDIEM_ID: 0,
						SONHA: "",
						DIACHI: "",
					},
					diaChiLD_TSL_Cuoi: {
						DIACHI_ID: 0,
						TINH_ID: 0,
						QUAN_ID: 0,
						PHUONG_ID: 0,
						PHO_ID: 0,
						AP_ID: 0,
						KHU_ID: 0,
						DACDIEM_ID: 0,
						SONHA: "",
						DIACHI: "",
					},
				},
				maThueBao: "",
				thueBaoId: 0,
				dichVuOptions: [],
				dichVuId: "",
				loaiHinhOptions: [],
				loaiHinhId: 0,
				tenThueBao: "",
				ngaySinh: "",
				diaChiThueBao: "",
				diaChiLD: "",
				khuVuc: "",
				doiTuongOptions: [],
				doiTuongId: 0,
				ghiChu: "",
				maTBWan: "",
				maTSL: "",
				loaiKenhId: -1,
			},
			duLieuPopupHuongKetNoi: {
				// kieu: vkieu,
				// dichvuvt_id: this.dichvuvt_id,
				// tinhkn_id: "tinhkh_id",
				// db_hd: 1,
				// vid: this.khachhang_id
				// kieu: 0,
				// dichvuvt_id: this.dichvuvt_id,
				// tinhkn_id:
				// 	this.donViQuanLyId != null && this.donViQuanLyId != undefined
				// 		? this.formThongTinKhachHang.donViQuanLyId
				// 		: this.canBoThaoTacTinhId,
				// db_hd: 1,
				// vid: 0,
			},
			_maLT: 0,
			dsDanhBaThueBaoDataSource: [],
			danhBaThueBaoSelectedRow: null,
			tsbtnNhapMoi: {
				Enabled: true,
			},
			tsbtnCapNhan: {
				Enabled: true,
			},
			tsbtnCapNhatDoThu: {
				Enabled: true,
			},
			tsbtnHuyBo: {
				Enabled: true,
			},
			tsbtnXoaPhieu: {
				Enabled: true,
			},
		};
	},
	methods: {
		async Get_donviql_id() { },
		async Get_tuyenthu_id() { },
		async SinhMaDVVT() {
			let sinhMaDvvtParams = {
				vphanvung_id: this.$root.token.getPhanVungID(),
				vquan_id: "0",
				vdichvuvt_id: this.formThongTinThueBao.dichVuId,
				vloaitb_id: this.formThongTinThueBao.loaiHinhId,
				vtentat: this.maCCBS,
				tinhthicong_id: 100
			};
			let strMatb = "";
			await api
				.sinh_matb_svvt(this.axios, JSON.stringify(sinhMaDvvtParams))
				.then((response) => {
					strMatb = response.data.data;
					if (strMatb !== "-1") {
						if (
							this.formThongTinThueBao.dichVuId ===
							ThongTinKenhTrangEnums.DichVuVienThong.MEGAWAN ||
							this.formThongTinThueBao.dichVuId ===
							ThongTinKenhTrangEnums.DichVuVienThong.METRONET
						) {
							this.formThongTinKhachHang.accountId = strMatb;
							this.tslMaTBWanIsDisabled = true;
							this.$toast.success("Sinh mã dịch vụ viễn thông thành công");
						} else if (
							this.formThongTinThueBao.dichVuId ==
							ThongTinKenhTrangEnums.DichVuVienThong.TSL
						) {
							this.formThongTinThueBao.TSL.maTSL = strMatb;
							this.formThongTinThueBao.TSL.accountId = strMatb;
							this.tslMaTBTslIsDisabled = true;
							this.$toast.success("Sinh mã dịch vụ viễn thông TSL thành công");
						}
					} else {
						this.tslMaTBWanIsDisabled = false;
						this.tslMaTBTslIsDisabled = false;
					}
				});
		},
		async KIEMTRA_MAKH(maKH, khachhang_id) {
			let objParams = {
				vphanvung_id: this.$root.token.getPhanVungID(),
				vma_kh: maKH,
				vkhachhang_id: khachhang_id,
			};
			await api
				.kiemtra_thangthai_makh(this.axios, JSON.stringify(objParams))
				.then((response) => {
					if (
						(response.data.error_code)
						 &&
						(response.data.error_code === "BSS-00000000")
					) {
						console.log("get api kiemtra_thangthai_makh \n" + response.data.data.toString());
						if (response.data.data.toString() == "1") {
							return true;
						} else {
							return false;
						}
					} else {
						this.$toast.error("Lỗi api kiểm tra mã khách hàng !");
						return false;
					}
				});
			return false;
		},
		async KIEMTRA_DIEUKIEN_KHAC (
			maTB,
			thueBaoId,
			loaiHinhId,
			loaihd_id,
			themmoi
		) {
			// TODO: bo di hard code de test;
			// return "1";
			let objParams = {
				vphanvung_id: this.$root.token.getPhanVungID().toString(),
				vma: maTB.toString(),
				vthuebao_id: thueBaoId.toString(),
				vloaitb_id: loaiHinhId.toString(),
				vloaihd_id: loaihd_id.toString(),
				vthemmoi: themmoi.toString(),
			};
			let result = false;
			await api
				.kiemtra_dieukien_khac(this.axios, JSON.stringify(objParams))
				.then((response) => {
					
					if (
						(response.data.error_code)
						 &&
						(response.data.error_code === "BSS-00000000")
					) {
						
						if (response.data.data.toString() === "1") {
							result = true;
						}
					} else {
						this.$toast.error("Lỗi api kiểm tra điều kiện khác !");
						result = false
					}
				});
			return result;
		},
		KIEMTRA_MATB_KHOSO_AO(soAo) {
			return new Promise((resovle, reject)=>{
				let objParams = {
					vma_tb: soAo
				};
				api.kiemtra_matb_khoso_ao(this.axios, JSON.stringify(objParams))
				.then((response) => {
					if (
						(response.data.error_code)
						 &&
						(response.data.error_code === "BSS-00000000")
					) {
						if (response.data.data.toString() == "1") {
							resovle(true)
						} else {
							resovle(false)
						}
					} else {
						this.$toast.error("Lỗi api kiểm tra số ảo !");
						resovle(false)
					}
					
				}).catch(e=>{
					this.$toast.error("Lỗi api kiểm tra số ảo !");
					resovle(false)
				});
			})
			
		},
		async KiemTraDL_KhachHang() {
			// TODO: hardcode de test
			// return true;
			if (this.formThongTinKhachHang.tenKhachHang.toString() == "") {
				this.$toast.error("Hãy nhập tên khách hàng !");
				this.SetButton(-1);
				return false;
			}
			let kiemTraMaKH = false; 
			await this.KIEMTRA_MAKH(
				this.formThongTinKhachHang.maKH,
				this.formThongTinKhachHang.khachhang_id
			).then(result => {
				console.log("KiemTraDL_KhachHang: " + result);
				kiemTraMaKH = result;
			});
			console.log("********************" + kiemTraMaKH);
			if (kiemTraMaKH) {
				this.$toast.error("Kiểm tra lại mã khách hàng !");
				this.SetButton(-1);
				//return false; // TODO: hardcode de test
			}
			if (
				!this.formThongTinKhachHang.dienThoai ||
				this.formThongTinKhachHang.dienThoai.toString().length > 11
			) {
				this.$toast.error(
					"Số điện thoại liên hệ của khách hàng phải là kiểu số!"
				);
				this.SetButton(-1);
				return false;
			}
			if (
				!this.formThongTinKhachHang.diaChiKH ||
				this.formThongTinKhachHang.diaChiKH.toString() == ""
			) {
				this.$toast.error("Hãy chọn địa chỉ khách hàng!");
				this.SetButton(-1);
				return false;
			}
			return true;
		},
		async KiemTraDL_ThueBao() {
	
			// if (Number(this.formThongTinThueBao.dichVuId) === ThongTinKenhTrangEnums.DichVuVienThong.MEGAWAN) {
			// 	if (this.formThongTinThueBao.maThue.toString() === "") {
			// 		this.$toast.error("Chưa nhập mã thuê bao");
			// 		this.SetButton(-1);
			// 		return false;
			// 	}
			// }
			// if (Number(this.formThongTinThueBao.dichVuId) === ThongTinKenhTrangEnums.DichVuVienThong.TSL) {
				
				// if (this.formThongTinThueBao.TSL.maTSL.toString() === "") {
				// 	// this.$toast.error("Chưa nhập mã thuê bao");
				// 	this.SetButton(-1);
				// 	return false;
				// }
			// }
			let _kiemTraDK_Params_MaTB = (this.formThongTinThueBao.dichVuId == ThongTinKenhTrangEnums.DichVuVienThong.MEGAWAN||this.formThongTinThueBao.dichVuId == ThongTinKenhTrangEnums.DichVuVienThong.METRONET)? this.formThongTinKhachHang.accountId: this.formThongTinThueBao.TSL.maTSL;	
			console.log('_kiemTraDK_Params_MaTB', _kiemTraDK_Params_MaTB)
			if (!_kiemTraDK_Params_MaTB) {
				this.$toast.error("Mã thuê bao không phải MEGAWAN hoặc TSL");
				return false;
			}
			let _kiemTraDK_Params_NhapMoi =
				this.tsbtnNhapMoi.Enabled == false ? 1 : 2;
			// Kiem tra dieu kien khac
			let _ketqua = false;
			await this.KIEMTRA_DIEUKIEN_KHAC(
				_kiemTraDK_Params_MaTB,
				this.formThongTinThueBao.thueBaoId,
				this.formThongTinThueBao.loaiHinhId,
				0,
				_kiemTraDK_Params_NhapMoi
			).then(result => {
				_ketqua = result;
			});
			
			;
			
			if (_ketqua.toString() !== "true") {
				this.$toast.error(_ketqua);
				this.SetButton(-1);
				return false;
			}
			// Ket thuc kiem tra dieu kien khac
			if ( (Number(this.diachiLD.PHUONG_ID) == 0) ) {
				this.$toast.error("Hãy chọn lại địa chỉ lắp đặt chi tiết hơn!");
				return false;
			}
			if ( (Number(this.diachiTB.PHUONG_ID) == 0) ){
				this.$toast.error("Hãy chọn lại địa chỉ thuê bao chi tiết hơn!");
				return false;
			}
			if ( (
				Number(this.formThongTinThueBao.dichVuId) ==
				Number(ThongTinKenhTrangEnums.DichVuVienThong.TSL)
			) ) {
				if (!this.TUDONG_SINH_SOAO) {
					let kiemTraSoAo_Dau = false;
					let kiemTraSoAo_Cuoi = false;	
					await this.KIEMTRA_MATB_KHOSO_AO(this.formThongTinThueBao.TSL.soAoDau).then(result => {
						kiemTraSoAo_Dau = result;
					});
					if (
						kiemTraSoAo_Dau === false
					) {
						//if (tabctlThongTin.SelectedIndex == 0) tabctlThongTin.SelectedIndex = 1;
						this.$toast.error(
							"Số ảo chưa có trong kho số hoặc chưa chọn số ảo cho điểm đầu !"
						);
						//txtSoAoTSL_Dau.Focus();
						this.SetButton(-1);
						return false;
					}
					await this.KIEMTRA_MATB_KHOSO_AO(this.formThongTinThueBao.TSL.soAoCuoi).then(result => {
						kiemTraSoAo_Cuoi = result;
					});
					if (
						kiemTraSoAo_Cuoi === false
					) {
						//if (tabctlThongTin.SelectedIndex == 0) tabctlThongTin.SelectedIndex = 1;
						this.$toast.error(
							"Số ảo chưa có trong kho số hoặc chưa chọn số ảo cho điểm cuối !"
						);
						//txtSoAoTSL_Cuoi.Focus();
						this.SetButton(-1);
						return false;
					}
				}
			}
			if ( (
				Number(this.formThongTinThueBao.dichVuId) ==
				ThongTinKenhTrangEnums.DichVuVienThong.MEGAWAN
			) ) {
				if (!this.TUDONG_SINH_SOAO) {
					//formThongTinKhachHang.soAo
					if(!this.formThongTinKhachHang.soAo||this.formThongTinKhachHang.soAo.trim()!=''){
						this.$toast.error('Số ảo chưa có trong kho số hoặc chưa chọn số ảo !')
						//this.SetButton(-1);
						this.$refs.inputSoAo.focus()
						return false
					}
					// let kiemTra_SoAoWan = false;
					
					// await this.KIEMTRA_MATB_KHOSO_AO(this.formThongTinThueBao.soAo).then(result => {
					// 	kiemTra_SoAoWan = result;
					// });
					// if (!kiemTra_SoAoWan) {
					// 	//if (tabctlThongTin.SelectedIndex == 0) tabctlThongTin.SelectedIndex = 1;
					// 	this.$toast.error(
					// 		"Số ảo chưa có trong kho số hoặc chưa chọn số ảo !"
					// 	);
					// 	this.SetButton(-1);
					// 	//txtSoAoWan.Focus();
					// 	return false;
					// }
				}
			}
		},
		SetButton(kieu) {
			kieu = Number(kieu);
			if (kieu == -1) {
				//Bat dau
				//txtMaKH.Focus();
				this.tsbtnNhapMoi.Enabled = false;
				this.tsbtnCapNhan.Enabled = true;
				this.tsbtnCapNhatDoThu.Enabled = true;
				this.tsbtnHuyBo.Enabled = true;
				//this.Clear()
			}
			if (kieu == 0) {
				//Bat dau
				//txtMaKH.Focus(); chua convert
				this.tsbtnNhapMoi.Enabled = true;
				this.tsbtnCapNhatDoThu.Enabled = false;
				this.tsbtnXoaPhieu.Enabled = true;
				this.Clear();
			}
			if (kieu == 1) {
				//Them moi
				//txtMaKH.Focus(); chua convert
				this.tsbtnHuyBo.Enabled = true;
				this.tsbtnNhapMoi.Enabled = false;
				this.tsbtnCapNhan.Enabled = true;
				this.tsbtnCapNhatDoThu.Enabled = true;
				this.Clear();
			}
			if (kieu == 2) {
				//Huy
				//txtMaKH.Focus();
				this.tsbtnNhapMoi.Enabled = false;
				this.Clear();
			}
			if (kieu == 3) {
				//Edit
				//txtMaKH.Focus();
				this.tsbtnNhapMoi.Enabled = true;
				//tsbtnCapNhan.Enabled = false;
				this.tsbtnCapNhatDoThu.Enabled = true;
				this.tsbtnHuyBo.Enabled = true;
			}
			if (kieu == 4) {
				//chấp nhận
				//gridThueBao.Focus();
				this.tsbtnCapNhatDoThu.Enabled = false;
				this.tsbtnNhapMoi.Enabled = false;
				this.tsbtnHuyBo.Enabled = true;
			}
			if (kieu == 5) {
				//chấp nhận
				//hiện hết lên
				this.tsbtnNhapMoi.Enabled = true;
				this.tsbtnCapNhatDoThu.Enabled = true;
				this.tsbtnHuyBo.Enabled = true;
				this.tsbtnXoaPhieu.Enabled = true;
				this.tsbtnCapNhan.Enabled = true;
			}
			if (kieu == 6) {
				//sau cập nhật
				this.tsbtnNhapMoi.Enabled = false;
				this.tsbtnCapNhatDoThu.Enabled = false;
				this.tsbtnHuyBo.Enabled = false;
				this.tsbtnXoaPhieu.Enabled = false;
				this.tsbtnCapNhan.Enabled = true;
			}
		},
		async TaoDuLieu(themmoi) {
			await this.TaoDuLieu_DBKH(themmoi);
			await this.TaoDuLieu_DBTT(themmoi);
			await this.TaoDuLieu_DBTB(themmoi);
			if (this.themdc) {
				await this.TaoDuLieu_DiaChi(true, 4);
			} else {
				await this.TaoDuLieu_DiaChi(themmoi, 4);
			}
			await this.CAPNHAT_THUEBAO_ID_DBTB_KV();
			switch (this.dichvuvt_id) {
				case ThongTinKenhTrangEnums.DichVuVienThong.TSL:
					await this.TaoDuLieu_DB_TSL(themmoi);
					
					break;
				case ThongTinKenhTrangEnums.DichVuVienThong.MEGAWAN:
					await this.TaoDuLieu_DBTB_MGWAN(themmoi);
					
					break;
				default:
					//TaoDuLieu_DB_CD(themmoi);
					break;
			}
		},
		handleShowModal() { },
		
		popupDSHuongKNV2Close(data) {
			// btnHuongKNWan_Click
			// Ham nay trong code C# ko su dung de gan gia tri vao bien
			
			let shuongkn_Text = data.shuongkn;
			this.vhuongkn = data.vma_tb;
			let loaisochinh = data.iloai;
			if (loaisochinh.toString() === "1") {
				this.thuebao_id_cha = data.iid_sochinh;
			} else {
				this.hdtb_id_cha = data.iid_sochinh;
			}
			this.formThongTinKhachHang.huongKN = shuongkn_Text;
		},
		getDiaChi: function (data, datatype) {
			this[datatype].DIACHI = data.diachimoi;
			this[datatype].TINH_ID = data.tinh_id;
			this[datatype].QUAN_ID = data.quan_id;
			this[datatype].PHUONG_ID = data.phuong_id;
			this[datatype].PHO_ID = data.pho_id !== null ? data.pho_id : 0;
			this[datatype].AP_ID = data.ap_id !== null ? data.ap_id : 0;
			this[datatype].KHU_ID = data.khu_id;
			this[datatype].DACDIEM_ID = data.dacdiem_id;
			this[datatype].SO_NHA = data.so_nha;
			this[datatype].Lat = data.lat;
			this[datatype].Lng = data.lng;
			if (datatype.toString() == "diachiKH") {
				this.tinhkh_id = data.tinh_id;
				this.quankh_id = data.quan_id;
				this.phuongkh_id = data.phuong_id;
				this.phokh_id = data.pho_id;
				this.apkh_id = data.ap_id;
				this.khukh_id = data.khu_id;
				this.dacdiemkh_id = data.dacdiem_id;
				this.sonhakh = data.so_nha;
				this.formThongTinKhachHang.tinhkh_id = data.tinh_id;
				this.formThongTinKhachHang.quankh_id = data.quan_id;
				this.formThongTinKhachHang.phuongkh_id = data.phuong_id;
				this.formThongTinKhachHang.phokh_id = data.pho_id !== null ? data.pho_id : 0;
				this.formThongTinKhachHang.apkh_id = data.ap_id !== null ? data.ap_id : 0;
				this.formThongTinKhachHang.khukh_id = data.khu_id;
				this.formThongTinKhachHang.dacdiemkh_id = data.dacdiem_id;
				this.formThongTinKhachHang.sonhakh = data.so_nha;
			}
		},
		getDiaChiTSL: function (data, datatype) {
			this.formThongTinThueBao.TSL[datatype].DIACHI = data.diachimoi;
			this.formThongTinThueBao.TSL[datatype].TINH_ID = data.tinh_id;
			this.formThongTinThueBao.TSL[datatype].QUAN_ID = data.quan_id;
			this.formThongTinThueBao.TSL[datatype].PHUONG_ID = data.phuong_id;
			this.formThongTinThueBao.TSL[datatype].PHO_ID = data.pho_id;
			this.formThongTinThueBao.TSL[datatype].AP_ID = data.ap_id;
			this.formThongTinThueBao.TSL[datatype].KHU_ID = data.khu_id;
			this.formThongTinThueBao.TSL[datatype].DACDIEM_ID = data.dacdiem_id;
			this.formThongTinThueBao.TSL[datatype].SO_NHA = data.so_nha;
			this.formThongTinThueBao.TSL[datatype].Lat = data.lat;
			this.formThongTinThueBao.TSL[datatype].Lng = data.lng;
		},
		getDiaChiKN: async function (data) {
			this.getDiaChi(data, "diachiKN");
			this.formThongTinKhachHang.diaChiKN = data.diachimoi;
		},
		getDiaChiKH: async function (data) {
			this.getDiaChi(data, "diachiKH");
			this.tinhkh_id = data.tinh_id;
			this.quankh_id = data.quan_id;
			this.phuongkh_id = data.phuong_id;
			this.phokh_id = data.pho_id;
			this.dacdiemkh_id = data.dacdiem_id;
			this.apkh_id = data.ap_id;
			this.khukh_id = data.khu_id;
			this.sonhakh = data.sonha;
			
			//địa chỉ thanh toán 
			this.tinhtt_id = this.tinhkh_id;
			this.quantt_id = this.quankh_id;
			this.phuongtt_id = this.phuongkh_id;
			this.dacdiemtt_id = this.dacdiemkh_id;
			this.phott_id = this.phokh_id;
			this.aptt_id = this.apkh_id;
			this.khutt_id = this.khukh_id;
			this.sonhatt = this.sonhakh;
			
			
			this.formThongTinKhachHang.diaChiKH = data.diachimoi;
		},
		getDiaChiTB: async function (data) {
			this.getDiaChi(data, "diachiTB");
			this.formThongTinThueBao.diaChiThueBao = data.diachimoi;
		},
		getDiaChiLD: async function (data) {
			this.getDiaChi(data, "diachiLD");
			
			this.tinhld_id = data.tinh_id;
			this.quanld_id = data.quan_id;
			this.phuongld_id = data.phuong_id;
			this.dacdiemld_id = data.dacdiem_id;
			this.phold_id = data.pho_id;
			this.apld_id = data.ap_id;
			this.khuld_id = data.khu_id;
			this.sonhald = data.sonha;
			this.Lat_LD = data.lattitude;
			this.Lng_LD = data.longtitude;
			this.formThongTinThueBao.diaChiLD = data.diachimoi;
			if (data.ketcuoi_id != -1)
			{
				this.KetCuoi_ID = data.ketcuoi_id;
				//Nhanvien_id_kc = Convert.ToInt32(objCheckData.MAP_ID("nhanvien_id", DatabaseConstants.DB5 + ".ql_ketcuoi", "Where ketcuoi_id=" + KetCuoi_ID));
			}
			
		},
		async get_formThongTinThueBao_diaChi_LD_TSL_Cuoi(data) {
			
			
			// // // console.log(JSON.stringify());
			// TODO: Message_Box.ShowWarning("Địa danh này chưa được gán đơn vị quản lý địa chỉ lắp đặt. Hãy liên hệ với Admin để xử lý!");
			// line: 1899
			let diaChiLD_TSL_Obj = this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi;
			
			this.getDiaChiTSL(data, "diaChiLD_TSL_Cuoi");
			this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.DIACHI = data.diachimoi;
			if ( ( (Number(diaChiLD_TSL_Obj.QUAN_ID) > 0) && (Number(diaChiLD_TSL_Obj.PHUONG_ID) > 0) ) ) {
				let lay_ds_donviql_Params = {
				    "vphanvung_id": this.$root.token.getPhanVungID()
				    ,"vphuong_id": diaChiLD_TSL_Obj.PHUONG_ID
				    ,"vpho_id": diaChiLD_TSL_Obj.PHO_ID
				    ,"vap_id": diaChiLD_TSL_Obj.AP_ID
				    ,"vkhu_id": diaChiLD_TSL_Obj.KHU_ID
				    ,"vkieu": "2"
				};
				let dsDonViQuanLy = [];
				await api
					.lay_ds_donviql(this.axios, JSON.stringify(lay_ds_donviql_Params))
					.then((response) => {
						if (
							response 
						) {
							
							dsDonViQuanLy = response.data.data;
							// if (dsDonViQuanLy === null || dsDonViQuanLy.length === 0) {
							// 	this.formThongTinThueBao.diaChiLD_TSL_Cuoi = {};
							// 	this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.DIACHI = "";
							// 	// Gán lại do trong code c# đã return luôn mà không gán địa chỉ
							// 	this.$root.toastError(
							// 		"Địa danh này chưa được gán đơn vị quản lý địa chỉ lắp đặt. Hãy liên hệ với Admin để xử lý!"
							// 	);
							// 	return;
							// } else if (dsDonViQuanLy.length === 1) {
							// 	this.donvi_cuoi_id = dsDonViQuanLy[0].donvi_id;
							// } else if (dsDonViQuanLy.length > 0) {
							// 	this.donvi_cuoi_id = dsDonViQuanLy[0].donviql_ID;
							// 	this.popupChonDonViQuanLyTSL(
							// 		2,
							// 		this.donvi_cuoi_id,
							// 		dsDonViQuanLy
							// 	);
							// }
						}
					});
			}
		},
		async get_formThongTinThueBao_diaChi_LD_TSL_Dau(data) {
			
			this.getDiaChiTSL(data, "diaChiLD_TSL_Dau");
			// TODO: Message_Box.ShowWarning("Địa danh này chưa được gán đơn vị quản lý địa chỉ lắp đặt. Hãy liên hệ với Admin để xử lý!");
			// line: 1822
			
			this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.DIACHI = data.diachimoi;
			let diaChiLD_TSL_Obj = this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau;
			let lay_ds_donviql_Params = {
				"vphanvung_id": this.$root.token.getPhanVungID()
				,"vphuong_id": diaChiLD_TSL_Obj.PHUONG_ID
				,"vpho_id": diaChiLD_TSL_Obj.PHO_ID
				,"vap_id": diaChiLD_TSL_Obj.AP_ID
				,"vkhu_id": diaChiLD_TSL_Obj.KHU_ID
				, "vkieu": "2"
			};
			let dsDonViQuanLy = [];
			await api
				.lay_ds_donviql(this.axios, JSON.stringify(lay_ds_donviql_Params))
				.then((response) => {
					if (
						response
					) {
						dsDonViQuanLy = response.data.data;
						if (dsDonViQuanLy === null || dsDonViQuanLy.length === 0) {
							
							this.formThongTinThueBao.diaChiLD_TSL_Dau = {};
							this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.DIACHI = "";
							this.$root.toastError(
								"Địa danh này chưa được gán đơn vị quản lý địa chỉ lắp đặt. Hãy liên hệ với Admin để xử lý!"
							);
						} else if (dsDonViQuanLy.length === 1) {
							this.donvi_dau_id = dsDonViQuanLy[0].donvi_id;
						} else if (dsDonViQuanLy.length > 0) {
							this.donvi_cuoi_id = dsDonViQuanLy[0].donviql_ID;
							this.popupChonDonViQuanLyTSL(
								1,
								this.donvi_cuoi_id,
								dsDonViQuanLy
							);
						}
					}
				});
		},
		// async get_key(keyname) {
		// 	let resultFn = "";
		// 	resultFn = await api
		// 		.get_key(this.axios, {
		// 			"keyname": keyname.toString(),
		// 			"phanvung_id": this.$root.token.getPhanVungID().toString()
		// 		})
		// 		.then((response) => {
		// 			if (
		// 				(response.data.error_code)
		// 				 &&
		// 				(response.data.error_code === "BSS-00000000")
		// 			) {
		// 				//"data": "{\"ERROR_CODE\":1,\"MESSAGE\":\"Thành công\",\"RESULT\":{\"DIACHIKH_ID\":20025699}}"
		// 				let dulieuapi = "";
		// 				dulieuapi = response.data.data;
		// 				return dulieuapi;
		// 			} else {
		// 				return "";
		// 			}
		// 		});
		// 	return resultFn;
		// },
		async get_keys(keyname){
            try{
                let response = await api.get_keys(this.axios, keyname)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return Number(response.data.data)
                }else{
                    return 0
                }
            }catch(e){
                return 0
            }
        },
		async loadDanhMuc() {
			this.loading(true);
			this.watcherActive = false;
			await api
				.danhmuc_quoctich(this.axios)
				.then(
					(ret) => (this.formThongTinKhachHang.quocTichOptions = ret.data.data)
				);
			await api
				.danhmuc_loai_giayto(this.axios)
				.then(
					(ret) =>
						(this.formThongTinKhachHang.loaiGiayToOptions = ret.data.data)
				);
			await api
				.danhmuc_dantoc(this.axios)
				.then(
					(ret) => (this.formThongTinKhachHang.danTocOptions = ret.data.data)
				);
			await api
				.danhmuc_dichvu_vt(this.axios)
				.then(
					(ret) => {
						this.formThongTinThueBao.dichVuOptions = ret.data.data;
						this.formThongTinThueBao.dichVuId = this.dichvuvt_id;
					}
				)
				.finally(() => {
					this.dichVuThueBaoChange();
				});
			await api
				.danhmuc_loaikenh(this.axios)
				.then(
					(ret) => (this.formThongTinKhachHang.loaiKenhOptions = ret.data.data)
				);
			await api
				.danhmuc_modem(this.axios)
				.then(
					(ret) => (this.formThongTinKhachHang.modemOptions = ret.data.data)
				);
			await api
				.danhmuc_chuquan(this.axios)
				.then(
					(ret) => (this.formThongTinKhachHang.chuQuanOptions = ret.data.data)
				);
			await api
				.danhmuc_thoihan(this.axios)
				.then(
					(ret) => (this.formThongTinKhachHang.thoiHanOptions = ret.data.data)
				);
			await api
				.danhmuc_trangbi(this.axios)
				.then(
					(ret) => (this.formThongTinKhachHang.trangBiOptions = ret.data.data)
				);
			await api
				.danhmuc_loaicap(this.axios)
				.then(
					(ret) => (this.formThongTinKhachHang.loaiCapOptions = ret.data.data)
				);
			await api
				.danhmuc_loaithietbi(this.axios)
				.then(
					(ret) =>
						(this.formThongTinKhachHang.loaiThietBiOptions = ret.data.data)
				);
			//api_danhmuc_nganhnghe(this.axios).then(ret => this.formThongTinKhachHang.nganhNgheOptions = ret.data.data);
			await api
				.danhmuc_doituong(this.axios)
				.then(
					(ret) => (this.formThongTinThueBao.doiTuongOptions = ret.data.data)
				);
			if(this.formThongTinThueBao.doiTuongOptions.length>0){
				this.formThongTinThueBao.doiTuongId=this.formThongTinThueBao.doiTuongOptions[0].DOITUONG_ID
			}
			await api
				.lay_danhmuc_lapmoi_tt_chung_v7(this.axios, "LOAI_KH")
				.then((ret) => {
					if (ret.data.data.loai_kh) {
						this.formThongTinKhachHang.loaiKhachHangOptions =
							ret.data.data.loai_kh;
					}
				});
			await api
				.danhmuc_tocdocir(this.axios)
				.then(
					(ret) => (this.formThongTinKhachHang.tocDoCirOptions = ret.data.data)
				);
			await api
				.danhmuc_tocdocpir(this.axios)
				.then(
					(ret) => (this.formThongTinKhachHang.tocDoPirOptions = ret.data.data)
				);
			await api
				.sp_ht_tocdo_kenh_combobox(this.axios, JSON.stringify({}))
				.then(
					(ret) => {
						let jsonTocDoKenh = ret.data.data;
						this.formThongTinThueBao.tocDoKenhOptions = jsonTocDoKenh["rs1"];
					}
				);
			let dsNganhNghe = [];
			var apiNganhNghe = api.danhmuc_nganhnghe(this.axios).then((response) => {
				if (
					(response.data.error_code) 
					&&
					(response.data.error_code === "BSS-00000000")
				) {
					let nganhNgheNull = {
						GHICHU: null,
						MANGANH: "",
						NGANHNGHE_ID: -1,
						SUDUNG: 0,
						TENNGANHNGHE: "",
					};
					dsNganhNghe = response.data.data;
					dsNganhNghe = [nganhNgheNull, ...dsNganhNghe];
					this.listbox.nganhngheloc.list = dsNganhNghe;
				}
			});
			// Bo sung 16/04/2022
			// let layTuyenThuIdParams = {
			// 	p_in_id_neo: ThongTinKenhTrangEnums.in_id_neo_tuyenthu,
			// 	p_in_table: ThongTinKenhTrangEnums.in_table_tuyenthu,
			// 	in_dk: ThongTinKenhTrangEnums.in_dk_tuyenthu,
			// 	type: 2
			// };
			// Fix atbm: Đổi params và fix cứng params giống code C#
			let layTuyenThuIdParams = {
				param: "KHDN",
				type: 2
			};
			api
				.lay_tuyenThuId(this.axios, JSON.stringify(layTuyenThuIdParams))
				.then((ret) => (this.formThongTinKhachHang.tuyenThuId = ret.data.data));
				
			// let layDonViQuanLyIdParams = {
			// 	p_in_id_neo: ThongTinKenhTrangEnums.in_id_neo_donvi,
			// 	p_in_table: ThongTinKenhTrangEnums.in_table_donvi,
			// 	p_in_dk: ThongTinKenhTrangEnums.in_dk_donvi,
			// 	type: 6
			// };
			// Fix atbm: Đổi params và fix cứng params giống code C#
			let layDonViQuanLyIdParams = {
				param: "webservice_hni",
				type: 6
			};
			api
				.lay_donViQuanLyId(this.axios, JSON.stringify(layDonViQuanLyIdParams))
				.then(
					(ret) => (this.formThongTinKhachHang.donViQuanLyId = ret.data.data)
				);
			Promise.all(apiNganhNghe)
				.then(async () => { })
				.catch(() => { })
				.then(async () => { })
				.finally(() => {
					this.watcherActive = false;
					if (
						this.listbox.nganhngheloc.list &&
						this.listbox.nganhngheloc.list.length > 0
					) {
						// this.listbox.nganhngheloc.value =
						// this.listbox.nganhngheloc.list[0].NGANHNGHE_ID;
					}
				})
				.then(() => {
					this.watcherActive = true;
				});
			api
				.lay_thamSoMacDinh(this.axios, JSON.stringify({
					p_param: ""
					,p_type: 1
				}))
				.then((response) => {
					let tuDongSinhSoAoCount = response.data.data.filter(function (el) {
						return el.ma_ts.toString() === "TUDONG_SINH_SOAO";
					}).length;
					if (tuDongSinhSoAoCount > 0) {
						this.TUDONG_SINH_SOAO = true;
					} else {
						this.TUDONG_SINH_SOAO = false;
					}
				});
			this.formThongTinKhachHang.accountId = this.ma_vnpt;
			this.formThongTinThueBao.TSL.accountId = this.ma_vnpt;
			this.formThongTinThueBao.dichVuId = this.dichvuvt_id;
			this.formThongTinThueBao.tenThueBao = this.ten_tb;
			this.hdtb_id_hni = this.hdtb_id_hni_prop;
			this.formThongTinThueBao.diaChiLD = this.diachi_ld;
			this.formThongTinThueBao.diaChiTB = this.diachi_tb;
			this.formThongTinKhachHang.dienThoai = this.dienThoai;
			if (
				this.ma_vnpt === null ||
				this.ma_vnpt === undefined ||
				this.ma_vnpt.toString() === ""
			) {
				await this.SinhMaDVVT();
			}
			this.loading(false);
			this.SetButton(-1);
		},
		showModalHuongKetNoi() {
			
			if (this.diachiLD.TINH_ID === 0) {
				this.$root.toastError(
					"Vui lòng chọn địa chỉ lắp đặt"
				);
				return;
			}
			this.duLieuPopupHuongKetNoi = {
				kieu: this.formThongTinThueBao.chuQuanId == 9 ? 0 : 1,
				dichvuvt_id: this.formThongTinThueBao.dichvuvt_ID,
				tinhkn_id: this.diachiLD.TINH_ID,
			};
			this.$refs.popupDSHuongKNV2['duLieuPopupHuongKetNoi'] = this.duLieuPopupHuongKetNoi;
			this.$refs.popupDSHuongKNV2.showModal();
		},
		async searchAccountModalClose(data) {		
			if (
				typeof data.ma_kh === "undefined" ||
				data.ma_kh === null ||
				data.ma_kh.toString() === ""
			) {
				this.formThongTinKhachHang.maKH = data.ma_kh;
				this.$root.toastInfo(
					"Không tìm thấy thông tin khách hàng của mã KH hiện tại"
				);
				return;
			}
			let maKhHienTai = data.ma_kh;
			let trangThaiThueBao = -1;
			let dataKiemTraMaKh = {
				vma_kh: data.ma_kh,
				vkhachhang_id: data.thuebao.khachhang_id,
				vphanvung_id: this.$root.token.getPhanVungID(),
			};
			console.log(dataKiemTraMaKh.vma_kh)
			this.formThongTinKhachHang.maKH=dataKiemTraMaKh.vma_kh
			this.txtMaKH_KeyPress()			
			// await api
				// .kiemtra_thangthai_makh(this.axios, JSON.stringify(dataKiemTraMaKh))
				// .then((ret) => {
				// 	trangThaiThueBao = ret.data.data;
				// });

			if (trangThaiThueBao === 7 || trangThaiThueBao === 9) {
				this.$root.toastError(
					"Thuê bao này đã thanh lý, bạn không thể thực hiện báo hỏng!"
				);
				return;
			} else {
				await this.HienThiTT_DBKH(data, true); // htds = trang thai hien thi thue bao
			}
		},
		dichVuThueBaoChange() {
			
			api.danhmuc_loaihinh_tb(this.axios, this.formThongTinThueBao.dichVuId).then((ret) => {
				this.formThongTinThueBao.loaiHinhOptions = ret.data.data;
				this.formThongTinThueBao.loaiHinhId = this.loaitb_id;
			})
		},
		Popup(modalId) {
			this.$bvModal.show(modalId);
		},
		ClosePopup(modalId) {
			this.$bvModal.hide(modalId);
		},
		//callback form địa chỉ khách hàng
		acceptDiaChiKN(data) {
			// console.log("Địa chỉ kết nối");
			// console.log(JSON.stringify(data));
			this.formThongTinKhachHang.diachikh_id;
			this.diachiKN.DIACHI = data.DIACHIMOI;
		},
		acceptDiaChiKH(data) {
			// console.log("Địa chỉ khách hàng");
			// console.log(JSON.stringify(data));
			this.formThongTinKhachHang.diachikh_id;
			this.diachiLD.DIACHI = data.DIACHIMOI;
		},
		popupTieuNganh() {
			if (!this.watcherActive) return;
			if (this.listbox.nganhngheloc.value !== -1) {
				this.popupComponent = () =>
					import("../../InstallNewSubs/Popups/popupTieuNganh");
				this.popupComponentName = "popupTieuNganh";
				this.popupComponentData = {
					vnganhnghe_id: this.listbox.nganhngheloc.value,
					tieunganh: this.tieunganh,
				};
				this.Popup("popupComponents");
			}
		},
		showModal() {
			this.$refs["popupChonDiaChiKhachHang"].show();
		},
		hideModal() {
			this.$refs["popupChonDiaChiKhachHang"].hide();
		},
		chapnhan_gankhuvuc() {
			//this.getDanhsachThuebao();
			this.$bvModal.hide("gankhuvuc");
		},
		async popupClosed(val) {
			if (!val) {
				this.popupRetVal = {};
				this.ClosePopup("popupComponents");
				return;
			}
			this.loading(true);
			try {
				switch (this.popupComponentName) {
					case "frmChonSM_WAN": {
						this.formThongTinKhachHang.mgwan.soAo = val.somay;
						break;
					}
					case "frmChonSM_TSL_Dau": {
						if (this.formThongTinThueBao.TSL.soAoDau != "") {
							if (val.somay == this.formThongTinThueBao.TSL.SoAoCuoi) {
								//Message_Box.ShowTB("Số ảo điểm đầu phải khác số ảo điểm cuối !");
								this.$toast.error("Số ảo điểm đầu phải khác số ảo điểm cuối !");
								//btnSoAoTSL_Dau.Focus();
								return;
							}
						}
						this.formThongTinThueBao.TSL.soAoDau = val.somay;
						break;
					}
					case "frmChonSM_TSL_Cuoi": {
						if (this.formThongTinThueBao.TSL.soAoCuoi != "") {
							if (val.somay == this.formThongTinThueBao.TSL.soAoDau) {
								//Message_Box.ShowTB("Số ảo điểm đầu phải khác số ảo điểm cuối !");
								this.$toast.error("Số ảo điểm cuối phải khác số ảo điểm đầu !");
								//btnSoAoTSL_Dau.Focus();
								return;
							}
						}
						this.formThongTinThueBao.TSL.soAoCuoi = val.somay;
						break;
					}
					case "frmChonKhuVuc": {
						this.dsHDTB_KV = val.hdtb_kv_data;
						console.log("frmChonKhuVuc *********************************** \n" + JSON.stringify(this.dsHDTB_KV));
						this.formThongTinThueBao.khuVuc = val.ma_kv;
						break;
					}
					case "popupTieuNganh": {
						//// // console.log("popupTieuNganh" + JSON.stringify(val));
						this.tieunganh.TNC1_ID = val.TNC1_ID;
						this.tieunganh.TNC2_ID = val.TNC2_ID;
						this.tieunganh.TNC3_ID = val.TNC3_ID;
						this.tieunganh.TNC4_ID = val.TNC4_ID;
						// // console.log("popupTieuNganh" + JSON.stringify(this.tieunganh));
					}
				}
			} catch (ex) {
				this.$toast.error("" + ex);
				if (this.debug) console.error(ex);
			} finally {
				this.ClosePopup("popupComponents");
				this.loading(false);
			}
		},
		popupChonKhuVuc() {
			// btnMaNVKD_Click
			if (
				!this.diachiLD.QUAN_ID ||
				this.diachiLD.QUAN_ID <= 0 ||
				!this.diachiLD.PHUONG_ID ||
				this.diachiLD.PHUONG_ID <= 0
			) {
				this.$toast.error(
					"Chưa có thông tin về địa chỉ lắp đặt. Bạn hãy kiểm tra lại!"
				);
				return;
			}
			this.popupComponent = () =>
				import("../../InstallNewSubs/Popups/popupGanKhuVucQuanLyThueBao.vue");
			this.popupComponentName = "frmChonKhuVuc";
			this.popupComponentData = {
				diachi: this.formThongTinThueBao.diaChiLD,
				khuvuc_id: this.$root.token.getMaTinh(),
				hdtb_id: this.formThongTinThueBao.thueBaoId,
				thuebao_id: this.formThongTinThueBao.thueBaoId,
				donviql_id: this._donviql_id,
				donvi_id: this.$root.token.getDonViID(),
				diachi: this.diachiLD,
			};
			this.Popup("popupComponents");
		},
		async TaoDuLieu_DiaChi(themmoi, kieu) {
			this.dsDiaChi = [];
			this.dsDiaChiHDKH = [];
			this.dsDiaChiHDTT = [];
			this.dsDiaChiHDTB = [];
			var _diachikh_id = this.diachikh_id;
			if (kieu === 1 || kieu === 4) {
				var rowDiaChiKH = {
					DIACHI_ID: 0,
					TINH_ID: 0,
					QUAN_ID: 0,
					PHUONG_ID: 0,
					PHO_ID: 0,
					AP_ID: 0,
					KHU_ID: 0,
					DACDIEM_ID: 0,
					SONHA: "",
					DIACHI: "",
					HDKH_ID: 0,
					KINHDO: 0,
					VIDO: 0,
					DIACHIBC_ID: 0,
					HDTT_ID: 0,
					KINHDO_BC: "",
					VIDO_BC: "",
					SONHA: "",
					BLOCK: "",
					TANG: "",
					PHONG: "",
				};
				if (themmoi) {
					console.log("sinh dia chi kh moi")
					_diachikh_id=await this.get_keys('DIACHI')
				}
				
                rowDiaChiKH.DIACHI_ID = _diachikh_id;
                rowDiaChiKH.TINH_ID = this.tinhkh_id
                rowDiaChiKH.QUAN_ID =this.quankh_id
                if (this.phuongkh_id != 0)
                    rowDiaChiKH.PHUONG_ID = this.phuongkh_id
                if (this.phokh_id != 0)
                    rowDiaChiKH.PHO_ID = this.phokh_id
                if (this.apkh_id != 0)
                    rowDiaChiKH.AP_ID = this.apkh_id 
                if (this.khukh_id != 0)
                    rowDiaChiKH.KHU_ID =this.khukh_id
                rowDiaChiKH.DACDIEM_ID = this.dacdiemkh_id
                // rowDiaChiKH.SONHA = CommonFunction.StringToString(sonhakh ?? "");
                // rowDiaChiKH.DIACHI = CommonFunction.StringToString(txtDiaChiKH.Text);
				rowDiaChiKH.SONHA = this.diachiKH.sonhakh;
				rowDiaChiKH.DIACHI = this.diachiKH.diaChiKH;
                this.dsDiaChi.push(rowDiaChiKH);
				if (themmoi) {
					var rowDiaChiHDKH = {};
					rowDiaChiHDKH.DIACHI_ID = _diachikh_id;					
					rowDiaChiHDKH.KHACHHANG_ID = this.formThongTinKhachHang.khachhang_id;
					this.dsDiaChiHDKH.push(rowDiaChiHDKH);
				}
				
			}
			if (kieu.toString() === "2" || kieu.toString() === "4" ) {
				var rowDiaChiTT = {
					DIACHI_ID: 0,
					TINH_ID: 0,
					QUAN_ID: 0,
					PHUONG_ID: 0,
					PHO_ID: 0,
					AP_ID: 0,
					KHU_ID: 0,
					DACDIEM_ID: 0,
					SONHA: "",
					DIACHI: "",
					HDKH_ID: 0,
					KINHDO: 0,
					VIDO: 0,
					DIACHIBC_ID: 0,
					HDTT_ID: 0,
					KINHDO_BC: "",
					VIDO_BC: "",
					SONHA: "",
					BLOCK: "",
					TANG: "",
					PHONG: "",
				};
				var _diachitt_id = this.diachitt_id;
				this.diachiTT = this.diachiTB;
				if (themmoi){
					_diachitt_id=await this.get_keys('DIACHI')
				}
				//DiachiTT
				rowDiaChiTT.DIACHI_ID = _diachitt_id;
				rowDiaChiTT.TINH_ID = this.diachiTT.TINH_ID;
				rowDiaChiTT.QUAN_ID = this.diachiTT.QUAN_ID;
				rowDiaChiTT.PHUONG_ID = this.diachiTT.PHUONG_ID;
				if (this.diachiTT.PHO_ID && this.diachiTT.PHO_ID != 0)
					rowDiaChiTT.PHO_ID = this.diachiTT.PHO_ID;
				if (this.diachiTT.AP_ID && this.diachiTT.AP_ID != 0)
					rowDiaChiTT.AP_ID = this.diachiTT.AP_ID;
				if (this.diachiTT.KHU_ID && this.diachiTT.KHU_ID != 0)
					rowDiaChiTT.KHU_ID = this.diachiTT.KHU_ID;
				if (this.diachiTT.DACDIEM_ID && this.diachiTT.DACDIEM_ID != 0)
					rowDiaChiTT.DACDIEM_ID = this.diachiTT.DACDIEM_ID;
				rowDiaChiTT.SONHA = this.diachiTT.SONHA;
				rowDiaChiTT.DIACHI = this.diachiTT.DIACHI;
					
				this.dsDiaChi.push(rowDiaChiTT);
				if (themmoi)
				{
					var rowDiaChiHDTT = {};
					rowDiaChiHDTT.DIACHI_ID = _diachitt_id;
					rowDiaChiHDTT.THANHTOAN_ID = this.thongTinThanhToan.thanhtoan_id;
					this.dsDiaChiHDTT.push(rowDiaChiHDTT);
				}
			}
			if (kieu.toString() === "3" || kieu.toString() === "4") {
				//Create by Hiếu    - 14/7/2010 - Địa chỉ lắp đặt & địa chỉ thuê bao
				var rowDiaChiTB = {
					DIACHI_ID: 0,
					TINH_ID: 0,
					QUAN_ID: 0,
					PHUONG_ID: 0,
					PHO_ID: 0,
					AP_ID: 0,
					KHU_ID: 0,
					DACDIEM_ID: 0,
					SONHA: "",
					DIACHI: "",
					HDKH_ID: 0,
					KINHDO: 0,
					VIDO: 0,
					DIACHIBC_ID: 0,
					HDTT_ID: 0,
					KINHDO_BC: "",
					VIDO_BC: "",
					SONHA: "",
					BLOCK: "",
					TANG: "",
					PHONG: "",
				};
				var rowDiachiLD = {
					DIACHI_ID: 0,
					TINH_ID: 0,
					QUAN_ID: 0,
					PHUONG_ID: 0,
					PHO_ID: 0,
					AP_ID: 0,
					KHU_ID: 0,
					DACDIEM_ID: 0,
					SONHA: "",
					DIACHI: "",
					HDKH_ID: 0,
					KINHDO: 0,
					VIDO: 0,
					DIACHIBC_ID: 0,
					HDTT_ID: 0,
					KINHDO_BC: "",
					VIDO_BC: "",
					SONHA: "",
					BLOCK: "",
					TANG: "",
					PHONG: "",
				};
				var _diachitb_id=0
				if(themmoi){
					_diachitb_id = await this.get_keys('DIACHI');
				}
			
				rowDiaChiTB.DIACHI_ID = _diachitb_id;
				rowDiaChiTB.TINH_ID = this.diachiTB.TINH_ID;
				rowDiaChiTB.QUAN_ID = this.diachiTB.QUAN_ID;
				rowDiaChiTB.PHUONG_ID = this.diachiTB.PHUONG_ID;
				if (this.diachiTB.PHO_ID && this.diachiTB.PHO_ID != 0)
					rowDiaChiTB.PHO_ID = this.diachiTB.PHO_ID;
				if (this.diachiTB.AP_ID && this.diachiTB.AP_ID != 0)
					rowDiaChiTB.AP_ID = this.diachiTB.AP_ID;
				if (this.diachiTB.KHU_ID && this.diachiTB.KHU_ID != 0)
					rowDiaChiTB.KHU_ID = this.diachiTB.KHU_ID;
				if (this.diachiTB.DACDIEM_ID && this.diachiTB.DACDIEM_ID != 0)
					rowDiaChiTB.DACDIEM_ID = this.diachiTB.DACDIEM_ID;
				rowDiaChiTB.SONHA = this.diachiTB.SONHA;
				rowDiaChiTB.DIACHI = this.diachiTB.DIACHI;
					
				this.dsDiaChi.push(rowDiaChiTB);
				
				if (this.formThongTinThueBao.dichVuId.toString() === ThongTinKenhTrangEnums.DichVuVienThong.MEGAWAN.toString()) {
					
					//Địa chỉ lắp đặt --> dataset dsdiachi
					console.log("địa chỉ lđ***********************")
					console.log(JSON.stringify(this.diachiLD))
					var _diachild_id=0
					if(themmoi){
						_diachild_id = await this.get_keys('DIACHI');
					}
					rowDiachiLD.DIACHI_ID = _diachild_id;
					rowDiachiLD.TINH_ID = this.diachiLD.TINH_ID;
					rowDiachiLD.QUAN_ID = this.diachiLD.QUAN_ID;
					rowDiachiLD.PHUONG_ID = this.diachiLD.PHUONG_ID;
					if (this.diachiLD.PHO_ID && this.diachiLD.PHO_ID != 0)
						rowDiachiLD.PHO_ID = this.diachiLD.PHO_ID;
					if (this.diachiLD.AP_ID && this.diachiLD.AP_ID != 0)
						rowDiachiLD.AP_ID = this.diachiLD.AP_ID;
					if (this.diachiLD.KHU_ID && this.diachiLD.KHU_ID != 0)
						rowDiachiLD.KHU_ID = this.diachiLD.KHU_ID;
					if (this.diachiLD.DACDIEM_ID && this.diachiLD.DACDIEM_ID != 0)
						rowDiachiLD.DACDIEM_ID = this.diachiLD.DACDIEM_ID;
					rowDiachiLD.SONHA = this.diachiLD.SONHA;
					// Kieun v
					rowDiachiLD.BLOCK = this.diachiLD.BLOCK;
					rowDiachiLD.TANG = this.diachiLD.TANG;
					rowDiachiLD.PHONG = this.diachiLD.PHONG;
					rowDiachiLD.MOTA = this.diachiLD.MOTA;
					rowDiachiLD.DIACHI = this.diachiLD.DIACHI
					this.dsDiaChi.push(rowDiachiLD);
				}
				//console.log(this.formThongTinThueBao.dichVuId.toString() === ThongTinKenhTrangEnums.DichVuVienThong.TSL.toString());
				if 
				(
					this.formThongTinThueBao.dichVuId.toString() === ThongTinKenhTrangEnums.DichVuVienThong.TSL.toString()
				)
				{
					var rowDiaChiDau = {
						DIACHI_ID: 0,
						TINH_ID: 0,
						QUAN_ID: 0,
						PHUONG_ID: 0,
						PHO_ID: 0,
						AP_ID: 0,
						KHU_ID: 0,
						DACDIEM_ID: 0,
						SONHA: "",
						DIACHI: "",
						HDKH_ID: 0,
						KINHDO: 0,
						VIDO: 0,
						DIACHIBC_ID: 0,
						HDTT_ID: 0,
						KINHDO_BC: "",
						VIDO_BC: "",
						SONHA: "",
						BLOCK: "",
						TANG: "",
						PHONG: "",
					};
					var rowDiaChiCuoi = {
						DIACHI_ID: 0,
						TINH_ID: 0,
						QUAN_ID: 0,
						PHUONG_ID: 0,
						PHO_ID: 0,
						AP_ID: 0,
						KHU_ID: 0,
						DACDIEM_ID: 0,
						SONHA: "",
						DIACHI: "",
						HDKH_ID: 0,
						KINHDO: 0,
						VIDO: 0,
						DIACHIBC_ID: 0,
						HDTT_ID: 0,
						KINHDO_BC: "",
						VIDO_BC: "",
						SONHA: "",
						BLOCK: "",
						TANG: "",
						PHONG: "",
					};
					var _diachidau_id = this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.DIACHI_ID
					if (themmoi) {
						_diachidau_id=await this.get_keys('DIACHI')
					}
					rowDiaChiDau.DIACHI_ID = _diachidau_id
					rowDiaChiDau.TINH_ID = this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.TINH_ID;
					rowDiaChiDau.QUAN_ID = this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.QUAN_ID;
					rowDiaChiDau.PHUONG_ID = this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.PHUONG_ID;
					if (this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.PHO_ID != 0)
							rowDiaChiDau.PHO_ID = this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.PHO_ID;
					if (this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.AP_ID != 0)
							rowDiaChiDau.AP_ID = this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.AP_ID;
					if (this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.KHU_ID != 0)
							rowDiaChiDau.KHU_ID = this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.KHU_ID;
					rowDiaChiDau.SONHA = this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.SONHA;
					rowDiaChiDau.DIACHI = this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.DIACHI;
					this.dsDiaChi.push(rowDiaChiDau);
					var diachicuoi_id =this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.DIACHI_ID
					if (themmoi.toString() === "true") {
						diachicuoi_id=await this.get_keys("DIACHI")
					}
							
					rowDiaChiCuoi.DIACHI_ID = diachicuoi_id;
					rowDiaChiCuoi.TINH_ID = this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.TINH_ID;
					rowDiaChiCuoi.QUAN_ID = this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.QUAN_ID;
					rowDiaChiCuoi.PHUONG_ID = this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.PHUONG_ID;
					if (this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.PHUONG_ID != 0)
						rowDiaChiCuoi.PHO_ID = this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.PHO_ID;
					if (this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.AP_ID != 0)
						rowDiaChiCuoi.AP_ID = this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.AP_ID;
					if (this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.KHU_ID != 0)
						rowDiaChiCuoi.KHU_ID = this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.KHU_ID;
					rowDiaChiCuoi.SONHA = this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.SONHA;
					rowDiaChiCuoi.DIACHI = this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.DIACHI;
					this.dsDiaChi.push(rowDiaChiCuoi);
				}
				if (themmoi)
				{
					var rowDiaChiHDTB = {};
					
					rowDiaChiHDTB.DIACHI_ID = _diachitb_id;
					rowDiaChiHDTB.THUEBAO_ID = this.formThongTinThueBao.thueBaoId;
					rowDiaChiHDTB.DIACHILD_ID = rowDiachiLD.DIACHI_ID;
					
					this.dsDiaChiHDTB.push(rowDiaChiHDTB);
				}
				
			}
			
			// console.log("this.dsDiaChi: -------------- \n" + JSON.stringify(this.dsDiaChi));
			// console.log("this.dsDiaChiHDKH-------------- \n" + JSON.stringify(this.dsDiaChiHDKH));
			// console.log("this.dsDiaChiHDTT -------------- \n" + JSON.stringify(this.dsDiaChi[0]));
			// console.log("this.dsDiaChiHDTT -------------- \n" + JSON.stringify(this.dsDiaChiHDTT));
			// console.log("this.dsDiaChiHDTB -------------- \n" + JSON.stringify(this.dsDiaChiHDTB));
			// console.log("this.dsDB_TSL -------------- \n" + JSON.stringify(this.dsDB_TSL));
		},
		async TaoDuLieu_DBKH(themmoi) {
			let maKh = "";
			this.dsDBKH=[]
			let rowDBKH = {};
			if (themmoi == true) {
				// Lay khachhangid qua api sinh ma KH
				let khachhang_id_temp = await this.get_keys('DB_KHACHHANG');
				let taoMaKhachHangParams = {
					vphanvung_id: this.$root.token.getPhanVungID(),
					vdonvi_id: this.$root.token.getDonViID(),
				};
				await api
					.sinh_ma_kh(this.axios, JSON.stringify(taoMaKhachHangParams))
					.then((response) => {
						// Hien tai Api dang loi: 500
						this.formThongTinKhachHang.maKH = response.data.data;
					});
				rowDBKH.KHACHHANG_ID = khachhang_id_temp;
				this.formThongTinKhachHang.khachhang_id=khachhang_id_temp
			} else {
				rowDBKH.KHACHHANG_ID = this.formThongTinKhachHang.khachhang_id;
			}
			rowDBKH.MA_KH = this.formThongTinKhachHang.maKH;
			rowDBKH.TEN_KH = this.formThongTinKhachHang.tenKhachHang;
			rowDBKH.DIACHI_KH = this.formThongTinKhachHang.diaChiKH;
			rowDBKH.SO_DT = this.formThongTinKhachHang.dienThoai;
			rowDBKH.NGAYLAP_HD = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
				
			rowDBKH.NGUOI_DD = "Ban KHDN HNI";
			rowDBKH.LOAIGT_ID = this.formThongTinKhachHang.loaiGiayToId;
			rowDBKH.KHLON_ID = 1; // k chuan cac tinh; truong nay bo di rồi 22/06/2018
			rowDBKH.SO_GT = this.formThongTinKhachHang.soGiayTo;
			if (this.formThongTinKhachHang.ngayCap.toString() != "") {
				rowDBKH.NGAYCAP =
					this.formThongTinKhachHang.ngayCap.toString() +" "+ "00:00:00";
			}
			//rowDBKH.NOICAP = txtNoiCap.Text;
			if (this.formThongTinKhachHang.ngaySinh.toString() != "") {
				rowDBKH.NGAY_SN =
					this.formThongTinKhachHang.ngaySinh.toString() + " " + "00:00:00";
			}
			//rowDBKH.SO_FAX = txtFaxKH.Text;
			if (this.formThongTinKhachHang.loaiKHId != 0)
				rowDBKH.LOAIKH_ID = this.formThongTinKhachHang.loaiKHId;
			else rowDBKH.LOAIKH_ID = 1;
			rowDBKH.CHUCDANH = "";
			if (themmoi == true) {
				let newRowDBKH = { NGAY_CN: "", NGUOI_CN: "", MAY_CN: "" };
				rowDBKH = { ...rowDBKH, ...newRowDBKH };
				rowDBKH.NGAY_CN = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
				rowDBKH.NGUOI_CN = this.$root.token.getUserName();
				rowDBKH.MAY_CN = 'localhost';
			}
			if (!this.formThongTinKhachHang.ngayHetHan) {
				rowDBKH.NGAYHETHAN_GT = this.formThongTinKhachHang.ngayHetHan;
			}
			//rowDBKH.NGAYHETHAN_GT= 'null';
			rowDBKH.GIOITINH = this.formThongTinKhachHang.gioiTinh;
			//if (cboNganhNghe.SelectedValue != null)
			rowDBKH.NGANHNGHE_ID = this.formThongTinKhachHang.nganhNgheId;
			//TuấnNA thêm tạo dữ liệu tiểu ngành cấp 1,2,3 --- Ngày 03/12/2010
			rowDBKH.TNC1_ID = this.tieunganh.TNC1_ID;
			rowDBKH.TNC2_ID = this.tieunganh.TNC2_ID;
			rowDBKH.TNC3_ID = this.tieunganh.TNC3_ID;
			rowDBKH.TNC4_ID = this.tieunganh.TNC4_ID;
			//
			if (this.formThongTinKhachHang.quoctich_id != 0)
				rowDBKH.QUOCTICH_ID = this.formThongTinKhachHang.quocTichId;
			if (this.formThongTinKhachHang.danTocId != 0)
				rowDBKH.DANTOC_ID = this.formThongTinKhachHang.danTocId;
			rowDBKH.DIACHI_NDD = "Ban KHDN HNI";
			this.dsDBKH.push(rowDBKH);
		},
		async TaoDuLieu_DBTT(themmoi) {
			this.dsDBTT=[]
			let rowDBTT = {
				THANHTOAN_ID: 0,
				KHACHHANG_ID: 0,
				MA_TT: "",
				TEN_TT: "",
				DIACHI_TT: "",
				DIACHI_CT: "",
				DIACHI_BC: "",
				MATB_DD: "",
				DOTGIAO_ID: 0,
				MST: "",
				HTTT_ID: "",
				GHICHU: "",
				STK: "",
				CHU_TK: "",
				DONVI_ID: "",
			};
			let thanhtoan_id = 0;
			let ma_tt = "";
			
			if (themmoi) {
				this.thongTinThanhToan.thanhtoan_id=await this.get_keys('DB_THANHTOAN')
				let sinhMaThanhToanParams = {
					vphanvung_id: this.$root.token.getPhanVungID(),
					vdonvi_id: this.$root.token.getDonViID(),
					vquan_id: this.diachiLD.quan_id,
				};
				await api
					.sinh_matt_v2(this.axios, JSON.stringify(sinhMaThanhToanParams))
					.then(
						(response) =>
							(this.thongTinThanhToan.ma_tt = response.data.data)
					);
			}
			if (!themmoi) {
				//cmUlt.CopyRow(tcdanhba.LAY_DS_DBTT_THEO_TTID(thanhtoan_id).Tables[0].Rows[0], rowDBTT);
				// ham get danh sach thanh toan cua thanh toan id
				let get_ds_dbtt_params = 
				{
					vphanvung_id: this.$root.token.getPhanVungID(),
					in_thanhtoan_id : this.thongTinThanhToan.thanhtoan_id
				}
				await api.lay_ds_dbtt_theo_ttid(this.axios, JSON.stringify(get_ds_dbtt_params))
					.then(
						(response) => {
							if (response.data.data&&response.data.data.length > 0) {
								rowDBTT = response.data.data[0];
								//upper case
								for(var i = 0; i < rowDBTT.length; i++){ 
									for (var key in rowDBTT[i]) {
										if(key.toUpperCase() !== key){
											rowDBTT[i][key.toUpperCase()] = rowDBTT[i][key];
											delete rowDBTT[i][key];
										}
									}
								}
							}
						}
							
					);
			}
			rowDBTT.THANHTOAN_ID = this.thongTinThanhToan.thanhtoan_id
			rowDBTT.KHACHHANG_ID = this.formThongTinKhachHang.khachhang_id;
			rowDBTT.MA_TT = this.thongTinThanhToan.ma_tt;
			rowDBTT.TEN_TT = this.formThongTinKhachHang.tenKhachHang;
			rowDBTT.DIACHI_TT = this.formThongTinKhachHang.diaChiKH;
			//rowDBTT.DIACHI_CT = CommonFunction.StringToString(txtDiaChiKH.Text);
			//rowDBTT.DIACHI_BC = CommonFunction.StringToString(txtDiaChiKH.Text);
			rowDBTT.DIACHI_CT = this.formThongTinKhachHang.diaChiKH;
			rowDBTT.DIACHI_BC = this.formThongTinKhachHang.diaChiKH;
			
			if (this.formThongTinThueBao.dichVuId === ThongTinKenhTrangEnums.DichVuVienThong.MEGAWAN) {
				rowDBTT.MATB_DD = this.formThongTinKhachHang.accountId;
			} else if (this.formThongTinThueBao.dichVuId === ThongTinKenhTrangEnums.DichVuVienThong.TSL) {
				rowDBTT.MATB_DD = this.formThongTinThueBao.TSL.maTSL;
			}
			if (this.tuyenthu_id !== -1) rowDBTT.TUYENTHU_ID = this.tuyenthu_id;
			rowDBTT.DOTGIAO_ID = 1;
			rowDBTT.MST = "";
			rowDBTT.HTTT_ID = 1;
			rowDBTT.GHICHU = "Thuê bao mới tạo từ kênh trắng KHDN HNI";
			//
			rowDBTT.STK = "";
			rowDBTT.CHU_TK = "";
			rowDBTT.DONVI_ID = this.$root.token.getDonViID();
			if (themmoi == true) {
				let truongThemMoiObj = { NGAY_CN: "", NGUOI_CN: "", MAY_CN: "" };
				rowDBTT = { ...rowDBTT, ...truongThemMoiObj };
				rowDBTT.MAY_CN = 'localhost';
				rowDBTT.NGUOI_CN = this.$root.token.getUserName();
				rowDBTT.NGAY_CN = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
			}
			///TuấnNA thêm code lấy tuyenthu_id vào bang danh bạ thanh toán --- Ngày 20/09/2010
			this.dsDBTT.push(rowDBTT);
		},
		async TaoDuLieu_DBTB(themmoi) {
			this.dSDBTB = [];
			
			if (themmoi) {
				this.formThongTinThueBao.thueBaoId =await this.get_keys('DB_THUEBAO')
				 //rowDBTB.thuebao_id = this.formThongTinThueBao.thueBaoId;	
						
			}			
			let rowDBTB = {};	
			
			if(themmoi==false)	cmUlt.CopyRow(this.lay_ds_dbtb_theo_tbid(thuebao_id).Tables[0].Rows[0], rowDBTB);
			rowDBTB.THUEBAO_ID = this.formThongTinThueBao.thueBaoId;	
			if (
				this.dichvuvt_id == 8||this.dichvuvt_id ==7||this.dichvuvt_id ==26
			) {
				rowDBTB.MA_TB = this.formThongTinKhachHang.accountId; 
			} else {
				rowDBTB.MA_TB = this.formThongTinThueBao.TSL.maTSL;
			}
			
			rowDBTB.TEN_TB = this.formThongTinThueBao.tenThueBao;
			rowDBTB.DIACHI_TB = this.formThongTinThueBao.diaChiThueBao;
			rowDBTB.LOAITB_ID = this.formThongTinThueBao.loaiHinhId;
			rowDBTB.DOITUONG_ID = this.formThongTinThueBao.doiTuongId;
			rowDBTB.DICHVUVT_ID = this.dichvuvt_id;
			rowDBTB.THANHTOAN_ID = this.thongTinThanhToan.thanhtoan_id;
			rowDBTB.KHACHHANG_ID = this.formThongTinKhachHang.khachhang_id;
			rowDBTB.CUOC_TB = 0;
			if (themmoi) {
				rowDBTB.TRANGTHAITB_ID =
					ThongTinKenhTrangEnums.TrangThaiTB.TRANGTHAI_THUEBAO_THUONG;
			}
			rowDBTB.DONVI_ID = this.formThongTinKhachHang.donViQuanLyId
			rowDBTB.NGAY_SD = moment(new Date()).format("DD/MM/YYYY HH:mm:ss"); // Can hoi lai db format chuan
			if (
				Number(this.formThongTinThueBao.dichVuId) ===
				Number(ThongTinKenhTrangEnums.DichVuVienThong.MEGAWAN)
			) {
				rowDBTB.KIEULD_ID = ThongTinKenhTrangEnums.KieuLapDat.DM_MGW_DOCLAP;
			} else {
				rowDBTB.KIEULD_ID = ThongTinKenhTrangEnums.KieuLapDat.DM_TSL_LUONG;
			}
			if (themmoi == true) {
				rowDBTB.NGAY_CN = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
				rowDBTB.NGUOI_CN = this.$root.token.getUserName();
				rowDBTB.MAY_CN = 'localhost';
			}
			rowDBTB.DIACHI_LD = this.formThongTinThueBao.diaChiLD;
			rowDBTB.CHUYENMANG = 0;
			this.dSDBTB.push(rowDBTB);
			
		},
		async TaoDuLieu_DBTB_MGWAN(themmoi) {
			this.dsDB_MGWAN=[]
			let rowDB_MGWAN = {};
			if (!themmoi) {
				let params = {
					in_phanvung_id: this.$root.token.getPhanVungID(),
					in_thuebao_id: this.formThongTinThueBao.thueBaoId,
				};
				let dsThueBaoMegaWanTheoTbID = [];
				await api
					.lay_ds_db_megawan_theo_tbid(this.axios, JSON.stringify(params))
					.then((response) => {
						dsThueBaoMegaWanTheoTbID = response.data.data;
						if (
							dsThueBaoMegaWanTheoTbID !== null &&
							dsThueBaoMegaWanTheoTbID.length > 0
						) {
							rowDB_MGWAN = dsThueBaoMegaWanTheoTbID[0];
							//uppercase
							for(var i = 0; i < rowDB_MGWAN.length; i++){ 
								for (var key in rowDB_MGWAN[i]) {
									if(key.toUpperCase() !== key){
										rowDB_MGWAN[i][key.toUpperCase()] = rowDB_MGWAN[i][key];
										delete rowDB_MGWAN[i][key];
									}
								}
							}
							
						}
					});
			}
			rowDB_MGWAN.THUEBAO_ID = this.formThongTinThueBao.thueBaoId;
			rowDB_MGWAN.CUOC_TC = 0;
			rowDB_MGWAN.CUOC_TK = 0;
			rowDB_MGWAN.CUOC_HT = 0;
			rowDB_MGWAN.CUOC_TBI = 0;
			rowDB_MGWAN.TOCDO_ID = 0;
			rowDB_MGWAN.LOAIMD_ID = 1;
			rowDB_MGWAN.MADOICAP = "0024"; //mã hni
			rowDB_MGWAN.MA_LT = await this.SINH_MALT();
			rowDB_MGWAN.MA_VNPT = this.ma_vnpt;
			rowDB_MGWAN.LOAIKENH_ID = Number(this.formThongTinKhachHang.loaiKenhId);
			rowDB_MGWAN.CHUQUAN_ID = Number(this.formThongTinKhachHang.chuQuanId);
			rowDB_MGWAN.THOIHAN_ID = Number(this.thoiHanId);
			rowDB_MGWAN.TRANGBI_ID = Number(this.trangBiId);
			rowDB_MGWAN.THIETBIDC_ID = Number(this.loaiThietBiId);
			this.dsDB_MGWAN.push(rowDB_MGWAN);
		},
		async SINH_MALT() {
			let maLT_Temp = "";
			let maPT_Parmas = {
				vphanvung_id: this.$root.token.getPhanVungID(),
				vquan_id: "0",
				vdichvuvt_id: this.formThongTinThueBao.dichVuId,
				vloaitb_id: this.formThongTinThueBao.loaiHinhId,
				vtentat: this.maCCBS,
			};
			return await api
				.sinh_ma_lt(this.axios, JSON.stringify(maPT_Parmas))
				.then((response) => {
					maLT_Temp = response.data.data;
					return maLT_Temp;
				});
		},
		async TaoDuLieu_DB_TSL(themmoi) {
			this.dsDB_TSL=[]
			let rowDB_TSLDau = {};
			let rowDB_TSLCuoi = {};
			let rowDB_TSL = []; // Obj chi nham muc dich nhan thong tin tu db
			let _loaitb_id = 0;
			_loaitb_id = this.loaiHinhId;
			if (!themmoi) {
				
				let layDsThongTinTSLParams = {
					in_phanvung_id: this.$root.token.getPhanVungID(),
					in_thuebao_id: this.formThongTinThueBao.thueBaoId,
				};
				await api
					.lay_ds_db_tsl_theo_tbid(
						axios,
						JSON.stringify(layDsThongTinTSLParams)
					)
					.then((ret) => {
						rowDB_TSL = ret.data.data;
						//uppercase
						for(var i = 0; i < rowDB_TSL.length; i++){ 
							for (var key in rowDB_TSL[i]) {
								if(key.toUpperCase() !== key){
									rowDB_TSL[i][key.toUpperCase()] = rowDB_TSL[i][key];
									delete rowDB_TSL[i][key];
								}
							}
						}
						if (rowDB_TSL.length < 2) {
							this.$root.toastError(
								"Thuê bao truyền số liệu thiếu điểm đầu hoặc điểm cuối"
							);
						} else if (rowDB_TSL && rowDB_TSL.length == 2) {
							let filter_TSL_Dau = rowDB_TSL.filter(function (e) {
								return e.daucuoi_ID.toString() == "1";
							});
							let filter_TSL_Cuoi = rowDB_TSL.filter(function (e) {
								return e.daucuoi_ID.toString() == "2";
							});
							if (filter_TSL_Dau) rowDB_TSLDau = filter_TSL_Dau[0];
							if (filter_TSL_Cuoi) rowDB_TSLCuoi = filter_TSL_Cuoi[0];
						}
					});
			} else {
				// // console.log("TaoDuLieu_DB_TSL: case2");
				rowDB_TSLDau.DAUCUOI_ID = 1;
				rowDB_TSLCuoi.DAUCUOI_ID = 2;
				rowDB_TSLCuoi.MA_TB_SUB = ((this.dichvuvt_id === ThongTinKenhTrangEnums.DichVuVienThong.MEGAWAN) ? this.formThongTinKhachHang.accountId : this.formThongTinThueBao.TSL.maTSL).trim().toLowerCase() + "_02";
				rowDB_TSLDau.MA_TB_SUB = ((this.dichvuvt_id === ThongTinKenhTrangEnums.DichVuVienThong.MEGAWAN) ? this.formThongTinKhachHang.accountId : this.formThongTinThueBao.TSL.maTSL).trim().toLowerCase() + "_01";
			}
			rowDB_TSLDau.THUEBAO_ID=this.formThongTinThueBao.thueBaoId;
			rowDB_TSLDau.DIACHI=this.formThongTinThueBao.diachiLD;
			rowDB_TSLDau.TOCDO_ID=0
			rowDB_TSLDau.CHUQUAN_ID=this.formThongTinThueBao.TSL.chuQuanId;;
			//rowDB_TSLDau.TOCDO_ID=this.formThongTinThueBao.TSL.tocDoKenhId;
			// TODO: convert bang ham chinh trong mounted
			rowDB_TSLDau.MA_LT=await this.SINH_MALT();
			if (Number(this.formThongTinThueBao.TSL.loaiKenhId) != -1){
				rowDB_TSLDau.LOAIKENH_ID=this.formThongTinThueBao.TSL.loaiKenhId
			}else{
				rowDB_TSLDau.LOAIKENH_ID=1
			}
			rowDB_TSLDau.DIACHI_ID=this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.DIACHI_ID
			rowDB_TSLDau.MA_LT=this.ma_tb_ao_ht
			rowDB_TSLDau.MA_VNPT=this.ma_vnpt
			rowDB_TSLCuoi.THUEBAO_ID=this.formThongTinThueBao.thueBaoId; 
			rowDB_TSLCuoi.DIACHI=this.formThongTinThueBao.diachiLD;
			rowDB_TSLCuoi.TOCDO_ID=this.formThongTinThueBao.TSL.tocDoKenhId;
			//this.formThongTinKhachHang.chuQuanId = data.thuebao.chuquan;
			rowDB_TSLCuoi.CHUQUAN_ID=this.formThongTinThueBao.TSL.chuQuanId;
			console.log('chu quan');
			console.log(this.formThongTinThueBao.TSL.chuQuanId);
			
			// TODO: convert bang ham chinh trong mounted
			rowDB_TSLCuoi.MA_LT=await this.SINH_MALT();
			if (Number(this.formThongTinThueBao.TSL.loaiKenhId) != -1){
				rowDB_TSLCuoi.LOAIKENH_ID=this.formThongTinThueBao.TSL.loaiKenhId
			}else{
				rowDB_TSLCuoi.LOAIKENH_ID=1
			}
			rowDB_TSLCuoi.DIACHI_ID=this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.DIACHI_ID;
			rowDB_TSLCuoi.MA_LT=this.ma_tb_ao_ht_cuoi
			rowDB_TSLCuoi.MA_VNPT=this.ma_vnpt
			// // console.log("rowDB_TSLDau ------------------- " + JSON.stringify(rowDB_TSLDau));
			// // console.log("rowDB_TSLCuoi ------------------- " + JSON.stringify(rowDB_TSLCuoi));
			this.dsDB_TSL.push(rowDB_TSLDau);
			this.dsDB_TSL.push(rowDB_TSLCuoi);
		},
		async HienThiTT_DBKH(data, htds) {
			// // // console.log("data kh");
			// // console.log("HienThiTT_DBKH" + JSON.stringify(data));
			//this.formThongTinKhachHang.khachhang_id = data.
			
			if (data.khachhang && data.khachhang.length) {
				this.formThongTinKhachHang.maKH = data.ma_kh;
				this.formThongTinKhachHang.khachhang_id = data.khachhang_id;
				
				this.formThongTinKhachHang.khachhang_id = data.khachhang.khachhang_id;
				this.formThongTinKhachHang.tenKhachHang = data.khachhang.ten_kh;
				this.formThongTinKhachHang.diaChiKH = data.khachhang.diachi_kh;
				this.diachiKH = data.khachhang.diachi_kh;
				this.formThongTinKhachHang.soGiayTo = data.khachhang.so_gt;
				this.formThongTinKhachHang.gioiTinh = data.khachhang.gioi_tinh;
				this.formThongTinKhachHang.ngaySinh = moment(
					data.khachhang.ngay_sn,
					"YYYY-MM-DD HH:mm:ss"
				)
					.format("DD/MM/YYYY")
					.toString();
				this.formThongTinKhachHang.loaiGiayToId = data.khachhang.loaigt_id;
				this.kh_loaiGiayToId = data.khachhang.loaigt_id;
				this.formThongTinKhachHang.quocTichId = data.khachhang.quoctich_id;
				this.formThongTinKhachHang.ngayCap = moment(
					data.khachhang.ngaycap,
					"YYYY-MM-DD HH:mm:ss"
				)
					.format("DD/MM/YYYY")
					.toString();
				this.formThongTinKhachHang.dienThoai = data.khachhang.so_dt;
				this.formThongTinKhachHang.soGiayTo = data.khachhang.so_gt;
				this.formThongTinKhachHang.danTocId = data.khachhang.dantoc_ID;
				this.formThongTinKhachHang.ngayHetHan = moment(
					data.khachhang.ngayhethan_gt,
					"YYYY-MM-DD HH:mm:ss"
				)
					.format("DD/MM/YYYY")
					.toString();
				this.formThongTinKhachHang.gioiTinh = data.khachhang.gioitinh;
				this.formThongTinKhachHang.nganhNgheId = data.khachhang.nganhnghe_ID;
				this.formThongTinKhachHang.loaiKHId = data.khachhang.loaikh_id;
				// this.formThongTinKhachHang.loaiCapId
				// this.formThongTinKhachHang.diaChiKN;
				// this.formThongTinKhachHang.huongKN
				// this.formThongTinKhachHang.loaiNodeId
			} else {
				// // console.log("3457: " + JSON.stringify(data[0]));
				console.log(data[0]);
				this.formThongTinKhachHang.maKH = data[0].ma_KH;
				
				this.formThongTinKhachHang.khachhang_id = data[0].khachhang_ID;
				this.formThongTinKhachHang.tenKhachHang = data[0].ten_KH;
				this.formThongTinKhachHang.diaChiKH = data[0].diachi_KH;
				this.diachiKH = data[0].diachi_KH;
				this.formThongTinKhachHang.soGiayTo = data[0].so_GT;
				this.formThongTinKhachHang.gioiTinh = data[0].gioitinh;
				this.formThongTinKhachHang.ngaySinh = moment(
					data[0].ngay_SN,
					"YYYY-MM-DD HH:mm:ss"
				)
					.format("DD/MM/YYYY")
					.toString();
				this.formThongTinKhachHang.loaiGiayToId = data[0].loaigt_ID;
				this.kh_loaiGiayToId = data[0].loaigt_id;
				this.formThongTinKhachHang.quocTichId = data[0].quoctich_ID;
				this.formThongTinKhachHang.ngayCap = moment(
					data[0].ngaycap,
					"YYYY-MM-DD HH:mm:ss"
				)
					.format("DD/MM/YYYY")
					.toString();
				this.formThongTinKhachHang.dienThoai = data[0].so_DT;
				this.formThongTinKhachHang.soGiayTo = data[0].so_GT;
				this.formThongTinKhachHang.danTocId = data[0].dantoc_ID;
				this.formThongTinKhachHang.ngayHetHan = moment(
					data[0].ngayhethan_GT,
					"YYYY-MM-DD HH:mm:ss"
				)
					.format("DD/MM/YYYY")
					.toString();
				this.formThongTinKhachHang.gioiTinh = data[0].gioitinh;
				this.formThongTinKhachHang.nganhNgheId = data[0].nganhnghe_ID;
				this.formThongTinKhachHang.loaiKHId = data[0].loaikh_ID;
				// this.formThongTinKhachHang.loaiCapId
				// this.formThongTinKhachHang.diaChiKN;
				// this.formThongTinKhachHang.huongKN
				// this.formThongTinKhachHang.loaiNodeId
			}
			if (data.thuebao) {
				this.formThongTinKhachHang.chuQuanId = data.thuebao.chuquan;
				// this.formThongTinKhachHang.tocDoCirId
				// this.formThongTinKhachHang.soAo
				// this.formThongTinKhachHang.maTN
				// this.formThongTinKhachHang.tocDoPirId
				// this.formThongTinKhachHang.modemId
				this.formThongTinKhachHang.loaiThietBiId = data.thuebao.loaitb_id;
				// this.formThongTinKhachHang.trangBiId
				// this.formThongTinKhachHang.thoiHanId
			}
			if (htds) {
				this.HienThiDanhSachDBTB();
			}
		},
		async HienThiDanhSachDBTB() {
			let getDanhBaThueBaoParams = {
				vphanvung_id: this.$root.token.getPhanVungID(),
				in_khachhang_id: this.formThongTinKhachHang.khachhang_id,
			};
			// // // console.log("HienThiDanhSachDBTB");
			await api
				.lay_ds_dbtb_theo_khid_v2(this.axios, getDanhBaThueBaoParams)
				.then((response) => {
					if (response.data.data.length == 0) {
						this.formThongTinThueBao.maThueBao = "";
						this.formThongTinThueBao.tenThueBao = "";
						this.formThongTinThueBao.dichVuId = this.dichvuvt_id;
						this.$toast.info(
							"Khách hàng này không có thuê bao đối với dịch vụ viễn thông hiện tại "
						);
						return;
					}
					let dsDanhBaThueBaoDataSource_ChuaFilter = response.data.data
					let dsDanhBaThueBaoDataSource_DaFilter;
					if (this.ma_bh.toString() == "") {
						// Dành cho kênh trắng từ hệ thống KHDN HNI
						// trong project winform xử lý linq
						// sử dụng filter js để lọc tương tự
						// sử dụng ma_bh từ prop form update đẩy sang
						let filterDichVuVT_ID = this.formThongTinThueBao.dichVuId;
						dsDanhBaThueBaoDataSource_DaFilter = dsDanhBaThueBaoDataSource_ChuaFilter.filter(function (e) {
							return ( e.dichvuvt_ID.toString() === filterDichVuVT_ID.toString() );
						});
						this.dsDanhBaThueBaoDataSource = dsDanhBaThueBaoDataSource_DaFilter;
					} else {
						this.dsDanhBaThueBaoDataSource = dsDanhBaThueBaoDataSource_ChuaFilter;
					}
				})
				.finally( () => {
					this.isDoneMounted = true;
				});
				
		},
		async tsbtnCapNhan_Click() {
			if (
				this.danhBaThueBaoSelectedRow == null ||
				this.danhBaThueBaoSelectedRow == undefined
			) {
				// Message_Box.ShowTB("Bạn chưa chọn thuê bao để map dữ liệu.");
				// return;
				this.$toast.error("Bạn chưa chọn thuê bao để map dữ liệu.");
				return;
			}
			let chapnhan = true;
			/*luu log đã ghép cho thuê bao kênh trắng*/
			try {
				let phanVungID = this.$root.token.getPhanVungID();
				let vkieu = -1;
				let vid_hni = 0;
				if (Number(this.hdtb_id_hni) !== 0) {
					vkieu = 1;
					vid_hni = this.hdtb_id_hni;
				} else {
					vkieu = 2;
					vid_hni = this.baohong_id_hni;
				}
				const itemInfoStr = localStorage.getItem("OneBSS-Client");
				let log_ghep_khdn_thong_tin_kenh_trang_Params = {
					vphanvung_id: phanVungID,
					vma_tb: this.danhBaThueBaoSelectedRow.ma_tb,
					vid_hni: vid_hni,
					vthuebao_id: this.formThongTinThueBao.thueBaoId,
					vnguoi_cn: JSON.stringify(itemInfoStr).ma_dangnhap,
					vmay_cn: "",
					vma_vnpt: this.ma_vnpt !== null ? this.ma_vnpt : "",
					vkieu: vkieu,
				};
				await api
					.log_ghep_khdn_thong_tin_kenh_trang(
						this.axios,
						JSON.stringify(log_ghep_khdn_thong_tin_kenh_trang_Params)
					)
					.then((response) => {
						// // console.log(response.data.error_code && (response.data.error_code === "BSS-00000000"));
						if (
							(response.data.error_code)
							&&
							(response.data.error_code === "BSS-00000000")
						) {
							this.$toast.success("Log ghép thông tin kênh trắng KHDN HNI thành công");
							this.$emit('accept',{
								ma_tb:this.formThongTinThueBao.maThueBao ,
								thuebao_id:this.formThongTinThueBao.thueBaoId
							})
						} else {
							this.$toast.error("Có lỗi xảy ra khi log ghép " + response.data.message);
						}
					});
			} catch (exception) {
				//Message_Box.ShowTB("Thực hiện lưu log không thành công!");
				this.$toast.error("Thực hiện lưu log không thành công!");
				
				// // console.log("Log ghép thông tin kênh trắng KHDN HNI: " + exception);
			}
		},
		tsbtnXoaPhieu_Click() { },
		async txtMaKH_KeyPress() {
			
			let dataKhachHang = [];
			let maKhParma = this.formThongTinKhachHang.maKH;
			let self=this
			await api
				.lay_thongtin_kh_theo_ma_kh(
					this.axios,
					JSON.stringify({
						vphanvung_id: this.$root.token.getPhanVungID(),
						vma_kh: maKhParma,
					})
				)
				.then((ret) => {
					if (ret.data.data && ret.data.data.length > 0) {
						dataKhachHang = ret.data.data;
						this.HienThiTT_DBKH(dataKhachHang, true);
						return;
					}
					
					if (dataKhachHang.length == 0 || dataKhachHang === null) {
						api
							.lay_thongtin_kh_theo_ma_tt(
								this.axios,
								JSON.stringify({
									vphanvung_id: this.$root.token.getPhanVungID(),
									vma_tt: maKhParma,
								})
							)
							.then((ret) => {
								if (ret.data.data && ret.data.data.length > 0) {
									dataKhachHang = ret.data.data;
									this.HienThiTT_DBKH(dataKhachHang, true);
									return;
								}
								
								if (dataKhachHang.length == 0 || dataKhachHang === null) {
									api
										.lay_thongtin_kh_theo_ma_tb(
											this.axios,
											JSON.stringify({
												vphanvung_id: this.$root.token.getPhanVungID(),
												vma_tb: maKhParma,
											})
										)
										.then((ret) => {
											
											if (ret.data.data  && ret.data.data.length > 0) {
												dataKhachHang = ret.data.data;
												this.HienThiTT_DBKH(dataKhachHang, true);
												return;
											}
											
											if (dataKhachHang.length == 0 || dataKhachHang === null) {
												api.lay_thongtin_kh_theo_soao(
														this.axios,
														JSON.stringify({
															vphanvung_id: this.$root.token.getPhanVungID(),
															vma_soao: maKhParma,
														})
													)
													.then((ret) => {
														if (ret.data.data && ret.data.data.length > 0) {
															dataKhachHang = ret.data.data;
															this.HienThiTT_DBKH(dataKhachHang, true);
															return;
														}
														if (dataKhachHang.length == 0 || dataKhachHang === null) {
															api
																.lay_thongtin_kh_theo_ma_tb_neo(
																	this.axios,
																	JSON.stringify({
																		vphanvung_id:
																			this.$root.token.getPhanVungID(),
																		vma_tt_neo: maKhParma,
																	})
																)
																.then((ret) => {
																	
																	if (ret.data.data && ret.data.data.length > 0) {
																		dataKhachHang = ret.data.data;
																		this.HienThiTT_DBKH(dataKhachHang, true);
																		return;
																	}
																});
														}
													});
											}
										});
								}
							});
					}
				})
				.finally(function() { 
					if (dataKhachHang.length === 0) {
						//this.Clear();
						self.$toast.info(
							"Không tìm thấy thông tin khách hàng của mã KH : " +
							maKhParma
						);
						self.SetButton(-1);
					}
				});
			this.tsbtnCapNhatDoThu.Enabled = true;
			this.tsbtnNhapMoi.Enabled = false;
			
		},
		gridviewThueBao_rowSelected(args) {
			console.log('gridviewThueBao_rowSelected', args)
		},
		gridviewThueBao_FocusedRowChanged(obj) {
			// Convert ham gridviewThueBao_FocusedRowChanged
			
			if (!obj) {
				return;
			} else {
				
				console.log('gridviewThueBao_FocusedRowChanged', obj)
				this.danhBaThueBaoSelectedRow = obj;
				// Đoạn này đã thay cho sử dụng hàm HienThiTT_DBTB
				this.formThongTinThueBao.maThueBao = obj.ma_TB;
				
				this.formThongTinThueBao.loaiHinhId = parseInt(obj.loaitb_ID);
				this.formThongTinThueBao.dichVuId = parseInt(obj.dichvuvt_ID);
				//this.dichvuvt_id = obj.dichvuvt_ID;
				this.formThongTinThueBao.thueBaoId = obj.thuebao_ID;
				this.formThongTinThueBao.tenThueBao = obj.ten_TB;
				this._maLT = obj.ma_LT;
				this.trangthaitb_id = obj.trangthaitb_ID;
				this._trangthaitb_id = obj.trangthaitb_ID;
				this.formThongTinThueBao.diaChiLD = obj.diachi_LD;
				this.formThongTinThueBao.doiTuongId = obj.doituong_ID.toString();
				this.formThongTinThueBao.thanhtoan_id = obj.thanhtoan_ID;
				if (
					obj.dichvuvt_ID == ThongTinKenhTrangEnums.DichVuVienThong.MEGAWAN ||
					obj.dichvuvt_ID == ThongTinKenhTrangEnums.DichVuVienThong.METRONET
				) {
					this.formThongTinKhachHang.accountId = obj.ma_tb;
				} else if (
					obj.dichvuvt_ID == ThongTinKenhTrangEnums.DichVuVienThong.TSL
				) {
					this.formThongTinThueBao.maTSL = obj.ma_tb;
					this.formThongTinThueBao.TSL.accountId = obj.ma_tb;
				}
				let ngaySN_Obj = moment(obj.ngay_SN, "YYYY-MM-DD HH:mm:ss")
					.format("MM/DD/YYYY")
					.toString();
				this.formThongTinKhachHang.ngaySinh = ngaySN_Obj.toString();
				this.formThongTinThueBao.diaChiThueBao = obj.diachi_TB;
				this.formThongTinThueBao.ghiChu = obj.ghichu;
				// Đoạn này đã thay cho sử dụng hàm HienThiTT_DBTB
				this.SetButton(5);
			}
			
		},
		Clear() {
			
			//khách hàng
			//txtMaKH.Text = "";
			this.formThongTinKhachHang.maKH = "";
			// dtpNgayYeuCau.Value = DateTime.Now; -- Khong dung
			// txtTenKH.Text = "";
			this.formThongTinKhachHang.tenKhachHang = "";
			// txtDiaChiKH.Text = "";
			this.formThongTinKhachHang.diaChiKH = "";
			//cboGiayToKH.Text = "";
			this.formThongTinKhachHang.loaiGiayToId = "";
			//txtSoGT.Text = "";
			this.formThongTinKhachHang.soGiayTo = "";
			//txtNgayCap.Text = "";
			this.formThongTinKhachHang.ngayCap = "";
			//txtNoiCap.Text = ""; -- Khong dung
			//txtNgayHetHan.Text = "";
			this.formThongTinKhachHang.ngayHetHan = "";
			//txtNgaySN.Text = "";
			this.formThongTinKhachHang.ngaySinh = "";
			//cboQuocTich.SelectedIndex = 1;
			this.formThongTinKhachHang.quocTichId = null;
			//cboNganhNgheLoc.EditValue = cboNganhNgheLoc.Properties.GetKeyValue(0);
			// this.listbox.nganhngheloc.value =
			//     this.listbox.nganhngheloc.list[0].NGANHNGHE_ID;
			// cboDanToc.SelectedIndex = 1;
			// this.formThongTinKhachHang.danTocId =
			//     this.formThongTinKhachHang.danTocOptions[0].DANTOC_ID;
			this.formThongTinKhachHang.danTocId = null;
			this.formThongTinKhachHang.gioiTinh = null;
			// txtNguoiDD.Text = ""; => Khong dung
			// txtChucDanhDD.Text = ""; => Khong dung
			// cboLoaiKH.EditValue = null;
			// 
			//     this.formThongTinKhachHang.loaiKhachHangOptions[0].loaikh_id;
			this.formThongTinThueBao.loaiHinhId = null;
			//cboKhachHangLon.SelectedIndex = 1;
			//txtTelKH.Text = "";
			this.formThongTinKhachHang.dienThoai = "";
			if (this.ma_vnpt != null || this.ma_vnpt != "") {
				this.formThongTinKhachHang.accountId = this.ma_vnpt;
				this.formThongTinThueBao.TSL.accountId = this.ma_vnpt;
			}
			//txtDiaChiTB.Text = txtDiaChiLD.Text = txtDiaChiDau.Text = diachi_tb;
			this.formThongTinThueBao.diaChiThueBao = "";
			//txtFaxKH.Text = "";
			//txtHoKhau.Text = "";
			//txtNoiCapHK.Text = "";
			//txtNgayCapHK.Text = "";
			//cboNganHangKH.Text = "";
			//txtSoTKKH.Text = "";
			//txtMaSTKH.Text = "";
			//txtDacTrung.Text = "";
			//txtGhiChuKH.Text = "";
			//thuê bao
			//cboDichVuVT.SelectedValue = dichvuvt_id;
			// this.formThongTinThueBao.dichVuId = this.dichVuOptions[0].DICHVUVT_ID;
			//cboLoaihinhTB.SelectedIndex = 1;
			this.formThongTinThueBao.loaiHinhId = null;
			//txtMaTB.Text = "";
			// txtNgaySNTB.Text = "";
			this.formThongTinThueBao.ngaySinh = "";
			//txtTenTB.Text = "";
			this.formThongTinThueBao.tenThueBao = "";
			//txtDiaChiTB.Text = "";
			this.formThongTinThueBao.diaChiThueBao = "";
			//txtDiaChiLD.Text = "";
			this.formThongTinThueBao.diaChiLD = "";
			// dnah sách thuê bao
			//gridThueBao.DataSource = null;
			this.dsDanhBaThueBaoDataSource = [];
		},
		tsbtnHuyBo_Click() {
			this.SetButton(0);
		},
		tsbtnNhapMoi_Click() {
			this.SetButton(1);
		},
		HienThiTT_DBTB(ds, htds) {
			if (ds !== null && ds.length > 0) {
				this.formThongTinThueBao.tenThueBao = ds[0].ten_tb;
				this.formThongTinThueBao.diaChiTB = ds[0].diachi_tb;
				this.formThongTinThueBao.diaChiLD = ds[0].diachi_ld;
				this.formThongTinThueBao.ngaySinh = ds[0].ngay_sn;
				this.formThongTinThueBao.dichVuId = ds[0].dichvuvt_id;
				this.formThongTinThueBao.loaiHinhId = ds[0].loaitb_id;
			}
		},
		async CAPNHAT_THUEBAO_ID_DBTB_KV() {
			if (this.dsHDTB_KV && this.dsHDTB_KV.length > 0) {
				for (var i = 0; i < this.dsHDTB_KV.length; i++) {
					this.dsHDTB_KV[i]["THUEBAO_ID"] = this.formThongTinThueBao.thueBaoId;
				}
			}
		},
		async CapNhat() {
			// try {
			console.log("1")
			let _KiemTraDL_KhachHang = false;
			await this.KiemTraDL_KhachHang().then(result => {
				_KiemTraDL_KhachHang = result;
			});
			if (_KiemTraDL_KhachHang == false) return;
			let _KiemTraDL_ThueBao = false;
			await this.KiemTraDL_ThueBao().then(result => {
				_KiemTraDL_ThueBao = result;
			});
			if (_KiemTraDL_ThueBao == false) return;
			let maTBCurrent = "";
			if (
				this.formThongTinThueBao.dichVuId ==
				ThongTinKenhTrangEnums.DichVuVienThong.MEGAWAN ||
				this.formThongTinThueBao.dichVuId ==
				ThongTinKenhTrangEnums.DichVuVienThong.METRONET
			) {
				maTBCurrent = this.formThongTinKhachHang.accountId;
			} else if (
				this.formThongTinThueBao.dichVuId ==
				ThongTinKenhTrangEnums.DichVuVienThong.TSL
			) {
				maTBCurrent = this.formThongTinThueBao.maTSL;
			}
			// txtMaTBWan.Text = txtMaTSL.Text = Common.Utilities.CommonFunction.clearTheSpace(((dichvuvt_id == DichVuVienThong.MEGAWAN || dichvuvt_id == DichVuVienThong.METRONET) ? txtMaTBWan.Text : txtMaTSL.Text));
			// if (Convert.ToInt32(cboDichVuVT.SelectedValue) == DichVuVienThong.ADSL)
			//        if (!KiemTraDL_TB_ADSL()) return;
			if (this.formThongTinKhachHang.diachikh_id == 0) this.themdc = true;
			else this.themdc = false;	
			if (!this.tsbtnNhapMoi.Enabled) {
				await this.TaoDuLieu(true);
			} else {
				await this.TaoDuLieu(false);
			}
			let capNhatThongTinKenhTrang = {};
			// capNhatThongTinKenhTrang["dsdb_mgwan"] = JSON.stringify(this.convertKeysToLowerCase(this.dsDB_MGWAN, "dsDB_MGWAN"));
			capNhatThongTinKenhTrang["dsdb_mgwan"] = JSON.stringify(this.dsDB_MGWAN);
			// console.log("convert done dsDB_MGWAN");
			capNhatThongTinKenhTrang["dsdb_tsl"] = JSON.stringify(this.dsDB_TSL);
			// console.log("convert done dsDB_TSL");
			capNhatThongTinKenhTrang["dsdbkh"] = JSON.stringify(this.dsDBKH);
			// console.log("convert done dSDBTT");
			capNhatThongTinKenhTrang["dsdbtb"] = JSON.stringify(this.dSDBTB);
			 console.log("convert done DSDBTB");
			capNhatThongTinKenhTrang["dsdbtt"] = JSON.stringify(this.dsDBTT);
			// console.log("convert done dSDBTT");
			
			capNhatThongTinKenhTrang["dsdiachi"] = JSON.stringify(this.dsDiaChi);
			capNhatThongTinKenhTrang["dsdiachidbkh"] = JSON.stringify(this.dsDiaChiHDKH);
			capNhatThongTinKenhTrang["dsdiachidbtb"] = JSON.stringify(this.dsDiaChiHDTB);
			capNhatThongTinKenhTrang["dsdiachidbtt"] = JSON.stringify(this.dsDiaChiHDTT);
			capNhatThongTinKenhTrang["dshdtb_kv"] = JSON.stringify(this.dsHDTB_KV);
			// // console.log("capNhatThongTinKenhTrang");
			
			let capNhatParams = new Object();
			let vdsParams = new Object();
			vdsParams.NHAPMOI = this.tsbtnNhapMoi.Enable == true ? 1 : 0;
			vdsParams.DICHVUVT_ID = this.formThongTinThueBao.dichVuId;
			vdsParams.MATBWAN = this.formThongTinKhachHang.accountId;
			vdsParams.MATSL = this.formThongTinThueBao.TSL.accountId;
			vdsParams.LOAIHINHTB = this.formThongTinThueBao.loaiHinhId;
			vdsParams.THEMDC = this.themdc;
			if (
				this.formThongTinThueBao.dichVuId === ThongTinKenhTrangEnums.DichVuVienThong.MEGAWAN
			)
                {
                    vdsParams.PHOIS_ID = this.checkIsNull(this.phold_id, true) != true ? this.phold_id : this.diaChiLD.PHO_ID ;
                    vdsParams.PHUONGIS_ID = this.checkIsNull(this.phuongld_id, true) != true ? this.phuongld_id : this.diaChiLD.PHUONG_ID;
                    vdsParams.QUANIS_ID = this.checkIsNull(this.quanld_id, true) != true ? this.quanld_id : this.diaChiLD.QUAN_ID;
                    vdsParams.SONHAIS = this.checkIsNull(this.sonhald, true) != true ? this.sonhald : this.diaChiLD.SONHA;
                    vdsParams.TINHIS_ID = this.checkIsNull(this.tinhld_id, true) != true ? this.tinhld_id : this.diaChiLD.TINH_ID;
                    vdsParams.APIS_ID = this.checkIsNull(this.apld_id, true) != true ? this.apld_id : this.diaChiLD.AP_ID;
                    vdsParams.KHUIS_ID = this.checkIsNull(this.khuld_id, true) != true ? this.khuld_id : this.diaChiLD.KHU_ID;
                    vdsParams.DACDIEMIS_ID = this.checkIsNull(this.dacdiemld_id, true) != true ? this.dacdiemld_id : this.diaChiLD.DACDIEM_ID;
                }
                else if (
					this.formThongTinThueBao.dichVuId === ThongTinKenhTrangEnums.DichVuVienThong.TSL
				)
                {
                    vdsParams.PHOIS_ID = this.checkIsNull(this.phodau_id) != true ? this.phodau_id : this.diaChiLD_TSL_Dau.PHO_ID ;
                    vdsParams.PHUONGIS_ID = this.checkIsNull(this.phuongdau_id) != true ? this.phuongdau_id : this.diaChiLD_TSL_Dau.PHUONG_ID;
                    vdsParams.QUANIS_ID = this.checkIsNull(this.quandau_id) != true ? this.quandau_id : this.diaChiLD_TSL_Dau.QUAN_ID;
                    vdsParams.SONHAIS = this.checkIsNull(this.sonhadau) != true ? this.sonhadau : this.diaChiLD_TSL_Dau.SONHA;
                    vdsParams.TINHIS_ID = this.checkIsNull(this.tinhdau_id) != true ? this.tinhdau_id : this.diaChiLD_TSL_Dau.TINH_ID;
                    vdsParams.APIS_ID = this.checkIsNull(this.apdau_id) != true ? this.apdau_id : this.diaChiLD_TSL_Dau.AP_ID;
                    vdsParams.KHUIS_ID = this.checkIsNull(this.khudau_id) != true ? this.khudau_id : this.diaChiLD_TSL_Dau.KHU_ID;
                    vdsParams.DACDIEMIS_ID = this.checkIsNull(this.dacdiemdau_id) != true ? this.dacdiemdau_id : this.diaChiLD_TSL_Dau.DACDIEM_ID;
                }
			capNhatThongTinKenhTrang["vds"] = JSON.stringify(vdsParams);
			capNhatThongTinKenhTrang["phanvung_id"] = this.$root.token.getPhanVungID();
			console.log("******************************************************************************");
			console.log(JSON.stringify(capNhatThongTinKenhTrang));
			var result='0'
			await api.fn_capnhat_tt_kenhtrang(
					this.axios,
					JSON.stringify(capNhatThongTinKenhTrang)
			)
			.then((response) => {
				if (
					response.data.error_code &&
					(response.data.error_code === "BSS-00000000")
				) {
					result=response.data.data
					if(response.data.data=='1'){
						this.$root.toastSuccess('Cập nhật dữ liệu thành công');
						
					}else{
						this.$root.toastError(response.data.data);
					}
					
				} else {
					this.$root.toastError(response.data.message);
				}
			});
			if(result=='1'){
				await this.HienThiDanhSachDBTB()
				this.SetButton(6);
			}
		},
		tsbtnCapNhatDoThu_Click() {
			console.log("2")
			if (this.diachiKH.PHUONG_ID && Number(this.diachiKH.PHUONG_ID) == 0) {
				//Message_Box.ShowTB("Hãy chọn lại địa chỉ khách hàng chính xác");
				this.$root.toastError("Hãy chọn lại địa chỉ khách hàng chính xác");
				return;
			}
			if (this.diachiLD.PHUONG_ID && Number(this.diachiLD.PHUONG_ID) == 0) {
				this.$root.toastError(
					"Hãy chọn lại địa chỉ lắp đặt/địa chỉ thuê bao chính xác"
				);
				return;
			}
			this.CapNhat();
		},
		btnHuongKNWan_Click() {
			// console.log("btnHuongKNWan_Click");
			this.duLieuPopupHuongKetNoi = {
				kieu: this.formThongTinThueBao.chuQuanId == 9 ? 0 : 1,
				dichvuvt_id: this.formThongTinThueBao.dichvuvt_ID,
				tinhkn_id: this.diachiLD.TINH_ID,
			}; // O day dung enums CHUQUAN.VNPT_TINH nhung chi co 1 gia tri duy nhat = 9 nen loai bo enums luon!
		},
		btnSoAoWan_Click() {
			if (
				!this.diachiLD.QUAN_ID ||
				this.diachiLD.QUAN_ID == 0 ||
				!this.diachiLD.PHUONG_ID ||
				this.diachiLD.PHUONG_ID == 0
			) {
				this.$toast.error("Bạn chưa chọn địa chỉ lắp đặt !");
				return;
			}
			this.popupComponent = () =>
				import("../../InstallNewSubs/Popups/popupChonSM.vue");
			this.popupComponentName = "frmChonSM_WAN";
			this.popupComponentData = {
				_quan_id: this.diachiLD.QUAN_ID,
				_phuong_id: this.diachiLD.PHUONG_ID,
				_loaiso_id: ThongTinKenhTrangEnums.LOAISO.AO,
				_thueso: false,
				loaitb_id: this.formThongTinThueBao.loaiHinhId,
				dichvuvt_id: this.formThongTinThueBao.dichVuId,
				donvi_ql_id: this.donvi_ql_id, //ql (lay field don vi lap dat.)
			};
			this.Popup("popupComponents");
		},
		btnSoAoTSL_Dau_Click() {
			if (
				this.formThongTinThueBao.dichVuId !=
				ThongTinKenhTrangEnums.DichVuVienThong.CO_DINH
			) {
				if (
					this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.QUAN_ID == 0 ||
					this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.PHUONG_ID == 0
				) {
					this.$toast.error("Bạn chưa chọn địa chỉ điểm đầu !");
					//txtDiaChiDau.Focus();
					return;
				}
				this.popupComponent = () =>
					import("../../InstallNewSubs/Popups/popupChonSM.vue");
				this.popupComponentName = "frmChonSM_TSL_Dau";
				this.popupComponentData = {
					_quan_id: this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.QUAN_ID,
					_phuong_id: this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.PHUONG_ID,
					_loaiso_id: ThongTinKenhTrangEnums.LOAISO.AO,
					_thueso: false,
					loaitb_id: this.formThongTinThueBao.loaiHinhId,
					dichvuvt_id: this.formThongTinThueBao.dichVuId,
					donvi_ql_id: this.donvi_dau_id, //ql (lay field don vi lap dat.)
				};
				this.Popup("popupComponents");
			}
		},
		btnSoAoTSL_Cuoi_Click() {
			if (
				this.formThongTinThueBao.dichVuId !=
				ThongTinKenhTrangEnums.DichVuVienThong.CO_DINH
			) {
				if (
					this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.QUAN_ID == 0 ||
					this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.PHUONG_ID == 0
				) {
					this.$toast.error("Bạn chưa chọn địa chỉ điểm cuối !");
					//txtDiaChiDau.Focus();
					return;
				}
				this.popupComponent = () =>
					import("../../InstallNewSubs/Popups/popupChonSM.vue");
				this.popupComponentName = "frmChonSM_TSL_Cuoi";
				this.popupComponentData = {
					_quan_id: this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.QUAN_ID,
					_phuong_id: this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.PHUONG_ID,
					_loaiso_id: ThongTinKenhTrangEnums.LOAISO.AO,
					_thueso: false,
					loaitb_id: this.formThongTinThueBao.loaiHinhId,
					dichvuvt_id: this.formThongTinThueBao.dichVuId,
					donvi_ql_id: this.donvi_cuoi_id, //ql (lay field don vi lap dat.)
				};
				this.Popup("popupComponents");
			}
		},
		changeTocDoPIR() {
			this.disabledPIR = !this.disabledPIR;
		},
		convertKeysToLowerCase(upperCased, name) {
			
			if (!(upperCased === null) && !(typeof  upperCased === 'undefined')) {
				let keyCount = Object.keys(upperCased).length;
				if (keyCount > 0) {
					var lowerCased = upperCased.map(function (item) {
						var mapped = {};
						for (var key in item) {
							let keyModified = key.toLowerCase();
							if (keyModified.toString() === "ten_dv") {
								keyModified = "name";
								// // console.log(key);
							}
							if (keyModified.toString() === "donvi_id") {
								keyModified = "id";
								// // console.log(key);
							}
							mapped[keyModified] = item[key];
						}
						return mapped;
					});
					return lowerCased;
				} else {
					return upperCased;
				}
			} else {
				return [];
			}
		},
		async popupChonDonViQuanLyTSL(dau_cuoi_type, donviql_ID_Index, ds) {
			let dsLowerCase = this.convertKeysToLowerCase(ds);
			let resolvePromise = await this.$refs.popupChonDonViQuanLyTSL.show(
				null,
				null,
				donviql_ID_Index,
				null,
				dsLowerCase
			);
			if (Number(dau_cuoi_type) == 1) {
				if (resolvePromise.ok)
					this.donvi_dau_id = resolvePromise.data.donviql_id;
			} else if (Number(dau_cuoi_type) == 2) {
				if (resolvePromise.ok)
					this.donvi_cuoi_id = resolvePromise.data.donviql_id;
			}
		},
		btnDiaChiKH_Click() {
			// if (phuongtb_id == 0)
            // {
            this.tinhtb_id = this.tinhkh_id;
            this.quantb_id = this.quankh_id;
            this.phuongtb_id = this.phuongkh_id;
            this.dacdiemtb_id = this.dacdiemkh_id;
            this.photb_id = this.phokh_id;
            this.aptb_id = this.apkh_id;
            this.sonhatb = this.sonhakh;
           
            //}
            //if (phuongld_id==0)
            //  {
            if (this.formThongTinThueBao.dichVuId === ThongTinKenhTrangEnums.DichVuVienThong.TSL)
            {
                this.tinhdau_id = this.tinhkh_id;
                this.quandau_id = this.quankh_id;
                this.phuongdau_id = this.phuongkh_id;
                this.dacdiemdau_id = this.dacdiemkh_id;
                this.phodau_id = this.phokh_id;
                this.apdau_id = this.apkh_id;
                this.sonhadau = this.sonhakh;
                this.formThongTinThueBao.diaChiLD = this.formThongTinThueBao.diaChiKH;
                //---
                this.tinhcuoi_id = this.tinhkh_id;
                this.quancuoi_id = this.quankh_id;
                this.phuongcuoi_id = this.phuongkh_id;
                this.dacdiemcuoi_id = this.dacdiemkh_id;
                this.phocuoi_id = this.phokh_id;
                this.apcuoi_id = this.apkh_id;
                this.sonhacuoi = this.sonhakh;
                this.formThongTinThueBao.diaChiLD = this.formThongTinThueBao.diaChiKH;
                //---
                this.tinhld_id = this.tinhkh_id;
                this.quanld_id = this.quankh_id;
                this.phuongld_id = this.phuongkh_id;
                this.dacdiemld_id = this.dacdiemkh_id;
                this.phold_id = this.phokh_id;
                this.apld_id = this.apkh_id;
                this.sonhald = this.sonhakh;
                this.formThongTinThueBao.diaChiLD = this.formThongTinThueBao.diaChiKH;
            }
            else if (this.formThongTinThueBao.dichVuId === ThongTinKenhTrangEnums.DichVuVienThong.MEGAWAN)
            {
                this.tinhdau_id = this.tinhkh_id;
                this.quandau_id = this.quankh_id;
                this.phuongdau_id = this.phuongkh_id;
                this.dacdiemdau_id = this.dacdiemkh_id;
                this.phodau_id = this.phokh_id;
                this.apdau_id = this.apkh_id;
                this.sonhadau = this.sonhakh;
                this.formThongTinThueBao.diaChiLD = this.formThongTinThueBao.diaChiKH;
                //---
                this.tinhld_id = this.tinhkh_id;
                this.quanld_id = this.quankh_id;
                this.phuongld_id = this.phuongkh_id;
                this.dacdiemld_id = this.dacdiemkh_id;
                this.phold_id = this.phokh_id;
                this.apld_id = this.apkh_id;
                this.sonhald = this.sonhakh;
                this.formThongTinThueBao.diaChiLD = this.formThongTinThueBao.diaChiKH;
            }
            // }
		},
		btnDiaChiLD_Click() {
			
            this.tinh_id = tinhld_id;
            this.quan_id = quanld_id;
            this.phuong_id = phuongld_id;
            this.dacdiem_id = dacdiemld_id;
            this.pho_id = phold_id;
            this.ap_id = apld_id;
            this.khu_id = khuld_id;
            this.sonha = sonhald;
            this.diachi = txtDiaChiLD.Text;
            this.lattitude = Lat_LD;
            this.longtitude = Lng_LD;
            this.ketcuoi_id = -1;
            
            if (obj.chapnhan)
            {
                tinhld_id = obj.tinh_id;
                quanld_id = obj.quan_id;
                phuongld_id = obj.phuong_id;
                dacdiemld_id = obj.dacdiem_id;
                phold_id = obj.pho_id;
                apld_id = obj.ap_id;
                khuld_id = obj.khu_id;
                sonhald = obj.sonha;
                txtDiaChiLD.Text = obj.diachi;
                txtDiaChiTB.Text = obj.diachi;
                Lat_LD = obj.lattitude;
                Lng_LD = obj.longtitude;
                if (obj.ketcuoi_id != -1)
                {
                    KetCuoi_ID = obj.ketcuoi_id;
                    //Nhanvien_id_kc = Convert.ToInt32(objCheckData.MAP_ID("nhanvien_id", DatabaseConstants.DB5 + ".ql_ketcuoi", "Where ketcuoi_id=" + KetCuoi_ID));
                }
            }
		},
		checkIsNull(variable, checkIsZero) {
			if (variable === null && typeof variable === "object" ) {
				if (checkIsZero === true) {
					return variable === 0;
				} else {
					return true;
				}
				
			} else {
				return false;
			}
		}
		,
		btnChonDCDau_Click() {
			// // console.log("btnChonDCDau_Click");
			this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.tinh_id = this.tinhdau_id;
            this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.quan_id = this.quandau_id;
            this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.phuong_id = this.phuongdau_id;
            this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.pho_id = this.phodau_id;
            this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.ap_id = this.adaui_id;
            this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.khu_id = this.khudau_id;
            this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.sonha = this.sonhadau;
            this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.lattitude = this.Lat_DC;
            this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.longtitude = this.Lng_DC;
            // this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.diachi = txtDiaChiCuoi.Text;
            // this.formThongTinThueBao.TSL.diaChiLD_TSL_Dau.ShowDialog();
			// Chi convert den day de dua du lieu vao modal chon dia chi va show
			// this.$refs.popupDiaChiLD_TSL_Dau.showModal();
			this.$bvModal.show('popupDiaChiLD_TSL_Dau');
		},
		btnChonDCCuoi_Click() {
			// // console.log("btnChonDCCuoi_Click");
			this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.tinh_id = this.tinhcuoi_id;
            this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.quan_id = this.quancuoi_id;
            this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.phuong_id = this.phuongcuoi_id;
            this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.pho_id = this.phocuoi_id;
            this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.ap_id = this.apcuoi_id;
            this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.khu_id = this.khucuoi_id;
            this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.sonha = this.sonhacuoi;
            this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.lattitude = this.Lat_DC;
            this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.longtitude = this.Lng_DC;
			
            // this.formThongTinThueBao.TSL.diaChiLD_TSL_Cuoi.diachi = txtDiaChiCuoi.Text;
			// Chi convert den day de dua du lieu vao modal chon dia chi va show
    		// Ben duoi da co modal callback accept khong phai convert
			// this.$refs.popupDiaChiLD_TSL_Cuoi.showModal();
			this.$bvModal.show('popupDiaChiLD_TSL_Cuoi');
		},
		btnMaTN_Wan_Click() {
			var dulieuMaTN_Wan = {};
			 if(this.formThongTinKhachHang.khachhang_id && this.formThongTinKhachHang.khachhang_id>0) {
				dulieuMaTN_Wan.khachhang_id = this.formThongTinKhachHang.khachhang_id;
				dulieuMaTN_Wan.loaitb_id = this.formThongTinThueBao.loaiHinhId;
				dulieuMaTN_Wan.dichvuvt_id = this.formThongTinThueBao.dichVuId;
				let kieuLdId = "";
				if ( (this.formThongTinThueBao.dichVuId.toString() === ThongTinKenhTrangEnums.DichVuVienThong.MEGAWAN.toString())) {
					kieuLdId = ThongTinKenhTrangEnums.KieuLapDat.DM_MGW_DOCLAP;
				} else 
				if ( (this.formThongTinThueBao.dichVuId.toString() === ThongTinKenhTrangEnums.DichVuVienThong.TSL.toString())) {
					kieuLdId = ThongTinKenhTrangEnums.KieuLapDat.DM_TSL_LUONG;
				}
				dulieuMaTN_Wan.kieuld_id = kieuLdId;
				console.log("btnMaTN_Wan_Click***********************-----\n" + JSON.stringify(dulieuMaTN_Wan));
				this.popupComponentData = dulieuMaTN_Wan;
				this.popupComponent = ()=>import('../../InstallNewSubs/Popups/popupDSTBCungDoiCap');
				this.popupComponentName = "popupDSTBCungDoiCap";
				this.Popup("popupComponents");
			} else {
				this.$toast.error("Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp trên đường có sẵn !");
				return;
			}
		}
		/*
			Trong project C# ham nay khong duoc tham chieu
			=> Ko dung => Khong can convert
		*/
		// private void HienThiTTDiaChi(DataSet ds, int kieu)
        // {
        //     if (ds.Tables[0].Rows.Count > 0)
        //     {
        //         if (kieu == 1)
        //         {
        //             diachikh_id = Convert.ToInt64(ds.Tables[0].Rows[0]["diachi_id"]);
        //             tinhkh_id = Convert.ToInt32(ds.Tables[0].Rows[0]["tinh_id"]);
        //             quankh_id = Convert.ToInt32(ds.Tables[0].Rows[0]["quan_id"]);
        //             phuongkh_id = Convert.ToInt32(ds.Tables[0].Rows[0]["phuong_id"]);
        //             if (ds.Tables[0].Rows[0]["pho_id"].ToString() != "")
        //                 phokh_id = Convert.ToInt32(ds.Tables[0].Rows[0]["pho_id"]);
        //             else
        //                 phokh_id = 0;
        //             if (ds.Tables[0].Rows[0]["ap_id"].ToString() != "")
        //                 apkh_id = Convert.ToInt32(ds.Tables[0].Rows[0]["ap_id"]);
        //             else
        //                 apkh_id = 0;
        //             if (ds.Tables[0].Rows[0]["khu_id"].ToString() != "")
        //                 khukh_id = Convert.ToInt32(ds.Tables[0].Rows[0]["khu_id"]);
        //             else
        //                 khukh_id = 0;
        //             if (ds.Tables[0].Rows[0]["dacdiem_id"].ToString() != "")
        //                 dacdiemkh_id = Convert.ToInt32(ds.Tables[0].Rows[0]["dacdiem_id"]);
        //             else
        //                 dacdiemkh_id = 0;
        //             sonhakh = CommonFunction.ChuanHoaTen(ds.Tables[0].Rows[0]["sonha"].ToString());
        //             if (ds.Tables[0].Rows[0]["kinhdo"].ToString().Trim() != "")
        //                 Lat_KH = Convert.ToDouble(ds.Tables[0].Rows[0]["kinhdo"]);
        //             else
        //                 Lat_KH = 0;
        //             if (ds.Tables[0].Rows[0]["vido"].ToString().Trim() != "")
        //                 Lng_KH = Convert.ToDouble(ds.Tables[0].Rows[0]["vido"]);
        //             else
        //                 Lng_KH = 0;
        //         }
        //         if (kieu == 2)
        //         {
        //             diachitt_id = Convert.ToInt64(ds.Tables[0].Rows[0]["diachi_id"]);
        //             tinhtt_id = Convert.ToInt32(ds.Tables[0].Rows[0]["tinh_id"]);
        //             quantt_id = Convert.ToInt32(ds.Tables[0].Rows[0]["quan_id"]);
        //             phuongtt_id = Convert.ToInt32(ds.Tables[0].Rows[0]["phuong_id"]);
        //             if (ds.Tables[0].Rows[0]["pho_id"].ToString() != "")
        //                 phott_id = Convert.ToInt32(ds.Tables[0].Rows[0]["pho_id"]);
        //             else
        //                 phott_id = 0;
        //             if (ds.Tables[0].Rows[0]["ap_id"].ToString() != "")
        //                 aptt_id = Convert.ToInt32(ds.Tables[0].Rows[0]["ap_id"]);
        //             else
        //                 aptt_id = 0;
        //             if (ds.Tables[0].Rows[0]["khu_id"].ToString() != "")
        //                 khutt_id = Convert.ToInt32(ds.Tables[0].Rows[0]["khu_id"]);
        //             else
        //                 khutt_id = 0;
        //             if (ds.Tables[0].Rows[0]["dacdiem_id"].ToString() != "")
        //                 dacdiemtt_id = Convert.ToInt32(ds.Tables[0].Rows[0]["dacdiem_id"]);
        //             else
        //                 dacdiemtt_id = 0;
        //             sonhatt = CommonFunction.ChuanHoaTen(ds.Tables[0].Rows[0]["sonha"].ToString());
        //             if (ds.Tables[0].Rows[0]["vido"].ToString().Trim() != "")
        //                 Lat_TT = Convert.ToDouble(ds.Tables[0].Rows[0]["vido"]);
        //             else
        //                 Lat_TT = 0;
        //             if (ds.Tables[0].Rows[0]["kinhdo"].ToString().Trim() != "")
        //                 Lng_TT = Convert.ToDouble(ds.Tables[0].Rows[0]["kinhdo"]);
        //             else
        //                 Lng_TT = 0;
        //             //Địa chỉ báo cước
        //             /* if (ds.Tables[0].Rows[0]["diachibc_id"].ToString() != "")
        //                  diachibc_id = Convert.ToInt64(ds.Tables[0].Rows[0]["diachibc_id"]);
        //              else
        //                  diachibc_id = 0;
        //              if (ds.Tables[0].Rows[0]["tinhbc_id"].ToString() != "")
        //                  tinhbc_id = Convert.ToInt32(ds.Tables[0].Rows[0]["tinhbc_id"]);
        //              else
        //                  tinhbc_id = 0;
        //              if (ds.Tables[0].Rows[0]["quanbc_id"].ToString() != "")
        //                  quanbc_id = Convert.ToInt32(ds.Tables[0].Rows[0]["quanbc_id"]);
        //              else
        //                  quanbc_id = 0;
        //              if (ds.Tables[0].Rows[0]["phuongbc_id"].ToString() != "")
        //                  phuongbc_id = Convert.ToInt32(ds.Tables[0].Rows[0]["phuongbc_id"]);
        //              else
        //                  phuongbc_id = 0;
        //              if (ds.Tables[0].Rows[0]["phobc_id"].ToString() != "")
        //                  phobc_id = Convert.ToInt32(ds.Tables[0].Rows[0]["phobc_id"]);
        //              else
        //                  phobc_id = 0;
        //              if (ds.Tables[0].Rows[0]["apbc_id"].ToString() != "")
        //                  apbc_id = Convert.ToInt32(ds.Tables[0].Rows[0]["apbc_id"]);
        //              else
        //                  apbc_id = 0;
        //              if (ds.Tables[0].Rows[0]["khubc_id"].ToString() != "")
        //                  khubc_id = Convert.ToInt32(ds.Tables[0].Rows[0]["khubc_id"]);
        //              else
        //                  khubc_id = 0;
        //              if (ds.Tables[0].Rows[0]["dacdiembc_id"].ToString() != "")
        //                  dacdiembc_id = Convert.ToInt32(ds.Tables[0].Rows[0]["dacdiembc_id"]);
        //              else
        //                  dacdiembc_id = 0;
        //              sonhabc = CommonFunction.ChuanHoaTen(ds.Tables[0].Rows[0]["sonha_bc"].ToString());
        //              */
        //             if (ds.Tables[0].Rows[0]["kinhdo_bc"].ToString().Trim() != "")
        //                 Lat_BC = Convert.ToDouble(ds.Tables[0].Rows[0]["kinhdo_bc"]);
        //             else
        //                 Lat_BC = 0;
        //             if (ds.Tables[0].Rows[0]["vido_bc"].ToString().Trim() != "")
        //                 Lng_BC = Convert.ToDouble(ds.Tables[0].Rows[0]["vido_bc"]);
        //             else
        //                 Lng_BC = 0;
        //         }
        //         if (kieu == 3)
        //         {
        //             diachitb_id = Convert.ToInt64(ds.Tables[0].Rows[0]["diachi_id"]);
        //             tinhtb_id = Convert.ToInt32(ds.Tables[0].Rows[0]["tinh_id"]);
        //             quantb_id = Convert.ToInt32(ds.Tables[0].Rows[0]["quan_id"]);
        //             if (ds.Tables[0].Rows[0]["phuong_id"].ToString() != "")
        //                 phuongtb_id = Convert.ToInt32(ds.Tables[0].Rows[0]["phuong_id"]);
        //             else
        //                 phuongtb_id = 0;
        //             if (ds.Tables[0].Rows[0]["pho_id"].ToString() != "")
        //                 photb_id = Convert.ToInt32(ds.Tables[0].Rows[0]["pho_id"]);
        //             else
        //                 photb_id = 0;
        //             if (ds.Tables[0].Rows[0]["ap_id"].ToString() != "")
        //                 aptb_id = Convert.ToInt32(ds.Tables[0].Rows[0]["ap_id"]);
        //             else
        //                 aptb_id = 0;
        //             if (ds.Tables[0].Rows[0]["khu_id"].ToString() != "")
        //                 khutb_id = Convert.ToInt32(ds.Tables[0].Rows[0]["khu_id"]);
        //             else
        //                 khutb_id = 0;
        //             if (ds.Tables[0].Rows[0]["dacdiem_id"].ToString() != "")
        //                 dacdiemtb_id = Convert.ToInt32(ds.Tables[0].Rows[0]["dacdiem_id"]);
        //             else
        //                 dacdiemtb_id = 0;
        //             sonhatb = CommonFunction.ChuanHoaTen(ds.Tables[0].Rows[0]["sonha"].ToString());
        //             if (ds.Tables[0].Rows[0]["vido"].ToString().Trim() != "")
        //                 Lat_TB = Convert.ToDouble(ds.Tables[0].Rows[0]["vido"]);
        //             else
        //                 Lat_TB = 0;
        //             if (ds.Tables[0].Rows[0]["kinhdo"].ToString().Trim() != "")
        //                 Lng_TB = Convert.ToDouble(ds.Tables[0].Rows[0]["kinhdo"]);
        //             else
        //                 Lng_TB = 0;
        //             //Địa chỉ lắp đặt
        //             diachild_id = Convert.ToInt64(ds.Tables[0].Rows[0]["diachild_id"]);
        //             tinhld_id = Convert.ToInt32(ds.Tables[0].Rows[0]["tinhld_id"]);
        //             quanld_id = Convert.ToInt32(ds.Tables[0].Rows[0]["quanld_id"]);
        //             if (ds.Tables[0].Rows[0]["phuongld_id"].ToString() != "")
        //                 phuongld_id = Convert.ToInt32(ds.Tables[0].Rows[0]["phuongld_id"]);
        //             else
        //                 phuongld_id = 0;
        //             if (ds.Tables[0].Rows[0]["phold_id"].ToString() != "")
        //                 phold_id = Convert.ToInt32(ds.Tables[0].Rows[0]["phold_id"]);
        //             else
        //                 phold_id = 0;
        //             if (ds.Tables[0].Rows[0]["apld_id"].ToString() != "")
        //                 apld_id = Convert.ToInt32(ds.Tables[0].Rows[0]["apld_id"]);
        //             else
        //                 apld_id = 0;
        //             if (ds.Tables[0].Rows[0]["khuld_id"].ToString() != "")
        //                 khuld_id = Convert.ToInt32(ds.Tables[0].Rows[0]["khuld_id"]);
        //             else
        //                 khuld_id = 0;
        //             if (ds.Tables[0].Rows[0]["dacdiemld_id"].ToString() != "")
        //                 dacdiemld_id = Convert.ToInt32(ds.Tables[0].Rows[0]["dacdiemld_id"]);
        //             else
        //                 dacdiemld_id = 0;
        //             sonhald = CommonFunction.ChuanHoaTen(ds.Tables[0].Rows[0]["sonha_ld"].ToString());
        //             if (ds.Tables[0].Rows[0]["vido_ld"].ToString().Trim() != "")
        //                 Lat_LD = Convert.ToDouble(ds.Tables[0].Rows[0]["vido_ld"]);
        //             else
        //                 Lat_LD = 0;
        //             if (ds.Tables[0].Rows[0]["kinhdo_ld"].ToString().Trim() != "")
        //                 Lng_LD = Convert.ToDouble(ds.Tables[0].Rows[0]["kinhdo_ld"]);
        //             else
        //                 Lng_LD = 0;
        //             // if (Convert.ToInt32(cboKieuLD.SelectedValue) == KieuLapDat.TACH_VLAN)
        //             // {
        //             //   txtDiaChiTB.Text = ds.Tables[0].Rows[0]["diachi"].ToString();
        //             //   txtDiaChiLD.Text = ds.Tables[0].Rows[0]["diachi_ld"].ToString();
        //             //}
        //             // LAY_DONVI_THEO_LOAIDV_DIACHI_LD(phuongld_id, phold_id, apld_id, khuld_id);
        //         }
        //     }
        // }
	},
};
</script>