import { actions } from './actions';
import { getters } from './getters';
import { mutations } from './mutations';

const state = {
    dsVungQuyHoach: null,
    dsDichVu: null,
    dsLoaiDichVu: null,
    dsQuyHoachVLAN: null,
}

const namespaced = true;

export const quyHoachVLAN = {
    namespaced,
    state,
    mutations,
    getters,
    actions
}

export const getterName = Object.getOwnPropertyNames(getters);
export const actionName = Object.getOwnPropertyNames(actions);
export const statePropertyName = Object.getOwnPropertyNames(state);
