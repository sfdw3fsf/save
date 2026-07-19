<template src="./GiaoDVCSKH.html"></template>
<style src="./GiaoDVCSKH.scss"></style>
<script>
	import select2 from "@/components/Select2.vue";
	import { mapActions, mapState } from 'vuex';
	import GiaoDVCSKHApi from "./../api/GiaoDVCSKHApi";
	import YesNoModal from "../utils/YesNoModal.vue";
	export default {
		name:"GiaoNVCSKH",
		components:{
			appSelect2: select2,
			confirmPopup: YesNoModal,
		},
		data() {
			return {
				header: {
						title: "GIAO ĐƠN VỊ CHĂM SÓC KHÁCH HÀNG",
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
								name: "Giao đơn vị CSKH",
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

				dsChuaGanFields: [
					{
						fieldName: "TEN_DV",
						headerText: "Đơn vị",
						allowFiltering: true,
						allowSorting: false,
					},

            	],

				dsDaGanFields: [
					{
						fieldName: "TEN_DV",
						headerText: "Đơn vị",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "NOIDUNG",
						headerText: "Nội dung",
						allowFiltering: true,
						allowSorting: false,
					},

            	],

				loaiDonViSelected: 0,
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
			this.$nextTick(() => this.$refs.txtNoiDungRef.focus());
			this.loading(true);
			await this.hienThiDsLoaiDonVi();
			if (this.dsLoaiDonVi.length > 0) {
				this.loaiDonViSelected = this.dsLoaiDonVi[0].id
			}
			await this.hienThiDsChuongTrinh();
		},
		computed:{
			...mapState("giaodvcskh", ["dsLoaiDonVi","dsChuongTrinh","dsDaGan","dsChuaGan"]),
        	// ...mapGetters("giaonvcskh", ["dsChuongTrinhGetter"]),
		},
		watch:{},
		methods:{
			...mapActions("giaodvcskh",
			["getDsLoaiDonVi",
			"getDsChuongTrinh",
			"getDsDaGan",
			"getDsChuaGan",
			"setDanhSachGanChuaGan",
			"khongGanDv",
			"ganTatCaDv"
			]),

			async hienThiDsLoaiDonVi() {
				try {
					await this.getDsLoaiDonVi();
				} catch (ex) {
				} finally {
					this.loading(false)
				}
        	},

			async onLoaiDonViChange() {
				// this.clearParam();
				this.loading(true);
				await this.hienThiDsDvChuaGan();
				// await this.hienThiDsCongViecCSKH();
				this.loading(false);
        	},

			async hienThiDsChuongTrinh(donViIdParam) {
				try {
					let ctCskhPost = {
						kieu: 1,
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
				try {
					if(dataItem != null ) {
						this.ctcskhIdSelected = dataItem.CTCSKH_ID;
						await this.hienThiDsDvChuaGan(this.ctcskhIdSelected);
						await this.hienThiDsDvDaGan(this.ctcskhIdSelected);
					}
				} catch (ex) {
				} finally {
					this.loading(false)
				}
            },

			async hienThiDsDvDaGan(ctcskhIdParam) {
				try {
					let daGanPost = {
						loai: 1,
						ctcskhId: ctcskhIdParam,
						loadDonViId: this.loaiDonViSelected,
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

			async hienThiDsDvChuaGan(ctcskhIdParam) {
				try {
					let chuaGanPost = {
						loai: 0,
						ctcskhId: ctcskhIdParam,
						loaiDvId: this.loaiDonViSelected,
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

			thayDoiTrangThaiGan(setGan) {
				this.txtNoiDungInput = this.txtNoiDungInput.trim();
				if(setGan == 1 && this.txtNoiDungInput.length > 500) {
					this.$root.toastError("Trường nội dung dữ liệu vượt quá độ dài cho phép.");
					return;
				}

				let dsChuaGanSelected = [];
				let dsDaGanSelected = [];
				let data = {};

					dsChuaGanSelected = this.getListItem(
						this.dsChuaGan,
						"DONVI_ID",
						this.gridChuaGanSelectedValues
					);
					dsDaGanSelected = this.getListItem(
						this.dsDaGan,
						"DONVI_ID",
						this.gridDaGanSelectedValues
					);
					data = {
						typeDanhSach: "dsDaGan",
						field: "DONVI_ID",
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
				let data = {
					ctcsKhId: this.ctcskhIdSelected,
					nguoiGiaoId: this.$auth.getNhanVienID(),
					// dsNhanVienId: '124#441',
					dsDonViId: this.dsDaGan.map((x) => x.DONVI_ID).join('#'),
					dsNoiDung: this.dsDaGan.map((x) => x.NOIDUNG).join('#'),

				};
				GiaoDVCSKHApi.ganDVCSKH(this.axios, data)
					.then((response) => {
					if (response.data.error_code === "BSS-00000000") {
						this.$root.toastSuccess("Giao đơn vị CSKH thành công!");
					} else {
						this.$root.toastError(response.data.message);
					}
					})
					.catch((reject) => {
					console.log(reject);
					if (
						reject.response &&
						reject.response.data &&
						reject.response.data.message
					)
						this.$root.toastError(reject.response.data.message);
						else this.$root.toastError("Có lỗi xảy ra!");
					});
			},

			async onGhiLaiClick() {
				this.$bvModal.show("ghiLaiConfirm");
			},

			onBlurNoiDung() {
				console.log("thay đổi nội dung")
			},
			khongGan() {
				let dataInput = {
					setTatCa : 2,
					noiDung: this.txtNoiDungInput
				}
				this.ganTatCaDv(dataInput)
			},
			ganTatCa() {
				if(this.txtNoiDungInput.length > 500) {
					this.$root.toastError("Trường nội dung dữ liệu vượt quá độ dài cho phép.");
					return;
				}
				let dataInput = {
					setTatCa : 1,
					noiDung: this.txtNoiDungInput
				}
				this.ganTatCaDv(dataInput)
			}

		},
	}
</script>
