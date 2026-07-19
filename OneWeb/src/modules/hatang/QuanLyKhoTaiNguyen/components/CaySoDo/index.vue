<template>
  <div class="cay-don-vi"
    style="overflow: auto; max-height: 660px; min-height: 85px; overflow-x: auto; max-width: 100%;">
    <div class="form-group">
      <div class="input-group">
        <input type="text" ref="textBoxSearch" placeholder="Tìm kiếm..." class="form-control" @keyup="handleSearchInput"
          @focus="showSearchResults = true" @blur="hideSearchResults">
        <div class="input-group-append">
          <button class="btn btn-search" @click="btnSearch_click"><span class="icon one-search"></span></button>
          <button class="btn btn-reload" @click="btnReload_click"><span
              class="nc-icon-outline arrows-1_refresh-68"></span></button>
        </div>
      </div>
      <div v-if="showSearchResults && searchResults.length > 0" class="search-results">
        <div v-for="result in searchResults" :key="result.id" class="search-result-item"
          @mousedown="selectSearchResult(result)">
          {{ result.name }}
        </div>
      </div>
    </div>
    <div class="e-tree-plus cay-sodo-tb">
      <ejs-treeview ref="tree" :fields="treeFields" :allowDragAndDrop="false" :allowMultiSelection="false"
        :showCheckBox="false" @nodeSelected="tree_nodeSelected" @nodeExpanded="tree_nodeExpanded"
        @nodeCollapsed="tree_nodeCollapsed">
      </ejs-treeview>
    </div>
  </div>
</template>

<script>
import ThietBi from "./ThietBi";
import { TreeView } from '@syncfusion/ej2-navigations';

export default {
  name: "CaySoDo",
  data() {
    return {
      treeFields: {
        dataSource: [],
        id: 'id',
        text: 'name',
        child: 'children',
        parentID: 'parentId',
        hasChildren: 'hasChild',
        expanded: 'expanded',
        iconCss: 'iconClass'
      },
      treeData: [],
      searchResults: [],
      showSearchResults: false
    };
  },
  methods: {
    async loadData() {
      try {
        console.log('Bắt đầu load dữ liệu cây sơ đồ');
        const response = await this.$root.context.post('/web-ecms/hatang/sodo-logic/get-cay-thietbi', {
          id: 0
        });
        if (response.error === '200') {
          console.log('Dữ liệu gốc:', response.data);
          const transformedData = this.transformData(response.data);
          console.log('Dữ liệu sau transform:', transformedData);

          this.treeData = transformedData;
          this.treeFields = {
            ...this.treeFields,
            dataSource: transformedData
          };

          console.log('Hoàn thành load toàn bộ dữ liệu');
        }
      } catch (error) {
        console.error('Lỗi khi tải dữ liệu cây sơ đồ:', error);
      }
    },

    transformData(data) {
      const nodeMap = new Map();

      data.forEach(item => {
        const nodeId = `${item.ITEM_TYPE}_${item.ITEM_ID}`;

        // Nếu node chưa tồn tại, tạo mới
        if (!nodeMap.has(nodeId)) {
          nodeMap.set(nodeId, {
            id: nodeId,
            name: `${item.NAME} (${ThietBi.getText(item.ITEM_TYPE, item.SUB_TYPE)})`,
            type: item.ITEM_TYPE,
            itemId: item.ITEM_ID,
            parents: [], // Mảng chứa tất cả các parent
            hasChild: false,
            expanded: false,
            children: [],
            isLoaded: false,
            iconClass: this.getNodeIconClass(item.ITEM_TYPE, item.SUB_TYPE)
          });
        }

        // Thêm thông tin parent vào node
        if (item.PARENT_TYPE && item.PARENT_ID) {
          const parentId = `${item.PARENT_TYPE}_${item.PARENT_ID}`;
          const node = nodeMap.get(nodeId);
          if (!node.parents.includes(parentId)) {
            node.parents.push(parentId);
          }
        }
      });

      const cloneNode = (node, parentPath = new Set()) => {
        // Tránh vòng lặp vô hạn
        if (parentPath.has(node.id)) {
          return null;
        }

        const newNode = { ...node };
        newNode.children = [];

        // Tìm tất cả các node con
        nodeMap.forEach((childNode) => {
          if (childNode.parents.includes(node.id)) {
            const newParentPath = new Set(parentPath);
            newParentPath.add(node.id);

            const clonedChild = cloneNode(childNode, newParentPath);
            if (clonedChild) {
              newNode.hasChild = true;
              newNode.children.push(clonedChild);
            }
          }
        });

        return newNode;
      };

      const rootNodes = [];
      nodeMap.forEach((node) => {
        const isRoot = node.parents.length === 0 || node.parents.every(parentId => !nodeMap.has(parentId));
        if (isRoot) {
          const clonedRoot = cloneNode(node);
          if (clonedRoot) {
            rootNodes.push(clonedRoot);
          }
        }
      });

      return rootNodes;
    },

    handleSearchInput(event) {
      const searchText = event.target.value.toLowerCase();
      if (!searchText) {
        this.searchResults = [];
        return;
      }

      // Tìm kiếm gần đúng trong cây
      const results = [];
      const searchInTree = (nodeList) => {
        for (const node of nodeList) {
          // Kiểm tra node hiện tại
          if (node.name.toLowerCase().includes(searchText)) {
            results.push({
              id: node.id,
              name: node.name,
              node: node
            });
          }
          // Tìm kiếm trong các node con
          if (node.children && node.children.length > 0) {
            searchInTree(node.children);
          }
        }
      };

      searchInTree(this.treeFields.dataSource);
      this.searchResults = results;
    },

    hideSearchResults() {
      setTimeout(() => {
        this.showSearchResults = false;
      }, 200);
    },

    selectSearchResult(result) {
      if (!this.$refs.tree || !this.$refs.tree.ej2Instances) return;

      console.log("Đang chọn node từ kết quả tìm kiếm:", result);
      const treeObj = this.$refs.tree.ej2Instances;

      try {
        // Lấy ID của node cần chọn
        const nodeId = result.node.id;

        // Phân tích cấu trúc cây và thu thập tất cả ID của các node cha
        let allParentIds = this.getAllParentIds(result.node);
        console.log("Các node cha cần mở rộng:", allParentIds);

        // Thu gọn tất cả các node hiện tại
        treeObj.collapseAll();

        // Thiết lập các node cha là expanded trong dữ liệu gốc
        for (const parentId of allParentIds) {
          const parentNode = this.findNode(this.treeData, parentId);
          if (parentNode) {
            parentNode.expanded = true;
          }
        }

        // Cập nhật dataSource với expanded flags đã được cập nhật
        this.treeFields = {
          ...this.treeFields,
          dataSource: JSON.parse(JSON.stringify(this.treeData))
        };

        // Thiết lập expandedNodes trực tiếp thay vì gọi expandAll
        treeObj.expandedNodes = allParentIds;

        // Đợi các node được mở rộng xong
        setTimeout(() => {
          // Đảm bảo các node vẫn được mở rộng
          if (allParentIds.length > 0 && !treeObj.expandedNodes.includes(allParentIds[0])) {
            console.log("Node cha không được mở rộng, thử lại...");
            treeObj.expandedNodes = allParentIds;
          }

          // Chọn node đích
          treeObj.selectedNodes = [nodeId];

          // Cuộn đến node được chọn
          const selectedElement = document.querySelector(`[data-uid="${nodeId}"]`);
          if (selectedElement) {
            selectedElement.scrollIntoView({ behavior: 'smooth', block: 'center' });
          }
        }, 300);

        // Đóng kết quả tìm kiếm
        this.showSearchResults = false;
        this.$refs.textBoxSearch.value = '';
        this.searchResults = [];
      } catch (error) {
        console.error("Lỗi trong selectSearchResult:", error);
      }
    },

    btnSearch_click() {
      const searchText = this.$refs.textBoxSearch.value.toLowerCase();
      if (searchText) {
        this.handleSearchInput({ target: { value: searchText } });
      }
    },

    btnReload_click() {
      this.loadData();
    },

    tree_nodeSelected(args) {
      if (args && args.nodeData) {
        console.log(args.nodeData);
        const [type, id] = args.nodeData.id.split('_');
        let parentType = null;
        let parentId = null;
        if (args.nodeData.parentID) {
          const parts = args.nodeData.parentID.split('_');
          if (parts.length >= 2) {
            parentType = parts[0];
            parentId = parts[1];
          }
        }
        console.log(args, 'hieunph')
        this.$emit('nodeSelected', {
          id: args.nodeData.id.split('_')[1],
          type: type,
          itemId: parseInt(id),
          name: args.nodeData.name,
          parentId: args.nodeData.parentId,
          parentType: parentType,
          parentItemId: parseInt(parentId),
          hasChild: args.nodeData.hasChild,
          expanded: args.nodeData.expanded
        });
      }
    },

    async tree_nodeExpanded(args) {
      if (args && args.nodeData) {
        const node = this.findNode(this.treeData, args.nodeData.id);
        if (node) {
          node.expanded = true;
        }
      }
    },

    tree_nodeCollapsed(args) {
      if (args && args.nodeData) {
        const node = this.findNode(this.treeData, args.nodeData.id);
        if (node) {
          node.expanded = false;
        }
      }
    },

    findNode(nodes, id) {
      for (const node of nodes) {
        if (node.id === id) return node;
        if (node.children && node.children.length > 0) {
          const found = this.findNode(node.children, id);
          if (found) return found;
        }
      }
      return null;
    },

    getNodeIconClass(type, subType) {
      switch (type) {
        case 'IDC':
          return 'donvi_1';
        case 'HTM':
          return 'ht_network';
        case 'HTAH':
          return 'ht_aohoa';
        case 'HTIDG':
          return 'ht_idg';
        case 'HTCLOUD':
          return 'ht_smartcloud';
        case 'THIETBI':
          switch (subType) {
            case 'MANG':
              return 'ht_device_network';
            case 'MAYCHU':
              return 'ht_device_server';
            case 'TUDIA':
              return 'ht_device_tudia';
            case 'IDC':
              return 'ht_device_idc';
            case 'KHAYDIA':
              return 'ht_device_khaydia';
            case 'CONTROLLER':
              return 'ht_device_controller';
            case 'KHAC':
              return 'ht_device';
            default:
              return 'ht_device';
          }
        case 'PORT':
          switch (subType) {
            case 'INTERFACE':
              return 'ht_port_interface';
            case 'POWER':
              return 'ht_port_power';
            case 'CONSOLE':
              return 'ht_port_console';
            default:
              return 'ht_port';
          }
        case 'RACK':
          return 'turack';
        case 'MATSAN':
          return 'ewsd';
        case 'TOANHA':
          return 'donvi_2';
        case 'ZONE':
          return 'donvi_3';
        case 'ZONE-VRF':
          return 'ht_zone_vrf';
        case 'VRF':
          return 'ht_vrf';
        case 'VLAN':
          return 'ht_vlan';
        case 'SUBNET':
          return 'ht_subnet';
        case 'IP-STORE':
          return 'ht_ipstore';
        case 'IP-ADDRESS':
          return 'ht_ipaddress';
        default:
          return 'one-folder';
      }
    },

    // Phương thức getAllParentIds được cải tiến để xử lý nhiều node cha
    getAllParentIds(node) {
      // Mảng chứa tất cả ID của node cha
      const parentIds = [];
      // Biến tạm để tránh lặp vô hạn
      const processedIds = new Set();

      const collectParentIds = (currentNode) => {
        // Không xử lý node đã xử lý rồi để tránh lặp vô hạn
        if (!currentNode || processedIds.has(currentNode.id)) {
          return;
        }

        processedIds.add(currentNode.id);

        // Xử lý parentId trực tiếp
        if (currentNode.parentId) {
          if (!parentIds.includes(currentNode.parentId)) {
            parentIds.push(currentNode.parentId);
          }

          // Tìm node cha trong cây
          const parentNode = this.findNode(this.treeData, currentNode.parentId);
          if (parentNode) {
            // Đánh dấu node cha là đã mở rộng trong dữ liệu gốc
            parentNode.expanded = true;
            // Tiếp tục thu thập các node cha cao hơn
            collectParentIds(parentNode);
          }
        }

        // Xử lý trường hợp có nhiều node cha (nếu có)
        if (currentNode.parents && Array.isArray(currentNode.parents)) {
          for (const parentId of currentNode.parents) {
            if (!parentIds.includes(parentId)) {
              parentIds.push(parentId);
            }

            const parentNode = this.findNode(this.treeData, parentId);
            if (parentNode) {
              parentNode.expanded = true;
              collectParentIds(parentNode);
            }
          }
        }
      };

      // Bắt đầu từ node hiện tại
      collectParentIds(node);

      return parentIds;
    },
  },
  mounted() {
    this.loadData();
  }
};
</script>

<style>
.cay-don-vi {
  border: 1px solid #ddd;
  border-radius: 4px;
  padding: 10px;
  overflow: scroll;
}

.e-tree-plus {
  width: 100%;
}

.e-treeview {
  width: 100%;
}

.btn-search,
.btn-reload {
  background-color: #f8f9fa;
  border: 1px solid #ddd;
  padding: 6px 12px;
}

.btn-search:hover,
.btn-reload:hover {
  background-color: #e9ecef;
}

.e-treeview .e-list-item {
  padding: 5px 0;
  width: 100%;
}

.e-treeview .e-text-content {
  font-size: 14px;
}

.e-treeview .e-list-text {
  padding-left: 10px !important;
}

/* Style cho dropdown kết quả tìm kiếm */
.search-results {
  position: absolute;
  z-index: 1000;
  width: 71%;
  max-height: 200px;
  overflow-y: auto;
  background-color: white;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-top: 2px;
}

.search-result-item {
  padding: 8px 12px;
  cursor: pointer;
  border-bottom: 1px solid #eee;
}

.search-result-item:hover {
  background-color: #f5f5f5;
}

.search-result-item:last-child {
  border-bottom: none;
}

/* Thêm style cho tree node và icon */
.tree-node {
  display: flex;
  align-items: center;
  gap: 8px;
}

.node-icon {
  font-size: 14px;
  width: 16px;
  text-align: center;
}

/* Icon styles */
.icon {
  font-size: 16px;
  width: 20px;
  height: 20px;
  display: inline-block;
  vertical-align: middle;
  margin-right: 5px;
}

/* Màu sắc cho các icon */
.one-network {
  color: #2196F3;
}

.one-shield {
  color: #4CAF50;
}

.one-server {
  color: #FF9800;
}

.one-cloud {
  color: #03A9F4;
}

.one-device {
  color: #607D8B;
}

.one-folder {
  color: #FFC107;
}
</style>
