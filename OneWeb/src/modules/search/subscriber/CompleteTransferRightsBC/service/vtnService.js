let root = {}
let login = {}
export default  {
   setupRoot:function(_root){
    root = _root
    },
    setLogin: function(_login){
        Object.assign(login,_login)
      },
    ChuyenQuyen:async function(ma_tb, hdtt_id, hdtb_id){
        try {
            let rs = await root.context.post(
                "/tichhop/visa-app/changeBillingInfo",
                {
                    ma_tb: ma_tb,
                    hdtt_id: hdtt_id,
                    hdtb_id: hdtb_id,
                    login:login
                }
              );
              return "OK"//rs.data;
        } catch (error) {
            return false;
        } finally {
        }
    },
}