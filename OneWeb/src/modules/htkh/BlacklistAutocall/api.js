export default{
    ImportFile: (axios,data) => axios.post("/web-tratruoc/ob_ghtt/fn_import_blacklist_autocall",data),
    LayTTAutoCall: (axios,data) => axios.post("/web-tratruoc/ob_ghtt/fn_lay_tt_blacklist_autocall",data)
}
