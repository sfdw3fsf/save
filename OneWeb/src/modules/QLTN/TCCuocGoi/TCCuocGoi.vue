<template src="./TCCuocGoi.html"></template>
<style src="./TCCuocGoi.scss"></style>
<script>	
	import select2 from "@/components/Select2.vue";
	import KyCuoc from "../components/KyCuoc.vue";
	import moment from "moment";
	import { mapActions, mapState, mapGetters } from 'vuex';
	import { TrangThaiTB, DichVuVienThong } from "@/const/enums";
	import downloadexcel from "vue-json-excel";
	import xlsx from "xlsx";
	import { Workbook } from "@syncfusion/ej2-excel-export/index";
	// import XuatFile from "../../CSKH/utils/XuatFile.vue";
	import XuatFileCuocGoi from "./XuatFileCuocGoi.vue"
	import Vue from 'vue'

	// import TaoCongViecCSKHApi from "./../api/TaoCongViecCSKHApi";
	export default {
		name: "TCCuocGoi",
		components:{			
			appSelect2: select2, 
			appKyCuoc: KyCuoc,
			downloadexcel,
			appModalXuatDuLieu: XuatFileCuocGoi
		},
		data() {
			return {
				header: {
						title: "Tra cứu cước cuộc gọi",
						list: [
						//   { name: "Trang chủ", link: { name: "Ui.cards" } },
							{
								name: "Quản lý thu nợ",                        
								link: { name: "Ui.buttons" },
						},
							{
								name: "Tra cứu cước cuộc gọi",
								link: { name: "Ui.buttons" },
							},
						],
				},
				pKyCuoc: "",
				txtBiGoi: "",
				txtChuGoi: "",
                txtMaTT: "",
				resetGrid:0,
				danhSachInternet: [],
				danhSachInternetFields: [
					{
						fieldName: "MA_TT",
						headerText: "Mã thanh toán",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "MA_TB",
						headerText: "Số máy/Acc",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "BEGIN_DATE",
						headerText: "Ngày truy cập",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "BEGIN_TIME",
						headerText: "Thời điểm truy cập",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "TG_GOI",
						headerText: "Thời gian truy cập",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "BYTE_IN",
						headerText: "Byte in",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "BYTE_OUT",
						headerText: "Byte out",
						allowFiltering: true,
						allowSorting: false
					},
				],
				danhSachCoDinh:[],
				danhSachCoDinhFields: [
					{
						fieldName: "MA_TT",
						headerText: "Mã thanh toán",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "CHUGOI",
						headerText: "Chủ gọi",
						allowFiltering: true,
						allowSorting: false,
						textAlign: "left"
					},
					{
						fieldName: "BIGOI",
						headerText: "Bị gọi",
						allowFiltering: true,
						allowSorting: false,
						textAlign: "left"
					},
					{
						fieldName: "NGAY_GOI",
						headerText: "Ngày gọi",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "GIO_GOI",
						headerText: "Giờ gọi",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "TG_GOI",
						headerText: "Thời gian gọi",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "TIEN_VND",
						headerText: "Tiền VNĐ",
						allowFiltering: true,
						allowSorting: false,
						cssClass: "text-currency",
						format: 'N2',
						type: 'number' 
					},
					{
						fieldName: "TIEN_USD",
						headerText: "Tiền USD",
						allowFiltering: true,
						allowSorting: false,
						type: 'number' 
					},
					{
						fieldName: "GIAMTRU",
						headerText: "Giảm trừ",
						allowFiltering: true,
						allowSorting: false,
						type: 'number' 
					},
					{
						fieldName: "TENHUONG",
						headerText: "Tên hướng",
						allowFiltering: true,
						allowSorting: false,
					},
					
				],
				sumTemplate: function() {
					return { template: Vue.component('sumTemplate', {
							template: `<input type="text" class="form-control tright" :value="data.Sum" :disabled="true" style="margin: 7px 0px 7px 12px; color: blue; font-weight: bold" />`,
							data: function () {return {data: {data: {}}};}
						})
					}
				}, 
				dsThuocTinhXuatExcel:[],
				danhSachCoDinhXuatExcel: [
					{
						fieldName: "MA_TT",
						headerText: "Mã thanh toán",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "CHUGOI",
						headerText: "Chủ gọi",
						allowFiltering: true,
						allowSorting: false,
						textAlign: "left"
					},
					{
						fieldName: "BIGOI",
						headerText: "Bị gọi",
						allowFiltering: true,
						allowSorting: false,
						textAlign: "left"
					},
					{
						fieldName: "NGAY_GOI",
						headerText: "Ngày gọi",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "GIO_GOI",
						headerText: "Giờ gọi",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "TG_GOI",
						headerText: "Thời gian gọi",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "TIEN_VND",
						headerText: "Tiền VNĐ",
						allowFiltering: true,
						allowSorting: false,
						cssClass: "text-currency",
						format: 'N2',
						type: 'number' 
					},
					{
						fieldName: "TIEN_USD",
						headerText: "Tiền USD",
						allowFiltering: true,
						allowSorting: false,
						type: 'number' 
					},
					{
						fieldName: "GIAMTRU",
						headerText: "Giảm trừ",
						allowFiltering: true,
						allowSorting: false,
						type: 'number' 
					},
					{
						fieldName: "HUONGDT_ID",
						headerText: "HUONGDT_ID",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "TENHUONG",
						headerText: "Tên hướng",
						allowFiltering: true,
						allowSorting: false,
					},
					{
						fieldName: "MAVUNG",
						headerText: "Mã vùng",
						allowFiltering: true,
						allowSorting: false,
					},
				],
				exportExcelFields: {
					"Mã thanh toán": "MA_TT",
					"Chủ gọi":"CHUGOI",
					"Bị gọi": "BIGOI",
					"Ngày gọi":"NGAY_GOI",
					"Giờ gọi":"GIO_GOI",
					"Thời gian gọi":"TG_GOI",
					"Tiền VNĐ":"TIEN_VND",
					"Gỉam trừ":"GIAMTRU",
					"Tên hướng":"TENHUONG"
				},
				huongDamThoaiSelected: 0,
				dichVuVTSelected: 1,
				loaiHinhTBSelected: 356,
				dichVuVTCoDinh: DichVuVienThong.CO_DINH,
				dichVuVTDiDong: DichVuVienThong.DI_DONG,
				dichVuVTADSL: DichVuVienThong.ADSL,
				txtBiGoiEnable: true,
				enableGrid:true,
				kieu: 0,
				dsImport: [],
				danhSachLoaiHinhTB: [{id: 356,text: "VISAT"}],
				loaiHinhTBDisable: true
			}
		},
		async created(){},
		async mounted(){
			this.loading(true);
			await this.getDanhSachHuongDamThoai();
			await this.getDanhSachDichVuVT();
			this.dichVuVTSelected = 1;
			// await this.getDanhSachLoaiHinhTB(this.dichVuVTSelected);
			// this.traCuu();
		},
		computed:{
			aggregatesColumns () {
				return [
					{ field: 'TIEN_VND', type: 'Sum', format: 'N2', footerTemplate: this.sumTemplate }
				]
			},
			dataExcel() {
				return JSON.stringify(this.danhSachCoDinh);
				//return Object.values(this.dsThueBao);
			},
			...mapState("TCCuocGoi", [
            	"danhSachHuongDamThoai",
				"danhSachDichVuVT",
                "danhSachCuocGoi"
       		 ]),

        	...mapGetters("TCCuocGoi", ["danhSachHuongDamThoaiGetter",
										"danhSachDichVuVTGetter",
                                        "danhSachCuocGoiGetter"]),
		},
		watch:{
			"dichVuVTSelected": {
				handler: async function (value) {
					this.loading(true);
					this.enableGrid = false;
					this.clearGrid();
					if(this.dichVuVTSelected == DichVuVienThong.CO_DINH) {
						this.txtBiGoi="";
						// await this.getDanhSachLoaiHinhTB(this.dichVuVTSelected)
						this.danhSachLoaiHinhTB = [
							{id: 356,text: "VISAT"}
						]
						this.$nextTick(() => this.loaiHinhTBSelected = 356)
						// this.loaiHinhTBSelected = 356;
						//txtBiGoi.Enabled = true;
						this.txtBiGoiEnable = true;
						this.loaiHinhTBDisable = true;
					} else if(this.dichVuVTSelected == DichVuVienThong.DI_DONG) {
						// await this.getDanhSachLoaiHinhTB(this.dichVuVTSelected)
						this.danhSachLoaiHinhTB = [
							{id: 20,text: "Di động trả sau"}
						]
						this.$nextTick(() => this.loaiHinhTBSelected = 20)
						// this.loaiHinhTBSelected = 20;
						this.txtBiGoi="";
						//txtBiGoi.Enabled = true;
						this.txtBiGoiEnable = true;
						this.loaiHinhTBDisable = true;

					} else if(this.dichVuVTSelected == DichVuVienThong.ADSL) {
						this.txtBiGoi=""
						//txtBiGoi.Enabled = false;
						this.txtBiGoiEnable = false;
						this.loaiHinhTBDisable = false;
						this.danhSachLoaiHinhTB = [
							{id: 11,text: "Mega"},
          					{id: 58,text: "Fiber"},
							{id: 61,text: "MyTV"}
						]
						this.$nextTick(() => this.loaiHinhTBSelected = 11)
						
					}
					
					this.loading(false);
				},
			},
			"loaiHinhTBSelected": {
				handler: function (value) {
					console.log('loaihinhTB: ', value)
					this.enableGrid = false;
					this.clearGrid();
					this.txtChuGoi="";
					this.txtBiGoi="";
				}
			}
		},
		methods:{
			...mapActions("TCCuocGoi",[
						"getDanhSachHuongDamThoai",
						"getDanhSachDichVuVT",
                        "getDanhSachCuocGoi",
						"getDanhSachCuocGoiTheoFile"
					]),
			handleKyCuoc(e) {
      			this.pKyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no;
			},	
			
			async traCuu() {			
				
                this.loading(true);
				this.clearGrid();
                let data = {
					kyCuoc :  this.pKyCuoc,
					maKH : this.txtMaTT,
					chuGoi : this.txtChuGoi,
					biGoi : this.txtBiGoi,
					huongId : this.huongDamThoaiSelected,
					dichVuVTId : this.dichVuVTSelected,
					loaiTBId : this.loaiHinhTBSelected
                 };
                    // await this.getDanhSachCuocGoi(data);

					const response = await this.getDanhSachCuocGoi(data)
						.then((res) => {
						if (res.data.error_code === "BSS-00000000") {
							this.resetGrid=this.resetGrid+1;
							this.danhSachCoDinh = res.data.data;													
							if (this.danhSachCoDinh.length==0)
									this.$toast.error('Không có dữ liệu. Vui lòng thử lại!');

							this.loading(false);
							this.enableGrid = true;
						}
						})
						.catch((err) => {
						this.loading(false);
						this.$toast.error(err.data.message_detail);
						});
					return response;
			},
			
			clearGrid() {
				// pKyCuoc: "",
				// txtBiGoi: "",
				// txtChuGoi: "",
                // txtMaTT: "",
				// danhSachInternet: [],
				// huongDamThoaiSelected: '',
				// dichVuVTSelected: '',
				// loaiHinhTBSelected: '',
				// dichVuVTCoDinh: DichVuVienThong.CO_DINH,
				// dichVuVTDiDong: DichVuVienThong.DI_DONG,
				// dichVuVTADSL: DichVuVienThong.ADSL,
				// txtBiGoiEnable: false,
				this.danhSachInternet= [],
				this.danhSachCoDinh=[]
			},

			onStartDownload() {
				if (this.danhSachCoDinh.length == 0) {
					this.$root.toastError('Không có dữ liệu để xuất Excel !')
					return
				}
			},

			async onChangeFile(e) {
				
				this.files = e.target.files;
				this.txtFileName = e.target.files[0].name;
				this.txtMaTT=this.txtFileName;
				await this.importExcel();
			},

			async importExcel() {
				const files = this.files;
				console.log(files);
				if (!files.length) {
					return;
				} else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
					this.$root.toastError("File không đúng định dạng xlsx hoặc xls");
				}
				const fileReader = new FileReader();

				fileReader.onload = async (ev) => {
					try {
						this.dsImport = [];
						const data = ev.target.result;
						const XLSX = xlsx;
						const workbook = XLSX.read(data, {
							type: "binary",
						});
						const wsname = workbook.SheetNames[0];
						const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);

						const excellist = [];

						// Edit data
						for (var i = 0; i < ws.length; i++) {
							excellist.push(ws[i]);
							//if (i>0)
							if(excellist[0].hasOwnProperty("CHU_GOI")) {
									this.dsImport.push(ws[i].CHU_GOI);
								} else if (excellist[0].hasOwnProperty("BI_GOI")) {
									this.dsImport.push(ws[i].BI_GOI);
								} else if (excellist[0].hasOwnProperty("MA_TT")) {
									this.dsImport.push(ws[i].MA_TT);
								}
							
						}
					
						var propCount = 0;
						if (excellist && excellist.length > 0) {
							for (var prop in excellist[0]) {
								if (excellist[0].hasOwnProperty(prop)) propCount++;
								// this.dsImport.push(prop);
							}
						}

						if (propCount != 1) {
							this.$root.toastError(
							"File có 1 cột: MA_TT (CHU_GOI, hoặc BI_GOI)"
							);
							return;
						}

						if ((!excellist[0].hasOwnProperty("CHU_GOI")) && (!excellist[0].hasOwnProperty("BI_GOI"))
							&& (!excellist[0].hasOwnProperty("MA_TT")))
						{
							this.$root.toastError("Tên cột không đúng. File có 1 cột: MA_TT (CHU_GOI, hoặc BI_GOI)");
							return;
						}

						if(excellist[0].hasOwnProperty("CHU_GOI")) {
							this.kieu = 1;
						} else if (excellist[0].hasOwnProperty("BI_GOI")) {
							this.kieu = 2;
						} else if (excellist[0].hasOwnProperty("MA_TT")) {
							this.kieu = 3;
						}

						// let rs = await this.$root.context.get('/web-thuno/api/thu-no/tra-cuu/chi-tiet-cuoc-goi-theo-file', {
						// 	// kyCuoc: this.kyCuoc,
						// 	// kieu: this.kieu,
						// 	// ds: this.dsImport,
						// 	// dichVuVTId: this.dichVuVTSelected,
						// 	// loaiTBId: this.loaiHinhTBSelected
						// 	kyCuoc: 20210801,
						// 	kieu: 3,
						// 	ds: ["HPG-04-023251","123456"],
						// 	dichVuVTId: 1,
						// 	loaiTBId: 1
						// })

						// TCCuocGoiAPI.getDanhSachCuocGoi(this._vm.axios, data)
					// 	console.log(this.dsImport);
					// 	let ds1='';						
					// for (let index = 0; index < this.dsImport.length; index++) {
          			// 			ds1=ds1+this.dsImport[index]+",";
        			// 		}
						if (this.dichVuVTSelected==2)
							this.$toast.error('Không tra cứu được theo file đối với dịch vụ Di động');
						else
						{
						this.danhSachCoDinh=[];
						let dataInput = {
							kyCuoc: this.pKyCuoc,
							kieu: this.kieu,
							ds: this.dsImport,
							dichVuVTId: this.dichVuVTSelected,
							loaiTBId: this.loaiHinhTBSelected
						};
							this.loading(true);
						const response = await this.getDanhSachCuocGoiTheoFile(dataInput)
						.then((res) => {
						if (res.data.error_code === "BSS-00000000") {
							
							let tmp_file=[];
							tmp_file = res.data.data;							
							if (tmp_file.length==0)
									this.$toast.error('Không có dữ liệu. Vui lòng thử lại!');
							if (tmp_file[0].TOTAL_ROWS<2000)							
								{
								this.resetGrid=this.resetGrid+1;
								this.danhSachCoDinh = tmp_file;								
								this.enableGrid = true;
								}
							else
								this.$toast.error('File dữ liệu lớn không thể hiển thị');
												
							
						}
						})
						.catch((err) => {
							this.loading(false);
							//this.$toast.error(err.data.message_detail);
						});

						}
						this.loading(false);												
						

					} catch (e) {
							this.$root.toastError(
							"File không có dữ liệu"
							);				
					}
				}

				fileReader.readAsBinaryString(files[0]);
      			var input = document.getElementById("upload");
			},

			onTaiFileMauClicked() {
			let columns = [
				{ index: 1, width: 130 },
				// { index: 2, width: 130 },
			];
			let rows = [];
			let headerRow = {
				index: 1,
				cells: [
				{
					index: 1,
					// value: this.loaiCot == 1 ? "THUE BAO_ID" : "MA_TB",
					value: "MA_TT",
					colSpan: 1,
					style: {
					borders: {
						color: "#00000000",
						lineStyle: "thin",
					},
					},
				},
				// {
				// 	index: 2,
				// 	value: this.loaiCot == 1 ? "MA_TB" : "LYDO",
				// 	colSpan: 1,
				// 	style: {
				// 	borders: {
				// 		color: "#00000000",
				// 		lineStyle: "thin",
				// 	},
				// 	},
				// }
				],
			};

			rows.push(headerRow);
			let worksheets = [{ columns, rows }];
			let workbook = new Workbook({ worksheets }, "xlsx");
			workbook.save("File_mau.xlsx");
			},

			onXuatFileClicked() {
				if (this.dichVuVTSelected == DichVuVienThong.ADSL)
					this.dsThuocTinhXuatExcel=this.danhSachInternetFields;
				
				if (this.dichVuVTSelected == DichVuVienThong.CO_DINH||this.dichVuVTSelected == DichVuVienThong.DI_DONG)
					this.dsThuocTinhXuatExcel=this.danhSachCoDinhXuatExcel;

				if (this.danhSachCoDinh.length <= 0) {
					this.$root.toastError("Không có dữ liệu để xuất!")
					return
				}
				this.$bvModal.show('modal-xuat-dulieu-2')
			}
			// convertSecondToTime(secondInput) {
			// 	let hours = Math.floor(secondInput / 3600);
			// 	let secondInput =  secondInput % 3600;
			// 	let minutes = Math.floor(totalSeconds / 60);
			// 	let seconds = totalSeconds % 60;
			// }
							
		}
	}
</script>