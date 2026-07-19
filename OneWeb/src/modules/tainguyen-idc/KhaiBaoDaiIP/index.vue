<template src="./index.template.html"></template>
<script>
import ModalChiTietDaiIP from './chitiet-DaiIP/index.vue';

export default {
  name: 'KhaiBaoDaiIP',
  components: {
    ModalChiTietDaiIP,
  },
  data() {
    return {
      isDisableBtnGhiLai: true,
      isDisableBtnSearch: false,
      isViewIDC: true,
      pKyHieu_Detail: "",
      pIPDau_Detail: "",
      pIPCuoi_Detail: "",
      tongSoIpDung: 0,
      tongSoIpChua: 0,
      pDaiIdSelect: 0,
      dsDaiIP: [],
      dsDaiIPCha: [],
      pIDCSearchId: null,
      dsIDCSearch: [],
      pIDCDonviSearchId: null,
      dsIDCDonviSearch: [],
      pLoaiIPSearchId: null,
      dsLoaiIPSearch: [],
      dsTrangthai: [
        {TT_ID: 1, TT_TEN: 'Chưa sử dụng'},
        {TT_ID: 2, TT_TEN: 'Đang sử dụng'},
        {TT_ID: 3, TT_TEN: 'Bị tạm ngưng sử dụng'},
        {TT_ID: 4, TT_TEN: 'Bị khóa tạm thời'},
      ],

      historyItemList: [],

      form: {
        pId: 0,
        pLoaiDaiIPId: 0,
        pLoaiDaiIP: null,
        pPhanLoaiId: null,
        pPhanLoai: null,
        pDiaChiDaiIP: "",
        pDonviThuchienId: 0,
        pDonviThuchienTen: "",
        pNguoiThuchienTen: "",
        pNguoiCapnhat: "",
        pNhanvienId: 0,
        pNhanvienTen: "",
        pKyHieu: "",
        pIPDau: "",
        pIPCuoi: "",
        pIDCId: 0,
        pIDCTen: "",
        pTrangthaiId: 0,
        pTrangthaiTen: "",
        pSubnetId: 0,
        pSubnetMaskId: 0,
        pSubnetMask: "255.255.255.0",
        pGateway: "",
        pCanCuKhaiBao: "",
        dtpThoidiemKhaibao: new Date(),
        pGhiChu: "",
        pHieuLuc: 1,
      },
      dsData: {
        dsDaiIP: [],
        dsDaiIPCha: [],
        dsLoaiIP: [],
        dsIdc: [],
        dsSubnetMark: [],
        pDonviThuchienId: 0,
        pDonviThuchienTen: "",
        pNguoiThuchienTen: "",
        pNguoiCapnhat: "",
        pNhanvienId: 0,
        pNhanvienTen: "",
      },
      currentItem: {
        id: 0,
        ten: '', // Tên subnet
        subnet: '', // Tên subnet
        ipId: 0, // Loại Ip
        plIpId: 0, // Phân loại Ip
        idcId: 0, // Thuộc IdC
        gateWay: '', //Gateway
        tenIDC: '',
        thuocHaTangId: 0, // Thuộc hạ tầng mạng
        thietBiId: 0, // Thuộc thiết bị
        tenThietBi: '',
        vlanId: 0, // Thuộc VlanId
        vrfId: 0,
        subnetId: 0,// SubnetId
        subnetMarkId: null, // SubnetMarkId
        mucDichId: 0, // Mục đích sử dụng
        tenVRF: '',
        zoneId: 0, // Thuộc zone
        tenZone: '',
        ghiChu: '',
        thamChieu: '',
        hieuLuc: 1,
      },
      selectionOptions: {
        type: 'Single',      // chỉ chọn 1 dòng
        mode: 'Row',         // chọn theo dòng
        checkboxOnly: false, // không cần checkbox
      },

      childGridDaiIP: {
        dataSource: [],
        queryString: 'IP_STORE_ID',
        columns: [
          {field: 'STT', headerText: 'STT', textAlign: 'Left', width: 50},
          {field: 'ID', headerText: 'ID', textAlign: 'Left', width: 150},
          {field: 'IP', headerText: 'IP', textAlign: 'Left', width: 250},
          {field: 'SUBNETMARK', headerText: 'Subnetmask', textAlign: 'Left', width: 250},
          {field: 'TRANGTHAICAPPHAT', headerText: 'Trạng thái', textAlign: 'Left', width: 250},
          { field: 'ACTIONS', headerText: 'Thao tác', textAlign: 'Center', width: 180, template: this.actionColumn(this,true) }
        ],
        load: function () {
          this.parentDetails.parentKeyFieldValue = this.parentDetails.parentRowData["ID"];
        },
        childGrid: {
          dataSource: [],
          queryString: 'IP_STORE_ID',
          columns: [
            {field: 'STT', headerText: 'STT', textAlign: 'Left', width: 50},
            {field: 'ID', headerText: 'ID', textAlign: 'Left', width: 150},
            {field: 'IP', headerText: 'IP', textAlign: 'Left', width: 250},
            {field: 'SUBNETMARK', headerText: 'Subnetmask', textAlign: 'Left', width: 250},
            {field: 'TRANGTHAICAPPHAT', headerText: 'Trạng thái', textAlign: 'Left', width: 250},
            { field: 'ACTIONS', headerText: 'Thao tác', textAlign: 'Center', width: 180, template: this.actionColumn(this,true) }
          ],
          load: function () {
            this.parentDetails.parentKeyFieldValue = this.parentDetails.parentRowData["ID"];
          }
        }
      }
    }
  },
  created: async function () {
    this.isError = false;
    this.childGridDaiIP.detailDataBound = this.onDetailDataBound;
    this.childGridDaiIP.childGrid.detailDataBound = this.onDetailDataBound;

    try {
      this.loading(true);

      //     // init current user Info
      // this.userInfo.nhanVienId = await this.$root.token.getNhanVienID();
      // this.userInfo.nhanhVienTen = await this.$root.context.user.getTenNhanVien();
      // this.userInfo.mayNhanVien = await this.$root.token.getMachine();
      // this.userInfo.IP = await this.$root.token.getIP();
      // this.userInfo.phanVungID = await this.$root.token.getPhanVungID();
      // this.userInfo.donViId = await this.$root.token.getDonViID();
      this.dsData.pDonviThuchienId = await this.$root.token.getDonViID();
      this.dsData.pDonviThuchienTen = await this.$root.context.user.getTenDonVi();
      this.dsData.pNguoiThuchienTen = await this.$root.context.user.getTenNhanVien();
      this.dsData.pNguoiCapnhat = await this.$root.token.getUserName();
      this.dsData.nhanVienId = await this.$root.token.getNhanVienID();
      this.dsData.nhanhVienTen = await this.$root.context.user.getTenNhanVien();

      var rs = await this.$root.context.post('/web-ecms/idc/daiIP/lay-ds-subnetmask', {});
      this.dsData.dsSubnetMark = rs.data;

      rs = await this.$root.context.post('/web-ecms/idc/daiIP/lay-ds-donvi-idc');
      this.dsIDCDonviSearch = [{ID: 0, TEN: 'Bỏ chọn'}, ...rs.data];
      if (this.dsData.pDonviThuchienId == 891543) {
        this.isViewIDC = false;
        rs = await this.$root.context.post('/web-ecms/idc/daiIP/lay-ds-idc-donviId', 0);
        this.dsIDCSearch = [{IDC_ID: 0, IDC_TEN: 'Bỏ chọn'}, ...rs.data];
      } else {
        this.pIDCDonviSearchId = this.dsData.pDonviThuchienId;
        rs = await this.$root.context.post('/web-ecms/idc/daiIP/lay-ds-idc-donviId', this.pIDCDonviSearchId);
        this.dsIDCSearch = [{IDC_ID: 0, IDC_TEN: 'Bỏ chọn'}, ...rs.data];
      }

      rs = await this.$root.context.post('/web-ecms/idc/daiIP/lay-ds-loai-IP', {});
      this.dsLoaiIPSearch = [{ID: 0, TEN: 'Bỏ chọn'}, ...rs.data];
      this.dsData.dsLoaiIP = rs.data;

      // rs = await this.$root.context.post('/web-ecms/idc/daiIP/lay-ds-daiIP', {});
      // this.dsData.dsDaiIP = rs.data;

      // rs = await this.$root.context.post('/web-ecms/idc/daiIP/lay-ds-daiIP-cha', {});
      // this.dsData.dsDaiIPCha = rs.data;
      await this.refreshDsDaiIP(1);

    } catch (error) {
      this.$root.toastError('Xảy ra lỗi')
      console.log(error)
    } finally {
      this.loading(false);
    }
  },
  methods: {
    apiListHistory: async function (DaiIPId) {
      let result = [];
      try {
        this.loading(true);
        const payload = {
          id: DaiIPId ? DaiIPId : null,
        };
        let rs = await this.$root.context.post("/web-ecms/idc/daiIP/lay-lichsu-tacdong", payload);
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      } finally {
        this.loading(false);
      }
      return result;
    },
    showModal: async function (item, showMode) {
      try {
        this.loading(true);
        var rs = await this.$root.context.post('/web-ecms/idc/daiIP/lay-ds-idc-donviId', this.form.pDonviThuchienId);
        this.dsData.dsIdc = rs.data;
        this.$nextTick(() => {
          //this.$refs.ModalChiTietDaiIP.show();
          this.$refs.ModalChiTietDaiIP.showModal(item, this.dsData, showMode);
        });
      } catch (e) {
        this.handleApiError(e);
        console.error("API error:", e);
        result = false;
      } finally {
        this.loading(false);
      }
    },
    async refreshDsDaiIP(iRefresh) {
      try {
        this.loading(true);
        var rs;
        if (iRefresh == 0) {
          // console.log(this.dsData.pDonviThuchienId)

          const payload = {
            idc_id: this.pIDCSearchId ? this.pIDCSearchId : null,
            donvi_th_id: this.pIDCDonviSearchId ? this.pIDCDonviSearchId : null,
            ip_loai_id: this.pLoaiIPSearchId ? this.pLoaiIPSearchId : null,
          };
          if (this.dsData.pDonviThuchienId != 891543) {
            payload.donvi_th_id = this.dsData.pDonviThuchienId;
          }

          let rs = await this.$root.context.post("/web-ecms/idc/daiIP/search-daiIP", payload);
          /*
         if(this.dsData.pDonviThuchienId == 891543) {
           rs = await this.$root.context.post('/web-ecms/idc/daiIP/lay-ds-daiIP', {});
         }
         else {
           rs = await this.$root.context.post('/web-ecms/idc/daiIP/lay-ds-daiIP', this.dsData.pDonviThuchienId);
         }
         */
          this.dsData.dsDaiIP = rs.data;
          this.dsDaiIP = rs.data;
        }
        if (this.dsData.pDonviThuchienId == 891543) {
          rs = await this.$root.context.post('/web-ecms/idc/daiIP/lay-ds-daiIP-DonviId', 0);
        }
        else {
          rs = await this.$root.context.post('/web-ecms/idc/daiIP/lay-ds-daiIP-DonviId', this.dsData.pDonviThuchienId);
        }
                // this.dsData.dsDaiIPCha = rs.data;
        // this.dsDaiIPCha = rs.data;
        this.dsData.dsDaiIPCha = [{ID: 0, TEN: 'Bỏ chọn'}, ...rs.data];
        this.dsDaiIPCha = [{ID: 0, TEN: 'Bỏ chọn'}, ...rs.data];
      } catch (e) {
        console.error(e);
        this.$root.toastError("Không thể refresh danh sách dải IP");
      } finally {
        this.loading(false);
      }
    },
    actionColumn(parent, isChild = false) {
        return function () {
          return {
            template: {
              template: `
            <div v-if="showAction" class="action-buttons gap-1">
              <button class="btn btn-sm btn-outline-primary" title="Xem chi tiết" @click="viewItem()" :disabled="false">
                <i class="fa fa-eye"></i>
              </button>
              <button class="btn btn-sm btn-outline-success" title="Chỉnh sửa" @click="editItem()" :disabled="false">
                <i class="icon one-edit"></i>
              </button>
              <button class="btn btn-sm btn-outline-danger" title="Xóa" @click="xoaItem()" :disabled="false">
                <i class="icon one-trash"></i>
              </button>
            </div>
            <div v-else style="text-align:center; color:#aaa;"></div>
          `,
            data() {
              return {
                isChildGrid: isChild, // ✅ đưa biến vào trong template
              };
            },
            computed: {
              showAction() {
                if (!this.data) return false;
                // ✅ Grid con: chỉ hiển thị khi có cháu
                if (this.isChildGrid) {
                  // console.log(this.data)
                  return this.data?.HAS_CHILD === 1 || this.data?.HAS_CHILD === true;
                }
                // ✅ Grid cha: luôn hiển thị
                return true;
              }
            },
            methods: {
              viewItem: async function() {
                // console.log('this.data')
                // console.log(this.data)
                const Id = this.data.ID;
                const rs = await parent.$root.context.post('/web-ecms/idc/daiIP/lay-chitiet-daiIP', { id: Id });
                // console.log(rs.data)
                parent.showModal(rs.data[0], "view");
                parent.refreshDsDaiIP(0);
              },
              editItem: async function() {
                // console.log('this.data')
                // console.log(this.data)
                const Id = this.data.ID;
                const rs = await parent.$root.context.post('/web-ecms/idc/daiIP/lay-chitiet-daiIP', { id: Id });
                // console.log(rs.data)
                parent.showModal(rs.data[0], "edit");
                parent.refreshDsDaiIP(0);
              },
              xoaItem: async function () {
                if (this.data.ID == 0) {
                  return;
                }
                let msg = `Bạn có muốn xóa dữ liệu không?`
                let flag = await this.$confirm(msg, "Xác nhận",
                    {
                      confirmButtonText: "Đồng ý",
                      cancelButtonText: "Không đồng ý",
                      type: "warning",
                      dangerouslyUseHTMLString: true,
                    }
                )
                    .then((s) => true)
                    .catch((e) => {
                      return false;
                    });
                if (flag) {
                  var mess = "";
                  var isSuccess = 1;
                  try {
                    this.loading(true);
                    let rs = await parent.$root.context.post("/web-ecms/idc/daiIP/delete-daiIP", {id: this.data.ID});
                    mess = rs.message;
                  } catch (e) {
                    isSuccess = 0;
                    if (e.response && e.response.data) {
                      // lấy message_detail
                      mess = e.response.data.message_detail;
                    } else {
                      console.log(e);
                      this.$root.toastError("Không nhận được response từ server");
                    }

                  } finally {
                    this.loading(false);
                  }
                  parent.refreshDsDaiIP(0);
                  if (isSuccess == 1)
                    this.$root.toastSuccess(mess);
                  else
                    this.$root.toastError(mess);
                }
              },
            },
          },
        };
      };
    },
    nhapMoi: async function () {
      this.setCurrentItemDefault();
      // this.form.pDonviThucHienTen
      // var rs = await this.$root.context.post('/web-ecms/idc/daiIP/lay-ds-idc-donviId', this.form.pDonviThuchienId);
      // this.dsData.dsIdc = rs.data;
      // console.log(this.form)
      this.showModal(this.form, "add");
      this.refreshDsDaiIP(0);
    },
    onIDCDonviChange: async function (value) {
      //   const selected = this.dsIDCDonviSearch.find(x => x.ID == value)
      var iDonvi = 0;
      if (value == 0)
        this.pIDCDonviSearchId = null;
      else if (value == 891543)
        this.pIDCDonviSearchId = value;
      else {
        this.pIDCDonviSearchId = value;
        iDonvi = value;
      }
      var rs = await this.$root.context.post('/web-ecms/idc/daiIP/lay-ds-idc-donviId', iDonvi);
      this.dsIDCSearch = [{IDC_ID: 0, IDC_TEN: 'Bỏ chọn'}, ...rs.data];
      // console.log(value)
      // console.log(selected)
      // console.log(this.pIDCSearchId)
    },
    onIDCChange(value) {
      //   const selected = this.dsIDCSearch.find(x => x.IDC_ID == value)
      if (value == 0)
        this.pIDCSearchId = null;
      else
        this.pIDCSearchId = value;

      // console.log(value)
      // console.log(selected)
      // console.log(this.pIDCSearchId)
    },
    onLoaiIPChange(value) {
      //   const selected = this.dsLoaiIP.find(x => x.ID == value)
      if (value == 0)
        this.pLoaiIPSearchId = null;
      else
        this.pLoaiIPSearchId = value;

      // console.log(value)
      // console.log(selected)
    },

    onDaiIPChange(value) {
      const selected = this.dsData.dsDaiIP.find(x => x.ID == value)
      // this.pDaiIdSelect = selected;
      if (selected) {
        // console.log(selected);
        this.pKyHieu_Detail = selected.KYHIEU
        this.pIPDau_Detail = selected.IP_DAU
        this.pIPCuoi_Detail = selected.IP_CUOI
        this.tongSoIpDung = selected.DA_SU_DUNG;
        this.tongSoIpChua = selected.TONG_IP - selected.DA_SU_DUNG;
      } else {
        // console.warn('KhÃ´ng tÃ¬m tháº¥y record phÃ¹ há»£p')
      }
    },
    onDelete: async function () {
      if (this.form.pId == 0) {
        return;
      }
      let msg = `Bạn có muốn xóa dữ liệu không?`
      let flag = await this.$confirm(msg, "Xác nhận",
          {
            confirmButtonText: "Đồng ý",
            cancelButtonText: "Không đồng ý",
            type: "warning",
            dangerouslyUseHTMLString: true,
          }
      )
          .then((s) => true)
          .catch((e) => {
            return false;
          });
      if (flag) {
        try {
          this.loading(true);
          let rs = await this.$root.context.post("/web-ecms/idc/daiIP/delete-daiIP", {id: this.form.pId});
          result = rs.data.result;
        } catch (e) {
          this.handleApiError(e);
          console.error("API error:", e);
          result = false;
        } finally {
          this.loading(false);
        }
        return result;
      }
    },
    setupGridChildBehavior(gridInstance, dataSource) {
      if (!gridInstance) return;

      // ✅ Gán lại dataSource
      gridInstance.dataSource = dataSource || [];

      const hasAnyChild = (dataSource || []).some(row => row.HAS_CHILD === 1 || row.HAS_CHILD === true);

      if (!hasAnyChild) {
        gridInstance.childGrid = null;
      }

      gridInstance.rowDataBound = (rowArgs) => {
        const hasChild = rowArgs.data.HAS_CHILD === 1 || rowArgs.data.HAS_CHILD === true;
        const detailCell = rowArgs.row.querySelector('td.e-detailrowcollapse, td.e-detailindentcell');

        if (detailCell) {
          if (!hasChild) {
            // ❌ Xoá childGrid khỏi dòng không có cháu
            if (gridInstance.childGrid) {
              delete rowArgs.data.childGrid;
            }

            detailCell.innerHTML = "";
            detailCell.style.width = "16px";
            detailCell.style.minWidth = "16px";
            detailCell.style.padding = "0px";
            detailCell.style.border = "none";
            detailCell.style.cursor = "default";
          } else {
            detailCell.style.width = "";
            detailCell.style.minWidth = "";
            detailCell.style.padding = "";
            detailCell.style.border = "";
            detailCell.style.cursor = "";
          }
        }

        if (!hasChild) {
          rowArgs.row.style.backgroundColor = "#f8f9fa"; // xám nhạt
          rowArgs.row.style.opacity = "0.9";
          rowArgs.row.setAttribute("title", "Hàng này không có IP con");
        } else {
          rowArgs.row.style.backgroundColor = "";
          rowArgs.row.style.opacity = "1";
          rowArgs.row.removeAttribute("title");
        }
      };

      gridInstance.detailExpand = function(args) {
        if (args.data.HAS_CHILD === 0 || args.data.HAS_CHILD === false) {
          args.cancel = true;
          // ⚠️ Xoá luôn childGrid khỏi dòng này để Syncfusion không tạo vùng expand
          delete args.data.childGrid;
        }
      };
    },
    async onDetailDataBound(args) {
      try {
        this.loading(true);

        const detailGrid = args.detailElement.querySelector('.e-grid');
        const gridInstance = detailGrid?.ej2_instances[0];

        if (gridInstance) {
          // 🧩 CHA → CON
          if (args.data && args.data.IDC_ID) {
            const parentId = args.data.ID;
            const rs = await this.$root.context.post('/web-ecms/idc/daiIP/lay-ds-daiIP-con', { id: parentId });
            this.setupGridChildBehavior(gridInstance, rs.data);
          }

          // 🧩 CON → CHÁU
          else if (args.data && args.data.IP) {
            const childId = args.data.ID;
            // console.log(args.data)
            const rs = await this.$root.context.post('/web-ecms/idc/daiIP/lay-ds-daiIP-con', { id: childId });

            // Nếu không có cháu → bỏ grid con luôn
            if (!rs.data || rs.data.length === 0) {
              gridInstance.childGrid = null;
              args.detailElement.innerHTML = "";
              return;
            }

            this.setupGridChildBehavior(gridInstance, rs.data);
          }
        }

        // ✅ Custom header như cũ
        if (args.data) {
          const count = (gridInstance?.dataSource || []).length;
          if (count > 0) {
            const headerElement = args.detailElement.querySelector('.e-gridheader');
            if (headerElement) {
              const customHeader = document.createElement('div');
              customHeader.innerHTML = `
            <div style="padding: 10px; background-color: #f5f5f5;
                        border-bottom: 1px solid #ddd;
                        font-weight: bold; color: #333;">
              <i class="fa fa-server" style="margin-right: 5px;"></i>
              Danh sách IP: (${count} bản ghi)
            </div>`;
              args.detailElement.insertBefore(customHeader, args.detailElement.firstChild);
            }
          }
        }
      } finally {
        this.loading(false);
      }
    },
    onSearch: async function () {
      try {
        this.loading(true);
        const payload = {
          idc_id: this.pIDCSearchId ? this.pIDCSearchId : null,
          donvi_th_id: this.pIDCDonviSearchId ? this.pIDCDonviSearchId : null,
          ip_loai_id: this.pLoaiIPSearchId ? this.pLoaiIPSearchId : null,
        };

        let rs = await this.$root.context.post("/web-ecms/idc/daiIP/search-daiIP", payload);
        this.dsData.dsDaiIP = rs.data
      } catch (e) {
        this.handleApiError(e);
        console.error("API error:", e);
        result = false;
      } finally {
        this.loading(false);
      }
    },
    onClearSeach: async function () {
      var rs;
      if (this.dsData.pDonviThuchienId == 891543) {
        this.pIDCDonviSearchId = null;
        this.isViewIDC = false;
        rs = await this.$root.context.post('/web-ecms/idc/daiIP/lay-ds-idc-donviId', 0);
        this.dsIDCSearch = [{IDC_ID: 0, IDC_TEN: 'Bỏ chọn'}, ...rs.data];
      } else {
        this.pIDCDonviSearchId = this.dsData.pDonviThuchienId;
        rs = await this.$root.context.post('/web-ecms/idc/daiIP/lay-ds-idc-donviId', this.pIDCDonviSearchId);
        this.dsIDCSearch = [{IDC_ID: 0, IDC_TEN: 'Bỏ chọn'}, ...rs.data];

      }
      this.pIDCSearchId = null;
      this.pLoaiIPSearchId = null;
      this.refreshDsDaiIP(0);
    },

    setCurrentItem: async function (item) {
      // console.log(item);
      this.form.pId = item.ID;
      this.form.pLoaiDaiIP = item.IPL_ID;

      if (item.TENLOAIIP === "IP v4") {
        this.form.pLoaiDaiIP = 1;
      } else if (item.TENLOAIIP === "IP v6") {
        this.form.pLoaiDaiIP = 2;
      }

      this.form.pDiaChiDaiIP = item.SUBNET;
      // if(this.form.pLoaiDaiIP=== 2 ? "0" : "1"
      // this.form.pLoaiDaiIP = item.TEN;
      if (item.TENPHANLOAIIP == "Internet") {
        this.form.pPhanLoai = 1;
      } else {
        this.form.pPhanLoai = 0;
      }
      this.form.pDiaChiDaiIP = item.SUBNET;
      this.form.pKyHieu = item.KYHIEU;
      this.form.pSubnetMaskId = item.SUBNETMARK_ID;
      // this.form.pSubnetId = item.SUBNET_ID;
      this.form.pGateway = item.GATEWAY;
      this.form.pIDCId = item.IDC_ID;
      this.form.pCanCuKhaiBao = item.CANCU_TH;
      this.form.dtpThoidiemKhaibao = item.NGAY_TH;
      this.form.subnet = item.SUBNET;
      this.form.pIPDau = item.IP_DAU;
      this.form.pIPCuoi = item.IP_CUOI;

      this.form.pTrangthaiId = item.TRANGTHAI_ID;
      this.form.pCanCuKhaiBao = item.CANCU_TH;
      // console.log(this.form);
      // this.lichSuList = [];
    },
    setCurrentItemDefault: function () {
      this.form = {
        pId: 0,
        pLoaiDaiIPId: 0,
        pLoaiDaiIP: 0,
        pPhanLoaiId: 0,
        pPhanLoai: 0,
        pDiaChiDaiIP: '',
        pKyHieu: '',
        pSubnetMaskId: 187,         // default subnet mask
        pSubnetId: 0,
        pGateway: '',
        pIDCId: 0,
        pCanCuKhaiBao: '',
        dtpThoidiemKhaibao: new Date(), // hôm nay
        subnet: '',
        pIPDau: '',
        pIPCuoi: '',
        pTrangthaiId: 1,            // Chưa sử dụng
        pDonviThuchienId: this.dsData.pDonviThuchienId,
        pDonviThuchienTen: this.dsData.pDonviThuchienTen,
        pNguoiThuchienTen: this.dsData.pNguoiThuchienTen,
        pNguoiCapnhat: this.dsData.pNguoiCapnhat,
        pNhanvienId: this.dsData.pNhanvienId,
        pNhanvienTen: this.dsData.pNhanvienTen,
        pSubnetMask: '255.255.255.0',
        dsDaiIP: this.dsData.dsDaiIP,
        dsDaiIPCha: this.dsData.dsDaiIPCha,
        dsSubnetMark: this.dsData.dsSubnetMark
      }
    },

    grdItems_RowSelected: async function (args) {
      try {
        this.form.pId = args.data.ID;
        this.$root.showLoading(true);
        this.setCurrentItem(args.data);
        // this.haTangAoHoaList = [];
        // this.subnetConList = [];
        // this.ipStoreList = [];
        this.historyItemList = await this.apiListHistory(args.data.ID);
        // this.lichSuList = [];

      } finally {
        this.$root.showLoading(false);
      }

    },
    grdItems_RowDeselected: async function () {
      let items = this.$refs.gridItems.getSelectedRecords();
      if (!(items == null || items.length == 0)) {
        try {
          this.$root.showLoading(true);
          this.setCurrentItem(items[0]);
          //   this.haTangMangItemList = await this.getHaTangItems(items[0].ID);
          //   this.subnetItemList = await this.getSubnetItems(items[0].VLAN_ID);


        } finally {
          this.$root.showLoading(false);
        }
      } else {
        this.setCurrentItemDefault();
        // this.lichSuItemList = [];
        // this.haTangMangItemList = [];
        // this.subnetItemList = [];
      }
    },
    isExist(ten, gateWay) {
      const exists = this.dsDaiIP.some(
          item =>
              item.TEN?.trim().toUpperCase() === ten?.trim().toUpperCase() &&
              item.GATEWAY?.trim().toUpperCase() === gateWay?.trim().toUpperCase()
      );

      return exists;
    },
    handleApiError: function (e) {
      if (e?.response?.data?.message_detail) {
        this.$root.toastError(e.response.data.message_detail);
      }
    },
    toNumberOrNull(val) {
      if (val === undefined || val === null || val === "" || isNaN(val)) {
        return null;
      }
      return Number(val);
    },
    formatDateISO(date) {
      if (!date) return null;
      const d = new Date(date);
      const year = d.getFullYear();
      const month = String(d.getMonth() + 1).padStart(2, "0");
      const day = String(d.getDate()).padStart(2, "0");
      return `${year}-${month}-${day}`;  // ISO yyyy-MM-dd
    },
    updateItem: async function (args) {
      // console.log(">>> Form args:", args);
      let result = true;
      try {
        this.loading(true);
        // xử lý id: 0 => null
        const payload = {
          id: (args.pId === 0 ? null : this.toNumberOrNull(args.pId)),
          ip_loai_id: this.toNumberOrNull(args.pLoaiDaiIP),
          ip_phanloai_id: this.toNumberOrNull(args.pPhanLoai),
          subnet_mask_id: this.toNumberOrNull(args.pSubnetMaskId),
          idc_id: this.toNumberOrNull(args.pIDCId),
          trangthai_id: this.toNumberOrNull(args.pTrangthaiId),
          ten: args.pDiaChiDaiIP,
          kyhieu: args.pKyHieu,
          ipDau: args.pIPDau,
          ipCuoi: args.pIPCuoi,
          gateway: args.pGateway,
          cancuTH: args.pCanCuKhaiBao ? args.pCanCuKhaiBao : null,
          // subnet_id: this.toNumberOrNull(args.pSubnetId),
          thoidiem_Khaibao: this.formatDateISO(args.dtpThoidiemKhaibao),
          nguoiCapnhat: args.pNguoiCapnhat
        };

        // console.log(">>> Payload:", JSON.stringify(payload));

        let rs = await this.$root.context.post("/web-ecms/idc/daiIP/update-daiIP", payload);
      } catch (e) {
        this.handleApiError(e);
        console.error("API error:", e);
        result = false;
      } finally {
        this.loading(false);
      }
      return result;
      // this.enableControls(true);
    },

    onSelectRowCheckbox(selectedItems) {
      this.selectedRows = selectedItems || []
    },
  }
}
</script>

<style scoped>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}

.e-detailrowcollapse,
.e-detailrowexpand {
  display: inline-block !important;
}

.e-detailrowcollapse::before, .e-detailrowexpand::before {
  display: none !important;
}
</style>
