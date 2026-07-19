import { actions } from "./actions"
import { getters } from "./getters"
import { mutations } from "./mutations"

const state = {
    dsChuongTrinhCSKH: []
}

const namespaced = true;

export const chuongtrinhCSKH = {
    actions,
    getters,
    mutations,
    state,
    namespaced
}