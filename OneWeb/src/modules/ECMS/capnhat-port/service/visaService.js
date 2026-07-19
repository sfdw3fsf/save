let root = {}
let login = {}
export default {
    setupRoot:function(_root){
    root = _root
    },
    setLogin: function(_login){
      Object.assign(login,_login)
      console.log("🚀 ~ file: visaService.js ~ line 10 ~ login", login)
    },
    changePortMegaVNN: async function (account, attributes)
    {
      try {
        return await root.context.post(
          "/tichhop/visa-app/changePortMegaVNN",
          {
            accountModel: {accountName: account},
            // login: login,
            attributes: attributes
          }
        )
      }
       catch (error) {
          throw error
      }
      finally {
      }
    },

    // changePortMegaVNN: async function (account, serviceObject)
    // {
    //   try {
    //     let rs = await root.context.post(
    //       "/tichhop/visa-app/changePortMegaVNN",
    //       {
    //         accountModel: {accountName:account},
    //         serviceObject: serviceObject,
    //         login:	login
    //       }
    //     );
    //     return rs.data;
    //   }
    //    catch (error) {
    //       return false;
    //   }
    //   finally {
    //   }
    // },
}
