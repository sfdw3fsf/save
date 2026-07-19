import token from "./token";
import moment from 'moment';
import menu from "../utils/menu";
import cache from './cache.js'
import requestContext from '../utils/RequestContext'
var AuthPlugin = {
  data: {
    CLIENT_KEY: "OneBSS-Client",
  },
  authenticate: function (stringToken) {
    this.setToken(stringToken);
  },
  isAuthenticated: function(){
    return token.getTokenFromStorage() != null;
  },
  destroy: function () {
    token.destroy();
    localStorage.clear();
    // sessionStorage.clear();
  },
  setToken: function (stringToken) {
    token.setToken(stringToken);
  },
  getToken: function () {
    return token.getToken();
  },
  getClaims: function () {
    return token.getClaims();
  },
  getProperty: function (name) {
    return token.getProperty(name);
  },
  getMaTinh: function () {
    return token.getMaTinh();
  },
  getPhanVungID: function () {
    return token.getPhanVungID();
  },
  getNgayCapNhat: function(){
    // tạm thời
    //return moment().subtract(3,'d').format('DD/MM/YYYY');
    try {
      return moment(getNgayCN()).format('DD/MM/YYYY');
    }
    catch (ex) {
      return moment().format('DD/MM/YYYY');
    }
  },
  getNgayCN: async function() {
    try {
      let data = cache.getters.getFromCache('ngay_cn', {})
      if (!data) 
      { 
        var rs = await requestContext.getWithOutToken('/web-danhba/danhmuc/get_ngaycn',{});

        if(rs && rs.data) {
          data = rs.data;
          cache.dispatch('addToCache', { key: 'ngay_cn', subkey: {}, data: rs.data, expirationTime:60000 })

          if(moment(data,'DD/MM/YYYY HH:mm:ss').isValid())
            return moment(data,'DD/MM/YYYY HH:mm:ss').toDate();
          else if (moment(data).isValid())
            return moment(data).toDate();
          else return new Date();
        }
        else return new Date();
      }
      else
      {
        if(moment(data,'DD/MM/YYYY HH:mm:ss').isValid())
          return moment(data,'DD/MM/YYYY HH:mm:ss').toDate();
        else if (moment(data).isValid())
          return moment(data).toDate();
        else return new Date();
      }
    }
    catch (ex) {
      console.error(ex);
      return new Date();
    }
    return new Date();
  },
  getUserName: function () {
    return token.getUserName();
  },
  getNhanVienID: function () {
    return token.getNhanVienID();
  },
  getMaNhanVien: function () {
    return token.getMaNhanVien();
  },
  getDonViID: function () {
    return token.getDonViID();
  },
  getDonViDuLieuID: function () {
    return token.getDonViDuLieuID();
  },
  getNguoiDungID: function () {
    return token.getNguoiDungID();
  },
  getMaCCBS: function () {
    return token.getMaCCBS();
  },
  getIP: function () {
    return token.getIP();
  },
  getMachine: function () {
    return token.getMachine();
  },
  getDsQuyenChuquan: function () {
    return token.getDsQuyenChuquan();
  },
  getThongTinNguoiDung: function() {
    return token.getThongTinNguoiDung();
  },
  getMenuFromStorage: function () {
    return menu.getMenuFromStorage();
  },
  saveMenuToStorage: function (menuString) {
    return menu.saveMenuToStorage(menuString);
  },
  getModules: function (listItems) {
    return menu.getModules(listItems);
  },
  getMenuSorted: function (module, parentItem, listItems) {
    return menu.getMenuSorted(module, parentItem, listItems);
  },
  getMenuItemParents: function (parentID, menuItems) {
    return menu.getMenuItemParents(parentID, menuItems);
  },
  getMenuPath: function (url) {
    return menu.getMenuPath(url);
  },
  getMenuItem: function (url) {
    return menu.getMenuItem(url);
  },
  saveClientInfoToStorage: function (clientInfo) {
    try {
      localStorage.removeItem(this.data.CLIENT_KEY);
      localStorage.setItem(this.data.CLIENT_KEY, JSON.stringify(clientInfo));
      return true;
    } catch (error) {
      return false;
    }
  },
  getClientInfoFromStorage: function () {
    if (localStorage.hasOwnProperty(this.data.CLIENT_KEY))
      return JSON.parse(localStorage.getItem(this.data.CLIENT_KEY));
    else return null;
  },
  getClientInfoProperty: function (name) {
    var obj = this.getClientInfoFromStorage();
    if (obj !== null) {
      return obj[name];
    } else {
      return null;
    }
  },
};

export default function (Vue) {
  Vue.auth = AuthPlugin;
  Object.defineProperties(Vue.prototype, {
    $auth: {
      get: function () {
        return Vue.auth;
      }
    }
  });
}
