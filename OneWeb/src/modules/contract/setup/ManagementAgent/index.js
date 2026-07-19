import ManagementAgent from "./ManagementAgent.vue";
export const actionTypes = {
  RESET_PASWORD: "RESET_PASWORD",
  CHANGE_PASSWORD: "CHANGE_PASSWORD",
  CHANGE_PERMISSION: "CHANGE_PERMISSION",
  DS_THONG_TIN_DANG_KY: "DS_THONG_TIN_DANG_KY"
};

export const typeMemberAction = {
  CREATE: 1,
  UPDATE: 2
};
export default ManagementAgent;
