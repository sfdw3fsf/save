import state from "./state";
import actions from "./actions";
import mutations from "./mutations";
import getters from "./getters";

const namespaced = true;

export const TiepNhanKN = {
  namespaced,
  state,
  actions,
  getters,
  mutations
};

export const getterName = Object.getOwnPropertyNames(getters);
export const actionName = Object.getOwnPropertyNames(actions);
export const statePropertyName = Object.getOwnPropertyNames(state);
