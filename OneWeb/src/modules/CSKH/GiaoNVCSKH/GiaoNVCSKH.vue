<template src="./GiaoNVCSKH.html"></template>
<style src="./GiaoNVCSKH.scss"></style>
<script>
	import { mapActions, mapState } from 'vuex';
	import GiaoNVCSKHApi from "./../api/GiaoNVCSKHApi";
	import YesNoModal from "../utils/YesNoModal.vue";
	export default {
		name:"GiaoNVCSKH",
		components:{
			confirmPopup: YesNoModal,
		},
		data() {
			return {
				header: {
						title: "GIAO NHÂN VIÊN CHĂM SÓC KHÁCH HÀNG",
						list: [
						//   { name: "Trang chủ", link: { name: "Ui.cards" } },
							{
								name: "Chăm sóc khách hàng",
								link: { name: "Ui.buttons" },
							},
							{
								name: "Quản lý chính sách CSKH",
								link: { name: "Ui.buttons" },
							},
							{
								name: "Giao nhân viên chăm sóc khách hàng",
								link: { name: "Ui.buttons" },
							},
						],
				},

				dsChuongTrinhFields: [
					{
						fieldName: "MA_CT",
						headerText: "Mã CT",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "TEN_CT",
						headerText: "Tên chương trình",
						allowFiltering: true,
						allowSorting: false,
					}
            	],

				dsDaGanFields: [
					{
						fieldName: "TEN_NV",
						headerText: "Tên nhân viên",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "NOIDUNG",
						headerText: "Nội dung",
						allowFiltering: true,
						allowSorting: false,
					}
            	],

				dsChuaGanFields: [
					{
						fieldName: "TEN_NV",
						headerText: "Tên nhân viên",
						allowFiltering: true,
						allowSorting: false,
					},

            	],

				donViSelected: 0,
				ctcskhIdSelected: 0,
				txtNoiDungInput:'',
				gridDaGanSelectedValues: [],
      			gridChuaGanSelectedValues: [],
			}
		},
		async created(){
			// this.clearParam();
		},
		async mounted(){
			this.loading(true);
			await this.hienThiDsDonVi();
			if (this.dsDonVi.length > 0) {
				this.donViSelected = this.dsDonVi[0].id
			}
		},
		computed:{
			...mapState("giaonvcskh", ["dsDonVi","dsChuongTrinh","dsDaGan","dsChuaGan"]),
        	// ...mapGetters("giaonvcskh", ["dsChuongTrinhGetter"]),
		},
		watch:{},
		methods:{
			...mapActions("giaonvcskh",
			["getDsDonVi",
			"getDsChuongTrinh",
			"getDsDaGan",
			"getDsChuaGan",
			"setDanhSachGanChuaGan",
			"ganTatCaNv"
			]),

			async hienThiDsDonVi() {
				try {
					await this.getDsDonVi();
				} catch (ex) {
				} finally {
					this.loading(false)
				}
        	},

			async onDonViChange() {
				// this.clearParam();
				this.loading(true);
				await this.hienThiDsChuongTrinh(this.donViSelected);
				// await this.hienThiDsCongViecCSKH();
				this.loading(false);
        	},

			async hienThiDsChuongTrinh(donViIdParam) {
				try {
					let ctCskhPost = {
						donViId: donViIdParam,
						pageNo: 1,
						pageSize: 1000
					};
					await this.getDsChuongTrinh(ctCskhPost);
				} catch (ex) {
				} finally {
					this.loading(false)
				}
        	},

			async gridChuongTrinh_selectedRowChanged(dataItem) {
				this.loading(true);
				if(dataItem != null ) {
					this.ctcskhIdSelected = dataItem.CTCSKH_ID;
					await this.hienThiDsNvDaGan();
					await this.hienThiDsNvChuaGan();
				} else {
                  this.ctcskhIdSelected = 0
                }
				this.loading(false);
            },

			async hienThiDsNvDaGan() {
				try {
					let daGanPost = {
						loai: 1,
						ctcskhId: this.ctcskhIdSelected,
						donViId: this.donViSelected,
						// donViId:981,
						// ctcsKhId: 7,
						// donViId: 102,
						pageNo: 1,
						pageSize: 1000
					};
					await this.getDsDaGan(daGanPost);
				} catch (ex) {
				}
			},
			async hienThiDsNvChuaGan() {
				try {
					let chuaGanPost = {
						loai: 0,
						ctcskhId: this.ctcskhIdSelected,
						donViId: this.donViSelected,
						// donViId:15903,
						// ctcsKhId: 7,
						// donViId: 102,
						pageNo: 1,
						pageSize: 1000
					};
					await this.getDsChuaGan(chuaGanPost);
				} catch (ex) {
				}
			},

			// thayDoiTrangThaiGan(setGan) {
			// 	let dsChuaGanSelected = [];
			// 	let dsDaGanSelected = [];
			// 	let data = {};
			// 		dsChuaGanSelected = this.getListItem(
			// 			this.dsChuaGan,
			// 			"NHANVIEN_ID",
			// 			this.gridChuaGanSelectedValues
			// 		);
			// 		dsDaGanSelected = this.getListItem(
			// 			this.dsDaGan,
			// 			"NHANVIEN_ID",
			// 			this.gridDaGanSelectedValues
			// 		);
			// 		data = {
			// 			typeDanhSach: "dsDaGan",
			// 			field: "NHANVIEN_ID",
			// 			setGan: setGan,
			// 			itemArr: setGan == 1 ? dsChuaGanSelected : dsDaGanSelected,
			// 			txt: this.txtNoiDungInput
			// 		};
			// 		if (setGan == 1)
			// 			this.$refs.gridChuaGan.selectAllItem = false
			// 		else
			// 			this.$refs.gridDaGan.selectAllItem = false


			// 	this.setDanhSachGanChuaGan(data);
			// },


		thayDoiTrangThaiGan(setGan) {
				this.txtNoiDungInput = this.txtNoiDungInput.trim();

				let dsChuaGanSelected = [];
				let dsDaGanSelected = [];
				let data = {};

					dsChuaGanSelected = this.getListItem(
						this.dsChuaGan,
						"NHANVIEN_ID",
						this.gridChuaGanSelectedValues
					);
					dsDaGanSelected = this.getListItem(
						this.dsDaGan,
						"NHANVIEN_ID",
						this.gridDaGanSelectedValues
					);
					data = {
						typeDanhSach: "dsDaGan",
						field: "NHANVIEN_ID",
						setGan: setGan,
						itemArr: setGan == 1 ? dsChuaGanSelected : dsDaGanSelected,
						txt: this.txtNoiDungInput
					};
					if (setGan == 1)
						this.$refs.gridChuaGan.selectAllItem = false
					else
						this.$refs.gridDaGan.selectAllItem = false

				this.setDanhSachGanChuaGan(data);
			},

			getListItem(arr, id, ids) {
				return arr.filter((x) => ids.includes(x[id]));
			},

			async onGhiLaiConfirm() {
                if(this.ctcskhIdSelected == 0) {
                    this.$root.toastError('Không có chương trình để cập nhật!');
                    return;
                }
                if(this.dsDaGan.length == 0) {
                    this.$root.toastError('Không có thông tin để cập nhật!');
                    return;
                }
				let data = {
					ctcsKhId: this.ctcskhIdSelected,
					nguoiGiaoId: this.$auth.getNhanVienID(),
					// dsNhanVienId: '124#441',
					dsNhanVienId: this.dsDaGan.map((x) => x.NHANVIEN_ID).join('#'),
					dsNoiDung: this.dsDaGan.map((x) => x.NOIDUNG).join('#'),
				};
                this.loading(true);
				GiaoNVCSKHApi.ganNVCSKH(this.axios, data)
					.then((response) => {
					if (response.data.error_code === "BSS-00000000") {
						this.$root.toastSuccess("Giao nhân viên CSKH thành công !");
					} else {
						this.$root.toastError(response.data.message);
					}
                    this.loading(false);
					})
					.catch((reject) => {
					// console.log(reject);
					if (reject) this.$root.toastError(reject.data.data);
                    this.loading(false);
					});
			},
		khongGan(data) {
			this.ganTatCaNv(data);
		}
        },
	}
</script>
