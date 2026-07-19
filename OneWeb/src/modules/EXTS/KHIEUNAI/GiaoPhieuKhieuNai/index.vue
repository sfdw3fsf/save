<template src="./index.template.html"></template>
<script>

export default {
  props: {
    argument: Object,
    data:Object,
  },
  data() {
    return {
      parameter: {
        kieuId: 0,
        huongGiaoId: null,
        maTb: null,
        ttphId: null,
        ngaygiao: null,
      },
      dsTrangThai: [],
      trangThaiId: null,
      ngayGiaoTimKiem: null,
      ngayGiaoPhieu: new Date(),
      dsHuongGiao: [],
      huongGiaoId: null,
      dsDonViXuLy: [],
      donViXuLyId: null,
      soMayTimKiem: null,
      soMayGiaoPhieu: null,
      soCongVan: null,
      noiDungGiao: null,
      btnTimKiem: {
        enabled: true,
      },
      btnGiaoPhieu: {
        enabled: true,
      },
      btnHuyGiao: {
        enabled: true,
      },
      btnCapNhatDonVi: {
        enabled: true,
      },
      dsKhieuNai: [],
      kyCuocHienHanh: null,
      dsKeyKhieuNaiDuocChon: [],
      checkAllKhieuNai: false,
      khienNaiDuocFocus: null,
      dsThamSoMacDinh: [],
      load: false,
    };
  },
  computed: {
    allowThemKhdbKhiGiaoPhieuKn() {
      return this.dsThamSoMacDinh.some((thamSo) => {
        return thamSo.ma_ts == "THEM_KHDB_KHI_GIAOPHIEU_KN" && thamSo.ten_ts == "1";
      });
    },
    allowNhanTinGiaoPhieuXlkn() {
      let phanVungId = this.$root.token.getPhanVungID();

      if (phanVungId == 26 /*HPG*/) {
        return true;
      }

      return this.dsThamSoMacDinh.some((thamSo) => {
        return thamSo.ma_ts == "NHANTIN_GIAOPHIEU_XLKN" && thamSo.ten_ts == "1";
      });
    },
    allowGiaoViecTuDong() {
      let huongGiao = this.dsHuongGiao.find((huongGiao) => {
        return parseInt(huongGiao.huonggiao_id) == parseInt(this.huongGiaoId);
      });

      return !!huongGiao && parseInt(huongGiao.giaoviec) == 1;
    },
  },
  watch: {
    async huongGiaoId(value) {
      if (!value) {
        this.donViXuLyId = null;
        this.dsDonViXuLy = [];
        this.dsKhieuNai = [];
        return;
      }

      try {
        this.loading(true);
        await this.layDsDonViXuLy(value);
      } finally {
        this.loading(false);
      }

      if (!!this.dsDonViXuLy && this.dsDonViXuLy.length > 0) {
        this.donViXuLyId = this.dsDonViXuLy[0].ID;
      }

      if (this.load) {
        await this.hienThiDanhSachKhieuNai();
      }
    },
    async trangThaiId() {
      if (this.load) {
        await this.hienThiDanhSachKhieuNai();
      }
    },
    async ngayGiaoTimKiem() {
      if (this.load) {
        await this.hienThiDanhSachKhieuNai();
      }
    },
    checkAllKhieuNai(newVal) {
      if (newVal) {
        this.dsKeyKhieuNaiDuocChon = this.dsKhieuNai.map((item) => item.KEY);
        return;
      }

      if (
        this.dsKeyKhieuNaiDuocChon.length > 0 &&
        this.dsKeyKhieuNaiDuocChon.length == this.dsKhieuNai.length
      ) {
        this.dsKeyKhieuNaiDuocChon = [];
      }
    },
    dsKeyKhieuNaiDuocChon(newVal) {
      this.checkAllKhieuNai =
        newVal.length > 0 && newVal.length == this.dsKhieuNai.length;
    },
    dsKhieuNai() {
      this.dsKeyKhieuNaiDuocChon = [];
    },
  },
  async mounted() {
    if (!!this.argument) {
      if (this.argument.kieuId != null) {
        this.parameter.kieuId = this.argument.kieuId;
      }

      if (this.argument.huongGiaoId != null) {
        this.parameter.huongGiaoId = this.argument.huongGiaoId;
      }

      if (this.argument.ttphId != null) {
        this.parameter.ttphId = this.argument.ttphId;
      }

      if (this.argument.ngaygiao != null) {
        this.parameter.ngaygiao = this.argument.ngaygiao;
      }

      if (this.argument.maTb != null) {
        this.parameter.maTb = this.argument.maTb;
      }
      
      
    }

    try {
      this.loading(true);
      let loading1 = this.layDsTrangThai();
      let loading2 = this.layDsHuongGiao();
      let loading3 = this.layKyCuocHienHanh();
      let loading4 = this.layDsThamSoMacDinh();
      await loading1;
      await loading2;
      await loading3;
      await loading4;
    } finally {
      this.loading(false);
    }

    if (!!this.dsTrangThai && this.dsTrangThai.length > 0) {
      if (this.parameter.ttphId != null) {
        this.trangThaiId = this.parameter.ttphId;
      } else {
        this.trangThaiId = this.dsTrangThai[0].trangthai_id;
      }
    }

    if (!!this.dsHuongGiao && this.dsHuongGiao.length > 0) {
      if (this.parameter.kieuId == 1 && this.parameter.huongGiaoId != null) {
        this.huongGiaoId = this.parameter.huongGiaoId;
      } else {
        this.huongGiaoId = this.dsHuongGiao[0].huonggiao_id;
      }
    }

    if (this.parameter.ngaygiao != null) {
      this.ngayGiaoTimKiem = this.ddmmyyyyToDate(this.parameter.ngaygiao);
    } else {
      this.ngayGiaoTimKiem = new Date();
    }

    if (this.parameter.ngaygiao != null) {
      this.ngayGiaoTimKiem = this.ddmmyyyyToDate(this.parameter.ngaygiao);
    } else {
      this.ngayGiaoTimKiem = new Date();
    }

    if (this.parameter.maTb != null) {
      this.soMayTimKiem = this.parameter.maTb;
    }

    let loadData = async () => {
      await this.hienThiDanhSachKhieuNai();
      this.load = true;
    };
    
    setTimeout(loadData, 200);
  },
  methods: {
    bindData: async function (kieu_id, huonggiao_id, ma_tb, ttph, ngaygiao) {
      this.parameter.kieuId = kieu_id;
      this.parameter.huongGiaoId = huonggiao_id;
      this.huongGiaoId = huonggiao_id;
      if (ma_tb != "0") {
        this.parameter.maTb = ma_tb;
        this.soMayTimKiem = ma_tb;
      }
      this.parameter.ttphId = ttph;
      this.trangThaiId = ttph;
      if (ngaygiao == "0") {
        this.parameter.ngaygiao = new Date();
        this.ngayGiaoTimKiem = new Date();
      }
      await this.hienThiDanhSachKhieuNai();
    },
    getColumnTemplateChonNhieu(parent) {
      return function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="check"
                                    v-model="parent.dsKeyKhieuNaiDuocChon"
                                    :value="data.KEY"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                parent: parent,
                data: {},
              };
            },
          },
        };
      };
    },
    getHeaderTemplateChonNhieu(parent) {
      return function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAllKhieuNai" class="check"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                parent: parent,
              };
            },
          },
        };
      };
    },
    onClickBtnHuyGiao() {
      if (!this.btnHuyGiao.enabled) {
        return;
      }

      if (this.dsKeyKhieuNaiDuocChon.length == 0) {
        this.$root.toastError("Bạn chưa chọn phiếu để hủy");
        return;
      }

      this.$refs["modal-xacnhan-huy"].show();
    },
    async okHuyGiaoPhieu() {
      this.$refs["modal-xacnhan-huy"].hide();

      let dsKhieuNai = this.dsKhieuNai.filter((khieuNai) => {
        return this.dsKeyKhieuNaiDuocChon.includes(khieuNai.KEY);
      });

      let dsPhieu = dsKhieuNai.map((khieuNai) => {
        return {
          KHIEUNAI_ID: khieuNai.KHIEUNAI_ID,
          PHIEUKN_ID: khieuNai.PHIEUKN_ID,
          TTPH_ID: khieuNai.TTPH_ID,
          MA_TB: khieuNai.MA_TB,
        };
      });

      try {
        this.loading(true);
        await this.huyGiaoPhieuKhieuNai({ ds_phieu: JSON.stringify(dsPhieu) });
      } catch (err) {
        this.$root.toastError(err);
        return;
      } finally {
        this.loading(false);
      }

      this.$root.toastSuccess("Đã hủy giao phiếu thành công!");
      this.hienThiDanhSachKhieuNai();
    },
    async onClickBtnGiaoPhieu() {
      if (!this.btnGiaoPhieu.enabled) {
        return;
      }

      if (!this.dsKeyKhieuNaiDuocChon || this.dsKeyKhieuNaiDuocChon.length == 0) {
        this.$root.toastError("Bạn chưa chọn phiếu giao");
        return;
      }

      let dsKhieuNai = this.dsKhieuNai.filter((khieuNai) => {
        return this.dsKeyKhieuNaiDuocChon.includes(khieuNai.KEY);
      });

      let dsPhieu = dsKhieuNai.map((khieuNai) => {
        return {
          KHIEUNAI_ID: khieuNai.KHIEUNAI_ID,
          DONVI_NHAN_ID: khieuNai.DONVINHAN_ID,
          PHIEU_CHA_ID: khieuNai.PHIEUKN_ID,
          TTKN_ID: khieuNai.TTKN_ID,
        };
      });

      let data = {
        huonggiao_id: this.huongGiaoId,
        ngaygiao: this.ddmmyyyyhhmi(this.ngayGiaoPhieu),
        nd_giao: !this.noiDungGiao ? "" : this.noiDungGiao.trim(),
        socongvan: !this.soCongVan ? "" : this.soCongVan.trim(),
        ds_phieu: JSON.stringify(dsPhieu),
      };

      let dsPhieuGiao = null;

      try {
        this.loading(true);
        dsPhieuGiao = await this.giaoPhieuKhieuNai(data);
      } catch (err) {
        this.$root.toastError(err);
        return;
      } finally {
        this.loading(false);
      }

      this.$root.toastSuccess("Đã giao phiếu thành công");
      this.$emit("success");
      this.noiDungGiao = null;
      this.soMayGiaoPhieu = null;
      await this.hienThiDanhSachKhieuNai();

      if (this.allowThemKhdbKhiGiaoPhieuKn) {
        let dsKhieuNaiId = dsPhieu.map((phieu) => phieu.KHIEUNAI_ID).join();
        try {
          this.loading(true);
          await this.capNhatKhdbTheoDsknId({
            ds_khieunai_id: dsKhieuNaiId,
            nguoi_cn: this.$root.token.getUserName(),
          });
        } catch (err) {
          this.$root.toastError("Cập nhật khách hàng đặc biệt thất bại");
        } finally {
          this.loading(false);
        }
      }

      if (this.allowNhanTinGiaoPhieuXlkn) {
        try {
          this.loading(true);

          for (let i = 0; i < dsPhieu.length; i++) {
            let phieu = dsPhieu[i];
            await this.xuLyThongBaoDonVi(phieu.KHIEUNAI_ID, phieu.DONVI_NHAN_ID, this.huongGiaoId);

            // let noiDungTinNhan = await this.layNoiDungTinNhanKhieuNai({
            //   khieunai_id: phieu.KHIEUNAI_ID,
            //   donvi_nhan_id: phieu.DONVI_NHAN_ID,
            // });

            // if (!!noiDungTinNhan) {
            //   let soDienThoai = await this.laySdtNhanTin({
            //     huonggiao_id: this.huongGiaoId,
            //     donvi_nhan_id: phieu.DONVI_NHAN_ID,
            //   });

            //   if (!!soDienThoai && soDienThoai.length > 0) {
            //     for (let i = 0; i < soDienThoai.length; i++) {
            //       try {
            //         await this.sendSms(soDienThoai[i], noiDungTinNhan);
            //       } catch (err) {}
            //     }
            //   }
            // }
          }
        } catch (err) {
          this.$root.toastError("Gửi tin nhắn thất bại");
        } finally {
          this.loading(false);
        }
      }

      if (this.allowGiaoViecTuDong) {
        try {
          this.loading(true);

          for (let i = 0; i < dsPhieuGiao.length; i++) {
            let phieuGiao = dsPhieuGiao[i];
            let data = {
              khieunai_id: phieuGiao.split("_")[0],
              phieukn_id: phieuGiao.split("_")[1],
            };
            await this.thongBaoGiaoViecNhanVienTuDong(data);
            // let noiDungTinNhan = await this.giaoViecNhanVienTuDong(data);

            // if (!!noiDungTinNhan && noiDungTinNhan.length > 0) {
            //   for (let j = 0; j < noiDungTinNhan.length; j++) {
            //     let soDienThoai = noiDungTinNhan[j].SO_DT;
            //     let noiDung = noiDungTinNhan[j].NOIDUNG_SMS;

            //     if (!!soDienThoai && !!noiDung) {
            //       await this.sendSms(soDienThoai, noiDung);
            //     }
            //   }
            // }
          }
        } catch (err) {
          this.$root.toastError("Giao việc nhân viên thất bại");
        } finally {
          this.loading(false);
        }
      }
    },
    async onClickBtnGiaoPhieu_01() {
      if (!this.btnGiaoPhieu.enabled) {
        return;
      }

      if (!this.dsKeyKhieuNaiDuocChon || this.dsKeyKhieuNaiDuocChon.length == 0) {
        this.$root.toastError("Bạn chưa chọn phiếu giao");
        return;
      }

      let dsKhieuNai = this.dsKhieuNai.filter((khieuNai) => {
        return this.dsKeyKhieuNaiDuocChon.includes(khieuNai.KEY);
      });

      let dsPhieu = dsKhieuNai.map((khieuNai) => {
        return {
          KHIEUNAI_ID: khieuNai.KHIEUNAI_ID,
          MA_KN: khieuNai.MA_KN,
          DONVI_NHAN_ID: khieuNai.DONVINHAN_ID,
          PHIEU_CHA_ID: khieuNai.PHIEUKN_ID,
          TTKN_ID: khieuNai.TTKN_ID,
        };
      });

      var totalCount = dsPhieu.length;
      var successCount = 0;

      try {
        this.loading(true);

        while (dsPhieu.length > 0) {
          var subDsPhieu = [];

          while (subDsPhieu.length < 100 && dsPhieu.length > 0) {
            subDsPhieu.push(dsPhieu.pop());
          }

          if (subDsPhieu.length == 0) {
            break;
          }

          let data = {
            huonggiao_id: this.huongGiaoId,
            ngaygiao: this.ddmmyyyyhhmi(this.ngayGiaoPhieu),
            nd_giao: !this.noiDungGiao ? "" : this.noiDungGiao.trim(),
            socongvan: !this.soCongVan ? "" : this.soCongVan.trim(),
            ds_phieu: JSON.stringify(subDsPhieu),
          };

          let dsPhieuGiao = await this.giaoPhieuKhieuNai(data);

          successCount += subDsPhieu.length;

          if (this.allowThemKhdbKhiGiaoPhieuKn) {
            let dsKhieuNaiId = subDsPhieu.map((phieu) => phieu.KHIEUNAI_ID).join();
            try {
              await this.capNhatKhdbTheoDsknId({
                ds_khieunai_id: dsKhieuNaiId,
                nguoi_cn: this.$root.token.getUserName(),
              });
            } catch (err) {
              console.log(
                `Cập nhật khách hàng đặc biệt thất bại [dsKhieuNaiId=${dsKhieuNaiId}]`,
                err
              );
            }
          }

          if (this.allowNhanTinGiaoPhieuXlkn) {
            try {
              for (let i = 0; i < subDsPhieu.length; i++) {
                let phieu = subDsPhieu[i];
                await this.xuLyThongBaoDonVi(phieu.KHIEUNAI_ID, phieu.DONVI_NHAN_ID, this.huongGiaoId);

                // let noiDungTinNhan = await this.layNoiDungTinNhanKhieuNai({
                //   khieunai_id: phieu.KHIEUNAI_ID,
                //   donvi_nhan_id: phieu.DONVI_NHAN_ID,
                // });

                // if (!!noiDungTinNhan) {
                //   let soDienThoai = await this.laySdtNhanTin({
                //     huonggiao_id: this.huongGiaoId,
                //     donvi_nhan_id: phieu.DONVI_NHAN_ID,
                //   });

                //   if (!!soDienThoai && soDienThoai.length > 0) {
                //     for (let i = 0; i < soDienThoai.length; i++) {
                //       this.sendSms(soDienThoai[i], noiDungTinNhan);
                //     }
                //   }
                // }
                /*
                                // Sửa theo yêu cầu vb điều chỉnh 
                                let soDienThoai = await this.laySdtNhanTin({
                                        huonggiao_id: this.huongGiaoId,
                                        donvi_nhan_id: phieu.DONVI_NHAN_ID
                                    })

                                    if (!!soDienThoai && soDienThoai.length > 0) {

                                        for (let i = 0; i < soDienThoai.length; i++) {
                                            let noiDungTinNhan=`user:${soDienThoai[i].TEN_NV} dang co PAKN (${phieu.MA_KN}) de nghi anh/chi xu ly som va thong bao ket qua cho TT HTKH de tra loi khach hang`
                                            this.sendSms(soDienThoai[i].SO_DT, noiDungTinNhan)
                                            //console.log(noiDungTinNhan)
                                        }
                                    }
                                */
              }
            } catch (err) {
              console.log("Lấy thông tin gửi tin nhắn thất bại", err);
            }
          }

          if (this.allowGiaoViecTuDong) {
            try {
              for (let i = 0; i < dsPhieuGiao.length; i++) {
                let phieuGiao = dsPhieuGiao[i];

                let data = {
                  khieunai_id: phieuGiao.split("_")[0],
                  phieukn_id: phieuGiao.split("_")[1],
                };
                await this.thongBaoGiaoViecNhanVienTuDong(data);

                // let noiDungTinNhan = await this.giaoViecNhanVienTuDong(data);

                // if (!!noiDungTinNhan && noiDungTinNhan.length > 0) {
                //   for (let j = 0; j < noiDungTinNhan.length; j++) {
                //     let soDienThoai = noiDungTinNhan[j].SO_DT;
                //     let noiDung = noiDungTinNhan[j].NOIDUNG_SMS;

                //     if (!!soDienThoai && !!noiDung) {
                //       this.sendSms(soDienThoai, noiDung);
                //     }
                //   }
                // }
              }
            } catch (err) {
              console.log("Giao việc nhân viên thất bại");
            }
          }
        }
      } catch (err) {
        this.$root.toastError(err);
      } finally {
        this.loading(false);
      }

      if (dsPhieu.length == 0) {
        this.$root.toastSuccess("Đã giao phiếu thành công");
        this.$emit("success");
      } else {
        this.$root.toastError(`Đã giao phiếu thành công: ${successCount}/${totalCount}`);
      }

      this.noiDungGiao = null;
      this.soMayGiaoPhieu = null;
      await this.hienThiDanhSachKhieuNai();
    },
    async onClickBtnCapNhatDonVi() {
      if (!this.btnCapNhatDonVi.enabled) {
        return;
      }

      if (this.dsKhieuNai.length == 0) {
        this.$root.toastError("Không có dữ liệu. Bạn hãy kiểm tra lại!");
        return;
      }

      if (!this.khienNaiDuocFocus) {
        this.$root.toastError("Bạn chưa chọn khiếu nại");
        return;
      }

      if (!this.donViXuLyId) {
        this.$root.toastError("Bạn chưa chọn đơn vị xử lý");
        return;
      }

      let data = {
        donvi_id: this.donViXuLyId,
        khieunai_id: this.khienNaiDuocFocus.KHIEUNAI_ID,
        huonggiao_id: this.huongGiaoId,
      };

      try {
        this.loading(true);
        await this.capNhatDonVi(data);
      } catch (err) {
        this.$root.toastError(err);
        return;
      } finally {
        this.loading(false);
      }

      this.$root.toastSuccess("Đã cập nhật lại đơn vị nhận !");

      let donViXuLy = this.dsDonViXuLy.find((donVi) => {
        return parseInt(donVi.ID) == parseInt(this.donViXuLyId);
      });

      let khieuNai = { ...this.khienNaiDuocFocus };
      khieuNai.DONVINHAN_ID = donViXuLy.ID;
      khieuNai.DONVINHAN = donViXuLy.NAME;

      let grid = this.$refs["grid-khieu-nai"].grid;
      grid.batchUpdate({ changedRecords: [khieuNai] });
    },
    onSelectRowKhieuNai(args) {
      let rowData = args.rowData;
      this.khienNaiDuocFocus = rowData;

      if (!rowData) {
        this.soMayGiaoPhieu = null;
        this.noiDungGiao = null;
        this.donViXuLyId = null;
      } else {
        this.soMayGiaoPhieu = rowData.MA_TB;
        this.noiDungGiao = rowData.ND_GIAO;
        this.donViXuLyId = rowData.DONVI_ID;
      }
    },
    gridDsKhieuNai_OnCellSelectedChange: async function (args) {
      console.log("gridDsKhieuNai_OnCellSelectedChange", args);
      let grid = this.$refs["grid-khieu-nai"];
      if (
        !(
          args.selectedRowCellIndex == undefined ||
          args.selectedRowCellIndex == null ||
          args.selectedRowCellIndex.length == 0
        )
      ) {
        for (let i = 0; i < args.selectedRowCellIndex.length; i++) {
          const rowCell = args.selectedRowCellIndex[i];
          if (rowCell.cellIndexes.length == 1) {
            if (rowCell.cellIndexes.includes(1)) {
              let row = grid.grid.getRowInfo(grid.grid.getRowByIndex(rowCell.rowIndex));
              if (!this.dsKeyKhieuNaiDuocChon.includes(row.rowData.KEY)) {
                //dsKeyKhieuNaiDuocChon
                this.dsKeyKhieuNaiDuocChon.push(row.rowData.KEY);
              }
            }
          }
        }
      }
      this.onSelectRowKhieuNai({ rowData: args.data });
      //await this.gridDS_TB_KHIEUNAI_SelectedRowChanged(args.data)
      grid.grid.clearSelection();
    },
    onClickBtnTimKiem() {
      if (this.btnTimKiem.enabled) {
        this.hienThiDanhSachKhieuNai();
      }
    },
    async hienThiDanhSachKhieuNai() {
      let trangThaiId = parseInt(this.trangThaiId);

      if (trangThaiId == 1) {
        // chưa giao
        this.btnCapNhatDonVi.enabled = true;
        this.btnHuyGiao.enabled = false;
        this.btnTimKiem.enabled = true;
        this.btnGiaoPhieu.enabled = true;
      } else {
        // đã giao
        this.btnCapNhatDonVi.enabled = false;
        this.btnHuyGiao.enabled = true;
        this.btnTimKiem.enabled = false;
        this.btnGiaoPhieu.enabled = false;
      }

      if (!this.huongGiaoId) {
        this.$root.toastError("Bạn chưa chọn hướng giao");
        return;
      }

      switch (trangThaiId) {
        case 1: // chưa giao
          await this.thucHienLayDsKhieuNaiChuaGiao();
          if (this.dsKhieuNai.length==0)
          if (this.data!=null)
              this.dsKhieuNai.push(this.data)
          break;
        case 2: // đã giao
          await this.thucHienLayDsKhieuNaiDaGiao();
          break;
        default:
          break;
      }

      if (this.dsKhieuNai.length == 0) {
        this.btnCapNhatDonVi.enabled = false;
        this.btnGiaoPhieu.enabled = false;
        this.soMayGiaoPhieu = null;
        this.noiDungGiao = null;
      } else {
        this.onSelectRowKhieuNai({ rowData: this.dsKhieuNai[0] });

        if (this.parameter.kieuId == 1) {
          if (!this.load) {
            this.dsKeyKhieuNaiDuocChon = this.dsKhieuNai.map((item) => item.KEY);
          }
        }
      }
    },
    async thucHienLayDsKhieuNaiDaGiao() {
      try {
        this.loading(true);
        await this.layDsKhieuNaiDaGiao({
          vhuonggiao_id: this.huongGiaoId,
          vkyhoadon: this.kyCuocHienHanh, // TODO: 20210401
          vma_tb: this.soMayTimKiem,
          vngay_giao: this.ddmmyyyy(this.ngayGiaoTimKiem), // TODO: '16/09/2021'
        });
      } finally {
        this.loading(false);
      }
    },
    async layDsKhieuNaiDaGiao(data) {
      try {
        let response = await this.$root.context.post(
          `/web-gqkn/GiaoPhieuKhieuNai/gqkn_lay_ds_khieunai_dagiao`,
          data
        );

        if (response.error_code === "BSS-00000000" && response.data != null) {
          response.data.forEach((item, index) => (item.KEY = index + 1));
          this.dsKhieuNai = response.data;
          return;
        }

        throw { response: { data: response } };
      } catch (err) {
        this.dsKhieuNai = [];
      }
    },
    async thucHienLayDsKhieuNaiChuaGiao() {
      try {
        this.loading(true);
        await this.layDsKhieuNaiChuaGiao({
          vhuonggiao_id: this.huongGiaoId,
          vkyhoadon: this.kyCuocHienHanh,
          vma_tb: !this.soMayTimKiem ? "" : this.soMayTimKiem.trim(),
        });
        
      } finally {
        this.loading(false);
      }

      if (!!this.soMayTimKiem) {
        this.checkAllKhieuNai = this.dsKhieuNai.length > 0;
      }
    },
    async layDsKhieuNaiChuaGiao(data) {
      try {
        let response = await this.$root.context.post(
          `/web-gqkn/GiaoPhieuKhieuNai/gqkn_lay_ds_khieunai_chuagiao`,
          data
        );

        if (response.error_code === "BSS-00000000" && response.data != null) {
          response.data.forEach((item, index) => {
            item.KEY = index + 1;
            item.NGAY_TN_HT = this.ddmmyyyyhhmiss(item.NGAY_TN);
          });
          this.dsKhieuNai = response.data;
          return;
        }

        throw { response: { data: response } };
      } catch (err) {
        this.dsKhieuNai = [];
      }
    },
    async layKyCuocHienHanh() {
      try {
        let response = await this.$root.context.get(
          `/web-thuno/api/thu-no/common/ky-cuoc-hien-hanh`
        );

        if (response.error_code === "BSS-00000000" && response.data != null) {
          this.kyCuocHienHanh = response.data[0].KYCUOC;
          return;
        }

        throw { response: { data: response } };
      } catch (err) {
        this.kyCuocHienHanh = null;
      }
    },
    async layDsTrangThai() {
      try {
        let response = await this.$root.context.get(
          `/web-gqkn/GiaoPhieuKhieuNai/gqkn_lay_ds_trangthai_giaophieu`
        );

        if (response.error_code === "BSS-00000000" && response.data != null) {
          this.dsTrangThai = response.data;
          return;
        }

        throw { response: { data: response } };
      } catch (err) {
        this.dsTrangThai = [];
      }
    },
    async layDsHuongGiao() {
      try {
        let response = await this.$root.context.get(
          `/web-gqkn/GiaoPhieuKhieuNai/gqkn_lay_ds_huong_giao`
        );

        if (response.error_code === "BSS-00000000" && response.data != null) {
          this.dsHuongGiao = response.data;
          return;
        }

        throw { response: { data: response } };
      } catch (err) {
        this.dsHuongGiao = [];
      }
    },
    async layDsDonViXuLy(huongGiaoId) {
      try {
        let response = await this.$root.context.get(
          `/web-gqkn/GiaoPhieuKhieuNai/gqkn_lay_ds_donvi_xuly?huonggiao_id=${huongGiaoId}`
        );

        if (response.error_code === "BSS-00000000" && response.data != null) {
          this.dsDonViXuLy = response.data;
          return;
        }

        throw { response: { data: response } };
      } catch (err) {
        this.dsDonViXuLy = [];
      }
    },
    async layDsThamSoMacDinh() {
      try {
        let response = await this.$root.context.get(
          `/web-gqkn/GiaoPhieuKhieuNai/gqkn_lay_ds_thamso_md`
        );

        if (response.error_code === "BSS-00000000" && response.data != null) {
          this.dsThamSoMacDinh = response.data;
          return;
        }

        throw { response: { data: response } };
      } catch (err) {
        this.dsThamSoMacDinh = [];
      }
    },
    xuLyThongBaoDonVi: async function (khieuNaiID, donvi_id, huongGiaoID) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/xuLyThongBaoDonVi",
        {
          khieuNaiID: khieuNaiID,
          donViID: donvi_id,
          huongGiaoID: huongGiaoID
        }
      );
      return rs.data;
    },
    async layNoiDungTinNhanKhieuNai(data) {
      try {
        let response = await this.$root.context.post(
          `/web-gqkn/GiaoPhieuKhieuNai/gqkn_lay_nd_tinnhan_khieunai`,
          data
        );

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data;
        }

        throw { response: { data: response } };
      } catch (err) {
        return null;
      }
    },
    async laySdtNhanTin(data) {
      try {
        let response = await this.$root.context.post(
          `/web-gqkn/GiaoPhieuKhieuNai/gqkn_lay_sodt_nhantin_kn`,
          data
        );
        //let response = await this.$root.context.post(`/web-gqkn/xu-ly-khieu-nai/gqkn_lay_sodt_nhantin_kn_v3`, data)

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data;
        }

        throw { response: { data: response } };
      } catch (err) {
        return null;
      }
    },
    async capNhatDonVi(data) {
      try {
        let response = await this.$root.context.post(
          `/web-gqkn/GiaoPhieuKhieuNai/gqkn_capnhat_donvinhan_xlkn`,
          data
        );

        if (response.error_code !== "BSS-00000000") {
          throw { response: { data: response } };
        }
      } catch (err) {
        let responseData = err.response.data;
        let message = [];

        if (responseData.message) {
          message.push(responseData.message);
        }

        if (responseData.message_detail) {
          message.push(responseData.message_detail);
        }

        if (responseData.data) {
          let detailMessage = Object.getOwnPropertyNames(responseData.data).map(
            (item) => responseData.data[item]
          );
          message.push([...new Set(detailMessage)].join("\n"));
        }

        throw message.join("\n");
      }
    },
    async giaoPhieuKhieuNai(data) {
      try {
        let response = await this.$root.context.post(
          `/web-gqkn/GiaoPhieuKhieuNai/gqkn_giaophieu_khieunai`,
          data
        );

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data;
        }

        throw { response: { data: response } };
      } catch (err) {
        let responseData = err.response.data;
        let message = [];

        if (responseData.message) {
          message.push(responseData.message);
        }

        if (responseData.message_detail) {
          message.push(responseData.message_detail);
        }

        if (responseData.data) {
          let detailMessage = Object.getOwnPropertyNames(responseData.data).map(
            (item) => responseData.data[item]
          );
          message.push([...new Set(detailMessage)].join("\n"));
        }

        throw message.join("\n");
      }
    },
    thongBaoGiaoViecNhanVienTuDong: async function (data) {
      let rs = await this.$root.context.post(
        "/web-gqkn/GiaoPhieuKhieuNai/thongbao_giaoviec_nv_phkh",
        data
      );
      return rs.data;
    },
    async giaoViecNhanVienTuDong(data) {
      try {
        let response = await this.$root.context.post(
          `/web-gqkn/GiaoPhieuKhieuNai/gqkn_tudong_giaoviec_nv_phkh`,
          data
        );

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data;
        }

        throw { response: { data: response } };
      } catch (err) {
        let responseData = err.response.data;
        let message = [];

        if (responseData.message) {
          message.push(responseData.message);
        }

        if (responseData.message_detail) {
          message.push(responseData.message_detail);
        }

        if (responseData.data) {
          let detailMessage = Object.getOwnPropertyNames(responseData.data).map(
            (item) => responseData.data[item]
          );
          message.push([...new Set(detailMessage)].join("\n"));
        }

        throw message.join("\n");
      }
    },
    async capNhatKhdbTheoDsknId(data) {
      try {
        let response = await this.$root.context.post(
          `/web-gqkn/GiaoPhieuKhieuNai/gqkn_capnhat_khdb_theo_dskn_id`,
          data
        );

        if (response.error_code !== "BSS-00000000") {
          throw { response: { data: response } };
        }
      } catch (err) {
        let responseData = err.response.data;
        let message = [];

        if (responseData.message) {
          message.push(responseData.message);
        }

        if (responseData.message_detail) {
          message.push(responseData.message_detail);
        }

        if (responseData.data) {
          let detailMessage = Object.getOwnPropertyNames(responseData.data).map(
            (item) => responseData.data[item]
          );
          message.push([...new Set(detailMessage)].join("\n"));
        }

        throw message.join("\n");
      }
    },
    async huyGiaoPhieuKhieuNai(data) {
      try {
        let response = await this.$root.context.post(
          `/web-gqkn/GiaoPhieuKhieuNai/gqkn_huyphieu_khieunai`,
          data
        );

        if (response.error_code !== "BSS-00000000") {
          throw { response: { data: response } };
        }
      } catch (err) {
        let responseData = err.response.data;
        let message = [];

        if (responseData.message) {
          message.push(responseData.message);
        }

        if (responseData.message_detail) {
          message.push(responseData.message_detail);
        }

        if (responseData.data) {
          let detailMessage = Object.getOwnPropertyNames(responseData.data).map(
            (item) => responseData.data[item]
          );
          message.push([...new Set(detailMessage)].join("\n"));
        }

        throw message.join("\n");
      }
    },
    async huyGiaoPhieuKhieuNai(data) {
      try {
        let response = await this.$root.context.post(
          `/web-gqkn/GiaoPhieuKhieuNai/gqkn_huyphieu_khieunai`,
          data
        );

        if (response.error_code !== "BSS-00000000") {
          throw { response: { data: response } };
        }
      } catch (err) {
        let responseData = err.response.data;
        let message = [];

        if (responseData.message) {
          message.push(responseData.message);
        }

        if (responseData.message_detail) {
          message.push(responseData.message_detail);
        }

        if (responseData.data) {
          let detailMessage = Object.getOwnPropertyNames(responseData.data).map(
            (item) => responseData.data[item]
          );
          message.push([...new Set(detailMessage)].join("\n"));
        }

        throw message.join("\n");
      }
    },
    // async sendSms(soDt, noiDung) {
    //   let data = {
    //     content: noiDung,
    //     msisdn: soDt,
    //     mt_type: "reply",
    //   };

    //   try {
    //     await this.$root.context.post("/web-cabman/traphieukhieunai/off-send-sms", data);
    //   } catch (err) {}
    // },
    getDateElements(date) {
      date = new Date(date);

      let day = date.getDate();
      day = day > 9 ? day : "0" + day;

      let month = date.getMonth() + 1;
      month = month > 9 ? month : "0" + month;

      let hour = date.getHours();
      hour = hour > 9 ? hour : "0" + hour;

      let min = date.getMinutes();
      min = min > 9 ? min : "0" + min;

      let second = date.getSeconds();
      second = second > 9 ? second : "0" + second;

      return {
        y: date.getFullYear(),
        M: month,
        d: day,
        h: hour,
        m: min,
        s: second,
      };
    },
    yyyymmdd(date) {
      if (!date) {
        return "";
      }

      date = this.getDateElements(date);
      return `${date.y}/${date.M}/${date.d}`;
    },
    ddmmyyyy(date) {
      if (!date) {
        return "";
      }

      date = this.getDateElements(date);
      return `${date.d}/${date.M}/${date.y}`;
    },
    ddmmyyyyhhmi(date) {
      if (!date) {
        return "";
      }

      date = this.getDateElements(date);
      return `${date.d}/${date.M}/${date.y} ${date.h}:${date.m}`;
    },
    ddmmyyyyhhmiss(date) {
      if (!date) {
        return "";
      }

      date = this.getDateElements(date);
      return `${date.d}/${date.M}/${date.y} ${date.h}:${date.m}:${date.s}`;
    },
    ddmmyyyyToDate(dateStr) {
      if (!dateStr) {
        return null;
      }

      let dateElement = dateStr.split("/");
      let day = parseInt(dateStr[0]);
      let month = parseInt(dateStr[1]) - 1;
      let yyyy = parseInt(dateStr[2]);
      return new Date(yyyy, month, day);
    },
  },
};
</script>
<style>
.giaophieu-khieunai
  .select2-container
  .select2-selection--single
  .select2-selection__rendered {
  white-space: initial;
}

.giaophieu-khieunai
  .e-grid
  div.e-gridheader
  div.e-headercontent
  thead
  tr.e-columnheader
  th[aria-colindex="1"],
.giaophieu-khieunai
  .e-grid
  div.e-gridheader
  div.e-headercontent
  thead
  tr.e-columnheader
  th[aria-colindex="1"]
  > div {
  padding: 0 !important;
}

.giaophieu-khieunai .disable-btn:hover {
  background-color: white !important;
}

.giaophieu-khieunai .disable-btn a {
  color: #6c757d !important;
}

.giaophieu-khieunai .disable-btn a:hover {
  cursor: default;
  color: #6c757d;
}
.e-grid td.e-cellselectionbackground {
  background-color: inherit;
}
.value .select2-container--default .select2-selection--single {
  overflow: hidden;
}
</style>
