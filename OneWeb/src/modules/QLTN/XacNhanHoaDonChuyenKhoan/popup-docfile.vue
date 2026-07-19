<template>
	<b-modal
		id="modal-docfile"
		size="lg"
		hide-footer
		hide-header
		hide-header-close
		body-class="modal-body p-0"
		@hide="resetData"
	>
		<div class="modal-content popup-box">
			<div class="popup-header">
				<div class="title"><span class="icon one-notepad"></span>Đọc file</div>
				<div
					class="close -ap icon-close"
					data-dismiss="modal"
					@click="close()"
				></div>
			</div>
			<div class="list-actions-top">
				<ul class="list">
					<li @click="DanhSach()">
						<a href="javascript:;">
							<span class="icon design_bullet-list-67 nc-icon-glyph"></span>
							Danh sách
						</a>
					</li>
					<li>
						<a href="javascript:void(0)" @click="ChapNhan()">
							<span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp
							nhận
						</a>
					</li>
					<li @click="XuatExcel()">
						<a href="javascript:;">
							<span class="icon one-excel"></span> Xuất Excel
						</a>
					</li>
				</ul>
			</div>
			<div class="popup-body">
				<div class="box-form">
					<div class="info-row">
						<div class="key w90">Đường dẫn</div>
						<div class="value">
							<div class="input-more-button">
								<label for="upload" class="btn">
									<span class="-ap icon-more_horiz"></span>
									<input
										ref="fileInput"
										type="file"
										id="upload"
										style="display: none"
										@change="onChangeFile"
										accept=".xls, .xlsx"
										@click="$refs.fileInput.value = null"
									/>
								</label>
								<input
									autofocus
									ref="duongDan"
									v-model="duongDan"
									type="text"
									class="form-control highlight"
								/>
							</div>
						</div>
					</div>
				</div>
				<div class="box-form">
					<div class="legend-title">
						<div class="pull-left">Danh sách công nợ</div>
						<div class="pull-right red">00:00:00</div>
						<div class="clearfix"></div>
					</div>
					<div class="nav tabs tab-over">
						<a
							href="javascript:;"
							data-toggle="tab"
							:class="{ active: !isTab }"
							@click="isTab = false"
						>
							<span
								class="
									nc-icon-glyph
									design_bullet-list-67
									text-main
									f20
									inline
									vcenter
								"
							></span>
							Danh sách
						</a>
						<a
							href="javascript:;"
							data-toggle="tab"
							:class="{ active: isTab }"
							@click="isTab = true"
						>
							<span class="one-alert text-warning f20 inline vcenter"></span>Lỗi
						</a>
					</div>
					<div class="tab-content">
						<div class="tab-pane active" id="tabDS" v-show="!isTab">
							<table-c
								:columns="fieldsLanTao"
								:dataSource="dsLanTao"
								:showFilter="true"
								ref="tabIn"
							></table-c>
						</div>
						<div class="tab-pane active" id="tabLoi" v-show="isTab">
							<table-c
								:columns="fieldsLoi"
								:dataSource="dsLoi"
								:showFilter="true"
								ref="tabLoi"
							></table-c>
						</div>
					</div>
				</div>
			</div>
		</div>
	</b-modal>
</template>
<style></style>
<script>
	import xlsx from "xlsx";
	import { mapActions, mapState, mapGetters } from "vuex";

	export default {
		name: "PopupDocFile",
		mounted() {},
		props: ["kyCuoc"],
		data() {
			return {
				fieldsLoi: [
					{
						fieldName: "STT",
						headerText: "STT",
						allowFiltering: true,
					},
					{
						fieldName: "MA_XLN",
						headerText: "Mã XLN",
						allowFiltering: true,
					},
					{
						fieldName: "MA_TB",
						headerText: "Số máy/Acc",
						allowFiltering: true,
					},
					{
						fieldName: "MA_NV",
						headerText: "Mã NV",
						allowFiltering: true,
					},
					{
						fieldName: "TT_HS",
						headerText: "Trạng thái HS",
						allowFiltering: true,
					},
					{
						fieldName: "TRANGTHAI",
						headerText: "Trạng thái",
						allowFiltering: true,
					},
				],
				fieldsLanTao: [
					{
						fieldName: "STT",
						headerText: "STT",
						allowFiltering: true,
					},
					{
						fieldName: "MA_TT",
						headerText: "Mã TT",
						allowFiltering: true,
					},
					{
						fieldName: "TEN_TT",
						headerText: "Tên TT",
						allowFiltering: true,
					},
					{
						fieldName: "DIACHI_TT",
						headerText: "Địa chỉ TT",
						allowFiltering: true,
					},
					{
						fieldName: "DIACHI_CT",
						headerText: "Địa chỉ CT",
						allowFiltering: true,
					},
					{
						fieldName: "SO_DT",
						headerText: "Số ĐT",
						allowFiltering: true,
					},
					{
						fieldName: "FAX",
						headerText: "Fax",
						allowFiltering: true,
					},
					{
						fieldName: "MA_NVQL",
						headerText: "Mã NVQL",
						allowFiltering: true,
					},
					{
						fieldName: "TUYEN_THU",
						headerText: "Tuyến thu",
						allowFiltering: true,
					},
					{
						fieldName: "TIEN_NO",
						headerText: "Tiền nợ",
						allowFiltering: true,
					},
					{
						fieldName: "TIEN_THUA",
						headerText: "Tiền thừa",
						allowFiltering: true,
					},
				],
				dsTemp: [],
				duongDan: "",
				dsLoi: [],
				dsLanTao: [],
				sourceFile: null,
				checkLoi: false,
				isTab: false,
			};
		},
		computed: {
			...mapGetters("TaoDuLieuXacNhanCongNo", [""]),
		},
		methods: {
			...mapActions("TaoDuLieuXacNhanCongNo", ["getTuFile", "clearData"]),

			close() {
				this.$bvModal.hide("modal-docfile");
			},

			XuatExcel() {
				if (this.dsLanTao.length <= 0) {
					this.$root.toastError("Chưa có danh sách để xuất Excel!");
					return;
				}
				this.loading(true);
				try {
					var dsach = xlsx.utils.json_to_sheet(this.dsLanTao);
					var wb = xlsx.utils.book_new(); // make Workbook of Excel
					xlsx.utils.book_append_sheet(wb, dsach, "TB"); // sheetAName is name of Worksheet
					// export Excel file
					xlsx.writeFile(wb, "mau.xlsx");
				} catch (error) {
				} finally {
					this.loading(false);
				}
			},

			async DanhSach() {
				if (this.duongDan.trim() === "") {
					this.$root.toastWarning("Hãy chọn đường dẫn!");
					this.$refs.fileInput.focus();
					return;
				}

				if (this.dsLoi.length > 0) {
					this.isTab = true;
					return;
				}

				const dataBody = {
					kyCuoc: this.kyCuoc,
					dsFile: this.dsDanhSachTemp,
				};
				this.loading(true);
				const response = await this.getTuFile(dataBody);
				if (response.data.error_code === "BSS-00000000") {
					this.loading(false);
					if (response.data.data !== undefined && response.data.data.length > 0) {
						console.log({ response });
						this.isTab = true;
						this.dsLoi = response.data.data;
						this.$refs.tabLoi.setCurrentSelectedRow(0);
					} else {
						this.isTab = false;
						this.dsLanTao = this.dsTemp;
						this.$refs.tabIn.setCurrentSelectedRow(0);
						this.dsLoi = [];
					}
				} else {
					this.loading(false);
					this.$root.toastError(response.message_detail);
				}
			},

			// DocFile() {
			// 	if (this.duongDan === "") {
			// 		this.$root.toastError("Hãy chọn đường dẫn!");
			// 		return;
			// 	}
			// 	this.onChangeFile(this.sourceFile);
			// },

			// onLoadFile(e) {
			// 	this.sourceFile = e;
			// 	this.duongDan = e.target.files[0].name;
			// 	this.dsLoi = [];
			// },

			async getDataFromFile(ws) {
				const listMaTB = ws.map((item) => {
					return {
						ma_tb: item.MA_TB,
						dichvuvt_id: this.dichVu.id,
					};
				});

				const data = {
					pNguoiDungId: this.$auth.getNhanVienID(),
					pData: listMaTB,
				};
				try {
					this.loading(true);
					const response = await this.getListTDTuFile(data);
					return response.data.data;
				} catch (e) {
				} finally {
					this.loading(false);
				}
			},

			async onChangeFile(e) {
				const files = e.target.files;
				this.duongDan = e.target.files[0].name;
				if (!files.length) {
					return;
				} else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
					return this.$root.toastWarning("File excel không đúng định dạng!");
				}
				const fileReader = new FileReader();
				fileReader.onload = async (ev) => {
					try {
						this.dsLoi = [];
						this.dsLanTao = [];
						const data = ev.target.result;
						const XLSX = xlsx;
						const workbook = XLSX.read(data, {
							type: "binary",
						});
						const wsname = workbook.SheetNames[0];
						const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
						console.log({ ws });
						const excellist = [];
						let dsDanhSach = [];
						let dsMaTT = [];
						let dsDuplicateMaTT = [];
						// Edit data
						for (var i = 0; i < ws.length; i++) {
							excellist.push(ws[i]);
							dsMaTT.push(ws[i].MA_TT);
							dsDanhSach.push({
								maTT: ws[i].MA_TT,
								tenTT: ws[i].TEN_TT,
								diaChiCT: ws[i].DIACHI_CT,
								diaChiTT: ws[i].DIACHI_TT,
								sdt: ws[i].SO_DT,
								fax: ws[i].FAX,
								// pbhId: ws[i].MA_TT,
								// tenPBH: ws[i].MA_TT,
								// toBanHang: ws[i].MA_TT,
								maNVQL: ws[i].MA_NVQL,
								tuyenThu: ws[i].TUYEN_THU,
								// maDTuong: ws[i].MA_TT,
								// httt: ws[i].MA_TT,
								tienNo: ws[i].TIEN_NO,
								tienThua: ws[i].TIEN_THUA,
								chuKyNo: ws[i].CHUKYNO,
							});
						}
						console.log({ dsDanhSach });
						// var propCount = 0;
						// if (excellist && excellist.length > 0) {
						// 	for (var prop in excellist[0]) {
						// 		if (excellist[0].hasOwnProperty(prop)) propCount++;
						// 	}
						// }
						// if (propCount == 0) {
						// 	this.dsLoi.push({
						// 		MA_TT: "",
						// 		GHICHU: "Không có thứ tự;Không có đợt",
						// 	});
						// 	return;
						// }
						// if (propCount < 3) {
						// 	this.$refs.duongDan.focus();
						// 	this.$refs.duongDan.select();
						// 	this.$root.toastWarning("Số lượng cột phải >= 3 cột!");
						// 	return;
						// }
						// const arrayCol = Object.keys(excellist[0]);
						// if (
						// 	arrayCol[0] !== "MA_TT" ||
						// 	arrayCol[1] !== "THUTU" ||
						// 	arrayCol[2] !== "DOT"
						// ) {
						// 	this.$refs.duongDan.focus();
						// 	this.$refs.duongDan.select();
						// 	this.$root.toastWarning("Thứ tự cột phải là: MA_TT, THUTU, DOT!");
						// 	return;
						// }

						dsMaTT.forEach((element) => {
							if (
								dsMaTT.indexOf(element) != dsMaTT.lastIndexOf(element) &&
								!dsDuplicateMaTT.includes(element)
							)
								dsDuplicateMaTT.push(element);
						});
						dsDuplicateMaTT.forEach((element) => {
							this.dsLoi.push({
								MA_TT: element,
								GHICHU: "Thanh toán bị lặp lại",
							});
						});

						this.dsTemp = ws;
						this.dsDanhSachTemp = dsDanhSach;
					} catch (e) {
						return this.$root.toastWarning("Kiểm tra dữ liệu file đầu vào!");
					}
				};
				fileReader.readAsBinaryString(files[0]);
			},
			// async onChangeFile(e) {
			// 	const files = e.target.files;

			// 	if (!files.length) {
			// 		return;
			// 	} else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
			// 		return this.$toast.warning("File excel không đúng định dạng!");
			// 	}
			// 	const fileReader = new FileReader();
			// 	fileReader.onload = async (ev) => {
			// 		try {
			// 			const data = ev.target.result;
			// 			const XLSX = xlsx;
			// 			const workbook = XLSX.read(data, {
			// 				type: "binary",
			// 			});
			// 			const wsname = workbook.SheetNames[0];
			// 			const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
			// 			const excellist = [];
			// 			// Edit data
			// 			for (var i = 0; i < ws.length; i++) {
			// 				excellist.push(ws[i]);
			// 			}

			// 			var propCount = 0;
			// 			if (excellist && excellist.length > 0) {
			// 				for (var prop in excellist[0]) {
			// 					if (excellist[0].hasOwnProperty(prop)) propCount++;
			// 				}
			// 			}

			// 			if (propCount != 1) {
			// 				this.$toast.warning(
			// 					"Số lượng cột phải = 1 cột, hiện đang có " + propCount + " cột!"
			// 				);
			// 				return;
			// 			}

			// 			if (!excellist[0].hasOwnProperty("MA_TB")) {
			// 				this.$toast.warning("Cột đầu tiên trong file phải là MA_TB!");
			// 				return;
			// 			}

			// 			let dsMaTT = await this.getDataFromFile(ws);
			// 			dsMaTT.forEach((item) => {
			// 				if (item.TRANGTHAI_ID !== "0") {
			// 					console.log({ item });
			// 					this.checkLoi = true;
			// 					return;
			// 				}
			// 				this.checkLoi = false;
			// 			});
			// 			console.log({ check: this.checkLoi });
			// 			this.dsLoi = dsMaTT.map((item) => ({
			// 				MA_TB: item.MA_TB,
			// 				TRANGTHAI: item.TRANGTHAI,
			// 			}));

			// 			this.$refs.danhSach.setCurrentSelectedRow(0);
			// 		} catch (e) {
			// 			console.log(e);
			// 			return this.$toast.warning("Kiểm tra dữ liệu file đầu vào!");
			// 		}
			// 	};
			// 	fileReader.readAsBinaryString(files[0]);
			// },

			async ChapNhan() {
				if (this.dsLanTao.length === 0) {
					this.$root.toastError("Chưa có danh sách lần tạo!");
					return;
				}
				console.log("data", this.dsDanhSachTemp);
				this.$emit("sendData", this.dsDanhSachTemp);
				this.$bvModal.hide("modal-docfile");
			},

			resetData() {
				this.duongDan = "";
				this.dsLoi = [];
			},
		},
	};
</script>
