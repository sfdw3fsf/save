<template src="./PopupDSThongTinThanhVienDangKy.html"></template>

<script>
import { typeMemberAction } from "../..";
import api from "../../api";
import TableMembers from "./TableMembers.vue";
export default {
  name: "PopupDSThongTinThanhVienDangKy",
  components: {
    TableMembers
  },
  props: {
    dsMemberTrangThai: {
      type: Array,
      default: () => []
    },
    dsTinhThanh: {
      type: Array,
      default: () => []
    },
    agent_id: {
      type: String,
      default: ""
    },
    show: {
      type: Boolean,
      default: false
    },
    row: {
      type: [Object, null],
      default: () => {}
    }
  },
  data() {
    return {
      modal: null,
      memberList: [],
      selectedMember: null,
      member: {
        ID: "",
        NAME: "",
        USERNAME: "",
        STATUSUSER: "",
        PASSWORD: "",
        PHONE: "",
        FAX: "",
        EMAIL: "",
        COMMENTS: "",
        PHUONGTHUC: 0,
        ADDRESS: "",
        STATUS: "",
        PROVINCEID: ""
      },
      showModal: false
    };
  },
  computed: {
    isEditing() {
      return Boolean(this.selectedMember);
    }
  },
  watch: {
    selectedMember: {
      handler(newValue) {
        if (newValue) {
          delete newValue.password;
          this.member = Object.assign(this.member, {
            ...newValue,
            PASSWORD: ""
          });
        }
      },
      immediate: true,
      deep: true
    },
    async show(newValue) {
      this.showModal = newValue;
      if (newValue) {
        document.querySelector(".dashboard-header").style.zIndex = -1;
        const { AGENTID } = this.row;
        await this.getMembers(AGENTID);
      } else {
        document.querySelector(".dashboard-header").style.zIndex = "initial";
      }
    }
  },
  methods: {
    closeModal() {
      this.$emit("update:show", false);
      this.$emit("update:row", null);
      this.showModal = false;
    },
    async createMember() {
      const { AGENTID } = this.row;
      const {
        PHONE,
        NAME,
        STATUS,
        FAX,
        EMAIL,
        GHICHU,
        PASSWORD,
        ADDRESS,
        PROVINCEID
      } = this.member;
      const payload = {
        type: typeMemberAction.CREATE,
        parentid: AGENTID,
        phone: PHONE,
        name: NAME,
        status: STATUS,
        fax: FAX,
        email: EMAIL,
        ghichu: GHICHU,
        password: PASSWORD,
        address: ADDRESS,
        provinceid: PROVINCEID
      };
      const response = await api.createUpdateMemberDaiLy(
        this.$root.axios,
        payload
      );
      const { error_code, message } = response.data;
      if (error_code === "BSS-00000000") {
        this.$toast.success(message, "title");

        // Update table
        await this.getMembers(AGENTID);

        // Reset state
        this.cancelEdit();
      } else this.$toast.warning(message, "title");
    },
    async updateMember() {
      try {
        this.$root.showLoading(true);
        const { AGENTID } = this.row;
        const {
          ID,
          NAME,
          USERNAME,
          STATUSUSER,
          PASSWORD,
          PHONE,
          FAX,
          EMAIL,
          COMMENTS,
          PHUONGTHUC,
          ADDRESS,
          STATUS,
          PROVINCEID
        } = this.member;
        const payload = {
          type: typeMemberAction.UPDATE,
          id: ID,
          parentid: AGENTID,
          name: NAME,
          user_name: USERNAME,
          statususer: STATUSUSER,
          password: PASSWORD,
          phone: PHONE,
          fax: FAX,
          email: EMAIL,
          ghichu: COMMENTS,
          phuongthuc: PHUONGTHUC,
          address: ADDRESS,
          status: STATUS,
          provinceid: PROVINCEID
        };
        const response = await api.createUpdateMemberDaiLy(
          this.$root.axios,
          payload
        );
        const { error_code, message } = response.data;
        if (error_code === "BSS-00000000") {
          this.$toast.success(message, "title");

          // Update table
          await this.getMembers(AGENTID);

          // Reset state
          this.cancelEdit();
        } else this.$toast.warning(message, "title");
      } catch (error) {
        console.log(error);
      } finally {
        this.$root.showLoading(false);
      }
    },
    async getMembers(agent_id) {
      const payload = {
        agent_id
      };
      const response = await api.listMemberDaiLy(this.$root.axios, payload);
      const { data, error_code } = response.data;
      if (error_code === "BSS-00000000") {
        this.memberList = data;
      }
    },
    cancelEdit() {
      this.selectedMember = null;
      this.member = {
        ID: "",
        NAME: "",
        USERNAME: "",
        STATUSUSER: "",
        PASSWORD: "",
        PHONE: "",
        FAX: "",
        EMAIL: "",
        COMMENTS: "",
        PHUONGTHUC: 0,
        ADDRESS: "",
        STATUS: "",
        PROVINCEID: ""
      };
    }
  },
  mounted() {
    this.modal = document.getElementById("tinhThanh");
  }
};
</script>
