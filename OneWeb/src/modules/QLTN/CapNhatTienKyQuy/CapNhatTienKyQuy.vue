<template src="./CapNhatTienKyQuy.html"></template>
<style src="./CapNhatTienKyQuy.scss"></style>
<script>
	import select2 from "@/components/Select2.vue";
	import KyCuoc from "../components/KyCuoc.vue";
	import moment from "moment";

	import { mapActions, mapState, mapGetters } from 'vuex'
	import CapNhatTienKyQuyAPI from "./../api/CapNhatTienKyQuyAPI";
	// import YesNoModal from "../utils/YesNoModal.vue";
	export default {
		name:"CapNhatTienKyQuy",
		components:{
			appSelect2: select2,
			appKyCuoc: KyCuoc
			// confirmPopup: YesNoModal,
		},
		data() {
			return {
				header: {
                    title: "CẬP NHẬT TIỀN KÝ QUỸ",
                    list: [
                    {
                        name: "Quản lý thu nợ",
                        link: { name: "Ui.buttons" }
                    },
                    {
                        name: "Cập nhật tiền ký quỹ",
                        link: { name: "Ui.buttons" }
                    }
                    ]
                },
				danhSachNVDC: [],
				danhSachNhanVienDatCocFields: [
					{
						fieldName: "MA_NV",
						headerText: "Mã nhân viên",
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
						fieldName: "TIEN_DC_VIEW",
						headerText: "Tiền ký quỹ gốc",
						allowFiltering: true,
						allowSorting: false,
						cellCssClass: 'text-right'
					},
					{
						fieldName: "TIEN_DC_THANG_VIEW",
						headerText: "Tiền ký quỹ tháng",
						allowFiltering: true,

						allowSorting: false,
						cellCssClass: 'text-right'
					},
					{
						fieldName: "GHICHU",
						headerText: "Ghi chú",
						allowFiltering: true,
						allowSorting: false,
					},
            	],
				pKyCuoc: "",
				txtMaNV: "",
				txtTenNV: "",
				txtTienKQGoc: 0,
				txtTienKQuy: 0,
				txtGhiChu: "",
				txtNVID: 0
			}
		},
		async created(){},
		async mounted(){
			this.loading(true);
			// load kỳ hóa đơn


			//load danh sách nhân viên đặt cọc
			// await this.hienThiDsNhanVienDatCoc();

		},
		computed:{
			// ...mapState("CapNhatTienKyQuy", [
            // "danhSachNhanVienDatCoc",
			// ]),
			// ...mapGetters("CapNhatTienKyQuy", ["danhSachNhanVienDatCocGetter"]),
		},
		watch:{},
		methods:{
			...mapActions("CapNhatTienKyQuy",
				["getDanhSachNhanVienDatCoc"]),
			handleKyCuoc(e) {
      			this.pKyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no;
				this.hienThiDsNhanVienDatCoc()
			},

			async hienThiDsNhanVienDatCoc() {


				// this.loading(true);

				// try {
				// 	if(!this.pKyCuoc.includes(null)) {
				// 		const response = await this.getDanhSachNhanVienDatCoc(this.pKyCuoc);
				// 		if (
				// 			response.data.error_code === "BSS-00000000" &&
				// 			response.data.data.length === 0
				// 		) {
				// 			this.$root.toastWarning(
				// 				"Không tìm thấy danh sách công nợ thỏa mãn điều kiện!"
				// 			);
				// 		}

				// 		response.data.data.data.forEach(item => {
				// 			item.TIEN_DC = Number(item.TIEN_DC).toLocaleString("en-GB");
				// 			item.TIEN_DC_THANG = Number(item.TIEN_DC_THANG).toLocaleString("en-GB");
				// 		});
				// 		this.danhSachNVDC = response.data.data.data;
				// 	}

				// } catch (e) {
				// 	console.log(e);
				// } finally {
				// 	this.loading(false);
				// }

				try {
					if(!this.pKyCuoc.includes(null)) {
						const response = await this.getDanhSachNhanVienDatCoc(this.pKyCuoc);
						if (
							response.data.error_code === "BSS-00000000" &&
							response.data.data.length === 0
						) {
							this.$root.toastError(
								"Không tìm thấy danh sách nhân viên đặt cọc thỏa mãn điều kiện!"
							);
						}
						// console.log(response.data.data);
						response.data.data.forEach(item => {
							item.TIEN_DC_VIEW = Number(item.TIEN_DC).toLocaleString("en-GB");
							item.TIEN_DC_THANG_VIEW = Number(item.TIEN_DC_THANG).toLocaleString("en-GB");
						});
						this.danhSachNVDC = response.data.data;
					}

				} catch (e) {
					console.log(e);
				} finally {
					this.loading(false);
				}
        	},

			//Chọn hàng trên grid gán vào txt
			async gridNhanVienDatCoc_selectedRowChanged(dataItem) {
				if(dataItem!=null) {
					// this.setButtonStatus(3);
					this.txtNVID = dataItem.NHANVIEN_ID,
					this.txtMaNV =  dataItem.MA_NV,
					this.txtTenNV =  dataItem.TEN_NV,
					this.txtTienKQGoc = dataItem.TIEN_DC,
					this.txtTienKQuy = dataItem.TIEN_DC_THANG,
					this.txtGhiChu = dataItem.GHICHU
				}
       		},

			// Bấm nút ghi lại gọi hàm cập nhật
			async onGhiLaiClick() {
				//kiểm tra tiền ký quỹ mới > tiền ký quỹ gốc
				 if (this.txtTienKQGoc > this.txtTienKQuy)
                {
                   this.$root.toastError("Tiền ký quỹ mới không được nhỏ hơn tiền ký quỹ gốc của nhân viên (Tiền gốc: " + txtTienKQGoc + " VNĐ)");
                    return;
                }

				//Cập nhật tiền ký quỹ
				this.loading(true);
				try {
					let capNhatTKQData = {
						nhanVienId : this.txtNVID,
						maNV : this.txtMaNV,
						tenNV : this.txtTenNV,
						tienDatCoc: this.txtTienKQuy,
						kyCuoc : this.pKyCuoc,
						ghiChu : this.txtGhiChu
					};
					console.log(capNhatTKQData);
					await CapNhatTienKyQuyAPI.capNhatTienKyQuy(this.axios, capNhatTKQData)
					.then((response) => {
						if (response.data.error_code == "BSS-00000000") {
							this.$root.toastSuccess(
								// this.btnStatus.btnNhapMoiEnabled
								"Thành công!"
							);
							this.hienThiDsNhanVienDatCoc()
						}
						else {
							this.$root.toastError(
								"Xảy ra lỗi trong quá trình cập nhập thông tin: " +
								response.data.message
							);
						}
					})
					.catch((reject) => {
						this.$root.toastError(
						reject.response.data.message || reject.response.data.error
						);
					});
				} catch (e) {
				} finally {
					this.loading(false);
				}

				//load lại danh sách nhân viên đặt cọc
			}
		},
	}
</script>
