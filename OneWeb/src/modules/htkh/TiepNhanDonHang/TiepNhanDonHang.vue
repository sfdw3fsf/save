<template src='./TiepNhanDonHang.html'></template>
<style scoped src='./TiepNhanDonHang.scss'></style>

<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import ThongTinKhachHang from './components/ThongTinKhachHang.vue'
import ThongTinThueBao from './components/ThongTinThueBao.vue'
import ThongTinThanhToan from './components/ThongTinThanhToan.vue'
import ThongTinKhac from './components/ThongTinKhac.vue'
import {
	LoaiHopDong,
	KieuLapDat
} from './ThamSo'
import myApi from './api'
import ThongTinHenKhachHangModal from './popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue'
import ChonCongTacVienModal from './popups/ChonCongTacVien/ChonCongTacVienModal.vue'
import ChonNguoiGTModal from './popups/ChonNguoiGT/ChonNguoiGTModal.vue'
import moment from 'moment'
import KDataGrid from '@/components/kylq_components/KDataGrid'

export default {
	name: 'TiepNhanDonHang',
	components: {
		Footer,
		breadcrumb,
		KTableV3,
		ActionTop,
		ThongTinKhachHang,
		ThongTinThueBao,
		ThongTinThanhToan,
		ThongTinKhac,
		ThongTinHenKhachHangModal,
		ChonCongTacVienModal,
		ChonNguoiGTModal,
		KDataGrid
	},
	data() {
		return {
			header: {
				title: "TIẾP NHẬN YÊU CẦU ĐƠN HÀNG - KHẢO SÁT",
				list: [{
						name: "Lập hợp đồng",
						link: {
							name: "Ui.cards"
						},
						active: false
					},
					{
						name: "Lắp đặt mới",
						link: {
							name: "Ui.buttons"
						},
						active: true
					},
				],
			},
			actions: [{
					id: 'layds',
					name: 'Lấy DS',
					active: true,
					icon_class: 'one-file-attach1',
					tooltip: 'Lấy danh sách mới'
				},
				{
					id: 'nhapmoi',
					name: 'Nhập mới',
					active: true,
					icon_class: 'one-file-plus',
					tooltip: 'Nhập mới'
				},
				{
					id: 'ghilai',
					name: 'Ghi lại',
					active: true,
					icon_class: 'one-save',
					tooltip: 'Ghi lại'
				},
				{
					id: 'huy',
					name: 'Huỷ',
					active: true,
					icon_class: 'nc-icon-glyph ui-1_circle-remove',
					tooltip: 'Huỷ'
				},
				{
					id: 'xoa_hd',
					name: 'xóa',
					active: true,
					icon_class: 'one-trash',
					tooltip: 'Xóa hợp đồng'
				},
				{
					id: 'them_yc',
					name: 'Thêm YC',
					active: true,
					icon_class: 'one-mobile-plus',
					tooltip: 'Thêm yêu cầu'
				},
				{
					id: 'xoa_yc',
					name: 'xóa YC',
					active: false,
					icon_class: 'one-phone-remove',
					tooltip: 'Xóa yêu cầu'
				},
				{
					id: 'hen_ld',
					name: 'Hẹn LĐ',
					active: true,
					icon_class: 'one-calendar-check',
					tooltip: 'Hẹn lắp đặt'
				},
				{
					id: 'giaophieu',
					name: 'Giao phiếu',
					active: false,
					icon_class: 'one-file-check',
					tooltip: 'Giao phiếu'
				}
			],
			columns: [
				{
					fieldName: 'ten_tinh',
					headerText: 'Tỉnh/thành',
					allowFiltering: true
				},{
					fieldName: 'ma_gd',
					headerText: 'Mã giao dịch',
					allowFiltering: true
				},
				{
					fieldName: 'ngay_yc',
					headerText: 'Ngày yêu cầu',
					allowFiltering: true
				},
				{
					fieldName: 'ma_tb',
					headerText: 'Số máy/Account',
					allowFiltering: true
				},
				{
					fieldName: 'ten_tb',
					headerText: 'Tên thuê bao',
					allowFiltering: true
				},
				{
					fieldName: 'diachi_ld',
					headerText: 'Địa chỉ lắp đặt',
					allowFiltering: true
				},
				{
					fieldName: 'loaihinh_tb',
					headerText: 'Loại hình',
					allowFiltering: true
				},
				{
					fieldName: 'ten_kieuld',
					headerText: 'Kiểu yêu cầu',
					allowFiltering: true
				},
				{
					fieldName: 'soluong',
					headerText: 'Số lượng',
					allowFiltering: true
				}
			],
			dataSources: [],
			checkCongTacVien: false,
			checkNguoiGT: false,
			loaihd_id: LoaiHopDong.TUVAN_DV,
			rowSelected: null,
			itemMaKHSelected: null,
			ctv: {
				ctv_id: null,
				ten_ctv: '',
				ma_ctv: '',
				loainv_id: -1,
				donvi_id: -1
			},
			nguoi_gt: {
				nguoi_gt_id: null,
				ten_nguoi_gt: '',
				ma_nguoi_gt: '',
				loainv_id: -1,
				donvi_id: -1
			},
			thuebao_id: 0,
			khachhang_id: 0,
			loaidv_id: 2,
			hdkh_id: 0,
			hdtb_id: 0,
			hdtt_id: 0,
			rowSelectedId: -1
		}
	},
	computed: {
		hdtb() {
			return this.rowSelected != null ? this.rowSelected : {}
		}
	},
	methods: {
		onActionClick(action) {
			if (action.id == 'layds') {
				this.clear()
				this.hienthi_danhsach_hdtb()
			} else if (action.id == 'nhapmoi') {
				this.setActiveActions(1)
			} else if (action.id == 'ghilai') {
				this.ghiDuLieu(true);
			} else if (action.id == 'huy') {
				if (this.rowSelected != null && this.dataSources.length > 0) {
					this.onGrid_SelectedRow(this.rowSelected)
				} else {
					this.setActiveActions(0)
				}
			} else if (action.id == 'xoa_hd') {
				if (this.rowSelected == null || this.dataSources.length == 0) {
					return
				}
				this.$confirm('Bạn thật sự muốn xóa thông tin không ?', 'Thông báo', {
					confirmButtonText: 'Đồng ý',
					cancelButtonText: 'Huỷ'
				}).then(() => {
					this.xoa_hdkh(this.rowSelected.tinh_id,this.rowSelected.hdkh_id)
				})

			} else if (action.id == 'them_yc') {
				this.themYC()
			} else if (action.id == 'xoa_yc') {
				this.xoaYC()
			} else if (action.id == 'hen_ld') {
				this.$refs.thongTinHenKhachHangModal.showModal()
			} else if (action.id == 'giaophieu') {
				this.giaoPhieu()
			}
		},
		setActiveActions(kieu) {
			this.actions.forEach((item) => {
				if (item.id != 'hen_ld' && item.id != 'giaophieu' && item.id != 'layds') {
					item.active = false
				}
			})
			if (kieu == -1) { //bắt đầu
				this.actions[this.actions.findIndex(x => x.id == 'ghilai')].active = true
				this.actions[this.actions.findIndex(x => x.id == 'huy')].active = true
			} else if (kieu == 0) { //bắt đầu
				//this.$refs.thongtinkhachhang.focusMaGD()
				this.actions[this.actions.findIndex(x => x.id == 'nhapmoi')].active = true
				this.actions[this.actions.findIndex(x => x.id == 'ghilai')].active = false
			} else if (kieu == 1) { //thêm mới
				//this.$refs.thongtinkhachhang.focusTenKH()
				this.actions[this.actions.findIndex(x => x.id == 'ghilai')].active = true
				this.actions[this.actions.findIndex(x => x.id == 'huy')].active = true
				this.clear()

			} else if (kieu == 2) { //Huỷ
				//this.$refs.thongtinkhachhang.focusMaGD()
				this.actions[this.actions.findIndex(x => x.id == 'nhapmoi')].active = true
				this.actions[this.actions.findIndex(x => x.id == 'xoa_hd')].active = true
				this.clear()
			} else if (kieu == 3) { //edit
				//this.$refs.thongtinkhachhang.focusMaGD()
				this.actions[this.actions.findIndex(x => x.id == 'nhapmoi')].active = true
				this.actions[this.actions.findIndex(x => x.id == 'ghilai')].active = true
				this.actions[this.actions.findIndex(x => x.id == 'huy')].active = true
				this.actions[this.actions.findIndex(x => x.id == 'xoa_hd')].active = true

				this.actions[this.actions.findIndex(x => x.id == 'giaophieu')].active = true
				this.actions[this.actions.findIndex(x => x.id == 'hen_ld')].active = true
				this.actions[this.actions.findIndex(x => x.id == 'them_yc')].active = true
				this.actions[this.actions.findIndex(x => x.id == 'xoa_yc')].active = true
			}
		},
		async getThongTinNguoiDung(){
			return {
				"NHANVIEN_ID": Number(await this.$root.token.getNhanVienID()),
				"IP_CN": await this.$root.token.getIP(),
				"MAY_CN": await this.$root.token.getMachine(),
				"NGUOI_CN": await this.$root.token.getUserName()
			}
		},
		clear() {
			this.thuebao_id = 0
			this.khachhang_id = 0
			this.hdkh_id = 0
			this.hdtb_id = 0
			this.hdtt_id = 0
			this.itemMaKHSelected = null

			//clear dữ liệu form
			this.$refs.thongtinkhachhang.clear()
			this.$refs.thongtinthuebao.clear()
			this.$refs.thongtinthanhtoan.clear()
			this.$refs.thongtinkhac.clear()

			this.ctv = {
				ctv_id: null,
				ten_ctv: '',
				ma_ctv: '',
				loainv_id: -1,
				donvi_id: -1
			}
			this.nguoi_gt = {
				nguoi_gt_id: null,
				ten_nguoi_gt: '',
				ma_nguoi_gt: '',
				loainv_id: -1,
				donvi_id: -1
			}

			this.checkNguoiGT = false
			this.checkCongTacVien = false

			this.actions[this.actions.findIndex(x => x.id == 'giaophieu')].active = false
			this.actions[this.actions.findIndex(x => x.id == 'hen_ld')].active = false
			this.actions[this.actions.findIndex(x => x.id == 'ghilai')].active = true
			this.actions[this.actions.findIndex(x => x.id == 'xoa_yc')].active = false
		},
		async searchMaGD(ma_gd) {
			if (ma_gd != '') {
				let nhanvien_id = this.$root.token.getNhanVienID()
				let data = await this.sp_lay_ds_donhang(this.loaihd_id, nhanvien_id, ma_gd)
				if (data.length > 0) {
					let result = data.filter(x => x.ma_gd == ma_gd)
					if (result.length > 0) {
						//focus row đầu tiên
						this.actions[this.actions.findIndex(x => x.id == 'giaophieu')].active = true
						this.actions[this.actions.findIndex(x => x.id == 'hen_ld')].active = true
						//active xoa_yc khi row lớn hơn 1, ==1 xóa hd
						if (result.length > 1) {
							this.actions[this.actions.findIndex(x => x.id == 'xoa_yc')].active = true
						} else {
							this.actions[this.actions.findIndex(x => x.id == 'xoa_yc')].active = false
						}
						this.dataSources = [].concat(result)
					} else {
						this.$toast.error('Không tìm thấy thông tin yêu cầu')
					}
				} else {
					this.setActiveActions(1);
					this.$toast.error('Không tìm thấy thông tin yêu cầu')
				}
			} else {
				this.setActiveActions(0)
			}
		},
		searchMaKH(ma_kh) {
			if (ma_kh != '') {
				this.tim_khachhang_theo_ma_kh(ma_kh)
			}
		},
		onEnterTenKH(tenKh) {
			if(
				this.$refs.thongtinthuebao.input_ten_tb == undefined ||
				this.$refs.thongtinthuebao.input_ten_tb == null ||
				this.$refs.thongtinthuebao.input_ten_tb.trim().length==0
			)
				this.$refs.thongtinthuebao.input_ten_tb = tenKh;
		},
		async onBlurSoGT(so_gt) {
		},
		async confirm(message, title) {
			try {
				let result = await this.$confirm(message, title, {
					confirmButtonText: 'Đồng ý',
					cancelButtonText: 'Hủy'
				})
				return 1
			} catch (e) {
				return 0
			}
		},
		onGrid_DataBound: function (args) {
			let rows = this.$refs.gridDs.grid.getRows();
			if(rows==0)return;

			if(this.$refs.gridDs.focusRowHand!=undefined && this.$refs.gridDs.focusRowHand>0){
				this.$refs.gridDs.selectRow(this.$refs.gridDs.focusRowHand);
			}else{
				this.$refs.gridDs.selectRow(0);
			}
		},
		async onGrid_SelectedRow(item) {
			if (!item) {
				this.clear()
				this.rowSelected = null
				return
			}

			try{
				this.loading(true);
				this.clear();
				this.rowSelected = item;
				this.thuebao_id = this.rowSelected.thuebao_id != null ? this.rowSelected.thuebao_id : 0

				this.hdkh_id = this.rowSelected.hdkh_id != null ? this.rowSelected.hdkh_id : 0
				this.hdtb_id = this.rowSelected.hdtb_id != null ? this.rowSelected.hdtb_id : 0
				this.hdtt_id = this.rowSelected.hdtt_id != null ? this.rowSelected.hdtt_id : 0

				this.setActiveActions(3)

				//init dữ liệu
				this.ctv.ctv_id = this.rowSelected.ctv_id
				this.nguoi_gt.nguoi_gt_id = this.rowSelected.nhanviengt_id

				//init người GT, CTV, form Thông tin khách hàng/Thông tin thuê bao
				await Promise.all(
					[
						this.intNguoiGT_CTV(),
						this.$refs.thongtinkhachhang.fillData(item),
						this.$refs.thongtinthuebao.fillData(item),
						this.$refs.thongtinthanhtoan.fillData(item),
						this.$refs.thongtinkhac.fillData(item)
					]
				);

			}catch(e){

			}finally {
				this.loading(false);
			}
		},
		changeCheckCTV() {
			if (this.checkCongTacVien) {
				this.$refs.chonCongTacVienModal.showModal()
			}
		},
		changeCheckNguoiGT() {
			if (this.checkNguoiGT) {
				this.$refs.chonNguoiGTModal.showModal()
			}
		},
		acceptCTV(data) {
			Object.assign(this.ctv, data)
			this.ctv.ten_ctv = this.ctv.ma_ctv + ' - ' + this.ctv.ten_ctv
		},
		acceptNguoiGT(data) {
			Object.assign(this.nguoi_gt, data)
			this.nguoi_gt.ten_nguoi_gt = this.nguoi_gt.ma_nguoi_gt + ' - ' + this.nguoi_gt.ten_nguoi_gt
		},
		successUpdateThayDoiKhuVucQuanLy() {
			this.hienthi_danhsach_hdtb()
		},
		async intNguoiGT_CTV() {
			try {
				//người giới thiệu
				if (this.rowSelected.nhanviengt_id != null) {
					let responseNguoiGT = await myApi.fn_lay_thongtin_nhanvien(this.axios, this.rowSelected.nhanviengt_id)
					if (responseNguoiGT && responseNguoiGT.data && responseNguoiGT.data.error_code && responseNguoiGT.data.error_code == 'BSS-00000000' && responseNguoiGT.data.data) {
						this.nguoi_gt.ten_nguoi_gt = responseNguoiGT.data.data
						this.checkNguoiGT = true
					} else {
						this.nguoi_gt.ten_nguoi_gt = ''
						this.checkNguoiGT = false
					}
				} else {
					this.nguoi_gt.ten_nguoi_gt = ''
					this.checkNguoiGT = false
				}
				//Cộng tác viên
				if (this.rowSelected.ctv_id != null) {
					let responseCTV = await myApi.fn_lay_thongtin_nhanvien(this.axios, this.rowSelected.ctv_id)
					if (responseCTV && responseCTV.data && responseCTV.data.error_code && responseCTV.data.error_code == 'BSS-00000000' && responseCTV.data.data) {
						this.ctv.ten_ctv = responseCTV.data.data
						this.checkCongTacVien = true
					} else {
						this.ctv.ten_ctv = ''
						this.checkCongTacVien = false
					}
				} else {
					this.ctv.ten_ctv = ''
					this.checkCongTacVien = false
				}
			} catch (e) {
				if (e.data && e.data.message) {
					this.$toast.error(e.data.message)
				} else if (e.response && e.response.data && e.response.data.message) {
					this.$toast.error(e.response.data.message)
				} else {
					this.$toast.error('Không load được thông tin người GT, CTV')
				}
			} finally {
			}
		},
		xoaYC() {
			if (this.rowSelected == null || this.dataSources.length == 0) {
				return
			}
			this.$confirm('Bạn thật sự muốn xóa thuê bao không ?', 'Thông báo', {
				confirmButtonText: 'Đồng ý',
				cancelButtonText: 'Huỷ'
			}).then(() => {
				this.xoa_hdtb(this.rowSelected.tinh_id,this.rowSelected.hdtb_id)
			})
		},
		async giaoPhieu() {
			let thongtin_kh = this.$refs.thongtinkhachhang.getData()
			let thongtin_tb = this.$refs.thongtinthuebao.getData()
			if (!this.actions[this.actions.findIndex(x => x.id == 'nhapmoi')].active) {
				this.$toast.error('Hãy ghi lại đơn hàng trước khi giao đi.')
				return
			}

			if (thongtin_kh.MA_GD.trim() == '') {
				this.$toast.error('Không có thông tin mã giao dịch')
				return
			}

			this.$confirm('Giao đơn hàng sang bộ phận tiếp theo?', 'Thông báo', {
				confirmButtonText: 'Đồng ý',
				cancelButtonText: 'Huỷ'
			}).then(async () => {
				if(await this.ghiDuLieu(false)==true){//Cập nhật trước rồi mới call API giao phiếu
					await this.giaophieu_hdtb(
						this.rowSelected.tinh_id,
						this.rowSelected.hdtb_id,
						await this.getThongTinNguoiDung()
					);
				}
			}).catch(() => {});
		},
		async sp_lay_ds_makh_theo_sogt(so_gt, hdkh_id) {
			try {
				let response = await myApi.sp_lay_ds_makh_theo_sogt(this.axios, so_gt, hdkh_id)
				if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
					return response.data.data
				} else {
					return []
				}
			} catch (e) {
				return []
			}
		},
		async ghiDuLieu(
			reload,/*reload=true||undefined: Thông báo thành công và load lại lưới*/
			mode/*mode=THEM_YC: Chỉ tạo mới HDTB*/
		) {
			//
			let is_insert = this.actions[this.actions.findIndex(x => x.id == 'nhapmoi')].active ? false : true;
			let action = is_insert?0:1;
			if(!is_insert && mode != undefined && mode=='THEM_YC')
				action=2;//Chỉ tạo mới HDTB


			//check dữ liệu
			if(action==0 || action==1)
				if (!await this.kiemtra_dl_khachhang()) return;

			if (!await this.kiemtra_dl_thuebao()) return;
			//
			let thongtin_kh = this.$refs.thongtinkhachhang.getData()
			let thongtin_tb = this.$refs.thongtinthuebao.getData()
			let thongtin_tt = this.$refs.thongtinthanhtoan.getData()
			let thongtin_khac = this.$refs.thongtinkhac.getData()

			//nhét thêm thông tin
			thongtin_kh.HDKH_ID = this.hdkh_id
			thongtin_kh.KHACHHANG_ID = this.khachhang_id != 0 ? this.khachhang_id : null
			thongtin_kh.DONVI_ID = this.$root.token.getDonViID()
			thongtin_kh.NHANVIEN_ID = this.$root.token.getNhanVienID()
			thongtin_kh.KHLON_ID = 1
			thongtin_kh.LOAIHD_ID = this.loaihd_id
			thongtin_kh.NGUOI_CN = this.$root.token.getUserName()
			thongtin_kh.NGAY_CN = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
			thongtin_kh.CTV_ID = this.checkCongTacVien ? (this.ctv.ctv_id != null ? this.ctv.ctv_id : this.$root.token.getNhanVienID()) : this.$root.token.getNhanVienID()
			thongtin_kh.NHANVIENGT_ID = this.checkNguoiGT ? (this.nguoi_gt.nguoi_gt_id != null ? this.nguoi_gt.nguoi_gt_id : 0) : 0

			thongtin_tb.HDTB_ID=this.hdtb_id;
			thongtin_tb.THUEBAO_ID=0;

			thongtin_tt.HDTT_ID=this.hdtt_id;

			let diachikh = {
				DIACHI_ID : (thongtin_kh != null && thongtin_kh.diachi_kh != null)? thongtin_kh.diachi_kh.diachi_id:0,
				HDKH_ID : this.hdkh_id,
				TINH_ID : thongtin_kh.dataDiaChiKH.tinh_id,
				QUAN_ID : thongtin_kh.dataDiaChiKH.quan_id,
				PHUONG_ID : thongtin_kh.dataDiaChiKH.phuong_id,
				PHO_ID : thongtin_kh.dataDiaChiKH.pho_id,
				AP_ID : thongtin_kh.dataDiaChiKH.ap_id,
				KHU_ID : thongtin_kh.dataDiaChiKH.khu_id,
				SONHA : thongtin_kh.dataDiaChiKH.sonha,
				DIACHI : thongtin_kh.dataDiaChiKH.diachi
			};

			let diachitb = {
				DIACHI_ID : (thongtin_tb != null && thongtin_tb.dataDiaChiTB != null)? thongtin_tb.dataDiaChiTB.diachi_id:0,
				HDTB_ID : this.hdtb_id,
				TINH_ID : thongtin_tb.dataDiaChiTB.tinh_id,
				QUAN_ID : thongtin_tb.dataDiaChiTB.quan_id,
				PHUONG_ID : thongtin_tb.dataDiaChiTB.phuong_id,
				PHO_ID : thongtin_tb.dataDiaChiTB.pho_id,
				AP_ID : thongtin_tb.dataDiaChiTB.ap_id,
				KHU_ID : thongtin_tb.dataDiaChiTB.khu_id,
				SONHA : thongtin_tb.dataDiaChiTB.sonha,
				DIACHI : thongtin_tb.dataDiaChiTB.diachi
			};

			let diachild = {
				DIACHI_ID : (thongtin_tb != null && thongtin_tb.dataDiaChiLD != null)? thongtin_tb.dataDiaChiLD.diachi_id:0,
				HDTB_ID : this.hdtb_id,
				TINH_ID : thongtin_tb.dataDiaChiLD.tinh_id,
				QUAN_ID : thongtin_tb.dataDiaChiLD.quan_id,
				PHUONG_ID : thongtin_tb.dataDiaChiLD.phuong_id,
				PHO_ID : thongtin_tb.dataDiaChiLD.pho_id,
				AP_ID : thongtin_tb.dataDiaChiLD.ap_id,
				KHU_ID : thongtin_tb.dataDiaChiLD.khu_id,
				SONHA : thongtin_tb.dataDiaChiLD.sonha,
				DIACHI : thongtin_tb.dataDiaChiLD.diachi
			};


			////////////
			let data = {
				"ACTION":action, //Thêm mới HĐ/cập nhật HĐ/Thêm mới HĐTB vào HĐKH hiện tại
				"HD_KHACHHANG": thongtin_kh,
				"HD_THANHTOAN": thongtin_tt,
				"HD_THUEBAO": thongtin_tb,
				"THONGTIN_KHAC": thongtin_khac,

				"DIACHI_HDKH":diachikh,
				"DIACHI_HDTB":diachitb,
				"DIACHI_HDTB_LD":diachild,

				"DS_HDTB_DV": thongtin_tb.dsHDTB_DV,
				"DS_HDTB_KV": thongtin_tb.dsHDTB_KV,
				"THONGTIN_NGUOIDUNG":await this.getThongTinNguoiDung()
			}

			return this.fn_lap_hopdong_donhang(
				{
					tinhthicong_id:is_insert?this.$refs.thongtinkhachhang.tinh_selected:this.rowSelected.tinh_id,
					jsondata:JSON.stringify(data)
				},
				reload
			);
		},
		async themYC() {
			this.ghiDuLieu(true,'THEM_YC');
		},
		Data_Changed(args) {
			this.loading(true);
			switch (args.action) {
				case 'CHON_TINH':
					this.$refs.thongtinthuebao.chuyenTinh(args.data.tinh_id);
					break;
				case 'CHON_LOAI_KH':
					this.$refs.thongtinthuebao.loaiKH = args.data;
					this.$refs.thongtinthuebao.lay_ds_dv_xuly();
					break;
				case 'CHON_DIACHI_KH':
					this.$refs.thongtinthuebao.setDiaChiLD_TB_Theo_KH(args.data);
					break;
				case 'CHON_HTDH':
					this.$refs.thongtinthuebao.htdh_selected = args.data.htdh_id;
					this.$refs.thongtinthuebao.lay_ds_dv_xuly();
					break;
			}
			this.loading(false);
		},
		initDiaChiKH(data) {
			this.$refs.thongtinthuebao.initDiaChiKH(data)
		},
		async kiemtra_dl_khachhang() {
			let thongtin_kh = this.$refs.thongtinkhachhang.getData()
			if (thongtin_kh.SO_DT.trim() == '') {
				this.$toast.error('Bạn chưa nhập số điện thoại liên hệ của khách hàng !')
				this.$refs.thongtinkhachhang.focusSDT()
				return false
			}
			if (thongtin_kh.SO_DT.trim().length > 10) {
				this.$toast.error('Số điện thoại liên hệ của khách hàng không được quá 10 ký tự.\nBạn hãy bỏ số 0 ở đầu (nếu có)!')
				this.$refs.thongtinkhachhang.focusSDT()
				return false
			}
			if (thongtin_kh.SO_DT.trim().length < 7) {
				this.$toast.error('Số điện thoại liên hệ của khách hàng không được ít hơn 7 ký tự.')
				this.$refs.thongtinkhachhang.focusSDT()
				return false
			}

			if (thongtin_kh.DIACHI_KH == '' || thongtin_kh.dataDiaChiKH.quan_id == 0 || thongtin_kh.dataDiaChiKH.phuong_id == 0) {
				this.$toast.error('Hãy nhập địa chỉ khách hàng!')
				this.$refs.thongtinkhachhang.focusDiaChiKH()
				return false
			}
			if (thongtin_kh.TEN_KH.trim() == '') {
				this.$toast.error('Bạn chưa nhập tên khách hàng!')
				this.$refs.thongtinkhachhang.focusTenKH()
				return false
			}else if(thongtin_kh.TEN_KH.length>=500){
				this.$toast.error('Tên khách hàng không được vượt quá 500 ký tự!')
				this.$refs.thongtinkhachhang.focusTenKH()
				return false
			}

			if (thongtin_kh.LOAIKH_ID == undefined || thongtin_kh.LOAIKH_ID==null) {
				this.$toast.error('Chưa chọn loại khách hàng!')
				return false
			}

			if (thongtin_kh.NGAYCAP != null && thongtin_kh.NGAYCAP != '') {
				let dtpNgayCap=moment(thongtin_kh.NGAYCAP, 'DD/MM/YYYY HH:mm:ss');
				if(dtpNgayCap>moment(new Date())){
					this.$toast.error('Ngày cấp giấy tờ không được lớn hơn ngày hiện tại!')
					this.$refs.thongtinkhachhang.focusNgayCap()
					return false
				}

				if (thongtin_kh.NGAYHETHAN_GT != null && thongtin_kh.NGAYHETHAN_GT != '') {
					let dtpHetHan=moment(thongtin_kh.NGAYHETHAN_GT, 'DD/MM/YYYY HH:mm:ss');

					if(dtpHetHan<dtpNgayCap){
						this.$toast.error('Ngày cấp giấy tờ phải trước ngày hết hạn!')
						this.$refs.thongtinkhachhang.focusNgayCap()
						return false
					}
				}
			}

			if (thongtin_kh.NGAY_SN != null && thongtin_kh.NGAY_SN != '') {
				let dtpNgayCap=moment(thongtin_kh.NGAY_SN, 'DD/MM/YYYY HH:mm:ss');
				if(dtpNgayCap>moment(new Date())){
					this.$toast.error('Ngày sinh không được lớn hơn ngày hiện tại!')
					this.$refs.thongtinkhachhang.focusNgaySinh()
					return false
				}
			}

			return true
		},
		async kiemtra_dl_thuebao() {
			let thongtin_tb = this.$refs.thongtinthuebao.getData()
			let thongtin_kh = this.$refs.thongtinkhachhang.getData()

			if (thongtin_tb.TEN_TB.trim() == '') {
				this.$toast.error('Hãy nhập tên thuê bao !')
				this.$refs.thongtinthuebao.focusTenTB()
				return false
			}

			if (thongtin_tb.DIACHI_LD.trim() == '' || thongtin_tb.dataDiaChiLD.quan_id == 0 || thongtin_tb.dataDiaChiLD.phuong_id == 0 || (thongtin_tb.dataDiaChiLD.pho_id + thongtin_tb.dataDiaChiLD.ap_id + thongtin_tb.dataDiaChiLD.khu_id) == 0) {
				this.$toast.error('Hãy nhập địa chỉ lắp đặt!')
				this.$refs.thongtinthuebao.focusDiaChiLD()
				return false
			}

			if (thongtin_tb.DIACHI_TB.trim() == '' || thongtin_tb.dataDiaChiTB.quan_id == 0 || thongtin_tb.dataDiaChiTB.phuong_id == 0 || (thongtin_tb.dataDiaChiTB.pho_id + thongtin_tb.dataDiaChiTB.ap_id + thongtin_tb.dataDiaChiTB.khu_id) == 0) {
				this.$toast.error('Hãy nhập địa chỉ thuê bao!')
				this.$refs.thongtinthuebao.focusDiaChiTB()
				return false
			}
			if (thongtin_tb.DONVI_ID == null) {
				this.$toast.error('Chưa có đơn vị xử lý, liên hệ admin tỉnh để bổ sung cấu hình đơn vị phân giao đơn hàng!')
				return false
			}

			return true
		},

		initHeader() {
			if (this.loaihd_id == LoaiHopDong.TUVAN_DV) {
				this.header.title = 'TIẾP NHẬN YÊU CẦU ĐƠN HÀNG - KHẢO SÁT (V0.7)'
				this.header.list = [{
						name: "Hợp đồng",
						link: {
							name: "Ui.cards"
						},
						active: false
					},
					{
						name: "Lập hợp đồng",
						link: {
							name: "Ui.cards"
						},
						active: false
					},
					{
						name: "Tiếp nhận yêu cầu đơn hàng",
						link: {
							name: "Ui.buttons"
						},
						active: true
					}
				]
			}
		},
		async sp_lay_ds_donhang(loaihd_id, nhanvien_id, ma_gd) {
			try {
				this.loading(true);

				let response = await myApi.sp_lay_ds_donhang(
					this.axios,
					JSON.stringify({
						"loaihd_id":loaihd_id,
						"nhanvien_id":nhanvien_id,
						"tthd_id":1,
						"ma_gd":ma_gd
					})
				);

				if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
					return response.data.data;
				} else {
					return [];
				}
			} catch (e) {
				this.setActiveActions(1);
				if (e.data && e.data.message) {
					this.$toast.error(e.data.message)
				} else if (e.response && e.response.data && e.response.data.message) {
					this.$toast.error(e.response.data.message)
				} else {
					this.$toast.error('Không load được danh sách')
				}
				return []
			}finally {
				this.loading(false);
			}
		},
		async tim_khachhang_theo_ma_kh(ma_kh,returnValue) {
			try {
				this.loading(true);
				let response = await myApi.tim_khachhang_theo_ma_kh(this.axios, ma_kh)

				if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
					if(returnValue){
						return response.data.data;
					}

					if (response.data.data.length > 0) {
						this.clear()
						this.setActiveActions(-1)
						this.hienthi_tt_danhba(response.data.data)
						this.$refs.thongtinkhachhang.focusMaKH()
					} else {
						this.$toast.error('Không tìm thấy thông tin khách hàng!')
					}
				} else {
					this.$toast.error('Không tìm thấy thông tin khách hàng!')
				}
			} catch (e) {
				if (e.data && e.data.message) {
					this.$toast.error(e.data.message)
				} else if (e.response && e.response.data && e.response.data.message) {
					this.$toast.error(e.response.data.message)
				} else {
					this.$toast.error('Không load được thông tin khách hàng')
				}
			}finally{
				this.loading(false);
			}
		},
		async hienthi_tt_danhba(ds) {
			//
			if (ds.length > 0) {
				//hiển thị thông tin lên form
				this.itemMaKHSelected = ds[0]
				this.thuebao_id = 0
				//if (this.loaihd_id != LoaiHopDong.TIEPNHAN_DATMOI) {
				//	this.thuebao_id = this.itemMaKHSelected.thuebao_id != null ? this.itemMaKHSelected.thuebao_id : 0
				//}


				let tinh_kh_id=this.itemMaKHSelected.tinhkh_id??this.itemMaKHSelected.tinhtt_id;

				if (this.itemMaKHSelected.khachhang_id != null) {
					this.khachhang_id = this.itemMaKHSelected.khachhang_id
				} else {
					this.khachhang_id = 0
				}
				await this.$refs.thongtinkhachhang.initInfoMaKH(ds[0])
				await this.$refs.thongtinthuebao.initInfoMaKH(ds[0])
			} else {
				this.setActiveActions(0)
			}
		},
		async hienthi_danhsach_hdtb(ma_gd) {
			if(ma_gd==undefined) ma_gd='';

			this.rowSelected = null
			let data = await this.sp_lay_ds_donhang(this.loaihd_id, this.$root.token.getNhanVienID(), ma_gd)

			if (data.length > 0) {
				this.actions[this.actions.findIndex(x => x.id == 'giaophieu')].active = true
				this.actions[this.actions.findIndex(x => x.id == 'hen_ld')].active = true
				//active xoa_yc khi row lớn hơn 1, ==1 xóa hd
				if (data.length > 1) {
					this.actions[this.actions.findIndex(x => x.id == 'xoa_yc')].active = true
				} else {
					this.actions[this.actions.findIndex(x => x.id == 'xoa_yc')].active = false
				}

				this.dataSources = data;

				if(data!=null && data.length>0){
					let rowHandle=0;
					if(ma_gd!=null && ma_gd!=''){
						rowHandle=data.findIndex(x => x.ma_gd == ma_gd);
						if(rowHandle<0)
							rowHandle=0;
					}
					this.$refs.gridDs.focusRowHand = rowHandle;
				}
			} else {
				this.actions[this.actions.findIndex(x => x.id == 'nhapmoi')].active = false
				this.actions[this.actions.findIndex(x => x.id == 'giaophieu')].active = false
				this.actions[this.actions.findIndex(x => x.id == 'hen_ld')].active = false
				this.actions[this.actions.findIndex(x => x.id == 'xoa_hd')].active = false
				this.actions[this.actions.findIndex(x => x.id == 'them_yc')].active = false
				this.actions[this.actions.findIndex(x => x.id == 'xoa_yc')].active = false
				this.dataSources = []
				this.rowSelected = null
				this.setActiveActions(1)
			}
		},
		async xoa_hdtb(tinh_id,hdtb_id) {
			try {
				this.loading(true)
				let response = await myApi.xoa_hdtb(this.axios, tinh_id, hdtb_id, await this.getThongTinNguoiDung())
				this.loading(false)
				if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
					this.$toast.success(response.data.message)
					this.clear()
					await this.hienthi_danhsach_hdtb()
				} else {
					if (response && response.data && response.data.message) {
						this.$toast.error(response.data.message)
					} else {
						this.$toast.error('Đã xảy ra lỗi, xóa yêu cầu không thành công')
					}
				}
			} catch (e) {
				this.loading(false)
				if (e.data && e.data.message) {
					this.$toast.error(e.data.message)
				} else if (e.response && e.response.data && e.response.data.message) {
					this.$toast.error(e.response.data.message)
				} else {
					this.$toast.error('Đã xảy ra lỗi, xóa yêu cầu không thành công')
				}
			}
		},
		async xoa_hdkh(tinh_id, hdkh_id) {
			try {
				this.loading(true)
				let response = await myApi.xoa_hdkh(this.axios, tinh_id, hdkh_id, await this.getThongTinNguoiDung())
				this.loading(false)
				if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
					this.$toast.success(response.data.message)
					//hiển thị lại danh sách
					this.clear()
					this.setActiveActions(0)
					await this.hienthi_danhsach_hdtb()

				} else {
					if (response && response.data && response.data.message) {
						this.$toast.error(response.data.message)
					} else {
						this.$toast.error('Đã xảy ra lỗi, xóa dữ liệu không thành công')
					}
				}
			} catch (e) {
				this.loading(false)
				if (e.data && e.data.message) {
					this.$toast.error(e.data.message)
				} else if (e.response && e.response.data && e.response.data.message) {
					this.$toast.error(e.response.data.message)
				} else {
					this.$toast.error('Đã xảy ra lỗi, xóa dữ liệu không thành công')
				}
			}
		},
		async giaophieu_hdtb(tinh_id,hdtb_id,tt_nd) {
			try {
				this.loading(true)
				let response = await myApi.giaophieu_hdtb(this.axios, tinh_id, hdtb_id, tt_nd)
				if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
					this.$toast.success(response.data.message)
					this.clear()
					await this.hienthi_danhsach_hdtb()
				} else {
					if (response && response.data && response.data.message) {
						this.$toast.error(response.data.message)
					} else {
						this.$toast.error('Đã xảy ra lỗi, giao phiếu không thành công')
					}
				}
			} catch (e) {
				this.loading(false)
				if (e.data && e.data.message) {
					this.$toast.error(e.data.message)
				} else if (e.response && e.response.data && e.response.data.message) {
					this.$toast.error(e.response.data.message)
				} else {
					this.$toast.error('Đã xảy ra lỗi, xóa dữ liệu không thành công')
				}
			}
		},
		async fn_lap_hopdong_donhang(data,reload) {
			try {
				this.loading(true)
				let response = await myApi.fn_lap_hopdong_donhang(this.axios, data)
				this.loading(false)
				if (response.data.error_code == 'BSS-00000000') {
					if (response.data.data.ERROR_CODE == 1) {
						if(reload==undefined || reload){
							this.$toast.success(response.data.data.MESSAGE);

							if(response.data.data.RESULT!=undefined && response.data.data.RESULT!=null && response.data.data.RESULT.MA_GD!=undefined)
								this.hienthi_danhsach_hdtb(response.data.data.RESULT.MA_GD);
							else
								this.hienthi_danhsach_hdtb();
						}

						return true;
					} else {
						this.$toast.error(response.data.data.MESSAGE)
					}
				} else {
					this.$toast.error(response.data.message)
				}
			} catch (e) {
				this.loading(false)
				if (e.data && e.data.message) {
					this.$toast.error(e.data.message)
				} else if (e.response && e.response.data && e.response.data.message) {
					this.$toast.error(e.response.data.message)
				} else {
					this.$toast.error('Lỗi cập nhật!')
				}
			}
			return false;
		},
		async initDuLieu() {
			this.initHeader()
			try{
				this.loading(true);

				await Promise.all(
					[
						this.$refs.thongtinkhachhang.initDuLieu(),
						this.$refs.thongtinthuebao.initDuLieu(),
						this.$refs.thongtinthanhtoan.initDuLieu(),
						this.$refs.thongtinkhac.initDuLieu()
					]
				);

				await this.hienthi_danhsach_hdtb();
			} catch (e) {

			} finally {
				this.loading(false);
			}
		}
	},
	mounted() {
		setTimeout(() => {
			this.initDuLieu()
		}, 200)
	}
}
</script>
