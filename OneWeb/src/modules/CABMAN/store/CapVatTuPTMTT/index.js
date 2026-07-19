import { actions } from './actions'

const state = {

}

const namespaced = true

export const capvattuptmtt = {
  namespaced,
  state,
  actions,
}

export const actionName = Object.getOwnPropertyNames(actions);
export const statePropertyName = Object.getOwnPropertyNames(state);
