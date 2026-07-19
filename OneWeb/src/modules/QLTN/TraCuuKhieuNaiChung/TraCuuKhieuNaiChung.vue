<template src="./TraCuuKhieuNaiChung.html"></template>
<style src="./TraCuuKhieuNaiChung.scss"></style>
<script>
	
	import select2 from "@/components/Select2.vue";
	import { mapActions, mapState, mapGetters } from 'vuex';
	import PopupDanhSachThueBao from './../TraCuuKhoaMoMay/component/PopupDanhSachThueBao'
	import moment from "moment";

	// import TaoCongViecCSKHApi from "./../api/TaoCongViecCSKHApi";
	export default {
		name:"TraCuuKhieuNaiChung",
		components:{
	    	appSelect2: select2,
			PopupDanhSachThueBao
		},
		data() {
			return {
				header: {
						title: "TRA CỨU KHIẾU NẠI",
						list: [
						//   { name: "Trang chủ", link: { name: "Ui.cards" } },
							{
								name: "Tra cứu ",                        
								link: { name: "Ui.buttons" },
							},
							{
								name: "Quản lý thu nợ",
								link: { name: "Ui.buttons" },
							},
							{
								name: " Tra cứu xử lý khiếu nại",
								link: { name: "Ui.buttons" },
							},
							{
								name: "Tra cứu khiếu nại - đại lý",
								link: { name: "Ui.buttons" },
							},
							
						],
				},
				dichVuVTSelected: '',
				loaiHinhTBSelected: '',
				danhSachKhieuNaiTB: [],
				danhSachKhieuNaiTBFields: [
					{
						fieldName: "MA_KN",
						headerText: "Mã khiếu nại",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "NGAY_TN",
						headerText: "Ngày TN",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "TRANGTHAI_KN",
						headerText: "Trạng thái KN",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "NGUOI_KN",
						headerText: "Người khiếu nại",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "TENCHUDE",
						headerText: "Tên chủ đề",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "DIENTHOAI_LH",
						headerText: "Điện thoại liên hệ",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "NHANVIEN_TN",
						headerText: "Người tiếp nhận",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "ND_KHIEUNAI",
						headerText: "Nội dung khiếu nại",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "NHANVIEN_GQ",
						headerText: "Người giải quyết",
						allowFiltering: true,
						allowSorting: false,
					},
				],
				danhSachNVQLDaiLy: [],
				danhSachNVQLDaiLyFields : [
					{
						fieldName: "MA_ND",
						headerText: "Mã người dùng",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "TEN_NV",
						headerText: "Tên nhân viên",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "SO_DT",
						headerText: "Điện thoại liên hệ",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "TEN_DV",
						headerText: "Tên đơn vị",
						allowFiltering: true,
						allowSorting: false,
					},
				],
				thongTinThanhToan: [],
				txtMaTB: '',
				txtMaTT: '',
				txtTenTT: '',
				txtDiaChiTT: '',
				txtDaiLy: '',
				txtTuyenThu: '',
				txtTrangThaiTB: '',
				daiLyId: 0,
				kyCuoc:0,
			}
		},
		async created(){},
		async mounted(){
			//set ky cước là kỳ tháng hiện tại
			this.getDanhSachDichVuVT();
			this.getKyCuoc();
		},
		computed:{
			...mapState("TraCuuKhieuNaiChung", [
				"danhSachDichVuVT",
				"danhSachLoaiHinhTB",
                // "danhSachKhieuNaiTB",
				// "danhSachNVQLDaiLy"
       		 ]),

        	...mapGetters("TCCuocGoi", ["danhSachDichVuVTGetter",
                                        "danhSachLoaiHinhTBGetter",
										// "danhSachKhieuNaiTBGetter",
										// "danhSachNVQLDaiLyGetter"
										]),
		},
		watch:{
				"dichVuVTSelected": {
					handler: async function (value) {
						this.loading(true);	
						await this.getDanhSachLoaiHinhTB(this.dichVuVTSelected)
						// await this.layDanhSachKhieuNai();
						this.loading(false);
					},
				},
				// "daiLyId": {
				// 	handler: async function (value) {
				// 		this.loading(true);	
				// 		await this.layDanhSachNhanVienQL();
				// 		this.loading(false);
				// 	},
				// }
				
		},
		methods:{
			...mapActions("TraCuuKhieuNaiChung",[
						"getDanhSachDichVuVT",
						"getDanhSachLoaiHinhTB",
                        "getDanhSachKhieuNaiTB",
						"getDanhSachNVQLDaiLy",
						"getThongTinThanhToan"
					]),

			async traCuu() {
				// this.cleanInputData();
				await this.layDanhSachThanhToan();
				if(this.thongTinThanhToan.length > 0) {
					if (this.thongTinThanhToan.length == 1) {
						this.dichVuVTSelected = this.thongTinThanhToan[0].DICHVUVT_ID;
						await this.getDanhSachLoaiHinhTB(this.dichVuVTSelected)
						await this.layDanhSachKhieuNai();
						await this.layDanhSachNhanVienQL();
					} else if (this.thongTinThanhToan.length > 1) {
						this.$refs.dlgDanhsachthuebao.openDialog();
						await this.getDanhSachLoaiHinhTB(this.dichVuVTSelected)
						await this.layDanhSachKhieuNai();
						await this.layDanhSachNhanVienQL();
					} 
				} else {
						this.clean();
						this.$toast.error("Không tìm thấy thông tin thuê bao " + this.txtMaTB);								
				}

				// await this.layDanhSachKhieuNai();
				
			},

			// cleanInputData() {
			// 	this.thongTinThanhToan= [],
			// 	this.danhSachKhieuNaiTB=[],
			// 	// this.txtMaTB= "";
			// 	this.txtMaTT= "";
			// 	this.txtTenTT= "";
			// 	this.txtDiaChiTT= "";
			// 	this.txtDaiLy= "",
			// 	this.txtTuyenThu= "";
			// 	this.txtTrangThaiTB= "";
			// 	this.daiLyId= 0;
			// },

			async layDanhSachThanhToan(){
				let thongtinTTInput = {
					maTB: this.txtMaTB		
				}

				await this.getThongTinThanhToan(thongtinTTInput)
						.then((res) => {
						if (res.data.error_code === "BSS-00000000") {
							this.thongTinThanhToan = res.data.data;
						} })
							.catch((err) => {
							this.$toast.error(err.data.message_detail);
						});

			},

			async layDanhSachKhieuNai() {
				let dataKhieuNaiInput = {
					kyCuoc: this.kyCuoc,
					dichVuVTId: this.dichVuVTSelected,
					maTB: this.txtMaTB
				};
				await this.getDanhSachKhieuNaiTB(dataKhieuNaiInput)
					.then((res) => {
					if (res.data.error_code === "BSS-00000000") {
						this.danhSachKhieuNaiTB = res.data.data;
					}
					if(this.danhSachKhieuNaiTB.length > 0) {
						this.loaiHinhTBSelected = this.danhSachKhieuNaiTB[0].LOAITB_ID;
						this.txtMaTT = this.danhSachKhieuNaiTB[0].MA_TT;
						this.txtTenTT = this.danhSachKhieuNaiTB[0].TEN_TT;
						this.txtDiaChiTT = this.danhSachKhieuNaiTB[0].DIACHI_TT;
						this.txtDaiLy = this.danhSachKhieuNaiTB[0].TEN_DLY;
						this.txtTuyenThu = this.danhSachKhieuNaiTB[0].TENTUYEN;
						this.txtTrangThaiTB = this.danhSachKhieuNaiTB[0].TRANGTHAI_TB;
						this.daiLyId = this.danhSachKhieuNaiTB[0].DAILY_ID;
								// this.loading(false);
					} else {
						this.$toast.error("Không tìm thấy thông tin thuê bao "+ this.txtMaTB);
						this.clean();
					}
					})
					.catch((err) => {
						this.$toast.error(err.data.message_detail);
					});
			},

			async layDanhSachNhanVienQL() {
				await this.getDanhSachNVQLDaiLy(this.daiLyId).then((res) => {
					if (res.data.error_code === "BSS-00000000") {
						this.danhSachNVQLDaiLy = res.data.data;
					} })	
					.catch((err) => {
					this.loading(false);
					this.$toast.error(err.data.message_detail);
					});
			},

			clean() {
				this.txtMaTT = "";
				this.txtTenTT = "";
				this.txtDiaChiTT = "";
				this.txtTrangThaiTB = "";
				this.txtDaiLy = "";
				this.txtTuyenThu = "";
				this.danhSachKhieuNaiTB= [];
				this.danhSachNVQLDaiLy= [];
				// dgvKhieuNai.DataSource = null;
				// dgvNhanVien.DataSource = null;	
			},
			async selectThueBao(index) {            
				if (index >= 0) {
					// this.thueBaoId = this.dsDanhBa[index].THUEBAO_ID;
					// this.maTT = this.dsDanhBa[index].MA_TT;                
					// this.newTab();
					// await this.getLichSuKhoaMoMay();
					// this.currentTabIndex = this.tabs.length - 1;
					this.dichVuVTSelected = this.thongTinThanhToan[index].DICHVUVT_ID;
				}
			},
			getKyCuoc:async function(){

				let response = await this.$root.context.get('/web-thuno/api/thu-no/common/ky-cuoc-hien-hanh');
				this.kyCuoc=response.data[0].KYCUOC;
				console.log(this.kyCuoc);
			},
		},
	}
</script>