<script>
import breadcrumb from '@/components/breadcrumb'
import api from "./api";
import moment from "moment";
import xlsx from 'xlsx';

export default {
	name: 'ExportInvoice',
	components: {
		breadcrumb
	},
	data() {
		return {
			message: "",
			resolveAction() {
			},
			header: {
				title: 'PHÁT HÀNH HOÁ ĐƠN ĐIỆN TỬ KHDN',
				list: [
					{ name: 'Thanh toán', link: { name: 'Ui.cards' } },
					{ name: 'Phát hành hóa đơn điện tử KHDN', link: { name: 'Ui.cards' } },
				]
			},

			cboTuThang_Value: this.lay_ky_truoc(),
			cboDenThang_Value: this.lay_ky_truoc(),
			cboTrangThai_Value: -1,

			/**@type {Array<string>}*/
			ds_ma_tt: [],

			/**@type {Array<{ma_tt: String, ky_cuoc: Number}>}*/
			ds_ma_tt_can_ph: [],

			dsThanhToan_DataSource: [],
			dsThanhToan_Columns: [
				{ fieldName: 'ma_tt', headerText: 'Mã thanh toán', allowfiltering: true },
				{ fieldName: 'ky_cuoc', headerText: 'Kỳ cước', allowfiltering: true, width: 100 },
			],
			dsThanhToan_CommandColumn: {
				headerText: 'Xoá',
				allowfiltering: false,
				width: 50,
				cellCssClass: 'center',
				firstColumn: false,
				show: true
			},
			dsThanhToan_Commands: [
				{
					name: 'xoa',
					cssClass: 'btn btn-primary icon one-trash',
					width: 40,
					title: 'Xóa mã thanh toán',
				},
			],

			dsHoaDon_DataSource: [],
			dsHoaDon_Columns: [
				{ fieldName: 'ma_tt', headerText: 'Mã thanh toán', allowfiltering: true, freeze: 'left' },
				{ fieldName: 'ky_cuoc', headerText: 'Kỳ cước', allowfiltering: true, width: 100 },
				{ fieldName: 'dot', headerText: 'Đợt', allowfiltering: true, width: 100 },
				{ fieldName: 'fkey', headerText: 'FKey', allowfiltering: true },

				{ fieldName: 'ma_khachhang', headerText: 'Mã khách hàng', allowfiltering: true },
				{ fieldName: 'ten_khachhang', headerText: 'Tên khách hàng', allowfiltering: true },
				{ fieldName: 'ms_thue', headerText: 'Mã số thuế', allowfiltering: true },
				{ fieldName: 'ten_khachhang', headerText: 'Tên khách hàng', allowfiltering: true },
				{ fieldName: 'diachi', headerText: 'Địa chỉ thanh toán', allowfiltering: true },
				{ fieldName: 'tien', headerText: 'Tiền', allowfiltering: true, format: "N0", type: 'number' },
				{ fieldName: 'thue', headerText: 'Thuế', allowfiltering: true, format: "N0", type: 'number' },
				{ fieldName: 'noidung', headerText: 'Nội dung', allowfiltering: true },

				{ fieldName: 'pattern', headerText: 'Pattern', allowfiltering: true },
				{ fieldName: 'serial', headerText: 'Serial', allowfiltering: true },
				{ fieldName: 'no', headerText: 'No', allowfiltering: true },

				{ fieldName: 'ngay_ph', headerText: 'Ngày PH', allowfiltering: true },
				{ fieldName: 'nguoi_ph', headerText: 'Người PH', allowfiltering: true },
				{ fieldName: 'status', headerText: 'TTPH', allowfiltering: true, width: 80, textAlign: 'center', freeze: 'right' },
			],
			dsHoaDon_CommandColumn: {
				headerText: 'Chức năng',
				allowfiltering: false,
				width: 120,
				cellCssClass: 'center',
				firstColumn: true,
				show: true,
				freeze: true
			},
			dsHoaDon_Commands: [
				{ name: 'xem', cssClass: 'btn btn-primary one-eye', width: 40, title: 'Xem hóa đơn', },
				{ name: 'tai', cssClass: 'btn btn-success one-download', width: 40, title: 'Tải hóa đơn', },
				{ name: 'sync', cssClass: 'btn btn-danger one-td-arrow-change', width: 40, title: 'Đồng bộ Serial', },
				{ name: 'edit', cssClass: 'btn btn-warning one-edit', width: 40, title: 'Sửa nội dung', },
			],

			dsPreview_Columns: [
				{ fieldName: 'ma_tt', headerText: 'Mã thanh toán', allowfiltering: true, freeze: 'left' },
				{ fieldName: 'ky_cuoc', headerText: 'Kỳ cước', allowfiltering: true, width: 100, freeze: 'left' },
				{ fieldName: 'dot', headerText: 'Đợt', allowfiltering: true, width: 100 },
				{ fieldName: 'fkey', headerText: 'FKey', allowfiltering: true },
				{ fieldName: 'ma_khachhang', headerText: 'Mã khách hàng', allowfiltering: true },
				{ fieldName: 'ten_khachhang', headerText: 'Tên khách hàng', allowfiltering: true },
				{ fieldName: 'ms_thue', headerText: 'Mã số thuế', allowfiltering: true },
				{ fieldName: 'ten_khachhang', headerText: 'Tên khách hàng', allowfiltering: true },
				{ fieldName: 'diachi', headerText: 'Địa chỉ thanh toán', allowfiltering: true },
				{ fieldName: 'noidung', headerText: 'Nội dung', allowfiltering: true },
				{ fieldName: 'tien', headerText: 'Tiền', allowfiltering: true },
				{ fieldName: 'thue', headerText: 'Thuế', allowfiltering: true },
				{ fieldName: 'tongtien', headerText: 'Tổng tiền', allowfiltering: true },
			],

			/**@type {{value: String, text: String}[]} */
			cboMauSo_DataSource: [],
			/**@type {String} */
			cboMauSo_Value: null,

			/**@type {{pattern: String, serial: String}[]} */
			dsKyHieu: [],
			/**@type {{value: String, text: String}[]} */
			cboKyHieu_DataSource: [],
			/**@type {String} */
			cboKyHieu_Value: null,

			dsPreview_DataSource: [],

			txtFKey_Value: "",

			hoadon: null,

			/**@type {NOIDUNG_HD[]} */
			dsNoiDungHD: [],

			/**@type {xlsx.WorkBook} */
			workbook: null,

			ds_noidung_hddt: [],
			ds_noidung_hddt_Columns: [
				{ fieldName: 'ma_tt', headerText: 'Mã thanh toán', allowfiltering: true, freeze: 'left' },
				{ fieldName: 'ky_cuoc', headerText: 'Kỳ cước', allowfiltering: true, width: 100 },
				{ fieldName: 'ma_kh', headerText: 'Mã khách hàng', allowfiltering: true, },
				{ fieldName: 'ten_khachhang', headerText: 'Tên khách hàng', allowfiltering: true, },
				{ fieldName: 'diachi', headerText: 'Địa chỉ', allowfiltering: true, },
				{ fieldName: 'ms_thue', headerText: 'Mã số thuế', allowfiltering: true, },
				{ fieldName: 'thutu_in', headerText: 'STT', allowfiltering: true, width: 100 },
				{ fieldName: 'tenkhoanmuc_hd', headerText: 'Tên hàng hóa, dịch vụ', allowfiltering: true },
				{ fieldName: 'so_luong', headerText: 'Số lượng', allowfiltering: true, align: "right", width: 100, format: "N0", type: 'number' },
				{ fieldName: 'dv_tinh', headerText: 'Đơn vị tính', allowfiltering: true, width: 100 },
				{ fieldName: 'don_gia', headerText: 'Đơn giá', allowfiltering: true, align: "right", width: 150, format: "N0", type: 'number'  },
				{ fieldName: 'tien', headerText: 'Thành tiền', allowfiltering: true, align: "right", width: 150, format: "N0", type: 'number' },
			],

			/**@type {{value: String, text: String}[]} */
			cboSheet_DataSource: [],
			cboSheet_Value: '',

			USER_HDDT: "",
			PASS_HDDT: "",

			/**@type {xlsx.WorkBook} */
			workbook2: null,
			/**@type {{ky_cuoc: Number, ma_tt: String, fkey: String, pattern?: String, serial?: String, no?: Number}[]} */
			ds_serial_hddt: [],
			/**@type {{value: String, text: String}[]} */
			cboSheet2_DataSource: [],
			cboSheet2_Value: '',
			dsHoaDon2_Columns: [
				{ fieldName: 'ma_tt', headerText: 'Mã thanh toán', allowfiltering: true, freeze: 'left' },
				{ fieldName: 'ky_cuoc', headerText: 'Kỳ cước', allowfiltering: true, width: 100 },
				{ fieldName: 'fkey', headerText: 'FKey', allowfiltering: true },

				{ fieldName: 'ma_khachhang', headerText: 'Mã khách hàng', allowfiltering: true },
				{ fieldName: 'ten_khachhang', headerText: 'Tên khách hàng', allowfiltering: true },
				{ fieldName: 'ms_thue', headerText: 'Mã số thuế', allowfiltering: true },
				{ fieldName: 'ten_khachhang', headerText: 'Tên khách hàng', allowfiltering: true },
				{ fieldName: 'diachi', headerText: 'Địa chỉ thanh toán', allowfiltering: true },
				{ fieldName: 'tien', headerText: 'Tiền', allowfiltering: true, format: "N0", type: 'number' },
				{ fieldName: 'thue', headerText: 'Thuế', allowfiltering: true, format: "N0", type: 'number' },
				{ fieldName: 'noidung', headerText: 'Nội dung', allowfiltering: true },

				{ fieldName: 'pattern', headerText: 'Pattern', allowfiltering: true },
				{ fieldName: 'serial', headerText: 'Serial', allowfiltering: true },
				{ fieldName: 'no', headerText: 'No', allowfiltering: true },

				{ fieldName: 'ngay_ph', headerText: 'Ngày PH', allowfiltering: true, format: { type: 'date', format: "dd/MM/yyyy HH:mm:ss" } },
				{ fieldName: 'status', headerText: 'Trạng thái', allowfiltering: true, width: 150, freeze: 'right' },
			],
		};
	},
	computed: {
		cboTrangThai_Options() {
			return [
				{ value: 0, text: 'Chưa phát hành' },
				{ value: 1, text: 'Đã phát hành' },
				{ value: -1, text: 'Tất cả' }
			];
		},
		ds_ma_tt_text() {
			return this.ds_ma_tt.join(", ")
		},
		ds_noidung_hddt_text() {
			return this.ds_noidung_hddt.length ? this.ds_noidung_hddt.length + " hóa đơn" : ""
		},
		ds_serial_hddt_text() {
			return this.ds_serial_hddt.length ? this.ds_serial_hddt.length + " hóa đơn" : ""
		},
	},
	watch: {
		cboMauSo_Value(newValue, _) {
			this.cboKyHieu_DataSource = this.lay_ds_kyhieu(newValue);
			this.cboKyHieu_Value = null;
		},
		cboTuThang_Value(newValue, _) {
			if (this.cboTuThang_Value > this.cboDenThang_Value)
				this.cboDenThang_Value = this.cboTuThang_Value;
		},
		cboDenThang_Value(newValue, _) {
			if (this.cboDenThang_Value < this.cboTuThang_Value)
				this.cboDenThang_Value = this.cboTuThang_Value;
		},
		cboSheet_Value(newValue, _) {
			if (this.workbook != null && this.workbook.Sheets != null && this.workbook.Sheets[this.cboSheet_Value] != null)
				this.ds_noidung_hddt = xlsx.utils.sheet_to_json(this.workbook.Sheets[this.cboSheet_Value]);
			else
				this.ds_noidung_hddt = [];
		},
		cboSheet2_Value(newValue, _) {
			if (this.workbook2 != null && this.workbook2.Sheets != null && this.workbook2.Sheets[this.cboSheet2_Value] != null)
				this.ds_serial_hddt = xlsx.utils.sheet_to_json(this.workbook2.Sheets[this.cboSheet2_Value]);
			else
				this.ds_serial_hddt = [];
		}
	},
	async mounted() {
		$(document).on('show.bs.modal', '.modal', function () {
			let zIndex = 1040 + (10 * $('.modal:visible').length);
			$(this).css('z-index', zIndex);
			setTimeout(function() {
				$('.modal-backdrop')
					.not('.modal-stack')
					.css('z-index', zIndex - 1)
					.addClass('modal-stack');
			}, 0);
		});

		try {
			// Lay acc
			let acc = await api.lay_account_invoice(this.axios);
			if (acc.data.data.error_message) {
				this.$toast.error(acc.data.data.error_message);
				return;
			}

			this.USER_HDDT = acc.data.data.username;
			this.PASS_HDDT = acc.data.data.password;

			// Lay serial
			let serial = await api.lay_ds_serial(this.axios);
			if (serial.data.data.error_message) {
				this.$toast.error(serial.data.data.error_message);
				return;
			}

			this.dsKyHieu = serial.data.data.data;
			this.cboMauSo_DataSource = [ ...new Set(this.dsKyHieu.map(x => x.pattern)) ]
				.map(x => ({
					text: x,
					value: x
				}));
			this.cboMauSo_Value = this.cboMauSo_DataSource[0].value;

			// Lay danh sach hddt
			await this.btnLamMoi_Click();
		}
		catch (error) {
			this.$toast.error(error + "")
		}
	},
	methods: {
		async alert(msg) {
			this.message = msg;
			$("#modalAlert").modal("show");
			return new Promise((resolve, _) => {
				this.resolveAction = resolve;
			});
		},
		async confirm(msg) {
			this.message = msg;
			$("#modalConfirm").modal("show");
			return new Promise((resolve, _) => {
				this.resolveAction = resolve;
			});
		},
		showMask(text) {
			$('.overlay-common').addClass('show').find(".loading-content").html(text || 'Đang nạp nội dung...');
		},
		hideMask() {
			$('.overlay-common').removeClass('show').find(".loading-content").html('Đang nạp nội dung...');
		},
		/**
		 * Mở file và trả về nội dung
		 * @param {String} extension loại file vd: .xls,.xlsx
		 * @returns {Promise<Blob | Error>} Nội dung file nếu thành công, Exception nếu thất bại
		 */
		async openFile(extension) {
			return await new Promise((resolve, reject) => {
				try {
					let input = document.createElement("input");
					input.type = 'file';
					input.accept = extension;
					input.style.display = 'none';
					input.onchange = function (e) {
						if (e.target && e.target.files[0])
							resolve(e.target.files[0]);
						else
							throw "File can not be read !";
					};
					document.body.appendChild(input);

					input.dispatchEvent(new MouseEvent('click', {
						view: window, bubbles: true, cancelable: true
					}));
				}
				catch (ex) {
					reject(ex);
				}
			});
		},
		lay_ds_ky_cuoc() {
			return new Array(12 * 2)
				.fill(0)
				.map((x, i) => moment().subtract(i, 'month'))
				.map(x => ({
					value: x.format("YYYYMM01"),
					text: x.format("MM/YYYY")
				}));
		},
		lay_ky_truoc() {
			return moment().subtract(1, 'month').format("YYYYMM01");
		},
		lay_ds_hddt(value) {
			this.ds_ma_tt_can_ph = value.map(e => ({
				ma_tt: e.ma_tt, ky_cuoc: e.ky_cuoc
			}));
		},
		lay_ds_kyhieu(mauso) {
			return this.dsKyHieu.filter(x => x.pattern === mauso).map(x => ({ value: x.serial, text: x.serial }));
		},
		async lay_ds_thanhtoan() {
			let resp = await api.lay_ds_ma_tt_can_phathanh(this.axios);
			this.dsThanhToan_DataSource = resp.data.data.data || [];
		},
		async lay_ds_hoadon() {
			let resp = await api.lay_ds_hddt(this.axios, {
				p_tu_thang: this.cboTuThang_Value,
				p_den_thang: this.cboDenThang_Value,
				p_trangthai: this.cboTrangThai_Value,
				p_ds_ma_tt: JSON.stringify(this.ds_ma_tt)
			});
			this.dsHoaDon_DataSource = resp.data.data.data || [];
		},

		/**
		 * @param {String} command
		 * @param {{ma_tt: String, ky_cuoc: Number}} data
		 * @return {Promise<void>}
		 */
		async dsThanhToan_CommandClicked(command, data) {
			this.showMask("Đang xóa mã thanh toán...");
			switch (command) {
				case "xoa":
					try {
						let resp = await api.xoa_ma_tt_can_phathanh(this.axios, {
							p_ds_ma_tt: data.ma_tt, p_ky_cuoc: data.ky_cuoc
						});

						if (resp.data.data.error_message)
							this.$toast.error(resp.data.data.error_message);
						else {
							this.$toast.success('Xóa thành công !');
							await this.lay_ds_thanhtoan();
						}
					}
					catch (error) {
						this.$toast.error(error + "")
					}
					break;
			}
			this.hideMask();
		},
		/**
		 * @param {String} command
		 * @param {{ma_tt: String, ky_cuoc: Number, fkey: String}} data
		 * @return {Promise<void>}
		 */
		async dsHoaDon_CommandClicked(command, data) {
			try {
				function base64ToBlob(base64, type = "application/octet-stream") {
					const binStr = atob(base64);
					const len = binStr.length;
					const arr = new Uint8Array(len);
					for (let i = 0; i < len; i++) arr[i] = binStr.charCodeAt(i);
					return new Blob([ arr ], { type: type });
				}

				/**
				 * Mở file PDF từ chuỗi BASE64
				 * @param {String} base64
				 */
				function openPDF(base64) {
					const blob = base64ToBlob(base64, 'application/pdf');
					const url = URL.createObjectURL(blob);
					const pdfWindow = window.open(url);
					pdfWindow.document.write(`<!DOCTYPE HTML>
                    <html lang="vi">
                        <head>
                            <title>${data.ma_tt} - ${data.ky_cuoc.toString().substring(6, 8)}/${data.ky_cuoc.toString().substring(0, 4)}</title>
                            <meta charset='UTF-8'>
                            <style>iframe { border: none; position: fixed; left: 0; top: 0; width: 100%; height: 100% }</style>
                        </head>
                        <body>
                            <iframe src='${url}'></iframe>
                        </body>
                    </html>`);
				}

				/**
				 * Tải file PDF từ chuỗi BASE64
				 * @param {String} base64
				 */
				function downloadPdf(base64) {
					const blob = base64ToBlob(base64, 'application/pdf');
					let a = document.createElement('A');
					a.href = URL.createObjectURL(blob);
					a.download = data.ma_tt + "_" + data.ky_cuoc + ".pdf";
					document.body.appendChild(a);
					a.click();
					document.body.removeChild(a);
				}

				this.hoadon = data;
				switch (command) {
					case "xem":
						this.showMask("Đang tải hóa đơn...");

						let xem = await api.downloadInvPDFFkeyNoPay(this.axios, {
							fkey: data.fkey, userName: this.USER_HDDT, userPass: this.PASS_HDDT
						});

						if (xem.data.errorCode > 0)
							this.$toast.error(xem.data.faultString);
						else
							openPDF(xem.data.data);
						break;
					case "tai":
						this.showMask("Đang tải hóa đơn...");

						let tai = await api.downloadInvPDFFkeyNoPay(this.axios, {
							fkey: data.fkey, userName: this.USER_HDDT, userPass: this.PASS_HDDT
						});

						if (tai.data.errorCode > 0)
							this.$toast.error(tai.data.faultString);
						else
							downloadPdf(tai.data.data);
						break;
					case "sync":
						this.showMask("Đang lấy thông tin hóa đơn...");
						let sync = await api.downloadInvFkeyNoPay(this.axios, {
							fkey: data.fkey, userName: this.USER_HDDT, userPass: this.PASS_HDDT
						});
						this.hideMask();

						if (sync.data.errorCode > 0) {
							if (sync.data.errorCode === 6) {
								if (!await this.confirm(sync.data.faultString + "\r\nBạn có muốn tự nhập serial không ?")) return;

								this.txtFKey_Value = data.fkey;
								$('#modalDongBoHD').modal('show');
							}
							else
								this.$toast.error(sync.data.faultString);
						}
						else {
							let doc = new DOMParser().parseFromString(sync.data.data, 'text/xml');
							let pattern = this.cboMauSo_DataSource[doc.evaluate('/HDon/DLHDon/TTChung/KHMSHDon', doc, null, XPathResult.STRING_TYPE, null).stringValue - 1].value;
							let serial = doc.evaluate('/HDon/DLHDon/TTChung/KHHDon', doc, null, XPathResult.STRING_TYPE, null).stringValue;
							let no = +doc.evaluate('/HDon/DLHDon/TTChung/SHDon', doc, null, XPathResult.STRING_TYPE, null).stringValue;
							let ngay_ph = doc.evaluate('//SigningTime', doc, null, XPathResult.STRING_TYPE, null).stringValue;

							if (!await this.confirm(`Phát hiện serial ${pattern}; ${serial}; ${no}, ký ngày ${moment(ngay_ph).format("DD/MM/YYYY HH:mm")}\nBạn có muốn cập nhật serial theo invoice không ?`)) {
								$("#modalDongBoHD").modal('show');
								return;
							}

							let cntt = await api.capnhat_trangthai_phathanh(this.axios, {
								p_ma_tt: data.ma_tt,
								p_ky_cuoc: data.ky_cuoc,
								p_no: no,
								p_pattern: pattern,
								p_serial: serial,
								p_ngay_ph: new Date(ngay_ph).toJSON(),
							});
							if (cntt.data.data.error_message) {
								this.$toast.error(cntt.data.data.error_message);
								return;
							}
							else {
								this.$toast.success("Đồng bộ thành công !");
								await this.btnLamMoi_Click();
							}
						}
						break;
					case "edit":
						this.showMask("Đang tải nội dung hóa đơn...");

						let resp = await api.lay_noidung_hoadon(this.axios, {
							p_ds_ma_tt: JSON.stringify([ {
								ma_tt: data.ma_tt, ky_cuoc: data.ky_cuoc
							} ])
						});
						if (resp.data.data.error_message)
							this.$toast.error(resp.data.data.error_message);
						else {
							this.dsNoiDungHD = resp.data.data.data.map(x => ({
								...x, goc: x
							}));
							$('#modalCapNhatHD').modal('show');
						}
						break;
				}
			}
			catch (error) {
				this.$toast.error(error + "");
			}
			finally {
				this.hideMask();
			}
		},
		dsHoaDon_QueryCellInfo(args) {
			let header = args.column.headerText;

			if (header === "Chức năng") {
				for (const wrapper of args.cell.childNodes) {
					for (const node of wrapper.childNodes) {
						switch (node.getAttribute("title")) {
							case "Xem hóa đơn":
							case "Tải hóa đơn":
								if (args.data['status'] === 2)
									node.style.display = 'inherit';
								else
									node.style.display = 'none';
								break;
							case "Sửa nội dung":
								if (args.data['status'] === 0)
									node.style.display = 'inherit';
								else
									node.style.display = 'none';
								break;
						}
					}
				}
			}
			else {
				if (args.data['status'] === 1)
					args.cell.style.color = 'blue';
				else if (args.data['status'] === 2)
					args.cell.style.color = 'green';

				if (header === "TTPH")
					args.cell.innerHTML = [
						"<i class='fa fa-circle' title='0 - Chưa phát hành'></i>",
						"<i class='fa fa-play-circle' title='1 - Đang phát hành'></i>",
						"<i class='fa fa-check-circle' title='2 - Đã phát hành'></i>"
					][args.data['status']] ;
			}
		},

		async btnLamMoi_Click() {
			this.showMask();
			try {
				await this.lay_ds_thanhtoan();
				await this.lay_ds_hoadon();
			}
			catch (error) {
				this.$toast.error(error + "")
			}
			this.hideMask();
		},
		async btnPhatHanh_Click() {
			try {
				let resp = await api.xemtruoc_hoadon(this.axios);
				if (resp.data.data.error_message)
					this.$toast.error(resp.data.data.error_message);
				else
					this.dsPreview_DataSource = resp.data.data.data;
			}
			catch (error) {
				this.$toast.error(error + "")
			}
		},
		async btnMoFile_Click() {
			let blob = await this.openFile(".txt");
			let text = await blob.text();
			this.ds_ma_tt = text.split(/[\r\n]+/).map(x => x.trim());
			await this.btnLamMoi_Click();
		},
		async btnXoa_Click() {
			this.ds_ma_tt = [];
			await this.btnLamMoi_Click();
		},
		async btnThemVaoDS_Click() {
			if (!this.ds_ma_tt_can_ph.length) {
				this.$toast.warning("Hãy chọn ít nhất 1 hóa đơn !");
				return;
			}

			this.showMask("Đang thêm mã thanh toán vào danh sách...");
			try {
				let resp = await api.them_ma_tt_can_phathanh(this.axios, {
					p_ds_ma_tt: JSON.stringify(this.ds_ma_tt_can_ph)
				});
				if (resp.data.data.error_message)
					this.$toast.error(resp.data.data.error_message);
				else {
					this.$toast.success('Thêm thành công !');
					await this.lay_ds_thanhtoan();
				}
			}
			catch (error) {
				this.$toast.error(error + "")
			}
			this.hideMask();
		},
		async btnXoaDS_Click() {
			if (!await this.confirm("Bạn có muốn xóa danh sách mã thanh toán cần xuất hóa đơn không ?"))
				return;

			this.showMask("Đang xóa danh sách mã thanh toán");
			try {
				let resp = await api.xoa_ds_ma_tt_can_phathanh(this.axios);

				if (resp.data.data.error_message)
					this.$toast.error(resp.data.data.error_message);
				else {
					this.$toast.success('Xóa thành công !');
					await this.lay_ds_thanhtoan();
				}
			}
			catch (error) {
				this.$toast.error(error + "")
			}
			this.hideMask();
		},
		async btnPhatHanh2_Click() {
			if (!this.cboMauSo_Value)
			{
				 this.$toast.warning("Hãy nhập mẫu số để phát hành !");
				 return;
			}
			if (!this.cboKyHieu_Value)
			{
				this.$toast.warning("Hãy nhập ký hiệu để phát hành !");
				return;
			}

			let me = this,
				pattern = me.cboMauSo_Value,
				serial = me.cboKyHieu_Value,
				tc = 0, tb = 0, id = 0, tt = 0;

			/**
			 * Cập nhật progressbar
			 */
			function capnhat_tientrinh() {
				let _tc = Math.round(tc / tt * 100);
				let _tb = Math.round(tb / tt * 100);
				me.showMask(`<div style="text-align: left">
                    <div class="mb-2">Đang phát hành ${++id} / ${tt} hóa đơn...</div>
                    <div class="progress">
                        <div class="progress-bar-striped progress-bar-animated bg-success" role="progressbar" style="width: ${_tc}%" aria-valuenow="${_tc}" aria-valuemin="0" aria-valuemax="100"></div>
                        <div class="progress-bar-striped progress-bar-animated bg-danger" role="progressbar" style="width: ${_tb}%" aria-valuenow="${_tb}" aria-valuemin="0" aria-valuemax="100"></div>
                    </div>
                </div>`);
			}

			/**
			 * Cập nhật lại serial sau khi phát hành
			 * @param {String} ma_tt mã thanh toán
			 * @param {Number} ky_cuoc kỳ cước
			 * @param {Number} no serial no
			 * @returns {Promise<void>}
			 */
			async function capnhat_serial(ma_tt, ky_cuoc, no) {
				let cntt = await api.capnhat_trangthai_phathanh(me.axios, {
					p_ma_tt: ma_tt,
					p_ky_cuoc: ky_cuoc,
					p_no: no,
					p_pattern: pattern,
					p_serial: serial,
					p_ngay_ph: new Date().toJSON(),
				});
				if (cntt.data.data.error_message) {
					capnhat_tientrinh(++tb);
					console.error("ERR:2:", ma_tt, ky_cuoc, cntt.data.data.error_message);
				}
				else if (no === -1)
					capnhat_tientrinh(++tb);
				else
					capnhat_tientrinh(++tc);
			}

			try {
				if (!await this.confirm("Bạn có muốn phát hành các hóa đơn đã chọn không ?"))
					return;

				// Kiểm tra
				me.showMask("Kiểm tra các mã cần phát hành...");
				let resp = await api.kiemtra_ds_ma_tt_can_phathanh(me.axios);
				if (resp.data.data.error_message) {
					me.$toast.error(resp.data.data.error_message);
					return;
				}
				tt = resp.data.data.data.length;

				// Phát hành
				for (let hd of resp.data.data.data) {
					// Cập nhật dữ liệu khách hàng
					if (hd.cus) {
						let upCus = await api.updateCus(me.axios, {
							"acPass": me.PASS_HDDT,
							"account": me.USER_HDDT,
							"convert": 0,
							"xmlCusData": hd.cus
						});
						if (upCus.data < 0) {
							console.error("ERR:0:", hd.ma_tt, hd.ky_cuoc, ({
								[0]: "Không update được khách hàng nào",
								[-1]: "Tài khoản đăng nhập sai hoặc không có quyền thêm khách hàng",
								[-2]: "Không import được khách hàng vào db",
								[-3]: "Dữ liệu xml đầu vào không đúng quy định"
							})[upCus.data] || "Lỗi không xác định INVOICE" + upCus.data);

							await capnhat_serial(hd.ma_tt, hd.ky_cuoc, -1);
							continue;
						}
					}

					// Phát hành hóa đơn
					let phhd = await api.importAndPublishInv(me.axios, {
						"acPass": me.PASS_HDDT,
						"account": me.USER_HDDT,
						"convert": 0,
						"pattern": pattern,
						"serial": serial,
						"xmlInvData": hd.xml,
						"cusCode": hd.ma_tt
					});

					if (phhd.data.errorCode > 0) {
						console.error("ERR:1:", hd.ma_tt, hd.ky_cuoc, phhd.data.faultString);

						await capnhat_serial(hd.ma_tt, hd.ky_cuoc, -1);
						continue;
					}

					// Cập nhật serial
					let dt = phhd.data.data.split(/[:\-_;]/);
					await capnhat_serial(hd.ma_tt, hd.ky_cuoc, +dt[4]);
				}

				// Thông báo
				let msg = `Đã phát hành ${id} / ${tt} hóa đơn...\r\n- Thành công: ${tc}\r\n- Thất bại: ${tb}`;
				if (tb === tt)
					me.$toast.error(msg);
				else if (tb > 0)
					me.$toast.warn(msg);
				else
					me.$toast.success(msg);
			}
			catch (error) {
				me.$toast.error(error + "")
			}
			finally {
				me.hideMask();
				$("#modalPreviewHD").modal('hide');
				await me.btnLamMoi_Click();
			}
		},
		async btnCapNhatSerial_Click() {
			try {
				// Lấy thông tin hóa đơn từ invoice theo fkey đã nhập
				let sync = await api.downloadInvFkeyNoPay(this.axios, {
					userName: this.USER_HDDT,
					userPass: this.PASS_HDDT,
					fkey: this.txtFKey_Value
				});

				// Nếu lỗi thì hiển thị lỗi
				if (sync.data.errorCode > 0) {
					this.$toast.error(sync.data.faultString);
					return;
				}

				// Lấy thông tin hóa đơn từ két quả trả về
				let doc = new DOMParser().parseFromString(sync.data.data, 'text/xml');
				let pattern = this.cboMauSo_DataSource[doc.evaluate('/HDon/DLHDon/TTChung/KHMSHDon', doc, null, XPathResult.STRING_TYPE, null).stringValue - 1].value;
				let serial = doc.evaluate('/HDon/DLHDon/TTChung/KHHDon', doc, null, XPathResult.STRING_TYPE, null).stringValue;
				let no = +doc.evaluate('/HDon/DLHDon/TTChung/SHDon', doc, null, XPathResult.STRING_TYPE, null).stringValue;
				let ngay_ph = doc.evaluate('//SigningTime', doc, null, XPathResult.STRING_TYPE, null).stringValue;

				// Cập nhật thông tin vào OLTP
				let cntt = await api.capnhat_trangthai_phathanh(this.axios, {
					p_ma_tt: this.hoadon.ma_tt,
					p_ky_cuoc: this.hoadon.ky_cuoc,
					p_no: no,
					p_pattern: pattern,
					p_serial: serial,
					p_ngay_ph: new Date(ngay_ph).toJSON(),
					p_fkey: this.txtFKey_Value
				});

				// Nếu lỗi thì hiển thị lỗi
				if (cntt.data.data.error_message) {
					this.$toast.error(cntt.data.data.error_message);
					return;
				}

				// Thông báo thành công
				this.$toast.success(`Cập nhật serial hóa đơn ${this.hoadon.ma_tt}, kỳ cước ${this.hoadon.ky_cuoc} thành công !`);
				$('#modalDongBoHD').modal('hide');
				await this.btnLamMoi_Click();
			}
			catch (error) {
				this.$toast.error(error + "")
			}
			finally {
				this.hideMask();
			}
		},

		async btnXuatExcel1_Click() {
			this.showMask("Đang lấy dữ liệu hóa đơn...");
			try {
				let resp = await api.lay_ds_hddt_xuat_xls(this.axios, {
					p_tu_thang: this.cboTuThang_Value,
					p_den_thang: this.cboDenThang_Value,
					p_trangthai: this.cboTrangThai_Value,
					p_ds_ma_tt: JSON.stringify(this.ds_ma_tt)
				});
				this.hideMask();

				if (resp.data.data.error_message)
					this.$toast.error(resp.data.data.error_message);
				else {
					let data = resp.data.data.data || [];
					if (data.length > 0) {
						let wb = xlsx.utils.book_new();
						const ws = xlsx.utils.json_to_sheet(data);
						xlsx.utils.book_append_sheet(wb, ws, 'Sheet 1');
						xlsx.writeFile(wb, 'ph_hddt.xlsx');
						this.$toast.success("Xuất file thành công !");
					}
					else
						this.$toast.warning("Không có hóa đơn nào để xuất !");
				}
			}
			catch (ex) {
				this.hideMask();
				this.$toast.error(ex + "")
			}
		},
		btnXuatExcel2_Click() {
			let data = this.dsPreview_DataSource.map((e) => {
				let exp = {};
				this.dsPreview_Columns.map((x) => {
					exp[x.headerText] = e[x.fieldName]
				});
				return exp;
			});
			if (data.length > 0) {
				let wb = xlsx.utils.book_new();
				const ws = xlsx.utils.json_to_sheet(data);
				xlsx.utils.book_append_sheet(wb, ws, 'Sheet 1');
				xlsx.writeFile(wb, 'ph_hddt.xlsx');
				this.$toast.success("Xuất file thành công !");
			}
		},
		btnXuatExcel3_Click() {
			let data = this.dsThanhToan_DataSource.map((e) => {
				let exp = {};
				this.dsThanhToan_Columns.map((x) => {
					exp[x.headerText] = e[x.fieldName]
				});
				return exp;
			});
			if (data.length > 0) {
				let wb = xlsx.utils.book_new();
				const ws = xlsx.utils.json_to_sheet(data);
				xlsx.utils.book_append_sheet(wb, ws, 'Sheet 1');
				xlsx.writeFile(wb, 'ph_hddt.xlsx');
				this.$toast.success("Xuất file thành công !");
			}
		},
		async btnCapNhatND_Click() {
			if (!await this.confirm(`Bạn có muốn cập nhật nội dung hóa đơn ${this.hoadon.ma_tt}, kỳ cước ${this.hoadon.ky_cuoc} không?`))
				return;

			this.showMask("Đang cập nhật nội dung hóa đơn...");

			try {
				let cntt = await api.capnhat_noidung_hoadon(this.axios, {
					p_noidung_hd: JSON.stringify(this.dsNoiDungHD.map(x => ([ {
						row_id: x.row_id, thutu_in: x.thutu_in, tenkhoanmuc_hd: x.tenkhoanmuc_hd, dv_tinh: x.dv_tinh
					} ])))
				});
				if (cntt.data.data.error_message) {
					this.$toast.error(cntt.data.data.error_message);
					return;
				}

				this.$toast.success(`Cập nhật nội dung hóa đơn ${this.hoadon.ma_tt}, kỳ cước ${this.hoadon.ky_cuoc} thành công !`);
				$('#modalCapNhatHD').modal('hide');
				await this.btnLamMoi_Click();
			}
			catch (error) {
				this.$toast.error(error + "")
			}
			finally {
				this.hideMask();
			}
		},
		async btnMoFile2_Click() {
			let blob = await this.openFile(".xls,.xlsx");
			let buffer = await blob.arrayBuffer();

			this.btnXoa2_Click();
			this.workbook = xlsx.read(buffer);
			this.cboSheet_DataSource = this.workbook.SheetNames.map(x => ({ value: x, text: x }));
			this.cboSheet_Value = this.workbook.SheetNames[0];
		},
		btnXoa2_Click() {
			this.ds_noidung_hddt = [];
			this.cboSheet_DataSource = [];
			this.cboSheet_Value = null;
		},
		async btnTaiFileMau_Click() {
			this.showMask("Đang lấy nội dung hóa đơn...");

			try {
				let resp = await api.lay_noidung_hoadon(this.axios, {
					p_ds_ma_tt: null
				});
				this.hideMask();

				if (resp.data.data.error_message)
					this.$toast.error(resp.data.data.error_message);
				else if (resp.data.data.data == null)
					this.$toast.warning("Hãy chọn ít nhất 1 mã thanh toán để xuất !");
				else {
					let data = resp.data.data.data.map(x => ({
						row_id: x.row_id,

						ma_kh: x.ma_kh,
						ten_khachhang: x.ten_khachhang,
						diachi: x.diachi,
						ms_thue: x.ms_thue,

						ma_tt: x.ma_tt,
						ky_cuoc: x.ky_cuoc,
						thutu_in: x.thutu_in,
						tenkhoanmuc_hd: x.tenkhoanmuc_hd,
						dv_tinh: x.dv_tinh,
						so_luong: x.so_luong,
						don_gia: x.don_gia,
						tien: x.tien,
					}));
					let wb = xlsx.utils.book_new();
					const ws = xlsx.utils.json_to_sheet(data);
					xlsx.utils.book_append_sheet(wb, ws, 'Sheet 1');
					xlsx.writeFile(wb, 'file_mau.xlsx');
					this.$toast.success("Xuất file thành công !");
					await this.alert("Lưu ý: Chỉ cập nhật các trường thutu_in, tenkhoanmuc_hd và dv_tinh.\r\nCác trường còn lại giữ nguyên.");
				}
			}
			catch (error) {
				this.$toast.error(error + "")
			}
			finally {
				this.hideMask();
			}
		},
		async btnCapNhatNoiDung_Click() {
			if (!await this.confirm("Bạn có muốn cập nhật nội dung các hóa đơn theo file đã chọn không?"))
				return;

			this.showMask("Đang cập nhật nội dung hóa đơn...");

			try {
				let resp = await api.capnhat_noidung_hoadon(this.axios, {
					p_noidung_hd: JSON.stringify(this.ds_noidung_hddt.map(x => ({
						row_id: x.row_id, thutu_in: x.thutu_in, tenkhoanmuc_hd: x.tenkhoanmuc_hd, dv_tinh: x.dv_tinh
					})))
				});
				if (resp.data.data.error_message)
					this.$toast.error(resp.data.data.error_message);
				else {
					this.$toast.success("Cập nhật thành công !");
					await this.btnLamMoi_Click();
					$("#modalCapNhatHdTheoFile").modal("hide");
				}
			}
			catch (error) {
				this.$toast.error(error + "")
			}
			finally {
				this.hideMask();
			}
		},

		async btnTaiFileMau2_Click() {
			this.showMask("Đang lấy danh sách hóa đơn...");

			try {
				let resp = await api.xemtruoc_hoadon(this.axios);
				this.hideMask();

				if (resp.data.data.error_message)
					this.$toast.error(resp.data.data.error_message);
				else if (resp.data.data.data == null)
					this.$toast.warning("Hãy chọn ít nhất 1 mã thanh toán để xuất !");
				else {
					let data = resp.data.data.data;
					let wb = xlsx.utils.book_new();
					const ws = xlsx.utils.json_to_sheet(data);
					xlsx.utils.book_append_sheet(wb, ws, 'Sheet 1');
					xlsx.writeFile(wb, 'file_mau.xlsx');
					this.$toast.success("Xuất file thành công !");
					await this.alert("Lưu ý: Chỉ cập nhật các trường fkey.\r\nCác trường còn lại giữ nguyên.");
				}
			}
			catch (error) {
				this.$toast.error(error + "")
			}
			finally {
				this.hideMask();
			}
		},
		async btnCapNhatSerial2_Click() {
			this.showMask("Đang cập nhật serial hóa đơn...");

			try {
				for (let data of this.ds_serial_hddt) {
					data.status = "Đang cập nhật...";
					this.ds_serial_hddt = [...this.ds_serial_hddt];

					// Cập nhật thông tin vào OLTP
					let cntt = await api.capnhat_trangthai_phathanh(this.axios, {
						p_ma_tt: data.ma_tt,
						p_ky_cuoc: data.ky_cuoc,
						p_no: data.no,
						p_pattern: data.pattern,
						p_serial: data.serial,
						p_ngay_ph: data.ngay_ph.toJSON(),
						p_fkey: data.fkey
					});

					// Nếu lỗi thì hiển thị lỗi
					if (cntt.data.data.error_message)
						data.status = cntt.data.data.error_message;
					else
						data.status = "OK";
					this.ds_serial_hddt = [...this.ds_serial_hddt];
				}

				this.hideMask();
				await this.alert(`Cập nhật hoàn tất !<br>
- Số hóa đơn: ${this.ds_serial_hddt.length}<br>
- Thành công: ${this.ds_serial_hddt.filter(x => x.status === "OK").length}<br>
- Thất bại: ${this.ds_serial_hddt.filter(x => x.status !== "OK").length}<br>
Hãy kiểm tra trạng thái cập nhật trên lưới.`);
			}
			catch (ex) {
				this.$toast.error(ex + "")
			}

			this.hideMask();
		},
		async btnXoaHdLoi_Click() {
			if (!await this.confirm("Bạn có muốn xóa các hóa đơn lỗi không ?"))
				return;

			this.ds_serial_hddt = this.ds_serial_hddt.filter(x => x.status === "OK");
			if (this.ds_serial_hddt.length === 0)
				this.btnXoa3_Click();
		},
		async btnMoFile3_Click() {
			let blob = await this.openFile(".xls,.xlsx");
			let buffer = await blob.arrayBuffer();

			this.btnXoa3_Click();
			this.workbook2 = xlsx.read(buffer);
			this.cboSheet2_DataSource = this.workbook2.SheetNames.map(x => ({ value: x, text: x }));
			this.cboSheet2_Value = this.workbook2.SheetNames[0];
		},
		btnXoa3_Click() {
			this.ds_serial_hddt = [];
			this.cboSheet2_DataSource = [];
			this.cboSheet2_Value = null;
		},
		async btnLayThongTin_Click() {
			this.showMask("Đang lấy thông tin hóa đơn...");

			try {
				let count = 0;
				for (let data of this.ds_serial_hddt) {
					this.showMask(`Đang lấy thông tin hóa đơn ${++count}/${this.ds_serial_hddt.length}...`);

					if (data.status !== "OK") {
						let sync = await api.downloadInvFkeyNoPay(this.axios, {
							fkey: data.fkey, userName: this.USER_HDDT, userPass: this.PASS_HDDT
						});

						if (sync.data.errorCode > 0)
							data.status = sync.data.faultString;
						else {
							let doc = new DOMParser().parseFromString(sync.data.data, 'text/xml');
							function xpath(selector) {
								return doc.evaluate(selector, doc, null, XPathResult.STRING_TYPE, null).stringValue
							}
							function xpaths(xpath) {
								let results = [];
								let query = doc.evaluate(xpath, doc, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
								for (let i = 0, length = query.snapshotLength; i < length; ++i)
									results.push(query.snapshotItem(i).textContent);
								return results;
							}
							data.pattern = this.cboMauSo_DataSource[xpath('//KHMSHDon') - 1].value;
							data.serial = xpath('//KHHDon');
							data.no = +xpath('//SHDon');
							data.ngay_ph = new Date(xpath('//SigningTime'));
							data.ma_khachhang = xpath('//NMua/TTKhac/TTin/DLieu');
							data.ten_khachhang = xpath('//NMua/Ten');
							data.diachi = xpath('//NMua/DChi');
							data.ms_thue = xpath('//NMua/MST');
							data.tien = +xpath('//TgTCThue');
							data.thue = +xpath('//TgTThue');
							data.tongtien = +xpath('//TgTTTBSo');
							let STT = xpaths('//HHDVu/STT');
							let THHDVu = xpaths('//HHDVu/THHDVu');
							let SLuong = xpaths('//HHDVu/SLuong');
							let DVTinh = xpaths('//HHDVu/DVTinh');
							let DGia = xpaths('//HHDVu/DGia');
							let ThTien = xpaths('//HHDVu/ThTien');
							data.noidung = STT.map((x, i) => [STT[i], THHDVu[i], SLuong[i], DVTinh[i], DGia[i], ThTien[i]].join(" | ")).join("\r\n");
							data.status = "OK";
						}
					}
				}

				this.ds_serial_hddt = [...this.ds_serial_hddt];
			}
			catch (ex) {
				this.$toast.error(ex + "")
			}

			this.hideMask();
		},
		btnXuatExcel4_Click() {
			let data = this.ds_serial_hddt.map((e) => {
				let exp = {};
				this.dsHoaDon2_Columns.map((x) => {
					exp[x.headerText] = e[x.fieldName]
				});
				return exp;
			});
			if (data.length > 0) {
				let wb = xlsx.utils.book_new();
				const ws = xlsx.utils.json_to_sheet(data);
				xlsx.utils.book_append_sheet(wb, ws, 'Sheet 1');
				xlsx.writeFile(wb, 'ph_hddt.xlsx');
				this.$toast.success("Xuất file thành công !");
			}
		},
	}
};
</script>

<template>
	<div>
		<breadcrumb :header="header"/>

		<div class="list-actions-top">
			<ul class="list">
				<li>
					<a href="javascript:void(0)" @click="btnLamMoi_Click">
						<span class="icon one-reload1"></span> Làm mới
					</a>
				</li>
				<li v-show="dsThanhToan_DataSource.length > 0">
					<a href="javascript:void(0)" data-toggle="modal" data-target="#modalPreviewHD"
					   @click="btnPhatHanh_Click">
						<i class="fa fa-upload"></i> Phát hành
					</a>
				</li>
				<li v-show="dsThanhToan_DataSource.length > 0">
					<a href="javascript:void(0)" data-toggle="modal" data-target="#modalCapNhatHdTheoFile"
					   @click="btnXoa2_Click">
						<span class="fa fa-map"></span> CN nội dung HĐ
					</a>
				</li>
				<li v-show="dsThanhToan_DataSource.length > 0">
					<a href="javascript:void(0)" data-toggle="modal" data-target="#modalCapNhatSerialTheoFile"
					   @click="btnXoa3_Click">
						<span class="fa fa-google-wallet"></span> CN serial HĐ
					</a>
				</li>
			</ul>
		</div>

		<div class="page-content">
			<div class="row mb-2">
				<div class="col">
					<div class="box-form p-2">
						<div class="legend-title">Thông tin tra cứu</div>

						<div class="row mb-2">
							<div class="col-4">
								<div class="input-group">
									<div class="input-group-prepend">
										<label class="input-group-text w-100px" for="cboTuThang">Từ tháng <span
											class="text-danger">(*)</span></label>
									</div>
									<select class="form-control" v-model="cboTuThang_Value" id="cboTuThang"
											@change="btnLamMoi_Click">
										<option v-for="item in lay_ds_ky_cuoc()" :value="item.value" :key="item.value">
											{{ item.text }}
										</option>
									</select>
								</div>
							</div>
							<div class="col-4">
								<div class="input-group">
									<div class="input-group-prepend">
										<label class="input-group-text w-100px" for="cboDenThang">Đến tháng <span
											class="text-danger">(*)</span></label>
									</div>
									<select class="form-control" v-model="cboDenThang_Value" id="cboDenThang"
											@change="btnLamMoi_Click">
										<option v-for="item in lay_ds_ky_cuoc()" :value="item.value" :key="item.value">
											{{ item.text }}
										</option>
									</select>
								</div>
							</div>
							<div class="col-4">
								<div class="input-group">
									<div class="input-group-prepend">
										<label class="input-group-text w-100px" for="cboTrangThai">Trạng thái <span
											class="text-danger">(*)</span></label>
									</div>
									<select class="form-control" v-model="cboTrangThai_Value" id="cboTrangThai"
											@change="btnLamMoi_Click">
										<option v-for="item in cboTrangThai_Options" :value="item.value"
												:key="item.value">{{ item.text }}
										</option>
									</select>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col">
								<div class="input-group">
									<div class="input-group-prepend">
										<label class="input-group-text w-100px" for="btnMoFile">DS Mã TT</label>
										<button class="btn btn-primary" id="btnMoFile" @click="btnMoFile_Click"
												title="Mở file text mỗi dòng là 1 mã thanh toán để lọc danh sách hóa đơn">
											<i class="fa fa-folder-open"></i> Mở file
										</button>
										<button class="btn btn-danger" @click="btnXoa_Click"
												v-show="ds_ma_tt_text.length">
											<i class="fa fa-times"></i> Xóa
										</button>
									</div>
									<input type="text" class="form-control bg-white" readonly :value="ds_ma_tt_text"
										   placeholder="Bấm [ Mở file ] để lọc hóa đơn theo danh sách mã thanh toán">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col" style="flex: 0 0 500px">
					<div class="box-form p-2">
						<div class="legend-title">
							Danh sách mã thanh toán cần phát hành
							<button v-show="dsThanhToan_DataSource.length"
									class="btn btn-danger btn-sm float-right pt-0 pb-0" @click="btnXoaDS_Click"
									title="Xóa danh sách">
								<i class="fa fa-times"></i> Xóa DS
							</button>
							<button v-show="dsThanhToan_DataSource.length"
									class="btn btn-success btn-sm float-right mr-2 pt-0 pb-0"
									@click="btnXuatExcel3_Click"
									title="Xuất Excel">
								<i class="one-excel"></i> Xuất Excel
							</button>
						</div>
						<DataGrid :columns="dsThanhToan_Columns" :dataSource="dsThanhToan_DataSource"
								  :showColumnCheckbox="false" :enable-paging-server="false" :showFilter="true"
								  :toolbar="null"
								  panelDataHeight="230px" :pageSize="50" :allowPaging="true" :enabledSelectFirstRow="false"
								  :commandColumn="dsThanhToan_CommandColumn" :commands="dsThanhToan_Commands"
								  @commandClicked="dsThanhToan_CommandClicked">
						</DataGrid>
					</div>
				</div>
				<div class="col text-center" style="flex: 0 0 60px">
					<button class="btn btn-primary mt-5" title="Thêm vào danh sách mã thanh toán cần phát hành"
							@click="btnThemVaoDS_Click">
						<i class="fa fa-arrow-left"></i>
					</button>
				</div>
				<div class="col overflow-hidden">
					<div class="box-form p-2">
						<div class="legend-title">
							Danh sách hóa đơn điện tử
							<button v-show="dsHoaDon_DataSource.length"
									class="btn btn-success btn-sm float-right pt-0 pb-0" @click="btnXuatExcel1_Click"
									title="Xuất Excel danh sách hóa đơn điện tử">
								<span class="one one-excel"></span> Xuất Excel
							</button>
						</div>
						<DataGrid :columns="dsHoaDon_Columns" :dataSource="dsHoaDon_DataSource"
								  :showColumnCheckbox="true" :enable-paging-server="false" :showFilter="true"
								  :toolbar="null"
								  panelDataHeight="230px" :pageSize="50" :allowPaging="true" :enabledSelectFirstRow="false"
								  :frozenColumns="2" @selectedItemsChanged="lay_ds_hddt"
								  @queryCellInfo="dsHoaDon_QueryCellInfo" :commandColumn="dsHoaDon_CommandColumn"
								  :commands="dsHoaDon_Commands" @commandClicked="dsHoaDon_CommandClicked">
						</DataGrid>
					</div>
				</div>
			</div>
		</div>

		<div class="modal zoom" id="modalConfirm" tabindex="-1" role="dialog" aria-labelledby="modalConfirmLabel"
			 aria-hidden="true" data-backdrop="false">
			<div class="modal-dialog modal-m modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header p-2">
						<h5 class="modal-title" id="modalConfirmLabel">Xác nhận</h5>
						<button class="close" data-dismiss="modal" aria-label="Đóng" @click="resolveAction(false)">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body p-2" v-html="message">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal" @click="resolveAction(true)">Có</button>
						<button type="button" class="btn btn-secondary" data-dismiss="modal" @click="resolveAction(false)">Không</button>
					</div>
				</div>
			</div>
		</div>

		<div class="modal zoom" id="modalAlert" tabindex="-1" role="dialog" aria-labelledby="modalAlertLabel"
			 aria-hidden="true" data-backdrop="false">
			<div class="modal-dialog modal-m modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header p-2">
						<h5 class="modal-title" id="modalAlertLabel">Thông báo</h5>
						<button class="close" data-dismiss="modal" aria-label="Đóng" @click="resolveAction(false)">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body p-2" v-html="message">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal" @click="resolveAction(true)">OK</button>
					</div>
				</div>
			</div>
		</div>

		<div class="modal zoom" id="modalDongBoHD" tabindex="-1" role="dialog" aria-labelledby="modalDongBoHDLabel"
			 aria-hidden="true" data-backdrop="false">
			<div class="modal-dialog modal-sm modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header p-2">
						<h5 class="modal-title" id="modalDongBoHDLabel">Cập nhật serial</h5>
						<button class="close" data-dismiss="modal" aria-label="Đóng">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body p-2">
						Hãy nhập Fkey mới để lấy thông tin serial từ invoice<br>
						<div class="input-group mb-2">
							<div class="input-group-prepend">
								<label class="input-group-text w-100px" for="txtFKey">FKey <span
									class="text-danger">(*)</span></label>
							</div>
							<input type="text" class="form-control" v-model="txtFKey_Value" id="txtFKey">
						</div>
						<button class="btn btn-primary btn-block" @click="btnCapNhatSerial_Click">Cập nhật</button>
					</div>
				</div>
			</div>
		</div>

		<div class="modal zoom" id="modalCapNhatHD" tabindex="-1" role="dialog" aria-labelledby="modalCapNhatHDLabel"
			 aria-hidden="true" data-backdrop="false">
			<div class="modal-dialog modal-xl modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header p-2">
						<h5 class="modal-title" id="modalCapNhatHDLabel">Cập nhật nội dung hóa đơn</h5>
						<button class="close" data-dismiss="modal" aria-label="Đóng" @click="btnLamMoi_Click()">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="list-actions-top">
						<ul class="list">
							<li>
								<a href="javascript:void(0)" @click="btnCapNhatND_Click">
									<span class="one-save"></span> Cập nhật
								</a>
							</li>
							<li class="float-right">
								<i class="fa fa-warning text-warning"></i> Dòng vàng là nội dung trước khi cập nhật
							</li>
						</ul>
					</div>
					<div class="modal-body p-3">
						<div class="row bold bg-header">
							<div class="col-1 border">STT</div>
							<div class="col-5 border">Tên hàng hóa, dịch vụ</div>
							<div class="col-1 border">Đơn vị tính</div>
							<div class="col-1 border text-right">Số lượng</div>
							<div class="col-2 border text-right">Đơn giá</div>
							<div class="col-2 border text-right">Thành tiền</div>
						</div>
						<div v-for="item in dsNoiDungHD" :key="item.value">
							<div class="row bg-original">
								<div class="col-1 border">{{ item.goc.thutu_in }}</div>
								<div class="col-5 border">{{ item.goc.tenkhoanmuc_hd }}</div>
								<div class="col-1 border">{{ item.goc.dv_tinh }}</div>
								<div class="col-1 border text-right">{{ item.goc.so_luong }}</div>
								<div class="col-2 border text-right">{{ item.goc.don_gia }}</div>
								<div class="col-2 border text-right">{{ item.goc.tien }}</div>
							</div>
							<div class="row">
								<input type="text" pattern="\d*" v-model="item.thutu_in" class="col-1 border"
									   maxlength="2">
								<textarea v-model="item.tenkhoanmuc_hd" class="col-5 border" maxlength="500"></textarea>
								<input type="text" v-model="item.dv_tinh" class="col-1 border" maxlength="100">
								<div class="col-1 border bg-striped"></div>
								<div class="col-2 border bg-striped"></div>
								<div class="col-2 border bg-striped"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="modal zoom" id="modalCapNhatHdTheoFile" tabindex="-1" role="dialog"
			 aria-labelledby="modalCapNhatHdTheoFileLabel" aria-hidden="true" data-backdrop="false">
			<div class="modal-dialog modal-xl modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header p-2">
						<h5 class="modal-title" id="modalCapNhatHdTheoFileLabel">Cập nhật nội dung hóa đơn theo file
						</h5>
						<button class="close" data-dismiss="modal" aria-label="Đóng">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="list-actions-top">
						<ul class="list">
							<li>
								<a href="javascript:void(0)" @click="btnCapNhatNoiDung_Click">
									<span class="one-save"></span> Cập nhật
								</a>
							</li>
							<li>
								<a href="javascript:void(0)" @click="btnTaiFileMau_Click"
								   title="Tải file mẫu từ danh sách mã thanh toán cần phát hành">
									<span class="one-excel"></span> Tải file mẫu
								</a>
							</li>
							<li class="float-right">
								<i class="fa fa-warning text-warning"></i> Nhấn tải file mẫu, điều chỉnh nội dung file
								rồi chọn file đó để cập nhật
							</li>
						</ul>
					</div>
					<div class="modal-body p-3">
						<div class="legend-title">Chọn file</div>
						<div class="row mb-2">
							<div class="col-8">
								<div class="input-group">
									<div class="input-group-prepend">
										<label class="input-group-text w-100px" for="btnMoFile2">File Excel</label>
										<button class="btn btn-primary" id="btnMoFile2" @click="btnMoFile2_Click" v-show="ds_noidung_hddt.length === 0"
												title="Mở file excel đã điều chỉnh từ file mẫu">
											<i class="fa fa-folder-open"></i> Mở file
										</button>
										<button class="btn btn-danger" @click="btnXoa2_Click"
												v-show="ds_noidung_hddt.length > 0"
												title="Xóa danh sách để mở file mới">
											<i class="fa fa-times"></i> Xóa
										</button>
									</div>
									<input type="text" class="form-control bg-white" readonly
										   :value="ds_noidung_hddt_text"
										   placeholder="Bấm [ Mở file ] để nạp vào danh sách điều chỉnh nội dung">
								</div>
							</div>
							<div class="col-4">
								<div class="input-group">
									<div class="input-group-prepend">
										<label class="input-group-text w-100px" for="cboSheet">Sheet</label>
									</div>
									<select class="form-control" v-model="cboSheet_Value" id="cboSheet">
										<option v-for="item in cboSheet_DataSource" :value="item.value"
												:key="item.value">{{ item.text }}
										</option>
									</select>
								</div>
							</div>
						</div>

						<div class="legend-title">Danh sách hóa đơn điện tử</div>
						<DataGrid v-bind:columns="ds_noidung_hddt_Columns" v-bind:dataSource="ds_noidung_hddt"
								  :showColumnCheckbox="false" :enable-paging-server="false" :showFilter="true"
								  :toolbar="null"
								  panelDataHeight="230px" :pageSize="50" :allowPaging="true" :enabledSelectFirstRow="false">
						</DataGrid>
					</div>
				</div>
			</div>
		</div>

		<div class="modal zoom" id="modalCapNhatSerialTheoFile" tabindex="-1" role="dialog"
			 aria-labelledby="modalCapNhatSerialTheoFileLabel" aria-hidden="true" data-backdrop="false">
			<div class="modal-dialog modal-xl modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header p-2">
						<h5 class="modal-title" id="modalCapNhatSerialTheoFileLabel">Cập nhật serial hóa đơn theo file
						</h5>
						<button class="close" data-dismiss="modal" aria-label="Đóng" @click="btnLamMoi_Click()">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="list-actions-top">
						<ul class="list">
							<template v-if="ds_serial_hddt.length > 0">
								<li v-if="ds_serial_hddt.filter(x => x.status === 'OK').length === ds_serial_hddt.length">
									<a href="javascript:void(0)" @click="btnCapNhatSerial2_Click">
										<span class="one-save"></span> Cập nhật
									</a>
								</li>
								<template v-else>
									<li>
										<a href="javascript:void(0)" @click="btnLayThongTin_Click" title="Lấy thông tin hóa đơn từ Invoice">
											<span class="fa fa-download"></span> Lấy TT
										</a>
									</li>
									<li>
										<a href="javascript:void(0)" @click="btnXoaHdLoi_Click" title="Xóa các hóa đơn có trạng thái lỗi">
											<span class="one-save"></span> Xóa HĐ lỗi
										</a>
									</li>
								</template>
							</template>
							<li>
								<a href="javascript:void(0)" @click="btnTaiFileMau2_Click"
								   title="Tải file mẫu từ danh sách mã thanh toán cần phát hành">
									<span class="one-excel"></span> Tải file mẫu
								</a>
							</li>
							<li class="float-right">
								<i class="fa fa-warning text-warning"></i> Nhấn tải file mẫu, nhập fkey mới sau đó chọn file để cập nhật
							</li>
						</ul>
					</div>
					<div class="modal-body p-3">
						<div class="legend-title">Chọn file</div>
						<div class="row mb-2">
							<div class="col-8">
								<div class="input-group">
									<div class="input-group-prepend">
										<label class="input-group-text w-100px" for="btnMoFile2">File Excel</label>
										<button class="btn btn-primary" id="btnMoFile3" @click="btnMoFile3_Click"
												title="Mở file excel đã điều chỉnh từ file mẫu" v-show="ds_serial_hddt.length === 0">
											<i class="fa fa-folder-open"></i> Mở file
										</button>
										<button class="btn btn-danger" @click="btnXoa3_Click"
												v-show="ds_serial_hddt.length > 0"
												title="Xóa danh sách để mở file mới">
											<i class="fa fa-times"></i> Xóa
										</button>
									</div>
									<input type="text" class="form-control bg-white" readonly
										   :value="ds_serial_hddt_text"
										   placeholder="Bấm [ Mở file ] để nạp vào danh sách điều chỉnh serial">
								</div>
							</div>
							<div class="col-4">
								<div class="input-group">
									<div class="input-group-prepend">
										<label class="input-group-text w-100px" for="cboSheet">Sheet</label>
									</div>
									<select class="form-control" v-model="cboSheet2_Value" id="cboSheet">
										<option v-for="item in cboSheet2_DataSource" :value="item.value"
												:key="item.value">{{ item.text }}
										</option>
									</select>
								</div>
							</div>
						</div>

						<div class="legend-title">
							Danh sách hóa đơn điện tử
							<button v-show="dsHoaDon_DataSource.length"
									class="btn btn-success btn-sm float-right pt-0 pb-0" @click="btnXuatExcel4_Click"
									title="Xuất Excel danh sách hóa đơn điện tử">
								<span class="one one-excel"></span> Xuất Excel
							</button>
						</div>
						<DataGrid v-bind:columns="dsHoaDon2_Columns" v-bind:dataSource="ds_serial_hddt"
								  :showColumnCheckbox="true" :enable-paging-server="false" :showFilter="true"
								  :toolbar="null"
								  panelDataHeight="230px" :pageSize="50" :allowPaging="true" :enabledSelectFirstRow="false">
						</DataGrid>
					</div>
				</div>
			</div>
		</div>

		<div class="modal zoom" id="modalPreviewHD" tabindex="-1" role="dialog" aria-labelledby="modalPreviewHDLabel"
			 aria-hidden="true" data-backdrop="false">
			<div class="modal-dialog modal-xl modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header p-2">
						<h5 class="modal-title" id="modalPreviewHDLabel">Phát hành hóa đơn điện tử</h5>
						<button class="close" data-dismiss="modal" aria-label="Đóng">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="list-actions-top">
						<ul class="list">
							<li>
								<a href="javascript:void(0)" @click="btnPhatHanh2_Click">
									<i class="fa fa-upload"></i> Phát hành
								</a>
							</li>
							<li>
								<a href="javascript:void(0)" @click="btnXuatExcel2_Click">
									<span class="one-excel"></span> Xuất excel
								</a>
							</li>
						</ul>
					</div>
					<div class="modal-body p-2">
						<div class="legend-title">Mẫu số / ký hiệu</div>
						<div class="row mb-2">
							<div class="col-6 mb-2">
								<div class="input-group">
									<div class="input-group-prepend">
										<label class="input-group-text w-100px" for="cboMauSo">Mẫu số <span
											class="text-danger">(*)</span></label>
									</div>
									<select class="form-control" v-model="cboMauSo_Value" id="cboMauSo">
										<option v-for="item in cboMauSo_DataSource" :value="item.value"
												:key="item.value">{{ item.text }}
										</option>
									</select>
								</div>
							</div>
							<div class="col-6">
								<div class="input-group">
									<div class="input-group-prepend">
										<label class="input-group-text w-100px" for="cboKyHieu">Ký hiệu <span
											class="text-danger">(*)</span></label>
									</div>
									<select class="form-control" v-model="cboKyHieu_Value" id="cboKyHieu">
										<option v-for="item in cboKyHieu_DataSource" :value="item.value"
												:key="item.value">{{ item.text }}
										</option>
									</select>
								</div>
							</div>
						</div>
						<div class="legend-title">Danh sách hóa đơn điện tử</div>
						<DataGrid v-bind:columns="dsPreview_Columns" v-bind:dataSource="dsPreview_DataSource"
								  :showColumnCheckbox="false" :enable-paging-server="false" :showFilter="true"
								  :toolbar="null"
								  panelDataHeight="230px" :pageSize="50" :allowPaging="true" :enabledSelectFirstRow="false">
						</DataGrid>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<style scoped>
.input-group {
	position: relative;
	display: -webkit-box;
	display: -ms-flexbox;
	display: flex;
	-ms-flex-wrap: wrap;
	flex-wrap: wrap;
	-webkit-box-align: stretch;
	-ms-flex-align: stretch;
	align-items: stretch;
	width: 100%;
}

.w-100px {
	width: 100px;
}

.input-group select {
	-webkit-appearance: none;
	-moz-appearance: none;
	background: transparent url("data:image/svg+xml;utf8,<svg fill='black' height='24' viewBox='0 0 24 24' width='24' xmlns='http://www.w3.org/2000/svg'><path d='M7 10l5 5 5-5z'/><path d='M0 0h24v24H0z' fill='none'/></svg>") no-repeat;
	background-position-x: 100%;
	background-position-y: 5px;
}

.list-actions-top {
	-webkit-box-shadow: none;
	box-shadow: none;
}

.zoom {
	display: none;
	transition: opacity .2s ease-in-out, transform .2s ease-in-out;
	transform: scale(0.5);
	opacity: 0;
}

/*noinspection CssUnusedSymbol*/
.zoom.show {
	display: block;
	opacity: 1;
	transform: scale(1);
}

.modal-content {
	box-shadow: 0 0 0 0 rgba(255, 255, 255, 1);
	transition: box-shadow .2s ease-in-out;
}

.zoom.show .modal-content {
	box-shadow: 0 0 1000px 1000px rgba(50, 50, 50, 0.9);
}

.bg-header {
	background-color: lightcyan;
}

.bg-original {
	background-color: lightyellow;
}

.bg-striped {
	background: repeating-linear-gradient(135deg,
	transparent,
	transparent 10px,
	#eee 10px,
	#eee 20px)
}

.mb-2 {
	margin-bottom: .5rem !important;
}
</style>
