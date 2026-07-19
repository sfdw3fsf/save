//http://codequality.devops.vnpt.vn/api/issues/search?resolved=false&rules=Web%3AAvoidCommentedOutCodeCheck&types=CODE_SMELL#
const axios = require('axios');
const FormData = require('form-data');
const querystring = require('querystring');
// axios.post("http://codequality.devops.vnpt.vn/api/authentication/login", {
//     "login": "thangdm1",
//     "password": "Cc9345197"
// }).then((response) => {
//     console.log(response.data);
//     axios.defaults.headers.common['Authorization'] = 'Basic'+ response.data.token;
//     // searchIssues();
// });
//http://codequality.devops.vnpt.vn/project/issues?id=VNPT-IT.OneBSS.oneweb&resolved=false&rules=Web%3AAvoidCommentedOutCodeCheck&types=CODE_SMELL#
axios.defaults.headers.common['Authorization'] = 'Basic MzJiNmEzYTQ5N2MzYjlkMmNlOTcyOTdmYzQ4MDk4NjFhZGE4YmVkZDo=';//32b6a3a497c3b9d2ce97297fc4809861ada8bedd:';
function ResolveIssues() {
//axios.get("http://codequality.devops.vnpt.vn/api/issues/search?componentKeys=VNPT-IT.OneBSS.oneweb&resolved=false&rules=javascript%3AS2737&types=CODE_SMELL&ps=500")
axios.get("http://codequality.devops.vnpt.vn/api/issues/search?componentKeys=VNPT-IT.OneBSS.oneweb&rules=javascript%3ADuplicatePropertyName&resolved=false&types=BUG&ps=500")
.then((response) => {
    const keylist = response.data.issues.map(x=>x.key);

    var bodyFormData = {
        // "comment": "Thu vien ben thu 3, khong sua duoc!",
        // "do_transition": "falsepositive",//"wontfix",
        // "sendNotifications": false,
        // "set_severity": "INFO",
        "assign": "tungduc",
        "issues": keylist.join(',')
    };
    axios.post("http://codequality.devops.vnpt.vn/api/issues/bulk_change",querystring.stringify(bodyFormData)).then((response) => {
        console.log(response.data);
        if(response.data.total > 0 && response.data.success > 0)
            setTimeout(ResolveIssues, 3000); //3s
    }).catch((error) => {
        console.log('Error:', error);
    });
}).catch((error) => {
    console.log('Error:', error);
});
}
ResolveIssues();