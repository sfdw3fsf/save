<template src="./index.html"></template>

<script>
import breadcrumb from "@/components/breadcrumb";
import CaySoDo from "./components/CaySoDo";
import { debounce } from 'lodash';

// Dynamic imports for better performance
const ComponentChiTietIDC = () => import(/* webpackChunkName: "idc" */ "../sodo-kho-tainguyen/components/chitiet-idc");
const ComponentKhoHTM = () => import(/* webpackChunkName: "htm" */ "../kho-hatang-mang/index.vue");
const ComponentKhoHTAH = () => import(/* webpackChunkName: "htah" */ "../KhoHaTangAoHoa/index.vue");
const ComponentKhoHTCLOUD = () => import(/* webpackChunkName: "htcloud" */ "../KhoHaTangSmartCloud/index.vue");
const ComponentKhoHTIDG = () => import(/* webpackChunkName: "htidg" */ "../IDG/IDG.vue");
const ComponentChiTietThietBi = () => import(/* webpackChunkName: "thietbi" */ "../ThongTinThietBi/index.vue");
const ComponentChiTietPort = () => import(/* webpackChunkName: "port" */ "../ChiTietCongThietBi_ThietBi/index.vue");
const ComponentQuanLyVRF = () => import(/* webpackChunkName: "vrf" */ "../VRF/index.vue");
const ComponentQuanLyZone = () => import(/* webpackChunkName: "zone" */ "../quanly-zone/index.vue");
const ComponentQuanLyVlan = () => import(/* webpackChunkName: "vlan" */ "../Vlan/index.vue");
const ComponentQuanLySubNet = () => import(/* webpackChunkName: "subnet" */ "../QuanLySubnet/index.vue");
const ComponentQuanLyIpStore = () => import(/* webpackChunkName: "ipstore" */ "../ipStore/index.vue");
const ComponentQuanLyIpAddress = () => import(/* webpackChunkName: "ipaddress" */ "../DanhMucDiaChiIPAdress/index.vue");

export default {
  name: "QuanLyKhoTaiNguyen",

  components: {
    breadcrumb,
    CaySoDo,
    ComponentChiTietIDC,
    ComponentKhoHTM,
    ComponentKhoHTAH,
    ComponentKhoHTCLOUD,
    ComponentKhoHTIDG,
    ComponentChiTietThietBi,
    ComponentChiTietPort,
    ComponentQuanLyVRF,
    ComponentQuanLyZone,
    ComponentQuanLyVlan,
    ComponentQuanLySubNet,
    ComponentQuanLyIpStore,
    ComponentQuanLyIpAddress
  },

  data: () => ({
    nodeSelected: {
      type: 'none',
      id: null,
      name: '',
      parentId: null,
      parentType: null,
      itemId: null
    },
    CaySoDo_nodeSelected: null,
    kieuTu: null,
    componentKey: 0,
    isLoading: false,
    error: null,
    retryCount: 0,
    maxRetries: 3,
    isFirstLoad: true,
    componentMounted: false,
    pendingNodeData: null,
    loadQueue: [],
    processingQueue: false
  }),

  computed: {
    currentComponent() {
      const componentMap = {
        'IDC': 'ComponentChiTietIDC',
        'THIETBI': 'ComponentChiTietThietBi',
        'HTM': 'ComponentKhoHTM',
        'HTAH': 'ComponentKhoHTAH',
        'HTCLOUD': 'ComponentKhoHTCLOUD',
        'HTIDG': 'ComponentKhoHTIDG',
        'PORT': 'ComponentChiTietPort',
        'VRF': 'ComponentQuanLyVRF',
        'ZONE-VRF': 'ComponentQuanLyZone',
        'VLAN': 'ComponentQuanLyVlan',
        'SUBNET': 'ComponentQuanLySubNet',
        'IP-STORE': 'ComponentQuanLyIpStore',
        'IP-ADDRESS': 'ComponentQuanLyIpAddress'
      };
      return componentMap[this.nodeSelected.type] || null;
    },

    showLoading() {
      return this.isLoading;
    }
  },

  methods: {
    async handleNodeSelected(node) {
      try {
        console.log('Node được chọn:', node);

        this.loadQueue.push({...node});

        if (this.processingQueue) {
          console.log('Đang xử lý queue, thêm request vào hàng đợi');
          return;
        }

        await this.processQueue();
      } catch (error) {
        this.handleError(error);
      }
    },

    async processQueue() {
      if (this.processingQueue || this.loadQueue.length === 0) return;

      this.processingQueue = true;

      try {
        while (this.loadQueue.length > 0) {
          const node = this.loadQueue.shift();
          await this.processNode(node);
        }
      } finally {
        this.processingQueue = false;
        this.setLoading(false);
      }
    },

    async processNode(node) {
      try {
        if (this.isLoading) {
          console.log('Đang tải dữ liệu, đợi xử lý tiếp theo');
          return;
        }

        this.setLoading(true);
        this.clearError();
        this.retryCount = 0;

        if (!this.validateNode(node)) {
          console.log('Node không hợp lệ');
          return;
        }

        this.pendingNodeData = {
          type: node.type || 'none',
          id: node.id || null,
          name: node.name || '',
          parentId: node.parentId || null,
          parentType: node.parentType || null,
          itemId: parseInt(node.itemId) || parseInt(node.id) || null
        };

        await this.updateNodeSelected(this.pendingNodeData);

        if (this.isFirstLoad) {
          console.log('Lần đầu load, đợi component mount');
          await new Promise(resolve => setTimeout(resolve, 500));
          this.isFirstLoad = false;
        }

        await this.$nextTick();
        await this.ensureComponentMounted();

        if (this.nodeSelected.type !== node.type ||
            this.nodeSelected.id !== node.id) {
          console.log('Node đã thay đổi trong quá trình xử lý, bỏ qua');
          return;
        }

        await this.loadComponentData();

      } catch (error) {
        this.handleError(error);
        await this.retryLoadingIfNeeded();
      }
    },

    validateNode(node) {
      if (!node) {
        console.warn('Node là null hoặc undefined');
        this.resetNodeSelected();
        return false;
      }

      if (!node.type) {
        console.warn('Node không có type');
        this.resetNodeSelected();
        return false;
      }

      if (!node.id && !node.itemId) {
        console.warn('Node không có id hoặc itemId');
        this.resetNodeSelected();
        return false;
      }

      return true;
    },

    async updateNodeSelected(nodeData) {
      const oldType = this.nodeSelected.type;
      this.nodeSelected = { ...nodeData };

      if (oldType !== this.nodeSelected.type) {
        console.log('Type thay đổi, tăng componentKey');
        this.componentKey++;
        await this.$nextTick();
      }
    },

    async loadComponentData() {
      const dynamicComponent = this.$refs.dynamicComponent;

      if (!dynamicComponent) {
        console.warn('Không tìm thấy dynamic component sau khi đợi');
        return;
      }

      console.log('Component hiện tại:', {
        type: this.nodeSelected.type,
        component: dynamicComponent,
        methods: Object.keys(dynamicComponent),
        props: dynamicComponent.$props
      });

      try {
        await this.invokeComponentMethod(dynamicComponent);
      } catch (error) {
        console.error(`Lỗi khi load dữ liệu cho node type ${this.nodeSelected.type}:`, error);
        throw error;
      }
    },

    async invokeComponentMethod(component) {
      const methodMap = {
        'IDC': { method: 'loadData', args: [this.nodeSelected.itemId] },
        'THIETBI': { method: 'loadThongTinThietBi', args: [this.nodeSelected.itemId, false] },
        'default': { method: 'loadData', args: [this.nodeSelected.itemId] }
      };

      const { method, args } = methodMap[this.nodeSelected.type] || methodMap.default;

      if (!component) {
        throw new Error('Component không tồn tại');
      }

      if (typeof component[method] !== 'function') {
        console.warn(`Method ${method} không tồn tại trong component ${this.nodeSelected.type}`);
        return;
      }

      console.log(`Gọi ${method} với arguments:`, args);
      try {
        await component[method](...args);
      } catch (error) {
        console.error(`Lỗi khi gọi ${method}:`, error);
        throw error;
      }
    },

    async retryLoadingIfNeeded() {
      if (this.retryCount < this.maxRetries) {
        this.retryCount++;
        const delay = 1000 * this.retryCount;
        console.log(`Thử lại lần ${this.retryCount}/${this.maxRetries} sau ${delay}ms`);
        await new Promise(resolve => setTimeout(resolve, delay));
        await this.loadComponentData();
      }
    },

    setLoading(value) {
      this.isLoading = value;
      this.$emit('loading', value);
    },

    handleError(error) {
      this.error = error.message || 'Có lỗi xảy ra khi tải dữ liệu';
      console.error('Error in handleNodeSelected:', error);
    },

    clearError() {
      this.error = null;
    },

    resetNodeSelected() {
      this.nodeSelected = {
        type: 'none',
        id: null,
        name: '',
        parentId: null,
        parentType: null,
        itemId: null
      };
    },

    async ensureComponentMounted() {
      let tryCount = 0;
      const maxTries = 5;

      while (!this.$refs.dynamicComponent && tryCount < maxTries) {
        console.log(`Đợi component mount lần ${tryCount + 1}`);
        await new Promise(resolve => setTimeout(resolve, 200));
        await this.$nextTick();
        tryCount++;
      }

      if (!this.$refs.dynamicComponent) {
        throw new Error('Component không được mount sau nhiều lần thử');
      }
    },

    clearQueue() {
      this.loadQueue = [];
      this.processingQueue = false;
    }
  },

  watch: {
    currentComponent: {
      immediate: true,
      handler: debounce(async function(newComponent, oldComponent) {
        if (newComponent !== oldComponent) {
          console.log('Component thay đổi:', { old: oldComponent, new: newComponent });
          this.componentKey += 1;

          await this.$nextTick();

          if (this.pendingNodeData && this.$refs.dynamicComponent) {
            console.log('Load lại data cho component mới với pending data:', this.pendingNodeData);
            try {
              await this.loadComponentData();
            } catch (error) {
              this.handleError(error);
            }
          }
        }
      }, 250)
    },

    '$refs.dynamicComponent': {
      handler: async function(newRef) {
        if (newRef && this.pendingNodeData) {
          console.log('Dynamic component ref thay đổi, kiểm tra pending data');
          if (this.nodeSelected.type === this.pendingNodeData.type) {
            try {
              await this.loadComponentData();
            } catch (error) {
              this.handleError(error);
            }
          }
        }
      }
    }
  },

  mounted() {
    console.log('Component QuanLyKhoTaiNguyen đã mount');
    this.componentMounted = true;
  },

  beforeDestroy() {
    this.clearQueue();
    this.resetNodeSelected();
    this.componentMounted = false;
    this.pendingNodeData = null;
  }
};
</script>
<style>
.list-actions-top {
  position: absolute !important;
  top: -60px !important;
  left: -485px !important;
  width: 100vw !important;
}

.padding-top-0 {
  padding-top: 0 !important;
}

.padding-top-4 {
  padding-top: 40px !important;
}

.page-content{
  margin-top: -40px !important;
}

/* Thêm styles cho loading và error states */
.loading-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.error-message {
  padding: 12px;
  margin: 8px 0;
  background-color: #ffebee;
  color: #c62828;
  border-radius: 4px;
  border: 1px solid #ef9a9a;
}

/* CSS Variables */
:root {
  --primary-color: #1976d2;
  --error-color: #c62828;
  --background-color: #ffffff;
  --border-color: #e0e0e0;
  --text-color: #333333;
  --spacing-unit: 8px;
}

/* Responsive styles */
@media (max-width: 768px) {
  .col-sm-3,
  .col-sm-9 {
    width: 100%;
    padding: var(--spacing-unit);
  }

  .list-actions-top {
    position: static !important;
    width: 100% !important;
    left: 0 !important;
  }
}
</style>
