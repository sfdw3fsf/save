<template src="./QuanLyNhanVienKhaoSat.html"></template>
<style src="./QuanLyNhanVienKhaoSat.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import { mapActions, mapState, mapGetters } from "vuex";
import CSKHGrid from "../utils/CSKHGrid";
import PhanQuyenNhanVienNhanKSApi from "../api/PhanQuyenNhanVienNhanKSApi";
import moment from "moment";
export default {
  components: {
    breadcrumb,
    CSKHGrid,
  },
  data() {
    return {
      API_SUCCESS: "BSS-00000000",
      header: {
        title: "QUẢN LÝ NHÂN VIÊN KHẢO SÁT",
        list: [
          {
            name: "Chăm sóc khách hàng",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Quản lý nhân viên khảo sát",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      fieldThongTinNhanVien: [
        {
          fieldName: "STT",
          headerText: "STT",
          width: "70px",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "MA_NV",
          headerText: "Mã nhân viên",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "TEN_NV",
          headerText: "Tên nhân viên",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "MA_DV",
          headerText: "Mã đơn vị",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "CHUCDANH",
          headerText: "Chức danh",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "VITRI",
          headerText: "Vị trí",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "SO_DT",
          headerText: "Điện thoại",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "DIACHI_NV",
          headerText: "Địa chỉ",
          allowFiltering: true,
          allowSorting: true,
        },
      ],
      fieldsNhanVienDaGan: [
        {
          fieldName: "STT",
          headerText: "STT",
          width: "70px",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "MA_NV",
          headerText: "Mã nhân viên",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "MA_DV",
          headerText: "Mã đơn vị",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "TEN_NV",
          headerText: "Tên đơn vị",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "CHUCDANH",
          headerText: "Chúc danh",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "VITRI",
          headerText: "Vị trí",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "SO_DT",
          headerText: "Điện thoại",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "DIACHI_NV",
          headerText: "Địa chỉ",
          allowFiltering: true,
          allowSorting: true,
        },
      ],
      fieldsLoaiDV: [
        {
          fieldName: "CAP_TN",
          headerText: "Loại ĐV",
          allowFiltering: true,
        },
      ],
      fieldsDVDaGan: [
        // {
        //     fieldName: "DONVI_ID",
        //     headerText: "DONVI_ID",
        //     allowFiltering: true,
        // },
        {
          fieldName: "TEN_DV",
          headerText: "Đơn vị",
          allowFiltering: true,
        },
      ],
      fieldsDVChuaGan: [
        //  {
        //     fieldName: "DONVI_ID",
        //     headerText: "DONVI_ID",
        //     allowFiltering: true,
        // },
        {
          fieldName: "TEN_DV",
          headerText: "Đơn vị",
          allowFiltering: true,
        },
      ],
      isEnabled: {
        tuNgay: false,
        denNgay: false,
      },
      dataSelect: {
        maNguoiDung: "",
        maNV: "",
        tuNgay: new Date(),
        denNgay: new Date(),
      },
      // treeDonViFields: [],
      dsNhanVien: [],
      dsNhanVienDaGan: [],
      dsLoaiDV: [],
      dsChuaGan: [],
      dsDaGan: [],
      gridDonViChuaGanSelectedValues: [],
      gridDonViDaGanSelectedValues: [],
      selectedNodes: [],
      currentFindIndex: 0,
      vdonvi_id: -1,
      vtinnhan_id: -1,
      vnhanvien_id: -1,
      vcaptn_id: -1,
      vnhanvien_dagan_id: -1,
      vma_nv_dagan: -1,
      gma_nv: "",
      tenDonViSearch:""
    };
  },
  computed: {
    treeDonViFields() {
      return {
        dataSource: this.cayDonVi,
        id: "DONVI_ID",
        text: "TEN_DV",
        parentID: "DONVI_CHA_ID",
        hasChildren: "hasChild",
      };
    },
    ...mapState("cskhCommon", ["cayDonVi"]),
    ...mapGetters("phanquyennhanviennhankhaosat", [
      "dsNhanVienTheoDonViGetter",
    ]),
  },
  async created() {
    await this.getCayDonViData();
    // await this.getDsLoaiDonViTheoTn();
  },
  async mounted() {
    await this.napDsLoaiTin();
  },
  methods: {
    ...mapActions("cskhCommon", ["getCayDonViData", "getThamSoMacDinh"]),
    ...mapActions("phanquyennhanviennhankhaosat", ["getDsNhanVienTheoDonVi"]),

    async onDonViSelect(data) {
      if (this.isLoading) return;
      let treeObj = document.getElementById("treeview").ej2_instances[0];
      let donViId = treeObj.selectedNodes[0];
      console.log("donViIdd", donViId);
      this.vdonvi_id = donViId || -1;
      this.loading(true);
      this.isLoading = true;
      // this.clearGrid();
      await this.getDsNhanVienTheoDonVi({
        pageSize: 100000,
        pageNo: 1,
        donViId: this.vdonvi_id,
      })
        .then(() => {
          this.isLoading = false;
          this.loading(false);
          if (this.dsNhanVienTheoDonViGetter.length > 0) {
            this.$refs.gridNhanVien.setCurrentSelectedRow(0);
            if (this.gma_nv) {
              const position =
                this.dsNhanVienTheoDonViGetter.findIndex(
                  (x) => x.MA_NV == this.gma_nv
                ) || 0;
              setTimeout(() => {
                this.$refs.gridNhanVien.setCurrentSelectedRow(position);
              }, 500);
            }
          }
        })
        .catch(() => {
          this.loading(false);
          this.isLoading = false;
        });
    },
    onTenDonViChange() {
      this.currentFindIndex = 0;
    },
    onFilterDonViSearch(data) {
      const donvi_id = data.DONVI_ID;
      this.gma_nv = data.MA_NV;
      if (donvi_id) {
        let filterDonVi = this.treeDonViFields.dataSource.filter(
          (x) => x.DONVI_ID == donvi_id
        );

        if (filterDonVi.length == 0) {
          this.$root.toastError("Không tìm thấy đơn vị phù hợp!");
          return;
        }

        let treeObj = document.getElementById("treeview").ej2_instances[0];

        if (this.currentFindIndex >= filterDonVi.length - 1)
          this.currentFindIndex = 0;
        this.selectedNodes = [];
        treeObj.selectedNodes = [];
        if (filterDonVi[this.currentFindIndex]) {
          let donViId = filterDonVi[this.currentFindIndex].DONVI_ID;
          let dsDonViChaId = this.findParentOnTree(
            filterDonVi[this.currentFindIndex].DONVI_CHA_ID
          );

          this.$refs.treeObj.expandAll(dsDonViChaId);
          this.selectedNodes.push("" + donViId);
          this.$refs["treeObj"].ensureVisible("" + donViId);
          this.currentFindIndex++;
        }
      }
    },
    findParentOnTree(nodeParentId) {
      var dsParents = this.treeDonViFields.dataSource.filter(
        (x) => x.DONVI_ID == nodeParentId
      );
      var dsParentsId = [];
      if (dsParents != null && dsParents.length > 0) {
        dsParentsId.push("" + dsParents[0].DONVI_ID);
        dsParentsId = dsParentsId.concat(
          this.findParentOnTree(dsParents[0].DONVI_CHA_ID)
        );
      }
      return dsParentsId;
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
            // setTimeout(function() {
            //   // document.getElementById("treeview").ej2_instances[0].getElement("" + donViId).scrollIntoView({ behavior: "smooth" })
            //   this.scrollToTop()
            // }, 100)
            this.currentFindIndex++
          }

        }
      }
    },
    // async getDsLoaiDonViTheoTn(){
    //     try {
    //         let data = {
    //             loaitnId : 22
    //         }
    //         this.$root.showLoading(true)
    //         this.dsLoaiDV = []
    //         let rs = await this.$root.context.get("/web-cskh/api/danh-muc/loai-don-vi", data)
    //         if (rs.error_code === this.API_SUCCESS) {
    //             this.dsLoaiDV = rs.data.data
    //         }
    //     }
    //     catch (e) {
    //         console.log(e)
    //     }
    //     finally {
    //         this.$root.showLoading(false)
    //     }
    // },

    async gridNhanVien_selectedRowChanged(dataItem) {
      this.loading(true);
      if (dataItem != null) {
        this.vnhanvien_id = dataItem.NHANVIEN_ID;
        this.napDonViDuLieu();
      } else {
        this.vnhanvien_id = -1;
      }
      this.loading(false);
    },

    async gridLoaiTin_selectedRowChanged(dataItem) {
      this.loading(true);
      if (dataItem != null) {
        this.vtinnhan_id = dataItem.TINNHAN_ID;
        this.vcaptn_id = dataItem.CAPTN_ID;
        this.napDonViDuLieu();
        // let tinNhanIdTemp = 58;
        // this.napDsNhanVienDaGan(tinNhanIdTemp);
        this.napDsNhanVienDaGan(this.vtinnhan_id);
      } else {
        this.vtinnhan_id = -1;
        this.vcaptn_id = -1;
      }
    },

    grvNhanVienDaGan_selectedRowChanged(dataItem) {
      if (dataItem != null) {
        this.vnhanvien_dagan_id = dataItem.NHANVIEN_ID;
        this.vma_nv_dagan = dataItem.MA_NV;
      } else {
        this.vnhanvien_dagan_id = -1;
        this.vma_nv_dagan = -1;
      }
    },

    onAddDaGan() {
      let ds = this.$refs.gridChuaGan.grid.getSelectedRecords();
      // this.dgvLoaiHinhThueBaoDaGan.dataSource = this.dgvLoaiHinhThueBaoDaGan.dataSource.concat(ds)

      let indexes = this.$refs.gridDaGan.grid.getSelectedRowIndexes();

      this.dsDaGan = this.dsDaGan.concat(ds);
      let dsIds = this.dsDaGan.map((x) => x.DONVI_ID);

      ds.forEach((e) => {
        indexes.push(dsIds.indexOf(e.DONVI_ID));
      });
      this.dsChuaGan = this.dsChuaGan.filter(
        (x) => !ds.map((x) => x.DONVI_ID).includes(x.DONVI_ID)
      );
    },

    onAddChuaGan() {
      let ds = this.$refs.gridDaGan.grid.getSelectedRecords();

      let indexes = this.$refs.gridChuaGan.grid.getSelectedRowIndexes();

      this.dsChuaGan = this.dsChuaGan.concat(ds);
      let dsIds = this.dsChuaGan.map((x) => x.DONVI_ID);

      ds.forEach((e) => {
        indexes.push(dsIds.indexOf(e.DONVI_ID));
      });

      // this.$refs.dgvLoaiHinhThueBaoChuaGan.flagSelectedRowIndexes = indexes;
      // this.dgvLoaiHinhThueBaoChuaGan.selectedRows = indexes
      this.dsDaGan = this.dsDaGan.filter(
        (x) => !ds.map((x) => x.DONVI_ID).includes(x.DONVI_ID)
      );
    },

    onGhiLaiClick() {
      let data = {
        nhanVienId: this.vnhanvien_id,
        tinNhanId: this.vtinnhan_id,
        // dsDonViId: "11213,11912",
        nhanTinTu: this.isEnabled.tuNgay
          ? moment(this.dataSelect.tuNgay).format("DD/MM/YYYY")
          : "",
        nhanTinDen: this.isEnabled.denNgay
          ? moment(this.dataSelect.denNgay).format("DD/MM/YYYY")
          : "",
        dsDonViId: this.dsDaGan.map((x) => x.DONVI_ID).join(","),
        // nhanTinTu: this.dataSelect.tuNgay,
        // nhanTinDen: this.dataSelect.denNgay
      };
      PhanQuyenNhanVienNhanKSApi.capNhatTnKhachHangKS(this.axios, data)
        .then((response) => {
          if (response.data.error_code === "BSS-00000000") {
            this.$root.toastSuccess("Thành công !");
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
      this.napDonViDuLieu();
      // let tinNhanIdTemp = 58;
      // this.napDsNhanVienDaGan(tinNhanIdTemp);
      this.napDsNhanVienDaGan(this.vtinnhan_id);
    },

    async napDsNhanVienDaGan(tinNhanId) {
      try {
        this.$root.showLoading(true);
        let nvDaGanParam = {
          donViId: this.vdonvi_id, //36176
          tinNhanId: tinNhanId,
        };
        let rsNVDaGan = await this.$root.context.get(
          "/web-cskh/api/nhan-vien-khao-sat/da-gan-tn",
          nvDaGanParam
        );
        if (rsNVDaGan.error_code === this.API_SUCCESS) {
          this.dsNhanVienDaGan = rsNVDaGan.data.data.map((item, index) => ({
            ...item,
            STT: ++index,
          }));
        }
      } catch (e) {
        console.log(e);
      } finally {
        this.$root.showLoading(false);
      }
    },

    async napDsLoaiTin() {
      try {
        let data = {
          loaitnId: 22,
        };
        this.$root.showLoading(true);
        this.dsLoaiDV = [];
        let rs = await this.$root.context.get(
          "/web-cskh/api/danh-muc/loai-don-vi",
          data
        );
        if (rs.error_code === this.API_SUCCESS) {
          this.dsLoaiDV = rs.data.data;
        }
      } catch (e) {
        console.log(e);
      } finally {
        this.$root.showLoading(false);
      }
    },

    async napDonViDuLieu() {
      try {
        let dvChuaGanParam = {
          capTnId: this.vcaptn_id,
          kieu: 0,
          tinNhanId: this.vtinnhan_id,
          nhanVienId: this.vnhanvien_id,
        };
        this.$root.showLoading(true);
        let rsDVChuaGan = await this.$root.context.get(
          "/web-cskh/api/nhan-vien-khao-sat/don-vi",
          dvChuaGanParam
        );
        if (rsDVChuaGan.error_code === this.API_SUCCESS) {
          this.dsChuaGan = rsDVChuaGan.data.data;
        }

        let dvCDaGanParam = {
          capTnId: this.vcaptn_id,
          kieu: 1,
          tinNhanId: this.vtinnhan_id,
          nhanVienId: this.vnhanvien_id,
        };
        let rsDVDaGan = await this.$root.context.get(
          "/web-cskh/api/nhan-vien-khao-sat/don-vi",
          dvCDaGanParam
        );
        if (rsDVDaGan.error_code === this.API_SUCCESS) {
          this.dsDVDaGan = rsDVDaGan.data.data;
        }
      } catch (e) {
        console.log(e);
      } finally {
        this.$root.showLoading(false);
      }
    },
    TroGiup() {
      console.log("first", this.dsNhanVienTheoDonViGetter);
    },
    onEnterMaND() {
      this.fetchKiemTraDonVi(this.dataSelect.maNguoiDung.trim(), "");
    },
    onEnterMaNV() {
      this.fetchKiemTraDonVi("", this.dataSelect.maNV.trim());
    },
    async fetchKiemTraDonVi(maND, maNV) {
      try {
        // this.loading(true);
        const params = {
          maND: maND,
          maNV: maNV,
        };
        const response = await this.$root.context.get(
          "/web-cskh/api/danh-muc/tim-kiem-nhan-vien",
          params
        );
        if (
          response.error_code === "BSS-00000000" &&
          response.data.length > 0
        ) {
          this.onFilterDonViSearch(response.data[0]);
        } else {
          if (maND) {
            this.$root.toastError(`Người dùng với mã ${maND} không tồn tại !`);
          } else {
            this.$root.toastError(`Nhân viên với mã ${maNV} không tồn tại !`);
          }
        }
      } catch (error) {
        //   this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
  },
};
</script>
