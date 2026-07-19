<template src="./index.template.html"></template>

<script>
import ThongTinChiTiet from './ThongTinChiTiet';

export default {
  name: 'IpStoreV2',
  components: {
    ThongTinChiTiet,
  },
  data() {
    return {
      searchForm: this.getDefaultSearchForm(),
      appliedSearch: null,
      loaiIPList: [],
      phanLoaiIPList: [],
      IDCList: [],
      VRFList: [],
      ZoneList: [],
      VlanList: [],
      SubnetList: [],
      QuyHoachSubnetList: [],
      dataItems: [],
      lichSuCapPhatList: [],
      lichSuThayDoiList: [],
      selectedItem: null,
      showModal: false,
      modalMode: 'CREATE',
      modalTitle: '',
      activeTab: 1,
      currentItem: this.getDefaultCurrentItem(),
      hieuLucOptions: [
        { value: -1, text: 'Tất cả' },
        { value: 1, text: 'Hiệu lực' },
        { value: 0, text: 'Không hiệu lực' },
      ],
    };
  },
  computed: {
    filteredItems() {
      if (!this.appliedSearch) {
        return this.dataItems;
      }
      const filters = this.appliedSearch;
      return this.dataItems.filter((item) => {
        if (filters.ip && !item.IP?.toLowerCase().includes(filters.ip.toLowerCase())) {
          return false;
        }
        if (this.isValidSelect(filters.loaiIP) && Number(item.IP_LOAI_ID) !== Number(filters.loaiIP)) {
          return false;
        }
        if (this.isValidSelect(filters.phanLoaiIP) && Number(item.IP_PHANLOAI_ID) !== Number(filters.phanLoaiIP)) {
          return false;
        }
        if (this.isValidSelect(filters.idc) && Number(item.IDC_ID) !== Number(filters.idc)) {
          return false;
        }
        if (filters.vrf && !item.VRF?.toLowerCase().includes(filters.vrf.toLowerCase())) {
          return false;
        }
        if (filters.zone && !item.ZONE?.toLowerCase().includes(filters.zone.toLowerCase())) {
          return false;
        }
        if (filters.vlan && !item.VLAN?.toLowerCase().includes(filters.vlan.toLowerCase())) {
          return false;
        }
        if (this.isValidSelect(filters.hieuLuc) && Number(item.HIEU_LUC) !== Number(filters.hieuLuc)) {
          return false;
        }
        return true;
      });
    },
    ipInvalid() {
      return !this.validateIPFormat(this.currentItem.ip);
    },
    ipStoreColumns() {
      return [
        { fieldName: 'IP', headerText: 'IP', textAlign: 'Left' },
        { fieldName: 'IP_LOAI', headerText: 'Loại IP', textAlign: 'Left' },
        { fieldName: 'IP_PHANLOAI', headerText: 'Phân loại IP', textAlign: 'Left' },
        { fieldName: 'IDC', headerText: 'Thuộc IDC', textAlign: 'Left' },
        { fieldName: 'VRF', headerText: 'Thuộc VRF', textAlign: 'Left' },
        { fieldName: 'ZONE', headerText: 'Thuộc Zone', textAlign: 'Left' },
        { fieldName: 'VLAN', headerText: 'Thuộc VLAN', textAlign: 'Left' },
        { fieldName: 'SUBNET', headerText: 'Thuộc subnet', textAlign: 'Left' },
        { fieldName: 'SUBNET_QUYHOACH', headerText: 'Mục đích', textAlign: 'Left' },
        { fieldName: 'HATANGMANG', headerText: 'Thuộc HTM', textAlign: 'Left' },
        { fieldName: 'THIETBI', headerText: 'Thuộc TB', textAlign: 'Left' },
        { fieldName: 'HIEU_LUC_TEXT', headerText: 'Hiệu lực', textAlign: 'Center', width: 120 },
        { fieldName: 'GHI_CHU', headerText: 'Ghi chú', textAlign: 'Left' },
        { fieldName: 'ACTIONS', headerText: 'Thao tác', textAlign: 'Center', width: 100, template: this.actionColumn(this) },
      ];
    },
    lichSuCapPhatColumns() {
      return [
        { fieldName: 'STT', headerText: 'STT', width: 60, textAlign: 'Center' },
        { fieldName: 'IP', headerText: 'IP', textAlign: 'Left' },
        { fieldName: 'TRANG_THAI_CAP_PHAT', headerText: 'Trạng thái cấp phát', textAlign: 'Left' },
        { fieldName: 'NGUOI_CAP', headerText: 'Người cấp', textAlign: 'Left' },
        { fieldName: 'MUC_DICH_SU_DUNG', headerText: 'Mục đích sử dụng', textAlign: 'Left' },
        { fieldName: 'THOI_GIAN_CAP', headerText: 'Thời gian cấp', textAlign: 'Left' },
        { fieldName: 'THOI_GIAN_THU_HOI', headerText: 'Thời gian thu hồi', textAlign: 'Left' },
        { fieldName: 'DON_VI_DUOC_CAP', headerText: 'Đơn vị được cấp', textAlign: 'Left' },
        { fieldName: 'SO_CU', headerText: 'Sở cứ', textAlign: 'Left' },
      ];
    },
    lichSuThayDoiColumns() {
      return [
        { fieldName: 'STT', headerText: 'STT', width: 60, textAlign: 'Center' },
        { fieldName: 'HANH_DONG', headerText: 'Hành động', textAlign: 'Left' },
        { fieldName: 'NOI_DUNG', headerText: 'Nội dung thay đổi', textAlign: 'Left' },
        { fieldName: 'NGUOI_THAY_DOI', headerText: 'Người thực hiện', textAlign: 'Left' },
        { fieldName: 'THOI_GIAN', headerText: 'Thời gian', textAlign: 'Left' },
      ];
    },
  },
  watch: {
    'currentItem.idc': function (value) {
      if (value) {
        this.onChangeIDC(value);
      }
    },
    'currentItem.vrf': function (value) {
      if (value) {
        this.onChangeVRF(value);
      }
    },
    'currentItem.zone': function (value) {
      if (value) {
        this.onChangeZone(value);
      }
    },
    'currentItem.vlan': function (value) {
      if (value) {
        this.onChangeVlan(value);
      }
    },
    'currentItem.subnet': async function (value) {
      if (!value) return;
      if (this.SubnetList.length > 0) {
        this.changeSubnet(value);
      } else {
        await this.getSubnetList(this.currentItem.vlan);
        if (this.SubnetList.length > 0) {
          this.changeSubnet(value);
        }
      }
    },
  },
  created() {
    this.initPage();
  },
  methods: {
    getDefaultSearchForm() {
      return {
        ip: '',
        loaiIP: -1,
        phanLoaiIP: -1,
        idc: -1,
        vrf: '',
        zone: '',
        vlan: '',
        hieuLuc: -1,
      };
    },
    getDefaultCurrentItem() {
      return {
        id: 0,
        ip: '',
        loaiIP: 0,
        phanLoaiIP: 0,
        idc: 0,
        vrf: 0,
        zone: 0,
        ghiChu: '',
        hieuLuc: 1,
        quangBaIP: 0,
        trangThaiCCDV: null,
        doiTuongCCDV: null,
        donViQuanLy: null,
        soCuThamChieu: '',
        vlan: 0,
        subnet: 0,
        mucDichSuDung: 0,
        haTangMang: '',
        hatangmang_id: 0,
        thietBi: '',
        thietBiMang: 0,
      };
    },
    async initPage() {
      try {
        this.$root.showLoading(true);
        await Promise.all([
          this.getLoaiIPList(),
          this.getPhanLoaiIPList(),
          this.getIDCList(),
          this.getQuyHoachSubnet(),
        ]);
        await this.loadItems();
      } catch (error) {
        console.error('Lỗi khởi tạo IpStoreV2:', error);
        this.$root.toastError('Không thể tải dữ liệu IP Store');
      } finally {
        this.$root.showLoading(false);
      }
    },
    async loadItems() {
      this.dataItems = await this.getItems();
    },
    onSearch() {
      this.appliedSearch = { ...this.searchForm };
    },
    onResetSearch() {
      this.searchForm = this.getDefaultSearchForm();
      this.appliedSearch = null;
    },
    onRowSelected(args) {
      this.selectedItem = args.data;
    },
    onNew() {
      this.modalMode = 'CREATE';
      this.modalTitle = 'Thêm IP Store';
      this.setCurrentItemDefault();
      this.lichSuCapPhatList = [];
      this.lichSuThayDoiList = [];
      this.activeTab = 1;
      this.showModal = true;
    },
    editItem(item) {
      if (!item) {
        this.$root.toastError('Vui lòng chọn IP Store cần chỉnh sửa');
        return;
      }
      this.modalMode = 'EDIT';
      this.modalTitle = `Cập nhật IP ${item.IP}`;
      this.openEditModal(item);
    },
    async openEditModal(item) {
      try {
        this.$root.showLoading(true);
        await this.setCurrentItem(item);
        await this.loadHistories(item.ID);
        this.activeTab = 1;
        this.showModal = true;
      } catch (error) {
        console.error('Lỗi mở modal chỉnh sửa:', error);
        this.$root.toastError('Không thể mở chi tiết IP Store');
      } finally {
        this.$root.showLoading(false);
      }
    },
    closeModal() {
      this.showModal = false;
    },
    onModalHidden() {
      this.activeTab = 1;
    },
    async saveCurrentItem() {
      if (!this.validateCurrentItem()) {
        return;
      }
      const confirmMessage = this.modalMode === 'CREATE'
        ? 'Bạn có chắc chắn muốn thêm mới IP Store này?'
        : 'Bạn có chắc chắn muốn cập nhật IP Store này?';
      const confirmed = await this.$confirm(confirmMessage, 'Xác nhận', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Huỷ',
        type: 'info',
      }).then(() => true).catch(() => false);
      if (!confirmed) return;
      try {
        this.$root.showLoading(true);
        const payload = { ...this.currentItem };
        let result = false;
        if (this.modalMode === 'CREATE') {
          result = await this.addItem(payload);
        } else {
          result = await this.updateItem(payload);
        }
        if (result) {
          await this.loadItems();
          this.$root.toastSuccess(this.modalMode === 'CREATE' ? 'Thêm IP Store thành công' : 'Cập nhật IP Store thành công');
          this.closeModal();
        }
      } catch (error) {
        console.error('Lỗi lưu IP Store:', error);
      } finally {
        this.$root.showLoading(false);
      }
    },
    setCurrentItemDefault() {
      Object.assign(this.currentItem, this.getDefaultCurrentItem());
    },
    async setCurrentItem(item) {
      Object.assign(this.currentItem, {
        id: item.id ?? item.ID ?? 0,
        ip: item.ip ?? item.IP ?? '',
        loaiIP: item.loaiIP ?? item.IP_LOAI_ID ?? 0,
        phanLoaiIP: item.phanLoaiIP ?? item.IP_PHANLOAI_ID ?? 0,
        idc: item.idc ?? item.IDC_ID ?? 0,
        vrf: item.vrf ?? item.VRF_ID ?? 0,
        zone: item.zone ?? item.ZONE_ID ?? 0,
        ghiChu: item.ghiChu ?? item.GHI_CHU ?? '',
        hieuLuc: item.hieuLuc ?? item.HIEU_LUC ?? 1,
        quangBaIP: item.quangBaIP ?? item.QUANG_BA_IP ?? 0,
        trangThaiCCDV: item.trangThaiCCDV ?? item.TRANGTHAI_CCDV ?? null,
        doiTuongCCDV: item.doiTuongCCDV ?? item.DOITUONG_CCDV ?? null,
        donViQuanLy: item.donViQuanLy ?? item.DONVI_QUANLY ?? null,
        soCuThamChieu: item.soCuThamChieu ?? item.SOCU_THAMCHIEU ?? '',
        vlan: item.vlan ?? item.VLAN_ID ?? 0,
        subnet: item.subnet ?? item.SUBNET_ID ?? 0,
        mucDichSuDung: item.mucDichSuDung ?? item.SUBNET_QUYHOACH_ID ?? 0,
        haTangMang: item.haTangMang ?? item.HATANGMANG ?? '',
        hatangmang_id: item.hatangmang_id ?? item.HATANGMANG_ID ?? 0,
        thietBi: item.thietBi ?? item.THIETBI ?? '',
        thietBiMang: item.thietBiMang ?? item.THIETBIMANG_ID ?? 0,
      });
    },
    validateCurrentItem() {
      let msg = '';
      if (this.ipInvalid) {
        msg = 'Địa chỉ IP không đúng định dạng IPv4/IPv6.';
      }
      const requiredFields = [
        { field: 'loaiIP', label: 'Loại IP' },
        { field: 'phanLoaiIP', label: 'Phân loại IP' },
        { field: 'idc', label: 'IDC' },
        { field: 'vrf', label: 'VRF' },
        { field: 'zone', label: 'Zone' },
        { field: 'vlan', label: 'VLAN' },
        { field: 'subnet', label: 'Subnet' },
      ];
      requiredFields.forEach(({ field, label }) => {
        if (this.currentItem[field] === 0 || this.currentItem[field] === null) {
          msg += (msg ? '\n' : '') + `${label} không được để trống.`;
        }
      });
      if (msg) {
        this.$root.toastError(msg);
      }
      return msg === '';
    },
    isValidSelect(value) {
      return value !== null && value !== undefined && Number(value) !== -1;
    },
    async loadHistories(id) {
      [this.lichSuCapPhatList, this.lichSuThayDoiList] = await Promise.all([
        this.getLichSuCapPhatIPStore(id),
        this.getLichSuThayDoiIPStore(id),
      ]);
    },
    async getLichSuCapPhatIPStore(id) {
      try {
        const rs = await this.$root.context.post('/web-ecms/danhmuc-phanloai-hatang/lay-lich-su-cap-phat-theo-ipstore', { id });
        return rs?.data ?? [];
      } catch (error) {
        console.error('Lỗi lấy lịch sử cấp phát IP Store:', error);
        this.$root.toastError('Không thể tải lịch sử cấp phát IP');
        return [];
      }
    },
    async getLichSuThayDoiIPStore(id) {
      try {
        const rs = await this.$root.context.post('/web-ecms/danhmuc-phanloai-hatang/lay-lich-su-thay-doi-ipstore', { id });
        return rs?.data ?? [];
      } catch (error) {
        console.warn('Chưa có API lịch sử thay đổi IP Store hoặc lỗi khi tải:', error);
        return [];
      }
    },
    actionColumn(parent) {
      return function () {
        return {
          template: {
            template: `<div class="action-buttons">
                <button class="btn btn-sm btn-outline-primary" title="Chỉnh sửa" @click="editItem()">
                  <i class="fa fa-edit"></i>
                </button>
              </div>`,
            methods: {
              editItem() {
                parent.editItem(this.data);
              },
            },
          },
        };
      };
    },
    async getItems() {
      const rs = await this.$root.context.post('/web-ecms/danhmuc-phanloai-hatang/lay-ds-ipstore');
      if (rs?.data) {
        return rs.data.map((item) => ({
          ...item,
          HIEU_LUC_TEXT: item.HIEU_LUC === 1 ? 'Hiệu lực' : 'Không hiệu lực',
        }));
      }
      return [];
    },
    async getLoaiIPList() {
      const rs = await this.$root.context.post('/web-ecms/loai-ip/lay-danhsach');
      if (rs?.data) {
        this.loaiIPList = rs.data;
      }
    },
    async getPhanLoaiIPList() {
      const rs = await this.$root.context.post('/web-ecms/phanloai-ip/lay-danhsach');
      if (rs?.data) {
        this.phanLoaiIPList = rs.data;
      }
    },
    async getIDCList() {
      const rs = await this.$root.context.get('/web-ecms/danhmuc/ha-tang-idc');
      if (rs?.data) {
        this.IDCList = rs.data;
      }
    },
    async onChangeIDC(idIDC) {
      await this.getVRFListByIDC(idIDC);
    },
    async getVRFListByIDC(idIDC) {
      const rs = await this.$root.context.get(`/web-ecms/danhmuc/vrf-by-idc?idcId=${idIDC}`);
      if (rs?.data) {
        this.VRFList = rs.data;
      } else {
        this.VRFList = [];
      }
    },
    async onChangeVRF(idVRF) {
      await this.getZoneList(idVRF);
    },
    async getZoneList(idVRF) {
      const rs = await this.$root.context.post('/web-ecms/danhmuc/quan-ly-zone/get-zone-by-vrf', { id: idVRF });
      if (rs?.data) {
        this.ZoneList = rs.data;
      } else {
        this.ZoneList = [];
      }
    },
    async onChangeZone(idZone) {
      await this.getVlanList(idZone);
    },
    async getVlanList(idZone) {
      const rs = await this.$root.context.post('/web-ecms/quanly-vlan/get-vlan-by-zone', { id: idZone });
      if (rs?.data) {
        this.VlanList = rs.data;
      } else {
        this.VlanList = [];
      }
    },
    async onChangeVlan(idVlan) {
      await this.getSubnetList(idVlan);
    },
    async getSubnetList(idVlan) {
      const params = {
        vlan_id: idVlan,
        ip_loai_id: this.currentItem.loaiIP,
      };
      const rs = await this.$root.context.post('/web-ecms/quanly-subnet/get-subnet-by-vlan-loai-ip', params);
      if (rs?.data) {
        this.SubnetList = rs.data;
      } else {
        this.SubnetList = [];
      }
    },
    async changeSubnet(subnetId) {
      if (!subnetId) return;
      const subnet = this.SubnetList.find((x) => x.ID === subnetId);
      if (!subnet) {
        this.currentItem.hatangmang_id = 0;
        this.currentItem.haTangMang = '';
        return;
      }
      this.currentItem.hatangmang_id = subnet.HATANGMANG_ID;
      try {
        const rs = await this.$root.context.post('/web-ecms/danhmuc/ha-tang-mang/get-hatangmang', { id: subnet.HATANGMANG_ID });
        if (rs?.data) {
          this.currentItem.haTangMang = rs.data.TEN ?? '';
        }
      } catch (error) {
        console.warn('Không thể lấy thông tin hạ tầng mạng:', error);
      }
    },
    async getQuyHoachSubnet() {
      const rs = await this.$root.context.get('/web-ecms/danhmuc/quy-hoach-subnet/danh-sach');
      if (rs?.data) {
        this.QuyHoachSubnetList = rs.data;
      }
    },
    async addItem(args) {
      try {
        const rs = await this.$root.context.post('/web-ecms/danhmuc-phanloai-hatang/them-moi-ipstore', {
          ip: args.ip.trim(),
          ip_loai_id: args.loaiIP,
          ip_phanloai_id: args.phanLoaiIP,
          idc_id: args.idc,
          vrf_id: args.vrf,
          zone_id: args.zone,
          vlan_id: args.vlan,
          subnet_id: args.subnet,
          subnet_quyhoach_id: args.mucDichSuDung,
          hatangmang_id: args.hatangmang_id,
          thietbimang_id: args.thietBiMang,
          ghi_chu: args.ghiChu,
          hieu_luc: args.hieuLuc,
        });
        return rs?.error === 200 && rs?.error_code === 'BSS-00000000';
      } catch (error) {
        console.error('Lỗi thêm IP Store:', error);
        this.$root.toastError(error?.response?.data?.message_detail || 'Không thể thêm IP Store');
        return false;
      }
    },
    async updateItem(args) {
      try {
        const rs = await this.$root.context.post('/web-ecms/danhmuc-phanloai-hatang/cap-nhat-ipstore', {
          id: args.id,
          ip: args.ip.trim(),
          ip_loai_id: args.loaiIP,
          ip_phanloai_id: args.phanLoaiIP,
          idc_id: args.idc,
          vrf_id: args.vrf,
          zone_id: args.zone,
          vlan_id: args.vlan,
          subnet_id: args.subnet,
          subnet_quyhoach_id: args.mucDichSuDung,
          hatangmang_id: args.hatangmang_id,
          thietbimang_id: args.thietBiMang,
          ghi_chu: args.ghiChu,
          hieu_luc: args.hieuLuc,
        });
        return rs?.error === 200 && rs?.error_code === 'BSS-00000000';
      } catch (error) {
        console.error('Lỗi cập nhật IP Store:', error);
        this.$root.toastError(error?.response?.data?.message_detail || 'Không thể cập nhật IP Store');
        return false;
      }
    },
    validateIPFormat(ip) {
      if (!ip || ip.trim() === '') return false;
      const trimmedIp = ip.trim();
      const ipv4Regex = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
      const ipv6Patterns = [
        /^([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$/,
        /^::$/,
        /^::1$/,
        /^::([0-9a-fA-F]{1,4}:){0,6}[0-9a-fA-F]{1,4}$/,
        /^([0-9a-fA-F]{1,4}:){1,7}:$/,
        /^([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}$/,
        /^([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}$/,
        /^([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}$/,
        /^([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}$/,
        /^([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}$/,
        /^[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})$/,
        /^([0-9a-fA-F]{1,4}:){6}((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/,
        /^::([0-9a-fA-F]{1,4}:){0,5}((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/,
        /^fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}$/,
        /^::ffff:((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/,
      ];
      if (trimmedIp.includes('/')) {
        const [ipAddress, cidr] = trimmedIp.split('/');
        const cidrNum = Number(cidr);
        if (Number.isNaN(cidrNum)) return false;
        if (ipv4Regex.test(ipAddress)) {
          const parts = ipAddress.split('.');
          if (parts.some((part) => part.length > 1 && part.startsWith('0'))) {
            return false;
          }
          return cidrNum >= 0 && cidrNum <= 32;
        }
        for (const pattern of ipv6Patterns) {
          if (pattern.test(ipAddress)) {
            if (ipAddress.includes('::')) {
              const doubleColonCount = (ipAddress.match(/::/g) || []).length;
              if (doubleColonCount > 1) {
                return false;
              }
            }
            return cidrNum >= 0 && cidrNum <= 128;
          }
        }
        return false;
      }
      if (ipv4Regex.test(trimmedIp)) {
        const parts = trimmedIp.split('.');
        if (parts.some((part) => part.length > 1 && part.startsWith('0'))) {
          return false;
        }
        return true;
      }
      for (const pattern of ipv6Patterns) {
        if (pattern.test(trimmedIp)) {
          if (trimmedIp.includes('::')) {
            const doubleColonCount = (trimmedIp.match(/::/g) || []).length;
            if (doubleColonCount > 1) {
              return false;
            }
          }
          return true;
        }
      }
      return false;
    },
  },
};
</script>

<style scoped>
.action-buttons {
  display: flex;
  justify-content: center;
}

.action-buttons .btn {
  padding: 2px 8px;
}

.total-records {
  font-size: 14px;
  color: #666;
}

.ip-store-modal .modal-body {
  max-height: 70vh;
  overflow-y: auto;
}
</style>

