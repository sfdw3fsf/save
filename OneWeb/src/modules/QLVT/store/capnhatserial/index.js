import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  DSSerialCN: [],
  capNhatSerialTC: "",
  logCapNhatSerial: [],
  keySessionIDVT: "",
  ketQuaCN: []
};

const namespaced = true;

export const capnhatserial = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
