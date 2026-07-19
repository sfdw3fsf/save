<template>
	<div class="col-sm-6 col-12">
		<div class="box-form">
			<div class="legend-title">Thông tin thuê bao</div>
			<div class="row">
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Dịch vụ
							<k-required-marker />
						</div>
						<div class="value">
							<div class="select-custom" ref="ds_dichvu">
								<select2 :settings="{ dropdownParent: $refs['ds_dichvu'] }" ref="cboDichVu" v-model="dichvu_selected" class="select2" :options="ds_dichvu.map(e=> ({id: e.dichvuvt_id, text: e.ten_dvvt}))" @change="changeDichVu"></select2>
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Loại hình
							<k-required-marker />
						</div>
						<div class="value">
							<div class="select-custom" ref="ds_loaihinh">
								<select2
									:settings="{ dropdownParent: $refs['ds_loaihinh'] }"
									ref="cboLoaiHinh"
									v-model="loaihinh_selected"
									class="select2"
									:options="loaihinh_tb_filter.map(e=> ({id: e.loaitb_id, text: e.loaihinh_tb}))"
									@change="changeLoaiHinh"
								></select2>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Kiểu yêu cầu</div>
						<div class="value">
							<div class="select-custom" ref="ds_kieu_yc">
								<select2
								:settings="{ dropdownParent: $refs['ds_kieu_yc'] }"
								ref="cboKieuYC"
								v-model="kieu_yc_selected"
								class="select2"
								:options="ds_kieu_yc.map(e=> ({id: e.KIEULD_ID, text: e.TEN_KIEULD}))"
								@change="changeKieuLD"
							></select2>
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Đối tượng TB</div>
						<div class="value">
							<div class="select-custom" ref="ds_doituong_tb">
								<select2 :settings="{ dropdownParent: $refs['ds_doituong_tb'] }" ref="cboDoiTuong" v-model="doituong_selected" class="select2" :options="ds_doituong_tb.map(e=> ({id: e.doituong_id, text: e.ten_dt}))"></select2>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="info-row">
				<div class="key w100">Tên thuê bao</div>
				<div class="value">
					<input type="text" ref="inputTenTB" :value="input_ten_tb" @change="e=>input_ten_tb=e.target.value" class="form-control bold" />
				</div>
			</div>
			<div class="info-row">
				<div class="key w100">Địa chỉ TB
					<k-required-marker />
				</div>
				<div class="value">
					<div class="input-more-button">
						<button class="btn" @click="clickChonDiaChi(1)" title="Chọn địa chỉ thuê bao">
							<span class="-ap icon-more_horiz"></span>
						</button>
						<input type="text" ref="inputDiaChiTB" @keypress="evt=>evt.preventDefault()" :value="input_diachi_tb" @change="e=>input_diachi_tb=e.target.value" class="form-control bold" />
					</div>
				</div>
			</div>
			<div class="info-row">
				<div class="key w100">Địa chỉ LĐ</div>
				<div class="value">
					<div class="input-more-button">
						<button class="btn" @click="clickChonDiaChi(2)" title="Chọn địa chỉ lắp đặt">
							<span class="-ap icon-more_horiz"></span>
						</button>
						<input type="text" ref="inputDiaChiLD" @keypress="evt=>evt.preventDefault()" :value="input_diachi_ld" @change="e=>input_diachi_ld=e.target.value" class="form-control bold" />
					</div>
				</div>
			</div>

			<div class="info-row">
				<div class="key w100">Đơn vị xử lý</div>

				<div class="value">
					<div class="select-custom" ref="ds_donvi_xyly">
						<select2
							:settings="{ dropdownParent: $refs['ds_donvi_xyly'] }"
							ref="cboDonViXL"
							v-model="donvi_xyly_selected"
							class="select2"
							@change="changeDonviXL"
							:options="ds_donvi_xyly.map(e=> ({id: e.donvi_id, text: e.ten_dv}))">
						</select2>
					</div>
				</div>
			</div>

			<div class="info-row">
				<div class="key w100">Khu vực</div>
				<div class="value">
					<!--<div class="input-more-button">-->
						<!--<button class="btn" @click="clickKhuVuc" title="Địa bàn quản lý thuê bao theo địa chỉ lắp đặt">
							<span class="-ap icon-more_horiz"></span>
						</button>-->
						<input type="text" ref="inputKhuVuc" @keypress="evt=>evt.preventDefault()" :value="input_khuvuc" @change="e=>input_khuvuc=e.target.value" class="form-control" />
					<!--</div>-->
				</div>
			</div>

			<div class="row">
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">{{labelAccount}}</div>
						<div class="value">
							<input type="text" ref="inputMaTB" :value="input_ma_tb" @change="e=>input_ma_tb=e.target.value" class="form-control highlight red" />
						</div>
					</div>
				</div>
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Số lượng</div>
						<div class="value">
							<input type="text" @blur="blurInputSoLuong" @keypress="isNumber($event)" :value="input_soluong" @change="e=>input_soluong=e.target.value" class="form-control tright" />
						</div>
					</div>
				</div>
			</div>
			<div class="info-row">
				<div class="key w100">{{labelTocDo}}</div>
				<div class="value">
					<select2 v-model="tocdo_selected" class="select2" :options="ds_tocdo.map(e=> ({id: e.tocdo_id, text: e.thuonghieu}))"></select2>
				</div>
			</div>
			<div class="info-row">
				<div class="key w100">Gói đa DV</div>
				<div class="value">
					<select2 v-model="goi_dadv_selected" class="select2" :options="ds_goi_dadv.map(e=> ({id: e.goi_id, text: e.ten_goi}))"></select2>
				</div>
			</div>
		</div>
		<!-- Modal -->
		<DiaChiModal ref="diaChiModal" :data="dataDiaChi" @accept="onAcceptDiaChi" />
		<ThayDoiKhuVucQuanLyThueBaoModal ref="thayDoiKhuVucQuanLyThueBaoModal" :data="dataPopupKhuVuc" @accept="onAcceptKhuVuc" @success_update="successUpdateThayDoiKhuVucQuanLy" />
		<ChonDonViModal ref="chonDonViModal" :data="ds_donvi_xyly" @accept="acceptChonDonVi" />
	</div>
</template>

<script>
import myApi from '../api'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import {
	DichVuVienThong,
	LoaiHopDong,
	LoaiNV
} from '../ThamSo'
import DiaChiModal from '../popups/ChonDiaChi/DiaChiModal.vue'
import ThayDoiKhuVucQuanLyThueBaoModal from '../popups/ThayDoiKhuVucQuanLyThueBao/ThayDoiKhuVucQuanLyThueBaoModal.vue'
import ChonDonViModal from '../popups/ChonDonVi/ChonDonViModal.vue'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
export default {
	name: 'ThongTinThueBao',
	props: {
		//Tham số
		parent: {}
	},
	components: {
		KDatePicker,
		DiaChiModal,
		ThayDoiKhuVucQuanLyThueBaoModal,
		ChonDonViModal,
		KRequiredMarker
	},
	computed: {
		loainhanvien_id() {
			if (this.loaiKH.loai) return 0;

			if (this.loaiKH.loai == 0) {
				return LoaiNV.NHANVIEN_KINHDOANH
			} else if (this.loaiKH.loai == 1) {
				return LoaiNV.NHANVIEN_AM
			} else return 0
		},
		ten_loaihinh() {
			if (this.loaihinh_selected == null) {
				return ''
			}
			const index = this.loaihinh_tb_filter.findIndex(x => x.loaitb_id == this.loaihinh_selected)
			return index > -1 ? this.loaihinh_tb_filter[0].loaihinh_tb : ''
		},
		labelAccount() {
			if (this.dichvu_selected == undefined || this.dichvu_selected == null) {
				return "Số máy/acc";
			}

			if (['1', '2', '10', '11'].includes(this.dichvu_selected))
				return "Số máy";

			return "Account";
		},
		labelTocDo() {
			if (this.dichvu_selected == undefined || this.dichvu_selected == null) {
				return "Tốc độ";
			}

			return this.dichvu_selected == '2' ? "Gói cước" : "Tốc độ";
		}
	},
	data() {
		return {
			hdtb:{},
	  		tinh_selected: this.$root.token.getPhanVungID(),
			ds_dichvu: [],
			dichvu_selected: null,
			ds_loaihinh: [],
			loaihinh_selected: null,
			ds_kieu_yc: [],
			kieu_yc_selected: null,
			ds_donvi_xyly: [],
			donvi_xyly_selected: null,
			ds_doituong_tb: [],
			doituong_selected: null,
			ds_tocdo: [],
			tocdo_selected: null,
			ds_goi_dadv: [],
			goi_dadv_selected: null,
			loaihinh_tb_filter: [],
			loaihd_id: 38,
			input_ten_tb: '',
			input_diachi_tb: '',
			input_diachi_ld: '',
			input_khuvuc: '',
			input_soluong: '1',
			input_ghichu: '',
			input_ma_tb: '',

			kieu_chon_diachi: 1,
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
				//
				block: '',
				tang: '',
				sophong: '',
				motathem: '',
				diachi: ''
			},
			dataDiaChiTB: {
				diachi_id: 0,
				tinh_id: 0,
				quan_id: 0,
				phuong_id: 0,
				pho_id: 0,
				ap_id: 0,
				khu_id: 0,
				dacdiem_id: 0,
				sonha: '',
				//
				block: '',
				tang: '',
				sophong: '',
				motathem: '',
				diachi: ''
			},
			dataDiaChiLD: {
				diachi_id: 0,
				tinh_id: 0,
				quan_id: 0,
				phuong_id: 0,
				pho_id: 0,
				ap_id: 0,
				khu_id: 0,
				dacdiem_id: 0,
				sonha: '',
				//
				block: '',
				tang: '',
				sophong: '',
				motathem: '',
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
			khuvuc_selected: null,
			loaidv_id: 115,
			htdh_selected:null,
			thuebao_id: 0,
			loaiKH: {},
			//hard
			dataPopupKhuVuc: {
				quan_id: 0,
				phuong_id: 0,
				pho_id: 0,
				ap_id: 0,
				khu_id: 0,
				dacdiem_id: 0,
				khuvuc_id: 0,
				donviql_id: 0,
				hdtb_id: 0
			},
			dsToThiCong: [],
			isFocusDichVu: false
		}
	},
	methods: {
		isNew(){
			return this.hdtb.hdtb_id==undefined || this.hdtb.hdtb_id==null || this.hdtb.hdtb_id<=0;
		},
		async changeDichVu(fireEvent) {
			if (this.dichvu_selected == null) {
				this.loaihinh_tb_filter = []
				this.loaihinh_selected = null
				return
			}
			this.loaihinh_tb_filter = this.ds_loaihinh.filter(x => x.dichvuvt_id == this.dichvu_selected)
			if (this.loaihinh_tb_filter.length > 0) {
				this.loaihinh_selected = this.loaihinh_tb_filter[0].loaitb_id
			}

			if(fireEvent==undefined || fireEvent)
	  			this.changeLoaiHinh();
		},
		async changeLoaiHinh() {
			if(this.isNew())
				await this.lay_ds_dv_xuly();
			await this.lay_danhsach_tocdo();
			await this.lay_danhsach_goi_dadv();
		},
		async changeKieuLD() {
			await this.lay_danhsach_tocdo();
		},
		chonNhanVienKhuVuc(data) {
			//console.log('nhanvien_selected', this.nhanvien_selected)
		},
		clickChonDiaChi(value) {
			//1: Địa chỉ TB, 2: Địa chỉ lắp đặt
			this.kieu_chon_diachi = value
			//khởi tạo dữ liệu, địa chỉ khách hàng
			if (this.kieu_chon_diachi == 1) {
				// this.dataDiaChi={ "type": "btnDiaChiTT", "diachi_id": 0, "tinh_id": "26", "quan_id": "1", "phuong_id": 398,
				// "pho_id": 93, "ap_id": 0, "khu_id": 0, "dacdiem_id": 0,
				//  "sonha": "0", "block": "", "tang": "", "sophong": "",
				//  "motathem": "",
				//   "diachi": "", "value": "Địa chỉ thanh toán" };
				Object.assign(this.dataDiaChi, this.dataDiaChiTB)
			} else if (this.kieu_chon_diachi == 2) {
				Object.assign(this.dataDiaChi, this.dataDiaChiLD)
			}
			this.$refs.diaChiModal.showModal()

		},
		onAcceptDiaChi(data) {
			console.log('data', data)
			if (this.kieu_chon_diachi == 1) {
				this.input_diachi_tb = data.diachi
				Object.assign(this.dataDiaChiTB, data)
				//Chọn địa chỉ TB apply luôn qua lắp đặt
				this.input_diachi_ld = data.diachi
				Object.assign(this.dataDiaChiLD, data)

			} else if (this.kieu_chon_diachi == 2) {
				this.input_diachi_ld = data.diachi
				Object.assign(this.dataDiaChiLD, data)
			}

		},
		acceptChonDonVi(data) {
			if (data != null) {
				this.donvi_xyly_selected = data.donvi_id;
			}
		},
		clickKhuVuc() {
			if (this.dataDiaChiLD.quan_id == 0 || this.dataDiaChiLD.phuong_id == 0) {

				this.khuvuc_selected = null
				this.input_khuvuc = ''
				this.$refs.inputKhuVuc.focus()

				this.$toast.warning('Chưa có thông tin về địa chỉ lắp đặt. Bạn hãy kiểm tra lại!')
				return
			}

			this.dataPopupKhuVuc.quan_id = this.dataDiaChiLD.quan_id
			this.dataPopupKhuVuc.phuong_id = this.dataDiaChiLD.phuong_id
			this.dataPopupKhuVuc.pho_id = this.dataDiaChiLD.pho_id
			this.dataPopupKhuVuc.ap_id = this.dataDiaChiLD.ap_id
			this.dataPopupKhuVuc.khu_id = this.dataDiaChiLD.khu_id
			this.dataPopupKhuVuc.dacdiem_id = this.dataDiaChiLD.dacdiem_id

			this.dataPopupKhuVuc.khuvuc_id = this.khuvuc_selected != null ? this.khuvuc_selected.khuvuc_id : 0
			this.dataPopupKhuVuc.donviql_id = this.donvi_xyly_selected != null ? this.donvi_xyly_selected : 0

			this.dataPopupKhuVuc.hdtb_id = (this.hdtb.hdtb_id != undefined && this.hdtb.hdtb_id != null) ? this.hdtb.hdtb_id : 0



			this.$refs.thayDoiKhuVucQuanLyThueBaoModal.showModal()
		},
		async onAcceptKhuVuc(data) {
			console.log('onAcceptKhuVuc', data)
			this.khuvuc_selected = data
			this.input_khuvuc = this.khuvuc_selected.ma_kv
		},
		async changeDonviXL() {

		},
		focusTenTB() {
			this.$nextTick(() => {
				this.$refs.inputTenTB.focus()
			})
		},
		focusDiaChiLD() {
			this.$nextTick(() => {
				this.$refs.inputDiaChiLD.focus()
			})
		},
		focusDiaChiTB() {
			this.$nextTick(() => {
				this.$refs.inputDiaChiTB.focus()
			})
		},
		successUpdateThayDoiKhuVucQuanLy() {
			this.$emit('successUpdateThayDoiKhuVucQuanLy')
		},
		isNumber(evt) {
			evt = (evt) ? evt : window.event;
			var charCode = (evt.which) ? evt.which : evt.keyCode;
			if ((charCode > 31 && (charCode < 48 || charCode > 57))) {
				evt.preventDefault()
			} else {
				return true
			}
		},
		blurInputSoLuong() {
			if (this.input_soluong.trim() == '' || this.input_soluong.trim() == '0') {
				this.input_soluong = '1'
			} else {
				this.input_soluong = Number(this.input_soluong.trim()).toString()
			}
		},
		getData() {
			return {
				MA_TB:this.input_ma_tb.trim(),
				TEN_TB: this.input_ten_tb,
				DIACHI_TB: this.input_diachi_tb,
				DIACHI_LD: this.input_diachi_ld,
				SOLUONG: this.input_soluong,
				DONVI_ID: this.donvi_xyly_selected,
				KIEULD_ID: this.kieu_yc_selected,
				DOITUONG_ID: this.doituong_selected,
				KHUVUC_ID: this.khuvuc_selected,
				DICHVUVT_ID: this.dichvu_selected,
				LOAITB_ID: this.loaihinh_selected,
				TOCDO_ID: this.tocdo_selected,
				GOI_DADV_ID: this.goi_dadv_selected,
				TTHD_ID : 1,
				KIEU_TB : 0,

				dataDiaChiLD: this.dataDiaChiLD,
				dataDiaChiTB: this.dataDiaChiTB,

				dsHDTB_DV:[{
					DONVI_ID:this.donvi_xyly_selected,
					LOAIDV_ID:115,
					KIEUDV_ID:2
				}],
				dsHDTB_KV:[{
					KHUVUC_ID:this.khuvuc_selected!=null?this.khuvuc_selected.khuvuc_id:-1,
					LOAIKV_ID:4,
					KIEUKV_ID:2
				}]
			}
		},
		initDiaChiKH(data) {
			Object.assign(this.dataDiaChiKH, data)
		},
		chuyenTinh(tinh_id){
	  		console.log("Chuyển tỉnh => "+tinh_id);
	  		this.tinh_selected=tinh_id;
	  		this.lay_ds_dv_xuly();
	  		this.lay_danhsach_goi_dadv();
	  		this.lay_nv_banhang_kv();
		},
		setDiaChiLD_TB_Theo_KH(data) {
			Object.assign(this.dataDiaChiKH, data)
			let object = {
				tinh_id: data.tinh_id,
				quan_id: data.quan_id,
				phuong_id: data.phuong_id,
				pho_id: data.pho_id,
				ap_id: data.ap_id,
				khu_id: data.khu_id,
				dacdiem_id: data.dacdiem_id,
				sonha: data.sonha,
				diachi: data.diachi
			}

			Object.assign(this.dataDiaChiLD, object);
			Object.assign(this.dataDiaChiTB, object);

			this.input_diachi_tb = data.diachi
			this.input_diachi_ld = data.diachi

			if(data.tinh_id!=this.tinh_selected){
				this.chuyenTinh(data.tinh_id);
			}else{
				this.lay_nv_banhang_kv();
			}
		},
		async lay_ds_dv_xuly() {
	  		if(this.tinh_selected==null){
				this.donvi_xyly_selected = null;
				return;
		  	}

			var result = []
			let response = await myApi.sp_lay_ds_donvi_xuly(
				this.axios,
				this.tinh_selected,
				{
					"loaitb_id":this.loaihinh_selected,
					"loaikh_id":this.loaiKH.loaikh_id,
					"loaihd_id":this.loaihd_id,
					"kieuld_id":this.kieu_yc_selected,
					"htdh_id":this.htdh_selected,
		        }
			);
			if (response && response.data && response.data.data) {
				this.ds_donvi_xyly = response.data.data;
			}else{
				this.ds_donvi_xyly=[];
				this.donvi_xyly_selected=null;
			}

			if(this.ds_donvi_xyly.length>0){
				if(this.ds_donvi_xyly.filter(x => x.donvi_id == this.donvi_xyly_selected).length==0){
					this.donvi_xyly_selected=null;

					if (this.ds_donvi_xyly.length == 1) {
						this.donvi_xyly_selected = this.ds_donvi_xyly[0].donvi_id;
					} else if (this.ds_donvi_xyly.length > 1) {
						this.$refs.chonDonViModal.showModal();
					}
				}
			}
		},
		async lay_nv_banhang_kv() {
			//
			let data = {
				tinhthicong_id: this.tinh_selected,
				ap_id: this.dataDiaChiLD.ap_id,
				dacdiem_id: this.dataDiaChiLD.dacdiem_id,
				khu_id: this.dataDiaChiLD.khu_id,
				loaikv_id: 4,
				pho_id: this.dataDiaChiLD.pho_id,
				phuong_id: this.dataDiaChiLD.phuong_id,
				quan_id: this.dataDiaChiLD.quan_id,
				tramtc_id: 0
			}
			try {
				let response = await myApi.sp_lay_khuvuc_theo_diadanh(this.axios, data)
				if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
					this.khuvuc_selected = response.data.data[0]
					this.input_khuvuc = this.khuvuc_selected.ma_kv
				} else {
					this.input_khuvuc = ''
				}

			} catch (e) {
				this.$toast.error('Không load được khu vực nhân viên')
			}
		},

		async lay_danhmuc_lapmoi_tt_chung_v7(chuoi) {
			try {
				this.ds_dichvu = []
				this.ds_loaihinh = []
				//this.ds_kieu_yc=[]

				let response = await myApi.lay_danhmuc_lapmoi_tt_chung_v7(this.axios, chuoi)
				if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
					if (response.data.data.loaihinh_tb) {
						this.ds_loaihinh = response.data.data.loaihinh_tb
					}
					if (response.data.data.dichvu_vt) {
						this.ds_dichvu = response.data.data.dichvu_vt;
						this.dichvu_selected = DichVuVienThong.ADSL
					}
					if (this.dichvu_selected) {
						this.changeDichVu()
					}
					if (this.loaihinh_selected) {
						this.changeLoaiHinh()
					}
					if (response.data.data.doituong) {
						this.ds_doituong_tb = response.data.data.doituong
						if (this.ds_doituong_tb.length > 0) {
							//TUNHAN
							this.doituong_selected = 1
						}
					}
				} else {
					this.ds_dichvu = []
					this.ds_loaihinh = []
					//this.ds_kieu_yc=[]
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
		async lay_danhsach_kieu_ld() {
			try {
				this.ds_kieu_yc = []
				let response = await myApi.lay_danhsach_kieu_ld(this.axios)
				if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
					//filter loại hd
					this.ds_kieu_yc = response.data.data.filter(x => x.LOAIHD_ID == this.loaihd_id && (x.KIEULD_ID==750 || x.KIEULD_ID==751))
					if (this.ds_kieu_yc.length > 0) {
						this.kieu_yc_selected = this.ds_kieu_yc[0].KIEULD_ID
					}
				} else {
					this.ds_kieu_yc = []
				}
			} catch (e) {
				if (e.data && e.data.message) {
					this.$toast.error(e.data.message)
				} else if (e.response && e.response.data && e.response.data.message) {
					this.$toast.error(e.response.data.message)
				} else {
					this.$toast.error('Không load được danh sách kiểu lắp đặt')
				}
			} finally {
			}
		},
		async lay_danhsach_tocdo() {
			this.ds_tocdo = [];
			this.tocdo_selected = null;

			try {
				if (this.loaihinh_selected == null || this.kieu_yc_selected == null)
					return;

				let response = await myApi.lay_tocdo_adsl_theo_loaitb(
					this.axios, {
						tinhthicong_id:this.tinh_selected,
						loaitb_id: this.loaihinh_selected
					}
				);
				if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
					this.ds_tocdo = response.data.data;
				} else {
					this.ds_tocdo = [];
				}
			} catch (e) {
				if (e.data && e.data.message) {
					this.$toast.error(e.data.message)
				} else if (e.response && e.response.data && e.response.data.message) {
					this.$toast.error(e.response.data.message)
				} else {
					this.$toast.error('Không load được danh sách tốc độ/gói cước')
				}
			} finally {

			}
		},
		async lay_danhsach_goi_dadv() {
			this.ds_goi_dadv = [];
			this.goi_dadv_selected = null;

	  		if(this.tinh_selected==null)
				return;

			try {
				if (this.loaihinh_selected == null)
					return;

				let response = await myApi.lay_ds_goi_cv_vnp_v1(
					this.axios, {
						tinh_id: this.tinh_selected,
						loaitb_id: this.loaihinh_selected
		  			}
				);

				if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
					this.ds_goi_dadv = response.data.data;
				} else {
					this.ds_goi_dadv = [];
				}
			} catch (e) {
				if (e.data && e.data.message) {
					this.$toast.error(e.data.message)
				} else if (e.response && e.response.data && e.response.data.message) {
					this.$toast.error(e.response.data.message)
				} else {
					this.$toast.error('Không load được danh sách gói đa DV')
				}
			} finally {
			}
		},
		//type MaGD
		async fillData(data) {
			let isChuyenTinh=this.tinh_selected != this.hdtb.tinh_id;
			this.hdtb=data;

			this.tinh_selected = this.hdtb.tinh_id;

			this.input_ten_tb = this.hdtb.ten_tb??'';
			this.input_diachi_tb = this.hdtb.diachi_tb??'';
			this.input_diachi_ld = this.hdtb.diachi_ld??'';
			this.input_ghichu = this.hdtb.ghichu_tb??'';
			this.input_ma_tb = this.hdtb.ma_tb??'';


			if (isChuyenTinh || this.dichvu_selected != this.hdtb.dichvuvt_id) {
				this.dichvu_selected = this.hdtb.dichvuvt_id;
				await this.changeDichVu(false);
			}

			if(isChuyenTinh || this.loaihinh_selected != this.hdtb.loaitb_id){
				this.loaihinh_selected = this.hdtb.loaitb_id
				await this.changeLoaiHinh();
			}

			this.donvi_xyly_selected = null
			if (this.hdtb.donvi_id != undefined && this.hdtb.donvi_id != null) {
				if(this.hdtb.ten_dv!=undefined)
		  			this.ds_donvi_xyly = [{donvi_id:this.hdtb.donvi_id,ten_dv:this.hdtb.ten_dv}];
				else
		  			this.ds_donvi_xyly = [{donvi_id:this.hdtb.donvi_id,ten_dv:this.hdtb.donvi_id+""}];
				this.donvi_xyly_selected = this.hdtb.donvi_id.toString()
			}else{
				this.ds_donvi_xyly=[];
				this.donvi_xyly_selected = null;
			}

			this.kieu_yc_selected = this.hdtb.kieuld_id
			this.input_soluong = this.hdtb.soluong != null ? this.hdtb.soluong.toString() : '0'
			this.doituong_selected = this.hdtb.doituong_id

			this.tocdo_selected = this.hdtb.tocdo_id;

			this.goi_dadv_selected = this.hdtb.goi_dadv_id;
			this.htdh_selected = this.hdtb.httthi_id;

			await this.hienthi_tt_diachi_tb_ld()
			await this.khoitao_khuvuc_nhanvien()
		},
		//Type maKH
		async initInfoMaKH(item) {
			let tinh_kh_id=item.tinhkh_id??item.tinhtt_id;
			this.tinh_selected = tinh_kh_id;

			this.input_ten_tb = item.ten_kh;

			this.doituong_selected = item.doituong_id

			//Đơn vị
			//await this.hienthi_donvi(this.thuebao_id, this.loaidv_id)
			this.chuyenTinh(this.tinh_selected);//Gọi sự kiện chuyển tỉnh để load lại đơn vị theo địa chỉ và danh mục gói

			//khu vực, nhân viên
			await this.khoitao_khuvuc_nhanvien_v1(this.thuebao_id)
		},
		async hienthi_tt_diachi_tb_ld() {
			if(
				this.hdtb.tinh_id==undefined ||
				this.hdtb.tinh_id==null ||
				this.hdtb.hdtb_id==undefined ||
				this.hdtb.hdtb_id==null
			){
				return;
			}

			try {
				this.dataDiaChiTB = {
					diachi_id: 0,
					tinh_id: 0,
					quan_id: 0,
					phuong_id: 0,
					pho_id: 0,
					ap_id: 0,
					khu_id: 0,
					dacdiem_id: 0,
					sonha: '',
					//
					block: '',
					tang: '',
					sophong: '',
					motathem: '',
					diachi: ''
				}
				this.dataDiaChiLD = {
					diachi_id: 0,
					tinh_id: 0,
					quan_id: 0,
					phuong_id: 0,
					pho_id: 0,
					ap_id: 0,
					khu_id: 0,
					dacdiem_id: 0,
					sonha: '',
					//
					block: '',
					tang: '',
					sophong: '',
					motathem: '',
					diachi: ''
				}
				let response = await myApi.lay_ds_diachi_theo_hdtb(this.axios, this.hdtb.tinh_id, this.hdtb.hdtb_id)
				if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data && response.data.data.length > 0) {
					let result = response.data.data[0]
					// Địa chỉ TB
					this.dataDiaChiTB = {
						diachi_id: result.diachi_id != null ? result.diachi_id : 0,
						tinh_id: result.tinh_id != null ? result.tinh_id : 0,
						quan_id: result.quan_id != null ? result.quan_id : 0,
						phuong_id: result.phuong_id != null ? result.phuong_id : 0,
						pho_id: result.pho_id != null ? result.pho_id : 0,
						ap_id: result.ap_id != null ? result.ap_id : 0,
						khu_id: result.khu_id != null ? result.khu_id : 0,
						dacdiem_id: result.dacdiem_id != null ? result.dacdiem_id : 0,
						sonha: result.sonha != null ? result.sonha : '',

						block: (result.hasOwnProperty("block") && result.block != null) ? result.block : '',
						tang: (result.hasOwnProperty("tang") && result.tang != null) ? result.tang : '',
						sophong: (result.hasOwnProperty("phong") && result.phong != null) ? result.phong : '',
						motathem: (result.hasOwnProperty("mota") && result.mota != null) ? result.mota : '',

						diachi: (this.hdtb.hasOwnProperty("diachi_tb") && this.hdtb.diachi_tb != null) ? this.hdtb.diachi_tb : '',
					}
					//Địa chỉ LD
					this.dataDiaChiLD = {
						diachi_id: result.diachild_id != null ? result.diachild_id : 0,
						tinh_id: result.tinhld_id != null ? result.tinhld_id : 0,
						quan_id: result.quanld_id != null ? result.quanld_id : 0,
						phuong_id: result.phuongld_id != null ? result.phuongld_id : 0,
						pho_id: result.phold_id != null ? result.phold_id : 0,
						ap_id: result.apld_id != null ? result.apld_id : 0,
						khu_id: result.khuld_id != null ? result.khuld_id : 0,
						dacdiem_id: result.dacdiemld_id != null ? result.dacdiemld_id : 0,
						sonha: result.sonha_ld != null ? result.sonha_ld : '',

						block: (result.hasOwnProperty("blockld") && result.blockLd != null) ? result.blockLd : '',
						tang: (result.hasOwnProperty("tangld") && result.tangLd != null) ? result.tangLd : '',
						sophong: (result.hasOwnProperty("phongld") && result.phongLd != null) ? result.phongLd : '',
						motathem: (result.hasOwnProperty("motald") && result.motaLd != null) ? result.motaLd : '',

						diachi: (this.hdtb.hasOwnProperty("diachi_ld") && this.hdtb.diachi_ld != null) ? this.hdtb.diachi_ld : '',
					}
				}
			} catch (e) {
				if (e.data && e.data.message) {
					this.$toast.error(e.data.message)
				} else if (e.response && e.response.data && e.response.data.message) {
					this.$toast.error(e.response.data.message)
				} else {
					this.$toast.error('Không load được thông tin địa chỉ TB, LD')
				}
			}
		},
		async hienthi_diachi_theo_dbtbid(thuebao_id) {
			try {
				this.dataDiaChiTB = {
					diachi_id: 0,
					tinh_id: 0,
					quan_id: 0,
					phuong_id: 0,
					pho_id: 0,
					ap_id: 0,
					khu_id: 0,
					dacdiem_id: 0,
					sonha: '',
					//
					block: '',
					tang: '',
					sophong: '',
					motathem: '',
					diachi: ''
				}
				this.dataDiaChiLD = {
					diachi_id: 0,
					tinh_id: 0,
					quan_id: 0,
					phuong_id: 0,
					pho_id: 0,
					ap_id: 0,
					khu_id: 0,
					dacdiem_id: 0,
					sonha: '',
					//
					block: '',
					tang: '',
					sophong: '',
					motathem: '',
					diachi: ''
				}
				let response = await myApi.lay_ds_diachi_theo_dbtbid(this.axios, thuebao_id)
				if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data && response.data.data.length > 0) {
					let result = response.data.data[0]
					// Địa chỉ TB
					this.dataDiaChiTB = {
						diachi_id: result.diachi_id != null ? result.diachi_id : 0,
						tinh_id: result.tinh_id != null ? result.tinh_id : 0,
						quan_id: result.quan_id != null ? result.quan_id : 0,
						phuong_id: result.phuong_id != null ? result.phuong_id : 0,
						pho_id: result.pho_id != null ? result.pho_id : 0,
						ap_id: result.ap_id != null ? result.ap_id : 0,
						khu_id: result.khu_id != null ? result.khu_id : 0,
						dacdiem_id: result.dacdiem_id != null ? result.dacdiem_id : 0,
						sonha: result.sonha != null ? result.sonha : '',

						block: (result.hasOwnProperty("block") && result.block != null) ? result.block : '',
						tang: (result.hasOwnProperty("tang") && result.tang != null) ? result.tang : '',
						sophong: (result.hasOwnProperty("phong") && result.phong != null) ? result.phong : '',
						motathem: (result.hasOwnProperty("mota") && result.mota != null) ? result.mota : '',

						diachi: result.diachi != null ? result.diachi : '',
					}
					//Địa chỉ LD
					this.dataDiaChiLD = {
						diachi_id: result.diachild_id != null ? result.diachild_id : 0,
						tinh_id: result.tinhld_id != null ? result.tinhld_id : 0,
						quan_id: result.quanld_id != null ? result.quanld_id : 0,
						phuong_id: result.phuongld_id != null ? result.phuongld_id : 0,
						pho_id: result.phold_id != null ? result.phold_id : 0,
						ap_id: result.apld_id != null ? result.apld_id : 0,
						khu_id: result.khuld_id != null ? result.khuld_id : 0,
						dacdiem_id: result.dacdiemld_id != null ? result.dacdiemld_id : 0,
						sonha: result.sonha_ld != null ? result.sonha_ld : '',

						block: (result.hasOwnProperty("blockLd") && result.blockLd != null) ? result.blockLd : '',
						tang: (result.hasOwnProperty("tangLd") && result.tangLd != null) ? result.tangLd : '',
						sophong: (result.hasOwnProperty("phongLd") && result.phongLd != null) ? result.phongLd : '',
						motathem: (result.hasOwnProperty("motaLd") && result.motaLd != null) ? result.motaLd : '',

						diachi: result.diachi_ld != null ? result.diachi_ld : '',
					}
				}
			} catch (e) {
				if (e.data && e.data.message) {
					this.$toast.error(e.data.message)
				} else if (e.response && e.response.data && e.response.data.message) {
					this.$toast.error(e.response.data.message)
				} else {
					this.$toast.error('Không load được thông tin địa chỉ TB, LD')
				}
			}
		},
		//từ ds
		async khoitao_khuvuc_nhanvien() {
			this.input_khuvuc = ''
			this.nhanvien_selected = null
			try {
				this.khuvuc_selected = null
				let responseKhuVuc = await myApi.lay_ds_khuvuc_theo_hdtb(this.axios, this.hdtb.tinh_id, this.hdtb.hdtb_id)
				if (responseKhuVuc && responseKhuVuc.data && responseKhuVuc.data.error_code && responseKhuVuc.data.error_code == 'BSS-00000000' && responseKhuVuc.data.data && responseKhuVuc.data.data.length > 0) {
					this.khuvuc_selected = responseKhuVuc.data.data[0]
					this.input_khuvuc = this.khuvuc_selected.ma_kv
				} else {
					this.input_khuvuc = ''
				}
			} catch (e) {
				if (e.data && e.data.message) {
					this.$toast.error(e.data.message)
				} else if (e.response && e.response.data && e.response.data.message) {
					this.$toast.error(e.response.data.message)
				} else {
					this.$toast.error('Không load được khu vực, nhân viên')
				}
			} finally {
			}

		},
		//từ enter makh
		async khoitao_khuvuc_nhanvien_v1(thuebao_id) {
			this.input_khuvuc = ''
			try {
				this.khuvuc_selected = null
				this.nhanvien_selected = null
				let responseKhuVuc = await myApi.lay_ds_khuvuc_theo_thuebao_id(this.axios, this.tinh_selected, thuebao_id);
				if (responseKhuVuc && responseKhuVuc.data && responseKhuVuc.data.error_code && responseKhuVuc.data.error_code == 'BSS-00000000' && responseKhuVuc.data.data && responseKhuVuc.data.data.length > 0) {
					this.khuvuc_selected = responseKhuVuc.data.data[0]
					this.input_khuvuc = this.khuvuc_selected.ma_kv
				} else {
					this.input_khuvuc = ''
				}
			} catch (e) {
				console.log(e)
				if (e.data && e.data.message) {
					this.$toast.error(e.data.message)
				} else if (e.response && e.response.data && e.response.data.message) {
					this.$toast.error(e.response.data.message)
				} else {
					this.$toast.error('Không load được khu vực')
				}
			} finally {

			}
		},
		async hienthi_donvi(thuebao_id, loaidv_id) {
			try {
				let response = await myApi.sp_lay_ds_donvi_thicong(this.axios, thuebao_id, loaidv_id)
				if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data && response.data.data.length > 0) {
					let result = response.data.data[0]
					this.donvi_xyly_selected = result.donvi_id.toString()
				} else {
					this.donvi_xyly_selected = null
				}
			} catch (e) {
				this.donvi_xyly_selected = null
				if (e.data && e.data.message) {
					this.$toast.error(e.data.message)
				} else if (e.response && e.response.data && e.response.data.message) {
					this.$toast.error(e.response.data.message)
				} else {
					this.$toast.error('Không load được đơn vị')
				}
			} finally {
			}
		},
		async clear() {
			this.hdtb={};
			this.input_ten_tb = '';
			this.input_diachi_tb = '';
			this.input_diachi_ld = '';
			this.input_khuvuc = '';
			this.input_soluong = '1';
			this.input_ghichu = '';
			this.input_ma_tb = '';

			this.khuvuc_selected = null;

			this.doituong_selected = 1;
			this.kieu_yc_selected = this.ds_kieu_yc.length > 0 ? this.ds_kieu_yc[0].KIEULD_ID : 0;
			this.nhanvien_selected = null;
			this.thuebao_id = 0;
			this.tocdo_selected = null;
			this.goi_dadv_selected = null;
			this.htdh_selected = null;

			this.ds_donvi_xyly=[];
			this.donvi_xyly_selected=null;

			this.dataDiaChi = {
				diachi_id: 0,
				tinh_id: 0,
				quan_id: 0,
				phuong_id: 0,
				pho_id: 0,
				ap_id: 0,
				khu_id: 0,
				dacdiem_id: 0,
				sonha: '',
				//
				block: '',
				tang: '',
				sophong: '',
				motathem: '',
				diachi: ''
			};

			this.dataDiaChiTB = {
				diachi_id: 0,
				tinh_id: 0,
				quan_id: 0,
				phuong_id: 0,
				pho_id: 0,
				ap_id: 0,
				khu_id: 0,
				dacdiem_id: 0,
				sonha: '',
				//
				block: '',
				tang: '',
				sophong: '',
				motathem: '',
				diachi: ''
			};
			this.dataDiaChiLD = {
				diachi_id: 0,
				tinh_id: 0,
				quan_id: 0,
				phuong_id: 0,
				pho_id: 0,
				ap_id: 0,
				khu_id: 0,
				dacdiem_id: 0,
				sonha: '',
				//
				block: '',
				tang: '',
				sophong: '',
				motathem: '',
				diachi: ''
			};
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
			};
		},
		async initDuLieu() {
			await this.lay_danhmuc_lapmoi_tt_chung_v7('DOITUONG|DICHVU_VT|LOAIHINH_TB');
			await this.lay_danhsach_kieu_ld();
		}
	}
}
</script>
<style>
.btn-second {
	background-color: #fff;
	border-color: #0176FF;
	color: #0176FF;
	font-weight: 600;
}
.btn-second:hover {
	background-color: #0176FF;
	border-color: #0176FF;
	color: #fff;
	font-weight: 600;
}
.btn-outline-secondary {
	background-color: #fff;
	color: #6c757d;
	border-color: #6c757d;
}
.btn-outline-secondary:hover {
	background-color: #6c757d;
	color: #fff;
	border-color: #6c757d;
}
.multiselect, .multiselect__input, .multiselect__single {
	font-size: 1rem !important;
}
.multiselect__tags{
	min-height: 32px !important;
	border: 1px solid #ced4da !important;
	padding: 6px 32px 0 6px;
	border-radius: 4px;
}
.multiselect__placeholder {
	margin-bottom: 5px!important;
	padding-top: 0px!important;
}
.multiselect__select{
	top: 0px!important;
	padding: 10px 9px!important;
}
.multiselect--disabled{
  background: none!important;
}
.multiselect--disabled .multiselect__current, .multiselect--disabled .multiselect__select{
  background: none!important;
}
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
</style>
