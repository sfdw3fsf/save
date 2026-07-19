import ca from './caFuntions';
import moment from "moment";
import api from './CompleteProfileAPI';
import {YEUCAU_LAY_DULIEU_TOKEN, YEUCAU_DUYET_CMS, CA_CHOXULY, GUI_FILE_HOSO_CMS} from './ca_objects';
import {LOAI_HOPDONG} from '@/constants';
var dllName = "";
var soPIN = "";
var certBase64 = "";
var key = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9Im5vIj8+PExpY2Vuc2U+PFBoYW5NZW0+Vk5QVC1DQSBQbHVnaW48L1BoYW5NZW0+PE5ndW9pQ2FwPlZOUFQgSVQ8L05ndW9pQ2FwPjxDQU1TPio8L0NBTVM+PE5nYXlCYXREYXU+MDEvMTAvMjAyNCAwMDowMDowMDwvTmdheUJhdERhdT48TmdheUtldFRodWM+MDEvMTAvMjAzMCAyMzo1OTo1OTwvTmdheUtldFRodWM+PFNpZ25hdHVyZSB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnIyI+PFNpZ25lZEluZm8+PENhbm9uaWNhbGl6YXRpb25NZXRob2QgQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzEwL3htbC1leGMtYzE0biMiPjxJbmNsdXNpdmVOYW1lc3BhY2VzIFByZWZpeExpc3Q9Imh0dHA6Ly93d3cudzMub3JnL1RSLzIwMDEvUkVDLXhtbC1jMTRuLTIwMDEwMzE1IiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMS8xMC94bWwtZXhjLWMxNG4jIiAvPjwvQ2Fub25pY2FsaXphdGlvbk1ldGhvZD48U2lnbmF0dXJlTWV0aG9kIEFsZ29yaXRobT0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnI3JzYS1zaGExIiAvPjxSZWZlcmVuY2UgVVJJPSIiPjxUcmFuc2Zvcm1zPjxUcmFuc2Zvcm0gQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwLzA5L3htbGRzaWcjZW52ZWxvcGVkLXNpZ25hdHVyZSIgLz48L1RyYW5zZm9ybXM+PERpZ2VzdE1ldGhvZCBBbGdvcml0aG09Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvMDkveG1sZHNpZyNzaGExIiAvPjxEaWdlc3RWYWx1ZT5TQ1ZHbXR2OUNIVDJWdU5qRTRBRWtuNzNnTDQ9PC9EaWdlc3RWYWx1ZT48L1JlZmVyZW5jZT48L1NpZ25lZEluZm8+PFNpZ25hdHVyZVZhbHVlPlRYSUMxT3U1M1dYYjI1N2NzN1ZORy84RFJBRXdCYTgvSmVFRjRlbG54Wm1CRFhHZnBrY2dGbjZxMDRXMjJaaHpraE0rcFV3dEJaVGxBWnNucHlobDBBVmkvQk8rcjZWNlF4d25YbUdhcUpJY0NjalJkRmRIUjBtUXNCTGxWS3NRZ3pBdll5ZG1UU2JqeVJRNlJqMnQ3TkU4SmZUc0UzQzdHU01UVHE2OUdoVTZMUUkzMnBGU1hwT081cVlOeStMNkxlZ002bzd0RnRXazBBTUxFVzFKN0N1Z0Y4am1vWmpoSkJkd0xQN25HbVBiR0dkU1UxV1h2eUY3NGt3bHZRWlQxek9naWZyMk9sKzFTQ3VkUFd2S2dhT2hlMElaM2IrVitGQVpNeE5CeDJsNWtqUExxQ0JsZFUwMmIvYzZFWlZnMVZhbCtQYW9lVWREUnZidXZBMDd0QT09PC9TaWduYXR1cmVWYWx1ZT48S2V5SW5mbz48S2V5VmFsdWU+PFJTQUtleVZhbHVlPjxNb2R1bHVzPnAyOGNVVG5vWEc5VW83NTRwandkWkdIOG1YNFZaUWtBeWtvcWhzQXB2Z1lQeVVRRWtCRTZkOXFzWkwzNkd2MWpGWjF2S2hvbkk2ZkxFeFZyYkhEbTkrRGVLZXpWSVpxUXZMaXM2M2czaHJtelJ0K2Mvb0F6YTZPUnFlQlZWbVBoQ1hPdy9KRHJ3eEgzQ0xacmxSMVhHeG1zcWxqaTcrVk0wcEMvQ0lraWcvNXRGdS9QQ3VUY1RZL05mL3lmbU1UOTdOaXk5Tm5GTlg1Y1FxREVZNWxTQ0Rvb3kwaHBTQ25jWVFzdUxjUUpBZll6dnp3OW9vT2pGallPVXZtY2VtREMxLzBFRTlkYldsV2V0Zm5WTmI4c3pvcXNMQnJMQURCMUNoU2ZubDlvUUZ6R1FWYTBSaUo5eE93UG9KMjVqUWRWaW1FMTh2VE1aV1p0bUdOUzJyKytPUT09PC9Nb2R1bHVzPjxFeHBvbmVudD5BUUFCPC9FeHBvbmVudD48L1JTQUtleVZhbHVlPjwvS2V5VmFsdWU+PFg1MDlEYXRhPjxYNTA5U3ViamVjdE5hbWU+T0lELjAuOS4yMzQyLjE5MjAwMzAwLjEwMC4xLjE9TVNUOjEwMTY4Njk3MzgtMDEyLCBDTj1WTlBUIFNPRlRXQVJFIC0gVk5QVCBDQSAtIFRFU1QgU0lHTiwgTD1D4bqndSBHaeG6pXksIFM9SMOAIE7hu5hJLCBDPVZOPC9YNTA5U3ViamVjdE5hbWU+PFg1MDlDZXJ0aWZpY2F0ZT5NSUlHUlRDQ0JDMmdBd0lCQWdJUVZBRWtqM3lYUkVJN3dLN0tSL2NIc2pBTkJna3Foa2lHOXcwQkFRVUZBREJwTVFzd0NRWURWUVFHRXdKV1RqRVRNQkVHQTFVRUNoTUtWazVRVkNCSGNtOTFjREVlTUJ3R0ExVUVDeE1WVms1UVZDMURRU0JVY25WemRDQk9aWFIzYjNKck1TVXdJd1lEVlFRREV4eFdUbEJVSUVObGNuUnBabWxqWVhScGIyNGdRWFYwYUc5eWFYUjVNQjRYRFRFM01ESXlOekE1TXpJd01Gb1hEVEU1TURJeU56SXhNekl3TUZvd2dZb3hDekFKQmdOVkJBWVRBbFpPTVJJd0VBWURWUVFJREFsSXc0QWdUdUc3bUVreEZUQVRCZ05WQkFjTURFUGh1cWQxSUVkcDRicWxlVEVzTUNvR0ExVUVBd3dqVms1UVZDQlRUMFpVVjBGU1JTQXRJRlpPVUZRZ1EwRWdMU0JVUlZOVUlGTkpSMDR4SWpBZ0Jnb0praWFKay9Jc1pBRUJEQkpOVTFRNk1UQXhOamcyT1Rjek9DMHdNVEl3Z2dFaU1BMEdDU3FHU0liM0RRRUJBUVVBQTRJQkR3QXdnZ0VLQW9JQkFRQ25ieHhST2VoY2IxU2p2bmltUEIxa1lmeVpmaFZsQ1FES1NpcUd3Q20rQmcvSlJBU1FFVHAzMnF4a3Zmb2EvV01Wblc4cUdpY2pwOHNURld0c2NPYjM0TjRwN05VaG1wQzh1S3pyZURlR3ViTkczNXorZ0ROcm81R3A0RlZXWStFSmM3RDhrT3ZERWZjSXRtdVZIVmNiR2F5cVdPTHY1VXpTa0w4SWlTS0QvbTBXNzg4SzVOeE5qODEvL0orWXhQM3MyTEwwMmNVMWZseENvTVJqbVZJSU9pakxTR2xJS2R4aEN5NHR4QWtCOWpPL1BEMmlnNk1XTmc1UytaeDZZTUxYL1FRVDExdGFWWjYxK2RVMXZ5ek9pcXdzR3NzQU1IVUtGSitlWDJoQVhNWkJWclJHSW4zRTdBK2duYm1OQjFXS1lUWHk5TXhsWm0yWVkxTGF2NzQ1QWdNQkFBR2pnZ0hGTUlJQndUQndCZ2dyQmdFRkJRY0JBUVJrTUdJd01nWUlLd1lCQlFVSE1BS0dKbWgwZEhBNkx5OXdkV0l1ZG01d2RDMWpZUzUyYmk5alpYSjBjeTkyYm5CMFkyRXVZMlZ5TUN3R0NDc0dBUVVGQnpBQmhpQm9kSFJ3T2k4dmIyTnpjQzUyYm5CMExXTmhMblp1TDNKbGMzQnZibVJsY2pBZEJnTlZIUTRFRmdRVWJINGh0RnNPYkxEMWswTm9DWGp5RlR2WkNRc3dEQVlEVlIwVEFRSC9CQUl3QURBZkJnTlZIU01FR0RBV2dCUUdhY0RWMVFLS0ZZMUdmZWw4NG1nS1ZheHFyekJvQmdOVkhTQUVZVEJmTUYwR0Rpc0dBUVFCZ2UwREFRRURBUUVDTUVzd0lnWUlLd1lCQlFVSEFnSXdGaDRVQUU4QVNRQkVBQzBBVUFCeUFDMEFNZ0F1QURBd0pRWUlLd1lCQlFVSEFnRVdHV2gwZEhBNkx5OXdkV0l1ZG01d2RDMWpZUzUyYmk5eWNHRXdNUVlEVlIwZkJDb3dLREFtb0NTZ0lvWWdhSFIwY0RvdkwyTnliQzUyYm5CMExXTmhMblp1TDNadWNIUmpZUzVqY213d0RnWURWUjBQQVFIL0JBUURBZ1R3TURRR0ExVWRKUVF0TUNzR0NDc0dBUVVGQndNQ0JnZ3JCZ0VGQlFjREJBWUtLd1lCQkFHQ053b0REQVlKS29aSWh2Y3ZBUUVGTUJ3R0ExVWRFUVFWTUJPQkVURmphSFZqZFhWQVoyMWhhV3d1WTI5dE1BMEdDU3FHU0liM0RRRUJCUVVBQTRJQ0FRQXlxaUpQdnZrQ01NRjNCQmlQWjNHbmVobnd2aHRtRFJKalpSUk5tYjUyMkx3OHlTcUlwbzU3Z1hJU25MZTNxV3lDMEdDV2FWcy9XSjZUSmczZkIxR2FIcDJ1Skc4aEdWMGc1TUpuZ2dBa1dJSEhia2hreVhsS2QveHh2RjdsQ1dzbGg3T0xvL0R3SnpQY0FRS2REbW9zRExrTHV0bGppcktnUDExOFhtVHBKZTljbmhUR1d4bVI0M1JYbzFwSzZNWkpTLzM1QTBhRWNVRndsa3lPSnBGdXorR3JqcTJxSzJZc1Rwdk5JbnczTFpEd1Jra1lqaG1KT0UrZnVrcWZwOFdGY0J0RUdxV0RhQmJtcWdadmNpdDlzK1JvaTNXZU9NeFVLcmkwZlNBS0RjdXhuZjRyeGlmUTBCYmFzOUhVbU5tNjVWRXNtK2Y1QjB2ZFNtNlM3WDhabzU0dVF0UmcvSGQxYUNQeTBBRmRmYUhpKzZhZHFyMlpxM3B0TUlTMTA3bENkcTI2S3lOM1JNeFl2aDVOWnNnODhKYzlJTmxHeEw1OFdEOUVCN016RmtXbW5HYjlKYzF1a0FFYVBaQTl2d1pXWlVBdHBXT0lkVHhObVV2c1pkbFZBOXZEYy9UbkpSYlVrMGFDd3ZxVldNYWpQUG96dDgvQ3Z6SHF3Y091Z2tGdWM3KzhNOVJGaStSdFpYQStrclROZ25US0pSSGQxNjNkV3N5M2ZweUZVOVBBY3lreVRLcUZmaWxGak5pRzg3WDBNWnJwNGxMUmNTdUZrZ0hZNnIvaUlrTHZGTTZESXJUSlN4RFAzYlhvOWpGbDlRT2FiU05mb1B4S200MXU1R3FwZUpYamxOTkFESStHTnRXUTZnM1oybXVZV3BTNTROVWRKTnJ5R1B5THhBPT08L1g1MDlDZXJ0aWZpY2F0ZT48L1g1MDlEYXRhPjwvS2V5SW5mbz48T2JqZWN0PjxTaWduYXR1cmVQcm9wZXJ0aWVzIHhtbG5zPSIiPjxTaWduYXR1cmVQcm9wZXJ0eSBJZD0iU2lnbmluZ1RpbWUiIFRhcmdldD0ic2lnbmF0dXJlUHJvcGVydGllcyI+PFNpZ25pbmdUaW1lPjIwMjQtMDEtMTBUMDE6Mjc6MTNaPC9TaWduaW5nVGltZT48L1NpZ25hdHVyZVByb3BlcnR5PjwvU2lnbmF0dXJlUHJvcGVydGllcz48L09iamVjdD48L1NpZ25hdHVyZT48L0xpY2Vuc2U+";

let CAConstanst = {
  ReqTypeCapMoi:  2,
  ReqTypeGiaHan:  8,
  ReqTypeTatCa:  -1,
  ReqTypeThayDoiThongTin:  49,
  ReqTypeThuHoi:  5,
  ReqTypeTamNgung:  6,
  ReqTypeKhoiPhuc: 60,
};

let token_info = {

}

export default {
  GetData: function (response) {
    //message "No Content"
    if (response.data.error === 200 || response.data.error === '200' || response.data.errorCode === 0 ) {
      return response.data.data
    } else {
      if(response.data.error_code){
        if (response.data.error_code !== "BSS-00000000" && response.data.error_code !== "BSS-00000204"){
          throw response.data.message;
        }
        console.error(response.data.error_code)
      }
      else
        throw response.data.message;
    }
    return null
  },
  async PHAILAM($this, luong_id, code, ds_luong_thaotac) {
    let luong_thaotac = ds_luong_thaotac;
    if(!luong_thaotac) {
      await api.lay_luong_thaotac($this.axios, {luong_id: luong_id,}).then((res) => {
        luong_thaotac = res.data.data;
      });
    }

    if(!luong_thaotac){
      luong_thaotac = [];
      console.warn("PHAILAM luong_thaotac is empty!");
    }

    return luong_thaotac.findIndex(
      (item) => item.enable == 1 && item.code == code
    ) >= 0;
  },
  LayThongTinTokenExist()
  {
    return token_info
  },
  async LayThongTinTokenDuyet_v2($this){
    $this.loading(false);

    if(!vnpt_cams_plugin){
      throw 'Thư viện VNPT plugin chưa sẵn sàng Bạn vui lòng chờ!';
    }

    if(!base64){
      throw 'Thư viện VNPT plugin chưa sẵn sàng. Bạn vui lòng chờ!';
    }

    let pkcs11DriverList = 'vnpt-ca_csp11.dll,vnptca_p11_v8.dll,vnptca_p11_v6.dll,vnptca_p11_v5.dll,vnptca_p11_v7.dll,vnptca_p11_v81.dll,vnptca_p11_v10.dll,gclib.dll,SCardSvr.dll,vnptcav2_p11_v8.dll,vnpt-ca_cl_v1.dll,VNPT-CA_v34.dll,VNPTCACardv8csp11.dll';
    if(!pkcs11DriverList || pkcs11DriverList.length <= 0){
      throw 'Không lấy được Danh sách DRIVER!';
    }
    //console.log("pkcs11DriverList", pkcs11DriverList);
    let data = {}
    let tokenList = [];
    let dll_list = pkcs11DriverList;
    try{
      console.log("Đang checkPlugin vnpt_cams_plugin");
      data = await vnpt_cams_plugin.checkPlugin();
      console.log("vnpt_cams_plugin checkPlugin", data);
      if(data !== "1"){
        throw "VNPT-Cams Plugin chưa được cài đặt hoặc chưa được bật trên máy của bạn!";
      }
      console.log("VNPT-Cams Plugin đã sẵn sàng");
    }catch(e){
      console.error(e);
      throw "VNPT-Cams Plugin chưa được cài đặt hoặc chưa được bật trên máy của bạn!";
    }

    await vnpt_cams_plugin.setLicenseKey(key).then(function (resData) {
      //console.log("setLicenseKey", resData);
      data = JSON.parse(resData);
    }).catch(function (e) {
      console.error(e)
    });

    console.log("setLicenseKey", data);
    if(data.code !=  1){
      throw "VNPT-Cams Plugin setLicenseKey lỗi: " + data.desc;
    }

    //console.log("KySoXML dll_list", dll_list);
    //#region getTokenInfo
    console.log("Đang getTokenInfo...");
    await vnpt_cams_plugin.getTokenInfo(dll_list).then(function (resData) {
      resData = JSON.parse(resData);
      //console.log("vnpt_cams_plugin.getTokenInfo", resData);
      if(resData && resData.code == 1 && resData.data && resData.data.length > 0)
      {
        resData.data = JSON.parse(resData.data);
        tokenList = resData.data;
        return;
      }
      else{
        throw "Không lấy được thông tin Token, bạn vui lòng kiểm tra lại USB Token. " + resData.desc;
      }
    }).catch(function (e) {
      console.error("vnpt_cams_plugin.getTokenInfo", e);
      tokenList = [];
      throw e;
    });
    //#endregion

    if (!tokenList || tokenList.length == 0)
    {
      throw "Bạn không cắm token hoặc không có driver token phù hợp!";
    }

    //#region GetCertInfo
    let certList = [];
    //console.log("tokenList", tokenList);
    dllName = tokenList[0].Driver;
    var tokenSerial = tokenList[0].SerialNumber;
    // token_info.serialnumber = tokenSerial;
    token_info.dllName = dllName;
    console.log(`Đang getCertInfo.. ${dllName} ${tokenSerial}`);
    await vnpt_cams_plugin.getCertInfo(dllName, tokenSerial).then(function (resData) {
      resData = JSON.parse(resData);
      //console.log("vnpt_cams_plugin.getCertInfo", resData);
      if(resData.code == 1 && resData.data){
        certList = JSON.parse(resData.data);
      }
      else{
        throw `Không lấy được CertInfo: code = ${resData.code}`;
      }
    }).catch(function (e) {
      console.error("vnpt_cams_plugin.getCertInfo", e);
      throw e;
    });
    //#endregion

    //console.log('certList', certList);
    if(!certList || certList.length <= 0){
      throw `Không lấy được chứng thư từ USB Token!`;
    }
    let certListModal = certList.map((x) => { return { serialnumber: x.serialNumber , subjectcn: `${x.subjectCN} - Serial: ${x.serialNumber} - Ngày hết hạn: ${x.notAfter}` } })
    //show popup Chọn chứng thư
    let dataModal = {};
    dataModal.type = "dataGrid"
    dataModal.title = "Chọn chứng thư"
    dataModal.list = certListModal;
    dataModal.headers = [
      {fieldName: 'subjectcn', headerText: 'Chủ thể', allowFiltering: false},
    ]

    //show modal chọn chứng thư
    // $this.loading(false);
    console.log($this);
    let res =  await $this.$refs.modalPromise.show(dataModal);
    //console.log("modalPromise", res);
    if(!res || res == false)
    {
      throw 'Bạn đã hủy bỏ thao tác!'
    }

    let cert = certList.filter(x=>x.serialNumber === res[0].serialnumber);
    if(!cert || cert.length == 0){
      throw 'Chứng thư bạn chọn không còn tồn tại!'
    }
    cert = cert[0];
    token_info.serialnumber = cert.serialNumber;
    var tkSerialNumber = tokenSerial;
    var pinType = 1; // 0: SO PIN, 1: USER PIN
    var userPIN = tokenList[0].Label;
    soPIN =  "";//"12345678";

    while(true){
      //show modal nhập mã PIN token;
      res = null
      dataModal.type = "form"
      dataModal.title = "Nhập mã PIN token"
      dataModal.list = [];
      dataModal.list.push({
        id: 1,
        name: 'name',
        type: 'text',
        text: 'User Token',
        required: false,
        readonly: true,
        defaultValue: userPIN
      });
      dataModal.list.push({
        id: 2,
        name: 'pin',
        type: 'password',
        text: 'Nhập mã PIN',
        required: true
      });

      res = await $this.$refs.modalPromise.show(dataModal);
      if(!res || res == false)
      {
        throw 'Bạn đã hủy bỏ thao tác!'
      }
      if(!res || res.pin == "")
      {
        $this.$toast.error("Bạn chưa nhập mã PIN!")
      }

      soPIN = res.pin;
      if(!soPIN || !userPIN) {
        throw "Không tìm thấy token user hoặc bạn chưa nhập mã PIN!";
      }
      // $this.loading(true);
      console.log(`Đang checkLoginToken... ${dllName} ${tkSerialNumber}`);
      await vnpt_cams_plugin.checkLoginToken(dllName, tkSerialNumber, soPIN, pinType).then(function (resData) {
        console.log("checkLoginToken", resData);
        data = JSON.parse(resData);
      }).catch(function (e) {
        console.error("checkLoginToken", e);
      });

      if(data.code !== 1){
        if(data.desc.indexOf('CKR_PIN_LOCKED') !== -1){
          throw "Mã PIN đã bị khóa. " + data.desc;
          //UnLock Token
          // await vnpt_cams_plugin.unlockToken(dllName, tkSerialNumber, soPIN, userPIN).then(function (resData) {
          //     console.log("unlockToken", resData);
          // }).catch(function (e) {
          //     console.error("unlockToken", e);
          // });
        }

        $this.$toast.error("Bạn nhập sai mã PIN của token, vui lòng nhập lại: " + data.desc);
      }
      else{
        break;
      }
    }
    certBase64 = cert.certBase64
    token_info.cert=certBase64;
    console.log(cert);
    token_info.serialnumber = cert.serialNumber
    //token_info.serialnumber = tkSerialNumber;
  },
  async LayThongTinTokenDuyet($this){


    if(!vnpt_cams_plugin){
      throw 'Thư viện VNPT plugin chưa sẵn sàng Bạn vui lòng chờ!';
    }

    if(!base64){
      throw 'Thư viện VNPT plugin chưa sẵn sàng. Bạn vui lòng chờ!';
    }

    let pkcs11DriverList = this.GetData(await api.ca_danhsach_driver($this.axios, {}));
    if(!pkcs11DriverList || pkcs11DriverList.length <= 0){
      throw 'Không lấy được Danh sách DRIVER!';
    }
    //console.log("pkcs11DriverList", pkcs11DriverList);
    let data = {}
    let tokenList = [];
    let dll_list = pkcs11DriverList;
    try{
      console.log("Đang checkPlugin vnpt_cams_plugin");
      data = await vnpt_cams_plugin.checkPlugin();
      console.log("vnpt_cams_plugin checkPlugin", data);
      if(data !== "1"){
        throw "VNPT-Cams Plugin chưa được cài đặt hoặc chưa được bật trên máy của bạn!";
      }
      console.log("VNPT-Cams Plugin đã sẵn sàng");
    }catch(e){
      console.error(e);
      throw "VNPT-Cams Plugin chưa được cài đặt hoặc chưa được bật trên máy của bạn!";
    }

    await vnpt_cams_plugin.setLicenseKey(key).then(function (resData) {
      //console.log("setLicenseKey", resData);
      data = JSON.parse(resData);
    }).catch(function (e) {
      console.error(e)
    });

    console.log("setLicenseKey", data);
    if(data.code !=  1){
      throw "VNPT-Cams Plugin setLicenseKey lỗi: " + data.desc;
    }

    //console.log("KySoXML dll_list", dll_list);
    //#region getTokenInfo
    console.log("Đang getTokenInfo...");
    await vnpt_cams_plugin.getTokenInfo(dll_list).then(function (resData) {
      resData = JSON.parse(resData);
      //console.log("vnpt_cams_plugin.getTokenInfo", resData);
      if(resData && resData.code == 1 && resData.data && resData.data.length > 0)
      {
        resData.data = JSON.parse(resData.data);
        tokenList = resData.data;
        return;
      }
      else{
        throw "Không lấy được thông tin Token, bạn vui lòng kiểm tra lại USB Token. " + resData.desc;
      }
    }).catch(function (e) {
      console.error("vnpt_cams_plugin.getTokenInfo", e);
      tokenList = [];
      throw e;
    });
    //#endregion

    if (!tokenList || tokenList.length == 0)
    {
      throw "Bạn không cắm token hoặc không có driver token phù hợp!";
    }

    //#region GetCertInfo
    let certList = [];
    //console.log("tokenList", tokenList);
    dllName = tokenList[0].Driver;
    var tokenSerial = tokenList[0].SerialNumber;
   // token_info.serialnumber = tokenSerial;
    token_info.dllName = dllName;
    console.log(`Đang getCertInfo.. ${dllName} ${tokenSerial}`);
    await vnpt_cams_plugin.getCertInfo(dllName, tokenSerial).then(function (resData) {
      resData = JSON.parse(resData);
      //console.log("vnpt_cams_plugin.getCertInfo", resData);
      if(resData.code == 1 && resData.data){
        certList = JSON.parse(resData.data);
      }
      else{
        throw `Không lấy được CertInfo: code = ${resData.code}`;
      }
    }).catch(function (e) {
      console.error("vnpt_cams_plugin.getCertInfo", e);
      throw e;
    });
    //#endregion

    //console.log('certList', certList);
    if(!certList || certList.length <= 0){
      throw `Không lấy được chứng thư từ USB Token!`;
    }
    let certListModal = certList.map((x) => { return { serialnumber: x.serialNumber , subjectcn: `${x.subjectCN} - Serial: ${x.serialNumber} - Ngày hết hạn: ${x.notAfter}` } })
    //show popup Chọn chứng thư
    let dataModal = {};
    dataModal.type = "dataGrid"
    dataModal.title = "Chọn chứng thư"
    dataModal.list = certListModal;
    dataModal.headers = [
      {fieldName: 'subjectcn', headerText: 'Chủ thể', allowFiltering: false},
    ]

    //show modal chọn chứng thư
   // $this.loading(false);
    console.log($this);
    let res =  await $this.$refs.modalPromise.show(dataModal);
    //console.log("modalPromise", res);
    if(!res || res == false)
    {
      throw 'Bạn đã hủy bỏ thao tác!'
    }

    let cert = certList.filter(x=>x.serialNumber === res[0].serialnumber);
    if(!cert || cert.length == 0){
      throw 'Chứng thư bạn chọn không còn tồn tại!'
    }
    cert = cert[0];
    token_info.serialnumber = cert.serialNumber;
    var tkSerialNumber = tokenSerial;
    var pinType = 1; // 0: SO PIN, 1: USER PIN
    var userPIN = tokenList[0].Label;
    soPIN =  "";//"12345678";

    while(true){
      //show modal nhập mã PIN token;
      res = null
      dataModal.type = "form"
      dataModal.title = "Nhập mã PIN token"
      dataModal.list = [];
      dataModal.list.push({
        id: 1,
        name: 'name',
        type: 'text',
        text: 'User Token',
        required: false,
        readonly: true,
        defaultValue: userPIN
      });
      dataModal.list.push({
        id: 2,
        name: 'pin',
        type: 'password',
        text: 'Nhập mã PIN',
        required: true
      });
    //  $this.loading(false);
      res = await $this.$refs.modalPromise.show(dataModal);
      if(!res || res == false)
      {
        throw 'Bạn đã hủy bỏ thao tác!'
      }
      if(!res || res.pin == "")
      {
        $this.$toast.error("Bạn chưa nhập mã PIN!")
      }

      soPIN = res.pin;
      if(!soPIN || !userPIN) {
        throw "Không tìm thấy token user hoặc bạn chưa nhập mã PIN!";
      }
     // $this.loading(true);
      console.log(`Đang checkLoginToken... ${dllName} ${tkSerialNumber}`);
      await vnpt_cams_plugin.checkLoginToken(dllName, tkSerialNumber, soPIN, pinType).then(function (resData) {
        console.log("checkLoginToken", resData);
        data = JSON.parse(resData);
      }).catch(function (e) {
        console.error("checkLoginToken", e);
      });

      if(data.code !== 1){
        if(data.desc.indexOf('CKR_PIN_LOCKED') !== -1){
          throw "Mã PIN đã bị khóa. " + data.desc;
          //UnLock Token
          // await vnpt_cams_plugin.unlockToken(dllName, tkSerialNumber, soPIN, userPIN).then(function (resData) {
          //     console.log("unlockToken", resData);
          // }).catch(function (e) {
          //     console.error("unlockToken", e);
          // });
        }

        $this.$toast.error("Bạn nhập sai mã PIN của token, vui lòng nhập lại: " + data.desc);
      }
      else{
        break;
      }
    }
    certBase64 = cert.certBase64
    token_info.cert=certBase64;
    console.log(cert);
    token_info.serialnumber = cert.serialNumber
    //token_info.serialnumber = tkSerialNumber;
  },
  async RAHangLoat($this, ma_gd){
      try {
        let obj = {
          vma_gd: ma_gd,
          vtthd_id: 11,
          vstatus: 0
        };
        let kq = await api.lay_ds_thuebaoca_theo_magd($this.axios, obj);
        return kq;
      }
      catch (e)
      {
        throw e;
      }
  },
  OBJtoXML(obj) {
    var toXML = function(obj){
      var xml = '';
      for (var prop in obj) {
        xml += obj[prop] instanceof Array ? '' : "<" + prop + ">";
        if (obj[prop] instanceof Array) {
          for (var array in obj[prop]) {
            xml += "<" + prop + ">";
            xml += toXML(new Object(obj[prop][array]));
            xml += "</" + prop + ">";
          }
        } else if (typeof obj[prop] == "object") {
          xml += toXML(new Object(obj[prop]));
        } else {
          xml += obj[prop];
        }
        xml += obj[prop] instanceof Array ? '' : "</" + prop + ">";
      }
      var xml = xml.replace(/<\/?[0-9]{1,}>/g, '');
      return xml;
    }

    let xml = toXML(obj);

    return '<?xml version="1.0" encoding="UTF-8"?>' + xml
  },
  async KyHangLoat(input)
  {

    let data = {};
    console.log(dllName);
    console.log(soPIN);
    console.log(certBase64);
    var dataInput = base64.encode(input);
    await vnpt_cams_plugin.signXml(dllName, soPIN, certBase64, dataInput).then(function (resData) {
      //console.log("signXml done!");
      data = JSON.parse(resData);
    }).catch(function (e) {
      console.error("signXml", e);
    });

    if(!data || data.code !== 1){
      throw "Lỗi: " + data.desc;
    }

    let ketqua = data.data
    return ketqua;
  },
  async KyHash(input)
  {

    let data = {};
    console.log(dllName);
    console.log(soPIN);
    console.log(certBase64);
    //var dataInput = base64.encode(input);
    var dataInput = input;
    await vnpt_cams_plugin.signHash(dllName, soPIN, certBase64, dataInput).then(function (resData) {
      //console.log("signXml done!");
      console.log(certBase64)
      console.log(dllName)
      console.log(dataInput)
      console.log(soPIN)
      data = JSON.parse(resData);
    }).catch(function (e) {
      console.error("signHash", e);
      console.log(e);
      throw e;
    });

    if(!data || data.code !== 1){
      throw "Lỗi: " + data.desc;
    }

    let ketqua = data.data
    return ketqua;
  },
  resetToken()
  {
    dllName = "";
    soPIN = "";
    certBase64 = "";
  },
  async DuyetHangLoat($this, ma_gd)
  {
    if(dllName == "" || soPIN == "" || certBase64 == "")
    {
      await this.LayThongTinTokenDuyet($this);
    }
    let obj = {
      vma_gd: ma_gd,
      vtthd_id: 9,
      vstatus: 3
    };
    let kq = await api.lay_ds_thuebaoca_theo_magd($this.axios, obj);

    return kq;
  },
  async HuyDuyetCA($this, data)
  {
    let loaihd_id = data.loaihd_id;
    let kieuld_id = data.kieuld_id;
    let hdtb_id = data.hdtb_id;
    let thuebao_id = data.thuebao_id;

    let lst = this.GetData(await api.lay_dulieu_tao_thucthe_ca($this.axios, {params: {hdtb_id: hdtb_id}}))
    if(!lst || lst.length <= 0){
      return 'Không tìm thấy dữ liệu thực thể CA!';
    }

    let ee_id = lst[0].ee_id;
    let req_id = lst[0].req_id;
    let ten_tb_ca = lst[0].ten_tb;
    let status = lst[0].status;

    let ycduyet_cms = Object.assign({}, YEUCAU_DUYET_CMS);
    ycduyet_cms.req_id = req_id;
    ycduyet_cms.type = 0; // không duyệt CA
    ycduyet_cms.reason = "Hủy duyệt trên OneBSS (Web)";
    ycduyet_cms.ma_tb = lst[0].ma_tb;
    ycduyet_cms.promotional_month = lst[0].promotional_month;
    ycduyet_cms.smartca_suffix = lst[0].smartca_suffix;
    ycduyet_cms.mst_dn = lst[0].mst_dn;
    ycduyet_cms.company_name = lst[0].ten_kh;

    if (loaihd_id == 1)
    {
      ycduyet_cms.smartca_suffix = lst[0].smartca_suffix;
      ycduyet_cms.mst_dn = lst[0].mst_dn;
    }
    if (loaihd_id == 41)
    {
      ycduyet_cms.smartca_request_type = lst[0].smartca_request_type;
      if (lst[0].smartca_request_type == 7)
      {
        ycduyet_cms.reason = "đổi khóa tài khoản/ đổi máy điện thoại";
      }
    }
    if (kieuld_id == 13179 || loaihd_id == 4 || loaihd_id == 7 || kieuld_id == 13261)
    {
      ycduyet_cms.promotional_month = 0;
    }

    if (loaihd_id == LOAI_HOPDONG.CHAMDUT_SD)
    {
      if (kieuld_id == 13263) // thu hồi CA tạo lỗi
      {
        ycduyet_cms.smartca_cancel_account = 0;
        let kt_huy_acc = this.GetData(await api.kiemtra_huy_acc_smartca_thuhoi_taoloi($this.axios, {params: {hdtb_id: hdtb_id}}));
        if (kt_huy_acc.toLowerCase() == "ok")
        {
          ycduyet_cms.smartca_cancel_account = 1;
        }
      }
      else
      {
        ycduyet_cms.smartca_cancel_account = 1;
      }
    }

    ycduyet_cms.matinh_ca = lst[0].matinh_ca;
    ycduyet_cms = {
      root: Object.assign({}, ycduyet_cms) //root: root
    };

    let yeucau_xml_chuaky = this.OBJtoXML(ycduyet_cms);
    let yeucau_xml =await this.KyHangLoat(yeucau_xml_chuaky);

    // if (ee_id == 0 || req_id == 0)
    // {
    //     throw (`Dữ liệu tạo thực thể CA không hợp lệ ee_id: ${ee_id} req_id: ${req_id}`);
    // }


    if(status == 3 || status == 7){
      let ca_choxl = Object.assign({}, CA_CHOXULY);
      ca_choxl.dulieu = yeucau_xml;
      ca_choxl.kieu_yc = CAConstanst.ReqTypeCapMoi;
      if (loaihd_id == 41 && (kieuld_id == 13177 || kieuld_id == 13248))
      {
        ca_choxl.kieu_yc = CAConstanst.ReqTypeGiaHan;
      }
      if (loaihd_id == 41 && kieuld_id == 13179)
      {
        ca_choxl.kieu_yc = CAConstanst.ReqTypeThayDoiThongTin;
      }
      if (loaihd_id == 4)
      {
        ca_choxl.kieu_yc = CAConstanst.ReqTypeThuHoi;
      }
      if (loaihd_id == 7)
      {
        if (kieuld_id == 714)
        {
          ca_choxl.kieu_yc = CAConstanst.ReqTypeTamNgung;
        }
        else
        {
          ca_choxl.kieu_yc = CAConstanst.ReqTypeKhoiPhuc;
        }

      }
      ca_choxl.ma_tinh = lst[0].ma_tinh;
      ca_choxl.kieu_xl = 2; // yêu cầu duyệt
      ca_choxl.trangthai = 0;
      ca_choxl.hdtb_id = hdtb_id;
      ca_choxl.thuebao_id = thuebao_id;

      let res = this.GetData(await api.GuiYeuCau($this.axios, ca_choxl));
      if(res){
        try{
          let kq = await api.DongBoHuyDuyetCA($this.axios, { vhdtb_id: hdtb_id, vdulieu: res.DULIEU_CMS });
          if (kq.data.data != "ok")
            return kq.data.data;
          else
            return "Hủy duyệt thành công!";
        }catch(ex){
          console.error("DongBoCMS", ex);
        }
        return "Hủy duyệt thành công!";
      }
      else{
        return ("Tạo yêu cầu thất bại: " + res);
      }
    }
  },
  async DuyetCA($this, data){
    try {
      let loaihd_id = data.loaihd_id;
      let kieuld_id = data.kieuld_id;
      let hdtb_id = data.hdtb_id;
      let thuebao_id = data.thuebao_id;

      let lst = this.GetData(await api.lay_dulieu_tao_thucthe_ca($this.axios, { params: { hdtb_id: hdtb_id } }))
      if (!lst || lst.length <= 0) {
        return 'Không tìm thấy dữ liệu thực thể CA!';
      }

      let files = this.GetData(await api.lay_ds_hoso_ca($this.axios, { params: { hdtb_id: hdtb_id } }));
      console.log(files);
      let ee_id = lst[0].ee_id;
      let req_id = lst[0].req_id;
      let ten_tb_ca = lst[0].ten_tb;
      let status = lst[0].status;

      let ycduyet_cms = Object.assign({}, YEUCAU_DUYET_CMS);

      ycduyet_cms.req_id = req_id;
      ycduyet_cms.type = 1;
      ycduyet_cms.reason = "";
      // ycduyet_cms.files = base64.encode(JSON.stringify(files));
      ycduyet_cms.files = btoa(unescape(encodeURIComponent(JSON.stringify(files))));

      ycduyet_cms.ma_tb = lst[0].ma_tb;
      ycduyet_cms.promotional_month = lst[0].promotional_month;
      ycduyet_cms.smartca_suffix = lst[0].smartca_suffix;
      ycduyet_cms.mst_dn = lst[0].mst_dn;
      ycduyet_cms.company_name = lst[0].ten_kh;
      if (loaihd_id == 1) {
        ycduyet_cms.smartca_suffix = lst[0].smartca_suffix;
        ycduyet_cms.mst_dn = lst[0].mst_dn;

      }
      if (loaihd_id == 41) {
        ycduyet_cms.smartca_request_type = lst[0].smartca_request_type;
        if (lst[0].smartca_request_type == 7) {
          ycduyet_cms.reason = "đổi khóa tài khoản/ đổi máy điện thoại";
        }
      }
      if (kieuld_id == 13179 || loaihd_id == 4 || loaihd_id == 7 || kieuld_id == 13261) {
        ycduyet_cms.promotional_month = 0;
      }

      if (loaihd_id == LOAI_HOPDONG.CHAMDUT_SD) {
        if (kieuld_id == 13263) // thu hồi CA tạo lỗi
        {
          ycduyet_cms.smartca_cancel_account = 0;
          let kt_huy_acc = this.GetData(await api.kiemtra_huy_acc_smartca_thuhoi_taoloi($this.axios, { hdtb_id: hdtb_id }));
          if (kt_huy_acc.toLowerCase() == "ok") {
            ycduyet_cms.smartca_cancel_account = 1;
          }
        } else {
          ycduyet_cms.smartca_cancel_account = 1;
        }
      }

      //  ycduyet_cms.matinh_ca = lst[0].matinh_ca;
      ycduyet_cms = {
        root: Object.assign({}, ycduyet_cms) //root: root
        // root: {
        //   ...ycduyet_cms
        // }
      };
      console.log(382);


      let yeucau_xml_chuaky = this.OBJtoXML(ycduyet_cms);

      yeucau_xml_chuaky = yeucau_xml_chuaky.replace('<root>', '<root xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">');
      console.log(yeucau_xml_chuaky);
      let yeucau_xml = await this.KyHangLoat(yeucau_xml_chuaky);

      // if (!ee_id || !req_id || ee_id == 0 || req_id == 0)
      // {
      //     throw (`Dữ liệu tạo thực thể CA không hợp lệ ee_id: ${ee_id} req_id: ${req_id}`);
      // }

      if (status == 7) {
        return "Yêu cầu đã được duyệt trước đó!";
      }
      if (status == 0) {
        return "Yêu cầu đã hủy duyệt trước đó hoặc chưa kích hoạt yêu cầu!";
      }

      if (status == 3) {
        let ca_choxl = Object.assign({}, CA_CHOXULY);
        ca_choxl.dulieu = yeucau_xml;
        ca_choxl.kieu_yc = CAConstanst.ReqTypeCapMoi;
        if (loaihd_id == 41 && (kieuld_id == 13177 || kieuld_id == 13248)) {
          ca_choxl.kieu_yc = CAConstanst.ReqTypeGiaHan;
        }
        if (loaihd_id == 41 && kieuld_id == 13179) {
          ca_choxl.kieu_yc = CAConstanst.ReqTypeThayDoiThongTin;
        }
        if (loaihd_id == 41 && kieuld_id == 13261) // thay đổi giấy tờ
        {
          ca_choxl.kieu_yc = CAConstanst.ReqTypeThayDoiThongTin;
        }
        if (loaihd_id == 4) {
          ca_choxl.kieu_yc = CAConstanst.ReqTypeThuHoi;
        }
        if (loaihd_id == 7) {
          if (kieuld_id == 714) {
            ca_choxl.kieu_yc = CAConstanst.ReqTypeTamNgung;
          } else {
            ca_choxl.kieu_yc = CAConstanst.ReqTypeKhoiPhuc;
          }

        }
        ca_choxl.ma_tinh = lst[0].ma_tinh;
        ca_choxl.kieu_xl = 2; // yêu cầu duyệt
        ca_choxl.trangthai = 0;
        ca_choxl.hdtb_id = hdtb_id;
        ca_choxl.thuebao_id = thuebao_id;

        let res = this.GetData(await api.GuiYeuCau($this.axios, ca_choxl));
        if (res) {
          let kq = await api.DongBoDuyetCA($this.axios, { vhdtb_id: hdtb_id, vdulieu: res.DULIEU_CMS });
          if (kq.data.data != "ok")
            throw (kq.data.data);
          else
            return "Duyệt thành công!";
        } else {
          return ("Duyệt thất bại: " + res);
        }
      }
    }
    catch (e)
    {
      console.log(e);
      return JSON.stringify(e);
    }
  },
  async DuyetCAOnline($this, data){
    try
    {
      let req_str = await api.tao_req_cntt($this.axios, {vhdtb_id: data.hdtb_id});
      console.log(req_str.data.data);
      let kq = await api.duyet_smartca_onesme($this.axios, JSON.parse(req_str.data.data));
     //return kq.data;
      if(kq.data.error_code !== "BSS-00000000" && kq.data.error_code !== "BSS-00000204")
      {
         throw kq.data.data;
      }
      else
      {
        return kq.data.data;
      }
    }
    catch(ex)
    {
      throw ex;
      console.log(ex);
    }
  },

  async DuyetCAOnline_v2($this, data){
    try
    {
      let req_str = await api.tao_req_cntt($this.axios, {vhdtb_id: data.hdtb_id});
      console.log(req_str.data.data);
      let kq = await api.duyet_smartca_onesme($this.axios, JSON.parse(req_str.data.data));
     //return kq.data;
      if(kq.data.error_code !== "BSS-00000000" && kq.data.error_code !== "BSS-00000204")
      {
         throw kq.data.data;
      }
      else
      {
        return kq.data.data;
      }
    }
    catch(ex)
    {
      throw ex;
      console.log(ex);
    }
  },

  async RASmartCA_V2($this, data)
  {
    try {
      let kq = await api.ra_smartca_v2($this.axios, {
        hdtb_id: data.hdtb_id
      });
      $this.$toast.success("RA thành công!")
    }
    catch (e)
    {
      throw  e;
    }
  },

  async LayThongTinSerialDynamicAPI()
  {
    try {
      let kq = await api.call_dynamic_api($this.axios, {
        hdtb_id: data.hdtb_id,
        vdem: 50000,
        vtype: 'lay_dulieu_smca'
      });
      console.log(kq);
      if(kq.data.data.status == '00')
        $this.$toast.success(kq.data.data.message)
      else
      {
        let loi = kq.data.data.message;
        $this.$toast.error(loi);
      }
    }
    catch (e)
    {
      throw  e;
    }
  },

  async RADynamicAPI($this, data)
  {
    try {
      let kq = await api.call_dynamic_api($this.axios, {
        hdtb_id: data.hdtb_id,
        vdem: 0,
        vtype: data.nv_thaotac
      });
      console.log(kq);
      if(kq.data.data.status == '00')
        $this.$toast.success("RA thành công!")
      else
      {
        let loi = kq.data.data.message;
        $this.$toast.error(loi);
      }
    }
    catch (e)
    {
      throw  e;
    }
  },

  async HuyDuyetSmartCAOnline(ax, hdtb_id, noidung)
  {
    try
    {
      let req_str = await api.tao_req_cntt_v2(ax, {vhdtb_id: hdtb_id, vkey: 'HUY', dem: 0});
      console.log(req_str.data.data);
      let obj = JSON.parse(req_str.data.data);
      let sent_req = {
        ...obj,
        lydo: noidung
      };
      console.log(sent_req);
      let kq = await api.duyet_smartca_onesme(ax, sent_req);
      //return kq.data;
      if(kq.data.error_code !== "BSS-00000000" && kq.data.error_code !== "BSS-00000204")
      {
        alert(kq.data.data);
        throw kq.data.data;
      }
      else
      {
        alert("Huy thanh cong");
        return kq.data.data;
      }
    }
    catch(ex)
    {
      alert(ex + " - loi");
      throw ex;
      console.log(ex);
    }
  },
  async TaoGoiNganSachTaiNguyen(ax, hdtb_id, $this)
  {
    try {
      $this.loading(true);
      let req_ns = await api.fn_tao_req_cntt(ax, { vhdtb_id: hdtb_id });
      if(req_ns.data.error_code != "BSS-00000000")
      {
          throw (req_ns.data.message + " - " + req_ns.data.data);
      }
      let req_cle = req_ns.data.data.replace('\\r\\n', '');
      console.log(req_cle);
      let kq = await api.dangky_ngansach_smartca(ax, {
        StrRequest: req_cle,
        StrLink: "",
        JwtToken: ""
      });
      if(kq.data.error_code == "BSS-00000000" && (kq.data.data.code == 0))
      {
        $this.$toast.success('Dang ky goi tai nguyen thanh cong');
        let kq_status =  await api.cap_nhat_status_hdtb(ax, {
          vhdtb_id: hdtb_id,
          vstatus: 6
        });
      }
      else
      {
        let kq_status =  await api.cap_nhat_status_hdtb(ax, {
          vhdtb_id: hdtb_id,
          vstatus: 0
        });
        throw kq.data.data.message;
      }
    }
    catch (ex)
    {
      console.log(ex);
      $this.$toast.error(ex);
    }
    finally {
      $this.loading(false);
    }
  },
  async MuaThemLuotKyGoiTaiNguyen(ax, hdtb_id, $this)
  {
    try {
      $this.loading(true);
      let req_ns = await api.fn_tao_req_cntt(ax, { vhdtb_id: hdtb_id });
      if(req_ns.data.error_code != "BSS-00000000")
      {
        throw (req_ns.data.message + " - " + req_ns.data.data);
      }
      let req_cle = req_ns.data.data.replace('\\r\\n', '');
      console.log(req_cle);
      let kq = await api.dangky_luotky_smartca(ax, {
        StrRequest: req_cle,
        StrLink: "",
        JwtToken: ""
      });
      if(kq.data.error_code == "BSS-00000000" && (kq.data.data.code == 0))
      {
        $this.$toast.success('Mua them luot ky thanh cong');
        let kq_status =  await api.cap_nhat_status_hdtb(ax, {
          vhdtb_id: hdtb_id,
          vstatus: 6
        });
      }
      else
      {
        let kq_status =  await api.cap_nhat_status_hdtb(ax, {
          vhdtb_id: hdtb_id,
          vstatus: 0
        });
        throw kq.data.data.message;
      }
    }
    catch (ex)
    {
      console.log(ex);
      $this.$toast.error(ex);
    }
    finally {
      $this.loading(false);
    }
  }
}
