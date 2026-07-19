<template>
	<div class="col-sm-6 col-12">
		<div class="box-form">
			<div class="legend-title">Thông tin khách hàng</div>
			<div class="row">
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Mã giao dịch</div>
						<div class="value">
							<input type="text" @keyup.enter="onEnterMaGD" :value="input_ma_gd" @change="e=>input_ma_gd=e.target.value" ref="inputMaGD" class="form-control highlight bold" />
						</div>
					</div>
				</div>
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Ngày yêu cầu</div>
						<div class="value">
							<div class="input-icon-right">
								<ejs-datepicker v-model="ngay_yc" :disabled="true" />
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Mã KH
							<k-required-marker />
						</div>
						<div class="value">
							<div class="input-more-button">
								<button class="btn" @click="showTraCuuDanhBa" title="Tìm mã khách hàng">
									<span class="-ap icon-more_horiz"></span>
								</button>
								<input type="text" ref="inputMaKH" :value="input_ma_kh" @change="e=>input_ma_kh=e.target.value" @keyup.enter="onEnterMaKH" class="form-control bold" />
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Loại KH<k-required-marker /></div>
						<div class="value">
							<div class="select-custom" ref="ds_loai_kh">
								<select2 :settings="{ dropdownParent: $refs['ds_loai_kh'] }" ref="cboLoaiKH" v-model="loai_kh_selected" class="select2" :options="ds_loai_kh.map(e=> ({id: e.loaikh_id, text: e.ten_loaikh_dis}))" @change="changeLoaiKH"></select2>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Tên KH
							<k-required-marker />
						</div>
						<div class="value">
							<input type="text" :value="input_ten_kh" @change="e=>input_ten_kh=e.target.value" ref="inputTenKH" @keyup.enter="onEnterTenKH" @blur="onEnterTenKH" class="form-control bold" />
						</div>
					</div>
				</div>
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Điện thoại
							<k-required-marker />
						</div>
						<div class="value">
							<input type="text" ref="inputSDT" :value="input_so_dt" @change="e=>input_so_dt=e.target.value" @keypress="isNumber($event)" class="form-control" />
						</div>
					</div>
				</div>
			</div>
			<div class="info-row">
				<div class="key w100">Địa chỉ KH
					<k-required-marker />
				</div>
				<div class="value">
					<div class="input-more-button">
						<button class="btn" @click="chonDiaChiKH" title="Chọn địa chỉ khách hàng">
							<span class="-ap icon-more_horiz"></span>
						</button>
						<input 
							type="text" 
							ref="inputDiaChiKH" 
							@keypress="evt=>evt.preventDefault()" 
							:value="input_diachi_kh" 
							@change="e=>input_diachi_kh=e.target.value" 
							class="form-control bold" />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Tỉnh/thành</div>
						<div class="value">
							<div class="select-custom">
								<select2 
									ref="cboTinh" 
									class="select2"
									:settings="{ dropdownParent: $refs['ds_tinh'] }"
									v-model="tinh_selected" 
									:options="ds_tinh.map(e=> ({id: e.tinh_id, text: e.tentinh}))"
									@change="changeTinh">
								</select2>
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Mã số thuế</div>
						<div class="value">
							<input type="text" :value="input_ma_so_thue" @change="e=>input_ma_so_thue=e.target.value" class="form-control" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Loại giấy tờ</div>
						<div class="value">
							<div class="select-custom" ref="loai_gt">
								<select2 :settings="{ dropdownParent: $refs['loai_gt'] }" ref="cboLoaiGT" v-model="loai_gt_selected" class="select2" :options="loai_gt.map(e=> ({id: e.loaigt_id, text: e.loai_gt}))"></select2>
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Số giấy tờ</div>
						<div class="value">
							<input type="text" :value="input_sogiayto" @change="e=>input_sogiayto=e.target.value" @keyup.enter="onBlurSoGT" ref="inputSoGT" class="form-control" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Ngày cấp</div>
						<div class="value">
							<div class="input-icon-right">
								<ejs-datepicker v-model="ngay_cap" ref="dtpNgayCap"/>
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Nơi cấp</div>
						<div class="value">
							<input type="text" :value="input_noicap" @change="e=>input_noicap=e.target.value" class="form-control" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Ngày hết hạn</div>
						<div class="value">
							<div class="input-icon-right">
								<ejs-datepicker v-model="ngay_hethan" />
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Ngày sinh</div>
						<div class="value">
							<div class="input-icon-right">
								<ejs-datepicker v-model="ngay_sinh" ref="dtpNgaySinh"/>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row" style="height:40px">
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Giới tính</div>
						<div class="value">
							<div class="list-checks mart7">
								<div class="item">
									<div class="check-action">
										<input type="radio" :checked="radio_gioitinh==0" name="gioitinh" value="0" @change="radio_gioitinh=0" class="check" />
										<span class="name">Không</span>
									</div>
								</div>
								<div class="item">
									<div class="check-action">
										<input type="radio" :checked="radio_gioitinh==1" name="gioitinh" value="1" @change="radio_gioitinh=1" class="check" />
										<span class="name">Nam</span>
									</div>
								</div>
								<div class="item">
									<div class="check-action">
										<input type="radio" :checked="radio_gioitinh==2" name="gioitinh" value="2" @change="radio_gioitinh=2" class="check" />
										<span class="name">Nữ</span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Quốc tịch</div>
						<div class="value">
							<div class="select-custom" ref="ds_quoctich">
								<select2 :settings="{ dropdownParent: $refs['ds_quoctich'] }" ref="cboQuocTich" v-model="quoctich_selected" class="select2" :options="ds_quoctich.map(e=> ({id: e.quoctich_id, text: e.tenquoctich}))"></select2>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="info-row">
				<div class="key w100">Ngành nghề</div>
				<div class="value">
					<div class="input-more-button">
						<button class="btn" @click="clickButtonNganhNghe" title="Chọn chi tiết ngành nghề">
							<span class="-ap icon-more_horiz"></span>
						</button>
						<div class="select-custom" ref="ds_nganhnghe">
							<select2 :settings="{ dropdownParent: $refs['ds_nganhnghe'] }" ref="cboNganhNghe" v-model="nganhnghe_selected" class="select2" :options="ds_nganhnghe.map(e=> ({id: e.nganhnghe_id, text: e.tennganhnghe}))" @change="changeNganhNghe"></select2>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal -->
		<SearchAccountModal ref="searchAccountModal" @form-close="formCloseSearchAccount" />
		<DiaChiKhachHangModal ref="diaChiKhachHangModal" :data="dataDiaChi" @accept="acceptDiaChiKH" />
		<ChiTietNganhNgheModal ref="chiTietNganhNgheModal" :data="dataPopupTieuNganh" @accept="acceptTieuNganh" />
	</div>
</template>

<script>
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import myApi from '../api'
import {
	LoaiHopDong
} from '../ThamSo'
import SearchAccountModal from '../popups/SearchAccountModal.vue'
import DiaChiKhachHangModal from '../popups/ChonDiaChi/DiaChiKhachHangModal.vue'
import ChiTietNganhNgheModal from '../popups/ChiTietNganhNghe/ChiTietNganhNgheModal.vue'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
export default {
	name: 'ThongTinKhachHang',
	props: {
		parent: {}
	},
	components: {
		SearchAccountModal,
		DiaChiKhachHangModal,
		ChiTietNganhNgheModal,
		KRequiredMarker
	},
	data() {
		return {
			hdtb:{},

			radio_gioitinh: 0,
			ngay_yc: moment(new Date()).format('DD/MM/YYYY'),
			ngay_cap: new Date(),
			ngay_hethan: '',
			ngay_sinh: '',

			ds_tinh: [],
			tinh_selected: this.$root.token.getPhanVungID(),
			loai_gt: [],
			loai_gt_selected: null,
			ds_nganhnghe: [],
			nganhnghe_selected: null,
			ds_quoctich: [],
			quoctich_selected: null,
			ds_loai_kh: [],
			loai_kh_selected: null,

			input_ma_gd: '',
			input_ma_kh: '',
			input_ma_tb: '',
			input_ten_kh: '',
			input_ma_so_thue: '',
			input_diachi_kh: '',
			input_sogiayto: '',
			input_noicap: '',
			input_so_dt: '',
			loaihd_id: 38,
			dataDiaChi: {
				diachi_id: 0,
				tinh_id: 0,
				quan_id: 0,
				phuong_id: 0,
				pho_id: 0,
				ap_id: 0,
				khu_id: 0,
				dacdiem_id: 0,
				sonha: '',
				diachi: ''
			},
			dataDiaChiKH: {
				diachi_id: 0,
				tinh_id: 0,
				quan_id: 0,
				phuong_id: 0,
				pho_id: 0,
				ap_id: 0,
				khu_id: 0,
				dacdiem_id: 0,
				sonha: '',
				diachi: ''
			},
			dataTieuNganh: {
				TNC1_ID: 0,
				TNC2_ID: 0,
				TNC3_ID: 0,
				TNC4_ID: 0,
				nganhnghe_id: 0
			},
			dataPopupTieuNganh: {
				TNC1_ID: 0,
				TNC2_ID: 0,
				TNC3_ID: 0,
				TNC4_ID: 0,
				nganhnghe_id: 0
			},
			readonlySomay: true
		}
	},
	methods: {
		isNumber(evt) {
			evt = (evt) ? evt : window.event;
			var charCode = (evt.which) ? evt.which : evt.keyCode;
			if ((charCode > 31 && (charCode < 48 || charCode > 57))) {
				evt.preventDefault()
			} else {
				return true
			}
		},
		focusMaGD() {
			this.$nextTick(() => {
				this.$refs.inputMaGD.focus()
			})
		},
		focusTenKH() {
			this.$nextTick(() => {
				this.$refs.inputTenKH.focus()
			})
		},
		focusMaKH() {
			this.$nextTick(() => {
				this.$refs.inputMaKH.focus()
			})
		},
		focusMaTB() {
			this.$nextTick(() => {
				this.$refs.inputMaTB.focus()
			})
		},
		focusSDT() {
			this.$nextTick(() => {
				this.$refs.inputSDT.focus()
			})
		},
		focusDiaChiKH() {
			this.$nextTick(() => {
				this.$refs.inputDiaChiKH.focus()
			})
		},
		focusSoGT() {
			this.$nextTick(() => {
				this.$refs.inputSoGT.focus()
			})
		},
		focusNgayCap() {
			this.$nextTick(() => {
				this.$refs.dtpNgayCap.show()
			})
		},
		focusNgaySinh() {
			this.$nextTick(() => {
				this.$refs.dtpNgaySinh.show()
			})
		},
		setInputMaGD(maGD) {
			this.input_ma_gd = maGD
		},
		onEnterMaGD() {
			this.$emit('searchMaGD', this.input_ma_gd.trim())
		},
		onEnterMaKH() {
			this.$emit('searchMaKH', this.input_ma_kh.trim())
		},
		showTraCuuDanhBa() {
			//UR2.8.002
			this.$refs.searchAccountModal.showModal()
		},
		formCloseSearchAccount(data) {
			console.log(data)
			if (data && data.ma_kh) {
				this.input_ma_kh = data.ma_kh
				this.$emit('searchMaKH', this.input_ma_kh.trim())
			}
		},
		changeTinh() {
			this.$emit('changed', {
				action: 'CHON_TINH',
				data: {tinh_id:this.tinh_selected}
			});
		},
		changeLoaiKH() {
			if (this.ds_loai_kh == null || this.ds_loai_kh.length == 0 || this.loai_kh_selected == null)
				return;

			let loaiKH = this.ds_loai_kh.find(x => x.loaikh_id == this.loai_kh_selected)

			this.$emit('changed', {
				action: 'CHON_LOAI_KH',
				data: loaiKH
			});
		},
		chonDiaChiKH() {
			Object.assign(this.dataDiaChi, this.dataDiaChiKH)
			//địa chỉ khách hàng
			this.$refs.diaChiKhachHangModal.showModal()
		},
		//callback form địa chỉ khách hàng
		acceptDiaChiKH(data) {
			//handle
			this.input_diachi_kh = data.diachi
			this.tinh_selected = data.tinh_id
			Object.assign(this.dataDiaChiKH, data)
			this.$emit('changed', {
				action: 'CHON_DIACHI_KH',
				data: this.dataDiaChiKH
			});
		},
		clickButtonNganhNghe() {
			Object.assign(this.dataPopupTieuNganh, this.dataTieuNganh)
			this.$refs.chiTietNganhNgheModal.showModal()
		},
		acceptTieuNganh(data) {
			Object.assign(this.dataTieuNganh, data)
			this.nganhnghe_selected = this.dataTieuNganh.nganhnghe_id

		},
		async lay_danhmuc_lapmoi_tt_chung_v7(chuoi) {
			try {
				
				this.ds_tinh = []
				this.loai_gt = []
				this.ds_nganhnghe = []
				this.ds_quoctich = []
				this.ds_loai_kh = []

				//Danh sách tỉnh
				let response = await myApi.lay_danhsach_tinh(this.axios)
				if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
					this.ds_tinh = response.data.data
				}

				//Danh mục khác
				response = await myApi.lay_danhmuc_lapmoi_tt_chung_v7(this.axios, chuoi)
				if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
					if (response.data.data.loai_gt) {
						this.loai_gt = response.data.data.loai_gt
						if (this.loai_gt.length > 0) {
							this.loai_gt_selected = this.loai_gt[0].loaigt_id
						}
					}
					if (response.data.data.nganhnghe) {
						this.ds_nganhnghe = response.data.data.nganhnghe
						if (this.ds_nganhnghe.length > 0) {
							this.nganhnghe_selected = this.ds_nganhnghe[0].nganhnghe_id
						}
					}
					if (response.data.data.quoctich) {
						this.ds_quoctich = response.data.data.quoctich
						if (this.ds_quoctich.length > 0) {
							this.quoctich_selected = this.ds_quoctich[0].quoctich_id
						}
					}
					if (response.data.data.loai_kh) {
						this.ds_loai_kh = response.data.data.loai_kh
						if (this.ds_loai_kh.length > 0) {
							//Gán mặc định "ten_loaikh_dis": "Cá nhân - Khác",
							this.loai_kh_selected = 1
							// this.loai_kh_selected=this.ds_loai_kh[0].loaikh_id
						}
					}
				}
			} catch (e) {
				if (e.data && e.data.message) {
					this.$toast.error(e.data.message)
				} else if (e.response && e.response.data && e.response.data.message) {
					this.$toast.error(e.response.data.message)
				} else {
					this.$toast.error('Không load được danh mục chung')
				}
			} finally {
				
			}
		},
		getData() {
			return {
				MA_GD: this.input_ma_gd,
				MA_KH: this.input_ma_kh,
				MA_TB: this.input_ma_tb,
				TEN_KH: this.input_ten_kh,
				MST: this.input_ma_so_thue,
				DIACHI_KH: this.input_diachi_kh,
				SO_GT: this.input_sogiayto,
				NOICAP: this.input_noicap,
				SO_DT: this.input_so_dt,
				NGANHNGHE_ID: this.nganhnghe_selected,
				
				LOAIGT_ID: this.loai_gt_selected,
				LOAIKH_ID: this.loai_kh_selected,

				NGAY_SN: Object.prototype.toString.call(this.ngay_sinh) === '[object Date]'?moment(this.ngay_sinh).format('DD/MM/YYYY HH:mm:ss'):this.ngay_sinh,
				
				NGAYCAP: Object.prototype.toString.call(this.ngay_cap) === '[object Date]'?moment(this.ngay_cap).format('DD/MM/YYYY HH:mm:ss'):this.ngay_cap,
				NGAY_YC: Object.prototype.toString.call(this.ngay_yc) === '[object Date]'?moment(this.ngay_yc).format('DD/MM/YYYY HH:mm:ss'):this.ngay_yc,
				NGAYHETHAN_GT: Object.prototype.toString.call(this.ngay_hethan) === '[object Date]'?moment(this.ngay_hethan).format('DD/MM/YYYY HH:mm:ss'):this.ngay_hethan,

				GIOITINH: this.radio_gioitinh == 1 ? 0 : this.radio_gioitinh == 2 ? 1 : 2,
				QUOCTICH_ID: this.quoctich_selected,

				TNC1_ID:this.dataTieuNganh.TNC1_ID,
				TNC2_ID:this.dataTieuNganh.TNC2_ID,
				TNC3_ID:this.dataTieuNganh.TNC3_ID,
				TNC4_ID:this.dataTieuNganh.TNC4_ID,

				dataDiaChiKH: this.dataDiaChiKH
			}
		},
		changeNganhNghe() {
			if (this.loaihd_id == LoaiHopDong.TUVAN_DV) {
				return
			}
			if (this.nganhnghe_selected != this.dataTieuNganh.nganhnghe_id) {
				this.dataTieuNganh = {
					TNC1_ID: 0,
					TNC2_ID: 0,
					TNC3_ID: 0,
					TNC4_ID: 0,
					nganhnghe_id: this.nganhnghe_selected != null ? this.nganhnghe_selected : 0
				}
			}
			this.clickButtonNganhNghe()
		},
		onEnterTenKH() {
			this.$emit('onEnterTenKH', this.input_ten_kh.trim())
		},
		onBlurSoGT() {
			if (this.input_sogiayto.trim() == '') {
				return
			}
			this.$emit('onBlurSoGT', this.input_sogiayto.trim())
		},
		//type maGD
		async fillData(data) {
			this.hdtb=data;

			this.input_ma_tb = this.hdtb.ma_tb != null ? this.hdtb.ma_tb : ''
			this.input_ma_gd = this.hdtb.ma_gd != null ? this.hdtb.ma_gd : ''
			this.input_so_dt = this.hdtb.so_dt != null ? this.hdtb.so_dt : ''
			this.input_ma_kh = this.hdtb.ma_kh != null ? this.hdtb.ma_kh : ''
			this.input_ten_kh = this.hdtb.ten_kh != null ? this.hdtb.ten_kh : ''
			this.input_ma_so_thue = (this.hdtb.mst != undefined && this.hdtb.mst != null) ? this.hdtb.mst : ''
			this.input_diachi_kh = this.hdtb.diachi_kh != null ? this.hdtb.diachi_kh : ''


			this.tinh_selected = this.hdtb.tinh_id;
			this.loai_gt_selected = this.hdtb.loaigt_id != null ? this.hdtb.loaigt_id : 1
			this.loai_kh_selected = this.hdtb.loaikh_id

			this.input_sogiayto = this.hdtb.so_gt != null ? this.hdtb.so_gt : ''
			this.ngay_cap = this.hdtb.ngaycap != null ? moment(this.hdtb.ngaycap, 'DD/MM/YYYY HH:mm:ss').toDate(): null;

			this.ngay_hethan = this.hdtb.ngayhethan_gt != null ? moment(this.hdtb.ngayhethan_gt, 'DD/MM/YYYY HH:mm:ss').toDate():null;
			this.input_noicap = this.hdtb.noicap != null ? this.hdtb.noicap : ''

			if(this.hdtb.gioitinh==0)
				this.radio_gioitinh = 1;
			else if(this.hdtb.gioitinh==1)
				this.radio_gioitinh = 2;
			else
				this.radio_gioitinh = 0;

			//khởi tạo ngành nghề
			this.dataTieuNganh = {
				TNC1_ID: (this.hdtb.tnc1_id != undefined && this.hdtb.tnc1_id != null) ? this.hdtb.tnc1_id : 0,
				TNC2_ID: (this.hdtb.tnc2_id != undefined && this.hdtb.tnc2_id != null) ? this.hdtb.tnc2_id : 0,
				TNC3_ID: (this.hdtb.tnc3_id != undefined && this.hdtb.tnc3_id != null) ? this.hdtb.tnc3_id : 0,
				TNC4_ID: (this.hdtb.tnc4_id != undefined && this.hdtb.tnc4_id != null) ? this.hdtb.tnc4_id : 0,
				nganhnghe_id: this.nganhnghe_selected != null ? this.nganhnghe_selected : 0
			}

			if (this.nganhnghe_selected != this.hdtb.nganhnghe_id) {
				this.dataTieuNganh.nganhnghe_id = this.hdtb.nganhnghe_id != null ? this.hdtb.nganhnghe_id : 0
				this.nganhnghe_selected = this.hdtb.nganhnghe_id
			} else {
				this.dataTieuNganh.nganhnghe_id = this.nganhnghe_selected != null ? this.nganhnghe_selected : 0
			}

			if (this.hdtb.ngay_sn != null && this.hdtb.ngay_sn.toString() != '') {
				this.ngay_sinh = moment(this.hdtb.ngay_sn, 'DD/MM/YYYY HH:mm:ss').toDate();
			} else {
				this.ngay_sinh = null;
			}

			this.ngay_yc = this.hdtb.ngay_yc != null ? moment(this.hdtb.ngay_yc, 'DD/MM/YYYY HH:mm:ss').toDate(): null;

			await this.hienthi_tt_diachi_kh();
		},
		//Type maKH
		async initInfoMaKH(item) {
			let tinh_kh_id=item.tinhkh_id??item.tinhtt_id;
			this.tinh_selected = tinh_kh_id;

			this.input_ma_kh = item.ma_kh != null ? item.ma_kh : ''
			this.input_ten_kh = item.ten_kh != null ? item.ten_kh : ''

			this.input_diachi_kh = item.diachi_kh != null ? item.diachi_kh : ''

			this.input_ma_so_thue = item.mst != null ? item.mst : ''

			if (item.ngay_sn != null && item.ngay_sn.toString() != '') {
				this.ngay_sinh = moment(item.ngay_sn, 'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY')
			} else {
				this.ngay_sinh = ''
			}
			this.ngay_yc = moment(new Date()).format('DD/MM/YYYY')

			this.loai_gt_selected = item.loaigt_id??1
			this.loai_kh_selected = item.loaikh_id??1

			if (item.gioitinh != null) {
				if (item.gioitinh.toString() == '0') {
					this.radio_gioitinh = 1
				} else if (item.gioitinh.toString() == '1') {
					this.radio_gioitinh = 2
				}
			} else {
				this.radio_gioitinh = 0
			}
			//
			this.quoctich_selected = item.quoctich_id
			this.input_so_dt = item.so_dt != null ? item.so_dt : ''

			//khởi tạo ngành nghề
			this.dataTieuNganh = {
				TNC1_ID: (item.tnc1_id != undefined && item.tnc1_id != null) ? item.tnc1_id : 0,
				TNC2_ID: (item.tnc2_id != undefined && item.tnc2_id != null) ? item.tnc2_id : 0,
				TNC3_ID: (item.tnc3_id != undefined && item.tnc3_id != null) ? item.tnc3_id : 0,
				TNC4_ID: (item.tnc4_id != undefined && item.tnc4_id != null) ? item.tnc4_id : 0,
				nganhnghe_id: this.nganhnghe_selected != null ? this.nganhnghe_selected : 0
			}

			if (this.nganhnghe_selected != item.nganhnghe_id) {
				this.dataTieuNganh.nganhnghe_id = item.nganhnghe_id != null ? item.nganhnghe_id : 0
				this.nganhnghe_selected = item.nganhnghe_id
			} else {
				this.dataTieuNganh.nganhnghe_id = this.nganhnghe_selected != null ? this.nganhnghe_selected : 0
			}

			if (item.ngayhethan_gt != null && item.ngayhethan_gt.toString() != '') {
				//chưa rõ format date đầu vào
				this.ngay_hethan = moment(item.ngayhethan_gt, 'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY')
			} else {
				this.ngay_hethan = ''
			}
			this.input_sogiayto = item.so_gt != null ? item.so_gt : ''

			if (item.ngaycap != null && item.ngaycap.toString() != '') {
				this.ngay_cap = moment(item.ngaycap, 'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY')
			} else {
				this.ngay_cap = ''
			}
			this.loai_gt_selected = item.loaigt_id
			this.input_noicap = item.noicap != null ? item.noicap : ''

			this.input_ma_tb = item.ma_tb != null ? item.ma_tb : ''

			//hiển thị thông tin địa chỉ// enter maKH
			this.dataDiaChiKH = {
				diachi_id: item.diachikh_id != null ? item.diachikh_id : 0,
				tinh_id: item.tinhkh_id != null ? item.tinhkh_id : 0,
				quan_id: item.quankh_id != null ? item.quankh_id : 0,
				phuong_id: item.phuongkh_id != null ? item.phuongkh_id : 0,
				pho_id: item.phokh_id != null ? item.phokh_id : 0,
				ap_id: item.apkh_id != null ? item.apkh_id : 0,
				khu_id: item.khukh_id != null ? item.khukh_id : 0,
				dacdiem_id: item.dacdiemkh_id != null ? item.dacdiemkh_id : 0,
				sonha: item.sonha_kh != null ? item.sonha_kh : '',
				diachi: item.diachi_kh != null ? item.diachi_kh : ''
			}
			//this.$emit('initDiaChiKH', this.dataDiaChiKH)

			this.$emit('changed', {
				action: 'CHON_DIACHI_KH',
				data: this.dataDiaChiKH
			});
		},
		async hienthi_tt_diachi_kh() {
			try {
				this.dataDiaChiKH = {
					diachi_id: 0,
					tinh_id: 0,
					quan_id: 0,
					phuong_id: 0,
					pho_id: 0,
					ap_id: 0,
					khu_id: 0,
					dacdiem_id: 0,
					sonha: '',
					diachi: ''
				}
				let response = await myApi.lay_ds_diachi_theo_hdkh(this.axios, this.hdtb.tinh_id, this.hdtb.hdkh_id)
				
				if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data && response.data.data.length > 0) {
					let result = response.data.data[0]
					this.dataDiaChiKH = {
						diachi_id: result.diachi_id != null ? result.diachi_id : 0,
						tinh_id: result.tinh_id != null ? result.tinh_id : 0,
						quan_id: result.quan_id != null ? result.quan_id : 0,
						phuong_id: result.phuong_id != null ? result.phuong_id : 0,
						pho_id: result.pho_id != null ? result.pho_id : 0,
						ap_id: result.ap_id != null ? result.ap_id : 0,
						khu_id: result.khu_id != null ? result.khu_id : 0,
						dacdiem_id: result.dacdiem_id != null ? result.dacdiem_id : 0,
						sonha: result.sonha != null ? result.sonha : '',
						diachi: result.diachi != null ? result.diachi : ''
					}
					this.$emit('initDiaChiKH', this.dataDiaChiKH)
				}
			} catch (e) {
				
				if (e.data && e.data.message) {
					this.$toast.error(e.data.message)
				} else if (e.response && e.response.data && e.response.data.message) {
					this.$toast.error(e.response.data.message)
				} else {
					this.$toast.error('Không load được thông tin địa chỉ khách hàng')
				}
			}
		},
		async clear() {
			this.hdtb={};
			this.input_ma_gd = ''
			this.input_ma_kh = ''
			this.input_ma_tb = ''
			this.input_ten_kh = ''
			this.input_ma_so_thue = ''
			this.input_diachi_kh = ''
			this.input_sogiayto = ''
			this.input_noicap = ''
			this.input_so_dt = ''

			this.radio_gioitinh = 0
			this.ngay_yc = moment(new Date()).format('DD/MM/YYYY')
			this.ngay_cap = ''
			this.ngay_hethan = ''
			this.ngay_sinh = ''

			//this.tinh_selected = this.$root.token.getPhanVungID()
			this.loai_kh_selected = null

			if (this.loai_gt.length > 0) {
				this.loai_gt_selected = this.loai_gt[0].loaigt_id
			}
			if (this.ds_quoctich.length > 0) {
				this.quoctich_selected = this.ds_quoctich[0].quoctich_id
			}
			this.dataTieuNganh = {
				TNC1_ID: 0,
				TNC2_ID: 0,
				TNC3_ID: 0,
				TNC4_ID: 0,
				nganhnghe_id: this.nganhnghe_selected != null ? this.nganhnghe_selected : 0
			}
			this.dataDiaChiKH = {
				diachi_id: 0,
				tinh_id: 0,
				quan_id: 0,
				phuong_id: 0,
				pho_id: 0,
				ap_id: 0,
				khu_id: 0,
				dacdiem_id: 0,
				sonha: '',
				diachi: ''
			}
		},
		async initDuLieu() {
			//Handle form trùng
			let endPath = this.$router.currentRoute.path.split('/').pop()
			if (endPath == 'SurveyRequest') {
				//[UR2.1.001] - Tiếp nhận yêu cầu khảo sát lắp đặt
				this.loaihd_id = LoaiHopDong.KHAOSATDATMOI
			} else if (endPath == '800126InquiryWatchList') {
				//[UR2.1.057] - 80126- Tiếp nhận yêu cầu
				this.loaihd_id = LoaiHopDong.TIEPNHANHD
			} else if (endPath == 'ReceiveSurveyRequestChangeType') {
				//[UR2.1.002] - Tiếp nhận yêu cầu khảo sát thay đổi loại hình
				this.loaihd_id = LoaiHopDong.KHAOSATCDLH
			} else if (endPath == 'ReceiveRequest') {
				//[UR2.1.055] - Tiếp nhận yêu cầu
				this.loaihd_id = LoaiHopDong.TIEPNHANHD
			}
			if (this.loaihd_id != LoaiHopDong.KHAOSATDATMOI && this.loaihd_id != LoaiHopDong.TIEPNHANHD && this.loaihd_id != LoaiHopDong.TUVAN_DV) {
				//enable số máy
				this.readonlySomay = false
			} else {
				this.readonlySomay = true
			}

			await this.lay_danhmuc_lapmoi_tt_chung_v7('LOAI_GT|NGANHNGHE|QUOCTICH|LOAI_KH')
		}
	}
}
</script>
<style>
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
</style>
