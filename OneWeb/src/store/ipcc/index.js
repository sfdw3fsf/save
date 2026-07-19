import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutation";
import { states } from "./states";

const namespaced = true;

export const ipcc = {
  namespaced,
  states,
  getters,
  actions,
  mutations
};
