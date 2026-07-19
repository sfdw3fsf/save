<template src="./index.template.html"></template>

<script>
import '@syncfusion/ej2-vue-diagrams/styles/material.css';
import {
  DiagramPlugin,
  DiagramComponent,
  DiagramConstraints,
  NodeConstraints,
  RadialTree,
  DiagramContextMenu
} from '@syncfusion/ej2-vue-diagrams';
import Vue from 'vue';
import { COLORS, DIAGRAM_CONFIG, CONNECTOR_DEFAULTS } from './constants.js';
import ComponentThongTinThietBi from '../ThongTinThietBi/index.vue';
import ComponentDichVuThietBi from './components/DichVuThietBi/index.vue';
import ComponentKhachHangThietBi from './components/KhachHangThietBi/index.vue';

Vue.use(DiagramPlugin);

export default {
  provide: {
    diagram: [RadialTree, DiagramContextMenu],
  },
  name: 'SoDoMucThietBi',
  components: {
    'ejs-diagram': DiagramComponent,
    ComponentThongTinThietBi,
    ComponentDichVuThietBi,
    ComponentKhachHangThietBi
  },
  props: {
    IDC_ID: {
      type: [Number, String],
      default: null
    },
    ListHaTangId: {
      type: Array,
      default: () => []
    }
  },
  computed: {
    diagram: function () {
      if (!(this.$refs.diagram == null || this.$refs.diagram == undefined))
        return this.$refs.diagram;
      return null;
    },
    loaiHienThiListWithAll() {
      if (!this.loaiHienThiList.length) return [];
      return [
        {
          ID: 'ALL',
          LOAI_HATANG: 'ALL',
          TEN: 'Tất cả',
          TENHIENTHI: 'Tất cả'
        },
        ...this.loaiHienThiList
      ];
    }
  },
  data: () => ({
    dsThietBiSet: new Set(),
    dsThietBi: [],
    showThongTinThietBi: false,
    showThongTinCacDichVuChayTrenThietBi: false,
    showDanhSachKhachHangSuDungDichVuChayTrenThietBi: false,
    showThongTinDichvuLienQuanDenThietBi: false,
    showThongTinKhachHangLienQuanDenThietBi: false,
    selectedIDC: 0,
    selectedTypeView: [],
    IDCList: [],
    loaiHienThiList: [],
    diagramData: {
      nodes: [],
      connectors: []
    },
    imageUrls: new Map(),
    isLoading: false,
    thietBiRelationshipCache: new Map(),
    isLoadingConnectors: false, // Thêm flag để tránh gọi API liên tục
    ...DIAGRAM_CONFIG,
    contextMenuSettings: {
      show: true,
      showCustomMenuOnly: true,
      items: [
        {
          text: "Thông tin chi tiết thiết bị",
          id: "CTIET_TBI",
          target: ".e-diagramcontent",
          iconCss: "e-menu-icon text-main -ap icon-info_outline ",
        },
        {
          text: "Hiển thị danh sách các dịch vụ chạy trên thiết bị",
          id: "DVU_TBI",
          target: ".e-diagramcontent",
          iconCss: "e-menu-icon text-main -ap icon-list2",
        },
        {
          text: "Hiển thị danh sách khách hàng sử dụng dịch vụ chạy trên thiết bị",
          id: "KH_TBI",
          target: ".e-diagramcontent",
          iconCss: "e-menu-icon text-main -ap icon-list2",
        },
      ],
    },
    fitOptions: {
      mode: 'Page',
      region: 'Content',
      margin: { left: 50, top: 50, right: 50, bottom: 50 },
    }
  }),
  methods: {
    async getHaTangByIDC() {
      this.resetDiagramData();
      this.isLoading = true;
      try {
        const response = await this.$root.context.post(
          `/web-ecms/hatang/sodo-logic/get-all-hatang-by-idc`,
          { id: this.selectedIDC }
        );
        this.loaiHienThiList = response.data.map(item => ({
          ID: item.ID + "-" + item.LOAI_HATANG,
          LOAI_HATANG: item.LOAI_HATANG,
          TEN: item.TEN,
          TENHIENTHI: item.TEN + ' (' + item.LOAI_HATANG + ')',
        }));
        // Reset selectedTypeView khi load lại danh sách
        this.selectedTypeView = [];
      } catch (error) {
        console.error('Error fetching ha tang by IDC:', error);
        this.loaiHienThiList = [];
        this.$root.showError('Không thể tải dữ liệu hạ tầng', 'Lỗi');
      } finally {
        this.isLoading = false;
      }
    },

    async handleSelectViewType(selected) {
      if (!selected) return;
      
      // Xử lý khi chọn "Tất cả"
      if (selected.some(item => item.ID === 'ALL')) {
        // Nếu đang chọn "Tất cả", select tất cả các option trừ option "Tất cả"
        this.selectedTypeView = this.loaiHienThiList;
      } else if (this.selectedTypeView.length === this.loaiHienThiList.length) {
        // Nếu đã chọn tất cả các option thường, tự động chọn option "Tất cả"
        this.selectedTypeView = [...this.selectedTypeView, this.loaiHienThiListWithAll[0]];
      }

      if (this.selectedIDC && this.selectedTypeView.length > 0) {
        await this.loadDiagramData();
      }
    },

    async getIDCList() {
      this.isLoading = true;
      try {
        const response = await this.$root.context.get("/web-ecms/danhmuc/ha-tang-idc");
        this.IDCList = response?.data || [];
      } catch (error) {
        console.error('Error fetching IDC list:', error);
        this.IDCList = [];
        this.$root.showError('Không thể tải danh sách IDC', 'Lỗi');
      } finally {
        this.isLoading = false;
      }
    },

    async loadDiagramData(idc_id) {
      try {
        this.isLoading = true;
        if (idc_id) {
          this.selectedIDC = idc_id;
        }

        await this.getContainer();
        await this.loadDevicesForContainers();

        this.$nextTick(() => {
          this.fitDiagramToPage();
        });
      } catch (error) {
        console.error('Error loading diagram data:', error);
        this.$root.showError('Không thể tải dữ liệu sơ đồ', 'Lỗi');
      } finally {
        this.isLoading = false;
      }
    },

    fitDiagramToPage() {
      if (this.diagram && this.diagram.ej2Instances) {
        try {
          this.diagram.ej2Instances.fitToPage(this.fitOptions);
          // Chỉ gọi loadConnectors nếu chưa có connectors và chưa đang load
          if (!this.isLoadingConnectors && this.diagramData.connectors.length === 0) {
            this.loadConnectorsThietBiToDiagram();
          }
        } catch (error) {
          console.warn('Không thể thực hiện fitToPage:', error);
        }
      }
    },

    async loadConnectorsThietBiToDiagram() {
      // Tránh gọi API liên tục
      if (this.isLoadingConnectors || this.dsThietBiSet.size === 0) return;
      
      try {
        this.isLoadingConnectors = true;
        
        const uniqueThietBiIds = [...new Set(Array.from(this.dsThietBiSet))];
        const deviceRelationships = [];

        // Sử dụng cache để tránh gọi API trùng lặp
        for (const thietbiId of uniqueThietBiIds) {
          if (this.thietBiRelationshipCache.has(thietbiId)) {
            deviceRelationships.push(this.thietBiRelationshipCache.get(thietbiId));
          } else {
            try {
              const response = await this.$root.context.post(
                `/web-ecms/hatang/sodo-logic/get-thietbi-cha-con`,
                { id: thietbiId }
              );
              const data = response.data;
              this.thietBiRelationshipCache.set(thietbiId, data);
              deviceRelationships.push(data);
            } catch (error) {
              console.error('Error fetching thiet bi cha con:', error);
              this.thietBiRelationshipCache.set(thietbiId, null);
              deviceRelationships.push(null);
            }
          }
        }

        // Lọc bỏ các kết quả null và tạo connectors
        const validRelationships = deviceRelationships.filter(Boolean);
        this.createConnectorsFromRelationships(validRelationships);
      } catch (error) {
        console.error('Error in loadConnectorsThietBiToDiagram:', error);
      } finally {
        this.isLoadingConnectors = false;
      }
    },

    findAllNodesByThietBiId(thietbiId) {
      if (!thietbiId || !this.diagramData?.nodes?.length) return [];

      return this.diagramData.nodes.filter(node =>
        node?.type_node === 'thietbi' && node?.idThietBi === thietbiId
      );
    },

    createConnectorsFromRelationships(relationships) {
      if (!relationships?.length) return;

      const existingConnectorIds = new Set(this.diagramData.connectors.map(c => c.id));
      const newConnectors = [];

      relationships.forEach(relationship => {
        if (!relationship?.length) return;

        relationship.forEach(item => {
          // Tìm tất cả nodes có cùng thietbiId
          const sourceNodes = this.findAllNodesByThietBiId(item.THIETBI_NGUON_ID);
          const targetNodes = this.findAllNodesByThietBiId(item.THIETBI_DICH_ID);
          
          if (sourceNodes.length === 0 || targetNodes.length === 0) return;

          // Nếu chỉ có 1 source và 1 target, tạo connector thông thường
          if (sourceNodes.length === 1 && targetNodes.length === 1) {
            const connectorId = `${item.THIETBI_NGUON_ID}_${item.THIETBI_DICH_ID}_connector`;
            
            if (!existingConnectorIds.has(connectorId)) {
              newConnectors.push({
                id: connectorId,
                sourceID: sourceNodes[0].id,
                targetID: targetNodes[0].id,
                type: 'Straight',
                style: {
                  ...CONNECTOR_DEFAULTS.style,
                  strokeWidth: 1
                },
                sourcePortID: item.THIETBI_NGUON_ID,
                targetPortID: item.THIETBI_DICH_ID,
                targetDecorator: { shape: 'None' }
              });
              existingConnectorIds.add(connectorId);
            }
          } else {
            // Nếu có nhiều instances, tạo connector cho từng cặp
            sourceNodes.forEach((sourceNode, sourceIndex) => {
              targetNodes.forEach((targetNode, targetIndex) => {
                const connectorId = `${sourceNode.id}_${targetNode.id}_connector`;
                
                if (!existingConnectorIds.has(connectorId)) {
                  newConnectors.push({
                    id: connectorId,
                    sourceID: sourceNode.id,
                    targetID: targetNode.id,
                    type: 'Straight',
                    style: {
                      ...CONNECTOR_DEFAULTS.style,
                      strokeWidth: 1,
                      // strokeDashArray: sourceNodes.length > 1 || targetNodes.length > 1 ? '5,5' : '', // Đường đứt nét cho multiple instances
                    },
                    sourcePortID: item.THIETBI_NGUON_ID,
                    targetPortID: item.THIETBI_DICH_ID,
                    targetDecorator: { shape: 'None' }
                  });
                  existingConnectorIds.add(connectorId);
                }
              });
            });
          }
        });
      });

      // Chỉ cập nhật nếu có connectors mới
      if (newConnectors.length > 0) {
        this.diagramData.connectors = [
          ...this.diagramData.connectors,
          ...newConnectors
        ];

        // Cập nhật diagram
        if (this.diagram?.ej2Instances) {
          this.diagram.ej2Instances.dataBind();
        }
      }
    },

    findNodeByThietBiId(thietbiId) {
      if (!thietbiId || !this.diagramData?.nodes?.length) return null;

      const matchingNodes = this.diagramData.nodes.filter(node =>
        node?.type_node === 'thietbi' && node?.idThietBi === thietbiId
      );

      return matchingNodes[0] || null; // Trả về node đầu tiên hoặc null
    },

    resetDiagramData() {
      this.imageUrls.forEach(url => {
        if (url && url.startsWith('blob:')) {
          URL.revokeObjectURL(url);
        }
      });

      this.diagramData = { nodes: [], connectors: [] };
      this.imageUrls.clear();
      this.dsThietBiSet.clear();
      this.dsThietBi = [];
      // Reset cache và flag
      this.thietBiRelationshipCache.clear();
      this.isLoadingConnectors = false;
      
      if (this.diagram && this.diagram.ej2Instances) {
        this.diagram.ej2Instances.selectedItems.nodes = [];
        this.diagram.ej2Instances.selectedItems.connectors = [];
        this.diagram.ej2Instances.selectedItems.groups = [];
        this.diagram.ej2Instances.clear();
      }
    },

    async loadDevicesForContainers() {
      const containerList = this.diagramData.nodes.filter(n => n.type_node === 'container');
      const devicePromises = containerList.map(container => {
        const [containerId, kieu] = container.id.split('_');
        return this.getThietBiByContainer(containerId, kieu);
      });
      await Promise.all(devicePromises);
    },

    getKieuId(loaiHaTang) {
      switch (loaiHaTang) {
        case 'HATANGMANG':
          return 1;
        case 'HATANGAOHOA':
          return 2;
        case 'HATANGIDG':
          return 3;
        case 'HATANGSMARTCLOUD':
          return 4;
        default:
          return 0;
      }
    },

    createBlobUrl(blob) {
      if (!blob) return null;

      if (typeof blob === 'string' && blob.startsWith('data:')) {
        return blob;
      }

      if (typeof blob === 'string' && !blob.startsWith('data:')) {
        return `data:image/*;base64,${blob}`;
      }

      if (blob instanceof Blob || blob instanceof File) {
        const url = URL.createObjectURL(blob);
        return url;
      }

      try {
        const newBlob = new Blob([blob], { type: 'image/*' });
        const url = URL.createObjectURL(newBlob);
        return url;
      } catch (error) {
        console.error('Error creating blob URL:', error);
        return null;
      }
    },

    createDeviceNode(item, container, index, kieu) {
      const devicesPerRow = 3;
      const rowIndex = Math.floor(index / devicesPerRow);
      const colIndex = index % devicesPerRow;

      let imageUrl = null;
      if (item.ANH_DAI_DIEN) {
        if (!this.imageUrls.has(item.ID)) {
          const url = this.createBlobUrl(item.ANH_DAI_DIEN);
          if (url) {
            this.imageUrls.set(item.ID, url);
          }
        }
        imageUrl = this.imageUrls.get(item.ID);
      }

      return {
        id: `${item.ID}_${kieu}_thietbi`,
        idThietBi: item.THIETBI_ID,
        type: item.LOAI_HATANG,
        type_node: 'thietbi',
        width: 60,
        height: 60,
        offsetX: container.offsetX - container.width / 3 + colIndex * 100,
        offsetY: container.offsetY - container.height / 3 + rowIndex * 80 + 30,
        shape: {
          type: 'Image',
          source: imageUrl || '',
          scale: 'Meet'
        },
        tooltip: {
          content: item.TENTHIETBI.toString().toUpperCase(),
        },
        style: {
          fill: COLORS.thietBi.fill[this.getKieuId(kieu) - 1],
          strokeColor: COLORS.thietBi.stroke[this.getKieuId(kieu) - 1]
        },
        constraints: NodeConstraints.Default & ~NodeConstraints.Drag & ~NodeConstraints.Rotate & ~NodeConstraints.Resize,
        annotations: [{
          content: item.TENTHIETBI.toString().toUpperCase(),
          style: { color: 'black', bold: true, fontSize: 10 },
          offset: { x: 0.5, y: 1.2 }
        }]
      };
    },

    async getThietBiByContainer(containerId, kieu) {
      try {
        const response = await this.$root.context.post(
          `/web-ecms/hatang/sodo-logic/get-danhsach-thietbi`,
          {
            id: containerId.split(
              '-'
            )[0],
            kieu: this.getKieuId(kieu)
          }
        );

        const container = this.diagramData.nodes.find(
          n => n.id === `${containerId}_${kieu}_container`
        );

        if (!container) return;

        response.data.forEach((item, index) => {
          const existingDevice = this.diagramData.nodes.find(
            n => n.id === `${item.ID}_${kieu}_thietbi` && n.type_node === 'thietbi'
          );

          if (!existingDevice) {
            const deviceNode = this.createDeviceNode(item, container, index, kieu);
            this.diagramData.nodes.push(deviceNode);
          }
          this.dsThietBiSet.add(item.THIETBI_ID);
          this.dsThietBi.push(item);
        });
      } catch (error) {
        console.error('Error fetching thiet bi:', error);
      }
    },

    createContainerNode(item, index, loaiHaTang) {
      const loaiIndex = this.getKieuId(loaiHaTang) - 1;

      return {
        id: `${item.ID}_${loaiHaTang}_container`,
        type: loaiHaTang,
        type_node: 'container',
        width: 400,
        height: 200,
        offsetX: 400,
        offsetY: 200 + loaiIndex * 250,
        style: {
          fill: COLORS.container.fill[this.getKieuId(loaiHaTang)],
          strokeColor: COLORS.container.stroke[this.getKieuId(loaiHaTang)]
        },
        constraints: NodeConstraints.Default & NodeConstraints.Tooltip,
        annotations: [{
          content: item.TEN.toString().toUpperCase(),
          style: { color: 'black', bold: true },
          offset: { x: 0.5, y: 0.1 }
        }]
      };
    },

    async getContainer() {
      try {
        this.resetDiagramData();

        const groupedByType = {};
        this.selectedTypeView.forEach(item => {
          if (!groupedByType[item.LOAI_HATANG]) {
            groupedByType[item.LOAI_HATANG] = [];
          }
          groupedByType[item.LOAI_HATANG].push(item);
        });

        const sortedTypes = Object.keys(groupedByType).sort((a, b) =>
          this.getKieuId(a) - this.getKieuId(b)
        );

        sortedTypes.forEach(loaiHaTang => {
          groupedByType[loaiHaTang].forEach((item, index) => {
            const existingContainer = this.diagramData.nodes.find(
              n => n.id === `${item.ID}_${item.LOAI_HATANG}_container` && n.type_node === 'container'
            );

            if (!existingContainer) {
              console.log(item, 'hieu check')
              const containerNode = this.createContainerNode(item, index, item.LOAI_HATANG);
              containerNode.offsetX = 400 + index * 420;
              this.diagramData.nodes.push(containerNode);
            }
          });
        });

        return this.selectedTypeView;
      } catch (error) {
        console.error('Error fetching diagram data:', error);
        this.resetDiagramData();
        throw error;
      }
    },

    beforeDestroy() {
      this.imageUrls.forEach(url => {
        if (url && url.startsWith('blob:')) {
          URL.revokeObjectURL(url);
        }
      });
      this.imageUrls.clear();

      if (this.diagram && this.diagram.ej2Instances) {
        this.diagram.ej2Instances.destroy();
      }
    },

    diagram_contextMenuOpen(args) {
      if (this.diagram.ej2Instances.selectedItems.nodes.length > 0) {
        let selectedItem = this.diagram.ej2Instances.selectedItems.nodes[0];
        if (selectedItem.type_node !== 'thietbi') {
          args.cancel = true;
        }
      } else {
        args.cancel = true;
      }
    },

    diagram_contextMenuClick(args) {
      switch (args.item.id) {
        case 'CTIET_TBI':
          this.showThongTinChiTietThietBi(this.diagram.ej2Instances.selectedItems.nodes[0].idThietBi);
          break;
        case 'DVU_TBI':
          this.hienThiThongTinCacDichVuChayTrenThietBi(this.diagram.ej2Instances.selectedItems.nodes[0].idThietBi);
          break;
        case 'KH_TBI':
          this.hienThiDanhSachKhachHangSuDungDichVuChayTrenThietBi(this.diagram.ej2Instances.selectedItems.nodes[0].idThietBi);
          break;
      }
    },

    showThongTinChiTietThietBi(args) {
      if (!args) {
        this.$root.showError('Không tìm thấy thông tin thiết bị', 'Lỗi');
        return;
      }

      this.showThongTinThietBi = true;
      this.$refs.modalChiTietThietBi.show();

      this.$nextTick(() => {
        if (this.$refs.thongTinThietBi) {
          this.$refs.thongTinThietBi.loadThongTinThietBi(args);
        } else {
          console.error('Không tìm thấy component thông tin thiết bị');
        }
      });
    },

    hienThiThongTinCacDichVuChayTrenThietBi(args) {
      if (!this.diagram || !this.diagram.ej2Instances ||
        !this.diagram.ej2Instances.selectedItems.nodes.length) {
        this.$root.showError('Vui lòng chọn một thiết bị', 'Lỗi');
        return;
      }

      this.showThongTinDichvuLienQuanDenThietBi = true;
      this.$refs.modalDichvuThietBi.show();
      this.$nextTick(() => {
        if (this.$refs.DichvuThietBi) {
          this.$refs.DichvuThietBi.show(args);
        } else {
          console.error('Không tìm thấy component thông tin thiết bị');
        }
      });
    },

    hienThiDanhSachKhachHangSuDungDichVuChayTrenThietBi(args) {
      if (!this.diagram || !this.diagram.ej2Instances ||
        !this.diagram.ej2Instances.selectedItems.nodes.length) {
        this.$root.showError('Vui lòng chọn một thiết bị', 'Lỗi');
        return;
      }

      this.showThongTinKhachHangLienQuanDenThietBi = true;
      this.$refs.modalKhachHangThietBi.show();
      this.$nextTick(() => {
        if (this.$refs.KhachHangThietBi) {
          this.$refs.KhachHangThietBi.show(args);
        } else {
          console.error('Không tìm thấy component thông tin thiết bị');
        }
      });
    },

    async loadDiagramFromPopup(idc, listHaTangId) {
      this.selectedIDC = idc;
      await this.getHaTangByIDC();
      this.selectedTypeView = []
      listHaTangId.forEach(item => {
        const selectedItem = this.loaiHienThiList.find(i => i.ID === item);
        if (selectedItem) {
          this.selectedTypeView.push(selectedItem);
        }
      });

      await this.$nextTick();
      this.resetDiagramData();

      setTimeout(async () => {
        await this.loadDiagramData(this.selectedIDC);
        if (this.diagram && this.diagram.ej2Instances) {
          this.diagram.ej2Instances.fitToPage({
            mode: 'Page',
            region: 'Content',
            margin: { left: 50, top: 50, right: 50, bottom: 50 }
          });
        }
        if (this.diagram && this.diagram.ej2Instances) {
          this.diagram.ej2Instances.zoomTo({
            type: 'ZoomIn',
            zoomFactor: 0.2
          });
        }
      }, 200);
    },
  },

  async created() {
    await this.getIDCList();

    if (this.IDC_ID) {
      this.selectedIDC = parseInt(this.IDC_ID);
      await this.getHaTangByIDC();

      if (this.ListHaTangId && this.ListHaTangId.length > 0) {
        await this.$nextTick();

        const selectedItems = this.loaiHienThiList.filter(item =>
          this.ListHaTangId.includes(item.ID) ||
          this.ListHaTangId.includes(item.ID.toString())
        );

        if (selectedItems.length > 0) {
          this.selectedTypeView = selectedItems;
          await this.loadDiagramData();
        }
      }
    }
  },

  watch: {
    selectedTypeView: {
      handler(newVal) {
        if (this.selectedIDC && newVal && newVal.length > 0) {
          this.loadDiagramData();
        } else if (newVal.length === 0) {
          this.resetDiagramData();
        }
      }
    },
    selectedIDC: {
      handler(newVal) {
        if (newVal) {
          this.selectedTypeView = [];
          this.loaiHienThiList = [];
          this.resetDiagramData();
          this.getHaTangByIDC();
        }
      }
    },
  },

  mounted() {
  },

  updated() {
    // Chỉ fit diagram khi có nodes và không đang loading
    if (this.diagram && this.diagram.ej2Instances &&
      this.diagramData.nodes.length > 0 && !this.isLoading && !this.isLoadingConnectors) {
      // Chỉ fit nếu chưa có connectors
      if (this.diagramData.connectors.length === 0) {
        this.fitDiagramToPage();
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.so-do-muc-thiet-bi {
  padding: 20px;
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;

  .diagram-container {
    margin-top: 20px;
    border: 1px solid #ddd;
    background-color: #fff;
    min-height: 800px;
    flex: 1;
    display: flex;
    flex-direction: column;
    position: relative;

    .loading-overlay {
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background-color: rgba(255, 255, 255, 0.7);
      display: flex;
      justify-content: center;
      align-items: center;
      z-index: 100;
    }

    ::v-deep .e-diagram {
      width: 100% !important;
      height: 100% !important;
    }

    ::v-deep .e-diagram-container {
      width: 100% !important;
      height: 100% !important;
    }
  }
}
</style>
