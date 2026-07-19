import axios from "axios";
import token from "../plugins/token";
import menu from "./menu";

export default {
  async getOriginal(url, params) {
    var instance = axios.create();
    instance.interceptors.request.use(request => {
      request.baseURL = process.env.API;
      request.headers = {
        "Content-Type": "application/json",
        Authorization: token.getAuthorization(),
        SelectedMenuId: this.getCurrentMenuItemID(),
        SelectedPath: this.getCurrentPath()
      };
      request.data = {};
      return request;
    });
    var begin = Date.now();
    var r = await instance.get(url, { params: params });
    if (process.env.NODE_ENV !== "production") {
      console.info(
        "url: " + url + " (" + (Date.now() - begin) + "ms)"
      );
    }
    return r;
  },
  async get(url, params) {
    var r = await this.getOriginal(url, params)
    return r.data;
  },
  async postOriginal(url, data) {
    var instance = axios.create();
    instance.interceptors.request.use(request => {
      request.baseURL = process.env.API;
      request.headers = {
        "Content-Type": "application/json",
        Authorization: token.getAuthorization(),
        SelectedMenuId: this.getCurrentMenuItemID(),
        SelectedPath: this.getCurrentPath()
      };
      return request;
    });
    var begin = Date.now();
    var r = await instance.post(url, data);
    if (process.env.NODE_ENV !== "production") {
      console.info(
        "url: " + url + " (" + (Date.now() - begin) + "ms)"
      );
    }
    return r;
  },
  async post(url, data) {
    var r = await this.postOriginal(url, data);
    return r.data;
  },
  async getWithOutToken(url, params) {
    var instance = axios.create();
    var r = await instance.get(url, { params: params });
    return r.data;
  },
  async postWithOutToken(url, data) {
    var instance = axios.create();
    var begin = Date.now();
    var r = await instance.post(url, data).then(function (response) {
      console.info(
        "url: " + url + " (" + (Date.now() - begin) + "ms)"
      );
      return response;
    });
    return r.data;
  },
  getCurrentMenuItemID() {
    return menu.getCurrentMenuItemID();
  },
  getCurrentPath() {
    return menu.getCurrentPath();
  },
  async download(method, url, params) {
    var instance = axios.create();
    instance.interceptors.request.use(request => {
      request.baseURL = process.env.API;
      request.responseType = 'blob';
      request.headers = {
        "Content-Type": "application/json",
        Authorization: token.getAuthorization(),
        SelectedMenuId: this.getCurrentMenuItemID(),
        SelectedPath: this.getCurrentPath()
      };
      request.data = {};
      return request;
    });
    if(method == "GET")
      return await instance.get(url, { params: params });
    else
      return await instance.post(url, params);
  },
  /**
   *
   * @param {*} url Giá trị đường dẫn hình ảnh trên máy chủ. Ví dụ: ECMS/26/2021-09-13/cdd0c32c-3891-4c6f-88cb-2bb300ac322f/1.jpg
   * @param {*} module nơi lưu trữ hình ảnh: ECMS, CABMAN, SUCO. Ví dụ: ECMS
   * @returns địa chỉ xem hình ảnh trên máy chủ
   */
   viewImageUrl(url, module) {
    url = "/storage/viewImage?imageSource=" + url;
    switch (module) {
      case "ECMS":
        url = "/web-ecms" + url;
        break;
      case "CABMAN":
        url = "/web-cabman" + url;
        break;
      case "SUCO":
        url = "/web-suco" + url;
        break;
      default:
        url = "/web-ecms" + url;
        break;
    }
    return process.env.API + url;
  }
}
