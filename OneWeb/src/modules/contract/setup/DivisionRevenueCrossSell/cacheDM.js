import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    cache: new Map() // Initialize an empty Map for the cache
  },
  mutations: {
    addToCache(state, { key, subkey, data, expirationTime }) {
      const cacheItem = {
        data,
        subkey: JSON.stringify(subkey),
        expirationTime: Date.now() + (expirationTime || 60 * 60 * 1000) // Calculate the expiration time based on the current time and the specified expiration period or default to 60 minutes (60 * 60 * 1000 milliseconds)
      };
      state.cache.set(key, cacheItem); // Add data to the cache with the specified key
    },
    removeFromCache(state, key) {
      state.cache.delete(key); // Remove data from the cache with the specified key
    }
  },
  actions: {
    addToCache({ commit }, { key, subkey, data, expirationTime }) {
      commit('addToCache', { key, subkey, data, expirationTime } );
    },
    removeFromCache({ commit }, key) {
      commit('removeFromCache', key);
    }
  },
  getters: {
    getFromCache: state => (key, subkey) => {
      const cacheItem = state.cache.get(key);
      if(cacheItem)
      if (cacheItem && cacheItem.subkey == JSON.stringify(subkey) && cacheItem.expirationTime > Date.now()) {
        return cacheItem.data; // Return the cached data if it exists and has not expired
      }
      state.cache.delete(key); // Remove the expired cache item
      return null; // Return null if the data is not in the cache or has expired
    }
  }
});