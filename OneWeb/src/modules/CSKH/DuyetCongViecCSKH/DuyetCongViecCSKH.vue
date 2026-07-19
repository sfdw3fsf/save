<template src="./DuyetCongViecCSKH.html"></template>
<style src="./DuyetCongViecCSKH.scss"></style>
<script>
	import { mapActions, mapState, mapGetters } from 'vuex';
	import TaoCongViecCSKHApi from "./../api/TaoCongViecCSKHApi";
	import DuyetCongViecCSKHApi from "./../api/DuyetCongViecCSKHApi";
	import YesNoModal from "../utils/YesNoModal.vue";
	import moment from "moment";

	export default {
		name:"DuyetCongViecCSKH",
		components:{
			confirmPopup: YesNoModal,
		},
		data() {
			return {
				header: {
						title: "DUYỆT CÔNG VIỆC CHĂM SÓC KHÁCH HÀNG",
						list: [
						//   { name: "Trang chủ", link: { name: "Ui.cards" } },
							{
								name: "Quản lý chính sách CSKH",
								link: { name: "Ui.buttons" },
						},
							{
								name: "Duyệt công việc chăm sóc khách hàng",
								link: { name: "Ui.buttons" },
							},
						],
				},

				dsCongViecCSKHTheoNguoiTaoFields: [
					{
						fieldName: "STT",
						headerText: "STT",
						allowFiltering: true,
						allowSorting: false,
                        width: 60
					},
					{
						fieldName: "NOIDUNG",
						headerText: "Nội dung",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "NGAYTAO",
						headerText: "Ngày tạo",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "TRANGTHAI_CV",
						headerText: "Trạng thái",
						allowFiltering: true,
						allowSorting: false,
					}
            	],

				dsChuongTrinhCSKHSelected: 0,
				dsNhanVienCSKHSelected: 0,
				ghiChuInputTxt: "",
				duyetCheckBox: 2,
				// gridCongViecCSKHTheoNguoiTaoSelectedValues: [],
			}
		},
		async created(){
			this.clearData();
		},
		async mounted(){
			this.loading(true);
			await this.hienThiDsChuongTrinhCSKH();
			if (this.dsChuongTrinhCSKH.length > 0) {
				this.dsChuongTrinhCSKHSelected = this.dsChuongTrinhCSKH[0].id
			}
		},
		computed:{
			...mapState("taocongvieccskh", [
            	"dsChuongTrinhCSKH"
			]),
			...mapState("duyetcongvieccskh", [
            	"dsNhanVienCSKH",
				"dsCongViecCSKHTheoNguoiTao"
			]),

        	...mapGetters("taocongvieccskh",
				 ["dsChuongTrinhCSKHGetter"]),
			...mapGetters("duyetcongvieccskh", [
				"dsNhanVienCSKHGetter",
				"dsCongViecCSKHTheoNguoiTaoGetter"
			]),
		},
		watch:{
			"dsChuongTrinhCSKHSelected": {
				handler: async function (value) {
						this.clearParam();
						this.loading(true);
						await this.hienThiDsNhanVienCSKH(this.dsChuongTrinhCSKHSelected);
						if (this.dsNhanVienCSKH.length > 0) {
							this.dsNhanVienCSKHSelected = this.dsNhanVienCSKH[0].id
						}
						// await this.hienThiDsCongViecCSKH();
						this.loading(false);
				}
			},

			"dsNhanVienCSKHSelected": {
				handler: async function (value) {
					this.loading(true);
					await this.hienThiDsCongViecCSKH();
					this.loading(false);
				}
			},
		},
		methods:{
			...mapActions("taocongvieccskh",[
            	"getDsChuongTrinhCSKH",
           ]),

		   ...mapActions("duyetcongvieccskh",[
            	"getDsNhanVienCSKH",
				"getDsCongViecCSKHTheoNguoiTao",
				"clearData"
           ]),

		   	async hienThiDsChuongTrinhCSKH() {
				try {
					let ctCskhPost = {
						kieu: 1,
						pageNo: 1,
						pageSize: 1000
					};
					await this.getDsChuongTrinhCSKH(ctCskhPost);
				} catch (ex) {
				} finally {
					this.loading(false)
				}
        	},

			async hienThiDsNhanVienCSKH(chuongTrinhCSKHId) {
				try {
					let ctCskhPost = {
						ctcsKhId: chuongTrinhCSKHId,
        				trangThaiCv: 1,
						pageNo: 1,
						pageSize: 1000
					};
					await this.getDsNhanVienCSKH(ctCskhPost);
				} catch (ex) {
				} finally {
					this.loading(false)
				}
        	},

			async hienThiDsCongViecCSKH() {
				let cvCskhPost = {
					nhanVienId: this.dsNhanVienCSKHSelected,
					trangThaiCv: 1,
					pageNo: 1,
					pageSize: 1000
				};
				try {
					await this.getDsCongViecCSKHTheoNguoiTao(cvCskhPost);
				}
				catch (ex) {
				}
				finally {
					this.loading(false)
				}
        	},

			async onDuyetClick() {
				if((Buffer.from(this.ghiChuInputTxt).length) > 300) {
					this.$root.toastError(
							"Nhập ghi chú quá dài!"
						);
						return;
				}
				if((this.$refs.gridCongViecCSKHTheoNguoiTao.selectedValues).length == 0) {
					this.$root.toastError(
							"Bạn chưa chọn công việc trên lưới để cập nhật!"
						);
						return;
				}
				this.$bvModal.show("duyetConfirm");
			},

			async onDuyetConfirm() {
				try {
					let i =0;
					for (i=0; i < this.$refs.gridCongViecCSKHTheoNguoiTao.selectedValues.length; i++) {
						let duyetPost = {
							congViecId: this.$refs.gridCongViecCSKHTheoNguoiTao.selectedValues[i],
							nguoiDuyetId: this.$auth.getNhanVienID(),
							ngayDuyet: this.f_DateToString(new Date()),
							duyet: this.duyetCheckBox,
							ghiChu: this.ghiChuInputTxt
						};
						// console.log("duyetpost"+duyetPost);
						await DuyetCongViecCSKHApi.duyetCongViecCSKH(this.axios, duyetPost)
						.then(async (response) => {
							if (response.data.error_code == "BSS-00000000") {
							this.$root.toastSuccess("Duyệt công việc CSKH thành công !");
							await this.hienThiDsCongViecCSKH(this.dsNhanVienCSKHSelected);
							} else
							this.$root.toastError(
								"Không thể duyet bản ghi này: " + response.data.message
							);
						})
						.catch((reject) => {
							this.$root.toastError(
							reject.response.data.message || reject.response.data.error
							);
						});
					}
					await this.hienThiDsChuongTrinhCSKH();
					await this.hienThiDsNhanVienCSKH();
					await this.hienThiDsCongViecCSKH();
					this.ghiChuInputTxt = "";

				} catch (e) {
					console.log("e"+e);
				} finally {
					this.loading(false);
				}
			},

   			clearParam() {
				// this.dsChuongTrinhCSKHSelected= 0
				this.dsNhanVienCSKHSelected= 0,
				this.ghiChuInputTxt= ""
        	},

			f_DateToString: function (value, format = "DD/MM/YYYY") {
				return moment(value).format(format);
			},

			 changeDuyet(event){
				this.duyetCheckBox=event.target.value
				// console.log('vkieu',event.target.value)
				// this.hienthi_dl()
        	},
		},
	}
</script>
