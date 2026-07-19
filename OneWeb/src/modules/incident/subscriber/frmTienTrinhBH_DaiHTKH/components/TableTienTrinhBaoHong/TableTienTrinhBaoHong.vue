<template>
  <div
    class="box-form"
    @contextmenu.prevent.stop="handleContextClick($event, null)"
  >
    <vue-simple-context-menu
      :elementId="'contextMenuTableTienTrinhHong'"
      :options="optionsContextMenu"
      ref="contextMenu"
      @option-clicked="onOptionClick"
    />
    <div class="legend-title">Tiến trình báo hỏng</div>

    <div class="table-content relative" style="height: 300px;">
      <table class="table-result table-gachle">
        <thead class="center">
          <tr>
            <th class="w20"></th>
            <th>Người CN</th>
            <th>Ngày CN</th>
            <th>Nội dung</th>
            <th>Đơn vị</th>
            <th>SMS</th>
            <th>Sửa</th>
            <th>Xoá</th>
          </tr>
          <tr>
            <th class="w20"></th>
            <th>
              <div class="input-icon-right">
                <input type="text" class="form-control" />
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
            </th>
            <th>
              <div class="input-icon-right">
                <input type="text" class="form-control" />
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
            </th>
            <th>
              <div class="input-icon-right">
                <input type="text" class="form-control" />
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
            </th>
            <th>
              <div class="input-icon-right">
                <input type="text" class="form-control" />
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
            </th>
            <th>
              <div class="input-icon-right">
                <input type="text" class="form-control" />
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
            </th>
            <th>
              <div class="input-icon-right">
                <input type="text" class="form-control" />
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
            </th>
            <th>
              <div class="input-icon-right">
                <input type="text" class="form-control" />
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="tienTrinhBaoHongList.length === 0">
            <th class="w20 center"></th>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
          </tr>
          <tr
            v-for="(item, index) in tienTrinhBaoHongList"
            :key="index"
            @contextmenu.prevent.stop="handleContextClick($event, item)"
          >
            <th class="w20 center"></th>
            <td>{{ item.nguoi_cn }}</td>
            <td>{{ item.ngay_cn }}</td>
            <td>{{ item.noidung }}</td>
            <td>{{ item.donvi_id }} {{ item.donvi_cn }}</td>
            <td class="center">
              <button class="btn btn-main" @click="smsTienTrinhBaoHong(item)">
                STC
              </button>
            </td>
            <td class="center">
              <button
                class="btn btn-main lh16 -ap icon-more_horiz"
                @click="editTienTrinhBaoHong(item)"
              />
            </td>
            <td class="center">
              <button
                class="btn btn-main lh16 -ap icon-close"
                @click="deleteTienTrinhBaoHong(item)"
              />
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Modals -->
    <PopupGuiSMSTienTrinhBaoHong
      :show.sync="showModal.guiSMSTienTrinhBaoHong"
      :selectedRow="selectedRow"
    />
    <PopupNhapTienTrinhBaoHong
      :show.sync="showModal.nhapTienTrinhBaoHong"
      :tienTrinhBaoHong="selectedTienTrinhBaoHong"
      :phieuYeuCau="selectedRow"
      @submit:add="onNewTienTrinhBaoHong"
      @submit:edit="onEditTienTrinhBaoHong"
    />
  </div>
</template>

<script>
import PopupGuiSMSTienTrinhBaoHong from "../PopupGuiSMSTienTrinhBaoHong";
import PopupNhapTienTrinhBaoHong from "../PopupNhapTienTrinhBaoHong";
import VueSimpleContextMenu from "vue-simple-context-menu";
import "vue-simple-context-menu/dist/vue-simple-context-menu.css";
import api from "../../api";
export default {
  name: "TableTienTrinhBaoHong",
  props: {
    selectedRow: {
      type: Object,
      default: () => {}
    }
  },
  components: {
    PopupGuiSMSTienTrinhBaoHong,
    PopupNhapTienTrinhBaoHong,
    "vue-simple-context-menu": VueSimpleContextMenu
  },
  watch: {
    async selectedRow(newVal) {
      const { baohong_id } = newVal;
      this.tienTrinhBaoHongList = [];
      this.selectedTienTrinhBaoHong = {};
      if (baohong_id) {
        await this.getTienTrinhBaoHong(baohong_id);
      }
    }
  },
  data() {
    return {
      showModal: {
        guiSMSTienTrinhBaoHong: false,
        nhapTienTrinhBaoHong: false
      },
      selectedTienTrinhRow: null,
      optionsContextMenu: [
        // {
        //   name: "Nhập mới",
        //   slug: "new"
        // },
        {
          name: "Gửi tin nhắn",
          slug: "sms"
        },
        {
          name: "Sửa nội dung",
          slug: "edit"
        },
        {
          name: "Xóa",
          slug: "delete"
        }
      ],
      tienTrinhBaoHongList: [],
      selectedTienTrinhBaoHong: {}
    };
  },
  methods: {
    async getTienTrinhBaoHong(baohong_id) {
      this.$root.showLoading(true);
      this.tienTrinhBaoHongList = [];
      try {
        const payload = {
          baohong_id
        };
        const response = await api.getDSTienTrinhBaoHong(
          this.$root.axios,
          payload
        );
        const { data, error_code } = response.data;
        if (error_code === "BSS-00000000") {
          this.tienTrinhBaoHongList = data;
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.$root.showLoading(false);
      }
    },
    rowSelected(row) {
      this.$emit("update:selectedRow", row.data);
    },
    handleContextClick(event, item) {
      this.$refs.contextMenu.showMenu(event, item);
    },
    onOptionClick(event) {
      if (this.selectedRow && this.selectedRow.baohong_id) {
        const { option, item } = event;
        switch (option.slug) {
          case "new":
            this.showModal.nhapTienTrinhBaoHong = true;
            break;
          case "sms":
            this.smsTienTrinhBaoHong(item);
            break;
          case "edit":
            this.editTienTrinhBaoHong(item);
            break;
          case "delete":
            this.deleteTienTrinhBaoHong(item);
            break;

          default:
            break;
        }
      } else this.$toast.error("Bạn chưa chọn phiếu yêu cầu", "title");
    },
    smsTienTrinhBaoHong(item = null) {
      if (item) {
        this.selectedTienTrinhRow = item;
        this.showModal.guiSMSTienTrinhBaoHong = true;
      }
    },
    editTienTrinhBaoHong(item = null) {
      if (item) {
        this.showModal.nhapTienTrinhBaoHong = true;
        this.selectedTienTrinhBaoHong = item;
      }
    },
    async deleteTienTrinhBaoHong(item = null) {
      if (item) {
        try {
          const { baohong_id, tientrinhbh_id } = item;
          const confirmDelete = await this.$confirm(
            "Bạn thực sự muốn xóa dữ liệu không?",
            "Thông báo",
            {
              confirmButtonText: "Đồng ý",
              cancelButtonText: "Hủy",
              type: "warning"
            }
          );
          if (confirmDelete) {
            const payload = {
              baohong_id,
              tientrinh_id: tientrinhbh_id
            };
            const response = await api.deleteTienTrinhBaoHong(
              this.$root.axios,
              payload
            );
            const { error_code, message } = response.data;
            if (error_code === "BSS-00000000") {
              this.$toast.success(message, "title");
              this.getTienTrinhBaoHong(this.selectedRow.baohong_id);
            } else this.$toast.error(message, "title");
          }
        } catch (error) {
          if (error === "error") return;
        }
      }
    },
    async onEditTienTrinhBaoHong() {
      await this.getTienTrinhBaoHong(this.selectedRow.baohong_id);
    },
    async onNewTienTrinhBaoHong() {
      await this.getTienTrinhBaoHong(this.selectedRow.baohong_id);
    }
  }
};
</script>

<style>
#contextMenuTableTienTrinhHong {
  position: fixed;
}
</style>
