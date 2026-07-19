<template src="./TraCuuHoaDonDienTuKH.html"></template>
<style src="./TraCuuHoaDonDienTuKH.scss"></style>
<script>
	import select2 from "@/components/Select2.vue";
	import API from "../api/HoaDonDienTuAPI";
	import { checkStatus } from "./checkStatus";
	import moment from "moment";
	import EventBus from '@/utils/eventBus'
	import Vue from 'vue'

	export default {
		name:"TraCuuHoaDonDienTuKH",
		components:{
			appSelect2: select2,
			// confirmPopup: YesNoModal,
		},

		data() {
			return {
				header: {
						title: "TRA CỨU HÓA ĐƠN ĐIỆN TỬ KHÁCH HÀNG",
						list: [
						//   { name: "Trang chủ", link: { name: "Ui.cards" } },
							{
								name: "Quản lý thu nợ",
								link: { name: "Ui.buttons" },
						},
							{
								name: "Tra cứu hóa đơn điện từ khách hàng",
								link: { name: "Ui.buttons" },
							},
						],
				},
				downloadInvTemplate: function () {
					return { template : Vue.component('columnTemplate', {
						template: `<div><button class="btn btn-primary lh14 pad3" @click="onDownloadInv" title="Tải file .inv">
										<span class="-ap icon-download"></span>
									</button></div>`,
						data () {
							return { data: {} };
						},
						methods: {
							onDownloadInv() {
								EventBus.$emit('onDownloadInv', this.data);
							}
						}})
					}
				},
				downloadPdfTemplate: function () {
					return { template : Vue.component('columnTemplate', {
						template: `<div><button class="btn btn-primary lh14 pad3" @click="onDownloadPdf" title="Tải file .pdf">
										<span class="-ap icon-download"></span>
									</button></div>`,
						data () {
							return { data: {} };
						},
						methods: {
							onDownloadPdf() {
								EventBus.$emit('onDownloadPdf', this.data);
							}
						}})
					}
				},
                downloadXmlTemplate: function () {
					return { template : Vue.component('columnTemplate', {
						template: `<div><button class="btn btn-primary lh14 pad3" @click="onDownloadXml" title="Tải file .xml">
										<span class="-ap icon-download"></span>
									</button></div>`,
						data () {
							return { data: {} };
						},
						methods: {
							async onDownloadXml() {
							    await this.$parent.$parent.$parent.downloadXML(this.data)
							}
						}})
					}
				},
				viewInvTemplate: function () {
					return { template : Vue.component('columnTemplate', {
						template: `<div><a class="text-danger" href="javascript: void(0)" @click="onViewInv" title="Xem chi tiết"><span></span>Xem chi tiết</a></div>`,
						data () {
							return { data: {} };
						},
						methods: {
							onViewInv() {
								EventBus.$emit('onViewInv', this.data);
							}
						}})
					}
				},


				loaiTraCuu: 'maTT',
				txtMaTT: '',
				txtFKey: '',
				txtToken: '',
				chkTuNgay: false,
				chkDenNgay: false,
				danhSachHoaDon: [],
				maTTDisabled: false,
				fKeyDisabled: true,
				tokenDisabled: true,
				tuNgay: new Date(),
				denNgay: new Date(),
				today: new Date()
			}
		},
		async created () {
			EventBus.$on('onDownloadInv', this.onDownloadInvHandler);
			EventBus.$on('onDownloadPdf', this.onDownloadPdfHandler);
			EventBus.$on('onViewInv', this.onViewInvHandler);
		},

		destroyed () {
			EventBus.$off('onDownloadInv', this.onDownloadInvHandler);
			EventBus.$off('onDownloadPdf', this.onDownloadPdfHandler);
			EventBus.$off('onViewInv', this.onViewInvHandler);
		},
		async mounted(){

		},
		computed:{
          returnDanhSachHoaDon(){
            let array = this.danhSachHoaDon.sort(this.sortFunction)
            array = array.map((item,index)=>({...item,publishDate:moment(item.publishDate,'MM/DD/YYYY HH:mm:ss a').format('DD/MM/YYYY'),stt:++index}))
            return array
          }
        },
		watch:{
				"loaiTraCuu": {
					handler: async function (value) {
						if (this.loaiTraCuu==='maTT') {
							this.maTTDisabled = false;
							this.fKeyDisabled = true;
							this.tokenDisabled = true;
						}  else if (this.loaiTraCuu==='fKey') {
							this.maTTDisabled = true;
							this.fKeyDisabled = false;
							this.tokenDisabled = true;
						} else if (this.loaiTraCuu==='token') {
							this.maTTDisabled = true;
							this.fKeyDisabled = true;
							this.tokenDisabled = false;
						}
					},
				}
		},
		methods:{
			sortFunction(a,b){
                var dateA = new Date(a.publishDate).getTime();
                var dateB = new Date(b.publishDate).getTime();
                return dateA < dateB ? 1 : -1;
            },
			async onTraCuuClick(){
				if (this.loaiTraCuu==='maTT'){
					if (this.txtMaTT !== ""){
                        this.traCuuTheoMaTT();
                    } else {
						this.$root.toastError("Chưa nhập Mã thanh toán!" );
					}
				} else if (this.loaiTraCuu==='fKey'){
					if (this.txtFKey !== ""){
                        this.traCuuTheoFkey();
                    } else {
						this.$root.toastError("Chưa nhập Fkey!" );
					}
				}  else if (this.loaiTraCuu==='token'){
					if (this.txtToken !== ""){
                        this.traCuuTheoToken();
                    } else {
						this.$root.toastError("Chưa nhập Token!" );
					}
				}
			},

			async traCuuTheoMaTT() {
				// this.loading(true);
				// const data = {
				// 	cusCode: this.txtMaTT,
				// 	fromDate: "",
				// 	toDate: "",
				// 	phanvung_id: 28,
				// };
				// const res = await API.getDanhSachHoaDon(this.axios, data);
				// if (res.data.error_code === "BSS-00000000") {
				// 	this.loading(false);
				// 	this.danhSachHoaDon = res.data.data
				// } else {
				// 	this.loading(false);
				// 	this.$toast.error(err.data.message_detail);
				// }

				 const data = {
					cusCode: this.txtMaTT,
					fromDate: this.chkTuNgay
					? moment(this.tuNgay).format("DD/MM/YYYY")
					: "",
					toDate: this.chkTuNgay
					? moment(this.denNgay).format("DD/MM/YYYY")
					: "",
					phanvung_id: this.$auth.getPhanVungID(),
				};
				try {
					this.loading(true);
					this.danhSachHoaDon = [];
					const response = await API.getDanhSachHoaDon(this.axios, data);
					if (
					response.data.error_code === "BSS-00000000" &&
					response.data.data.length > 0
					) {
					let count = 1;
					const result = response.data.data.map((item) => ({
						...item,
						status_chu: checkStatus(item.status),
						detail : "Xem chi tiết",
						payment_chu: (item.payment == 1 ? "Đã thanh toán" : "Chưa thanh toán"),
						signStatus_chu: item.signStatus === '0' ? 'Chưa xem' : 'Đã xem',
						stt: count++
					}));
					this.danhSachHoaDon = result;
					} else {
						this.danhSachHoaDon = [];
						this.$root.toastError(
						"Không tìm thấy hóa đơn theo tiêu chí đã nhập !"
						);
					}
				} catch (error) {
					this.danhSachHoaDon = [];
					if(error.data.error_code === "BSS-00004008"){
						this.$toast.error("Không tồn tại khách hàng tương ứng với Mã GD "+this.txtMaTT)
					} else {
						this.$root.toastError(error.data.message);
					}
				} finally {
					this.loading(false);
				}

			},

			async getDSHoaDonByFkey() {
				const data = {
					key: this.txtFKey,
					fromDate: this.chkTuNgay
					? moment(this.tuNgay).format("DD/MM/YYYY")
					: "",
					toDate: this.chkTuNgay
					? moment(this.denNgay).format("DD/MM/YYYY")
					: "",
					phanvung_id: this.$auth.getPhanVungID(),
				};

				try {
					this.loading(true);
					const response = await API.getDanhSachHoaDonByFkey(this.axios, data);
					if (
					response.data.error_code === "BSS-00000000" &&
					response.data.data.length > 0
					) {
						let count = 1;
						const result = response.data.data.map((item) => ({
						...item,
						status_chu: checkStatus(item.status),
						payment_chu: (item.payment == 1 ? "Đã thanh toán" : "Chưa thanh toán"),
						signStatus_chu: item.signStatus === '0' ? 'Chưa xem' : 'Đã xem',
                        cuscode: this.params.maTT,
                        stt: count++
					}));
					this.danhSachHoaDon = result;
					} else {
					this.danhSachHoaDon = [];
					this.$root.toastError(
						"Không tìm thấy hóa đơn theo tiêu chí đã nhập !"
					);
					}
				} catch (error) {
					this.danhSachHoaDon = [];
					this.$root.toastError(error.data.message);
				} finally {
					this.loading(false);
				}

			},

			async traCuuTheoFkey() {
				try {
					this.loading(true);
					this.danhSachHoaDon = [];
					let apiBody = {
						key: this.txtFKey,
						fromDate: this.chkTuNgay ? moment(this.tuNgay).format("DD/MM/YYYY") : "",
						toDate: this.chkDenNgay ? moment(this.denNgay).format("DD/MM/YYYY") : "",
						phanvung_id: this.$auth.getPhanVungID()
					};
					let response = await API.getDanhSachHoaDonByFkey(this.axios, apiBody);
					if (response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
						let count = 1;
						const result = response.data.data.map((item) => ({
							...item,
							status_chu: checkStatus(item.status),
							payment_chu: (item.payment == 1 ? "Đã thanh toán" : "Chưa thanh toán"),
							signStatus_chu: item.signStatus === '0' ? 'Chưa xem' : 'Đã xem',
							cuscode: this.txtMaTT,
							invToken: `${item.pattern};${item.serial};${item.invNum}`,
							stt: count++
						}));
						this.danhSachHoaDon = result;
					} else {
						this.danhSachHoaDon = [];
						this.$root.toastError("Không tìm thấy hóa đơn theo tiêu chí đã nhập !");
					}

				} catch (error) {
					console.log(error)
					this.$toast.error(error.data.message);
				}
				finally {
					this.loading(false);
				}
			},

			async traCuuTheoToken() {
				try {
                	if (this.txtToken == ""){
						this.danhSachHoaDon=[];
						this.$root.toastError("Bạn chưa nhập Token");
					} else {
						const data = {
							invToken: this.txtToken,
							phanvung_id: this.$auth.getPhanVungID(),
						};
						this.loading(true);
						const response = await API.getChiTietHoaDon(this.axios, data);
						if (response.data.error_code === "BSS-00000000") {
							if(response.data.data.hasOwnProperty("Invoice")){
                                const result = response.data.data.Invoice.Content;
                                this.txtMaTT = result.CusCode
                            } else if(response.data.data.hasOwnProperty("DLHDon")){
                                const result = response.data.data.DLHDon;
                                this.txtMaTT = result.NDHDon.NMua.MKHang
                            }
								this.traCuuTheoMaTT();
							// else {
							// 	this.danhSachHoaDon = [];
							// // this.$root.toastError(
							// // "Không tìm thấy hóa đơn theo tiêu chí đã nhập !"
							// // );
							// }
						} else {
							this.$root.toastError("Không tìm thấy hóa đơn theo tiêu chí đã nhập !");
						}
					}
            //     {
            //         Message_Box.ShowWarning("Bạn chưa nhập Token");
            //         txtToken.Focus();
            //         return;
            //     }

            //     string invToken = txtToken.Text.Trim();
            //     string xml = invoice.downloadInvNoPay_V2(invToken, tt_nd.user_hddt, tt_nd.pass_hddt);
            //     if (xml.StartsWith("ERR:"))
            //     {
            //         Message_Box.ShowError("Mã lỗi: " + xml);
            //         gridKetQua.DataSource = null;
            //         txtMaTT.Text = "";
            //         return;
            //     }
            //     else
            //     {
            //         XmlDocument doc = new XmlDocument();
            //         doc.LoadXml(xml);
            //         XmlNode xnList = doc.SelectNodes("/Invoice/Content").Item(0);
            //         XmlDocument doc1 = new XmlDocument();
            //         doc1.LoadXml("<Content>" + xnList.InnerXml + "</Content>");
            //         objHoaDonDienTu = (Content)ObjectToXML(doc1.InnerXml, typeof(Content));

            //         txtMaTT.Text = objHoaDonDienTu.CusCode;
            //         TraCuuTheoMaTT();
            //     }
            	} catch (error) {
					console.log(error)
					if (error.data.message)
						this.$toast.error(error.data.message);
					else
					this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
					// return null;
				} finally {
					this.loading(false);
				}
			},

		 	async onDownloadInvHandler(item) {
                if(item.fkey == 'null'){
                  this.$toast.error('Hóa đơn không có fkey !');
                  return;
                }
				let rs = await this.postDownloadInvFkeyNoPayXml(item.fkey);
				if (rs !== null) {
					let content = rs;
					var FileSaver = require("file-saver");
					var file = new File([content], item.invNum + '.inv', {
						type: "application/inv"
					});
					FileSaver.saveAs(file);
				}
			},

			async onDownloadPdfHandler(item) {
                if(item.fkey == 'null'){
                  this.$toast.error('Hóa đơn không có fkey !');
                  return;
                }
				let rs = await this.postDownloadInvPDFFkeyNoPay(item.fkey);
				if (rs !== null) {
					let content = Buffer.from(rs, 'base64');
					var FileSaver = require("file-saver");
					var file = new File([content], item.invNum, {
						type: "application/pdf"
					});
					FileSaver.saveAs(file);
				}
			},

            async downloadXML(item){
                if(item.fkey == 'null'){
                  this.$toast.error('Hóa đơn không có fkey !');
                  return;
                }
                let rs = await this.postDownloadInvFkeyNoPayXml(item.fkey);
				if (rs !== null) {
					let content = rs;
					var FileSaver = require("file-saver");
					var file = new File([content], item.invNum + '.xml', {
						type: "application/xml"
					});
					FileSaver.saveAs(file);
				}
            },

			async onViewInvHandler(item) {
				 let rs = await this.getInvViewNoPay(item.invToken);
				if (rs !== null) {
					var newTab = window.open('', '_blank');
					newTab.document.body.innerHTML = rs;
				}
			},

			async postDownloadInvFkeyNoPayXml(fkey) {
				try {
					this.loading(true);
					let apiBody = {
						fkey: fkey,
						phanvung_id: this.$auth.getPhanVungID()
					};
					let response = await API.downloadInvFkeyNoPay(this.axios, apiBody);
					if (response.data.errorCode == 0) {
						return response.data.data;
					} else return null;
				}
				catch (error) {
					if (error.data.message) {
						this.$toast.error(error.data.message);
					} else this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
					return null;
				}
				finally {
					this.loading(false);
				}
			},

			async postDownloadInvPDFFkeyNoPay(fkey) {
				try {
					this.loading(true);
					let apiBody = {
						fkey: fkey,
						phanvung_id: this.$auth.getPhanVungID()
					};
					let response = await API.postDownloadInvPDFFkeyNoPay(this.axios, apiBody);
					if (response.data.error_code === 'BSS-00000000') {
						return response.data.data.response;
					} else return null;
				}
				catch (error) {
					if (error.data.message)
						this.$toast.error(error.data.message);
					else this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
					return null;
				}
				finally {
					this.loading(false);
				}
			},

			async getInvViewFkey(fkey) {
				try {
					this.loading(true);
					let apiBody = {
						fkey: fkey,
						phanvung_id: this.$auth.getPhanVungID()
					};
					let response = await API.getInvViewFkey(this.axios, apiBody);
					if (response.data.error_code === 'BSS-00000000') {
						return response.data.data.response;
					} else return null;
				}
				catch (error) {
					this.$toast.error(error.data.message);
					return null;
				}
				finally {
					this.loading(false);
				}
			},

			async getInvViewNoPay(invToken) {
				try {
					this.loading(true);
					let apiBody = {
						invToken: invToken,
						phanvung_id: this.$auth.getPhanVungID()
					};
					let response = await API.getInvViewNoPay(this.axios, apiBody);
					if (response.data.error_code === 'BSS-00000000') {
						return response.data.data.response;
					} else return null;
				}
				catch (error) {
					if (error.data.message)
						this.$toast.error(error.data.message);
					else this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
					return null;
				}
				finally {
					this.loading(false);
				}
       	 	}
		},
	}
</script>
