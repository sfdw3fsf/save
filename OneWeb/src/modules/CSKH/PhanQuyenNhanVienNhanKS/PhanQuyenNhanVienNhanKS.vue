<template src="./PhanQuyenNhanVienNhanKS.html"></template>
<style src="./PhanQuyenNhanVienNhanKS.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import PhanQuyenNhanVienNhanKSApi from "./../api/PhanQuyenNhanVienNhanKSApi";
import { mapActions, mapState, mapGetters } from "vuex";
import LoaiKhachHang from "./popup/LoaiKhachHang.vue";
import NVDonVi from "./popup/NVDonVi.vue";
import CSKHGrid from "../utils/CSKHGrid"
export default {
  name: "PhanQuyenNhanVienNhanKS",
  components: {
    breadcrumb,
    appLoaiKhachHang: LoaiKhachHang,
    appNVDonVi: NVDonVi,
    CSKHGrid
  },
  async created() {
    try {
      this.$root.showLoading(true);
      this.clearData();
      await this.getCayDonViData();


    } catch (e) {
      console.log(e);
    } finally {
      this.$root.showLoading(false);
    }
  },
  async mounted() {
    this.loading(true);
    try {
      await this.getDsDichVu();
      if (this.dsDichVuGetter && this.dsDichVuGetter.length > 0) {
        this.dichVuSelected = this.dsDichVuGetter[0].value;
      }
      this.$nextTick(() => console.log('aaaaaaaaaaa'))
    } catch (e) {
    } finally {
      this.loading(false);
    }
  },
  data() {
    return {
      header: {
        title: "PHÂN QUYỀN NHÂN VIÊN KHẢO SÁT",
        list: [
          //   { name: "Trang chủ", link: { name: "Ui.cards" } },
          {
            name: "Chăm sóc khách hàng",
            link: { name: "Ui.buttons" }
          },
          {
            name: "Phân quyền nhân viên nhận KS",
            link: { name: "Ui.buttons" }
          }
        ]
      },
      fields: "",
      fieldThongTinNhanVien: [
        {
          fieldName: "STT",
          headerText: "STT",
          width:"70px",
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: "MA_NV",
          headerText: "Mã nhân viên",
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: "TEN_NV",
          headerText: "Tên nhân viên",
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: "MA_DV",
          headerText: "Mã đơn vị",
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: "CHUCDANH",
          headerText: "Chức danh",
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: "VITRI",
          headerText: "Vị trí",
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: "SO_DT",
          headerText: "Điện thoại",
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: "DIACHI_NV",
          headerText: "Địa chỉ",
          allowFiltering: true,
          allowSorting: true
        }
      ],
      fieldLoaiHinhThueBaoKSDaGan: [ { fieldName: "LOAIHINH_TB", headerText: "Loại hình đã gán", allowFiltering: true, allowSorting: true } ],
      fieldLoaiHinhThueBaoKSChuaGan: [ { fieldName: "LOAIHINH_TB", headerText: "Loại hình chưa gán", allowFiltering: true, allowSorting: true } ],
      fieldHopDongThueBaoKSDaGan: [ { fieldName: "TEN_LOAIHD", headerText: "Loại HĐ đã gán", allowFiltering: true, allowSorting: true } ],
      fieldHopDongThueBaoKSChuaGan: [ { fieldName: "TEN_LOAIHD", headerText: "Loại HĐ chưa gán", allowFiltering: true, allowSorting: true } ],
      fieldPhanLoaiKhachHangKSDaGan: [ { fieldName: "TEN_PLKH", headerText: "Loại KH đã gán", allowFiltering: true, allowSorting: true } ],
      fieldPhanLoaiKhachHangKSChuaGan: [ { fieldName: "TEN_PLKH", headerText: "Loại KH chưa gán", allowFiltering: true, allowSorting: true } ],
      dichVuSelected: "",
      nhanVienSelected: null,
      loaiGrid: {
        DICH_VU: 1,
        HOP_DONG: 2,
        KHACH_HANG: 3
      },
      gridDichVuDaGanSelectedValues: [],
      gridDichVuChuaGanSelectedValues: [],
      gridHopDongDaGanSelectedValues: [],
      gridHopDongChuaGanSelectedValues: [],
      gridKhachHangDaGanSelectedValues: [],
      gridKhachHangChuaGanSelectedValues: [],
      thiCongChecked: false,
      baoHongChecked: false,
      tenDonViSearch: "",
      currentFindIndex: 0,
      isLoading: false,
      dgvLoaiHinhThueBaoDaGan: {
        columns: [ { fieldName: "LOAIHINH_TB", headerText: "Loại hình đã gán", allowFiltering: true, allowSorting: true } ],
        dataSource: [],
        selectedRows: [],
      },
      dgvLoaiHinhThueBaoChuaGan: {
        columns: [ { fieldName: "LOAIHINH_TB", headerText: "Loại hình chưa gán", allowFiltering: true, allowSorting: true } ],
        dataSource: [],
        selectedRows: [],
      },
      dgvHopDongThueBaoDaGan: {
        columns: [ { fieldName: "TEN_LOAIHD", headerText: "Loại HĐ đã gán", allowFiltering: true, allowSorting: true } ],
        dataSource: [],
        selectedRows: [],
      },
      dgvHopDongThueBaoChuaGan: {
        columns:[ { fieldName: "TEN_LOAIHD", headerText: "Loại HĐ chưa gán", allowFiltering: true, allowSorting: true } ],
        dataSource: [],
        selectedRows: [],
      },
      dgvPhanLoaiKhachHangDaGan: {
        columns: [ { fieldName: "TEN_PLKH", headerText: "Loại KH đã gán", allowFiltering: true, allowSorting: true } ],
        dataSource: [],
        selectedRows: [],
      },
      dgvPhanLoaiKhachHangChuaGan: {
        columns: [ { fieldName: "TEN_PLKH", headerText: "Loại KH chưa gán", allowFiltering: true, allowSorting: true } ],
        dataSource: [],
        selectedRows: [],
      },
      selectedNodes: [],
    };
  },
  computed: {
    ...mapState("cskhCommon", ["cayDonVi"]),
    ...mapGetters("cskhCommon", ["dsDichVuGetter"]),
    ...mapGetters("phanquyennhanviennhankhaosat", [
      "dsNhanVienTheoDonViGetter",
      "dsLoaiHinhThueBaoKSDaGanGetter",
      "dsLoaiHinhThueBaoKSChuaGanGetter",
      "dsHopDongThueBaoKSDaGanGetter",
      "dsHopDongThueBaoKSChuaGanGetter",
      "dsPhanLoaiKhachHangKSDaGanGetter",
      "dsPhanLoaiKhachHangKSChuaGanGetter"
    ]),
    nhanVienId() {
      return this.nhanVienSelected ? this.nhanVienSelected.NHANVIEN_ID : -1;
    },
    treeDonViFields() {
      return {
        dataSource: this.cayDonVi,
        id: "DONVI_ID",
        text: "TEN_DV",
        parentID: "DONVI_CHA_ID",
        hasChildren: "hasChild",
        selected: 'selected',
      }
    },
  },
  methods: {
    ...mapActions("cskhCommon", ["getDsDichVu", "getCayDonViData"]),
    ...mapActions("phanquyennhanviennhankhaosat", [
      "getDsNhanVienTheoDonVi",
      "getDsLoaiHinhThueBaoKSChuaGan",
      "getDsLoaiHinhThueBaoKSDaGan",
      "getDsHopDongThueBaoKSChuaGan",
      "getDsHopDongThueBaoKSDaGan",
      "getDsPhanLoaiKhachHangKSChuaGan",
      "getDsPhanLoaiKhachHangKSDaGan",
      "setDanhSachGanChuaGan",
      "clearData",
      "clearGrid"
    ]),
    async onDonViSelect (data) {
      console.log("2");
      if (this.isLoading) return;
      let treeObj = document.getElementById("treeview").ej2_instances[0];
      let donViId = treeObj.selectedNodes[0];
      console.log('donViIdd', donViId)
      this.loading(true);
      this.isLoading = true;
      this.clearGrid();
      await this.getDsNhanVienTheoDonVi({
        pageSize: 100000,
        pageNo: 1,
        donViId: donViId
      })
        .then(() => {
          this.isLoading = false;
          this.loading(false);
          if (this.dsNhanVienTheoDonViGetter.length > 0) {
            this.$refs.gridNhanVien.setCurrentSelectedRow(0);
          }
        })
        .catch(() => {
          this.loading(false);
          this.isLoading = false;
        });
    },
    async onDichVuChange() {
      let dataLoaiHinh = {
        nhanVienId: this.nhanVienId,
        dichVuVtId: this.dichVuSelected,
        pageSize: 1000,
        pageNo: 1
      };
      let dataHopDong = {
        nhanVienId: this.nhanVienId,
        pageSize: 1000,
        pageNo: 1
      };
      this.clearGrid();
      this.loading(true);
      await Promise.all([
        this.getDsLoaiHinhThueBao(true),
        this.getDsLoaiHinhThueBao(false),
        this.getDsHopDongThueBao(true),
        this.getDsHopDongThueBao(false),
        this.getDsPhanLoaiKhachHang(true),
        this.getDsPhanLoaiKhachHang(false),
        // this.getDsLoaiHinhThueBaoKSChuaGan(dataLoaiHinh),
        // this.getDsLoaiHinhThueBaoKSDaGan(dataLoaiHinh),
        // this.getDsHopDongThueBaoKSChuaGan(dataHopDong),
        // this.getDsHopDongThueBaoKSDaGan(dataHopDong),
        // this.getDsPhanLoaiKhachHangKSChuaGan(dataHopDong),
        // this.getDsPhanLoaiKhachHangKSDaGan(dataHopDong)
      ])
        .then(() => {
          this.loading(false);
        })
        .catch(() => {
          this.loading(false);
        });
    },
    async nhanVienRowClicked(dataItem) {
        this.thiCongChecked = false
        this.baoHongChecked = false
        this.nhanVienSelected = dataItem;
        this.onDichVuChange();
        this.thiCongChecked = false
        this.baoHongChecked = false
        if (dataItem)
        {
            try
            {
                    this.$root.showLoading(true)
                    let response = await this.$root.context.get("/web-cskh/api/phan-quyen-nvks/lay-quyen-ks-theo-nv", {nhanVienId: dataItem.NHANVIEN_ID})
                    if (response.error_code == "BSS-00000000") {
                        if (response.data && response.data.data && response.data.data.length > 0)
                        {
                            let giaTri = response.data.data[0].GIATRI
                            this.thiCongChecked = giaTri.includes("1")
                            this.baoHongChecked = giaTri.includes("2")
                        }
                        //if (message.)
                    }
            }
            catch (e)
            {

            }
            finally
            {
                this.$root.showLoading(false)
            }
        }
    },
    //setGan = 1 => set gán | setGan = 2 => set chưa gán
    thayDoiTrangThaiGan(loaiGrid, setGan) {
      let dsChuaGanSelected = [];
      let dsDaGanSelected = [];
      let data = {};
      switch (loaiGrid) {
        case this.loaiGrid.DICH_VU:
          dsChuaGanSelected = this.getListItem(
            this.dsLoaiHinhThueBaoKSChuaGanGetter,
            "LOAITB_ID",
            this.gridDichVuChuaGanSelectedValues
          );
          dsDaGanSelected = this.getListItem(
            this.dsLoaiHinhThueBaoKSDaGanGetter,
            "LOAITB_ID",
            this.gridDichVuDaGanSelectedValues
          );
          data = {
            typeDanhSach: "dsLoaiHinhThueBaoKSDaGan",
            field: "LOAITB_ID",
            setGan: setGan,
            itemArr: setGan == 1 ? dsChuaGanSelected : dsDaGanSelected
          };
          if (setGan == 1)
            this.$refs.gridLoaiHinhThueBaoKSChuaGan.selectAllItem = false;
          else this.$refs.gridLoaiHinhThueBaoKSDaGan.selectAllItem = false;

          break;
        case this.loaiGrid.HOP_DONG:
          dsChuaGanSelected = this.getListItem(
            this.dsHopDongThueBaoKSChuaGanGetter,
            "LOAIHD_ID",
            this.gridHopDongChuaGanSelectedValues
          );
          dsDaGanSelected = this.getListItem(
            this.dsHopDongThueBaoKSDaGanGetter,
            "LOAIHD_ID",
            this.gridHopDongDaGanSelectedValues
          );
          data = {
            typeDanhSach: "dsHopDongThueBaoKSDaGan",
            field: "LOAIHD_ID",
            setGan: setGan,
            itemArr: setGan == 1 ? dsChuaGanSelected : dsDaGanSelected
          };
          if (setGan == 1)
            this.$refs.gridHopDongThueBaoKSChuaGan.selectAllItem = false;
          else this.$refs.gridHopDongThueBaoKSDaGan.selectAllItem = false;
          break;
        case this.loaiGrid.KHACH_HANG:
          dsChuaGanSelected = this.getListItem(
            this.dsPhanLoaiKhachHangKSChuaGanGetter,
            "PHANLOAIKH_ID",
            this.gridKhachHangChuaGanSelectedValues
          );
          dsDaGanSelected = this.getListItem(
            this.dsPhanLoaiKhachHangKSDaGanGetter,
            "PHANLOAIKH_ID",
            this.gridKhachHangDaGanSelectedValues
          );
          data = {
            typeDanhSach: "dsPhanLoaiKhachHangKSDaGan",
            field: "PHANLOAIKH_ID",
            setGan: setGan,
            itemArr: setGan == 1 ? dsChuaGanSelected : dsDaGanSelected
          };
          if (setGan == 1)
            this.$refs.gridPhanLoaiKhachHangKSChuaGan.selectAllItem = false;
          else this.$refs.gridPhanLoaiKhachHangKSDaGan.selectAllItem = false;
          break;
      }
      this.setDanhSachGanChuaGan(data);
    },
    //chuyển từ đã gán sang chưa gán
    onAddChuaGan(loaiGrid) {
      switch (loaiGrid) {
        case this.loaiGrid.DICH_VU:
          let ds = this.$refs.dgvLoaiHinhThueBaoDaGan.grid.getSelectedRecords()

          let indexes = this.$refs.dgvLoaiHinhThueBaoChuaGan.grid.getSelectedRowIndexes()

          this.dgvLoaiHinhThueBaoChuaGan.dataSource = this.dgvLoaiHinhThueBaoChuaGan.dataSource.concat(ds)
          let dsIds = this.dgvLoaiHinhThueBaoChuaGan.dataSource.map(x => x.LOAITB_ID)

          ds.forEach(e => {
            indexes.push(dsIds.indexOf(e.LOAITB_ID))
          })

          // this.$refs.dgvLoaiHinhThueBaoChuaGan.flagSelectedRowIndexes = indexes;
          this.dgvLoaiHinhThueBaoChuaGan.selectedRows = indexes
          this.dgvLoaiHinhThueBaoDaGan.dataSource = this.dgvLoaiHinhThueBaoDaGan.dataSource.filter(x => !ds.map(x => x.LOAITB_ID).includes(x.LOAITB_ID))
          break;
        case this.loaiGrid.HOP_DONG:
          let ds1 = this.$refs.dgvHopDongThueBaoDaGan.grid.getSelectedRecords()
          // this.dgvHopDongThueBaoChuaGan.dataSource = this.dgvHopDongThueBaoChuaGan.dataSource.concat(ds1)

          let indexes1 = this.$refs.dgvHopDongThueBaoChuaGan.grid.getSelectedRowIndexes()
          this.dgvHopDongThueBaoChuaGan.dataSource = this.dgvHopDongThueBaoChuaGan.dataSource.concat(ds1)
          let dsIds1 = this.dgvHopDongThueBaoChuaGan.dataSource.map(x => x.LOAIHD_ID)

          ds1.forEach(e => {
            indexes1.push(dsIds1.indexOf(e.LOAIHD_ID))
          })
          // this.$refs.dgvHopDongThueBaoChuaGan.grid.selectRows(indexes1);//  = indexes1

          this.dgvHopDongThueBaoChuaGan.selectedRows = indexes1
          this.dgvHopDongThueBaoDaGan.dataSource = this.dgvHopDongThueBaoDaGan.dataSource.filter(x => !ds1.map(x => x.LOAIHD_ID).includes(x.LOAIHD_ID))
          break;
        case this.loaiGrid.KHACH_HANG:
          let ds2 = this.$refs.dgvPhanLoaiKhachHangDaGan.grid.getSelectedRecords()
          // this.dgvPhanLoaiKhachHangChuaGan.dataSource = this.dgvPhanLoaiKhachHangChuaGan.dataSource.concat(ds2)

          let indexes2 = this.$refs.dgvPhanLoaiKhachHangChuaGan.grid.getSelectedRowIndexes()
          this.dgvPhanLoaiKhachHangChuaGan.dataSource = this.dgvPhanLoaiKhachHangChuaGan.dataSource.concat(ds2)
          let dsIds2 = this.dgvPhanLoaiKhachHangChuaGan.dataSource.map(x => x.PHANLOAIKH_ID)

          ds2.forEach(e => {
            indexes2.push(dsIds2.indexOf(e.PHANLOAIKH_ID))
          })
          // this.$refs.dgvPhanLoaiKhachHangChuaGan.grid.selectRows(indexes2);//  = indexes2

          this.dgvPhanLoaiKhachHangChuaGan.selectedRows = indexes2
          this.dgvPhanLoaiKhachHangDaGan.dataSource = this.dgvPhanLoaiKhachHangDaGan.dataSource.filter(x => !ds2.map(x => x.PHANLOAIKH_ID).includes(x.PHANLOAIKH_ID))
          break;
      }
    },
    onAddDaGan(loaiGrid) {
      switch (loaiGrid) {
        case this.loaiGrid.DICH_VU:
          let ds = this.$refs.dgvLoaiHinhThueBaoChuaGan.grid.getSelectedRecords()
          // this.dgvLoaiHinhThueBaoDaGan.dataSource = this.dgvLoaiHinhThueBaoDaGan.dataSource.concat(ds)

          let indexes = this.$refs.dgvLoaiHinhThueBaoDaGan.grid.getSelectedRowIndexes()

          this.dgvLoaiHinhThueBaoDaGan.dataSource = this.dgvLoaiHinhThueBaoDaGan.dataSource.concat(ds)
          let dsIds = this.dgvLoaiHinhThueBaoDaGan.dataSource.map(x => x.LOAITB_ID)

          ds.forEach(e => {
            indexes.push(dsIds.indexOf(e.LOAITB_ID))
          })

          // this.$nextTick(() => this.$refs.dgvLoaiHinhThueBaoDaGan.grid.selectRows(indexes))
          // this.$refs.dgvLoaiHinhThueBaoDaGan.flagSelectedRowIndexes = indexes;
          this.dgvLoaiHinhThueBaoDaGan.selectedRows = indexes
          this.dgvLoaiHinhThueBaoChuaGan.dataSource = this.dgvLoaiHinhThueBaoChuaGan.dataSource.filter(x => !ds.map(x => x.LOAITB_ID).includes(x.LOAITB_ID))
          break;
        case this.loaiGrid.HOP_DONG:
          let ds1 = this.$refs.dgvHopDongThueBaoChuaGan.grid.getSelectedRecords()
          // this.dgvHopDongThueBaoDaGan.dataSource = this.dgvHopDongThueBaoDaGan.dataSource.concat(ds1)

          let indexes1 = this.$refs.dgvHopDongThueBaoDaGan.grid.getSelectedRowIndexes()
          this.dgvHopDongThueBaoDaGan.dataSource = this.dgvHopDongThueBaoDaGan.dataSource.concat(ds1)
          let dsIds1 = this.dgvHopDongThueBaoDaGan.dataSource.map(x => x.LOAIHD_ID)

          ds1.forEach(e => {
            indexes1.push(dsIds1.indexOf(e.LOAIHD_ID))
          })
          // this.$refs.dgvHopDongThueBaoDaGan.grid.selectRows(indexes1);//  = indexes1
          this.dgvHopDongThueBaoDaGan.selectedRows = indexes1

          this.dgvHopDongThueBaoChuaGan.dataSource = this.dgvHopDongThueBaoChuaGan.dataSource.filter(x => !ds1.map(x => x.LOAIHD_ID).includes(x.LOAIHD_ID))
          break;
        case this.loaiGrid.KHACH_HANG:
          let ds2 = this.$refs.dgvPhanLoaiKhachHangChuaGan.grid.getSelectedRecords()
          // this.dgvPhanLoaiKhachHangDaGan.dataSource = this.dgvPhanLoaiKhachHangDaGan.dataSource.concat(ds2)

          let indexes2 = this.$refs.dgvPhanLoaiKhachHangDaGan.grid.getSelectedRowIndexes()
          this.dgvPhanLoaiKhachHangDaGan.dataSource = this.dgvPhanLoaiKhachHangDaGan.dataSource.concat(ds2)
          let dsIds2 = this.dgvPhanLoaiKhachHangDaGan.dataSource.map(x => x.PHANLOAIKH_ID)

          ds2.forEach(e => {
            indexes2.push(dsIds2.indexOf(e.PHANLOAIKH_ID))
          })
          // this.$refs.dgvPhanLoaiKhachHangDaGan.grid.selectRows(indexes2);// flagSelectedRowIndexes = indexes2
          this.dgvPhanLoaiKhachHangDaGan.selectedRows = indexes2

          this.dgvPhanLoaiKhachHangChuaGan.dataSource = this.dgvPhanLoaiKhachHangChuaGan.dataSource.filter(x => !ds2.map(x => x.PHANLOAIKH_ID).includes(x.PHANLOAIKH_ID))
          break;
      }
    },
    getListItem(arr, id, ids) {
      return arr.filter(x => ids.includes(x[id]));
    },
    async capNhap() {
      if (this.nhanVienSelected) {
        // let dsLoaiTbId = this.dsLoaiHinhThueBaoKSDaGanGetter.map(x => x.LOAITB_ID) .join()
        // let dsLoaiHdId = this.dsHopDongThueBaoKSDaGanGetter.map(x => x.LOAIHD_ID) .join()
        // let dsPhanLoaiKhId = this.dsPhanLoaiKhachHangKSDaGanGetter.map(x => x.PHANLOAIKH_ID) .join()
        // if (!dsLoaiTbId && !dsLoaiHdId && !dsPhanLoaiKhId) {
        //   this.$root.toastError("Không có thông tin để cập nhập!")
        //   return
        // }
        let dsLoaiTbId = this.dgvLoaiHinhThueBaoDaGan.dataSource.map(x => x.LOAITB_ID) .join()
        let dsLoaiHdId = this.dgvHopDongThueBaoDaGan.dataSource.map(x => x.LOAIHD_ID) .join()
        let dsPhanLoaiKhId = this.dgvPhanLoaiKhachHangDaGan.dataSource.map(x => x.PHANLOAIKH_ID) .join()
        let data = {
          nhanVienId: this.nhanVienSelected.NHANVIEN_ID,
          dsLoaiTbId: dsLoaiTbId,
          dsLoaiHdId: dsLoaiHdId,
          dsPhanLoaiKhId: dsPhanLoaiKhId,
          thiCong: this.thiCongChecked ? 1 : 0,
          baoHong: this.baoHongChecked ? 1 : 0
        };
        this.loading(true);
        await PhanQuyenNhanVienNhanKSApi.capNhapPhanQuyenNhanVien(
          this.axios,
          data
        )
          .then(response => {
            this.loading(false);
            if (response.data.error_code === "BSS-00000000") {
              this.$root.toastSuccess("Cập nhật thành công !");
            } else {
              this.$root.toastError(response.data.message);
            }
          })
          .catch(reject => {
            this.loading(false);
            if (
              reject.response &&
              reject.response.data &&
              reject.response.data.message
            )
              this.$root.toastError(reject.response.data.message);
            else this.$root.toastError("Có lỗi xảy ra!");
          });
      } else {
        this.$root.toastError("Bạn chưa chọn nhân viên!");
      }
    },
    onTenDonViSearch(event) {
      if (!this.isLoading && event.keyCode == 13) {
        if (this.tenDonViSearch) {
          let filterDonVi = this.treeDonViFields.dataSource
            .filter(x =>
              x.TEN_DV.toLowerCase().includes(
                this.tenDonViSearch.trim().toLowerCase()
              )
            )
            .sort((a, b) => a.TEN_DV.localeCompare(b.TEN_DV));

          if (filterDonVi.length == 0) {
            this.$root.toastError('Không tìm thấy đơn vị phù hợp')
            return
          }

          let treeObj = document.getElementById("treeview").ej2_instances[0];

          if (this.currentFindIndex >= filterDonVi.length - 1)
            this.currentFindIndex = 0;

          this.selectedNodes = []
          treeObj.selectedNodes = [];
          if (filterDonVi[this.currentFindIndex]) {
            let donViId = filterDonVi[this.currentFindIndex].DONVI_ID;
            let dsDonViChaId = this.findParentOnTree(filterDonVi[this.currentFindIndex].DONVI_CHA_ID)
            console.log('dsDonViChaId', dsDonViChaId)

            this.$refs.treeObj.expandAll(dsDonViChaId)
            this.selectedNodes.push("" + donViId);
            this.$refs["treeObj"].ensureVisible("" + donViId);
            setTimeout(function() {
              // document.getElementById("treeview").ej2_instances[0].getElement("" + donViId).scrollIntoView({ behavior: "smooth" })
              this.scrollToTop
            }, 100)
            this.currentFindIndex++
          }

        }
      }
    },
    findParentOnTree(nodeParentId) {
      var dsParents = this.treeDonViFields.dataSource.filter(x => x.DONVI_ID == nodeParentId)
      var dsParentsId = []
      if (dsParents != null && dsParents.length > 0) {
        dsParentsId.push('' + dsParents[0].DONVI_ID)
        dsParentsId = dsParentsId.concat(this.findParentOnTree(dsParents[0].DONVI_CHA_ID))
      }
      return dsParentsId;
    },
    onTenDonViChange() {
      this.currentFindIndex = 0;
    },
    scrollToTop() {
      document.getElementById("txtTenDonViSearch").scrollIntoView({ behavior: "smooth" })
      // this.$refs.txtTenDonViSearch.scrollIntoView({ behavior: "smooth" })
    },
    async getDsLoaiHinhThueBao(daGan) {
      try {
        let dataLoaiHinh = {
          nhanVienId: this.nhanVienId,
          dichVuVtId: this.dichVuSelected,
          kieu: daGan ? 2 : 1,
          pageSize: 1000,
          pageNo: 1
        };

        let response = await this.$root.context.get("/web-cskh/api/phan-quyen-nvks/loai-thue-bao", dataLoaiHinh)
        if (daGan) {
          this.dgvLoaiHinhThueBaoDaGan.dataSource = []
          if (response.error_code == "BSS-00000000") {
            this.dgvLoaiHinhThueBaoDaGan.dataSource = response.data.data || []
            this.dgvLoaiHinhThueBaoDaGan.dataSource = this.dgvLoaiHinhThueBaoDaGan.dataSource.sort((a, b) => a.LOAIHINH_TB.localeCompare(b.LOAIHINH_TB));
          }
        }
        else {
          this.dgvLoaiHinhThueBaoChuaGan.dataSource = []
          if (response.error_code == "BSS-00000000") {
            this.dgvLoaiHinhThueBaoChuaGan.dataSource = response.data.data || []
            this.dgvLoaiHinhThueBaoChuaGan.dataSource = this.dgvLoaiHinhThueBaoChuaGan.dataSource.sort((a, b) => a.LOAIHINH_TB.localeCompare(b.LOAIHINH_TB));
          }
        }
      }
      catch (e) {
        console.log(e)
      }
    },
    async getDsHopDongThueBao(daGan) {
      try {
        let dataLoaiHinh = {
          nhanVienId: this.nhanVienId,
          kieu: daGan ? 2 : 1,
          pageSize: 1000,
          pageNo: 1
        };

        let response = await this.$root.context.get("/web-cskh/api/phan-quyen-nvks/loai-hop-dong", dataLoaiHinh)
        if (daGan) {
          this.dgvHopDongThueBaoDaGan.dataSource = []
          if (response.error_code == "BSS-00000000") {
            this.dgvHopDongThueBaoDaGan.dataSource = response.data.data || []
            this.dgvHopDongThueBaoDaGan.dataSource = this.dgvHopDongThueBaoDaGan.dataSource.sort((a, b) => a.TEN_LOAIHD.localeCompare(b.TEN_LOAIHD));
          }
        }
        else {
          this.dgvHopDongThueBaoChuaGan.dataSource = []
          if (response.error_code == "BSS-00000000") {
            this.dgvHopDongThueBaoChuaGan.dataSource = response.data.data || []
            this.dgvHopDongThueBaoChuaGan.dataSource = this.dgvHopDongThueBaoChuaGan.dataSource.sort((a, b) => a.TEN_LOAIHD.localeCompare(b.TEN_LOAIHD));
          }
        }
      }
      catch (e) {
        console.log(e)
      }
    },
    async getDsPhanLoaiKhachHang(daGan) {
      try {
        let dataLoaiHinh = {
          nhanVienId: this.nhanVienId,
          kieu: daGan ? 2 : 1,
          pageSize: 1000,
          pageNo: 1
        };

        let response = await this.$root.context.get("/web-cskh/api/phan-quyen-nvks/phan-loai-khach-hang", dataLoaiHinh)
        if (daGan) {
          this.dgvPhanLoaiKhachHangDaGan.dataSource = []
          if (response.error_code == "BSS-00000000") {
            this.dgvPhanLoaiKhachHangDaGan.dataSource = response.data.data || []
            this.dgvPhanLoaiKhachHangDaGan.dataSource = this.dgvPhanLoaiKhachHangDaGan.dataSource.sort((a, b) => a.TEN_PLKH.localeCompare(b.TEN_PLKH));
          }
        }
        else {
          this.dgvPhanLoaiKhachHangChuaGan.dataSource = []
          if (response.error_code == "BSS-00000000") {
            this.dgvPhanLoaiKhachHangChuaGan.dataSource = response.data.data || []
            this.dgvPhanLoaiKhachHangChuaGan.dataSource = this.dgvPhanLoaiKhachHangChuaGan.dataSource.sort((a, b) => a.TEN_PLKH.localeCompare(b.TEN_PLKH));
          }
        }
      }
      catch (e) {
        console.log(e)
      }
    },
    onOpenLoaiKhachHang() {
      if (this.nhanVienId == 0 || this.nhanVienId == -1) {
        this.$root.toastError("Chưa chọn nhân viên")
        return
      }
      this.$refs.frmPhanQuyenNVKS_LoaiKhachHang.nhanVienId = this.nhanVienId
      this.$refs.frmPhanQuyenNVKS_LoaiKhachHang.openDialog()
    },
    onOpenLoaiDonVi() {
      if (this.nhanVienId == 0 || this.nhanVienId == -1) {
        this.$root.toastError("Chưa chọn nhân viên")
        return
      }
      this.$refs.frmPhanQuyenNVKS_LoaiDonVi.nhanVienId = this.nhanVienId
      this.$refs.frmPhanQuyenNVKS_LoaiDonVi.openDialog()
    },
    onGridDataBound(type) {
      switch (type) {
        case 1:
          this.$refs.dgvLoaiHinhThueBaoDaGan.grid.selectRows(this.dgvLoaiHinhThueBaoDaGan.selectedRows)
          this.$refs.dgvLoaiHinhThueBaoDaGan.enableCheckAll();
          this.dgvLoaiHinhThueBaoDaGan.selectedRows = []
          break;
        case 2:
          this.$refs.dgvLoaiHinhThueBaoChuaGan.grid.selectRows(this.dgvLoaiHinhThueBaoChuaGan.selectedRows)
          this.$refs.dgvLoaiHinhThueBaoChuaGan.enableCheckAll();
          this.dgvLoaiHinhThueBaoChuaGan.selectedRows = []
          break;
        case 3:
          this.$refs.dgvHopDongThueBaoDaGan.grid.selectRows(this.dgvHopDongThueBaoDaGan.selectedRows)
          this.$refs.dgvHopDongThueBaoDaGan.enableCheckAll();
          this.dgvHopDongThueBaoDaGan.selectedRows = []
          break;
        case 4:
          this.$refs.dgvHopDongThueBaoChuaGan.grid.selectRows(this.dgvHopDongThueBaoChuaGan.selectedRows)
          this.$refs.dgvHopDongThueBaoChuaGan.enableCheckAll();
          this.dgvHopDongThueBaoChuaGan.selectedRows = []
          break;
        case 5:
          this.$refs.dgvPhanLoaiKhachHangDaGan.grid.selectRows(this.dgvPhanLoaiKhachHangDaGan.selectedRows)
          this.$refs.dgvPhanLoaiKhachHangDaGan.enableCheckAll();
          this.dgvPhanLoaiKhachHangDaGan.selectedRows = []
          break;
        case 6:
          this.$refs.dgvPhanLoaiKhachHangChuaGan.grid.selectRows(this.dgvPhanLoaiKhachHangChuaGan.selectedRows)
          this.$refs.dgvPhanLoaiKhachHangChuaGan.enableCheckAll();
          this.dgvPhanLoaiKhachHangChuaGan.selectedRows = []
          break;
      }


    },
  }
};
</script>
